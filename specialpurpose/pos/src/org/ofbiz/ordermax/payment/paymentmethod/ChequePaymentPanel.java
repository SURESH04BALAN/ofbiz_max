/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.paymentmethod;

import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.entity.EftAccount;
import org.ofbiz.ordermax.entity.PaymentMethod;
import org.ofbiz.ordermax.entity.PaymentMethodType;

/**
 *
 * @author siranjeev
 */
public class ChequePaymentPanel extends javax.swing.JPanel implements PaymentMethodInterface {

    public static final String module = ChequePaymentPanel.class.getName();

    /**
     * Creates new form ChequeDetailForm
     */
    public ChequePaymentPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBankName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtBranch = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtReference = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtComments = new javax.swing.JTextField();

        jLabel2.setText("Company Name on Account:");

        jLabel3.setText("Bank Name:");

        jLabel4.setText("Branch:");

        jLabel6.setText("Cheque Number:");

        jLabel8.setText("Comments:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCompanyName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(txtBankName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBranch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtReference, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtComments, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtBankName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField txtBankName;
    private javax.swing.JTextField txtBranch;
    private javax.swing.JTextField txtComments;
    private javax.swing.JTextField txtCompanyName;
    private javax.swing.JTextField txtReference;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getReference() {
        return txtReference.getText();
    }

    @Override
    public String getComment() {
        return txtComments.getText();
    }

    @Override
    public void setReference(String val) {
        txtReference.setText(val);
    }

    @Override
    public void setComment(String val) {
        txtComments.setText(val);
    }

    @Override
    public void setPartyId(String partyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    PaymentMethod payMethod = null;

    @Override
    public void setPaymentMethod(PaymentMethod payMethod) {
        this.payMethod = payMethod;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    PaymentMethodType paymentMethodType = null;

    @Override
    public void setPaymentMethodType(PaymentMethodType payMethod) {
        paymentMethodType = payMethod;
    }

    @Override
    public PaymentMethodType getPaymentMethodType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setPaymentMethodDetails(Map<String, GenericValue> mapValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    EftAccount eftAccount = null;
    @Override
    public void setPaymentMethodDetails(GenericValue payMethod) {
        if (payMethod != null) {
            eftAccount = new EftAccount(payMethod);
            txtCompanyName.setText(eftAccount.getCompanyNameOnAccount());
            txtBankName.setText(eftAccount.getBankName());
            txtBranch.setText(eftAccount.getRoutingNumber());
        }
    }
}
