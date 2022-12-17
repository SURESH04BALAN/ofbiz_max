/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;


import javax.swing.JPanel;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public interface GenericValuePanelInterfaceOrderMax {

    void changeUIObject(GenericValueObjectInterface uiObject);
    GenericValueObjectInterface createUIObject(GenericValue baseVal);
    void getUIFields() throws  java.text.ParseException;
    void setUIFields() throws  java.text.ParseException;    
    JPanel getContainerPanel();    
    public GenericValueObjectInterface getUIObject();
    public boolean isModified();
    public void setIsModified(boolean isModified);
    
}
