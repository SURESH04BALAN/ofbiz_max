/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import org.ofbiz.ordermax.base.BaseProductTreeNode;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public class BrandTreeNode extends BaseProductTreeNode {

    public static final String BrandNodeName = BrandTreeNode.class.getName();
/*
    public BrandTreeNode(String string1, String string2, boolean isLeaf) {
        super(string1, string2, isLeaf);
    }
*/
    public BrandTreeNode(String string1, String string2, boolean isLeaf, GenericValue dataObject) {
        super(string1, string2, isLeaf, dataObject);

    }

    public String getNodeName() {
        return BrandNodeName;
    }

    @Override
    public GenericValue creatGenericValue(Delegator delegator) throws GenericEntityException {
        return delegator.makeValue("ProductCategory");
    }

    @Override
    public GenericValue loadDetails(String id, Delegator delegator) throws GenericEntityException {
        return delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", id));
    }

    @Override
    public boolean saveDetails(GenericValue genericVal, Delegator delegator) throws GenericEntityException {
        genericVal.create();
        delegator.store(genericVal);
        return true;
    }

}
