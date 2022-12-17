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
public class GlAccountType implements GenericValueObjectInterface {
public static final String module =GlAccountType.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GlAccountType(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GlAccountType () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"glAccountTypeId","Gl Account Type Id"},
{"createdTxStamp","Created Tx Stamp"},
{"hasTable","Has Table"},
{"createdStamp","Created Stamp"},
{"parentTypeId","Parent Type Id"},
{"description","Description"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.glAccountTypeId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.hasTable = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.parentTypeId = "";
this.description = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.glAccountTypeId = (java.lang.String) genVal.get("glAccountTypeId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.hasTable = (java.lang.String) genVal.get("hasTable");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.parentTypeId = (java.lang.String) genVal.get("parentTypeId");
this.description = (java.lang.String) genVal.get("description");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("glAccountTypeId",OrderMaxUtility.getValidEntityString(this.glAccountTypeId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("hasTable",OrderMaxUtility.getValidEntityString(this.hasTable));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("parentTypeId",OrderMaxUtility.getValidEntityString(this.parentTypeId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("glAccountTypeId",this.glAccountTypeId);
valueMap.put("hasTable",this.hasTable);
valueMap.put("parentTypeId",this.parentTypeId);
valueMap.put("description",this.description);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GlAccountType");
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
private java.lang.String glAccountTypeId;
public java.lang.String getglAccountTypeId() {
return glAccountTypeId;
}
public void setglAccountTypeId( java.lang.String glAccountTypeId ) {
this.glAccountTypeId = glAccountTypeId;
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
        Collection<GlAccountType> objectList = new ArrayList<GlAccountType>();
        for (GenericValue genVal : genList) {
            objectList.add(new GlAccountType(genVal));
        }
        return objectList;
    }    
}
