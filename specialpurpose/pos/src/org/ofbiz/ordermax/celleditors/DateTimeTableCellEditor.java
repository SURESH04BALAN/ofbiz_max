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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
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
import org.ofbiz.ordermax.base.DatePicker;
import org.ofbiz.ordermax.base.ObservingTextField;

public class DateTimeTableCellEditor extends DefaultCellEditor
        implements TableCellEditor, FocusListener, AncestorListener, KeyListener {

    private static final Log log = LogFactory.getLog(DateTimeTableCellEditor.class);
    public static final int scale = UtilNumber.getBigDecimalScale("order.decimals");
    public static final int rounding = UtilNumber.getBigDecimalRoundingMode("order.rounding");

    private static final long serialVersionUID = 1L;
    protected JTextField textField = null;

    public JTextField getTextField() {
        return textField;
    }
    private boolean keyPressed;

    /**
     * constructor
     */
    public DateTimeTableCellEditor(JTextField tField) {
        super(tField);
//        textField = new JTextField();
        this.textField = tField;
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
        textField.setText(value.toString());
        return textField;
    }

    // the editor should return a BigDecimal
    public Object getCellEditorValue() {
        try {
            String textValue = textField.getText().trim();
            //replace all comma by full stop
            textValue = textValue.replaceAll(",", ".");
            SimpleDateFormat format = new SimpleDateFormat("d/MM/yy");
            Date parsed = format.parse(textValue);
            java.sql.Timestamp sqlTime = new java.sql.Timestamp(parsed.getTime());
            return sqlTime;
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
//    public boolean isCellEditable(EventObject evt) {
//        return !(evt instanceof MouseEvent) || ((MouseEvent) evt).getClickCount() == 1;
//    }
    public boolean isCellEditable(EventObject anEvent) {
        boolean isEditable = super.isCellEditable(anEvent);
        if (isEditable && anEvent instanceof MouseEvent) {
            setupDatePicker();
        }
        return isEditable;
    }
  // Set the edit placeholder for the cell, and make the delegate our DatePickerComponent
    // (the component that displays the DatePicker).
    private void setupDatePicker() {
        editorComponent = new JLabel("*** Editing ***");
        delegate = new DatePickerComponent(this);
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

    // This component contains the actual date picker (represented here by a dialog with a
    // text field and an OK button).
    class DatePickerComponent extends EditorDelegate {

        CellEditor cellEditor;  // reference to our cell editor so we can tell it when we're finished.

        // My fake DatePicker.
        DatePicker datePicker;

        // The component constructor - stores the cell editor and creates the date picker.
        DatePickerComponent(CellEditor cellEditor) {
            this.cellEditor = cellEditor;
            createDatePicker();
        }

        // Do whatever you need to create the date picker here.
        private void createDatePicker() {
//            datePicker = new DatePicker();
            final Locale locale = Locale.getDefault();//getLocale(lang);
            datePicker = new DatePicker((ObservingTextField) textField, locale);
            //previously Selectd date
            java.util.Date selectedDate = datePicker.parseDate(textField.getText());
            datePicker.setSelectedDate(selectedDate);
            datePicker.start(textField);
        }

        // Set the date to be edited into the date picker and display / edit it.
        public void setValue(Object value) {
//            datePicker.setValue(value);
//            datePicker.startEditing();
        }

        // Get the edited date out of the date picker and return it.
        public Object getCellEditorValue() {
            
            return new java.sql.Timestamp(0);//datePicker.getValue();
        }

        // Call this when the date picker edit has finished.
        private void stopEditing() {
            cellEditor.stopCellEditing();
        }

    }
}