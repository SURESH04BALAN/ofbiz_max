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
public class JobInterviewType implements GenericValueObjectInterface {
public static final String module =JobInterviewType.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public JobInterviewType(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public JobInterviewType () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"jobInterviewTypeId","Job Interview Type Id"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.jobInterviewTypeId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.jobInterviewTypeId = (java.lang.String) genVal.get("jobInterviewTypeId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("jobInterviewTypeId",OrderMaxUtility.getValidEntityString(this.jobInterviewTypeId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("jobInterviewTypeId",this.jobInterviewTypeId);
valueMap.put("description",this.description);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("JobInterviewType");
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
private java.sql.Timestamp createdTxStamp;
public java.sql.Timestamp getcreatedTxStamp() {
return createdTxStamp;
}
public void setcreatedTxStamp( java.sql.Timestamp createdTxStamp ) {
this.createdTxStamp = createdTxStamp;
}
private java.lang.String jobInterviewTypeId;
public java.lang.String getjobInterviewTypeId() {
return jobInterviewTypeId;
}
public void setjobInterviewTypeId( java.lang.String jobInterviewTypeId ) {
this.jobInterviewTypeId = jobInterviewTypeId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
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
        Collection<JobInterviewType> objectList = new ArrayList<JobInterviewType>();
        for (GenericValue genVal : genList) {
            objectList.add(new JobInterviewType(genVal));
        }
        return objectList;
    }    
}
