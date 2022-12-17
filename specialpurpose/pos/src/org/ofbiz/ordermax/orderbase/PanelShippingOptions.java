/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JGenericListBoxSelectionModel;
import mvc.model.list.OrderTermListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderDeliverySchedule;
import org.ofbiz.ordermax.entity.OrderTerm;
import org.ofbiz.ordermax.entity.TermType;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PanelShippingOptions extends javax.swing.JPanel {

    private OrderDeliverySchedule orderDeliverySchedule = null;

    private JGenericComboBoxSelectionModel<TermType> selectionComboModel = null;
    private JGenericListBoxSelectionModel<OrderTerm> listSelectionModel = null;

    /**
     * Creates new form TelephonePanel
     */
    public PanelShippingOptions() {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);

        selectionComboModel = new JGenericComboBoxSelectionModel<TermType>(TermTypeSingleton.getValueList());
        panelTermType.setLayout(new BorderLayout());
        panelTermType.add(BorderLayout.CENTER, selectionComboModel.jComboBox);

        listSelectionModel = new JGenericListBoxSelectionModel<OrderTerm>(new OrderTermListCellRenderer());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(listSelectionModel.jlistBox);
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(panelGoodIdentificationList, "List");
        ComponentBorder.loweredBevelBorder(panelGoodIdentificationDetail, "Detail");

        listSelectionModel.jlistBox.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            setGoodIdentification(i);
                            break;
                        }
                    }
                }
            }
        });

    }
    OrderTerm orderTerm = null;

    public void setGoodIdentification(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            orderTerm = (OrderTerm) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public OrderTerm getOrderTerm() {
        return orderTerm;
    }

    public void setOrderTerms(List<OrderTerm> orderTerms) {

        listSelectionModel.dataListModel.clear();
        clearDialogFields();
        if (orderTerms != null) {
            listSelectionModel.dataListModel.addAll(orderTerms);
            if (listSelectionModel.dataListModel.getSize() > 0) {
                listSelectionModel.listModelSelection.setSelection(listSelectionModel.dataListModel.getElementAt(0));
            } else {
                clearDialogFields();
                newOrderTerm();
                setDialogField();
            }
        }

    }

    public void clearDialogFields() {
        txtTermValue.setText("");
        txtTermDays.setText("");
        txtDescription.setText("");
    }

    public void setDialogField() {

        if (orderTerm != null) {
            try {
                TermType typeId = TermTypeSingleton.getTermType(orderTerm.gettermTypeId());
                int index = selectionComboModel.comboBoxModel.getSelectedItemIndex(typeId);
                selectionComboModel.jComboBox.setSelectedIndex(index);//.setSelectedItem();
                selectionComboModel.selectionModel.setLeadSelectionIndex(index);//.setSelectedItem(typeId);

            } catch (Exception ex) {
                Logger.getLogger(PanelShippingOptions.class.getName()).log(Level.SEVERE, null, ex);
            }
        

            if (UtilValidate.isNotEmpty(orderTerm.gettermValue())) {
                txtTermValue.setText(orderTerm.gettermValue().toString());
            } else {
                txtTermValue.setText("");
            }
        
            if (UtilValidate.isNotEmpty(orderTerm.gettermDays())) {
                txtTermDays.setText(orderTerm.gettermDays().toString());
            } else {
                txtTermDays.setText("");
            }

            if (UtilValidate.isNotEmpty(orderTerm.getdescription())) {
                txtDescription.setText(orderTerm.getdescription());
            } else {
                txtDescription.setText("");
            }
        }
    }

    public void getDialogField() {
        if (orderTerm != null) {
            try {
                org.ofbiz.ordermax.entity.TermType goodid = (org.ofbiz.ordermax.entity.TermType) selectionComboModel.comboBoxModel.getSelectedItem();
                if (goodid != null) {
                    orderTerm.settermTypeId(goodid.gettermTypeId());
                }
            } catch (Exception ex) {
                Logger.getLogger(PanelShippingOptions.class.getName()).log(Level.SEVERE, null, ex);
            }

     
            if(UtilValidate.isNotEmpty(txtTermValue.getText())){
                orderTerm.settermValue(new BigDecimal(txtTermValue.getText()));
            }        
            else{
                  orderTerm.settermValue(BigDecimal.ZERO);
            }
            
            if(UtilValidate.isNotEmpty(txtTermDays.getText())){
                orderTerm.settermDays(new Long(txtTermDays.getText()));
            }
            else{
                orderTerm.settermDays(new Long("0"));
            }
            
            orderTerm.setdescription(txtDescription.getText());            
        }
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

        panelGoodIdentificationList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelGoodIdentificationDetail = new javax.swing.JPanel();
        btnSaveTelephone = new javax.swing.JButton();
        btnDeleteTelephone = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        panelTermType = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTermValue = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTermDays = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDescription1 = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationList.setLayout(new java.awt.GridBagLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 182;
        gridBagConstraints.ipady = 107;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 12, 13, 0);
        panelGoodIdentificationList.add(jScrollPane1, gridBagConstraints);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 177;
        gridBagConstraints.ipady = 107;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 18, 13, 12);
        panelGoodIdentificationList.add(jScrollPane2, gridBagConstraints);

        jLabel1.setText("Internal Note:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 0, 0);
        panelGoodIdentificationList.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Shipping Notes:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 18, 0, 0);
        panelGoodIdentificationList.add(jLabel2, gridBagConstraints);

        add(panelGoodIdentificationList, java.awt.BorderLayout.CENTER);

        panelGoodIdentificationDetail.setMinimumSize(new java.awt.Dimension(200, 0));
        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(455, 200));

        btnSaveTelephone.setText("Save");
        btnSaveTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTelephoneActionPerformed(evt);
            }
        });

        btnDeleteTelephone.setText("Delete");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Ship Group :");

        panelTermType.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelTermTypeLayout = new javax.swing.GroupLayout(panelTermType);
        panelTermType.setLayout(panelTermTypeLayout);
        panelTermTypeLayout.setHorizontalGroup(
            panelTermTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelTermTypeLayout.setVerticalGroup(
            panelTermTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Shipping estimate amount:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Ship Before Date:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText(" Ship After Date:");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Special Instructions:");

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTermType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTermValue, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTermDays, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addComponent(btnSaveTelephone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteTelephone)))
                .addGap(334, 334, 334))
        );
        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelTermType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTermValue, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTermDays, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveTelephone)
                    .addComponent(btnDeleteTelephone)))
        );

        add(panelGoodIdentificationDetail, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents
    private void newOrderTerm() {
        orderTerm = new OrderTerm();
//        orderTerm.setproductId(productComposite.getProduct().getproductId());
 //       orderTerm.setorderTermTypeId("EAN");
    }

    private void btnSaveTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTelephoneActionPerformed
        getDialogField();
        Debug.logInfo("Save: " + orderTerm.getorderId(), "module");
        if (orderTerm != null
                && (orderTerm.getorderId() != null && !orderTerm.getorderId().isEmpty())) {
            listSelectionModel.dataListModel.add(orderTerm);
            Debug.logInfo("Save valid : " + orderTerm.getorderId(), "module");
//            LoadProductWorker.saveGoodIdentification(listSelectionModel.dataListModel.getList(), GoodIdentificationTypeSingleton.getSingletonSession());
        }
    }//GEN-LAST:event_btnSaveTelephoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDeleteTelephone;
    public javax.swing.JButton btnSaveTelephone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelGoodIdentificationList;
    private javax.swing.JPanel panelTermType;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtDescription1;
    private javax.swing.JTextField txtTermDays;
    private javax.swing.JTextField txtTermValue;
    // End of variables declaration//GEN-END:variables
}
