/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.OrderSearchTableModel;
import mvc.view.GenericTableModelPanel;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.Dates.DateSingleton;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.FindDateSelectionPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;
import org.ofbiz.ordermax.entity.OrderType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.base.components.GenericDateSelectionPanel;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.report.ReportBaseMain;
import static org.ofbiz.ordermax.report.ReportBaseMain.AddPartyIdSelection;
import static org.ofbiz.ordermax.report.ReportBaseMain.createReportSelectionComboPanel;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.CollapsiblePanel;

import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindOrderByPartyListPanel extends javax.swing.JPanel {
    public static final String module = FindOrderByPartyListPanel.class.getName();
    private final JTextField txtOrderIdTableTextField = new JTextField();
//    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    public GenericTableModelPanel<OrderHeaderAndRoleSummary, OrderSearchTableModel> tablePanel = null;
    public JGenericComboBoxSelectionModel<OrderType> orderTypeComboModel = null;
    public JGenericComboBoxSelectionModel<StatusItem> statusItemComboModel = null;
    PartyPickerEditPanel partyPickerEditPanel = null;    
    PartyPickerEditPanel panelFromPartyId;
    ControllerOptions options = null;
    FindDateSelectionPanel dateFilter;
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindOrderByPartyListPanel(ControllerOptions options) {
        initComponents();

        tablePanel = new GenericTableModelPanel<OrderHeaderAndRoleSummary, OrderSearchTableModel>(new OrderSearchTableModel());
       // jPanel2.setLayout(new BorderLayout());
        jPanel2.add(BorderLayout.CENTER, tablePanel);        
        setupEditOrderTable();

        /*List<StatusItem> findStatusItemList = StatusSingleton.getValueList("ORDER_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        statusItemComboModel = new JGenericComboBoxSelectionModel<StatusItem>(findStatusItemList);
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, statusItemComboModel.jComboBox);
        statusItemComboModel.setSelectedItem(statusItem);

        panelFromPartyId = new PartyPickerEditPanel(options);
        pFromPartyId.setLayout(new BorderLayout());
        pFromPartyId.add(BorderLayout.CENTER, panelFromPartyId);
        dateFilter = new GenericDateSelectionPanel("orderDate");
        panelDateSelection.setLayout(new BorderLayout());
        panelDateSelection.add(BorderLayout.WEST, dateFilter);
*/
        //role        
/*        List<RoleType> findroleList = RoleTypeSingleton.getValueList();
         RoleType partyType = new RoleType();
         partyType.setdescription("All");
         partyType.setroleTypeId("ANY");
         findroleList.add(0, partyType);
         */
        //order Type

/*
        orderTypeComboModel = new JGenericComboBoxSelectionModel<OrderType>(findOrderTypeList, "orderTypeId");
        panelOrderTypeId.setLayout(new BorderLayout());
        panelOrderTypeId.add(BorderLayout.CENTER, orderTypeComboModel.jComboBox);

        if (options.contains("orderId")) {
            txtOrderId.setText((String) options.get("orderId"));
        }

        if (options.getOrderType() != null) {
            orderTypeComboModel.setSelectedItem(options.getOrderType());
        } else {
            orderTypeComboModel.setSelectedItem(orderType);
        }

        if (options.contains("partyId")) {
            panelFromPartyId.textIdField.setText((String) options.get("partyId"));
        }
*/
//        JPanel panel = AddDateSelection(filterList,"orderDate", controllerOptions, "Date Selection:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        partyPickerEditPanel = (PartyPickerEditPanel)AddPartyIdSelection(filterList,options, null);
        pFromPartyId.setLayout(new BorderLayout());
        pFromPartyId.add(BorderLayout.CENTER, partyPickerEditPanel);

//        panel = AddProductIdSelection(filterList,controllerOptions, "Product Id:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        List<OrderType> findOrderTypeList = OrderTypeSingleton.getValueList();
        OrderType orderType = new OrderType();
        orderType.setdescription("All");
        orderType.setorderTypeId("ANY");
        findOrderTypeList.add(0, orderType);
        if(options.getOrderType()!=null){
            orderType = options.getOrderType();
        }
        
        JPanel panel = createReportSelectionComboPanel(filterList,"orderTypeId", findOrderTypeList, null, orderType);
        panelOrderTypeId.setLayout(new BorderLayout());
        panelOrderTypeId.add(BorderLayout.CENTER, panel);


        panel = ReportBaseMain.AddTextIdSelection(filterList, options , null, "orderId");
        panelOrderId.setLayout(new BorderLayout());
        panelOrderId.add(BorderLayout.CENTER, panel); 
        
        
        List<StatusItem> findStatusItemList = StatusSingleton.getValueList("ORDER_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        findStatusItemList.add(0, statusItem);

        panel = createReportSelectionComboPanel(filterList,"statusId", findStatusItemList, null, statusItem);
        panelStatusId.setLayout(new BorderLayout());
        panelStatusId.add(BorderLayout.CENTER, panel);
        
       // dateFilter = new GenericDateSelectionPanel("orderDate");
        //panelDateSelection.setLayout(new BorderLayout());
        //panelDateSelection.add(BorderLayout.CENTER, dateFilter);
        
        dateFilter = ReportBaseMain.AddFindDateSelection(filterList, "orderDate", options ,  null, DateSingleton.PERIOD.NODATE);
        panelDateSelection.setLayout(new BorderLayout());
        panelDateSelection.add(BorderLayout.CENTER, dateFilter);
        
        
        ComponentBorder.doubleRaisedLoweredBevelBorder(jPanel2, "Order List");
        ComponentBorder.doubleRaisedLoweredBevelBorder(jPanelFilter, "Order Selection");
//        ComponentBorder.doubleRaisedLoweredBevelBorder(panelDateSelection, "Date Selection");

    }

    public void setReceiveInventoryList(ListAdapterListModel<OrderHeaderAndRoleSummary> listModel) {
        //    accountListModel.setModel(listModel);
        tablePanel.setListModel(listModel);
//        listModelSelection.setListModels(listModel, selectionModel);
//        accountCompositeTableModel.setListModel(listModel);
    }
    Account account = null;

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            partyPickerEditPanel.textIdField.setText(account.getParty().getpartyId());
        }

    }
    
    public OrderHeaderAndRoleSummary getSelectedOrder() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtOrderIdTableTextField() {
        return txtOrderIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditOrderTable() {
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtOrderIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtOrderIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("Order Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < OrderSearchTableModel.Columns.values().length; i++) {
            OrderSearchTableModel.Columns[] columns = OrderSearchTableModel.Columns.values();
            OrderSearchTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);

            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } /*else if (OrderSearchTableModel.Columns.ORDERNBR == column) {
             Debug.logError("OrderSearchTableModel.Columns.ORDERNBR " + OrderSearchTableModel.Columns.ORDERNBR, "module");
             tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
             txtOrderIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
             DefaultCellEditor editor = new DefaultCellEditor(txtOrderIdTableTextField);
             editor.setClickCountToStart(0);
             rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
             col.setCellEditor(rowColumnClickActionTableCellEditor);
             }*/

        }
    }
/*
    public Map<String, Object> getFindOptionList() {
//        Map<String, Object> findOptionList = FastMap.newInstance();
        boolean showAll = true;
        Map<String, Object> svcCtx = FastMap.newInstance();

        if (UtilValidate.isNotEmpty(panelFromPartyId.textIdField.getText())) {
            svcCtx.put("partyId", panelFromPartyId.textIdField.getText());
            showAll = false;
        }

        if (UtilValidate.isNotEmpty(txtOrderId.getText())) {
            svcCtx.put("orderId", txtOrderId.getText());
            showAll = false;
        }


        
        if (orderTypeComboModel.jComboBox.getSelectedItem() != null) {
            OrderType partyType = (OrderType) orderTypeComboModel.jComboBox.getSelectedItem();
            if (partyType != null && !"ANY".equals(partyType.getorderTypeId())) {
                svcCtx.put("orderTypeId", partyType.getorderTypeId());
                showAll = false;
            }
        }

        if (statusItemComboModel.jComboBox.getSelectedItem() != null) {
            StatusItem statusType = (StatusItem) statusItemComboModel.jComboBox.getSelectedItem();
            if (statusType != null && !"ANY".equals(statusType.getstatusId())) {
                svcCtx.put("statusId", statusType.getstatusId());
                showAll = false;
            }
        }

        /*
         if (UtilValidate.isNotEmpty(txtOrderId.getText())) {
         findOptionList.put("firstName", txtOrderId.getText());
         showAll = false;
         }
         if (UtilValidate.isNotEmpty(txtLastName.getText())) {
         findOptionList.put("lastName", txtLastName.getText());
         showAll = false;
         }
         if (UtilValidate.isNotEmpty(txtGroupName.getText())) {
         findOptionList.put("groupName", txtGroupName.getText());
         showAll = false;
         }

         List<String> stausList = FastList.newInstance();
         stausList.add("ORDER_APPROVED");
         stausList.add("ORDER_CANCELLED");
         stausList.add("ORDER_COMPLETED");
         stausList.add("ORDER_CREATED");
         stausList.add("ORDER_HOLD");
         stausList.add("ORDER_PROCESSING");
         stausList.add("ORDER_REJECTED");

         List<String> orderTypeId = FastList.newInstance();
         orderTypeId.add("SALES_ORDER");
         orderTypeId.add("PURCHASE_ORDER");

         svcCtx.put("filterPartiallyReceivedPOs", "Y");

         svcCtx.put("filterPOsOpenPastTheirETA", "Y");
         svcCtx.put("filterPOsWithRejectedItems", "Y");

         String filterInventoryProblems = "N";
         filterInventoryProblems = "Y";

         Locale locale = Locale.getDefault();
         //     String partyId = session.getCompanyPartyId();
         //svcCtx.put("orderId", order.getOrderHeader().getOrderId());
         svcCtx.put("roleTypeId", UtilMisc.toList("BILL_TO_CUSTOMER", "BILL_FROM_VENDOR"));

         svcCtx.put("locale", locale);
         if(showAll){
         svcCtx.put("showAll", "Y");
         }
         svcCtx.put("viewIndex", new Integer(1));
         svcCtx.put("viewSize", new Integer(10000));
         svcCtx.put("orderStatusId", stausList);
         svcCtx.put("orderTypeId", orderTypeId);
         svcCtx.put("filterInventoryProblems", filterInventoryProblems);
         */
 //       return svcCtx;
 //   }
    public List<EntityCondition> getFindOptionCondList() {
        List<EntityCondition> whereClauseMap = ReportBaseMain.getWhereClauseCond(filterList);
        EntityCondition  cond = dateFilter.getEntityCondition();
        if (cond != null) {
            Debug.logInfo("cond : " + cond.toString(), module);
            whereClauseMap.add(cond);
        }
        return whereClauseMap;
    }
    /*
    public List<EntityCondition> getFindOptionCondList() {
        
        
        List<EntityCondition> whereClauseMap = new ArrayList<EntityCondition>();

        EntityCondition cond = panelFromPartyId.getEntityCondition();
        if (cond != null) {
            Debug.logInfo("cond : " + cond.toString(), module);
            whereClauseMap.add(cond);
        }
        
        cond = dateFilter.getEntityCondition();
        if (cond != null) {
            Debug.logInfo("cond : " + cond.toString(), module);
            whereClauseMap.add(cond);
        }
        
        if (UtilValidate.isNotEmpty(txtOrderId.getText())) {
            cond = EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, txtOrderId.getText());
            whereClauseMap.add(cond);
        }

        if (orderTypeComboModel.jComboBox.getSelectedItem() != null) {
            OrderType partyType = (OrderType) orderTypeComboModel.jComboBox.getSelectedItem();
            if (partyType != null && !"ANY".equals(partyType.getorderTypeId())) {

                cond = EntityCondition.makeCondition("orderTypeId", EntityOperator.EQUALS, partyType.getorderTypeId());
                whereClauseMap.add(cond);

            }
        }

        if (statusItemComboModel.jComboBox.getSelectedItem() != null) {
            StatusItem statusType = (StatusItem) statusItemComboModel.jComboBox.getSelectedItem();
            if (statusType != null && !"ANY".equals(statusType.getstatusId())) {
                cond = EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, statusType.getstatusId());
                whereClauseMap.add(cond);
            }
        }

        return whereClauseMap;
    }
*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanelHeader = new CollapsiblePanel();
        jPanelFilter = new javax.swing.JPanel();
        panelOrderTypeId = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelStatusId = new javax.swing.JPanel();
        pFromPartyId = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panelOrderId = new javax.swing.JPanel();
        panelDateSelection = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jToggleFilter = new javax.swing.JToggleButton();
        btnFind = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblCaption = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanelHeader.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelHeader.setForeground(new java.awt.Color(240, 240, 240));
        jPanelHeader.setPreferredSize(new java.awt.Dimension(835, 200));
        jPanelHeader.setLayout(new java.awt.BorderLayout());

        jPanelFilter.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanelFilter.setLayout(null);

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

        jPanelFilter.add(panelOrderTypeId);
        panelOrderTypeId.setBounds(240, 50, 330, 22);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Order Status:");
        jPanelFilter.add(jLabel4);
        jLabel4.setBounds(150, 100, 78, 16);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Party Id:");
        jPanelFilter.add(jLabel1);
        jLabel1.setBounds(180, 74, 49, 16);

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

        jPanelFilter.add(panelStatusId);
        panelStatusId.setBounds(240, 100, 330, 22);

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

        jPanelFilter.add(pFromPartyId);
        pFromPartyId.setBounds(240, 74, 330, 22);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Order Id:");
        jPanelFilter.add(jLabel6);
        jLabel6.setBounds(170, 20, 53, 16);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Order Type Id:");
        jPanelFilter.add(jLabel7);
        jLabel7.setBounds(140, 50, 85, 16);

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

        jPanelFilter.add(panelOrderId);
        panelOrderId.setBounds(240, 20, 330, 22);

        panelDateSelection.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelDateSelectionLayout = new javax.swing.GroupLayout(panelDateSelection);
        panelDateSelection.setLayout(panelDateSelectionLayout);
        panelDateSelectionLayout.setHorizontalGroup(
            panelDateSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );
        panelDateSelectionLayout.setVerticalGroup(
            panelDateSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jPanelFilter.add(panelDateSelection);
        panelDateSelection.setBounds(240, 130, 610, 22);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Order Date:");
        jPanelFilter.add(jLabel5);
        jLabel5.setBounds(160, 130, 68, 16);

        jPanelHeader.add(jPanelFilter, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jToggleFilter.setSelected(true);
        jToggleFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleFilterActionPerformed(evt);
            }
        });
        jPanel4.add(jToggleFilter);

        btnFind.setText("Execute Report");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });
        jPanel4.add(btnFind);

        jPanel3.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel5.setLayout(new java.awt.BorderLayout());

        lblCaption.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCaption.setText("jLabel1");
        jPanel5.add(lblCaption, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanelHeader.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanelHeader, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleFilterActionPerformed

        jPanelFilter.setVisible(jToggleFilter.isSelected());
    }//GEN-LAST:event_jToggleFilterActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed

    }//GEN-LAST:event_btnFindActionPerformed
  
    protected void setVisibleButtonFilter(boolean value) {
        jToggleFilter.setVisible(value);
    }

    protected void setVisibleFilter(boolean value) {
        jToggleFilter.setSelected(value);
        jToggleFilterActionPerformed(null);
    }
    /*
    public void setStatusId(String statusId) {

        try {
            statusItemComboModel.setSelectedItem(StatusSingleton.getStatusItem(statusId));
        } catch (Exception ex) {
            Logger.getLogger(FindOrderByPartyListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOrderTypeId(String orderTypeId) {
        try {
            orderTypeComboModel.setSelectedItem(OrderTypeSingleton.getOrderType(orderTypeId));
        } catch (Exception ex) {
            Logger.getLogger(FindOrderByPartyListPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    */
/*
    public void setPartyId(String partyId) {
        panelFromPartyId.textIdField.setText(partyId);
    }

    public void setOrderId(String orderId) {
        txtOrderId.setText(orderId);
    }
*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelFilter;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JToggleButton jToggleFilter;
    private javax.swing.JLabel lblCaption;
    private javax.swing.JPanel pFromPartyId;
    private javax.swing.JPanel panelDateSelection;
    private javax.swing.JPanel panelOrderId;
    private javax.swing.JPanel panelOrderTypeId;
    private javax.swing.JPanel panelStatusId;
    // End of variables declaration//GEN-END:variables
}
