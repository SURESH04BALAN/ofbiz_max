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
public class PartyContactWithPurpose implements GenericValueObjectInterface {
public static final String module =PartyContactWithPurpose.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyContactWithPurpose(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyContactWithPurpose () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"purposeThruDate","Purpose Thru Date"},
{"purposeFromDate","Purpose From Date"},
{"allowSolicitation","Allow Solicitation"},
{"contactMechId","Contact Mech Id"},
{"contactFromDate","Contact From Date"},
{"contactMechPurposeTypeId","Contact Mech Purpose Type Id"},
{"contactMechTypeId","Contact Mech Type Id"},
{"extension","Extension"},
{"partyId","Party Id"},
{"purposeDescription","Purpose Description"},
{"contactThruDate","Contact Thru Date"},
{"comments","Comments"},
{"infoString","Info String"},
};
protected void initObject(){
this.purposeThruDate = UtilDateTime.nowTimestamp();
this.purposeFromDate = UtilDateTime.nowTimestamp();
this.allowSolicitation = "";
this.contactMechId = "";
this.contactFromDate = UtilDateTime.nowTimestamp();
this.contactMechPurposeTypeId = "";
this.contactMechTypeId = "";
this.extension = "";
this.partyId = "";
this.purposeDescription = "";
this.contactThruDate = UtilDateTime.nowTimestamp();
this.comments = "";
this.infoString = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.purposeThruDate = (java.sql.Timestamp) genVal.get("purposeThruDate");
this.purposeFromDate = (java.sql.Timestamp) genVal.get("purposeFromDate");
this.allowSolicitation = (java.lang.String) genVal.get("allowSolicitation");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.contactFromDate = (java.sql.Timestamp) genVal.get("contactFromDate");
this.contactMechPurposeTypeId = (java.lang.String) genVal.get("contactMechPurposeTypeId");
this.contactMechTypeId = (java.lang.String) genVal.get("contactMechTypeId");
this.extension = (java.lang.String) genVal.get("extension");
this.partyId = (java.lang.String) genVal.get("partyId");
this.purposeDescription = (java.lang.String) genVal.get("purposeDescription");
this.contactThruDate = (java.sql.Timestamp) genVal.get("contactThruDate");
this.comments = (java.lang.String) genVal.get("comments");
this.infoString = (java.lang.String) genVal.get("infoString");
}
protected void getGenericValue(GenericValue val) {
val.set("purposeThruDate",OrderMaxUtility.getValidEntityTimestamp(this.purposeThruDate));
val.set("purposeFromDate",OrderMaxUtility.getValidEntityTimestamp(this.purposeFromDate));
val.set("allowSolicitation",OrderMaxUtility.getValidEntityString(this.allowSolicitation));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("contactFromDate",OrderMaxUtility.getValidEntityTimestamp(this.contactFromDate));
val.set("contactMechPurposeTypeId",OrderMaxUtility.getValidEntityString(this.contactMechPurposeTypeId));
val.set("contactMechTypeId",OrderMaxUtility.getValidEntityString(this.contactMechTypeId));
val.set("extension",OrderMaxUtility.getValidEntityString(this.extension));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("purposeDescription",OrderMaxUtility.getValidEntityString(this.purposeDescription));
val.set("contactThruDate",OrderMaxUtility.getValidEntityTimestamp(this.contactThruDate));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
val.set("infoString",OrderMaxUtility.getValidEntityString(this.infoString));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("purposeThruDate",this.purposeThruDate);
valueMap.put("purposeFromDate",this.purposeFromDate);
valueMap.put("allowSolicitation",this.allowSolicitation);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("contactFromDate",this.contactFromDate);
valueMap.put("contactMechPurposeTypeId",this.contactMechPurposeTypeId);
valueMap.put("contactMechTypeId",this.contactMechTypeId);
valueMap.put("extension",this.extension);
valueMap.put("partyId",this.partyId);
valueMap.put("purposeDescription",this.purposeDescription);
valueMap.put("contactThruDate",this.contactThruDate);
valueMap.put("comments",this.comments);
valueMap.put("infoString",this.infoString);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyContactWithPurpose");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp purposeThruDate;
public java.sql.Timestamp getpurposeThruDate() {
return purposeThruDate;
}
public void setpurposeThruDate( java.sql.Timestamp purposeThruDate ) {
this.purposeThruDate = purposeThruDate;
}
private java.sql.Timestamp purposeFromDate;
public java.sql.Timestamp getpurposeFromDate() {
return purposeFromDate;
}
public void setpurposeFromDate( java.sql.Timestamp purposeFromDate ) {
this.purposeFromDate = purposeFromDate;
}
private java.lang.String allowSolicitation;
public java.lang.String getallowSolicitation() {
return allowSolicitation;
}
public void setallowSolicitation( java.lang.String allowSolicitation ) {
this.allowSolicitation = allowSolicitation;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.sql.Timestamp contactFromDate;
public java.sql.Timestamp getcontactFromDate() {
return contactFromDate;
}
public void setcontactFromDate( java.sql.Timestamp contactFromDate ) {
this.contactFromDate = contactFromDate;
}
private java.lang.String contactMechPurposeTypeId;
public java.lang.String getcontactMechPurposeTypeId() {
return contactMechPurposeTypeId;
}
public void setcontactMechPurposeTypeId( java.lang.String contactMechPurposeTypeId ) {
this.contactMechPurposeTypeId = contactMechPurposeTypeId;
}
private java.lang.String contactMechTypeId;
public java.lang.String getcontactMechTypeId() {
return contactMechTypeId;
}
public void setcontactMechTypeId( java.lang.String contactMechTypeId ) {
this.contactMechTypeId = contactMechTypeId;
}
private java.lang.String extension;
public java.lang.String getextension() {
return extension;
}
public void setextension( java.lang.String extension ) {
this.extension = extension;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String purposeDescription;
public java.lang.String getpurposeDescription() {
return purposeDescription;
}
public void setpurposeDescription( java.lang.String purposeDescription ) {
this.purposeDescription = purposeDescription;
}
private java.sql.Timestamp contactThruDate;
public java.sql.Timestamp getcontactThruDate() {
return contactThruDate;
}
public void setcontactThruDate( java.sql.Timestamp contactThruDate ) {
this.contactThruDate = contactThruDate;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
private java.lang.String infoString;
public java.lang.String getinfoString() {
return infoString;
}
public void setinfoString( java.lang.String infoString ) {
this.infoString = infoString;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyContactWithPurpose> objectList = new ArrayList<PartyContactWithPurpose>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyContactWithPurpose(genVal));
        }
        return objectList;
    }    
}
