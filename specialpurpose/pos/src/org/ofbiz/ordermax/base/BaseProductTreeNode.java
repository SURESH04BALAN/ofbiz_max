/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

/**
 *
 * @author siranjeev
 */
public class BaseProductTreeNode extends TreeNode {

    protected boolean virtual = false;

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public boolean isVariant() {
        return variant;
    }

    public void setVariant(boolean variant) {
        this.variant = variant;
        Debug.log("Set Variant: " + this.variant);
    }
    protected boolean variant = false;    
/*
    public BaseProductTreeNode(String string1, String string2, boolean isLeaf) {
        super(string1, string2, isLeaf);
    }
    */
    public BaseProductTreeNode(String string1, String string2, boolean isLeaf, GenericValue dataObject) {
        super(string1, string2, isLeaf, dataObject);

    }
    @Override
    public GenericValue creatGenericValue(Delegator delegator) throws GenericEntityException {
        throw new IllegalArgumentException("creatGenericValue() : Not Implemented");
    }

    @Override
    public GenericValue loadDetails(String id, Delegator delegator) throws GenericEntityException {
        throw new IllegalArgumentException("loadDetails() : Not Implemented");
    }

    @Override
    public boolean saveDetails(GenericValue val, Delegator delegator) throws GenericEntityException {
        throw new IllegalArgumentException("saveDetails(): Not Implemented");
    }

    public static GenericValue getProductCategoryRollup(String productCategoryId, String parentProductCategoryId, Delegator delegator) {

        return PosProductHelper.getProductCategoryRollup(productCategoryId, parentProductCategoryId, delegator);

    }

    public static GenericValue createProductCategoryRollup(String productCategoryId, String parentProductCategoryId, Delegator delegator) {
        return PosProductHelper.createProductCategoryRollup(productCategoryId, parentProductCategoryId, delegator);
    }

    public static GenericValue getProductCategory(String productCategoryId, Delegator delegator) {
        return PosProductHelper.getProductCategory(productCategoryId, delegator);
    }

    public static GenericValue createProductCategory(String productCategoryId, String primaryParentCategoryId,
            String productCategoryTypeId, String categoryName, Delegator delegator) {

        return PosProductHelper.createProductCategory(productCategoryId, primaryParentCategoryId, productCategoryTypeId, categoryName, null, null, null, delegator);
    }

    public static GenericValue getProductCategoryMember(String productId, String productCategoryId, Delegator delegator) {
        return PosProductHelper.getProductCategoryMember(productId, productCategoryId, delegator);
    }

    public static GenericValue createProductCategoryMember(String productId, String productCategoryId, Delegator delegator) {
        return PosProductHelper.createProductCategoryMember(productId, productCategoryId, delegator);
        // product price
    }

    public static GenericValue createProductContent(String productId, String contentId, Delegator delegator) {
        return PosProductHelper.createProductContent(productId, contentId, delegator);
    }

    public static GenericValue createProductDimension(String productId, String dimensionId, String internalName, Delegator delegator) {
        return PosProductHelper.createProductDimension(productId, dimensionId, internalName, delegator);
    }
}
