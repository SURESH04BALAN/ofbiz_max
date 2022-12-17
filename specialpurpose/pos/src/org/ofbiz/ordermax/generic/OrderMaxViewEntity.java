/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntity;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;

/**
 *
 * @author siranjeev
 */
public class OrderMaxViewEntity {
    /*    SELECT O_R.ORDER_ID, O_R.PARTY_ID, O_H.ORDER_NAME, O_H.ORDER_DATE, O_H.STATUS_ID, O_I.PRODUCT_ID, O_I.QUANTITY, O_I.UNIT_PRICE, O_I.ITEM_DESCRIPTION, O_I.STATUS_ID 
     FROM ORDER_ROLE AS O_R 
     INNER JOIN ORDER_HEADER AS O_H ON(O_R.ORDER_ID = O_H.ORDER_ID)
     INNER JOIN ORDER_ITEM AS O_I ON(O_R.ORDER_ID = O_I.ORDER_ID)
     WHERE PARTY_ID = 'ALPHA' 
     AND ROLE_TYPE_ID = 'SUPPLIER_AGENT'
     AND ORDER_TYPE_ID = 'PURCHASE_ORDER'
     AND O_I.STATUS_ID = 'ITEM_COMPLETED'
     AND PRODUCT_ID = 'C71200'
     */

    final static int ID_COL_SIZE = 70;
    final static int DESC_COL_SIZE = 150;
    final static int DATE_COL_SIZE = 80;
    final static int AMOUNT_COL_SIZE = 70;
    final static int CURRENCY_COL_SIZE = 50;
    final static int INTEGER_COL_SIZE = 70;
    final static int TYPEID_COL_SIZE = 80;
    final static int ORDERTYPEID_COL_SIZE = 120;
    final static int STATUSID_COL_SIZE = 120;
    final static int invoiceId = ID_COL_SIZE;
    final static int invoiceTypeId = TYPEID_COL_SIZE;
    final static int partyIdFrom = ID_COL_SIZE;
    final static int partyId = ID_COL_SIZE;
    final static int roleTypeId = TYPEID_COL_SIZE;
    final static int statusId = STATUSID_COL_SIZE;
    final static int billingAccountId = ID_COL_SIZE;
    final static int contactMechId = ID_COL_SIZE;
    final static int invoiceDate = DATE_COL_SIZE;
    final static int dueDate = DATE_COL_SIZE;
    final static int paidDate = DATE_COL_SIZE;
    final static int invoiceMessage = DESC_COL_SIZE;
    final static int referenceNumber = ID_COL_SIZE;
    final static int description = DESC_COL_SIZE;
    final static int currencyUomId = CURRENCY_COL_SIZE;
    final static int recurrenceInfoId = ID_COL_SIZE;
    final static int lastUpdatedStamp = DATE_COL_SIZE;
    final static int lastUpdatedTxStamp = DATE_COL_SIZE;
    final static int createdStamp = DATE_COL_SIZE;
    final static int createdTxStamp = DATE_COL_SIZE;
    final static int orderId = ID_COL_SIZE;
    final static int orderTypeId = ORDERTYPEID_COL_SIZE;
    final static int productId = ID_COL_SIZE;
    final static int itemDescription = DESC_COL_SIZE;
    final static int quantity = INTEGER_COL_SIZE;
    final static int unitPrice = AMOUNT_COL_SIZE;
    final static int orderName = DESC_COL_SIZE;
    final static int grandTotal = AMOUNT_COL_SIZE;
    final static int remainingSubTotal = AMOUNT_COL_SIZE;
    final static int COL_SIZE = 80;
    //cell renders
    final static String Default_Cell_Rendere = "DEFAULT";
    final static String ProductTreeNode_Cell_Rendere = "PRODUCT_TREENODE";
    final static String StringDescription_Cell_Renderer = "STRING_DESCRIPTION";
    final static String InvoiceId_Cell_Renderer = "INVOICE_ID_RENDERER";
    final static String OrderId_Cell_Renderer = "ORDER_ID_RENDERER";
    final static String PartyId_Cell_Renderer = "PARTY_ID_RENDERER";
    final static String Address_Cell_Renderer = "ADDRESS_ID_RENDERER";
    final static String GenericValue_Cell_Renderer = "GENERIC_VALUE";
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
        new ColumnDetails("invoiceId", "Invoice Id", "java.lang.String", new Integer(invoiceId), InvoiceId_Cell_Renderer),
        new ColumnDetails("invoiceTypeId", "Invoice TypeId", "java.lang.String", new Integer(invoiceTypeId), Default_Cell_Rendere),
        new ColumnDetails("partyIdFrom", "Party Id From", "java.lang.String", new Integer(partyIdFrom), PartyId_Cell_Renderer),
        new ColumnDetails("partyId", "Party Id", "java.lang.String", new Integer(partyId), PartyId_Cell_Renderer),
        new ColumnDetails("roleTypeId", "Role Type Id", "java.lang.String", new Integer(roleTypeId), Default_Cell_Rendere),
        new ColumnDetails("statusId", "Status Id", "java.lang.String", new Integer(statusId), Default_Cell_Rendere),
        new ColumnDetails("billingAccountId", "Billing Account Id", "java.lang.String", new Integer(billingAccountId), Default_Cell_Rendere),
        new ColumnDetails("contactMechId", "Contact MechId", "java.lang.String", new Integer(contactMechId), Address_Cell_Renderer),
        new ColumnDetails("invoiceDate", "Invoice Date", "java.sql.Timestamp", new Integer(invoiceDate), Default_Cell_Rendere),
        new ColumnDetails("dueDate", "Due Date", "java.sql.Timestamp", new Integer(dueDate), Default_Cell_Rendere),
        new ColumnDetails("paidDate", "Paid Date", "java.sql.Timestamp", new Integer(paidDate), Default_Cell_Rendere),
        new ColumnDetails("invoiceMessage", "Invoice Message", "java.lang.String", new Integer(invoiceMessage), StringDescription_Cell_Renderer),
        new ColumnDetails("referenceNumber", "Reference Number", "java.lang.String", new Integer(referenceNumber), Default_Cell_Rendere),
        new ColumnDetails("description", "Description", "java.lang.String", new Integer(description), StringDescription_Cell_Renderer),
        new ColumnDetails("infoString", "Email", "java.lang.String", new Integer(description), StringDescription_Cell_Renderer),
        new ColumnDetails("contactNumber", "Contact Number", "java.lang.String", new Integer(description), StringDescription_Cell_Renderer),
        new ColumnDetails("currencyUomId", "CurrencyUomId", "java.lang.String", new Integer(currencyUomId), Default_Cell_Rendere),
        new ColumnDetails("recurrenceInfoId", "Recurrence InfoId", "java.lang.String", new Integer(recurrenceInfoId), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedStamp", "Last Updated Stamp", "java.sql.Timestamp", new Integer(lastUpdatedStamp), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedTxStamp", "Last Updated TxStamp", "java.sql.Timestamp", new Integer(lastUpdatedTxStamp), Default_Cell_Rendere),
        new ColumnDetails("createdStamp", "CreatedS tamp", "java.sql.Timestamp", new Integer(createdStamp), Default_Cell_Rendere),
        new ColumnDetails("createdTxStamp", "Created TxStamp", "java.sql.Timestamp", new Integer(createdTxStamp), Default_Cell_Rendere),
        new ColumnDetails("orderId", "Order Id", "java.lang.String", new Integer(orderId), OrderId_Cell_Renderer),
        new ColumnDetails("orderTypeId", "Order TypeId", "java.lang.String", new Integer(orderTypeId), Default_Cell_Rendere),
        new ColumnDetails("productId", "Product Id", "java.lang.String", new Integer(productId), ProductTreeNode_Cell_Rendere),
        new ColumnDetails("itemDescription", "Item Description", "java.lang.String", new Integer(itemDescription), StringDescription_Cell_Renderer),
        new ColumnDetails("quantity", "Quantity", "java.math.BigDecimal", new Integer(quantity), Default_Cell_Rendere),
        new ColumnDetails("unitPrice", "Unit Price", "java.math.BigDecimal", new Integer(unitPrice), Default_Cell_Rendere),
        new ColumnDetails("roleTypeId", "Role Type Id", "java.lang.String", new Integer(roleTypeId), Default_Cell_Rendere),
        new ColumnDetails("orderName", "Order Name", "java.lang.String", new Integer(orderName), StringDescription_Cell_Renderer),
        new ColumnDetails("statusId", "Status Id", "java.lang.String", new Integer(statusId), Default_Cell_Rendere),
        new ColumnDetails("grandTotal", "Grand Total", "java.math.BigDecimal", new Integer(grandTotal), Default_Cell_Rendere),
        new ColumnDetails("remainingSubTotal", "Remaining SubTotal", "java.math.BigDecimal", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("facilityId", "facilityId", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("facilityName", "facilityName", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("ownerPartyId", "ownerPartyId", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("facilityTypeId", "facilityTypeId", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("parentFacilityId", "parentFacilityId", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("defaultInventoryItemTypeId", "defaultInventoryItemTypeId", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("productStoreId", "productStoreId", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("defaultDaysToShip", "defaultDaysToShip", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("openedDate", "openedDate", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("closedDate", "closedDate", "java.lang.String", new Integer(remainingSubTotal), Default_Cell_Rendere),
        new ColumnDetails("partyId", "partyId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyTypeId", "partyTypeId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("externalId", "externalId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("preferredCurrencyUomId", "preferredCurrencyUomId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("description", "description", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("statusId", "statusId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdDate", "createdDate", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdByUserLogin", "createdByUserLogin", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastModifiedDate", "lastModifiedDate", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastModifiedByUserLogin", "lastModifiedByUserLogin", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dataSourceId", "dataSourceId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isUnread", "isUnread", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedStamp", "lastUpdatedStamp", "java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedTxStamp", "lastUpdatedTxStamp", "java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdStamp", "createdStamp", "java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdTxStamp", "createdTxStamp", "java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdTxStamp", "createdTxStamp", "java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyId", "partyId", "class java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("salutation", "salutation", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("firstName", "firstName", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("middleName", "middleName", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastName", "lastName", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("personalTitle", "personalTitle", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("suffix", "suffix", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("nickname", "nickname", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("firstNameLocal", "firstNameLocal", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("middleNameLocal", "middleNameLocal", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastNameLocal", "lastNameLocal", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("otherLocal", "otherLocal", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("memberId", "memberId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("gender", "gender", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("birthDate", "birthDate", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("deceasedDate", "deceasedDate", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("height", "height", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("weight", "weight", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("mothersMaidenName", "mothersMaidenName", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maritalStatus", "maritalStatus", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("socialSecurityNumber", "socialSecurityNumber", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("passportNumber", "passportNumber", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("passportExpireDate", "passportExpireDate", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalYearsWorkExperience", "totalYearsWorkExperience", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("comments", "comments", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("employmentStatusEnumId", "employmentStatusEnumId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("residenceStatusEnumId", "residenceStatusEnumId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("occupation", "occupation", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("yearsWithEmployer", "yearsWithEmployer", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("monthsWithEmployer", "monthsWithEmployer", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("existingCustomer", "existingCustomer", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cardId", "cardId", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedStamp", "lastUpdatedStamp", "class java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedTxStamp", "lastUpdatedTxStamp", "class java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdStamp", "createdStamp", "class java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdTxStamp", "createdTxStamp", "class java.sql.Timestamp", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryCode", "Country Code", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryCapital", "Country Capital", "java.lang.String", new Integer(STATUSID_COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drDataResourceId", "Dr Data Resource Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("roundingMode", "Rounding Mode", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("passwordUsed", "Password Used", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productHeight", "Product Height", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("username", "Username", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("logDir", "Log Dir", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("estimatedCompletionDate", "Estimated Completion Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentServiceTypeEnumId", "Payment Service Type Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("emailCustomer", "Email Customer", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("actualStartDate", "Actual Start Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("textData", "Text Data", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("daysToCancelNonPay", "Days To Cancel Non Pay", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pricePriceWithTax", "Price Price With Tax", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("previousStatusId", "Previous Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("wednesdayCapacity", "Wednesday Capacity", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("createdBy", "Created By", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("askForName", "Ask For Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyToLastName", "Party To Last Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("selectPaymentTypePerItem", "Select Payment Type Per Item", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("targetDelegatorName", "Target Delegator Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tnCountryCode", "Tn Country Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("logEnabled", "Log Enabled", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityProduced", "Quantity Produced", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("calendarWeekId", "Calendar Week Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("ccAddress", "Cc Address", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("clientHostName", "Client Host Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("estimatedStartDate", "Estimated Start Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("featurePrice", "Feature Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("transDescription", "Trans Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxFormId", "Tax Form Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("customMethodName", "Custom Method Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentRouteSegmentId", "Shipment Route Segment Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("itemStatus", "Item Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("abbreviation", "Abbreviation", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oiExternalId", "Oi External Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("bccAddress", "Bcc Address", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("actualTransportCost", "Actual Transport Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("startingDrawerAmount", "Starting Drawer Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("purchaseFromDate", "Purchase From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("expirationPeriod", "Expiration Period", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryName", "Country Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("boxNumber", "Box Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("decimalScale", "Decimal Scale", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requireConfirmedShipping", "Require Confirmed Shipping", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productFeatureCategoryId", "Product Feature Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("topDescription", "Top Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("changeByUserLoginId", "Change By User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("changedDate", "Changed Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("refundPwd", "Refund Pwd", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priority", "Priority", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceTaxAuthGeoId", "Price Tax Auth Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("releaseUrl", "Release Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("minEstimateWeight", "Min Estimate Weight", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isSummary", "Is Summary", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmPaymentPreferenceId", "Pm Payment Preference Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("apiPassword", "Api Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productPiecesIncluded", "Product Pieces Included", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("billShipperAccountNumber", "Bill Shipper Account Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requestUrl", "Request Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("aisleId", "Aisle Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("billingWeightUomId", "Billing Weight Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("sundayCapacity", "Sunday Capacity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drLastModifiedDate", "Dr Last Modified Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("useTimeUomId", "Use Time Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("quoteIdPrefix", "Quote Id Prefix", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("statusCode", "Status Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldQuoteSequenceEnumId", "Old Quote Sequence Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnItemSeqId", "Return Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("detailImageUrl", "Detail Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tuesdayCapacity", "Tuesday Capacity", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastSuccessfulSynchTime", "Last Successful Synch Time", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("parentPortalPageId", "Parent Portal Page Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("binEndDateTime", "Bin End Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tempExprTypeId", "Temp Expr Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnHeaderTypeId", "Return Header Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("disabledDateTime", "Disabled Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accessUserKey", "Access User Key", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productRatingTypeEnum", "Product Rating Type Enum", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productLastModifiedByUserLogin", "Product Last Modified By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productLongDescription", "Product Long Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("periodLength", "Period Length", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("ownerContentId", "Owner Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("delimiterChar", "Delimiter Char", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmAmount", "Pm Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coDataSourceId", "Co Data Source Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("postalCode", "Postal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("theirProductId", "Their Product Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destAddress1", "Dest Address 1", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productIntroductionDate", "Product Introduction Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("deploymentId", "Deployment Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderStatusId", "Order Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("entryDate", "Entry Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("attrValue", "Attr Value", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accountPostedBalance", "Account Posted Balance", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originDirections", "Origin Directions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("availableFromDate", "Available From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("preferenceStatusId", "Preference Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("finAccountTransId", "Fin Account Trans Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("parentPaymentTypeId", "Parent Payment Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destCity", "Dest City", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("actualEndingCc", "Actual Ending Cc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enableAmountRound", "Enable Amount Round", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("assocProductCount", "Assoc Product Count", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countyGeoSecCode", "County Geo Sec Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paPostalCode", "Pa Postal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("payToPartyId", "Pay To Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("reasonEnumId", "Reason Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("maxSize", "Max Size", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityOnHandTotal", "Quantity On Hand Total", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("origAmount", "Orig Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accessLicenseNumber", "Access License Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("finAccountId", "Fin Account Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceWithTax", "Price With Tax", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("vatTaxAuthGeoId", "Vat Tax Auth Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("countryAbbr", "Country Abbr", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isSystem", "Is System", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceLastModifiedByUserLogin", "Price Last Modified By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("groupId", "Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("wellKnownText", "Well Known Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("saturdayStartTime", "Saturday Start Time", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("logEndDateTime", "Log End Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dynamicAccess", "Dynamic Access", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maxRunningNoUpdateMillis", "Max Running No Update Millis", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("facilityGroupTypeId", "Facility Group Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnReasonId", "Return Reason Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("byMonthList", "By Month List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drIsPublic", "Dr Is Public", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("emplPositionTypeId", "Empl Position Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("coContentTypeId", "Co Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("responsibilityTypeId", "Responsibility Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("caContentAssocPredicateId", "Ca Content Assoc Predicate Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("logStartDateTime", "Log Start Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coLastModifiedByUserLogin", "Co Last Modified By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("changedEntityName", "Changed Entity Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("apiUserName", "Api User Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productOriginGeoId", "Product Origin Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("hostNameFailover", "Host Name Failover", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("stateGeoCode", "State Geo Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityIssued", "Quantity Issued", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("heightUomId", "Height Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("checkCvv2", "Check Cvv 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("entityOrPackage", "Entity Or Package", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("autoCreateKeywords", "Auto Create Keywords", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("screenshot", "Screenshot", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("invoiceIdPrefix", "Invoice Id Prefix", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortContentTypeId", "Work Effort Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("agreementId", "Agreement Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("defaultPackagingType", "Default Packaging Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("telecomContactMechId", "Telecom Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceCurrencyUomId", "Price Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("cookieDomain", "Cookie Domain", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("containerId", "Container Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("geoTypeId", "Geo Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destinationContactMechId", "Destination Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productShippingWidth", "Product Shipping Width", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("unitAverageCost", "Unit Average Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceUomId", "Price Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("integer1", "Integer 1", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityUomId", "Quantity Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("successiveFailedLogins", "Successive Failed Logins", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("primaryShipGroupSeqId", "Primary Ship Group Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("integer2", "Integer 2", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coCharacterSetId", "Co Character Set Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("partyQualTypeId", "Party Qual Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("serviceLoaderName", "Service Loader Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("autoApproveOrder", "Auto Approve Order", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accessPassword", "Access Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("parentTypeId", "Parent Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("fixedAssetId", "Fixed Asset Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("autoSaveCart", "Auto Save Cart", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("proxyPassword", "Proxy Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastInvoiceRestartDate", "Last Invoice Restart Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldHeaderLogo", "Old Header Logo", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("firstNameOnCard", "First Name On Card", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("geoIdTo", "Geo Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destAddress2", "Dest Address 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("additionalShippingCharge", "Additional Shipping Charge", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productOrderDecimalQuantity", "Product Order Decimal Quantity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enableBeagle", "Enable Beagle", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("subProductQuantity", "Sub Product Quantity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmPaymentRefNum", "Pm Payment Ref Num", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("timeout", "Timeout", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("customerClassification", "Customer Classification", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("merchantKeyStorePassword", "Merchant Key Store Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("supplierRatingTypeId", "Supplier Rating Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderIdPrefix", "Order Id Prefix", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("originCountryCode", "Origin Country Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accessUserPwd", "Access User Pwd", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("endingBalance", "Ending Balance", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("production", "Production", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentTypeId", "Shipment Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("voidUrl", "Void Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentTypeDesc", "Payment Type Desc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("entityGroupId", "Entity Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drDataCategoryId", "Dr Data Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortToEstimatedStartDate", "Work Effort To Estimated Start Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("trackingIdNumber", "Tracking Id Number", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("settlementTermId", "Settlement Term Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("estimatedSetupMillis", "Estimated Setup Millis", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("hitTypeId", "Hit Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("effectiveAlias", "Effective Alias", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalSubRemainingAmount", "Total Sub Remaining Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("midDescription", "Mid Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("theirAcctgTransId", "Their Acctg Trans Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("visitorId", "Visitor Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productDetailScreen", "Product Detail Screen", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("columnSeqId", "Column Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("byDayList", "By Day List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("issuedByUserLoginId", "Issued By User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("loggingLevel", "Logging Level", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("estimatedArrivalDate", "Estimated Arrival Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("salesOpportunityId", "Sales Opportunity Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("sdkVersion", "Sdk Version", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productProductDiameter", "Product Product Diameter", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("groupNameLocal", "Group Name Local", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("boxWidth", "Box Width", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderBlacklistTypeId", "Order Blacklist Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("authoriseTransType", "Authorise Trans Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("numOfBytes", "Num Of Bytes", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("elevation", "Elevation", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("reserveOrderEnumId", "Reserve Order Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paymentGatewayConfigId", "Payment Gateway Config Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destDirections", "Dest Directions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalGrandAmount", "Total Grand Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productCreatedByUserLogin", "Product Created By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("toRoleTypeId", "To Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("accessMeterNumber", "Access Meter Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enableCVM", "Enable Cvm", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("scheduledPostingDate", "Scheduled Posting Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("autoApproveReviews", "Auto Approve Reviews", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("periodName", "Period Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productProductRating", "Product Product Rating", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fromRoleTypeId", "From Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("privateCert", "Private Cert", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("typeHasTable", "Type Has Table", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("metaDataPredicateId", "Meta Data Predicate Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("portletSeqId", "Portlet Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("viewCartOnAdd", "View Cart On Add", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("invoiceRolePartyId", "Invoice Role Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("parentInvoiceItemSeqId", "Parent Invoice Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("thirdPartyAccountNumber", "Third Party Account Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originToName", "Origin To Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tranKey", "Tran Key", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productAverageCostTypeId", "Product Average Cost Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("numberHits", "Number Hits", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("redirectUrl", "Redirect Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productSupportDiscontinuationDate", "Product Support Discontinuation Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enabled", "Enabled", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmActualCurrencyUomId", "Pm Actual Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderItemCancelQuantity", "Order Item Cancel Quantity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cardType", "Card Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("carrierRestrictionDesc", "Carrier Restriction Desc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("specialTerms", "Special Terms", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentGatewayResponseId", "Payment Gateway Response Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("budgetId", "Budget Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("codSurchargeAmount", "Cod Surcharge Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("termValue", "Term Value", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("salesDiscontinuationDate", "Sales Discontinuation Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("facilitySizeUomId", "Facility Size Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("noLanguageMenu", "No Language Menu", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("delimitedData", "Delimited Data", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyRelationshipName", "Party Relationship Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("hideContact", "Hide Contact", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quoteItemSeqId", "Quote Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("cancelDateTime", "Cancel Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productWidth", "Product Width", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("weightUomId", "Weight Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("enumTypeId", "Enum Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("postalCodeExt", "Postal Code Ext", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderItemTypeId", "Order Item Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("configProps", "Config Props", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceTaxAmount", "Price Tax Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originalPortalPageId", "Original Portal Page Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("invoiceItemTypeId", "Invoice Item Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("acquirerKeyStoreFilename", "Acquirer Key Store Filename", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("estimateCalcMethod", "Estimate Calc Method", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("catalogName", "Catalog Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("annualRevenue", "Annual Revenue", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("custRequestResolutionId", "Cust Request Resolution Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("stateGeoId", "State Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("caMapKey", "Ca Map Key", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultSalesChannelEnumId", "Default Sales Channel Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shipmentGatewayConfigId", "Shipment Gateway Config Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("quantityAccepted", "Quantity Accepted", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("acctgTransTypeId", "Acctg Trans Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productFeatureTypeId", "Product Feature Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderedQuantity", "Ordered Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priorityTypeId", "Priority Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("authDeclinedMessage", "Auth Declined Message", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("loaderName", "Loader Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("weightUomDescription", "Weight Uom Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentService", "Payment Service", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("processMode", "Process Mode", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("itemApprovedStatus", "Item Approved Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("documentTypeId", "Document Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("invoiceSeqCustMethId", "Invoice Seq Cust Meth Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("changedSessionInfo", "Changed Session Info", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("addToCartRemoveIncompat", "Add To Cart Remove Incompat", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("prodCatalogId", "Prod Catalog Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastOrderNumber", "Last Order Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productPriceTypeId", "Product Price Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("intervalNumber", "Interval Number", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("entitySyncId", "Entity Sync Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("avsDeclineCodes", "Avs Decline Codes", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("insuredValue", "Insured Value", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("explodeOrderItems", "Explode Order Items", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("voucherDate", "Voucher Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("logoImageUrl", "Logo Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("sslSocketFactory", "Ssl Socket Factory", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("parentGlAccountId", "Parent Gl Account Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastQuoteNumber", "Last Quote Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("authFraudMessage", "Auth Fraud Message", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentMethodDescription", "Shipment Method Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("checkAvs", "Check Avs", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldStyleSheet", "Old Style Sheet", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("poolId", "Pool Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drCharacterSetId", "Dr Character Set Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortToRun", "Work Effort To Run", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("topRoleTypeId", "Top Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paymentApplicationId", "Payment Application Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("costComponentTypeId", "Cost Component Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productFeatureGroupId", "Product Feature Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("itemCancelStatus", "Item Cancel Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drDataResourceName", "Dr Data Resource Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("selectedAmount", "Selected Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paCountyGeoId", "Pa County Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shippingHeight", "Shipping Height", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("actualEndingOther", "Actual Ending Other", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("successfulLogin", "Successful Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("excludeGeoId", "Exclude Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pickSheetPrintedDate", "Pick Sheet Printed Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productFacilityId", "Product Facility Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("excludeFeatureGroup", "Exclude Feature Group", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("headerCancelStatus", "Header Cancel Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userId", "User Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productStoreGroupName", "Product Store Group Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("longitude", "Longitude", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isRefundable", "Is Refundable", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultDropoffType", "Default Dropoff Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("useQuickAdd", "Use Quick Add", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("recurringFreqUomId", "Recurring Freq Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnId", "Return Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("boxWeight", "Box Weight", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryGeoTypeId", "Country Geo Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("entityGroupName", "Entity Group Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("virtualVariantMethodEnum", "Virtual Variant Method Enum", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentContentDescription", "Shipment Content Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("currencyUom", "Currency Uom", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalOrders", "Total Orders", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("ownerUserLoginId", "Owner User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("sequenceNumber", "Sequence Number", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("weightBreakId", "Weight Break Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("showInSelect", "Show In Select", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipperNumber", "Shipper Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caCreatedDate", "Ca Created Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("untilDateTime", "Until Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coChildLeafCount", "Co Child Leaf Count", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("deliverableId", "Deliverable Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productPricePurposeId", "Product Price Purpose Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drLastModifiedByUserLogin", "Dr Last Modified By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortToParentId", "Work Effort To Parent Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shipmentPackageSeqId", "Shipment Package Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("handlingInstructions", "Handling Instructions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmStatusId", "Pm Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("refundUrl", "Refund Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentPreferenceId", "Payment Preference Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("swipedFlag", "Swiped Flag", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("roleTypeIdValidFrom", "Role Type Id Valid From", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paCity", "Pa City", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("presentFlag", "Present Flag", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("thursdayCapacity", "Thursday Capacity", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceThruDate", "Price Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("deliveryId", "Delivery Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmCurrencyUomId", "Pm Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("monthsWithContactMech", "Months With Contact Mech", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentGatewayConfigTypeId", "Payment Gateway Config Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("authorizationURI", "Authorization Uri", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productInternalName", "Product Internal Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("hasTable", "Has Table", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destStateProvinceGeoId", "Dest State Province Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("proxyLogon", "Proxy Logon", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("invoiceItemSeqId", "Invoice Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("carrierRestrictionCodes", "Carrier Restriction Codes", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originAttnName", "Origin Attn Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dataResourceTypeId", "Data Resource Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("hostName", "Host Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partner", "Partner", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryNumber", "Country Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("transactionUrl", "Transaction Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dataTemplateTypeId", "Data Template Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("contentIdTo", "Content Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productWeightUomId", "Product Weight Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("glXbrlClassId", "Gl Xbrl Class Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("contactFromDate", "Contact From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lowDescription", "Low Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("saveCertPath", "Save Cert Path", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destPostalCodeGeoId", "Dest Postal Code Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shipBeforeDate", "Ship Before Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("carrierServiceStatusId", "Carrier Service Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualOtherCost", "Actual Other Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destCountryGeoId", "Dest Country Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destFacilityId", "Dest Facility Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oldValueText", "Old Value Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("elevationUomId", "Elevation Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("tnContactNumber", "Tn Contact Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destinationFacilityId", "Destination Facility Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("defaultReturnLabelSubject", "Default Return Label Subject", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("statusDesc", "Status Desc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("digitalItemApprovedStatus", "Digital Item Approved Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("groupName", "Group Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxInPrice", "Tax In Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxableFlag", "Taxable Flag", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentCustomMethodId", "Payment Custom Method Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("headerApprovedStatus", "Header Approved Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originTelecomNumberId", "Origin Telecom Number Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("includeNoChargeItems", "Include No Charge Items", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("budgetItemSeqId", "Budget Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("estimatedBudget", "Estimated Budget", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("effectiveDate", "Effective Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityToProduce", "Quantity To Produce", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("templateLocation", "Template Location", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("origCurrencyUomId", "Orig Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("primaryParentCategoryId", "Primary Parent Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productStoreShipMethId", "Product Store Ship Meth Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("baseCurrencyUomId", "Base Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("parentProductCategoryId", "Parent Product Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualMilliSeconds", "Actual Milli Seconds", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityOnHandDiff", "Quantity On Hand Diff", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("postedDate", "Posted Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("standardLeadTimeDays", "Standard Lead Time Days", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("prorateTaxes", "Prorate Taxes", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paStateProvinceGeoId", "Pa State Province Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("overflowFlag", "Overflow Flag", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("internalName", "Internal Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("parentPeriodId", "Parent Period Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmEffectiveDate", "Pm Effective Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceTaxAuthPartyId", "Price Tax Auth Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("address1", "Address 1", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("reservNthPPPerc", "Reserv Nth Pp Perc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("availableToPromiseTotal", "Available To Promise Total", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("address2", "Address 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("stackTraceOn", "Stack Trace On", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enableAutoSuggestionList", "Enable Auto Suggestion List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderItemStatusId", "Order Item Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("jobName", "Job Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentGroupTypeId", "Payment Group Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("templateShipment", "Template Shipment", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("billOfMaterialLevel", "Bill Of Material Level", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("categoryImageUrl", "Category Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isModifiedPrice", "Is Modified Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userPrefDataType", "User Pref Data Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productQuantityIncluded", "Product Quantity Included", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("inventoryItemId", "Inventory Item Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destAttnName", "Dest Attn Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("acctgTransEntrySeqId", "Acctg Trans Entry Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("dataResourceName", "Data Resource Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contentPathPrefix", "Content Path Prefix", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("acquirerKeyStorePassword", "Acquirer Key Store Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("portalPortletId", "Portal Portlet Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("subProductId", "Sub Product Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("emailTemplateSettingId", "Email Template Setting Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("showOutOfStockProducts", "Show Out Of Stock Products", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyContentTypeId", "Party Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("trackingNumber", "Tracking Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("useInvoiceIdForReturns", "Use Invoice Id For Returns", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("ratingTypeEnum", "Rating Type Enum", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productLargeImageUrl", "Product Large Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("typeDescription", "Type Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentId", "Payment Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("coDescription", "Co Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("sourceId", "Source Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("customMethodId", "Custom Method Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmPaymentGatewayResponseId", "Pm Payment Gateway Response Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oldQuantityOnHand", "Old Quantity On Hand", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyGroupComments", "Party Group Comments", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryGeoId", "Country Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("partyToGroupName", "Party To Group Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("closedByUserLoginId", "Closed By User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oldInvoiceSequenceEnumId", "Old Invoice Sequence Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("headerDeclinedStatus", "Header Declined Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("langCode2", "Lang Code 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("siteName", "Site Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originCountryGeoId", "Origin Country Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("byMinuteList", "By Minute List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("preAuth", "Pre Auth", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oiStatusId", "Oi Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortToName", "Work Effort To Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("mondayCapacity", "Monday Capacity", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("billingWeight", "Billing Weight", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("attnName", "Attn Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oiEstimatedShipDate", "Oi Estimated Ship Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("packageOtherCost", "Package Other Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("showPricesWithVatTax", "Show Prices With Vat Tax", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("recurrenceCount", "Recurrence Count", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("inventoryMessage", "Inventory Message", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("runTime", "Run Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coContentId", "Co Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("withDelivery", "With Delivery", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentCostEstimateId", "Shipment Cost Estimate Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("runByInstanceId", "Run By Instance Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("estimatedMilliSeconds", "Estimated Milli Seconds", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("webAnalyticsTypeId", "Web Analytics Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("noteId", "Note Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isPhysical", "Is Physical", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("autoOrderCcTryLaterMax", "Auto Order Cc Try Later Max", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("configId", "Config Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("connectSoapUrl", "Connect Soap Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caFromDate", "Ca From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("expireDate", "Expire Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("changedByInfo", "Changed By Info", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("stateGeoName", "State Geo Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderItemGroupSeqId", "Order Item Group Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("transitionName", "Transition Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("trainingClassTypeId", "Training Class Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("manualAuthIsCapture", "Manual Auth Is Capture", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("subject", "Subject", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("averageCost", "Average Cost", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("reqShipAddrForDigItems", "Req Ship Addr For Dig Items", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("upperCoordinate", "Upper Coordinate", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contentId", "Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("supplierPrefOrderId", "Supplier Pref Order Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("applEnumId", "Appl Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("overrideOrgPartyId", "Override Org Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("finishDateTime", "Finish Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lotId", "Lot Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("checkGcBalance", "Check Gc Balance", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceTaxInPrice", "Price Tax In Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyFromLastName", "Party From Last Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coStatusId", "Co Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("teleCode", "Tele Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("universalId", "Universal Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productDiameter", "Product Diameter", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productBrandName", "Product Brand Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("purposeFromDate", "Purpose From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortTypeId", "Work Effort Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("connectUrl", "Connect Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("resourceTypeEnumId", "Resource Type Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("coChildBranchCount", "Co Child Branch Count", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("periodTypeId", "Period Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productPriceActionTypeId", "Product Price Action Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isRushOrder", "Is Rush Order", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("infoUrl", "Info Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("includeFeatureGroup", "Include Feature Group", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("duplicateWindow", "Duplicate Window", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("needsNsfRetry", "Needs Nsf Retry", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmPaymentId", "Pm Payment Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("byMonthDayList", "By Month Day List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("amountTotal", "Amount Total", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("employerPaidPercentage", "Employer Paid Percentage", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyStatusId", "Party Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceProductStoreGroupId", "Price Product Store Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("packageServiceCost", "Package Service Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("testMode", "Test Mode", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("longDescription", "Long Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityBreakTypeId", "Quantity Break Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("editFormName", "Edit Form Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productSmallImageUrl", "Product Small Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("toPartyId", "To Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("autoBill", "Auto Bill", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("labelHtml", "Label Html", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destCountryCode", "Dest Country Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("currentStatusId", "Current Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("contentAssocTypeId", "Content Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("endingTxId", "Ending Tx Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("wednesdayStartTime", "Wednesday Start Time", "java.sql.Time", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("assocPriceFromDate", "Assoc Price From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fromPartyId", "From Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("termTypeId", "Term Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destContactMechId", "Dest Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("apiSignature", "Api Signature", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("numEmployees", "Num Employees", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("langCode3b", "Lang Code 3B", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oiSyncStatusId", "Oi Sync Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("assocMaxPrice", "Assoc Max Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentTypeId", "Payment Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paContactMechId", "Pa Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("defaultDimensionUomId", "Default Dimension Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("releaseDate", "Release Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("langCode3t", "Lang Code 3T", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productShippingDepth", "Product Shipping Depth", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("scopeEnumId", "Scope Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("stateWellKnownText", "State Well Known Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("inShippingBox", "In Shipping Box", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productVirtualVariantMethodEnum", "Product Virtual Variant Method Enum", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("typeParentTypeId", "Type Parent Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("coOwnerContentId", "Co Owner Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortToActualStartDate", "Work Effort To Actual Start Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("estimatedReadyDate", "Estimated Ready Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("purposeDescription", "Purpose Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contentIdStart", "Content Id Start", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("estimatedShipDate", "Estimated Ship Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pricePriceWithoutTax", "Price Price Without Tax", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("changedFieldName", "Changed Field Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmPaymentTypeId", "Pm Payment Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("titleTransferEnumId", "Title Transfer Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("taxShipping", "Tax Shipping", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oversizePrice", "Oversize Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productFeatureIactnTypeId", "Product Feature Iactn Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("showTaxIsExempt", "Show Tax Is Exempt", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("datetimeManufactured", "Datetime Manufactured", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("skillTypeId", "Skill Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("serverIpAddress", "Server Ip Address", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("amountUomTypeId", "Amount Uom Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("moneyUomId", "Money Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("currentPassword", "Current Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("supplierCommissionPerc", "Supplier Commission Perc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("assocProductId", "Assoc Product Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceProductPriceTypeId", "Price Product Price Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("characterSetId", "Character Set Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("physicalInventoryId", "Physical Inventory Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("price", "Price", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productChargeShipping", "Product Charge Shipping", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("invoiceItemAssocTypeId", "Invoice Item Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("tuesdayStartTime", "Tuesday Start Time", "java.sql.Time", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("latitude", "Latitude", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("connectUrlLabels", "Connect Url Labels", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("primaryParentGroupId", "Primary Parent Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("glResourceTypeId", "Gl Resource Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("locationTypeEnumId", "Location Type Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("jobId", "Job Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("assocCurrencyUomId", "Assoc Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("statusDate", "Status Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityOrdered", "Quantity Ordered", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("geoId", "Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shoppingListTypeId", "Shopping List Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("originPostalCodeGeoId", "Origin Postal Code Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("customTimePeriodId", "Custom Time Period Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("usePrimaryEmailUsername", "Use Primary Email Username", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("bodyScreenLocation", "Body Screen Location", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fixedAssetIdentTypeId", "Fixed Asset Ident Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("caUpperCoordinate", "Ca Upper Coordinate", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("itemIssuanceId", "Item Issuance Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("contentTypeId", "Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("partyToFirstName", "Party To First Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cancelBackOrderDate", "Cancel Back Order Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedDate", "Last Updated Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentMethod", "Payment Method", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paGeoPointId", "Pa Geo Point Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("parentInvoiceId", "Parent Invoice Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("passwordHint", "Password Hint", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderItemFlatPrice", "Order Item Flat Price", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("statusDatetime", "Status Datetime", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("vatTaxAuthPartyId", "Vat Tax Auth Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pushEntitySyncId", "Push Entity Sync Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("taxAuthorityAssocTypeId", "Tax Authority Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("requireTaxIdForExemption", "Require Tax Id For Exemption", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("receivedByUserLoginId", "Received By User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("parentCategoryId", "Parent Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("requireUspsAddr", "Require Usps Addr", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("runtimeInfo", "Runtime Info", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("retryFailedAuths", "Retry Failed Auths", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productDepthUomId", "Product Depth Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("fraudScore", "Fraud Score", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldAvailableToPromise", "Old Available To Promise", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipperPickupType", "Shipper Pickup Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("finAccountTransTypeId", "Fin Account Trans Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("internalCode", "Internal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contactMechPurposeTypeId", "Contact Mech Purpose Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("autoInvoiceDigitalItems", "Auto Invoice Digital Items", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coLastModifiedDate", "Co Last Modified Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("attrType", "Attr Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("thirdPartyCountryGeoCode", "Third Party Country Geo Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryGeoName", "Country Geo Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accessUserId", "Access User Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("geoPointId", "Geo Point Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("confirmTemplate", "Confirm Template", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destinationTelecomNumberId", "Destination Telecom Number Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("manualRefNum", "Manual Ref Num", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requirementId", "Requirement Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("countyWellKnownText", "County Well Known Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("titleOnCard", "Title On Card", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("glAccountId", "Gl Account Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("defaultReturnLabelMemo", "Default Return Label Memo", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("issuedDateTime", "Issued Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("reserv2ndPPPerc", "Reserv 2Nd Pp Perc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("inventoryItemDetailSeqId", "Inventory Item Detail Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productPrimaryProductCategoryId", "Product Primary Product Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("portletName", "Portlet Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isPromo", "Is Promo", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalTimesViewed", "Total Times Viewed", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("jobInterviewTypeId", "Job Interview Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("packageTransportCost", "Package Transport Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxPercentage", "Tax Percentage", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productContentTypeId", "Product Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("totalMilliSecondsAllowed", "Total Milli Seconds Allowed", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("runAsUser", "Run As User", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("salesChannelEnumId", "Sales Channel Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastInvoiceNumber", "Last Invoice Number", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("insuredAmount", "Insured Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productQuantityUomId", "Product Quantity Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("logSize", "Log Size", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("statusUserLogin", "Status User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("categoryName", "Category Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentId", "Shipment Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortToWorkEffortPurposeTypeId", "Work Effort To Work Effort Purpose Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("weightUomAbbreviation", "Weight Uom Abbreviation", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("information", "Information", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oversizeUnit", "Oversize Unit", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("inventoryFacilityId", "Inventory Facility Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("quoteTypeId", "Quote Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("exceptionDateTimes", "Exception Date Times", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paPostalCodeExt", "Pa Postal Code Ext", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coContentName", "Co Content Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("theirPartyId", "Their Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("glAccountCategoryTypeId", "Gl Account Category Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isCreate", "Is Create", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxPromotions", "Tax Promotions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("authenticationTransType", "Authentication Trans Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requiredByDate", "Required By Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("columnWidthPercentage", "Column Width Percentage", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countyGeoName", "County Geo Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("terminalName", "Terminal Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maxTotal", "Max Total", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originFacilityId", "Origin Facility Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productPriceActionSeqId", "Product Price Action Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("taxIdFormatPattern", "Tax Id Format Pattern", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("caCreatedByUserLogin", "Ca Created By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastHistoryStartDate", "Last History Start Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("title", "Title", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("largeImageUrl", "Large Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("startingTxId", "Starting Tx Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("recurrenceRuleId", "Recurrence Rule Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("companyNameOnCard", "Company Name On Card", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("revisionNumber", "Revision Number", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("logFileName", "Log File Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("areaCode", "Area Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("includeTaxInPrice", "Include Tax In Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("authUserLoginId", "Auth User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("changeReason", "Change Reason", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderItemQuantity", "Order Item Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enableDigProdUpload", "Enable Dig Prod Upload", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldHeaderRightBackground", "Old Header Right Background", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("preOfflineSynchTime", "Pre Offline Synch Time", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("trackingDigest", "Tracking Digest", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("needsInventoryIssuance", "Needs Inventory Issuance", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("calendarId", "Calendar Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("imageUrl", "Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productRequireInventory", "Product Require Inventory", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productMediumImageUrl", "Product Medium Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caLastModifiedDate", "Ca Last Modified Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("assocProductStoreGroupId", "Assoc Product Store Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lowRoleTypeId", "Low Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isDemoStore", "Is Demo Store", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("vendorPartyId", "Vendor Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("auditHistorySeqId", "Audit History Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("statusTypeId", "Status Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("date2", "Date 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contactThruDate", "Contact Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("latestCancelDate", "Latest Cancel Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("date1", "Date 1", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productReturnable", "Product Returnable", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("minSize", "Min Size", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maxLogFileSize", "Max Log File Size", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("minWeight", "Min Weight", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("certificateAlias", "Certificate Alias", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("connectionPassword", "Connection Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("termName", "Term Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coTemplateDataResourceId", "Co Template Data Resource Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastNameOnCard", "Last Name On Card", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("httpsPort", "Https Port", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("boxHeight", "Box Height", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("reservPersons", "Reserv Persons", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productMeterTypeId", "Product Meter Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderAdjustmentTypeId", "Order Adjustment Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("authoriseUrl", "Authorise Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("facilityGroupName", "Facility Group Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fixedAssetProductTypeId", "Fixed Asset Product Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("coMimeTypeId", "Co Mime Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("apiVersion", "Api Version", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("codSurchargeCurrencyUomId", "Cod Surcharge Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("defaultWeightUomId", "Default Weight Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("langFamily", "Lang Family", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("authenticationUrl", "Authentication Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("estimatedDeliveryDate", "Estimated Delivery Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("apiEnvironment", "Api Environment", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accountName", "Account Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("keyText", "Key Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("sourceReferenceId", "Source Reference Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("groupStatusId", "Group Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productName", "Product Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("assocPriceTypeId", "Assoc Price Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnUrl", "Return Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceLastModifiedDate", "Price Last Modified Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("acctgTransId", "Acctg Trans Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceWithoutTax", "Price Without Tax", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("portFailover", "Port Failover", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("terminalId", "Terminal Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drCreatedDate", "Dr Created Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("byWeekNoList", "By Week No List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("reservedQuantity", "Reserved Quantity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("initialRequest", "Initial Request", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productWidthUomId", "Product Width Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("postalCodeGeoId", "Postal Code Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("includeGeoId", "Include Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastStatusUpdate", "Last Status Update", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("applyToAllProducts", "Apply To All Products", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caContentAssocTypeId", "Ca Content Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("hitStartDateTime", "Hit Start Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maxAmount", "Max Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("track2", "Track 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("companyName", "Company Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("typeUomTypeId", "Type Uom Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isGift", "Is Gift", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("processTimeout", "Process Timeout", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("postedDebits", "Posted Debits", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("refundPaymentMethodId", "Refund Payment Method Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isDigital", "Is Digital", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("itemCount", "Item Count", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contentOperationId", "Content Operation Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualCurrencyUomId", "Actual Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("merchantContact", "Merchant Contact", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderItemPriceInfoId", "Order Item Price Info Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("prodCatContentTypeId", "Prod Cat Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnItemTypeId", "Return Item Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("customerId", "Customer Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("customMethodTypeId", "Custom Method Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("countryGeoCode", "Country Geo Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requireCompanyAddr", "Require Company Addr", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalPostedCredits", "Total Posted Credits", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productProductName", "Product Product Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("locationDesc", "Location Desc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("canDropShip", "Can Drop Ship", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("carrierPartyId", "Carrier Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("countyAbbreviation", "County Abbreviation", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originPostalCode", "Origin Postal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("roleTypeIdFrom", "Role Type Id From", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("suffixOnCard", "Suffix On Card", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("securityCode", "Security Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caContentId", "Ca Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("countNumber", "Count Number", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("actualCompletionDate", "Actual Completion Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("autoCancelDate", "Auto Cancel Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentPropertiesPath", "Payment Properties Path", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enumCode", "Enum Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("unitsIncluded", "Units Included", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("brandName", "Brand Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coCreatedByUserLogin", "Co Created By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("sagePayMode", "Sage Pay Mode", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentItemSeqId", "Shipment Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("useCountLimit", "Use Count Limit", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("widthUomId", "Width Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceBreakId", "Price Break Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("estimatedShipCost", "Estimated Ship Cost", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("numberSpecified", "Number Specified", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("leftCoordinate", "Left Coordinate", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("idValue", "Id Value", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("syncEndBufferMillis", "Sync End Buffer Millis", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("refByWebContactMechId", "Ref By Web Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortIdFrom", "Work Effort Id From", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("clientIpIspName", "Client Ip Isp Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productReleaseDate", "Product Release Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("sessionId", "Session Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("assocMinPrice", "Assoc Min Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("parentJobId", "Parent Job Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("datetimeReceived", "Datetime Received", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requirementMethodEnumId", "Requirement Method Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productPriceRuleId", "Product Price Rule Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("thruDate", "Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cpVersion", "Cp Version", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cpMarketType", "Cp Market Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultGlAccountId", "Default Gl Account Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("areaId", "Area Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("budgetItemTypeId", "Budget Item Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shipIfCaptureFails", "Ship If Capture Fails", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maxRetry", "Max Retry", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("glAccountGroupId", "Gl Account Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("rejectionId", "Rejection Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productPriceChangeId", "Product Price Change Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("bySecondList", "By Second List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productProductHeight", "Product Product Height", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("glJournalName", "Gl Journal Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("httpsHost", "Https Host", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coServiceName", "Co Service Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fromAddress", "From Address", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("byHourList", "By Hour List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("byYearDayList", "By Year Day List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("receiptId", "Receipt Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("reason", "Reason", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drDataSourceId", "Dr Data Source Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("securityMainAction", "Security Main Action", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortToActualCompletionDate", "Work Effort To Actual Completion Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shippingCallbackUrl", "Shipping Callback Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("stateGeoSecCode", "State Geo Sec Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("storeCreditValidDays", "Store Credit Valid Days", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dontCancelSetUserLogin", "Dont Cancel Set User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("uomTypeId", "Uom Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("officeSiteName", "Office Site Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requirementTypeId", "Requirement Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("glAccountGroupTypeId", "Gl Account Group Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("geoIdFrom", "Geo Id From", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("autoOrderCcTryExp", "Auto Order Cc Try Exp", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productStoreGroupId", "Product Store Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paDirections", "Pa Directions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countyGeoCode", "County Geo Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productRating", "Product Rating", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oiQuantity", "Oi Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("prodCatalogCategoryTypeId", "Prod Catalog Category Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("webSiteId", "Web Site Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("subscriptionTypeId", "Subscription Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("maxRecurrenceCount", "Max Recurrence Count", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastFailedAuthDate", "Last Failed Auth Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("purchaseThruDate", "Purchase Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("organizationPartyId", "Organization Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("viewAllowPermReqd", "View Allow Perm Reqd", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productManufacturerPartyId", "Product Manufacturer Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("transactionId", "Transaction Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("defaultCurrencyUomId", "Default Currency Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("levelId", "Level Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("correspondingPoId", "Corresponding Po Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("idByIpContactMechId", "Id By Ip Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paymentStatusId", "Payment Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("contentStatusId", "Content Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("itemDeclinedStatus", "Item Declined Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("actualCurrencyAmount", "Actual Currency Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldSquareFootage", "Old Square Footage", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requireAmount", "Require Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("returnAdjustmentTypeId", "Return Adjustment Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceFromDate", "Price From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("toPaymentId", "To Payment Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("facilityGroupId", "Facility Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("vendor", "Vendor", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("agreementItemSeqId", "Agreement Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oiCancelQuantity", "Oi Cancel Quantity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productPriceDetailText", "Product Price Detail Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maySplit", "May Split", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmPartyIdFrom", "Pm Party Id From", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("unitRecurringPrice", "Unit Recurring Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("localeString", "Locale String", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("facilitySize", "Facility Size", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("method", "Method", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paToName", "Pa To Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("childLeafCount", "Child Leaf Count", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productionHost", "Production Host", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productHeightUomId", "Product Height Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("linkTwoImageUrl", "Link Two Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userLoginId", "User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("locationSeqId", "Location Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("stateGeoTypeId", "State Geo Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastFailedNsfDate", "Last Failed Nsf Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originAddress1", "Origin Address 1", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("authErrorMessage", "Auth Error Message", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("connectTimeout", "Connect Timeout", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortToCurrentStatusId", "Work Effort To Current Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("originAddress2", "Origin Address 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("assocPriceThruDate", "Assoc Price Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("trackingCodeTypeId", "Tracking Code Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("taxAuthorityRateTypeId", "Tax Authority Rate Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmFinAccountTransId", "Pm Fin Account Trans Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("quantityRejected", "Quantity Rejected", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("useTime", "Use Time", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyFromFirstName", "Party From First Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oversizeCode", "Oversize Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drSurveyResponseId", "Dr Survey Response Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("acquirerURL", "Acquirer Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxAmount", "Tax Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceCustomPriceCalcService", "Price Custom Price Calc Service", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("rateTypeId", "Rate Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("merchantSubId", "Merchant Sub Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("posTerminalId", "Pos Terminal Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productCategoryId", "Product Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("authMode", "Auth Mode", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("homeDeliveryType", "Home Delivery Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("yearsWithContactMech", "Years With Contact Mech", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("prorateShipping", "Prorate Shipping", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("consecutiveFailedNsf", "Consecutive Failed Nsf", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fiscalYearStartMonth", "Fiscal Year Start Month", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productDefaultShipmentBoxTypeId", "Product Default Shipment Box Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("countryWellKnownText", "Country Well Known Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("introductionDate", "Introduction Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("keyName", "Key Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accountingQuantitySum", "Accounting Quantity Sum", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cpDeviceType", "Cp Device Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalPostedDebits", "Total Posted Debits", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accountingQuantityTotal", "Accounting Quantity Total", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("storeName", "Store Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("mimeTypeId", "Mime Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("uomIdTo", "Uom Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("requirePasswordChange", "Require Password Change", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("objectInfo", "Object Info", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("prodSearchExcludeVariants", "Prod Search Exclude Variants", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("xslfoAttachScreenLocation", "Xslfo Attach Screen Location", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("positionTitle", "Position Title", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("subtitle", "Subtitle", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("surveyApplTypeId", "Survey Appl Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastCurrencyUom", "Last Currency Uom", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("perfRatingTypeId", "Perf Rating Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("destContactNumber", "Dest Contact Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caLastModifiedByUserLogin", "Ca Last Modified By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("columnWidthPixels", "Column Width Pixels", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortIdTo", "Work Effort Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("countyGeoTypeId", "County Geo Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualEndingCheck", "Actual Ending Check", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("runningTimeMillis", "Running Time Millis", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentRefNum", "Payment Ref Num", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityBreakId", "Quantity Break Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("featurePercent", "Feature Percent", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("salesDiscWhenNotAvail", "Sales Disc When Not Avail", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("proxyPort", "Proxy Port", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderStatus", "Order Status", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("validFromDate", "Valid From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fromDate", "From Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shoppingListId", "Shopping List Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drMimeTypeId", "Dr Mime Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("geoSecCode", "Geo Sec Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("piecesIncluded", "Pieces Included", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("depthUomId", "Depth Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("exceptionRuleId", "Exception Rule Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("addToCartReplaceUpsell", "Add To Cart Replace Upsell", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("acctgTransEntryTypeId", "Acctg Trans Entry Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("editFormLocation", "Edit Form Location", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("subscriptionId", "Subscription Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("middleNameOnCard", "Middle Name On Card", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("initialUserAgent", "Initial User Agent", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accessAccountNbr", "Access Account Nbr", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pkCombinedValueText", "Pk Combined Value Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("benefitName", "Benefit Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("allowProductStoreChange", "Allow Product Store Change", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contactMechTypeId", "Contact Mech Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("entitySyncRemoveId", "Entity Sync Remove Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("allowPassword", "Allow Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("syncStatusId", "Sync Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("conditionExpression", "Condition Expression", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("sectionId", "Section Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("voucherRef", "Voucher Ref", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maintHistSeqId", "Maint Hist Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("positionId", "Position Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productWeight", "Product Weight", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("abbrev", "Abbrev", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderSeqCustMethId", "Order Seq Cust Meth Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("cardNumber", "Card Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceTermUomId", "Price Term Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("portletSequenceNum", "Portlet Sequence Num", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("codAmount", "Cod Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("allowSolicitation", "Allow Solicitation", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cogsMethodId", "Cogs Method Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("originContactMechId", "Origin Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("internalContentId", "Internal Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("labelPrinted", "Label Printed", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("activationValidThru", "Activation Valid Thru", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destAreaCode", "Dest Area Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalQuantityOrdered", "Total Quantity Ordered", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityIncluded", "Quantity Included", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("readTimeoutSeconds", "Read Timeout Seconds", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryGeoSecCode", "Country Geo Sec Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("binNumber", "Bin Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quoteId", "Quote Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("fixedAssetTypeId", "Fixed Asset Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oiShipAfterDate", "Oi Ship After Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("carrierRoleTypeId", "Carrier Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("totalMoneyAllowed", "Total Money Allowed", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyFromGroupName", "Party From Group Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultUomId", "Default Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oiShipBeforeDate", "Oi Ship Before Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("digProdUploadCategoryId", "Dig Prod Upload Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("sequenceId", "Sequence Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderPricePercent", "Order Price Percent", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortToSetup", "Work Effort To Setup", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("deductionTypeId", "Deduction Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("clientIpAddress", "Client Ip Address", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tnAskForName", "Tn Ask For Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("hostAddress", "Host Address", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("purchaseAllowPermReqd", "Purchase Allow Perm Reqd", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productProductWidth", "Product Product Width", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("unitCost", "Unit Cost", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("autoOrderCcTryLaterNsf", "Auto Order Cc Try Later Nsf", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultSequenceNum", "Default Sequence Num", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentMethodTypeDesc", "Payment Method Type Desc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drDataResourceTypeId", "Dr Data Resource Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("percentage", "Percentage", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("instId", "Inst Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("testingHost", "Testing Host", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("headerLogo", "Header Logo", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultShipmentBoxTypeId", "Default Shipment Box Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualSetupMillis", "Actual Setup Millis", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("companyPartyId", "Company Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productRequireAmount", "Product Require Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderFlatPrice", "Order Flat Price", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("forPushOnly", "For Push Only", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("inventoryComments", "Inventory Comments", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userPrefGroupTypeId", "User Pref Group Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("tnContactMechId", "Tn Contact Mech Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("proxyAddress", "Proxy Address", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productCreatedDate", "Product Created Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maxTimeMillis", "Max Time Millis", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("relayResponse", "Relay Response", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("clientUser", "Client User", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("agreementItemTypeId", "Agreement Item Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("postedBalance", "Posted Balance", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("responseType", "Response Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("labelImageFormat", "Label Image Format", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shippingPricePercent", "Shipping Price Percent", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productRequirementMethodEnumId", "Product Requirement Method Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productProductDepth", "Product Product Depth", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("logFile", "Log File", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("autoApproveInvoice", "Auto Approve Invoice", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("headAction", "Head Action", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderNumberPrefix", "Order Number Prefix", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("primaryStoreGroupId", "Primary Store Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnable", "Returnable", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("port", "Port", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("startDateTime", "Start Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("instanceOfContentId", "Instance Of Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("userAlias", "User Alias", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("serviceName", "Service Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caSequenceNum", "Ca Sequence Num", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coDecoratorContentId", "Co Decorator Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("portalPageId", "Portal Page Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isImmediatelyFulfilled", "Is Immediately Fulfilled", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originGeoId", "Origin Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("helpContentId", "Help Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("issuedQuantity", "Issued Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requireInventory", "Require Inventory", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("termUomId", "Term Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("conversionFactor", "Conversion Factor", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityOpen", "Quantity Open", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("issueNumber", "Issue Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("reserveInventory", "Reserve Inventory", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pwd", "Pwd", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("terminationTypeId", "Termination Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("portalPageName", "Portal Page Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("surveyResponseId", "Survey Response Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productShippingHeight", "Product Shipping Height", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productSalesDiscontinuationDate", "Product Sales Discontinuation Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("stateProvinceGeoId", "State Province Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productOriginalImageUrl", "Product Original Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("hostPort", "Host Port", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("overrideGlAccountId", "Override Gl Account Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("checkInventory", "Check Inventory", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contentPurposeTypeId", "Content Purpose Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("updatedByUserLoginId", "Updated By User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("accessShippingKey", "Access Shipping Key", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("glAccountClassId", "Gl Account Class Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("keysDir", "Keys Dir", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("amountApplied", "Amount Applied", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderItemAssocTypeId", "Order Item Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("protocolVersion", "Protocol Version", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("primaryFacilityGroupId", "Primary Facility Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drSurveyId", "Dr Survey Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("geoName", "Geo Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("codAllowCod", "Cod Allow Cod", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drCreatedByUserLogin", "Dr Created By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("minItemPrice", "Min Item Price", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productTypeId", "Product Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("fixedAssetStdCostTypeId", "Fixed Asset Std Cost Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("carrierFirstName", "Carrier First Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortName", "Work Effort Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("binStartDateTime", "Bin Start Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("surveyQuestionTypeId", "Survey Question Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("reqReturnInventoryReceive", "Req Return Inventory Receive", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyRelationshipTypeId", "Party Relationship Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("externalAuthId", "External Auth Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oldOrderSequenceEnumId", "Old Order Sequence Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("errorGlJournalId", "Error Gl Journal Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("childBranchCount", "Child Branch Count", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("langCharset", "Lang Charset", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("maxEstimateWeight", "Max Estimate Weight", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("manufacturerPartyId", "Manufacturer Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("lastTimeZone", "Last Time Zone", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("keyword", "Keyword", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("minimumOrderQuantity", "Minimum Order Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("smallImageUrl", "Small Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("invoiceItemMapKey", "Invoice Item Map Key", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("codFundsCode", "Cod Funds Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceDetailText", "Price Detail Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isPublic", "Is Public", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("securityServiceName", "Security Service Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productAmountUomTypeId", "Product Amount Uom Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("custRequestTypeId", "Cust Request Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("caContentIdTo", "Ca Content Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("quantityTotal", "Quantity Total", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("addtlShippingChargeDesc", "Addtl Shipping Charge Desc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("glFiscalTypeId", "Gl Fiscal Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("labelImageType", "Label Image Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("initialLocale", "Initial Locale", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("referrerUrl", "Referrer Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("rateEstimateTemplate", "Rate Estimate Template", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("serverHitBinId", "Server Hit Bin Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("privilegeEnumId", "Privilege Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("perfReviewItemTypeId", "Perf Review Item Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("merchantId", "Merchant Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualServiceCost", "Actual Service Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productIsVirtual", "Product Is Virtual", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxable", "Taxable", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("packageDateCreated", "Package Date Created", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("finAccountTypeId", "Fin Account Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("showAsEnumId", "Show As Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("securityGroupId", "Security Group Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("contentName", "Content Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("bySetPosList", "By Set Pos List", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accountingQuantityDiff", "Accounting Quantity Diff", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dataCategoryId", "Data Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paCountryGeoId", "Pa Country Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("leaveTypeId", "Leave Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("itemStatusId", "Item Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceCreatedByUserLogin", "Price Created By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("primaryProductCategoryId", "Primary Product Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("upsHighValueReport", "Ups High Value Report", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fiscalYearStartDay", "Fiscal Year Start Day", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("requireCustomerRole", "Require Customer Role", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortPurposeTypeId", "Work Effort Purpose Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmPaymentMethodId", "Pm Payment Method Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productDiameterUomId", "Product Diameter Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("supplierProductName", "Supplier Product Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coDataResourceId", "Co Data Resource Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productIsVariant", "Product Is Variant", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("sequenceNum", "Sequence Num", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("boxLength", "Box Length", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("permissionId", "Permission Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drDataTemplateTypeId", "Dr Data Template Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("internationalInvoice", "International Invoice", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("primaryReturnId", "Primary Return Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("communicationEventTypeId", "Communication Event Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("totalEndingBalance", "Total Ending Balance", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("roleTypeIdTo", "Role Type Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnItemMapKey", "Return Item Map Key", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("midRoleTypeId", "Mid Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productReservMaxPersons", "Product Reserv Max Persons", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultPassword", "Default Password", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("unitListPrice", "Unit List Price", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("mondayStartTime", "Monday Start Time", "java.sql.Time", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("picklistBinId", "Picklist Bin Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("debitCreditFlag", "Debit Credit Flag", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coInstanceOfContentId", "Co Instance Of Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("merchantKeyStoreFilename", "Merchant Key Store Filename", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("showCheckoutGiftOptions", "Show Checkout Gift Options", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caLeftCoordinate", "Ca Left Coordinate", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmPartyIdTo", "Pm Party Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("enableTransmit", "Enable Transmit", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultLocaleString", "Default Locale String", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productAutoCreateKeywords", "Product Auto Create Keywords", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxId", "Tax Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("clientIpCountryGeoId", "Client Ip Country Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("webappName", "Webapp Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userAgentId", "User Agent Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("goodIdentificationTypeId", "Good Identification Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("communicationEventPrpTypId", "Communication Event Prp Typ Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmRoleTypeIdTo", "Pm Role Type Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("frequency", "Frequency", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("weekStart", "Week Start", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userLdapDn", "User Ldap Dn", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productCategoryTypeId", "Product Category Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("contentAssocPredicateId", "Content Assoc Predicate Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("requirementStartDate", "Requirement Start Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workReqFulfTypeId", "Work Req Fulf Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmPaymentMethodTypeId", "Pm Payment Method Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("datetimePerformed", "Datetime Performed", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("engineClass", "Engine Class", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caThruDate", "Ca Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("langName", "Lang Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("estimatedArrivalWorkEffId", "Estimated Arrival Work Eff Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("sendNotificationEmail", "Send Notification Email", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("carrierGroupName", "Carrier Group Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("firstAttemptOrderId", "First Attempt Order Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("dateCreated", "Date Created", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coCreatedDate", "Co Created Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("saturdayCapacity", "Saturday Capacity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("certsPath", "Certs Path", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productLastModifiedDate", "Product Last Modified Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("screenName", "Screen Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dataResourceId", "Data Resource Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceCreatedDate", "Price Created Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("standardLanguageId", "Standard Language Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("reconcileStatusId", "Reconcile Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("verified", "Verified", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastLocale", "Last Locale", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("carrierLastName", "Carrier Last Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originContactNumber", "Origin Contact Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentBoxTypeId", "Shipment Box Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("thirdPartyPostalCode", "Third Party Postal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("labelIntlSignImage", "Label Intl Sign Image", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paAddress2", "Pa Address 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productAssocTypeId", "Product Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("paAddress1", "Pa Address 1", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("offlineSyncSplitMillis", "Offline Sync Split Millis", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("varianceReasonId", "Variance Reason Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("partyIdTo", "Party Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualEndingCash", "Actual Ending Cash", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("serverURL", "Server Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmActualCurrencyAmount", "Pm Actual Currency Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("lastPrice", "Last Price", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("uomId", "Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("useRoleTypeId", "Use Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("transactionDate", "Transaction Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fixedAmount", "Fixed Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("hideCurrency", "Hide Currency", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("langId", "Lang Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("activationNumber", "Activation Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("connectionTimeoutSeconds", "Connection Timeout Seconds", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentReceiptId", "Shipment Receipt Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualArrivalDate", "Actual Arrival Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cancelReturnUrl", "Cancel Return Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fromQuantity", "From Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oiEstimatedDeliveryDate", "Oi Estimated Delivery Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("visitId", "Visit Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("maxWeight", "Max Weight", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceProductPricePurposeId", "Price Product Price Purpose Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("primaryKeyRemoved", "Primary Key Removed", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("trackingCode", "Tracking Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("billingPostalCode", "Billing Postal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productDepth", "Product Depth", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("processAttempt", "Process Attempt", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("totalTimeMillis", "Total Time Millis", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("percentComplete", "Percent Complete", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldPrice", "Old Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("giftMessage", "Gift Message", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderContentTypeId", "Order Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("enableDav", "Enable Dav", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originFacilityName", "Origin Facility Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productInShippingBox", "Product In Shipping Box", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("averageCustomerRating", "Average Customer Rating", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderDecimalQuantity", "Order Decimal Quantity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("seqId", "Seq Id", "java.lang.Long", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("acquirerTimeout", "Acquirer Timeout", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("extension", "Extension", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destPostalCode", "Dest Postal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("stateAbbreviation", "State Abbreviation", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productReservNthPPPerc", "Product Reserv Nth Pp Perc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("availableThruDate", "Available Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("attrName", "Attr Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("minTimeMillis", "Min Time Millis", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enumId", "Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("resourceValue", "Resource Value", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contentType", "Content Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("roleTypeIdValidTo", "Role Type Id Valid To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("directions", "Directions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("segmentGroupTypeId", "Segment Group Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isAssetClass", "Is Asset Class", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("invoiceRoleTypeId", "Invoice Role Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("weightUnitPrice", "Weight Unit Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("relevancyWeight", "Relevancy Weight", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipByDate", "Ship By Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("balanceResOnOrderCreation", "Balance Res On Order Creation", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("carrierServiceCode", "Carrier Service Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortGoodStdTypeId", "Work Effort Good Std Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("fridayStartTime", "Friday Start Time", "java.sql.Time", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("pmComments", "Pm Comments", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drObjectInfo", "Dr Object Info", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("mapKey", "Map Key", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("allowCompanyAddr", "Allow Company Addr", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countryAbbreviation", "Country Abbreviation", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("glAccountTypeId", "Gl Account Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("autoOrderCcTryOtherCards", "Auto Order Cc Try Other Cards", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fileExtensionId", "File Extension Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productProductTypeId", "Product Product Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("originAreaCode", "Origin Area Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("invoiceTypeDesc", "Invoice Type Desc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("emailMerchant", "Email Merchant", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quantityOnHandSum", "Quantity On Hand Sum", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accommodationSpotId", "Accommodation Spot Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("confItemContentTypeId", "Conf Item Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortAssocTypeId", "Work Effort Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productDetailImageUrl", "Product Detail Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("allowUspsAddr", "Allow Usps Addr", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("defaultAmount", "Default Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("permissionsEnumId", "Permissions Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("thursdayStartTime", "Thursday Start Time", "java.sql.Time", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("availableToPromiseDiff", "Available To Promise Diff", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("geoCode", "Geo Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dontCancelSetDate", "Dont Cancel Set Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("quoteSeqCustMethId", "Quote Seq Cust Meth Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("dimensionUomId", "Dimension Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("autoOrderShoppingListId", "Auto Order Shopping List Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shipGroupSeqId", "Ship Group Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("originCity", "Origin City", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isPosted", "Is Posted", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("thruQuantity", "Thru Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("string2", "String 2", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destToName", "Dest To Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("string1", "String 1", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coPrivilegeEnumId", "Co Privilege Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("xName", "X Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drStatusId", "Dr Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pmOverrideGlAccountId", "Pm Override Gl Account Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("runtimeDataId", "Runtime Data Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("sundayStartTime", "Sunday Start Time", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("consecutiveFailedAuths", "Consecutive Failed Auths", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentMethodId", "Payment Method Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("syncSplitMillis", "Sync Split Millis", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("openedByUserLoginId", "Opened By User Login Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shoppingListItemSeqId", "Shopping List Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualEndingGc", "Actual Ending Gc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentContactMechTypeId", "Shipment Contact Mech Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("cookie", "Cookie", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("statusIdTo", "Status Id To", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productBillOfMaterialLevel", "Product Bill Of Material Level", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("headVersion", "Head Version", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accommodationMapId", "Accommodation Map Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("chargeShipping", "Charge Shipping", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("includeInPromotions", "Include In Promotions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("keysFile", "Keys File", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("detailScreen", "Detail Screen", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("inventoryItemTypeId", "Inventory Item Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shippingDepth", "Shipping Depth", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destFacilityName", "Dest Facility Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("clientIpStateProvGeoId", "Client Ip State Prov Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("totalAmount", "Total Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paPostalCodeGeoId", "Pa Postal Code Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("fromInventoryItemId", "From Inventory Item Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("glJournalId", "Gl Journal Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productIncludeInPromotions", "Product Include In Promotions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("homeDeliveryDate", "Home Delivery Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("oldHeaderMiddleBackground", "Old Header Middle Background", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxAuthPartyId", "Tax Auth Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("priceTaxPercentage", "Price Tax Percentage", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("standardContentPrefix", "Standard Content Prefix", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isVariant", "Is Variant", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productSalesDiscWhenNotAvail", "Product Sales Disc When Not Avail", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("periodNum", "Period Num", "java.lang.Long", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("serialNumber", "Serial Number", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("relationshipName", "Relationship Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("hasLoggedOut", "Has Logged Out", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productConfigId", "Product Config Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("amount", "Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("serverHostName", "Server Host Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("disableBillAvs", "Disable Bill Avs", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("destTelecomNumberId", "Dest Telecom Number Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productStoreGroupTypeId", "Product Store Group Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("posTerminalLogId", "Pos Terminal Log Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("pricePrice", "Price Price", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("postedCredits", "Posted Credits", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fridayCapacity", "Friday Capacity", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tempExprId", "Temp Expr Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("timeTransparency", "Time Transparency", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("clientId", "Client Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("styleSheet", "Style Sheet", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxAuthGeoId", "Tax Auth Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderItemSeqId", "Order Item Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("returnTypeId", "Return Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("portletCategoryId", "Portlet Category Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("mrpEventTypeId", "Mrp Event Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("quantityUnitPrice", "Quantity Unit Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("seqName", "Seq Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("supplierProductId", "Supplier Product Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("purposeThruDate", "Purpose Thru Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("manualAuthCode", "Manual Auth Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("modifyAmount", "Modify Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("webSiteContentTypeId", "Web Site Content Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shipAfterDate", "Ship After Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("merchantReturnURL", "Merchant Return Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("employmentAppSourceTypeId", "Employment App Source Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isVirtual", "Is Virtual", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("labelImage", "Label Image", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("relatedDetailId", "Related Detail Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("packageQuantity", "Package Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("changedByUserLogin", "Changed By User Login", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("coLocaleString", "Co Locale String", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("visualThemeId", "Visual Theme Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("memberComments", "Member Comments", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("workEffortToEstimatedCompletionDate", "Work Effort To Estimated Completion Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("saveCertInfo", "Save Cert Info", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("templateSubscription", "Template Subscription", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("setOwnerUponIssuance", "Set Owner Upon Issuance", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentCustomMethodId", "Shipment Custom Method Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productDescription", "Product Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("test", "Test", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("targetServiceName", "Target Service Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("agreementTypeId", "Agreement Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productProductWeight", "Product Product Weight", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("personComments", "Person Comments", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("city", "City", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("templateDataResourceId", "Template Data Resource Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("secureContentPrefix", "Secure Content Prefix", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("customPriceCalcService", "Custom Price Calc Service", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("recurrenceDateTimes", "Recurrence Date Times", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("dataSourceTypeId", "Data Source Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("keepRemoveInfoHours", "Keep Remove Info Hours", "java.lang.Double", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("priceUnitPrice", "Price Unit Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shippingWidth", "Shipping Width", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("packedQuantity", "Packed Quantity", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productMaintTypeId", "Product Maint Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("estimatedShipWorkEffId", "Estimated Ship Work Eff Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("actualCost", "Actual Cost", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("primaryOrderId", "Primary Order Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortId", "Work Effort Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("initialReferrer", "Initial Referrer", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productFixedAmount", "Product Fixed Amount", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("softIdentifier", "Soft Identifier", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("currentRecurrenceCount", "Current Recurrence Count", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("forPullOnly", "For Pull Only", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shipmentMethodTypeId", "Shipment Method Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("minTotal", "Min Total", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("supplierPartyId", "Supplier Party Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("visualThemeSetId", "Visual Theme Set Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("rateCode", "Rate Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originalImageUrl", "Original Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("ignoreAvs", "Ignore Avs", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isViewed", "Is Viewed", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("businessEmail", "Business Email", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderPaymentPreferenceId", "Order Payment Preference Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productInventoryMessage", "Product Inventory Message", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productReserv2ndPPPerc", "Product Reserv 2Nd Pp Perc", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("newValueText", "New Value Text", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("storeCreditAccountEnumId", "Store Credit Account Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("orderQtyIncrements", "Order Qty Increments", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userPrefValue", "User Pref Value", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("idCode", "Id Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("geoAssocTypeId", "Geo Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("diameterUomId", "Diameter Uom Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("confirmUrl", "Confirm Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("userPrefTypeId", "User Pref Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("isItemGroupPrimary", "Is Item Group Primary", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tnAreaCode", "Tn Area Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enableCvn", "Enable Cvn", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("httpPort", "Http Port", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("caDataSourceId", "Ca Data Source Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productTaxable", "Product Taxable", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("accountCode", "Account Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("drLocaleString", "Dr Locale String", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("originStateProvinceGeoId", "Origin State Province Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("reservMaxPersons", "Reserv Max Persons", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("releaseTransType", "Release Trans Type", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("mediumImageUrl", "Medium Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isClosed", "Is Closed", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("orderDate", "Order Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paAttnName", "Pa Attn Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("benefitTypeId", "Benefit Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("minPurchase", "Min Purchase", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("transTypeDescription", "Trans Type Description", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("taxAuthorityRateSeqId", "Tax Authority Rate Seq Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("productFeatureId", "Product Feature Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("workEffortParentId", "Work Effort Parent Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("screenLocation", "Screen Location", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("tickerSymbol", "Ticker Symbol", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("countyGeoId", "County Geo Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("drRelatedDetailId", "Dr Related Detail Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("merchantDescr", "Merchant Descr", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("enableHttps", "Enable Https", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("partyClassificationTypeId", "Party Classification Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("httpHost", "Http Host", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("clientIpPostalCode", "Client Ip Postal Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("contactListTypeId", "Contact List Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("toName", "To Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("paymentMethodTypeId", "Payment Method Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("splitPayPrefPerShpGrp", "Split Pay Pref Per Shp Grp", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("budgetTypeId", "Budget Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("supportDiscontinuationDate", "Support Discontinuation Date", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("templatePathPrefix", "Template Path Prefix", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("shippingPrice", "Shipping Price", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("decoratorContentId", "Decorator Content Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("replenishEnumId", "Replenish Enum Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shipmentGatewayConfTypeId", "Shipment Gateway Conf Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("notifyUrl", "Notify Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("fixContact", "Fix Contact", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productComments", "Product Comments", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("parentClassId", "Parent Class Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("emplLeaveReasonTypeId", "Empl Leave Reason Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("linkOneImageUrl", "Link One Image Url", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("previousJobId", "Previous Job Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("oneInventoryFacility", "One Inventory Facility", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("productFeatureApplTypeId", "Product Feature Appl Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("shippingInstructions", "Shipping Instructions", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("runStatusId", "Run Status Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("userCreated", "User Created", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("useCase", "Use Case", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("codSurchargeApplyToPackage", "Cod Surcharge Apply To Package", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("packagingTypeCode", "Packaging Type Code", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("surveyId", "Survey Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        new ColumnDetails("carrierDeliveryZone", "Carrier Delivery Zone", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("cancelQuantity", "Cancel Quantity", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("ruleName", "Rule Name", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
        
                new ColumnDetails("reasonComment", "Reason Comment", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
                        new ColumnDetails("reasonComment", "Reason Comment", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere),
                                new ColumnDetails("paidAmount", "Paid Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        new ColumnDetails("isSale", "Is Sale", "java.lang.String", new Integer(COL_SIZE), Default_Cell_Rendere), /*        new ColumnDetails("facilityId", "Id", "java.lang.String"),
        
     new ColumnDetails("facilityName", "Facility Name", "java.lang.String"),
     new ColumnDetails("ownerPartyId", "Owner Party Id", "java.lang.String"),
     new ColumnDetails("facilityTypeId", "Type Id", "java.lang.String"),
     new ColumnDetails("parentFacilityId", "Parent Facility Id", "java.lang.String"),
     new ColumnDetails("defaultInventoryItemTypeId", "Default Inventory Item TypeId", "java.lang.String"),
     //        new ColumnDetails("primaryFacilityGroupId", "Primary Facility GroupId", "java.lang.String"),
     //        new ColumnDetails("facilitySize", "FacilitySize", "java.lang.Long"),
     //        new ColumnDetails("facilitySizeUomId", "FacilitySizeUomId", "java.lang.String"),
     new ColumnDetails("productStoreId", "productStoreId", "java.lang.String"),
     new ColumnDetails("defaultDaysToShip", "Default Days To Ship", "java.lang.Long"),
     new ColumnDetails("openedDate", "openedDate", "java.sql.Timestamp"),
     new ColumnDetails("closedDate", "closedDate", "java.sql.Timestamp"),
     new ColumnDetails("description", "description", "java.lang.String"),
     //        new ColumnDetails("defaultDimensionUomId", "defaultDimensionUomId", "java.lang.String"),
     //        new ColumnDetails("defaultWeightUomId", "defaultWeightUomId", "java.lang.String"),
     //        new ColumnDetails("geoPointId", "geoPointId", "java.lang.String"),
     new ColumnDetails("lastUpdatedStamp", "lastUpdatedStamp", "java.sql.Timestamp"),
     //        new ColumnDetails("lastUpdatedTxStamp", "lastUpdatedTxStamp", "java.sql.Timestamp"),
     new ColumnDetails("createdStamp", "createdStamp", "java.sql.Timestamp"),
     new ColumnDetails("createdTxStamp", "createdTxStamp", "java.sql.Timestamp"),};
     */};

    static public class ColumnDetails {

        protected String Id;
        protected String Name;
        protected String ClassName;
        protected Integer ColumnWidth;
        protected String RendererName;

        ColumnDetails(String Id, String Name, String ClassName, Integer width, String rendererName) {
            this.Id = Id;
            this.Name = Name;
            this.ClassName = ClassName;
            this.ColumnWidth = width;
            this.RendererName = rendererName;
        }

        static public ColumnDetails getColumnDetails(final String id) throws Exception {
            for (int i = 0; i < OrderMaxViewEntity.GenericColumnName.length; ++i) {
                if (OrderMaxViewEntity.GenericColumnName[i].Id.equals(id)) {
                    return OrderMaxViewEntity.GenericColumnName[i];
                }
            }
            new ColumnDetails(id, id, "java.lang.String", new Integer(OrderMaxViewEntity.STATUSID_COL_SIZE), OrderMaxViewEntity.Default_Cell_Rendere).outputToDebug();
            throw new Exception("Row Index not found: " + id);

        }

        void outputToDebug() {
            String str = "new ColumnDetails(" + "\"" + Id + "\"," + "\"" + Name + "\",";
            str = str.concat("\"" + ClassName + "\"," + "\"new Integer(STATUSID_COL_SIZE)\"," + "\"Default_Cell_Rendere\"),");
            Debug.logInfo(str, "ColumnDetails");
        }

        static void outputFieldMap(GenericValue genVal) {
            Map<String, Object> fieldsMap = genVal.getAllFields();
            for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
                String str = "new ColumnDetails(" + "\"" + entryDept.getKey() + "\"," + "\"" + entryDept.getKey() + "\",";
                str = str.concat("\"" + entryDept.getValue().getClass() + "\"," + "\"new Integer(STATUSID_COL_SIZE)\"," + "\"Default_Cell_Rendere\"),");
                Debug.logInfo(str, "ColumnDetails");
            }
        }
    }
    static public String[][] InvoiceIdColumnName = {
        {"invoiceId", "invoiceId", "java.lang.String"},
        {"invoiceTypeId", "invoiceTypeId", "java.lang.String"},
        {"partyIdFrom", "partyIdFrom", "java.lang.String"},
        {"partyId", "partyId", "java.lang.String"},
        {"roleTypeId", "roleTypeId", "java.lang.String"},
        {"statusId", "statusId", "java.lang.String"},
        {"billingAccountId", "billingAccountId", "java.lang.String"},
        {"contactMechId", "contactMechId", "java.lang.String"},
        {"invoiceDate", "invoiceDate", "java.sql.Timestamp"},
        {"dueDate", "dueDate", "java.sql.Timestamp"},
        {"paidDate", "paidDate", "java.sql.Timestamp"},
        {"invoiceMessage", "invoiceMessage", "java.lang.String"},
        {"referenceNumber", "referenceNumber", "java.lang.String"},
        {"description", "description", "java.lang.String"},
        {"currencyUomId", "currencyUomId", "java.lang.String"},
        {"recurrenceInfoId", "recurrenceInfoId", "java.lang.String"},
        {"lastUpdatedStamp", "lastUpdatedStamp", "java.sql.Timestamp"},
        {"lastUpdatedTxStamp", "lastUpdatedTxStamp", "java.sql.Timestamp"},
        {"createdStamp", "createdStamp", "java.sql.Timestamp"},
        {"createdTxStamp", "createdTxStamp", "java.sql.Timestamp"},};
    static public String[][] PartyIdColumnName = {
        {"partyId", "partyId"},
        {"description", "descri}tion"},
        {"statusId", "statusId"},
        {"createdDate", "createdDate"},
        {"partyTypeId", "partyTypeId"},
        //        {"infoString", "Email"},
        //        {"contactNumber", "contactNumber"},
        {"externalId", "externalId"},
        {"preferredCurrencyUomId", "preferredCurrencyUomId"},
        {"createdByUserLogin", "createdByUserLogin"},
        {"lastModifiedDate", "lastModifiedDate"},
        {"lastModifiedByUserLogin", "lastModifiedByUserLogin"},
        {"dataSourceId", "dataSourceId"},
        {"isUnread", "isUnread"},
        {"lastUpdatedStamp", "lastUpdatedStamp"},
        {"lastUpdatedTxStamp", "lastUpdatedTxStamp"},
        {"createdStamp", "createdStamp"},
        {"createdTxStamp", "createdTxStamp"},};
    static public String[][] OrderQueryIdColumnName = {
        {"orderId", "orderId", "java.lang.String"},
        {"partyId", "partyId", "java.lang.String"},
        {"orderTypeId", "orderTypeId", "java.lang.String"},
        {"productId", "productId", "java.lang.String"},
        {"itemDescription", "itemDescription", "java.lang.String"},
        {"quantity", "quantity", "java.math.BigDecimal"},
        {"unitPrice", "unitPrice", "java.math.BigDecimal"},
        {"roleTypeId", "roleTypeId", "java.lang.String"},
        {"orderName", "orderName", "java.lang.String"},
        {"statusId", "statusId", "java.lang.String"},
        {"grandTotal", "grandTotal", "java.math.BigDecimal"},
        {"remainingSubTotal", "remainingSubTotal", "java.math.BigDecimal"},
        {"createdStamp", "createdStamp", "java.sql.Timestamp"},};
    static String module = OrderMaxViewEntity.class.getName();

    static public List<GenericValue> getOrderDetails(String partyId, String roleTypeId,
            String productId, String orderTypeId, String statusId, Delegator delegator) {

        List<GenericValue> partyList = null;
        List<GenericValue> resultList = null;

        // create the dynamic view entity
        DynamicViewEntity dynamicView = new DynamicViewEntity();

        /*SELECT O_R.ORDER_ID, O_R.PARTY_ID, O_H.ORDER_NAME, O_H.ORDER_DATE, O_H.STATUS_ID, O_I.PRODUCT_ID, O_I.QUANTITY, O_I.UNIT_PRICE, O_I.ITEM_DESCRIPTION, O_I.STATUS_ID 
         FROM ORDER_ROLE AS O_R 
         INNER JOIN ORDER_HEADER AS O_H ON(O_R.ORDER_ID = O_H.ORDER_ID)
         INNER JOIN ORDER_ITEM AS O_I ON(O_R.ORDER_ID = O_I.ORDER_ID)
         WHERE PARTY_ID = 'ALPHA' 
         AND ROLE_TYPE_ID = 'SUPPLIER_AGENT'
         AND ORDER_TYPE_ID = 'PURCHASE_ORDER'
         AND O_I.STATUS_ID = 'ITEM_COMPLETED'
         AND PRODUCT_ID = 'C71200'
         */
        // Person (name + card)
        dynamicView.addMemberEntity("O_R", "OrderRole");
        dynamicView.addAlias("O_R", "orderId");
        dynamicView.addAlias("O_R", "partyId");
        dynamicView.addAlias("O_R", "roleTypeId");

        dynamicView.addMemberEntity("O_H", "OrderHeader");
        dynamicView.addAlias("O_H", "orderName");
        dynamicView.addAlias("O_H", "statusId");
        dynamicView.addAlias("O_H", "orderTypeId");
        dynamicView.addAlias("O_H", "grandTotal");
        dynamicView.addAlias("O_H", "remainingSubTotal");
        dynamicView.addViewLink("O_R", "O_H", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));

        // Create the order Item
        dynamicView.addMemberEntity("O_I", "OrderItem");
        dynamicView.addAlias("O_I", "productId");
        dynamicView.addAlias("O_I", "quantity");
        dynamicView.addAlias("O_I", "unitPrice");
        dynamicView.addAlias("O_I", "itemDescription");
        dynamicView.addAlias("O_I", "statusId");
        dynamicView.addAlias("O_I", "createdStamp");
        dynamicView.addViewLink("O_H", "O_I", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));

        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;

        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();

        // fields we need to select; will be used to set distinct
        fieldsToSelect.add("orderId");
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("roleTypeId");
        fieldsToSelect.add("orderName");
        fieldsToSelect.add("statusId");
        fieldsToSelect.add("orderTypeId");
        fieldsToSelect.add("grandTotal");
        fieldsToSelect.add("remainingSubTotal");
        fieldsToSelect.add("productId");
        fieldsToSelect.add("quantity");
        fieldsToSelect.add("unitPrice");
        fieldsToSelect.add("itemDescription");
        fieldsToSelect.add("statusId");
        fieldsToSelect.add("createdStamp");

        if (partyId != null) {
            andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId)));
        }

        if (roleTypeId != null) {
            andExprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, roleTypeId)); // Only persons for now...
        }

        if (productId != null) {
            andExprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId)); // Only persons for now...
        }

        if (orderTypeId != null) {
            andExprs.add(EntityCondition.makeCondition("orderTypeId", EntityOperator.EQUALS, orderTypeId)); // Only persons for now...
        }

        if (orderTypeId != null) {
            andExprs.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, statusId)); // Only persons for now...
        }

        // NOTE: _must_ explicitly allow null as it is not included in a not equal in many databases... odd but true
        // This allows to get all clients when any informations has been entered
//        andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId)));
        //       andExprs.add(EntityCondition.makeCondition("goodIdentificationTypeId", EntityOperator.EQUALS, "EAN")); // Only persons for now...
//       andExprs.add(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, "DEFAULT_PRICE")); // Only persons for now...
//        andExprs.add(EntityCondition.makeCondition("productPricePurposeId", EntityOperator.EQUALS, "PURCHASE")); // Only persons for now...
//        andExprs.add(EntityCondition.makeCondition("goodIdentificationTypeId", EntityOperator.EQUALS, "EAN")); // Only persons for now...
        mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
        orderBy.add("createdStamp");

        Debug.logInfo("dynamicView=" + dynamicView.toString(), module);
        Debug.logInfo("dynamicView1=" + dynamicView.getEntityName(), module);
        Debug.logInfo("In searchClientProfile mainCond=" + mainCond, module);

        Integer maxRows = 100000;
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            try {
                try {
                    String str = dynamicView.getViewXml("PartyRole");
                    Debug.logInfo("getViewXml=" + str, module);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // set distinct on so we only get one row per person
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
                // using list iterator
                EntityListIterator pli = delegator.findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);

                // get the partial list for this page
                partyList = pli.getPartialList(1, maxRows);

                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        if (partyList != null) {
            resultList = FastList.newInstance();
            for (GenericValue party : partyList) {

                resultList.add(party);
            }
            Debug.logInfo("supplierProductId list is empty", module);
        } else {
            resultList = FastList.newInstance();
            Debug.logInfo("supplierProductId list is null", module);
        }

        return resultList;
    }

    static public void outputFieldMap(GenericEntity genVal, String entityName, String fileNameDir) {

        List<String> modifiedProductList = FastList.newInstance();

        modifiedProductList.add("package org.ofbiz.ordermax.entity;" + "\n");

        modifiedProductList.add("import org.ofbiz.base.util.Debug;" + "\n");
        modifiedProductList.add("import org.ofbiz.base.util.UtilDateTime;" + "\n");
        modifiedProductList.add("import org.ofbiz.entity.Delegator;" + "\n");

        modifiedProductList.add("import org.ofbiz.entity.GenericValue;" + "\n");
        modifiedProductList.add("import org.ofbiz.ordermax.generic.GenericValueObjectInterface;" + "\n");
        modifiedProductList.add("import org.ofbiz.ordermax.generic.OrderMaxViewEntity.ColumnDetails;" + "\n");
        modifiedProductList.add("import org.ofbiz.ordermax.utility.OrderMaxUtility;" + "\n");
        modifiedProductList.add("import java.util.Collection;" + "\n");
        modifiedProductList.add("import java.util.List;" + "\n");
        modifiedProductList.add("import java.util.ArrayList;" + "\n");
        modifiedProductList.add("import java.util.HashMap;" + "\n");
        modifiedProductList.add("import java.util.Map;" + "\n");

        modifiedProductList.add("public class " + entityName + " implements GenericValueObjectInterface {" + "\n");
//        modifiedProductList.add("public static final String module = Client.class.getName();" + "\n");
        modifiedProductList.add("public static final String module =" + entityName + ".class.getName();" + "\n");

        modifiedProductList.add("public static int COL_SIZE = 80;" + "\n");
        modifiedProductList.add("final static String Default_Cell_Rendere = \"DEFAULT\";" + "\n");
        modifiedProductList.add("final static String GenericValue_Cell_Renderer = \"GENERIC_VALUE\";" + "\n");

        modifiedProductList.add("private GenericValue genVal = null;" + "\n");
        modifiedProductList.add("public " + entityName + "(GenericValue val){" + "\n");
        modifiedProductList.add("genVal =  val;" + "\n");
        modifiedProductList.add("try {" + "\n");
        modifiedProductList.add("setGenericValue();" + "\n");
        modifiedProductList.add("} catch (Exception ex) {" + "\n");
        modifiedProductList.add("//Debug.logInfo(ex, module);" + "\n");
        modifiedProductList.add("}" + "\n");
        modifiedProductList.add("}" + "\n");

        modifiedProductList.add("public " + entityName + " () {" + "\n");
        modifiedProductList.add("initObject();" + "\n");
        modifiedProductList.add("}" + "\n");

        modifiedProductList.add(" static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{" + "\n");
        Map<String, Object> fieldsMap = genVal.getAllFields();

        HashMap<String, Object> fieldsMapX1 = new HashMap<String, Object>();
        HashMap<String, Object> fieldsMapX2 = new HashMap<String, Object>();
        HashMap<String, Object> fieldsMapX3 = new HashMap<String, Object>();
        HashMap<String, String> dataTypeFieldName = new HashMap<String, String>();
        HashMap<String, String> dataTypeEntityFieldName = new HashMap<String, String>();

        Map<String, Object> fieldsMap1 = genVal.getAllFields();
        for (Map.Entry<String, Object> entryDept : fieldsMap1.entrySet()) {

            String str = splitCamelCase(entryDept.getKey());

            if (str.contains("Id")) {
                fieldsMapX1.put(entryDept.getKey(), entryDept.getValue());

            } else if (str.contains("Stamp")) {
                fieldsMapX2.put(entryDept.getKey(), entryDept.getValue());
            } else {
                fieldsMapX3.put(entryDept.getKey(), entryDept.getValue());
            }
        }

        //class types
        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            Debug.logInfo( " entryDept.getKey(): " + entryDept.getKey() +  " entryDept.getValue(): " + entryDept.getValue() , module);
            String strVal;
            if (entryDept.getValue() != null) {

                strVal = entryDept.getValue().getClass().toString().replace("class ", "");
            Debug.logInfo( " entryDept.getKey(): " + entryDept.getKey() +  " entryDept.getValue(): " + entryDept.getValue().getClass().toString() , module);                
                entryDept.setValue(strVal);
            } else {
                strVal = "java.lang.String";
                if (splitCamelCase(entryDept.getKey()).contains("Date")) {
                    strVal = "java.sql.Timestamp";
                } else {
                    strVal = "java.lang.String";
                }
                entryDept.setValue(strVal);
            }
        }

        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {

            if (entryDept.getValue().toString().trim().contains("String")) {
                dataTypeFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidString(");
                dataTypeEntityFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidEntityString(");

            } else if (entryDept.getValue().toString().trim().contains("BigDecimal")) {
                dataTypeFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidBigDecimal(");
                dataTypeEntityFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidEntityBigDecimal(");
            } else if (entryDept.getValue().toString().trim().contains("Timestamp")) {
                dataTypeFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidTimestamp(");
                dataTypeEntityFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidEntityTimestamp(");

            } else if (entryDept.getValue().toString().trim().contains("Long")) {
                dataTypeFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidLong(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityLong(");

            } else if (entryDept.getValue().toString().trim().contains("Double")) {
                dataTypeFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidDouble(");
                dataTypeEntityFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidEntityDouble(");
            } else if (entryDept.getValue().toString().trim().contains("Date")) {
                dataTypeFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidDate(");
                dataTypeEntityFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidEntityDate(");
            } else if (entryDept.getValue().toString().trim().contains("Time")) {
                dataTypeFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidTime(");
                dataTypeEntityFieldName.put(entryDept.getKey(), "OrderMaxUtility.getValidEntityTime(");
            }
        }

        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            boolean exists = false;
            try {
                OrderMaxViewEntity.ColumnDetails columnDetails = OrderMaxViewEntity.ColumnDetails.getColumnDetails(entryDept.getKey());
                if (columnDetails != null) {
                    exists = true;
                }
            } catch (Exception ex) {
//                Logger.getLogger(OrderMaxViewEntity.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (exists == false) {
                if (columnIdClass.containsKey(entryDept.getKey()) == false) {
                    String str = "new ColumnDetails(" + "\"" + entryDept.getKey() + "\"," + "\"" + capitalizeString(splitCamelCase(entryDept.getKey())) + "\",";
                    str = str.concat("\"" + entryDept.getValue() + "\"," + "new Integer(COL_SIZE),");
                    if (fieldsMapX1.containsKey(entryDept.getKey())) {
                        str = str.concat("GenericValue_Cell_Renderer),");
                    } else {
                        str = str.concat("Default_Cell_Rendere),");
                    }
                    columnIdClass.put(entryDept.getKey(), str);
//                modifiedProductList.add(str + "\n");
                }
            }
        }

        modifiedProductList.add("};" + "\n");

        modifiedProductList.add(" static public String[][] ColumnNameId = {" + "\n");

        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            String str = "{" + "\"" + entryDept.getKey() + "\"," + "\"" + capitalizeString(splitCamelCase(entryDept.getKey())) + "\"";
            str = str.concat("},");
            modifiedProductList.add(str + "\n");
            ////Debug.logInfo(str, "");
        }
        modifiedProductList.add("};" + "\n");

        //        new ColumnDetails("caContentAssocTypeId", "Ca Content Assoc Type Id", "java.lang.String", new Integer(COL_SIZE), GenericValue_Cell_Renderer),
        //new ColumnDetails("hitStartDateTime", "Hit Start Date Time", "java.sql.Timestamp", new Integer(COL_SIZE), Default_Cell_Rendere),
        //new ColumnDetails("maxAmount", "Max Amount", "java.math.BigDecimal", new Integer(COL_SIZE), Default_Cell_Rendere),
        //init object
        modifiedProductList.add("protected void initObject(){" + "\n");

        // build get method from genericValue
        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            String str = "this." + entryDept.getKey() + " = ";

            if (entryDept.getValue().toString().trim().equals("java.sql.Timestamp")) {
                str = str.concat("UtilDateTime.nowTimestamp();");
            } else if (entryDept.getValue().toString().trim().equals("java.math.BigDecimal")) {
                str = str.concat("java.math.BigDecimal.ZERO;");
            } else if (entryDept.getValue().toString().trim().equals("java.lang.Long")) {
                str = str.concat("new Long(0);");
            } else if (entryDept.getValue().toString().trim().equals("java.lang.Double")) {
                str = str.concat("new Double(0);");
            } else if (entryDept.getValue().toString().trim().equals("java.sql.Date")) {
                str = str.concat(" new java.sql.Date(System.currentTimeMillis());");
            } else if (entryDept.getValue().toString().trim().equals("java.sql.Time")) {
                str = str.concat(" new java.sql.Time(System.currentTimeMillis());");
            } else {
                str = str.concat("\"\";");
            }

            modifiedProductList.add(str + "\n");
//            //Debug.logInfo(str, "");
        }
        modifiedProductList.add("}" + "\n");

        //set method
        modifiedProductList.add("public void setGenericValue() throws Exception {" + "\n");

        modifiedProductList.add("if(genVal==null){" + "\n");
        modifiedProductList.add("throw new Exception(\"Generic Value object is not set\");" + "\n");
        modifiedProductList.add("}" + "\n");

        // build get method from genericValue
        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            String str = "this." + entryDept.getKey() + " = (" + entryDept.getValue() + ") genVal.get(\"" + entryDept.getKey() + "\");";
            modifiedProductList.add(str + "\n");
//            //Debug.logInfo(str, "");
        }
        modifiedProductList.add("}" + "\n");

        modifiedProductList.add("protected void getGenericValue(GenericValue val) {" + "\n");
        // build set method from genericValue

        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {

            String str;

            if (dataTypeEntityFieldName.containsKey(entryDept.getKey())) {
//                addToClassList(entryDept.getKey() + ".setText( " + dataTypeEntityFieldName.get(entryDept.getKey()) + localEntityClass + ".get" + entryDept.getValue() + "()));");
                str = "val.set(\"" + entryDept.getKey() + "\"," + dataTypeEntityFieldName.get(entryDept.getKey()) + "this." + entryDept.getKey() + "));";

                //                addToClassList(localEntityClass + ".set" + entryDept.getValue() + "( " + dataTypeFieldName.get(entryDept.getKey()) + entryDept.getKey() + ".getText()))");
            } else {
                str = "val.set(\"" + entryDept.getKey() + "\"," + "this." + entryDept.getKey() + ");";
            }
            modifiedProductList.add(str + "\n");

            //Debug.logInfo(str, "");
        }
        modifiedProductList.add("}" + "\n");

        //get value as map for service                
        modifiedProductList.add("public Map<String, Object> getGenericValueAsMap() {" + "\n");
        modifiedProductList.add("Map<String, Object> valueMap = new HashMap<String,Object>();" + "\n");

        // build set method from genericValue
        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            if ("createdTxStamp".equals(entryDept.getKey()) == false
                    && "createdStamp".equals(entryDept.getKey()) == false
                    && "lastUpdatedStamp".equals(entryDept.getKey()) == false
                    && "lastUpdatedTxStamp".equals(entryDept.getKey()) == false) {
                String str = "valueMap.put(\"" + entryDept.getKey() + "\"," + "this." + entryDept.getKey() + ");";
                modifiedProductList.add(str + "\n");
            }
        }
        modifiedProductList.add("return valueMap;" + "\n");
        modifiedProductList.add("}" + "\n");

        modifiedProductList.add("public void getGenericValue(){" + "\n");
        // build set method from genericValue
 /*       for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {

         String str = "genVal.set(\"" + entryDept.getKey() + "\"," + "this." + entryDept.getKey() + ");";
         modifiedProductList.add(str + "\n");
         //Debug.logInfo(str, "");
         }*/

        modifiedProductList.add("getGenericValue(genVal);" + "\n");
        modifiedProductList.add("}" + "\n");

        modifiedProductList.add("public org.ofbiz.entity.GenericValue createNewGenericValueObj(org.ofbiz.entity.Delegator delegator){" + "\n");
        modifiedProductList.add(" genVal = delegator.makeValue(\"" + entityName + "\");" + "\n");
        modifiedProductList.add("        getGenericValue(genVal);" + "\n");
        modifiedProductList.add("return genVal;" + "\n");
        modifiedProductList.add("}" + "\n");

        modifiedProductList.add("public boolean isGenericValueSet() {" + "\n");
        modifiedProductList.add("return genVal != null;" + "\n");
        modifiedProductList.add("}" + "\n");

        modifiedProductList.add("public GenericValue getGenericValueObj() {" + "\n");
        modifiedProductList.add("return genVal;" + "\n");
        modifiedProductList.add("}" + "\n");

        //set get and set methods
        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            String strVal = entryDept.getValue().toString();
            String varDec = "private " + strVal + " " + entryDept.getKey() + ";";
            //Debug.logInfo(varDec, "");
            modifiedProductList.add(varDec + "\n");
            String methodName =  entryDept.getKey().substring(0, 1).toUpperCase() + entryDept.getKey().substring(1);
            String getFun = "public " + strVal + " get" + methodName + "() {";
            //Debug.logInfo(getFun, "");
            modifiedProductList.add(getFun + "\n");
            getFun = "return " + entryDept.getKey() + ";";
            modifiedProductList.add(getFun + "\n");
            //Debug.logInfo(getFun, "");
            getFun = "}";
            //Debug.logInfo(getFun, "");
            modifiedProductList.add(getFun + "\n");

            getFun = "public void set" + methodName + "( " + strVal + " " + entryDept.getKey() + " ) {";
            //Debug.logInfo(getFun, "");
            modifiedProductList.add(getFun + "\n");
            getFun = "this." + entryDept.getKey() + " = " + entryDept.getKey() + ";";
            //Debug.logInfo(getFun, "");
            modifiedProductList.add(getFun + "\n");
            getFun = "}";
            //Debug.logInfo(getFun, "");
            modifiedProductList.add(getFun + "\n");
        }

        modifiedProductList.add("@Override" + "\n");
        modifiedProductList.add("public String[][] getColumnNameId() {" + "\n");
        modifiedProductList.add("        return ColumnNameId;" + "\n");
        modifiedProductList.add("    }" + "\n");

//        modifiedProductList.add("@Override" + "\n");
        modifiedProductList.add("    public Collection getObjectList(List<org.ofbiz.entity.GenericValue> genList) {" + "\n");
        modifiedProductList.add("        Collection<" + entityName + "> objectList = new ArrayList<" + entityName + ">();" + "\n");
        modifiedProductList.add("        for (GenericValue genVal : genList) {" + "\n");
        modifiedProductList.add("            objectList.add(new " + entityName + "(genVal));" + "\n");
        modifiedProductList.add("        }" + "\n");
        modifiedProductList.add("        return objectList;" + "\n");
        modifiedProductList.add("    }    " + "\n");

        modifiedProductList.add(
                "}" + "\n");
        /*
         modifiedProductList.add("public void getUIFields(){" + "\n");
         // build set method from genericValue

         for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {

         String str = "edit" + entryDept.getKey() + ".setText(" + entityName + ".get" + entryDept.getKey() + "());";
         modifiedProductList.add(str + "\n");
         //Debug.logInfo(str, "");
         }
         modifiedProductList.add("}" + "\n");

         modifiedProductList.add("public void SetUIFields(){" + "\n");
         // build set method from genericValue
         for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {

         String str = entityName + ".set" + entryDept.getKey() + "(" + "edit" + entryDept.getKey() + ".getText());";
         modifiedProductList.add(str + "\n");
         //Debug.logInfo(str, "");
         }
         modifiedProductList.add("}" + "\n");
         */

        try {
            String filename = fileNameDir + entityName + ".java";
            FileWriter writer = new FileWriter(filename);
            for (String str : modifiedProductList) {
                writer.write(str);
//                //Debug.logInfo("output: " + str, module);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void writeColumnIds() {
        try {
            String filename = "C:\\AuthLog\\entity\\pojo\\coldIds.java";
            FileWriter writer = new FileWriter(filename);
            for (Map.Entry<String, String> entryDept : columnIdClass.entrySet()) {
                writer.write(entryDept.getValue() + "\n");
//                Debug.logInfo("output: " + entryDept.getValue()"\n", module);

            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"),
                " ");
    }
    static List<String> classString = FastList.newInstance();

    static void addToClassList(String line) {
        classString.add(line + "\n");
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }
    static public HashMap<String, String> columnIdClass = new HashMap<String, String>();
    static public HashMap<String, String> IgnoreFieldList = new HashMap<String, String>();

    static public void addToIgnoreFieldList() {
        IgnoreFieldList.clear();
        IgnoreFieldList.put(ModelEntity.STAMP_FIELD, ModelEntity.STAMP_FIELD);
        IgnoreFieldList.put(ModelEntity.STAMP_TX_FIELD, ModelEntity.STAMP_TX_FIELD);
        IgnoreFieldList.put(ModelEntity.CREATE_STAMP_FIELD, ModelEntity.CREATE_STAMP_FIELD);
        IgnoreFieldList.put(ModelEntity.CREATE_STAMP_TX_FIELD, ModelEntity.CREATE_STAMP_TX_FIELD);
    }

    static public Map<String, Object> getAllFields(GenericEntity genVal) {
        Map<String, Object> newFields = new HashMap<String, Object>();

        Map<String, Object> fieldsMap1 = genVal.getAllFields();
        for (Map.Entry<String, Object> entryDept : fieldsMap1.entrySet()) {
            if (IgnoreFieldList.containsKey(entryDept.getKey()) == false) {
                newFields.put(entryDept.getKey(), entryDept.getValue());
            }
        }
        return newFields;
    }

    static public void writeJavaDisplayClass(GenericEntity genericValue, String entityName) {
        classString.clear();
        HashMap<String, String> labelsEdits = new HashMap<String, String>();
        String localEntityClass = entityName.toLowerCase();
        String className = entityName + "Panel";
        String layoutPanel = "layoutPanel";
        HashMap<String, String> labelsName = new HashMap<String, String>();
        HashMap<String, String> editMethod = new HashMap<String, String>();
        HashMap<String, String> dataTypeFieldName = new HashMap<String, String>();
        HashMap<String, String> dataTypeEntityFieldName = new HashMap<String, String>();
        HashMap<String, Object> fieldsMapX1 = new HashMap<String, Object>();
        HashMap<String, Object> fieldsMapX2 = new HashMap<String, Object>();
        HashMap<String, Object> fieldsMapX3 = new HashMap<String, Object>();

        Map<String, Object> fieldsMap1 = getAllFields(genericValue);
        for (Map.Entry<String, Object> entryDept : fieldsMap1.entrySet()) {

            String str = splitCamelCase(entryDept.getKey());
            //Debug.logInfo("str: " + str, module);
            if (str.contains("Id")) {
                fieldsMapX1.put(entryDept.getKey(), entryDept.getValue());
                //Debug.logInfo("Id: " + str, module);
            } else if (str.contains("Stamp")) {
                fieldsMapX2.put(entryDept.getKey(), entryDept.getValue());
                //Debug.logInfo("Stamp: " + str, module);
            } else {
                fieldsMapX3.put(entryDept.getKey(), entryDept.getValue());
                //Debug.logInfo("else: " + str, module);
            }
        }

        Map<String, Object> fieldsMap = new HashMap<String, Object>();
        fieldsMap.putAll(fieldsMapX1);
        fieldsMap.putAll(fieldsMapX2);
        fieldsMap.putAll(fieldsMapX3);
        List<Object> newList = FastList.newInstance();
        List<String> newKeyList = FastList.newInstance();

        for (Map.Entry<String, Object> entryDept : fieldsMapX1.entrySet()) {
            newList.add(entryDept.getValue());
            newKeyList.add(entryDept.getKey() + "Label");
        }
        for (Map.Entry<String, Object> entryDept : fieldsMapX3.entrySet()) {
            newList.add(entryDept.getValue());
            newKeyList.add(entryDept.getKey() + "Label");
        }
        for (Map.Entry<String, Object> entryDept : fieldsMapX2.entrySet()) {
            newList.add(entryDept.getValue());
            newKeyList.add(entryDept.getKey() + "Label");
        }

        //class types
        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            String strVal;
            if (entryDept.getValue() != null) {

                strVal = entryDept.getValue().getClass().toString().replace("class ", "");
                entryDept.setValue(strVal);
            } else {
                strVal = "java.lang.String";
                if (splitCamelCase(entryDept.getKey()).contains("Date")) {
                    strVal = "java.sql.Timestamp";
                } else {
                    strVal = "java.lang.String";
                }
                entryDept.setValue(strVal);
            }
        }

        for (Map.Entry<String, Object> entryDept : fieldsMap.entrySet()) {
            labelsEdits.put(entryDept.getKey() + "Label", entryDept.getKey() + "TextField");
            labelsName.put(entryDept.getKey() + "Label", capitalizeString(splitCamelCase(entryDept.getKey())));
            editMethod.put(entryDept.getKey() + "TextField", entryDept.getKey() + "");

            if (entryDept.getValue().toString().trim().contains("String")) {
                dataTypeFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidString(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityString(");

            } else if (entryDept.getValue().toString().trim().contains("BigDecimal")) {
                dataTypeFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidBigDecimal(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityBigDecimal(");
            } else if (entryDept.getValue().toString().trim().contains("Timestamp")) {
                dataTypeFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidTimestamp(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityTimestamp(");

            } else if (entryDept.getValue().toString().trim().contains("Long")) {
                dataTypeFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidLong(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityLong(");

            } else if (entryDept.getValue().toString().trim().contains("Double")) {
                dataTypeFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidDouble(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityDouble(");
            } else if (entryDept.getValue().toString().trim().contains("Date")) {
                dataTypeFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidDate(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityDate(");
            } else if (entryDept.getValue().toString().trim().contains("Time")) {
                dataTypeFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidTime(");
                dataTypeEntityFieldName.put(entryDept.getKey() + "TextField", "OrderMaxUtility.getValidEntityTime(");
            }
        }

        addToClassList("package org.ofbiz.ordermax.panel;");

        addToClassList("import org.ofbiz.base.util.Debug;");
        addToClassList("import org.ofbiz.entity.GenericValue;");
        addToClassList("import org.ofbiz.ordermax.entity." + entityName + ";");
        addToClassList("import org.ofbiz.ordermax.generic.GenericValueObjectInterface;");
        addToClassList("import org.ofbiz.ordermax.generic.GenericValuePanelInterface;");
        addToClassList("import javax.swing.event.DocumentEvent;");
        addToClassList("import javax.swing.event.DocumentListener;");
        addToClassList("import javax.swing.*;");
        addToClassList("import java.beans.PropertyChangeListener;");

        addToClassList("public class " + className + " extends JPanel implements GenericValuePanelInterface{");
        addToClassList("public static final String module =" + className + ".class.getName();");
//        addToClassList("private javax.swing.JPanel mainPanel;");

        addToClassList("private " + entityName + " " + localEntityClass + ";");

//    Map<String, S> fieldsMap = genVal.getAllFields();
        for (Map.Entry<String, String> entryDept : labelsEdits.entrySet()) {
            //          labelsEdits.put(entryDept.getKey() + "Label", entryDept.getKey() + "TextField");
            addToClassList("private javax.swing.JLabel " + entryDept.getKey() + ";");
            addToClassList("private javax.swing.JTextField " + entryDept.getValue() + ";");
        }
        addToClassList("private JButton button;");
        addToClassList("private ComponentBorder cb;");

        addToClassList("public boolean isModified() {");
        addToClassList("return isModified;");
        addToClassList("}");

        addToClassList("public void setIsModified(boolean isModified) {");
        addToClassList("this.isModified = isModified;");
        addToClassList("}");
        addToClassList("private boolean isModified = false;");

        addToClassList("public " + className + "(" + entityName + " " + " val ){");
        addToClassList("this." + localEntityClass + "= val;");
        addToClassList("initComponents();");
//        addToClassList("set" + entityName + "UIFields();");
        addToClassList("}");

        addToClassList("public " + className + "(){");
        addToClassList("initComponents();");
//        addToClassList("set" + entityName + "UIFields();");
        addToClassList("}");

        addToClassList("private void initComponents() {");

//        addToClassList("mainPanel = new javax.swing.JPanel();");
        for (Map.Entry<String, String> entryDept : labelsEdits.entrySet()) {
//            labelsEdits.put(entryDept.getKey() + "Label", entryDept.getKey() + "TextField");
            addToClassList(entryDept.getKey() + " = new javax.swing.JLabel();");
            addToClassList(entryDept.getValue() + " = new javax.swing.JTextField();");
            //add change listner
            addToClassList(entryDept.getValue() + ".getDocument().addDocumentListener(new TextChangeListner());");

            if (labelsName.get(entryDept.getKey()).contains(" Id")) {
                createLookUpButton(entryDept.getValue(), entryDept.getValue());
            }

        }

//            labelsEdits.put(entryDept.getKey() + "Label", entryDept.getKey() + "TextField");
//            labelsName.put(entryDept.getKey() + "Label", capitalizeString(splitCamelCase(entryDept.getKey())));
//        addToClassList("mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());");
//        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        for (Map.Entry<String, String> entryDept : labelsName.entrySet()) {
//            labelsEdits.put(entryDept.getKey() + "Label", entryDept.getKey() + "TextField");
            addToClassList(entryDept.getKey() + ".setText(\"" + entryDept.getValue() + ":\");");
        }

//        addToClassList("mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());");
//        addToClassList("org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(mainPanel);");
//        addToClassList("mainPanel.setLayout(layoutPanel);");
        addToClassList("org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);");
        addToClassList("this.setLayout(layoutPanel);");

        addToClassList("layoutPanel.setHorizontalGroup(");
        addToClassList("layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)");
        addToClassList(".add(layoutPanel.createSequentialGroup()");
        addToClassList(".addContainerGap()");
        addToClassList(".add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)");

        List<String> firstHalfKeyList = FastList.newInstance();
        List<String> secondHalfKeyList = FastList.newInstance();
        int i = 0;
        int ii = (newKeyList.size() / 2);
        if (ii * 2 < newKeyList.size()) {
            ii++;
        }

        for (String str : newKeyList) {
            if (i++ < ii) {
                firstHalfKeyList.add(str);
            } else {
                secondHalfKeyList.add(str);
            }
        }

        addHorizElement(firstHalfKeyList, labelsEdits);
        addToClassList(".addContainerGap()");
        addToClassList(".add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)");
        addHorizElement(secondHalfKeyList, labelsEdits);

        addToClassList(".addContainerGap()");
        addToClassList("));");
        addToClassList("layoutPanel.setVerticalGroup(");
        addToClassList("            layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)");
        addToClassList("            .add(layoutPanel.createSequentialGroup()");
        addToClassList("                .addContainerGap()");
        addToClassList("                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)");
        addVertElement(firstHalfKeyList, secondHalfKeyList, labelsEdits);

        addToClassList(".addContainerGap()");

        addToClassList("));");

        addToClassList("}");
        addToClassList(" ");
        addToClassList("public void setUIFields() throws java.text.ParseException {");
        for (Map.Entry<String, String> entryDept : editMethod.entrySet()) {

            if (dataTypeFieldName.containsKey(entryDept.getKey())) {
                addToClassList(entryDept.getKey() + ".setText( " + dataTypeFieldName.get(entryDept.getKey()) + localEntityClass + ".get" + entryDept.getValue() + "()));");
//                addToClassList(localEntityClass + ".set" + entryDept.getValue() + "( " + dataTypeFieldName.get(entryDept.getKey()) + entryDept.getKey() + ".getText()))");
            } else {
                addToClassList(entryDept.getKey() + ".setText(" + localEntityClass + ".get" + entryDept.getValue() + "());");
            }
        }

        addToClassList("}");

        addToClassList(" ");
        addToClassList("public void getUIFields() throws java.text.ParseException {");
        for (Map.Entry<String, String> entryDept : editMethod.entrySet()) {
            if (dataTypeFieldName.containsKey(entryDept.getKey())) {
                addToClassList(localEntityClass + ".set" + entryDept.getValue() + "( " + dataTypeFieldName.get(entryDept.getKey()) + entryDept.getKey() + ".getText()));");
            } else {
                addToClassList(localEntityClass + ".set" + entryDept.getValue() + "( " + entryDept.getKey() + ".getText());");
            }
        }
        addToClassList("}");

        addToClassList(" ");
        addToClassList("private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();");
        addToClassList(" ");
        addToClassList("private void notifyListeners(String property, String oldValue, String newValue) {");
        addToClassList("        for (PropertyChangeListener name : listener) {");
        addToClassList("            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));");
        addToClassList("        }");
        addToClassList("    }");

        addToClassList(" ");
        addToClassList("    public void addChangeListener(PropertyChangeListener newListener) {");
        addToClassList("        listener.add(newListener);");
        addToClassList("    }");

        addToClassList("public static void createAndShowUI(" + entityName + " val){");
        addToClassList("try {");
        addToClassList(className + " panel = new " + className + "(val);");

        addToClassList("JFrame frame = new JFrame(\"Test Gui\");");
        addToClassList("frame.getContentPane().add(panel);");
        addToClassList("panel.setUIFields();");
        addToClassList("frame.pack();");
        addToClassList("frame.setLocationRelativeTo(null);");
        addToClassList("frame.setVisible(true);");
        addToClassList("} catch (java.text.ParseException ex) {");
        addToClassList("    Debug.logError(ex,module);        ");
        addToClassList("}");

        addToClassList("}");

        addToClassList("@Override");
        addToClassList("public GenericValueObjectInterface createUIObject(GenericValue baseVal) {");

        addToClassList(entityName + " newObj =null;");
        addToClassList("if (baseVal != null) {");

        addToClassList(" newObj = new " + entityName + "(baseVal);");

        addToClassList("try { ");
        addToClassList("    newObj.setGenericValue();");
        addToClassList("} catch (Exception ex) {");
        addToClassList("Debug.logError (ex, module);");
        addToClassList("}");

        addToClassList("} else {");
        addToClassList(" newObj = new " + entityName + "();");
        addToClassList("}");

        addToClassList("return newObj;");
        addToClassList("}");

        addToClassList("public GenericValueObjectInterface getUIObject() {");
        addToClassList("return " + localEntityClass + ";");
        addToClassList("}");

        addToClassList("@Override");
        addToClassList("public void changeUIObject(GenericValueObjectInterface uiObject) {");
        addToClassList("    if (uiObject instanceof " + entityName + ") {");
        addToClassList(entityName + " newObj = (" + entityName + ") uiObject;");
        addToClassList(localEntityClass + " = newObj;");

        addToClassList("try { ");
        addToClassList(localEntityClass + ".setGenericValue();");
        addToClassList("} catch (Exception ex) {");
        addToClassList("//Debug.logError (ex, module);");
        addToClassList("}");

        addToClassList(" }");
        addToClassList(" }");

        addToClassList("public JPanel getContainerPanel(){ return this; }");

        addToClassList("private class TextChangeListner implements DocumentListener {");

        addToClassList("public void actionPerformed(java.awt.event.ActionEvent e) {");
        addToClassList("}");

        addToClassList("public void changedUpdate(DocumentEvent e) {");
        addToClassList("warn(e);");
        addToClassList("}");

        addToClassList("public void removeUpdate(DocumentEvent e) {");
        addToClassList("warn(e);");
        addToClassList("}");

        addToClassList("public void insertUpdate(DocumentEvent e) {");
        addToClassList("warn(e);");
        addToClassList("}");

        addToClassList("void warn(DocumentEvent e) {");
        addToClassList("    isModified = true;");

        addToClassList("}");
        addToClassList("}");
        addToClassList("}");

        addToClassList("//calling function copy and paste");
        addToClassList("/*");
        addToClassList("try {");
        addToClassList("");
        addToClassList("    Delegator delegator = XuiContainer.getSession().getDelegator();");
        addToClassList("List<GenericValue> genValList = PosProductHelper.getGenericValueLists(\"" + entityName + "\", delegator);");
        addToClassList("GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession()," + entityName + ".ColumnNameId);");
        addToClassList("dlg.setupOrderTableList(genValList);");
        addToClassList("GenericValue val = genValList.get(0);");
        addToClassList("GenericValuePanelInterface panel = new org.ofbiz.ordermax.utility." + className + "( ); ");
        addToClassList("Object uiObj = panel.createUIObject(val);");
        addToClassList("panel.changeUIObject(uiObj);");
        addToClassList("panel.setUIFields();");
        addToClassList("dlg.setChildPanelInterface(panel);");
        addToClassList("OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());");
        addToClassList("dlg.setLocationRelativeTo(null);");
        addToClassList("dlg.pack();");
        addToClassList("dlg.setVisible(true);");
        addToClassList("} catch (java.text.ParseException ex) {");
        addToClassList("Debug.logError(ex, module);");
        addToClassList("//Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);");
        addToClassList("}");
        addToClassList("*/");

        try {
            String filename = "C:\\AuthLog\\entity\\panel\\" + className + ".java";
            FileWriter writer = new FileWriter(filename);
            for (String str : classString) {
                writer.write(str + "\n");
//                Debug.logInfo("output: " + str, module);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void addHorizElement(List<String> newKeyList, HashMap<String, String> labelsEdits) {
        for (String str : newKeyList) {
            addToClassList(".add(" + str + ")");
        }

        addToClassList(")");

        addToClassList(".addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)");
        addToClassList(".add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)");

        for (String str : newKeyList) {
            Object value = labelsEdits.get(str);
            addToClassList(".add(" + value + ", org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)");
        }
        addToClassList(")");
    }

    static public void addVertElement(List<String> leftKeyList, List<String> rightKeyList, HashMap<String, String> labelsEdits) {
        boolean isFirst = true;
        int i = 0;
        for (String str : leftKeyList) {
            Object value = labelsEdits.get(str);

            if (isFirst) {
                addToClassList(".add(" + str + ")");
                addToClassList(".add(" + value + ", org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)");

            } else {
                addToClassList(".addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)");
                addToClassList(".add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)");
                addToClassList(".add(" + str + ")");
                addToClassList(".add(" + value + ", org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)");

            }
            if (i < rightKeyList.size()) {
                str = rightKeyList.get(i++);
                value = labelsEdits.get(str);
                if (isFirst) {
                    addToClassList(".add(" + str + ")");
                    addToClassList(".add(" + value + ", org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))");
                } else {
                    addToClassList(".add(" + str + ")");
                    addToClassList(".add(" + value + ", org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))");
                }
            } else {
                addToClassList(")");
            }

            isFirst = false;
        }

    }

    static public void createLookUpButton(String textField, String fieldName) {
        addToClassList(" button = new JButton(\"...\");");
        addToClassList(" cb = new ComponentBorder( button );");
        addToClassList("cb.install(" + textField + ");");
        addToClassList("button.addActionListener(new LookupActionListner(" + textField + ", \"" + fieldName + "\"  ));");
    }
}
