/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.celleditors;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author administrator
 */
public class BigDecimalCellEditor extends DefaultCellEditor {

    private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    private JTextField textField;

    public BigDecimalCellEditor(JTextField textField) {
        super(textField);
        clickCountToStart = 0;
        this.textField = textField;
        this.textField.setHorizontalAlignment(JTextField.RIGHT);
    }

    @Override
    public boolean stopCellEditing() {
        BigDecimal val = BigDecimal.ZERO;
        try {
            double v = new BigDecimal(textField.getText()).doubleValue();
//            if (v < 0) {
//                throw new NumberFormatException();
//            }
        } catch (NumberFormatException e) {
            textField.setBorder(red);
            return false;
        }
        return super.stopCellEditing();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int column) {
        
  
/*
        if ((editor instanceof JTextComponent)) {
            Debug.logInfo("PositiveDecimalCellEditor Jtext component", "Module");
            final JTextComponent jtc = (JTextComponent) editor;
            jtc.requestFocus();

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    jtc.selectAll();
                }
            });
        }*/
        textField.setBorder(black);
      return super.getTableCellEditorComponent(
                table, value, isSelected, row, column);        

    }
    
        @Override
    public Object getCellEditorValue() {
        return new BigDecimal(textField.getText());
    }
}
