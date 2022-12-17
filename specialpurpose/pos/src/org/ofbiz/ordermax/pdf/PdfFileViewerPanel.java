/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pdf;

import com.openbravo.basic.BasicException;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.format.Formats;
import com.openbravo.pos.reports.params.JParamsStartAndEndDatesInterval;
import org.ofbiz.ordermax.orderbase.orderviews.*;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import mvc.controller.SalesRegistryWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import org.apache.tools.ant.util.FileUtils;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.OrderSummaryView;
import org.ofbiz.ordermax.entity.OrderType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.SalesRegister;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.pos.container.PosContainer;

/**
 *
 * @author BBS Auctions
 */
public class PdfFileViewerPanel extends javax.swing.JPanel implements SimpleScreenInterface {
    public static final String module= PdfFileViewerPanel.class.getName();
    OrderDetailViewTablePanel tablePanel = null;
    ControllerOptions options = null;
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    ListAdapterListModel<OrderSummaryView> listModel = new ListAdapterListModel<OrderSummaryView>();
    public JGenericComboBoxSelectionModel<RoleType> roleTypeComboModel = null;
    PartyPickerEditPanel partyPickerEditPanel = null;
    PartyPickerEditPanel panelPartyPickerEditPanel = null;

    /**
     * Creates new form OrderDetailView
     */
    public PdfFileViewerPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        tablePanel = new OrderDetailViewTablePanel();
        panelDetailView.setLayout(new BorderLayout());
        panelDetailView.add(BorderLayout.CENTER, tablePanel);

        partyPickerEditPanel = (PartyPickerEditPanel) ReportBaseMain.AddPartyIdSelection(filterList, options, null);
        panelPartyId.setLayout(new BorderLayout());
        panelPartyId.add(partyPickerEditPanel, BorderLayout.CENTER);

        panelPartyPickerEditPanel = new PartyPickerEditPanel(new ControllerOptions());;
        pdfPanelPartyId.setLayout(new BorderLayout());
        pdfPanelPartyId.add(panelPartyPickerEditPanel, BorderLayout.CENTER);

        JPanel panel = ReportBaseMain.AddOrderIdSelection(filterList, options, null);
        panelOrderId.setLayout(new BorderLayout());
        panelOrderId.add(panel, BorderLayout.CENTER);
        Debug.logInfo("options.getRoleType(): " + options.getRoleType().getdescription(), module);
        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "roleTypeId", RoleTypeSingleton.getValueList(options.getRoleTypeParent()), null, options.getRoleType());
        paneRoleType.setLayout(new BorderLayout());
        paneRoleType.add(BorderLayout.CENTER, panel);

        List<OrderType> findOrderTypeList = OrderTypeSingleton.getValueList();
        OrderType orderType = new OrderType();
        orderType.setdescription("All");
        orderType.setorderTypeId("ANY");
        findOrderTypeList.add(0, orderType);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "orderTypeId", findOrderTypeList, null, options.getOrderType());
        panelOrderType.setLayout(new BorderLayout());
        panelOrderType.add(BorderLayout.CENTER, panel);

        List<StatusItem> statusItemList = StatusSingleton.getValueList("ORDER_STATUS");
        StatusItem statusItem = new StatusItem();
        statusItem.setdescription("All");
        statusItem.setstatusId("ANY");
        statusItemList.add(0, statusItem);

        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "statusId", statusItemList, null, options.getOrderStatusType());
        paneOrderStatus.setLayout(new BorderLayout());
        paneOrderStatus.add(BorderLayout.CENTER, panel);

        getPdfViewContainer();
    }

    org.icepdf.ri.common.SwingController controller = null;

    void getPdfViewContainer() {
        // Get a file from the command line to open
        // String filePath = "C:\\ofbiz\\ofbiz-12.04.02\\applications\\content\\data\\MACI22733.pdf";

        // build a component controller
        controller = new org.icepdf.ri.common.SwingController();
        controller.setIsEmbeddedComponent(true);

        org.icepdf.ri.util.PropertiesManager properties = new org.icepdf.ri.util.PropertiesManager(
                System.getProperties(),
                ResourceBundle.getBundle(org.icepdf.ri.util.PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

        properties.set(org.icepdf.ri.util.PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.75");

        org.icepdf.ri.common.SwingViewBuilder factory = new org.icepdf.ri.common.SwingViewBuilder(controller, properties);

        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
        JPanel viewerComponentPanel = factory.buildViewerPanel();
        // JFrame applicationFrame = new JFrame();
        //applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pdfPanel.setLayout(new BorderLayout());
        pdfPanel.add(viewerComponentPanel, BorderLayout.CENTER);
        // Now that the GUI is all in place, we can try openning a PDF
        // controller.openDocument(filePath);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        deleteButton1 = new javax.swing.JButton();
        panelDetailView = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelOrderId = new javax.swing.JPanel();
        panelPartyId = new javax.swing.JPanel();
        btnFind = new javax.swing.JButton();
        paneRoleType = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        paneOrderStatus = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        panelOrderType = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtSourceFilePdf = new javax.swing.JTextField();
        btnSmallIcon2 = new javax.swing.JButton();
        pdfPanelPartyId = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtInvoiceNo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jTxtStartDate = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txtInvoiceAmount = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        txtBalanceAmount = new javax.swing.JTextField();
        btnPrevioud = new javax.swing.JButton();
        btnRenameandSave = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnDateStart = new javax.swing.JButton();
        btnRenameandMove = new javax.swing.JButton();
        lblCaption = new javax.swing.JLabel();
        pdfPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSourceFile = new javax.swing.JTextField();
        txtDestinationDir = new javax.swing.JTextField();
        btnSmallIcon = new javax.swing.JButton();
        btnSmallIcon1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JList();
        cbLoadUnprocessed = new javax.swing.JCheckBox();
        cbSorted = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        deleteButton1.setText("View Invoice Pdf");
        deleteButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(deleteButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 353, Short.MAX_VALUE)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton)
                        .addComponent(newButton)
                        .addComponent(deleteButton)
                        .addComponent(deleteButton1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(okButton)))
                .addContainerGap())
        );

        jPanel3.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout panelDetailViewLayout = new javax.swing.GroupLayout(panelDetailView);
        panelDetailView.setLayout(panelDetailViewLayout);
        panelDetailViewLayout.setHorizontalGroup(
            panelDetailViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 898, Short.MAX_VALUE)
        );
        panelDetailViewLayout.setVerticalGroup(
            panelDetailViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        jPanel3.add(panelDetailView, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(403, 150));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Party Id:");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Order Id:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        panelOrderId.setMaximumSize(new java.awt.Dimension(400, 0));
        panelOrderId.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelOrderIdLayout = new javax.swing.GroupLayout(panelOrderId);
        panelOrderId.setLayout(panelOrderIdLayout);
        panelOrderIdLayout.setHorizontalGroup(
            panelOrderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        panelOrderIdLayout.setVerticalGroup(
            panelOrderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelPartyId.setMaximumSize(new java.awt.Dimension(400, 0));
        panelPartyId.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelPartyIdLayout = new javax.swing.GroupLayout(panelPartyId);
        panelPartyId.setLayout(panelPartyIdLayout);
        panelPartyIdLayout.setHorizontalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        panelPartyIdLayout.setVerticalGroup(
            panelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        btnFind.setText("Load");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        paneRoleType.setMaximumSize(new java.awt.Dimension(400, 0));
        paneRoleType.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout paneRoleTypeLayout = new javax.swing.GroupLayout(paneRoleType);
        paneRoleType.setLayout(paneRoleTypeLayout);
        paneRoleTypeLayout.setHorizontalGroup(
            paneRoleTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        paneRoleTypeLayout.setVerticalGroup(
            paneRoleTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Order Role:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Order Status:");

        paneOrderStatus.setMaximumSize(new java.awt.Dimension(400, 0));
        paneOrderStatus.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout paneOrderStatusLayout = new javax.swing.GroupLayout(paneOrderStatus);
        paneOrderStatus.setLayout(paneOrderStatusLayout);
        paneOrderStatusLayout.setHorizontalGroup(
            paneOrderStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        paneOrderStatusLayout.setVerticalGroup(
            paneOrderStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Order Type:");

        panelOrderType.setMaximumSize(new java.awt.Dimension(400, 0));
        panelOrderType.setPreferredSize(new java.awt.Dimension(400, 0));

        javax.swing.GroupLayout panelOrderTypeLayout = new javax.swing.GroupLayout(panelOrderType);
        panelOrderType.setLayout(panelOrderTypeLayout);
        panelOrderTypeLayout.setHorizontalGroup(
            panelOrderTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        panelOrderTypeLayout.setVerticalGroup(
            panelOrderTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelOrderType, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paneOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paneRoleType, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {paneOrderStatus, paneRoleType, panelOrderId, panelOrderType, panelPartyId});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(paneOrderStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelPartyId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(paneRoleType, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelOrderType, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFind))
        );

        jPanel3.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Summary", jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(300, 300));
        jPanel5.setLayout(null);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);
        jButton1.setBounds(130, 70, 79, 25);

        jLabel4.setText("Load File:");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(12, 17, 56, 16);

        txtSourceFilePdf.setPreferredSize(new java.awt.Dimension(6, 26));
        jPanel5.add(txtSourceFilePdf);
        txtSourceFilePdf.setBounds(10, 40, 200, 26);

        btnSmallIcon2.setText("jButton1");
        btnSmallIcon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmallIcon2ActionPerformed(evt);
            }
        });
        jPanel5.add(btnSmallIcon2);
        btnSmallIcon2.setBounds(210, 40, 35, 25);

        pdfPanelPartyId.setBackground(new java.awt.Color(255, 255, 204));

        javax.swing.GroupLayout pdfPanelPartyIdLayout = new javax.swing.GroupLayout(pdfPanelPartyId);
        pdfPanelPartyId.setLayout(pdfPanelPartyIdLayout);
        pdfPanelPartyIdLayout.setHorizontalGroup(
            pdfPanelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        pdfPanelPartyIdLayout.setVerticalGroup(
            pdfPanelPartyIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jPanel5.add(pdfPanelPartyId);
        pdfPanelPartyId.setBounds(10, 100, 200, 25);

        jLabel9.setText("Party:");
        jPanel5.add(jLabel9);
        jLabel9.setBounds(10, 70, 34, 16);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtInvoiceNo, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(txtInvoiceNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8);
        jPanel8.setBounds(10, 150, 200, 25);

        jLabel10.setText("Invoice No:");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(10, 130, 64, 16);

        jLabel11.setText("Date");
        jPanel5.add(jLabel11);
        jLabel11.setBounds(10, 180, 26, 16);

        jTxtStartDate.setMinimumSize(new java.awt.Dimension(6, 25));
        jTxtStartDate.setPreferredSize(new java.awt.Dimension(6, 25));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTxtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTxtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel5.add(jPanel9);
        jPanel9.setBounds(10, 200, 200, 25);

        jLabel12.setText("Invoice Amount");
        jPanel5.add(jLabel12);
        jLabel12.setBounds(10, 230, 88, 16);

        txtInvoiceAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInvoiceAmountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtInvoiceAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(txtInvoiceAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel10);
        jPanel10.setBounds(10, 250, 200, 25);

        jLabel13.setText("Balance Amount");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(10, 280, 92, 16);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBalanceAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(txtBalanceAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel11);
        jPanel11.setBounds(10, 300, 200, 25);

        btnPrevioud.setText("Previous");
        btnPrevioud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevioudActionPerformed(evt);
            }
        });
        jPanel5.add(btnPrevioud);
        btnPrevioud.setBounds(70, 360, 81, 25);

        btnRenameandSave.setText("Rename By invoice Number");
        btnRenameandSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenameandSaveActionPerformed(evt);
            }
        });
        jPanel5.add(btnRenameandSave);
        btnRenameandSave.setBounds(10, 330, 189, 25);

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel5.add(btnNext);
        btnNext.setBounds(10, 360, 57, 25);

        btnDateStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/date.png"))); // NOI18N
        btnDateStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateStartActionPerformed(evt);
            }
        });
        jPanel5.add(btnDateStart);
        btnDateStart.setBounds(210, 200, 30, 25);

        btnRenameandMove.setText("Move, Rename and Save");
        btnRenameandMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenameandMoveActionPerformed(evt);
            }
        });
        jPanel5.add(btnRenameandMove);
        btnRenameandMove.setBounds(10, 390, 175, 25);

        lblCaption.setBackground(new java.awt.Color(255, 255, 204));
        lblCaption.setOpaque(true);
        jPanel5.add(lblCaption);
        lblCaption.setBounds(200, 390, 80, 20);

        jPanel4.add(jPanel5, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout pdfPanelLayout = new javax.swing.GroupLayout(pdfPanel);
        pdfPanel.setLayout(pdfPanelLayout);
        pdfPanelLayout.setHorizontalGroup(
            pdfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
        );
        pdfPanelLayout.setVerticalGroup(
            pdfPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );

        jPanel4.add(pdfPanel, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("View Invoice", jPanel4);

        jLabel2.setText("Source Dir:");

        jLabel3.setText("Destination Dir:");

        btnSmallIcon.setText("jButton1");
        btnSmallIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmallIconActionPerformed(evt);
            }
        });

        btnSmallIcon1.setText("jButton1");
        btnSmallIcon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmallIcon1ActionPerformed(evt);
            }
        });

        jButton2.setText("Load Files");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(fileList);

        cbLoadUnprocessed.setSelected(true);
        cbLoadUnprocessed.setText("LOAD UNPROCESSED ONLY");

        cbSorted.setText("IS SORTED");

        jButton3.setText("UPDATE DB");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbLoadUnprocessed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSorted))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDestinationDir, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                            .addComponent(txtSourceFile))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSmallIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSmallIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSourceFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmallIcon))
                .addGap(7, 7, 7)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDestinationDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmallIcon1))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(cbLoadUnprocessed)
                    .addComponent(cbSorted))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dir", jPanel6);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        //      doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        //        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        /*  try {
         PDDocument document = PDDocument.load(f);
         document.getClass();
         if (document.isEncrypted()) {
         try {
         document.decrypt("");
         } catch (InvalidPasswordException e) {
         System.err.println("Error: Document is encrypted with a password.");
         // System.exit(1);
         }
         }

         PDFTextStripperByArea stripper = new PDFTextStripperByArea();
         stripper.setSortByPosition(true);
         PDFTextStripper stripper = new PDFTextStripper();
         String st = stripper.getText(document);

         System.out.println(st);
         } catch (Exception e) {
         System.err.println("Error: Document is encrypted with a password.");
         // System.exit(1);
         }*/
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed

        List<EntityCondition> findOptionList = getWhereClauseCond();
        List<EntityCondition> list = new ArrayList<EntityCondition>();
        for (EntityCondition anEntry : findOptionList) {
            if (anEntry.toString().contains("partyId =")) {
                list.add(anEntry);
                Debug.logInfo("anEntry.getKey " + anEntry.toString(), "Hello");
            }
        }
        //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, session, findOptionList);
        List<OrderSummaryView> orderSummaryViewList = SalesRegistryWorker.getSalesRegistarSummary(PosContainer.getSession(), list);
        listModel.clear();
        listModel.addAll(orderSummaryViewList);
        for (OrderSummaryView anEntry : orderSummaryViewList) {

            Debug.logInfo("anEntry.getKey " + anEntry.getInvoiceTypeId(), "Hello");
        }
        // orderSummaryViewList = LoadOrderWorker.getOrderSummary(PosContainer.getSession(), findOptionList);
//        listModel.clear();
        // listModel.addAll(orderSummaryViewList);
        setListModal(listModel);

    }//GEN-LAST:event_btnFindActionPerformed

    private void deleteButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButton1ActionPerformed

        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("C:\\ofbiz\\ofbiz-12.04.02\\applications\\content\\data\\pdftest.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }//GEN-LAST:event_deleteButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Get a file from the command line to open
        String filePath = "C:\\ofbiz\\ofbiz-12.04.02\\applications\\content\\data\\MACI22733.pdf";

        // build a component controller
        controller = new org.icepdf.ri.common.SwingController();
        controller.setIsEmbeddedComponent(true);

        org.icepdf.ri.util.PropertiesManager properties = new org.icepdf.ri.util.PropertiesManager(
                System.getProperties(),
                ResourceBundle.getBundle(org.icepdf.ri.util.PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

        properties.set(org.icepdf.ri.util.PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.75");

        org.icepdf.ri.common.SwingViewBuilder factory = new org.icepdf.ri.common.SwingViewBuilder(controller, properties);

        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
        JPanel viewerComponentPanel = factory.buildViewerPanel();
        // JFrame applicationFrame = new JFrame();
        //applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pdfPanel.setLayout(new BorderLayout());
        pdfPanel.add(viewerComponentPanel, BorderLayout.CENTER);
        // Now that the GUI is all in place, we can try openning a PDF
        controller.openDocument(filePath);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSmallIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmallIconActionPerformed
        File file = BaseHelper.getDirPath("Invoice Source Location");
        if (file != null) {
            txtSourceFile.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_btnSmallIconActionPerformed

    private void btnSmallIcon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmallIcon1ActionPerformed
//        txtDestinationDir.setText(BaseHelper.getImageFilePath("Invoice Destination Location").getAbsolutePath());
        File file = BaseHelper.getDirPath("Invoice Destination Location");
        if (file != null) {
            txtDestinationDir.setText(file.getAbsolutePath());
        }

    }//GEN-LAST:event_btnSmallIcon1ActionPerformed

    private void btnSmallIcon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmallIcon2ActionPerformed
        File file = BaseHelper.getImageFilePath("Invoice Source Location");
        if (file != null) {
            txtSourceFilePdf.setText(file.getAbsolutePath());
            controller.openDocument(file.getAbsolutePath());
        }
    }//GEN-LAST:event_btnSmallIcon2ActionPerformed

    void setCaption() {
        lblCaption.setText(String.valueOf(index) + "/" + String.valueOf(fileList.getModel().getSize()));
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        index = -1;
        fileList.removeAll();

        File folder = new File(txtSourceFile.getText());
        File[] listOfFiles = folder.listFiles();
        DefaultListModel przyklad = new DefaultListModel();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                String ext = getFileExtension(listOfFiles[i]);//FileNameUtils.getExtension(listOfFiles[i].getPath());
                if ("pdf".equals(ext)) {
                    if (cbLoadUnprocessed.isSelected()) {
                        if (listOfFiles[i].getName().length() > 10) {
                            przyklad.addElement(listOfFiles[i].getPath());
                            // fileList.getModel().
                            System.out.println("File " + listOfFiles[i].getName());
                        }
                    } else {
                        przyklad.addElement(listOfFiles[i].getPath());
                    }
                }
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        fileList.setModel(przyklad);
        moveToNextRecord();
    }//GEN-LAST:event_jButton2ActionPerformed
    int index = -1;
    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        //org.icepdf.ri.common.SwingController controller
        moveToNextRecord();

    }//GEN-LAST:event_btnNextActionPerformed
    protected void moveToNextRecord() {
        if (fileList.getModel().getSize() > (index + 1)) {
            index = index + 1;
            String filePath = (String) fileList.getModel().getElementAt(index);
            txtSourceFilePdf.setText(filePath);
            controller.openDocument(filePath);
            currFileName = filePath;
        }
        txtInvoiceNo.setText("");
        txtInvoiceNo.requestFocus();
        if (!cbSorted.isSelected()) {
            panelPartyPickerEditPanel.textIdField.setText("");
            panelPartyPickerEditPanel.textIdField.requestFocus();
        }
        setCaption();
    }
    private void btnPrevioudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevioudActionPerformed

        if (fileList.getModel().getSize() > 0 && (index - 1) >= 0) {
            index = index - 1;
            String filePath = (String) fileList.getModel().getElementAt(index);
            txtSourceFilePdf.setText(filePath);
            controller.openDocument(filePath);
            currFileName = filePath;
        }
        setCaption();
    }//GEN-LAST:event_btnPrevioudActionPerformed

    private void txtInvoiceAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInvoiceAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInvoiceAmountActionPerformed

    private void btnDateStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateStartActionPerformed

        Date date;
        try {
            date = (Date) Formats.TIMESTAMP.parseValue(jTxtStartDate.getText());
        } catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            jTxtStartDate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }//GEN-LAST:event_btnDateStartActionPerformed

    private void btnRenameandSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenameandSaveActionPerformed

        try {
            String newFilePath = getUniqueFileName(txtDestinationDir.getText(),
                    (String)panelPartyPickerEditPanel.getEntityValue(), txtInvoiceNo.getText(), 0);
            FileUtils.getFileUtils().copyFile(txtSourceFilePdf.getText(), newFilePath);
            //Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(PdfFileViewerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        SalesRegister salesRegister = new SalesRegister();
        salesRegister.setPartyId((String)panelPartyPickerEditPanel.getEntityValue());
        salesRegister.setTrxTypeId("SALES_INVOICE");
        salesRegister.setRelatedId(txtInvoiceNo.getText());
        salesRegister.setAmount(new BigDecimal(txtInvoiceAmount.getText()));
        salesRegister.setBalanceamount(new BigDecimal(txtBalanceAmount.getText()));

        salesRegister.setTrxDate(getDate());

        SalesRegistryWorker.createOrUpdateSalesRegister(salesRegister, ControllerOptions.getSession());
    }//GEN-LAST:event_btnRenameandSaveActionPerformed

    String currFileName = null;

    private void btnRenameandMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenameandMoveActionPerformed
        if (UtilValidate.isNotEmpty(txtInvoiceNo.getText()) && UtilValidate.isNotEmpty(panelPartyPickerEditPanel.getEntityValue())) {
            GenericValue partyGenric = null;
            String partyId = (String)panelPartyPickerEditPanel.getEntityValue();
            try {

                partyGenric = ControllerOptions.getSession().getDelegator().findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));
            } catch (GenericEntityException e) {
                Debug.logWarning(e.getMessage(), module);
            }

            if (partyGenric != null) {
                try {
                    String newFilePath = getUniqueFileName(txtDestinationDir.getText(),
                            (String)panelPartyPickerEditPanel.getEntityValue(), txtInvoiceNo.getText(), 0);
                    FileUtils.getFileUtils().copyFile(txtSourceFilePdf.getText(), newFilePath);

                    SalesRegister salesRegister = new SalesRegister();
                    salesRegister.setPartyId((String)panelPartyPickerEditPanel.getEntityValue());
                    salesRegister.setTrxTypeId("SALES_INVOICE");
                    salesRegister.setRelatedId(txtInvoiceNo.getText());
                    salesRegister.setAmount(new BigDecimal("0"));
                    salesRegister.setBalanceamount(new BigDecimal("0"));

                    salesRegister.setTrxDate(getDate());

                    SalesRegistryWorker.createOrUpdateSalesRegister(salesRegister, ControllerOptions.getSession());

                    //Files.move(file, newFile, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    Logger.getLogger(PdfFileViewerPanel.class.getName()).log(Level.SEVERE, null, ex);
                }        // TODO add your handling code here:
                if (fileList.getModel().getSize() > (index + 1)) {
                    index = index + 1;
                    String filePath = (String) fileList.getModel().getElementAt(index);
                    txtSourceFilePdf.setText(filePath);
                    controller.openDocument(filePath);
                }
                if (currFileName != null) {
                    try {
                        FileUtils.getFileUtils().rename(new File(currFileName), new File(txtSourceFile.getText() + "\\" + txtInvoiceNo.getText() + ".pdf"));
                    } catch (IOException ex) {
                        Logger.getLogger(PdfFileViewerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    currFileName = (String) fileList.getModel().getElementAt(index);
                    txtInvoiceNo.setText("");
                    txtInvoiceNo.requestFocus();
                    if (!cbSorted.isSelected()) {
                        panelPartyPickerEditPanel.textIdField.setText("");
                        panelPartyPickerEditPanel.textIdField.requestFocus();
                    }

                }
            } else {
                OrderMaxOptionPane.showMessageDialog(null, "Party doesn't exists: " + partyId);
            }
        } else {
            OrderMaxOptionPane.showMessageDialog(null, "Party or Invoice number empty: ");
        }

        setCaption();
    }//GEN-LAST:event_btnRenameandMoveActionPerformed
    public void walk(List<String> result, String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                walk(result, f.getAbsolutePath());
                System.out.println("Dir:" + f.getAbsoluteFile());
            } else {
                String ext = getFileExtension(f);//FileNameUtils.getExtension(listOfFiles[i].getPath());
                if ("pdf".equals(ext)) {

                    if (f.getName().length() < 10) {
                        result.add(f.getPath());
                        // fileList.getModel().
                        System.out.println("File " + f.getName());
                    }

                }
            }
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        List<String> result = new ArrayList<String>();
        walk(result, txtSourceFile.getText());
        DefaultListModel przyklad = new DefaultListModel();
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        for (String fileName : result) {

            String[] splittedFileName = fileName.split(pattern);
            //String fileNameWithOutExt = FilenameUtils.removeExtension(fileNameWithExt);
            String invoiceId = splittedFileName[splittedFileName.length - 1];
            String partyId = splittedFileName[splittedFileName.length - 2];
            Debug.logInfo("simpleFileName " + getFileName(invoiceId) + "  simpleDirName: " + partyId, module);
            saveSalesRegistry(partyId, getFileName(invoiceId), BigDecimal.ZERO, BigDecimal.ZERO, null);
            przyklad.addElement(fileName);
        }
        fileList.setModel(przyklad);
    }//GEN-LAST:event_jButton3ActionPerformed

    void saveSalesRegistry(String partyId, String relatedId, BigDecimal amount, BigDecimal balace,  Timestamp date) {
        SalesRegister salesRegister = new SalesRegister();
        salesRegister.setPartyId(partyId);
        salesRegister.setTrxTypeId("SALES_INVOICE");
        salesRegister.setRelatedId(relatedId);
        salesRegister.setAmount(amount);
        salesRegister.setBalanceamount(balace);
        salesRegister.setTrxDate(date);
        SalesRegistryWorker.createOrUpdateSalesRegister(salesRegister, ControllerOptions.getSession());
    }

    Account account = null;

    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);

        } catch (Exception e) {
            return "";
        }

    }

    private String getFileName(String name) {
        // String name = file.getName();
        try {
            // return name.substring(name.lastIndexOf(".") + 1);
            int pos = name.lastIndexOf(".");
            if (pos > 0) {
                name = name.substring(0, pos);
            }
        } catch (Exception e) {
            return "";
        }

        return name;
    }

    protected String getUniqueFileName(String basePath, String accountId, String invoiceNum, int index) {
        String newFilePath = basePath + "\\" + accountId + "\\" + invoiceNum;
        if (index != 0) {
            newFilePath = newFilePath.concat("_").concat(String.valueOf(index));
        }
        newFilePath = newFilePath.concat(".pdf");

        File f = new File(newFilePath);
        if (!f.exists() && !f.isDirectory()) {
            return newFilePath;
        }
        OrderMaxOptionPane.showMessageDialog(null, "File Exists: " + newFilePath);
//        Debug.logInfo("File Exists: " + newFilePath, accountId);
        return getUniqueFileName(basePath, accountId, invoiceNum, ++index);
    }

    public void setAccount(Account account) {
        this.account = account;
        if (account != null) {
            partyPickerEditPanel.textIdField.setText(account.getParty().getpartyId());
        }

    }

    public Timestamp getDate() {

        try {
            Timestamp fromDate = null;

            Object startdate = Formats.TIMESTAMP.parseValue(jTxtStartDate.getText());
            if (startdate != null) {
                fromDate = new Timestamp(((java.util.Date) startdate).getTime());
            }
            return fromDate;

        } catch (BasicException ex) {
            Logger.getLogger(JParamsStartAndEndDatesInterval.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    void renameFile(String oldfileName, String newFileName) {
        File oldfile = new File(oldfileName);
        File newfile = new File(newFileName);

        if (oldfile.renameTo(newfile)) {
            System.out.println("Rename succesful");
        } else {
            System.out.println("Rename failed");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDateStart;
    public javax.swing.JButton btnFind;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevioud;
    private javax.swing.JButton btnRenameandMove;
    private javax.swing.JButton btnRenameandSave;
    private javax.swing.JButton btnSmallIcon;
    private javax.swing.JButton btnSmallIcon1;
    private javax.swing.JButton btnSmallIcon2;
    public javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox cbLoadUnprocessed;
    private javax.swing.JCheckBox cbSorted;
    public javax.swing.JButton deleteButton;
    public javax.swing.JButton deleteButton1;
    private javax.swing.JList fileList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTxtStartDate;
    private javax.swing.JLabel lblCaption;
    public javax.swing.JButton newButton;
    public javax.swing.JButton okButton;
    private javax.swing.JPanel paneOrderStatus;
    private javax.swing.JPanel paneRoleType;
    private javax.swing.JPanel panelDetailView;
    private javax.swing.JPanel panelOrderId;
    private javax.swing.JPanel panelOrderType;
    private javax.swing.JPanel panelPartyId;
    private javax.swing.JPanel pdfPanel;
    private javax.swing.JPanel pdfPanelPartyId;
    public javax.swing.JButton saveButton;
    private javax.swing.JTextField txtBalanceAmount;
    private javax.swing.JTextField txtDestinationDir;
    private javax.swing.JTextField txtInvoiceAmount;
    private javax.swing.JTextField txtInvoiceNo;
    private javax.swing.JTextField txtSourceFile;
    private javax.swing.JTextField txtSourceFilePdf;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return okButton;
    }

    @Override
    public JButton getCancelButton() {
        return cancelButton;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    public void setListModal(ListAdapterListModel<OrderSummaryView> list) {
        tablePanel.setListModal(list);
    }

    public List<EntityCondition> getWhereClauseCond() {
        return ReportBaseMain.getWhereClauseCond(filterList);
    }
}
