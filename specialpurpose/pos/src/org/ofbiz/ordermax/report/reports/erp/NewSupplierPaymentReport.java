/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports.erp;

import org.ofbiz.ordermax.report.reports.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class NewSupplierPaymentReport extends NewEntityJasperReport {


//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = NewSupplierPaymentReport.class.getName();

    @Override
    public String identifier() {
        return "Supplier Paymnet Report";
    }
    
    @Override
    public String getReportFileName() {
        return "SupplierPayment.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "SupplierPayment.jasper";
    }
    ShipmentReceiptComposite orderFinancialData = null;

    @Override
    public JPanel runReport() {
        deactivate();

        setReport(getCompiledReportPathAndFile());
        List<EntityCondition> condList = getEntityConditions();
        EntityCondition cond = EntityCondition.makeCondition("paymentTypeId", EntityOperator.EQUALS, "VENDOR_PAYMENT");
        condList.add(cond);
        NewEntityJasperReport.addConditionList(reportArgs, condList);
        NewEntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        NewEntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        
        addEntity(reportArgs, "PaymentAndTypePartyNameView");
        addOrderBy(reportArgs, "effectiveDate ASC");

        launchreport(reportArgs);
        return this;        
    }
    /*
     @Override
     public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

     String entityName = (String) collectionMap.get(EntityName);
     Delegator delegator = (Delegator) collectionMap.get(DelegatorName);
     String orderBy = (String) collectionMap.get(OrderByClause);
     List<GenericValue> genList = null;

     Map<String, Object> whereClauseMap = null;
     List<EntityCondition> entityConditionList = null;
     if (collectionMap.containsKey(WhereClauseMap)) {
     whereClauseMap = (Map<String, Object>) collectionMap.get(WhereClauseMap);
     genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, whereClauseMap, orderBy, delegator);
     } else {
     entityConditionList = (List<EntityCondition>) collectionMap.get(EntityConditionList);
     genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, entityConditionList, orderBy, delegator);
     }

     Collection result = null;
     try {
     String classname = "org.ofbiz.ordermax.entity." + entityName;
     Debug.logInfo("classname: " + classname, module);
     String amethodname = "getObjectList";
     Class myTarget = Class.forName(classname);
     Object myinstance = myTarget.newInstance();
     Method myMethod;
     myMethod = myTarget.getDeclaredMethod(amethodname, List.class);
     if (myMethod != null) {
     result = (Collection) myMethod.invoke(myinstance, genList);
     if (result != null) {
     Debug.logInfo("got valid result: " + classname, module);
     if (result.size() > 0) {
     Debug.logInfo("count got valid result of  " + result.size(), module);
     }
     }
     } else {
     Debug.logInfo("method is not found amethodname: " + classname, module);
     }

     } catch (final ClassNotFoundException e) {
     throw new Exception(e.getMessage());
     } catch (final SecurityException e) {
     throw new Exception(e.getMessage());
     } catch (final NoSuchMethodException e) {
     throw new Exception(e.getMessage());
     } catch (final IllegalArgumentException e) {
     throw new Exception(e.getMessage());
     } catch (final IllegalAccessException e) {
     throw new Exception(e.getMessage());
     } catch (InstantiationException ex) {
     Logger.getLogger(JasperReportGenericPanel.class.getName()).log(Level.SEVERE, null, ex);
     }
     return result;
     }
     */
/*
    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Collection result = new ArrayList<PaymentAndTypePartyNameView>();
        ShipmentReceiptComposite orderFinancialData = null;
        if (collectionMap.containsKey("DataObject")) {
            orderFinancialData = (ShipmentReceiptComposite) collectionMap.get("DataObject");
        } else if (collectionMap.containsKey(OrderPickerEditPanel.keyId)) {
            String orderId = (String) collectionMap.get(OrderPickerEditPanel.keyId);
            ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite(orderId, XuiContainer.getSession());
            
            for (final ShipmentReceiptComposite inventoryReceipt : ric.getShipmentReceiptCompositeList().getList()) {
                orderFinancialData = inventoryReceipt;
                break;
            }
        }

        if (orderFinancialData != null) {
            InventoryData data = new InventoryData();
            data.productId = orderFinancialData.getOrderItemComposite().getOrderItem().getproductId();
//        data.partyId = orderFinancialData.getOrder().getBillFromAccount().getParty().getDisplayName();
            data.description = orderFinancialData.getOrderItemComposite().getOrderItem().getitemDescription();
            data.expireDate = orderFinancialData.getInventoryItem().getexpireDate();
            data.datetimeManufactured = orderFinancialData.getInventoryItem().getdatetimeManufactured();
            data.accountingQuantityTotal = orderFinancialData.getInventoryItem().getaccountingQuantityTotal();
            data.comments = orderFinancialData.getInventoryItem().getcomments();
            data.containerId = orderFinancialData.getInventoryItem().getcontainerId();
            data.datetimeReceived = orderFinancialData.getInventoryItem().getdatetimeReceived();
            data.lotId = orderFinancialData.getInventoryItem().getlotId();

            result.add(data);
        }

        return result;
    }
*/
 
    @Override
    public void loadParameterSelections() {
        ControllerOptions optionsDate = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(getQbffilter(), optionsDate, "Date Selection", "effectiveDate");

        ControllerOptions optionsParty = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(getQbffilter(), optionsParty, "Select Party");

        InvoiceType invType = null;
        try {
            invType = InvoiceTypeSingleton.getInvoiceType("PURCHASE_INVOICE");
        } catch (Exception ex) {
            Logger.getLogger(NewSalesSummaryReportJasper.class.getName()).log(Level.SEVERE, null, ex);
        }      

       // com.openbravo.pos.reports.params.JParamHelper.addReportSelectionComboPanel(getQbffilter(), "invoiceTypeId", InvoiceTypeSingleton.getValueList(), "Invoice Type Selection", "invoiceTypeId", "Invoice Type:", invType);        

        
        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportInvoiceId(getQbffilter(), options, "Select Invoice");
    }    
}
