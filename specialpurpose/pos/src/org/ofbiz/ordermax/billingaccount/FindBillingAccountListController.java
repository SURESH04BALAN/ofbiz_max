/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindBillingAccountBean;
import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javolution.util.FastList;
import mvc.controller.LoadBillingAccountWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindBillingAccountListController extends BaseMainScreen implements PrimaryIdInterface {

    public static final String module = FindBillingAccountListController.class.getName();
    public PanelFindBillingAccountBean panel = null;
    public final String caption = "Billing Account List";

    public String getCaption() {
        return "Billing Account List";
    }

    public FindBillingAccountListController(boolean isSalesList, XuiSession sess) {
        super(sess);
    }

    FindInvoiceListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<BillingAccount> invoiceCompositeListModel = new ListAdapterListModel<BillingAccount>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();
    ControllerOptions controllerOptions = new ControllerOptions();
    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindBillingAccountBean(controllerOptions);
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        panel.setBillingAccountList(invoiceCompositeListModel);

        buttonPanel = new FindInvoiceListButtonPanel();
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
                invoiceCompositeListModel.clear();

                try {

                    invoiceCompositeListModel.clear();
                    Map<String, Object> findOptionList = panel.getValues();
//                    invList = LoadPaymentWorker.loadPayments(findOptionList, ControllerOptions.getSession());
                    Debug.logError("find billing account", module);
                    List<BillingAccount> list = LoadBillingAccountWorker.loadBillingAccounts(findOptionList, ControllerOptions.getSession());
                    invoiceCompositeListModel.addAll(list);
                    panel.setVisibleFilter(false);                         

                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        BillingAccountIdClickAction productIdClickAction = new BillingAccountIdClickAction(this, ControllerOptions.getDesktopPane(), ControllerOptions.getSession());
        panel.getRowColumnActionCellEditor().addActionListener(productIdClickAction);

    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 30;
        int x = 100;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    @Override
    public String getPrimaryId() {
        return panel.getTextIdTableTextField().getText();
    }

    @Override
    public String getClassName() {
        return module;
    }

}
