/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import mvc.controller.LoadOrderWorker;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.DataBeanList;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addCompiliedFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSourceFileName;
import org.ofbiz.ordermax.reportdesigner_old.OrderHeaderReport;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class PickingSlipReportJasper extends EntityJasperReport {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = PickingSlipReportJasper.class.getName();

    @Override
    public String identifier() {
        return "Picking Slip Report";

    }

    @Override
    public String getReportFileName() {
        return "PickingSlipReport.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "PickingSlipReport.jasper";
    }    
    
    @Override
    public String getSubReportCompiledFileName() {
        return "PickingSlipItemReport.jasper";
    }

    @Override
    public String getSubReportFileName() {
        return "PickingSlipItemReport.jrxml";
    }
    
    @Override
    public JPanel runReport() {
        
        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Order Pickling Slip");
        param.put("DataFile", "CustomBeanFactory.java - Bean Collection");
        System.out.println(" runReport: " + "runReport");
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


//        String dir = "C:\\AuthLog\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\";
//        String masterReportFileName = getReportPathAndFile();// dir + "PickingSlipReport.jasper";
//        String subReportFileName = getReportPath().concat("PickingSlipItemReport.jrxml");
//        String destFileName = dir + "PurchaseInvoice.JRprint";

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        JasperPrint jasperPrint = null;
        try {
            /* Compile the master and sub report */

//            JasperViewer.viewReport(jasperPrint);
//        } catch (JRException e) {

//            Debug.logError(e, module);
//        }
//        System.out.println("Done filling!!! ...");

//        return super.getReportPanel(jasperPrint);
    }
    
    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
        System.out.println(" getBeanCollection: " + "getBeanCollection");
//        DataBeanList dataBeanList = new DataBeanList();
        Collection result = new ArrayList<OrderHeaderReport>();        
        Set<Order> orderHeaderSet = new HashSet<Order>();
//        List<OrderHeaderReport> dataList = new ArrayList<OrderHeaderReport>();

        if (collectionMap.containsKey("orderHeader")) {
            orderHeaderSet = (Set<Order>) collectionMap.get("orderHeader");
        }
        else{
            if(collectionMap.containsKey("orderId")){
                Order order = LoadOrderWorker.loadOrder((String)collectionMap.get("orderId"), XuiContainer.getSession());
                orderHeaderSet.add(order);
            }
        }

//        invoiceIds.add("MACI194");
        for (Order order : orderHeaderSet) {
//            public OrderHeaderReport getOrderBean(XuiSession session, String orderId, java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd) {
            OrderHeaderReport headerReport = DataBeanList.getOrderBean(session, order, startDate, endDate);
            System.out.println(" orderId ID: " + order.getOrderId());
            headerReport.setOrderPartyDetails( session, order);
            result.add(headerReport);
        }
/*
        Collection result = new ArrayList<InventoryItemReceiptReportJasper.InventoryData>();
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
            InventoryItemReceiptReportJasper.InventoryData data = new InventoryItemReceiptReportJasper.InventoryData();
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
*/
        return result;
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
//        JPanel panel = AddDateSelection("orderDate", controllerOptions, "Date Selection:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
/*
        JPanel panel = AddPartyIdSelection(controllerOptions, "Party Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddProductIdSelection(controllerOptions, "Product Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = createReportSelectionComboPanel("orderTypeId", OrderTypeSingleton.getValueList(), "Order Type");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
*/
        JPanel panel = AddOrderIdSelection(filterList,controllerOptions, "Order Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
       
        return panelFilter;
    }
}
