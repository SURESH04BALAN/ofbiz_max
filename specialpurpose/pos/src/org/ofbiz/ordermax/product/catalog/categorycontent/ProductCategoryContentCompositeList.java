/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.product.catalog.categorycontent;

import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.ProductContent;
import org.ofbiz.ordermax.entity.ProductPriceType;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryContentCompositeList extends ListAdapterListModel<ProductCategoryContentComposite>{

    public ProductCategoryContentComposite getProductContent(String productContentTypeId){
        ProductCategoryContentComposite ppt = null;
        for(ProductCategoryContentComposite pptIter: list){
            if(productContentTypeId.equals(pptIter.getProductCategoryContent().getprodCatContentTypeId())){
                ppt = pptIter;
                break;
            }
        }
        return ppt;
    }
}
