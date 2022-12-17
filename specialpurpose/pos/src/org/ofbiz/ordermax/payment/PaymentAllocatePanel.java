/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import javax.swing.*;
import javax.swing.table.*;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;
import org.ofbiz.ordermax.celleditors.ButtonColumnCellEditor;
import org.ofbiz.ordermax.base.InvoiceRolePartyPayment;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author administrator
 */
public class PaymentAllocatePanel extends javax.swing.JPanel implements BaseMainPanelInterface {

    public static final String module = PaymentAllocatePanel.class.getName();
    private List<Map<String, Object>> partyMechList = null;
    protected XuiSession session = null;
    protected Delegator delegator = null;//XuiContainer.getSession().getDelegator();
    private java.awt.Frame parentFrame = null;

    /**
     * Creates new form PaymentPanel
     */
    public PaymentAllocatePanel() {
        initComponents();
    }

    public PaymentAllocatePanel(XuiSession session) {
        delegator = session.getDelegator();
        this.session = session;

        initComponents();
        /*        Action delete = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
         JTable table = (JTable) e.getSource();
         int modelRow = Integer.valueOf(e.getActionCommand());
         ((DefaultTableModel) table.getModel()).removeRow(modelRow);
         }
         };

         for (int i = 0; i < jTable1.getColumnModel().getColumnCount(); i++) {
         TableColumn column = jTable1.getColumnModel().getColumn(i);

         column.setCellEditor(new ButtonColumnCellEditor(jTable1, delete,  i));

         }
         */
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentHeader, "Payment Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelPaymentDetail, "Payment Application");                               
    }

    private List<InvoiceRolePartyPayment> resultlist = new ArrayList<InvoiceRolePartyPayment>();

    public List<InvoiceRolePartyPayment> getResultlist() {
        return resultlist;
    }

    public void setResultlist(List<InvoiceRolePartyPayment> resultlist) {
        this.resultlist = resultlist;
    }

    public void loadInvoiceDetails(String partyId, String roleTypeId, String invoiceTypeId) {

//        String partyId = "SOUTHPACIFIC";
//        String roleTypeId = "BILL_TO_CUSTOMER";
//        String invoiceTypeId = "PURCHASE_INVOICE";
        try {
            List<Map<String, Object>> list = InvoicePaymentHelper.getPurchaseInvoices(partyId, roleTypeId, invoiceTypeId, delegator);
            resultlist = new ArrayList<InvoiceRolePartyPayment>();
            for (Map<String, Object> mapVal : list) {
                InvoiceRolePartyPayment val = new InvoiceRolePartyPayment(mapVal, delegator);
                resultlist.add(val);
            }
            SimpleTableModel model = new SimpleTableModel(resultlist);
            jTable1.setModel(model);

        } catch (GenericEntityException e) {
            Debug.logError(e, "hhi");
        }
    }
    //      ImageIcon icon = new ImageIcon(ButtonColumnCellEditor.getImage("preyear.gif"), "<<");
    //      jButton1.setIcon(icon);

//        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("C:/AuthLog/server/finalpos/specialpurpose/pos/src/org/ofbiz/ordermax/images/larich/coconut--sambol.jpg"))); // NOI18N        
//        initComponent1();
    public JTextField getPartyTextField() {
        return partyIdTextField;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPaymentHeader = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        partyIdTextField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtBankCode = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtPaymentDate = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtBillingContact = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtAmountToAllocate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAllocatedAmount = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtBalanceAmt = new javax.swing.JTextField();
        panelPaymentDetail = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnSelectAllRows = new javax.swing.JButton();
        btnClearSelection = new javax.swing.JButton();
        btnAutoAllocate = new javax.swing.JButton();
        btnClearAll = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelPaymentHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPaymentHeader.setAlignmentX(0.0F);
        panelPaymentHeader.setFocusable(false);
        panelPaymentHeader.setMaximumSize(new java.awt.Dimension(770, 2147483647));
        panelPaymentHeader.setMinimumSize(new java.awt.Dimension(770, 200));
        panelPaymentHeader.setPreferredSize(new java.awt.Dimension(770, 150));
        panelPaymentHeader.setLayout(new java.awt.GridLayout(1, 0));

        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Customer Code:");

        partyIdTextField.setPreferredSize(new java.awt.Dimension(6, 25));
        partyIdTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                partyIdTextFieldFocusLost(evt);
            }
        });

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("Bank Code:");

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("Payment Date:");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBankCode, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(txtPaymentDate, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(partyIdTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(partyIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBankCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {partyIdTextField, txtBankCode, txtPaymentDate});

        jPanel13.add(jPanel16);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        txtBillingContact.setColumns(20);
        txtBillingContact.setRows(5);
        txtBillingContact.setEnabled(false);
        txtBillingContact.setFocusable(false);
        jScrollPane2.setViewportView(txtBillingContact);

        jPanel1.add(jScrollPane2);

        jPanel13.add(jPanel1);

        jPanel3.setPreferredSize(new java.awt.Dimension(201, 183));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Amount to Allocate:");

        txtAmountToAllocate.setPreferredSize(new java.awt.Dimension(6, 25));
        txtAmountToAllocate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAmountToAllocateFocusLost(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Allocated so far:");

        txtAllocatedAmount.setText("0");
        txtAllocatedAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAllocatedAmountFocusLost(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Balance:");

        txtBalanceAmt.setText("0");
        txtBalanceAmt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBalanceAmtFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAmountToAllocate, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(txtAllocatedAmount)
                    .addComponent(txtBalanceAmt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(txtAmountToAllocate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAllocatedAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBalanceAmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtAllocatedAmount, txtAmountToAllocate, txtBalanceAmt});

        jPanel13.add(jPanel3);

        panelPaymentHeader.add(jPanel13);

        add(panelPaymentHeader, java.awt.BorderLayout.PAGE_START);

        panelPaymentDetail.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelPaymentDetail.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "5", "9", "13"},
                {"2", "6", "10", "14"},
                {"3", "7", "11", "15"},
                {"4", "8", "12", null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        panelPaymentDetail.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(806, 40));

        btnSelectAllRows.setText("Sel All Rows");
        btnSelectAllRows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllRowsActionPerformed(evt);
            }
        });

        btnClearSelection.setText("Clear Selection");
        btnClearSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearSelectionActionPerformed(evt);
            }
        });

        btnAutoAllocate.setText("Auto Allocate");
        btnAutoAllocate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoAllocateActionPerformed(evt);
            }
        });

        btnClearAll.setText("Clear All");
        btnClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelectAllRows)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClearSelection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 308, Short.MAX_VALUE)
                .addComponent(btnAutoAllocate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClearAll)
                .addGap(74, 74, 74))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAutoAllocate, btnClearAll});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAutoAllocate)
                        .addComponent(btnClearAll))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSelectAllRows)
                        .addComponent(btnClearSelection)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPaymentDetail.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        add(panelPaymentDetail, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void partyIdTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_partyIdTextFieldFocusLost
        // TODO add your handling code here:
        notifyListeners(OrderMaxUtility.PARTY_CHANGED, "", partyIdTextField.getText());
        Debug.logWarning("Party changed", module);
    }//GEN-LAST:event_partyIdTextFieldFocusLost

    private void txtAmountToAllocateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmountToAllocateFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountToAllocateFocusLost

    private void txtAllocatedAmountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAllocatedAmountFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAllocatedAmountFocusLost

    private void txtBalanceAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBalanceAmtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceAmtFocusLost

    private void btnClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAllActionPerformed

        for (InvoiceRolePartyPayment val : resultlist) {
            val.setAllocationAmount(BigDecimal.ZERO);
        }

        jTable1.updateUI();

        txtBalanceAmt.setText(BigDecimal.ZERO.toString());
        txtAllocatedAmount.setText(BigDecimal.ZERO.toString());
    }//GEN-LAST:event_btnClearAllActionPerformed

    private void btnAutoAllocateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoAllocateActionPerformed
        BigDecimal allocatedAmount = new BigDecimal(txtAllocatedAmount.getText());
        BigDecimal unallocatedAmount = BigDecimal.ZERO; //new BigDecimal(txtBalanceAmt.getText());
        Debug.logInfo("unallocatedAmount " + unallocatedAmount.toString(), "hi");
        Debug.logInfo("paymentAmt " + paymentAmt.toString(), "hi");
        if (allocatedAmount.compareTo(paymentAmt) < 0) {

            unallocatedAmount = paymentAmt.subtract(allocatedAmount, MathContext.UNLIMITED); //new BigDecimal(txtBalanceAmt.getText());
            allocatedAmount = BigDecimal.ZERO;
            for (InvoiceRolePartyPayment val : resultlist) {
                Debug.logInfo("unallocatedAmount " + unallocatedAmount.toString(), "hi");
                if (val.isSelected()) {
                    BigDecimal outAmt = val.getOutstandingAmount();
                    if (outAmt.compareTo(unallocatedAmount) <= 0) {
                        val.setAllocationAmount(outAmt);
                    } else {
                        val.setAllocationAmount(unallocatedAmount);
                    }
                    unallocatedAmount = unallocatedAmount.subtract(val.getAllocationAmount(), MathContext.UNLIMITED);
                }
                allocatedAmount = allocatedAmount.add(val.getAllocationAmount());
            }

            jTable1.updateUI();
        }
        unallocatedAmount = paymentAmt.subtract(allocatedAmount, MathContext.UNLIMITED); //new BigDecimal(txtBalanceAmt.getText());        
        txtBalanceAmt.setText(unallocatedAmount.toString());
        txtAllocatedAmount.setText(allocatedAmount.toString());
    }//GEN-LAST:event_btnAutoAllocateActionPerformed

    private void btnSelectAllRowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAllRowsActionPerformed
        for (InvoiceRolePartyPayment val : resultlist) {
            val.setSelected(true);
        }
        jTable1.updateUI();
    }//GEN-LAST:event_btnSelectAllRowsActionPerformed

    private void btnClearSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSelectionActionPerformed
        for (InvoiceRolePartyPayment val : resultlist) {
            val.setSelected(false);
        }
        jTable1.updateUI();
    }//GEN-LAST:event_btnClearSelectionActionPerformed

    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    public void refreshScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newItem() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveItem() throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    public void loadItem() {
        String roleTypeId = "BILL_TO_CUSTOMER";
        String invoiceTypeId = "PURCHASE_INVOICE";
        txtAllocatedAmount.setText("0");
        txtBalanceAmt.setText("0");
        loadInvoiceDetails(partyIdTextField.getText(), roleTypeId, invoiceTypeId);

//        String partyId = "SOUTHPACIFIC";
//        String roleTypeId = "BILL_TO_CUSTOMER";
//        String invoiceTypeId = "PURCHASE_INVOICE";
    }

    public void setItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isModified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setIsModified(boolean isModified) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    BigDecimal paymentAmt = BigDecimal.ZERO;

    public void setPaymentAmount(BigDecimal val) {
        txtAmountToAllocate.setText(val.toString());
        paymentAmt = val;
    }

    public void setBillingContact(String val) {
        txtBillingContact.setText(val);
    }

    public void setPartyId(String val) {
        partyIdTextField.setText(val);
    }

    public void setBankDetail(String val) {
        txtBankCode.setText(val);
    }

    public void setPaymentDate(String val) {
        txtPaymentDate.setText(val);
    }

    public BigDecimal getUnallocateAmount() {
        return BigDecimal.ZERO;
    }

    public String getBillingContact() {
        return txtBillingContact.getText();
    }

    public String getPartyId() {
        return partyIdTextField.getText();
    }

    public String getBankDetail() {
        return txtBankCode.getText();
    }

    public String getPaymentDate() {
        return txtPaymentDate.getText();

    }
    public static int SELECT_COL = 0;
    public static int DATE_COL = 1;
    public static int INVOICE_COL = 2;
    public static int REFERANCE_COL = 3;
    public static int INVOICE_AMT_COL = 4;
    public static int PAID_AMT_COL = 5;
    public static int BALANCE_AMT_COL = 6;
    public static int ALLOCATION_AMT_COL = 7;
    public static int BRANCH_COL = 8;

    class SimpleTableModel extends AbstractTableModel {

        public String[] m_colNames = {"Sel", "Date", "Invoice", "Reference", "Invoice Amt", "Paid Amt", "Balance Amt", "Allocation", "Branch"};
        public Class[] m_colTypes = {Boolean.class, java.sql.Timestamp.class, String.class, String.class,
            BigDecimal.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, String.class};
        List<InvoiceRolePartyPayment> m_macDataVector = new ArrayList<InvoiceRolePartyPayment>();

        public SimpleTableModel(List<InvoiceRolePartyPayment> data) {
            super();
//            m_macDataVector.add(new InvoiceRolePartyPayment());
            m_macDataVector.addAll(data);

        }

        public int getColumnCount() {
            return m_colNames.length;
        }

        public int getRowCount() {
            return m_macDataVector.size();
        }

        public void setAllRows(Object value, int col) {


            /*
             for (int i = 1; i < m_macDataVector.size(); ++i) {
             SupplierProductAndListPriceData macData = m_macDataVector.get(i);
             switch (col) {
             case PROD_ID_INDEX:
             macData.setProductId((String) value);
             break;
             case PROD_NAME:
             macData.setProductInternalName((String) value);
             break;
             case PROD_DEFAULT_PRICE:
             macData.setListPrice((BigDecimal) value);
             break;
             case PROD_LIST_PRICE:
             macData.setDefaultPrice((BigDecimal) value);
             break;
             case PROD_AVG_COST:
             macData.setAvgCost((BigDecimal) value);
             break;
             case PROD_SUPPLIER_ID:
             macData.setSupplierId((ComboKey) value);
             break;
             case PROD_SUPPLIER_PROD_ID:
             macData.setProductId((String) value);
             break;
             case PROD_SUPPLIER_LAST_PRICE:
             macData.setLastPrice((BigDecimal) value);
             break;
             case PROD_SUPPLIER_SCAN_CODE:
             macData.setScanCode((String) value);
             break;
                
             fireTableCellUpdated(i, col);
             }*/
        }

        public void setValueAt(Object value, int row, int col) {

            InvoiceRolePartyPayment macData = m_macDataVector.get(row);
            if (col == SELECT_COL) {
                macData.setSelected((Boolean) value);
                fireTableCellUpdated(row, col);
            } else if (col == ALLOCATION_AMT_COL) {
                macData.setAllocationAmount((BigDecimal) value);
                fireTableCellUpdated(row, col);
            }
            /*
             switch (col) {
             case PROD_ID_INDEX:
             macData.setProductId((String) value);
             break;
             case PROD_NAME:
             macData.setProductInternalName((String) value);
             break;
             case PROD_DEFAULT_PRICE:
             macData.setListPrice((BigDecimal) value);
             break;
             case PROD_LIST_PRICE:
             macData.setDefaultPrice((BigDecimal) value);
             break;
             case PROD_AVG_COST:
             macData.setAvgCost((BigDecimal) value);
             break;
             case PROD_SUPPLIER_ID:
             macData.setSupplierId((ComboKey) value);
             break;
             case PROD_SUPPLIER_PROD_ID:
             macData.setProductId((String) value);
             break;
             case PROD_SUPPLIER_LAST_PRICE:
             macData.setLastPrice((BigDecimal) value);
             break;
             case PROD_SUPPLIER_SCAN_CODE:
             macData.setScanCode((String) value);
             break;



             }
             fireTableCellUpdated(row, col);
             * */
        }

        public String getColumnName(int col) {
            return m_colNames[col];
        }

        @Override
        public Class getColumnClass(int col) {
            return m_colTypes[col];
        }

        public Object getValueAt(int row, int col) {
            InvoiceRolePartyPayment macData = m_macDataVector.get(row);
            macData.setRowIndex(row);
            if (col == SELECT_COL) {
                return macData.isSelected();
            } else if (col == DATE_COL) {
                return macData.getInvoice().getTimestamp("invoiceDate");
            } else if (col == INVOICE_COL) {
                return macData.getInvoice().getString("invoiceId");
            } else if (col == REFERANCE_COL) {
                return new String();
            } else if (col == INVOICE_AMT_COL) {
                return macData.getInvoiceAmount();
            } else if (col == PAID_AMT_COL) {
                return macData.getPaidAmount();
            } else if (col == BALANCE_AMT_COL) {
                return macData.getOutstandingAmount();
            } else if (col == ALLOCATION_AMT_COL) {
                return macData.getAllocationAmount();
            } else if (col == BRANCH_COL) {
                return new String();
            } else {
                return new String();
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int col) {
            if (col == SELECT_COL) {
                return true;
            } else if (col == DATE_COL) {
                return false;
            } else if (col == INVOICE_COL) {
                return false;
            } else if (col == REFERANCE_COL) {
                return false;
            } else if (col == INVOICE_AMT_COL) {
                return false;
            } else if (col == PAID_AMT_COL) {
                return false;
            } else if (col == BALANCE_AMT_COL) {
                return false;
            } else if (col == ALLOCATION_AMT_COL) {
                return true;
            } else if (col == BRANCH_COL) {
                return false;
            } else {
                return false;
            }

        }

        public boolean hasEmptyRow() {
            if (m_macDataVector.isEmpty()) {
                return false;
            }
            return true;
        }
    }

    public BigDecimal getAmountFromText(String text) {
        BigDecimal amount = BigDecimal.ZERO;
        if (text != null && text.isEmpty() == false) {
            try {
                amount = new BigDecimal(text);
            } catch (Exception e) {
                amount = BigDecimal.ZERO;
            }
        }
        return amount;
    }

    public BigDecimal getAllocatedAmount() {
        return getAmountFromText(txtAllocatedAmount.getText());
    }

    public BigDecimal getUnallocatedAmount() {
        return getAmountFromText(txtBalanceAmt.getText());
    }

    public BigDecimal getTotalAllocatedAndUnallocated() {
        return getAllocatedAmount().add(getUnallocatedAmount());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutoAllocate;
    private javax.swing.JButton btnClearAll;
    private javax.swing.JButton btnClearSelection;
    private javax.swing.JButton btnSelectAllRows;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelPaymentDetail;
    private javax.swing.JPanel panelPaymentHeader;
    private javax.swing.JTextField partyIdTextField;
    private javax.swing.JTextField txtAllocatedAmount;
    private javax.swing.JTextField txtAmountToAllocate;
    private javax.swing.JTextField txtBalanceAmt;
    private javax.swing.JTextField txtBankCode;
    private javax.swing.JTextArea txtBillingContact;
    private javax.swing.JTextField txtPaymentDate;
    // End of variables declaration//GEN-END:variables
}
