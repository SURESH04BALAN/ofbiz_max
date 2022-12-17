/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.supplierproduct;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import static javax.swing.Action.NAME;
import javax.swing.JFrame;
import mvc.controller.LoadSupplierProductWorker;
import mvc.model.list.ListAdapterListModel;
import static org.ofbiz.base.container.ClassLoaderContainer.getClassLoader;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.party.PartyIdClickAction;
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.product.ProductIdClickAction;
import org.ofbiz.ordermax.product.GetProductIdInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindSupplierProductListController extends BaseMainScreen
        implements GetProductIdInterface, PartyIdInterface {

    public static final String module = FindSupplierProductListController.class.getName();
    public FindSupplierProductListPanel panel = null;
    final ListAdapterListModel<SupplierProductComposite> orderListModel = new ListAdapterListModel<SupplierProductComposite>();
    private boolean isSalesList = false;

    public String getCaption() {

        return "Supplier Product List";

    }
    
    ControllerOptions controllerOptions = null;
    
    public FindSupplierProductListController(ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        this.controllerOptions = controllerOptions;
    }

    public FindSupplierProductListController(ListAdapterListModel<SupplierProductComposite> ordListModel, ControllerOptions controllerOptions,  XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
        this.controllerOptions = controllerOptions;
    }

    FindSupplierProductListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<SupplierProductComposite> invoiceCompositeListModel = new ListAdapterListModel<SupplierProductComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new FindSupplierProductListPanel(controllerOptions);

        panel.setReceiveInventoryList(invoiceCompositeListModel);

        buttonPanel = new FindSupplierProductListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
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
                f.okButtonPressed();
            }
        });

        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                if( panel.cbAll.isSelected()
//                orderStatusId
/*                List<String> stausList = FastList.newInstance();
                 invoiceCompositeListModel.clear();
                 SupplierProductList invList;
                 try {

                 invoiceCompositeListModel.clear();

                 //                    Map<String, Object> findOptionList = panel.getFindOptionList();
                 if (panel.getProductWithoutSupplier()==false) {
                 String partyId = panel.getSupplierPartyId();
                 invList = LoadSupplierProductFromFileWorker.getSupplierProductByParty(partyId, ControllerOptions.getSession());
                 invoiceCompositeListModel.addAll(invList.getList());
                 }
                 else{
                 invList = LoadSupplierProductFromFileWorker.getProductWithoutSupplierProduct(ControllerOptions.getSession());
                 invoiceCompositeListModel.addAll(invList.getList());                        
                 }   
                 //                    Debug.logInfo("val.toString(): " + val.toString(), module);
                 } catch (Exception ex) {
                 Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 */

                invoiceCompositeListModel.clear();
//                panel.setReceiveInventoryList(invoiceCompositeListModel);

                Map<String, Object> findOptionList = panel.getFindOptionList();
//                findOptionList.put("userLogin", ControllerOptions.getSession().getUserLogin());
                for (Map.Entry<String, Object> entry : findOptionList.entrySet()) {
                    Debug.logError(entry.getKey() + " : " + entry.getValue(), module);
                }

                //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                LoadSupplierProductWorker worker = new LoadSupplierProductWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                panel.tablePanel.actionPerformed(worker);
            }
        });
        ProductIdClickAction productIdClickAction = new ProductIdClickAction(this, controllerOptions, ControllerOptions.getDesktopPane(), ControllerOptions.getSession());
        PartyIdClickAction partyIdClickAction = new PartyIdClickAction(this, ControllerOptions.getDesktopPane(), controllerOptions);
        panel.getProductActionTableCellEditor().addActionListener(productIdClickAction);
        panel.getPartyActionTableCellEditor().addActionListener(partyIdClickAction);
    }

    @Override
    public String getProductId() {
        return panel.getTxtProdIdTableTextField().getText();

    }

    @Override
    public String getPartyId() {
        return panel.getTxtPartyIdTableTextField().getText();
    }
    @Override
    public String getClassName() {
        return module;
    }

}
