package org.ofbiz.ordermax.product.productloader;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javolution.util.FastMap;
import mvc.controller.LoadProductWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.BrandTreeNode;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DataKey;
import org.ofbiz.ordermax.base.DatePicker;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.product.ProductTreeNode;

public class ProductDataTreeLoader {

    public static final String module = ProductDataTreeLoader.class.getName();
    protected ProductCategory categoryRoot = null;// "MAX_PRODUCT_ALL";    
    public ProdCatalog prodCatalog = new ProdCatalog();// "MAX_PRODUCT_ALL";   
    public ImageIcon productIcon = null;
    public ImageIcon catalogIcon = null;

    public String imageProduct = "productIconsmall.png";
    public String imageCatalog = "folder_open.jpg";

//    public static String CategoryRootIdName = "Product All";    
    //public static String CatalogId = null;// "MAX_CATALOG";    
    // final public static String CategoryPromotionId = "MAX_CAT_PROMOTIONS";
    //final public static String BaseImagePath = "C:/ordermax/apache-ofbiz-12.04.04/apache-ofbiz-12.04.04/themes/osafe_theme/webapp";
    final public static String RootPath = "C:/backup/ofbiz-12.04.02";
    //public static String BaseImagePath = "C:/backup/ofbiz-12.04.02/themes/osafe_theme/webapp";
    //C:/ofbiz_web/MAX SPICE/Max_Spice/themes/grocery_theme/webapp/grocery_theme/images/catalog/grocery/PDP
    public static String BaseImagePath = "C:/ofbiz_web/MAX SPICE/Max_Spice/themes/grocery_theme/webapp/";
    public static String InvoicePdfFilePath = "C:\\pdfCopy\\Sorted\\";//C:\\ofbiz\\ofbiz-12.04.02\\applications\\content\\data\\";

    final public static String CategoryImagePath = BaseImagePath; //"C:/ordermax/apache-ofbiz-12.04.04/apache-ofbiz-12.04.04/themes/osafe_theme/webapp";

    protected boolean lazyLoad = true;

    public boolean isLazyLoad() {
        return lazyLoad;
    }

    public void setLazyLoad(boolean lazyLoad) {
        this.lazyLoad = lazyLoad;
    }
    protected TreeNode rootTreeeNode = null;
//    protected HashMap<String, Key> productIdMap = new HashMap<String, Key>();
    protected String m_productIdSelected = null;
    protected boolean m_productLoaded = false;
    // protected XuiSession session = null;
    public static String AllString = "All";
//    public ProductCategory categoryId = categoryRootId;

    public boolean isProductLoaded() {
        return m_productLoaded;
    }

    public void setProductLoaded(boolean productLoaded) {
        m_productLoaded = productLoaded;
    }

    public String getProductIdSelected() {
        return m_productIdSelected;
    }

    public void setProductIdSelected(String productId) {
        m_productIdSelected = productId;
    }
//    ProgressReporter progressReporter = null;

    public ProductDataTreeLoader() {
//        this.session = page;
        this.categoryRoot = ControllerOptions.getSession().getProductCategory();
        rootTreeeNode = new ProductRootTreeNode(categoryRoot.getProductCategoryId(), categoryRoot.getDescription(), false, categoryRoot.getGenericValueObj());

    }

    public ProductDataTreeLoader(ProductCategory categoryId) {
//        this.session = page;
        this.categoryRoot = categoryId;
        rootTreeeNode = new ProductRootTreeNode(categoryRoot.getProductCategoryId(), categoryRoot.getDescription(), false, categoryId.getGenericValueObj());
        rootTreeeNode.setHasChildrean(true);
    }

    public TreeNode getProductFromId(Key key) {
        return rootTreeeNode.getNodeFromId(key);
    }

    public void loadList() {

        //if product is not loaded or force load 
        if (m_productLoaded == false) {
            buildProductTree();
            m_productLoaded = true;
        }
    }

    public ArrayList<Key> getList(String searchStr, String brandId) {
//     	// //////Debug.logInfo("new search str: "+searchStr , module);

        ArrayList<Key> productsArray = new ArrayList<Key>();
        ArrayList<Key> tempProdList = new ArrayList<Key>();

        if (brandId != null && brandId.isEmpty() == false) {
            List<GenericValue> productVariantList = loadProductList(brandId);
//            List<GenericValue> productVariantList = loadProductVariantList(productId);
            for (GenericValue productVarGen : productVariantList) {
                String productVarId = productVarGen.getString("productId");
                String prodVarName = productVarGen.getString("productName");
                String smallImageUrlVar = productVarGen.getString("smallImageUrl");
                DataKey productVar = new DataKey(productVarId, prodVarName, smallImageUrlVar);
                tempProdList.add(productVar);
            }
        } else {
            //not loaded then try to load
            if (m_productLoaded == false) {
                loadList();
            }
            tempProdList = getAllProducts();
        }

        if (searchStr == null || searchStr.isEmpty()) {
            productsArray.addAll(tempProdList);
        } else {
            Iterator<Key> it = tempProdList.iterator();
            while (it.hasNext()) {
                Key obj = it.next();
                String val = obj._name;
                String brandName = obj._name;
                if (searchStr == null || searchStr.isEmpty()) {
                    productsArray.add(obj);
                } else if (val.toUpperCase().contains(searchStr.toUpperCase())) {
                    productsArray.add(obj);
                }
            }
        }

        return productsArray;
    }

    public void reloadTreeNode(TreeNode newNode) throws Exception {
        throw new Exception("reloadTreeNode not implemented");
    }

    public List<GenericValue> loadProductList(String categoryId) {
        List<GenericValue> productsList = new ArrayList<GenericValue>();
        try {
//            // //////Debug.logInfo("brandId: " + brandid, module);

            productsList = PosProductHelper.getProductCategoryMembers(categoryId, ControllerOptions.getSession().getDispatcher());

            for (GenericValue entry : productsList) {
                String productId = entry.getString("productId");
//                String name = entry.getString("productName");
//                Debug.logInfo("loadProductList newNode._id:" + key, module);
//                setProgress(name);
                String smallImageUrl = entry.getString("smallImageUrl");
                if (UtilValidate.isEmpty(smallImageUrl)) {
                    ProductContentComposite comp = LoadProductWorker.loadProductContent(productId, "SMALL_IMAGE_URL", ControllerOptions.getSession());
                    //ProductContentComposite comp = list.getProductContent("SMALL_IMAGE_URL");
                    if (comp != null) {
                        smallImageUrl = comp.getDataResource().getobjectInfo();
                    }
                }
                entry.setString("smallImageUrl", smallImageUrl);

//                brandProductList.add(new DataKey(key, /*brandName + " " +*/ name, smallImageUrl));
            }
        } catch (Exception e1) {
            Debug.logError(e1, module);
        }

        return productsList;
    }

    public ArrayList<GenericValue> loadProductVariantList(String productId) {
        ArrayList<GenericValue> variantProductList = new ArrayList<GenericValue>();
        try {

            List<GenericValue> paList = EntityUtil.filterByDate(ControllerOptions.getSession().getDelegator().findByAnd("ProductAssoc", UtilMisc.toMap("productId", productId, "productAssocTypeId", "PRODUCT_VARIANT")));
            for (GenericValue prodAssoc : paList) {
                String key = prodAssoc.getString("productIdTo");

                GenericValue entry = PosProductHelper.getProduct(key, ControllerOptions.getSession().getDelegator());
//                String key = entry.getString("productId");
                String name = entry.getString("productName");
                Debug.logInfo("loadProductList newNode._id:" + key, module);
//                setProgress(name);
                String smallImageUrl = entry.getString("smallImageUrl");
                if (UtilValidate.isEmpty(smallImageUrl)) {
                    ProductContentComposite comp = LoadProductWorker.loadProductContent(key, "SMALL_IMAGE_URL", ControllerOptions.getSession());
                    //ProductContentComposite comp = list.getProductContent("SMALL_IMAGE_URL");
                    if (comp != null) {
                        smallImageUrl = comp.getDataResource().getobjectInfo();
                    }
                }
                entry.setString("smallImageUrl", smallImageUrl);
                variantProductList.add(entry);
                ////////Debug.logInfo("name: " + name + " key: " + key + "smallImageUrl: " + smallImageUrl, module);
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductDataTreeLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return variantProductList;
    }

    public ArrayList<TreeNode> loadProductCategoryMemebers(Key brandKey) {
        ArrayList<TreeNode> brandProductList = new ArrayList<TreeNode>();
        try {
            // //////Debug.logInfo("brandId: " + brandKey._id, module);

            List<GenericValue> productsList = PosProductHelper.getProductCategoryMembers(brandKey._id, ControllerOptions.getSession().getDispatcher());

            for (GenericValue productVarGen : productsList) {
                /*String key = entry.getString("productId");
                 String name = entry.getString("internalName");
                 String brandName = entry.getString("brandName");
                 brandProductList.add(new Key(key, brandName + " " + name));
                 */
                String productVarId = productVarGen.getString("productId");
                String prodVarName = productVarGen.getString("productName");
                String smallImageUrlVar = productVarGen.getString("smallImageUrl");
                DataKey productVar = new DataKey(productVarId, prodVarName, smallImageUrlVar);

                String brandName = productVarGen.getString("brandName");
                String nameProdVar = prodVarName;
                if (brandName != null) {
                    nameProdVar = brandName + " " + prodVarName;
                }

                ProductTreeNode newProdNodeVar = new ProductTreeNode(productVarId, nameProdVar, true, productVarGen);
                newProdNodeVar.setVariant("Y".equalsIgnoreCase(productVarGen.getString("isVariant")));
                newProdNodeVar.setImagePath(((DataKey) productVar).getData());
                brandProductList.add(newProdNodeVar);
                //newProdNode.addNodes(new Key(keyProdVar, nameProdVar), newProdNodeVar);

            }
        } catch (Exception e1) {
            Debug.logError(e1, module);
        }

        return brandProductList;
    }

    public ArrayList<Key> getListAll() {

        //not loaded then try to load
        if (m_productLoaded == false) {
            loadList();
        }

        return getAllProducts();
    }

    protected void buildProductTree() {

        try {
            rootTreeeNode.listNodes().clear();
            Debug.logError("buildProductTree categoryId: " + categoryRoot.getProductCategoryId(), module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            List<GenericValue> categoryList = PosProductHelper.getProductCategories(categoryRoot.getProductCategoryId(), ControllerOptions.getSession().getDelegator());
            //////Debug.logInfo("categoryList size: " + categoryList.size(), module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            //add all 
            for (GenericValue entry : categoryList) {

                String id = entry.getString("productCategoryId");
                String name = entry.getString("categoryName");
//                productTreeMap.put(new Key(id, name), new HashMap<Key, List<Key>>());
                DepartmentTreeNode newNode = new DepartmentTreeNode(id, name, false, entry);
                rootTreeeNode.addNodes(new Key(id, name), newNode);
                addProductTreeNodes(newNode);
            }

//            productIdMap.clear();
            ArrayList<Key> productList = getAllProducts();
//            // //////Debug.logInfo(" Department[" + departmentId + "," + departmentName + "]" + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
//            for (Key product : productList) {
//                productIdMap.put(product._id, product);
            //////Debug.logInfo(" productIdMap[" + product._id + "," + product._name + "]", "module");
//            }
        } finally {
            m_productLoaded = true;
        }
    }
    /*
     public Key getProductFromId(String id) throws Exception {
     // //////Debug.logInfo("getProductFromId Product [" + id, "module");
     if (productIdMap.containsKey(id)) {
     return productIdMap.get(id);
     }
     throw new Exception("Product not found Id: " + id);
     }
     */
    Map<String, GenericValue> productMap = FastMap.newInstance();

    void addProductTreeNodes(TreeNode newNode) {
        List<GenericValue> brandList = PosProductHelper.getProductCategories(newNode._id, ControllerOptions.getSession().getDelegator());
        for (GenericValue brand : brandList) {
            String keyBrand = brand.getString("productCategoryId");
            String name = brand.getString("categoryName");
            BrandTreeNode childNode = new BrandTreeNode(keyBrand, name, false, brand);
            newNode.addNodes(new Key(keyBrand, name), childNode);
            addProductTreeNodes(childNode);
            List<GenericValue> productList = loadProductList(keyBrand);
            for (GenericValue productGen : productList) {
                String productId = productGen.getString("productId");
                if (!productMap.containsKey(productId)) {
                    productMap.put(productId, productGen);
                    String prodName = productGen.getString("productName");
                    String smallImageUrl = productGen.getString("smallImageUrl");
                    DataKey product = new DataKey(productId, prodName, smallImageUrl);
                    String keyProd = product._id;
                    String nameProd = product._name;

                    ProductTreeNode newProdNode = new ProductTreeNode(keyProd, nameProd, true, productGen);
                    newProdNode.setImagePath(((DataKey) product).getData());
                    childNode.addNodes(new Key(keyProd, nameProd), newProdNode);

                    if ("Y".equalsIgnoreCase(productGen.getString("isVirtual"))) {
                        //newProdNode.setVirtual(true);
                        List<GenericValue> productVariantList = loadProductVariantList(productId);
                        for (GenericValue productVarGen : productVariantList) {
                            String productVarId = productVarGen.getString("productId");
                            if (!productMap.containsKey(productVarId)) {
                                productMap.put(productVarId, productVarGen);

                                String prodVarName = productVarGen.getString("productName");
                                String smallImageUrlVar = productVarGen.getString("smallImageUrl");
                                DataKey productVar = new DataKey(productVarId, prodVarName, smallImageUrlVar);
                                String keyProdVar = product._id;
                                String nameProdVar = product._name;

                                ProductTreeNode newProdNodeVar = new ProductTreeNode(keyProdVar, nameProdVar, true, productVarGen);
                                newProdNodeVar.setVariant("Y".equalsIgnoreCase(productVarGen.getString("isVariant")));
                                newProdNodeVar.setImagePath(((DataKey) productVar).getData());
                                newProdNode.addNodes(new Key(keyProdVar, nameProdVar), newProdNodeVar);
                            }
                        }
                    }
                }
                //Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
            }
        }
    }

    public void addProductTreeNode(TreeNode parentNode, TreeNode product) {

        ProductTreeNode newProdNode = new ProductTreeNode(product._id, product._name, true, product.getGenericValue());
        if (UtilValidate.isNotEmpty(newProdNode.getImagePath())) {
            newProdNode.setIcon(loadIcons(newProdNode.getImagePath()));
        } else {
            newProdNode.setIcon(productIcon);
        }
        parentNode.addNodes(new Key(product._id, product._name), newProdNode);

//        productIdMap.put(product._id, newProdNode);
    }

    public ImageIcon loadIcons(String fileName) {
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

    public void printProductCache() {
        /*        // //////Debug.logInfo(" printProductCache ", module);
         for (Entry<Key, HashMap<Key, List<Key>>> entryDept : productTreeMap.entrySet()) {
         String departId = entryDept.getKey()._id;
         String departName = entryDept.getKey()._name;

         for (Entry<Key, List<Key>> entryBrand : entryDept.getValue().entrySet()) {
         String brandId = entryBrand.getKey()._id;
         String brandName = entryBrand.getKey()._name;

         for (Key product : entryBrand.getValue()) {
         String keyProd = product._id;
         String nameProd = product._name;

         // //////Debug.logInfo(" Department[" + departId + "," + departName + "]" + " Brand(" + brandId + "," + brandName + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
         }

         }
         }
         */
    }

    public HashMap<String, Key> getAllTopCategories() {

        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        HashMap<String, Key> topCategories = new HashMap<String, Key>();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            String keyProd = entryBrand.getValue()._id;
            String nameProd = entryBrand.getValue()._name;
            topCategories.put(keyProd, entryBrand.getValue());
        }
        return topCategories;
    }

    public HashMap<String, TreeNode> getAllTopCategoryTreeNodes() {

        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        HashMap<String, TreeNode> topCategories = new HashMap<String, TreeNode>();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            topCategories.put(entryBrand.getValue()._id, entryBrand.getValue());
        }
        return topCategories;
    }

    void getProductKeys(TreeNode parentNode, ArrayList<Key> listItems, boolean productOnly) {

        //                // //////Debug.logInfo(" getProductTreeNodes node [" + parentNode._id + "," + parentNode._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
        TreeMap<Key, TreeNode> list = parentNode.listNodes();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            TreeNode prodNode = entryBrand.getValue();
            String keyProd = prodNode._id;
            String nameProd = prodNode._name;
            //          // //////Debug.logInfo(" getProductTreeNodes node [" + entryBrand.getValue()._id + "," + entryBrand.getValue()._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            if (productOnly == true) {
                if (prodNode.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    listItems.add(prodNode);
//                     // //////Debug.logInfo(" product node true", "true");
                }
            } else {
                if (prodNode.getNodeName().equals(ProductTreeNode.ProdutNodeName) == false) {
                    listItems.add(prodNode);
                }
            }
            getProductKeys(prodNode, listItems, productOnly);
        }
    }

    void getNonVirtualProductKeys(TreeNode parentNode, ArrayList<Key> listItems) {

        //                // //////Debug.logInfo(" getProductTreeNodes node [" + parentNode._id + "," + parentNode._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
        TreeMap<Key, TreeNode> list = parentNode.listNodes();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            TreeNode prodNode = entryBrand.getValue();

            //          // //////Debug.logInfo(" getProductTreeNodes node [" + entryBrand.getValue()._id + "," + entryBrand.getValue()._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            if (prodNode.getNodeName().equals(ProductTreeNode.ProdutNodeName)/* && !((ProductTreeNode) prodNode).isVirtual()*/) {
                if ("Y".equals(prodNode.getGenericValue().getString("isVirtual")) == false) {
                    listItems.add(prodNode);
                }
//                     // //////Debug.logInfo(" product node true", "true");
            }
            getNonVirtualProductKeys(prodNode, listItems);
        }
    }

    void getProductTreeNodes(TreeNode parentNode, ArrayList<TreeNode> listItems, boolean productOnly) {

        //                // //////Debug.logInfo(" getProductTreeNodes node [" + parentNode._id + "," + parentNode._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
        TreeMap<Key, TreeNode> list = parentNode.listNodes();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            TreeNode prodNode = entryBrand.getValue();
            String keyProd = prodNode._id;
            String nameProd = prodNode._name;
            //          // //////Debug.logInfo(" getProductTreeNodes node [" + entryBrand.getValue()._id + "," + entryBrand.getValue()._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            if (productOnly == true) {
                if (prodNode.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
                    listItems.add(prodNode);
//                     // //////Debug.logInfo(" product node true", "true");
                }
            } else {
                if (prodNode.getNodeName().equals(ProductTreeNode.ProdutNodeName) == false) {
                    listItems.add(prodNode);
                }
            }
            getProductTreeNodes(prodNode, listItems, productOnly);
        }
    }

    protected TreeNode findProductTreeNodes(TreeNode parentNode, Key key) {
//        TreeNode treeNode = null;
        TreeMap<Key, TreeNode> list = parentNode.listNodes();
        TreeNode treeNode = list.get(key);
        if (treeNode == null) {
            for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
                if (entryBrand.getValue().isHasChildrean()) {
                    treeNode = findProductTreeNodes(entryBrand.getValue(), key);
                }
                if (treeNode != null) {
                    break;
                }
            }
        }

        return treeNode;
    }

    public TreeNode findProductTreeNodes(TreeNode parentNode, String id, String name) {
//        TreeNode treeNode = null;
        TreeMap<Key, TreeNode> list = parentNode.listNodes();

        //search the child
        TreeNode treeNode = null;
        for (Entry<Key, TreeNode> entryKey : list.entrySet()) {
            if (entryKey.getKey()._id.equals(id) && entryKey.getKey()._name.equals(name)) {
                treeNode = entryKey.getValue();
                break;
            }
        }

        if (treeNode == null) {
            for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
                if (entryBrand.getValue().isHasChildrean()) {
                    treeNode = findProductTreeNodes(entryBrand.getValue(), id, name);
                }
                if (treeNode != null) {
                    break;
                }
            }
        }

        return treeNode;
    }

    public TreeNode findParentTreeNode(TreeNode parentNode, Key key) {
//        TreeNode treeNode = null;
        TreeMap<Key, TreeNode> list = parentNode.listNodes();
        TreeNode treeNode = list.get(key);
        if (treeNode == null) {
            for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
                if (entryBrand.getValue().isHasChildrean()) {
                    treeNode = findProductTreeNodes(entryBrand.getValue(), key);
                }
                if (treeNode != null) {
                    return parentNode;
                }
            }
        } else {
            treeNode = parentNode;
        }

        return treeNode;
    }

    public TreeNode findTreeNode(TreeNode parentNode, Key key) {
//        TreeNode treeNode = null;

        if (parentNode._key.equals(key._key)) {
            return parentNode;
        } else {
            TreeMap<Key, TreeNode> list = parentNode.listNodes();

            for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
                if (entryBrand.getValue().isHasChildrean()) {
                    TreeNode val = findProductTreeNodes(entryBrand.getValue(), key);
                    if (val != null) {
                        return val;
                    }
                }
            }
        }

        return null;
    }

    public ArrayList<Key> getAllProducts() {

        ArrayList<Key> productList = new ArrayList<Key>();
        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            TreeNode product = entryBrand.getValue();
            getProductKeys(product, productList, true);
        }
        return productList;
    }

    public List<TreeNode> getAllProductsTreeNodes() {

        ArrayList<TreeNode> productList = new ArrayList<TreeNode>();
        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            TreeNode product = entryBrand.getValue();
            getProductTreeNodes(product, productList, true);
        }
        return productList;
    }

    public ArrayList<Key> getAllBrands() {

        ArrayList<Key> brandList = new ArrayList<Key>();
        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {
            TreeNode product = entryBrand.getValue();
            getProductKeys(product, brandList, false);
        }

        return brandList;

    }

    public ArrayList<Key> getAllDepartmentsList() {

        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        ArrayList<Key> deptList = new ArrayList<Key>();
        for (Entry<Key, TreeNode> entryBrand : list.entrySet()) {

            deptList.add(entryBrand.getValue());
        }
        return deptList;
    }

    public HashMap<String, String> getBrandIdMap(final String departmentId, final String departmentName) {
        HashMap<String, String> brandMap = new HashMap<String, String>();
        ArrayList<Key> brandList = new ArrayList<Key>();
        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        TreeNode department = list.get(new Key(departmentId, departmentName));
        //////Debug.logInfo(" getBrandIdMap[" + departmentId + "," + departmentName + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
        if (department != null) {
            //////Debug.logInfo(" getBrandIdMap[" + department._id + "," + department._name + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
            getProductKeys(department, brandList, false);

        }

        for (Key entry : brandList) {
            brandMap.put(entry._id, entry._name);
        }

        return brandMap;
    }

    public ArrayList<Key> getBrandIds(Key deptKey) {

        ArrayList<Key> brandList = new ArrayList<Key>();
        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        TreeNode department = list.get(new Key(deptKey._id, deptKey._name));
        // //////Debug.logInfo(" getBrandIdMap[" + deptKey._id + "," + deptKey._name + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
        if (department != null) {
            // //////Debug.logInfo(" getBrandIdMap[" + department._id + "," + department._name + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
            getProductKeys(department, brandList, false);

        }

        return brandList;
    }

    public ArrayList<Key> getProductIds(Key key) {

        ArrayList<Key> brandList = new ArrayList<Key>();
        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        TreeNode newKey = findProductTreeNodes(rootTreeeNode, key._id, key._name);
//        TreeNode newKey = list.get(new Key(key._id, key._name));
        // //////Debug.logInfo(" getProductIds[" + key._id + "," + key._name + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
        if (newKey != null) {
            // //////Debug.logInfo(" getProductIds[" + newKey._id + "," + newKey._name + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
            getProductKeys(newKey, brandList, true);
        }
        return brandList;
    }

    public ArrayList<Key> getNonVirtualProductIds(Key key) {

        ArrayList<Key> brandList = new ArrayList<Key>();
//        TreeMap<Key, TreeNode> list = rootTreeeNode.listNodes();
        TreeNode newKey = findProductTreeNodes(rootTreeeNode, key._id, key._name);
//        TreeNode newKey = list.get(new Key(key._id, key._name));
        // //////Debug.logInfo(" getProductIds[" + key._id + "," + key._name + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
        if (newKey != null) {
            // //////Debug.logInfo(" getProductIds[" + newKey._id + "," + newKey._name + "]", module);// + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
            getNonVirtualProductKeys(newKey, brandList);
        }
        return brandList;
    }

    public HashMap<String, String> getProductMap(String departmentId, String departmentName, String brandId, String brandName) {
        HashMap<String, String> productMap = new HashMap<String, String>();

        if (brandId.equals(AllString) == true && departmentId.equals(AllString) == true) {
            ArrayList<Key> productList = getAllProducts();
//            // //////Debug.logInfo(" Department[" + departmentId + "," + departmentName + "]" + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            for (Key product : productList) {
                productMap.put(product._id, product._name);
            }

        } else if (brandId.equals(AllString) == true && departmentId.equals(AllString) != true) {
//            // //////Debug.logInfo(" Department[" + departmentId + "," + departmentName + "]" + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            TreeNode node = findProductTreeNodes(rootTreeeNode, new Key(departmentId, departmentName));

//            HashMap<String, String> brandIdMap = getBrandIdMap(departmentId, departmentName);
//			HashMap<Key, List<Key>> brandMap = productTreeMap.get(new Key(departId, departName ));
            if (node != null) {
//                // //////Debug.logInfo(" found node Department [" + node._id + "," + node._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
                ArrayList<Key> productList = new ArrayList<Key>();
                getProductKeys(node, productList, true);
                for (Key product : productList) {
                    productMap.put(product._id, product._name);
                }
            }
        } else {
//            // //////Debug.logInfo(" Department[" + departmentId + "," + departmentName + "]" + " Brand(" + brandId + "," + brandName + ")",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            TreeNode nodeDepartment = findProductTreeNodes(rootTreeeNode, new Key(departmentId, departmentName));
            if (nodeDepartment != null) {
//                // //////Debug.logInfo(" found node Department [" + nodeDepartment._id + "," + nodeDepartment._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
                TreeNode nodeBrand = findProductTreeNodes(nodeDepartment, new Key(brandId, brandName));
                if (nodeBrand != null) {
//                    // //////Debug.logInfo(" found node Brand [" + nodeBrand._id + "," + nodeBrand._name + "]",  module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
                    ArrayList<Key> productList = new ArrayList<Key>();
                    getProductKeys(nodeBrand, productList, true);
                    for (Key product : productList) {
                        productMap.put(product._id, product._name);
                    }
                }
            }
        }
        return productMap;
    }
    /*
     public boolean isBrandExists(String departmentId, String departmentName, String brandId, String brandName){
     boolean result = false;
     return result;
     }

     public boolean getDepartment(Key deptKey){
     boolean result = false;
     TreeNode val = findProductTreeNodes(rootTreeeNode, deptKey);
     if(val!=null){
     result = true
     }
     return result;
     }*/

    public TreeNode getRootNode() {
        return rootTreeeNode;
    }

    public List<TreeNode> getChildTreeNodes(TreeNode parentNode) {
        List<TreeNode> listArray = new ArrayList<TreeNode>();
        return listArray;
    }

    protected byte[] getImage(String fileName) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(DatePicker.class.getClassLoader().getResourceAsStream(fileName));
            byte[] b = new byte[is.available()];
            is.read(b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public void loadAndAddProductNodes(String catId, TreeNode parentNode) {
        List<GenericValue> productList = loadProductList(catId);
        /*for (GenericValue productGen : productList) {
         String productId = productGen.getString("productId");
         String prodName = productGen.getString("productName");
         String smallImageUrl = productGen.getString("smallImageUrl");
         DataKey product = new DataKey(productId, prodName, smallImageUrl);
         //        String keyProd = product._id;
         //        String nameProd = product._name;

         ProductTreeNode newProdNode = new ProductTreeNode(productId, prodName, true, productGen);
         newProdNode.setVirtual("Y".equalsIgnoreCase(productGen.getString("isVirtual")));
         newProdNode.setVariant("Y".equalsIgnoreCase(productGen.getString("isVariant")));
         newProdNode.setImagePath(((DataKey) product).getData());
         if (UtilValidate.isNotEmpty(newProdNode.getImagePath())) {
         newProdNode.setIcon(loadIcons(newProdNode.getImagePath()));
         } else {
         newProdNode.setIcon(productIcon);
         }
         parentNode.addNodes(new Key(productId, prodName), newProdNode);

         Debug.logInfo("isVirtual: " + productGen.getString("isVirtual"), module);
         if ("Y".equalsIgnoreCase(productGen.getString("isVirtual"))) {
         //newProdNode.setVirtual(true);
         List<GenericValue> productVariantList = loadProductVariantList(productId);
         for (GenericValue productVarGen : productVariantList) {
         String productVarId = productVarGen.getString("productId");
         String prodVarName = productVarGen.getString("productName");
         String smallImageUrlVar = productVarGen.getString("smallImageUrl");
         DataKey productVar = new DataKey(productVarId, prodVarName, smallImageUrlVar);
         //String keyProdVar = product._id;
         //String nameProdVar = product._name;
         Debug.logInfo("virtual productVarId: " + productVarId, module);
         ProductTreeNode newProdNodeVar = new ProductTreeNode(productVarId, prodVarName, true, productVarGen);
         newProdNodeVar.setVariant("Y".equalsIgnoreCase(productVarGen.getString("isVariant")));
         newProdNodeVar.setImagePath(((DataKey) productVar).getData());
         if (UtilValidate.isNotEmpty(newProdNodeVar.getImagePath())) {
         newProdNodeVar.setIcon(loadIcons(newProdNodeVar.getImagePath()));
         } else {
         newProdNodeVar.setIcon(productIcon);
         }
         newProdNode.addNodes(new Key(productVarId, prodVarName), newProdNodeVar);
         }
         }
         //Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
         }*/
    }

    public void recursivelyBuildTreeNodes(TreeNode newNode) {

    }

    public ProductCategory getCategoryRoot() {
        return categoryRoot;
    }

    public void setCategoryRoot(ProductCategory categoryRoot) {
        this.categoryRoot = categoryRoot;
    }

    public ArrayList<Key> getProductCategoryPath(Key brandKey) {
        ArrayList<Key> brandProductList = new ArrayList<Key>();
        try {
            // //////Debug.logInfo("brandId: " + brandKey._id, module);

            List<GenericValue> productsList = PosProductHelper.getProductCategoryMembers(brandKey._id, ControllerOptions.getSession().getDispatcher());

            for (GenericValue entry : productsList) {
                String key = entry.getString("productId");
                String name = entry.getString("internalName");
                String brandName = entry.getString("brandName");
                brandProductList.add(new Key(key, brandName + " " + name));
            }
        } catch (Exception e1) {
            Debug.logError(e1, module);
        }

        return brandProductList;
    }

    static public void getProductCategoryPath(String id, List<GenericValue> values) {
        List<GenericValue> catValues = PosProductHelper.getParentProductCategories(id, ControllerOptions.getSession().getDelegator());
        for (GenericValue catValue : catValues) {
            values.add(0, catValue);
            getProductCategoryPath(catValue.getString("primaryParentCategoryId"), values);
        }
    }
}
