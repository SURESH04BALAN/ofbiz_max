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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
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
public class PosInventoryDiffDetailReport extends PosInventoryReport {

    public static final String module = PosInventoryDiffDetailReport.class.getName();

    public PosInventoryDiffDetailReport() {
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
            Logger.getLogger(PosInventoryDiffDetailReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Inventory diary details";
    }

    @Override
    public String getReportCompiledFileName() {
        return "inventorydiffdetail.jasper";
    }

    @Override
    public String getReportFileName() {
        return "inventorydiffdetail.jrxml";
    }

    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties

    @Override
    public String getResourceFileName() {
        return "inventorydiffdetail_messages.properties";
    }
    
   
     String sqlStr = "SELECT PROD.product_id, PROD.internal_name, PCM.PRODUCT_CATEGORY_ID,  \n"
            + "                   OH.ORDER_ID, \n"
            + "                   OI.ORDER_ITEM_SEQ_ID, \n"             
            + "                   OH.order_Type_Id,  \n"
            + "                   OI.product_Id,\n"
            + "                   OI.item_Description,\n"
            + "                   OI.QUANTITY AS UNITS,\n"
            + "                   oi.QUANTITY*oi.UNIT_PRICE AS TOTALVALUE\n"
            + "               FROM ofbiz.Order_Header AS OH \n"
            + "                    INNER JOIN ofbiz.Order_Item AS OI ON (OH.order_id = OI.order_id)\n"
            + "                    INNER JOIN ofbiz.product as PROD ON (PROD.PRODUCT_ID = OI.PRODUCT_ID)\n"
            + "                    INNER JOIN ofbiz.product_category_MEMBER as PCM ON (PROD.product_id = PCM.PRODUCT_ID)\n"
            + " WHERE PCM.PRODUCT_CATEGORY_ID <> 'MS_CAT_PROMOTIONS'";            

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Delegator delegator = ControllerOptions.getSession().getDelegator();

        Collection<InventoryData> result = new ArrayList<InventoryData>();
        String sqlGroupBy = "	ORDER BY OI.product_Id,  OH.ORDER_ID, OI.ORDER_ITEM_SEQ_ID";
        String scanStr = getGoodIdentificationSql(collectionMap);
        String sqlForm = getProductFormSql(collectionMap);
        String dateSql = getDateSql("OH.order_Date", collectionMap);        
        String facilityId = getFacilityId(collectionMap);//"SevenHillsStore";
        if (UtilValidate.isNotEmpty(facilityId)) {
            sqlStr = sqlStr.concat(" AND OH.ORIGIN_FACILITY_ID = '" + facilityId + "'");
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
               // str = str.concat(")");
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
            posCloseData.setNAME(rs.getString("item_Description"));
            String orderTypeId = rs.getString("order_Type_Id");
            String orderId = rs.getString("ORDER_ID");            
            String orderSeqId = rs.getString("ORDER_ITEM_SEQ_ID");       
            posCloseData.setATTINSTANCEDESC(orderId+"_"+orderSeqId);
            posCloseData.setATTINSTANCEID(orderId);
            posCloseData.setATTINSTANCENAME(orderSeqId);
            if ("SALES_ORDER".equals(orderTypeId)) {
                posCloseData.setUNITSOUT(rs.getBigDecimal("UNITS").doubleValue());
                posCloseData.setTOTALOUT(rs.getBigDecimal("TOTALVALUE").doubleValue());
            } else if ("PURCHASE_ORDER".equals(orderTypeId)) {
                posCloseData.setUNITSIN(rs.getBigDecimal("UNITS").doubleValue());
                posCloseData.setTOTALIN(rs.getBigDecimal("TOTALVALUE").doubleValue());
            }

            try {
                posCloseData.setLOCATIONNAME(FacilitySingleton.getFacility(facilityId).getfacilityName());
                posCloseData.setCATEGORYNAME(ProductCategoryMemberSingleton.getProductCategory(rs.getString("PRODUCT_CATEGORY_ID")).getCategoryName());
            } catch (Exception e) {

            }

            result.add(posCloseData);
        }

        for (InventoryData posCloseData : result) {
            posCloseData.setUNITSDIFF(posCloseData.getUNITSIN() - posCloseData.getUNITSOUT());
            posCloseData.setTOTALDIFF(posCloseData.getTOTALIN() - posCloseData.getTOTALOUT());
        }

        rs.close();
        sqlproc.close();

        return result;
    }
    /*
    String sqlStr = " SELECT      OH.ORDER_ID, \n"
            +"                    OI.ORDER_ITEM_SEQ_ID, \n"
            + "                   OH.order_Type_Id,  \n"
            + "                   OI.product_Id,\n"
            + "                   OI.item_Description,\n"
            + "                   OI.QUANTITY AS UNITS,\n"
            + "                   oi.QUANTITY*oi.UNIT_PRICE AS TOTALVALUE\n"
            + "               FROM ofbiz.Order_Header AS OH \n"
            + "                    INNER JOIN ofbiz.Order_Item AS OI ON (OH.order_id = OI.order_id)\n"
            + "             GROUP BY  OH.order_Type_Id, OI.product_Id, OI.item_Description\n"
            + "			  ORDER BY OI.product_Id";

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Delegator delegator = ControllerOptions.getSession().getDelegator();

        String dateSql = getDateSql("OH.order_Date", collectionMap);
        String scanStr = getGoodIdentificationSql(collectionMap);
        String sqlForm = getProductCategorySql(collectionMap);

        Collection<InventoryData> result = new ArrayList<InventoryData>();

        String str = sqlStr;
        boolean whereset = false;
        if (UtilValidate.isNotEmpty(dateSql) || UtilValidate.isNotEmpty(sqlForm) || UtilValidate.isNotEmpty(scanStr)) {

            if (UtilValidate.isNotEmpty(dateSql)) {
                str = str.concat(" WHERE  ");
                str = str.concat(dateSql);
                whereset = true;
            }
            if (!whereset && UtilValidate.isNotEmpty(scanStr)) {
                str = str.concat(" WHERE EXISTS ( ");
                str = str.concat(scanStr);
                str = str.concat(")");
                whereset = true;
            }

            if (!whereset && UtilValidate.isNotEmpty(sqlForm)) {
                str = str.concat(" WHERE EXISTS ( ");
                str = str.concat(sqlForm);
                str = str.concat(")");
            }
        }

        Debug.logInfo(str, module);
        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);
        sqlproc.prepareStatement(str);
        ResultSet rs = sqlproc.executeQuery();
        while (rs.next()) {
            InventoryData posCloseData = new InventoryData();
            posCloseData.setREFERENCE(rs.getString("product_Id"));
            posCloseData.setNAME(rs.getString("item_Description"));
            String orderTypeId = rs.getString("order_Type_Id");
            String orderId = rs.getString("ORDER_ID");            
            String orderSeqId = rs.getString("ORDER_ITEM_SEQ_ID");       
            posCloseData.setATTINSTANCEDESC(orderId+"_"+orderSeqId);
            posCloseData.setATTINSTANCEID(orderId);
            posCloseData.setATTINSTANCENAME(orderSeqId);
            if ("SALES_ORDER".equals(orderTypeId)) {
                posCloseData.setUNITSIN(rs.getBigDecimal("UNITS").doubleValue());
                posCloseData.setTOTALIN(rs.getBigDecimal("TOTALVALUE").doubleValue());
            } else if ("PURCHASE_ORDER".equals(orderTypeId)) {
                posCloseData.setUNITSOUT(rs.getBigDecimal("UNITS").doubleValue());
                posCloseData.setTOTALOUT(rs.getBigDecimal("TOTALVALUE").doubleValue());
            }
            //dataStore.put(productCode, posCloseData);
            result.add(posCloseData);
        }
        
        for( InventoryData posCloseData :  result){
            posCloseData.setUNITSDIFF(posCloseData.getUNITSIN() - posCloseData.getUNITSOUT());
            posCloseData.setTOTALDIFF(posCloseData.getTOTALIN() - posCloseData.getTOTALOUT());            
        }

        rs.close();
        sqlproc.close();

        return result;
    }    */
}
