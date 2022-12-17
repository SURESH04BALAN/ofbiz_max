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
public class PartyAndGroup implements GenericValueObjectInterface {
public static final String module =PartyAndGroup.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyAndGroup(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyAndGroup () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"groupName","Group Name"},
{"preferredCurrencyUomId","Preferred Currency Uom Id"},
{"externalId","External Id"},
{"partyTypeId","Party Type Id"},
{"isUnread","Is Unread"},
{"dataSourceId","Data Source Id"},
{"logoImageUrl","Logo Image Url"},
{"statusId","Status Id"},
{"groupNameLocal","Group Name Local"},
{"tickerSymbol","Ticker Symbol"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"officeSiteName","Office Site Name"},
{"annualRevenue","Annual Revenue"},
{"partyId","Party Id"},
{"description","Description"},
{"lastModifiedDate","Last Modified Date"},
{"createdDate","Created Date"},
{"numEmployees","Num Employees"},
{"comments","Comments"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.groupName = "";
this.preferredCurrencyUomId = "";
this.externalId = "";
this.partyTypeId = "";
this.isUnread = "";
this.dataSourceId = "";
this.logoImageUrl = "";
this.statusId = "";
this.groupNameLocal = "";
this.tickerSymbol = "";
this.lastModifiedByUserLogin = "";
this.officeSiteName = "";
this.annualRevenue = "";
this.partyId = "";
this.description = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.createdDate = UtilDateTime.nowTimestamp();
this.numEmployees = "";
this.comments = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.groupName = (java.lang.String) genVal.get("groupName");
this.preferredCurrencyUomId = (java.lang.String) genVal.get("preferredCurrencyUomId");
this.externalId = (java.lang.String) genVal.get("externalId");
this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
this.isUnread = (java.lang.String) genVal.get("isUnread");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.logoImageUrl = (java.lang.String) genVal.get("logoImageUrl");
this.statusId = (java.lang.String) genVal.get("statusId");
this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
this.tickerSymbol = (java.lang.String) genVal.get("tickerSymbol");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.officeSiteName = (java.lang.String) genVal.get("officeSiteName");
this.annualRevenue = (java.lang.String) genVal.get("annualRevenue");
this.partyId = (java.lang.String) genVal.get("partyId");
this.description = (java.lang.String) genVal.get("description");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.numEmployees = (java.lang.String) genVal.get("numEmployees");
this.comments = (java.lang.String) genVal.get("comments");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("preferredCurrencyUomId",OrderMaxUtility.getValidEntityString(this.preferredCurrencyUomId));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("partyTypeId",OrderMaxUtility.getValidEntityString(this.partyTypeId));
val.set("isUnread",OrderMaxUtility.getValidEntityString(this.isUnread));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("logoImageUrl",OrderMaxUtility.getValidEntityString(this.logoImageUrl));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("groupNameLocal",OrderMaxUtility.getValidEntityString(this.groupNameLocal));
val.set("tickerSymbol",OrderMaxUtility.getValidEntityString(this.tickerSymbol));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("officeSiteName",OrderMaxUtility.getValidEntityString(this.officeSiteName));
val.set("annualRevenue",OrderMaxUtility.getValidEntityString(this.annualRevenue));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("numEmployees",OrderMaxUtility.getValidEntityString(this.numEmployees));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("groupName",this.groupName);
valueMap.put("preferredCurrencyUomId",this.preferredCurrencyUomId);
valueMap.put("externalId",this.externalId);
valueMap.put("partyTypeId",this.partyTypeId);
valueMap.put("isUnread",this.isUnread);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("logoImageUrl",this.logoImageUrl);
valueMap.put("statusId",this.statusId);
valueMap.put("groupNameLocal",this.groupNameLocal);
valueMap.put("tickerSymbol",this.tickerSymbol);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("officeSiteName",this.officeSiteName);
valueMap.put("annualRevenue",this.annualRevenue);
valueMap.put("partyId",this.partyId);
valueMap.put("description",this.description);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("createdDate",this.createdDate);
valueMap.put("numEmployees",this.numEmployees);
valueMap.put("comments",this.comments);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyAndGroup");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String groupName;
public java.lang.String getgroupName() {
return groupName;
}
public void setgroupName( java.lang.String groupName ) {
this.groupName = groupName;
}
private java.lang.String preferredCurrencyUomId;
public java.lang.String getpreferredCurrencyUomId() {
return preferredCurrencyUomId;
}
public void setpreferredCurrencyUomId( java.lang.String preferredCurrencyUomId ) {
this.preferredCurrencyUomId = preferredCurrencyUomId;
}
private java.lang.String externalId;
public java.lang.String getexternalId() {
return externalId;
}
public void setexternalId( java.lang.String externalId ) {
this.externalId = externalId;
}
private java.lang.String partyTypeId;
public java.lang.String getpartyTypeId() {
return partyTypeId;
}
public void setpartyTypeId( java.lang.String partyTypeId ) {
this.partyTypeId = partyTypeId;
}
private java.lang.String isUnread;
public java.lang.String getisUnread() {
return isUnread;
}
public void setisUnread( java.lang.String isUnread ) {
this.isUnread = isUnread;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String logoImageUrl;
public java.lang.String getlogoImageUrl() {
return logoImageUrl;
}
public void setlogoImageUrl( java.lang.String logoImageUrl ) {
this.logoImageUrl = logoImageUrl;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String groupNameLocal;
public java.lang.String getgroupNameLocal() {
return groupNameLocal;
}
public void setgroupNameLocal( java.lang.String groupNameLocal ) {
this.groupNameLocal = groupNameLocal;
}
private java.lang.String tickerSymbol;
public java.lang.String gettickerSymbol() {
return tickerSymbol;
}
public void settickerSymbol( java.lang.String tickerSymbol ) {
this.tickerSymbol = tickerSymbol;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String officeSiteName;
public java.lang.String getofficeSiteName() {
return officeSiteName;
}
public void setofficeSiteName( java.lang.String officeSiteName ) {
this.officeSiteName = officeSiteName;
}
private java.lang.String annualRevenue;
public java.lang.String getannualRevenue() {
return annualRevenue;
}
public void setannualRevenue( java.lang.String annualRevenue ) {
this.annualRevenue = annualRevenue;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String numEmployees;
public java.lang.String getnumEmployees() {
return numEmployees;
}
public void setnumEmployees( java.lang.String numEmployees ) {
this.numEmployees = numEmployees;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyAndGroup> objectList = new ArrayList<PartyAndGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyAndGroup(genVal));
        }
        return objectList;
    }    
}
