/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productloader;

import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.BrandTreeNode;
import org.ofbiz.ordermax.base.DataKey;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.product.ProductCategoryTreeNode;
import static org.ofbiz.party.party.PartyServices.module;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryDataTree extends CatalogCategoryDataTree {

    public ProductCategoryDataTree(ProductCategory productCategory) {
        super(productCategory);
    }

    @Override
    protected void buildProductTree() {

        try {
            rootTreeeNode.listNodes().clear();
            Debug.logInfo("buildProductTree ", module);//+ " Product{" + keyProd + "," + nameProd + "}", module);
            List<GenericValue> categoryList = PosProductHelper.getProductCategories(null, ControllerOptions.getSession().getDelegator());

            //add all 
            for (GenericValue entry : categoryList) {

                String id = entry.getString("productCategoryId");
                String name = getDisplayName(entry);
                String imageUrl = entry.getString("categoryImageUrl");

//                Debug.logInfo("buildProductTree id: " + id + "categoryName: " + name + "lazyLoad: " + lazyLoad, module);//+ " Product{" + keyProd + "," + nameProd + "}", module);                
//                productTreeMap.put(new Key(id, name), new HashMap<Key, List<Key>>());
                ProductCategoryTreeNode newNode = new ProductCategoryTreeNode(id, name, false, entry);
                newNode.setImagePath(imageUrl);
//            childNode.setIcon(catalogIcon);
                if (UtilValidate.isNotEmpty(newNode.getImagePath())) {
                    newNode.setIcon(loadIcons(newNode.getImagePath()));
                } else {
                    newNode.setIcon(catalogIcon);
                }
                rootTreeeNode.addNodes(new Key(id, name), newNode);
                if (lazyLoad) {
                    loadTopTreeNodes(newNode);
                } else {
                    recursivelyBuildTreeNodes(newNode);
                }
            }

        } finally {
            m_productLoaded = true;
        }
    }

    /*    void recursivelyBuildTreeNodes(TreeNode newNode) {
     List<GenericValue> brandList = PosProductHelper.getProductCategories(newNode._id, session.getDelegator());
     for (GenericValue brand : brandList) {
     String keyBrand = brand.getString("productCategoryId");
     String name = brand.getString("categoryName");
     ProductCategoryTreeNode childNode = new ProductCategoryTreeNode(keyBrand, name, false);
     newNode.addNodes(new Key(keyBrand, name), childNode);
     recursivelyBuildTreeNodes(childNode);

     //now load any child products
     List<Key> productList = loadProductList(keyBrand);
     for (Key product : productList) {
     String keyProd = product._id;
     String nameProd = product._name;

     ProductTreeNode newProdNode = new ProductTreeNode(keyProd, nameProd, true);
     newProdNode.setImagePath(((DataKey) product).getData());
     childNode.addNodes(new Key(keyProd, nameProd), newProdNode);
     //                    // Debug.logInfo(" Department[" + newNode._id + "," + newNode._name + "]" + " Brand(" + keyBrand + "," + name + ")" + " Product{" + keyProd + "," + nameProd + "}", module);
     }
     }
     }
     */
}
