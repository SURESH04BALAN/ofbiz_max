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
public class PortalPage implements GenericValueObjectInterface {
public static final String module =PortalPage.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PortalPage(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PortalPage () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"parentPortalPageId","Parent Portal Page Id"},
{"originalPortalPageId","Original Portal Page Id"},
{"securityGroupId","Security Group Id"},
{"ownerUserLoginId","Owner User Login Id"},
{"sequenceNum","Sequence Num"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"portalPageId","Portal Page Id"},
{"portalPageName","Portal Page Name"},
{"helpContentId","Help Content Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.parentPortalPageId = "";
this.originalPortalPageId = "";
this.securityGroupId = "";
this.ownerUserLoginId = "";
this.sequenceNum = new Long(0);
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.portalPageId = "";
this.portalPageName = "";
this.helpContentId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.parentPortalPageId = (java.lang.String) genVal.get("parentPortalPageId");
this.originalPortalPageId = (java.lang.String) genVal.get("originalPortalPageId");
this.securityGroupId = (java.lang.String) genVal.get("securityGroupId");
this.ownerUserLoginId = (java.lang.String) genVal.get("ownerUserLoginId");
this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.portalPageId = (java.lang.String) genVal.get("portalPageId");
this.portalPageName = (java.lang.String) genVal.get("portalPageName");
this.helpContentId = (java.lang.String) genVal.get("helpContentId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("parentPortalPageId",OrderMaxUtility.getValidEntityString(this.parentPortalPageId));
val.set("originalPortalPageId",OrderMaxUtility.getValidEntityString(this.originalPortalPageId));
val.set("securityGroupId",OrderMaxUtility.getValidEntityString(this.securityGroupId));
val.set("ownerUserLoginId",OrderMaxUtility.getValidEntityString(this.ownerUserLoginId));
val.set("sequenceNum",this.sequenceNum);
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("portalPageId",OrderMaxUtility.getValidEntityString(this.portalPageId));
val.set("portalPageName",OrderMaxUtility.getValidEntityString(this.portalPageName));
val.set("helpContentId",OrderMaxUtility.getValidEntityString(this.helpContentId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("parentPortalPageId",this.parentPortalPageId);
valueMap.put("originalPortalPageId",this.originalPortalPageId);
valueMap.put("securityGroupId",this.securityGroupId);
valueMap.put("ownerUserLoginId",this.ownerUserLoginId);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("description",this.description);
valueMap.put("portalPageId",this.portalPageId);
valueMap.put("portalPageName",this.portalPageName);
valueMap.put("helpContentId",this.helpContentId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PortalPage");
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
private java.lang.String parentPortalPageId;
public java.lang.String getparentPortalPageId() {
return parentPortalPageId;
}
public void setparentPortalPageId( java.lang.String parentPortalPageId ) {
this.parentPortalPageId = parentPortalPageId;
}
private java.lang.String originalPortalPageId;
public java.lang.String getoriginalPortalPageId() {
return originalPortalPageId;
}
public void setoriginalPortalPageId( java.lang.String originalPortalPageId ) {
this.originalPortalPageId = originalPortalPageId;
}
private java.lang.String securityGroupId;
public java.lang.String getsecurityGroupId() {
return securityGroupId;
}
public void setsecurityGroupId( java.lang.String securityGroupId ) {
this.securityGroupId = securityGroupId;
}
private java.lang.String ownerUserLoginId;
public java.lang.String getownerUserLoginId() {
return ownerUserLoginId;
}
public void setownerUserLoginId( java.lang.String ownerUserLoginId ) {
this.ownerUserLoginId = ownerUserLoginId;
}
private java.lang.Long sequenceNum;
public java.lang.Long getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.Long sequenceNum ) {
this.sequenceNum = sequenceNum;
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
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String portalPageId;
public java.lang.String getportalPageId() {
return portalPageId;
}
public void setportalPageId( java.lang.String portalPageId ) {
this.portalPageId = portalPageId;
}
private java.lang.String portalPageName;
public java.lang.String getportalPageName() {
return portalPageName;
}
public void setportalPageName( java.lang.String portalPageName ) {
this.portalPageName = portalPageName;
}
private java.lang.String helpContentId;
public java.lang.String gethelpContentId() {
return helpContentId;
}
public void sethelpContentId( java.lang.String helpContentId ) {
this.helpContentId = helpContentId;
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
        Collection<PortalPage> objectList = new ArrayList<PortalPage>();
        for (GenericValue genVal : genList) {
            objectList.add(new PortalPage(genVal));
        }
        return objectList;
    }    
}
