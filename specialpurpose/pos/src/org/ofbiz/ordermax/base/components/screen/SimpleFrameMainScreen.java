/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components.screen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.utility.PersonDialogForm;
import org.ofbiz.ordermax.base.ThreePanelContainerFrame;
import org.ofbiz.ordermax.party.contacts.SelectAddressPanel;

/**
 *
 * @author siranjeev
 */
public class SimpleFrameMainScreen extends BaseMainScreen {

    public static String module = SimpleFrameMainScreen.class.getName();
//    public BaseMainPanelInterface panel = null;
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    SimpleScreenInterface childPanel = null;

    static public SimpleFrameMainScreen runController(ControllerOptions options) {

        SimpleFrameMainScreen controller = new SimpleFrameMainScreen(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableFrameDialogScreen(SelectAddressPanel.module, null);
        } else {
            controller.loadSingleNonSizeableInternalFrameDialogScreen(SelectAddressPanel.module, ControllerOptions.getDesktopPane());
        }
        return controller;
    }
    
     static public SimpleFrameMainScreen runControllerInternalFrame(ControllerOptions options) {

        SimpleFrameMainScreen controller = new SimpleFrameMainScreen(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableFrameDialogScreen(SelectAddressPanel.module, null);
        } else {
            controller.loadSinglePanelInternalFrame(SelectAddressPanel.module, ControllerOptions.getDesktopPane());
        }
        return controller;
    }
  
    ControllerOptions options = null;

    public SimpleFrameMainScreen(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.childPanel = options.getSimpleScreenInterface();
        this.options = options;
        module = options.getName();
        if (options.contains("X")) {
            x = (Integer) options.get("X");
        }
        if (options.contains("Y")) {
            y = (Integer) options.get("Y");
        }
    }

    public SimpleFrameMainScreen(SimpleScreenInterface childPanel, String name, XuiSession sess) {
        super(sess);
        this.childPanel = childPanel;
        module = name;

    }
    
    int y = 10;
    int x = 200;
    @Override
    protected void setSizeAndLocation(JInternalFrame contFrame) {

        if (ControllerOptions.getDesktopPane() != null) {
            contFrame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);

        }
        contFrame.setLocation(x, y);
    }

    public void loadScreen(final ContainerPanelInterface f) {
        f.getPanelDetail().setLayout(new java.awt.BorderLayout());
        f.getPanelDetail().add(BorderLayout.CENTER, childPanel.getPanel());
        if (UtilValidate.isNotEmpty(childPanel.getOkButton())) {
            childPanel.getOkButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.okButtonPressed();
                }
            });
        }
        if (UtilValidate.isNotEmpty(childPanel.getCancelButton())) {
            childPanel.getCancelButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.cancelButtonPressed();
                }
            });
        }
    }

    public void addItem(String id) throws Exception {
    }

    public void refreshScreen() {
    }

    @Override
    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
//        panel.addItem(id, price, qty);
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
