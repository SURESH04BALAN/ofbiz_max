/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.celleditors.ActionTableCellEditor;

/**
 *
 * @author siranjeev
 *
 */
public class ProductTreeActionTableCellEditor extends ActionTableCellEditor {

    public static final String module = ProductTreeActionTableCellEditor.class.getName();
    java.awt.Frame parent = null;
    boolean modal = false;
    XuiSession session = null;
    private static final Border red = new LineBorder(Color.red);
    ProductDataTreeLoader productListArray = null;
    OrderEnteryPanelInterface orderPanel = null;

    public ProductTreeActionTableCellEditor(OrderEnteryPanelInterface orderPanel, TableCellEditor editor, ProductDataTreeLoader prodArray, java.awt.Frame parent, boolean modal, XuiSession sessionVal) {
        super(editor);
        this.parent = parent;
        this.modal = modal;
        this.session = sessionVal;
        this.orderPanel = orderPanel;
        productListArray = prodArray;
    }
    
    public ProductTreeActionTableCellEditor(TableCellEditor editor) {
        super(editor);
    }

    @Override
    protected void editCell(JTable table, Object partialValue, int row, int column) {
  /*      try {
            
            Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(productListArray);//getUserPartyUserSelection();
            if (genVal != null) {
                if (genVal.containsKey("productId")) {

                    if (orderPanel != null) {

                        JTextField textField = (JTextField) ((DefaultCellEditor) editor).getComponent();
                        if (textField != null) {
                            textField.setText(genVal.get("productId").toString());
                            orderPanel.processProductIdTextField(textField, row);
                            table.setValueAt(genVal.get("productId").toString(), row, column);
                            table.changeSelection(row, OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);

                        }
                    } else {
                        table.setValueAt(genVal.get("productId").toString(), row, column);
                    }
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(ProductTreeActionTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        Debug.logInfo("editCell", module);
            ActionEvent event = new RowColumnActionEvent(this, 1, "productId",  row, column, table);
            for (ActionListener listener : listeners) {
                Debug.logInfo("editCell Action listner", module);
                listener.actionPerformed(event); // broadcast to all
            }
    }

    @Override
    public boolean stopCellEditing() {
        Debug.logInfo("stopCellEditing", module);
/*
        JTextField textField = (JTextField) ((DefaultCellEditor) editor).getComponent();
        if (textField != null) {
            //productListArray
            String prodId = textField.getText();
            if (prodId != null && prodId.isEmpty() == false) {
                try {
                    Key key = productListArray.getProductFromId(prodId);
                } catch (Exception e) {
                    textField.setBorder(red);
                    Debug.logError(e, "stopCellEditing");
                    return false;
                }
            }
        }
*/
        return super.stopCellEditing();
    }

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();
        
    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }
    
}
