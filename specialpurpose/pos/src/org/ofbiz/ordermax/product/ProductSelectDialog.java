/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.util.Collections.list;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JListBoxSelectionModel;
import mvc.model.list.ProductListCellRenderer;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ProductSelectDialog extends javax.swing.JPanel {

    public JListBoxSelectionModel<Product> listSelectionModel = null;

    /**
     * Creates new form TelephonePanel
     */
    public ProductSelectDialog() {
        initComponents();
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);

        listSelectionModel = new JListBoxSelectionModel<Product>(new ProductListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(listSelectionModel.jlistBox);
        panelGoodIdentificationList.setLayout(new BorderLayout());
        panelGoodIdentificationList.add(BorderLayout.CENTER, scrollPane);

        listSelectionModel.jlistBox.setFixedCellHeight(50);
        listSelectionModel.jlistBox.setFixedCellWidth(200);
        //Font font = new Font("Verdana", Font.BOLD, 32);
//        listSelectionModel.jlistBox.setFont(font);

        ComponentBorder.loweredBevelBorder(panelGoodIdentificationList, "Found Multiple Items");
        listSelectionModel.jlistBox.setFont(new java.awt.Font("Verdana", 0, 24));
        scrollPane.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        listSelectionModel.jlistBox.setForeground(Color.BLUE);

//        ComponentBorder.loweredBevelBorder(panelGoodIdentificationDetail, "Detail");
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
    Product product = null;

    public void setGoodIdentification(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            product = (Product) listSelectionModel.dataListModel.getElementAt(index);
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
//        listSelectionModel.dataListModel.clear();
        listSelectionModel.listModelSelection.setSelection(listSelectionModel.dataListModel.getElementAt(0));

    }

    public void setProductIndex(int selIndex) {
        if (selIndex > listSelectionModel.dataListModel.getSize()) {
//        listSelectionModel.dataListModel.clear();
            listSelectionModel.selectionModel.setLeadSelectionIndex(selIndex);
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

        panelGoodIdentificationDetail = new javax.swing.JPanel();
        btnSelect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panelGoodIdentificationList = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(400, 371));
        setLayout(new java.awt.BorderLayout());

        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(205, 200));

        btnSelect.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        btnSelect.setText("Add");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setToolTipText("");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGoodIdentificationDetailLayout = new javax.swing.GroupLayout(panelGoodIdentificationDetail);
        panelGoodIdentificationDetail.setLayout(panelGoodIdentificationDetailLayout);
        panelGoodIdentificationDetailLayout.setHorizontalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(btnSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panelGoodIdentificationDetailLayout.setVerticalGroup(
            panelGoodIdentificationDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGoodIdentificationDetailLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelGoodIdentificationDetailLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancel, btnSelect});

        add(panelGoodIdentificationDetail, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout panelGoodIdentificationListLayout = new javax.swing.GroupLayout(panelGoodIdentificationList);
        panelGoodIdentificationList.setLayout(panelGoodIdentificationListLayout);
        panelGoodIdentificationListLayout.setHorizontalGroup(
            panelGoodIdentificationListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        panelGoodIdentificationListLayout.setVerticalGroup(
            panelGoodIdentificationListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        add(panelGoodIdentificationList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed

    }//GEN-LAST:event_btnSelectActionPerformed
    private void newGoodIdentification() {

    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed

    }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSelect;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelGoodIdentificationList;
    // End of variables declaration//GEN-END:variables
}
