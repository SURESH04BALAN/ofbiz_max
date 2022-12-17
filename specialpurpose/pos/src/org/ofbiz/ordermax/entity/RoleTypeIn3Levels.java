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
public class RoleTypeIn3Levels implements GenericValueObjectInterface {
public static final String module =RoleTypeIn3Levels.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public RoleTypeIn3Levels(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public RoleTypeIn3Levels () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"midRoleTypeId","Mid Role Type Id"},
{"topRoleTypeId","Top Role Type Id"},
{"topDescription","Top Description"},
{"lowDescription","Low Description"},
{"lowRoleTypeId","Low Role Type Id"},
{"midDescription","Mid Description"},
};
protected void initObject(){
this.midRoleTypeId = "";
this.topRoleTypeId = "";
this.topDescription = "";
this.lowDescription = "";
this.lowRoleTypeId = "";
this.midDescription = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.midRoleTypeId = (java.lang.String) genVal.get("midRoleTypeId");
this.topRoleTypeId = (java.lang.String) genVal.get("topRoleTypeId");
this.topDescription = (java.lang.String) genVal.get("topDescription");
this.lowDescription = (java.lang.String) genVal.get("lowDescription");
this.lowRoleTypeId = (java.lang.String) genVal.get("lowRoleTypeId");
this.midDescription = (java.lang.String) genVal.get("midDescription");
}
protected void getGenericValue(GenericValue val) {
val.set("midRoleTypeId",OrderMaxUtility.getValidEntityString(this.midRoleTypeId));
val.set("topRoleTypeId",OrderMaxUtility.getValidEntityString(this.topRoleTypeId));
val.set("topDescription",OrderMaxUtility.getValidEntityString(this.topDescription));
val.set("lowDescription",OrderMaxUtility.getValidEntityString(this.lowDescription));
val.set("lowRoleTypeId",OrderMaxUtility.getValidEntityString(this.lowRoleTypeId));
val.set("midDescription",OrderMaxUtility.getValidEntityString(this.midDescription));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("midRoleTypeId",this.midRoleTypeId);
valueMap.put("topRoleTypeId",this.topRoleTypeId);
valueMap.put("topDescription",this.topDescription);
valueMap.put("lowDescription",this.lowDescription);
valueMap.put("lowRoleTypeId",this.lowRoleTypeId);
valueMap.put("midDescription",this.midDescription);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("RoleTypeIn3Levels");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String midRoleTypeId;
public java.lang.String getmidRoleTypeId() {
return midRoleTypeId;
}
public void setmidRoleTypeId( java.lang.String midRoleTypeId ) {
this.midRoleTypeId = midRoleTypeId;
}
private java.lang.String topRoleTypeId;
public java.lang.String gettopRoleTypeId() {
return topRoleTypeId;
}
public void settopRoleTypeId( java.lang.String topRoleTypeId ) {
this.topRoleTypeId = topRoleTypeId;
}
private java.lang.String topDescription;
public java.lang.String gettopDescription() {
return topDescription;
}
public void settopDescription( java.lang.String topDescription ) {
this.topDescription = topDescription;
}
private java.lang.String lowDescription;
public java.lang.String getlowDescription() {
return lowDescription;
}
public void setlowDescription( java.lang.String lowDescription ) {
this.lowDescription = lowDescription;
}
private java.lang.String lowRoleTypeId;
public java.lang.String getlowRoleTypeId() {
return lowRoleTypeId;
}
public void setlowRoleTypeId( java.lang.String lowRoleTypeId ) {
this.lowRoleTypeId = lowRoleTypeId;
}
private java.lang.String midDescription;
public java.lang.String getmidDescription() {
return midDescription;
}
public void setmidDescription( java.lang.String midDescription ) {
this.midDescription = midDescription;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<RoleTypeIn3Levels> objectList = new ArrayList<RoleTypeIn3Levels>();
        for (GenericValue genVal : genList) {
            objectList.add(new RoleTypeIn3Levels(genVal));
        }
        return objectList;
    }    
}
