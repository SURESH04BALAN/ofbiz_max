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
public class WebSite implements GenericValueObjectInterface {
public static final String module =WebSite.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public WebSite(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public WebSite () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"lastUpdatedStamp","Last Updated Stamp"},
{"productStoreId","Product Store Id"},
{"allowProductStoreChange","Allow Product Store Change"},
{"httpsPort","Https Port"},
{"httpPort","Http Port"},
{"visualThemeSetId","Visual Theme Set Id"},
{"httpsHost","Https Host"},
{"createdTxStamp","Created Tx Stamp"},
{"createdStamp","Created Stamp"},
{"secureContentPrefix","Secure Content Prefix"},
{"cookieDomain","Cookie Domain"},
{"enableHttps","Enable Https"},
{"httpHost","Http Host"},
{"webSiteId","Web Site Id"},
{"lastUpdatedTxStamp","Last Updated Tx Stamp"},
{"standardContentPrefix","Standard Content Prefix"},
{"siteName","Site Name"},
};
protected void initObject(){
this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
this.productStoreId = "";
this.allowProductStoreChange = "";
this.httpsPort = "";
this.httpPort = "";
this.visualThemeSetId = "";
this.httpsHost = "";
this.createdTxStamp = UtilDateTime.nowTimestamp();
this.createdStamp = UtilDateTime.nowTimestamp();
this.secureContentPrefix = "";
this.cookieDomain = "";
this.enableHttps = "";
this.httpHost = "";
this.webSiteId = "";
this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
this.standardContentPrefix = "";
this.siteName = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.allowProductStoreChange = (java.lang.String) genVal.get("allowProductStoreChange");
this.httpsPort = (java.lang.String) genVal.get("httpsPort");
this.httpPort = (java.lang.String) genVal.get("httpPort");
this.visualThemeSetId = (java.lang.String) genVal.get("visualThemeSetId");
this.httpsHost = (java.lang.String) genVal.get("httpsHost");
this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
this.secureContentPrefix = (java.lang.String) genVal.get("secureContentPrefix");
this.cookieDomain = (java.lang.String) genVal.get("cookieDomain");
this.enableHttps = (java.lang.String) genVal.get("enableHttps");
this.httpHost = (java.lang.String) genVal.get("httpHost");
this.webSiteId = (java.lang.String) genVal.get("webSiteId");
this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
this.standardContentPrefix = (java.lang.String) genVal.get("standardContentPrefix");
this.siteName = (java.lang.String) genVal.get("siteName");
}
protected void getGenericValue(GenericValue val) {
val.set("lastUpdatedStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("allowProductStoreChange",OrderMaxUtility.getValidEntityString(this.allowProductStoreChange));
val.set("httpsPort",OrderMaxUtility.getValidEntityString(this.httpsPort));
val.set("httpPort",OrderMaxUtility.getValidEntityString(this.httpPort));
val.set("visualThemeSetId",OrderMaxUtility.getValidEntityString(this.visualThemeSetId));
val.set("httpsHost",OrderMaxUtility.getValidEntityString(this.httpsHost));
val.set("createdTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
val.set("createdStamp",OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
val.set("secureContentPrefix",OrderMaxUtility.getValidEntityString(this.secureContentPrefix));
val.set("cookieDomain",OrderMaxUtility.getValidEntityString(this.cookieDomain));
val.set("enableHttps",OrderMaxUtility.getValidEntityString(this.enableHttps));
val.set("httpHost",OrderMaxUtility.getValidEntityString(this.httpHost));
val.set("webSiteId",OrderMaxUtility.getValidEntityString(this.webSiteId));
val.set("lastUpdatedTxStamp",OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
val.set("standardContentPrefix",OrderMaxUtility.getValidEntityString(this.standardContentPrefix));
val.set("siteName",OrderMaxUtility.getValidEntityString(this.siteName));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("allowProductStoreChange",this.allowProductStoreChange);
valueMap.put("httpsPort",this.httpsPort);
valueMap.put("httpPort",this.httpPort);
valueMap.put("visualThemeSetId",this.visualThemeSetId);
valueMap.put("httpsHost",this.httpsHost);
valueMap.put("secureContentPrefix",this.secureContentPrefix);
valueMap.put("cookieDomain",this.cookieDomain);
valueMap.put("enableHttps",this.enableHttps);
valueMap.put("httpHost",this.httpHost);
valueMap.put("webSiteId",this.webSiteId);
valueMap.put("standardContentPrefix",this.standardContentPrefix);
valueMap.put("siteName",this.siteName);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("WebSite");
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
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String allowProductStoreChange;
public java.lang.String getallowProductStoreChange() {
return allowProductStoreChange;
}
public void setallowProductStoreChange( java.lang.String allowProductStoreChange ) {
this.allowProductStoreChange = allowProductStoreChange;
}
private java.lang.String httpsPort;
public java.lang.String gethttpsPort() {
return httpsPort;
}
public void sethttpsPort( java.lang.String httpsPort ) {
this.httpsPort = httpsPort;
}
private java.lang.String httpPort;
public java.lang.String gethttpPort() {
return httpPort;
}
public void sethttpPort( java.lang.String httpPort ) {
this.httpPort = httpPort;
}
private java.lang.String visualThemeSetId;
public java.lang.String getvisualThemeSetId() {
return visualThemeSetId;
}
public void setvisualThemeSetId( java.lang.String visualThemeSetId ) {
this.visualThemeSetId = visualThemeSetId;
}
private java.lang.String httpsHost;
public java.lang.String gethttpsHost() {
return httpsHost;
}
public void sethttpsHost( java.lang.String httpsHost ) {
this.httpsHost = httpsHost;
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
private java.lang.String secureContentPrefix;
public java.lang.String getsecureContentPrefix() {
return secureContentPrefix;
}
public void setsecureContentPrefix( java.lang.String secureContentPrefix ) {
this.secureContentPrefix = secureContentPrefix;
}
private java.lang.String cookieDomain;
public java.lang.String getcookieDomain() {
return cookieDomain;
}
public void setcookieDomain( java.lang.String cookieDomain ) {
this.cookieDomain = cookieDomain;
}
private java.lang.String enableHttps;
public java.lang.String getenableHttps() {
return enableHttps;
}
public void setenableHttps( java.lang.String enableHttps ) {
this.enableHttps = enableHttps;
}
private java.lang.String httpHost;
public java.lang.String gethttpHost() {
return httpHost;
}
public void sethttpHost( java.lang.String httpHost ) {
this.httpHost = httpHost;
}
private java.lang.String webSiteId;
public java.lang.String getwebSiteId() {
return webSiteId;
}
public void setwebSiteId( java.lang.String webSiteId ) {
this.webSiteId = webSiteId;
}
private java.sql.Timestamp lastUpdatedTxStamp;
public java.sql.Timestamp getlastUpdatedTxStamp() {
return lastUpdatedTxStamp;
}
public void setlastUpdatedTxStamp( java.sql.Timestamp lastUpdatedTxStamp ) {
this.lastUpdatedTxStamp = lastUpdatedTxStamp;
}
private java.lang.String standardContentPrefix;
public java.lang.String getstandardContentPrefix() {
return standardContentPrefix;
}
public void setstandardContentPrefix( java.lang.String standardContentPrefix ) {
this.standardContentPrefix = standardContentPrefix;
}
private java.lang.String siteName;
public java.lang.String getsiteName() {
return siteName;
}
public void setsiteName( java.lang.String siteName ) {
this.siteName = siteName;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<WebSite> objectList = new ArrayList<WebSite>();
        for (GenericValue genVal : genList) {
            objectList.add(new WebSite(genVal));
        }
        return objectList;
    }    
}
