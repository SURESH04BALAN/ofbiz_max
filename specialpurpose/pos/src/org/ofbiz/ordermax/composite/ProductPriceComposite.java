/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.entity.ProductPriceType;

/**
 *
 * @author siranjeev
 */
public class ProductPriceComposite {
    private ProductPriceType productPriceType;
    private ProductPrice productPrice;

    public ProductPriceType getProductPriceType() {
        return productPriceType;
    }

    public void setProductPriceType(ProductPriceType productPriceType) {
        this.productPriceType = productPriceType;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public ProductPriceComposite() {
    }

}
