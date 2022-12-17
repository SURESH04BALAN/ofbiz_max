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
public class CountryTeleCodeAndName implements GenericValueObjectInterface {
public static final String module =CountryTeleCodeAndName.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public CountryTeleCodeAndName(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public CountryTeleCodeAndName () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"teleCode","Tele Code"},
{"countryName","Country Name"},
{"countryCode","Country Code"},
};
protected void initObject(){
this.teleCode = "";
this.countryName = "";
this.countryCode = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.teleCode = (java.lang.String) genVal.get("teleCode");
this.countryName = (java.lang.String) genVal.get("countryName");
this.countryCode = (java.lang.String) genVal.get("countryCode");
}
protected void getGenericValue(GenericValue val) {
val.set("teleCode",OrderMaxUtility.getValidEntityString(this.teleCode));
val.set("countryName",OrderMaxUtility.getValidEntityString(this.countryName));
val.set("countryCode",OrderMaxUtility.getValidEntityString(this.countryCode));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("teleCode",this.teleCode);
valueMap.put("countryName",this.countryName);
valueMap.put("countryCode",this.countryCode);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("CountryTeleCodeAndName");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String teleCode;
public java.lang.String getteleCode() {
return teleCode;
}
public void setteleCode( java.lang.String teleCode ) {
this.teleCode = teleCode;
}
private java.lang.String countryName;
public java.lang.String getcountryName() {
return countryName;
}
public void setcountryName( java.lang.String countryName ) {
this.countryName = countryName;
}
private java.lang.String countryCode;
public java.lang.String getcountryCode() {
return countryCode;
}
public void setcountryCode( java.lang.String countryCode ) {
this.countryCode = countryCode;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<CountryTeleCodeAndName> objectList = new ArrayList<CountryTeleCodeAndName>();
        for (GenericValue genVal : genList) {
            objectList.add(new CountryTeleCodeAndName(genVal));
        }
        return objectList;
    }    
}
