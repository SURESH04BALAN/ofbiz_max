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
import mvc.model.table.FindProductPriceTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.ProductPriceComposite;

/**
 *
 * @author adrianromero
 */
public class PanelFindProductPriceBean extends PanelFindBaseBean {

    public static final String module = PanelFindProductPriceBean.class.getName();

    private final JTextField txtProdIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;

    private final JTextField txtPartyIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;
    public GenericTableModelPanel<ProductPriceComposite, FindProductPriceTableModel> tablePanel = null;
    ControllerOptions options = null;

    public PanelFindProductPriceBean(ControllerOptions options) {
        this.options = options;
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        loadParameterSelections();

        tablePanel = new GenericTableModelPanel<ProductPriceComposite, FindProductPriceTableModel>(new FindProductPriceTableModel());
        jPanel2.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        super.init(app);
    }
    com.openbravo.pos.reports.params.JParamsFormProductPrice panel = null;

    public void loadParameterSelections() {
        panel = com.openbravo.pos.reports.params.JParamHelper.AddReportFormProductPrice(getQbffilter(), options, "By form");
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    public void setProductPriceList(ListAdapterListModel<ProductPriceComposite> orderListModel) {
        tablePanel.setListModel(orderListModel);
    }
    
    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
          //  panel.partyPickerEditPanel.textIdField.setText(account.getParty().getpartyId());
        }
    }
public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getProductActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    public JTextField getTxtPartyIdTableTextField() {
        return txtPartyIdTableTextField;
    }

    final public void setupEditOrderTable() {

        for (int i = 0; i < FindProductPriceTableModel.Columns.values().length; i++) {
            FindProductPriceTableModel.Columns[] columns = FindProductPriceTableModel.Columns.values();
            FindProductPriceTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (FindProductPriceTableModel.Columns.PRODUCTID == column) {
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);
            }
            /*else if(FindProductPriceTableModel.Columns == column){
             tableReceiveInv.setSurrendersFocusOnKeystroke(true);
             txtPartyIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
             DefaultCellEditor editor = new DefaultCellEditor(txtPartyIdTableTextField);
             editor.setClickCountToStart(0);
             orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
             col.setCellEditor(orderRowColumnClickActionTableCellEditor);                   
             } */
        }
    }


    public List<EntityCondition> getFindOptionCondList() {
        return getEntityConditions();
    }
}
