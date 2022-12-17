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
public class GlAccountAndHistory implements GenericValueObjectInterface {
public static final String module =GlAccountAndHistory.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GlAccountAndHistory(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GlAccountAndHistory () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"glAccountClassId","Gl Account Class Id"},
{"organizationPartyId","Organization Party Id"},
{"glAccountTypeId","Gl Account Type Id"},
{"glResourceTypeId","Gl Resource Type Id"},
{"postedCredits","Posted Credits"},
{"glXbrlClassId","Gl Xbrl Class Id"},
{"endingBalance","Ending Balance"},
{"externalId","External Id"},
{"productId","Product Id"},
{"accountCode","Account Code"},
{"accountName","Account Name"},
{"parentGlAccountId","Parent Gl Account Id"},
{"postedDebits","Posted Debits"},
{"glAccountId","Gl Account Id"},
{"description","Description"},
{"postedBalance","Posted Balance"},
{"customTimePeriodId","Custom Time Period Id"},
};
protected void initObject(){
this.glAccountClassId = "";
this.organizationPartyId = "";
this.glAccountTypeId = "";
this.glResourceTypeId = "";
this.postedCredits = java.math.BigDecimal.ZERO;
this.glXbrlClassId = "";
this.endingBalance = "";
this.externalId = "";
this.productId = "";
this.accountCode = "";
this.accountName = "";
this.parentGlAccountId = "";
this.postedDebits = java.math.BigDecimal.ZERO;
this.glAccountId = "";
this.description = "";
this.postedBalance = java.math.BigDecimal.ZERO;
this.customTimePeriodId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.glAccountClassId = (java.lang.String) genVal.get("glAccountClassId");
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.glAccountTypeId = (java.lang.String) genVal.get("glAccountTypeId");
this.glResourceTypeId = (java.lang.String) genVal.get("glResourceTypeId");
this.postedCredits = (java.math.BigDecimal) genVal.get("postedCredits");
this.glXbrlClassId = (java.lang.String) genVal.get("glXbrlClassId");
this.endingBalance = (java.lang.String) genVal.get("endingBalance");
this.externalId = (java.lang.String) genVal.get("externalId");
this.productId = (java.lang.String) genVal.get("productId");
this.accountCode = (java.lang.String) genVal.get("accountCode");
this.accountName = (java.lang.String) genVal.get("accountName");
this.parentGlAccountId = (java.lang.String) genVal.get("parentGlAccountId");
this.postedDebits = (java.math.BigDecimal) genVal.get("postedDebits");
this.glAccountId = (java.lang.String) genVal.get("glAccountId");
this.description = (java.lang.String) genVal.get("description");
this.postedBalance = (java.math.BigDecimal) genVal.get("postedBalance");
this.customTimePeriodId = (java.lang.String) genVal.get("customTimePeriodId");
}
protected void getGenericValue(GenericValue val) {
val.set("glAccountClassId",OrderMaxUtility.getValidEntityString(this.glAccountClassId));
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("glAccountTypeId",OrderMaxUtility.getValidEntityString(this.glAccountTypeId));
val.set("glResourceTypeId",OrderMaxUtility.getValidEntityString(this.glResourceTypeId));
val.set("postedCredits",OrderMaxUtility.getValidEntityBigDecimal(this.postedCredits));
val.set("glXbrlClassId",OrderMaxUtility.getValidEntityString(this.glXbrlClassId));
val.set("endingBalance",OrderMaxUtility.getValidEntityString(this.endingBalance));
val.set("externalId",OrderMaxUtility.getValidEntityString(this.externalId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("accountCode",OrderMaxUtility.getValidEntityString(this.accountCode));
val.set("accountName",OrderMaxUtility.getValidEntityString(this.accountName));
val.set("parentGlAccountId",OrderMaxUtility.getValidEntityString(this.parentGlAccountId));
val.set("postedDebits",OrderMaxUtility.getValidEntityBigDecimal(this.postedDebits));
val.set("glAccountId",OrderMaxUtility.getValidEntityString(this.glAccountId));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("postedBalance",OrderMaxUtility.getValidEntityBigDecimal(this.postedBalance));
val.set("customTimePeriodId",OrderMaxUtility.getValidEntityString(this.customTimePeriodId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("glAccountClassId",this.glAccountClassId);
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("glAccountTypeId",this.glAccountTypeId);
valueMap.put("glResourceTypeId",this.glResourceTypeId);
valueMap.put("postedCredits",this.postedCredits);
valueMap.put("glXbrlClassId",this.glXbrlClassId);
valueMap.put("endingBalance",this.endingBalance);
valueMap.put("externalId",this.externalId);
valueMap.put("productId",this.productId);
valueMap.put("accountCode",this.accountCode);
valueMap.put("accountName",this.accountName);
valueMap.put("parentGlAccountId",this.parentGlAccountId);
valueMap.put("postedDebits",this.postedDebits);
valueMap.put("glAccountId",this.glAccountId);
valueMap.put("description",this.description);
valueMap.put("postedBalance",this.postedBalance);
valueMap.put("customTimePeriodId",this.customTimePeriodId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GlAccountAndHistory");
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
private java.lang.String glResourceTypeId;
public java.lang.String getglResourceTypeId() {
return glResourceTypeId;
}
public void setglResourceTypeId( java.lang.String glResourceTypeId ) {
this.glResourceTypeId = glResourceTypeId;
}
private java.math.BigDecimal postedCredits;
public java.math.BigDecimal getpostedCredits() {
return postedCredits;
}
public void setpostedCredits( java.math.BigDecimal postedCredits ) {
this.postedCredits = postedCredits;
}
private java.lang.String glXbrlClassId;
public java.lang.String getglXbrlClassId() {
return glXbrlClassId;
}
public void setglXbrlClassId( java.lang.String glXbrlClassId ) {
this.glXbrlClassId = glXbrlClassId;
}
private java.lang.String endingBalance;
public java.lang.String getendingBalance() {
return endingBalance;
}
public void setendingBalance( java.lang.String endingBalance ) {
this.endingBalance = endingBalance;
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
private java.lang.String accountCode;
public java.lang.String getaccountCode() {
return accountCode;
}
public void setaccountCode( java.lang.String accountCode ) {
this.accountCode = accountCode;
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
private java.math.BigDecimal postedDebits;
public java.math.BigDecimal getpostedDebits() {
return postedDebits;
}
public void setpostedDebits( java.math.BigDecimal postedDebits ) {
this.postedDebits = postedDebits;
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
private java.math.BigDecimal postedBalance;
public java.math.BigDecimal getpostedBalance() {
return postedBalance;
}
public void setpostedBalance( java.math.BigDecimal postedBalance ) {
this.postedBalance = postedBalance;
}
private java.lang.String customTimePeriodId;
public java.lang.String getcustomTimePeriodId() {
return customTimePeriodId;
}
public void setcustomTimePeriodId( java.lang.String customTimePeriodId ) {
this.customTimePeriodId = customTimePeriodId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GlAccountAndHistory> objectList = new ArrayList<GlAccountAndHistory>();
        for (GenericValue genVal : genList) {
            objectList.add(new GlAccountAndHistory(genVal));
        }
        return objectList;
    }    
}
