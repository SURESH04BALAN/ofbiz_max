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
public class PartyAcctgPrefAndGroup implements GenericValueObjectInterface {
public static final String module =PartyAcctgPrefAndGroup.class.getName();
public static int COL_SIZE = 80;
final static String Default_Cell_Rendere = "DEFAULT";
final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
private GenericValue genVal = null;
public PartyAcctgPrefAndGroup(GenericValue val){
genVal =  val;
try {
setGenericValue();
} catch (Exception ex) {
Debug.logInfo(ex, module);
}
}
public PartyAcctgPrefAndGroup () {
initObject();
}
 static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
};
 static public String[][] ColumnNameId = {
{"groupName","Group Name"},
{"partyId","Party Id"},
{"baseCurrencyUomId","Base Currency Uom Id"},
};
protected void initObject(){
this.groupName = "";
this.partyId = "";
this.baseCurrencyUomId = "";
}
public void setGenericValue() throws Exception {
if(genVal==null){
throw new Exception("Generic Value object is not set");
}
this.groupName = (java.lang.String) genVal.get("groupName");
this.partyId = (java.lang.String) genVal.get("partyId");
this.baseCurrencyUomId = (java.lang.String) genVal.get("baseCurrencyUomId");
}
protected void getGenericValue(GenericValue val) {
val.set("groupName",OrderMaxUtility.getValidEntityString(this.groupName));
val.set("partyId",OrderMaxUtility.getValidEntityString(this.partyId));
val.set("baseCurrencyUomId",OrderMaxUtility.getValidEntityString(this.baseCurrencyUomId));
}
public Map<String, Object> getValuesMap() {
Map<String, Object> valueMap = new HashMap<String,Object>();
valueMap.put("groupName",this.groupName);
valueMap.put("partyId",this.partyId);
valueMap.put("baseCurrencyUomId",this.baseCurrencyUomId);
return valueMap;
}
public void getGenericValue(){
getGenericValue(genVal);
}
public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){
 genVal = delegator.makeValue("PartyAcctgPrefAndGroup");
        getGenericValue(genVal);
return genVal;
}
public boolean isGenericValueSet() {
return genVal != null;
}
public GenericValue getGenericValueObj() {
return genVal;
}
private java.lang.String groupName;
public java.lang.String getgroupName() {
return groupName;
}
public void setgroupName( java.lang.String groupName ) {
this.groupName = groupName;
}
private java.lang.String partyId;
public java.lang.String getpartyId() {
return partyId;
}
public void setpartyId( java.lang.String partyId ) {
this.partyId = partyId;
}
private java.lang.String baseCurrencyUomId;
public java.lang.String getbaseCurrencyUomId() {
return baseCurrencyUomId;
}
public void setbaseCurrencyUomId( java.lang.String baseCurrencyUomId ) {
this.baseCurrencyUomId = baseCurrencyUomId;
}
@Override
public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<PartyAcctgPrefAndGroup> objectList = new ArrayList<PartyAcctgPrefAndGroup>();
        for (GenericValue genVal : genList) {
            objectList.add(new PartyAcctgPrefAndGroup(genVal));
        }
        return objectList;
    }    
}
