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
public class PosInventoryDiffReport extends PosInventoryReport {

    public static final String module = PosInventoryDiffReport.class.getName();

    public PosInventoryDiffReport() {
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
            Logger.getLogger(PosInventoryDiffReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Inventory diary";
    }

    @Override
    public String getReportCompiledFileName() {
        return "inventorydiff.jasper";
    }

    @Override
    public String getReportFileName() {
        return "inventorydiff.jrxml";
    }

    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties
    @Override
    public String getResourceFileName() {
        return "inventorydiff_messages.properties";
    }

    /*  String sqlStr = "SELECT PROD.product_id, PROD.internal_name, PCM.PRODUCT_CATEGORY_ID,  \n"
     + "                   OH.order_Type_Id,  \n"
     + "                   OI.product_Id,\n"
     + "                   OI.item_Description,\n"
     + "                   sum(OI.QUANTITY) AS UNITS,\n"
     + "                   SUM(oi.QUANTITY*oi.UNIT_PRICE) AS TOTALVALUE\n"
     + "               FROM ofbiz.Order_Header AS OH \n"
     + "                    INNER JOIN ofbiz.Order_Item AS OI ON (OH.order_id = OI.order_id)\n"
     + "                    INNER JOIN ofbiz.product as PROD ON (PROD.PRODUCT_ID = OI.PRODUCT_ID)\n"
     + "                    INNER JOIN ofbiz.product_category_MEMBER as PCM ON (PROD.product_id = PCM.PRODUCT_ID)\n"
     + " WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS'";            
     */
    String sqlStr = "SELECT PROD.product_id, PROD.internal_name, PCM.PRODUCT_CATEGORY_ID, "
            + "SUM(CASE WHEN INV.AVAILABLE_TO_PROMISE_TOTAL <0 THEN INV.AVAILABLE_TO_PROMISE_TOTAL ELSE 0 END) AS UNITSOUT, \n"
            + "SUM(CASE WHEN INV.AVAILABLE_TO_PROMISE_TOTAL <0 THEN INV.AVAILABLE_TO_PROMISE_TOTAL * INV.UNIT_COST ELSE 0 END) AS TOTALOUT, \n"
            + "SUM(CASE WHEN INV.AVAILABLE_TO_PROMISE_TOTAL >=0 THEN INV.AVAILABLE_TO_PROMISE_TOTAL ELSE 0 END) AS UNITSIN, \n"
            + "SUM(CASE WHEN INV.AVAILABLE_TO_PROMISE_TOTAL >=0 THEN INV.AVAILABLE_TO_PROMISE_TOTAL * INV.UNIT_COST ELSE 0 END) AS TOTALIN, \n"
            + "SUM(INV.AVAILABLE_TO_PROMISE_TOTAL) AS UNITSDIFF, "
            + "SUM(INV.AVAILABLE_TO_PROMISE_TOTAL * INV.UNIT_COST) AS TOTALDIFF \n"
            + "FROM ofbiz.product as PROD\n"
            + " INNER JOIN ofbiz.product_category_MEMBER as PCM "
            + "     ON (PROD.product_id = PCM.PRODUCT_ID)"
            + " INNER JOIN ofbiz.inventory_item as inv"
            + "     ON (PROD.product_id = inv.PRODUCT_ID)"
            + " WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS'";

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Delegator delegator = ControllerOptions.getSession().getDelegator();

        Collection<InventoryData> result = new ArrayList<InventoryData>();
        String sqlGroupBy = "             GROUP BY  PROD.product_id, PROD.internal_name, PCM.PRODUCT_CATEGORY_ID\n"//,  OI.product_Id, OH.order_Type_Id, OI.item_Description\n"
                + "			  ORDER BY PROD.product_Id";
        String scanStr = getGoodIdentificationSql(collectionMap);
        String sqlForm = getProductFormSql(collectionMap);
        String dateSql = getDateSql("OH.order_Date", collectionMap);
        String facilityId = getFacilityId(collectionMap);//"SevenHillsStore";
        if (UtilValidate.isNotEmpty(facilityId)) {
            sqlStr = sqlStr.concat(" AND INV.FACILITY_ID = '" + facilityId + "'");
        }

        String str = sqlStr;
        boolean whereset = false;
        if (UtilValidate.isNotEmpty(dateSql) || UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)) {
            if (UtilValidate.isNotEmpty(dateSql)) {
                str = str.concat(" WHERE  ");
                str = str.concat(dateSql);
                whereset = true;
            }
            if (!whereset && UtilValidate.isNotEmpty(scanStr)) {
                str = str.concat(" AND EXISTS ( ");
                str = str.concat(scanStr);
                str = str.concat(")");
                whereset = true;
            }

            if (!whereset && UtilValidate.isNotEmpty(sqlForm)) {
                str = str.concat(" AND  ");
                str = str.concat(sqlForm);
                //str = str.concat(")");
            }
        }

        str = str.concat(sqlGroupBy);
        Debug.logInfo(str, module);
        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);
        sqlproc.prepareStatement(str);
        ResultSet rs = sqlproc.executeQuery();
        while (rs.next()) {
            InventoryData posCloseData = new InventoryData();
            posCloseData.setREFERENCE(rs.getString("product_Id"));
            posCloseData.setNAME(rs.getString("internal_name"));
            //String orderTypeId = rs.getString("order_Type_Id");
            //posCloseData.setTOTALIN(rs.getDouble("quantity"));

            posCloseData.setUNITSOUT(rs.getBigDecimal("UNITSOUT").doubleValue());
            posCloseData.setTOTALOUT(rs.getBigDecimal("TOTALOUT").doubleValue());

            posCloseData.setUNITSIN(rs.getBigDecimal("UNITSIN").doubleValue());
            posCloseData.setTOTALIN(rs.getBigDecimal("TOTALIN").doubleValue());
            posCloseData.setUNITSDIFF(rs.getBigDecimal("UNITSDIFF").doubleValue());
            posCloseData.setTOTALDIFF(rs.getBigDecimal("TOTALDIFF").doubleValue());

            try {
                posCloseData.setLOCATIONNAME(FacilitySingleton.getFacility(facilityId).getfacilityName());
                posCloseData.setCATEGORYNAME(ProductCategoryMemberSingleton.getProductCategory(rs.getString("PRODUCT_CATEGORY_ID")).getCategoryName());
            } catch (Exception e) {

            }

            result.add(posCloseData);
        }

        rs.close();
        sqlproc.close();

        return result;
    }

    /*
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
     String sqlForm = getProductCategorySql(collectionMap);
     String facilityId = getFacilityId(collectionMap);//"SevenHillsStore";

     if (UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)) {
     if (UtilValidate.isNotEmpty(scanStr)) {
     sqlStr = sqlStr.concat(" AND EXISTS ( ");
     sqlStr = sqlStr.concat(scanStr);
     sqlStr = sqlStr.concat(")");
     }

     if (UtilValidate.isEmpty(scanStr) && UtilValidate.isNotEmpty(sqlForm)) {
     sqlStr = sqlStr.concat(" AND EXISTS ( ");
     sqlStr = sqlStr.concat(sqlForm);
     sqlStr = sqlStr.concat(")");
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
     }*/
}
