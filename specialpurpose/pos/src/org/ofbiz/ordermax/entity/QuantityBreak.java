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
public class QuantityBreak implements GenericValueObjectInterface {
public static final String module =QuantityBreak.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public QuantityBreak(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public QuantityBreak () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"thruQuantity","Thru Quantity"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"quantityBreakId","Quantity Break Id"},
{"quantityBreakTypeId","Quantity Break Type Id"},
{"fromQuantity","From Quantity"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.thruQuantity = java.math.BigDecimal.ZERO;
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.quantityBreakId = "";
this.quantityBreakTypeId = "";
this.fromQuantity = java.math.BigDecimal.ZERO;
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.thruQuantity = (java.math.BigDecimal) genVal.get("thruQuantity");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.quantityBreakId = (java.lang.String) genVal.get("quantityBreakId");
this.quantityBreakTypeId = (java.lang.String) genVal.get("quantityBreakTypeId");
this.fromQuantity = (java.math.BigDecimal) genVal.get("fromQuantity");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("thruQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.thruQuantity));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("quantityBreakId",OrderMaxUtility.getValidEntityString(this.quantityBreakId));
val.set("quantityBreakTypeId",OrderMaxUtility.getValidEntityString(this.quantityBreakTypeId));
val.set("fromQuantity",OrderMaxUtility.getValidEntityBigDecimal(this.fromQuantity));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("thruQuantity",this.thruQuantity);
valueMap.put("quantityBreakId",this.quantityBreakId);
valueMap.put("quantityBreakTypeId",this.quantityBreakTypeId);
valueMap.put("fromQuantity",this.fromQuantity);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("QuantityBreak");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.math.BigDecimal thruQuantity;
public java.math.BigDecimal getthruQuantity() {
return thruQuantity;
}
public void setthruQuantity( java.math.BigDecimal thruQuantity ) {
this.thruQuantity = thruQuantity;
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
private java.lang.String quantityBreakId;
public java.lang.String getquantityBreakId() {
return quantityBreakId;
}
public void setquantityBreakId( java.lang.String quantityBreakId ) {
this.quantityBreakId = quantityBreakId;
}
private java.lang.String quantityBreakTypeId;
public java.lang.String getquantityBreakTypeId() {
return quantityBreakTypeId;
}
public void setquantityBreakTypeId( java.lang.String quantityBreakTypeId ) {
this.quantityBreakTypeId = quantityBreakTypeId;
}
private java.math.BigDecimal fromQuantity;
public java.math.BigDecimal getfromQuantity() {
return fromQuantity;
}
public void setfromQuantity( java.math.BigDecimal fromQuantity ) {
this.fromQuantity = fromQuantity;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<QuantityBreak> objectList = new ArrayList<QuantityBreak>();
        for (GenericValue genVal : genList) {
            objectList.add(new QuantityBreak(genVal));
        }
        return objectList;
    }    
}
