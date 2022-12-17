/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductInventoryPojo;
import static org.ofbiz.ordermax.orders.OrderDisplayDialog.module;
import org.ofbiz.ordermax.orders.OrderFindHelper;
import org.ofbiz.ordermax.reportdesigner_old.InvoiceHeaderReport;
import javolution.util.FastMap;
import mvc.controller.LoadAccountWorker;
import mvc.controller.LoadOrderWorker;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.orderbase.OrderFinancialData;
import org.ofbiz.ordermax.reportdesigner_old.OrderHeaderReport;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class DataBeanList {

    public ArrayList<InvoiceReportItemEntity> getDataBeanList(XuiSession session, String facilityId, java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd) {

        Delegator delegator = session.getDelegator();
        ArrayList<InvoiceReportItemEntity> dataBeanList = new ArrayList<InvoiceReportItemEntity>();
        ArrayList<Invoice> invoiceDataList = new ArrayList<Invoice>();

        boolean salesOrders = true;
        List<GenericValue> invoiceList = null;

        //Debug.logInfo(" dayStart: " + dayStart.toString(), module);
        //Debug.logInfo(" closedDate: " + dayEnd.toString(), module);
        if (salesOrders) {
            invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//
        } else {
            invoiceList = OrderFindHelper.getPurchaseInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//            
        }

//        List<GenericValue> invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);
        int i = 0;
//        List<GenericValue> invList = PosProductHelper.getInvoiceLists(delegator);
        invoiceList = EntityUtil.orderBy(invoiceList, UtilMisc.toList("invoiceDate DESC"));
        for (GenericValue invoice : invoiceList) {
            try {
                Invoice inv = new Invoice(invoice);

                invoiceDataList.add(inv);

                if (inv.getinvoiceTypeId().equalsIgnoreCase("SALES_INVOICE")) {
                    i++;
                } else {
                    continue;
                }
                //get last supplier details
                List<GenericValue> invoiceItemList = invoice.getRelated("InvoiceItem");
                List<GenericValue> orderedinvoiceItemList = EntityUtil.orderBy(invoiceItemList,
                        UtilMisc.toList("createdStamp ASC"));

                InvoiceReportItemEntity dataBean = new InvoiceReportItemEntity();
                dataBean.setName(inv.getinvoiceId());
                dataBean.setCountry(inv.getinvoiceTypeId());
                dataBean.setInvoiceDate(inv.getinvoiceDate());
                List<org.ofbiz.ordermax.entity.SubReportBean> invItemList = new ArrayList<org.ofbiz.ordermax.entity.SubReportBean>();

                for (GenericValue invItem : orderedinvoiceItemList) {
                    InvoiceItem ivoiceItem = new InvoiceItem(invItem);
                    ivoiceItem.calculateTotalItemAmount();

                    if (ivoiceItem.getInvoiceItemTypeId().equalsIgnoreCase("ITM_DISCOUNT_ADJ")) {
                        org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();
                        sub.setCity(ivoiceItem.getProductId());
                        sub.setStreet("Item Discount");
                        sub.setProductId(ivoiceItem.getProductId());
                        sub.setDescription("Item Discount");
                        sub.setQuantity(BigDecimal.ZERO);
                        sub.setAmount(ivoiceItem.getAmount());
//                        sub.setItemProfit(sub.getAmount());
                        sub.setItemAmount(ivoiceItem.getAmount());
//                        sub.setTotalItemProfit(sub.getAmount());
                        sub.calculateProfit();
                        invItemList.add(sub);
                    } else if (ivoiceItem.getInvoiceItemTypeId().equalsIgnoreCase("ITM_SALES_TAX")) {
                        org.ofbiz.ordermax.entity.SubReportBean previousItem = getProductItem(invItemList, ivoiceItem.getProductId());
                        if (previousItem == null) {
                            org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();

                            sub.setCity(ivoiceItem.getProductId());
                            sub.setStreet(ivoiceItem.getDescription());
                            sub.setProductId(ivoiceItem.getProductId());
                            sub.setDescription(ivoiceItem.getDescription());
                            sub.setQuantity(ivoiceItem.getQuantity());
                            sub.setAmount(ivoiceItem.getAmount());
                            sub.setItemAmount(sub.getQuantity().multiply(sub.getAmount()));
                            sub.setCost(BigDecimal.ZERO);
                            sub.setQuantity(BigDecimal.ONE);
                            sub.setTotalCost(sub.getQuantity().multiply(sub.getCost()));
                            //sub.setItemProfit(sub.getAmount().subtract(sub.getCost()));
                            //sub.setTotalItemProfit(sub.getItemProfit().multiply(sub.getQuantity()));
                            sub.calculateProfit();
                            invItemList.add(sub);
                        } else {
                            previousItem.setAmount(previousItem.getAmount().add(ivoiceItem.getAmount()));
                            previousItem.setItemAmount(previousItem.getAmount().multiply(previousItem.getQuantity()));
                            previousItem.setTotalCost(previousItem.getQuantity().multiply(previousItem.getCost()));
//                            previousItem.setItemProfit(previousItem.getItemAmount().subtract(previousItem.getCost()));
//                            previousItem.setTotalItemProfit(ivoiceItem.getAmount());
                            previousItem.calculateProfit();
                        }

                    } else {
                        org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();

                        sub.setCity(ivoiceItem.getProductId());
                        sub.setStreet(ivoiceItem.getDescription());
                        sub.setProductId(ivoiceItem.getProductId());
                        sub.setDescription(ivoiceItem.getDescription());
                        sub.setQuantity(ivoiceItem.getQuantity());
                        sub.setAmount(ivoiceItem.getAmount());
                        sub.setItemAmount(sub.getQuantity().multiply(sub.getAmount()));
//                            sub.setItemAmount(ivoiceItem.getAmount());

                        Map<String, Object> whereClauseMap = new HashMap<String, Object>();
                        whereClauseMap.put("productId", ivoiceItem.getProductId());
                        whereClauseMap.put("facilityId", facilityId);
                        ProductInventoryPojo pojo = new ProductInventoryPojo();
                        pojo.productId = ivoiceItem.getProductId();
                        GenericValue productAvgPrice = PosProductHelper.getProductPrice(ivoiceItem.getProductId(), PosProductHelper.ProductPriceTypeId_AVERAGECOST, PosProductHelper.productPricePurposeId_purchase, PosProductHelper.productStoreGroupId_na, delegator);
//                    List<GenericValue> productAvgList = PosProductHelper.getGenericValueListsWithSelection("ProductAverageCost", whereClauseMap, null, delegator);                    
//                    List<GenericValue> orderedProductAvgList = EntityUtil.orderBy(productAvgList,UtilMisc.toList("createdStamp ASC"));
                        if (productAvgPrice != null) {//orderedProductAvgList.size()>0){
//                        ProductAverageCost avgCost = new ProductAverageCost(orderedProductAvgList.get(0));
                            ProductPrice avgCost = new ProductPrice(productAvgPrice);

                            sub.setCost(avgCost.getprice());
                            sub.calculateProfit();
                            /*if (sub.getCost() != null && sub.getQuantity() != null) {
                             sub.setTotalCost(sub.getQuantity().multiply(sub.getCost()));
                             sub.setItemProfit(sub.getAmount().subtract(sub.getCost()));
                             sub.setTotalItemProfit(sub.getItemProfit().multiply(sub.getQuantity()));
                             }*/

                        }

                        invItemList.add(sub);

                    }

                    dataBean.setSubReportBeanList(invItemList);
//                dataBean.setChildBeand(subBean3);

                }
//                dataBeanList.add(produce(inv.getinvoiceId(), inv.getinvoiceTypeId(),
//                        Arrays.asList(subBean1, subBean2, subBean3)));
                dataBeanList.add(dataBean);
            } catch (GenericEntityException ex) {
                Logger.getLogger(DataBeanList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dataBeanList;
    }

    public org.ofbiz.ordermax.entity.SubReportBean getProductItem(List<org.ofbiz.ordermax.entity.SubReportBean> invItemList, String productId) {
        org.ofbiz.ordermax.entity.SubReportBean subReportBean = null;
        for (org.ofbiz.ordermax.entity.SubReportBean val : invItemList) {
            if (val.getProductId().equalsIgnoreCase(productId)) {
//                subReportBean = val;
                break;
            }
        }
        return subReportBean;
    }

    public InvoiceItem getInvoiceItem(List<InvoiceItem> invItemList, String productId, String itemSeq) {
        InvoiceItem item = null;
        for (InvoiceItem val : invItemList) {
            if (UtilValidate.isNotEmpty(val.getProductId()) && val.getProductId().equalsIgnoreCase(productId) && val.getInvoiceItemSeqId() != null
                    && val.getInvoiceItemSeqId().equalsIgnoreCase(itemSeq)) {
                item = val;
                break;
            }
        }
        return item;
    }

    public InvoiceHeaderReport getSalesInvoiceBean(XuiSession session, String invoiceId,
            java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd, OrderFinancialData orderFinancialData) {

        Delegator delegator = session.getDelegator();
//        ArrayList<InvoiceReportItemEntity> dataBeanList = new ArrayList<InvoiceReportItemEntity>();

        boolean salesOrders = true;
        List<GenericValue> invoiceList = null;
        Map<String, Object> input = FastMap.newInstance();
        input.put("invoiceId", invoiceId);
        GenericValue invoice = PosProductHelper.getGenericValueByKey("Invoice", input, delegator);

        Set<String> orderIds = LoadOrderWorker.getInvoiceOrderIds(invoice);
        PostalAddress deliveryPostalAddress = null;
        if (orderIds.size() > 0) {
            String orderId = orderIds.iterator().next();
            Debug.logInfo("orderId: " + orderId, module);
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("orderId", orderId);
            whereClauseMap.put("contactMechPurposeTypeId", "SHIPPING_LOCATION");
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("OrderContactMech", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                Debug.logInfo("deliveryPostalAddress contactMechId: " + val.getString("contactMechId"), module);
                deliveryPostalAddress = LoadAccountWorker.getPostalAddress(val.getString("contactMechId"));
                break;
                //  getInstance().valueMap.put(productPriceType.getproductStoreGroupId(), productPriceType);
            }
        }

        PostalAddress biilToPostalAddress = null;
        if (orderIds.size() > 0) {
            String orderId = orderIds.iterator().next();
            Debug.logInfo("orderId: " + orderId, module);
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("orderId", orderId);
            whereClauseMap.put("contactMechPurposeTypeId", "BILLING_LOCATION");
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("OrderContactMech", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                Debug.logInfo("biilToPostalAddress contactMechId: " + val.getString("contactMechId"), module);
                biilToPostalAddress = LoadAccountWorker.getPostalAddress(val.getString("contactMechId"));
                break;
                //  getInstance().valueMap.put(productPriceType.getproductStoreGroupId(), productPriceType);
            }
        }

//        if (salesOrders) {
//            invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//
//        } else {
//            invoiceList = OrderFindHelper.getPurchaseInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//            
///        }
//        List<GenericValue> invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);
//        int i = 0;
//        List<GenericValue> invList = PosProductHelper.getInvoiceLists(delegator);
        //       invoiceList = EntityUtil.orderBy(invoiceList, UtilMisc.toList("invoiceDate DESC"));
        InvoiceHeaderReport inv = null;
        //for (GenericValue invoice : invoiceList) {
        try {
            Invoice invoiceComposite = new Invoice(invoice);
            inv = new InvoiceHeaderReport(invoice);
            BigDecimal totalInvoiceAmt = InvoiceWorker.getInvoiceTotal(delegator, invoiceId);
            BigDecimal totalTaxAmount = InvoiceWorker.getInvoiceTaxTotal(invoice);
            BigDecimal totalExAmountAmount = InvoiceWorker.getInvoiceNoTaxTotal(invoice);

            inv.setTotal_amt(totalInvoiceAmt);
            inv.setTotalGstAmount(totalTaxAmount);
            inv.setTotalAmountExcludingGst(totalExAmountAmount);
            inv.setInvoiceNumber(invoiceId);
            //invoice details    
            GenericValue party = InvoiceWorker.getBillToParty(invoice);
            if (party != null) {

                Account biilToParty = LoadAccountWorker.getAccount(party.getString("partyId"), session);

                Map<String, Object> inputMap = FastMap.newInstance();
                inputMap.put("partyId", biilToParty.getParty().getpartyId());
                inputMap.put("partyIdentificationTypeId", "AUS_ABN");
                Debug.logInfo("shipmentParty: " + biilToParty.getParty().getpartyId(), module);
                List<GenericValue> partyIdentificationList = PosProductHelper.getGenericValueListsWithSelection(
                        "PartyIdentification",
                        inputMap, null, delegator);

                GenericValue partyIdentification = null;
                String abn = "";
                if (partyIdentificationList.size() > 0) {
                    partyIdentification = partyIdentificationList.get(0);
                    abn = partyIdentification.getString("idValue");
                    inv.setPartyIdentification(abn);
                }
                inv.setPartyId(biilToParty.getParty().getpartyId());
                Debug.logInfo("biilToParty.getPartyTypeId(): " + biilToParty.getPartyTypeId(), module);

                if ("PERSON".equals(biilToParty.getPartyTypeId())) {
                    inv.setEntity_name(biilToParty.getPerson().getfirstName() + " " + biilToParty.getPerson().getlastName());
                    //GenericValue addressBillFrom = InvoiceWorker.getSendFromAddress(invoice);
                    if (deliveryPostalAddress != null) {
                        inv.setReceiver_add1(OrderMaxUtility.getFormatedAddress(deliveryPostalAddress.getGenericValueObj()));
                    } else {
                        GenericValue addressReceiver = InvoiceWorker.getBillToAddress(invoice);
                        if (addressReceiver != null) {
                            inv.setReceiver_add1(OrderMaxUtility.getFormatedAddress(addressReceiver));
                        }
                    }

                    if (biilToPostalAddress != null) {
                        inv.setBilling_add1(OrderMaxUtility.getFormatedAddress(biilToPostalAddress.getGenericValueObj()));
                    } else {
                        GenericValue addressReceiver = InvoiceWorker.getBillToAddress(invoice);
                        if (addressReceiver != null) {
                            inv.setBilling_add1(OrderMaxUtility.getFormatedAddress(addressReceiver));
                        }
                    }

                    /*                inv.setBilling_add1();
                     inv.setBilling_add2();
                     inv.setBilling_add3();
                     inv.setBilling_city();
                     inv.setBilling_zip();
                     inv.setBilling_state();
                     inv.setBbilling_zip();
                     inv.setBilling_country();
                     */
                } else {
                    inv.setEntity_name(biilToParty.getPartyGroup().getgroupName());
                    /*GenericValue addressBillFrom = InvoiceWorker.getBillToAddress(invoice);

                     inv.setBilling_add1(OrderMaxUtility.getFormatedAddress(addressBillFrom));
                     if (deliveryPostalAddress != null) {
                     GenericValue addressReceiver = deliveryPostalAddress.getGenericValueObj();//InvoiceWorker.getInvoiceAddressByType(invoice, "SHIPPING_LOCAION");
                     inv.setReceiver_add1(OrderMaxUtility.getFormatedAddress(addressReceiver));
                     }
                     */
                    if (deliveryPostalAddress != null) {
                        inv.setReceiver_add1(OrderMaxUtility.getFormatedAddress(deliveryPostalAddress.getGenericValueObj()));
                    } else {
                        GenericValue addressReceiver = InvoiceWorker.getBillToAddress(invoice);
                        if (addressReceiver != null) {
                            inv.setReceiver_add1(OrderMaxUtility.getFormatedAddress(addressReceiver));
                        }
                    }

                    if (biilToPostalAddress != null) {
                        inv.setBilling_add1(OrderMaxUtility.getFormatedAddress(biilToPostalAddress.getGenericValueObj()));
                    } else {
                        GenericValue addressReceiver = InvoiceWorker.getBillToAddress(invoice);
                        if (addressReceiver != null) {
                            inv.setBilling_add1(OrderMaxUtility.getFormatedAddress(addressReceiver));
                        }
                    }

                    inv.setCustomerReference(invoiceComposite.getreferenceNumber());
                    inv.setCodeCustomer(invoiceComposite.getpartyIdFrom());
                    inv.setCodeSupplier(invoiceComposite.getpartyIdFrom());
                    inv.setPhoneNo(module);
                    inv.setFaxNo(module);

                    inv.setSalesPerson(module);
                    inv.setSupplierABN(abn);
                    inv.setInvoiceDate(UtilDateTime.nowTimestamp());
                    if (biilToParty.getPartyContactList().getBillingLocationPhoneContact() != null) {
                        String phone = OrderMaxUtility.getFormatedTelecom(biilToParty.getPartyContactList()
                                .getBillingLocationPhoneContact().getContact()
                                .getTelecomNumber().getGenericValueObj());

                        inv.setSupplierPhone(phone);
                        String contactName = biilToParty.getPartyContactList()
                                .getBillingLocationPhoneContact().getContact()
                                .getTelecomNumber().getaskForName();
                        inv.setAttnName(contactName);
                    }

                    inv.setAccountStatus(module);
                }

                Debug.logInfo("orderFinancialData: ", module);
                if (orderFinancialData != null) {
                    BigDecimal val = orderFinancialData.getTotalOutstanding();
                    inv.setTotalOutstanding(val);
                    Debug.logInfo("setTotalOutstanding: " + val.toString(), module);
                    val = orderFinancialData.getZeroTo29DaysTotal();
                    inv.setCurrentOutstanding(val);

                    val = orderFinancialData.get30To59DaysTotal();
                    inv.setDays30Outstanding(val);

                    val = orderFinancialData.get60To89DaysTotal();
                    inv.setDays60Outstanding(val);

                    val = orderFinancialData.getMoreThan90DaysTotal();
                    inv.setDays90OrMoreOutstanding(val);
                }
            }
            Debug.logInfo("totalInvoiceAmt: " + totalInvoiceAmt, module);
//                invoiceDataList.add(inv);

            //if (inv.getinvoiceTypeId().equalsIgnoreCase("SALES_INVOICE")) {
            //    i++;
            //} else {
            //    continue;
            // }
            //get last supplier details
            List<GenericValue> invoiceItemList = invoice.getRelated("InvoiceItem");
            List<GenericValue> orderedinvoiceItemList = EntityUtil.orderBy(invoiceItemList,
                    UtilMisc.toList("invoiceItemSeqId ASC"));
            List<InvoiceItem> itemList = new ArrayList<InvoiceItem>();
            List<org.ofbiz.ordermax.entity.SubReportBean> invItemList = new ArrayList<org.ofbiz.ordermax.entity.SubReportBean>();

            for (GenericValue invItem : orderedinvoiceItemList) {
                InvoiceItem ivoiceItem = new InvoiceItem(invItem);
                InvoiceItem parentInvoiceItem = getInvoiceItem(itemList, ivoiceItem.getProductId(), ivoiceItem.getParentInvoiceItemSeqId());
                if (parentInvoiceItem == null) {
                    itemList.add(ivoiceItem);
                } else {
                    parentInvoiceItem.addRelatedItem(ivoiceItem);
                }
            }

            for (InvoiceItem invItem : itemList) {
                //invItem.calculateTaxAndDiscount();
                invItem.caculateGST();
                totalTaxAmount = totalTaxAmount.add(invItem.getTotalGST());
               Debug.logInfo("invItem.getTax(): " + invItem.getTax(), module);
               Debug.logInfo("invItem.getquantity(): " + invItem.getquantity(), module);
         Debug.logInfo("totalTaxAmount: " + totalTaxAmount, module);    
           //totalTaxAmount.add(invItem.getTotalGST());
            }

            for (InvoiceItem ivoiceItem : itemList) {
                if (ivoiceItem.getInvoiceItemTypeId().equalsIgnoreCase("INV_FPROD_ITEM")) {
                    org.ofbiz.ordermax.entity.SubReportBean sub = getSubReportBean(ivoiceItem);
                    invItemList.add(sub);
                    Debug.logInfo("invoiceRelItem: " + ivoiceItem.getDescription() + "invoiceRelItem Size: " + ivoiceItem.getRelatedItems().size(), module);
                    for (InvoiceItem invoiceRelItem : ivoiceItem.getRelatedItems()) {
                        Debug.logInfo("invoiceRelItem: " + invoiceRelItem.getDescription(), module);

//                        if (ivoiceItem.getInvoiceItemTypeId().equalsIgnoreCase("ITM_PROMOTION_ADJ")) {
                        org.ofbiz.ordermax.entity.SubReportBean sub1 = getSubReportBean(invoiceRelItem);
                        invItemList.add(sub1);
                        //                      }
                    }
                } else if (ivoiceItem.getInvoiceItemTypeId().equalsIgnoreCase("ITM_PROMOTION_ADJ")) {
                    org.ofbiz.ordermax.entity.SubReportBean sub = getSubReportBean(ivoiceItem);
                    invItemList.add(sub);
                }
            }
            inv.setSubReportBeanList(invItemList);
             inv.setTotal_amt(totalInvoiceAmt.add(totalTaxAmount) );
            inv.setTotalGstAmount(totalTaxAmount);
            inv.setTotalAmountExcludingGst(totalInvoiceAmt);
            
               Debug.logInfo("inv.getTotal_amt(: " + inv.getTotal_amt(), module);
         Debug.logInfo("inv.getTotalGstAmount(: " + inv.getTotalGstAmount(), module);
         Debug.logInfo("inv.setTotalAmountExcludingGst(: " + inv.getTotalAmountExcludingGst(), module);         
        } catch (GenericEntityException ex) {
            Logger.getLogger(DataBeanList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DataBeanList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inv;
    }

    public org.ofbiz.ordermax.entity.SubReportBean getSubReportBean(InvoiceItem ivoiceItem) {
        org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();
        sub.setCity(ivoiceItem.getProductId());
        sub.setStreet(ivoiceItem.getDescription());
        if (UtilValidate.isNotEmpty(ivoiceItem.getProductId())) {
            sub.setProductId(ivoiceItem.getProductId());
        } else {
            sub.setProductId("");
        }
        sub.setDescription(ivoiceItem.getDescription());
        sub.setQuantity(ivoiceItem.getQuantity());
        sub.setAmount(ivoiceItem.getAmount());
        Debug.logInfo("Gst item: " + ivoiceItem.getTax().toString(), module);
        Debug.logInfo("ivoiceItem.getInvoiceItemTypeId(): " + ivoiceItem.getInvoiceItemTypeId(), module);
         sub.setItemGSTAmount(ivoiceItem.getTax());
        sub.setItemTotalGst(ivoiceItem.getTotalGST());
        sub.setTotalItemExGst(ivoiceItem.getAmount().multiply(ivoiceItem.getQuantity()));
//                        sub.setItemAmount(sub.getQuantity().multiply(sub.getAmount()));
        BigDecimal total = sub.getTotalItemExGst().add(sub.getItemTotalGst());
        sub.setItemAmount(total.add(ivoiceItem.getDiscount()));

        return sub;
    }

    public InvoiceHeaderReport getInvoiceBean(XuiSession session, String invoiceId,
            java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd, OrderFinancialData orderFinancialData) {

        Delegator delegator = session.getDelegator();
//        ArrayList<InvoiceReportItemEntity> dataBeanList = new ArrayList<InvoiceReportItemEntity>();

        boolean salesOrders = true;
        List<GenericValue> invoiceList = null;
        Map<String, Object> input = FastMap.newInstance();
        input.put("invoiceId", invoiceId);
        GenericValue invoice = PosProductHelper.getGenericValueByKey("Invoice", input, delegator);

//        if (salesOrders) {
//            invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//
//        } else {
//            invoiceList = OrderFindHelper.getPurchaseInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//            
///        }
//        List<GenericValue> invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);
//        int i = 0;
//        List<GenericValue> invList = PosProductHelper.getInvoiceLists(delegator);
        //       invoiceList = EntityUtil.orderBy(invoiceList, UtilMisc.toList("invoiceDate DESC"));
        InvoiceHeaderReport inv = null;
        //for (GenericValue invoice : invoiceList) {
        try {
            Invoice invoiceComposite = new Invoice(invoice);
            inv = new InvoiceHeaderReport(invoice);
            BigDecimal totalInvoiceAmt = InvoiceWorker.getInvoiceTotal(delegator, invoiceId);
            BigDecimal totalTaxAmount = InvoiceWorker.getInvoiceTaxTotal(invoice);
            BigDecimal totalExAmountAmount = InvoiceWorker.getInvoiceNoTaxTotal(invoice);

            inv.setTotal_amt(totalInvoiceAmt);
            inv.setTotalGstAmount(totalTaxAmount);
            inv.setTotalAmountExcludingGst(totalExAmountAmount);
            inv.setInvoiceNumber(invoiceId);
            //invoice details    
            GenericValue party = InvoiceWorker.getSendFromParty(invoice);
            if (party != null) {

                Account biilFromParty = LoadAccountWorker.getAccount(party.getString("partyId"), session);

                Map<String, Object> inputMap = FastMap.newInstance();
                inputMap.put("partyId", biilFromParty.getParty().getpartyId());
                inputMap.put("partyIdentificationTypeId", "ABN");
                Debug.logInfo("shipmentParty: " + biilFromParty.getParty().getpartyId(), module);
                List<GenericValue> partyIdentificationList = PosProductHelper.getGenericValueListsWithSelection(
                        "PartyIdentification",
                        inputMap, null, delegator);

                GenericValue partyIdentification = null;
                String abn = "";
                if (partyIdentificationList.size() > 0) {
                    partyIdentification = partyIdentificationList.get(0);
                    abn = partyIdentification.getString("idValue");
                }

                Debug.logInfo("abn: " + abn, module);

                if ("PERSON".equals(biilFromParty.getPartyTypeId())) {
                    inv.setEntity_name(biilFromParty.getPerson().getfirstName() + " " + biilFromParty.getPerson().getlastName());
                    GenericValue addressBillFrom = InvoiceWorker.getSendFromAddress(invoice);
                    inv.setBilling_add1(OrderMaxUtility.getFormatedAddress(addressBillFrom));

                    GenericValue addressReceiver = InvoiceWorker.getBillToAddress(invoice);
                    inv.setReceiver_add1(OrderMaxUtility.getFormatedAddress(addressReceiver));

                    /*                inv.setBilling_add1();
                     inv.setBilling_add2();
                     inv.setBilling_add3();
                     inv.setBilling_city();
                     inv.setBilling_zip();
                     inv.setBilling_state();
                     inv.setBbilling_zip();
                     inv.setBilling_country();
                     */
                } else {
                    inv.setEntity_name(biilFromParty.getPartyGroup().getgroupName());
                    GenericValue addressBillFrom = InvoiceWorker.getSendFromAddress(invoice);
                    inv.setBilling_add1(OrderMaxUtility.getFormatedAddress(addressBillFrom));
                    GenericValue addressReceiver = InvoiceWorker.getBillToAddress(invoice);
                    inv.setReceiver_add1(OrderMaxUtility.getFormatedAddress(addressReceiver));
                    inv.setCustomerReference(invoiceComposite.getreferenceNumber());
                    inv.setCodeCustomer(invoiceComposite.getpartyIdFrom());
                    inv.setCodeSupplier(invoiceComposite.getpartyIdFrom());
                    inv.setPhoneNo(module);
                    inv.setFaxNo(module);

                    inv.setSalesPerson(module);
                    inv.setSupplierABN(abn);
                    inv.setInvoiceDate(UtilDateTime.nowTimestamp());
                    if (biilFromParty.getPartyContactList().getBillingLocationPhoneContact() != null) {
                        String phone = OrderMaxUtility.getFormatedTelecom(biilFromParty.getPartyContactList()
                                .getBillingLocationPhoneContact().getContact()
                                .getTelecomNumber().getGenericValueObj());

                        inv.setSupplierPhone(phone);
                        String contactName = biilFromParty.getPartyContactList()
                                .getBillingLocationPhoneContact().getContact()
                                .getTelecomNumber().getaskForName();
                        inv.setAttnName(contactName);
                    }

                    inv.setAccountStatus(module);
                }
                Debug.logInfo("orderFinancialData: ", module);
                if (orderFinancialData != null) {
                    BigDecimal val = orderFinancialData.getTotalOutstanding();
                    inv.setTotalOutstanding(val);
                    Debug.logInfo("setTotalOutstanding: " + val.toString(), module);
                    val = orderFinancialData.getZeroTo29DaysTotal();
                    inv.setCurrentOutstanding(val);

                    val = orderFinancialData.get30To59DaysTotal();
                    inv.setDays30Outstanding(val);

                    val = orderFinancialData.get60To89DaysTotal();
                    inv.setDays60Outstanding(val);

                    val = orderFinancialData.getMoreThan90DaysTotal();
                    inv.setDays90OrMoreOutstanding(val);
                }
            }
            Debug.logInfo("totalInvoiceAmt: " + totalInvoiceAmt, module);
//                invoiceDataList.add(inv);

            //if (inv.getinvoiceTypeId().equalsIgnoreCase("SALES_INVOICE")) {
            //    i++;
            //} else {
            //    continue;
            // }
            //get last supplier details
            List<GenericValue> invoiceItemList = invoice.getRelated("InvoiceItem");
            List<GenericValue> orderedinvoiceItemList = EntityUtil.orderBy(invoiceItemList,
                    UtilMisc.toList("invoiceItemSeqId ASC"));
            List<InvoiceItem> itemList = new ArrayList<InvoiceItem>();
            List<org.ofbiz.ordermax.entity.SubReportBean> invItemList = new ArrayList<org.ofbiz.ordermax.entity.SubReportBean>();

            for (GenericValue invItem : orderedinvoiceItemList) {
                InvoiceItem ivoiceItem = new InvoiceItem(invItem);
                InvoiceItem parentInvoiceItem = getInvoiceItem(itemList, ivoiceItem.getProductId(), ivoiceItem.getParentInvoiceItemSeqId());
                if (parentInvoiceItem == null) {
                    itemList.add(ivoiceItem);
                } else {
                    parentInvoiceItem.addRelatedItem(ivoiceItem);
                }
            }

            for (InvoiceItem invItem : itemList) {
                invItem.calculateTaxAndDiscount();
            }

            for (InvoiceItem ivoiceItem : itemList) {
                org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();
                sub.setCity(ivoiceItem.getProductId());
                sub.setStreet(ivoiceItem.getDescription());
                sub.setProductId(ivoiceItem.getProductId());
                sub.setDescription(ivoiceItem.getDescription());
                sub.setQuantity(ivoiceItem.getQuantity());
                sub.setAmount(ivoiceItem.getAmount());
                Debug.logInfo("Gst item: " + ivoiceItem.getTax().toString(), module);
                sub.setItemTotalGst(ivoiceItem.getTax());
                sub.setTotalItemExGst(ivoiceItem.getAmount().multiply(ivoiceItem.getQuantity()));
//                        sub.setItemAmount(sub.getQuantity().multiply(sub.getAmount()));
                BigDecimal total = sub.getTotalItemExGst().add(ivoiceItem.getTax());
                sub.setItemAmount(total.add(ivoiceItem.getDiscount()));
                invItemList.add(sub);

            }

            /*
             if (ivoiceItem.getInvoiceItemTypeId().equalsIgnoreCase("ITM_DISCOUNT_ADJ")) {
             org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();
             sub.setCity(ivoiceItem.getProductId());
             sub.setStreet("Item Discount");
             sub.setProductId(ivoiceItem.getProductId());
             sub.setDescription("Item Discount");
             sub.setQuantity(BigDecimal.ZERO);
             sub.setAmount(ivoiceItem.getAmount());
             //                        sub.setItemProfit(sub.getAmount());
             sub.setItemAmount(ivoiceItem.getAmount());
             //                        sub.setTotalItemProfit(sub.getAmount());
             sub.calculateProfit();
             invItemList.add(sub);
             } else if (ivoiceItem.getInvoiceItemTypeId().equalsIgnoreCase("ITM_SALES_TAX")) {
             org.ofbiz.ordermax.entity.SubReportBean previousItem = getProductItem(invItemList, ivoiceItem.getProductId());
             if (previousItem == null) {
             org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();

             sub.setCity(ivoiceItem.getProductId());
             sub.setStreet(ivoiceItem.getDescription());
             sub.setProductId(ivoiceItem.getProductId());
             sub.setDescription(ivoiceItem.getDescription());
             sub.setQuantity(ivoiceItem.getQuantity());
             sub.setAmount(ivoiceItem.getAmount());
             sub.setItemAmount(sub.getQuantity().multiply(sub.getAmount()));
             sub.setCost(BigDecimal.ZERO);
             sub.setQuantity(BigDecimal.ONE);
             sub.setTotalCost(sub.getQuantity().multiply(sub.getCost()));
             //sub.setItemProfit(sub.getAmount().subtract(sub.getCost()));
             //sub.setTotalItemProfit(sub.getItemProfit().multiply(sub.getQuantity()));
             sub.calculateProfit();
             Debug.logInfo(" ivoiceItem.getProductId(): " + ivoiceItem.getProductId(), module);                            
             invItemList.add(sub);
             } else {
             previousItem.setAmount(previousItem.getAmount().add(ivoiceItem.getAmount()));
             previousItem.setItemAmount(previousItem.getAmount().multiply(previousItem.getQuantity()));
             previousItem.setTotalCost(previousItem.getQuantity().multiply(previousItem.getCost()));
             //                            previousItem.setItemProfit(previousItem.getItemAmount().subtract(previousItem.getCost()));
             //                            previousItem.setTotalItemProfit(ivoiceItem.getAmount());
             previousItem.calculateProfit();
             }

             } else {
             org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();

             sub.setCity(ivoiceItem.getProductId());
             sub.setStreet(ivoiceItem.getDescription());
             sub.setProductId(ivoiceItem.getProductId());
             sub.setDescription(ivoiceItem.getDescription());
             sub.setQuantity(ivoiceItem.getQuantity());
             sub.setAmount(ivoiceItem.getAmount());
             sub.setItemAmount(sub.getQuantity().multiply(sub.getAmount()));
             invItemList.add(sub);

             }


             //                dataBean.setChildBeand(subBean3);

             }
             */
            inv.setSubReportBeanList(invItemList);
        } catch (GenericEntityException ex) {
            Logger.getLogger(DataBeanList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DataBeanList.class.getName()).log(Level.SEVERE, null, ex);
        }

        //}
        return inv;
    }

    static public OrderHeaderReport getOrderBean(XuiSession session, Order order, java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd) {

        Delegator delegator = session.getDelegator();
//        ArrayList<InvoiceReportItemEntity> dataBeanList = new ArrayList<InvoiceReportItemEntity>();

        boolean salesOrders = true;
//        List<GenericValue> invoiceList = null;
//        Map<String, Object> input = FastMap.newInstance();
//        input.put("orderId", orderId);
//        GenericValue orderHeader = PosProductHelper.getGenericValueByKey("OrderHeader", input, delegator);

//        if (salesOrders) {
//            invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//
//        } else {
//            invoiceList = OrderFindHelper.getPurchaseInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);//            
///        }
//        List<GenericValue> invoiceList = OrderFindHelper.getSalesInvoicesForGivenDatePeriod(delegator, dayStart, dayEnd);
//        int i = 0;
//        List<GenericValue> invList = PosProductHelper.getInvoiceLists(delegator);
        //       invoiceList = EntityUtil.orderBy(invoiceList, UtilMisc.toList("invoiceDate DESC"));
        OrderHeaderReport inv = null;
        //for (GenericValue invoice : invoiceList) {
//        try {
        GenericValue orderHeader = null;
        try {
            orderHeader = delegator.findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", order.getOrderId()));
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }

        inv = new OrderHeaderReport(orderHeader);

//                invoiceDataList.add(inv);
        //if (inv.getinvoiceTypeId().equalsIgnoreCase("SALES_INVOICE")) {
        //    i++;
        //} else {
        //    continue;
        // }
        //get last supplier details
            /*
         List<GenericValue> invoiceItemList = orderHeader.getRelated("OrderItem");
         List<GenericValue> orderedinvoiceItemList = EntityUtil.orderBy(invoiceItemList,
         UtilMisc.toList("orderItemSeqId ASC"));


         */
        List<org.ofbiz.ordermax.entity.SubReportBean> invItemList = new ArrayList<org.ofbiz.ordermax.entity.SubReportBean>();
        List<OrderItem> itemList = new ArrayList<OrderItem>();
        for (OrderItemComposite invItem : order.getOrderItemsList().getList()) {
//                OrderItem ivoiceItem = new OrderItem(invItem);
            if (invItem.getOrderItem().getproductId() != null && invItem.getOrderItem().getproductId().isEmpty() == false) {
                itemList.add(invItem.getOrderItem());
            }
        }

        for (OrderItem ivoiceItem : itemList) {
            org.ofbiz.ordermax.entity.SubReportBean sub = new org.ofbiz.ordermax.entity.SubReportBean();
            sub.setCity(ivoiceItem.getproductId());
            sub.setStreet(ivoiceItem.getitemDescription());
            sub.setProductId(ivoiceItem.getproductId());
            sub.setDescription(ivoiceItem.getitemDescription());
            sub.setQuantity(ivoiceItem.getquantity());
//                        sub.setAmount(ivoiceItem.getamount());
//                        Debug.logInfo("Gst item: " + ivoiceItem.getTax().toString(), module);
//                        sub.setItemTotalGst(ivoiceItem.getTax());
//                        sub.setTotalItemExGst(ivoiceItem.getAmount().multiply(ivoiceItem.getQuantity()));
//                        sub.setItemAmount(sub.getQuantity().multiply(sub.getAmount()));
//                        BigDecimal total = sub.getTotalItemExGst().add(ivoiceItem.getTax());
//                        sub.setItemAmount(total.add(ivoiceItem.getDiscount()));                        
            invItemList.add(sub);

        }
        inv.setSubReportBeanList(invItemList);

        //}
        return inv;
    }

    /*
     * This method returns a DataBean object,
     * with name, country and sub report
     * bean data set in it.
     */
    int i = 0;
    int j = 0;

    private InvoiceReportItemEntity produce(String name, String country,
            List<org.ofbiz.ordermax.entity.SubReportBean> subBean) {
        InvoiceReportItemEntity dataBean = new InvoiceReportItemEntity();

        dataBean.setName(name);
        dataBean.setCountry(country);
        dataBean.setSubReportBeanList(subBean);
        dataBean.setChildBeand(subBean.get(0));
        return dataBean;
    }

    void addSubBeanData(org.ofbiz.ordermax.entity.SubReportBean dataBean) {
        //dataBean.dataBean_id = Integer.valueOf(dataBeanNum).longValue();
        dataBean.setInvoiceId("Inv - " + String.valueOf(i));
        dataBean.setProductId("Prod - " + String.valueOf(j));
        dataBean.setDescription("Desc - " + String.valueOf(i));
        dataBean.setQuantity(new BigDecimal(j));
        dataBean.setAmount(new BigDecimal(i));
        dataBean.setItemAmount(dataBean.getAmount().multiply(dataBean.getQuantity()));
        i++;
        j += 5;
    }
}
