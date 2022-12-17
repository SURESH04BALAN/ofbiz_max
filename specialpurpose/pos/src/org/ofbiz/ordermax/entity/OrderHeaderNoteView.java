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
public class OrderHeaderNoteView implements GenericValueObjectInterface {
public static final String module =OrderHeaderNoteView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public OrderHeaderNoteView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public OrderHeaderNoteView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"noteName","Note Name"},
{"noteDateTime","Note Date Time"},
{"noteInfo","Note Info"},
{"noteParty","Note Party"},
{"noteId","Note Id"},
{"orderId","Order Id"},
{"internalNote","Internal Note"},
};
protected void initObject(){
this.noteName = "";
this.noteDateTime = UtilDateTime.nowTimestamp();
this.noteInfo = "";
this.noteParty = "";
this.noteId = "";
this.orderId = "";
this.internalNote = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.noteName = (java.lang.String) genVal.get("noteName");
this.noteDateTime = (java.sql.Timestamp) genVal.get("noteDateTime");
this.noteInfo = (java.lang.String) genVal.get("noteInfo");
this.noteParty = (java.lang.String) genVal.get("noteParty");
this.noteId = (java.lang.String) genVal.get("noteId");
this.orderId = (java.lang.String) genVal.get("orderId");
this.internalNote = (java.lang.String) genVal.get("internalNote");
}
protected void getGenericValue(GenericValue val) {
val.set("noteName",OrderMaxUtility.getValidEntityString(this.noteName));
val.set("noteDateTime",OrderMaxUtility.getValidEntityTimestamp(this.noteDateTime));
val.set("noteInfo",OrderMaxUtility.getValidEntityString(this.noteInfo));
val.set("noteParty",OrderMaxUtility.getValidEntityString(this.noteParty));
val.set("noteId",OrderMaxUtility.getValidEntityString(this.noteId));
val.set("orderId",OrderMaxUtility.getValidEntityString(this.orderId));
val.set("internalNote",OrderMaxUtility.getValidEntityString(this.internalNote));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("noteName",this.noteName);
valueMap.put("noteDateTime",this.noteDateTime);
valueMap.put("noteInfo",this.noteInfo);
valueMap.put("noteParty",this.noteParty);
valueMap.put("noteId",this.noteId);
valueMap.put("orderId",this.orderId);
valueMap.put("internalNote",this.internalNote);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("OrderHeaderNoteView");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String noteName;
public java.lang.String getnoteName() {
return noteName;
}
public void setnoteName( java.lang.String noteName ) {
this.noteName = noteName;
}
private java.sql.Timestamp noteDateTime;
public java.sql.Timestamp getnoteDateTime() {
return noteDateTime;
}
public void setnoteDateTime( java.sql.Timestamp noteDateTime ) {
this.noteDateTime = noteDateTime;
}
private java.lang.String noteInfo;
public java.lang.String getnoteInfo() {
return noteInfo;
}
public void setnoteInfo( java.lang.String noteInfo ) {
this.noteInfo = noteInfo;
}
private java.lang.String noteParty;
public java.lang.String getnoteParty() {
return noteParty;
}
public void setnoteParty( java.lang.String noteParty ) {
this.noteParty = noteParty;
}
private java.lang.String noteId;
public java.lang.String getnoteId() {
return noteId;
}
public void setnoteId( java.lang.String noteId ) {
this.noteId = noteId;
}
private java.lang.String orderId;
public java.lang.String getorderId() {
return orderId;
}
public void setorderId( java.lang.String orderId ) {
this.orderId = orderId;
}
private java.lang.String internalNote;
public java.lang.String getinternalNote() {
return internalNote;
}
public void setinternalNote( java.lang.String internalNote ) {
this.internalNote = internalNote;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<OrderHeaderNoteView> objectList = new ArrayList<OrderHeaderNoteView>();
        for (GenericValue genVal : genList) {
            objectList.add(new OrderHeaderNoteView(genVal));
        }
        return objectList;
    }    
}
