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
public class SubContentDataResourceView implements GenericValueObjectInterface {
public static final String module =SubContentDataResourceView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public SubContentDataResourceView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public SubContentDataResourceView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"childLeafCount","Child Leaf Count"},
{"drDataResourceId","Dr Data Resource Id"},
{"drLastModifiedDate","Dr Last Modified Date"},
{"ownerContentId","Owner Content Id"},
{"instanceOfContentId","Instance Of Content Id"},
{"drDataSourceId","Dr Data Source Id"},
{"dataResourceId","Data Resource Id"},
{"contentId","Content Id"},
{"dataSourceId","Data Source Id"},
{"serviceName","Service Name"},
{"childBranchCount","Child Branch Count"},
{"statusId","Status Id"},
{"drStatusId","Dr Status Id"},
{"drCreatedDate","Dr Created Date"},
{"privilegeEnumId","Privilege Enum Id"},
{"templateDataResourceId","Template Data Resource Id"},
{"description","Description"},
{"drDataCategoryId","Dr Data Category Id"},
{"drLocaleString","Dr Locale String"},
{"decoratorContentId","Decorator Content Id"},
{"createdDate","Created Date"},
{"drLastModifiedByUserLogin","Dr Last Modified By User Login"},
{"drCharacterSetId","Dr Character Set Id"},
{"drSurveyResponseId","Dr Survey Response Id"},
{"drDataResourceTypeId","Dr Data Resource Type Id"},
{"mimeTypeId","Mime Type Id"},
{"drObjectInfo","Dr Object Info"},
{"contentName","Content Name"},
{"characterSetId","Character Set Id"},
{"drDataResourceName","Dr Data Resource Name"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"localeString","Locale String"},
{"contentTypeId","Content Type Id"},
{"drSurveyId","Dr Survey Id"},
{"drRelatedDetailId","Dr Related Detail Id"},
{"lastModifiedDate","Last Modified Date"},
{"drMimeTypeId","Dr Mime Type Id"},
{"drCreatedByUserLogin","Dr Created By User Login"},
{"drIsPublic","Dr Is Public"},
{"createdByUserLogin","Created By User Login"},
{"drDataTemplateTypeId","Dr Data Template Type Id"},
};
protected void initObject(){
this.childLeafCount = "";
this.drDataResourceId = "";
this.drLastModifiedDate = UtilDateTime.nowTimestamp();
this.ownerContentId = "";
this.instanceOfContentId = "";
this.drDataSourceId = "";
this.dataResourceId = "";
this.contentId = "";
this.dataSourceId = "";
this.serviceName = "";
this.childBranchCount = "";
this.statusId = "";
this.drStatusId = "";
this.drCreatedDate = UtilDateTime.nowTimestamp();
this.privilegeEnumId = "";
this.templateDataResourceId = "";
this.description = "";
this.drDataCategoryId = "";
this.drLocaleString = "";
this.decoratorContentId = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.drLastModifiedByUserLogin = "";
this.drCharacterSetId = "";
this.drSurveyResponseId = "";
this.drDataResourceTypeId = "";
this.mimeTypeId = "";
this.drObjectInfo = "";
this.contentName = "";
this.characterSetId = "";
this.drDataResourceName = "";
this.lastModifiedByUserLogin = "";
this.localeString = "";
this.contentTypeId = "";
this.drSurveyId = "";
this.drRelatedDetailId = "";
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.drMimeTypeId = "";
this.drCreatedByUserLogin = "";
this.drIsPublic = "";
this.createdByUserLogin = "";
this.drDataTemplateTypeId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.childLeafCount = (java.lang.String) genVal.get("childLeafCount");
this.drDataResourceId = (java.lang.String) genVal.get("drDataResourceId");
this.drLastModifiedDate = (java.sql.Timestamp) genVal.get("drLastModifiedDate");
this.ownerContentId = (java.lang.String) genVal.get("ownerContentId");
this.instanceOfContentId = (java.lang.String) genVal.get("instanceOfContentId");
this.drDataSourceId = (java.lang.String) genVal.get("drDataSourceId");
this.dataResourceId = (java.lang.String) genVal.get("dataResourceId");
this.contentId = (java.lang.String) genVal.get("contentId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.serviceName = (java.lang.String) genVal.get("serviceName");
this.childBranchCount = (java.lang.String) genVal.get("childBranchCount");
this.statusId = (java.lang.String) genVal.get("statusId");
this.drStatusId = (java.lang.String) genVal.get("drStatusId");
this.drCreatedDate = (java.sql.Timestamp) genVal.get("drCreatedDate");
this.privilegeEnumId = (java.lang.String) genVal.get("privilegeEnumId");
this.templateDataResourceId = (java.lang.String) genVal.get("templateDataResourceId");
this.description = (java.lang.String) genVal.get("description");
this.drDataCategoryId = (java.lang.String) genVal.get("drDataCategoryId");
this.drLocaleString = (java.lang.String) genVal.get("drLocaleString");
this.decoratorContentId = (java.lang.String) genVal.get("decoratorContentId");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.drLastModifiedByUserLogin = (java.lang.String) genVal.get("drLastModifiedByUserLogin");
this.drCharacterSetId = (java.lang.String) genVal.get("drCharacterSetId");
this.drSurveyResponseId = (java.lang.String) genVal.get("drSurveyResponseId");
this.drDataResourceTypeId = (java.lang.String) genVal.get("drDataResourceTypeId");
this.mimeTypeId = (java.lang.String) genVal.get("mimeTypeId");
this.drObjectInfo = (java.lang.String) genVal.get("drObjectInfo");
this.contentName = (java.lang.String) genVal.get("contentName");
this.characterSetId = (java.lang.String) genVal.get("characterSetId");
this.drDataResourceName = (java.lang.String) genVal.get("drDataResourceName");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.localeString = (java.lang.String) genVal.get("localeString");
this.contentTypeId = (java.lang.String) genVal.get("contentTypeId");
this.drSurveyId = (java.lang.String) genVal.get("drSurveyId");
this.drRelatedDetailId = (java.lang.String) genVal.get("drRelatedDetailId");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.drMimeTypeId = (java.lang.String) genVal.get("drMimeTypeId");
this.drCreatedByUserLogin = (java.lang.String) genVal.get("drCreatedByUserLogin");
this.drIsPublic = (java.lang.String) genVal.get("drIsPublic");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
this.drDataTemplateTypeId = (java.lang.String) genVal.get("drDataTemplateTypeId");
}
protected void getGenericValue(GenericValue val) {
val.set("childLeafCount",OrderMaxUtility.getValidEntityString(this.childLeafCount));
val.set("drDataResourceId",OrderMaxUtility.getValidEntityString(this.drDataResourceId));
val.set("drLastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.drLastModifiedDate));
val.set("ownerContentId",OrderMaxUtility.getValidEntityString(this.ownerContentId));
val.set("instanceOfContentId",OrderMaxUtility.getValidEntityString(this.instanceOfContentId));
val.set("drDataSourceId",OrderMaxUtility.getValidEntityString(this.drDataSourceId));
val.set("dataResourceId",OrderMaxUtility.getValidEntityString(this.dataResourceId));
val.set("contentId",OrderMaxUtility.getValidEntityString(this.contentId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("serviceName",OrderMaxUtility.getValidEntityString(this.serviceName));
val.set("childBranchCount",OrderMaxUtility.getValidEntityString(this.childBranchCount));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("drStatusId",OrderMaxUtility.getValidEntityString(this.drStatusId));
val.set("drCreatedDate",OrderMaxUtility.getValidEntityTimestamp(this.drCreatedDate));
val.set("privilegeEnumId",OrderMaxUtility.getValidEntityString(this.privilegeEnumId));
val.set("templateDataResourceId",OrderMaxUtility.getValidEntityString(this.templateDataResourceId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("drDataCategoryId",OrderMaxUtility.getValidEntityString(this.drDataCategoryId));
val.set("drLocaleString",OrderMaxUtility.getValidEntityString(this.drLocaleString));
val.set("decoratorContentId",OrderMaxUtility.getValidEntityString(this.decoratorContentId));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("drLastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.drLastModifiedByUserLogin));
val.set("drCharacterSetId",OrderMaxUtility.getValidEntityString(this.drCharacterSetId));
val.set("drSurveyResponseId",OrderMaxUtility.getValidEntityString(this.drSurveyResponseId));
val.set("drDataResourceTypeId",OrderMaxUtility.getValidEntityString(this.drDataResourceTypeId));
val.set("mimeTypeId",OrderMaxUtility.getValidEntityString(this.mimeTypeId));
val.set("drObjectInfo",OrderMaxUtility.getValidEntityString(this.drObjectInfo));
val.set("contentName",OrderMaxUtility.getValidEntityString(this.contentName));
val.set("characterSetId",OrderMaxUtility.getValidEntityString(this.characterSetId));
val.set("drDataResourceName",OrderMaxUtility.getValidEntityString(this.drDataResourceName));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("localeString",OrderMaxUtility.getValidEntityString(this.localeString));
val.set("contentTypeId",OrderMaxUtility.getValidEntityString(this.contentTypeId));
val.set("drSurveyId",OrderMaxUtility.getValidEntityString(this.drSurveyId));
val.set("drRelatedDetailId",OrderMaxUtility.getValidEntityString(this.drRelatedDetailId));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("drMimeTypeId",OrderMaxUtility.getValidEntityString(this.drMimeTypeId));
val.set("drCreatedByUserLogin",OrderMaxUtility.getValidEntityString(this.drCreatedByUserLogin));
val.set("drIsPublic",OrderMaxUtility.getValidEntityString(this.drIsPublic));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
val.set("drDataTemplateTypeId",OrderMaxUtility.getValidEntityString(this.drDataTemplateTypeId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("childLeafCount",this.childLeafCount);
valueMap.put("drDataResourceId",this.drDataResourceId);
valueMap.put("drLastModifiedDate",this.drLastModifiedDate);
valueMap.put("ownerContentId",this.ownerContentId);
valueMap.put("instanceOfContentId",this.instanceOfContentId);
valueMap.put("drDataSourceId",this.drDataSourceId);
valueMap.put("dataResourceId",this.dataResourceId);
valueMap.put("contentId",this.contentId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("serviceName",this.serviceName);
valueMap.put("childBranchCount",this.childBranchCount);
valueMap.put("statusId",this.statusId);
valueMap.put("drStatusId",this.drStatusId);
valueMap.put("drCreatedDate",this.drCreatedDate);
valueMap.put("privilegeEnumId",this.privilegeEnumId);
valueMap.put("templateDataResourceId",this.templateDataResourceId);
valueMap.put("description",this.description);
valueMap.put("drDataCategoryId",this.drDataCategoryId);
valueMap.put("drLocaleString",this.drLocaleString);
valueMap.put("decoratorContentId",this.decoratorContentId);
valueMap.put("createdDate",this.createdDate);
valueMap.put("drLastModifiedByUserLogin",this.drLastModifiedByUserLogin);
valueMap.put("drCharacterSetId",this.drCharacterSetId);
valueMap.put("drSurveyResponseId",this.drSurveyResponseId);
valueMap.put("drDataResourceTypeId",this.drDataResourceTypeId);
valueMap.put("mimeTypeId",this.mimeTypeId);
valueMap.put("drObjectInfo",this.drObjectInfo);
valueMap.put("contentName",this.contentName);
valueMap.put("characterSetId",this.characterSetId);
valueMap.put("drDataResourceName",this.drDataResourceName);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("localeString",this.localeString);
valueMap.put("contentTypeId",this.contentTypeId);
valueMap.put("drSurveyId",this.drSurveyId);
valueMap.put("drRelatedDetailId",this.drRelatedDetailId);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("drMimeTypeId",this.drMimeTypeId);
valueMap.put("drCreatedByUserLogin",this.drCreatedByUserLogin);
valueMap.put("drIsPublic",this.drIsPublic);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
valueMap.put("drDataTemplateTypeId",this.drDataTemplateTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("SubContentDataResourceView");
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
private java.lang.String drDataResourceId;
public java.lang.String getdrDataResourceId() {
return drDataResourceId;
}
public void setdrDataResourceId( java.lang.String drDataResourceId ) {
this.drDataResourceId = drDataResourceId;
}
private java.sql.Timestamp drLastModifiedDate;
public java.sql.Timestamp getdrLastModifiedDate() {
return drLastModifiedDate;
}
public void setdrLastModifiedDate( java.sql.Timestamp drLastModifiedDate ) {
this.drLastModifiedDate = drLastModifiedDate;
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
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String drDataCategoryId;
public java.lang.String getdrDataCategoryId() {
return drDataCategoryId;
}
public void setdrDataCategoryId( java.lang.String drDataCategoryId ) {
this.drDataCategoryId = drDataCategoryId;
}
private java.lang.String drLocaleString;
public java.lang.String getdrLocaleString() {
return drLocaleString;
}
public void setdrLocaleString( java.lang.String drLocaleString ) {
this.drLocaleString = drLocaleString;
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
private java.lang.String drSurveyResponseId;
public java.lang.String getdrSurveyResponseId() {
return drSurveyResponseId;
}
public void setdrSurveyResponseId( java.lang.String drSurveyResponseId ) {
this.drSurveyResponseId = drSurveyResponseId;
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
private java.lang.String characterSetId;
public java.lang.String getcharacterSetId() {
return characterSetId;
}
public void setcharacterSetId( java.lang.String characterSetId ) {
this.characterSetId = characterSetId;
}
private java.lang.String drDataResourceName;
public java.lang.String getdrDataResourceName() {
return drDataResourceName;
}
public void setdrDataResourceName( java.lang.String drDataResourceName ) {
this.drDataResourceName = drDataResourceName;
}
private java.lang.String lastModifiedByUserLogin;
public java.lang.String getlastModifiedByUserLogin() {
return lastModifiedByUserLogin;
}
public void setlastModifiedByUserLogin( java.lang.String lastModifiedByUserLogin ) {
this.lastModifiedByUserLogin = lastModifiedByUserLogin;
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
private java.lang.String drSurveyId;
public java.lang.String getdrSurveyId() {
return drSurveyId;
}
public void setdrSurveyId( java.lang.String drSurveyId ) {
this.drSurveyId = drSurveyId;
}
private java.lang.String drRelatedDetailId;
public java.lang.String getdrRelatedDetailId() {
return drRelatedDetailId;
}
public void setdrRelatedDetailId( java.lang.String drRelatedDetailId ) {
this.drRelatedDetailId = drRelatedDetailId;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
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
private java.lang.String drIsPublic;
public java.lang.String getdrIsPublic() {
return drIsPublic;
}
public void setdrIsPublic( java.lang.String drIsPublic ) {
this.drIsPublic = drIsPublic;
}
private java.lang.String createdByUserLogin;
public java.lang.String getcreatedByUserLogin() {
return createdByUserLogin;
}
public void setcreatedByUserLogin( java.lang.String createdByUserLogin ) {
this.createdByUserLogin = createdByUserLogin;
}
private java.lang.String drDataTemplateTypeId;
public java.lang.String getdrDataTemplateTypeId() {
return drDataTemplateTypeId;
}
public void setdrDataTemplateTypeId( java.lang.String drDataTemplateTypeId ) {
this.drDataTemplateTypeId = drDataTemplateTypeId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<SubContentDataResourceView> objectList = new ArrayList<SubContentDataResourceView>();
        for (GenericValue genVal : genList) {
            objectList.add(new SubContentDataResourceView(genVal));
        }
        return objectList;
    }    
}
