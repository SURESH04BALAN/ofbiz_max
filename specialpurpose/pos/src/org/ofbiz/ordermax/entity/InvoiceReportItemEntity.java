/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.entity;

import java.util.List;

/**
 *
 * @author siranjeev
 */
public class InvoiceReportItemEntity {

    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public ItemIssuance getItemIssuance() {
        return itemIssuance;
    }

    public void setItemIssuance(ItemIssuance itemIssuance) {
        this.itemIssuance = itemIssuance;
    }

    public ProductAverageCost getProductAverageCost() {
        return productAverageCost;
    }

    public void setProductAverageCost(ProductAverageCost productAverageCost) {
        this.productAverageCost = productAverageCost;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product prod) {
        this.product = prod;
    }
    
    InvoiceItem invoiceItem;
    OrderItem orderItem;
    ItemIssuance itemIssuance;    
    ProductAverageCost productAverageCost;
    Product product;    
    
   private String name;
   private String country;
   private SubReportBean childBeand; 

    public SubReportBean getChildBeand() {
        return childBeand;
    }

    public void setChildBeand(SubReportBean childBeand) {
        this.childBeand = childBeand;
    }
   private List<SubReportBean> subReportBeanList;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public List<SubReportBean> getSubReportBeanList() {
      return subReportBeanList;
   }

   public void setSubReportBeanList(List<SubReportBean> subReportBeanList) {
      this.subReportBeanList = subReportBeanList;
   }    
    private java.sql.Timestamp invoiceDate;

    public java.sql.Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(java.sql.Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }   
}
