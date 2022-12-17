/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.util.ArrayList;
import java.util.List;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.SupplierProduct;

/**
 *
 * @author siranjeev
 */
public class SupplierProductList extends ListAdapterListModel<SupplierProductComposite> {

    private String currentPartyId;

    //assuming we sorted by date list
    public SupplierProductComposite getCurrentSupplierProduct() {

        if (getSize() > 0) {
            return getElementAt(0);
        }

        return null;
    }

    //assuming we sorted by date list
    public SupplierProductComposite getCurrentSupplierProductByParty() {
        return getSupplerProductByParty(currentPartyId);
    }

    public SupplierProductComposite getSupplerProductByParty(String partyId) {

        SupplierProductComposite spc = null;
        for (SupplierProductComposite itr : list) {
            if (itr.getSupplierProduct().getpartyId().equals(partyId)) {
                spc = itr;
                break;
            }
        }

        return spc;
    }
    public SupplierProductComposite getSupplerProduct(String partyId, String curency) {
            Debug.logInfo("selected product partyId: " + partyId + " Currency: " + curency, "this");
        SupplierProductComposite spc = null;
        for (SupplierProductComposite itr : list) {
            if (itr.getSupplierProduct().getpartyId().equals(partyId)
                    && itr.getSupplierProduct().getcurrencyUomId().equals(curency)) {
                Debug.logInfo("selected product found: " + itr.getSupplierProduct().getlastPrice() + " Currency: " + curency, "this");                
                return itr;
            }
        }

        return spc;
    }

    public void createAllElement(List<GenericValue> priceList) {

//        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
        for (GenericValue priceGV : priceList) {
            SupplierProductComposite ppc = new SupplierProductComposite();
            SupplierProduct price = new SupplierProduct(priceGV);
            ppc.setSupplierProduct(price);
            Debug.logInfo("SupplierProduct.getpartyId(): " + price.getpartyId(), "SupplierProduct");
            this.add(ppc);
        }
    }

    public String getCurrentPartyId() {
        return currentPartyId;
    }

    public void setCurrentPartyId(String currentPartyId) {
        this.currentPartyId = currentPartyId;
    }
    
    public List<GenericValue> getAllGenericValueElement(){
            List<GenericValue> priceList = new ArrayList<GenericValue>();
//        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
            for (SupplierProductComposite supplierProdGV : list) {
                priceList.add(supplierProdGV.getSupplierProduct().getGenericValueObj());
            }    
            return priceList;
    }    
}
