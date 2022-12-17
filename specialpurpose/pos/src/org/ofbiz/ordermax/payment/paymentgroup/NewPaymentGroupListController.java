/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.paymentgroup;

import org.ofbiz.ordermax.billingaccount.*;
import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.reports.params.JParamFormGeneric;
import com.openbravo.pos.reports.selectionbeans.PanelFindBaseBean;
import java.awt.BorderLayout;
import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import mvc.controller.LoadPaymentWorker;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ActionTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.EntityComponentFactory;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.PaymentGroup;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.payment.PaymentGroupTypeSingleton;
import org.ofbiz.ordermax.payment.sales.CustomerPaymentGroupInvoiceController;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class NewPaymentGroupListController extends BaseMainScreen implements PrimaryIdInterface {

    public static final String module = NewPaymentGroupListController.class.getName();
    public FindParametersFilter panel = null;
    static public final String caption = "Payment Group List";
    ControllerOptions controllerOptions = null;

    static public NewPaymentGroupListController runController(ControllerOptions options) {

        NewPaymentGroupListController controller = new NewPaymentGroupListController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(NewPaymentGroupListController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(NewPaymentGroupListController.module, options.getDesktopPane());
        }
        return controller;
    }

    public String getCaption() {
        return caption;
    }

    public NewPaymentGroupListController(ControllerOptions controllerOptions) {
        super(ControllerOptions.getSession());
        this.controllerOptions = controllerOptions;
    }

    FindInvoiceListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<PaymentGroup> invoiceCompositeListModel = new ListAdapterListModel<PaymentGroup>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new FindParametersFilter(controllerOptions);
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        panel.setBillingAccountList(invoiceCompositeListModel);

        buttonPanel = new FindInvoiceListButtonPanel();
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        
        buttonPanel.btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
                setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_CANCEL);
            }
        });
        
        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                invoiceCompositeListModel.clear();

                try {

                    invoiceCompositeListModel.clear();

                    Map<String, Object> result = panel.getValues();
//                findOptionList.put("userLogin", ControllerOptions.getSession().getUserLogin());
                    for (Map.Entry<String, Object> entry : result.entrySet()) {
                        Debug.logError(entry.getKey() + " : " + entry.getValue(), module);
                    }
                    Map<String, Object> findOptionList = (Map<String, Object>) result.get("JParamFormGeneric");

                    for (Map.Entry<String, Object> entry : findOptionList.entrySet()) {
                        Debug.logError(entry.getKey() + " : " + entry.getValue(), module);
                    }
                    //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    //LoadPaymentWorker worker = new LoadPaymentWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    //panel.tablePanel.actionPerformed(worker);

                    //Map<String, Object> findOptionList = panel.getValues();
//                    invList = LoadPaymentWorker.loadPayments(findOptionList, ControllerOptions.getSession());
                    Debug.logError("Payment Group controller", module);
                    //static public List<GenericValue> getPaymentGroupList(Map<String, Object> searchCondMap, final XuiSession session) {
                    List<GenericValue> genList = LoadPaymentWorker.getPaymentGroupList(findOptionList, ControllerOptions.getSession());
                    List<PaymentGroup> list = new ArrayList<PaymentGroup>();
                    for (GenericValue genVal : genList) {
                        list.add(new PaymentGroup(genVal));
                    }
                    invoiceCompositeListModel.addAll(list);
                    panel.tablePanel.setListModel(invoiceCompositeListModel);
                    panel.setVisibleFilter(false);

                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        CustomerPaymentGroupInvoiceController.CustomerPaymentGroupIdClickAction  
                paymentGroupAction = new CustomerPaymentGroupInvoiceController.CustomerPaymentGroupIdClickAction(this, controllerOptions.getCopy());
        panel.getRowColumnActionCellEditor().addActionListener(paymentGroupAction);        
           
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 30;
        int x = 100;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }

    @Override
    public String getPrimaryId() {
        return panel.getTextIdTableTextField().getText();
    }

    @Override
    public String getClassName() {
        return module;
    }

    //filter selection
    static public class FindParametersFilter extends PanelFindBaseBean {

        public static final String module = FindParametersFilter.class.getName();
        private final JTextField txtBillingAccountIdTableTextField = new JTextField();
        private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;

        public GenericTableModelPanel<PaymentGroup, PaymentGroupTableModel> tablePanel = null;

        ControllerOptions options = null;

        public FindParametersFilter(ControllerOptions options) {
            this.options = options;
        }

        @Override
        public void init(AppView app) throws BeanFactoryException {
            loadParameterSelections();

            tablePanel = new GenericTableModelPanel<PaymentGroup, PaymentGroupTableModel>(new PaymentGroupTableModel());
            jPanel2.add(BorderLayout.CENTER, tablePanel);
            setupEditOrderTable();

            super.init(app);
        }

        com.openbravo.pos.reports.params.JParamFormGeneric panel = null;

        public void loadParameterSelections() {
            panel = new JParamFormGeneric() {
                //  getQbffilter(), options, "By form"
                public void init(AppView app) {
                    //this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                    this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
                    try {
                        //ReportCreatorSelectionInterface panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.PartyId, options, this, "Party Id:");
//                    panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.FacilityId, options, this, "Facility Id:");

                        //ControllerOptions newOpt = options.getCopy().put("entityId", "partyTypeId");
                        //panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.PartyTypeId, newOpt, this, "Party Type:");
                        //newOpt = options.getCopy().put("entityId", "partyRoleTypeId");
                        //panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.PartyRoleTypeId, newOpt, this, "Party Role Type:");                    
                        ControllerOptions newOpt = options.getCopy().put("entityId", "paymentGroupId");
                        ReportCreatorSelectionInterface panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.TextFieldSelection, newOpt, this, "Payment Group Id:");

                        newOpt = options.getCopy().put("entityId", "paymentGroupTypeId");                        
                        panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.TextFieldSelection, newOpt, this, "Payment Group Type Id:", PaymentGroupTypeSingleton.getValueList(), null, "paymentGroupTypeId");

                        newOpt = options.getCopy().put("entityId", "paymentGroupName");
                        panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.TextFieldSelection, newOpt, this, "Payment Group Name:");

                    } catch (Exception ex) {
                        Logger.getLogger(FindParametersFilter.class.getName()).log(Level.SEVERE, null, ex);
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

        public void setBillingAccountList(ListAdapterListModel<PaymentGroup> listModel) {
            tablePanel.setListModel(listModel);
        }

        public JTextField getTextIdTableTextField() {
            return txtBillingAccountIdTableTextField;
        }

        public RowColumnClickActionTableCellEditor getRowColumnActionCellEditor() {
            return orderRowColumnClickActionTableCellEditor;
        }

        final public void setupEditOrderTable() {

            tablePanel.jTable.setSurrendersFocusOnKeystroke(true);

            for (int i = 0; i < PaymentGroupTableModel.Columns.values().length; i++) {
                PaymentGroupTableModel.Columns[] columns = PaymentGroupTableModel.Columns.values();
                PaymentGroupTableModel.Columns column = columns[i];
                TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            //TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
                if (PaymentGroupTableModel.Columns.PAYMENTGROUPID.toString().equals(column.toString())) {
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

        public PaymentGroup getSelectedBillingAccount() {
            return tablePanel.listModelSelection.getSelection();
        }
    }

    static public class PaymentGroupTableModel extends ActionTableModel<PaymentGroup> {

        public enum Columns {

            PAYMENTGROUPID(0, 200, String.class, "Payment Group Id", true),
            PAYMENTGROUPTYPE(1, 200, String.class, "Payment Group Type", false),
            PAYMENTGROUPNAME(2, 300, String.class, "Payment Group Name", false),
            PDF(3, 200, String.class, "Pdf", true),
            CANCEL(4, 200, String.class, "Cancel", true);

            private int columnIndex;
            private int columnWidth;

            public String getHeaderString() {
                return headerString;
            }

            public void setHeaderString(String headerString) {
                this.headerString = headerString;
            }

            public Class getClassName() {
                return className;
            }

            public void setClassName(Class className) {
                this.className = className;
            }

            public boolean isIsEditable() {
                return isEditable;
            }

            public void setIsEditable(boolean isEditable) {
                this.isEditable = isEditable;
            }
            private String headerString;
            private Class className;
            private boolean isEditable;

            Columns(int index, int width, Class className, String header, boolean edit) {
                columnIndex = index;
                columnWidth = width;
                headerString = header;
                this.className = className;
                isEditable = edit;
            }

            public int getColumnIndex() {
                return columnIndex;
            }

            public int getColumnWidth() {
                return columnWidth;
            }
        }

        public PaymentGroupTableModel() {
        }

        public int getRowCount() {
            return listModel.getSize();
        }

        public int getColumnCount() {
            return Columns.values().length;
        }

        @Override
        public boolean isCellEditable(int row, int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.isEditable;

        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Object columnValue = null;
            PaymentGroup paymentGroup = listModel.getElementAt(rowIndex);
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            switch (column) {
                case PAYMENTGROUPID:
                    columnValue = paymentGroup.getPaymentGroupId();
                    break;
                case PAYMENTGROUPTYPE:
                    try {
                        columnValue = paymentGroup.getPaymentGroupTypeId();
                    } catch (Exception ex) {
                        Logger.getLogger(PaymentGroupTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case PAYMENTGROUPNAME:
                    try {
                        columnValue = paymentGroup.getPaymentGroupName();
                    } catch (Exception ex) {
                        Logger.getLogger(PaymentGroupTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

                case PDF:
                    columnValue = "PDF";
                    break;

                case CANCEL:
                    columnValue = "Cancel";
                    break;

                default:
                    break;
            }

            return columnValue;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.className;
        }

        @Override
        public String getColumnName(int columnIndex) {
            Columns[] columns = Columns.values();
            Columns column = columns[columnIndex];
            return column.headerString;
        }

    }

    static public class FindMenuAction extends ScreenAction {

        final String iconPathStr = "";//"clients.png";
        final String iconPathSmallStr = "";//"clients.png";
        ControllerOptions controllerOptions = null;

        public FindMenuAction(String name, ControllerOptions controllerOptions) {
            super(name);
            this.controllerOptions = controllerOptions;
            if (name == null) {
                this.setName(caption);
            }

            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            NewPaymentGroupListController.runController(controllerOptions);

        }

        @Override
        public Action getAction() {
            return this;
        }
    }

    static public void addToMenu(ControllerOptions options, javax.swing.JMenu parentMenu) {

        ControllerOptions controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "PaymentGroup");
        FindMenuAction productStoreConfigMenuAction = new FindMenuAction("Payment Group", controllerOptions);
        JMenuItem mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);
    }
}
