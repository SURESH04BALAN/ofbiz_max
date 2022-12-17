/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addCompiliedFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addParametersNameMap;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSourceFileName;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class CustomerPaymentSummaryAgeingReportJasper extends SupplierAgeingReport {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = CustomerPaymentSummaryAgeingReportJasper.class.getName();

    @Override
    public String identifier() {
        return "Customer Payment Summary Ageing";
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

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
//        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
          getSelectionPanelValues(param);
        java.sql.Timestamp date = (java.sql.Timestamp) param.get(currentDate);        
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
        param.put("LAST90EOM_HEAD", new SimpleDateFormat("dd/MM").format(last90Month.getStart()) +  " >");

        reportArgs.put("invoiceTypeId", "SALES_INVOICE");

//        EntityJasperReport.addConditionList(reportArgs, condList);
        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        addCompiliedFileName(reportArgs, getCompiledReportPathAndFile());
        addParametersNameMap(reportArgs, param);
        return super.runReport(reportArgs);
    }

    ReportDateSelectionPanel panelFilter = null;
/*
    @Override
    public JPanel getSelectionPanel() {
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
        JPanel panel = AddDateSelection("effectiveDate", controllerOptions, "Date Selection:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddPartyIdSelection(controllerOptions, "Party Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

//        panel = AddProductIdSelection(controllerOptions, "Product Id:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        panel = createReportSelectionComboPanel("orderTypeId", OrderTypeSingleton.getValueList(), "Order Type");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddOrderIdSelection(controllerOptions, "Order Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        return panelFilter;
    }
*/
}
