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
public class PortalPortlet implements GenericValueObjectInterface {
public static final String module =PortalPortlet.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PortalPortlet(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PortalPortlet () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"screenshot","Screenshot"},
{"portletName","Portlet Name"},
{"screenName","Screen Name"},
{"portalPortletId","Portal Portlet Id"},
{"editFormLocation","Edit Form Location"},
{"securityMainAction","Security Main Action"},
{"securityServiceName","Security Service Name"},
{"screenLocation","Screen Location"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"editFormName","Edit Form Name"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.screenshot = "";
this.portletName = "";
this.screenName = "";
this.portalPortletId = "";
this.editFormLocation = "";
this.securityMainAction = "";
this.securityServiceName = "";
this.screenLocation = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.editFormName = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.screenshot = (java.lang.String) genVal.get("screenshot");
this.portletName = (java.lang.String) genVal.get("portletName");
this.screenName = (java.lang.String) genVal.get("screenName");
this.portalPortletId = (java.lang.String) genVal.get("portalPortletId");
this.editFormLocation = (java.lang.String) genVal.get("editFormLocation");
this.securityMainAction = (java.lang.String) genVal.get("securityMainAction");
this.securityServiceName = (java.lang.String) genVal.get("securityServiceName");
this.screenLocation = (java.lang.String) genVal.get("screenLocation");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.editFormName = (java.lang.String) genVal.get("editFormName");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("screenshot",OrderMaxUtility.getValidEntityString(this.screenshot));
val.set("portletName",OrderMaxUtility.getValidEntityString(this.portletName));
val.set("screenName",OrderMaxUtility.getValidEntityString(this.screenName));
val.set("portalPortletId",OrderMaxUtility.getValidEntityString(this.portalPortletId));
val.set("editFormLocation",OrderMaxUtility.getValidEntityString(this.editFormLocation));
val.set("securityMainAction",OrderMaxUtility.getValidEntityString(this.securityMainAction));
val.set("securityServiceName",OrderMaxUtility.getValidEntityString(this.securityServiceName));
val.set("screenLocation",OrderMaxUtility.getValidEntityString(this.screenLocation));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("editFormName",OrderMaxUtility.getValidEntityString(this.editFormName));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("screenshot",this.screenshot);
valueMap.put("portletName",this.portletName);
valueMap.put("screenName",this.screenName);
valueMap.put("portalPortletId",this.portalPortletId);
valueMap.put("editFormLocation",this.editFormLocation);
valueMap.put("securityMainAction",this.securityMainAction);
valueMap.put("securityServiceName",this.securityServiceName);
valueMap.put("screenLocation",this.screenLocation);
valueMap.put("description",this.description);
valueMap.put("editFormName",this.editFormName);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PortalPortlet");
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
private java.lang.String screenshot;
public java.lang.String getscreenshot() {
return screenshot;
}
public void setscreenshot( java.lang.String screenshot ) {
this.screenshot = screenshot;
}
private java.lang.String portletName;
public java.lang.String getportletName() {
return portletName;
}
public void setportletName( java.lang.String portletName ) {
this.portletName = portletName;
}
private java.lang.String screenName;
public java.lang.String getscreenName() {
return screenName;
}
public void setscreenName( java.lang.String screenName ) {
this.screenName = screenName;
}
private java.lang.String portalPortletId;
public java.lang.String getportalPortletId() {
return portalPortletId;
}
public void setportalPortletId( java.lang.String portalPortletId ) {
this.portalPortletId = portalPortletId;
}
private java.lang.String editFormLocation;
public java.lang.String geteditFormLocation() {
return editFormLocation;
}
public void seteditFormLocation( java.lang.String editFormLocation ) {
this.editFormLocation = editFormLocation;
}
private java.lang.String securityMainAction;
public java.lang.String getsecurityMainAction() {
return securityMainAction;
}
public void setsecurityMainAction( java.lang.String securityMainAction ) {
this.securityMainAction = securityMainAction;
}
private java.lang.String securityServiceName;
public java.lang.String getsecurityServiceName() {
return securityServiceName;
}
public void setsecurityServiceName( java.lang.String securityServiceName ) {
this.securityServiceName = securityServiceName;
}
private java.lang.String screenLocation;
public java.lang.String getscreenLocation() {
return screenLocation;
}
public void setscreenLocation( java.lang.String screenLocation ) {
this.screenLocation = screenLocation;
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
private java.lang.String editFormName;
public java.lang.String geteditFormName() {
return editFormName;
}
public void seteditFormName( java.lang.String editFormName ) {
this.editFormName = editFormName;
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
        Collection<PortalPortlet> objectList = new ArrayList<PortalPortlet>();
        for (GenericValue genVal : genList) {
            objectList.add(new PortalPortlet(genVal));
        }
        return objectList;
    }    
}
