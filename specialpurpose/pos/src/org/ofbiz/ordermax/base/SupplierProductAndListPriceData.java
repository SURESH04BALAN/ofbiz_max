/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public class SupplierProductAndListPriceData {

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    private GenericValue productValue = null;
    private List<GenericValue> listPriceValue = null;
    private List<GenericValue> defaultPriceValue = null;
    private List<GenericValue> avgCostValue = null;
    private List<GenericValue> supplierProduct = null;
    private GenericValue scanCode = null;
    Map<String, Object> dataMap = null;
    Map<String, BigDecimal> valueMap = new HashMap<String, BigDecimal>();

    public SupplierProductAndListPriceData() {
    }

    public SupplierProductAndListPriceData(Map<String, Object> dataList) {
        dataMap = dataList;
        if (dataMap.containsKey("Product")) {
            productValue = (GenericValue) dataMap.get("Product");
        }
        if (dataMap.containsKey(PosProductHelper.ProductPriceTypeId_LISTPRICE)) {
            listPriceValue = (List<GenericValue>) dataMap.get(PosProductHelper.ProductPriceTypeId_LISTPRICE);
        }

        if (dataMap.containsKey(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE)) {
            defaultPriceValue = (List<GenericValue>) dataMap.get(PosProductHelper.ProductPriceTypeId_DEFAULTPRICE);
        }

        if (dataMap.containsKey(PosProductHelper.ProductPriceTypeId_AVERAGECOST)) {
            avgCostValue = (List<GenericValue>) dataMap.get(PosProductHelper.ProductPriceTypeId_AVERAGECOST);
        }

        if (dataMap.containsKey(PosProductHelper.SUPPLIERPRODUCT)) {
            supplierProduct = (List<GenericValue>) dataMap.get(PosProductHelper.SUPPLIERPRODUCT);
        }

        if (dataMap.containsKey(PosProductHelper.GoodIdentificationTypeIdEAN)) {
            scanCode = (GenericValue) dataMap.get(PosProductHelper.GoodIdentificationTypeIdEAN);
        }

        valueMap.put("availableToPromiseTotal", BigDecimal.ZERO);
        valueMap.put("quantityOnHandTotal", BigDecimal.ZERO);

    }

    public void setInventoryMap(Map<String, Object> invMap) {

        BigDecimal availableToPromiseTotal = (BigDecimal) invMap.get("availableToPromiseTotal");
    
        BigDecimal quantityOnHandTotal = (BigDecimal) invMap.get("quantityOnHandTotal");

        if (quantityOnHandTotal != null) {
            valueMap.put("quantityOnHandTotal", quantityOnHandTotal);
        }

        if (availableToPromiseTotal != null) {
            valueMap.put("availableToPromiseTotal", availableToPromiseTotal);
        }
    }

    public String getProductInternalName() {
        return productValue.getString("internalName");
    }

    private BigDecimal getValue(List<GenericValue> list, String name) {
        if (list != null && list.size() > 0) {
            return list.get(0).getBigDecimal("price");

        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getListPrice() {
        return getValue(listPriceValue, "price");
    }

    public BigDecimal getDefaultPrice() {
        return getValue(defaultPriceValue, "price");
    }

    public BigDecimal getAvgCost() {
        return getValue(avgCostValue, "price");
//        return avgCostValue.getBigDecimal("price");
    }

    public String getProductId() {
        return productValue.getString("productId");
    }

    public String getSupplierId() {
        if (supplierProduct != null && supplierProduct.size() > 0) {
            return supplierProduct.get(0).getString("partyId");
        } else {
            return "";
        }
    }

    public BigDecimal getLastPrice() {
        if (supplierProduct != null && supplierProduct.size() > 0) {
            return supplierProduct.get(0).getBigDecimal("lastPrice");

        } else {
            return BigDecimal.ZERO;
        }
    }

    public String getSupplierProductId() {

        if (supplierProduct != null && supplierProduct.size() > 0) {
            return supplierProduct.get(0).getString("supplierProductId");
        } else {
            return "";
        }
    }

    public String getScanCode() {
        if (scanCode == null) {
            return "";
        } else {
            return scanCode.getString("idValue");
        }
    }

    public BigDecimal getQuantityOnHandTotal() {
        return valueMap.get("quantityOnHandTotal");
    }

    public BigDecimal getAvailableToPromiseTotal() {
        return valueMap.get("availableToPromiseTotal");
    }

    public void setProductInternalName(String aa) {
        productValue.setString("internalName", aa);
    }

    private void setValue(List<GenericValue> list, String name, BigDecimal value) {
        if (list != null && list.size() > 0) {
            list.get(0).set(name, value);
        }
    }

    private void setValue(List<GenericValue> list, String name, String value) {
        if (list != null && list.size() > 0) {
            list.get(0).set(name, value);
        }
    }

    public void setListPrice(BigDecimal macName) {
        setValue(listPriceValue, "price", macName);
    }

    public void setDefaultPrice(BigDecimal cc) {
        setValue(defaultPriceValue, "price", cc);
    }

    public void setAvgCost(BigDecimal dd) {
        setValue(avgCostValue, "price", dd);
    }

    public void setProductId(String ss) {
        productValue.setString("productId", ss);
    }

    public void setSupplierId(String dd) {
        setValue(supplierProduct, "partyId", dd);
    }

    public void setLastPrice(BigDecimal dd) {
        setValue(supplierProduct, "lastPrice", dd);
    }

    public void setSupplierProductId(String dd) {
        setValue(supplierProduct, "supplierProductId", dd);
    }

    public void setScanCode(String dd) {
        scanCode.set("idValue", dd);
    }

    public GenericValue getSupplierProduct(String partyId) {
        GenericValue suppVal = null;
        for (GenericValue val : supplierProduct) {
            if (val.getString("partyId").equalsIgnoreCase(partyId)) {
                suppVal = val;
                break;
            }
        }

        return suppVal;
    }

    private GenericValue getGVItem(List<GenericValue> list) {
        if (list != null && list.size() > 0) {
            return list.get(0);

        } else {
            return null;
        }
    }

    public GenericValue getProductValue() {
        return productValue;
    }

    public GenericValue getListPriceValue() {
        return getGVItem(listPriceValue);
    }

    public GenericValue getDefaultPriceValue() {
        return getGVItem(defaultPriceValue);
    }

    public GenericValue getAvgCostValue() {
        return getGVItem(avgCostValue);
    }

    public GenericValue getSupplierProduct() {
        return getGVItem(supplierProduct);
    }

    public List<GenericValue> getListPriceValueList() {
        return listPriceValue;
    }

    public List<GenericValue> getDefaultPriceValueList() {
        return defaultPriceValue;
    }

    public List<GenericValue> getAvgCostValueList() {
        return avgCostValue;
    }

    public List<GenericValue> getSupplierProductList() {
        return supplierProduct;
    }

    /*    private GenericValue productValue = null;
     private List<GenericValue> listPriceValue = null;
     private List<GenericValue> defaultPriceValue = null;
     private List<GenericValue> avgCostValue = null;
     private List<GenericValue> supplierProduct = null;
     private GenericValue scanCode = null;
     */
}
