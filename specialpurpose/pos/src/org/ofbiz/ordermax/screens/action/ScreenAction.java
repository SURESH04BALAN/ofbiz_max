/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import mvc.controller.SwingWorkerPropertyChangeListener;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePicker;

/**
 *
 * @author siranjeev
 */
public abstract class ScreenAction extends AbstractAction {

    public final static String BEFORE_SAVE = "Before Save";
    public final static String AFTER_SAVE = "After Save";

    protected JDesktopPane desktopPane = null;
    protected XuiSession session;
//    protected JFrame frame = null;
    protected List<ActionListener> listeners = new ArrayList<ActionListener>();
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    public void setDesktopPane(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }

    public XuiSession getSession() {
        return session;
    }

    public void setSession(XuiSession session) {
        this.session = session;
    }

   /* public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPathSmall() {
        return iconPathSmall;
    }

    public void setIconPathSmall(String iconPathSmall) {
        this.iconPathSmall = iconPathSmall;
    }
    protected String name = "";
    protected String iconPath = "";
    protected String iconPathSmall = "";
    protected ActionType actionType = null;
    private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();

    public ScreenAction(ActionType act) {
        actionType = act;
    }
    
    public ScreenAction(String name) {
        this.desktopPane = ControllerOptions.getDesktopPane();
        this.session = ControllerOptions.getSession();
        this.name = name;
    }

    public ScreenAction(ActionType act, String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {

        this.desktopPane = desktopPane;
        this.session = session;
//        this.frame = frame;
        actionType = act;
        this.name = name;
    }

    public ScreenAction(ActionType act, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
        this.session = session;
//        this.frame = frame;
        actionType = act;
    }

    public ScreenAction(ActionType act, XuiSession session) {
        this.desktopPane = null;
//        this.frame = null;
        this.session = session;
        actionType = act;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType model) {
        this.actionType = model;
    }

    public void loadIcons() {
        this.putValue(Action.NAME, name);
        this.putValue(Action.SHORT_DESCRIPTION, name);
        if (iconPathSmall != null) {
            Debug.logInfo("icon path: " + iconPathSmall, "module");
            ImageIcon icon = new ImageIcon(getImage(iconPathSmall));
            Image image = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            //   ImageIcon icon = new ImageIcon(getImage(iconPathSmall));
            this.putValue(Action.SMALL_ICON, new ImageIcon(image));
        }
        /*if (iconPath != null) {
            
         ImageIcon icon = new ImageIcon(getImage(iconPath), name);
         Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);       
            
         if (icon != null) {
         this.putValue(Action.LARGE_ICON_KEY, new ImageIcon(image));
         }
         }*/
    }

    public abstract void actionPerformed(ActionEvent e);

    public abstract Action getAction();
    /*    public abstract ScreenAction createScreenAction(ActionType act, 
     String name, 
     XuiSession session, 
     javax.swing.JDesktopPane desktopPane, 
     JComponent frame);
     */

    protected byte[] getImage(String fileName) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(DatePicker.class.getClassLoader().getResourceAsStream(fileName));
            byte[] b = new byte[is.available()];
            is.read(b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public void setActionMenuItem(JMenuItem menuItem) {
        menuItem.setAction(this); //.add(new JMenuItem(rf.getAction()));
        ImageIcon icon = (ImageIcon) this.getValue(Action.SMALL_ICON);
        if (icon != null) {
//            menuItem.setIcon(icon);
        }
    }

    public void setActionButtonItem(JButton btnItem) {
        btnItem.setAction(this); //.add(new JMenuItem(rf.getAction()));
        ImageIcon icon = (ImageIcon) this.getValue(Action.SMALL_ICON);
        if (icon != null) {
            btnItem.setIcon(icon);
            btnItem.setText("");
        }
    }

    public void setActionButtonItemToggle(JToggleButton btnItem) {
        btnItem.setAction(this); //.add(new JMenuItem(rf.getAction()));
        ImageIcon icon = (ImageIcon) this.getValue(Action.SMALL_ICON);
        if (icon != null) {
            btnItem.setIcon(icon);
//            btnItem.setText("");
        }
    }

    public void setActionButton(JButton btnItem) {
        btnItem.setAction(this); //.add(new JMenuItem(rf.getAction()));
        ImageIcon icon = (ImageIcon) this.getValue(Action.SMALL_ICON);
        if (icon != null) {
            btnItem.setIcon(icon);
//            btnItem.setText("");
        }
    }

    public void createActionMenuItem(JMenuItem menuItem) {
        menuItem.setAction(this); //.add(new JMenuItem(rf.getAction()));
        ImageIcon icon = (ImageIcon) this.getValue(Action.SMALL_ICON);
        if (icon != null) {
            menuItem.setIcon(icon);
        }
    }

    public void createActionButtonItem(JButton btnItem) {
        btnItem.setAction(this); //.add(new JMenuItem(rf.getAction()));
        ImageIcon icon = (ImageIcon) this.getValue(Action.LARGE_ICON_KEY);
        if (icon != null) {
            btnItem.setIcon(icon);
            btnItem.setText("");
        }
    }
    public JToggleButton createActionButtonItemToggle() {
        return createActionButtonItemToggle(getName());
    }

    public JToggleButton createActionButtonItemToggle(String btnName) {
        //Debug.logInfo(btnName, btnName);
        final JToggleButton btnItem = new JToggleButton(btnName);
        btnItem.setFont(new java.awt.Font("Tahoma", 0, 12));
        btnItem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
//        btnItem.setContentAreaFilled(false);
        btnItem.setHorizontalAlignment(SwingConstants.LEFT);
        btnItem.setHorizontalTextPosition(SwingConstants.LEFT);
        btnItem.addMouseListener(new java.awt.event.MouseAdapter() {
            Font originalFont = null;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                originalFont = btnItem.getFont();
                Map attributes = originalFont.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                btnItem.setFont(originalFont.deriveFont(attributes));
                btnItem.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnItem.setFont(originalFont);
                btnItem.setBackground(UIManager.getColor("control"));
            }
        });
//        setActionButtonItemToggle(btnItem);
        btnItem.setAction(this); //.add(new JMenuItem(rf.getAction())); 
        //Debug.logInfo("btnText: " + btnItem.getText() + "btnName: " + btnItem.getName(), btnName);   
        btnItem.setText(btnName);
        return btnItem;
    }

    public JButton createActionButton() {
        JButton btnItem = new JButton(name);
        btnItem.setFont(new java.awt.Font("Tahoma", 0, 12));
//        btnItem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
//        btnItem.setContentAreaFilled(false);
        setActionButton(btnItem);
        return btnItem;
    }

    public JMenuItem createActionMenuItem() {
        JMenuItem menuItem = new JMenuItem(name);
//        menuItem.setFont(new java.awt.Font("Tahoma", 0, 12));
//        menuItem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
//        menuItem.setContentAreaFilled(false);
        setActionMenuItem(menuItem);
        return menuItem;
    }

    public void addSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
        swingWorkerPropertyChangeListeners.add(swingWorkerPropertyChangeListener);
    }

    public void removeSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
        swingWorkerPropertyChangeListeners.remove(swingWorkerPropertyChangeListener);
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;
    }
}
