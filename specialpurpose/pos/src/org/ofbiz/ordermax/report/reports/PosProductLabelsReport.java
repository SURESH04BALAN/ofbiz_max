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
import java.util.Random;
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
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMemberSingleton;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author BBS Auctions
 */
public class PosProductLabelsReport extends NewEntityJasperReport {

    public static final String module = PosProductLabelsReport.class.getName();

    public PosProductLabelsReport() {
    }

    @Override
    public void loadParameterSelections() {
        ControllerOptions options = new ControllerOptions();
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
            Logger.getLogger(PosProductLabelsReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Product Labels Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "productlabels.jasper";
    }

    @Override
    public String getReportFileName() {
        return "productlabels.jrxml";
    }

    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties
    @Override
    public String getResourceFileName() {
        return "productlabels_messages.properties";
    }
//report.setResourceBundle("com/openbravo/reports/usersales_messages");

    public class ProductLabelData {

        private java.lang.String ID;
        java.lang.String REFERENCE;
        private java.lang.String CODE;
        java.lang.String NAME;
        private java.lang.Double PRICEBUY;
        private java.lang.Double PRICESELL;
        private java.lang.String TAXCAT;
        private java.lang.String TAXCATNAME;
        private java.lang.String CATEGORY;
        private java.lang.String CATEGORYNAME;

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getREFERENCE() {
            return REFERENCE;
        }

        public void setREFERENCE(String REFERENCE) {
            this.REFERENCE = REFERENCE;
        }

        public java.lang.String getID() {
            return ID;
        }

        public void setID(java.lang.String ID) {
            this.ID = ID;
        }

        public java.lang.String getCODE() {
            return CODE;
        }

        public void setCODE(java.lang.String CODE) {
            this.CODE = CODE;
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
    }

    Map<String, ProductLabelData> dataStore = new HashMap<String, ProductLabelData>();

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
        Delegator delegator = ControllerOptions.getSession().getDelegator();

        String str = "SELECT Distinct PROD.product_id, PROD.internal_name, pp.PRICE, pp.PRODUCT_PRICE_TYPE_ID, GOID.ID_VALUE, GOID.GOOD_IDENTIFICATION_TYPE_ID, PCM.PRODUCT_CATEGORY_ID  \n"
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
  
        boolean whereset = false;
        if( UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)){
            
          
            if(!whereset && UtilValidate.isNotEmpty(scanStr)){
                str = str.concat(" AND EXISTS ( ");
                str = str.concat(scanStr);            
                str = str.concat(")"); 
                whereset = true;
            }
            
          if(!whereset && UtilValidate.isNotEmpty(sqlForm)){
                str = str.concat(" AND  ");
                str = str.concat(sqlForm);            
              //  str = str.concat(")");   
            }            
        }
        
        Collection result = new ArrayList<ProductLabelData>();
        Debug.logInfo(str, module);
        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);
        sqlproc.prepareStatement(str);
        ResultSet rs = sqlproc.executeQuery();
        while (rs.next()) {
            String productCode = rs.getString("PRODUCT_ID");
            String internalName = rs.getString("INTERNAL_NAME");
            double defPrice = rs.getDouble("PRICE");
            String productPriceTypeId = rs.getString("PRODUCT_PRICE_TYPE_ID");
            String goodIdentificationTypeId = rs.getString("GOOD_IDENTIFICATION_TYPE_ID");
            ProductLabelData posCloseData = null;
            if (dataStore.containsKey(productCode)) {
                posCloseData = dataStore.get(productCode);

            } else {
                posCloseData = new ProductLabelData();
                posCloseData.setREFERENCE(productCode);
                posCloseData.setID(productCode);
                posCloseData.setNAME(internalName);
                dataStore.put(productCode, posCloseData);
            }

            if ("DEFAULT_PRICE".equals(productPriceTypeId)) {
                posCloseData.setPRICESELL(defPrice);
            } else if ("AVERAGE_COST".equals(productPriceTypeId)) {
                posCloseData.setPRICEBUY(defPrice);
            }
            if ("EAN".equals(goodIdentificationTypeId)) {
                posCloseData.setCODE(rs.getString("ID_VALUE"));
            }
            try{            
            posCloseData.setCATEGORYNAME(ProductCategoryMemberSingleton.getProductCategory(rs.getString("PRODUCT_CATEGORY_ID")).getCategoryName());
            }
            catch(Exception e){
                
            }
        }

        rs.close();
        sqlproc.close();

        java.sql.Timestamp dayStart = UtilDateTime.nowTimestamp();
        if (collectionMap.containsKey("startDate")) {
            dayStart = (java.sql.Timestamp) collectionMap.get("startDate");
        }

        result.addAll(dataStore.values());


        return result;
    }
}
