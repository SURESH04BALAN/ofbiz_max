/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

import bsh.ParseException;
import javax.swing.JPanel;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public interface GenericValuePanelInterface {

    void changeUIObject(org.ofbiz.ordermax.generic.GenericValueObjectInterface uiObject);
    org.ofbiz.ordermax.generic.GenericValueObjectInterface createUIObject(GenericValue baseVal);
    void getUIFields() throws ParseException, java.text.ParseException;
    void setUIFields() throws ParseException, java.text.ParseException;    
    JPanel getContainerPanel();    
    public org.ofbiz.ordermax.generic.GenericValueObjectInterface getUIObject();
    public boolean isModified();
    public void setIsModified(boolean isModified);
    
}
