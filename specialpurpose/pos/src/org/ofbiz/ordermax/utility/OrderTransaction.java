/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.accounting.payment.PaymentGatewayServices;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.Log4jLoggerWriter;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.collections.LifoSet;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.CheckOutHelper;
import org.ofbiz.order.shoppingcart.ItemNotFoundException;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.order.shoppinglist.ShoppingListEvents;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.product.config.ProductConfigWrapper;
import org.ofbiz.product.config.ProductConfigWrapper.ConfigOption;
import org.ofbiz.product.product.ProductWorker;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

@SuppressWarnings("serial")
public class OrderTransaction implements Serializable {

    public static final int scale = UtilNumber.getBigDecimalScale("order.decimals");
    public static final int rounding = UtilNumber.getBigDecimalRoundingMode("order.rounding");
    public static final BigDecimal ZERO = (BigDecimal.ZERO).setScale(scale, rounding);
    public static final String resource = "PosUiLabels";
    public static final String module = OrderTransaction.class.getName();
    public static final int NO_PAYMENT = 0;
    public static final int INTERNAL_PAYMENT = 1;
    public static final int EXTERNAL_PAYMENT = 2;
    protected static PrintWriter defaultPrintWriter = new Log4jLoggerWriter(Debug.getLogger(module));
    protected static OrderTransaction currentTx = null;
    protected static LifoSet<OrderTransaction> savedTx = new LifoSet<OrderTransaction>();
    protected XuiSession session = null;
    private ShoppingCart cart = null;
    protected CheckOutHelper ch = null;
    protected PrintWriter trace = null;
    protected GenericValue txLog = null;
    protected String productStoreId = null;
    protected String transactionId = null;
    protected String facilityId = null;
    protected String terminalId = null;
    protected String currency = null;
    protected String orderId = null;
    protected String partyId = null;
    protected Locale locale = null;
    protected boolean isOpen = false;
    protected int drawerIdx = 0;
    private GenericValue shipAddress = null;
    private Map<String, Integer> skuDiscounts = FastMap.newInstance();
    private int cartDiscount = -1;

    public OrderTransaction(XuiSession session) {
        this.session = session;
        this.terminalId = session.getId();
        this.partyId = "_NA_";
        this.trace = defaultPrintWriter;

        this.productStoreId = (String) session.getAttribute("productStoreId");
        this.facilityId = (String) session.getAttribute("facilityId");
        this.currency = (String) session.getAttribute("currency");
//        this.locale = (Locale) session.getAttribute("locale"); This is legacy code and may come (demo) from ProductStore.defaultLocaleString defined in demoRetail and is incompatible with how localisation is handled in the POS
        this.locale = Locale.getDefault();

        this.setCart(new ShoppingCart(session.getDelegator(), productStoreId, locale, currency));
        this.ch = new CheckOutHelper(session.getDispatcher(), session.getDelegator(), getCart());
        getCart().setChannelType("POS_SALES_CHANNEL");
        getCart().setTransactionId(transactionId);
        getCart().setFacilityId(facilityId);
        getCart().setTerminalId(terminalId);
        if (session.getUserLogin() != null) {
            getCart().addAdditionalPartyRole(session.getUserLogin().getString("partyId"), "SALES_REP");
        }

        // setup the TX log
        this.transactionId = session.getDelegator().getNextSeqId("PosTerminalLog");
        txLog = session.getDelegator().makeValue("PosTerminalLog");
        txLog.set("posTerminalLogId", this.transactionId);
        txLog.set("posTerminalId", terminalId);
        txLog.set("transactionId", transactionId);
        txLog.set("userLoginId", session.getUserId());
        txLog.set("statusId", "POSTX_ACTIVE");
        txLog.set("logStartDateTime", UtilDateTime.nowTimestamp());
        try {
            txLog.create();
        } catch (GenericEntityException e) {
            Debug.logError(e, "Unable to create TX log - not fatal", module);
        }

        currentTx = this;
        trace("transaction created");
    }

    public XuiSession getSession() {
        return session;
    }

    public String getUserId() {
        return session.getUserId();
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
        this.getCart().setPlacingCustomerPartyId(partyId);
        try {
            this.getCart().setUserLogin(session.getUserLogin(), session.getDispatcher());
        } catch (CartItemModifyException e) {
            Debug.logError(e, module);
        }
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public String getTerminalId() {
        return this.terminalId;
    }

    public String getFacilityId() {
        return this.facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
        this.getCart().setFacilityId(facilityId);
    }

    public String getTerminalLogId() {
        return txLog.getString("posTerminalLogId");
    }

    public boolean isEmpty() {
        return (UtilValidate.isEmpty(getCart()));
    }

    public List<GenericValue> lookupItem(String sku) throws GeneralException {
        return ProductWorker.findProductsById(session.getDelegator(), sku, null);
    }

    public String getOrderId() {
        return this.orderId;
    }

    public BigDecimal getTaxTotal() {
        return getCart().getTotalSalesTax();
    }

    public BigDecimal getGrandTotal() {
        return getCart().getGrandTotal();
    }

    public int getNumberOfPayments() {
        return getCart().selectedPayments();
    }

    public BigDecimal getPaymentTotal() {
        return getCart().getPaymentTotal();
    }

    public BigDecimal getTotalDue() {
        BigDecimal grandTotal = this.getGrandTotal();
        BigDecimal paymentAmt = this.getPaymentTotal();
        return grandTotal.subtract(paymentAmt);
    }

    public int size() {
        return getCart().size();
    }

    public Map<String, Object> getItemInfo(int index) {
        ShoppingCartItem item = getCart().findCartItem(index);
        Map<String, Object> itemInfo = FastMap.newInstance();
        itemInfo.put("productId", item.getProductId());
        String description = item.getDescription();
        if (UtilValidate.isEmpty(description)) {
            itemInfo.put("description", item.getName());
        } else {
            itemInfo.put("description", description);
        }
        itemInfo.put("quantity", UtilFormatOut.formatQuantity(item.getQuantity()));
        itemInfo.put("subtotal", UtilFormatOut.formatPrice(item.getItemSubTotal()));
        itemInfo.put("isTaxable", item.taxApplies() ? "T" : " ");

        itemInfo.put("discount", "");
        itemInfo.put("adjustments", "");
        if (item.getOtherAdjustments().compareTo(BigDecimal.ZERO) != 0) {
//sur            itemInfo.put("itemDiscount", UtilFormatOut.padString(
//sur            UtilProperties.getMessage(resource, "PosItemDiscount", locale), Receipt.pridLength[0] + 1, true, ' '));
//sur            itemInfo.put("adjustments", UtilFormatOut.formatPrice(item.getOtherAdjustments()));
        }

        if (isAggregatedItem(item.getProductId())) {
            ProductConfigWrapper pcw = null;
            pcw = item.getConfigWrapper();
            itemInfo.put("basePrice", UtilFormatOut.formatPrice(pcw.getDefaultPrice()));
        } else {
            itemInfo.put("basePrice", UtilFormatOut.formatPrice(item.getBasePrice()));
        }
        return itemInfo;
    }

    public List<Map<String, Object>> getItemConfigInfo(int index) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // I think I need to initialize the list in a special way
        // to use foreach in receipt.java

        ShoppingCartItem item = getCart().findCartItem(index);
        if (this.isAggregatedItem(item.getProductId())) {
            ProductConfigWrapper pcw = null;
            pcw = item.getConfigWrapper();
            List<ConfigOption> selected = pcw.getSelectedOptions();
            for (ConfigOption configoption : selected) {
                Map<String, Object> itemInfo = FastMap.newInstance();
                if (configoption.isSelected() && !configoption.isDefault()) {
                    itemInfo.put("productId", "");
                    itemInfo.put("sku", "");
                    itemInfo.put("configDescription", configoption.getDescription(locale));
                    itemInfo.put("configQuantity", UtilFormatOut.formatQuantity(item.getQuantity()));
                    itemInfo.put("configBasePrice", UtilFormatOut.formatPrice(configoption.getOffsetPrice()));
                    //itemInfo.put("isTaxable", item.taxApplies() ? "T" : " ");
                    list.add(itemInfo);
                }
            }
        }
        return list;
    }

    public Map<String, Object> getPaymentInfo(int index) {
        ShoppingCart.CartPaymentInfo inf = getCart().getPaymentInfo(index);
        GenericValue infValue = inf.getValueObject(session.getDelegator());
        GenericValue paymentPref = null;
        try {
            Map<String, Object> fields = FastMap.newInstance();
            fields.put("paymentMethodTypeId", inf.paymentMethodTypeId);
            if (inf.paymentMethodId != null) {
                fields.put("paymentMethodId", inf.paymentMethodId);
            }
            fields.put("maxAmount", inf.amount);
            fields.put("orderId", this.getOrderId());

            List<GenericValue> paymentPrefs = session.getDelegator().findByAnd("OrderPaymentPreference", fields);
            if (UtilValidate.isNotEmpty(paymentPrefs)) {
                //Debug.logInfo("Found some prefs - " + paymentPrefs.size(), module);
                if (paymentPrefs.size() > 1) {
                    Debug.logError("Multiple OrderPaymentPreferences found for the same payment method!", module);
                } else {
                    paymentPref = EntityUtil.getFirst(paymentPrefs);
                    //Debug.logInfo("Got the first pref - " + paymentPref, module);
                }
            } else {
                Debug.logError("No OrderPaymentPreference found - " + fields, module);
            }
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        //Debug.logInfo("PaymentPref - " + paymentPref, module);

        Map<String, Object> payInfo = FastMap.newInstance();

        // locate the auth info
        GenericValue authTrans = null;
        if (paymentPref != null) {
            authTrans = PaymentGatewayServices.getAuthTransaction(paymentPref);
            if (authTrans != null) {
                payInfo.putAll(authTrans);

                String authInfoString = "Ref: " + authTrans.getString("referenceNum") + " Auth: " + authTrans.getString("gatewayCode");
                payInfo.put("authInfoString", authInfoString);
            } else {
                Debug.logError("No Authorization transaction found for payment preference - " + paymentPref, module);
            }
        } else {
            Debug.logError("Payment preference is empty!", module);
            return payInfo;
        }
        //Debug.logInfo("AuthTrans - " + authTrans, module);

        if ("PaymentMethodType".equals(infValue.getEntityName())) {
            payInfo.put("description", infValue.get("description", locale));
            payInfo.put("payInfo", infValue.get("description", locale));
            payInfo.put("amount", UtilFormatOut.formatPrice(inf.amount));
        } else {
            String paymentMethodTypeId = infValue.getString("paymentMethodTypeId");
            GenericValue pmt = null;
            try {
                pmt = infValue.getRelatedOne("PaymentMethodType");
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }
            if (pmt != null) {
                payInfo.put("description", pmt.get("description", locale));
                payInfo.put("amount", UtilFormatOut.formatPrice(inf.amount));
            }

            if ("CREDIT_CARD".equals(paymentMethodTypeId)) {
                GenericValue cc = null;
                try {
                    cc = infValue.getRelatedOne("CreditCard");
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                }
                String nameOnCard = cc.getString("firstNameOnCard") + " " + cc.getString("lastNameOnCard");
                nameOnCard = nameOnCard.trim();
                payInfo.put("nameOnCard", nameOnCard);

                String cardNum = cc.getString("cardNumber");
                String cardStr = UtilFormatOut.formatPrintableCreditCard(cardNum);

                String expDate = cc.getString("expireDate");
                String infoString = cardStr + " " + expDate;
                payInfo.put("payInfo", infoString);
                payInfo.putAll(cc);
                payInfo.put("cardNumber", cardStr);  // masked cardNumber

            } else if ("GIFT_CARD".equals(paymentMethodTypeId)) {
                /*
                 GenericValue gc = null;
                 try {
                 gc = infValue.getRelatedOne("GiftCard"); //FIXME is this really useful ? (Maybe later...)
                 } catch (GenericEntityException e) {
                 Debug.logError(e, module);
                 }
                 */
            }
        }

        return payInfo;
    }

    public BigDecimal getItemQuantity(String productId) {
        trace("request item quantity", productId);
        ShoppingCartItem item = getCart().findCartItem(productId, null, null, null, BigDecimal.ZERO);
        if (item != null) {
            return item.getQuantity();
        } else {
            trace("item not found", productId);
            return BigDecimal.ZERO;
        }
    }

    public boolean isAggregatedItem(String productId) {
        trace("is Aggregated Item", productId);
        try {
            Delegator delegator = getCart().getDelegator();
            GenericValue product = null;
            product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
            if (UtilValidate.isNotEmpty(product) && "AGGREGATED".equals(product.getString("productTypeId"))) {
                return true;
            }
        } catch (GenericEntityException e) {
            trace("item lookup error", e);
            Debug.logError(e, module);
        } catch (Exception e) {
            trace("general exception", e);
            Debug.logError(e, module);
        }
        return false;
    }

    public ProductConfigWrapper getProductConfigWrapper(String productId) {
        //Get a PCW for a new product
        trace("get Product Config Wrapper", productId);
        ProductConfigWrapper pcw = null;
        try {
            Delegator delegator = getCart().getDelegator();
            pcw = new ProductConfigWrapper(delegator, session.getDispatcher(),
                    productId, null, null, null, null, null, null);
        } catch (ItemNotFoundException e) {
            trace("item not found", e);
            //throw e;
        } catch (CartItemModifyException e) {
            trace("add item error", e);
            //throw e;
        } catch (GenericEntityException e) {
            trace("item lookup error", e);
            Debug.logError(e, module);
        } catch (Exception e) {
            trace("general exception", e);
            Debug.logError(e, module);
        }
        return pcw;
    }

    public ProductConfigWrapper getProductConfigWrapper(String productId, String cartIndex) {
        // Get a PCW for a pre-configured product
        trace("get Product Config Wrapper", productId + "/" + cartIndex);
        ProductConfigWrapper pcw = null;
        try {
            int index = Integer.parseInt(cartIndex);
            ShoppingCartItem product = getCart().findCartItem(index);
            pcw = product.getConfigWrapper();
        } catch (Exception e) {
            trace("general exception", e);
            Debug.logError(e, module);
        }
        return pcw;
    }

    public void addItem(String productId, BigDecimal quantity) throws CartItemModifyException, ItemNotFoundException {
        trace("add item", productId + "/" + quantity);
        
        try {
            Delegator delegator = cart.getDelegator();
            GenericValue product = null;
            ProductConfigWrapper pcw = null;
            product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
            if (UtilValidate.isNotEmpty(product) && "AGGREGATED".equals(product.getString("productTypeId"))) {
                // if it's an aggregated item, load the configwrapper and set to defaults
                pcw = new ProductConfigWrapper(delegator, session.getDispatcher(), productId, null, null, null, null, null, null);
                pcw.setDefaultConfig();
            }
            //cart.addOrIncreaseItem(productId, null, quantity, null, null, null, null, null, null, null, null, null, null, null, null, session.getDispatcher());
            cart.addOrIncreaseItem(productId, null, quantity, null, null, null, null, null, null, null, null, pcw, null, null, null, session.getDispatcher());
        } catch (ItemNotFoundException e) {
            trace("item not found", e);
            throw e;
        } catch (CartItemModifyException e) {
            trace("add item error", e);
            throw e;
        } catch (GenericEntityException e) {
            trace("item lookup error", e);
            Debug.logError(e, module);
        } catch (Exception e) {
            trace("general exception", e);
            Debug.logError(e, module);
        }
    }

    public void addItem(String productId, ProductConfigWrapper pcw)
            throws ItemNotFoundException, CartItemModifyException {
        trace("add item with ProductConfigWrapper", productId);
        try {
            cart.addOrIncreaseItem(productId, null, BigDecimal.ONE, null, null, null, null, null, null, null, null, pcw, null, null, null, session.getDispatcher());
        } catch (ItemNotFoundException e) {
            trace("item not found", e);
            throw e;
        } catch (CartItemModifyException e) {
            trace("add item error", e);
            throw e;
        } catch (Exception e) {
            trace("general exception", e);
            Debug.logError(e, module);
        }
    }

    public void modifyConfig(String productId, ProductConfigWrapper pcw, String cartIndex)
            throws CartItemModifyException, ItemNotFoundException {
        trace("modify item config", cartIndex);
        try {
            int cartIndexInt = Integer.parseInt(cartIndex);
            ShoppingCartItem cartItem = getCart().findCartItem(cartIndexInt);
            BigDecimal quantity = cartItem.getQuantity();
            getCart().removeCartItem(cartIndexInt, session.getDispatcher());
            getCart().addOrIncreaseItem(productId, null, quantity, null, null, null, null, null, null, null, null, pcw, null, null, null, session.getDispatcher());
        } catch (CartItemModifyException e) {
            Debug.logError(e, module);
            trace("void or add item error", productId, e);
            throw e;
        } catch (ItemNotFoundException e) {
            trace("item not found", e);
            throw e;
        } catch (Exception e) {
            trace("general exception", e);
            Debug.logError(e, module);
        }
        return;
    }

    public void modifyQty(String productId, BigDecimal quantity) throws CartItemModifyException {
        trace("modify item quantity", productId + "/" + quantity);
        ShoppingCartItem item = getCart().findCartItem(productId, null, null, null, BigDecimal.ZERO);
        if (item != null) {
            try {
                item.setQuantity(quantity, session.getDispatcher(), getCart(), true);
            } catch (CartItemModifyException e) {
                Debug.logError(e, module);
                trace("modify item error", e);
                throw e;
            }
        } else {
            trace("item not found", productId);
        }
    }

    public ShoppingCartItem getCartItem(String productId) {

        return getCart().findCartItem(productId, null, null, null, BigDecimal.ZERO);

    }

    public void modifyPrice(String productId, BigDecimal price) {
        trace("modify item price", productId + "/" + price);
        ShoppingCartItem item = getCart().findCartItem(productId, null, null, null, BigDecimal.ZERO);
        if (item != null) {
            item.setBasePrice(price);
        } else {
            trace("item not found", productId);
        }
    }

    public void addDiscount(String productId, BigDecimal discount, boolean percent) {
        GenericValue adjustment = session.getDelegator().makeValue("OrderAdjustment");
        adjustment.set("orderAdjustmentTypeId", "DISCOUNT_ADJUSTMENT");
        if (percent) {
            adjustment.set("sourcePercentage", discount.movePointRight(2));
        } else {
            adjustment.set("amount", discount);
        }

        if (productId != null) {
            trace("add item adjustment");
            ShoppingCartItem item = getCart().findCartItem(productId, null, null, null, BigDecimal.ZERO);
            Integer itemAdj = skuDiscounts.get(productId);
            if (itemAdj != null) {
                item.removeAdjustment(itemAdj.intValue());
            }
            int idx = item.addAdjustment(adjustment);
            skuDiscounts.put(productId, idx);
        } else {
            trace("add sale adjustment");
            if (cartDiscount > -1) {
                getCart().removeAdjustment(cartDiscount);
            }
            cartDiscount = getCart().addAdjustment(adjustment);
        }
    }

    public void clearDiscounts() {
        if (cartDiscount > -1) {
            getCart().removeAdjustment(cartDiscount);
            cartDiscount = -1;
        }
        for (String productId : skuDiscounts.keySet()) {
            ShoppingCartItem item = getCart().findCartItem(productId, null, null, null, BigDecimal.ZERO);
            Integer itemAdj = skuDiscounts.remove(productId);
            if (itemAdj != null) {
                item.removeAdjustment(itemAdj.intValue());
            }
        }
    }

    public BigDecimal GetTotalDiscount() {
        return getCart().getOrderOtherAdjustmentTotal();
    }

    public void voidItem(String productId) throws CartItemModifyException {
        trace("void item", productId);
        ShoppingCartItem item = getCart().findCartItem(productId, null, null, null, BigDecimal.ZERO);
        if (item != null) {
            try {
                int itemIdx = getCart().getItemIndex(item);
                getCart().removeCartItem(itemIdx, session.getDispatcher());
            } catch (CartItemModifyException e) {
                Debug.logError(e, module);
                trace("void item error", productId, e);
                throw e;
            }
        } else {
            trace("item not found", productId);
        }
    }

    public void voidSale() {
        trace("void sale");
        txLog.set("statusId", "POSTX_VOIDED");
        txLog.set("itemCount", new Long(getCart().size()));
        txLog.set("logEndDateTime", UtilDateTime.nowTimestamp());
        try {
            txLog.store();
        } catch (GenericEntityException e) {
            Debug.logError(e, "Unable to store TX log - not fatal", module);
        }
        getCart().clear();
//        pos.getPromoStatusBar().clear();
        currentTx = null;
    }

    public void closeTx() {
        trace("transaction closed");
        txLog.set("statusId", "POSTX_CLOSED");
        txLog.set("itemCount", new Long(getCart().size()));
        txLog.set("logEndDateTime", UtilDateTime.nowTimestamp());
        try {
            txLog.store();
        } catch (GenericEntityException e) {
            Debug.logError(e, "Unable to store TX log - not fatal", module);
        }
        getCart().clear();
        currentTx = null;
    }

    public void paidInOut(String type) {
        trace("paid " + type);
        txLog.set("statusId", "POSTX_PAID_" + type);
        txLog.set("logEndDateTime", UtilDateTime.nowTimestamp());
        try {
            txLog.store();
        } catch (GenericEntityException e) {
            Debug.logError(e, "Unable to store TX log - not fatal", module);
        }
        currentTx = null;
    }

    public void calcTax() {
        try {
            ch.calcAndAddTax(this.getStoreOrgAddress());
        } catch (GeneralException e) {
            Debug.logError(e, module);
        }
    }

    public void clearTax() {
        getCart().removeAdjustmentByType("SALES_TAX");
    }

    public int checkPaymentMethodType(String paymentMethodTypeId) {
        Map<String, String> fields = UtilMisc.toMap("paymentMethodTypeId", paymentMethodTypeId, "productStoreId", productStoreId);
        List<GenericValue> values = null;
        try {
            values = session.getDelegator().findByAndCache("ProductStorePaymentSetting", fields);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        final String externalCode = "PRDS_PAY_EXTERNAL";
        if (UtilValidate.isEmpty(values)) {
            return NO_PAYMENT;
        } else {
            boolean isExternal = true;
            Iterator<GenericValue> i = values.iterator();
            while (i.hasNext() && isExternal) {
                GenericValue v = i.next();
                //Debug.logInfo("Testing [" + paymentMethodTypeId + "] - " + v, module);
                if (!externalCode.equals(v.getString("paymentServiceTypeEnumId"))) {
                    isExternal = false;
                }
            }

            if (isExternal) {
                return EXTERNAL_PAYMENT;
            } else {
                return INTERNAL_PAYMENT;
            }
        }
    }

    public BigDecimal addPayment(String id, BigDecimal amount) {
        return this.addPayment(id, amount, null, null);
    }

    public BigDecimal addPayment(String id, BigDecimal amount, String refNum, String authCode) {
        trace("added payment", id + "/" + amount);
        /*   if ("CASH".equals(id)) {
         // clear cash payments first; so there is only one
         cart.clearPayment(id);
         }*/
        getCart().addPaymentAmount(id, amount, refNum, authCode, true, true, false);
        return this.getTotalDue();
    }

    public void setPaymentRefNum(int paymentIndex, String refNum, String authCode) {
        trace("setting payment index reference number", paymentIndex + " / " + refNum + " / " + authCode);
        ShoppingCart.CartPaymentInfo inf = getCart().getPaymentInfo(paymentIndex);
        inf.refNum[0] = refNum;
        inf.refNum[1] = authCode;
    }

    /* CVV2 code should be entered when a card can't be swiped */
    public void setPaymentSecurityCode(String paymentId, String refNum, String securityCode) {
        trace("setting payment security code", paymentId);
        int paymentIndex = getCart().getPaymentInfoIndex(paymentId, refNum);
        ShoppingCart.CartPaymentInfo inf = getCart().getPaymentInfo(paymentIndex);
        inf.securityCode = securityCode;
        inf.isSwiped = false;
    }

    /* Track2 data should be sent to processor when a card is swiped. */
    public void setPaymentTrack2(String paymentId, String refNum, String securityCode) {
        trace("setting payment security code", paymentId);
        int paymentIndex = getCart().getPaymentInfoIndex(paymentId, refNum);
        ShoppingCart.CartPaymentInfo inf = getCart().getPaymentInfo(paymentIndex);
        inf.securityCode = securityCode;
        inf.isSwiped = true;
    }

    /* Postal code should be entered when a card can't be swiped */
    public void setPaymentPostalCode(String paymentId, String refNum, String postalCode) {
        trace("setting payment security code", paymentId);
        int paymentIndex = getCart().getPaymentInfoIndex(paymentId, refNum);
        ShoppingCart.CartPaymentInfo inf = getCart().getPaymentInfo(paymentIndex);
        inf.postalCode = postalCode;
    }

    public void clearPayments() {
        trace("all payments cleared from sale");
        getCart().clearPayments();
    }

    public void clearPayment(int index) {
        trace("removing payment", "" + index);
        getCart().clearPayment(index);
    }

    public void clearPayment(String id) {
        trace("removing payment", id);
        getCart().clearPayment(id);
    }

    public int selectedPayments() {
        return getCart().selectedPayments();
    }

    public void setTxAsReturn(String returnId) {
        trace("returned sale");
        txLog.set("statusId", "POSTX_RETURNED");
        txLog.set("returnId", returnId);
        txLog.set("logEndDateTime", UtilDateTime.nowTimestamp());
        try {
            txLog.store();
        } catch (GenericEntityException e) {
            Debug.logError(e, "Unable to store TX log - not fatal", module);
        }
        getCart().clear();
        currentTx = null;
    }

    public BigDecimal processSale(/*Output output*/) throws GeneralException {
        trace("process sale");
        BigDecimal grandTotal = this.getGrandTotal();
        BigDecimal paymentAmt = this.getPaymentTotal();
        if (grandTotal.compareTo(paymentAmt) > 0) {
            throw new IllegalStateException();
        }

        // attach the party ID to the cart
        getCart().setOrderPartyId(partyId);


        // Set the shipping type
        getCart().setShipmentMethodTypeId(0, "NO_SHIPPING");
        // cart.setCarrierPartyId();

        // validate payment methods
//sur        output.print(UtilProperties.getMessage(resource, "PosValidating", locale));
        Map<String, Object> valRes = ch.validatePaymentMethods();
        if (valRes != null && ServiceUtil.isError(valRes)) {
            throw new GeneralException(ServiceUtil.getErrorMessage(valRes));
        }

        // store the "order"
        if (UtilValidate.isEmpty(this.orderId)) {  // if order does not exist
//sur             output.print(UtilProperties.getMessage(resource, "PosSaving", locale));
            Map<String, Object> orderRes = ch.createOrder(session.getUserLogin());
            //Debug.logInfo("Create Order Resp : " + orderRes, module);

            if (orderRes != null && ServiceUtil.isError(orderRes)) {
                throw new GeneralException(ServiceUtil.getErrorMessage(orderRes));
            } else if (orderRes != null) {
                this.orderId = (String) orderRes.get("orderId");
            }
        } else { // if the order has already been created
            Map<?, ?> changeMap = UtilMisc.toMap("itemReasonMap",
                    UtilMisc.toMap("reasonEnumId", "EnumIdHere"), // TODO: where does this come from?
                    "itemCommentMap", UtilMisc.toMap("changeComments", "change Comments here")); //TODO

            Map<String, Object> svcCtx = FastMap.newInstance();
            svcCtx.put("userLogin", session.getUserLogin());
            svcCtx.put("orderId", orderId);
            svcCtx.put("shoppingCart", getCart());
            svcCtx.put("locale", locale);
            svcCtx.put("changeMap", changeMap);

            Map<String, Object> svcRes = null;
            try {
                LocalDispatcher dispatcher = session.getDispatcher();
                svcRes = dispatcher.runSync("saveUpdatedCartToOrder", svcCtx);
            } catch (GenericServiceException e) {
                Debug.logError(e, module);
                //OrderMaxOptionPane.showMessageDialog(null,"dialog/error/exception", e.getMessage());
                throw new GeneralException(ServiceUtil.getErrorMessage(svcRes));
            }
        }

        // process the payment(s)
//sur        output.print(UtilProperties.getMessage(resource, "PosProcessing", locale));
        Map<String, Object> payRes = null;
        try {
            payRes = ch.processPayment(ProductStoreWorker.getProductStore(productStoreId, session.getDelegator()), session.getUserLogin(), true);
        } catch (GeneralException e) {
            Debug.logError(e, module);
            throw e;
        }

        if (payRes != null && ServiceUtil.isError(payRes)) {
            throw new GeneralException(ServiceUtil.getErrorMessage(payRes));
        }

        // get the change due
        BigDecimal change = grandTotal.subtract(paymentAmt);

        // notify the change due
//sur        output.print(UtilProperties.getMessage(resource, "PosChange",locale) + " " + UtilFormatOut.formatPrice(this.getTotalDue().negate()));
/*
         // threaded drawer/receipt printing
         final OrderTransaction currentTrans = this;
         final SwingWorker worker = new SwingWorker() {
         @Override
         public Object construct() {
         // open the drawer
         currentTrans.popDrawer();

         // print the receipt
         DeviceLoader.receipt.printReceipt(currentTrans, true);

         return null;
         }
         };
         worker.start();
         */
        // save the TX Log
        txLog.set("statusId", "POSTX_SOLD");
        txLog.set("orderId", orderId);
        txLog.set("itemCount", new Long(getCart().size()));
        txLog.set("logEndDateTime", UtilDateTime.nowTimestamp());
        try {
            txLog.store();
        } catch (GenericEntityException e) {
            Debug.logError(e, "Unable to store TX log - not fatal", module);
        }

        // clear the tx
        currentTx = null;
        partyId = "_NA_";

        return change;
    }

    protected synchronized GenericValue getStoreOrgAddress() {
        if (this.shipAddress == null) {
            // locate the store's physical address - use this for tax
            GenericValue facility = (GenericValue) session.getAttribute("facility");
            if (facility == null) {
                return null;
            }

            Delegator delegator = session.getDelegator();
            GenericValue facilityContactMech = ContactMechWorker.getFacilityContactMechByPurpose(delegator, facilityId, UtilMisc.toList("SHIP_ORIG_LOCATION", "PRIMARY_LOCATION"));
            if (facilityContactMech != null) {
                try {
                    this.shipAddress = session.getDelegator().findByPrimaryKey("PostalAddress",
                            UtilMisc.toMap("contactMechId", facilityContactMech.getString("contactMechId")));
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                }
            }
        }
        return this.shipAddress;
    }

    public void saveTx() {
        savedTx.push(this);
        currentTx = null;
        trace("transaction saved");
    }
    /*
     public void appendItemDataModel(XModel model) {
     if (getCart() != null) {
     Iterator<?> i = getCart().iterator();
     while (i.hasNext()) {
     ShoppingCartItem item = (ShoppingCartItem) i.next();
     BigDecimal quantity = item.getQuantity();
     BigDecimal unitPrice = item.getBasePrice();
     BigDecimal subTotal = unitPrice.multiply(quantity);
     BigDecimal adjustment = item.getOtherAdjustments();

     XModel line = Journal.appendNode(model, "tr", ""+getCart().getItemIndex(item), "");
     Journal.appendNode(line, "td", "sku", item.getProductId());
     Journal.appendNode(line, "td", "desc", item.getName());
     Journal.appendNode(line, "td", "qty", UtilFormatOut.formatQuantity(quantity));
     Journal.appendNode(line, "td", "price", UtilFormatOut.formatPrice(subTotal));
     Journal.appendNode(line, "td", "index", Integer.toString(cart.getItemIndex(item)));

     if (this.isAggregatedItem(item.getProductId())) {
     // put alterations here
     ProductConfigWrapper pcw = null;
     // product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
     // pcw = new ProductConfigWrapper(delegator, session.getDispatcher(), productId, null, null, null, null, null, null);
     pcw = item.getConfigWrapper();
     List<ConfigOption> selected = pcw.getSelectedOptions();
     for (ConfigOption configoption : selected) {
     if (configoption.isSelected()) {
     XModel option = Journal.appendNode(model, "tr", ""+getCart().getItemIndex(item), "");
     Journal.appendNode(option, "td", "sku", "");
     Journal.appendNode(option, "td", "desc", configoption.getDescription());
     Journal.appendNode(option, "td", "qty", "");
     Journal.appendNode(option, "td", "price", UtilFormatOut.formatPrice(configoption.getPrice()));
     Journal.appendNode(option, "td", "index", Integer.toString(cart.getItemIndex(item)));
     }
     }
     }

     if (adjustment.compareTo(BigDecimal.ZERO) != 0) {
     // append the promo info
     XModel promo = Journal.appendNode(model, "tr", "itemadjustment", "");
     Journal.appendNode(promo, "td", "sku", "");
     Journal.appendNode(promo, "td", "desc", UtilProperties.getMessage(resource, "PosItemDiscount", locale));
     Journal.appendNode(promo, "td", "qty", "");
     Journal.appendNode(promo, "td", "price", UtilFormatOut.formatPrice(adjustment));
     }
     }
     }
     }

     public void appendTotalDataModel(XModel model) {
     if (getCart() != null) {
     BigDecimal taxAmount = getCart().getTotalSalesTax();
     BigDecimal total = getCart().getGrandTotal();
     List<GenericValue> adjustments = getCart().getAdjustments();
     BigDecimal itemsAdjustmentsAmount = BigDecimal.ZERO;

     Iterator<?> i = getCart().iterator();
     while (i.hasNext()) {
     ShoppingCartItem item = (ShoppingCartItem) i.next();
     BigDecimal adjustment = item.getOtherAdjustments();
     if (adjustment.compareTo(BigDecimal.ZERO) != 0) {
     itemsAdjustmentsAmount = itemsAdjustmentsAmount.add(adjustment);
     }
     }

     for (GenericValue orderAdjustment : adjustments) {
     BigDecimal amount = orderAdjustment.getBigDecimal("amount");
     BigDecimal sourcePercentage = orderAdjustment.getBigDecimal("sourcePercentage");
     XModel adjustmentLine = Journal.appendNode(model, "tr", "adjustment", "");
     Journal.appendNode(adjustmentLine, "td", "sku", "");
     Journal.appendNode(adjustmentLine, "td", "desc",
     UtilProperties.getMessage(resource, "PosSalesDiscount", locale));
     if (UtilValidate.isNotEmpty(amount)) {
     Journal.appendNode(adjustmentLine, "td", "qty", "");
     Journal.appendNode(adjustmentLine, "td", "price", UtilFormatOut.formatPrice(amount));
     } else if (UtilValidate.isNotEmpty(sourcePercentage)) {
     BigDecimal percentage = sourcePercentage.movePointLeft(2).negate(); // sourcePercentage is negative and must be show as a positive value (it's a discount not an amount)
     Journal.appendNode(adjustmentLine, "td", "qty", UtilFormatOut.formatPercentage(percentage));
     amount = cart.getItemTotal().add(itemsAdjustmentsAmount).multiply(percentage); // itemsAdjustmentsAmount is negative
     Journal.appendNode(adjustmentLine, "td", "price", UtilFormatOut.formatPrice(amount.negate())); // amount must be shown as a negative value
     }
     Journal.appendNode(adjustmentLine, "td", "index", "-1");
     }

     XModel taxLine = Journal.appendNode(model, "tr", "tax", "");
     Journal.appendNode(taxLine, "td", "sku", "");

     Journal.appendNode(taxLine, "td", "desc", UtilProperties.getMessage(resource, "PosSalesTax", locale));
     Journal.appendNode(taxLine, "td", "qty", "");
     Journal.appendNode(taxLine, "td", "price", UtilFormatOut.formatPrice(taxAmount));
     Journal.appendNode(taxLine, "td", "index", "-1");

     XModel totalLine = Journal.appendNode(model, "tr", "total", "");
     Journal.appendNode(totalLine, "td", "sku", "");
     Journal.appendNode(totalLine, "td", "desc", UtilProperties.getMessage(resource, "PosGrandTotal", locale));
     Journal.appendNode(totalLine, "td", "qty", "");
     Journal.appendNode(totalLine, "td", "price", UtilFormatOut.formatPrice(total));
     Journal.appendNode(totalLine, "td", "index", "-1");
     }
     }

     public void appendPaymentDataModel(XModel model) {
     if (getCart() != null) {
     int paymentInfoSize = getCart().selectedPayments();
     for (int i = 0; i < paymentInfoSize; i++) {
     ShoppingCart.CartPaymentInfo inf = getCart().getPaymentInfo(i);
     GenericValue paymentInfoObj = inf.getValueObject(session.getDelegator());

     GenericValue paymentMethodType = null;
     GenericValue paymentMethod = null;
     if ("PaymentMethod".equals(paymentInfoObj.getEntityName())) {
     paymentMethod = paymentInfoObj;
     try {
     paymentMethodType = paymentMethod.getRelatedOne("PaymentMethodType");
     } catch (GenericEntityException e) {
     Debug.logError(e, module);
     }
     } else {
     paymentMethodType = paymentInfoObj;
     }

     Object desc = paymentMethodType != null ? paymentMethodType.get("description",locale) : "??";
     String descString = desc.toString();
     BigDecimal amount = BigDecimal.ZERO;
     if (inf.amount == null) {
     amount = getCart().getGrandTotal().subtract(getCart().getPaymentTotal());
     } else {
     amount = inf.amount;
     }

     XModel paymentLine = Journal.appendNode(model, "tr", Integer.toString(i), "");
     Journal.appendNode(paymentLine, "td", "sku", "");
     Journal.appendNode(paymentLine, "td", "desc", descString);
     Journal.appendNode(paymentLine, "td", "qty", "-");
     Journal.appendNode(paymentLine, "td", "price", UtilFormatOut.formatPrice(amount.negate()));
     Journal.appendNode(paymentLine, "td", "index", Integer.toString(i));
     }
     }
     }

     public void appendChangeDataModel(XModel model) {
     if (getCart() != null) {
     BigDecimal changeDue = this.getTotalDue().negate();
     if (changeDue.compareTo(BigDecimal.ZERO) >= 0) {
     XModel changeLine = Journal.appendNode(model, "tr", "", "");
     Journal.appendNode(changeLine, "td", "sku", "");
     Journal.appendNode(changeLine, "td", "desc", "Change");
     Journal.appendNode(changeLine, "td", "qty", "-");
     Journal.appendNode(changeLine, "td", "price", UtilFormatOut.formatPrice(changeDue));
     }
     }
     }
     */

    public String makeCreditCardVo(String cardNumber, String expDate, String firstName, String lastName) {
        LocalDispatcher dispatcher = session.getDispatcher();
        String expMonth = expDate.substring(0, 2);
        String expYear = expDate.substring(2);
        // two digit year check -- may want to re-think this
        if (expYear.length() == 2) {
            expYear = "20" + expYear;
        }

        Map<String, Object> svcCtx = FastMap.newInstance();
        svcCtx.put("userLogin", session.getUserLogin());
        svcCtx.put("partyId", partyId);
        svcCtx.put("cardNumber", cardNumber);
        svcCtx.put("firstNameOnCard", firstName == null ? "" : firstName);
        svcCtx.put("lastNameOnCard", lastName == null ? "" : lastName);
        svcCtx.put("expMonth", expMonth);
        svcCtx.put("expYear", expYear);
        svcCtx.put("cardType", UtilValidate.getCardType(cardNumber));

        //Debug.logInfo("Create CC : " + svcCtx, module);
        Map<String, Object> svcRes = null;
        try {
            svcRes = dispatcher.runSync("createCreditCard", svcCtx);
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            return null;
        }
        if (ServiceUtil.isError(svcRes)) {
            Debug.logError(ServiceUtil.getErrorMessage(svcRes) + " - " + svcRes, module);
            return null;
        } else {
            return (String) svcRes.get("paymentMethodId");
        }
    }

    public GenericValue getTerminalState() {
        Delegator delegator = session.getDelegator();
        List<GenericValue> states = null;
        try {
            states = delegator.findByAnd("PosTerminalState", UtilMisc.toMap("posTerminalId", this.getTerminalId()));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        states = EntityUtil.filterByDate(states, UtilDateTime.nowTimestamp(), "openedDate", "closedDate", true);
        return EntityUtil.getFirst(states);
    }

    public void setPrintWriter(PrintWriter writer) {
        this.trace = writer;
    }

    protected void trace(String s) {
        trace(s, null, null);
    }

    protected void trace(String s, Throwable t) {
        trace(s, null, t);
    }

    protected void trace(String s1, String s2) {
        trace(s1, s2, null);
    }

    protected void trace(String s1, String s2, Throwable t) {
        if (trace != null) {
            String msg = s1;
            if (UtilValidate.isNotEmpty(s2)) {
                msg = msg + "(" + s2 + ")";
            }
            if (t != null) {
                msg = msg + " : " + t.getMessage();
            }

            // print the trace line
            trace.println("[POS @ " + terminalId + " TX:" + transactionId + "] - " + msg);
            trace.flush();
        }
    }

    public static synchronized OrderTransaction getCurrentTx(XuiSession session) {
        if (currentTx == null) {
            if (session.getUserLogin() != null) {
                currentTx = new OrderTransaction(session);
            }
        }
        return currentTx;
    }
    /*
     public void loadSale(PosScreen pos) {
     trace("Load a sale");
     List<GenericValue> shoppingLists = createShoppingLists();
     if (!shoppingLists.isEmpty()) {
     Map<String, String> salesMap = createSalesMap(shoppingLists);
     if (!salesMap.isEmpty()) {
     LoadSale loadSale = new LoadSale(salesMap, this, pos);
     loadSale.openDlg();
     }
     else {
     OrderMaxOptionPane.showMessageDialog(null,"dialog/error/nosales");
     }
     } else {
     OrderMaxOptionPane.showMessageDialog(null,"dialog/error/nosales");
     }
     }

     public void loadOrder(PosScreen pos) {
     List<GenericValue> orders = findOrders();
     if (!orders.isEmpty()) {
     LoadSale loadSale = new LoadSale(createOrderHash(orders), this, pos);
     loadSale.openDlg();
     } else {
     OrderMaxOptionPane.showMessageDialog(null,"dialog/error/nosales");
     }
     }
     */

    protected List<GenericValue> findOrders() {
        LocalDispatcher dispatcher = session.getDispatcher();

        Map<String, Object> svcCtx = FastMap.newInstance();
        svcCtx.put("userLogin", session.getUserLogin());
        svcCtx.put("partyId", partyId);
        List<String> orderStatusIds = FastList.newInstance();
        orderStatusIds.add("ORDER_CREATED");
        svcCtx.put("orderStatusId", orderStatusIds);
        svcCtx.put("viewIndex", 1);
        svcCtx.put("viewSize", 25);
        svcCtx.put("showAll", "Y");

        Map<String, Object> svcRes = null;
        try {
            svcRes = dispatcher.runSync("findOrders", svcCtx);
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
        }

        if (svcRes == null) {
            Debug.logInfo(UtilProperties.getMessage("EcommerceUiLabels", "EcommerceNoShoppingListsCreate", locale), module);
        } else if (ServiceUtil.isError(svcRes)) {
            Debug.logError(ServiceUtil.getErrorMessage(svcRes) + " - " + svcRes, module);
        } else {
            Integer orderListSize = (Integer) svcRes.get("orderListSize");
            if (orderListSize > 0) {
                List<GenericValue> orderList = UtilGenerics.checkList(svcRes.get("orderList"), GenericValue.class);
                return orderList;
            }
        }
        return null;
    }

    /*    public void configureItem(String cartIndex, PosScreen pos) {
     trace("configure item", cartIndex);
     try {
     int index = Integer.parseInt(cartIndex);
     ShoppingCartItem product = cart.findCartItem(index);
     Delegator delegator = cart.getDelegator();
     ProductConfigWrapper pcw = null;
     pcw = product.getConfigWrapper();
     if (pcw != null) {
     ConfigureItem configItem = new ConfigureItem(cartIndex, pcw, this, pos);
     configItem.openDlg();
     }
     else {
     OrderMaxOptionPane.showMessageDialog(null,"dialog/error/itemnotconfigurable");
     }
     } catch (Exception e) {
     trace("general exception", e);
     Debug.logError(e, module);
     }
     }  */
    public List<GenericValue> createShoppingLists() {
        List<GenericValue> shoppingLists = null;
        Delegator delegator = this.session.getDelegator();
        try {
            shoppingLists = delegator.findList("ShoppingList", null, null, null, null, false);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return null;
        }

        if (shoppingLists == null) {
            Debug.logInfo(UtilProperties.getMessage("EcommerceUiLabels", "EcommerceNoShoppingListsCreate", locale), module);
        }
        return shoppingLists;
    }

    public Map<String, String> createSalesMap(List<GenericValue> shoppingLists) {
        Map<String, String> salesMap = FastMap.newInstance();
        for (GenericValue shoppingList : shoppingLists) {
            List<GenericValue> items = null;
            try {
                items = shoppingList.getRelated("ShoppingListItem", UtilMisc.toList("shoppingListItemSeqId"));
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }
            if (UtilValidate.isNotEmpty(items)) {
                String listName = shoppingList.getString("listName");
                String shoppingListId = shoppingList.getString("shoppingListId");
                salesMap.put(shoppingListId, listName);
            }
        }
        return salesMap;
    }

    public Map<String, String> createOrderHash(List<GenericValue> orders) {
        Map<String, String> hash = FastMap.newInstance();
        for (GenericValue order : orders) {
            String orderName = order.getString("orderName");
            String orderId = order.getString("orderId");
            if (orderName != null) {
                hash.put(orderId, orderName);
            }
        }
        return hash;
    }

    public boolean addListToCart(String shoppingListId, boolean append) {
        trace("Add list to cart");
        Delegator delegator = session.getDelegator();
        LocalDispatcher dispatcher = session.getDispatcher();
        String includeChild = null; // Perhaps will be used later ...
        String prodCatalogId = null;

        try {
            ShoppingListEvents.addListToCart(delegator, dispatcher, getCart(), prodCatalogId, shoppingListId, (includeChild != null), true, append);
        } catch (IllegalArgumentException e) {
            Debug.logError(e, module);
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        return true;
    }

    public boolean restoreOrder(String orderId, boolean append) {
        trace("Restore an order");

        LocalDispatcher dispatcher = session.getDispatcher();

        Map<String, Object> svcCtx = FastMap.newInstance();
        svcCtx.put("userLogin", session.getUserLogin());
        svcCtx.put("orderId", orderId);
        svcCtx.put("skipInventoryChecks", Boolean.TRUE);
        svcCtx.put("skipProductChecks", Boolean.TRUE);

        Map<String, Object> svcRes = null;
        try {
            svcRes = dispatcher.runSync("loadCartFromOrder", svcCtx);
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        }

        if (svcRes == null) {
            Debug.logInfo(UtilProperties.getMessage("EcommerceUiLabels", "EcommerceNoShoppingListsCreate", locale), module);
        } else if (ServiceUtil.isError(svcRes)) {
            Debug.logError(ServiceUtil.getErrorMessage(svcRes) + " - " + svcRes, module);
        } else {
            ShoppingCart restoredCart = (ShoppingCart) svcRes.get("shoppingCart");
            if (append) {
                // TODO: add stuff to append items
                this.setCart(restoredCart);
                this.orderId = orderId;
            } else {
                this.setCart(restoredCart);
                this.orderId = orderId;
            }
            this.ch = new CheckOutHelper(session.getDispatcher(), session.getDelegator(), getCart());
            if (session.getUserLogin() != null) {
                getCart().addAdditionalPartyRole(session.getUserLogin().getString("partyId"), "SALES_REP");
            }
            getCart().setFacilityId(facilityId);
            getCart().setTerminalId(terminalId);
            getCart().setOrderId(orderId);
            return true;
        }
        return false;
    }

    public boolean clearList(String shoppingListId) {
        Delegator delegator = session.getDelegator();
        try {
            ShoppingListEvents.clearListInfo(delegator, shoppingListId);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
        return true;
    }

    public String addProductPromoCode(String code) {
        trace("Add a promo code");
        LocalDispatcher dispatcher = session.getDispatcher();
        String result = getCart().addProductPromoCode(code, dispatcher);
        calcTax();
        return result;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
