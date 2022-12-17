/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.entity.ProductPriceType;

/**
 *
 * @author siranjeev
 */
public class ProductPriceTypeList extends ListAdapterListModel<ProductPriceType>{

    public ProductPriceType getProductPriceType(String productPriceTypeId){
        ProductPriceType ppt = null;
        for(ProductPriceType pptIter: list){
            if(productPriceTypeId.equals(pptIter.getproductPriceTypeId())){
                ppt = pptIter;
                break;
            }
        }
        return ppt;
    }
}
