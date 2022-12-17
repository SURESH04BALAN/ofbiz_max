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
import org.ofbiz.base.util.UtilValidate;

public class PartyNameView implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = PartyNameView.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public PartyNameView(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public PartyNameView() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"middleName", "Middle Name"},
        {"lastName", "Last Name"},
        {"groupName", "Group Name"},
        {"firstNameLocal", "First Name Local"},
        {"suffix", "Suffix"},
        {"personalTitle", "Personal Title"},
        {"partyTypeId", "Party Type Id"},
        {"statusId", "Status Id"},
        {"groupNameLocal", "Group Name Local"},
        {"lastNameLocal", "Last Name Local"},
        {"partyId", "Party Id"},
        {"description", "Description"},
        {"firstName", "First Name"},};

    protected void initObject() {
        this.middleName = "";
        this.lastName = "";
        this.groupName = "";
        this.firstNameLocal = "";
        this.suffix = "";
        this.personalTitle = "";
        this.partyTypeId = "";
        this.statusId = "";
        this.groupNameLocal = "";
        this.lastNameLocal = "";
        this.partyId = "";
        this.description = "";
        this.firstName = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.middleName = (java.lang.String) genVal.get("middleName");
        this.lastName = (java.lang.String) genVal.get("lastName");
        this.groupName = (java.lang.String) genVal.get("groupName");
        this.firstNameLocal = (java.lang.String) genVal.get("firstNameLocal");
        this.suffix = (java.lang.String) genVal.get("suffix");
        this.personalTitle = (java.lang.String) genVal.get("personalTitle");
        this.partyTypeId = (java.lang.String) genVal.get("partyTypeId");
        this.statusId = (java.lang.String) genVal.get("statusId");
        this.groupNameLocal = (java.lang.String) genVal.get("groupNameLocal");
        this.lastNameLocal = (java.lang.String) genVal.get("lastNameLocal");
        this.partyId = (java.lang.String) genVal.get("partyId");
        this.description = (java.lang.String) genVal.get("description");
        this.firstName = (java.lang.String) genVal.get("firstName");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("middleName", OrderMaxUtility.getValidEntityString(this.middleName));
        val.set("lastName", OrderMaxUtility.getValidEntityString(this.lastName));
        val.set("groupName", OrderMaxUtility.getValidEntityString(this.groupName));
        val.set("firstNameLocal", OrderMaxUtility.getValidEntityString(this.firstNameLocal));
        val.set("suffix", OrderMaxUtility.getValidEntityString(this.suffix));
        val.set("personalTitle", OrderMaxUtility.getValidEntityString(this.personalTitle));
        val.set("partyTypeId", OrderMaxUtility.getValidEntityString(this.partyTypeId));
        val.set("statusId", OrderMaxUtility.getValidEntityString(this.statusId));
        val.set("groupNameLocal", OrderMaxUtility.getValidEntityString(this.groupNameLocal));
        val.set("lastNameLocal", OrderMaxUtility.getValidEntityString(this.lastNameLocal));
        val.set("partyId", OrderMaxUtility.getValidEntityString(this.partyId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("firstName", OrderMaxUtility.getValidEntityString(this.firstName));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("middleName", this.middleName);
        valueMap.put("lastName", this.lastName);
        valueMap.put("groupName", this.groupName);
        valueMap.put("firstNameLocal", this.firstNameLocal);
        valueMap.put("suffix", this.suffix);
        valueMap.put("personalTitle", this.personalTitle);
        valueMap.put("partyTypeId", this.partyTypeId);
        valueMap.put("statusId", this.statusId);
        valueMap.put("groupNameLocal", this.groupNameLocal);
        valueMap.put("lastNameLocal", this.lastNameLocal);
        valueMap.put("partyId", this.partyId);
        valueMap.put("description", this.description);
        valueMap.put("firstName", this.firstName);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("PartyNameView");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String middleName;

    public java.lang.String getmiddleName() {
        return middleName;
    }

    public void setmiddleName(java.lang.String middleName) {
        this.middleName = middleName;
    }
    private java.lang.String lastName;

    public java.lang.String getlastName() {
        return lastName;
    }

    public void setlastName(java.lang.String lastName) {
        this.lastName = lastName;
    }
    private java.lang.String groupName;

    public java.lang.String getgroupName() {
        return groupName;
    }

    public void setgroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }
    private java.lang.String firstNameLocal;

    public java.lang.String getfirstNameLocal() {
        return firstNameLocal;
    }

    public void setfirstNameLocal(java.lang.String firstNameLocal) {
        this.firstNameLocal = firstNameLocal;
    }
    private java.lang.String suffix;

    public java.lang.String getsuffix() {
        return suffix;
    }

    public void setsuffix(java.lang.String suffix) {
        this.suffix = suffix;
    }
    private java.lang.String personalTitle;

    public java.lang.String getpersonalTitle() {
        return personalTitle;
    }

    public void setpersonalTitle(java.lang.String personalTitle) {
        this.personalTitle = personalTitle;
    }
    private java.lang.String partyTypeId;

    public java.lang.String getpartyTypeId() {
        return partyTypeId;
    }

    public void setpartyTypeId(java.lang.String partyTypeId) {
        this.partyTypeId = partyTypeId;
    }
    private java.lang.String statusId;

    public java.lang.String getstatusId() {
        return statusId;
    }

    public void setstatusId(java.lang.String statusId) {
        this.statusId = statusId;
    }
    private java.lang.String groupNameLocal;

    public java.lang.String getgroupNameLocal() {
        return groupNameLocal;
    }

    public void setgroupNameLocal(java.lang.String groupNameLocal) {
        this.groupNameLocal = groupNameLocal;
    }
    private java.lang.String lastNameLocal;

    public java.lang.String getlastNameLocal() {
        return lastNameLocal;
    }

    public void setlastNameLocal(java.lang.String lastNameLocal) {
        this.lastNameLocal = lastNameLocal;
    }
    private java.lang.String partyId;

    public java.lang.String getpartyId() {
        return partyId;
    }

    public void setpartyId(java.lang.String partyId) {
        this.partyId = partyId;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String firstName;

    public java.lang.String getfirstName() {
        return firstName;
    }

    public void setfirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyNameView> objectList = new ArrayList<PartyNameView>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyNameView(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayTypes showId) {
        StringBuilder orderToStringBuilder = new StringBuilder();


            if ("PERSON".equals(partyTypeId)) {

                if (UtilValidate.isNotEmpty(suffix)) {
                    orderToStringBuilder.append(suffix);
                    orderToStringBuilder.append(" ");
                }

                if (UtilValidate.isNotEmpty(personalTitle)) {
                    orderToStringBuilder.append(personalTitle);
                    orderToStringBuilder.append(" ");
                }

                if (UtilValidate.isNotEmpty(firstName)) {
                    orderToStringBuilder.append(firstName);
                    orderToStringBuilder.append(" ");
                }

                if (UtilValidate.isNotEmpty(middleName)) {
                    orderToStringBuilder.append(middleName);
                    orderToStringBuilder.append(" ");
                }
                if (UtilValidate.isNotEmpty(lastName)) {
                orderToStringBuilder.append(lastName);
                }

            } else {
                orderToStringBuilder.append(groupName);
            }

            //orderToStringBuilder.append(getdescription());
            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(partyId);
                orderToStringBuilder.append(" ]");
            }
//        } else {
//            orderToStringBuilder.append(partyId);
//        }

        return orderToStringBuilder.toString();
    }
}
