/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.utility.PersonDialogForm;

/**
 *
 * @author siranjeev
 */
public class PartyMainScreen extends BaseMainScreen {

    public static final String module = PartyMainScreen.class.getName();
//    public BaseMainPanelInterface panel = null;
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    PartyGroupDialogForm partyGroupPanel = null;
    PersonDialogForm personPanel = null;
    RoleTypePanelExt roleTypePanel = null;
    protected JPanel productCardPanel = null;
    String roleTypeId = null;
    String visibleCardName = null;

    public PartyMainScreen(String roleTypeId, XuiSession sess) {
        super( sess);
        this.roleTypeId = roleTypeId;
    }
    
    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 150;
        if(ControllerOptions.getDesktopPane()!=null){
        frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    public void loadScreen(ContainerPanelInterface f) {

        setCaption("Party Detail");
  
        createTreePanel();
        PartyTreeList partyTreeList = (PartyTreeList) treePanel.getTreeDataList();//new ProductListArray(ControllerOptions.getSession());
        partyTreeList.loadList(roleTypeId);

        productCardPanel = new JPanel(new CardLayout());
        partyGroupPanel = new PartyGroupDialogForm(ControllerOptions.getSession());
        personPanel = new PersonDialogForm(ControllerOptions.getSession());
        roleTypePanel = new RoleTypePanelExt();

        productCardPanel.add(partyGroupPanel, PartyGroupTreeNode.PartyGroupNodeName);
        productCardPanel.add(personPanel, PersonTreeNode.PersonNodeName);
        productCardPanel.add(roleTypePanel, RoleTypeTreeNode.RoleTypeRootName);


        PartyButtonPanel buttonPanel = new PartyButtonPanel();

        OrderMaxUtility.addAPanelToPanel(productCardPanel, f.getPanelDetail());
        f.getPanelSelecton().setLayout(new BorderLayout());
        f.getPanelSelecton().add(treePanel.getContainerPanel(), BorderLayout.CENTER);
//        OrderMaxUtility.addAPanelToPanel(treePanel.getContainerPanel(), );
        OrderMaxUtility.addAPanelToPanel(buttonPanel, f.getPanelButton());

        comboPostalChanged(PartyGroupTreeNode.PartyGroupNodeName);

        buttonPanel.getBtnSave().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    baseMainPanelInterface.saveItem();
                    baseMainPanelInterface.refreshScreen();
                } catch (Exception ex) {
                    Logger.getLogger(PartyMainScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                loadTree();
            }
        });
/*
        buttonPanel.getBtnLoad().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreeNode prodNode = treePanel.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNode != null) {
                    try {
                        baseMainPanelInterface.setItem(prodNode);
                    } catch (Exception ex) {
                        Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                

            }
        });
        */
/*
        buttonPanel.getBtnNewRole().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (baseMainPanelInterface.isModified()) {
                    try {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            try {
                                baseMainPanelInterface.saveItem();
                            } catch (Exception e3) {
                                Debug.logError(e3, module);
                            }
                        } else if (reply == JOptionPane.CANCEL_OPTION) {
                            return;
                        }
                    } catch (HeadlessException ex) {
                        Logger.getLogger(PartyMainScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                comboPostalChanged(RoleTypeTreeNode.RoleTypeRootName);
                baseMainPanelInterface.newItem();
            }
        });*/

        buttonPanel.getBtnNewPartyGroup().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (baseMainPanelInterface.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            baseMainPanelInterface.saveItem();
                        } catch (Exception e1) {
                            Debug.logError(e1, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                comboPostalChanged(PartyGroupTreeNode.PartyGroupNodeName);
             
                baseMainPanelInterface.newItem();
                try {
                    baseMainPanelInterface.addItem(treePanel.getSelectedTreeNode(RoleTypeTreeNode.RoleTypeRootName)._id);
                } catch (Exception ex) {
                    Logger.getLogger(PartyMainScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
/*
        buttonPanel.getBtnNewPerson().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (baseMainPanelInterface.isModified()) {
                    String message = "Do you want to save?";
                    int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        try {
                            baseMainPanelInterface.saveItem();
                        } catch (Exception e2) {
                            Debug.logError(e2, module);
                        }

                    } else if (reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }

                comboPostalChanged(PersonTreeNode.PersonNodeName);
                baseMainPanelInterface.newItem();
            }
        });*/


//        f.setSize(1200, 700);
//        f.setSize(1000, 700);
//        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
//        f.setVisible(true);
    }

    public void addItem(String id) throws Exception {
        baseMainPanelInterface.addItem(id);
    }

    public void refreshScreen() {
        baseMainPanelInterface.refreshScreen();
    }

    @Override
    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
//        panel.addItem(id, price, qty);
    }

    @Override
    protected void productTreeMouseClicked(java.awt.event.MouseEvent evt) {
        JTree jTree = treePanel.getTree();
        TreePath tp = null;
        tp = jTree.getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            if (node != null) {
                node.tp = tp;
                //Debug.logInfo(node.getNodeName(), module);
                if (node.getNodeName().equals(PartyGroupTreeNode.PartyGroupNodeName)) {
                    if (baseMainPanelInterface.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {

                            try {
                                baseMainPanelInterface.saveItem();
                            } catch (Exception e2) {
                                Debug.logError(e2, module);
                            }

                        }
                    }

                    comboPostalChanged(PartyGroupTreeNode.PartyGroupNodeName);
                    baseMainPanelInterface.setItem(node.getGenericValue());
                    baseMainPanelInterface.setIsModified(false);
                } else if (node.getNodeName().equals(PersonTreeNode.PersonNodeName)) {
                    if (baseMainPanelInterface.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            try {
                                baseMainPanelInterface.saveItem();
                            } catch (Exception ex) {
                                Debug.logError(ex, module);
                            }

                        }
                    }
//                    brandPanel.setItem(node);
                    comboPostalChanged(PersonTreeNode.PersonNodeName);
                    baseMainPanelInterface.setItem(node.getGenericValue());
                    baseMainPanelInterface.setIsModified(false);
                } else if (node.getNodeName().equals(RoleTypeTreeNode.RoleTypeRootName)) {
                    if (baseMainPanelInterface.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {

                            try {
                                baseMainPanelInterface.saveItem();
                            } catch (Exception ex1) {
                                Debug.logError(ex1, module);
                            }

                        }
                    }

                    comboPostalChanged(RoleTypeTreeNode.RoleTypeRootName);
                    baseMainPanelInterface.setItem(node.getGenericValue());
                    baseMainPanelInterface.setIsModified(false);
                } else if (node.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
                }
                node.tp = null;
            }
        }

    }

    public void comboPostalChanged(String desc) {
        if (productCardPanel != null) {
            CardLayout cl = (CardLayout) (productCardPanel.getLayout());
            cl.show(productCardPanel, desc);
            visibleCardName = desc;
            if (visibleCardName.equals(PartyGroupTreeNode.PartyGroupNodeName)) {
                baseMainPanelInterface = partyGroupPanel;
            } else if (visibleCardName.equals(PersonTreeNode.PersonNodeName)) {
                baseMainPanelInterface = personPanel;
            } else if (visibleCardName.equals(RoleTypeTreeNode.RoleTypeRootName)) {
                baseMainPanelInterface = roleTypePanel;
            }
        }
    }

    void loadTree() {

        TreePath path = treePanel.getTree().getSelectionPath();
        treePanel.loadTree();
        PartyTreeList m_productsArray = (PartyTreeList) treePanel.getTreeDataList();//.getProductListArray();//new ProductListArray(ControllerOptions.getSession());

        treePanel.getTree().scrollPathToVisible(path);
    }

    @Override
    protected void createTreePanel() {

        final PartySelectionTreePanel treePanel1 = new PartySelectionTreePanel(roleTypeId, ControllerOptions.getSession());
        treePanel = treePanel1;

        treePanel1.getBtnAddSelected().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreeNode prodNode = treePanel1.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNode != null) {
                    try {
                        refreshScreen();
                    } catch (Exception ex) {
                        Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTreeMouseClicked(evt);
            }
        });

    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
