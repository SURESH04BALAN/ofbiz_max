/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public interface KeyEntityInterface {
    public GenericValue creatGenericValue(Delegator delegator) throws GenericEntityException ;
    public GenericValue loadDetails(String id, Delegator delegator) throws GenericEntityException ;
    public boolean saveDetails(GenericValue valString, Delegator delegator) throws GenericEntityException ;    
}

