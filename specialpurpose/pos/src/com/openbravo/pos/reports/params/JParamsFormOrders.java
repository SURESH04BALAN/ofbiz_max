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
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.Dates.DateSingleton;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.FindDateSelectionPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.entity.OrderType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import static org.ofbiz.ordermax.report.ReportBaseMain.createReportSelectionComboPanel;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

public class JParamsFormOrders extends ParamReportEditor {
     public String getEditorClassName(){
        return "JParamsFormOrders";
    }
    public JGenericComboBoxSelectionModel<OrderType> orderTypeComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboModel = null;
    PartyPickerEditPanel partyPickerEditPanel = null;
    PartyPickerEditPanel panelFromPartyId;
    //ControllerOptions options = null;
    FindDateSelectionPanel dateFilter;

    final String labelText;
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();

    public JParamsFormOrders(String labelText) {
        this.labelText = labelText;
        initComponents();
        ControllerOptions controllerOptions = new ControllerOptions();
        partyPickerEditPanel = new PartyPickerEditPanel(controllerOptions);
        //partyPickerEditPanel = (PartyPickerEditPanel)ReportBaseMain.AddPartyIdSelection(filterList, controllerOptions, "Party Id:");
        pFromPartyId.setLayout(new BorderLayout());
        pFromPartyId.add(BorderLayout.CENTER, partyPickerEditPanel);
        filterList.add(partyPickerEditPanel);
        
//        panel = AddProductIdSelection(filterList,controllerOptions, "Product Id:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        List<OrderType> findOrderTypeList = OrderTypeSingleton.getValueList();
        OrderType orderType = new OrderType();
        orderType.setdescription("All");
        orderType.setorderTypeId("ANY");
        findOrderTypeList.add(0, orderType);
        if (controllerOptions.getOrderType() != null) {
            orderType = controllerOptions.getOrderType();
        }

       // JGenericComboBoxSelectionModel model = new JGenericComboBoxSelectionModel<OrderType>(panelOrderTypeId, findOrderTypeList);
       // model.keyId = "orderTypeId";
       // model.setSelectedItem(orderType);
        JPanel panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "orderTypeId", findOrderTypeList, null, orderType);
        panelOrderTypeId.setLayout(new BorderLayout());
        panelOrderTypeId.add(BorderLayout.CENTER, panel);

        //JPanel panel = new FindSelectionEditPanel(controllerOptions, "orderId"); 
         panel =ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "orderId");
        panelOrderId.setLayout(new BorderLayout());
        panelOrderId.add(BorderLayout.CENTER, panel);

        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("ORDER_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        panel = createReportSelectionComboPanel(filterList, "statusId", findStatusItemList, null, statusItem);
        //model = new JGenericComboBoxSelectionModel<StatusItem>(panelStatusId, findStatusItemList);
        //model.keyId = "statusId";
        //model.setSelectedItem(statusItem);
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, panel);

        // dateFilter = new GenericDateSelectionPanel("orderDate");
        //panelDateSelection.setLayout(new BorderLayout());
        //panelDateSelection.add(BorderLayout.CENTER, dateFilter);
        dateFilter = ReportBaseMain.AddFindDateSelection(filterList, "orderDate", controllerOptions, null, DateSingleton.PERIOD.NODATE);
        panelDateSelection.setLayout(new BorderLayout());
        panelDateSelection.add(BorderLayout.CENTER, dateFilter);
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
        Map<String, Object> values = new HashMap<String, Object>();
        // values.put(pickerEditPanel.keyId, pickerEditPanel.getEntityValue());
        return values;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        panelOrderId = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        panelOrderTypeId = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pFromPartyId = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelStatusId = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelDateSelection = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 170));
        setLayout(null);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Order Id:");
        jLabel6.setPreferredSize(new java.awt.Dimension(85, 0));
        add(jLabel6);
        jLabel6.setBounds(22, 20, 85, 22);

        panelOrderId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelOrderIdLayout = new javax.swing.GroupLayout(panelOrderId);
        panelOrderId.setLayout(panelOrderIdLayout);
        panelOrderIdLayout.setHorizontalGroup(
            panelOrderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelOrderIdLayout.setVerticalGroup(
            panelOrderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelOrderId);
        panelOrderId.setBounds(130, 20, 330, 22);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Order Type Id:");
        jLabel7.setPreferredSize(new java.awt.Dimension(85, 0));
        add(jLabel7);
        jLabel7.setBounds(22, 50, 85, 22);

        panelOrderTypeId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelOrderTypeIdLayout = new javax.swing.GroupLayout(panelOrderTypeId);
        panelOrderTypeId.setLayout(panelOrderTypeIdLayout);
        panelOrderTypeIdLayout.setHorizontalGroup(
            panelOrderTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelOrderTypeIdLayout.setVerticalGroup(
            panelOrderTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelOrderTypeId);
        panelOrderTypeId.setBounds(130, 50, 330, 22);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Party Id:");
        jLabel1.setPreferredSize(new java.awt.Dimension(85, 0));
        add(jLabel1);
        jLabel1.setBounds(22, 74, 85, 22);

        pFromPartyId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout pFromPartyIdLayout = new javax.swing.GroupLayout(pFromPartyId);
        pFromPartyId.setLayout(pFromPartyIdLayout);
        pFromPartyIdLayout.setHorizontalGroup(
            pFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        pFromPartyIdLayout.setVerticalGroup(
            pFromPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(pFromPartyId);
        pFromPartyId.setBounds(130, 74, 330, 22);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Order Status:");
        jLabel4.setPreferredSize(new java.awt.Dimension(85, 0));
        add(jLabel4);
        jLabel4.setBounds(22, 100, 85, 22);

        panelStatusId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelStatusIdLayout = new javax.swing.GroupLayout(panelStatusId);
        panelStatusId.setLayout(panelStatusIdLayout);
        panelStatusIdLayout.setHorizontalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelStatusIdLayout.setVerticalGroup(
            panelStatusIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelStatusId);
        panelStatusId.setBounds(130, 100, 330, 22);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Order Date:");
        jLabel5.setPreferredSize(new java.awt.Dimension(85, 0));
        add(jLabel5);
        jLabel5.setBounds(22, 130, 85, 22);

        panelDateSelection.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelDateSelectionLayout = new javax.swing.GroupLayout(panelDateSelection);
        panelDateSelection.setLayout(panelDateSelectionLayout);
        panelDateSelectionLayout.setHorizontalGroup(
            panelDateSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
        );
        panelDateSelectionLayout.setVerticalGroup(
            panelDateSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        add(panelDateSelection);
        panelDateSelection.setBounds(130, 130, 730, 22);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel pFromPartyId;
    private javax.swing.JPanel panelDateSelection;
    private javax.swing.JPanel panelOrderId;
    private javax.swing.JPanel panelOrderTypeId;
    private javax.swing.JPanel panelStatusId;
    // End of variables declaration//GEN-END:variables

}
