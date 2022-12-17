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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.entity.OrderHeaderAndPaymentPref;
import org.ofbiz.ordermax.entity.OrderReportView;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author BBS Auctions
 */
public class PosClosedProductsReport extends NewEntityJasperReport {

    public static final String module = PosClosedProductsReport.class.getName();

    public PosClosedProductsReport() {
    }

    @Override
    public void loadParameterSelections() {
        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(getQbffilter(), options, "Date Selection", "orderDate");
        GoodIdentificationType facility;
        try {
            facility = GoodIdentificationTypeSingleton.getGoodIdentificationType("EAN");
            com.openbravo.pos.reports.params.JParamHelper.addReportGoodIdentificationPanel(getQbffilter(), "idValue", "By barcode", "originFacilityId", "Scan Code:", facility);
        } catch (Exception ex) {
            Logger.getLogger(PosClosedProductsReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        com.openbravo.pos.reports.params.JParamHelper.AddReportFormId(getQbffilter(), options, "By form");

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
            Logger.getLogger(PosClosedProductsReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderReportView");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Closed Products Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "closedproducts.jasper";
    }

    @Override
    public String getReportFileName() {
        return "closedproducts.jrxml";
    }

    @Override
    public String getResourceFileName() {
        return "closedproducts_messages.properties";
    }

    public class ClosedProductData {

        private String HOST;
        private String MONEY;
        private java.sql.Timestamp DATEEND;
        private String REFERENCE;
        private String NAME;
        private double UNITS;
        private double TOTAL;

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

        public java.sql.Timestamp getDATEEND() {
            return DATEEND;
        }

        public void setDATEEND(java.sql.Timestamp DATEEND) {
            this.DATEEND = DATEEND;
        }

        public String getREFERENCE() {
            return REFERENCE;
        }

        public void setREFERENCE(String REFERENCE) {
            this.REFERENCE = REFERENCE;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public double getUNITS() {
            return UNITS;
        }

        public void setUNITS(double UNITS) {
            this.UNITS = UNITS;
        }

        public double getTOTAL() {
            return TOTAL;
        }

        public void setTOTAL(double TOTAL) {
            this.TOTAL = TOTAL;
        }

    }

    class DailySales {

        private java.util.Date DATESTART = UtilDateTime.nowTimestamp();
        private java.util.Date DATEEND = UtilDateTime.nowTimestamp();
        Map<String, ClosedProductData> dataMap = new HashMap<String, ClosedProductData>();
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

    Map<String, ClosedProductData> dataStore = new HashMap<String, ClosedProductData>();
    String sqlStr = "SELECT PROD.product_id, PROD.internal_name, PCM.PRODUCT_CATEGORY_ID,   "
            + " OS.description AS groupName,\n"
            + "      OH.order_Id,\n"
            + "      OH.order_Type_Id,\n"
            + "      OH.order_Date,\n"
            + "      OS.description AS orderStatus,\n"
            + "      PMT.description AS paymentMethod,\n"
            + "      OH.visit_Id,\n"
            + "      OH.currency_Uom,\n"
            + "      OH.origin_Facility_Id,\n"
            + "      OH.web_Site_Id,\n"
            + "      OH.grand_Total,\n"
            + "      OI.product_Id,\n"
            + "      OI.item_Description,\n"
            + "      OIS. description AS  item_Status,\n"
            + "      OI.quantity,\n"
            + "      OI.unit_Price,\n"
            + "      OPP.created_By_User_Login  \n"
            + "  FROM ofbiz.Order_Header AS OH INNER JOIN ofbiz.Order_Payment_Preference AS OPP ON(OH.order_id = opp.order_id)\n"
            + "       INNER JOIN ofbiz.Payment_Method_Type AS PMT ON (OPP.PAYMENT_METHOD_TYPE_ID = PMT.PAYMENT_METHOD_TYPE_ID)\n"
            + "       INNER JOIN ofbiz.Status_Item AS OS ON (OH.STATUS_ID = OS.STATUS_ID)\n"
            + "       INNER JOIN ofbiz.Order_Item AS OI ON (OH.order_id = OI.order_id)\n"
            + "       INNER JOIN ofbiz.Status_Item AS OIS ON (OIS.STATUS_ID = OI.STATUS_ID)"
            + "       INNER JOIN  ofbiz.product as PROD ON (PROD.product_id = OI.product_id)\n"
            + "       INNER JOIN ofbiz.product_category_MEMBER as PCM ON (PROD.product_id = PCM.PRODUCT_ID)"
            + " WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS' ";

    ;

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Delegator delegator = ControllerOptions.getSession().getDelegator();

        String dateSql = getDateSql("OH.order_Date", collectionMap);
        String scanStr = getGoodIdentificationSql(collectionMap);
        String sqlForm = getProductFormSql(collectionMap);

        /*if (collectionMap.containsKey("JParamsStartAndEndDatesInterval")) {
         Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsStartAndEndDatesInterval");
         if (values.containsKey("startDate")) {
         dayStart = (java.sql.Timestamp) values.get("startDate");
         }
         if (values.containsKey("endDate")) {
         dayEnd = (java.sql.Timestamp) values.get("endDate");
         }
         }*/
// gets the helper (localmysql, localpostgres, etc.) for your entity group org.ofbiz
    /*    GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
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

         Collection result = new ArrayList<ClosedProductData>();
         while (rs.next()) {
         String productCode = rs.getString("PRODUCT_ID");
         String internalName = rs.getString("INTERNAL_NAME");
         double defPrice = rs.getDouble("PRICE");
         ClosedProductData posCloseData = new ClosedProductData();
         posCloseData.setREFERENCE(productCode);
         posCloseData.setNAME(internalName);
         posCloseData.setUNITS(defPrice);
         posCloseData.setTOTAL(new Double(0));
         dataStore.put(productCode, posCloseData);
         //result.add(posCloseData);
         }

         rs.close();
         sqlproc.close();

         str = "SELECT Distinct prod.product_id, prod.internal_name, pp.PRICE FROM ofbiz.product as prod \n"
         + "inner join ofbiz.product_price as pp \n"
         + "on (prod.product_id = pp.PRODUCT_ID)\n"
         + "WHERE pp.PRODUCT_PRICE_TYPE_ID = 'AVERAGE_COST'\n"
         //           + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
         //           + "(pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"
         + "order by prod.product_id, pp.FROM_DATE";

         sqlproc.prepareStatement(str);
         rs = sqlproc.executeQuery();

         while (rs.next()) {
         String productCode = rs.getString("PRODUCT_ID");
         String internalName = rs.getString("INTERNAL_NAME");
         double costPrice = rs.getDouble("PRICE");
         if (dataStore.containsKey(productCode)) {
         ClosedProductData posCloseData = dataStore.get(productCode);
         posCloseData.TOTAL = costPrice;
         } else {
         ClosedProductData posCloseData = new ClosedProductData();
         posCloseData.setREFERENCE(productCode);
         posCloseData.setNAME(internalName);
         posCloseData.setUNITS(new Double(0));
         posCloseData.setTOTAL(costPrice);
         dataStore.put(productCode, posCloseData);
         }

         //result.add(posCloseData);
         }

         rs.close();
         sqlproc.close();
         */
//        result.addAll(dataStore.values());
        Collection result = new ArrayList<ClosedProductData>();
        List<OrderReportView> results = new ArrayList<OrderReportView>();

        String str = sqlStr;
        boolean whereset = false;
        if (UtilValidate.isNotEmpty(dateSql) || UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)) {

            if (UtilValidate.isNotEmpty(dateSql)) {
                str = str.concat(" AND ");
                str = str.concat(dateSql);
            }
            if (!whereset && UtilValidate.isNotEmpty(scanStr)) {
                str = str.concat(" AND EXISTS ( ");
                str = str.concat(scanStr);
                str = str.concat(")");
                whereset = true;
            }

            if (!whereset && UtilValidate.isNotEmpty(sqlForm)) {
                /*str = str.concat(" AND EXISTS ( ");
                 str = str.concat(sqlForm);            
                 str = str.concat(")");   */
                str = str.concat(" AND  ");
                str = str.concat(sqlForm);
//                str = str.concat(")");
            }
        }

        Debug.logInfo(str, module);
        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);
        sqlproc.prepareStatement(str);
        ResultSet rs = sqlproc.executeQuery();
        while (rs.next()) {
            ClosedProductData posCloseData = new ClosedProductData();
            posCloseData.setREFERENCE(rs.getString("product_Id"));
            posCloseData.setNAME(rs.getString("item_Description"));
            posCloseData.setUNITS(rs.getDouble("quantity"));
            posCloseData.setTOTAL((rs.getBigDecimal("quantity").multiply(rs.getBigDecimal("unit_Price"))).doubleValue());
            posCloseData.HOST = rs.getString("created_By_User_Login");
            //private String MONEY;
            posCloseData.DATEEND = rs.getTimestamp("order_Date");
            //dataStore.put(productCode, posCloseData);
            result.add(posCloseData);
        }

        rs.close();
        sqlproc.close();

        /*genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, entityConditionList, orderBy, delegator);

         for (GenericValue genVal : genList) {
         OrderReportView orderReportView = new OrderReportView(genVal);
         ClosedProductData posCloseData = new ClosedProductData();
         posCloseData.setREFERENCE(orderReportView.getproductId());
         posCloseData.setNAME(orderReportView.getitemDescription());
         posCloseData.setUNITS(orderReportView.getquantity().doubleValue());
         posCloseData.setTOTAL((orderReportView.getquantity().multiply(orderReportView.getunitPrice())).doubleValue());
         posCloseData.HOST = orderReportView.getGenericValueObj().getString("createdByUserLogin");
         //private String MONEY;
         posCloseData.DATEEND = orderReportView.getorderDate();

         result.add(posCloseData);
         }*/
        /*
         ClosedProductData posCloseData = new ClosedProductData();
         posCloseData.setREFERENCE("Testing PAYMENT 1");
         posCloseData.setNAME("Testing MONEY 1");
         posCloseData.setUNITS(new Double(100));
         posCloseData.setTOTAL(new Double(100));
         result.add(posCloseData);

         posCloseData = new ClosedProductData();
         posCloseData.setREFERENCE("Testing PAYMENT 2");
         posCloseData.setNAME("Testing MONEY 2");
         posCloseData.setUNITS(new Double(200));
         posCloseData.setTOTAL(new Double(200));
         result.add(posCloseData);
         */
        return result;
    }
}
