/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.reports.selectionbeans.PanelFindPartyBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import mvc.controller.LoadAccountWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.AccountCompositeTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindPartyListController extends BaseMainScreen implements
        PartyIdInterface {

    public static final String module = FindPartyListController.class.getName();
    public PanelFindPartyBean panel = null;
    public final String caption = "Party List";
    String currPartyId = null;
//    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();

    public String getCaption() {

        if ("CUSTOMER".equals(controllerOptions.getRoleType())) {
            return "Customer List";
        } else {
            return "Supplier List";
        }
    }

    static public FindPartyListController runController(ControllerOptions options) {

        FindPartyListController controller = new FindPartyListController(options);
        if (options.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(FindPartyListController.module, options.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(FindPartyListController.module, options.getDesktopPane());
        }
        return controller;
    }

    static public FindPartyListController createController(ControllerOptions options) {

        FindPartyListController controller = new FindPartyListController(options);
        return controller;
    }

    ControllerOptions controllerOptions = null;

    public FindPartyListController(ControllerOptions controllerOptions) {
        super(ControllerOptions.getSession());
//        this.isCustomerList = isCustomerList;
        this.controllerOptions = controllerOptions;
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame contFrame) {
        int y = 10;
        int x = 200;
        if (ControllerOptions.getDesktopPane() != null) {
            contFrame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);

        }
        contFrame.setLocation(x, y);
    }

    FindPartyListButtonPanel buttonPanel = null;
    final private ListAdapterListModel<Account> invoiceCompositeListModel = new ListAdapterListModel<Account>();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new PanelFindPartyBean(controllerOptions);
        panel.init(ControllerOptions.getMainApp());
        try {
            panel.activate();
        } catch (BasicException ex) {
            Debug.logError(ex, module);
        }
        panel.setPartyList(invoiceCompositeListModel);

        buttonPanel = new FindPartyListButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
                setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);
            }
        });

        buttonPanel.btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editParty(currPartyId);
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

                try {
                    invoiceCompositeListModel.clear();

                    //List<EntityCondition> findOptionList = panel.getFindOptionCondList();
                    Map<String, Object> findOptionList = panel.getValues();
//                findOptionList.put("userLogin", ControllerOptions.getSession().getUserLogin());
                    for (Map.Entry<String, Object> entry : findOptionList.entrySet()) {
                        Debug.logError(entry.getKey() + " : " + entry.getValue(), module);
                    }
                    Map<String, Object> valueList = new HashMap<String, Object>();

                    if (findOptionList.containsKey("JParamsFormParties")) {
                        valueList = (Map<String, Object>) findOptionList.get("JParamsFormParties");
                        for (Map.Entry<String, Object> entry : valueList.entrySet()) {
                            Debug.logError("valueList: " + entry.getKey() + " : " + entry.getValue(), module);
                        }
                    } else {
                        valueList.put("showAll", "Y");
                    }
                    valueList.put("lookupFlag", "Y");
                    valueList.put("VIEW_INDEX", "0");
                    valueList.put("VIEW_SIZE", "10000");
                    //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, ControllerOptions.getSession(), findOptionList);
                    LoadAccountWorker worker = new LoadAccountWorker(invoiceCompositeListModel, ControllerOptions.getSession(), valueList);
                    panel.tablePanel.actionPerformed(worker);
                    panel.setVisibleFilter(false);

                } catch (BasicException ex) {
                    Logger.getLogger(FindPartyListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        panel.tablePanel.jTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    Debug.logInfo("valueChanged ", "module");
                    if (lsm.isSelectionEmpty()) {
                        System.out.println(" <none>");
                    } else {
                        Debug.logInfo("valueChanged else", "module");
                        // Find out which indexes are selected.
                        int minIndex = lsm.getMinSelectionIndex();
                        int maxIndex = lsm.getMaxSelectionIndex();
                        for (int i = minIndex; i <= maxIndex; i++) {
                            if (lsm.isSelectedIndex(i)) {
                                Account acct = invoiceCompositeListModel.getElementAt(i);
                                currPartyId = acct.getParty().getpartyId();
                            }
                        }
                    }
                }
            }
        });

        panel.tablePanel.jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    if (controllerOptions.isDoubleClickCloseDialog()) {
                        f.okButtonPressed();
                        setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);

                    } else {
                        TableModel model = panel.tablePanel.jTable.getModel();
                        int row = panel.tablePanel.jTable.rowAtPoint(e.getPoint());
                        int col = panel.tablePanel.jTable.columnAtPoint(e.getPoint());
                        Debug.logInfo("row: " + row + " col: " + col, module);
                        if (row >= 0 && col >= 0) {
                            String partyId = model.getValueAt(row, AccountCompositeTableModel.Columns.PARTYID.getColumnIndex()).toString();
                            editParty(partyId);
                        }
                    }
                }
            }
        });
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        String enterName = "enter";
        /*        InputMap inputMap = panel.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

         inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        
         //        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterName);
         ActionMap actionMap = panel.getRootPane().getActionMap();

         actionMap.put(cancelName, new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
         f.cancelButtonPressed();
         }
         });

         actionMap.put(enterName, new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
         f.okButtonPressed();
         setReturnStatus(ContainerPanelInterface.ReturnStausType.RET_OK);                                
         }
         });
         */
        PartyIdClickAction partyIdClickAction = new PartyIdClickAction(this, ControllerOptions.getDesktopPane(), controllerOptions);
        panel.getProductActionTableCellEditor().addActionListener(partyIdClickAction);
    }

    public void editParty(String partyId) {
        Debug.logInfo("partyId: " + partyId, module);
        if (UtilValidate.isNotEmpty(partyId)) {
            ControllerOptions options = new ControllerOptions(controllerOptions);
            options.addPartyId(partyId);
            PartyMaintainControllerNew.runController(options);
        }
    }

    @Override
    public String getPartyId() {
        return panel.getTxtPartyIdTableTextField().getText();
    }

    public Account getSelectedAccount() {
        return panel.getSelectedAccount();
    }

    @Override
    public String getClassName() {
        return module;
    }

}
