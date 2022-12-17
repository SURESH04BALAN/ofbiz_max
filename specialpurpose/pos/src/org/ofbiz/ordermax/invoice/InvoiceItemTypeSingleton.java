/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.invoice;

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
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.InvoiceItemType;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class InvoiceItemTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, InvoiceItemType> valueMap = null;
    private InvoiceItemTypeSingleton() {
        valueMap = new HashMap<String, InvoiceItemType>();
    }

    private static class SingletonHolder {
        
        public static final InvoiceItemTypeSingleton INSTANCE = new InvoiceItemTypeSingleton(); 
    }

    public static InvoiceItemTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }
    final static public List<InvoiceItemType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<InvoiceItemType>(getInstance().valueMap.values());
    }

    final static public ListModel<InvoiceItemType> getValueListModal() {
        ListAdapterListModel<InvoiceItemType> modal = new ListAdapterListModel<InvoiceItemType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("InvoiceItemType", XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                InvoiceItemType invoiceType = new InvoiceItemType(val);
                getInstance().valueMap.put(invoiceType.getinvoiceItemTypeId(), invoiceType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static InvoiceItemType getInvoiceItemType(String invoiceItemTypeId) throws Exception {
        InvoiceItemType invoiceType = null;
        if (getInstance().valueMap.containsKey(invoiceItemTypeId)) {
            invoiceType = getInstance().valueMap.get(invoiceItemTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(invoiceItemTypeId)) {
                invoiceType = getInstance().valueMap.get(invoiceItemTypeId);
            } else {
                throw new Exception("unable to load paymentType : " + invoiceItemTypeId);
            }
        }
        
        return invoiceType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        InvoiceItemTypeSingleton.singletonSesion = singletonSesion;
    }    
}
