/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ofbiz.ordermax.report.ReportInterface;
import org.ofbiz.ordermax.report.reports.erp.NewCustomerPaymentReportJasper;
import org.ofbiz.ordermax.report.reports.erp.NewCustomerPaymentSummaryAgeingReportJasper;
import org.ofbiz.ordermax.report.reports.erp.NewCustomerSalesReport;
import org.ofbiz.ordermax.report.reports.erp.NewInventoryItemReport;
import org.ofbiz.ordermax.report.reports.erp.NewInvoiceItemReport;
import org.ofbiz.ordermax.report.reports.erp.NewPickingSlipReportJasper;
import org.ofbiz.ordermax.report.reports.erp.NewPurchaseInvoiceReport;
import org.ofbiz.ordermax.report.reports.erp.NewSalesInvoiceReport;
import org.ofbiz.ordermax.report.reports.erp.NewSalesSummaryReportJasper;
import org.ofbiz.ordermax.report.reports.erp.NewSupplierAgeingSummaryReport;
import org.ofbiz.ordermax.report.reports.erp.NewSupplierPaymentReport;

/**
 *
 * @author siranjeev
 */
public class ReportBaseFactory {

//    private static List<ReportInterface> reports = null;
    final static public String REP_GROUP_SUPPLIER = "Supplier Reports";
    final static public String REP_GROUP_POS = "POS Reports";
    final static public String REP_GROUP_CUSTOMER = "Customer Reports";
    final static public String REP_GROUP_PRODUCT = "Product Reports";
    final static public String REP_GROUP_INVENTORY = "Inventory Report";

    final static public String REP_SUB_GROUP_SALES = "Sales Report";
    final static public String REP_SUB_GROUP_PURCHASE = "Purchse Report";    
    final static public String REP_SUB_GROUP_ORDER = "Order Report";
    final static public String REP_SUB_GROUP_INVENTORY = "Inventory Report";
    final static public String REP_SUB_GROUP_PAYMENT = "Payment Report";
    final static public String REP_SUB_GROUP_ACCOUNTS = "Accounts Report";
    final static public String REP_SUB_GROUP_GENERAL = "General Report";
    final static public String REP_SUB_GROUP_PRODUCTS = "Product Report";

    final static public Map<String, Map<String, List<String>>> reportMap = new HashMap<String, Map<String, List<String>>>();

    ;

    static {

//        reports = new ArrayList<ReportInterface>();

        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_PAYMENT, NewCustomerPaymentSummaryAgeingReportJasper.module);                
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_ORDER, NewPickingSlipReportJasper.module);
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_INVENTORY, InventoryItemReceiptReportJasper.module);
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_SALES, NewCustomerSalesReport.module);        
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_SALES, NewSalesSummaryReportJasper.module);        
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_PAYMENT, NewCustomerPaymentReportJasper.module);
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_SALES, NewSalesInvoiceReport.module);  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_SALES, NewInvoiceItemReport.module);  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_CUSTOMER, REP_SUB_GROUP_INVENTORY, NewInventoryItemReport.module);          
        
        

        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_SUPPLIER, REP_SUB_GROUP_PAYMENT, NewSupplierAgeingSummaryReport.module);        
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_SUPPLIER, REP_SUB_GROUP_PURCHASE, InvoiceItemReport.module);
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_SUPPLIER, REP_SUB_GROUP_INVENTORY, NewInventoryItemReport.module);
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_SUPPLIER, REP_SUB_GROUP_ACCOUNTS, ProfitReport.module);
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_SUPPLIER, REP_SUB_GROUP_PURCHASE, NewPurchaseInvoiceReport.module); 
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_SUPPLIER, REP_SUB_GROUP_PAYMENT, NewSupplierPaymentReport.module);            
//        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_SUPPLIER, NewSupplierAgeingReport.module);
        

//        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, EndOfTheDayReportPanel.module);        
//        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, InventoryReportPanel.module);                
//        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, ProductRequirmentReportPanel.module);                        
//        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, SalesSummaryReportJasper.module); 
//        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, ClosedPosReportJasper.module); 
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_ACCOUNTS, PosEndOfTheDayReport.module);         
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_PRODUCTS, PosProductReport.module); 
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_PRODUCTS, PosClosedProductsReport.module);      
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_ACCOUNTS, PosCustomersDiaryReport.module);      
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_SALES, PosUserSalesReport.module);      
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_PRODUCTS, PosProductSalesReport.module);  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_PRODUCTS, PosProductLabelsReport.module);  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_ACCOUNTS, PosPeopleReport.module);  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_INVENTORY, PosInventoryReport.module);                  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_INVENTORY, PosInventoryBReport.module);  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_INVENTORY, PosInventoryBySupplierReport.module);
        
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_INVENTORY, PosInventoryDiffReport.module);  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_INVENTORY, PosInventoryDiffDetailReport.module);                  
        ReportBaseFactory.registerReport(ReportBaseFactory.REP_GROUP_POS, REP_SUB_GROUP_INVENTORY,PosInventoryListDetailReport.module);                                                 
    }

    public static void registerReport(String group, String subGroup, String report) {
        if (reportMap.containsKey(group) == false) {
            Map<String, List<String>> subReportGroup = new HashMap<String, List<String>>();
            List<String> reportsList = new ArrayList<String>();
            reportsList.add(report);
            subReportGroup.put(subGroup, reportsList);
            reportMap.put(group, subReportGroup);
        } else {
            Map<String, List<String>> subReportGroup = reportMap.get(group);
            if (subReportGroup.containsKey(subGroup) == false) {
                List<String> reportsList = new ArrayList<String>();
                reportsList.add(report);
                subReportGroup.put(subGroup, reportsList);
            } else {
                subReportGroup.get(subGroup).add(report);
            }
        }
    }

    public static List<String> getSubGroupList(String group) {
        List reportList = new ArrayList<String>();
        Map<String, List<String>> rList = reportMap.get(group);
        if (rList != null) {
            reportList.addAll(rList.keySet());
        }
        return reportList;
    }

    public static List<String> getReportList(String group, String subGroup) {
        List reportList = new ArrayList<String>();
        Map<String, List<String>> subReportGroup = reportMap.get(group);
        if (subReportGroup != null && subReportGroup.containsKey(subGroup)) {
            reportList.addAll(subReportGroup.keySet());
            /*List<String> rList = subReportGroup.get(subGroup);
             if (rList != null) {
             for (String instance : rList) {
             reportList.add(instance);
             }
             }*/
        } else {
            for (Map.Entry<String, Map<String, List<String>>> subreports : reportMap.entrySet()) {

                Map<String, List<String>> reports = subreports.getValue();

                for (Map.Entry<String, List<String>> mapEntry : reports.entrySet()) {
                    reportList.addAll(mapEntry.getValue());
                }
            }

        }

        return reportList;
    }

    public static ReportInterface getReport(String reportName) throws Exception {

        for (Map.Entry<String, Map<String, List<String>>> subreports : reportMap.entrySet()) {

            Map<String, List<String>> reports = subreports.getValue();

            for (Map.Entry<String, List<String>> mapEntry : reports.entrySet()) {
                for (String instance : mapEntry.getValue()) {
                    if (instance.equals(reportName)) {
                        return (ReportInterface) Class.forName(instance).getConstructor().newInstance();
                    }
                }
            }
        }
        throw new Exception("Report not found: " + reportName);
    }
}
