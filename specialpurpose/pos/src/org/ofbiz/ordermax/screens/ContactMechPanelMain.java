/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

import java.awt.BorderLayout;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javolution.util.FastList;
import javolution.util.FastMap;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.PostalAddressListCellRenderer;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.generic.DynamicTableModelInterface;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;
import org.ofbiz.ordermax.generic.GenericValueTablePanel;
import org.ofbiz.ordermax.utility.ContactMechPanel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.utility.PostalAddressPanel;
import org.ofbiz.ordermax.utility.TelecomNumberPanel;
import org.ofbiz.ordermax.entity.ContactMech;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ContactMechPanelMain extends javax.swing.JPanel {

    public static final String module = ContactMechPanelMain.class.getName();
    public static String Location = "POSTAL_ADDRESS";
    static String Fax = "TELECOM_NUMBER";
    public static String Email = "EMAIL_ADDRESS";
    static String Web = "WEB_ADDRESS";
    public static String Phone = "TELECOM_NUMBER";
    public static String InfoString = "EMAIL_ADDRESS";
    //
    static public String POSTALADDRESS = "postalAddress";
    static public String TELECOMNUMBER = "telecomNumber";
    static public String EMAIL = "email";
    HashMap<String, HashMap<String, ContactMechGeneric>> genericValueMap = new HashMap<String, HashMap<String, ContactMechGeneric>>();
    HashMap<String, String> contactMechTypePurpose = new HashMap<String, String>();
    List<Map<String, Object>> mechListVal = new ArrayList<Map<String, Object>>();
    protected List<String> comboPurposeModelBidingCombo = FastList.newInstance();
    protected List<String> comboTelePurposeModelBidingCombo = FastList.newInstance();
    protected List<String> comboEmailPurposeModelBidingCombo = FastList.newInstance();
//    protected GenericValueTablePanel addressTablePanel = null;
    protected GenericValueTablePanel telephoneTablePanel = null;
    protected GenericValueTablePanel emailTablePanel = null;
    protected JPanel addressCardPanel = null;
    protected JPanel telecomCardPanel = null;
    protected JPanel emailCardPanel = null;
    protected XuiSession session = null;
//    java.awt.Frame parentFrame = null;
    JPanel addressTablePanel = null;
//    String partyId = "CUST";
    final private ListAdapterListModel<PostalAddress> postalAddressListModel = new ListAdapterListModel<PostalAddress>();

    private JList<PostalAddress> postalAddressList = new JList<PostalAddress>(postalAddressListModel);
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private ListModelSelection<PostalAddress> listModelSelection = new ListModelSelection<PostalAddress>();
    org.ofbiz.ordermax.party.PostalAddressPanel postalAddressPanel = new org.ofbiz.ordermax.party.PostalAddressPanel();
    org.ofbiz.ordermax.party.ContactMechPurposeTypeSelectionPanel panelAddressPurpose = null;
    public void setPersonList(ListAdapterListModel<PostalAddress> personListModel) {
        postalAddressList.setModel(personListModel);
        listModelSelection.setListModels(personListModel, selectionModel);
    }

    /**
     * Creates new form ContactMechPanel
     */
    public ContactMechPanelMain( XuiSession session) {
        initComponents();
        Delegator delegator = session.getDelegator();
        //parentFrame = parent;
        this.session = session;

        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postalAddressList.setSelectionModel(selectionModel);

        ListCellRenderer<PostalAddress> postalAddressListCellRenderer = new PostalAddressListCellRenderer();
        postalAddressList.setCellRenderer(postalAddressListCellRenderer);
        postalAddressList.setEnabled(true);
        postalAddressList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i) ) {
                            System.out.println(" " + i);
                            setPostalAddressDetail(i);
                            break;
                        }
                    }

                }
            }
        });
        
        addressTablePanel = new JPanel();
        addressTablePanel.setLayout(new BorderLayout());
        addressTablePanel.add(BorderLayout.CENTER, postalAddressList);
        panelAddressList.setLayout(new BorderLayout());
        panelAddressList.add(BorderLayout.CENTER, addressTablePanel);

        panelAddressPurpose = new org.ofbiz.ordermax.party.ContactMechPurposeTypeSelectionPanel("POSTAL_ADDRESS");
        panelPostalAddressPurpose.setLayout(new BorderLayout());
        panelPostalAddressPurpose.add(BorderLayout.CENTER, panelAddressPurpose);  
        
        panelAddressPurpose.btnAddPurpose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactMechPurposeType contactMechPurposeType = panelAddressPurpose.getSelectedPurposeType();                
                
            }
        }
        );
        
        //      addressTablePanel = new GenericValueTablePanel(parentFrame, session, PostalAddress.ColumnNameId);
        telephoneTablePanel = new GenericValueTablePanel(session, TelecomNumber.ColumnNameId);
        emailTablePanel = new GenericValueTablePanel(session, ContactMech.ColumnNameId);

//        OrderMaxUtility.addAPanelToPanel(addressTablePanel, panelAddress);
//        OrderMaxUtility.addAPanelToPanel(telephoneTablePanel, panelTelephone);
//        OrderMaxUtility.addAPanelToPanel(emailTablePanel, panelEmail);
        setupNewContactMap();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelAddressList, "Postal Address List");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelAddressDetail, "Postal Address Details");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPostalAddressPurpose, "Postal Address Purpose");        
        
    }

    public void setPostalAddressDetail(int index) {
        if (index < postalAddressListModel.getSize()) {
            System.out.println(" " + i);
            PostalAddress opt = postalAddressListModel.getElementAt(index);
            postalAddressPanel.setPostalAddress(opt);
            try {
                postalAddressPanel.setDialogFields();
            } catch (ParseException ex) {
                Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("selected index :" + postalAddressList.getSelectedIndex() + "[[" + opt.getAddress1() + "]]");
        }
    }

    static int i = 0;

    static public ContactMechGeneric createContactMechGeneric(final String purposeId, final String mechTypeId) {

        ContactMechGeneric newUiMech = new ContactMechGeneric(purposeId);
        newUiMech.type = mechTypeId;
        if (mechTypeId.toUpperCase().equals(Location)) {
            newUiMech.uiPanel = new PostalAddressPanel();
        } else if (mechTypeId.toUpperCase().equals(Phone)) {
            newUiMech.uiPanel = new TelecomNumberPanel();
        } else if (mechTypeId.toUpperCase().equals(Fax)) {
            newUiMech.uiPanel = new TelecomNumberPanel();
        } else if (mechTypeId.toUpperCase().equals(Email)) {
            newUiMech.uiPanel = new ContactMechPanel();
        } else if (mechTypeId.toUpperCase().equals(Web)) {
            newUiMech.uiPanel = new ContactMechPanel();
        } else {
            newUiMech.uiPanel = new ContactMechPanel();
        }

        try {
            if (newUiMech.uiPanel != null) {
                GenericValueObjectInterface uiObject = newUiMech.uiPanel.createUIObject(null);
                newUiMech.uiPanel.changeUIObject(uiObject);
                newUiMech.uiPanel.setUIFields();
            }
        } catch (Exception ex) {
            Logger.getLogger(ContactMechPanelMain.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return newUiMech;
    }

    public void setContactMechs(List<Map<String, Object>> machList) {
        //Debug.logInfo("setContactMechs : 1", module);
        mechListVal.clear();
        mechListVal.addAll(machList);
        List<GenericValue> addressList = FastList.newInstance();
        List phoneList = FastList.newInstance();
        List emaiList = FastList.newInstance();
        for (Map<String, Object> mapList : machList) {
            //Debug.logInfo("setContactMechs : 2", module);
            if (mapList.containsKey("contactMech")) {
                GenericValue contactMech = (GenericValue) mapList.get("contactMech");
                String contactMechTypeId = contactMech.getString("contactMechTypeId");
                //Debug.logInfo("contactMechTypeId : " + contactMechTypeId, module);
                //Debug.logInfo("setContactMechs : 3", module);
                if (contactMechTypeId.equals(Location)
                        && mapList.containsKey("postalAddress")) {
                    //Debug.logInfo("setContactMechs : 4", module);
                    if (mapList.get("postalAddress") != null) {
                        //Debug.logInfo("setContactMechs : 5", module);
                        GenericValue postalMech = (GenericValue) mapList.get("postalAddress");
                        addressList.add(postalMech);
                        //Debug.logInfo("setContactMechs : 6", module);
                        List<GenericValue> mechPurposes = (List<GenericValue>) mapList.get("partyContactMechPurposes"); //addressList.add(mapList.get("postalAddress"));
                        for (GenericValue mechPurpose : mechPurposes) {
                            //Debug.logInfo("setContactMechs : 7", module);
                            String purposeTypeId = mechPurpose.getString("contactMechPurposeTypeId");
                            if (purposeTypeId != null) {
                                ContactMechGeneric mechVal = getContactMechGeneric(purposeTypeId);
                                try {
                                    GenericValueObjectInterface postalAddress = mechVal.uiPanel.createUIObject(postalMech);
                                    //Debug.logInfo("setContactMechs : 8", module);
                                    mechVal.uiPanel.changeUIObject(postalAddress);
                                    mechVal.uiPanel.setUIFields();
                                    mechVal.uiPanel.setIsModified(false);

                                } catch (Exception ex) {
                                    Logger.getLogger(ContactMechPanelMain.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            }
                        }
                    }
                } else if (contactMechTypeId.equals(Phone) && mapList.containsKey("telecomNumber")) {
                    if (mapList.get("telecomNumber") != null) {
                        GenericValue postalMech = (GenericValue) mapList.get("telecomNumber");
                        phoneList.add(postalMech);
                        List<GenericValue> mechPurposes = (List<GenericValue>) mapList.get("partyContactMechPurposes"); //addressList.add(mapList.get("postalAddress"));
                        for (GenericValue mechPurpose : mechPurposes) {
                            String purposeTypeId = mechPurpose.getString("contactMechPurposeTypeId");
                            if (purposeTypeId != null) {
                                ContactMechGeneric mechVal = getContactMechGeneric(purposeTypeId);
                                try {
                                    GenericValueObjectInterface postalAddress = null;
                                    postalAddress = mechVal.uiPanel.createUIObject(postalMech);
                                    mechVal.uiPanel.changeUIObject(postalAddress);
                                    mechVal.uiPanel.setUIFields();
                                    mechVal.uiPanel.setIsModified(false);
                                } catch (Exception ex) {
                                    Logger.getLogger(ContactMechPanelMain.class
                                            .getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            }
                        }

                    }
                } else if (contactMechTypeId.equals(Email)) {
                    //Debug.logInfo("email found contactMechTypeId : " + contactMechTypeId, module);
                    GenericValue postalMech = (GenericValue) mapList.get("contactMech");
                    emaiList.add(postalMech);
                    List<GenericValue> mechPurposes = (List<GenericValue>) mapList.get("partyContactMechPurposes"); //addressList.add(mapList.get("postalAddress"));
                    for (GenericValue mechPurpose : mechPurposes) {
                        String purposeTypeId = mechPurpose.getString("contactMechPurposeTypeId");
                        if (purposeTypeId != null) {
                            ContactMechGeneric mechVal = getContactMechGeneric(purposeTypeId);
                            try {
                                GenericValueObjectInterface postalAddress = null;
                                postalAddress = mechVal.uiPanel.createUIObject(postalMech);
                                mechVal.uiPanel.changeUIObject(postalAddress);
                                mechVal.uiPanel.setUIFields();
                                mechVal.uiPanel.setIsModified(false);
                            } catch (Exception ex) {
                                Logger.getLogger(ContactMechPanelMain.class
                                        .getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        }
                    }

                }
            }
        }

        postalAddressListModel.clear();
        for (GenericValue val : addressList) {
            postalAddressListModel.add(new PostalAddress(val));
        }
        setPostalAddressDetail(0);
        /*        
         addressTablePanel.setupOrderTableList(addressList);
         addressTablePanel.getJtable().getSelectionModel().addListSelectionListener(
         new ContactMechSelListner(addressTablePanel.getJtable(), addressTablePanel.getTableModel(), comboPurposeId, comboPurposeModelBidingCombo));
         */
        telephoneTablePanel.setupOrderTableList(phoneList);
        telephoneTablePanel.getJtable().getSelectionModel().addListSelectionListener(
                new ContactMechSelListner(telephoneTablePanel.getJtable(), telephoneTablePanel.getTableModel(), comboTelephone, comboTelePurposeModelBidingCombo));

        emailTablePanel.setupOrderTableList(emaiList);
        emailTablePanel.getJtable().getSelectionModel().addListSelectionListener(
                new ContactMechSelListner(emailTablePanel.getJtable(), emailTablePanel.getTableModel(), comboEmail, comboEmailPurposeModelBidingCombo));


        comboTelephone.setSelectedIndex(0);
        comboEmail.setSelectedIndex(0);

        this.revalidate();
        this.repaint();

    }

    static public class ContactMechGeneric {

        public String type;
        String contachMechPurposeId;
        Map<String, Object> genericValueMap;
        GenericValuePanelInterfaceOrderMax uiPanel;

        public ContactMechGeneric(String id) {
            contachMechPurposeId = id;
            genericValueMap = FastMap.newInstance();
        }
    }

    class ContactMechSelListner implements ListSelectionListener {

        JTable table = null;
        DynamicTableModelInterface tModel = null;
        JComboBox comboBox = null;
        List<String> comboBiding = null;

        ContactMechSelListner(JTable table, DynamicTableModelInterface tModel, JComboBox combo, List<String> comboBiding) {
            this.table = table;
            this.tModel = tModel;
            this.comboBox = combo;
            this.comboBiding = comboBiding;
        }

        public void valueChanged(ListSelectionEvent e) {

            if (!e.getValueIsAdjusting() && tModel != null && table != null && comboBox != null) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //Debug.logInfo(" tModel.getSize() " + table.getRowCount(), module);
                if (lsm.isSelectionEmpty()) {
                    System.out.println(" <none>");
                } else {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i) && i < tModel.getRowCount()) {
                            System.out.println(" " + i);
                            GenericValue genVal = tModel.getRowGenericData(i);
                            if (genVal != null) {
                                try {
                                    //                              try {
                                    String mechId = genVal.getString("contactMechId");

                                    List<GenericValue> contactMechPurposeList = getPartyContactMechPurposes(mechId);
//                                    GenericValue postalMech = getContactMechPostal(mechId);
                                    System.out.println("Selection Changed: " + contactMechPurposeList.get(0).getString("contactMechPurposeTypeId"));
                                    comboBox.setSelectedIndex(comboBiding.indexOf(contactMechPurposeList.get(0).getString("contactMechPurposeTypeId")));
                                } catch (Exception ex) {
                                    Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelAddress = new javax.swing.JPanel();
        panelAddressList = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelAddressDetail = new javax.swing.JPanel();
        panelPostalAddressPurpose = new javax.swing.JPanel();
        panelTelephone = new javax.swing.JPanel();
        panelTeleSelePanel = new javax.swing.JPanel();
        maritalStatusLabel1 = new javax.swing.JLabel();
        comboTelephone = new javax.swing.JComboBox();
        panelEmail = new javax.swing.JPanel();
        panelEmailSelec = new javax.swing.JPanel();
        maritalStatusLabel2 = new javax.swing.JLabel();
        comboEmail = new javax.swing.JComboBox();
        panelWeb = new javax.swing.JPanel();

        panelAddress.setLayout(new java.awt.BorderLayout());

        panelAddressList.setLayout(new java.awt.GridLayout());
        panelAddress.add(panelAddressList, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout());

        panelAddressDetail.setPreferredSize(new java.awt.Dimension(424, 300));

        javax.swing.GroupLayout panelAddressDetailLayout = new javax.swing.GroupLayout(panelAddressDetail);
        panelAddressDetail.setLayout(panelAddressDetailLayout);
        panelAddressDetailLayout.setHorizontalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
        );
        panelAddressDetailLayout.setVerticalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel1.add(panelAddressDetail);

        javax.swing.GroupLayout panelPostalAddressPurposeLayout = new javax.swing.GroupLayout(panelPostalAddressPurpose);
        panelPostalAddressPurpose.setLayout(panelPostalAddressPurposeLayout);
        panelPostalAddressPurposeLayout.setHorizontalGroup(
            panelPostalAddressPurposeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
        );
        panelPostalAddressPurposeLayout.setVerticalGroup(
            panelPostalAddressPurposeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel1.add(panelPostalAddressPurpose);

        panelAddress.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Address", panelAddress);

        maritalStatusLabel1.setText("Telephone Change:"); // NOI18N

        comboTelephone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTelephoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTeleSelePanelLayout = new javax.swing.GroupLayout(panelTeleSelePanel);
        panelTeleSelePanel.setLayout(panelTeleSelePanelLayout);
        panelTeleSelePanelLayout.setHorizontalGroup(
            panelTeleSelePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTeleSelePanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(maritalStatusLabel1)
                .addGap(18, 18, 18)
                .addComponent(comboTelephone, 0, 291, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTeleSelePanelLayout.setVerticalGroup(
            panelTeleSelePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleSelePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(maritalStatusLabel1)
                .addComponent(comboTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelTelephoneLayout = new javax.swing.GroupLayout(panelTelephone);
        panelTelephone.setLayout(panelTelephoneLayout);
        panelTelephoneLayout.setHorizontalGroup(
            panelTelephoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTelephoneLayout.createSequentialGroup()
                .addComponent(panelTeleSelePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTelephoneLayout.setVerticalGroup(
            panelTelephoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTelephoneLayout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(panelTeleSelePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TelePhone", panelTelephone);

        maritalStatusLabel2.setText("Email To Change:"); // NOI18N

        comboEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEmailSelecLayout = new javax.swing.GroupLayout(panelEmailSelec);
        panelEmailSelec.setLayout(panelEmailSelecLayout);
        panelEmailSelecLayout.setHorizontalGroup(
            panelEmailSelecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmailSelecLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(maritalStatusLabel2)
                .addGap(18, 18, 18)
                .addComponent(comboEmail, 0, 302, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEmailSelecLayout.setVerticalGroup(
            panelEmailSelecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailSelecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(maritalStatusLabel2)
                .addComponent(comboEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelEmailLayout = new javax.swing.GroupLayout(panelEmail);
        panelEmail.setLayout(panelEmailLayout);
        panelEmailLayout.setHorizontalGroup(
            panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailLayout.createSequentialGroup()
                .addComponent(panelEmailSelec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEmailLayout.setVerticalGroup(
            panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailLayout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(panelEmailSelec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Email", panelEmail);

        javax.swing.GroupLayout panelWebLayout = new javax.swing.GroupLayout(panelWeb);
        panelWeb.setLayout(panelWebLayout);
        panelWebLayout.setHorizontalGroup(
            panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );
        panelWebLayout.setVerticalGroup(
            panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Web", panelWeb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Address");
    }// </editor-fold>//GEN-END:initComponents

    private ContactMechGeneric getContactMechGeneric(String purposeId) {
        String mechType = contactMechTypePurpose.get(purposeId);
        HashMap<String, ContactMechGeneric> mechPurposeVal = genericValueMap.get(mechType);
        return mechPurposeVal.get(purposeId);
    }

    private List<GenericValue> getPartyContactMechPurposes(String mechId) {
        List<GenericValue> mechPurposes = FastList.newInstance();

        for (Map<String, Object> mapList : mechListVal) {

            if (mapList.containsKey("partyContactMechPurposes")) {
                mechPurposes = (List<GenericValue>) mapList.get("partyContactMechPurposes"); //addressList.add(mapList.get("postalAddress"));
                for (GenericValue mechPurpose : mechPurposes) {
                    String tmpMechId = mechPurpose.getString("contactMechId");
                    if (tmpMechId != null && tmpMechId.equals(mechId)) {
                        return mechPurposes;
                    }
                }
            }
        }
        return mechPurposes;
    }

    public void createNewContactMech() {
        setupNewContactMap();
    }

    public void saveContactMech(String partyId) {

        for (Entry<String, HashMap<String, ContactMechGeneric>> entryDept : genericValueMap.entrySet()) {
            HashMap<String, ContactMechGeneric> mapMechGeneric = entryDept.getValue();

            for (Entry<String, ContactMechGeneric> entryBrand : mapMechGeneric.entrySet()) {
                ContactMechGeneric mechGeneric = entryBrand.getValue();
                //Debug.logInfo("Save: " + mechGeneric.contachMechPurposeId + " " + mechGeneric.type, module);
                if (mechGeneric.uiPanel.isModified()) {
                    try {
                        //Debug.logInfo("Save Modified: " + mechGeneric.contachMechPurposeId + " " + mechGeneric.type, module);
                        Delegator delegator = session.getDelegator();
                        GenericValueObjectInterface mechObj = mechGeneric.uiPanel.getUIObject();
                        GenericValue detailValue = mechObj.getGenericValueObj();
                        boolean isNew = false;
                        if (mechObj.isGenericValueSet() == false) {
                            detailValue = mechObj.createNewGenericValueObj(session.getDelegator());
                            isNew = true;
                        }
                        mechGeneric.uiPanel.getUIFields();
                        mechObj.getGenericValue();
                        if (isNew) {
                            //Debug.logInfo("Save Modified new : " + mechGeneric.contachMechPurposeId + " " + mechGeneric.type, module);
                            PosProductHelper.createNewContectMech(partyId, mechGeneric.contachMechPurposeId, mechGeneric.type, detailValue, delegator);
                            mechGeneric.uiPanel.setIsModified(false);
                        } else {
                            try {

                                delegator.store(detailValue);
                                mechGeneric.uiPanel.setIsModified(false);
                                //Debug.logInfo("Save Modified store : " + mechGeneric.contachMechPurposeId + " " + mechGeneric.type, module);
                            } catch (GenericEntityException ex) {
                                Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
    }

    public void addComponentToPane(JPanel tablePanel, JPanel comboDetail,
            JPanel cardPanel, JPanel containerPanel, JComboBox comboBox, List<String> modelBindingCombo) {

        containerPanel.removeAll();
        for (int i = 0; i < comboBox.getModel().getSize(); i++) {
            String purposeId = modelBindingCombo.get(i);
            ContactMechGeneric mechVal = getContactMechGeneric(purposeId);
            if (mechVal != null) {
                cardPanel.add(mechVal.uiPanel.getContainerPanel(), comboBox.getModel().getElementAt(i).toString());
            }
        }

        OrderMaxUtility.addThreePanelToPanel(tablePanel, comboDetail, cardPanel, containerPanel);
    }

    public void setupNewContactMap() {

        Delegator delegator = session.getDelegator();

        DefaultComboBoxModel comboPurposeModel = new DefaultComboBoxModel();
        DefaultComboBoxModel comboTelPurposeModel = new DefaultComboBoxModel();
        DefaultComboBoxModel comboEmailPurposeModel = new DefaultComboBoxModel();
        genericValueMap.clear();
        contactMechTypePurpose.clear();
        List<GenericValue> listTypeValue = PosProductHelper.getGenericValueLists("ContactMechType", delegator);
        for (GenericValue id : listTypeValue) {
            HashMap<String, ContactMechGeneric> mechPurposeVal = new HashMap<String, ContactMechGeneric>();
            genericValueMap.put(id.getString("contactMechTypeId"), mechPurposeVal);
        }

        List<GenericValue> listTypePurposeValue = PosProductHelper.getGenericValueLists("ContactMechTypePurpose", delegator);
        for (GenericValue id : listTypePurposeValue) {
            contactMechTypePurpose.put(id.getString("contactMechPurposeTypeId"), id.getString("contactMechTypeId"));
            //Debug.logInfo(" ContactMechTypePurpose: " + id.getString("contactMechPurposeTypeId") + "  " + id.getString("contactMechTypeId"), "ContactMechTypePurpose");
        }

        List<GenericValue> listValue = PosProductHelper.getGenericValueLists("ContactMechPurposeType", delegator);
        for (GenericValue id : listValue) {
            String purposeId = id.getString("contactMechPurposeTypeId");
            String description = id.getString("description");
            //Debug.logInfo(" ContactMechPurposeType: " + id.getString("contactMechPurposeTypeId") + "  " + id.getString("description") + " Map:: " + contactMechTypePurpose.get(purposeId), "ContactMechTypePurpose");
            if (contactMechTypePurpose.get(purposeId) == null) {
                continue;
            }
            ContactMechGeneric mechVal = createContactMechGeneric(purposeId, contactMechTypePurpose.get(purposeId));

            HashMap<String, ContactMechGeneric> mechPurposeVal = genericValueMap.get(mechVal.type);
            mechPurposeVal.put(purposeId, mechVal);
            if (mechVal.type.equals(Location)) {
                comboPurposeModelBidingCombo.add(purposeId);
                comboPurposeModel.addElement(description);
            } else if (mechVal.type.equals(Phone)) {
                comboTelePurposeModelBidingCombo.add(purposeId);
                comboTelPurposeModel.addElement(description);
            } else if (mechVal.type.equals(Email)) {
                comboEmailPurposeModelBidingCombo.add(purposeId);
                comboEmailPurposeModel.addElement(description);

            } else if (mechVal.type.equals(Web)) {
                comboEmailPurposeModelBidingCombo.add(purposeId);
                comboEmailPurposeModel.addElement(description);
            }
        }
//        comboPurposeId.setModel(comboPurposeModel);
        comboTelephone.setModel(comboTelPurposeModel);
        comboEmail.setModel(comboEmailPurposeModel);

        panelAddressList.setLayout(new BorderLayout());
        panelAddressList.add(BorderLayout.CENTER, addressTablePanel);

        panelAddressDetail.setLayout(new BorderLayout());
        panelAddressDetail.add(BorderLayout.CENTER, postalAddressPanel);

//        addressCardPanel = new JPanel(new CardLayout());
//        addComponentToPane(addressTablePanel, postalAddressPanel,
//                addressCardPanel, panelAddress, comboPurposeId, comboPurposeModelBidingCombo);
        telecomCardPanel = new JPanel(new CardLayout());
        addComponentToPane(telephoneTablePanel, panelTeleSelePanel,
                telecomCardPanel, panelTelephone, comboTelephone, comboTelePurposeModelBidingCombo);

        emailCardPanel = new JPanel(new CardLayout());
        addComponentToPane(emailTablePanel, panelEmailSelec,
                emailCardPanel, panelEmail, comboEmail, comboEmailPurposeModelBidingCombo);

    }

    public void comboPostalChanged(String desc) {
        if (addressCardPanel != null) {
            CardLayout cl = (CardLayout) (addressCardPanel.getLayout());
            cl.show(addressCardPanel, desc);
        }
    }

    public void comboTelecomChanged(String desc) {
        if (telecomCardPanel != null) {
            CardLayout cl = (CardLayout) (telecomCardPanel.getLayout());
            cl.show(telecomCardPanel, desc);
        }
    }

    public void comboEmailChanged(String desc) {
        if (emailCardPanel != null) {
            CardLayout cl = (CardLayout) (emailCardPanel.getLayout());
            cl.show(emailCardPanel, desc);
        }
    }

    private void comboTelephoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTelephoneActionPerformed
        String desc = (String) comboTelephone.getSelectedItem();
        //Debug.logInfo("comboTelephoneActionPerformed: clicked", "purposeId");
        if (UtilValidate.isNotEmpty(desc)) {
            comboTelecomChanged(desc);
        }
    }//GEN-LAST:event_comboTelephoneActionPerformed

    private void comboEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmailActionPerformed
        //Debug.logInfo("comboEmailActionPerformed: clicked", "purposeId");
        String desc = (String) comboEmail.getSelectedItem();
        if (UtilValidate.isNotEmpty(desc)) {
            String purposeId = comboEmailPurposeModelBidingCombo.get(comboEmail.getSelectedIndex());
            if (UtilValidate.isNotEmpty(desc)) {
                comboEmailChanged(desc);
            }
        }
    }//GEN-LAST:event_comboEmailActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboEmail;
    private javax.swing.JComboBox comboTelephone;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel maritalStatusLabel1;
    private javax.swing.JLabel maritalStatusLabel2;
    private javax.swing.JPanel panelAddress;
    private javax.swing.JPanel panelAddressDetail;
    private javax.swing.JPanel panelAddressList;
    private javax.swing.JPanel panelEmail;
    private javax.swing.JPanel panelEmailSelec;
    private javax.swing.JPanel panelPostalAddressPurpose;
    private javax.swing.JPanel panelTeleSelePanel;
    private javax.swing.JPanel panelTelephone;
    private javax.swing.JPanel panelWeb;
    // End of variables declaration//GEN-END:variables
}
