/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.entity.ShoppingListItem;
import org.ofbiz.ordermax.product.feature.ProductFeatureMaintainTreeController;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.LocalDispatcher;

/**
 *
 * @author BBS Auctions
 */
public class EntityPersistanceFactory {

    static String module = EntityPersistanceFactory.class.getName();

    static public boolean saveEntity(GenericSaveInterface genericSaveInterface, ControllerOptions options) {
        boolean result = false;
        String entityName = null;
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }
        if (entityName != null && UtilValidate.isNotEmpty(entityName)) {
            EntitySaveInterface entitySave = null;
            if (entityName.equalsIgnoreCase("ShoppingListItem")) {
                entitySave = new ShoppingListItemEntitySave();
            } else if (entityName.equalsIgnoreCase("ShoppingList")) {
                entitySave = new ShoppingListEntitySave();
            } else if (entityName.equalsIgnoreCase("ProductFeatureApplBulk")) {
                entitySave = new ProductFeatureApplBulkEntitySave();
                entityName = "ProductFeatureAppl";
            } else {
                entitySave = new GenericEntitySave();
            }
            result = entitySave.saveEntity(genericSaveInterface, entityName);
        }
        return result;
    }

    static public boolean deleteEntity(GenericValueInterface genericSaveInterface, ControllerOptions options) {
        boolean result = false;
        String entityName = null;
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }
        if (entityName != null && UtilValidate.isNotEmpty(entityName)) {
            EntitySaveInterface entitySave = null;
            if (entityName.equalsIgnoreCase("ShoppingListItem")) {
                entitySave = new ShoppingListItemEntitySave();
            } else if (entityName.equalsIgnoreCase("ShoppingList")) {
                entitySave = new ShoppingListEntitySave();
            } else {
                entitySave = new GenericEntitySave();
            }

            result = entitySave.deleteEntity(genericSaveInterface, entityName);
        }
        return result;
    }

    static public class GenericEntitySave implements EntitySaveInterface {

        protected boolean isRecordExists(GenericSaveInterface genericSaveInterface, String entityName) {
            boolean result = false;
            Map<String, Object> values = genericSaveInterface.getValuesMap();
            List<String> keys = genericSaveInterface.getKey();
            Map<String, Object> keyValues = new HashMap<String, Object>();
            Object value = null;
            Debug.logInfo(" Print 3", module);
            Debug.logInfo(" Print 4:  " + values, module);
            List<EntityExpr> contactList = new ArrayList<EntityExpr>();

            for (String key : keys) {
                if (values.containsKey(key)) {
                    value = values.get(key);
                    keyValues.put(key, value);
                    Debug.logInfo(" key: " + key + " value:  " + value + " entityName: " + entityName, module);
                }

                if (UtilValidate.isNotEmpty(value) && UtilValidate.isNotEmpty(entityName)) {
                    contactList.add(EntityCondition.makeCondition(key, EntityOperator.EQUALS, value));
                }
            }

            Delegator delegator = ControllerOptions.getSession().getDelegator();

            List<GenericValue> machList = null;
            if (!contactList.isEmpty()) {
                try {
                    machList = delegator.findList(entityName, EntityCondition.makeCondition(contactList, EntityOperator.AND), null, null, null, false);
                    if (machList != null) {
                        result = !machList.isEmpty();
                    }
                } catch (GenericEntityException ex) {
                    Debug.logError(ex, module);
                }
            }
            //      machList = EntityUtil.filterByDate(machList);

            return result;
        }

        public boolean saveEntity(GenericSaveInterface genericSaveInterface, String entityName) {
            boolean result = true;
            Map<String, Object> values = genericSaveInterface.getValuesMap();
            List<String> keys = genericSaveInterface.getKey();
            Map<String, String> keyValues = new HashMap<String, String>();
            String value = null;
            Debug.logInfo(" Print 3", module);
            Debug.logInfo(" Print 4:  " + values, module);
            List<EntityExpr> contactList = new ArrayList<EntityExpr>();
            if (UtilValidate.isNotEmpty(values)) {

                Delegator delegator = ControllerOptions.getSession().getDelegator();

                if (!isRecordExists(genericSaveInterface, entityName)) {
                    try {
                        Debug.logInfo("insert entity", module);
                        TransactionUtil.begin();
                        if (!keys.isEmpty()) {
                            String id = delegator.getNextSeqId(entityName);
                            values.put(keys.get(0), id);
                        }
                        delegator.create(entityName, values);
                        TransactionUtil.commit();
                    } catch (GenericTransactionException ex) {
                        Debug.logError(ex, module);
                        result = false;
                    } catch (GenericEntityException ex) {
                        Debug.logError(ex, module);
                        result = false;

                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                        result = false;
                    }
                } else {
                    Debug.logInfo("update entity", module);
                    try {
                        if (genericSaveInterface.getGenericValueObj() != null) {
                            Debug.logInfo("update getGenericValueObj " + genericSaveInterface.getGenericValueObj(), module);
                            delegator.store(genericSaveInterface.getGenericValueObj());
                        }
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                        result = false;
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                        result = false;
                    }
                }
            }
            return result;
        }

        @Override
        public boolean deleteEntity(GenericValueInterface genericSaveInterface, String entityName) {

            Map<String, Object> values = genericSaveInterface.getValuesMap();

            Map<String, String> keyValues = new HashMap<String, String>();
            String value = null;
            boolean result = false;
            List<EntityExpr> contactList = new ArrayList<EntityExpr>();
            if (UtilValidate.isNotEmpty(values)) {

                Delegator delegator = ControllerOptions.getSession().getDelegator();

                try {
                    if (genericSaveInterface.getGenericValueObj() != null) {
                        delegator.removeValue(genericSaveInterface.getGenericValueObj());
                        result = true;
                    }
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(e.toString());
                    OrderMaxOptionPane.showMessageDialog(null, stringBuilder);//,
                    //JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);       
                    result = false;
                }
            }

            return result;
        }

        static public Map<String, Object> runEntityService(String serviceName, Map<String, Object> inMap) throws Exception {
            // create the return header
            GenericValue userLogin = ControllerOptions.getSession().getUserLogin();
            LocalDispatcher dispatcher = ControllerOptions.getSession().getDispatcher();

            inMap.put("userLogin", userLogin);
            // approve the return
            Map<String, Object> paymentResults = dispatcher.runSync(serviceName, inMap);
            if (OrderMaxUtility.handleServiceReturn("Running service " + serviceName, paymentResults) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                return paymentResults;
            }
            return null;
        }

    }

    static public class ShoppingListItemEntitySave extends GenericEntitySave {

        @Override
        public boolean saveEntity(GenericSaveInterface genericSaveInterface, String entityName) {
            boolean result = true;
            Map<String, Object> values = genericSaveInterface.getValuesMap();

            if (UtilValidate.isNotEmpty(values)) {

                if (!isRecordExists(genericSaveInterface, entityName)) {
                    try {
                        Debug.logInfo("Insert createShoppingListItem: " + entityName, module);
                        Map<String, Object> tmpValues = new HashMap<String, Object>();
                        tmpValues.putAll(values);
                        tmpValues.remove("shoppingListItemSeqId");
                        Map<String, Object> resultVal = runEntityService("createShoppingListItem", tmpValues);
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                } else {
                    try {
                        Debug.logInfo("Update updateShoppingListItem: " + entityName, module);
                        Map<String, Object> resultVal = runEntityService("updateShoppingListItem", values);
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                }
            }
            return result;
        }

        public boolean deleteEntity(GenericValueInterface genericSaveInterface, String entityName) {

            boolean result = true;
            Map<String, Object> values = genericSaveInterface.getValuesMap();
            Debug.logInfo("deleteEntity values: " + values, module);
            if (UtilValidate.isNotEmpty(values)) {

                try {
                    Map<String, Object> tmpValues = new HashMap<String, Object>();
                    tmpValues.put("shoppingListId", values.get("shoppingListId"));
                    tmpValues.put("shoppingListItemSeqId", values.get("shoppingListItemSeqId"));
                    Map<String, Object> resultVal = runEntityService("removeShoppingListItem", tmpValues);
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }

            }
            return result;
        }
    }

    static public class ShoppingListEntitySave extends GenericEntitySave {

        @Override
        public boolean saveEntity(GenericSaveInterface genericSaveInterface, String entityName) {
            boolean result = true;
            Map<String, Object> values = genericSaveInterface.getValuesMap();

            if (UtilValidate.isNotEmpty(values)) {

                if (!isRecordExists(genericSaveInterface, entityName)) {
                    try {
                        Debug.logInfo("Insert createShoppingList: " + entityName, module);
                        Map<String, Object> tmpValues = new HashMap<String, Object>();
                        tmpValues.putAll(values);
                        tmpValues.remove("shoppingListId");
                        Map<String, Object> resultVal = runEntityService("createShoppingList", tmpValues);
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                } else {
                    try {
                        Debug.logInfo("Update updateShoppingListItem: " + entityName, module);
                        Map<String, Object> resultVal = runEntityService("updateShoppingList", values);
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                }
            }
            return result;
        }

        @Override
        public boolean deleteEntity(GenericValueInterface genericSaveInterface, String entityName) {

            boolean result = true;
            Map<String, Object> values = genericSaveInterface.getValuesMap();

            if (UtilValidate.isNotEmpty(values)) {
                Debug.logInfo("deleteEntity entityName: " + entityName + " values: " + values, module);

                try {
                    //delete all the shopping list item

                    ControllerOptions options = new ControllerOptions();
                    options.put("keys", UtilMisc.toMap("shoppingListId", values.get("shoppingListId")));
                    List<ShoppingListItem> genList = new LoadGenericValues<ShoppingListItem>().loadValues(options);
                    ShoppingListItemEntitySave listItems = new ShoppingListItemEntitySave();
                    for (ShoppingListItem genVal : genList) {
                        listItems.deleteEntity(genVal, "ShoppingListItem");
                    }

                    Map<String, Object> tmpValues = new HashMap<String, Object>();
                    tmpValues.put("shoppingListId", values.get("shoppingListId"));
                    Map<String, Object> resultVal = runEntityService("removeShoppingList", tmpValues);
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
            return result;
        }
    }

    static public class ProductFeatureApplBulkEntitySave extends GenericEntitySave {

        @Override
        protected boolean isRecordExists(GenericSaveInterface gSaveInterface, String entityName) {
            boolean result = false;
            if (gSaveInterface instanceof ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate) {

                ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate concreteVal = (ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate) gSaveInterface;
                //check for valid data
                if (UtilValidate.isNotEmpty(concreteVal.getOldPoductFeatureAppl().getProductId())
                        && UtilValidate.isNotEmpty(concreteVal.getOldPoductFeatureAppl().getProductFeatureId())
                        && UtilValidate.isNotEmpty(concreteVal.getOldPoductFeatureAppl().getFromDate())) {

                    Map<String, Object> values = concreteVal.getOldPoductFeatureAppl().getValuesMap();
                    List<String> keys = gSaveInterface.getKey();
                    Map<String, Object> keyValues = new HashMap<String, Object>();
                    Object value = null;
                    Debug.logInfo(" Print 3", module);
                    Debug.logInfo(" Print 4:  " + values, module);
                    List<EntityExpr> contactList = new ArrayList<EntityExpr>();
                    Debug.logInfo(" keys: " + keys + " entityName: " + entityName, module);
                    for (String key : keys) {
                        Debug.logInfo(" key: " + key + " values exists : " + values.containsKey(key), module);
                        if (values.containsKey(key)) {
                            value = values.get(key);
                            keyValues.put(key, value);
                            Debug.logInfo(" key: " + key + " value:  " + value + " entityName: " + entityName, module);
                        }

                        if (UtilValidate.isNotEmpty(value) && UtilValidate.isNotEmpty(entityName)) {
                            contactList.add(EntityCondition.makeCondition(key, EntityOperator.EQUALS, value));
                        }
                    }
                    Debug.logInfo(" keyValues: " + keyValues + " entityName: " + entityName, module);
                    Delegator delegator = ControllerOptions.getSession().getDelegator();

                    List<GenericValue> machList = null;
                    if (!contactList.isEmpty()) {
                        try {
                            machList = delegator.findList(entityName, EntityCondition.makeCondition(contactList, EntityOperator.AND), null, null, null, false);
                            if (machList != null) {
                                result = !machList.isEmpty();
                            }
                        } catch (GenericEntityException ex) {
                            Debug.logError(ex, module);
                        }
                    }
                }
            }
            return result;
        }

        @Override
        public boolean saveEntity(GenericSaveInterface genericSaveInterface, String entityName) {
            boolean result = true;

            if (isRecordExists(genericSaveInterface, entityName)) {
                deleteEntity(genericSaveInterface, "ProductFeatureAppl");
            }

            Map<String, Object> values = genericSaveInterface.getValuesMap();

            if (UtilValidate.isNotEmpty(values)) {

                try {
                    Debug.logInfo("Insert ProductFeatureApplEntitySave: " + entityName, module);
                    Map<String, Object> tmpValues = new HashMap<String, Object>();
                    tmpValues.putAll(values);
                    tmpValues.remove("recurringAmount");
                    Map<String, Object> resultVal = runEntityService("applyFeatureToProduct", tmpValues);
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
                /* } else {
                 try {
                 Debug.logInfo("Update updateShoppingListItem: " + entityName, module);
                 Map<String, Object> resultVal = runEntityService("updateShoppingListItem", values);
                 } catch (Exception ex) {
                 Debug.logError(ex, module);
                 }
                 }*/
            }
            return result;
        }

        @Override
        public boolean deleteEntity(GenericValueInterface gSaveInterface, String entityName) {

            boolean result = true;
            if (gSaveInterface instanceof ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate) {
                ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate concreteVal = (ProductFeatureMaintainTreeController.ProductFeatureApplBulkUpdate) gSaveInterface;
                Map<String, Object> values = concreteVal.getOldPoductFeatureAppl().getValuesMap();
                Debug.logInfo("deleteEntity values: " + values, module);
                if (UtilValidate.isNotEmpty(values)) {

                    try {
                        Map<String, Object> tmpValues = new HashMap<String, Object>();
                        tmpValues.put("productId", values.get("productId"));
                        tmpValues.put("productFeatureId", values.get("productFeatureId"));
                        tmpValues.put("fromDate", values.get("fromDate"));
                        Map<String, Object> resultVal = runEntityService("removeFeatureFromProduct", tmpValues);
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }

                }
            }
            return result;
        }
    }

}
