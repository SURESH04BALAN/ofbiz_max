/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Contact;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeComposite;
import org.ofbiz.ordermax.composite.PartyHelper;
import org.ofbiz.ordermax.entity.ContactMech;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Party;
import org.ofbiz.ordermax.entity.PartyContactMech;
import org.ofbiz.ordermax.entity.PartyContactMechPurpose;
import org.ofbiz.ordermax.entity.PartyGroup;
import org.ofbiz.ordermax.entity.PartyNameView;
import org.ofbiz.ordermax.entity.Person;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import org.ofbiz.party.contact.ContactMechWorker;

/**
 *
 * @author siranjeev
 */
/**
 *
 * @author siranjeev
 */
public class LoadPartyFromFileWorker extends SwingWorker<List<Account>, Account> {
    public static final String module = LoadPartyFromFileWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<Account> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session = null;

    public LoadPartyFromFileWorker(ListAdapterListModel<Account> personListModel, XuiSession session) {
        this.personListModel = personListModel;
        this.session = session;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    private String partyId = "";

    @Override
    protected List<Account> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<Account> accounts = new ArrayList<Account>();

        Debug.logInfo("doInBackground ", module);
        GenericValue partyGen = PartyHelper.getParty(partyId, session.getDelegator());
        Debug.logInfo("partyId: " + partyId, module);
        Party party = new Party(partyGen);
        Account account = new Account();
        account.setParty(party);
//        account.setPartyId(partyId);

        if ("PARTY_GROUP".equalsIgnoreCase(partyGen.getString("partyTypeId"))) {
//            account.setPartyTypeId(Account.PartyTypeId.PARTY_GROUP);
            GenericValue partyGroupGen = PartyHelper.getPartyGroup(partyId, session.getDelegator());
            PartyGroup partyGroup = new PartyGroup(partyGroupGen);
            account.setPartyGroup(partyGroup);
        } else {
//            account.setPartyTypeId(Account.PartyTypeId.PERSON);
            GenericValue partyPersonGen = PartyHelper.getPerson(partyId, session.getDelegator());
            Person person = new Person(partyPersonGen);
            account.setPerson(person);
        }

        List<GenericValue> purposeTypeList = PosProductHelper.getGenericValueLists("ContactMechPurposeType", session.getDelegator());
        List<ContactMechPurposeType> purposeTypeListCmp = new ArrayList<ContactMechPurposeType>();
        for (GenericValue gv : purposeTypeList) {
            purposeTypeListCmp.add(new ContactMechPurposeType(gv));
        }

        ArrayList itemList = new ArrayList();
        List<Map<String, Object>> partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
        Debug.logInfo("partyMechList: " + partyId, module);
        if (partyMechList != null) {
            Debug.logInfo("if (partyMechList != null): " + partyId, module);
            for (Map<String, Object> mapOfObject : partyMechList) {
                PartyContactMechComposite partyContact = new PartyContactMechComposite();
                if (mapOfObject.containsKey("contactMech")) {

                    //set party contach
                    partyContact.setPartyContactMech(new PartyContactMech((GenericValue) mapOfObject.get("partyContactMech")));

                    GenericValue contactMech = (GenericValue) mapOfObject.get("contactMech");
                    String contactMechTypeId = contactMech.getString("contactMechTypeId");
                    Debug.logInfo("contactMechTypeId: " + contactMechTypeId, module);

                    Contact contact = new Contact(contactMechTypeId);
                    contact.setContactMech(new ContactMech(contactMech));

                    if (Contact.isPostalAddress(contactMechTypeId)) {

                        if (mapOfObject.containsKey("postalAddress") && mapOfObject.get("postalAddress") != null) {
                            contact.setPostalAddress(new PostalAddress((GenericValue) mapOfObject.get("postalAddress")));
                        }
                    } else if (Contact.isTelecomNumberAddress(contactMechTypeId)) {
                        if (mapOfObject.containsKey("telecomNumber") && mapOfObject.get("telecomNumber") != null) {
                            contact.setTelecomNumber(new TelecomNumber((GenericValue) mapOfObject.get("telecomNumber")));
                        }

                    }

                    //set the contact
                    partyContact.setContact(contact);
                    itemList.add(partyContact);
                }
            }
        }
        PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
        partyContactList.addAll(itemList);
        account.setPartyContactList(partyContactList);
        account.outputToDebug();
        accounts.add(account);
        return accounts;
    }

   // public static Contact getContact(String conatctMechId, XuiSession session){
    //}

    public static Account getAccount(String partyId, XuiSession session) {

        GenericValue partyGen = PartyHelper.getParty(partyId, session.getDelegator());

        Debug.logInfo("partyId: " + partyId, module);

        Party party = new Party(partyGen);
        Account account = new Account();
        account.setParty(party);
//        account.setPartyId(partyId);

        if ("PARTY_GROUP".equalsIgnoreCase(partyGen.getString("partyTypeId"))) {
//            account.setPartyTypeId(Account.PartyTypeId.PARTY_GROUP);
            GenericValue partyGroupGen = PartyHelper.getPartyGroup(partyId, session.getDelegator());
            PartyGroup partyGroup = new PartyGroup(partyGroupGen);
            account.setPartyGroup(partyGroup);
        } else {
//            account.setPartyTypeId(Account.PartyTypeId.PERSON);
            GenericValue partyPersonGen = PartyHelper.getPerson(partyId, session.getDelegator());
            Person person = new Person(partyPersonGen);
            account.setPerson(person);
        }

        List<GenericValue> purposeTypeList = PosProductHelper.getGenericValueLists("ContactMechPurposeType", session.getDelegator());
        Debug.logInfo("ContactMechPurposeType: start ", module);
        List<ContactMechPurposeType> purposeTypeListCmp = new ArrayList<ContactMechPurposeType>();
        for (GenericValue gv : purposeTypeList) {
            ContactMechPurposeType val = new ContactMechPurposeType(gv);
            purposeTypeListCmp.add(val);
            Debug.logInfo("ContactMechPurposeType: " + val.getdescription(), module);
        }

        ArrayList itemList = new ArrayList();
        ArrayList itemPurposeList = new ArrayList();
        List<Map<String, Object>> partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
        Debug.logInfo("partyMechList: " + partyId, module);
        if (partyMechList != null) {
            Debug.logInfo("if (partyMechList != null): " + partyId, module);
            for (Map<String, Object> mapOfObject : partyMechList) {
                PartyContactMechComposite partyContact = new PartyContactMechComposite();
                if (mapOfObject.containsKey("contactMech")) {

                    //set party contach
                    partyContact.setPartyContactMech(new PartyContactMech((GenericValue) mapOfObject.get("partyContactMech")));

                    GenericValue contactMech = (GenericValue) mapOfObject.get("contactMech");
                    String contactMechTypeId = contactMech.getString("contactMechTypeId");
                    Debug.logInfo("contactMechTypeId: " + contactMechTypeId, module);

                    Contact contact = new Contact(contactMechTypeId);
                    contact.setContactMech(new ContactMech(contactMech));

                    if (Contact.isPostalAddress(contactMechTypeId)) {

                        if (mapOfObject.containsKey("postalAddress") && mapOfObject.get("postalAddress") != null) {
                            contact.setPostalAddress(new PostalAddress((GenericValue) mapOfObject.get("postalAddress")));
                        }
                    } else if (Contact.isTelecomNumberAddress(contactMechTypeId)) {
                        if (mapOfObject.containsKey("telecomNumber") && mapOfObject.get("telecomNumber") != null) {
                            contact.setTelecomNumber(new TelecomNumber((GenericValue) mapOfObject.get("telecomNumber")));
                        }

                    }

                    //set the contact
                    partyContact.setContact(contact);
                    itemList.add(partyContact);

                    List<GenericValue> partyContactMechPurposes = (List<GenericValue>) mapOfObject.get("partyContactMechPurposes");
                    for (GenericValue purposeGV : partyContactMechPurposes) {
                        PartyContactMechPurposeComposite pcmp = new PartyContactMechPurposeComposite();
                        pcmp.setPartyContactMechPurpose(new PartyContactMechPurpose(purposeGV));
//                        pcmp.setPartyContact(partyContact);
//                        itemPurposeList.add(pcmp);
                        partyContact.getPartyContactMechPurposeList().add(pcmp);
                        ContactMechPurposeType cmp = getContactMechPurposeType(purposeTypeListCmp, pcmp.getPartyContactMechPurpose().getcontactMechPurposeTypeId());
                        pcmp.setContactMechPurposeType(cmp);
                        Debug.logInfo("pcmp.getPartyContactMechPurpose: " + pcmp.getPartyContactMechPurpose().getcontactMechPurposeTypeId(), module);
                    }

                }
            }
        }

        PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
        partyContactList.addAll(itemList);
        account.setPartyContactList(partyContactList);

//        PartyContactMechPurposeList partyContactMechPurposeList = new PartyContactMechPurposeList();
//        partyContactMechPurposeList.addAll(itemPurposeList);
//        account.setPartyContactMechPurposeList(partyContactMechPurposeList);
        account.outputToDebug();

        return account;
    }
        public static ContactMechPurposeType getContactMechPurposeType(List<ContactMechPurposeType> purposeTypeList, String purposeTypeId) {
        ContactMechPurposeType purposeTypeCmp = null;
        for (ContactMechPurposeType gv : purposeTypeList) {
            if (gv.getcontactMechPurposeTypeId().equals(purposeTypeId)) {
                purposeTypeCmp = gv;
                break;
            }
        }
        return purposeTypeCmp;
    }

   
    
    static public String getFormatedPartyName(String partyId, DisplayNameInterface.DisplayTypes showId) {
        String partyName = "";
        try {
            GenericValue genPartyNameView = XuiContainer.getSession().getDelegator().findByPrimaryKeyCache("PartyNameView", UtilMisc.toMap("partyId", partyId));
            PartyNameView partyNameView = new PartyNameView(genPartyNameView);
            partyName = partyNameView.getdisplayName(showId);
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPartyFromFileWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return partyName;
    }

    @Override
    protected void process(List<Account> chunks) {
        /*            try{
         throw new Exception("process(List<Person> chunks)");
         }
         catch(Exception e){
         e.printStackTrace();
         }
         */
        personListModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    private void sleepAWhile() {
        try {
            Thread.yield();
            long timeToSleep = getTimeToSleep();
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
        }
    }

    private long getTimeToSleep() {
        return loadPersonsSpeedModel.getValue();
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;

    }
}
