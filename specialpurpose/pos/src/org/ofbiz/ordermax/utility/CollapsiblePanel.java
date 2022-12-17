/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

/**
 *
 * @author rgd
 */
public class CollapsiblePanel extends JPanel {

    
 public static void main(String[] args) {  
  
        //CollapsablePanel cp = new CollapsablePanel("test", buildPanel());  
     JPanel cp = buildPanel();
  
        JFrame f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new JScrollPane(cp));  
        f.setSize(360, 500);  
        f.setLocation(200, 100);  
        f.setVisible(true);  
    }  
  
    public static JPanel buildPanel() {  
        GridBagConstraints gbc = new GridBagConstraints();  
        gbc.insets = new Insets(2, 1, 2, 1);  
        gbc.weightx = 1.0;  
        gbc.weighty = 1.0;  
  
        CollapsiblePanel p1 = new CollapsiblePanel();  
        p1.setTitle("Text Collapse Panel");
        JPanel p = new JPanel();  
        JPanel p2 = new JPanel();          
        p1.setLayout(new GridBagLayout());
        gbc.gridwidth = gbc.RELATIVE;  
        p1.add(new JButton("button 1"), gbc);  
        gbc.gridwidth = gbc.REMAINDER;  
        p1.add(new JButton("button 2"), gbc);  
        gbc.gridwidth = gbc.RELATIVE;  
        p1.add(new JButton("button 3"), gbc);  
        gbc.gridwidth = gbc.REMAINDER;  
        p1.add(new JButton("button 4"), gbc);  
        
        GridBagConstraints gbc1 = new GridBagConstraints();  
        gbc1.insets = new Insets(2, 1, 2, 1);  
        gbc1.weightx = 1.0;  
        gbc1.weighty = 1.0;  
          
        p2.setLayout(new GridBagLayout());
        gbc1.gridwidth = gbc1.RELATIVE;  
        p2.add(new JButton("button 1"), gbc1);  
        gbc1.gridwidth = gbc1.REMAINDER;  
        p2.add(new JButton("button 2"), gbc1);  
        gbc1.gridwidth = gbc1.RELATIVE;  
        p2.add(new JButton("button 3"), gbc1);  
        gbc1.gridwidth = gbc1.REMAINDER;  
        p2.add(new JButton("button 4"), gbc1);  
        
        p.setLayout(new BorderLayout());
        p.add(BorderLayout.NORTH, p1);
        p.add(BorderLayout.CENTER, p2);
        return p;  
    }  
    
    String title = "Title";
    TitledBorder border;

    public CollapsiblePanel() {
        border = BorderFactory.createTitledBorder(title);
        setBorder(border);
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
//        addMouseListener(mouseListener);
    }

    MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            toggleVisibility();
        }
    };

    ComponentListener contentComponentListener = new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent e) {
            updateBorderTitle();
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            updateBorderTitle();
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        firePropertyChange("title", this.title, this.title = title);
    }

    @Override
    public Component add(Component comp) {
        comp.addComponentListener(contentComponentListener);
        Component r = super.add(comp);
        updateBorderTitle();
        return r;
    }

    @Override
    public Component add(String name, Component comp) {
        comp.addComponentListener(contentComponentListener);
        Component r = super.add(name, comp);
        updateBorderTitle();
        return r;
    }

    @Override
    public Component add(Component comp, int index) {
        comp.addComponentListener(contentComponentListener);
        Component r = super.add(comp, index);
        updateBorderTitle();
        return r;
    }

    @Override
    public void add(Component comp, Object constraints) {
        comp.addComponentListener(contentComponentListener);
        super.add(comp, constraints);
        updateBorderTitle();
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        comp.addComponentListener(contentComponentListener);
        super.add(comp, constraints, index);
        updateBorderTitle();
    }

    @Override
    public void remove(int index) {
        Component comp = getComponent(index);
        comp.removeComponentListener(contentComponentListener);
        super.remove(index);
    }

    @Override
    public void remove(Component comp) {
        comp.removeComponentListener(contentComponentListener);
        super.remove(comp);
    }

    @Override
    public void removeAll() {
        for (Component c : getComponents()) {
            c.removeComponentListener(contentComponentListener);
        }
        super.removeAll();
    }

    protected void toggleVisibility() {
        toggleVisibility(hasInvisibleComponent());
    }

    protected void toggleVisibility(boolean visible) {
        for (Component c : getComponents()) {
            c.setVisible(visible);
        }
        updateBorderTitle();
    }

    protected void updateBorderTitle() {
        String arrow = "";
        if (getComponentCount() > 0) {
            arrow = (hasInvisibleComponent() ? "▽" : "△");
        }
        border.setTitle(title + " " + arrow);
        repaint();
    }

    protected final boolean hasInvisibleComponent() {
        for (Component c : getComponents()) {
            if (!c.isVisible()) {
                return true;
            }
        }
        return false;
    }

}
