/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.LoadAccountWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JGenericListBoxSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import static org.ofbiz.order.shoppingcart.CheckOutHelper.module;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.ActionButtonGroup;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.base.components.ShipGroupEditPanel;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderContactMechComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupList;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.OrderDeliverySchedule;
import org.ofbiz.ordermax.entity.OrderItemShipGroup;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.ProductStoreFacility;
import org.ofbiz.ordermax.entity.ShipmentMethodType;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.party.PartyContactMechHelper;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.party.contact.ContactMechWorker;

/**
 *
 * @author siranjeev
 */
public class PanelShippingGroup extends javax.swing.JPanel {

    private OrderDeliverySchedule orderDeliverySchedule = null;

    //private JComboBoxSelectionModel<String> selectionComboModel = null;
    public JGenericListBoxSelectionModel<OrderItemShipGroup> orderItemShipGroupListModel = null;
    public JGenericComboBoxSelectionModel<ShipmentMethodType> shipmentMethodTypeModel = null;
    public JGenericComboBoxSelectionModel<ShipmentMethodType> supplierPartyModel = null;
    public JGenericComboBoxSelectionModel<ProductStoreFacility> inventoryFacilityModel = null;
    public JGenericComboBoxSelectionModel<Facility> facilityModel = null;
    private JGenericComboBoxSelectionModel<PostalAddress> postalAddressListModel = null;
    public PartyPickerEditPanel panelSupplierPartyIdPicker;
    org.ofbiz.ordermax.party.PostalAddressPanel postalAddressPanel = new org.ofbiz.ordermax.party.PostalAddressPanel();
    ControllerOptions options = null;
    private ButtonGroup group = null;

    String addressHeader = "      <table class=\"basic-table\" cellspacing=\"0\">\n"
            + "        <tbody>";
    String addressFooter = "      </tbody></table>\n";

    //ShoppingCart.CartShipInfo shipInfo = null;
    DatePickerEditPanel shipBeforeDate = null;
    DatePickerEditPanel shipAfterDate = null;
    OrderItemShipGroup orderItemShipGroup = null;

    /**
     * Creates new form TelephonePanel
     */
    public PanelShippingGroup(ControllerOptions options) {
        initComponents();
        this.options = options;
//        panelGoodIdentificationList.setLayout(new BorderLayout());
//        panelGoodIdentificationList.add(BorderLayout.CENTER, goodIdentificationList);

//        selectionComboModel = new JComboBoxSelectionModel<String>(new StringListCellRenderer(false));
//        panelShipGroups.setLayout(new BorderLayout());
        //      panelShipGroups.add(BorderLayout.CENTER, selectionComboModel.jComboBox);
        //       panelAddress.setLayout(new BorderLayout());
        List<PostalAddress> listPost = new ArrayList<PostalAddress>();
        postalAddressListModel = new JGenericComboBoxSelectionModel<PostalAddress>(listPost);
        postalAddressListModel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    PostalAddress postalAddress = postalAddressListModel.getSelectedItem();
                    postalAddressPanel.setPostalAddress(postalAddress);
                    try {
                        postalAddressPanel.setDialogFields();
                    } catch (ParseException ex) {
                        Logger.getLogger(PanelShippingGroup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        List<OrderItemShipGroup> list = new ArrayList<OrderItemShipGroup>();
        orderItemShipGroupListModel = new JGenericListBoxSelectionModel<OrderItemShipGroup>(list);
        panelShipGroups.setLayout(new BorderLayout());
        panelShipGroups.add(BorderLayout.CENTER, orderItemShipGroupListModel.jlistBox);
        orderItemShipGroupListModel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting() && !orderItemShipGroupListModel.selectionModel.isSelectionEmpty()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    orderItemShipGroup = orderItemShipGroupListModel.getSelectedItem();
                    setDialogField();
                }
            }
        });

        panelAddressDetail.setLayout(new BorderLayout());
        JPanel dateSelPanel = new JPanel();
//        dateSelPanel.add(new JLabel("Hello World"));
        dateSelPanel.add(BorderLayout.CENTER, postalAddressListModel.jComboBox);
        dateSelPanel.setSize(1024, 60);

        panelAddressDetail.add(BorderLayout.NORTH, dateSelPanel);
        panelAddressDetail.add(BorderLayout.CENTER, postalAddressPanel);
        // panelAddressDetail.add(BorderLayout.CENTER, postalAddressPanel);

        shipmentMethodTypeModel = new JGenericComboBoxSelectionModel<ShipmentMethodType>(ShipmentMethodTypeSingleton.getValueList());
        panelShippmentMethod.setLayout(new BorderLayout());
        panelShippmentMethod.add(BorderLayout.CENTER, shipmentMethodTypeModel.jComboBox);

        facilityModel = new JGenericComboBoxSelectionModel<Facility>(FacilitySingleton.getValueList());
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, facilityModel.jComboBox);

        shipBeforeDate = new DatePickerEditPanel();
        shipBeforeDate.setSession(XuiContainer.getSession());
        panelShipBeforeDate.setLayout(new BorderLayout());
        panelShipBeforeDate.add(BorderLayout.CENTER, shipBeforeDate);

        shipAfterDate = new DatePickerEditPanel();
        shipAfterDate.setSession(XuiContainer.getSession());
        panelShipBeforeAfter.setLayout(new BorderLayout());
        panelShipBeforeAfter.add(BorderLayout.CENTER, shipAfterDate);

        ControllerOptions partyOptions = new ControllerOptions(options);
        panelSupplierPartyIdPicker = new PartyPickerEditPanel(partyOptions);
        panelSupplierPartyId.setLayout(new BorderLayout());
        panelSupplierPartyId.add(BorderLayout.CENTER, panelSupplierPartyIdPicker);
//        panelGoodIdentificationList.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(panelShippingDetail, "Shipping Details");
        ComponentBorder.loweredBevelBorder(panelAddressDetail, "Shipping Address List");
        ComponentBorder.loweredBevelBorder(panelGoodIdentificationDetail, "Shipping Group");

        /*selectionComboModel.jComboBox.addActionListener(new ActionListener() {//add actionlistner to listen for change
         @Override
         public void actionPerformed(ActionEvent e) {

         String s = (String) selectionComboModel.jComboBox.getSelectedItem();//get the selected item
         int index = selectionComboModel.jComboBox.getSelectedIndex();

         getDialogField();

         shipInfo = order.getShipInfo(index);

         panelAddress.removeAll();
         JPanel panel = layoutEnquiryResults(s, shipInfo);
         //            panelList.put(str, panel);
         panelAddress.add(panel);
         setDialogField();

         }
         });*/
    }

    // method to iterate over the results from the database
    // construct an EnquiryPanel and add that EnquiryPanel to ourselves
    List<ShipGroupEditPanel> panelList = new ArrayList<ShipGroupEditPanel>();

    public JPanel layoutEnquiryResults(String str, final ShoppingCart.CartShipInfo shipInfo) {

        panelList.clear();
        JPanel panel = new JPanel();
        panel.setName(str);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int i = 0;

        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        final ActionButtonGroup buttonGroup = new ActionButtonGroup();
        buttonGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action Command is: " + e.getActionCommand());
                for (ShipGroupEditPanel shipPanel : panelList) {
                    if (shipPanel.rbAddress.isSelected()) {
                        shipPanel.shipInfo.setFacilityId(shipPanel.facilityId);
                        shipPanel.shipInfo.setContactMechId(shipPanel.contachMechId);
                    }
                }
            }
        });

        FacilitySingleton.getValueList();
        for (Facility facility : FacilitySingleton.getValueList()) {
            GenericValue facilityContactMech = ContactMechWorker.getFacilityContactMechByPurpose(XuiContainer.getSession().getDelegator(), facility.getfacilityId(), UtilMisc.toList("SHIP_ORIG_LOCATION", "PRIMARY_LOCATION"));
            if (facilityContactMech != null) {
                try {
                    GenericValue shipAddress = XuiContainer.getSession().getDelegator().findByPrimaryKey("PostalAddress",
                            UtilMisc.toMap("contactMechId", facilityContactMech.getString("contactMechId")));

                    StringBuilder orderToStringBuilder = new StringBuilder();
                    orderToStringBuilder.append(addressHeader);

                    String facilityStr = PartyContactMechHelper.getDisplayHtmlString("Facility: ", facility);
                    orderToStringBuilder.append(facilityStr);

                    orderToStringBuilder.append(PartyContactMechHelper.getAddresHtmlString(new PostalAddress(shipAddress), ""));
                    orderToStringBuilder.append(addressFooter);
                    ShipGroupEditPanel editPanel = new ShipGroupEditPanel(new ControllerOptions());
                    editPanel.facilityId = facility.getfacilityId();
                    editPanel.contachMechId = facilityContactMech.getString("contactMechId");
                    editPanel.shipInfo = shipInfo;
                    buttonGroup.add(editPanel.rbAddress);
                    if (UtilValidate.isNotEmpty(shipInfo.getFacilityId()) && shipInfo.getFacilityId().equals(facility.getfacilityId())) {
                        editPanel.rbAddress.setSelected(true);
                    }
                    editPanel.editAddress.setText(orderToStringBuilder.toString());
                    panel.add(editPanel);
                    gbc.gridy = i++;
                    gbc.gridwidth = 2;
                    gbc.gridx = 0;
                    gbc.weightx = 1;
                    gbc.weighty = 1;
                    gbc.fill = GridBagConstraints.BOTH;
                    panel.add(editPanel, gbc);
                    panelList.add(editPanel);
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                }
            }

        }

        /*
         for (int i = 0; i < order.getOrderContactList().getSize(); ++i) {
         OrderContact contact = order.getOrderContactList().getElementAt(i);
         contact.getContact().outputToDebug();

         if (contact.getContact().getContactMechTypeId() == Contact.ContactMechTypeId.POSTAL_ADDRESS) {
         //Debug.logInfo("postalAddress address1: " + OrderMaxUtility.getFormatedAddress(postalAddress.getGenericValueObj()), module);   
         StringBuilder orderToStringBuilder = new StringBuilder();
         orderToStringBuilder.append(addressHeader);

         String str = PartyContactMechHelper.getPartyHtmlString(order.getBillToAccount().getParty());
         orderToStringBuilder.append(str);

         orderToStringBuilder.append(PartyContactMechHelper.getAddresHtmlString(contact.getContact().getPostalAddress(),
         contact.getOrderContactMech().getcontactMechPurposeTypeId()));
         orderToStringBuilder.append(addressFooter);
         ShipGroupEditPanel editPanel = new ShipGroupEditPanel(new ControllerOptions());
         group.add(editPanel.rbAddress);
         if (i == 0) {
         editPanel.rbAddress.setSelected(true);
         }
         editPanel.editAddress.setText(orderToStringBuilder.toString());
         panelGoodIdentificationList.add(editPanel);

         }

         }
         */
//            Debug.logInfo(orderToStringBuilder.toString(), module);
        /*editBillingAddress.setText(orderToStringBuilder.toString());
         for (int i = 0; i < 5; i++) {
         ShipGroupEditPanel editPanel = new ShipGroupEditPanel(new ControllerOptions());
         group.add(editPanel.rbAddress);
         if (i == 0) {
         editPanel.rbAddress.setSelected(true);
         }
         panelGoodIdentificationList.add(editPanel);
         }*/
        return panel;
    }
    private Order order = null;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        if (order != null) {
            postalAddressListModel.dataListModel.clear();
            OrderItemShipGroupList orderItemShipGroupList = this.order.getOrderItemShipGroupList();
            orderItemShipGroupListModel.clear();
            if (orderItemShipGroupList != null) {
                orderItemShipGroupListModel.setDataList(orderItemShipGroupList.getList());
            }
            orderItemShipGroupListModel.jlistBox.repaint();
            if (this.order.getBillToAccount().getParty().getPartyContactList().getList().isEmpty()) {
                PartyContactMechCompositeList mechList = LoadAccountWorker.getPartyContactMechList(this.order.getBillToAccount().getParty().getParty().getpartyId(), XuiContainer.getSession());
                this.order.getBillToAccount().getParty().setPartyContactList(mechList);
            }

//        this.order.getBillToAccount().getParty().getPartyContactList();
//        ListAdapterListModel<PartyContactMechComposite> tempAddressPurposeListModel = new ListAdapterListModel<PartyContactMechComposite>();
            for (PartyContactMechComposite partyContactMechComposite : this.order.getBillToAccount().getParty().getPartyContactList().getList()) {
                if ("POSTAL_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
                    postalAddressListModel.dataListModel.add(partyContactMechComposite.getContact().getPostalAddress());
                } /*else if ("TELECOM_NUMBER".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
                 telecomNumberListModel.add(partyContactMechComposite.getContact().getTelecomNumber());
                 }*/

            }
//tmpVal = delegator.findByPrimaryKey(objetName, valueList/*UtilMisc.toMap("partyId", partyId)*/);

            for (Map.Entry<String, String> contactMechId : order.getOrderContactMechIds().entrySet()) {
                //for (OrderContactMechComposite contachMech : order.getOrderContactList().getList()) {
                if (this.order.getBillToAccount().getParty().getPartyContactList().getPartyContactMech(contactMechId.getValue()) == null) {
                    PartyContactMechComposite partyContactMechComposite = this.order.getBillToAccount().getParty().getPartyContactList().getPartyContactMech(contactMechId.getValue());
                    if (partyContactMechComposite!=null&&"POSTAL_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
                        postalAddressListModel.dataListModel.add(partyContactMechComposite.getContact().getPostalAddress());
                    }
                }
            }
        }
        /*        List<String> listStr = new ArrayList<String>();
         List<ShoppingCart.CartShipInfo> shipGroups = order.getShipGroups();

         int i = 0;
         for (ShoppingCart.CartShipInfo shipCart : shipGroups) {
         String index = "";
         i++;
         if (UtilValidate.isNotEmpty(shipCart.getShipGroupSeqId())) {
         index = shipCart.getShipGroupSeqId();
         } else {
         index = Integer.toString(i);
         }
         String str = "Ship Group Nbr " + index;
         listStr.add(str);
         //            panelScrollPane.setLayout(new BorderLayout());
         //            panelScrollPane.add(BorderLayout.CENTER, panel);
         }
        
         revalidate();
         selectionComboModel.setDataList(listStr);
         if (selectionComboModel.jComboBox.getItemCount() > 0) {
         selectionComboModel.jComboBox.setSelectedIndex(0);
         } */
    }

    //OrderTerm orderTerm = null;
    public void setGoodIdentification(int index) {

    }

    public void clearDialogFields() {
        /*txtTermValue.setText("");
         txtTermDays.setText("");
         txtDescription.setText("");*/
//        selectionComboModel.setDataList(new ArrayList<String>());
    }

    public void setDialogField() {
        if (orderItemShipGroup != null) {

            try {
//                TermType typeId = TermTypeSingleton.getTermType(orderTerm.gettermTypeId());
                //              int index = selectionComboModel.comboBoxModel.getSelectedItemIndex(typeId);

                textShipGroup.setText(orderItemShipGroup.getshipGroupSeqId());
                try {
                    shipmentMethodTypeModel.setSelectedItem(ShipmentMethodTypeSingleton.getShipmentMethodType(orderItemShipGroup.getshipmentMethodTypeId()));
                } catch (Exception ex) {
                    Logger.getLogger(PanelShippingGroup.class.getName()).log(Level.SEVERE, null, ex);
                }

                shipBeforeDate.setDate(orderItemShipGroup.getshipByDate());

                shipAfterDate.setDate(orderItemShipGroup.getshipAfterDate());

                if (orderItemShipGroup.getestimatedShipDate() != null) {
                    txtTermValue.setText(orderItemShipGroup.getestimatedShipDate().toString());// = new BigDecimal();
                }

                txtInstructions.setText(orderItemShipGroup.getshippingInstructions());
                Debug.logInfo("orderItemShipGroup.getcontactMechId(): " + orderItemShipGroup.getcontactMechId(), module);
                if (UtilValidate.isNotEmpty(orderItemShipGroup.getcontactMechId())) {
                    //OrderContactMechComposite comp = order.getOrderContactList().getOrderContactMech(orderItemShipGroup.getcontactMechId());
                    //Debug.logInfo("comp.getContact().getPostalAddress(): " + comp.getContact().getPostalAddress().getAddress1(), module);
                    if (UtilValidate.isNotEmpty(orderItemShipGroup.getcontactMechId())) {
                        for (PostalAddress postalAddress : postalAddressListModel.dataListModel.getList()) {
                            if (postalAddress.getContactMechId().equals(orderItemShipGroup.getcontactMechId())) {
                                postalAddressListModel.setSelectedItem(postalAddress);
                            }
                        }
                    }
                }

                try {
                    panelSupplierPartyIdPicker.textIdField.setText(orderItemShipGroup.getsupplierPartyId());
                    if (UtilValidate.isNotEmpty(orderItemShipGroup.getfacilityId())) {
                        facilityModel.setSelectedItem(FacilitySingleton.getFacility(orderItemShipGroup.getfacilityId()));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PanelShippingGroup.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception ex) {
                Logger.getLogger(PanelShippingGroup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*

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
         }*/
    }

    public void getDialogField() {
        if (orderItemShipGroup != null) {
            if (shipmentMethodTypeModel.comboBoxModel.getSelectedItem() != null) {
                ShipmentMethodType statusType = (ShipmentMethodType) shipmentMethodTypeModel.comboBoxModel.getSelectedItem();
                orderItemShipGroup.setshipmentMethodTypeId(statusType.getshipmentMethodTypeId());
            }

            try {
                //
                if (UtilValidate.isNotEmpty(shipBeforeDate.txtDate)) {
                    orderItemShipGroup.setshipByDate(shipBeforeDate.getTimeStamp());
                }
                //          }
            } catch (Exception ex) {
//                Logger.getLogger(PanelShippingGroup.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (UtilValidate.isNotEmpty(shipAfterDate.getTimeStamp())) {
                    orderItemShipGroup.setshipAfterDate(shipAfterDate.getTimeStamp());
                }
            } catch (Exception ex) {
//                Logger.getLogger(PanelShippingGroup.class.getName()).log(Level.SEVERE, null, ex);
            }

            //  if (UtilValidate.isNotEmpty(txtTermValue.getText())) {
            //     shipInfo.shipEstimate = new BigDecimal(txtTermValue.getText());
            // }
            orderItemShipGroup.setshippingInstructions(txtInstructions.getText());
            if (UtilValidate.isNotEmpty(postalAddressListModel.getSelectedItem())) {
                orderItemShipGroup.setcontactMechId(postalAddressListModel.getSelectedItem().getContactMechId());
            }

            orderItemShipGroup.setsupplierPartyId(panelSupplierPartyIdPicker.textIdField.getText());
            if (UtilValidate.isNotEmpty(facilityModel.getSelectedItem())) {
                orderItemShipGroup.setfacilityId(facilityModel.getSelectedItem().getfacilityId());
            }
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

        panelDetail = new javax.swing.JPanel();
        panelShippingDetail = new javax.swing.JPanel();
        btnSaveTelephone1 = new javax.swing.JButton();
        btnDeleteTelephone1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        panelShippmentMethod = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTermValue = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtInstructions = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        textShipGroup = new javax.swing.JTextField();
        panelShipBeforeAfter = new javax.swing.JPanel();
        panelShipBeforeDate = new javax.swing.JPanel();
        btnNewTelephone = new javax.swing.JButton();
        btnDeleteTelephone = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        panelSupplierPartyId = new javax.swing.JPanel();
        panelFacilityId = new javax.swing.JPanel();
        panelAddressDetail = new javax.swing.JPanel();
        panelGoodIdentificationDetail = new javax.swing.JPanel();
        panelShipGroups = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panelDetail.setPreferredSize(new java.awt.Dimension(700, 350));
        panelDetail.setLayout(new java.awt.GridLayout(1, 0));

        panelShippingDetail.setMinimumSize(new java.awt.Dimension(200, 0));
        panelShippingDetail.setPreferredSize(new java.awt.Dimension(350, 200));

        btnSaveTelephone1.setText("Save");
        btnSaveTelephone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTelephone1ActionPerformed(evt);
            }
        });

        btnDeleteTelephone1.setText("Delete");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Shipment Method:");

        panelShippmentMethod.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelShippmentMethodLayout = new javax.swing.GroupLayout(panelShippmentMethod);
        panelShippmentMethod.setLayout(panelShippmentMethodLayout);
        panelShippmentMethodLayout.setHorizontalGroup(
            panelShippmentMethodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelShippmentMethodLayout.setVerticalGroup(
            panelShippmentMethodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Shipping estimate amount:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Ship Before Date:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText(" Ship After Date:");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Special Instructions:");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Ship Group:");

        panelShipBeforeAfter.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelShipBeforeAfterLayout = new javax.swing.GroupLayout(panelShipBeforeAfter);
        panelShipBeforeAfter.setLayout(panelShipBeforeAfterLayout);
        panelShipBeforeAfterLayout.setHorizontalGroup(
            panelShipBeforeAfterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelShipBeforeAfterLayout.setVerticalGroup(
            panelShipBeforeAfterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelShipBeforeDate.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelShipBeforeDateLayout = new javax.swing.GroupLayout(panelShipBeforeDate);
        panelShipBeforeDate.setLayout(panelShipBeforeDateLayout);
        panelShipBeforeDateLayout.setHorizontalGroup(
            panelShipBeforeDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelShipBeforeDateLayout.setVerticalGroup(
            panelShipBeforeDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        btnNewTelephone.setText("New");
        btnNewTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTelephoneActionPerformed(evt);
            }
        });

        btnDeleteTelephone.setText("Delete");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Supplier:");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Reserve Inventory From Facility:");

        javax.swing.GroupLayout panelSupplierPartyIdLayout = new javax.swing.GroupLayout(panelSupplierPartyId);
        panelSupplierPartyId.setLayout(panelSupplierPartyIdLayout);
        panelSupplierPartyIdLayout.setHorizontalGroup(
            panelSupplierPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        panelSupplierPartyIdLayout.setVerticalGroup(
            panelSupplierPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelShippingDetailLayout = new javax.swing.GroupLayout(panelShippingDetail);
        panelShippingDetail.setLayout(panelShippingDetailLayout);
        panelShippingDetailLayout.setHorizontalGroup(
            panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShippingDetailLayout.createSequentialGroup()
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShippingDetailLayout.createSequentialGroup()
                        .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelShippingDetailLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(2, 2, 2)
                        .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textShipGroup, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(panelSupplierPartyId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelFacilityId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelShippmentMethod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTermValue)
                            .addComponent(panelShipBeforeDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelShipBeforeAfter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtInstructions)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelShippingDetailLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveTelephone1)
                        .addGap(5, 5, 5)
                        .addComponent(btnDeleteTelephone)
                        .addGap(5, 5, 5)
                        .addComponent(btnDeleteTelephone1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNewTelephone)))
                .addContainerGap())
        );

        panelShippingDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteTelephone, btnDeleteTelephone1, btnNewTelephone, btnSaveTelephone1});

        panelShippingDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel15, jLabel8, jLabel9});

        panelShippingDetailLayout.setVerticalGroup(
            panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShippingDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShippingDetailLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel13))
                    .addComponent(textShipGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(panelSupplierPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(panelShippmentMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShippingDetailLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9))
                    .addComponent(txtTermValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelShipBeforeDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(panelShipBeforeAfter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtInstructions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveTelephone1)
                    .addComponent(btnDeleteTelephone)
                    .addGroup(panelShippingDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDeleteTelephone1)
                        .addComponent(btnNewTelephone)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        panelShippingDetailLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelFacilityId, panelShipBeforeAfter, panelShipBeforeDate, panelShippmentMethod, panelSupplierPartyId, textShipGroup, txtInstructions, txtTermValue});

        panelDetail.add(panelShippingDetail);

        javax.swing.GroupLayout panelAddressDetailLayout = new javax.swing.GroupLayout(panelAddressDetail);
        panelAddressDetail.setLayout(panelAddressDetailLayout);
        panelAddressDetailLayout.setHorizontalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        panelAddressDetailLayout.setVerticalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        panelDetail.add(panelAddressDetail);

        add(panelDetail, java.awt.BorderLayout.PAGE_END);

        panelGoodIdentificationDetail.setMinimumSize(new java.awt.Dimension(200, 0));
        panelGoodIdentificationDetail.setPreferredSize(new java.awt.Dimension(455, 100));
        panelGoodIdentificationDetail.setLayout(new java.awt.BorderLayout());

        panelShipGroups.setMinimumSize(new java.awt.Dimension(0, 22));
        panelShipGroups.setPreferredSize(new java.awt.Dimension(284, 28));

        javax.swing.GroupLayout panelShipGroupsLayout = new javax.swing.GroupLayout(panelShipGroups);
        panelShipGroups.setLayout(panelShipGroupsLayout);
        panelShipGroupsLayout.setHorizontalGroup(
            panelShipGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        panelShipGroupsLayout.setVerticalGroup(
            panelShipGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        panelGoodIdentificationDetail.add(panelShipGroups, java.awt.BorderLayout.CENTER);

        add(panelGoodIdentificationDetail, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTelephoneActionPerformed
        /*String s = (String) selectionComboModel.jComboBox.getSelectedItem();//get the selected item
         int indexCount = selectionComboModel.jComboBox.getItemCount();
         ShoppingCart.CartShipInfo shipInfo = order.getShipInfo(indexCount);
         if (UtilValidate.isEmpty(shipInfo.getFacilityId())) {
         shipInfo.setFacilityId(order.getFacilityId());
         }
         GenericValue facilityContactMech = ContactMechWorker.getFacilityContactMechByPurpose(XuiContainer.getSession().getDelegator(), shipInfo.getFacilityId(), UtilMisc.toList("SHIP_ORIG_LOCATION", "PRIMARY_LOCATION"));
         if (facilityContactMech != null) {

         try {
         GenericValue shipAddress = XuiContainer.getSession().getDelegator().findByPrimaryKey("PostalAddress",
         UtilMisc.toMap("contactMechId", facilityContactMech.getString("contactMechId")));
         shipInfo.setContactMechId(facilityContactMech.getString("contactMechId"));
         } catch (GenericEntityException ex) {
         Logger.getLogger(PanelShippingGroup.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         List<String> listStr = new ArrayList<String>();
         List<ShoppingCart.CartShipInfo> shipGroups = order.getShipGroups();

         int i = 0;
         for (ShoppingCart.CartShipInfo shipCart : shipGroups) {
         String index = "";
         i++;
         if (UtilValidate.isNotEmpty(shipCart.getShipGroupSeqId())) {
         index = shipCart.getShipGroupSeqId();
         } else {
         index = Integer.toString(i);
         }
         String str = "Ship Group Nbr " + index;
         listStr.add(str);
         }
         order.setShipmentMethodTypeId(indexCount, "STANDARD");
         order.setCarrierPartyId(indexCount, "_NA_");
         order.setShippingInstructions(indexCount, "");
         order.setGiftMessage(indexCount, "");
         order.setMaySplit(indexCount, Boolean.TRUE);
         order.setIsGift(indexCount, Boolean.FALSE);
         //        order.setShipmentMethodTypeId(indexCount, addressHeader);
         revalidate();

         selectionComboModel.setDataList(listStr);

         if (selectionComboModel.jComboBox.getItemCount() > indexCount) {
         selectionComboModel.jComboBox.setSelectedIndex(indexCount);
         }*/
        /*
         panelGoodIdentificationList.removeAll();
         JPanel panel = layoutEnquiryResults(s, shipInfo);
         //            panelList.put(str, panel);
         panelGoodIdentificationList.add(panel);
         */
    }//GEN-LAST:event_btnNewTelephoneActionPerformed


    private void btnSaveTelephone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTelephone1ActionPerformed
    }//GEN-LAST:event_btnSaveTelephone1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDeleteTelephone;
    public javax.swing.JButton btnDeleteTelephone1;
    public javax.swing.JButton btnNewTelephone;
    public javax.swing.JButton btnSaveTelephone1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelAddressDetail;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelGoodIdentificationDetail;
    private javax.swing.JPanel panelShipBeforeAfter;
    private javax.swing.JPanel panelShipBeforeDate;
    private javax.swing.JPanel panelShipGroups;
    private javax.swing.JPanel panelShippingDetail;
    private javax.swing.JPanel panelShippmentMethod;
    private javax.swing.JPanel panelSupplierPartyId;
    private javax.swing.JTextField textShipGroup;
    private javax.swing.JTextField txtInstructions;
    private javax.swing.JTextField txtTermValue;
    // End of variables declaration//GEN-END:variables
}
