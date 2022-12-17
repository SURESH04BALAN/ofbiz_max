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
public class InvoiceTerm implements GenericValueObjectInterface {
public static final String module =InvoiceTerm.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public InvoiceTerm(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public InvoiceTerm () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"termDays","Term Days"},
{"termValue","Term Value"},
{"invoiceId","Invoice Id"},
{"invoiceTermId","Invoice Term Id"},
{"invoiceItemSeqId","Invoice Item Seq Id"},
{"uomId","Uom Id"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"description","Description"},
{"termTypeId","Term Type Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"textValue","Text Value"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.termDays = new Long(0);
this.termValue = "";
this.invoiceId = "";
this.invoiceTermId = "";
this.invoiceItemSeqId = "";
this.uomId = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.description = "";
this.termTypeId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.textValue = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.termDays = (java.lang.Long) genVal.get("termDays");
this.termValue = (java.lang.String) genVal.get("termValue");
this.invoiceId = (java.lang.String) genVal.get("invoiceId");
this.invoiceTermId = (java.lang.String) genVal.get("invoiceTermId");
this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
this.uomId = (java.lang.String) genVal.get("uomId");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.description = (java.lang.String) genVal.get("description");
this.termTypeId = (java.lang.String) genVal.get("termTypeId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.textValue = (java.lang.String) genVal.get("textValue");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("termDays",this.termDays);
val.set("termValue",OrderMaxUtility.getValidEntityString(this.termValue));
val.set("invoiceId",OrderMaxUtility.getValidEntityString(this.invoiceId));
val.set("invoiceTermId",OrderMaxUtility.getValidEntityString(this.invoiceTermId));
val.set("invoiceItemSeqId",OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
val.set("uomId",OrderMaxUtility.getValidEntityString(this.uomId));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("termTypeId",OrderMaxUtility.getValidEntityString(this.termTypeId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("textValue",OrderMaxUtility.getValidEntityString(this.textValue));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("termDays",this.termDays);
valueMap.put("termValue",this.termValue);
valueMap.put("invoiceId",this.invoiceId);
valueMap.put("invoiceTermId",this.invoiceTermId);
valueMap.put("invoiceItemSeqId",this.invoiceItemSeqId);
valueMap.put("uomId",this.uomId);
valueMap.put("description",this.description);
valueMap.put("termTypeId",this.termTypeId);
valueMap.put("textValue",this.textValue);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("InvoiceTerm");
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
private java.lang.Long termDays;
public java.lang.Long gettermDays() {
return termDays;
}
public void settermDays( java.lang.Long termDays ) {
this.termDays = termDays;
}
private java.lang.String termValue;
public java.lang.String gettermValue() {
return termValue;
}
public void settermValue( java.lang.String termValue ) {
this.termValue = termValue;
}
private java.lang.String invoiceId;
public java.lang.String getinvoiceId() {
return invoiceId;
}
public void setinvoiceId( java.lang.String invoiceId ) {
this.invoiceId = invoiceId;
}
private java.lang.String invoiceTermId;
public java.lang.String getinvoiceTermId() {
return invoiceTermId;
}
public void setinvoiceTermId( java.lang.String invoiceTermId ) {
this.invoiceTermId = invoiceTermId;
}
private java.lang.String invoiceItemSeqId;
public java.lang.String getinvoiceItemSeqId() {
return invoiceItemSeqId;
}
public void setinvoiceItemSeqId( java.lang.String invoiceItemSeqId ) {
this.invoiceItemSeqId = invoiceItemSeqId;
}
private java.lang.String uomId;
public java.lang.String getuomId() {
return uomId;
}
public void setuomId( java.lang.String uomId ) {
this.uomId = uomId;
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
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String termTypeId;
public java.lang.String gettermTypeId() {
return termTypeId;
}
public void settermTypeId( java.lang.String termTypeId ) {
this.termTypeId = termTypeId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String textValue;
public java.lang.String gettextValue() {
return textValue;
}
public void settextValue( java.lang.String textValue ) {
this.textValue = textValue;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InvoiceTerm> objectList = new ArrayList<InvoiceTerm>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceTerm(genVal));
        }
        return objectList;
    }    
}
