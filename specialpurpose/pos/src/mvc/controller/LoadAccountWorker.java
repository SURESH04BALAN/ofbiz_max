/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

/**
 *
 * @author siranjeev
 */
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Contact;
import org.ofbiz.ordermax.composite.ContactList;
import org.ofbiz.ordermax.composite.PartyRolesList;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeComposite;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeList;
import org.ofbiz.ordermax.composite.PartyHelper;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
import org.ofbiz.ordermax.entity.ContactMech;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;
import org.ofbiz.ordermax.entity.PartyRole;
import org.ofbiz.ordermax.entity.Party;
import org.ofbiz.ordermax.entity.PartyContactMech;
import org.ofbiz.ordermax.entity.PartyContactMechPurpose;
import org.ofbiz.ordermax.entity.PartyGroup;
import org.ofbiz.ordermax.entity.PartyIdentification;
import org.ofbiz.ordermax.entity.PartyIdentificationType;
import org.ofbiz.ordermax.entity.Person;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import org.ofbiz.ordermax.entity.UserLogin;
import org.ofbiz.ordermax.party.ContactMechPurposeTypeSingleton;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.product.UomCurrencySingleton;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadAccountWorker extends LoadBaseSwingWorker<Account> {

     public LoadAccountWorker(ListAdapterListModel<Account> personListModel, XuiSession session, Map<String, Object> findOptionMap) {
        super(personListModel, findOptionMap);
        this.session = session;
    }
    
    public LoadAccountWorker(XuiSession session) {
        super();
        this.session = session;
    }

    @Override
    protected List<Account> doInBackground() throws Exception {
        listModel.clear();
  /* //new
        List<Account> accounts = new ArrayList<Account>();

        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        //Map<String, Object> genList = findParty(findOptionMap, session);
        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("Party", entityConditionList, "+partyId", session.getDelegator(), true);
 
        maxProgress = resultList.size() + 1;
        for (GenericValue roleType : resultList) {
            String id = roleType.getString("partyId");
            Account account;
            try {
                 account = PartyListSingleton.getAccount(id);
               // account = getSimpleAccount(id, session);
//                listModel.add(account);
                int prograss = calcProgress(progressedItems + 1);
                accounts.add(account);

//                if ( (prograss+1) % 4 == 0) {
                publish(account);
//                }
//                else{
//                    progressedItems++;                        
//                }

                if (isCancelled()) {
                    break;
                }

            } catch (Exception ex) {
//                Logger.getLogger(PartyListSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return accounts; */
        listModel.clear();

        List<Account> accounts = new ArrayList<Account>();
        List<GenericValue> partyList = null;
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        Map<String, Object> genList = findParty(findOptionMap, session);
        List<GenericValue> selList = (List<GenericValue>) genList.get("partyList");
        maxProgress = selList.size() + 1;
        for (GenericValue roleType : selList) {
            String id = roleType.getString("partyId");
            Account account;
            try {
                account = getSimpleAccount(id, session);

                int prograss = calcProgress(progressedItems + 1);
                accounts.add(account);


                publish(account);

                if (isCancelled()) {
                    break;
                }

            } catch (Exception ex) {
                Debug.logError(ex, module);
//                Logger.getLogger(PartyListSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return accounts;        
    }

    // public static Contact getContact(String conatctMechId, XuiSession session){
    //}
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

    public static Account getAccount(String partyId, XuiSession session) throws Exception {

        GenericValue partyGen = PartyHelper.getParty(partyId, session.getDelegator());
        if (partyGen == null) {
            throw new Exception("Party[partyId=" + partyId + "] doesn't exist.");
        }

        /*        if (UtilValidate.isEmpty(partyGen.getString("partyTypeId"))) {
         throw new Exception("PartyTypeId is null : " + partyId);
         }
         */
        Party party = new Party(partyGen);
        Account account = new Account();
        account.setParty(party);
//        account.setPartyId(partyId);

        if ("PARTY_GROUP".equalsIgnoreCase(partyGen.getString("partyTypeId"))
                || "TEAM".equalsIgnoreCase(partyGen.getString("partyTypeId"))) {
//            account.setPartyTypeId(Account.PartyTypeId.PARTY_GROUP);
            Debug.logInfo("Party Group partyId: " + partyId, module);
            GenericValue partyGroupGen = PartyHelper.getPartyGroup(partyId, session.getDelegator());
            PartyGroup partyGroup = new PartyGroup(partyGroupGen);
            account.setPartyGroup(partyGroup);
        } else {
//            account.setPartyTypeId(Account.PartyTypeId.PERSON);
            Debug.logInfo("Person partyId: " + partyId, module);
            GenericValue partyPersonGen = PartyHelper.getPerson(partyId, session.getDelegator());
            Person person = new Person(partyPersonGen);
            account.setPerson(person);
        }

        List<GenericValue> purposeTypeList = PosProductHelper.getGenericValueLists("ContactMechPurposeType", session.getDelegator());
        //Debug.logInfo("ContactMechPurposeType: start ", NAME);
        List<ContactMechPurposeType> purposeTypeListCmp = new ArrayList<ContactMechPurposeType>();
        for (GenericValue gv : purposeTypeList) {
            ContactMechPurposeType val = new ContactMechPurposeType(gv);
            purposeTypeListCmp.add(val);
//            Debug.logInfo("ContactMechPurposeType: " + val.getdescription(), NAME);
        }

        /*ArrayList itemList = new ArrayList();
         ArrayList itemPurposeList = new ArrayList();
         List<Map<String, Object>> partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
         //Debug.logInfo("partyMechList: " + partyId, NAME);
         if (partyMechList != null) {
         //Debug.logInfo("if (partyMechList != null): " + partyId, NAME);
         for (Map<String, Object> mapOfObject : partyMechList) {
         PartyContactMechComposite partyContact = new PartyContactMechComposite();
         if (mapOfObject.containsKey("contactMech")) {

         //set party contach
         GenericValue partyContactMech = (GenericValue) mapOfObject.get("partyContactMech");

         partyContact.setPartyContactMech(new PartyContactMech((GenericValue) mapOfObject.get("partyContactMech")));

         GenericValue contactMech = (GenericValue) mapOfObject.get("contactMech");
         String contactMechTypeId = contactMech.getString("contactMechTypeId");
         //Debug.logInfo("contactMechTypeId: " + contactMechTypeId, NAME);

         Contact contact = new Contact(contactMechTypeId);
         contact.setContactMech(new ContactMech(contactMech));

         if ("POSTAL_ADDRESS".equalsIgnoreCase(contactMechTypeId)) {

         if (mapOfObject.containsKey("postalAddress") && mapOfObject.get("postalAddress") != null) {
         contact.setPostalAddress(new PostalAddress((GenericValue) mapOfObject.get("postalAddress")));
         }
         } else if ("TELECOM_NUMBER".equalsIgnoreCase(contactMechTypeId)) {
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
         ContactMechPurposeType cmp = getContactMechPurposeType(purposeTypeListCmp, pcmp.getPartyContactMechPurpose().getcontactMechPurposeTypeId());
         pcmp.setContactMechPurposeType(cmp);
         partyContact.getPartyContactMechPurposeList().add(pcmp);
         //Debug.logInfo("pcmp.getPartyContactMechPurpose: " + pcmp.getPartyContactMechPurpose().getcontactMechPurposeTypeId(), NAME);
         }
         }
         }
         }

         PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
         partyContactList.addAll(itemList);
         */
        account.setPartyContactList(getPartyContactMechList(partyId, session));

        //get all roles so we can get parties related to this order
        ArrayList<PartyRoleComposite> array = new ArrayList<PartyRoleComposite>();

        List<GenericValue> partyRoleList = partyGen.getRelated("PartyRole");
        for (GenericValue partyRoleGen : partyRoleList) {
            //create order role
            PartyRole partyRole = new PartyRole(partyRoleGen);
            //crreate the order composite
            PartyRoleComposite partyRoleComposite = new PartyRoleComposite();

            //get party details
//                Account acct = PartyListSingleton.getAccount(partyRole.getpartyId());//LoadAccountWorker.getAccount(partyRole.getpartyId(), session);
            partyRoleComposite.setPartyRole(partyRole);
//                partyRoleComposite.setParty(acct);
            //add to list
            array.add(partyRoleComposite);
        }

        //let set order composite to order
        PartyRolesList rolesList = new PartyRolesList();
        rolesList.addAll(array);
        //set it to order
        account.setPartyRolesList(rolesList);

        List<PartyIdentification> partyIdentifications = getPartyIdentification(partyId, session);
        Debug.logInfo("Person partyId: " + partyId, module);
        if (partyIdentifications.size() > 0) {
            account.setPartyIdentification(partyIdentifications.get(0));
        }/* else {
         account.setPartyIdentification(new PartyIdentification());
         }*/

        return account;
    }

    public static Account getSimpleAccount(String partyId, XuiSession session) throws Exception {

        GenericValue partyGen = PartyHelper.getParty(partyId, session.getDelegator());
        if (partyGen == null) {
            throw new Exception("Party[partyId=" + partyId + "] doesn't exist.");
        }

        if (UtilValidate.isEmpty(partyGen.getString("partyTypeId"))) {
            throw new Exception("PartyTypeId is null : " + partyId);
        }

        Party party = new Party(partyGen);
        Account account = new Account();
        account.setParty(party);
//        account.setPartyId(partyId);

        if ("PARTY_GROUP".equalsIgnoreCase(partyGen.getString("partyTypeId"))
                || "TEAM".equalsIgnoreCase(partyGen.getString("partyTypeId"))) {
//            account.setPartyTypeId(Account.PartyTypeId.PARTY_GROUP);
            Debug.logInfo("Party Group partyId: " + partyId, module);
            GenericValue partyGroupGen = PartyHelper.getPartyGroup(partyId, session.getDelegator());
            PartyGroup partyGroup = new PartyGroup(partyGroupGen);
            account.setPartyGroup(partyGroup);
        } else {
//            account.setPartyTypeId(Account.PartyTypeId.PERSON);

            GenericValue partyPersonGen = PartyHelper.getPerson(partyId, session.getDelegator());
            Person person = new Person(partyPersonGen);
            account.setPerson(person);
        }
//get all roles so we can get parties related to this order
        ArrayList<PartyRoleComposite> array = new ArrayList<PartyRoleComposite>();

        List<GenericValue> partyRoleList = partyGen.getRelated("PartyRole");
        for (GenericValue partyRoleGen : partyRoleList) {
            //create order role
            PartyRole partyRole = new PartyRole(partyRoleGen);
            //crreate the order composite
            PartyRoleComposite partyRoleComposite = new PartyRoleComposite();

            //get party details
//                Account acct = PartyListSingleton.getAccount(partyRole.getpartyId());//LoadAccountWorker.getAccount(partyRole.getpartyId(), session);
            partyRoleComposite.setPartyRole(partyRole);
//                partyRoleComposite.setParty(acct);
            //add to list
            array.add(partyRoleComposite);
        }

        //let set order composite to order
        PartyRolesList rolesList = new PartyRolesList();
        rolesList.addAll(array);
        //set it to order
        account.setPartyRolesList(rolesList);

        return account;
    }

    static public List<PartyIdentification> getPartyIdentification(String partyId, XuiSession session) {

        List<PartyIdentification> partyIdentifications = FastList.newInstance();
        List<EntityExpr> contactList = UtilMisc.toList(
                EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId)
        );
        try {

            List<GenericValue> machList = session.getDelegator().findList("PartyIdentification", EntityCondition.makeCondition(contactList, EntityOperator.AND), null, null, null, false);
            for (GenericValue genVal : machList) {
                partyIdentifications.add(new PartyIdentification(genVal));
            }

        } catch (GenericEntityException e) {
            Debug.logWarning(e.getMessage(), module);
        }
        return partyIdentifications;
    }

    static public PartyContactMechCompositeList getPartyContactMechList(String partyId, XuiSession session) {
        Debug.logInfo("getPartyContactMechList partyId: " + partyId, module);
        PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
        ArrayList<PartyContactMechComposite> itemList = new ArrayList<PartyContactMechComposite>();
//        ArrayList itemPurposeList = new ArrayList();
        List<Map<String, Object>> partyMechList = ContactMechWorker.getPartyContactMechValueMaps(session.getDelegator(), partyId, false);
        Debug.logInfo("partyMechList: " + partyId, module);
        if (partyMechList != null) {
            //Debug.logInfo("if (partyMechList != null): " + partyId, NAME);
            for (Map<String, Object> mapOfObject : partyMechList) {
                PartyContactMechComposite partyContact = new PartyContactMechComposite();
                if (mapOfObject.containsKey("contactMech")) {

                    //set party contach
                    //GenericValue partyContactMech = (GenericValue) mapOfObject.get("partyContactMech");
                    partyContact.setPartyContactMech(new PartyContactMech((GenericValue) mapOfObject.get("partyContactMech")));

                    GenericValue contactMech = (GenericValue) mapOfObject.get("contactMech");
                    String contactMechTypeId = contactMech.getString("contactMechTypeId");
                    Debug.logInfo("contactMechTypeId: " + contactMechTypeId, module);

                    Contact contact = new Contact();
                    contact.setContactMech(new ContactMech(contactMech));

                    if ("POSTAL_ADDRESS".equalsIgnoreCase(contactMechTypeId)) {

                        contact.setContactMechTypeId(contactMechTypeId);
                        if (mapOfObject.containsKey("postalAddress") && mapOfObject.get("postalAddress") != null) {
                            contact.setPostalAddress(new PostalAddress((GenericValue) mapOfObject.get("postalAddress")));
                        }
                    } else if ("TELECOM_NUMBER".equalsIgnoreCase(contactMechTypeId)) {

                        if (mapOfObject.containsKey("telecomNumber") && mapOfObject.get("telecomNumber") != null) {
                            contact.setTelecomNumber(new TelecomNumber((GenericValue) mapOfObject.get("telecomNumber")));
                        }

                    }

                    //set the contact
                    Debug.logInfo("partyContact contactMechTypeId: " + contactMechTypeId, module);
                    partyContact.setContact(contact);
                    itemList.add(partyContact);

                    List<GenericValue> partyContactMechPurposes = (List<GenericValue>) mapOfObject.get("partyContactMechPurposes");
                    for (GenericValue purposeGV : partyContactMechPurposes) {
                        try {
                            PartyContactMechPurposeComposite pcmp = new PartyContactMechPurposeComposite();
                            pcmp.setPartyContactMechPurpose(new PartyContactMechPurpose(purposeGV));
//                        pcmp.setPartyContact(partyContact);
//                        itemPurposeList.add(pcmp);
                            //ContactMechPurposeType cmp = getContactMechPurposeType(purposeTypeListCmp, pcmp.getPartyContactMechPurpose().getcontactMechPurposeTypeId());
                            ContactMechPurposeType cmp = ContactMechPurposeTypeSingleton.getContactMechPurposeType(pcmp.getPartyContactMechPurpose().getcontactMechPurposeTypeId());
                            pcmp.setContactMechPurposeType(cmp);
                            partyContact.getPartyContactMechPurposeList().add(pcmp);
                            Debug.logInfo("pcmp.getPartyContactMechPurpose: " + pcmp.getPartyContactMechPurpose().getcontactMechPurposeTypeId(), module);
                        } catch (Exception ex) {
                            Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

        partyContactList.addAll(itemList);
        Debug.logInfo("partyContact contactMechTypeId partyContactList size: " + partyContactList.getSize(), module);
        return partyContactList;
    }

    static public ContactMech getContactMech(String contactMechId) {
        ContactMech contactMech = null;
        try {
            contactMech = new ContactMech(ControllerOptions.getSession().getDelegator().findByPrimaryKey("ContactMech", UtilMisc.toMap("contactMechId", contactMechId)));
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        return contactMech;
    }

    static public PostalAddress getPostalAddress(String contactMechId) {
        PostalAddress postalAdress = null;
        try {
            postalAdress = new PostalAddress(ControllerOptions.getSession().getDelegator().findByPrimaryKey("PostalAddress", UtilMisc.toMap("contactMechId", contactMechId)));
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        return postalAdress;
    }

    static public TelecomNumber getTelecomNumber(String contactMechId) {
        TelecomNumber telecomNumber = null;
        try {
            telecomNumber = new TelecomNumber(ControllerOptions.getSession().getDelegator().findByPrimaryKey("TelecomNumber", UtilMisc.toMap("contactMechId", contactMechId)));
        } catch (GenericEntityException e) {
            Debug.logWarning(e, module);
        }
        return telecomNumber;
    }

    public static void saveAccount(Account account, boolean isCustomer, XuiSession session) {

        Party party = account.getParty();
        GenericValue partyGenric = null;
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        String partyId = party.getpartyId();
        try {
            partyGenric = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));
        } catch (GenericEntityException e) {
            Debug.logWarning(e.getMessage(), module);
        }
        boolean isUpdate = partyGenric != null;

        //is it party group
        if ("PARTY_GROUP".equals(party.getpartyTypeId())) {
            //update or create
            if (isUpdate) {
                if (UtilValidate.isNotEmpty(partyId)) {
                    try {

                        resultMap = dispatcher.runSync("updatePartyGroup", UtilMisc.toMap(
                                "partyId", partyId,
                                "userLogin", userLogin,
                                "groupName", account.getPartyGroup().getgroupName(),
                                "preferredCurrencyUomId", account.getParty().getpreferredCurrencyUomId(),
                                "statusId", account.getParty().getstatusId(),
                                "locale", locale));
                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }

//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                    } catch (Exception exc) {
                        Debug.logError(exc, module);
                    }
                }

            } else {
                try {
                    resultMap = dispatcher.runSync("createPartyGroup", UtilMisc.toMap(
                            "partyId", partyId,
                            "userLogin", userLogin,
                            "locale", locale,
                            "groupName", account.getPartyGroup().getgroupName(),
                            "partyTypeId", "PARTY_GROUP",
                            "preferredCurrencyUomId", account.getParty().getpreferredCurrencyUomId(),
                            "statusId", account.getParty().getstatusId()));
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                    Debug.logInfo("update roles", module);
                    /*List<PartyRole> list = null;
                     if (isCustomer) {
                     //org.ofbiz.ordermax.party.PartyHelper.createPartyCustomerRoles(partyId, session);
                     list = LoadAccountWorker.createCustomerPartyRoles(account.getParty().getpartyId());
                     } else {
                     Debug.logInfo("update supplier roles", module);
                     list = LoadAccountWorker.createSupplierPartyRoles(account.getParty().getpartyId());
                     //                        org.ofbiz.ordermax.party.PartyHelper.createPartySupplierRoles(partyId, session);
                     }
                     for (PartyRole prole : list) {
                     PartyRoleComposite tmpPartyRoleComposite = new PartyRoleComposite();
                     tmpPartyRoleComposite.setParty(account);
                     tmpPartyRoleComposite.setPartyRole(prole);
                     account.getPartyRolesList().add(tmpPartyRoleComposite);
                     }*/
                    LoadAccountWorker.createPartyRoles(account, XuiContainer.getSession());
                } catch (GenericServiceException ex) {
                    Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            //update or create
            if (isUpdate) {
                if (UtilValidate.isNotEmpty(partyId)) {
                    try {

                        resultMap = dispatcher.runSync("updatePerson", UtilMisc.toMap(
                                "partyId", partyId,
                                "userLogin", userLogin,
                                "firstName", account.getPerson().getfirstName(),
                                "lastName", account.getPerson().getlastName(),
                                "preferredCurrencyUomId", account.getParty().getpreferredCurrencyUomId(),
                                "statusId", account.getParty().getstatusId(),
                                "locale", locale));
                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }

//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                    } catch (Exception exc) {
                        Debug.logError(exc, module);
                    }
                }

            } else {
                try {
                    resultMap = dispatcher.runSync("createPerson", UtilMisc.toMap(
                            "partyId", partyId,
                            "userLogin", userLogin,
                            "locale", locale,
                            "firstName", account.getPerson().getfirstName(),
                            "lastName", account.getPerson().getlastName(),
                            "preferredCurrencyUomId", account.getParty().getpreferredCurrencyUomId(),
                            "statusId", account.getParty().getstatusId()));
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                } catch (GenericServiceException ex) {
                    Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        isUpdate = false;
        ContactList contactMechListToSave = account.getContactMechListToCreate();
        if (contactMechListToSave != null) {
            for (Contact contachMech : contactMechListToSave.getList()) {
//            Contact contact = partyContactMechComposite.getContact();
                String contactMechId = null;

                if (Contact.isPostalAddress(contachMech.getContactMechTypeId())) {

                    //create postal address
                    resultMap = createOrUpdatePostalAddress(contachMech, partyId, false, session);

                    PostalAddress postalAddress = contachMech.getPostalAddress();
                    if (resultMap.get("responseMessage").equals("success")) {
                        contactMechId = (String) resultMap.get("contactMechId");
                        for (PartyContactMechComposite pcmc : account.getPartyContactList().getList()) {
                            Debug.logInfo("pcmc.getPartyContactMech().getcontactMechId(): " + pcmc.getPartyContactMech().getcontactMechId(), module);
                            Debug.logInfo("contachMech.getContactMech().getcontactMechId(): " + contachMech.getContactMech().getcontactMechId(), module);
                            if (pcmc.getPartyContactMech().getcontactMechId().equals(contachMech.getContactMech().getcontactMechId())) {
                                Debug.logInfo("equal: " + contachMech.getContactMech().getcontactMechId(), module);
                                pcmc.getPartyContactMech().setcontactMechId(contactMechId);
                                break;
                            }
                        }
                        //party contact to this new contact mech id
                        postalAddress.setContactMechId(contactMechId);
                        contachMech.getContactMech().setcontactMechId(contactMechId);

                    }

                } else if (Contact.isTelecomNumberAddress(contachMech.getContactMechTypeId())) {

                    //Debug.logInfo("Create Telecom: " + contachMech.getContactMech().getcontactMechId(), module);
                    resultMap = createOrUpdateTelecomNumber(contachMech, partyId, false, session);

                    TelecomNumber telecomNumber = contachMech.getTelecomNumber();
                    if (resultMap.get("responseMessage").equals("success")) {
                        contactMechId = (String) resultMap.get("contactMechId");
                        for (PartyContactMechComposite pcmc : account.getPartyContactList().getList()) {
                            //Debug.logInfo("pcmc.getPartyContactMech().getcontactMechId(): " + pcmc.getPartyContactMech().getcontactMechId(), module);
                            //Debug.logInfo("contachMech.getContactMech().getcontactMechId(): " + contachMech.getContactMech().getcontactMechId(), module);

                            if (pcmc.getPartyContactMech().getcontactMechId().equals(contachMech.getContactMech().getcontactMechId())) {
                                //Debug.logInfo("equal: " + contachMech.getContactMech().getcontactMechId(), module);
                                pcmc.getPartyContactMech().setcontactMechId(contactMechId);
                                break;
                            }
                        }
                        //party contact to this new contact mech id
                        telecomNumber.setcontactMechId(contactMechId);
                        contachMech.getContactMech().setcontactMechId(contactMechId);

                    }
                } else {
                    //Debug.logInfo("Create Else: " + contachMech.getContactMech().getcontactMechId(), module);
                    //create postal address
                    resultMap = createOrUpdateEmailAdress(contachMech, partyId, false, session);

                    if (resultMap.get("responseMessage").equals("success")) {
                        contactMechId = (String) resultMap.get("contactMechId");
                        for (PartyContactMechComposite pcmc : account.getPartyContactList().getList()) {
                            //Debug.logInfo("pcmc.getPartyContactMech().getcontactMechId(): " + pcmc.getPartyContactMech().getcontactMechId(), module);
                            //Debug.logInfo("contachMech.getContactMech().getcontactMechId(): " + contachMech.getContactMech().getcontactMechId(), module);

                            if (pcmc.getPartyContactMech().getcontactMechId().equals(contachMech.getContactMech().getcontactMechId())) {
                                //Debug.logInfo("equal: " + contachMech.getContactMech().getcontactMechId(), module);
                                pcmc.getPartyContactMech().setcontactMechId(contactMechId);
                                break;
                            }
                        }
                        contachMech.getContactMech().setcontactMechId(contactMechId);
                        //party contact to this new contact mech id
                    }

                }
            }
        }

        Debug.logInfo("11111111", module);
        PartyContactMechCompositeList partyContactList = account.getPartyContactList();
        for (PartyContactMechComposite partyContactMechComposite : partyContactList.getList()) {
            //create the puposes
            if (partyContactMechComposite.getPartyContactMech().getcontactMechId() != null) {
                Debug.logInfo("equal partyContactMechComposite.getPartyContactMech().getcontactMechId(): " + partyContactMechComposite.getPartyContactMech().getcontactMechId(), module);
                createContachMechPurposes(partyContactMechComposite.getPartyContactMech().getcontactMechId(), partyContactMechComposite, session);
            }
        }

        //update party roles
        updatePartyRoles(account, session);
    }

    static public List<PartyRole> createSupplierPartyRoles(String partyId) {
        List<PartyRole> rolesList = new ArrayList<PartyRole>();
        final String roleTypeIds[] = {"SUPPLIER", "ACCOUNT", "BILL_FROM_VENDOR", "SHIP_FROM_VENDOR", "SUPPLIER_AGENT"};
        Debug.logInfo("update roles createPartySupplierRoles", module);
        for (String roleTypeId : roleTypeIds) {
            PartyRole prole = new PartyRole();
            prole.setpartyId(partyId);
            prole.setroleTypeId(roleTypeId);
            rolesList.add(prole);
        }
        return rolesList;
    }

    static public List<PartyRole> createCustomerPartyRoles(String partyId) {
        List<PartyRole> rolesList = new ArrayList<PartyRole>();
        final String roleTypeIds[] = {"CUSTOMER", "ACCOUNT", "BILL_TO_CUSTOMER", "END_USER_CUSTOMER", "PLACING_CUSTOMER", "SHIP_TO_CUSTOMER"};
        Debug.logInfo("update roles createPartySupplierRoles", module);
        for (String roleTypeId : roleTypeIds) {
            PartyRole prole = new PartyRole();
            prole.setpartyId(partyId);
            prole.setroleTypeId(roleTypeId);
            rolesList.add(prole);
        }
        return rolesList;
    }

    public static void updatePartyRoles(Account account, XuiSession session) {
        GenericValue partyGen = PartyHelper.getParty(account.getParty().getpartyId(), session.getDelegator());
        List<GenericValue> removelist = new ArrayList<GenericValue>();

        if (partyGen != null) {
            try {
                List<GenericValue> partyRoleList = partyGen.getRelated("PartyRole");
                for (GenericValue partyRoleGen : partyRoleList) {
                    if (account.getPartyRolesList().getPartyFromRole(partyRoleGen.getString("roleTypeId")) == null) {
                        removelist.add(partyRoleGen);
                    }
                }

                session.getDelegator().removeAll(removelist);

            } catch (GenericEntityException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void createPartyRoles(Account account, XuiSession session) {
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        String partyId = account.getParty().getpartyId();
        // GenericValue partyGen = PartyHelper.getParty(partyId, session.getDelegator());
        Debug.logInfo("createPartyRoles partyId: " + partyId, module);
        if (UtilValidate.isNotEmpty(partyId)) {

            for (PartyRoleComposite partyRoleGen : account.getPartyRolesList().getList()) {
                Debug.logInfo("partyRoleGen.getPartyRole().getroleTypeId() : " + partyRoleGen.getPartyRole().getroleTypeId()
                        + " partyId: " + partyId, module);
                try {
                    List<EntityExpr> contactList = UtilMisc.toList(
                            EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, partyRoleGen.getPartyRole().getroleTypeId()),
                            EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId)
                    );

                    List<GenericValue> machList = delegator.findList("PartyRole", EntityCondition.makeCondition(contactList, EntityOperator.AND), null, null, null, false);

                    if (UtilValidate.isEmpty(machList)) {
                        try {
                            // create a new party contact mech for the partyIdTo
                            resultMap = dispatcher.runSync("createPartyRole",
                                    UtilMisc.<String, Object>toMap(
                                            "partyId", partyId,
                                            "userLogin", userLogin,
                                            "roleTypeId", partyRoleGen.getPartyRole().getroleTypeId()
                                    ));
                        } catch (GenericServiceException ex) {
                            Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }

                        if (ServiceUtil.isError(resultMap)) {
                            return;
                        }
                    }
                } catch (GenericEntityException ex) {
                    Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public static void createOrUpdatePartyIdentification(PartyIdentification partyIdentification, XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Locale locale = Locale.getDefault();

        Delegator delegator = session.getDelegator();
        GenericValue partyGenric = null;
        String partyId = partyIdentification.getPartyId();
        String partyIdentificationTypeId = partyIdentification.getPartyIdentificationTypeId();
        try {
            partyGenric = delegator.findByPrimaryKey("PartyIdentification", UtilMisc.toMap("partyId", partyId, "partyIdentificationTypeId", partyIdentificationTypeId));
        } catch (GenericEntityException e) {
            Debug.logWarning(e.getMessage(), module);
        }
        boolean isUpdate = partyGenric != null;
        Debug.logInfo("createOrUpdatePartyIdentification... partyId: " + partyId +  "  partyIdentificationTypeId: " + partyIdentificationTypeId + " IDvALUE: " + partyIdentification.getIdValue(), module);
        if (!isUpdate) {
            try {

                resultMap = dispatcher.runSync("createPartyIdentification", UtilMisc.toMap(
                        "partyId", partyId,
                        "userLogin", userLogin,
                        "locale", locale,
                        "partyIdentificationTypeId", partyIdentificationTypeId,
                        "idValue", partyIdentification.getIdValue()
                ));
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }

            } catch (GenericServiceException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            //Debug.logInfo(" Create : " + Contact.ContactMechTypeId.TELECOM_NUMBER.name(), module);
            try {

                resultMap = dispatcher.runSync("updatePartyIdentification", UtilMisc.toMap(
                        "partyId", partyId,
                        "userLogin", userLogin,
                        "locale", locale,
                        "partyIdentificationTypeId", partyIdentificationTypeId,
                        "idValue", partyIdentification.getIdValue()
                ));

                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }

            } catch (GenericServiceException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static Map<String, Object> createOrUpdateTelecomNumber(Contact contachMech, String partyId, boolean isUpdate, XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Locale locale = Locale.getDefault();
        TelecomNumber telecomNumber = contachMech.getTelecomNumber();
        if (isUpdate == false) {
            try {

                resultMap = dispatcher.runSync("createTelecomNumber", UtilMisc.toMap(
                        "contactMechId", null,
                        //                        "partyId", partyId,
                        "userLogin", userLogin,
                        "locale", locale,
                        "askForName", telecomNumber.getaskForName(),
                        "countryCode", telecomNumber.getcountryCode(),
                        "areaCode", telecomNumber.getareaCode(),
                        "contactNumber", telecomNumber.getcontactNumber()
                ));
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    //Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }

            } catch (GenericServiceException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            //Debug.logInfo(" Create : " + Contact.ContactMechTypeId.TELECOM_NUMBER.name(), module);
            try {

                resultMap = dispatcher.runSync("updateTelecomNumber", UtilMisc.toMap(
                        "contactMechId", contachMech.getContactMech().getcontactMechId(),
                        //                        "partyId", partyId,
                        "userLogin", userLogin,
                        "locale", locale,
                        "askForName", telecomNumber.getaskForName(),
                        "countryCode", telecomNumber.getcountryCode(),
                        "areaCode", telecomNumber.getareaCode(),
                        "contactNumber", telecomNumber.getcontactNumber()
                ));

                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    //Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }

            } catch (GenericServiceException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return resultMap;
    }

    public static Map<String, Object> createOrUpdateEmailAdress(Contact contachMech, String partyId, boolean isUpdate, XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        Locale locale = Locale.getDefault();

        //Debug.logInfo(" Create : " + Contact.ContactMechTypeId.POSTAL_ADDRESS.name(), module);
        try {
            resultMap = dispatcher.runSync("createTelecomNumber", UtilMisc.toMap(
                    "contactMechId", null,
                    "userLogin", userLogin,
                    "locale", locale,
                    "infoString", contachMech.getContactMech().getinfoString()
            ));
            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }

        } catch (GenericServiceException ex) {
            Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultMap;
    }

    public static Map<String, Object> createOrUpdatePostalAddress(Contact contachMech, String partyId, boolean isUpdate, XuiSession session) {

        GenericValue partyGenric = null;
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        PostalAddress postalAddress = contachMech.getPostalAddress();
        //update or create
        if (isUpdate) {
            if (UtilValidate.isNotEmpty(partyId)) {
                try {
                    //Debug.logInfo(" Update: " + Contact.ContactMechTypeId.POSTAL_ADDRESS.name(), module);
                    resultMap = dispatcher.runSync("updatePostalAddress", UtilMisc.toMap(
                            "contactMechId", postalAddress.getContactMechId(),
                            "partyId", partyId,
                            "userLogin", userLogin,
                            "locale", locale,
                            "toName", postalAddress.getToName(),
                            "attnName", postalAddress.getAttnName(),
                            "address1", postalAddress.getAddress1(),
                            "address2", postalAddress.getAddress2(),
                            "directions", postalAddress.getDirections(),
                            "city", postalAddress.getCity(),
                            "postalCode", postalAddress.getPostalCode(),
                            "postalCodeExt", postalAddress.getpostalCodeExt(),
                            "stateProvinceGeoId", postalAddress.getstateProvinceGeoId(),
                            "countryGeoId", postalAddress.getcountryGeoId(),
                            "postalCodeGeoId", postalAddress.getpostalCodeGeoId()
                    ));
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }

//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                }
            }

        } else {
            //Debug.logInfo(" Create : " + Contact.ContactMechTypeId.POSTAL_ADDRESS.name(), module);
            try {
                resultMap = dispatcher.runSync("createPostalAddress", UtilMisc.toMap(
                        "contactMechId", null,
                        //                      "partyId", partyId,
                        "userLogin", userLogin,
                        "locale", locale,
                        "toName", postalAddress.getToName(),
                        "attnName", postalAddress.getAttnName(),
                        "address1", postalAddress.getAddress1(),
                        "address2", postalAddress.getAddress2(),
                        "directions", postalAddress.getDirections(),
                        "city", postalAddress.getCity(),
                        "postalCode", postalAddress.getPostalCode(),
                        "postalCodeExt", postalAddress.getpostalCodeExt(),
                        "stateProvinceGeoId", postalAddress.getstateProvinceGeoId(),
                        "countryGeoId", postalAddress.getcountryGeoId(),
                        "postalCodeGeoId", postalAddress.getpostalCodeGeoId()
                ));
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }

            } catch (GenericServiceException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultMap;
    }

    public static void createContachMechPurposes(String realMechId, PartyContactMechComposite partyContactMechComposite, XuiSession session) {
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        String partyId = partyContactMechComposite.getPartyContactMech().getpartyId();
        String contactMechTypeId = partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId();
        //Debug.logInfo("createContachMechPurposes realMechId: " + realMechId + " partyId: " + partyId, module);
// get the contactMechId
        String contactMechId = realMechId;
        if (UtilValidate.isNotEmpty(partyId)) {
            try {
                try {

                    List<EntityExpr> contactList = UtilMisc.toList(
                            EntityCondition.makeCondition("contactMechId", EntityOperator.EQUALS, contactMechId),
                            EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId)
                    );

                    List<GenericValue> machList = delegator.findList("PartyContactMech", EntityCondition.makeCondition(contactList, EntityOperator.AND), null, null, null, false);

                    if (UtilValidate.isEmpty(machList)) {
                        // create a new party contact mech for the partyIdTo
                        resultMap = dispatcher.runSync("createPartyContactMech",
                                UtilMisc.<String, Object>toMap(
                                        "partyId", partyId,
                                        "userLogin", userLogin,
                                        "contactMechId", contactMechId,
                                        "contactMechTypeId", contactMechTypeId,
                                        "fromDate", UtilDateTime.nowTimestamp()
                                ));

                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }

                        if (ServiceUtil.isError(resultMap)) {
                            return;
                        }
                    }

                } catch (GenericEntityException e) {
                    Debug.logWarning(e.getMessage(), module);
                }

                //Debug.logInfo("createContachMechPurposes realMechId: " + realMechId, module);
                PartyContactMechPurposeList partyContactMechPurposeList = partyContactMechComposite.getPartyContactMechPurposeList();
                for (PartyContactMechPurposeComposite composite : partyContactMechPurposeList.getList()) {
//            Debug.logInfo("Contact.ContactMechTypeId.POSTAL_ADDRESS: " + Contact.ContactMechTypeId.POSTAL_ADDRESS.name(), module);
                    PartyContactMechPurpose purposeGen = composite.getPartyContactMechPurpose();

                    String contactMechPurposeTypeId = purposeGen.getcontactMechPurposeTypeId();

                    List<EntityExpr> contactPurposeList = UtilMisc.toList(
                            EntityCondition.makeCondition("contactMechId", EntityOperator.EQUALS, contactMechId),
                            EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId),
                            EntityCondition.makeCondition("contactMechPurposeTypeId", EntityOperator.EQUALS, contactMechPurposeTypeId)
                    );

                    List<GenericValue> purposeList = delegator.findList("PartyContactMechPurpose", EntityCondition.makeCondition(contactPurposeList, EntityOperator.AND), null, null, null, false);

                    if (purposeList.isEmpty()) {

                        resultMap = dispatcher.runSync("createPartyContactMechPurpose", UtilMisc.toMap(
                                "partyId", partyId,
                                "userLogin", userLogin,
                                "contactMechId", contactMechId,
                                "contactMechPurposeTypeId", contactMechPurposeTypeId,
                                "locale", locale));
                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }
                    }
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);

                }
            } catch (Exception exc) {
                Debug.logError(exc, module);
            }
        }
    }

    public static Map<String, Object> getContachMechIdMap(Map<String, String> mapAccount) {
        Map<String, Object> resultMap = FastMap.newInstance();

        if (mapAccount.get("ADDRESS1") != null
                && UtilValidate.isNotEmpty(mapAccount.get("ADDRESS1"))) {

            PostalAddress postalAddress = new PostalAddress();
            postalAddress.setAttnName(mapAccount.get("ATTN_NAME"));
            postalAddress.setAddress1(mapAccount.get("ADDRESS1"));
            postalAddress.setAddress2(mapAccount.get("ADDRESS2"));
            postalAddress.setDirections(mapAccount.get("DIRECTIONS"));
            postalAddress.setCity(mapAccount.get("CITY"));
            postalAddress.setpostalCode(mapAccount.get("POSTAL_CODE"));
            postalAddress.setpostalCodeExt(mapAccount.get("POSTAL_CODE_EXT"));
            postalAddress.setcountryGeoId(mapAccount.get("COUNTRY_GEO_ID"));
            postalAddress.setstateProvinceGeoId(mapAccount.get("STATE_PROVINCE_GEO_ID"));
            postalAddress.setcountyGeoId(mapAccount.get("COUNTY_GEO_ID"));
            postalAddress.setpostalCodeGeoId(mapAccount.get("POSTAL_CODE_GEO_ID"));
            postalAddress.setgeoPointId(mapAccount.get("GEO_POINT_ID"));
            resultMap.put("mainAddress", postalAddress);
        }
        if (mapAccount.get("TELE_PHONE") != null
                && UtilValidate.isNotEmpty(mapAccount.get(mapAccount.get("TELE_PHONE")))) {

            TelecomNumber telNumber = new TelecomNumber();
            telNumber.setcontactNumber(mapAccount.get("TELE_PHONE"));
            resultMap.put("mainTelephone", telNumber);
        }

        if (mapAccount.get("FAX_NUMBER") != null
                && UtilValidate.isNotEmpty(mapAccount.get(mapAccount.get("FAX_NUMBER")))) {
            TelecomNumber faxNumber = new TelecomNumber();
            faxNumber.setcontactNumber(mapAccount.get("FAX_NUMBER"));
            resultMap.put("mainFaxphone", faxNumber);
        }

        if (mapAccount.get("DEL_ADDRESS1") != null
                && UtilValidate.isNotEmpty(mapAccount.get(mapAccount.get("DEL_ADDRESS1")))) {
            PostalAddress postalAddress = new PostalAddress();
            postalAddress.setAttnName(mapAccount.get("DEL_ATTN_NAME"));
            postalAddress.setAddress1(mapAccount.get("DEL_ADDRESS1"));
            postalAddress.setAddress2(mapAccount.get("DEL_ADDRESS2"));
            postalAddress.setDirections(mapAccount.get("DEL_DIRECTIONS"));
            postalAddress.setCity(mapAccount.get("DEL_CITY"));
            postalAddress.setpostalCode(mapAccount.get("DEL_POSTAL_CODE"));
            postalAddress.setpostalCodeExt(mapAccount.get("DEL_POSTAL_CODE_EXT"));
            postalAddress.setcountryGeoId(mapAccount.get("DEL_COUNTRY_GEO_ID"));
            postalAddress.setstateProvinceGeoId(mapAccount.get("DEL_STATE_PROVINCE_GEO_ID"));
            postalAddress.setcountyGeoId(mapAccount.get("DEL_COUNTY_GEO_ID"));
            postalAddress.setpostalCodeGeoId(mapAccount.get("DEL_POSTAL_CODE_GEO_ID"));
            postalAddress.setgeoPointId(mapAccount.get("DEL_GEO_POINT_ID"));
            resultMap.put("shippingAddress", postalAddress);
        }

        if (mapAccount.get("DEL_TELE_PHONE") != null
                && UtilValidate.isNotEmpty(mapAccount.get(mapAccount.get("DEL_TELE_PHONE")))) {

            TelecomNumber telNumber = new TelecomNumber();
            telNumber.setcontactNumber(mapAccount.get("DEL_TELE_PHONE"));
            resultMap.put("deliveryTelephone", telNumber);
        }

        if (mapAccount.get("DEL_FAX_NUMBER") != null
                && UtilValidate.isNotEmpty(mapAccount.get(mapAccount.get("DEL_FAX_NUMBER")))) {
            TelecomNumber faxNumber = new TelecomNumber();
            faxNumber.setcontactNumber(mapAccount.get("DEL_FAX_NUMBER"));
            resultMap.put("deliveryFaxphone", faxNumber);
        }
        return resultMap;
    }

    public static Account createPartyComposite(String partyTypeId) {
        Account account = new Account();
        account.newComposite();
        account.getParty().setpartyTypeId(partyTypeId);
        account.getParty().setpreferredCurrencyUomId((String) ControllerOptions.getSession().getAttribute("currency"));
        account.getParty().setstatusId("PARTY_ENABLED");
        PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
        account.setPartyContactList(partyContactList);
        account.setPartyRolesList(new PartyRolesList());
        account.setContactMechListToCreate(new ContactList());
        return account;
    }

    public static Account createNewEmptyAccount(XuiSession session) {
        Account account = new Account();
        account.newComposite();
        account.getParty().setpartyTypeId("PARTY_GROUP");
        account.getParty().setstatusId("PARTY_ENABLED");        
        PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
        account.setPartyContactList(partyContactList);
        account.setPartyRolesList(new PartyRolesList());
        account.setContactMechListToCreate(new ContactList());
        return account;
    }

    public static Account createNewPersonAccount() {
        Account account = new Account();
        account.newComposite();
        account.getParty().setpartyTypeId("PERSON");
        PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
        account.setPartyContactList(partyContactList);
        account.setPartyRolesList(new PartyRolesList());
        return account;
    }

    public static Account createAccount(Map<String, String> mapAccount, XuiSession session) {
        //Debug.logInfo("0", module);
        Account account = new Account();
        account.newComposite();

        //party
        Party party = account.getParty();
        party.setpartyId(mapAccount.get("PARTY_ID"));
        party.setpartyTypeId("Y".equals(mapAccount.get("ISPERSON")) ? "PERSON" : "PARTY_GROUP");
        try {
            UomCurrencySingleton.getUom(mapAccount.get("CURRENCYUOM"));
            party.setpreferredCurrencyUomId(mapAccount.get("CURRENCYUOM"));
        } catch (Exception e) {
            Debug.logInfo("get currency : " + mapAccount.get("CURRENCYUOM"), module);
        }
        party.setstatusId("PARTY_ENABLED");
        party.setcreatedByUserLogin(session.getUserId());

        if ("PARTY_GROUP".equals(party.getpartyTypeId())) {
            PartyGroup pg = account.getPartyGroup();
            pg.setpartyId(mapAccount.get("PARTY_ID"));
            pg.setgroupName(mapAccount.get("TO_NAME"));
            pg.setgroupNameLocal(mapAccount.get("TO_NAME"));
        } else {
            Person person = account.getPerson();
            person.setpartyId(mapAccount.get("PARTY_ID"));
            person.setfirstName(mapAccount.get("TO_NAME"));
            person.setlastName(mapAccount.get("ATTN_NAME"));
        }
        //Debug.logInfo("1", module);
        Map<String, Object> dataObjectList = getContachMechIdMap(mapAccount);
        //Debug.logInfo("2 Size " + dataObjectList.size(), module);
        ContactList contactMechList = new ContactList();
        account.setContactMechListToCreate(contactMechList);

        PartyContactMechCompositeList partyContactList = new PartyContactMechCompositeList();
        account.setPartyContactList(partyContactList);

//        PartyContactMechPurposeList partyContactMechPurposeList = new PartyContactMechPurposeList();
        int index = 1;

        if (dataObjectList.containsKey("mainAddress")) {
            Debug.logInfo("mainAddress: " + " found ", module);
            //Debug.logInfo("3", module);
            String tempContachMechId = "0000" + index++;
            Contact mainContact = new Contact(Contact.POSTAL_ADDRESS);
            ContactMech contactMechAddress = new ContactMech();
            contactMechAddress.setcontactMechId(tempContachMechId);
            contactMechAddress.setcontactMechTypeId(Contact.POSTAL_ADDRESS);
            mainContact.setContactMech(contactMechAddress);

            PostalAddress postalAddress = (PostalAddress) dataObjectList.get("mainAddress");

            postalAddress.setContactMechId(contactMechAddress.getcontactMechId());
            mainContact.setPostalAddress(postalAddress);
            contactMechList.add(mainContact);

            //general
            PartyContactMechComposite partyContactMechComposite = new PartyContactMechComposite();
            //create PartyContactMech
            PartyContactMech partyContactMech = new PartyContactMech();
            partyContactMech.setpartyId(party.getpartyId());
            partyContactMech.setcontactMechId(contactMechAddress.getcontactMechId());

            partyContactMechComposite.setPartyContactMech(partyContactMech);
            //set the contact
            partyContactMechComposite.setContact(mainContact);
            partyContactList.add(partyContactMechComposite);

            PartyContactMechPurposeComposite compositeGeneralLocation = createPartyContactMechPurposeComposite("GENERAL_LOCATION",
                    contactMechAddress.getcontactMechId(), party.getpartyId());
            partyContactMechComposite.getPartyContactMechPurposeList().add(compositeGeneralLocation);

            PartyContactMechPurposeComposite compositeBillLocation = createPartyContactMechPurposeComposite("BILLING_LOCATION",
                    contactMechAddress.getcontactMechId(), party.getpartyId());
            partyContactMechComposite.getPartyContactMechPurposeList().add(compositeBillLocation);

            if (dataObjectList.containsKey("shippingAddress") == false) {
                PartyContactMechPurposeComposite compositeShippLocation = createPartyContactMechPurposeComposite("SHIPPING_LOCATION",
                        contactMechAddress.getcontactMechId(), party.getpartyId());
                partyContactMechComposite.getPartyContactMechPurposeList().add(compositeShippLocation);
            }
        }

        if (dataObjectList.containsKey("shippingAddress")) {
            Debug.logInfo("shippingAddress: " + " found ", module);
            //Debug.logInfo("3", module);
            String tempContachMechId = "0000" + index++;
            Contact mainContact = new Contact(Contact.POSTAL_ADDRESS);
            ContactMech contactMechAddress = new ContactMech();
            contactMechAddress.setcontactMechId(tempContachMechId);
            contactMechAddress.setcontactMechTypeId(Contact.POSTAL_ADDRESS);
            mainContact.setContactMech(contactMechAddress);

            PostalAddress postalAddress = (PostalAddress) dataObjectList.get("shippingAddress");
            postalAddress.setContactMechId(contactMechAddress.getcontactMechId());

            mainContact.setPostalAddress(postalAddress);

            contactMechList.add(mainContact);

            //general
            PartyContactMechComposite partyContactMechComposite = new PartyContactMechComposite();
            //create PartyContactMech
            PartyContactMech partyContactMech = new PartyContactMech();
            partyContactMech.setpartyId(party.getpartyId());
            partyContactMech.setcontactMechId(contactMechAddress.getcontactMechId());

            partyContactMechComposite.setPartyContactMech(partyContactMech);
            //set the contact
            partyContactMechComposite.setContact(mainContact);
            partyContactList.add(partyContactMechComposite);

            PartyContactMechPurposeComposite compositeShippLocation = createPartyContactMechPurposeComposite("SHIPPING_LOCATION",
                    contactMechAddress.getcontactMechId(), party.getpartyId());
            partyContactMechComposite.getPartyContactMechPurposeList().add(compositeShippLocation);
        }

        if (dataObjectList.containsKey("mainTelephone")) {
            Debug.logInfo("mainTelephone: " + " found ", module);
            String tempContachMechId = "0000" + ++index;
            Contact mainTelContact = new Contact(Contact.TELECOM_NUMBER);

            ContactMech contactMechTel = new ContactMech();
            contactMechTel.setcontactMechId(tempContachMechId);
            contactMechTel.setcontactMechTypeId(Contact.TELECOM_NUMBER);

            TelecomNumber telecomNumber = (TelecomNumber) dataObjectList.get("mainTelephone");
            mainTelContact.setTelecomNumber(telecomNumber);
            mainTelContact.setContactMech(contactMechTel);
            contactMechList.add(mainTelContact);

            //telephone
            PartyContactMechComposite partyTeleContact = new PartyContactMechComposite();
            partyTeleContact.setContact(mainTelContact);

            PartyContactMech partyTeleContactMech = new PartyContactMech();
            partyTeleContactMech.setpartyId(party.getpartyId());
            partyTeleContactMech.setcontactMechId(contactMechTel.getcontactMechId());
            partyTeleContact.setPartyContactMech(partyTeleContactMech);
            partyContactList.add(partyTeleContact);

            PartyContactMechPurposeComposite compositeTel = createPartyContactMechPurposeComposite("PHONE_WORK",
                    contactMechTel.getcontactMechId(), party.getpartyId());
            partyTeleContact.getPartyContactMechPurposeList().add(compositeTel);
        }

        if (dataObjectList.containsKey("deliveryTelephone")) {
            Debug.logInfo("deliveryTelephone: " + " found ", module);
            String tempContachMechId = "0000" + ++index;
            Contact mainTelContact = new Contact(Contact.TELECOM_NUMBER);

            ContactMech contactMechTel = new ContactMech();
            contactMechTel.setcontactMechId(tempContachMechId);
            contactMechTel.setcontactMechTypeId(Contact.TELECOM_NUMBER);

            TelecomNumber telecomNumber = (TelecomNumber) dataObjectList.get("deliveryTelephone");
            mainTelContact.setTelecomNumber(telecomNumber);
            mainTelContact.setContactMech(contactMechTel);
            contactMechList.add(mainTelContact);

            //telephone
            PartyContactMechComposite partyTeleContact = new PartyContactMechComposite();
            partyTeleContact.setContact(mainTelContact);

            PartyContactMech partyTeleContactMech = new PartyContactMech();
            partyTeleContactMech.setpartyId(party.getpartyId());
            partyTeleContactMech.setcontactMechId(contactMechTel.getcontactMechId());
            partyTeleContact.setPartyContactMech(partyTeleContactMech);
            partyContactList.add(partyTeleContact);

            PartyContactMechPurposeComposite compositeTel = createPartyContactMechPurposeComposite("PHONE_SHIPPING",
                    contactMechTel.getcontactMechId(), party.getpartyId());
            partyTeleContact.getPartyContactMechPurposeList().add(compositeTel);
        }

//        if (mapAccount.get("FAX_NUMBER") != null && mapAccount.get("FAX_NUMBER").isEmpty() == false) {
        if (dataObjectList.containsKey("mainFaxphone")) {
            Debug.logInfo("mainFaxphone: " + " found ", module);
            String tempContachMechId = "0000" + ++index;

            Contact mainFaxContact = new Contact(Contact.TELECOM_NUMBER);

            ContactMech contactMechFax = new ContactMech();
            contactMechFax.setcontactMechId(tempContachMechId);
            contactMechFax.setcontactMechTypeId(Contact.TELECOM_NUMBER);

            TelecomNumber faxNumber = (TelecomNumber) dataObjectList.get("mainFaxphone");
            faxNumber.setcontactMechId(contactMechFax.getcontactMechId());
            mainFaxContact.setTelecomNumber(faxNumber);
            mainFaxContact.setContactMech(contactMechFax);
            contactMechList.add(mainFaxContact);

            //fax
            PartyContactMechComposite faxPartyContactMechComposite = new PartyContactMechComposite();
            faxPartyContactMechComposite.setContact(mainFaxContact);

            PartyContactMech faxPartyContactMech = new PartyContactMech();
            faxPartyContactMech.setpartyId(party.getpartyId());
            faxPartyContactMech.setcontactMechId(contactMechFax.getcontactMechId());
            faxPartyContactMechComposite.setPartyContactMech(faxPartyContactMech);
            partyContactList.add(faxPartyContactMechComposite);

            PartyContactMechPurposeComposite compositeFax = createPartyContactMechPurposeComposite("FAX_NUMBER",
                    contactMechFax.getcontactMechId(), party.getpartyId());

            faxPartyContactMechComposite.getPartyContactMechPurposeList().add(compositeFax);

        }

        if (dataObjectList.containsKey("deliveryFaxphone")) {
            Debug.logInfo("deliveryFaxphone: " + " found ", module);
            String tempContachMechId = "0000" + ++index;

            Contact mainFaxContact = new Contact(Contact.TELECOM_NUMBER);

            ContactMech contactMechFax = new ContactMech();
            contactMechFax.setcontactMechId(tempContachMechId);
            contactMechFax.setcontactMechTypeId(Contact.TELECOM_NUMBER);

            TelecomNumber faxNumber = (TelecomNumber) dataObjectList.get("deliveryFaxphone");
            faxNumber.setcontactMechId(contactMechFax.getcontactMechId());
            mainFaxContact.setTelecomNumber(faxNumber);
            mainFaxContact.setContactMech(contactMechFax);
            contactMechList.add(mainFaxContact);

            //fax
            PartyContactMechComposite faxPartyContactMechComposite = new PartyContactMechComposite();
            faxPartyContactMechComposite.setContact(mainFaxContact);

            PartyContactMech faxPartyContactMech = new PartyContactMech();
            faxPartyContactMech.setpartyId(party.getpartyId());
            faxPartyContactMech.setcontactMechId(contactMechFax.getcontactMechId());
            faxPartyContactMechComposite.setPartyContactMech(faxPartyContactMech);
            partyContactList.add(faxPartyContactMechComposite);

            PartyContactMechPurposeComposite compositeFax = createPartyContactMechPurposeComposite("FAX_SHIPPING",
                    contactMechFax.getcontactMechId(), party.getpartyId());

            faxPartyContactMechComposite.getPartyContactMechPurposeList().add(compositeFax);

        }

//        account.outputToDebug();
        return account;
    }

    static public PartyContactMechComposite createPartyContactMechComposite(String partyId, String contactMechTypeId) {

//        PartyContactMechPurposeComposite pcmpc = LoadAccountWorker.createPartyContactMechPurposeComposite(null, null, partyId);
//      pcmc.getPartyContactMechPurposeList().add(pcmpc);
        PartyContactMechComposite pcmc = new PartyContactMechComposite();

        pcmc.setContact(new Contact());
        pcmc.getContact().setContactMech(new ContactMech());
        if (Contact.isPostalAddress(contactMechTypeId)) {
            PostalAddress pa = new PostalAddress();
            pcmc.getContact().getContactMech().setcontactMechTypeId(Contact.POSTAL_ADDRESS);
            pcmc.getContact().setPostalAddress(pa);
        } else {
            TelecomNumber pa = new TelecomNumber();
            pcmc.getContact().getContactMech().setcontactMechTypeId(Contact.TELECOM_NUMBER);
            pcmc.getContact().setTelecomNumber(pa);
        }

        pcmc.getContact().setContactMechTypeId(contactMechTypeId);
        pcmc.setPartyContactMech(new PartyContactMech());
        pcmc.getPartyContactMech().setpartyId(partyId);

        return pcmc;
    }

    static public PartyContactMechPurposeComposite createPartyContactMechPurposeComposite(
            String contactMechPurposeTypeId, String contactMechId, String partyId) {

        //billing location
        PartyContactMechPurpose partyContactMechPurpose = new PartyContactMechPurpose();
        partyContactMechPurpose.setpartyId(partyId);
        partyContactMechPurpose.setcontactMechId(contactMechId);
        partyContactMechPurpose.setcontactMechPurposeTypeId(contactMechPurposeTypeId);

        PartyContactMechPurposeComposite partyContactMechPurposeComposite = new PartyContactMechPurposeComposite();
        partyContactMechPurposeComposite.setPartyContactMechPurpose(partyContactMechPurpose);

        return partyContactMechPurposeComposite;
    }

    static public String savePostalAddressContachhMeach(PostalAddress value, XuiSession session) {

        GenericValue partyGenric = null;
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        Map<String, Object> inMap = getServiceDataMap(value);

        inMap.put("userLogin", userLogin);

        String contactMechId = value.getContactMechId();
        if (UtilValidate.isEmpty(contactMechId)) {
            try {
                Map<String, Object> resultMap = dispatcher.runSync("createPostalAddress", inMap);
                contactMechId = (String) resultMap.get("contactMechId");
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
                //Debug.logInfo("Test sales order with id [" + contactMechId + "] has been shipped", module);
            } catch (GenericServiceException ex) {
                Debug.logError(ex, module);
            } catch (Exception exc) {
                Debug.logWarning("Unable to quick ship test sales order with id [" + contactMechId + "] with error: " + exc.getMessage(), module);
            }
        } else {
            try {

                //Debug.logInfo("inMap.toString(): " + inMap.toString(), "module");
                Map<String, Object> resultMap = dispatcher.runSync("updatePostalAddress", inMap);
                contactMechId = (String) resultMap.get("contactMechId");
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }

            } catch (GenericServiceException ex) {
                Debug.logError(ex, module);
            } catch (Exception exc) {
                Debug.logWarning("Unable to quick ship test sales order with id [" + contactMechId + "] with error: " + exc.getMessage(), module);
            }
        }

        return contactMechId;
    }

    static public List<GenericValue> getPartyByRole(String roleTypeId, XuiSession session) {

        List<GenericValue> roleList = PosProductHelper.getGenericValueLists("PartyRole", "roleTypeId", roleTypeId, session.getDelegator());
        return roleList;
    }

    static public Map<String, Object> getServiceDataMap(PostalAddress value) {

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("contactMechId", value.getContactMechId());
        map.put("toName", value.getToName());
        map.put("attnName", value.getAttnName());
        map.put("address1", value.getAddress1());
        map.put("address2", value.getAddress2());
        map.put("directions", value.getDirections());
        map.put("city", value.getCity());
        map.put("postalCode", value.getPostalCode());
        map.put("postalCodeExt", value.getpostalCodeExt());
        map.put("countryGeoId", value.getcountryGeoId());
        map.put("stateProvinceGeoId", value.getstateProvinceGeoId());
        map.put("countyGeoId", value.getcountyGeoId());
        map.put("postalCodeGeoId", value.getpostalCodeGeoId());
        map.put("geoPointId", value.getgeoPointId());

        return map;
    }

    static public void createPartyContactMechPurpose(PartyContactMechPurpose purposeComposite, XuiSession session
    ) throws GeneralException {

        // convert the int to a purpose type ID
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();

        String partyId = purposeComposite.getpartyId();
        String contactMechId = purposeComposite.getcontactMechId();
        String contactMechPurposeTypeId = purposeComposite.getcontactMechPurposeTypeId();

        // check to make sure the purpose doesn't already exist
        List<GenericValue> values = session.getDelegator().findByAnd("PartyContactMechPurpose", UtilMisc.toMap("partyId", partyId,
                "contactMechId", contactMechId,
                "contactMechPurposeTypeId", contactMechPurposeTypeId));

        if (UtilValidate.isEmpty(values)) {

            Map<String, Object> addPurposeMap = FastMap.newInstance();
            addPurposeMap.put("contactMechId", contactMechId);
            addPurposeMap.put("partyId", partyId);
            addPurposeMap.put("contactMechPurposeTypeId", contactMechPurposeTypeId);
            addPurposeMap.put("userLogin", userLogin);

            Map<String, Object> addPurposeResp = dispatcher.runSync("createPartyContactMechPurpose", addPurposeMap);
            if (addPurposeResp != null && ServiceUtil.isError(addPurposeResp)) {
                throw new GeneralException(ServiceUtil.getErrorMessage(addPurposeResp));
            }
        }
    }

    public static Map<String, Object> findParty(Map<String, Object> findOption, XuiSession session) {
        GenericValue partyGenric = null;
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        findOption.put("userLogin", userLogin);
        findOption.put("locale", locale);
        //Debug.logInfo(" Create : " + Contact.ContactMechTypeId.POSTAL_ADDRESS.name(), module);
        try {
            resultMap = dispatcher.runSync("findParty", findOption);

            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                if (entryDept.getKey() != null && entryDept.getValue() != null) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
            }

        } catch (GenericServiceException ex) {
            Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultMap;
    }

    public static void saveUserLogin(UserLogin modUserLogin, String currentPasswordVerify, XuiSession session) throws Exception {
        try {

            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();
            System.out.println("1 Start product modUserLogin.getpartyId: " + modUserLogin.getpartyId());

            String userLoginId = modUserLogin.getuserLoginId();
            System.out.println("Start modUserLogin: " + userLoginId);
            GenericValue userLoginGV = delegator.findByPrimaryKey("UserLogin",
                    UtilMisc.toMap("userLoginId", userLoginId)
            );

            Map<String, Object> toStore = new HashMap<String, Object>(); //modUserLogin.getValuesMap();
            toStore.put("userLogin", userLogin);
            toStore.put("locale", locale);

            if (userLoginGV == null) {
                try {
                    System.out.println("Create userLoginId: " + userLoginId);

//                    toStore.put("currentPasswordVerify", currentPasswordVerify);
                    toStore.put("userLoginId", modUserLogin.getuserLoginId());
                    toStore.put("currentPassword", modUserLogin.getcurrentPassword());
                    toStore.put("currentPasswordVerify", currentPasswordVerify);
                    toStore.put("partyId", modUserLogin.getpartyId());
                    resultMap = session.getDispatcher().runSync("createUserLogin", toStore);
                    /*resultMap = dispatcher.runSync("createUserLogin",
                     UtilMisc.toMap("userLogin", userLogin,
                     "userLoginId", email,
                     "currentPassword", phone,
                     "currentPasswordVerify", phone,
                     "partyId", partyId));*/
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                    System.out.println("create createUserLogin");
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                    throw exc;
                }

            } else {
                try {
                    System.out.println("Update userLoginId: " + userLoginId);
                    toStore.put("userLoginId", modUserLogin.getuserLoginId());
                    //                   toStore.put("currentPassword", modUserLogin.getcurrentPassword());
//                    toStore.put("currentPasswordVerify", currentPasswordVerify);
//                    toStore.put("partyId", modUserLogin.getpartyId());
                    toStore.put("enabled", modUserLogin.getenabled());

                    resultMap = session.getDispatcher().runSync("updateUserLoginSecurity", toStore);
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }

//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                    throw exc;
                }

            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadProductCatalogWorker.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    static public void updatePassword(UserLogin modUserLogin, String newPassword, String newPasswordVerify, XuiSession session) throws Exception {
        try {

            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();
            System.out.println("1 Start product modUserLogin.getpartyId: " + modUserLogin.getpartyId());

            String userLoginId = modUserLogin.getuserLoginId();
            System.out.println("Start modUserLogin: " + userLoginId);
            GenericValue userLoginGV = delegator.findByPrimaryKey("UserLogin",
                    UtilMisc.toMap("userLoginId", userLoginId)
            );

            Map<String, Object> toStore = new HashMap<String, Object>(); //modUserLogin.getValuesMap();
            toStore.put("userLogin", userLogin);
            toStore.put("locale", locale);

            if (userLoginGV != null) {
                try {
                    System.out.println("updatePassword userLoginId: " + userLoginId);

//                    toStore.put("currentPasswordVerify", currentPasswordVerify);
                    toStore.put("userLoginId", modUserLogin.getuserLoginId());
                    toStore.put("currentPassword", modUserLogin.getcurrentPassword());
                    toStore.put("newPassword", newPassword);
                    toStore.put("newPasswordVerify", newPasswordVerify);
                    toStore.put("passwordHint", modUserLogin.getpasswordHint());
                    resultMap = session.getDispatcher().runSync("updatePassword", toStore);
                    /*resultMap = dispatcher.runSync("createUserLogin",
                     UtilMisc.toMap("userLogin", userLogin,
                     "userLoginId", email,
                     "currentPassword", phone,
                     "currentPasswordVerify", phone,
                     "partyId", partyId));*/
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                    System.out.println("create createUserLogin");
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                    throw exc;
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadProductCatalogWorker.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }
}
