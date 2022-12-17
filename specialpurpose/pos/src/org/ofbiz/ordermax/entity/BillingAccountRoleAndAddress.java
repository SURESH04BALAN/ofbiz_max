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
public class BillingAccountRoleAndAddress implements GenericValueObjectInterface {
public static final String module =BillingAccountRoleAndAddress.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public BillingAccountRoleAndAddress(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
//Debug.logInfo(ex, module);
}
}
public BillingAccountRoleAndAddress () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"pcmFromDate","Pcm From Date"},
{"pcmThruDate","Pcm Thru Date"},
{"billingAccountId","Billing Account Id"},
{"partyId","Party Id"},
{"roleTypeId","Role Type Id"},
{"fromDate","From Date"},
{"thruDate","Thru Date"},
{"contactMechId","Contact Mech Id"},
{"toName","To Name"},
{"attnName","Attn Name"},
{"address1","Address 1"},
{"address2","Address 2"},
{"directions","Directions"},
{"city","City"},
{"postalCode","Postal Code"},
{"postalCodeExt","Postal Code Ext"},
{"countryGeoId","Country Geo Id"},
{"stateProvinceGeoId","State Province Geo Id"},
{"countyGeoId","County Geo Id"},
{"postalCodeGeoId","Postal Code Geo Id"},
{"geoPointId","Geo Point Id"},
};
protected void initObject(){
this.pcmFromDate = UtilDateTime.nowTimestamp();
this.pcmThruDate = UtilDateTime.nowTimestamp();
this.billingAccountId = "";
this.partyId = "";
this.roleTypeId = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.thruDate = UtilDateTime.nowTimestamp();
this.contactMechId = "";
this.toName = "";
this.attnName = "";
this.address1 = "";
this.address2 = "";
this.directions = "";
this.city = "";
this.postalCode = "";
this.postalCodeExt = "";
this.countryGeoId = "";
this.stateProvinceGeoId = "";
this.countyGeoId = "";
this.postalCodeGeoId = "";
this.geoPointId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.pcmFromDate = (java.sql.Timestamp) genVal.get("pcmFromDate");
this.pcmThruDate = (java.sql.Timestamp) genVal.get("pcmThruDate");
this.billingAccountId = (java.lang.String) genVal.get("billingAccountId");
this.partyId = (java.lang.String) genVal.get("partyId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.toName = (java.lang.String) genVal.get("toName");
this.attnName = (java.lang.String) genVal.get("attnName");
this.address1 = (java.lang.String) genVal.get("address1");
this.address2 = (java.lang.String) genVal.get("address2");
this.directions = (java.lang.String) genVal.get("directions");
this.city = (java.lang.String) genVal.get("city");
this.postalCode = (java.lang.String) genVal.get("postalCode");
this.postalCodeExt = (java.lang.String) genVal.get("postalCodeExt");
this.countryGeoId = (java.lang.String) genVal.get("countryGeoId");
this.stateProvinceGeoId = (java.lang.String) genVal.get("stateProvinceGeoId");
this.countyGeoId = (java.lang.String) genVal.get("countyGeoId");
this.postalCodeGeoId = (java.lang.String) genVal.get("postalCodeGeoId");
this.geoPointId = (java.lang.String) genVal.get("geoPointId");
}
protected void getGenericValue(GenericValue val) {
val.set("pcmFromDate",OrderMaxUtility.getValidEntityTimestamp(this.pcmFromDate));
val.set("pcmThruDate",OrderMaxUtility.getValidEntityTimestamp(this.pcmThruDate));
val.set("billingAccountId",OrderMaxUtility.getValidEntityString(this.billingAccountId));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("toName",OrderMaxUtility.getValidEntityString(this.toName));
val.set("attnName",OrderMaxUtility.getValidEntityString(this.attnName));
val.set("address1",OrderMaxUtility.getValidEntityString(this.address1));
val.set("address2",OrderMaxUtility.getValidEntityString(this.address2));
val.set("directions",OrderMaxUtility.getValidEntityString(this.directions));
val.set("city",OrderMaxUtility.getValidEntityString(this.city));
val.set("postalCode",OrderMaxUtility.getValidEntityString(this.postalCode));
val.set("postalCodeExt",OrderMaxUtility.getValidEntityString(this.postalCodeExt));
val.set("countryGeoId",OrderMaxUtility.getValidEntityString(this.countryGeoId));
val.set("stateProvinceGeoId",OrderMaxUtility.getValidEntityString(this.stateProvinceGeoId));
val.set("countyGeoId",OrderMaxUtility.getValidEntityString(this.countyGeoId));
val.set("postalCodeGeoId",OrderMaxUtility.getValidEntityString(this.postalCodeGeoId));
val.set("geoPointId",OrderMaxUtility.getValidEntityString(this.geoPointId));
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("BillingAccountRoleAndAddress");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp pcmFromDate;
public java.sql.Timestamp getpcmFromDate() {
return pcmFromDate;
}
public void setpcmFromDate( java.sql.Timestamp pcmFromDate ) {
this.pcmFromDate = pcmFromDate;
}
private java.sql.Timestamp pcmThruDate;
public java.sql.Timestamp getpcmThruDate() {
return pcmThruDate;
}
public void setpcmThruDate( java.sql.Timestamp pcmThruDate ) {
this.pcmThruDate = pcmThruDate;
}
private java.lang.String billingAccountId;
public java.lang.String getbillingAccountId() {
return billingAccountId;
}
public void setbillingAccountId( java.lang.String billingAccountId ) {
this.billingAccountId = billingAccountId;
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
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String toName;
public java.lang.String gettoName() {
return toName;
}
public void settoName( java.lang.String toName ) {
this.toName = toName;
}
private java.lang.String attnName;
public java.lang.String getattnName() {
return attnName;
}
public void setattnName( java.lang.String attnName ) {
this.attnName = attnName;
}
private java.lang.String address1;
public java.lang.String getaddress1() {
return address1;
}
public void setaddress1( java.lang.String address1 ) {
this.address1 = address1;
}
private java.lang.String address2;
public java.lang.String getaddress2() {
return address2;
}
public void setaddress2( java.lang.String address2 ) {
this.address2 = address2;
}
private java.lang.String directions;
public java.lang.String getdirections() {
return directions;
}
public void setdirections( java.lang.String directions ) {
this.directions = directions;
}
private java.lang.String city;
public java.lang.String getcity() {
return city;
}
public void setcity( java.lang.String city ) {
this.city = city;
}
private java.lang.String postalCode;
public java.lang.String getpostalCode() {
return postalCode;
}
public void setpostalCode( java.lang.String postalCode ) {
this.postalCode = postalCode;
}
private java.lang.String postalCodeExt;
public java.lang.String getpostalCodeExt() {
return postalCodeExt;
}
public void setpostalCodeExt( java.lang.String postalCodeExt ) {
this.postalCodeExt = postalCodeExt;
}
private java.lang.String countryGeoId;
public java.lang.String getcountryGeoId() {
return countryGeoId;
}
public void setcountryGeoId( java.lang.String countryGeoId ) {
this.countryGeoId = countryGeoId;
}
private java.lang.String stateProvinceGeoId;
public java.lang.String getstateProvinceGeoId() {
return stateProvinceGeoId;
}
public void setstateProvinceGeoId( java.lang.String stateProvinceGeoId ) {
this.stateProvinceGeoId = stateProvinceGeoId;
}
private java.lang.String countyGeoId;
public java.lang.String getcountyGeoId() {
return countyGeoId;
}
public void setcountyGeoId( java.lang.String countyGeoId ) {
this.countyGeoId = countyGeoId;
}
private java.lang.String postalCodeGeoId;
public java.lang.String getpostalCodeGeoId() {
return postalCodeGeoId;
}
public void setpostalCodeGeoId( java.lang.String postalCodeGeoId ) {
this.postalCodeGeoId = postalCodeGeoId;
}
private java.lang.String geoPointId;
public java.lang.String getgeoPointId() {
return geoPointId;
}
public void setgeoPointId( java.lang.String geoPointId ) {
this.geoPointId = geoPointId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
//@Override
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<BillingAccountRoleAndAddress> objectList = new ArrayList<BillingAccountRoleAndAddress>();
        for (GenericValue genVal : genList) {
            objectList.add(new BillingAccountRoleAndAddress(genVal));
        }
        return objectList;
    }    
    
    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
