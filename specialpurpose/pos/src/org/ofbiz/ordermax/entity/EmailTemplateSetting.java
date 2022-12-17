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
public class EmailTemplateSetting implements GenericValueObjectInterface {
public static final String module =EmailTemplateSetting.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public EmailTemplateSetting(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public EmailTemplateSetting () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"bodyScreenLocation","Body Screen Location"},
{"subject","Subject"},
{"contentType","Content Type"},
{"emailTemplateSettingId","Email Template Setting Id"},
{"ccAddress","Cc Address"},
{"createdTxStamp","Created Tx Stamp"},
{"xslfoAttachScreenLocation","Xslfo Attach Screen Location"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"fromAddress","From Address"},
{"bccAddress","Bcc Address"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.bodyScreenLocation = "";
this.subject = "";
this.contentType = "";
this.emailTemplateSettingId = "";
this.ccAddress = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.xslfoAttachScreenLocation = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.fromAddress = "";
this.bccAddress = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.bodyScreenLocation = (java.lang.String) genVal.get("bodyScreenLocation");
this.subject = (java.lang.String) genVal.get("subject");
this.contentType = (java.lang.String) genVal.get("contentType");
this.emailTemplateSettingId = (java.lang.String) genVal.get("emailTemplateSettingId");
this.ccAddress = (java.lang.String) genVal.get("ccAddress");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.xslfoAttachScreenLocation = (java.lang.String) genVal.get("xslfoAttachScreenLocation");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.fromAddress = (java.lang.String) genVal.get("fromAddress");
this.bccAddress = (java.lang.String) genVal.get("bccAddress");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("bodyScreenLocation",OrderMaxUtility.getValidEntityString(this.bodyScreenLocation));
val.set("subject",OrderMaxUtility.getValidEntityString(this.subject));
val.set("contentType",OrderMaxUtility.getValidEntityString(this.contentType));
val.set("emailTemplateSettingId",OrderMaxUtility.getValidEntityString(this.emailTemplateSettingId));
val.set("ccAddress",OrderMaxUtility.getValidEntityString(this.ccAddress));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("xslfoAttachScreenLocation",OrderMaxUtility.getValidEntityString(this.xslfoAttachScreenLocation));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("fromAddress",OrderMaxUtility.getValidEntityString(this.fromAddress));
val.set("bccAddress",OrderMaxUtility.getValidEntityString(this.bccAddress));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("bodyScreenLocation",this.bodyScreenLocation);
valueMap.put("subject",this.subject);
valueMap.put("contentType",this.contentType);
valueMap.put("emailTemplateSettingId",this.emailTemplateSettingId);
valueMap.put("ccAddress",this.ccAddress);
valueMap.put("xslfoAttachScreenLocation",this.xslfoAttachScreenLocation);
valueMap.put("description",this.description);
valueMap.put("fromAddress",this.fromAddress);
valueMap.put("bccAddress",this.bccAddress);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("EmailTemplateSetting");
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
private java.lang.String bodyScreenLocation;
public java.lang.String getbodyScreenLocation() {
return bodyScreenLocation;
}
public void setbodyScreenLocation( java.lang.String bodyScreenLocation ) {
this.bodyScreenLocation = bodyScreenLocation;
}
private java.lang.String subject;
public java.lang.String getsubject() {
return subject;
}
public void setsubject( java.lang.String subject ) {
this.subject = subject;
}
private java.lang.String contentType;
public java.lang.String getcontentType() {
return contentType;
}
public void setcontentType( java.lang.String contentType ) {
this.contentType = contentType;
}
private java.lang.String emailTemplateSettingId;
public java.lang.String getemailTemplateSettingId() {
return emailTemplateSettingId;
}
public void setemailTemplateSettingId( java.lang.String emailTemplateSettingId ) {
this.emailTemplateSettingId = emailTemplateSettingId;
}
private java.lang.String ccAddress;
public java.lang.String getccAddress() {
return ccAddress;
}
public void setccAddress( java.lang.String ccAddress ) {
this.ccAddress = ccAddress;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String xslfoAttachScreenLocation;
public java.lang.String getxslfoAttachScreenLocation() {
return xslfoAttachScreenLocation;
}
public void setxslfoAttachScreenLocation( java.lang.String xslfoAttachScreenLocation ) {
this.xslfoAttachScreenLocation = xslfoAttachScreenLocation;
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
private java.lang.String fromAddress;
public java.lang.String getfromAddress() {
return fromAddress;
}
public void setfromAddress( java.lang.String fromAddress ) {
this.fromAddress = fromAddress;
}
private java.lang.String bccAddress;
public java.lang.String getbccAddress() {
return bccAddress;
}
public void setbccAddress( java.lang.String bccAddress ) {
this.bccAddress = bccAddress;
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
        Collection<EmailTemplateSetting> objectList = new ArrayList<EmailTemplateSetting>();
        for (GenericValue genVal : genList) {
            objectList.add(new EmailTemplateSetting(genVal));
        }
        return objectList;
    }    
}
