/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospanel;

/**
 *
 * @author administrator
 */
import javax.swing.JButton;

import javax.swing.JTextArea;
import javax.swing.JPanel;

import java.net.URL;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.base.util.Debug;

public class PosBrandPanel extends JPanel
        implements ActionListener, BaseMainPanelInterface {

    protected JTextArea textArea;
    private final JPanel cards = new JPanel(new CardLayout());
    JButton[][] buttons = null;
    private Action action;
    XuiSession session = null;

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    public PosBrandPanel(XuiSession session, Action action, ArrayList<Key> brandList, ProductDataTreeLoader productsArray) {
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
        leftPanel.panelTop.setLayout(new BorderLayout());
        leftPanel.panelTop.add(createBrandPanel());
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor);
        leftPanel.panelBottom.setLayout(new BorderLayout());
        leftPanel.panelBottom.add(createNavagationPanel());
        leftPanel.panelTop.setBackground(PosPanelHelper.BackgroundColor);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return leftPanel;
    }
    public static int ProductRows = 6;
    public static int ProductColumns = 6;

    private JPanel createBrandPanel() {

        AbstractAction actionToolButton = new AbstractAction("\u22b2ToolBar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.show(cards, e.getActionCommand());
            }
        };

        Action productSelectAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                selProdId = e.getActionCommand();
                ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand());
                action.actionPerformed(event);
            }
        };

        JPanel brandPanel = new JPanel(new GridLayout(0, 1, 1, 1));

        for (Key key : brandList) {

            ArrayList<Key> prodArray = productsArray.getProductIds(key);

            //split product array
            Map<String, ArrayList<Key>> maplist = new HashMap<String, ArrayList<Key>>();
            int maxPageButtons = ProductRows * ProductColumns;

            /*
             int i = 0;
             int j = 1;
             ArrayList<Key> keyList = new ArrayList<Key>();

             String name = key._name + "[" + String.valueOf(j) + "]";
             if (prodArray.size() < maxPageButtons) {
             name = key._name;
             }

             maplist.put(name, keyList);

             for (Key keyProd : prodArray) {
             if (i++ == maxPageButtons) {
             i = 0;
             j++;
             keyList = new ArrayList<Key>();
             maplist.put(key._name + "[" + String.valueOf(j) + "]", keyList);
             }
             keyList.add(keyProd);
             }
             */
            maplist = getSortedProductList(key, prodArray, maxPageButtons);

            //crate our panels
            for (Entry<String, ArrayList<Key>> productEntry : maplist.entrySet()) {

                ProductPosCardPanel p = new ProductPosCardPanel(session, String.valueOf(productEntry.getKey()), productEntry.getValue(), productSelectAction, ProductRows, ProductColumns);
                p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                String id = productEntry.getKey() + key._id;
                cards.add(p, id);
                //first button
                JButton button = makeNavigationButton("Back24", id,
                        "Back to previous something-or-other",
                        productEntry.getKey());
                button.addActionListener(actionToolButton);
                brandPanel.add(button);
            }
        }
        return brandPanel;
    }

    protected Map<String, ArrayList<Key>> getSortedProductList(Key brand, ArrayList<Key> productsArray, int maxButtons) {
        System.err.println("getSortedProductList: start");
        HashMap<String, ArrayList<Key>> charMapList = new HashMap<String, ArrayList<Key>>();
        String brandName = brand._key;
        //group it
        for (Key keyProd : productsArray) {
            char charStr = productsArray.get(0)._key.charAt(0);
            String str = String.valueOf(charStr);
            if (charMapList.containsKey(str)) {
                charMapList.get(str).add(keyProd);
            } else {
                ArrayList<Key> currArrayList = new ArrayList<Key>();
                currArrayList.add(keyProd);
                charMapList.put(str, currArrayList);
            }
        }

        //built it to button map
        HashMap<String, ArrayList<Key>> maplist = new HashMap<String, ArrayList<Key>>();
        ArrayList<Key> arrayList = new ArrayList<Key>();
        char charStrStart = '\0';
        char charStrEnd = charStrStart;

        for (Entry<String, ArrayList<Key>> productEntry : charMapList.entrySet()) {

            if (charStrStart == '\0') {
                charStrStart = productEntry.getKey().charAt(0);
                charStrEnd = charStrStart;
            }

            //is one alpha numeric list contins more items than button panel buttons
            ArrayList<Key> currProdList = productEntry.getValue();
            if (currProdList.size() >= maxButtons) {
                //if previous list is not empty add it to panel first
                if (arrayList.isEmpty() == false) {
                    maplist.put(brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]", arrayList);
                    arrayList = new ArrayList<Key>();
                    charStrStart = '\0';
                }

                int count = 0;
                ArrayList<Key> tmpList = new ArrayList<Key>();
                for (Key keyProd : productsArray) {
                    if (count++ < maxButtons) {
                        tmpList.add(keyProd);
                    } else {
                        maplist.put(brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrStart).toUpperCase() + "]", tmpList);
                        tmpList = new ArrayList<Key>();
                        count = 0;
                    }
                }
                maplist.put(brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrStart).toUpperCase() + "]", tmpList);
                charStrStart = '\0';
            } else {
                int totalSize = currProdList.size() + arrayList.size();
                if (totalSize > maxButtons) {
                    maplist.put(brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]", arrayList);
                    charStrStart = productEntry.getKey().charAt(0);
                    charStrEnd = charStrStart;

                    arrayList = new ArrayList<Key>();
                    arrayList.addAll(productEntry.getValue());
                } else {
                    charStrEnd = productEntry.getKey().charAt(0);
                    arrayList.addAll(productEntry.getValue());
                }
            }
        }

        if (arrayList.isEmpty() == false) {
            maplist.put(brandName + "[" + String.valueOf(charStrStart).toUpperCase() + "-" + String.valueOf(charStrEnd).toUpperCase() + "]", arrayList);
        }
        System.err.println("getSortedProductList: end");
        return maplist;
    }

    protected String getButtonText(String name) {
        String html = "<html><left><b><u><font size=\"3\" face=\"Georgia, Arial\" color=\"maroon\"> " + name.charAt(0) + "</font></u>"
                + "<font size=\"2\" face=\"Georgia, Arial\" color=\"black\"> " + name.substring(1) + "</font></b><br>";
        return html;
    }

    public JPanel getCurrentCard() {
        JPanel card = null;
        System.out.println("getCurrentCard");
                       Debug.logInfo("getCurrentCard", "module");
        for (Component comp : cards.getComponents()) {
            if (comp.isVisible() == true) {
                card = (JPanel) comp;
                Debug.logInfo(card.getName(),"module");
            }
        }
        System.out.println();

        return card;
    }

    private JPanel createNavagationPanel() {

        JPanel control = new JPanel(new GridLayout(0, 1, 1, 1));
        JPanel ChildControl = new JPanel(new GridLayout(1, 2));

        ChildControl.add(new JButton(new AbstractAction(getButtonText("Prev")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                            getCurrentCard();
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.previous(cards);
    
            }
        }));

        ChildControl.add(new JButton(new AbstractAction(getButtonText("Next")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                            getCurrentCard();
                CardLayout cl = (CardLayout) cards.getLayout();
                cl.next(cards);
                getCurrentCard();
            }
        }));
        control.add(ChildControl);

        JButton button = new JButton(new AbstractAction(getButtonText("Departments")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionEvent event = new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, PosPanelHelper.ShowDepartment);
                System.out.println("Departments");
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
               Debug.logInfo("Home", "module");
                action.actionPerformed(event);
            }
        });

        button.setPreferredSize(new Dimension(100, button.getPreferredSize().height));
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
        //Look for the image.
        String imgLocation = "images/"
                + imageName
                + ".gif";
        URL imageURL = PosBrandPanel.class
                .getResource(imgLocation);

        //Create and initialize the button.
        JButton button = new JButton(getButtonText(altText));

        button.setActionCommand(actionCommand);

        button.setToolTipText(toolTipText);

        button.addActionListener(
                this);
//        button.setPreferredSize(new Dimension(200, 20));
        button.setPreferredSize(
                new Dimension(100, button.getPreferredSize().height));
        button.setMaximumSize(
                new Dimension(140, button.getMaximumSize().height * 2));
        button.setMinimumSize(
                new Dimension(65, button.getMinimumSize().height));

        if (imageURL
                != null) {                      //image found
//            button.setIcon(new ImageIcon(imageURL, altText));
//            button.setText(altText);
        } else {                                     //no image found
//            button.setText(altText);
            System.err.println("Resource not found: "
                    + imgLocation);
        }
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
    protected ArrayList<Key> brandList = null;

    public void setBrandList(ArrayList<Key> brandList) {
        this.brandList = brandList;
    }
    protected String selProdId = null;

    public void loadItem() {
    }
}
