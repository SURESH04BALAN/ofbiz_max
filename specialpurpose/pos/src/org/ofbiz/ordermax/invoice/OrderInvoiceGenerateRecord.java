/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

import java.math.BigDecimal;
import javax.swing.JButton;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;

/**
 *
 * @author siranjeev
 */
public class OrderInvoiceGenerateRecord {

    protected String orderId;
    protected String partyName;
    protected String orderStatus;
    protected String orderValue;

    protected String invoiceIds;
    protected String invoiceStatus;
    protected BigDecimal orderAmount = BigDecimal.ZERO;
    protected BigDecimal invoiceAmount = BigDecimal.ZERO;
    protected JButton orderButton;
    final ListAdapterListModel<InvoiceComposite> invoicesListModel = new ListAdapterListModel<InvoiceComposite>();

    public ListAdapterListModel<InvoiceComposite> getInvoicesListModel() {
        return invoicesListModel;
    }
    
    public JButton getOrderButton() {
        return orderButton;
    }

    public void setOrderButton(JButton orderButton) {
        this.orderButton = orderButton;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }
    protected Order order = null;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderInvoiceGenerateRecord() {
        orderId = "";
        partyName = "";
        orderStatus = "";
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public String getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(String invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }    
}
