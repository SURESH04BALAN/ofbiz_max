/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.price;

import java.util.ArrayList;
import java.util.List;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductPrice;

/**
 *
 * @author BBS Auctions
 */
public class ProductPriceBulkUpdate {

    private List<ProductPrice> defaultPrice = new ArrayList<ProductPrice>();
    private List<ProductPrice> listPrice = new ArrayList<ProductPrice>();
    private List<ProductPrice> avgCost = new ArrayList<ProductPrice>();
    private Product product;
    
    public ProductPriceBulkUpdate(Product product ){
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductPrice> getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(List<ProductPrice> defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public List<ProductPrice> getListPrice() {
        return listPrice;
    }

    public void setListPrice(List<ProductPrice> listPrice) {
        this.listPrice = listPrice;
    }

    public List<ProductPrice> getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(List<ProductPrice> avgCost) {
        this.avgCost = avgCost;
    }
    
    
    
}
