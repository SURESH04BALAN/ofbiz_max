/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.TableModel;
import javolution.util.FastList;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ReturnTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindOrderReturnListController extends BaseMainScreen {

    public static final String module = FindOrderReturnListController.class.getName();
    public FindOrderReturnListPanel panel = null;
    public final String caption = "Payment List";
    //   final ListAdapterListModel<PaymentComposite> paymentListModel = new ListAdapterListModel<PaymentComposite>();
    private boolean isSalesList = false;
    ControllerOptions options;

    public String getCaption() {

        if (options.isSalesReturn()) {
            return "Customer Return List";
        } else {
            return "Supplier Return List";
        }

    }

    public FindOrderReturnListController(ControllerOptions options, XuiSession sess) {
        super(sess);
        this.options = options;
    }

    FindInvoiceListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<ReturnHeaderComposite> invoiceCompositeListModel = new ListAdapterListModel<ReturnHeaderComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new FindOrderReturnListPanel(options.getCopy());
        panel.setReturnListModel(invoiceCompositeListModel);
//        

        buttonPanel = new FindInvoiceListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });
        
        buttonPanel.btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
                setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_CANCEL);
            }
        });
        
        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                List<String> stausList = FastList.newInstance();
//                invoiceCompositeListModel.clear();
                List<ReturnHeaderComposite> invList;
                try {

                    //  invoiceCompositeListModel.clear();
//               
                    invoiceCompositeListModel.clear();
                    List<EntityCondition> findOptionList = panel.getWhereClauseCond();
                    //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    LoadReturnWorker worker = new LoadReturnWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    panel.tablePanel.actionPerformed(worker);

                    /* invList = LoadOrderReturnWorker.loadReturnHeaderComposites(findOptionList, ControllerOptions.getSession());

                     invoiceCompositeListModel.addAll(invList);
                     panel.setReturnListModel(invoiceCompositeListModel);*/
                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
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
                        String returnId = null;
                        TableModel model = panel.tablePanel.jTable.getModel();
                        int row = panel.tablePanel.jTable.rowAtPoint(e.getPoint());
                        int col = panel.tablePanel.jTable.columnAtPoint(e.getPoint());
                        if (row >= 0 && col >= 0) {
                            returnId = model.getValueAt(row, ReturnTableModel.Columns.RETURNID.getColumnIndex()).toString();
                        }

                        ControllerOptions option = options.getCopy();
                        option.addReturnId(returnId);
                        MaintainOrderReturnController.runController(option);
                    }
                }
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
                        String returnId = panel.getTxtProdIdTableTextField().getText();
                        Debug.logError("returnId: " + returnId, module);

                        ControllerOptions option = options.getCopy();
                        option.addReturnId(returnId);
                        MaintainOrderReturnController.runController(option);

                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }

                }
            }
        };

        panel.getProductTreeActionTableCellEditor().addActionListener(invoiceIdChangeAction);
    }

    @Override
    public String getClassName() {
        return module;
    }

    public ReturnHeaderComposite getSelectedReturn() {
        return panel.getSelectedReturn();
    }

}
