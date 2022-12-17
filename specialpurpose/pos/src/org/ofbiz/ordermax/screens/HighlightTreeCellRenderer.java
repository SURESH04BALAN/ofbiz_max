/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

import java.awt.Color;
import java.awt.Component;
import java.util.Objects;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.TreeNode;

/**
 *
 * @author BBS Auctions
 */
/*
 public class HighlightTreeCellRenderer extends DefaultTreeCellRenderer {

 private static final Color rollOverRowColor = new Color(220, 240, 255);
 private String q = null;

 public String getQ() {
 return q;
 }

 public void setQ(String val) {
 if (val != null) {
 this.q = val.toUpperCase();
 }
 }

 @Override
 public void updateUI() {
 setTextSelectionColor(null);
 setTextNonSelectionColor(null);
 setBackgroundSelectionColor(null);
 setBackgroundNonSelectionColor(null);
 super.updateUI();
 }
 boolean rollOver = false;

 @Override
 public Color getBackgroundNonSelectionColor() {
 return rollOver ? rollOverRowColor : super.getBackgroundNonSelectionColor();
 }
   

 @Override
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

 JComponent c = (JComponent) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
 if (selected) {
 c.setForeground(getTextSelectionColor());
 } else {
 rollOver = q != null && !q.isEmpty() && value.toString().toUpperCase().contains(q);
 c.setForeground(getTextNonSelectionColor());
 c.setBackground(getBackgroundNonSelectionColor());
 }

 super.getTreeCellRendererComponent(tree, value, selected, expanded,
 leaf, row, hasFocus);

 return this;
 }
 }
 */
public class HighlightTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final Color ROLLOVER_ROW_COLOR = new Color(220, 240, 255);
    public String q;
    private boolean rollOver;

    public String getQ() {
        return q;
    }

    public void setQ(String val) {
        if (val != null) {
            this.q = val.toUpperCase();
        }
    }

    @Override
    public void updateUI() {
        setTextSelectionColor(null);
        setTextNonSelectionColor(null);
        setBackgroundSelectionColor(null);
        setBackgroundNonSelectionColor(null);
        super.updateUI();
    }

    @Override
    public Color getBackgroundNonSelectionColor() {
        return rollOver ? ROLLOVER_ROW_COLOR : super.getBackgroundNonSelectionColor();
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
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

        JComponent c = (JComponent) super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);
        if (isSelected) {
            c.setForeground(getTextSelectionColor());
        } else {
            if (UtilValidate.isNotEmpty(value) && Objects.toString(value, "") != null) {
                rollOver = q != null && !q.isEmpty() && value != null && Objects.toString(value, "").toUpperCase().startsWith(q);
            }
            c.setForeground(getTextNonSelectionColor());
            c.setBackground(getBackgroundNonSelectionColor());
        }
        return c;
    }
}
