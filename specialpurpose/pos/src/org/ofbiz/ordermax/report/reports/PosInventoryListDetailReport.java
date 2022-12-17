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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMemberSingleton;

/**
 *
 * @author BBS Auctions
 */
public class PosInventoryListDetailReport extends PosInventoryReport {

    public static final String module = PosInventoryListDetailReport.class.getName();

    public PosInventoryListDetailReport() {
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
            Logger.getLogger(PosInventoryListDetailReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Current inventory details";
    }

    @Override
    public String getReportCompiledFileName() {
        return "inventorylistdetail.jasper";
    }

    @Override
    public String getReportFileName() {
        return "inventorylistdetail.jrxml";
    }

    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties
    @Override
    public String getResourceFileName() {
        return "inventorylistdetail_messages.properties";
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
/*        String sqlStr = "SELECT Distinct PROD.product_id, PROD.internal_name, PCM.PRODUCT_CATEGORY_ID,"
         + " INV.INVENTORY_ITEM_ID, INV.available_to_promise_total  \n"
         + "FROM ofbiz.product as PROD\n"             
         + " INNER JOIN ofbiz.product_category_MEMBER as PCM "
         + "     ON (PROD.product_id = PCM.PRODUCT_ID)"
         + " INNER JOIN ofbiz.inventory_item as inv"
         + "     ON (PROD.product_id = inv.PRODUCT_ID)"
         + " WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS'";
         */
        String sqlStr = "SELECT PROD.product_id, PROD.INTERNAL_NAME, CASE \n"
                + "            WHEN iid.ORDER_ID IS NOT NULL AND iid.RECEIPT_ID IS NOT NULL THEN CONCAT('PURCASE ORDER - ', iid.ORDER_ID)\n"
                + "			WHEN iid.ORDER_ID IS NOT NULL AND RECEIPT_ID IS NULL THEN CONCAT('SALES ORDER - ', iid.ORDER_ID)\n"
                + "			WHEN iid.RETURN_ID IS NOT NULL THEN CONCAT('CUSTOMER RETURN - ', iid.RETURN_ID)\n"
                + "               ELSE \"UNKNOWN\" \n"
                + "       END as InventoryDetail, iid.INVENTORY_ITEM_ID, iid.ORDER_ID, iid.RECEIPT_ID, iid.RETURN_ID, ii.AVAILABLE_TO_PROMISE_TOTAL, iid.AVAILABLE_TO_PROMISE_DIFF\n"
                + "FROM ofbiz.inventory_item as ii \n"
                + "	INNER JOIN ofbiz.inventory_item_detail as iid on (ii.INVENTORY_ITEM_ID  = iid.INVENTORY_ITEM_ID)\n"
                + "    INNER JOIN ofbiz.product as PROD  ON (PROD.product_id = ii.PRODUCT_ID)            \n"
                + "                INNER JOIN ofbiz.product_category_MEMBER as PCM \n"
                + "                 ON (PROD.product_id = PCM.PRODUCT_ID)\n"
                + "                WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS'";
        
        String scanStr = getGoodIdentificationSql(collectionMap);
        String sqlForm = getProductFormSql(collectionMap);
        String facilityId = getFacilityId(collectionMap);//"SevenHillsStore";
        if (UtilValidate.isNotEmpty(facilityId)) {
            sqlStr = sqlStr.concat(" AND ii.FACILITY_ID = '" + facilityId + "'");
        }

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

        sqlStr = sqlStr.concat(" order by PROD.product_id, ii.INVENTORY_ITEM_ID");
        Debug.logInfo(sqlStr, module);

        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);

        sqlproc.prepareStatement(sqlStr);
        ResultSet rs = sqlproc.executeQuery();

        while (rs.next()) {

            String productCode = rs.getString("PRODUCT_ID");
            String internalName = rs.getString("INTERNAL_NAME");
            InventoryData posCloseData = new InventoryData();
            posCloseData.setREFERENCE(productCode);
            posCloseData.setNAME(internalName);
            posCloseData.setUNITS(rs.getDouble("AVAILABLE_TO_PROMISE_DIFF"));
            posCloseData.setATTINSTANCEDESC(rs.getString("InventoryDetail"));
            posCloseData.setSTOCKMAXIMUM(0.0);

            posCloseData.setSTOCKSECURITY(0.0);
            posCloseData.setSTOCKVOLUME(0.0);
            posCloseData.setLOCATIONID(facilityId);

            try {
                posCloseData.setLOCATIONNAME(FacilitySingleton.getFacility(facilityId).getfacilityName());
                posCloseData.setCATEGORYNAME(ProductCategoryMemberSingleton.getProductCategory(rs.getString("PRODUCT_CATEGORY_ID")).getCategoryName());
            } catch (Exception e) {

            }

           // Map<String, Object> invMap = LoadInventoryWorker.getInventoryAvailableByFacility(productCode, facilityId, ControllerOptions.getSession());
            // posCloseData.setUNITS(((BigDecimal) invMap.get("availableToPromiseTotal")).doubleValue());
            result.add(posCloseData);

//resultMap.put("availableToPromiseTotal", availableToPromiseTotal!= null ? availableToPromiseTotal : BigDecimal.ZERO );
            //          resultMap.put("quantityOnHandTotal", availableToPromiseTotal != null ? quantityOnHandTotal : BigDecimal.ZERO);
        }

        rs.close();
        sqlproc.close();

        //result.addAll(dataStore.values());
        return result;
    }
}
