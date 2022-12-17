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
public class StandardLanguage implements GenericValueObjectInterface {
public static final String module =StandardLanguage.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public StandardLanguage(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public StandardLanguage () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"langCode3b","Lang Code 3B"},
{"lastUpdatedStamp","Last Updated Stamp"},
{"standardLanguageId","Standard Language Id"},
{"langFamily","Lang Family"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"langName","Lang Name"},
{"langCode3t","Lang Code 3T"},
{"langCharset","Lang Charset"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"langCode2","Lang Code 2"},
};
protected void initObject(){
this.langCode3b = "";
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.standardLanguageId = "";
this.langFamily = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.langName = "";
this.langCode3t = "";
this.langCharset = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.langCode2 = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.langCode3b = (java.lang.String) genVal.get("langCode3b");
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.standardLanguageId = (java.lang.String) genVal.get("standardLanguageId");
this.langFamily = (java.lang.String) genVal.get("langFamily");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.langName = (java.lang.String) genVal.get("langName");
this.langCode3t = (java.lang.String) genVal.get("langCode3t");
this.langCharset = (java.lang.String) genVal.get("langCharset");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.langCode2 = (java.lang.String) genVal.get("langCode2");
}
protected void getGenericValue(GenericValue val) {
val.set("langCode3b",OrderMaxUtility.getValidEntityString(this.langCode3b));
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("standardLanguageId",OrderMaxUtility.getValidEntityString(this.standardLanguageId));
val.set("langFamily",OrderMaxUtility.getValidEntityString(this.langFamily));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("langName",OrderMaxUtility.getValidEntityString(this.langName));
val.set("langCode3t",OrderMaxUtility.getValidEntityString(this.langCode3t));
val.set("langCharset",OrderMaxUtility.getValidEntityString(this.langCharset));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("langCode2",OrderMaxUtility.getValidEntityString(this.langCode2));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("langCode3b",this.langCode3b);
valueMap.put("standardLanguageId",this.standardLanguageId);
valueMap.put("langFamily",this.langFamily);
valueMap.put("langName",this.langName);
valueMap.put("langCode3t",this.langCode3t);
valueMap.put("langCharset",this.langCharset);
valueMap.put("langCode2",this.langCode2);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("StandardLanguage");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String langCode3b;
public java.lang.String getlangCode3b() {
return langCode3b;
}
public void setlangCode3b( java.lang.String langCode3b ) {
this.langCode3b = langCode3b;
}
private java.sql.Timestamp lastUpdatedStamp;
public java.sql.Timestamp getlastUpdatedStamp() {
return lastUpdatedStamp;
}
public void setlastUpdatedStamp( java.sql.Timestamp lastUpdatedStamp ) {
this.lastUpdatedStamp = lastUpdatedStamp;
}
private java.lang.String standardLanguageId;
public java.lang.String getstandardLanguageId() {
return standardLanguageId;
}
public void setstandardLanguageId( java.lang.String standardLanguageId ) {
this.standardLanguageId = standardLanguageId;
}
private java.lang.String langFamily;
public java.lang.String getlangFamily() {
return langFamily;
}
public void setlangFamily( java.lang.String langFamily ) {
this.langFamily = langFamily;
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
private java.lang.String langName;
public java.lang.String getlangName() {
return langName;
}
public void setlangName( java.lang.String langName ) {
this.langName = langName;
}
private java.lang.String langCode3t;
public java.lang.String getlangCode3t() {
return langCode3t;
}
public void setlangCode3t( java.lang.String langCode3t ) {
this.langCode3t = langCode3t;
}
private java.lang.String langCharset;
public java.lang.String getlangCharset() {
return langCharset;
}
public void setlangCharset( java.lang.String langCharset ) {
this.langCharset = langCharset;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String langCode2;
public java.lang.String getlangCode2() {
return langCode2;
}
public void setlangCode2( java.lang.String langCode2 ) {
this.langCode2 = langCode2;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<StandardLanguage> objectList = new ArrayList<StandardLanguage>();
        for (GenericValue genVal : genList) {
            objectList.add(new StandardLanguage(genVal));
        }
        return objectList;
    }    
}
