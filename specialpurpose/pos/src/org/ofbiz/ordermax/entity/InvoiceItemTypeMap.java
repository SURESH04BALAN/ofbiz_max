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
public class InvoiceItemTypeMap implements GenericValueObjectInterface {
public static final String module =InvoiceItemTypeMap.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InvoiceItemTypeMap(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InvoiceItemTypeMap () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"invoiceTypeId","Invoice Type Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"invoiceItemMapKey","Invoice Item Map Key"},
{"invoiceItemTypeId","Invoice Item Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.invoiceTypeId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.invoiceItemMapKey = "";
this.invoiceItemTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.invoiceTypeId = (java.lang.String) genVal.get("invoiceTypeId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.invoiceItemMapKey = (java.lang.String) genVal.get("invoiceItemMapKey");
this.invoiceItemTypeId = (java.lang.String) genVal.get("invoiceItemTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("invoiceTypeId",OrderMaxUtility.getValidEntityString(this.invoiceTypeId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("invoiceItemMapKey",OrderMaxUtility.getValidEntityString(this.invoiceItemMapKey));
val.set("invoiceItemTypeId",OrderMaxUtility.getValidEntityString(this.invoiceItemTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("invoiceTypeId",this.invoiceTypeId);
valueMap.put("invoiceItemMapKey",this.invoiceItemMapKey);
valueMap.put("invoiceItemTypeId",this.invoiceItemTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InvoiceItemTypeMap");
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
private java.lang.String invoiceTypeId;
public java.lang.String getinvoiceTypeId() {
return invoiceTypeId;
}
public void setinvoiceTypeId( java.lang.String invoiceTypeId ) {
this.invoiceTypeId = invoiceTypeId;
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
private java.lang.String invoiceItemMapKey;
public java.lang.String getinvoiceItemMapKey() {
return invoiceItemMapKey;
}
public void setinvoiceItemMapKey( java.lang.String invoiceItemMapKey ) {
this.invoiceItemMapKey = invoiceItemMapKey;
}
private java.lang.String invoiceItemTypeId;
public java.lang.String getinvoiceItemTypeId() {
return invoiceItemTypeId;
}
public void setinvoiceItemTypeId( java.lang.String invoiceItemTypeId ) {
this.invoiceItemTypeId = invoiceItemTypeId;
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
        Collection<InvoiceItemTypeMap> objectList = new ArrayList<InvoiceItemTypeMap>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceItemTypeMap(genVal));
        }
        return objectList;
    }    
}
