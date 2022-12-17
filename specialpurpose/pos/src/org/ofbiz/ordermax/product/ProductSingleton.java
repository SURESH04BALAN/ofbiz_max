/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.ListModel;
import mvc.controller.LoadProductWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.Product;
import static org.ofbiz.ordermax.product.UomQuantitySingleton.getInstance;
import org.ofbiz.ordermax.product.productloader.ProductDataLoaderInterace;

/**
 *
 * @author siranjeev
 */
public class ProductSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Product> valueMap = null;

    private ProductSingleton() {
        valueMap = new HashMap<String, Product>();
    }

    private static class SingletonHolder {

        public static final ProductSingleton INSTANCE = new ProductSingleton();
    }

    public static ProductSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<Product> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<Product>(getInstance().valueMap.values());
    }

    final static public ListModel<Product> getValueListModal() {
        ListAdapterListModel<Product> modal = new ListAdapterListModel<Product>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("Product", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                Product product = new Product(val);
                getInstance().valueMap.put(product.getproductId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProduct(String productId) {
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productId", productId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("Product", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                String smallImageUrl = val.getString("smallImageUrl");
                if (UtilValidate.isEmpty(smallImageUrl)) {
                    ProductContentComposite comp = null;// LoadProductWorker.loadProductContent(productId, "SMALL_IMAGE_URL", ControllerOptions.getSession());
                    //ProductContentComposite comp = list.getProductContent("SMALL_IMAGE_URL");
                    if (comp != null) {
                        smallImageUrl = comp.getDataResource().getobjectInfo();
                    }
                }
                val.setString("smallImageUrl", smallImageUrl);
                Product product = new Product(val);

                getInstance().valueMap.put(product.getproductId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Product getProduct(String productId) throws Exception {
        Product product = null;
//        Debug.logInfo("Find productId: "+ productId, productId);

        if (getInstance().valueMap.containsKey(productId)) {
            product = getInstance().valueMap.get(productId);
            //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProduct(productId);
            getInstance().valueMap = sortByComparator(getInstance().valueMap);
            if (getInstance().valueMap.containsKey(productId)) {
                product = getInstance().valueMap.get(productId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load product[productId] : " + productId);
            }
        }

        return product;
    }

    private static final Comparator<Map.Entry<String, Product>> valueComparator = new Comparator<Map.Entry<String, Product>>() {
        @Override
        public int compare(Map.Entry<String, Product> e1,
                Map.Entry<String, Product> e2) {
            //comparing by values, if it's equals, compare by keys
            // in other case, entries with equals values will be removed
            if (UtilValidate.isNotEmpty(e1.getValue().getinternalName()) && UtilValidate.isNotEmpty(e2.getValue().getinternalName())) {
                if (e1.getValue().getinternalName().equals(e2.getValue().getinternalName())) {
                    return e1.getKey().compareTo(e2.getKey());
                }

                return (e1.getValue().getinternalName()).compareTo(e2.getValue().getinternalName());
            }
            return 0;
        }
    };

    private static Map<String, Product> sortByComparator(
            Map<String, Product> unsortMap) {

        // sorted set based on comparator
        SortedSet<Map.Entry<String, Product>> set = new TreeSet<Map.Entry<String, Product>>(valueComparator);
        set.addAll(unsortMap.entrySet());

        // LinkedHashMap make sure order in which keys were inserted
        Map<String, Product> sortedMap = new LinkedHashMap<String, Product>();
        for (Iterator<Map.Entry<String, Product>> it = set.iterator(); it
                .hasNext();) {
            Map.Entry<String, Product> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    /* private static XuiSession singletonSession = null;

     public static XuiSession getSingletonSession() {
     return singletonSession;
     }

     public static void setSingletonSession(XuiSession singletonSession) {
     ProductSingleton.singletonSession = singletonSession;
     }*/

    static public ProductTreeNode getProductTreeNode(String productId) throws Exception {
        Product product = getProduct(productId);

        //public ProductTreeNode getProductTreeNode(Product product) {
        GenericValue productGen = product.getGenericValueObj();
        //String productId = productGen.getString("productId");

        String prodName = productGen.getString("productName");
        String smallImageUrl = productGen.getString("smallImageUrl");

        ProductTreeNode newProdNode = new ProductTreeNode(productId, prodName, true, productGen);
        newProdNode.setVirtual("Y".equalsIgnoreCase(productGen.getString("isVirtual")));
        newProdNode.setVariant("Y".equalsIgnoreCase(productGen.getString("isVariant")));
        newProdNode.setImagePath(smallImageUrl);

        if (UtilValidate.isNotEmpty(newProdNode.getImagePath())) {
            newProdNode.setIcon(loadIcons(newProdNode.getImagePath()));
        } else {
            newProdNode.setIcon(null);
        }

        return newProdNode;
    }

    static public ImageIcon loadIcons(String fileName) {
        ImageIcon iconVal = null;
        if (UtilValidate.isNotEmpty(fileName)) {
            ImageIcon icon = BaseHelper.loadImage(fileName);
            if (icon != null) {
//            ImageIcon icon = new ImageIcon(getImage(fileName));
                Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                //   ImageIcon icon = new ImageIcon(getImage(iconPathSmall));
                iconVal = new ImageIcon(image);
            }
        }

        return iconVal;
    }
    static VariantProductDataLoader variantProductDataLoader = null;

    static public List<TreeNode> getAllVariantProductsTreeNodes() {
        if (variantProductDataLoader == null) {
            variantProductDataLoader = new VariantProductDataLoader();
        }
        return variantProductDataLoader.getAllProductsTreeNodes();
    }

    static VirtualProductDataLoader virtualProductDataLoader = null;

    static public List<TreeNode> getAllVirtualProductsTreeNodes() {
        if (virtualProductDataLoader == null) {
            virtualProductDataLoader = new VirtualProductDataLoader();
        }
        return virtualProductDataLoader.getAllProductsTreeNodes();
    }

    static public class VariantProductDataLoader implements ProductDataLoaderInterace {

        static Map<String, Product> variantproductResultMap = null;

        public VariantProductDataLoader() {
        }

        @Override
        public List<TreeNode> getAllProductsTreeNodes() {
            variantproductResultMap = loadProducts();
            List<TreeNode> resultList = new ArrayList<TreeNode>();
            for (Map.Entry<String, Product> products : variantproductResultMap.entrySet()) {

                try {
                    resultList.add(getProductTreeNode(products.getKey()));
//                          parentNode.addNodes(new Key(productId, prodName), newProdNode);
                } catch (Exception ex) {
                    Logger.getLogger(ProductSingleton.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return resultList;
        }

        public Map<String, Product> loadProducts() {
            if (variantproductResultMap == null || variantproductResultMap.isEmpty()) {
                List<EntityCondition> entityCondList = new ArrayList<EntityCondition>();
                List<GenericValue> productList = LoadProductWorker.loadAllProducts(entityCondList, ControllerOptions.getSession().getDelegator());

//        List<GenericValue> productList = LoadProductWorker.loadAllProducts(entityCondList, ControllerOptions.getSession().getDelegator());
                //List<TreeNode> variantproductResultMap = new ArrayList<TreeNode>();
                variantproductResultMap = new HashMap<String, Product>();
                for (GenericValue productG : productList) {
                    if ("Y".equalsIgnoreCase(productG.getString("isVariant"))) {
                        String productId = productG.getString("productId");
                        try {
                            //
                            Product product = ProductSingleton.getProduct(productId);
                            variantproductResultMap.put(productId, product);
                            // do not consider discontinued product                                          
                        } catch (Exception ex) {
                            //Logger.getLogger(PosProductHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            //Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
            return variantproductResultMap;
        }

        @Override
        public List<Product> getAllProducts() {
            List<Product> list = new ArrayList<Product>();

            //Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
            return list;
        }
    }

    static public class VirtualProductDataLoader implements ProductDataLoaderInterace {

        static Map<String, Product> virtualproductResultMap = null;

        public VirtualProductDataLoader() {


        }

        @Override
        public List<TreeNode> getAllProductsTreeNodes() {
            
            virtualproductResultMap = loadProducts();
            
            List<TreeNode> resultList = new ArrayList<TreeNode>();
            for (Map.Entry<String, Product> products : virtualproductResultMap.entrySet()) {

                try {
                    resultList.add(getProductTreeNode(products.getKey()));
//                          parentNode.addNodes(new Key(productId, prodName), newProdNode);
                } catch (Exception ex) {
                    Logger.getLogger(ProductSingleton.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return resultList;
        }

        protected Map<String, Product> loadProducts() {
            if (virtualproductResultMap == null || virtualproductResultMap.isEmpty()) {
                List<EntityCondition> entityCondList = new ArrayList<EntityCondition>();
                List<GenericValue> productList = LoadProductWorker.loadAllProducts(entityCondList, ControllerOptions.getSession().getDelegator());

//        List<GenericValue> productList = LoadProductWorker.loadAllProducts(entityCondList, ControllerOptions.getSession().getDelegator());
                //List<TreeNode> virtualproductResultMap = new ArrayList<TreeNode>();
                virtualproductResultMap = new HashMap<String, Product>();
                for (GenericValue productG : productList) {
                    if ("Y".equalsIgnoreCase(productG.getString("isVirtual"))) {
                        String productId = productG.getString("productId");
                        try {
                            //
                            Product product = ProductSingleton.getProduct(productId);
                            virtualproductResultMap.put(productId, product);
                            // do not consider discontinued product                                          
                        } catch (Exception ex) {
                            //Logger.getLogger(PosProductHelper.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            //Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
            return virtualproductResultMap;
        }

        @Override
        public List<Product> getAllProducts() {
            List<Product> list = new ArrayList<Product>();

            //Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
            return list;
        }
    }
}
