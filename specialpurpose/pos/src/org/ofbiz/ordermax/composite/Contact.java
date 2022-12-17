/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.entity.ContactMech;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class Contact {

    public static final String module = Contact.class.getName();
    final static public String DOMAIN_NAME = "DOMAIN_NAME";
    final static public String ELECTRONIC_ADDRESS = "ELECTRONIC_ADDRESS";
    final static public String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    final static public String INTERNAL_PARTYID = "INTERNAL_PARTYID";
    final static public String IP_ADDRESS = "IP_ADDRESS";
    final static public String LDAP_ADDRESS = "LDAP_ADDRESS";
    final static public String POSTAL_ADDRESS = "POSTAL_ADDRESS";
    final static public String TELECOM_NUMBER = "TELECOM_NUMBER";
    final static public String WEB_ADDRESS = "WEB_ADDRESS";

    private ContactMech contactMech = null;
    private PostalAddress postalAddress = null;
    private TelecomNumber telecomNumber = null;
    private String contactMechTypeId;

    public Contact() {

    }

    public Contact(String mechTypeId) {
        contactMechTypeId = mechTypeId;
    }

    public ContactMech getContactMech() {
        return contactMech;
    }

    public void setContactMech(ContactMech contactMech) {
        this.contactMech = contactMech;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public TelecomNumber getTelecomNumber() {
        return telecomNumber;
    }

    public void setTelecomNumber(TelecomNumber telecomNumber) {
        this.telecomNumber = telecomNumber;
    }

    public String getContactMechTypeId() {
        return contactMechTypeId;
    }

    public void setContactMechTypeId(String contactMechTypeId) {
        this.contactMechTypeId = contactMechTypeId;
    }

    public void setContachMechId(String contachMechId) {
        contactMech.setcontactMechId(contachMechId);
        if (postalAddress != null) {
            postalAddress.setContactMechId(contachMechId);
        }
        if (telecomNumber != null) {
            telecomNumber.setcontactMechId(contachMechId);
        }
    }

    public boolean isPostalAddress(){
        return POSTAL_ADDRESS.equalsIgnoreCase(contactMechTypeId);
    }

    public boolean isTelecomNumberAddress(){
        return POSTAL_ADDRESS.equalsIgnoreCase(contactMechTypeId);
    }
    
    static public boolean isPostalAddress(String contactMechTypeId){
        return POSTAL_ADDRESS.equalsIgnoreCase(contactMechTypeId);
    }

    static public boolean isTelecomNumberAddress(String contactMechTypeId){
        return POSTAL_ADDRESS.equalsIgnoreCase(contactMechTypeId);
    }
        
    public void outputToDebug() {
        Debug.logInfo("contactMechTypeId: " + contactMechTypeId, module);
        Debug.logInfo("contactMech Id: " + contactMech.getcontactMechId(), module);

        if ("POSTAL_ADDRESS".equalsIgnoreCase(contactMechTypeId)) {
            Debug.logInfo("postalAddress address1: " + OrderMaxUtility.getFormatedAddress(postalAddress.getGenericValueObj()), module);
        } else if ("TELECOM_NUMBER".equalsIgnoreCase(contactMechTypeId)) {
            Debug.logInfo("telecomNumber: " + OrderMaxUtility.getFormatedTelecom(telecomNumber.getGenericValueObj()), module);
        } else {
            Debug.logInfo("info Sgtring: " + contactMech.getinfoString(), module);
        }

    }
}
