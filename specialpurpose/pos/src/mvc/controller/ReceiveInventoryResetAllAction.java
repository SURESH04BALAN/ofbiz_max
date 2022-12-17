/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;

/**
 *
 * @author siranjeev
 */

public class ReceiveInventoryResetAllAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2914235274602131249L;
    private ListAdapterListModel<?> listModel;

    public ReceiveInventoryResetAllAction(ListAdapterListModel<?> listModel) {
        this.listModel = listModel;
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
        Debug.logInfo("ReceiveInventoryAlllAction::actionPerformed: " , "Module");
        for (int index=0; index <  listModel.getSize(); ++index) {
            ShipmentReceiptComposite shipmentReceipt = (ShipmentReceiptComposite) listModel.getElementAt(index);
            shipmentReceipt.getShipmentReceipt().setquantityAccepted(BigDecimal.ZERO);
            shipmentReceipt.getShipmentReceipt().setquantityRejected(BigDecimal.ZERO);
            Debug.logInfo("after shipmentReceipt.getOrderItemComposite().getOrderItem().getquantity: " + shipmentReceipt.getOrderItemComposite().getOrderItem().getquantity(), "Module");
        }
        listModel.fireListDataChanged();
    }
}
