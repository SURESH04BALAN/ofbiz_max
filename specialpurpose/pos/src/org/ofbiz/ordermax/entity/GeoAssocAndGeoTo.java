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
public class GeoAssocAndGeoTo implements GenericValueObjectInterface {
public static final String module =GeoAssocAndGeoTo.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public GeoAssocAndGeoTo(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public GeoAssocAndGeoTo () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"geoId","Geo Id"},
{"wellKnownText","Well Known Text"},
{"geoIdFrom","Geo Id From"},
{"geoAssocTypeId","Geo Assoc Type Id"},
{"geoName","Geo Name"},
{"geoSecCode","Geo Sec Code"},
{"abbreviation","Abbreviation"},
{"geoTypeId","Geo Type Id"},
{"geoCode","Geo Code"},
};
protected void initObject(){
this.geoId = "";
this.wellKnownText = "";
this.geoIdFrom = "";
this.geoAssocTypeId = "";
this.geoName = "";
this.geoSecCode = "";
this.abbreviation = "";
this.geoTypeId = "";
this.geoCode = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.geoId = (java.lang.String) genVal.get("geoId");
this.wellKnownText = (java.lang.String) genVal.get("wellKnownText");
this.geoIdFrom = (java.lang.String) genVal.get("geoIdFrom");
this.geoAssocTypeId = (java.lang.String) genVal.get("geoAssocTypeId");
this.geoName = (java.lang.String) genVal.get("geoName");
this.geoSecCode = (java.lang.String) genVal.get("geoSecCode");
this.abbreviation = (java.lang.String) genVal.get("abbreviation");
this.geoTypeId = (java.lang.String) genVal.get("geoTypeId");
this.geoCode = (java.lang.String) genVal.get("geoCode");
}
protected void getGenericValue(GenericValue val) {
val.set("geoId",OrderMaxUtility.getValidEntityString(this.geoId));
val.set("wellKnownText",OrderMaxUtility.getValidEntityString(this.wellKnownText));
val.set("geoIdFrom",OrderMaxUtility.getValidEntityString(this.geoIdFrom));
val.set("geoAssocTypeId",OrderMaxUtility.getValidEntityString(this.geoAssocTypeId));
val.set("geoName",OrderMaxUtility.getValidEntityString(this.geoName));
val.set("geoSecCode",OrderMaxUtility.getValidEntityString(this.geoSecCode));
val.set("abbreviation",OrderMaxUtility.getValidEntityString(this.abbreviation));
val.set("geoTypeId",OrderMaxUtility.getValidEntityString(this.geoTypeId));
val.set("geoCode",OrderMaxUtility.getValidEntityString(this.geoCode));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("geoId",this.geoId);
valueMap.put("wellKnownText",this.wellKnownText);
valueMap.put("geoIdFrom",this.geoIdFrom);
valueMap.put("geoAssocTypeId",this.geoAssocTypeId);
valueMap.put("geoName",this.geoName);
valueMap.put("geoSecCode",this.geoSecCode);
valueMap.put("abbreviation",this.abbreviation);
valueMap.put("geoTypeId",this.geoTypeId);
valueMap.put("geoCode",this.geoCode);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("GeoAssocAndGeoTo");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String geoId;
public java.lang.String getgeoId() {
return geoId;
}
public void setgeoId( java.lang.String geoId ) {
this.geoId = geoId;
}
private java.lang.String wellKnownText;
public java.lang.String getwellKnownText() {
return wellKnownText;
}
public void setwellKnownText( java.lang.String wellKnownText ) {
this.wellKnownText = wellKnownText;
}
private java.lang.String geoIdFrom;
public java.lang.String getgeoIdFrom() {
return geoIdFrom;
}
public void setgeoIdFrom( java.lang.String geoIdFrom ) {
this.geoIdFrom = geoIdFrom;
}
private java.lang.String geoAssocTypeId;
public java.lang.String getgeoAssocTypeId() {
return geoAssocTypeId;
}
public void setgeoAssocTypeId( java.lang.String geoAssocTypeId ) {
this.geoAssocTypeId = geoAssocTypeId;
}
private java.lang.String geoName;
public java.lang.String getgeoName() {
return geoName;
}
public void setgeoName( java.lang.String geoName ) {
this.geoName = geoName;
}
private java.lang.String geoSecCode;
public java.lang.String getgeoSecCode() {
return geoSecCode;
}
public void setgeoSecCode( java.lang.String geoSecCode ) {
this.geoSecCode = geoSecCode;
}
private java.lang.String abbreviation;
public java.lang.String getabbreviation() {
return abbreviation;
}
public void setabbreviation( java.lang.String abbreviation ) {
this.abbreviation = abbreviation;
}
private java.lang.String geoTypeId;
public java.lang.String getgeoTypeId() {
return geoTypeId;
}
public void setgeoTypeId( java.lang.String geoTypeId ) {
this.geoTypeId = geoTypeId;
}
private java.lang.String geoCode;
public java.lang.String getgeoCode() {
return geoCode;
}
public void setgeoCode( java.lang.String geoCode ) {
this.geoCode = geoCode;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GeoAssocAndGeoTo> objectList = new ArrayList<GeoAssocAndGeoTo>();
        for (GenericValue genVal : genList) {
            objectList.add(new GeoAssocAndGeoTo(genVal));
        }
        return objectList;
    }    
}
