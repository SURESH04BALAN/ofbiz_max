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
public class SupplierPaymentSummaryAgeingReportJasper extends SupplierAgeingReport {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = SupplierPaymentSummaryAgeingReportJasper.class.getName();

    @Override
    public String identifier() {
        return "Supplier Payment Summary Ageing";
    }

    @Override
    public String getReportFileName() {
        return "SupplierPaymentSummaryAgeingReport.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "SupplierPaymentSummaryAgeingReport.jasper";
    }
    ShipmentReceiptComposite orderFinancialData = null;

    @Override
    public JPanel runReport() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
//        List<EntityCondition> condList = getWhereClauseCond();

        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        

        param.put("ReportTitle", "Inventory Item Report");
        param.put("DataFile", "Entity: OrderHeaderAndItems");
        getSelectionPanelValues(param);
        java.sql.Timestamp date = (java.sql.Timestamp) param.get(currentDate);        
        org.ofbiz.ordermax.Dates.Month curMonth = new org.ofbiz.ordermax.Dates.Month(date);

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
        reportArgs.put("invoiceTypeId", "PURCHASE_INVOICE");
        
//        EntityJasperReport.addConditionList(reportArgs, condList);
        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        addCompiliedFileName(reportArgs, getCompiledReportPathAndFile());
        addParametersNameMap(reportArgs, param);

        return super.runReport(reportArgs);
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
    ReportDateSelectionPanel panelFilter = null;

}
