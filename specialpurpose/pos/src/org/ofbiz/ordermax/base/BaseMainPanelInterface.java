/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.beans.PropertyChangeListener;

/**
 *
 * @author siranjeev
 */
public interface BaseMainPanelInterface {
    
    public void refreshScreen();    
    public void addItem(String id) throws Exception;
    public void newItem();
    public void saveItem() throws Exception;
    public void loadItem();
    public void setItem(Object val);    
    public boolean isModified();
    public void setIsModified(boolean isModified);
    public void setParentItem(Object val);    
    public void addChangeListener(PropertyChangeListener newListener);
}
