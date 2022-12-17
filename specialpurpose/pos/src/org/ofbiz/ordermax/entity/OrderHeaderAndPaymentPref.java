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
public class OrderHeaderAndPaymentPref implements GenericValueObjectInterface {
public static final String module =OrderHeaderAndPaymentPref.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderHeaderAndPaymentPref(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderHeaderAndPaymentPref () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"orderPaymentPreferenceId","Order Payment Preference Id"},
{"originFacilityId","Origin Facility Id"},
{"productStoreId","Product Store Id"},
{"orderDate","Order Date"},
{"paymentMethodTypeId","Payment Method Type Id"},
{"currencyUom","Currency Uom"},
{"paymentStatusId","Payment Status Id"},
{"maxAmount","Max Amount"},
{"orderStatusId","Order Status Id"},
{"terminalId","Terminal Id"},
{"webSiteId","Web Site Id"},
{"orderId","Order Id"},
};
protected void initObject(){
this.orderPaymentPreferenceId = "";
this.originFacilityId = "";
this.productStoreId = "";
this.orderDate = UtilDateTime.nowTimestamp();
this.paymentMethodTypeId = "";
this.currencyUom = "";
this.paymentStatusId = "";
this.maxAmount = java.math.BigDecimal.ZERO;
this.orderStatusId = "";
this.terminalId = "";
this.webSiteId = "";
this.orderId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.orderPaymentPreferenceId = (java.lang.String) genVal.get("orderPaymentPreferenceId");
this.originFacilityId = (java.lang.String) genVal.get("originFacilityId");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.orderDate = (java.sql.Timestamp) genVal.get("orderDate");
this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
this.currencyUom = (java.lang.String) genVal.get("currencyUom");
this.paymentStatusId = (java.lang.String) genVal.get("paymentStatusId");
this.maxAmount = (java.math.BigDecimal) genVal.get("maxAmount");
this.orderStatusId = (java.lang.String) genVal.get("orderStatusId");
this.terminalId = (java.lang.String) genVal.get("terminalId");
this.webSiteId = (java.lang.String) genVal.get("webSiteId");
this.orderId = (java.lang.String) genVal.get("orderId");
}
protected void getGenericValue(GenericValue val) {
val.set("orderPaymentPreferenceId",OrderMaxUtility.getValidEntityString(this.orderPaymentPreferenceId));
val.set("originFacilityId",OrderMaxUtility.getValidEntityString(this.originFacilityId));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("orderDate",OrderMaxUtility.getValidEntityTimestamp(this.orderDate));
val.set("paymentMethodTypeId",OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
val.set("currencyUom",OrderMaxUtility.getValidEntityString(this.currencyUom));
val.set("paymentStatusId",OrderMaxUtility.getValidEntityString(this.paymentStatusId));
val.set("maxAmount",OrderMaxUtility.getValidEntityBigDecimal(this.maxAmount));
val.set("orderStatusId",OrderMaxUtility.getValidEntityString(this.orderStatusId));
val.set("terminalId",OrderMaxUtility.getValidEntityString(this.terminalId));
val.set("webSiteId",OrderMaxUtility.getValidEntityString(this.webSiteId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("orderPaymentPreferenceId",this.orderPaymentPreferenceId);
valueMap.put("originFacilityId",this.originFacilityId);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("orderDate",this.orderDate);
valueMap.put("paymentMethodTypeId",this.paymentMethodTypeId);
valueMap.put("currencyUom",this.currencyUom);
valueMap.put("paymentStatusId",this.paymentStatusId);
valueMap.put("maxAmount",this.maxAmount);
valueMap.put("orderStatusId",this.orderStatusId);
valueMap.put("terminalId",this.terminalId);
valueMap.put("webSiteId",this.webSiteId);
valueMap.put("orderId",this.orderId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderHeaderAndPaymentPref");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String orderPaymentPreferenceId;
public java.lang.String getorderPaymentPreferenceId() {
return orderPaymentPreferenceId;
}
public void setorderPaymentPreferenceId( java.lang.String orderPaymentPreferenceId ) {
this.orderPaymentPreferenceId = orderPaymentPreferenceId;
}
private java.lang.String originFacilityId;
public java.lang.String getoriginFacilityId() {
return originFacilityId;
}
public void setoriginFacilityId( java.lang.String originFacilityId ) {
this.originFacilityId = originFacilityId;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.sql.Timestamp orderDate;
public java.sql.Timestamp getorderDate() {
return orderDate;
}
public void setorderDate( java.sql.Timestamp orderDate ) {
this.orderDate = orderDate;
}
private java.lang.String paymentMethodTypeId;
public java.lang.String getpaymentMethodTypeId() {
return paymentMethodTypeId;
}
public void setpaymentMethodTypeId( java.lang.String paymentMethodTypeId ) {
this.paymentMethodTypeId = paymentMethodTypeId;
}
private java.lang.String currencyUom;
public java.lang.String getcurrencyUom() {
return currencyUom;
}
public void setcurrencyUom( java.lang.String currencyUom ) {
this.currencyUom = currencyUom;
}
private java.lang.String paymentStatusId;
public java.lang.String getpaymentStatusId() {
return paymentStatusId;
}
public void setpaymentStatusId( java.lang.String paymentStatusId ) {
this.paymentStatusId = paymentStatusId;
}
private java.math.BigDecimal maxAmount;
public java.math.BigDecimal getmaxAmount() {
return maxAmount;
}
public void setmaxAmount( java.math.BigDecimal maxAmount ) {
this.maxAmount = maxAmount;
}
private java.lang.String orderStatusId;
public java.lang.String getorderStatusId() {
return orderStatusId;
}
public void setorderStatusId( java.lang.String orderStatusId ) {
this.orderStatusId = orderStatusId;
}
private java.lang.String terminalId;
public java.lang.String getterminalId() {
return terminalId;
}
public void setterminalId( java.lang.String terminalId ) {
this.terminalId = terminalId;
}
private java.lang.String webSiteId;
public java.lang.String getwebSiteId() {
return webSiteId;
}
public void setwebSiteId( java.lang.String webSiteId ) {
this.webSiteId = webSiteId;
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
        Collection<OrderHeaderAndPaymentPref> objectList = new ArrayList<OrderHeaderAndPaymentPref>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderAndPaymentPref(genVal));
        }
        return objectList;
    }    
}
