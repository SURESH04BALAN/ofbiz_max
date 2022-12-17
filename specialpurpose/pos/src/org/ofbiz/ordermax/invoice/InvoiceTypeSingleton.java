/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.invoice;

import org.ofbiz.ordermax.orderbase.*;
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
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import static org.ofbiz.ordermax.payment.PaymentTypeSingleton.getInstance;

/**
 *
 * @author siranjeev
 */
public class InvoiceTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, InvoiceType> valueMap = null;
    private InvoiceTypeSingleton() {
        valueMap = new HashMap<String, InvoiceType>();
    }

    private static class SingletonHolder {
        
        public static final InvoiceTypeSingleton INSTANCE = new InvoiceTypeSingleton(); 
    }

    public static InvoiceTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }
    final static public List<InvoiceType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<InvoiceType>(getInstance().valueMap.values());
    }

    final static public ListModel<InvoiceType> getValueListModal() {
        ListAdapterListModel<InvoiceType> modal = new ListAdapterListModel<InvoiceType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("InvoiceType", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                InvoiceType invoiceType = new InvoiceType(val);
                getInstance().valueMap.put(invoiceType.getinvoiceTypeId(), invoiceType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static InvoiceType getInvoiceType(String invoiceTypeId) throws Exception {
        InvoiceType invoiceType = null;
        if (getInstance().valueMap.containsKey(invoiceTypeId)) {
            invoiceType = getInstance().valueMap.get(invoiceTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(invoiceTypeId)) {
                invoiceType = getInstance().valueMap.get(invoiceTypeId);
            } else {
                throw new Exception("unable to load paymentType : " + invoiceTypeId);
            }
        }
        
        return invoiceType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        InvoiceTypeSingleton.singletonSesion = singletonSesion;
    }    
}
