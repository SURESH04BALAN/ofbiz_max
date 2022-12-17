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

public class DataResource implements GenericValueObjectInterface {

    public static final String module = DataResource.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public DataResource(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public DataResource() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"dataResourceName", "Data Resource Name"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"dataTemplateTypeId", "Data Template Type Id"},
        {"mimeTypeId", "Mime Type Id"},
        {"dataResourceId", "Data Resource Id"},
        {"relatedDetailId", "Related Detail Id"},
        {"isPublic", "Is Public"},
        {"dataSourceId", "Data Source Id"},
        {"statusId", "Status Id"},
        {"dataCategoryId", "Data Category Id"},
        {"characterSetId", "Character Set Id"},
        {"objectInfo", "Object Info"},
        {"localeString", "Locale String"},
        {"lastModifiedByUserLogin", "Last Modified By User Login"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"dataResourceTypeId", "Data Resource Type Id"},
        {"surveyResponseId", "Survey Response Id"},
        {"lastModifiedDate", "Last Modified Date"},
        {"surveyId", "Survey Id"},
        {"createdDate", "Created Date"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdByUserLogin", "Created By User Login"},};

    protected void initObject() {
        this.dataResourceName = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.dataTemplateTypeId = "";
        this.mimeTypeId = "";
        this.dataResourceId = "";
        this.relatedDetailId = "";
        this.isPublic = "";
        this.dataSourceId = "";
        this.statusId = "";
        this.dataCategoryId = "";
        this.characterSetId = "";
        this.objectInfo = "";
        this.localeString = "";
        this.lastModifiedByUserLogin = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.dataResourceTypeId = "";
        this.surveyResponseId = "";
        this.lastModifiedDate = UtilDateTime.nowTimestamp();
        this.surveyId = "";
        this.createdDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdByUserLogin = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.dataResourceName = (java.lang.String) genVal.get("dataResourceName");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.dataTemplateTypeId = (java.lang.String) genVal.get("dataTemplateTypeId");
        this.mimeTypeId = (java.lang.String) genVal.get("mimeTypeId");
        this.dataResourceId = (java.lang.String) genVal.get("dataResourceId");
        this.relatedDetailId = (java.lang.String) genVal.get("relatedDetailId");
        this.isPublic = (java.lang.String) genVal.get("isPublic");
        this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.dataCategoryId = (java.lang.String) genVal.get("dataCategoryId");
        this.characterSetId = (java.lang.String) genVal.get("characterSetId");
        this.objectInfo = (java.lang.String) genVal.get("objectInfo");
        this.localeString = (java.lang.String) genVal.get("localeString");
        this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.dataResourceTypeId = (java.lang.String) genVal.get("dataResourceTypeId");
        this.surveyResponseId = (java.lang.String) genVal.get("surveyResponseId");
        this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
        this.surveyId = (java.lang.String) genVal.get("surveyId");
        this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("dataResourceName", OrderMaxUtility.getValidEntityString(this.dataResourceName));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("dataTemplateTypeId", OrderMaxUtility.getValidEntityString(this.dataTemplateTypeId));
        val.set("mimeTypeId", OrderMaxUtility.getValidEntityString(this.mimeTypeId));
        val.set("dataResourceId", OrderMaxUtility.getValidEntityString(this.dataResourceId));
        val.set("relatedDetailId", OrderMaxUtility.getValidEntityString(this.relatedDetailId));
        val.set("isPublic", OrderMaxUtility.getValidEntityString(this.isPublic));
        val.set("dataSourceId", OrderMaxUtility.getValidEntityString(this.dataSourceId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("dataCategoryId", OrderMaxUtility.getValidEntityString(this.dataCategoryId));
        val.set("characterSetId", OrderMaxUtility.getValidEntityString(this.characterSetId));
        val.set("objectInfo", OrderMaxUtility.getValidEntityString(this.objectInfo));
        val.set("localeString", OrderMaxUtility.getValidEntityString(this.localeString));
        val.set("lastModifiedByUserLogin", OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("dataResourceTypeId", OrderMaxUtility.getValidEntityString(this.dataResourceTypeId));
        val.set("surveyResponseId", OrderMaxUtility.getValidEntityString(this.surveyResponseId));
        val.set("lastModifiedDate", OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
        val.set("surveyId", OrderMaxUtility.getValidEntityString(this.surveyId));
        val.set("createdDate", OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdByUserLogin", OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("dataResourceName", this.dataResourceName);
        valueMap.put("dataTemplateTypeId", this.dataTemplateTypeId);
        valueMap.put("mimeTypeId", this.mimeTypeId);
        valueMap.put("dataResourceId", this.dataResourceId);
        valueMap.put("relatedDetailId", this.relatedDetailId);
        valueMap.put("isPublic", this.isPublic);
        valueMap.put("dataSourceId", this.dataSourceId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("dataCategoryId", this.dataCategoryId);
        valueMap.put("characterSetId", this.characterSetId);
        valueMap.put("objectInfo", this.objectInfo);
        valueMap.put("localeString", this.localeString);
        valueMap.put("lastModifiedByUserLogin", this.lastModifiedByUserLogin);
        valueMap.put("dataResourceTypeId", this.dataResourceTypeId);
        valueMap.put("surveyResponseId", this.surveyResponseId);
        valueMap.put("lastModifiedDate", this.lastModifiedDate);
        valueMap.put("surveyId", this.surveyId);
        valueMap.put("createdDate", this.createdDate);
        valueMap.put("createdByUserLogin", this.createdByUserLogin);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("DataResource");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String dataResourceName;

    public java.lang.String getdataResourceName() {
        return dataResourceName;
    }

    public void setdataResourceName(java.lang.String dataResourceName) {
        this.dataResourceName = dataResourceName;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String dataTemplateTypeId;

    public java.lang.String getdataTemplateTypeId() {
        return dataTemplateTypeId;
    }

    public void setdataTemplateTypeId(java.lang.String dataTemplateTypeId) {
        this.dataTemplateTypeId = dataTemplateTypeId;
    }
    private java.lang.String mimeTypeId;

    public java.lang.String getmimeTypeId() {
        return mimeTypeId;
    }

    public void setmimeTypeId(java.lang.String mimeTypeId) {
        this.mimeTypeId = mimeTypeId;
    }
    private java.lang.String dataResourceId;

    public java.lang.String getdataResourceId() {
        return dataResourceId;
    }

    public void setdataResourceId(java.lang.String dataResourceId) {
        this.dataResourceId = dataResourceId;
    }
    private java.lang.String relatedDetailId;

    public java.lang.String getrelatedDetailId() {
        return relatedDetailId;
    }

    public void setrelatedDetailId(java.lang.String relatedDetailId) {
        this.relatedDetailId = relatedDetailId;
    }
    private java.lang.String isPublic;

    public java.lang.String getisPublic() {
        return isPublic;
    }

    public void setisPublic(java.lang.String isPublic) {
        this.isPublic = isPublic;
    }
    private java.lang.String dataSourceId;

    public java.lang.String getdataSourceId() {
        return dataSourceId;
    }

    public void setdataSourceId(java.lang.String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String dataCategoryId;

    public java.lang.String getdataCategoryId() {
        return dataCategoryId;
    }

    public void setdataCategoryId(java.lang.String dataCategoryId) {
        this.dataCategoryId = dataCategoryId;
    }
    private java.lang.String characterSetId;

    public java.lang.String getcharacterSetId() {
        return characterSetId;
    }

    public void setcharacterSetId(java.lang.String characterSetId) {
        this.characterSetId = characterSetId;
    }
    private java.lang.String objectInfo;

    public java.lang.String getobjectInfo() {
        return objectInfo;
    }

    public void setobjectInfo(java.lang.String objectInfo) {
        this.objectInfo = objectInfo;
    }
    private java.lang.String localeString;

    public java.lang.String getlocaleString() {
        return localeString;
    }

    public void setlocaleString(java.lang.String localeString) {
        this.localeString = localeString;
    }
    private java.lang.String lastModifiedByUserLogin;

    public java.lang.String getlastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setlastModifiedByUserLogin(java.lang.String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String dataResourceTypeId;

    public java.lang.String getdataResourceTypeId() {
        return dataResourceTypeId;
    }

    public void setdataResourceTypeId(java.lang.String dataResourceTypeId) {
        this.dataResourceTypeId = dataResourceTypeId;
    }
    private java.lang.String surveyResponseId;

    public java.lang.String getsurveyResponseId() {
        return surveyResponseId;
    }

    public void setsurveyResponseId(java.lang.String surveyResponseId) {
        this.surveyResponseId = surveyResponseId;
    }
    private java.sql.Timestamp lastModifiedDate;

    public java.sql.Timestamp getlastModifiedDate() {
        return lastModifiedDate;
    }

    public void setlastModifiedDate(java.sql.Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    private java.lang.String surveyId;

    public java.lang.String getsurveyId() {
        return surveyId;
    }

    public void setsurveyId(java.lang.String surveyId) {
        this.surveyId = surveyId;
    }
    private java.sql.Timestamp createdDate;

    public java.sql.Timestamp getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(java.sql.Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String createdByUserLogin;

    public java.lang.String getcreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setcreatedByUserLogin(java.lang.String createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<DataResource> objectList = new ArrayList<DataResource>();
        for (GenericValue genVal : genList) {
            objectList.add(new DataResource(genVal));
        }
        return objectList;
    }
}
