/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.orderbase;

import java.awt.event.ActionEvent;
import javax.swing.JTable;

/**
 *
 * @author siranjeev
 */
public class RowColumnActionEvent extends ActionEvent{

    public RowColumnActionEvent(Object source, int id, String command) {
        super(source, id, command);
    }
    private int row=-1;
    private int col=-1;
    private JTable table = null;
    
   public RowColumnActionEvent(Object source, int id, String command, int row, int col, JTable table) {
        super(source, id, command);
        
    this.row = row;
    this.col = col;
    this.table=  table;
    }    

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
}
