/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

/**
 *
 * @author siranjeev
 */
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderContactMechCompositeList;
import org.ofbiz.ordermax.composite.OrderRolesList;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupList;
import org.ofbiz.ordermax.composite.OrderItemsList;
import org.ofbiz.ordermax.composite.OrderStatusList;
import org.ofbiz.ordermax.composite.ProductItemPrice;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.OrderStatus;
import org.ofbiz.product.product.ProductWorker;
import org.ofbiz.service.LocalDispatcher;

/**
 *
 * @author siranjeev
 */
public class LoadPurchaseOrderWorker extends LoadOrderWorker {

    public LoadPurchaseOrderWorker(ListAdapterListModel<Order> orderListModel, XuiSession delegator, Map<String, Object> whereClauseMap) {
        super(orderListModel, whereClauseMap);
        this.session = delegator;

    }

    @Override
    protected List<Order> doInBackground() throws Exception {
        listModel.clear();
        Debug.logError("start thread: start", module);
        List<Order> orders = new ArrayList<Order>();
        List<GenericValue> orderList = null;
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        /*Map<String, Object> svcRes = null;
         try {
         LocalDispatcher dispatcher = session.getDispatcher();
         svcRes = dispatcher.runSync("findOrders", whereClauseMap);
         for (Map.Entry<String, Object> entryDept : svcRes.entrySet()) {
         Debug.logError("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
         }
         if (svcRes.containsKey("orderList")) {
         orderList = (List<GenericValue>) svcRes.get("orderList");
         maxProgress = orderList.size() + 1;
         for (GenericValue genVal : orderList) {
         if (genVal != null) {
         //                        if (genVal.containsKey("orderId")) {
         Debug.logError("order Id:" + genVal.getString("orderId"), module);
         //  Order orderMax = OrderEntryHelper.loadOrderFromPersistance(genVal.getString("orderId"), session.getDelegator());
         //                    Order orderMax = loadOrder(genVal.getString("orderId"), session);
         //create order header
         OrderHeader orderHeader = new OrderHeader(genVal);

         //create our composite object
         Order orderMax = new Order(orderHeader.getOrderTypeId());
         orderMax.setOrderHeader(orderHeader);
         //get all roles so we can get parties related to this order
         ArrayList<OrderRoleComposite> array = new ArrayList<OrderRoleComposite>();


         //let set order composite to order
         OrderRolesList rolesList = new OrderRolesList();
         rolesList.addAll(array);
         //set it to order
         orderMax.setOrderRolesList(rolesList);
         orders.add(orderMax);
         publish(orderMax);
         Debug.logError("End order Id:" + genVal.getString("orderId"), module);
         }
         // }
         }
         }
         } catch (GenericServiceException ex1) {
         Debug.logError(ex1, module);
         try {
         //OrderMaxOptionPane.showMessageDialog(null,"dialog/error/exception", e.getMessage());
         throw new GeneralException(ServiceUtil.getErrorMessage(svcRes));
         } catch (GeneralException ex) {
         Logger.getLogger(LoadSalesOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
         }
         }*/

        // Simulate progress
        //        sleepAWhile();
        LocalDispatcher dispatcher = session.getDispatcher();

        String entityName = "OrderHeader";

        //List<GenericValue> genValList = PosProductHelper.getGenericValueListsWithSelection(entityName, whereClauseMap, null, session.getDelegator());
        String partyId = null;
        String roleTypeId = null;
        String productId = null;
        String orderTypeId = null;
        String statusId = null;

        List<GenericValue> genValList = getOrderDetails(partyId, roleTypeId,
                productId, orderTypeId, statusId, session.getDelegator());
        maxProgress = genValList.size() + 1;
        int i = 0;
        for (GenericValue genVal : genValList) {
            if (genVal != null) {
                if (genVal.containsKey("orderId")) {
                    Debug.logError("order Id:" + genVal.containsKey("orderId"), module);
                    //  Order orderMax = OrderEntryHelper.loadOrderFromPersistance(genVal.getString("orderId"), session.getDelegator());

                    Order orderMax = loadOrder(genVal.getString("orderId"), session);
                    //create order header
                    //OrderHeader orderHeader = new OrderHeader(genVal);

                    //create our composite object
                    //Order orderMax = new Order(orderHeader.getOrderTypeId());
                    //orderMax.setOrderHeader(orderHeader);
                    //get all roles so we can get parties related to this order
/*                    ArrayList<OrderRoleComposite> array = new ArrayList<OrderRoleComposite>();

                     List<GenericValue> orderRoleList = genVal.getRelated("OrderRole");
                     for (GenericValue orderRoleGen : orderRoleList) {
                     //create order role
                     OrderRole orderRole = new OrderRole(orderRoleGen);
                     //crreate the order composite
                     OrderRoleComposite orderRoleComp = new OrderRoleComposite();

                     //get party details
                     Account acct = PartyListSingleton.getAccount(orderRole.getpartyId());
                     //                        Account acct = LoadAccountWorker.getAccount(orderRole.getpartyId(), session);
                     orderRoleComp.setOrderRole(orderRole);
                     orderRoleComp.setParty(acct);
                     //add to list
                     array.add(orderRoleComp);
                     }

                     //let set order composite to order
                     OrderRolesList rolesList = new OrderRolesList();
                     rolesList.addAll(array);
                     //set it to order
                     orderMax.setOrderRolesList(rolesList);
                     */
                    orders.add(orderMax);
                    publish(orderMax);
                    if (isCancelled()) {
                        break;
                    }
                }
            }
        }

        Debug.logError("start thread: end", module);
        return orders;
    }
    
    public static OrderItemComposite addProduct(Order order, OrderItemComposite orderItem, String productId, String partyId, XuiSession session) throws Exception {
        //        Order order = panel.getOrder();
        //Key key = ProductTreeArraySingleton.getInstance().getProductFromId(productId);
        GenericValue product = null;
        if (UtilValidate.isNotEmpty(productId)) {
            try {
                product = ProductWorker.findProduct(session.getDelegator(), productId);
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }
        }
        //        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(prodId, order.getDestinationFacilityId(), session);
        //        OrderItemComposite orderItem = panel.getTableModel().getRowData(row);
        //        OrderItemComposite orderItem = orderItemCompositeListModel.getElementAt(row);

        /*if( record.getSupplierProductList() != null && 
         record.getSupplierProductList().getCurrentSupplierProduct()!=null &&
         record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct()!=null){
         return record.getSupplierProductList().getCurrentSupplierProduct().getSupplierProduct().getlastPrice();
         }*/
//        LoadProductPriceWorker
        //selling price
        orderItem.getOrderItem().setproductId(productId);
        ProductItemPrice pip = LoadProductPriceWorker.getProductItemPrice(productId, session);
        orderItem.setProductItemPrice(pip);

        //buying prices
        SupplierProductList spl = LoadSupplierProductWorker.getSupplierProductList(productId, partyId, order.getCurrency(), session);
        //supplier product doesn't exist
        //create one
        if (spl.getSize() == 0) {
            SupplierProductComposite spc = LoadSupplierProductWorker.createSupplierProduct(productId, order.getBillFromVendorPartyId(), session);
            spc.getSupplierProduct().setcurrencyUomId(order.getCurrency());
            spl.add(spc);
        }
        orderItem.setSupplierProductList(spl);

        orderItem.averageCost = LoadProductPriceWorker.getProductAveragePrice(productId, order.getOwnerPartyId(), order.getFacilityId(), session);

        //get inventory
        Map<String, Object> qopMap = LoadInventoryWorker.getInventoryAvailableByFacility(productId, order.getFacilityId(), session);
        BigDecimal availableToPromiseTotal = (BigDecimal) qopMap.get("availableToPromiseTotal");
        BigDecimal quantityOnHandTotal = (BigDecimal) qopMap.get("quantityOnHandTotal");
        orderItem.setQuantyOnHand(quantityOnHandTotal);
        orderItem.setAtp(availableToPromiseTotal);
        Debug.logInfo("pip.1: ", module);
        if ("SALES_ORDER".equals(order.getOrderType())) {
            Debug.logInfo("pip.2: ", module);
            if (pip != null && pip.getDefaultPrice().getSize() != 0) {
                orderItem.getOrderItem().setunitPrice(pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice());
                Debug.logInfo("pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice(): " + pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice(), module);
            }
            if (orderItem.getShoppingCartItem() == null) {
//                SupplierProductComposite supplierProductComp = orderItem.getSupplierProductList().getSupplerProduct(productId, order.getCurrency());
//                if (pip != null && pip.getDefaultPrice().getSize() != 0) {
//                    orderItem.getOrderItem().setunitPrice(pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice());
//                }
                int index = order.getShopingCart().addItemToEnd(orderItem.getOrderItem().getproductId(), orderItem.getOrderItem().getselectedAmount(), orderItem.getOrderItem().getquantity(), orderItem.getOrderItem().getunitPrice(), null, null, null, orderItem.getOrderItem().getorderItemTypeId(), null, session.getDispatcher(), true, true);
                ShoppingCartItem scItem = order.getShopingCart().findCartItem(index);
                orderItem.setShoppingCartItem(scItem);
            } else {
                if (orderItem.getShoppingCartItem().getProductId().equals(productId) == false) {
                    try {
                        int itemIdx = order.getShopingCart().getItemIndex(orderItem.getShoppingCartItem());
                        order.getShopingCart().removeCartItem(itemIdx, session.getDispatcher());
                        Iterator sciIter = order.getShopingCart().iterator();
                        while (sciIter.hasNext()) {
                            ShoppingCartItem curSci = (ShoppingCartItem) sciIter.next();
                            curSci.updatePrice(session.getDispatcher(), order.getShopingCart());
                        }
                    } catch (CartItemModifyException e) {
                        Debug.logError(e, module);
                    }
                    //                SupplierProductCompoBillingAccount = getSupplierProduct(orderItem, productId, panel.partyIdTextField.getText(), order.getOrderHeader().getCurrencyUom());
//                    SupplierProductComposite supplierProductComp = orderItem.getSupplierProductList().getSupplerProduct(productId, order.getCurrency());
//                    if (supplierProductComp != null) {
//                        orderItem.getOrderItem().setunitPrice(supplierProductComp.getSupplierProduct().getlastPrice());
//                    }

                    int index = order.getShopingCart().addItemToEnd(orderItem.getOrderItem().getproductId(), orderItem.getOrderItem().getselectedAmount(), orderItem.getOrderItem().getquantity(), orderItem.getOrderItem().getunitPrice(), null, null, null, orderItem.getOrderItem().getorderItemTypeId(), null, session.getDispatcher(), true, true);
                    ShoppingCartItem scItem = order.getShopingCart().findCartItem(index);
                    orderItem.setShoppingCartItem(scItem);
                }
            }

        } else {
            Debug.logInfo("pip.3: ", module);
            //                SupplierProductCompoBillingAccount = getSupplierProduct(orderItem, productId, panel.partyIdTextField.getText(), order.getOrderHeader().getCurrencyUom());
            SupplierProductComposite supplierProductComp = orderItem.getSupplierProductList().getSupplerProduct(partyId, order.getCurrency());
            if (supplierProductComp != null) {
                Debug.logInfo("supplierProductComp.getSupplierProduct().getlastPrice(): " + supplierProductComp.getSupplierProduct().getlastPrice(), module);
                orderItem.getOrderItem().setunitPrice(supplierProductComp.getSupplierProduct().getlastPrice());
                //orderItem.getOrderItem().setselectedAmount(supplierProductComp.getSupplierProduct().getlastPrice());
            }
            if (orderItem.getShoppingCartItem() == null) {
//                SupplierProductComposite supplierProductComp = orderItem.getSupplierProductList().getSupplerProduct(productId, order.getCurrency());
//                if (supplierProductComp != null) {
//                    orderItem.getOrderItem().setunitPrice(supplierProductComp.getSupplierProduct().getlastPrice());
//                }
                int index = order.getShopingCart().addItemToEnd(orderItem.getOrderItem().getproductId(), orderItem.getOrderItem().getselectedAmount(), orderItem.getOrderItem().getquantity(), orderItem.getOrderItem().getunitPrice(), null, null, null, orderItem.getOrderItem().getorderItemTypeId(), null, session.getDispatcher(), true, true);
                ShoppingCartItem scItem = order.getShopingCart().findCartItem(index);
                orderItem.setShoppingCartItem(scItem);
                //now sink the values back again..
                scItem.setBasePrice(orderItem.getOrderItem().getunitPrice());

            } else {
                if (orderItem.getShoppingCartItem().getProductId().equals(productId) == false) {
                    try {
                        int itemIdx = order.getShopingCart().getItemIndex(orderItem.getShoppingCartItem());
                        order.getShopingCart().removeCartItem(itemIdx, session.getDispatcher());
                        Iterator sciIter = order.getShopingCart().iterator();
                        while (sciIter.hasNext()) {
                            ShoppingCartItem curSci = (ShoppingCartItem) sciIter.next();
                            curSci.updatePrice(session.getDispatcher(), order.getShopingCart());
                        }
                    } catch (CartItemModifyException e) {
                        Debug.logError(e, module);
                    }

                    int index = order.getShopingCart().addItemToEnd(orderItem.getOrderItem().getproductId(), orderItem.getOrderItem().getselectedAmount(), orderItem.getOrderItem().getquantity(), orderItem.getOrderItem().getunitPrice(), null, null, null, orderItem.getOrderItem().getorderItemTypeId(), null, session.getDispatcher(), true, true);
                    ShoppingCartItem scItem = order.getShopingCart().findCartItem(index);
                    orderItem.setShoppingCartItem(scItem);
                    scItem.setBasePrice(orderItem.getOrderItem().getunitPrice());
                }
            }

        }
        order.addOrderItem(orderItem);
        if (product != null) {
            orderItem.getOrderItem().setitemDescription(product.getString("internalName"));
        }
        LoadOrderWorker.calculateProductItemValues(orderItem);
        //sur        orderItem.setupOrderItemData(order.getPartyId());
        //        Debug.logInfo("addProductById : " + key.toString(), module);
        //        Debug.logInfo("After update : " + orderItem.getOrderItem().getitemDescription(), module);
        //        Debug.logInfo("orderItem.getOrderItem().getunitPrice() : " + orderItem.getOrderItem().getunitPrice(), module);
        return orderItem;
        //
    }

    public static Order newPurchaseOrder(XuiSession session, String OrderTypeId,
            Account billFrom, Account billTo,
            String productStoreId, String facilityId, String currency) {
        //shopping cart
//        String productStoreId = "9000";//(String) session.getAttribute("productStoreId");
//        String facilityId = (String) session.getAttribute("facilityId");
//        String currency = (String) session.getAttribute("currency");
        Locale locale = Locale.getDefault();
        Order order = new Order(session.getDelegator(), productStoreId, null, locale, currency, billTo.getParty().getpartyId(), billFrom.getParty().getpartyId());
        order.setOrderType(OrderTypeId);
        order.setOrderDate(UtilDateTime.nowTimestamp());
        order.setOrderStatusId("ORDER_CREATED");
        order.setFacilityId(facilityId);
        order.setBillFromVendorPartyId(billFrom.getParty().getpartyId());
        order.setShipFromVendorPartyId(billFrom.getParty().getpartyId());
        order.setSupplierAgentPartyId(billFrom.getParty().getpartyId());
        order.setOrderPartyId(billTo.getParty().getpartyId());
        //        shopingCart.setCreatedBy(session.getUserPartyId());

        //order.setShopingCart(shopingCart);
        //        Order order = new Order(shopingCart);
        //Debug.logInfo("createSupplierOrderRoles :", module);
        String roleTypeId = "BILL_TO_CUSTOMER";
        order.setBillToAccount(createOrderRole(roleTypeId, billTo));
        roleTypeId = "BILL_FROM_VENDOR";
        order.setBillFromAccount(createOrderRole(roleTypeId, billFrom));
        //        order.setOrderHeader(orderHeader);
        //        order.setOwnerPartyId(PosProductHelper.organizationPartyId);
        //let set order composite to order
        OrderRolesList rolesList = new OrderRolesList();
        //        createSupplierOrderRoles(rolesList, billFrom, billTo);
        order.setOrderRolesList(rolesList);
        order.setOrderStatusId("ORDER_CREATED");
        //create order item list
        OrderItemsList orderItmList = new OrderItemsList();
        order.setOrderItemsList(orderItmList);
        //new order status
        OrderStatus osItem = new OrderStatus();
        osItem.setorderItemSeqId(null);
        osItem.setstatusId("ORDER_CREATED");
        osItem.setorderItemSeqId(null);
        osItem.setstatusId(order.getOrderStatusId());
        osItem.setstatusUserLogin(session.getUserId());
        osItem.setstatusDatetime(UtilDateTime.nowTimestamp());
        order.setOrderStatusList(new OrderStatusList());
        order.getOrderStatusList().add(osItem);
        //        PartyContactMechComposite pc = billFrom.getPartyContactList().getBillingLocationContact();
        OrderContactMechCompositeList oclArray = new OrderContactMechCompositeList();
//        OrderContactList ocl = new OrderContactList();
        createSupplierOrderContacts(oclArray, billTo);

        order.setOrderContactList(oclArray);


        order.setOrderItemShipGroupList(new OrderItemShipGroupList());
        //        order.getOrderItemShipGroupList().add(oisg);
        //        ShoppingCart shopingCart = new ShoppingCart(session.getDelegator(), productStoreId, locale, currency);
        //        shopingCart.setOrderType(orderHeader.getOrderTypeId());
        //        shopingCart.setOrderPartyId("_NA_");
        //Debug.logInfo("billFromPartyId: " + billFromPartyId, "module");
        //Debug.logInfo("billToPartyId: " + billToPartyId, "module");

        //        shopingCart.setSu(order.getBillFromAccount().getParty().getParty().getpartyId());
        order.setDefaultCheckoutOptions(session.getDispatcher());
        List<ShoppingCart.CartShipInfo> shipGroups = order.getShipGroups();
        for (ShoppingCart.CartShipInfo shipCart : shipGroups) {
            Debug.logInfo(shipCart.getContactMechId(), module);
            Debug.logInfo(shipCart.getFacilityId(), module);
            Debug.logInfo(shipCart.getOrderTypeId(), module);
            Debug.logInfo(shipCart.getSupplierPartyId(), module);
            Debug.logInfo(shipCart.getVendorPartyId(), module);
            Debug.logInfo(shipCart.getOrderTypeId(), module);
            Debug.logInfo(shipCart.orderTypeId, module);
            //        Debug.logInfo(shipCart.getInternalContactMechId(),module);
            Debug.logInfo(shipCart.telecomContactMechId, module);
            Debug.logInfo(shipCart.shipmentMethodTypeId, module);
            Debug.logInfo(shipCart.supplierPartyId, module);
            Debug.logInfo(shipCart.carrierRoleTypeId, module);
            Debug.logInfo(shipCart.carrierPartyId, module);
            Debug.logInfo(shipCart.getFacilityId(), module);
            Debug.logInfo(shipCart.giftMessage, module);
            Debug.logInfo(shipCart.shippingInstructions, module);
            Debug.logInfo(shipCart.maySplit, module);
            Debug.logInfo(shipCart.isGift, module);
            //        Debug.logInfo(shipCart.shipEstimate. ,module);
            //        Debug.logInfo(shipCart.shipBeforeDat,module);
            //        Debug.logInfo(shipCart.shipAfterDate,module);
            Debug.logInfo(shipCart.getShipGroupSeqId(), module);
            //        Debug.logInfo(shipCart.associatedShipGroupSeqId,module);
            Debug.logInfo(shipCart.vendorPartyId, module);
            Debug.logInfo(shipCart.productStoreShipMethId, module);
        }
        Debug.logInfo("get party id: " + order.getBillFromVendorPartyId(), module);
        return order;
    }

}
