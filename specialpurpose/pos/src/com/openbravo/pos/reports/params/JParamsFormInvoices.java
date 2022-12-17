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
package com.openbravo.pos.reports.params;

import com.openbravo.basic.BasicException;
import java.awt.Component;
//import com.openbravo.pos.forms.AppLocal;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.table.InvoiceCompositeTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.Dates.DateSingleton;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.FindDateSelectionPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.OrderType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import static org.ofbiz.ordermax.report.ReportBaseMain.createReportSelectionComboPanel;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;

public class JParamsFormInvoices extends ParamReportEditor {

     public String getEditorClassName(){
        return "JParamsFormInvoices";
    }
     
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    ControllerOptions options = null;
    boolean showComboKeys = true;
    public PartyPickerEditPanel partyPickerEditPanel = null;

    public JParamsFormInvoices(ControllerOptions options) {

        initComponents();
        this.options = options;

        JPanel panel = ReportBaseMain.AddFindDateSelection(filterList, "invoiceDate", options, null, DateSingleton.PERIOD.NODATE);
        panelInvoiceDate.setLayout(new BorderLayout());
        panelInvoiceDate.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddFindDateSelection(filterList, "dueDate", options, null, DateSingleton.PERIOD.NODATE);
        panelInvoiceDueDate.setLayout(new BorderLayout());
        panelInvoiceDueDate.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, options, null, "billingAccountId");
        panelBillingAccount.setLayout(new BorderLayout());
        panelBillingAccount.add(BorderLayout.CENTER, panel);

        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("INVOICE_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "statusId", findStatusItemList, null, null);
        panelStatus.setLayout(new BorderLayout());
        panelStatus.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, options, null, "invoiceId");
        panelInvoiceId.setLayout(new BorderLayout());
        panelInvoiceId.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, options, null, "referenceNumber");
        panelReference.setLayout(new BorderLayout());
        panelReference.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, options, null, "description");
        panelDescription.setLayout(new BorderLayout());
        panelDescription.add(BorderLayout.CENTER, panel);

        List<InvoiceType> invoiceTypeList = InvoiceTypeSingleton.getValueList();
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setdescription("All");
        invoiceType.setinvoiceTypeId("ANY");
        invoiceTypeList.add(0, invoiceType);

//        invoiceTypeComboBoxModel = new JGenericComboBoxSelectionModel<InvoiceType>(invoiceTypeList);
        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "invoiceTypeId", invoiceTypeList, "", options.getInvoiceType());
        panelInvoiceType.setLayout(new BorderLayout());
        panelInvoiceType.add(BorderLayout.CENTER, panel);

        ControllerOptions fromOptions = new ControllerOptions(options);
        fromOptions.addDefaultPartyIdField(null);
        fromOptions.put("keyId", "partyIdFrom");
        panel = ReportBaseMain.AddPartyIdSelection(filterList, fromOptions, null);
        panelFromPartyId.setLayout(new BorderLayout());
        panelFromPartyId.add(BorderLayout.CENTER, panel);

        ControllerOptions toOptions = new ControllerOptions(options);
        toOptions.put("keyId", "partyId");
        partyPickerEditPanel = (PartyPickerEditPanel) ReportBaseMain.AddPartyIdSelection(filterList, toOptions, null);
        panelToPartyId.setLayout(new BorderLayout());
        panelToPartyId.add(BorderLayout.CENTER, partyPickerEditPanel);

        if (options.isSalesInvoice()) {
            lblPartyId.setText("Party Id To:");
        } else {
            lblPartyId.setText("Party Id From:");
        }
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public EntityCondition getEntityCondition() {
        return JParamHelper.getEntityCondition(filterList);
    }

    @Override
    public Map<String, Object> getValues() throws BasicException {
        return JParamHelper.getValuesMap(filterList);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblPartyId2 = new javax.swing.JLabel();
        panelInvoiceDueDate = new javax.swing.JPanel();
        lblPartyId3 = new javax.swing.JLabel();
        panelInvoiceDate = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelFromPartyId = new javax.swing.JPanel();
        lblPartyId1 = new javax.swing.JLabel();
        panelToPartyId = new javax.swing.JPanel();
        lblPartyId = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelInvoiceType = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelInvoiceId = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelDescription = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        panelBillingAccount = new javax.swing.JPanel();
        panelStatus = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        panelReference = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 220));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(null);

        lblPartyId2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId2.setText("Invoice Date:");
        jPanel1.add(lblPartyId2);
        lblPartyId2.setBounds(12, 13, 128, 16);

        panelInvoiceDueDate.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout panelInvoiceDueDateLayout = new javax.swing.GroupLayout(panelInvoiceDueDate);
        panelInvoiceDueDate.setLayout(panelInvoiceDueDateLayout);
        panelInvoiceDueDateLayout.setHorizontalGroup(
            panelInvoiceDueDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelInvoiceDueDateLayout.setVerticalGroup(
            panelInvoiceDueDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel1.add(panelInvoiceDueDate);
        panelInvoiceDueDate.setBounds(158, 42, 897, 22);

        lblPartyId3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId3.setText("Invoice Due Date:");
        jPanel1.add(lblPartyId3);
        lblPartyId3.setBounds(39, 42, 101, 16);

        panelInvoiceDate.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout panelInvoiceDateLayout = new javax.swing.GroupLayout(panelInvoiceDate);
        panelInvoiceDate.setLayout(panelInvoiceDateLayout);
        panelInvoiceDateLayout.setHorizontalGroup(
            panelInvoiceDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 897, Short.MAX_VALUE)
        );
        panelInvoiceDateLayout.setVerticalGroup(
            panelInvoiceDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanel1.add(panelInvoiceDate);
        panelInvoiceDate.setBounds(158, 13, 897, 22);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(2636, 120));

        jPanel2.setPreferredSize(new java.awt.Dimension(600, 131));
        jPanel2.setLayout(null);

        panelFromPartyId.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelFromPartyIdLayout = new javax.swing.GroupLayout(panelFromPartyId);
        panelFromPartyId.setLayout(panelFromPartyIdLayout);
        panelFromPartyIdLayout.setHorizontalGroup(
            panelFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );
        panelFromPartyIdLayout.setVerticalGroup(
            panelFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jPanel2.add(panelFromPartyId);
        panelFromPartyId.setBounds(146, 94, 252, 24);

        lblPartyId1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId1.setText("From Party ID:");
        jPanel2.add(lblPartyId1);
        lblPartyId1.setBounds(0, 94, 141, 16);

        panelToPartyId.setMinimumSize(new java.awt.Dimension(252, 0));
        panelToPartyId.setPreferredSize(new java.awt.Dimension(252, 24));

        javax.swing.GroupLayout panelToPartyIdLayout = new javax.swing.GroupLayout(panelToPartyId);
        panelToPartyId.setLayout(panelToPartyIdLayout);
        panelToPartyIdLayout.setHorizontalGroup(
            panelToPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelToPartyIdLayout.setVerticalGroup(
            panelToPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jPanel2.add(panelToPartyId);
        panelToPartyId.setBounds(146, 70, 386, 24);

        lblPartyId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPartyId.setText("To Party ID:");
        jPanel2.add(lblPartyId);
        lblPartyId.setBounds(0, 70, 141, 16);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Invoice Type:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(0, 45, 141, 16);

        panelInvoiceType.setMinimumSize(new java.awt.Dimension(252, 0));
        panelInvoiceType.setPreferredSize(new java.awt.Dimension(252, 24));

        javax.swing.GroupLayout panelInvoiceTypeLayout = new javax.swing.GroupLayout(panelInvoiceType);
        panelInvoiceType.setLayout(panelInvoiceTypeLayout);
        panelInvoiceTypeLayout.setHorizontalGroup(
            panelInvoiceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelInvoiceTypeLayout.setVerticalGroup(
            panelInvoiceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel2.add(panelInvoiceType);
        panelInvoiceType.setBounds(146, 45, 386, 25);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Invoice Id:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(0, 13, 141, 25);

        panelInvoiceId.setMinimumSize(new java.awt.Dimension(252, 0));
        panelInvoiceId.setPreferredSize(new java.awt.Dimension(252, 24));

        javax.swing.GroupLayout panelInvoiceIdLayout = new javax.swing.GroupLayout(panelInvoiceId);
        panelInvoiceId.setLayout(panelInvoiceIdLayout);
        panelInvoiceIdLayout.setHorizontalGroup(
            panelInvoiceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        panelInvoiceIdLayout.setVerticalGroup(
            panelInvoiceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel2.add(panelInvoiceId);
        panelInvoiceId.setBounds(146, 13, 386, 25);

        jPanel3.setPreferredSize(new java.awt.Dimension(600, 135));
        jPanel3.setLayout(null);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Reference Number:");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(12, 45, 141, 16);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Description:");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(12, 96, 141, 25);

        panelDescription.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelDescriptionLayout = new javax.swing.GroupLayout(panelDescription);
        panelDescription.setLayout(panelDescriptionLayout);
        panelDescriptionLayout.setHorizontalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelDescriptionLayout.setVerticalGroup(
            panelDescriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel3.add(panelDescription);
        panelDescription.setBounds(158, 96, 268, 26);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status:");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(12, 13, 141, 16);

        panelBillingAccount.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelBillingAccountLayout = new javax.swing.GroupLayout(panelBillingAccount);
        panelBillingAccount.setLayout(panelBillingAccountLayout);
        panelBillingAccountLayout.setHorizontalGroup(
            panelBillingAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        panelBillingAccountLayout.setVerticalGroup(
            panelBillingAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel3.add(panelBillingAccount);
        panelBillingAccount.setBounds(158, 71, 268, 25);

        panelStatus.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel3.add(panelStatus);
        panelStatus.setBounds(158, 13, 268, 25);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Billing Account ID:");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(12, 71, 141, 16);

        panelReference.setMinimumSize(new java.awt.Dimension(252, 0));

        javax.swing.GroupLayout panelReferenceLayout = new javax.swing.GroupLayout(panelReference);
        panelReference.setLayout(panelReferenceLayout);
        panelReferenceLayout.setHorizontalGroup(
            panelReferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelReferenceLayout.setVerticalGroup(
            panelReferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanel3.add(panelReference);
        panelReference.setBounds(158, 45, 268, 26);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        add(jPanel4, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblPartyId;
    private javax.swing.JLabel lblPartyId1;
    private javax.swing.JLabel lblPartyId2;
    private javax.swing.JLabel lblPartyId3;
    private javax.swing.JPanel panelBillingAccount;
    private javax.swing.JPanel panelDescription;
    private javax.swing.JPanel panelFromPartyId;
    private javax.swing.JPanel panelInvoiceDate;
    private javax.swing.JPanel panelInvoiceDueDate;
    private javax.swing.JPanel panelInvoiceId;
    private javax.swing.JPanel panelInvoiceType;
    private javax.swing.JPanel panelReference;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelToPartyId;
    // End of variables declaration//GEN-END:variables

}
