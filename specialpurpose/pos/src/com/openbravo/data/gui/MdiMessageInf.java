/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.data.gui;

import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import java.awt.Component;

/**
 *
 * @author PospointAus
 */
public class MdiMessageInf extends MessageInf {

    public MdiMessageInf(int iSignalWord, String sHazard, Object e) {
        super(iSignalWord, sHazard, e);
    }
    
    public void show(Component parent) {        
        JMessageDialog.showMessage(parent, this);
    }    
}
