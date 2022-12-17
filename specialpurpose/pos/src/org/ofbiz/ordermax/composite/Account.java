/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.util.List;
import static javax.swing.Action.NAME;
import javolution.util.FastList;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Party;
import org.ofbiz.ordermax.entity.PartyGroup;
import org.ofbiz.ordermax.entity.PartyIdentification;
import org.ofbiz.ordermax.entity.Person;
import org.ofbiz.ordermax.entity.UserLogin;

/**
 *
 * @author siranjeev
 */
public class Account implements CompositeInterface, DisplayNameInterface {

    public String getPartyTypeId() {
        return party.getpartyTypeId();
    }

    public PartyContactMechCompositeList getPartyContactList() {
        return partyContactList;
    }

    public void setPartyContactList(PartyContactMechCompositeList partyContactList) {
        this.partyContactList = partyContactList;
    }

    public PartyContactMechComposite getPartyContact(String contachMachId) {
        return partyContactList.getPartyContact(contachMachId);
    }

    public enum PartyTypeId {

        PERSON, PARTY_GROUP
    };

    Person person = null;
    Party party = null;
    PartyGroup partyGroup = null;
    private PartyIdentification partyIdentification = null;

    public PartyIdentification getPartyIdentification() {
        return partyIdentification;
    }

    public void setPartyIdentification(PartyIdentification partyIdentification) {
        this.partyIdentification = partyIdentification;

    }
    /*  List<BillingAccount> billingAccounts = null;

     public List<BillingAccount> getBillingAccounts() {
     return billingAccounts;
     }

     public void setBillingAccounts(List<BillingAccount> billingAccounts) {
     this.billingAccounts = billingAccounts;
     }
     */
    List<BillingAccountComposite> billingAccountComposites = null;

    public List<BillingAccountComposite> getBillingAccountComposite() {
        return billingAccountComposites;
    }

    public void setBillingAccountComposite(List<BillingAccountComposite> billingAccountComposite) {
        this.billingAccountComposites = billingAccountComposite;
    }

    public List<BillingAccount> getBillingAccounts() {
        List<BillingAccount> billingAccounts = FastList.newInstance();
        for (BillingAccountComposite billingAccountComposite : billingAccountComposites) {
            billingAccounts.add(billingAccountComposite.getBillingAccount());
        }
        return billingAccounts;
    }

    public BillingAccountComposite getNewBillingAccountComposite() {

        for (BillingAccountComposite billingAccountComposite : billingAccountComposites) {
            if (UtilValidate.isEmpty(billingAccountComposite.getBillingAccount().getbillingAccountId())) {
                return billingAccountComposite;
            }
        }
        return null;
    }

    //private PartyTypeId partyTypeId;
    private PartyContactMechCompositeList partyContactList = null;
    private PartyRolesList partyRolesList = null;
    ListAdapterListModel<UserLogin> userLoginList = null;

    public ListAdapterListModel<UserLogin> getUserLoginList() {
        //load on demand
        if (userLoginList == null) {
            userLoginList = new ListAdapterListModel<UserLogin>();
            if (UtilValidate.isNotEmpty(this.getParty())) {
                List<GenericValue> userLoginListGV = PosProductHelper.getGenericValueLists("UserLogin", "partyId", getParty().getpartyId(), XuiContainer.getSession().getDelegator());
                Debug.logInfo("userLoginListGV: start ", NAME);

                for (GenericValue gv : userLoginListGV) {
                    UserLogin val = new UserLogin(gv);
                    userLoginList.add(val);
                    Debug.logInfo("userLoginListGV: " + val.getuserLoginId(), NAME);
                }
            }
        }
        return userLoginList;
    }

    public void setUserLoginList(ListAdapterListModel<UserLogin> userLoginList) {
        this.userLoginList = userLoginList;
    }

    public PartyRolesList getPartyRolesList() {
        return partyRolesList;
    }

    public void setPartyRolesList(PartyRolesList partyRolesList) {
        this.partyRolesList = partyRolesList;
    }

    public Account() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public PartyGroup getPartyGroup() {
        return partyGroup;
    }

    public void setPartyGroup(PartyGroup partyGroup) {
        this.partyGroup = partyGroup;
    }

    @Override
    public boolean newComposite() {
        boolean result = true;
        person = new Person();
        party = new Party();
        partyGroup = new PartyGroup();
        partyIdentification = new PartyIdentification();
        return result;
    }

    public void outputToDebug() {
        Debug.logInfo("partyId: " + party.getpartyId(), NAME);
        Debug.logInfo("partyTypeId: " + party.getpartyTypeId(), NAME);
        Debug.logInfo("party obj: " + party.getpartyId(), NAME);
        if ("PERSON".equals(party.getpartyTypeId())) {
            Debug.logInfo("Person obj: " + person.getfirstName() + "," + person.getlastName(), NAME);
        } else {
            Debug.logInfo("Party Group: " + partyGroup.getgroupName(), NAME);
        }
        partyContactList.outputToDebug();
        //partyContactMechPurposeList.outputToDebug();        
    }

    @Override
    public boolean saveComposite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean loadComposite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateComposite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteComposite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ContactList contactMechListToCreate;

    public ContactList getContactMechListToCreate() {
        return contactMechListToCreate;
    }

    public void setContactMechListToCreate(ContactList contactMechListToCreate) {
        this.contactMechListToCreate = contactMechListToCreate;
    }

    public String getDisplayName() {
        String str = "";
        if ("PERSON".equals(party.getpartyTypeId())) {
            if (person != null) {
                if (person.getGenericValueObj() != null) {
                    str = org.ofbiz.party.party.PartyHelper.formatPartyNameObject(person.getGenericValueObj(), false);
                } else {
                    str = person.getfirstName();
                }
            }
        } else {
            if (partyGroup != null) {
                if (partyGroup.getGenericValueObj() != null) {
                    str = org.ofbiz.party.party.PartyHelper.formatPartyNameObject(partyGroup.getGenericValueObj(), false);
                } else {
                    str = partyGroup.getgroupName();
                }
            }
        }
        return str;
    }

    public String getdisplayName(DisplayNameInterface.DisplayTypes showId) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {

            orderToStringBuilder.append(getDisplayName());

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(party.getpartyId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(party.getpartyId());
        }

        return orderToStringBuilder.toString();
    }
}
