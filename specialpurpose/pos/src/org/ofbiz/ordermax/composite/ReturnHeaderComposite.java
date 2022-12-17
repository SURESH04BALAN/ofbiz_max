/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastList;
import mvc.data.Address;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ItemNotFoundException;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.ordermax.entity.Invoice;
import org.ofbiz.ordermax.entity.ReturnHeader;
import org.ofbiz.ordermax.entity.Payment;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.ordermax.entity.ShipmentPackage;
import org.ofbiz.ordermax.entity.ShipmentPackageRouteSeg;
import org.ofbiz.ordermax.entity.ShipmentRouteSegment;
import org.ofbiz.ordermax.entity.ShipmentStatus;
import org.ofbiz.ordermax.orderbase.returns.ReturnItemComposite;

/**
 *
 * @author siranjeev
 */
public class ReturnHeaderComposite {

    public static final String module = ReturnHeaderComposite.class.getName();

    public static final String PROP_PARTYID = "PROP_PARTYID";
    public static final String PROP_DELIVERYADDRESS = "PROP_DELIVERYADDRESS";

    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
//    protected ArrayList<OrderItemComposite> itemsList = new ArrayList<OrderItemComposite>();
    private String deliveryAddress;
    private boolean printOrder = true;
    private Order returnOrder;
    private Invoice returnInvoice;
    private Payment returnPayment;

//    private String ownerPartyId = PosProductHelper.organizationPartyId;
//    private String destinationFacilityId = null;
    private Map<String, String> contactMap = new HashMap<String, String>();
    static public String DELIVERY_LOCATION = "SHIPPING_LOCATION";
    static public String BILLING_LOCATION = "BILLING_LOCATION";
    static public String PHONE_BILLING = "PHONE_BILLING";
    static public String BILLING_EMAIL = "BILLING_EMAIL";
    static public String DELIVERY_PHONE = "PHONE_SHIPPING";
    static public String PRIMARY_PHONE = "PRIMARY_PHONE";
    static public String RETURNTYPE_CUSTOMER = "CUSTOMER_RETURN"; // default returnHeaderType    
    static public String RETURNTYPE_SUPPLIER = "VENDOR_RETURN"; // default returnHeaderType        
    public static final String PROP_PRINTORDER = "PROP_PRINTORDER";
    protected String returnHeaderType = RETURNTYPE_CUSTOMER;
    String shippingContactMechId = null;

    ReturnHeader returnHeader = new ReturnHeader();
    Financials financials = new Financials();
    private OrderContactList orderContactList;
    private OrderItemShipGroupList orderItemAndShipGrpList;
    private OrderItemIssuancesList orderItemIssuancesList;
    private OrderItemPriceInfosList orderItemPriceInfosList;
    private OrderItemShipGrpInvResList orderItemShipGrpInvResList;
    private OrderItemsList orderItemsList;
    private ListAdapterListModel<ReturnItemComposite> orderReturnItemsList = null;
    private OrderStatusList orderStatusesList;
    private OrderRolesList orderRolesList;

    public ReturnHeader getReturnHeader() {
        return returnHeader;
    }

    public boolean isOrderItemExists(String orderId, String orderItemSeqId) {
        for (ReturnItemComposite rtiComp : orderReturnItemsList.getList()) {
            if (rtiComp.getReturnItem().getorderId().equals(orderId)
                    && rtiComp.getReturnItem().getorderItemSeqId().equals(orderItemSeqId)) {
                return true;
            }
        }
        return false;
    }

    public String getOwnerPartyId() {
        return XuiContainer.getSession().getCompanyPartyId();
    }

    public void setReturnHeader(ReturnHeader returnHeader) {
        this.returnHeader = returnHeader;
    }

    public Financials getFinancials() {
        return financials;
    }

    public void setFinancials(Financials financials) {
        this.financials = financials;
    }

    public OrderRolesList getOrderRolesList() {
        return orderRolesList;
    }

    public void setOrderRolesList(OrderRolesList orderRolesList) {
        this.orderRolesList = orderRolesList;
    }

    public OrderRoleComposite getBillToAccount() {
        return orderRolesList.getBillToParty();
    }

    public OrderRoleComposite getBillFromAccount() {
        return orderRolesList.getBillFromParty();
    }

    public String getShippingContactMechId() {
        return shippingContactMechId;
    }

    public void setShippingContactMechId(String shippingContactMechId) {
        this.shippingContactMechId = shippingContactMechId;
    }

    public boolean isValid() {
        boolean isvalid = true;

        if (shippingContactMechId == null) {
            shippingContactMechId = getContactMechId(DELIVERY_LOCATION);
            if (shippingContactMechId == null) {
                shippingContactMechId = getAValidContactMech();
            }
        }

        if (shippingContactMechId == null) {
            isvalid = false;
        }
        return isvalid;
    }

    public void setValidContactMechId() {
        if (shippingContactMechId == null) {
            shippingContactMechId = getContactMechId(DELIVERY_LOCATION);
            if (shippingContactMechId == null) {
                shippingContactMechId = getAValidContactMech();
            }
        }
    }

    /**
     * Sets the order type.
     */
    public void setReturnHeaderType(String returnHeaderType) {
        this.returnHeaderType = returnHeaderType;
    }

    /**
     * Returns the order type.
     */
    public String getReturnHeaderType() {
        return this.returnHeaderType;
    }

    public void addContactMechId(String mechPurposeId, String mechId) {
        if (mechPurposeId != null && mechId != null) {
            if (mechPurposeId.isEmpty() == false && mechId.isEmpty() == false) {
                contactMap.put(mechPurposeId, mechId);
            }
        }
    }

    public String getContactMechId(String mechPurposeId) {
        String conactMechId = null;
        if (contactMap.containsKey(mechPurposeId)) {
            conactMechId = contactMap.get(mechPurposeId);
        }
        return conactMechId;
    }

    public String getDestinationFacilityId() {
        return this.returnHeader.getdestinationFacilityId();
    }

    public void setDestinationFacilityId(String destinationFacilityId) {
        Debug.logInfo("Order Header set Facility Id: " + destinationFacilityId, module);
        this.returnHeader.setdestinationFacilityId(destinationFacilityId);
    }

    //OrderTransaction orderTransaction = new OrderTransaction(XuiContainer.getSession());
    public void setupShoppingCartItems() {
        if (shopingCart != null) {
            shopingCart.setFacilityId(this.returnHeader.getdestinationFacilityId());
        }
        Debug.logInfo("Order Header set Facility Id: " + shopingCart.getFacilityId(), module);
        if (orderContactList != null) {
            for (OrderContact pc : orderContactList.getList()) {
                shopingCart.addContactMech(pc.orderContactMech.getcontactMechPurposeTypeId(),
                        pc.orderContactMech.getcontactMechId());
            }
        }

//        shopingCart.setOrderName(returnHeader.getOrderName());
        shopingCart.setExternalId(returnHeader.getsupplierRmaId());
//        shopingCart.setPoNumber(returnHeader.getP);        
    }

    public Set<String> getPaymentIds() {
        Set<String> paymentIds = new HashSet<String>();

        for (ReturnItemComposite itemComp : orderReturnItemsList.getList()) {
            try {
                GenericValue response = itemComp.getReturnItem().getGenericValueObj().getRelatedOne("ReturnItemResponse");

                if (response != null) {
                    String paymentId = response.getString("paymentId");
                    if (paymentId != null) {
                        paymentIds.add(paymentId);
                    }
                    Debug.logInfo("paymentId: " + paymentId, module);
                }

            } catch (GenericEntityException ex) {
                Logger.getLogger(ReturnHeaderComposite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return paymentIds;
    }

    ShoppingCart shopingCart = null;

    //   public Order(String returnHeaderType) {
    //       orderTransaction.getCart().setreturnHeaderType(returnHeaderType);
    //   }
    public ReturnHeaderComposite(String returnHeaderType) {
        this.returnHeaderType = returnHeaderType;
    }

    public ReturnHeaderComposite(GenericValue val) {
        returnHeader = new ReturnHeader(val);
        this.returnHeaderType = returnHeader.getreturnHeaderTypeId();
    }

    public void addOrderItem(OrderItemComposite orderMaxItem) throws CartItemModifyException, ItemNotFoundException {
//        orderItemsList.add(orderMaxItem);
/*
         shopingCart.addItem(orderMaxItem.getOrderItem().getproductId(), 
         orderMaxItem.getOrderItem().getquantity());

         ShoppingCartItem item = orderTransaction.getCartItem(orderMaxItem.getOrderItem().getproductId());
         if (item != null) {
         orderTransaction.modifyPrice(orderMaxItem.getOrderItem().getproductId(), orderMaxItem.getOrderItem().getunitPrice());
         }

         ShoppingCartItem updatedItem = orderTransaction.getCartItem(orderMaxItem.getOrderItem().getproductId());
         if (updatedItem != null) {
         orderMaxItem.getOrderItem().setunitListPrice(updatedItem.getListPrice());
         }
         */
    }

    public void removeOrderItem(OrderItemComposite order) {
        orderItemsList.remove(order);
    }

    public void voidOrderItem(OrderItemComposite orderItem, Delegator delegator) {

        /*         ShoppingCartItem delItem = orderTransaction.getCartItem(orderItem.getOrderItem().getproductId());
         if (delItem != null) {
         try {
         Debug.logInfo("voidSelectedOrderItems : " + orderItem.getOrderItem().getproductId(), module);
         orderTransaction.voidItem(orderItem.getOrderItem().getproductId());
         removeOrderItem(orderItem);
         } catch (CartItemModifyException ex) {
         Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         if (orderItem != null) {
         try {
         if (orderItem.getOrderItem().getGenericValueObj() != null) {
         Debug.logInfo("order Id : " + orderItem.getOrderItem().getorderId(), module);
         Debug.logInfo("getorderItemSeqId Id : " + orderItem.getOrderItem().getorderItemSeqId(), module);
         Debug.logInfo("getstatusId Id : " + orderItem.getOrderItem().getstatusId(), module);
         orderItem.getOrderItem().setquantity(BigDecimal.ZERO);
         orderItem.getOrderItem().setunitPrice(BigDecimal.ZERO);
         orderItem.getOrderItem().setstatusId("ITEM_CANCELLED");

         orderItem.getOrderItem().getGenericValue();
         delegator.store(orderItem.getOrderItem().getGenericValueObj());
         }
         } catch (GenericEntityException ex) {
         Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         */
    }

    public boolean isOrderItemExist(Key orderKey) {
        Debug.logInfo(orderKey._id + "  orderKey name " + orderKey._name, "orderId");
        for (OrderItemComposite entry : orderItemsList.getList()) {
//            Debug.logInfo(entry._id + "  entry name " + entry._name, "orderId");
            if (orderKey._id.equals(entry.getOrderItem().getproductId())) {
                return true;
            }
        }
        return false;
    }

    public List<OrderItemComposite> getOrderItem(String productId) {
        List<OrderItemComposite> list = new ArrayList<OrderItemComposite>();
        for (OrderItemComposite entry : orderItemsList.getList()) {
//            Debug.logInfo(entry._id + "  entry name " + entry._name, "orderId");
            if (productId.equals(entry.getOrderItem().getproductId())) {
                list.add(entry);
            }
        }
        return list;
    }

    /**
     * @return the deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * @return the printOrder
     */
    public boolean isPrintOrder() {
        return printOrder;
    }

    /**
     * @param printOrder the printOrder to set
     */
    public void setPrintOrder(boolean printOrder) {
        boolean oldPrintOrder = printOrder;
        this.printOrder = printOrder;
        propertyChangeSupport.firePropertyChange(PROP_PRINTORDER, oldPrintOrder, printOrder);
    }

    public String getAValidContactMech() {

        for (Entry<String, String> entryContacts : contactMap.entrySet()) {
            if (entryContacts.getValue() == null) {
                return entryContacts.getValue();
            }
        }
        return null;
    }
//    private List<OrderItemShipGroup> orderItemShipGroupList = FastList.newInstance();
    public List<ShipmentRouteSegment> shipmentRouteSegmentList = FastList.newInstance();
    public List<ShipmentPackage> shipmentPackageList = FastList.newInstance();
    public List<ShipmentPackageRouteSeg> shipmentPackageRouteSegList = FastList.newInstance();
    public List<ShipmentStatus> shipmentStatusList = FastList.newInstance();
    private Shipment shipment = null;

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
    public String shipGroupSeqId = null;
    //   GenericValue orderItem = delegator.makeValue("OrderItem", orderItemValues);
    //delegator.setNextSubSeqId(orderItem, "orderItemSeqId", ORDER_ITEM_PADDING, 1);            


    public String getPartyId() {
        if (RETURNTYPE_CUSTOMER.equals(returnHeader.getreturnHeaderTypeId())) {
            return getBillFromAccount().getParty().getParty().getpartyId();
        } else {
            return getBillToAccount().getParty().getParty().getpartyId();
        }
    }

    public String getPartyName() {

        if (RETURNTYPE_CUSTOMER.equals(returnHeader.getreturnHeaderTypeId())) {
            return getBillFromAccount().getParty().getPartyGroup().getgroupName();
        } else {
            return getBillToAccount().getParty().getPartyGroup().getgroupName();
        }

    }

    public int size() {
        return orderItemsList.getSize();
    }

    public OrderItemComposite getItem(int i) {
        return orderItemsList.getElementAt(i);
    }

    public OrderItemComposite getOrderItemByProdId(String id) {
        for (OrderItemComposite item : orderItemsList.getList()) {
            if (item.getOrderItem().getproductId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public BigDecimal getSubTotal() {
        return shopingCart.getSubTotal();
    }

    public BigDecimal getTotalShipping() {
        return shopingCart.getTotalShipping();
    }

    public BigDecimal getTaxTotal() {
        return shopingCart.getTotalSalesTax();
    }

    public BigDecimal getGrandTotal() {
        BigDecimal totalValue = BigDecimal.ZERO;
         for( ReturnItemComposite itemComposite: orderReturnItemsList.getList()){
             if(itemComposite.getReturnItem().getreturnPrice()!=null && itemComposite.getReturnItem().getreturnQuantity()!=null){
                totalValue = totalValue.add(itemComposite.getReturnItem().getreturnPrice().multiply(itemComposite.getReturnItem().getreturnQuantity()));
             }
         }
        
        return totalValue;
    }

    public int getNumberOfPayments() {
        return shopingCart.selectedPayments();
    }

    public BigDecimal getPaymentTotal() {
        return shopingCart.getPaymentTotal();
    }

    public BigDecimal getTotalDue() {
        BigDecimal grandTotal = this.getGrandTotal();
        BigDecimal paymentAmt = this.getPaymentTotal();
        return grandTotal.subtract(paymentAmt);

    }

    public Set<String> getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(Set<String> invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    private Set<String> invoiceIds = new HashSet<String>();

    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OrderContactList getOrderContactList() {
        return orderContactList;
    }

    public void setOrderContactList(OrderContactList orderContactList) {
        this.orderContactList = orderContactList;
    }

    public OrderItemShipGroupList getOrderItemShipGroupList() {
        return orderItemAndShipGrpList;
    }

    public void setOrderItemShipGroupList(OrderItemShipGroupList orderItemAndShipGrpList) {
        this.orderItemAndShipGrpList = orderItemAndShipGrpList;
    }

    public OrderItemIssuancesList getOrderItemIssuancesList() {
        return orderItemIssuancesList;
    }

    public void setOrderItemIssuancesList(OrderItemIssuancesList orderItemIssuancesList) {
        this.orderItemIssuancesList = orderItemIssuancesList;
    }

    public OrderItemPriceInfosList getOrderItemPriceInfosList() {
        return orderItemPriceInfosList;
    }

    public void setOrderItemPriceInfosList(OrderItemPriceInfosList orderItemPriceInfosList) {
        this.orderItemPriceInfosList = orderItemPriceInfosList;
    }

    public OrderItemShipGrpInvResList getOrderItemShipGrpInvResList() {
        return orderItemShipGrpInvResList;
    }

    public void setOrderItemShipGrpInvResList(OrderItemShipGrpInvResList orderItemShipGrpInvResList) {
        this.orderItemShipGrpInvResList = orderItemShipGrpInvResList;
    }

    public OrderItemsList getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(OrderItemsList orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public ListAdapterListModel<ReturnItemComposite> getOrderReturnItemsList() {
        return orderReturnItemsList;
    }

    public void setOrderReturnItemsList(ListAdapterListModel<ReturnItemComposite> orderReturnItemsList) {
        this.orderReturnItemsList = orderReturnItemsList;
    }

    public OrderStatusList getOrderStatusList() {
        return orderStatusesList;
    }

    public void setOrderStatusList(OrderStatusList orderStatusesList) {
        this.orderStatusesList = orderStatusesList;
    }

    /*    public List<OrderItemShipGroup> getOrderItemShipGroupList() {
     return orderItemShipGroupList;
     }

     public void setOrderItemShipGroupList(List<OrderItemShipGroup> orderItemShipGroupList) {
     this.orderItemShipGroupList = orderItemShipGroupList;
     }*/
    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    Boolean selected = new Boolean(true);

    public Order getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(Order returnOrder) {
        this.returnOrder = returnOrder;
    }

    public Invoice getReturnInvoice() {
        return returnInvoice;
    }

    public void setReturnInvoice(Invoice returnInvoice) {
        this.returnInvoice = returnInvoice;
    }

    public Payment getReturnPayment() {
        return returnPayment;
    }

    public void setReturnPayment(Payment returnPayment) {
        this.returnPayment = returnPayment;
    }

}
