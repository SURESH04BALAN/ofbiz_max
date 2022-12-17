/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.entity.InvoiceRole;

/**
 *
 * @author siranjeev
 */
public class InvoiceRoleComposite {

    
    private InvoiceRole invoiceRole;
    private Account party;
    
    public InvoiceRoleComposite() {
    }

    public InvoiceRole getInvoiceRole() {
        return invoiceRole;
    }

    public void setInvoiceRole(InvoiceRole invoiceRole) {
        this.invoiceRole = invoiceRole;
    }

    public Account getParty() {
        return party;
    }

    public void setParty(Account party) {
        this.party = party;
    }
    
    public void outputDebug(){
        Debug.logInfo("invoiceId: " + invoiceRole.getinvoiceId() +
               ", invoicParty: " + invoiceRole.getpartyId() + 
                               ", role type id: " + invoiceRole.getroleTypeId()
        , " test");
    }
}
