/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import javax.swing.*;

public class testJavaGui extends JPanel {
    // Variables declaration - do not modify
    // Variables declaration - do not modify
    private javax.swing.JPanel contactPanel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel imLabel;
    private javax.swing.JTextField imTextField;
    private javax.swing.JLabel nicknameLabel;
    private javax.swing.JTextField nicknameTextField;
    private javax.swing.JLabel webLabel;
    private javax.swing.JTextField webTextField;

    public testJavaGui() {
        initComponents();
    }
    private void initComponents() {


        contactPanel = new javax.swing.JPanel();
        nicknameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        webLabel = new javax.swing.JLabel();
        imLabel = new javax.swing.JLabel();
        nicknameTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        webTextField = new javax.swing.JTextField();
        imTextField = new javax.swing.JTextField();

        contactPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nicknameLabel.setText("Nickname:"); // NOI18N

        emailLabel.setText("E-mail:"); // NOI18N

        webLabel.setText("Web:"); // NOI18N

        imLabel.setText("IM:"); // NOI18N

        org.jdesktop.layout.GroupLayout contactPanelLayout = new org.jdesktop.layout.GroupLayout(contactPanel);
        contactPanel.setLayout(contactPanelLayout);
        contactPanelLayout.setHorizontalGroup(
            contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(nicknameLabel)
                    .add(emailLabel)
                    .add(webLabel)
                    .add(imLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(nicknameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .add(emailTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .add(webTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .add(imTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                .addContainerGap())
        );
        contactPanelLayout.setVerticalGroup(
            contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nicknameLabel)
                    .add(nicknameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(emailLabel)
                    .add(emailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(webLabel)
                    .add(webTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(imLabel)
                    .add(imTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(contactPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(contactPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, Short.MAX_VALUE))
        );
    }

    public static void createAndShowUI() {
        JFrame frame = new JFrame("testJavaGui");
        frame.getContentPane().add(new testJavaGui());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
 @Override
            public void run() {
                createAndShowUI();
            }
        });
    }
}