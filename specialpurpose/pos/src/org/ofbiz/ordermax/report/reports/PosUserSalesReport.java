/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.basic.BasicException;
import java.sql.ResultSet;
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
import org.ofbiz.entity.datasource.GenericHelperInfo;
import org.ofbiz.entity.jdbc.SQLProcessor;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author BBS Auctions
 */
public class PosUserSalesReport extends NewEntityJasperReport {

    public static final String module = PosUserSalesReport.class.getName();

    public PosUserSalesReport() {
    }
    @Override
    public void loadParameterSelections() {
        try {
            ControllerOptions options = new ControllerOptions();
            com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(getQbffilter(), options, "Date Selection", "orderDate");            
        } catch (Exception ex) {
            Logger.getLogger(PosEndOfTheDayReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @Override
    public JPanel runReport() {
        deactivate();
        Debug.logInfo("runreport : " + getCompiledReportPathAndFile(), module);

        setReport(getCompiledReportPathAndFile());
        setResourceBundle(getResourcePathAndFile());
        addConditionList(reportArgs, getEntityConditions());
        try {
            reportArgs.putAll(getValues());
        } catch (BasicException ex) {
            Logger.getLogger(PosUserSalesReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "User Sales Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "usersales.jasper";
    }

    @Override
    public String getReportFileName() {
        return "usersales.jrxml";
    }
    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties
    @Override
    public String getResourceFileName() {
        return "usersales_messages.properties";
    }
//report.setResourceBundle("com/openbravo/reports/usersales_messages");
    public class CustomersDiaryData {

  
        private java.sql.Timestamp STARTDATE;
        private java.sql.Timestamp ENDDATE;                
        private String NAME;
        private double TOTAL;

   



        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public double getTOTAL() {
            return TOTAL;
        }

        public void setTOTAL(double TOTAL) {
            this.TOTAL = TOTAL;
        }

        public java.sql.Timestamp getSTARTDATE() {
            return STARTDATE;
        }

        public void setSTARTDATE(java.sql.Timestamp STARTDATE) {
            this.STARTDATE = STARTDATE;
        }

        public java.sql.Timestamp getENDDATE() {
            return ENDDATE;
        }

        public void setENDDATE(java.sql.Timestamp ENDDATE) {
            this.ENDDATE = ENDDATE;
        }

   
    }


    Map<String, CustomersDiaryData> dataStore = new HashMap<String, CustomersDiaryData>();

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

        java.sql.Timestamp dayStart = UtilDateTime.nowTimestamp();
        if (collectionMap.containsKey("startDate")) {
            dayStart = (java.sql.Timestamp) collectionMap.get("startDate");
        }
        //dayStart.setYear(2012);
        dayStart = zeroTime(dayStart);

// gets the helper (localmysql, localpostgres, etc.) for your entity group org.ofbiz
        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);

        String str = "SELECT Distinct prod.product_id, prod.internal_name, pp.PRICE FROM ofbiz.product as prod \n"
                + "inner join ofbiz.product_price as pp \n"
                + "on (prod.product_id = pp.PRODUCT_ID)\n"
                + "WHERE pp.PRODUCT_PRICE_TYPE_ID = 'DEFAULT_PRICE'\n"
                //             + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
                //             + "(pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"
                + "order by prod.product_id, pp.FROM_DATE";

        sqlproc.prepareStatement(str);
        ResultSet rs = sqlproc.executeQuery();

        Collection result = new ArrayList<CustomersDiaryData>();
        while (rs.next()) {
            String productCode = rs.getString("PRODUCT_ID");
            String internalName = rs.getString("INTERNAL_NAME");
            double defPrice = rs.getDouble("PRICE");
            CustomersDiaryData posCloseData = new CustomersDiaryData();
            //posCloseData.setTICKETID(productCode);
            posCloseData.setNAME(internalName);
            //posCloseData.setUNITS(defPrice);
            posCloseData.setTOTAL(new Double(0));
            dataStore.put(productCode, posCloseData);
            //result.add(posCloseData);
        }

        rs.close();
        sqlproc.close();
       
        result.addAll(dataStore.values());
/*        List<OrderHeaderAndPaymentPref> results = new ArrayList<OrderHeaderAndPaymentPref>();

        genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, entityConditionList, orderBy, delegator);

        for (GenericValue genVal : genList) {
            results.add(new OrderHeaderAndPaymentPref(genVal));
        }
*/
        String invoiceId = "";

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

        CustomersDiaryData posCloseData = new CustomersDiaryData();
//        posCloseData.setPAYMENT("Testing PAYMENT 1");
        posCloseData.setNAME("Testing MONEY 1");
        //posCloseData.setUNITS(new Double(100));
        posCloseData.setTOTAL(new Double(100));
        result.add(posCloseData);

        posCloseData = new CustomersDiaryData();
//        posCloseData.setPAYMENT("Testing PAYMENT 2");
        posCloseData.setNAME("Testing MONEY 2");
        //posCloseData.setUNITS(new Double(200));
        posCloseData.setTOTAL(new Double(200));
        result.add(posCloseData);

        return result;
    }
}
