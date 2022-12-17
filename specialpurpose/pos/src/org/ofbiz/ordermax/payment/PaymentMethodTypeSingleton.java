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
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.PaymentMethodType;
import static org.ofbiz.ordermax.payment.PaymentTypeSingleton.loadAll;

/**
 *
 * @author siranjeev
 */
public class PaymentMethodTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, PaymentMethodType> valueMap = null;

    private PaymentMethodTypeSingleton() {
        valueMap = new HashMap<String, PaymentMethodType>();
    }

    private static class SingletonHolder {

        public static final PaymentMethodTypeSingleton INSTANCE = new PaymentMethodTypeSingleton();
    }

    public static PaymentMethodTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    final static public List<PaymentMethodType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<PaymentMethodType>(getInstance().valueMap.values());
    }

    final static public ListModel<PaymentMethodType> getValueListModal() {
        ListAdapterListModel<PaymentMethodType> modal = new ListAdapterListModel<PaymentMethodType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    protected Object readResolve() {
        return getInstance();
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();

            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("PaymentMethodType", XuiContainer.getSession().getDelegator(), "description ASC");
            for (GenericValue val : valueList) {
                PaymentMethodType paymentMethodType = new PaymentMethodType(val);
                getInstance().valueMap.put(paymentMethodType.getpaymentMethodTypeId(), paymentMethodType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static PaymentMethodType getPaymentMethodType(String paymentMethodTypeId) throws Exception {
        PaymentMethodType paymentType = null;
        if (getInstance().valueMap.containsKey(paymentMethodTypeId)) {
            paymentType = getInstance().valueMap.get(paymentMethodTypeId);
        } else {

            loadAll();
            if (getInstance().valueMap.containsKey(paymentMethodTypeId)) {
                paymentType = getInstance().valueMap.get(paymentMethodTypeId);
            } else {
                throw new Exception("unable to load paymentType : " + paymentMethodTypeId);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        PaymentMethodTypeSingleton.singletonSesion = singletonSesion;
    }
}
