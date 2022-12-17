/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.basic.BasicException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mvc.controller.LoadInventoryWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.datasource.GenericHelperInfo;
import org.ofbiz.entity.jdbc.SQLProcessor;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMemberSingleton;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author BBS Auctions
 */
public class PosInventoryReport extends NewEntityJasperReport {

    public static final String module = PosInventoryReport.class.getName();

    public PosInventoryReport() {
    }

    @Override
    public void loadParameterSelections() {
        ControllerOptions options = new ControllerOptions();
//        com.openbravo.pos.reports.params.JParamHelper.AddReportDateSelection(getQbffilter(), options, "Date Selection", "orderDate");
        try {

            GoodIdentificationType goodIdentificationType = GoodIdentificationTypeSingleton.getGoodIdentificationType("EAN");
            com.openbravo.pos.reports.params.JParamHelper.addReportGoodIdentificationPanel(getQbffilter(), "idValue", "By barcode", "originFacilityId", "Scan Code:", goodIdentificationType);

            com.openbravo.pos.reports.params.JParamHelper.AddReportFormId(getQbffilter(), options, "By form");
            PosTransaction pos = PosTransaction.getCurrentTx(XuiContainer.getSession());
            // PosTerminal posTerminal = PosTerminalSingleton.getPosTerminal(pos.getTerminalId());
            Facility facility = FacilitySingleton.getFacility(pos.getFacilityId());
            com.openbravo.pos.reports.params.JParamHelper.addReportSelectionComboPanel(getQbffilter(), "facilityId", FacilitySingleton.getValueList(), "By facility", "facilityId", "Facility Name:", facility);
            //   com.openbravo.pos.reports.params.JParamHelper.addReportSelectionComboPanel(getQbffilter(), "posTerminalId", PosTerminalSingleton.getValueList(), "Terminal Id:", "terminalId", "Terminal Id:", posTerminal);            
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
            Logger.getLogger(PosInventoryReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Warehouses Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "inventory.jasper";
    }

    @Override
    public String getReportFileName() {
        return "inventory.jrxml";
    }

    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties
    @Override
    public String getResourceFileName() {
        return "inventory_messages.properties";
    }
//report.setResourceBundle("com/openbravo/reports/usersales_messages");

    public class InventoryData {

        private java.lang.String REFERENCE;
        private java.lang.String NAME;
        private java.lang.String CATEGORY;
        private java.lang.String CATEGORYNAME;
        private java.lang.Double UNITS;
        private java.lang.Double PRICEBUY;
        private java.lang.Double PRICESELL;
        private java.lang.Double STOCKVOLUME;
        private java.lang.Double STOCKCOST;
        private java.lang.Double STOCKSECURITY;
        private java.lang.String LOCATIONID;
        private java.lang.String LOCATIONNAME;
        private java.lang.Double STOCKMAXIMUM;
        private java.lang.String SUPPLIERID;
        private java.lang.String SUPPLIERNAME;
        private java.lang.Double UNITSOUT= BigDecimal.ZERO.doubleValue();
        private java.lang.Double TOTALOUT= BigDecimal.ZERO.doubleValue();
        private java.lang.Double UNITSIN = BigDecimal.ZERO.doubleValue();
        private java.lang.Double TOTALIN= BigDecimal.ZERO.doubleValue();
        private java.lang.Double UNITSDIFF= BigDecimal.ZERO.doubleValue();
        private java.lang.Double TOTALDIFF= BigDecimal.ZERO.doubleValue();
        private java.lang.String ATTINSTANCEID;
        private java.lang.String ATTINSTANCENAME;
        private java.lang.String ATTINSTANCEDESC;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public java.lang.String getREFERENCE() {
            return REFERENCE;
        }

        public void setREFERENCE(java.lang.String REFERENCE) {
            this.REFERENCE = REFERENCE;
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

        public java.lang.Double getUNITS() {
            return UNITS;
        }

        public void setUNITS(java.lang.Double UNITS) {
            this.UNITS = UNITS;
        }

        public java.lang.Double getPRICEBUY() {
            return PRICEBUY;
        }

        public void setPRICEBUY(java.lang.Double PRICEBUY) {
            this.PRICEBUY = PRICEBUY;
        }

        public java.lang.Double getPRICESELL() {
            return PRICESELL;
        }

        public void setPRICESELL(java.lang.Double PRICESELL) {
            this.PRICESELL = PRICESELL;
        }

        public java.lang.Double getSTOCKVOLUME() {
            return STOCKVOLUME;
        }

        public void setSTOCKVOLUME(java.lang.Double STOCKVOLUME) {
            this.STOCKVOLUME = STOCKVOLUME;
        }

        public java.lang.Double getSTOCKCOST() {
            return STOCKCOST;
        }

        public void setSTOCKCOST(java.lang.Double STOCKCOST) {
            this.STOCKCOST = STOCKCOST;
        }

        public java.lang.Double getSTOCKSECURITY() {
            return STOCKSECURITY;
        }

        public void setSTOCKSECURITY(java.lang.Double STOCKSECURITY) {
            this.STOCKSECURITY = STOCKSECURITY;
        }

        public java.lang.String getLOCATIONID() {
            return LOCATIONID;
        }

        public void setLOCATIONID(java.lang.String LOCATIONID) {
            this.LOCATIONID = LOCATIONID;
        }

        public java.lang.String getLOCATIONNAME() {
            return LOCATIONNAME;
        }

        public void setLOCATIONNAME(java.lang.String LOCATIONNAME) {
            this.LOCATIONNAME = LOCATIONNAME;
        }

        public java.lang.Double getSTOCKMAXIMUM() {
            return STOCKMAXIMUM;
        }

        public void setSTOCKMAXIMUM(java.lang.Double STOCKMAXIMUM) {
            this.STOCKMAXIMUM = STOCKMAXIMUM;
        }

        public java.lang.Double getUNITSOUT() {
            return UNITSOUT;
        }

        public void setUNITSOUT(java.lang.Double UNITSOUT) {
            this.UNITSOUT = UNITSOUT;
        }

        public java.lang.Double getTOTALOUT() {
            return TOTALOUT;
        }

        public void setTOTALOUT(java.lang.Double TOTALOUT) {
            this.TOTALOUT = TOTALOUT;
        }

        public java.lang.Double getUNITSIN() {
            return UNITSIN;
        }

        public void setUNITSIN(java.lang.Double UNITSIN) {
            this.UNITSIN = UNITSIN;
        }

        public java.lang.Double getTOTALIN() {
            return TOTALIN;
        }

        public void setTOTALIN(java.lang.Double TOTALIN) {
            this.TOTALIN = TOTALIN;
        }

        public java.lang.Double getUNITSDIFF() {
            return UNITSDIFF;
        }

        public void setUNITSDIFF(java.lang.Double UNITSDIFF) {
            this.UNITSDIFF = UNITSDIFF;
        }

        public java.lang.Double getTOTALDIFF() {
            return TOTALDIFF;
        }

        public void setTOTALDIFF(java.lang.Double TOTALDIFF) {
            this.TOTALDIFF = TOTALDIFF;
        }

        public java.lang.String getATTINSTANCEID() {
            return ATTINSTANCEID;
        }

        public void setATTINSTANCEID(java.lang.String ATTINSTANCEID) {
            this.ATTINSTANCEID = ATTINSTANCEID;
        }

        public java.lang.String getATTINSTANCENAME() {
            return ATTINSTANCENAME;
        }

        public void setATTINSTANCENAME(java.lang.String ATTINSTANCENAME) {
            this.ATTINSTANCENAME = ATTINSTANCENAME;
        }

        public java.lang.String getATTINSTANCEDESC() {
            return ATTINSTANCEDESC;
        }

        public void setATTINSTANCEDESC(java.lang.String ATTINSTANCEDESC) {
            this.ATTINSTANCEDESC = ATTINSTANCEDESC;
        }

        public java.lang.String getSUPPLIERID() {
            return SUPPLIERID;
        }

        public void setSUPPLIERID(java.lang.String SUPPLIERID) {
            this.SUPPLIERID = SUPPLIERID;
        }

        public java.lang.String getSUPPLIERNAME() {
            return SUPPLIERNAME;
        }

        public void setSUPPLIERNAME(java.lang.String SUPPLIERNAME) {
            this.SUPPLIERNAME = SUPPLIERNAME;
        }

    }

    Map<String, InventoryData> dataStore = new HashMap<String, InventoryData>();

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
        Delegator delegator = ControllerOptions.getSession().getDelegator();
        Collection result = new ArrayList<InventoryData>();
        final ClassLoader cl = super.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        //  String sqlStr = "SELECT Distinct PROD.product_id, PROD.internal_name, pp.PRICE, pp.PRODUCT_PRICE_TYPE_ID\n"
        //          + "FROM ofbiz.product as PROD\n"
        //          + "inner join ofbiz.product_price as pp\n"
        //          + "on (PROD.product_id = pp.PRODUCT_ID)\n";
        String sqlStr = "SELECT Distinct PROD.product_id, PROD.internal_name, pp.PRICE, pp.PRODUCT_PRICE_TYPE_ID, GOID.ID_VALUE, GOID.GOOD_IDENTIFICATION_TYPE_ID, PCM.PRODUCT_CATEGORY_ID  \n"
                + "FROM ofbiz.product as PROD\n"
                + " INNER JOIN ofbiz.product_price as pp\n"
                + "     ON (PROD.product_id = pp.PRODUCT_ID)\n"
                + " LEFT OUTER JOIN ofbiz.good_identification as GOID\n"
                + "     ON (PROD.product_id = GOID.PRODUCT_ID)\n"
                + " INNER JOIN ofbiz.product_category_MEMBER as PCM "
                + "     ON (PROD.product_id = PCM.PRODUCT_ID)"
                + " WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS'";

        String scanStr = getGoodIdentificationSql(collectionMap);
        String sqlForm = getProductFormSql(collectionMap);
        String facilityId = getFacilityId(collectionMap);//"SevenHillsStore";
        
        if (UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)) {
            if (UtilValidate.isNotEmpty(scanStr)) {
                sqlStr = sqlStr.concat(" AND EXISTS ( ");
                sqlStr = sqlStr.concat(scanStr);
                sqlStr = sqlStr.concat(")");
            }

            if (UtilValidate.isEmpty(scanStr) && UtilValidate.isNotEmpty(sqlForm)) {
                sqlStr = sqlStr.concat(" AND  ");
                sqlStr = sqlStr.concat(sqlForm);
               // sqlStr = sqlStr.concat(")");
            }
        }

        sqlStr = sqlStr.concat(" order by PROD.product_id, pp.FROM_DATE");
        Debug.logInfo(sqlStr, module);

        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);

        sqlproc.prepareStatement(sqlStr);
        ResultSet rs = sqlproc.executeQuery();

        while (rs.next()) {

            String productCode = rs.getString("PRODUCT_ID");
            String internalName = rs.getString("INTERNAL_NAME");
            double defPrice = rs.getDouble("PRICE");
            String productPriceTypeId = rs.getString("PRODUCT_PRICE_TYPE_ID");
            InventoryData posCloseData = null;
            if (dataStore.containsKey(productCode)) {
                posCloseData = dataStore.get(productCode);

            } else {
                posCloseData = new InventoryData();
                posCloseData.setREFERENCE(productCode);
                posCloseData.setNAME(internalName);
                posCloseData.setSTOCKMAXIMUM(0.0);
                //posCloseData.setSTOCKCOST(2.0);
                posCloseData.setSTOCKSECURITY(0.0);
                posCloseData.setSTOCKVOLUME(0.0);
                posCloseData.setLOCATIONID(facilityId);


                try {
                    posCloseData.setLOCATIONNAME(FacilitySingleton.getFacility(facilityId).getfacilityName());
                    posCloseData.setCATEGORYNAME(ProductCategoryMemberSingleton.getProductCategory(rs.getString("PRODUCT_CATEGORY_ID")).getCategoryName());
                } catch (Exception e) {

                }

                Map<String, Object> invMap = LoadInventoryWorker.getInventoryAvailableByFacility(productCode, facilityId, ControllerOptions.getSession());
                posCloseData.setUNITS(((BigDecimal) invMap.get("availableToPromiseTotal")).doubleValue());
                dataStore.put(productCode, posCloseData);
            }

            if ("DEFAULT_PRICE".equals(productPriceTypeId)) {
                posCloseData.setPRICESELL(defPrice);
            } else if ("AVERAGE_COST".equals(productPriceTypeId)) {
                posCloseData.setPRICEBUY(defPrice);
                posCloseData.setSTOCKCOST(defPrice);
            }

//resultMap.put("availableToPromiseTotal", availableToPromiseTotal!= null ? availableToPromiseTotal : BigDecimal.ZERO );
            //          resultMap.put("quantityOnHandTotal", quantityOnHandTotal != null ? quantityOnHandTotal : BigDecimal.ZERO);
        }

        rs.close();
        sqlproc.close();

        result.addAll(dataStore.values());

        return result;
    }
}
