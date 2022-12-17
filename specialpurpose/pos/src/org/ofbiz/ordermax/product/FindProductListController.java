/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindProductBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import mvc.controller.LoadProductWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ProductCompositeTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.invoice.FindInvoiceListButtonPanel;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindProductListController extends BaseMainScreen {

    public static final String module = FindProductListController.class.getName();
    public PanelFindProductBean panel = null;
    public final String caption = null;
//    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();
    ControllerOptions options = null;
    int iCount = 0;
    List<ProductComposite> selList = new ArrayList<ProductComposite>();

    public String getCaption() {
        return "Product List";
    }

    public FindProductListController(ControllerOptions options, XuiSession sess) {
        super(sess);
        this.options = options;
    }

    FindInvoiceListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<ProductComposite> invoiceCompositeListModel = new ListAdapterListModel<ProductComposite>();

//    final private Map<String, Object> findOptionList = FastMap.newInstance();
    //JButton btnAddToList = null;
    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindProductBean(options);
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        panel.setProductList(invoiceCompositeListModel);

        buttonPanel = new FindInvoiceListButtonPanel();
        //btnAddToList = new JButton();
//        buttonPanel.add(btnAddToList);

//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        buttonPanel.btnOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                selList.addAll(getTableSelectedListArray());
                buttonPanel.btnOptions.setText("Add Selected ( " + String.valueOf(selList.size()) + ")");
            }
        });

        buttonPanel.btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
                setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_CANCEL);
            }
        });

        panel.btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                invoiceCompositeListModel.clear();
                List<EntityCondition> findOptionList = panel.getFindOptionCondList();
                for (EntityCondition entry : findOptionList) {
                    Debug.logError(entry.toString(), module);
                }

                //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                LoadProductWorker worker = new LoadProductWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                panel.tablePanel.actionPerformed(worker);
                panel.setVisibleFilter(false);
            }
        });

        ActionListener invoiceIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductTreeActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    RowColumnActionEvent event = (RowColumnActionEvent) e;

                    try {
                        String productId = panel.getTxtProdIdTableTextField().getText();
                        //GenericValue productValue = PosProductHelper.getGenericValueByKey("Product", UtilMisc.toMap("productId", productId), delegator);

                        ProductMaintainController productMaintainController = new ProductMaintainController(productId, options, ControllerOptions.getSession());
                        productMaintainController.loadTwoPanelInternalFrame(ProductMaintainController.module, ControllerOptions.getDesktopPane());

                        /*                        if ("PURCHASE_INVOICE".equals(orderValue.getString("invoiceTypeId"))) {
                         PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, null, ControllerOptions.getSession());
                         purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
                         } else {
                         SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, null, ControllerOptions.getSession());
                         salesOrder.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane, null);
                         }
                         */
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                    /*
                     Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
                     if (genVal != null) {
                     if (genVal.containsKey("productId")) {

                     if (panel != null) {

                     JTextField textField = panel.getTxtProdIdTableTextField();
                     if (textField != null) {
                     textField.setText(genVal.get("productId").toString());
                     Debug.logWarning("Order changed - product id text field: " + genVal.get("productId").toString(), module);
                     //                                        processProductIdTextFieldChange(textField, event.getRow());
                     event.getTable().setValueAt(genVal.get("productId").toString(), event.getRow(), OrderEntryTableModel.ORDER_PROD_ID_INDEX);
                     event.getTable().changeSelection(event.getRow(), OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);
                     }
                     } else {
                     //                        table.setValueAt(genVal.get("productId").toString(), row, column);
                     }
                     }
                     }
                     */

                }
            }
        };

        panel.getProductTreeActionTableCellEditor().addActionListener(invoiceIdChangeAction);
        panel.tablePanel.jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    Debug.logInfo("valueChanged ", "module");
                    if (lsm.isSelectionEmpty()) {
                        System.out.println(" <none>");
                    } else {
                        Debug.logInfo("valueChanged else", "module");
                        // Find out which indexes are selected.
                        int minIndex = lsm.getMinSelectionIndex();
                        int maxIndex = lsm.getMaxSelectionIndex();
                        for (int i = minIndex; i <= maxIndex; i++) {
                            if (lsm.isSelectedIndex(i)) {
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

                    if (options.isDoubleClickCloseDialog()) {
                        f.okButtonPressed();
                        setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);

                    } else {
                        String productId = null;
                        TableModel model = panel.tablePanel.jTable.getModel();
                        int row = panel.tablePanel.jTable.rowAtPoint(e.getPoint());
                        int col = panel.tablePanel.jTable.columnAtPoint(e.getPoint());
                        if (row >= 0 && col >= 0) {
                            productId = model.getValueAt(row, ProductCompositeTableModel.Columns.PRODUCTID.getColumnIndex()).toString();
                        }
                        ControllerOptions option = new ControllerOptions(options);
                        ProductMaintainController productMaintainController = new ProductMaintainController(productId, option, ControllerOptions.getSession());

                        if (ControllerOptions.getDesktopPane() == null) {
                            productMaintainController.loadNonSizeableTwoPanelDialogScreen(ProductMaintainController.module, ControllerOptions.getDesktopPane(), null);

                        } else {
                            productMaintainController.loadTwoPanelInternalFrame(ProductMaintainController.module, ControllerOptions.getDesktopPane());
                        }
                    }
                }
            }
        });
//        ReceiveInventorySetReceiveAllAction allAction = new ReceiveInventorySetReceiveAllAction(invoiceCompositeListModel);
//        buttonPanel.btnReceiveAllInventory.addActionListener(allAction);
//        ReceiveInventoryResetAllAction allResetAction = new ReceiveInventoryResetAllAction(invoiceCompositeListModel);
//        buttonPanel.btnGenerateInvoice.addActionListener(allResetAction);
//        ReceiveInventoryGenerateInventoryAction generateInventoryAction = new ReceiveInventoryGenerateInventoryAction(invoiceCompositeListModel, ControllerOptions.getSession());
//        buttonPanel.btnGenerateInventory.addActionListener(generateInventoryAction);
//        LoadOrderListAction findBtnAction = new LoadOrderListAction(invoiceCompositeListModel, findOptionList, ControllerOptions.getSession());
//        panel.btnFind.addActionListener(findBtnAction);
    }

    private List<ProductComposite> getTableSelectedListArray() {
        List<ProductComposite> arrayList = new ArrayList<ProductComposite>();
        for (ProductComposite prodCom : invoiceCompositeListModel.getList()) {
            if (prodCom.isSelected()) {
                arrayList.add(prodCom);
                prodCom.setSelected(false);
            }
        }
        return arrayList;
    }

    public List<ProductComposite> getSelectedProductListArray() {
        Debug.logInfo("selList " + selList.size(), "module");

        return selList;
    }

    public ProductComposite getSelectedProduct() {
        return panel.getSelectedProduct();
    }

    @Override
    public String getClassName() {
        return module;
    }

}
