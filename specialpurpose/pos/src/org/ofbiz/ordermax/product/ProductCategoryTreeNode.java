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

/**
 *
 * @author siranjeev
 */
public class ProductCategoryTreeNode extends BaseProductTreeNode {
    public static final String ProductCategoryTreeNodeName = ProductCategoryTreeNode.class.getName();
/*    
    public ProductCategoryTreeNode(String string1, String string2, boolean isLeaf) {
        super(string1, string2, isLeaf);     
    }
 */         
         public ProductCategoryTreeNode(String string1, String string2, boolean isLeaf, GenericValue dataObject) {
        super(string1, string2, isLeaf, dataObject);

    }
    @Override
    public String getNodeName(){
        return ProductCategoryTreeNodeName;
    }
    
@Override
    public GenericValue creatGenericValue(Delegator delegator) throws GenericEntityException  {
        return delegator.makeValue("ProductCategory");
    }

    @Override
    public GenericValue loadDetails(String id, Delegator delegator) throws GenericEntityException 
    {
        return delegator.findByPrimaryKey("ProductCategory",  UtilMisc.toMap("productCategoryId", id));	             
    }

    @Override
    public boolean saveDetails(GenericValue genericVal, Delegator delegator) throws GenericEntityException {
        genericVal.create();
        delegator.store(genericVal);
        return true;
    }    
}
