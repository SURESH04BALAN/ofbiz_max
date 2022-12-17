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
public class ProductStorePaymentSetting implements GenericValueObjectInterface {
public static final String module =ProductStorePaymentSetting.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductStorePaymentSetting(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductStorePaymentSetting () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"paymentPropertiesPath","Payment Properties Path"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"productStoreId","Product Store Id"},
{"paymentMethodTypeId","Payment Method Type Id"},
{"paymentGatewayConfigId","Payment Gateway Config Id"},
{"paymentService","Payment Service"},
{"paymentCustomMethodId","Payment Custom Method Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"paymentServiceTypeEnumId","Payment Service Type Enum Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"applyToAllProducts","Apply To All Products"},
};
protected void initObject(){
this.paymentPropertiesPath = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.productStoreId = "";
this.paymentMethodTypeId = "";
this.paymentGatewayConfigId = "";
this.paymentService = "";
this.paymentCustomMethodId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.paymentServiceTypeEnumId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.applyToAllProducts = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.paymentPropertiesPath = (java.lang.String) genVal.get("paymentPropertiesPath");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.paymentMethodTypeId = (java.lang.String) genVal.get("paymentMethodTypeId");
this.paymentGatewayConfigId = (java.lang.String) genVal.get("paymentGatewayConfigId");
this.paymentService = (java.lang.String) genVal.get("paymentService");
this.paymentCustomMethodId = (java.lang.String) genVal.get("paymentCustomMethodId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.paymentServiceTypeEnumId = (java.lang.String) genVal.get("paymentServiceTypeEnumId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.applyToAllProducts = (java.lang.String) genVal.get("applyToAllProducts");
}
protected void getGenericValue(GenericValue val) {
val.set("paymentPropertiesPath",OrderMaxUtility.getValidEntityString(this.paymentPropertiesPath));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("paymentMethodTypeId",OrderMaxUtility.getValidEntityString(this.paymentMethodTypeId));
val.set("paymentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.paymentGatewayConfigId));
val.set("paymentService",OrderMaxUtility.getValidEntityString(this.paymentService));
val.set("paymentCustomMethodId",OrderMaxUtility.getValidEntityString(this.paymentCustomMethodId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("paymentServiceTypeEnumId",OrderMaxUtility.getValidEntityString(this.paymentServiceTypeEnumId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("applyToAllProducts",OrderMaxUtility.getValidEntityString(this.applyToAllProducts));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("paymentPropertiesPath",this.paymentPropertiesPath);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("paymentMethodTypeId",this.paymentMethodTypeId);
valueMap.put("paymentGatewayConfigId",this.paymentGatewayConfigId);
valueMap.put("paymentService",this.paymentService);
valueMap.put("paymentCustomMethodId",this.paymentCustomMethodId);
valueMap.put("paymentServiceTypeEnumId",this.paymentServiceTypeEnumId);
valueMap.put("applyToAllProducts",this.applyToAllProducts);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductStorePaymentSetting");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String paymentPropertiesPath;
public java.lang.String getpaymentPropertiesPath() {
return paymentPropertiesPath;
}
public void setpaymentPropertiesPath( java.lang.String paymentPropertiesPath ) {
this.paymentPropertiesPath = paymentPropertiesPath;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String paymentMethodTypeId;
public java.lang.String getpaymentMethodTypeId() {
return paymentMethodTypeId;
}
public void setpaymentMethodTypeId( java.lang.String paymentMethodTypeId ) {
this.paymentMethodTypeId = paymentMethodTypeId;
}
private java.lang.String paymentGatewayConfigId;
public java.lang.String getpaymentGatewayConfigId() {
return paymentGatewayConfigId;
}
public void setpaymentGatewayConfigId( java.lang.String paymentGatewayConfigId ) {
this.paymentGatewayConfigId = paymentGatewayConfigId;
}
private java.lang.String paymentService;
public java.lang.String getpaymentService() {
return paymentService;
}
public void setpaymentService( java.lang.String paymentService ) {
this.paymentService = paymentService;
}
private java.lang.String paymentCustomMethodId;
public java.lang.String getpaymentCustomMethodId() {
return paymentCustomMethodId;
}
public void setpaymentCustomMethodId( java.lang.String paymentCustomMethodId ) {
this.paymentCustomMethodId = paymentCustomMethodId;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String paymentServiceTypeEnumId;
public java.lang.String getpaymentServiceTypeEnumId() {
return paymentServiceTypeEnumId;
}
public void setpaymentServiceTypeEnumId( java.lang.String paymentServiceTypeEnumId ) {
this.paymentServiceTypeEnumId = paymentServiceTypeEnumId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String applyToAllProducts;
public java.lang.String getapplyToAllProducts() {
return applyToAllProducts;
}
public void setapplyToAllProducts( java.lang.String applyToAllProducts ) {
this.applyToAllProducts = applyToAllProducts;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductStorePaymentSetting> objectList = new ArrayList<ProductStorePaymentSetting>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductStorePaymentSetting(genVal));
        }
        return objectList;
    }    
}
