/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindPaymentBean;
import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Action.NAME;
import javax.swing.JInternalFrame;
import mvc.controller.LoadPaymentWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.payment.purchase.SupplierPaymentInvoiceController;
import org.ofbiz.ordermax.payment.sales.CustomerPaymentInvoiceController;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindPaymentListController extends BaseMainScreen {

    public static final String module = FindPaymentListController.class.getName();
    public PanelFindPaymentBean panel = null;
    public final String caption = "Payment List";
    //   final ListAdapterListModel<PaymentComposite> paymentListModel = new ListAdapterListModel<PaymentComposite>();

    ControllerOptions options = null;

    @Override
    public String getCaption() {

        if (options.isSalesOrder()) {
            return "Customer Payment List";
        } else {
            return "Supplier Payment List";
        }

    }

    static public FindPaymentListController runController(ControllerOptions options) {

        FindPaymentListController controller = new FindPaymentListController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(FindPaymentListController.module, options.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(FindPaymentListController.module, options.getDesktopPane());
        }
        return controller;
    }

    public FindPaymentListController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
    }
    
    @Override
    protected void setSizeAndLocation(JInternalFrame contFrame) {
        int y = 10;
        int x = 200;
        if (ControllerOptions.getDesktopPane() != null) {
            contFrame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);

        }
        contFrame.setLocation(x, y);        
    }
    FindInvoiceListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<PaymentComposite> invoiceCompositeListModel = new ListAdapterListModel<PaymentComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        /*      try {
         throw new Exception("double click");
         } catch (Exception e1) {
         Debug.logError(e1, module);
         }
         */
        ControllerOptions paneloption = options.getCopy();
        panel = new PanelFindPaymentBean(paneloption);
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        panel.setPaymentList(invoiceCompositeListModel);

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
        
        panel.tablePanel.jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    if (options.isDoubleClickCloseDialog()) {

                        f.okButtonPressed();
                        setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);

                    } else {
                        int row = panel.tablePanel.jTable.rowAtPoint(e.getPoint());

                        try {
                            int rowIndex = panel.tablePanel.jTable.convertRowIndexToView(row);
                            if (rowIndex > -1) {
                                PaymentComposite paymentComposite = invoiceCompositeListModel.getElementAt(rowIndex);
                                if (paymentComposite != null) {
                                    String paymentTypeId = paymentComposite.getPayment().getpaymentTypeId();
                                    PaymentType paymentType = PaymentTypeSingleton.getPaymentType(paymentTypeId);
                                    if (paymentType != null) {
                                         //String paymentId = panel.getTxtProdIdTableTextField().getText();
                                        if ("RECEIPT".equals(paymentType.getparentTypeId())) {
                                            ControllerOptions option = options.getCopy();
                                            String paymentId = paymentComposite.getPayment().getpaymentId();
                                            option.addPaymentId(paymentId);
                                            option.addPaymentType(paymentTypeId);
                                            Debug.logError("paymentId: " + paymentId, module);

                                            CustomerPaymentInvoiceController.runController(option);// paymentController = new CustomerPaymentInvoiceController(option);
                                            //paymentController.loadTwoPanelInternalFrame(PaymentController.module, ControllerOptions.getDesktopPane());
                                        } else {
                                            ControllerOptions option = options.getCopy();
                                            String paymentId = paymentComposite.getPayment().getpaymentId();
                                            option.addPaymentId(paymentId);
                                            option.addPaymentType(paymentTypeId);                                            
                                            Debug.logError("paymentId: " + paymentId, module);

                                            SupplierPaymentInvoiceController.runController(option);
                                            //SupplierPaymentInvoiceController paymentController = new SupplierPaymentInvoiceController(paymentId, ControllerOptions.getSession());
                                            //paymentController.loadTwoPanelInternalFrame(PaymentController.module, ControllerOptions.getDesktopPane());
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }

                    }
                }
            }
        });
        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {                   
                    invoiceCompositeListModel.clear();
                    
                    Map<String, Object> result = panel.getValues();
//                findOptionList.put("userLogin", ControllerOptions.getSession().getUserLogin());
                    for (Map.Entry<String, Object> entry : result.entrySet()) {
                        Debug.logError(entry.getKey() + " : " + entry.getValue(), module);
                    }
                    Map<String, Object> findOptionList = (Map<String, Object>) result.get("JParamsFormPayment");
                    
                    for (Map.Entry<String, Object> entry : findOptionList.entrySet()) {
                        Debug.logError(entry.getKey() + " : " + entry.getValue(), module);
                    }                    
                    //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    LoadPaymentWorker worker = new LoadPaymentWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    panel.tablePanel.actionPerformed(worker);
                    panel.setVisibleFilter(false);                    
                } catch (BasicException ex) {
                    Logger.getLogger(FindPaymentListController.class.getName()).log(Level.SEVERE, null, ex);
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
                        int rowIndex = panel.tablePanel.jTable.convertRowIndexToView(event.getRow());
                        if (rowIndex > -1) {
                            PaymentComposite paymentComposite = invoiceCompositeListModel.getElementAt(rowIndex);
                            if (paymentComposite != null) {
                                String paymentTypeId = paymentComposite.getPayment().getpaymentTypeId();
                                PaymentType paymentType = PaymentTypeSingleton.getPaymentType(paymentTypeId);
                                if (paymentType != null) {
                                    String paymentId = panel.getTxtProdIdTableTextField().getText();
                                    ControllerOptions option = options.getCopy();
                                    option.addPaymentId(paymentId);
                                    Debug.logError("paymentId: " + paymentId, module);
                                    if ("RECEIPT".equals(paymentType.getparentTypeId())) {
                                        CustomerPaymentInvoiceController.runController(option);// paymentController = new CustomerPaymentInvoiceController(option);
                                        //paymentController.loadTwoPanelInternalFrame(PaymentController.module, ControllerOptions.getDesktopPane());
                                    } else {
                                        SupplierPaymentInvoiceController.runController(option);
                                        //SupplierPaymentInvoiceController paymentController = new SupplierPaymentInvoiceController(paymentId, ControllerOptions.getSession());
                                        //paymentController.loadTwoPanelInternalFrame(PaymentController.module, ControllerOptions.getDesktopPane());
                                    }
                                }
                            }
                        }
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

}
