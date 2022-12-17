/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.OrderRole;

/**
 *
 * @author siranjeev
 */
public class OrderRoleComposite {

    
    private OrderRole orderRole;
    private Account party;
    
    public OrderRoleComposite() {
    }

    public OrderRole getOrderRole() {
        return orderRole;
    }

    public void setOrderRole(OrderRole orderRole) {
        this.orderRole = orderRole;
    }

    public Account getParty() {
        return party;
    }

    public void setParty(Account party) {
        this.party = party;
    }
    
    
}
