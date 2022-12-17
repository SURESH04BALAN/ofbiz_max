package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.entity.ProdCatalog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class ProdCatalogPanel extends JPanel implements GenericValuePanelInterfaceOrderMax, org.ofbiz.ordermax.base.BaseMainPanelInterface {

    public static final String module = ProdCatalogPanel.class.getName();
    private ProdCatalog prodcatalog;
    private javax.swing.JLabel createdTxStampLabel;
    private javax.swing.JTextField createdTxStampTextField;
    private javax.swing.JLabel lastUpdatedStampLabel;
    private javax.swing.JTextField lastUpdatedStampTextField;
    private javax.swing.JLabel prodCatalogIdLabel;
    private javax.swing.JTextField prodCatalogIdTextField;
    private javax.swing.JLabel templatePathPrefixLabel;
    private javax.swing.JTextField templatePathPrefixTextField;
    private javax.swing.JLabel purchaseAllowPermReqdLabel;
    private javax.swing.JTextField purchaseAllowPermReqdTextField;
    private javax.swing.JLabel lastUpdatedTxStampLabel;
    private javax.swing.JTextField lastUpdatedTxStampTextField;
    private javax.swing.JLabel createdStampLabel;
    private javax.swing.JTextField createdStampTextField;
    private javax.swing.JLabel headerLogoLabel;
    private javax.swing.JTextField headerLogoTextField;
    private javax.swing.JLabel styleSheetLabel;
    private javax.swing.JTextField styleSheetTextField;
    private javax.swing.JLabel catalogNameLabel;
    private javax.swing.JTextField catalogNameTextField;
    private javax.swing.JLabel useQuickAddLabel;
    private javax.swing.JTextField useQuickAddTextField;
    private javax.swing.JLabel viewAllowPermReqdLabel;
    private javax.swing.JTextField viewAllowPermReqdTextField;
    private javax.swing.JLabel contentPathPrefixLabel;
    private javax.swing.JTextField contentPathPrefixTextField;
    private JButton button;
    private ComponentBorder cb;
    protected XuiSession session = null;

    public ProdCatalogPanel(ProdCatalog val, XuiSession session) {
        this.prodcatalog = val;
        this.session = session;
        initComponents();
    }

    public ProdCatalogPanel(XuiSession session) {
        initComponents();
        this.session = session;
    }

    private void initComponents() {
        createdTxStampLabel = new javax.swing.JLabel();
        createdTxStampTextField = new javax.swing.JTextField();
        lastUpdatedStampLabel = new javax.swing.JLabel();
        lastUpdatedStampTextField = new javax.swing.JTextField();
        prodCatalogIdLabel = new javax.swing.JLabel();
        prodCatalogIdTextField = new javax.swing.JTextField();
        button = new JButton("...");
        cb = new ComponentBorder(button);
        cb.install(prodCatalogIdTextField);
        button.addActionListener(new LookupActionListner(prodCatalogIdTextField, "prodCatalogIdTextField"));
        templatePathPrefixLabel = new javax.swing.JLabel();
        templatePathPrefixTextField = new javax.swing.JTextField();
        purchaseAllowPermReqdLabel = new javax.swing.JLabel();
        purchaseAllowPermReqdTextField = new javax.swing.JTextField();
        lastUpdatedTxStampLabel = new javax.swing.JLabel();
        lastUpdatedTxStampTextField = new javax.swing.JTextField();
        createdStampLabel = new javax.swing.JLabel();
        createdStampTextField = new javax.swing.JTextField();
        headerLogoLabel = new javax.swing.JLabel();
        headerLogoTextField = new javax.swing.JTextField();
        styleSheetLabel = new javax.swing.JLabel();
        styleSheetTextField = new javax.swing.JTextField();
        catalogNameLabel = new javax.swing.JLabel();
        catalogNameTextField = new javax.swing.JTextField();
        useQuickAddLabel = new javax.swing.JLabel();
        useQuickAddTextField = new javax.swing.JTextField();
        viewAllowPermReqdLabel = new javax.swing.JLabel();
        viewAllowPermReqdTextField = new javax.swing.JTextField();
        contentPathPrefixLabel = new javax.swing.JLabel();
        contentPathPrefixTextField = new javax.swing.JTextField();
        createdTxStampLabel.setText("Created Tx Stamp:");
        lastUpdatedStampLabel.setText("Last Updated Stamp:");
        prodCatalogIdLabel.setText("Prod Catalog Id:");
        templatePathPrefixLabel.setText("Template Path Prefix:");
        purchaseAllowPermReqdLabel.setText("Purchase Allow Perm Reqd:");
        lastUpdatedTxStampLabel.setText("Last Updated Tx Stamp:");
        createdStampLabel.setText("Created Stamp:");
        headerLogoLabel.setText("Header Logo:");
        styleSheetLabel.setText("Style Sheet:");
        catalogNameLabel.setText("Catalog Name:");
        useQuickAddLabel.setText("Use Quick Add:");
        viewAllowPermReqdLabel.setText("View Allow Perm Reqd:");
        contentPathPrefixLabel.setText("Content Path Prefix:");
        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layoutPanel);
        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(prodCatalogIdLabel)
                .add(styleSheetLabel)
                .add(viewAllowPermReqdLabel)
                .add(templatePathPrefixLabel)
                .add(contentPathPrefixLabel)
                .add(catalogNameLabel)
                .add(headerLogoLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(prodCatalogIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(styleSheetTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(viewAllowPermReqdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(templatePathPrefixTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(contentPathPrefixTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(catalogNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(headerLogoTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(useQuickAddLabel)
                .add(purchaseAllowPermReqdLabel)
                .add(lastUpdatedStampLabel)
                .add(createdTxStampLabel)
                .add(createdStampLabel)
                .add(lastUpdatedTxStampLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(useQuickAddTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(purchaseAllowPermReqdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(lastUpdatedStampTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(createdTxStampTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(createdStampTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(lastUpdatedTxStampTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));
        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(prodCatalogIdLabel)
                .add(prodCatalogIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(useQuickAddLabel)
                .add(useQuickAddTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(styleSheetLabel)
                .add(styleSheetTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(purchaseAllowPermReqdLabel)
                .add(purchaseAllowPermReqdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(viewAllowPermReqdLabel)
                .add(viewAllowPermReqdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(lastUpdatedStampLabel)
                .add(lastUpdatedStampTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(templatePathPrefixLabel)
                .add(templatePathPrefixTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(createdTxStampLabel)
                .add(createdTxStampTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(contentPathPrefixLabel)
                .add(contentPathPrefixTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(createdStampLabel)
                .add(createdStampTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(catalogNameLabel)
                .add(catalogNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(lastUpdatedTxStampLabel)
                .add(lastUpdatedTxStampTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(headerLogoLabel)
                .add(headerLogoTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));
    }

    public void setUIFields() throws  java.text.ParseException {
        lastUpdatedTxStampTextField.setText(OrderMaxUtility.getValidTimestamp(prodcatalog.getlastUpdatedTxStamp()));
        styleSheetTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getstyleSheet()));
        createdTxStampTextField.setText(OrderMaxUtility.getValidTimestamp(prodcatalog.getcreatedTxStamp()));
        purchaseAllowPermReqdTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getpurchaseAllowPermReqd()));
        templatePathPrefixTextField.setText(OrderMaxUtility.getValidString(prodcatalog.gettemplatePathPrefix()));
        prodCatalogIdTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getprodCatalogId()));
        contentPathPrefixTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getcontentPathPrefix()));
        createdStampTextField.setText(OrderMaxUtility.getValidTimestamp(prodcatalog.getcreatedStamp()));
        catalogNameTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getcatalogName()));
        viewAllowPermReqdTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getviewAllowPermReqd()));
        headerLogoTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getheaderLogo()));
        useQuickAddTextField.setText(OrderMaxUtility.getValidString(prodcatalog.getuseQuickAdd()));
        lastUpdatedStampTextField.setText(OrderMaxUtility.getValidTimestamp(prodcatalog.getlastUpdatedStamp()));
    }

    public void getUIFields() throws java.text.ParseException {
        prodcatalog.setlastUpdatedTxStamp(OrderMaxUtility.getValidTimestamp(lastUpdatedTxStampTextField.getText()));
        prodcatalog.setstyleSheet(OrderMaxUtility.getValidString(styleSheetTextField.getText()));
        prodcatalog.setcreatedTxStamp(OrderMaxUtility.getValidTimestamp(createdTxStampTextField.getText()));
        prodcatalog.setpurchaseAllowPermReqd(OrderMaxUtility.getValidString(purchaseAllowPermReqdTextField.getText()));
        prodcatalog.settemplatePathPrefix(OrderMaxUtility.getValidString(templatePathPrefixTextField.getText()));
        prodcatalog.setprodCatalogId(OrderMaxUtility.getValidString(prodCatalogIdTextField.getText()));
        prodcatalog.setcontentPathPrefix(OrderMaxUtility.getValidString(contentPathPrefixTextField.getText()));
        prodcatalog.setcreatedStamp(OrderMaxUtility.getValidTimestamp(createdStampTextField.getText()));
        prodcatalog.setcatalogName(OrderMaxUtility.getValidString(catalogNameTextField.getText()));
        prodcatalog.setviewAllowPermReqd(OrderMaxUtility.getValidString(viewAllowPermReqdTextField.getText()));
        prodcatalog.setheaderLogo(OrderMaxUtility.getValidString(headerLogoTextField.getText()));
        prodcatalog.setuseQuickAdd(OrderMaxUtility.getValidString(useQuickAddTextField.getText()));
        prodcatalog.setlastUpdatedStamp(OrderMaxUtility.getValidTimestamp(lastUpdatedStampTextField.getText()));
    }

    public JPanel getContainerPanel() {
        return this;
    }

    @Override
    public void changeUIObject(org.ofbiz.ordermax.generic.GenericValueObjectInterface uiObject) {
        if (uiObject instanceof ProdCatalog) {
            ProdCatalog newObj = (ProdCatalog) uiObject;
            prodcatalog = newObj;
            try {
                prodcatalog.setGenericValue();
            } catch (Exception ex) {
                Logger.getLogger(ProdCatalogPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public org.ofbiz.ordermax.generic.GenericValueObjectInterface createUIObject(GenericValue baseVal) {
        
//        if(baseVal == null){
//            baseVal = ProdCatalog.getNewGenericValueObj(session.getDelegator());
//        }
                    
        ProdCatalog newObj = new ProdCatalog(baseVal);
        try {
            newObj.setGenericValue();
        } catch (Exception ex) {
          //  Logger.getLogger(ProdCatalogPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newObj;
    }

    public void newItem() {

        try {
            //TelecomNumber telNumber = new TelecomNumber();
            GenericValueObjectInterface uiObject = this.createUIObject(null);
            changeUIObject(uiObject);
            setUIFields();
        } catch (java.text.ParseException ex) {
        }
    }

    public void saveItem() throws Exception {

        GenericValueObjectInterface prodCat = getUIObject();
        GenericValue detailValue = prodCat.getGenericValueObj();
        if (prodCat.isGenericValueSet() == false) {
            detailValue = prodCat.createNewGenericValueObj(session.getDelegator());
        }
        getUIFields();
        prodCat.getGenericValue();
        Debug.logInfo("Save 1", module);
        try {
//            GenericValue prodCat = delegator.findByPrimaryKey("ProdCatalog", UtilMisc.toMap("prodCatalogId", prodCatalogId));
//            if (prodCat == null) {
//                prodCat = createNewGenericValueObj(delegator);
//            }
            //store person
            session.getDelegator().createOrStore(prodCat.getGenericValueObj());
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
//            Debug.logError(e, module);
            e.printStackTrace();
//            result = false;
        }
//        prodCat.createOrStoreGenericValue(session.getDelegator());
        Debug.logInfo("Save 3", module);
    }

    public GenericValueObjectInterface getUIObject() {
        return prodcatalog;
    }

    public boolean isModified() {
        return isModified;

    }

    public void setIsModified(boolean isModified) {
        this.isModified = isModified;
    }
    private boolean isModified = false;

    @Override
    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addChangeListener(PropertyChangeListener newListener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setItem(Object val) {

        GenericValue prodCat = (GenericValue) val;
        GenericValueObjectInterface uiObj = this.createUIObject(prodCat);
        this.changeUIObject(uiObj);
        try {
            this.setUIFields();
        }catch (java.text.ParseException ex) {
            Debug.logError(ex, module);
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
}
//calling function copy and paste
/*
 try {

 Delegator delegator = XuiContainer.getSession().getDelegator();
 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("ProdCatalog", delegator);
 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),ProdCatalog.ColumnNameId);
 dlg.setupOrderTableList(genValList);
 GenericValue val = genValList.get(0);
 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.ProdCatalogPanel( ); 
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
