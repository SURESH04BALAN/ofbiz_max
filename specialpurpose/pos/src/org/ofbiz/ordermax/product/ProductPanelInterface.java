/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.product;

import java.util.Map;
import javax.swing.JTextField;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.composite.ProductComposite;

/**
 *
 * @author BBS Auctions
 */
public interface ProductPanelInterface {

    void clearDialogFields();

    void getDialogField();

    ProductComposite getProductComposite();

    JTextField getProductIdTextField();

    boolean isIsEnable();

    void setDialogField();

    void setIsEnable(boolean isEnable);

    void setProductComposite(ProductComposite productComposite);
    void setProductTreePath(Map<Integer,TreeNode>treePath);
    boolean isNeedSavingPrices();
}
