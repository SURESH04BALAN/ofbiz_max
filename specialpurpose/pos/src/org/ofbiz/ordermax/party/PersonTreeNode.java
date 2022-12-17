/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.TreeNode;

/**
 *
 * @author siranjeev
 */
public class PersonTreeNode extends TreeNode{
    
    public static final String PersonNodeName = PersonTreeNode.class.getName();

    /*public PersonTreeNode(String string1, String string2, boolean isLeaf) {
        super(string1, string2, isLeaf);
    }*/
    public PersonTreeNode(String string1, String string2, boolean isLeaf, GenericValue dataObject) {
        super(string1, string2, isLeaf, dataObject);

    }
    @Override
    public String getNodeName() {
        return PersonNodeName;
    }

    @Override
    public GenericValue creatGenericValue(Delegator delegator) throws GenericEntityException {
        return delegator.makeValue("RoleType");
    }

    @Override
    public GenericValue loadDetails(String id, Delegator delegator) throws GenericEntityException {
        return delegator.findByPrimaryKey("RoleType", UtilMisc.toMap("roleTypeId", id));
    }

    @Override
    public boolean saveDetails(GenericValue genericVal, Delegator delegator) throws GenericEntityException {
        genericVal.create();
        delegator.store(genericVal);
        return true;
    }    
}
