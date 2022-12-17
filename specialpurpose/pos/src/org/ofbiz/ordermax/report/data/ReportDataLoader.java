/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
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
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.entity.OrderHeaderAndPaymentPref;
import org.ofbiz.pos.generic.MapValueTableDataModel;

/**
 *
 * @author BBS Auctions
 */
public class ReportDataLoader {

    public static final String module = ReportDataLoader.class.getName();

    static public List<OrderHeaderAndPaymentPref> getOrderHeaderAndPaymentPrefList(String originFacilityId,
            String terminalId, java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd) {

        List<OrderHeaderAndPaymentPref> results = new ArrayList<OrderHeaderAndPaymentPref>();

        if (dayEnd == null) {
            dayEnd = UtilDateTime.nowTimestamp();
        }

        Debug.logInfo(" dayStart: " + dayStart.toString(), module);
        Debug.logInfo(" closedDate: " + dayEnd.toString(), module);
        Debug.logInfo(" originFacilityId: " + originFacilityId, module);
        Debug.logInfo(" terminalId: " + terminalId, module);

        Delegator delegator = ControllerOptions.getSession().getDelegator();
        List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("originFacilityId", EntityOperator.EQUALS, originFacilityId),
                EntityCondition.makeCondition("terminalId", EntityOperator.EQUALS, terminalId));

        try {
            List<GenericValue> machList = delegator.findList("OrderHeaderAndPaymentPref", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            machList = EntityUtil.orderBy(machList, UtilMisc.toList("orderDate"));

            for (GenericValue genVal : machList) {
                results.add(new OrderHeaderAndPaymentPref(genVal));
            }

        } catch (GenericEntityException e) {
            Debug.logWarning(e.getMessage(), module);
        }

        return results;
    }

}
