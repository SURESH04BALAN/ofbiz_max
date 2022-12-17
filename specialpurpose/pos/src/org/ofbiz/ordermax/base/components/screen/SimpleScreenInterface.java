/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.base.components.screen;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author BBS Auctions
 */
public interface SimpleScreenInterface {
    public JButton getOkButton();            
    public JButton getCancelButton();   
    public JPanel getPanel();
}
