/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.party.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductPricePurpose;

/**
 *
 * @author siranjeev
 */
public class ProductPricePurposeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductPricePurpose> valueMap = null;

    private ProductPricePurposeSingleton() {
        valueMap = new HashMap<String, ProductPricePurpose>();
    }

    private static class SingletonHolder {

        public static final ProductPricePurposeSingleton INSTANCE = new ProductPricePurposeSingleton();
    }

    public static ProductPricePurposeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductPricePurpose> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductPricePurpose>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductPricePurpose> getValueListModal() {
        ListAdapterListModel<ProductPricePurpose> modal = new ListAdapterListModel<ProductPricePurpose>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductPricePurpose", whereClauseMap, null, ProductPricePurposeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ProductPricePurpose  productPriceType = new ProductPricePurpose(val);
     
                getInstance().valueMap.put(productPriceType.getproductPricePurposeId(), productPriceType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductPricePurposeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductPricePurpose getProductPricePurposeType(String productTypeId) throws Exception {
        ProductPricePurpose geoList = null;
        if (getInstance().valueMap.containsKey(productTypeId)) {
            geoList = getInstance().valueMap.get(productTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(productTypeId)) {
                geoList = getInstance().valueMap.get(productTypeId);
            } else {
                throw new Exception("unable to load geo : " + productTypeId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ProductPricePurposeSingleton.singletonSession = singletonSession;
    }
}
