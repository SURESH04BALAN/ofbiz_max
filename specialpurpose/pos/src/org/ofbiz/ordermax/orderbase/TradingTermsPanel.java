/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import org.ofbiz.ordermax.orderbase.deliveryschedule.*;
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
public class TradingTermsPanel extends javax.swing.JPanel {

    
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
    PartyPickerEditPanel panelFromPartyId;
    ControllerOptions options = null;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public TradingTermsPanel(ControllerOptions options) {
        initComponents();
        this.options = options;

        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("ORDER_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);


        //role        
        List<Uom> findroleList = UomVolumeDrySingleton.getValueList();
        Uom partyType = new Uom();
        partyType.setdescription("All");
        partyType.setuomId("ANY");
        findroleList.add(0, partyType);



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
    }

    final public void getDialogFields() {
    }

    public Map<String, Object> getFindOptionList() {
//        Map<String, Object> findOptionList = FastMap.newInstance();
        boolean showAll = true;
        Map<String, Object> svcCtx = FastMap.newInstance();

        if (UtilValidate.isNotEmpty(panelFromPartyId.textIdField.getText())) {
            svcCtx.put("partyId", panelFromPartyId.textIdField.getText());
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
        jLabel7 = new javax.swing.JLabel();
        panelEstimatedReadyDate = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCartons = new javax.swing.JTextField();
        txtSkidsPallets = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtUnitsPieces = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelLookupInvoice.setForeground(new java.awt.Color(240, 240, 240));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Term Type \t:");

        panelEstimatedReadyDate.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelEstimatedReadyDateLayout = new javax.swing.GroupLayout(panelEstimatedReadyDate);
        panelEstimatedReadyDate.setLayout(panelEstimatedReadyDateLayout);
        panelEstimatedReadyDateLayout.setHorizontalGroup(
            panelEstimatedReadyDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelEstimatedReadyDateLayout.setVerticalGroup(
            panelEstimatedReadyDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Term Value \t:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Term Days:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Description:");

        jButton1.setText("Add");

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelEstimatedReadyDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCartons, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSkidsPallets, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnitsPieces, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jButton1)))
                .addGap(524, 524, 524))
        );
        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelEstimatedReadyDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCartons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSkidsPallets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnitsPieces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jButton1))
        );

        add(panelLookupInvoice, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public void setStatusId(String statusId) {

        try {
            statusItemComboModel.setSelectedItem(StatusSingleton.getStatusItem(statusId));
        } catch (Exception ex) {
            Logger.getLogger(TradingTermsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOrderTypeId(String orderTypeId) {
        try {
            weightMeasureComboModel.setSelectedItem(UomWeightSingleton.getUom(orderTypeId));
        } catch (Exception ex) {
            Logger.getLogger(TradingTermsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setPartyId(String partyId) {
        panelFromPartyId.textIdField.setText(partyId);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelEstimatedReadyDate;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JTextField txtCartons;
    private javax.swing.JTextField txtSkidsPallets;
    private javax.swing.JTextField txtUnitsPieces;
    // End of variables declaration//GEN-END:variables
}
