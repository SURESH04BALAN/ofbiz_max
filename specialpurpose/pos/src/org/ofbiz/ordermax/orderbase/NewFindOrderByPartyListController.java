/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import com.openbravo.pos.reports.selectionbeans.PanelFindOrderBean;
import com.openbravo.basic.BasicException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import mvc.controller.LoadOrderHeaderRolesOrderWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.OrderSearchTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderIdClickAction;

import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderController;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderIdClickAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class NewFindOrderByPartyListController extends BaseMainScreen implements
        OrderIdInterface {

    public static final String module = NewFindOrderByPartyListController.class.getName();
    public PanelFindOrderBean panel = null;
    final ListAdapterListModel<OrderHeaderAndRoleSummary> orderListModel = new ListAdapterListModel<OrderHeaderAndRoleSummary>();

    public String getCaption() {

        if (controllerOptions.getName() != null) {
            return controllerOptions.getName();
        } else {
            return "Purchase Order List";
        }

    }

    ControllerOptions controllerOptions = null;

    public NewFindOrderByPartyListController(ControllerOptions options, XuiSession sess) {
        super(sess);
        controllerOptions = options;
    }

    public NewFindOrderByPartyListController(ListAdapterListModel<OrderHeaderAndRoleSummary> ordListModel, ControllerOptions options, XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
        controllerOptions = options;
    }

    FindOrderByPartyListButtonPanel buttonPanel = null;
    final private ListAdapterListModel<OrderHeaderAndRoleSummary> invoiceCompositeListModel = new ListAdapterListModel<OrderHeaderAndRoleSummary>();
    protected OrderHeaderAndRoleSummary currOrderHeaderAndRoleSummary = null;

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindOrderBean(controllerOptions.getCopy());
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Logger.getLogger(NewFindOrderByPartyListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        panel.setReceiveInventoryList(invoiceCompositeListModel);

        buttonPanel = new FindOrderByPartyListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        buttonPanel.btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    ControllerOptions cOptions = controllerOptions.getCopy();
                    if (controllerOptions.isSalesOrder()) {

                        cOptions.addRoleType("CUSTOMER");
                        cOptions.addOrderType("SALES_ORDER");
                        cOptions.addRoleTypeParent("CUSTOMER");
                        SalesOrderController.runController(cOptions);/*
                         SalesOrderController productMaintainController = new SalesOrderController(cOptions, ControllerOptions.getSession());
                         productMaintainController.loadSinglePanelInternalFrame(FindOrderByPartyListController.module, ControllerOptions.getDesktopPane());
                         */

                        //     f.okButtonPressed();

                    } else {
                        cOptions.addRoleType("SUPPLIER");
                        cOptions.addOrderType("PURCHASE_ORDER");
                        cOptions.addRoleTypeParent("SUPPLIER");
                        PurchaseOrderController.runController(cOptions);/* productMaintainController = new PurchaseOrderController(cOptions, ControllerOptions.getSession());
                         productMaintainController.loadSinglePanelInternalFrame(FindOrderByPartyListController.module, desktopPane, parentFrame);*/

                    }
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
            }
        });
        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (currOrderHeaderAndRoleSummary != null) {
                        ControllerOptions cOptions = controllerOptions.getCopy();
                        String orderId = currOrderHeaderAndRoleSummary.getorderId();
                        cOptions.addOrderId(orderId);
                        if ("SALES_ORDER".equals(currOrderHeaderAndRoleSummary.getorderTypeId())) {

                            cOptions.addRoleType("CUSTOMER");
                            SalesOrderController.runController(cOptions);
//                            SalesOrderController salesOrderController = new SalesOrderController(orderId, controllerOptions,  ControllerOptions.getSession());
//                        productMaintainController.loadNonSizeableTwoPanelDialogScreen(FindOrderByPartyListController.module, desktopPane, null);
                            //                           salesOrderController.loadSinglePanelInternalFrame(SalesOrderController.module, ControllerOptions.getDesktopPane());
                            //     f.okButtonPressed();
                        } else {
                            cOptions.addRoleType("SUPPLIER");
                            PurchaseOrderController.runController(cOptions);/* purchaseOrderController = new PurchaseOrderController(orderId, controllerOptions, null, ControllerOptions.getSession());
                             //                        productMaintainController.loadNonSizeableTwoPanelDialogScreen(FindOrderByPartyListController.module, desktopPane, null);
                             purchaseOrderController.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
                             */

                        }
                    }
                } catch (Exception ex) {
                    Debug.logError(ex, module);
                }
            }
        });

        panel.btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoiceCompositeListModel.clear();
                List<EntityCondition> findOptionList = panel.getFindOptionCondList();
                LoadOrderHeaderRolesOrderWorker worker = new LoadOrderHeaderRolesOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                panel.tablePanel.actionPerformed(worker);
                panel.setVisibleFilter(false);                
            }
        });

        panel.tablePanel.jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    Debug.logInfo("valueChanged ", "module");
                    if (lsm.isSelectionEmpty()) {
                        System.out.println(" <none>");
                        currOrderHeaderAndRoleSummary = null;
                    } else {
                        Debug.logInfo("valueChanged else", "module");
                        // Find out which indexes are selected.
                        int minIndex = lsm.getMinSelectionIndex();
                        int maxIndex = lsm.getMaxSelectionIndex();
                        for (int i = minIndex; i <= maxIndex; i++) {
                            if (lsm.isSelectedIndex(i)) {
                                currOrderHeaderAndRoleSummary = invoiceCompositeListModel.getElementAt(i);
                            }
                        }
                    }
                }
            }
        });

        panel.tablePanel.jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    if (controllerOptions.isDoubleClickCloseDialog()) {
                        f.okButtonPressed();
                        setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);

                    } else {
                        String partyId = null;
                        TableModel model = panel.tablePanel.jTable.getModel();
                        int row = panel.tablePanel.jTable.rowAtPoint(e.getPoint());
                        int col = panel.tablePanel.jTable.columnAtPoint(e.getPoint());
                        if (row >= 0 && col >= 0) {
                            partyId = model.getValueAt(row, OrderSearchTableModel.Columns.ORDERNBR.getColumnIndex()).toString();
                        }

                        ControllerOptions cOptions = controllerOptions.getCopy();
                        String orderId = currOrderHeaderAndRoleSummary.getorderId();
                        cOptions.addOrderId(orderId);
                        if ("SALES_ORDER".equals(currOrderHeaderAndRoleSummary.getorderTypeId())) {

                            cOptions.addRoleType("CUSTOMER");
                            SalesOrderController.runController(cOptions);
                            /*SalesOrderController salesOrderController = new SalesOrderController(orderId, cOptions, ControllerOptions.getSession());
                             //                        productMaintainController.loadNonSizeableTwoPanelDialogScreen(FindOrderByPartyListController.module, desktopPane, null);
                             if (ControllerOptions.getDesktopPane() == null) {
                             salesOrderController.loadNonSizeableTwoPanelDialogScreen(SalesOrderController.module, ControllerOptions.getDesktopPane(), null);
                             } else {
                             salesOrderController.loadSinglePanelInternalFrame(SalesOrderController.module, ControllerOptions.getDesktopPane());
                             }*/

                            //     f.okButtonPressed();
                        } else {
                            cOptions.addRoleType("SUPPLIER");
                            PurchaseOrderController.runController(cOptions);// purchaseOrderController = new PurchaseOrderController(orderId, cOptions, null, ControllerOptions.getSession());

                        }

                    }
                }
            }
        });

        // Close the dialog when Esc is pressed
        if (panel.getRootPane() != null) {
            String cancelName = "cancel";
            String enterName = "enter";
            InputMap inputMap = panel.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterName);
            ActionMap actionMap = panel.getRootPane().getActionMap();

            actionMap.put(cancelName, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    f.cancelButtonPressed();
                }
            });

            actionMap.put(enterName, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f.okButtonPressed();
                }
            });
        }

        if (controllerOptions.isSalesOrder()) {
            SalesOrderIdClickAction partyIdClickAction = new SalesOrderIdClickAction(this, ControllerOptions.getDesktopPane(), ControllerOptions.getSession(), f.getContainerType());
            panel.getProductActionTableCellEditor().addActionListener(partyIdClickAction);

        } else {
            PurchaseOrderIdClickAction partyIdClickAction = new PurchaseOrderIdClickAction(this, ControllerOptions.getDesktopPane(), ControllerOptions.getSession(), f.getContainerType());
            panel.getProductActionTableCellEditor().addActionListener(partyIdClickAction);
        }

        /* ActionListener orderIdChangeAction = new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
         Debug.logInfo("orderId: " + e.getSource().toString(), module);
         if (e.getSource() instanceof ProductTreeActionTableCellEditor
         && e instanceof RowColumnActionEvent) {

         //                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
         RowColumnActionEvent event = (RowColumnActionEvent) e;

         try {
         String orderId = panel.getTxtOrderIdTableTextField().getText();
         Debug.logInfo("orderId: " + orderId, module);
         ControllerOptions cOptions = new ControllerOptions(controllerOptions);
         GenericValue orderValue = PosProductHelper.getGenericValueByKey("OrderHeader", UtilMisc.toMap("orderId", orderId), delegator);
         if ("SALES_ORDER".equals(orderValue.getString("orderTypeId"))) {
         SalesOrderController purchaseOrder = new SalesOrderController(orderId, cOptions, null, ControllerOptions.getSession());
         purchaseOrder.loadTwoPanelInternalFrame(SalesOrderController.module, desktopPane, null);
         } else {
         PurchaseOrderController purchaseOrder = new PurchaseOrderController(orderId, cOptions, null, ControllerOptions.getSession());
         purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
         }
         } catch (Exception ex) {
         Debug.logError(ex, module);
         }
         }
         }
         };

         panel.getProductActionTableCellEditor().addActionListener(orderIdChangeAction);
         */
    }

    public OrderHeaderAndRoleSummary getSelectedOrder() {
        return panel.getSelectedOrder();
    }

    @Override
    public String getClassName() {
        return module;
    }

    @Override
    public String getOrderId() {
        return panel.getTxtOrderIdTableTextField().getText();
    }

}
