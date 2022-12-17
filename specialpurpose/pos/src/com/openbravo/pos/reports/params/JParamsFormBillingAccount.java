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
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.FindSelectionEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.product.PartyTypeSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

public class JParamsFormBillingAccount extends ParamReportEditor {
         public String getEditorClassName(){
        return "JParamsFormBillingAccount";
    }
    private DatePickerEditPanel fromDate = null;
    private DatePickerEditPanel thruDate = null;
    ControllerOptions controllerOptions = null;
    PartyPickerEditPanel partyPickerEditPanel = null;
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
   // public FindSelectionEditPanel partyPanel = null;

    public JParamsFormBillingAccount(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;
        
        partyPickerEditPanel = new PartyPickerEditPanel(controllerOptions);
        //partyPickerEditPanel = (PartyPickerEditPanel)ReportBaseMain.AddPartyIdSelection(filterList, controllerOptions, "Party Id:");
        panelPartyId.setLayout(new BorderLayout());
        panelPartyId.add(BorderLayout.CENTER, partyPickerEditPanel);
        filterList.add(partyPickerEditPanel);
        
        List<PartyType> findPartyList = PartyTypeSingleton.getValueList();
        PartyType partyType = new PartyType();
        partyType.setdescription("All");
        partyType.setpartyTypeId("ANY");
        findPartyList.add(0, partyType);

        JPanel panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "partyTypeId", findPartyList, null, partyType);
        panelPartyTypeId.setLayout(new BorderLayout());
        panelPartyTypeId.add(BorderLayout.CENTER, panel);

        List<RoleType> findPartyRoleList = RoleTypeSingleton.getValueList(this.controllerOptions.getRoleTypeParent());
        RoleType partyRole = new RoleType();
        partyRole.setdescription("All");
        partyRole.setroleTypeId("ANY");
        findPartyRoleList.add(0, partyRole);
      
        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "accountLimit");
        panelAccountLimit.setLayout(new BorderLayout());
        panelAccountLimit.add(BorderLayout.CENTER, panel);
              
        
        panel = ReportBaseMain.AddFindSingleDateSelection(filterList, "fromDate", controllerOptions,  null);                
        panelFromDate.setLayout(new BorderLayout());
        panelFromDate.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddFindSingleDateSelection(filterList, "thruDate", controllerOptions,  null);                
        panelThruDate.setLayout(new BorderLayout());
        panelThruDate.add(BorderLayout.CENTER, panel);       
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
        panelPartyTypeId = new javax.swing.JPanel();
        panelPartyId = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        panelFromDate = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        panelThruDate = new javax.swing.JPanel();
        panelAccountLimit = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 170));
        setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Party Id:");
        add(jLabel1);
        jLabel1.setBounds(186, 14, 81, 16);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Party Type Id:");
        add(jLabel3);
        jLabel3.setBounds(186, 44, 81, 16);

        panelPartyTypeId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelPartyTypeIdLayout = new javax.swing.GroupLayout(panelPartyTypeId);
        panelPartyTypeId.setLayout(panelPartyTypeIdLayout);
        panelPartyTypeIdLayout.setHorizontalGroup(
            panelPartyTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelPartyTypeIdLayout.setVerticalGroup(
            panelPartyTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelPartyTypeId);
        panelPartyTypeId.setBounds(280, 44, 290, 22);

        panelPartyId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelPartyIdLayout = new javax.swing.GroupLayout(panelPartyId);
        panelPartyId.setLayout(panelPartyIdLayout);
        panelPartyIdLayout.setHorizontalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelPartyIdLayout.setVerticalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelPartyId);
        panelPartyId.setBounds(280, 14, 290, 22);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Account Limit:");
        add(jLabel10);
        jLabel10.setBounds(155, 71, 119, 16);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("From Date:");
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(jLabel17);
        jLabel17.setBounds(155, 100, 119, 15);

        panelFromDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelFromDate);
        panelFromDate.setBounds(278, 100, 290, 25);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Thru Date:");
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        add(jLabel16);
        jLabel16.setBounds(155, 132, 119, 25);

        panelThruDate.setPreferredSize(new java.awt.Dimension(0, 25));

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelThruDate);
        panelThruDate.setBounds(278, 132, 290, 25);

        panelAccountLimit.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelAccountLimitLayout = new javax.swing.GroupLayout(panelAccountLimit);
        panelAccountLimit.setLayout(panelAccountLimitLayout);
        panelAccountLimitLayout.setHorizontalGroup(
            panelAccountLimitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelAccountLimitLayout.setVerticalGroup(
            panelAccountLimitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelAccountLimit);
        panelAccountLimit.setBounds(280, 71, 290, 22);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel panelAccountLimit;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelPartyId;
    private javax.swing.JPanel panelPartyTypeId;
    private javax.swing.JPanel panelThruDate;
    // End of variables declaration//GEN-END:variables

}
