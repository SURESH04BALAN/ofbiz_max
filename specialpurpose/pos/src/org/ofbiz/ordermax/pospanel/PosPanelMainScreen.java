/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospanel;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.product.ProductTreeNode;
import org.ofbiz.ordermax.base.ThreePanelContainerFrame;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.payment.PaymentAllocatePanel;
import org.ofbiz.ordermax.payment.PaymentEntryPanel;
import javolution.util.FastList;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DepartmentTreeNode;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.product.tree.ProductSelectionTreePanel;

/**
 *
 * @author administrator
 */
public class PosPanelMainScreen extends BaseMainScreen {

    public static final String module = PosPanelMainScreen.class.getName();
    public PaymentEntryPanel paymentPanel = null;
    public PaymentAllocatePanel paymentAllocatePanel = null;
    static String PAYMENT_ENTRY_TAB_INDEX = "PAyment Entry";
    static String PAYMENT_ALLOCATE_TAB_INDEX = "Payment Allocate";
    ThreePanelContainerFrame f = null;
    String caption = "";
    protected JPanel productCardPanel = null;
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    protected List<String> brandMap = null;
    protected Map<String, String> departmentMap = null;
    protected List<Key> departmentList = FastList.newInstance();
    protected PosBrandPanel productPriceEditTablePanel = null;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
    protected String productId = null;

//    protected PriceTreeMap priceTreeMap = null;
    public PosPanelMainScreen( XuiSession sess) {
        super( sess);

    }
    /*
     public void loadScreen() {
     productCardPanel = new JPanel(new CardLayout());
     paymentPanel = new PaymentEntryPanel(ControllerOptions.getSession());
     paymentAllocatePanel = new PaymentAllocatePanel(ControllerOptions.getSession());

     productCardPanel.add(paymentPanel, PAYMENT_ENTRY_TAB_INDEX);
     productCardPanel.add(paymentAllocatePanel, PAYMENT_ALLOCATE_TAB_INDEX);

     f = new ThreePanelContainerFrame();
     f.setTitle("Purchase Order");
     //        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     createTreePanel();

     comboPostalChanged(PAYMENT_ENTRY_TAB_INDEX);


     baseMainPanelInterface.newItem();
     //        OrderHeaderMax orderMax = OrderMaxUtility.loadOrderFromPersistance(orderId, delegator);
     //        baseMainPanelInterface.setOrderHeader(orderMax);
     //        baseMainPanelInterface.loadItemEditDataModel();

     baseMainPanelInterface.addChangeListener(this);

     PaymentButtonPanel buttonPanel = new PaymentButtonPanel();

     //        OrderMaxUtility.addAPanelToPanel(panel, f.getPanelDetail());
     OrderMaxUtility.addAPanelGrid(productCardPanel, f.getPanelDetail());
     //        OrderMaxUtility.addAPanelToPanel(productCardPanel, f.getPanelDetail());        
     OrderMaxUtility.addAPanelToPanel(treePanel.getContainerPanel(), f.getPanelSelecton());
     OrderMaxUtility.addAPanelToPanel(buttonPanel, f.getPanelButton());


     buttonPanel.getBtnSave().addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
     try {
     baseMainPanelInterface.saveItem();
     } catch (Exception ex) {
     Logger.getLogger(PosPanelMainScreen.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     });

     buttonPanel.getBtnAllocate().addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
     //        baseMainPanelInterface.newItem();
     if (paymentPanel.isValidAmount()) {
     paymentAllocatePanel.setPaymentAmount(paymentPanel.getPaymentAmount());
     paymentAllocatePanel.setBillingContact(paymentPanel.getContactDetails());
     paymentAllocatePanel.setPartyId(paymentPanel.getPartyId());
     paymentAllocatePanel.setBankDetail(paymentPanel.getBankDetail());
     paymentAllocatePanel.setPaymentDate(paymentPanel.getPaymentDate());
     comboPostalChanged(PAYMENT_ALLOCATE_TAB_INDEX);
     }
     }
     });

     buttonPanel.getBtnLoad().addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
     //                baseMainPanelInterface.loadItem();
     comboPostalChanged(PAYMENT_ENTRY_TAB_INDEX);
     }
     });
     //        baseMainPanelInterface.setProductListArray((ProductDataTreeLoader) treePanel.getTreeDataList());
     //        f.setSize(1200, 700);
     f.setSize(1000, 700);
     f.setLocationRelativeTo(null);
     f.textField = paymentPanel.getPartyTextField();
     f.setVisible(true);
     }
     */

    public void loadScreen(final ContainerPanelInterface f) {

        createTreePanel();
//        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
//        m_productsArray.loadList();
        Action closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                productId = e.getActionCommand();
                posSelectionDlg.setVisible(false);
//                posSelectionDlg.dispose();
            }
        };

        //productPriceEditTablePanel = new PosBrandPanel(ControllerOptions.getSession(), closeAction);
        //baseMainPanelInterface = productPriceEditTablePanel;
        /*
         brandMap = FastList.newInstance();
         departmentMap = FastMap.newInstance();
         Map departmentValMap = FastMap.newInstance();

         HashMap<String, Key> departmentMap1 = m_productsArray.getAllDepartments();
         Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap1);
         Map<Integer, Key> treeIdMap = new TreeMap<Integer, Key>();
         for (Map.Entry<String, Key> entryDept : treeMap.entrySet()) {
         departmentValMap.put(entryDept.getValue()._id, entryDept.getValue()._name);
         try {
         treeIdMap.put(new Integer(entryDept.getValue()._id), entryDept.getValue());
         } catch (Exception e) {
         //                Debug.logInfo("entryDept.getValue()._id: " + entryDept.getValue()._id, module);
         //                Debug.logError(e, module);
         }
         }

         //        productPriceEditTablePanel.setDepartmentMap(departmentValMap);

         //sorted by id list
         for (Map.Entry<Integer, Key> entryDept : treeIdMap.entrySet()) {
         departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
         }
         //         productPriceEditTablePanel.reloadItemDataModel(departmentList);

         ArrayList<Key> brandList = m_productsArray.getAllBrands();

         //sort
         Map<String, Key> brandTreeMap = new TreeMap<String, Key>();
         for (Key brand : brandList) {
         brandTreeMap.put(brand._name, brand);
         }

         brandList.clear();
         for (Map.Entry<String, Key> entryDept : brandTreeMap.entrySet()) {
         brandList.add(entryDept.getValue());
         }
         */

        OrderMaxUtility.addAPanelGrid(productPriceEditTablePanel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), f.getPanelSelecton());
//        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
    }

    public void loadScreen() {

        ThreePanelContainerFrame f = new ThreePanelContainerFrame(module);
        f.setTitle("Product Edit");
        loadScreen(f);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(1000, 700);
        f.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
        f.setVisible(true);
    }
    TwoPanelNonSizableContainerDlg posSelectionDlg = null;

    public void loadScreenDialog() {
        javax.swing.JFrame frame = new javax.swing.JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
//          frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//          frame.setUndecorated(true);

        //frame.setUndecorated(true);
        //frame.setBounds(frame.getGraphicsConfiguration().getBounds()); 
        //getGraphicsConfiguration().getDevice().setFullScreenWindow(frame);

        posSelectionDlg = new TwoPanelNonSizableContainerDlg(frame, true);

        loadScreen(posSelectionDlg);
        posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        posSelectionDlg.setSize(1000, 750);
        posSelectionDlg.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
//        f.pack();
        posSelectionDlg.setVisible(true);
    }

    @Override
    public void addItem(String id) throws Exception {
        baseMainPanelInterface.addItem(id);
    }

    @Override
    public void refreshScreen() {
        baseMainPanelInterface.refreshScreen();
    }

    @Override
    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
//        baseMainPanelInterface.addItem(id, price, qty);
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

        caption = "Payment Entry - " + partyId + "[";
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

        f.setTitle(caption);
    }
    String visibleCardName = null;

    public void comboPostalChanged(String desc) {
        if (productCardPanel != null) {
            CardLayout cl = (CardLayout) (productCardPanel.getLayout());
            cl.show(productCardPanel, desc);
            visibleCardName = desc;
            if (visibleCardName.equals(PAYMENT_ENTRY_TAB_INDEX)) {
                baseMainPanelInterface = paymentPanel;
            } else if (visibleCardName.equals(PAYMENT_ALLOCATE_TAB_INDEX)) {
                baseMainPanelInterface = paymentAllocatePanel;
            }
        }
    }

    @Override
    protected void createTreePanel() {
//        treePanel 
//        final PartySelectionTreePanel treePanel1 = new PartySelectionTreePanel(ControllerOptions.getSession());
        final ProductSelectionTreePanel treePanel1 = new ProductSelectionTreePanel(ControllerOptions.getSession());
//        treePanel1.loadTree();
        treePanel = treePanel1;


        /*treePanel1.getBtnAddSelected().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TreeNode prodNode = treePanel1.getSelectedTreeNode(ProductTreeNode.ProdutNodeName);
                if (prodNode != null) {
                    try {
                        addItem(prodNode._id);//, treePanel1.getPrice(), treePanel1.getQuantity());
                        refreshScreen();
                    } catch (Exception ex) {
                        Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });*/

        treePanel.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTreeMouseClicked(evt);
            }
        });

    }

    public void setDepartment(String id, String name) {

        ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
        ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(id, name));
        productPriceEditTablePanel.setProductListArray(m_productsArray);
        productPriceEditTablePanel.setBrandList(brandList);
        productPriceEditTablePanel.loadItem();
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
                if (node.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
                    ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
                    setDepartment(node._id, node._name);
                }
            }
        }
        /*
         if (node.getNodeName().equals(ProductTreeNode.ProdutNodeName)) {
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

         customProductPanel.setItem(node);
         comboPostalChanged(PRODUCT_TAB_INDEX);
         } else if (node.getNodeName().equals(BrandTreeNode.BrandNodeName)) {
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

         TreeNode departNode = treePanel.getSelectedParentTreeNode();
         if (departNode != null) {
         ProductDataTreeLoader m_productsArray = (ProductDataTreeLoader) treePanel.getTreeDataList();//.getProductListArray();//new ProductDataTreeLoader(ControllerOptions.getSession());
         ArrayList<Key> brandList = m_productsArray.getBrandIds(new Key(departNode._id, departNode._name));
         brandPanel.reloadBrandItemDataModel(brandList);
         customProductPanel.setBrandList(brandList);
         brandPanel.setItem(node);
         comboPostalChanged(BRAND_TAB_INDEX);
         }

         } else if (node.getNodeName().equals(DepartmentTreeNode.DepartmentNodeName)) {
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
         departmentPanel.setItem(node);
         comboPostalChanged(DEPARTMENT_TAB_INDEX);
         } else if (node.getNodeName().equals(ProductRootTreeNode.ProductRootNodeName)) {
         }
         node.tp = null;
         }
         }
         * */
    }
    
        @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
