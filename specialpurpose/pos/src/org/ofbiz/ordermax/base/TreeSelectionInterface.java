/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTree;
import org.ofbiz.ordermax.base.TreeNode;

/**
 *
 * @author siranjeev
 */
public interface TreeSelectionInterface {

    JTree getTree();

    void loadTree();
    void findFromId(String id);
    void setupPanel();
    void setPartyId(String id);
    List<TreeNode> getAllSelectedTreeNode(String typeName);  
    List<TreeNode> getAllChildTreeNode();
    TreeNode getSelectedTreeNode(String typeName);    
    Object getTreeDataList();
    JPanel getContainerPanel();
    TreeNode getParentNode(TreeNode childNode)  ;
    TreeNode getSelectedParentTreeNode();    
}