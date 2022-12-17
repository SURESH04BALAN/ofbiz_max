/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.invoice.htmlsummarypages;

import java.util.Map;

/**
 *
 * @author BBS Auctions
 */
public interface InvoiceHtmlScreenletInterface {
    public String getScreenletHtml(String invoiceId);
    public String getScreenletHtml(Map root);
}
