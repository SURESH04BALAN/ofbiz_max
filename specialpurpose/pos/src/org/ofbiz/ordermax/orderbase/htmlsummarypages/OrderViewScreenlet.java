/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.htmlsummarypages;

import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.ScriptUtil;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.collections.MapStack;
import org.ofbiz.base.util.collections.ResourceBundleMapWrapper;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.webapp.event.EventHandlerException;
import org.ofbiz.widget.fo.FoFormRenderer;
import org.ofbiz.widget.fo.FoScreenRenderer;
import org.ofbiz.widget.screen.ScreenRenderer;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author BBS Auctions
 */
public class OrderViewScreenlet implements HtmlScreenletInterface {

    public static final String module = OrderViewScreenlet.class.getName();
    protected static final org.ofbiz.widget.html.HtmlScreenRenderer htmlScreenRenderer = new org.ofbiz.widget.html.HtmlScreenRenderer();
    protected static final FoScreenRenderer foScreenRenderer = new FoScreenRenderer();
    protected static final FoFormRenderer foFormRenderer = new FoFormRenderer();

    public void setOrderSummary() {
// add a HTMLEditorKit to the editor pane
       /* HTMLEditorKit kit = new HTMLEditorKit();
         editBillingAddress2.setEditorKit(kit);
         // add some styles to the html

         URL url;
         try {
         url = new File("C:\\backup\\ofbiz-12.04.02\\themes\\flatgrey\\webapp\\flatgrey\\maincss.css").toURI().toURL();

         StyleSheet styleSheet = kit.getStyleSheet();
         styleSheet.importStyleSheet(url);

         } catch (MalformedURLException ex) {
         Logger.getLogger(SalesOrderEnteryPanel1.class.getName()).log(Level.SEVERE, null, ex);
         }

         Document doc = kit.createDefaultDocument();
         editBillingAddress2.setDocument(doc);
         */
//editBillingAddress2.setText(htmlString);

    }

    @Override
    public String getScreenletHtml(Order order) {
        if (UtilValidate.isNotEmpty(order) && UtilValidate.isNotEmpty(order.getOrderId())) {
            Bindings result = null;
            try {
                result = invoke(order);
//            Debug.logInfo("resut : " + result, module);
            } catch (EventHandlerException ex) {
                Logger.getLogger(OrderViewScreenlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            /* Merge data-model with template */
            Writer out = new StringWriter();

            /* Create a data-model */
            Map<String, Object> root = new HashMap<String, Object>();

            ResourceBundleMapWrapper uiLabelMap = UtilProperties.getResourceBundleMap("OrderUiLabels", Locale.getDefault());
            uiLabelMap.addBottomResourceBundle("CommonUiLabels");
            uiLabelMap.addBottomResourceBundle("AccountingUiLabels");
            uiLabelMap.addBottomResourceBundle("ProductUiLabels");
            root.put("uiLabelMap", uiLabelMap);
//        root.putAll(result);
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

            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setAttribute("dispatcher", XuiContainer.getSession().getDispatcher());
            root.putAll(result);
            root.put("request", request);
            root.put("externalLoginKey", " ");
            root.put("paramString", " ");
            root.put("externalKeyParam", " ");
            Map<String, Object> values = (Map) result.get("widget");
            root.putAll(values);
            root.put("OrderHeader", order.orderHeader.getGenericValueObj());
            Map<String, Object> inventorySummary;
            boolean beganTransaction = false;
            try {
                beganTransaction = TransactionUtil.begin();

                try {
                    inventorySummary = XuiContainer.getSession().getDispatcher().runSync("getProductInventorySummaryForItems",
                            UtilMisc.toMap("orderItems", result.get("orderItems")));
                    root.put("availableToPromiseMap", inventorySummary.get("availableToPromiseMap"));
                    root.put("quantityOnHandMap", inventorySummary.get("quantityOnHandMap"));
                    root.put("mktgPkgATPMap", inventorySummary.get("mktgPkgATPMap"));
                    root.put("mktgPkgQOHMap", inventorySummary.get("mktgPkgQOHMap"));
                    root.put("requiredProductQuantityMap", FastMap.newInstance());
                    root.put("onOrderProductQuantityMap", FastMap.newInstance());
                    root.put("productionProductQuantityMap", values.get("productionProductQuantityMap"));

                } catch (GenericServiceException ex) {
                    Logger.getLogger(OrderViewScreenlet.class.getName()).log(Level.SEVERE, null, ex);
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
//        Map<String, Object> values = (Map) result.get("widget");
        /*for (Map.Entry<String, Object> entryDept : root.entrySet()) {
             if (entryDept.getValue() != null) {
             Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
             } else {
             Debug.logInfo("Key : " + entryDept.getKey() + " Value: null", module);
             }
             }*/
            OrderItemsScreenlet orderItemsScreenlet = new OrderItemsScreenlet();
            OrderInfoScreenlet orderSummaryScreenlet = new OrderInfoScreenlet();
            OrderContactInfoScreenlet contactInformationScreenlet = new OrderContactInfoScreenlet();
            OrderTermsScreenlet orderTermsScreenlet = new OrderTermsScreenlet();
            OrderPaymentInfoScreenlet paymentInformationScreenlet = new OrderPaymentInfoScreenlet();
            OrderShippingInfoScreenlet shipmentInformationScreenlet = new OrderShippingInfoScreenlet();
            OrderNotesScreenlet orderNotesScreenlet = new OrderNotesScreenlet();
            OrderSalesRepsScreenlet orderSalesRepsScreenlet = new OrderSalesRepsScreenlet();
            StringBuilder orderToStringBuilder = new StringBuilder();

            orderToStringBuilder.append("<html>");
            orderToStringBuilder.append("<head>");
            orderToStringBuilder.append("<title>The slash at the end of the href is important!</title>");
            orderToStringBuilder.append("<base href=\"C:\\backup\\ofbiz-12.04.02\\themes\\flatgrey\\webapp\\flatgrey\\maincss.css\" />");
            orderToStringBuilder.append("</head>");
            orderToStringBuilder.append("<body>");
            orderToStringBuilder.append("  <div class=\"page-container\">");
            orderToStringBuilder.append("       <div class=\"contentarea\">");
            orderToStringBuilder.append("           <div id=\"column-container\">");
            orderToStringBuilder.append("               <!-- Begin Section Widget  -->");
            orderToStringBuilder.append("               <div id=\"content-main-section\">");
            orderToStringBuilder.append("                   <div id=\"split50\">");
            orderToStringBuilder.append("                       <table width=100%>");
            orderToStringBuilder.append("                           <tr> ");
            orderToStringBuilder.append("                               <td style=border-width:1px; border-color:Black ; border-style :groove ; width=50% vertical-align:top>");
            orderToStringBuilder.append("                                   <div class=\"lefthalf\";  width=100%>");
            orderToStringBuilder.append("                                       <!-- Left -->");

            orderToStringBuilder.append(orderSummaryScreenlet.getScreenletHtml(root));
            orderToStringBuilder.append(orderTermsScreenlet.getScreenletHtml(root));
            orderToStringBuilder.append(paymentInformationScreenlet.getScreenletHtml(root));
            orderToStringBuilder.append(orderSalesRepsScreenlet.getScreenletHtml(root));

            orderToStringBuilder.append("                                   </div>");
            orderToStringBuilder.append("                               </td>");
            orderToStringBuilder.append("                               <td style=border-width:1px; border-color:Black ; border-style :groove ; width=50% ; vertical-align:top>");
            orderToStringBuilder.append("                                   <div class=\"righthalf\" width=80%>");
            orderToStringBuilder.append("                                       <!-- Right -->");
            orderToStringBuilder.append(contactInformationScreenlet.getScreenletHtml(root));
            orderToStringBuilder.append(shipmentInformationScreenlet.getScreenletHtml(root));

            orderToStringBuilder.append("                                   </div>");
            orderToStringBuilder.append("                               </td>");
            orderToStringBuilder.append("                           </tr>");
            orderToStringBuilder.append("                       </table> ");
            orderToStringBuilder.append("                   </div>");
            orderToStringBuilder.append("                   <div class=\"clear\"> </div>");
            orderToStringBuilder.append("                       <table width=100%>");
            orderToStringBuilder.append("                           <tr> ");
            orderToStringBuilder.append("                               <td style=border-width:1px; border-color:Black ; border-style :groove ; width=100% vertical-align:top>");
            orderToStringBuilder.append("                                   <div class=\"lefthalf\";  width=100%>");
            orderToStringBuilder.append("                                       <div width=100%>");
            orderToStringBuilder.append(orderItemsScreenlet.getScreenletHtml(root));
            orderToStringBuilder.append(orderNotesScreenlet.getScreenletHtml(root));
            orderToStringBuilder.append("                                       </div>");
            orderToStringBuilder.append("                               </td>");
            orderToStringBuilder.append("                           </tr>");
            orderToStringBuilder.append("                       </table> ");
            orderToStringBuilder.append("                   <div class=\"clear\"> </div>");
            orderToStringBuilder.append("               </div>");
            orderToStringBuilder.append("           </div><!-- End Section Widget  -->");
            orderToStringBuilder.append("       </div>");
            orderToStringBuilder.append("   </div>");
            orderToStringBuilder.append("</body>");
            orderToStringBuilder.append("</html>");
            //Debug.logInfo("orderToStringBuilder : " + orderToStringBuilder, module);
            return orderToStringBuilder.toString();
        } else {
            return "Please save the order...";
        }
    }

    private static final Set<String> protectedKeys = createProtectedKeys();

    private static Set<String> createProtectedKeys() {
        Set<String> newSet = new HashSet<String>();
        newSet.add("request");
        newSet.add("response");
        newSet.add("session");
        newSet.add("dispatcher");
        newSet.add("delegator");
        newSet.add("security");
        newSet.add("locale");
        newSet.add("timeZone");
        newSet.add("userLogin");
        /* Commenting out for now because some scripts write to the parameters Map - which should not be allowed.
         newSet.add(ScriptUtil.PARAMETERS_KEY);
         */
        return Collections.unmodifiableSet(newSet);
    }

    public Bindings invoke(Order order) throws EventHandlerException {
        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        Bindings bindings = null;
        String path = "C:\\backup\\ofbiz-12.04.02\\specialpurpose\\pos\\webapp\\groovy\\OrderView.groovy";
        try {

            Map<String, Object> context = new HashMap<String, Object>();

            context.put("locale", Locale.getDefault());
            context.put("delegator", XuiContainer.getSession().getDelegator());
            context.put("security", XuiContainer.getSession().getDispatcher().getSecurity());
            context.put("session", XuiContainer.getSession());
            context.put("dispatcher", XuiContainer.getSession().getDispatcher());
            context.put("timeZone", TimeZone.getDefault());
            context.put("userLogin", XuiContainer.getSession().getUserLogin());

            context.put(ScriptUtil.PARAMETERS_KEY,
                    UtilMisc.toMap("orderId", order.orderHeader.getOrderId(),
                            "workEffortId", null,
                            "partyId", null,
                            "roleTypeId", null,
                            "fromDate", null,
                            "delegate", false
                    ));

            Object result = null;
            try {
                ScriptContext scriptContext = ScriptUtil.createScriptContext(context, protectedKeys);
                // Write all script outputs to an out.txt file
                Writer fileWriter = new FileWriter("out.txt");
                scriptContext.setWriter(fileWriter);
                result = ScriptUtil.executeScript(UtilURL.fromFilename(path).toExternalForm(), null, scriptContext, null);
                bindings = scriptContext.getBindings(ScriptContext.ENGINE_SCOPE);

                System.out.println(result);
                if (result == null) {
                    result = scriptContext.getAttribute(ScriptUtil.RESULT_KEY);
                } else {
                    result = scriptContext.getAttribute(ScriptUtil.RESULT_KEY);
//                                  Map<Object, Object> resultMap = (Map<Object, Object>) result;
                    // for (Map.Entry<Object, Object> entryDept : scriptContext.entrySet()) {
                    //     Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    // }
                    // Write all script outputs to an out.txt file
                    //fileWriter = new FileWriter("out1.txt");
                    //scriptContext.setWriter(fileWriter);
                    scriptContext.getWriter().close();
                }

            } catch (Exception e) {
                Debug.logWarning(e, "Error running event " + path + ": ", module);
                Debug.logError(e, module);
//                request.setAttribute("_ERROR_MESSAGE_", e.getMessage());
                return bindings;
            }

            if (result instanceof Map) {
                Map<Object, Object> resultMap = (Map<Object, Object>) result;
                for (Map.Entry<Object, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }

                String successMessage = (String) resultMap.get("_event_message_");
                if (successMessage != null) {
//                    request.setAttribute("_EVENT_MESSAGE_", successMessage);
                }
                String errorMessage = (String) resultMap.get("_error_message_");
                if (errorMessage != null) {
//                    request.setAttribute("_ERROR_MESSAGE_", errorMessage);
                }
                return bindings;
            }
            if (result != null && !(result instanceof String)) {
                throw new EventHandlerException("Event did not return a String result, it returned a " + result.getClass().getName());
            }
            return bindings;
        } catch (Exception e) {
            Debug.logError(e, module);
            throw new EventHandlerException("Error running event " + path, e);
        }
//        return bindings;
    }

    protected ClassLoader getClassLoader() {

        ClassLoader cl = null;
        try {
            cl = XuiContainer.getSession().getClassLoader();

            if (cl == null) {
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                }
                if (cl == null) {
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

    @Override
    public String getScreenletHtml(Map root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
