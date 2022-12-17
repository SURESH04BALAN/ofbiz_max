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
public class PortalPagePortlet implements GenericValueObjectInterface {
public static final String module =PortalPagePortlet.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PortalPagePortlet(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PortalPagePortlet () {
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
{"portalPortletId","Portal Portlet Id"},
{"columnSeqId","Column Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"sequenceNum","Sequence Num"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.portalPageId = "";
this.portletSeqId = "";
this.portalPortletId = "";
this.columnSeqId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.sequenceNum = new Long(0);
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
this.portalPortletId = (java.lang.String) genVal.get("portalPortletId");
this.columnSeqId = (java.lang.String) genVal.get("columnSeqId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.sequenceNum = (java.lang.Long) genVal.get("sequenceNum");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("portalPageId",OrderMaxUtility.getValidEntityString(this.portalPageId));
val.set("portletSeqId",OrderMaxUtility.getValidEntityString(this.portletSeqId));
val.set("portalPortletId",OrderMaxUtility.getValidEntityString(this.portalPortletId));
val.set("columnSeqId",OrderMaxUtility.getValidEntityString(this.columnSeqId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("sequenceNum",this.sequenceNum);
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("portalPageId",this.portalPageId);
valueMap.put("portletSeqId",this.portletSeqId);
valueMap.put("portalPortletId",this.portalPortletId);
valueMap.put("columnSeqId",this.columnSeqId);
valueMap.put("sequenceNum",this.sequenceNum);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PortalPagePortlet");
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
private java.lang.String portalPortletId;
public java.lang.String getportalPortletId() {
return portalPortletId;
}
public void setportalPortletId( java.lang.String portalPortletId ) {
this.portalPortletId = portalPortletId;
}
private java.lang.String columnSeqId;
public java.lang.String getcolumnSeqId() {
return columnSeqId;
}
public void setcolumnSeqId( java.lang.String columnSeqId ) {
this.columnSeqId = columnSeqId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.Long sequenceNum;
public java.lang.Long getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.Long sequenceNum ) {
this.sequenceNum = sequenceNum;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PortalPagePortlet> objectList = new ArrayList<PortalPagePortlet>();
        for (GenericValue genVal : genList) {
            objectList.add(new PortalPagePortlet(genVal));
        }
        return objectList;
    }    
}
