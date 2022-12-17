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

public class Content implements GenericValueObjectInterface {

    public static final String module = Content.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public Content(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public Content() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"childLeafCount", "Child Leaf Count"},
        {"ownerContentId", "Owner Content Id"},
        {"instanceOfContentId", "Instance Of Content Id"},
        {"dataResourceId", "Data Resource Id"},
        {"contentId", "Content Id"},
        {"dataSourceId", "Data Source Id"},
        {"serviceName", "Service Name"},
        {"childBranchCount", "Child Branch Count"},
        {"statusId", "Status Id"},
        {"privilegeEnumId", "Privilege Enum Id"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"templateDataResourceId", "Template Data Resource Id"},
        {"createdStamp", "Created Stamp"},
        {"description", "Description"},
        {"decoratorContentId", "Decorator Content Id"},
        {"createdDate", "Created Date"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"mimeTypeId", "Mime Type Id"},
        {"contentName", "Content Name"},
        {"characterSetId", "Character Set Id"},
        {"lastModifiedByUserLogin", "Last Modified By User Login"},
        {"localeString", "Locale String"},
        {"contentTypeId", "Content Type Id"},
        {"lastModifiedDate", "Last Modified Date"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdByUserLogin", "Created By User Login"},};

    protected void initObject() {
        this.childLeafCount = "";
        this.ownerContentId = "";
        this.instanceOfContentId = "";
        this.dataResourceId = "";
        this.contentId = "";
        this.dataSourceId = "";
        this.serviceName = "";
        this.childBranchCount = "";
        this.statusId = "";
        this.privilegeEnumId = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.templateDataResourceId = "";
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.description = "";
        this.decoratorContentId = "";
        this.createdDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.mimeTypeId = "";
        this.contentName = "";
        this.characterSetId = "";
        this.lastModifiedByUserLogin = "";
        this.localeString = "";
        this.contentTypeId = "";
        this.lastModifiedDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdByUserLogin = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.childLeafCount = (java.lang.String) genVal.get("childLeafCount");
        this.ownerContentId = (java.lang.String) genVal.get("ownerContentId");
        this.instanceOfContentId = (java.lang.String) genVal.get("instanceOfContentId");
        this.dataResourceId = (java.lang.String) genVal.get("dataResourceId");
        this.contentId = (java.lang.String) genVal.get("contentId");
        this.dataSourceId = (java.lang.String) genVal.get("dataSourceId");
        this.serviceName = (java.lang.String) genVal.get("serviceName");
        this.childBranchCount = (java.lang.String) genVal.get("childBranchCount");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.privilegeEnumId = (java.lang.String) genVal.get("privilegeEnumId");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.templateDataResourceId = (java.lang.String) genVal.get("templateDataResourceId");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.description = (java.lang.String) genVal.get("description");
        this.decoratorContentId = (java.lang.String) genVal.get("decoratorContentId");
        this.createdDate = (java.sql.Timestamp) genVal.get("createdDate");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.mimeTypeId = (java.lang.String) genVal.get("mimeTypeId");
        this.contentName = (java.lang.String) genVal.get("contentName");
        this.characterSetId = (java.lang.String) genVal.get("characterSetId");
        this.lastModifiedByUserLogin = (java.lang.String) genVal.get("lastModifiedByUserLogin");
        this.localeString = (java.lang.String) genVal.get("localeString");
        this.contentTypeId = (java.lang.String) genVal.get("contentTypeId");
        this.lastModifiedDate = (java.sql.Timestamp) genVal.get("lastModifiedDate");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdByUserLogin = (java.lang.String) genVal.get("createdByUserLogin");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("childLeafCount", OrderMaxUtility.getValidEntityString(this.childLeafCount));
        val.set("ownerContentId", OrderMaxUtility.getValidEntityString(this.ownerContentId));
        val.set("instanceOfContentId", OrderMaxUtility.getValidEntityString(this.instanceOfContentId));
        val.set("dataResourceId", OrderMaxUtility.getValidEntityString(this.dataResourceId));
        val.set("contentId", OrderMaxUtility.getValidEntityString(this.contentId));
        val.set("dataSourceId", OrderMaxUtility.getValidEntityString(this.dataSourceId));
        val.set("serviceName", OrderMaxUtility.getValidEntityString(this.serviceName));
        val.set("childBranchCount", OrderMaxUtility.getValidEntityString(this.childBranchCount));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("privilegeEnumId", OrderMaxUtility.getValidEntityString(this.privilegeEnumId));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("templateDataResourceId", OrderMaxUtility.getValidEntityString(this.templateDataResourceId));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("decoratorContentId", OrderMaxUtility.getValidEntityString(this.decoratorContentId));
        val.set("createdDate", OrderMaxUtility.getValidEntityTimestamp(this.createdDate));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("mimeTypeId", OrderMaxUtility.getValidEntityString(this.mimeTypeId));
        val.set("contentName", OrderMaxUtility.getValidEntityString(this.contentName));
        val.set("characterSetId", OrderMaxUtility.getValidEntityString(this.characterSetId));
        val.set("lastModifiedByUserLogin", OrderMaxUtility.getValidEntityString(this.lastModifiedByUserLogin));
        val.set("localeString", OrderMaxUtility.getValidEntityString(this.localeString));
        val.set("contentTypeId", OrderMaxUtility.getValidEntityString(this.contentTypeId));
        val.set("lastModifiedDate", OrderMaxUtility.getValidEntityTimestamp(this.lastModifiedDate));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdByUserLogin", OrderMaxUtility.getValidEntityString(this.createdByUserLogin));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("childLeafCount", this.childLeafCount);
        valueMap.put("ownerContentId", this.ownerContentId);
        valueMap.put("instanceOfContentId", this.instanceOfContentId);
        valueMap.put("dataResourceId", this.dataResourceId);
        valueMap.put("contentId", this.contentId);
        valueMap.put("dataSourceId", this.dataSourceId);
        valueMap.put("serviceName", this.serviceName);
        valueMap.put("childBranchCount", this.childBranchCount);
        valueMap.put("statusId", this.statusId);
        valueMap.put("privilegeEnumId", this.privilegeEnumId);
        valueMap.put("templateDataResourceId", this.templateDataResourceId);
        valueMap.put("description", this.description);
        valueMap.put("decoratorContentId", this.decoratorContentId);
        valueMap.put("createdDate", this.createdDate);
        valueMap.put("mimeTypeId", this.mimeTypeId);
        valueMap.put("contentName", this.contentName);
        valueMap.put("characterSetId", this.characterSetId);
        valueMap.put("lastModifiedByUserLogin", this.lastModifiedByUserLogin);
        valueMap.put("localeString", this.localeString);
        valueMap.put("contentTypeId", this.contentTypeId);
        valueMap.put("lastModifiedDate", this.lastModifiedDate);
        valueMap.put("createdByUserLogin", this.createdByUserLogin);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("Content");
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

    public void setchildLeafCount(java.lang.String childLeafCount) {
        this.childLeafCount = childLeafCount;
    }
    private java.lang.String ownerContentId;

    public java.lang.String getownerContentId() {
        return ownerContentId;
    }

    public void setownerContentId(java.lang.String ownerContentId) {
        this.ownerContentId = ownerContentId;
    }
    private java.lang.String instanceOfContentId;

    public java.lang.String getinstanceOfContentId() {
        return instanceOfContentId;
    }

    public void setinstanceOfContentId(java.lang.String instanceOfContentId) {
        this.instanceOfContentId = instanceOfContentId;
    }
    private java.lang.String dataResourceId;

    public java.lang.String getdataResourceId() {
        return dataResourceId;
    }

    public void setdataResourceId(java.lang.String dataResourceId) {
        this.dataResourceId = dataResourceId;
    }
    private java.lang.String contentId;

    public java.lang.String getcontentId() {
        return contentId;
    }

    public void setcontentId(java.lang.String contentId) {
        this.contentId = contentId;
    }
    private java.lang.String dataSourceId;

    public java.lang.String getdataSourceId() {
        return dataSourceId;
    }

    public void setdataSourceId(java.lang.String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
    private java.lang.String serviceName;

    public java.lang.String getserviceName() {
        return serviceName;
    }

    public void setserviceName(java.lang.String serviceName) {
        this.serviceName = serviceName;
    }
    private java.lang.String childBranchCount;

    public java.lang.String getchildBranchCount() {
        return childBranchCount;
    }

    public void setchildBranchCount(java.lang.String childBranchCount) {
        this.childBranchCount = childBranchCount;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String privilegeEnumId;

    public java.lang.String getprivilegeEnumId() {
        return privilegeEnumId;
    }

    public void setprivilegeEnumId(java.lang.String privilegeEnumId) {
        this.privilegeEnumId = privilegeEnumId;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.lang.String templateDataResourceId;

    public java.lang.String gettemplateDataResourceId() {
        return templateDataResourceId;
    }

    public void settemplateDataResourceId(java.lang.String templateDataResourceId) {
        this.templateDataResourceId = templateDataResourceId;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String decoratorContentId;

    public java.lang.String getdecoratorContentId() {
        return decoratorContentId;
    }

    public void setdecoratorContentId(java.lang.String decoratorContentId) {
        this.decoratorContentId = decoratorContentId;
    }
    private java.sql.Timestamp createdDate;

    public java.sql.Timestamp getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(java.sql.Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String mimeTypeId;

    public java.lang.String getmimeTypeId() {
        return mimeTypeId;
    }

    public void setmimeTypeId(java.lang.String mimeTypeId) {
        this.mimeTypeId = mimeTypeId;
    }
    private java.lang.String contentName;

    public java.lang.String getcontentName() {
        return contentName;
    }

    public void setcontentName(java.lang.String contentName) {
        this.contentName = contentName;
    }
    private java.lang.String characterSetId;

    public java.lang.String getcharacterSetId() {
        return characterSetId;
    }

    public void setcharacterSetId(java.lang.String characterSetId) {
        this.characterSetId = characterSetId;
    }
    private java.lang.String lastModifiedByUserLogin;

    public java.lang.String getlastModifiedByUserLogin() {
        return lastModifiedByUserLogin;
    }

    public void setlastModifiedByUserLogin(java.lang.String lastModifiedByUserLogin) {
        this.lastModifiedByUserLogin = lastModifiedByUserLogin;
    }
    private java.lang.String localeString;

    public java.lang.String getlocaleString() {
        return localeString;
    }

    public void setlocaleString(java.lang.String localeString) {
        this.localeString = localeString;
    }
    private java.lang.String contentTypeId;

    public java.lang.String getcontentTypeId() {
        return contentTypeId;
    }

    public void setcontentTypeId(java.lang.String contentTypeId) {
        this.contentTypeId = contentTypeId;
    }
    private java.sql.Timestamp lastModifiedDate;

    public java.sql.Timestamp getlastModifiedDate() {
        return lastModifiedDate;
    }

    public void setlastModifiedDate(java.sql.Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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
        Collection<Content> objectList = new ArrayList<Content>();
        for (GenericValue genVal : genList) {
            objectList.add(new Content(genVal));
        }
        return objectList;
    }
}
