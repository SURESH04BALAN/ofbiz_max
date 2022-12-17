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
public class ProductStoreShipmentMethView implements GenericValueObjectInterface {
public static final String module =ProductStoreShipmentMethView.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductStoreShipmentMethView(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductStoreShipmentMethView () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"maxTotal","Max Total"},
{"productStoreId","Product Store Id"},
{"excludeGeoId","Exclude Geo Id"},
{"shipmentCustomMethodId","Shipment Custom Method Id"},
{"shipmentGatewayConfigId","Shipment Gateway Config Id"},
{"excludeFeatureGroup","Exclude Feature Group"},
{"sequenceNumber","Sequence Number"},
{"serviceName","Service Name"},
{"maxWeight","Max Weight"},
{"includeNoChargeItems","Include No Charge Items"},
{"description","Description"},
{"requireUspsAddr","Require Usps Addr"},
{"includeGeoId","Include Geo Id"},
{"requireCompanyAddr","Require Company Addr"},
{"maxSize","Max Size"},
{"allowUspsAddr","Allow Usps Addr"},
{"configProps","Config Props"},
{"productStoreShipMethId","Product Store Ship Meth Id"},
{"companyPartyId","Company Party Id"},
{"roleTypeId","Role Type Id"},
{"minSize","Min Size"},
{"partyId","Party Id"},
{"minWeight","Min Weight"},
{"allowCompanyAddr","Allow Company Addr"},
{"shipmentMethodTypeId","Shipment Method Type Id"},
{"minTotal","Min Total"},
{"includeFeatureGroup","Include Feature Group"},
};
protected void initObject(){
this.maxTotal = "";
this.productStoreId = "";
this.excludeGeoId = "";
this.shipmentCustomMethodId = "";
this.shipmentGatewayConfigId = "";
this.excludeFeatureGroup = "";
this.sequenceNumber = new Long(0);
this.serviceName = "";
this.maxWeight = "";
this.includeNoChargeItems = "";
this.description = "";
this.requireUspsAddr = "";
this.includeGeoId = "";
this.requireCompanyAddr = "";
this.maxSize = "";
this.allowUspsAddr = "";
this.configProps = "";
this.productStoreShipMethId = "";
this.companyPartyId = "";
this.roleTypeId = "";
this.minSize = "";
this.partyId = "";
this.minWeight = "";
this.allowCompanyAddr = "";
this.shipmentMethodTypeId = "";
this.minTotal = "";
this.includeFeatureGroup = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.maxTotal = (java.lang.String) genVal.get("maxTotal");
this.productStoreId = (java.lang.String) genVal.get("productStoreId");
this.excludeGeoId = (java.lang.String) genVal.get("excludeGeoId");
this.shipmentCustomMethodId = (java.lang.String) genVal.get("shipmentCustomMethodId");
this.shipmentGatewayConfigId = (java.lang.String) genVal.get("shipmentGatewayConfigId");
this.excludeFeatureGroup = (java.lang.String) genVal.get("excludeFeatureGroup");
this.sequenceNumber = (java.lang.Long) genVal.get("sequenceNumber");
this.serviceName = (java.lang.String) genVal.get("serviceName");
this.maxWeight = (java.lang.String) genVal.get("maxWeight");
this.includeNoChargeItems = (java.lang.String) genVal.get("includeNoChargeItems");
this.description = (java.lang.String) genVal.get("description");
this.requireUspsAddr = (java.lang.String) genVal.get("requireUspsAddr");
this.includeGeoId = (java.lang.String) genVal.get("includeGeoId");
this.requireCompanyAddr = (java.lang.String) genVal.get("requireCompanyAddr");
this.maxSize = (java.lang.String) genVal.get("maxSize");
this.allowUspsAddr = (java.lang.String) genVal.get("allowUspsAddr");
this.configProps = (java.lang.String) genVal.get("configProps");
this.productStoreShipMethId = (java.lang.String) genVal.get("productStoreShipMethId");
this.companyPartyId = (java.lang.String) genVal.get("companyPartyId");
this.roleTypeId = (java.lang.String) genVal.get("roleTypeId");
this.minSize = (java.lang.String) genVal.get("minSize");
this.partyId = (java.lang.String) genVal.get("partyId");
this.minWeight = (java.lang.String) genVal.get("minWeight");
this.allowCompanyAddr = (java.lang.String) genVal.get("allowCompanyAddr");
this.shipmentMethodTypeId = (java.lang.String) genVal.get("shipmentMethodTypeId");
this.minTotal = (java.lang.String) genVal.get("minTotal");
this.includeFeatureGroup = (java.lang.String) genVal.get("includeFeatureGroup");
}
protected void getGenericValue(GenericValue val) {
val.set("maxTotal",OrderMaxUtility.getValidEntityString(this.maxTotal));
val.set("productStoreId",OrderMaxUtility.getValidEntityString(this.productStoreId));
val.set("excludeGeoId",OrderMaxUtility.getValidEntityString(this.excludeGeoId));
val.set("shipmentCustomMethodId",OrderMaxUtility.getValidEntityString(this.shipmentCustomMethodId));
val.set("shipmentGatewayConfigId",OrderMaxUtility.getValidEntityString(this.shipmentGatewayConfigId));
val.set("excludeFeatureGroup",OrderMaxUtility.getValidEntityString(this.excludeFeatureGroup));
val.set("sequenceNumber",this.sequenceNumber);
val.set("serviceName",OrderMaxUtility.getValidEntityString(this.serviceName));
val.set("maxWeight",OrderMaxUtility.getValidEntityString(this.maxWeight));
val.set("includeNoChargeItems",OrderMaxUtility.getValidEntityString(this.includeNoChargeItems));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("requireUspsAddr",OrderMaxUtility.getValidEntityString(this.requireUspsAddr));
val.set("includeGeoId",OrderMaxUtility.getValidEntityString(this.includeGeoId));
val.set("requireCompanyAddr",OrderMaxUtility.getValidEntityString(this.requireCompanyAddr));
val.set("maxSize",OrderMaxUtility.getValidEntityString(this.maxSize));
val.set("allowUspsAddr",OrderMaxUtility.getValidEntityString(this.allowUspsAddr));
val.set("configProps",OrderMaxUtility.getValidEntityString(this.configProps));
val.set("productStoreShipMethId",OrderMaxUtility.getValidEntityString(this.productStoreShipMethId));
val.set("companyPartyId",OrderMaxUtility.getValidEntityString(this.companyPartyId));
val.set("roleTypeId",OrderMaxUtility.getValidEntityString(this.roleTypeId));
val.set("minSize",OrderMaxUtility.getValidEntityString(this.minSize));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("minWeight",OrderMaxUtility.getValidEntityString(this.minWeight));
val.set("allowCompanyAddr",OrderMaxUtility.getValidEntityString(this.allowCompanyAddr));
val.set("shipmentMethodTypeId",OrderMaxUtility.getValidEntityString(this.shipmentMethodTypeId));
val.set("minTotal",OrderMaxUtility.getValidEntityString(this.minTotal));
val.set("includeFeatureGroup",OrderMaxUtility.getValidEntityString(this.includeFeatureGroup));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("maxTotal",this.maxTotal);
valueMap.put("productStoreId",this.productStoreId);
valueMap.put("excludeGeoId",this.excludeGeoId);
valueMap.put("shipmentCustomMethodId",this.shipmentCustomMethodId);
valueMap.put("shipmentGatewayConfigId",this.shipmentGatewayConfigId);
valueMap.put("excludeFeatureGroup",this.excludeFeatureGroup);
valueMap.put("sequenceNumber",this.sequenceNumber);
valueMap.put("serviceName",this.serviceName);
valueMap.put("maxWeight",this.maxWeight);
valueMap.put("includeNoChargeItems",this.includeNoChargeItems);
valueMap.put("description",this.description);
valueMap.put("requireUspsAddr",this.requireUspsAddr);
valueMap.put("includeGeoId",this.includeGeoId);
valueMap.put("requireCompanyAddr",this.requireCompanyAddr);
valueMap.put("maxSize",this.maxSize);
valueMap.put("allowUspsAddr",this.allowUspsAddr);
valueMap.put("configProps",this.configProps);
valueMap.put("productStoreShipMethId",this.productStoreShipMethId);
valueMap.put("companyPartyId",this.companyPartyId);
valueMap.put("roleTypeId",this.roleTypeId);
valueMap.put("minSize",this.minSize);
valueMap.put("partyId",this.partyId);
valueMap.put("minWeight",this.minWeight);
valueMap.put("allowCompanyAddr",this.allowCompanyAddr);
valueMap.put("shipmentMethodTypeId",this.shipmentMethodTypeId);
valueMap.put("minTotal",this.minTotal);
valueMap.put("includeFeatureGroup",this.includeFeatureGroup);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductStoreShipmentMethView");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String maxTotal;
public java.lang.String getmaxTotal() {
return maxTotal;
}
public void setmaxTotal( java.lang.String maxTotal ) {
this.maxTotal = maxTotal;
}
private java.lang.String productStoreId;
public java.lang.String getproductStoreId() {
return productStoreId;
}
public void setproductStoreId( java.lang.String productStoreId ) {
this.productStoreId = productStoreId;
}
private java.lang.String excludeGeoId;
public java.lang.String getexcludeGeoId() {
return excludeGeoId;
}
public void setexcludeGeoId( java.lang.String excludeGeoId ) {
this.excludeGeoId = excludeGeoId;
}
private java.lang.String shipmentCustomMethodId;
public java.lang.String getshipmentCustomMethodId() {
return shipmentCustomMethodId;
}
public void setshipmentCustomMethodId( java.lang.String shipmentCustomMethodId ) {
this.shipmentCustomMethodId = shipmentCustomMethodId;
}
private java.lang.String shipmentGatewayConfigId;
public java.lang.String getshipmentGatewayConfigId() {
return shipmentGatewayConfigId;
}
public void setshipmentGatewayConfigId( java.lang.String shipmentGatewayConfigId ) {
this.shipmentGatewayConfigId = shipmentGatewayConfigId;
}
private java.lang.String excludeFeatureGroup;
public java.lang.String getexcludeFeatureGroup() {
return excludeFeatureGroup;
}
public void setexcludeFeatureGroup( java.lang.String excludeFeatureGroup ) {
this.excludeFeatureGroup = excludeFeatureGroup;
}
private java.lang.Long sequenceNumber;
public java.lang.Long getsequenceNumber() {
return sequenceNumber;
}
public void setsequenceNumber( java.lang.Long sequenceNumber ) {
this.sequenceNumber = sequenceNumber;
}
private java.lang.String serviceName;
public java.lang.String getserviceName() {
return serviceName;
}
public void setserviceName( java.lang.String serviceName ) {
this.serviceName = serviceName;
}
private java.lang.String maxWeight;
public java.lang.String getmaxWeight() {
return maxWeight;
}
public void setmaxWeight( java.lang.String maxWeight ) {
this.maxWeight = maxWeight;
}
private java.lang.String includeNoChargeItems;
public java.lang.String getincludeNoChargeItems() {
return includeNoChargeItems;
}
public void setincludeNoChargeItems( java.lang.String includeNoChargeItems ) {
this.includeNoChargeItems = includeNoChargeItems;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String requireUspsAddr;
public java.lang.String getrequireUspsAddr() {
return requireUspsAddr;
}
public void setrequireUspsAddr( java.lang.String requireUspsAddr ) {
this.requireUspsAddr = requireUspsAddr;
}
private java.lang.String includeGeoId;
public java.lang.String getincludeGeoId() {
return includeGeoId;
}
public void setincludeGeoId( java.lang.String includeGeoId ) {
this.includeGeoId = includeGeoId;
}
private java.lang.String requireCompanyAddr;
public java.lang.String getrequireCompanyAddr() {
return requireCompanyAddr;
}
public void setrequireCompanyAddr( java.lang.String requireCompanyAddr ) {
this.requireCompanyAddr = requireCompanyAddr;
}
private java.lang.String maxSize;
public java.lang.String getmaxSize() {
return maxSize;
}
public void setmaxSize( java.lang.String maxSize ) {
this.maxSize = maxSize;
}
private java.lang.String allowUspsAddr;
public java.lang.String getallowUspsAddr() {
return allowUspsAddr;
}
public void setallowUspsAddr( java.lang.String allowUspsAddr ) {
this.allowUspsAddr = allowUspsAddr;
}
private java.lang.String configProps;
public java.lang.String getconfigProps() {
return configProps;
}
public void setconfigProps( java.lang.String configProps ) {
this.configProps = configProps;
}
private java.lang.String productStoreShipMethId;
public java.lang.String getproductStoreShipMethId() {
return productStoreShipMethId;
}
public void setproductStoreShipMethId( java.lang.String productStoreShipMethId ) {
this.productStoreShipMethId = productStoreShipMethId;
}
private java.lang.String companyPartyId;
public java.lang.String getcompanyPartyId() {
return companyPartyId;
}
public void setcompanyPartyId( java.lang.String companyPartyId ) {
this.companyPartyId = companyPartyId;
}
private java.lang.String roleTypeId;
public java.lang.String getroleTypeId() {
return roleTypeId;
}
public void setroleTypeId( java.lang.String roleTypeId ) {
this.roleTypeId = roleTypeId;
}
private java.lang.String minSize;
public java.lang.String getminSize() {
return minSize;
}
public void setminSize( java.lang.String minSize ) {
this.minSize = minSize;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String minWeight;
public java.lang.String getminWeight() {
return minWeight;
}
public void setminWeight( java.lang.String minWeight ) {
this.minWeight = minWeight;
}
private java.lang.String allowCompanyAddr;
public java.lang.String getallowCompanyAddr() {
return allowCompanyAddr;
}
public void setallowCompanyAddr( java.lang.String allowCompanyAddr ) {
this.allowCompanyAddr = allowCompanyAddr;
}
private java.lang.String shipmentMethodTypeId;
public java.lang.String getshipmentMethodTypeId() {
return shipmentMethodTypeId;
}
public void setshipmentMethodTypeId( java.lang.String shipmentMethodTypeId ) {
this.shipmentMethodTypeId = shipmentMethodTypeId;
}
private java.lang.String minTotal;
public java.lang.String getminTotal() {
return minTotal;
}
public void setminTotal( java.lang.String minTotal ) {
this.minTotal = minTotal;
}
private java.lang.String includeFeatureGroup;
public java.lang.String getincludeFeatureGroup() {
return includeFeatureGroup;
}
public void setincludeFeatureGroup( java.lang.String includeFeatureGroup ) {
this.includeFeatureGroup = includeFeatureGroup;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductStoreShipmentMethView> objectList = new ArrayList<ProductStoreShipmentMethView>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductStoreShipmentMethView(genVal));
        }
        return objectList;
    }    
}
