/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.tree;

import javax.swing.tree.TreeModel;
import org.ofbiz.ordermax.base.KeyTreeNodeInterface;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.TreeNode;

/**
 *
 * @author siranjeev
 */
public class ProductTreeModel implements TreeModel  {

    private KeyTreeNodeInterface root;
   
    public ProductTreeModel(KeyTreeNodeInterface root) {
        this.root = root;
    }

    @Override
    public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
        //do nothing
    }

    @Override
    public Object getChild(Object parent, int index) {
        KeyTreeNodeInterface node = (KeyTreeNodeInterface) parent;
        return node.getChild(index);//.listNodes().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        KeyTreeNodeInterface f = (KeyTreeNodeInterface) parent;

        return f.getChildCount();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        TreeNode par = (TreeNode) parent;
        return par.getIndexOfChild(child);
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        KeyTreeNodeInterface f = (KeyTreeNodeInterface) node;
        return !f.isHasChildrean();
    }

    @Override
    public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
        //do nothing
    }

    @Override
    public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue) {
        //do nothing
    }
    
    public boolean loadChildNodes(KeyTreeNodeInterface node)
    {
        return true;
    }
}