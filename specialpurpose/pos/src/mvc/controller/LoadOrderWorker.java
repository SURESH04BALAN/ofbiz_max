/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.io.IOException;
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
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javolution.util.FastList;
import javolution.util.FastMap;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.accounting.payment.BillingAccountWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.model.DynamicViewEntity;
import org.ofbiz.entity.model.ModelKeyMap;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ShoppingCart;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.Contact;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderContactMechComposite;
import org.ofbiz.ordermax.composite.OrderContactMechCompositeList;
import org.ofbiz.ordermax.composite.OrderItemBillingsList;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.OrderItemShipGroupAssocsList;
import org.ofbiz.ordermax.composite.OrderItemShipGroupList;
import org.ofbiz.ordermax.composite.OrderItemsList;
import org.ofbiz.ordermax.composite.OrderRoleComposite;
import org.ofbiz.ordermax.composite.OrderRolesList;
import org.ofbiz.ordermax.composite.OrderStatusList;
import org.ofbiz.ordermax.composite.OrderSummaryView;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PaymentApplicationCompositeList;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.ProductItemPrice;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.ContactMech;
import org.ofbiz.ordermax.entity.OrderContactMech;
import org.ofbiz.ordermax.entity.OrderDeliverySchedule;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.OrderItem;
import org.ofbiz.ordermax.entity.OrderItemBilling;
import org.ofbiz.ordermax.entity.OrderItemShipGroup;
import org.ofbiz.ordermax.entity.OrderItemShipGroupAssoc;
import org.ofbiz.ordermax.entity.OrderPaymentPreference;
import org.ofbiz.ordermax.entity.OrderRole;
import org.ofbiz.ordermax.entity.OrderStatus;
import org.ofbiz.ordermax.entity.OrderTerm;
import org.ofbiz.ordermax.entity.PaymentApplication;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.orderviews.OrderViewDetailAction;
import org.ofbiz.ordermax.orderbase.returns.ReturnHeaderTypeSingleton;
import org.ofbiz.ordermax.orderbase.returns.ReturnItemComposite;
import org.ofbiz.ordermax.party.ContactMechPurposeTypeSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.party.contact.ContactMechWorker;
import org.ofbiz.product.store.ProductStoreWorker;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author BBS Auctions
 */
public abstract class LoadOrderWorker extends LoadBaseSwingWorker<Order> {

    public static final String module = LoadPurchaseOrderWorker.class.getName();

    public static List<GenericValue> getOrderDetails(String partyId, String roleTypeId, String productId, String orderTypeId, String statusId, Delegator delegator) {
        List<GenericValue> partyList = null;
        List<GenericValue> resultList = null;
        // create the dynamic view entity
        DynamicViewEntity dynamicView = new DynamicViewEntity();

        // Person (name + card)
        dynamicView.addMemberEntity("O_R", "OrderRole");
        dynamicView.addAlias("O_R", "orderId");
        dynamicView.addAlias("O_R", "partyId");
        dynamicView.addAlias("O_R", "roleTypeId");
        dynamicView.addMemberEntity("O_H", "OrderHeader");
        dynamicView.addAlias("O_H", "orderName");
        dynamicView.addAlias("O_H", "statusId");
        dynamicView.addAlias("O_H", "orderTypeId");
        dynamicView.addAlias("O_H", "grandTotal");
        dynamicView.addAlias("O_H", "remainingSubTotal");
        dynamicView.addViewLink("O_R", "O_H", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));
        // Create the order Item
        /*        dynamicView.addMemberEntity("O_I", "OrderItem");
         dynamicView.addAlias("O_I", "productId");
         dynamicView.addAlias("O_I", "quantity");
         dynamicView.addAlias("O_I", "unitPrice");
         dynamicView.addAlias("O_I", "itemDescription");
         dynamicView.addAlias("O_I", "statusId");
         dynamicView.addAlias("O_I", "createdStamp");
         dynamicView.addViewLink("O_H", "O_I", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));
         */
        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;
        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();
        // fields we need to select; will be used to set distinct
        fieldsToSelect.add("orderId");
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("roleTypeId");
        fieldsToSelect.add("orderName");
        fieldsToSelect.add("statusId");
        fieldsToSelect.add("orderTypeId");
        fieldsToSelect.add("grandTotal");
        fieldsToSelect.add("remainingSubTotal");
        fieldsToSelect.add("orderDate");
        fieldsToSelect.add("totalOrders");
        fieldsToSelect.add("totalGrandAmount");
        fieldsToSelect.add("totalSubRemainingAmount");
        //      fieldsToSelect.add("productId");
        //      fieldsToSelect.add("quantity");
        //      fieldsToSelect.add("unitPrice");
        //      fieldsToSelect.add("itemDescription");
        //      fieldsToSelect.add("statusId");
        //      fieldsToSelect.add("createdStamp");
        if (partyId != null) {
            andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId)));
        }
        if (roleTypeId != null) {
            andExprs.add(EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, roleTypeId)); // Only persons for now...
        }
        if (productId != null) {
            andExprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId)); // Only persons for now...
        }
        if (orderTypeId != null) {
            andExprs.add(EntityCondition.makeCondition("orderTypeId", EntityOperator.EQUALS, orderTypeId)); // Only persons for now...
        }
        if (orderTypeId != null) {
            andExprs.add(EntityCondition.makeCondition("statusId", EntityOperator.EQUALS, statusId)); // Only persons for now...
        }
        // NOTE: _must_ explicitly allow null as it is not included in a not equal in many databases... odd but true
        // This allows to get all clients when any informations has been entered
        //        andExprs.add(EntityCondition.makeCondition(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, null), EntityOperator.OR, EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId)));
        //       andExprs.add(EntityCondition.makeCondition("goodIdentificationTypeId", EntityOperator.EQUALS, "EAN")); // Only persons for now...
        //       andExprs.add(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, "DEFAULT_PRICE")); // Only persons for now...
        //        andExprs.add(EntityCondition.makeCondition("productPricePurposeId", EntityOperator.EQUALS, "PURCHASE")); // Only persons for now...
        //        andExprs.add(EntityCondition.makeCondition("goodIdentificationTypeId", EntityOperator.EQUALS, "EAN")); // Only persons for now...
        mainCond = EntityCondition.makeCondition(andExprs, EntityOperator.AND);
        //        orderBy.add("createdStamp");
        Debug.logInfo("dynamicView=" + dynamicView.toString(), module);
        Debug.logInfo("dynamicView1=" + dynamicView.getEntityName(), module);
        Debug.logInfo("In searchClientProfile mainCond=" + mainCond, module);
        Integer maxRows = 100000;
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();
            try {
                try {
                    String str = dynamicView.getViewXml("PartyRole");
                    Debug.logInfo("getViewXml=" + str, module);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // set distinct on so we only get one row per person
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
                // using list iterator
                EntityListIterator pli = delegator.findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);
                // get the partial list for this page
                partyList = pli.getPartialList(1, maxRows);
                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        if (partyList != null) {
            resultList = FastList.newInstance();
            for (GenericValue party : partyList) {
                resultList.add(party);
            }
            Debug.logInfo("supplierProductId list is empty", module);
        } else {
            resultList = FastList.newInstance();
            Debug.logInfo("supplierProductId list is null", module);
        }
        return resultList;
    }

    public static List<GenericValue> getOrderList(XuiSession session, List<EntityCondition> entityConditionList) {
        List<GenericValue> partyList = null;
        List<GenericValue> resultList = null;
        // create the dynamic view entity
        DynamicViewEntity dynamicView = new DynamicViewEntity();

        // Person (name + card)
        dynamicView.addMemberEntity("O_R", "OrderRole");
        dynamicView.addAlias("O_R", "orderId");
        dynamicView.addAlias("O_R", "partyId");
        dynamicView.addAlias("O_R", "roleTypeId");
        dynamicView.addMemberEntity("O_H", "OrderHeader");
        dynamicView.addAlias("O_H", "orderName");
        dynamicView.addAlias("O_H", "statusId");
        dynamicView.addAlias("O_H", "orderTypeId");
        dynamicView.addAlias("O_H", "grandTotal");
        dynamicView.addAlias("O_H", "remainingSubTotal");
        dynamicView.addViewLink("O_R", "O_H", Boolean.FALSE, ModelKeyMap.makeKeyMapList("orderId"));

        // define the main condition & expression list
        List<EntityCondition> andExprs = FastList.newInstance();
        EntityCondition mainCond = null;
        List<String> orderBy = FastList.newInstance();
        List<String> fieldsToSelect = FastList.newInstance();

        // fields we need to select; will be used to set distinct
        fieldsToSelect.add("orderId");
        fieldsToSelect.add("partyId");
        fieldsToSelect.add("roleTypeId");
        fieldsToSelect.add("orderName");
        fieldsToSelect.add("statusId");
        fieldsToSelect.add("orderTypeId");
        fieldsToSelect.add("grandTotal");
        fieldsToSelect.add("remainingSubTotal");
//        fieldsToSelect.add("orderDate");
//        fieldsToSelect.add("totalOrders");
//        fieldsToSelect.add("totalGrandAmount");
//        fieldsToSelect.add("totalSubRemainingAmount");

        mainCond = EntityCondition.makeCondition(entityConditionList, EntityOperator.AND);

        Integer maxRows = 100000;
        // attempt to start a transaction
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();
            try {
                try {
                    String str = dynamicView.getViewXml("PartyRole");
                    Debug.logInfo("getViewXml=" + str, module);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // set distinct on so we only get one row per person
                EntityFindOptions findOpts = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, -1, maxRows, true);
                // using list iterator
                EntityListIterator pli = session.getDelegator().findListIteratorByCondition(dynamicView, mainCond, null, fieldsToSelect, orderBy, findOpts);
                // get the partial list for this page
                partyList = pli.getPartialList(1, maxRows);
                // close the list iterator
                pli.close();
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        if (partyList != null) {
            resultList = FastList.newInstance();
            for (GenericValue party : partyList) {
                resultList.add(party);
            }
            Debug.logInfo("summaryList size: " + partyList.size(), module);
        } else {
            resultList = FastList.newInstance();
            Debug.logInfo("supplierProductId list is null", module);
        }
        return resultList;
    }

    public static Order loadShoppingCart(final String orderId, XuiSession session) {
        Order order = null;
        //create our composite object

        ShoppingCart shopingCart = loadShoppingCartFromService(orderId, session);
        Debug.logInfo("loadShoppingCart size: " + shopingCart.getOrderContactMechIds().size(), module);
        for (Map.Entry<String, String> val : shopingCart.getOrderContactMechIds().entrySet()) {
            Debug.logInfo("val key: " + val.getKey() + " value: " + val.getValue(), module);
        }
        order = new Order(shopingCart);
        //            order.setOrderHeader(orderHeader);
        return order;
    }

    public static ShoppingCart loadShoppingCartFromService(String orderId, XuiSession session) {
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> svcCtx = FastMap.newInstance();
        svcCtx.put("userLogin", session.getUserLogin());
        svcCtx.put("orderId", orderId);
        svcCtx.put("skipInventoryChecks", Boolean.TRUE);
        svcCtx.put("skipProductChecks", Boolean.TRUE);
        Map<String, Object> svcRes = null;
        try {
            svcRes = dispatcher.runSync("loadCartFromOrder", svcCtx);
            try {
                if (OrderMaxUtility.handleServiceReturn("Load Order", svcRes) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    ShoppingCart shoppingCart = (ShoppingCart) svcRes.get("shoppingCart");
                    shoppingCart.setOrderId(orderId);
                    List<ShoppingCartItem> cartItemList = shoppingCart.items(); //oicIter.getOrderItem().getproductId());
                    for (ShoppingCartItem sciIter : cartItemList) {
                        Debug.logInfo("shoppingCart details item prodId: " + sciIter.getProductId() + "order Id: " + orderId + "order Seq Id: " + sciIter.getOrderItemSeqId() + "sciIter.getQuantity(): " + sciIter.getQuantity() + "sciIter.getBasePrice(): " + sciIter.getBasePrice(), module);
                    }
                    Debug.logInfo("order facility Id: " + shoppingCart.getFacilityId(), module);
                    Debug.logInfo("order getProductStoreId: " + shoppingCart.getProductStoreId(), module);
                    Debug.logInfo("order shoppingCart.getOrderStatusId(): " + shoppingCart.getOrderStatusId(), module);

                    if (shoppingCart.getProductStoreId() != null) {

                        // set the default view cart on add for this store
                        GenericValue productStore = ProductStoreWorker.getProductStore(shoppingCart.getProductStoreId(), session.getDelegator());
                        if (productStore == null) {
                            throw new IllegalArgumentException("Unable to locate ProductStore by ID [" + shoppingCart.getProductStoreId() + "]");
                        }

                        String storeViewCartOnAdd = productStore.getString("viewCartOnAdd");
                        if (storeViewCartOnAdd != null && "Y".equalsIgnoreCase(storeViewCartOnAdd)) {
                            shoppingCart.setViewCartOnAdd(true);
                        }

                        shoppingCart.setFacilityId(productStore.getString("inventoryFacilityId"));

                        return shoppingCart;
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (GenericServiceException e) {
            try {
                Debug.logError(e, module);
                OrderMaxUtility.handleServiceReturn("Load Order", svcRes);
            } catch (Exception ex) {
                Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public static void setShoppingCartItemToItemComposite(Order order1, ShoppingCart shopingCart, XuiSession session) {
        for (OrderItemComposite oicIter : order1.getOrderItemsList().getList()) {
            if (oicIter.getShoppingCartItem() == null && oicIter.getOrderItem() != null) {
                List<ShoppingCartItem> cartItemList = shopingCart.findAllCartItems(oicIter.getOrderItem().getproductId());
                for (ShoppingCartItem sciIter : cartItemList) {
                    if (UtilValidate.isNotEmpty(sciIter.getOrderItemSeqId())
                            && UtilValidate.isNotEmpty(oicIter.getOrderItem().getorderItemSeqId())
                            && sciIter.getOrderItemSeqId().equals(oicIter.getOrderItem().getorderItemSeqId())) {
                        oicIter.setShoppingCartItem(sciIter);
                        //                        oicIter.getOrderItem().setunitPrice(sciIter.get());
                        oicIter.getOrderItem().setquantity(sciIter.getQuantity());
                        Map<String, Object> qopMap = LoadInventoryWorker.getInventoryAvailableByFacility(oicIter.getOrderItem().getproductId(), order1.getFacilityId(), session);
                        BigDecimal availableToPromiseTotal = (BigDecimal) qopMap.get("availableToPromiseTotal");
                        BigDecimal quantityOnHandTotal = (BigDecimal) qopMap.get("quantityOnHandTotal");
                        oicIter.setQuantyOnHand(quantityOnHandTotal);
                        oicIter.setAtp(availableToPromiseTotal);
                        Debug.logInfo("found shoppingCart item prodId: " + oicIter.getOrderItem().getproductId() + "order Id: " + oicIter.getOrderItem().getorderId() + "order Seq Id: " + oicIter.getOrderItem().getorderItemSeqId() + "sciIter.getQuantity(): " + sciIter.getQuantity() + "sciIter.getBasePrice(): " + sciIter.getBasePrice(), module);
                        break;
                    }
                }
            }
        }
        Debug.logInfo("shopingCart.getOrderContactMechIds() - start: ", module);
        Map<String, String> orderMechIds = shopingCart.getOrderContactMechIds();
        for (Map.Entry<String, String> entryDept : orderMechIds.entrySet()) {
            Debug.logInfo("entryDept purpose: " + entryDept.getKey() + "mech Id: " + entryDept.getValue(), module);
        }
    }

    public static Order loadOrder(final String orderId, XuiSession session) {
        Order order = null;
        try {
            order = loadShoppingCart(orderId, session);

            if (order != null) {
                //get order genericvalue
                GenericValue orderGenVal = OrderReadHelper.getOrderHeader(session.getDelegator(), orderId);
                //create order header
                OrderHeader orderHeader = new OrderHeader(orderGenVal);
                OrderReadHelper orderReadHelper = new OrderReadHelper(orderGenVal);

                //order = new Order(shopingCart);
                order.setOrderHeader(orderHeader);
                order.setOrderReadHelper(orderReadHelper);
                order.setEntryDate(orderHeader.getEntryDate());
                Debug.logError("shopingCart facility Id: " + order.getFacilityId(), module);
                try {
                    GenericValue billingAccountGen = orderReadHelper.getBillingAccount();
                    if (billingAccountGen != null) {
                        BillingAccount billingAccount = new BillingAccount(billingAccountGen);
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

                ArrayList<OrderRoleComposite> array = new ArrayList<OrderRoleComposite>();
                List<GenericValue> orderRoleList = orderGenVal.getRelated("OrderRole");
                for (GenericValue orderRoleGen : orderRoleList) {
                    //create order role
                    OrderRole orderRole = new OrderRole(orderRoleGen);
                    //crreate the order composite
                    OrderRoleComposite orderRoleComp = new OrderRoleComposite();
                    //get party details
                    Account acct = LoadAccountWorker.getAccount(orderRole.getpartyId(), session);
                    orderRoleComp.setOrderRole(orderRole);
                    orderRoleComp.setParty(acct);
                    //add to list
                    array.add(orderRoleComp);
                }
                //let set order composite to order
                OrderRolesList rolesList = new OrderRolesList();
                rolesList.addAll(array);
                //set it to order
                //            order.setOrderRolesList(rolesList);
                order.setBillFromAccount(rolesList.getBillFromParty());
                order.setBillToAccount(rolesList.getBillToParty());
                //order term
                //get all roles so we can get parties related to this order
                ArrayList<OrderTerm> orderTermList = new ArrayList<OrderTerm>();
                List<GenericValue> otList = orderGenVal.getRelated("OrderTerm");
                for (GenericValue ot : otList) {
                    //create order role
                    OrderTerm orderTerm = new OrderTerm(ot);
                    //crreate the order composite
                    //add to list
                    orderTermList.add(orderTerm);
                }

                //let set order composite to order
                ListAdapterListModel<OrderTerm> otAdapterList = new ListAdapterListModel<OrderTerm>();
                otAdapterList.addAll(orderTermList);

                //set it to order
                order.setOrderTermList(otAdapterList);

                //order status
                List<GenericValue> stausList = orderReadHelper.getOrderHeaderStatuses();
                order.setOrderStatusList(new OrderStatusList());
                for (GenericValue status : stausList) {
                    order.getOrderStatusList().add(new OrderStatus(status));
                }

                //create order item array
                ArrayList<OrderItemComposite> arrayItems = new ArrayList<OrderItemComposite>();
                //get the order items gen values
                List<GenericValue> orderItems = orderReadHelper.getOrderItems();
                //        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
                for (GenericValue orderItem : orderItems) {
                    String orderStatusId = orderItem.getString("statusId");
                    if (orderStatusId.equals("ITEM_CANCELLED") == false) {
                        OrderItem orderIt = new OrderItem(orderItem);
                        ShoppingCartItem cartItem = order.findCartItem(orderIt.getorderItemSeqId());
//                    if(cartItem==null){
//                        List<ShoppingCartItem> itemList = order.findAllCartItems(orderIt.getproductId());
//        return this.findAllCartItems(productId, null);
//    }

                        OrderItemComposite productItem = new OrderItemComposite();
                        productItem.setOrderItem(orderIt);
                        productItem.setShoppingCartItem(cartItem);
                        Debug.logInfo("orderIt seq: " + orderIt.getorderItemSeqId(), module);
//                    Debug.logInfo("cartItem seq: " + cartItem.getOrderItemSeqId(), module);
                        arrayItems.add(productItem);

                        //productItem.getOrderItem().setquantity(orderItem.getBigDecimal("quantity"));
                        //productItem.getOrderItem().setunitPrice(orderItem.getBigDecimal("unitPrice"));
                        //Debug.logInfo("productItem.getOrderItem().setquantity: " + orderItem.getBigDecimal("quantity"), "module");
                        productItem.setTotalAmount(orderReadHelper.getOrderItemTotal(orderItem));
                        productItem.setGstAmount(orderReadHelper.getOrderItemTax(orderItem));
                        calculateProductItemValues(productItem);
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

                        ProductItemPrice pip = LoadProductPriceWorker.getProductItemPrice(productItem.getOrderItem().getproductId(), session);
                        productItem.setProductItemPrice(pip);

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

                Debug.logInfo("Load ship group Start", module);
                List<GenericValue> orderItemShipGrouplist = orderReadHelper.getOrderItemShipGroups();
                OrderItemShipGroupList orderItemShipGroupList = new OrderItemShipGroupList();
                for (GenericValue oisgGV : orderItemShipGrouplist) {
                    OrderItemShipGroup oisg = new OrderItemShipGroup(oisgGV);
                    oisg.setGenericValue();
                    orderItemShipGroupList.add(oisg);
                    createShippingGroup(order, "NO_SHIPPING", null);
                    //Debug.logInfo("shipGroupSeqId for " + oisg.getshipGroupSeqId(), module);
                }
                order.setOrderItemShipGroupList(orderItemShipGroupList);

                //Debug.logInfo("Load ship group end", module);
                //create order item list
                OrderItemsList orderItmList = new OrderItemsList();
                orderItmList.addAll(arrayItems);
                order.setOrderItemsList(orderItmList);
                //load billings
                List<GenericValue> orderItemBillings = orderGenVal.getRelated("OrderItemBilling");
                Set<String> invoiceIds = new HashSet<String>();
                for (Iterator<GenericValue> iter = orderItemBillings.iterator(); iter.hasNext();) {
                    GenericValue orderItemBilling = iter.next();
                    invoiceIds.add(orderItemBilling.getString("invoiceId"));
                    for (int index = 0; index < orderItmList.getSize(); index++) {
                        OrderItemComposite productItem = orderItmList.getElementAt(index);
                        if (productItem.getOrderItem().getorderItemSeqId().equals(orderItemBilling.getString("orderItemSeqId"))) {
                            productItem.getOrderItemBillingsList().add(new OrderItemBilling(orderItemBilling));
                            break;
                        }
                    }
                }
                order.setInvoiceIds(invoiceIds);
                List<OrderPaymentPreference> orderPaymentPreferences = getOrderPaymentPreferences(orderGenVal);
                order.setOrderPaymentPreferences(orderPaymentPreferences);
                /* //get order contacts
                 ArrayList<OrderContact> oclArray = new ArrayList<OrderContact>();
                 List<GenericValue> orderContachMapList = orderGenVal.getRelated("OrderContactMech");
                 for (GenericValue orderContactMech : orderContachMapList) {
                 OrderContact oc = new OrderContact();
                 OrderContactMech ocm = new OrderContactMech(orderContactMech);
                 oc.setOrderContactMech(ocm);
                 //Debug.logInfo("ocm.getcontactMechId() " + ocm.getcontactMechId(), module);
                 PartyContactMechComposite pc = rolesList.getPartyContact(ocm.getcontactMechId());
                 //                if (pc != null) {
                 if (pc != null) {
                 oc.setContact(pc.getContact());
                 oclArray.add(oc);
                 }
                 //                }
                 }
                 OrderContactList ocl = new OrderContactList();
                 ocl.addAll(oclArray);
                 order.setOrderContactList(ocl);
                 */

                OrderContactMechCompositeList oclArray = getOrderContactMechList(orderId, session);
                order.setOrderContactList(oclArray);

                OrderItemShipGroupList orderItemAndShipGrpList = new OrderItemShipGroupList();
                List<GenericValue> orderItemShipGroup = orderGenVal.getRelated("OrderItemShipGroup");
                for (GenericValue iter : orderItemShipGroup) {
                    orderItemAndShipGrpList.add(new OrderItemShipGroup(iter));
                }
                order.setOrderItemShipGroupList(orderItemAndShipGrpList);
                //            order.setDestinationFacilityId(orderGenVal.getString("originFacilityId"));
                order.getFinancials().setOrderTotal(orderReadHelper.getOrderGrandTotal());
                order.setOrderId(orderId);

//            setShoppingCartItemToItemComposite(order,  shopingCart,  session);
                //update any product average cost price
                Debug.logInfo("getunitAverageCost for 1", module);
                for (OrderItemComposite entry : order.getOrderItemsList().getList()) {
                    Debug.logInfo("getunitAverageCost for 2", module);
                    if (entry.getOrderItem() != null) {

                        Debug.logInfo("getunitAverageCost for 3: " + entry.getOrderItem().getorderItemSeqId(), module);
                        if (UtilValidate.isEmpty(entry.getOrderItem().getunitAverageCost())) {
                            OrderItem orderItem = updateOrederItemUnitAverageCost(order.getOrderId(), entry.getOrderItem().getorderItemSeqId(), entry.averageCost);
                        } else if ((entry.getOrderItem().getunitAverageCost().equals(BigDecimal.ZERO)
                                && UtilValidate.isNotEmpty(entry.averageCost)
                                && entry.averageCost.equals(BigDecimal.ZERO) == false)) {
                            Debug.logInfo("getunitAverageCost for 4", module);
                            OrderItem orderItem = updateOrederItemUnitAverageCost(order.getOrderId(), entry.getOrderItem().getorderItemSeqId(), entry.averageCost);
//                        entry.setOrderItem(orderItem);
                        }
                    }
                }
                Map<String, Object> findOption = new HashMap<String, Object>();
                findOption.put("orderId", orderId);
                List<ReturnItemComposite> returnableItems = LoadOrderReturnWorker.getReturnableItems(findOption, session);
                if (returnableItems != null) {
                    order.setReturnableItems(returnableItems);
                }
            }

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }

    static public void createShippingGroup(Order order, String shipmentMethodTypeId, Account account) {
        //set order shipping
        Debug.logInfo("SHIPPING 0 :", module);
        if (order.getShipGroupSize() > 0) {
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
            boolean shippingFound = false;
            Debug.logInfo("SHIPPING 1 :", module);
            if (account != null) {
                Debug.logInfo("SHIPPING 2 :", module);
                PartyContactMechComposite pc = getPartyContactFromPurpose(account, "SHIPPING_LOCATION");;//billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("SHIPPING_LOCATION");
                if (pc != null) {
                    Debug.logInfo("SHIPPING 3 :" + pc.getPartyContactMech().getcontactMechId(), module);
                    shippingFound = true;
                    cartShipInfo.setContactMechId(pc.getPartyContactMech().getcontactMechId());
                    oisg.setcontactMechId(pc.getPartyContactMech().getcontactMechId());
                    order.setShippingContact("SHIPPING_LOCATION", pc.getPartyContactMech().getcontactMechId());
//                            orderToStringBuilder.append(getPostalAddress(contactList, "SHIPPING_LOCATION"));
//        orderToStringBuilder.append(getTelecomNumbers(contactList, "PHONE_SHIPPING"));
//        orderToStringBuilder.append(getTelecomNumbers(contactList, "FAX_SHIPPING"));
                    //order.setShippingContact(pc);
                }
            }
            Debug.logInfo("SHIPPING 4 :", module);
            if (!shippingFound) {
                if (order.getContactMechId("SHIPPING_LOCATION") != null) {
                    //OrderContactMechComposite contact = order.getOrderContactList().getShippingLocationContact();
                    cartShipInfo.setContactMechId(order.getContactMechId("SHIPPING_LOCATION"));
                    oisg.setcontactMechId(order.getContactMechId("SHIPPING_LOCATION"));
                    order.setShippingContact("SHIPPING_LOCATION", order.getContactMechId("SHIPPING_LOCATION"));
                    Debug.logInfo("SHIPPING 5 :", module);
                } else {
                    if (order.getContactMechId("BILLING_LOCATION") != null) {
                        cartShipInfo.setContactMechId(order.getContactMechId("BILLING_LOCATION"));
                        oisg.setcontactMechId(order.getContactMechId("BILLING_LOCATION"));
                        order.setShippingContact("SHIPPING_LOCATION", order.getContactMechId("BILLING_LOCATION"));
                        Debug.logInfo("SHIPPING 6 :", module);
                    }
                }
            }
        } else {
            Debug.logInfo("SHIPPING 7 :", module);
            if (order.getShippingContactMechId() != null) {
                Debug.logInfo("SHIPPING 8 :", module);
                order.setShippingContact("SHIPPING_LOCATION", order.getShippingContactMechId());
            }
        }
    }

    static public List<OrderPaymentPreference> getOrderPaymentPreferences(GenericValue order) {

        List<OrderPaymentPreference> paymentPrefences = new ArrayList<OrderPaymentPreference>();

        if (UtilValidate.isNotEmpty(order)) {
            List<GenericValue> orderPaymentPreferences;
            try {
                orderPaymentPreferences = order.getRelated("OrderPaymentPreference");
                if (UtilValidate.isNotEmpty(orderPaymentPreferences)) {
                    for (GenericValue orderPaymentPreference : orderPaymentPreferences) {
                        paymentPrefences.add(new OrderPaymentPreference(orderPaymentPreference));
                    }
                }
            } catch (GenericEntityException ex) {
                Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return paymentPrefences;
    }

    static public OrderPaymentPreference getOrderPaymentPreference(GenericValue order) {

        OrderPaymentPreference paymentPrefence = null;

        if (UtilValidate.isNotEmpty(order)) {
            List<GenericValue> orderPaymentPreferences;
            try {
                orderPaymentPreferences = order.getRelated("OrderPaymentPreference");
                if (UtilValidate.isNotEmpty(orderPaymentPreferences)) {
                    GenericValue orderPaymentPreference = EntityUtil.getFirst(orderPaymentPreferences);

                    paymentPrefence = new OrderPaymentPreference(orderPaymentPreference);

                }
            } catch (GenericEntityException ex) {
                Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return paymentPrefence;
    }

    static public Set<String> getOrderInvoiceIds(GenericValue orderGenVal) {
        Set<String> invoiceIds = new HashSet<String>();
        try {
            List<GenericValue> orderItemBillings = orderGenVal.getRelated("OrderItemBilling");
            for (Iterator<GenericValue> iter = orderItemBillings.iterator(); iter.hasNext();) {
                GenericValue orderItemBilling = iter.next();
                invoiceIds.add(orderItemBilling.getString("invoiceId"));
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoiceIds;
    }

    static public Set<String> getInvoiceOrderIds(GenericValue invoiceGenVal) {
        Set<String> orderIds = new HashSet<String>();
        try {
            List<GenericValue> orderItemBillings = invoiceGenVal.getRelated("OrderItemBilling");
            for (Iterator<GenericValue> iter = orderItemBillings.iterator(); iter.hasNext();) {
                GenericValue orderItemBilling = iter.next();
                orderIds.add(orderItemBilling.getString("orderId"));
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderIds;
    }

    static public Set<String> getOrderReturnIds(GenericValue orderGenVal) {
        Set<String> returnIds = new HashSet<String>();
        try {

            List<GenericValue> returnItems = orderGenVal.getRelated("ReturnItem");
            for (Iterator<GenericValue> iter = returnItems.iterator(); iter.hasNext();) {
                GenericValue orderItemBilling = iter.next();
                returnIds.add(orderItemBilling.getString("returnId"));
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnIds;
    }

    static public Set<String> getOrderReturnInvoiceIds(GenericValue returnHeaderGenVal) {
        Set<String> invoiceIds = new HashSet<String>();
        try {
            List<GenericValue> returnItemBillings = returnHeaderGenVal.getRelated("ReturnItemBilling");
            for (Iterator<GenericValue> iter = returnItemBillings.iterator(); iter.hasNext();) {
                GenericValue returnItemBilling = iter.next();
                invoiceIds.add(returnItemBilling.getString("invoiceId"));
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invoiceIds;

    }

    /**
     * Get the returned total by return type (credit, refund, etc.). Specify
     * returnTypeId = null to get sum over all return types. Specify includeAll
     * = true to sum up over all return statuses except cancelled. Specify
     * includeAll = false to sum up over ACCEPTED,RECEIVED And COMPLETED
     * returns.
     */
    /*
     public BigDecimal getOrderReturnedTotalByTypeBd( List<GenericValue> returnedItemsBase, boolean includeAll ) {


     List<GenericValue> returnedItems = new ArrayList<GenericValue>(returnedItemsBase.size());

     // get only the RETURN_RECEIVED and RETURN_COMPLETED statusIds
     if (!includeAll) {
     returnedItems.addAll(EntityUtil.filterByAnd(returnedItemsBase, UtilMisc.toMap("statusId", "RETURN_ACCEPTED")));
     returnedItems.addAll(EntityUtil.filterByAnd(returnedItemsBase, UtilMisc.toMap("statusId", "RETURN_RECEIVED")));
     returnedItems.addAll(EntityUtil.filterByAnd(returnedItemsBase, UtilMisc.toMap("statusId", "RETURN_COMPLETED")));
     } else {
     // otherwise get all of them except cancelled ones
     returnedItems.addAll(EntityUtil.filterByAnd(returnedItemsBase,
     UtilMisc.toList(EntityCondition.makeCondition("statusId", EntityOperator.NOT_EQUAL, "RETURN_CANCELLED"))));
     }
     BigDecimal returnedAmount = ZERO;
     String orderId = orderHeader.getString("orderId");
     List<String> returnHeaderList = FastList.newInstance();
     for(GenericValue returnedItem : returnedItems) {
     if ((returnedItem.get("returnPrice") != null) && (returnedItem.get("returnQuantity") != null)) {
     returnedAmount = returnedAmount.add(returnedItem.getBigDecimal("returnPrice").multiply(returnedItem.getBigDecimal("returnQuantity")).setScale(scale, rounding));
     }
     Map<String, Object> itemAdjustmentCondition = UtilMisc.toMap("returnId", returnedItem.get("returnId"), "returnItemSeqId", returnedItem.get("returnItemSeqId"), "returnTypeId", returnTypeId);
     returnedAmount = returnedAmount.add(getReturnAdjustmentTotal(orderHeader.getDelegator(), itemAdjustmentCondition));
     if (orderId.equals(returnedItem.getString("orderId")) && (!returnHeaderList.contains(returnedItem.getString("returnId")))) {
     returnHeaderList.add(returnedItem.getString("returnId"));
     }
     }
     //get  returnedAmount from returnHeader adjustments whose orderId must equals to current orderHeader.orderId
     for(String returnId : returnHeaderList) {
     Map<String, Object> returnHeaderAdjFilter = UtilMisc.<String, Object>toMap("returnId", returnId, "returnItemSeqId", "_NA_", "returnTypeId", returnTypeId);
     returnedAmount =returnedAmount.add(getReturnAdjustmentTotal(orderHeader.getDelegator(), returnHeaderAdjFilter)).setScale(scale, rounding);
     }
     return returnedAmount.setScale(scale, rounding);
     }
     */
    static public List<OrderSummaryView> getOrderSummary(XuiSession session, List<EntityCondition> entityConditionList) {
        List<OrderSummaryView> listModel = new ArrayList<OrderSummaryView>();

        List<GenericValue> resultList = getOrderList(session, entityConditionList);
//PosProductHelper.getReadOnlyGenericValueListsWithSelection("OrderHeader", entityConditionList, "orderId", session.getDelegator(), true);
        Debug.logInfo("resultList " + resultList.size(), module);

//        List<GenericValue> filteredList = EntityUtil.filterByDate(resultList);
        for (GenericValue gvOrder : resultList) {
            try {
                org.ofbiz.order.order.OrderReadHelper orderReadHelper = new org.ofbiz.order.order.OrderReadHelper(session.getDelegator(), gvOrder.getString("orderId"));
//            ListAdapterListModel<OrderSummaryView> listModel = new ListAdapterListModel<OrderSummaryView>();
                OrderSummaryView orderSummaryView = new OrderSummaryView();
                orderSummaryView.setNumber(orderReadHelper.getOrderId());
                orderSummaryView.setDetails(orderReadHelper.getOrderName());
                orderSummaryView.setType(OrderTypeSingleton.getOrderType(orderReadHelper.getOrderTypeId()).getdescription());
                orderSummaryView.setOrderTypeId(orderReadHelper.getOrderTypeId());
                orderSummaryView.setAmount(orderReadHelper.getOrderGrandTotal());
                orderSummaryView.setReference(orderReadHelper.getOrderHeader().getString("externalId"));
                orderSummaryView.setDate(orderReadHelper.getOrderHeader().getTimestamp("orderDate"));
                orderSummaryView.setLineItemType(OrderSummaryView.LineItemType.OrderLineItem);
                listModel.add(orderSummaryView);
                Set<String> invoiceIds = LoadOrderWorker.getOrderInvoiceIds(orderReadHelper.getOrderHeader());
                for (String invoiceId : invoiceIds) {
                    try {
                        InvoiceComposite invoiceComp = LoadInvoiceWorker.loadInvoice(invoiceId, session);
                        orderSummaryView = new OrderSummaryView();
                        if (invoiceComp.getInvoice() != null) {
                            orderSummaryView.setNumber(invoiceComp.getInvoice().getinvoiceId());
                            orderSummaryView.setDetails(invoiceComp.getInvoice().getdescription());
                            orderSummaryView.setType(InvoiceTypeSingleton.getInvoiceType(invoiceComp.getInvoice().getinvoiceTypeId()).getdescription());
                            orderSummaryView.setReference(invoiceComp.getInvoice().getreferenceNumber());
                            orderSummaryView.setDate(invoiceComp.getInvoice().getinvoiceDate());
                            orderSummaryView.setLineItemType(OrderSummaryView.LineItemType.InvoiceLineItem);
                            if (invoiceComp.getInvoice().getdueDate() != null) {
                                orderSummaryView.setDueDate(invoiceComp.getInvoice().getdueDate());
                            } else {
                                orderSummaryView.setDueDate(invoiceComp.getInvoice().getinvoiceDate());
                            }
                        }
                        orderSummaryView.setAmount(invoiceComp.getTotalAmount());
                        listModel.add(orderSummaryView);

                        Set<String> returnIds = LoadOrderWorker.getOrderReturnIds(orderReadHelper.getOrderHeader());
                        for (String returnId : returnIds) {
                            ReturnHeaderComposite returnHeaderComposite = LoadOrderReturnWorker.loadReturnHeaderComposite(returnId, session);
                            if (returnHeaderComposite != null) {

                                orderSummaryView = new OrderSummaryView();

                                orderSummaryView.setNumber(returnId);
                                orderSummaryView.setDetails(returnId);
                                orderSummaryView.setType(ReturnHeaderTypeSingleton.getReturnHeaderType(returnHeaderComposite.getReturnHeaderType()).getdescription());
                                orderSummaryView.setAmount(returnHeaderComposite.getGrandTotal());
                                orderSummaryView.setLineItemType(OrderSummaryView.LineItemType.OrderReturnLineItem);
                                listModel.add(orderSummaryView);

                                Set<String> returnInvoiceIds = getOrderReturnInvoiceIds(returnHeaderComposite.getReturnHeader().getGenericValueObj());
                                for (String returnInvoiceId : returnInvoiceIds) {

                                    InvoiceComposite returnInvoiceComp = LoadInvoiceWorker.loadInvoice(returnInvoiceId, session);
                                    orderSummaryView = new OrderSummaryView();
                                    if (returnInvoiceComp.getInvoice() != null) {
                                        orderSummaryView.setNumber(returnInvoiceComp.getInvoice().getinvoiceId());
                                        orderSummaryView.setDetails(returnInvoiceComp.getInvoice().getdescription());
                                        orderSummaryView.setType(InvoiceTypeSingleton.getInvoiceType(returnInvoiceComp.getInvoice().getinvoiceTypeId()).getdescription());
                                        orderSummaryView.setReference(returnInvoiceComp.getInvoice().getreferenceNumber());
                                        orderSummaryView.setDate(returnInvoiceComp.getInvoice().getinvoiceDate());
                                        orderSummaryView.setLineItemType(OrderSummaryView.LineItemType.InvoiceLineItem);
                                        if (returnInvoiceComp.getInvoice().getdueDate() != null) {
                                            orderSummaryView.setDueDate(returnInvoiceComp.getInvoice().getdueDate());
                                        } else {
                                            orderSummaryView.setDueDate(returnInvoiceComp.getInvoice().getinvoiceDate());
                                        }
                                        orderSummaryView.setAmount(returnInvoiceComp.getTotalAmount());
                                        listModel.add(orderSummaryView);
                                    }

                                }

                            }
                        }

                        PaymentApplicationCompositeList applicationList = LoadPaymentWorker.loadPaymentApplicationsForInvoice(invoiceId, session);
                        for (PaymentApplication pa : applicationList.getList()) {
                            PaymentComposite paymentComposite = LoadPaymentWorker.loadPayment(pa.getpaymentId(), session);
                            String paymentFromPartyId = paymentComposite.getPayment().getpartyIdTo();
                            orderSummaryView = new OrderSummaryView();
                            orderSummaryView.setOrderTypeId(orderReadHelper.getOrderTypeId());
                            orderSummaryView.setNumber(pa.getpaymentId());
                            orderSummaryView.setDetails(pa.getpaymentApplicationId());
                            orderSummaryView.setType(PaymentTypeSingleton.getPaymentType(paymentComposite.getPayment().getpaymentTypeId()).getDescription());
                            orderSummaryView.setReference(pa.getpaymentId());
                            orderSummaryView.setDate(pa.getcreatedStamp());
                            orderSummaryView.setDueDate(null);
                            orderSummaryView.setAmount(pa.getamountApplied());
                            orderSummaryView.setLineItemType(OrderSummaryView.LineItemType.PaymentLineItem);
                            listModel.add(orderSummaryView);
                        }

                        List<Map<String, Object>> paymentList = LoadInvoiceWorker.getInvoicePaymentList(invoiceComp.getInvoice().getGenericValueObj(), session);
                        for (Map<String, Object> gv : paymentList) {

                            //     if (invoiceComp.getInvoice() != null) {
                            OrderSummaryView paidSummaryView = new OrderSummaryView();

                            paidSummaryView.setNumber("");
                            // orderSummaryView.setDetails(invoiceComp.getInvoice().getdescription());
                            paidSummaryView.setType("");
                            paidSummaryView.setReference("Paid Amount");
                            paidSummaryView.setAmount((BigDecimal) gv.get("paidAmount"));
                            paidSummaryView.setDate(null);
                            paidSummaryView.setDueDate(null);
                            if (!paidSummaryView.getAmount().equals(BigDecimal.ZERO)) {
                                listModel.add(paidSummaryView);
                            }
                            //   }
                            // if (invoiceComp.getInvoice() != null) {
                            OrderSummaryView balanceSummaryView = new OrderSummaryView();
                            balanceSummaryView.setNumber("");
                            // orderSummaryView.setDetails(invoiceComp.getInvoice().getdescription());
                            balanceSummaryView.setType("");
                            balanceSummaryView.setReference("Balance Amount");
                            balanceSummaryView.setAmount((BigDecimal) gv.get("outstandingAmount"));
                            balanceSummaryView.setDate(null);
                            balanceSummaryView.setDueDate(null);
                            listModel.add(balanceSummaryView);
                            //}
                            OrderSummaryView emptySummaryView = new OrderSummaryView();
                            listModel.add(emptySummaryView);

                        }

                    } catch (Exception ex) {
                        Logger.getLogger(OrderViewDetailAction.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                OrderSummaryView emptySummaryView = new OrderSummaryView();
                listModel.add(emptySummaryView);

                /*
                 JOptionPane pane = new JOptionPane(viewer, JOptionPane.PLAIN_MESSAGE,
                 JOptionPane.DEFAULT_OPTION, null, new Object[0], null);
                
                 final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Product Category");
                
                 viewer.addActionListener(new ActionListener() {
                
                 public void actionPerformed(ActionEvent e) {
                 dialog.setVisible(false);
                 }
                 });
                
                 //            JPanel panelJasper = twoPanelContainerPanel.getPanelDetail();
                 //                            chooser.getPanelSelecton().setVisible(false);
                 //            panelJasper.removeAll();
                 //            panelJasper.setLayout(new GridLayout(1, 1));
                 //            panelJasper.add(viewer);
                 setSizeAndLocation(dialog);
                 dialog.setVisible(true);
                 */
            } catch (Exception ex) {
                Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listModel;
    }

    static public OrderDeliverySchedule loadOrderDeliverySchedule(String orderId, XuiSession session) {
        GenericValue orderItem = null;
        try {
            orderItem = XuiContainer.getSession().getDelegator().findByPrimaryKey("OrderDeliverySchedule", UtilMisc.toMap("orderId", orderId, "orderItemSeqId", "_NA_"));
        } catch (GenericEntityException ex) {
            Debug.logError(ex, module);
        }
        if (orderItem != null) {
            return new OrderDeliverySchedule(orderItem);
        } else {
            return null;
        }
    }

    static public OrderContactMechCompositeList getOrderContactMechList(String orderId, XuiSession session) {
//            Debug.logInfo("ContactMechPurposeType: " + val.getdescription(), NAME);

        ArrayList itemList = new ArrayList();
        List<Map<String, GenericValue>> orderMechList = ContactMechWorker.getOrderContactMechValueMaps(session.getDelegator(), orderId);
        Debug.logInfo("getOrderContactMechList: " + orderId + " orderMechList: " + orderMechList.size(), module);
        if (orderMechList != null) {
            //Debug.logInfo("if (partyMechList != null): " + partyId, NAME);
            for (Map<String, GenericValue> mapOfObject : orderMechList) {
                OrderContactMechComposite orderContact = new OrderContactMechComposite();
                if (mapOfObject.containsKey("contactMech")) {

                    orderContact.setOrderContactMech(new OrderContactMech((GenericValue) mapOfObject.get("orderContactMech")));

                    GenericValue contactMech = (GenericValue) mapOfObject.get("contactMech");
                    String contactMechTypeId = contactMech.getString("contactMechTypeId");
                    String contactMechId = contactMech.getString("contactMechId");
                    Debug.logInfo("contactMechId: " + contactMechId, module);

                    Contact contact = new Contact();
                    contact.setContactMech(new ContactMech(contactMech));

                    if ("POSTAL_ADDRESS".equalsIgnoreCase(contactMechTypeId)) {

                        contact.setContactMechTypeId(contactMechTypeId);
                        if (mapOfObject.containsKey("postalAddress") && mapOfObject.get("postalAddress") != null) {
                            contact.setPostalAddress(new PostalAddress((GenericValue) mapOfObject.get("postalAddress")));
                        }
                    } else if ("TELECOM_NUMBER".equalsIgnoreCase(contactMechTypeId)) {

                        if (mapOfObject.containsKey("telecomNumber") && mapOfObject.get("telecomNumber") != null) {
                            contact.setTelecomNumber(new TelecomNumber((GenericValue) mapOfObject.get("telecomNumber")));
                        }
                    }

                    //set the contact
                    orderContact.setContact(contact);
                    itemList.add(orderContact);

                    GenericValue contactMechPurposeType = (GenericValue) mapOfObject.get("contactMechPurposeType");
                    try {
                        orderContact.setPartyContactMechPurposeType(ContactMechPurposeTypeSingleton.getContactMechPurposeType(contactMechPurposeType.getString("contactMechPurposeTypeId")));
                    } catch (Exception ex) {
                        Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        OrderContactMechCompositeList orderContactMechList = new OrderContactMechCompositeList();
        orderContactMechList.addAll(itemList);
        return orderContactMechList;
    }

    static public void updateOrederItemUnitAverageCost(Order order) {
        for (OrderItemComposite entry : order.getOrderItemsList().getList()) {
            if (entry.getShoppingCartItem() != null) {
                OrderItem orderItem = updateOrederItemUnitAverageCost(order.getOrderId(), entry.getShoppingCartItem().getOrderItemSeqId(), entry.averageCost);
                entry.setOrderItem(orderItem);
                /*                Debug.logInfo("entry.getOrderItem().getorderId() : " + order.getOrderId()
                 + " entry.getShoppingCartItem().getOrderItemSeqId(): " + entry.getShoppingCartItem().getOrderItemSeqId()
                 + " entry.getOrderItem().isGenericValueSet(): " + entry.getOrderItem().isGenericValueSet(), module);

                 GenericValue orderItem;
                 try {
                 orderItem = XuiContainer.getSession().getDelegator().findByPrimaryKey("OrderItem", UtilMisc.toMap("orderId", order.getOrderId(),
                 "orderItemSeqId", entry.getShoppingCartItem().getOrderItemSeqId()));
                 orderItem.set("unitAverageCost", entry.averageCost);
                 XuiContainer.getSession().getDelegator().store(orderItem);
                 entry.setOrderItem(new OrderItem(orderItem));
                 } catch (GenericEntityException ex) {
                 Debug.logError(ex, module);
                 }*/
            }
        }

    }

    static public OrderItem updateOrederItemUnitAverageCost(String orderId, String seq, BigDecimal unitAvgCost) {

        GenericValue orderItem = null;
        Debug.logInfo("getunitAverageCost for 5", module);
        try {
            orderItem = XuiContainer.getSession().getDelegator().findByPrimaryKey("OrderItem", UtilMisc.toMap("orderId", orderId,
                    "orderItemSeqId", seq));
            if (orderItem != null) {
                orderItem.set("unitAverageCost", unitAvgCost);
                XuiContainer.getSession().getDelegator().store(orderItem);
            }

        } catch (GenericEntityException ex) {
            Debug.logError(ex, module);
        }
        return new OrderItem(orderItem);
    }

    public static OrderRolesList getOrderRolesList(GenericValue orderGenVal, XuiSession session) {
        //let set order composite to order
        ArrayList<OrderRoleComposite> array = new ArrayList<OrderRoleComposite>();
        try {
            List<GenericValue> orderRoleList = orderGenVal.getRelated("OrderRole");
            for (GenericValue orderRoleGen : orderRoleList) {
                try {
                    //create order role
                    OrderRole orderRole = new OrderRole(orderRoleGen);
                    //crreate the order composite
                    OrderRoleComposite orderRoleComp = new OrderRoleComposite();
                    //get party details
                    Account acct = LoadAccountWorker.getAccount(orderRole.getpartyId(), session);
                    orderRoleComp.setOrderRole(orderRole);
                    orderRoleComp.setParty(acct);
                    //add to list
                    array.add(orderRoleComp);
                } catch (Exception ex) {
                    Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        //let set order composite to order
        OrderRolesList rolesList = new OrderRolesList();
        rolesList.addAll(array);
        return rolesList;
    }

    public static List<GenericValue> getOrderList(Map<String, Object> svcCtx, XuiSession session) {
        Map<String, Object> svcRes = null;
//                Map<String, Object> svcCtx = FastMap.newInstance();
        List<GenericValue> orderList = null; //(List<GenericValue>) svcRes.get("orderList");

        try {
            LocalDispatcher dispatcher = session.getDispatcher();
            svcRes = dispatcher.runSync("findOrders", svcCtx);
            for (Map.Entry<String, Object> entryDept : svcRes.entrySet()) {
                Debug.logInfo("key: " + entryDept.getKey() + " value: " + entryDept.getValue(), module);
            }
            if (svcRes.containsKey("orderList")) {
                orderList = (List<GenericValue>) svcRes.get("orderList");
            }
        } catch (GenericServiceException ex1) {
            Debug.logError(ex1, module);
            try {
                //OrderMaxOptionPane.showMessageDialog(null,"dialog/error/exception", e.getMessage());
                throw new GeneralException(ServiceUtil.getErrorMessage(svcRes));
            } catch (GeneralException ex) {
                Logger.getLogger(LoadSalesOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return orderList;
    }

    //create order role composite
    public static OrderRoleComposite createOrderRole(String roleTypeId, Account account) {
        OrderRoleComposite orc = new OrderRoleComposite();
        orc.setParty(account);
        OrderRole role = new OrderRole();
        role.setroleTypeId(roleTypeId);
        role.setpartyId(account.getParty().getpartyId());
        orc.setOrderRole(role);
        return orc;
    }
    /*
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
     Account acct = LoadAccountWorker.getAccount(orderRole.getpartyId(), session);
     orderRoleComp.setOrderRole(orderRole);
     orderRoleComp.setParty(acct);
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
     //Debug.logInfo("ocm.getcontactMechId() " + ocm.getcontactMechId(), module);
     PartyContactMechComposite pc = rolesList.getPartyContact(ocm.getcontactMechId());
     //                if (pc != null) {
     if (pc != null) {
     oc.setContact(pc.getContact());
     oclArray.add(oc);
     }
     //                }
     }
     ocl.addAll(oclArray);
     } catch (GenericEntityException ex) {
     Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
     } catch (Exception ex) {
     Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
     }
     return ocl;
     }
     */

    public static void createSupplierOrderRoles(OrderRolesList rolesList, Account billFrom, Account billTo) {
        //Debug.logInfo("createSupplierOrderRoles :", module);
        String roleTypeId = "BILL_TO_CUSTOMER";
        rolesList.add(createOrderRole(roleTypeId, billTo));
        roleTypeId = "BILL_FROM_VENDOR";
        rolesList.add(createOrderRole(roleTypeId, billFrom));
        roleTypeId = "SHIP_FROM_VENDOR";
        rolesList.add(createOrderRole(roleTypeId, billFrom));
        roleTypeId = "SUPPLIER";
        rolesList.add(createOrderRole(roleTypeId, billFrom));
    }

    public static PartyContactMechComposite getPartyContactFromPurpose(Account account, String purposeId) {
        for (PartyContactMechComposite pc : account.getPartyContactList().getList()) {
            if (pc.isPartyContactMechPurposeExists(purposeId)) {
                //String contachMechId = pc.getPartyContactMechPurposeList().getPartyContactPurpose(purposeId);
                return pc;
            }
        }
        return null;
    }

    public static void createSupplierOrderContacts(OrderContactMechCompositeList ocl, Account billFrom) {
        //Debug.logInfo("createSupplierOrderContacts :", module);
        PartyContactMechComposite pc = getPartyContactFromPurpose(billFrom, "BILLING_LOCATION"); //billFrom.getPartyContactList().getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_LOCATION");
        //Debug.logInfo("BILLING_LOCATION 1 :", module);
        if (pc != null) {
            //Debug.logInfo("BILLING_LOCATION 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
            ocl.add(createSupplierOrderContact(pc, "BILLING_LOCATION"));
        }
        //Debug.logInfo("BILLING_LOCATION 3 :", module);
        pc = getPartyContactFromPurpose(billFrom, "PHONE_BILLING"); //billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("PHONE_BILLING");
        if (pc != null) {
            //Debug.logInfo("BILLING_LOCATION 4 :", module);
            ocl.add(createSupplierOrderContact(pc, "PHONE_BILLING"));
        }
        //Debug.logInfo("BILLING_LOCATION 5 :", module);
        pc = getPartyContactFromPurpose(billFrom, "BILLING_EMAIL"); //billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("BILLING_EMAIL");
        if (pc != null) {
            ocl.add(createSupplierOrderContact(pc, "BILLING_EMAIL"));
        }
        pc = getPartyContactFromPurpose(billFrom, "FAX_BILLING");
        ; //billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("FAX_BILLING");
        if (pc != null) {
            ocl.add(createSupplierOrderContact(pc, "FAX_BILLING"));
        }
        pc = getPartyContactFromPurpose(billFrom, "PHONE_SHIPPING");
        ; //billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("PHONE_SHIPPING");
        if (pc != null) {
            ocl.add(createSupplierOrderContact(pc, "PHONE_SHIPPING"));
        }
        pc = getPartyContactFromPurpose(billFrom, "SHIPPING_LOCATION");
        ; //billFrom.getPartyContactMechPurposeList().getPartyContactPurpose("SHIPPING_LOCATION");
        if (pc != null) {
            //Debug.logInfo("SHIPPING 2 :" + pc.getPartyContactMech().getcontactMechId(), module);
            ocl.add(createSupplierOrderContact(pc, "SHIPPING_LOCATION"));
        }
    }

    public static OrderContactMechComposite createSupplierOrderContact(PartyContactMechComposite pc, String purposeId) {
        //Debug.logInfo("createSupplierOrderContact2 :" + pc.getContact().getContactMechTypeId(), module);
        OrderContactMechComposite omc = new OrderContactMechComposite();
        OrderContactMech ocm = new OrderContactMech();
        ocm.setcontactMechId(pc.getPartyContactMech().getcontactMechId());
        ocm.setcontactMechPurposeTypeId(purposeId);
//        OrderContact oc = new OrderContact();
        omc.setContact(pc.getContact());
        omc.setOrderContactMech(ocm);
        omc.setContachMechId(pc.getPartyContactMech().getcontactMechId());

        try {
            omc.setPartyContactMechPurposeType(ContactMechPurposeTypeSingleton.getContactMechPurposeType(purposeId));
        } catch (Exception ex) {
            Logger.getLogger(LoadOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return omc;
    }

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
//            SupplierProductList spl = LoadProductPriceWorker.getSupplierProduct(productId, session);
//            oic.setSupplierProductList(spl);
            oic.setSupplierProductList(new SupplierProductList());
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadPurchaseOrderWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oic;
    }

    public static void removeOrderItem(Order order, OrderItemComposite orderItemComposite, XuiSession session) throws CartItemModifyException {
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> svcCtx = FastMap.newInstance();
        svcCtx.put("userLogin", session.getUserLogin());
        svcCtx.put("orderId", orderItemComposite.getOrderItem().getorderId());
        svcCtx.put("orderItemSeqId", orderItemComposite.getOrderItem().getorderItemSeqId());
        Map<String, Object> svcRes = null;
        try {
            svcRes = dispatcher.runSync("cancelOrderItem", svcCtx);
            for (Map.Entry<String, Object> entryDept : svcRes.entrySet()) {
                Debug.logInfo("key: " + entryDept.getKey() + " value: " + entryDept.getValue(), module);
            }
        } catch (GenericServiceException e) {
            //Debug.logError(e, module);
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        }
        order.getOrderItemsList().remove(orderItemComposite);

    }

    public static OrderItemComposite newOrderItem(XuiSession session) {
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

    public static OrderItemComposite updateOrderItem(OrderItemComposite oic, String facilityId, XuiSession session) {
        SupplierProductAndListPriceData data = PosProductHelper.getOrderItemDetails(oic.getOrderItem().getproductId(), facilityId, session);
        //Debug.logInfo("before update : " + oic.getOrderItem().getitemDescription(), module);
        //        orderItem.setOrderItemData(data);
        return oic;
    }

    public static void calculateProductItemValues(OrderItemComposite oic) {
        if (oic.getOrderItem().getunitPrice() != null && oic.getOrderItem().getquantity() != null) {
            oic.setTotalAmount(oic.getOrderItem().getquantity().multiply(oic.getOrderItem().getunitPrice()));
        }
    }

    public static void calculateOrderValues(Order order) {
        /*if(oic.getOrderItem().getunitPrice() != null &&
         oic.getOrderItem().getquantity() != null    ){
         oic.saleLineTotal = oic.getOrderItem().getquantity().multiply(oic.getOrderItem().getunitPrice());
         }*/
    }

    protected BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;

    public LoadOrderWorker(ListAdapterListModel<Order> listModel, Map<String, Object> findOptionMap) {
        super(listModel, findOptionMap);
    }

    @Override
    protected abstract List<Order> doInBackground() throws Exception;

}
