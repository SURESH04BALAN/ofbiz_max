/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author siranjeev
 */
public class TreeNode extends Key implements KeyTreeNodeInterface, KeyEntityInterface/*, javax.swing.tree.TreeNode*/ {

    static public String TreeNodeName = TreeNode.class.getName();
    protected String parentCatId = null;
    protected boolean hasChildrean = true;
    protected boolean childreanLoaded = false;
    protected TreeMap<Key, TreeNode> childList = new TreeMap<Key, TreeNode>();
    public TreePath tp = null;
    protected GenericValue genericValue = null;
    protected boolean loaded = false;
    protected ImageIcon icon = null;

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void setParentCatId(String parentCatId) {
        this.parentCatId = parentCatId;
    }

    public String getParentCatId() {
        return parentCatId;
    }

    public TreeNode(String string1, String string2, boolean isLeaf, GenericValue genericValue) {
        super(string1, string2);
        this.genericValue = genericValue;
//        hasChildrean = !isLeaf;

    }

    public boolean isHasChildrean() {
        return hasChildrean;
    }

    public void setHasChildrean(boolean val) {
        hasChildrean = val;
    }

    public boolean isChildrenLoaded() {
        return childreanLoaded;
    }

    public TreeMap<Key, TreeNode> listNodes() {
        return childList;
    }

    public boolean addNodes(Key key, Object val) {
        childList.put(key, (TreeNode) val);
        childreanLoaded = true;
        hasChildrean = childList.isEmpty() == false;
        return true;
    }

    public boolean clearChildNodes() {
        for (Map.Entry<Key, TreeNode> entryBrand : childList.entrySet()) {
            entryBrand.getValue().clearChildNodes();
        }
        childList.clear();

        childreanLoaded = false;
        hasChildrean = childList.isEmpty() == false;
        loaded = false;
        return true;
    }

    public boolean setChildrenNodes(List<TreeNode> list) {
        childList.clear();
        for (TreeNode child : list) {
            childList.put(new Key(child._id, child._name), (TreeNode) child);
        }
        childreanLoaded = true;
        hasChildrean = childList.isEmpty() == false;
        return true;
    }

    public int getChildCount() {
        return childList.size();
    }

    public Object getChild(int index) {
        int count = 0;
        Object obj = null;
        for (Map.Entry<Key, TreeNode> entryBrand : childList.entrySet()) {
            if (count++ == index) {
                obj = entryBrand.getValue();
                break;
            }
        }
        return obj;
    }

    public TreeNode getNodeFromId(Key key) {
        TreeNode node = null;
//       Debug.logInfo("getNodeFromId: key" + key + " this key" + this._key + " list size: " + childList.size(), _id);
        for (Map.Entry<Key, TreeNode> entryBrand : childList.entrySet()) {
            if (entryBrand.getValue().compareTo(key) == 0) {
                Debug.logInfo("getNodeFromId found : " + entryBrand.getValue(), _id);
                return entryBrand.getValue();
            } else {
//                Debug.logInfo("getNodeFromId Not found ", _id);                   
                node = entryBrand.getValue().getNodeFromId(key);
                if (node != null) {
                    return node;
                }
            }
        }
        return node;
    }

    @Override
    public int getIndexOfChild(Object child) {
        int count = 0;
        TreeNode node = (TreeNode) child;
        for (Map.Entry<Key, TreeNode> entryBrand : childList.entrySet()) {
            count++;
            if (entryBrand.getValue()._key.equals(node._key)) {
                break;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return _name;
    }

    public String getNodeName() {
        return TreeNodeName;
    }

    public GenericValue creatGenericValue(Delegator delegator) throws GenericEntityException {
        throw new IllegalArgumentException("creatGenericValue() : Not Implemented");
    }

    public GenericValue loadDetails(String id, Delegator delegator) throws GenericEntityException {
        throw new IllegalArgumentException("loadDetails() : Not Implemented");
    }

    public boolean saveDetails(GenericValue val, Delegator delegator) throws GenericEntityException {
        throw new IllegalArgumentException("saveDetails(): Not Implemented");
    }

    public GenericValue getGenericValue() {
        return genericValue;
    }

    public void setGenericValue(GenericValue genericValue) {
        this.genericValue = genericValue;
    }
    //user data
    protected Object data = null;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private String imageName;

    public String getImagePath() {
        return imageName;
    }

    public void setImagePath(String data) {
        this.imageName = data;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

}
