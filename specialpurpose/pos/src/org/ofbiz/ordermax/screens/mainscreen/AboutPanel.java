/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.mainscreen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ofbiz.ordermax.utility.PidFileUtil;

/**
 *
 * @author BBS Auctions
 */
public class AboutPanel extends javax.swing.JPanel implements ActionListener {

    public static final String RCS_ID = "$Id: AboutDialog.java,v 1.1.1.1 1998/02/22 05:47:55 time Exp $";
    public static final String RCS_REV = "$Revision: 1.1.1.1 $";
    public static final String RCS_NAME = "$Name:  $";
    public static final String VERSION_STR = "1";
    private String messageString;
    //   private TextArea messageText;

    /**
     * Creates new form AboutPanel
     */
    public AboutPanel() {
        initComponents();
        establishDialogContents();
    }

    public void establishDialogContents() {
//        Button button;
        this.messageText.setEditable(false);
        this.messageText.setFont(new Font("Serif", Font.BOLD, 12));

        this.messageText.setText(
                "OrderMax, version "
                + this.VERSION_STR
                + ", Ecommerce and Point of Sale system\n"
                + "\n"
                + "Written by Suresh Balan, sureshyamini@yahoo.com\n"
                + "Copyright (c) 2014 by Sureshkumar Balan\n"
                + "\n"
                + "OFBIZ is free software, which is licensed to you under the\n"
                + "GNU General Public License, version 2. Please see the file\n"
                + "LICENSE for more details, or visit 'www.gnu.org'.\n"
                + "\n"
                + "This software is provided AS-IS, with ABSOLUTELY NO WARRANTY.\n"
                + "\n"
                + "When Jeevi and Nivi learn Java. They will finsh this software.\n"
                + "\n"
                + "YOU ASSUME ALL RESPONSIBILITY FOR ANY AND ALL CONSEQUENCES\n"
                + "THAT MAY RESULT FROM THE USE OF THIS SOFTWARE!"
                + "\n" + "\n\n"
                + "PID: " + PidFileUtil.getProcessPid() + "\n"
                +"Mem usage: " + "jmap -F -histo PID"
        );

        button.addActionListener(this);
        button.setActionCommand("OK");
    }

    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();

        if (command.compareTo("OK") == 0) {
//            this.dispose();
            doClose(RET_OK);
        }
    }
    private List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private int returnStatus = RET_CANCEL;

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        //notify 
        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event); // broadcast to all
        }
//        setVisible(false);
//        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        messageText = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        button = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        messageText.setColumns(20);
        messageText.setRows(5);
        jScrollPane1.setViewportView(messageText);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        button.setText("Ok");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(293, Short.MAX_VALUE)
                .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button)
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea messageText;
    // End of variables declaration//GEN-END:variables
}
