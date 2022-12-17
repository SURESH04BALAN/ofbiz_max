/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.shoppinglist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastList;
import mvc.controller.LoadBaseSwingWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ShoppingList;

/**
 *
 * @author siranjeev
 */
public class LoadShoppingListWorker extends LoadBaseSwingWorker< ShoppingList> {

    public LoadShoppingListWorker(ListAdapterListModel<ShoppingList> invoiceListModel, XuiSession session, Map<String, Object> findOptionMap) {
        super(invoiceListModel, findOptionMap);
        this.session = session;

    }

    public LoadShoppingListWorker(ListAdapterListModel<ShoppingList> invoiceListModel, XuiSession session, List<EntityCondition> entityConditionList) {
        super(invoiceListModel, entityConditionList);
        this.session = session;

    }

    @Override
    protected List<ShoppingList> doInBackground() throws Exception {

        List<ShoppingList> dataList = new ArrayList<ShoppingList>();
        Map<String, Object> result = null;

        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ShoppingList", entityConditionList, "shoppingListId", session.getDelegator(), true);
        Debug.logInfo("resultList " + resultList.size(), module);
        maxProgress = resultList.size();
//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gv : resultList) {
//                tempResults.addAll(filteredList);
            ShoppingList header = new ShoppingList(gv);
            dataList.add(header);
            publish(header);
            if (isCancelled()) {
                break;
            }
        }

        return dataList;
    }
    
    static public List<GenericValue> getShoppingList(Map<String, Object> searchCondMap, final XuiSession session) {
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

            pastDueInvoices = session.getDelegator().findList("ShoppingList", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, UtilMisc.toList("shoppingListId DESC"), null, false);

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadShoppingListWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pastDueInvoices;
    }
    
    @Override
    protected long getTimeToSleep() {
        return 1000; //loadSpeedModel.getValue();
    }
}
