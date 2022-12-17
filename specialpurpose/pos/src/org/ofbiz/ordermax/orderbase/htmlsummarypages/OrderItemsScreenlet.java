/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.htmlsummarypages;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.base.util.collections.MapStack;
import org.ofbiz.base.util.collections.ResourceBundleMapWrapper;
import org.ofbiz.base.util.template.FreeMarkerWorker;
import org.ofbiz.base.util.template.XslTransform;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.order.order.OrderContentWrapper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.widget.fo.FoFormRenderer;
import org.ofbiz.widget.fo.FoScreenRenderer;
import org.ofbiz.widget.screen.ScreenRenderer;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author BBS Auctions
 */
public class OrderItemsScreenlet implements HtmlScreenletInterface {

    public static final String module = OrderItemsScreenlet.class.getName();
    protected static final org.ofbiz.widget.html.HtmlScreenRenderer htmlScreenRenderer = new org.ofbiz.widget.html.HtmlScreenRenderer();
    protected static final FoScreenRenderer foScreenRenderer = new FoScreenRenderer();
    protected static final FoFormRenderer foFormRenderer = new FoFormRenderer();

    @Override
    public String getScreenletHtml(Order order) {
        /* Merge data-model with template */
        Writer out = new StringWriter();

        /* Create a data-model */
        Map root = new HashMap();

        ResourceBundleMapWrapper uiLabelMap = UtilProperties.getResourceBundleMap("OrderUiLabels", Locale.getDefault());
        root.put("uiLabelMap", uiLabelMap);
        root.put("orderHeader", order.orderHeader.getGenericValueObj());
        root.put("locale", Locale.getDefault()/*order.orderHeader.getGenericValueObj().getRelatedOne("OrderType")*/);
        root.put("orderId", order.getOrderId());
        root.put("timeZone", TimeZone.getDefault());
        try {
            root.put("productStore", ProductStoreSingleton.getProductStore(order.getProductStoreId()).getGenericValueObj());
        } catch (Exception ex) {
            Logger.getLogger(OrderInfoScreenlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // get inventory summary for each shopping cart product item
        /*       GenericValue orderItem = null;
         List<OrderItemComposite> items = order.getOrderItemsList().getList();
         for (OrderItemComposite item : items) {

         orderItem = item.getOrderItem().getGenericValueObj();
         break;
         }
         root.put("orderItem", orderItem);
         */
        OrderContentWrapper orderWrapper = new OrderContentWrapper(
                XuiContainer.getSession().getDispatcher(), order.orderHeader.getGenericValueObj(),
                Locale.getDefault(), "text/xml");

        root.put("orderContentWrapper", orderWrapper);
        System.out.println("order status size: " + order.getOrderStatusList().getList().size());
        try {
            root.put("currentStatus", StatusSingleton.getStatusItem(order.orderHeader.getStatusId()).getGenericValueObj());
        } catch (Exception ex) {
            Logger.getLogger(OrderInfoScreenlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<GenericValue> orderHeaderAdjustments = new ArrayList<GenericValue>();
        for (GenericValue orderHeaderAdjustment : order.getOrderReadHelper().getOrderHeaderAdjustments()) {
            orderHeaderAdjustments.add(orderHeaderAdjustment);
        }

        List<GenericValue> orderItemList = new ArrayList<GenericValue>();
        for (GenericValue orderItem : order.getOrderReadHelper().getOrderItems()) {
            orderItemList.add(orderItem);
        }
        Map<Object, List<GenericValue>> itemIssuancesPerItem = new HashMap<Object, List<GenericValue>>();
        for (GenericValue orderItem : orderItemList) {
            List<GenericValue> items = order.getOrderReadHelper().getOrderItemIssuances(orderItem);
            itemIssuancesPerItem.put(orderItem.get("orderItemSeqId"), items);
        }

        Map<String, Object> inventorySummary;
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            try {
                inventorySummary = XuiContainer.getSession().getDispatcher().runSync("getProductInventorySummaryForItems",
                        UtilMisc.toMap("orderItems", orderItemList));
                root.put("availableToPromiseMap", inventorySummary.get("availableToPromiseMap"));
                root.put("quantityOnHandMap", inventorySummary.get("quantityOnHandMap"));
                root.put("mktgPkgATPMap", inventorySummary.get("mktgPkgATPMap"));
                root.put("mktgPkgQOHMap", inventorySummary.get("mktgPkgQOHMap"));
                root.put("requiredProductQuantityMap", FastMap.newInstance());
                root.put("onOrderProductQuantityMap", FastMap.newInstance());
            } catch (GenericServiceException ex) {
                Logger.getLogger(OrderItemsScreenlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
//                pos.showMessageDialog("dialog/error/exception", e2.getMessage());
            }
//            pos.showMessageDialog("dialog/error/exception", e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
//                pos.showMessageDialog("dialog/error/exception", e.getMessage());
            }
        }

        root.put("setOrderCompleteOption", false);
        root.put("externalKeyParam", "test Hello");
        root.put("orderHeaderAdjustments", orderHeaderAdjustments);
        root.put("orderItemList", orderItemList);
        root.put("itemIssuancesPerItem", itemIssuancesPerItem);

        root.put("orderSubTotal", order.getOrderReadHelper().getOrderItemsSubTotal());
        root.put("otherAdjAmount", order.getOrderReadHelper().getOrderAdjustmentsTotal());
        root.put("shippingAmount", order.getOrderReadHelper().getShippingTotal());
        root.put("taxAmount", order.getOrderReadHelper().getTaxTotal());
        root.put("grandTotal", order.getOrderReadHelper().getOrderGrandTotal());

        if (uiLabelMap.containsKey("OrderOrder")) {
            Debug.logInfo("Key : " + "OrderOrder" + " Value: " + uiLabelMap.get("OrderOrder"), module);
        } else {
            Debug.logInfo("Key : " + "OrderOrder" + " Value: " + "Not found", module);
            uiLabelMap.put("OrderOrder", "Sales Order");
        }

//        uiLabelMap.addBottomResourceBundle("OrderUiLabels");
        uiLabelMap.addBottomResourceBundle("CommonUiLabels");
        uiLabelMap.addBottomResourceBundle("AccountingUiLabels");

        List<Map<String, GenericValue>> orderContactMechValueMaps = ContactMechWorker.getOrderContactMechValueMaps(XuiContainer.getSession().getDelegator(), order.orderHeader.getOrderId());
        root.put("orderContactMechValueMaps", orderContactMechValueMaps);
        root.put("displayParty", order.getBillToAccount().getParty().getParty().getGenericValueObj());
        root.put("dispatcher", XuiContainer.getSession().getDispatcher());
        root.put("userLogin", XuiContainer.getSession().getUserLogin());
        root.put("orderReadHelper", order.getOrderReadHelper());
        root.put("orderTypeId", order.orderHeader.getOrderTypeId());

        root.put("security", XuiContainer.getSession().getDispatcher().getSecurity());
        root.put("session", XuiContainer.getSession().getUserLogin());
        root.put("partyId", order.getBillToAccount().getParty().getParty().getpartyId());
        root.put("customerDetailLink", "/partymgr/control/viewprofile?partyId=");
        root.put("orderPaymentPreferences", order.getOrderReadHelper().getPaymentPreferences());
        root.put("billingAccount", order.getOrderReadHelper().getBillingAccount());
        root.put("invoices", order.getInvoiceIds());
        root.put("externalLoginKey", "test_externalLoginKey");
        root.put("paramString", "paramString");

        MapStack<String> screenContextTmp = MapStack.create();
        screenContextTmp.put("locale", Locale.getDefault());
        screenContextTmp.put("delegator", XuiContainer.getSession().getDelegator());

        MapStack<String> screenContext = MapStack.create();
        screenContext.put("locale", Locale.getDefault());
        screenContext.put("delegator", XuiContainer.getSession().getDelegator());

//        BeansWrapper defaultOfbizWrapper = BeansWrapper.getDefaultInstance();
        //screenContext.put("context", new BeanModel(new HashMap<Object, Object>(), defaultOfbizWrapper));
        org.ofbiz.widget.screen.ScreenRenderer screens = new org.ofbiz.widget.screen.ScreenRenderer(out, screenContext, foScreenRenderer);
        screens.populateContextForService(XuiContainer.getSession().getDispatcher().getDispatchContext(), screenContext);
        screenContextTmp.putAll(screenContext);

        ScreenRenderer screensAtt = new ScreenRenderer(out, screenContextTmp, foScreenRenderer);
        screensAtt.getContext().put("formStringRenderer", foFormRenderer);
        screensAtt.getContext().put("postalAddressTemplate", "PostalAddress.ftl");
        root.put("screens", screensAtt);

        root.put("delegator", XuiContainer.getSession().getDelegator());
        root.put("postalAddressTemplate", "PostalAddress.ftl");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("dispatcher", XuiContainer.getSession().getDispatcher());
        Map<String, BigDecimal> returnQuantityMap = order.getOrderReadHelper().getOrderItemReturnedQuantities();
        root.put("returnQuantityMap", returnQuantityMap);
        root.put("orderAdjustments", order.getOrderReadHelper().getAdjustments());
//        List<GenericValue> getAdjustments()
//        request.setAttribute("dispatcher", XuiContainer.getSession().getDispatcher());

        root.put("request", request);
        /*  javax.servlet.http.HttpServletRequest mock = new javax.servlet.http.HttpServletRequestWrapper();/* {
            
         private final Map<String, String[]> params = new HashMap<String, String[]>();

         public Map<String, String[]> getParameterMap() {
         return params;
         }

         public String getParameter(String name) {
         String[] matches = params.get(name);
         if (matches == null || matches.length == 0) {
         return null;
         }
         return matches[0];
         }

         // TODO *many* methods to implement here
         };
         */
        /*List<GenericValue> orderItemBillings;
         try {
         orderItemBillings = order.orderHeader.getGenericValueObj().getRelated("OrderItemBilling");
         Set<String> invoiceIds = new HashSet<String>();
         for (Iterator<GenericValue> iter = orderItemBillings.iterator(); iter.hasNext();) {
         GenericValue orderItemBilling = iter.next();
         invoiceIds.add(orderItemBilling.getString("invoiceId"));
         Debug.logInfo("invoiceId : " +  orderItemBilling.getString("invoiceId"),module);
         //                break;
         }
         Debug.logInfo("invoiceId s list size : " +  orderItemBillings.size(),module);
         orderHeader.setInvoiceIds(invoiceIds);
         } catch (GenericEntityException ex) {
         Logger.getLogger(OrderEntryHelper.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        /* Get the template (uses cache internally) */
//            "order/webapp/ordermgr/order/orderinfo.ftl"
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\orderitems.ftl");
        try {
            FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(), root, out);
        } catch (Exception e) {
            Debug.logFatal("Unable to create - " + "orderinfo.ftl", module);

        }
        Debug.logInfo("out : " + out, module);
        return out.toString();
    }


    @Override
    public String getScreenletHtml(Map root) {
               /* Merge data-model with template */
        Writer out = new StringWriter();
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\orderitems.ftl");
//        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\accounting\\widget\\InvoiceForms.xml");
        try {
            FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(),  root, out);
            //XslTransform.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(), "invoiceHeader");
        } catch (Exception e) {
            Debug.logFatal("Unable to create - " + "InvoiceForms.xml", module);
            Debug.logError(e, module);

        }
        //Debug.logInfo("out : " + out, module);
       return out.toString();
    }

    
    
}
