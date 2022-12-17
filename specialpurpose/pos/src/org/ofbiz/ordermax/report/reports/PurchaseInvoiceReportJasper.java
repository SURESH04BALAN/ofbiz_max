/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import org.ofbiz.ordermax.report.ReportInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.DataBeanList;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.orderbase.OrderFinancialData;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addCompiliedFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSourceFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSubCompiliedFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSubSourceFileName;
import org.ofbiz.ordermax.reportdesigner_old.InvoiceHeaderReport;

/**
 *
 * @author siranjeev
 */
public class PurchaseInvoiceReportJasper extends EntityJasperReport implements ReportInterface {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = PurchaseInvoiceReportJasper.class.getName();

    @Override
    public String identifier() {
        return "Invoice Report";

    }

    @Override
    public String getReportFileName() {
        return "PurchaseInvoice3.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "PurchaseInvoice3.jasper";
    }

    @Override
    public String getSubReportCompiledFileName() {
        return "PurchaseInvoiceItemReport.jasper";
    }

    @Override
    public String getSubReportFileName() {
        return "PurchaseInvoiceItemReport.jrxml";
    }

    @Override
    public JPanel runReport() {

        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Invoice Report");
        param.put("DataFile", "CustomBeanFactory.java - Bean Collection");

//        Map<String, Object> reportArgs = EntityJasperReport.getNewArgMap();
//        EntityJasperReport.addParameters(reportArgs, param);
        getSelectionPanelValues(reportArgs);
        EntityJasperReport.addConditionList(reportArgs, condList);
        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
//        EntityJasperReport.addFacilityId(reportArgs, facilityId);

        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        addCompiliedFileName(reportArgs, getCompiledReportPathAndFile());
        addSubSourceFileName(reportArgs, getSubReportPathAndFile());
        addSubCompiliedFileName(reportArgs, getSubCompiledReportPathAndFile());

        return super.runReport(reportArgs);
        /*
         Map<String, Object> whereClauseMap = getWhereClause();
         Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
         param.put("ReportTitle", "Address Report");
         param.put("DataFile", "CustomBeanFactory.java - Bean Collection");

         Map<String, Object> reportArgs = EntityJasperReport.getNewArgMap();
         //        EntityJasperReport.addParameters(reportArgs, param);        
         EntityJasperReport.addWhereClause(reportArgs, whereClauseMap);
         EntityJasperReport.addDelegator(reportArgs, session.getDelegator());
         EntityJasperReport.addSession(reportArgs, session);
         //       EntityJasperReport.addFacilityId(reportArgs,facilityId);

         if (panelFilter != null) {
         panelFilter.getDateSelection();

         java.sql.Timestamp dateEnd = new java.sql.Timestamp(panelFilter.getEndDate().getTime());
         java.sql.Timestamp dateStart = new java.sql.Timestamp(panelFilter.getStartDate().getTime());
         EntityJasperReport.addStartDate(reportArgs, dateStart);
         EntityJasperReport.addEndDate(reportArgs, dateEnd);

         }

         getParameters(reportArgs);

         //Path currentRelativePath = Paths.get("");
         // String s = currentRelativePath.toAbsolutePath().toString();
         //System.out.println("Current relative path is: " + s);
         String masterReportFileName = getReportPathAndFile();// s.concat("\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\PurchaseInvoice3.jasper");
         //String dir = "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\";
         //        String dir = "C:\\AuthLog\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\";
         //        String masterReportFileName = dir + "PurchaseInvoice3.jasper";
         //        String subReportFileName = s.concat("\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\PurchaseInvoiceItemReport.jrxml");
         String subReportFileName = getReportPath().concat("PurchaseInvoiceItemReport.jrxml");
         //        dir + "PurchaseInvoiceItemReport.jrxml";
         */
        //      String destFileName = s + "PurchaseInvoice.JRprint";
//        }

        //     JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        //      JasperPrint jasperPrint = null;
//        try {
            /* Compile the master and sub report */
        /*           JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportFileName);

         //            JasperReport jasperMasterReport = JasperCompileManager.compileReport(masterReportFileName);
         JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObjectFromFile(masterReportFileName);
         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("subreportParameter", jasperSubReport);

         parameters.put("TOTAL_AMT", new BigDecimal(1000).toString());
         System.out.println(" subreportDir: " + getReportPath());
         parameters.put("SUBREPORT_DIR", getReportPath());

         jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);

         //            JasperViewer.viewReport(jasperPrint);
         } catch (JRException e) {

         Debug.logError(e, module);
         }
         System.out.println("Done filling!!! ...");
         */
//        return super.getReportPanel(jasperPrint);
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
                partyId = genericVal.getString("partyIdFrom");
                invoiceTypeId = (String) genericVal.getString("invoiceTypeId");
            }
        }
         else {
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
        InvoiceHeaderReport headerReport = dataBeanList.getInvoiceBean(session, invoiceId, startDate, endDate, orderFinancialData);
        System.out.println(" Invoice ID");
        System.out.println(invoiceId);
        result.add(headerReport);

        return result;
    }

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
        JPanel panel = AddInvoiceIdSelection(filterList,controllerOptions, "Invoice Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        return panelFilter;
    }
}
