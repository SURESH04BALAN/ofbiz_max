/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productstore;

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
import org.ofbiz.ordermax.entity.PosTerminal;

/**
 *
 * @author siranjeev
 */
public class PosTerminalSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, PosTerminal> valueMap = null;

    private PosTerminalSingleton() {
        valueMap = new HashMap<String, PosTerminal>();
    }

    private static class SingletonHolder {

        public static final PosTerminalSingleton INSTANCE = new PosTerminalSingleton();
    }

    public static PosTerminalSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<PosTerminal> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<PosTerminal>(getInstance().valueMap.values());
    }

    final static public ListModel<PosTerminal> getValueListModal() {
        ListAdapterListModel<PosTerminal> modal = new ListAdapterListModel<PosTerminal>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("PosTerminal", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                PosTerminal  posTerminal = new PosTerminal(val);     
                getInstance().valueMap.put(posTerminal.getPosTerminalId(), posTerminal);
                Debug.logInfo("Find productId: "+ posTerminal.getPosTerminalId() + " getTerminalName " + posTerminal.getTerminalName(), "getTerminalName");
            }
        } catch (Exception ex) {
            Logger.getLogger(PosTerminalSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadPosTerminal(String posTerminalId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("posTerminalId", posTerminalId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("PosTerminal", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                PosTerminal  product = new PosTerminal(val);     
                getInstance().valueMap.put(product.getPosTerminalId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(PosTerminalSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static PosTerminal getPosTerminal(String posTerminalId) throws Exception {
        PosTerminal geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(posTerminalId)) {
            geoList = getInstance().valueMap.get(posTerminalId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadPosTerminal( posTerminalId);
         
            if (getInstance().valueMap.containsKey(posTerminalId)) {
                geoList = getInstance().valueMap.get(posTerminalId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load Pos Terminal Id : " + posTerminalId);
            }
        }

        return geoList;
    }
    
}
