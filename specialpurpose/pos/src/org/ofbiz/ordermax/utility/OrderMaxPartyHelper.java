/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.util.Map;
import javolution.util.FastMap;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.LocalDispatcher;

/**
 *
 * @author siranjeev
 */
public class OrderMaxPartyHelper {

    public static boolean createOrUpdateParty(LocalDispatcher dispatcher, Delegator delegator, GenericValue userLogin, Map<String, Object> context) {
        /*
         if (partyId != null) {
         GenericValue party = null;
         try {
         party = delegator.findOne("Party", false, "partyId", partyId);
         } catch (GenericEntityException e) {
         Debug.logError(e, module);
         }
         if (party == null) {
         partyId = null;
         }
         }

         Map<String, Object> inMap = FastMap.newInstance();
         Map<String, Object> outMap = null;
         // Create the person if necessary
         boolean newParty = false;
         if (partyId == null) {
         newParty = true;
         inMap.put("userLogin", cart.getUserLogin());
         inMap.put("personalTitle", decoder.get("SALUTATION"));
         inMap.put("firstName", decoder.get("FIRSTNAME"));
         inMap.put("middleName", decoder.get("MIDDLENAME"));
         inMap.put("lastName", decoder.get("LASTNAME"));
         inMap.put("suffix", decoder.get("SUFFIX"));
         try {
         outMap = dispatcher.runSync("createPerson", inMap);
         partyId = (String) outMap.get("partyId");
         cart.setOrderPartyId(partyId);
         cart.getUserLogin().setString("partyId", partyId);
         inMap.clear();
         inMap.put("userLogin", cart.getUserLogin());
         inMap.put("partyId", partyId);
         inMap.put("roleTypeId", "CUSTOMER");
         dispatcher.runSync("createPartyRole", inMap);
         } catch (GenericServiceException e) {
         Debug.logError(e, module);
         return ServiceUtil.returnError(e.getMessage());
         }
         }
         // Create a new email address if necessary
         String emailContactMechId = null;
         String emailContactPurposeTypeId = "PRIMARY_EMAIL";
         String emailAddress = decoder.get("EMAIL");
         if (!newParty) {
         EntityCondition cond = EntityCondition.makeCondition(UtilMisc.toList(
         EntityCondition.makeCondition(UtilMisc.toMap("partyId", partyId, "contactMechTypeId", "EMAIL_ADDRESS")),
         EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("infoString"), EntityComparisonOperator.EQUALS, EntityFunction.UPPER(emailAddress)),
         EntityUtil.getFilterByDateExpr()

         ));
         try {
         GenericValue matchingEmail = EntityUtil.getFirst(delegator.findList("PartyAndContactMech", cond, null, UtilMisc.toList("fromDate"), null, false));
         if (matchingEmail != null) {
         emailContactMechId = matchingEmail.getString("contactMechId");
         } else {
         // No email found so we'll need to create one but first check if it should be PRIMARY or just BILLING
         cond = EntityCondition.makeCondition(UtilMisc.toList(
         EntityCondition.makeCondition(UtilMisc.toMap("partyId", partyId, "contactMechTypeId", "EMAIL_ADDRESS", "contactMechPurposeTypeId", "PRIMARY_EMAIL")),
         EntityCondition.makeConditionDate("contactFromDate", "contactThruDate"),
         EntityCondition.makeConditionDate("purposeFromDate", "purposeThruDate")));
         List<GenericValue> primaryEmails = delegator.findList("PartyContactWithPurpose", cond, null, null, null, false);
         if (UtilValidate.isNotEmpty(primaryEmails)) emailContactPurposeTypeId = "BILLING_EMAIL";
         }
         } catch (GenericEntityException e) {
         Debug.logError(e, module);
         }
         }
         if (emailContactMechId == null) {
         inMap.clear();
         inMap.put("userLogin", cart.getUserLogin());
         inMap.put("contactMechPurposeTypeId", emailContactPurposeTypeId);
         inMap.put("emailAddress", emailAddress);
         inMap.put("partyId", partyId);
         inMap.put("roleTypeId", "CUSTOMER");
         inMap.put("verified", "Y");  // Going to assume PayPal has taken care of this for us
         inMap.put("fromDate", UtilDateTime.nowTimestamp());
         try {
         outMap = dispatcher.runSync("createPartyEmailAddress", inMap);
         emailContactMechId = (String) outMap.get("contactMechId");
         } catch (GenericServiceException e) {
         Debug.logError(e, module);
         return ServiceUtil.returnError(e.getMessage());
         }
         }
         cart.addContactMech("ORDER_EMAIL", emailContactMechId);

         // Phone number
         String phoneNumber = decoder.get("PHONENUM");
         String phoneContactId = null;
         if (phoneNumber != null) {
         inMap.clear();
         if (phoneNumber.startsWith("+")) {
         // International, format is +XXX XXXXXXXX which we'll split into countryCode + contactNumber
         String[] phoneNumbers = phoneNumber.split(" ");
         inMap.put("countryCode", StringUtil.removeNonNumeric(phoneNumbers[0]));
         inMap.put("contactNumber", phoneNumbers[1]);
         } else {
         // U.S., format is XXX-XXX-XXXX which we'll split into areaCode + contactNumber
         inMap.put("countryCode", "1");
         String[] phoneNumbers = phoneNumber.split("-");
         inMap.put("areaCode", phoneNumbers[0]);
         inMap.put("contactNumber", phoneNumbers[1] + phoneNumbers[2]);
         }
         inMap.put("userLogin", cart.getUserLogin());
         inMap.put("partyId", partyId);
         try {
         outMap = dispatcher.runSync("createUpdatePartyTelecomNumber", inMap);
         phoneContactId = (String) outMap.get("contactMechId");
         cart.addContactMech("PHONE_BILLING", phoneContactId);
         } catch (GenericServiceException e) {
         Debug.logError(e, module);
         }
         }
         // Create a new Postal Address if necessary
         String postalContactId = null;
         boolean needsShippingPurpose = true;
         // if the cart for some reason already has a billing address, we'll leave it be
         boolean needsBillingPurpose = (cart.getContactMech("BILLING_LOCATION") == null);
         Map<String, Object> postalMap = FastMap.newInstance();
         postalMap.put("toName", decoder.get("SHIPTONAME"));
         postalMap.put("address1", decoder.get("SHIPTOSTREET"));
         postalMap.put("address2", decoder.get("SHIPTOSTREET2"));
         postalMap.put("city", decoder.get("SHIPTOCITY"));
         String countryGeoId = PayPalServices.getCountryGeoIdFromGeoCode(decoder.get("SHIPTOCOUNTRYCODE"), delegator);
         postalMap.put("countryGeoId", countryGeoId);
         postalMap.put("stateProvinceGeoId", parseStateProvinceGeoId(decoder.get("SHIPTOSTATE"), countryGeoId, delegator));
         postalMap.put("postalCode", decoder.get("SHIPTOZIP"));
         if (!newParty) {
         // We want an exact match only
         EntityCondition cond = EntityCondition.makeCondition(UtilMisc.toList(
         EntityCondition.makeCondition(postalMap),
         EntityCondition.makeCondition(UtilMisc.toMap("attnName", null, "directions", null, "postalCodeExt", null,"postalCodeGeoId", null)),
         EntityUtil.getFilterByDateExpr(),
         EntityCondition.makeCondition("partyId", partyId)
         ));
         try {
         GenericValue postalMatch = EntityUtil.getFirst(delegator.findList("PartyAndPostalAddress", cond, null, UtilMisc.toList("fromDate"), null, false));
         if (postalMatch != null) {
         postalContactId = postalMatch.getString("contactMechId");
         EntityCondition purposeCond = EntityCondition.makeCondition(UtilMisc.toList(
         EntityCondition.makeCondition(UtilMisc.toMap("partyId", partyId, "contactMechId", postalContactId)),
         EntityUtil.getFilterByDateExpr()
         ));
         List<GenericValue> postalPurposes = delegator.findList("PartyContactMechPurpose", purposeCond, null, null, null, false);
         List<Object> purposeStrings = EntityUtil.getFieldListFromEntityList(postalPurposes, "contactMechPurposeTypeId", false);
         if (UtilValidate.isNotEmpty(purposeStrings) && purposeStrings.contains("SHIPPING_LOCATION")) {
         needsShippingPurpose = false;
         }
         if (needsBillingPurpose && UtilValidate.isNotEmpty(purposeStrings) && purposeStrings.contains("BILLING_LOCATION")) {
         needsBillingPurpose = false;
         }
         }
         } catch (GenericEntityException e) {
         Debug.logError(e, module);
         }
         }
         if (postalContactId == null) {
         postalMap.put("userLogin", cart.getUserLogin());
         postalMap.put("fromDate", UtilDateTime.nowTimestamp());
         try {
         outMap = dispatcher.runSync("createPartyPostalAddress", postalMap);
         postalContactId = (String) outMap.get("contactMechId");
         } catch (GenericServiceException e) {
         Debug.logError(e, module);
         return ServiceUtil.returnError(e.getMessage());
         }
         }
         if (needsShippingPurpose || needsBillingPurpose) {
         inMap.clear();
         inMap.put("userLogin", cart.getUserLogin());
         inMap.put("contactMechId", postalContactId);
         inMap.put("partyId", partyId);
         try {
         if (needsShippingPurpose) {
         inMap.put("contactMechPurposeTypeId", "SHIPPING_LOCATION");
         dispatcher.runSync("createPartyContactMechPurpose", inMap);
         }
         if (needsBillingPurpose) {
         inMap.put("contactMechPurposeTypeId", "BILLING_LOCATION");
         dispatcher.runSync("createPartyContactMechPurpose", inMap);
         }
         } catch (GenericServiceException e) {
         // Not the end of the world, we'll carry on
         Debug.logInfo(e.getMessage(), module);
         }
         }

         // Load the selected shipping method - thanks to PayPal's less than sane API all we've to work with is the shipping option label
         // that was shown to the customer
         String shipMethod = decoder.get("SHIPPINGOPTIONNAME");
         if ("Calculated Offline".equals(shipMethod)) {
         cart.setCarrierPartyId("_NA_");
         cart.setShipmentMethodTypeId("NO_SHIPPING");
         } else {
         String[] shipMethodSplit = shipMethod.split(" - ");
         cart.setCarrierPartyId(shipMethodSplit[0]);
         String shippingMethodTypeDesc = StringUtils.join(shipMethodSplit, " - ", 1, shipMethodSplit.length);
         try {
         EntityCondition cond = EntityCondition.makeCondition(
         UtilMisc.toMap("productStoreId", cart.getProductStoreId(), "partyId", shipMethodSplit[0], "roleTypeId", "CARRIER", "description", shippingMethodTypeDesc)
         );
         GenericValue shipmentMethod = EntityUtil.getFirst(delegator.findList("ProductStoreShipmentMethView", cond, null, null, null, false));
         cart.setShipmentMethodTypeId(shipmentMethod.getString("shipmentMethodTypeId"));
         } catch (GenericEntityException e1) {
         Debug.logError(e1, module);
         }
         }
         //Get rid of any excess ship groups
         List<ShoppingCart.CartShipInfo> shipGroups = cart.getShipGroups();
         for (int i = 1; i < shipGroups.size(); i++) {
         Map<ShoppingCartItem, BigDecimal> items = cart.getShipGroupItems(i);
         for (Map.Entry<ShoppingCartItem, BigDecimal> entry : items.entrySet()) {
         cart.positionItemToGroup(entry.getKey(), entry.getValue(), i, 0, false);
         }
         }
         cart.cleanUpShipGroups();
         cart.setShippingContactMechId(postalContactId);
         Map<String, Object> result = ShippingEvents.getShipGroupEstimate(dispatcher, delegator, cart, 0);
         if (result.get(ModelService.RESPONSE_MESSAGE).equals(ModelService.RESPOND_ERROR)) {
         return ServiceUtil.returnError((String) result.get(ModelService.ERROR_MESSAGE));
         }

         BigDecimal shippingTotal = (BigDecimal) result.get("shippingTotal");
         if (shippingTotal == null) {
         shippingTotal = BigDecimal.ZERO;
         }
         cart.setItemShipGroupEstimate(shippingTotal, 0);
         CheckOutHelper cho = new CheckOutHelper(dispatcher, delegator, cart);
         try {
         cho.calcAndAddTax();
         } catch (GeneralException e) {
         Debug.logError(e, module);
         return ServiceUtil.returnError(e.getMessage());
         }

         // Create the PayPal payment method
         inMap.clear();
         inMap.put("userLogin", cart.getUserLogin());
         inMap.put("partyId", partyId);
         inMap.put("contactMechId", postalContactId);
         inMap.put("fromDate", UtilDateTime.nowTimestamp());
         inMap.put("payerId", decoder.get("PAYERID"));
         inMap.put("expressCheckoutToken", token);
         inMap.put("payerStatus", decoder.get("PAYERSTATUS"));

         try {
         outMap = dispatcher.runSync("createPayPalPaymentMethod", inMap);
         } catch (GenericServiceException e) {
         Debug.logError(e, module);
         return ServiceUtil.returnError(e.getMessage());
         }
         String paymentMethodId = (String) outMap.get("paymentMethodId");

         cart.clearPayments();
         BigDecimal maxAmount = cart.getGrandTotal().setScale(2, BigDecimal.ROUND_HALF_UP);
         cart.addPaymentAmount(paymentMethodId, maxAmount, true);

         return ServiceUtil.returnSuccess();
         */
        return true;
    }

    static public FastMap<String, Object> createPartyContactMech(Delegator delegator, GenericValue party,
            GenericValue telNumber, GenericValue address) {
        
        FastMap<String, Object> input = FastMap.newInstance();
        input.put("partyId", party.get("partyId"));
        input.put("description", party.get("description"));
        input.put("statusId", party.get("statusId"));
        input.put("createdDate", party.get("createdDate"));
        input.put("partyTypeId", party.get("partyTypeId"));
        input.put("externalId", party.get("externalId"));
        input.put("preferredCurrencyUomId", party.get("preferredCurrencyUomId"));
        input.put("createdByUserLogin", party.get("createdByUserLogin"));
        input.put("lastModifiedDate", party.get("lastModifiedDate"));


//        GenericValue partyContechMap = delegator.makeValue("TestParty", input);
        return input;
    }
}
