/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.base.util.Debug;
import static org.ofbiz.ordermax.composite.Contact.module;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;
import org.ofbiz.ordermax.entity.PartyContactMechPurpose;

/**
 *
 * @author siranjeev
 */
public class PartyContactMechPurposeComposite {
    PartyContactMechPurpose partyContactMechPurpose;
    private ContactMechPurposeType contactMechPurposeType;    

    public PartyContactMechPurposeComposite() {
    }

    public PartyContactMechPurpose getPartyContactMechPurpose() {
        return partyContactMechPurpose;
    }

    public void setPartyContactMechPurpose(PartyContactMechPurpose partyContactMechPurpose) {
        this.partyContactMechPurpose = partyContactMechPurpose;
    }


    public ContactMechPurposeType getContactMechPurposeType() {
        return contactMechPurposeType;
    }

    public void setContactMechPurposeType(ContactMechPurposeType contactMechPurposeType) {
        this.contactMechPurposeType = contactMechPurposeType;
    }
        public void outputToDebug(){
        Debug.logInfo("contactMechPurposeType Id: " + partyContactMechPurpose.getcontactMechPurposeTypeId(), module); 
//        partyContact.outputToDebug();
    }    
    
}
