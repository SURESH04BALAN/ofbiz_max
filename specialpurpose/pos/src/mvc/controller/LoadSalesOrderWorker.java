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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javolution.util.FastMap;
import org.ofbiz.accounting.payment.BillingAccountWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ItemNotFoundException;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderContact;
import org.ofbiz.ordermax.composite.OrderContactList;
import org.ofbiz.ordermax.composite.OrderContactMechComposite;
import org.ofbiz.ordermax.composite.OrderContactMechCompositeList;
import org.ofbiz.ordermax.composite.OrderRoleComposite;
import org.ofbiz.ordermax.composite.OrderRolesList;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupList;
import org.ofbiz.ordermax.composite.OrderItemsList;
import org.ofbiz.ordermax.composite.OrderStatusList;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.composite.ProductItemPrice;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.OrderContactMech;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.OrderItemShipGroup;
import org.ofbiz.ordermax.entity.OrderRole;
import org.ofbiz.ordermax.entity.OrderStatus;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.product.config.ProductConfigWrapper;
import org.ofbiz.product.product.ProductWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadSalesOrderWorker extends LoadOrderWorker {

    public static final String module = LoadSalesOrderWorker.class.getName();

    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<Order> orderListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;

    public LoadSalesOrderWorker(ListAdapterListModel<Order> orderListModel, XuiSession delegator, Map<String, Object> whereClauseMap) {
        super(orderListModel, whereClauseMap);
        this.orderListModel = orderListModel;
        this.session = delegator;
    }

    public LoadSalesOrderWorker(XuiSession session) {
        super(null, null);
        this.session = session;
    }

    @Override
    protected List<Order> doInBackground() throws Exception {
        orderListModel.clear();

        List<Order> orders = new ArrayList<Order>();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        // Simulate progress
        sleepAWhile();
        String entityName = "OrderHeader";

        Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//                    whereClauseMap.put("partyId", partyIdTextField.getText());
//                    whereClauseMap.put("roleTypeId", "BILL_FROM_VENDOR");

        List<GenericValue> genValList = PosProductHelper.getGenericValueListsWithSelection(entityName, whereClauseMap, null, session.getDelegator());
        maxProgress = genValList.size() + 1;
        for (GenericValue genVal : genValList) {
            if (genVal != null) {
                if (genVal.containsKey("orderId")) {

                    //  Order orderMax = OrderEntryHelper.loadOrderFromPersistance(genVal.getString("orderId"), session.getDelegator());
                    Order orderMax = loadOrder(genVal.getString("orderId"), session);
                    orders.add(orderMax);
                    publish(orderMax);

                }
            }
        }

        return orders;
    }

    /*
     public static Order newOrder(XuiSession session, String OrderTypeId, Account billTo, Account billFrom, String productStoreId) {

     Order order = null;

     //get order genericvalue
     //create order header
     OrderHeader orderHeader = new OrderHeader();
     orderHeader.setStatusId("ORDER_CREATED");
     orderHeader.setOrderTypeId(OrderTypeId);
     orderHeader.setSalesChannelEnumId("PHONE_SALES_CHANNEL");
     orderHeader.setOrderDate(UtilDateTime.nowTimestamp());
     orderHeader.setPriority("1");
     orderHeader.setEntryDate(UtilDateTime.nowTimestamp());
     orderHeader.setCreatedBy(session.getUserPartyId());
     //    order.setorderId(delegator.getNextSeqId("OrderHeader"));
     orderHeader.setRemainingSubTotal(BigDecimal.ZERO);
     orderHeader.setGrandTotal(BigDecimal.ZERO);
     orderHeader.setCurrencyUom(UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
     Debug.logInfo("productStoreId: " + productStoreId, module);
     String facilityId = (String) session.getAttribute("facilityId");
     String currency = (String) session.getAttribute("currency");
     //        this.locale = (Locale) session.getAttribute("locale"); This is legacy code and may come (demo) from ProductStore.defaultLocaleString defined in demoRetail and is incompatible with how localisation is handled in the POS
     Locale locale = Locale.getDefault();
        
     //        order.setPartyName(org.ofbiz.party.party.PartyHelper.getPartyName(delegator, order.getPartyId(), false));
     //        order.setPartyId(partyIdTextField.getText());
     //create our composite object
     ShoppingCart shopingCart = new ShoppingCart(session.getDelegator(), productStoreId, null, locale,
     currency, billTo.getParty().getpartyId(), billFrom.getParty().getpartyId());
     shopingCart.setOrderType(OrderTypeId);
        
     order = new Order(shopingCart);
     order.setOrderHeader(orderHeader);

     //        order.setOwnerPartyId(PosProductHelper.organizationPartyId);
     order.setFacilityId(session.getFacility().getfacilityId());

     //let set order composite to order
     OrderRolesList rolesList = new OrderRolesList();
     createSupplierOrderRoles(rolesList, billTo, billFrom);
     order.setOrderRolesList(rolesList);

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
     OrderContactList ocl = new OrderContactList();
     createCustomerOrderContacts(ocl, billTo);
     order.setOrderContactList(ocl);

     String shipmentMethodTypeId = "NO_SHIPPING";
     String carrierPartyId = "_NA_";
     String carrierRoleTypeId = "CARRIER";
     String maySplit = "N";
     String isGift = "N";
     Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();

     order.setOrderItemShipGroupList(new OrderItemShipGroupList());
     //        order.getOrderItemShipGroupList().add(oisg);

     //shopping cart
     //        String productStoreId = (String) session.getAttribute("productStoreId");
  

     //        ShoppingCart shopingCart = new ShoppingCart(session.getDelegator(), productStoreId, locale, currency);
     //        shopingCart.setOrderType(orderHeader.getOrderTypeId());
     //        shopingCart.setOrderPartyId("_NA_");
     String billFromPartyId = order.getBillFromAccount().getParty().getParty().getpartyId();
     String billToPartyId = order.getBillToAccount().getParty().getParty().getpartyId();

     //        order.setShopingCart(shopingCart);
     Debug.logInfo("billFromPartyId: " + billFromPartyId, "module");
     Debug.logInfo("billToPartyId: " + billToPartyId, "module");

     shopingCart.setBillFromVendorPartyId(billFromPartyId);
     shopingCart.setShipFromVendorPartyId(billFromPartyId);
     shopingCart.setSupplierAgentPartyId(billFromPartyId);
     shopingCart.setOrderPartyId(billToPartyId);
     //        shopingCart.setSu(order.getBillFromAccount().getParty().getParty().getpartyId());        

     shopingCart.setFacilityId(order.getFacilityId());
     shopingCart.setDefaultCheckoutOptions(session.getDispatcher());

     return order;
     }
     */
    public static Order newSalesOrder(XuiSession session, String OrderTypeId,
            Account billTo, Account billFrom,
            String productStoreId, String facilityId, String currencyUomId, String shipmentMethodTypeId) {
        //shopping cart
//        String productStoreId = "9000";//(String) session.getAttribute("productStoreId");
//        String facilityId = (String) session.getAttribute("facilityId");
//        String currency = (String) session.getAttribute("currency");
        Locale locale = Locale.getDefault();
        Order order = new Order(session.getDelegator(), productStoreId, null, locale, currencyUomId, billTo.getParty().getpartyId(), billFrom.getParty().getpartyId());
         OrderHeader orderHeader = new OrderHeader();
         orderHeader.setStatusId("ORDER_CREATED");
         orderHeader.setOrderTypeId(OrderTypeId);
         order.setOrderHeader(orderHeader);
        
        order.setOrderType(OrderTypeId);
        order.setOrderDate(UtilDateTime.nowTimestamp());
        order.setOrderStatusId("ORDER_CREATED");
        order.setFacilityId(facilityId);
        order.setBillFromVendorPartyId(billFrom.getParty().getpartyId());
        order.setShipFromVendorPartyId(billFrom.getParty().getpartyId());
        order.setSupplierAgentPartyId(billFrom.getParty().getpartyId());
        order.setOrderPartyId(billTo.getParty().getpartyId());

        try {
            BillingAccount billingAccount = LoadBillingAccountWorker.getPartyBillingAccount(currencyUomId, billTo.getParty().getpartyId(), session.getDelegator());
            if (billingAccount != null) {
                order.setBillingAccount(billingAccount.getbillingAccountId(), billingAccount.getaccountLimit());
                BigDecimal bigDecimal = BillingAccountWorker.getBillingAccountBalance(billingAccount.getGenericValueObj());
                order.setBillingAccountBalance(bigDecimal);
                order.setBillingAccountLimit(billingAccount.getaccountLimit());
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadSalesOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralException ex) {
            Logger.getLogger(LoadSalesOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        //        shopingCart.setCreatedBy(session.getUserPartyId());
        /*
         //get order genericvalue
         //create order header
         OrderHeader orderHeader = new OrderHeader();
         orderHeader.setStatusId("ORDER_CREATED");
         orderHeader.setOrderTypeId(OrderTypeId);
         orderHeader.setSalesChannelEnumId("PHONE_SALES_CHANNEL");
         orderHeader.setOrderDate(UtilDateTime.nowTimestamp());
         orderHeader.setPriority("2");
         //    order.setorderId(delegator.getNextSeqId("OrderHeader"));
         orderHeader.setRemainingSubTotal(BigDecimal.ZERO);
         orderHeader.setGrandTotal(BigDecimal.ZERO);
         orderHeader.setCurrencyUom(UtilProperties.getPropertyValue("general", "currency.uom.id.default"));
         //        order.setPartyName(org.ofbiz.party.party.PartyHelper.getPartyName(delegator, order.getPartyId(), false));
         //        order.setPartyId(partyIdTextField.getText());
         //create our composite object
         String billFromPartyId = order.getBillFromAccount().getParty().getParty().getpartyId();
         String billToPartyId = order.getBillToAccount().getParty().getParty().getpartyId();
         */
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
//        OrderContactList ocl = new OrderContactList();

        PartyContactMechCompositeList mechList = LoadAccountWorker.getPartyContactMechList(billTo.getParty().getpartyId(), session);
        billTo.setPartyContactList(mechList);
        OrderContactMechCompositeList oclArray = createCustomerOrderContacts(billTo);
        order.setOrderContactList(oclArray);
        
        createShippingGroup(order, shipmentMethodTypeId, billTo);
  /*      
//set order shipping
        ShoppingCart.CartShipInfo cartShipInfo = order.getShipInfo(0);
        cartShipInfo.shipmentMethodTypeId = shipmentMethodTypeId;
        cartShipInfo.carrierPartyId = "_NA_";
        cartShipInfo.carrierRoleTypeId = "CARRIER";
        cartShipInfo.maySplit = "N";
        cartShipInfo.isGift = "N";
//        cartShipInfo. = UtilDateTime.nowTimestamp();
        OrderItemShipGroup oisg = new OrderItemShipGroup();
        oisg.setshipmentMethodTypeId(cartShipInfo.shipmentMethodTypeId);
        oisg.setcarrierPartyId(cartShipInfo.carrierPartyId);
        oisg.setcarrierRoleTypeId(cartShipInfo.carrierRoleTypeId);
        oisg.setmaySplit(cartShipInfo.maySplit);
        oisg.setisGift(cartShipInfo.isGift);
//        oisg.setestimatedDeliveryDate(estimatedDeliveryDate);
        order.setOrderItemShipGroupList(new OrderItemShipGroupList());
        order.getOrderItemShipGroupList().add(oisg);

        if (order.getContactMechId("SHIPPING_LOCATION") != null) {
            //OrderContactMechComposite contact = order.getOrderContactList().getShippingLocationContact();
            cartShipInfo.setContactMechId(order.getContactMechId("SHIPPING_LOCATION"));
            oisg.setcontactMechId(order.getContactMechId("SHIPPING_LOCATION"));
        } else {
            if (order.getContactMechId("BILLING_LOCATION") != null) {
                cartShipInfo.setContactMechId(order.getContactMechId("BILLING_LOCATION"));
                oisg.setcontactMechId(order.getContactMechId("BILLING_LOCATION"));
            }

        }
*/
        //terms
        order.addOrderTerm("FINANCIAL_TERM", new BigDecimal("10000"), new Long("60"));
        order.addPayment("EXT_BILLACT");

        //        ShoppingCart shopingCart = new ShoppingCart(session.getDelegator(), productStoreId, locale, currency);
        //        shopingCart.setOrderType(orderHeader.getOrderTypeId());
        //        shopingCart.setOrderPartyId("_NA_");
        //Debug.logInfo("billFromPartyId: " + billFromPartyId, "module");
        //Debug.logInfo("billToPartyId: " + billToPartyId, "module");
        /*   Debug.logInfo("billFromPartyId: " + billFrom.getParty().getpartyId(), "module");
         shopingCart.setBillFromVendorPartyId( billFrom.getParty().getpartyId());
         shopingCart.setShipFromVendorPartyId(billFrom.getParty().getpartyId());
         shopingCart.setSupplierAgentPartyId(billFrom.getParty().getpartyId());
         shopingCart.setOrderPartyId(billTo.getParty().getpartyId());*/
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

    //create order role composite
    static public OrderRoleComposite createOrderRole(String roleTypeId, Account account) {
        OrderRoleComposite orc = new OrderRoleComposite();
        orc.setParty(account);
        OrderRole role = new OrderRole();
        role.setroleTypeId(roleTypeId);
        role.setpartyId(account.getParty().getpartyId());
        orc.setOrderRole(role);
        return orc;
    }

    public static OrderContactList loadOrderContactList(final String orderId, XuiSession session) {

        OrderContactList ocl = new OrderContactList();
        try {
            //get order genericvalue
            GenericValue orderGenVal = OrderReadHelper.getOrderHeader(session.getDelegator(), orderId);

            //get all roles so we can get parties related to this order
            ArrayList<OrderRoleComposite> array = new ArrayList<OrderRoleComposite>();

            List<GenericValue> orderRoleList = orderGenVal.getRelated("OrderRole");
            for (GenericValue orderRoleGen : orderRoleList) {
                //create order role
                OrderRole orderRole = new OrderRole(orderRoleGen);
                //crreate the order composite
                OrderRoleComposite orderRoleComp = new OrderRoleComposite();

                //get party details
                Account account = PartyListSingleton.getAccount(orderRole.getpartyId());//, session);

                account.setPartyContactList(LoadAccountWorker.getPartyContactMechList(account.getParty().getpartyId(), session));
                orderRoleComp.setOrderRole(orderRole);
                orderRoleComp.setParty(account);
                //add to list
                array.add(orderRoleComp);
            }

            //let set order composite to order
            OrderRolesList rolesList = new OrderRolesList();
            rolesList.addAll(array);

            //get order contacts            
            ArrayList<OrderContact> oclArray = new ArrayList<OrderContact>();

            List<GenericValue> orderContachMapList = orderGenVal.getRelated("OrderContactMech");
            for (GenericValue orderContactMech : orderContachMapList) {
                OrderContact oc = new OrderContact();
                OrderContactMech ocm = new OrderContactMech(orderContactMech);
                oc.setOrderContactMech(ocm);
                Debug.logInfo("ocm.getcontactMechId() " + ocm.getcontactMechId(), module);
                PartyContactMechComposite pc = rolesList.getPartyContact(ocm.getcontactMechId());
                if (pc != null) {
                    oc.setContact(pc.getContact());
                    oclArray.add(oc);
                }
            }

            ocl.addAll(oclArray);

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadSalesOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoadSalesOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ocl;
    }

    static public OrderContactMechCompositeList createCustomerOrderContacts(Account billFrom) {
        OrderContactMechCompositeList ocl = new OrderContactMechCompositeList();
        Debug.logInfo("createSupplierOrderContacts :", module);
        PartyContactMechComposite pc = getPartyContactFromPurpose(billFrom, "BILLING_LOCATION");//billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");
        Debug.logInfo("BILLING_LOCATION 1 :", module);
        if (pc != null) {
            Debug.logInfo("BILLING_LOCATION 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
            ocl.add(createSupplierOrderContact(pc, "BILLING_LOCATION"));
        }
        Debug.logInfo("BILLING_LOCATION 3 :", module);
        pc = getPartyContactFromPurpose(billFrom, "PHONE_BILLING");//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("PHONE_BILLING");
        if (pc != null) {
            Debug.logInfo("BILLING_LOCATION 4 :", module);
            ocl.add(createSupplierOrderContact(pc, "PHONE_BILLING"));
        }
        Debug.logInfo("BILLING_LOCATION 5 :", module);

        pc = getPartyContactFromPurpose(billFrom, "BILLING_EMAIL");//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_EMAIL");
        if (pc != null) {
            ocl.add(createSupplierOrderContact(pc, "BILLING_EMAIL"));
        }

        pc = getPartyContactFromPurpose(billFrom, "FAX_BILLING");;//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("FAX_BILLING");
        if (pc != null) {
            ocl.add(createSupplierOrderContact(pc, "FAX_BILLING"));
        }
        pc = getPartyContactFromPurpose(billFrom, "PHONE_SHIPPING");;//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("PHONE_SHIPPING");
        if (pc != null) {
            ocl.add(createSupplierOrderContact(pc, "PHONE_SHIPPING"));
        }

        //pc = getPartyContactFromPurpose(billFrom, "SHIPPING_LOCATION");;//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("SHIPPING_LOCATION");
        //if (pc != null) {
        //    Debug.logInfo("SHIPPING 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
        //    ocl.add(createSupplierOrderContact(pc, "SHIPPING_LOCATION"));
        //}
        return ocl;
    }


    /*
     static public OrderItemComposite newOrderItem(String productId, String productDesc, XuiSession session) {
     OrderItemComposite oic = new OrderItemComposite();

     OrderItem orderIt = new OrderItem();
     orderIt.setstatusId("ITEM_CREATED");
     orderIt.setproductId(productId);
     orderIt.setisModifiedPrice("Y");
     orderIt.setisPromo("N");

     orderIt.setitemDescription(productDesc);
     oic.setOrderItem(orderIt);
     oic.setOrderItemBillingsList(new OrderItemBillingsList());

     //new order status
     OrderStatus osItem = new OrderStatus();
     osItem.setstatusId("ITEM_CREATED");
     osItem.setstatusUserLogin(session.getUserId());
     oic.setOrderStatusList(new OrderStatusList());
     oic.getOrderStatusList().add(osItem);

     oic.setOrderItemShipGroupAssocsList(new OrderItemShipGroupAssocsList());

     ProductItemPrice pip;
     try {
     pip = LoadProductPriceWorker.getProductItemPrice(productId, session);
     oic.setProductItemPrice(pip);

     SupplierProductList spl = LoadProductPriceWorker.getSupplierProduct(productId, session);
     oic.setSupplierProductList(spl);

     } catch (GenericEntityException ex) {
     Logger.getLogger(LoadSalesOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
     }
     return oic;
     }
     */
    /*  static public void removeOrderItem(Order order, OrderItemComposite orderItemComposite, XuiSession session) throws CartItemModifyException {

     LocalDispatcher dispatcher = session.getDispatcher();

     Map<String, Object> svcCtx = FastMap.newInstance();
     svcCtx.put("userLogin", session.getUserLogin());
     svcCtx.put("orderId", orderItemComposite.getOrderItem().getorderId());
     svcCtx.put("orderItemSeqId", orderItemComposite.getOrderItem().getorderItemSeqId());

     Map<String, Object> svcRes = null;
     try {
     svcRes = dispatcher.runSync("cancelOrderItem", svcCtx);
     for (Map.Entry<String, Object> entryDept : svcRes.entrySet()) {
     Debug.logInfo("key: " + entryDept.getKey()
     + " value: " + entryDept.getValue(), module);

     }
     } catch (GenericServiceException e) {
     Debug.logError(e, module);
     OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
     }
     order.getOrderItemsList().remove(orderItemComposite);

     }
     */
    /*  static public OrderItemComposite newOrderItem(XuiSession session) {
     OrderItemComposite oic = new OrderItemComposite();

     OrderItem orderIt = new OrderItem();
     orderIt.setstatusId("ITEM_CREATED");
     String orderItemTypeId = "PRODUCT_ORDER_ITEM";
     orderIt.setorderItemTypeId(orderItemTypeId);
     orderIt.setisModifiedPrice("Y");
     orderIt.setisPromo("N");

     //        orderIt.setproductId("");
     //        orderIt.setitemDescription("Hello World");
     oic.setOrderItem(orderIt);
     oic.setOrderItemBillingsList(new OrderItemBillingsList());

     //new order status
     OrderStatus osItem = new OrderStatus();
     osItem.setstatusId("ITEM_CREATED");
     osItem.setstatusUserLogin(session.getUserId());
     oic.setOrderStatusList(new OrderStatusList());
     oic.getOrderStatusList().add(osItem);

     oic.setOrderItemShipGroupAssocsList(new OrderItemShipGroupAssocsList());

     return oic;
     }
     */
    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;

    }

    static public ShoppingCart loadShopingCart(String orderId, XuiSession session) {

        LocalDispatcher dispatcher = session.getDispatcher();

        Map<String, Object> svcCtx = FastMap.newInstance();
        svcCtx.put("userLogin", session.getUserLogin());
        svcCtx.put("orderId", orderId);
        svcCtx.put("skipInventoryChecks", Boolean.TRUE);
        svcCtx.put("skipProductChecks", Boolean.TRUE);

        Map<String, Object> svcRes = null;
        try {
            svcRes = dispatcher.runSync("loadCartFromOrder", svcCtx);
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        }

        if (svcRes == null) {
            Debug.logInfo("EcommerceNoShoppingListsCreate", module);
        } else if (ServiceUtil.isError(svcRes)) {
            Debug.logError(ServiceUtil.getErrorMessage(svcRes) + " - " + svcRes, module);
        } else {
            ShoppingCart shoppingCart = (ShoppingCart) svcRes.get("shoppingCart");

            shoppingCart.setOrderId(orderId);
            return shoppingCart;
        }

        return null;
    }

    static public void setShoppingCartItemToItemComposite(Order order1, ShoppingCart shopingCart) {
        for (OrderItemComposite oicIter : order1.getOrderItemsList().getList()) {
            if (oicIter.getShoppingCartItem() == null) {
                List<ShoppingCartItem> cartItemList = shopingCart.findAllCartItems(oicIter.getOrderItem().getproductId());
                for (ShoppingCartItem sciIter : cartItemList) {

                    if (UtilValidate.isNotEmpty(sciIter.getOrderItemSeqId()) && sciIter.getOrderItemSeqId().equals(oicIter.getOrderItem().getorderItemSeqId())) {
                        oicIter.setShoppingCartItem(sciIter);
                        Debug.logInfo("found shoppingCart item prodId: " + oicIter.getOrderItem().getproductId()
                                + "order Id: " + oicIter.getOrderItem().getorderId()
                                + "order Seq Id: " + oicIter.getOrderItem().getorderItemSeqId(), module);

                        break;
                    }
                }
            }

        }
        Debug.logInfo("shopingCart.getOrderContactMechIds() - start: ", module);
        Map<String, String> orderMechIds = shopingCart.getOrderContactMechIds();
        for (Map.Entry<String, String> entryDept : orderMechIds.entrySet()) {
            Debug.logInfo("entryDept purpose: " + entryDept.getKey()
                    + "mech Id: " + entryDept.getValue(), module);

        }

    }

    //   @Override
    static public OrderItemComposite addProduct(Order order, OrderItemComposite orderItem, String productId, String partyId) throws Exception {
        Debug.logInfo("old cart size: " + order.getShopingCart().size(), module);

        GenericValue product = null;
        if (UtilValidate.isNotEmpty(productId)) {
            try {
                product = ProductWorker.findProduct(ControllerOptions.getSession().getDelegator(), productId);
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                throw e;
            }
        }

        //try to add to cart first
        ShoppingCartItem scItem = addItem(order.getShopingCart(), productId, orderItem.getOrderItem().getquantity());
        if (scItem != null) {
            orderItem.getOrderItem().setitemDescription(scItem.getName());
            orderItem.setShoppingCartItem(scItem);
        }

        orderItem.getOrderItem().setproductId(productId);
        ProductItemPrice pip = LoadProductPriceWorker.getProductItemPrice(productId, ControllerOptions.getSession());
        orderItem.setProductItemPrice(pip);

        //buying prices
        /*SupplierProductList spl = LoadSupplierProductWorker.getSupplierProductList(productId, partyId, order.getCurrency(), session);
         //supplier product doesn't exist
         //create one
         if (spl.getSize() == 0) {
         SupplierProductComposite spc = LoadSupplierProductWorker.createSupplierProduct(productId, order.getBillFromVendorPartyId(), session);
         spc.getSupplierProduct().setcurrencyUomId(order.getCurrency());
         spl.add(spc);
         }
        
         orderItem.setSupplierProductList(spl);
         */
        orderItem.averageCost = LoadProductPriceWorker.getProductAveragePrice(productId, order.getOwnerPartyId(), order.getFacilityId(), ControllerOptions.getSession());

        //get inventory
        Map<String, Object> qopMap = LoadInventoryWorker.getInventoryAvailableByFacility(productId, order.getFacilityId(), ControllerOptions.getSession());
        BigDecimal availableToPromiseTotal = (BigDecimal) qopMap.get("availableToPromiseTotal");
        BigDecimal quantityOnHandTotal = (BigDecimal) qopMap.get("quantityOnHandTotal");
        orderItem.setQuantyOnHand(quantityOnHandTotal);
        orderItem.setAtp(availableToPromiseTotal);
        Debug.logInfo("pip.1: ", module);
//      if ("SALES_ORDER".equals(order.getOrderType())) {
        Debug.logInfo("pip.2: ", module);
        if (pip != null && pip.getDefaultPrice().getSize() != 0) {
            orderItem.getOrderItem().setunitPrice(pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice());
            Debug.logInfo("pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice(): " + pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice(), module);
        }

        Debug.logInfo("new cart size: " + order.getShopingCart().size(), module);

        /*if (orderItem.getShoppingCartItem() == null) {
         //                SupplierProductComposite supplierProductComp = orderItem.getSupplierProductList().getSupplerProduct(productId, order.getCurrency());
         //                if (pip != null && pip.getDefaultPrice().getSize() != 0) {
         //                    orderItem.getOrderItem().setunitPrice(pip.getDefaultPrice().getCurrentPrice().getProductPrice().getprice());
         //                }
         int index = order.getShopingCart().addItemToEnd(orderItem.getOrderItem().getproductId(), orderItem.getOrderItem().getselectedAmount(), orderItem.getOrderItem().getquantity(), orderItem.getOrderItem().getunitPrice(), null, null, null, orderItem.getOrderItem().getorderItemTypeId(), null, session.getDispatcher(), true, true);
         ShoppingCartItem scItem = order.getShopingCart().findCartItem(index);
         orderItem.setShoppingCartItem(scItem);
         orderItem.getOrderItem().setitemDescription(scItem.getName());
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
         orderItem.getOrderItem().setitemDescription(scItem.getName());                    
         }
         }*/
//      }
        //order.addOrderItem(orderItem);
        /*
         if (product != null) {
         orderItem.getOrderItem().setitemDescription(product.getString("internalName"));
         }
         */
        LoadOrderWorker.calculateProductItemValues(orderItem);
        //sur        orderItem.setupOrderItemData(order.getPartyId());
        //        Debug.logInfo("addProductById : " + key.toString(), module);
        //        Debug.logInfo("After update : " + orderItem.getOrderItem().getitemDescription(), module);
        //        Debug.logInfo("orderItem.getOrderItem().getunitPrice() : " + orderItem.getOrderItem().getunitPrice(), module);
        return orderItem;
        //
    }

    static public ShoppingCartItem addItem(ShoppingCart cart, String productId, BigDecimal quantity) throws CartItemModifyException, ItemNotFoundException {
        Debug.logInfo("add item", productId + "/" + quantity, module);
        BigDecimal basePrice = null;
        ShoppingCartItem item = null;
        /*try{
         throw new Exception("Add Item");        
         } catch (Exception e) {
         trace("general exception", e);
         Debug.logError(e, module);
         }*/
        try {

            Delegator delegator = cart.getDelegator();
            GenericValue product = null;
            ProductConfigWrapper pcw = null;
            product = delegator.findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));

            if (UtilValidate.isNotEmpty(product) && "AGGREGATED".equals(product.getString("productTypeId"))) {
                // if it's an aggregated item, load the configwrapper and set to defaults
                pcw = new ProductConfigWrapper(delegator, ControllerOptions.getSession().getDispatcher(), productId, null, null, null, null, null, null);
                pcw.setDefaultConfig();
            }

            item = cart.findCartItem(productId, null, null, null, BigDecimal.ZERO);
            if (item != null) {
                basePrice = item.getBasePrice();
            }

//cart.addOrIncreaseItem(productId, null, quantity, null, null, null, null, null, null, null, null, null, null, null, null, session.getDispatcher());
            int index = cart.addOrIncreaseItem(productId, null, quantity, null, null, null, null, null, null, null, null, pcw, null, null, null, ControllerOptions.getSession().getDispatcher());
            item = cart.findCartItem(index);

            if (basePrice != null) {
                // item = cart.findCartItem(productId, null, null, null, BigDecimal.ZERO);
                if (item != null) {
                    item.setBasePrice(basePrice);
                }
            }

        } catch (ItemNotFoundException e) {
            Debug.logError(e, module);
            throw e;
        } catch (CartItemModifyException e) {
            Debug.logError(e, module);
            throw e;
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            Debug.logError(e, module);
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return item;
    }

}
