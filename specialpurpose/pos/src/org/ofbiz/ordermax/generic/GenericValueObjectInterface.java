/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public interface GenericValueObjectInterface extends GenericValueInterface{
    public void getGenericValue();
    public void setGenericValue() throws Exception;
    public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator);
    public boolean isGenericValueSet();
    public String[][] getColumnNameId();      
}