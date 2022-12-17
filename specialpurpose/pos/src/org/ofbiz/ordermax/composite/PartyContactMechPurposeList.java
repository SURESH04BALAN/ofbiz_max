/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author siranjeev
 */
public class PartyContactMechPurposeList extends ListAdapterListModel<PartyContactMechPurposeComposite>{
    public String getBillingLocationContact(){
        String oc = getPartyContactPurpose("BILLING_LOCATION");
        return oc;      
    }

    public String getBillingLocationPhoneContact(){
        String oc = getPartyContactPurpose("PHONE_BILLING");
        return oc;      
    }    

    public String getShippingLocationContact(){
        String oc = getPartyContactPurpose("SHIPPING_LOCATION");
        return oc;      
    }

    public String getShippingLocationPhoneContact(){
        String oc = getPartyContactPurpose("PHONE_SHIPPING");
        return oc;      
    }    
    
        
    public String getPartyContactPurpose(String contactMechPurposeTypeId){
        
        String pc = null;
        for(PartyContactMechPurposeComposite itr : list){
            if( itr.getPartyContactMechPurpose().getcontactMechPurposeTypeId().equals(contactMechPurposeTypeId)){
                pc = itr.getPartyContactMechPurpose().getcontactMechId();
                break;
            }
        }
        
        return pc;
    }

    public PartyContactMechPurposeComposite getPartyContactMechPurpose(String contactMechId){
        
        PartyContactMechPurposeComposite pc = null;
        for(PartyContactMechPurposeComposite itr : list){
            if( itr.getPartyContactMechPurpose().getcontactMechId().equals(contactMechId)){
                pc = itr;
                break;
            }
        }
        
        return pc;
    }
    
    public void outputToDebug(){
        Debug.logInfo("getSize(): " + getSize(), "module"); 
        for( int i=0; i < getSize(); ++i) {
             PartyContactMechPurposeComposite contact =  getElementAt(i);
             contact.outputToDebug();

	}
    }
    
    
}
