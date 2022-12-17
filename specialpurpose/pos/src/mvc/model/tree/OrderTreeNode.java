/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import mvc.data.Address;

import mvc.model.tree.AddressTreeNode.AddressProperty;
import org.ofbiz.ordermax.composite.Order;

class OrderTreeNode implements TreeNode {

	private Order order;
	private TreeNode parent;

	private List<TreeNode> addressTreeNodes;

	public OrderTreeNode(TreeNode parent, Order order) {
		this.parent = parent;
		this.order = order;
	}

	Order getOrder() {
		return order;
	}

	private List<TreeNode> getAddressTreeNodes() {
		if (addressTreeNodes == null) {

		}
		return addressTreeNodes;
	}

	public TreeNode getChildAt(int childIndex) {
		List<TreeNode> addressTreeNodes = getAddressTreeNodes();
		return addressTreeNodes.get(childIndex);
	}

	public int getChildCount() {
		return AddressProperty.values().length;
	}

	public TreeNode getParent() {
		return parent;
	}

	public int getIndex(TreeNode node) {
		List<TreeNode> addressTreeNodes = getAddressTreeNodes();
		return addressTreeNodes.indexOf(node);
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return false;
	}

	public Enumeration<TreeNode> children() {
		List<TreeNode> addressTreeNodes = getAddressTreeNodes();
		return Collections.enumeration(addressTreeNodes);
	}

}
