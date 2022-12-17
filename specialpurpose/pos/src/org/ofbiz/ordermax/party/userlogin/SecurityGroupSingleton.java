/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party.userlogin;

import org.ofbiz.ordermax.inventory.*;
import org.ofbiz.ordermax.product.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.SecurityGroup;
import static org.ofbiz.ordermax.product.ProductSingleton.getInstance;

/**
 *
 * @author siranjeev
 */
public class SecurityGroupSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, SecurityGroup> valueMap = null;

    private SecurityGroupSingleton() {
        valueMap = new HashMap<String, SecurityGroup>();
    }

    private static class SingletonHolder {

        public static final SecurityGroupSingleton INSTANCE = new SecurityGroupSingleton();
    }

    public static SecurityGroupSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<SecurityGroup> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<SecurityGroup>(getInstance().valueMap.values());
    }

    final static public ListModel<SecurityGroup> getValueListModal() {
        ListAdapterListModel<SecurityGroup> modal = new ListAdapterListModel<SecurityGroup>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("SecurityGroup", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                SecurityGroup  securityGroup = new SecurityGroup(val);
                if(securityGroup.getdescription()==null){
                    securityGroup.setdescription("null");
                }
                getInstance().valueMap.put(securityGroup.getgroupId(), securityGroup);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductPriceTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static SecurityGroup getSecurityGroup(String groupId) throws Exception {
        SecurityGroup geoList = null;
        if (getInstance().valueMap.containsKey(groupId)) {
            geoList = getInstance().valueMap.get(groupId);
        } else {
            loadAll();
            getInstance().valueMap = sortByComparator(getInstance().valueMap);                        
            if (getInstance().valueMap.containsKey(groupId)) {
                geoList = getInstance().valueMap.get(groupId);
            } else {
                throw new Exception("unable to load geo : " + groupId);
            }
        }

        return geoList;
    }
    
    private static final Comparator<Map.Entry<String, SecurityGroup>> valueComparator = new Comparator<Map.Entry<String, SecurityGroup>>() {
        @Override
        public int compare(Map.Entry<String, SecurityGroup> e1,
                Map.Entry<String, SecurityGroup> e2) {
		//comparing by values, if it's equals, compare by keys
            // in other case, entries with equals values will be removed
            if (e1.getValue().getdescription().equals(e2.getValue().getdescription())) {
                return e1.getKey().compareTo(e2.getKey());
            }

            return (e1.getValue().getdescription()).compareTo(e2.getValue().getdescription());
        }
    };

    private static Map<String, SecurityGroup> sortByComparator(
            Map<String, SecurityGroup> unsortMap) {

        // sorted set based on comparator
        SortedSet<Map.Entry<String, SecurityGroup>> set = new TreeSet<Map.Entry<String, SecurityGroup>>(valueComparator);
        set.addAll(unsortMap.entrySet());

        // LinkedHashMap make sure order in which keys were inserted
        Map<String, SecurityGroup> sortedMap = new LinkedHashMap<String, SecurityGroup>();
        for (Iterator<Map.Entry<String, SecurityGroup>> it = set.iterator(); it
                .hasNext();) {
            Map.Entry<String, SecurityGroup> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        SecurityGroupSingleton.singletonSession = singletonSession;
    }
}
