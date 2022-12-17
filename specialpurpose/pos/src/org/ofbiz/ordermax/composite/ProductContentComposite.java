/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import javax.swing.ImageIcon;
import org.ofbiz.ordermax.entity.Content;
import org.ofbiz.ordermax.entity.DataResource;
import org.ofbiz.ordermax.entity.ProductContent;

/**
 *
 * @author siranjeev
 */
public class ProductContentComposite {

    private Content content = new Content();
    private ProductContent productContent = new ProductContent();
    private DataResource dataResource = new DataResource();

    public DataResource getDataResource() {
        return dataResource;
    }

    public void setDataResource(DataResource dataResource) {
        this.dataResource = dataResource;
    }
    
    public ProductContent getProductContent() {
        return productContent;
    }

    public void setProductContent(ProductContent productContent) {
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
