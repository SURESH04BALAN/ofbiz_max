/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.base.BaseProductTreeNode;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.BaseProductTreeNode;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author siranjeev
 */
public class ProductTreeNode extends BaseProductTreeNode {

    public static final String ProdutNodeName = ProductTreeNode.class.getName();

    public ProductTreeNode(String string1, String string2, boolean isLeaf, GenericValue dataObject) {
        super(string1, string2, isLeaf, dataObject);
        hasChildrean = false;
        childreanLoaded = true;
        loaded = true;
    }

    @Override
    public String getNodeName() {
        return ProdutNodeName;
    }

    @Override
    public GenericValue creatGenericValue(Delegator delegator) throws GenericEntityException {
        return delegator.makeValue("Product");
    }

    @Override
    public GenericValue loadDetails(String id, Delegator delegator) throws GenericEntityException {
        return delegator.findOne("Product", UtilMisc.toMap("productId", id), false);
    }

    public static GenericValue loadProductDetail(String id) throws GenericEntityException {
        return ControllerOptions.getSession().getDelegator().findOne("Product", UtilMisc.toMap("productId", id), false);
    }

    @Override
    public boolean saveDetails(GenericValue genericVal, Delegator delegator) throws GenericEntityException {
        genericVal.create();
        delegator.store(genericVal);
        return true;
    }
}
