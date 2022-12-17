/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.base.util.Debug;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;
/**
 *
 * @author sureshbalan
 */
public class InvoicePaymentHelper {
    
    public static final String module = InvoicePaymentHelper.class.getName();
    
    public static List<Map<String, Object>> getPurchaseInvoices(String partyId, String roleTypeId, String invoiceTypeId, Delegator delegator) throws GenericEntityException {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<EntityExpr> exprs = new ArrayList<EntityExpr>();
        exprs.add(EntityCondition.makeCondition("partyIdFrom", EntityOperator.EQUALS, partyId));
//        exprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, roleTypeId));
        exprs.add(EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId));

//        exprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, roleTypeId),
//                EntityOperator.AND, EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId)));

        GenericValue defaultPrice = null;
        GenericValue listPrice = null;
        GenericValue avgCost = null;

//        List<GenericValue> invoiceList = delegator.findList("InvoiceAndRole", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        List<GenericValue> invoiceList = delegator.findList("Invoice",  EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);        
        if (invoiceList != null) {

            Iterator<GenericValue> it = invoiceList.iterator();
            while (it.hasNext()) {

                Map<String, Object> resultMap = new HashMap<String, Object>();

                GenericValue invoiceRole = it.next();

                String invoiceId = invoiceRole.getString("invoiceId");
                GenericValue invoice = delegator.findOne("Invoice", UtilMisc.toMap("invoiceId", invoiceId), false);

                if (invoice != null) {

                    resultMap.put("Invoice", invoice);
                    GenericValue role = delegator.findOne("RoleType", UtilMisc.toMap("roleTypeId", roleTypeId), false);
                    resultMap.put("InvoiceRole", role);

                    GenericValue partyFrom = delegator.findOne("Party", UtilMisc.toMap("partyId", partyId), false);
                    resultMap.put("PartyFrom", partyFrom);

                }
                result.add(resultMap);
            }
        }
        return result;
    }
    
//--------------------------------------------------------------------------------------------------//
//		payments
//--------------------------------------------------------------------------------------------------//

    public static GenericValue createSupplierPayment(String paymentMethodTypeId, 
            String referenceNumber, 
            String partyIdFrom, 
            String partyIdTo, 
            BigDecimal notApplied, 
            String currency,
            Delegator delegator) {

        String paymentTypeId = "VENDOR_PAYMENT";
        
        FastMap<String, Object> input = FastMap.newInstance();
        String paymentId = delegator.getNextSeqId("Payment");
        input.put("paymentId", paymentId);
        input.put("paymentTypeId", paymentTypeId);
        input.put("paymentMethodTypeId", paymentMethodTypeId);
        input.put("partyIdFrom", partyIdFrom);
        input.put("partyIdTo", partyIdTo);
        input.put("roleTypeIdTo", "MANAGER");
        input.put("statusId", "PMNT_CONFIRMED");
        input.put("effectiveDate", UtilDateTime.nowTimestamp());
        input.put("paymentRefNum", referenceNumber);
        input.put("amount", notApplied);
        input.put("currencyUomId", currency);
        input.put("actualCurrencyUomId", currency);

        GenericValue payment = delegator.makeValue("Payment", input);
        try {
            delegator.create(payment);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        return payment;
    }

    public static boolean createPaymentApplication(String paymentId, String invoiceId, BigDecimal notApplied, GenericValue userLogin, LocalDispatcher dispatcher) {
        boolean result = true;
        Map<String, Object> appl = FastMap.newInstance();
        appl.put("paymentId", paymentId);
        appl.put("invoiceId", invoiceId);
//     appl.put("billingAccountId", supplierPartyId);
        appl.put("amountApplied", notApplied);
        appl.put("userLogin", userLogin);
        Map<String, Object> createPayApplResult;
        try {
            createPayApplResult = dispatcher.runSync("createPaymentApplication", appl);
            if (ServiceUtil.isError(createPayApplResult)) {
                Debug.logInfo("create payment app failed", module);
                result = false;
            }
        } catch (GenericServiceException e) {
            result = false;
            Debug.logError(e, module);
        }

        return result;
    }
    
}
