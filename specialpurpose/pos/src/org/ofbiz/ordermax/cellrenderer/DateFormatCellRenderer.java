/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.cellrenderer;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.ofbiz.base.util.Debug;
import static org.ofbiz.pos.PosTransaction.rounding;
import static org.ofbiz.pos.PosTransaction.scale;

/**
 *
 * @author siranjeev
 */
public class DateFormatCellRenderer extends DefaultTableCellRenderer {

    private static SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       try{ 
           if( value instanceof java.util.Date) {
            value = f.format(value);
        }
        else if(value instanceof java.sql.Timestamp){
            value = f.format(value);
        }
       }
       catch(Exception e){
           Debug.logError(e, "date format");
       }
        
        setHorizontalAlignment(JLabel.LEFT);
// And pass it on to parent class
        return super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
    }
}
