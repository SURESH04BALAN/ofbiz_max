/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.LoadReceiveInventoryCompositeWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReceiveInventoryComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Geo;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class PostalAddressPanel extends javax.swing.JPanel {

    /**
     * Creates new form PostalAddressPanel
     */
    /*final private ListAdapterListModel<Geo> geoListModel = new ListAdapterListModel<Geo>();
     private ListComboBoxModel<Geo> countryComboBoxModel = new ListComboBoxModel<Geo>();
     private ListSelectionModel selectionModel = new DefaultListSelectionModel();
     private ListModelSelection<Geo> listModelSelection = new ListModelSelection<Geo>();

     final private ListAdapterListModel<Geo> geoStateListModel = new ListAdapterListModel<Geo>();
     private ListComboBoxModel<Geo> stateComboBoxModel = new ListComboBoxModel<Geo>();
     private ListSelectionModel selectionStateModel = new DefaultListSelectionModel();
     private ListModelSelection<Geo> listModelStateSelection = new ListModelSelection<Geo>();
     */
    private JGenericComboBoxSelectionModel<Geo> comboCountry;
    private JGenericComboBoxSelectionModel<Geo> comboState;

    void setStateComboBox(Geo countryId) {
        /*
         geoStateListModel.addAll(GeoCountryAssociationSingleton.getValueList(countryId));
         stateComboBoxModel.setListModel(geoStateListModel);
         stateProvinceGeoIdTextField.setModel(stateComboBoxModel);

         selectionStateModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         listModelStateSelection.setListModels(geoStateListModel, selectionStateModel);
         stateComboBoxModel.setListSelectionModel(selectionStateModel);

         GeoListCellRenderer geoStateListCellRenderer = new GeoListCellRenderer(true);
         stateProvinceGeoIdTextField.setRenderer(geoStateListCellRenderer);

         */
        if (UtilValidate.isNotEmpty(countryId)) {
            comboState.setDataList(GeoCountryAssociationSingleton.getValueList(countryId.getgeoId()));
        }
    }

    public PostalAddressPanel() {
        initComponents();

        /*      geoListModel.addAll(GeoCountrySingleton.getValueList());
         countryComboBoxModel.setListModel(geoListModel);
         countryGeoIdTextField.setModel(countryComboBoxModel);
         selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         listModelSelection.setListModels(geoListModel, selectionModel);
         countryComboBoxModel.setListSelectionModel(selectionModel);

         //        panelStatus.setLayout(new BorderLayout());
         //        panelStatus.add(BorderLayout.CENTER, statusItemComboBox);
         GeoListCellRenderer geoListCellRenderer = new GeoListCellRenderer(true);
         countryGeoIdTextField.setRenderer(geoListCellRenderer);
         */
        comboCountry = new JGenericComboBoxSelectionModel<Geo>(panelCountry, GeoCountrySingleton.getValueList());
        comboCountry.jComboBox.addActionListener(null);
        comboCountry.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                if (!event.getValueIsAdjusting()) {
                    setStateComboBox(comboCountry.comboBoxModel.getSelectedItem());
                }
            }
        });
        comboState = new JGenericComboBoxSelectionModel<Geo>(panelState);
    }

    PostalAddress postaladdress;
    private String toName = "";

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public PostalAddress getPostalAddress() {
        return postaladdress;
    }

    public void setPostalAddress(PostalAddress postaladdress) {
        this.postaladdress = postaladdress;
    }

    public void setDialogFields() throws java.text.ParseException {

        cityTextField.setText(OrderMaxUtility.getValidString(postaladdress.getCity()));

        postalCodeTextField.setText(OrderMaxUtility.getValidString(postaladdress.getPostalCode()));

        toNameTextField.setText(OrderMaxUtility.getValidString(postaladdress.getToName()));

//        postalCodeExtTextField.setText(OrderMaxUtility.getValidString(postaladdress.getpostalCodeExt()));
//        stateProvinceGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getstateProvinceGeoId()));
        address2TextField.setText(OrderMaxUtility.getValidString(postaladdress.getAddress2()));

//        countyGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getcountyGeoId()));
        attnNameTextField.setText(OrderMaxUtility.getValidString(postaladdress.getAttnName()));

//        directionsTextField.setText(OrderMaxUtility.getValidString(postaladdress.getDirections()));
//        postalCodeGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getpostalCodeGeoId()));
        contactMechIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getContactMechId()));

        address1TextField.setText(OrderMaxUtility.getValidString(postaladdress.getAddress1()));
        try {
            //        geoPointIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getgeoPointId()));

//        countryGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getcountryGeoId()));
            if (UtilValidate.isNotEmpty(postaladdress.getcountryGeoId())) {
                comboCountry.setSelectedItem(GeoCountrySingleton.getCountry(postaladdress.getcountryGeoId()));

                setStateComboBox(GeoCountrySingleton.getCountry(postaladdress.getcountryGeoId()));
                Debug.logInfo("postaladdress.getstateProvinceGeoId()" + postaladdress.getstateProvinceGeoId(), "module");
                comboState.setSelectedItem(GeoSingleton.getGeo(postaladdress.getstateProvinceGeoId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(PostalAddressPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearDialogFields() {

        cityTextField.setText("");

        postalCodeTextField.setText("");

        toNameTextField.setText("");

//        postalCodeExtTextField.setText(OrderMaxUtility.getValidString(postaladdress.getpostalCodeExt()));
//        stateProvinceGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getstateProvinceGeoId()));
        address2TextField.setText("");

//        countyGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getcountyGeoId()));
        attnNameTextField.setText("");

//        directionsTextField.setText(OrderMaxUtility.getValidString(postaladdress.getDirections()));
//        postalCodeGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getpostalCodeGeoId()));
        contactMechIdTextField.setText("");

        address1TextField.setText("");
        try {
            //        geoPointIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getgeoPointId()));

//        countryGeoIdTextField.setText(OrderMaxUtility.getValidString(postaladdress.getcountryGeoId()));
//            listModelSelection.setSelection(GeoCountrySingleton.getCountry(postaladdress.getcountryGeoId()));
//            setStateComboBox(postaladdress.getcountryGeoId());
//            Debug.logInfo("postaladdress.getstateProvinceGeoId()" + postaladdress.getstateProvinceGeoId(), "module");
            //           listModelStateSelection.setSelection(GeoSingleton.getGeo(postaladdress.getstateProvinceGeoId()));
        } catch (Exception ex) {
            Logger.getLogger(PostalAddressPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getDialogFields() throws java.text.ParseException {

        postaladdress.setCity(OrderMaxUtility.getValidString(cityTextField.getText()));

        postaladdress.setpostalCode(OrderMaxUtility.getValidString(postalCodeTextField.getText()));

        postaladdress.setToName(OrderMaxUtility.getValidString(toNameTextField.getText()));
        Debug.logInfo("toNameTextField: " + toNameTextField.getText() + "postaladdress: " + postaladdress.toString(), "this");
//        postaladdress.setpostalCodeExt(OrderMaxUtility.getValidString(postalCodeExtTextField.getText()));

        postaladdress.setstateProvinceGeoId(comboState.getSelectedItem().getgeoId());

        postaladdress.setAddress2(OrderMaxUtility.getValidString(address2TextField.getText()));

//        postaladdress.setcountyGeoId(listModelStateSelection.getSelection().getgeoId());
        postaladdress.setAttnName(OrderMaxUtility.getValidString(attnNameTextField.getText()));

//        postaladdress.setDirections(OrderMaxUtility.getValidString(directionsTextField.getText()));
//        postaladdress.setpostalCodeGeoId(OrderMaxUtility.getValidString(postalCodeGeoIdTextField.getText()));
        postaladdress.setContactMechId(OrderMaxUtility.getValidString(contactMechIdTextField.getText()));

        postaladdress.setAddress1(OrderMaxUtility.getValidString(address1TextField.getText()));

//        postaladdress.setgeoPointId(OrderMaxUtility.getValidString(geoPointIdTextField.getText()));
        postaladdress.setcountryGeoId(comboCountry.getSelectedItem().getgeoId());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        toNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        attnNameTextField = new javax.swing.JTextField();
        address1TextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        address2TextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        postalCodeTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        contactMechIdTextField = new javax.swing.JTextField();
        btnNewPostalAddress = new javax.swing.JButton();
        btnDeletePostalAddress = new javax.swing.JButton();
        btnSavePostalAddress = new javax.swing.JButton();
        panelCountry = new javax.swing.JPanel();
        panelState = new javax.swing.JPanel();

        jLabel1.setText("To Name:");

        jLabel2.setText("Att Name:");

        jLabel3.setText("Address1:");

        jLabel4.setText("Address2:");

        jLabel5.setText("City:");

        jLabel6.setText("Country:");

        jLabel7.setText("State:");

        jLabel8.setText("Postal Code:");

        jLabel9.setText("Contact Mech Id:");

        btnNewPostalAddress.setText("New");

        btnDeletePostalAddress.setText("Delete");

        btnSavePostalAddress.setText("Save");

        javax.swing.GroupLayout panelCountryLayout = new javax.swing.GroupLayout(panelCountry);
        panelCountry.setLayout(panelCountryLayout);
        panelCountryLayout.setHorizontalGroup(
            panelCountryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );
        panelCountryLayout.setVerticalGroup(
            panelCountryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelStateLayout = new javax.swing.GroupLayout(panelState);
        panelState.setLayout(panelStateLayout);
        panelStateLayout.setHorizontalGroup(
            panelStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );
        panelStateLayout.setVerticalGroup(
            panelStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(attnNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(address1TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(address2TextField, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(cityTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(panelCountry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelState, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(postalCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(contactMechIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNewPostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSavePostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletePostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeletePostalAddress, btnNewPostalAddress, btnSavePostalAddress});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {address1TextField, address2TextField, attnNameTextField, cityTextField, contactMechIdTextField, panelCountry, panelState, postalCodeTextField, toNameTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(toNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(attnNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(address1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(address2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(panelCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(postalCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(contactMechIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewPostalAddress)
                    .addComponent(btnDeletePostalAddress)
                    .addComponent(btnSavePostalAddress))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1TextField;
    private javax.swing.JTextField address2TextField;
    private javax.swing.JTextField attnNameTextField;
    public javax.swing.JButton btnDeletePostalAddress;
    public javax.swing.JButton btnNewPostalAddress;
    public javax.swing.JButton btnSavePostalAddress;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JTextField contactMechIdTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelCountry;
    private javax.swing.JPanel panelState;
    private javax.swing.JTextField postalCodeTextField;
    private javax.swing.JTextField toNameTextField;
    // End of variables declaration//GEN-END:variables
}
