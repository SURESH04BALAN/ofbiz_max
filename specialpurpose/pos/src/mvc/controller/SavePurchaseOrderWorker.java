/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.CheckOutHelper;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemBillingsList;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupAssocsList;
import org.ofbiz.ordermax.composite.OrderItemShipGroupList;
import org.ofbiz.ordermax.composite.OrderItemsList;
import org.ofbiz.ordermax.composite.OrderStatusList;
import org.ofbiz.ordermax.composite.ProductItemPrice;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.OrderItem;
import org.ofbiz.ordermax.entity.OrderItemShipGroup;
import org.ofbiz.ordermax.entity.OrderItemShipGroupAssoc;
import org.ofbiz.ordermax.entity.OrderStatus;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class SavePurchaseOrderWorker extends SwingWorker<List<Order>, Order> {
    
    public static final String module = SavePurchaseOrderWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private Order order;
    private BoundedRangeModel saveOrderSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;
    
    public SavePurchaseOrderWorker(Order sOrder, XuiSession session) {
        this.order = sOrder;
        this.session = session;
    }
    
    @Override
    protected List<Order> doInBackground() throws Exception {
        
        List<Order> orders = new ArrayList<Order>();
        maxProgress = 3;
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        Delegator delegator = session.getDelegator();
        
        return orders;
    }
    
    public void createORSaveOrderItem(OrderItem orderItem, Delegator delegator) throws GenericEntityException, Exception {
        
        Debug.logInfo(" createOrderItem: ", module);
        
        String productId = orderItem.getproductId();
        String orderId = orderItem.getorderId();
        String orderItemSeqId = orderItem.getorderItemSeqId();
        String productName = orderItem.getitemDescription();
        String orderItemTypeId = orderItem.getorderItemTypeId();//"PRODUCT_ORDER_ITEM";;
        BigDecimal quantity = orderItem.getquantity();
        BigDecimal unitPrice = orderItem.getunitPrice();//.supplierLastPrice;// new BigDecimal("24.00");
        BigDecimal unitListPrice = orderItem.getunitListPrice();//new BigDecimal("0.00");

        Debug.logInfo(" productId: " + productId, module);
        
        String prodCatalogId = "DemoCatalog";
        String isPromo = "N";
        BigDecimal selectedAmount = BigDecimal.ZERO;
        String isModifiedPrice = "N";
        String statusId = "ITEM_CREATED";
        
        Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();
        Debug.logInfo(" quantity: " + quantity + " unitPrice: " + unitPrice + " orderItemSeqId: " + orderItemSeqId, "module");
        
        GenericValue orderItemGV = orderItem.getGenericValueObj();//PosProductHelper.getOrderItem(orderId, orderItemSeqId, delegator);

        if (orderItemGV == null) {
            orderItemGV = orderItem.createNewGenericValueObj(delegator);
            delegator.setNextSubSeqId(orderItemGV, "orderItemSeqId", 4, 1);
            orderItemGV.create();
            orderItem.setGenericValue();
            /*            
             val = PosProductHelper.createOrderItem(orderId, orderItemSeqId, orderItemTypeId,
             productId, prodCatalogId, isPromo, quantity,
             selectedAmount, unitPrice, unitListPrice,
             isModifiedPrice, productName, statusId,
             estimatedDeliveryDate, delegator);
        
             orderItemSeqId = val.getString("orderItemSeqId");
             */
        } else {
            orderItem.getGenericValue();
            orderItemGV = orderItem.getGenericValueObj();
            orderItemGV.store();

            /*            val = PosProductHelper.updateOrderItem(orderId, orderItemSeqId, orderItemTypeId,
             productId, prodCatalogId, isPromo, quantity,
             selectedAmount, unitPrice, unitListPrice,
             isModifiedPrice, productName, statusId,
             estimatedDeliveryDate, delegator);
            
             orderItemSeqId = val.getString("orderItemSeqId");
             */
        }

//        this.orderItem.setorderItemSeqId(val.getString("orderItemSeqId"));
//        copyShipmentValues();
//        createOrderShipmentItem(delegator);
//        Debug.logInfo("orderStatusId: " + orderStatusId, module);
        Timestamp statusDatetime = UtilDateTime.nowTimestamp();

//        GenericValue val2 = PosProductHelper.createOrderStatus(orderStatusId, statusId, orderId,
//                orderItemSeqId, statusDatetime, statusUserLogin, delegator);
    }
    
    @Override
    protected void process(List<Order> chunks) {
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }
    
    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }
    
    private void sleepAWhile() {
        try {
            Thread.yield();
            long timeToSleep = getTimeToSleep();
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
        }
    }
    
    private long getTimeToSleep() {
        return saveOrderSpeedModel.getValue();
    }
    
    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.saveOrderSpeedModel = loadPersonsSpeedModel;
        
    }
    
    public GenericValue createShipGroup(String orderId, String shipGroupSeqId,
            String contactMechId,
            String shipmentMethodTypeId,
            String carrierPartyId,
            String carrierRoleTypeId,
            Delegator delegator) throws GenericEntityException {
        // create ship group
        //String shipGroupSeqId = null;

//        String shipmentMethodTypeId = "NO_SHIPPING";
//        String carrierPartyId = "_NA_";
//        String carrierRoleTypeId = "CARRIER";
//        String contactMechId = getContactMechId(DELIVERY_LOCATION);
//        if (orderItemShipGroupList.size() > 0) {
//            shipGroupSeqId = orderItemShipGroupList.get(0).getshipGroupSeqId();
//        }
        GenericValue shipGroupGenericValue = null;
        String maySplit = "N";
        String isGift = "N";
        Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();
        
        shipGroupGenericValue = PosProductHelper.findOrderItemShipGroup(orderId,
                shipGroupSeqId, shipmentMethodTypeId, carrierPartyId,
                carrierRoleTypeId, contactMechId, maySplit, isGift,
                estimatedDeliveryDate, delegator);
        
        if (shipGroupGenericValue == null) {
            
            shipGroupGenericValue = PosProductHelper.createOrderItemShipGroup(orderId,
                    shipGroupSeqId, shipmentMethodTypeId, carrierPartyId,
                    carrierRoleTypeId, contactMechId, maySplit, isGift,
                    estimatedDeliveryDate, delegator);
            
        }
        
        shipGroupSeqId = shipGroupGenericValue.getString("shipGroupSeqId");
        return shipGroupGenericValue;
    }

    //create roles for this supplier
    public static void createSupplierPartyRoles(String partyId, String ownerPartyId, Delegator delegator)
            throws GenericEntityException {
        
        String roleTypeId = "SUPPLIER";
        GenericValue val = PosProductHelper.createPartyRole(partyId, roleTypeId, delegator);
        
        roleTypeId = "BILL_TO_CUSTOMER";
        val = PosProductHelper.createPartyRole(ownerPartyId, roleTypeId, delegator);
        
        roleTypeId = "BILL_FROM_VENDOR";
        val = PosProductHelper.createPartyRole(partyId, roleTypeId, delegator);
        
        roleTypeId = "SHIP_FROM_VENDOR";
        val = PosProductHelper.createPartyRole(partyId, roleTypeId, delegator);
        
        roleTypeId = "SUPPLIER";
        val = PosProductHelper.createPartyRole(partyId, roleTypeId, delegator);
    }
    
    static public void createSupplierOrderRoles(String orderId, String partyId, String ownerPartyId, Delegator delegator) throws GenericEntityException {
        
        String roleTypeId = "BILL_TO_CUSTOMER";
        
        GenericValue val = PosProductHelper.createOrderRole(orderId, ownerPartyId, roleTypeId,
                delegator);
        // orderId="Demo1001";

        roleTypeId = "BILL_FROM_VENDOR";
        val = PosProductHelper.createOrderRole(orderId, partyId, roleTypeId, delegator);
        // orderId="Demo1001";

        roleTypeId = "SHIP_FROM_VENDOR";
        val = PosProductHelper.createOrderRole(orderId, partyId, roleTypeId, delegator);
        // orderId="Demo1001";

        roleTypeId = "SUPPLIER";
        val = PosProductHelper.createOrderRole(orderId, partyId, roleTypeId, delegator);
    }
    
    public GenericValue createOrderStatus(String statusId, String orderId, String createdBy,
            Delegator delegator) throws GenericEntityException {
        
        String orderStatusId = delegator.getNextSeqId("OrderStatus");// "9009";
        Timestamp statusDatetime = UtilDateTime.nowTimestamp();
        
        GenericValue val = PosProductHelper.createOrderStatus(orderStatusId, statusId,
                orderId, null, statusDatetime, createdBy, delegator);
        return val;
    }
    
    public GenericValue createOrderContactMechs(String orderId, String contactMechPurposeTypeId, String contactMechId,
            Delegator delegator) throws GenericEntityException {
        
        GenericValue val = PosProductHelper.findOrderContactMech(orderId, contactMechPurposeTypeId, contactMechId, delegator);
        if (val == null) {
            val = PosProductHelper.createOrderContactMech(orderId, contactMechPurposeTypeId, contactMechId, delegator);
        }
        
        return val;
    }
    
    private ClassLoader getClassLoader() {
        
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            
            if (cl == null) {
                try {
                    
                    cl = Thread.currentThread().getContextClassLoader();
                    
                } catch (Throwable t) {
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
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
    
    static public void saveOrderCart(Order order, XuiSession session) {
        // store the "order"
        if (UtilValidate.isEmpty(order.getOrderId())) {  // if order does not exist

//            order.setupShoppingCartItems();
            CheckOutHelper ch = new CheckOutHelper(session.getDispatcher(), session.getDelegator(), order.getShopingCart());
            
            Map<String, Object> orderRes = ch.createOrder(session.getUserLogin());
            try {
                //Debug.logInfo("Create Order Resp : " + orderRes, module);
                if (OrderMaxUtility.handleServiceReturn("Save Order", orderRes) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    order.setOrderId((String) orderRes.get("orderId"));
                    GenericValue orderGenVal = OrderReadHelper.getOrderHeader(XuiContainer.getSession().getDelegator(), (String) orderRes.get("orderId"));
                    //create order header
                    OrderHeader orderHeader = new OrderHeader(orderGenVal);
                    order.setOrderHeader(orderHeader);
                }
            } catch (Exception ex) {
                Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else { // if the order has already been created
            updateOrderContacts(order, session);
            Map<?, ?> changeMap = UtilMisc.toMap("itemReasonMap",
                    UtilMisc.toMap("reasonEnumId", "OICR_MISORDER_ITEM"), // TODO: where does this come from?
                    "itemCommentMap", UtilMisc.toMap("changeComments", "change Comments here")); //TODO

            Locale locale = Locale.getDefault();
            Map<String, Object> svcCtx = FastMap.newInstance();
            svcCtx.put("userLogin", session.getUserLogin());
            svcCtx.put("orderId", order.getOrderId());
            svcCtx.put("shoppingCart", order.getShopingCart());
            svcCtx.put("locale", locale);
            svcCtx.put("changeMap", changeMap);
            Debug.logInfo("shoppingCart getFacilityId: " + order.getShopingCart().getFacilityId(), module);
            for (Map.Entry<String, String> val : order.getOrderContactMechIds().entrySet()) {
                Debug.logInfo("val key: " + val.getKey() + " value: " + val.getValue(), module);
            }
            
            Map<String, Object> svcRes = null;
            try {
                LocalDispatcher dispatcher = session.getDispatcher();
                svcRes = dispatcher.runSync("saveUpdatedCartToOrder", svcCtx);
                try {
                    //Debug.logInfo("Create Order Resp : " + orderRes, module);
                    if (OrderMaxUtility.handleServiceReturn("Save Order", svcRes) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                        //order.setOrderId((String) svcRes.get("orderId"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (GenericServiceException e) {
                Debug.logError(e, module);
                try {
                    //OrderMaxOptionPane.showMessageDialog(null,"dialog/error/exception", e.getMessage());
                    throw new GeneralException(ServiceUtil.getErrorMessage(svcRes));
                } catch (GeneralException ex) {
                    Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //reloadOrderItemsFromCart(order);
        LoadSalesOrderWorker.updateOrederItemUnitAverageCost(order);
        /*for (OrderItemComposite entry : order.getOrderItemsList().getList()) {
         if (entry.getShoppingCartItem() != null) {
         Debug.logInfo("entry.getOrderItem().getorderId() : " + order.getOrderId()
         + " entry.getShoppingCartItem().getOrderItemSeqId(): " + entry.getShoppingCartItem().getOrderItemSeqId()
         + " entry.getOrderItem().isGenericValueSet(): " + entry.getOrderItem().isGenericValueSet(), module);

         GenericValue orderItem;
         try {
         orderItem = session.getDelegator().findByPrimaryKey("OrderItem", UtilMisc.toMap("orderId", order.getOrderId(),
         "orderItemSeqId", entry.getShoppingCartItem().getOrderItemSeqId()));
         orderItem.set("unitAverageCost", entry.averageCost);
         session.getDelegator().store(orderItem);
         entry.setOrderItem(new OrderItem(orderItem));
         } catch (GenericEntityException e) {
         Debug.logError(e, module);
         }
         }
         }*/
        
    }
    
    static public void reloadOrderItemsFromCart(Order order) {
        XuiSession session = XuiContainer.getSession();
        //create order item array
        ArrayList<OrderItemComposite> arrayItems = new ArrayList<OrderItemComposite>();
        String orderId = order.getOrderId();
        //get the order items gen values
        GenericValue orderGenVal = OrderReadHelper.getOrderHeader(XuiContainer.getSession().getDelegator(), orderId);
        //create order header
        OrderHeader orderHeader = new OrderHeader(orderGenVal);
        //create our composite object
//        ShoppingCart shopingCart = loadShopingCart(orderId, session);
//        order = new Order(shopingCart);
        order.setOrderHeader(orderHeader);
        order.setEntryDate(orderHeader.getEntryDate());
        Debug.logError("shopingCart facility Id: " + order.getFacilityId(), module);
        
        OrderReadHelper orderReadHelper = new OrderReadHelper(orderGenVal);
        //get the order items gen values
        List<GenericValue> orderItems = orderReadHelper.getOrderItems();
        //        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
        for (GenericValue orderItem : orderItems) {
            String orderStatusId = orderItem.getString("statusId");
            if (orderStatusId.equals("ITEM_CANCELLED") == false) {
                OrderItem orderIt = new OrderItem(orderItem);
                OrderItemComposite productItem = new OrderItemComposite();
                productItem.setOrderItem(orderIt);
                arrayItems.add(productItem);

                //productItem.getOrderItem().setquantity(orderItem.getBigDecimal("quantity"));
                //productItem.getOrderItem().setunitPrice(orderItem.getBigDecimal("unitPrice"));
                //Debug.logInfo("productItem.getOrderItem().setquantity: " + orderItem.getBigDecimal("quantity"), "module");
                productItem.setTotalAmount(orderReadHelper.getOrderItemTotal(orderItem));
                productItem.setGstAmount(orderReadHelper.getOrderItemTax(orderItem));
                LoadOrderWorker.calculateProductItemValues(productItem);
                productItem.setOrderItemBillingsList(new OrderItemBillingsList());
                //getitem status list
                List<GenericValue> statusItemList = orderReadHelper.getOrderItemStatuses(orderItem);
                productItem.setOrderStatusList(new OrderStatusList());
                for (GenericValue status : statusItemList) {
                    productItem.getOrderStatusList().add(new OrderStatus(status));
                }
                
                List<GenericValue> orderItemShipGroupAssocslist = orderReadHelper.getOrderItemShipGroupAssocs(orderItem);
                productItem.setOrderItemShipGroupAssocsList(new OrderItemShipGroupAssocsList());
                for (GenericValue oisga : orderItemShipGroupAssocslist) {
                    productItem.getOrderItemShipGroupAssocsList().add(new OrderItemShipGroupAssoc(oisga));
                }
                
                try {
                    ProductItemPrice pip = LoadProductPriceWorker.getProductItemPrice(productItem.getOrderItem().getproductId(), session);
                    productItem.setProductItemPrice(pip);
                } catch (GenericEntityException ex) {
                    Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                SupplierProductList spl = LoadSupplierProductWorker.getSupplierProductList(productItem.getOrderItem().getproductId(),
                        order.getBillFromAccount().getParty().getParty().getpartyId(), order.getCurrency(), session);
                productItem.setSupplierProductList(spl);
                
                productItem.averageCost = LoadProductPriceWorker.getProductAveragePrice(productItem.getOrderItem().getproductId(), order.getOwnerPartyId(), order.getFacilityId(), session);
                Map<String, Object> qopMap = LoadInventoryWorker.getInventoryAvailableByFacility(productItem.getOrderItem().getproductId(), order.getFacilityId(), session);
                BigDecimal availableToPromiseTotal = (BigDecimal) qopMap.get("availableToPromiseTotal");
                BigDecimal quantityOnHandTotal = (BigDecimal) qopMap.get("quantityOnHandTotal");
                productItem.setQuantyOnHand(quantityOnHandTotal);
                productItem.setAtp(availableToPromiseTotal);
            }
        }

        //Debug.logInfo("Load ship group Start", module);
        List<GenericValue> orderItemShipGrouplist = orderReadHelper.getOrderItemShipGroups();
        OrderItemShipGroupList orderItemShipGroupList = new OrderItemShipGroupList();
        for (GenericValue oisgGV : orderItemShipGrouplist) {
            OrderItemShipGroup oisg = new OrderItemShipGroup(oisgGV);
            try {
                oisg.setGenericValue();
            } catch (Exception ex) {
                Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
            orderItemShipGroupList.add(oisg);
            //Debug.logInfo("shipGroupSeqId for " + oisg.getshipGroupSeqId(), module);
        }
        order.setOrderItemShipGroupList(orderItemShipGroupList);
        //Debug.logInfo("Load ship group end", module);
        //create order item list
        OrderItemsList orderItmList = new OrderItemsList();
        orderItmList.addAll(arrayItems);
        order.setOrderItemsList(orderItmList);
        
    }
    
    static public void updateOrderContacts(Order order, XuiSession session) {
        // get the payments of the desired type for these invoices TODO: in models where invoices can have many orders, this needs to be refined
        List<EntityExpr> conditions = UtilMisc.toList(
                EntityCondition.makeCondition("orderId", EntityOperator.EQUALS, order.orderHeader.getOrderId()));
        
        EntityConditionList<EntityExpr> ecl = EntityCondition.makeCondition(conditions, EntityOperator.AND);
        List<GenericValue> orderContactMechs;
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        try {
            orderContactMechs = session.getDelegator().findList("OrderContactMech", ecl, null, null, null, true);
            for (GenericValue orderContactMech : orderContactMechs) {
                try {
                    resultMap = session.getDispatcher().runSync("removeOrderContactMech", UtilMisc.toMap(
                            "orderId", orderContactMech.getString("orderId"),
                            "contactMechId", orderContactMech.getString("contactMechId"),
                            "contactMechPurposeTypeId", orderContactMech.getString("contactMechPurposeTypeId"),
                            "userLogin", session.getUserLogin()));
                    
                } catch (GenericServiceException ex) {
                    Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for (Map.Entry<String, String> val : order.getOrderContactMechIds().entrySet()) {
                try {
                    resultMap = session.getDispatcher().runSync("createOrderContactMech", UtilMisc.toMap(
                            "orderId", order.orderHeader.getOrderId(),
                            "contactMechId", val.getValue(),
                            "contactMechPurposeTypeId", val.getKey(),
                            "userLogin", session.getUserLogin()));
                } catch (GenericServiceException ex) {
                    Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(SavePurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    static public GenericValue createShipment(Order order, XuiSession session) throws GeneralException {
        Delegator delegator = session.getDelegator();
        String orderId = order.getOrderId();
        String shipGroupId = order.getOrderItemShipGroupList().getElementAt(0).getshipGroupSeqId();
        Map<String, Object> newShipment = FastMap.newInstance();
        newShipment.put("originFacilityId", order.getFacilityId());
        newShipment.put("primaryShipGroupSeqId", orderId);
        newShipment.put("primaryOrderId", orderId);
        newShipment.put("shipmentTypeId", "PURCHASE_SHIPMENT");
//        newShipment.put("statusId", "SHIPMENT_DELIVERED");
        newShipment.put("userLogin", session.getUserLogin());
        GenericValue orderRoleShipTo = EntityUtil.getFirst(delegator.findByAnd("OrderRole", UtilMisc.toMap("orderId", orderId, "roleTypeId", "SHIP_TO_CUSTOMER")));
        if (UtilValidate.isNotEmpty(orderRoleShipTo)) {
            newShipment.put("partyIdTo", orderRoleShipTo.getString("partyId"));
        }
        String partyIdFrom = null;
        GenericValue orderItemShipGroup = EntityUtil.getFirst(delegator.findByAnd("OrderItemShipGroup", UtilMisc.toMap("orderId", orderId, "shipGroupSeqId", shipGroupId)));
        if (UtilValidate.isNotEmpty(orderItemShipGroup.getString("vendorPartyId"))) {
            partyIdFrom = orderItemShipGroup.getString("vendorPartyId");
        } else if (UtilValidate.isNotEmpty(orderItemShipGroup.getString("facilityId"))) {
            GenericValue facility = delegator.findOne("Facility", UtilMisc.toMap("facilityId", orderItemShipGroup.getString("facilityId")), false);
            if (UtilValidate.isNotEmpty(facility.getString("ownerPartyId"))) {
                partyIdFrom = facility.getString("ownerPartyId");
            }
        }
        
        if (UtilValidate.isEmpty(partyIdFrom)) {
            GenericValue orderRoleShipFrom = EntityUtil.getFirst(delegator.findByAnd("OrderRole", UtilMisc.toMap("orderId", orderId, "roleTypeId", "SHIP_FROM_VENDOR")));
            if (UtilValidate.isNotEmpty(orderRoleShipFrom)) {
                partyIdFrom = orderRoleShipFrom.getString("partyId");
            } else {
                orderRoleShipFrom = EntityUtil.getFirst(delegator.findByAnd("OrderRole", UtilMisc.toMap("orderId", orderId, "roleTypeId", "BILL_FROM_VENDOR")));
                partyIdFrom = orderRoleShipFrom.getString("partyId");
            }
        }
        newShipment.put("partyIdFrom", partyIdFrom);
        Map<String, Object> newShipResp = session.getDispatcher().runSync("createShipment", newShipment);
        if (ServiceUtil.isError(newShipResp)) {
            Debug.logError(new GeneralException(ServiceUtil.getErrorMessage(newShipResp)), module);
        }
        
        String shipmentId = (String) newShipResp.get("shipmentId");
        GenericValue shipmentGv = delegator.findByPrimaryKey("Shipment", UtilMisc.toMap("shipmentId", shipmentId));
        return shipmentGv;
    }
    
    static public Map<String, Object> quickShipPurchaseOrder(Order order, XuiSession session) {
        
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        String orderId = order.getOrderId();
        GenericValue shipmentGV = null;
        
        Debug.logInfo("$$$$$$$$$$$$$$$$$$$$$$$$ start btnGenerateInventoryActionPerformed orderId: " + orderId + "order.getDestinationFacilityId(): " + order.getFacilityId(), module);
        
        try {
            resultMap = dispatcher.runSync("quickShipPurchaseOrder", UtilMisc.toMap(
                    //                    "order", order.getGenericValueObj(),
                    //                    "facility", facility,
                    "orderId", orderId,
                    "facilityId", order.getFacilityId(),
                    "userLogin", userLogin));
            
            Debug.logInfo("Test sales order with id [" + orderId + "] has been shipped", module);
            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }
            
            List<GenericValue> shipmentGVList = session.getDelegator().findByAnd("Shipment", UtilMisc.toMap("primaryOrderId", orderId, "destinationFacilityId", order.getFacilityId()));
            Debug.logInfo("find shipment start", module);
            if (shipmentGVList.size() > 0) {
                shipmentGV = shipmentGVList.get(0);
                order.shipment = new Shipment(shipmentGV);
                Debug.logInfo("find shipment id: " + order.shipment.getshipmentId(), module);
            }

            //get ship group list
            OrderItemShipGroupList list = order.getOrderItemShipGroupList();
            Debug.logInfo("ship group list size: " + list.getSize(), module);
            if (list.getSize() == 0 && order.shipment != null) {
                List<GenericValue> shipGroupGVList = session.getDelegator().findByAnd("OrderItemShipGroup", UtilMisc.toMap("orderId", orderId, "shipGroupSeqId", order.shipment.getprimaryShipGroupSeqId()));
                Debug.logInfo("find shipment start", module);
                if (shipGroupGVList.size() > 0) {
                    shipmentGV = shipGroupGVList.get(0);
                    OrderItemShipGroup orderItemShipGroup = new OrderItemShipGroup(shipmentGV);
                    list.add(orderItemShipGroup);
                    Debug.logInfo("find ship group Id: " + orderItemShipGroup.getshipGroupSeqId(), module);
                }
            }
            
            Debug.logInfo("find shipment end ", module);
        } catch (Exception exc) {
            Debug.logWarning("Unable to quick ship test sales order with id [" + orderId + "] with error: " + exc.getMessage(), module);
        }
        
        return resultMap;
    }
    
}
