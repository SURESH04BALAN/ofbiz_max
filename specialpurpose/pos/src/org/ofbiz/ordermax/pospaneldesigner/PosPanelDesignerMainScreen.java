/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.xml.parsers.ParserConfigurationException;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.product.productloader.CatalogCategoryDataTree;
import org.ofbiz.ordermax.product.catalog.CatalogSelectionPanel;
import org.ofbiz.ordermax.product.tree.ProductTreeModel;
import org.ofbiz.pos.screen.PosScreen;
import org.xml.sax.SAXException;

/**
 *
 * @author siranjeev
 */
public class PosPanelDesignerMainScreen extends BaseMainScreen {

    public static final String module = PosPanelDesignerMainScreen.class.getName();
    public PosDesignPanel panel = null;
    String caption = "";
    // public PosScreen pos = null;
    CatalogSelectionPanel catalogTreeSelPanel = null;

    static public PosPanelDesignerMainScreen runController(ControllerOptions options) {

        PosPanelDesignerMainScreen controller = new PosPanelDesignerMainScreen(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadThreePanelDialog(null, true);
        } else {
            controller.loadThreePanelInternalFrame(PosPanelDesignerMainScreen.module, ControllerOptions.getDesktopPane());
        }
        return controller;
    }
    ControllerOptions options = null;

    public PosPanelDesignerMainScreen(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
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
/*
    @Override
    protected void setSizeAndLocation(JDialog dlg) {
        int y = 10;
        int x = 10;
        dlg.setSize(1000 - 2 * x, 750 - 2 * y);
        dlg.setLocation(x, y);
    }
*/
    @Override
    public void loadScreen(ContainerPanelInterface f) {
        panel = new PosDesignPanel();//(ControllerOptions.getSession());
        try {
            panel.createGUI();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PosPanelDesignerMainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PosPanelDesignerMainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PosPanelDesignerMainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
//        f = new ThreePanelContainerFrame(module);
//        f.setTitle("Purchase Order");
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createTreePanel();
        catalogTreeSelPanel = new CatalogSelectionPanel(options);
        catalogTreeSelPanel.hideButtons(false);
        f.getPanelSelecton().setLayout(new BorderLayout());
        f.getPanelSelecton().add(BorderLayout.CENTER, catalogTreeSelPanel);

        catalogTreeSelPanel.panelTree.setLayout(new BorderLayout());
        catalogTreeSelPanel.panelTree.add(BorderLayout.CENTER, treePanel.getContainerPanel());
        catalogTreeSelPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (catalogTreeSelPanel.getSelectedCategory() != null) {
//                    String categoryId = catalogTreeSelPanel.getSelectedCategory();
                    reloadTreePanel(catalogTreeSelPanel.getSelectedCategory());
                } else {

                }
            }
        });
        catalogTreeSelPanel.setSelectedCatalog(ControllerOptions.getSession().getProdCatalog().getprodCatalogId());

//        panel.newItem();
//        OrderHeaderMax orderMax = OrderMaxUtility.loadOrderFromPersistance(orderId, delegator);
//        panel.setOrderHeader(orderMax);
//        panel.loadItemEditDataModel();
//        panel.addChangeListener(this);
//        OrderButtonPanel buttonPanel = new OrderButtonPanel();
//        OrderMaxUtility.addAPanelToPanel(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
//        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), f.getPanelSelecton());
//        f.getPanelButton().setVisible(false);
  /*      OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
         buttonPanel.getCancelButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         cancelButtonPressed();
         }
         });

         buttonPanel.getOkButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         okButtonPressed();
         }
         });

  
         buttonPanel.getBtnLoad().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         //                panel.loadItem();
         }
         });

         buttonPanel.getPosButtonButton().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         String productId = ManagerEvents.showProductLookup(pos);
         if (productId != null) {
         try {
         addItem(productId);
         } catch (Exception ex) {
         Logger.getLogger(PurchaseOrderMainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         }
         });
         */

        //Sur      panel.setProductListArray((ProductDataTreeLoader) treePanel.getTreeDataList());
//        setInitialFocusField(panel.getPartyTextField());
//        f.setSize(1200, 700);
//        f.setSize(1000, 700);
//        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
//        f.setVisible(true);
    }
//    ProductSelectionTreePanel treePanel = null;

    protected void reloadTreePanel(ProductCategory tmpCategoryId/*, final ContainerPanelInterface f*/) {
        //        treePanel 
        if (treePanel != null) {
            //                  createTreePanel();
            ProductDataTreeLoader productCategoryDataTree = new CatalogCategoryDataTree(tmpCategoryId);
            productCategoryDataTree.setProductLoaded(false);
            productCategoryDataTree.setLazyLoad(true);
            productCategoryDataTree.loadList();

            ProductTreeModel productTreeModel = new ProductTreeModel(productCategoryDataTree.getRootNode());
            treePanel.getTree().setModel(productTreeModel);

            ((ProductSelectionTreePanel) treePanel).setProductListArray(productCategoryDataTree);

        }
    }

    @Override
    protected void createTreePanel() {

        final ProductSelectionTreePanel treePanel1 = new ProductSelectionTreePanel(ControllerOptions.getSession());
        treePanel = treePanel1;

        /*treePanel1.getBtnAddSelected().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreeNode prodNode = treePanel1.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNode != null) {
                    try {
                        addItem(prodNode._id, prodNode._name);
                    } catch (Exception ex) {
                        Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });*/

        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TreeNode prodNode = treePanel1.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNode != null) {
                    try {
                        addItem(prodNode._id, prodNode._name);
                    } catch (Exception ex) {
                        Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

    }

    @Override
    public void addItem(String id) throws Exception {
    }

    @Override
    public void refreshScreen() {
//        panel.refreshScreen();
    }

    public void addItem(String id, String name) throws Exception {
        panel.setSelectionData(id, name);
    }
    boolean isNew = false;
    boolean isModified = false;
    String orderStatus;
    String orderId;
    String partyId = "";

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(OrderMaxUtility.ITEM_NEW)) {
            isNew = true;
            isModified = false;
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_SAVED)) {
            isNew = false;
            isModified = false;
            orderId = (String) event.getNewValue();
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_MODIFIED)) {
            isModified = true;
        } else if (event.getPropertyName().equals(OrderMaxUtility.ITEM_STATUS_CHANGED)) {
            orderStatus = (String) event.getNewValue();
        } else if (event.getPropertyName().equals(OrderMaxUtility.PARTY_CHANGED)) {
            partyId = (String) event.getNewValue();
            treePanel.setPartyId(partyId);
        }

        caption = "Purchase Order - " + partyId + "[";
        if (isNew) {
            caption = caption.concat(" New ");
        } else {
            caption = caption.concat("Order Id: " + orderId);
        }

        if (orderStatus != null) {
            caption = caption.concat(", Order Status: " + orderStatus);
        }

        caption = caption.concat(" ]");

        if (isModified) {
            caption = caption.concat(" - Modified ");
        }

        setCaption(caption);
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
