/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableColumn;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.celleditors.OrderIdActionTableCellEditor;
import org.ofbiz.ordermax.celleditors.PartyIdActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.celleditors.ProductTreeActionTableCellEditorValid;
import org.ofbiz.ordermax.celleditors.StringActionTableCellEditor;

/**
 *
 * @author siranjeev
 */
public class GenericValueTableDialog extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    /**
     * Column Header details
     */
    private String[][] IdColumnName = null;
    /**
     */
    XuiSession session;
    private boolean DEBUG = true;
    protected DynamicTableModelInterface tableModel = null;
    java.awt.Frame parentFrame = null;

    /**
     * Creates new form GenericValueTableDisplayDialog
     */
    /*
     public GenericValueTableDialog(java.awt.Frame parent, boolean modal, XuiSession sessionVal, String[][] IdColumnName, List<GenericValue> dataList) {
     super(parent, modal);
     initComponents();
     session = sessionVal;
     parentFrame = parent;
     this.IdColumnName = IdColumnName;

     //        List<GenericValue> facilityList = PosProductHelper.getInvoiceLists(session.getDelegator());

     GenericValueTableDataModel model = new GenericValueTableDataModel(IdColumnName);
     jtable.setModel(model);
     tableModel = model;
     tableModel.addRows(dataList);
     jtable.setFillsViewportHeight(true);
     setupOrderTable();
     /*        int colCount = tableModel.getColumnCount();
     for (int i = 0; i < colCount; i++) {
     TableColumn column = jtable.getColumnModel().getColumn(i);
     column.setPreferredWidth(tableModel.getColumnWidth(i)); //third column is bigger            
     }

     // Close the dialog when Esc is pressed
     String cancelName = "cancel";
     InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
     inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
     ActionMap actionMap = getRootPane().getActionMap();
     actionMap.put(cancelName, new AbstractAction() {
     public void actionPerformed(ActionEvent e) {
     doClose(RET_CANCEL);
     }
     });
     }
     */
    /**
     * Creates new form GenericValueTableDisplayDialog
     */
    public GenericValueTableDialog(java.awt.Frame parent, boolean modal, XuiSession sessionVal, String[][] IdColumnName) {
        super(parent, modal);
        initComponents();
        session = sessionVal;
        parentFrame = parent;
        this.IdColumnName = IdColumnName;

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    public GenericValueTableDialog(java.awt.Frame parent, boolean modal, XuiSession sessionVal, String classname) {
        super(parent, modal);
        initComponents();
        session = sessionVal;
        parentFrame = parent;
        try {
            //        this.IdColumnName = IdColumnName;
            this.IdColumnName = reflectionCall("org.ofbiz.ordermax.entity." + classname, "getColumnNameId", null, null);
            Delegator delegator = sessionVal.getDelegator();
            List<GenericValue> partyList = PosProductHelper.getGenericValueLists(classname, delegator);        // TODO add your handling code here:
            this.setupOrderTableList(partyList);

        } catch (Exception ex) {
            Logger.getLogger(GenericValueTableDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelDetail = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        panelButton = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0};
        layout.rowHeights = new int[] {0, 0, 0, 0, 0};
        getContentPane().setLayout(layout);

        panelDetail.setAutoscrolls(true);
        panelDetail.setMinimumSize(new java.awt.Dimension(400, 400));

        jScrollPane1.setAutoscrolls(true);

        jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jtable);

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(panelDetail, gridBagConstraints);

        panelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelButton.setAutoscrolls(true);
        panelButton.setMinimumSize(new java.awt.Dimension(400, 20));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(504, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );

        panelButtonLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addGap(21, 21, 21))
        );

        getRootPane().setDefaultButton(okButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        getContentPane().add(panelButton, gridBagConstraints);

        jLabel1.setLabelFor(jScrollPane1);
        jLabel1.setText("Invoice Lisit:");
        jLabel1.setAlignmentX(0.5F);
        jLabel1.setAutoscrolls(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 103;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    public void setGenericValue(GenericValue value) {
    }

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    GenericValue selectedGenericVal = null;

    public GenericValue getSelectedGenericVal() {
        return selectedGenericVal;
    }

    public void setSelectedGenericVal(GenericValue selectedGenericVal) {
        this.selectedGenericVal = selectedGenericVal;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelDetail;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;

    public void setupOrderTableList(List<GenericValue> dataList) {
        GenericValueTableDataModel model = new GenericValueTableDataModel(IdColumnName);
        jtable.setModel(model);
        tableModel = model;
        tableModel.addRows(dataList);
        jtable.setFillsViewportHeight(true);

        jtable.setSurrendersFocusOnKeystroke(true);
        ProductDataTreeLoader productListArray = new ProductDataTreeLoader();
        final JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(textField);
        editor.setClickCountToStart(1);
        int colCount = tableModel.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            TableColumn column = jtable.getColumnModel().getColumn(i);
            column.setPreferredWidth(tableModel.getColumnWidth(i)); //third column is bigger            
            String colRenderer = tableModel.getColumnRendererName(i);
            if (colRenderer.equals(OrderMaxViewEntity.ProductTreeNode_Cell_Rendere)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new ProductTreeActionTableCellEditor(null, editor, productListArray, parentFrame, true, session));
            } else if (colRenderer.equals(OrderMaxViewEntity.StringDescription_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new StringActionTableCellEditor(editor));
            } else if (colRenderer.equals(OrderMaxViewEntity.OrderId_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new OrderIdActionTableCellEditor(editor, parentFrame, true, session));
            } else if (colRenderer.equals(OrderMaxViewEntity.PartyId_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new PartyIdActionTableCellEditor(editor, parentFrame, true, session));
            }
        }

    }

    public void setupOrderTableMap(List<Map<String, Object>> dataList) {
        MapValueTableDataModel model = new MapValueTableDataModel(IdColumnName);
        jtable.setModel(model);
        tableModel = model;
        tableModel.addMapRows(dataList);
        jtable.setFillsViewportHeight(true);

        jtable.setSurrendersFocusOnKeystroke(true);

        final JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(textField);
        editor.setClickCountToStart(1);
        int colCount = tableModel.getColumnCount();
        for (int i = 0; i < colCount; i++) {
            TableColumn column = jtable.getColumnModel().getColumn(i);
            column.setPreferredWidth(tableModel.getColumnWidth(i)); //third column is bigger            
            String colRenderer = tableModel.getColumnRendererName(i);
            if (colRenderer.equals(OrderMaxViewEntity.ProductTreeNode_Cell_Rendere)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new ProductTreeActionTableCellEditorValid(editor, parentFrame, true, session));
            } else if (colRenderer.equals(OrderMaxViewEntity.StringDescription_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new StringActionTableCellEditor(editor));
            } else if (colRenderer.equals(OrderMaxViewEntity.OrderId_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new OrderIdActionTableCellEditor(editor, parentFrame, true, session));
            } else if (colRenderer.equals(OrderMaxViewEntity.PartyId_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new PartyIdActionTableCellEditor(editor, parentFrame, true, session));
            }
        }
    }

    /**
     * Allow for instance call, avoiding certain class circular dependencies.
     * <br /> Calls even private method if java Security allows it.
     *
     * @param aninstance instance on which method is invoked (if null, static
     * call)
     * @param classname name of the class containing the method (can be null -
     * ignored, actually - if instance if provided, must be provided if static
     * call)
     * @param amethodname name of the method to invoke
     * @param parameterTypes array of Classes
     * @param parameters array of Object
     * @return resulting Object
     * @throws CCException if any problem
     */
    public static String[][] reflectionCall(final String classname, final String amethodname, final Class[] parameterTypes, final Object[] parameters) throws Exception {
        String[][] res = null;
        try {
            Class myTarget = Class.forName(classname);
            Object myinstance = myTarget.newInstance();
            Method myMethod;
            myMethod = myTarget.getDeclaredMethod(amethodname);  // Works!
            res = (String[][]) myMethod.invoke(myinstance);            
            
        } catch (final ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (final SecurityException e) {
            throw new Exception(e.getMessage());
        } catch (final NoSuchMethodException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalAccessException e) {
            throw new Exception(e.getMessage());
        } catch (final InvocationTargetException e) {
            throw new Exception(e.getMessage());
        }
        return res;
    }

    public final class CCException extends Exception {

        public CCException(Exception e) {
            super(e);
        }
    }
}
