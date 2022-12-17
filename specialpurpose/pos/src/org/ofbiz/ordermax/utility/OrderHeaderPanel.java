package org.ofbiz.ordermax.utility;


import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.OrderHeader;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;

public class OrderHeaderPanel extends JPanel implements GenericValuePanelInterfaceOrderMax{

public static final String module =OrderHeaderPanel.class.getName();

private OrderHeader orderheader;

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

public OrderHeaderPanel(OrderHeader  val ){

this.orderheader= val;

initComponents();

}

public OrderHeaderPanel(){

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

 cb = new ComponentBorder( button );

cb.install(autoOrderShoppingListIdTextField);

button.addActionListener(new LookupActionListner(autoOrderShoppingListIdTextField, "autoOrderShoppingListIdTextField"  ));

priorityLabel = new javax.swing.JLabel();

priorityTextField = new javax.swing.JTextField();

priorityTextField.getDocument().addDocumentListener(new TextChangeListner());

externalIdLabel = new javax.swing.JLabel();

externalIdTextField = new javax.swing.JTextField();

externalIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(externalIdTextField);

button.addActionListener(new LookupActionListner(externalIdTextField, "externalIdTextField"  ));

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

 cb = new ComponentBorder( button );

cb.install(visitIdTextField);

button.addActionListener(new LookupActionListner(visitIdTextField, "visitIdTextField"  ));

salesChannelEnumIdLabel = new javax.swing.JLabel();

salesChannelEnumIdTextField = new javax.swing.JTextField();

salesChannelEnumIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(salesChannelEnumIdTextField);

button.addActionListener(new LookupActionListner(salesChannelEnumIdTextField, "salesChannelEnumIdTextField"  ));

pickSheetPrintedDateLabel = new javax.swing.JLabel();

pickSheetPrintedDateTextField = new javax.swing.JTextField();

pickSheetPrintedDateTextField.getDocument().addDocumentListener(new TextChangeListner());

terminalIdLabel = new javax.swing.JLabel();

terminalIdTextField = new javax.swing.JTextField();

terminalIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(terminalIdTextField);

button.addActionListener(new LookupActionListner(terminalIdTextField, "terminalIdTextField"  ));

remainingSubTotalLabel = new javax.swing.JLabel();

remainingSubTotalTextField = new javax.swing.JTextField();

remainingSubTotalTextField.getDocument().addDocumentListener(new TextChangeListner());

syncStatusIdLabel = new javax.swing.JLabel();

syncStatusIdTextField = new javax.swing.JTextField();

syncStatusIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(syncStatusIdTextField);

button.addActionListener(new LookupActionListner(syncStatusIdTextField, "syncStatusIdTextField"  ));

orderIdLabel = new javax.swing.JLabel();

orderIdTextField = new javax.swing.JTextField();

orderIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(orderIdTextField);

button.addActionListener(new LookupActionListner(orderIdTextField, "orderIdTextField"  ));

billingAccountIdLabel = new javax.swing.JLabel();

billingAccountIdTextField = new javax.swing.JTextField();

billingAccountIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(billingAccountIdTextField);

button.addActionListener(new LookupActionListner(billingAccountIdTextField, "billingAccountIdTextField"  ));

webSiteIdLabel = new javax.swing.JLabel();

webSiteIdTextField = new javax.swing.JTextField();

webSiteIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(webSiteIdTextField);

button.addActionListener(new LookupActionListner(webSiteIdTextField, "webSiteIdTextField"  ));

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

 cb = new ComponentBorder( button );

cb.install(orderTypeIdTextField);

button.addActionListener(new LookupActionListner(orderTypeIdTextField, "orderTypeIdTextField"  ));

statusIdLabel = new javax.swing.JLabel();

statusIdTextField = new javax.swing.JTextField();

statusIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(statusIdTextField);

button.addActionListener(new LookupActionListner(statusIdTextField, "statusIdTextField"  ));

entryDateLabel = new javax.swing.JLabel();

entryDateTextField = new javax.swing.JTextField();

entryDateTextField.getDocument().addDocumentListener(new TextChangeListner());

firstAttemptOrderIdLabel = new javax.swing.JLabel();

firstAttemptOrderIdTextField = new javax.swing.JTextField();

firstAttemptOrderIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(firstAttemptOrderIdTextField);

button.addActionListener(new LookupActionListner(firstAttemptOrderIdTextField, "firstAttemptOrderIdTextField"  ));

originFacilityIdLabel = new javax.swing.JLabel();

originFacilityIdTextField = new javax.swing.JTextField();

originFacilityIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(originFacilityIdTextField);

button.addActionListener(new LookupActionListner(originFacilityIdTextField, "originFacilityIdTextField"  ));

createdByLabel = new javax.swing.JLabel();

createdByTextField = new javax.swing.JTextField();

createdByTextField.getDocument().addDocumentListener(new TextChangeListner());

transactionIdLabel = new javax.swing.JLabel();

transactionIdTextField = new javax.swing.JTextField();

transactionIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(transactionIdTextField);

button.addActionListener(new LookupActionListner(transactionIdTextField, "transactionIdTextField"  ));

internalCodeLabel = new javax.swing.JLabel();

internalCodeTextField = new javax.swing.JTextField();

internalCodeTextField.getDocument().addDocumentListener(new TextChangeListner());

productStoreIdLabel = new javax.swing.JLabel();

productStoreIdTextField = new javax.swing.JTextField();

productStoreIdTextField.getDocument().addDocumentListener(new TextChangeListner());

 button = new JButton("...");

 cb = new ComponentBorder( button );

cb.install(productStoreIdTextField);

button.addActionListener(new LookupActionListner(productStoreIdTextField, "productStoreIdTextField"  ));

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

.add(orderTypeIdLabel)

.add(autoOrderShoppingListIdLabel)

.add(firstAttemptOrderIdLabel)

.add(webSiteIdLabel)

.add(salesChannelEnumIdLabel)

)

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

.add(orderTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(autoOrderShoppingListIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(firstAttemptOrderIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(webSiteIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

.add(salesChannelEnumIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

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

.add(isRushOrderLabel)

)

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

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

.add(isRushOrderTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)

)

.addContainerGap()

));

layoutPanel.setVerticalGroup(

            layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)

            .add(layoutPanel.createSequentialGroup()

                .addContainerGap()

                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(originFacilityIdLabel)

.add(originFacilityIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(orderIdLabel)

.add(orderIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(productStoreIdLabel)

.add(productStoreIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(needsInventoryIssuanceLabel)

.add(needsInventoryIssuanceTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(transactionIdLabel)

.add(transactionIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(pickSheetPrintedDateLabel)

.add(pickSheetPrintedDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(billingAccountIdLabel)

.add(billingAccountIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(orderDateLabel)

.add(orderDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(visitIdLabel)

.add(visitIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(orderNameLabel)

.add(orderNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(externalIdLabel)

.add(externalIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(grandTotalLabel)

.add(grandTotalTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(statusIdLabel)

.add(statusIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(currencyUomLabel)

.add(currencyUomTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(terminalIdLabel)

.add(terminalIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(internalCodeLabel)

.add(internalCodeTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(syncStatusIdLabel)

.add(syncStatusIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

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

.add(autoOrderShoppingListIdLabel)

.add(autoOrderShoppingListIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(createdByLabel)

.add(createdByTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(firstAttemptOrderIdLabel)

.add(firstAttemptOrderIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(isViewedLabel)

.add(isViewedTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(webSiteIdLabel)

.add(webSiteIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(priorityLabel)

.add(priorityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)

.add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)

.add(salesChannelEnumIdLabel)

.add(salesChannelEnumIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

.add(isRushOrderLabel)

.add(isRushOrderTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

.addContainerGap()

));

}

 

public void setUIFields() throws  java.text.ParseException {

externalIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getExternalId()));

statusIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getStatusId()));

entryDateTextField.setText( OrderMaxUtility.getValidTimestamp(orderheader.getEntryDate()));

terminalIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getTerminalId()));

needsInventoryIssuanceTextField.setText( OrderMaxUtility.getValidString(orderheader.getNeedsInventoryIssuance()));

billingAccountIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getBillingAccountId()));

firstAttemptOrderIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getFirstAttemptOrderId()));

productStoreIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getProductStoreId()));

orderIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getOrderId()));

syncStatusIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getSyncStatusId()));

createdByTextField.setText( OrderMaxUtility.getValidString(orderheader.getCreatedBy()));

isRushOrderTextField.setText( OrderMaxUtility.getValidString(orderheader.getIsRushOrder()));

autoOrderShoppingListIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getAutoOrderShoppingListId()));

isViewedTextField.setText( OrderMaxUtility.getValidString(orderheader.getIsViewed()));

internalCodeTextField.setText( OrderMaxUtility.getValidString(orderheader.getInternalCode()));

currencyUomTextField.setText( OrderMaxUtility.getValidString(orderheader.getCurrencyUom()));

orderDateTextField.setText( OrderMaxUtility.getValidTimestamp(orderheader.getOrderDate()));

grandTotalTextField.setText( OrderMaxUtility.getValidBigDecimal(orderheader.getGrandTotal()));

orderTypeIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getOrderTypeId()));

originFacilityIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getOriginFacilityId()));

webSiteIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getWebSiteId()));

priorityTextField.setText( OrderMaxUtility.getValidString(orderheader.getPriority()));

salesChannelEnumIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getSalesChannelEnumId()));

remainingSubTotalTextField.setText( OrderMaxUtility.getValidBigDecimal(orderheader.getRemainingSubTotal()));

orderNameTextField.setText( OrderMaxUtility.getValidString(orderheader.getOrderName()));

pickSheetPrintedDateTextField.setText( OrderMaxUtility.getValidTimestamp(orderheader.getPickSheetPrintedDate()));

visitIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getVisitId()));

transactionIdTextField.setText( OrderMaxUtility.getValidString(orderheader.getTransactionId()));

}

 

public void getUIFields() throws  java.text.ParseException {

orderheader.setExternalId( OrderMaxUtility.getValidString(externalIdTextField.getText()));

orderheader.setStatusId( OrderMaxUtility.getValidString(statusIdTextField.getText()));

orderheader.setEntryDate( OrderMaxUtility.getValidTimestamp(entryDateTextField.getText()));

orderheader.setTerminalId( OrderMaxUtility.getValidString(terminalIdTextField.getText()));

orderheader.setNeedsInventoryIssuance( OrderMaxUtility.getValidString(needsInventoryIssuanceTextField.getText()));

orderheader.setBillingAccountId( OrderMaxUtility.getValidString(billingAccountIdTextField.getText()));

orderheader.setFirstAttemptOrderId( OrderMaxUtility.getValidString(firstAttemptOrderIdTextField.getText()));

orderheader.setProductStoreId( OrderMaxUtility.getValidString(productStoreIdTextField.getText()));

orderheader.setOrderId( OrderMaxUtility.getValidString(orderIdTextField.getText()));

orderheader.setSyncStatusId( OrderMaxUtility.getValidString(syncStatusIdTextField.getText()));

orderheader.setCreatedBy( OrderMaxUtility.getValidString(createdByTextField.getText()));

orderheader.setIsRushOrder( OrderMaxUtility.getValidString(isRushOrderTextField.getText()));

orderheader.setAutoOrderShoppingListId( OrderMaxUtility.getValidString(autoOrderShoppingListIdTextField.getText()));

orderheader.setIsViewed( OrderMaxUtility.getValidString(isViewedTextField.getText()));

orderheader.setInternalCode( OrderMaxUtility.getValidString(internalCodeTextField.getText()));

orderheader.setCurrencyUom( OrderMaxUtility.getValidString(currencyUomTextField.getText()));

orderheader.setOrderDate( OrderMaxUtility.getValidTimestamp(orderDateTextField.getText()));

orderheader.setGrandTotal( OrderMaxUtility.getValidBigDecimal(grandTotalTextField.getText()));

orderheader.setOrderTypeId( OrderMaxUtility.getValidString(orderTypeIdTextField.getText()));

orderheader.setOriginFacilityId( OrderMaxUtility.getValidString(originFacilityIdTextField.getText()));

orderheader.setWebSiteId( OrderMaxUtility.getValidString(webSiteIdTextField.getText()));

orderheader.setPriority( OrderMaxUtility.getValidString(priorityTextField.getText()));

orderheader.setSalesChannelEnumId( OrderMaxUtility.getValidString(salesChannelEnumIdTextField.getText()));

orderheader.setRemainingSubTotal( OrderMaxUtility.getValidBigDecimal(remainingSubTotalTextField.getText()));

orderheader.setOrderName( OrderMaxUtility.getValidString(orderNameTextField.getText()));

orderheader.setPickSheetPrintedDate( OrderMaxUtility.getValidTimestamp(pickSheetPrintedDateTextField.getText()));

orderheader.setVisitId( OrderMaxUtility.getValidString(visitIdTextField.getText()));

orderheader.setTransactionId( OrderMaxUtility.getValidString(transactionIdTextField.getText()));

}

public static void createAndShowUI(OrderHeader val){

try {

OrderHeaderPanel panel = new OrderHeaderPanel(val);

JFrame frame = new JFrame("Test Gui");

frame.getContentPane().add(panel);

panel.setUIFields();

frame.pack();

frame.setLocationRelativeTo(null);

frame.setVisible(true);

}  catch (java.text.ParseException ex) {

    Debug.logError(ex,module);        

}

}

@Override

public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

OrderHeader newObj =null;

if (baseVal != null) {

 newObj = new OrderHeader(baseVal);

try { 

    newObj.setGenericValue();

} catch (Exception ex) {

Debug.logError (ex, module);

}

} else {

 newObj = new OrderHeader();

}

return newObj;

}

public GenericValueObjectInterface getUIObject() {

return orderheader;

}

@Override

public void changeUIObject(GenericValueObjectInterface uiObject) {

    if (uiObject instanceof OrderHeader) {

OrderHeader newObj = (OrderHeader) uiObject;

orderheader = newObj;

try { 

orderheader.setGenericValue();

} catch (Exception ex) {

//Debug.logError (ex, module);

}

 }

 }

public JPanel getContainerPanel(){ return this; }

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

List<GenericValue> genValList = PosProductHelper.getGenericValueLists("OrderHeader", delegator);

GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),OrderHeader.ColumnNameId);

dlg.setupOrderTableList(genValList);

GenericValue val = genValList.get(0);

GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.OrderHeaderPanel( ); 

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

