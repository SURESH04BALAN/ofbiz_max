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
public class NoteData implements GenericValueObjectInterface {
public static final String module =NoteData.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public NoteData(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public NoteData () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"moreInfoItemName","More Info Item Name"},
{"noteDateTime","Note Date Time"},
{"noteId","Note Id"},
{"noteName","Note Name"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"noteInfo","Note Info"},
{"moreInfoUrl","More Info Url"},
{"noteParty","Note Party"},
{"moreInfoItemId","More Info Item Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.moreInfoItemName = "";
this.noteDateTime = UtilDateTime.nowTimestamp();
this.noteId = "";
this.noteName = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.noteInfo = "";
this.moreInfoUrl = "";
this.noteParty = "";
this.moreInfoItemId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.moreInfoItemName = (java.lang.String) genVal.get("moreInfoItemName");
this.noteDateTime = (java.sql.Timestamp) genVal.get("noteDateTime");
this.noteId = (java.lang.String) genVal.get("noteId");
this.noteName = (java.lang.String) genVal.get("noteName");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.noteInfo = (java.lang.String) genVal.get("noteInfo");
this.moreInfoUrl = (java.lang.String) genVal.get("moreInfoUrl");
this.noteParty = (java.lang.String) genVal.get("noteParty");
this.moreInfoItemId = (java.lang.String) genVal.get("moreInfoItemId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("moreInfoItemName",OrderMaxUtility.getValidEntityString(this.moreInfoItemName));
val.set("noteDateTime",OrderMaxUtility.getValidEntityTimestamp(this.noteDateTime));
val.set("noteId",OrderMaxUtility.getValidEntityString(this.noteId));
val.set("noteName",OrderMaxUtility.getValidEntityString(this.noteName));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("noteInfo",OrderMaxUtility.getValidEntityString(this.noteInfo));
val.set("moreInfoUrl",OrderMaxUtility.getValidEntityString(this.moreInfoUrl));
val.set("noteParty",OrderMaxUtility.getValidEntityString(this.noteParty));
val.set("moreInfoItemId",OrderMaxUtility.getValidEntityString(this.moreInfoItemId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("moreInfoItemName",this.moreInfoItemName);
valueMap.put("noteDateTime",this.noteDateTime);
valueMap.put("noteId",this.noteId);
valueMap.put("noteName",this.noteName);
valueMap.put("noteInfo",this.noteInfo);
valueMap.put("moreInfoUrl",this.moreInfoUrl);
valueMap.put("noteParty",this.noteParty);
valueMap.put("moreInfoItemId",this.moreInfoItemId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("NoteData");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String moreInfoItemName;
public java.lang.String getmoreInfoItemName() {
return moreInfoItemName;
}
public void setmoreInfoItemName( java.lang.String moreInfoItemName ) {
this.moreInfoItemName = moreInfoItemName;
}
private java.sql.Timestamp noteDateTime;
public java.sql.Timestamp getnoteDateTime() {
return noteDateTime;
}
public void setnoteDateTime( java.sql.Timestamp noteDateTime ) {
this.noteDateTime = noteDateTime;
}
private java.lang.String noteId;
public java.lang.String getnoteId() {
return noteId;
}
public void setnoteId( java.lang.String noteId ) {
this.noteId = noteId;
}
private java.lang.String noteName;
public java.lang.String getnoteName() {
return noteName;
}
public void setnoteName( java.lang.String noteName ) {
this.noteName = noteName;
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
private java.lang.String noteInfo;
public java.lang.String getnoteInfo() {
return noteInfo;
}
public void setnoteInfo( java.lang.String noteInfo ) {
this.noteInfo = noteInfo;
}
private java.lang.String moreInfoUrl;
public java.lang.String getmoreInfoUrl() {
return moreInfoUrl;
}
public void setmoreInfoUrl( java.lang.String moreInfoUrl ) {
this.moreInfoUrl = moreInfoUrl;
}
private java.lang.String noteParty;
public java.lang.String getnoteParty() {
return noteParty;
}
public void setnoteParty( java.lang.String noteParty ) {
this.noteParty = noteParty;
}
private java.lang.String moreInfoItemId;
public java.lang.String getmoreInfoItemId() {
return moreInfoItemId;
}
public void setmoreInfoItemId( java.lang.String moreInfoItemId ) {
this.moreInfoItemId = moreInfoItemId;
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
        Collection<NoteData> objectList = new ArrayList<NoteData>();
        for (GenericValue genVal : genList) {
            objectList.add(new NoteData(genVal));
        }
        return objectList;
    }    
}
