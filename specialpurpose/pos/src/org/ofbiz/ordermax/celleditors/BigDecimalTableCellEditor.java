/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.celleditors;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.TableCellEditor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ofbiz.base.util.UtilNumber;

public class BigDecimalTableCellEditor extends AbstractCellEditor
        implements TableCellEditor, FocusListener, AncestorListener, KeyListener {

    private static final Log log = LogFactory.getLog(BigDecimalTableCellEditor.class);
    public static final int scale = UtilNumber.getBigDecimalScale("order.decimals");
    public static final int rounding = UtilNumber.getBigDecimalRoundingMode("order.rounding");

    private static final long serialVersionUID = 1L;
    protected final JTextField textField;

    public JTextField getTextField() {
        return textField;
    }
    private boolean keyPressed;

    /**
     * constructor
     */
    public BigDecimalTableCellEditor() {
        textField = new JTextField();
        textField.setEditable(true);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.addFocusListener(this);
        textField.addAncestorListener(this);
        textField.addKeyListener(this);
        keyPressed = false;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof BigDecimal) {
            textField.setText(new DecimalFormat("#0.##").format(value));
        } else {
            textField.setText(value.toString());
        }
        return textField;
    }

    // the editor should return a BigDecimal
    @Override
    public Object getCellEditorValue() {
        String textValue = textField.getText().trim();
        //replace all comma by full stop
        textValue = textValue.replaceAll(",", ".");
        if (textValue.equals("")) {
            return BigDecimal.ZERO;
        } else {
            //set 0 in case the user type in a non number format
            BigDecimal bdValue = BigDecimal.ZERO;
            try {
                
                bdValue = new BigDecimal(textValue);
            } catch (NumberFormatException eee) {
                if (log.isErrorEnabled()) {
                    log.error("Can't set " + textValue + " to a BigDecimal", eee);
                }
            }

            //round half up the number using the scale given by the configuration
            bdValue = bdValue.setScale(scale, rounding);
            return bdValue;
        }
    }

    @Override
    public boolean isCellEditable(EventObject evt) {
        return !(evt instanceof MouseEvent) || ((MouseEvent) evt).getClickCount() == 1;
    }

    /**
     * Listeners
     */
    @Override
    public void focusGained(FocusEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textField.selectAll();
            }
        });
    }

    @Override
    public void focusLost(FocusEvent e) {
        keyPressed = false;
    }

    @Override
    public void ancestorAdded(final AncestorEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textField.requestFocus();
            }
        });
    }

    @Override
    public void ancestorRemoved(AncestorEvent e) {
    }

    @Override
    public void ancestorMoved(AncestorEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // replace all the cell content only if all the text has been selected
        if (keyPressed == false
                && textField.getSelectionStart() == 0
                && textField.getSelectionEnd() == textField.getText().length()) {
            // delete the the cell content
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                textField.setText("");
                keyPressed = true;
                // replace the content by the char typed in
            } else if (String.valueOf(e.getKeyChar()).matches("[a-zA-z0-9]")) {
                textField.setText(String.valueOf(e.getKeyChar()));
                keyPressed = true;
            }
        }
    }
}
