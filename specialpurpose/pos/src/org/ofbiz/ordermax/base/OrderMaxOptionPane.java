/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author BBS Auctions
 */
public class OrderMaxOptionPane {

    public static int showConfirmDialog(Component cmpnt, Object o) throws HeadlessException {
        if (ControllerOptions.getDesktopPane() != null) {
            return JOptionPane.showInternalConfirmDialog(ControllerOptions.getDesktopPane(), o);
        } else {
            return JOptionPane.showConfirmDialog(cmpnt, o);
        }
    }

    public static int showConfirmDialog(Component cmpnt, Object o, String string, int i) throws HeadlessException {
        if (ControllerOptions.getDesktopPane() != null) {
            return JOptionPane.showInternalConfirmDialog(ControllerOptions.getDesktopPane(), o, string, i);
        } else {
            return JOptionPane.showConfirmDialog(cmpnt, o, string, i);
        }
    }

    public static int showConfirmDialog(Component cmpnt, Object o, String string, int i, int i1) throws HeadlessException {
        if (ControllerOptions.getDesktopPane() != null) {
            return JOptionPane.showInternalConfirmDialog(ControllerOptions.getDesktopPane(), o, string, i, i1);
        } else {
            return JOptionPane.showConfirmDialog(cmpnt, o, string, i, i1);
        }
    }

    public static int showConfirmDialog(Component cmpnt, Object o, String string, int i, int i1, Icon icon) throws HeadlessException {
        if (ControllerOptions.getDesktopPane() != null) {
            return JOptionPane.showInternalConfirmDialog(ControllerOptions.getDesktopPane(), o, string, i, i1, icon);
        } else {
            return JOptionPane.showConfirmDialog(cmpnt, o, string, i, i1, icon);
        }
    }

    public static void showMessageDialog(Component cmpnt, StringBuilder stringBuilder) throws HeadlessException {
        JTextArea msg = new JTextArea();
        msg.setWrapStyleWord(true);
        JScrollPane scrollpane = new JScrollPane();
        //JList list = new JList(((List<String>)resultMap.get("errorMessageList")).toArray());
        msg.setText(stringBuilder.toString());

                //JPanel panel = new JPanel(new GridLayout(0, 1));
        //panel.add(msg);
        scrollpane = new JScrollPane(msg);
        scrollpane.setPreferredSize(new Dimension(600, 250));//<-----any size you want
        JPanel panel = new JPanel();
        panel.add(scrollpane);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpane.getViewport().add(msg);
        
        if (ControllerOptions.getDesktopPane() != null) {
            JOptionPane.showInternalMessageDialog(ControllerOptions.getDesktopPane(), panel);
        } else {
            JOptionPane.showMessageDialog(cmpnt, panel);
        }
    }

    public static void showMessageDialog(Component cmpnt, Object o) throws HeadlessException {
        if (ControllerOptions.getDesktopPane() != null) {
            JOptionPane.showInternalMessageDialog(ControllerOptions.getDesktopPane(), o);
        } else {
            JOptionPane.showMessageDialog(cmpnt, o);
        }
    }

    public static void showMessageDialog(Component cmpnt, Object o, String string, int i) throws HeadlessException {
        if (ControllerOptions.getDesktopPane() != null) {
            JOptionPane.showInternalMessageDialog(ControllerOptions.getDesktopPane(), o, string, i);
        } else {
            JOptionPane.showMessageDialog(cmpnt, o, string, i);
        }
    }

    public static void showMessageDialog(Component cmpnt, Object o, String string, int i, Icon icon) throws HeadlessException {
        if (ControllerOptions.getDesktopPane() != null) {
            JOptionPane.showInternalMessageDialog(ControllerOptions.getDesktopPane(), o, string, i, icon);
        } else {
            JOptionPane.showMessageDialog(cmpnt, o, string, i, icon);
        }
    }

}
