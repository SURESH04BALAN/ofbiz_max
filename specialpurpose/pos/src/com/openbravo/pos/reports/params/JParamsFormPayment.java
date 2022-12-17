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
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ProductPriceTypeListCellRenderer;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.FindSelectionEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.ProductStoreGroup;
import org.ofbiz.ordermax.entity.ProductType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;
import org.ofbiz.ordermax.product.ProductStoreGroupSingleton;
import org.ofbiz.ordermax.product.ProductTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

public class JParamsFormPayment extends ParamReportEditor {
     public String getEditorClassName(){
        return "JParamsFormPayment";
    }
    ProductPickerEditPanel panelProductIdPicker;
    ControllerOptions controllerOptions = null;
    JGenericComboBoxSelectionModel<ProductStoreGroup> productStoreGroupComboModel = null;
    public JComboBoxSelectionModel<Account> accountComboModel = null;
    public JGenericComboBoxSelectionModel<Product> productComboModel = null;
    public JComboBoxSelectionModel<ProductPriceType> productPriceTypeComboModel = null;

    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    public FindSelectionEditPanel paymentIdPanel = null;
    public FindSelectionEditPanel commentsPanel = null;
    public FindSelectionEditPanel ammountPanel = null;    
    public FindSelectionEditPanel refNoPanel = null;
    public FindSelectionEditPanel referencePanel = null;      
    PartyPickerEditPanel panelFromPartyId;
    PartyPickerEditPanel panelToPartyId; 
    
    public JParamsFormPayment(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;

        paymentIdPanel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "paymentId");
        panelPaymentId.setLayout(new BorderLayout());
        panelPaymentId.add(BorderLayout.CENTER, paymentIdPanel);
                
        commentsPanel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "comment");
        panelComment.setLayout(new BorderLayout());
        panelComment.add(BorderLayout.CENTER, commentsPanel);

        List<PaymentType> paymentTypeeList = PaymentTypeSingleton.getValueList(controllerOptions.getParentPaymentTypeId());
        PaymentType paymentType = new PaymentType();
        paymentType.setdescription("All");
        paymentType.setpaymentTypeId("ANY");
        paymentTypeeList.add(0, paymentType);

        //paymentTypeComboModel = new JGenericComboBoxSelectionModel<PaymentType>(paymentTypeeList);        
        JPanel panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "paymentTypeId", paymentTypeeList, null, paymentType);
        panelPaymentType.setLayout(new BorderLayout());
        panelPaymentType.add(BorderLayout.CENTER, panel);
        
        panelFromPartyId = new PartyPickerEditPanel(controllerOptions);
        panelFromPartyId.keyId = "partyIdForm";
        panelFromParty.setLayout(new BorderLayout());
        panelFromParty.add(BorderLayout.CENTER, panelFromPartyId);
        filterList.add(panelFromPartyId);
        
        panelToPartyId = new PartyPickerEditPanel(controllerOptions);
        panelFromPartyId.keyId = "partyIdTo";        
        panelToParty.setLayout(new BorderLayout());
        panelToParty.add(BorderLayout.CENTER, panelToPartyId);
        filterList.add(panelToPartyId);
        
        ammountPanel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "amount", panelAmount);
        ammountPanel.fieldTypeDecimal = true;
   
        referencePanel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "paymentPreference");
        panelReference.setLayout(new BorderLayout());
        panelReference.add(BorderLayout.CENTER, referencePanel);
                        
       refNoPanel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "paymentRefNum");
        panelReferenceNumber.setLayout(new BorderLayout());
        panelReferenceNumber.add(BorderLayout.CENTER, refNoPanel);
        
   
        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("PMNT_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "statusId", findStatusItemList, null, statusItem);
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, panel);                            
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

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panelPaymentType = new javax.swing.JPanel();
        panelStatusId = new javax.swing.JPanel();
        panelFromParty = new javax.swing.JPanel();
        panelToParty = new javax.swing.JPanel();
        panelPaymentId = new javax.swing.JPanel();
        panelComment = new javax.swing.JPanel();
        panelAmount = new javax.swing.JPanel();
        panelReference = new javax.swing.JPanel();
        panelReferenceNumber = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 270));
        setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Payment Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel1);
        jLabel1.setBounds(178, 14, 112, 24);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Comments:");
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel3);
        jLabel3.setBounds(178, 42, 112, 24);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Payment Type:");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel4);
        jLabel4.setBounds(178, 70, 112, 24);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("From Party Id:");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel5);
        jLabel5.setBounds(178, 98, 112, 24);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Amount:");
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel6);
        jLabel6.setBounds(180, 126, 112, 24);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Reference No:");
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel7);
        jLabel7.setBounds(180, 150, 112, 24);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("To Party Id:");
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel8);
        jLabel8.setBounds(190, 234, 100, 24);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status:");
        jLabel9.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel9);
        jLabel9.setBounds(220, 206, 68, 24);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Reference Number:");
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 24));
        add(jLabel10);
        jLabel10.setBounds(170, 180, 120, 24);

        panelPaymentType.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelPaymentTypeLayout = new javax.swing.GroupLayout(panelPaymentType);
        panelPaymentType.setLayout(panelPaymentTypeLayout);
        panelPaymentTypeLayout.setHorizontalGroup(
            panelPaymentTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelPaymentTypeLayout.setVerticalGroup(
            panelPaymentTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelPaymentType);
        panelPaymentType.setBounds(300, 70, 400, 24);

        panelStatusId.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelStatusIdLayout = new javax.swing.GroupLayout(panelStatusId);
        panelStatusId.setLayout(panelStatusIdLayout);
        panelStatusIdLayout.setHorizontalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStatusIdLayout.setVerticalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelStatusId);
        panelStatusId.setBounds(300, 206, 400, 24);

        panelFromParty.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelFromPartyLayout = new javax.swing.GroupLayout(panelFromParty);
        panelFromParty.setLayout(panelFromPartyLayout);
        panelFromPartyLayout.setHorizontalGroup(
            panelFromPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelFromPartyLayout.setVerticalGroup(
            panelFromPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelFromParty);
        panelFromParty.setBounds(300, 98, 400, 24);

        panelToParty.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelToPartyLayout = new javax.swing.GroupLayout(panelToParty);
        panelToParty.setLayout(panelToPartyLayout);
        panelToPartyLayout.setHorizontalGroup(
            panelToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelToPartyLayout.setVerticalGroup(
            panelToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelToParty);
        panelToParty.setBounds(300, 234, 400, 24);

        panelPaymentId.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelPaymentIdLayout = new javax.swing.GroupLayout(panelPaymentId);
        panelPaymentId.setLayout(panelPaymentIdLayout);
        panelPaymentIdLayout.setHorizontalGroup(
            panelPaymentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPaymentIdLayout.setVerticalGroup(
            panelPaymentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelPaymentId);
        panelPaymentId.setBounds(300, 14, 400, 24);

        panelComment.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelCommentLayout = new javax.swing.GroupLayout(panelComment);
        panelComment.setLayout(panelCommentLayout);
        panelCommentLayout.setHorizontalGroup(
            panelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelCommentLayout.setVerticalGroup(
            panelCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelComment);
        panelComment.setBounds(300, 42, 400, 24);

        panelAmount.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelAmountLayout = new javax.swing.GroupLayout(panelAmount);
        panelAmount.setLayout(panelAmountLayout);
        panelAmountLayout.setHorizontalGroup(
            panelAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelAmountLayout.setVerticalGroup(
            panelAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelAmount);
        panelAmount.setBounds(300, 126, 400, 24);

        panelReference.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelReferenceLayout = new javax.swing.GroupLayout(panelReference);
        panelReference.setLayout(panelReferenceLayout);
        panelReferenceLayout.setHorizontalGroup(
            panelReferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelReferenceLayout.setVerticalGroup(
            panelReferenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelReference);
        panelReference.setBounds(300, 150, 400, 24);

        panelReferenceNumber.setPreferredSize(new java.awt.Dimension(400, 24));

        javax.swing.GroupLayout panelReferenceNumberLayout = new javax.swing.GroupLayout(panelReferenceNumber);
        panelReferenceNumber.setLayout(panelReferenceNumberLayout);
        panelReferenceNumberLayout.setHorizontalGroup(
            panelReferenceNumberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        panelReferenceNumberLayout.setVerticalGroup(
            panelReferenceNumberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelReferenceNumber);
        panelReferenceNumber.setBounds(300, 178, 400, 24);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelAmount;
    private javax.swing.JPanel panelComment;
    private javax.swing.JPanel panelFromParty;
    private javax.swing.JPanel panelPaymentId;
    private javax.swing.JPanel panelPaymentType;
    private javax.swing.JPanel panelReference;
    private javax.swing.JPanel panelReferenceNumber;
    private javax.swing.JPanel panelStatusId;
    private javax.swing.JPanel panelToParty;
    // End of variables declaration//GEN-END:variables

}
