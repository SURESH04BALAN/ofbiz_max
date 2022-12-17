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
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

public class JParamsFormInventoryItem extends ParamReportEditor {
     public String getEditorClassName(){
        return "JParamsFormInventoryItem";
    }
    public JGenericComboBoxSelectionModel<PartyType> productIdComboModel = null;
    public JGenericComboBoxSelectionModel<RoleType> partyRoleComboModel = null;
    ControllerOptions controllerOptions = null;
    PartyPickerEditPanel partyPickerEditPanel = null;
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    public FindSelectionEditPanel partyPanel = null;

    public JParamsFormInventoryItem(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;

        JPanel panel = ReportBaseMain.AddProductIdSelection(filterList, controllerOptions, null);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(panel, BorderLayout.CENTER);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "facilityId", FacilitySingleton.getValueList(), null, null);
        panelFacilityId.setLayout(new BorderLayout());
        panelFacilityId.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "internalName");
        panelInternalName.setLayout(new BorderLayout());
        panelInternalName.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "inventoryItemId");
        panelInventoryItemId.setLayout(new BorderLayout());
        panelInventoryItemId.add(BorderLayout.CENTER, panel);

        List<StatusItem> findStatusList = StatusSingleton.getValueList("INV_SERIALIZED_STTS");
        StatusItem partyType = new StatusItem();
        partyType.setdescription("All");
        partyType.setstatusTypeId("ANY");
        findStatusList.add(0, partyType);
        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "statusId", findStatusList, null, partyType);
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "serialNumber");
        panelSerialNumber.setLayout(new BorderLayout());
        panelSerialNumber.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "softIdentifier");
        panelSoftIdentifier.setLayout(new BorderLayout());
        panelSoftIdentifier.add(BorderLayout.CENTER, panel);

        partyPickerEditPanel = new PartyPickerEditPanel(controllerOptions);
        //partyPickerEditPanel = (PartyPickerEditPanel)ReportBaseMain.AddPartyIdSelection(filterList, controllerOptions, "Party Id:");
        panelManufacturePartyId.setLayout(new BorderLayout());
        panelManufacturePartyId.add(BorderLayout.CENTER, partyPickerEditPanel);
        filterList.add(partyPickerEditPanel);
        
        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "lotId");
        panelLotId.setLayout(new BorderLayout());
        panelLotId.add(BorderLayout.CENTER, panel);
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

        jLabel6 = new javax.swing.JLabel();
        panelStatus = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelFacilityId = new javax.swing.JPanel();
        panelLotId = new javax.swing.JPanel();
        panelProductId = new javax.swing.JPanel();
        panelInternalName = new javax.swing.JPanel();
        panelInventoryItemId = new javax.swing.JPanel();
        panelStatusId = new javax.swing.JPanel();
        panelSerialNumber = new javax.swing.JPanel();
        panelSoftIdentifier = new javax.swing.JPanel();
        panelManufacturePartyId = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 280));
        setLayout(null);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Soft Identifier:");
        jLabel6.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel6);
        jLabel6.setBounds(160, 182, 160, 24);

        javax.swing.GroupLayout panelStatusLayout = new javax.swing.GroupLayout(panelStatus);
        panelStatus.setLayout(panelStatusLayout);
        panelStatusLayout.setHorizontalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelStatusLayout.setVerticalGroup(
            panelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(panelStatus);
        panelStatus.setBounds(-40, 0, 0, 0);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Lot Id:");
        jLabel10.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel10);
        jLabel10.setBounds(160, 238, 160, 24);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Manufacturer Party Id:");
        jLabel8.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel8);
        jLabel8.setBounds(160, 210, 160, 24);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Serial Number:");
        jLabel5.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel5);
        jLabel5.setBounds(160, 154, 160, 24);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Status Id:");
        jLabel9.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel9);
        jLabel9.setBounds(160, 126, 160, 24);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Facility Id:");
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel1);
        jLabel1.setBounds(160, 10, 160, 24);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Product Id:");
        jLabel3.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel3);
        jLabel3.setBounds(160, 40, 160, 24);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Internal Name:");
        jLabel11.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel11);
        jLabel11.setBounds(160, 70, 160, 24);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Inventory Item Id:");
        jLabel12.setPreferredSize(new java.awt.Dimension(200, 24));
        add(jLabel12);
        jLabel12.setBounds(160, 98, 160, 24);

        panelFacilityId.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelFacilityId);
        panelFacilityId.setBounds(330, 10, 350, 24);

        panelLotId.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelLotIdLayout = new javax.swing.GroupLayout(panelLotId);
        panelLotId.setLayout(panelLotIdLayout);
        panelLotIdLayout.setHorizontalGroup(
            panelLotIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLotIdLayout.setVerticalGroup(
            panelLotIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelLotId);
        panelLotId.setBounds(330, 238, 350, 24);

        panelProductId.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelProductId);
        panelProductId.setBounds(330, 40, 350, 24);

        panelInternalName.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelInternalNameLayout = new javax.swing.GroupLayout(panelInternalName);
        panelInternalName.setLayout(panelInternalNameLayout);
        panelInternalNameLayout.setHorizontalGroup(
            panelInternalNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelInternalNameLayout.setVerticalGroup(
            panelInternalNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelInternalName);
        panelInternalName.setBounds(330, 70, 350, 24);

        panelInventoryItemId.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelInventoryItemIdLayout = new javax.swing.GroupLayout(panelInventoryItemId);
        panelInventoryItemId.setLayout(panelInventoryItemIdLayout);
        panelInventoryItemIdLayout.setHorizontalGroup(
            panelInventoryItemIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelInventoryItemIdLayout.setVerticalGroup(
            panelInventoryItemIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelInventoryItemId);
        panelInventoryItemId.setBounds(330, 98, 350, 24);

        panelStatusId.setPreferredSize(new java.awt.Dimension(350, 24));

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
        panelStatusId.setBounds(330, 126, 350, 24);

        panelSerialNumber.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelSerialNumberLayout = new javax.swing.GroupLayout(panelSerialNumber);
        panelSerialNumber.setLayout(panelSerialNumberLayout);
        panelSerialNumberLayout.setHorizontalGroup(
            panelSerialNumberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelSerialNumberLayout.setVerticalGroup(
            panelSerialNumberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelSerialNumber);
        panelSerialNumber.setBounds(330, 154, 350, 24);

        panelSoftIdentifier.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelSoftIdentifierLayout = new javax.swing.GroupLayout(panelSoftIdentifier);
        panelSoftIdentifier.setLayout(panelSoftIdentifierLayout);
        panelSoftIdentifierLayout.setHorizontalGroup(
            panelSoftIdentifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelSoftIdentifierLayout.setVerticalGroup(
            panelSoftIdentifierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelSoftIdentifier);
        panelSoftIdentifier.setBounds(330, 182, 350, 24);

        panelManufacturePartyId.setPreferredSize(new java.awt.Dimension(350, 24));

        javax.swing.GroupLayout panelManufacturePartyIdLayout = new javax.swing.GroupLayout(panelManufacturePartyId);
        panelManufacturePartyId.setLayout(panelManufacturePartyIdLayout);
        panelManufacturePartyIdLayout.setHorizontalGroup(
            panelManufacturePartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelManufacturePartyIdLayout.setVerticalGroup(
            panelManufacturePartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelManufacturePartyId);
        panelManufacturePartyId.setBounds(330, 210, 350, 24);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelInternalName;
    private javax.swing.JPanel panelInventoryItemId;
    private javax.swing.JPanel panelLotId;
    private javax.swing.JPanel panelManufacturePartyId;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelSerialNumber;
    private javax.swing.JPanel panelSoftIdentifier;
    private javax.swing.JPanel panelStatus;
    private javax.swing.JPanel panelStatusId;
    // End of variables declaration//GEN-END:variables

}
