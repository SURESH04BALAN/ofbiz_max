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
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.PaymentGroupType;

/**
 *
 * @author siranjeev
 */
public class PaymentGroupTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, PaymentGroupType> valueMap = null;

    private PaymentGroupTypeSingleton() {
        valueMap = new HashMap<String, PaymentGroupType>();
    }

    private static class SingletonHolder {

        public static final PaymentGroupTypeSingleton INSTANCE = new PaymentGroupTypeSingleton();
    }

    public static PaymentGroupTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<PaymentGroupType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<PaymentGroupType>(getInstance().valueMap.values());
    }



    final static public ListModel<PaymentGroupType> getValueListModal() {
        ListAdapterListModel<PaymentGroupType> modal = new ListAdapterListModel<PaymentGroupType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }


    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("PaymentGroupType", XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                PaymentGroupType paymentType = new PaymentGroupType(val);
                getInstance().valueMap.put(paymentType.getpaymentGroupTypeId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentGroupTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static PaymentGroupType getPaymentGroupType(String paymentGroupTypeId) throws Exception {
        PaymentGroupType paymentType = null;
        if (getInstance().valueMap.containsKey(paymentGroupTypeId)) {
            paymentType = getInstance().valueMap.get(paymentGroupTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(paymentGroupTypeId)) {
                paymentType = getInstance().valueMap.get(paymentGroupTypeId);
            } else {
                throw new Exception("unable to load paymentType : " + paymentGroupTypeId);
            }
        }

        return paymentType;
    }
}
