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
public class OrderRolesList extends ListAdapterListModel<OrderRoleComposite> {

    public OrderRolesList() {
    }
/**
     * Returns party from OrderRole of BILL_TO_CUSTOMER
     */
    public OrderRoleComposite getBillToParty() {
        return this.getPartyFromRole("BILL_TO_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of BILL_FROM_VENDOR
     */
    public OrderRoleComposite getBillFromParty() {
        return this.getPartyFromRole("BILL_FROM_VENDOR");
    }

    /**
     * Returns party from OrderRole of SHIP_TO_CUSTOMER
     */
    public OrderRoleComposite getShipToParty() {
        return this.getPartyFromRole("SHIP_TO_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of PLACING_CUSTOMER
     */
    public OrderRoleComposite getPlacingParty() {
        return this.getPartyFromRole("PLACING_CUSTOMER");
    }

    public OrderRoleComposite getOwnerParty() {
        return this.getPartyFromRole("OWNER");
    }

    /**
     * Returns party from OrderRole of END_USER_CUSTOMER
     */
    public OrderRoleComposite getEndUserParty() {
        return this.getPartyFromRole("END_USER_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of SUPPLIER_AGENT
     */
    public OrderRoleComposite getSupplierAgent() {
        return this.getPartyFromRole("SUPPLIER_AGENT");
    }
    
    public OrderRoleComposite getPartyFromRole(String partyRoleTypeId){
        OrderRoleComposite partyRoleComposite = null;
        for(OrderRoleComposite orderRoleComposite : list){
            if(orderRoleComposite.getOrderRole().getroleTypeId().equals(partyRoleTypeId)){
                partyRoleComposite = orderRoleComposite;
                break;
            }
        }
        return partyRoleComposite;
    }

    public PartyContactMechComposite getPartyContact(String contachMachId){
        PartyContactMechComposite pc = null;
        for(OrderRoleComposite orderRoleComposite : list){
            pc = orderRoleComposite.getParty().getPartyContact(contachMachId);
            if(pc!=null){
                break;
            }
        }     
        
        return pc;
    }    

}
