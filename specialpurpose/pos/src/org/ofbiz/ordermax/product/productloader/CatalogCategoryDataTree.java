/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productloader;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import mvc.controller.LoadProductWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.DataKey;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.product.ProductCategoryTreeNode;
import org.ofbiz.ordermax.product.ProductSingleton;
import static org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader.module;

/**
 *
 * @author siranjeev
 */
public class CatalogCategoryDataTree extends ProductDataTreeLoader {

    //ProductCategory productCategory = null;
    public CatalogCategoryDataTree(ProductCategory productCategory) {
        super(productCategory);
        loadIcons();
    }

    public void loadIcons() {

        if (imageProduct != null) {
            ImageIcon icon = new ImageIcon(getImage(imageProduct));
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            //   ImageIcon icon = new ImageIcon(getImage(iconPathSmall));
            productIcon = new ImageIcon(image);
        }

        if (imageCatalog != null) {
            ImageIcon icon = new ImageIcon(getImage(imageCatalog));
            Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            //   ImageIcon icon = new ImageIcon(getImage(iconPathSmall));
            catalogIcon = new ImageIcon(image);
        }
    }

    @Override
    protected void buildProductTree() {

        try {
            rootTreeeNode.listNodes().clear();
            if (UtilValidate.isNotEmpty(categoryRoot)) {
                Debug.logInfo("buildProductTree id: " + categoryRoot + " categoryName: " + categoryRoot + "lazyLoad: " + lazyLoad, module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
                List<GenericValue> categoryList = PosProductHelper.getProductCategories(categoryRoot.getProductCategoryId(), ControllerOptions.getSession().getDelegator());

                //add all 
                for (GenericValue entry : categoryList) {

                    String id = entry.getString("productCategoryId");
                    String name = getDisplayName(entry);
                    String imageUrl = entry.getString("categoryImageUrl");

                    Debug.logInfo("buildProductTree id: " + id + "categoryName: " + name + "lazyLoad: " + lazyLoad, module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
//                productTreeMap.put(new Key(id, name), new HashMap<Key, List<Key>>());
                    ProductCategoryTreeNode newNode = new ProductCategoryTreeNode(id, name, false, entry);
                    newNode.setImagePath(imageUrl);
//            childNode.setIcon(catalogIcon);
                    if (UtilValidate.isNotEmpty(newNode.getImagePath())) {
                        newNode.setIcon(loadIcons(newNode.getImagePath()));
                    } else {
                        newNode.setIcon(catalogIcon);
                    }
                    /*    try {
                     throw new Exception("buildProductTree id: " + id + "categoryName: " + name + "lazyLoad: " + lazyLoad);
                     } catch (Exception e) {
                     Debug.logError(e, module);
                     } */
                    rootTreeeNode.addNodes(new Key(id, name), newNode);
                    if (!lazyLoad) {/*
                         loadTopTreeNodes(newNode);
                         } else {*/

                        recursivelyBuildTreeNodes(newNode);
                    }
                }

                loadAndAddProductNodes(categoryRoot.getProductCategoryId(), rootTreeeNode);

            }
        } finally {
            m_productLoaded = true;
        }
    }

    public static String getDisplayName(GenericValue value) {
        String id = value.getString("productCategoryId");
        String name = value.getString("categoryName");
        if (UtilValidate.isEmpty(name) || UtilValidate.isEmpty(name.trim())) {
            name = value.getString("description");
            if (UtilValidate.isEmpty(name)) {
                name = id;
            }
        }

//        Debug.logInfo("getDisplayName id: " + id + "categoryName: " + name, module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
        return name;
    }

    @Override
    public void reloadTreeNode(TreeNode newNode) {
        newNode.clearChildNodes();
        recursivelyBuildTreeNodes(newNode);
//      
//now load any child products
        String keyBrand = newNode._id;
        loadAndAddProductNodes(keyBrand, newNode);

    }

    public void recursivelyBuildTreeNodes(TreeNode newNode) {
        List<GenericValue> brandList = PosProductHelper.getProductCategories(newNode._id, ControllerOptions.getSession().getDelegator());
        newNode.setLoaded(true);
        for (GenericValue brand : brandList) {
            String keyBrand = brand.getString("productCategoryId");
//            Debug.logInfo("reloadTreeNode keyBrand:" + keyBrand, module);
            String name = getDisplayName(brand);
            String imageUrl = brand.getString("categoryImageUrl");
            ProductCategoryTreeNode childNode = new ProductCategoryTreeNode(keyBrand, name, false, brand);
            newNode.addNodes(new Key(keyBrand, name), childNode);
            childNode.setImagePath(imageUrl);
//            childNode.setIcon(catalogIcon);
            if (UtilValidate.isNotEmpty(childNode.getImagePath())) {
                childNode.setIcon(loadIcons(childNode.getImagePath()));
            } else {
                childNode.setIcon(catalogIcon);
            }
            if (!lazyLoad) {/*
                 loadTopTreeNodes(newNode);
                 } else {*/

                recursivelyBuildTreeNodes(childNode);
            }
//            recursivelyBuildTreeNodes(childNode);

            loadAndAddProductNodes(keyBrand, childNode);
        }
    }

    public void loadTopTreeNodes(TreeNode newNode) {

        List<GenericValue> brandList = PosProductHelper.getProductCategories(newNode._id, ControllerOptions.getSession().getDelegator());
        newNode.setLoaded(true);
        for (GenericValue brand : brandList) {
            String keyBrand = brand.getString("productCategoryId");
            String name = getDisplayName(brand);
            String imageUrl = brand.getString("categoryImageUrl");
            ProductCategoryTreeNode childNode = new ProductCategoryTreeNode(keyBrand, name, false, brand);
            newNode.addNodes(new Key(keyBrand, name), childNode);
            childNode.setImagePath(imageUrl);

            if (UtilValidate.isNotEmpty(childNode.getImagePath())) {
                childNode.setIcon(loadIcons(childNode.getImagePath()));
            } else {
                childNode.setIcon(catalogIcon);
            }
            loadAndAddProductNodes(keyBrand, childNode);
        }
    }

    @Override
    public List<TreeNode> getChildTreeNodes(TreeNode parentNode) {
        List<TreeNode> listArray = new ArrayList<TreeNode>();

        List<GenericValue> brandList = PosProductHelper.getProductCategories(parentNode._id, ControllerOptions.getSession().getDelegator());
        for (GenericValue brand : brandList) {
            String keyBrand = brand.getString("productCategoryId");
            String name = getDisplayName(brand);
            String imageUrl = brand.getString("categoryImageUrl");
//            Debug.logInfo("buildProductTree id: " + keyBrand + "categoryName: " + name + "imageUrl: " + imageUrl, module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                            
            ProductCategoryTreeNode childNode = new ProductCategoryTreeNode(keyBrand, name, false, brand);
            childNode.setImagePath(imageUrl);
//            childNode.setIcon(catalogIcon);
            if (UtilValidate.isNotEmpty(childNode.getImagePath())) {
                childNode.setIcon(loadIcons(childNode.getImagePath()));
            } else {
                childNode.setIcon(catalogIcon);
            }
            childNode.setHasChildrean(true);
            listArray.add(childNode);
            ///newNode.addNodes(new Key(keyBrand, name), childNode);
//            recursivelyBuildTreeNodes(childNode);            
        }

        List<GenericValue> productsList = PosProductHelper.getProductCategoryMembersOnly(parentNode._id, ControllerOptions.getSession().getDispatcher());

        for (GenericValue entry : productsList) {
            String productId = entry.getString("productId");
            try {
                Product product = ProductSingleton.getProduct(productId);
                if ("Y".equals(product.getisVirtual())) {
                    //final ProductDataLoaderInterace virtualLoader = BaseHelper.getVirtualProductArray(ControllerOptions.getSession());
                    ProductTreeNode newProdNode = ProductSingleton.getProductTreeNode(productId);
                    listArray.add(newProdNode);
                    //load variant list

                    //ArrayList<GenericValue> variantProductList = new ArrayList<GenericValue>();
                    //final ProductDataLoaderInterace variantLoader = BaseHelper.getVariantProductArray(ControllerOptions.getSession());
                    List<GenericValue> paList = EntityUtil.filterByDate(ControllerOptions.getSession().getDelegator().findByAnd("ProductAssoc", UtilMisc.toMap("productId", productId, "productAssocTypeId", "PRODUCT_VARIANT")));
                    for (GenericValue prodAssoc : paList) {
                        String productVarId = prodAssoc.getString("productIdTo");
                        Product varProduct = ProductSingleton.getProduct(productVarId);
                        productVarId = varProduct.getproductId();
                        String prodVarName = varProduct.getproductName();

                        ProductTreeNode newProdNodeVar = ProductSingleton.getProductTreeNode(productVarId);
                        newProdNode.addNodes(new Key(productVarId, prodVarName), newProdNodeVar);
                        //listArray.add(newProdNode);
                    }
                } else {
                    //final ProductDataLoaderInterace variantLoader = BaseHelper.getVariantProductArray(ControllerOptions.getSession());
                    ProductTreeNode newProdNode = ProductSingleton.getProductTreeNode(productId);
                    listArray.add(newProdNode);
                }

            } catch (Exception ex) {
                Logger.getLogger(CatalogCategoryDataTree.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        /*
         //now load any child products
         Debug.logInfo("buildProductTree load child products id: " + parentNode._id, module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                                    
         List<GenericValue> productList = loadProductList(parentNode._id);
         for (GenericValue productGen : productList) {

         String productId = productGen.getString("productId");
         String nameProd = productGen.getString("productName");
         String imageUrl = productGen.getString("smallImageUrl");

         Debug.logInfo("buildProductTree id: " + productId + " prodName: " + nameProd + "imageUrl: " + imageUrl, module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                            
         ProductTreeNode newProdNode = new ProductTreeNode(productId, nameProd, true, productGen);
         newProdNode.setVirtual("Y".equalsIgnoreCase(productGen.getString("isVirtual")));
         newProdNode.setVariant("Y".equalsIgnoreCase(productGen.getString("isVariant")));
         newProdNode.setHasChildrean(false);
         newProdNode.setImagePath(imageUrl);
         if (UtilValidate.isNotEmpty(newProdNode.getImagePath())) {
         newProdNode.setIcon(loadIcons(newProdNode.getImagePath()));
         } else {
         newProdNode.setIcon(productIcon);
         }

         listArray.add(newProdNode);

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
         }*/

        return listArray;
    }

    @Override
    public List<GenericValue> loadProductList(String categoryId) {

        List<GenericValue> productsList = new ArrayList<GenericValue>();
        try {
//            // //////Debug.logInfo("brandId: " + brandid, module);

            productsList = PosProductHelper.getProductCategoryMembersOnly(categoryId, ControllerOptions.getSession().getDispatcher());

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
}
