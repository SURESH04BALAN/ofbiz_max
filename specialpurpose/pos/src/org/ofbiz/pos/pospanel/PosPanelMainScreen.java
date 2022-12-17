/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.pospanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.OnePanelNonSizableContainerDlg;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TreeNode;

/**
 *
 * @author administrator
 */
public class PosPanelMainScreen extends BaseMainScreen {

    public static final String module = PosPanelMainScreen.class.getName();
    String caption = "";
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    protected String productId = null;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

//    protected PriceTreeMap priceTreeMap = null;
    public PosPanelMainScreen(XuiSession sess) {
        super(sess);

    }

    //OnePanelNonSizableContainerDlg posSelectionDlg;
    public void loadScreen() {

        final ProductDataTreeLoader productListArray = BaseHelper.getProductDataLoader();
        productId = null;

        productListArray.loadList();

        HashMap<String, TreeNode> treeMap = productListArray.getAllTopCategoryTreeNodes();
        //Map<String, Key> treeMap = new TreeMap<String, Key>(departmentMap1);
        //Map<String, Key> treeIdMap = new TreeMap<String, Key>();
        ArrayList<TreeNode> departmentList = new ArrayList<TreeNode>();
        for (Map.Entry<String, TreeNode> treeNodes : treeMap.entrySet()) {
            departmentList.add(treeNodes.getValue());
            //try {
            //    treeIdMap.put(entryDept.getValue()._name, entryDept.getValue());
            //} catch (Exception e) {
            //}
        }

        //for (Map.Entry<String, Key> entryDept : treeIdMap.entrySet()) {
        //    departmentList.add(new Key(entryDept.getValue()._id, entryDept.getValue()._name));
        //}
        int rows = 5;
        int cols = (departmentList.size() / rows) + 1;

        final PosDepartmentPanel deptPanel = new PosDepartmentPanel(ControllerOptions.getSession(), departmentList, rows, cols);
        deptPanel.setDepartmentList(departmentList);
        deptPanel.setProductListArray(productListArray);
        deptPanel.loadItem();

        final javax.swing.JFrame frame = new javax.swing.JFrame();
//        frame.add(deptPanel);
//        frame.setVisible(true);

        final OnePanelNonSizableContainerDlg posSelectionDlg = new OnePanelNonSizableContainerDlg(frame, true);
        posSelectionDlg.getPanelDetail().setLayout(new BorderLayout());
        posSelectionDlg.getPanelDetail().add(deptPanel);
//        posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        posSelectionDlg.setSize(1024, 768);
        posSelectionDlg.setLocation(ControllerOptions.getSession().getScreenP1());
        posSelectionDlg.setLocationRelativeTo(null);
//         posSelectionDlg.setVisible(true);
        //      posSelectionDlg = null;
        Debug.logInfo("Department OnePanelNonSizableContainerDlg is opened ", module);
        deptPanel.buttonHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Debug.logInfo("Department OnePanelNonSizableContainerDlg is trying to close ", module);
                posSelectionDlg.okButtonPressed();
                //posSelectionDlg.dispose();
                Debug.logInfo("Department OnePanelNonSizableContainerDlg is trying to closed ", module);
            }
        });

        ActionListener actListner = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int commaPos = e.getActionCommand().indexOf(",");
                String id = e.getActionCommand().substring(0, commaPos);
                String name = e.getActionCommand().substring(commaPos + 1, e.getActionCommand().length());
                Debug.logInfo("Col: " + id + " Row: " + name, "module");
                productId = null;
                javax.swing.JFrame frame = new javax.swing.JFrame();
                final OnePanelNonSizableContainerDlg posBrandSelectionDlg = new OnePanelNonSizableContainerDlg(frame, true);

                final Action brandAction = new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        productId = null;
                        if (PosPanelHelper.ShowDepartment.equals(e.getActionCommand())) {
                            posBrandSelectionDlg.okButtonPressed();;
                            //posBrandSelectionDlg.dispose();

                        } else if (PosPanelHelper.ShowHome.equals(e.getActionCommand())) {
                            posBrandSelectionDlg.okButtonPressed();;
                            //posBrandSelectionDlg.dispose();

                            posSelectionDlg.okButtonPressed();
                            //posSelectionDlg.dispose();

                        } else {

                            productId = e.getActionCommand();
                            posBrandSelectionDlg.okButtonPressed();;
                            //posBrandSelectionDlg.dispose();

                            posSelectionDlg.okButtonPressed();
                        }
                    }
                };

                /*if (node.isLoaded() == false) {
                 List<TreeNode> childs = productListArray.getChildTreeNodes(node);
                 node.setChildrenNodes(childs);
                 node.setLoaded(true);
                 node.setHasChildrean(!childs.isEmpty());
                 Debug.logInfo("treeTreeWillExpand 1 just loaded): "
                 + path.getLastPathComponent().toString() + "node.isLoaded(): " + node.isLoaded() + " node.setHasChildrean: " + node.isHasChildrean()
                 + " count: " + childs.size(), module);

                 }*/
                if (e.getSource() instanceof PosDepartmentPanel.PosDesktopButton) {
                    PosDepartmentPanel.PosDesktopButton button = (PosDepartmentPanel.PosDesktopButton) e.getSource();

                    List<TreeNode> brandList = productListArray.getChildTreeNodes(button.treeNode);
                    PosBrandPanel brandPanel = new PosBrandPanel(ControllerOptions.getSession(), brandAction, brandList, productListArray);

                    posBrandSelectionDlg.getPanelDetail().setLayout(new BorderLayout());
                    posBrandSelectionDlg.getPanelDetail().add(brandPanel);
//        posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    posBrandSelectionDlg.setSize(1024, 768);
                    posBrandSelectionDlg.setLocation(ControllerOptions.getSession().getScreenP1());

//        posSelectionDlg.setLocationRelativeTo(null);
//        f.textField = panel.getPartyTextField();
//        f.pack();
                    Debug.logInfo("Brand OnePanelNonSizableContainerDlg is opened ", "module");
                    posBrandSelectionDlg.setVisible(true);
                    brandPanel = null;
                    Debug.logInfo("Brand OnePanelNonSizableContainerDlg is closed ", "module");
                }
            }
        };

        ArrayList<PosDepartmentPanel.PosDesktopButton> buttonList = deptPanel.getDepartmentButtonList();
        for (PosDepartmentPanel.PosDesktopButton btn : buttonList) {
            btn.addActionListener(actListner);
        }

        posSelectionDlg.setVisible(true);

    }

    public void loadScreen(final ContainerPanelInterface f) {
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
    }

    @Override
    protected void createTreePanel() {
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
