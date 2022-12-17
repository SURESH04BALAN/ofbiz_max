/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import mvc.model.list.ListAdapterListModel;

/**
 *
 * @author siranjeev
 */
public class PartyRolesList extends ListAdapterListModel<PartyRoleComposite> {

    public PartyRolesList() {
    }
/**
     * Returns party from OrderRole of BILL_TO_CUSTOMER
     */
    public PartyRoleComposite getBillToParty() {
        return this.getPartyFromRole("BILL_TO_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of BILL_FROM_VENDOR
     */
    public PartyRoleComposite getBillFromParty() {
        return this.getPartyFromRole("BILL_FROM_VENDOR");
    }

    /**
     * Returns party from OrderRole of SHIP_TO_CUSTOMER
     */
    public PartyRoleComposite getShipToParty() {
        return this.getPartyFromRole("SHIP_TO_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of PLACING_CUSTOMER
     */
    public PartyRoleComposite getPlacingParty() {
        return this.getPartyFromRole("PLACING_CUSTOMER");
    }

    public PartyRoleComposite getOwnerParty() {
        return this.getPartyFromRole("OWNER");
    }

    /**
     * Returns party from OrderRole of END_USER_CUSTOMER
     */
    public PartyRoleComposite getEndUserParty() {
        return this.getPartyFromRole("END_USER_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of SUPPLIER_AGENT
     */
    public PartyRoleComposite getSupplierAgent() {
        return this.getPartyFromRole("SUPPLIER_AGENT");
    }
    
    public PartyRoleComposite getPartyFromRole(String partyRoleTypeId){
        PartyRoleComposite partyRoleComposite = null;
        for(PartyRoleComposite orderRoleComposite : list){
            if(orderRoleComposite.getPartyRole().getroleTypeId().equals(partyRoleTypeId)){
                partyRoleComposite = orderRoleComposite;
                break;
            }
        }
        return partyRoleComposite;
    }

    public PartyContactMechComposite getPartyContact(String contachMachId){
        PartyContactMechComposite pc = null;
        for(PartyRoleComposite orderRoleComposite : list){
            pc = orderRoleComposite.getParty().getPartyContact(contachMachId);
            if(pc!=null){
                break;
            }
        }     
        
        return pc;
    }    

}
