/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mvc.controller.LoadBaseSwingWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;

/**
 *
 * @author siranjeev
 */
public class LoadReturnWorker extends LoadBaseSwingWorker< ReturnHeaderComposite> {

    public LoadReturnWorker(ListAdapterListModel<ReturnHeaderComposite> invoiceListModel, XuiSession session, Map<String, Object> findOptionMap) {
        super(invoiceListModel, findOptionMap);
        this.session = session;

    }

    public LoadReturnWorker(ListAdapterListModel<ReturnHeaderComposite> invoiceListModel, XuiSession session, List<EntityCondition> entityConditionList) {
        super(invoiceListModel, entityConditionList);
        this.session = session;

    }

    @Override
    protected List<ReturnHeaderComposite> doInBackground() throws Exception {

        List<ReturnHeaderComposite> dataList = new ArrayList<ReturnHeaderComposite>();
        Map<String, Object> result = null;

        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ReturnHeader", entityConditionList, "returnId", session.getDelegator(), true);
        Debug.logInfo("resultList " + resultList.size(), module);
        maxProgress = resultList.size();
//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gv : resultList) {
//                tempResults.addAll(filteredList);
            ReturnHeaderComposite header = new ReturnHeaderComposite(gv);
            dataList.add(header);
            publish(header);
            if (isCancelled()) {
                break;
            }
        }

        return dataList;
    }
    
    @Override
    protected long getTimeToSleep() {
        return 1000; //loadSpeedModel.getValue();
    }
}
