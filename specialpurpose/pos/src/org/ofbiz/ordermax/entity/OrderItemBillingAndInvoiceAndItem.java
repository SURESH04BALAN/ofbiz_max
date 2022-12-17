package org.ofbiz.ordermax.entity;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class OrderItemBillingAndInvoiceAndItem implements GenericValueObjectInterface {
public static final String module =OrderItemBillingAndInvoiceAndItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderItemBillingAndInvoiceAndItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderItemBillingAndInvoiceAndItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"statusId","Status Id"},
{"amount","Amount"},
{"orderItemSeqId","Order Item Seq Id"},
{"invoiceId","Invoice Id"},
{"itemIssuanceId","Item Issuance Id"},
{"invoiceItemSeqId","Invoice Item Seq Id"},
{"quantity","Quantity"},
{"shipmentReceiptId","Shipment Receipt Id"},
{"orderId","Order Id"},
};
protected void initObject(){
this.statusId = "";
this.amount = java.math.BigDecimal.ZERO;
this.orderItemSeqId = "";
this.invoiceId = "";
this.itemIssuanceId = "";
this.invoiceItemSeqId = "";
this.quantity = java.math.BigDecimal.ZERO;
this.shipmentReceiptId = "";
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.statusId = (java.lang.String) genVal.get("statusId");
this.amount = (java.math.BigDecimal) genVal.get("amount");
this.orderItemSeqId = (java.lang.String) genVal.get("orderItemSeqId");
this.invoiceId = (java.lang.String) genVal.get("invoiceId");
this.itemIssuanceId = (java.lang.String) genVal.get("itemIssuanceId");
this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
this.quantity = (java.math.BigDecimal) genVal.get("quantity");
this.shipmentReceiptId = (java.lang.String) genVal.get("shipmentReceiptId");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("amount",OrderMaxUtility.getValidEntityBigDecimal(this.amount));
val.set("orderItemSeqId",OrderMaxUtility.getValidEntityString(this.orderItemSeqId));
val.set("invoiceId",OrderMaxUtility.getValidEntityString(this.invoiceId));
val.set("itemIssuanceId",OrderMaxUtility.getValidEntityString(this.itemIssuanceId));
val.set("invoiceItemSeqId",OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
val.set("quantity",OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
val.set("shipmentReceiptId",OrderMaxUtility.getValidEntityString(this.shipmentReceiptId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("amount",this.amount);
valueMap.put("orderItemSeqId",this.orderItemSeqId);
valueMap.put("invoiceId",this.invoiceId);
valueMap.put("itemIssuanceId",this.itemIssuanceId);
valueMap.put("invoiceItemSeqId",this.invoiceItemSeqId);
valueMap.put("quantity",this.quantity);
valueMap.put("shipmentReceiptId",this.shipmentReceiptId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderItemBillingAndInvoiceAndItem");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.math.BigDecimal amount;
public java.math.BigDecimal getamount() {
return amount;
}
public void setamount( java.math.BigDecimal amount ) {
this.amount = amount;
}
private java.lang.String orderItemSeqId;
public java.lang.String getorderItemSeqId() {
return orderItemSeqId;
}
public void setorderItemSeqId( java.lang.String orderItemSeqId ) {
this.orderItemSeqId = orderItemSeqId;
}
private java.lang.String invoiceId;
public java.lang.String getinvoiceId() {
return invoiceId;
}
public void setinvoiceId( java.lang.String invoiceId ) {
this.invoiceId = invoiceId;
}
private java.lang.String itemIssuanceId;
public java.lang.String getitemIssuanceId() {
return itemIssuanceId;
}
public void setitemIssuanceId( java.lang.String itemIssuanceId ) {
this.itemIssuanceId = itemIssuanceId;
}
private java.lang.String invoiceItemSeqId;
public java.lang.String getinvoiceItemSeqId() {
return invoiceItemSeqId;
}
public void setinvoiceItemSeqId( java.lang.String invoiceItemSeqId ) {
this.invoiceItemSeqId = invoiceItemSeqId;
}
private java.math.BigDecimal quantity;
public java.math.BigDecimal getquantity() {
return quantity;
}
public void setquantity( java.math.BigDecimal quantity ) {
this.quantity = quantity;
}
private java.lang.String shipmentReceiptId;
public java.lang.String getshipmentReceiptId() {
return shipmentReceiptId;
}
public void setshipmentReceiptId( java.lang.String shipmentReceiptId ) {
this.shipmentReceiptId = shipmentReceiptId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderItemBillingAndInvoiceAndItem> objectList = new ArrayList<OrderItemBillingAndInvoiceAndItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderItemBillingAndInvoiceAndItem(genVal));
        }
        return objectList;
    }    
}
