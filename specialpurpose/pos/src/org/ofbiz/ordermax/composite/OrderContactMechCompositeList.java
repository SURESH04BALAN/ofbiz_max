/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import java.util.ArrayList;
import java.util.List;
import static javax.swing.Action.NAME;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author siranjeev
 */
public class OrderContactMechCompositeList extends ListAdapterListModel<OrderContactMechComposite>{
    
    public OrderContactMechCompositeList(){
        
    }

    
    public OrderContactMechComposite getBillingLocationContact(){
        OrderContactMechComposite oc = getOrderContact("BILLING_LOCATION");
        return oc;      
    }

    public OrderContactMechComposite getBillingLocationPhoneContact(){
        OrderContactMechComposite oc = getOrderContact("PHONE_BILLING");
        return oc;      
    }    

    public OrderContactMechComposite getShippingLocationContact(){
        OrderContactMechComposite oc = getOrderContact("SHIPPING_LOCATION");
        return oc;      
    }

    public OrderContactMechComposite getShippingLocationPhoneContact(){
        OrderContactMechComposite oc = getOrderContact("PHONE_SHIPPING");
        return oc;      
    }    
    
    public OrderContactMechComposite getOrderContactMech(String contachMechId) {

        for (OrderContactMechComposite itr : list) {
            if (itr.getContact().getContactMech().getcontactMechId().equals(contachMechId)) {
                return itr;
            }
        }

        return null;
    }        
    public OrderContactMechComposite getOrderContact(String contachMachPurposeId){
        
        OrderContactMechComposite pc = null;
        for(OrderContactMechComposite itr : list){
            Debug.logInfo("ocm.getcontactMechId() " + itr.contactMechPurposeType.getcontactMechPurposeTypeId(), "module");
            if( itr.contactMechPurposeType.getcontactMechPurposeTypeId().equals(contachMachPurposeId)){
                pc = itr;
                break;
            }
        }
        
        return pc;
    }

    public List<OrderContactMechComposite> getPartyContactMechTypeList(String contachMechTypeId){
        
        ArrayList<OrderContactMechComposite> pcList = new ArrayList<OrderContactMechComposite>();
        
        for(OrderContactMechComposite itr : list){
            if( itr.getContact().getContactMech().getcontactMechTypeId().equals(contachMechTypeId)){
               pcList.add(itr);
            }
        }
        
        return pcList;
    }
    
    public void outputToDebug(){
        Debug.logInfo("getSize(): " + getSize(), NAME); 
        for( int i=0; i < getSize(); ++i) {
             OrderContactMechComposite contact =  getElementAt(i);
             contact.outputToDebug();

	}
    }
}