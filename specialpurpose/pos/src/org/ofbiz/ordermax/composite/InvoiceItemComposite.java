/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;

import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.InvoiceItem;

import org.ofbiz.ordermax.entity.ShipmentItem;

/**
 *
 * @author siranjeev
 */
public class InvoiceItemComposite {
    public static final String module = InvoiceItemComposite.class.getName();    
    
    public Boolean itemSelected = false;
    public Boolean itemDeletd = false;
    public Boolean itemNew = false;
    public Boolean itemModified = false;

    public BigDecimal totalCost = BigDecimal.ZERO;
    public BigDecimal listPrice = BigDecimal.ZERO;
    public BigDecimal purchasePrice = BigDecimal.ZERO;
    public BigDecimal defaultPrice = BigDecimal.ZERO;
    public Integer    qtyOnHand = 0;
    public BigDecimal lastSalePrice = BigDecimal.ZERO;

    protected String shipGroupSeqId;
    public String supplierProductId;
    private InvoiceItem invoiceItem;
    private OrderStatusList orderStatusList;
    private SupplierProductList supplierProductList;
    
    private ShoppingCartItem shoppingCartItem;

    public ShoppingCartItem getShoppingCartItem() {
        return shoppingCartItem;
    }

    public void setShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        this.shoppingCartItem = shoppingCartItem;
    }
    
    private ShipmentItem shipmentItem = new ShipmentItem();

    private OrderItemBillingsList orderItemBillingsList;
    /**
     *
     */
    public InvoiceItemComposite() {
        invoiceItem = new InvoiceItem(); 
    }
    
    public InvoiceItemComposite(String string1, String string2) {
//        super(string1, string2); 
        invoiceItem = new InvoiceItem();        
        this.invoiceItem.setproductId(string1);
        this.invoiceItem.setdescription(string2);        
    }

    public InvoiceItemComposite(GenericValue item) {
        invoiceItem = new InvoiceItem(item);
    }

//    public 
    public void createInvoiceItem(String shipmentId, String shipGroupSeqId, String shipmentPackageSeqId, String supplierPartyId, String ownerPartyId,
            String currencyUomId, String destinationFacilityId, String origCurrencyUomId, String acctgTransShipmentId, Delegator delegator) throws GenericEntityException {
        //     for (ProductInventoryPojo entry : productList) {
        int transSq = 1;
        String shipmentItemSeqId = null;
        String acctgTransEntrySeqId = UtilFormatOut.formatPaddedNumber(transSq++, 4);
        shipmentItemSeqId = UtilFormatOut.formatPaddedNumber(1, 4);

        //   String invoiceItemSeqId = UtilFormatOut.formatPaddedNumber(orderItemSeqIdval, 4);
        // orderItemSeqId = UtilFormatOut.formatPaddedNumber(orderItemSeqIdval++, 4);

        String productId = this.invoiceItem.getproductId();
        String orderId = this.invoiceItem.getInvoiceId();
        String orderItemSeqId = this.invoiceItem.getInvoiceItemSeqId();
        String productName = this.invoiceItem.getdescription();
        String orderItemTypeId = this.invoiceItem.getInvoiceItemTypeId();//"PRODUCT_ORDER_ITEM";;

        Debug.logInfo(" productId: " + productId, module);

        String prodCatalogId = "DemoCatalog";
        String isPromo = "N";
        BigDecimal quantity = this.invoiceItem.getquantity();
        BigDecimal selectedAmount = BigDecimal.ZERO;
        BigDecimal unitPrice = this.invoiceItem.getAmount();//.supplierLastPrice;// new BigDecimal("24.00");
        BigDecimal unitListPrice =  BigDecimal.ZERO;//this.invoiceItem.getunitListPrice();//new BigDecimal("0.00");
        String isModifiedPrice = "N";

        String statusId = "ITEM_CREATED";
        Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();

        GenericValue val = PosProductHelper.createOrderItem(orderId, orderItemSeqId, orderItemTypeId,
                productId, prodCatalogId, isPromo, quantity,
                selectedAmount, unitPrice, unitListPrice,
                isModifiedPrice, productName, statusId,
                estimatedDeliveryDate, delegator);
        orderItemSeqId = val.getString("orderItemSeqId");

        String orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";

        Timestamp statusDatetime = UtilDateTime.nowTimestamp();
        String statusUserLogin = "admin";
        GenericValue val2 = PosProductHelper.createOrderStatus(orderStatusId, statusId, orderId,
                orderItemSeqId, statusDatetime, statusUserLogin, delegator);

        GenericValue val4 = PosProductHelper.createShipmentItem(shipmentId, shipmentItemSeqId, productId, quantity, delegator);
        shipmentItemSeqId = val4.getString("shipmentItemSeqId");

    }
    public OrderStatusList getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(OrderStatusList orderStatusList) {
        this.orderStatusList = orderStatusList;
    }

    public InvoiceItem getOrderItem() {
        return invoiceItem;
    }

    public void setOrderItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public OrderItemBillingsList getOrderItemBillingsList() {
        return orderItemBillingsList;
    }

    public void setOrderItemBillingsList(OrderItemBillingsList orderItemBillingsList) {
        this.orderItemBillingsList = orderItemBillingsList;
    }
    
    private OrderItemShipGroupAssocsList orderItemShipGroupAssocsList;
    public OrderItemShipGroupAssocsList getOrderItemShipGroupAssocsList() {
        return orderItemShipGroupAssocsList;
    }

    public void setOrderItemShipGroupAssocsList(OrderItemShipGroupAssocsList orderItemShipGroupAssocsList) {
        this.orderItemShipGroupAssocsList = orderItemShipGroupAssocsList;
    }
    
    protected BigDecimal quantyOnHand= BigDecimal.ZERO;;

    public BigDecimal getQuantyOnHand() {
        return quantyOnHand;
    }

    public void setQuantyOnHand(BigDecimal quantyOnHand) {
        this.quantyOnHand = quantyOnHand;
    }
    protected BigDecimal lastPrice;

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }
    protected BigDecimal gstAmount = BigDecimal.ZERO;;

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }
    
    protected BigDecimal totalAmount = BigDecimal.ZERO;

    public BigDecimal getTotalAmount() {
        if(shoppingCartItem!=null){
            
            shoppingCartItem.getItemSubTotal();
        }
        
        return BigDecimal.ZERO;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
   
    protected boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }  

    public String getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(String supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

    
    ProductItemPrice productItemPrice;

    public ProductItemPrice getProductItemPrice() {
        return productItemPrice;
    }

    public void setProductItemPrice(ProductItemPrice productItemPrice) {
        this.productItemPrice = productItemPrice;
    }

    public SupplierProductList getSupplierProductList() {
        return supplierProductList;
    }

    public void setSupplierProductList(SupplierProductList supplierProductList) {
        this.supplierProductList = supplierProductList;
    }

    public ShipmentItem getShipmentItem() {
        return shipmentItem;
    }

    public void setShipmentItem(ShipmentItem shipmentItem) {
        this.shipmentItem = shipmentItem;
    }           
}
