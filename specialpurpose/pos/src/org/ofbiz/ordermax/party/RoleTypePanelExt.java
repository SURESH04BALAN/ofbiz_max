/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.panel.RoleTypePanel;
import org.ofbiz.ordermax.screens.ContactMechPanelMain;

/**
 *
 * @author siranjeev
 */
public class RoleTypePanelExt extends RoleTypePanel implements org.ofbiz.ordermax.base.BaseMainPanelInterface {

    public RoleTypePanelExt(RoleType val) {
        super(val);

    }

    public RoleTypePanelExt() {
        super();
    }

    public void refreshScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void newItem() {
        try {
            //TelecomNumber telNumber = new TelecomNumber();
            GenericValueObjectInterface uiObject = this.createUIObject(null);
            changeUIObject(uiObject);
            setUIFields();
        } catch (java.text.ParseException ex) {
           Debug.logError(ex,module);
        }

    }

    public void saveItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadItem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setItem(Object val) {
        GenericValue partyGroup = (GenericValue) val;
        GenericValueObjectInterface uiObj = this.createUIObject(partyGroup);
        this.changeUIObject(uiObj);
        try {
            this.setUIFields();
        } catch (java.text.ParseException ex) {
            Debug.logError(ex, module);
        }
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
