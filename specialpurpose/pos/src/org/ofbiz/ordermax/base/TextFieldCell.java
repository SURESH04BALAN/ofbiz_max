/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author administrator
 */

public class TextFieldCell extends JTextField {
    public TextFieldCell(JTable cellTable) {
        super();                            // calling parent constructor
        final JTable table = cellTable;     // this one is required to get cell editor and stop editing

        this.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            // this function successfully provides cell editing stop
            // on cell losts focus (but another cell doesn't gain focus)
            public void focusLost(FocusEvent e) {
                CellEditor cellEditor = table.getCellEditor();
                if (cellEditor != null)
                    if (cellEditor.getCellEditorValue() != null)
                        cellEditor.stopCellEditing();
                    else
                        cellEditor.cancelCellEditing();
            }
        });
    }
}