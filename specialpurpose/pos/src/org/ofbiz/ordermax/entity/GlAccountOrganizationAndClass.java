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
public class GlAccountOrganizationAndClass implements GenericValueObjectInterface {
public static final String module =GlAccountOrganizationAndClass.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GlAccountOrganizationAndClass(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GlAccountOrganizationAndClass () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"glAccountClassId","Gl Account Class Id"},
{"organizationPartyId","Organization Party Id"},
{"glAccountTypeId","Gl Account Type Id"},
{"thruDate","Thru Date"},
{"glResourceTypeId","Gl Resource Type Id"},
{"glXbrlClassId","Gl Xbrl Class Id"},
{"externalId","External Id"},
{"productId","Product Id"},
{"accountPostedBalance","Account Posted Balance"},
{"accountCode","Account Code"},
{"roleTypeId","Role Type Id"},
{"accountName","Account Name"},
{"parentGlAccountId","Parent Gl Account Id"},
{"fromDate","From Date"},
{"glAccountId","Gl Account Id"},
{"description","Description"},
{"postedBalance","Posted Balance"},
};
protected void initObject(){
this.glAccountClassId = "";
this.organizationPartyId = "";
this.glAccountTypeId = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.glResourceTypeId = "";
this.glXbrlClassId = "";
this.externalId = "";
this.productId = "";
this.accountPostedBalance = java.math.BigDecimal.ZERO;
this.accountCode = "";
this.roleTypeId = "";
this.accountName = "";
this.parentGlAccountId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.glAccountId = "";
this.description = "";
this.postedBalance = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.glAccountClassId = (java.lang.String) genVal.get("glAccountClassId");
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.glAccountTypeId = (java.lang.String) genVal.get("glAccountTypeId");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.glResourceTypeId = (java.lang.String) genVal.get("glResourceTypeId");
this.glXbrlClassId = (java.lang.String) genVal.get("glXbrlClassId");
this.externalId = (java.lang.String) genVal.get("externalId");
this.productId = (java.lang.String) genVal.get("productId");
this.accountPostedBalance = (java.math.BigDecimal) genVal.get("accountPostedBalance");
this.accountCode = (java.lang.String) genVal.get("accountCode");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.accountName = (java.lang.String) genVal.get("accountName");
this.parentGlAccountId = (java.lang.String) genVal.get("parentGlAccountId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.glAccountId = (java.lang.String) genVal.get("glAccountId");
this.description = (java.lang.String) genVal.get("description");
this.postedBalance = (java.lang.String) genVal.get("postedBalance");
}
protected void getGenericValue(GenericValue val) {
val.set("glAccountClassId",OrderMaxUtility.getValidEntityString(this.glAccountClassId));
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("glAccountTypeId",OrderMaxUtility.getValidEntityString(this.glAccountTypeId));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("glResourceTypeId",OrderMaxUtility.getValidEntityString(this.glResourceTypeId));
val.set("glXbrlClassId",OrderMaxUtility.getValidEntityString(this.glXbrlClassId));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("accountPostedBalance",OrderMaxUtility.getValidEntityBigDecimal(this.accountPostedBalance));
val.set("accountCode",OrderMaxUtility.getValidEntityString(this.accountCode));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("accountName",OrderMaxUtility.getValidEntityString(this.accountName));
val.set("parentGlAccountId",OrderMaxUtility.getValidEntityString(this.parentGlAccountId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("glAccountId",OrderMaxUtility.getValidEntityString(this.glAccountId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("postedBalance",OrderMaxUtility.getValidEntityString(this.postedBalance));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("glAccountClassId",this.glAccountClassId);
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("glAccountTypeId",this.glAccountTypeId);
valueMap.put("thruDate",this.thruDate);
valueMap.put("glResourceTypeId",this.glResourceTypeId);
valueMap.put("glXbrlClassId",this.glXbrlClassId);
valueMap.put("externalId",this.externalId);
valueMap.put("productId",this.productId);
valueMap.put("accountPostedBalance",this.accountPostedBalance);
valueMap.put("accountCode",this.accountCode);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("accountName",this.accountName);
valueMap.put("parentGlAccountId",this.parentGlAccountId);
valueMap.put("fromDate",this.fromDate);
valueMap.put("glAccountId",this.glAccountId);
valueMap.put("description",this.description);
valueMap.put("postedBalance",this.postedBalance);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GlAccountOrganizationAndClass");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String glAccountClassId;
public java.lang.String getglAccountClassId() {
return glAccountClassId;
}
public void setglAccountClassId( java.lang.String glAccountClassId ) {
this.glAccountClassId = glAccountClassId;
}
private java.lang.String organizationPartyId;
public java.lang.String getorganizationPartyId() {
return organizationPartyId;
}
public void setorganizationPartyId( java.lang.String organizationPartyId ) {
this.organizationPartyId = organizationPartyId;
}
private java.lang.String glAccountTypeId;
public java.lang.String getglAccountTypeId() {
return glAccountTypeId;
}
public void setglAccountTypeId( java.lang.String glAccountTypeId ) {
this.glAccountTypeId = glAccountTypeId;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String glResourceTypeId;
public java.lang.String getglResourceTypeId() {
return glResourceTypeId;
}
public void setglResourceTypeId( java.lang.String glResourceTypeId ) {
this.glResourceTypeId = glResourceTypeId;
}
private java.lang.String glXbrlClassId;
public java.lang.String getglXbrlClassId() {
return glXbrlClassId;
}
public void setglXbrlClassId( java.lang.String glXbrlClassId ) {
this.glXbrlClassId = glXbrlClassId;
}
private java.lang.String externalId;
public java.lang.String getexternalId() {
return externalId;
}
public void setexternalId( java.lang.String externalId ) {
this.externalId = externalId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.math.BigDecimal accountPostedBalance;
public java.math.BigDecimal getaccountPostedBalance() {
return accountPostedBalance;
}
public void setaccountPostedBalance( java.math.BigDecimal accountPostedBalance ) {
this.accountPostedBalance = accountPostedBalance;
}
private java.lang.String accountCode;
public java.lang.String getaccountCode() {
return accountCode;
}
public void setaccountCode( java.lang.String accountCode ) {
this.accountCode = accountCode;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String accountName;
public java.lang.String getaccountName() {
return accountName;
}
public void setaccountName( java.lang.String accountName ) {
this.accountName = accountName;
}
private java.lang.String parentGlAccountId;
public java.lang.String getparentGlAccountId() {
return parentGlAccountId;
}
public void setparentGlAccountId( java.lang.String parentGlAccountId ) {
this.parentGlAccountId = parentGlAccountId;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String glAccountId;
public java.lang.String getglAccountId() {
return glAccountId;
}
public void setglAccountId( java.lang.String glAccountId ) {
this.glAccountId = glAccountId;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String postedBalance;
public java.lang.String getpostedBalance() {
return postedBalance;
}
public void setpostedBalance( java.lang.String postedBalance ) {
this.postedBalance = postedBalance;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GlAccountOrganizationAndClass> objectList = new ArrayList<GlAccountOrganizationAndClass>();
        for (GenericValue genVal : genList) {
            objectList.add(new GlAccountOrganizationAndClass(genVal));
        }
        return objectList;
    }    
}
