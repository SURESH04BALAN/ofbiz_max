/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.InventoryItem;
import org.ofbiz.ordermax.entity.InventoryItemDetail;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.ordermax.entity.ShipmentReceipt;

/**
 *
 * @author siranjeev
 */
public class ReceiveInventoryComposite {
    
    Order order;
    ShipmentReceiptCompositeList shipmentReceiptCompositeList = new ShipmentReceiptCompositeList();
    Shipment shipment;    

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    Boolean selected = new Boolean(true);

    public ReceiveInventoryComposite() {
    }

    public Order getOrder() {
        return order;
    }


    public void setOrder(Order order) {
        this.order = order;
    }

    public ShipmentReceiptCompositeList getShipmentReceiptCompositeList() {
        return shipmentReceiptCompositeList;
    }

    public void setShipmentReceiptCompositeList(ShipmentReceiptCompositeList shipmentReceipt) {
        this.shipmentReceiptCompositeList = shipmentReceipt;
    }    
    
    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
    
}
