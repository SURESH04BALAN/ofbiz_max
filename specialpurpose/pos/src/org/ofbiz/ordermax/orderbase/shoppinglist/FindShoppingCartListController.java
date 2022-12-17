/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.shoppinglist;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.reports.params.JParamFormGeneric;
import com.openbravo.pos.reports.selectionbeans.PanelFindBaseBean;
import java.awt.BorderLayout;
import org.ofbiz.ordermax.orderbase.returns.*;
import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.EntityComponentFactory;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.billingaccount.PrimaryIdInterface;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.entity.ShoppingList;
import org.ofbiz.ordermax.generic.GenericSaveInterface;
import org.ofbiz.ordermax.generic.GenericSavePanel;
import org.ofbiz.ordermax.generic.entitypanelsimpl.EntityPanelFactory;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.productstore.ProductStoreSetupMenuAction;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindShoppingCartListController extends BaseMainScreen implements PrimaryIdInterface {

    public static final String module = FindShoppingCartListController.class.getName();
    public FindParametersFilter panel = null;
    public final String caption = "Payment List";
    //   final ListAdapterListModel<PaymentComposite> paymentListModel = new ListAdapterListModel<PaymentComposite>();
    private boolean isSalesList = false;
    ControllerOptions controllerOptions;

    static public FindShoppingCartListController runController(ControllerOptions options) {

        FindShoppingCartListController controller = new FindShoppingCartListController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(FindShoppingCartListController.module, ControllerOptions.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(FindShoppingCartListController.module, options.getDesktopPane());
        }
        return controller;
    }

    public String getCaption() {
        return caption;
    }

    public FindShoppingCartListController(ControllerOptions controllerOptions) {
        super(ControllerOptions.getSession());
        this.controllerOptions = controllerOptions;
    }

    FindInvoiceListButtonPanel buttonPanel = null;
    final private ListAdapterListModel<ShoppingList> invoiceCompositeListModel = new ListAdapterListModel<ShoppingList>();

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

//        
        buttonPanel = new FindInvoiceListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
                setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);                
            }
        });

        /*buttonPanel.btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });*/



        buttonPanel.btnCancel.addActionListener(new ActionListener() {
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

                    //LoadShoppingListWorker worker = new LoadShoppingListWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    //panel.tablePanel.actionPerformed(worker);
                    //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    //LoadPaymentWorker worker = new LoadPaymentWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    //panel.tablePanel.actionPerformed(worker);
                    //Map<String, Object> findOptionList = panel.getValues();
//                    invList = LoadPaymentWorker.loadPayments(findOptionList, ControllerOptions.getSession());
                    Debug.logError("Payment Group controller", module);
                    //static public List<GenericValue> getPaymentGroupList(Map<String, Object> searchCondMap, final XuiSession session) {
                    List<GenericValue> genList = LoadShoppingListWorker.getShoppingList(findOptionList, ControllerOptions.getSession());
                    List<ShoppingList> list = new ArrayList<ShoppingList>();
                    for (GenericValue genVal : genList) {
                        list.add(new ShoppingList(genVal));
                    }
                    invoiceCompositeListModel.addAll(list);
                    panel.tablePanel.setListModel(invoiceCompositeListModel);
                    panel.setVisibleFilter(false);

                } catch (Exception ex) {
                    Logger.getLogger(PurchaseOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        ActionListener invoiceIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() instanceof ProductTreeActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

//                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    RowColumnActionEvent event = (RowColumnActionEvent) e;

                    try {
                        String returnId = panel.getTextIdTableTextField().getText();
                        Debug.logError("returnId: " + returnId, module);
                        ControllerOptions option = controllerOptions.getCopy();
                        option.addReturnId(returnId);
                        MaintainOrderReturnController.runController(option);
                        //MaintainOrderReturnController paymentController = new MaintainOrderReturnController(returnId, options, session);
                        //paymentController.loadTwoPanelInternalFrame(MaintainOrderReturnController.module, ControllerOptions.getDesktopPane());
                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }

                }
            }
        };
        ShoppingListIdClickAction paymentGroupAction = new ShoppingListIdClickAction(this, controllerOptions.getCopy());
        panel.getRowColumnActionCellEditor().addActionListener(paymentGroupAction);        
        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShoppingListIdClickAction.actionPerformed(panel.primaryId, controllerOptions);
            }
        });
        
        ProductStoreSetupMenuAction.ProductStoreConfigMenuAction shoppingListMnu = new ProductStoreSetupMenuAction.ProductStoreConfigMenuAction("Shopping List", controllerOptions.getCopy());
        buttonPanel.btnNew.addActionListener(shoppingListMnu.getAction());
        //panel.getProductTreeActionTableCellEditor().addActionListener(invoiceIdChangeAction);
    }

    @Override
    public String getClassName() {
        return module;
    }

    @Override
    public String getPrimaryId() {
        return panel.getTextIdTableTextField().getText();
    }

    //filter selection
    static public class FindParametersFilter extends PanelFindBaseBean {

        public static final String module = FindParametersFilter.class.getName();
        private final JTextField txtBillingAccountIdTableTextField = new JTextField();
        private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;
        public GenericTableModelPanel<ShoppingList, ShoppingListTableModel> tablePanel = null;
        String primaryId = null;
        ControllerOptions options = null;
        ShoppingList shoppingList = null;
        public FindParametersFilter(ControllerOptions options) {
            this.options = options;
        }

        @Override
        public void init(AppView app) throws BeanFactoryException {
            loadParameterSelections();

            tablePanel = new GenericTableModelPanel<ShoppingList, ShoppingListTableModel>(new ShoppingListTableModel());
            jPanel2.add(BorderLayout.CENTER, tablePanel);
            tablePanel.jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    ListSelectionModel lsm = (ListSelectionModel) tablePanel.jTable.getSelectionModel();//listSelectionModel.selectionModel;
                    if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                        // Find out which indexes are selected.
                        int minIndex = lsm.getMinSelectionIndex();
                        int maxIndex = lsm.getMaxSelectionIndex();
                        for (int i = minIndex; i <= maxIndex; i++) {
                            if (lsm.isSelectedIndex(i)) {
                                System.out.println(" " + i);
                                shoppingList = tablePanel.tableModel.getRowGenericData(i);
                                primaryId = shoppingList.getshoppingListId();
                                //    setGoodIdentification(i);
                                break;
                            }
                        }
                    }
                }
            });

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
                        ControllerOptions newOpt = options.getCopy().put("entityId", "shoppingListId");
                        ReportCreatorSelectionInterface panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.TextFieldSelection, newOpt, this, "Shopping List Id:");

                        newOpt = options.getCopy().put("entityId", "shoppingListTypeId");
                        panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.TextFieldSelection, newOpt, this, "Shopping List Type Id:", ShoppingListTypeSingleton.getValueList(), null, "shoppingListTypeId");

                        newOpt = options.getCopy().put("entityId", "description");
                        panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.TextFieldSelection, newOpt, this, "Description:");

                        newOpt = options.getCopy().put("entityId", "partyId");
                        panel = EntityComponentFactory.createControl(filterList, LookupActionListnerInterface.LookupType.PartyId, newOpt, this, "Party Id:");

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

        public void setBillingAccountList(ListAdapterListModel<ShoppingList> listModel) {
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

            for (int i = 0; i < ShoppingListTableModel.Columns.values().length; i++) {
                ShoppingListTableModel.Columns[] columns = ShoppingListTableModel.Columns.values();
                ShoppingListTableModel.Columns column = columns[i];
                TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
                //TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
                col.setPreferredWidth(column.getColumnWidth());
                if (ShoppingListTableModel.Columns.SHOPPINGLISTID.toString().equals(column.toString())) {
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

        public ShoppingList getSelectedShoppingList() {
            return tablePanel.listModelSelection.getSelection();
        }
    }

    static public class ShoppingListIdClickAction implements ActionListener {

        private PrimaryIdInterface primaryIdInterface;
        ControllerOptions options = new ControllerOptions();

        public ShoppingListIdClickAction(PrimaryIdInterface prodInterface, ControllerOptions options) {
            this.primaryIdInterface = prodInterface;
            this.options = options;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (primaryIdInterface.getPrimaryId()!= null) {
                actionPerformed(primaryIdInterface.getPrimaryId(), options);
            }
        }
        
        static public void actionPerformed(String primaryId, ControllerOptions options) {

            if (primaryId!= null) {
                ControllerOptions tmpOptions = new ControllerOptions(options);
                tmpOptions.put("EntityName", "ShoppingList");
                tmpOptions.put("shoppingListId", primaryId);                
                tmpOptions.put("X", 200);
                tmpOptions.put("Y", 0);

                GenericSaveInterface entityPanelInterface = EntityPanelFactory.createEntityPanel(tmpOptions);                
                //entityPanelInterface.setGenericValue(primaryIdInterface.getSelectedShoppingList().getGenericValueObj());
                final GenericSavePanel viewer = new GenericSavePanel(tmpOptions);
                viewer.setGenericSaveInterface(entityPanelInterface);
                viewer.add(entityPanelInterface.getPanel(), BorderLayout.CENTER);
                tmpOptions.setSimpleScreenInterface(viewer);
                final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(tmpOptions);
                frame.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                                    if (viewer.getSelectedValue() != null) {
                                       
                                    }
                                    //genericValueTablePanel.loadList();
                                }
                            }
                        });
            }           
        }
    }
}
