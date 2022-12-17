/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.LoadProductWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JGenericListBoxSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PanelGoodIdentification extends javax.swing.JPanel {

    private JGenericComboBoxSelectionModel<GoodIdentificationType> selectionComboModel = null;
    private JGenericListBoxSelectionModel<GoodIdentification> listSelectionModel = null;

    /**
     * Creates new form TelephonePanel
     */
    public PanelGoodIdentification() {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);

        selectionComboModel = new JGenericComboBoxSelectionModel<GoodIdentificationType>(GoodIdentificationTypeSingleton.getValueList());
        panelItType.setLayout(new BorderLayout());
        panelItType.add(BorderLayout.CENTER, selectionComboModel.jComboBox);

        listSelectionModel = new JGenericListBoxSelectionModel<GoodIdentification>(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);

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
    GoodIdentification goodIdentification = null;

    public void setGoodIdentification(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            goodIdentification = (GoodIdentification) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        listSelectionModel.dataListModel.clear();
        clearDialogFields();
        if (productComposite != null) {
            listSelectionModel.dataListModel.addAll(productComposite.getGoodIdentificationList().getList());
            if (listSelectionModel.dataListModel.getSize() > 0) {
                listSelectionModel.listModelSelection.setSelection(listSelectionModel.dataListModel.getElementAt(0));
            } else {
                clearDialogFields();
                newGoodIdentification();
                setDialogField();
            }
        }

    }
private boolean isEnable = true;

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
        txtProductId.setEnabled(isEnable);
        textIdValue.setEnabled(isEnable);
        btnNewTelephone.setEnabled(isEnable);
        btnSaveTelephone.setEnabled(isEnable);
        btnDeleteTelephone.setEnabled(isEnable);
        panelGoodIdentificationList.setEnabled(isEnable);
        panelItType.setEnabled(isEnable);
        selectionComboModel.jComboBox.setEnabled(isEnable);
    }
    
    public void clearDialogFields() {
        textIdValue.setText("");
        txtProductId.setText("");
    }

    public void setDialogField() {
        txtProductId.setText(productComposite.getProduct().getproductId());
        if (goodIdentification != null) {

            try {
                GoodIdentificationType typeId = GoodIdentificationTypeSingleton.getGoodIdentificationType(goodIdentification.getgoodIdentificationTypeId());
                int index = selectionComboModel.comboBoxModel.getSelectedItemIndex(typeId);
                selectionComboModel.jComboBox.setSelectedIndex(index);//.setSelectedItem();
//                        public int getSelectedItemIndex(Object anItem){
                selectionComboModel.selectionModel.setLeadSelectionIndex(index);//.setSelectedItem(typeId);

            } catch (Exception ex) {
                Logger.getLogger(PanelGoodIdentification.class.getName()).log(Level.SEVERE, null, ex);
            }
            textIdValue.setText(goodIdentification.getidValue());
        } else {
            textIdValue.setText("");
        }

    }

    public void getDialogField() {
        if (goodIdentification != null) {
            try {
                org.ofbiz.ordermax.entity.GoodIdentificationType goodid = (org.ofbiz.ordermax.entity.GoodIdentificationType) selectionComboModel.comboBoxModel.getSelectedItem();
                if (goodid != null) {

                    goodIdentification.setgoodIdentificationTypeId(goodid.getgoodIdentificationTypeId());
                }
            } catch (Exception ex) {
                Logger.getLogger(PanelGoodIdentification.class.getName()).log(Level.SEVERE, null, ex);
            }

            goodIdentification.setidValue(textIdValue.getText());
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
        jPanel2 = new javax.swing.JPanel();
        panelGoodIdentificationDetail = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnNewTelephone = new javax.swing.JButton();
        textIdValue = new javax.swing.JTextField();
        btnSaveTelephone = new javax.swing.JButton();
        btnDeleteTelephone = new javax.swing.JButton();
        jLabel = new javax.swing.JLabel();
        panelItType = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationList.setLayout(new java.awt.BorderLayout());
        add(panelGoodIdentificationList, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(455, 200));

        jLabel2.setText("Id Value:");

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
        btnDeleteTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTelephoneActionPerformed(evt);
            }
        });

        jLabel.setText("Id Type:");

        javax.swing.GroupLayout panelItTypeLayout = new javax.swing.GroupLayout(panelItType);
        panelItType.setLayout(panelItTypeLayout);
        panelItTypeLayout.setHorizontalGroup(
            panelItTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelItTypeLayout.setVerticalGroup(
            panelItTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel3.setText("Product Id:");

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(textIdValue, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelGoodIdentificationDetailLayout.createSequentialGroup()
                        .addComponent(btnNewTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSaveTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtProductId, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelItType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelItType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewTelephone)
                    .addComponent(btnDeleteTelephone)
                    .addComponent(btnSaveTelephone))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jPanel2.add(panelGoodIdentificationDetail, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTelephoneActionPerformed
        clearDialogFields();
        newGoodIdentification();
        setDialogField();
    }//GEN-LAST:event_btnNewTelephoneActionPerformed
    private void newGoodIdentification() {
        goodIdentification = new GoodIdentification();
        goodIdentification.setproductId(productComposite.getProduct().getproductId());
        goodIdentification.setgoodIdentificationTypeId("EAN");

    }

    private void btnSaveTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTelephoneActionPerformed
        getDialogField();
        Debug.logInfo("Save: " + goodIdentification.getproductId(), "module");
        if (goodIdentification != null
                && (goodIdentification.getproductId() != null && !goodIdentification.getproductId().isEmpty())) {
            if (!PosProductHelper.isDuplicateGoodIdentification(goodIdentification.getproductId(),
                    goodIdentification.getidValue(), goodIdentification.getgoodIdentificationTypeId(), GoodIdentificationTypeSingleton.getSingletonSession().getDelegator())) {
                int index = listSelectionModel.dataListModel.indexOf(goodIdentification);
                if (index >= listSelectionModel.dataListModel.getSize() || index < 0) {
                    listSelectionModel.dataListModel.add(goodIdentification);
                }

                Debug.logInfo("Save valid : " + goodIdentification.getproductId(), "module");
                LoadProductWorker.saveGoodIdentification(listSelectionModel.dataListModel.getList(), GoodIdentificationTypeSingleton.getSingletonSession());
                this.repaint();

            } else {
                List<GenericValue> gvList = PosProductHelper.getProductsFromIdentificationType(
                        goodIdentification.getidValue(), goodIdentification.getgoodIdentificationTypeId(), GoodIdentificationTypeSingleton.getSingletonSession().getDelegator());

                StringBuilder orderToStringBuilder = new StringBuilder();
                orderToStringBuilder.append("Duplicate code, clash with following prduct(s): \n");
                for (GenericValue gv : gvList) {
                    Debug.logInfo("Duplicate code: " + gv.getString("productId"), "module");
                    if (goodIdentification.getproductId().equals(gv.getString("productId")) == false) {
                        GenericValue product = PosProductHelper.getProduct(goodIdentification.getproductId(), GoodIdentificationTypeSingleton.getSingletonSession().getDelegator());
                        orderToStringBuilder.append(product.getString("internalName") + "[" + goodIdentification.getproductId() + "]");
                        orderToStringBuilder.append("\n");
                    }
                }

                if (GoodIdentificationTypeSingleton.getSingletonSession().getDesktopPane() != null) {
                    JOptionPane.showMessageDialog(null,
                            orderToStringBuilder.toString(), "Dupplicate Code", JOptionPane.YES_NO_OPTION);
                } else {

                    OrderMaxOptionPane.showMessageDialog(GoodIdentificationTypeSingleton.getSingletonSession().getDesktopPane(),
                            orderToStringBuilder.toString(), "Dupplicate Code", JOptionPane.YES_NO_OPTION);

                }
            }
        }
    }//GEN-LAST:event_btnSaveTelephoneActionPerformed

    private void btnDeleteTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTelephoneActionPerformed
        int index = listSelectionModel.dataListModel.indexOf(goodIdentification);
        if (index < listSelectionModel.dataListModel.getSize() && index > -1) {
            LoadProductWorker.removeGoodIdentification(goodIdentification, GoodIdentificationTypeSingleton.getSingletonSession());
            listSelectionModel.dataListModel.remove(goodIdentification);
        }

        Debug.logInfo("Save valid : " + goodIdentification.getproductId(), "module");

        this.repaint();
    }//GEN-LAST:event_btnDeleteTelephoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDeleteTelephone;
    public javax.swing.JButton btnNewTelephone;
    public javax.swing.JButton btnSaveTelephone;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelGoodIdentificationList;
    private javax.swing.JPanel panelItType;
    private javax.swing.JTextField textIdValue;
    private javax.swing.JTextField txtProductId;
    // End of variables declaration//GEN-END:variables
}
