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
public class InvoiceContactMech implements GenericValueObjectInterface {
public static final String module =InvoiceContactMech.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InvoiceContactMech(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InvoiceContactMech () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"createdTxStamp","Created Tx Stamp"},
{"invoiceId","Invoice Id"},
{"createdStamp","Created Stamp"},
{"contactMechId","Contact Mech Id"},
{"contactMechPurposeTypeId","Contact Mech Purpose Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.invoiceId = "";
this.createdStamp = UtilDateTime.nowTimestamp();
this.contactMechId = "";
this.contactMechPurposeTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.invoiceId = (java.lang.String) genVal.get("invoiceId");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.contactMechId = (java.lang.String) genVal.get("contactMechId");
this.contactMechPurposeTypeId = (java.lang.String) genVal.get("contactMechPurposeTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("invoiceId",OrderMaxUtility.getValidEntityString(this.invoiceId));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("contactMechId",OrderMaxUtility.getValidEntityString(this.contactMechId));
val.set("contactMechPurposeTypeId",OrderMaxUtility.getValidEntityString(this.contactMechPurposeTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("invoiceId",this.invoiceId);
valueMap.put("contactMechId",this.contactMechId);
valueMap.put("contactMechPurposeTypeId",this.contactMechPurposeTypeId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InvoiceContactMech");
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
private java.lang.String invoiceId;
public java.lang.String getinvoiceId() {
return invoiceId;
}
public void setinvoiceId( java.lang.String invoiceId ) {
this.invoiceId = invoiceId;
}
private java.sql.Timestamp createdStamp;
public java.sql.Timestamp getcreatedStamp() {
return createdStamp;
}
public void setcreatedStamp( java.sql.Timestamp createdStamp ) {
this.createdStamp = createdStamp;
}
private java.lang.String contactMechId;
public java.lang.String getcontactMechId() {
return contactMechId;
}
public void setcontactMechId( java.lang.String contactMechId ) {
this.contactMechId = contactMechId;
}
private java.lang.String contactMechPurposeTypeId;
public java.lang.String getcontactMechPurposeTypeId() {
return contactMechPurposeTypeId;
}
public void setcontactMechPurposeTypeId( java.lang.String contactMechPurposeTypeId ) {
this.contactMechPurposeTypeId = contactMechPurposeTypeId;
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
        Collection<InvoiceContactMech> objectList = new ArrayList<InvoiceContactMech>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceContactMech(genVal));
        }
        return objectList;
    }    
}
