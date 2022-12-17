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
public class GlJournal implements GenericValueObjectInterface {
public static final String module =GlJournal.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GlJournal(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GlJournal () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"organizationPartyId","Organization Party Id"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"glJournalId","Gl Journal Id"},
{"postedDate","Posted Date"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"glJournalName","Gl Journal Name"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"isPosted","Is Posted"},
};
protected void initObject(){
this.organizationPartyId = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.glJournalId = "";
this.postedDate = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.glJournalName = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.isPosted = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.organizationPartyId = (java.lang.String) genVal.get("organizationPartyId");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.glJournalId = (java.lang.String) genVal.get("glJournalId");
this.postedDate = (java.sql.Timestamp) genVal.get("postedDate");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.glJournalName = (java.lang.String) genVal.get("glJournalName");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.isPosted = (java.lang.String) genVal.get("isPosted");
}
protected void getGenericValue(GenericValue val) {
val.set("organizationPartyId",OrderMaxUtility.getValidEntityString(this.organizationPartyId));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("glJournalId",OrderMaxUtility.getValidEntityString(this.glJournalId));
val.set("postedDate",OrderMaxUtility.getValidEntityTimestamp(this.postedDate));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("glJournalName",OrderMaxUtility.getValidEntityString(this.glJournalName));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("isPosted",OrderMaxUtility.getValidEntityString(this.isPosted));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("organizationPartyId",this.organizationPartyId);
valueMap.put("glJournalId",this.glJournalId);
valueMap.put("postedDate",this.postedDate);
valueMap.put("glJournalName",this.glJournalName);
valueMap.put("isPosted",this.isPosted);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GlJournal");
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
private java.lang.String glJournalId;
public java.lang.String getglJournalId() {
return glJournalId;
}
public void setglJournalId( java.lang.String glJournalId ) {
this.glJournalId = glJournalId;
}
private java.sql.Timestamp postedDate;
public java.sql.Timestamp getpostedDate() {
return postedDate;
}
public void setpostedDate( java.sql.Timestamp postedDate ) {
this.postedDate = postedDate;
}
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String glJournalName;
public java.lang.String getglJournalName() {
return glJournalName;
}
public void setglJournalName( java.lang.String glJournalName ) {
this.glJournalName = glJournalName;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String isPosted;
public java.lang.String getisPosted() {
return isPosted;
}
public void setisPosted( java.lang.String isPosted ) {
this.isPosted = isPosted;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GlJournal> objectList = new ArrayList<GlJournal>();
        for (GenericValue genVal : genList) {
            objectList.add(new GlJournal(genVal));
        }
        return objectList;
    }    
}
