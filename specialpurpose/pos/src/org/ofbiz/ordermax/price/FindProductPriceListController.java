/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.price;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindProductPriceBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.Action.NAME;
import javolution.util.FastList;
import mvc.controller.LoadProductPriceWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.ProductPriceList;
import org.ofbiz.ordermax.invoice.FindInvoiceListButtonPanel;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.product.ProductIdClickAction;
import org.ofbiz.ordermax.product.GetProductIdInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindProductPriceListController extends BaseMainScreen
        implements GetProductIdInterface, PartyIdInterface {

    public static final String module = FindProductPriceListController.class.getName();
    public PanelFindProductPriceBean panel = null;
//    final ListAdapterListModel<ProductPriceComposite> orderListModel = new ListAdapterListModel<ProductPriceComposite>();
    final private ListAdapterListModel<ProductPriceComposite> invoiceCompositeListModel = new ListAdapterListModel<ProductPriceComposite>();
    private ControllerOptions controllerOptions;

    public String getCaption() {

        return "Product Price List";

    }

    public FindProductPriceListController(ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        this.controllerOptions = controllerOptions;
    }

    public FindProductPriceListController(ListAdapterListModel<ProductPriceComposite> ordListModel, ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        invoiceCompositeListModel.addAll(ordListModel.getList());
        this.controllerOptions = controllerOptions;
    }

    FindInvoiceListButtonPanel buttonPanel = null;

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindProductPriceBean(controllerOptions);

        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        panel.setProductPriceList(invoiceCompositeListModel);

        buttonPanel = new FindInvoiceListButtonPanel();

        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String> stausList = FastList.newInstance();
                invoiceCompositeListModel.clear();
                ProductPriceList invList;
                try {

                    invoiceCompositeListModel.clear();
                    invoiceCompositeListModel.clear();

                    List<EntityCondition> findOptionList = panel.getFindOptionCondList();
                    for (EntityCondition entry : findOptionList) {
                        Debug.logError(entry.toString(), module);
                    }
                   
                    LoadProductPriceWorker worker = new LoadProductPriceWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    panel.tablePanel.actionPerformed(worker);
                    panel.setVisibleFilter(false);                     
                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        
        ProductIdClickAction productIdClickAction = new ProductIdClickAction(this, controllerOptions, ControllerOptions.getDesktopPane(), ControllerOptions.getSession());
        panel.getProductActionTableCellEditor().addActionListener(productIdClickAction);

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
