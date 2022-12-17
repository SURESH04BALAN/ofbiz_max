/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.util.HashMap;
import java.util.Map;
import org.ofbiz.base.util.UtilValidate;

/**
 *
 * @author siranjeev
 */
public class ProductItemPrice {

    public ProductItemPrice() {
    }
    Map<String, ProductPriceList> productPriceListByType = new HashMap<String, ProductPriceList>();/*
     private ProductPriceList averageCost;
     private ProductPriceList boxPrice;
     private ProductPriceList competitivePrice;
     private ProductPriceList defaultPrice;
     private ProductPriceList listPrice;
     private ProductPriceList maximumPrice;
     private ProductPriceList minimumOrderPrice;
     private ProductPriceList minimumPrice;
     private ProductPriceList promoPrice;
     private ProductPriceList specialPromoPrice;
     private ProductPriceList wholesalePrice;  
     */



    public void addProductPrice(ProductPriceComposite prodPriceComp) throws Exception {        
        if (UtilValidate.isNotEmpty(prodPriceComp.getProductPrice().getproductPriceTypeId())){
            ProductPriceList productPriceList = null;
            if(productPriceListByType.containsKey(prodPriceComp.getProductPrice().getproductPriceTypeId())==false) {
                productPriceList = new ProductPriceList();
                setProductPriceList(prodPriceComp.getProductPrice().getproductPriceTypeId(), productPriceList);                
            }
            else{
                productPriceList = productPriceListByType.get(prodPriceComp.getProductPrice().getproductPriceTypeId());
            }
            productPriceList.add(prodPriceComp);                                        
        }
        else{
            throw new Exception("Invalid Product Price Type Id: " + prodPriceComp.getProductPrice().getproductPriceTypeId());
        }
    }
    
    public ProductPriceList getProductPriceList(String productPriceType) {
        if (productPriceListByType.containsKey(productPriceType)) {
            return productPriceListByType.get(productPriceType);
        }

        return null;
    }

    void setProductPriceList(String productPriceType, ProductPriceList productPriceList) {

        productPriceListByType.put(productPriceType, productPriceList);
    }

    public ProductPriceList getAverageCost() {
        return getProductPriceList("AVERAGE_COST");
    }

    public void setAverageCost(ProductPriceList averageCost) {
        setProductPriceList("AVERAGE_COST", averageCost);
    }

    public ProductPriceList getBoxPrice() {
        return getProductPriceList("BOX_PRICE");

    }

    public void setBoxPrice(ProductPriceList boxPrice) {
        setProductPriceList("BOX_PRICE", boxPrice);
    }

    public ProductPriceList getCompetitivePrice() {
        return getProductPriceList("COMPETITIVE_PRICE");

    }

    public void setCompetitivePrice(ProductPriceList competitivePrice) {
        setProductPriceList("COMPETITIVE_PRICE", competitivePrice);
    }

    public ProductPriceList getDefaultPrice() {
        return getProductPriceList("DEFAULT_PRICE");

    }

    public void setDefaultPrice(ProductPriceList defaultPrice) {
        setProductPriceList("DEFAULT_PRICE", defaultPrice);
    }

    public ProductPriceList getListPrice() {
        return getProductPriceList("LIST_PRICE");

    }

    public void setListPrice(ProductPriceList listPrice) {
        setProductPriceList("LIST_PRICE", listPrice);
    }

    public ProductPriceList getMaximumPrice() {
        return getProductPriceList("MAXIMUM_PRICE");
    }

    public void setMaximumPrice(ProductPriceList maximumPrice) {
        setProductPriceList("MAXIMUM_PRICE", maximumPrice);
    }

    public ProductPriceList getMinimumOrderPrice() {
        return getProductPriceList("MINIMUM_ORDER_PRICE");

    }

    public void setMinimumOrderPrice(ProductPriceList minimumOrderPrice) {
        setProductPriceList("MINIMUM_ORDER_PRICE", minimumOrderPrice);
    }

    public ProductPriceList getMinimumPrice() {
        return getProductPriceList("MINIMUM_PRICE");

    }

    public void setMinimumPrice(ProductPriceList minimumPrice) {
        setProductPriceList("MINIMUM_PRICE", minimumPrice);
    }

    public ProductPriceList getPromoPrice() {
        return getProductPriceList("PROMO_PRICE");
    }

    public void setPromoPrice(ProductPriceList promoPrice) {
        setProductPriceList("PROMO_PRICE", promoPrice);
    }

    public ProductPriceList getSpecialPromoPrice() {
        return getProductPriceList("SPECIAL_PROMO_PRICE");

    }

    public void setSpecialPromoPrice(ProductPriceList specialPromoPrice) {
        setProductPriceList("SPECIAL_PROMO_PRICE", specialPromoPrice);
    }

    public ProductPriceList getWholesalePrice() {
        return getProductPriceList("WHOLESALE_PRICE");

    }

    public void setWholesalePrice(ProductPriceList wholesalePrice) {
        setProductPriceList("WHOLESALE_PRICE", wholesalePrice);
    }

    public ProductPriceList getProductPriceList() {

        ProductPriceList productPriceList = new ProductPriceList();
        for (Map.Entry<String, ProductPriceList> anEntry : productPriceListByType.entrySet()) {
            productPriceList.addAll(anEntry.getValue().getList());
        }
        return productPriceList;

    }

}
