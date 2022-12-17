/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports.erp;

import com.openbravo.basic.BasicException;
import org.ofbiz.ordermax.report.reports.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.DataBeanList;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.orderbase.OrderFinancialData;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import org.ofbiz.ordermax.reportdesigner_old.InvoiceHeaderReport;

/**
 *
 * @author siranjeev
 */
public class NewSalesInvoiceReport extends NewEntityJasperReport {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = NewSalesInvoiceReport.class.getName();

    @Override
    public String identifier() {
        return "Invoice Report";

    }

    @Override
    public String getReportFileName() {

        //return "balaji_SalesInvoice.jrxml";
        return "SalesInvoice5.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        // return "balaji_SalesInvoice.jasper";
        return "SalesInvoice5.jasper";
    }

    @Override
    public String getSubReportCompiledFileName() {
        return "PurchaseInvoiceItemReport_old.jasper";
    }

    @Override
    public String getSubReportFileName() {
        return "PurchaseInvoiceItemReport_old.jrxml";
    }

    @Override
    public JPanel runReport() {
        try {
            deactivate();

            setReport(getCompiledReportPathAndFile());
            List<EntityCondition> condList = getEntityConditions();
            //  EntityCondition cond = EntityCondition.makeCondition("paymentTypeId", EntityOperator.EQUALS, "CUSTOMER_PAYMENT");
            //  condList.add(cond);
            Map<String, Object> paramVal = getValues();
            reportArgs.putAll(getValues());
            Map<String, Object> values = (Map<String, Object>) paramVal.get("JParamsInvoiceId");
            Debug.logInfo(" values: " + values.toString(), module);
            String orderId = (String) values.get("invoiceId");
            if (UtilValidate.isNotEmpty(orderId)) {
                reportArgs.put("invoiceId", orderId);
                NewEntityJasperReport.addConditionList(reportArgs, condList);
                NewEntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
                NewEntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
                addSubSourceFileName(reportArgs, getSubReportPathAndFile());
                addSubCompiliedFileName(reportArgs, getSubCompiledReportPathAndFile());
                launchreport(reportArgs);
            }
        } catch (BasicException ex) {
            Logger.getLogger(NewSalesInvoiceReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
    }

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
        DataBeanList dataBeanList = new DataBeanList();
        String invoiceId = "";
        Collection result = new ArrayList<InvoiceHeaderReport>();
//        InvoiceComposite invoiceComposite = null;
        String partyId = null;
        String invoiceTypeId = null;

        if (reportArgs.containsKey("invoiceId")) {
            invoiceId = (String) reportArgs.get("invoiceId");
        } else {
            throw new Exception("Invalid invoice number: " + invoiceId);
        }

        if (UtilValidate.isNotEmpty(invoiceId)) {
            GenericValue genericVal = session.getDelegator().findByPrimaryKey("Invoice",
                    UtilMisc.toMap("invoiceId", invoiceId));
            if (genericVal != null) {
                partyId = genericVal.getString("partyId");
                invoiceTypeId = (String) genericVal.getString("invoiceTypeId");
            }
        } else {
            throw new Exception("Invalid invoice number: " + invoiceId);
        }

        if (UtilValidate.isEmpty(partyId)) {
            throw new Exception("Invalid invoice number: " + invoiceId);
        }

//        if (reportArgs.containsKey("partyId")) {
//            partyId = (String) reportArgs.get("partyId");
        //           invoiceTypeId = (String) reportArgs.get("invoiceTypeId");
//        }
//        else{
//        throw new Exception("Invalid invoice number: " + invoiceId);
        //       }
//        if (reportArgs.containsKey("Invoice")) {
//            invoiceComposite = (InvoiceComposite) reportArgs.get("Invoice");
//        }
//        if (reportArgs.containsKey("invoiceTypeId")) {
//            invoiceTypeId = (String) reportArgs.get("invoiceTypeId");
//        }
//        if (invoiceComposite == null) {
//            invoiceComposite = LoadInvoiceWorker.loadInvoice(invoiceId, session);
//        }
        //load financial data
        final ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();

        //get financial data for this party
//        String billFromPartyId = invoiceComposite.getPartyFrom().getParty().getpartyId();
        List<InvoiceComposite> list = LoadInvoiceWorker.loadOutstandingInvoices(partyId, invoiceTypeId /*"PURCHASE_INVOICE"*/, session);
        invoiceCompositeListModel.addAll(list);
        OrderFinancialData orderFinancialData = new OrderFinancialData(invoiceCompositeListModel, OrderFinancialData.getCurrentDate());

//        OrderFinancialData orderFinancialData = null;
//        if (reportArgs.containsKey("OrderFinancialData")) {
//            orderFinancialData = (OrderFinancialData) reportArgs.get("OrderFinancialData");
//        }
//        invoiceIds.add("MACI194");
//        for (String invoiceId : invoiceIds) {
        System.out.println(" Invoice ID: " + invoiceId);
        InvoiceHeaderReport headerReport = dataBeanList.getSalesInvoiceBean(session, invoiceId, startDate, endDate, orderFinancialData);
        System.out.println(" Invoice ID");
        System.out.println(invoiceId);
        result.add(headerReport);

        return result;
    }
    /*
     @Override
     public JPanel getSelectionPanel() {

     Debug.logInfo("getSelectionPanel", module);
     ReportParameterSelectionPanel panelFilter = new ReportParameterSelectionPanel();
     filterList.clear();
     GridBagLayout layout = new GridBagLayout();
     panelFilter.setLayout(layout);
     GridBagConstraints gbc = new GridBagConstraints();
     ControllerOptions controllerOptions = new ControllerOptions();
     gbc.fill = GridBagConstraints.HORIZONTAL;
     gbc.anchor = GridBagConstraints.NORTHWEST;
     gbc.insets = new Insets(10, 0, 0, 0);  //top padding   
     gbc.weightx = 1;

     int idx = 0;
     int idy = 0;
     //        JPanel panel = AddDateSelection("orderDate", controllerOptions, "Date Selection:");
     //        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

     //        JPanel panel = AddProductIdSelection(controllerOptions, "Product Id:");
     //        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
     //JPanel panel = AddPartyIdSelection(controllerOptions, "Party Id:");
     //addToGridLayout(panelFilter, panel, gbc, idx, idy++);
     //JPanel panel = createReportSelectionComboPanel("invoiceTypeId", InvoiceTypeSingleton.getValueList(), "Invoice Type");
     //addToGridLayout(panelFilter, panel, gbc, idx, idy++);
     JPanel panel = AddInvoiceIdSelection(filterList, controllerOptions, "Invoice Id:");
     addToGridLayout(panelFilter, panel, gbc, idx, idy++);

     return panelFilter;
     }
     */

    @Override
    public void loadParameterSelections() {
        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportInvoiceId(getQbffilter(), options, "Select Invoice");
    }
}
