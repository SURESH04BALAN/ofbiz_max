/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.reports.params;

import com.openbravo.basic.BasicException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author BBS Auctions
 */
public class JParamHelper {

    static public JPanel addReportProductIdSelection(JParamsComposed filterList, ControllerOptions controllerOptions, String labelName) {
        JParamsProductId productPickerEditPanel = new JParamsProductId("Product Id");
        ComponentBorder.doubleRaisedLoweredBevelBorder(productPickerEditPanel, labelName);
        filterList.addEditor(productPickerEditPanel);
        return productPickerEditPanel;
    }

    static public JPanel addReportCategoryIdSelection(JParamsComposed filterList, ControllerOptions controllerOptions, String labelName) {
        JParamsCategoryId paramsCategoryId = new JParamsCategoryId("Category Id");
        ComponentBorder.doubleRaisedLoweredBevelBorder(paramsCategoryId, labelName);
        filterList.addEditor(paramsCategoryId);
        return paramsCategoryId;
    }

    static public JPanel AddReportDateIntervalSelection(JParamsComposed filterList, ControllerOptions controllerOptions, String name, String fieldId) {
        com.openbravo.pos.reports.params.JParamsStartAndEndDatesInterval paramdates = new com.openbravo.pos.reports.params.JParamsStartAndEndDatesInterval(fieldId);
        // paramdates.setStartDate(com.openbravo.beans.DateUtils.getToday());
        ComponentBorder.doubleRaisedLoweredBevelBorder(paramdates, name);
        filterList.addEditor(paramdates);
        return paramdates;
    }

    static public JPanel AddReportDateSelection(JParamsComposed filterList, ControllerOptions controllerOptions, String name, String fieldId) {
        com.openbravo.pos.reports.params.JParamsDateSelection paramdates = new com.openbravo.pos.reports.params.JParamsDateSelection(fieldId);
        // paramdates.setStartDate(com.openbravo.beans.DateUtils.getToday());
        ComponentBorder.doubleRaisedLoweredBevelBorder(paramdates, name);
        filterList.addEditor(paramdates);
        return paramdates;
    }

    
    static public JPanel AddReportPartyId(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsPartyId panel = new com.openbravo.pos.reports.params.JParamsPartyId("Party Id");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }

    static public JPanel AddReportProductId(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsProductId panel = new com.openbravo.pos.reports.params.JParamsProductId("Product Id");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }

    static public JPanel AddReportFormId(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormId panel = new com.openbravo.pos.reports.params.JParamsFormId("By form");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }

    static public JPanel AddReportFormOrder(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormOrders panel = new com.openbravo.pos.reports.params.JParamsFormOrders("By form");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }

    static public com.openbravo.pos.reports.params.JParamsFormInvoices AddReportFormInvoice(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormInvoices panel = new com.openbravo.pos.reports.params.JParamsFormInvoices(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }

    static public com.openbravo.pos.reports.params.JParamsFormParties AddReportFormParty(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormParties panel = new com.openbravo.pos.reports.params.JParamsFormParties(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }
    
    static public com.openbravo.pos.reports.params.JParamsFormInventoryItem AddReportFormInventoryItem(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormInventoryItem panel = new com.openbravo.pos.reports.params.JParamsFormInventoryItem(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }
    
    static public com.openbravo.pos.reports.params.JParamsFormProductPrice AddReportFormProductPrice(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormProductPrice panel = new com.openbravo.pos.reports.params.JParamsFormProductPrice(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }
    
    
    static public com.openbravo.pos.reports.params.JParamsFormPayment AddReportFormPayment(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormPayment panel = new com.openbravo.pos.reports.params.JParamsFormPayment(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }    
    static public com.openbravo.pos.reports.params.JParamsFormProducts AddReportFormProduct(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormProducts panel = new com.openbravo.pos.reports.params.JParamsFormProducts(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }
    
   static public com.openbravo.pos.reports.params.JParamsFormBillingAccount AddReportFormBillingAccount(JParamsComposed paramContainer, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsFormBillingAccount panel = new com.openbravo.pos.reports.params.JParamsFormBillingAccount(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        paramContainer.addEditor(panel);
        return panel;
    }
   
    static public JPanel AddReportOrderId(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsOrderId panel = new com.openbravo.pos.reports.params.JParamsOrderId("Order Id");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }

    static public JPanel AddReportInvoiceId(JParamsComposed filterList, ControllerOptions controllerOptions, String name) {
        com.openbravo.pos.reports.params.JParamsInvoiceId panel = new com.openbravo.pos.reports.params.JParamsInvoiceId("Invoice Id");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);
        return panel;
    }

    static public <E> JPanel addReportSelectionComboPanel(JParamsComposed filterList, String keyId, List<E> values, String name, String paramId, String labelCaption, E defaultValue) {

        com.openbravo.pos.reports.params.JParamsGenericComboBoxSelection<E> panel = new com.openbravo.pos.reports.params.JParamsGenericComboBoxSelection<E>(keyId, values, paramId, defaultValue);
        panel.labelCaption.setText(labelCaption);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);

        return panel;
    }

    static public JPanel addReportGoodIdentificationPanel(JParamsComposed filterList, String keyId, String name, String paramId, String labelCaption, GoodIdentificationType defaultValue) {

        com.openbravo.pos.reports.params.JParamsGoodIdentificationSelection panel = new com.openbravo.pos.reports.params.JParamsGoodIdentificationSelection(keyId, paramId, defaultValue);
        panel.labelCaption.setText(labelCaption);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panel, name);
        filterList.addEditor(panel);

        return panel;
    }

    static public List<EntityCondition> getEntityConditions(List<ReportParameterSelectionInterface> filterList) {
        List<EntityCondition> entityConditionList = new ArrayList<EntityCondition>();
        for (ReportParameterSelectionInterface genPanel : filterList) {
            EntityCondition cond = genPanel.getEntityCondition();
            if (cond != null) {
                Debug.logInfo("cond : " + cond.toString(), "module");
                entityConditionList.add(cond);
            }
        }
        return entityConditionList;
    }

    static public EntityCondition getEntityCondition(List<ReportParameterSelectionInterface> filterList) {
        List<EntityCondition> entityConditionList = new ArrayList<EntityCondition>();
        for (ReportParameterSelectionInterface genPanel : filterList) {
            EntityCondition cond = genPanel.getEntityCondition();
            if (cond != null) {
                entityConditionList.add(cond);
            }
        }
        return EntityCondition.makeCondition(entityConditionList, EntityOperator.AND);
    }

    static public Map<String, Object> getValuesMap(List<ReportParameterSelectionInterface> filterList) throws BasicException {
        Map<String, Object> values = new HashMap<String, Object>();
        Debug.logInfo("getValuesMap: " + filterList.size(), "module");
        for (ReportParameterSelectionInterface genPanel : filterList) {
                   Debug.logInfo("getValuesMap: " + genPanel.toString(), "module");
            genPanel.getValueMap(values);      
        }                
           Debug.logInfo("getValuesMap: " + values, "module");
        return values;
    }

}
