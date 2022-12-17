/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import org.ofbiz.ordermax.screens.*;
import java.awt.BorderLayout;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javolution.util.FastList;
import mvc.controller.LoadAccountWorker;
import mvc.controller.SavePostalAddressAction;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.PostalAddressListCellRenderer;
import mvc.model.list.TelecomNumberListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Contact;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeComposite;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeList;
import org.ofbiz.ordermax.generic.GenericValueTablePanel;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;
import org.ofbiz.ordermax.entity.PartyContactMechPurpose;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class PartyContactMechPanel extends javax.swing.JPanel {

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
    protected HashMap<String, String> contactMechTypePurpose = new HashMap<String, String>();
    protected List<Map<String, Object>> mechListVal = new ArrayList<Map<String, Object>>();
    protected List<String> comboPurposeModelBidingCombo = FastList.newInstance();
    protected List<String> comboTelePurposeModelBidingCombo = FastList.newInstance();
    protected List<String> comboEmailPurposeModelBidingCombo = FastList.newInstance();
//    protected GenericValueTablePanel addressTablePanel = null;
    protected JPanel telephoneTablePanel = null;
    protected GenericValueTablePanel emailTablePanel = null;
    protected JPanel addressCardPanel = null;
    protected JPanel telecomCardPanel = null;
    protected JPanel emailCardPanel = null;
    protected XuiSession session = null;
    //java.awt.Frame parentFrame = null;
    JPanel addressTablePanel = null;

    static public enum ContechPanelAction {

        SAVE_POSTAL_ADDREES,
        DELETE_POSTAL_ADDRESS,
        SAVE_TELECOM,
        DELETE_TELECOM,
        SAVE_POSTAL_PURPOSE,
        DELETE_POSTAL_PURPOSE,
        SAVE_TELECOM_PURPOSE,
        DELETE_TELECOM_PURPOSE,
    };
//    String partyId = "CUST";
    final private ListAdapterListModel<PostalAddress> postalAddressListModel = new ListAdapterListModel<PostalAddress>();
    private JList<PostalAddress> postalAddressList = new JList<PostalAddress>(postalAddressListModel);
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
//    private ListModelSelection<PostalAddress> listModelSelection = new ListModelSelection<PostalAddress>();

    final private ListAdapterListModel<TelecomNumber> telecomNumberListModel = new ListAdapterListModel<TelecomNumber>();
    private JList<TelecomNumber> telecomNumberList = new JList<TelecomNumber>(telecomNumberListModel);
    private ListSelectionModel selectionTelecomNumberModel = new DefaultListSelectionModel();
    private ListModelSelection<TelecomNumber> listModelTelecomNumberSelection = new ListModelSelection<TelecomNumber>();

    org.ofbiz.ordermax.party.PostalAddressPanel postalAddressPanel = new org.ofbiz.ordermax.party.PostalAddressPanel();
    org.ofbiz.ordermax.party.TelephonePanel telephonePanel = new org.ofbiz.ordermax.party.TelephonePanel();
    private PartyContactMechCompositeList partyContactMechCompositeListData = null;
    org.ofbiz.ordermax.party.ContactMechPurposeTypeSelectionPanel panelAddressPurpose = null;
    org.ofbiz.ordermax.party.ContactMechPurposeTypeSelectionPanel panelTelephonePurpose = null;
    SavePostalAddressAction savePostalAddressAction = null;
    SavePostalAddressAction saveTelephoneAction = null;
    final ListAdapterListModel<PartyContactMechComposite> postalAddressToSaveListModel = new ListAdapterListModel<PartyContactMechComposite>();
    final ListAdapterListModel<PartyContactMechComposite> telephoneNumberToSaveListModel = new ListAdapterListModel<PartyContactMechComposite>();

    public PartyContactMechCompositeList getPartyContactMechCompositeListData() {
        return partyContactMechCompositeListData;
    }

    public void setPartyContactMechCompositeListData(PartyContactMechCompositeList partyContactMechCompositeListData) {
        this.partyContactMechCompositeListData = partyContactMechCompositeListData;
        postalAddressListModel.clear();
        telecomNumberListModel.clear();
        panelAddressPurpose.clearDialogFields();
        postalAddressPanel.clearDialogFields();

        ListAdapterListModel<PartyContactMechComposite> tempAddressPurposeListModel = new ListAdapterListModel<PartyContactMechComposite>();
        for (PartyContactMechComposite partyContactMechComposite : partyContactMechCompositeListData.getList()) {
            if ("POSTAL_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
                postalAddressListModel.add(partyContactMechComposite.getContact().getPostalAddress());
            } else if ("TELECOM_NUMBER".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
                telecomNumberListModel.add(partyContactMechComposite.getContact().getTelecomNumber());
            }
        }
        if (postalAddressListModel.getSize() > 0) {
            setPostalAddressDetail(0);
        } else {
            createNewPostalContactMech();
        }

        if (telecomNumberListModel.getSize() > 0) {
            setTelecomNumberDetail(0);
        } else {
            createNewTelephoneContactMech();
        }

        this.revalidate();
        this.repaint();

        //panelAddressPurpose.setAddressPurpose(tempAddressPurposeListModel);
    }
    /*
     private void setPostalAddressList(ListAdapterListModel<PostalAddress> personListModel) {
     postalAddressList.setModel(personListModel);
     listModelSelection.setListModels(personListModel, selectionModel);
     }

     private void setTelecomNumberList(ListAdapterListModel<TelecomNumber> telecomNumberListModel) {
     telecomNumberList.setModel(telecomNumberListModel);
     listModelTelecomNumberSelection.setListModels(telecomNumberListModel, selectionTelecomNumberModel);
     }
     */
    private Account partyAccount = null;

    public Account getPartyAccount() {
        return partyAccount;
    }

    public void setPartyAccount(Account partyAccount) {
        this.partyAccount = partyAccount;
    }

    /**
     * Creates new form ContactMechPanel
     */
    public PartyContactMechPanel(XuiSession session) {
        initComponents();
        Delegator delegator = session.getDelegator();

        this.session = session;
        createPostalAddressUI();
        createTelephoneUI();
//        createNewPostalContactMech();
    }

    boolean isNewPostalAddress = false;
    boolean isNewTele = false;

    protected void createPostalAddressUI() {

        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postalAddressList.setSelectionModel(selectionModel);

        ListCellRenderer<PostalAddress> postalAddressListCellRenderer = new PostalAddressListCellRenderer();
        postalAddressList.setCellRenderer(postalAddressListCellRenderer);
        postalAddressList.setEnabled(true);
        postalAddressList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                Debug.logInfo("postalAddressList: 1", module);
                ListSelectionModel lsm = (ListSelectionModel) selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    Debug.logInfo("postalAddressList: 2", module);
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            Debug.logInfo("postalAddressList: 3 + " + i, module);
                            panelAddressPurpose.clearDialogFields();
                            setPostalAddressDetail(i);
                            isNewPostalAddress = false;
                            break;
                        }
                    }
                }
            }
        });

        //save
        savePostalAddressAction = new SavePostalAddressAction(SavePostalAddressAction.nameStr, session, null, postalAddressToSaveListModel);
        postalAddressPanel.btnSavePostalAddress.addActionListener(savePostalAddressAction);
        savePostalAddressAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ScreenAction.BEFORE_SAVE.equals(e.getActionCommand())) {
                    Debug.logInfo(ScreenAction.BEFORE_SAVE, module);
                    try {
                        postalAddressPanel.getDialogFields();
                        setPartyId();
                    } catch (ParseException ex) {
                        Logger.getLogger(PartyContactMechPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Debug.logInfo(ScreenAction.AFTER_SAVE, module);
                    try {
                        postalAddressPanel.setDialogFields();
                        if (isNewPostalAddress) {
                            postalAddressListModel.add(postalAddressPanel.getPostalAddress());
                            mergeSaveList(postalAddressToSaveListModel);
                            isNewPostalAddress = false;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(PartyContactMechPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        postalAddressPanel.btnNewPostalAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewPostalContactMech();
            }
        });

        postalAddressPanel.btnDeletePostalAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*            postalAddressToSaveListModel.clear();
                 PartyContactMechComposite pcmc = LoadAccountWorker.createPartyContactMechComposite(partyAccount.getParty().getpartyId(),
                 Contact.ContactMechTypeId.POSTAL_ADDRESS);
                 postalAddressPanel.clearDialogFields();
                 postalAddressPanel.setPostalAddress(pcmc.getContact().getPostalAddress());

                 postalAddressToSaveListModel.add(pcmc);
                 */
            }
        });

        addressTablePanel = new JPanel();
        addressTablePanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(postalAddressList);

        addressTablePanel.add(BorderLayout.CENTER, scrollPane);
        panelAddressList.setLayout(new BorderLayout());
        panelAddressList.add(BorderLayout.CENTER, addressTablePanel);

        panelAddressPurpose = new org.ofbiz.ordermax.party.ContactMechPurposeTypeSelectionPanel("POSTAL_ADDRESS");
        panelPostalAddressPurpose.setLayout(new BorderLayout());
        panelPostalAddressPurpose.add(BorderLayout.CENTER, panelAddressPurpose);

        panelAddressPurpose.btnAddPurpose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactMechPurposeType contactMechPurposeType = panelAddressPurpose.getSelectedPurposeType();
                PostalAddress pa = postalAddressPanel.getPostalAddress();
                PartyContactMechPurposeComposite pcmpc = new PartyContactMechPurposeComposite();
                pcmpc.setContactMechPurposeType(contactMechPurposeType);
                pcmpc.setPartyContactMechPurpose(new PartyContactMechPurpose());
                pcmpc.getPartyContactMechPurpose().setcontactMechPurposeTypeId(contactMechPurposeType.getcontactMechPurposeTypeId());
                pcmpc.getPartyContactMechPurpose().setpartyId(partyAccount.getParty().getpartyId());
                PartyContactMechComposite partyContactMechComposite = partyContactMechCompositeListData.getPartyContactMechComposite(pa.getContactMechId());
                Debug.logInfo("pa.getContactMechId(): " + pa.getContactMechId(), module);
                /*
                 if(partyContactMechComposite.getPartyContactMechPurposeList()==null){
                 partyContactMechComposite.setPartyContactMechPurposeList(new PartyContactMechPurposeList());
                 }
                 */
                partyContactMechComposite.getPartyContactMechPurposeList().add(pcmpc);
                loadPostalAddressPurposeList(partyContactMechComposite);
                postalAddressToSaveListModel.add(partyContactMechComposite);
            }
        });

        panelAddressPurpose.btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

//        panelAddressList.setLayout(new BorderLayout());
//        panelAddressList.add(BorderLayout.CENTER, addressTablePanel);
        panelAddressDetail.setLayout(new BorderLayout());
        panelAddressDetail.add(BorderLayout.CENTER, postalAddressPanel);

        ComponentBorder.doubleRaisedLoweredBevelBorder(panelAddressList, "Postal Address List");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelAddressDetail, "Postal Address Details");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPostalAddressPurpose, "Postal Address Purpose");
    }

    protected void setPartyId() {
        if (partyAccount != null) {
            for (int i = 0; i < postalAddressToSaveListModel.getSize(); i++) {
                PartyContactMechComposite pcmc = postalAddressToSaveListModel.getElementAt(i);
                pcmc.getPartyContactMech().setpartyId(partyAccount.getParty().getpartyId());
            }
        }
    }

    void mergeSaveList(ListAdapterListModel<PartyContactMechComposite> saveListModel) {

        if (partyContactMechCompositeListData != null) {
            for (PartyContactMechComposite pmcmc : saveListModel.getList()) {
                PartyContactMechComposite partyContactMechComposite = partyContactMechCompositeListData.getPartyContactMechComposite(pmcmc.getContact().getContactMech().getcontactMechId());
                if(partyContactMechComposite==null){
                    partyContactMechCompositeListData.add(pmcmc);
                }
            }
        }
    }

    protected void createNewPostalContactMech() {
        postalAddressToSaveListModel.clear();
        PartyContactMechComposite pcmc = null;
        if (partyAccount != null && partyAccount.getParty() != null) {
            pcmc = LoadAccountWorker.createPartyContactMechComposite(partyAccount.getParty().getpartyId(),
                    Contact.POSTAL_ADDRESS);
        } else {
            pcmc = LoadAccountWorker.createPartyContactMechComposite(null, Contact.POSTAL_ADDRESS);
        }
        postalAddressPanel.clearDialogFields();
        panelAddressPurpose.clearDialogFields();
        postalAddressPanel.setPostalAddress(pcmc.getContact().getPostalAddress());
        isNewPostalAddress = true;
        postalAddressToSaveListModel.add(pcmc);
    }

    protected void createNewTelephoneContactMech() {

        telephoneNumberToSaveListModel.clear();

        PartyContactMechComposite pcmc = null;
        if (partyAccount != null && partyAccount.getParty() != null) {
            pcmc = LoadAccountWorker.createPartyContactMechComposite(partyAccount.getParty().getpartyId(),
                    Contact.TELECOM_NUMBER);
        } else {
            pcmc = LoadAccountWorker.createPartyContactMechComposite(null, Contact.TELECOM_NUMBER);
        }
        telephonePanel.clearDialogFields();
        panelTelephonePurpose.clearDialogFields();
        telephonePanel.setTelecomNumber(pcmc.getContact().getTelecomNumber());
        isNewTele = true;
        telephoneNumberToSaveListModel.add(pcmc);
    }

    protected void createTelephoneUI() {

        selectionTelecomNumberModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        telecomNumberList.setSelectionModel(selectionTelecomNumberModel);

        ListCellRenderer<TelecomNumber> telecomNumberListCellRenderer = new TelecomNumberListCellRenderer();
        telecomNumberList.setCellRenderer(telecomNumberListCellRenderer);
        telecomNumberList.setEnabled(true);
        telecomNumberList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) selectionTelecomNumberModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            isNewTele = false;
                            panelTelephonePurpose.clearDialogFields();
                            setTelecomNumberDetail(i);
                            break;
                        }
                    }
                }
            }
        });

        //save
        saveTelephoneAction = new SavePostalAddressAction(SavePostalAddressAction.nameStr, session, null, telephoneNumberToSaveListModel);
        telephonePanel.btnSaveTelephone.addActionListener(saveTelephoneAction);

        saveTelephoneAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ScreenAction.BEFORE_SAVE.equals(e.getActionCommand())) {
                    Debug.logInfo(ScreenAction.BEFORE_SAVE, module);
                    try {
                        telephonePanel.getDialogFields();
                        setPartyId();
                    } catch (ParseException ex) {
                        Logger.getLogger(PartyContactMechPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Debug.logInfo(ScreenAction.AFTER_SAVE, module);
                    try {
                        telephonePanel.setDialogFields();
                        if (isNewTele) {
                            telecomNumberListModel.add(telephonePanel.getTelecomNumber());
                            mergeSaveList(telephoneNumberToSaveListModel);
                            isNewTele = false;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(PartyContactMechPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        telephonePanel.btnNewTelephone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewTelephoneContactMech();
            }
        });

        telephonePanel.btnDeleteTelephone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyAction(ContechPanelAction.DELETE_TELECOM);
                /*              telephoneNumberToSaveListModel.clear();
                 PartyContactMechComposite pcmc = LoadAccountWorker.createPartyContactMechComposite(partyAccount.getParty().getpartyId(),
                 Contact.ContactMechTypeId.POSTAL_ADDRESS);
                 telephonePanel.clearDialogFields();
                 telephonePanel.setTelecomNumber(pcmc.getContact().getTelecomNumber());

                 telephoneNumberToSaveListModel.add(pcmc);
                 */
            }
        });

        telephoneTablePanel = new JPanel();
        telephoneTablePanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(telecomNumberList);

        telephoneTablePanel.add(BorderLayout.CENTER, scrollPane);
        panelTelephoneList.setLayout(new BorderLayout());
        panelTelephoneList.add(BorderLayout.CENTER, telephoneTablePanel);

        panelTelephonePurpose = new org.ofbiz.ordermax.party.ContactMechPurposeTypeSelectionPanel("TELECOM_NUMBER");
        panelTelecomPurpose.setLayout(new BorderLayout());
        panelTelecomPurpose.add(BorderLayout.CENTER, panelTelephonePurpose);

        panelTelephonePurpose.btnAddPurpose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactMechPurposeType contactMechPurposeType = panelTelephonePurpose.getSelectedPurposeType();
                TelecomNumber pa = telephonePanel.getTelecomNumber();
                PartyContactMechPurposeComposite pcmpc = new PartyContactMechPurposeComposite();
                pcmpc.setContactMechPurposeType(contactMechPurposeType);
                pcmpc.setPartyContactMechPurpose(new PartyContactMechPurpose());
                pcmpc.getPartyContactMechPurpose().setcontactMechPurposeTypeId(contactMechPurposeType.getcontactMechPurposeTypeId());
                pcmpc.getPartyContactMechPurpose().setpartyId(partyAccount.getParty().getpartyId());
                PartyContactMechComposite partyContactMechComposite = partyContactMechCompositeListData.getPartyContactMechComposite(pa.getcontactMechId());
                if (partyContactMechComposite.getPartyContactMechPurposeList() == null) {
                    partyContactMechComposite.setPartyContactMechPurposeList(new PartyContactMechPurposeList());
                }
                partyContactMechComposite.getPartyContactMechPurposeList().add(pcmpc);
                loadTelephonePurposeList(partyContactMechComposite);
                telephoneNumberToSaveListModel.add(partyContactMechComposite);
            }
        });

        panelTelephonePurpose.btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        panelTelephoneDetail.setLayout(new BorderLayout());
        panelTelephoneDetail.add(BorderLayout.CENTER, telephonePanel);

        ComponentBorder.doubleRaisedLoweredBevelBorder(panelTelephoneList, "Telephone List");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelTelephoneDetail, "TelephoneDetails");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelTelecomPurpose, "Telephone Purpose");
    }

    public void loadPostalAddressPurposeList(PartyContactMechComposite partyContactMechComposite) {

        if (partyContactMechComposite != null) {
            partyContactMechComposite.outputToDebug();
            ListAdapterListModel<PartyContactMechComposite> tempAddressPurposeListModel = new ListAdapterListModel<PartyContactMechComposite>();

            //create temporary composite for each purpose for the postal address
            Debug.logInfo("loadPostalAddressPurposeList: ", module);
            PartyContactMechPurposeList mechPurposeList = partyContactMechComposite.getPartyContactMechPurposeList();
            for (PartyContactMechPurposeComposite purpose : mechPurposeList.getList()) {
                Debug.logInfo("loadPostalAddressPurposeList: " + purpose.getContactMechPurposeType().getcontactMechPurposeTypeId(), module);
                PartyContactMechComposite tmpComposite = new PartyContactMechComposite();
                tmpComposite.setContact(partyContactMechComposite.getContact());
                tmpComposite.setPartyContactMech(partyContactMechComposite.getPartyContactMech());
                tmpComposite.setPartyContactMechPurposeList(new PartyContactMechPurposeList());
                tmpComposite.getPartyContactMechPurposeList().add(purpose);
                tempAddressPurposeListModel.add(tmpComposite);
            }

            panelAddressPurpose.setAddressPurpose(tempAddressPurposeListModel);
        }

    }

    public void loadTelephonePurposeList(PartyContactMechComposite partyContactMechComposite) {
        if (partyContactMechComposite != null) {
            ListAdapterListModel<PartyContactMechComposite> tempAddressPurposeListModel = new ListAdapterListModel<PartyContactMechComposite>();

            //create temporary composite for each purpose for the postal address
            PartyContactMechPurposeList mechPurposeList = partyContactMechComposite.getPartyContactMechPurposeList();
            for (PartyContactMechPurposeComposite purpose : mechPurposeList.getList()) {
                PartyContactMechComposite tmpComposite = new PartyContactMechComposite();
                tmpComposite.setContact(partyContactMechComposite.getContact());
                tmpComposite.setPartyContactMech(partyContactMechComposite.getPartyContactMech());
                tmpComposite.setPartyContactMechPurposeList(new PartyContactMechPurposeList());
                tmpComposite.getPartyContactMechPurposeList().add(purpose);
                tempAddressPurposeListModel.add(tmpComposite);
            }

            panelTelephonePurpose.setAddressPurpose(tempAddressPurposeListModel);
        }

    }

    public void setPostalAddressDetail(int index) {

        //clear save list
        postalAddressToSaveListModel.clear();
        Debug.logInfo("setPostalAddressDetail: " + index, module);
        if (index < postalAddressListModel.getSize()) {
            PostalAddress opt = postalAddressListModel.getElementAt(index);
            postalAddressPanel.setPostalAddress(opt);
            try {
                postalAddressPanel.setDialogFields();
                Debug.logInfo("opt.getContactMechId(): " + opt.getContactMechId(), module);
                PartyContactMechComposite partyContactMechComposite = partyContactMechCompositeListData.getPartyContactMechComposite(opt.getContactMechId());
                loadPostalAddressPurposeList(partyContactMechComposite);
                postalAddressToSaveListModel.add(partyContactMechComposite);
            } catch (ParseException ex) {
                Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("selected index :" + postalAddressList.getSelectedIndex() + "[[" + opt.getAddress1() + "]]");
        }
    }

    public void setTelecomNumberDetail(int index) {

        //clear save list
        telephoneNumberToSaveListModel.clear();

        if (index < telecomNumberListModel.getSize()) {
            TelecomNumber opt = telecomNumberListModel.getElementAt(index);
            telephonePanel.setTelecomNumber(opt);
            try {
                telephonePanel.setDialogFields();
                PartyContactMechComposite partyContactMechComposite = partyContactMechCompositeListData.getPartyContactMechComposite(opt.getcontactMechId());
                loadTelephonePurposeList(partyContactMechComposite);
                telephoneNumberToSaveListModel.add(partyContactMechComposite);
            } catch (ParseException ex) {
                Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
            }
//            System.out.println("selected index :" + telecomNumberListModel.getSelectedIndex() + "[[" + opt.getAddress1() + "]]");
        }
    }

    protected void notifyAction(ContechPanelAction actionType) {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_FIRST, actionType.toString());
        for (ActionListener listener : listeners) {

            listener.actionPerformed(event); // broadcast to all
        }
    }
    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public void clearActionListener() {
        listeners.clear();
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
        panelTelephoneList = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelTelephoneDetail = new javax.swing.JPanel();
        panelTelecomPurpose = new javax.swing.JPanel();
        panelEmail = new javax.swing.JPanel();
        panelEmailSelec = new javax.swing.JPanel();
        maritalStatusLabel2 = new javax.swing.JLabel();
        comboEmail = new javax.swing.JComboBox();
        panelWeb = new javax.swing.JPanel();

        panelAddress.setLayout(new java.awt.BorderLayout());

        panelAddressList.setMaximumSize(new java.awt.Dimension(32767, 400));
        panelAddressList.setMinimumSize(new java.awt.Dimension(0, 100));
        panelAddressList.setPreferredSize(new java.awt.Dimension(0, 100));

        javax.swing.GroupLayout panelAddressListLayout = new javax.swing.GroupLayout(panelAddressList);
        panelAddressList.setLayout(panelAddressListLayout);
        panelAddressListLayout.setHorizontalGroup(
            panelAddressListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
        );
        panelAddressListLayout.setVerticalGroup(
            panelAddressListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        panelAddress.add(panelAddressList, java.awt.BorderLayout.CENTER);

        jPanel1.setMinimumSize(new java.awt.Dimension(0, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(848, 320));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        panelAddressDetail.setPreferredSize(new java.awt.Dimension(424, 320));

        javax.swing.GroupLayout panelAddressDetailLayout = new javax.swing.GroupLayout(panelAddressDetail);
        panelAddressDetail.setLayout(panelAddressDetailLayout);
        panelAddressDetailLayout.setHorizontalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        panelAddressDetailLayout.setVerticalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        jPanel1.add(panelAddressDetail);

        panelPostalAddressPurpose.setMinimumSize(new java.awt.Dimension(0, 400));
        panelPostalAddressPurpose.setPreferredSize(new java.awt.Dimension(217, 450));

        javax.swing.GroupLayout panelPostalAddressPurposeLayout = new javax.swing.GroupLayout(panelPostalAddressPurpose);
        panelPostalAddressPurpose.setLayout(panelPostalAddressPurposeLayout);
        panelPostalAddressPurposeLayout.setHorizontalGroup(
            panelPostalAddressPurposeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        panelPostalAddressPurposeLayout.setVerticalGroup(
            panelPostalAddressPurposeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jPanel1.add(panelPostalAddressPurpose);

        panelAddress.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Address", panelAddress);

        panelTelephone.setLayout(new java.awt.BorderLayout());

        panelTelephoneList.setMaximumSize(new java.awt.Dimension(32767, 400));
        panelTelephoneList.setMinimumSize(new java.awt.Dimension(0, 100));

        javax.swing.GroupLayout panelTelephoneListLayout = new javax.swing.GroupLayout(panelTelephoneList);
        panelTelephoneList.setLayout(panelTelephoneListLayout);
        panelTelephoneListLayout.setHorizontalGroup(
            panelTelephoneListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
        );
        panelTelephoneListLayout.setVerticalGroup(
            panelTelephoneListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        panelTelephone.add(panelTelephoneList, java.awt.BorderLayout.CENTER);

        jPanel2.setMinimumSize(new java.awt.Dimension(0, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(848, 320));
        jPanel2.setLayout(new java.awt.GridLayout());

        panelTelephoneDetail.setPreferredSize(new java.awt.Dimension(424, 320));

        javax.swing.GroupLayout panelTelephoneDetailLayout = new javax.swing.GroupLayout(panelTelephoneDetail);
        panelTelephoneDetail.setLayout(panelTelephoneDetailLayout);
        panelTelephoneDetailLayout.setHorizontalGroup(
            panelTelephoneDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        panelTelephoneDetailLayout.setVerticalGroup(
            panelTelephoneDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );

        jPanel2.add(panelTelephoneDetail);

        panelTelecomPurpose.setMinimumSize(new java.awt.Dimension(0, 400));
        panelTelecomPurpose.setPreferredSize(new java.awt.Dimension(217, 450));

        javax.swing.GroupLayout panelTelecomPurposeLayout = new javax.swing.GroupLayout(panelTelecomPurpose);
        panelTelecomPurpose.setLayout(panelTelecomPurposeLayout);
        panelTelecomPurposeLayout.setHorizontalGroup(
            panelTelecomPurposeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        panelTelecomPurposeLayout.setVerticalGroup(
            panelTelecomPurposeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jPanel2.add(panelTelecomPurpose);

        panelTelephone.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Telephone", panelTelephone);

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
                .addComponent(comboEmail, 0, 716, Short.MAX_VALUE)
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
                .addContainerGap(456, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Email", panelEmail);

        javax.swing.GroupLayout panelWebLayout = new javax.swing.GroupLayout(panelWeb);
        panelWeb.setLayout(panelWebLayout);
        panelWebLayout.setHorizontalGroup(
            panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
        );
        panelWebLayout.setVerticalGroup(
            panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Web", panelWeb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 853, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 728, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Address");
    }// </editor-fold>//GEN-END:initComponents


    private void comboEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEmailActionPerformed
    }//GEN-LAST:event_comboEmailActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboEmail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel maritalStatusLabel2;
    private javax.swing.JPanel panelAddress;
    private javax.swing.JPanel panelAddressDetail;
    private javax.swing.JPanel panelAddressList;
    private javax.swing.JPanel panelEmail;
    private javax.swing.JPanel panelEmailSelec;
    private javax.swing.JPanel panelPostalAddressPurpose;
    private javax.swing.JPanel panelTelecomPurpose;
    private javax.swing.JPanel panelTelephone;
    private javax.swing.JPanel panelTelephoneDetail;
    private javax.swing.JPanel panelTelephoneList;
    private javax.swing.JPanel panelWeb;
    // End of variables declaration//GEN-END:variables
}
