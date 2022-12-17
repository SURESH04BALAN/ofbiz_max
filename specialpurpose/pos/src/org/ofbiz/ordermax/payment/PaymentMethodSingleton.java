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
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.PaymentMethod;

/**
 *
 * @author siranjeev
 */
public class PaymentMethodSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, PaymentMethod> valueMap = null;

    private PaymentMethodSingleton() {
        valueMap = new HashMap<String, PaymentMethod>();
    }

    private static class SingletonHolder {

        public static final PaymentMethodSingleton INSTANCE = new PaymentMethodSingleton();
    }

    public static PaymentMethodSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<PaymentMethod> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<PaymentMethod>(getInstance().valueMap.values());
    }

    final static public List<PaymentMethod> getValueList(String partyId) {
        List<PaymentMethod> modal = new ArrayList<PaymentMethod>();
        for (PaymentMethod paymentType : getInstance().getValueList()) {
            if (UtilValidate.isNotEmpty(paymentType.getpartyId()) && paymentType.getpartyId().equals(partyId)) {
                modal.add(paymentType);
            }
        }
        return modal;
    }

    final static public ListModel<PaymentMethod> getValueListModal() {
        ListAdapterListModel<PaymentMethod> modal = new ListAdapterListModel<PaymentMethod>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<PaymentMethod> getValueListModal(String partyId) {
        ListAdapterListModel<PaymentMethod> modal = new ListAdapterListModel<PaymentMethod>();
        for (PaymentMethod paymentType : getInstance().getValueList()) {
            if (UtilValidate.isNotEmpty(paymentType.getpartyId()) && paymentType.getpartyId().equals(partyId)) {
                modal.add(paymentType);
            }
        }
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("PaymentMethod", XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                PaymentMethod paymentType = new PaymentMethod(val);
                getInstance().valueMap.put(paymentType.getpaymentMethodId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentMethodSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static PaymentMethod getPaymentMethod(String paymentTypeId) throws Exception {
        PaymentMethod paymentType = null;
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
        PaymentMethodSingleton.singletonSesion = singletonSesion;
    }
}
