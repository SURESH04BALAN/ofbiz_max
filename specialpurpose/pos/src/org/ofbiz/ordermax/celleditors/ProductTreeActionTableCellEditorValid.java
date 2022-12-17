/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.celleditors;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.screens.ProductDetailEditDialog;

/**
 *
 * @author siranjeev
 */
public class ProductTreeActionTableCellEditorValid extends ActionTableCellEditor {

    java.awt.Frame parent = null;
    boolean modal = false;
    XuiSession session = null;
    private final Border red = new LineBorder(Color.red);

    public ProductTreeActionTableCellEditorValid(TableCellEditor editor, java.awt.Frame parent, boolean modal, XuiSession sessionVal) {
        super(editor);
        this.parent = parent;
        this.modal = modal;
        this.session = sessionVal;

    }

    protected void editCell(JTable table, Object partialValue, int row, int column) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProductDetailEditDialog dlg = new ProductDetailEditDialog(parent, true, session);
                dlg.setVisible(true);
            }
        });

    }
}
