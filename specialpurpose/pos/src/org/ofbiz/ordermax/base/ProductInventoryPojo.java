package org.ofbiz.ordermax.base;

import java.math.BigDecimal;


public class ProductInventoryPojo extends ProductPojo {
	public String inventoryItemId;
	public BigDecimal quantityOnHand = BigDecimal.ZERO;
	public BigDecimal availableToPromise;
	public BigDecimal inventoryValue;
	public BigDecimal minimumStock;
	public BigDecimal reorderQuantity;	
	public Long daysToShip;
	public java.sql.Timestamp priceFromDate;
	public java.sql.Timestamp expireDate;
	public String productStoreGroupId;
	
	public String inventoryItemTypeId;
	public String ownerPartyId;	
	public String inventoryGlAccountId; 
	public String offsettingGlAccountId; 
	public String acctgTransId;
	public boolean isQuantityOnHandModified = false;
	public boolean isProductModified = false;
        public String orderId;
        public String orderItemSeqId;
        
}
