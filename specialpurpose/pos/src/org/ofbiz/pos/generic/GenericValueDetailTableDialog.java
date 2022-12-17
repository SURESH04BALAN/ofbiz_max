/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

import bsh.ParseException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.pos.generic.DynamicTableModelInterface;

import org.ofbiz.pos.generic.GenericValuePanelInterface;
import org.ofbiz.pos.generic.GenericValueTableDataModel;
import org.ofbiz.pos.generic.MapValueTableDataModel;

/**
 *
 * @author siranjeev
 */
public class GenericValueDetailTableDialog extends javax.swing.JDialog {

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

    public GenericValueDetailTableDialog(java.awt.Frame parent, boolean modal, XuiSession sessionVal, String[][] IdColumnName) {
        super(parent, modal);
        initComponents();
//        myInitComponents();
        session = sessionVal;
        parentFrame = parent;
        this.IdColumnName = IdColumnName;
        jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                    if (lsm.isSelectionEmpty()) {
                        System.out.println(" <none>");
                    } else {
                        // Find out which indexes are selected.
                        int minIndex = lsm.getMinSelectionIndex();
                        int maxIndex = lsm.getMaxSelectionIndex();
                        for (int i = minIndex; i <= maxIndex; i++) {
                            if (lsm.isSelectedIndex(i)) {
                                System.out.println(" " + i);
                                selectedGenericVal = tableModel.getRowGenericData(i);

                                if (selectedGenericVal == null) {
                                    Debug.logInfo("Map val selected", "module");
                                    selectedMapVal = tableModel.getRowMapData(i);
                                }

                                if (selectedGenericVal != null && childPanelInterface != null) {
                                    try {
                                        //            Party party = new Party(val);
                                        //           party.setGenericValue();
                                        org.ofbiz.ordermax.generic.GenericValueObjectInterface partyObj = childPanelInterface.createUIObject(selectedGenericVal);
                                        childPanelInterface.changeUIObject(partyObj);
                                        childPanelInterface.setUIFields();
                                        break;
                                    } catch (ParseException ex) {
                                        Logger.getLogger(GenericValueDetailTableDialog.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (java.text.ParseException ex) {
                                        Logger.getLogger(GenericValueDetailTableDialog.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            }
                        }
                    }
                    /*
                     //                if (!lse.getValueIsAdjusting()) {
                     int row = e.getFirstIndex();

                     try {
                     //                    int column = evt.getColumn();
                     //                  int row = evt.getFirstRow();
                     GenericValue genVal = tableModel.getRowData(row);                        
                     if(genVal!=null){
                     System.out.println("Selection Changed: " + new Integer(row).toString());
                     //            Party party = new Party(val);
                     //           party.setGenericValue();
                     Object partyObj = childPanelInterface.createUIObject(genVal);
                     childPanelInterface.changeUIObject(partyObj);
                     childPanelInterface.setUIFields();
                     }
                     //            OrderMaxUtility.addAPanelToPanel(partyPanel.getContainerPanel(), dlg.getParentPanel());

                     //           System.out.println("row: " + row + " column: " + column);
                     //           jtable.setColumnSelectionInterval(column + 1, column + 1);
                     //           jtable.setRowSelectionInterval(row, row);
                     } catch (ParseException ex) {
                     Logger.getLogger(GenericValueDetailTableDialog.class.getName()).log(Level.SEVERE, null, ex);
                     } catch (java.text.ParseException ex) {
                     Logger.getLogger(GenericValueDetailTableDialog.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     */
                }
            }
        });

//        List<GenericValue> facilityList = PosProductHelper.getInvoiceLists(session.getDelegator());

//        setupOrderTable(List<GenericValue> dataList);
/*        int colCount = tableModel.getColumnCount();
         for (int i = 0; i < colCount; i++) {
         TableColumn column = jtable.getColumnModel().getColumn(i);
         column.setPreferredWidth(tableModel.getColumnWidth(i)); //third column is bigger            
         }
         */
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();

        actionMap.put(cancelName,
                new AbstractAction() {
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

        panelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        panelButton = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        detailPanel = new javax.swing.JPanel();

        setModal(true);
        setPreferredSize(new java.awt.Dimension(786, 740));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panelTable.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelTable.setAutoscrolls(true);
        panelTable.setMinimumSize(new java.awt.Dimension(400, 100));

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
        jtable.setMinimumSize(new java.awt.Dimension(60, 20));
        jScrollPane1.setViewportView(jtable);

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
        );

        panelButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelButton.setAutoscrolls(true);
        panelButton.setMinimumSize(new java.awt.Dimension(400, 20));
        panelButton.setPreferredSize(new java.awt.Dimension(168, 40));

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
                .addContainerGap(656, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );

        panelButtonLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cancelButton)
                .addComponent(okButton))
        );

        getRootPane().setDefaultButton(okButton);

        detailPanel.setMinimumSize(new java.awt.Dimension(200, 50));

        javax.swing.GroupLayout detailPanelLayout = new javax.swing.GroupLayout(detailPanel);
        detailPanel.setLayout(detailPanelLayout);
        detailPanelLayout.setHorizontalGroup(
            detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        detailPanelLayout.setVerticalGroup(
            detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detailPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(detailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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
    Map<String, Object> selectedMapVal = null;

    public Map<String, Object> getSelectedMapVal() {
        return selectedMapVal;
    }

    public void setSelectedMapVal(Map<String, Object> selectedMapVal) {
        this.selectedMapVal = selectedMapVal;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtable;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelTable;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;

    public void setupOrderTableList(List<GenericValue> dataList) {

        GenericValueTableDataModel model = new GenericValueTableDataModel(IdColumnName);
//        model.addTableModelListener(new GenericValueDetailTableDialog.GenericValueTableModelListener());

        jtable.setModel(model);
        tableModel = model;
        tableModel.addRows(dataList);
        jtable.setFillsViewportHeight(true);
        jtable.setSurrendersFocusOnKeystroke(true);

        final JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(textField);
        editor.setClickCountToStart(1);
        int colCount = tableModel.getColumnCount();
        /*
        for (int i = 0; i < colCount; i++) {
            TableColumn column = jtable.getColumnModel().getColumn(i);
            column.setPreferredWidth(tableModel.getColumnWidth(i)); //third column is bigger            
            String colRenderer = tableModel.getColumnRendererName(i);
            if (colRenderer.equals(OrderMaxViewEntity.ProductTreeNode_Cell_Rendere)) {
                ProductListArray productListArray = new ProductListArray(session);
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new ProductTreeActionTableCellEditor(editor, productListArray, parentFrame, true, session));
            } else if (colRenderer.equals(OrderMaxViewEntity.StringDescription_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new StringActionTableCellEditor(editor));
            } else if (colRenderer.equals(OrderMaxViewEntity.OrderId_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new OrderIdActionTableCellEditor(editor, parentFrame, true, session));
            } else if (colRenderer.equals(OrderMaxViewEntity.PartyId_Cell_Renderer)) {
                jtable.getColumn(jtable.getColumnName(i)).setCellEditor(new PartyIdActionTableCellEditor(editor, parentFrame, true, session));
            }

        }
        */
//            panel.pack();
    }

    public void setupOrderTableMap(List<Map<String, Object>> dataList) {
        MapValueTableDataModel model = new MapValueTableDataModel(IdColumnName);
//        model.addTableModelListener(new GenericValueDetailTableDialog.GenericValueTableModelListener());
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
/*
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
        */
    }

    public class GenericValueTableModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent evt) {

            if (evt.getType() == TableModelEvent.UPDATE) {
                try {
                    int column = evt.getColumn();
                    int row = evt.getFirstRow();
                    GenericValue genVal = tableModel.getRowGenericData(row);
                    if (genVal == null) {
                        Debug.logInfo("Map val selected", "module");
                        selectedMapVal = tableModel.getRowMapData(row);
                    }
                    if (childPanelInterface != null) {
                        org.ofbiz.ordermax.generic.GenericValueObjectInterface partyObj = childPanelInterface.createUIObject(genVal);
                        childPanelInterface.changeUIObject(partyObj);
                        childPanelInterface.setUIFields();
                    }
                    //            OrderMaxUtility.addAPanelToPanel(partyPanel.getContainerPanel(), dlg.getParentPanel());

                    //           System.out.println("row: " + row + " column: " + column);
                    //           jtable.setColumnSelectionInterval(column + 1, column + 1);
                    //           jtable.setRowSelectionInterval(row, row);
                } catch (ParseException ex) {
                    Logger.getLogger(GenericValueDetailTableDialog.class.getName()).log(Level.SEVERE, null, ex);
                } catch (java.text.ParseException ex) {
                    Logger.getLogger(GenericValueDetailTableDialog.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public JPanel getParentPanel() {
        return detailPanel;
    }
    private GenericValuePanelInterface childPanelInterface = null;

    public GenericValuePanelInterface getChildPanelInterface() {
        return childPanelInterface;
    }

    public void setChildPanelInterface(GenericValuePanelInterface childPanelInterface) {
        this.childPanelInterface = childPanelInterface;
    }
}
