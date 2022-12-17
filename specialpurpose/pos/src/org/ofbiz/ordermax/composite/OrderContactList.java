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
public class OrderContactList extends ListAdapterListModel<OrderContact>{
    
    public OrderContactList(){
        
    }
    
    public OrderContact getBillingLocationContact(){
        OrderContact oc = getContactByContactMechPurposeTypeId("BILLING_LOCATION");
        return oc;      
    }

    public OrderContact getBillingLocationPhoneContact(){
        OrderContact oc = getContactByContactMechPurposeTypeId("PHONE_BILLING");
        return oc;      
    }    

    public OrderContact getShippingLocationContact(){
        OrderContact oc = getContactByContactMechPurposeTypeId("SHIPPING_LOCATION");
        return oc;      
    }

    public OrderContact getShippingLocationPhoneContact(){
        OrderContact oc = getContactByContactMechPurposeTypeId("PHONE_SHIPPING");
        return oc;      
    }    
    
    public OrderContact getContactByContactMechPurposeTypeId(String purposeTypeId){
        OrderContact oc = null;
        for(OrderContact itr : list){
            if(itr.getOrderContactMech().getcontactMechPurposeTypeId().equals(purposeTypeId)){
                oc = itr;
                break;
            }
        }
        return oc;     
    }
    
    public void outputToDebug(){
        Debug.logInfo("OrderContact List: " + getSize(), "module"); 
        for( int i=0; i < getSize(); ++i) {
             OrderContact contact =  getElementAt(i);
             contact.getContact().outputToDebug();
             

	}
    }
        
}