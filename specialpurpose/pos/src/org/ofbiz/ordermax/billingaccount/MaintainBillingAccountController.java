/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import mvc.controller.LoadBillingAccountWorker;
import mvc.controller.ProductIdVerifyValidator;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.party.PanelPartyRole;
import org.ofbiz.ordermax.product.ProductIdLookupClickAction;
import org.ofbiz.ordermax.product.SetProductIdInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class MaintainBillingAccountController extends BaseMainScreen
        implements BillingAccountActionInterface {

    public static final String module = MaintainBillingAccountController.class.getName();
    public BillingAccountPanel billingAccountPanel = null;

    final ListAdapterListModel<SupplierProductComposite> orderListModel = new ListAdapterListModel<SupplierProductComposite>();
    private String billingAccountId = "";

    public String getCaption() {

        return "Billing Account";

    }
    
    ControllerOptions options = new ControllerOptions();

    public MaintainBillingAccountController(String billingAccountId, boolean isSalesList, XuiSession sess) {
        super(sess);
        this.billingAccountId = billingAccountId;
    }

    public MaintainBillingAccountController(ListAdapterListModel<SupplierProductComposite> ordListModel, boolean isSalesList, XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
//        this.isSalesList = isSalesList;
    }

//    FindSupplierProductListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<SupplierProductComposite> invoiceCompositeListModel = new ListAdapterListModel<SupplierProductComposite>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        billingAccountPanel = new BillingAccountPanel(options);

//        billingAccountDetailPanel.txtBillingAccountId.addActionListener(new PartyIdLookupClickAction(this, ControllerOptions.getDesktopPane(), ControllerOptions.getSession()));
        OrderMaxUtility.addAPanelGrid(billingAccountPanel, f.getPanelDetail());

        //order selection button
        ActionListener partyIdTextChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductIdVerifyValidator) {
                    ProductIdVerifyValidator validator = (ProductIdVerifyValidator) e.getSource();
                    String partyId = validator.getField().getText();
                                        loadBillingAccount(partyId);
                }
            }
        };

        ProductIdVerifyValidator prodValidator = new ProductIdVerifyValidator(billingAccountPanel.billingAccountDetailPanel.txtBillingAccountId, ControllerOptions.getSession());
        prodValidator.addActionListener(partyIdTextChangeAction);
        billingAccountPanel.billingAccountDetailPanel.txtBillingAccountId.setInputVerifier(prodValidator);
        if (billingAccountId != null) {
            loadBillingAccount(billingAccountId);
        }
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 30;
        int x = 300;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    public void loadBillingAccount(String billingAccountId) {

        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        BillingAccountComposite billingAccountComposite = LoadBillingAccountWorker.getBillingAccountComposite(billingAccountId, ControllerOptions.getSession());
        if (billingAccountComposite != null) {
            //clear dialog
            clearDialogFields();

            //set order
            setBillingAccountComposite(billingAccountComposite);

            //update the dialog
            setDialogFields();
        } else {
            JOptionPane.showMessageDialog(null, "Product not found: " + billingAccountId, "Load Product", JOptionPane.YES_NO_OPTION);
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
    public void setBillingAccountComposite(BillingAccountComposite billingAccountComposite) {
        billingAccountPanel.setBillingAccountComposite(billingAccountComposite);

    }

    public void setBillingAccountId(String billingAccountId) {
        loadBillingAccount(billingAccountId);
    }

    @Override
    public String getClassName() {
        return module;
    }

    @Override
    public BillingAccountComposite getBillingAccountComposite() {
        return billingAccountPanel.getBillingAccountComposite();
    }

    void getDialogFields() {
        billingAccountPanel.getDialogFields();

    }

    void setDialogFields() {
        billingAccountPanel.setDialogFields();

    }

    void clearDialogFields() {
        billingAccountPanel.clearDialogFields();

    }

}
