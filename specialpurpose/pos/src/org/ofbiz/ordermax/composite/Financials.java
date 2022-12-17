/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

/**
 *
 * @author siranjeev
 */
public class Financials {
        public static final String PROP_DAYS_30 = "PROP_DAYS_30";
    public static final String PROP_DAYS_60 = "PROP_DAYS_60";
    public static final String PROP_DAYS_90 = "PROP_DAYS_90";
    public static final String PROP_TOTALOUTSTANDING = "PROP_TOTALOUTSTANDING";
    public static final String PROP_ORDERID = "PROP_ORDERID";
    public static final String PROP_ORDERNUMBER = "PROP_ORDERNUMBER";
    public static final String PROP_ORDERREFERNCE = "PROP_ORDERREFERNCE";
    public static final String PROP_ORDERDATE = "PROP_ORDERDATE";
    public static final String PROP_ORDERDELIVERYDATE = "PROP_ORDERDELIVERYDATE";
    public static final String PROP_ENTEREDBY = "PROP_ENTEREDBY";

    public static final String PROP_TOTALCOST = "PROP_TOTALCOST";
    public static final String PROP_GROSSPROFIT = "PROP_GROSSPROFIT";
    public static final String PROP_GROSSPROFITPCNT = "PROP_GROSSPROFITPCNT";
    public static final String PROP_TOTALWEIGHT = "PROP_TOTALWEIGHT";
    public static final String PROP_TOTALVOLUME = "PROP_TOTALVOLUME";
    public static final String PROP_NUMBERITEMS = "PROP_NUMBERITEMS";
    public static final String PROP_ORDERTOTAL = "PROP_ORDERTOTAL";
    public static final String PROP_GSTTOTAL = "PROP_GSTTOTAL";
    public static final String PROP_GSTEXTOTAL = "PROP_GSTEXTOTAL";

    
    private BigDecimal days_30 = BigDecimal.ZERO;
    private BigDecimal days_60 = BigDecimal.ZERO;
    private BigDecimal days_90 = BigDecimal.ZERO;
    private BigDecimal totalOutstanding = BigDecimal.ZERO;
    private BigDecimal totalCost = BigDecimal.ZERO;
    private BigDecimal grossProfit = BigDecimal.ZERO;
    private BigDecimal grossProfitPcnt = BigDecimal.ZERO;
    private BigDecimal totalWeight = BigDecimal.ZERO;
    private BigDecimal totalVolume = BigDecimal.ZERO;
    private BigDecimal numberItems = BigDecimal.ZERO;
    private BigDecimal orderTotal = BigDecimal.ZERO;
    private BigDecimal gstTotal = BigDecimal.ZERO;
    private BigDecimal gstExTotal = BigDecimal.ZERO;
    
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    public Financials()
    {
        
    }
    
    /**
     * @return the days_30
     */
    public BigDecimal getDays_30() {
        return days_30;
    }

    /**
     * @param days_30 the days_30 to set
     */
    public void setDays_30(BigDecimal days_30) {
        java.math.BigDecimal oldDays_30 = days_30;
        this.days_30 = days_30;
        propertyChangeSupport.firePropertyChange(PROP_DAYS_30, oldDays_30, days_30);
    }

    /**
     * @return the days_60
     */
    public BigDecimal getDays_60() {
        return days_60;
    }

    /**
     * @param days_60 the days_60 to set
     */
    public void setDays_60(BigDecimal days_60) {
        java.math.BigDecimal oldDays_60 = days_60;
        this.days_60 = days_60;
        propertyChangeSupport.firePropertyChange(PROP_DAYS_60, oldDays_60, days_60);
    }

    /**
     * @return the days_90
     */
    public BigDecimal getDays_90() {
        return days_90;
    }

    /**
     * @param days_90 the days_90 to set
     */
    public void setDays_90(BigDecimal days_90) {
        java.math.BigDecimal oldDays_90 = days_90;
        this.days_90 = days_90;
        propertyChangeSupport.firePropertyChange(PROP_DAYS_90, oldDays_90, days_90);
    }

    /**
     * @return the totalOutstanding
     */
    public BigDecimal getTotalOutstanding() {
        return totalOutstanding;
    }

    /**
     * @param totalOutstanding the totalOutstanding to set
     */
    public void setTotalOutstanding(BigDecimal totalOutstanding) {
        java.math.BigDecimal oldTotalOutstanding = totalOutstanding;
        this.totalOutstanding = totalOutstanding;
        propertyChangeSupport.firePropertyChange(PROP_TOTALOUTSTANDING, oldTotalOutstanding, totalOutstanding);
    }
    /**
     * @return the totalCost
     */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(BigDecimal totalCost) {
        java.math.BigDecimal oldTotalCost = totalCost;
        this.totalCost = totalCost;
        propertyChangeSupport.firePropertyChange(PROP_TOTALCOST, oldTotalCost, totalCost);
    }

    /**
     * @return the grossProfit
     */
    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    /**
     * @param grossProfit the grossProfit to set
     */
    public void setGrossProfit(BigDecimal grossProfit) {
        java.math.BigDecimal oldGrossProfit = grossProfit;
        this.grossProfit = grossProfit;
        propertyChangeSupport.firePropertyChange(PROP_GROSSPROFIT, oldGrossProfit, grossProfit);
    }

    /**
     * @return the grossProfitPcnt
     */
    public BigDecimal getGrossProfitPcnt() {
        return grossProfitPcnt;
    }

    /**
     * @param grossProfitPcnt the grossProfitPcnt to set
     */
    public void setGrossProfitPcnt(BigDecimal grossProfitPcnt) {
        java.math.BigDecimal oldGrossProfitPcnt = grossProfitPcnt;
        this.grossProfitPcnt = grossProfitPcnt;
        propertyChangeSupport.firePropertyChange(PROP_GROSSPROFITPCNT, oldGrossProfitPcnt, grossProfitPcnt);
    }

    /**
     * @return the totalWeight
     */
    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    /**
     * @param totalWeight the totalWeight to set
     */
    public void setTotalWeight(BigDecimal totalWeight) {
        java.math.BigDecimal oldTotalWeight = totalWeight;
        this.totalWeight = totalWeight;
        propertyChangeSupport.firePropertyChange(PROP_TOTALWEIGHT, oldTotalWeight, totalWeight);
    }

    /**
     * @return the totalVolume
     */
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    /**
     * @param totalVolume the totalVolume to set
     */
    public void setTotalVolume(BigDecimal totalVolume) {
        java.math.BigDecimal oldTotalVolume = totalVolume;
        this.totalVolume = totalVolume;
        propertyChangeSupport.firePropertyChange(PROP_TOTALVOLUME, oldTotalVolume, totalVolume);
    }

    /**
     * @return the numberItems
     */
    public BigDecimal getNumberItems() {
        return numberItems;
    }

    /**
     * @param numberItems the numberItems to set
     */
    public void setNumberItems(BigDecimal numberItems) {
        java.math.BigDecimal oldNumberItems = numberItems;
        this.numberItems = numberItems;
        propertyChangeSupport.firePropertyChange(PROP_NUMBERITEMS, oldNumberItems, numberItems);
    }

    /**
     * @return the orderTotal
     */
    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    /**
     * @param orderTotal the orderTotal to set
     */
    public void setOrderTotal(BigDecimal orderTotal) {
        java.math.BigDecimal oldOrderTotal = orderTotal;
        this.orderTotal = orderTotal;
        propertyChangeSupport.firePropertyChange(PROP_ORDERTOTAL, oldOrderTotal, orderTotal);
    }

    /**
     * @return the gstTotal
     */
    public BigDecimal getGstTotal() {
        return gstTotal;
    }

    /**
     * @param gstTotal the gstTotal to set
     */
    public void setGstTotal(BigDecimal gstTotal) {
        java.math.BigDecimal oldGstTotal = gstTotal;
        this.gstTotal = gstTotal;
        propertyChangeSupport.firePropertyChange(PROP_GSTTOTAL, oldGstTotal, gstTotal);
    }

    /**
     * @return the gstExTotal
     */
    public BigDecimal getGstExTotal() {
        return gstExTotal;
    }

    /**
     * @param gstExTotal the gstExTotal to set
     */
    public void setGstExTotal(BigDecimal gstExTotal) {
        java.math.BigDecimal oldGstExTotal = gstExTotal;
        this.gstExTotal = gstExTotal;
        propertyChangeSupport.firePropertyChange(PROP_GSTEXTOTAL, oldGstExTotal, gstExTotal);
    }
    
}
