/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;


/**
 *
 * @author siranjeev
 */
public class PartyHelper {
    public static final String module = PartyHelper.class.getName();
    
    static public GenericValue getPerson(String partyId, Delegator delegator) {
        GenericValue partyVal = null;
        try {
            partyVal = delegator.findByPrimaryKey("Person", UtilMisc.toMap("partyId", partyId));
        } catch (GenericEntityException ex) {
            Logger.getLogger(PartyHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return partyVal;
    }
    // check if product already exists in database

    public static GenericValue getPartyGroup(String partyId,
            Delegator delegator) {

        GenericValue tmpPartyGroup = null;

        try {
            tmpPartyGroup = delegator.findByPrimaryKey("PartyGroup", UtilMisc
                    .toMap("partyId", partyId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product group", module);
        }

        return tmpPartyGroup;
    }

    // check if party already exists in database
    public static GenericValue getParty(String partyId, Delegator delegator) {

        GenericValue tmpPartyGroup = null;

        try {
            tmpPartyGroup = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data from party", module);
        }

        return tmpPartyGroup;
    }
    
}
