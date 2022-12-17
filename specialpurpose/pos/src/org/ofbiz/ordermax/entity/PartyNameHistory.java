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
public class PartyNameHistory implements GenericValueObjectInterface {
public static final String module =PartyNameHistory.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyNameHistory(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyNameHistory () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"middleName","Middle Name"},
{"lastName","Last Name"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"groupName","Group Name"},
{"personalTitle","Personal Title"},
{"suffix","Suffix"},
{"changeDate","Change Date"},
{"createdTxStamp","Created Tx Stamp"},
{"partyId","Party Id"},
{"createdStamp","Created Stamp"},
{"firstName","First Name"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.middleName = "";
this.lastName = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.groupName = "";
this.personalTitle = "";
this.suffix = "";
this.changeDate = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.partyId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.firstName = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.middleName = (java.lang.String) genVal.get("middleName");
this.lastName = (java.lang.String) genVal.get("lastName");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.groupName = (java.lang.String) genVal.get("groupName");
this.personalTitle = (java.lang.String) genVal.get("personalTitle");
this.suffix = (java.lang.String) genVal.get("suffix");
this.changeDate = (java.sql.Timestamp) genVal.get("changeDate");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.partyId = (java.lang.String) genVal.get("partyId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.firstName = (java.lang.String) genVal.get("firstName");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("middleName",OrderMaxUtility.getValidEntityString(this.middleName));
val.set("lastName",OrderMaxUtility.getValidEntityString(this.lastName));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("personalTitle",OrderMaxUtility.getValidEntityString(this.personalTitle));
val.set("suffix",OrderMaxUtility.getValidEntityString(this.suffix));
val.set("changeDate",OrderMaxUtility.getValidEntityTimestamp(this.changeDate));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("firstName",OrderMaxUtility.getValidEntityString(this.firstName));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("middleName",this.middleName);
valueMap.put("lastName",this.lastName);
valueMap.put("groupName",this.groupName);
valueMap.put("personalTitle",this.personalTitle);
valueMap.put("suffix",this.suffix);
valueMap.put("changeDate",this.changeDate);
valueMap.put("partyId",this.partyId);
valueMap.put("firstName",this.firstName);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyNameHistory");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String middleName;
public java.lang.String getmiddleName() {
return middleName;
}
public void setmiddleName( java.lang.String middleName ) {
this.middleName = middleName;
}
private java.lang.String lastName;
public java.lang.String getlastName() {
return lastName;
}
public void setlastName( java.lang.String lastName ) {
this.lastName = lastName;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String groupName;
public java.lang.String getgroupName() {
return groupName;
}
public void setgroupName( java.lang.String groupName ) {
this.groupName = groupName;
}
private java.lang.String personalTitle;
public java.lang.String getpersonalTitle() {
return personalTitle;
}
public void setpersonalTitle( java.lang.String personalTitle ) {
this.personalTitle = personalTitle;
}
private java.lang.String suffix;
public java.lang.String getsuffix() {
return suffix;
}
public void setsuffix( java.lang.String suffix ) {
this.suffix = suffix;
}
private java.sql.Timestamp changeDate;
public java.sql.Timestamp getchangeDate() {
return changeDate;
}
public void setchangeDate( java.sql.Timestamp changeDate ) {
this.changeDate = changeDate;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String firstName;
public java.lang.String getfirstName() {
return firstName;
}
public void setfirstName( java.lang.String firstName ) {
this.firstName = firstName;
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
        Collection<PartyNameHistory> objectList = new ArrayList<PartyNameHistory>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyNameHistory(genVal));
        }
        return objectList;
    }    
}
