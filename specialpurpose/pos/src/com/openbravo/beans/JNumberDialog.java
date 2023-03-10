//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.beans;

import com.openbravo.pos.util.Utility;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author adrian
 */
public class JNumberDialog extends javax.swing.JDialog {

    private static LocaleResources m_resources;

    private Double m_value;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private com.openbravo.editor.JEditorDoublePositive m_jnumber;

    /**
     * Creates new form JNumberDialog
     */
    public JNumberDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        init();
    }

    /**
     * Creates new form JNumberDialog
     */
    public JNumberDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        init();
    }

    private void init() {

        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("beans_messages");
        }

        m_jKeys = new com.openbravo.editor.JEditorKeys();
        m_jnumber = new com.openbravo.editor.JEditorDoublePositive();
         Font font = new Font("Courier", Font.BOLD,16);
        m_jnumber.setDisplayFont(font);
        initComponents();
        //getRootPane().setDefaultButton(jcmdOK);
        jPanel3.add(m_jKeys);
        jPanel4.add(m_jnumber, java.awt.BorderLayout.CENTER);
        m_jnumber.addEditorKeys(m_jKeys);
        m_jnumber.reset();
        m_jnumber.setDoubleValue(0.0);
        m_jnumber.activate();

        m_jPanelTitle.setBorder(RoundedBorder.createGradientBorder());

        m_value = null;
    }

    private void setTitle(String title, String message, Icon icon) {
        setTitle(title);
        m_lblMessage.setText(message);
        m_lblMessage.setIcon(icon);
    }

    public static Double showEditNumber(Component parent, String title) {
        return showEditNumber(parent, title, null, null);
    }

    public static Double showEditNumber(Component parent, String title, String message) {
        return showEditNumber(parent, title, message, null);
    }

    public static Double showEditNumber(Component parent, String title, String message, Icon icon) {

        Window window = SwingUtilities.windowForComponent(parent);

        JNumberDialog myMsg;
        if (window instanceof Frame) {
            myMsg = new JNumberDialog((Frame) window, true);
        } else {
            myMsg = new JNumberDialog((Dialog) window, true);
        }
        int y = 60;
        int x = 525;
        myMsg.setResizable(true);
        if (ControllerOptions.getDesktopPane() != null) {
            myMsg.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
            Debug.logInfo("myMsg.getSize().height: " + myMsg.getSize().height + "myMsg.getSize().width: " + myMsg.getSize().width, title);;
            // myMsg.getSize().height;
        } else {
            myMsg.setSize(340, 480);
            myMsg.setLocationRelativeTo(null);
        }
        /*try {
            throw new Exception("resize");
        } catch (Exception e) {
            Debug.logError(e, title);
        }*/
        myMsg.setLocation(x, y);
        myMsg.setTitle(title, message, icon);
        myMsg.setVisible(true);
        return myMsg.m_value;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jcmdOK = new javax.swing.JButton();
        jcmdCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanelGrid = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        m_jPanelTitle = new javax.swing.JPanel();
        m_lblMessage = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jcmdOK.setIcon(Utility.loadImage("/com/openbravo/images/button_ok.png"));
        jcmdOK.setText("Ok");
        jcmdOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdOK.setMaximumSize(new java.awt.Dimension(100, 35));
        jcmdOK.setPreferredSize(new java.awt.Dimension(100, 35));
        jcmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdOKActionPerformed(evt);
            }
        });

        jcmdCancel.setIcon(Utility.loadImage("/com/openbravo/images/button_cancel.png"));
        jcmdCancel.setText("Cancel");
        jcmdCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jcmdCancel)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jcmdCancel, jcmdOK});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcmdOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcmdCancel)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jcmdCancel, jcmdOK});

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel4);

        jPanelGrid.add(jPanel3);

        jPanel2.add(jPanelGrid, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        m_jPanelTitle.setLayout(new java.awt.BorderLayout());

        m_lblMessage.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.darkGray), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        m_jPanelTitle.add(m_lblMessage, java.awt.BorderLayout.CENTER);

        getContentPane().add(m_jPanelTitle, java.awt.BorderLayout.NORTH);

        setSize(new java.awt.Dimension(253, 433));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jcmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdOKActionPerformed

        m_value = m_jnumber.getDoubleValue();
        setVisible(false);
        dispose();


    }//GEN-LAST:event_jcmdOKActionPerformed

    private void jcmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdCancelActionPerformed

        setVisible(false);
        dispose();

    }//GEN-LAST:event_jcmdCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        setVisible(false);
        dispose();

    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelGrid;
    private javax.swing.JButton jcmdCancel;
    private javax.swing.JButton jcmdOK;
    private javax.swing.JPanel m_jPanelTitle;
    private javax.swing.JLabel m_lblMessage;
    // End of variables declaration//GEN-END:variables

}
