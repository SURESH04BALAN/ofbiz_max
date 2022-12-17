/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.supplierproduct;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import mvc.controller.LoadProductWorker;
import mvc.controller.ProductIdVerifyValidator;
import mvc.model.list.ListAdapterListModel;
import static mvc.model.table.InventoryItemDetailTableModel.Columns.orderId;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.product.ProductIdLookupClickAction;
import org.ofbiz.ordermax.product.SetProductIdInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class MaintainSupplierProductController extends BaseMainScreen 
    implements SetProductIdInterface{

    public static final String module = MaintainSupplierProductController.class.getName();
    public SupplierProductPanel panel = null;
    final ListAdapterListModel<SupplierProductComposite> orderListModel = new ListAdapterListModel<SupplierProductComposite>();
    private boolean isSalesList = false;

    public String getCaption() {

        return "Supplier Product";

    }

    public MaintainSupplierProductController( boolean isSalesList, XuiSession sess) {
        super( sess);
        this.isSalesList = isSalesList;
    }

    public MaintainSupplierProductController(ListAdapterListModel<SupplierProductComposite> ordListModel, boolean isSalesList,  XuiSession sess) {
        super( sess);
        orderListModel.addAll(ordListModel.getList());
        this.isSalesList = isSalesList;
    }

//    FindSupplierProductListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<SupplierProductComposite> invoiceCompositeListModel = new ListAdapterListModel<SupplierProductComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new SupplierProductPanel();
        panel.btnProductId.addActionListener(new ProductIdLookupClickAction(this,ControllerOptions.getDesktopPane(),ControllerOptions.getSession()));
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());

        //order selection button
        ActionListener productIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductIdVerifyValidator) {
                    ProductIdVerifyValidator validator = (ProductIdVerifyValidator) e.getSource();
                    String productId = validator.getField().getText();
                    Debug.logInfo("orderId: " + orderId, module);
                    loadProduct(productId);
                }
            }
        };
        
        ProductIdVerifyValidator prodValidator = new ProductIdVerifyValidator(panel.productIdTextField, ControllerOptions.getSession());
        prodValidator.addActionListener(productIdTextChangeAction);
        panel.productIdTextField.setInputVerifier(prodValidator);        
        
        /* if (isSalesList) {
         panel.cbSalesOrder.setSelected(true);
         panel.cbPurchaseOrder.setSelected(false);
         } else {
         panel.cbSalesOrder.setSelected(false);
         panel.cbPurchaseOrder.setSelected(true);

         }
         */

//        panel.setReceiveInventoryList(invoiceCompositeListModel);

//        buttonPanel = new FindSupplierProductListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
/*        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //              panel.getDialogFields();
                //              Order order = panel.getOrder();
                //              SavePurchaseOrderWorker saveOrderWorker = new SavePurchaseOrderWorker(order, ControllerOptions.getSession());;
                Debug.logInfo("before loadPersonsWorker.execute", NAME);
//                saveOrderWorker.execute();
                //               SavePurchaseOrderWorker.saveOrderCart(order, ControllerOptions.getSession());
                Debug.logInfo("after loadPersonsWorker.execute", NAME);

                //update the dialog
//                panel.setDialogFields();
                okButtonPressed();
            }
        });

        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> stausList = FastList.newInstance();
                invoiceCompositeListModel.clear();
                SupplierProductList invList;
                try {

                    invoiceCompositeListModel.clear();

//                    Map<String, Object> findOptionList = panel.getFindOptionList();
                    String partyId = panel.getSupplierPartyId();
                    invList = LoadSupplierProductWorker.getSupplierProductByParty(partyId, ControllerOptions.getSession());

                    invoiceCompositeListModel.addAll(invList.getList());

//                    Debug.logInfo("val.toString(): " + val.toString(), module);
                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
*/        
/*
        ProductIdClickAction productIdClickAction = new ProductIdClickAction(this, parentFrame, desktopPane, ControllerOptions.getSession());
        PartyIdClickAction partyIdClickAction = new PartyIdClickAction(this, parentFrame, desktopPane, ControllerOptions.getSession());     
        panel.getProductActionTableCellEditor().addActionListener(productIdClickAction);
        panel.getPartyActionTableCellEditor().addActionListener(partyIdClickAction);
*/                
        /*
         ActionListener invoiceIdChangeAction = new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

         if (e.getSource() instanceof ProductTreeActionTableCellEditor
         && e instanceof RowColumnActionEvent) {

         //                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
         RowColumnActionEvent event = (RowColumnActionEvent) e;

         try {
         String invoiceId = panel.getTxtProdIdTableTextField().getText();
         Debug.logInfo("orderId: " + invoiceId, module);

         GenericValue orderValue = PosProductHelper.getGenericValueByKey("Invoice", UtilMisc.toMap("invoiceId", invoiceId), delegator);
         if ("PURCHASE_INVOICE".equals(orderValue.getString("invoiceTypeId"))) {
         PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, null, ControllerOptions.getSession());
         purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
         } else {
         SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, null, ControllerOptions.getSession());
         salesOrder.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane, null);
         }
         } catch (Exception ex) {
         Debug.logError(ex, module);
         }
         }
         }
         };
         */

//        ReceiveInventorySetReceiveAllAction allAction = new ReceiveInventorySetReceiveAllAction(invoiceCompositeListModel);
//        buttonPanel.btnReceiveAllInventory.addActionListener(allAction);
//        ReceiveInventoryResetAllAction allResetAction = new ReceiveInventoryResetAllAction(invoiceCompositeListModel);
//        buttonPanel.btnGenerateInvoice.addActionListener(allResetAction);
//        ReceiveInventoryGenerateInventoryAction generateInventoryAction = new ReceiveInventoryGenerateInventoryAction(invoiceCompositeListModel, ControllerOptions.getSession());
//        buttonPanel.btnGenerateInventory.addActionListener(generateInventoryAction);
//        LoadOrderListAction findBtnAction = new LoadOrderListAction(invoiceCompositeListModel, findOptionList, ControllerOptions.getSession());
//        panel.btnFind.addActionListener(findBtnAction);
    }
    public void loadProduct(String productId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        ProductComposite productComposite = LoadProductWorker.loadProduct(productId, ControllerOptions.getSession());
        if (productComposite != null) {
            //clear dialog
            panel.clearDialogFields();

            //set order
            panel.setProductComposite(productComposite);

            //update the dialog
            panel.setDialogField();
        } else {
            JOptionPane.showMessageDialog(null, "Product not found: " + productId, "Load Product", JOptionPane.YES_NO_OPTION);
        }
    }

/*
    @Override
    public String getProductId() {
        return panel.getTxtProdIdTableTextField().getText();

    }

    @Override
    public String getPartyId() {
        return panel.getTxtPartyIdTableTextField().getText();
    }
*/

    @Override
    public void setProductId(String productId) {
        loadProduct(productId);
    }
    @Override
    public String getClassName() {
        return module;
    }
    
}

