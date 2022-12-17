package org.ofbiz.ordermax.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
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
import java.util.Map;
import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.product.product.ProductWorker;

public class InvoiceItem implements GenericValueObjectInterface {

    public static final String module = InvoiceItem.class.getName();
    public static int COL_SIZE = 80;
    public static final int scale = UtilNumber.getBigDecimalScale("order.decimals");
    public static final int rounding = UtilNumber.getBigDecimalRoundingMode("order.rounding");

    final static String Default_Cell_Rendere = "DEFAULT";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    private GenericValue genVal = null;

    public InvoiceItem(GenericValue val) {
        genVal = val;
        try {
            setGenericValue();
        } catch (Exception ex) {
            Debug.logInfo(ex, module);
        }
    }

    public InvoiceItem() {
        initObject();
    }
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{};
    static public String[][] ColumnNameId = {
        {"invoiceId", "Invoice Id"},
        {"invoiceItemSeqId", "Invoice Item Seq Id"},
        {"invoiceItemTypeId", "Invoice Item Type Id"},
        {"overrideGlAccountId", "Override Gl Account Id"},
        {"overrideOrgPartyId", "Override Org Party Id"},
        {"inventoryItemId", "Inventory Item Id"},
        {"productId", "Product Id"},
        {"productFeatureId", "Product Feature Id"},
        {"parentInvoiceId", "Parent Invoice Id"},
        {"parentInvoiceItemSeqId", "Parent Invoice Item Seq Id"},
        {"uomId", "Uom Id"},
        {"taxableFlag", "Taxable Flag"},
        {"quantity", "Quantity"},
        {"amount", "Amount"},
        {"description", "Description"},
        {"taxAuthPartyId", "Tax Auth Party Id"},
        {"taxAuthGeoId", "Tax Auth Geo Id"},
        {"taxAuthorityRateSeqId", "Tax Authority Rate Seq Id"},
        {"salesOpportunityId", "Sales Opportunity Id"},
        {"lastUpdatedStamp", "Last Updated Stamp"},
        {"lastUpdatedTxStamp", "Last Updated Tx Stamp"},
        {"createdStamp", "Created Stamp"},
        {"createdTxStamp", "Created Tx Stamp"},};

    protected void initObject() {
        this.invoiceId = "";
        this.invoiceItemSeqId = "";
        this.invoiceItemTypeId = "";
        this.overrideGlAccountId = "";
        this.overrideOrgPartyId = "";
        this.inventoryItemId = "";
        this.productId = "";
        this.productFeatureId = "";
        this.parentInvoiceId = "";
        this.parentInvoiceItemSeqId = "";
        this.uomId = "";
        this.taxableFlag = "";
        this.quantity = java.math.BigDecimal.ZERO;
        this.amount = java.math.BigDecimal.ZERO;
        this.description = "";
        this.taxAuthPartyId = "";
        this.taxAuthGeoId = "";
        this.taxAuthorityRateSeqId = "";
        this.salesOpportunityId = "";
        this.lastUpdatedStamp = UtilDateTime.nowTimestamp();
        this.lastUpdatedTxStamp = UtilDateTime.nowTimestamp();
        this.createdStamp = UtilDateTime.nowTimestamp();
        this.createdTxStamp = UtilDateTime.nowTimestamp();
    }

    public void setGenericValue() throws Exception {
        if (genVal == null) {
            throw new Exception("Generic Value object is not set");
        }
        this.invoiceId = (java.lang.String) genVal.get("invoiceId");
        this.invoiceItemSeqId = (java.lang.String) genVal.get("invoiceItemSeqId");
        this.invoiceItemTypeId = (java.lang.String) genVal.get("invoiceItemTypeId");
        this.overrideGlAccountId = (java.lang.String) genVal.get("overrideGlAccountId");
        this.overrideOrgPartyId = (java.lang.String) genVal.get("overrideOrgPartyId");
        this.inventoryItemId = (java.lang.String) genVal.get("inventoryItemId");
        this.productId = (java.lang.String) genVal.get("productId");
        this.productFeatureId = (java.lang.String) genVal.get("productFeatureId");
        this.parentInvoiceId = (java.lang.String) genVal.get("parentInvoiceId");
        this.parentInvoiceItemSeqId = (java.lang.String) genVal.get("parentInvoiceItemSeqId");
        this.uomId = (java.lang.String) genVal.get("uomId");
        this.taxableFlag = (java.lang.String) genVal.get("taxableFlag");
        this.quantity = (java.math.BigDecimal) genVal.get("quantity");
        this.amount = (java.math.BigDecimal) genVal.get("amount");
        this.description = (java.lang.String) genVal.get("description");
        this.taxAuthPartyId = (java.lang.String) genVal.get("taxAuthPartyId");
        this.taxAuthGeoId = (java.lang.String) genVal.get("taxAuthGeoId");
        this.taxAuthorityRateSeqId = (java.lang.String) genVal.get("taxAuthorityRateSeqId");
        this.salesOpportunityId = (java.lang.String) genVal.get("salesOpportunityId");
        this.lastUpdatedStamp = (java.sql.Timestamp) genVal.get("lastUpdatedStamp");
        this.lastUpdatedTxStamp = (java.sql.Timestamp) genVal.get("lastUpdatedTxStamp");
        this.createdStamp = (java.sql.Timestamp) genVal.get("createdStamp");
        this.createdTxStamp = (java.sql.Timestamp) genVal.get("createdTxStamp");
        if (amount != null && quantity != null) {
            this.setTotalItemAmount(amount.multiply(quantity).setScale(scale, rounding));
        }
    }

    protected void getGenericValue(GenericValue val) {
        val.set("invoiceId", OrderMaxUtility.getValidEntityString(this.invoiceId));
        val.set("invoiceItemSeqId", OrderMaxUtility.getValidEntityString(this.invoiceItemSeqId));
        val.set("invoiceItemTypeId", OrderMaxUtility.getValidEntityString(this.invoiceItemTypeId));
        val.set("overrideGlAccountId", OrderMaxUtility.getValidEntityString(this.overrideGlAccountId));
        val.set("overrideOrgPartyId", OrderMaxUtility.getValidEntityString(this.overrideOrgPartyId));
        val.set("inventoryItemId", OrderMaxUtility.getValidEntityString(this.inventoryItemId));
        val.set("productId", OrderMaxUtility.getValidEntityString(this.productId));
        val.set("productFeatureId", OrderMaxUtility.getValidEntityString(this.productFeatureId));
        val.set("parentInvoiceId", OrderMaxUtility.getValidEntityString(this.parentInvoiceId));
        val.set("parentInvoiceItemSeqId", OrderMaxUtility.getValidEntityString(this.parentInvoiceItemSeqId));
        val.set("uomId", OrderMaxUtility.getValidEntityString(this.uomId));
        val.set("taxableFlag", OrderMaxUtility.getValidEntityString(this.taxableFlag));
        val.set("quantity", OrderMaxUtility.getValidEntityBigDecimal(this.quantity));
        val.set("amount", OrderMaxUtility.getValidEntityBigDecimal(this.amount));
        val.set("description", OrderMaxUtility.getValidEntityString(this.description));
        val.set("taxAuthPartyId", OrderMaxUtility.getValidEntityString(this.taxAuthPartyId));
        val.set("taxAuthGeoId", OrderMaxUtility.getValidEntityString(this.taxAuthGeoId));
        val.set("taxAuthorityRateSeqId", OrderMaxUtility.getValidEntityString(this.taxAuthorityRateSeqId));
        val.set("salesOpportunityId", OrderMaxUtility.getValidEntityString(this.salesOpportunityId));
        val.set("lastUpdatedStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedStamp));
        val.set("lastUpdatedTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.lastUpdatedTxStamp));
        val.set("createdStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdStamp));
        val.set("createdTxStamp", OrderMaxUtility.getValidEntityTimestamp(this.createdTxStamp));
    }

    public void getGenericValue() {
        getGenericValue(genVal);
    }

    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator) {
        genVal = delegator.makeValue("InvoiceItem");
        getGenericValue(genVal);
        return genVal;
    }

    public boolean isGenericValueSet() {
        return genVal != null;
    }

    public GenericValue getGenericValueObj() {
        return genVal;
    }
    private java.lang.String invoiceId;

    public java.lang.String getInvoiceId() {
        return invoiceId;
    }

    public static int getCOL_SIZE() {
        return COL_SIZE;
    }

    public static void setCOL_SIZE(int COL_SIZE) {
        InvoiceItem.COL_SIZE = COL_SIZE;
    }

    public GenericValue getGenVal() {
        return genVal;
    }

    public void setGenVal(GenericValue genVal) {
        this.genVal = genVal;
    }

    public String getInvoiceItemSeqId() {
        return invoiceItemSeqId;
    }

    public void setInvoiceItemSeqId(String invoiceItemSeqId) {
        this.invoiceItemSeqId = invoiceItemSeqId;
    }

    public String getInvoiceItemTypeId() {
        return invoiceItemTypeId;
    }

    public void setInvoiceItemTypeId(String invoiceItemTypeId) {
        this.invoiceItemTypeId = invoiceItemTypeId;
    }

    public String getOverrideGlAccountId() {
        return overrideGlAccountId;
    }

    public void setOverrideGlAccountId(String overrideGlAccountId) {
        this.overrideGlAccountId = overrideGlAccountId;
    }

    public String getOverrideOrgPartyId() {
        return overrideOrgPartyId;
    }

    public void setOverrideOrgPartyId(String overrideOrgPartyId) {
        this.overrideOrgPartyId = overrideOrgPartyId;
    }

    public String getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductFeatureId() {
        return productFeatureId;
    }

    public void setProductFeatureId(String productFeatureId) {
        this.productFeatureId = productFeatureId;
    }

    public String getParentInvoiceId() {
        return parentInvoiceId;
    }

    public void setParentInvoiceId(String parentInvoiceId) {
        this.parentInvoiceId = parentInvoiceId;
    }

    public String getParentInvoiceItemSeqId() {
        return parentInvoiceItemSeqId;
    }

    public void setParentInvoiceItemSeqId(String parentInvoiceItemSeqId) {
        this.parentInvoiceItemSeqId = parentInvoiceItemSeqId;
    }

    public String getUomId() {
        return uomId;
    }

    public void setUomId(String uomId) {
        this.uomId = uomId;
    }

    public String getTaxableFlag() {
        return taxableFlag;
    }

    public void setTaxableFlag(String taxableFlag) {
        this.taxableFlag = taxableFlag;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaxAuthPartyId() {
        return taxAuthPartyId;
    }

    public void setTaxAuthPartyId(String taxAuthPartyId) {
        this.taxAuthPartyId = taxAuthPartyId;
    }

    public String getTaxAuthGeoId() {
        return taxAuthGeoId;
    }

    public void setTaxAuthGeoId(String taxAuthGeoId) {
        this.taxAuthGeoId = taxAuthGeoId;
    }

    public String getTaxAuthorityRateSeqId() {
        return taxAuthorityRateSeqId;
    }

    public void setTaxAuthorityRateSeqId(String taxAuthorityRateSeqId) {
        this.taxAuthorityRateSeqId = taxAuthorityRateSeqId;
    }

    public String getSalesOpportunityId() {
        return salesOpportunityId;
    }

    public void setSalesOpportunityId(String salesOpportunityId) {
        this.salesOpportunityId = salesOpportunityId;
    }

    public Timestamp getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
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

    public void setInvoiceId(java.lang.String invoiceId) {
        this.invoiceId = invoiceId;
    }
    private java.lang.String invoiceItemSeqId;

    public java.lang.String getinvoiceItemSeqId() {
        return invoiceItemSeqId;
    }

    public void setinvoiceItemSeqId(java.lang.String invoiceItemSeqId) {
        this.invoiceItemSeqId = invoiceItemSeqId;
    }
    private java.lang.String invoiceItemTypeId;

    public java.lang.String getinvoiceItemTypeId() {
        return invoiceItemTypeId;
    }

    public void setinvoiceItemTypeId(java.lang.String invoiceItemTypeId) {
        this.invoiceItemTypeId = invoiceItemTypeId;
    }
    private java.lang.String overrideGlAccountId;

    public java.lang.String getoverrideGlAccountId() {
        return overrideGlAccountId;
    }

    public void setoverrideGlAccountId(java.lang.String overrideGlAccountId) {
        this.overrideGlAccountId = overrideGlAccountId;
    }
    private java.lang.String overrideOrgPartyId;

    public java.lang.String getoverrideOrgPartyId() {
        return overrideOrgPartyId;
    }

    public void setoverrideOrgPartyId(java.lang.String overrideOrgPartyId) {
        this.overrideOrgPartyId = overrideOrgPartyId;
    }
    private java.lang.String inventoryItemId;

    public java.lang.String getinventoryItemId() {
        return inventoryItemId;
    }

    public void setinventoryItemId(java.lang.String inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }
    private java.lang.String productId;

    public java.lang.String getproductId() {
        return productId;
    }

    public void setproductId(java.lang.String productId) {
        this.productId = productId;
    }
    private java.lang.String productFeatureId;

    public java.lang.String getproductFeatureId() {
        return productFeatureId;
    }

    public void setproductFeatureId(java.lang.String productFeatureId) {
        this.productFeatureId = productFeatureId;
    }
    private java.lang.String parentInvoiceId;

    public java.lang.String getparentInvoiceId() {
        return parentInvoiceId;
    }

    public void setparentInvoiceId(java.lang.String parentInvoiceId) {
        this.parentInvoiceId = parentInvoiceId;
    }
    private java.lang.String parentInvoiceItemSeqId;

    public java.lang.String getparentInvoiceItemSeqId() {
        return parentInvoiceItemSeqId;
    }

    public void setparentInvoiceItemSeqId(java.lang.String parentInvoiceItemSeqId) {
        this.parentInvoiceItemSeqId = parentInvoiceItemSeqId;
    }
    private java.lang.String uomId;

    public java.lang.String getuomId() {
        return uomId;
    }

    public void setuomId(java.lang.String uomId) {
        this.uomId = uomId;
    }
    private java.lang.String taxableFlag;

    public java.lang.String gettaxableFlag() {
        return taxableFlag;
    }

    public void settaxableFlag(java.lang.String taxableFlag) {
        this.taxableFlag = taxableFlag;
    }
    private java.math.BigDecimal quantity;

    public java.math.BigDecimal getquantity() {
        return quantity;
    }

    public void setquantity(java.math.BigDecimal quantity) {
        this.quantity = quantity;
    }
    private java.math.BigDecimal amount;

    public java.math.BigDecimal getamount() {
        return amount;
    }

    public void setamount(java.math.BigDecimal amount) {
        this.amount = amount;
    }
    private java.lang.String description;

    public java.lang.String getdescription() {
        return description;
    }

    public void setdescription(java.lang.String description) {
        this.description = description;
    }
    private java.lang.String taxAuthPartyId;

    public java.lang.String gettaxAuthPartyId() {
        return taxAuthPartyId;
    }

    public void settaxAuthPartyId(java.lang.String taxAuthPartyId) {
        this.taxAuthPartyId = taxAuthPartyId;
    }
    private java.lang.String taxAuthGeoId;

    public java.lang.String gettaxAuthGeoId() {
        return taxAuthGeoId;
    }

    public void settaxAuthGeoId(java.lang.String taxAuthGeoId) {
        this.taxAuthGeoId = taxAuthGeoId;
    }
    private java.lang.String taxAuthorityRateSeqId;

    public java.lang.String gettaxAuthorityRateSeqId() {
        return taxAuthorityRateSeqId;
    }

    public void settaxAuthorityRateSeqId(java.lang.String taxAuthorityRateSeqId) {
        this.taxAuthorityRateSeqId = taxAuthorityRateSeqId;
    }
    private java.lang.String salesOpportunityId;

    public java.lang.String getsalesOpportunityId() {
        return salesOpportunityId;
    }

    public void setsalesOpportunityId(java.lang.String salesOpportunityId) {
        this.salesOpportunityId = salesOpportunityId;
    }
    private java.sql.Timestamp lastUpdatedStamp;

    public java.sql.Timestamp getlastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setlastUpdatedStamp(java.sql.Timestamp lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
    private java.sql.Timestamp lastUpdatedTxStamp;

    public java.sql.Timestamp getlastUpdatedTxStamp() {
        return lastUpdatedTxStamp;
    }

    public void setlastUpdatedTxStamp(java.sql.Timestamp lastUpdatedTxStamp) {
        this.lastUpdatedTxStamp = lastUpdatedTxStamp;
    }
    private java.sql.Timestamp createdStamp;

    public java.sql.Timestamp getcreatedStamp() {
        return createdStamp;
    }

    public void setcreatedStamp(java.sql.Timestamp createdStamp) {
        this.createdStamp = createdStamp;
    }
    private java.sql.Timestamp createdTxStamp;

    public java.sql.Timestamp getcreatedTxStamp() {
        return createdTxStamp;
    }

    public void setcreatedTxStamp(java.sql.Timestamp createdTxStamp) {
        this.createdTxStamp = createdTxStamp;
    }

    @Override
    public String[][] getColumnNameId() {
        return ColumnNameId;
    }
    private BigDecimal totalItemAmount = BigDecimal.ZERO;

    public BigDecimal getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(BigDecimal totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    public void calculateTotalItemAmount() {

        if (amount != null && quantity != null) {
            this.setTotalItemAmount(amount.multiply(quantity).setScale(scale, rounding));
        }
    }

    private List<InvoiceItem> relatedItems = new ArrayList<InvoiceItem>();

    public List<InvoiceItem> getRelatedItems() {
        return relatedItems;
    }

    public void setRelatedItems(List<InvoiceItem> relatedItems) {
        this.relatedItems = relatedItems;
    }

    public void addRelatedItem(InvoiceItem item) {
        relatedItems.add(item);
    }
    BigDecimal discount = BigDecimal.ZERO;

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    BigDecimal tax = BigDecimal.ZERO;

    public void calculateTaxAndDiscount() {
        for (InvoiceItem relVal : relatedItems) {
            Debug.logInfo("item type: " + relVal.getInvoiceItemTypeId(), module);
            Debug.logInfo("relVal.getAmount(): " + relVal.getAmount(), module);
            if (relVal.getInvoiceItemTypeId().equalsIgnoreCase("ITM_DISCOUNT_ADJ")) {
                discount = discount.add(relVal.getAmount());
            } else if (relVal.getInvoiceItemTypeId().equalsIgnoreCase("ITM_SALES_TAX")) {
                tax = tax.add(relVal.getAmount());
            }

            /*else if(relVal.getInvoiceItemTypeId().equalsIgnoreCase("ITM_PROMOTION_ADJ")){
             Debug.logInfo("current.getAmount(): " + this.getAmount(), module);                
             this.amount = this.amount.add(relVal.getAmount());
                
             Debug.logInfo("relVal.getAmount(): " + relVal.getAmount(), module);                                
             Debug.logInfo("this.getAmount(): " + this.getAmount(), module);                
             }*/
        }
    }

    void caculateGST() {
        GenericValue product = null;
        tax = BigDecimal.ZERO;
        if (UtilValidate.isNotEmpty(productId)) {
            try {
                product = ProductWorker.findProduct(ControllerOptions.getSession().getDelegator(), productId);
                if (product != null) {
                    if ("Y".equals(product.getString("taxable")) && getAmount() != null) {
                        tax = tax.add(getAmount().multiply(BigDecimal.valueOf(0.1)));//.setScale(scale, RoundingMode.UP);
                    }

                    if (quantity == null) {                       
                        tax = BigDecimal.ZERO;
                    }

                }
            } catch (GenericEntityException e) {
                Debug.logError(e, module);

            }
        }
    }

    public BigDecimal getTotalGST() {
        BigDecimal totalGST = BigDecimal.ZERO;
        if (quantity != null) {
            totalGST = tax.multiply(quantity).setScale(scale, RoundingMode.UP);
        } else {
            totalGST = BigDecimal.ZERO;
        }
        return totalGST;
    }

//    @Override
    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
        Collection<InvoiceItem> objectList = new ArrayList<InvoiceItem>();
        for (GenericValue genVal : genList) {
            objectList.add(new InvoiceItem(genVal));
        }
        return objectList;
    }
    
    @Override
    public Map<String, Object> getValuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
