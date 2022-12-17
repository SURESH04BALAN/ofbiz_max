package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.entity.ProductCategory;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;
import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.ordermax.base.TreeNode;

public class ProductCategoryPanel extends JPanel implements GenericValuePanelInterfaceOrderMax, org.ofbiz.ordermax.base.BaseMainPanelInterface {

    public static final String module = ProductCategoryPanel.class.getName();
    private ProductCategory productcategory;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JLabel longDescriptionLabel;
    private javax.swing.JTextField longDescriptionTextField;
    public javax.swing.JLabel categoryImageUrlLabel;
    public javax.swing.JTextField categoryImageUrlTextField;
    private javax.swing.JLabel primaryParentCategoryIdLabel;
    public javax.swing.JTextField primaryParentCategoryIdTextField;
    public javax.swing.JLabel linkOneImageUrlLabel;
    public javax.swing.JTextField linkOneImageUrlTextField;
    private javax.swing.JLabel productCategoryIdLabel;
    public javax.swing.JTextField productCategoryIdTextField;
    private javax.swing.JLabel linkTwoImageUrlLabel;
    public javax.swing.JTextField linkTwoImageUrlTextField;
    private javax.swing.JLabel categoryNameLabel;
    private javax.swing.JTextField categoryNameTextField;
    private javax.swing.JLabel detailScreenLabel;
    private javax.swing.JTextField detailScreenTextField;
    private javax.swing.JLabel productCategoryTypeIdLabel;
    public javax.swing.JTextField productCategoryTypeIdTextField;
    private javax.swing.JLabel showInSelectLabel;
    private javax.swing.JTextField showInSelectTextField;
    private JButton button;
    private ComponentBorder cb;
    protected XuiSession session = null;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }
    private boolean isModified = false;

    public ProductCategoryPanel(ProductCategory val, XuiSession session) {

        this.productcategory = val;
        this.session = session;
        initComponents();

    }

    public ProductCategoryPanel(XuiSession session) {
        this.session = session;
        initComponents();

    }

    private void initComponents() {

        descriptionLabel = new javax.swing.JLabel();

        descriptionTextField = new javax.swing.JTextField();

        descriptionTextField.getDocument().addDocumentListener(new TextChangeListner());

        longDescriptionLabel = new javax.swing.JLabel();

        longDescriptionTextField = new javax.swing.JTextField();

        longDescriptionTextField.getDocument().addDocumentListener(new TextChangeListner());

        categoryImageUrlLabel = new javax.swing.JLabel();

        categoryImageUrlTextField = new javax.swing.JTextField();

        categoryImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        primaryParentCategoryIdLabel = new javax.swing.JLabel();

        primaryParentCategoryIdTextField = new javax.swing.JTextField();

        primaryParentCategoryIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(primaryParentCategoryIdTextField);

        button.addActionListener(new LookupActionListner(primaryParentCategoryIdTextField, "primaryParentCategoryIdTextField"));

        linkOneImageUrlLabel = new javax.swing.JLabel();

        linkOneImageUrlTextField = new javax.swing.JTextField();

        linkOneImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        productCategoryIdLabel = new javax.swing.JLabel();

        productCategoryIdTextField = new javax.swing.JTextField();

        productCategoryIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(productCategoryIdTextField);

        button.addActionListener(new LookupActionListner(productCategoryIdTextField, "productCategoryIdTextField"));

        linkTwoImageUrlLabel = new javax.swing.JLabel();

        linkTwoImageUrlTextField = new javax.swing.JTextField();

        linkTwoImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        categoryNameLabel = new javax.swing.JLabel();

        categoryNameTextField = new javax.swing.JTextField();

        categoryNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        detailScreenLabel = new javax.swing.JLabel();

        detailScreenTextField = new javax.swing.JTextField();

        detailScreenTextField.getDocument().addDocumentListener(new TextChangeListner());

        productCategoryTypeIdLabel = new javax.swing.JLabel();

        productCategoryTypeIdTextField = new javax.swing.JTextField();

        productCategoryTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(productCategoryTypeIdTextField);

        button.addActionListener(new LookupActionListner(productCategoryTypeIdTextField, "productCategoryTypeIdTextField"));

        showInSelectLabel = new javax.swing.JLabel();

        showInSelectTextField = new javax.swing.JTextField();

        showInSelectTextField.getDocument().addDocumentListener(new TextChangeListner());

        descriptionLabel.setText("Description:");

        longDescriptionLabel.setText("Long Description:");

        categoryImageUrlLabel.setText("Category Image Url:");

        primaryParentCategoryIdLabel.setText("Primary Parent Category Id:");

        linkOneImageUrlLabel.setText("Link One Image Url:");

        productCategoryIdLabel.setText("Product Category Id:");

        linkTwoImageUrlLabel.setText("Link Two Image Url:");

        categoryNameLabel.setText("Category Name:");

        detailScreenLabel.setText("Detail Screen:");

        productCategoryTypeIdLabel.setText("Product Category Type Id:");

        showInSelectLabel.setText("Show In Select:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(productCategoryTypeIdLabel)
                .add(primaryParentCategoryIdLabel)
                .add(productCategoryIdLabel)
                .add(detailScreenLabel)
                .add(categoryNameLabel)
                .add(linkOneImageUrlLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(productCategoryTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(primaryParentCategoryIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productCategoryIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(detailScreenTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(categoryNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(linkOneImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(categoryImageUrlLabel)
                .add(descriptionLabel)
                .add(longDescriptionLabel)
                .add(linkTwoImageUrlLabel)
                .add(showInSelectLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(categoryImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(descriptionTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(longDescriptionTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(linkTwoImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(showInSelectTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(productCategoryTypeIdLabel)
                .add(productCategoryTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(categoryImageUrlLabel)
                .add(categoryImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(primaryParentCategoryIdLabel)
                .add(primaryParentCategoryIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(descriptionLabel)
                .add(descriptionTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(productCategoryIdLabel)
                .add(productCategoryIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(longDescriptionLabel)
                .add(longDescriptionTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(detailScreenLabel)
                .add(detailScreenTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(linkTwoImageUrlLabel)
                .add(linkTwoImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(categoryNameLabel)
                .add(categoryNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(showInSelectLabel)
                .add(showInSelectTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(linkOneImageUrlLabel)
                .add(linkOneImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws  java.text.ParseException {

        productCategoryIdTextField.setText(OrderMaxUtility.getValidString(productcategory.getProductCategoryId()));

        linkTwoImageUrlTextField.setText(OrderMaxUtility.getValidString(productcategory.getLinkTwoImageUrl()));

        longDescriptionTextField.setText(OrderMaxUtility.getValidString(productcategory.getLongDescription()));

        detailScreenTextField.setText(OrderMaxUtility.getValidString(productcategory.getDetailScreen()));

        showInSelectTextField.setText(OrderMaxUtility.getValidString(productcategory.getShowInSelect()));

        descriptionTextField.setText(OrderMaxUtility.getValidString(productcategory.getDescription()));

        primaryParentCategoryIdTextField.setText(OrderMaxUtility.getValidString(productcategory.getPrimaryParentCategoryId()));

        categoryNameTextField.setText(OrderMaxUtility.getValidString(productcategory.getCategoryName()));

        categoryImageUrlTextField.setText(OrderMaxUtility.getValidString(productcategory.getCategoryImageUrl()));

        productCategoryTypeIdTextField.setText(OrderMaxUtility.getValidString(productcategory.getProductCategoryTypeId()));

        linkOneImageUrlTextField.setText(OrderMaxUtility.getValidString(productcategory.getLinkOneImageUrl()));

    }

    public void getUIFields() throws  java.text.ParseException {

        productcategory.setProductCategoryId(OrderMaxUtility.getValidString(productCategoryIdTextField.getText()));

        productcategory.setLinkTwoImageUrl(OrderMaxUtility.getValidString(linkTwoImageUrlTextField.getText()));

        productcategory.setLongDescription(OrderMaxUtility.getValidString(longDescriptionTextField.getText()));

        productcategory.setDetailScreen(OrderMaxUtility.getValidString(detailScreenTextField.getText()));

        productcategory.setShowInSelect(OrderMaxUtility.getValidString(showInSelectTextField.getText()));

        productcategory.setDescription(OrderMaxUtility.getValidString(descriptionTextField.getText()));

        productcategory.setPrimaryParentCategoryId(OrderMaxUtility.getValidString(primaryParentCategoryIdTextField.getText()));

        productcategory.setCategoryName(OrderMaxUtility.getValidString(categoryNameTextField.getText()));

        productcategory.setCategoryImageUrl(OrderMaxUtility.getValidString(categoryImageUrlTextField.getText()));

        productcategory.setProductCategoryTypeId(OrderMaxUtility.getValidString(productCategoryTypeIdTextField.getText()));

        productcategory.setLinkOneImageUrl(OrderMaxUtility.getValidString(linkOneImageUrlTextField.getText()));

    }

    public static void createAndShowUI(ProductCategory val) {

        try {

            ProductCategoryPanel panel = new ProductCategoryPanel(val, null);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        }  catch (java.text.ParseException ex) {

            Debug.logError(ex, module);

        }

    }

    @Override
    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        ProductCategory newObj = null;

        if (baseVal != null) {

            newObj = new ProductCategory(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new ProductCategory();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return productcategory;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof ProductCategory) {

            ProductCategory newObj = (ProductCategory) uiObject;

            productcategory = newObj;

            try {

                productcategory.setGenericValue();

            } catch (Exception ex) {
//Debug.logError (ex, module);
            }

        }

    }

    public JPanel getContainerPanel() {
        return this;
    }

    public void newItem() {

        try {
            //TelecomNumber telNumber = new TelecomNumber();
            GenericValueObjectInterface uiObject = this.createUIObject(null);
            changeUIObject(uiObject);
            setUIFields();
        }  catch (java.text.ParseException ex) {
        }
    }

    @Override
    public void saveItem() throws Exception {

        GenericValueObjectInterface prodCat = getUIObject();
        GenericValue detailValue = prodCat.getGenericValueObj();
        if (prodCat.isGenericValueSet() == false) {
            detailValue = prodCat.createNewGenericValueObj(session.getDelegator());
                        Debug.logInfo("Save 1 new generic value", module);
        }
        else
        {
            Debug.logInfo("Save 1 generic value exists", module);
        }
        getUIFields();
        prodCat.getGenericValue();
        
        try {
//            GenericValue prodCat = delegator.findByPrimaryKey("ProdCatalog", UtilMisc.toMap("prodCatalogId", prodCatalogId));
//            if (prodCat == null) {
//                prodCat = createNewGenericValueObj(delegator);
//            }
            //store person
            session.getDelegator().createOrStore(prodCat.getGenericValueObj(), true);
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
//            Debug.logError(e, module);
            e.printStackTrace();
//            result = false;
        }
//        prodCat.createOrStoreGenericValue(session.getDelegator());
        Debug.logInfo("Save 3", module);
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addChangeListener(PropertyChangeListener newListener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setItem(Object val) {
        try {
            TreeNode node = (TreeNode) val;
            String productCategoryId = node._id;
//            isNew = false;

            GenericValue productCategoryEntity = node.loadDetails(productCategoryId, session.getDelegator());
//            GenericValue prodCat = (GenericValue) val;
            GenericValueObjectInterface uiObj = this.createUIObject(productCategoryEntity);
            this.changeUIObject(uiObj);
            try {
                this.setUIFields();
                isModified = false;
            } catch (java.text.ParseException ex) {
                Debug.logError(ex, module);
            }

        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductCategoryPanel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void refreshScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadItem() {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
//calling function copy and paste

/*

 try {



 Delegator delegator = XuiContainer.getSession().getDelegator();

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("ProductCategory", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),ProductCategory.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.ProductCategoryPanel( ); 

 Object uiObj = panel.createUIObject(val);

 panel.changeUIObject(uiObj);

 panel.setUIFields();

 dlg.setChildPanelInterface(panel);

 OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());

 dlg.setLocationRelativeTo(null);

 dlg.pack();

 dlg.setVisible(true);

 } catch (ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 } catch (java.text.ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 }

 */
