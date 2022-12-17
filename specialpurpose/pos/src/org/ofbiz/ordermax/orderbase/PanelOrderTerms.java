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
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderDeliverySchedule;
import org.ofbiz.ordermax.entity.OrderTerm;
import org.ofbiz.ordermax.entity.TermType;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PanelOrderTerms extends javax.swing.JPanel {

    private OrderDeliverySchedule orderDeliverySchedule = null;

    private JGenericComboBoxSelectionModel<TermType> selectionComboModel = null;
    private JGenericListBoxSelectionModel<OrderTerm> listSelectionModel = null;

    /**
     * Creates new form TelephonePanel
     */
    public PanelOrderTerms() {
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
        panelGoodIdentificationList.setLayout(new BorderLayout());
        panelGoodIdentificationList.add(BorderLayout.CENTER, scrollPane);
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
    Order order = null;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if(order!=null && order.getOrderTermList()!=null){
            setOrderTerms(order.getOrderTermList().getList());
        }
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

        if (orderTerm != null && UtilValidate.isNotEmpty(orderTerm.gettermTypeId())) {
            try {
                TermType typeId = TermTypeSingleton.getTermType(orderTerm.gettermTypeId());
                int index = selectionComboModel.comboBoxModel.getSelectedItemIndex(typeId);
                selectionComboModel.jComboBox.setSelectedIndex(index);//.setSelectedItem();
                selectionComboModel.selectionModel.setLeadSelectionIndex(index);//.setSelectedItem(typeId);

            } catch (Exception ex) {
                Logger.getLogger(PanelOrderTerms.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PanelOrderTerms.class.getName()).log(Level.SEVERE, null, ex);
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

        panelGoodIdentificationList = new javax.swing.JPanel();
        panelGoodIdentificationDetail = new javax.swing.JPanel();
        btnNewTelephone = new javax.swing.JButton();
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

        setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationList.setLayout(new java.awt.BorderLayout());
        add(panelGoodIdentificationList, java.awt.BorderLayout.CENTER);

        panelGoodIdentificationDetail.setMinimumSize(new java.awt.Dimension(200, 0));
        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(455, 200));

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

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Term Type \t:");

        panelTermType.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelTermTypeLayout = new javax.swing.GroupLayout(panelTermType);
        panelTermType.setLayout(panelTermTypeLayout);
        panelTermTypeLayout.setHorizontalGroup(
            panelTermTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelTermTypeLayout.setVerticalGroup(
            panelTermTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Term Value \t:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Term Days:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Description:");

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addComponent(btnNewTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addComponent(panelTermType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(34, 34, 34))
                    .addComponent(txtTermValue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTermDays, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );
        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelTermType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTermValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTermDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTelephone)
                    .addComponent(btnDeleteTelephone)
                    .addComponent(btnSaveTelephone))
                .addContainerGap())
        );

        add(panelGoodIdentificationDetail, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTelephoneActionPerformed
        clearDialogFields();
        newOrderTerm();
        setDialogField();
    }//GEN-LAST:event_btnNewTelephoneActionPerformed
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
    public javax.swing.JButton btnNewTelephone;
    public javax.swing.JButton btnSaveTelephone;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelGoodIdentificationList;
    private javax.swing.JPanel panelTermType;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtTermDays;
    private javax.swing.JTextField txtTermValue;
    // End of variables declaration//GEN-END:variables
}
