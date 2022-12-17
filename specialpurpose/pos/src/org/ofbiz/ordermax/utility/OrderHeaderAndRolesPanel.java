package org.ofbiz.ordermax.utility;


import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.OrderHeaderAndRoles;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class OrderHeaderAndRolesPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = OrderHeaderAndRolesPanel.class.getName();
    private OrderHeaderAndRoles orderheaderandroles;
    private javax.swing.JLabel grandTotalLabel;
    private javax.swing.JTextField grandTotalTextField;
    private javax.swing.JLabel orderNameLabel;
    private javax.swing.JTextField orderNameTextField;
    private javax.swing.JLabel autoOrderShoppingListIdLabel;
    private javax.swing.JTextField autoOrderShoppingListIdTextField;
    private javax.swing.JLabel priorityLabel;
    private javax.swing.JTextField priorityTextField;
    private javax.swing.JLabel externalIdLabel;
    private javax.swing.JTextField externalIdTextField;
    private javax.swing.JLabel needsInventoryIssuanceLabel;
    private javax.swing.JTextField needsInventoryIssuanceTextField;
    private javax.swing.JLabel currencyUomLabel;
    private javax.swing.JTextField currencyUomTextField;
    private javax.swing.JLabel isRushOrderLabel;
    private javax.swing.JTextField isRushOrderTextField;
    private javax.swing.JLabel visitIdLabel;
    private javax.swing.JTextField visitIdTextField;
    private javax.swing.JLabel salesChannelEnumIdLabel;
    private javax.swing.JTextField salesChannelEnumIdTextField;
    private javax.swing.JLabel partyIdLabel;
    private javax.swing.JTextField partyIdTextField;
    private javax.swing.JLabel pickSheetPrintedDateLabel;
    private javax.swing.JTextField pickSheetPrintedDateTextField;
    private javax.swing.JLabel terminalIdLabel;
    private javax.swing.JTextField terminalIdTextField;
    private javax.swing.JLabel remainingSubTotalLabel;
    private javax.swing.JTextField remainingSubTotalTextField;
    private javax.swing.JLabel syncStatusIdLabel;
    private javax.swing.JTextField syncStatusIdTextField;
    private javax.swing.JLabel orderIdLabel;
    private javax.swing.JTextField orderIdTextField;
    private javax.swing.JLabel billingAccountIdLabel;
    private javax.swing.JTextField billingAccountIdTextField;
    private javax.swing.JLabel webSiteIdLabel;
    private javax.swing.JTextField webSiteIdTextField;
    private javax.swing.JLabel orderDateLabel;
    private javax.swing.JTextField orderDateTextField;
    private javax.swing.JLabel isViewedLabel;
    private javax.swing.JTextField isViewedTextField;
    private javax.swing.JLabel orderTypeIdLabel;
    private javax.swing.JTextField orderTypeIdTextField;
    private javax.swing.JLabel statusIdLabel;
    private javax.swing.JTextField statusIdTextField;
    private javax.swing.JLabel entryDateLabel;
    private javax.swing.JTextField entryDateTextField;
    private javax.swing.JLabel roleTypeIdLabel;
    private javax.swing.JTextField roleTypeIdTextField;
    private javax.swing.JLabel firstAttemptOrderIdLabel;
    private javax.swing.JTextField firstAttemptOrderIdTextField;
    private javax.swing.JLabel originFacilityIdLabel;
    private javax.swing.JTextField originFacilityIdTextField;
    private javax.swing.JLabel createdByLabel;
    private javax.swing.JTextField createdByTextField;
    private javax.swing.JLabel transactionIdLabel;
    private javax.swing.JTextField transactionIdTextField;
    private javax.swing.JLabel internalCodeLabel;
    private javax.swing.JTextField internalCodeTextField;
    private javax.swing.JLabel productStoreIdLabel;
    private javax.swing.JTextField productStoreIdTextField;
    private JButton button;
    private ComponentBorder cb;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }
    private boolean isModified = false;

    public OrderHeaderAndRolesPanel(OrderHeaderAndRoles val) {

        this.orderheaderandroles = val;

        initComponents();

    }

    public OrderHeaderAndRolesPanel() {

        initComponents();

    }

    private void initComponents() {

        grandTotalLabel = new javax.swing.JLabel();

        grandTotalTextField = new javax.swing.JTextField();

        grandTotalTextField.getDocument().addDocumentListener(new TextChangeListner());

        orderNameLabel = new javax.swing.JLabel();

        orderNameTextField = new javax.swing.JTextField();

        orderNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        autoOrderShoppingListIdLabel = new javax.swing.JLabel();

        autoOrderShoppingListIdTextField = new javax.swing.JTextField();

        autoOrderShoppingListIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(autoOrderShoppingListIdTextField);

        button.addActionListener(new LookupActionListner(autoOrderShoppingListIdTextField, "autoOrderShoppingListIdTextField"));

        priorityLabel = new javax.swing.JLabel();

        priorityTextField = new javax.swing.JTextField();

        priorityTextField.getDocument().addDocumentListener(new TextChangeListner());

        externalIdLabel = new javax.swing.JLabel();

        externalIdTextField = new javax.swing.JTextField();

        externalIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(externalIdTextField);

        button.addActionListener(new LookupActionListner(externalIdTextField, "externalIdTextField"));

        needsInventoryIssuanceLabel = new javax.swing.JLabel();

        needsInventoryIssuanceTextField = new javax.swing.JTextField();

        needsInventoryIssuanceTextField.getDocument().addDocumentListener(new TextChangeListner());

        currencyUomLabel = new javax.swing.JLabel();

        currencyUomTextField = new javax.swing.JTextField();

        currencyUomTextField.getDocument().addDocumentListener(new TextChangeListner());

        isRushOrderLabel = new javax.swing.JLabel();

        isRushOrderTextField = new javax.swing.JTextField();

        isRushOrderTextField.getDocument().addDocumentListener(new TextChangeListner());

        visitIdLabel = new javax.swing.JLabel();

        visitIdTextField = new javax.swing.JTextField();

        visitIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(visitIdTextField);

        button.addActionListener(new LookupActionListner(visitIdTextField, "visitIdTextField"));

        salesChannelEnumIdLabel = new javax.swing.JLabel();

        salesChannelEnumIdTextField = new javax.swing.JTextField();

        salesChannelEnumIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(salesChannelEnumIdTextField);

        button.addActionListener(new LookupActionListner(salesChannelEnumIdTextField, "salesChannelEnumIdTextField"));

        partyIdLabel = new javax.swing.JLabel();

        partyIdTextField = new javax.swing.JTextField();

        partyIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(partyIdTextField);

        button.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField"));

        pickSheetPrintedDateLabel = new javax.swing.JLabel();

        pickSheetPrintedDateTextField = new javax.swing.JTextField();

        pickSheetPrintedDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        terminalIdLabel = new javax.swing.JLabel();

        terminalIdTextField = new javax.swing.JTextField();

        terminalIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(terminalIdTextField);

        button.addActionListener(new LookupActionListner(terminalIdTextField, "terminalIdTextField"));

        remainingSubTotalLabel = new javax.swing.JLabel();

        remainingSubTotalTextField = new javax.swing.JTextField();

        remainingSubTotalTextField.getDocument().addDocumentListener(new TextChangeListner());

        syncStatusIdLabel = new javax.swing.JLabel();

        syncStatusIdTextField = new javax.swing.JTextField();

        syncStatusIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(syncStatusIdTextField);

        button.addActionListener(new LookupActionListner(syncStatusIdTextField, "syncStatusIdTextField"));

        orderIdLabel = new javax.swing.JLabel();

        orderIdTextField = new javax.swing.JTextField();

        orderIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(orderIdTextField);

        button.addActionListener(new LookupActionListner(orderIdTextField, "orderIdTextField"));

        billingAccountIdLabel = new javax.swing.JLabel();

        billingAccountIdTextField = new javax.swing.JTextField();

        billingAccountIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(billingAccountIdTextField);

        button.addActionListener(new LookupActionListner(billingAccountIdTextField, "billingAccountIdTextField"));

        webSiteIdLabel = new javax.swing.JLabel();

        webSiteIdTextField = new javax.swing.JTextField();

        webSiteIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(webSiteIdTextField);

        button.addActionListener(new LookupActionListner(webSiteIdTextField, "webSiteIdTextField"));

        orderDateLabel = new javax.swing.JLabel();

        orderDateTextField = new javax.swing.JTextField();

        orderDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        isViewedLabel = new javax.swing.JLabel();

        isViewedTextField = new javax.swing.JTextField();

        isViewedTextField.getDocument().addDocumentListener(new TextChangeListner());

        orderTypeIdLabel = new javax.swing.JLabel();

        orderTypeIdTextField = new javax.swing.JTextField();

        orderTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(orderTypeIdTextField);

        button.addActionListener(new LookupActionListner(orderTypeIdTextField, "orderTypeIdTextField"));

        statusIdLabel = new javax.swing.JLabel();

        statusIdTextField = new javax.swing.JTextField();

        statusIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(statusIdTextField);

        button.addActionListener(new LookupActionListner(statusIdTextField, "statusIdTextField"));

        entryDateLabel = new javax.swing.JLabel();

        entryDateTextField = new javax.swing.JTextField();

        entryDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        roleTypeIdLabel = new javax.swing.JLabel();

        roleTypeIdTextField = new javax.swing.JTextField();

        roleTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(roleTypeIdTextField);

        button.addActionListener(new LookupActionListner(roleTypeIdTextField, "roleTypeIdTextField"));

        firstAttemptOrderIdLabel = new javax.swing.JLabel();

        firstAttemptOrderIdTextField = new javax.swing.JTextField();

        firstAttemptOrderIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(firstAttemptOrderIdTextField);

        button.addActionListener(new LookupActionListner(firstAttemptOrderIdTextField, "firstAttemptOrderIdTextField"));

        originFacilityIdLabel = new javax.swing.JLabel();

        originFacilityIdTextField = new javax.swing.JTextField();

        originFacilityIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(originFacilityIdTextField);

        button.addActionListener(new LookupActionListner(originFacilityIdTextField, "originFacilityIdTextField"));

        createdByLabel = new javax.swing.JLabel();

        createdByTextField = new javax.swing.JTextField();

        createdByTextField.getDocument().addDocumentListener(new TextChangeListner());

        transactionIdLabel = new javax.swing.JLabel();

        transactionIdTextField = new javax.swing.JTextField();

        transactionIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(transactionIdTextField);

        button.addActionListener(new LookupActionListner(transactionIdTextField, "transactionIdTextField"));

        internalCodeLabel = new javax.swing.JLabel();

        internalCodeTextField = new javax.swing.JTextField();

        internalCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

        productStoreIdLabel = new javax.swing.JLabel();

        productStoreIdTextField = new javax.swing.JTextField();

        productStoreIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(productStoreIdTextField);

        button.addActionListener(new LookupActionListner(productStoreIdTextField, "productStoreIdTextField"));

        grandTotalLabel.setText("Grand Total:");

        orderNameLabel.setText("Order Name:");

        autoOrderShoppingListIdLabel.setText("Auto Order Shopping List Id:");

        priorityLabel.setText("Priority:");

        externalIdLabel.setText("External Id:");

        needsInventoryIssuanceLabel.setText("Needs Inventory Issuance:");

        currencyUomLabel.setText("Currency Uom:");

        isRushOrderLabel.setText("Is Rush Order:");

        visitIdLabel.setText("Visit Id:");

        salesChannelEnumIdLabel.setText("Sales Channel Enum Id:");

        partyIdLabel.setText("Party Id:");

        pickSheetPrintedDateLabel.setText("Pick Sheet Printed Date:");

        terminalIdLabel.setText("Terminal Id:");

        remainingSubTotalLabel.setText("Remaining Sub Total:");

        syncStatusIdLabel.setText("Sync Status Id:");

        orderIdLabel.setText("Order Id:");

        billingAccountIdLabel.setText("Billing Account Id:");

        webSiteIdLabel.setText("Web Site Id:");

        orderDateLabel.setText("Order Date:");

        isViewedLabel.setText("Is Viewed:");

        orderTypeIdLabel.setText("Order Type Id:");

        statusIdLabel.setText("Status Id:");

        entryDateLabel.setText("Entry Date:");

        roleTypeIdLabel.setText("Role Type Id:");

        firstAttemptOrderIdLabel.setText("First Attempt Order Id:");

        originFacilityIdLabel.setText("Origin Facility Id:");

        createdByLabel.setText("Created By:");

        transactionIdLabel.setText("Transaction Id:");

        internalCodeLabel.setText("Internal Code:");

        productStoreIdLabel.setText("Product Store Id:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(originFacilityIdLabel)
                .add(productStoreIdLabel)
                .add(transactionIdLabel)
                .add(billingAccountIdLabel)
                .add(visitIdLabel)
                .add(externalIdLabel)
                .add(statusIdLabel)
                .add(terminalIdLabel)
                .add(syncStatusIdLabel)
                .add(roleTypeIdLabel)
                .add(orderTypeIdLabel)
                .add(partyIdLabel)
                .add(autoOrderShoppingListIdLabel)
                .add(firstAttemptOrderIdLabel)
                .add(webSiteIdLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(originFacilityIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productStoreIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(transactionIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(billingAccountIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(visitIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(externalIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(statusIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(terminalIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(syncStatusIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(orderTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(partyIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(autoOrderShoppingListIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(firstAttemptOrderIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(webSiteIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(salesChannelEnumIdLabel)
                .add(orderIdLabel)
                .add(needsInventoryIssuanceLabel)
                .add(pickSheetPrintedDateLabel)
                .add(orderDateLabel)
                .add(orderNameLabel)
                .add(grandTotalLabel)
                .add(currencyUomLabel)
                .add(internalCodeLabel)
                .add(entryDateLabel)
                .add(remainingSubTotalLabel)
                .add(createdByLabel)
                .add(isViewedLabel)
                .add(priorityLabel)
                .add(isRushOrderLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(salesChannelEnumIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(orderIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(needsInventoryIssuanceTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(pickSheetPrintedDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(orderDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(orderNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(grandTotalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(currencyUomTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(internalCodeTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(entryDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(remainingSubTotalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(createdByTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(isViewedTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(priorityTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(isRushOrderTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(originFacilityIdLabel)
                .add(originFacilityIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(salesChannelEnumIdLabel)
                .add(salesChannelEnumIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(productStoreIdLabel)
                .add(productStoreIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(orderIdLabel)
                .add(orderIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(transactionIdLabel)
                .add(transactionIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(needsInventoryIssuanceLabel)
                .add(needsInventoryIssuanceTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(billingAccountIdLabel)
                .add(billingAccountIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(pickSheetPrintedDateLabel)
                .add(pickSheetPrintedDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(visitIdLabel)
                .add(visitIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(orderDateLabel)
                .add(orderDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(externalIdLabel)
                .add(externalIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(orderNameLabel)
                .add(orderNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(statusIdLabel)
                .add(statusIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(grandTotalLabel)
                .add(grandTotalTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(terminalIdLabel)
                .add(terminalIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(currencyUomLabel)
                .add(currencyUomTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(syncStatusIdLabel)
                .add(syncStatusIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(internalCodeLabel)
                .add(internalCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(roleTypeIdLabel)
                .add(roleTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(entryDateLabel)
                .add(entryDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(orderTypeIdLabel)
                .add(orderTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(remainingSubTotalLabel)
                .add(remainingSubTotalTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(partyIdLabel)
                .add(partyIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(createdByLabel)
                .add(createdByTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(autoOrderShoppingListIdLabel)
                .add(autoOrderShoppingListIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(isViewedLabel)
                .add(isViewedTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(firstAttemptOrderIdLabel)
                .add(firstAttemptOrderIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(priorityLabel)
                .add(priorityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(webSiteIdLabel)
                .add(webSiteIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(isRushOrderLabel)
                .add(isRushOrderTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws java.text.ParseException {

        externalIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getexternalId()));

        statusIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getstatusId()));

        entryDateTextField.setText(OrderMaxUtility.getValidTimestamp(orderheaderandroles.getentryDate()));

        terminalIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getterminalId()));

        needsInventoryIssuanceTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getneedsInventoryIssuance()));

        partyIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getpartyId()));

        billingAccountIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getbillingAccountId()));

        roleTypeIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getroleTypeId()));

        firstAttemptOrderIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getfirstAttemptOrderId()));

        productStoreIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getproductStoreId()));

        orderIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getorderId()));

        syncStatusIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getsyncStatusId()));

        createdByTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getcreatedBy()));

        isRushOrderTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getisRushOrder()));

        autoOrderShoppingListIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getautoOrderShoppingListId()));

        isViewedTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getisViewed()));

        internalCodeTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getinternalCode()));

        currencyUomTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getcurrencyUom()));

        orderDateTextField.setText(OrderMaxUtility.getValidTimestamp(orderheaderandroles.getorderDate()));

        grandTotalTextField.setText(OrderMaxUtility.getValidBigDecimal(orderheaderandroles.getgrandTotal()));

        orderTypeIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getorderTypeId()));

        originFacilityIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getoriginFacilityId()));

        webSiteIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getwebSiteId()));

        priorityTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getpriority()));

        salesChannelEnumIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getsalesChannelEnumId()));

        remainingSubTotalTextField.setText(OrderMaxUtility.getValidBigDecimal(orderheaderandroles.getremainingSubTotal()));

        orderNameTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getorderName()));

        pickSheetPrintedDateTextField.setText(OrderMaxUtility.getValidTimestamp(orderheaderandroles.getpickSheetPrintedDate()));

        visitIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.getvisitId()));

        transactionIdTextField.setText(OrderMaxUtility.getValidString(orderheaderandroles.gettransactionId()));

    }

    public void getUIFields() throws  java.text.ParseException {

        orderheaderandroles.setexternalId(OrderMaxUtility.getValidString(externalIdTextField.getText()));

        orderheaderandroles.setstatusId(OrderMaxUtility.getValidString(statusIdTextField.getText()));

        orderheaderandroles.setentryDate(OrderMaxUtility.getValidTimestamp(entryDateTextField.getText()));

        orderheaderandroles.setterminalId(OrderMaxUtility.getValidString(terminalIdTextField.getText()));

        orderheaderandroles.setneedsInventoryIssuance(OrderMaxUtility.getValidString(needsInventoryIssuanceTextField.getText()));

        orderheaderandroles.setpartyId(OrderMaxUtility.getValidString(partyIdTextField.getText()));

        orderheaderandroles.setbillingAccountId(OrderMaxUtility.getValidString(billingAccountIdTextField.getText()));

        orderheaderandroles.setroleTypeId(OrderMaxUtility.getValidString(roleTypeIdTextField.getText()));

        orderheaderandroles.setfirstAttemptOrderId(OrderMaxUtility.getValidString(firstAttemptOrderIdTextField.getText()));

        orderheaderandroles.setproductStoreId(OrderMaxUtility.getValidString(productStoreIdTextField.getText()));

        orderheaderandroles.setorderId(OrderMaxUtility.getValidString(orderIdTextField.getText()));

        orderheaderandroles.setsyncStatusId(OrderMaxUtility.getValidString(syncStatusIdTextField.getText()));

        orderheaderandroles.setcreatedBy(OrderMaxUtility.getValidString(createdByTextField.getText()));

        orderheaderandroles.setisRushOrder(OrderMaxUtility.getValidString(isRushOrderTextField.getText()));

        orderheaderandroles.setautoOrderShoppingListId(OrderMaxUtility.getValidString(autoOrderShoppingListIdTextField.getText()));

        orderheaderandroles.setisViewed(OrderMaxUtility.getValidString(isViewedTextField.getText()));

        orderheaderandroles.setinternalCode(OrderMaxUtility.getValidString(internalCodeTextField.getText()));

        orderheaderandroles.setcurrencyUom(OrderMaxUtility.getValidString(currencyUomTextField.getText()));

        orderheaderandroles.setorderDate(OrderMaxUtility.getValidTimestamp(orderDateTextField.getText()));

        orderheaderandroles.setgrandTotal(OrderMaxUtility.getValidBigDecimal(grandTotalTextField.getText()));

        orderheaderandroles.setorderTypeId(OrderMaxUtility.getValidString(orderTypeIdTextField.getText()));

        orderheaderandroles.setoriginFacilityId(OrderMaxUtility.getValidString(originFacilityIdTextField.getText()));

        orderheaderandroles.setwebSiteId(OrderMaxUtility.getValidString(webSiteIdTextField.getText()));

        orderheaderandroles.setpriority(OrderMaxUtility.getValidString(priorityTextField.getText()));

        orderheaderandroles.setsalesChannelEnumId(OrderMaxUtility.getValidString(salesChannelEnumIdTextField.getText()));

        orderheaderandroles.setremainingSubTotal(OrderMaxUtility.getValidBigDecimal(remainingSubTotalTextField.getText()));

        orderheaderandroles.setorderName(OrderMaxUtility.getValidString(orderNameTextField.getText()));

        orderheaderandroles.setpickSheetPrintedDate(OrderMaxUtility.getValidTimestamp(pickSheetPrintedDateTextField.getText()));

        orderheaderandroles.setvisitId(OrderMaxUtility.getValidString(visitIdTextField.getText()));

        orderheaderandroles.settransactionId(OrderMaxUtility.getValidString(transactionIdTextField.getText()));

    }

    public static void createAndShowUI(OrderHeaderAndRoles val) {

        try {

            OrderHeaderAndRolesPanel panel = new OrderHeaderAndRolesPanel(val);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        } catch (java.text.ParseException ex) {

            Debug.logError(ex, module);

        }

    }

    @Override
    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        OrderHeaderAndRoles newObj = null;

        if (baseVal != null) {

            newObj = new OrderHeaderAndRoles(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new OrderHeaderAndRoles();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return orderheaderandroles;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof OrderHeaderAndRoles) {

            OrderHeaderAndRoles newObj = (OrderHeaderAndRoles) uiObject;

            orderheaderandroles = newObj;

            try {

                orderheaderandroles.setGenericValue();

            } catch (Exception ex) {
//Debug.logError (ex, module);
            }

        }

    }

    public JPanel getContainerPanel() {
        return this;
    }

    private class TextChangeListner implements DocumentListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
        }

        public void changedUpdate(DocumentEvent e) {

            warn(e);

        }

        public void removeUpdate(DocumentEvent e) {

            warn(e);

        }

        public void insertUpdate(DocumentEvent e) {

            warn(e);

        }

        void warn(DocumentEvent e) {

            isModified = true;

        }
    }
}
//calling function copy and paste

/*

 try {



 Delegator delegator = XuiContainer.getSession().getDelegator();

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("OrderHeaderAndRoles", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),OrderHeaderAndRoles.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.OrderHeaderAndRolesPanel( ); 

 Object uiObj = panel.createUIObject(val);

 panel.changeUIObject(uiObj);

 panel.setUIFields();

 dlg.setChildPanelInterface(panel);

 OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());

 dlg.setLocationRelativeTo(null);

 dlg.pack();

 dlg.setVisible(true);

 } catch (ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 } catch (java.text.ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 }

 */
