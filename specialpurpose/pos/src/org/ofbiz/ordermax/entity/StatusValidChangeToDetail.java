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

public class StatusValidChangeToDetail implements GenericValueObjectInterface {

    public static final String module = StatusValidChangeToDetail.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public StatusValidChangeToDetail(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public StatusValidChangeToDetail() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"statusId", "Status Id"},
        {"statusCode", "Status Code"},
        {"conditionExpression", "Condition Expression"},
        {"statusIdTo", "Status Id To"},
        {"statusTypeId", "Status Type Id"},
        {"description", "Description"},
        {"sequenceId", "Sequence Id"},
        {"transitionName", "Transition Name"},};

    protected void initObject() {
        this.statusId = "";
        this.statusCode = "";
        this.conditionExpression = "";
        this.statusIdTo = "";
        this.statusTypeId = "";
        this.description = "";
        this.sequenceId = "";
        this.transitionName = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.statusCode = (java.lang.String) genVal.get("statusCode");
        this.conditionExpression = (java.lang.String) genVal.get("conditionExpression");
        this.statusIdTo = (java.lang.String) genVal.get("statusIdTo");
        this.statusTypeId = (java.lang.String) genVal.get("statusTypeId");
        this.description = (java.lang.String) genVal.get("description");
        this.sequenceId = (java.lang.String) genVal.get("sequenceId");
        this.transitionName = (java.lang.String) genVal.get("transitionName");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("statusCode", OrderMaxUtility.getValidEntityString(this.statusCode));
        val.set("conditionExpression", OrderMaxUtility.getValidEntityString(this.conditionExpression));
        val.set("statusIdTo", OrderMaxUtility.getValidEntityString(this.statusIdTo));
        val.set("statusTypeId", OrderMaxUtility.getValidEntityString(this.statusTypeId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("sequenceId", OrderMaxUtility.getValidEntityString(this.sequenceId));
        val.set("transitionName", OrderMaxUtility.getValidEntityString(this.transitionName));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("statusId", this.statusId);
        valueMap.put("statusCode", this.statusCode);
        valueMap.put("conditionExpression", this.conditionExpression);
        valueMap.put("statusIdTo", this.statusIdTo);
        valueMap.put("statusTypeId", this.statusTypeId);
        valueMap.put("description", this.description);
        valueMap.put("sequenceId", this.sequenceId);
        valueMap.put("transitionName", this.transitionName);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("StatusValidChangeToDetail");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String statusCode;

    public java.lang.String getstatusCode() {
        return statusCode;
    }

    public void setstatusCode(java.lang.String statusCode) {
        this.statusCode = statusCode;
    }
    private java.lang.String conditionExpression;

    public java.lang.String getconditionExpression() {
        return conditionExpression;
    }

    public void setconditionExpression(java.lang.String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }
    private java.lang.String statusIdTo;

    public java.lang.String getstatusIdTo() {
        return statusIdTo;
    }

    public void setstatusIdTo(java.lang.String statusIdTo) {
        this.statusIdTo = statusIdTo;
    }
    private java.lang.String statusTypeId;

    public java.lang.String getstatusTypeId() {
        return statusTypeId;
    }

    public void setstatusTypeId(java.lang.String statusTypeId) {
        this.statusTypeId = statusTypeId;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String sequenceId;

    public java.lang.String getsequenceId() {
        return sequenceId;
    }

    public void setsequenceId(java.lang.String sequenceId) {
        this.sequenceId = sequenceId;
    }
    private java.lang.String transitionName;

    public java.lang.String gettransitionName() {
        return transitionName;
    }

    public void settransitionName(java.lang.String transitionName) {
        this.transitionName = transitionName;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<StatusValidChangeToDetail> objectList = new ArrayList<StatusValidChangeToDetail>();
        for (GenericValue genVal : genList) {
            objectList.add(new StatusValidChangeToDetail(genVal));
        }
        return objectList;
    }
}
