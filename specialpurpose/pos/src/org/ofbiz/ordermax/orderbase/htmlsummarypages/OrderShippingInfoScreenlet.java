/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.htmlsummarypages;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.base.util.collections.MapStack;
import org.ofbiz.base.util.collections.ResourceBundleMapWrapper;
import org.ofbiz.base.util.template.FreeMarkerWorker;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.order.order.OrderContentWrapper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.entity.OrderItemShipGroup;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import static org.ofbiz.ordermax.orderbase.htmlsummarypages.OrderContactInfoScreenlet.module;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.widget.fo.FoFormRenderer;
import org.ofbiz.widget.fo.FoScreenRenderer;
import org.ofbiz.widget.screen.ScreenRenderer;

/**
 *
 * @author BBS Auctions
 */
public class OrderShippingInfoScreenlet implements HtmlScreenletInterface {

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
            root.put("productStore", order.getOrderReadHelper().getProductStore());

            List<GenericValue> productStoreShipmentMethList = XuiContainer.getSession().getDelegator().findByAndCache(
                    "ProductStoreShipmentMethView",
                    UtilMisc.toMap("productStoreId", order.getOrderReadHelper().getProductStoreId()),
                    UtilMisc.toList("sequenceNumber"));
            root.put("productStoreShipmentMethList", productStoreShipmentMethList);
//                    [productStoreId: productStore.productStoreId], ['sequenceNumber']);
        } catch (Exception ex) {
            Logger.getLogger(OrderInfoScreenlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        GenericValue orderItem = null;
        List<OrderItemComposite> items = order.getOrderItemsList().getList();
        for (OrderItemComposite item : items) {

            orderItem = item.getOrderItem().getGenericValueObj();
            break;
        }
        root.put("orderItem", orderItem);

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

        List<GenericValue> shipGroups = new ArrayList<GenericValue>();
        for (OrderItemShipGroup orderItemShipGroup : order.getOrderItemShipGroupList().getList()) {
            shipGroups.add(orderItemShipGroup.getGenericValueObj());
        }

        root.put("setOrderCompleteOption", false);
        root.put("externalKeyParam", "test Hello");
        root.put("shipGroups", shipGroups);
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
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\ordershippinginfo.ftl");
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
        Map rootVal = new HashMap();
        rootVal.putAll(root);
        rootVal.put("customerDetailLink", "/partymgr/control/viewprofile?partyId=");
        rootVal.put("session", XuiContainer.getSession().getUserLogin());
        
        Writer out = new StringWriter();
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\ordershippinginfo.ftl");
        try {
            FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(), rootVal, out);
        } catch (Exception e) {
            Debug.logFatal("Unable to create - " + "orderinfo.ftl", module);
            Debug.logError(e, module);

        }
        //Debug.logInfo("out : " + out, module);
        return out.toString();
    }
}
