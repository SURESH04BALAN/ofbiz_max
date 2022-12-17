/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.ofbiz.base.util.UtilValidate;

public class FilteredJList extends JList {

    private FilterField filterField;
    private int DEFAULT_FIELD_WIDTH = 20;
    private PopUpKeyboard keyboard;

    public FilteredJList() {
        super();
        setModel(new FilterModel());
        filterField = new FilterField(DEFAULT_FIELD_WIDTH);
        keyboard = new PopUpKeyboard(filterField);

//        filterField.setSize(100, 40);
        this.setFont(this.getFont().deriveFont(20.0f));
        this.setFixedCellHeight(40);
        filterField.setFont(filterField.getFont().deriveFont(20.0f));

        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) filterField.getDocument()).setDocumentFilter(filter);

//        filterField.setSize(100, 40);        
        this.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        /* filterField.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
         Point p = filterField.getLocationOnScreen();
         p.y += 30;
         keyboard.setLocation(p);
         keyboard.setVisible(true);
         }
         });*/
    }

    public void setModel(ListModel m) {
        if (!(m instanceof FilterModel)) {
            throw new IllegalArgumentException();
        }
        super.setModel(m);
    }

    public void addItem(Object o) {
        ((FilterModel) getModel()).addElement(o);
    }

    public JTextField getFilterField() {
        return filterField;
    }

    public PopUpKeyboard getKeyboardPanel() {
        return keyboard;
    }

    // test filter list
    public static void main(String[] args) {
        String[] listItems = {"Chrissssssssssss", "Joshua", "Daniel", "Michael", "Don", "Kimi", "Kelly", "Keagan",
            "Chris1", "Joshua1", "Daniel1", "Michael1", "Don1", "Kimi1", "Kelly1", "Keagan1"};
        JFrame frame = new JFrame("FilteredJList");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        // populate list
        FilteredJList list = new FilteredJList();
        for (int i = 0; i < listItems.length; i++) {
            list.addItem(listItems[i]/*.toUpperCase()*/);
        }
        // add to gui
        JScrollPane pane = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(pane, BorderLayout.CENTER);

        frame.getContentPane().add(list.getFilterField(), BorderLayout.NORTH);
        frame.getContentPane().add(list.getKeyboardPanel(), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    // inner class to provide filtered model
    class FilterModel extends AbstractListModel {

        ArrayList items;
        ArrayList filterItems;

        public FilterModel() {
            super();
            items = new ArrayList();
            filterItems = new ArrayList();
        }

        public Object getElementAt(int index) {
            if (index < filterItems.size()) {
                return filterItems.get(index);
            } else {
                return null;
            }
        }

        public int getSize() {
            return filterItems.size();
        }

        public void addElement(Object o) {
            items.add(o);
            refilter();
        }

        private void refilter() {
            filterItems.clear();
            // if (UtilValidate.isNotEmpty(getFilterField().getText())) {
            String term = getFilterField().getText();//.toUpperCase();
            if (!term.isEmpty()) {
                term = term.toUpperCase();
            }
            for (int i = 0; i < items.size(); i++) {
                String val = items.get(i).toString();
                if (UtilValidate.isNotEmpty(val)) {
                    val = val.toUpperCase();
                    if (val.indexOf(term, 0) != -1) {
                        filterItems.add(items.get(i));
                    }
                }

            }
            fireContentsChanged(this, 0, getSize());
            //}
        }
    }

    // inner class provides filter-by-keystroke field
    class FilterField extends JTextField implements DocumentListener {

        public FilterField(int width) {
            super(width);
            getDocument().addDocumentListener(this);
        }

        public void changedUpdate(DocumentEvent e) {
            ((FilterModel) getModel()).refilter();
        }

        public void insertUpdate(DocumentEvent e) {
            ((FilterModel) getModel()).refilter();
        }

        public void removeUpdate(DocumentEvent e) {
            ((FilterModel) getModel()).refilter();
        }
    }

    class UppercaseDocumentFilter extends DocumentFilter {

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text,
                AttributeSet attr) throws BadLocationException {

            fb.insertString(offset, text/*.toUpperCase()*/, attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                AttributeSet attrs) throws BadLocationException {

            fb.replace(offset, length, text/*.toUpperCase()*/, attrs);
        }
    }

    private class PopUpKeyboard extends JPanel implements ActionListener {

        private JTextField txt;
        String firstRow[] = {"~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "<<<"};//BackSpace
        String secondRow[] = {"Tab", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "[", "]"};
        String thirdRow[] = {"Cap", "L", "M", "N", "O", "P", "Q", "R", "S", "T", ":", "\"", "/", "_"};
        String fourthRow[] = {"Shift", "U", "V", "W", "X", "Y", "Z", "\\", ",", ".", "?", "<", "_", ">"};
        String fifthRow[] = {"Ctrl", "Alt", "Space", "Enter", "Delete", "(", ")"};
        //all keys without shift key press
        String noShift = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
        //special charactors on keyboard that has to be addressed duing keypress
        String specialChars = "~-+[]\\;',.?";
        //Jbuttons corresponting to each individual rows 
        JButton first[];
        JButton second[];
        JButton third[];
        JButton fourth[];
        JButton fifth[];

        //default color of the button to be repainted when key released 
        //Color cc = new JButton().getBackground();
        public PopUpKeyboard(final JTextField txt) {
            this.txt = txt;
            setLayout(new GridLayout(5, 1));

            /*paint first keyboard row  and add it to the keyboard*/
            first = new JButton[firstRow.length + 1];
            //get the panel for the  row
            JPanel p = new JPanel(new GridLayout(1, firstRow.length));
            for (int i = 0; i < firstRow.length; ++i) {
                JButton b = createButton(firstRow[i]);
                first[i] = b;
                p.add(first[i]);
            }
            JButton btn = first[firstRow.length - 1];
            removeActionListner(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int pos = txt.getCaretPosition() - 1;
                    if (pos >= 0) {
                        StringBuilder sb = new StringBuilder(txt.getText());
                        sb.deleteCharAt(pos);
//                    txt.setText(txt.getText().substring(0, txt.getText().length() - 1));
                        txt.setText(sb.toString());
                        txt.requestFocus();
                        txt.setCaretPosition(pos);
                    }
                }
            });
            this.add(p);

            /*paint second keyboard row  and add it to the keyboard*/
            second = new JButton[secondRow.length];
            //get the panel for the  row
            p = new JPanel(new GridLayout(1, secondRow.length));
            for (int i = 0; i < secondRow.length; ++i) {
                second[i] = createButton(secondRow[i]);
                p.add(second[i]);
            }
            this.add(p);

            third = new JButton[thirdRow.length];
            //get the panel for the  row
            p = new JPanel(new GridLayout(1, thirdRow.length));
            for (int i = 0; i < thirdRow.length; ++i) {
                third[i] = createButton(thirdRow[i]);
                p.add(third[i]);
            }
            this.add(p);

            /*paint fourth keyboard row  and add it to the keyboard*/
            fourth = new JButton[fourthRow.length];
            //get the panel for the  row
            p = new JPanel(new GridLayout(1, fourthRow.length));
            for (int i = 0; i < fourthRow.length; ++i) {
                fourth[i] = createButton(fourthRow[i]);
                p.add(fourth[i]);
            }
            this.add(p);

            //paint the fifth row
            fifth = new JButton[fifthRow.length];
            //get the panel for the  row
            p = new JPanel(new BorderLayout());
            JPanel altPanel = new JPanel(new GridLayout(1, 2));
            for (int i = 0; i < 2; ++i) {
                fifth[i] = createButton(fifthRow[i]);
                altPanel.add(fifth[i]);
            }
            p.add(altPanel, BorderLayout.WEST);

            JPanel spacePanel = new JPanel(new GridLayout(1, 2));

            fifth[2] = createButton(fifthRow[2]);
            spacePanel.add(fifth[2]);
            btn = fifth[2];
            removeActionListner(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    txt.setText(txt.getText() + " ");

                }
            });

            fifth[3] = createButton(fifthRow[3]);
            spacePanel.add(fifth[3]);

            fifth[4] = createButton(fifthRow[4]);
            spacePanel.add(fifth[4]);
            removeActionListner(fifth[4]);
            fifth[4].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int pos = txt.getCaretPosition();
                    if (pos < txt.getText().length()) {
                        StringBuilder sb = new StringBuilder(txt.getText());
                        sb.deleteCharAt(pos);
//                    txt.setText(txt.getText().substring(0, txt.getText().length() - 1));
                        txt.setText(sb.toString());

                        if (pos <= txt.getText().length()) {
                            txt.setCaretPosition(pos);
                        }
                        txt.requestFocus();
                    }
                }
            });

            p.add(spacePanel, BorderLayout.CENTER);

            JPanel cursorPanel = new JPanel(new GridLayout(1, 3));
            for (int i = 5; i < fifthRow.length; ++i) {
                fifth[i] = createButton(fifthRow[i]);
                cursorPanel.add(fifth[i]);
            }

            p.add(cursorPanel, BorderLayout.EAST);
            this.add(p);

        }

        private JButton createButton(String label) {
            JButton btn = new JButton(label);
            btn.addActionListener(this);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(60, 60));
            Font font = btn.getFont();
            float size = font.getSize();
            if (label.length() == 1) {
                size = size + 15.0f;
            }
            btn.setFont(font.deriveFont(size));
            return btn;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if (actionCommand.length() == 1) {
                txt.setText(txt.getText() + actionCommand);
            }

        }

        private void removeActionListner(JButton currentButton) {

            for (ActionListener al : currentButton.getActionListeners()) {
                currentButton.removeActionListener(al);
            }

        }
    }
}
