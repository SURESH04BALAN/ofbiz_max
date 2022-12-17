/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.celleditors;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author siranjeev
 */
public class PositiveIntegerCellEditor extends DefaultCellEditor {

    private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    private JTextField textField;

    public PositiveIntegerCellEditor(JTextField textField) {
        super(textField);
        clickCountToStart = 0;
        this.textField = textField;
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
    }

    @Override
    public boolean stopCellEditing() {
        try {
            int v = Integer.valueOf(textField.getText());
            if (v < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            textField.setBorder(red);
            return false;
        }
        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int column) {

        Component editor = super.getTableCellEditorComponent(
                table, value, isSelected, row, column);

        if ((editor instanceof JTextComponent)) {
            Debug.logInfo("PositiveIntegerCellEditor Jtext component", "Module");
            final JTextComponent jtc = (JTextComponent) editor;
            jtc.requestFocus();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    jtc.selectAll();
                }
            });
        }
        textField.setBorder(black);
        return editor;
    }
}
