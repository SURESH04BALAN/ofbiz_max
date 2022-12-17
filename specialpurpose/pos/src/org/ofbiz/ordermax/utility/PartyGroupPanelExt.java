/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import org.ofbiz.ordermax.entity.PartyGroup;

/**
 *
 * @author siranjeev
 */
public class PartyGroupPanelExt extends PartyGroupPanel {
    public static final String module = PartyGroupPanel.class.getName();
    public String getPartyId(){ return this.partyIdTextField.getText(); }
    
    public PartyGroupPanelExt(PartyGroup val){
        super(val);
    }
    
    public PartyGroupPanelExt(){
        super();
    }
}
