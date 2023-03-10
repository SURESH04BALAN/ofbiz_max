/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author administrator
 */
public class TextFieldCellEditor extends DefaultCellEditor {

    TextFieldCell textField;    // an instance of edit field
    Class<?> columnClass;       // specifies cell type class
    Object valueObject;         // for storing correct value before editing

    public TextFieldCellEditor(TextFieldCell tf, Class<?> cc) {
        super(tf);
        textField = tf;
        columnClass = cc;
        valueObject = null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        TextFieldCell tf = (TextFieldCell) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        if (value != null) {
            tf.setText(value.toString());
        }
        // we have to save current value to restore it on another cell selection
        // if edited value couldn't be parsed to this cell's type
        valueObject = value;
        return tf;
    }

    @Override
    public Object getCellEditorValue() {
        try {
            // converting edited value to specified cell's type
            if (columnClass.equals(Double.class)) {
                return Double.parseDouble(textField.getText());
            } else if (columnClass.equals(Float.class)) {
                return Float.parseFloat(textField.getText());
            } else if (columnClass.equals(Integer.class)) {
                return Integer.parseInt(textField.getText());
            } else if (columnClass.equals(Byte.class)) {
                return Byte.parseByte(textField.getText());
            } else if (columnClass.equals(String.class)) {
                return textField.getText();            
            } else if (columnClass.equals(BigDecimal.class)) {
                return new BigDecimal(textField.getText());
            }

            
        } catch (NumberFormatException ex) {
            Debug.logError(ex, "Module");
        }

        // this handles restoring cell's value on jumping to another cell
        if (valueObject != null) {
            if (valueObject instanceof Double) {
                return ((Double) valueObject).doubleValue();
            } else if (valueObject instanceof Float) {
                return ((Float) valueObject).floatValue();
            } else if (valueObject instanceof Integer) {
                return ((Integer) valueObject).intValue();
            } else if (valueObject instanceof Byte) {
                return ((Byte) valueObject).byteValue();
            } else if (valueObject instanceof String) {
                return (String) valueObject;
            
            } else if (valueObject instanceof BigDecimal) {
                return (BigDecimal) valueObject;
            }            
        }

        return null;
    }
}