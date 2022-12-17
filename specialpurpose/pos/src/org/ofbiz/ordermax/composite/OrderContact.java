/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.OrderContactMech;

/**
 *
 * @author siranjeev
 */
public class OrderContact {


    Contact contact = null;
    OrderContactMech orderContactMech = null;
    
    public OrderContact(){        
    }
    
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public OrderContactMech getOrderContactMech() {
        return orderContactMech;
    }

    public void setOrderContactMech(OrderContactMech orderContactMech) {
        this.orderContactMech = orderContactMech;
    }    
}
