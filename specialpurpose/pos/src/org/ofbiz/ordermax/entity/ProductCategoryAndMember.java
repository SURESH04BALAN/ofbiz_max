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
public class ProductCategoryAndMember implements GenericValueObjectInterface {
public static final String module =ProductCategoryAndMember.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public ProductCategoryAndMember(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public ProductCategoryAndMember () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"linkOneImageUrl","Link One Image Url"},
{"thruDate","Thru Date"},
{"linkTwoImageUrl","Link Two Image Url"},
{"primaryParentCategoryId","Primary Parent Category Id"},
{"productId","Product Id"},
{"showInSelect","Show In Select"},
{"sequenceNum","Sequence Num"},
{"detailScreen","Detail Screen"},
{"categoryName","Category Name"},
{"categoryImageUrl","Category Image Url"},
{"fromDate","From Date"},
{"description","Description"},
{"longDescription","Long Description"},
{"productCategoryTypeId","Product Category Type Id"},
{"quantity","Quantity"},
{"productCategoryId","Product Category Id"},
{"comments","Comments"},
};
protected void initObject(){
this.linkOneImageUrl = "";
this.thruDate = UtilDateTime.nowTimestamp();
this.linkTwoImageUrl = "";
this.primaryParentCategoryId = "";
this.productId = "";
this.showInSelect = "";
this.sequenceNum = "";
this.detailScreen = "";
this.categoryName = "";
this.categoryImageUrl = "";
this.fromDate = UtilDateTime.nowTimestamp();
this.description = "";
this.longDescription = "";
this.productCategoryTypeId = "";
this.quantity = "";
this.productCategoryId = "";
this.comments = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.linkOneImageUrl = (java.lang.String) genVal.get("linkOneImageUrl");
this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");
this.linkTwoImageUrl = (java.lang.String) genVal.get("linkTwoImageUrl");
this.primaryParentCategoryId = (java.lang.String) genVal.get("primaryParentCategoryId");
this.productId = (java.lang.String) genVal.get("productId");
this.showInSelect = (java.lang.String) genVal.get("showInSelect");
this.sequenceNum = (java.lang.String) genVal.get("sequenceNum");
this.detailScreen = (java.lang.String) genVal.get("detailScreen");
this.categoryName = (java.lang.String) genVal.get("categoryName");
this.categoryImageUrl = (java.lang.String) genVal.get("categoryImageUrl");
this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
this.description = (java.lang.String) genVal.get("description");
this.longDescription = (java.lang.String) genVal.get("longDescription");
this.productCategoryTypeId = (java.lang.String) genVal.get("productCategoryTypeId");
this.quantity = (java.lang.String) genVal.get("quantity");
this.productCategoryId = (java.lang.String) genVal.get("productCategoryId");
this.comments = (java.lang.String) genVal.get("comments");
}
protected void getGenericValue(GenericValue val) {
val.set("linkOneImageUrl",OrderMaxUtility.getValidEntityString(this.linkOneImageUrl));
val.set("thruDate",OrderMaxUtility.getValidEntityTimestamp(this.thruDate));
val.set("linkTwoImageUrl",OrderMaxUtility.getValidEntityString(this.linkTwoImageUrl));
val.set("primaryParentCategoryId",OrderMaxUtility.getValidEntityString(this.primaryParentCategoryId));
val.set("productId",OrderMaxUtility.getValidEntityString(this.productId));
val.set("showInSelect",OrderMaxUtility.getValidEntityString(this.showInSelect));
val.set("sequenceNum",OrderMaxUtility.getValidEntityString(this.sequenceNum));
val.set("detailScreen",OrderMaxUtility.getValidEntityString(this.detailScreen));
val.set("categoryName",OrderMaxUtility.getValidEntityString(this.categoryName));
val.set("categoryImageUrl",OrderMaxUtility.getValidEntityString(this.categoryImageUrl));
val.set("fromDate",OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
val.set("description",OrderMaxUtility.getValidEntityString(this.description));
val.set("longDescription",OrderMaxUtility.getValidEntityString(this.longDescription));
val.set("productCategoryTypeId",OrderMaxUtility.getValidEntityString(this.productCategoryTypeId));
val.set("quantity",OrderMaxUtility.getValidEntityString(this.quantity));
val.set("productCategoryId",OrderMaxUtility.getValidEntityString(this.productCategoryId));
val.set("comments",OrderMaxUtility.getValidEntityString(this.comments));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("linkOneImageUrl",this.linkOneImageUrl);
valueMap.put("thruDate",this.thruDate);
valueMap.put("linkTwoImageUrl",this.linkTwoImageUrl);
valueMap.put("primaryParentCategoryId",this.primaryParentCategoryId);
valueMap.put("productId",this.productId);
valueMap.put("showInSelect",this.showInSelect);
valueMap.put("sequenceNum",this.sequenceNum);
valueMap.put("detailScreen",this.detailScreen);
valueMap.put("categoryName",this.categoryName);
valueMap.put("categoryImageUrl",this.categoryImageUrl);
valueMap.put("fromDate",this.fromDate);
valueMap.put("description",this.description);
valueMap.put("longDescription",this.longDescription);
valueMap.put("productCategoryTypeId",this.productCategoryTypeId);
valueMap.put("quantity",this.quantity);
valueMap.put("productCategoryId",this.productCategoryId);
valueMap.put("comments",this.comments);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("ProductCategoryAndMember");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String linkOneImageUrl;
public java.lang.String getlinkOneImageUrl() {
return linkOneImageUrl;
}
public void setlinkOneImageUrl( java.lang.String linkOneImageUrl ) {
this.linkOneImageUrl = linkOneImageUrl;
}
private java.sql.Timestamp thruDate;
public java.sql.Timestamp getthruDate() {
return thruDate;
}
public void setthruDate( java.sql.Timestamp thruDate ) {
this.thruDate = thruDate;
}
private java.lang.String linkTwoImageUrl;
public java.lang.String getlinkTwoImageUrl() {
return linkTwoImageUrl;
}
public void setlinkTwoImageUrl( java.lang.String linkTwoImageUrl ) {
this.linkTwoImageUrl = linkTwoImageUrl;
}
private java.lang.String primaryParentCategoryId;
public java.lang.String getprimaryParentCategoryId() {
return primaryParentCategoryId;
}
public void setprimaryParentCategoryId( java.lang.String primaryParentCategoryId ) {
this.primaryParentCategoryId = primaryParentCategoryId;
}
private java.lang.String productId;
public java.lang.String getproductId() {
return productId;
}
public void setproductId( java.lang.String productId ) {
this.productId = productId;
}
private java.lang.String showInSelect;
public java.lang.String getshowInSelect() {
return showInSelect;
}
public void setshowInSelect( java.lang.String showInSelect ) {
this.showInSelect = showInSelect;
}
private java.lang.String sequenceNum;
public java.lang.String getsequenceNum() {
return sequenceNum;
}
public void setsequenceNum( java.lang.String sequenceNum ) {
this.sequenceNum = sequenceNum;
}
private java.lang.String detailScreen;
public java.lang.String getdetailScreen() {
return detailScreen;
}
public void setdetailScreen( java.lang.String detailScreen ) {
this.detailScreen = detailScreen;
}
private java.lang.String categoryName;
public java.lang.String getcategoryName() {
return categoryName;
}
public void setcategoryName( java.lang.String categoryName ) {
this.categoryName = categoryName;
}
private java.lang.String categoryImageUrl;
public java.lang.String getcategoryImageUrl() {
return categoryImageUrl;
}
public void setcategoryImageUrl( java.lang.String categoryImageUrl ) {
this.categoryImageUrl = categoryImageUrl;
}
private java.sql.Timestamp fromDate;
public java.sql.Timestamp getfromDate() {
return fromDate;
}
public void setfromDate( java.sql.Timestamp fromDate ) {
this.fromDate = fromDate;
}
private java.lang.String description;
public java.lang.String getdescription() {
return description;
}
public void setdescription( java.lang.String description ) {
this.description = description;
}
private java.lang.String longDescription;
public java.lang.String getlongDescription() {
return longDescription;
}
public void setlongDescription( java.lang.String longDescription ) {
this.longDescription = longDescription;
}
private java.lang.String productCategoryTypeId;
public java.lang.String getproductCategoryTypeId() {
return productCategoryTypeId;
}
public void setproductCategoryTypeId( java.lang.String productCategoryTypeId ) {
this.productCategoryTypeId = productCategoryTypeId;
}
private java.lang.String quantity;
public java.lang.String getquantity() {
return quantity;
}
public void setquantity( java.lang.String quantity ) {
this.quantity = quantity;
}
private java.lang.String productCategoryId;
public java.lang.String getproductCategoryId() {
return productCategoryId;
}
public void setproductCategoryId( java.lang.String productCategoryId ) {
this.productCategoryId = productCategoryId;
}
private java.lang.String comments;
public java.lang.String getcomments() {
return comments;
}
public void setcomments( java.lang.String comments ) {
this.comments = comments;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductCategoryAndMember> objectList = new ArrayList<ProductCategoryAndMember>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductCategoryAndMember(genVal));
        }
        return objectList;
    }    
}
