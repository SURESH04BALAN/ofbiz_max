package com.openbravo.pos.config;

import com.openbravo.basic.BasicException;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import static javolution.util.StandardLog.config;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.ItemListInterface;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class PosConfigurationAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
//    private ListAdapterListModel<ShipmentReceiptComposite> invoiceCompListModel;
    ItemListInterface<ShipmentReceiptComposite> itemListInterface = null;
    public static final String module = PosConfigurationAction.class.getName();
    public static final String nameStr = "Pos Configuration";
    final String iconPathStr = "";
    final String iconPathSmallStr = "";

    private XuiSession session;

    public PosConfigurationAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, name, session, desktopPane);

        this.session = session;
    }

    public PosConfigurationAction(ItemListInterface<ShipmentReceiptComposite> itemListInterface, XuiSession session) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, session);
        this.itemListInterface = itemListInterface;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return nameStr;
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 10;
        frame.setSize(desktopPane.getBounds().width - 2 * x, desktopPane.getBounds().height - 2 * y);
        frame.setLocation(x, y);
    }

    protected void setSizeAndLocation(TwoPanelNonSizableContainerDlg dlg) {
        int y = 10;
        int x = 10;
        dlg.setSize(1000 - 2 * x, 750 - 2 * y);
        dlg.setLocation(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //     JFrmConfig frm  = new JFrmConfig(config);
        //frm.setVisible(true);
        int y = 10;
        int x = 150;
        if (ControllerOptions.getDesktopPane() != null) {
            
            JPanelConfiguration pannConfig = new JPanelConfiguration(ControllerOptions.getSession().getAppConfig());

            //getContentPane().add(config, BorderLayout.CENTER);
            try {
                pannConfig.activate();
            } catch (BasicException ex) { // never thrown ;-)
            }

            //final TwoPanelContainerPanel chooser = new TwoPanelContainerPanel();
            JOptionPane pane = new JOptionPane(pannConfig, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[0], null);
            final JInternalFrame dialog = pane.createInternalFrame(ControllerOptions.getDesktopPane(), "Dialog Frame");

            if (ControllerOptions.getDesktopPane() != null) {
                dialog.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
            }
            dialog.setLocation(x, y);
            dialog.setVisible(true);
        }
    }
}
