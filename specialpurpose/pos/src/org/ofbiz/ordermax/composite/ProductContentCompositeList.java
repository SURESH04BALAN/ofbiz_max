/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.entity.ProductContent;
import org.ofbiz.ordermax.entity.ProductPriceType;

/**
 *
 * @author siranjeev
 */
public class ProductContentCompositeList extends ListAdapterListModel<ProductContentComposite>{

    public ProductContentComposite getProductContent(String productContentTypeId){
        ProductContentComposite ppt = null;
        for(ProductContentComposite pptIter: list){
            if(productContentTypeId.equals(pptIter.getProductContent().getproductContentTypeId())){
                ppt = pptIter;
                break;
            }
        }
        return ppt;
    }
}
