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
public class PortletAttribute implements GenericValueObjectInterface {
public static final String module =PortletAttribute.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PortletAttribute(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PortletAttribute () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"portalPageId","Portal Page Id"},
{"portletSeqId","Portlet Seq Id"},
{"attrType","Attr Type"},
{"portalPortletId","Portal Portlet Id"},
{"attrName","Attr Name"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"attrValue","Attr Value"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.portalPageId = "";
this.portletSeqId = "";
this.attrType = "";
this.portalPortletId = "";
this.attrName = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.attrValue = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.portalPageId = (java.lang.String) genVal.get("portalPageId");
this.portletSeqId = (java.lang.String) genVal.get("portletSeqId");
this.attrType = (java.lang.String) genVal.get("attrType");
this.portalPortletId = (java.lang.String) genVal.get("portalPortletId");
this.attrName = (java.lang.String) genVal.get("attrName");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.attrValue = (java.lang.String) genVal.get("attrValue");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("portalPageId",OrderMaxUtility.getValidEntityString(this.portalPageId));
val.set("portletSeqId",OrderMaxUtility.getValidEntityString(this.portletSeqId));
val.set("attrType",OrderMaxUtility.getValidEntityString(this.attrType));
val.set("portalPortletId",OrderMaxUtility.getValidEntityString(this.portalPortletId));
val.set("attrName",OrderMaxUtility.getValidEntityString(this.attrName));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("attrValue",OrderMaxUtility.getValidEntityString(this.attrValue));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("portalPageId",this.portalPageId);
valueMap.put("portletSeqId",this.portletSeqId);
valueMap.put("attrType",this.attrType);
valueMap.put("portalPortletId",this.portalPortletId);
valueMap.put("attrName",this.attrName);
valueMap.put("attrValue",this.attrValue);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PortletAttribute");
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
private java.lang.String portalPageId;
public java.lang.String getportalPageId() {
return portalPageId;
}
public void setportalPageId( java.lang.String portalPageId ) {
this.portalPageId = portalPageId;
}
private java.lang.String portletSeqId;
public java.lang.String getportletSeqId() {
return portletSeqId;
}
public void setportletSeqId( java.lang.String portletSeqId ) {
this.portletSeqId = portletSeqId;
}
private java.lang.String attrType;
public java.lang.String getattrType() {
return attrType;
}
public void setattrType( java.lang.String attrType ) {
this.attrType = attrType;
}
private java.lang.String portalPortletId;
public java.lang.String getportalPortletId() {
return portalPortletId;
}
public void setportalPortletId( java.lang.String portalPortletId ) {
this.portalPortletId = portalPortletId;
}
private java.lang.String attrName;
public java.lang.String getattrName() {
return attrName;
}
public void setattrName( java.lang.String attrName ) {
this.attrName = attrName;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String attrValue;
public java.lang.String getattrValue() {
return attrValue;
}
public void setattrValue( java.lang.String attrValue ) {
this.attrValue = attrValue;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PortletAttribute> objectList = new ArrayList<PortletAttribute>();
        for (GenericValue genVal : genList) {
            objectList.add(new PortletAttribute(genVal));
        }
        return objectList;
    }    
}
