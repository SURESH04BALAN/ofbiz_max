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
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.list.GoodIdentificationListCellRenderer;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;

public class GoodIdentification implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = GoodIdentification.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public GoodIdentification(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
Debug.logInfo(ex, module);
        }
    }

    public GoodIdentification() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"idValue", "Id Value"},
        {"createdTxStamp", "Created Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"goodIdentificationTypeId", "Good Identification Type Id"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"productId", "Product Id"},};

    protected void initObject() {
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.idValue = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.goodIdentificationTypeId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.productId = "";
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.idValue = (java.lang.String) genVal.get("idValue");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.goodIdentificationTypeId = (java.lang.String) genVal.get("goodIdentificationTypeId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.productId = (java.lang.String) genVal.get("productId");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("idValue", OrderMaxUtility.getValidEntityString(this.idValue));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("goodIdentificationTypeId", OrderMaxUtility.getValidEntityString(this.goodIdentificationTypeId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("idValue", this.idValue);
        valueMap.put("goodIdentificationTypeId", this.goodIdentificationTypeId);
        valueMap.put("productId", this.productId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("GoodIdentification");
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

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String idValue;

    public java.lang.String getidValue() {
        return idValue;
    }

    public void setidValue(java.lang.String idValue) {
        this.idValue = idValue;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.lang.String goodIdentificationTypeId;

    public java.lang.String getgoodIdentificationTypeId() {
        return goodIdentificationTypeId;
    }

    public void setgoodIdentificationTypeId(java.lang.String goodIdentificationTypeId) {
        this.goodIdentificationTypeId = goodIdentificationTypeId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<GoodIdentification> objectList = new ArrayList<GoodIdentification>();
        for (GenericValue genVal : genList) {
            objectList.add(new GoodIdentification(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayNameInterface.DisplayTypes showId) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {

            orderToStringBuilder.append(getidValue());

            if (DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                try {
                    GoodIdentificationType type = GoodIdentificationTypeSingleton.getGoodIdentificationType(getgoodIdentificationTypeId());
                    orderToStringBuilder.append(" [");
                    orderToStringBuilder.append(type.getdescription());
                    orderToStringBuilder.append(" ]");
                } catch (Exception ex) {
                    Logger.getLogger(GoodIdentificationListCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            orderToStringBuilder.append(getidValue());
        }

        return orderToStringBuilder.toString();
    }

}
