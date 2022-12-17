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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductStoreGroup;


/**
 *
 * @author siranjeev
 */
public class ProductStoreGroupSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductStoreGroup> valueMap = null;

    private ProductStoreGroupSingleton() {
        valueMap = new HashMap<String, ProductStoreGroup>();
    }

    private static class SingletonHolder {

        public static final ProductStoreGroupSingleton INSTANCE = new ProductStoreGroupSingleton();
    }

    public static ProductStoreGroupSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductStoreGroup> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductStoreGroup>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductStoreGroup> getValueListModal() {
        ListAdapterListModel<ProductStoreGroup> modal = new ListAdapterListModel<ProductStoreGroup>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductStoreGroup", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductStoreGroup  productPriceType = new ProductStoreGroup(val);
     
                getInstance().valueMap.put(productPriceType.getproductStoreGroupId(), productPriceType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreGroupSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductStoreGroup getProductStoreGroup(String productStoreGroupId) throws Exception {
        ProductStoreGroup geoList = null;
        if (getInstance().valueMap.containsKey(productStoreGroupId)) {
            geoList = getInstance().valueMap.get(productStoreGroupId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(productStoreGroupId)) {
                geoList = getInstance().valueMap.get(productStoreGroupId);
            } else {
                throw new Exception("unable to load geo : " + productStoreGroupId);
            }
        }

        return geoList;
    }
}
