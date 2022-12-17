package org.ofbiz.ordermax.base;

import java.math.BigDecimal;

public class ProductPojo {

    public String productId = null;
    public String productTypeId;
    public String internalName;
    public String description;
    public String longDescription;
    public String productName;
    public String brandName;
    public String comments;
    public String smallImageUrl;
    public String mediumImageUrl;
    public String largeImageUrl;
    public String detailImageUrl;
    public String originalImageUrl;

    public String heightUomId;

    public BigDecimal productLength;
    public String productLengthUomId;
    public BigDecimal width;
    public String widthUomId;
    public BigDecimal weight;
    public String weightUomId;

    public String taxable = "N";
    public String isVirtual = "N";
    public String isVariant = "N";
    public String isInactive = "N";
    public String returnable = "Y";
    public String chargeShipping = "N";
    public String autoCreateKeywords = "N";
    public String includeInPromotions = "Y";
    public String requirementMethodEnumId = "PRODRQM_AUTO";

    public java.sql.Timestamp createdDate;

    public String productPriceTypeId;

    public String eanValue;
    public String skuValue;
    public String productFeature1;
    public String supplierPartyId;
    public String supplierPartyDesc;
    public String requireInventory = "Y";
    public String inventoryMessage = "Confirm it with Suresh";
    public String goodIdentificationTypeId1;
    public boolean isEanValueModified = false;
    public String goodIdentificationTypeId2;
    public String productPricePurposeId;
    public String currencyUomId;
    public String supplierProductId;

    public BigDecimal quantity = BigDecimal.ZERO;
    public BigDecimal totalCost = BigDecimal.ZERO;
    public BigDecimal height = BigDecimal.ZERO;

    public BigDecimal price = BigDecimal.ZERO;
    public BigDecimal purchasePrice = BigDecimal.ZERO;
    public BigDecimal defaultPrice = BigDecimal.ZERO;

    public BigDecimal minimumOrderQuantity = BigDecimal.ZERO;
    public BigDecimal supplierLastPrice = BigDecimal.ZERO;
    public boolean isProductExists = false;
    public boolean isPriceExists = false;
    public boolean isFeatureExists = false;
    public boolean isSupplierProductExists = false;
    public boolean isInventoryExists = false;

    public String productAverageCostTypeId = "SIMPLE_AVG_COST";
    public java.sql.Timestamp fromDate;
//		public BigDecimal averageCost;
    public String facilityId;
    public String organizationPartyId;
    public String categoryId;
    public String categoryName = "Need Category Name - suresh";
    public String brandId;

    public boolean isPriceModiefied = false;
    public boolean isPurchasePriceModified = false;
    public boolean isDefaultPriceModified = false;
    public Long piecesIncluded = new Long(0);
}
