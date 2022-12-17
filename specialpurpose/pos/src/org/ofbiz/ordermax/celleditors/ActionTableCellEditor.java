/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.celleditors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EventObject;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public abstract class ActionTableCellEditor implements TableCellEditor, ActionListener{ 
    //public final Icon DOTDOTDOT_ICON = new ImageIcon(getClass().getResource("dotdotdot.gif")); 
 
    protected TableCellEditor editor; 
    private final JButton customEditorButton = new JButton("....." /*"\u2026"DOTDOTDOT_ICON*/); 

    public JButton getCustomEditorButton() {
        return customEditorButton;
    }
 
    protected JTable table; 
    protected int row, column; 
 
    public ActionTableCellEditor(TableCellEditor editor){ 
        this.editor = editor; 
        customEditorButton.addActionListener(this); 
 
        // ui-tweaking 
        customEditorButton.setFocusable(false); 
        customEditorButton.setFocusPainted(false); 
        customEditorButton.setMargin(new Insets(0, 0, 0, 0)); 
        customEditorButton.setMinimumSize(new Dimension(20,20));
    } 
        
 /*
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){ 
        JPanel panel = new JPanel(new BorderLayout()); 
        panel.add(editor.getTableCellEditorComponent(table, value, isSelected, row, column)); 
        panel.add(customEditorButton, BorderLayout.EAST); 
        this.table = table; 
        this.row = row; 
        this.column = column; 
        return panel; 
    } 
 */
/*        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
        final JComponent editorComp = (JComponent)editor.getTableCellEditorComponent(table, value, isSelected, row, column);
        JPanel panel = new JPanel(new BorderLayout()){
            protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed){
                InputMap map = editorComp.getInputMap(condition);
                ActionMap am = editorComp.getActionMap();

                if(map!=null && am!=null && isEnabled()){
                    Object binding = map.get(ks);
                    Action action = (binding==null) ? null : am.get(binding);
                    if(action!=null){
                        return SwingUtilities.notifyAction(action, ks, e, editorComp,
                                e.getModifiers());
                    }
                }
                return false;
            }
        };
        panel.setRequestFocusEnabled(true);
        panel.add(editorComp);
        panel.add(customEditorButton, BorderLayout.EAST);
        this.table = table;
        this.row = row;
        this.column = column;
        return panel;
    }
  */
    
    @Override
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
        final JComponent editorComp = (JComponent)editor.getTableCellEditorComponent(table, value, isSelected, row, column);
        JPanel panel = new JPanel(new BorderLayout()){
            @Override
            public void addNotify(){
                super.addNotify();
                editorComp.requestFocus();
            }

            @Override
            protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed){
                InputMap map = editorComp.getInputMap(condition);
                ActionMap am = editorComp.getActionMap();

                if(map!=null && am!=null && isEnabled()){
                    Object binding = map.get(ks);
                    Action action = (binding==null) ? null : am.get(binding);
                    if(action!=null){
                        return SwingUtilities.notifyAction(action, ks, e, editorComp,
                                e.getModifiers());
                    }
                }
                return false;
            }
        };
        panel.setRequestFocusEnabled(true);
        panel.add(editorComp);
        panel.add(customEditorButton, BorderLayout.EAST);
        this.table = table;
        this.row = row;
        this.column = column;
        return panel;
    }
    @Override
    public Object getCellEditorValue(){ 
        return editor.getCellEditorValue(); 
    } 
 
    @Override
    public boolean isCellEditable(EventObject anEvent){ 
        return editor.isCellEditable(anEvent); 
    } 
 
    @Override
    public boolean shouldSelectCell(EventObject anEvent){ 
        return editor.shouldSelectCell(anEvent); 
    } 
 
    @Override
    public boolean stopCellEditing(){ 

        return editor.stopCellEditing(); 
    } 
 
    @Override
    public void cancelCellEditing(){ 
        editor.cancelCellEditing(); 
    } 
 
    @Override
    public void addCellEditorListener(CellEditorListener l){ 
        editor.addCellEditorListener(l); 
    } 
 
    @Override
    public void removeCellEditorListener(CellEditorListener l){ 
        editor.removeCellEditorListener(l); 
    } 
 

    @Override
 public final void actionPerformed(ActionEvent e){
        Object partialValue = editor.getCellEditorValue();
        editor.cancelCellEditing();
        editCell(table, partialValue, row, column);
    }
      protected abstract void editCell(JTable table, Object partialValue, int row, int column);
}
