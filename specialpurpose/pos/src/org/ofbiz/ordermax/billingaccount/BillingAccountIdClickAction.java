/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import org.ofbiz.ordermax.product.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;

/**
 *
 * @author siranjeev
 */
public class BillingAccountIdClickAction implements ActionListener {

    private PrimaryIdInterface prodInterface;
    private JFrame pFrame;
    private XuiSession session;

    javax.swing.JDesktopPane desktopPane;

    public BillingAccountIdClickAction(PrimaryIdInterface prodInterface,  javax.swing.JDesktopPane desktopPane, XuiSession session) {
        this.prodInterface = prodInterface;
        this.pFrame = pFrame;
        this.session = session;
        this.desktopPane = desktopPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String productId = prodInterface.getPrimaryId();
        if (productId != null) {
            MaintainBillingAccountController productMaintainController = new MaintainBillingAccountController(productId, false,  session);
            productMaintainController.loadTwoPanelInternalFrame(ProductMaintainController.module, desktopPane);

        }
    }
}
