/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

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
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.PaymentType;

/**
 *
 * @author siranjeev
 */
public class PaymentTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, PaymentType> valueMap = null;

    private PaymentTypeSingleton() {
        valueMap = new HashMap<String, PaymentType>();
    }

    private static class SingletonHolder {

        public static final PaymentTypeSingleton INSTANCE = new PaymentTypeSingleton();
    }

    public static PaymentTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<PaymentType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<PaymentType>(getInstance().valueMap.values());
    }
    final static public List<PaymentType> getValueList(String parentTypeId) {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        List<PaymentType> resultList = new ArrayList<PaymentType>();
        for (PaymentType paymentType : PaymentTypeSingleton.getValueList()) {
            if (UtilValidate.isNotEmpty(paymentType.getparentTypeId()) && paymentType.getparentTypeId().equals(parentTypeId)) {
                Debug.logInfo("paymentType.getparentTypeId(): " + paymentType.getparentTypeId() +"  parentTypeId: " + parentTypeId, parentTypeId);
                resultList.add(paymentType);
            }
        }
        return resultList;
    }

    final static public ListModel<PaymentType> getValueListModal() {
        ListAdapterListModel<PaymentType> modal = new ListAdapterListModel<PaymentType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<PaymentType> getValueListModal(String parentTypeId) {
        ListAdapterListModel<PaymentType> modal = new ListAdapterListModel<PaymentType>();
        for (PaymentType paymentType : getInstance().getValueList()) {
            if (UtilValidate.isNotEmpty(paymentType.getparentTypeId()) && paymentType.getparentTypeId().equals(parentTypeId)) {
                modal.add(paymentType);
            }
        }

        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("PaymentType",ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                PaymentType paymentType = new PaymentType(val);
                getInstance().valueMap.put(paymentType.getpaymentTypeId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static PaymentType getPaymentType(String paymentTypeId) throws Exception {
        PaymentType paymentType = null;
        if (getInstance().valueMap.containsKey(paymentTypeId)) {
            paymentType = getInstance().valueMap.get(paymentTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(paymentTypeId)) {
                paymentType = getInstance().valueMap.get(paymentTypeId);
            } else {
                throw new Exception("unable to load paymentType : " + paymentTypeId);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        PaymentTypeSingleton.singletonSesion = singletonSesion;
    }
}
