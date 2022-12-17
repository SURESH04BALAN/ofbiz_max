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
public class ShipmentGatewayUps implements GenericValueObjectInterface {
public static final String module =ShipmentGatewayUps.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ShipmentGatewayUps(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ShipmentGatewayUps () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"codSurchargeAmount","Cod Surcharge Amount"},
{"shipmentGatewayConfigId","Shipment Gateway Config Id"},
{"shipperNumber","Shipper Number"},
{"connectTimeout","Connect Timeout"},
{"accessPassword","Access Password"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"defaultReturnLabelMemo","Default Return Label Memo"},
{"maxEstimateWeight","Max Estimate Weight"},
{"minEstimateWeight","Min Estimate Weight"},
{"shipperPickupType","Shipper Pickup Type"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"codSurchargeCurrencyUomId","Cod Surcharge Currency Uom Id"},
{"connectUrl","Connect Url"},
{"accessLicenseNumber","Access License Number"},
{"codFundsCode","Cod Funds Code"},
{"saveCertPath","Save Cert Path"},
{"billShipperAccountNumber","Bill Shipper Account Number"},
{"codSurchargeApplyToPackage","Cod Surcharge Apply To Package"},
{"saveCertInfo","Save Cert Info"},
{"codAllowCod","Cod Allow Cod"},
{"defaultReturnLabelSubject","Default Return Label Subject"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"customerClassification","Customer Classification"},
{"accessUserId","Access User Id"},
};
protected void initObject(){
this.codSurchargeAmount = java.math.BigDecimal.ZERO;
this.shipmentGatewayConfigId = "";
this.shipperNumber = "";
this.connectTimeout = new Long(0);
this.accessPassword = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.defaultReturnLabelMemo = "";
this.maxEstimateWeight = java.math.BigDecimal.ZERO;
this.minEstimateWeight = java.math.BigDecimal.ZERO;
this.shipperPickupType = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.codSurchargeCurrencyUomId = "";
this.connectUrl = "";
this.accessLicenseNumber = "";
this.codFundsCode = "";
this.saveCertPath = "";
this.billShipperAccountNumber = "";
this.codSurchargeApplyToPackage = "";
this.saveCertInfo = "";
this.codAllowCod = "";
this.defaultReturnLabelSubject = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.customerClassification = "";
this.accessUserId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.codSurchargeAmount = (java.math.BigDecimal) genVal.get("codSurchargeAmount");
this.shipmentGatewayConfigId = (java.lang.String) genVal.get("shipmentGatewayConfigId");
this.shipperNumber = (java.lang.String) genVal.get("shipperNumber");
this.connectTimeout = (java.lang.Long) genVal.get("connectTimeout");
this.accessPassword = (java.lang.String) genVal.get("accessPassword");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.defaultReturnLabelMemo = (java.lang.String) genVal.get("defaultReturnLabelMemo");
this.maxEstimateWeight = (java.math.BigDecimal) genVal.get("maxEstimateWeight");
this.minEstimateWeight = (java.math.BigDecimal) genVal.get("minEstimateWeight");
this.shipperPickupType = (java.lang.String) genVal.get("shipperPickupType");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.codSurchargeCurrencyUomId = (java.lang.String) genVal.get("codSurchargeCurrencyUomId");
this.connectUrl = (java.lang.String) genVal.get("connectUrl");
this.accessLicenseNumber = (java.lang.String) genVal.get("accessLicenseNumber");
this.codFundsCode = (java.lang.String) genVal.get("codFundsCode");
this.saveCertPath = (java.lang.String) genVal.get("saveCertPath");
this.billShipperAccountNumber = (java.lang.String) genVal.get("billShipperAccountNumber");
this.codSurchargeApplyToPackage = (java.lang.String) genVal.get("codSurchargeApplyToPackage");
this.saveCertInfo = (java.lang.String) genVal.get("saveCertInfo");
this.codAllowCod = (java.lang.String) genVal.get("codAllowCod");
this.defaultReturnLabelSubject = (java.lang.String) genVal.get("defaultReturnLabelSubject");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.customerClassification = (java.lang.String) genVal.get("customerClassification");
this.accessUserId = (java.lang.String) genVal.get("accessUserId");
}
protected void getGenericValue(GenericValue val) {
val.set("codSurchargeAmount",OrderMaxUtility.getValidEntityBigDecimal(this.codSurchargeAmount));
val.set("shipmentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.shipmentGatewayConfigId));
val.set("shipperNumber",OrderMaxUtility.getValidEntityString(this.shipperNumber));
val.set("connectTimeout",this.connectTimeout);
val.set("accessPassword",OrderMaxUtility.getValidEntityString(this.accessPassword));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("defaultReturnLabelMemo",OrderMaxUtility.getValidEntityString(this.defaultReturnLabelMemo));
val.set("maxEstimateWeight",OrderMaxUtility.getValidEntityBigDecimal(this.maxEstimateWeight));
val.set("minEstimateWeight",OrderMaxUtility.getValidEntityBigDecimal(this.minEstimateWeight));
val.set("shipperPickupType",OrderMaxUtility.getValidEntityString(this.shipperPickupType));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("codSurchargeCurrencyUomId",OrderMaxUtility.getValidEntityString(this.codSurchargeCurrencyUomId));
val.set("connectUrl",OrderMaxUtility.getValidEntityString(this.connectUrl));
val.set("accessLicenseNumber",OrderMaxUtility.getValidEntityString(this.accessLicenseNumber));
val.set("codFundsCode",OrderMaxUtility.getValidEntityString(this.codFundsCode));
val.set("saveCertPath",OrderMaxUtility.getValidEntityString(this.saveCertPath));
val.set("billShipperAccountNumber",OrderMaxUtility.getValidEntityString(this.billShipperAccountNumber));
val.set("codSurchargeApplyToPackage",OrderMaxUtility.getValidEntityString(this.codSurchargeApplyToPackage));
val.set("saveCertInfo",OrderMaxUtility.getValidEntityString(this.saveCertInfo));
val.set("codAllowCod",OrderMaxUtility.getValidEntityString(this.codAllowCod));
val.set("defaultReturnLabelSubject",OrderMaxUtility.getValidEntityString(this.defaultReturnLabelSubject));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("customerClassification",OrderMaxUtility.getValidEntityString(this.customerClassification));
val.set("accessUserId",OrderMaxUtility.getValidEntityString(this.accessUserId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("codSurchargeAmount",this.codSurchargeAmount);
valueMap.put("shipmentGatewayConfigId",this.shipmentGatewayConfigId);
valueMap.put("shipperNumber",this.shipperNumber);
valueMap.put("connectTimeout",this.connectTimeout);
valueMap.put("accessPassword",this.accessPassword);
valueMap.put("defaultReturnLabelMemo",this.defaultReturnLabelMemo);
valueMap.put("maxEstimateWeight",this.maxEstimateWeight);
valueMap.put("minEstimateWeight",this.minEstimateWeight);
valueMap.put("shipperPickupType",this.shipperPickupType);
valueMap.put("codSurchargeCurrencyUomId",this.codSurchargeCurrencyUomId);
valueMap.put("connectUrl",this.connectUrl);
valueMap.put("accessLicenseNumber",this.accessLicenseNumber);
valueMap.put("codFundsCode",this.codFundsCode);
valueMap.put("saveCertPath",this.saveCertPath);
valueMap.put("billShipperAccountNumber",this.billShipperAccountNumber);
valueMap.put("codSurchargeApplyToPackage",this.codSurchargeApplyToPackage);
valueMap.put("saveCertInfo",this.saveCertInfo);
valueMap.put("codAllowCod",this.codAllowCod);
valueMap.put("defaultReturnLabelSubject",this.defaultReturnLabelSubject);
valueMap.put("customerClassification",this.customerClassification);
valueMap.put("accessUserId",this.accessUserId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ShipmentGatewayUps");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.math.BigDecimal codSurchargeAmount;
public java.math.BigDecimal getcodSurchargeAmount() {
return codSurchargeAmount;
}
public void setcodSurchargeAmount( java.math.BigDecimal codSurchargeAmount ) {
this.codSurchargeAmount = codSurchargeAmount;
}
private java.lang.String shipmentGatewayConfigId;
public java.lang.String getshipmentGatewayConfigId() {
return shipmentGatewayConfigId;
}
public void setshipmentGatewayConfigId( java.lang.String shipmentGatewayConfigId ) {
this.shipmentGatewayConfigId = shipmentGatewayConfigId;
}
private java.lang.String shipperNumber;
public java.lang.String getshipperNumber() {
return shipperNumber;
}
public void setshipperNumber( java.lang.String shipperNumber ) {
this.shipperNumber = shipperNumber;
}
private java.lang.Long connectTimeout;
public java.lang.Long getconnectTimeout() {
return connectTimeout;
}
public void setconnectTimeout( java.lang.Long connectTimeout ) {
this.connectTimeout = connectTimeout;
}
private java.lang.String accessPassword;
public java.lang.String getaccessPassword() {
return accessPassword;
}
public void setaccessPassword( java.lang.String accessPassword ) {
this.accessPassword = accessPassword;
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
private java.lang.String defaultReturnLabelMemo;
public java.lang.String getdefaultReturnLabelMemo() {
return defaultReturnLabelMemo;
}
public void setdefaultReturnLabelMemo( java.lang.String defaultReturnLabelMemo ) {
this.defaultReturnLabelMemo = defaultReturnLabelMemo;
}
private java.math.BigDecimal maxEstimateWeight;
public java.math.BigDecimal getmaxEstimateWeight() {
return maxEstimateWeight;
}
public void setmaxEstimateWeight( java.math.BigDecimal maxEstimateWeight ) {
this.maxEstimateWeight = maxEstimateWeight;
}
private java.math.BigDecimal minEstimateWeight;
public java.math.BigDecimal getminEstimateWeight() {
return minEstimateWeight;
}
public void setminEstimateWeight( java.math.BigDecimal minEstimateWeight ) {
this.minEstimateWeight = minEstimateWeight;
}
private java.lang.String shipperPickupType;
public java.lang.String getshipperPickupType() {
return shipperPickupType;
}
public void setshipperPickupType( java.lang.String shipperPickupType ) {
this.shipperPickupType = shipperPickupType;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String codSurchargeCurrencyUomId;
public java.lang.String getcodSurchargeCurrencyUomId() {
return codSurchargeCurrencyUomId;
}
public void setcodSurchargeCurrencyUomId( java.lang.String codSurchargeCurrencyUomId ) {
this.codSurchargeCurrencyUomId = codSurchargeCurrencyUomId;
}
private java.lang.String connectUrl;
public java.lang.String getconnectUrl() {
return connectUrl;
}
public void setconnectUrl( java.lang.String connectUrl ) {
this.connectUrl = connectUrl;
}
private java.lang.String accessLicenseNumber;
public java.lang.String getaccessLicenseNumber() {
return accessLicenseNumber;
}
public void setaccessLicenseNumber( java.lang.String accessLicenseNumber ) {
this.accessLicenseNumber = accessLicenseNumber;
}
private java.lang.String codFundsCode;
public java.lang.String getcodFundsCode() {
return codFundsCode;
}
public void setcodFundsCode( java.lang.String codFundsCode ) {
this.codFundsCode = codFundsCode;
}
private java.lang.String saveCertPath;
public java.lang.String getsaveCertPath() {
return saveCertPath;
}
public void setsaveCertPath( java.lang.String saveCertPath ) {
this.saveCertPath = saveCertPath;
}
private java.lang.String billShipperAccountNumber;
public java.lang.String getbillShipperAccountNumber() {
return billShipperAccountNumber;
}
public void setbillShipperAccountNumber( java.lang.String billShipperAccountNumber ) {
this.billShipperAccountNumber = billShipperAccountNumber;
}
private java.lang.String codSurchargeApplyToPackage;
public java.lang.String getcodSurchargeApplyToPackage() {
return codSurchargeApplyToPackage;
}
public void setcodSurchargeApplyToPackage( java.lang.String codSurchargeApplyToPackage ) {
this.codSurchargeApplyToPackage = codSurchargeApplyToPackage;
}
private java.lang.String saveCertInfo;
public java.lang.String getsaveCertInfo() {
return saveCertInfo;
}
public void setsaveCertInfo( java.lang.String saveCertInfo ) {
this.saveCertInfo = saveCertInfo;
}
private java.lang.String codAllowCod;
public java.lang.String getcodAllowCod() {
return codAllowCod;
}
public void setcodAllowCod( java.lang.String codAllowCod ) {
this.codAllowCod = codAllowCod;
}
private java.lang.String defaultReturnLabelSubject;
public java.lang.String getdefaultReturnLabelSubject() {
return defaultReturnLabelSubject;
}
public void setdefaultReturnLabelSubject( java.lang.String defaultReturnLabelSubject ) {
this.defaultReturnLabelSubject = defaultReturnLabelSubject;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String customerClassification;
public java.lang.String getcustomerClassification() {
return customerClassification;
}
public void setcustomerClassification( java.lang.String customerClassification ) {
this.customerClassification = customerClassification;
}
private java.lang.String accessUserId;
public java.lang.String getaccessUserId() {
return accessUserId;
}
public void setaccessUserId( java.lang.String accessUserId ) {
this.accessUserId = accessUserId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ShipmentGatewayUps> objectList = new ArrayList<ShipmentGatewayUps>();
        for (GenericValue genVal : genList) {
            objectList.add(new ShipmentGatewayUps(genVal));
        }
        return objectList;
    }    
}
