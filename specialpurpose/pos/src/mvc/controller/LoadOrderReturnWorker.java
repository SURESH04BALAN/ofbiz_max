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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.finaccount.FinAccountHelper;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.ReturnHeader;
import org.ofbiz.ordermax.entity.ReturnItem;
import org.ofbiz.ordermax.entity.ReturnItemResponse;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.ordermax.orderbase.returns.ReturnItemComposite;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadOrderReturnWorker extends BaseOrderMaxSwingWorker<List<ReturnHeaderComposite>, ReturnHeaderComposite> {

    public static final String module = LoadOrderReturnWorker.class.getName();
    public static final String resourceError = "AccountingErrorUiLabels";

    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<ReturnHeaderComposite> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;

    public LoadOrderReturnWorker(ListAdapterListModel<ReturnHeaderComposite> personListModel, String fileName, XuiSession delegator) {
        super(personListModel, delegator);
        this.personListModel = personListModel;
        this.session = delegator;
    }

    static public ReturnHeaderComposite getNewPurchaseReturnHeaderComposite(String returnTypeId) {
        ReturnHeaderComposite returnHeader = new ReturnHeaderComposite(returnTypeId);
        ReturnHeader rh = new ReturnHeader();
        rh.setreturnHeaderTypeId(returnTypeId);
        rh.setstatusId("RETURN_REQUESTED");
        rh.setfromPartyId(XuiSession.getCompanyPartyId());
        rh.setcurrencyUomId((String) XuiContainer.getSession().getAttribute("currency"));
        rh.setdestinationFacilityId((String) XuiContainer.getSession().getAttribute("facilityId"));
        returnHeader.setReturnHeader(rh);

        return returnHeader;
    }

    static public ReturnHeaderComposite getNewReturnHeaderComposite(Order order) {
        String returnTypeId = "VENDOR_RETURN";
        if ("SALES_ORDER".equals(order.getOrderType())) {
            returnTypeId = "CUSTOMER_RETURN";
        }
        ReturnHeaderComposite returnHeader = new ReturnHeaderComposite(returnTypeId);
        ReturnHeader rh = new ReturnHeader();
        rh.setreturnHeaderTypeId(returnTypeId);
        rh.setstatusId("RETURN_REQUESTED");
        rh.setfromPartyId(order.getOrderReadHelper().getBillToParty().getString("partyId"));
        rh.settoPartyId(order.getOrderReadHelper().getBillFromParty().getString("partyId"));
        rh.setcurrencyUomId(order.getOrderReadHelper().getCurrency());
        rh.setdestinationFacilityId(order.getFacilityId());
        rh.setcreatedBy(XuiContainer.getSession().getUserId());
        returnHeader.setReturnHeader(rh);

        return returnHeader;
    }

    static public ReturnHeaderComposite getNewSalesReturnHeaderComposite(String returnTypeId) {
        ReturnHeaderComposite returnHeader = new ReturnHeaderComposite(returnTypeId);
        ReturnHeader rh = new ReturnHeader();
        rh.setreturnHeaderTypeId(returnTypeId);
        rh.setstatusId("RETURN_REQUESTED");
        rh.settoPartyId(XuiSession.getCompanyPartyId());
        rh.setcurrencyUomId((String) XuiContainer.getSession().getAttribute("currency"));
        rh.setdestinationFacilityId((String) XuiContainer.getSession().getAttribute("facilityId"));

        returnHeader.setReturnHeader(rh);

        return returnHeader;
    }

    static public ReturnHeaderComposite loadReturnHeaderComposite(String returnId, XuiSession session) {
        ReturnHeaderComposite returnHeaderComposite = null;
        GenericValue returnHeader = null;
        ListAdapterListModel<ReturnItemComposite> orderReturnItemsList = null;
        try {
            returnHeader = session.getDelegator().findByPrimaryKey("ReturnHeader", UtilMisc.toMap("returnId", returnId));
            if (returnHeader != null) {
                Debug.logError("return header done", "module");
                returnHeaderComposite = new ReturnHeaderComposite(returnHeader);
                orderReturnItemsList = new ListAdapterListModel<ReturnItemComposite>();

                List<GenericValue> returnItems = null;
//            List<GenericValue> returnAdjustments = FastList.newInstance();
                try {
                    returnItems = returnHeader.getRelated("ReturnItem");
                    Debug.logError("returnItems size: " + returnItems.size(), "module");
                    for (GenericValue genVal : returnItems) {
                        ReturnItemComposite retComp = new ReturnItemComposite(genVal, null);
                        Debug.logError(retComp.getReturnItem().getdescription(), "module");
                        orderReturnItemsList.add(retComp);
                    }
                    returnHeaderComposite.setOrderReturnItemsList(orderReturnItemsList);
                    GenericValue shipment = EntityUtil.getFirst(session.getDelegator().findByAnd("Shipment", UtilMisc.toMap("primaryReturnId", returnId)));
                    //session.getDelegator().findByAnd("Shipment", UtilMisc.toMap("primaryReturnId", returnId), false);   
                    //GenericValue shipment = returnHeader.getRelatedOne("Shipment");
                    if (shipment != null) {
                        returnHeaderComposite.setShipment(new Shipment(shipment));
                    }
//                returnAdjustments = delegator.findList("ReturnAdjustment", EntityCondition.makeCondition(
//                                    EntityCondition.makeCondition("returnId", EntityOperator.EQUALS, returnId), EntityOperator.AND,
//                                    EntityCondition.makeCondition("returnItemSeqId", EntityOperator.EQUALS, "_NA_")), null, UtilMisc.toList("returnAdjustmentTypeId"), null, true);
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
//                return ServiceUtil.returnError(UtilProperties.getMessage(resource_error, 
                    //                      "OrderErrorUnableToGetReturnItemRecordsFromReturnHeader", locale));
                }

            }

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadOrderReturnWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnHeaderComposite;
    }

    static public List<ReturnHeaderComposite> loadReturnHeaderComposites(List<EntityCondition> inputFields, XuiSession session) {
        List<ReturnHeaderComposite> listInventItemLocation = new ArrayList<ReturnHeaderComposite>();

        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ReturnHeader", inputFields, "returnId", session.getDelegator(), true);
        Debug.logInfo("resultList " + resultList.size(), module);
//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gv : resultList) {
//                tempResults.addAll(filteredList);
            listInventItemLocation.add(new ReturnHeaderComposite(gv));
        }
        return listInventItemLocation;

    }

    static public List<OrderHeader> loadOrders(Map<String, Object> inputFields, XuiSession session) {
        List<OrderHeader> listInventItemLocation = new ArrayList<OrderHeader>();

        Map<String, Object> result = null;
        List<Map<String, Object>> tempResults = FastList.newInstance();
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            // = FastMap.newInstance();
            try {
//                inputFields.put("facilityId", "Company");
//        inputFields.put("infoString_ic", caseInsensitiveEmail);
                result = session.getDispatcher().runSync("performFind", UtilMisc.<String, Object>toMap("entityName", "OrderHeader",
                        "inputFields", inputFields, "userLogin", session.getUserLogin()));
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
//                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
                    for (GenericValue gv : list) {
//                tempResults.addAll(filteredList);
                        listInventItemLocation.add(new OrderHeader(gv));
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

        return listInventItemLocation;

    }

    static public List<ReturnItemComposite> getReturnableItems(Map<String, Object> findOption, XuiSession session) {
        List<ReturnItemComposite> returnableItems = new ArrayList<ReturnItemComposite>();

        Map<String, Object> result = null;
        GenericValue userLogin = session.getUserLogin();
        Locale locale = Locale.getDefault();
        findOption.put("userLogin", userLogin);
        findOption.put("locale", locale);

        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            // = FastMap.newInstance();
            try {
                Debug.logInfo("getReturnableItems : ", module);
//                inputFields.put("facilityId", "Company");
//        inputFields.put("infoString_ic", caseInsensitiveEmail);
                result = session.getDispatcher().runSync("getReturnableItems", findOption);

            } catch (GenericServiceException ex) {
                Debug.logError(ex, module);
            }
            Map<GenericValue, Map<String, Object>> listIt = (Map<GenericValue, Map<String, Object>>) result.get("returnableItems");
            if (listIt != null) {

                for (Map.Entry<GenericValue, Map<String, Object>> entryDept : listIt.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    BigDecimal returnableQuantity = (BigDecimal.ZERO);
                    BigDecimal returnablePrice = (BigDecimal.ZERO);
                    String itemTypeKey = "";

                    for (Map.Entry<String, Object> entryVal : entryDept.getValue().entrySet()) {
                        Debug.logInfo("Key : " + entryVal.getKey() + " Value: " + entryVal.getValue().toString(), module);
                        returnableQuantity = (BigDecimal) entryDept.getValue().get("returnableQuantity");
                        returnablePrice = (BigDecimal) entryDept.getValue().get("returnablePrice");
                        itemTypeKey = (String) entryDept.getValue().get("itemTypeKey");
                    }

                    ReturnItemComposite retComp = new ReturnItemComposite(null, entryDept.getKey());
                    retComp.setReturnableQty(returnableQuantity);
                    retComp.getReturnItem().setreturnPrice(returnablePrice);
                    retComp.setReturnPrice(returnablePrice);
                    //retComp.setReturnableQuantity(returnableQuantity);
                    retComp.setItemTypeKey(itemTypeKey);
                    returnableItems.add(retComp);
                }

//                    List<GenericValue> list = listIt.getCompleteList();
//                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
//                    for (GenericValue gv : list) {
//                tempResults.addAll(filteredList);
//                        returnableItems.add(new OrderItemComposite(gv));
//                    }
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

        return returnableItems;

    }

    @Override
    protected List<ReturnHeaderComposite> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<ReturnHeaderComposite> persons = new ArrayList<ReturnHeaderComposite>();
        return persons;
    }

    static public void saveOrderReturnHeader(ReturnHeader returnHeader, XuiSession session) {

        // create the return header
        Map<String, Object> returnHeaderInfo = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        returnHeaderInfo.put("fromPartyId", returnHeader.getfromPartyId());
        returnHeaderInfo.put("returnHeaderTypeId", returnHeader.getreturnHeaderTypeId());
        returnHeaderInfo.put("entryDate", returnHeader.getentryDate());
        returnHeaderInfo.put("currencyUomId", returnHeader.getcurrencyUomId());
        returnHeaderInfo.put("toPartyId", returnHeader.gettoPartyId());
        returnHeaderInfo.put("statusId", returnHeader.getstatusId());
        returnHeaderInfo.put("createdBy", userLogin.getString("userLoginId"));
        returnHeaderInfo.put("paymentMethodId", returnHeader.getpaymentMethodId());
        returnHeaderInfo.put("finAccountId", returnHeader.getfinAccountId());
        returnHeaderInfo.put("billingAccountId", returnHeader.getbillingAccountId());
        returnHeaderInfo.put("destinationFacilityId", returnHeader.getdestinationFacilityId());

        returnHeaderInfo.put("userLogin", userLogin);
        Map<String, Object> returnHeaderResp = null;
        try {
            if (UtilValidate.isNotEmpty(returnHeader.getreturnId())) {
                returnHeaderInfo.put("returnId", returnHeader.getreturnId());
                returnHeaderResp = session.getDispatcher().runSync("updateReturnHeader", returnHeaderInfo);
            } else {
                returnHeaderResp = session.getDispatcher().runSync("createReturnHeader", returnHeaderInfo);
            }
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            return;
        }

        if (returnHeaderResp != null) {
            String errorMessage = ServiceUtil.getErrorMessage(returnHeaderResp);
            if (errorMessage != null) {
                Debug.logError(errorMessage, module);
            }
        }

        String returnId = null;
        if (returnHeaderResp != null) {
            returnId = (String) returnHeaderResp.get("returnId");
        }
        if (returnId != null) {
            returnHeader.setreturnId(returnId);
        }
    }
    /*
     static public void saveOrderReturnHeader(ReturnHeader returnHeader, XuiSession session) {

     // create the return header
     Map<String, Object> returnHeaderInfo = FastMap.newInstance();
     GenericValue userLogin = session.getUserLogin();
     LocalDispatcher dispatcher = session.getDispatcher();
     Map<String, Object> resultMap = ServiceUtil.returnSuccess();
     Delegator delegator = session.getDelegator();
     Locale locale = Locale.getDefault();

     returnHeaderInfo.put("fromPartyId", returnHeader.getfromPartyId());
     returnHeaderInfo.put("returnHeaderTypeId", returnHeader.getreturnHeaderTypeId());
     returnHeaderInfo.put("entryDate", returnHeader.getentryDate());
     returnHeaderInfo.put("currencyUomId", returnHeader.getcurrencyUomId());
     returnHeaderInfo.put("toPartyId", returnHeader.gettoPartyId());
     returnHeaderInfo.put("statusId", returnHeader.getstatusId());
     returnHeaderInfo.put("createdBy", userLogin.getString("userLoginId"));
     returnHeaderInfo.put("paymentMethodId", returnHeader.getpaymentMethodId());
     returnHeaderInfo.put("finAccountId", returnHeader.getfinAccountId());
     returnHeaderInfo.put("billingAccountId", returnHeader.getbillingAccountId());
     returnHeaderInfo.put("destinationFacilityId", returnHeader.getdestinationFacilityId());

     returnHeaderInfo.put("userLogin", userLogin);
     Map<String, Object> returnHeaderResp = null;
     try {
     if (UtilValidate.isNotEmpty(returnHeader.getreturnId())) {
     returnHeaderInfo.put("returnId", returnHeader.getreturnId());
     returnHeaderResp = session.getDispatcher().runSync("updateReturnHeader", returnHeaderInfo);
     } else {
     returnHeaderResp = session.getDispatcher().runSync("createReturnHeader", returnHeaderInfo);
     }
     } catch (GenericServiceException e) {
     Debug.logError(e, module);
     return;
     }

     if (returnHeaderResp != null) {
     String errorMessage = ServiceUtil.getErrorMessage(returnHeaderResp);
     if (errorMessage != null) {
     Debug.logError(errorMessage, module);
     }
     }

     String returnId = null;
     if (returnHeaderResp != null) {
     returnId = (String) returnHeaderResp.get("returnId");
     }
     if (returnId != null) {
     returnHeader.setreturnId(returnId);
     }
     }*/
    /*
     static public void saveOrderReturnHeaderAndItems(ReturnHeaderComposite headerComposite, XuiSession session) {

     // create the return header
     Map<String, Object> returnHeaderInfo = FastMap.newInstance();
     GenericValue userLogin = session.getUserLogin();
     LocalDispatcher dispatcher = session.getDispatcher();
     Map<String, Object> resultMap = ServiceUtil.returnSuccess();
     Delegator delegator = session.getDelegator();
     Locale locale = Locale.getDefault();

     //        ReturnHeaderComposite headerComposite
     ReturnHeader returnHeader = headerComposite.getReturnHeader();
     Map<String, Object> result = FinAccountServices.refundFinAccount(dispatcher.getDispatchContext(), returnHeaderInfo);

     //        ReturnHeader returnHeader = headerComposite.getReturnHeader();
     returnHeaderInfo.put("fromPartyId", returnHeader.getfromPartyId());
     returnHeaderInfo.put("returnHeaderTypeId", returnHeader.getreturnHeaderTypeId());
     returnHeaderInfo.put("entryDate", returnHeader.getentryDate());
     returnHeaderInfo.put("currencyUomId", returnHeader.getcurrencyUomId());
     returnHeaderInfo.put("toPartyId", returnHeader.gettoPartyId());
     returnHeaderInfo.put("statusId", returnHeader.getstatusId());
     returnHeaderInfo.put("createdBy", userLogin.getString("userLoginId"));
     returnHeaderInfo.put("paymentMethodId", returnHeader.getpaymentMethodId());
     returnHeaderInfo.put("finAccountId", returnHeader.getfinAccountId());
     returnHeaderInfo.put("billingAccountId", returnHeader.getbillingAccountId());
     returnHeaderInfo.put("destinationFacilityId", returnHeader.getdestinationFacilityId());

     returnHeaderInfo.put("userLogin", userLogin);
     Map<String, Object> returnHeaderResp = null;
     try {
     if (UtilValidate.isNotEmpty(returnHeader.getreturnId())) {
     returnHeaderInfo.put("returnId", returnHeader.getreturnId());
     returnHeaderResp = session.getDispatcher().runSync("updateReturnHeader", returnHeaderInfo);
     } else {
     returnHeaderResp = session.getDispatcher().runSync("createReturnHeader", returnHeaderInfo);
     }
     } catch (GenericServiceException e) {
     Debug.logError(e, module);
     return;
     }

     if (returnHeaderResp != null) {
     String errorMessage = ServiceUtil.getErrorMessage(returnHeaderResp);
     if (errorMessage != null) {
     Debug.logError(errorMessage, module);
     }
     }

     String returnId = null;
     if (returnHeaderResp != null) {
     returnId = (String) returnHeaderResp.get("returnId");
     }
     if (returnId != null) {
     returnHeader.setreturnId(returnId);
     }
     }
     */

    static public Map<String, Object> saveFinancialOrderReturnHeaderAndItems(ReturnHeaderComposite returnHeaderComposite, XuiSession session) {

        // create the return header
        Map<String, Object> returnHeaderInfo = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        ReturnHeader returnHeader = returnHeaderComposite.getReturnHeader();
        String finAccountId = returnHeader.getfinAccountId();
        GenericValue finAccount;
        Map<String, Object> result = null;
        try {
            finAccount = delegator.findByPrimaryKey("FinAccount", UtilMisc.toMap("finAccountId", finAccountId));
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError(e.getMessage());
        }

        if (finAccount != null) {
            // check to make sure the account is refundable
            if (!"Y".equals(finAccount.getString("isRefundable"))) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "AccountingFinAccountIsNotRefundable", locale));
            }

            // get the actual and available balance
            BigDecimal availableBalance = finAccount.getBigDecimal("availableBalance");
            BigDecimal actualBalance = finAccount.getBigDecimal("actualBalance");

            // if they do not match, then there are outstanding authorizations which need to be settled first
            if (actualBalance.compareTo(availableBalance) != 0) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "AccountingFinAccountCannotBeRefunded", locale));
            }

            // now we make sure there is something to refund
            if (actualBalance.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal remainingBalance = new BigDecimal(actualBalance.toString());
                BigDecimal refundAmount = BigDecimal.ZERO;

                List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("finAccountTransTypeId", EntityOperator.EQUALS, "DEPOSIT"),
                        EntityCondition.makeCondition("finAccountId", EntityOperator.EQUALS, finAccountId));
                EntityCondition condition = EntityCondition.makeCondition(exprs, EntityOperator.AND);
                EntityListIterator eli = null;
                try {
                    eli = delegator.find("FinAccountTrans", condition, null, null, UtilMisc.toList("-transactionDate"), null);

                    GenericValue trans;
                    while (remainingBalance.compareTo(FinAccountHelper.ZERO) < 0 && (trans = eli.next()) != null) {
                        String orderId = trans.getString("orderId");
                        String orderItemSeqId = trans.getString("orderItemSeqId");

                        // make sure there is an order available to refund
                        if (orderId != null && orderItemSeqId != null) {
                            GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
                            GenericValue productStore = delegator.getRelatedOne("ProductStore", orderHeader);
                            GenericValue orderItem = delegator.findByPrimaryKey("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId));
                            if (!"ITEM_CANCELLED".equals(orderItem.getString("statusId")) && returnHeaderComposite.isOrderItemExists(orderId, orderItemSeqId)) {

                                // make sure the item hasn't already been returned
                                List<GenericValue> returnItems = orderItem.getRelated("ReturnItem");
                                if (UtilValidate.isEmpty(returnItems)) {
                                    BigDecimal txAmt = trans.getBigDecimal("amount");
                                    BigDecimal refAmt = txAmt;
                                    if (remainingBalance.compareTo(txAmt) == -1) {
                                        refAmt = remainingBalance;
                                    }
                                    remainingBalance = remainingBalance.subtract(refAmt);
                                    refundAmount = refundAmount.add(refAmt);

                                    // create the return header
                                    Map<String, Object> rhCtx = UtilMisc.toMap("returnHeaderTypeId", "CUSTOMER_RETURN", "fromPartyId", finAccount.getString("ownerPartyId"), "toPartyId", productStore.getString("payToPartyId"), "userLogin", userLogin);
                                    Map<String, Object> rhResp = dispatcher.runSync("createReturnHeader", rhCtx);
                                    if (ServiceUtil.isError(rhResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(rhResp));
                                    }
                                    String returnId = (String) rhResp.get("returnId");

                                    // create the return item
                                    Map<String, Object> returnItemCtx = FastMap.newInstance();
                                    returnItemCtx.put("returnId", returnId);
                                    returnItemCtx.put("orderId", orderId);
                                    returnItemCtx.put("description", orderItem.getString("itemDescription"));
                                    returnItemCtx.put("orderItemSeqId", orderItemSeqId);
                                    returnItemCtx.put("returnQuantity", BigDecimal.ONE);
                                    returnItemCtx.put("receivedQuantity", BigDecimal.ONE);
                                    returnItemCtx.put("returnPrice", refAmt);
                                    returnItemCtx.put("returnReasonId", "RTN_NOT_WANT");
                                    returnItemCtx.put("returnTypeId", "RTN_REFUND"); // refund return
                                    returnItemCtx.put("returnItemTypeId", "RET_FPROD_ITEM");
                                    returnItemCtx.put("userLogin", userLogin);

                                    Map<String, Object> retItResp = dispatcher.runSync("createReturnItem", returnItemCtx);
                                    if (ServiceUtil.isError(retItResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(retItResp));
                                    }
                                    String returnItemSeqId = (String) retItResp.get("returnItemSeqId");

                                    // approve the return
                                    Map<String, Object> appRet = UtilMisc.toMap("statusId", "RETURN_ACCEPTED", "returnId", returnId, "userLogin", userLogin);
                                    Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", appRet);
                                    if (ServiceUtil.isError(appResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(appResp));
                                    }

                                    // "receive" the return - should trigger the refund
                                    Map<String, Object> recRet = UtilMisc.toMap("statusId", "RETURN_RECEIVED", "returnId", returnId, "userLogin", userLogin);
                                    Map<String, Object> recResp = dispatcher.runSync("updateReturnHeader", recRet);
                                    if (ServiceUtil.isError(recResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(recResp));
                                    }

                                    // get the return item
                                    GenericValue returnItem = delegator.findByPrimaryKey("ReturnItem",
                                            UtilMisc.toMap("returnId", returnId, "returnItemSeqId", returnItemSeqId));
                                    GenericValue response = returnItem.getRelatedOne("ReturnItemResponse");
                                    if (response == null) {
                                        throw new GeneralException("No return response found for: " + returnItem.getPrimaryKey());
                                    }
                                    String paymentId = response.getString("paymentId");

                                    // create the adjustment transaction
                                    Map<String, Object> txCtx = FastMap.newInstance();
                                    txCtx.put("finAccountTransTypeId", "ADJUSTMENT");
                                    txCtx.put("finAccountId", finAccountId);
                                    txCtx.put("orderId", orderId);
                                    txCtx.put("orderItemSeqId", orderItemSeqId);
                                    txCtx.put("paymentId", paymentId);
                                    txCtx.put("amount", refAmt.negate());
                                    txCtx.put("partyId", finAccount.getString("ownerPartyId"));
                                    txCtx.put("userLogin", userLogin);

                                    Map<String, Object> txResp = dispatcher.runSync("createFinAccountTrans", txCtx);
                                    if (ServiceUtil.isError(txResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(txResp));
                                    }
                                }
                            }
                        }
                    }
                } catch (GeneralException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                } finally {
                    if (eli != null) {
                        try {
                            eli.close();
                        } catch (GenericEntityException e) {
                            Debug.logWarning(e, module);
                        }
                    }
                }

                // check to make sure we balanced out
                if (remainingBalance.compareTo(FinAccountHelper.ZERO) == 1) {
                    result = ServiceUtil.returnSuccess(UtilProperties.getMessage(resourceError,
                            "AccountingFinAccountPartiallyRefunded", locale));
                }

            }
        }

        if (result == null) {
            result = ServiceUtil.returnSuccess();
        }

        return result;
    }

    static public Map<String, Object> saveOrderReturnHeaderAndItems(ReturnHeaderComposite returnHeaderComposite, XuiSession session) throws Exception {

        // create the return header
        Map<String, Object> rhCtx = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        ReturnHeader returnHeader = returnHeaderComposite.getReturnHeader();

        Map<String, Object> result = null;

        //if (finAccount != null) {
        // now we make sure there is something to refund
        //if (actualBalance.compareTo(BigDecimal.ZERO) > 0) {
        //BigDecimal remainingBalance = new BigDecimal(actualBalance.toString());
        BigDecimal refundAmount = BigDecimal.ZERO;
        /*
         List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("finAccountTransTypeId", EntityOperator.EQUALS, "DEPOSIT"),
         EntityCondition.makeCondition("finAccountId", EntityOperator.EQUALS, finAccountId));
         EntityCondition condition = EntityCondition.makeCondition(exprs, EntityOperator.AND);
         EntityListIterator eli = null;
         try {
         eli = delegator.find("FinAccountTrans", condition, null, null, UtilMisc.toList("-transactionDate"), null);

         GenericValue trans;
         while (remainingBalance.compareTo(FinAccountHelper.ZERO) < 0 && (trans = eli.next()) != null) {
         */
        String returnId = returnHeader.getreturnId();
        rhCtx.put("fromPartyId", returnHeader.getfromPartyId());
        rhCtx.put("returnHeaderTypeId", returnHeader.getreturnHeaderTypeId());
        rhCtx.put("entryDate", returnHeader.getentryDate());
        rhCtx.put("currencyUomId", returnHeader.getcurrencyUomId());
        rhCtx.put("toPartyId", returnHeader.gettoPartyId());
        rhCtx.put("statusId", returnHeader.getstatusId());
        rhCtx.put("createdBy", userLogin.getString("userLoginId"));
        rhCtx.put("paymentMethodId", returnHeader.getpaymentMethodId());
        rhCtx.put("finAccountId", returnHeader.getfinAccountId());
        rhCtx.put("billingAccountId", returnHeader.getbillingAccountId());
        rhCtx.put("destinationFacilityId", returnHeader.getdestinationFacilityId());
        rhCtx.put("userLogin", userLogin);
        try {
            if (UtilValidate.isEmpty(returnId)) {
                //Map<String, Object> rhCtx = UtilMisc.toMap("returnHeaderTypeId", "CUSTOMER_RETURN", "fromPartyId", finAccount.getString("ownerPartyId"), "toPartyId", productStore.getString("payToPartyId"), "userLogin", userLogin);
                Map<String, Object> rhResp = dispatcher.runSync("createReturnHeader", rhCtx);
                if (OrderMaxUtility.handleServiceReturn("Save Return", rhResp) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                    throw new GeneralException(ServiceUtil.getErrorMessage(rhResp));
                }

                //load return header
                returnId = (String) rhResp.get("returnId");
                if (returnId != null) {
                    GenericValue genValue = delegator.findByPrimaryKey("ReturnHeader", UtilMisc.toMap("returnId", returnId));
                    returnHeaderComposite.setReturnHeader(new ReturnHeader(genValue));
                }
            } else {
                rhCtx.put("returnId", returnId);
                Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", rhCtx);
                if (OrderMaxUtility.handleServiceReturn("Update Return", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    GenericValue genValue = delegator.findByPrimaryKey("ReturnHeader", UtilMisc.toMap("returnId", returnId));
                    returnHeaderComposite.setReturnHeader(new ReturnHeader(genValue));
                }
            }

            //returnHeaderComposite.getReturnHeader().setreturnId(returnId);
            Debug.logInfo("returnId: " + returnId + "returnHeaderComposite.getOrderReturnItemsList().getList(): " + returnHeaderComposite.getOrderReturnItemsList().getList(), module);
            for (ReturnItemComposite rtiComp : returnHeaderComposite.getOrderReturnItemsList().getList()) {

                String orderId = rtiComp.getOrderItem().getorderId();
                String orderItemSeqId = rtiComp.getOrderItem().getorderItemSeqId();
                Debug.logInfo("orderId: " + orderId + " orderItemSeqId: " + orderItemSeqId, module);
                // make sure there is an order available to refund
                if (orderId != null && orderItemSeqId != null) {
                    GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
                    GenericValue productStore = delegator.getRelatedOne("ProductStore", orderHeader);
                    GenericValue orderItem = delegator.findByPrimaryKey("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId));
                    if (!"ITEM_CANCELLED".equals(orderItem.getString("statusId")) && returnHeaderComposite.isOrderItemExists(orderId, orderItemSeqId)) {

                        // make sure the item hasn't already been returned
                        List<GenericValue> returnItems = orderItem.getRelated("ReturnItem");
                        boolean canReturn = true;
                        if (UtilValidate.isNotEmpty(returnItems)) {
                            /*();
                             for (GenericValue genVal : returnItems) {

                             }*/
                            if (rtiComp.getReturnableQty().compareTo(rtiComp.getReturnQty()) >= 0) {
                                canReturn = true;

                            } else {
                                canReturn = false;
                            }
                            Debug.logInfo("canReturn: " + canReturn + " tiComp.getReturnableQty(): " + rtiComp.getReturnableQty() + " tiComp.getReturnQty(): " + rtiComp.getReturnQty(), module);
                        }
                        Debug.logInfo("returnItems: " + returnItems.size(), module);
                        if (canReturn) {
                            /*                                    BigDecimal txAmt = trans.getBigDecimal("amount");
                             BigDecimal refAmt = txAmt;
                             if (remainingBalance.compareTo(txAmt) == -1) {
                             refAmt = remainingBalance;
                             }
                             remainingBalance = remainingBalance.subtract(refAmt);
                             refundAmount = refundAmount.add(refAmt);
                             */
                            // create the return header
                            String returnItemSeqId = rtiComp.getReturnItem().getreturnItemSeqId();
                            // create the return item
                            Map<String, Object> returnItemCtx = FastMap.newInstance();
                            returnItemCtx.put("returnId", returnId);
                            returnItemCtx.put("orderId", orderId);
                            returnItemCtx.put("productId", orderItem.getString("productId"));
                            returnItemCtx.put("description", orderItem.getString("itemDescription"));
                            returnItemCtx.put("orderItemSeqId", orderItemSeqId);
                            returnItemCtx.put("returnQuantity", rtiComp.getReturnQty());
                            returnItemCtx.put("receivedQuantity", rtiComp.getReturnableQty());
                            returnItemCtx.put("returnPrice", rtiComp.getReturnPrice());
//                            returnItemCtx.put("returnReasonId", rtiComp.getReturnItem().getreturnReasonId());
//                            returnItemCtx.put("returnTypeId", rtiComp.getReturnItem().getreturnTypeId()); // refund return
//                            returnItemCtx.put("returnItemTypeId", rtiComp.getReturnItem().getreturnItemTypeId());
                            returnItemCtx.put("returnReasonId", "RTN_NOT_WANT");
                            returnItemCtx.put("returnTypeId", "RTN_REFUND"); // refund return
                            returnItemCtx.put("returnItemTypeId", "RET_FPROD_ITEM");
                            returnItemCtx.put("userLogin", userLogin);

                            if (UtilValidate.isEmpty(returnItemSeqId)) {

                                Map<String, Object> retItResp = dispatcher.runSync("createReturnItem", returnItemCtx);

                                if (OrderMaxUtility.handleServiceReturn("Save Return", retItResp) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                                    throw new GeneralException(ServiceUtil.getErrorMessage(retItResp));
                                }
                                returnItemSeqId = (String) retItResp.get("returnItemSeqId");

                                rtiComp.getReturnItem().setreturnItemSeqId(returnItemSeqId);
                            } else {
                                returnItemCtx.put("returnItemSeqId", returnItemSeqId);
                                Map<String, Object> retItResp = dispatcher.runSync("updateReturnItem", returnItemCtx);

                                if (OrderMaxUtility.handleServiceReturn("Save Return Item", retItResp) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                                    throw new GeneralException(ServiceUtil.getErrorMessage(retItResp));
                                }
                            }
                            /* // "receive" the return - should trigger the refund
                             Map<String, Object> recRet = UtilMisc.toMap("statusId", "RETURN_RECEIVED", "returnId", returnId, "userLogin", userLogin);
                             Map<String, Object> recResp = dispatcher.runSync("updateReturnHeader", recRet);
                             if (OrderMaxUtility.handleServiceReturn("Save Return", recResp) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                             throw new GeneralException(ServiceUtil.getErrorMessage(recResp));
                             }
                             */
                            // get the return item
                            GenericValue returnItem = delegator.findByPrimaryKey("ReturnItem",
                                    UtilMisc.toMap("returnId", returnId, "returnItemSeqId", returnItemSeqId));
                            rtiComp.setReturnItem(new ReturnItem(returnItem));
                            GenericValue response = returnItem.getRelatedOne("ReturnItemResponse");

                            if (response != null) {
                                String paymentId = response.getString("paymentId");
                                Debug.logInfo("paymentId: " + paymentId, module);
                                rtiComp.setPaymentId(paymentId);
                                rtiComp.setReturnItemResponse(new ReturnItemResponse(response));
//                                throw new GeneralException("No return response found for: " + returnItem.getPrimaryKey());
                            }
//                            String paymentId = response.getString("paymentId");
//                            Debug.logInfo("paymentId: " + paymentId, module);
                            /* // create the adjustment transaction
                             Map<String, Object> txCtx = FastMap.newInstance();
                             txCtx.put("finAccountTransTypeId", "ADJUSTMENT");
                             txCtx.put("finAccountId", finAccountId);
                             txCtx.put("orderId", orderId);
                             txCtx.put("orderItemSeqId", orderItemSeqId);
                             txCtx.put("paymentId", paymentId);
                             txCtx.put("amount", refAmt.negate());
                             txCtx.put("partyId", finAccount.getString("ownerPartyId"));
                             txCtx.put("userLogin", userLogin);

                             Map<String, Object> txResp = dispatcher.runSync("createFinAccountTrans", txCtx);
                             if (ServiceUtil.isError(txResp)) {
                             throw new GeneralException(ServiceUtil.getErrorMessage(txResp));
                             }*/
                        }
                    }
                }
            }
        } catch (GeneralException e) {
            Debug.logError(e, module);
            return ServiceUtil.returnError(e.getMessage());
        } /*finally {
         if (eli != null) {
         try {
         eli.close();
         } catch (GenericEntityException e) {
         Debug.logWarning(e, module);
         }
         }
         }

         // check to make sure we balanced out
         if (remainingBalance.compareTo(FinAccountHelper.ZERO) == 1) {
         result = ServiceUtil.returnSuccess(UtilProperties.getMessage(resourceError,
         "AccountingFinAccountPartiallyRefunded", locale));
         }*/

        //}
        //}

        if (result == null) {
            result = ServiceUtil.returnSuccess();
        }

        return result;
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToReturnAccepted(ReturnHeaderComposite returnHeaderComposite, XuiSession session) throws Exception {

        // create the return header
        Map<String, Object> rhCtx = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        ReturnHeader returnHeader = returnHeaderComposite.getReturnHeader();
        Map<String, Object> result = null;
        String returnId = returnHeader.getreturnId();
        // approve the return
        Map<String, Object> appRet = UtilMisc.toMap("statusId", "RETURN_ACCEPTED", "returnId", returnId, "userLogin", userLogin);
        Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", appRet);
        if (OrderMaxUtility.handleServiceReturn("Set Return Accepted", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            appResp = dispatcher.runSync("updateReturnItemsStatus", appRet);
            if (OrderMaxUtility.handleServiceReturn("Set Return Accepted", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            }

            returnHeader.setstatusId("RETURN_ACCEPTED");
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;

        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToReturnReceived(ReturnHeaderComposite returnHeaderComposite, XuiSession session) throws Exception {

        // create the return header
        Map<String, Object> rhCtx = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        ReturnHeader returnHeader = returnHeaderComposite.getReturnHeader();
        Map<String, Object> result = null;
        String returnId = returnHeader.getreturnId();
        // approve the return
        Map<String, Object> appRet = UtilMisc.toMap("statusId", "RETURN_RECEIVED", "returnId", returnId, "userLogin", userLogin);
        Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", appRet);
        if (OrderMaxUtility.handleServiceReturn("Set Return Received", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            appResp = dispatcher.runSync("updateReturnItemsStatus", appRet);
            if (OrderMaxUtility.handleServiceReturn("Set Return Received", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            }
            returnHeader.setstatusId("RETURN_RECEIVED");
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToReturnManual(ReturnHeaderComposite returnHeaderComposite, XuiSession session) throws Exception {

        // create the return header
        Map<String, Object> rhCtx = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        ReturnHeader returnHeader = returnHeaderComposite.getReturnHeader();
        Map<String, Object> result = null;
        String returnId = returnHeader.getreturnId();
        // approve the return
        Map<String, Object> appRet = UtilMisc.toMap("statusId", "RETURN_MAN_REFUND", "returnId", returnId, "userLogin", userLogin);
        Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", appRet);
        if (OrderMaxUtility.handleServiceReturn("Set Return Manual Refund", appResp) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
            appResp = dispatcher.runSync("updateReturnItemsStatus", appRet);
            if (OrderMaxUtility.handleServiceReturn("Set Return Manual Refund", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            }
            returnHeader.setstatusId("RETURN_MAN_REFUND");
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;

        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToReturnCompleted(ReturnHeaderComposite returnHeaderComposite, XuiSession session) throws Exception {

        // create the return header
        Map<String, Object> rhCtx = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        ReturnHeader returnHeader = returnHeaderComposite.getReturnHeader();
        Map<String, Object> result = null;
        String returnId = returnHeader.getreturnId();
        // approve the return
        Map<String, Object> appRet = UtilMisc.toMap("statusId", "RETURN_COMPLETED", "returnId", returnId, "userLogin", userLogin);
        Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", appRet);
        if (OrderMaxUtility.handleServiceReturn("Set Return Completed", appResp) == OrderMaxUtility.ServiceReturnStatus.ERROR) {

            appResp = dispatcher.runSync("updateReturnItemsStatus", appRet);

            if (OrderMaxUtility.handleServiceReturn("Set Return Completed", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            }
            returnHeader.setstatusId("RETURN_COMPLETED");
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;

        }
    }

    static public OrderMaxUtility.ServiceReturnStatus setStatusToReturnCancelled(ReturnHeaderComposite returnHeaderComposite, XuiSession session) throws Exception {

        // create the return header
        Map<String, Object> rhCtx = FastMap.newInstance();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        ReturnHeader returnHeader = returnHeaderComposite.getReturnHeader();
        Map<String, Object> result = null;
        String returnId = returnHeader.getreturnId();
        // approve the return
        Map<String, Object> appRet = UtilMisc.toMap("statusId", "RETURN_CANCELLED", "returnId", returnId, "userLogin", userLogin);
        Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", appRet);
        if (OrderMaxUtility.handleServiceReturn("Set Return Completed", appResp) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
            appResp = dispatcher.runSync("updateReturnItemsStatus", appRet);
            if (OrderMaxUtility.handleServiceReturn("Set Return Completed", appResp) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
            }
            returnHeader.setstatusId("RETURN_CANCELLED");
            return OrderMaxUtility.ServiceReturnStatus.SUCCESS;
        } else {
            return OrderMaxUtility.ServiceReturnStatus.ERROR;
        }
    }

    public static Map<String, Object> refundFinAccount(DispatchContext dctx, Map<String, Object> context) {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Delegator delegator = dctx.getDelegator();
        Locale locale = (Locale) context.get("locale");
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String finAccountId = (String) context.get("finAccountId");
        Map<String, Object> result = null;

        GenericValue finAccount;
        try {
            finAccount = delegator.findByPrimaryKey("FinAccount", UtilMisc.toMap("finAccountId", finAccountId));
        } catch (GenericEntityException e) {
            return ServiceUtil.returnError(e.getMessage());
        }

        if (finAccount != null) {
            // check to make sure the account is refundable
            if (!"Y".equals(finAccount.getString("isRefundable"))) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "AccountingFinAccountIsNotRefundable", locale));
            }

            // get the actual and available balance
            BigDecimal availableBalance = finAccount.getBigDecimal("availableBalance");
            BigDecimal actualBalance = finAccount.getBigDecimal("actualBalance");

            // if they do not match, then there are outstanding authorizations which need to be settled first
            if (actualBalance.compareTo(availableBalance) != 0) {
                return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
                        "AccountingFinAccountCannotBeRefunded", locale));
            }

            // now we make sure there is something to refund
            if (actualBalance.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal remainingBalance = new BigDecimal(actualBalance.toString());
                BigDecimal refundAmount = BigDecimal.ZERO;

                List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("finAccountTransTypeId", EntityOperator.EQUALS, "DEPOSIT"),
                        EntityCondition.makeCondition("finAccountId", EntityOperator.EQUALS, finAccountId));
                EntityCondition condition = EntityCondition.makeCondition(exprs, EntityOperator.AND);

                EntityListIterator eli = null;
                try {
                    eli = delegator.find("FinAccountTrans", condition, null, null, UtilMisc.toList("-transactionDate"), null);

                    GenericValue trans;
                    while (remainingBalance.compareTo(FinAccountHelper.ZERO) < 0 && (trans = eli.next()) != null) {
                        String orderId = trans.getString("orderId");
                        String orderItemSeqId = trans.getString("orderItemSeqId");

                        // make sure there is an order available to refund
                        if (orderId != null && orderItemSeqId != null) {
                            GenericValue orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
                            GenericValue productStore = delegator.getRelatedOne("ProductStore", orderHeader);
                            GenericValue orderItem = delegator.findByPrimaryKey("OrderItem", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", orderItemSeqId));
                            if (!"ITEM_CANCELLED".equals(orderItem.getString("statusId"))) {

                                // make sure the item hasn't already been returned
                                List<GenericValue> returnItems = orderItem.getRelated("ReturnItem");
                                if (UtilValidate.isEmpty(returnItems)) {
                                    BigDecimal txAmt = trans.getBigDecimal("amount");
                                    BigDecimal refAmt = txAmt;
                                    if (remainingBalance.compareTo(txAmt) == -1) {
                                        refAmt = remainingBalance;
                                    }
                                    remainingBalance = remainingBalance.subtract(refAmt);
                                    refundAmount = refundAmount.add(refAmt);

                                    // create the return header
                                    Map<String, Object> rhCtx = UtilMisc.toMap("returnHeaderTypeId", "CUSTOMER_RETURN", "fromPartyId", finAccount.getString("ownerPartyId"), "toPartyId", productStore.getString("payToPartyId"), "userLogin", userLogin);
                                    Map<String, Object> rhResp = dispatcher.runSync("createReturnHeader", rhCtx);
                                    if (ServiceUtil.isError(rhResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(rhResp));
                                    }
                                    String returnId = (String) rhResp.get("returnId");

                                    // create the return item
                                    Map<String, Object> returnItemCtx = FastMap.newInstance();
                                    returnItemCtx.put("returnId", returnId);
                                    returnItemCtx.put("orderId", orderId);
                                    returnItemCtx.put("description", orderItem.getString("itemDescription"));
                                    returnItemCtx.put("orderItemSeqId", orderItemSeqId);
                                    returnItemCtx.put("returnQuantity", BigDecimal.ONE);
                                    returnItemCtx.put("receivedQuantity", BigDecimal.ONE);
                                    returnItemCtx.put("returnPrice", refAmt);
                                    returnItemCtx.put("returnReasonId", "RTN_NOT_WANT");
                                    returnItemCtx.put("returnTypeId", "RTN_REFUND"); // refund return
                                    returnItemCtx.put("returnItemTypeId", "RET_FPROD_ITEM");
                                    returnItemCtx.put("userLogin", userLogin);

                                    Map<String, Object> retItResp = dispatcher.runSync("createReturnItem", returnItemCtx);
                                    if (ServiceUtil.isError(retItResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(retItResp));
                                    }
                                    String returnItemSeqId = (String) retItResp.get("returnItemSeqId");

                                    // approve the return
                                    Map<String, Object> appRet = UtilMisc.toMap("statusId", "RETURN_ACCEPTED", "returnId", returnId, "userLogin", userLogin);
                                    Map<String, Object> appResp = dispatcher.runSync("updateReturnHeader", appRet);
                                    if (ServiceUtil.isError(appResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(appResp));
                                    }

                                    // "receive" the return - should trigger the refund
                                    Map<String, Object> recRet = UtilMisc.toMap("statusId", "RETURN_RECEIVED", "returnId", returnId, "userLogin", userLogin);
                                    Map<String, Object> recResp = dispatcher.runSync("updateReturnHeader", recRet);

                                    if (ServiceUtil.isError(recResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(recResp));
                                    }

                                    // get the return item
                                    GenericValue returnItem = delegator.findByPrimaryKey("ReturnItem",
                                            UtilMisc.toMap("returnId", returnId, "returnItemSeqId", returnItemSeqId));
                                    GenericValue response = returnItem.getRelatedOne("ReturnItemResponse");
                                    if (response == null) {
                                        throw new GeneralException("No return response found for: " + returnItem.getPrimaryKey());
                                    }
                                    String paymentId = response.getString("paymentId");

                                    // create the adjustment transaction
                                    Map<String, Object> txCtx = FastMap.newInstance();
                                    txCtx.put("finAccountTransTypeId", "ADJUSTMENT");
                                    txCtx.put("finAccountId", finAccountId);
                                    txCtx.put("orderId", orderId);
                                    txCtx.put("orderItemSeqId", orderItemSeqId);
                                    txCtx.put("paymentId", paymentId);
                                    txCtx.put("amount", refAmt.negate());
                                    txCtx.put("partyId", finAccount.getString("ownerPartyId"));
                                    txCtx.put("userLogin", userLogin);

                                    Map<String, Object> txResp = dispatcher.runSync("createFinAccountTrans", txCtx);
                                    if (ServiceUtil.isError(txResp)) {
                                        throw new GeneralException(ServiceUtil.getErrorMessage(txResp));
                                    }
                                }
                            }
                        }
                    }
                } catch (GeneralException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                } finally {
                    if (eli != null) {
                        try {
                            eli.close();
                        } catch (GenericEntityException e) {
                            Debug.logWarning(e, module);
                        }
                    }
                }

                // check to make sure we balanced out
                if (remainingBalance.compareTo(FinAccountHelper.ZERO) == 1) {
                    result = ServiceUtil.returnSuccess(UtilProperties.getMessage(resourceError,
                            "AccountingFinAccountPartiallyRefunded", locale));
                }
            }
        }

        if (result == null) {
            result = ServiceUtil.returnSuccess();
        }

        return result;
    }

}
