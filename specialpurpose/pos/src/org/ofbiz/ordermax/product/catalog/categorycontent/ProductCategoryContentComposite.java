/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog.categorycontent;

import javax.swing.ImageIcon;
import org.ofbiz.ordermax.entity.Content;
import org.ofbiz.ordermax.entity.DataResource;
import org.ofbiz.ordermax.entity.ProductCategoryContent;
//import org.ofbiz.ordermax.entity.ProductCategoryContent;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryContentComposite {

    private Content content = new Content();
    private ProductCategoryContent productContent = new ProductCategoryContent();
    private DataResource dataResource = new DataResource();

    public DataResource getDataResource() {
        return dataResource;
    }

    public void setDataResource(DataResource dataResource) {
        this.dataResource = dataResource;
    }
    
    public ProductCategoryContent getProductCategoryContent() {
        return productContent;
    }

    public void setProductCategoryContent(ProductCategoryContent productContent) {
        this.productContent = productContent;
    }
    
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    
    ImageIcon icon = null;  

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
    
}
