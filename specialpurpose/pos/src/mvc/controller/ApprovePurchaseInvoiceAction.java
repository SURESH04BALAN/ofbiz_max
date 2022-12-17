package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.Action;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.party.PartyContactMechHelper;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.service.ServiceUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class ApprovePurchaseInvoiceAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ListAdapterListModel<InvoiceComposite> invoiceCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "APPROVE INVOICE";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    private XuiSession session;

    public ApprovePurchaseInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<InvoiceComposite> invoiceCompListModel) {
        super(ActionType.APPROVE_PURCHASE_INVOICE_ACTION, name, session, desktopPane);
        this.invoiceCompListModel = invoiceCompListModel;
        this.session = session;
    }

    public ApprovePurchaseInvoiceAction(ListAdapterListModel<InvoiceComposite> invoiceCompListModel, XuiSession session) {
        super(ActionType.APPROVE_PURCHASE_INVOICE_ACTION, session);
        this.invoiceCompListModel = invoiceCompListModel;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "APPROVE INVOICE";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    public void actionPerformed(ActionEvent e) {

        GenericValue userLogin = session.getUserLogin();
        for (final InvoiceComposite invoice : invoiceCompListModel.getList()) {
            String invoiceId = invoice.getInvoice().getinvoiceId();
            if (UtilValidate.isNotEmpty(invoiceId)) {
                try {

                    String nextStatusId = "INVOICE_READY";//PURCHASE_INVOICE".equals(invoice.getInvoice().getinvoiceTypeId()) ? "INVOICE_IN_PROCESS" : "INVOICE_READY";
                    Map<String, Object> setInvoiceStatusResult = session.getDispatcher().runSync("setInvoiceStatus", UtilMisc.<String, Object>toMap("invoiceId", invoiceId, "statusId", nextStatusId, "userLogin", userLogin));
                    PartyContactMechHelper.outputServiceResult(setInvoiceStatusResult);
                    ActionEvent event = new ActionEvent(this, 1, invoiceId);
                    for (ActionListener listener : listeners) {
                        Debug.logInfo("editCell Action listner", module);
                        listener.actionPerformed(event); // broadcast to all
                    }

                    if (ServiceUtil.isError(setInvoiceStatusResult)) {
                    }
                } catch (Exception exc) {
                    Debug.logWarning("Unable to quick ship test sales order with id [" + invoiceId + "] with error: " + exc.getMessage(), module);
                }
            }
        }
    }
}