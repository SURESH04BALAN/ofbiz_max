package mvc.model.tree;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author siranjeev
 */
import mvc.data.Address;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;


class AddressTreeNode implements TreeNode {

	public enum AddressProperty {
		STREET, STREET_NR, ZIP_CODE, CITY;
	}

	private Address address;
	private AddressProperty addressProperty;
	private TreeNode parent;

	public AddressTreeNode(TreeNode parent, Address address,
			AddressProperty addressProperty) {
		this.parent = parent;
		this.address = address;
		this.addressProperty = addressProperty;
	}

	Address getAddress() {
		return address;
	}

	AddressProperty getAddressProperty() {
		return addressProperty;

	}

	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	public int getChildCount() {
		return 0;
	}

	public TreeNode getParent() {
		return parent;
	}

	public int getIndex(TreeNode node) {
		return -1;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public boolean isLeaf() {
		return true;
	}

	public Enumeration<TreeNode> children() {
		List<TreeNode> emptyList = Collections.emptyList();
		return Collections.enumeration(emptyList);
	}

}
