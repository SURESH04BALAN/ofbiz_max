/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author siranjeev
 */
public class TwoPanelContainerFrame extends javax.swing.JFrame implements ContainerPanelInterface {
public static final String module = TwoPanelContainerFrame.class.getName();
  

    /**
     * Creates new form ThreePanelContainerFrame
     */
    public TwoPanelContainerFrame(String ownerName) {
        initComponents();
        this.ownerName = ownerName;
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentPanel = new javax.swing.JPanel();
        panelButton = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelDetail = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        parentPanel.setLayout(new java.awt.BorderLayout());

        panelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelButton.setPreferredSize(new java.awt.Dimension(415, 45));

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        parentPanel.add(panelButton, java.awt.BorderLayout.PAGE_END);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        panelDetail.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 251, Short.MAX_VALUE)
        );

        jPanel3.add(panelDetail);

        parentPanel.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(parentPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JPanel getPanelDetail() {
        return panelDetail;
    }

   

    public JPanel getjPanel1() {
        return panelDetail;
    }
    public JPanel getPanelParent(){
        return parentPanel;
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
        setVisible(false);
//        dispose();
    }

    public void okButtonPressed() {
        doClose(ReturnStausType.RET_OK);
    }

    public void cancelButtonPressed() {
        doClose(ReturnStausType.RET_CANCEL);
    }
    
    @Override
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
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
    public void setCaption(String title){
        
    }
    @Override
    public ContainerType getContainerType() {
        return ContainerType.TwoPanelContainer;
    }      
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelDetail;
    public javax.swing.JPanel parentPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public JPanel getPanelSelecton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
