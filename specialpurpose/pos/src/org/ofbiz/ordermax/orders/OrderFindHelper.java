/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.ordermax.entity.Invoice;

/**
 *
 * @author sureshbalan
 */
public class OrderFindHelper {

    public static final String module = OrderFindHelper.class.getName();

    static public List<GenericValue> getPurchaseInvoicesForGivenDatePeriod(Delegator delegator, java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd) {

        EntityListIterator entityListIterator = null;
        boolean beganTx = false;
        String productId = null;
        List<GenericValue> orderList = FastList.newInstance();
        //         Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
        ArrayList<GenericValue> productsList = new ArrayList<GenericValue>();
        try {
            // begin the transaction
            beganTx = TransactionUtil.begin(7200);
            try {

                List<String> orderBy = FastList.newInstance();
                //Set<String> fieldsToSelect = FastSet.newInstance();
                List<String> conditionToSelect = FastList.newInstance();
                // fields we need to select; will be used to set distinct

                orderBy.add("invoiceDate");
                List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, "PURCHASE_INVOICE"));

                EntityCondition cond = EntityCondition.makeCondition(exprs, EntityOperator.AND);
                EntityFindOptions efo = new EntityFindOptions();
                efo.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);

                entityListIterator = delegator.find("Invoice", cond, null, null, orderBy, efo);
                GenericValue invoice;
                while (((invoice = entityListIterator.next()) != null)) {
                    java.sql.Timestamp orderDate = invoice.getTimestamp("invoiceDate");
                    if (orderDate.after(dayStart) && orderDate.before(dayEnd)) {
                        String msg = "INVOICE ID: " + invoice.getString("invoiceId") + " ,Party Id from: " + invoice.getString("partyIdFrom");
                        Debug.logInfo(msg, module);
                        orderList.add(invoice);
                    }
                }

            } catch (GenericEntityException gee) {
                Debug.logWarning(gee, gee.getMessage(), module);
                Map<String, String> messageMap = UtilMisc.toMap("gee", gee.toString());
                throw gee;
            }

        } catch (GenericEntityException e) {
            try {
                TransactionUtil.rollback(beganTx, e.getMessage(), e);
            } catch (Exception e1) {
                Debug.logError(e1, module);
            }
        } catch (Throwable t) {
            Debug.logError(t, module);
            try {
                TransactionUtil.rollback(beganTx, t.getMessage(), t);
            } catch (Exception e2) {
                Debug.logError(e2, module);
            }

        } finally {
            if (entityListIterator != null) {
                try {
                    entityListIterator.close();
                } catch (GenericEntityException gee) {
                    Debug.logError(gee, "Error closing EntityListIterator when indexing product keywords.", module);
                }
            }

            // commit the transaction
            try {
                TransactionUtil.commit(beganTx);
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }

        return orderList;
    }

    static public java.sql.Timestamp noTimeDate(java.sql.Timestamp date) {

        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(cal.getTimeInMillis());
        return zeroedDate;
    }

    static public java.sql.Timestamp noTimeEndOfDayDate(java.sql.Timestamp date) {

        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 23);            // set hour to midnight
        cal.set(Calendar.MINUTE, 59);                 // set minute in hour
        cal.set(Calendar.SECOND, 59);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(cal.getTimeInMillis());
        return zeroedDate;
    }
    
    static public java.sql.Timestamp getEndateForDateWithNoTime(java.sql.Timestamp date) {

/*        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 23);            // set hour to midnight
        cal.set(Calendar.MINUTE, 59);                 // set minute in hour
        cal.set(Calendar.SECOND, 59);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
*/        
        Calendar calEndDate = Calendar.getInstance();       // get calendar instance
        calEndDate.setTime(date);                           // set cal to date
        calEndDate.add(Calendar.DAY_OF_MONTH, -1);             
        
        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(calEndDate.getTimeInMillis());
        
        return noTimeEndOfDayDate(zeroedDate);
    }


    static public List<GenericValue> getSalesInvoicesForGivenDatePeriod(Delegator delegator, java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd) {
        //String errMsg = "";
        if(dayStart==null){
            dayStart = UtilDateTime.nowTimestamp();
        }
        dayStart = noTimeDate(dayStart);
        dayEnd = noTimeEndOfDayDate(dayEnd);

        EntityListIterator entityListIterator = null;
        boolean beganTx = false;
        String productId = null;
        List<GenericValue> orderList = FastList.newInstance();
        //         Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
        ArrayList<GenericValue> productsList = new ArrayList<GenericValue>();
        try {
            // begin the transaction
            beganTx = TransactionUtil.begin(7200);
            try {

                List<String> orderBy = FastList.newInstance();
                //Set<String> fieldsToSelect = FastSet.newInstance();
                List<String> conditionToSelect = FastList.newInstance();
                // fields we need to select; will be used to set distinct

                orderBy.add("invoiceDate");
                List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, "SALES_INVOICE"));

                EntityCondition cond = EntityCondition.makeCondition(exprs, EntityOperator.AND);
                EntityFindOptions efo = new EntityFindOptions();
                efo.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);

                entityListIterator = delegator.find("Invoice", cond, null, null, orderBy, efo);
                GenericValue invoice;
                while (((invoice = entityListIterator.next()) != null)) {
                    java.sql.Timestamp orderDate = invoice.getTimestamp("invoiceDate");
                    if (orderDate.after(dayStart) && orderDate.before(dayEnd)) {
                        String msg = "INVOICE ID: " + invoice.getString("invoiceId") + " ,Party Id from: " + invoice.getString("partyIdFrom");
                        Debug.logInfo(msg, module);
                        orderList.add(invoice);
                    }
                }

            } catch (GenericEntityException gee) {
                Debug.logWarning(gee, gee.getMessage(), module);
                Map<String, String> messageMap = UtilMisc.toMap("gee", gee.toString());
                throw gee;
            }

        } catch (GenericEntityException e) {
            try {
                TransactionUtil.rollback(beganTx, e.getMessage(), e);
            } catch (Exception e1) {
                Debug.logError(e1, module);
            }
        } catch (Throwable t) {
            Debug.logError(t, module);
            try {
                TransactionUtil.rollback(beganTx, t.getMessage(), t);
            } catch (Exception e2) {
                Debug.logError(e2, module);
            }

        } finally {
            if (entityListIterator != null) {
                try {
                    entityListIterator.close();
                } catch (GenericEntityException gee) {
                    Debug.logError(gee, "Error closing EntityListIterator when indexing product keywords.", module);
                }
            }

            // commit the transaction
            try {
                TransactionUtil.commit(beganTx);
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }

        return orderList;
    }

    static public List<GenericValue> getInvoices(Delegator delegator, String invoiceTypeId) {

        List<GenericValue> invoiceListGV = FastList.newInstance();
        //         Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
        try {

            List<String> orderBy = FastList.newInstance();

            // fields we need to select; will be used to set distinct
            orderBy.add("invoiceDate");
            List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, invoiceTypeId));

            EntityCondition cond = EntityCondition.makeCondition(exprs, EntityOperator.AND);
            EntityFindOptions efo = new EntityFindOptions();
            efo.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);
//public List<GenericValue> findList(String entityName, EntityCondition entityCondition, Set<String> fieldsToSelect, List<String> orderBy, EntityFindOptions findOptions, boolean useCache) 
            invoiceListGV = delegator.findList("Invoice", cond, null, orderBy, efo, true);
        } catch (GenericEntityException gee) {
            Debug.logWarning(gee, gee.getMessage(), module);
        }

        return invoiceListGV;
    }

    static public GenericValue getOrder(String orderId, Delegator delegator) {

        GenericValue orderHeader = null;
        //         Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
        try {

            orderHeader = delegator.findByPrimaryKey("OrderHeader",
                    UtilMisc.toMap("orderId", orderId));

        } catch (GenericEntityException gee) {
            Debug.logError(gee, module);
        }

        return orderHeader;
    }

}
