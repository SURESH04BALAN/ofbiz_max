/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.htmlsummarypages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.IOException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderEnteryPanel1;
import static org.ofbiz.ordermax.orderbase.salesorder.SalesOrderEnteryPanel1.module;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;

/**
 *
 * @author BBS Auctions
 */
public class OrderInfoScreenlet implements HtmlScreenletInterface {

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

        uiLabelMap.addBottomResourceBundle("AccountingUiLabels");
        uiLabelMap.addBottomResourceBundle("CommonUiLabels");

        /* Get the template (uses cache internally) */
//            "order/webapp/ordermgr/order/orderinfo.ftl"
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\orderinfo.ftl");
        try {
            FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(), root, out);
        } catch (Exception e) {
            Debug.logFatal("Unable to create - " + "orderinfo.ftl", module);

        }

        return out.toString();
    }
    /*    @Override
     public String getScreenletHtml(Order order) {

     StringBuilder orderToStringBuilder = new StringBuilder();
     orderToStringBuilder.append("<div class=\"screenlet order-info\">");
     orderToStringBuilder.append("    <div class=\"screenlet-title-bar\">");
     orderToStringBuilder.append("        <ul>");
     orderToStringBuilder.append("            <li class=\"h3\">&nbsp;Sales Order&nbsp;Nbr&nbsp;<a href=\"/ordermgr/control/orderview?orderId=replace_order_id\">replace_order_id</a>  [&nbsp;<a href=\"/ordermgr/control/order.pdf?orderId=replace_order_id\" target=\"_blank\">PDF</a>&nbsp;]</li>");
     //orderToStringBuilder.append(getHeaderLine(order));
     orderToStringBuilder.append("        </ul>");
     orderToStringBuilder.append("        <br class=\"clear\"/>");
     orderToStringBuilder.append("    </div>");
     orderToStringBuilder.append("    <div class=\"screenlet-body\">");
     orderToStringBuilder.append("        <table class=\"basic-table\" cellspacing='0'>");
     orderToStringBuilder.append("            <tr>");
     orderToStringBuilder.append("              <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Status History</td>");
     orderToStringBuilder.append("              <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\" class=\"COMPLETED\">");
     orderToStringBuilder.append("                <span class=\"current-status\">Current Status: Completed</span>");
     orderToStringBuilder.append("                  <hr />");
     if(order!=null){
     System.out.println("order status size: " + order.getOrderStatusList().getList().size());
     for (OrderStatus status : order.getOrderStatusList().getList()) {
     try {
     String str = StatusSingleton.getStatusItem(status.getstatusId()).getdescription() + " - " + status.getstatusDatetime().toString();
     orderToStringBuilder.append("                    <div>");
     orderToStringBuilder.append(str);                    
     orderToStringBuilder.append("                      &nbsp;");
     str = "By -  [" + status.getstatusUserLogin() + "]";
     orderToStringBuilder.append(str);
     orderToStringBuilder.append("                    </div>");                    
     } catch (Exception ex) {
     Logger.getLogger(OrderSummaryScreenlet.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     }
     orderToStringBuilder.append("              </td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("            <tr>");
     orderToStringBuilder.append("              <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Date Ordered</td>");
     orderToStringBuilder.append("              <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">replace_order_date</td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("            <tr>");
     orderToStringBuilder.append("              <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Currency</td>");
     orderToStringBuilder.append("              <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">replace_order_currency</td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("            <tr>");
     orderToStringBuilder.append("              <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Sales Channel</td>");
     orderToStringBuilder.append("              <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                    replace_sales_channel\n");
     orderToStringBuilder.append("              </td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("              <tr>");
     orderToStringBuilder.append("                <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Product Store</td>");
     orderToStringBuilder.append("                <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("                <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                  replace_online_storename&nbsp;<a href=\"/catalog/control/EditProductStore?productStoreId=replace_online_storeid&amp;amp&#59;externalLoginKey&#61;EL153368419754\" target=\"catalogmgr\" class=\"buttontext\">(replace_online_storeid)</a>");
     orderToStringBuilder.append("                </td>");
     orderToStringBuilder.append("              </tr>");
     orderToStringBuilder.append("              <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("            <tr>");
     orderToStringBuilder.append("              <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Origin Facility</td>");
     orderToStringBuilder.append("              <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                    <a href=\"/facility/control/EditFacility?facilityId=VISHAL_FAC&amp;amp&#59;externalLoginKey&#61;EL153368419754\" target=\"facilitymgr\" class=\"buttontext\">replace_origin_facility</a>");
     orderToStringBuilder.append("              </td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("            <tr>");
     orderToStringBuilder.append("              <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Created By</td>");
     orderToStringBuilder.append("              <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                    <a href=\"/partymgr/control/viewprofile?userlogin_id=replace_created_by&amp;amp&#59;externalLoginKey&#61;EL153368419754\" target=\"partymgr\" class=\"buttontext\">replace_created_by</a>");
     orderToStringBuilder.append("              </td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("                <tr>");
     orderToStringBuilder.append("                  <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Priority</td>");
     orderToStringBuilder.append("                  <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("                  <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                     <form name=\"setOrderReservationPriority\" method=\"post\" action=\"/ordermgr/control/setOrderReservationPriority\">");
     orderToStringBuilder.append("                     <input type = \"hidden\" name=\"orderId\" value=\"replace_order_id\"/>");
     orderToStringBuilder.append("                    <select name=\"priority\">");
     orderToStringBuilder.append("                      <option value=\"1\" >High</option>");
     orderToStringBuilder.append("                      <option value=\"2\" selected=\"selected\" >Normal</option>");
     orderToStringBuilder.append("                      <option value=\"3\" >Low</option>");
     orderToStringBuilder.append("                    </select>");
     orderToStringBuilder.append("                    <input type=\"submit\" class=\"smallSubmit\" value=\"Reserve Inventory\"/>");
     orderToStringBuilder.append("                    </form>");
     orderToStringBuilder.append("                  </td>");
     orderToStringBuilder.append("                </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("            <tr>");
     orderToStringBuilder.append("              <td align=\"right\" valign=\"top\" width=\"15%\" class=\"label\">&nbsp;Invoice Per Shipment</td>");
     orderToStringBuilder.append("              <td width=\"5%\">&nbsp;</td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                 <form name=\"setInvoicePerShipment\" method=\"post\" action=\"/ordermgr/control/setInvoicePerShipment\">");
     orderToStringBuilder.append("                 <input type = \"hidden\" name=\"orderId\" value=\"replace_order_id\"/>");
     orderToStringBuilder.append("                <select name=\"invoicePerShipment\">");
     orderToStringBuilder.append("                  <option value=\"Y\" selected=\"selected\" >Yes</option>");
     orderToStringBuilder.append("                  <option value=\"N\" >No</option>");
     orderToStringBuilder.append("                </select>");
     orderToStringBuilder.append("                <input type=\"submit\" class=\"smallSubmit\" value=\"Update\"/>");
     orderToStringBuilder.append("                </form>");
     orderToStringBuilder.append("              </td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr><td colspan=\"3\"><hr /></td></tr>");
     orderToStringBuilder.append("            <tr id=\"isViewed\">");
     orderToStringBuilder.append("              <td class=\"label\">Mark Viewed</td>");
     orderToStringBuilder.append("              <td width=\"5%\"></td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                <form id=\"orderViewed\" action=\"\">");
     orderToStringBuilder.append("                  <input type=\"checkbox\" name=\"checkViewed\" onclick=\"javascript:markOrderViewed();\"/>");
     orderToStringBuilder.append("                  <input type=\"hidden\" name=\"orderId\" value=\"replace_order_id\"/>");
     orderToStringBuilder.append("                  <input type=\"hidden\" name=\"isViewed\" value=\"Y\"/>");
     orderToStringBuilder.append("                </form>");
     orderToStringBuilder.append("              </td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("            <tr id=\"viewed\" style=\"display: none;\">");
     orderToStringBuilder.append("              <td class=\"label\">Viewed</td>");
     orderToStringBuilder.append("              <td width=\"5%\"></td>");
     orderToStringBuilder.append("              <td valign=\"top\" width=\"80%\">");
     orderToStringBuilder.append("                Yes\n");
     orderToStringBuilder.append("              </td>");
     orderToStringBuilder.append("            </tr>");
     orderToStringBuilder.append("        </table>");
     orderToStringBuilder.append("    </div>");
     orderToStringBuilder.append("</div>");
     //        orderToStringBuilder.append();
     String strOut = null;
     if (order != null) {
     // replaceString(orderToStringBuilder, "replace_order_id", order.getOrderId());
     String orderDate = order.getOrderDate().toString();
     String orderCurrency = order.getCurrency();
     String salesChannel = order.getChannelType();
     String replace_online_storename = "";
     try {
     replace_online_storename = org.ofbiz.ordermax.product.productstore.ProductStoreSingleton.getProductStore(order.getProductStoreId()).getstoreName();
     } catch (Exception ex) {
     Logger.getLogger(OrderSummaryScreenlet.class.getName()).log(Level.SEVERE, null, ex);
     }
     String replace_online_storeid = order.getProductStoreId();
     String replace_origin_facility = order.getFacilityId();
     String replace_created_by = order.getCreatedBy();
     String replace_Current_Status = "";
     try {
     replace_Current_Status = StatusSingleton.getStatusItem(order.getOrderStatusId()).getdescription();
     } catch (Exception ex) {
     Logger.getLogger(OrderSummaryScreenlet.class.getName()).log(Level.SEVERE, null, ex);
     }
     strOut = org.apache.commons.lang3.StringUtils.replaceEach(orderToStringBuilder.toString(),
     new String[]{"replace_order_id",
     "replace_order_date",
     "replace_order_currency",
     "replace_sales_channel",
     "replace_online_storename",
     "replace_online_storeid",
     "replace_origin_facility",
     "replace_created_by",
     "replace_Current_Status"},
     new String[]{order.getOrderId(),
     orderDate,
     orderCurrency,
     salesChannel,
     replace_online_storename,
     replace_online_storeid,
     replace_origin_facility,
     replace_created_by,
     replace_Current_Status});
            
     //order status
     System.out.println("order status size: " + order.getOrderStatusList().getList().size());
     for (OrderStatus status : order.getOrderStatusList().getList()) {
     try {
     System.out.println(StatusSingleton.getStatusItem(status.getstatusId()).getdescription() + "  " + status.getstatusDatetime().toString());
     } catch (Exception ex) {
     Logger.getLogger(OrderSummaryScreenlet.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     } else {
     strOut = orderToStringBuilder.toString();
     }
     return strOut;
     }
     */

    static String getHeaderLine(Order order) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        orderToStringBuilder.append("<li class=\"h3\">&nbsp;");
//        orderToStringBuilder.re
        if (order != null) {
            orderToStringBuilder.append("Sales Order");
            String val = "&nbsp;Nbr&nbsp;<a href=\"/ordermgr/control/orderview?orderId=replace_order_id\">replace_order_id</a>  [&nbsp;<a href=\"/ordermgr/control/order.pdf?orderId=replace_order_id\" target=\"_blank\">PDF</a>&nbsp;]</li>";
            val = val.replaceAll("replace_order_id", order.getOrderId());
            orderToStringBuilder.append(val);
        } else {
            orderToStringBuilder.append("Order Not set");
        }

//        orderToStringBuilder.append();
        return orderToStringBuilder.toString();
    }

    static public String replaceString(StringBuilder sb, String findString, String replaceStr) {

//        StringBuilder sb = new StringBuilder();
//        sb.append("This works with StringBuffers");
        Pattern p = Pattern.compile(findString);
        Matcher m = p.matcher(sb);
        System.out.println(m.replaceAll(replaceStr));
        return sb.toString();
    }

    @Override
    public String getScreenletHtml(Map root) {
        /* Merge data-model with template */
        Writer out = new StringWriter();
        Map rootVal = new HashMap();
        rootVal.putAll(root);
        GenericValue orderHeader = (GenericValue) root.get("OrderHeader");
        if (orderHeader != null) {
            OrderContentWrapper orderWrapper = new OrderContentWrapper(
                    XuiContainer.getSession().getDispatcher(), orderHeader,
                    Locale.getDefault(), "text/xml");

            rootVal.put("orderContentWrapper", orderWrapper);
        }
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\orderinfo.ftl");
        try {
            FreeMarkerWorker.renderTemplate(UtilURL.fromFilename(templateFile.getAbsolutePath()).toExternalForm(), rootVal, out);
        } catch (Exception e) {
            Debug.logFatal("Unable to create - " + "orderinfo.ftl", module);
            Debug.logError(e, module);

        }
//        Debug.logInfo("out : " + out, module);
        return out.toString();
    }
}
