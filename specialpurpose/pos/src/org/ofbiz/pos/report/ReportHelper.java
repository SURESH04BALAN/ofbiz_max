/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.report;

import java.util.List;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
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
import org.ofbiz.ordermax.base.OrderMaxOptionPane;

/**
 *
 * @author siranjeev
 */
public class ReportHelper {
    public static final String module = ReportHelper.class.getName();
    static public List<GenericValue> getGenericValueLists(String entity, Delegator delegator) {
        List<GenericValue> shoppingLists = null;

        try {
            shoppingLists = delegator.findList(entity, null, null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        return shoppingLists;
    }
    static public List<GenericValue> getSupplierList(Delegator delegator) {


        List<GenericValue> partyList = null;
        List<GenericValue> resultList = FastList.newInstance();

        // create the dynamic view entity
        DynamicViewEntity dynamicView = new DynamicViewEntity();

        // Person (name + card)
        dynamicView.addMemberEntity("PT", "Party");
        dynamicView.addAlias("PT", "partyId");
        dynamicView.addAlias("PT", "description");        
        dynamicView.addAlias("PT", "statusId");
        dynamicView.addAlias("PT", "partyTypeId");

        dynamicView.addMemberEntity("PR", "PartyRole");
        dynamicView.addAlias("PR", "roleTypeId");
        dynamicView.addViewLink("PT", "PR", Boolean.FALSE, ModelKeyMap.makeKeyMapList("partyId"));

        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;

        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();

        // fields we need to select; will be used to set distinct
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("partyTypeId");
        fieldsToSelect.add("roleTypeId");

        // NOTE: _must_ explicitly allow null as it is not included in a not equal in many databases... odd but true
        // This allows to get all clients when any informations has been entered
        andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "PARTY_DISABLED")));
        andExprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, "SUPPLIER")); // Only persons for now...

        mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
        orderBy.add("partyId");

        Debug.logInfo("In searchSupplierParty mainCond=" + mainCond, module);

        //thoushand suppliers enough for me
        Integer maxRows = 100000;
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            try {
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

            for (GenericValue party : partyList) {
                /*                    Map<String, String> partyMap = FastMap.newInstance();
                 partyMap.put("partyId", party.getString("partyId"));
                 partyMap.put("roleTypeId", party.getString("roleTypeId"));                    
                 */

                GenericValue partyVal = null;
                String partyTypeId = party.getString("partyTypeId");
                String supplierName = null;
                if ("PERSON".equals(partyTypeId)) {
                    try {
                        partyVal = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", party.getString("partyId")));
                        if (partyVal != null) {
                            if (partyVal.getString("lastName") != null) {
                                supplierName = partyVal.getString("lastName");
                            }

                            if (partyVal.getString("firstName") != null) {
                                supplierName = partyVal.getString("lastName") + " " + partyVal.getString("firstName");
                            }
                        }
                    } catch (GenericEntityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if ("PARTY_GROUP".equals(partyTypeId)) {
                    try {
                        partyVal = delegator.findByPrimaryKey("PartyGroup", UtilMisc.toMap("partyId", party.getString("partyId")));
                        if (partyVal != null) {
                            if (partyVal.getString("groupName") != null) {
                                supplierName = partyVal.getString("groupName");
                            }
                        }

                    } catch (GenericEntityException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    continue;
                }

//                Debug.logInfo("partyid: " + party.getString("partyId") + " name " + supplierName, module);

                if (partyVal != null) {
                    party.setString("description", supplierName);
                    resultList.add(party);
                }
            }
        } 
        return resultList;
    }    
    
    // check if product already exists in database
    public static GenericValue getGenericValue(String entityName, String id, String idValue,
            Delegator delegator) {

        GenericValue tmpProductGV = null;

        try {
            tmpProductGV = delegator.findByPrimaryKey(entityName, UtilMisc
                    .toMap(id, idValue));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data from " + entityName + " " + id + "[" + idValue+ "]" , module);
        }

        return tmpProductGV;
    }

}
