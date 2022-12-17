/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.pospanel;

/**
 *
 * @author administrator
 */
import javax.swing.JButton;

import javax.swing.JTextArea;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.String;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.Key;

public class PosBrandPanel extends JPanel
        implements ActionListener, BaseMainPanelInterface {

    protected JTextArea textArea;
    private final JPanel cards = new JPanel(new CardLayout());
//    JButton[][] buttons = null;
    private Action action;
    XuiSession session = null;
    public ArrayList<ProductPosCardPanel> productCardList = new ArrayList<ProductPosCardPanel>();

    public static int ProductRows = 6;
    public static int ProductColumns = 6;

    public PosBrandPanel(XuiSession session, Action action, List<TreeNode> brandList, ProductDataTreeLoader productsArray) {
        super(new BorderLayout());
        this.session = session;
        this.action = action;
        this.productsArray = productsArray;
        this.brandList = brandList;
        this.setBackground(PosPanelHelper.BackgroundColor);

        add(createMenuPanel(), BorderLayout.WEST);
        add(cards, BorderLayout.CENTER);
    }

    private JPanel createMenuPanel() {
        PosLeftMenuPanel leftPanel = new PosLeftMenuPanel();
        leftPanel.setPreferredSize(new Dimension(150, 230));
        leftPanel.panelTop.setPreferredSize(new Dimension(150, 150));
        leftPanel.panelTop.setLayout(new GridLayout(1, 1));
        leftPanel.panelTop.add(new javax.swing.JScrollPane(createBrandPanel()));
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor);
        leftPanel.panelBottom.setLayout(new BorderLayout());
        leftPanel.panelBottom.add(createNavagationPanel());
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return leftPanel;
    }

    private JPanel createBrandPanel() {

        AbstractAction actionToolButton = new AbstractAction("\u22b2ToolBar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, e.getActionCommand());
            }
        };

        final Action productSelectAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                selProdId = e.getActionCommand();
                ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
                action.actionPerformed(event);
            }
        };

        JPanel brandPanel = new JPanel(/*new GridLayout(0, 1, 1, 1),*/);
        GridBagLayout layout = new GridBagLayout();
        brandPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        Debug.logError(selProdId, selProdId);
//        javax.swing.JScrollPane scrollPaneTop = new javax.swing.JScrollPane(new GridLayout(0, 1, 1, 1));
        int buttonsCount = 0;
        Debug.logInfo("brandList size: " + brandList.size(), selProdId);
        for (TreeNode key : brandList) {

//            ArrayList<TreeNode> prodArray = productsArray.loadProductList(key._id);
            //ArrayList<Key> prodKeyArray = productsArray.getNonVirtualProductIds((Key) key);
            ArrayList<TreeNode> prodKeyArray = productsArray.loadProductCategoryMemebers((Key) key);
            ArrayList<TreeNode> prodArray = new ArrayList<TreeNode>();
            for (Key key1 : prodKeyArray) {
                prodArray.add((TreeNode) key1);
            }

            //split product array
            Map<String, ArrayList<TreeNode>> maplist = new TreeMap<String, ArrayList<TreeNode>>();
            int maxPageButtons = ProductRows * ProductColumns;

            /*          int i = 0;
             int j = 1;
             ArrayList<TreeNode> keyList = new ArrayList<TreeNode>();
    
             String name = key._name + "[" + String.valueOf(j) + "]";
             if (prodArray.size() < maxPageButtons) {
             name = key._name;
             }

             maplist.put(name, keyList);

             for (TreeNode keyProd : prodArray) {
             if (i++ == maxPageButtons) {
             i = 0;
             j++;
             keyList = new ArrayList<TreeNode>();
             maplist.put(key._name + "[" + String.valueOf(j) + "]", keyList);
             }
             keyList.add(keyProd);
             }
             */
            maplist = getSortedProductList(key, prodArray, maxPageButtons);

            //get image details
            GenericValue genVal = PosProductHelper.getProductCategory(key._id, session.getDelegator());
            String fileName = null;
            ImageIcon newIcon = null;

            if (genVal != null) {
                fileName = genVal.getString("categoryImageUrl");//
            }

            //crate our panels
            for (Entry<String, ArrayList<TreeNode>> productEntry : maplist.entrySet()) {

                ProductPosCardPanel p = new ProductPosCardPanel(session, productSelectAction, String.valueOf(productEntry.getKey()), productEntry.getValue(), ProductRows, ProductColumns);
                p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                String id = productEntry.getKey() + key._id;
                cards.add(p, id);
                //first button
                JButton button = makeNavigationButton("Back24", id,
                        "Back to previous something-or-other",
                        productEntry.getKey());
                button.addActionListener(actionToolButton);
                if (fileName != null) {

                    Debug.logInfo("is data file name" + fileName, "module");
                    ImageIcon icon = BaseHelper.getImage(fileName);

//                    ImageIcon icon = BaseHelper.getImage(field);
                    if (icon != null) {
                        Image img = icon.getImage();
                        Image newimg = img.getScaledInstance(100, 64, java.awt.Image.SCALE_SMOOTH);
                        newIcon = new ImageIcon(newimg);
                    }

                    button.setVerticalTextPosition(AbstractButton.BOTTOM);
                    button.setHorizontalTextPosition(AbstractButton.CENTER); //aka LEFT, for left-to-right locales                    
                    button.setIcon(newIcon);
//                    button.setText(null);

                }
                gbc.gridx = 0;
                gbc.gridy = buttonsCount++;
                gbc.anchor = GridBagConstraints.NORTHWEST;

                brandPanel.add(button, gbc);
            }
        }
        return brandPanel;
    }

    protected Map<String, ArrayList<TreeNode>> getSortedProductList(TreeNode brand, ArrayList<TreeNode> productsArray, int maxButtons) {
        System.err.println("getSortedProductList: start");
        TreeMap<String, ArrayList<TreeNode>> charMapList = new TreeMap<String, ArrayList<TreeNode>>();
        String brandName = brand._name;
        int id = 0;
        //group it
        for (TreeNode keyProd : productsArray) {
            if (UtilValidate.isNotEmpty(keyProd._name)) {
                char charStr = keyProd._name.charAt(0);
                System.err.println("getSortedProductList keyProd._name : " + keyProd._name);
                String str = String.valueOf(charStr);
                if (charMapList.containsKey(str)) {
                    charMapList.get(str).add(keyProd);
                    System.err.println("getSortedProductList adding string : " + str);
                } else {
                    ArrayList<TreeNode> currArrayList = new ArrayList<TreeNode>();
                    currArrayList.add(keyProd);
                    charMapList.put(str, currArrayList);
                    System.err.println("getSortedProductList new string : " + str);
                }
            }
        }

        //built it to button map
        TreeMap<String, ArrayList<TreeNode>> maplist = new TreeMap<String, ArrayList<TreeNode>>();
        ArrayList<TreeNode> arrayList = new ArrayList<TreeNode>();
        char charStrStart = '\0';
        char charStrEnd = charStrStart;

        for (Entry<String, ArrayList<TreeNode>> productEnt : charMapList.entrySet()) {
            /*arrayList = new ArrayList<TreeNode>();
             charStrStart = productEnt.getKey().charAt(0);
             charStrEnd = charStrStart;
             ArrayList<TreeNode> currProdList = productEnt.getValue();
             if (currProdList.size() >= maxButtons) {
              
             //if previous list is not empty add it to panel first
             if (arrayList.isEmpty() == false) {
             //                    key = brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]";
             //                    maplist.put(key, arrayList);
             addUniqueKeyArray(brandName, charStrStart, charStrEnd, arrayList, maplist);
             arrayList = new ArrayList<TreeNode>();
             charStrStart = productEnt.getKey().charAt(0);
             //                    charStrStart = '\0';
             }

             int count = 0;
             ArrayList<TreeNode> tmpList = new ArrayList<TreeNode>();
             for (TreeNode keyProd : currProdList) {
             if (count++ < maxButtons) {
             tmpList.add(keyProd);
             charStrEnd = productEnt.getKey().charAt(0);
             } else {
             //        key = brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]";
             //      maplist.put(key, tmpList);
             addUniqueKeyArray(brandName, charStrStart, charStrEnd, tmpList, maplist);
             tmpList = new ArrayList<TreeNode>();
             count = 0;
             }
             }
             //                key = brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]";
             //                maplist.put(key, tmpList);                
             addUniqueKeyArray(brandName, charStrStart, charStrEnd, tmpList, maplist);
             charStrStart = '\0';
             } else {
             arrayList.addAll(productEnt.getValue());
             addUniqueKeyArray(brandName, charStrStart, charStrEnd, arrayList, maplist);
             }
             continue;*/

            if (charStrStart == '\0') {
                charStrStart = productEnt.getKey().charAt(0);
                charStrEnd = charStrStart;
            }
            System.err.println("charStrStart: " + charStrStart);
            System.err.println("charStrEnd: " + charStrEnd);
            //is one alpha numeric list contins more items than button panel buttons
            ArrayList<TreeNode> currProdList = productEnt.getValue();

            if (currProdList.size() >= maxButtons) {
                //if previous list is not empty add it to panel first
                if (arrayList.isEmpty() == false) {
                    //                    key = brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]";
                    //                    maplist.put(key, arrayList);
                    addUniqueKeyArray(brandName, charStrStart, charStrEnd, arrayList, maplist);
                    arrayList = new ArrayList<TreeNode>();
                    charStrStart = productEnt.getKey().charAt(0);
                    //                    charStrStart = '\0';
                }

                int count = 0;
                ArrayList<TreeNode> tmpList = new ArrayList<TreeNode>();
                for (TreeNode keyProd : currProdList) {
                    if (count++ < maxButtons) {
                        tmpList.add(keyProd);
                        charStrEnd = productEnt.getKey().charAt(0);
                    } else {
                        //        key = brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]";
                        //      maplist.put(key, tmpList);
                        addUniqueKeyArray(brandName, charStrStart, charStrEnd, tmpList, maplist);
                        tmpList = new ArrayList<TreeNode>();
                        count = 0;
                    }
                }
                //                key = brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]";
                //                maplist.put(key, tmpList);                
                addUniqueKeyArray(brandName, charStrStart, charStrEnd, tmpList, maplist);
                charStrStart = '\0';
            } else {
                int totalSize = currProdList.size() + arrayList.size();
                if (totalSize > maxButtons) {
                    //                    key = brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]";
                    //                        maplist.put(key, tmpList);                    
                    //                    maplist.put(key, arrayList);
                    addUniqueKeyArray(brandName, charStrStart, charStrEnd, arrayList, maplist);
                    charStrStart = productEnt.getKey().charAt(0);
                    charStrEnd = charStrStart;

                    arrayList = new ArrayList<TreeNode>();
                    arrayList.addAll(currProdList);
                } else {
                    arrayList.addAll(currProdList);
                    charStrEnd = productEnt.getKey().charAt(0);
                }
            }

        }

        if (arrayList.isEmpty() == false) {
            // maplist.put(brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]", arrayList);
            addUniqueKeyArray(brandName, charStrStart, charStrEnd, arrayList, maplist);
        }
        System.err.println("getSortedProductList: end");
        return maplist;
    }
    int i = 0;

    void addUniqueKeyArray(String brandName, char start, char end, ArrayList<TreeNode> arrayList, TreeMap<String, ArrayList<TreeNode>> maplist) {
        String key = brandName + "[" + String.valueOf(start).toUpperCase() + "-" + String.valueOf(end).toUpperCase() + "]";
        System.err.println("addUniqueKeyArray: new key" + key);
        if (maplist.containsKey(key)) {
            key = key + " - " + i++;
            maplist.put(key, arrayList);
        } else {
            maplist.put(key, arrayList);
        }
    }

    protected String getButtonText(String name) {
        String html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + name.charAt(0) + "</font></u>"
                + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + name.substring(1) + "</font></b><br>";
        return html;
    }

    private JPanel createNavagationPanel() {

        JPanel control = new JPanel(new GridLayout(0, 1, 1, 1));
        JPanel ChildControl = new JPanel(new GridLayout(1, 2));

        ChildControl.add(new JButton(new AbstractAction(getButtonText("Prev")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.previous(cards);
            }
        }));

        ChildControl.add(new JButton(new AbstractAction(getButtonText("Next")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.next(cards);
            }
        }));
        control.add(ChildControl);

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
        control.add(button);

        button = new JButton(new AbstractAction(getButtonText("Home")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, PosPanelHelper.ShowHome);
                action.actionPerformed(event);
            }
        });

        button.setPreferredSize(new Dimension(130, button.getPreferredSize().height));
        button.setMaximumSize(new Dimension(140, button.getMaximumSize().height * 2));
        button.setMinimumSize(new Dimension(65, button.getMinimumSize().height));
        control.add(button);

        return control;
        /*
         //first button
         JButton button = makeNavigationButton("Back24", PREVIOUS,
         "Back to previous something-or-other",
         "0");
         button.addActionListener(new AbstractAction("Next\u22b3") {
         @Override
         public void actionPerformed(ActionEvent e) {
         CardLayout cl = (CardLayout) cards.getLayout();
         cl.next(cards);
         }
         });
         /*
         //second button
         button = makeNavigationButton("Up24", UP,
         "Up to something-or-other",
         "Up");
         navagationPanel.add(button);
         //        button.addActionListener(action);

         //third button
         button = makeNavigationButton("Forward24", NEXT,
         "Forward to something-or-other",
         "Next");
         navagationPanel.add(button);

         return navagationPanel;
         }

         protected void addButtons(JToolBar toolBar) {
         JButton button = null;

         //first button
         button = makeNavigationButton("Back24", PREVIOUS,
         "Back to previous something-or-other",
         "0");
         toolBar.add(button);
         //        button.addActionListener(action);

         //second button
         button = makeNavigationButton("Up24", UP,
         "Up to something-or-other",
         "Up");
         toolBar.add(button);
         //        button.addActionListener(action);

         //third button
         button = makeNavigationButton("Forward24", NEXT,
         "Forward to something-or-other",
         "Next");
         toolBar.add(button);
         //                button.addActionListener(action);
         */
    }

    protected JButton makeNavigationButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {

        //Create and initialize the button.
        JButton button = new JButton(getButtonText(altText));
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
//        button.setPreferredSize(new Dimension(200, 20));
        button.setPreferredSize(new Dimension(120, 80));
        button.setMaximumSize(new Dimension(140, button.getMaximumSize().height * 2));
        button.setMinimumSize(new Dimension(65, button.getMinimumSize().height));

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
    protected List<TreeNode> brandList = null;

    public void setBrandList(List<TreeNode> brandList) {
        this.brandList = brandList;
    }
    protected String selProdId = null;

    public void loadItem() {
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

}
