/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.math.BigDecimal;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.composite.OrderItemComposite;

/**
 *
 * @author administrator
 */
public class AudioRecord extends OrderItemComposite{

    protected boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
//    private java.lang.String productId;

//    public java.lang.String getproductId() {
//        return productId;
//    }

//    public void setproductId(java.lang.String productId) {
//        this.productId = productId;
//    }
//    private java.lang.String itemDescription;

//    public java.lang.String getitemDescription() {
//        return itemDescription;
//    }

//    public void setitemDescription(java.lang.String itemDescription) {
//        this.itemDescription = itemDescription;
//    }
//    private java.math.BigDecimal quantity;

//    public java.math.BigDecimal getquantity() {
//        return quantity;
//    }

//    public void setquantity(java.math.BigDecimal quantity) {
//        this.quantity = quantity;
//    }
 //   private java.math.BigDecimal unitPrice;

 //   public java.math.BigDecimal getunitPrice() {
 //       return unitPrice;
 //   }

//    public void setunitPrice(java.math.BigDecimal unitPrice) {
//        this.unitPrice = unitPrice;
//    }
/*    private java.math.BigDecimal unitListPrice;

    public java.math.BigDecimal getunitListPrice() {
        return unitListPrice;
    }

    public void setunitListPrice(java.math.BigDecimal unitListPrice) {
        this.unitListPrice = unitListPrice;
    }
    protected BigDecimal quantyOnHand;

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
    protected BigDecimal gstAmount;

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
*/
    //protected BigDecimal totalAmount;
//    public OrderItemComposite oim = null;

//    public AudioRecord(OrderItemComposite oim) {
//        this.oim = oim;
//        selected = false;
/*        totalAmount = BigDecimal.ZERO;
        gstAmount = BigDecimal.ZERO;
        lastPrice = BigDecimal.ZERO;
        quantyOnHand = BigDecimal.ZERO;
        unitListPrice = BigDecimal.ZERO;
        unitPrice = BigDecimal.ZERO;
        quantity = BigDecimal.ZERO;*/
//    }

//    static public AudioRecord createRecord(OrderItemComposite oim) {
//        AudioRecord pojo = new AudioRecord(oim);
/*        pojo.unitListPrice = oim.listPrice;
        pojo.unitPrice = oim.getOrderItem().getunitPrice();
        pojo.quantity = oim.getOrderItem().getquantity();
        pojo.totalAmount = oim.saleLineTotal;
        Debug.logInfo(" total amt:" + pojo.totalAmount, "module");
        pojo.gstAmount = oim.saleGstTotal;
//        pojo.defaultPrice = oim.defaultPrice;
//        pojo.supplierLastPrice = oim.getunitPrice();
        pojo.lastPrice = oim.lastSalePrice;
        pojo.quantyOnHand = new BigDecimal(oim.qtyOnHand.doubleValue());
        pojo.itemDescription = oim.getOrderItem().getitemDescription();
        pojo.productId = oim.getOrderItem().getproductId();
*/
//        return pojo;
//    }

    String supplierProductId;

    public String getSupplierProductId() {
        return supplierProductId;
    }

    public void setSupplierProductId(String supplierProductId) {
        this.supplierProductId = supplierProductId;
    }

//    public void updateOrderMax() {
//        if (oim != null) {
            /*oim.listPrice = this.unitListPrice;
            oim.getOrderItem().setunitPrice(this.unitPrice);
            oim.getOrderItem().setquantity(this.quantity);
            oim.saleLineTotal = this.totalAmount;
            oim.saleGstTotal = this.gstAmount;
            oim.lastSalePrice = this.lastPrice;
//        this.defaultPrice = oim.defaultPrice;
//        this.supplierLastPrice = oim.getunitPrice();
            oim.qtyOnHand = this.quantyOnHand.intValue();// = new BigDecimal(.doubleValue());
            oim.supplierProductId = this.supplierProductId;
//        this.itemDescription = oim.getitemDescription();
//        this.productId = oim.getproductId();            
            */
//        }
//    }

    private SupplierProductAndListPriceData orderItemData = null;

    public SupplierProductAndListPriceData getOrderItemData() {
        return orderItemData;
    }

    public void setOrderItemData(SupplierProductAndListPriceData orderItemData) {
        this.orderItemData = orderItemData;
    }

}
