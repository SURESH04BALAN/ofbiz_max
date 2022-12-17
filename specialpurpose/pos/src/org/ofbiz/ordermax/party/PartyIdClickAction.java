/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author siranjeev
 */
public class PartyIdClickAction implements ActionListener {

    private PartyIdInterface partyInterface;
    private XuiSession session;

    ControllerOptions controllerOptions = null;
    javax.swing.JDesktopPane desktopPane;

    public PartyIdClickAction(PartyIdInterface partyInterface, javax.swing.JDesktopPane desktopPane,
            ControllerOptions controllerOptions) {
        this.partyInterface = partyInterface;

        this.desktopPane = desktopPane;
        this.controllerOptions = controllerOptions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String partyId = partyInterface.getPartyId();
        ControllerOptions options = new ControllerOptions(controllerOptions);
        options.addPartyId(partyId);
        PartyMaintainControllerNew.runController(options);

    }
}
