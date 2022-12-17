/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javolution.util.FastList;
import mvc.data.Address;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ItemNotFoundException;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.ContactMech;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.OrderPaymentPreference;
import org.ofbiz.ordermax.entity.OrderTerm;
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
public class Order extends ShoppingCart {

    public static final String module = Order.class.getName();

    public static final String PROP_PARTYID = "PROP_PARTYID";
    public static final String PROP_DELIVERYADDRESS = "PROP_DELIVERYADDRESS";

    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
//    protected ArrayList<OrderItemComposite> itemsList = new ArrayList<OrderItemComposite>();
    private String deliveryAddress;
    private boolean printOrder = true;

//    private String ownerPartyId = PosProductHelper.organizationPartyId;
//    private String destinationFacilityId = null;
    private Map<String, String> contactMap = new HashMap<String, String>();
    static public String DELIVERY_LOCATION = "SHIPPING_LOCATION";
    static public String BILLING_LOCATION = "BILLING_LOCATION";
    static public String PHONE_BILLING = "PHONE_BILLING";
    static public String BILLING_EMAIL = "BILLING_EMAIL";
    static public String DELIVERY_PHONE = "PHONE_SHIPPING";
    static public String PRIMARY_PHONE = "PRIMARY_PHONE";
    static public String ORDERTYPE_SALESORDER = "SALES_ORDER"; // default orderType    
    static public String ORDERTYPE_PURCHSEORDER = "PURCHASE_ORDER"; // default orderType        
    public static final String PROP_PRINTORDER = "PROP_PRINTORDER";
    //protected String orderType = ORDERTYPE_SALESORDER;
    String shippingContactMechId = null;

    public OrderHeader orderHeader = null;//new OrderHeader();
    Financials financials = new Financials();
//    private OrderContactMechCompositeList orderContactList;
    private OrderItemShipGroupList orderItemAndShipGrpList;
    private OrderItemIssuancesList orderItemIssuancesList;
    private OrderItemPriceInfosList orderItemPriceInfosList;
    private OrderItemShipGrpInvResList orderItemShipGrpInvResList;
    private OrderItemsList orderItemsList;
    private OrderReturnItemsList orderReturnItemsList;
    private OrderStatusList orderStatusesList;
    private OrderRolesList orderRolesList;
    private ListAdapterListModel<OrderTerm> orderTermList = null;
    private List<ReturnItemComposite> returnableItems;
    private List<OrderPaymentPreference> orderPaymentPreferences = null;

    public List<OrderPaymentPreference> getOrderPaymentPreferences() {
        return orderPaymentPreferences;
    }

    public void setOrderPaymentPreferences(List<OrderPaymentPreference> orderPaymentPreferences) {
        this.orderPaymentPreferences = orderPaymentPreferences;
    }

    public List<ReturnItemComposite> getReturnableItems() {
        return returnableItems;
    }

    public void setReturnableItems(List<ReturnItemComposite> returnableItems) {
        this.returnableItems = returnableItems;
    }

    public ListAdapterListModel<OrderTerm> getOrderTermList() {
        return orderTermList;
    }

    public void setOrderTermList(ListAdapterListModel<OrderTerm> orderTermList) {
        this.orderTermList = orderTermList;
    }

    /*    public OrderHeader getOrderHeader() {
     return orderHeader;
     }
     */
    public String getOwnerPartyId() {
        return XuiContainer.getSession().getCompanyPartyId();
    }

    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }

    public Financials getFinancials() {
        return financials;
    }

    public void setFinancials(Financials orderHeader) {
        this.financials = orderHeader;
    }

    public OrderRolesList getOrderRolesList() {
        return orderRolesList;
    }

    public void setOrderRolesList(OrderRolesList orderRolesList) {
        this.orderRolesList = orderRolesList;
    }
    /*
     public Account getBillingAccount() {
     OrderRoleComposite orderRoleComposite = orderRolesList.getBillToParty();
     if (orderRoleComposite != null) {
     return orderRoleComposite.getParty();
     } else {
     return null;
     }

     }
     */
    OrderRoleComposite billToAccount = null;

    public OrderRoleComposite getBillToAccount() {
        return billToAccount;
    }

    public void setBillToAccount(OrderRoleComposite billToAccount) {
        this.billToAccount = billToAccount;
    }

    OrderRoleComposite billFromAccount = null;

    public OrderRoleComposite getBillFromAccount() {
        return billFromAccount;
    }

    public void setBillFromAccount(OrderRoleComposite billFromAccount) {
        this.billFromAccount = billFromAccount;
    }
    /*
     public String getShippingContactMechId() {
     return shippingContactMechId;
     }

     public void setShippingContactMechId(String shippingContactMechId) {
     this.shippingContactMechId = shippingContactMechId;
     }
     */

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
    /*
     public void setOrderType(String orderType) {
     this.orderType = orderType;
     }

     public String getOrderType() {
     return this.orderType;
     }
     */
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
    /*
     public String getDestinationFacilityId() {
     return this.orderHeader.getOriginFacilityId();
     }

     public void setDestinationFacilityId(String destinationFacilityId) {
     Debug.logInfo("Order Header set Facility Id: " + destinationFacilityId, module);
     this.orderHeader.setOriginFacilityId(destinationFacilityId);
     }
     */

    //OrderTransaction orderTransaction = new OrderTransaction(XuiContainer.getSession());
    public void setupShoppingCartItems() {
        //      if (shopingCart != null) {
//            setFacilityId(this.orderHeader.getOriginFacilityId());
//        }
//        OrderContactMechCompositeList
        Debug.logInfo("Order Header set Facility Id: " + getFacilityId(), module);
        /*       if (orderContactList != null) {
         for (OrderContactMechComposite pc : orderContactList.getList()) {
         addContactMech(pc.getPartyContactMechPurposeType().getcontactMechPurposeTypeId(),
         pc.getOrderContactMech().getcontactMechId());
         }
         }
         */
//        setOrderName(orderHeader.getOrderName());
//        setExternalId(orderHeader.getExternalId());
//        shopingCart.setPoNumber(orderHeader.getP);        
    }

    public ShoppingCart getShopingCart() {
        return this;
    }

    private OrderReadHelper orderReadHelper;

    //public void setShopingCart(ShoppingCart shopingCart) {
    //    this.shopingCart = shopingCart;
    //}
    //ShoppingCart shopingCart = null;
    //   public Order(String orderType) {
    //       orderTransaction.getCart().setOrderType(orderType);
    //   }
    /*public Order(String orderType) {
     this.orderType = orderType;
     }

     public Order(GenericValue val, String orderType) {
     orderHeader = new OrderHeader(val);
     this.orderType = orderType;
     }*/
    public Order(ShoppingCart shoppingCart) {
        super(shoppingCart);
        this.setOrderStatusId(shoppingCart.getOrderStatusId());
        this.setOrderDate(shoppingCart.getOrderDate());
        this.setOrderType(shoppingCart.getOrderType());
        this.setFacilityId(shoppingCart.getFacilityId());
        this.setProductStoreId(shoppingCart.getProductStoreId());
        this.setOrderDate(shoppingCart.getOrderDate());
    }

    public Order(Delegator delegator, String productStoreId, Locale locale, String currencyUom) {
        super(delegator, productStoreId, null, locale, currencyUom);

    }

    public Order(Delegator delegator, String productStoreId, String webSiteId, Locale locale, String currencyUom, String billToCustomerPartyId, String billFromVendorPartyId) {
        super(delegator, productStoreId, webSiteId, locale, currencyUom, billToCustomerPartyId, billFromVendorPartyId);
    }

    public void addOrderItem(OrderItemComposite orderMaxItem) throws CartItemModifyException, ItemNotFoundException {
//        orderItemsList.add(orderMaxItem);
/*
        this.addItem(orderMaxItem.getOrderItem().getproductId(), orderMaxItem.getOrderItem().getquantity());

        ShoppingCartItem item = this.getCartItem(orderMaxItem.getOrderItem().getproductId());
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
     * @param deliveryAddress the deliveryAddress to set
     */
    public void setDeliveryAddress(String deliveryAddress) {
        java.lang.String oldDeliveryAddress = deliveryAddress;
        this.deliveryAddress = deliveryAddress;
        propertyChangeSupport.firePropertyChange(PROP_DELIVERYADDRESS, oldDeliveryAddress, deliveryAddress);
    }

    /**
     * @return the orderId
     */
    //   public String getOrderId() {
    //       return orderId;
    //   }
    /**
     * @param orderId the orderId to set
     */
    //   public void setOrderId(String orderId) {
    //       java.lang.String oldOrderId = orderId;
    //       this.orderId = orderId;
    //       propertyChangeSupport.firePropertyChange(PROP_ORDERID, oldOrderId, orderId);
    //   }
    /**
     * @return the orderNumber
     */
//    public String getOrderNumber() {
//        return orderNumber;
//    }
    /**
     * @param orderNumber the orderNumber to set
     */
//    public void setOrderNumber(String orderNumber) {
//        java.lang.String oldOrderNumber = orderNumber;
//        this.orderNumber = orderNumber;
//        propertyChangeSupport.firePropertyChange(PROP_ORDERNUMBER, oldOrderNumber, orderNumber);
//    }
    /**
     * @return the orderRefernce
     */
//    public String getOrderRefernce() {
//        return orderRefernce;
//    }
    /**
     * @param orderRefernce the orderRefernce to set
     */
//    public void setOrderRefernce(String orderRefernce) {
//        java.lang.String oldOrderRefernce = orderRefernce;
//        this.orderRefernce = orderRefernce;
//        propertyChangeSupport.firePropertyChange(PROP_ORDERREFERNCE, oldOrderRefernce, orderRefernce);
//    }
    /**
     * @return the orderDate
     */
    /*    public String getOrderDate() {
     return orderDate;
     }
     */
    /**
     * @param orderDate the orderDate to set
     */
    /*    public void setOrderDate(String orderDate) {
     java.lang.String oldOrderDate = orderDate;
     this.orderDate = orderDate;
     propertyChangeSupport.firePropertyChange(PROP_ORDERDATE, oldOrderDate, orderDate);
     }
     */
    /**
     * @return the orderDeliveryDate
     */
//    public String getOrderDeliveryDate() {
//        return orderDeliveryDate;
//    }
    /**
     * @param orderDeliveryDate the orderDeliveryDate to set
     */
    //   public void setOrderDeliveryDate(String orderDeliveryDate) {
    //       java.lang.String oldOrderDeliveryDate = orderDeliveryDate;
    //       this.orderDeliveryDate = orderDeliveryDate;
    //       propertyChangeSupport.firePropertyChange(PROP_ORDERDELIVERYDATE, oldOrderDeliveryDate, orderDeliveryDate);
    //   }
    /**
     * @return the enteredBy
     */
//    public String getEnteredBy() {
//        return enteredBy;
//    }
    /**
     * @param enteredBy the enteredBy to set
     */
//    public void setEnteredBy(String enteredBy) {
//        java.lang.String oldEnteredBy = enteredBy;
//        this.enteredBy = enteredBy;
//        propertyChangeSupport.firePropertyChange(PROP_ENTEREDBY, oldEnteredBy, enteredBy);
//    }
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
    public Shipment shipment = null;
    public String shipGroupSeqId = null;
    //   GenericValue orderItem = delegator.makeValue("OrderItem", orderItemValues);
    //delegator.setNextSubSeqId(orderItem, "orderItemSeqId", ORDER_ITEM_PADDING, 1);            

    public void createShipment(Delegator delegator) throws GenericEntityException {
        // create ship group
//        String shipGroupSeqId = shipGroupSeqId;
/*        String shipmentMethodTypeId = "NO_SHIPPING";
         String carrierPartyId = "_NA_";
         String carrierRoleTypeId = "CARRIER";
         String orderId = this.orderHeader.getOrderId();
         String contactMechId = getContactMechId(DELIVERY_LOCATION);
         //        if (orderItemShipGroupList.size() > 0) {
         //            shipGroupSeqId = orderItemShipGroupList.get(0).getshipGroupSeqId();
         //        }

         String maySplit = "N";
         String isGift = "N";
         Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();

         GenericValue val = PosProductHelper.createOrderItemShipGroup(orderId,
         shipGroupSeqId, shipmentMethodTypeId, carrierPartyId,
         carrierRoleTypeId, contactMechId, maySplit, isGift,
         estimatedDeliveryDate, delegator);
         shipGroupSeqId = val.getString("shipGroupSeqId");

         // create the shipment
         String shipmentId = delegator.getNextSeqId("Shipment");
         String shipmentTypeId = "PURCHASE_SHIPMENT";
         BigDecimal estimatedShipCost = new BigDecimal("0.00");
         String destinationContactMechId = contactMechId;
         String destinationTelecomNumberId = getContactMechId(DELIVERY_PHONE);;

         GenericValue val3 = PosProductHelper.createShipment(shipmentId, shipmentTypeId,
         this.orderHeader.getStatusId(), orderId, shipGroupSeqId, estimatedShipCost,
         this.orderHeader.getOriginFacilityId(), destinationContactMechId,
         destinationTelecomNumberId, this.partyId, delegator);

         shipment = new Shipment(val3);
         /*
         String shipmentRouteSegmentId = "00001";
         carrierPartyId = "_NA_";
         shipmentMethodTypeId = "NO_SHIPPING";
         String carrierServiceStatusId = "SHRSCS_NOT_STARTED";

         GenericValue val6 = PosProductHelper.createShipmentRouteSegment(shipmentId,
         shipmentRouteSegmentId, destinationFacilityId,
         destinationContactMechId, destinationTelecomNumberId, carrierPartyId,
         shipmentMethodTypeId, carrierServiceStatusId, delegator);
         shipmentRouteSegmentId = val6.getString("shipmentRouteSegmentId");

         String shipmentPackageSeqId = "00001";
         Timestamp dateCreated = UtilDateTime.nowTimestamp();
         GenericValue val7 = PosProductHelper.createShipmentPackage(shipmentId,
         shipmentPackageSeqId, dateCreated, delegator);
         shipmentPackageSeqId = val7.getString("shipmentPackageSeqId");

         GenericValue val9 = PosProductHelper.createShipmentPackageRouteSeg(shipmentId,
         shipmentPackageSeqId, shipmentRouteSegmentId, delegator);

         GenericValue val10 = PosProductHelper.createShipmentStatus(shipmentId,
         "PURCH_SHIP_CREATED", UtilDateTime.nowTimestamp(),
         delegator);
         GenericValue val11 = PosProductHelper.createShipmentStatus(shipmentId,
         "PURCH_SHIP_RECEIVED", UtilDateTime.nowTimestamp(),
         delegator);
         GenericValue val12 = PosProductHelper.createShipmentStatus(shipmentId,
         "PURCH_SHIP_SHIPPED", UtilDateTime.nowTimestamp(),
         delegator);

         String acctgTransShipmentId = delegator.getNextSeqId("AcctgTrans");// "9000";
         String acctgTransTypeId = "SHIPMENT_RECEIPT";
         Timestamp transactionDate = UtilDateTime.nowTimestamp();
         String isPosted = "Y";
         Timestamp postedDate = UtilDateTime.nowTimestamp();
         String glFiscalTypeId = "ACTUAL";

         // shipmentId="9997";

         GenericValue acctgTrans = PosProductHelper.createAcctgTrans(acctgTransShipmentId,
         acctgTransTypeId, transactionDate, isPosted, postedDate,
         glFiscalTypeId, this.partyId, shipmentId, null, null,
         delegator);

         List<OrderItemMax> items = getAllItemsList();
         for (OrderItemComposite item : items) {
         /*                item.createOrderItem(shipmentId, shipGroupSeqId, shipmentPackageSeqId, 
         carrierPartyId, ownerPartyId, carrierPartyId, destinationFacilityId, 
         shipmentTypeId, acctgTransShipmentId, delegator);
         item.setorderId(orderId);
         //        String productId = item.getproductId();
         //        String orderId = item.orderHeader.getOrderId();
         //        String orderItemSeqId = item.getorderItemSeqId();
         //        String productName = item.getitemDescription();
         item.setorderItemTypeId("PRODUCT_ORDER_ITEM");

         item.createOrderItem(shipmentId, shipGroupSeqId, shipmentPackageSeqId, partyId,
         ownerPartyId,
         getcurrencyUom(), destinationFacilityId, getcurrencyUom(), acctgTransShipmentId, delegator);

         }
         */
    }

    /*    
     public ArrayList<OrderItemMax> getItemsList() {
     ArrayList<OrderItemMax> unDeletedItemsList = new ArrayList<OrderItemMax>();
     for (OrderItemComposite entry : itemsList) {
     if (entry.itemDeletd == false) {
     unDeletedItemsList.add(entry);
     }
     }
     return unDeletedItemsList;
     }

     public ArrayList<OrderItemMax> getAllItemsList() {
     return itemsList;
     }
     */
    /*  public String getPartyId() {
     if (ORDERTYPE_PURCHSEORDER.equals(orderHeader.getOrderTypeId())) {
     return getBillFromAccount().getParty().getParty().getpartyId();
     } else {
     return getBillToAccount().getParty().getParty().getpartyId();
     }
     }
     */
    /* public String getPartyName() {

     if (ORDERTYPE_PURCHSEORDER.equals(orderHeader.getOrderTypeId())) {
     return getBillFromAccount().getParty().getPartyGroup().getgroupName();
     } else {
     return getBillToAccount().getParty().getPartyGroup().getgroupName();
     }

     }*/
    //public int size() {
    //    return orderItemsList.getSize();
    //}

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
    /*
     public BigDecimal getSubTotal() {
     return shopingCart.getSubTotal();
     }

     public BigDecimal getTotalShipping() {
     return shopingCart.getTotalShipping();
     }
     */

    public BigDecimal getTaxTotal() {
        BigDecimal taxTotal =  getTotalSalesTax();
        for (OrderItemComposite item : orderItemsList.getList()) {
            taxTotal = taxTotal.add(item.getGstAmount());
        }    
        return taxTotal;
    }
    /*
     public BigDecimal getGrandTotal() {
     return shopingCart.getGrandTotal();
     }
     */

    public int getNumberOfPayments() {
        return selectedPayments();
    }

//    public BigDecimal getPaymentTotal() {
//        return shopingCart.getPaymentTotal();
//    }
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

    /*public OrderContactMechCompositeList getOrderContactList() {
     return orderContactList;
     }
     */
    public void setOrderContactList(OrderContactMechCompositeList orderContactList) {
        if (orderContactList != null) {
            for (OrderContactMechComposite pc : orderContactList.getList()) {
                addContactMech(pc.getPartyContactMechPurposeType().getcontactMechPurposeTypeId(),
                        pc.getOrderContactMech().getcontactMechId());
            }
        }
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

    public OrderReturnItemsList getOrderReturnItemsList() {
        return orderReturnItemsList;
    }

    public void setOrderReturnItemsList(OrderReturnItemsList orderReturnItemsList) {
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

    public String getCreatedBy() {
        if (this.getUserLogin() != null) {
            return this.getUserLogin().getString("partyId");
        }

        return "";
    }

    public void setCreatedBy(String str) {

    }

    public void setEntryDate(Timestamp shipAfterDate) {
        this.setShipAfterDate(shipAfterDate);
    }

    /**
     * Get ship after date for ship group 0
     *
     * @return return the ship after date for the first ship group
     */
    public Timestamp getEntryDate() {
        return this.getShipAfterDate(0);
    }

    private BigDecimal billingAccountLimit = BigDecimal.ZERO;

    private BigDecimal billingAccountBalance = BigDecimal.ZERO;

    public BigDecimal getCreditAccountBalance() {
        return billingAccountLimit.subtract(billingAccountBalance);
    }

    public BigDecimal getBillingAccountBalance() {
        return billingAccountBalance;
    }

    public void setBillingAccountBalance(BigDecimal billingAccountAmt) {
        this.billingAccountBalance = billingAccountAmt;
    }

    public BigDecimal getBillingAccountLimit() {
        return billingAccountLimit;
    }

    public void setBillingAccountLimit(BigDecimal billingAccountLimit) {
        this.billingAccountLimit = billingAccountLimit;
    }

    public OrderReadHelper getOrderReadHelper() {
        return orderReadHelper;
    }

    public void setOrderReadHelper(OrderReadHelper orderReadHelper) {
        this.orderReadHelper = orderReadHelper;
    }

    private Map<String, String> shippingContact = new HashMap<String, String>();

    public Map<String, String> getShippingContact() {
        return shippingContact;
    }

    public void setShippingContact(String purpose, String mechId) {
        this.shippingContact.put(purpose, mechId);
    }
}
