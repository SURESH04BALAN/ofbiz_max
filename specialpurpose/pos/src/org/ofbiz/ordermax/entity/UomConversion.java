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
public class UomConversion implements GenericValueObjectInterface {
public static final String module =UomConversion.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public UomConversion(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public UomConversion () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"customMethodId","Custom Method Id"},
{"conversionFactor","Conversion Factor"},
{"roundingMode","Rounding Mode"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"decimalScale","Decimal Scale"},
{"uomId","Uom Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"uomIdTo","Uom Id To"},
};
protected void initObject(){
this.customMethodId = "";
this.conversionFactor = new Double(0);
this.roundingMode = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.decimalScale = "";
this.uomId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.uomIdTo = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.customMethodId = (java.lang.String) genVal.get("customMethodId");
this.conversionFactor = (java.lang.Double) genVal.get("conversionFactor");
this.roundingMode = (java.lang.String) genVal.get("roundingMode");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.decimalScale = (java.lang.String) genVal.get("decimalScale");
this.uomId = (java.lang.String) genVal.get("uomId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.uomIdTo = (java.lang.String) genVal.get("uomIdTo");
}
protected void getGenericValue(GenericValue val) {
val.set("customMethodId",OrderMaxUtility.getValidEntityString(this.customMethodId));
val.set("conversionFactor",OrderMaxUtility.getValidEntityDouble(this.conversionFactor));
val.set("roundingMode",OrderMaxUtility.getValidEntityString(this.roundingMode));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("decimalScale",OrderMaxUtility.getValidEntityString(this.decimalScale));
val.set("uomId",OrderMaxUtility.getValidEntityString(this.uomId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("uomIdTo",OrderMaxUtility.getValidEntityString(this.uomIdTo));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("customMethodId",this.customMethodId);
valueMap.put("conversionFactor",this.conversionFactor);
valueMap.put("roundingMode",this.roundingMode);
valueMap.put("decimalScale",this.decimalScale);
valueMap.put("uomId",this.uomId);
valueMap.put("uomIdTo",this.uomIdTo);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("UomConversion");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String customMethodId;
public java.lang.String getcustomMethodId() {
return customMethodId;
}
public void setcustomMethodId( java.lang.String customMethodId ) {
this.customMethodId = customMethodId;
}
private java.lang.Double conversionFactor;
public java.lang.Double getconversionFactor() {
return conversionFactor;
}
public void setconversionFactor( java.lang.Double conversionFactor ) {
this.conversionFactor = conversionFactor;
}
private java.lang.String roundingMode;
public java.lang.String getroundingMode() {
return roundingMode;
}
public void setroundingMode( java.lang.String roundingMode ) {
this.roundingMode = roundingMode;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
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
private java.lang.String decimalScale;
public java.lang.String getdecimalScale() {
return decimalScale;
}
public void setdecimalScale( java.lang.String decimalScale ) {
this.decimalScale = decimalScale;
}
private java.lang.String uomId;
public java.lang.String getuomId() {
return uomId;
}
public void setuomId( java.lang.String uomId ) {
this.uomId = uomId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String uomIdTo;
public java.lang.String getuomIdTo() {
return uomIdTo;
}
public void setuomIdTo( java.lang.String uomIdTo ) {
this.uomIdTo = uomIdTo;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<UomConversion> objectList = new ArrayList<UomConversion>();
        for (GenericValue genVal : genList) {
            objectList.add(new UomConversion(genVal));
        }
        return objectList;
    }    
}
