/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.util.List;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductInventoryPojo;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ItemNotFoundException;
import org.ofbiz.ordermax.celleditors.ButtonColumnCellEditor;
import org.ofbiz.ordermax.screens.ContactMechPanelMain;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.entity.OrderItemShipGroup;
import org.ofbiz.ordermax.entity.Shipment;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastList;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;

/**
 *
 * @author administrator
 */
public class OrderEntryHelper {

    static String module = OrderEntryHelper.class.getName();

    static public GenericValue createShipmentItem(String shipmentId,
            String productId, BigDecimal quantity,
            String shipmentContentDescription,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = null;

        genericVal = delegator.makeValue("ShipmentItem");
        delegator.setNextSubSeqId(genericVal, "shipmentItemSeqId", 4, 1);
        genericVal.set("shipmentId", shipmentId);
        genericVal.set("shipmentContentDescription", shipmentContentDescription);
        genericVal.set("productId", productId);
        genericVal.set("quantity", quantity);
        genericVal.create();

        return genericVal;
    }

    public static GenericValue updateShipmentItem(String shipmentId, String shipmentItemSeqId,
            String productId, BigDecimal quantity,
            String shipmentContentDescription,
            Delegator delegator) throws GenericEntityException {

        GenericValue orderItem = getShipmentItem(shipmentId, shipmentItemSeqId, productId, delegator);

        orderItem.set("quantity", quantity);
        orderItem.set("shipmentContentDescription", shipmentContentDescription);

        return orderItem;
    }

    static public GenericValue getShipmentItem(String shipmentId,
            String shipmentItemSeqId, String productId,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = null;
        genericVal = delegator.findByPrimaryKey("ShipmentItem", UtilMisc
                .toMap("shipmentId", shipmentId,
                        "shipmentItemSeqId", shipmentItemSeqId,
                        "productId", productId));

        return genericVal;
    }

    static public GenericValue getShipmentItem(String shipmentId, String productId,
            Delegator delegator) throws GenericEntityException {

        GenericValue genericVal = null;
        genericVal = delegator.findByPrimaryKey("ShipmentItem", UtilMisc
                .toMap("shipmentId", shipmentId,
                        "productId", productId));

        return genericVal;
    }
/*
    public static Order loadOrderFromPersistance(final String orderId, final Delegator delegator) {

        GenericValue orderGenVal = OrderReadHelper.getOrderHeader(delegator, orderId);
        OrderReadHelper orderReadHelper = new OrderReadHelper(orderGenVal);

        Order orderHeader = new Order(orderGenVal, orderReadHelper.getOrderTypeId());
        orderHeader.setDestinationFacilityId(orderGenVal.getString("originFacilityId"));

        orderHeader.getFinancials().setOrderTotal(orderReadHelper.getOrderGrandTotal());
        orderHeader.setOrderId(orderId);
        GenericValue orderParty = orderReadHelper.getSupplierAgent();
        Debug.logInfo("orderParty.getString(partyId): " + orderParty.getString("partyId"), "module");
        if (orderParty != null) {
            Debug.logInfo("orderParty.getString(partyId): " + orderParty.getString("partyId"), "module");
//            orderHeader.setPartyId(orderParty.getString("partyId"));
        }
        
        List<GenericValue> orderItemBillings;
        try {
            orderItemBillings = orderGenVal.getRelated("OrderItemBilling");
            Set<String> invoiceIds = new HashSet<String>();
            for (Iterator<GenericValue> iter = orderItemBillings.iterator(); iter.hasNext();) {
                GenericValue orderItemBilling = iter.next();
                invoiceIds.add(orderItemBilling.getString("invoiceId"));
                Debug.logInfo("invoiceId : " +  orderItemBilling.getString("invoiceId"),module);
//                break;
            }
            Debug.logInfo("invoiceId s list size : " +  orderItemBillings.size(),module);
            orderHeader.setInvoiceIds(invoiceIds);
        } catch (GenericEntityException ex) {
            Logger.getLogger(OrderEntryHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<GenericValue> shipGroups = orderReadHelper.getOrderItemShipGroups();
        for (GenericValue shipGroup : shipGroups) {
            orderHeader.getOrderItemShipGroupList().add(new OrderItemShipGroup(shipGroup));
//            orderHeader.shipGroupSeqId = orderHeader.getOrderItemShipGroupList().(0).getshipGroupSeqId();
        }

        //load shipment
        List<GenericValue> shipments = PosProductHelper.getGenericValueLists("Shipment", "primaryOrderId", orderId, delegator);
        for (GenericValue shipment : shipments) {
            orderHeader.shipment = new Shipment(shipment);
            break;
        }

        Debug.logInfo("orderHeader.getPartyId(): " + orderHeader.getPartyId(), "module");
        orderHeader.setExternalId(orderReadHelper.getOrderName());
        List<GenericValue> orderItems = orderReadHelper.getOrderItems();
//        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
        for (GenericValue orderItem : orderItems) {
            String orderStatusId = orderItem.getString("statusId");
            if (orderStatusId.equals("ITEM_CANCELLED") == false) {
//                Map<String, Object> itemInfo = orderReadHelper.getItemInfoMap(orderItem);
//                SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, facilityIdCombo.getSelectedItemId(), session);
                OrderItemComposite productItem = new OrderItemComposite(orderItem);//.getString("productId"), orderItem.getString("itemDescription"));
//            productItem.setorderId(orderItem.getString("orderId"));
                Debug.logInfo("orderItem.getString(\"orderItemSeqId\"): " + orderItem.getString("orderItemSeqId"), "module");
//            productItem.setorderItemSeqId(orderItem.getString("orderItemSeqId"));
//            productItem.setquantity(orderItem.getBigDecimal("quantity"));
//            productItem.setunitPrice(orderItem.getBigDecimal("unitPrice"));
//                productItem.saleLineTotal = orderReadHelper.getOrderItemTotal(orderItem);
//                productItem.saleGstTotal = orderReadHelper.getOrderItemTax(orderItem);

//                productItem.listPrice 
                try {
                    orderHeader.addOrderItem(productItem);
                } catch (CartItemModifyException e) {
                    Debug.logError(e, module);
                } catch (ItemNotFoundException e1) {
                    Debug.logError(e1, module);
                }
            }
        }

        String purposeId = Order.DELIVERY_LOCATION;
        List<GenericValue> orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
        for (GenericValue genVal : orderContachs) {
            orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
            Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
        }

        purposeId = Order.BILLING_LOCATION;
        orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
        for (GenericValue genVal : orderContachs) {
            orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
            Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
        }
    
        purposeId = Order.DELIVERY_PHONE;
        orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
        for (GenericValue genVal : orderContachs) {
            orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
            Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
        }

        purposeId = Order.BILLING_EMAIL;
        orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
        for (GenericValue genVal : orderContachs) {
            orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
            Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
        }
        return orderHeader;
    }
*/
    static public List<GenericValue> getOrderRoleList(final String partyId, final String externalId, final Delegator delegator) {
        List<GenericValue> orderList = null;

        try {
            List<EntityExpr> exprs = FastList.newInstance();

            exprs.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));
            exprs.add(EntityCondition.makeCondition("externalId", EntityOperator.EQUALS, externalId));

            orderList = delegator.findList("OrderHeaderAndRoles", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        return orderList;

    }

    static public void loadPriceAndQtyData(Order orderHeader, XuiSession session) {

        for (int i = 0; i < orderHeader.size(); i++) {
            OrderItemComposite orderItem = orderHeader.getItem(i);
            SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(orderItem.getOrderItem().getproductId(), orderHeader.getFacilityId(), session);
            orderItem.lastSalePrice = data.getLastPrice();
            orderItem.listPrice = data.getDefaultPrice();
            orderItem.getOrderItem().setunitListPrice(data.getDefaultPrice());
            orderItem.setQuantyOnHand(data.getAvailableToPromiseTotal());

        }

    }
}
