/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import com.openbravo.pos.reports.common.TwoPanelNonSizableReportContainerDlg;
import com.openbravo.pos.reports.common.TwoPanelReportInternalFrame;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;

/**
 *
 * @author siranjeev
 */
abstract public class BaseMainScreen implements PropertyChangeListener {

    public static final String moduleBase = BaseMainScreen.class.getName();
    //protected XuiSession session = null;
//    protected JFrame parentFrame = null;
    //protected Delegator delegator = null;
    protected TreeSelectionInterface treePanel = null;

    protected BaseMainScreen() {
    }

    protected BaseMainScreen(XuiSession session) {
        //this.session = session;
//        this.parentFrame = parentFrame;
        //delegator = session.getDelegator();
    }

    public void addItem(String id) throws Exception {
        throw new Exception("Not implemented ");
    }

    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
        throw new Exception("Not implemented ");
    }

    protected void productTreeMouseClicked(java.awt.event.MouseEvent evt) {
    }

    public void refreshScreen() {
    }

    abstract public void loadScreen(ContainerPanelInterface f);
    private ThreePanelContainerFrame threePanelFrame = null;

    public void setVisibleItems() {
    }
    protected int width = 1000;
    protected int height = 600;

    public void loadThreePanelFrame(String name) {

        threePanelFrame = new ThreePanelContainerFrame(name);
        threePanelFrame.setSize(width, height);
        setSizeAndLocation(twoPanelFrame);
        loadScreen(threePanelFrame);
        threePanelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
        threePanelFrame.setVisible(true);
        setVisibleItems();
        threePanelFrame.setAlwaysOnTop(true);
        threePanelFrame.toFront();
//        f.requestFocus();
        threePanelFrame.setAlwaysOnTop(false);
    }
    private TwoPanelContainerFrame twoPanelFrame = null;

    public void loadScreenTwoPanel(String name) {
        loadTwoPanelFrame(name);
    }

    public void loadTwoPanelFrame(String name) {
        twoPanelFrame = new TwoPanelContainerFrame(name);
        twoPanelFrame.setSize(width, height);
        setSizeAndLocation(twoPanelFrame);
        loadScreen(twoPanelFrame);
        twoPanelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        twoPanelFrame.setSize(width, height);

//        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
        twoPanelFrame.setVisible(true);
        setVisibleItems();
        twoPanelFrame.setAlwaysOnTop(true);
        twoPanelFrame.toFront();
//        f.requestFocus();
        twoPanelFrame.setAlwaysOnTop(false);
    }

    private ThreePanelResizeableContainerInternalFrame threePanelResizeableContainerInternalFrame = null;

    public void loadThreePanelInternalFrame(String name, javax.swing.JDesktopPane desktopPane) {

        threePanelResizeableContainerInternalFrame = new ThreePanelResizeableContainerInternalFrame(name);
        threePanelResizeableContainerInternalFrame.setTitle(getCaption());

//        setSizeAndLocation(threePanelResizeableContainerInternalFrame);
        threePanelResizeableContainerInternalFrame.setSize(width, height);
        setSizeAndLocation(threePanelResizeableContainerInternalFrame);
        loadScreen(threePanelResizeableContainerInternalFrame);
        threePanelResizeableContainerInternalFrame.setDividerLocation(220);
//        threePanelResizeableContainerInternalFrame.setSize(width, height);

//        int y = 10;
//        int x = 10;
//        internalTreePanelFrame.setSize(desktopPane.getBounds().width-2*x, desktopPane.getBounds().height-2*y);
//        internalTreePanelFrame.setLocation(x, y);
//        internalTreePanelFrame.pack();
        desktopPane.add(threePanelResizeableContainerInternalFrame);

        threePanelResizeableContainerInternalFrame.setVisible(true);
        setVisibleItems();
        threePanelResizeableContainerInternalFrame.toFront();
        threePanelResizeableContainerInternalFrame.requestFocus();
    }
    private ThreePanelContainerDlg threePanelContainerDlg = null;

    public void loadThreePanelDialog(java.awt.Frame parent, boolean modal) {

        threePanelContainerDlg = new ThreePanelContainerDlg(parent, modal);
        threePanelContainerDlg.setTitle(getCaption());
        setSizeAndLocation(threePanelContainerDlg);
//        threePanelContainerDlg.setSize(width, height);
        loadScreen(threePanelContainerDlg);
        threePanelContainerDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        threePanelContainerDlg.setLocationRelativeTo(null);
        threePanelContainerDlg.setDividerLocation(220);
//        f.textField = panel.getPartyTextField();
//        f.pack();
        threePanelContainerDlg.setVisible(true);
        returnStatus = threePanelContainerDlg.getReturnStatus();
        //threePanelContainerDlg.setDividerLocation(220);

//        int y = 10;
//        int x = 10;
//        internalTreePanelFrame.setSize(desktopPane.getBounds().width-2*x, desktopPane.getBounds().height-2*y);
//        internalTreePanelFrame.setLocation(x, y);
//        internalTreePanelFrame.pack();
       // threePanelContainerDlg.setVisible(true);
        // setVisibleItems();
        // threePanelContainerDlg.toFront();
        // threePanelContainerDlg.requestFocus();
    }

    private TwoPanelContainerInternalFrame twoInternalFrame;

    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 10;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    protected void setSizeAndLocation(JDialog dlg) {
        int y = 10;
        int x = 10;
        dlg.setSize(1000 - 2 * x, 750 - 2 * y);
        dlg.setLocation(x, y);
    }

    static public void setSizeAndLocation(int x, int y, JDialog dlg) {
        dlg.setSize(1000 - x, 750 - 2 * y);
        dlg.setLocation(2 * x, y);
    }

    protected void setSizeAndLocation(JFrame frame) {
        int y = 10;
        int x = 10;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }
    protected SinglePanelContainerInternalFrame singlePanelContainerInternalFrame;

    public void loadSinglePanelInternalFrame(String name, javax.swing.JDesktopPane desktopPane) {

        if (desktopPane != null) {
            singlePanelContainerInternalFrame = new SinglePanelContainerInternalFrame(name);
            singlePanelContainerInternalFrame.setTitle(getCaption());
            setSizeAndLocation(singlePanelContainerInternalFrame);
            loadScreen(singlePanelContainerInternalFrame);
            desktopPane.add(singlePanelContainerInternalFrame);
            singlePanelContainerInternalFrame.setVisible(true);
            setVisibleItems();
            singlePanelContainerInternalFrame.toFront();
            singlePanelContainerInternalFrame.requestFocus();
        } else {
            //loadScreenTwoPanel(name);
            loadSinglePanelNonSizeableFrameDialogScreen(name, null, null);
        }
    }

    public void loadTwoPanelInternalFrame(String name, javax.swing.JDesktopPane desktopPane) {

        if (desktopPane != null) {
            twoInternalFrame = new TwoPanelContainerInternalFrame(name);
            twoInternalFrame.setTitle(getCaption());
            setSizeAndLocation(twoInternalFrame);
            loadScreen(twoInternalFrame);
            desktopPane.add(twoInternalFrame);
            twoInternalFrame.setVisible(true);
            setVisibleItems();
            twoInternalFrame.toFront();
            twoInternalFrame.requestFocus();
        } else {
            loadScreenTwoPanel(name);
        }
    }

    public void loadTwoPanelInternalFrame(String name, javax.swing.JDesktopPane desktopPane, boolean vertical) {

        twoInternalFrame = new TwoPanelContainerInternalFrame(name);
        twoInternalFrame.setOrientation(vertical);
        twoInternalFrame.setTitle(getCaption());
        setSizeAndLocation(twoInternalFrame);
        loadScreen(twoInternalFrame);
        desktopPane.add(twoInternalFrame);
        twoInternalFrame.setVisible(true);
        setVisibleItems();
        twoInternalFrame.toFront();
        twoInternalFrame.requestFocus();

    }

    TwoPanelResizeableContainerInternalFrame twoResizeInternalFrame = null;

    public void loadTwoPanelSizeableInternalFrame(String name, javax.swing.JDesktopPane desktopPane) {

        if (desktopPane != null) {
            twoResizeInternalFrame = new TwoPanelResizeableContainerInternalFrame(name);
            twoResizeInternalFrame.setTitle(getCaption());
            setSizeAndLocation(twoResizeInternalFrame);
            loadScreen(twoResizeInternalFrame);
            desktopPane.add(twoResizeInternalFrame);
            twoResizeInternalFrame.setVisible(true);
            setVisibleItems();
            twoResizeInternalFrame.toFront();
            twoResizeInternalFrame.requestFocus();
        } else {
            loadScreenTwoPanel(name);
        }
    }

    TwoPanelNonSizableContainerDlg posSelectionDlg = null;

    public void loadNonSizeableTwoPanelDialogScreen(String name, javax.swing.JDesktopPane desktopPane, JFrame parentJFrame) {
        if (desktopPane == null) {
            posSelectionDlg = new TwoPanelNonSizableContainerDlg(parentJFrame, true);
            setSizeAndLocation(posSelectionDlg);
            loadScreen(posSelectionDlg);
            posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            posSelectionDlg.setLocationRelativeTo(null);
            posSelectionDlg.setDividerLocation(0);
//        f.textField = panel.getPartyTextField();
//        f.pack();
            posSelectionDlg.setVisible(true);
            returnStatus = posSelectionDlg.getReturnStatus();
        } else {
            loadNonSizeableInternalFrameDialogScreen(name, desktopPane);
        }
    }

    public TwoPanelNonSizableContainerDlg loadNonSizeableFrameDialogScreen(String name, JFrame parentJFrame) {

        posSelectionDlg = new TwoPanelNonSizableContainerDlg(parentJFrame, true);
        setSizeAndLocation(posSelectionDlg);
        loadScreen(posSelectionDlg);
        posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        posSelectionDlg.setLocationRelativeTo(null);
        posSelectionDlg.setDividerLocation(0);
        posSelectionDlg.setVisible(true);
        return posSelectionDlg;
    }
    public void loadReportInternalFrameScreen(String name, javax.swing.JDesktopPane desktopPane, boolean vertical) {

        TwoPanelReportInternalFrame twoInternalFrame = new TwoPanelReportInternalFrame(name);
//        twoInternalFrame.setOrientation(vertical);
        twoInternalFrame.setTitle(getCaption());
        setSizeAndLocation(twoInternalFrame);
        loadScreen(twoInternalFrame);
        desktopPane.add(twoInternalFrame);
        twoInternalFrame.setVisible(true);
        setVisibleItems();
        twoInternalFrame.toFront();
        twoInternalFrame.requestFocus();

    }

    public TwoPanelNonSizableReportContainerDlg loadNonSizeableReportDialogScreen(String name, JFrame parentJFrame) {

        TwoPanelNonSizableReportContainerDlg posSelectionDlg = new TwoPanelNonSizableReportContainerDlg(parentJFrame, true);
        setSizeAndLocation(posSelectionDlg);
        loadScreen(posSelectionDlg);
        posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        posSelectionDlg.setLocationRelativeTo(null);
        posSelectionDlg.setDividerLocation(0);
        posSelectionDlg.setVisible(true);
        return posSelectionDlg;
    }

    public TwoPanelContainerPanel loadNonSizeableInternalFrameDialogScreen(String name, javax.swing.JDesktopPane desktopPane) {

        final TwoPanelContainerPanel twoPanelContainerPanel = new TwoPanelContainerPanel();
        JOptionPane pane = new JOptionPane(twoPanelContainerPanel, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[0], null);

        loadScreen(twoPanelContainerPanel);

        final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Dialog Caption");
        setSizeAndLocation(dialog);
        twoPanelContainerPanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                returnStatus = twoPanelContainerPanel.getReturnStatus();
                System.out.println("twoPanelContainerPanel getReturnStatus : " + twoPanelContainerPanel.getReturnStatus());
                dialog.setVisible(false);
            }
        });

        dialog.setVisible(true);
        return twoPanelContainerPanel;
    }

    public SinglePanelContainerPanel loadSingleNonSizeableInternalFrameDialogScreen(String name, javax.swing.JDesktopPane desktopPane) {

        final SinglePanelContainerPanel singlePanelContainerPanel = new SinglePanelContainerPanel();
        JOptionPane pane = new JOptionPane(singlePanelContainerPanel, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[0], null);

        loadScreen(singlePanelContainerPanel);

        final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Dialog Caption");
        setSizeAndLocation(dialog);
        singlePanelContainerPanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                returnStatus = singlePanelContainerPanel.getReturnStatus();
                System.out.println("twoPanelContainerPanel getReturnStatus : " + singlePanelContainerPanel.getReturnStatus());
                dialog.setVisible(false);
                ActionEvent event = new ActionEvent(this, returnStatus.ordinal(), "returnStatus");
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event); // broadcast to all
                }
            }
        });
        //dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        return singlePanelContainerPanel;
    }

    SinglePanelNonSizableContainerDlg singlePanelSelectionDlg = null;

    public void loadSinglePanelNonSizeableFrameDialogScreen(String name, javax.swing.JDesktopPane desktopPane, JFrame parentJFrame) {

        if (desktopPane == null) {
            singlePanelSelectionDlg = new SinglePanelNonSizableContainerDlg(parentJFrame, true);
            setSizeAndLocation(singlePanelSelectionDlg);
            loadScreen(singlePanelSelectionDlg);
            singlePanelSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            singlePanelSelectionDlg.setLocationRelativeTo(null);
            singlePanelSelectionDlg.setDividerLocation(0);
//        f.textField = panel.getPartyTextField();
//        f.pack();
            singlePanelSelectionDlg.setVisible(true);
            returnStatus = singlePanelSelectionDlg.getReturnStatus();
        } else {

            final SinglePanelContainerPanel chooser = new SinglePanelContainerPanel();
            JOptionPane pane = new JOptionPane(chooser, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[0], null);
            final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Dialog Frame");
            setSizeAndLocation(dialog);
            loadScreen(chooser);

            chooser.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    returnStatus = chooser.getReturnStatus();
                    if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                        System.out.println("getReturnStatus : "
                                + chooser.getReturnStatus());
                    }
                    dialog.setVisible(false);
                }
            });

            dialog.setVisible(true);
        }

    }

    public String getCaption() {
        return "";
    }

    public void hidePanel() {
        threePanelFrame.setVisible(false);
    }

    public void setCaption(String caption) {
        if (threePanelFrame != null) {
            threePanelFrame.setTitle(caption);
        }
    }

    protected void setInitialFocusField(JTextField field) {
        if (threePanelFrame != null) {
            threePanelFrame.textField = field;
        }
    }

    protected void createTreePanel() {

    }

    abstract public String getClassName();

    static public boolean makeCurrentScreenVisible(String ownerClassName) {
        boolean result = false;
        // Collect all active frames in a array
        Frame[] activeframes = JFrame.getFrames();

        for (int i = 0; i < activeframes.length; i++) {
            Frame frame = activeframes[i];
            Debug.logInfo(frame.toString(), moduleBase);
            Debug.logInfo("Title: " + frame.getTitle() + " Name: " + frame.getName(), moduleBase);
            if (frame instanceof ThreePanelContainerFrame) {
                ThreePanelContainerFrame panel = (ThreePanelContainerFrame) frame;
                Debug.logInfo(panel.getOwnerName(), moduleBase);
                if (panel.getOwnerName().equals(ownerClassName)) {
                    //                   frame.dispose();
//                    panel.setVisible(true);
//                    panel.toFront();
//                    result = true;
                    if (!panel.isVisible()) {
                        panel.dispose();
                    } else {
                        panel.setVisible(true);
                        panel.toFront();
                        result = true;
                    }
                    break;
                }
            } else if (frame instanceof TwoPanelContainerFrame) {
                TwoPanelContainerFrame panel = (TwoPanelContainerFrame) frame;
                Debug.logInfo(panel.getOwnerName(), moduleBase);
                if (panel.getOwnerName().equals(ownerClassName)) {
//                    panel.setVisible(true);
//                    panel.toFront();
//                    result = true;
                    frame.dispose();
                    break;
                }
            }
        }

        return result;
    }

    static public boolean makeInternalFrameVisible(String ownerClassName, JDesktopPane desktopPane) {
        boolean result = false;
        // Collect all active frames in a array
        JInternalFrame[] activeframes = desktopPane.getAllFrames();

        for (int i = 0; i < activeframes.length; i++) {
            JInternalFrame frame = activeframes[i];
            Debug.logInfo(frame.toString(), moduleBase);
            Debug.logInfo("Title: " + frame.getTitle() + " Name: " + frame.getName(), moduleBase);
            if (frame instanceof TwoPanelContainerInternalFrame) {
                TwoPanelContainerInternalFrame panel = (TwoPanelContainerInternalFrame) frame;
                Debug.logInfo(panel.getOwnerName(), moduleBase);
                if (panel.getOwnerName().equals(ownerClassName)) {
                    //frame.setVisible(false);
                    frame.dispose();
//                    panel.setVisible(true);
//                    panel.toFront();
//                    result = true;
                    break;
                }
            } else if (frame instanceof TwoPanelResizeableContainerInternalFrame) {
                TwoPanelResizeableContainerInternalFrame panel = (TwoPanelResizeableContainerInternalFrame) frame;
                Debug.logInfo(panel.getOwnerName(), moduleBase);
                if (panel.getOwnerName().equals(ownerClassName)) {
//                    panel.setVisible(true);
//                    panel.toFront();
//                    result = true;
                    frame.setVisible(false);
                    frame.dispose();
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        System.out.println("Changed property: " + event.getPropertyName() + " [old -> "
                + event.getOldValue() + "] | [new -> " + event.getNewValue() + "]");
    }

    protected ClassLoader getClassLoader() {

        ClassLoader cl = null;
        try {
            cl = ControllerOptions.getSession().getClassLoader();

            if (cl == null) {
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                }
                if (cl == null) {
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, moduleBase);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, moduleBase);
        }
        return cl;
    }
    private ContainerPanelInterface.ReturnStausType returnStatus = ContainerPanelInterface.ReturnStausType.RET_CANCEL;

    public ContainerPanelInterface.ReturnStausType getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(ContainerPanelInterface.ReturnStausType status) {
        returnStatus = status;
    }

    public void setScreenButtons(final ContainerPanelInterface f, SimpleScreenInterface s) {

        s.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        s.getCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
            }
        });
    }

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public void clearActionListener() {
        listeners.clear();
    }

}
