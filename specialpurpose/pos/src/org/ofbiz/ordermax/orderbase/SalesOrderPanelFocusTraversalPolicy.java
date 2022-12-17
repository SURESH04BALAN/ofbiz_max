/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author siranjeev
 */
public  class SalesOrderPanelFocusTraversalPolicy extends FocusTraversalPolicy {

    Vector<Component> order;

    public SalesOrderPanelFocusTraversalPolicy(Vector<Component> order) {
        this.order = new Vector<Component>(order.size());
        this.order.addAll(order);
    }

    public Component getComponentAfter(Container focusCycleRoot,
            Component aComponent) {
        int idx = (order.indexOf(aComponent) + 1) % order.size();
        Debug.logInfo("getComponentAfter", "module");        
        return order.get(idx);
    }

    public Component getComponentBefore(Container focusCycleRoot,
            Component aComponent) {
        int idx = order.indexOf(aComponent) - 1;
        if (idx < 0) {
            idx = order.size() - 1;
        }
        Debug.logInfo("getComponentBefore", "module");
        return order.get(idx);
    }

    public Component getDefaultComponent(Container focusCycleRoot) {
        Debug.logInfo("getDefaultComponent", "module");                
        return order.get(0);
    }

    public Component getLastComponent(Container focusCycleRoot) {
        return order.lastElement();
    }

    public Component getFirstComponent(Container focusCycleRoot) {
        return order.get(0);
    }
}
