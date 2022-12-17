/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class ProductPriceList extends ListAdapterListModel<ProductPriceComposite> {

    //assuming we sorted by date list
    public ProductPriceComposite getCurrentPrice() {

        if (getSize() > 0) {
            return getElementAt(0);
        }

        return null;
    }

    public void createAllElement(List<GenericValue> priceList) {

//        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
        for (GenericValue priceGV : priceList) {
            try {
                ProductPriceComposite ppc = new ProductPriceComposite();
                ProductPrice price = new ProductPrice(priceGV);
                ppc.setProductPrice(price);

                ppc.setProductPriceType(ProductPriceTypeSingleton.getProductPriceType(price.getproductPriceTypeId()));
                this.add(ppc);
            } catch (Exception ex) {
                Logger.getLogger(ProductPriceList.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public List<GenericValue> getAllGenericValueElement() {
        List<GenericValue> priceList = new ArrayList<GenericValue>();
//        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
        for (ProductPriceComposite priceGV : list) {
            priceList.add(priceGV.getProductPrice().getGenericValueObj());
        }
        return priceList;
    }

}
