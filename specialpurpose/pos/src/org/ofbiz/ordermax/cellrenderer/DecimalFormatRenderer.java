/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.cellrenderer;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;

/**
 *
 * @author siranjeev
 */
public class DecimalFormatRenderer extends DefaultTableCellRenderer {

    private static final DecimalFormat formatter = new DecimalFormat("#.##");

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof BigDecimal) {
            BigDecimal bdValue = (BigDecimal) value;
            if (bdValue != null) {
                value = formatter.format(bdValue);// bdValue.setScale(scale, rounding);
                setHorizontalAlignment(JLabel.RIGHT);
            }
        }
// And pass it on to parent class
        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
