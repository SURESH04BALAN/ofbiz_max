/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.InventoryItem;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.ordermax.entity.ShipmentReceipt;

/**
 *
 * @author siranjeev
 */
public class ShipmentReceiptComposite {

    public OrderItemComposite getOrderItemComposite() {
        return orderItemComposite;
    }

    public void setOrderItemComposite(OrderItemComposite orderItemComposite) {
        this.orderItemComposite = orderItemComposite;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public ShipmentReceipt getShipmentReceipt() {
        return shipmentReceipt;
    }

    public void setShipmentReceipt(ShipmentReceipt shipmentReceipt) {
        this.shipmentReceipt = shipmentReceipt;
    }
    OrderItemComposite orderItemComposite;
    InventoryItem inventoryItem;
    ShipmentReceipt shipmentReceipt;    
    Order order = null;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    Boolean selected = new Boolean(true);
    
}
