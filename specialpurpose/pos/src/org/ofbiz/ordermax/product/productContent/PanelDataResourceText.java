/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.DataResource;

/**
 *
 * @author siranjeev
 */
public class PanelDataResourceText extends javax.swing.JPanel {

    String productId;
//    protected CopyWebProductImagePanel copyWebProductImagePanel = null;

    /**
     * Creates new form PanelImages
     */
    public PanelDataResourceText() {
        initComponents();
    }
  /*
    private ProductContentComposite productContentComposite = null;

    public ProductContentComposite getProductComposite() {
        return productContentComposite;
    }

    public void setProductComposite(ProductContentComposite productContentComposite) {
        this.productContentComposite = productContentComposite;
        setDialogField();
    }
*/
    DataResource dataResource = null;

    public DataResource getDataResource() {
        return dataResource;
    }

    public void setDataResource(DataResource dataResource) {
        this.dataResource = dataResource;
         setDialogField();
    }
    
    public void clearDialogFields() {
    }

    public void setDialogField() {

        if (UtilValidate.isNotEmpty(dataResource)) {
            try {
                String str = streamDataResource(XuiContainer.getSession().getDelegator(), dataResource.getdataResourceId());
                editSelect.setText(str);
//            String filePath = fileName.concat(productContentComposite.getDataResource().getobjectInfo());
                //          originalImageUrlTextField.setText(filePath);
                //          showSelectandFileImage(filePath);
//                    ImageIcon icon1 = BaseHelper.getImage(filePath);
            } catch (Exception ex) {
                Logger.getLogger(PanelDataResourceText.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getDialogField() {

    }

    public static String streamDataResource(Delegator delegator, String dataResourceId) throws Exception{
        try {
            GenericValue dataResource = delegator.findOne("DataResource", UtilMisc.toMap("dataResourceId", dataResourceId), true);
            if (dataResource == null) {
                throw new Exception("Error in streamDataResource: DataResource with ID [" + dataResourceId + "] was not found.");
            }
            String dataResourceTypeId = dataResource.getString("dataResourceTypeId");
            if (UtilValidate.isEmpty(dataResourceTypeId)) {
                dataResourceTypeId = "SHORT_TEXT";
            }
            String mimeTypeId = dataResource.getString("mimeTypeId");
            if (UtilValidate.isEmpty(mimeTypeId)) {
                mimeTypeId = "text/html";
            }

            if (dataResourceTypeId.equals("SHORT_TEXT")) {
                String text = dataResource.getString("objectInfo");
                return text;
                //os.write(text.getBytes());
            } else if (dataResourceTypeId.equals("ELECTRONIC_TEXT")) {
                GenericValue electronicText = delegator.findOne("ElectronicText", UtilMisc.toMap("dataResourceId", dataResourceId), true);
                if (electronicText != null) {
                    String text = electronicText.getString("textData");
                    if (text != null) {
//                        os.write(text.getBytes());
                        return text;
                    }
                }
            } else if (dataResourceTypeId.equals("IMAGE_OBJECT")) {
                /*byte[] imageBytes = acquireImage(delegator, dataResource);
                if (imageBytes != null) {
                    os.write(imageBytes);
                }*/
            } else if (dataResourceTypeId.equals("LINK")) {
                /*String text = dataResource.getString("objectInfo");
                os.write(text.getBytes());*/
            } else if (dataResourceTypeId.equals("URL_RESOURCE")) {
                /*URL url = new URL(dataResource.getString("objectInfo"));
                if (url.getHost() == null) { // is relative
                    String prefix = buildRequestPrefix(delegator, locale, webSiteId, https);
                    String sep = "";
                    //String s = "";
                    if (url.toString().indexOf("/") != 0 && prefix.lastIndexOf("/") != (prefix.length() - 1)) {
                        sep = "/";
                    }
                    String s2 = prefix + sep + url.toString();
                    url = new URL(s2);
                }
                InputStream in = url.openStream();
                UtilIO.copy(in, true, os, false);*/
            } else if (dataResourceTypeId.indexOf("_FILE") >= 0) {
                /*String objectInfo = dataResource.getString("objectInfo");
                File inputFile = getContentFile(dataResourceTypeId, objectInfo, rootDir);
                //long fileSize = inputFile.length();
                FileInputStream fis = new FileInputStream(inputFile);
                UtilIO.copy(fis, true, os, false);
                */
            } else {
                throw new Exception("The dataResourceTypeId [" + dataResourceTypeId + "] is not supported in streamDataResource");
            }
        } catch (GenericEntityException e) {
            throw new Exception("Error in streamDataResource", e);
        }
        return "";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        editSelect = new javax.swing.JEditorPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        originalImageUrlTextField = new javax.swing.JTextField();
        btnOriginalIcon = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelImage.setBackground(new java.awt.Color(255, 255, 255));
        panelImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Image"));
        panelImage.setLayout(new java.awt.BorderLayout());

        editSelect.setEditable(false);
        editSelect.setContentType("text/html"); // NOI18N
        editSelect.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        editSelect.setText("<html>\n  <head>\n\n  </head>\n  <body>\n  </body>\n</html>\n\n");
        jScrollPane3.setViewportView(editSelect);

        panelImage.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        add(panelImage, java.awt.BorderLayout.CENTER);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Images"));
        jPanel3.setPreferredSize(new java.awt.Dimension(641, 52));

        jLabel15.setText("Original:");

        originalImageUrlTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                originalImageUrlTextFieldFocusGained(evt);
            }
        });

        btnOriginalIcon.setText("jButton1");
        btnOriginalIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalIconActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(16, 16, 16)
                .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOriginalIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(originalImageUrlTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOriginalIcon))
                .addGap(0, 2, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void originalImageUrlTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_originalImageUrlTextFieldFocusGained

    }//GEN-LAST:event_originalImageUrlTextFieldFocusGained

    private void btnOriginalIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalIconActionPerformed

        originalImageUrlTextField.setText(BaseHelper.selectandSetFileName("original", productId, 0, 0));

    }//GEN-LAST:event_btnOriginalIconActionPerformed
   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOriginalIcon;
    private javax.swing.JEditorPane editSelect;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField originalImageUrlTextField;
    private javax.swing.JPanel panelImage;
    // End of variables declaration//GEN-END:variables
}
