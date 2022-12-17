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
public class ContentPurposeOperation implements GenericValueObjectInterface {
public static final String module =ContentPurposeOperation.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ContentPurposeOperation(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ContentPurposeOperation () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"contentPurposeTypeId","Content Purpose Type Id"},
{"statusId","Status Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"roleTypeId","Role Type Id"},
{"privilegeEnumId","Privilege Enum Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"contentOperationId","Content Operation Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.contentPurposeTypeId = "";
this.statusId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.roleTypeId = "";
this.privilegeEnumId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.contentOperationId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.contentPurposeTypeId = (java.lang.String) genVal.get("contentPurposeTypeId");
this.statusId = (java.lang.String) genVal.get("statusId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.privilegeEnumId = (java.lang.String) genVal.get("privilegeEnumId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.contentOperationId = (java.lang.String) genVal.get("contentOperationId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("contentPurposeTypeId",OrderMaxUtility.getValidEntityString(this.contentPurposeTypeId));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("privilegeEnumId",OrderMaxUtility.getValidEntityString(this.privilegeEnumId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("contentOperationId",OrderMaxUtility.getValidEntityString(this.contentOperationId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("contentPurposeTypeId",this.contentPurposeTypeId);
valueMap.put("statusId",this.statusId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("privilegeEnumId",this.privilegeEnumId);
valueMap.put("contentOperationId",this.contentOperationId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ContentPurposeOperation");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String contentPurposeTypeId;
public java.lang.String getcontentPurposeTypeId() {
return contentPurposeTypeId;
}
public void setcontentPurposeTypeId( java.lang.String contentPurposeTypeId ) {
this.contentPurposeTypeId = contentPurposeTypeId;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String privilegeEnumId;
public java.lang.String getprivilegeEnumId() {
return privilegeEnumId;
}
public void setprivilegeEnumId( java.lang.String privilegeEnumId ) {
this.privilegeEnumId = privilegeEnumId;
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
private java.lang.String contentOperationId;
public java.lang.String getcontentOperationId() {
return contentOperationId;
}
public void setcontentOperationId( java.lang.String contentOperationId ) {
this.contentOperationId = contentOperationId;
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
        Collection<ContentPurposeOperation> objectList = new ArrayList<ContentPurposeOperation>();
        for (GenericValue genVal : genList) {
            objectList.add(new ContentPurposeOperation(genVal));
        }
        return objectList;
    }    
}
