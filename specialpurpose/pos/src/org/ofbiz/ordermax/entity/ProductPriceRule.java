/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.entity;

import java.sql.Timestamp;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity;

public class ProductPriceRule implements GenericValueObjectInterface {

    public static final String module = ProductPriceRule.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProductPriceRule(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public ProductPriceRule() {
        initObject();
    }
    static public OrderMaxViewEntity.ColumnDetails[] GenericColumnName = new OrderMaxViewEntity.ColumnDetails[]{};

    static public String[][] ColumnNameId = {
        {"productPriceRuleId", "Product Price Rule Id"},
        {"ruleName", "Rule Name"},
        {"description", "Description"},
        {"isSale", "Is Sale"},
        {"fromDate", "From Date"},
        {"thruDate", "Thru Date"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},};

    protected void initObject() {

        this.productPriceRuleId = "";
        this.ruleName = "";
        this.description = "";
        this.isSale = "";
        this.fromDate = UtilDateTime.nowTimestamp();
        this.thruDate = UtilDateTime.nowTimestamp();
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
    }
    private java.lang.String ruleName;
    private java.lang.String isSale;
    private java.sql.Timestamp fromDate;
    private java.sql.Timestamp thruDate;
    private java.lang.String description;
    private java.sql.Timestamp lastUpdatedStamp;
    private java.sql.Timestamp lastUpdatedTxStamp;
    private java.sql.Timestamp createdStamp;
    private java.sql.Timestamp createdTxStamp;

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.productPriceRuleId = (java.lang.String) genVal.get("productPriceRuleId");
        this.description = (java.lang.String) genVal.get("description");
        this.ruleName = (java.lang.String) genVal.get("ruleName");
        this.isSale = (java.lang.String) genVal.get("isSale");
        this.fromDate = (java.sql.Timestamp) genVal.get("fromDate");
        this.thruDate = (java.sql.Timestamp) genVal.get("thruDate");

        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("productPriceRuleId", OrderMaxUtility.getValidEntityString(this.productPriceRuleId));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));        
        val.set("ruleName", OrderMaxUtility.getValidEntityString(this.ruleName));
        val.set("isSale", OrderMaxUtility.getValidEntityString(this.isSale));
        val.set("fromDate", OrderMaxUtility.getValidEntityTimestamp(this.fromDate));
        val.set("thruDate", OrderMaxUtility.getValidEntityTimestamp(this.thruDate));

        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProductPriceRule");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String productPriceRuleId;

    public java.lang.String getProductPriceRuleId() {
        return productPriceRuleId;
    }

    public void setProductPriceRuleId(java.lang.String ProductPriceRuleId) {
        this.productPriceRuleId = productPriceRuleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getIsSale() {
        return isSale;
    }

    public void setIsSale(String isSale) {
        this.isSale = isSale;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getThruDate() {
        return thruDate;
    }

    public void setThruDate(Timestamp thruDate) {
        this.thruDate = thruDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getLastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    public Timestamp getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }

    public Timestamp getCreatedTxStamp() {
        return createdTxStamp;
    }

    public void setCreatedTxStamp(Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

//    @Override
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProductPriceRule> objectList = new ArrayList<ProductPriceRule>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProductPriceRule(genVal));
        }
        return objectList;
    }
    
    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
