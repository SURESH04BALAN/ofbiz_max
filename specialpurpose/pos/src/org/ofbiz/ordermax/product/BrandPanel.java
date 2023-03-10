/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.Component;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastList;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.generic.GenericValueMapComboBox;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;
import org.ofbiz.ordermax.screens.InteractiveTableModel;
import org.ofbiz.ordermax.screens.KeyTableModel;
import org.ofbiz.ordermax.screens.ProductDetailEditDialog;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import java.io.File;

/**
 *
 * @author siranjeev
 */
public class BrandPanel extends javax.swing.JPanel implements BaseMainPanelInterface, GenericValuePanelInterfaceOrderMax {

    public static final String[] brandColumnNames = {"Brand Id", "Brand Name", ""};
    private static int[] width = {250, 250, 2};
    protected Map<String, String> departmentValMap = new TreeMap<String, String>();
    protected List<String> departmentListBidingCombo = FastList.newInstance();
    protected KeyTableModel brandTableModel = null;
    protected boolean isModified = false;
    protected boolean isNew = true;
    protected Delegator delegator = null;
    protected GenericValueMapComboBox genericValueMapComboBox;

    /**
     * Creates new form BrandPanel
     */
    public BrandPanel(XuiSession session) {
        delegator = session.getDelegator();
        initComponents();
        editBrandName.getDocument().addDocumentListener(new TextChangeListner());
        genericValueMapComboBox = new GenericValueMapComboBox(comboDepartmentName, null);
    }

    public void setDepartmentMap(Map depValMap) {
        departmentValMap = depValMap;
        /*        
         DefaultComboBoxModel comboDepartmentModel = new DefaultComboBoxModel();
         for (Map.Entry<String, String> entryDept : departmentValMap.entrySet()) {

         comboDepartmentModel.addElement(entryDept.getValue());
         departmentListBidingCombo.add(entryDept.getKey());
         }
         comboDepartmentName.setModel(comboDepartmentModel);
         * */
        genericValueMapComboBox = new GenericValueMapComboBox(comboDepartmentName, depValMap);
        genericValueMapComboBox.loadCombo();
    }

    public void reloadBrandItemDataModel(List<Key> brandList) {

        brandTableModel = new KeyTableModel(brandColumnNames);
        brandTableModel.addTableModelListener(new InteractiveTableModelListener(tableBrand));
        tableBrand.setModel(brandTableModel);
        tableBrand.setSurrendersFocusOnKeystroke(true);
        //tableDepartmentTable.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));        
        setColumnWidth(width);
        TableColumn hidden = tableBrand.getColumnModel().getColumn(KeyTableModel.HIDDEN_INDEX);
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);
        hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX, brandTableModel, tableBrand));
        tableBrand.setPreferredSize(new java.awt.Dimension(500, (brandList.size()) * tableBrand.getRowHeight()));
        brandTableModel.addRows(brandList);
        if (!brandTableModel.hasEmptyRow()) {
            brandTableModel.addEmptyRow();
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBrand = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        editBrandName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        editBrandId = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        comboDepartmentName = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtsmallImageUrl = new javax.swing.JTextField();
        btnSmallIcon = new javax.swing.JButton();
        btnMediumIcon = new javax.swing.JButton();
        txtmediumImageUrl = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnLargeIcon = new javax.swing.JButton();
        txtlargeImageUrl = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnDetailIcon = new javax.swing.JButton();
        txtdetailImageUrl = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtoriginalImageUrl = new javax.swing.JTextField();
        btnOriginalIcon = new javax.swing.JButton();
        panelImage = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        cbScaleImage = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setPreferredSize(new java.awt.Dimension(500, 400));
        setLayout(new java.awt.GridLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        tableBrand.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableBrand);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel11.setText("Brand Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanel1.add(jLabel11, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 186;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanel1.add(editBrandName, gridBagConstraints);

        jLabel14.setText("Brand Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanel1.add(jLabel14, gridBagConstraints);

        editBrandId.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 186;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanel1.add(editBrandId, gridBagConstraints);

        jLabel15.setText("Department Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanel1.add(jLabel15, gridBagConstraints);

        comboDepartmentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboDepartmentNameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 191;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanel1.add(comboDepartmentName, gridBagConstraints);

        jLabel31.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jLabel31, gridBagConstraints);

        jTabbedPane1.addTab("Brand Details", jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Images"));

        jLabel12.setText("Small:");

        txtsmallImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsmallImageUrlFocusGained(evt);
            }
        });

        btnSmallIcon.setText("jButton1");
        btnSmallIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmallIconActionPerformed(evt);
            }
        });

        btnMediumIcon.setText("jButton1");
        btnMediumIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMediumIconActionPerformed(evt);
            }
        });

        txtmediumImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtmediumImageUrlFocusGained(evt);
            }
        });

        jLabel13.setText("Medium:");

        btnLargeIcon.setText("jButton1");
        btnLargeIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLargeIconActionPerformed(evt);
            }
        });

        txtlargeImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtlargeImageUrlFocusGained(evt);
            }
        });

        jLabel16.setText("Large:");

        btnDetailIcon.setText("jButton1");
        btnDetailIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailIconActionPerformed(evt);
            }
        });

        txtdetailImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtdetailImageUrlFocusGained(evt);
            }
        });

        jLabel17.setText("Detail:");

        jLabel18.setText("Original:");

        txtoriginalImageUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtoriginalImageUrlFocusGained(evt);
            }
        });

        btnOriginalIcon.setText("jButton1");
        btnOriginalIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalIconActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImageLayout = new javax.swing.GroupLayout(panelImage);
        panelImage.setLayout(panelImageLayout);
        panelImageLayout.setHorizontalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelImageLayout.setVerticalGroup(
            panelImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
                .addContainerGap())
        );

        cbScaleImage.setSelected(true);
        cbScaleImage.setText("Scale Image");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsmallImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmediumImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtlargeImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdetailImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtoriginalImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSmallIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnLargeIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnMediumIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnOriginalIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDetailIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbScaleImage))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(panelImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtsmallImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmallIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtmediumImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMediumIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtlargeImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLargeIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtdetailImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetailIcon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtoriginalImageUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalIcon)
                    .addComponent(cbScaleImage))
                .addGap(18, 18, 18)
                .addComponent(panelImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1071, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel4);

        jTabbedPane1.addTab("Display Properties", jPanel2);

        add(jTabbedPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void comboDepartmentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboDepartmentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDepartmentNameActionPerformed

    private void txtsmallImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsmallImageUrlFocusGained
        showSelectandFileImage(txtsmallImageUrl.getText());
    }//GEN-LAST:event_txtsmallImageUrlFocusGained

    private void btnSmallIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmallIconActionPerformed
        txtsmallImageUrl.setText(BaseHelper.selectandSetFileName("small", editBrandId.getText(), 60, 60));
    }//GEN-LAST:event_btnSmallIconActionPerformed

    private void btnMediumIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMediumIconActionPerformed
        txtmediumImageUrl.setText(BaseHelper.selectandSetFileName("small-60", editBrandId.getText(), 48, 48));
    }//GEN-LAST:event_btnMediumIconActionPerformed

    private void txtmediumImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtmediumImageUrlFocusGained
        showSelectandFileImage(txtmediumImageUrl.getText());
    }//GEN-LAST:event_txtmediumImageUrlFocusGained

    private void btnLargeIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLargeIconActionPerformed
        txtlargeImageUrl.setText(BaseHelper.selectandSetFileName("large", editBrandId.getText(), 240, 240));
    }//GEN-LAST:event_btnLargeIconActionPerformed

    private void txtlargeImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtlargeImageUrlFocusGained
        showSelectandFileImage(txtlargeImageUrl.getText());
    }//GEN-LAST:event_txtlargeImageUrlFocusGained

    private void btnDetailIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailIconActionPerformed
        txtdetailImageUrl.setText(BaseHelper.selectandSetFileName("large-200", editBrandId.getText(), 200, 200));        // TODO add your handling code here:
    }//GEN-LAST:event_btnDetailIconActionPerformed

    private void txtdetailImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtdetailImageUrlFocusGained
        showSelectandFileImage(txtdetailImageUrl.getText());
    }//GEN-LAST:event_txtdetailImageUrlFocusGained

    private void txtoriginalImageUrlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtoriginalImageUrlFocusGained
        showSelectandFileImage(txtoriginalImageUrl.getText());
    }//GEN-LAST:event_txtoriginalImageUrlFocusGained

    private void btnOriginalIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalIconActionPerformed

        if (cbScaleImage.isSelected()) {
            File filePath = BaseHelper.getImageFilePath("original");

            txtoriginalImageUrl.setText(BaseHelper.CopyImageSetFileName("original", filePath, editBrandId.getText(), 0, 0));
            txtmediumImageUrl.setText(BaseHelper.CopyImageSetFileName("small-60", filePath, editBrandId.getText(), 60, 60));
            txtsmallImageUrl.setText(BaseHelper.CopyImageSetFileName("small", filePath, editBrandId.getText(), 48, 48));
            txtlargeImageUrl.setText(BaseHelper.CopyImageSetFileName("large", filePath, editBrandId.getText(), 240, 240));
            txtdetailImageUrl.setText(BaseHelper.CopyImageSetFileName("large-200", filePath, editBrandId.getText(), 200, 200));        // TODO add your handling code here:
            showSelectandFileImage(txtoriginalImageUrl.getText());
            //            btnSmallIconActionPerformed(evt);
            //            btnMediumIconActionPerformed(evt);
            //            btnLargeIconActionPerformed(evt);
            //            btnDetailIconActionPerformed(evt);

        } else {
            txtoriginalImageUrl.setText(BaseHelper.selectandSetFileName("original", editBrandId.getText(), 0, 0));
        }
    }//GEN-LAST:event_btnOriginalIconActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetailIcon;
    private javax.swing.JButton btnLargeIcon;
    private javax.swing.JButton btnMediumIcon;
    private javax.swing.JButton btnOriginalIcon;
    private javax.swing.JButton btnSmallIcon;
    private javax.swing.JCheckBox cbScaleImage;
    private javax.swing.JComboBox comboDepartmentName;
    private javax.swing.JTextField editBrandId;
    private javax.swing.JTextField editBrandName;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JPanel panelImage;
    private javax.swing.JTable tableBrand;
    private javax.swing.JTextField txtdetailImageUrl;
    private javax.swing.JTextField txtlargeImageUrl;
    private javax.swing.JTextField txtmediumImageUrl;
    private javax.swing.JTextField txtoriginalImageUrl;
    private javax.swing.JTextField txtsmallImageUrl;
    // End of variables declaration//GEN-END:variables

    public void clearBrandDialogFields() {
        editBrandId.setText("");
        editBrandName.setText("");
    }

    public void refreshScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void newItem() {
        clearBrandDialogFields();
        isModified = false;
        isNew = true;
    }

    public void saveItem() {
        try {
            if (editBrandId.getText().isEmpty()) {
                String depId = getDepartmentCode();
                String depName = getDepartmentName();
                String brandName = editBrandName.getText();

                String brandId = depId + "_" + brandName.replaceAll("\\s", "");

                if (brandId != null && brandId.length() > 20) {
                    brandId = brandId.substring(0, 19);
                }
                editBrandId.setText(brandId);
                GenericValue brandCategory = PosProductHelper.getProductCategory(brandId, delegator);
                if (brandCategory == null) {
                    delegator.create(PosProductHelper.createProductCategory(brandId, depId, "CATALOG_CATEGORY", brandName,
                            txtsmallImageUrl.getText(),
                             txtmediumImageUrl.getText(),
                             txtlargeImageUrl.getText(),
                             delegator));
                }

                GenericValue rollUp = PosProductHelper.getProductCategoryRollup(brandId, depId, delegator);
                if (rollUp == null) {
                    rollUp = PosProductHelper.createProductCategoryRollup(brandId, depId, delegator);
                    delegator.create(rollUp);
                    //Debug.logInfo("create [createProductCategoryRollup] " + rollUp, module);
                }
            } else {
                try {
                    GenericValue brandCategory = PosProductHelper.getProductCategory(editBrandId.getText(), delegator);
                    if (brandCategory != null) {
                        brandCategory.set("categoryName", editBrandName.getText());
                        brandCategory.set("categoryImageUrl", txtsmallImageUrl.getText());
                        brandCategory.set("linkOneImageUrl", txtmediumImageUrl.getText());
                        brandCategory.set("linkTwoImageUrl", txtlargeImageUrl.getText());                        
                        
                        delegator.store(brandCategory);
                    }

                } catch (GenericEntityException ex) {
                    Logger.getLogger(ProductDetailEditDialog.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            isNew = false;
            isModified = false;
//            loadProductTree();
            //}

            //now add the node to tree



        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductDetailEditDialog.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    String getDepartmentCode() {

        String departmentCode = null;
        if (comboDepartmentName.getSelectedIndex() > -1) {
            departmentCode = genericValueMapComboBox.getSelectedItemId();//departmentListBidingCombo.get(comboDepartmentName.getSelectedIndex());
        }
        return departmentCode;
    }

    String getDepartmentName() {

        String departmentName = null;
        if (comboDepartmentName.getSelectedItem() != null) {
            departmentName = comboDepartmentName.getSelectedItem().toString();
        }
        return departmentName;
    }

    public void setItem(Object val) {

        try {
            TreeNode node = (TreeNode) val;
            String brandId = node._id;
            isNew = false;

            GenericValue brandEntity = node.loadDetails(brandId, delegator);
            editBrandId.setText(brandEntity.getString("productCategoryId"));
            editBrandName.setText(brandEntity.getString("categoryName"));
            txtsmallImageUrl.setText(brandEntity.getString("categoryImageUrl"));
            txtmediumImageUrl.setText(brandEntity.getString("linkOneImageUrl"));
            txtlargeImageUrl.setText(brandEntity.getString("linkTwoImageUrl"));


            TreeNode deptName = OrderMaxUtility.GetRecusevilyNodeType(node, node.tp, DepartmentTreeNode.DepartmentNodeName);
            if (deptName != null) {
                //Debug.logInfo("tree clicked dep -- " + deptName.getNodeName() + " node id " + deptName._id + " - " + deptName._name, module);
                comboDepartmentName.getModel().setSelectedItem(deptName._name);
                int index = brandTableModel.getRowNumber(brandEntity.getString("productCategoryId"));
                if (index > 0) {
                    tableBrand.setRowSelectionInterval(index, index);
                    tableBrand.scrollRectToVisible(new Rectangle(tableBrand.getCellRect(index, 0, true)));
                }
//                editDepartmentId.setText(deptName._id);
                //               editDepartmentName.setText(deptName._name);

//                comboProductDepName.getModel().setSelectedItem(deptName._name);
            } else {
                //Debug.logInfo("tree clicked dep null", module);
            }

//            comboPostalChanged(BRAND_TAB_INDEX);
            isModified = false;
        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductDetailEditDialog.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


//        throw new UnsupportedOperationException("Not supported yet.");
/*                 try {

         String depId = getDepartmentCode();
         String depName = getDepartmentName();
         String brandName = editBrandName.getText();

         String brandId = depId + "_" + brandName.replaceAll("\\s", "");

         if (brandId != null && brandId.length() > 20) {
         brandId = brandId.substring(0, 19);
         }
         editBrandId.setText(brandId);
         BrandTreeNode brandNode = new BrandTreeNode(brandId, brandName, false);

         GenericValue brandCategory = PosProductHelper.getProductCategory(brandId, delegator);
         if (brandCategory == null) {
         delegator.create(PosProductHelper.createProductCategory(brandId, depId, "CATALOG_CATEGORY", brandName, delegator));
         }

         GenericValue rollUp = PosProductHelper.getProductCategoryRollup(brandId, depId, delegator);
         if (rollUp == null) {
         rollUp = PosProductHelper.createProductCategoryRollup(brandId, depId, delegator);
         delegator.create(rollUp);
         //Debug.logInfo("create [createProductCategoryRollup] " + rollUp, module);
         }

         loadProductTree();
         //}

         //now add the node to tree



         } catch (GenericEntityException ex) {
         Logger.getLogger(ProductDetailEditDialog.class
         .getName()).log(Level.SEVERE, null, ex);
         }
         */
    }

    public void changeUIObject(GenericValueObjectInterface uiObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getUIFields() throws  java.text.ParseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setUIFields() throws  java.text.ParseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JPanel getContainerPanel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public GenericValueObjectInterface getUIObject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isModified() {
        return isModified;
    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }

    public void setParentItem(Object val) {
        if (val != null && val instanceof TreeNode) {
            TreeNode deptNode = (TreeNode) val;
            genericValueMapComboBox.setSelectedItemId(deptNode._id);
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class InteractiveRenderer extends DefaultTableCellRenderer {

        protected int interactiveColumn;
        protected KeyTableModel model = null;
        protected JTable jtable = null;

        public InteractiveRenderer(int interactiveColumn, KeyTableModel tabModel, JTable table) {
            this.interactiveColumn = interactiveColumn;
            model = tabModel;
            jtable = table;
        }

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                if ((model.getRowCount() - 1) == row
                        && !model.hasEmptyRow()) {
                    model.addEmptyRow();
                }

                this.highlightLastRowInner(row);
                //        rowChanged(row);
            }

            return c;
        }

        public void highlightLastRowInner(int row) {
            int lastrow = model.getRowCount();
            if (row == lastrow - 1) {
                jtable.setRowSelectionInterval(lastrow - 1, lastrow - 1);
            } else {
                jtable.setRowSelectionInterval(row + 1, row + 1);
            }

            jtable.setColumnSelectionInterval(0, 0);
        }
    }

    public class InteractiveTableModelListener implements TableModelListener {

        protected JTable jtable = null;

        public InteractiveTableModelListener(JTable table) {
            jtable = table;
        }

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                jtable.setColumnSelectionInterval(column + 1, column + 1);
                jtable.setRowSelectionInterval(row, row);
            }
        }
    }

    public void setColumnWidth(int[] columnWidth) {
        TableColumn column = null;
        for (int i = 0; i < columnWidth.length; i++) {
            column = tableBrand.getColumnModel().getColumn(i);
//            if (i == 2) {
            column.setPreferredWidth(columnWidth[i]); //third column is bigger
//            } else {
//                column.setPreferredWidth(50);
//            }
        }
    }

    private class TextChangeListner implements DocumentListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
        }

        public void changedUpdate(DocumentEvent e) {
            warn(e);
        }

        public void removeUpdate(DocumentEvent e) {
            warn(e);
        }

        public void insertUpdate(DocumentEvent e) {
            warn(e);
        }

        void warn(DocumentEvent e) {
            isModified = true;
        }
    }

    protected void showSelectandFileImage(String field) {

        if (field != null && field.isEmpty() == false) {
            lblImage.setIcon(/*new ImageIcon(OrderMaxUtility.getImage(field.getText()))*/BaseHelper.getImage(field));
        }
    }

    protected void showLabelFileImage(String field) {

        if (field != null && field.isEmpty() == false) {
            jLabel31.setIcon(/*new ImageIcon(OrderMaxUtility.getImage(field.getText()))*/BaseHelper.getImage(field));
        }
    }
}
