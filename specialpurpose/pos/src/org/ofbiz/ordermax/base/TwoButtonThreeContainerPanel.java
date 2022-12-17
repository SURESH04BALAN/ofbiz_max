/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author BBS Auctions
 */
public class TwoButtonThreeContainerPanel extends javax.swing.JPanel implements ContainerPanelInterface {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
//    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
//    public static final int RET_OK = 1;

    /**
     * Creates new form TwoPanelContainerPanel
     */
    public TwoButtonThreeContainerPanel() {
        initComponents();
        
    }

    private List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public JPanel getPanelDetail() {
        return panelDetail;
    }

    public JPanel getParentPanel() {
        return panelParent;
    }

    public JPanel getPanelParent() {
        return this;
    }

    public JTextField textField = null;

    @Override
    public void setVisible(boolean value) {
        super.setVisible(value);
        if (textField != null) {
            textField.requestFocusInWindow();
        }
    }

    public JPanel getPanelButton() {
        return panelButton;
    }
    private ReturnStausType returnStatus = ReturnStausType.RET_CANCEL;

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public ReturnStausType getReturnStatus() {
        return returnStatus;
    }

    private void doClose(ReturnStausType retStatus) {
        returnStatus = retStatus;
        //notify 
        ActionEvent event = new ActionEvent(this, 1, "partyId", new Date().getTime(), 2);
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event); // broadcast to all
        }
//        setVisible(false);
//        dispose();
    }

    @Override
    public void okButtonPressed() {
        doClose(ReturnStausType.RET_OK);
    }

    @Override
    public void cancelButtonPressed() {
        doClose(ReturnStausType.RET_CANCEL);
    }
    /*
     @Override
     public void setVisible(final boolean visible) {
     // make sure that frame is marked as not disposed if it is asked to be visible
     //        if (visible) {
     //            setDisposed(false);
     //        }
     // let's handle visibility...
     if (!visible || !isVisible()) { // have to check this condition simply because super.setVisible(true) invokes toFront if frame was already visible
     super.setVisible(visible);
     }
     // ...and bring frame to the front.. in a strange and weird way
     if (visible) {
     int state = super.getExtendedState();
     state &= ~JFrame.ICONIFIED;
     super.setExtendedState(state);
     super.setAlwaysOnTop(true);
     super.toFront();
     super.requestFocus();
     super.setAlwaysOnTop(false);
     }
     if (textField != null) {
     textField.requestFocusInWindow();
     }
     }

     @Override
     public void toFront() {
     super.setVisible(true);
     int state = super.getExtendedState();
     state &= ~JFrame.ICONIFIED;
     super.setExtendedState(state);
     super.setAlwaysOnTop(true);
     super.toFront();
     super.requestFocus();
     super.setAlwaysOnTop(false);
     }
     */
    protected String ownerName = null;

    public String getOwnerName() {
        return ownerName;
    }

    public void setDividerLocation(int value) {

    }

    public void setCaption(String title) {

    }

    @Override
    public ContainerType getContainerType() {
        return ContainerType.TwoPanelContainer;
    }

    // End of variables declaration                   
    @Override
    public JPanel getPanelSelecton() {
        return panelSelecton;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelParent = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panelSelecton = new javax.swing.JPanel();
        panelDetail = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();
        buttonOk = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelParent.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelParent.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(200, 297));

        panelSelecton.setForeground(new java.awt.Color(255, 0, 51));
        panelSelecton.setPreferredSize(new java.awt.Dimension(403, 200));

        javax.swing.GroupLayout panelSelectonLayout = new javax.swing.GroupLayout(panelSelecton);
        panelSelecton.setLayout(panelSelectonLayout);
        panelSelectonLayout.setHorizontalGroup(
            panelSelectonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelSelectonLayout.setVerticalGroup(
            panelSelectonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(panelSelecton);

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(panelDetail);

        jPanel3.add(jSplitPane1);

        panelParent.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(panelParent, java.awt.BorderLayout.CENTER);

        panelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelButton.setPreferredSize(new java.awt.Dimension(415, 45));

        buttonOk.setText("Ok");
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(245, Short.MAX_VALUE)
                .addComponent(buttonCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOk)
                .addGap(21, 21, 21))
        );

        panelButtonLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonCancel, buttonOk});

        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonOk)
                    .addComponent(buttonCancel))
                .addContainerGap())
        );

        add(panelButton, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        okButtonPressed();
    }//GEN-LAST:event_buttonOkActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        cancelButtonPressed();
    }//GEN-LAST:event_buttonCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOk;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelParent;
    private javax.swing.JPanel panelSelecton;
    // End of variables declaration//GEN-END:variables
}
