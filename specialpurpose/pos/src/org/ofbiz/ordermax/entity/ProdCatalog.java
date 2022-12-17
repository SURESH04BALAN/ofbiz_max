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

public class ProdCatalog implements GenericValueObjectInterface, DisplayNameInterface {

    public static final String module = ProdCatalog.class.getName();
    public static int COL_SIZE = 80;
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public ProdCatalog(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public ProdCatalog() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"prodCatalogId", "Prod Catalog Id"},
        {"catalogName", "Catalog Name"},
        {"viewAllowPermReqd", "View Allow Perm Reqd"},
        {"contentPathPrefix", "Content Path Prefix"},
        {"headerLogo", "Header Logo"},
        {"useQuickAdd", "Use Quick Add"},
        {"purchaseAllowPermReqd", "Purchase Allow Perm Reqd"},
        {"styleSheet", "Style Sheet"},
        {"templatePathPrefix", "Template Path Prefix"},};

    protected void initObject() {
        this.viewAllowPermReqd = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.contentPathPrefix = "";
        this.headerLogo = "";
        this.useQuickAdd = "";
        this.purchaseAllowPermReqd = "";
        this.styleSheet = "";
        this.templatePathPrefix = "";
        this.catalogName = "";
        this.createdTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.prodCatalogId = "";
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.viewAllowPermReqd = (java.lang.String) genVal.get("viewAllowPermReqd");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.contentPathPrefix = (java.lang.String) genVal.get("contentPathPrefix");
        this.headerLogo = (java.lang.String) genVal.get("headerLogo");
        this.useQuickAdd = (java.lang.String) genVal.get("useQuickAdd");
        this.purchaseAllowPermReqd = (java.lang.String) genVal.get("purchaseAllowPermReqd");
        this.styleSheet = (java.lang.String) genVal.get("styleSheet");
        this.templatePathPrefix = (java.lang.String) genVal.get("templatePathPrefix");
        this.catalogName = (java.lang.String) genVal.get("catalogName");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.prodCatalogId = (java.lang.String) genVal.get("prodCatalogId");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
    }

    protected void getGenericValue(GenericValue val) {
        val.set("viewAllowPermReqd", OrderMaxUtility.getValidEntityString(this.viewAllowPermReqd));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("contentPathPrefix", OrderMaxUtility.getValidEntityString(this.contentPathPrefix));
        val.set("headerLogo", OrderMaxUtility.getValidEntityString(this.headerLogo));
        val.set("useQuickAdd", OrderMaxUtility.getValidEntityString(this.useQuickAdd));
        val.set("purchaseAllowPermReqd", OrderMaxUtility.getValidEntityString(this.purchaseAllowPermReqd));
        val.set("styleSheet", OrderMaxUtility.getValidEntityString(this.styleSheet));
        val.set("templatePathPrefix", OrderMaxUtility.getValidEntityString(this.templatePathPrefix));
        val.set("catalogName", OrderMaxUtility.getValidEntityString(this.catalogName));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("prodCatalogId", OrderMaxUtility.getValidEntityString(this.prodCatalogId));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
    }

    public Map<String, Object> getValuesMap() {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("viewAllowPermReqd", this.viewAllowPermReqd);
        valueMap.put("contentPathPrefix", this.contentPathPrefix);
        valueMap.put("headerLogo", this.headerLogo);
        valueMap.put("useQuickAdd", this.useQuickAdd);
        valueMap.put("purchaseAllowPermReqd", this.purchaseAllowPermReqd);
        valueMap.put("styleSheet", this.styleSheet);
        valueMap.put("templatePathPrefix", this.templatePathPrefix);
        valueMap.put("catalogName", this.catalogName);
        valueMap.put("prodCatalogId", this.prodCatalogId);
        return valueMap;
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("ProdCatalog");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String viewAllowPermReqd;

    public java.lang.String getviewAllowPermReqd() {
        return viewAllowPermReqd;
    }

    public void setviewAllowPermReqd(java.lang.String viewAllowPermReqd) {
        this.viewAllowPermReqd = viewAllowPermReqd;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.lang.String contentPathPrefix;

    public java.lang.String getcontentPathPrefix() {
        return contentPathPrefix;
    }

    public void setcontentPathPrefix(java.lang.String contentPathPrefix) {
        this.contentPathPrefix = contentPathPrefix;
    }
    private java.lang.String headerLogo;

    public java.lang.String getheaderLogo() {
        return headerLogo;
    }

    public void setheaderLogo(java.lang.String headerLogo) {
        this.headerLogo = headerLogo;
    }
    private java.lang.String useQuickAdd;

    public java.lang.String getuseQuickAdd() {
        return useQuickAdd;
    }

    public void setuseQuickAdd(java.lang.String useQuickAdd) {
        this.useQuickAdd = useQuickAdd;
    }
    private java.lang.String purchaseAllowPermReqd;

    public java.lang.String getpurchaseAllowPermReqd() {
        return purchaseAllowPermReqd;
    }

    public void setpurchaseAllowPermReqd(java.lang.String purchaseAllowPermReqd) {
        this.purchaseAllowPermReqd = purchaseAllowPermReqd;
    }
    private java.lang.String styleSheet;

    public java.lang.String getstyleSheet() {
        return styleSheet;
    }

    public void setstyleSheet(java.lang.String styleSheet) {
        this.styleSheet = styleSheet;
    }
    private java.lang.String templatePathPrefix;

    public java.lang.String gettemplatePathPrefix() {
        return templatePathPrefix;
    }

    public void settemplatePathPrefix(java.lang.String templatePathPrefix) {
        this.templatePathPrefix = templatePathPrefix;
    }
    private java.lang.String catalogName;

    public java.lang.String getcatalogName() {
        return catalogName;
    }

    public void setcatalogName(java.lang.String catalogName) {
        this.catalogName = catalogName;
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
    private java.lang.String prodCatalogId;

    public java.lang.String getprodCatalogId() {
        return prodCatalogId;
    }

    public void setprodCatalogId(java.lang.String prodCatalogId) {
        this.prodCatalogId = prodCatalogId;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }

    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<ProdCatalog> objectList = new ArrayList<ProdCatalog>();
        for (GenericValue genVal : genList) {
            objectList.add(new ProdCatalog(genVal));
        }
        return objectList;
    }

    @Override
    public String getdisplayName(DisplayTypes showId) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId) || DisplayTypes.SHOW_NAME_ONLY.equals(showId)) {

            orderToStringBuilder.append(getcatalogName());

            if (DisplayTypes.SHOW_NAME_AND_CODE.equals(showId)) {
                orderToStringBuilder.append(" [");
                orderToStringBuilder.append(getprodCatalogId());
                orderToStringBuilder.append(" ]");
            }
        } else {
            orderToStringBuilder.append(getprodCatalogId());
        }

        return orderToStringBuilder.toString();
    }
}
