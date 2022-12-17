package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import javax.swing.AbstractAction;
import javax.swing.Action;

import javax.swing.KeyStroke;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class ReceiveInventoryRejectAllAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2914235274602131249L;
    private ListAdapterListModel<?> listModel;

    public ReceiveInventoryRejectAllAction(ListAdapterListModel<?> listModel) {
        this.listModel = listModel;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Reject All";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int index=0; index <  listModel.getSize(); ++index) {
            ShipmentReceiptComposite shipmentReceipt = (ShipmentReceiptComposite) listModel.getElementAt(index);
            shipmentReceipt.getShipmentReceipt().setquantityAccepted(BigDecimal.ZERO);
            shipmentReceipt.getShipmentReceipt().setquantityRejected(shipmentReceipt.getOrderItemComposite().getOrderItem().getquantity());
        }
        listModel.fireListDataChanged();
    }
}
