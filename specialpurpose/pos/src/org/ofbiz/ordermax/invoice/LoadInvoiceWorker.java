/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastList;
import javolution.util.FastMap;
import mvc.controller.LoadAccountWorker;
import mvc.controller.LoadBaseSwingWorker;
import mvc.controller.LoadPurchaseOrderWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.InvoiceContact;
import org.ofbiz.ordermax.composite.InvoiceContactList;
import org.ofbiz.ordermax.composite.InvoiceItemCompositeList;
import org.ofbiz.ordermax.composite.InvoiceRolesList;
import org.ofbiz.ordermax.composite.InvoiceStatusList;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemBillingsList;
import org.ofbiz.ordermax.composite.InvoiceItemComposite;
import org.ofbiz.ordermax.composite.InvoiceRoleComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupAssocsList;
import org.ofbiz.ordermax.composite.OrderStatusList;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.entity.Invoice;
import org.ofbiz.ordermax.entity.InvoiceContactMech;
import org.ofbiz.ordermax.entity.InvoiceItem;
import org.ofbiz.ordermax.entity.InvoiceRole;
import org.ofbiz.ordermax.entity.InvoiceStatus;
import org.ofbiz.ordermax.entity.OrderStatus;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadInvoiceWorker extends LoadBaseSwingWorker< InvoiceComposite> {

    public LoadInvoiceWorker(ListAdapterListModel<InvoiceComposite> invoiceListModel, XuiSession session, Map<String, Object> findOptionMap) {
        super(invoiceListModel, findOptionMap);
        this.session = session;

    }

    public LoadInvoiceWorker(ListAdapterListModel<InvoiceComposite> invoiceListModel, XuiSession session, List<EntityCondition> entityConditionList) {
        super(invoiceListModel, entityConditionList);
        this.session = session;
    }

    public LoadInvoiceWorker(ListAdapterListModel<InvoiceComposite> invoiceListModel, XuiSession session) {
        super(invoiceListModel, new ArrayList<EntityCondition>());
        this.session = session;

    }

    @Override
    protected List<InvoiceComposite> doInBackground() throws Exception {

        /*    List<InvoiceComposite> dataList = new ArrayList<InvoiceComposite>();
         Map<String, Object> result = null;
         boolean beganTransaction = false;
         try {
         beganTransaction = TransactionUtil.begin();
         try {

         result = session.getDispatcher().runSync("performFind",
         UtilMisc.<String, Object>toMap("entityName", "Invoice",
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
         Invoice inv = new Invoice(gv);
         InvoiceComposite comp = new InvoiceComposite();
         comp.setInvoice(inv);
         comp.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
         comp.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
         comp.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
         comp.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));
         comp.setPaidAmount(InvoiceWorker.getInvoiceApplied(inv.getGenericValueObj()));
         dataList.add(comp);
         //                        int prograss = calcProgress(progressedItems + 1);

         //                        if ((prograss + 1) % 4 == 0) {
         publish(comp);
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
         */
        List<InvoiceComposite> dataList = new ArrayList<InvoiceComposite>();
        Map<String, Object> result = null;

        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("Invoice", entityConditionList, "-invoiceDate", session.getDelegator(), true);
        Debug.logInfo("resultList " + resultList.size(), module);
        maxProgress = resultList.size();
//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gv : resultList) {
            Invoice inv = new Invoice(gv);
            InvoiceComposite comp = new InvoiceComposite();
            comp.setInvoice(inv);
            comp.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
            comp.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
            comp.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
            comp.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));
            comp.setPaidAmount(InvoiceWorker.getInvoiceApplied(inv.getGenericValueObj()));
            dataList.add(comp);
//                        int prograss = calcProgress(progressedItems + 1);

//                        if ((prograss + 1) % 4 == 0) {
            publish(comp);
//                        } else {
//                            progressedItems++;
//                        }

            if (isCancelled()) {
                break;
            }
        }

        return dataList;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    private String partyId = "";

    static public List<InvoiceComposite> loadOutstandingInvoices(String partyId, String invoiceTypeId, XuiSession session) throws Exception {

        List<InvoiceComposite> dataList = new ArrayList<InvoiceComposite>();

        //set the class loader
//        getClassLoader();
        //      final ClassLoader cl = this.getClassLoader();
        //      Thread.currentThread().setContextClassLoader(cl);
        List<GenericValue> invoiceListGV = getOutstandingInvoiceList(partyId, invoiceTypeId, session);

        List<Invoice> invoiceList = getInvoiceConvertList(invoiceListGV);

        List<String> invoiceIdStrList = new ArrayList<String>();
//        InvoiceComposite
        for (Invoice inv : invoiceList) {
            InvoiceComposite comp = new InvoiceComposite();
            comp.setInvoice(inv);
            comp.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
            comp.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
            comp.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
            comp.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));
            invoiceIdStrList.add(inv.getinvoiceId());
            dataList.add(comp);

        }
        String outStandingTotal = getInvoiceFinancials(invoiceIdStrList, session);
        Debug.logInfo("outStandingTotal: " + outStandingTotal, module);
        return dataList;
    }

    static public List<InvoiceComposite> loadInvoices(Map<String, Object> searchCondMap, XuiSession session, boolean loadZeroAmount) throws Exception {

        List<InvoiceComposite> dataList = new ArrayList<InvoiceComposite>();

        List<GenericValue> invoiceListGV = getInvoiceList(searchCondMap, session);

        List<Invoice> invoiceList = getInvoiceConvertList(invoiceListGV);

        List<String> invoiceIdStrList = new ArrayList<String>();
//        InvoiceComposite
        for (Invoice inv : invoiceList) {
            InvoiceComposite comp = new InvoiceComposite();
            comp.setInvoice(inv);
            comp.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
            comp.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
            comp.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
            comp.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));
            if (loadZeroAmount) {
                invoiceIdStrList.add(inv.getinvoiceId());
                dataList.add(comp);
            } else {
                if (comp.getOutstandingAmount().compareTo(BigDecimal.ZERO) != 0) {
                    invoiceIdStrList.add(inv.getinvoiceId());
                    dataList.add(comp);
                }
            }

        }
        String outStandingTotal = getInvoiceFinancials(invoiceIdStrList, session);
        Debug.logInfo("outStandingTotal: " + outStandingTotal, module);
        return dataList;
    }

    static public List<InvoiceComposite> loadInvoicesWithDetail(Map<String, Object> searchCondMap, XuiSession session) throws Exception {

        List<InvoiceComposite> dataList = new ArrayList<InvoiceComposite>();

        List<GenericValue> invoiceListGV = getInvoiceList(searchCondMap, session);
        for (GenericValue inv : invoiceListGV) {
            InvoiceComposite invoiceComposite = loadInvoice(inv.getString("invoiceId"), session);
            dataList.add(invoiceComposite);
        }

        return dataList;
    }

    static public InvoiceComposite loadInvoice(String invoiceId, XuiSession session) throws Exception {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Map<String, Object> conditionMap = ServiceUtil.returnSuccess();
        conditionMap.put("invoiceId", invoiceId);

        GenericValue invoiceValue = null;
        List<GenericValue> invoiceItems = new ArrayList<GenericValue>();
        InvoiceComposite invoiceComposite = new InvoiceComposite();

        try {

            resultMap = dispatcher.runSync("getInvoice", UtilMisc.toMap(
                    "invoiceId", invoiceId,
                    "userLogin", userLogin));

            if (OrderMaxUtility.handleServiceReturn("Load Invoice", resultMap) == OrderMaxUtility.ServiceReturnStatus.SUCCESS
                    && resultMap.containsKey("invoice")) {
                invoiceValue = (GenericValue) resultMap.get("invoice");
                Invoice inv = new Invoice(invoiceValue);
                invoiceComposite.setInvoice(inv);
                invoiceComposite.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
                invoiceComposite.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
                invoiceComposite.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
                invoiceComposite.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));

                //get all roles so we can get parties related to this order
                ArrayList<InvoiceRoleComposite> array = new ArrayList<InvoiceRoleComposite>();

                List<GenericValue> invoiceRoleList = invoiceValue.getRelated("InvoiceRole");
                for (GenericValue invoiceRoleGen : invoiceRoleList) {
                    //create order role
                    InvoiceRole invoiceRole = new InvoiceRole(invoiceRoleGen);
                    //crreate the order composite
                    InvoiceRoleComposite invoiceRoleComp = new InvoiceRoleComposite();
                    Debug.logInfo("invoiceRoleId: " + invoiceRole.getroleTypeId() + " invoiceRole.getpartyId(): " + invoiceRole.getpartyId(), module);
//                    Debug.logInfo("invoiceRole.getpartyId(): " + invoiceRole.getpartyId(), module);
                    //get party details
                    Account acct = PartyListSingleton.getAccount(invoiceRole.getpartyId());
                    invoiceRoleComp.setInvoiceRole(invoiceRole);
                    invoiceRoleComp.setParty(acct);
                    //add to list
                    array.add(invoiceRoleComp);
                }

                //let set order composite to order
                InvoiceRolesList rolesList = new InvoiceRolesList();
                rolesList.addAll(array);

                //set it to order
                invoiceComposite.setInvoiceRolesList(rolesList);//.setOrderRolesList(rolesList);

                //order status
                List<GenericValue> stausList = PosProductHelper.getGenericValueLists("InvoiceStatus", "invoiceId", invoiceId, session.getDelegator());
                invoiceComposite.setInvoiceStatusesList(new InvoiceStatusList());
                for (GenericValue status : stausList) {
                    invoiceComposite.getInvoiceStatusesList().add(new InvoiceStatus(status));
                }
            }

            if (resultMap.containsKey("invoiceItems")) {
                invoiceItems = (List<GenericValue>) resultMap.get("invoiceItems");
                InvoiceItemCompositeList iicList = new InvoiceItemCompositeList();
                for (GenericValue gv : invoiceItems) {
                    iicList.add(new InvoiceItemComposite(gv));
                }
                invoiceComposite.setInvoiceItemCompositeList(iicList);
            }

        } catch (Exception exc) {
            Debug.logWarning("Unable to load invoice:" + invoiceId + "  with error: " + exc.getMessage(), module);
        }

        return invoiceComposite;
    }

    public static InvoiceContactList loadInvoiceContactList(final String invoiceId, XuiSession session) {

        InvoiceContactList ocl = new InvoiceContactList();
        try {
            //get order genericvalue
            GenericValue orderGenVal = PosProductHelper.getGenericValueByKey("Invoice", UtilMisc.toMap("invoiceId", invoiceId), session.getDelegator());

            //get all roles so we can get parties related to this order
            ArrayList<InvoiceRoleComposite> array = new ArrayList<InvoiceRoleComposite>();

            List<GenericValue> orderRoleList = orderGenVal.getRelated("InvoiceRole");
            for (GenericValue orderRoleGen : orderRoleList) {
                //create order role
                InvoiceRole invoiceRole = new InvoiceRole(orderRoleGen);
                //crreate the order composite
                InvoiceRoleComposite invoiceRoleComp = new InvoiceRoleComposite();

                //get party details
                Account acct = LoadAccountWorker.getAccount(invoiceRole.getpartyId(), session);
                invoiceRoleComp.setInvoiceRole(invoiceRole);
                invoiceRoleComp.setParty(acct);
                //add to list
                array.add(invoiceRoleComp);
            }

            //let set order composite to order
            InvoiceRolesList rolesList = new InvoiceRolesList();
            rolesList.addAll(array);

            //get order contacts            
            ArrayList<InvoiceContact> oclArray = new ArrayList<InvoiceContact>();

            List<GenericValue> orderContachMapList = orderGenVal.getRelated("InvoiceContactMech");
            for (GenericValue orderContactMech : orderContachMapList) {
                InvoiceContact oc = new InvoiceContact();
                InvoiceContactMech ocm = new InvoiceContactMech(orderContactMech);
                oc.setInvoiceContactMech(ocm);
                Debug.logInfo("ocm.getcontactMechId() " + ocm.getcontactMechId(), module);
                PartyContactMechComposite pc = rolesList.getPartyContact(ocm.getcontactMechId());
                if (pc != null) {
                oc.setContact(pc.getContact());
                oclArray.add(oc);
                }
            }

            ocl.addAll(oclArray);

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ocl;
    }

    static public void generateInvoice(Order order, final XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        String orderId = order.getOrderId();
        String shipGroupSeqId = order.shipGroupSeqId;
        Delegator delegator = session.getDelegator();
        GenericValue facility = PosProductHelper.getFacility(order.getFacilityId(), delegator);

        Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ start btnGnerateInvoiceActionPerformed:", module);
        if (UtilValidate.isNotEmpty(orderId)) {
            try {

                resultMap = dispatcher.runSync("createInvoiceForOrderAllItems", UtilMisc.toMap("orderId", order.getOrderId(), "userLogin", userLogin));
                if (resultMap.containsKey("responseMessage")) {
                    if ("success".equals(resultMap.get("responseMessage"))) {
                        if (resultMap.containsKey("invoiceId")) {
                            order.getInvoiceIds().add(resultMap.get("invoiceId").toString());
                        }
                    }
                }

                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
            } catch (Exception exc) {
                Debug.logWarning("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
            }
        }

        Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END  btnGnerateInvoiceActionPerformed:", module);

    }

    static public List<GenericValue> getOutstandingInvoiceList(String partyId, String invoiceTypeId, final XuiSession session) {
        List<GenericValue> pastDueInvoices = FastList.newInstance();
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();

            org.ofbiz.entity.condition.EntityConditionBuilder exprBldr = new org.ofbiz.entity.condition.EntityConditionBuilder();

            // lookup payment applications which took place before the asOfDateTime for this invoice
            EntityConditionList<EntityExpr> statusCondition = EntityCondition.makeCondition(UtilMisc.toList(
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_RECEIVED"),
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_IN_PROCESS"),
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_READY"),
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_APPROVED")), EntityOperator.OR);

            EntityConditionList<EntityCondition> conditions = null;
            if (partyId == null) {
                conditions = EntityCondition.makeCondition(UtilMisc.toList(
                        statusCondition,
                        EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId)),
                        EntityOperator.AND);
            } else {

                conditions = EntityCondition.makeCondition(UtilMisc.toList(
                        statusCondition,
                        EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId),
                        "PURCHASE_INVOICE".equals(invoiceTypeId)
                        ? EntityCondition.makeCondition("partyIdFrom", EntityOperator.EQUALS, partyId)
                        : EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId)
                ),
                        EntityOperator.AND);

            }
            pastDueInvoices = session.getDelegator().findList("Invoice", conditions, null, UtilMisc.toList("dueDate DESC"), null, false);
            if (pastDueInvoices != null) {
                for (GenericValue val : pastDueInvoices) {
                    Debug.logInfo(val.getString("invoiceId"), module);
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pastDueInvoices;
    }

    static public List<GenericValue> getInvoiceList(Map<String, Object> searchCondMap, final XuiSession session) {
        List<GenericValue> pastDueInvoices = FastList.newInstance();
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            EntityConditionList<EntityCondition> conditions = null;
            /*
             org.ofbiz.entity.condition.EntityConditionBuilder exprBldr = new org.ofbiz.entity.condition.EntityConditionBuilder();

             // lookup payment applications which took place before the asOfDateTime for this invoice
             EntityConditionList<EntityExpr> statusCondition = EntityCondition.makeCondition(UtilMisc.toList(
             EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_RECEIVED"),
             EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_IN_PROCESS"),
             EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_READY"),
             EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_APPROVED")), EntityOperator.OR);

             EntityConditionList<EntityCondition> conditions = null;
             if(partyId==null){
             conditions = EntityCondition.makeCondition(UtilMisc.toList(
             statusCondition,
             EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId)),
             EntityOperator.AND);
             }
             else{
                
             conditions = EntityCondition.makeCondition(UtilMisc.toList(
             statusCondition,
             EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId),
             "PURCHASE_INVOICE".equals(invoiceTypeId) ?
             EntityCondition.makeCondition("partyIdFrom", EntityOperator.EQUALS, partyId)
             : EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId)
             ),                        
             EntityOperator.AND);
                
             }
             */
            List<EntityExpr> exprs = FastList.newInstance();
            for (Map.Entry<String, Object> entry : searchCondMap.entrySet()) {
                exprs.add(EntityCondition.makeCondition(entry.getKey(), EntityOperator.EQUALS, entry.getValue()));
                Debug.logInfo(entry.getKey() + " : " + entry.getValue(), module);
            }

            pastDueInvoices = session.getDelegator().findList("Invoice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, UtilMisc.toList("dueDate DESC"), null, false);

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pastDueInvoices;
    }

    static public List<GenericValue> getOutstandingSalesInvoiceList(String partyId, final XuiSession session) {
        List<GenericValue> pastDueInvoices = FastList.newInstance();
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            String invoiceTypeId = "SALES_INVOICE";
            org.ofbiz.entity.condition.EntityConditionBuilder exprBldr = new org.ofbiz.entity.condition.EntityConditionBuilder();

            // lookup payment applications which took place before the asOfDateTime for this invoice
            EntityConditionList<EntityExpr> statusCondition = EntityCondition.makeCondition(UtilMisc.toList(
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_RECEIVED"),
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_IN_PROCESS"),
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_READY"),
                    EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, "INVOICE_APPROVED")), EntityOperator.OR);

            EntityConditionList<EntityCondition> conditions = null;
            if (partyId == null) {
                conditions = EntityCondition.makeCondition(UtilMisc.toList(
                        statusCondition,
                        EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId)),
                        EntityOperator.AND);
            } else {
                conditions = EntityCondition.makeCondition(UtilMisc.toList(
                        statusCondition,
                        EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId),
                        EntityCondition.makeCondition("partyIdFrom", EntityOperator.EQUALS, partyId)),
                        EntityOperator.AND);

            }
            pastDueInvoices = session.getDelegator().findList("Invoice", conditions, null, UtilMisc.toList("dueDate DESC"), null, false);
            if (pastDueInvoices != null) {
                for (GenericValue val : pastDueInvoices) {
                    Debug.logInfo(val.getString("invoiceId"), module);
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pastDueInvoices;
    }

    static public String getInvoiceFinancials(List<String> invoiceIds, final XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        String totalValue = "";
        String partyId = session.getCompanyPartyId();
//        List<String> invoiceIds = new ArrayList<String>();
//        if (UtilValidate.isNotEmpty(inv)) {
//            invoiceIds.add(inv.getinvoiceId());
        try {

            resultMap = dispatcher.runSync("getInvoiceRunningTotal", UtilMisc.toMap(
                    "invoiceIds", invoiceIds,
                    "organizationPartyId", partyId,
                    "userLogin", userLogin));
            if (resultMap.containsKey("invoiceRunningTotal")) {
                totalValue = (String) resultMap.get("invoiceRunningTotal");
            }
            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                Debug.logInfo("Payment Info : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }
        } catch (Exception exc) {
            Debug.logWarning("Unable to quick ship test sales order with id  with error: " + exc.getMessage(), module);
        }
        //      }
        return totalValue;
    }

    static public List<Map<String, Object>> getInvoicePaymentList(GenericValue invoice, final XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
//        String orderId = order.getOrderHeader().getOrderId();
//        String shipGroupSeqId = order.shipGroupSeqId;
        Delegator delegator = session.getDelegator();
//        GenericValue facility = PosProductHelper.getFacility(order.getDestinationFacilityId(), delegator);

        Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ start btnGnerateInvoiceActionPerformed:", module);
        if (UtilValidate.isNotEmpty(invoice)) {
            try {

                resultMap = dispatcher.runSync("getInvoicePaymentInfoList", UtilMisc.toMap("invoice", invoice, "userLogin", userLogin));
                if (OrderMaxUtility.handleServiceReturn("Load Invoice Payment Info List", resultMap) == OrderMaxUtility.ServiceReturnStatus.SUCCESS
                        && resultMap.containsKey("invoicePaymentInfoList")) {

                    return (List<Map<String, Object>>) resultMap.get("invoicePaymentInfoList");

                }

            } catch (Exception exc) {
//                Debug.logWarning("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
            }
        }

        Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END  btnGnerateInvoiceActionPerformed:", module);

        return null;
    }

    static public List<Invoice> getInvoiceConvertList(List<GenericValue> invoiceListGV) {

        List<Invoice> invoiceList = FastList.newInstance();
        for (GenericValue inv : invoiceListGV) {
            invoiceList.add(new Invoice(inv));
        }

        return invoiceList;
    }

    static public List<InvoiceComposite> loadPurchaseOrderComposites(Set<String> invoiceIds, XuiSession session) {

        List<InvoiceComposite> invoiceList = new ArrayList<InvoiceComposite>();
        Debug.logInfo("invoiceIds size: " + invoiceIds.size(), module);
        Map<String, String> valMap = new HashMap<String, String>();
        for (String invId : invoiceIds) {
            try {
                valMap.put("invoiceId", invId);
                GenericValue genVal = PosProductHelper.getGenericValueByKey("Invoice", valMap, session.getDelegator());
                InvoiceComposite comp = new InvoiceComposite();
                Invoice inv = new Invoice(genVal);
                comp.setInvoice(new Invoice(genVal));
                comp.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
                comp.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
                comp.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
                comp.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));
                invoiceList.add(comp);
                Debug.logInfo("comp.getTotalAmount(): " + comp.getTotalAmount(), module);
            } catch (Exception ex) {
                Logger.getLogger(module).log(Level.SEVERE, null, ex);
            }
        }
        return invoiceList;
    }

    static public List<InvoiceComposite> loadSalesOrderComposites(Set<String> invoiceIds, XuiSession session) {

//        invoicesListModel.clear();
        List<InvoiceComposite> invoiceList = new ArrayList<InvoiceComposite>();
        Map<String, String> valMap = new HashMap<String, String>();
        for (String invId : invoiceIds) {
            try {
                valMap.put("invoiceId", invId);
                GenericValue genVal = PosProductHelper.getGenericValueByKey("Invoice", valMap, session.getDelegator());
                InvoiceComposite comp = new InvoiceComposite();
                Invoice inv = new Invoice(genVal);
                comp.setInvoice(new Invoice(genVal));
                comp.setPartyFrom(PartyListSingleton.getAccount(inv.getpartyIdFrom()));
                comp.setPartyTo(PartyListSingleton.getAccount(inv.getpartyId()));
                comp.setTotalAmount(InvoiceWorker.getInvoiceTotal(inv.getGenericValueObj()));
                comp.setOutstandingAmount(InvoiceWorker.getInvoiceNotApplied(inv.getGenericValueObj()));
                invoiceList.add(comp);
            } catch (Exception ex) {
                Logger.getLogger(module).log(Level.SEVERE, null, ex);
            }
        }
        return invoiceList;
    }

    static public void removeOrderItem(InvoiceComposite order, InvoiceItemComposite orderItemComposite, XuiSession session) throws CartItemModifyException {

        LocalDispatcher dispatcher = session.getDispatcher();

        Map<String, Object> svcCtx = FastMap.newInstance();
        svcCtx.put("userLogin", session.getUserLogin());
//Sur        svcCtx.put("orderId", orderItemComposite.getOrderItem().getorderId());
//        svcCtx.put("orderItemSeqId", orderItemComposite.getOrderItem().getorderItemSeqId());

        Map<String, Object> svcRes = null;
        try {
            svcRes = dispatcher.runSync("cancelOrderItem", svcCtx);
            for (Map.Entry<String, Object> entryDept : svcRes.entrySet()) {
                Debug.logInfo("key: " + entryDept.getKey()
                        + " value: " + entryDept.getValue(), module);

            }
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        }
        order.getInvoiceItemCompositeList().remove(orderItemComposite);
        /*
         ShoppingCart shopingCart = order.getShopingCart();
         Debug.logInfo("orderItemComposite.getShoppingCartItem(): " + orderItemComposite.getShoppingCartItem().getName(), module);
         shopingCart.removeCartItem(orderItemComposite.getShoppingCartItem(), session.getDispatcher());
         <attribute name="orderId" type="String" mode="IN" optional="false"/>
         <attribute name="orderItemSeqId" type="String" mode="IN" optional="true"/>
        

         */
    }

    static public InvoiceItemComposite newOrderItem(XuiSession session) {
        InvoiceItemComposite oic = new InvoiceItemComposite();

        InvoiceItem orderIt = new InvoiceItem();
//        orderIt.setstatusId("ITEM_CREATED");
        String orderItemTypeId = "PRODUCT_ORDER_ITEM";
//        orderIt.setorderItemTypeId(orderItemTypeId);
//        orderIt.setisModifiedPrice("Y");
//        orderIt.setisPromo("N");

//        orderIt.setproductId("");
//        orderIt.setitemDescription("Hello World");
        oic.setOrderItem(orderIt);
        oic.setOrderItemBillingsList(new OrderItemBillingsList());

        //new order status
        OrderStatus osItem = new OrderStatus();
        osItem.setstatusId("ITEM_CREATED");
        osItem.setstatusUserLogin(session.getUserId());
        oic.setOrderStatusList(new OrderStatusList());
        oic.getOrderStatusList().add(osItem);

        oic.setOrderItemShipGroupAssocsList(new OrderItemShipGroupAssocsList());

        return oic;
    }

    static public InvoiceItemComposite updateOrderItem(InvoiceItemComposite oic, String facilityId, XuiSession session) {

        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(oic.getOrderItem().getproductId(), facilityId, session);

        Debug.logInfo("before update : " + oic.getOrderItem().getdescription(), module);

//        orderItem.setOrderItemData(data);
        return oic;
    }

    public static InvoiceComposite newInvoice(XuiSession session, String invoiceTypeId, Account billFrom, Account billTo) {

        InvoiceComposite order = null;

        //get order genericvalue
        //create order header
        Invoice invoice = new Invoice();
        invoice.setstatusId("ORDER_CREATED");
        invoice.setinvoiceTypeId(invoiceTypeId);
        /*sur        orderHeader.setSalesChannelEnumId("PHONE_SALES_CHANNEL");
         orderHeader.setOrderDate(UtilDateTime.nowTimestamp());
         orderHeader.setPriority("2");
         orderHeader.setEntryDate(UtilDateTime.nowTimestamp());
         orderHeader.setCreatedBy(session.getUserPartyId());
         //    order.setorderId(delegator.getNextSeqId("OrderHeader"));
         orderHeader.setRemainingSubTotal(BigDecimal.ZERO);
         orderHeader.setGrandTotal(BigDecimal.ZERO);
         orderHeader.setCurrencyUom(UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
         */
//        order.setPartyName(org.ofbiz.party.party.PartyHelper.getPartyName(delegator, order.getPartyId(), false));
//        order.setPartyId(partyIdTextField.getText());
        //create our composite object
        order = new InvoiceComposite(/*invoiceTypeId*/);
        order.setInvoice(invoice);

//        order.setOwnerPartyId(PosProductHelper.organizationPartyId);
//        order.setDestinationFacilityId("mainwarehouse"/*(String) session.getAttribute("facilityId")*/);
        //let set order composite to order
        InvoiceRolesList rolesList = new InvoiceRolesList();
//sur        createSupplierOrderRoles(rolesList, billFrom, billTo);
        order.setInvoiceRolesList(rolesList);

        //create order item list
        InvoiceItemCompositeList invoiceItemCompositeList = new InvoiceItemCompositeList();
        order.setInvoiceItemCompositeList(invoiceItemCompositeList);

        //new order status
        InvoiceStatus osItem = new InvoiceStatus();
        // sur 
/*        osItem.setorderItemSeqId(null);
         osItem.setstatusId("ORDER_CREATED");
         osItem.setorderItemSeqId(null);
         osItem.setstatusId(order.getInvoice().getstatusId());
         osItem.setstatusUserLogin(session.getUserId());
         osItem.setstatusDatetime(UtilDateTime.nowTimestamp());
         */
        order.setInvoiceStatusesList(new InvoiceStatusList());

        order.getInvoiceStatusesList().add(osItem);

//        PartyContactMechComposite pc = billFrom.getPartyContactList().getBillingLocationContact();
        InvoiceContactList ocl = new InvoiceContactList();
        createSupplierInvoiceContacts(ocl, billFrom);
        order.setInvoiceContactList(ocl);

        String shipmentMethodTypeId = "NO_SHIPPING";
        String carrierPartyId = "_NA_";
        String carrierRoleTypeId = "CARRIER";
        String maySplit = "N";
        String isGift = "N";
        Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();
        /*
         OrderItemShipGroup oisg = new OrderItemShipGroup();
         oisg.setshipmentMethodTypeId(shipmentMethodTypeId);
         oisg.setcarrierPartyId(carrierPartyId);
         oisg.setcarrierRoleTypeId(carrierRoleTypeId);
         oisg.setmaySplit(maySplit);
         oisg.setisGift(isGift);
         oisg.setestimatedDeliveryDate(estimatedDeliveryDate);
         */

        //shopping cart
 /*       String productStoreId = (String) session.getAttribute("productStoreId");
         String facilityId = (String) session.getAttribute("facilityId");
         String currency = (String) session.getAttribute("currency");
         //        this.locale = (Locale) session.getAttribute("locale"); This is legacy code and may come (demo) from ProductStore.defaultLocaleString defined in demoRetail and is incompatible with how localisation is handled in the POS
         Locale locale = Locale.getDefault();
         */
//        ShoppingCart shopingCart = new ShoppingCart(session.getDelegator(), productStoreId, locale, currency);
//        shopingCart.setOrderType(orderHeader.getOrderTypeId());
//        shopingCart.setOrderPartyId("_NA_");
        String billFromPartyId = order.getBillFromAccount().getParty().getParty().getpartyId();
        String billToPartyId = order.getBillToAccount().getParty().getParty().getpartyId();

        Debug.logInfo("billFromPartyId: " + billFromPartyId, "module");
        Debug.logInfo("billToPartyId: " + billToPartyId, "module");

        //load billings
         /*
         GenericValue orderParty = orderReadHelper.getSupplierAgent();
         Debug.logInfo("orderParty.getString(partyId): " + orderParty.getString("partyId"), "module");
         if (orderParty != null) {
         Debug.logInfo("orderParty.getString(partyId): " + orderParty.getString("partyId"), "module");
         orderHeader.setPartyId(orderParty.getString("partyId"));
         }
            
            
            
         List<GenericValue> shipGroups = orderReadHelper.getOrderItemShipGroups();
         for (GenericValue shipGroup : shipGroups) {
         orderHeader.orderItemShipGroupList.add(new OrderItemShipGroup(shipGroup));
         orderHeader.shipGroupSeqId = orderHeader.orderItemShipGroupList.get(0).getshipGroupSeqId();
         }
            
         //load shipment
         List<GenericValue> shipments = PosProductHelper.getGenericValueLists("Shipment", "primaryOrderId", orderId, delegator);
         for (GenericValue shipment : shipments) {
         orderHeader.shipment = new Shipment(shipment);
         break;
         }
            
         Debug.logInfo("orderHeader.getPartyId(): " + orderHeader.getPartyId(), "module");
            
            
         String purposeId = InvoiceComposite.DELIVERY_LOCATION;
         List<GenericValue> orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
         for (GenericValue genVal : orderContachs) {
         orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
         Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
         }
            
         purposeId = InvoiceComposite.BILLING_LOCATION;
         orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
         for (GenericValue genVal : orderContachs) {
         orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
         Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
         }
            
         purposeId = InvoiceComposite.DELIVERY_PHONE;
         orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
         for (GenericValue genVal : orderContachs) {
         orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
         Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
         }
            
         purposeId = InvoiceComposite.BILLING_EMAIL;
         orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
         for (GenericValue genVal : orderContachs) {
         orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
         Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
         }*/
        return order;
    }

    static public void createSupplierInvoiceContacts(InvoiceContactList ocl, Account billFrom) {
        Debug.logInfo("createSupplierOrderContacts :", module);
        PartyContactMechComposite pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(billFrom, "BILLING_LOCATION");//billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");

        Debug.logInfo("BILLING_LOCATION 1 :", module);
        if (pc != null) {
            Debug.logInfo("BILLING_LOCATION 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
            ocl.add(createSupplierInvoiceContact(pc, "BILLING_LOCATION"));
        }
        Debug.logInfo("BILLING_LOCATION 3 :", module);
        pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(billFrom, "PHONE_BILLING");//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("PHONE_BILLING");
        if (pc != null) {
            Debug.logInfo("BILLING_LOCATION 4 :", module);
            ocl.add(createSupplierInvoiceContact(pc, "PHONE_BILLING"));
        }
        Debug.logInfo("BILLING_LOCATION 5 :", module);

        pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(billFrom, "BILLING_EMAIL");//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_EMAIL");
        if (pc != null) {
            ocl.add(createSupplierInvoiceContact(pc, "BILLING_EMAIL"));
        }

        pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(billFrom, "FAX_BILLING");;//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("FAX_BILLING");
        if (pc != null) {
            ocl.add(createSupplierInvoiceContact(pc, "FAX_BILLING"));
        }
        pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(billFrom, "PHONE_SHIPPING");;//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("PHONE_SHIPPING");
        if (pc != null) {
            ocl.add(createSupplierInvoiceContact(pc, "PHONE_SHIPPING"));
        }

        pc = LoadPurchaseOrderWorker.getPartyContactFromPurpose(billFrom, "SHIPPING_LOCATION");;//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("SHIPPING_LOCATION");
        if (pc != null) {
            Debug.logInfo("SHIPPING 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
            ocl.add(createSupplierInvoiceContact(pc, "SHIPPING_LOCATION"));
        }
    }

    static public InvoiceContact createSupplierInvoiceContact(PartyContactMechComposite pc, String purposeId) {
        Debug.logInfo("createSupplierOrderContact2 :" + pc.getContact().getContactMechTypeId(), module);
        InvoiceContactMech ocm = new InvoiceContactMech();
        ocm.setcontactMechId(pc.getPartyContactMech().getcontactMechId());
        ocm.setcontactMechPurposeTypeId(purposeId);
        InvoiceContact oc = new InvoiceContact();
        oc.setContact(pc.getContact());
        oc.setInvoiceContactMech(ocm);
        Debug.logInfo("createSupplierInvoiceContact  purposeId:" + purposeId, module);
        return oc;
    }

}
