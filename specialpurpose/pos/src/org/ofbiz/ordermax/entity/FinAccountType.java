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
public class FinAccountType implements GenericValueObjectInterface {
public static final String module =FinAccountType.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public FinAccountType(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public FinAccountType () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"isRefundable","Is Refundable"},
{"createdTxStamp","Created Tx Stamp"},
{"hasTable","Has Table"},
{"createdStamp","Created Stamp"},
{"parentTypeId","Parent Type Id"},
{"description","Description"},
{"replenishEnumId","Replenish Enum Id"},
{"finAccountTypeId","Fin Account Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.isRefundable = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.hasTable = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.parentTypeId = "";
this.description = "";
this.replenishEnumId = "";
this.finAccountTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.isRefundable = (java.lang.String) genVal.get("isRefundable");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.hasTable = (java.lang.String) genVal.get("hasTable");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.parentTypeId = (java.lang.String) genVal.get("parentTypeId");
this.description = (java.lang.String) genVal.get("description");
this.replenishEnumId = (java.lang.String) genVal.get("replenishEnumId");
this.finAccountTypeId = (java.lang.String) genVal.get("finAccountTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("isRefundable",OrderMaxUtility.getValidEntityString(this.isRefundable));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("hasTable",OrderMaxUtility.getValidEntityString(this.hasTable));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("parentTypeId",OrderMaxUtility.getValidEntityString(this.parentTypeId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("replenishEnumId",OrderMaxUtility.getValidEntityString(this.replenishEnumId));
val.set("finAccountTypeId",OrderMaxUtility.getValidEntityString(this.finAccountTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("isRefundable",this.isRefundable);
valueMap.put("hasTable",this.hasTable);
valueMap.put("parentTypeId",this.parentTypeId);
valueMap.put("description",this.description);
valueMap.put("replenishEnumId",this.replenishEnumId);
valueMap.put("finAccountTypeId",this.finAccountTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("FinAccountType");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String isRefundable;
public java.lang.String getisRefundable() {
return isRefundable;
}
public void setisRefundable( java.lang.String isRefundable ) {
this.isRefundable = isRefundable;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String hasTable;
public java.lang.String gethasTable() {
return hasTable;
}
public void sethasTable( java.lang.String hasTable ) {
this.hasTable = hasTable;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String parentTypeId;
public java.lang.String getparentTypeId() {
return parentTypeId;
}
public void setparentTypeId( java.lang.String parentTypeId ) {
this.parentTypeId = parentTypeId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String replenishEnumId;
public java.lang.String getreplenishEnumId() {
return replenishEnumId;
}
public void setreplenishEnumId( java.lang.String replenishEnumId ) {
this.replenishEnumId = replenishEnumId;
}
private java.lang.String finAccountTypeId;
public java.lang.String getfinAccountTypeId() {
return finAccountTypeId;
}
public void setfinAccountTypeId( java.lang.String finAccountTypeId ) {
this.finAccountTypeId = finAccountTypeId;
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
        Collection<FinAccountType> objectList = new ArrayList<FinAccountType>();
        for (GenericValue genVal : genList) {
            objectList.add(new FinAccountType(genVal));
        }
        return objectList;
    }    
}
