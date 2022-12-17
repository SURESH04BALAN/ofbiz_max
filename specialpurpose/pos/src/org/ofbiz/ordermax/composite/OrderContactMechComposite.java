/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import org.ofbiz.base.util.Debug;
import static org.ofbiz.ordermax.composite.Contact.module;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;
import org.ofbiz.ordermax.entity.OrderContactMech;
import org.ofbiz.ordermax.entity.PartyContactMech;

/**
 *
 * @author siranjeev
 */
public class OrderContactMechComposite {

    private OrderContactMech orderContactMech;
    private Contact contact;
    ContactMechPurposeType contactMechPurposeType = null;

    public OrderContactMechComposite() {

    }

    public OrderContactMech getOrderContactMech() {
        return orderContactMech;
    }

    public void setOrderContactMech(OrderContactMech orderContactMech) {
        this.orderContactMech = orderContactMech;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public ContactMechPurposeType getPartyContactMechPurposeType() {
        return contactMechPurposeType;
    }

    public void setPartyContactMechPurposeType(ContactMechPurposeType partyContactMechPurposeType) {
        this.contactMechPurposeType = partyContactMechPurposeType;
    }

    public boolean isPartyContactMechPurposeExists(String purposeId) {

        if (contactMechPurposeType.getcontactMechPurposeTypeId().equals(purposeId)) {
            return true;
        }

        return false;
    }

    public void setContachMechId(String contachMechId) {
        orderContactMech.setcontactMechId(contachMechId);
        contact.setContachMechId(contachMechId);
 
    }

    public void outputToDebug() {
        Debug.logInfo("orderContactMech Id: " + orderContactMech.getcontactMechId(), module);
        Debug.logInfo("getContactMechTypeId Id: " + contact.getContactMechTypeId(), module);
        Debug.logInfo("contactMechPurposeType: " + contactMechPurposeType.getdescription(), module);
        contact.outputToDebug();
    }
}
