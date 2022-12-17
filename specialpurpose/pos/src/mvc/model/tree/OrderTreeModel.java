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
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.ofbiz.ordermax.composite.Order;


public class OrderTreeModel extends DefaultTreeModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1572938192116000199L;

	private final ListModelChangeAdapter listModelChangeAdapter = new ListModelChangeAdapter();

	private ListAdapterListModel<Order> personListModel = new ListAdapterListModel<Order>();

	private OrderRootTreeNode personsRootTreeNode;

	public OrderTreeModel() {
		super(null);
	}

	public void setListModel(ListAdapterListModel<Order> personListModel) {
		if (this.personListModel != null) {
			this.personListModel.removeListDataListener(listModelChangeAdapter);
		}
		this.personListModel = personListModel;
		if (personListModel != null) {
			personListModel.addListDataListener(listModelChangeAdapter);
		}
		personsRootTreeNode = new OrderRootTreeNode(personListModel);
		setRoot(personsRootTreeNode);
	}

	public TreePath getTreePath(Order person) {
		if (personsRootTreeNode == null) {
			return null;
		}
		List<Object> nodes = new ArrayList<Object>();
		nodes.add(personsRootTreeNode);

		TreeNode treeNode = personsRootTreeNode.getPersonTreeNode(person);
		if (treeNode != null) {
			nodes.add(treeNode);
		}

		Object[] path = (Object[]) nodes.toArray(new Object[nodes.size()]);
		TreePath treePath = new TreePath(path);
		return treePath;
	}

	public Order getOrder(TreePath path) {
		Object[] objectPath = path.getPath();
		Order order = null;
		for (Object object : objectPath) {
			if (object instanceof OrderTreeNode) {
				OrderTreeNode personTreeNode = (OrderTreeNode) object;
				order = personTreeNode.getOrder();
				break;
			}
		}
		return order;
	}

	private class ListModelChangeAdapter implements ListDataListener {

		public void update() {
			personsRootTreeNode.update();
			setRoot(personsRootTreeNode);
		}

		public void intervalAdded(ListDataEvent e) {
			update();
		}

		public void intervalRemoved(ListDataEvent e) {
			update();
		}

		public void contentsChanged(ListDataEvent e) {
			update();
		}

	}

}
