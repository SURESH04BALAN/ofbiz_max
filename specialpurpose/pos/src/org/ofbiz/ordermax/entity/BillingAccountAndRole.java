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
import java.util.Map;
public class BillingAccountAndRole implements GenericValueObjectInterface {
public static final String module =BillingAccountAndRole.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public BillingAccountAndRole(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
//Debug.logInfo(ex, module);
}
}
public BillingAccountAndRole () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"billingAccountId","Billing Account Id"},
{"accountLimit","Account Limit"},
{"accountCurrencyUomId","Account Currency Uom Id"},
{"contactMechId","Contact Mech Id"},
{"accountFromDate","Account From Date"},
{"accountThruDate","Account Thru Date"},
{"description","Description"},
{"partyId","Party Id"},
{"roleTypeId","Role Type Id"},
{"fromDate","From Date"},
{"thruDate","Thru Date"},
};
protected void initObject(){
this.billingAccountId = "";
this.accountLimit = "";
this.accountCurrencyUomId = "";
this.contactMechId = "";
this.accountFromDate = UtilDateTime.nowTimestamp();
this.accountThruDate = UtilDateTime.nowTimestamp();
this.description = "";
this.partyId = "";
this.roleTypeId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.thruDate = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
this.accountLimit = (java.lang.String) genVal.get("accountLimit");
this.accountCurrencyUomId = (java.lang.String) genVal.get("accountCurrencyUomId");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.accountFromDate = (java.sql.Timestamp) genVal.get("accountFromDate");
this.accountThruDate = (java.sql.Timestamp) genVal.get("accountThruDate");
this.description = (java.lang.String) genVal.get("description");
this.partyId = (java.lang.String) genVal.get("partyId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
}
protected void getGenericValue(GenericValue val) {
val.set("billingAccountId",OrderMaxUtility.getValidEntityString(this.billingAccountId));
val.set("accountLimit",OrderMaxUtility.getValidEntityString(this.accountLimit));
val.set("accountCurrencyUomId",OrderMaxUtility.getValidEntityString(this.accountCurrencyUomId));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("accountFromDate",OrderMaxUtility.getValidEntityTimestamp(this.accountFromDate));
val.set("accountThruDate",OrderMaxUtility.getValidEntityTimestamp(this.accountThruDate));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("BillingAccountAndRole");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String billingAccountId;
public java.lang.String getbillingAccountId() {
return billingAccountId;
}
public void setbillingAccountId( java.lang.String billingAccountId ) {
this.billingAccountId = billingAccountId;
}
private java.lang.String accountLimit;
public java.lang.String getaccountLimit() {
return accountLimit;
}
public void setaccountLimit( java.lang.String accountLimit ) {
this.accountLimit = accountLimit;
}
private java.lang.String accountCurrencyUomId;
public java.lang.String getaccountCurrencyUomId() {
return accountCurrencyUomId;
}
public void setaccountCurrencyUomId( java.lang.String accountCurrencyUomId ) {
this.accountCurrencyUomId = accountCurrencyUomId;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.sql.Timestamp accountFromDate;
public java.sql.Timestamp getaccountFromDate() {
return accountFromDate;
}
public void setaccountFromDate( java.sql.Timestamp accountFromDate ) {
this.accountFromDate = accountFromDate;
}
private java.sql.Timestamp accountThruDate;
public java.sql.Timestamp getaccountThruDate() {
return accountThruDate;
}
public void setaccountThruDate( java.sql.Timestamp accountThruDate ) {
this.accountThruDate = accountThruDate;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
//@Override
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<BillingAccountAndRole> objectList = new ArrayList<BillingAccountAndRole>();
        for (GenericValue genVal : genList) {
            objectList.add(new BillingAccountAndRole(genVal));
        }
        return objectList;
    }    
    
    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
