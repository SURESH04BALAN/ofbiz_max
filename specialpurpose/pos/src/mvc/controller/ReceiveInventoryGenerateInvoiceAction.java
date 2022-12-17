/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class ReceiveInventoryGenerateInvoiceAction extends AbstractAction {
    public static final String module = ReceiveInventoryGenerateInvoiceAction.class.getName();
    /**
     *
     */
    private static final long serialVersionUID = 2914235274602131249L;
    private ListAdapterListModel<?> listModel;

    public ReceiveInventoryGenerateInvoiceAction(ListAdapterListModel<?> listModel, Order order, XuiSession session) {
        this.listModel = listModel;
        this.order = order;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Clear";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Debug.logInfo("ReceiveInventoryAlllAction::actionPerformed: ", "Module");
        generateInvoice();
        listModel.fireListDataChanged();
    }

    XuiSession session;
    Order order;

    public void generateInvoice() {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        String orderId = order.getOrderId();
        String shipGroupSeqId = order.shipGroupSeqId;
        GenericValue facility = PosProductHelper.getFacility(order.getFacilityId(), session.getDelegator());
        
        Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ start btnGnerateInvoiceActionPerformed:", module);
        if (UtilValidate.isNotEmpty(orderId)) {
            try {
                
                resultMap = dispatcher.runSync("createInvoiceForOrderAllItems", UtilMisc.toMap("orderId", order.getOrderId(), "userLogin", userLogin));
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ finsh   createInvoiceForOrderAllItems invoiceId:" + resultMap.get("invoiceId"), module);

//                String nextStatusId = "PURCHASE_INVOICE".equals(invoiceType) ? "INVOICE_IN_PROCESS" : "INVOICE_READY";
//                Map<String, Object> setInvoiceStatusResult = dispatcher.runSync("setInvoiceStatus", UtilMisc.<String, Object>toMap("invoiceId", invoiceId, "statusId", nextStatusId, "userLogin", userLogin));
//                if (ServiceUtil.isError(setInvoiceStatusResult)) {
//                }
            } catch (Exception exc) {
                Debug.logWarning("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
            }
        }
        
        Debug.logInfo("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END  btnGnerateInvoiceActionPerformed:", module);

    }

}
