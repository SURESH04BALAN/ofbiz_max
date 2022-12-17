/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author siranjeev
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.AbstractAction;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * @see http://stackoverflow.com/questions/5654926
 */
public class ProductPosCardPanel extends JPanel implements ActionListener {

    public static final String module = ProductPosCardPanel.class.getName();
    private static final Random random = new Random();
//    private static final JPanel cards = new JPanel(new CardLayout());
    private final String name;
    private int rows = 0;
    private int columns = 0;
    JButton[][] buttons = null;
    private PosButton[][] productList = null;
    private Action action;
    public Color color = null;
    protected ArrayList<PosButtonStyles> styleList;

    public ProductPosCardPanel(String name, PosButton[][] productList, Action action, int rows, int columns, ArrayList list) {
        this.name = name;
        this.action = action;
        this.styleList = list;

        //   this.setPreferredSize(new Dimension(320, 240));
        //   this.setMaximumSize(new Dimension(320, 240));       
        color = Color.DARK_GRAY; //new Color(Color.DARK_GRAY/*random.nextInt()*/);
        this.setBackground(color);
        this.setLayout(new GridLayout(rows, columns, 10, 10));

//        this.add(new JLabel(name));
        this.rows = rows;
        this.columns = columns;
        this.productList = productList;
        butGen();
    }
    //   ImageIcon leftButtonIcon = createImageIcon("right.gif");
//    ImageIcon middleButtonIcon = createImageIcon("middle.gif");
//    ImageIcon rightButtonIcon = createImageIcon("left.gif");


    public void butGen() {
        String html1 = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + "Y" + "</font></u>"
                + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + "This Should Be Long Enough" + "</font></b><br>";
        buttons = new JButton[rows][columns];
        JButton b2 = new JButton(html1);//key._name);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                JPanel panel = new JPanel(new BorderLayout());
//                panel.setPreferredSize(new Dimension(50, 30));
//                panel.setMaximumSize(new Dimension(140, 30));
//              panel.setPreferredSize(new Dimension(60, 60));                
                panel.setPreferredSize(new Dimension(100, b2.getPreferredSize().height));
                panel.setMaximumSize(new Dimension(140, b2.getMaximumSize().height * 2));
                panel.setMinimumSize(new Dimension(65, b2.getMinimumSize().height));
                panel.setBackground(color);

                PosButton key = productList[i][j];
                String substring = key.getContent();
//            myString.split("\\s+");
//                    String html =  "<html>" +substring+"</html>";
                //String html = "<html><center><b><u>" + substring.charAt(0) + "</u>" + substring.substring(1) + "</b><br>";
                String html = "";
                if (substring != null && substring.length() > 1) {
                    html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + substring.charAt(0) + "</font></u>"
                            + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + substring.substring(1) + "</font></b><br>";
                }
                //JButton b1 = new JButton(html);//key._name);
                JButton b1 = productList[i][j].getButton();

                b1.setText(html);
                b1.setVerticalTextPosition(AbstractButton.CENTER);
                b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                PosButtonStyles sel = getSelectedStyle(key.getStyle());
                if (sel != null) {
                    b1.setBackground(Color.decode("#" + sel.getColorBack()));
                }
                buttons[i][j] = b1;
                buttons[i][j].setActionCommand(key.getName());
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
                this.add(panel);
//                    this.add(b1);
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
        action.actionPerformed(event);
    }

    public void clear() {
        this.removeAll();
        this.updateUI();
    }

    public void updateCurrSelection() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                PosButton key = productList[i][j];
                String substring = key.getContent();
                String html = "";
                if (substring != null && substring.length() > 1) {
                    html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + substring.charAt(0) + "</font></u>"
                            + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + substring.substring(1) + "</font></b><br>";
                }
                //JButton b1 = new JButton(html);//key._name);
                JButton b1 = productList[i][j].getButton();
                b1.setText(html);
                b1.setActionCommand(key.getName());
                PosButtonStyles sel = getSelectedStyle(key.getStyle());
                if (sel != null) {
                    b1.setBackground(Color.decode("#" + sel.getColorBack()));
                }
//                buttons[i][j].addActionListener(this);

                System.out.println("updateCurrSelection" + substring);
            }
        }
    }

    public PosButtonStyles getSelectedStyle(String styleName) {
        PosButtonStyles sel = null;
        for (PosButtonStyles style : styleList) {
            if (styleName.equals(style.getStyleName())) {
                return style;
            }
        }
        return sel;
    }

}
