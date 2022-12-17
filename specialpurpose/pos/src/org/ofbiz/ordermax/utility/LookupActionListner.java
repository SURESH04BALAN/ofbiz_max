/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.generic.GenericValueDetailTableDialog;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;
import java.awt.Dimension;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelContainerPanel;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface.LookupType;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.base.components.ReturnPickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;

import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;
import org.ofbiz.ordermax.invoice.FindInvoiceListController;
import org.ofbiz.ordermax.orderbase.FindOrderByPartyListController;
import org.ofbiz.ordermax.orderbase.returns.FindOrderReturnListController;
import org.ofbiz.ordermax.party.FindPartyListController;
import org.ofbiz.ordermax.party.PartyMainScreen;
import org.ofbiz.ordermax.generic.GenericSelectionPanel;
import org.ofbiz.ordermax.product.CategoryTreeSelectionPanelFrame;
import org.ofbiz.ordermax.product.FindProductListController;

/**
 *
 * @author siranjeev
 */
public class LookupActionListner implements ActionListener {

    public static final String module = LookupActionListner.class.getName();
    JTextField textField = null;
    String fieldName;
    JTextField textFieldTwo = null;
    String fieldNameTwo;
    List<String> resultIds = new ArrayList<String>();
//    JFrame parentFrame = null;
//    protected javax.swing.JDesktopPane ControllerOptions.getDesktopPane()  = null;
//    String roleTypeId = "BILL_FROM_VENDOR";

    private List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public List<String> getResultIds() {
        return resultIds;
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public void removeAll() {
        listeners.clear();
    }
    private LookupType lookupType = LookupType.PartyId;
    ControllerOptions options = new ControllerOptions();

    public LookupActionListner(LookupType lookupType, ControllerOptions options) {
        this.lookupType = lookupType;
        this.options = options.getCopy();

        /*Debug.logInfo("LookupActionListner 1", module);
         try{
         throw new Exception("Lookup Action Listner");
         }
         catch(Exception e){
         Debug.logError(e, module);
         }*/
    }

    LookupActionListnerInterface lookupInterface = null;

    public LookupActionListner(LookupActionListnerInterface lookupInterface, ControllerOptions options) {
        this.lookupType = lookupInterface.getLookupType();
        this.options = options.getCopy();
        this.lookupInterface = lookupInterface;

    }

    public LookupActionListner(JTextField field, String name) {
        textField = field;
        fieldName = name;
        Debug.logInfo("LookupActionListner 2", module);
    }

    public LookupActionListner(JTextField field, String name, JTextField field1, String name1) {
        textField = field;
        fieldName = name;
        textFieldTwo = field1;
        fieldNameTwo = name1;
        Debug.logInfo("LookupActionListner 5", module);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (this.lookupType.equals(LookupType.PartyId)) {

                JTextField partyIdTextField = (JTextField) options.get("PartyIdTextField");
                if (partyIdTextField != null && UtilValidate.isNotEmpty(partyIdTextField.getText())) {
                    options.put("partyId", "");//partyIdTextField.getText());
                    Debug.logError("options.get( : " + options.get("partyId"), module);
                }
                Debug.logError("options.get( : 1", module);
                Map<String, Object> genVal = getUserPartyUserSelection();
                if (genVal != null) {
                    if (genVal.containsKey("Account")) {
                        Account acct = (Account) genVal.get("Account");
                        partyIdTextField.setText(acct.getParty().getpartyId());
                        partyIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
                    }
                }
                return;
            } else if (this.lookupType.equals(LookupType.OrderId)) {

                JTextField partyIdTextField = (JTextField) options.get("PartyIdTextField");
                if (partyIdTextField != null && UtilValidate.isNotEmpty(partyIdTextField.getText())) {
                    options.put("partyId", partyIdTextField.getText());
                }

                JTextField orderIdTextField = (JTextField) options.get("OrderIdTextField");
                if (orderIdTextField != null && UtilValidate.isNotEmpty(orderIdTextField.getText())) {
                    options.put("orderId", orderIdTextField.getText());
                }

                Map<String, Object> genVal = getOrderUserSelection();
                if (genVal != null) {
                    if (genVal.containsKey("orderId")) {
                        String orderId = (String) genVal.get("orderId");
                        orderIdTextField.setText(orderId);
                        orderIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "orderId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
                    }
                }
                return;
            } else if (this.lookupType.equals(LookupType.ProductId)) {

                JTextField productIdTextField = (JTextField) options.get(ProductPickerEditPanel.editorId);

                if (productIdTextField != null) {
                    Map<String, Object> genVal = getUserProductSelection();

                    if (genVal != null && genVal.containsKey("Product")) {
                        ProductComposite acct = (ProductComposite) genVal.get("Product");
                        productIdTextField.setText(acct.getProduct().getproductId());
                        productIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
                    }
                } else {
                    Map<String, Object> genVal = getUserProductSelection();
                    resultIds.clear();
                    if (genVal != null && genVal.containsKey("Product")) {
                        ProductComposite acct = (ProductComposite) genVal.get("Product");
                        resultIds.add(acct.getProduct().getproductId());
                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "productId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
                    }
                }
                return;
            } else if (this.lookupType.equals(LookupType.InvoiceId)) {
                JTextField partyIdTextField = (JTextField) options.get("PartyIdTextField");
                if (partyIdTextField != null && UtilValidate.isNotEmpty(partyIdTextField.getText())) {
                    options.put("partyId", partyIdTextField.getText());
                }

                JTextField invoiceIdTextField = (JTextField) options.get("InvoiceIdTextField");
                if (invoiceIdTextField != null && UtilValidate.isNotEmpty(invoiceIdTextField.getText())) {
                    options.put("invoiceId", invoiceIdTextField.getText());
                }

                Map<String, Object> genVal = getInvoiceUserSelection();
                if (genVal != null) {
                    if (genVal.containsKey("invoiceId")) {
                        String orderId = (String) genVal.get("invoiceId");
                        invoiceIdTextField.setText(orderId);
                        invoiceIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "invoiceId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
                    }
                }
                return;
            } else if (this.lookupType.equals(LookupType.ReturnId)) {
                JTextField partyIdTextField = (JTextField) options.get("PartyIdTextField");
                if (partyIdTextField != null && UtilValidate.isNotEmpty(partyIdTextField.getText())) {
                    options.put("partyId", partyIdTextField.getText());
                }

                JTextField returnIdTextField = (JTextField) options.get(ReturnPickerEditPanel.ReturnEditorId);
                if (returnIdTextField != null && UtilValidate.isNotEmpty(returnIdTextField.getText())) {
                    options.put("returnId", returnIdTextField.getText());
                }

                Map<String, Object> genVal = getReturnUserSelection();
                if (genVal != null) {
                    if (genVal.containsKey("returnId")) {
                        String orderId = (String) genVal.get("returnId");
                        returnIdTextField.setText(orderId);
                        returnIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "returnId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
                    }
                }

                return;
            } else if (this.lookupType.equals(LookupType.CategoryId)
                    || this.lookupType.equals(LookupType.CatalogId)
                    || this.lookupType.equals(LookupType.FacilityId)
                    || this.lookupType.equals(LookupType.ProductStoreId)
                    || this.lookupType.equals(LookupType.TerminalId)
                    || this.lookupType.equals(LookupType.ProdCatalogCategoryId)
                    || this.lookupType.equals(LookupType.ProductStoreCatalogId)) {

                final JTextField productIdTextField = (JTextField) options.get(lookupInterface.getEditorId());

                if (productIdTextField != null) {

                    ControllerOptions tmpOptions = new ControllerOptions(this.options);
                    tmpOptions.put("X", 150);
                    tmpOptions.put("Y", 10);

                    final GenericSelectionPanel viewer = new GenericSelectionPanel(tmpOptions);
                    tmpOptions.setSimpleScreenInterface(viewer);
                    final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(tmpOptions);
                    frame.addActionListener(
                            new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                                        if (viewer.getSelectedValue() != null) {
                                            //seleVal.put("Category", viewer.selectedCategory);
                                            // GenericValue genericValue = (GenericValue) genValMap.get("Category");
                                            productIdTextField.setText(viewer.getSelectedValue().getString(lookupInterface.getEntityId()));
                                            productIdTextField.requestFocusInWindow();
                                            lookupInterface.setGenericValue(viewer.getSelectedValue());
                                            //notify 
                                            ActionEvent event = new ActionEvent(this, 1, "categoryId", new Date().getTime(), 2);
                                            for (ActionListener listener : listeners) {
                                                listener.actionPerformed(event); // broadcast to all
                                            }
                                            listeners.clear();
                                            Debug.logInfo(" lookup brandCategory : " + viewer.getSelectedValue().getString(lookupInterface.getEntityId()), module);
                                            /*
                                             panel.getOrder().addContactMech("BILLING_LOCATION", viewer.getSelectedPostalContact().getContactMech().getcontactMechId());
                                             //                                            panel.getOrder().setAllShippingContactMechId(viewer.getSelectedPostalContact().getPostalAddress().getContactMechId());
                                             panel.setDialogFields();
                                             //                                            panel.getOrder().getOrderContactList().setShippingLocationContact()
                                             Debug.logInfo("ok pressed: " + viewer.postalAddressList.getSelectedValue().getContactMechId(), module);
                                             */
                                        }
                                    }
                                }
                            });
                }

                return;
            }

            /*
             if (fieldName.equals("countryGeoIdTextField")) {
             String entityName = "CountryCode";
             String[][] idCols = Geo.ColumnNameId;
             Delegator delegator = XuiContainer.getSession().getDelegator();
             //                List<GenericValue> genValList = PosProductHelper.getGenericValueLists("CountryCode", delegator);
             GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.CountryCodePanel();
             List<GenericValue> genValList = OrderMaxUtility.getCountries(delegator);
             GenericValue genVal = getUserSelection(entityName, idCols, genValList, panel);
             if (genVal != null) {
             textField.setText(genVal.getString("geoId"));
             }

             } else if (fieldName.equals("stateProvinceGeoIdTextField")) {
             String entityName = "Geo";
             String[][] idCols = Geo.ColumnNameId;
             Delegator delegator = XuiContainer.getSession().getDelegator();
             //List<GenericValue> genValList = PosProductHelper.getGenericValueLists(entityName, delegator);
             List<GenericValue> genValList = OrderMaxUtility.getStates(delegator);
             GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.CountryCodePanel();
             GenericValue genVal = getUserSelection( entityName, idCols, genValList, panel);
             if (genVal != null) {
             textField.setText(genVal.getString("geoId"));
             }
             } else if (fieldName.equals("partyIdTextField")) {

             Map<String, Object> genVal = getUserPartyUserSelection();
             if (genVal != null) {
             if (genVal.containsKey("Account")) {
             Account acct = (Account) genVal.get("Account");
             textField.setText(acct.getParty().getpartyId());
             textField.requestFocusInWindow();

             //notify 
             ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
             for (ActionListener listener : listeners) {
             listener.actionPerformed(event); // broadcast to all
             }
             }
             }
             } else if (fieldName.equals("productIdTextField")) {
             Map<String, Object> genVal = getUserProductSelection();
             if (genVal != null) {
             if (genVal.containsKey("Product")) {
             ProductComposite acct = (ProductComposite) genVal.get("Product");
             textField.setText(acct.getProduct().getproductId());
             textField.requestFocusInWindow();

             //notify 
             ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
             for (ActionListener listener : listeners) {
             listener.actionPerformed(event); // broadcast to all
             }
             }
             }
             } else if (fieldName.equals("orderIdTextField")) {

             String[][] idCols = OrderHeaderAndRoles.ColumnNameId;
             Delegator delegator = XuiContainer.getSession().getDelegator();

             String partyId = null;
             String orderId = null;
             if (textField != null && textField.getText().isEmpty() == false) {
             orderId = textField.getText();
             }

             if (textFieldTwo != null && textFieldTwo.getText().isEmpty() == false) {

             partyId = textFieldTwo.getText();
             }

             //List<GenericValue> genValList = PosProductHelper.getGenericValueLists(entityName, delegator);
             //                List<GenericValue> genValList = PosProductHelper.getOrderHeader(partyId, orderId, delegator);
             String entityName = "OrderHeaderAndRoles";
             Map<String, Object> whereClauseMap = new HashMap<String, Object>();
             if (partyId != null && partyId.isEmpty() == false) {
             whereClauseMap.put("partyId", partyId);
             }
             whereClauseMap.put("roleTypeId", roleTypeId);
             List<GenericValue> genValList = PosProductHelper.getGenericValueListsWithSelection(entityName, whereClauseMap, null, delegator);

             GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.OrderHeaderAndRolesPanel();
             GenericValue genVal = getUserSelection(entityName, idCols, genValList, panel);
             if (genVal != null) {
             if (genVal.containsKey("orderId")) {
             textField.setText(genVal.get("orderId").toString());
             textField.requestFocusInWindow();
             }
             }
             }*/
            //   public static List<GenericValue> getOrderHeader(String partyId, String orderId, Delegator delegator) {
        } catch (ParseException ex) {
            Debug.logError(ex, module);
        }
    }

    public static GenericValue getUserSelection(String entityName, String[][] IdColumnName,
            List<GenericValue> genValList, GenericValuePanelInterfaceOrderMax panel) throws java.text.ParseException {

        GenericValue seleVal = null;
        GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(null, false, XuiContainer.getSession(), IdColumnName);
        dlg.setReadOnlyTable(true);
        dlg.setupOrderTableList(genValList);
        GenericValue val = null;
        if (genValList != null && genValList.size() > 0) {
            genValList.get(0);
        }
        //GenericValuePanelInterface panel = new org.ofbiz.ordermax.utility.CountryCapitalPanel();
        GenericValueObjectInterface uiObj = panel.createUIObject(val);
        panel.changeUIObject(uiObj);
        panel.setUIFields();
        dlg.setChildPanelInterface(panel);

        dlg.getParentPanel().setPreferredSize(new Dimension(200, 250));

        JScrollPane vertical = new JScrollPane(panel.getContainerPanel());
        vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dlg.getParentPanel().setLayout(new BorderLayout());
        dlg.getParentPanel().add(BorderLayout.CENTER, vertical);
//        OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());
//        dlg.setLocationRelativeTo(null);
        dlg.pack();
        Debug.logInfo("set Visibale", module);
        dlg.setVisible(true);
        GenericValue genVal = dlg.getSelectedGenericVal();
        if (genVal != null && dlg.getReturnStatus() == GenericValueDetailTableDialog.RET_OK) {

            seleVal = genVal;
        }

        return seleVal;
    }

    private boolean isVaildPartySearch(GenericValue genParty, Map<String, String> searchSelMap) {
        boolean result = true;
        Debug.logInfo("isVaildPartySearch", module);
        if (searchSelMap.isEmpty() == false) {
            Debug.logInfo("isVaildPartySearch size: " + searchSelMap.size(), module);
            for (Map.Entry<String, String> anEntry : searchSelMap.entrySet()) {
                String val = genParty.getString(anEntry.getKey());
                Debug.logInfo("isVaildPartySearch str: " + val, module);
                if (anEntry.getValue() == null || val.toUpperCase().indexOf(anEntry.getValue()) == -1) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    List<GenericValue> partyList = null;  //PosProductHelper.getGenericValueLists("PartyRoleAndPartyDetail", delegator);
/*
     public List<Map<String, Object>> getPartyList(Map<String, String> searchSelMap) {
     List<Map<String, Object>> list = FastList.newInstance();
     Delegator delegator = XuiContainer.getSession().getDelegator();

     if (partyList == null) {
     partyList = PosProductHelper.getGenericValueLists("PartyRoleAndPartyDetail", delegator);
     Map<String, Object> whereClauseMap = new HashMap<String, Object>();
     whereClauseMap.put("roleTypeId", roleTypeId);
     partyList = PosProductHelper.getGenericValueListsWithSelection("PartyRoleAndPartyDetail", whereClauseMap, null, delegator);
     for (GenericValue genParty : partyList) {
     String str = PartyHelper.getPartyName(genParty, false);
     genParty.setString("description", str);
     }
     }

     for (GenericValue genParty : partyList) {
     //            String str = PartyHelper.getPartyName(genParty, false);
     //            genParty.setString("description", str);
     if (isVaildPartySearch(genParty, searchSelMap)) {
     Map<String, Object> dataMap = OrderMaxPartyHelper.createPartyContactMech(XuiContainer.getSession().getDelegator(), genParty, null, null);

     String partyId = genParty.getString("partyId");
     GenericValue email = PartyWorker.findPartyLatestContactMech(partyId, "EMAIL_ADDRESS", delegator);
     if (UtilValidate.isNotEmpty(email)) {
     String emailString = email.getString("infoString");
     dataMap.put("infoString", emailString);
     }

     GenericValue telecomNumber = PartyWorker.findPartyLatestTelecomNumber(partyId, delegator);
     if (UtilValidate.isNotEmpty(telecomNumber)) {
     String contactNumber = telecomNumber.getString("contactNumber");
     dataMap.put("contactNumber", contactNumber);
     }
     list.add(dataMap);
     }
     }

     return list;
     }
     */

    Map<String, Object> getUserPartyUserSelection() throws java.text.ParseException, ParseException {

        Map<String, Object> seleVal = new HashMap<String, Object>();
        final FindPartyListController findOrderListMain = FindPartyListController.createController(options);

        if (ControllerOptions.getDesktopPane() != null) {
            final TwoPanelContainerPanel dlg = findOrderListMain.loadNonSizeableInternalFrameDialogScreen(FindProductListController.module, ControllerOptions.getDesktopPane());
            dlg.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Account account = findOrderListMain.getSelectedAccount();

                    if (account != null && account.getParty() != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                        JTextField partyIdTextField = (JTextField) options.get("PartyIdTextField");
                        if (partyIdTextField != null) {
                            try {
                                GenericValue genPartyNameView = XuiContainer.getSession().getDelegator().findByPrimaryKeyCache("PartyNameView", UtilMisc.toMap("partyId", account.getParty().getpartyId()));
                                if (lookupInterface != null) {
                                    lookupInterface.setGenericValue(genPartyNameView);
                                }
                            } catch (GenericEntityException ex) {
                                Logger.getLogger(LookupActionListner.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            partyIdTextField.setText(account.getParty().getpartyId());
                            partyIdTextField.requestFocusInWindow();
                        }

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
//                            seleVal.put("Product", genVal);
//                            Debug.logError("getUserProductSelection: " + genVal.getProduct().getproductId(), module);
                    }

                }
            });

        } else {

            findOrderListMain.loadNonSizeableFrameDialogScreen(PartyMainScreen.module, null);

            Account genVal = findOrderListMain.getSelectedAccount();
//            Debug.logError(" genVal : " + genVal.getDisplayName() + "  findOrderListMain.getReturnStatus() : " + findOrderListMain.getReturnStatus() , module);
            if (genVal != null && findOrderListMain.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                seleVal.put("Account", genVal);
                Debug.logError(" put in array : " + genVal.getDisplayName(), module);
            } else {
                Debug.logError("NOT put in array : ", module);
            }
        }
        return seleVal;

    }

    Map<String, Object> getOrderUserSelection() throws java.text.ParseException, ParseException {

        Map<String, Object> seleVal = new HashMap<String, Object>();

        final FindOrderByPartyListController findOrderListController = new FindOrderByPartyListController(options, XuiContainer.getSession());
        if (ControllerOptions.getDesktopPane() != null) {
            final TwoPanelContainerPanel dlg = findOrderListController.loadNonSizeableInternalFrameDialogScreen(FindProductListController.module, ControllerOptions.getDesktopPane());
            dlg.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    OrderHeaderAndRoleSummary orderHeaderAndRoleSummary = findOrderListController.getSelectedOrder();

                    if (orderHeaderAndRoleSummary != null && orderHeaderAndRoleSummary.getorderId() != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                        JTextField invoiceIdTextField = (JTextField) options.get("OrderIdTextField");
                        invoiceIdTextField.setText(orderHeaderAndRoleSummary.getorderId());
                        invoiceIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
//                            seleVal.put("Product", genVal);
//                            Debug.logError("getUserProductSelection: " + genVal.getProduct().getproductId(), module);
                    }

                }
            });
        } else {

            TwoPanelNonSizableContainerDlg dlg = findOrderListController.loadNonSizeableFrameDialogScreen(PartyMainScreen.module, null);

            OrderHeaderAndRoleSummary genVal = findOrderListController.getSelectedOrder();
            if (genVal != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                seleVal.put("orderId", genVal.getorderId());
            }
        }
        return seleVal;

    }

    Map<String, Object> getInvoiceUserSelection() throws java.text.ParseException, ParseException {

        Map<String, Object> seleVal = new HashMap<String, Object>();

        final FindInvoiceListController findInvoiceListController = new FindInvoiceListController(null, options, XuiContainer.getSession());
        if (ControllerOptions.getDesktopPane() != null) {
            final TwoPanelContainerPanel dlg = findInvoiceListController.loadNonSizeableInternalFrameDialogScreen(FindProductListController.module, ControllerOptions.getDesktopPane());
            dlg.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    InvoiceComposite invoiceComposite = findInvoiceListController.getSelectedInvoice();

                    if (invoiceComposite != null && invoiceComposite.getInvoice() != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                        JTextField invoiceIdTextField = (JTextField) options.get("InvoiceIdTextField");
                        invoiceIdTextField.setText(invoiceComposite.getInvoice().getinvoiceId());
                        invoiceIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
//                            seleVal.put("Product", genVal);
//                            Debug.logError("getUserProductSelection: " + genVal.getProduct().getproductId(), module);
                    }

                }
            });
        } else {
            TwoPanelNonSizableContainerDlg dlg = findInvoiceListController.loadNonSizeableFrameDialogScreen(PartyMainScreen.module, null);

            InvoiceComposite genVal = findInvoiceListController.getSelectedInvoice();
            if (genVal != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                seleVal.put("invoiceId", genVal.getInvoice().getinvoiceId());
            }
        }
        return seleVal;
    }

    Map<String, Object> getReturnUserSelection() throws java.text.ParseException, ParseException {

        Map<String, Object> seleVal = new HashMap<String, Object>();

        final FindOrderReturnListController findInvoiceListController = new FindOrderReturnListController(options, XuiContainer.getSession());
        if (ControllerOptions.getDesktopPane() != null) {
            final TwoPanelContainerPanel dlg = findInvoiceListController.loadNonSizeableInternalFrameDialogScreen(FindProductListController.module, ControllerOptions.getDesktopPane());
            dlg.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    ReturnHeaderComposite returnHeaderComposite = findInvoiceListController.getSelectedReturn();

                    if (returnHeaderComposite != null && returnHeaderComposite.getReturnHeader() != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                        JTextField invoiceIdTextField = (JTextField) options.get("ReturnIdTextField");
                        invoiceIdTextField.setText(returnHeaderComposite.getReturnHeader().getreturnId());
                        invoiceIdTextField.requestFocusInWindow();

                        //notify 
                        ActionEvent event = new ActionEvent(this, 1, "returnId", new Date().getTime(), 2);
                        for (ActionListener listener : listeners) {
                            listener.actionPerformed(event); // broadcast to all
                        }
                        listeners.clear();
//                            seleVal.put("Product", genVal);
//                            Debug.logError("getUserProductSelection: " + genVal.getProduct().getproductId(), module);
                    }

                }
            });
        } else {
            TwoPanelNonSizableContainerDlg dlg = findInvoiceListController.loadNonSizeableFrameDialogScreen(PartyMainScreen.module, null);

            ReturnHeaderComposite returnHeaderComposite = findInvoiceListController.getSelectedReturn();

            if (returnHeaderComposite != null && returnHeaderComposite.getReturnHeader() != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                JTextField invoiceIdTextField = (JTextField) options.get("ReturnIdTextField");
                invoiceIdTextField.setText(returnHeaderComposite.getReturnHeader().getreturnId());
                invoiceIdTextField.requestFocusInWindow();

                //notify 
                ActionEvent event = new ActionEvent(this, 1, "returnId", new Date().getTime(), 2);
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event); // broadcast to all
                }
                listeners.clear();
//                            seleVal.put("Product", genVal);
//                            Debug.logError("getUserProductSelection: " + genVal.getProduct().getproductId(), module);
            }
        }
        return seleVal;
    }

    public Map<String, Object> getUserProductSelection() {
        {

            Map<String, Object> seleVal = new HashMap<String, Object>();
            final FindProductListController findOrderListMain = new FindProductListController(options, XuiContainer.getSession());

            if (ControllerOptions.getDesktopPane() != null) {
                final TwoPanelContainerPanel dlg = findOrderListMain.loadNonSizeableInternalFrameDialogScreen(FindProductListController.module, ControllerOptions.getDesktopPane());
                dlg.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        ProductComposite prodComp = findOrderListMain.getSelectedProduct();
                        
                        if (prodComp != null && prodComp.getProduct() != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                        
                            JTextField productIdTextField = (JTextField) options.get(ProductPickerEditPanel.editorId);
                            if (productIdTextField != null) {
                                productIdTextField.setText(prodComp.getProduct().getproductId());
                                productIdTextField.requestFocusInWindow();
                            }
                            
                            resultIds.clear();
                            List<ProductComposite> items = findOrderListMain.getSelectedProductListArray();
                            Debug.logError("items: " + items.size(), "Module");
                            for (ProductComposite item : items) {
                                resultIds.add(item.getProduct().getproductId());
                            }
                        Debug.logError("listeners: " + listeners.size(), "Module");                           
                            //notify 
                            ActionEvent event = new ActionEvent(this, 1, "productId", new Date().getTime(), 2);
                            for (ActionListener listener : listeners) {
                       Debug.logError("listeners: " + listeners.size(), "Module");                    
                                listener.actionPerformed(event); // broadcast to all
                            }
                        Debug.logError("resultIds: " + resultIds.size(), "Module");                           
                            resultIds.clear();
                             dlg.removeActionListener(this);
                   // listeners.clear();                            
                        }

                    }
                });
            } else {

                resultIds.clear();

                TwoPanelNonSizableContainerDlg dlg = findOrderListMain.loadNonSizeableFrameDialogScreen(PartyMainScreen.module, null);
                ProductComposite prodComp = findOrderListMain.getSelectedProduct();
                if (prodComp != null && prodComp.getProduct() != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {

                    JTextField productIdTextField = (JTextField) options.get(ProductPickerEditPanel.editorId);

                    if (productIdTextField != null) {
                        productIdTextField.setText(prodComp.getProduct().getproductId());
                        productIdTextField.requestFocusInWindow();
                    }
                    resultIds.add(prodComp.getProduct().getproductId());

                    //notify 
                    ActionEvent event = new ActionEvent(this, 1, "productId", new Date().getTime(), 2);
                    for (ActionListener listener : listeners) {
                        listener.actionPerformed(event); // broadcast to all
                    }
                    listeners.clear();

                    //notify                     
                }

                /* if (genVal != null && dlg.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {

                 seleVal.put("Product", genVal);
                 Debug.logError("getUserProductSelection: " + genVal.getProduct().getproductId(), module);
                 }
                 Debug.logError("not waiting lookup: " + "Out side", module);*/
            }
            return seleVal;

        }

    }

    public Map<String, Object> getUserCategorySelection() {
        {
            /*            try{
             throw new Exception("getUserProductSelection");
             }
             catch(Exception e){
             Debug.logError(e, module);;
             }
             */
            final Map<String, Object> seleVal = new HashMap<String, Object>();
            ControllerOptions options = new ControllerOptions();
            options.put("X", 150);
            options.put("Y", 10);
            final CategoryTreeSelectionPanelFrame viewer = new CategoryTreeSelectionPanelFrame(options);
            //viewer.setPartyContactMechCompositeListData(panel.getOrder().getBillToAccount().getParty().getPartyContactList());
            options.setSimpleScreenInterface(viewer);
            final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(options);
            frame.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                                if (viewer.selectedCategory != null) {
                                    seleVal.put("Category", viewer.selectedCategory);
                                    Debug.logInfo(" lookup brandCategory : " + viewer.selectedCategory.getString("productCategoryId"), module);
                                    /*
                                     panel.getOrder().addContactMech("BILLING_LOCATION", viewer.getSelectedPostalContact().getContactMech().getcontactMechId());
                                     //                                            panel.getOrder().setAllShippingContactMechId(viewer.getSelectedPostalContact().getPostalAddress().getContactMechId());
                                     panel.setDialogFields();
                                     //                                            panel.getOrder().getOrderContactList().setShippingLocationContact()
                                     Debug.logInfo("ok pressed: " + viewer.postalAddressList.getSelectedValue().getContactMechId(), module);
                                     */
                                }
                            }
                        }
                    });
            return seleVal;

        }
    }
}
