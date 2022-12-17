/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;

/**
 *
 * @author BBS Auctions
 */
public class LoadGenericValues<T> implements LoadGenericValueInterface<T> {

    @Override
    public List<T> loadValues(ControllerOptions options) {
        List<T> values = new ArrayList<T>();
        Map<String, Object> keys = null;
        if (options != null && options.contains("keys")) {
            keys = (Map<String, Object>) options.get("keys");
        }
        String entityName = null;
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();

            List<GenericValue> genList = null;
            if (keys != null) {
                genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, keys, null, delegator);
            } else {
                genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, UtilMisc.toMap(), null, delegator);
            }

            for (GenericValue genVal : genList) {
                T val = getEntityClass(entityName, genVal);
                if (val != null) {
                    values.add(val);
                }
            }
        }
        //Debug.logInfo(" entityName list: " + list.size(), SimpleShoppingListPanel.class.getName());
        return values;
    }

    protected T getEntityClass(String entityName, GenericValue genVal) {
        try {
            Debug.logInfo(" getEntityClass entityName : " + entityName, "module");
            String className = "org.ofbiz.ordermax.entity." + entityName;
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(GenericValue.class);
            Object instance = constructor.newInstance(genVal);
            Debug.logInfo(" getEntityClass entityName : " + "Sucess", "module");
            return (T) instance;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadGenericValues.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(LoadGenericValues.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LoadGenericValues.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(LoadGenericValues.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LoadGenericValues.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(LoadGenericValues.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(LoadGenericValues.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
