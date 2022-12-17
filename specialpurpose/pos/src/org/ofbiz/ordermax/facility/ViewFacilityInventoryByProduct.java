/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.facility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.service.GenericServiceException;

/**
 *
 * @author BBS Auctions
 */
public class ViewFacilityInventoryByProduct {

    public static final String module = ViewFacilityInventoryByProduct.class.getName();

    public Map<String, Object> runReport() throws GenericEntityException, GenericServiceException {
        //MockHttpServletRequest request = new MockHttpServletRequest();
        String action = "Y";//request.getParameter("action");
        String statusId = null;// request.getParameter("statusId");
        String searchParameterString = "";
        String facilityId = "MAX_FAC";
        String monthsInPastLimitStr = null;
        searchParameterString = "action=Y&facilityId=" + facilityId;

        int offsetQOH = -1;
        int offsetATP = -1;
        boolean hasOffsetQOH = false;
        boolean hasOffsetATP = false;
        String offsetQOHQty = null;
        String offsetATPQty = null;
        String productTypeId = "FINISHED_GOOD";
        String searchInProductCategoryId = null;
        String productSupplierId = null;
        Timestamp productsSoldThruTimestamp = null;
        String internalName = null;
        String productId = null;

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
                    hasOffsetQOH = true;
                    searchParameterString = searchParameterString + "&offsetQOHQty=" + offsetQOH;
                } catch (NumberFormatException nfe) {
                }
            }

            if (offsetATPQty != null) {
                try {
                    offsetATP = Integer.parseInt(offsetATPQty);
                    hasOffsetATP = true;
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
