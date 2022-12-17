/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ShipementCartItemModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderDeliverySchedule;
import org.ofbiz.ordermax.entity.OrderTerm;
import org.ofbiz.ordermax.entity.TermType;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PanelShippingItem extends javax.swing.JPanel {

    private OrderDeliverySchedule orderDeliverySchedule = null;
    final private ListAdapterListModel<ShoppingCartItem> shoppingCartItemListModel = new ListAdapterListModel<ShoppingCartItem>();
    private JGenericComboBoxSelectionModel<TermType> selectionComboModel = null;
    private static String[] COLUMN_NAMES = {
                "Product Code",
                "Product Name",
                "Quantity"
            };
    
    private ShipementCartItemModel shipementCartItemModel = null;

    /**
     * Creates new form TelephonePanel
     */
    public PanelShippingItem() {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);
        shipementCartItemModel = new ShipementCartItemModel(Arrays.asList(COLUMN_NAMES));
        selectionComboModel = new JGenericComboBoxSelectionModel<TermType>(TermTypeSingleton.getValueList()         );
//        panelTermType.setLayout(new BorderLayout());
//        panelTermType.add(BorderLayout.CENTER, selectionComboModel.jComboBox);
        tableShippingItem.setModel(shipementCartItemModel);
        tableShippingItem.setFillsViewportHeight(true);

        ComponentBorder.loweredBevelBorder(panelGoodIdentificationList, "List");
        ComponentBorder.loweredBevelBorder(panelGoodIdentificationDetail, "Detail");

    }
    Order order = null;

    public void setGoodIdentification(int index) {

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        List<String> colList = new ArrayList<String>();
        colList.addAll(Arrays.asList(COLUMN_NAMES));
//        colList.add("Product Code");
//        colList.add("Product Name");
//        colList.add("Quantity");
        //colList.add("Ship Grp 1");
        List<ShoppingCart.CartShipInfo> shipGroups = order.getShipGroups();

        int i = 0;
        for (ShoppingCart.CartShipInfo shipCart : shipGroups) {
            String index = "";
            i++;
            if (UtilValidate.isNotEmpty(shipCart.getShipGroupSeqId())) {
                index = shipCart.getShipGroupSeqId();
            } else {
                index = Integer.toString(i);
            }
            String str = "Ship Group Nbr " + index;
            colList.add(str);
//            panelScrollPane.setLayout(new BorderLayout());
//            panelScrollPane.add(BorderLayout.CENTER, panel);
        }
        
        shipementCartItemModel = new ShipementCartItemModel(colList);
        shipementCartItemModel.setOrder(order);
        shoppingCartItemListModel.clear();
        shoppingCartItemListModel.addAll(order.items());
        shipementCartItemModel.setListModel(shoppingCartItemListModel);
        tableShippingItem.setModel(shipementCartItemModel);
        tableShippingItem.setFillsViewportHeight(true);        
        revalidate();        
    }

    public void clearDialogFields() {
        /*txtTermValue.setText("");
         txtTermDays.setText("");
         txtDescription.setText("");*/
    }

    public void setDialogField() {
    }

    public void getDialogField() {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGoodIdentificationList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShippingItem = new javax.swing.JTable();
        panelGoodIdentificationDetail = new javax.swing.JPanel();
        btnNewTelephone = new javax.swing.JButton();
        btnSaveTelephone = new javax.swing.JButton();
        btnDeleteTelephone = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationList.setPreferredSize(new java.awt.Dimension(0, 100));
        panelGoodIdentificationList.setLayout(new java.awt.BorderLayout());

        tableShippingItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableShippingItem);

        panelGoodIdentificationList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panelGoodIdentificationList, java.awt.BorderLayout.CENTER);

        panelGoodIdentificationDetail.setMinimumSize(new java.awt.Dimension(200, 0));
        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(455, 50));

        btnNewTelephone.setText("New");
        btnNewTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTelephoneActionPerformed(evt);
            }
        });

        btnSaveTelephone.setText("Save");
        btnSaveTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTelephoneActionPerformed(evt);
            }
        });

        btnDeleteTelephone.setText("Delete");

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnNewTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTelephone)
                    .addComponent(btnDeleteTelephone)
                    .addComponent(btnSaveTelephone))
                .addContainerGap())
        );

        add(panelGoodIdentificationDetail, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTelephoneActionPerformed

    }//GEN-LAST:event_btnSaveTelephoneActionPerformed

    private void btnNewTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTelephoneActionPerformed
        clearDialogFields();
        setDialogField();
    }//GEN-LAST:event_btnNewTelephoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDeleteTelephone;
    public javax.swing.JButton btnNewTelephone;
    public javax.swing.JButton btnSaveTelephone;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelGoodIdentificationList;
    private javax.swing.JTable tableShippingItem;
    // End of variables declaration//GEN-END:variables
}