/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospanel;

/**
 *
 * @author administrator
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JToolBar;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.ofbiz.entity.Delegator;
import org.ofbiz.ordermax.celleditors.ButtonColumnCellEditor;
import static org.ofbiz.ordermax.pospanel.ProductPosCardPanel.createImageIcon;

public class PosDepartmentPanel extends JPanel
        implements ActionListener, BaseMainPanelInterface {

    protected JTextArea textArea;
    protected String newline = "\n";
    static final private String PREVIOUS = "0";
    static final private String UP = "1";
    static final private String NEXT = "2";
    private final Random random = new Random();
    ArrayList<Key> departmentList = null;
    JButton[][] buttons = null;//new JButton[4][10];
    public Color color = null;
    private int rows = 0;
    private int columns = 0;
    private XuiSession session = null;
    private Action action;

    @Override
    public Insets getInsets() {
        return new Insets(10, 20, 10, 20);
    }

    public PosDepartmentPanel(XuiSession session, Action action, ArrayList<Key> departmentList, int rows, int columns) {
        super(new BorderLayout());
        this.session = session;
        this.action = action;
        color = Color.DARK_GRAY;;//new Color(random.nextInt());
        this.setBackground(color);
//        this.setLayout(new GridLayout(rows, columns, 10, 10));

//        this.add(new JLabel(name));
        this.rows = rows;
        this.columns = columns;
        this.departmentList = departmentList;
        buttons = new JButton[rows][columns];
        
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
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor );
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor );       
        return leftPanel;
    }
    
    private JPanel createNavagationPanel() {


        JPanel control = new JPanel(new GridLayout(0, 1, 1, 1));
        JPanel ChildControl = new JPanel(new GridLayout(1, 2));

        ChildControl.add(new JButton(new AbstractAction(getButtonText("Prev")) {
            @Override
            public void actionPerformed(ActionEvent e) {
//                CardLayout cl = (CardLayout) cards.getLayout();
//                cl.previous(cards);
            }
        }));

        ChildControl.add(new JButton(new AbstractAction(getButtonText("Next")) {
            @Override
            public void actionPerformed(ActionEvent e) {
//                CardLayout cl = (CardLayout) cards.getLayout();
//                cl.next(cards);
            }
        }));
//        control.add(ChildControl);

        JButton button = new JButton(new AbstractAction(getButtonText("Departments")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, PosPanelHelper.ShowDepartment);
                action.actionPerformed(event);
            }
        });
        button.setPreferredSize(new Dimension(100, button.getPreferredSize().height));
        button.setMaximumSize(new Dimension(140, button.getMaximumSize().height * 2));
        button.setMinimumSize(new Dimension(65, button.getMinimumSize().height));
  //      control.add(button);

        button = new JButton(new AbstractAction(getButtonText("Home")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, PosPanelHelper.ShowHome);
                action.actionPerformed(event);
            }
        });

        button.setPreferredSize(new Dimension(100, button.getPreferredSize().height));
        button.setMaximumSize(new Dimension(140, button.getMaximumSize().height * 2));
        button.setMinimumSize(new Dimension(65, button.getMinimumSize().height));

        control.add(button);

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

    public void setDepartmentList(ArrayList<Key> depList) {
        this.departmentList = depList;
    }
    protected String selProdId = null;

    public void loadItem() {

        final Action closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                selProdId = e.getActionCommand();
//                posSelectionDlg.setVisible(false);
//                posSelectionDlg.dispose();
            }
        };

        Action departmentSelectAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int commaPos = e.getActionCommand().indexOf(",");
                String id = e.getActionCommand().substring(0, commaPos);
                String name = e.getActionCommand().substring(commaPos + 1, e.getActionCommand().length());
                Debug.logInfo("Col: " + id + " Row: " + name, "module");

                ArrayList<Key> brandList = productsArray.getBrandIds(new Key(id, name));
                PosBrandPanel brandPanel = new PosBrandPanel(session, closeAction, brandList, productsArray);
//                brandPanel.setProductListArray(productsArray);
//                brandPanel.setBrandList(brandList);
//                brandPanel.loadItem();
                javax.swing.JFrame frame = new javax.swing.JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
//          frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//          frame.setUndecorated(true);

                //frame.setUndecorated(true);
                //frame.setBounds(frame.getGraphicsConfiguration().getBounds()); 
                //getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);

                OnePanelNonSizableContainerDlg posSelectionDlg = new OnePanelNonSizableContainerDlg(frame, true);


                posSelectionDlg.getPanelDetail().setLayout(new BorderLayout());
                posSelectionDlg.getPanelDetail().add(brandPanel);
                posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                posSelectionDlg.setSize(1024, 768);
                posSelectionDlg.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
//        f.pack();
                Debug.logInfo("Brand OnePanelNonSizableContainerDlg is visible ", "module");
                posSelectionDlg.setVisible(true);
                Debug.logInfo("Brand OnePanelNonSizableContainerDlg is closed ", "module");

//                System.out.println("product sel id: " + e.getActionCommand());
//                selProdId = e.getActionCommand();
//                ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
//                action.actionPerformed(event);
                //Debug.logInfo("Col: " + modelCol + " Row: " + modelRow, "module");
                //((DefaultTableModel) table.getModel()).removeRow(modelRow);
            }
        };

        if (productsArray != null && departmentList != null) {

            /*
             ArrayList<Key> brandList = productsArray.getBrandIds(new Key(id, name));
             for (Key key : departmentList) {
             //            for (int i = 0; i < 5; i++) {

             ArrayList<Key> prodArray = productsArray.loadProductList(key._id);

             //split product array
             Map<String, ArrayList<Key>> maplist = new HashMap<String, ArrayList<Key>>();
             int i = 0;
             int j = 1;
             ArrayList<Key> keyList = new ArrayList<Key>();
             int maxPageButtons = 30;
             String name = key._name + "[" + String.valueOf(j) + "]";
             if (prodArray.size() < maxPageButtons) {
             name = key._name;
             }

             maplist.put(name, keyList);

             for (Key keyProd : prodArray) {
             if (i++ == 30) {
             i = 0;
             j++;
             keyList = new ArrayList<Key>();
             maplist.put(key._name + "[" + String.valueOf(j) + "]", keyList);
             }
             keyList.add(keyProd);
             }

             //crate our panels
             for (Entry<String, ArrayList<Key>> entryDept : maplist.entrySet()) {
             ProductPosCardPanel p = new ProductPosCardPanel(String.valueOf(entryDept.getKey()), entryDept.getValue(), productSelectAction, 6, 5);
             String id = entryDept.getKey() + key._id;
             cards.add(p, id);
             //first button
             JButton button = makeNavigationButton("Back24", id,
             "Back to previous something-or-other",
             entryDept.getKey());
             toolBar.add(button);
             button.addActionListener(actionToolButton);
             }

             }*/
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*        String cmd = e.getActionCommand();
         String description = null;

         // Handle each button.
         if (PREVIOUS.equals(cmd)) { //first button clicked
         description = "taken you to the previous <something>.";
         } else if (UP.equals(cmd)) { // second button clicked
         description = "taken you up one level to <something>.";
         } else if (NEXT.equals(cmd)) { // third button clicked
         description = "taken you to the next <something>.";
         }
         */


        int commaPos = e.getActionCommand().indexOf(",");
        String id = e.getActionCommand().substring(0, commaPos);
        String name = e.getActionCommand().substring(commaPos + 1, e.getActionCommand().length());
        Debug.logInfo("Col: " + id + " Row: " + name, "module");
        selProdId = null;
//        brandPanel.setProductListArray(productsArray);
//        brandPanel.setBrandList(brandList);
//        brandPanel.loadItem();
        javax.swing.JFrame frame = new javax.swing.JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
//          frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//          frame.setUndecorated(true);

        //frame.setUndecorated(true);
        //frame.setBounds(frame.getGraphicsConfiguration().getBounds()); 
        //getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);

        final OnePanelNonSizableContainerDlg posSelectionDlg = new OnePanelNonSizableContainerDlg(frame, true);
        final Action closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                selProdId = null;
                if (PosPanelHelper.ShowDepartment.equals(e.getActionCommand())) {
                    posSelectionDlg.setVisible(false);
//                    posSelectionDlg.dispose();


                } else if (PosPanelHelper.ShowHome.equals(e.getActionCommand())) {
                    posSelectionDlg.setVisible(false);
//                    posSelectionDlg.dispose();
                    ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
                    action.actionPerformed(event);

                } else {
                    selProdId = e.getActionCommand();
                    posSelectionDlg.setVisible(false);
//                    posSelectionDlg.dispose();
                    ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
                    action.actionPerformed(event);
                }

            }            
        };
        
        ArrayList<Key> brandList = productsArray.getBrandIds(new Key(id, name));
        PosBrandPanel brandPanel = new PosBrandPanel(session, closeAction, brandList, productsArray);

        posSelectionDlg.getPanelDetail().setLayout(new BorderLayout());
        posSelectionDlg.getPanelDetail().add(brandPanel);
        posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        posSelectionDlg.setSize(1024, 768);
        posSelectionDlg.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
//        f.pack();
        Debug.logInfo("Brand OnePanelNonSizableContainerDlg is opened ", "module");
        posSelectionDlg.setVisible(true);
        brandPanel = null;
        Debug.logInfo("Brand OnePanelNonSizableContainerDlg is closed ", "module");
    }
    ImageIcon leftButtonIcon = createImageIcon("right.gif");

    private JPanel generateButton() {
        JPanel brandPanel = new JPanel(new GridLayout(rows, columns, 5, 5));
        brandPanel.setBackground(color);
        int dataIndex = 0;
        String html1 = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + "Y" + "</font></u>"
                + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + "This Should Be Long Enough" + "</font></b><br>";

        JButton b2 = new JButton(html1, leftButtonIcon);//key._name);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(color);
                
                panel.setPreferredSize(new Dimension(120, b2.getPreferredSize().height));
                panel.setMaximumSize(new Dimension(160, b2.getMaximumSize().height * 4));
                panel.setMinimumSize(new Dimension(85, b2.getMinimumSize().height));
                panel.setBackground(color);

                if (dataIndex < departmentList.size()) {
                    Key key = departmentList.get(dataIndex);
                    String actionKey = key._id + "," + key._name;
                    String substring = key._name;
                    String html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + substring.charAt(0) + "</font></u>"
                            + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + substring.substring(1) + "</font></b><br>";

                    JButton b1 = new JButton(html, leftButtonIcon);//key._name);

                    b1.setVerticalTextPosition(AbstractButton.CENTER);
                    b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                    buttons[i][j] = b1;
                    buttons[i][j].setActionCommand(actionKey);
                    buttons[i][j].addActionListener(this);
                    panel.add(buttons[i][j]);
                    brandPanel.add(panel);
//                    this.add(b1);
                } else {
                    JButton b1 = new JButton();//String.valueOf(i) + "x" + String.valueOf(j), leftButtonIcon);//key._name);
                    b1.setEnabled(false);

                    b1.setVerticalTextPosition(AbstractButton.CENTER);
                    b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
                    buttons[i][j] = b1; //new JButton(String.valueOf(i) + "x" + String.valueOf(j));
                    buttons[i][j].setActionCommand("button" + i + "_" + j);
                    buttons[i][j].addActionListener(this);
                    brandPanel.add(panel);
                }

                dataIndex++;
            }
        }
        return brandPanel;
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        return new ImageIcon(ButtonColumnCellEditor.getImage("cashew_curry.gif"));
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ToolBarDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
//        frame.add(new PosDepartmentPanel(null, null));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        JToolBar toolbar = new JToolBar();
        String classID = toolbar.getUIClassID();
        System.out.println(classID);
        String className = (String) UIManager.get(classID);
        System.out.println(className);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
