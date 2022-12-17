/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.celleditors.ButtonColumnCellEditor;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseHelper;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    ArrayList<Key> productList = null;
    private Action action;
    public Color color = null;
    private XuiSession session = null;

    public ProductPosCardPanel(XuiSession session, String name, ArrayList<Key> productList, Action action, int rows, int columns) {
        this.name = name;
        this.action = action;
        this.session = session;
        //   this.setPreferredSize(new Dimension(320, 240));
        //   this.setMaximumSize(new Dimension(320, 240));       
        color = Color.DARK_GRAY; //new Color(Color.DARK_GRAY/*random.nextInt()*/);
        this.setBackground(color);
        this.setLayout(new GridLayout(rows, columns, 10, 10));

//        this.add(new JLabel(name));
        this.rows = rows;
        this.columns = columns;
        this.productList = productList;
        buttons = new JButton[rows][columns];
        butGen();
    }
    //   ImageIcon leftButtonIcon = createImageIcon("right.gif");
//    ImageIcon middleButtonIcon = createImageIcon("middle.gif");
//    ImageIcon rightButtonIcon = createImageIcon("left.gif");

    private void butGen() {
        Path currentRelativePath = Paths.get("");
        String s =  currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
//            String localPath = "\\Image" + "\\" + editProductId.getText() + "\\" + filePath.getName();
        String path = s;

        int dataIndex = 0;
        String html1 = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + "Y" + "</font></u>"
                + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + "This Should Be Long Enough" + "</font></b><br>";

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
                
                if (dataIndex < productList.size()) {
                    Key key = productList.get(dataIndex);
                    String substring = key._name;
//            myString.split("\\s+");
//                    String html =  "<html>" +substring+"</html>";
                    //String html = "<html><center><b><u>" + substring.charAt(0) + "</u>" + substring.substring(1) + "</b><br>";
                    String html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + substring.charAt(0) + "</font></u>"
                            + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + substring.substring(1) + "</font></b><br>";

                    //+ "<font color=#ffffdd>middle button</font>";
/*
                     if (key._name.length() > 12) {
                     //                        substring = key._name.substring(0, 11);
                     String[] split = key._name.split("\\s+");
                     html = "<html>";
                     boolean isFirst = true;
                     for (String val : split) {
                     if (isFirst) {
                     html = html + val;
                     isFirst = false;
                     } else {
                     html = html + "<p>" + val;
                     //                                "<html>Jeev Col:" + column + "<p> Row: " + row + "</html>";
                     }
                     }
                     html = html + "</html>";
                     }
                     */

//                    Debug.logInfo("Key Class name" + key.toString(), "module");
                    String fileName = null;
                    if (key instanceof TreeNode) {
                        fileName = ((TreeNode) key).getImagePath();//
                    }
                    
                    //GenericValue prod = PosProductHelper.getProduct(key._id, session.getDelegator());
//                    Debug.logInfo("is data file name" + fileName, module);
                    ImageIcon icon = BaseHelper.getImage(fileName);
                    /*
                    BufferedImage img = null;
                    if (fileName != null && fileName.isEmpty() == false) {
                        try {
                            String local_path = s + fileName;
                            File f = new File(local_path);
                            Debug.logInfo("file name" + f.getName(), module);
                            Debug.logInfo("file name" + f.getPath(), module);
                            if (f.exists()) { 
                                //icon = new ImageIcon(OrderMaxUtility.getImage(fileName));//prod.getString("smallImageUrl")));
                                URL url = f.toURI().toURL();
                                img = ImageIO.read(url);
                                icon = new ImageIcon(img);
                            } else {
                                Debug.logInfo("file doesb't exists to load Image file name" + fileName, "module");
                            }
                        } catch (Exception e) {
                            Debug.logInfo("unable to load Image file name" + fileName, "module");
///                            Debug.logError(e,"module");

                        }
                    }
                    */
                    //JLabel pictureLabel = new JLabel();
                    //pictureLabel.setIcon(icon);//new ImageIcon(OrderMaxUtility.getImage("gulab--jamun-mix.jpeg")));
                    //this.add(panel);
                    JButton b1 = new JButton(html, icon);//key._name);
                    //b1.setIcon(icon);
                    //b1.add(pictureLabel);//(img);
//                    b1.setMaximumSize(new Dimension(30, 30));
                    //b1.setPreferredSize(new Dimension(20, 20));
//                    b1.setPreferredSize(new Dimension(100, b1.getPreferredSize().height));
//                    b1.setMaximumSize(new Dimension(140, b1.getMaximumSize().height * 4));
//                    b1.setMinimumSize(new Dimension(65, b1.getMinimumSize().height));

//                    Font font = b1.getFont().deriveFont(Font.PLAIN);
//                    b1.setFont(font);

                    b1.setVerticalTextPosition(AbstractButton.CENTER);
                    b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                    buttons[i][j] = b1;
                    buttons[i][j].setActionCommand(key._id);
                    buttons[i][j].addActionListener(this);
                    panel.add(buttons[i][j]);
                    this.add(panel);
//                    this.add(b1);
                } else {
                    JButton b1 = new JButton();//String.valueOf(i) + "x" + String.valueOf(j), leftButtonIcon);//key._name);
                    b1.setEnabled(false);
                    //b1.setMaximumSize(new Dimension(20, 20));
                    //b1.setPreferredSize(new Dimension(20, 20));
//                    b1.setPreferredSize(new Dimension(100, b1.getPreferredSize().height));
//                    b1.setMaximumSize(new Dimension(140, b1.getMaximumSize().height * 4));
//                    b1.setMinimumSize(new Dimension(65, b1.getMinimumSize().height));

//                    Font font = b1.getFont().deriveFont(Font.PLAIN);
//                    b1.setFont(font);

                    b1.setVerticalTextPosition(AbstractButton.CENTER);
                    b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                    buttons[i][j] = b1; //new JButton(String.valueOf(i) + "x" + String.valueOf(j));
                    buttons[i][j].setActionCommand("button" + i + "_" + j);
                    buttons[i][j].addActionListener(this);
//                    this.add(buttons[i][j]);
//                    buttons[i][j].setMaximumSize(new Dimension(20, 20));
//                    buttons[i][j].setPreferredSize(new Dimension(20, 20));

//                    panel.add(buttons[i][j]);
//                    this.add(panel);
//                    panel.add(buttons[i][j]);
                    this.add(panel);
//                    this.add(b1);

                }

                dataIndex++;
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        return new ImageIcon(ButtonColumnCellEditor.getImage("cashew_curry.gif"));
        //java.net.URL imgURL = path; //ProductPosCardPanel.class.getResource(path);
//        if (imgURL != null) {
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("Couldn't find file: " + imgURL + "\\" + path);
//            return null;
//        }
    }

    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().contains("button")) {
//            int i = Integer.parseInt(Character.toString(e.getActionCommand().replaceAll("button", "").replaceAll("_", "").charAt(0)));
//            int j = Integer.parseInt(Character.toString(e.getActionCommand().replaceAll("button", "").replaceAll("_", "").charAt(1)));
//			intArray[i][j]++;
        System.out.println(e.getActionCommand());
        /*ActionEvent event = new ActionEvent(
         table,
         ActionEvent.ACTION_PERFORMED,
         act);
         */
        ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
        action.actionPerformed(event);

//        }
//		score2();
    }
}