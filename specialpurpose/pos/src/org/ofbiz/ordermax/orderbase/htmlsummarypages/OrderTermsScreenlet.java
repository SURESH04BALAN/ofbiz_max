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
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.base.util.collections.ResourceBundleMapWrapper;
import org.ofbiz.base.util.template.FreeMarkerWorker;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.order.order.OrderContentWrapper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.entity.OrderStatus;
import org.ofbiz.ordermax.entity.OrderTerm;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import static org.ofbiz.ordermax.orderbase.htmlsummarypages.OrderContactInfoScreenlet.module;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;
import org.ofbiz.party.contact.ContactMechWorker;

/**
 *
 * @author BBS Auctions
 */
public class OrderTermsScreenlet implements HtmlScreenletInterface {

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

        List<GenericValue> orderTerms = new ArrayList<GenericValue>();
        for (OrderTerm orderTerm :  order.getOrderTermList().getList()) {
            orderTerms.add(orderTerm.getGenericValueObj());
        }

        root.put("setOrderCompleteOption", false);
        root.put("externalKeyParam", "test Hello");
        root.put("orderTerms", orderTerms);
        if (uiLabelMap.containsKey("OrderOrder")) {
            Debug.logInfo("Key : " + "OrderOrder" + " Value: " + uiLabelMap.get("OrderOrder"), module);
        } else {
            Debug.logInfo("Key : " + "OrderOrder" + " Value: " + "Not found", module);
            uiLabelMap.put("OrderOrder", "Sales Order");
        }

        uiLabelMap.addBottomResourceBundle("OrderUiLabels");
        uiLabelMap.addBottomResourceBundle("CommonUiLabels");

        List<Map<String, GenericValue>> orderContactMechValueMaps = ContactMechWorker.getOrderContactMechValueMaps(XuiContainer.getSession().getDelegator(), order.orderHeader.getOrderId());
        root.put("orderContactMechValueMaps", orderContactMechValueMaps);
        root.put("displayParty", order.getBillToAccount().getParty().getParty().getGenericValueObj());
        root.put("dispatcher", XuiContainer.getSession().getDispatcher());
        root.put("userLogin", XuiContainer.getSession().getUserLogin());

//        root.put("security", XuiContainer.getSession().getDispatcher().getSecurity());
        root.put("session", XuiContainer.getSession().getUserLogin());
        root.put("partyId", order.getBillToAccount().getParty().getParty().getpartyId());
        root.put("customerDetailLink","/partymgr/control/viewprofile?partyId=" );

        /* Get the template (uses cache internally) */
//            "order/webapp/ordermgr/order/orderinfo.ftl"
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\orderterms.ftl");
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
                File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\orderterms.ftl");
        try {
            FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(), root, out);
        } catch (Exception e) {
            Debug.logFatal("Unable to create - " + "orderinfo.ftl", module);
            Debug.logError(e, module);

        }
        //Debug.logInfo("out : " + out, module);
       return out.toString();
    }
}
