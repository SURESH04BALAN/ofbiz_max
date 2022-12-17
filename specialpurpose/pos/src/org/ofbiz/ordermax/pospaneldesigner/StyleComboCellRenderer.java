/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.pospaneldesigner;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author administrator
 */
public class StyleComboCellRenderer  extends DefaultListCellRenderer {

        private ListCellRenderer defaultRenderer;
        private ArrayList<PosButtonStyles> listStyle;

        public StyleComboCellRenderer(ListCellRenderer defaultRenderer, ArrayList<PosButtonStyles> list) {
            this.defaultRenderer = defaultRenderer;
            this.listStyle = list;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            Component c = defaultRenderer.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (c instanceof JLabel) {
                if (!isSelected && index >= 0) {
                    PosButtonStyles style = listStyle.get(index); //                   if (index == 0) {
                    c.setBackground(Color.decode("#" + style.getColorBack()));

                }
            } else {
                c.setBackground(Color.red);
                c = super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
            }
            return c;
        }
    }

