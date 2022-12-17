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
public class ContentAssocViewTo implements GenericValueObjectInterface {
public static final String module =ContentAssocViewTo.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ContentAssocViewTo(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ContentAssocViewTo () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"childLeafCount","Child Leaf Count"},
{"thruDate","Thru Date"},
{"ownerContentId","Owner Content Id"},
{"instanceOfContentId","Instance Of Content Id"},
{"caContentAssocPredicateId","Ca Content Assoc Predicate Id"},
{"dataResourceId","Data Resource Id"},
{"caDataSourceId","Ca Data Source Id"},
{"caLastModifiedByUserLogin","Ca Last Modified By User Login"},
{"contentId","Content Id"},
{"dataSourceId","Data Source Id"},
{"caCreatedByUserLogin","Ca Created By User Login"},
{"caLastModifiedDate","Ca Last Modified Date"},
{"serviceName","Service Name"},
{"childBranchCount","Child Branch Count"},
{"statusId","Status Id"},
{"caSequenceNum","Ca Sequence Num"},
{"privilegeEnumId","Privilege Enum Id"},
{"caCreatedDate","Ca Created Date"},
{"templateDataResourceId","Template Data Resource Id"},
{"description","Description"},
{"decoratorContentId","Decorator Content Id"},
{"createdDate","Created Date"},
{"caContentAssocTypeId","Ca Content Assoc Type Id"},
{"mimeTypeId","Mime Type Id"},
{"caUpperCoordinate","Ca Upper Coordinate"},
{"contentName","Content Name"},
{"caThruDate","Ca Thru Date"},
{"characterSetId","Character Set Id"},
{"caLeftCoordinate","Ca Left Coordinate"},
{"localeString","Locale String"},
{"fromDate","From Date"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"contentTypeId","Content Type Id"},
{"caFromDate","Ca From Date"},
{"caContentId","Ca Content Id"},
{"caMapKey","Ca Map Key"},
{"lastModifiedDate","Last Modified Date"},
{"contentIdStart","Content Id Start"},
{"caContentIdTo","Ca Content Id To"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.childLeafCount = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.ownerContentId = "";
this.instanceOfContentId = "";
this.caContentAssocPredicateId = "";
this.dataResourceId = "";
this.caDataSourceId = "";
this.caLastModifiedByUserLogin = "";
this.contentId = "";
this.dataSourceId = "";
this.caCreatedByUserLogin = "";
this.caLastModifiedDate = UtilDateTime.nowTimestamp();
this.serviceName = "";
this.childBranchCount = "";
this.statusId = "";
this.caSequenceNum = new Long(0);
this.privilegeEnumId = "";
this.caCreatedDate = UtilDateTime.nowTimestamp();
this.templateDataResourceId = "";
this.description = "";
this.decoratorContentId = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.caContentAssocTypeId = "";
this.mimeTypeId = "";
this.caUpperCoordinate = "";
this.contentName = "";
this.caThruDate = UtilDateTime.nowTimestamp();
this.characterSetId = "";
this.caLeftCoordinate = "";
this.localeString = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.lastModifiedByUserLogin = "";
this.contentTypeId = "";
this.caFromDate = UtilDateTime.nowTimestamp();
this.caContentId = "";
this.caMapKey = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.contentIdStart = "";
this.caContentIdTo = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.childLeafCount = (java.lang.String) genVal.get("childLeafCount");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.ownerContentId = (java.lang.String) genVal.get("ownerContentId");
this.instanceOfContentId = (java.lang.String) genVal.get("instanceOfContentId");
this.caContentAssocPredicateId = (java.lang.String) genVal.get("caContentAssocPredicateId");
this.dataResourceId = (java.lang.String) genVal.get("dataResourceId");
this.caDataSourceId = (java.lang.String) genVal.get("caDataSourceId");
this.caLastModifiedByUserLogin = (java.lang.String) genVal.get("caLastModifiedByUserLogin");
this.contentId = (java.lang.String) genVal.get("contentId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.caCreatedByUserLogin = (java.lang.String) genVal.get("caCreatedByUserLogin");
this.caLastModifiedDate = (java.sql.Timestamp) genVal.get("caLastModifiedDate");
this.serviceName = (java.lang.String) genVal.get("serviceName");
this.childBranchCount = (java.lang.String) genVal.get("childBranchCount");
this.statusId = (java.lang.String) genVal.get("statusId");
this.caSequenceNum = (java.lang.Long) genVal.get("caSequenceNum");
this.privilegeEnumId = (java.lang.String) genVal.get("privilegeEnumId");
this.caCreatedDate = (java.sql.Timestamp) genVal.get("caCreatedDate");
this.templateDataResourceId = (java.lang.String) genVal.get("templateDataResourceId");
this.description = (java.lang.String) genVal.get("description");
this.decoratorContentId = (java.lang.String) genVal.get("decoratorContentId");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.caContentAssocTypeId = (java.lang.String) genVal.get("caContentAssocTypeId");
this.mimeTypeId = (java.lang.String) genVal.get("mimeTypeId");
this.caUpperCoordinate = (java.lang.String) genVal.get("caUpperCoordinate");
this.contentName = (java.lang.String) genVal.get("contentName");
this.caThruDate = (java.sql.Timestamp) genVal.get("caThruDate");
this.characterSetId = (java.lang.String) genVal.get("characterSetId");
this.caLeftCoordinate = (java.lang.String) genVal.get("caLeftCoordinate");
this.localeString = (java.lang.String) genVal.get("localeString");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.contentTypeId = (java.lang.String) genVal.get("contentTypeId");
this.caFromDate = (java.sql.Timestamp) genVal.get("caFromDate");
this.caContentId = (java.lang.String) genVal.get("caContentId");
this.caMapKey = (java.lang.String) genVal.get("caMapKey");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.contentIdStart = (java.lang.String) genVal.get("contentIdStart");
this.caContentIdTo = (java.lang.String) genVal.get("caContentIdTo");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("childLeafCount",OrderMaxUtility.getValidEntityString(this.childLeafCount));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("ownerContentId",OrderMaxUtility.getValidEntityString(this.ownerContentId));
val.set("instanceOfContentId",OrderMaxUtility.getValidEntityString(this.instanceOfContentId));
val.set("caContentAssocPredicateId",OrderMaxUtility.getValidEntityString(this.caContentAssocPredicateId));
val.set("dataResourceId",OrderMaxUtility.getValidEntityString(this.dataResourceId));
val.set("caDataSourceId",OrderMaxUtility.getValidEntityString(this.caDataSourceId));
val.set("caLastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.caLastModifiedByUserLogin));
val.set("contentId",OrderMaxUtility.getValidEntityString(this.contentId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("caCreatedByUserLogin",OrderMaxUtility.getValidEntityString(this.caCreatedByUserLogin));
val.set("caLastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.caLastModifiedDate));
val.set("serviceName",OrderMaxUtility.getValidEntityString(this.serviceName));
val.set("childBranchCount",OrderMaxUtility.getValidEntityString(this.childBranchCount));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("caSequenceNum",this.caSequenceNum);
val.set("privilegeEnumId",OrderMaxUtility.getValidEntityString(this.privilegeEnumId));
val.set("caCreatedDate",OrderMaxUtility.getValidEntityTimestamp(this.caCreatedDate));
val.set("templateDataResourceId",OrderMaxUtility.getValidEntityString(this.templateDataResourceId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("decoratorContentId",OrderMaxUtility.getValidEntityString(this.decoratorContentId));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("caContentAssocTypeId",OrderMaxUtility.getValidEntityString(this.caContentAssocTypeId));
val.set("mimeTypeId",OrderMaxUtility.getValidEntityString(this.mimeTypeId));
val.set("caUpperCoordinate",OrderMaxUtility.getValidEntityString(this.caUpperCoordinate));
val.set("contentName",OrderMaxUtility.getValidEntityString(this.contentName));
val.set("caThruDate",OrderMaxUtility.getValidEntityTimestamp(this.caThruDate));
val.set("characterSetId",OrderMaxUtility.getValidEntityString(this.characterSetId));
val.set("caLeftCoordinate",OrderMaxUtility.getValidEntityString(this.caLeftCoordinate));
val.set("localeString",OrderMaxUtility.getValidEntityString(this.localeString));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("contentTypeId",OrderMaxUtility.getValidEntityString(this.contentTypeId));
val.set("caFromDate",OrderMaxUtility.getValidEntityTimestamp(this.caFromDate));
val.set("caContentId",OrderMaxUtility.getValidEntityString(this.caContentId));
val.set("caMapKey",OrderMaxUtility.getValidEntityString(this.caMapKey));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("contentIdStart",OrderMaxUtility.getValidEntityString(this.contentIdStart));
val.set("caContentIdTo",OrderMaxUtility.getValidEntityString(this.caContentIdTo));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("childLeafCount",this.childLeafCount);
valueMap.put("thruDate",this.thruDate);
valueMap.put("ownerContentId",this.ownerContentId);
valueMap.put("instanceOfContentId",this.instanceOfContentId);
valueMap.put("caContentAssocPredicateId",this.caContentAssocPredicateId);
valueMap.put("dataResourceId",this.dataResourceId);
valueMap.put("caDataSourceId",this.caDataSourceId);
valueMap.put("caLastModifiedByUserLogin",this.caLastModifiedByUserLogin);
valueMap.put("contentId",this.contentId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("caCreatedByUserLogin",this.caCreatedByUserLogin);
valueMap.put("caLastModifiedDate",this.caLastModifiedDate);
valueMap.put("serviceName",this.serviceName);
valueMap.put("childBranchCount",this.childBranchCount);
valueMap.put("statusId",this.statusId);
valueMap.put("caSequenceNum",this.caSequenceNum);
valueMap.put("privilegeEnumId",this.privilegeEnumId);
valueMap.put("caCreatedDate",this.caCreatedDate);
valueMap.put("templateDataResourceId",this.templateDataResourceId);
valueMap.put("description",this.description);
valueMap.put("decoratorContentId",this.decoratorContentId);
valueMap.put("createdDate",this.createdDate);
valueMap.put("caContentAssocTypeId",this.caContentAssocTypeId);
valueMap.put("mimeTypeId",this.mimeTypeId);
valueMap.put("caUpperCoordinate",this.caUpperCoordinate);
valueMap.put("contentName",this.contentName);
valueMap.put("caThruDate",this.caThruDate);
valueMap.put("characterSetId",this.characterSetId);
valueMap.put("caLeftCoordinate",this.caLeftCoordinate);
valueMap.put("localeString",this.localeString);
valueMap.put("fromDate",this.fromDate);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("contentTypeId",this.contentTypeId);
valueMap.put("caFromDate",this.caFromDate);
valueMap.put("caContentId",this.caContentId);
valueMap.put("caMapKey",this.caMapKey);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("contentIdStart",this.contentIdStart);
valueMap.put("caContentIdTo",this.caContentIdTo);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ContentAssocViewTo");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String childLeafCount;
public java.lang.String getchildLeafCount() {
return childLeafCount;
}
public void setchildLeafCount( java.lang.String childLeafCount ) {
this.childLeafCount = childLeafCount;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String ownerContentId;
public java.lang.String getownerContentId() {
return ownerContentId;
}
public void setownerContentId( java.lang.String ownerContentId ) {
this.ownerContentId = ownerContentId;
}
private java.lang.String instanceOfContentId;
public java.lang.String getinstanceOfContentId() {
return instanceOfContentId;
}
public void setinstanceOfContentId( java.lang.String instanceOfContentId ) {
this.instanceOfContentId = instanceOfContentId;
}
private java.lang.String caContentAssocPredicateId;
public java.lang.String getcaContentAssocPredicateId() {
return caContentAssocPredicateId;
}
public void setcaContentAssocPredicateId( java.lang.String caContentAssocPredicateId ) {
this.caContentAssocPredicateId = caContentAssocPredicateId;
}
private java.lang.String dataResourceId;
public java.lang.String getdataResourceId() {
return dataResourceId;
}
public void setdataResourceId( java.lang.String dataResourceId ) {
this.dataResourceId = dataResourceId;
}
private java.lang.String caDataSourceId;
public java.lang.String getcaDataSourceId() {
return caDataSourceId;
}
public void setcaDataSourceId( java.lang.String caDataSourceId ) {
this.caDataSourceId = caDataSourceId;
}
private java.lang.String caLastModifiedByUserLogin;
public java.lang.String getcaLastModifiedByUserLogin() {
return caLastModifiedByUserLogin;
}
public void setcaLastModifiedByUserLogin( java.lang.String caLastModifiedByUserLogin ) {
this.caLastModifiedByUserLogin = caLastModifiedByUserLogin;
}
private java.lang.String contentId;
public java.lang.String getcontentId() {
return contentId;
}
public void setcontentId( java.lang.String contentId ) {
this.contentId = contentId;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String caCreatedByUserLogin;
public java.lang.String getcaCreatedByUserLogin() {
return caCreatedByUserLogin;
}
public void setcaCreatedByUserLogin( java.lang.String caCreatedByUserLogin ) {
this.caCreatedByUserLogin = caCreatedByUserLogin;
}
private java.sql.Timestamp caLastModifiedDate;
public java.sql.Timestamp getcaLastModifiedDate() {
return caLastModifiedDate;
}
public void setcaLastModifiedDate( java.sql.Timestamp caLastModifiedDate ) {
this.caLastModifiedDate = caLastModifiedDate;
}
private java.lang.String serviceName;
public java.lang.String getserviceName() {
return serviceName;
}
public void setserviceName( java.lang.String serviceName ) {
this.serviceName = serviceName;
}
private java.lang.String childBranchCount;
public java.lang.String getchildBranchCount() {
return childBranchCount;
}
public void setchildBranchCount( java.lang.String childBranchCount ) {
this.childBranchCount = childBranchCount;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.Long caSequenceNum;
public java.lang.Long getcaSequenceNum() {
return caSequenceNum;
}
public void setcaSequenceNum( java.lang.Long caSequenceNum ) {
this.caSequenceNum = caSequenceNum;
}
private java.lang.String privilegeEnumId;
public java.lang.String getprivilegeEnumId() {
return privilegeEnumId;
}
public void setprivilegeEnumId( java.lang.String privilegeEnumId ) {
this.privilegeEnumId = privilegeEnumId;
}
private java.sql.Timestamp caCreatedDate;
public java.sql.Timestamp getcaCreatedDate() {
return caCreatedDate;
}
public void setcaCreatedDate( java.sql.Timestamp caCreatedDate ) {
this.caCreatedDate = caCreatedDate;
}
private java.lang.String templateDataResourceId;
public java.lang.String gettemplateDataResourceId() {
return templateDataResourceId;
}
public void settemplateDataResourceId( java.lang.String templateDataResourceId ) {
this.templateDataResourceId = templateDataResourceId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String decoratorContentId;
public java.lang.String getdecoratorContentId() {
return decoratorContentId;
}
public void setdecoratorContentId( java.lang.String decoratorContentId ) {
this.decoratorContentId = decoratorContentId;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String caContentAssocTypeId;
public java.lang.String getcaContentAssocTypeId() {
return caContentAssocTypeId;
}
public void setcaContentAssocTypeId( java.lang.String caContentAssocTypeId ) {
this.caContentAssocTypeId = caContentAssocTypeId;
}
private java.lang.String mimeTypeId;
public java.lang.String getmimeTypeId() {
return mimeTypeId;
}
public void setmimeTypeId( java.lang.String mimeTypeId ) {
this.mimeTypeId = mimeTypeId;
}
private java.lang.String caUpperCoordinate;
public java.lang.String getcaUpperCoordinate() {
return caUpperCoordinate;
}
public void setcaUpperCoordinate( java.lang.String caUpperCoordinate ) {
this.caUpperCoordinate = caUpperCoordinate;
}
private java.lang.String contentName;
public java.lang.String getcontentName() {
return contentName;
}
public void setcontentName( java.lang.String contentName ) {
this.contentName = contentName;
}
private java.sql.Timestamp caThruDate;
public java.sql.Timestamp getcaThruDate() {
return caThruDate;
}
public void setcaThruDate( java.sql.Timestamp caThruDate ) {
this.caThruDate = caThruDate;
}
private java.lang.String characterSetId;
public java.lang.String getcharacterSetId() {
return characterSetId;
}
public void setcharacterSetId( java.lang.String characterSetId ) {
this.characterSetId = characterSetId;
}
private java.lang.String caLeftCoordinate;
public java.lang.String getcaLeftCoordinate() {
return caLeftCoordinate;
}
public void setcaLeftCoordinate( java.lang.String caLeftCoordinate ) {
this.caLeftCoordinate = caLeftCoordinate;
}
private java.lang.String localeString;
public java.lang.String getlocaleString() {
return localeString;
}
public void setlocaleString( java.lang.String localeString ) {
this.localeString = localeString;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.lang.String contentTypeId;
public java.lang.String getcontentTypeId() {
return contentTypeId;
}
public void setcontentTypeId( java.lang.String contentTypeId ) {
this.contentTypeId = contentTypeId;
}
private java.sql.Timestamp caFromDate;
public java.sql.Timestamp getcaFromDate() {
return caFromDate;
}
public void setcaFromDate( java.sql.Timestamp caFromDate ) {
this.caFromDate = caFromDate;
}
private java.lang.String caContentId;
public java.lang.String getcaContentId() {
return caContentId;
}
public void setcaContentId( java.lang.String caContentId ) {
this.caContentId = caContentId;
}
private java.lang.String caMapKey;
public java.lang.String getcaMapKey() {
return caMapKey;
}
public void setcaMapKey( java.lang.String caMapKey ) {
this.caMapKey = caMapKey;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String contentIdStart;
public java.lang.String getcontentIdStart() {
return contentIdStart;
}
public void setcontentIdStart( java.lang.String contentIdStart ) {
this.contentIdStart = contentIdStart;
}
private java.lang.String caContentIdTo;
public java.lang.String getcaContentIdTo() {
return caContentIdTo;
}
public void setcaContentIdTo( java.lang.String caContentIdTo ) {
this.caContentIdTo = caContentIdTo;
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
        Collection<ContentAssocViewTo> objectList = new ArrayList<ContentAssocViewTo>();
        for (GenericValue genVal : genList) {
            objectList.add(new ContentAssocViewTo(genVal));
        }
        return objectList;
    }    
}
