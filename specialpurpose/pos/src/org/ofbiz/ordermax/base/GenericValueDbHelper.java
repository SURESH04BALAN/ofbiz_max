/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public class GenericValueDbHelper {
    
        static public GenericValue createPartyRole(String partyId, String roleTypeId, Delegator delegator) throws GenericEntityException {
        GenericValue partyRole = null;
//        try {
        partyRole = delegator.findByPrimaryKey("PartyRole", UtilMisc.toMap("partyId", partyId, "roleTypeId", roleTypeId));
        if (partyRole == null) {
            partyRole = delegator.makeValue("PartyRole");
            partyRole.set("partyId", partyId);
            partyRole.set("roleTypeId", roleTypeId);
            partyRole.create();
        }
//        } catch (GenericEntityException e) {
        // TODO Auto-generated catch block
//            Debug.logError(e, module);
//        }
        return partyRole;
    }

}
