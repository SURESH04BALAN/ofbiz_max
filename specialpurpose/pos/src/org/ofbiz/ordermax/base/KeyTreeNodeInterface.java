/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.util.HashMap;
import java.util.TreeMap;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public interface KeyTreeNodeInterface {
    public boolean isHasChildrean();
    public boolean isChildrenLoaded();    ;    
    public TreeMap<Key, TreeNode> listNodes();
    public boolean addNodes(Key key, Object val);    
    public int getChildCount();
    public Object getChild(int index) ;
    public int getIndexOfChild(Object child);
}
