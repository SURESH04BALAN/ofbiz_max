/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productloader;

import java.io.Serializable;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;

/**
 *
 * @author siranjeev
 */


public class ProductTreeArraySingleton extends CatalogCategoryDataTree implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private ProductTreeArraySingleton(ProductCategory categoryId) {
        super(categoryId);
    }
    
    private static class ProductTreeArraySingletonHolder {

        public static final ProductTreeArraySingleton INSTANCE = new ProductTreeArraySingleton( ControllerOptions.getSession().getProductCategory());
    }

    public static ProductTreeArraySingleton getInstance() {
        return ProductTreeArraySingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }
    
    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        ProductTreeArraySingleton.singletonSesion = singletonSesion;
    }    
}
