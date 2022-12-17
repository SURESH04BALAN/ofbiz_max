/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.htmlsummarypages;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
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
import org.ofbiz.base.util.StringUtil;
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
import org.ofbiz.ordermax.entity.OrderStatus;
import org.ofbiz.ordermax.orderbase.StatusSingleton;

import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.widget.fo.FoFormRenderer;
import org.ofbiz.widget.fo.FoScreenRenderer;
import org.ofbiz.widget.screen.ScreenRenderer;

/**
 *
 * @author BBS Auctions
 */
public class OrderContactInfoScreenlet implements HtmlScreenletInterface {

    public static final String module = OrderContactInfoScreenlet.class.getName();
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

        List<GenericValue> orderHeaderStatuses = new ArrayList<GenericValue>();
        for (OrderStatus status : order.getOrderStatusList().getList()) {
            orderHeaderStatuses.add(status.getGenericValueObj());
        }

        root.put("setOrderCompleteOption", false);
        root.put("externalKeyParam", "test Hello");
        root.put("orderHeaderStatuses", orderHeaderStatuses);
        if (uiLabelMap.containsKey("OrderOrder")) {
            Debug.logInfo("Key : " + "OrderOrder" + " Value: " + uiLabelMap.get("OrderOrder"), module);
        } else {
            Debug.logInfo("Key : " + "OrderOrder" + " Value: " + "Not found", module);
            uiLabelMap.put("OrderOrder", "Sales Order");
        }

        uiLabelMap.addBottomResourceBundle("OrderUiLabels");
        uiLabelMap.addBottomResourceBundle("CommonUiLabels");

        List<Map<String, GenericValue>> orderContactMechValueMaps = ContactMechWorker.getOrderContactMechValueMaps(XuiContainer.getSession().getDelegator(), order.orderHeader.getOrderId());
        orderContactMechValueMaps.clear();
        root.put("orderContactMechValueMaps", orderContactMechValueMaps);
        root.put("displayParty", order.getBillToAccount().getParty().getParty().getGenericValueObj());
        root.put("dispatcher", XuiContainer.getSession().getDispatcher());
        root.put("userLogin", XuiContainer.getSession().getUserLogin());

        root.put("security", XuiContainer.getSession().getDispatcher().getSecurity());
        root.put("session", XuiContainer.getSession().getUserLogin());
        root.put("partyId", order.getBillToAccount().getParty().getParty().getpartyId());
        root.put("customerDetailLink", "/partymgr/control/viewprofile?partyId=");

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
        /* Get the template (uses cache internally) */
//            "order/webapp/ordermgr/order/orderinfo.ftl"
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\ordercontactinfo.ftl");
        try {
            FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(), root, out);
        } catch (Exception e) {
            Debug.logFatal("Unable to create - " + "orderinfo.ftl", module);
            Debug.logError(e, module);

        }
        //Debug.logInfo("out : " + out, module);
        return out.toString();
    }

    @Override
    public String getScreenletHtml(Map root) {
        Map rootVal = new HashMap();
        rootVal.putAll(root);
        rootVal.put("customerDetailLink", "/partymgr/control/viewprofile?partyId=");
        rootVal.put("session", XuiContainer.getSession().getUserLogin());
        
        Writer out = new StringWriter();
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\ordercontactinfo.ftl");
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
