/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;

import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class ConditionSelectSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, String> valueMap = null;
    public final static String CONTAINS = "contains";
    public final static String EQUALS = "equals";
    public final static String GREATER_THAN = "greater_than";
    public final static String GREATER_THAN_EQUALS = "greater_than_equals";
    public final static String LESS_THAN = "less_than";
    public final static String LESS_THAN_EQUALS = "less_than_equals";
    
    public final static String CONTAINS_STR = "Contains";
    public final static String EQUALS_STR = "Equals";
    public final static String GREATER_THAN_STR = "Greater Than";
    public final static String GREATER_THAN_EQUALS_STR = "Greater Than Equals";
    public final static String LESS_THAN_STR = "Less Than";
    public final static String LESS_THAN_EQUALS_STR = "Less Than Equals";

    private ConditionSelectSingleton() {
        valueMap = new HashMap<String, String>();
    }

    private static class SingletonHolder {

        public static final ConditionSelectSingleton INSTANCE = new ConditionSelectSingleton();
    }

    public static ConditionSelectSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<String> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<String>(getInstance().valueMap.values());
    }

    final static public ListModel<String> getValueListModal() {
        ListAdapterListModel<String> modal = new ListAdapterListModel<String>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<String> getValueListModal(String typeId) {

        ListModel<String> modalRet = null;
        if (typeId != null) {
            if (getInstance().valueMap.isEmpty()) {
                loadAll();
            }

            ListAdapterListModel<String> modal = new ListAdapterListModel<String>();
            for (Map.Entry<String, String> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().equals(typeId)) {
                    modal.add(entry.getValue());
                }
            }
            modalRet = modal;
        } else {
            modalRet = getValueListModal();
        }

        return modalRet;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Locale locale = (Locale) Locale.getDefault();
            String opContains = CONTAINS_STR;//UtilProperties.getMessage("conditional", CONTAINS, locale);
            String opEquals = EQUALS_STR;//UtilProperties.getMessage("conditional", EQUALS, locale);
            String opGreaterThan = GREATER_THAN_STR;//UtilProperties.getMessage("conditional", GREATER_THAN, locale);
            String opGreaterThanEquals = GREATER_THAN_EQUALS_STR;//UtilProperties.getMessage("conditional", GREATER_THAN_EQUALS, locale);
            String opLessThan = LESS_THAN_STR;//UtilProperties.getMessage("conditional", LESS_THAN, locale);
            String opLessThanEquals = LESS_THAN_EQUALS_STR;//UtilProperties.getMessage("conditional", LESS_THAN_EQUALS, locale);

            getInstance().valueMap.put(CONTAINS, opContains);
            getInstance().valueMap.put(EQUALS, opEquals);
            getInstance().valueMap.put(GREATER_THAN, opGreaterThan);
            getInstance().valueMap.put(GREATER_THAN_EQUALS, opGreaterThanEquals);
            getInstance().valueMap.put(LESS_THAN, opLessThan);
            getInstance().valueMap.put(LESS_THAN_EQUALS, opLessThanEquals);

        } catch (Exception ex) {
            Logger.getLogger(ConditionSelectSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static EntityComparisonOperator getOperator(final String opString) throws Exception {

        if (CONTAINS.equals(opString)) {
            return EntityOperator.LIKE;
        } else if (EQUALS.equals(opString)) {
            return EntityOperator.EQUALS;
        } else if (GREATER_THAN.equals(opString)) {
            return EntityOperator.GREATER_THAN;
        } else if (GREATER_THAN_EQUALS.equals(opString)) {
            return EntityOperator.GREATER_THAN_EQUAL_TO;
        } else if (LESS_THAN.equals(opString)) {
            return EntityOperator.LESS_THAN;
        } else if (LESS_THAN_EQUALS.equals(opString)) {
            return EntityOperator.LESS_THAN_EQUAL_TO;
        } else {
            throw new Exception("Operator not found: " + opString);
        }

    }

    public static String getString(String statusId) throws Exception {
        String string = null;
        if (getInstance().valueMap.containsKey(statusId)) {
            string = getInstance().valueMap.get(statusId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(statusId)) {
                string = getInstance().valueMap.get(statusId);
            } else {
                throw new Exception("unable to load uom : " + statusId);
            }
        }

        return string;
    }

    public static String getKeyString(String value) throws Exception {
        String string = null;
        for (Map.Entry<String, String> entry : getInstance().valueMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }

        return string;
    }


}
