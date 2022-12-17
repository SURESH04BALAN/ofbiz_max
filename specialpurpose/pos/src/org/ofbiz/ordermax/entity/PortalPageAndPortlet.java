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
public class PortalPageAndPortlet implements GenericValueObjectInterface {
public static final String module =PortalPageAndPortlet.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PortalPageAndPortlet(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PortalPageAndPortlet () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"portletSequenceNum","Portlet Sequence Num"},
{"portletSeqId","Portlet Seq Id"},
{"parentPortalPageId","Parent Portal Page Id"},
{"originalPortalPageId","Original Portal Page Id"},
{"portalPortletId","Portal Portlet Id"},
{"securityGroupId","Security Group Id"},
{"ownerUserLoginId","Owner User Login Id"},
{"sequenceNum","Sequence Num"},
{"description","Description"},
{"portalPageId","Portal Page Id"},
{"portalPageName","Portal Page Name"},
{"helpContentId","Help Content Id"},
{"columnSeqId","Column Seq Id"},
};
protected void initObject(){
this.portletSequenceNum = new Long(0);
this.portletSeqId = "";
this.parentPortalPageId = "";
this.originalPortalPageId = "";
this.portalPortletId = "";
this.securityGroupId = "";
this.ownerUserLoginId = "";
this.sequenceNum = new Long(0);
this.description = "";
this.portalPageId = "";
this.portalPageName = "";
this.helpContentId = "";
this.columnSeqId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.portletSequenceNum = (java.lang.Long) genVal.get("portletSequenceNum");
this.portletSeqId = (java.lang.String) genVal.get("portletSeqId");
this.parentPortalPageId = (java.lang.String) genVal.get("parentPortalPageId");
this.originalPortalPageId = (java.lang.String) genVal.get("originalPortalPageId");
this.portalPortletId = (java.lang.String) genVal.get("portalPortletId");
this.securityGroupId = (java.lang.String) genVal.get("securityGroupId");
this.ownerUserLoginId = (java.lang.String) genVal.get("ownerUserLoginId");
this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
this.description = (java.lang.String) genVal.get("description");
this.portalPageId = (java.lang.String) genVal.get("portalPageId");
this.portalPageName = (java.lang.String) genVal.get("portalPageName");
this.helpContentId = (java.lang.String) genVal.get("helpContentId");
this.columnSeqId = (java.lang.String) genVal.get("columnSeqId");
}
protected void getGenericValue(GenericValue val) {
val.set("portletSequenceNum",this.portletSequenceNum);
val.set("portletSeqId",OrderMaxUtility.getValidEntityString(this.portletSeqId));
val.set("parentPortalPageId",OrderMaxUtility.getValidEntityString(this.parentPortalPageId));
val.set("originalPortalPageId",OrderMaxUtility.getValidEntityString(this.originalPortalPageId));
val.set("portalPortletId",OrderMaxUtility.getValidEntityString(this.portalPortletId));
val.set("securityGroupId",OrderMaxUtility.getValidEntityString(this.securityGroupId));
val.set("ownerUserLoginId",OrderMaxUtility.getValidEntityString(this.ownerUserLoginId));
val.set("sequenceNum",this.sequenceNum);
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("portalPageId",OrderMaxUtility.getValidEntityString(this.portalPageId));
val.set("portalPageName",OrderMaxUtility.getValidEntityString(this.portalPageName));
val.set("helpContentId",OrderMaxUtility.getValidEntityString(this.helpContentId));
val.set("columnSeqId",OrderMaxUtility.getValidEntityString(this.columnSeqId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("portletSequenceNum",this.portletSequenceNum);
valueMap.put("portletSeqId",this.portletSeqId);
valueMap.put("parentPortalPageId",this.parentPortalPageId);
valueMap.put("originalPortalPageId",this.originalPortalPageId);
valueMap.put("portalPortletId",this.portalPortletId);
valueMap.put("securityGroupId",this.securityGroupId);
valueMap.put("ownerUserLoginId",this.ownerUserLoginId);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("description",this.description);
valueMap.put("portalPageId",this.portalPageId);
valueMap.put("portalPageName",this.portalPageName);
valueMap.put("helpContentId",this.helpContentId);
valueMap.put("columnSeqId",this.columnSeqId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PortalPageAndPortlet");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.Long portletSequenceNum;
public java.lang.Long getportletSequenceNum() {
return portletSequenceNum;
}
public void setportletSequenceNum( java.lang.Long portletSequenceNum ) {
this.portletSequenceNum = portletSequenceNum;
}
private java.lang.String portletSeqId;
public java.lang.String getportletSeqId() {
return portletSeqId;
}
public void setportletSeqId( java.lang.String portletSeqId ) {
this.portletSeqId = portletSeqId;
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
private java.lang.String portalPortletId;
public java.lang.String getportalPortletId() {
return portalPortletId;
}
public void setportalPortletId( java.lang.String portalPortletId ) {
this.portalPortletId = portalPortletId;
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
private java.lang.String columnSeqId;
public java.lang.String getcolumnSeqId() {
return columnSeqId;
}
public void setcolumnSeqId( java.lang.String columnSeqId ) {
this.columnSeqId = columnSeqId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PortalPageAndPortlet> objectList = new ArrayList<PortalPageAndPortlet>();
        for (GenericValue genVal : genList) {
            objectList.add(new PortalPageAndPortlet(genVal));
        }
        return objectList;
    }    
}
