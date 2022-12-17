/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productloader;

import java.util.List;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.product.ProductTreeNode;

/**
 *
 * @author PospointAus
 */
public interface ProductDataLoaderInterace {
    List<TreeNode> getAllProductsTreeNodes();
    List<Product> getAllProducts();   
    //Product getProduct(String productId);
    //ProductTreeNode getProductTreeNode(String productId);    
}
