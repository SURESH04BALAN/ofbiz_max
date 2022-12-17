/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.deliveryschedule;

import org.ofbiz.ordermax.orderbase.*;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javolution.util.FastMap;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderDeliverySchedule;
import org.ofbiz.ordermax.entity.OrderType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.product.UomVolumeDrySingleton;
import org.ofbiz.ordermax.product.UomWeightSingleton;
import org.ofbiz.ordermax.utility.CollapsiblePanel;

/**
 *
 * @author siranjeev
 */
public class EditDeliverySchedulePanel extends javax.swing.JPanel {

    private OrderDeliverySchedule orderDeliverySchedule = null;

    public OrderDeliverySchedule getOrderDeliverySchedule() {
        return orderDeliverySchedule;
    }

    public void setOrderDeliverySchedule(OrderDeliverySchedule orderDeliverySchedule) {
        this.orderDeliverySchedule = orderDeliverySchedule;
    }
//    private OrderTableModel accountCompositeTableModel = new OrderTableModel();
//    private ListAdapterListModel<OrderHeaderAndRoleSummary> accountListModel = new ListAdapterListModel<OrderHeaderAndRoleSummary>();
//    private ListModelSelection<OrderHeaderAndRoleSummary> listModelSelection = new ListModelSelection<OrderHeaderAndRoleSummary>();
//    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    //private ListAdapterListModel<ProductComposite> invoiceCompositeListModel = new ListAdapterListModel<ProductComposite>();
    public JGenericComboBoxSelectionModel<Uom> dryWeightComboModel = null;
    public JGenericComboBoxSelectionModel<Uom> weightMeasureComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboModel = null;
//    public JComboBoxSelectionModel<OrderHeaderAndRoleSummary> productNameComboModel = null;

    boolean showComboKeys = false;
    //PartyPickerEditPanel panelFromPartyId;
    ControllerOptions options = null;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public EditDeliverySchedulePanel(ControllerOptions options) {
        initComponents();
        this.options = options;

        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("ORDER_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        statusItemComboModel = new JGenericComboBoxSelectionModel<StatusItem>(findStatusItemList);
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, statusItemComboModel.jComboBox);
        statusItemComboModel.setSelectedItem(statusItem);

//        panelFromPartyId = new PartyPickerEditPanel(options);
        //       panelCubicMeasurement.setLayout(new BorderLayout());
        //       panelCubicMeasurement.add(BorderLayout.CENTER, panelFromPartyId);
        //role        
        List<Uom> findroleList = UomVolumeDrySingleton.getValueList();
        Uom partyType = new Uom();
        partyType.setdescription("All");
        partyType.setuomId("ANY");
        findroleList.add(0, partyType);

        dryWeightComboModel = new JGenericComboBoxSelectionModel<Uom>(findroleList);
        panelCubicMeasurement.setLayout(new BorderLayout());
        panelCubicMeasurement.add(BorderLayout.CENTER, dryWeightComboModel.jComboBox);
        dryWeightComboModel.setSelectedItem(partyType);

        //order Type
        List<Uom> findOrderTypeList = UomWeightSingleton.getValueList();
        Uom orderType = new Uom();
        orderType.setdescription("All");
        orderType.setuomId("ANY");
        findOrderTypeList.add(0, orderType);

        weightMeasureComboModel = new JGenericComboBoxSelectionModel<Uom>(findOrderTypeList);
        panelEstimatedReadyDate.setLayout(new BorderLayout());
        panelEstimatedReadyDate.add(BorderLayout.CENTER, weightMeasureComboModel.jComboBox);

        /*if (options.contains("orderId")) {
         txtOrderId.setText((String) options.get("orderId"));
         }

         if (options.getOrderType() != null) {
         weightMeasureComboModel.setSelectedItem(options.getOrderType());
         } else {
         weightMeasureComboModel.setSelectedItem(orderType);
         }

         if (options.contains("partyId")) {
         panelFromPartyId.textIdField.setText((String) options.get("partyId"));
         }*/
    }

    public void clearDialogFields() {

    }

    final public void setDialogFields() {
        txtOrderId.setText(orderDeliverySchedule.getorderId());
        if (UtilValidate.isNotEmpty(orderDeliverySchedule.getcartons())) {
            txtCartons.setText(orderDeliverySchedule.getcartons().toString());
        }
        if (UtilValidate.isNotEmpty(orderDeliverySchedule.getskidsPallets())) {
            txtSkidsPallets.setText(orderDeliverySchedule.getskidsPallets().toString());
        }
        if (UtilValidate.isNotEmpty(orderDeliverySchedule.getunitsPieces())) {
            txtUnitsPieces.setText(orderDeliverySchedule.getunitsPieces().toString());
        }
        if (UtilValidate.isNotEmpty(orderDeliverySchedule.getunitsPieces())) {
            txtUnitsPieces.setText(orderDeliverySchedule.getunitsPieces().toString());
        }
        if (UtilValidate.isNotEmpty(orderDeliverySchedule.gettotalCubicSize())) {
            txtTotalCubicSize.setText(orderDeliverySchedule.gettotalCubicSize().toString());
        }

    }

    final public void getDialogFields() {
    }

    public Map<String, Object> getFindOptionList() {
//        Map<String, Object> findOptionList = FastMap.newInstance();
        boolean showAll = true;
        Map<String, Object> svcCtx = FastMap.newInstance();

        /*if (UtilValidate.isNotEmpty(panelFromPartyId.textIdField.getText())) {
         svcCtx.put("partyId", panelFromPartyId.textIdField.getText());
         showAll = false;
         }*/
        if (UtilValidate.isNotEmpty(txtOrderId.getText())) {
            svcCtx.put("orderId", txtOrderId.getText());
            showAll = false;
        }

        if (dryWeightComboModel.jComboBox.getSelectedItem() != null) {
            RoleType partyType = (RoleType) dryWeightComboModel.jComboBox.getSelectedItem();
            if (partyType != null && !"ANY".equals(partyType.getroleTypeId())) {
                svcCtx.put("roleTypeId", partyType.getroleTypeId());
                showAll = false;
            }
        }

        if (weightMeasureComboModel.jComboBox.getSelectedItem() != null) {
            OrderType partyType = (OrderType) weightMeasureComboModel.jComboBox.getSelectedItem();
            if (partyType != null && !"ANY".equals(partyType.getorderTypeId())) {
                svcCtx.put("orderTypeId", partyType.getorderTypeId());
                showAll = false;
            }
        }

        if (statusItemComboModel.jComboBox.getSelectedItem() != null) {
            StatusItem statusType = (StatusItem) statusItemComboModel.jComboBox.getSelectedItem();
            if (statusType != null && !"ANY".equals(statusType.getstatusId())) {
                svcCtx.put("statusId", statusType.getstatusId());
                showAll = false;
            }
        }
        return svcCtx;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelLookupInvoice = new CollapsiblePanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelWeightMeasurement = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtOrderId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        panelEstimatedReadyDate = new javax.swing.JPanel();
        panelCubicMeasurement = new javax.swing.JPanel();
        panelStatusId = new javax.swing.JPanel();
        txtTotalWeight = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCartons = new javax.swing.JTextField();
        txtSkidsPallets = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtUnitsPieces = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTotalCubicSize = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLookupInvoice.setForeground(new java.awt.Color(240, 240, 240));
        java.awt.GridBagLayout panelLookupInvoiceLayout = new java.awt.GridBagLayout();
        panelLookupInvoiceLayout.columnWidths = new int[] {0, 6, 0, 6, 0};
        panelLookupInvoiceLayout.rowHeights = new int[] {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0};
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Cubic Measurement:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel1, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Weight Measurement:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel3, gridBagConstraints);

        panelWeightMeasurement.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelWeightMeasurementLayout = new javax.swing.GroupLayout(panelWeightMeasurement);
        panelWeightMeasurement.setLayout(panelWeightMeasurementLayout);
        panelWeightMeasurementLayout.setHorizontalGroup(
            panelWeightMeasurementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        panelWeightMeasurementLayout.setVerticalGroup(
            panelWeightMeasurementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelWeightMeasurement, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Status ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel4, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Order Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtOrderId, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Estimated Ready Date:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel7, gridBagConstraints);

        panelEstimatedReadyDate.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelEstimatedReadyDateLayout = new javax.swing.GroupLayout(panelEstimatedReadyDate);
        panelEstimatedReadyDate.setLayout(panelEstimatedReadyDateLayout);
        panelEstimatedReadyDateLayout.setHorizontalGroup(
            panelEstimatedReadyDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        panelEstimatedReadyDateLayout.setVerticalGroup(
            panelEstimatedReadyDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelEstimatedReadyDate, gridBagConstraints);

        panelCubicMeasurement.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelCubicMeasurementLayout = new javax.swing.GroupLayout(panelCubicMeasurement);
        panelCubicMeasurement.setLayout(panelCubicMeasurementLayout);
        panelCubicMeasurementLayout.setHorizontalGroup(
            panelCubicMeasurementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        panelCubicMeasurementLayout.setVerticalGroup(
            panelCubicMeasurementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelCubicMeasurement, gridBagConstraints);

        panelStatusId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelStatusIdLayout = new javax.swing.GroupLayout(panelStatusId);
        panelStatusId.setLayout(panelStatusIdLayout);
        panelStatusIdLayout.setHorizontalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        panelStatusIdLayout.setVerticalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(panelStatusId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtTotalWeight, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Total Weight:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel8, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Cartons:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel9, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtCartons, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtSkidsPallets, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Skids Pallets:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel10, gridBagConstraints);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Units Pieces:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtUnitsPieces, gridBagConstraints);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Total Cubic Size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(jLabel12, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panelLookupInvoice.add(txtTotalCubicSize, gridBagConstraints);

        add(panelLookupInvoice, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public void setStatusId(String statusId) {

        try {
            statusItemComboModel.setSelectedItem(StatusSingleton.getStatusItem(statusId));
        } catch (Exception ex) {
            Logger.getLogger(EditDeliverySchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOrderTypeId(String orderTypeId) {
        try {
            weightMeasureComboModel.setSelectedItem(UomWeightSingleton.getUom(orderTypeId));
        } catch (Exception ex) {
            Logger.getLogger(EditDeliverySchedulePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
     public void setPartyId(String partyId) {
     panelFromPartyId.textIdField.setText(partyId);
     }
     */

    public void setOrderId(String orderId) {
        txtOrderId.setText(orderId);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelCubicMeasurement;
    private javax.swing.JPanel panelEstimatedReadyDate;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelStatusId;
    private javax.swing.JPanel panelWeightMeasurement;
    private javax.swing.JTextField txtCartons;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtSkidsPallets;
    private javax.swing.JTextField txtTotalCubicSize;
    private javax.swing.JTextField txtTotalWeight;
    private javax.swing.JTextField txtUnitsPieces;
    // End of variables declaration//GEN-END:variables
}
