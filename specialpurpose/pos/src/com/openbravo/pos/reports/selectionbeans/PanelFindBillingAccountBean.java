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
import com.openbravo.pos.reports.params.JParamFormGeneric;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.BillingAccountTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.BasePickerEditPanel;
import org.ofbiz.ordermax.base.components.EntityComponentFactory;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;

/**
 *
 * @author adrianromero
 */
public class PanelFindBillingAccountBean extends PanelFindBaseBean {

    public static final String module = PanelFindBillingAccountBean.class.getName();
    private final JTextField txtBillingAccountIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;

    public GenericTableModelPanel<BillingAccount, BillingAccountTableModel> tablePanel = null;

    ControllerOptions options = null;

    public PanelFindBillingAccountBean(ControllerOptions options) {
        this.options = options;
    }

    @Override
    public void init(AppView app) throws BeanFactoryException {
        loadParameterSelections();

        tablePanel = new GenericTableModelPanel<BillingAccount, BillingAccountTableModel>(new BillingAccountTableModel());
        jPanel2.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        super.init(app);
    }

    //      com.openbravo.pos.reports.params.JParamsFormBillingAccount panel = null;
/*
     public void loadParameterSelections() {
     panel = com.openbravo.pos.reports.params.JParamHelper.AddReportFormBillingAccount(getQbffilter(), options, "By form");
     }
     */
    com.openbravo.pos.reports.params.JParamFormGeneric panel = null;

    public void loadParameterSelections() {
        panel = new JParamFormGeneric() {
            //  getQbffilter(), options, "By form"
            public void init(AppView app) {
                //this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
                try {
                    ReportCreatorSelectionInterface panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.PartyId, options, this, "Party Id:");
//                    panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.FacilityId, options, this, "Facility Id:");
                                        
                    ControllerOptions newOpt = options.getCopy().put("entityId", "partyTypeId");
                    panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.PartyTypeId, newOpt, this, "Party Type:");                    
                    
                    //newOpt = options.getCopy().put("entityId", "partyRoleTypeId");
                    //panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.PartyRoleTypeId, newOpt, this, "Party Role Type:");                    
                    newOpt = options.getCopy().put("entityId", "accountLimit");
                    panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.TextFieldSelection, newOpt, this, "Account Limit:");                    
                    
                    newOpt = options.getCopy().put("entityId", "fromDate");                    
                    panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.SingleDateSelection, newOpt, this, "From Date:");                    

                    newOpt = options.getCopy().put("entityId", "thruDate");
                    panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.SingleDateSelection, newOpt, this, "Thru Date:");                                        
                    
                } catch (Exception ex) {
                    Logger.getLogger(PanelFindBillingAccountBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        getQbffilter().addEditor(panel);
    }

    @Override
    public void activate() throws BasicException {
        super.activate();
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    public void setBillingAccountList(ListAdapterListModel<BillingAccount> listModel) {
        tablePanel.setListModel(listModel);
    }

    /*Account account = null;

     public void setAccount(Account account) {
     this.account = account;
     if (account != null) {
     panel.txtPartyId.setText(account.getParty().getpartyId());
     }
     }

     public Account getSelectedAccount() {
     return tablePanel.listModelSelection.getSelection();
     }*/
    public JTextField getTextIdTableTextField() {
        return txtBillingAccountIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getRowColumnActionCellEditor() {
        return orderRowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);

        for (int i = 0; i < BillingAccountTableModel.Columns.values().length; i++) {
            BillingAccountTableModel.Columns[] columns = BillingAccountTableModel.Columns.values();
            BillingAccountTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);

            if (BillingAccountTableModel.Columns.BILLINGACCOUNTID.toString().equals(column.toString())) {
                Debug.logError("col name: swwt" + column.toString(), "module");
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtBillingAccountIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtBillingAccountIdTableTextField);
                editor.setClickCountToStart(0);
                orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(orderRowColumnClickActionTableCellEditor);
                Debug.logError("col name: swwt end" + column.toString(), "module");
            } else if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }

        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public List<EntityCondition> getFindOptionCondList() {
        return getEntityConditions();
    }

    public BillingAccount getSelectedBillingAccount() {
        return tablePanel.listModelSelection.getSelection();
    }

}
