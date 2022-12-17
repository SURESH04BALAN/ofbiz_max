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
public class PosInventoryBySupplierReport extends PosInventoryReport {

    public static final String module = PosInventoryBySupplierReport.class.getName();

    public PosInventoryBySupplierReport() {
    }

    @Override
    public void loadParameterSelections() {

        ControllerOptions options = new ControllerOptions();
//        com.openbravo.pos.reports.params.JParamHelper.AddReportDateSelection(getQbffilter(), options, "Date Selection", "orderDate");
        try {
            ControllerOptions supplierOptions = new ControllerOptions();
            com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(getQbffilter(), supplierOptions, "By Supplier");

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
            Logger.getLogger(PosInventoryBySupplierReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Supplier Current inventory";
    }

    @Override
    public String getReportCompiledFileName() {
        return "inventorybysuppliernew.jasper";
    }

    @Override
    public String getReportFileName() {
        return "inventorybysuppliernew.jrxml";
    }

    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties
    @Override
    public String getResourceFileName() {
        return "inventoryb_messages.properties";
    }

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
        String sqlStr = "SELECT sp.party_id , PCM.PRODUCT_CATEGORY_ID , PROD.product_id, PROD.internal_name,  pp.PRICE, pp.PRODUCT_PRICE_TYPE_ID, GOID.ID_VALUE, GOID.GOOD_IDENTIFICATION_TYPE_ID \n"
                + "                 FROM ofbiz.product as PROD\n"
                + "                  INNER JOIN ofbiz.product_price as pp\n"
                + "                      ON (PROD.product_id = pp.PRODUCT_ID)\n"
                + "                  LEFT OUTER JOIN ofbiz.good_identification as GOID\n"
                + "                      ON (PROD.product_id = GOID.PRODUCT_ID)\n"
                + "                  INNER JOIN ofbiz.product_category_MEMBER as PCM \n"
                + "                      ON (PROD.product_id = PCM.PRODUCT_ID)\n"
                + "                  INNER JOIN ofbiz.supplier_product as sp \n"
                + "                      ON (PROD.product_id = sp.PRODUCT_ID)\n"
                + "                  WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS'\n"
                + "						";

        String scanStr = getGoodIdentificationSql(collectionMap);
        String sqlForm = getProductFormSql(collectionMap);
        String sqlParty = getPartyIdSql("sp.party_id", collectionMap);
        String facilityId = getFacilityId(collectionMap);//"SevenHillsStore";

        if (UtilValidate.isNotEmpty(sqlParty) || UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)) {
           
            if (UtilValidate.isNotEmpty(sqlParty)) {
                sqlStr = sqlStr.concat(" AND ");
                sqlStr = sqlStr.concat(sqlParty);
  
            }
            
            if (UtilValidate.isNotEmpty(scanStr)) {
                sqlStr = sqlStr.concat(" AND EXISTS ( ");
                sqlStr = sqlStr.concat(scanStr);
                sqlStr = sqlStr.concat(")");
            }

            if (UtilValidate.isEmpty(scanStr) && UtilValidate.isNotEmpty(sqlForm)) {
                sqlStr = sqlStr.concat(" AND ");
                sqlStr = sqlStr.concat(sqlForm);
               // sqlStr = sqlStr.concat(")");
            }
        }

        sqlStr = sqlStr.concat(" order by sp.party_id, PCM.PRODUCT_CATEGORY_ID , product_id, pp.FROM_DATE");
        Debug.logInfo(sqlStr, module);

        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);

        sqlproc.prepareStatement(sqlStr);
        ResultSet rs = sqlproc.executeQuery();

        while (rs.next()) {

            String productCode = rs.getString("PRODUCT_ID");
            String partyId = rs.getString("PARTY_ID");            
            String internalName = rs.getString("INTERNAL_NAME");
            double defPrice = rs.getDouble("PRICE");
            String productPriceTypeId = rs.getString("PRODUCT_PRICE_TYPE_ID");
            InventoryData posCloseData = null;
            String code = productCode.concat("-").concat(partyId);
            if (dataStore.containsKey(code)) {
                posCloseData = dataStore.get(code);

            } else {
                posCloseData = new InventoryData();
                posCloseData.setREFERENCE(productCode);
                posCloseData.setNAME(internalName);
                posCloseData.setSTOCKMAXIMUM(0.0);
                //posCloseData.setSTOCKCOST(2.0);
                posCloseData.setSTOCKSECURITY(0.0);
                posCloseData.setSTOCKVOLUME(0.0);
                posCloseData.setLOCATIONID(facilityId);
                posCloseData.setSUPPLIERID(partyId);
                posCloseData.setSUPPLIERNAME(partyId);                
                try {
                    posCloseData.setLOCATIONNAME(FacilitySingleton.getFacility(facilityId).getfacilityName());
                    posCloseData.setCATEGORYNAME(ProductCategoryMemberSingleton.getProductCategory(rs.getString("PRODUCT_CATEGORY_ID")).getCategoryName());
                } catch (Exception e) {

                }

                Map<String, Object> invMap = LoadInventoryWorker.getInventoryAvailableByFacility(productCode, facilityId, ControllerOptions.getSession());
                posCloseData.setUNITS(((BigDecimal) invMap.get("availableToPromiseTotal")).doubleValue());
                dataStore.put(code, posCloseData);
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
