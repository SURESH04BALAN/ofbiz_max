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
public class EntityAuditLog implements GenericValueObjectInterface {
public static final String module =EntityAuditLog.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public EntityAuditLog(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public EntityAuditLog () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"pkCombinedValueText","Pk Combined Value Text"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"changedFieldName","Changed Field Name"},
{"newValueText","New Value Text"},
{"changedEntityName","Changed Entity Name"},
{"changedDate","Changed Date"},
{"auditHistorySeqId","Audit History Seq Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"changedSessionInfo","Changed Session Info"},
{"changedByInfo","Changed By Info"},
{"oldValueText","Old Value Text"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.pkCombinedValueText = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.changedFieldName = "";
this.newValueText = "";
this.changedEntityName = "";
this.changedDate = UtilDateTime.nowTimestamp();
this.auditHistorySeqId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.changedSessionInfo = "";
this.changedByInfo = "";
this.oldValueText = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.pkCombinedValueText = (java.lang.String) genVal.get("pkCombinedValueText");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.changedFieldName = (java.lang.String) genVal.get("changedFieldName");
this.newValueText = (java.lang.String) genVal.get("newValueText");
this.changedEntityName = (java.lang.String) genVal.get("changedEntityName");
this.changedDate = (java.sql.Timestamp) genVal.get("changedDate");
this.auditHistorySeqId = (java.lang.String) genVal.get("auditHistorySeqId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.changedSessionInfo = (java.lang.String) genVal.get("changedSessionInfo");
this.changedByInfo = (java.lang.String) genVal.get("changedByInfo");
this.oldValueText = (java.lang.String) genVal.get("oldValueText");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("pkCombinedValueText",OrderMaxUtility.getValidEntityString(this.pkCombinedValueText));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("changedFieldName",OrderMaxUtility.getValidEntityString(this.changedFieldName));
val.set("newValueText",OrderMaxUtility.getValidEntityString(this.newValueText));
val.set("changedEntityName",OrderMaxUtility.getValidEntityString(this.changedEntityName));
val.set("changedDate",OrderMaxUtility.getValidEntityTimestamp(this.changedDate));
val.set("auditHistorySeqId",OrderMaxUtility.getValidEntityString(this.auditHistorySeqId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("changedSessionInfo",OrderMaxUtility.getValidEntityString(this.changedSessionInfo));
val.set("changedByInfo",OrderMaxUtility.getValidEntityString(this.changedByInfo));
val.set("oldValueText",OrderMaxUtility.getValidEntityString(this.oldValueText));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("pkCombinedValueText",this.pkCombinedValueText);
valueMap.put("changedFieldName",this.changedFieldName);
valueMap.put("newValueText",this.newValueText);
valueMap.put("changedEntityName",this.changedEntityName);
valueMap.put("changedDate",this.changedDate);
valueMap.put("auditHistorySeqId",this.auditHistorySeqId);
valueMap.put("changedSessionInfo",this.changedSessionInfo);
valueMap.put("changedByInfo",this.changedByInfo);
valueMap.put("oldValueText",this.oldValueText);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("EntityAuditLog");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String pkCombinedValueText;
public java.lang.String getpkCombinedValueText() {
return pkCombinedValueText;
}
public void setpkCombinedValueText( java.lang.String pkCombinedValueText ) {
this.pkCombinedValueText = pkCombinedValueText;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String changedFieldName;
public java.lang.String getchangedFieldName() {
return changedFieldName;
}
public void setchangedFieldName( java.lang.String changedFieldName ) {
this.changedFieldName = changedFieldName;
}
private java.lang.String newValueText;
public java.lang.String getnewValueText() {
return newValueText;
}
public void setnewValueText( java.lang.String newValueText ) {
this.newValueText = newValueText;
}
private java.lang.String changedEntityName;
public java.lang.String getchangedEntityName() {
return changedEntityName;
}
public void setchangedEntityName( java.lang.String changedEntityName ) {
this.changedEntityName = changedEntityName;
}
private java.sql.Timestamp changedDate;
public java.sql.Timestamp getchangedDate() {
return changedDate;
}
public void setchangedDate( java.sql.Timestamp changedDate ) {
this.changedDate = changedDate;
}
private java.lang.String auditHistorySeqId;
public java.lang.String getauditHistorySeqId() {
return auditHistorySeqId;
}
public void setauditHistorySeqId( java.lang.String auditHistorySeqId ) {
this.auditHistorySeqId = auditHistorySeqId;
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
private java.lang.String changedSessionInfo;
public java.lang.String getchangedSessionInfo() {
return changedSessionInfo;
}
public void setchangedSessionInfo( java.lang.String changedSessionInfo ) {
this.changedSessionInfo = changedSessionInfo;
}
private java.lang.String changedByInfo;
public java.lang.String getchangedByInfo() {
return changedByInfo;
}
public void setchangedByInfo( java.lang.String changedByInfo ) {
this.changedByInfo = changedByInfo;
}
private java.lang.String oldValueText;
public java.lang.String getoldValueText() {
return oldValueText;
}
public void setoldValueText( java.lang.String oldValueText ) {
this.oldValueText = oldValueText;
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
        Collection<EntityAuditLog> objectList = new ArrayList<EntityAuditLog>();
        for (GenericValue genVal : genList) {
            objectList.add(new EntityAuditLog(genVal));
        }
        return objectList;
    }    
}
