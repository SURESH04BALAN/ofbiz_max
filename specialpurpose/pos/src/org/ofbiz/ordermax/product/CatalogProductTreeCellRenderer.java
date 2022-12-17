/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.ofbiz.ordermax.base.TreeNode;

/**
 *
 * @author BBS Auctions
 */
public class CatalogProductTreeCellRenderer extends DefaultTreeCellRenderer {

    /**
     *
     */
    private static final long serialVersionUID = 1515795958576231663L;

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        if (value instanceof TreeNode) {
            TreeNode treeNode = (TreeNode) value;
            if (treeNode.getIcon() != null) {
                setClosedIcon(treeNode.getIcon());
                setOpenIcon(treeNode.getIcon());
                setLeafIcon(treeNode.getIcon());
            } else {
                setClosedIcon(getDefaultClosedIcon());
                setOpenIcon(getDefaultOpenIcon());
                setLeafIcon(getDefaultLeafIcon());
            }
        }
        super.getTreeCellRendererComponent(tree, value, selected, expanded,
                leaf, row, hasFocus);

        return this;
    }

}
