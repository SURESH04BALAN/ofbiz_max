/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.pospanel;

/**
 *
 * @author administrator
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

import javax.swing.JTextArea;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.OnePanelNonSizableContainerDlg;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.TreeNode;

public class PosDepartmentPanel extends JPanel
        implements ActionListener, BaseMainPanelInterface {

    public static final String module = PosDepartmentPanel.class.getName();
    protected JTextArea textArea;
    protected String newline = "\n";
    static final private String PREVIOUS = "0";
    static final private String UP = "1";
    static final private String NEXT = "2";
    private final Random random = new Random();
    ArrayList<TreeNode> departmentList = null;
    ArrayList<PosDesktopButton> departmentButtonList = new ArrayList<PosDesktopButton>();

    public ArrayList<PosDesktopButton> getDepartmentButtonList() {
        return departmentButtonList;
    }
    PosDesktopButton[][] buttons = null;//new JButton[4][10];
    public Color color = null;
    private int rows = 0;
    private int columns = 0;
    private XuiSession session = null;
    private Action action;
    public String command = PosPanelHelper.ShowDepartment;

    @Override
    public Insets getInsets() {
        return new Insets(10, 20, 10, 20);
    }

    public PosDepartmentPanel(XuiSession session, ArrayList<TreeNode> departmentList, int rows, int columns) {
        super(new BorderLayout());
        this.session = session;

        color = Color.DARK_GRAY;;//new Color(random.nextInt());
        this.setBackground(color);
//        this.setLayout(new GridLayout(rows, columns, 10, 10));

//        this.add(new JLabel(name));
        this.rows = rows;
        this.columns = columns;
        this.departmentList = departmentList;
        buttons = new PosDesktopButton[rows][columns];

        add(createMenuPanel(), BorderLayout.WEST);
        add(generateButton(), BorderLayout.CENTER);
    }

    private JPanel createMenuPanel() {
        PosLeftMenuPanel leftPanel = new PosLeftMenuPanel();

//        JToolBar toolBar = new JToolBar();
//        toolBar.setFloatable(false);
//        toolBar.setOrientation(JToolBar.VERTICAL);
        leftPanel.setPreferredSize(new Dimension(150, 230));
        leftPanel.panelTop.setLayout(new BorderLayout());
//        toolBar.add();
        leftPanel.panelTop.add(createNavagationPanel());
//        toolBar.addSeparator();
//        leftPanel.panelBottom.setLayout(new BorderLayout());
//        leftPanel.panelBottom.add();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor);
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor);
        return leftPanel;
    }
    public JButton buttonHome = null;

    private JPanel createNavagationPanel() {

        JPanel control = new JPanel(new GridLayout(0, 1, 1, 1));
        JPanel ChildControl = new JPanel(new GridLayout(1, 2));

        buttonHome = new JButton(getButtonText("Home"));/* {
         @Override
         public void actionPerformed(ActionEvent e) {
         //ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, PosPanelHelper.ShowHome);
         //action.actionPerformed(event);
         command = PosPanelHelper.ShowHome;
         }
         });
         */

        buttonHome.setPreferredSize(new Dimension(100, buttonHome.getPreferredSize().height));
        buttonHome.setMaximumSize(new Dimension(140, buttonHome.getMaximumSize().height * 2));
        buttonHome.setMinimumSize(new Dimension(65, buttonHome.getMinimumSize().height));

        control.add(buttonHome);

        return control;
    }

    protected String getButtonText(String name) {
        String html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + name.charAt(0) + "</font></u>"
                + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + name.substring(1) + "</font></b><br>";
        return html;
    }
    private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

    private void notifyListeners(String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    public void refreshScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addItem(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void newItem() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveItem() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isModified() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setIsModified(boolean isModified) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setParentItem(Object val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    protected ProductDataTreeLoader productsArray = null;

    public void setProductListArray(ProductDataTreeLoader m_productsArray) {
        productsArray = m_productsArray;
    }

    public void setDepartmentList(ArrayList<TreeNode> depList) {
        this.departmentList = depList;
    }
    protected String selProdId = null;

    public void loadItem() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int commaPos = e.getActionCommand().indexOf(",");
        String id = e.getActionCommand().substring(0, commaPos);
        String name = e.getActionCommand().substring(commaPos + 1, e.getActionCommand().length());
        Debug.logInfo("Col: " + id + " Row: " + name, "module");
        selProdId = null;
        javax.swing.JFrame frame = new javax.swing.JFrame();

        final OnePanelNonSizableContainerDlg posSelectionDlg = new OnePanelNonSizableContainerDlg(frame, true);
        final Action closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                selProdId = null;
                if (PosPanelHelper.ShowDepartment.equals(e.getActionCommand())) {
                    posSelectionDlg.okButtonPressed();
//                    posSelectionDlg.dispose();

                } else if (PosPanelHelper.ShowHome.equals(e.getActionCommand())) {
                    ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
                    action.actionPerformed(event);
                    posSelectionDlg.okButtonPressed();
//                    posSelectionDlg.dispose();

                } else {
                    selProdId = e.getActionCommand();
                    ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
                    action.actionPerformed(event);
                    posSelectionDlg.okButtonPressed();
//                    posSelectionDlg.dispose();
                }
            }
        };

        Debug.logInfo("build brand panel", module);
        ArrayList<Key> brandList = productsArray.getBrandIds(new Key(id, name));
        ArrayList<TreeNode> brandList1 = new ArrayList<TreeNode>();
        for(Key key : brandList){
            brandList1.add(new TreeNode(key._id,key._name,false,null));
        }
        PosBrandPanel brandPanel = new PosBrandPanel(session, closeAction, brandList1, productsArray);

        posSelectionDlg.getPanelDetail().setLayout(new BorderLayout());
        posSelectionDlg.getPanelDetail().add(brandPanel);
//        posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        posSelectionDlg.setSize(1000, 750);
        posSelectionDlg.setLocation(session.getScreenP1());

//        posSelectionDlg.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
//        f.pack();
        Debug.logInfo("Brand OnePanelNonSizableContainerDlg is opened ", "module");
        posSelectionDlg.setVisible(true);
        Debug.logInfo("Brand OnePanelNonSizableContainerDlg is closed ", "module");
    }

    private JPanel generateButton() {
        JPanel brandPanel = new JPanel(new GridLayout(rows, columns, 5, 5));
        brandPanel.setBackground(color);
        int dataIndex = 0;
        String html1 = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + "Y" + "</font></u>"
                + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + "This Should Be Long Enough" + "</font></b><br>";

        JButton b2 = new JButton(html1);//key._name);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(color);

                panel.setPreferredSize(new Dimension(120, b2.getPreferredSize().height));
                panel.setMaximumSize(new Dimension(160, b2.getMaximumSize().height * 4));
                panel.setMinimumSize(new Dimension(85, b2.getMinimumSize().height));
                panel.setBackground(color);

                if (dataIndex < departmentList.size()) {
                    TreeNode treeNode = departmentList.get(dataIndex);
                    String actionKey = treeNode._id + "," + treeNode._name;
                    String substring = treeNode._name;
                    String html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + substring.charAt(0) + "</font></u>"
                            + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + substring.substring(1) + "</font></b><br>";

                    PosDesktopButton button = new PosDesktopButton(html, treeNode);//key._name);

                    button.setVerticalTextPosition(AbstractButton.BOTTOM);
                    button.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                    buttons[i][j] = button;
                    buttons[i][j].setActionCommand(actionKey);
                    GenericValue genVal = PosProductHelper.getProductCategory(treeNode._id, session.getDelegator());
                    String fileName = null;
                    ImageIcon newIcon = null;
                    Debug.logInfo("genVal : " + treeNode._id, module);
                    if (genVal != null) {
                        fileName = genVal.getString("categoryImageUrl");//
                        Debug.logInfo("genVal fileName : " + fileName, module);
                        if (fileName != null) {

                            ImageIcon icon = BaseHelper.getImage(fileName);

//                    ImageIcon icon = BaseHelper.getImage(field);
                            if (icon != null) {
                                Image img = icon.getImage();
                                Image newimg = img.getScaledInstance(84, 64, java.awt.Image.SCALE_SMOOTH);
                                newIcon = new ImageIcon(newimg);
                            }

                            button.setVerticalTextPosition(AbstractButton.BOTTOM);
                            button.setHorizontalTextPosition(AbstractButton.CENTER); //aka LEFT, for left-to-right locales                    
                            button.setIcon(newIcon);
                        }
                    }

                    //buttons[i][j].addActionListener(this);
                    panel.add(buttons[i][j]);
                    brandPanel.add(panel);
//                    this.add(b1);
                } else {
                    PosDesktopButton b1 = new PosDesktopButton(null);//String.valueOf(i) + "x" + String.valueOf(j), leftButtonIcon);//key._name);
                    b1.setEnabled(false);

                    b1.setVerticalTextPosition(AbstractButton.CENTER);
                    b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                    buttons[i][j] = b1; //new JButton(String.valueOf(i) + "x" + String.valueOf(j));
                    buttons[i][j].setActionCommand("button" + i + "_" + j);
                    //buttons[i][j].addActionListener(this);
                    brandPanel.add(panel);
                }
                departmentButtonList.add(buttons[i][j]);
                dataIndex++;
            }
        }
        return brandPanel;
    }

    static public class PosDesktopButton extends JButton {
        public TreeNode parentTreeNode = null;
        public TreeNode treeNode = null;
        public PosDesktopButton(TreeNode treeNode){
            super();
            this.treeNode = treeNode;
        }
        public PosDesktopButton(String str, TreeNode treeNode){
            super(str);
             this.treeNode = treeNode;
        }
    }
}
