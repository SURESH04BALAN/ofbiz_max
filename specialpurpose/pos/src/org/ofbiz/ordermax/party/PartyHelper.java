/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.PartyRole;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class PartyHelper {

    public static final String module = PartyHelper.class.getName();

    static public void createPartyAdminRoles(String partyId, XuiSession session) throws GenericServiceException {

        final String roleTypeIds[] = {"ADDRESSEE", "APPROVER", "BUYER", "EMAIL_ADMIN",
            "IMAGEAPPROVER", "MANAGER", "ORDER_CLERK", "OWNER", "PACKER", "PROJECT_TEAM",
            "REQ_TAKER", "SALES_REP", "SHIPMENT_CLERK", "_NA_"};
 //       for (String roleTypeId : roleTypeIds) {
        //           GenericValue roleGeneriv = GenericValueDbHelper.createPartyRole(partyId, roleTypeId, delegator);
        //           Debug.logInfo("Saved Party [" + roleGeneriv.getString("partyId") + "," + roleGeneriv.getString("roleTypeId"), module);
        //       }
        for (String roleTypeId : roleTypeIds) {
            createPartyRole(partyId, roleTypeId, session);
        }
    }

    static public void createPartySupplierRoles(String partyId, XuiSession session) throws GenericServiceException {

        final String roleTypeIds[] = {"SUPPLIER", "ACCOUNT", "BILL_FROM_VENDOR", "SHIP_FROM_VENDOR", "SUPPLIER_AGENT"};
        Debug.logInfo("update roles createPartySupplierRoles", module);
        for (String roleTypeId : roleTypeIds) {
            createPartyRole(partyId, roleTypeId, session);
        }

    }

    static public void createPartyRole(String partyId, String roleTypeId, XuiSession session) throws GenericServiceException {
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Debug.logInfo("createPartyRole roles createPartySupplierRoles: " + partyId + " role: " + roleTypeId, module);
        Map<String, Object> inMap = FastMap.newInstance();
        inMap.clear();
        inMap.put("userLogin", userLogin);
        inMap.put("partyId", partyId);
        inMap.put("roleTypeId", roleTypeId);
        resultMap = dispatcher.runSync("createPartyRole", inMap);

        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
        }

    }

    static public void createPartyCustomerRoles(String partyId, XuiSession session) throws GenericServiceException {
        final String roleTypeIds[] = {"CUSTOMER", "ACCOUNT", "BILL_TO_CUSTOMER", "END_USER_CUSTOMER", "PLACING_CUSTOMER", "SHIP_TO_CUSTOMER"};
//        for (String roleTypeId : roleTypeIds) {
//            GenericValue roleGeneriv = GenericValueDbHelper.createPartyRole(partyId, roleTypeId, delegator);
//            Debug.logInfo("Saved Party roles [" + roleGeneriv.getString("partyId") + "," + roleGeneriv.getString("roleTypeId"), module);
//        }
        for (String roleTypeId : roleTypeIds) {
            createPartyRole(partyId, roleTypeId, session);
        }

    }

    static public void createPartyRoles(String roleTypeId, String partyId, XuiSession session) throws GenericServiceException {

        if (roleTypeId != null && roleTypeId.equals("SUPPLIER")) {
            createPartySupplierRoles(partyId, session);
        }
    }

}
