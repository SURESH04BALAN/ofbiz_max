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
import java.sql.Timestamp;

import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.InventoryItemComposite;
import org.ofbiz.ordermax.entity.InventoryItem;
import org.ofbiz.ordermax.entity.InventoryItemAndDetail;
import org.ofbiz.ordermax.entity.InventoryItemAndLocation;
import org.ofbiz.service.GenericServiceException;

/**
 *
 * @author siranjeev
 */
public class LoadInventoryWorker extends BaseOrderMaxSwingWorker<List<InventoryItemComposite>, InventoryItemComposite> {

    public static final String module = LoadInventoryWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<InventoryItemComposite> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;

    public LoadInventoryWorker(ListAdapterListModel<InventoryItemComposite> personListModel, String fileName, XuiSession delegator) {
        super(personListModel, delegator);
        this.personListModel = personListModel;
        this.session = delegator;
    }

    public static Map<String, Object> getInventoryAvailableByFacility(String productId, String facilityId,
            XuiSession session) {

        Map<String, Object> resultMap = FastMap.newInstance();
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();
            Debug.logError("productId: " + productId + " facilityId: " + facilityId, module);
            // Get ATP for the product
            Map<String, Object> getProductInventoryAvailableResult = session.getDispatcher().runSync("getInventoryAvailableByFacility", UtilMisc.toMap("productId", productId, "facilityId", facilityId));
            BigDecimal availableToPromiseTotal = (BigDecimal) getProductInventoryAvailableResult.get("availableToPromiseTotal");
            BigDecimal quantityOnHandTotal = (BigDecimal) getProductInventoryAvailableResult.get("quantityOnHandTotal");
            resultMap.put("availableToPromiseTotal", availableToPromiseTotal != null ? availableToPromiseTotal : BigDecimal.ZERO);
            resultMap.put("quantityOnHandTotal", quantityOnHandTotal != null ? quantityOnHandTotal : BigDecimal.ZERO);

            for (Map.Entry<String, Object> entryDept : getProductInventoryAvailableResult.entrySet()) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            //pos.OrderMaxOptionPane.showMessageDialog(null, "dialog/error/exception", e.getMessage());            
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
            }

        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
            }
        }

        return resultMap;
    }

    static public List<InventoryItemAndLocation> loadInventoryItemAndLocation(List<EntityCondition> inputFields, XuiSession session) {
        List<InventoryItemAndLocation> listInventItemLocation = new ArrayList<InventoryItemAndLocation>();

        /*      
         Map<String, Object> result = null;
         List<Map<String, Object>> tempResults = FastList.newInstance();
         boolean beganTransaction = false;
         try {
         beganTransaction = TransactionUtil.begin();

         //          Map<String, String> inputFields = FastMap.newInstance();
         try {

         result = session.getDispatcher().runSync("performFind", UtilMisc.<String, Object>toMap("entityName", "InventoryItemAndLocation",
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
         listInventItemLocation.add(new InventoryItemAndLocation(gv));
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
        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("InventoryItemAndLocation", inputFields, "inventoryItemId", session.getDelegator(), true);
//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gv : resultList) {
//                tempResults.addAll(filteredList);
            listInventItemLocation.add(new InventoryItemAndLocation(gv));
        }
        return listInventItemLocation;

    }

    static public List<InventoryItem> loadInventoryItem(List<EntityCondition> inputFields, XuiSession session) {
        List<InventoryItem> listInventItemLocation = new ArrayList<InventoryItem>();
        Debug.logInfo("loadInventoryItem: ", module);
        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("InventoryItem", inputFields, "inventoryItemId", session.getDelegator(), true);
//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gv : resultList) {
//                tempResults.addAll(filteredList);
            listInventItemLocation.add(new InventoryItem(gv));
            Debug.logInfo("gv: " + gv.getString("inventoryItemId"), module);
        }
        return listInventItemLocation;

    }

    static public List<InventoryItemAndDetail> loadInventoryItemDetail(List<EntityCondition> inputFields, XuiSession session) {
        List<InventoryItemAndDetail> listInventItemLocation = new ArrayList<InventoryItemAndDetail>();
        /*
         Map<String, Object> result = null;
         List<Map<String, Object>> tempResults = FastList.newInstance();
         boolean beganTransaction = false;
         try {
         beganTransaction = TransactionUtil.begin();

         // = FastMap.newInstance();
         try {
         //                inputFields.put("facilityId", "Company");
         //        inputFields.put("infoString_ic", caseInsensitiveEmail);
         result = session.getDispatcher().runSync("performFind", UtilMisc.<String, Object>toMap("entityName", "InventoryItemAndDetail",
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
         listInventItemLocation.add(new InventoryItemAndDetail(gv));
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
        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("InventoryItemAndDetail", inputFields, "inventoryItemId", session.getDelegator(), true);
//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gv : resultList) {
//                tempResults.addAll(filteredList);
            listInventItemLocation.add(new InventoryItemAndDetail(gv));
        }
        return listInventItemLocation;

    }

    /*
     public static Map<String, Object> getInventoryAvailableByFacility(String productId, String facilityId, 
     XuiSession session) {

     Map<String, Object> getProductInventoryAvailableResult = FastMap.newInstance();

     try {

     // Get ATP for the product
     getProductInventoryAvailableResult = session.getDispatcher().runSync("getInventoryAvailableByFacility", UtilMisc.toMap("productId", productId, "facilityId", facilityId));
     BigDecimal availableToPromiseTotal = (BigDecimal) getProductInventoryAvailableResult.get("availableToPromiseTotal");
     BigDecimal quantityOnHandTotal = (BigDecimal) getProductInventoryAvailableResult.get("quantityOnHandTotal");

     if (availableToPromiseTotal == null) {
     getProductInventoryAvailableResult.put("availableToPromiseTotal",BigDecimal.ZERO);                
     Debug.logError("availableToPromise is null ", module);
     }

     if (quantityOnHandTotal == null) {
     getProductInventoryAvailableResult.put("quantityOnHandTotal",BigDecimal.ZERO);                                
     Debug.logError("quantityOnHandTotal is null ", module);
     }
     } catch (GenericServiceException e) {
     Debug.logError(e, module);
     }

     return getProductInventoryAvailableResult;
     }
     */
    @Override
    protected List<InventoryItemComposite> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<InventoryItemComposite> persons = new ArrayList<InventoryItemComposite>();
        return persons;
    }

    static public Map<String, Object> loadInventory(Map<String, Object> request) throws GenericEntityException, GenericServiceException {
        //MockHttpServletRequest request = new MockHttpServletRequest();
        String action = "Y";//request.getParameter("action");
        String searchParameterString = "";        
        String statusId = request.containsKey("statusId") ? (String) request.get("statusId") : null;// request.getParameter("statusId");
        String facilityId = request.containsKey("facilityId") ? (String) request.get("facilityId") : null;;
        String monthsInPastLimitStr = null;
        searchParameterString = "action=Y&facilityId=" + facilityId;

        int offsetQOH = -1;
        int offsetATP = -1;
       
        String offsetQOHQty = request.containsKey("offsetQOHQty") ? (String) request.get("offsetQOHQty") : null;
        String offsetATPQty = request.containsKey("offsetATPQty") ? (String) request.get("offsetATPQty") : null;
        String productTypeId = request.containsKey("productTypeId") ? (String) request.get("productTypeId") : null;
        String searchInProductCategoryId = request.containsKey("searchInProductCategoryId") ? (String) request.get("searchInProductCategoryId") : null;;
        String productSupplierId = request.containsKey("productSupplierId") ? (String) request.get("productSupplierId") : null;;
        Timestamp productsSoldThruTimestamp = request.containsKey("productsSoldThruTimestamp") ? (Timestamp) request.get("productsSoldThruTimestamp") : null;;
        String internalName = request.containsKey("internalName") ? (String) request.get("internalName") : null;;
        String productId = request.containsKey("productId") ? (String) request.get("productId") : null;;

//String productSupplierId = null;
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Map<String, Object> context = new HashMap<String, Object>();
        Debug.logInfo("action N", module);
        if (action != null) {
            Debug.logInfo("action Y", module);
            // ------------------------------
            DynamicViewEntity prodView = new DynamicViewEntity();
            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("facilityId", facilityId);

            if (offsetQOHQty != null) {
                try {
                    offsetQOH = Integer.parseInt(offsetQOHQty);
//                    hasOffsetQOH = true;
                    searchParameterString = searchParameterString + "&offsetQOHQty=" + offsetQOH;
                } catch (NumberFormatException nfe) {
                }
            }

            if (offsetATPQty != null) {
                try {
                    offsetATP = Integer.parseInt(offsetATPQty);
//                    hasOffsetATP = true;
                    searchParameterString = searchParameterString + "&offsetATPQty=" + offsetATP;
                } catch (NumberFormatException nfe) {
                }
            }

            prodView.addMemberEntity("PRFA", "ProductFacility");
            prodView.addAliasAll("PRFA", null, null);

            prodView.addMemberEntity("PROD", "Product");
            prodView.addViewLink("PROD", "PRFA", Boolean.FALSE, ModelKeyMap.makeKeyMapList("productId"));
            prodView.addAlias("PROD", "internalName");
            prodView.addAlias("PROD", "isVirtual");
            prodView.addAlias("PROD", "salesDiscontinuationDate");
            if (productTypeId != null) {
                prodView.addAlias("PROD", "productTypeId");
                conditionMap.put("productTypeId", productTypeId);
                searchParameterString = searchParameterString + "&productTypeId=" + productTypeId;
            }

            if (searchInProductCategoryId != null) {
                prodView.addMemberEntity("PRCA", "ProductCategoryMember");
                prodView.addViewLink("PRFA", "PRCA", Boolean.FALSE, ModelKeyMap.makeKeyMapList("productId"));
                prodView.addAlias("PRCA", "productCategoryId");
                conditionMap.put("productCategoryId", searchInProductCategoryId);
                searchParameterString = searchParameterString + "&searchInProductCategoryId=" + searchInProductCategoryId;
            }

            if (productSupplierId != null) {
                prodView.addMemberEntity("SPPR", "SupplierProduct");
                prodView.addViewLink("PRFA", "SPPR", Boolean.FALSE, ModelKeyMap.makeKeyMapList("productId"));
                prodView.addAlias("SPPR", "partyId");
                conditionMap.put("partyId", productSupplierId);
                searchParameterString = searchParameterString + "&productSupplierId=" + productSupplierId;
            }

            // set distinct on so we only get one row per product
            EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
            EntityCondition searchCondition = EntityCondition.makeCondition(conditionMap, EntityOperator.AND);
            EntityCondition notVirtualCondition = EntityCondition.makeCondition(EntityCondition.makeCondition("isVirtual", EntityOperator.EQUALS, null),
                    EntityOperator.OR,
                    EntityCondition.makeCondition("isVirtual", EntityOperator.NOT_EQUAL, "Y"));

            List<EntityCondition> whereConditionsList = UtilMisc.toList(searchCondition, notVirtualCondition);
            // add the discontinuation date condition
            if (productsSoldThruTimestamp != null) {
                EntityCondition discontinuationDateCondition = EntityCondition.makeCondition(
                        UtilMisc.toList(
                                EntityCondition.makeCondition("salesDiscontinuationDate", EntityOperator.EQUALS, null),
                                EntityCondition.makeCondition("salesDiscontinuationDate", EntityOperator.GREATER_THAN, productsSoldThruTimestamp)
                        ),
                        EntityOperator.OR);
                whereConditionsList.add(discontinuationDateCondition);
                searchParameterString = searchParameterString + "&productsSoldThruTimestamp=" + productsSoldThruTimestamp;
            }

            // add search on internal name
            if (internalName != null) {
                whereConditionsList.add(EntityCondition.makeCondition("internalName", EntityOperator.LIKE, "%" + internalName + "%"));
                searchParameterString = searchParameterString + "&internalName=" + internalName;
            }

            // add search on productId
            if (productId != null) {
                whereConditionsList.add(EntityCondition.makeCondition("productId", EntityOperator.LIKE, productId + "%"));
                searchParameterString = searchParameterString + "&productId=" + productId;
            }
            EntityCondition whereCondition = EntityCondition.makeCondition(whereConditionsList, EntityOperator.AND);

            boolean beganTransaction = false;
            List<GenericValue> prods = null;
            try {
                Debug.logInfo("searchParameterString: " + searchParameterString, module);
                Debug.logInfo("prodView: " + prodView.getOneRealEntityName(), module);
                Debug.logInfo("whereCondition: " + whereCondition.toString(), module);
                beganTransaction = TransactionUtil.begin();
                EntityListIterator prodsEli = ControllerOptions.getSession().getDelegator().findListIteratorByCondition(prodView, whereCondition, null, null, UtilMisc.toList("productId"), findOpts);
                prods = prodsEli.getCompleteList();
                prodsEli.close();
            } catch (GenericEntityException e) {
                String errMsg = "Failure in operation, rolling back transaction";
                Debug.logError(e, errMsg, "ViewFacilityInventoryByProduct");
                try {
                    // only rollback the transaction if we started one...
                    TransactionUtil.rollback(beganTransaction, errMsg, e);
                } catch (GenericEntityException e2) {
                    Debug.logError(e2, "Could not rollback transaction: " + e2.toString(), "ViewFacilityInventoryByProduct");
                }
                // after rolling back, rethrow the exception
                throw e;
            } finally {
                // only commit the transaction if we started one... this will throw an exception if it fails
                TransactionUtil.commit(beganTransaction);
            }
            Debug.logInfo("prods: " + prods.size(), module);
            // If the user has specified a number of months over which to sum usage quantities, define the correct timestamp
            Timestamp checkTime = UtilDateTime.nowTimestamp();
            //String monthsInPastLimitStr = request.getParameter("monthsInPastLimit");
            if (monthsInPastLimitStr != null) {
                try {
                    int monthsInPastLimit = Integer.parseInt(monthsInPastLimitStr);
                    com.ibm.icu.util.Calendar cal = UtilDateTime.toCalendar(null);
                    cal.add(Calendar.MONTH, 0 - monthsInPastLimit);
                    checkTime = UtilDateTime.toTimestamp(cal.getTime());
                    searchParameterString += "&monthsInPastLimit=" + monthsInPastLimitStr;
                } catch (Exception e) {
                    // Ignore
                }
            }

            for (GenericValue oneProd : prods) {
                Debug.logInfo("Product id: " + oneProd.getString("productId"), module);
                Map<String, Object> oneInventory = new HashMap<String, Object>();
                Map<String, Object> resultMap = new HashMap<String, Object>();
                oneInventory.put("checkTime", checkTime);
                oneInventory.put("facilityId", facilityId);
                oneInventory.put("productId", oneProd.getString("productId"));
                String minimumStock = oneProd.getString("minimumStock");
                oneInventory.put("minimumStock", minimumStock);
                oneInventory.put("reorderQuantity", oneProd.getString("reorderQuantity"));
                oneInventory.put("daysToShip", oneProd.getString("daysToShip"));

                Map<String, Object> valMap = new HashMap<String, Object>();
                valMap.put("productId", oneProd.getString("productId"));
                valMap.put("minimumStock", minimumStock);
                valMap.put("facilityId", oneProd.getString("facilityId"));
                valMap.put("checkTime", checkTime);
                valMap.put("statusId", statusId);
                boolean beganTrans = false;
                try {
                    Debug.logInfo("searchParameterString: " + searchParameterString, module);
                    Debug.logInfo("prodView: " + prodView.getOneRealEntityName(), module);
                    Debug.logInfo("whereCondition: " + whereCondition.toString(), module);
                    beganTrans = TransactionUtil.begin();

                    resultMap = ControllerOptions.getSession().getDispatcher().runSync("getProductInventoryAndFacilitySummary", valMap);
                } catch (GenericEntityException e) {
                    String errMsg = "Failure in operation, rolling back transaction";
                    Debug.logError(e, errMsg, "ViewFacilityInventoryByProduct");
                    try {
                        // only rollback the transaction if we started one...
                        TransactionUtil.rollback(beganTrans, errMsg, e);
                    } catch (GenericEntityException e2) {
                        Debug.logError(e2, "Could not rollback transaction: " + e2.toString(), "ViewFacilityInventoryByProduct");
                    }
                    // after rolling back, rethrow the exception
                    throw e;
                } finally {
                    // only commit the transaction if we started one... this will throw an exception if it fails
                    TransactionUtil.commit(beganTrans);
                }

                if (resultMap != null) {
                    oneInventory.put("totalAvailableToPromise", resultMap.get("totalAvailableToPromise"));
                    oneInventory.put("totalQuantityOnHand", resultMap.get("totalQuantityOnHand"));
                    oneInventory.put("quantityOnOrder", resultMap.get("quantityOnOrder"));
                    oneInventory.put("offsetQOHQtyAvailable", resultMap.get("offsetQOHQtyAvailable"));
                    oneInventory.put("offsetATPQtyAvailable", resultMap.get("offsetATPQtyAvailable"));
                    oneInventory.put("usageQuantity", resultMap.get("usageQuantity"));
                    oneInventory.put("defultPrice", resultMap.get("defultPrice"));
                    oneInventory.put("listPrice", resultMap.get("listPrice"));
                    oneInventory.put("wholeSalePrice", resultMap.get("wholeSalePrice"));
                    if (offsetQOHQty != null && offsetATPQty != null) {
                        if ((offsetQOHQty != null && (Integer) resultMap.get("offsetQOHQtyAvailable") < offsetQOH) && (offsetATPQty != null && (Integer) resultMap.get("offsetATPQtyAvailable") < offsetATP)) {
                            rows.add(oneInventory);
                        }
                    } else if (offsetQOHQty != null || offsetATPQty != null) {
                        if ((offsetQOHQty != null && (Integer) resultMap.get("offsetQOHQtyAvailable") < offsetQOH) || (offsetATPQty != null && (Integer) resultMap.get("offsetATPQtyAvailable") < offsetATP)) {
                            rows.add(oneInventory);
                        }
                    } else {
                        rows.add(oneInventory);
                    }
                }
            }

        }
        context.put("inventoryByProduct", rows);
        context.put("searchParameterString", searchParameterString);
        return context;
    }
}
