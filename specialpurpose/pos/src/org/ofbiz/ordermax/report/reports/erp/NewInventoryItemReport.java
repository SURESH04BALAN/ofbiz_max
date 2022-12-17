/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports.erp;

import com.openbravo.basic.BasicException;
import org.ofbiz.ordermax.report.reports.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author siranjeev
 */
public class NewInventoryItemReport extends NewEntityJasperReport {

    public static final String module = NewInventoryItemReport.class.getName();
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
        try {
            deactivate();

            setReport(getCompiledReportPathAndFile());
            List<EntityCondition> condList = getEntityConditions();
            //  EntityCondition cond = EntityCondition.makeCondition("paymentTypeId", EntityOperator.EQUALS, "CUSTOMER_PAYMENT");
            //  condList.add(cond);
            Map<String, Object> paramVal = getValues();
            reportArgs.putAll(getValues());
            Map<String, Object> values = (Map<String, Object>) paramVal.get("JParamsOrderId");
            Debug.logInfo(" values: " + values.toString(), module);
            String orderId = (String) values.get("orderId");
            reportArgs.put("orderId", orderId);
            NewEntityJasperReport.addConditionList(reportArgs, condList);
            NewEntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
            NewEntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        addEntity(reportArgs, "OrderHeaderAndItems");
        addOrderBy(reportArgs, "orderDate ASC");

        } catch (BasicException ex) {
            Logger.getLogger(NewSalesInvoiceReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        launchreport(reportArgs);
        return this;
    }
    
    
    @Override
    public void loadParameterSelections() {
        ControllerOptions optionsDate = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(getQbffilter(), optionsDate, "Date Selection", "effectiveDate");

        ControllerOptions optionsParty = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(getQbffilter(), optionsParty, "Select Party");

        ControllerOptions optionsProduct = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportProductId(getQbffilter(), optionsProduct, "Select Product");

        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportOrderId(getQbffilter(), options, "Select Order");
    }
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
*/
}
