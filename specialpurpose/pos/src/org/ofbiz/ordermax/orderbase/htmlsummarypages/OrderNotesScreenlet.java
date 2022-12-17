/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.htmlsummarypages;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.base.util.template.FreeMarkerWorker;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.Order;

/**
 *
 * @author BBS Auctions
 */
public class OrderNotesScreenlet implements HtmlScreenletInterface {
    public static final String module = OrderNotesScreenlet.class.getName();
    @Override
    public String getScreenletHtml(Order order) {
        //if (order == null) {
        //    return "";
        //}
        StringBuilder orderToStringBuilder = new StringBuilder();


       orderToStringBuilder.append("<div class=\"screenlet\">");
        orderToStringBuilder.append("    <div class=\"screenlet-title-bar\">");
        orderToStringBuilder.append("      <ul>");
        orderToStringBuilder.append("        <li class=\"h3\">&nbsp;Notes</li>");
        orderToStringBuilder.append("          <li><a href=\"/ordermgr/control/createnewnote?orderId&#61;VGCO18\">Create New</a></li>");
        orderToStringBuilder.append("      </ul>");
        orderToStringBuilder.append("      <br class=\"clear\"/>");
        orderToStringBuilder.append("    </div>");
        orderToStringBuilder.append("    <div class=\"screenlet-body\">");
        orderToStringBuilder.append("      <table class=\"basic-table\" cellspacing='0'>");
        orderToStringBuilder.append("        <tr>");
        orderToStringBuilder.append("          <td>");
        orderToStringBuilder.append("              <span class=\"label\">&nbsp;No notes for this order.</span>");
        orderToStringBuilder.append("          </td>");
        orderToStringBuilder.append("        </tr>");
        orderToStringBuilder.append("      </table>");
        orderToStringBuilder.append("    </div>");
        orderToStringBuilder.append("</div>");

        return orderToStringBuilder.toString();
    }

     @Override
    public String getScreenletHtml(Map root) {
               /* Merge data-model with template */
        Writer out = new StringWriter();
        root.put("session", XuiContainer.getSession().getUserLogin());
        File templateFile = new File("C:\\backup\\ofbiz-12.04.02\\applications\\order\\webapp\\ordermgr\\order\\ordernotes.ftl");
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
