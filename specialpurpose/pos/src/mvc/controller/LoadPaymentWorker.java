/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

/**
 *
 * @author siranjeev
 */
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.accounting.payment.PaymentWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.ObjectType;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderChangeHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.PaymentApplicationCompositeList;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.PaymentGroupComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.OrderPaymentPreference;
import org.ofbiz.ordermax.entity.Payment;
import org.ofbiz.ordermax.entity.PaymentApplication;
import org.ofbiz.ordermax.entity.PaymentGroup;
import org.ofbiz.ordermax.entity.PaymentGroupMember;
import org.ofbiz.ordermax.entity.PaymentMethod;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.payment.paymentmethod.PaymentMethodBaseFactory;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadPaymentWorker extends LoadBaseSwingWorker< PaymentComposite> {

    private static int decimals = UtilNumber.getBigDecimalScale("invoice.decimals");
    private static int rounding = UtilNumber.getBigDecimalRoundingMode("invoice.rounding");

    public LoadPaymentWorker(ListAdapterListModel<PaymentComposite> personListModel, XuiSession delegator, Map<String, Object> findOptionMap) {
        super(personListModel, findOptionMap);
        this.session = delegator;
    }

    @Override
    protected List<PaymentComposite> doInBackground() throws Exception {

        List<PaymentComposite> paymentComposites = new ArrayList<PaymentComposite>();
        Map<String, Object> result = null;

        List<Order> orders = new ArrayList<Order>();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        // Simulate progress
        sleepAWhile();
        String entityName = "Payment";

        //Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//                    whereClauseMap.put("partyId", partyIdTextField.getText());
//                    whereClauseMap.put("roleTypeId", "BILL_FROM_VENDOR");

        List<GenericValue> genValList = PosProductHelper.getGenericValueListsWithSelection(entityName, findOptionMap, null, session.getDelegator());
        maxProgress = genValList.size() + 1;
        for (GenericValue genVal : genValList) {

            PaymentComposite paymentComposite = new PaymentComposite();
            Payment payment = new Payment(genVal);
            paymentComposite.setPayment(payment);
            //paymentComposite.setPartyPaymentFrom(PartyListSingleton.getAccount(payment.getpartyIdFrom()));
            //paymentComposite.setPartyPaymentTo(PartyListSingleton.getAccount(payment.getpartyIdTo()));
            paymentComposite.setAppliedAmount(PaymentWorker.getPaymentApplied(session.getDelegator(), payment.getpaymentId()));
            paymentComposite.setOutstandingAmount(payment.getamount().subtract(paymentComposite.getAppliedAmount()));

            paymentComposites.add(paymentComposite);
//                        int prograss = calcProgress(progressedItems + 1);

//                        if ((prograss + 1) % 4 == 0) {
            publish(paymentComposite);
//                        } else {
//                            progressedItems++;
//                        }

            if (isCancelled()) {
                break;
            }

        }

        return paymentComposites;
        /*        
         boolean beganTransaction = false;
         try {
         beganTransaction = TransactionUtil.begin();
         try {

         result = session.getDispatcher().runSync("performFind",
         UtilMisc.<String, Object>toMap("entityName", "Payment",
         "inputFields", findOptionMap,
         "userLogin", session.getUserLogin()));

         for (Map.Entry<String, Object> entryDept : result.entrySet()) {
         if (entryDept.getValue() == null) {
         Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + "Null", module);
         } else {
         Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
         }
         }

         } catch (GenericServiceException ex) {
         Debug.logError(ex, module);
         }

         EntityListIterator listIt = (EntityListIterator) result.get("listIt");
         if (listIt != null) {
         try {
         List<GenericValue> list = listIt.getCompleteList();
         maxProgress = list.size() + 1;
         //                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
         for (GenericValue gv : list) {

         PaymentComposite paymentComposite = new PaymentComposite();
         Payment payment = new Payment(gv);
         paymentComposite.setPayment(payment);
         //paymentComposite.setPartyPaymentFrom(PartyListSingleton.getAccount(payment.getpartyIdFrom()));
         //paymentComposite.setPartyPaymentTo(PartyListSingleton.getAccount(payment.getpartyIdTo()));
         paymentComposite.setAppliedAmount(PaymentWorker.getPaymentApplied(session.getDelegator(), payment.getpaymentId()));
         paymentComposite.setOutstandingAmount(payment.getamount().subtract(paymentComposite.getAppliedAmount()));

         paymentComposites.add(paymentComposite);
         //                        int prograss = calcProgress(progressedItems + 1);

         //                        if ((prograss + 1) % 4 == 0) {
         publish(paymentComposite);
         //                        } else {
         //                            progressedItems++;
         //                        }

         if (isCancelled()) {
         break;
         }
         }

         } catch (GenericEntityException e) {
         Debug.logError(e, module);
         } finally {
         try {
         listIt.close();
         } catch (GenericEntityException e) {
         Debug.logError(e, module);
         }
         }
         }

         } catch (GenericTransactionException e) {
         Debug.logError(e, module);
         try {
         TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
         } catch (GenericTransactionException e2) {
         Debug.logError(e2, "Unable to rollback transaction", module);
         //                pos.showMessageDialog("dialog/error/exception", e2.getMessage());
         }
         //            pos.showMessageDialog("dialog/error/exception", e.getMessage());
         } finally {
         try {
         TransactionUtil.commit(beganTransaction);
         } catch (GenericTransactionException e) {
         Debug.logError(e, "Unable to commit transaction", module);
         //                pos.showMessageDialog("dialog/error/exception", e.getMessage());
         }
         }

         return paymentComposites;*/
    }

    static public PaymentComposite newPayment(XuiSession session, String paymentFromPartyId, String paymentToPartyId, String paymentTypeId, GenericValue order) {
        PaymentComposite paymentComposite = newPayment(session, paymentFromPartyId, paymentToPartyId, paymentTypeId);
        OrderPaymentPreference orderPaymentPreference = LoadOrderWorker.getOrderPaymentPreference(order);
        paymentComposite.setOrderPaymentPreferences(orderPaymentPreference);
        return paymentComposite;
    }

    static public PaymentComposite newPayment(XuiSession session, String paymentFromPartyId, String paymentToPartyId, String paymentTypeId) {
        PaymentComposite paymentComposite = new PaymentComposite();
        paymentComposite.setPayment(new Payment());
        paymentComposite.getPayment().setamount(BigDecimal.ZERO);
//VENDOR_PAYMENT
        //CUSTOMER_PAYMENT
        paymentComposite.getPayment().setstatusId("PMNT_NOT_PAID");
        paymentComposite.getPayment().setpaymentTypeId(paymentTypeId);
        paymentComposite.getPayment().setpaymentMethodId("ABN_CHECKING");
        paymentComposite.getPayment().setpaymentMethodTypeId(PaymentMethodBaseFactory.COMPANY_CHECK);
        paymentComposite.getPayment().setcurrencyUomId((String) XuiContainer.getSession().getAttribute("currency"));
        paymentComposite.getPayment().setpartyIdFrom(paymentFromPartyId);
        paymentComposite.getPayment().setpartyIdTo(paymentToPartyId);
        paymentComposite.getPayment().seteffectiveDate(UtilDateTime.nowTimestamp());

        /*  try {
         paymentComposite.setPartyPaymentTo(PartyListSingleton.getAccount(paymentComposite.getPayment().getpartyIdTo()));
         } catch (Exception ex) {
         Logger.getLogger(LoadPaymentWorker.class.getName()).log(Level.SEVERE, null, ex);
         }

         paymentComposite.setPartyPaymentFrom(paymentFromAccount);
         paymentComposite.setPartyPaymentTo(paymentToAccount);*/
        paymentComposite.setPaymentApplicationCompositeList(new PaymentApplicationCompositeList());
        return paymentComposite;
    }

    static public PaymentComposite newPayment(XuiSession session, Account paymentToAccount, String paymentTypeId) {
        PaymentComposite paymentComposite = new PaymentComposite();
        paymentComposite.setPayment(new Payment());
        paymentComposite.getPayment().setamount(BigDecimal.ZERO);
//VENDOR_PAYMENT
        //CUSTOMER_PAYMENT
        paymentComposite.getPayment().setstatusId("PMNT_NOT_PAID");
        paymentComposite.getPayment().setpaymentTypeId(paymentTypeId);
        paymentComposite.getPayment().setpaymentMethodId("ABN_CHECKING");
        paymentComposite.getPayment().setpaymentMethodTypeId(PaymentMethodBaseFactory.COMPANY_CHECK);
        paymentComposite.getPayment().setcurrencyUomId((String) XuiContainer.getSession().getAttribute("currency"));
        paymentComposite.getPayment().seteffectiveDate(UtilDateTime.nowTimestamp());
//        paymentComposite.setPartyPaymentFrom(paymentFromAccount);
        paymentComposite.getPayment().setpartyIdTo(paymentToAccount.getParty().getpartyId());
        paymentComposite.setPaymentApplicationCompositeList(new PaymentApplicationCompositeList());
        return paymentComposite;
    }

    static public PaymentGroupComposite newPaymentGroupComposite(String paymentGroupTypeId, String paymentGroupName) {
        PaymentGroupComposite paymentGroupComposite = new PaymentGroupComposite();
        PaymentGroup pay = new PaymentGroup();
        pay.setPaymentGroupName(paymentGroupName);
        pay.setPaymentGroupTypeId(paymentGroupTypeId);
        paymentGroupComposite.setPaymentGroup(pay);

        return paymentGroupComposite;
    }

    static public PaymentGroupComposite newPaymentGroupComposite(PaymentGroup pay) {
        PaymentGroupComposite paymentGroupComposite = new PaymentGroupComposite();
        paymentGroupComposite.setPaymentGroup(pay);
        return paymentGroupComposite;
    }

    static public List<PaymentComposite> loadPayments(Map<String, Object> searchCondMap, XuiSession session) throws Exception {

        List<PaymentComposite> dataList = new ArrayList<PaymentComposite>();

        List<GenericValue> pastDueInvoices = FastList.newInstance();

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        EntityConditionList<EntityCondition> conditions = null;

        pastDueInvoices = getPaymentList(searchCondMap, session); //session.getDelegator().findList("Payment", conditions, null, UtilMisc.toList("effectiveDate DESC"), null, false);
        if (pastDueInvoices != null) {
            for (GenericValue val : pastDueInvoices) {
                PaymentComposite paymentComposite = new PaymentComposite();
                Payment payment = new Payment(val);
                paymentComposite.setPayment(payment);
//                    paymentComposite.setPartyPaymentFrom(PartyListSingleton.getAccount(payment.getpartyIdFrom()));
//                    paymentComposite.setPartyPaymentTo(PartyListSingleton.getAccount(payment.getpartyIdTo()));
                paymentComposite.setAppliedAmount(PaymentWorker.getPaymentApplied(session.getDelegator(), payment.getpaymentId()));
                paymentComposite.setOutstandingAmount(payment.getamount().subtract(paymentComposite.getAppliedAmount()));
                dataList.add(paymentComposite);
                Debug.logInfo("paymentId: " + val.getString("paymentId"), module);
            }
        }

        return dataList;
    }

    static public List<GenericValue> getPaymentList(Map<String, Object> searchCondMap, final XuiSession session) {
        List<GenericValue> pastDueInvoices = FastList.newInstance();
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            EntityConditionList<EntityCondition> conditions = null;

            List<EntityExpr> exprs = FastList.newInstance();
            for (Map.Entry<String, Object> entry : searchCondMap.entrySet()) {
                exprs.add(EntityCondition.makeCondition(entry.getKey(), EntityOperator.EQUALS, entry.getValue()));
                Debug.logInfo(entry.getKey() + " : " + entry.getValue(), module);
            }

            pastDueInvoices = session.getDelegator().findList("Payment", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, UtilMisc.toList("effectiveDate DESC"), null, false);

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pastDueInvoices;
    }

    static public PaymentComposite loadPayment(String paymentId, XuiSession session) throws Exception {

        PaymentComposite paymentComposite = null;

        try {
            GenericValue val = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            if (val != null) {
                paymentComposite = new PaymentComposite();
                Payment payment = new Payment(val);
                paymentComposite.setPayment(payment);
//                paymentComposite.setPartyPaymentFrom(PartyListSingleton.getAccount(payment.getpartyIdFrom()));
//                paymentComposite.setPartyPaymentTo(PartyListSingleton.getAccount(payment.getpartyIdTo()));
                paymentComposite.setPaymentApplicationCompositeList(loadPaymentApplications(paymentId, session));
                paymentComposite.setAppliedAmount(PaymentWorker.getPaymentApplied(val));
//                paymentComposite.setAppliedAmount(PaymentWorker.getPaymentApplied(session.getDelegator(), payment.getpaymentId()));
                paymentComposite.setOutstandingAmount(PaymentWorker.getPaymentNotApplied(val));//payment.getamount().subtract(paymentComposite.getAppliedAmount()));
                paymentComposite.setUncapturedAmount(getPaymentNotCapturedApplied(val, false));
                String billingAccountId = getPaymentBillingAccount(val);
                if (billingAccountId != null) {
                    BillingAccount billingAccount = LoadBillingAccountWorker.loadBillingAccount(billingAccountId, session);
                    paymentComposite.setBillingAccount(billingAccount);
                }
//              dataList.add(paymentComposite);
                Debug.logInfo("paymentId: " + val.getString("paymentId"), module);
                Debug.logInfo("payment curr: " + payment.getcurrencyUomId(), module);
                Debug.logInfo("billingAccount: " + billingAccountId, module);
                Debug.logInfo("getOutstandingAmount: " + paymentComposite.getOutstandingAmount(), module);
                Debug.logInfo("getAppliedAmount: " + paymentComposite.getAppliedAmount(), module);
                Debug.logInfo("paymentComposite.getUncapturedAmount() " + paymentComposite.getUncapturedAmount(), module);
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paymentComposite;
    }

    static public PaymentGroupComposite loadPaymentGroup(String paymentGroupId, XuiSession session) throws Exception {

        PaymentGroupComposite paymentGroupComposite = null;
        List<GenericValue> paymentGroupMembers = null;
        try {
            GenericValue val = session.getDelegator().findByPrimaryKey("PaymentGroup", UtilMisc.toMap("paymentGroupId", paymentGroupId));
            if (val != null) {
                Debug.logInfo("PaymentGroup is loaded: " + paymentGroupId, module);
                paymentGroupComposite = new PaymentGroupComposite();
                PaymentGroup paymentGroup = new PaymentGroup(val);
                paymentGroupComposite.setPaymentGroup(paymentGroup);
                Map<String, Object> whereClauseMap = new HashMap<String, Object>();
                whereClauseMap.put("paymentGroupId", paymentGroupId);
//                    whereClauseMap.put("roleTypeId", "BILL_FROM_VENDOR");

                paymentGroupMembers = PosProductHelper.getGenericValueListsWithSelection("PaymentGroupMember", whereClauseMap, "paymentId", session.getDelegator());
                if (UtilValidate.isNotEmpty(paymentGroupMembers)) {
                    Debug.logInfo("PaymentGroupMember found loaded: " + paymentGroupId, module);
                    for (GenericValue paymentGroupMemeber : paymentGroupMembers) {
                        PaymentGroupMember groupMemeber = new PaymentGroupMember(paymentGroupMemeber);
                        Debug.logInfo("PaymentGroupMember paymentID: " + groupMemeber.getPaymentId(), module);
                        PaymentComposite comp = loadPayment(groupMemeber.getPaymentId(), session);
                        paymentGroupComposite.addPayment(comp, groupMemeber);
                    }
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paymentGroupComposite;
    }

    static public PaymentApplicationCompositeList loadPaymentApplications(String paymentId, XuiSession session) throws Exception {

        PaymentApplicationCompositeList paymentApplicationCompositeList = new PaymentApplicationCompositeList();

        try {
            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("paymentId", EntityOperator.EQUALS, paymentId));

            List<GenericValue> valueList = session.getDelegator().findList("PaymentApplication", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (valueList != null) {
                for (GenericValue val : valueList) {
                    PaymentApplication app = new PaymentApplication(val);
                    paymentApplicationCompositeList.add(app);
                }
            }

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paymentApplicationCompositeList;
    }

    /**
     * Method to return the total amount of a payment which is applied to a
     * payment
     *
     * @param payment GenericValue object of the Payment
     * @param actual false for currency of the payment, true for the actual
     * currency
     * @return the applied total as BigDecimal in the currency of the payment
     */
    public static BigDecimal getPaymentNotCapturedApplied(GenericValue payment, Boolean actual) {
        BigDecimal paymentApplied = BigDecimal.ZERO;
        List<GenericValue> paymentApplications = null;
        try {
            List<EntityExpr> cond = UtilMisc.toList(
                    EntityCondition.makeCondition("paymentId", EntityOperator.EQUALS, payment.getString("paymentId")),
                    EntityCondition.makeCondition("toPaymentId", EntityOperator.EQUALS, payment.getString("paymentId"))
            );
            EntityCondition partyCond = EntityCondition.makeCondition(cond, EntityOperator.OR);
            paymentApplications = payment.getDelegator().findList("PaymentApplication", partyCond, null, UtilMisc.toList("invoiceId", "billingAccountId"), null, false);
            if (UtilValidate.isNotEmpty(paymentApplications)) {
                for (GenericValue paymentApplication : paymentApplications) {
                    BigDecimal amountApplied = paymentApplication.getBigDecimal("amountApplied");
                    // check currency invoice and if different convert amount applied for display
                    //if (actual.equals(Boolean.FALSE) && paymentApplication.get("invoiceId") == null && paymentApplication.get("billingAccountId") != null) {
                    //    amountApplied = amountApplied.multiply(payment.getBigDecimal("amount")).divide(payment.getBigDecimal("actualCurrencyAmount"), new MathContext(100));
                    //}
                    if (paymentApplication.get("invoiceId") == null) {
                        paymentApplied = paymentApplied.add(amountApplied).setScale(decimals, rounding);
                    }
                }
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Trouble getting entities", module);
        }
        return paymentApplied;
    }

    public static String getPaymentBillingAccount(GenericValue payment) {

        List<GenericValue> paymentApplications = null;
        try {
            List<EntityExpr> cond = UtilMisc.toList(
                    EntityCondition.makeCondition("paymentId", EntityOperator.EQUALS, payment.getString("paymentId")),
                    EntityCondition.makeCondition("toPaymentId", EntityOperator.EQUALS, payment.getString("paymentId"))
            );
            EntityCondition partyCond = EntityCondition.makeCondition(cond, EntityOperator.OR);
            paymentApplications = payment.getDelegator().findList("PaymentApplication", partyCond, null, UtilMisc.toList("invoiceId", "billingAccountId"), null, false);
            if (UtilValidate.isNotEmpty(paymentApplications)) {
                for (GenericValue paymentApplication : paymentApplications) {

                    if (paymentApplication.get("billingAccountId") != null) {
                        return (String) paymentApplication.get("billingAccountId");
                    }
                }
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Trouble getting entities", module);
        }
        return null;
    }

    static public PaymentApplicationCompositeList loadPaymentApplicationsForInvoice(String invoiceId, XuiSession session) throws Exception {

        PaymentApplicationCompositeList paymentApplicationCompositeList = new PaymentApplicationCompositeList();

        try {
            List<EntityExpr> exprs = FastList.newInstance();
            Debug.logInfo("loadPaymentApplicationsForInvoice invoiceId: " + invoiceId, module);
            exprs.add(EntityCondition.makeCondition("invoiceId", EntityOperator.EQUALS, invoiceId));

            List<GenericValue> valueList = session.getDelegator().findList("PaymentApplication", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (valueList != null) {
                for (GenericValue val : valueList) {
                    PaymentApplication app = new PaymentApplication(val);
                    paymentApplicationCompositeList.add(app);
                }
            }

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paymentApplicationCompositeList;
    }

    static public void savePaymentGroup(PaymentGroupComposite paymentGroupComposite) throws Exception {

        if (paymentGroupComposite.getFirstPaymentComposite().getBillingAccount() != null) {
            for (PaymentGroupComposite.PaymentGroupMemberComposite paymentCompo : paymentGroupComposite.getList()) {

                PaymentComposite paymentComposite = paymentCompo.paymentComposite;
                Map<String, Object> paymentValueMap = FastMap.newInstance();

                if (!BigDecimal.ZERO.equals(paymentComposite.getPayment().getamount())) {
                    paymentValueMap.put("billingAccountId", paymentComposite.getBillingAccount().getbillingAccountId());
                    paymentValueMap.put("currencyUomId", paymentComposite.getPayment().getcurrencyUomId());
                    paymentValueMap.put("partyIdFrom", paymentComposite.getPayment().getpartyIdFrom());
                    paymentValueMap.put("partyIdTo", paymentComposite.getPayment().getpartyIdTo());
                    paymentValueMap.put("statusId", paymentComposite.getPayment().getstatusId());
                    paymentValueMap.put("amount", paymentComposite.getPayment().getamount());
                    paymentValueMap.put("paymentTypeId", paymentComposite.getPayment().getpaymentTypeId());
                    paymentValueMap.put("paymentMethodTypeId", paymentComposite.getPayment().getpaymentMethodTypeId());
                    paymentValueMap.put("paymentRefNum", paymentComposite.getPayment().getpaymentRefNum());

                    String paymentId = LoadPaymentWorker.createPaymentAndApplication(paymentValueMap, ControllerOptions.getSession());
                    paymentComposite.getPayment().setpaymentId(paymentId);
                    paymentCompo.paymentGroupMember.setPaymentId(paymentId);
                }
            }
        } else if (paymentGroupComposite.getFirstPaymentComposite().getOrderPaymentPreference() != null) {
            Debug.logInfo("paymentGroupComposite.getFirstPaymentComposite().getOrderPaymentPreference() : " + paymentGroupComposite.getFirstPaymentComposite().getOrderPaymentPreference().getorderId(), module);
            //create payment method type map 
            //this will create multiple payment record for each 
            //payment method type id
            Map<String, Object> paymentValueMap = FastMap.newInstance();
            paymentValueMap.put("orderId", paymentGroupComposite.getFirstPaymentComposite().getOrderPaymentPreference().getorderId());
            for (PaymentGroupComposite.PaymentGroupMemberComposite paymentCompo : paymentGroupComposite.getList()) {

                PaymentComposite paymentComposite = paymentCompo.paymentComposite;
                if (!BigDecimal.ZERO.equals(paymentComposite.getPayment().getamount())) {
                    paymentValueMap.put(paymentComposite.getPayment().getpaymentMethodTypeId() + "_amount", paymentComposite.getPayment().getamount().toString());
                    paymentValueMap.put(paymentComposite.getPayment().getpaymentMethodTypeId() + "_reference", paymentComposite.getPayment().getpaymentRefNum());
                }
            }

            Map<String, String> result;
            //List<String> paymentIdList = new ArrayList<String>();
            try {
                result = LoadPaymentWorker.receiveOfflinePayment(paymentValueMap, ControllerOptions.getSession());

                if (!result.isEmpty()) {

                    String paymentId = "";
                    for (Map.Entry<String, String> paymentIds : result.entrySet()) {
                        paymentId = paymentIds.getValue();
                        String paymenMethodtTypeId = paymentIds.getKey();
                        PaymentComposite comp = paymentGroupComposite.getPaymentByPaymentMethodType(paymenMethodtTypeId);
                        if (comp != null) {
                            comp.getPayment().setpaymentId(paymentId);
                        }
                    }

                }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

        } else {
            for (PaymentGroupComposite.PaymentGroupMemberComposite paymentCompo : paymentGroupComposite.getList()) {
                PaymentComposite paymentComposite = paymentCompo.paymentComposite;
                if (!BigDecimal.ZERO.equals(paymentComposite.getPayment().getamount())) {
                    LoadPaymentWorker.savePayment(paymentComposite, ControllerOptions.getSession());
                    paymentCompo.paymentGroupMember.setPaymentId(paymentComposite.getPayment().getpaymentId());
                }
            }

        }

        Set<String> savePaymentIds = new HashSet<String>();
        for (PaymentGroupComposite.PaymentGroupMemberComposite paymentCompo : paymentGroupComposite.getList()) {
            PaymentComposite paymentComposite = paymentCompo.paymentComposite;
            if (UtilValidate.isNotEmpty(paymentComposite.getPayment().getpaymentId())) {
                savePaymentIds.add(paymentComposite.getPayment().getpaymentId());
            }
        }

        String paymentGroupId = LoadPaymentWorker.createPaymentGroup(paymentGroupComposite.getPaymentGroup());
        paymentGroupComposite.getPaymentGroup().setPaymentGroupId(paymentGroupId);

        LoadPaymentWorker.createPaymentGroupMembers(paymentGroupComposite.getPaymentGroup(), savePaymentIds);
    }

    static public void savePayment(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        try {

            Map<String, Object> paymentParams
                    = UtilMisc.<String, Object>toMap("paymentTypeId", paymentComposite.getPayment().getpaymentTypeId(),//"CUSTOMER_PAYMENT", 
                            "paymentMethodTypeId", paymentComposite.getPayment().getpaymentMethodTypeId(),//"EXT_BILLACT",
                            "partyIdFrom", paymentComposite.getPayment().getpartyIdFrom(),
                            "partyIdTo", paymentComposite.getPayment().getpartyIdTo(),
                            "statusId", paymentComposite.getPayment().getstatusId(),//"PMNT_RECEIVED", 
                            "effectiveDate", paymentComposite.getPayment().geteffectiveDate());

            paymentParams.put("amount", paymentComposite.getPayment().getamount());
            paymentParams.put("currencyUomId", paymentComposite.getPayment().getcurrencyUomId());
            paymentParams.put("paymentRefNum", paymentComposite.getPayment().getpaymentRefNum());
            paymentParams.put("comments", paymentComposite.getPayment().getcomments());
            if (paymentComposite.getOrderPaymentPreference() != null) {
                paymentParams.put("paymentPreferenceId", paymentComposite.getOrderPaymentPreference().getorderPaymentPreferenceId());
            }
            Debug.logInfo("amount : " + paymentComposite.getPayment().getamount() + " paymentPreferenceId: " + paymentParams.get("paymentPreferenceId"), module);
            Map<String, Object> tmpResult = null;
            paymentParams.put("userLogin", session.getUserLogin());
            String paymentId = null;

            if (UtilValidate.isEmpty(paymentComposite.getPayment().getpaymentId())) {
                tmpResult = session.getDispatcher().runSync("createPayment", paymentParams);
                if (OrderMaxUtility.handleServiceReturn("Save new Payment", tmpResult) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    paymentId = (String) tmpResult.get("paymentId");

                    GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
                    paymentComposite.setPayment(new Payment(genValue));
                }

            } else {
                paymentParams.put("paymentId", paymentComposite.getPayment().getpaymentId());
                tmpResult = session.getDispatcher().runSync("updatePayment", paymentParams);

                if (OrderMaxUtility.handleServiceReturn("Update Payment", tmpResult) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    paymentId = (String) tmpResult.get("paymentId");

                    GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
                    paymentComposite.setPayment(new Payment(genValue));

                }
            }
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }
    }

    static public void savePaymentApplications(PaymentComposite paymentComposite, XuiSession session) throws Exception {
        String paymentId = paymentComposite.getPayment().getpaymentId();
        for (PaymentApplication pa : paymentComposite.getPaymentApplicationCompositeList().getList()) {
            pa.setpaymentId(paymentId);
            savePaymentApplication(pa, session);
        }
    }

    static public OrderPaymentPreference createOrderPaymentPreference(Map<String, Object> serviceContext, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        OrderPaymentPreference orderPaymentPreference = null;
        serviceContext.put("userLogin", userLogin);
//        Map<String, Object> serviceContext = UtilMisc.toMap("orderId", orderId, "paymentMethodId", paymentMethodId, "paymentMethodTypeId", "CREDIT_CARD", "userLogin", userLogin);
        String orderPaymentPreferenceId = null;

        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("createOrderPaymentPreference", serviceContext);
        if (OrderMaxUtility.handleServiceReturn("Create Order Payment Preference", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            orderPaymentPreferenceId = (String) paymentResults.get("orderPaymentPreferenceId");
            GenericValue genValue = session.getDelegator().findByPrimaryKey("OrderPaymentPreference", UtilMisc.toMap("orderPaymentPreferenceId", orderPaymentPreferenceId));
            if (genValue != null) {
                orderPaymentPreference = new OrderPaymentPreference(genValue);
            }

        }

        return orderPaymentPreference;
    }

    static public PaymentApplication createPaymentApplication(String paymentId, String invoiceId, BigDecimal amount) {
        PaymentApplication paymentApplication = new PaymentApplication();
        paymentApplication.setpaymentId(paymentId);
        paymentApplication.setinvoiceId(invoiceId);
        paymentApplication.setamountApplied(amount);
        return paymentApplication;
    }

    static public void savePaymentApplication(PaymentApplication paymentApplication, XuiSession session) throws Exception {

        try {

            Map<String, Object> appl = FastMap.newInstance();
            appl.put("paymentId", paymentApplication.getpaymentId());
            appl.put("invoiceId", paymentApplication.getinvoiceId());
//            appl.put("billingAccountId", billingAccountId);
            appl.put("amountApplied", paymentApplication.getamountApplied());
            appl.put("userLogin", session.getUserLogin());
            Map<String, Object> tmpResult = null;

            if (paymentApplication.getpaymentApplicationId() == null
                    || (paymentApplication.getpaymentApplicationId().trim()).isEmpty()) {
                tmpResult = session.getDispatcher().runSync("createPaymentApplication", appl);
            } else {
                appl.put("paymentApplicationId", paymentApplication.getpaymentApplicationId());
                tmpResult = session.getDispatcher().runSync("updatePaymentApplication", appl);
            }

            for (Map.Entry<String, Object> entryDept : tmpResult.entrySet()) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }
            if (ServiceUtil.isError(tmpResult)) {
                Debug.logError("createPayment service erroe", module);
            } else {
                String paymentApplicationId = (String) tmpResult.get("paymentApplicationId");
                paymentApplication.setpaymentApplicationId(paymentApplicationId);
            }

            /*            
             tmpResult = dispatcher.runSync("createPaymentApplication", UtilMisc.<String, Object>toMap("paymentId", paymentId, "invoiceId", invoiceId, "billingAccountId", billingAccountId,
             "amountApplied", captureAmount, "userLogin", userLogin));
             if (ServiceUtil.isError(tmpResult)) {
             return tmpResult;
             }
             if (paymentId == null) {
             return ServiceUtil.returnError(UtilProperties.getMessage(resource, 
             "AccountingNoPaymentCreatedForInvoice", 
             UtilMisc.toMap("invoiceId", invoiceId, "billingAccountId", billingAccountId), locale));
             }
             results.put("paymentId", paymentId);
            
             GenericValue val = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
             if (val != null) {
             paymentComposite = new PaymentComposite();
             Payment payment = new Payment(val);
             paymentComposite.setPayment(payment);
             paymentComposite.setPartyPaymentFrom(PartyListSingleton.getAccount(payment.getpartyIdFrom()));
             paymentComposite.setPartyPaymentTo(PartyListSingleton.getAccount(payment.getpartyIdTo()));
             paymentComposite.setPaymentApplicationCompositeList(loadPaymentApplications(paymentId, session));
             Debug.logInfo("paymentId: " + val.getString("paymentId"), module);
             }
             */
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToPaymentConfirmed(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Payment payment = paymentComposite.getPayment();
        String paymentId = payment.getpaymentId();
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("setPaymentStatus", UtilMisc.toMap("userLogin", userLogin, "paymentId", paymentId, "statusId", "PMNT_CONFIRMED"));
        if (OrderMaxUtility.handleServiceReturn("Confirm Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            paymentComposite.setPayment(new Payment(genValue));
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public GenericValue loadPaymentGV(String paymentId, XuiSession session) {
        GenericValue genValue = null;
        try {
            genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPaymentWorker.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return genValue;
    }

    static public GenericValue loadPaymentGroupGV(String paymentId, XuiSession session) {
        GenericValue genValue = null;
        try {
            genValue = session.getDelegator().findByPrimaryKey("PaymentGroup", UtilMisc.toMap("paymentGroupId", paymentId));

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPaymentWorker.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return genValue;
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToPaymentCancelled(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Payment payment = paymentComposite.getPayment();
        String paymentId = payment.getpaymentId();
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("setPaymentStatus", UtilMisc.toMap("userLogin", userLogin, "paymentId", paymentId, "statusId", "PMNT_CANCELLED"));
        if (OrderMaxUtility.handleServiceReturn("Cancel Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            paymentComposite.setPayment(new Payment(genValue));
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }

    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToPaymentReceived(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Payment payment = paymentComposite.getPayment();
        String paymentId = payment.getpaymentId();
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("setPaymentStatus", UtilMisc.toMap("userLogin", userLogin, "paymentId", paymentId, "statusId", "PMNT_RECEIVED"));
        if (OrderMaxUtility.handleServiceReturn("Receive Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            paymentComposite.setPayment(new Payment(genValue));
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToPaymentSent(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Payment payment = paymentComposite.getPayment();
        String paymentId = payment.getpaymentId();
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("setPaymentStatus", UtilMisc.toMap("userLogin", userLogin, "paymentId", paymentId, "statusId", "PMNT_SENT"));
        if (OrderMaxUtility.handleServiceReturn("Sent Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            paymentComposite.setPayment(new Payment(genValue));
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToPaymentNotPaid(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Payment payment = paymentComposite.getPayment();
        String paymentId = payment.getpaymentId();
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("setPaymentStatus", UtilMisc.toMap("userLogin", userLogin, "paymentId", paymentId, "statusId", "PMNT_NOT_PAID"));
        if (OrderMaxUtility.handleServiceReturn("Not Paid Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            paymentComposite.setPayment(new Payment(genValue));
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public OrderMaxUtility.ServiceReturnStatus voidPayment(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Payment payment = paymentComposite.getPayment();
        String paymentId = payment.getpaymentId();
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("voidPayment", UtilMisc.toMap("userLogin", userLogin, "paymentId", paymentId));
        if (OrderMaxUtility.handleServiceReturn("Void Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            paymentComposite.setPayment(new Payment(genValue));
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public List<GenericValue> getPaymentGroupList(Map<String, Object> searchCondMap, final XuiSession session) {
        List<GenericValue> pastDueInvoices = FastList.newInstance();
        try {
            //GenericValue userLogin = session.getUserLogin();
            //LocalDispatcher dispatcher = session.getDispatcher();
            //Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            //EntityConditionList<EntityCondition> conditions = null;

            List<EntityExpr> exprs = FastList.newInstance();
            for (Map.Entry<String, Object> entry : searchCondMap.entrySet()) {
                exprs.add(EntityCondition.makeCondition(entry.getKey(), EntityOperator.EQUALS, entry.getValue()));
                Debug.logInfo(entry.getKey() + " : " + entry.getValue(), module);
            }

            pastDueInvoices = session.getDelegator().findList("PaymentGroup", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, UtilMisc.toList("paymentGroupId DESC"), null, false);

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pastDueInvoices;
    }
    
    /*
     @Override
     protected void process(List<Person> chunks) {
     personListModel.addAll(chunks);
     progressedItems = progressedItems + chunks.size();
     setProgress(calcProgress(progressedItems));
     }

     private int calcProgress(int progressedItems) {
     int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
     return progress;
     }

     private void sleepAWhile() {
     try {
     Thread.yield();
     long timeToSleep = getTimeToSleep();
     Thread.sleep(timeToSleep);
     } catch (InterruptedException e) {
     }
     }

     private long getTimeToSleep() {
     return loadPersonsSpeedModel.getValue();
     }

     public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
     this.loadPersonsSpeedModel = loadPersonsSpeedModel;

     }
    
     private ClassLoader getClassLoader() {
     Debug.logInfo("class loader 1", module);
     ClassLoader cl = null;
     try {
     cl = session.getClassLoader();
     Debug.logInfo("class loader 2", module);
     if (cl == null) {
     try {
     Debug.logInfo("class loader 3", module);
     cl = Thread.currentThread().getContextClassLoader();
     Debug.logInfo("class loader 4", module);
     } catch (Throwable t) {
     Debug.logInfo("class loader 5", module);
     }
     if (cl == null) {
     Debug.log("No context classloader available; using class classloader", module);
     try {
     cl = this.getClass().getClassLoader();
     } catch (Throwable t) {
     Debug.logError(t, module);
     }
     }
     }
     } catch (Exception e) {
     Debug.logError(e, module);
     }
     return cl;
     }
     */
    static public PaymentMethod getPaymentMethod(String paymentMethodId, String partyId, XuiSession session) {
        PaymentMethod paymentMethod = null;
        try {
            GenericValue pm = session.getDelegator().findByPrimaryKey("PaymentMethod", UtilMisc.toMap("paymentMethodId", paymentMethodId));
            paymentMethod = new PaymentMethod(pm);

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPaymentWorker.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return paymentMethod;
    }

    public static Map<String, Map<String, GenericValue>> getPartyPaymentMethodValueMaps(Delegator delegator, String partyId, String paymentMethodTypeId, Boolean showOld) {
        Map<String, Map<String, GenericValue>> paymentMethodValueMaps = FastMap.newInstance();
        try {
            List<GenericValue> paymentMethods = delegator.findByAnd("PaymentMethod", UtilMisc.toMap("partyId", partyId, "paymentMethodTypeId", paymentMethodTypeId));

            if (!showOld) {
                paymentMethods = EntityUtil.filterByDate(paymentMethods, true);
            }

            for (GenericValue paymentMethod : paymentMethods) {
                Map<String, GenericValue> valueMap = FastMap.newInstance();

                paymentMethodValueMaps.put(paymentMethod.getString("paymentMethodId"), valueMap);
                valueMap.put("paymentMethod", paymentMethod);
                if ("CREDIT_CARD".equals(paymentMethod.getString("paymentMethodTypeId"))) {
                    GenericValue creditCard = paymentMethod.getRelatedOne("CreditCard");
                    if (creditCard != null) {
                        Debug.logInfo("paymentMethodTypeId: " + paymentMethod.getString("paymentMethodTypeId"), module);
                        valueMap.put(paymentMethod.getString("paymentMethodId"), creditCard);
                    }
                } else if ("GIFT_CARD".equals(paymentMethod.getString("paymentMethodTypeId"))) {
                    GenericValue giftCard = paymentMethod.getRelatedOne("GiftCard");
                    if (giftCard != null) {
                        valueMap.put(paymentMethod.getString("paymentMethodTypeId"), giftCard);
                    }
                } else if ("EFT_ACCOUNT".equals(paymentMethod.getString("paymentMethodTypeId"))
                        || "COMPANY_CHECK".equals(paymentMethod.getString("paymentMethodTypeId"))
                        || "CERTIFIED_CHECK".equals(paymentMethod.getString("paymentMethodTypeId"))
                        || "PERSONAL_CHECK".equals(paymentMethod.getString("paymentMethodTypeId"))) {
                    GenericValue eftAccount = paymentMethod.getRelatedOne("EftAccount");
                    if (eftAccount != null) {
                        valueMap.put(paymentMethod.getString("paymentMethodTypeId"), eftAccount);
                    }
                }
            }
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        return paymentMethodValueMaps;
    }

    public static String createPaymentGroup(PaymentGroup paymentGroup) throws Exception {

        LocalDispatcher dispatcher = ControllerOptions.getSession().getDispatcher();
//        Delegator delegator = ControllerOptions.getSession().getDelegator();
//        Locale locale = Locale.getDefault();        
        GenericValue userLogin = ControllerOptions.getSession().getUserLogin();
        List<String> paymentIdValues = new ArrayList<String>();
//        for(String paymentId: paymentIds ){
        //   GenericValue val =  loadPaymentGV(paymentId, ControllerOptions.getSession());
//            paymentIdValues.add(paymentId);
        //       }

        Map<String, Object> request = new HashMap<String, Object>();
//        request.put("paymentIds", paymentIdValues);
        request.put("paymentGroupTypeId", "BATCH_PAYMENT");
        request.put("paymentGroupName", "multiple payments");
        request.put("userLogin", userLogin);
        String paymentGroupId = null;
// create a payment record
        Map<String, Object> results = null;
        try {
            results = dispatcher.runSync("createPaymentGroup", request);
            if (OrderMaxUtility.handleServiceReturn("Create Payment From Preference", results) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                paymentGroupId = (String) results.get("paymentGroupId");
            }
        } catch (GenericServiceException e) {
            Debug.logError(e, "Failed to execute service createPaymentFromPreference", module);
            request.put("_ERROR_MESSAGE_", e.getMessage());
            //         return paymentResultMap;
        }

        if ((results == null) || (results.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_ERROR))) {
            Debug.logError((String) results.get(ModelService.ERROR_MESSAGE), module);
            request.put("_ERROR_MESSAGE_", results.get(ModelService.ERROR_MESSAGE));
            //        return paymentResultMap;
        }

        return paymentGroupId;
        //"createPaymentGroupAndMember" in-map-name="createPaymentGroupAndMemberMap"
    }

    public static String createPaymentGroupMembers(PaymentGroup paymentGroup, Set<String> paymentIds) throws Exception {

        LocalDispatcher dispatcher = ControllerOptions.getSession().getDispatcher();
        String paymentGroupId = null;
//        Delegator delegator = ControllerOptions.getSession().getDelegator();
//        Locale locale = Locale.getDefault();        
        GenericValue userLogin = ControllerOptions.getSession().getUserLogin();
        List<GenericValue> paymentIdValues = new ArrayList<GenericValue>();
        GenericValue groupVal = loadPaymentGroupGV(paymentGroup.getPaymentGroupId(), ControllerOptions.getSession());
        for (String paymentId : paymentIds) {
            GenericValue val = loadPaymentGV(paymentId, ControllerOptions.getSession());
            Debug.logInfo("Payment Id: " + paymentId, "test");
            Debug.logInfo("Payment Group Id: " + paymentGroup.getPaymentGroupId(), "test");
            paymentIdValues.add(val);

            Map<String, Object> request = new HashMap<String, Object>();
            //request.put("paymentGroup",groupVal);
            //request.put("payment", val);
            request.put("paymentGroupId", paymentGroup.getPaymentGroupId());
            request.put("paymentId", paymentId);

            //request.put("paymentGroupName", "multiple payments");
            request.put("userLogin", userLogin);

// create a payment record
            Map<String, Object> results = null;
            try {
                results = dispatcher.runSync("createPaymentGroupMember", request);
                if (OrderMaxUtility.handleServiceReturn("Create Payment From Preference", results) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    paymentGroupId = (String) results.get("paymentGroupId");
                }
            } catch (GenericServiceException e) {
                Debug.logError(e, "Failed to execute service createPaymentFromPreference", module);
                request.put("_ERROR_MESSAGE_", e.getMessage());
                //         return paymentResultMap;
            }

            if ((results == null) || (results.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_ERROR))) {
                Debug.logError((String) results.get(ModelService.ERROR_MESSAGE), module);
                request.put("_ERROR_MESSAGE_", results.get(ModelService.ERROR_MESSAGE));
                //        return paymentResultMap;
            }
        }
        return paymentGroupId;
        //"createPaymentGroupAndMember" in-map-name="createPaymentGroupAndMemberMap"
    }

    public static String createPaymentGroupAndMembers(PaymentGroup paymentGroup, Set<String> paymentIds) throws Exception {

        LocalDispatcher dispatcher = ControllerOptions.getSession().getDispatcher();
//        Delegator delegator = ControllerOptions.getSession().getDelegator();
//        Locale locale = Locale.getDefault();        
        GenericValue userLogin = ControllerOptions.getSession().getUserLogin();
        List<String> paymentIdValues = new ArrayList<String>();
        for (String paymentId : paymentIds) {
            //   GenericValue val =  loadPaymentGV(paymentId, ControllerOptions.getSession());
            paymentIdValues.add(paymentId);
        }

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("paymentIds", paymentIdValues);
        request.put("paymentGroupTypeId", "BATCH_PAYMENT");
        request.put("paymentGroupName", "multiple payments");
        request.put("userLogin", userLogin);
        String paymentGroupId = null;
// create a payment record
        Map<String, Object> results = null;
        try {
            results = dispatcher.runSync("createPaymentGroupAndMember", request);
            if (OrderMaxUtility.handleServiceReturn("Create Payment From Preference", results) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                paymentGroupId = (String) results.get("paymentGroupId");
            }
        } catch (GenericServiceException e) {
            Debug.logError(e, "Failed to execute service createPaymentFromPreference", module);
            request.put("_ERROR_MESSAGE_", e.getMessage());
            //         return paymentResultMap;
        }

        if ((results == null) || (results.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_ERROR))) {
            Debug.logError((String) results.get(ModelService.ERROR_MESSAGE), module);
            request.put("_ERROR_MESSAGE_", results.get(ModelService.ERROR_MESSAGE));
            //        return paymentResultMap;
        }

        return paymentGroupId;
        //"createPaymentGroupAndMember" in-map-name="createPaymentGroupAndMemberMap"
    }

    public static Map<String, String> receiveOfflinePayment(Map<String, Object> request, XuiSession session) throws Exception {

        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        GenericValue userLogin = session.getUserLogin();
        Locale locale = Locale.getDefault();
        String orderId = (String) request.get("orderId");
        Map<String, String> paymentResultMap = FastMap.newInstance();

        // get the order header & payment preferences
        GenericValue orderHeader = null;
        try {
            orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
        } catch (GenericEntityException e) {
            Debug.logError(e, "Problems reading order header from datasource.", module);
            request.put("_ERROR_MESSAGE_", UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderProblemsReadingOrderHeaderInformation", locale));
            return paymentResultMap;
        }

        BigDecimal grandTotal = BigDecimal.ZERO;
        if (orderHeader != null) {
            grandTotal = orderHeader.getBigDecimal("grandTotal");
        }

        // get the payment types to receive
        List<GenericValue> paymentMethodTypes = null;

        try {
            EntityExpr ee = EntityCondition.makeCondition("paymentMethodTypeId", EntityOperator.NOT_EQUAL, "EXT_OFFLINE");
            paymentMethodTypes = delegator.findList("PaymentMethodType", ee, null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Problems getting payment types", module);
            request.put("_ERROR_MESSAGE_", UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderProblemsWithPaymentTypeLookup", locale));
            return paymentResultMap;
        }

        if (paymentMethodTypes == null) {
            request.put("_ERROR_MESSAGE_", UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderProblemsWithPaymentTypeLookup", locale));
            return paymentResultMap;
        }

        List<GenericValue> toBeStored = FastList.newInstance();
        GenericValue placingCustomer = null;
        try {
            List<GenericValue> pRoles = delegator.findByAnd("OrderRole", UtilMisc.toMap("orderId", orderId, "roleTypeId", "PLACING_CUSTOMER"));
            if (UtilValidate.isNotEmpty(pRoles)) {
                placingCustomer = EntityUtil.getFirst(pRoles);
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, "Problems looking up order payment preferences", module);
            request.put("_ERROR_MESSAGE_", UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderErrorProcessingOfflinePayments", locale));
            return paymentResultMap;
        }

        for (GenericValue paymentMethodType : paymentMethodTypes) {
            String paymentMethodTypeId = paymentMethodType.getString("paymentMethodTypeId");
            String amountStr = (String) request.get(paymentMethodTypeId + "_amount");
            String paymentReference = (String) request.get(paymentMethodTypeId + "_reference");
            if (!UtilValidate.isEmpty(amountStr)) {
                BigDecimal paymentTypeAmount = BigDecimal.ZERO;
                try {
                    paymentTypeAmount = (BigDecimal) ObjectType.simpleTypeConvert(amountStr, "BigDecimal", null, locale);
                } catch (GeneralException e) {
                    request.put("_ERROR_MESSAGE_", UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderProblemsPaymentParsingAmount", locale));
                    return paymentResultMap;
                }

                if (paymentTypeAmount.compareTo(BigDecimal.ZERO) > 0) {

                    // create the OrderPaymentPreference
                    // TODO: this should be done with a service
                    Map<String, String> prefFields = UtilMisc.<String, String>toMap("orderPaymentPreferenceId", delegator.getNextSeqId("OrderPaymentPreference"));
                    GenericValue paymentPreference = delegator.makeValue("OrderPaymentPreference", prefFields);
                    paymentPreference.set("paymentMethodTypeId", paymentMethodType.getString("paymentMethodTypeId"));
                    paymentPreference.set("maxAmount", paymentTypeAmount);
                    paymentPreference.set("statusId", "PAYMENT_RECEIVED");
                    paymentPreference.set("orderId", orderId);
                    paymentPreference.set("createdDate", UtilDateTime.nowTimestamp());
                    if (userLogin != null) {
                        paymentPreference.set("createdByUserLogin", userLogin.getString("userLoginId"));
                    }

                    try {
                        delegator.create(paymentPreference);
                    } catch (GenericEntityException ex) {
                        Debug.logError(ex, "Cannot create a new OrderPaymentPreference", module);
                        request.put("_ERROR_MESSAGE_", ex.getMessage());
                        return paymentResultMap;
                    }

                    // create a payment record
                    Map<String, Object> results = null;
                    try {
                        results = dispatcher.runSync("createPaymentFromPreference", UtilMisc.toMap("userLogin", userLogin,
                                "orderPaymentPreferenceId", paymentPreference.get("orderPaymentPreferenceId"), "paymentRefNum", paymentReference,
                                "paymentFromId", placingCustomer.getString("partyId"), "comments", "Payment received offline and manually entered."));
                        if (OrderMaxUtility.handleServiceReturn("Create Payment From Preference", results) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                            String paymentId = (String) results.get("paymentId");
                            paymentResultMap.put(paymentMethodTypeId, paymentId);
                        }
                    } catch (GenericServiceException e) {
                        Debug.logError(e, "Failed to execute service createPaymentFromPreference", module);
                        request.put("_ERROR_MESSAGE_", e.getMessage());
                        return paymentResultMap;
                    }

                    if ((results == null) || (results.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_ERROR))) {
                        Debug.logError((String) results.get(ModelService.ERROR_MESSAGE), module);
                        request.put("_ERROR_MESSAGE_", results.get(ModelService.ERROR_MESSAGE));
                        return paymentResultMap;
                    }
                }
            }
        }

        // get the current payment prefs
        GenericValue offlineValue = null;
        List<GenericValue> currentPrefs = null;
        BigDecimal paymentTally = BigDecimal.ZERO;
        try {
            EntityConditionList<EntityExpr> ecl = EntityCondition.makeCondition(UtilMisc.toList(
                    EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId),
                    EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "PAYMENT_CANCELLED")),
                    EntityOperator.AND);
            currentPrefs = delegator.findList("OrderPaymentPreference", ecl, null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, "ERROR: Unable to get existing payment preferences from order", module);
        }

        if (UtilValidate.isNotEmpty(currentPrefs)) {
            for (GenericValue cp : currentPrefs) {
                String paymentMethodType = cp.getString("paymentMethodTypeId");
                if ("EXT_OFFLINE".equals(paymentMethodType)) {
                    offlineValue = cp;
                } else {
                    BigDecimal cpAmt = cp.getBigDecimal("maxAmount");
                    if (cpAmt != null) {
                        paymentTally = paymentTally.add(cpAmt);
                    }
                }
            }
        }

        // now finish up
        boolean okayToApprove = false;
        if (paymentTally.compareTo(grandTotal) >= 0) {
            // cancel the offline preference
            okayToApprove = true;
            if (offlineValue != null) {
                offlineValue.set("statusId", "PAYMENT_CANCELLED");
                toBeStored.add(offlineValue);
            }
        }

        // store the status changes and the newly created payment preferences and payments
        // TODO: updating order payment preference should be done with a service
        try {
            delegator.storeAll(toBeStored);
        } catch (GenericEntityException e) {
            Debug.logError(e, "Problems storing payment information", module);
            request.put("_ERROR_MESSAGE_", UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderProblemStoringReceivedPaymentInformation", locale));
            return paymentResultMap;
        }

        if (okayToApprove) {
            // update the status of the order and items
            OrderChangeHelper.approveOrder(dispatcher, userLogin, orderId);
        }

        return paymentResultMap;
    }

    static public String createPaymentGroupAndMember(Map<String, Object> inMap, XuiSession session) throws Exception {
        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        inMap.put("userLogin", userLogin);
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("createPaymentGroupAndMember", inMap);
        if (OrderMaxUtility.handleServiceReturn("Create Billing Account Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            if (paymentResults.containsKey("paymentGroupId")) {
                return (String) paymentResults.get("paymentGroupId");
            }
        }
        return null;

    }

    static public String createPaymentAndApplication(Map<String, Object> inMap, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        inMap.put("userLogin", userLogin);
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("createPaymentAndApplication", inMap);
        if (OrderMaxUtility.handleServiceReturn("Biilling Account Payment", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            if (paymentResults.containsKey("paymentId")) {
                return (String) paymentResults.get("paymentId");
            }
        }
        return null;

    }

    static public OrderMaxUtility.ServiceReturnStatus capturePaymentsByInvoice(Map<String, Object> inMap, XuiSession session) throws Exception {

        // create the return header
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        inMap.put("userLogin", userLogin);
        // approve the return
        Map<String, Object> paymentResults = dispatcher.runSync("capturePaymentsByInvoice", inMap);
        if (OrderMaxUtility.handleServiceReturn("Capture Payments By Invoice", paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            //GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
            //paymentComposite.setPayment(new Payment(genValue));
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }
}
