/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import java.util.ArrayList;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductAssoc;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryMember;

/**
 *
 * @author siranjeev
 */
public class ProductComposite {
    Product product = null;
    GoodIdentificationList goodIdentificationList;
    SupplierProductList supplierProductList;
    //ProductPriceList productPriceList;
    ProductItemPrice productItemPrice;
    org.ofbiz.ordermax.base.TreeNode categoryTreeDef;
    ArrayList<ProductAssoc> productAssocList = null;
    ArrayList<ProductAssoc> productAssocToList = null;

    public ArrayList<ProductAssoc> getProductAssocToList() {
        return productAssocToList;
    }

    public void setProductAssocToList(ArrayList<ProductAssoc> productAssocToList) {
        this.productAssocToList = productAssocToList;
    }

    public ArrayList<ProductAssoc> getProductAssocList() {
        return productAssocList;
    }

    public void setProductAssocList(ArrayList<ProductAssoc> productAssocList) {
        this.productAssocList = productAssocList;
    }
    
    public ProductComposite(){
        
    }

    public TreeNode getCategoryTreeDef() {
        return categoryTreeDef;
    }

    public void setCategoryTreeDef(TreeNode categoryTreeDef) {
        this.categoryTreeDef = categoryTreeDef;
    }
    

    ListAdapterListModel<ProductCategoryMember> productCategoryList;
    ProductContentCompositeList productContentCompositeList = null;

    public ProductContentCompositeList getProductContentCompositeList() {
        return productContentCompositeList;
    }

    public void setProductContentCompositeList(ProductContentCompositeList productContentCompositeList) {
        this.productContentCompositeList = productContentCompositeList;
    }
    
    public ListAdapterListModel<ProductCategoryMember> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(ListAdapterListModel<ProductCategoryMember> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }

    public ProductItemPrice getProductItemPrice() {
        return productItemPrice;
    }

    public void setProductItemPrice(ProductItemPrice productItemPrice) {
        this.productItemPrice = productItemPrice;
    }
    /*
    public ProductPriceList getProductPriceList() {
        return productPriceList;
    }

    public void setProductPriceList(ProductPriceList productPriceList) {
        this.productPriceList = productPriceList;
    }
*/    
    public SupplierProductList getSupplierProductList() {
        return supplierProductList;
    }

    public void setSupplierProductList(SupplierProductList supplierProductList) {
        this.supplierProductList = supplierProductList;
    }
    
    public GoodIdentificationList getGoodIdentificationList() {
        return goodIdentificationList;
    }

    public void setGoodIdentificationList(GoodIdentificationList goodIdentificationList) {
        this.goodIdentificationList = goodIdentificationList;
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
