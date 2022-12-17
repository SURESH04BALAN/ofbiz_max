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
public class ProductContentAndInfo implements GenericValueObjectInterface {
public static final String module =ProductContentAndInfo.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductContentAndInfo(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductContentAndInfo () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"childLeafCount","Child Leaf Count"},
{"purchaseFromDate","Purchase From Date"},
{"drDataResourceId","Dr Data Resource Id"},
{"ownerContentId","Owner Content Id"},
{"drDataSourceId","Dr Data Source Id"},
{"dataResourceId","Data Resource Id"},
{"dataSourceId","Data Source Id"},
{"drStatusId","Dr Status Id"},
{"drCreatedDate","Dr Created Date"},
{"privilegeEnumId","Privilege Enum Id"},
{"templateDataResourceId","Template Data Resource Id"},
{"drDataCategoryId","Dr Data Category Id"},
{"useCountLimit","Use Count Limit"},
{"description","Description"},
{"drLocaleString","Dr Locale String"},
{"createdDate","Created Date"},
{"useTime","Use Time"},
{"drSurveyResponseId","Dr Survey Response Id"},
{"drObjectInfo","Dr Object Info"},
{"contentName","Content Name"},
{"useRoleTypeId","Use Role Type Id"},
{"characterSetId","Character Set Id"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"fromDate","From Date"},
{"drRelatedDetailId","Dr Related Detail Id"},
{"drSurveyId","Dr Survey Id"},
{"drMimeTypeId","Dr Mime Type Id"},
{"drCreatedByUserLogin","Dr Created By User Login"},
{"purchaseThruDate","Purchase Thru Date"},
{"drLastModifiedDate","Dr Last Modified Date"},
{"useTimeUomId","Use Time Uom Id"},
{"thruDate","Thru Date"},
{"instanceOfContentId","Instance Of Content Id"},
{"contentId","Content Id"},
{"serviceName","Service Name"},
{"childBranchCount","Child Branch Count"},
{"statusId","Status Id"},
{"decoratorContentId","Decorator Content Id"},
{"drLastModifiedByUserLogin","Dr Last Modified By User Login"},
{"drCharacterSetId","Dr Character Set Id"},
{"drDataResourceTypeId","Dr Data Resource Type Id"},
{"mimeTypeId","Mime Type Id"},
{"sequenceNum","Sequence Num"},
{"productId","Product Id"},
{"drDataResourceName","Dr Data Resource Name"},
{"localeString","Locale String"},
{"contentTypeId","Content Type Id"},
{"lastModifiedDate","Last Modified Date"},
{"productContentTypeId","Product Content Type Id"},
{"drIsPublic","Dr Is Public"},
{"drDataTemplateTypeId","Dr Data Template Type Id"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.childLeafCount = "";
this.purchaseFromDate = UtilDateTime.nowTimestamp();
this.drDataResourceId = "";
this.ownerContentId = "";
this.drDataSourceId = "";
this.dataResourceId = "";
this.dataSourceId = "";
this.drStatusId = "";
this.drCreatedDate = UtilDateTime.nowTimestamp();
this.privilegeEnumId = "";
this.templateDataResourceId = "";
this.drDataCategoryId = "";
this.useCountLimit = "";
this.description = "";
this.drLocaleString = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.useTime = "";
this.drSurveyResponseId = "";
this.drObjectInfo = "";
this.contentName = "";
this.useRoleTypeId = "";
this.characterSetId = "";
this.lastModifiedByUserLogin = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.drRelatedDetailId = "";
this.drSurveyId = "";
this.drMimeTypeId = "";
this.drCreatedByUserLogin = "";
this.purchaseThruDate = UtilDateTime.nowTimestamp();
this.drLastModifiedDate = UtilDateTime.nowTimestamp();
this.useTimeUomId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.instanceOfContentId = "";
this.contentId = "";
this.serviceName = "";
this.childBranchCount = "";
this.statusId = "";
this.decoratorContentId = "";
this.drLastModifiedByUserLogin = "";
this.drCharacterSetId = "";
this.drDataResourceTypeId = "";
this.mimeTypeId = "";
this.sequenceNum = "";
this.productId = "";
this.drDataResourceName = "";
this.localeString = "";
this.contentTypeId = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.productContentTypeId = "";
this.drIsPublic = "";
this.drDataTemplateTypeId = "";
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.childLeafCount = (java.lang.String) genVal.get("childLeafCount");
this.purchaseFromDate = (java.sql.Timestamp) genVal.get("purchaseFromDate");
this.drDataResourceId = (java.lang.String) genVal.get("drDataResourceId");
this.ownerContentId = (java.lang.String) genVal.get("ownerContentId");
this.drDataSourceId = (java.lang.String) genVal.get("drDataSourceId");
this.dataResourceId = (java.lang.String) genVal.get("dataResourceId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.drStatusId = (java.lang.String) genVal.get("drStatusId");
this.drCreatedDate = (java.sql.Timestamp) genVal.get("drCreatedDate");
this.privilegeEnumId = (java.lang.String) genVal.get("privilegeEnumId");
this.templateDataResourceId = (java.lang.String) genVal.get("templateDataResourceId");
this.drDataCategoryId = (java.lang.String) genVal.get("drDataCategoryId");
this.useCountLimit = (java.lang.String) genVal.get("useCountLimit");
this.description = (java.lang.String) genVal.get("description");
this.drLocaleString = (java.lang.String) genVal.get("drLocaleString");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.useTime = (java.lang.String) genVal.get("useTime");
this.drSurveyResponseId = (java.lang.String) genVal.get("drSurveyResponseId");
this.drObjectInfo = (java.lang.String) genVal.get("drObjectInfo");
this.contentName = (java.lang.String) genVal.get("contentName");
this.useRoleTypeId = (java.lang.String) genVal.get("useRoleTypeId");
this.characterSetId = (java.lang.String) genVal.get("characterSetId");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.drRelatedDetailId = (java.lang.String) genVal.get("drRelatedDetailId");
this.drSurveyId = (java.lang.String) genVal.get("drSurveyId");
this.drMimeTypeId = (java.lang.String) genVal.get("drMimeTypeId");
this.drCreatedByUserLogin = (java.lang.String) genVal.get("drCreatedByUserLogin");
this.purchaseThruDate = (java.sql.Timestamp) genVal.get("purchaseThruDate");
this.drLastModifiedDate = (java.sql.Timestamp) genVal.get("drLastModifiedDate");
this.useTimeUomId = (java.lang.String) genVal.get("useTimeUomId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.instanceOfContentId = (java.lang.String) genVal.get("instanceOfContentId");
this.contentId = (java.lang.String) genVal.get("contentId");
this.serviceName = (java.lang.String) genVal.get("serviceName");
this.childBranchCount = (java.lang.String) genVal.get("childBranchCount");
this.statusId = (java.lang.String) genVal.get("statusId");
this.decoratorContentId = (java.lang.String) genVal.get("decoratorContentId");
this.drLastModifiedByUserLogin = (java.lang.String) genVal.get("drLastModifiedByUserLogin");
this.drCharacterSetId = (java.lang.String) genVal.get("drCharacterSetId");
this.drDataResourceTypeId = (java.lang.String) genVal.get("drDataResourceTypeId");
this.mimeTypeId = (java.lang.String) genVal.get("mimeTypeId");
this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
this.productId = (java.lang.String) genVal.get("productId");
this.drDataResourceName = (java.lang.String) genVal.get("drDataResourceName");
this.localeString = (java.lang.String) genVal.get("localeString");
this.contentTypeId = (java.lang.String) genVal.get("contentTypeId");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.productContentTypeId = (java.lang.String) genVal.get("productContentTypeId");
this.drIsPublic = (java.lang.String) genVal.get("drIsPublic");
this.drDataTemplateTypeId = (java.lang.String) genVal.get("drDataTemplateTypeId");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("childLeafCount",OrderMaxUtility.getValidEntityString(this.childLeafCount));
val.set("purchaseFromDate",OrderMaxUtility.getValidEntityTimestamp(this.purchaseFromDate));
val.set("drDataResourceId",OrderMaxUtility.getValidEntityString(this.drDataResourceId));
val.set("ownerContentId",OrderMaxUtility.getValidEntityString(this.ownerContentId));
val.set("drDataSourceId",OrderMaxUtility.getValidEntityString(this.drDataSourceId));
val.set("dataResourceId",OrderMaxUtility.getValidEntityString(this.dataResourceId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("drStatusId",OrderMaxUtility.getValidEntityString(this.drStatusId));
val.set("drCreatedDate",OrderMaxUtility.getValidEntityTimestamp(this.drCreatedDate));
val.set("privilegeEnumId",OrderMaxUtility.getValidEntityString(this.privilegeEnumId));
val.set("templateDataResourceId",OrderMaxUtility.getValidEntityString(this.templateDataResourceId));
val.set("drDataCategoryId",OrderMaxUtility.getValidEntityString(this.drDataCategoryId));
val.set("useCountLimit",OrderMaxUtility.getValidEntityString(this.useCountLimit));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("drLocaleString",OrderMaxUtility.getValidEntityString(this.drLocaleString));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("useTime",OrderMaxUtility.getValidEntityString(this.useTime));
val.set("drSurveyResponseId",OrderMaxUtility.getValidEntityString(this.drSurveyResponseId));
val.set("drObjectInfo",OrderMaxUtility.getValidEntityString(this.drObjectInfo));
val.set("contentName",OrderMaxUtility.getValidEntityString(this.contentName));
val.set("useRoleTypeId",OrderMaxUtility.getValidEntityString(this.useRoleTypeId));
val.set("characterSetId",OrderMaxUtility.getValidEntityString(this.characterSetId));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("drRelatedDetailId",OrderMaxUtility.getValidEntityString(this.drRelatedDetailId));
val.set("drSurveyId",OrderMaxUtility.getValidEntityString(this.drSurveyId));
val.set("drMimeTypeId",OrderMaxUtility.getValidEntityString(this.drMimeTypeId));
val.set("drCreatedByUserLogin",OrderMaxUtility.getValidEntityString(this.drCreatedByUserLogin));
val.set("purchaseThruDate",OrderMaxUtility.getValidEntityTimestamp(this.purchaseThruDate));
val.set("drLastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.drLastModifiedDate));
val.set("useTimeUomId",OrderMaxUtility.getValidEntityString(this.useTimeUomId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("instanceOfContentId",OrderMaxUtility.getValidEntityString(this.instanceOfContentId));
val.set("contentId",OrderMaxUtility.getValidEntityString(this.contentId));
val.set("serviceName",OrderMaxUtility.getValidEntityString(this.serviceName));
val.set("childBranchCount",OrderMaxUtility.getValidEntityString(this.childBranchCount));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("decoratorContentId",OrderMaxUtility.getValidEntityString(this.decoratorContentId));
val.set("drLastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.drLastModifiedByUserLogin));
val.set("drCharacterSetId",OrderMaxUtility.getValidEntityString(this.drCharacterSetId));
val.set("drDataResourceTypeId",OrderMaxUtility.getValidEntityString(this.drDataResourceTypeId));
val.set("mimeTypeId",OrderMaxUtility.getValidEntityString(this.mimeTypeId));
val.set("sequenceNum",OrderMaxUtility.getValidEntityString(this.sequenceNum));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("drDataResourceName",OrderMaxUtility.getValidEntityString(this.drDataResourceName));
val.set("localeString",OrderMaxUtility.getValidEntityString(this.localeString));
val.set("contentTypeId",OrderMaxUtility.getValidEntityString(this.contentTypeId));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("productContentTypeId",OrderMaxUtility.getValidEntityString(this.productContentTypeId));
val.set("drIsPublic",OrderMaxUtility.getValidEntityString(this.drIsPublic));
val.set("drDataTemplateTypeId",OrderMaxUtility.getValidEntityString(this.drDataTemplateTypeId));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("childLeafCount",this.childLeafCount);
valueMap.put("purchaseFromDate",this.purchaseFromDate);
valueMap.put("drDataResourceId",this.drDataResourceId);
valueMap.put("ownerContentId",this.ownerContentId);
valueMap.put("drDataSourceId",this.drDataSourceId);
valueMap.put("dataResourceId",this.dataResourceId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("drStatusId",this.drStatusId);
valueMap.put("drCreatedDate",this.drCreatedDate);
valueMap.put("privilegeEnumId",this.privilegeEnumId);
valueMap.put("templateDataResourceId",this.templateDataResourceId);
valueMap.put("drDataCategoryId",this.drDataCategoryId);
valueMap.put("useCountLimit",this.useCountLimit);
valueMap.put("description",this.description);
valueMap.put("drLocaleString",this.drLocaleString);
valueMap.put("createdDate",this.createdDate);
valueMap.put("useTime",this.useTime);
valueMap.put("drSurveyResponseId",this.drSurveyResponseId);
valueMap.put("drObjectInfo",this.drObjectInfo);
valueMap.put("contentName",this.contentName);
valueMap.put("useRoleTypeId",this.useRoleTypeId);
valueMap.put("characterSetId",this.characterSetId);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("fromDate",this.fromDate);
valueMap.put("drRelatedDetailId",this.drRelatedDetailId);
valueMap.put("drSurveyId",this.drSurveyId);
valueMap.put("drMimeTypeId",this.drMimeTypeId);
valueMap.put("drCreatedByUserLogin",this.drCreatedByUserLogin);
valueMap.put("purchaseThruDate",this.purchaseThruDate);
valueMap.put("drLastModifiedDate",this.drLastModifiedDate);
valueMap.put("useTimeUomId",this.useTimeUomId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("instanceOfContentId",this.instanceOfContentId);
valueMap.put("contentId",this.contentId);
valueMap.put("serviceName",this.serviceName);
valueMap.put("childBranchCount",this.childBranchCount);
valueMap.put("statusId",this.statusId);
valueMap.put("decoratorContentId",this.decoratorContentId);
valueMap.put("drLastModifiedByUserLogin",this.drLastModifiedByUserLogin);
valueMap.put("drCharacterSetId",this.drCharacterSetId);
valueMap.put("drDataResourceTypeId",this.drDataResourceTypeId);
valueMap.put("mimeTypeId",this.mimeTypeId);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("productId",this.productId);
valueMap.put("drDataResourceName",this.drDataResourceName);
valueMap.put("localeString",this.localeString);
valueMap.put("contentTypeId",this.contentTypeId);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("productContentTypeId",this.productContentTypeId);
valueMap.put("drIsPublic",this.drIsPublic);
valueMap.put("drDataTemplateTypeId",this.drDataTemplateTypeId);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductContentAndInfo");
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
private java.sql.Timestamp purchaseFromDate;
public java.sql.Timestamp getpurchaseFromDate() {
return purchaseFromDate;
}
public void setpurchaseFromDate( java.sql.Timestamp purchaseFromDate ) {
this.purchaseFromDate = purchaseFromDate;
}
private java.lang.String drDataResourceId;
public java.lang.String getdrDataResourceId() {
return drDataResourceId;
}
public void setdrDataResourceId( java.lang.String drDataResourceId ) {
this.drDataResourceId = drDataResourceId;
}
private java.lang.String ownerContentId;
public java.lang.String getownerContentId() {
return ownerContentId;
}
public void setownerContentId( java.lang.String ownerContentId ) {
this.ownerContentId = ownerContentId;
}
private java.lang.String drDataSourceId;
public java.lang.String getdrDataSourceId() {
return drDataSourceId;
}
public void setdrDataSourceId( java.lang.String drDataSourceId ) {
this.drDataSourceId = drDataSourceId;
}
private java.lang.String dataResourceId;
public java.lang.String getdataResourceId() {
return dataResourceId;
}
public void setdataResourceId( java.lang.String dataResourceId ) {
this.dataResourceId = dataResourceId;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String drStatusId;
public java.lang.String getdrStatusId() {
return drStatusId;
}
public void setdrStatusId( java.lang.String drStatusId ) {
this.drStatusId = drStatusId;
}
private java.sql.Timestamp drCreatedDate;
public java.sql.Timestamp getdrCreatedDate() {
return drCreatedDate;
}
public void setdrCreatedDate( java.sql.Timestamp drCreatedDate ) {
this.drCreatedDate = drCreatedDate;
}
private java.lang.String privilegeEnumId;
public java.lang.String getprivilegeEnumId() {
return privilegeEnumId;
}
public void setprivilegeEnumId( java.lang.String privilegeEnumId ) {
this.privilegeEnumId = privilegeEnumId;
}
private java.lang.String templateDataResourceId;
public java.lang.String gettemplateDataResourceId() {
return templateDataResourceId;
}
public void settemplateDataResourceId( java.lang.String templateDataResourceId ) {
this.templateDataResourceId = templateDataResourceId;
}
private java.lang.String drDataCategoryId;
public java.lang.String getdrDataCategoryId() {
return drDataCategoryId;
}
public void setdrDataCategoryId( java.lang.String drDataCategoryId ) {
this.drDataCategoryId = drDataCategoryId;
}
private java.lang.String useCountLimit;
public java.lang.String getuseCountLimit() {
return useCountLimit;
}
public void setuseCountLimit( java.lang.String useCountLimit ) {
this.useCountLimit = useCountLimit;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String drLocaleString;
public java.lang.String getdrLocaleString() {
return drLocaleString;
}
public void setdrLocaleString( java.lang.String drLocaleString ) {
this.drLocaleString = drLocaleString;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String useTime;
public java.lang.String getuseTime() {
return useTime;
}
public void setuseTime( java.lang.String useTime ) {
this.useTime = useTime;
}
private java.lang.String drSurveyResponseId;
public java.lang.String getdrSurveyResponseId() {
return drSurveyResponseId;
}
public void setdrSurveyResponseId( java.lang.String drSurveyResponseId ) {
this.drSurveyResponseId = drSurveyResponseId;
}
private java.lang.String drObjectInfo;
public java.lang.String getdrObjectInfo() {
return drObjectInfo;
}
public void setdrObjectInfo( java.lang.String drObjectInfo ) {
this.drObjectInfo = drObjectInfo;
}
private java.lang.String contentName;
public java.lang.String getcontentName() {
return contentName;
}
public void setcontentName( java.lang.String contentName ) {
this.contentName = contentName;
}
private java.lang.String useRoleTypeId;
public java.lang.String getuseRoleTypeId() {
return useRoleTypeId;
}
public void setuseRoleTypeId( java.lang.String useRoleTypeId ) {
this.useRoleTypeId = useRoleTypeId;
}
private java.lang.String characterSetId;
public java.lang.String getcharacterSetId() {
return characterSetId;
}
public void setcharacterSetId( java.lang.String characterSetId ) {
this.characterSetId = characterSetId;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String drRelatedDetailId;
public java.lang.String getdrRelatedDetailId() {
return drRelatedDetailId;
}
public void setdrRelatedDetailId( java.lang.String drRelatedDetailId ) {
this.drRelatedDetailId = drRelatedDetailId;
}
private java.lang.String drSurveyId;
public java.lang.String getdrSurveyId() {
return drSurveyId;
}
public void setdrSurveyId( java.lang.String drSurveyId ) {
this.drSurveyId = drSurveyId;
}
private java.lang.String drMimeTypeId;
public java.lang.String getdrMimeTypeId() {
return drMimeTypeId;
}
public void setdrMimeTypeId( java.lang.String drMimeTypeId ) {
this.drMimeTypeId = drMimeTypeId;
}
private java.lang.String drCreatedByUserLogin;
public java.lang.String getdrCreatedByUserLogin() {
return drCreatedByUserLogin;
}
public void setdrCreatedByUserLogin( java.lang.String drCreatedByUserLogin ) {
this.drCreatedByUserLogin = drCreatedByUserLogin;
}
private java.sql.Timestamp purchaseThruDate;
public java.sql.Timestamp getpurchaseThruDate() {
return purchaseThruDate;
}
public void setpurchaseThruDate( java.sql.Timestamp purchaseThruDate ) {
this.purchaseThruDate = purchaseThruDate;
}
private java.sql.Timestamp drLastModifiedDate;
public java.sql.Timestamp getdrLastModifiedDate() {
return drLastModifiedDate;
}
public void setdrLastModifiedDate( java.sql.Timestamp drLastModifiedDate ) {
this.drLastModifiedDate = drLastModifiedDate;
}
private java.lang.String useTimeUomId;
public java.lang.String getuseTimeUomId() {
return useTimeUomId;
}
public void setuseTimeUomId( java.lang.String useTimeUomId ) {
this.useTimeUomId = useTimeUomId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String instanceOfContentId;
public java.lang.String getinstanceOfContentId() {
return instanceOfContentId;
}
public void setinstanceOfContentId( java.lang.String instanceOfContentId ) {
this.instanceOfContentId = instanceOfContentId;
}
private java.lang.String contentId;
public java.lang.String getcontentId() {
return contentId;
}
public void setcontentId( java.lang.String contentId ) {
this.contentId = contentId;
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
private java.lang.String decoratorContentId;
public java.lang.String getdecoratorContentId() {
return decoratorContentId;
}
public void setdecoratorContentId( java.lang.String decoratorContentId ) {
this.decoratorContentId = decoratorContentId;
}
private java.lang.String drLastModifiedByUserLogin;
public java.lang.String getdrLastModifiedByUserLogin() {
return drLastModifiedByUserLogin;
}
public void setdrLastModifiedByUserLogin( java.lang.String drLastModifiedByUserLogin ) {
this.drLastModifiedByUserLogin = drLastModifiedByUserLogin;
}
private java.lang.String drCharacterSetId;
public java.lang.String getdrCharacterSetId() {
return drCharacterSetId;
}
public void setdrCharacterSetId( java.lang.String drCharacterSetId ) {
this.drCharacterSetId = drCharacterSetId;
}
private java.lang.String drDataResourceTypeId;
public java.lang.String getdrDataResourceTypeId() {
return drDataResourceTypeId;
}
public void setdrDataResourceTypeId( java.lang.String drDataResourceTypeId ) {
this.drDataResourceTypeId = drDataResourceTypeId;
}
private java.lang.String mimeTypeId;
public java.lang.String getmimeTypeId() {
return mimeTypeId;
}
public void setmimeTypeId( java.lang.String mimeTypeId ) {
this.mimeTypeId = mimeTypeId;
}
private java.lang.String sequenceNum;
public java.lang.String getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.String sequenceNum ) {
this.sequenceNum = sequenceNum;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String drDataResourceName;
public java.lang.String getdrDataResourceName() {
return drDataResourceName;
}
public void setdrDataResourceName( java.lang.String drDataResourceName ) {
this.drDataResourceName = drDataResourceName;
}
private java.lang.String localeString;
public java.lang.String getlocaleString() {
return localeString;
}
public void setlocaleString( java.lang.String localeString ) {
this.localeString = localeString;
}
private java.lang.String contentTypeId;
public java.lang.String getcontentTypeId() {
return contentTypeId;
}
public void setcontentTypeId( java.lang.String contentTypeId ) {
this.contentTypeId = contentTypeId;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String productContentTypeId;
public java.lang.String getproductContentTypeId() {
return productContentTypeId;
}
public void setproductContentTypeId( java.lang.String productContentTypeId ) {
this.productContentTypeId = productContentTypeId;
}
private java.lang.String drIsPublic;
public java.lang.String getdrIsPublic() {
return drIsPublic;
}
public void setdrIsPublic( java.lang.String drIsPublic ) {
this.drIsPublic = drIsPublic;
}
private java.lang.String drDataTemplateTypeId;
public java.lang.String getdrDataTemplateTypeId() {
return drDataTemplateTypeId;
}
public void setdrDataTemplateTypeId( java.lang.String drDataTemplateTypeId ) {
this.drDataTemplateTypeId = drDataTemplateTypeId;
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
        Collection<ProductContentAndInfo> objectList = new ArrayList<ProductContentAndInfo>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductContentAndInfo(genVal));
        }
        return objectList;
    }    
}
