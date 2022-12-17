/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.tree;


import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;
import org.ofbiz.ordermax.composite.Order;


class OrderRootTreeNode implements TreeNode {

	private List<TreeNode> personTreeNodes;
	private ListAdapterListModel<Order> personListModel;

	public OrderRootTreeNode(ListAdapterListModel<Order> personListModel) {
		this.personListModel = personListModel;
	}

	private List<TreeNode> getOrderTreeNodes() {
		if (personTreeNodes == null) {
			personTreeNodes = new ArrayList<TreeNode>();

			int size = personListModel.getSize();
			for (int i = 0; i < size; i++) {
				Order person = (Order) personListModel.getElementAt(i);
				OrderTreeNode personTreeNode = new OrderTreeNode(this, person);
				personTreeNodes.add(personTreeNode);
			}
		}
		return personTreeNodes;
	}

        @Override
	public TreeNode getChildAt(int childIndex) {
		List<TreeNode> perTreeNodes = getOrderTreeNodes();
		return perTreeNodes.get(childIndex);
	}

        @Override
	public int getChildCount() {
		List<TreeNode> perTreeNodes = getOrderTreeNodes();
		return perTreeNodes.size();
	}

        @Override
	public TreeNode getParent() {
		return null;
	}

        @Override
	public int getIndex(TreeNode node) {
		List<TreeNode> perTreeNodes = getOrderTreeNodes();
		return perTreeNodes.indexOf(node);
	}

        @Override
	public boolean getAllowsChildren() {
		return true;
	}

        @Override
	public boolean isLeaf() {
		return false;
	}

        @Override
	public Enumeration<TreeNode> children() {
		List<TreeNode> perTreeNodes = getOrderTreeNodes();
		return Collections.enumeration(perTreeNodes);
	}

	TreeNode getPersonTreeNode(Order person) {
		TreeNode personTreeNode = null;
		int personIndex = personListModel.indexOf(person);
		if (personIndex > -1) {
			List<TreeNode> perTreeNodes = getOrderTreeNodes();
			personTreeNode = perTreeNodes.get(personIndex);
		}
		return personTreeNode;

	}

	void update() {
		this.personTreeNodes = null;
	}
}