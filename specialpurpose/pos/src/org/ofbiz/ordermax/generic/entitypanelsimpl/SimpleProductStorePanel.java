/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic.entitypanelsimpl;

import org.ofbiz.ordermax.generic.GenericSaveInterface;
import com.openbravo.data.user.DirtyManager;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.JGenericListBoxSelectionModel;
import mvc.model.list.JYesNoComboBoxSelectionModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.ProductStoreGroup;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.ProductStoreGroupSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class SimpleProductStorePanel extends javax.swing.JPanel implements GenericSaveInterface {

    private JGenericListBoxSelectionModel<ProductStore> listSelectionModel = null;
    public JGenericComboBoxSelectionModel<Facility> facilityModel = null;
    public JGenericComboBoxSelectionModel<ProductStoreGroup> productStoreGroupModel = null;
    private JYesNoComboBoxSelectionModel isDemo = null;
    private JYesNoComboBoxSelectionModel oneInventoryFacility = null;
    private JYesNoComboBoxSelectionModel isImmediatelyFulfilled = null;
    private JYesNoComboBoxSelectionModel checkInventory = null;
    private JYesNoComboBoxSelectionModel requireInventory = null;
    private JYesNoComboBoxSelectionModel reserveInventory = null;
    private JYesNoComboBoxSelectionModel balanceResOnOrderCreation = null;
    ControllerOptions options = null;
    private DirtyManager dirty = new DirtyManager();
    String entityName = null;

    /**
     * Creates new form SimplePosTerminalPanel
     */
    public SimpleProductStorePanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        List<ProductStore> list = new ArrayList<ProductStore>();//PosTerminalSingleton.getValueList();
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        listSelectionModel = new JGenericListBoxSelectionModel<ProductStore>(list);
        recordList.setLayout(new BorderLayout());
        loadList();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 200);

        scrollPane.setViewportView(listSelectionModel.jlistBox);
        recordList.setLayout(new BorderLayout());
        recordList.add(BorderLayout.CENTER, scrollPane);
        ComponentBorder.loweredBevelBorder(recordList, "List");
        ComponentBorder.loweredBevelBorder(jPanel1, "Detail");

        listSelectionModel.jlistBox.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            setSelected(i);
                            break;
                        }
                    }
                }
            }
        });

        productStoreGroupModel = new JGenericComboBoxSelectionModel<ProductStoreGroup>(panelProductStoreGroup, ProductStoreGroupSingleton.getValueList());
        isDemo = new JYesNoComboBoxSelectionModel(panelDemo, dirty);

        //inventory
        facilityModel = new JGenericComboBoxSelectionModel<Facility>(panelFacilityId, FacilitySingleton.getValueList());
        oneInventoryFacility = new JYesNoComboBoxSelectionModel(paneloneInventoryFacility, dirty);
        isImmediatelyFulfilled = new JYesNoComboBoxSelectionModel(panelisImmediatelyFulfilled, dirty);
        checkInventory = new JYesNoComboBoxSelectionModel(panelcheckInventory, dirty);
        requireInventory = new JYesNoComboBoxSelectionModel(panelrequireInventory, dirty);
        reserveInventory = new JYesNoComboBoxSelectionModel(panelreserveInventory, dirty);
        balanceResOnOrderCreation = new JYesNoComboBoxSelectionModel(panelbalanceResOnOrderCreation, dirty);

        //shipping
        txtProductStoreId.getDocument().addDocumentListener(dirty);
        storeNameTextField.getDocument().addDocumentListener(dirty);
        companyNameTextField.getDocument().addDocumentListener(dirty);
        titleTextField.getDocument().addDocumentListener(dirty);
        subtitleTextField.getDocument().addDocumentListener(dirty);

        facilityModel.jComboBox.addActionListener(dirty);
        visualThemeIdTextField.getDocument().addDocumentListener(dirty);
        productStoreGroupModel.jComboBox.addActionListener(dirty);
        
        currentRecord = new ProductStore();
        setDialogField();

    }

    ProductStore currentRecord = null;

    public void setSelected(int index) {

        if (index < listSelectionModel.dataListModel.getSize()) {
            currentRecord = (ProductStore) listSelectionModel.dataListModel.getElementAt(index);
            setDialogField();
        }
    }

    public void setDialogField() {
        if (currentRecord != null) {

            txtProductStoreId.setText(OrderMaxUtility.getValidString(currentRecord.getproductStoreId()));
            companyNameTextField.setText(OrderMaxUtility.getValidString(currentRecord.getcompanyName()));
            titleTextField.setText(OrderMaxUtility.getValidString(currentRecord.gettitle()));
            storeNameTextField.setText(OrderMaxUtility.getValidString(currentRecord.getstoreName()));
            subtitleTextField.setText(OrderMaxUtility.getValidString(currentRecord.getsubtitle()));

            isDemo.setSelected(currentRecord.getisDemoStore());
            oneInventoryFacility.setSelected(currentRecord.getoneInventoryFacility());
            isImmediatelyFulfilled.setSelected(currentRecord.getisImmediatelyFulfilled());
            checkInventory.setSelected(currentRecord.getcheckInventory());
            requireInventory.setSelected(currentRecord.getrequireInventory());
            reserveInventory.setSelected(currentRecord.getreserveInventory());
            balanceResOnOrderCreation.setSelected(currentRecord.getbalanceResOnOrderCreation());

            if (UtilValidate.isNotEmpty(currentRecord.getprimaryStoreGroupId())) {
                try {
                    productStoreGroupModel.setSelectedItem(ProductStoreGroupSingleton.getProductStoreGroup(currentRecord.getprimaryStoreGroupId()));
                } catch (Exception ex) {
                    Logger.getLogger(SimpleProductStorePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            visualThemeIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getvisualThemeId()));
            /*  orderNumberPrefixTextField.setText(OrderMaxUtility.getValidString(currentRecord.getorderNumberPrefix()));

             vatTaxAuthPartyIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getvatTaxAuthPartyId()));

             itemDeclinedStatusTextField.setText(OrderMaxUtility.getValidString(currentRecord.getitemDeclinedStatus()));

             oneInventoryFacilityTextField.setText(OrderMaxUtility.getValidString(currentRecord.getoneInventoryFacility()));

             digitalItemApprovedStatusTextField.setText(OrderMaxUtility.getValidString(currentRecord.getdigitalItemApprovedStatus()));

             autoOrderCcTryExpTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoOrderCcTryExp()));


             headerApprovedStatusTextField.setText(OrderMaxUtility.getValidString(currentRecord.getheaderApprovedStatus()));

             oldHeaderRightBackgroundTextField.setText(OrderMaxUtility.getValidString(currentRecord.getoldHeaderRightBackground()));

             oldHeaderMiddleBackgroundTextField.setText(OrderMaxUtility.getValidString(currentRecord.getoldHeaderMiddleBackground()));

             requireInventoryTextField.setText(OrderMaxUtility.getValidString(currentRecord.getrequireInventory()));

             itemApprovedStatusTextField.setText(OrderMaxUtility.getValidString(currentRecord.getitemApprovedStatus()));

             headerDeclinedStatusTextField.setText(OrderMaxUtility.getValidString(currentRecord.getheaderDeclinedStatus()));

             storeCreditAccountEnumIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getstoreCreditAccountEnumId()));



             productStoreIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getproductStoreId()));

             selectPaymentTypePerItemTextField.setText(OrderMaxUtility.getValidString(currentRecord.getselectPaymentTypePerItem()));

             viewCartOnAddTextField.setText(OrderMaxUtility.getValidString(currentRecord.getviewCartOnAdd()));

             autoApproveReviewsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoApproveReviews()));

             authDeclinedMessageTextField.setText(OrderMaxUtility.getValidString(currentRecord.getauthDeclinedMessage()));

             defaultPasswordTextField.setText(OrderMaxUtility.getValidString(currentRecord.getdefaultPassword()));

             requireCustomerRoleTextField.setText(OrderMaxUtility.getValidString(currentRecord.getrequireCustomerRole()));

             enableDigProdUploadTextField.setText(OrderMaxUtility.getValidString(currentRecord.getenableDigProdUpload()));

             headerCancelStatusTextField.setText(OrderMaxUtility.getValidString(currentRecord.getheaderCancelStatus()));

             orderDecimalQuantityTextField.setText(OrderMaxUtility.getValidString(currentRecord.getorderDecimalQuantity()));

             digProdUploadCategoryIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getdigProdUploadCategoryId()));

             setOwnerUponIssuanceTextField.setText(OrderMaxUtility.getValidString(currentRecord.getsetOwnerUponIssuance()));

             balanceResOnOrderCreationTextField.setText(OrderMaxUtility.getValidString(currentRecord.getbalanceResOnOrderCreation()));

             autoOrderCcTryLaterMaxTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoOrderCcTryLaterMax()));


             vatTaxAuthGeoIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getvatTaxAuthGeoId()));

             requirementMethodEnumIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getrequirementMethodEnumId()));

             reqReturnInventoryReceiveTextField.setText(OrderMaxUtility.getValidString(currentRecord.getreqReturnInventoryReceive()));

             inventoryFacilityIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getinventoryFacilityId()));

             prorateShippingTextField.setText(OrderMaxUtility.getValidString(currentRecord.getprorateShipping()));

             payToPartyIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getpayToPartyId()));

             authErrorMessageTextField.setText(OrderMaxUtility.getValidString(currentRecord.getauthErrorMessage()));

             autoApproveOrderTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoApproveOrder()));

             daysToCancelNonPayTextField.setText(OrderMaxUtility.getValidLong(currentRecord.getdaysToCancelNonPay()));

             autoOrderCcTryOtherCardsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoOrderCcTryOtherCards()));

             shipIfCaptureFailsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getshipIfCaptureFails()));

             explodeOrderItemsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getexplodeOrderItems()));

             isDemoStoreTextField.setText(OrderMaxUtility.getValidString(currentRecord.getisDemoStore()));

             prodSearchExcludeVariantsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getprodSearchExcludeVariants()));

             addToCartReplaceUpsellTextField.setText(OrderMaxUtility.getValidString(currentRecord.getaddToCartReplaceUpsell()));

             addToCartRemoveIncompatTextField.setText(OrderMaxUtility.getValidString(currentRecord.getaddToCartRemoveIncompat()));

             showTaxIsExemptTextField.setText(OrderMaxUtility.getValidString(currentRecord.getshowTaxIsExempt()));

             itemCancelStatusTextField.setText(OrderMaxUtility.getValidString(currentRecord.getitemCancelStatus()));

             checkGcBalanceTextField.setText(OrderMaxUtility.getValidString(currentRecord.getcheckGcBalance()));


             allowPasswordTextField.setText(OrderMaxUtility.getValidString(currentRecord.getallowPassword()));

             reqShipAddrForDigItemsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getreqShipAddrForDigItems()));

             reserveOrderEnumIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getreserveOrderEnumId()));

             defaultCurrencyUomIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getdefaultCurrencyUomId()));

             autoOrderCcTryLaterNsfTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoOrderCcTryLaterNsf()));

             showOutOfStockProductsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getshowOutOfStockProducts()));

             showPricesWithVatTaxTextField.setText(OrderMaxUtility.getValidString(currentRecord.getshowPricesWithVatTax()));

             defaultLocaleStringTextField.setText(OrderMaxUtility.getValidString(currentRecord.getdefaultLocaleString()));

             prorateTaxesTextField.setText(OrderMaxUtility.getValidString(currentRecord.getprorateTaxes()));

             showCheckoutGiftOptionsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getshowCheckoutGiftOptions()));

             reserveInventoryTextField.setText(OrderMaxUtility.getValidString(currentRecord.getreserveInventory()));



             autoApproveInvoiceTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoApproveInvoice()));

             primaryStoreGroupIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getprimaryStoreGroupId()));

             enableAutoSuggestionListTextField.setText(OrderMaxUtility.getValidString(currentRecord.getenableAutoSuggestionList()));

             retryFailedAuthsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getretryFailedAuths()));

             usePrimaryEmailUsernameTextField.setText(OrderMaxUtility.getValidString(currentRecord.getusePrimaryEmailUsername()));

             autoSaveCartTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoSaveCart()));

             storeCreditValidDaysTextField.setText(OrderMaxUtility.getValidLong(currentRecord.getstoreCreditValidDays()));

             isImmediatelyFulfilledTextField.setText(OrderMaxUtility.getValidString(currentRecord.getisImmediatelyFulfilled()));

             autoInvoiceDigitalItemsTextField.setText(OrderMaxUtility.getValidString(currentRecord.getautoInvoiceDigitalItems()));

             oldHeaderLogoTextField.setText(OrderMaxUtility.getValidString(currentRecord.getoldHeaderLogo()));

             manualAuthIsCaptureTextField.setText(OrderMaxUtility.getValidString(currentRecord.getmanualAuthIsCapture()));

             splitPayPrefPerShpGrpTextField.setText(OrderMaxUtility.getValidString(currentRecord.getsplitPayPrefPerShpGrp()));

             defaultSalesChannelEnumIdTextField.setText(OrderMaxUtility.getValidString(currentRecord.getdefaultSalesChannelEnumId()));

             checkInventoryTextField.setText(OrderMaxUtility.getValidString(currentRecord.getcheckInventory()));

             authFraudMessageTextField.setText(OrderMaxUtility.getValidString(currentRecord.getauthFraudMessage()));

             managedByLotTextField.setText(OrderMaxUtility.getValidString(currentRecord.getmanagedByLot()));

             oldStyleSheetTextField.setText(OrderMaxUtility.getValidString(currentRecord.getoldStyleSheet()));
             */
            dirty.setDirty(false);
        }
    }

    public void getDialogFields() {

        if (currentRecord != null) {
            currentRecord.setproductStoreId(OrderMaxUtility.getValidString(txtProductStoreId.getText()));
            currentRecord.setcompanyName(OrderMaxUtility.getValidString(companyNameTextField.getText()));
            currentRecord.settitle(OrderMaxUtility.getValidString(titleTextField.getText()));
            currentRecord.setsubtitle(OrderMaxUtility.getValidString(subtitleTextField.getText()));
            currentRecord.setstoreName(OrderMaxUtility.getValidString(storeNameTextField.getText()));

            if (UtilValidate.isNotEmpty(productStoreGroupModel.getSelectedItem())) {
                try {
                    currentRecord.setprimaryStoreGroupId(productStoreGroupModel.getSelectedItem().getproductStoreGroupId());
                    //    productStoreGroupModel.setSelectedItem(ProductStoreGroupSingleton.getProductStoreGroup(currentRecord.getprimaryStoreGroupId()));
                } catch (Exception ex) {
                    Logger.getLogger(SimpleProductStorePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (isDemo.getSelectedItem() != null) {
                try {
                    String str = YesNoConditionSelectSingleton.getKeyFromDisplayName(isDemo.getSelectedItem());
                    currentRecord.setisDemoStore(str);//productEntity.getBoolean("taxable"));                
                } catch (Exception ex) {
                    Logger.getLogger(SimpleProductStorePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            currentRecord.setisDemoStore(isDemo.getSelected());
            currentRecord.setoneInventoryFacility(oneInventoryFacility.getSelected());
            currentRecord.setisImmediatelyFulfilled(isImmediatelyFulfilled.getSelected());
            currentRecord.setcheckInventory(checkInventory.getSelected());
            currentRecord.setrequireInventory(requireInventory.getSelected());
            currentRecord.setreserveInventory(reserveInventory.getSelected());
            currentRecord.setbalanceResOnOrderCreation(balanceResOnOrderCreation.getSelected());

            currentRecord.setvisualThemeId(OrderMaxUtility.getValidString(visualThemeIdTextField.getText()));

            try {
                if (UtilValidate.isNotEmpty(currentRecord.getinventoryFacilityId())) {
                    facilityModel.setSelectedItem(FacilitySingleton.getFacility(currentRecord.getinventoryFacilityId()));
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductStorePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                if (UtilValidate.isNotEmpty(facilityModel.getSelectedItem())) {
                    currentRecord.setinventoryFacilityId(facilityModel.getSelectedItem().getfacilityId());
                }
            } catch (Exception ex) {
                Logger.getLogger(SimpleProductStorePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*
             currentRecord.setorderNumberPrefix(OrderMaxUtility.getValidString(orderNumberPrefixTextField.getText()));

             currentRecord.setvatTaxAuthPartyId(OrderMaxUtility.getValidString(vatTaxAuthPartyIdTextField.getText()));

             currentRecord.setitemDeclinedStatus(OrderMaxUtility.getValidString(itemDeclinedStatusTextField.getText()));

             currentRecord.setoneInventoryFacility(OrderMaxUtility.getValidString(oneInventoryFacilityTextField.getText()));

             currentRecord.setdigitalItemApprovedStatus(OrderMaxUtility.getValidString(digitalItemApprovedStatusTextField.getText()));

             currentRecord.setautoOrderCcTryExp(OrderMaxUtility.getValidString(autoOrderCcTryExpTextField.getText()));


             currentRecord.setheaderApprovedStatus(OrderMaxUtility.getValidString(headerApprovedStatusTextField.getText()));

             currentRecord.setoldHeaderRightBackground(OrderMaxUtility.getValidString(oldHeaderRightBackgroundTextField.getText()));

             currentRecord.setoldHeaderMiddleBackground(OrderMaxUtility.getValidString(oldHeaderMiddleBackgroundTextField.getText()));

             currentRecord.setrequireInventory(OrderMaxUtility.getValidString(requireInventoryTextField.getText()));

             currentRecord.setitemApprovedStatus(OrderMaxUtility.getValidString(itemApprovedStatusTextField.getText()));

             currentRecord.setheaderDeclinedStatus(OrderMaxUtility.getValidString(headerDeclinedStatusTextField.getText()));

             currentRecord.setstoreCreditAccountEnumId(OrderMaxUtility.getValidString(storeCreditAccountEnumIdTextField.getText()));



             currentRecord.setproductStoreId(OrderMaxUtility.getValidString(productStoreIdTextField.getText()));

             currentRecord.setselectPaymentTypePerItem(OrderMaxUtility.getValidString(selectPaymentTypePerItemTextField.getText()));

             currentRecord.setviewCartOnAdd(OrderMaxUtility.getValidString(viewCartOnAddTextField.getText()));

             currentRecord.setautoApproveReviews(OrderMaxUtility.getValidString(autoApproveReviewsTextField.getText()));

             currentRecord.setauthDeclinedMessage(OrderMaxUtility.getValidString(authDeclinedMessageTextField.getText()));

             currentRecord.setdefaultPassword(OrderMaxUtility.getValidString(defaultPasswordTextField.getText()));

             currentRecord.setrequireCustomerRole(OrderMaxUtility.getValidString(requireCustomerRoleTextField.getText()));

             currentRecord.setenableDigProdUpload(OrderMaxUtility.getValidString(enableDigProdUploadTextField.getText()));

             currentRecord.setheaderCancelStatus(OrderMaxUtility.getValidString(headerCancelStatusTextField.getText()));

             currentRecord.setorderDecimalQuantity(OrderMaxUtility.getValidString(orderDecimalQuantityTextField.getText()));

             currentRecord.setdigProdUploadCategoryId(OrderMaxUtility.getValidString(digProdUploadCategoryIdTextField.getText()));

             currentRecord.setsetOwnerUponIssuance(OrderMaxUtility.getValidString(setOwnerUponIssuanceTextField.getText()));

             currentRecord.setbalanceResOnOrderCreation(OrderMaxUtility.getValidString(balanceResOnOrderCreationTextField.getText()));

             currentRecord.setautoOrderCcTryLaterMax(OrderMaxUtility.getValidString(autoOrderCcTryLaterMaxTextField.getText()));


             currentRecord.setvatTaxAuthGeoId(OrderMaxUtility.getValidString(vatTaxAuthGeoIdTextField.getText()));

             currentRecord.setrequirementMethodEnumId(OrderMaxUtility.getValidString(requirementMethodEnumIdTextField.getText()));

             currentRecord.setreqReturnInventoryReceive(OrderMaxUtility.getValidString(reqReturnInventoryReceiveTextField.getText()));

             currentRecord.setinventoryFacilityId(OrderMaxUtility.getValidString(inventoryFacilityIdTextField.getText()));

             currentRecord.setprorateShipping(OrderMaxUtility.getValidString(prorateShippingTextField.getText()));

             currentRecord.setpayToPartyId(OrderMaxUtility.getValidString(payToPartyIdTextField.getText()));

             currentRecord.setauthErrorMessage(OrderMaxUtility.getValidString(authErrorMessageTextField.getText()));

             currentRecord.setautoApproveOrder(OrderMaxUtility.getValidString(autoApproveOrderTextField.getText()));

             currentRecord.setdaysToCancelNonPay(OrderMaxUtility.getValidLong(daysToCancelNonPayTextField.getText()));

             currentRecord.setautoOrderCcTryOtherCards(OrderMaxUtility.getValidString(autoOrderCcTryOtherCardsTextField.getText()));

             currentRecord.setshipIfCaptureFails(OrderMaxUtility.getValidString(shipIfCaptureFailsTextField.getText()));

             currentRecord.setexplodeOrderItems(OrderMaxUtility.getValidString(explodeOrderItemsTextField.getText()));

             currentRecord.setisDemoStore(OrderMaxUtility.getValidString(isDemoStoreTextField.getText()));

             currentRecord.setprodSearchExcludeVariants(OrderMaxUtility.getValidString(prodSearchExcludeVariantsTextField.getText()));

             currentRecord.setaddToCartReplaceUpsell(OrderMaxUtility.getValidString(addToCartReplaceUpsellTextField.getText()));

             currentRecord.setaddToCartRemoveIncompat(OrderMaxUtility.getValidString(addToCartRemoveIncompatTextField.getText()));

             currentRecord.setshowTaxIsExempt(OrderMaxUtility.getValidString(showTaxIsExemptTextField.getText()));

             currentRecord.setitemCancelStatus(OrderMaxUtility.getValidString(itemCancelStatusTextField.getText()));

             currentRecord.setcheckGcBalance(OrderMaxUtility.getValidString(checkGcBalanceTextField.getText()));


             currentRecord.setallowPassword(OrderMaxUtility.getValidString(allowPasswordTextField.getText()));

             currentRecord.setreqShipAddrForDigItems(OrderMaxUtility.getValidString(reqShipAddrForDigItemsTextField.getText()));

             currentRecord.setreserveOrderEnumId(OrderMaxUtility.getValidString(reserveOrderEnumIdTextField.getText()));

             currentRecord.setdefaultCurrencyUomId(OrderMaxUtility.getValidString(defaultCurrencyUomIdTextField.getText()));

             currentRecord.setautoOrderCcTryLaterNsf(OrderMaxUtility.getValidString(autoOrderCcTryLaterNsfTextField.getText()));

             currentRecord.setshowOutOfStockProducts(OrderMaxUtility.getValidString(showOutOfStockProductsTextField.getText()));

             currentRecord.setshowPricesWithVatTax(OrderMaxUtility.getValidString(showPricesWithVatTaxTextField.getText()));

             currentRecord.setdefaultLocaleString(OrderMaxUtility.getValidString(defaultLocaleStringTextField.getText()));

             currentRecord.setprorateTaxes(OrderMaxUtility.getValidString(prorateTaxesTextField.getText()));

             currentRecord.setshowCheckoutGiftOptions(OrderMaxUtility.getValidString(showCheckoutGiftOptionsTextField.getText()));

             currentRecord.setreserveInventory(OrderMaxUtility.getValidString(reserveInventoryTextField.getText()));


             currentRecord.setautoApproveInvoice(OrderMaxUtility.getValidString(autoApproveInvoiceTextField.getText()));

             currentRecord.setprimaryStoreGroupId(OrderMaxUtility.getValidString(primaryStoreGroupIdTextField.getText()));

             currentRecord.setenableAutoSuggestionList(OrderMaxUtility.getValidString(enableAutoSuggestionListTextField.getText()));

             currentRecord.setretryFailedAuths(OrderMaxUtility.getValidString(retryFailedAuthsTextField.getText()));

             currentRecord.setusePrimaryEmailUsername(OrderMaxUtility.getValidString(usePrimaryEmailUsernameTextField.getText()));

             currentRecord.setautoSaveCart(OrderMaxUtility.getValidString(autoSaveCartTextField.getText()));

             currentRecord.setstoreCreditValidDays(OrderMaxUtility.getValidLong(storeCreditValidDaysTextField.getText()));

             currentRecord.setisImmediatelyFulfilled(OrderMaxUtility.getValidString(isImmediatelyFulfilledTextField.getText()));

             currentRecord.setautoInvoiceDigitalItems(OrderMaxUtility.getValidString(autoInvoiceDigitalItemsTextField.getText()));

             currentRecord.setoldHeaderLogo(OrderMaxUtility.getValidString(oldHeaderLogoTextField.getText()));

             currentRecord.setmanualAuthIsCapture(OrderMaxUtility.getValidString(manualAuthIsCaptureTextField.getText()));

             currentRecord.setsplitPayPrefPerShpGrp(OrderMaxUtility.getValidString(splitPayPrefPerShpGrpTextField.getText()));

             currentRecord.setdefaultSalesChannelEnumId(OrderMaxUtility.getValidString(defaultSalesChannelEnumIdTextField.getText()));

             currentRecord.setcheckInventory(OrderMaxUtility.getValidString(checkInventoryTextField.getText()));

             currentRecord.setauthFraudMessage(OrderMaxUtility.getValidString(authFraudMessageTextField.getText()));

             currentRecord.setmanagedByLot(OrderMaxUtility.getValidString(managedByLotTextField.getText()));

             currentRecord.setoldStyleSheet(OrderMaxUtility.getValidString(oldStyleSheetTextField.getText()));
             */
        }
    }
    /*
     public void setDialogField() {
     if (currentRecord != null) {

     terminalIdTextField.setText(currentRecord.getfacilityId());
     terminalNameTextField.setText(currentRecord.getfacilityName());
     txtEntitySync.setText(currentRecord.getdescription());
     try {
     if (UtilValidate.isNotEmpty(currentRecord.getfacilityTypeId())) {
     facilityModel.setSelectedItem(FacilityTypeSingleton.getFacilityType(currentRecord.getfacilityTypeId()));
     }
     } catch (Exception ex) {
     Logger.getLogger(SimpleProductStorePanel.class.getName()).log(Level.SEVERE, null, ex);
     }
     dirty.setDirty(false);
     }
     //        numEmployeesTextField.setText(OrderMaxUtility.getValidString(partygroup.getnumEmployees()));
     }*/

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public void clearDialogFields() {

        storeNameTextField.setText("");
//        terminalNameTextField.setText("");
    }
    /*
     public void getDialogFields() {

     if (currentRecord != null) {
     Debug.logInfo(" getDialogFields Print 1", SimpleProductStorePanel.class.getName());
     currentRecord.setfacilityId(terminalIdTextField.getText());
     currentRecord.setfacilityName(terminalNameTextField.getText());
     currentRecord.setdescription(txtEntitySync.getText());
     try {
     if (UtilValidate.isNotEmpty(facilityModel.getSelectedItem())) {
     currentRecord.setfacilityId(facilityModel.getSelectedItem().getfacilityTypeId());
     }
     } catch (Exception ex) {
     Logger.getLogger(SimpleProductStorePanel.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     //       PartyGroup partygroup = partyGroupAccount.getPartyGroup();
     //       partygroup.setpartyId(OrderMaxUtility.getValidString(terminalIdTextField.getText()));

     //      partygroup.setgroupNameLocal(OrderMaxUtility.getValidString(terminalNameTextField.getText()));
     //        partygroup.setnumEmployees(OrderMaxUtility.getValidString(numEmployeesTextField.getText()));
     }*/
    /*
     public static <T> T instanceOf(Class<T> clazz) throws Exception {
     return clazz.newInstance();
     }
    
     public static <T> Collection<T> getObjectList(List<org.ofbiz.entity.GenericValue> genList) {
     Collection<T> objectList = new ArrayList<T>();
     Class<T> clazz 
     for (GenericValue genVal : genList) {
     objectList.add(new T(genVal));
     }
     return objectList;
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        storeNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        panelDemo = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelOpenDate1 = new javax.swing.JPanel();
        visualThemeIdTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        panelProductStoreGroup = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        subtitleTextField = new javax.swing.JTextField();
        companyNameTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtProductStoreId = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelreserveInventory = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        panelbalanceResOnOrderCreation = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        panelFacilityId = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        paneloneInventoryFacility = new javax.swing.JPanel();
        panelisImmediatelyFulfilled = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelcheckInventory = new javax.swing.JPanel();
        panelrequireInventory = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelFacilityId8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        panelFacilityId9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panelOpenDate3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        panelFacilityId11 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        panelFacilityId12 = new javax.swing.JPanel();
        panelFacilityId13 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelFacilityId14 = new javax.swing.JPanel();
        panelFacilityId15 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        panelFacilityId17 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panelOpenDate4 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        panelFacilityId16 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        panelFacilityId18 = new javax.swing.JPanel();
        panelFacilityId19 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        panelFacilityId20 = new javax.swing.JPanel();
        panelFacilityId21 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        panelFacilityId10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        panelOpenDate5 = new javax.swing.JPanel();
        txtEntitySync2 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        panelFacilityId22 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        panelFacilityId23 = new javax.swing.JPanel();
        panelFacilityId24 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        panelFacilityId25 = new javax.swing.JPanel();
        panelFacilityId26 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        panelFacilityId27 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        panelFacilityId28 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        panelFacilityId29 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        panelFacilityId30 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        panelFacilityId31 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        panelFacilityId32 = new javax.swing.JPanel();
        panelFacilityId33 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        panelFacilityId34 = new javax.swing.JPanel();
        panelFacilityId35 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        panelFacilityId36 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        panelFacilityId37 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        panelFacilityId39 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        panelFacilityId40 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        panelFacilityId38 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        panelFacilityId41 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        panelFacilityId42 = new javax.swing.JPanel();
        panelFacilityId43 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        panelFacilityId44 = new javax.swing.JPanel();
        panelFacilityId45 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        panelFacilityId47 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        panelFacilityId46 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        panelFacilityId48 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        panelFacilityId50 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        panelFacilityId51 = new javax.swing.JPanel();
        panelFacilityId52 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        panelFacilityId53 = new javax.swing.JPanel();
        panelFacilityId54 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        panelFacilityId55 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        panelFacilityId56 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        panelFacilityId57 = new javax.swing.JPanel();
        panelFacilityId58 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        panelFacilityId59 = new javax.swing.JPanel();
        panelFacilityId60 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        panelFacilityId61 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        panelFacilityId49 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        panelFacilityId62 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        panelFacilityId63 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        panelFacilityId64 = new javax.swing.JPanel();
        recordList = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(400, 340));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Product Store Id:");

        jLabel2.setText("Is Demo Store:");

        javax.swing.GroupLayout panelDemoLayout = new javax.swing.GroupLayout(panelDemo);
        panelDemo.setLayout(panelDemoLayout);
        panelDemoLayout.setHorizontalGroup(
            panelDemoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelDemoLayout.setVerticalGroup(
            panelDemoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel10.setText("Visual Theme:");

        javax.swing.GroupLayout panelOpenDate1Layout = new javax.swing.GroupLayout(panelOpenDate1);
        panelOpenDate1.setLayout(panelOpenDate1Layout);
        panelOpenDate1Layout.setHorizontalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(visualThemeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelOpenDate1Layout.setVerticalGroup(
            panelOpenDate1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(visualThemeIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel4.setText("Primary Store Group Id:");

        javax.swing.GroupLayout panelProductStoreGroupLayout = new javax.swing.GroupLayout(panelProductStoreGroup);
        panelProductStoreGroup.setLayout(panelProductStoreGroupLayout);
        panelProductStoreGroupLayout.setHorizontalGroup(
            panelProductStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelProductStoreGroupLayout.setVerticalGroup(
            panelProductStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        Title.setText("Title:");

        jLabel6.setText("Sub-Title:");

        jLabel7.setText("Company Name :");

        jLabel3.setText("Store Name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Title, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(storeNameTextField)
                        .addComponent(panelDemo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelProductStoreGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(titleTextField)
                        .addComponent(subtitleTextField)
                        .addComponent(companyNameTextField))
                    .addComponent(txtProductStoreId, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(227, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelProductStoreGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtProductStoreId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(storeNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Title)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(subtitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(companyNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(panelDemo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelOpenDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Detail", jPanel1);

        jLabel5.setText("Reserve Inventory:");

        javax.swing.GroupLayout panelreserveInventoryLayout = new javax.swing.GroupLayout(panelreserveInventory);
        panelreserveInventory.setLayout(panelreserveInventoryLayout);
        panelreserveInventoryLayout.setHorizontalGroup(
            panelreserveInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelreserveInventoryLayout.setVerticalGroup(
            panelreserveInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel11.setText("Balance Res On Order Creation:");

        javax.swing.GroupLayout panelbalanceResOnOrderCreationLayout = new javax.swing.GroupLayout(panelbalanceResOnOrderCreation);
        panelbalanceResOnOrderCreation.setLayout(panelbalanceResOnOrderCreationLayout);
        panelbalanceResOnOrderCreationLayout.setHorizontalGroup(
            panelbalanceResOnOrderCreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        panelbalanceResOnOrderCreationLayout.setVerticalGroup(
            panelbalanceResOnOrderCreationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel8.setText("Inventory Facility Id:");

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel9.setText("One Inventory Facility:");

        javax.swing.GroupLayout paneloneInventoryFacilityLayout = new javax.swing.GroupLayout(paneloneInventoryFacility);
        paneloneInventoryFacility.setLayout(paneloneInventoryFacilityLayout);
        paneloneInventoryFacilityLayout.setHorizontalGroup(
            paneloneInventoryFacilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        paneloneInventoryFacilityLayout.setVerticalGroup(
            paneloneInventoryFacilityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelisImmediatelyFulfilledLayout = new javax.swing.GroupLayout(panelisImmediatelyFulfilled);
        panelisImmediatelyFulfilled.setLayout(panelisImmediatelyFulfilledLayout);
        panelisImmediatelyFulfilledLayout.setHorizontalGroup(
            panelisImmediatelyFulfilledLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelisImmediatelyFulfilledLayout.setVerticalGroup(
            panelisImmediatelyFulfilledLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel13.setText("Is Immediately Fulfilled:");

        jLabel14.setText("Check Inventory:");

        javax.swing.GroupLayout panelcheckInventoryLayout = new javax.swing.GroupLayout(panelcheckInventory);
        panelcheckInventory.setLayout(panelcheckInventoryLayout);
        panelcheckInventoryLayout.setHorizontalGroup(
            panelcheckInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelcheckInventoryLayout.setVerticalGroup(
            panelcheckInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelrequireInventoryLayout = new javax.swing.GroupLayout(panelrequireInventory);
        panelrequireInventory.setLayout(panelrequireInventoryLayout);
        panelrequireInventoryLayout.setHorizontalGroup(
            panelrequireInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelrequireInventoryLayout.setVerticalGroup(
            panelrequireInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel15.setText("Require Inventory:");

        jLabel16.setText("Reserve Order Enum Id:");

        javax.swing.GroupLayout panelFacilityId8Layout = new javax.swing.GroupLayout(panelFacilityId8);
        panelFacilityId8.setLayout(panelFacilityId8Layout);
        panelFacilityId8Layout.setHorizontalGroup(
            panelFacilityId8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId8Layout.setVerticalGroup(
            panelFacilityId8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel17.setText("Requirement Method Enum Id:");

        javax.swing.GroupLayout panelFacilityId9Layout = new javax.swing.GroupLayout(panelFacilityId9);
        panelFacilityId9.setLayout(panelFacilityId9Layout);
        panelFacilityId9Layout.setHorizontalGroup(
            panelFacilityId9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId9Layout.setVerticalGroup(
            panelFacilityId9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelreserveInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelbalanceResOnOrderCreation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(paneloneInventoryFacility, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelisImmediatelyFulfilled, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelcheckInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelrequireInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(paneloneInventoryFacility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelisImmediatelyFulfilled, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelcheckInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelrequireInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(panelreserveInventory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(panelFacilityId8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(panelbalanceResOnOrderCreation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Inventory", jPanel3);

        javax.swing.GroupLayout panelOpenDate3Layout = new javax.swing.GroupLayout(panelOpenDate3);
        panelOpenDate3.setLayout(panelOpenDate3Layout);
        panelOpenDate3Layout.setHorizontalGroup(
            panelOpenDate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        panelOpenDate3Layout.setVerticalGroup(
            panelOpenDate3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel19.setText("View Cart On Add:");

        javax.swing.GroupLayout panelFacilityId11Layout = new javax.swing.GroupLayout(panelFacilityId11);
        panelFacilityId11.setLayout(panelFacilityId11Layout);
        panelFacilityId11Layout.setHorizontalGroup(
            panelFacilityId11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId11Layout.setVerticalGroup(
            panelFacilityId11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel20.setText("Auto Save Cart:");

        javax.swing.GroupLayout panelFacilityId12Layout = new javax.swing.GroupLayout(panelFacilityId12);
        panelFacilityId12.setLayout(panelFacilityId12Layout);
        panelFacilityId12Layout.setHorizontalGroup(
            panelFacilityId12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId12Layout.setVerticalGroup(
            panelFacilityId12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId13Layout = new javax.swing.GroupLayout(panelFacilityId13);
        panelFacilityId13.setLayout(panelFacilityId13Layout);
        panelFacilityId13Layout.setHorizontalGroup(
            panelFacilityId13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId13Layout.setVerticalGroup(
            panelFacilityId13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel21.setText("Add To Cart Replace Upsell:");

        jLabel22.setText("Add To Cart Remove Incompat:");

        javax.swing.GroupLayout panelFacilityId14Layout = new javax.swing.GroupLayout(panelFacilityId14);
        panelFacilityId14.setLayout(panelFacilityId14Layout);
        panelFacilityId14Layout.setHorizontalGroup(
            panelFacilityId14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId14Layout.setVerticalGroup(
            panelFacilityId14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId15Layout = new javax.swing.GroupLayout(panelFacilityId15);
        panelFacilityId15.setLayout(panelFacilityId15Layout);
        panelFacilityId15Layout.setHorizontalGroup(
            panelFacilityId15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId15Layout.setVerticalGroup(
            panelFacilityId15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel23.setText("Show Checkout Gift Options:");

        jLabel25.setText("Prod Search Exclude Variants:");

        javax.swing.GroupLayout panelFacilityId17Layout = new javax.swing.GroupLayout(panelFacilityId17);
        panelFacilityId17.setLayout(panelFacilityId17Layout);
        panelFacilityId17Layout.setHorizontalGroup(
            panelFacilityId17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId17Layout.setVerticalGroup(
            panelFacilityId17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelOpenDate3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFacilityId11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(65, 65, 65)
                .addComponent(panelOpenDate3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Shopping Cart", jPanel4);

        javax.swing.GroupLayout panelOpenDate4Layout = new javax.swing.GroupLayout(panelOpenDate4);
        panelOpenDate4.setLayout(panelOpenDate4Layout);
        panelOpenDate4Layout.setHorizontalGroup(
            panelOpenDate4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        panelOpenDate4Layout.setVerticalGroup(
            panelOpenDate4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel24.setText("Prorate Shipping:");

        javax.swing.GroupLayout panelFacilityId16Layout = new javax.swing.GroupLayout(panelFacilityId16);
        panelFacilityId16.setLayout(panelFacilityId16Layout);
        panelFacilityId16Layout.setHorizontalGroup(
            panelFacilityId16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId16Layout.setVerticalGroup(
            panelFacilityId16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel26.setText("Req Ship Addr For Dig Items:");

        javax.swing.GroupLayout panelFacilityId18Layout = new javax.swing.GroupLayout(panelFacilityId18);
        panelFacilityId18.setLayout(panelFacilityId18Layout);
        panelFacilityId18Layout.setHorizontalGroup(
            panelFacilityId18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId18Layout.setVerticalGroup(
            panelFacilityId18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId19Layout = new javax.swing.GroupLayout(panelFacilityId19);
        panelFacilityId19.setLayout(panelFacilityId19Layout);
        panelFacilityId19Layout.setHorizontalGroup(
            panelFacilityId19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId19Layout.setVerticalGroup(
            panelFacilityId19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel27.setText("Select Payment Type Per Item:");

        jLabel28.setText("Ship If Capture Fails:");

        javax.swing.GroupLayout panelFacilityId20Layout = new javax.swing.GroupLayout(panelFacilityId20);
        panelFacilityId20.setLayout(panelFacilityId20Layout);
        panelFacilityId20Layout.setHorizontalGroup(
            panelFacilityId20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId20Layout.setVerticalGroup(
            panelFacilityId20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId21Layout = new javax.swing.GroupLayout(panelFacilityId21);
        panelFacilityId21.setLayout(panelFacilityId21Layout);
        panelFacilityId21Layout.setHorizontalGroup(
            panelFacilityId21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId21Layout.setVerticalGroup(
            panelFacilityId21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel29.setText("Split Pay Pref Per Shp Grp:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelOpenDate4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFacilityId16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(94, 94, 94)
                .addComponent(panelOpenDate4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Shipping", jPanel5);

        jLabel12.setText("Auto Order Cc Try Later Nsf");

        javax.swing.GroupLayout panelFacilityId10Layout = new javax.swing.GroupLayout(panelFacilityId10);
        panelFacilityId10.setLayout(panelFacilityId10Layout);
        panelFacilityId10Layout.setHorizontalGroup(
            panelFacilityId10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId10Layout.setVerticalGroup(
            panelFacilityId10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel18.setText("Store Credit Valid Days");

        javax.swing.GroupLayout panelOpenDate5Layout = new javax.swing.GroupLayout(panelOpenDate5);
        panelOpenDate5.setLayout(panelOpenDate5Layout);
        panelOpenDate5Layout.setHorizontalGroup(
            panelOpenDate5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEntitySync2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelOpenDate5Layout.setVerticalGroup(
            panelOpenDate5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtEntitySync2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel30.setText("Pay To Party Id:");

        javax.swing.GroupLayout panelFacilityId22Layout = new javax.swing.GroupLayout(panelFacilityId22);
        panelFacilityId22.setLayout(panelFacilityId22Layout);
        panelFacilityId22Layout.setHorizontalGroup(
            panelFacilityId22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId22Layout.setVerticalGroup(
            panelFacilityId22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel31.setText("Manual Auth Is Capture");

        javax.swing.GroupLayout panelFacilityId23Layout = new javax.swing.GroupLayout(panelFacilityId23);
        panelFacilityId23.setLayout(panelFacilityId23Layout);
        panelFacilityId23Layout.setHorizontalGroup(
            panelFacilityId23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId23Layout.setVerticalGroup(
            panelFacilityId23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId24Layout = new javax.swing.GroupLayout(panelFacilityId24);
        panelFacilityId24.setLayout(panelFacilityId24Layout);
        panelFacilityId24Layout.setHorizontalGroup(
            panelFacilityId24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId24Layout.setVerticalGroup(
            panelFacilityId24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel32.setText("Retry Failed Auths");

        jLabel33.setText("Days To Cancel Non Pay");

        javax.swing.GroupLayout panelFacilityId25Layout = new javax.swing.GroupLayout(panelFacilityId25);
        panelFacilityId25.setLayout(panelFacilityId25Layout);
        panelFacilityId25Layout.setHorizontalGroup(
            panelFacilityId25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId25Layout.setVerticalGroup(
            panelFacilityId25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId26Layout = new javax.swing.GroupLayout(panelFacilityId26);
        panelFacilityId26.setLayout(panelFacilityId26Layout);
        panelFacilityId26Layout.setHorizontalGroup(
            panelFacilityId26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId26Layout.setVerticalGroup(
            panelFacilityId26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel34.setText("Auto Order Cc Try Exp");

        jLabel35.setText("Auto Order Cc Try Later Max ");

        javax.swing.GroupLayout panelFacilityId27Layout = new javax.swing.GroupLayout(panelFacilityId27);
        panelFacilityId27.setLayout(panelFacilityId27Layout);
        panelFacilityId27Layout.setHorizontalGroup(
            panelFacilityId27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId27Layout.setVerticalGroup(
            panelFacilityId27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel36.setText("Auto Order Cc Try Other Cards");

        javax.swing.GroupLayout panelFacilityId28Layout = new javax.swing.GroupLayout(panelFacilityId28);
        panelFacilityId28.setLayout(panelFacilityId28Layout);
        panelFacilityId28Layout.setHorizontalGroup(
            panelFacilityId28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId28Layout.setVerticalGroup(
            panelFacilityId28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel37.setText("Set Owner Upon Issuance ");

        javax.swing.GroupLayout panelFacilityId29Layout = new javax.swing.GroupLayout(panelFacilityId29);
        panelFacilityId29.setLayout(panelFacilityId29Layout);
        panelFacilityId29Layout.setHorizontalGroup(
            panelFacilityId29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId29Layout.setVerticalGroup(
            panelFacilityId29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpenDate5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFacilityId22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(panelFacilityId10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(panelFacilityId27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(panelFacilityId29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(panelOpenDate5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Payments", jPanel6);

        jLabel38.setText("Auto Approve Order");

        javax.swing.GroupLayout panelFacilityId30Layout = new javax.swing.GroupLayout(panelFacilityId30);
        panelFacilityId30.setLayout(panelFacilityId30Layout);
        panelFacilityId30Layout.setHorizontalGroup(
            panelFacilityId30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId30Layout.setVerticalGroup(
            panelFacilityId30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel40.setText("Order Number Prefix ");

        javax.swing.GroupLayout panelFacilityId31Layout = new javax.swing.GroupLayout(panelFacilityId31);
        panelFacilityId31.setLayout(panelFacilityId31Layout);
        panelFacilityId31Layout.setHorizontalGroup(
            panelFacilityId31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId31Layout.setVerticalGroup(
            panelFacilityId31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel41.setText("Default Sales Channel Enum Id ");

        javax.swing.GroupLayout panelFacilityId32Layout = new javax.swing.GroupLayout(panelFacilityId32);
        panelFacilityId32.setLayout(panelFacilityId32Layout);
        panelFacilityId32Layout.setHorizontalGroup(
            panelFacilityId32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId32Layout.setVerticalGroup(
            panelFacilityId32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId33Layout = new javax.swing.GroupLayout(panelFacilityId33);
        panelFacilityId33.setLayout(panelFacilityId33Layout);
        panelFacilityId33Layout.setHorizontalGroup(
            panelFacilityId33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId33Layout.setVerticalGroup(
            panelFacilityId33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel42.setText("Explode Order Items ");

        jLabel43.setText("Check Gc Balance");

        javax.swing.GroupLayout panelFacilityId34Layout = new javax.swing.GroupLayout(panelFacilityId34);
        panelFacilityId34.setLayout(panelFacilityId34Layout);
        panelFacilityId34Layout.setHorizontalGroup(
            panelFacilityId34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId34Layout.setVerticalGroup(
            panelFacilityId34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId35Layout = new javax.swing.GroupLayout(panelFacilityId35);
        panelFacilityId35.setLayout(panelFacilityId35Layout);
        panelFacilityId35Layout.setHorizontalGroup(
            panelFacilityId35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId35Layout.setVerticalGroup(
            panelFacilityId35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel44.setText("Auto Invoice Digital Items");

        jLabel45.setText("Req Return Inventory Receive ");

        javax.swing.GroupLayout panelFacilityId36Layout = new javax.swing.GroupLayout(panelFacilityId36);
        panelFacilityId36.setLayout(panelFacilityId36Layout);
        panelFacilityId36Layout.setHorizontalGroup(
            panelFacilityId36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId36Layout.setVerticalGroup(
            panelFacilityId36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel46.setText("Auto Approve Invoice ");

        javax.swing.GroupLayout panelFacilityId37Layout = new javax.swing.GroupLayout(panelFacilityId37);
        panelFacilityId37.setLayout(panelFacilityId37Layout);
        panelFacilityId37Layout.setHorizontalGroup(
            panelFacilityId37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId37Layout.setVerticalGroup(
            panelFacilityId37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(panelFacilityId30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45)
                    .addComponent(panelFacilityId36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120, 120, 120))
        );

        jTabbedPane1.addTab("Orders", jPanel7);

        jLabel47.setText("Default Locale String ");

        javax.swing.GroupLayout panelFacilityId39Layout = new javax.swing.GroupLayout(panelFacilityId39);
        panelFacilityId39.setLayout(panelFacilityId39Layout);
        panelFacilityId39Layout.setHorizontalGroup(
            panelFacilityId39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId39Layout.setVerticalGroup(
            panelFacilityId39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel48.setText("Default Currency Uom Id");

        javax.swing.GroupLayout panelFacilityId40Layout = new javax.swing.GroupLayout(panelFacilityId40);
        panelFacilityId40.setLayout(panelFacilityId40Layout);
        panelFacilityId40Layout.setHorizontalGroup(
            panelFacilityId40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId40Layout.setVerticalGroup(
            panelFacilityId40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addGap(294, 294, 294))
        );

        jTabbedPane1.addTab("Localisation", jPanel8);

        jLabel39.setText("Item Cancel Status");

        javax.swing.GroupLayout panelFacilityId38Layout = new javax.swing.GroupLayout(panelFacilityId38);
        panelFacilityId38.setLayout(panelFacilityId38Layout);
        panelFacilityId38Layout.setHorizontalGroup(
            panelFacilityId38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId38Layout.setVerticalGroup(
            panelFacilityId38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel49.setText("Header Approved Status");

        javax.swing.GroupLayout panelFacilityId41Layout = new javax.swing.GroupLayout(panelFacilityId41);
        panelFacilityId41.setLayout(panelFacilityId41Layout);
        panelFacilityId41Layout.setHorizontalGroup(
            panelFacilityId41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId41Layout.setVerticalGroup(
            panelFacilityId41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel50.setText("Item Approved Status");

        javax.swing.GroupLayout panelFacilityId42Layout = new javax.swing.GroupLayout(panelFacilityId42);
        panelFacilityId42.setLayout(panelFacilityId42Layout);
        panelFacilityId42Layout.setHorizontalGroup(
            panelFacilityId42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId42Layout.setVerticalGroup(
            panelFacilityId42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId43Layout = new javax.swing.GroupLayout(panelFacilityId43);
        panelFacilityId43.setLayout(panelFacilityId43Layout);
        panelFacilityId43Layout.setHorizontalGroup(
            panelFacilityId43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId43Layout.setVerticalGroup(
            panelFacilityId43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel51.setText("Digital Item Approved Status ");

        jLabel52.setText("Header Declined Status ");

        javax.swing.GroupLayout panelFacilityId44Layout = new javax.swing.GroupLayout(panelFacilityId44);
        panelFacilityId44.setLayout(panelFacilityId44Layout);
        panelFacilityId44Layout.setHorizontalGroup(
            panelFacilityId44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId44Layout.setVerticalGroup(
            panelFacilityId44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId45Layout = new javax.swing.GroupLayout(panelFacilityId45);
        panelFacilityId45.setLayout(panelFacilityId45Layout);
        panelFacilityId45Layout.setHorizontalGroup(
            panelFacilityId45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId45Layout.setVerticalGroup(
            panelFacilityId45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel53.setText("Item Declined Status ");

        jLabel55.setText("Header Cancel Status ");

        javax.swing.GroupLayout panelFacilityId47Layout = new javax.swing.GroupLayout(panelFacilityId47);
        panelFacilityId47.setLayout(panelFacilityId47Layout);
        panelFacilityId47Layout.setHorizontalGroup(
            panelFacilityId47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId47Layout.setVerticalGroup(
            panelFacilityId47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel53, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(panelFacilityId38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(149, 149, 149))
        );

        jTabbedPane1.addTab("Orders Status", jPanel9);

        jLabel54.setText("Auth Declined Message ");

        javax.swing.GroupLayout panelFacilityId46Layout = new javax.swing.GroupLayout(panelFacilityId46);
        panelFacilityId46.setLayout(panelFacilityId46Layout);
        panelFacilityId46Layout.setHorizontalGroup(
            panelFacilityId46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId46Layout.setVerticalGroup(
            panelFacilityId46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel56.setText("Auth Fraud Message");

        javax.swing.GroupLayout panelFacilityId48Layout = new javax.swing.GroupLayout(panelFacilityId48);
        panelFacilityId48.setLayout(panelFacilityId48Layout);
        panelFacilityId48Layout.setHorizontalGroup(
            panelFacilityId48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId48Layout.setVerticalGroup(
            panelFacilityId48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel57.setText("Auth Error Message ");

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addComponent(jTextField2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel54)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(291, 291, 291))
        );

        jTabbedPane1.addTab("Messages", jPanel10);

        jLabel59.setText("Prorate Taxes ");

        javax.swing.GroupLayout panelFacilityId50Layout = new javax.swing.GroupLayout(panelFacilityId50);
        panelFacilityId50.setLayout(panelFacilityId50Layout);
        panelFacilityId50Layout.setHorizontalGroup(
            panelFacilityId50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId50Layout.setVerticalGroup(
            panelFacilityId50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel60.setText("Show prices with VAT tax included");

        javax.swing.GroupLayout panelFacilityId51Layout = new javax.swing.GroupLayout(panelFacilityId51);
        panelFacilityId51.setLayout(panelFacilityId51Layout);
        panelFacilityId51Layout.setHorizontalGroup(
            panelFacilityId51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId51Layout.setVerticalGroup(
            panelFacilityId51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId52Layout = new javax.swing.GroupLayout(panelFacilityId52);
        panelFacilityId52.setLayout(panelFacilityId52Layout);
        panelFacilityId52Layout.setHorizontalGroup(
            panelFacilityId52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId52Layout.setVerticalGroup(
            panelFacilityId52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel61.setText("Show Tax Is Exempt");

        jLabel62.setText("Header Declined Status ");

        javax.swing.GroupLayout panelFacilityId53Layout = new javax.swing.GroupLayout(panelFacilityId53);
        panelFacilityId53.setLayout(panelFacilityId53Layout);
        panelFacilityId53Layout.setHorizontalGroup(
            panelFacilityId53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId53Layout.setVerticalGroup(
            panelFacilityId53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId54Layout = new javax.swing.GroupLayout(panelFacilityId54);
        panelFacilityId54.setLayout(panelFacilityId54Layout);
        panelFacilityId54Layout.setHorizontalGroup(
            panelFacilityId54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId54Layout.setVerticalGroup(
            panelFacilityId54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel63.setText("Vat Tax Auth Geo Id ");

        jLabel64.setText("Vat Tax Auth Party Id ");

        javax.swing.GroupLayout panelFacilityId55Layout = new javax.swing.GroupLayout(panelFacilityId55);
        panelFacilityId55.setLayout(panelFacilityId55Layout);
        panelFacilityId55Layout.setHorizontalGroup(
            panelFacilityId55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId55Layout.setVerticalGroup(
            panelFacilityId55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addGap(178, 178, 178))
        );

        jTabbedPane1.addTab("Tax", jPanel11);

        jLabel65.setText("Auto Approve Reviews");

        javax.swing.GroupLayout panelFacilityId56Layout = new javax.swing.GroupLayout(panelFacilityId56);
        panelFacilityId56.setLayout(panelFacilityId56Layout);
        panelFacilityId56Layout.setHorizontalGroup(
            panelFacilityId56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId56Layout.setVerticalGroup(
            panelFacilityId56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel66.setText("Allow Password ");

        javax.swing.GroupLayout panelFacilityId57Layout = new javax.swing.GroupLayout(panelFacilityId57);
        panelFacilityId57.setLayout(panelFacilityId57Layout);
        panelFacilityId57Layout.setHorizontalGroup(
            panelFacilityId57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId57Layout.setVerticalGroup(
            panelFacilityId57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId58Layout = new javax.swing.GroupLayout(panelFacilityId58);
        panelFacilityId58.setLayout(panelFacilityId58Layout);
        panelFacilityId58Layout.setHorizontalGroup(
            panelFacilityId58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId58Layout.setVerticalGroup(
            panelFacilityId58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel67.setText("Default Password");

        jLabel68.setText("Use Primary Email Username");

        javax.swing.GroupLayout panelFacilityId59Layout = new javax.swing.GroupLayout(panelFacilityId59);
        panelFacilityId59.setLayout(panelFacilityId59Layout);
        panelFacilityId59Layout.setHorizontalGroup(
            panelFacilityId59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId59Layout.setVerticalGroup(
            panelFacilityId59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFacilityId60Layout = new javax.swing.GroupLayout(panelFacilityId60);
        panelFacilityId60.setLayout(panelFacilityId60Layout);
        panelFacilityId60Layout.setHorizontalGroup(
            panelFacilityId60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId60Layout.setVerticalGroup(
            panelFacilityId60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel69.setText("Require Customer Role");

        jLabel70.setText("Enable Auto Suggestion List ");

        javax.swing.GroupLayout panelFacilityId61Layout = new javax.swing.GroupLayout(panelFacilityId61);
        panelFacilityId61.setLayout(panelFacilityId61Layout);
        panelFacilityId61Layout.setHorizontalGroup(
            panelFacilityId61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId61Layout.setVerticalGroup(
            panelFacilityId61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel69))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70))
                .addGap(178, 178, 178))
        );

        jTabbedPane1.addTab("Visitors", jPanel12);

        jLabel58.setText("Enable Digital Product Upload");

        javax.swing.GroupLayout panelFacilityId49Layout = new javax.swing.GroupLayout(panelFacilityId49);
        panelFacilityId49.setLayout(panelFacilityId49Layout);
        panelFacilityId49Layout.setHorizontalGroup(
            panelFacilityId49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId49Layout.setVerticalGroup(
            panelFacilityId49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel71.setText("Dig Prod Upload Category Id ");

        javax.swing.GroupLayout panelFacilityId62Layout = new javax.swing.GroupLayout(panelFacilityId62);
        panelFacilityId62.setLayout(panelFacilityId62Layout);
        panelFacilityId62Layout.setHorizontalGroup(
            panelFacilityId62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId62Layout.setVerticalGroup(
            panelFacilityId62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel71))
                .addGap(294, 294, 294))
        );

        jTabbedPane1.addTab("Upload", jPanel13);

        jLabel72.setText("Store Credit Account Enum Id ");

        javax.swing.GroupLayout panelFacilityId63Layout = new javax.swing.GroupLayout(panelFacilityId63);
        panelFacilityId63.setLayout(panelFacilityId63Layout);
        panelFacilityId63Layout.setHorizontalGroup(
            panelFacilityId63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId63Layout.setVerticalGroup(
            panelFacilityId63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel73.setText("Show Out Of Stock Products");

        javax.swing.GroupLayout panelFacilityId64Layout = new javax.swing.GroupLayout(panelFacilityId64);
        panelFacilityId64.setLayout(panelFacilityId64Layout);
        panelFacilityId64Layout.setHorizontalGroup(
            panelFacilityId64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFacilityId64Layout.setVerticalGroup(
            panelFacilityId64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel73, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFacilityId63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFacilityId64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel72))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFacilityId64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel73))
                .addGap(294, 294, 294))
        );

        jTabbedPane1.addTab("Other", jPanel14);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.PAGE_END);

        recordList.setPreferredSize(new java.awt.Dimension(0, 100));

        javax.swing.GroupLayout recordListLayout = new javax.swing.GroupLayout(recordList);
        recordList.setLayout(recordListLayout);
        recordListLayout.setHorizontalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        recordListLayout.setVerticalGroup(
            recordListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );

        add(recordList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title;
    public javax.swing.JTextField companyNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JPanel panelDemo;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelFacilityId10;
    private javax.swing.JPanel panelFacilityId11;
    private javax.swing.JPanel panelFacilityId12;
    private javax.swing.JPanel panelFacilityId13;
    private javax.swing.JPanel panelFacilityId14;
    private javax.swing.JPanel panelFacilityId15;
    private javax.swing.JPanel panelFacilityId16;
    private javax.swing.JPanel panelFacilityId17;
    private javax.swing.JPanel panelFacilityId18;
    private javax.swing.JPanel panelFacilityId19;
    private javax.swing.JPanel panelFacilityId20;
    private javax.swing.JPanel panelFacilityId21;
    private javax.swing.JPanel panelFacilityId22;
    private javax.swing.JPanel panelFacilityId23;
    private javax.swing.JPanel panelFacilityId24;
    private javax.swing.JPanel panelFacilityId25;
    private javax.swing.JPanel panelFacilityId26;
    private javax.swing.JPanel panelFacilityId27;
    private javax.swing.JPanel panelFacilityId28;
    private javax.swing.JPanel panelFacilityId29;
    private javax.swing.JPanel panelFacilityId30;
    private javax.swing.JPanel panelFacilityId31;
    private javax.swing.JPanel panelFacilityId32;
    private javax.swing.JPanel panelFacilityId33;
    private javax.swing.JPanel panelFacilityId34;
    private javax.swing.JPanel panelFacilityId35;
    private javax.swing.JPanel panelFacilityId36;
    private javax.swing.JPanel panelFacilityId37;
    private javax.swing.JPanel panelFacilityId38;
    private javax.swing.JPanel panelFacilityId39;
    private javax.swing.JPanel panelFacilityId40;
    private javax.swing.JPanel panelFacilityId41;
    private javax.swing.JPanel panelFacilityId42;
    private javax.swing.JPanel panelFacilityId43;
    private javax.swing.JPanel panelFacilityId44;
    private javax.swing.JPanel panelFacilityId45;
    private javax.swing.JPanel panelFacilityId46;
    private javax.swing.JPanel panelFacilityId47;
    private javax.swing.JPanel panelFacilityId48;
    private javax.swing.JPanel panelFacilityId49;
    private javax.swing.JPanel panelFacilityId50;
    private javax.swing.JPanel panelFacilityId51;
    private javax.swing.JPanel panelFacilityId52;
    private javax.swing.JPanel panelFacilityId53;
    private javax.swing.JPanel panelFacilityId54;
    private javax.swing.JPanel panelFacilityId55;
    private javax.swing.JPanel panelFacilityId56;
    private javax.swing.JPanel panelFacilityId57;
    private javax.swing.JPanel panelFacilityId58;
    private javax.swing.JPanel panelFacilityId59;
    private javax.swing.JPanel panelFacilityId60;
    private javax.swing.JPanel panelFacilityId61;
    private javax.swing.JPanel panelFacilityId62;
    private javax.swing.JPanel panelFacilityId63;
    private javax.swing.JPanel panelFacilityId64;
    private javax.swing.JPanel panelFacilityId8;
    private javax.swing.JPanel panelFacilityId9;
    private javax.swing.JPanel panelOpenDate1;
    private javax.swing.JPanel panelOpenDate3;
    private javax.swing.JPanel panelOpenDate4;
    private javax.swing.JPanel panelOpenDate5;
    private javax.swing.JPanel panelProductStoreGroup;
    private javax.swing.JPanel panelbalanceResOnOrderCreation;
    private javax.swing.JPanel panelcheckInventory;
    private javax.swing.JPanel panelisImmediatelyFulfilled;
    private javax.swing.JPanel paneloneInventoryFacility;
    private javax.swing.JPanel panelrequireInventory;
    private javax.swing.JPanel panelreserveInventory;
    private javax.swing.JPanel recordList;
    public javax.swing.JTextField storeNameTextField;
    public javax.swing.JTextField subtitleTextField;
    public javax.swing.JTextField titleTextField;
    private javax.swing.JTextField txtEntitySync2;
    public javax.swing.JTextField txtProductStoreId;
    private javax.swing.JTextField visualThemeIdTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void newRecord() {
        currentRecord = new ProductStore();
        setDialogField();
    }

    public void copyRecord() {
        if (currentRecord != null) {
            currentRecord.setproductStoreId("");
            currentRecord.setstoreName(OrderMaxUtility.getValidString(storeNameTextField.getText()) + "(Copy)");
            setDialogField();
            dirty.setDirty(true);
        }
    }

    @Override
    public Map<String, Object> getValuesMap() {
        if (currentRecord != null) {
            return currentRecord.getValuesMap();
        }
        return null;
    }

    @Override
    public GenericValue getGenericValueObj() {
        if (currentRecord != null) {
            currentRecord.getGenericValue();            
            return currentRecord.getGenericValueObj();
        }
        return null;
    }

    @Override
    public List<String> getKey() {
        return UtilMisc.toList("productStoreId");
    }

    @Override
    public boolean isValidValues() {
        boolean result = true;
        getDialogFields();
        if (UtilValidate.isEmpty(storeNameTextField.getText())) {
            storeNameTextField.requestFocus();
            result = false;
//        } else if (UtilValidate.isEmpty(terminalNameTextField.getText())) {
            //           terminalNameTextField.requestFocus();
            //           result = false;
        } else if (UtilValidate.isEmpty(facilityModel.getSelectedItem())) {
            facilityModel.jComboBox.requestFocus();
            result = false;
        }
        Debug.logInfo(" isValidValues: " + result, SimpleProductStorePanel.class.getName());
        if (!result) {
            OrderMaxOptionPane.showConfirmDialog(null, "Field is empty", "Pos Terminal",
                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
        }

        return result;
    }

    void loadList() {
        loadList(null);
    }

    @Override
    public void loadList(org.ofbiz.ordermax.base.ControllerOptions options) {
        List<ProductStore> list = new ArrayList<ProductStore>();//PosTerminalSingleton.getValueList();
        if (UtilValidate.isNotEmpty(entityName)) {
            Delegator delegator = ControllerOptions.getSession().getDelegator();
            // this.setupTable();
            list.clear();
            List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);
            for (GenericValue genVal : genList) {
                list.add(new ProductStore(genVal));
            }
        }
        listSelectionModel.setDataList(list);
    }

    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setGenericValue(GenericValue val) {
        currentRecord = new ProductStore(val);
        setDialogField();
    }
}
