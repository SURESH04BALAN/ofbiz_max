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
public class EnumTypeChildAndEnum implements GenericValueObjectInterface {
public static final String module =EnumTypeChildAndEnum.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public EnumTypeChildAndEnum(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public EnumTypeChildAndEnum () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"enumTypeId","Enum Type Id"},
{"childDescription","Child Description"},
{"enumId","Enum Id"},
{"enumCode","Enum Code"},
{"parentHasTable","Parent Has Table"},
{"parentDescription","Parent Description"},
{"childEnumTypeId","Child Enum Type Id"},
{"parentEnumTypeId","Parent Enum Type Id"},
{"childHasTable","Child Has Table"},
{"description","Description"},
{"parentParentTypeId","Parent Parent Type Id"},
{"sequenceId","Sequence Id"},
};
protected void initObject(){
this.enumTypeId = "";
this.childDescription = "";
this.enumId = "";
this.enumCode = "";
this.parentHasTable = "";
this.parentDescription = "";
this.childEnumTypeId = "";
this.parentEnumTypeId = "";
this.childHasTable = "";
this.description = "";
this.parentParentTypeId = "";
this.sequenceId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.enumTypeId = (java.lang.String) genVal.get("enumTypeId");
this.childDescription = (java.lang.String) genVal.get("childDescription");
this.enumId = (java.lang.String) genVal.get("enumId");
this.enumCode = (java.lang.String) genVal.get("enumCode");
this.parentHasTable = (java.lang.String) genVal.get("parentHasTable");
this.parentDescription = (java.lang.String) genVal.get("parentDescription");
this.childEnumTypeId = (java.lang.String) genVal.get("childEnumTypeId");
this.parentEnumTypeId = (java.lang.String) genVal.get("parentEnumTypeId");
this.childHasTable = (java.lang.String) genVal.get("childHasTable");
this.description = (java.lang.String) genVal.get("description");
this.parentParentTypeId = (java.lang.String) genVal.get("parentParentTypeId");
this.sequenceId = (java.lang.String) genVal.get("sequenceId");
}
protected void getGenericValue(GenericValue val) {
val.set("enumTypeId",OrderMaxUtility.getValidEntityString(this.enumTypeId));
val.set("childDescription",OrderMaxUtility.getValidEntityString(this.childDescription));
val.set("enumId",OrderMaxUtility.getValidEntityString(this.enumId));
val.set("enumCode",OrderMaxUtility.getValidEntityString(this.enumCode));
val.set("parentHasTable",OrderMaxUtility.getValidEntityString(this.parentHasTable));
val.set("parentDescription",OrderMaxUtility.getValidEntityString(this.parentDescription));
val.set("childEnumTypeId",OrderMaxUtility.getValidEntityString(this.childEnumTypeId));
val.set("parentEnumTypeId",OrderMaxUtility.getValidEntityString(this.parentEnumTypeId));
val.set("childHasTable",OrderMaxUtility.getValidEntityString(this.childHasTable));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("parentParentTypeId",OrderMaxUtility.getValidEntityString(this.parentParentTypeId));
val.set("sequenceId",OrderMaxUtility.getValidEntityString(this.sequenceId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("enumTypeId",this.enumTypeId);
valueMap.put("childDescription",this.childDescription);
valueMap.put("enumId",this.enumId);
valueMap.put("enumCode",this.enumCode);
valueMap.put("parentHasTable",this.parentHasTable);
valueMap.put("parentDescription",this.parentDescription);
valueMap.put("childEnumTypeId",this.childEnumTypeId);
valueMap.put("parentEnumTypeId",this.parentEnumTypeId);
valueMap.put("childHasTable",this.childHasTable);
valueMap.put("description",this.description);
valueMap.put("parentParentTypeId",this.parentParentTypeId);
valueMap.put("sequenceId",this.sequenceId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("EnumTypeChildAndEnum");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String enumTypeId;
public java.lang.String getenumTypeId() {
return enumTypeId;
}
public void setenumTypeId( java.lang.String enumTypeId ) {
this.enumTypeId = enumTypeId;
}
private java.lang.String childDescription;
public java.lang.String getchildDescription() {
return childDescription;
}
public void setchildDescription( java.lang.String childDescription ) {
this.childDescription = childDescription;
}
private java.lang.String enumId;
public java.lang.String getenumId() {
return enumId;
}
public void setenumId( java.lang.String enumId ) {
this.enumId = enumId;
}
private java.lang.String enumCode;
public java.lang.String getenumCode() {
return enumCode;
}
public void setenumCode( java.lang.String enumCode ) {
this.enumCode = enumCode;
}
private java.lang.String parentHasTable;
public java.lang.String getparentHasTable() {
return parentHasTable;
}
public void setparentHasTable( java.lang.String parentHasTable ) {
this.parentHasTable = parentHasTable;
}
private java.lang.String parentDescription;
public java.lang.String getparentDescription() {
return parentDescription;
}
public void setparentDescription( java.lang.String parentDescription ) {
this.parentDescription = parentDescription;
}
private java.lang.String childEnumTypeId;
public java.lang.String getchildEnumTypeId() {
return childEnumTypeId;
}
public void setchildEnumTypeId( java.lang.String childEnumTypeId ) {
this.childEnumTypeId = childEnumTypeId;
}
private java.lang.String parentEnumTypeId;
public java.lang.String getparentEnumTypeId() {
return parentEnumTypeId;
}
public void setparentEnumTypeId( java.lang.String parentEnumTypeId ) {
this.parentEnumTypeId = parentEnumTypeId;
}
private java.lang.String childHasTable;
public java.lang.String getchildHasTable() {
return childHasTable;
}
public void setchildHasTable( java.lang.String childHasTable ) {
this.childHasTable = childHasTable;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String parentParentTypeId;
public java.lang.String getparentParentTypeId() {
return parentParentTypeId;
}
public void setparentParentTypeId( java.lang.String parentParentTypeId ) {
this.parentParentTypeId = parentParentTypeId;
}
private java.lang.String sequenceId;
public java.lang.String getsequenceId() {
return sequenceId;
}
public void setsequenceId( java.lang.String sequenceId ) {
this.sequenceId = sequenceId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<EnumTypeChildAndEnum> objectList = new ArrayList<EnumTypeChildAndEnum>();
        for (GenericValue genVal : genList) {
            objectList.add(new EnumTypeChildAndEnum(genVal));
        }
        return objectList;
    }    
}
