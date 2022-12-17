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
public class RequirementPartyProductCount implements GenericValueObjectInterface {
public static final String module =RequirementPartyProductCount.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public RequirementPartyProductCount(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public RequirementPartyProductCount () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"statusId","Status Id"},
{"roleTypeId","Role Type Id"},
{"thruDate","Thru Date"},
{"fromDate","From Date"},
{"partyId","Party Id"},
{"requirementTypeId","Requirement Type Id"},
{"productId","Product Id"},
};
protected void initObject(){
this.statusId = "";
this.roleTypeId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.fromDate = UtilDateTime.nowTimestamp();
this.partyId = "";
this.requirementTypeId = "";
this.productId = new Long(0);
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.statusId = (java.lang.String) genVal.get("statusId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.partyId = (java.lang.String) genVal.get("partyId");
this.requirementTypeId = (java.lang.String) genVal.get("requirementTypeId");
this.productId = (java.lang.Long) genVal.get("productId");
}
protected void getGenericValue(GenericValue val) {
val.set("statusId",OrderMaxUtility.getValidEntityString(this.statusId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("requirementTypeId",OrderMaxUtility.getValidEntityString(this.requirementTypeId));
val.set("productId",this.productId);
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("statusId",this.statusId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("fromDate",this.fromDate);
valueMap.put("partyId",this.partyId);
valueMap.put("requirementTypeId",this.requirementTypeId);
valueMap.put("productId",this.productId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("RequirementPartyProductCount");
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
public void setstatusId( java.lang.String statusId ) {
this.statusId = statusId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String requirementTypeId;
public java.lang.String getrequirementTypeId() {
return requirementTypeId;
}
public void setrequirementTypeId( java.lang.String requirementTypeId ) {
this.requirementTypeId = requirementTypeId;
}
private java.lang.Long productId;
public java.lang.Long getproductId() {
return productId;
}
public void setproductId( java.lang.Long productId ) {
this.productId = productId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<RequirementPartyProductCount> objectList = new ArrayList<RequirementPartyProductCount>();
        for (GenericValue genVal : genList) {
            objectList.add(new RequirementPartyProductCount(genVal));
        }
        return objectList;
    }    
}
