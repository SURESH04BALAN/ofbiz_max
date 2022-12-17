/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadProductWorker;
import mvc.model.table.ProductContentTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.composite.ProductContentCompositeList;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;

/**
 *
 * @author siranjeev
 */
public class PanelProductContentSet extends javax.swing.JPanel implements ProductContentCompositeInterface {

    String productId;
    public GenericTableModelPanel<ProductContentComposite, ProductContentTableModel> tablePanel = null;
//    protected CopyWebProductImagePanel copyWebProductImagePanel = null;

    /**
     * Creates new form PanelImages
     */
    public PanelProductContentSet() {
        initComponents();
        tablePanel = new GenericTableModelPanel<ProductContentComposite, ProductContentTableModel>(new ProductContentTableModel());
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(BorderLayout.CENTER, tablePanel);
        tablePanel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) tablePanel.selectionModel;//listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            clearDialogFields();
                            productContentComposite = tablePanel.listModel.getElementAt(i);

                            /* 
                             contentMaintainPanel.setContent(value.getContent());
                             dataResourceMaintainPanel.setDataResource(value.getDataResource());
                             try {
                             String dirPath = "C:/ordermax/apache-ofbiz-12.04.04/apache-ofbiz-12.04.04/themes/osafe_theme/webapp";                                
                             String fileName = streamDataResource( XuiContainer.getSession().getDelegator(), value.getDataResource().getdataResourceId());
                             boolean val = BaseHelper.isFileExists(dirPath, fileName); 
                             if(val){
                             tabbedPaneProductContentDetail.setSelectedIndex(1);
                             }
                             else{
                             tabbedPaneProductContentDetail.setSelectedIndex(2);
                             }

                             } catch (Exception ex) {
                             Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
                             }
                             panelImages.setProductComposite(value);
                             panelText.setProductComposite(value);*/
                            break;
                        }
                    }
                }
            }
        });

        txtDataResourceValue.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtDataResourceValue);
        editor.setClickCountToStart(0);
        dataResourceActionTableCellEditor = new DataResourceActionTableCellEditor(editor);
        setupEditOrderTable();

        ResourceDataContentMaintainAction action = new ResourceDataContentMaintainAction(this);
        btnEdit.setAction(action);
    }
    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }
    private ProductContentComposite productContentComposite = null;

    @Override
    public ProductContentComposite getProductContentComposite() {
        return productContentComposite;
    }

    public void setProductContentComposite(ProductContentComposite productContentComposite) {
        this.productContentComposite = productContentComposite;
        setDialogField();
    }

    final public void setupEditOrderTable() {

        int colCount = tablePanel.tableModel.getColumnCount();
        ProductContentTableModel.Columns[] columns = ProductContentTableModel.Columns.values();

        for (int i = 0; i < colCount; i++) {

            TableColumn column = tablePanel.jTable.getColumnModel().getColumn(i);
            ProductContentTableModel.Columns columnValues = columns[i];
            column.setPreferredWidth(columnValues.getColumnWidth());
            ProductContentTableModel.Columns columnVal = columns[i];

            if (i == ProductContentTableModel.Columns.Data.getColumnIndex()) {
                setupProductIdEditColumn(column);
            }
        }

        //order product Id selection click
        //party selection button
        ActionListener productIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof DataResourceActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
/*                    RowColumnActionEvent event = (RowColumnActionEvent) e;

                     //                        Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
                     //                    Map<String, Object> genVal = LookupActionListner.getUserProductSelection();
                     ControllerOptions options = new ControllerOptions();
                     LookupActionListner listner = new LookupActionListner(LookupType.ProductId, options);
                     Map<String, Object> genVal = listner.getUserProductSelection();
                     if (genVal != null) {
                     if (genVal.containsKey("Product")) {


                     JTextField textField = txtDataResourceValue;
                     if (textField != null) {
                     ProductComposite prod = (ProductComposite) genVal.get("Product");
                     textField.setText(prod.getProduct().getproductId());

                     //                                    processProductIdTextFieldChange(textField, event.getRow());
                     event.getTable().setValueAt(prod.getProduct().getproductId(), event.getRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex());
                     event.getTable().changeSelection(event.getRow(), PurchaseOrderTableModel.Columns.ORDER_PROD_INTERNALNAME_INDEX.getColumnIndex(), false, false);
                     }

                     }
                     }
                     */
                    RowColumnActionEvent event = (RowColumnActionEvent) e;
                    ProductContentComposite comp = getProductContentComposite();
                    Debug.logInfo("comp.getDataResource().getdataResourceName(): " + comp.getDataResource().getdataResourceName(), "val");
                    if (comp != null && UtilValidate.isNotEmpty(comp.getDataResource().getdataResourceTypeId()) && "ELECTRONIC_TEXT".equalsIgnoreCase(comp.getDataResource().getdataResourceTypeId())) {
                        JTextArea textArea = new JTextArea(6, 25);
                        textArea.setText(comp.getDataResource().getobjectInfo());
                        textArea.setEditable(true);

                        // wrap a scrollpane around it
                        JScrollPane scrollPane = new JScrollPane(textArea);

                        // display them in a message dialog
                        OrderMaxOptionPane.showMessageDialog(PanelProductContentSet.this, scrollPane);
                        txtDataResourceValue.setText(textArea.getText());
                        comp.getDataResource().setobjectInfo(textArea.getText());
                    } else {
                        File filePath = BaseHelper.getImageFilePath("original");
                        Debug.logInfo(filePath.getPath(), "val");
                        if (UtilValidate.isNotEmpty(comp.getDataResource().getdataResourceName()) && UtilValidate.isNotEmpty(filePath)) {
                            
                            txtDataResourceValue.setText(BaseHelper.CopyDataResourceImageSetFileName(comp.getDataResource().getdataResourceName(), filePath, comp.getDataResource().getdataResourceName(), 0, 0));
                            comp.getDataResource().setobjectInfo(txtDataResourceValue.getText());
                        }
                    }
                    Debug.logInfo("comp.getDataResource().getobjectInfo(): " + comp.getDataResource().getobjectInfo(), "val");
                    LoadProductWorker.saveProductContentComposite(productContentComposite, XuiContainer.getSession());                    
                    event.getTable().setValueAt(comp.getDataResource().getobjectInfo(), event.getRow(), ProductContentTableModel.Columns.Data.getColumnIndex());                    
                    event.getTable().changeSelection(event.getRow(), ProductContentTableModel.Columns.Data.getColumnIndex(), false, false);
                    event.getTable().setValueAt(comp.getDataResource().getobjectInfo(), event.getRow(), ProductContentTableModel.Columns.Data.getColumnIndex());                    
                    event.getTable().changeSelection(event.getRow(), ProductContentTableModel.Columns.Data.getColumnIndex(), false, false);
                    
//        showSelectandFileImage(txtObjectInfo.getText());

                }
            }
        };
        dataResourceActionTableCellEditor.addActionListener(productIdChangeAction);

    }

    private final JTextField txtDataResourceValue = new JTextField();
    private DataResourceActionTableCellEditor dataResourceActionTableCellEditor = null;

    void setupProductIdEditColumn(TableColumn column) {

        //set the cell editor
        column.setCellEditor(dataResourceActionTableCellEditor);
    }

    public void clearDialogFields() {
    }

    public void setDialogField() {
        String fileName = ProductDataTreeLoader.BaseImagePath;
        if (UtilValidate.isNotEmpty(productContentComposite.getDataResource()) && UtilValidate.isNotEmpty(productContentComposite.getDataResource().getobjectInfo())) {
            String filePath = fileName.concat(productContentComposite.getDataResource().getobjectInfo());
            originalImageUrlTextField.setText(filePath);
            showSelectandFileImage(filePath);
//                    ImageIcon icon1 = BaseHelper.getImage(filePath);
        }
        /*      Product product = productContentComposite.getProduct();
         productId = product.getproductId();
         mediumImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getmediumImageUrl()));
         smallImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getsmallImageUrl()));
         originalImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getoriginalImageUrl()));

         largeImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getlargeImageUrl()));
         detailImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getdetailImageUrl()));
         if (UtilValidate.isNotEmpty(detailImageUrlTextField.getText())) {
         detailImageUrlTextField.requestFocus();
         }
         */
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

        panelImage1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        originalImageUrlTextField = new javax.swing.JTextField();
        btnOriginalIcon1 = new javax.swing.JButton();
        btnProductContentSet = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelImage1.setBackground(new java.awt.Color(255, 255, 255));
        panelImage1.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Image"));
        panelImage1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );

        panelImage1.add(panelHeader, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        lblImage.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblImage, java.awt.BorderLayout.CENTER);

        panelImage1.add(jPanel2, java.awt.BorderLayout.EAST);

        add(panelImage1, java.awt.BorderLayout.CENTER);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel16.setText("Original:");

        originalImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                originalImageUrlTextFieldFocusGained(evt);
            }
        });

        btnOriginalIcon1.setText("jButton1");
        btnOriginalIcon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalIcon1ActionPerformed(evt);
            }
        });

        btnProductContentSet.setText("New Product Content Set");
        btnProductContentSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductContentSetActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnProductContentSet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOriginalIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalIcon1)
                    .addComponent(btnProductContentSet)
                    .addComponent(btnEdit))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        add(jPanel4, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void originalImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_originalImageUrlTextFieldFocusGained
        showSelectandFileImage(originalImageUrlTextField.getText());
    }//GEN-LAST:event_originalImageUrlTextFieldFocusGained

    private void btnOriginalIcon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalIcon1ActionPerformed

        originalImageUrlTextField.setText(BaseHelper.selectandSetFileName("original", productId, 0, 0));
    }//GEN-LAST:event_btnOriginalIcon1ActionPerformed

    private void btnProductContentSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductContentSetActionPerformed
        if (UtilValidate.isNotEmpty(productComposite.getProduct())) {
            ProductContentCompositeList pcontentList = LoadProductWorker.newProductContentGroceryList(productComposite.getProduct().getproductId());
            
            for (ProductContentComposite comp : pcontentList.getList()) {
                if (this.productComposite.getProductContentCompositeList().getProductContent(comp.getProductContent().getproductContentTypeId()) == null) {
                    this.productComposite.getProductContentCompositeList().add(comp);
                }
            }
            tablePanel.setListModel(this.productComposite.getProductContentCompositeList());
        }
    }//GEN-LAST:event_btnProductContentSetActionPerformed

    protected void showSelectandFileImage(String field) {

        if (field != null && field.isEmpty() == false) {
            lblImage.setIcon(BaseHelper.getImage(field));
        } else {
            lblImage.setIcon(null);
//            copyWebProductImagePanel.imageLabelSave.setIcon(null);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnOriginalIcon1;
    private javax.swing.JButton btnProductContentSet;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTextField originalImageUrlTextField;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelImage1;
    // End of variables declaration//GEN-END:variables
}
