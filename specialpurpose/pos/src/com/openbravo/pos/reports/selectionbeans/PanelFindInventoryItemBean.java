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
import javax.swing.table.TableColumn;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.AccountCompositeTableModel;
import mvc.model.table.InventoryItemTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.InventoryItem;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;

/**
 *
 * @author adrianromero
 */
public class PanelFindInventoryItemBean extends PanelFindBaseBean {

    public static final String module = PanelFindInventoryItemBean.class.getName();

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    public GenericTableModelPanel<InventoryItem, InventoryItemTableModel> tablePanel = null;
    ControllerOptions options = null;

    public PanelFindInventoryItemBean(ControllerOptions options) {
        this.options = options;
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        loadParameterSelections();

        tablePanel = new GenericTableModelPanel<InventoryItem, InventoryItemTableModel>(new InventoryItemTableModel());
        jPanel2.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        super.init(app);
    }
    com.openbravo.pos.reports.params.JParamsFormInventoryItem panel = null;

    public void loadParameterSelections() {
        panel = com.openbravo.pos.reports.params.JParamHelper.AddReportFormInventoryItem(getQbffilter(), options, "By form");
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    public void setInventoryItemList(ListAdapterListModel<InventoryItem> listModel) {
        tablePanel.setListModel(listModel);
    }

    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            panel.partyPanel.textIdField.setText(account.getParty().getpartyId());
        }
    }

    public InventoryItem getSelectedAccount() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
//        tableReceiveInv.getColumn("INVOICE ID").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < InventoryItemTableModel.Columns.values().length; i++) {
            InventoryItemTableModel.Columns[] columns = InventoryItemTableModel.Columns.values();
            InventoryItemTableModel.Columns column = columns[i];
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
