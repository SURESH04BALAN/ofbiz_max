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
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;

/**
 *
 * @author siranjeev
 */
public class ProductIdVerifyValidator extends BaseInputVerifier {

    public static final String module = ProductIdVerifyValidator.class.getName();
    private GenericValue party = null;

    public ProductIdVerifyValidator(JTextField field, XuiSession session) {
        this.field = field;
        this.session = session;
        Debug.logInfo("this.field: " + this.field, module);
    }

    protected boolean yieldFocusWhenEmpty = false;

    public ProductIdVerifyValidator(JTextField field, boolean yieldFocusWhenEmpty, XuiSession session) {
        this.field = field;
        this.session = session;
        this.yieldFocusWhenEmpty = yieldFocusWhenEmpty;
        Debug.logInfo("this.field: " + this.field, module);
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        //if empty null then return
        if (UtilValidate.isEmpty(field.getText())) {
            return yieldFocusWhenEmpty;
        }

        if (verify(input)) {
            //can Change??
            Debug.logInfo("field.getText(): " + field.getText(), module);
            shouldYieldFocus = true;
            ActionEvent event = new ActionEvent(this, 1, "productId", new Date().getTime(), 2);
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
        party = PosProductHelper.getProduct(field.getText(), session.getDelegator());
        if (party != null) {
            return true;
        } else {
            return false;
        }
    }

}
