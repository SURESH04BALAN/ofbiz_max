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
public class InvoiceRolesList extends ListAdapterListModel<InvoiceRoleComposite> {

    public InvoiceRolesList() {
    }
/**
     * Returns party from OrderRole of BILL_TO_CUSTOMER
     */
    public InvoiceRoleComposite getBillToParty() {
        return this.getPartyFromRole("BILL_TO_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of BILL_FROM_VENDOR
     */
    public InvoiceRoleComposite getBillFromParty() {
        return this.getPartyFromRole("BILL_FROM_VENDOR");
    }

    /**
     * Returns party from OrderRole of SHIP_TO_CUSTOMER
     */
    public InvoiceRoleComposite getShipToParty() {
        return this.getPartyFromRole("SHIP_TO_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of PLACING_CUSTOMER
     */
    public InvoiceRoleComposite getPlacingParty() {
        return this.getPartyFromRole("PLACING_CUSTOMER");
    }

    public InvoiceRoleComposite getOwnerParty() {
        return this.getPartyFromRole("OWNER");
    }

    /**
     * Returns party from OrderRole of END_USER_CUSTOMER
     */
    public InvoiceRoleComposite getEndUserParty() {
        return this.getPartyFromRole("END_USER_CUSTOMER");
    }

    /**
     * Returns party from OrderRole of SUPPLIER_AGENT
     */
    public InvoiceRoleComposite getSupplierAgent() {
        return this.getPartyFromRole("SUPPLIER_AGENT");
    }
    
    public InvoiceRoleComposite getPartyFromRole(String partyRoleTypeId){

        for(InvoiceRoleComposite invoiceRoleComposite : list){
            invoiceRoleComposite.outputDebug();
            Debug.logInfo("partyRoleTypeId: " + partyRoleTypeId, "value");
            if(invoiceRoleComposite.getInvoiceRole().getroleTypeId().equals(partyRoleTypeId)){
                return invoiceRoleComposite;
            }
        }
        return null;
    }

    public PartyContactMechComposite getPartyContact(String contachMachId){
        PartyContactMechComposite pc = null;
        for(InvoiceRoleComposite invoiceRoleComposite : list){
            pc = invoiceRoleComposite.getParty().getPartyContact(contachMachId);
            if(pc!=null){
                return pc;
            }
        }     
        
        return null;
    }    

}
