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
public class SequenceValueItem implements GenericValueObjectInterface {
public static final String module =SequenceValueItem.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public SequenceValueItem(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public SequenceValueItem () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"seqId","Seq Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"seqName","Seq Name"},
};
protected void initObject(){
this.lastUpdatedStamp = "";
this.createdTxStamp = "";
this.createdStamp = "";
this.seqId = new Long(0);
this.lastUpdatedTxStamp = "";
this.seqName = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.lang.String) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.lang.String) genVal.get("createdTxStamp");
this.createdStamp = (java.lang.String) genVal.get("createdStamp");
this.seqId = (java.lang.Long) genVal.get("seqId");
this.lastUpdatedTxStamp = (java.lang.String) genVal.get("lastUpdatedTxStamp");
this.seqName = (java.lang.String) genVal.get("seqName");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityString(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityString(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityString(this.createdStamp));
val.set("seqId",this.seqId);
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityString(this.lastUpdatedTxStamp));
val.set("seqName",OrderMaxUtility.getValidEntityString(this.seqName));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("seqId",this.seqId);
valueMap.put("seqName",this.seqName);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("SequenceValueItem");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String lastUpdatedStamp;
public java.lang.String getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.lang.String lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String createdTxStamp;
public java.lang.String getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.lang.String createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String createdStamp;
public java.lang.String getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.lang.String createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.Long seqId;
public java.lang.Long getseqId() {
return seqId;
}
public void setseqId( java.lang.Long seqId ) {
this.seqId = seqId;
}
private java.lang.String lastUpdatedTxStamp;
public java.lang.String getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.lang.String lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String seqName;
public java.lang.String getseqName() {
return seqName;
}
public void setseqName( java.lang.String seqName ) {
this.seqName = seqName;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<SequenceValueItem> objectList = new ArrayList<SequenceValueItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new SequenceValueItem(genVal));
        }
        return objectList;
    }    
}
