/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;

/**
 *
 * @author siranjeev
 */
public class PartyIdVerifyValidator extends BaseInputVerifier {

    private GenericValue party = null;
    boolean validate = true;
    
    public PartyIdVerifyValidator(JTextField field, XuiSession session) {
        this.field = field;
        this.session = session;
    }
    
    public PartyIdVerifyValidator(JTextField field, boolean validate,  XuiSession session) {
        this.field = field;
        this.session = session;
        this.validate = validate;
    }
    @Override
    public boolean shouldYieldFocus(JComponent input) {
          //if empty null then return
        if(field.getText() == null  ||  field.getText().isEmpty()){        
            return false;
        }
        
        if(validate==false){
            ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
            for (ActionListener listener : listeners) {
                listener.actionPerformed(event); // broadcast to all
            }            
            return true;
        }
        
        if (verify(input)) {
            //can Change??
            shouldYieldFocus = true;
            ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
            for (ActionListener listener : listeners) {
                listener.actionPerformed(event); // broadcast to all
            }
            return shouldYieldFocus;
        } else {
            field.selectAll();
            return false;
        }
    }

    @Override
    public boolean verify(JComponent input) {
        try {
            party = PosProductHelper.getParty(field.getText(), session.getDelegator());
            if (party != null) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
