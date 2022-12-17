/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.InvoiceComposite;

/**
 *
 * @author siranjeev
 */
public class OrderFinancialData {

    public static final String module = OrderFinancialData.class.getName();
    private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel;
    private java.util.Date currentDate;

    public OrderFinancialData(ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel, Date invoiceDate) {
        this.invoiceCompositeListModel = invoiceCompositeListModel;
        this.currentDate = getNoTimeDate(invoiceDate);
    }

    public BigDecimal getTotalOutstanding() {
        BigDecimal value = new BigDecimal(0);
        for (InvoiceComposite ic : invoiceCompositeListModel.getList()) {

                value = value.add(ic.getOutstandingAmount());

            Debug.logInfo("value.toString(): " + value.toString(), module);
        }
        return value;

    }
    
    private BigDecimal getTotalOutstanding(java.util.Date date) {
        BigDecimal value = new BigDecimal(0);
        for (InvoiceComposite ic : invoiceCompositeListModel.getList()) {
            java.util.Date noTimeDate = getNoTimeDate(ic.getInvoice().getinvoiceDate());
            if (noTimeDate.equals(date) || noTimeDate.before(date)) {
                value = value.add(ic.getOutstandingAmount());
            }
            Debug.logInfo("value.toString(): " + value.toString(), module);
        }
        return value;

    }

    private BigDecimal getTotalOutstanding(java.util.Date suartDate, java.util.Date endDate) {
        BigDecimal value = new BigDecimal(0);
        for (InvoiceComposite ic : invoiceCompositeListModel.getList()) {
            java.util.Date noTimeDate = getNoTimeDate(ic.getInvoice().getinvoiceDate());
            if ((noTimeDate.equals(suartDate) || noTimeDate.before(suartDate))
                    && noTimeDate.after(endDate)) {

                value = value.add(ic.getOutstandingAmount());
            }
            Debug.logInfo("value.toString(): " + value.toString(), module);
        }
        return value;

    }

    static public java.util.Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(cal.getTime());                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
//        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(cal.getTimeInMillis());        
        return new java.util.Date(cal.getTimeInMillis());
    }

    static public java.util.Date getNoTimeDate(java.util.Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
//        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(cal.getTimeInMillis());        
        return new java.util.Date(cal.getTimeInMillis());
    }

    static public java.util.Date getDate(java.util.Date date, int days) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return new java.util.Date(cal.getTimeInMillis());

    }

    static public java.util.Date get30DaysStart(java.util.Date date) {
        return getDate(date, -30);
    }
    
   static public java.util.Date get60DaysStart(java.util.Date date) {
        return getDate(date, -60);
    }
   
   static public java.util.Date get90DaysStart(java.util.Date date) {
        return getDate(date, -90);
    }

   static public java.util.Date get120DaysStart(java.util.Date date) {
        return getDate(date, -120);
    }
    
    public BigDecimal getZeroTo29DaysTotal() {
        BigDecimal value = BigDecimal.ZERO;

        //30days
        java.util.Date reqCurrStartDate =  currentDate;
        java.util.Date reqCurrEndDate = get30DaysStart(currentDate);
        value = getTotalOutstanding(reqCurrStartDate, reqCurrEndDate);
        return value;
    }

    public BigDecimal get30To59DaysTotal() {
        BigDecimal value = BigDecimal.ZERO;

        //30days
        java.util.Date reqCurrStartDate =  get30DaysStart(currentDate);;
        java.util.Date reqCurrEndDate = get60DaysStart(currentDate);
        value = getTotalOutstanding(reqCurrStartDate, reqCurrEndDate);
        return value;
    }
    
    public BigDecimal get60To89DaysTotal() {
        BigDecimal value = BigDecimal.ZERO;

        //30days
        java.util.Date reqCurrStartDate =  get60DaysStart(currentDate);;
        java.util.Date reqCurrEndDate = get90DaysStart(currentDate);
        value = getTotalOutstanding(reqCurrStartDate, reqCurrEndDate);
        return value;
    }

    public BigDecimal getMoreThan90DaysTotal() {
        BigDecimal value = BigDecimal.ZERO;

        //30days
        java.util.Date reqCurrStartDate =  get90DaysStart(currentDate);;
        value = getTotalOutstanding(reqCurrStartDate);
        return value;
    }
}
