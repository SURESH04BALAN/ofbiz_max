/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.Order;

/**
 *
 * @author BBS Auctions
 */
public abstract class OrderPopupButtonMenu extends PopupButtonMenu {

    public Order popupOrder;
    OrderActionInterface orderActInterface;

    public OrderPopupButtonMenu(JToggleButton button, OrderActionInterface orderActInterface, javax.swing.JDesktopPane desktopPane) {
        this.popupButton = button;
        this.orderActInterface = orderActInterface;
        this.desktopPane = desktopPane;
//        this.parentFrame = parentFrame;
    }


    public abstract void loadPopMenuAction(final Order pOrder);


    final public void loadPopMenu(final Order pOrder) {
        this.popupOrder = pOrder;
        popupPanel.removeAll();
        decoratePopupMenuPanel(popupPanel);
//        if (popupOrder != null
//                && popupOrder.getOrderId() != null
//                && popupOrder.getOrderId().isEmpty() == false) {

//            popupPanel.add(newOrderButton/*, BorderLayout.LINE_START*/);
            loadPopMenuAction(pOrder);
            createPopupMenu(popupPanel);
            
//        }
    }
}
