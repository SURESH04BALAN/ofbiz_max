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
public class UomAndType implements GenericValueObjectInterface {
public static final String module =UomAndType.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public UomAndType(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public UomAndType () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"typeDescription","Type Description"},
{"typeParentTypeId","Type Parent Type Id"},
{"uomTypeId","Uom Type Id"},
{"typeHasTable","Type Has Table"},
{"description","Description"},
{"uomId","Uom Id"},
{"abbreviation","Abbreviation"},
{"typeUomTypeId","Type Uom Type Id"},
};
protected void initObject(){
this.typeDescription = "";
this.typeParentTypeId = "";
this.uomTypeId = "";
this.typeHasTable = "";
this.description = "";
this.uomId = "";
this.abbreviation = "";
this.typeUomTypeId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.typeDescription = (java.lang.String) genVal.get("typeDescription");
this.typeParentTypeId = (java.lang.String) genVal.get("typeParentTypeId");
this.uomTypeId = (java.lang.String) genVal.get("uomTypeId");
this.typeHasTable = (java.lang.String) genVal.get("typeHasTable");
this.description = (java.lang.String) genVal.get("description");
this.uomId = (java.lang.String) genVal.get("uomId");
this.abbreviation = (java.lang.String) genVal.get("abbreviation");
this.typeUomTypeId = (java.lang.String) genVal.get("typeUomTypeId");
}
protected void getGenericValue(GenericValue val) {
val.set("typeDescription",OrderMaxUtility.getValidEntityString(this.typeDescription));
val.set("typeParentTypeId",OrderMaxUtility.getValidEntityString(this.typeParentTypeId));
val.set("uomTypeId",OrderMaxUtility.getValidEntityString(this.uomTypeId));
val.set("typeHasTable",OrderMaxUtility.getValidEntityString(this.typeHasTable));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("uomId",OrderMaxUtility.getValidEntityString(this.uomId));
val.set("abbreviation",OrderMaxUtility.getValidEntityString(this.abbreviation));
val.set("typeUomTypeId",OrderMaxUtility.getValidEntityString(this.typeUomTypeId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("typeDescription",this.typeDescription);
valueMap.put("typeParentTypeId",this.typeParentTypeId);
valueMap.put("uomTypeId",this.uomTypeId);
valueMap.put("typeHasTable",this.typeHasTable);
valueMap.put("description",this.description);
valueMap.put("uomId",this.uomId);
valueMap.put("abbreviation",this.abbreviation);
valueMap.put("typeUomTypeId",this.typeUomTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("UomAndType");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String typeDescription;
public java.lang.String gettypeDescription() {
return typeDescription;
}
public void settypeDescription( java.lang.String typeDescription ) {
this.typeDescription = typeDescription;
}
private java.lang.String typeParentTypeId;
public java.lang.String gettypeParentTypeId() {
return typeParentTypeId;
}
public void settypeParentTypeId( java.lang.String typeParentTypeId ) {
this.typeParentTypeId = typeParentTypeId;
}
private java.lang.String uomTypeId;
public java.lang.String getuomTypeId() {
return uomTypeId;
}
public void setuomTypeId( java.lang.String uomTypeId ) {
this.uomTypeId = uomTypeId;
}
private java.lang.String typeHasTable;
public java.lang.String gettypeHasTable() {
return typeHasTable;
}
public void settypeHasTable( java.lang.String typeHasTable ) {
this.typeHasTable = typeHasTable;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String uomId;
public java.lang.String getuomId() {
return uomId;
}
public void setuomId( java.lang.String uomId ) {
this.uomId = uomId;
}
private java.lang.String abbreviation;
public java.lang.String getabbreviation() {
return abbreviation;
}
public void setabbreviation( java.lang.String abbreviation ) {
this.abbreviation = abbreviation;
}
private java.lang.String typeUomTypeId;
public java.lang.String gettypeUomTypeId() {
return typeUomTypeId;
}
public void settypeUomTypeId( java.lang.String typeUomTypeId ) {
this.typeUomTypeId = typeUomTypeId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<UomAndType> objectList = new ArrayList<UomAndType>();
        for (GenericValue genVal : genList) {
            objectList.add(new UomAndType(genVal));
        }
        return objectList;
    }    
}
