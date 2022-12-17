/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports.erp;

import com.openbravo.basic.BasicException;
import org.ofbiz.ordermax.report.reports.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import static org.ofbiz.ordermax.report.reports.NewEntityJasperReport.addConditionList;
import static org.ofbiz.ordermax.report.reports.NewEntityJasperReport.addEntity;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class NewSupplierAgeingSummaryReport extends NewSupplierAgeingReport {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = NewSupplierAgeingSummaryReport.class.getName();

    @Override
    public String identifier() {
        return "Supplier Ageing Report";
    }

    @Override
    public String getReportFileName() {
        return "CustomerPaymentSummaryAgeingReport.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "CustomerPaymentSummaryAgeingReport.jasper";
    }
    
    
    ShipmentReceiptComposite orderFinancialData = null;

    @Override
    public JPanel runReport() {

        deactivate();

        setReport(getCompiledReportPathAndFile());
//        setResourceBundle(getResourcePathAndFile());
        addConditionList(reportArgs, getEntityConditions());
        
        try {
            Map<String, Object> paramVal =  getValues();
            reportArgs.putAll(getValues());
            Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
            
            Map<String, Object> values = (Map<String, Object>)paramVal.get("JParamsDateSelection");
            Debug.logInfo(" values: " + values.toString(), module);
            java.sql.Timestamp date = UtilDateTime.nowTimestamp();
            if(values.containsKey("startDate")){
                date = (java.sql.Timestamp) values.get("startDate");            
            }
            
            org.ofbiz.ordermax.Dates.Month curMonth = new org.ofbiz.ordermax.Dates.Month(date);

            param.put("ReportTitle", "Inventory Item Report");
            param.put("DataFile", "Entity: OrderHeaderAndItems");
//        org.ofbiz.ordermax.Dates.Month curMonth = new org.ofbiz.ordermax.Dates.Month();

            param.put("CURRENTEOM", curMonth.getEnd());
            param.put("CURRENTEOM_HEAD", new SimpleDateFormat("dd/MM").format(curMonth.getStart())
                    + " - " + new SimpleDateFormat("dd/MM").format(curMonth.getEnd()));

            org.ofbiz.ordermax.Dates.RegularTimePeriod lastMonth = curMonth.previous();
            param.put("LAST30EOM", lastMonth.getEnd());
            param.put("LAST30EOM_HEAD", new SimpleDateFormat("dd/MM").format(lastMonth.getStart())
                    + " - " + new SimpleDateFormat("dd/MM").format(lastMonth.getEnd()));

            org.ofbiz.ordermax.Dates.RegularTimePeriod last60Month = lastMonth.previous();
            param.put("LAST60EOM", last60Month.getEnd());
            param.put("LAST60EOM_HEAD", new SimpleDateFormat("dd/MM").format(last60Month.getStart())
                    + " - " + new SimpleDateFormat("dd/MM").format(last60Month.getEnd()));

            org.ofbiz.ordermax.Dates.RegularTimePeriod last90Month = last60Month.previous();
            param.put("LAST90EOM", last90Month.getEnd());
            param.put("LAST90EOM_HEAD", new SimpleDateFormat("dd/MM").format(last90Month.getStart()) + " >");

            reportArgs.put("invoiceTypeId", "PURCHASE_INVOICE");

        } catch (BasicException ex) {
            Logger.getLogger(PosEndOfTheDayReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        // addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
        /*
         Path currentRelativePath = Paths.get("");
         String s = currentRelativePath.toAbsolutePath().toString();
         //        List<EntityCondition> condList = getWhereClauseCond();
         Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
         //    getSelectionPanelValues(param);
         //   java.sql.Timestamp date = (java.sql.Timestamp) param.get(currentDate);        
         //        org.ofbiz.ordermax.Dates.Month curMonth = new org.ofbiz.ordermax.Dates.Month(date);

         param.put("ReportTitle", "Inventory Item Report");
         param.put("DataFile", "Entity: OrderHeaderAndItems");
         //        org.ofbiz.ordermax.Dates.Month curMonth = new org.ofbiz.ordermax.Dates.Month();

         param.put("CURRENTEOM", curMonth.getEnd());
         param.put("CURRENTEOM_HEAD", new SimpleDateFormat("dd/MM").format(curMonth.getStart())
         + " - " + new SimpleDateFormat("dd/MM").format(curMonth.getEnd()));

         org.ofbiz.ordermax.Dates.RegularTimePeriod lastMonth = curMonth.previous();
         param.put("LAST30EOM", lastMonth.getEnd());
         param.put("LAST30EOM_HEAD", new SimpleDateFormat("dd/MM").format(lastMonth.getStart())
         + " - " + new SimpleDateFormat("dd/MM").format(lastMonth.getEnd()));

         org.ofbiz.ordermax.Dates.RegularTimePeriod last60Month = lastMonth.previous();
         param.put("LAST60EOM", last60Month.getEnd());
         param.put("LAST60EOM_HEAD", new SimpleDateFormat("dd/MM").format(last60Month.getStart())
         + " - " + new SimpleDateFormat("dd/MM").format(last60Month.getEnd()));
        
        
         org.ofbiz.ordermax.Dates.RegularTimePeriod last90Month = last60Month.previous();
         param.put("LAST90EOM", last90Month.getEnd());
         param.put("LAST90EOM_HEAD", new SimpleDateFormat("dd/MM").format(last90Month.getStart()) +  " >");

         reportArgs.put("invoiceTypeId", "SALES_INVOICE");

         //        EntityJasperReport.addConditionList(reportArgs, condList);
         EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
         EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
         addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
         addCompiliedFileName(reportArgs, getCompiledReportPathAndFile());
         addParametersNameMap(reportArgs, param);
         return super.runReport(reportArgs);
         */
    }

    ReportDateSelectionPanel panelFilter = null;

}
