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
public class DataResourceContentView implements GenericValueObjectInterface {
public static final String module =DataResourceContentView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public DataResourceContentView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public DataResourceContentView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"coContentTypeId","Co Content Type Id"},
{"coTemplateDataResourceId","Co Template Data Resource Id"},
{"coPrivilegeEnumId","Co Privilege Enum Id"},
{"coLastModifiedByUserLogin","Co Last Modified By User Login"},
{"coCharacterSetId","Co Character Set Id"},
{"dataResourceId","Data Resource Id"},
{"coDataSourceId","Co Data Source Id"},
{"dataSourceId","Data Source Id"},
{"coDescription","Co Description"},
{"coCreatedByUserLogin","Co Created By User Login"},
{"statusId","Status Id"},
{"coContentId","Co Content Id"},
{"coDecoratorContentId","Co Decorator Content Id"},
{"dataResourceTypeId","Data Resource Type Id"},
{"coChildLeafCount","Co Child Leaf Count"},
{"createdDate","Created Date"},
{"coStatusId","Co Status Id"},
{"coMimeTypeId","Co Mime Type Id"},
{"coDataResourceId","Co Data Resource Id"},
{"dataResourceName","Data Resource Name"},
{"dataTemplateTypeId","Data Template Type Id"},
{"mimeTypeId","Mime Type Id"},
{"coInstanceOfContentId","Co Instance Of Content Id"},
{"isPublic","Is Public"},
{"relatedDetailId","Related Detail Id"},
{"coContentName","Co Content Name"},
{"dataCategoryId","Data Category Id"},
{"coChildBranchCount","Co Child Branch Count"},
{"characterSetId","Character Set Id"},
{"coOwnerContentId","Co Owner Content Id"},
{"objectInfo","Object Info"},
{"coServiceName","Co Service Name"},
{"lastModifiedByUserLogin","Last Modified By User Login"},
{"localeString","Locale String"},
{"coLocaleString","Co Locale String"},
{"surveyResponseId","Survey Response Id"},
{"coLastModifiedDate","Co Last Modified Date"},
{"lastModifiedDate","Last Modified Date"},
{"surveyId","Survey Id"},
{"coCreatedDate","Co Created Date"},
{"createdByUserLogin","Created By User Login"},
};
protected void initObject(){
this.coContentTypeId = "";
this.coTemplateDataResourceId = "";
this.coPrivilegeEnumId = "";
this.coLastModifiedByUserLogin = "";
this.coCharacterSetId = "";
this.dataResourceId = "";
this.coDataSourceId = "";
this.dataSourceId = "";
this.coDescription = "";
this.coCreatedByUserLogin = "";
this.statusId = "";
this.coContentId = "";
this.coDecoratorContentId = "";
this.dataResourceTypeId = "";
this.coChildLeafCount = "";
this.createdDate = UtilDateTime.nowTimestamp();
this.coStatusId = "";
this.coMimeTypeId = "";
this.coDataResourceId = "";
this.dataResourceName = "";
this.dataTemplateTypeId = "";
this.mimeTypeId = "";
this.coInstanceOfContentId = "";
this.isPublic = "";
this.relatedDetailId = "";
this.coContentName = "";
this.dataCategoryId = "";
this.coChildBranchCount = "";
this.characterSetId = "";
this.coOwnerContentId = "";
this.objectInfo = "";
this.coServiceName = "";
this.lastModifiedByUserLogin = "";
this.localeString = "";
this.coLocaleString = "";
this.surveyResponseId = "";
this.coLastModifiedDate = UtilDateTime.nowTimestamp();
this.lastModifiedDate = UtilDateTime.nowTimestamp();
this.surveyId = "";
this.coCreatedDate = UtilDateTime.nowTimestamp();
this.createdByUserLogin = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.coContentTypeId = (java.lang.String) genVal.get("coContentTypeId");
this.coTemplateDataResourceId = (java.lang.String) genVal.get("coTemplateDataResourceId");
this.coPrivilegeEnumId = (java.lang.String) genVal.get("coPrivilegeEnumId");
this.coLastModifiedByUserLogin = (java.lang.String) genVal.get("coLastModifiedByUserLogin");
this.coCharacterSetId = (java.lang.String) genVal.get("coCharacterSetId");
this.dataResourceId = (java.lang.String) genVal.get("dataResourceId");
this.coDataSourceId = (java.lang.String) genVal.get("coDataSourceId");
this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
this.coDescription = (java.lang.String) genVal.get("coDescription");
this.coCreatedByUserLogin = (java.lang.String) genVal.get("coCreatedByUserLogin");
this.statusId = (java.lang.String) genVal.get("statusId");
this.coContentId = (java.lang.String) genVal.get("coContentId");
this.coDecoratorContentId = (java.lang.String) genVal.get("coDecoratorContentId");
this.dataResourceTypeId = (java.lang.String) genVal.get("dataResourceTypeId");
this.coChildLeafCount = (java.lang.String) genVal.get("coChildLeafCount");
this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
this.coStatusId = (java.lang.String) genVal.get("coStatusId");
this.coMimeTypeId = (java.lang.String) genVal.get("coMimeTypeId");
this.coDataResourceId = (java.lang.String) genVal.get("coDataResourceId");
this.dataResourceName = (java.lang.String) genVal.get("dataResourceName");
this.dataTemplateTypeId = (java.lang.String) genVal.get("dataTemplateTypeId");
this.mimeTypeId = (java.lang.String) genVal.get("mimeTypeId");
this.coInstanceOfContentId = (java.lang.String) genVal.get("coInstanceOfContentId");
this.isPublic = (java.lang.String) genVal.get("isPublic");
this.relatedDetailId = (java.lang.String) genVal.get("relatedDetailId");
this.coContentName = (java.lang.String) genVal.get("coContentName");
this.dataCategoryId = (java.lang.String) genVal.get("dataCategoryId");
this.coChildBranchCount = (java.lang.String) genVal.get("coChildBranchCount");
this.characterSetId = (java.lang.String) genVal.get("characterSetId");
this.coOwnerContentId = (java.lang.String) genVal.get("coOwnerContentId");
this.objectInfo = (java.lang.String) genVal.get("objectInfo");
this.coServiceName = (java.lang.String) genVal.get("coServiceName");
this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
this.localeString = (java.lang.String) genVal.get("localeString");
this.coLocaleString = (java.lang.String) genVal.get("coLocaleString");
this.surveyResponseId = (java.lang.String) genVal.get("surveyResponseId");
this.coLastModifiedDate = (java.sql.Timestamp) genVal.get("coLastModifiedDate");
this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
this.surveyId = (java.lang.String) genVal.get("surveyId");
this.coCreatedDate = (java.sql.Timestamp) genVal.get("coCreatedDate");
this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
}
protected void getGenericValue(GenericValue val) {
val.set("coContentTypeId",OrderMaxUtility.getValidEntityString(this.coContentTypeId));
val.set("coTemplateDataResourceId",OrderMaxUtility.getValidEntityString(this.coTemplateDataResourceId));
val.set("coPrivilegeEnumId",OrderMaxUtility.getValidEntityString(this.coPrivilegeEnumId));
val.set("coLastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.coLastModifiedByUserLogin));
val.set("coCharacterSetId",OrderMaxUtility.getValidEntityString(this.coCharacterSetId));
val.set("dataResourceId",OrderMaxUtility.getValidEntityString(this.dataResourceId));
val.set("coDataSourceId",OrderMaxUtility.getValidEntityString(this.coDataSourceId));
val.set("dataSourceId",OrderMaxUtility.getValidEntityString(this.dataSourceId));
val.set("coDescription",OrderMaxUtility.getValidEntityString(this.coDescription));
val.set("coCreatedByUserLogin",OrderMaxUtility.getValidEntityString(this.coCreatedByUserLogin));
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("coContentId",OrderMaxUtility.getValidEntityString(this.coContentId));
val.set("coDecoratorContentId",OrderMaxUtility.getValidEntityString(this.coDecoratorContentId));
val.set("dataResourceTypeId",OrderMaxUtility.getValidEntityString(this.dataResourceTypeId));
val.set("coChildLeafCount",OrderMaxUtility.getValidEntityString(this.coChildLeafCount));
val.set("createdDate",OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
val.set("coStatusId",OrderMaxUtility.getValidEntityString(this.coStatusId));
val.set("coMimeTypeId",OrderMaxUtility.getValidEntityString(this.coMimeTypeId));
val.set("coDataResourceId",OrderMaxUtility.getValidEntityString(this.coDataResourceId));
val.set("dataResourceName",OrderMaxUtility.getValidEntityString(this.dataResourceName));
val.set("dataTemplateTypeId",OrderMaxUtility.getValidEntityString(this.dataTemplateTypeId));
val.set("mimeTypeId",OrderMaxUtility.getValidEntityString(this.mimeTypeId));
val.set("coInstanceOfContentId",OrderMaxUtility.getValidEntityString(this.coInstanceOfContentId));
val.set("isPublic",OrderMaxUtility.getValidEntityString(this.isPublic));
val.set("relatedDetailId",OrderMaxUtility.getValidEntityString(this.relatedDetailId));
val.set("coContentName",OrderMaxUtility.getValidEntityString(this.coContentName));
val.set("dataCategoryId",OrderMaxUtility.getValidEntityString(this.dataCategoryId));
val.set("coChildBranchCount",OrderMaxUtility.getValidEntityString(this.coChildBranchCount));
val.set("characterSetId",OrderMaxUtility.getValidEntityString(this.characterSetId));
val.set("coOwnerContentId",OrderMaxUtility.getValidEntityString(this.coOwnerContentId));
val.set("objectInfo",OrderMaxUtility.getValidEntityString(this.objectInfo));
val.set("coServiceName",OrderMaxUtility.getValidEntityString(this.coServiceName));
val.set("lastModifiedByUserLogin",OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
val.set("localeString",OrderMaxUtility.getValidEntityString(this.localeString));
val.set("coLocaleString",OrderMaxUtility.getValidEntityString(this.coLocaleString));
val.set("surveyResponseId",OrderMaxUtility.getValidEntityString(this.surveyResponseId));
val.set("coLastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.coLastModifiedDate));
val.set("lastModifiedDate",OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
val.set("surveyId",OrderMaxUtility.getValidEntityString(this.surveyId));
val.set("coCreatedDate",OrderMaxUtility.getValidEntityTimestamp(this.coCreatedDate));
val.set("createdByUserLogin",OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("coContentTypeId",this.coContentTypeId);
valueMap.put("coTemplateDataResourceId",this.coTemplateDataResourceId);
valueMap.put("coPrivilegeEnumId",this.coPrivilegeEnumId);
valueMap.put("coLastModifiedByUserLogin",this.coLastModifiedByUserLogin);
valueMap.put("coCharacterSetId",this.coCharacterSetId);
valueMap.put("dataResourceId",this.dataResourceId);
valueMap.put("coDataSourceId",this.coDataSourceId);
valueMap.put("dataSourceId",this.dataSourceId);
valueMap.put("coDescription",this.coDescription);
valueMap.put("coCreatedByUserLogin",this.coCreatedByUserLogin);
valueMap.put("statusId",this.statusId);
valueMap.put("coContentId",this.coContentId);
valueMap.put("coDecoratorContentId",this.coDecoratorContentId);
valueMap.put("dataResourceTypeId",this.dataResourceTypeId);
valueMap.put("coChildLeafCount",this.coChildLeafCount);
valueMap.put("createdDate",this.createdDate);
valueMap.put("coStatusId",this.coStatusId);
valueMap.put("coMimeTypeId",this.coMimeTypeId);
valueMap.put("coDataResourceId",this.coDataResourceId);
valueMap.put("dataResourceName",this.dataResourceName);
valueMap.put("dataTemplateTypeId",this.dataTemplateTypeId);
valueMap.put("mimeTypeId",this.mimeTypeId);
valueMap.put("coInstanceOfContentId",this.coInstanceOfContentId);
valueMap.put("isPublic",this.isPublic);
valueMap.put("relatedDetailId",this.relatedDetailId);
valueMap.put("coContentName",this.coContentName);
valueMap.put("dataCategoryId",this.dataCategoryId);
valueMap.put("coChildBranchCount",this.coChildBranchCount);
valueMap.put("characterSetId",this.characterSetId);
valueMap.put("coOwnerContentId",this.coOwnerContentId);
valueMap.put("objectInfo",this.objectInfo);
valueMap.put("coServiceName",this.coServiceName);
valueMap.put("lastModifiedByUserLogin",this.lastModifiedByUserLogin);
valueMap.put("localeString",this.localeString);
valueMap.put("coLocaleString",this.coLocaleString);
valueMap.put("surveyResponseId",this.surveyResponseId);
valueMap.put("coLastModifiedDate",this.coLastModifiedDate);
valueMap.put("lastModifiedDate",this.lastModifiedDate);
valueMap.put("surveyId",this.surveyId);
valueMap.put("coCreatedDate",this.coCreatedDate);
valueMap.put("createdByUserLogin",this.createdByUserLogin);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("DataResourceContentView");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String coContentTypeId;
public java.lang.String getcoContentTypeId() {
return coContentTypeId;
}
public void setcoContentTypeId( java.lang.String coContentTypeId ) {
this.coContentTypeId = coContentTypeId;
}
private java.lang.String coTemplateDataResourceId;
public java.lang.String getcoTemplateDataResourceId() {
return coTemplateDataResourceId;
}
public void setcoTemplateDataResourceId( java.lang.String coTemplateDataResourceId ) {
this.coTemplateDataResourceId = coTemplateDataResourceId;
}
private java.lang.String coPrivilegeEnumId;
public java.lang.String getcoPrivilegeEnumId() {
return coPrivilegeEnumId;
}
public void setcoPrivilegeEnumId( java.lang.String coPrivilegeEnumId ) {
this.coPrivilegeEnumId = coPrivilegeEnumId;
}
private java.lang.String coLastModifiedByUserLogin;
public java.lang.String getcoLastModifiedByUserLogin() {
return coLastModifiedByUserLogin;
}
public void setcoLastModifiedByUserLogin( java.lang.String coLastModifiedByUserLogin ) {
this.coLastModifiedByUserLogin = coLastModifiedByUserLogin;
}
private java.lang.String coCharacterSetId;
public java.lang.String getcoCharacterSetId() {
return coCharacterSetId;
}
public void setcoCharacterSetId( java.lang.String coCharacterSetId ) {
this.coCharacterSetId = coCharacterSetId;
}
private java.lang.String dataResourceId;
public java.lang.String getdataResourceId() {
return dataResourceId;
}
public void setdataResourceId( java.lang.String dataResourceId ) {
this.dataResourceId = dataResourceId;
}
private java.lang.String coDataSourceId;
public java.lang.String getcoDataSourceId() {
return coDataSourceId;
}
public void setcoDataSourceId( java.lang.String coDataSourceId ) {
this.coDataSourceId = coDataSourceId;
}
private java.lang.String dataSourceId;
public java.lang.String getdataSourceId() {
return dataSourceId;
}
public void setdataSourceId( java.lang.String dataSourceId ) {
this.dataSourceId = dataSourceId;
}
private java.lang.String coDescription;
public java.lang.String getcoDescription() {
return coDescription;
}
public void setcoDescription( java.lang.String coDescription ) {
this.coDescription = coDescription;
}
private java.lang.String coCreatedByUserLogin;
public java.lang.String getcoCreatedByUserLogin() {
return coCreatedByUserLogin;
}
public void setcoCreatedByUserLogin( java.lang.String coCreatedByUserLogin ) {
this.coCreatedByUserLogin = coCreatedByUserLogin;
}
private java.lang.String statusId;
public java.lang.String getstatusId() {
return statusId;
}
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String coContentId;
public java.lang.String getcoContentId() {
return coContentId;
}
public void setcoContentId( java.lang.String coContentId ) {
this.coContentId = coContentId;
}
private java.lang.String coDecoratorContentId;
public java.lang.String getcoDecoratorContentId() {
return coDecoratorContentId;
}
public void setcoDecoratorContentId( java.lang.String coDecoratorContentId ) {
this.coDecoratorContentId = coDecoratorContentId;
}
private java.lang.String dataResourceTypeId;
public java.lang.String getdataResourceTypeId() {
return dataResourceTypeId;
}
public void setdataResourceTypeId( java.lang.String dataResourceTypeId ) {
this.dataResourceTypeId = dataResourceTypeId;
}
private java.lang.String coChildLeafCount;
public java.lang.String getcoChildLeafCount() {
return coChildLeafCount;
}
public void setcoChildLeafCount( java.lang.String coChildLeafCount ) {
this.coChildLeafCount = coChildLeafCount;
}
private java.sql.Timestamp createdDate;
public java.sql.Timestamp getcreatedDate() {
return createdDate;
}
public void setcreatedDate( java.sql.Timestamp createdDate ) {
this.createdDate = createdDate;
}
private java.lang.String coStatusId;
public java.lang.String getcoStatusId() {
return coStatusId;
}
public void setcoStatusId( java.lang.String coStatusId ) {
this.coStatusId = coStatusId;
}
private java.lang.String coMimeTypeId;
public java.lang.String getcoMimeTypeId() {
return coMimeTypeId;
}
public void setcoMimeTypeId( java.lang.String coMimeTypeId ) {
this.coMimeTypeId = coMimeTypeId;
}
private java.lang.String coDataResourceId;
public java.lang.String getcoDataResourceId() {
return coDataResourceId;
}
public void setcoDataResourceId( java.lang.String coDataResourceId ) {
this.coDataResourceId = coDataResourceId;
}
private java.lang.String dataResourceName;
public java.lang.String getdataResourceName() {
return dataResourceName;
}
public void setdataResourceName( java.lang.String dataResourceName ) {
this.dataResourceName = dataResourceName;
}
private java.lang.String dataTemplateTypeId;
public java.lang.String getdataTemplateTypeId() {
return dataTemplateTypeId;
}
public void setdataTemplateTypeId( java.lang.String dataTemplateTypeId ) {
this.dataTemplateTypeId = dataTemplateTypeId;
}
private java.lang.String mimeTypeId;
public java.lang.String getmimeTypeId() {
return mimeTypeId;
}
public void setmimeTypeId( java.lang.String mimeTypeId ) {
this.mimeTypeId = mimeTypeId;
}
private java.lang.String coInstanceOfContentId;
public java.lang.String getcoInstanceOfContentId() {
return coInstanceOfContentId;
}
public void setcoInstanceOfContentId( java.lang.String coInstanceOfContentId ) {
this.coInstanceOfContentId = coInstanceOfContentId;
}
private java.lang.String isPublic;
public java.lang.String getisPublic() {
return isPublic;
}
public void setisPublic( java.lang.String isPublic ) {
this.isPublic = isPublic;
}
private java.lang.String relatedDetailId;
public java.lang.String getrelatedDetailId() {
return relatedDetailId;
}
public void setrelatedDetailId( java.lang.String relatedDetailId ) {
this.relatedDetailId = relatedDetailId;
}
private java.lang.String coContentName;
public java.lang.String getcoContentName() {
return coContentName;
}
public void setcoContentName( java.lang.String coContentName ) {
this.coContentName = coContentName;
}
private java.lang.String dataCategoryId;
public java.lang.String getdataCategoryId() {
return dataCategoryId;
}
public void setdataCategoryId( java.lang.String dataCategoryId ) {
this.dataCategoryId = dataCategoryId;
}
private java.lang.String coChildBranchCount;
public java.lang.String getcoChildBranchCount() {
return coChildBranchCount;
}
public void setcoChildBranchCount( java.lang.String coChildBranchCount ) {
this.coChildBranchCount = coChildBranchCount;
}
private java.lang.String characterSetId;
public java.lang.String getcharacterSetId() {
return characterSetId;
}
public void setcharacterSetId( java.lang.String characterSetId ) {
this.characterSetId = characterSetId;
}
private java.lang.String coOwnerContentId;
public java.lang.String getcoOwnerContentId() {
return coOwnerContentId;
}
public void setcoOwnerContentId( java.lang.String coOwnerContentId ) {
this.coOwnerContentId = coOwnerContentId;
}
private java.lang.String objectInfo;
public java.lang.String getobjectInfo() {
return objectInfo;
}
public void setobjectInfo( java.lang.String objectInfo ) {
this.objectInfo = objectInfo;
}
private java.lang.String coServiceName;
public java.lang.String getcoServiceName() {
return coServiceName;
}
public void setcoServiceName( java.lang.String coServiceName ) {
this.coServiceName = coServiceName;
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
private java.lang.String coLocaleString;
public java.lang.String getcoLocaleString() {
return coLocaleString;
}
public void setcoLocaleString( java.lang.String coLocaleString ) {
this.coLocaleString = coLocaleString;
}
private java.lang.String surveyResponseId;
public java.lang.String getsurveyResponseId() {
return surveyResponseId;
}
public void setsurveyResponseId( java.lang.String surveyResponseId ) {
this.surveyResponseId = surveyResponseId;
}
private java.sql.Timestamp coLastModifiedDate;
public java.sql.Timestamp getcoLastModifiedDate() {
return coLastModifiedDate;
}
public void setcoLastModifiedDate( java.sql.Timestamp coLastModifiedDate ) {
this.coLastModifiedDate = coLastModifiedDate;
}
private java.sql.Timestamp lastModifiedDate;
public java.sql.Timestamp getlastModifiedDate() {
return lastModifiedDate;
}
public void setlastModifiedDate( java.sql.Timestamp lastModifiedDate ) {
this.lastModifiedDate = lastModifiedDate;
}
private java.lang.String surveyId;
public java.lang.String getsurveyId() {
return surveyId;
}
public void setsurveyId( java.lang.String surveyId ) {
this.surveyId = surveyId;
}
private java.sql.Timestamp coCreatedDate;
public java.sql.Timestamp getcoCreatedDate() {
return coCreatedDate;
}
public void setcoCreatedDate( java.sql.Timestamp coCreatedDate ) {
this.coCreatedDate = coCreatedDate;
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
        Collection<DataResourceContentView> objectList = new ArrayList<DataResourceContentView>();
        for (GenericValue genVal : genList) {
            objectList.add(new DataResourceContentView(genVal));
        }
        return objectList;
    }    
}
