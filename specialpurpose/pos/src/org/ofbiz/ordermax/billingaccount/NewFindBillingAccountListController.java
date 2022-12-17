/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

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
import javolution.util.FastList;
import mvc.controller.LoadBillingAccountWorker;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.BillingAccountTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.EntityComponentFactory;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class NewFindBillingAccountListController extends BaseMainScreen implements PrimaryIdInterface {

    public static final String module = NewFindBillingAccountListController.class.getName();
    public FindBillingAccountFilter panel = null;
    static public final String caption = "Billing Account List";
    ControllerOptions controllerOptions = null;

    static public NewFindBillingAccountListController runController(ControllerOptions options) {

        NewFindBillingAccountListController controller = new NewFindBillingAccountListController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(NewFindBillingAccountListController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(NewFindBillingAccountListController.module, options.getDesktopPane());
        }
        return controller;
    }

    public String getCaption() {
        return caption;
    }

    public NewFindBillingAccountListController(ControllerOptions controllerOptions) {
        super(ControllerOptions.getSession());
        this.controllerOptions = controllerOptions;
    }

    FindInvoiceListButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
    final private ListAdapterListModel<BillingAccount> invoiceCompositeListModel = new ListAdapterListModel<BillingAccount>();
//    final private Map<String, Object> findOptionList = FastMap.newInstance();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new FindBillingAccountFilter(controllerOptions);
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
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
                setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_CANCEL);
            }
        });
        
        panel.btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                List<String> stausList = FastList.newInstance();
                invoiceCompositeListModel.clear();

                try {

                    invoiceCompositeListModel.clear();
                    Map<String, Object> findOptionList = panel.getValues();
//                    invList = LoadPaymentWorker.loadPayments(findOptionList, ControllerOptions.getSession());
                    Debug.logError("find billing account", module);
                    List<BillingAccount> list = LoadBillingAccountWorker.loadBillingAccounts(findOptionList, ControllerOptions.getSession());
                    invoiceCompositeListModel.addAll(list);
                    panel.setVisibleFilter(false);

                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        BillingAccountIdClickAction productIdClickAction = new BillingAccountIdClickAction(this, ControllerOptions.getDesktopPane(), ControllerOptions.getSession());
        panel.getRowColumnActionCellEditor().addActionListener(productIdClickAction);

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
    static public class FindBillingAccountFilter extends PanelFindBaseBean {

        public static final String module = FindBillingAccountFilter.class.getName();
        private final JTextField txtBillingAccountIdTableTextField = new JTextField();
        private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;

        public GenericTableModelPanel<BillingAccount, BillingAccountTableModel> tablePanel = null;

        ControllerOptions options = null;

        public FindBillingAccountFilter(ControllerOptions options) {
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
                        Logger.getLogger(FindBillingAccountFilter.class.getName()).log(Level.SEVERE, null, ex);
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

    static public class FindBillingAccountAction extends ScreenAction {

        final String iconPathStr = "";//"clients.png";
        final String iconPathSmallStr = "";//"clients.png";
        ControllerOptions controllerOptions = null;

        public FindBillingAccountAction(String name, ControllerOptions controllerOptions) {
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
            NewFindBillingAccountListController.runController(controllerOptions);

        }

        @Override
        public Action getAction() {
            return this;
        }
    }

    static public void addToMenu(ControllerOptions options, javax.swing.JMenu parentMenu) {
        
        ControllerOptions controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "BillingAccount");
        FindBillingAccountAction productStoreConfigMenuAction = new FindBillingAccountAction("Billing Accounts", controllerOptions);
        JMenuItem mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);
    }
}
