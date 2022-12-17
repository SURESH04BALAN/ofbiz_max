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
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.FindSelectionEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.product.PartyTypeSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

public class JParamsFormParties extends ParamReportEditor {
     public String getEditorClassName(){
        return "JParamsFormParties";
    }
    public JGenericComboBoxSelectionModel<PartyType> productIdComboModel = null;
    //public JGenericComboBoxSelectionModel<RoleType> partyRoleComboModel = null;
    ControllerOptions controllerOptions = null;

    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    public FindSelectionEditPanel partyPanel = null;

    public JParamsFormParties(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;

        partyPanel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "partyId");
        panelPartyId.setLayout(new BorderLayout());
        panelPartyId.add(BorderLayout.CENTER, partyPanel);

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

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "roleTypeId", findPartyRoleList, null, controllerOptions.getRoleType());
        panelPartyRole.setLayout(new BorderLayout());
        panelPartyRole.add(BorderLayout.CENTER, panel);

        if (controllerOptions.contains("partyId")) {
            partyPanel.textIdField.setText((String) controllerOptions.get("partyId"));
        }
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "firstName");
        panelFirstName.setLayout(new BorderLayout());
        panelFirstName.add(BorderLayout.CENTER, panel);
        
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "lastName");
        panelLastName.setLayout(new BorderLayout());
        panelLastName.add(BorderLayout.CENTER, panel);        
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "groupName");
        panelGroupName.setLayout(new BorderLayout());
        panelGroupName.add(BorderLayout.CENTER, panel);        
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
        panelGroupName = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panelPartyRole = new javax.swing.JPanel();
        panelPartyTypeId = new javax.swing.JPanel();
        panelPartyId = new javax.swing.JPanel();
        panelFirstName = new javax.swing.JPanel();
        panelLastName = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 200));
        setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Party Id:");
        add(jLabel1);
        jLabel1.setBounds(186, 10, 81, 16);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Party Type Id:");
        add(jLabel3);
        jLabel3.setBounds(186, 42, 81, 16);

        panelGroupName.setMinimumSize(new java.awt.Dimension(0, 22));
        panelGroupName.setPreferredSize(new java.awt.Dimension(450, 24));

        javax.swing.GroupLayout panelGroupNameLayout = new javax.swing.GroupLayout(panelGroupName);
        panelGroupName.setLayout(panelGroupNameLayout);
        panelGroupNameLayout.setHorizontalGroup(
            panelGroupNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelGroupNameLayout.setVerticalGroup(
            panelGroupNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelGroupName);
        panelGroupName.setBounds(280, 170, 290, 24);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Last name:");
        add(jLabel4);
        jLabel4.setBounds(186, 138, 81, 16);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Group Name:");
        add(jLabel5);
        jLabel5.setBounds(186, 170, 81, 16);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("First name:");
        add(jLabel6);
        jLabel6.setBounds(186, 106, 81, 16);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Party Role:");
        add(jLabel7);
        jLabel7.setBounds(186, 74, 81, 16);

        panelPartyRole.setMinimumSize(new java.awt.Dimension(0, 22));
        panelPartyRole.setPreferredSize(new java.awt.Dimension(450, 24));

        javax.swing.GroupLayout panelPartyRoleLayout = new javax.swing.GroupLayout(panelPartyRole);
        panelPartyRole.setLayout(panelPartyRoleLayout);
        panelPartyRoleLayout.setHorizontalGroup(
            panelPartyRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelPartyRoleLayout.setVerticalGroup(
            panelPartyRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelPartyRole);
        panelPartyRole.setBounds(280, 74, 290, 24);

        panelPartyTypeId.setMinimumSize(new java.awt.Dimension(0, 22));
        panelPartyTypeId.setPreferredSize(new java.awt.Dimension(450, 24));

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
        panelPartyTypeId.setBounds(280, 42, 290, 24);

        panelPartyId.setMinimumSize(new java.awt.Dimension(0, 22));
        panelPartyId.setPreferredSize(new java.awt.Dimension(450, 24));

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
        panelPartyId.setBounds(280, 10, 290, 24);

        panelFirstName.setMinimumSize(new java.awt.Dimension(0, 22));
        panelFirstName.setPreferredSize(new java.awt.Dimension(450, 24));

        javax.swing.GroupLayout panelFirstNameLayout = new javax.swing.GroupLayout(panelFirstName);
        panelFirstName.setLayout(panelFirstNameLayout);
        panelFirstNameLayout.setHorizontalGroup(
            panelFirstNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelFirstNameLayout.setVerticalGroup(
            panelFirstNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelFirstName);
        panelFirstName.setBounds(280, 106, 290, 24);

        panelLastName.setMinimumSize(new java.awt.Dimension(0, 22));
        panelLastName.setPreferredSize(new java.awt.Dimension(450, 24));

        javax.swing.GroupLayout panelLastNameLayout = new javax.swing.GroupLayout(panelLastName);
        panelLastName.setLayout(panelLastNameLayout);
        panelLastNameLayout.setHorizontalGroup(
            panelLastNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );
        panelLastNameLayout.setVerticalGroup(
            panelLastNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelLastName);
        panelLastName.setBounds(280, 138, 290, 24);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel panelFirstName;
    private javax.swing.JPanel panelGroupName;
    private javax.swing.JPanel panelLastName;
    private javax.swing.JPanel panelPartyId;
    private javax.swing.JPanel panelPartyRole;
    private javax.swing.JPanel panelPartyTypeId;
    // End of variables declaration//GEN-END:variables

}
