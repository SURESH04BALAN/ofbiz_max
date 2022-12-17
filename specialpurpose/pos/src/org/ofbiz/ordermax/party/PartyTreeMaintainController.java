/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import mvc.controller.LoadAccountWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ProductRootTreeNode;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;

/**
 *
 * @author siranjeev
 */
public class PartyTreeMaintainController extends PartyMaintainControllerNew {

    public static final String module = PartyTreeMaintainController.class.getName();

    static public PartyTreeMaintainController runController(ControllerOptions options) {

        PartyTreeMaintainController controller = new PartyTreeMaintainController(options);
        if (null == ControllerOptions.getDesktopPane()) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(PartyTreeMaintainController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelSizeableInternalFrame(PartyTreeMaintainController.module, ControllerOptions.getDesktopPane());
        }
        return controller;
    }

    protected PartyTreeMaintainController(ControllerOptions options) {
        super(options);
        this.options = options;
        this.partyId = options.getPartyId();
        if (options.getRoleType() != null) {
            this.roleTypeId = options.getRoleType().getroleTypeId();
        }

        if (options.contains("IsMaintain")) {
            this.isMaintain = true;
        }
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 10;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    @Override
    public void loadScreen(ContainerPanelInterface f) {

        setCaption("Party Detail");

        createTreePanel();
        PartyTreeList partyTreeList = (PartyTreeList) treePanel.getTreeDataList();//new ProductListArray(ControllerOptions.getSession());
        partyTreeList.loadList(roleTypeId);   
        super.loadScreen(f);
        f.getPanelSelecton().setLayout(new BorderLayout());
        f.getPanelSelecton().add(treePanel.getContainerPanel(), BorderLayout.CENTER);
             
    }


    @Override
    public void saveParty() {
        try {
//                    account = partyGroupPanel.getAccount();
           super.saveParty();
            loadTree();
            
        } catch (Exception ex) {
            Logger.getLogger(PartyTreeMaintainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void productTreeMouseClicked(java.awt.event.MouseEvent e) {
        JTree jTree = treePanel.getTree();
        int selRow = treePanel.getTree().getRowForLocation(e.getX(), e.getY());
        TreePath tp = treePanel.getTree().getPathForLocation(e.getX(), e.getY());

//        tp = jTree.getSelectionPath();
        if (tp != null) {
            TreeNode node = (TreeNode) tp.getLastPathComponent();
            Debug.logInfo(node.toString(), module);
            if (node != null) {
                node.tp = tp;
                Debug.logInfo(node.getNodeName(), module);
                Debug.logError("Get Node Name: " + node.getNodeName(), module);
                if (node.getNodeName().equals(PartyGroupTreeNode.PartyGroupNodeName)) {
                    if (partyGroupPanel.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {

                            try {
//sur                                partyGroupPanel.saveItem();
                            } catch (Exception e2) {
                                Debug.logError(e2, module);
                            }
                        }
                    }

                    comboPostalChanged(PartyGroupTreeNode.PartyGroupNodeName);
//sur                    partyGroupPanel.setItem(node.getGenericValue());
                    String partyId = node._id;

                    try {
                        if (UtilValidate.isNotEmpty(partyId)) {
                            loadParty(partyId);

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PartyGroupPanel.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (node.getNodeName().equals(PersonTreeNode.PersonNodeName)) {
                    if (partyGroupPanel.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            try {
//sur                                partyGroupPanel.saveItem();
                            } catch (Exception ex) {
                                Debug.logError(ex, module);
                            }

                        }
                    }
//                    brandPanel.setItem(node);
                    comboPostalChanged(PersonTreeNode.PersonNodeName);
//sur                    partyGroupPanel.setItem(node.getGenericValue());
//sur                    partyGroupPanel.setItem(node.getGenericValue());
                    String partyId = node._id;

                    try {
                        if (UtilValidate.isNotEmpty(partyId)) {
                            loadParty(partyId);

                        }/* else {
                         account = LoadAccountWorker.createNewEmptyAccount(ControllerOptions.getSession());
                         }
                         partyGroupPanel.setAccount(account);
                         partyGroupPanel.setDialogFields();
                         partyGroupPanel.setIsModified(false);

                         } catch (GenericEntityException ex) {
                         Logger.getLogger(PartyGroupPanel.class.getName()).log(Level.SEVERE, null, ex);*/

                    } catch (Exception ex) {
                        Logger.getLogger(PartyGroupPanel.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                    partyGroupPanel.setIsModified(false);
                } else if (node.getNodeName().equals(RoleTypeTreeNode.RoleTypeRootName)) {
                    if (partyGroupPanel.isModified()) {
                        String message = "Do you want to save?";
                        int reply = OrderMaxOptionPane.showConfirmDialog(null, message, "Modified", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {

                            try {
//sur                                partyGroupPanel.saveItem();
                            } catch (Exception ex1) {
                                Debug.logError(ex1, module);
                            }

                        }
                    }

                    comboPostalChanged(RoleTypeTreeNode.RoleTypeRootName);
//sur                    partyGroupPanel.setItem(node.getGenericValue());
                    partyGroupPanel.setIsModified(false);
                } else if (node.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
                }
                node.tp = null;
            }
        }

    }

    public void comboPostalChanged(String desc) {
        partyGroupPanel = partyGroupPanel;
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
                        Logger.getLogger(OrderMaxMainForm.class
                                .getName()).log(Level.SEVERE, null, ex);
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
