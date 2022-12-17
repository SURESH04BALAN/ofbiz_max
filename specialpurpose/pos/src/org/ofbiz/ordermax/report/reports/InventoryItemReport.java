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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class InventoryItemReport extends EntityJasperReport {

    public static final String module = InventoryItemReport.class.getName();
    /*    static {
     ReportBaseFactory.registerReport(new InventoryItemReport());
     }
     */

    @Override
    public String identifier() {
        return "Inventory Item Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "InventoryItem.jasper";
    }

    @Override
    public String getReportFileName() {
        return "InventoryItem.jrxml";
    }

    @Override
    public JPanel runReport() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Inventory Item Report");
        param.put("DataFile", "Entity: OrderHeaderAndItems");
        param.put("PRODUCT_STORE", "Max Warehouse");

        EntityJasperReport.addConditionList(reportArgs, condList);
        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        String subReport = getReportPath().concat("InventoryItem.jasper");
        addCompiliedFileName(reportArgs, subReport);//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jasper");
        addEntity(reportArgs, "OrderHeaderAndItems");
        addOrderBy(reportArgs, "orderDate ASC");

//        EntityJasperReport.addWhereClause(reportArgs, param);
        //       EntityJasperReport.addFacilityId(reportArgs, facilityId);
        return super.runReport(reportArgs);
    }

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
        JPanel panel = AddDateSelection(filterList,"orderDate", controllerOptions, "Date Selection:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddPartyIdSelection(filterList,controllerOptions, "Party Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddProductIdSelection(filterList,controllerOptions, "Product Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = createReportSelectionComboPanel(filterList,"orderTypeId", OrderTypeSingleton.getValueList(), "Order Type");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddOrderIdSelection(filterList,controllerOptions, "Order Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        return panelFilter;
    }

}
