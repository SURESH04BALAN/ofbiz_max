//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.pos.reports.selectionbeans;

/*import com.openbravo.basic.BasicException;
 import com.openbravo.data.loader.BaseSentence;
 import com.openbravo.data.loader.Datas;
 import com.openbravo.data.loader.QBFBuilder;
 import com.openbravo.data.loader.SerializerReadBasic;
 import com.openbravo.data.loader.StaticSentence;*/
import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTextField;
import static javax.swing.RowFilter.dateFilter;
import javax.swing.table.TableColumn;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.OrderSearchTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.report.ReportBaseMain;

/**
 *
 * @author adrianromero
 */
public class PanelFindOrderBean extends PanelFindBaseBean {

    public static final String module = PanelFindOrderBean.class.getName();

    private final JTextField txtOrderIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;
    public GenericTableModelPanel<OrderHeaderAndRoleSummary, OrderSearchTableModel> tablePanel = null;
    ControllerOptions options = new ControllerOptions();
    public PanelFindOrderBean(ControllerOptions options) {
        this.options = options;
    }
    @Override
    public void init(AppView app) throws BeanFactoryException {
        loadParameterSelections();
        tablePanel = new GenericTableModelPanel<OrderHeaderAndRoleSummary, OrderSearchTableModel>(new OrderSearchTableModel());
        jPanel2.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        super.init(app);
    }

    public void loadParameterSelections() {
        com.openbravo.pos.reports.params.JParamHelper.AddReportFormOrder(getQbffilter(), options, "By form");
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }
 public void setReceiveInventoryList(ListAdapterListModel<OrderHeaderAndRoleSummary> listModel) {
        tablePanel.setListModel(listModel);
    }
    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
//            partyPickerEditPanel.textIdField.setText(account.getParty().getpartyId());
        }

    }
    
    public OrderHeaderAndRoleSummary getSelectedOrder() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtOrderIdTableTextField() {
        return txtOrderIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditOrderTable() {
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtOrderIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtOrderIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("Order Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < OrderSearchTableModel.Columns.values().length; i++) {
            OrderSearchTableModel.Columns[] columns = OrderSearchTableModel.Columns.values();
            OrderSearchTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);

            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } /*else if (OrderSearchTableModel.Columns.ORDERNBR == column) {
             Debug.logError("OrderSearchTableModel.Columns.ORDERNBR " + OrderSearchTableModel.Columns.ORDERNBR, "module");
             tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
             txtOrderIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
             DefaultCellEditor editor = new DefaultCellEditor(txtOrderIdTableTextField);
             editor.setClickCountToStart(0);
             rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
             col.setCellEditor(rowColumnClickActionTableCellEditor);
             }*/

        }
    }

    public List<EntityCondition> getFindOptionCondList() {
        return getEntityConditions();
    }
}
