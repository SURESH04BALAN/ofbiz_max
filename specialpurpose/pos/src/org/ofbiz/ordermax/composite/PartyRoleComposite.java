/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.PartyRole;

/**
 *
 * @author siranjeev
 */
public class PartyRoleComposite {

    
    private PartyRole partyRole;
    private Account party;
    
    public PartyRoleComposite() {
    }

    public PartyRole getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(PartyRole partyRole) {
        this.partyRole = partyRole;
    }

    public Account getParty() {
        return party;
    }

    public void setParty(Account party) {
        this.party = party;
    }        
}
