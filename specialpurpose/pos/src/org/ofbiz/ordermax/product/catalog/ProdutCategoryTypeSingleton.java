/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import org.ofbiz.ordermax.product.productstore.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductCategoryType;

/**
 *
 * @author siranjeev
 */
public class ProdutCategoryTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductCategoryType> valueMap = null;

    private ProdutCategoryTypeSingleton() {
        valueMap = new HashMap<String, ProductCategoryType>();
    }

    private static class SingletonHolder {

        public static final ProdutCategoryTypeSingleton INSTANCE = new ProdutCategoryTypeSingleton();
    }

    public static ProdutCategoryTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductCategoryType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductCategoryType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductCategoryType> getValueListModal() {
        ListAdapterListModel<ProductCategoryType> modal = new ListAdapterListModel<ProductCategoryType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductCategoryType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductCategoryType  posTerminal = new ProductCategoryType(val);     
                getInstance().valueMap.put(posTerminal.getproductCategoryTypeId(), posTerminal);
                Debug.logInfo("Find productId: "+ posTerminal.getproductCategoryTypeId() + " getTerminalName " + posTerminal.getdescription(), "getTerminalName");
            }
        } catch (Exception ex) {
            Logger.getLogger(ProdutCategoryTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductCategoryType(String productCategoryTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productCategoryTypeId", productCategoryTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductCategoryType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductCategoryType  product = new ProductCategoryType(val);     
                getInstance().valueMap.put(product.getproductCategoryTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProdutCategoryTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductCategoryType getProductCategoryType(String productCategoryTypeId) throws Exception {
        ProductCategoryType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productCategoryTypeId)) {
            geoList = getInstance().valueMap.get(productCategoryTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductCategoryType( productCategoryTypeId);
         
            if (getInstance().valueMap.containsKey(productCategoryTypeId)) {
                geoList = getInstance().valueMap.get(productCategoryTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load Pos Terminal Id : " + productCategoryTypeId);
            }
        }

        return geoList;
    }
    
}
