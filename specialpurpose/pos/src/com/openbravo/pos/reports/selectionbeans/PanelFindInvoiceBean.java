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
import mvc.model.table.InvoiceCompositeTableModel;
import mvc.model.table.OrderSearchTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.report.ReportBaseMain;

/**
 *
 * @author adrianromero
 */
public class PanelFindInvoiceBean extends PanelFindBaseBean {

    public static final String module = PanelFindInvoiceBean.class.getName();

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;
    public GenericTableModelPanel<InvoiceComposite, InvoiceCompositeTableModel> tablePanel = null;
    ControllerOptions options = new ControllerOptions();
    public PanelFindInvoiceBean(ControllerOptions options) {
        this.options = options;
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        loadParameterSelections();

        tablePanel = new GenericTableModelPanel<InvoiceComposite, InvoiceCompositeTableModel>(new InvoiceCompositeTableModel());
        jPanel2.add(BorderLayout.CENTER, tablePanel);
        setupEditInvoiceTable();
        super.init(app);
    }

    com.openbravo.pos.reports.params.JParamsFormInvoices panel;

    public void loadParameterSelections() {
        panel = com.openbravo.pos.reports.params.JParamHelper.AddReportFormInvoice(getQbffilter(), options, "Order Selection");
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    public void setInvoiceList(ListAdapterListModel<InvoiceComposite> orderListModel) {
        tablePanel.setListModel(orderListModel);
    }

    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            panel.partyPickerEditPanel.textIdField.setText(account.getParty().getpartyId());
        }

    }

    public InvoiceComposite getSelectedInvoice() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtInvoiceIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getInvoiceActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditInvoiceTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("Invoice Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < InvoiceCompositeTableModel.Columns.values().length; i++) {
            InvoiceCompositeTableModel.Columns[] columns = InvoiceCompositeTableModel.Columns.values();
            InvoiceCompositeTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

    }

    public List<EntityCondition> getFindOptionCondList() {
        return getEntityConditions();
    }
}
