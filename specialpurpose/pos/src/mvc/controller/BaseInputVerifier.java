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
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;

/**
 *
 * @author siranjeev
 */
public abstract class BaseInputVerifier extends InputVerifier {
    protected JTextField field;
    protected XuiSession session = null;
    protected boolean shouldYieldFocus = true;
    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public BaseInputVerifier() {
    }

    public JTextField getField() {
        return field;
    }

    public boolean isShouldYieldFocus() {
        return shouldYieldFocus;
    }

    public void setShouldYieldFocus(boolean shouldYieldFocus) {
        this.shouldYieldFocus = shouldYieldFocus;
    }

    @Override
    abstract public boolean shouldYieldFocus(JComponent input);        
    @Override
    abstract public boolean verify(JComponent input);
    
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
    
}
