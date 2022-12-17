/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view;


import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.OrderListCellRenderer;
import mvc.model.tree.OrderTreeCellRenderer;
import mvc.model.tree.OrderTreeModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import mvc.model.table.OrderTableModel;
import mvc.model.tree.OrderTreeListModelSelectionAdapter;
import org.ofbiz.ordermax.composite.Order;


/**
 * The main panel that holds a {@link JList}, {@link JTable}, {@link JTree} and
 * {@link JComboBox} that display the loaded {@link Order} objects. All the
 * components are synchronized by models so that they will all show the same
 * data and reflect the same selection. This panel is an example of how to
 * implement a MVC (model-view-controller) pattern with swing.
 *
 * @author RenÃ© Link <a
 *         href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 *         intersystems.com]</a>
 *
 */
public class OrderOverviewPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4471606875093169644L;

        private OrderTreeModel orderTreeModel = new OrderTreeModel();
	private OrderTableModel orderTableModel = new OrderTableModel();
	private ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();

        private OrderTreeListModelSelectionAdapter treeSelectionListModelSelectionAdapter = new OrderTreeListModelSelectionAdapter(
			orderTreeModel);
	private ListModelSelection<Order> listModelSelection = new ListModelSelection<Order>();
	private ListSelectionModel selectionModel = new DefaultListSelectionModel();

	private JTable orderTable = new JTable(orderTableModel);

        private JList<Order> orderList = new JList<Order>(orderListModel);
	private JTree orderTree = new JTree(orderTreeModel);
	private ListComboBoxModel<Order> orderComboBoxModel = new ListComboBoxModel<Order>();
	private JComboBox<Order> ordersComboBox = new JComboBox<Order>(orderComboBoxModel);

	public void setOrderList(ListAdapterListModel<Order> orderListModel) {
		orderList.setModel(orderListModel);
		orderTableModel.setListModel(orderListModel);
		orderTreeModel.setListModel(orderListModel);
		orderComboBoxModel.setListModel(orderListModel);
		listModelSelection.setListModels(orderListModel, selectionModel);
	}

	public OrderOverviewPanel() {
		setLayout(null);
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treeSelectionListModelSelectionAdapter.setListModelSelection(listModelSelection);
		orderList.setSelectionModel(selectionModel);
		orderTable.setSelectionModel(selectionModel);
		orderComboBoxModel.setListSelectionModel(selectionModel);
		orderTree.setSelectionModel(treeSelectionListModelSelectionAdapter);

		ListCellRenderer<Order> orderRenderer = new OrderListCellRenderer();
		orderList.setCellRenderer(orderRenderer);
		orderList.setEnabled(true);
		ordersComboBox.setRenderer(orderRenderer);
		orderTree.setCellRenderer(new OrderTreeCellRenderer());
		orderTree.setRootVisible(false);
		orderTable.setSelectionModel(orderList.getSelectionModel());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 580, 130);
		add(scrollPane);

		scrollPane.setViewportView(orderTable);

		ordersComboBox.setBounds(10, 153, 580, 30);
		add(ordersComboBox);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 194, 580, 105);
		add(scrollPane_1);

		scrollPane_1.setViewportView(orderList);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 310, 580, 205);
		add(scrollPane_2);

		scrollPane_2.setColumnHeaderView(orderTree);
	}

}

