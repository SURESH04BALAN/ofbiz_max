/*
 * To change this template, choose Tools | Templates
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
import org.ofbiz.ordermax.base.ProductInventoryPojo;
import org.ofbiz.ordermax.entity.OrderItem;
import org.ofbiz.ordermax.entity.ShipmentItem;
import org.ofbiz.ordermax.orderbase.OrderEntryHelper;
import static org.ofbiz.ordermax.entity.OrderItem.module;
import org.ofbiz.ordermax.entity.SupplierProduct;

/**
 *
 * @author siranjeev
 */
public class OrderItemComposite  {

    public Boolean itemSelected = false;
    public Boolean itemDeletd = false;
    public Boolean itemNew = false;
    public Boolean itemModified = false;
    private Boolean taxable = false;

    public BigDecimal totalCost = BigDecimal.ZERO;
    public BigDecimal listPrice = BigDecimal.ZERO;
    public BigDecimal purchasePrice = BigDecimal.ZERO;
    public BigDecimal defaultPrice = BigDecimal.ZERO;
    public BigDecimal atp = BigDecimal.ZERO; 
    public BigDecimal lastSalePrice = BigDecimal.ZERO;
    public BigDecimal averageCost = BigDecimal.ZERO;//getProductAveragePrice
    protected String shipGroupSeqId;
    public String supplierProductId;
    private OrderItem orderItem;
    private OrderStatusList orderStatusList;
    private SupplierProductList supplierProductList;
    
    private SupplierProduct supplierProduct;
    
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
    public OrderItemComposite() {
        orderItem = new OrderItem(); 
    }
    
    public OrderItemComposite(String string1, String string2) {
//        super(string1, string2); 
        orderItem = new OrderItem();        
        this.orderItem.setproductId(string1);
        this.orderItem.setitemDescription(string2);        
    }

    public OrderItemComposite(GenericValue item) {
        orderItem = new OrderItem(item);
    }

    public void copyValues(ProductInventoryPojo pojo) {
        this.listPrice = pojo.price;
        this.purchasePrice = pojo.purchasePrice;
        this.defaultPrice = pojo.defaultPrice;
//        this.qtyOnHand = pojo.quantityOnHand.intValue();
    }

    public BigDecimal getAtp() {
        return atp;
    }

    public void setAtp(BigDecimal atp) {
        this.atp = atp;
    }

    public ProductInventoryPojo createPojo() {
        ProductInventoryPojo pojo = new ProductInventoryPojo();
        pojo.price = this.listPrice;
        pojo.purchasePrice = this.orderItem.getunitPrice();
        pojo.defaultPrice = this.defaultPrice;
        pojo.supplierLastPrice = this.orderItem.getunitPrice();
        pojo.quantityOnHand = this.orderItem.getquantity();
        pojo.productName = orderItem.getitemDescription();
        pojo.supplierPartyId = this.supplierProductId;
        pojo.productId = orderItem.getproductId();
        pojo.isPriceModiefied = true;
        pojo.isPurchasePriceModified = true;
        pojo.isQuantityOnHandModified = true;

        pojo.minimumStock = BigDecimal.ZERO;
        pojo.orderId = this.orderItem.getorderId();
        pojo.orderItemSeqId = this.orderItem.getorderItemSeqId();
        return pojo;
    }

    
    public void copyShipmentValues() {
        shipmentItem.setproductId(this.orderItem.getproductId());
        shipmentItem.setquantity(this.orderItem.getquantity());
        shipmentItem.setshipmentContentDescription(this.orderItem.getitemDescription());
    }

    public void createOrderShipmentItem(Delegator delegator) throws GenericEntityException {

        Debug.logInfo(" createOrderShipmentItem: ", module);
        String productId = this.orderItem.getproductId();
        BigDecimal quantity = this.orderItem.getquantity();
        String productName = this.orderItem.getitemDescription();

//        Debug.logInfo("orderItemSeqId: " + orderItemSeqId, "module");
        GenericValue val = OrderEntryHelper.getShipmentItem(shipmentItem.getshipmentId(), shipmentItem.getshipmentItemSeqId(), delegator);
        if (val == null) {

            val = OrderEntryHelper.createShipmentItem(shipmentItem.getshipmentId(), productId, quantity,
                    productName, delegator);

            shipmentItem = new ShipmentItem(val);

        } else {
//            val = OrderEntryHelper.updateShipmentItem(shipmentItem.getshipmentId(), shipmentItem.getshipmentItemSeqId(), productId, quantity,
//                    productName, delegator);
            val.set("quantity", quantity);
            val.set("shipmentContentDescription", productName);
            shipmentItem = new ShipmentItem(val);
        }
    }

//    public 
    /*
    public void createOrderItem(String shipmentId, String shipGroupSeqId, String shipmentPackageSeqId, String supplierPartyId, String ownerPartyId,
            String currencyUomId, String destinationFacilityId, String origCurrencyUomId, String acctgTransShipmentId, Delegator delegator) throws GenericEntityException {
        //     for (ProductInventoryPojo entry : productList) {
        int transSq = 1;
        String shipmentItemSeqId = null;
        String acctgTransEntrySeqId = UtilFormatOut.formatPaddedNumber(transSq++, 4);
        shipmentItemSeqId = UtilFormatOut.formatPaddedNumber(1, 4);

        //   String invoiceItemSeqId = UtilFormatOut.formatPaddedNumber(orderItemSeqIdval, 4);
        // orderItemSeqId = UtilFormatOut.formatPaddedNumber(orderItemSeqIdval++, 4);

        String productId = this.orderItem.getproductId();
        String orderId = this.orderItem.getorderId();
        String orderItemSeqId = this.orderItem.getorderItemSeqId();
        String productName = this.orderItem.getitemDescription();
        String orderItemTypeId = this.orderItem.getorderItemTypeId();//"PRODUCT_ORDER_ITEM";;

        Debug.logInfo(" productId: " + productId, module);

        String prodCatalogId = "DemoCatalog";
        String isPromo = "N";
        BigDecimal quantity = this.orderItem.getquantity();
        BigDecimal selectedAmount = BigDecimal.ZERO;
        BigDecimal unitPrice = this.orderItem.getunitPrice();//.supplierLastPrice;// new BigDecimal("24.00");
        BigDecimal unitListPrice = this.orderItem.getunitListPrice();//new BigDecimal("0.00");
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
    */
    public OrderStatusList getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(OrderStatusList orderStatusList) {
        this.orderStatusList = orderStatusList;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
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

    public SupplierProduct getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(SupplierProduct supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public Boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(Boolean taxable) {
        this.taxable = taxable;
    }
}
