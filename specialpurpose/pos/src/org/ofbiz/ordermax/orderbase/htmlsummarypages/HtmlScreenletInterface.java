/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.orderbase.htmlsummarypages;

import java.util.Map;
import org.ofbiz.ordermax.composite.Order;

/**
 *
 * @author BBS Auctions
 */
public interface HtmlScreenletInterface {
    public String getScreenletHtml(Order order);
    public String getScreenletHtml(Map root);
}
