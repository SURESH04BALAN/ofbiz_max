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
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addCompiliedFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addEntity;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSourceFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.getReportPath;

/**
 *
 * @author siranjeev
 */
public class InvoiceItemReport extends EntityJasperReport {

    public static final String module = InvoiceItemReport.class.getName();

    @Override
    public String identifier() {
        return "Invoice Item Report";
    }

    @Override
    public String getReportFileName() {
        return "InvoiceItem.jrxml";
    }

    @Override
    public JPanel runReport() {

        /*     Map<String,Object> whereClauseMap = getWhereClause();
         Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
         param.put("ReportTitle", "Address Report");
         param.put("DataFile", "CustomBeanFactory.java - Bean Collection");
                
         Map<String,Object> reportArgs = EntityJasperReport.getNewArgMap();  
         EntityJasperReport.addParameters(reportArgs, param);        
         EntityJasperReport.addWhereClause(reportArgs, whereClauseMap);
         EntityJasperReport.addDelegator(reportArgs, session.getDelegator());
         EntityJasperReport.addSession(reportArgs, session );
         EntityJasperReport.addFacilityId(reportArgs,facilityId);
        
         addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InvoiceItem.jrxml");
         String subReport = getReportPath().concat("InvoiceItem.jasper");        
         addCompiliedFileName(reportArgs, subReport);//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InvoiceItem.jasper");
         addEntity(reportArgs, "InvoiceItem");
         */
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Inventory Item Report");
        param.put("DataFile", "Entity: OrderHeaderAndItems");

        Map<String, Object> reportArgs = EntityJasperReport.getNewArgMap();
        EntityJasperReport.addConditionList(reportArgs, condList);
        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        String subReport = getReportPath().concat("InvoiceItem.jasper");
        addCompiliedFileName(reportArgs, subReport);//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jasper");
        addEntity(reportArgs, "InvoiceItem");
//        addOrderBy(reportArgs, "orderDate ASC");

//        EntityJasperReport.addWhereClause(reportArgs, param);
        //       EntityJasperReport.addFacilityId(reportArgs, facilityId);
        return super.runReport(reportArgs);

    }

    ReportDateSelectionPanel panelFilter = null;

   

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
//        JPanel panel = AddDateSelection("invoiceDate", controllerOptions, "Date Selection:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        JPanel panel = AddPartyIdSelection(filterList,controllerOptions, "Party Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddProductIdSelection(filterList,controllerOptions, "Product Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        
/*        List<InvoiceType> invoiceTypeList = InvoiceTypeSingleton.getValueList();
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setdescription("All");
        invoiceType.setinvoiceTypeId("ANY");
        invoiceTypeList.add(0, invoiceType);
        
//        panel = createReportSelectionComboPanel("invoiceTypeId", invoiceTypeList, "Invoice Type");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
*/
        panel = AddInvoiceIdSelection(filterList,controllerOptions, "Invoice Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        
        return panelFilter;
    }

}
