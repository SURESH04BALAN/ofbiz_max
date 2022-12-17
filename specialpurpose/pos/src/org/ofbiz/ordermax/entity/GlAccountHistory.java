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
public class GlAccountHistory implements GenericValueObjectInterface {
public static final String module =GlAccountHistory.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GlAccountHistory(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GlAccountHistory () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"organizationPartyId","Organization Party Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"postedDebits","Posted Debits"},
{"createdTxStamp","Created Tx Stamp"},
{"glAccountId","Gl Account Id"},
{"postedCredits","Posted Credits"},
{"createdStamp","Created Stamp"},
{"customTimePeriodId","Custom Time Period Id"},
{"endingBalance","Ending Balance"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.organizationPartyId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.postedDebits = java.math.BigDecimal.ZERO;
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.glAccountId = "";
this.postedCredits = java.math.BigDecimal.ZERO;
this.createdStamp = UtilDateTime.nowTimestamp();
this.customTimePeriodId = "";
this.endingBalance = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.postedDebits = (java.math.BigDecimal) genVal.get("postedDebits");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.glAccountId = (java.lang.String) genVal.get("glAccountId");
this.postedCredits = (java.math.BigDecimal) genVal.get("postedCredits");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.customTimePeriodId = (java.lang.String) genVal.get("customTimePeriodId");
this.endingBalance = (java.lang.String) genVal.get("endingBalance");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("postedDebits",OrderMaxUtility.getValidEntityBigDecimal(this.postedDebits));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("glAccountId",OrderMaxUtility.getValidEntityString(this.glAccountId));
val.set("postedCredits",OrderMaxUtility.getValidEntityBigDecimal(this.postedCredits));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("customTimePeriodId",OrderMaxUtility.getValidEntityString(this.customTimePeriodId));
val.set("endingBalance",OrderMaxUtility.getValidEntityString(this.endingBalance));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("postedDebits",this.postedDebits);
valueMap.put("glAccountId",this.glAccountId);
valueMap.put("postedCredits",this.postedCredits);
valueMap.put("customTimePeriodId",this.customTimePeriodId);
valueMap.put("endingBalance",this.endingBalance);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GlAccountHistory");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String organizationPartyId;
public java.lang.String getorganizationPartyId() {
return organizationPartyId;
}
public void setorganizationPartyId( java.lang.String organizationPartyId ) {
this.organizationPartyId = organizationPartyId;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.math.BigDecimal postedDebits;
public java.math.BigDecimal getpostedDebits() {
return postedDebits;
}
public void setpostedDebits( java.math.BigDecimal postedDebits ) {
this.postedDebits = postedDebits;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String glAccountId;
public java.lang.String getglAccountId() {
return glAccountId;
}
public void setglAccountId( java.lang.String glAccountId ) {
this.glAccountId = glAccountId;
}
private java.math.BigDecimal postedCredits;
public java.math.BigDecimal getpostedCredits() {
return postedCredits;
}
public void setpostedCredits( java.math.BigDecimal postedCredits ) {
this.postedCredits = postedCredits;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String customTimePeriodId;
public java.lang.String getcustomTimePeriodId() {
return customTimePeriodId;
}
public void setcustomTimePeriodId( java.lang.String customTimePeriodId ) {
this.customTimePeriodId = customTimePeriodId;
}
private java.lang.String endingBalance;
public java.lang.String getendingBalance() {
return endingBalance;
}
public void setendingBalance( java.lang.String endingBalance ) {
this.endingBalance = endingBalance;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GlAccountHistory> objectList = new ArrayList<GlAccountHistory>();
        for (GenericValue genVal : genList) {
            objectList.add(new GlAccountHistory(genVal));
        }
        return objectList;
    }    
}
