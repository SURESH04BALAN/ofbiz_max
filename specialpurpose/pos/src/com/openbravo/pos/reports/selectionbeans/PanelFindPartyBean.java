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
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;

/**
 *
 * @author adrianromero
 */
public class PanelFindPartyBean extends PanelFindBaseBean {

    public static final String module = PanelFindPartyBean.class.getName();

    private final JTextField txtPartyIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;
    public GenericTableModelPanel<Account, AccountCompositeTableModel> tablePanel = null;
    ControllerOptions options = null;

    public PanelFindPartyBean(ControllerOptions options) {
        this.options = options;
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        loadParameterSelections();

        tablePanel = new GenericTableModelPanel<Account, AccountCompositeTableModel>(new AccountCompositeTableModel());
        jPanel2.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        super.init(app);
    }
    com.openbravo.pos.reports.params.JParamsFormParties panel = null;

    public void loadParameterSelections() {
        panel = com.openbravo.pos.reports.params.JParamHelper.AddReportFormParty(getQbffilter(), options, "By form");
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    public void setPartyList(ListAdapterListModel<Account> listModel) {
       tablePanel.setListModel(listModel);        
    }
    
    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            panel.partyPanel.textIdField.setText(account.getParty().getpartyId());
        }
    }

    public Account getSelectedAccount() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtPartyIdTableTextField() {
        return txtPartyIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getProductActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

        for (int i = 0; i < AccountCompositeTableModel.Columns.values().length; i++) {
            AccountCompositeTableModel.Columns[] columns = AccountCompositeTableModel.Columns.values();
            AccountCompositeTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (AccountCompositeTableModel.Columns.PARTYID == column) {
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtPartyIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtPartyIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);
            }
        }
    }


    public List<EntityCondition> getFindOptionCondList() {
        return getEntityConditions();
    }
}
