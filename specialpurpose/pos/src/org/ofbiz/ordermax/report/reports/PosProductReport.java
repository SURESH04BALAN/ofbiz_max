/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.basic.BasicException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.datasource.GenericHelperInfo;
import org.ofbiz.entity.jdbc.SQLProcessor;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.entity.OrderHeaderAndPaymentPref;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author BBS Auctions
 */
public class PosProductReport extends NewEntityJasperReport {

    public static final String module = PosProductReport.class.getName();

    public PosProductReport() {
    }

    @Override
    public void loadParameterSelections() {
        ControllerOptions options = new ControllerOptions();
//        com.openbravo.pos.reports.params.JParamHelper.AddReportDateSelection(getQbffilter(), options, "Date Selection", "orderDate");
        try {

            GoodIdentificationType facility = GoodIdentificationTypeSingleton.getGoodIdentificationType("EAN");
            com.openbravo.pos.reports.params.JParamHelper.addReportGoodIdentificationPanel(getQbffilter(), "idValue", "By barcode", "originFacilityId", "Scan Code:", facility);
            
            com.openbravo.pos.reports.params.JParamHelper.AddReportFormId(getQbffilter(), options, "By form");
            
            //com.openbravo.pos.reports.params.JParamHelper.addReportCategoryIdSelection(getQbffilter(), options, "Category Selection");
        } catch (Exception ex) {
            Logger.getLogger(PosProductReport.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PosProductReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "Product");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Pos Product Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "products.jasper";
    }

    @Override
    public String getReportFileName() {
        return "products.jrxml";
    }

    @Override
    public String getResourceFileName() {
        return "products_messages.properties";
    }

    public class ProductData {

        private java.lang.String REFERENCE;
        private java.lang.String NAME;
        private java.lang.Double PRICESELL;
        private java.lang.String CATEGORY;
        private java.lang.String CATEGORYNAME;
        private java.lang.String TAXCAT;
        private java.lang.String TAXCATNAME;
        private java.lang.Double PRICEBUY;

        public java.lang.String getREFERENCE() {
            return REFERENCE;
        }

        public void setREFERENCE(java.lang.String REFERENCE) {
            this.REFERENCE = REFERENCE;
        }

        public java.lang.String getNAME() {
            return NAME;
        }

        public void setNAME(java.lang.String NAME) {
            this.NAME = NAME;
        }

        public java.lang.Double getPRICESELL() {
            return PRICESELL;
        }

        public void setPRICESELL(java.lang.Double PRICESELL) {
            this.PRICESELL = PRICESELL;
        }

        public java.lang.String getCATEGORY() {
            return CATEGORY;
        }

        public void setCATEGORY(java.lang.String CATEGORY) {
            this.CATEGORY = CATEGORY;
        }

        public java.lang.String getCATEGORYNAME() {
            return CATEGORYNAME;
        }

        public void setCATEGORYNAME(java.lang.String CATEGORYNAME) {
            this.CATEGORYNAME = CATEGORYNAME;
        }

        public java.lang.String getTAXCAT() {
            return TAXCAT;
        }

        public void setTAXCAT(java.lang.String TAXCAT) {
            this.TAXCAT = TAXCAT;
        }

        public java.lang.String getTAXCATNAME() {
            return TAXCATNAME;
        }

        public void setTAXCATNAME(java.lang.String TAXCATNAME) {
            this.TAXCATNAME = TAXCATNAME;
        }

        public java.lang.Double getPRICEBUY() {
            return PRICEBUY;
        }

        public void setPRICEBUY(java.lang.Double PRICEBUY) {
            this.PRICEBUY = PRICEBUY;
        }
    }

    class DailySales {

        private java.util.Date DATESTART = UtilDateTime.nowTimestamp();
        private java.util.Date DATEEND = UtilDateTime.nowTimestamp();
        Map<String, ProductData> dataMap = new HashMap<String, ProductData>();
    }

    List<DailySales> dailySalesList = new ArrayList<DailySales>();

    public DailySales getDailySales(java.sql.Timestamp orderDate) {
        for (DailySales sales : dailySalesList) {
            if (sales.DATESTART.getTime() <= orderDate.getTime()
                    && sales.DATEEND.getTime() >= orderDate.getTime()) {
                return sales;
            }
        }
        return null;
    }

    Map<String, ProductData> dataStore = new HashMap<String, ProductData>();

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Delegator delegator = ControllerOptions.getSession().getDelegator();
        Collection result = new ArrayList<ProductData>();

        String sqlStr = "SELECT Distinct PROD.product_id, PROD.internal_name, pp.PRICE, pp.PRODUCT_PRICE_TYPE_ID\n"
                        + "FROM ofbiz.product as PROD\n"
                        + "inner join ofbiz.product_price as pp\n"
                        + "on (PROD.product_id = pp.PRODUCT_ID)\n";
        String scanStr = getGoodIdentificationSql( collectionMap);
        String sqlForm = getProductFormSql(collectionMap);
       /* if (collectionMap.containsKey("JParamsGoodIdentificationSelection")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsGoodIdentificationSelection");
           
           if (values.containsKey("idValue")) {
                idValue = (String) values.get("idValue");
            }
            if (values.containsKey("goodIdentificationTypeId")) {
                goodIdentificationTypeId = (String) values.get("goodIdentificationTypeId");
            }
            if (UtilValidate.isNotEmpty(idValue) && UtilValidate.isNotEmpty(goodIdentificationTypeId)) {

                str = "SELECT Distinct p.product_id, p.internal_name, pp.PRICE, pp.PRODUCT_PRICE_TYPE_ID\n"
                        + "FROM ofbiz.product as p\n"
                        + "inner join ofbiz.product_price as pp\n"
                        + "on (p.product_id = pp.PRODUCT_ID)\n"
                        + "WHERE EXISTS (SELECT *\n"
                        + "              FROM ofbiz.good_identification as gi\n"
                        + "              WHERE gi.ID_VALUE ='" + idValue + "'\n";
                str = str.concat(" AND gi.GOOD_IDENTIFICATION_TYPE_ID = '" + goodIdentificationTypeId + "'\n");
                str = str.concat("                        AND p.PRODUCT_ID = gi.PRODUCT_ID\n"
                        + "                        )");
                str = str.concat(" order by p.product_id, pp.FROM_DATE");
            }

        }*/

       /* if (collectionMap.containsKey("JParamsFormId")) {            
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsFormId");
            sqlForm = getProductCategorySql(values);
            String productCategoryId = null;
            if (values.containsKey("productCategoryId")) {
                productCategoryId = (String) values.get("productCategoryId");
            }

            if (UtilValidate.isNotEmpty(productCategoryId) ) {
                str = "SELECT Distinct p.product_id, p.internal_name, pp.PRICE, pp.PRODUCT_PRICE_TYPE_ID\n"
                        + "FROM ofbiz.product as p\n"
                        + "inner join ofbiz.product_price as pp\n"
                        + "on (p.product_id = pp.PRODUCT_ID)\n"
                        + "WHERE EXISTS ("
                        + " SELECT distinct pc.PRODUCT_ID  FROM ofbiz.product_category_MEMBER as pc\n" 
                        + "     WHERE p.PRODUCT_ID  = pc.PRODUCT_ID AND pc.PRODUCT_CATEGORY_ID = '" + productCategoryId + "'\n"                        
                        + ")";
                str = str.concat(" order by prod.product_id, pp.FROM_DATE");
            }

        }     */   
        
        if(UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)){
            if(UtilValidate.isNotEmpty(scanStr)){
                sqlStr = sqlStr.concat(" WHERE EXISTS ( ");
                sqlStr = sqlStr.concat(scanStr);            
                sqlStr = sqlStr.concat(")");   
            }
            
          if(UtilValidate.isEmpty(scanStr) && UtilValidate.isNotEmpty(sqlForm)){
                sqlStr = sqlStr.concat(" WHERE  ");
                sqlStr = sqlStr.concat(sqlForm);            
               // sqlStr = sqlStr.concat(")");   
            }            
        }
        
        sqlStr = sqlStr.concat(" order by PROD.product_id, pp.FROM_DATE");
        
        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);
        //       String str;
 /*       if (UtilValidate.isEmpty(str)) {
            str = "SELECT Distinct prod.product_id, prod.internal_name, pp.PRICE , pp.PRODUCT_PRICE_TYPE_ID FROM ofbiz.product as prod \n"
                    + "inner join ofbiz.product_price as pp \n"
                    + "on (prod.product_id = pp.PRODUCT_ID)\n"
                    + "order by prod.product_id, pp.FROM_DATE\n";
            //             + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
            //             + "(pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"
            // + "order by prod.product_id, pp.FROM_DATE";
            //if (UtilValidate.isNotEmpty(productId)) {
            //str = str.concat(" AND prod.product_id = " + productId);
            //}
        }
        //str = str.concat(" order by prod.product_id, pp.FROM_DATE");
*/
        sqlproc.prepareStatement(sqlStr);
        ResultSet rs = sqlproc.executeQuery();

        while (rs.next()) {

            String productCode = rs.getString("PRODUCT_ID");
            String internalName = rs.getString("INTERNAL_NAME");
            double defPrice = rs.getDouble("PRICE");
            String productPriceTypeId = rs.getString("PRODUCT_PRICE_TYPE_ID");
            ProductData posCloseData = null;
            if (dataStore.containsKey(productCode)) {
                posCloseData = dataStore.get(productCode);

            } else {
                posCloseData = new ProductData();
                posCloseData.setREFERENCE(productCode);
                posCloseData.setNAME(internalName);
                dataStore.put(productCode, posCloseData);
            }

            if ("DEFAULT_PRICE".equals(productPriceTypeId)) {
                posCloseData.setPRICESELL(defPrice);
            } else if ("AVERAGE_COST".equals(productPriceTypeId)) {
                posCloseData.setPRICEBUY(defPrice);
            }
               // posCloseData.setPRICEBUY(costPrice);

            /*ProductData posCloseData = new ProductData();
             posCloseData.setREFERENCE(productCode);
             posCloseData.setNAME(internalName);
             posCloseData.setPRICESELL(defPrice);
             posCloseData.setPRICEBUY(new Double(0));
             dataStore.put(productCode, posCloseData);*/
            //result.add(posCloseData);
        }

        rs.close();
        sqlproc.close();
        /*
         str = "SELECT Distinct prod.product_id, prod.internal_name, pp.PRICE FROM ofbiz.product as prod \n"
         + "inner join ofbiz.product_price as pp \n"
         + "on (prod.product_id = pp.PRODUCT_ID)\n"
         + "WHERE pp.PRODUCT_PRICE_TYPE_ID = 'AVERAGE_COST'\n";
         //           + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
         //           + "(pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"
         //+ "order by prod.product_id, pp.FROM_DATE";

         if (UtilValidate.isNotEmpty(productId)) {
         str = str.concat(" AND prod.product_id = " + productId);
         }

         str = str.concat(" order by prod.product_id, pp.FROM_DATE");
         sqlproc.prepareStatement(str);
         rs = sqlproc.executeQuery();

         while (rs.next()) {
         String productCode = rs.getString("PRODUCT_ID");
         String internalName = rs.getString("INTERNAL_NAME");
         double costPrice = rs.getDouble("PRICE");
         if (dataStore.containsKey(productCode)) {
         ProductData posCloseData = dataStore.get(productCode);
         posCloseData.PRICEBUY = costPrice;
         } else {
         ProductData posCloseData = new ProductData();
         posCloseData.setREFERENCE(productCode);
         posCloseData.setNAME(internalName);
         posCloseData.setPRICESELL(new Double(0));
         posCloseData.setPRICEBUY(costPrice);
         dataStore.put(productCode, posCloseData);
         }

         //result.add(posCloseData);
         }

         rs.close();
         sqlproc.close();
         */
        result.addAll(dataStore.values());
        List<OrderHeaderAndPaymentPref> results = new ArrayList<OrderHeaderAndPaymentPref>();

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

        /*   String originFacilityId = "SevenHillsStore";//trans.getFacilityId();
         String terminalId = trans.getTerminalId();
         java.sql.Timestamp dayStart = UtilDateTime.nowTimestamp();
         if(collectionMap.containsKey("startDate")){
         dayStart = ( java.sql.Timestamp) collectionMap.get("startDate");
         }
         //dayStart.setYear(2012);
         dayStart = zeroTime(dayStart);
         //dayStart = incrementDate(dayStart, -720);
         java.sql.Timestamp dayEnd = maxTime(UtilDateTime.nowTimestamp());
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
         ProductData ProductData = dailySales.dataMap.get(payment.getpaymentMethodTypeId());
         ProductData.TOTAL += payment.getmaxAmount().doubleValue();
         } else {
         ProductData ProductData = new ProductData();
         ProductData.DATESTART = dailySales.DATESTART;
         ProductData.DATEEND = dailySales.DATEEND;
         ProductData.TOTAL += payment.getmaxAmount().doubleValue();
         ProductData.PAYMENT = payment.getpaymentMethodTypeId();
         ProductData.MONEY = payment.getpaymentMethodTypeId();
         dailySales.dataMap.put(payment.getpaymentMethodTypeId(), posCloseData);
         }
         }
         }
         for (DailySales sales : dailySalesList) {
         result.addAll(sales.dataMap.values());
         }*/

        /*        
         ProductData posCloseData = new ProductData();
         posCloseData.setREFERENCE("Testing PAYMENT 1");
         posCloseData.setNAME("Testing MONEY 1");
         posCloseData.setPRICESELL(new Double(100));
         posCloseData.setPRICEBUY(new Double(100));
         result.add(posCloseData);

         posCloseData = new ProductData();
         posCloseData.setREFERENCE("Testing PAYMENT 2");
         posCloseData.setNAME("Testing MONEY 2");
         posCloseData.setPRICESELL(new Double(200));
         posCloseData.setPRICEBUY(new Double(200));
         result.add(posCloseData);
         */
        return result;
    }
}
