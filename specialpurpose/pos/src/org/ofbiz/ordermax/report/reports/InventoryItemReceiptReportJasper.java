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
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import mvc.controller.LoadReceiveInventoryCompositeWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.OrderPickerEditPanel;
import org.ofbiz.ordermax.composite.ReceiveInventoryComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class InventoryItemReceiptReportJasper extends EntityJasperReport {


//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = InventoryItemReceiptReportJasper.class.getName();

    @Override
    public String identifier() {
        return "Inventory Item Receipt Report";

    }

    public class InventoryData {

        public java.sql.Timestamp getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(java.sql.Timestamp expireDate) {
            this.expireDate = expireDate;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String desc) {
            this.description = desc;
        }

        public java.sql.Timestamp getDatetimeReceived() {
            return datetimeReceived;
        }

        public void setDatetimeReceived(java.sql.Timestamp datetimeReceived) {
            this.datetimeReceived = datetimeReceived;
        }

        public java.lang.String getContainerId() {
            return containerId;
        }

        public void setContainerId(java.lang.String containerId) {
            this.containerId = containerId;
        }

        public java.lang.String getComments() {
            return comments;
        }

        public void setComments(java.lang.String comments) {
            this.comments = comments;
        }

        public java.math.BigDecimal getAccountingQuantityTotal() {
            return accountingQuantityTotal;
        }

        public void setAccountingQuantityTotal(java.math.BigDecimal accountingQuantityTotal) {
            this.accountingQuantityTotal = accountingQuantityTotal;
        }

        private java.sql.Timestamp datetimeManufactured;
        private java.math.BigDecimal accountingQuantityTotal;
        private java.lang.String comments;
        private java.lang.String containerId;
        private String description;
        private java.sql.Timestamp datetimeReceived;
        private String partyId;
        private String productId;
        private java.sql.Timestamp expireDate;
        private java.lang.String lotId;

        public java.lang.String getLotId() {
            return lotId;
        }

        public void setLotId(java.lang.String lotId) {
            this.lotId = lotId;
        }
        private java.lang.String binNumber;

        public java.lang.String getBinNumber() {
            return binNumber;
        }

        public void setBinNumber(java.lang.String binNumber) {
            this.binNumber = binNumber;
        }

        public java.sql.Timestamp getDatetimeManufactured() {
            return datetimeManufactured;
        }

        public void setDatetimeManufactured(java.sql.Timestamp datetimeManufactured) {
            this.datetimeManufactured = datetimeManufactured;
        }

    }

    @Override
    public String getReportFileName() {
        return "InventoryItemReceiptReportJasper.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "InventoryItemReceiptReportJasper.jasper";
    }
    ShipmentReceiptComposite orderFinancialData = null;

    @Override
    public JPanel runReport() {

        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Inventory Item Receipt Report");
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
//        String subReport = getReportPath().concat("InventoryItem.jasper");
//        addCompiliedFileName(reportArgs, subReport);//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jasper");
//        addEntity(reportArgs, "OrderHeaderAndItems");
//        addOrderBy(reportArgs, "orderDate ASC");

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

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Collection result = new ArrayList<InventoryData>();
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
