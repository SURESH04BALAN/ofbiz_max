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
public class RoleTypeAndParty implements GenericValueObjectInterface {
public static final String module =RoleTypeAndParty.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public RoleTypeAndParty(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public RoleTypeAndParty () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"roleTypeId","Role Type Id"},
{"partyId","Party Id"},
{"parentTypeId","Parent Type Id"},
{"description","Description"},
};
protected void initObject(){
this.roleTypeId = "";
this.partyId = "";
this.parentTypeId = "";
this.description = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.parentTypeId = (java.lang.String) genVal.get("parentTypeId");
this.description = (java.lang.String) genVal.get("description");
}
protected void getGenericValue(GenericValue val) {
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("parentTypeId",OrderMaxUtility.getValidEntityString(this.parentTypeId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("partyId",this.partyId);
valueMap.put("parentTypeId",this.parentTypeId);
valueMap.put("description",this.description);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("RoleTypeAndParty");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String parentTypeId;
public java.lang.String getparentTypeId() {
return parentTypeId;
}
public void setparentTypeId( java.lang.String parentTypeId ) {
this.parentTypeId = parentTypeId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<RoleTypeAndParty> objectList = new ArrayList<RoleTypeAndParty>();
        for (GenericValue genVal : genList) {
            objectList.add(new RoleTypeAndParty(genVal));
        }
        return objectList;
    }    
}
