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
import java.io.IOException;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;

/**
 *
 * @author siranjeev
 */
public class LoadOrderHeaderRolesOrderWorker extends LoadBaseSwingWorker<OrderHeaderAndRoleSummary> {

    public static final String module = LoadOrderHeaderRolesOrderWorker.class.getName();

//    private ListAdapterListModel<OrderHeaderAndRoleSummary> orderListModel;
//    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    public LoadOrderHeaderRolesOrderWorker(ListAdapterListModel<OrderHeaderAndRoleSummary> orderListModel, XuiSession delegator, Map<String, Object> findOptionMap) {
        super(orderListModel, findOptionMap);
        this.session = delegator;
    }

    public LoadOrderHeaderRolesOrderWorker(ListAdapterListModel<OrderHeaderAndRoleSummary> orderListModel, XuiSession delegator, List<EntityCondition> findOptionList) {
        super(orderListModel, findOptionList);
        this.session = delegator;
    }

    static public List<GenericValue> getOrderDetails(Map<String, Object> wClauseMap,/*String partyId, String roleTypeId,
             String productId, String orderTypeId, String statusId,*/ Delegator delegator) {

        List<GenericValue> partyList = null;
        List<GenericValue> resultList = null;

        // create the dynamic view entity
        DynamicViewEntity dynamicView = new DynamicViewEntity();

        // Person (name + card)
        dynamicView.addMemberEntity("O_R", "OrderRole");
        dynamicView.addAlias("O_R", "orderId");
        dynamicView.addAlias("O_R", "partyId");
        dynamicView.addAlias("O_R", "roleTypeId");

        dynamicView.addMemberEntity("O_H", "OrderHeader");
        dynamicView.addAlias("O_H", "orderName");
        dynamicView.addAlias("O_H", "statusId");
        dynamicView.addAlias("O_H", "orderTypeId");
        dynamicView.addAlias("O_H", "grandTotal");
        dynamicView.addAlias("O_H", "remainingSubTotal");
        dynamicView.addAlias("O_H", "orderDate");

        dynamicView.addViewLink("O_R", "O_H", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));

        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;

        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();

        // fields we need to select; will be used to set distinct
        fieldsToSelect.add("orderId");
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("roleTypeId");
        fieldsToSelect.add("orderName");
        fieldsToSelect.add("statusId");
        fieldsToSelect.add("orderTypeId");
        fieldsToSelect.add("grandTotal");
        fieldsToSelect.add("remainingSubTotal");
        fieldsToSelect.add("orderDate");

        if (wClauseMap.containsKey("partyId")) {
            andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, wClauseMap.get("partyId"))));
        }

        if (wClauseMap.containsKey("roleTypeId")) {
            andExprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, wClauseMap.get("roleTypeId"))); // Only persons for now...
        }

        if (wClauseMap.containsKey("productId")) {
            andExprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, wClauseMap.get("productId"))); // Only persons for now...
        }

        if (wClauseMap.containsKey("orderTypeId")) {
            andExprs.add(EntityCondition.makeCondition("orderTypeId", EntityOperator.EQUALS, wClauseMap.get("orderTypeId"))); // Only persons for now...
        }

        if (wClauseMap.containsKey("orderId")) {
            andExprs.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, wClauseMap.get("orderId"))); // Only persons for now...
        }

        if (wClauseMap.containsKey("statusId")) {
            andExprs.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, wClauseMap.get("statusId"))); // Only persons for now...
        }

        mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
//        orderBy.add("createdStamp");

        Debug.logInfo("dynamicView=" + dynamicView.toString(), module);
        Debug.logInfo("dynamicView1=" + dynamicView.getEntityName(), module);
        Debug.logInfo("In searchClientProfile mainCond=" + mainCond, module);

        Integer maxRows = 100000;
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            try {
                try {
                    String str = dynamicView.getViewXml("PartyRole");
                    Debug.logInfo("getViewXml=" + str, module);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // set distinct on so we only get one row per person
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
                // using list iterator
                EntityListIterator pli = delegator.findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);

                // get the partial list for this page
                partyList = pli.getPartialList(1, maxRows);

                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        if (partyList != null) {
            resultList = FastList.newInstance();
            for (GenericValue orderResult : partyList) {
                boolean found = false;
                for (GenericValue resultVal : resultList) {
                    if (orderResult.getString("orderId").equalsIgnoreCase(resultVal.getString("orderId"))) {
                        found = true;
                        break;
                    }
                }
                if (found == false) {
                    resultList.add(orderResult);
                }
            }
        } else {
            resultList = FastList.newInstance();
            Debug.logInfo("supplierProductId list is null", module);
        }

        return resultList;
    }

    @Override
    protected List<OrderHeaderAndRoleSummary> doInBackground() throws Exception {
        listModel.clear();

        List<OrderHeaderAndRoleSummary> orders = new ArrayList<OrderHeaderAndRoleSummary>();
        List<GenericValue> orderList = null;
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        List<GenericValue> genValList = null;
        if (entityConditionList != null) {
//            genValList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("OrderHeaderAndRoleSummary", entityConditionList, "orderId", session.getDelegator(), false);
            genValList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("OrderHeaderAndRole", entityConditionList, "orderId", session.getDelegator(), false);
        }
//        List<GenericValue> genValList = getOrderDetails(/*partyId, roleTypeId,
///                 productId, orderTypeId, statusId,*/findOptionMap, session.getDelegator());
        Map<String, OrderHeaderAndRoleSummary> treeMap = new HashMap<String, OrderHeaderAndRoleSummary>();
        Debug.logError("genValList.size() : " + genValList.size(), module);
        if (genValList != null) {
            maxProgress = genValList.size() + 1;
            for (GenericValue genVal : genValList) {
                String orderId = genVal.getString("orderId");
                Debug.logError("treeMap.put(orderId, orderId): " + orderId, module);
                if (UtilValidate.isNotEmpty(orderId)) {
                    if (!treeMap.containsKey(orderId)) {
                        Debug.logError("treeMap.put(orderId, orderId): " + orderId, module);

//                        OrderHeader order = new OrderHeader(genVal);
                        OrderHeaderAndRoleSummary orderMax = new OrderHeaderAndRoleSummary(genVal);
                        treeMap.put(orderId, orderMax);
                        if (genVal.getString("roleTypeId").equals("BILL_FROM_VENDOR")) {
                            orderMax.setBillFrom(genVal.getString("partyId"));
                        } else if (genVal.getString("roleTypeId").equals("BILL_TO_CUSTOMER")) {
                            orderMax.setBillTo(genVal.getString("partyId"));
                        }
                        /* //orderMax.setorderId(order.getOrderId());
                         List<EntityCondition> whereClauseMap = new ArrayList<EntityCondition>();
                         whereClauseMap.add(EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, orderId));
                         List<GenericValue> sumValList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("OrderHeaderAndRoleSummary", whereClauseMap, "orderId", session.getDelegator(), false);
                         if (sumValList.size() > 0) {
                         GenericValue gVal = sumValList.get(0);
                         OrderHeaderAndRoleSummary orderMax = new OrderHeaderAndRoleSummary(gVal);
                         orderMax.setOrderName(order.getOrderName());
                         orderMax.setBillTo(order.getOrderName());
                         orderMax.setTrackingCode(order.getTerminalId());
                         orderMax.setProductStoreId(order.getProductStoreId());
                         for (int i = 0; i < sumValList.size(); ++i) {
                         GenericValue val = sumValList.get(i);
                         Debug.logError("treeMap.put(orderId, orderId): " + val.getString("roleTypeId") + " " + val.getString("partyId"), module);
                         if(val.getString("roleTypeId").equals("BILL_FROM_VENDOR")){
                         orderMax.setBillFrom(val.getString("partyId"));
                         }
                         else if(val.getString("roleTypeId").equals("BILL_TO_CUSTOMER")){
                         orderMax.setBillTo(val.getString("partyId"));                                    
                         }
                         }
                            
                         calcProgress(progressedItems + 1);
                         orders.add(orderMax);

                         //                    if ( (prograss+1) % 4 == 0) {
                         publish(orderMax);
                         }*/
                        calcProgress(progressedItems + 1);
                        orders.add(orderMax);

//                    if ( (prograss+1) % 4 == 0) {
                        publish(orderMax);
//                        OrderHeaderAndRoleSummary orderMax = new OrderHeaderAndRoleSummary(genVal);
//                    }
//                    else{
//                        progressedItems++;                        
//                    }
                        if (isCancelled()) {
                            break;
                        }
                    } else {
                        OrderHeaderAndRoleSummary orderMax = treeMap.get(orderId);
                        if (genVal.getString("roleTypeId").equals("BILL_FROM_VENDOR")) {
                            orderMax.setBillFrom(genVal.getString("partyId"));
                        } else if (genVal.getString("roleTypeId").equals("BILL_TO_CUSTOMER")) {
                            orderMax.setBillTo(genVal.getString("partyId"));
                        }
                    }
                }
            }
        }

        Debug.logError("start thread: end: " + orders.size(), module);
        return orders;
    }

}
