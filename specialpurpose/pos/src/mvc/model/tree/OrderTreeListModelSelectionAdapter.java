/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.tree;

/**
 *
 * @author siranjeev
 */
import java.util.Observable;
import java.util.Observer;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


import mvc.model.list.ListModelSelection;
import org.ofbiz.ordermax.composite.Order;

public class OrderTreeListModelSelectionAdapter extends DefaultTreeSelectionModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4155180698317178080L;
	private OrderTreeModel personTreeModel;

	private ListModelSelectionAdapter listModelSelectionAdapter = new ListModelSelectionAdapter();
	private TreeSelectionAdapter treeSelectionAdapter = new TreeSelectionAdapter();

	private ListModelSelection<Order> listModelSelection = new ListModelSelection<Order>();

	public OrderTreeListModelSelectionAdapter(OrderTreeModel personTreeModel) {
		setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.personTreeModel = personTreeModel;
		listModelSelection.addObserver(listModelSelectionAdapter);
		addTreeSelectionListener(treeSelectionAdapter);
	}

	public void setListModelSelection(
			ListModelSelection<Order> listModelSelection) {
		if (this.listModelSelection != null) {
			listModelSelection.deleteObserver(listModelSelectionAdapter);
		}
		this.listModelSelection = listModelSelection;
		if (listModelSelection != null) {
			listModelSelection.addObserver(listModelSelectionAdapter);
		}
	}

	private class TreeSelectionAdapter implements TreeSelectionListener {

		public void valueChanged(TreeSelectionEvent e) {
			TreePath path = e.getPath();
			Order person = personTreeModel.getOrder(path);
			listModelSelection.setSelection(person);

		}

	}

	private class ListModelSelectionAdapter implements Observer {

		public void update(Observable o, Object arg) {
			Order selection = listModelSelection.getSelection();
			if (selection == null) {
				return;
			}
			TreePath treePath = personTreeModel.getTreePath(selection);
			TreePath selectionPath = getSelectionPath();
			if (treePath != null && treePath.isDescendant(selectionPath)) {
				return;
			}
			setSelectionPath(treePath);
		}

	}
}
