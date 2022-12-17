/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public class InvoiceContactList extends ListAdapterListModel<InvoiceContact>{
    
    public InvoiceContactList(){
        
    }
    
    public InvoiceContact getBillingLocationContact(){
        InvoiceContact oc = getContactByContactMechPurposeTypeId("BILLING_LOCATION");
        return oc;      
    }

    public InvoiceContact getBillingLocationPhoneContact(){
        InvoiceContact oc = getContactByContactMechPurposeTypeId("PHONE_BILLING");
        return oc;      
    }    

    public InvoiceContact getShippingLocationContact(){
        InvoiceContact oc = getContactByContactMechPurposeTypeId("SHIPPING_LOCATION");
        return oc;      
    }

    public InvoiceContact getShippingLocationPhoneContact(){
        InvoiceContact oc = getContactByContactMechPurposeTypeId("PHONE_SHIPPING");
        return oc;      
    }    
    
    public InvoiceContact getContactByContactMechPurposeTypeId(String purposeTypeId){
        InvoiceContact oc = null;
        for(InvoiceContact itr : list){
            if(itr.getInvoiceContactMech().getcontactMechPurposeTypeId().equals(purposeTypeId)){
                oc = itr;
                break;
            }
        }
        return oc;     
    }
    
    public void outputToDebug(){
        Debug.logInfo("InvoiceContact List: " + getSize(), "module"); 
        for( int i=0; i < getSize(); ++i) {
             InvoiceContact contact =  getElementAt(i);
             contact.getContact().outputToDebug();
             

	}
    }
        
}