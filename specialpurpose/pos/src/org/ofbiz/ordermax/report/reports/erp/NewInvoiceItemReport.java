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
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class NewInvoiceItemReport extends NewEntityJasperReport {

    public static final String module = NewInvoiceItemReport.class.getName();

    @Override
    public String identifier() {
        return "Invoice Item Report";
    }

    @Override
    public String getReportFileName() {
        return "InvoiceItem.jrxml";
    }
   @Override
    public String getReportCompiledFileName() {
        // return "balaji_SalesInvoice.jasper";
        return "InvoiceItem.jasper";
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
            reportArgs.put("invoiceId", orderId);
            NewEntityJasperReport.addConditionList(reportArgs, condList);
            NewEntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
            NewEntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
            addEntity(reportArgs, "InvoiceItem");
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
        com.openbravo.pos.reports.params.JParamHelper.AddReportInvoiceId(getQbffilter(), options, "Select Invoice");
    }

}
