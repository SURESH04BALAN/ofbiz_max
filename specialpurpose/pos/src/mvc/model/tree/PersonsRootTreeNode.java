/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.tree;

import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;


class PersonsRootTreeNode implements TreeNode {

	private List<TreeNode> personTreeNodes;
	private ListAdapterListModel<Person> personListModel;

	public PersonsRootTreeNode(ListAdapterListModel<Person> personListModel) {
		this.personListModel = personListModel;
	}

	private List<TreeNode> getPersonTreeNodes() {
		if (personTreeNodes == null) {
			personTreeNodes = new ArrayList<TreeNode>();

			int size = personListModel.getSize();
			for (int i = 0; i < size; i++) {
				Person person = (Person) personListModel.getElementAt(i);
				PersonTreeNode personTreeNode = new PersonTreeNode(this, person);
				personTreeNodes.add(personTreeNode);
			}
		}
		return personTreeNodes;
	}

        @Override
	public TreeNode getChildAt(int childIndex) {
		List<TreeNode> perTreeNodes = getPersonTreeNodes();
		return perTreeNodes.get(childIndex);
	}

        @Override
	public int getChildCount() {
		List<TreeNode> perTreeNodes = getPersonTreeNodes();
		return perTreeNodes.size();
	}

        @Override
	public TreeNode getParent() {
		return null;
	}

        @Override
	public int getIndex(TreeNode node) {
		List<TreeNode> perTreeNodes = getPersonTreeNodes();
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
		List<TreeNode> perTreeNodes = getPersonTreeNodes();
		return Collections.enumeration(perTreeNodes);
	}

	TreeNode getPersonTreeNode(Person person) {
		TreeNode personTreeNode = null;
		int personIndex = personListModel.indexOf(person);
		if (personIndex > -1) {
			List<TreeNode> perTreeNodes = getPersonTreeNodes();
			personTreeNode = perTreeNodes.get(personIndex);
		}
		return personTreeNode;

	}

	void update() {
		this.personTreeNodes = null;
	}
}