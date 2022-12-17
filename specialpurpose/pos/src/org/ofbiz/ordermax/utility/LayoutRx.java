/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 
public class LayoutRx {
    JPanel centerPanel;
    JScrollPane scrollPane;
 
    public LayoutRx() {
        JTextPane headPane = new JTextPane();
        headPane.setBackground(Color.green.darker());
        scrollPane = new JScrollPane(headPane);
        scrollPane.setPreferredSize(new Dimension(200,140));
    }
 
    public JPanel getCenterPanel() {
        JTextPane editorPane = new JTextPane();
        editorPane.setBackground(Color.blue);
        
        JTextPane editorPane1 = new JTextPane();
        editorPane1.setBackground(Color.red);

        JTextPane editorPane2 = new JTextPane();
        editorPane2.setBackground(Color.yellow);

        centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = getConstraints();

        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
  //      JScrollPane pane = new JScrollPane();
  //      centerPanel.add(pane, gbc);
        GridBagConstraints gbc1 = getConstraints();        
        gbc1.weighty = 1.0;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        
        centerPanel.add(new PostalAddressPanel(), gbc1);
        centerPanel.add(new PostalAddressPanel(), gbc1);
        centerPanel.add(new PostalAddressPanel(), gbc1);
        centerPanel.add(new TelecomNumberPanel(), gbc1);
        centerPanel.add(new TelecomNumberPanel(), gbc1);
        centerPanel.add(new PersonPanel(), gbc1);
        centerPanel.add(new PartyRolePanel(), gbc1);        
        centerPanel.add(new ContactMechPanel(), gbc1);   
       
//        pane.add(new JScrollPane(editorPane2), gbc);        
        
        return centerPanel;
    }
 
    static public GridBagConstraints getConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        return gbc;
    }
 
    public JPanel getUIPanel() {
        String[] ids = { "Add", "Remove" };
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = e.getActionCommand();
                boolean in = centerPanel.getComponent(0) == scrollPane;
                if(id.equals("Add")) {
                    if(!in) {
                        centerPanel.add(scrollPane, getConstraints(), 0);
                    }
                } else {
                    if(in) {
                        centerPanel.remove(scrollPane);
                    }
                }
                centerPanel.revalidate();
            }
        };
        JPanel panel = new JPanel();
        for(int i = 0; i < ids.length; i++) {
            JButton button = new JButton(ids[i]);
            button.setActionCommand(ids[i]);
            button.addActionListener(al);
            panel.add(button);
        }
        return panel;
    }
 
    public static void main(String[] args) {
        LayoutRx test = new LayoutRx();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(test.getCenterPanel());
        f.add(test.getUIPanel(), "Last");
        f.setSize(500,600);
        f.setLocation(100,100);
        f.setVisible(true);
    }
}