/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.InvoiceContactMech;

/**
 *
 * @author siranjeev
 */
public class InvoiceContact {


    Contact contact = null;
    InvoiceContactMech invoiceContactMech = null;

    public InvoiceContactMech getInvoiceContactMech() {
        return invoiceContactMech;
    }

    public void setInvoiceContactMech(InvoiceContactMech invoiceContactMech) {
        this.invoiceContactMech = invoiceContactMech;
    }
    
    public InvoiceContact(){        
    }
    
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
