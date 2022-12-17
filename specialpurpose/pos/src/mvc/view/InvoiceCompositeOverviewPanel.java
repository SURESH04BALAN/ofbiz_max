/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import mvc.model.table.InvoiceCompositeTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;

/**
 * The main panel that holds a {@link JList}, {@link JTable}, {@link JTree} and
 * {@link JComboBox} that display the loaded {@link Order} objects. All the
 * components are synchronized by models so that they will all show the same
 * data and reflect the same selection. This panel is an example of how to
 * implement a MVC (model-view-controller) pattern with swing.
 *
 * @author RenÃ© Link <a
 * href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 * intersystems.com]</a>
 *
 */
public class InvoiceCompositeOverviewPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4471606875093169644L;

    private InvoiceCompositeTableModel orderTableModel = new InvoiceCompositeTableModel();
    private ListAdapterListModel<InvoiceComposite> orderListModel = new ListAdapterListModel<InvoiceComposite>();

    private ListModelSelection<InvoiceComposite> listModelSelection = new ListModelSelection<InvoiceComposite>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();

    private JTable orderTable = new JTable(orderTableModel);

//        private JList<InvoiceComposite> orderList = new JList<InvoiceComposite>(orderListModel);
    public void setInvoiceCompositeList(ListAdapterListModel<InvoiceComposite> orderListModel) {
//		orderList.setModel(orderListModel);
        orderTableModel.setListModel(orderListModel);
        listModelSelection.setListModels(orderListModel, selectionModel);
    }

    public InvoiceCompositeOverviewPanel() {
        setLayout(new BorderLayout());
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderTable.setSelectionModel(selectionModel);
        for (int i = 0; i < InvoiceCompositeTableModel.Columns.values().length; i++) {
            InvoiceCompositeTableModel.Columns[] columns = InvoiceCompositeTableModel.Columns.values();
            InvoiceCompositeTableModel.Columns column = columns[i];
            TableColumn col = orderTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 130);
        add(BorderLayout.CENTER, scrollPane);

        scrollPane.setViewportView(orderTable);

    }

}
