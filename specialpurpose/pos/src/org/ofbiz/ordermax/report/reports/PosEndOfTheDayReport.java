/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.basic.BasicException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.OrderHeaderAndPaymentPref;
import org.ofbiz.ordermax.entity.PosTerminal;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.product.productstore.PosTerminalSingleton;
import org.ofbiz.ordermax.report.data.ReportDataLoader;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author BBS Auctions
 */
public class PosEndOfTheDayReport extends NewEntityJasperReport {

    public static final String module = PosEndOfTheDayReport.class.getName();

    public PosEndOfTheDayReport() {
    }
    /*
     @Override
     public JPanel runReport() {
     launchreport(null);
     final JasperPrint jasperPrint = currReport.runReport(resultMap);
     if (jasperPrint != null) {
     panelReport.removeAll();
     JRViewer viewer = new JRViewer(jasperPrint);
     /*Rectangle rec = viewer.getBounds();
     rec.width = 900;
     rec.height = 700;
     viewer.setBounds(rec);
     panelReport.setLayout(new BorderLayout());
     viewer.setPreferredSize(new Dimension(getSize()));
     JScrollPane reportScroll = new JScrollPane(viewer);            
     panelReport.add(viewer,BorderLayout.CENTER);
     panelReport.invalidate();
     tabReportPane.setSelectedIndex(1);
     this.repaint();
     }
     return this;
     }*/

    @Override
    public void loadParameterSelections() {
        try {
            ControllerOptions options = new ControllerOptions();
            com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(getQbffilter(), options, "Date Selection", "orderDate");
            PosTransaction pos = PosTransaction.getCurrentTx(XuiContainer.getSession());
            PosTerminal posTerminal = PosTerminalSingleton.getPosTerminal(pos.getTerminalId());
            Facility facility = FacilitySingleton.getFacility(pos.getFacilityId());
            com.openbravo.pos.reports.params.JParamHelper.addReportSelectionComboPanel(getQbffilter(), "facilityId", FacilitySingleton.getValueList(), "Category Selection", "originFacilityId", "Facility Name:", facility);
            com.openbravo.pos.reports.params.JParamHelper.addReportSelectionComboPanel(getQbffilter(), "posTerminalId", PosTerminalSingleton.getValueList(), "Terminal Id:", "terminalId", "Terminal Id:", posTerminal);
        } catch (Exception ex) {
            Logger.getLogger(PosEndOfTheDayReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public JPanel runReport() {
        deactivate();
//        System.out.println("getCompiledReportPathAndFile() : " + getCompiledReportPathAndFile());
//        System.out.println("setReport : " + "C:\\ofbiz\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\com\\openbravo\\pos\\reports\\closedpos.jasper");
        //setReport("C:\\ofbiz\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\com\\openbravo\\pos\\reports\\closedpos.jasper");
        setReport(getCompiledReportPathAndFile());
        setResourceBundle(getResourcePathAndFile());
        addConditionList(reportArgs, getEntityConditions());
        try {
            reportArgs.putAll(getValues());
        } catch (BasicException ex) {
            Logger.getLogger(PosEndOfTheDayReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "End Of The Day Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "closedpos.jasper";
    }

    @Override
    public String getReportFileName() {
        return "closedpos.jrxml";
    }

    @Override
    public String getResourceFileName() {
        return "closedpos_messages.properties";
    }

    public class PosCloseData {

        private double TOTAL = 0;
        private String HOST;
        private String MONEY = "MONEY";
        private java.util.Date DATESTART = UtilDateTime.nowTimestamp();
        private java.util.Date DATEEND = UtilDateTime.nowTimestamp();
        private String PAYMENT = "PAYMENT";
        private Integer SEQUENCE = 0;

        public double getTOTAL() {
            return TOTAL;
        }

        public void setTOTAL(double TOTAL) {
            this.TOTAL = TOTAL;
        }

        public String getHOST() {
            return HOST;
        }

        public void setHOST(String HOST) {
            this.HOST = HOST;
        }

        public String getMONEY() {
            return MONEY;
        }

        public void setMONEY(String MONEY) {
            this.MONEY = MONEY;
        }

        public java.util.Date getDATESTART() {
            return DATESTART;
        }

        public void setDATESTART(java.util.Date DATESTART) {
            this.DATESTART = DATESTART;
        }

        public java.util.Date getDATEEND() {
            return DATEEND;
        }

        public void setDATEEND(java.util.Date DATEEND) {
            this.DATEEND = DATEEND;
        }

        public String getPAYMENT() {
            return PAYMENT;
        }

        public void setPAYMENT(String PAYMENT) {
            this.PAYMENT = PAYMENT;
        }

        public Integer getSEQUENCE() {
            return SEQUENCE;
        }

        public void setSEQUENCE(Integer SEQUENCE) {
            this.SEQUENCE = SEQUENCE;
        }

    }

    class DailySales {

        private java.util.Date DATESTART = UtilDateTime.nowTimestamp();
        private java.util.Date DATEEND = UtilDateTime.nowTimestamp();
        Map<String, PosCloseData> dataMap = new HashMap<String, PosCloseData>();
    }

    List<DailySales> dailySalesList = new ArrayList<DailySales>();

    private DailySales getDailySales(java.sql.Timestamp orderDate) {
        for (DailySales sales : dailySalesList) {
            if (sales.DATESTART.getTime() <= orderDate.getTime()
                    && sales.DATEEND.getTime() >= orderDate.getTime()) {
                return sales;
            }
        }
        return null;
    }

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        String entityName = (String) collectionMap.get(EntityName);
        Delegator delegator = ControllerOptions.getSession().getDelegator();
        String orderBy = (String) collectionMap.get(OrderByClause);
        List<GenericValue> genList = null;

        List<EntityCondition> entityConditionList = null;
        if (collectionMap.containsKey(EntityConditionList)) {
            entityConditionList = (List<EntityCondition>) collectionMap.get(EntityConditionList);
        }

        List<OrderHeaderAndPaymentPref> results = new ArrayList<OrderHeaderAndPaymentPref>();

        genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, entityConditionList, orderBy, delegator);

        for (GenericValue genVal : genList) {
            results.add(new OrderHeaderAndPaymentPref(genVal));
        }

        String invoiceId = "";
        Collection result = new ArrayList<PosCloseData>();
//        InvoiceComposite invoiceComposite = null;
        String partyId = null;
        String invoiceTypeId = null;
        /*      try {
         throw new Exception("Invalid invoice number: getBeanCollection");
         } catch (Exception e) {
         Debug.logError(e, module);
         }
         */
        PosTransaction trans = PosTransaction.getCurrentTx(session);

        GenericValue state = null;
        if (state == null) {
            state = trans.getTerminalState();
        }

        String originFacilityId = "SevenHillsStore";//trans.getFacilityId();
        String terminalId = trans.getTerminalId();
        java.sql.Timestamp dayStart = zeroTime(UtilDateTime.nowTimestamp());
        java.sql.Timestamp dayEnd = maxTime(UtilDateTime.nowTimestamp());
        if (collectionMap.containsKey("JParamsStartAndEndDatesInterval")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsStartAndEndDatesInterval");
            if (values.containsKey("startDate")) {
                dayStart = (java.sql.Timestamp) values.get("startDate");
            }
            if (values.containsKey("endDate")) {
                dayEnd = (java.sql.Timestamp) values.get("endDate");
            }            
        }
        //dayStart.setYear(2012);
        //dayStart = zeroTime(dayStart);
        //dayStart = incrementDate(dayStart, -720);
        //java.sql.Timestamp dayEnd = maxTime(UtilDateTime.nowTimestamp());
        Debug.logInfo("dayStart : " + dayStart
                + "  dayEnd: " + dayEnd, module);
        java.sql.Timestamp tmpDate = new java.sql.Timestamp(dayStart.getTime());
        while (tmpDate.before(dayEnd)) {
            DailySales dailySales = new DailySales();
            dailySales.DATESTART = new java.sql.Timestamp(tmpDate.getTime());
            dailySales.DATEEND = maxTime(tmpDate);
            dailySalesList.add(dailySales);
            tmpDate = incrementDate(tmpDate, 1);
            Debug.logInfo("DailySales dailySales.DATESTART : " + dailySales.DATESTART.toString()
                    + "  dailySales.DATEEND: " + dailySales.DATEEND, module);

        }
        Debug.logInfo("dailySalesList size : " + dailySalesList.size(), module);

        // List<OrderHeaderAndPaymentPref> 
        //results = ReportDataLoader.getOrderHeaderAndPaymentPrefList(originFacilityId, terminalId, dayStart, dayEnd);
        for (OrderHeaderAndPaymentPref payment : results) {
            GenericValue ohpp = payment.getGenericValueObj();
            String pmt = ohpp.getString("paymentMethodTypeId");
            BigDecimal amt = ohpp.getBigDecimal("maxAmount");
            String pmt1 = ohpp.getString("orderId");
            String pmt2 = ohpp.getString("orderDate");
            String pmt3 = ohpp.getString("originFacilityId");
            String pmt4 = ohpp.getString("productStoreId");
            String pmt5 = ohpp.getString("terminalId");
            String pmt6 = ohpp.getString("webSiteId");
            String pmt7 = ohpp.getString("currencyUom");
            String pmt8 = ohpp.getString("orderPaymentPreferenceId");

            String pmt9 = ohpp.getString("orderStatusId");
            String pmt10 = ohpp.getString("paymentStatusId");
            String msg = "paymentMethodTypeId: " + pmt + " ,orderId: " + pmt1 + " ,orderDate: " + pmt2 + " ,originFacilityId: "
                    + pmt3 + " ,productStoreId: " + pmt4 + " ,terminalId: " + pmt5 + " ,webSiteId: " + pmt6 + " ,currencyUom: " + pmt7 + " ,orderPaymentPreferenceId: " + pmt8 + " ,orderStatusId " + pmt9
                    + " ,paymentStatusId " + pmt10 + " ,maxAmount: " + amt.toString();
            Debug.logInfo(msg, module);

            DailySales dailySales = getDailySales(payment.getorderDate());
            if (dailySales != null) {
                if (dailySales.dataMap.containsKey(payment.getpaymentMethodTypeId())) {
                    PosCloseData posCloseData = dailySales.dataMap.get(payment.getpaymentMethodTypeId());
                    posCloseData.TOTAL += payment.getmaxAmount().doubleValue();
                    posCloseData.HOST = payment.getGenericValueObj().getString("createdByUserLogin");
                } else {
                    PosCloseData posCloseData = new PosCloseData();
                    posCloseData.DATESTART = dailySales.DATESTART;
                    posCloseData.DATEEND = dailySales.DATEEND;
                    posCloseData.TOTAL += payment.getmaxAmount().doubleValue();
                    posCloseData.PAYMENT = payment.getpaymentMethodTypeId();
                    posCloseData.MONEY = payment.getpaymentMethodTypeId();
                    dailySales.dataMap.put(payment.getpaymentMethodTypeId(), posCloseData);
                }
            }
        }
        for (DailySales sales : dailySalesList) {
            result.addAll(sales.dataMap.values());
        }

        /*      PosCloseData posCloseData = new PosCloseData();
         posCloseData.PAYMENT = "Testing PAYMENT 1";
         posCloseData.MONEY = "Testing MONEY 1";
         posCloseData.TOTAL = 100;
         result.add(posCloseData);

         posCloseData = new PosCloseData();
         posCloseData.TOTAL = 200;
         posCloseData.PAYMENT = "Testing PAYMENT 2";
         posCloseData.MONEY = "Testing MONEY 2";
         result.add(posCloseData);
         */
        return result;
    }
}
