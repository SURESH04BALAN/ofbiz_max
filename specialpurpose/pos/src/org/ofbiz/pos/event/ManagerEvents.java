/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ofbiz.pos.event;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import javolution.util.FastMap;
import mvc.controller.dataload.posdata.PosSalesData;
import net.xoetrope.xui.XProjectManager;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.adaptor.SyncCallbackAdaptor;
import org.ofbiz.pos.component.Input;
import org.ofbiz.pos.component.Output;
import org.ofbiz.pos.device.DeviceLoader;
import org.ofbiz.pos.device.impl.Receipt;
import org.ofbiz.pos.screen.PaidInOut;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.ofbiz.ordermax.base.OnePanelNonSizableContainerDlg;
import org.ofbiz.ordermax.price.PriceMaintainTreeController;
import org.ofbiz.pos.pospanel.PosPanelMainScreen;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.orders.OrderDisplayDialog;
import org.ofbiz.ordermax.pospaneldesigner.PosPanelDesignerMainScreen;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.Key;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderController;
import org.ofbiz.ordermax.party.FindPartyListController;
import org.ofbiz.ordermax.party.PartyTreeMaintainController;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.payment.FindPaymentListController;
import org.ofbiz.ordermax.product.FilteredJList;
import org.ofbiz.ordermax.product.ProductSearchDialog;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.ordermax.product.catalog.CategoryProductManagePanel;
import org.ofbiz.ordermax.product.productloader.ProductDataLoaderInterace;
import org.ofbiz.ordermax.screens.DateSelectionDialog;

public class ManagerEvents {

    public static final String module = ManagerEvents.class.getName();
    public static boolean mgrLoggedIn = false;
    static DecimalFormat priceDecimalFormat = new DecimalFormat("#,##0.00");
    // scales and rounding modes for BigDecimal math
    public static final int scale = UtilNumber.getBigDecimalScale("order.decimals");
    public static final int rounding = UtilNumber.getBigDecimalRoundingMode("order.rounding");
    public static final BigDecimal ZERO = (BigDecimal.ZERO).setScale(scale, rounding);

    public static synchronized void modifyPrice(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        String sku = null;
        try {
            sku = MenuEvents.getSelectedItem(pos);
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        if (sku == null) {
            pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosInvalidSelection", Locale.getDefault()));
            pos.getJournal().refresh(pos);
            pos.getInput().clear();
        }

        Input input = pos.getInput();
        String value = input.value();
        if (UtilValidate.isNotEmpty(value)) {
            BigDecimal price = ZERO;
            boolean parsed = false;
            try {
                price = new BigDecimal(value);
                parsed = true;
            } catch (NumberFormatException e) {
            }

            if (parsed) {
                ShoppingCartItem shoppingCartItem = trans.findShoppingCartItem(sku);

                if (shoppingCartItem != null && shoppingCartItem.taxApplies()) {
                    price = price.multiply(new BigDecimal(0.909090909090909091));
                }

                price = price.movePointLeft(2);

                trans.modifyPrice(sku, price);

                // re-calc tax
                trans.calcTax();
            }
        }

        // refresh the other components
        pos.refresh();
    }

    public static synchronized void openTerminal(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
            return;
        }

        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Input input = pos.getInput();
        if (!trans.isOpen()) {
            if (input.isFunctionSet("OPEN")) {
                BigDecimal amt = ZERO;
                String amountStr = input.value();
                if (UtilValidate.isNotEmpty(amountStr)) {
                    try {
                        amt = new BigDecimal(amountStr);
                        amt = amt.movePointLeft(2);
                    } catch (NumberFormatException e) {
                        Debug.logError(e, module);
                    }
                }
                GenericValue state = pos.getSession().getDelegator().makeValue("PosTerminalState");
                state.set("posTerminalId", pos.getSession().getId());
                state.set("openedDate", UtilDateTime.nowTimestamp());
                state.set("openedByUserLoginId", pos.getSession().getUserId());
                state.set("startingTxId", trans.getTransactionId());
                state.set("startingDrawerAmount", amt);
                try {
                    state.create();
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                    pos.showMessageDialog("dialog/error/exception", e.getMessage());
                }
                pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosIsOpen", Locale.getDefault()));
                NavagationEvents.showPosScreen(pos);
            } else {
                input.clear();
                input.setFunction("OPEN");
                pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosOpDrAm", Locale.getDefault()));
                return;
            }
        } else {
            pos.showPage("pospanel");
        }
    }

    public static synchronized void closeTerminal(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
            return;
        }
        Debug.logError("1", module);
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
            return;
        }
        /*      try {
         throw new Exception("close pos");
         } catch (Exception e) {
         Debug.logError(e, module);
         }
         */
        Output output = pos.getOutput();
        Input input = pos.getInput();
        if (input.isFunctionSet("CLOSE")) {
            String[] func = input.getFunction("CLOSE");
            String lastValue = input.value();
            if (UtilValidate.isNotEmpty(lastValue)) {

                try {
                    BigDecimal amt = new BigDecimal(lastValue);
                    amt = amt.movePointLeft(2);
                    lastValue = amt.toString();
                } catch (NumberFormatException e) {
                    Debug.logError(e, module);
                }
                if (UtilValidate.isNotEmpty(func[1])) {
                    func[1] = func[1] + "|";
                    func[1] = func[1] + lastValue;
                } else {
                    //cash entry
                    func[1] = func[1] + lastValue;
                    if (pos.getSession().isCanChequePayment()) {
                        //skip chq
                        func[1] = func[1] + "|" + "0.00";
                    }
                }
                input.setFunction("CLOSE", func[1]);
            }

            String[] closeInfo = new String[0];
            if (UtilValidate.isNotEmpty(func[1])) {
                closeInfo = func[1].split("\\|");
            }
            switch (closeInfo.length) {
                case 0:
                    output.print(UtilProperties.getMessage(PosTransaction.resource, "PosEntCas", Locale.getDefault()));
                    func = input.getFunction("CLOSE");
                    if (pos.getSession().isCanChequePayment()) {
                        func[1] = func[1] + "|" + "0.00";
                    }
                    input.setFunction("CLOSE", func[1]);
                    break;
                case 1:
                    output.print(UtilProperties.getMessage(PosTransaction.resource, "PosEntChk", Locale.getDefault()));
                    break;
                case 2:
                    output.print(UtilProperties.getMessage(PosTransaction.resource, "PosEntCrc", Locale.getDefault()));
                    if (pos.getSession().isCanGiftCardPayment()) {
                        func[1] = func[1] + "|" + "0.00";
                        func[1] = func[1] + "|" + "0.00";
                    }
                    input.setFunction("CLOSE", func[1]);
                    break;
                case 3:
                    output.print(UtilProperties.getMessage(PosTransaction.resource, "PosEntGfc", Locale.getDefault()));
                    break;
                case 4:
                    output.print(UtilProperties.getMessage(PosTransaction.resource, "PosEntOth", Locale.getDefault()));
                    break;
                case 5:
                    GenericValue state = trans.getTerminalState();
                    state.set("closedDate", UtilDateTime.nowTimestamp());
                    state.set("closedByUserLoginId", pos.getSession().getUserId());
                    state.set("actualEndingCash", new BigDecimal(closeInfo[0]));
                    state.set("actualEndingCheck", new BigDecimal(closeInfo[1]));
                    state.set("actualEndingCc", new BigDecimal(closeInfo[2]));
                    state.set("actualEndingGc", new BigDecimal(closeInfo[3]));
                    state.set("actualEndingOther", new BigDecimal(closeInfo[4]));
                    state.set("endingTxId", trans.getTransactionId());
                    Debug.logInfo("Updated State - " + state, module);
                    try {
                        state.store();
                        state.refresh();
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                        pos.showMessageDialog("dialog/error/exception", e.getMessage());
                    }

                    // print the totals report
                    output.print(UtilProperties.getMessage(PosTransaction.resource, "PosWaitingFinalSales", Locale.getDefault()));
                    //pos.showMessageDialog("dialog/error/terminalclosed"); JLR 14/11/06 : Pb with that don't know why, useless => commented out
                    printTotals(pos, state, true);

                    // lock the terminal for the moment
                    pos.getInput().setLock(true);
                    pos.getButtons().setLock(true);
                    pos.refresh(false);

                    // transmit final data to server
                    GenericValue terminal = null;
                    try {
                        terminal = state.getRelatedOne("PosTerminal");
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                        pos.showMessageDialog("dialog/error/exception", e.getMessage());
                    }
                    if (terminal != null && terminal.get("pushEntitySyncId") != null) {
                        String syncId = terminal.getString("pushEntitySyncId");
                        SyncCallbackAdaptor cb = new SyncCallbackAdaptor(pos, syncId, state.getTimestamp("lastUpdatedTxStamp"));
                        pos.getSession().getDispatcher().registerCallback("runEntitySync", cb);
                    } else {
                        // no sync setting; just logout
                        SecurityEvents.logout(pos);
                    }
                    // unlock the terminal
                    pos.getInput().setLock(false);
                    pos.getButtons().setLock(false);
                    pos.refresh(true);
                    output.print(UtilProperties.getMessage(PosTransaction.resource, "PosIsClosed", Locale.getDefault()));
                    
            }

        } else {
            trans.popDrawer();
            input.clear();
            input.setFunction("CLOSE");
            output.print(UtilProperties.getMessage(PosTransaction.resource, "PosEntCas", Locale.getDefault()));
        }
        Debug.logError("6", module);
    }

    public static synchronized void voidOrder(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
            return;
        }

        XuiSession session = pos.getSession();
        PosTransaction trans = PosTransaction.getCurrentTx(session);
        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
            return;
        }

        Output output = pos.getOutput();
        Input input = pos.getInput();
        boolean lookup = false;

        if (input.isFunctionSet("VOID")) {
            lookup = true;
        } else if (UtilValidate.isNotEmpty(input.value())) {
            lookup = true;
        }

        if (lookup) {
            GenericValue state = trans.getTerminalState();
            Timestamp openDate = state.getTimestamp("openedDate");

            String orderId = input.value();
            GenericValue orderHeader = null;
            try {
                orderHeader = session.getDelegator().findByPrimaryKey("OrderHeader", UtilMisc.toMap("orderId", orderId));
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }
            if (orderHeader == null) {
                input.clear();
                pos.showMessageDialog("dialog/error/ordernotfound");
                return;
            } else {
                Timestamp orderDate = orderHeader.getTimestamp("orderDate");
                if (orderDate.after(openDate)) {
                    LocalDispatcher dispatcher = session.getDispatcher();
                    Map<String, Object> returnResp = null;
                    try {
                        returnResp = dispatcher.runSync("quickReturnOrder", UtilMisc.<String, Object>toMap("orderId", orderId,
                                "returnHeaderTypeId", "CUSTOMER_RETURN", "userLogin", session.getUserLogin()));
                    } catch (GenericServiceException e) {
                        Debug.logError(e, module);
                        pos.showMessageDialog("dialog/error/exception", e.getMessage());
                        pos.refresh();
                        return;
                    }
                    if (returnResp != null && ServiceUtil.isError(returnResp)) {
                        pos.showMessageDialog("dialog/error/exception", ServiceUtil.getErrorMessage(returnResp));
                        pos.refresh();
                        return;
                    }

                    // todo print void receipt
                    trans.setTxAsReturn((String) returnResp.get("returnId"));
                    input.clear();
                    pos.showMessageDialog("dialog/error/salevoided");
                    pos.refresh();
                } else {
                    input.clear();
                    pos.showMessageDialog("dialog/error/ordernotfound");
                    return;
                }
            }
        } else {
            input.setFunction("VOID");
            output.print(UtilProperties.getMessage(PosTransaction.resource, "PosVoid", Locale.getDefault()));
        }
    }

    public static synchronized void reprintLastTx(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
            return;
        }
        DeviceLoader.receipt.reprintReceipt(true);
        pos.refresh();
    }

    public static synchronized void popDrawer(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showDialog("dialog/error/mgrnotloggedin");
        } else {
            PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());

            trans.popDrawer();
            pos.refresh();
        }
    }

    public static synchronized void clearCache(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            //UtilCache.clearAllCaches();
            pos.refresh();
        }

    }

    public static synchronized void resetXui(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            XProjectManager.getCurrentProject().getPageManager().reset();
            pos.refresh();
        }
    }

    public static synchronized void SwapKeyboard(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            String showKeyboardInSaveSale = null;
            showKeyboardInSaveSale = UtilProperties.getPropertyValue("parameters.properties", "ShowKeyboardInSaveSale");
            if ("N".equalsIgnoreCase(showKeyboardInSaveSale)) {
                UtilProperties.setPropertyValueInMemory("parameters.properties", "ShowKeyboardInSaveSale", "Y");
            } else {
                UtilProperties.setPropertyValueInMemory("parameters.properties", "ShowKeyboardInSaveSale", "N");
            }
        }
    }

    public static synchronized void shutdown(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosShuttingDown", Locale.getDefault()));
            PosTransaction.getCurrentTx(pos.getSession()).closeTx();
            System.exit(0);
        }
    }

    public static synchronized void totalsReport(PosScreen pos) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
            return;
        }
        printTotals(pos, null, false);
    }

    public static synchronized void paidOut(PosScreen pos) {
        paidOutAndIn(pos, "OUT");
    }

    public static synchronized void paidIn(PosScreen pos) {
        paidOutAndIn(pos, "IN");
    }

    public static synchronized void paidOutAndIn(PosScreen pos, String type) {
        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
            return;
        }

        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
            return;
        }

        PaidInOut PaidInOut = new PaidInOut(trans, pos, type);
        Map<String, String> mapInOut = PaidInOut.openDlg();
        if (null != mapInOut.get("amount")) {
            String amount = mapInOut.get("amount");
            BigDecimal amt = ZERO;
            try {
                amt = new BigDecimal(amount);
                amt = amt.movePointLeft(2);
            } catch (NumberFormatException e) {
                Debug.logError(e, module);
                return;
            }

            GenericValue internTx = pos.getSession().getDelegator().makeValue("PosTerminalInternTx");
            internTx.set("posTerminalLogId", trans.getTerminalLogId());
            internTx.set("paidAmount", amt);
            internTx.set("reasonComment", mapInOut.get("reasonComment"));
            internTx.set("reasonEnumId", mapInOut.get("reason"));
            try {
                internTx.create();
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                pos.showMessageDialog("dialog/error/exception", e.getMessage());
                return;
            }
            //save the TX Log
            trans.paidInOut(type);
            NavagationEvents.showPosScreen(pos);
        }
    }

    private static synchronized void printTotals(PosScreen pos, GenericValue state, boolean runBalance) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        if (!trans.isOpen()) {
            pos.showDialog("dialog/error/terminalclosed");
            return;
        }
        if (state == null) {
            state = trans.getTerminalState();
        }

        BigDecimal checkTotal = ZERO;
        BigDecimal cashTotal = ZERO;
        BigDecimal gcTotal = ZERO;
        BigDecimal ccTotal = ZERO;
        BigDecimal othTotal = ZERO;
        BigDecimal total = ZERO;

        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            Delegator delegator = pos.getSession().getDelegator();
            List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("originFacilityId", EntityOperator.EQUALS, trans.getFacilityId()),
                    EntityCondition.makeCondition("terminalId", EntityOperator.EQUALS, trans.getTerminalId()));
            EntityListIterator eli = null;

            try {
                eli = delegator.find("OrderHeaderAndPaymentPref", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, null);
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }

            Timestamp dayStart = state.getTimestamp("openedDate");
            Timestamp dayEnd = state.getTimestamp("closedDate");
            if (dayEnd == null) {
                dayEnd = UtilDateTime.nowTimestamp();
            }

            if (eli != null) {
                GenericValue ohpp;
                while (((ohpp = eli.next()) != null)) {
                    Timestamp orderDate = ohpp.getTimestamp("orderDate");
                    if (orderDate.after(dayStart) && orderDate.before(dayEnd)) {
                        String pmt = ohpp.getString("paymentMethodTypeId");
                        BigDecimal amt = ohpp.getBigDecimal("maxAmount");

                        if ("CASH".equals(pmt)) {
                            cashTotal = cashTotal.add(amt);
                        } else if ("PERSONAL_CHECK".equals(pmt)) {
                            checkTotal = checkTotal.add(amt);
                        } else if ("GIFT_CARD".equals(pmt)) {
                            gcTotal = gcTotal.add(amt);
                        } else if ("CREDIT_CARD".equals(pmt)) {
                            ccTotal = ccTotal.add(amt);
                        } else {
                            othTotal = othTotal.add(amt);
                        }
                        total = total.add(amt);
                    }
                }

                try {
                    eli.close();
                } catch (GenericEntityException e) {
                    Debug.logWarning(e, "Trouble closing ELI", module);
                    pos.showMessageDialog("dialog/error/exception", e.getMessage());
                }
            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                pos.showMessageDialog("dialog/error/exception", e2.getMessage());
            }
            pos.showMessageDialog("dialog/error/exception", e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                pos.showMessageDialog("dialog/error/exception", e.getMessage());
            }
        }

        Map<String, String> reportMap = FastMap.newInstance();
        String reportTemplate = Receipt.Totals_Receipt_File_Name;;//"totals.txt";

        // miscellaneous
        reportMap.put("term", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosTerm", Locale.getDefault()), 20, false, ' '));
        reportMap.put("draw", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosDraw", Locale.getDefault()), 20, false, ' '));
        reportMap.put("clerk", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosClerk", Locale.getDefault()), 20, false, ' '));
        reportMap.put("total_report", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosTotalReport", Locale.getDefault()), 20, false, ' '));

        // titles
        reportMap.put("cashTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCash", Locale.getDefault()), 20, false, ' '));
        reportMap.put("checkTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCheck", Locale.getDefault()), 20, false, ' '));
        reportMap.put("giftCardTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGiftCard", Locale.getDefault()), 20, false, ' '));
        reportMap.put("creditCardTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCreditCard", Locale.getDefault()), 20, false, ' '));
        reportMap.put("otherTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosOther", Locale.getDefault()), 20, false, ' '));
        reportMap.put("grossSalesTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGrossSales", Locale.getDefault()), 20, false, ' '));
        reportMap.put("grossSalesDiffTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGrossSalesDiff", Locale.getDefault()), 0, false, ' '));

        reportMap.put("+/-", UtilFormatOut.padString("+/-", 20, false, ' '));
        reportMap.put("spacer", UtilFormatOut.padString("", 20, false, ' '));

        // logged
        reportMap.put("cashTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashTotal), 8, false, ' '));
        reportMap.put("checkTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkTotal), 8, false, ' '));
        reportMap.put("ccTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccTotal), 8, false, ' '));
        reportMap.put("gcTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcTotal), 8, false, ' '));
        reportMap.put("otherTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(othTotal), 8, false, ' '));
        reportMap.put("grossTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(total), 8, false, ' '));

        if (runBalance) {
            // actuals
            BigDecimal cashEnd = state.getBigDecimal("actualEndingCash");
            BigDecimal checkEnd = state.getBigDecimal("actualEndingCheck");
            BigDecimal ccEnd = state.getBigDecimal("actualEndingCc");
            BigDecimal gcEnd = state.getBigDecimal("actualEndingGc");
            BigDecimal othEnd = state.getBigDecimal("actualEndingOther");
            BigDecimal grossEnd = cashEnd.add(checkEnd.add(ccEnd.add(gcEnd.add(othEnd))));

            reportMap.put("cashEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashEnd), 8, false, ' '));
            reportMap.put("checkEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkEnd), 8, false, ' '));
            reportMap.put("ccEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccEnd), 8, false, ' '));
            reportMap.put("gcEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcEnd), 8, false, ' '));
            reportMap.put("otherEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(othEnd), 8, false, ' '));
            reportMap.put("grossEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(grossEnd), 8, false, ' '));

            // diffs
            BigDecimal cashDiff = cashEnd.subtract(cashTotal);
            BigDecimal checkDiff = checkEnd.subtract(checkTotal);
            BigDecimal ccDiff = ccEnd.subtract(ccTotal);
            BigDecimal gcDiff = gcEnd.subtract(gcTotal);
            BigDecimal othDiff = othEnd.subtract(othTotal);
            BigDecimal grossDiff = cashDiff.add(checkDiff.add(ccDiff.add(gcDiff.add(othDiff))));

            reportMap.put("cashDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashDiff), 8, false, ' '));
            reportMap.put("checkDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkDiff), 8, false, ' '));
            reportMap.put("ccDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccDiff), 8, false, ' '));
            reportMap.put("gcDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcDiff), 8, false, ' '));
            reportMap.put("otherDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(othDiff), 8, false, ' '));
            reportMap.put("grossDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(grossDiff), 8, false, ' '));

            // set the report template
            reportTemplate = "balance.txt";
        }

        Receipt receipt = DeviceLoader.receipt;
        if (receipt.isEnabled()) {
            receipt.printReport(trans, reportTemplate, reportMap);
        }
    }

    public static synchronized void printTotals(PosScreen pos, GenericValue state, boolean runBalance, List<PosSalesData> closingData) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        if (!trans.isOpen()) {
            pos.showDialog("dialog/error/terminalclosed");
            return;
        }
        if (state == null) {
            state = trans.getTerminalState();
        }
//List<Map<String, BigDecimal>> closingData
        for (PosSalesData values : closingData) {
            BigDecimal checkTotal = ZERO;
            BigDecimal cashTotal = ZERO;
            BigDecimal gcTotal = ZERO;
            BigDecimal ccTotal = ZERO;
            BigDecimal othTotal = ZERO;

            BigDecimal total = ZERO;
            cashTotal = values.cash;
            ccTotal = values.eftpos;
            othTotal = values.cashout;
            total = values.sales;


            /*
             if ("PERSONAL_CHECK".equals(pmt)) {
             checkTotal = checkTotal.add(amt);
             } else if ("GIFT_CARD".equals(pmt)) {
             gcTotal = gcTotal.add(amt);
             } else if ("CREDIT_CARD".equals(pmt)) {
             ccTotal = ccTotal.add(amt);
             } else {
             othTotal = othTotal.add(amt);
             }
             total = total.add(amt);
             */
            Map<String, String> reportMap = FastMap.newInstance();
            String reportTemplate = Receipt.Totals_Receipt_File_Name;//"totals.txt";

            // miscellaneous
            reportMap.put("term", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosTerm", Locale.getDefault()), 20, false, ' '));
            reportMap.put("draw", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosDraw", Locale.getDefault()), 20, false, ' '));
            reportMap.put("clerk", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosClerk", Locale.getDefault()), 20, false, ' '));
            reportMap.put("total_report", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosTotalReport", Locale.getDefault()), 20, false, ' '));

            // titles
            reportMap.put("cashTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCash", Locale.getDefault()), 20, false, ' '));
            reportMap.put("checkTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCheck", Locale.getDefault()), 20, false, ' '));
            reportMap.put("giftCardTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGiftCard", Locale.getDefault()), 20, false, ' '));
            reportMap.put("creditCardTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCreditCard", Locale.getDefault()), 20, false, ' '));
            reportMap.put("otherTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosOther", Locale.getDefault()), 20, false, ' '));
            reportMap.put("grossSalesTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGrossSales", Locale.getDefault()), 20, false, ' '));
            reportMap.put("grossSalesDiffTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGrossSalesDiff", Locale.getDefault()), 0, false, ' '));

            reportMap.put("+/-", UtilFormatOut.padString("+/-", 20, false, ' '));
            reportMap.put("spacer", UtilFormatOut.padString("", 20, false, ' '));

            // logged
            reportMap.put("cashTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashTotal), 8, false, ' '));
            reportMap.put("checkTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkTotal), 8, false, ' '));
            reportMap.put("ccTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccTotal), 8, false, ' '));
            reportMap.put("gcTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcTotal), 8, false, ' '));
            reportMap.put("otherTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(othTotal), 8, false, ' '));
            reportMap.put("grossTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(total), 8, false, ' '));

            if (runBalance) {
                // actuals
                BigDecimal cashEnd = state.getBigDecimal("actualEndingCash");
                BigDecimal checkEnd = state.getBigDecimal("actualEndingCheck");
                BigDecimal ccEnd = state.getBigDecimal("actualEndingCc");
                BigDecimal gcEnd = state.getBigDecimal("actualEndingGc");
                BigDecimal othEnd = state.getBigDecimal("actualEndingOther");
                BigDecimal grossEnd = cashEnd.add(checkEnd.add(ccEnd.add(gcEnd.add(othEnd))));

                reportMap.put("cashEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashEnd), 8, false, ' '));
                reportMap.put("checkEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkEnd), 8, false, ' '));
                reportMap.put("ccEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccEnd), 8, false, ' '));
                reportMap.put("gcEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcEnd), 8, false, ' '));
                reportMap.put("otherEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(othEnd), 8, false, ' '));
                reportMap.put("grossEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(grossEnd), 8, false, ' '));

                // diffs
                BigDecimal cashDiff = cashEnd.subtract(cashTotal);
                BigDecimal checkDiff = checkEnd.subtract(checkTotal);
                BigDecimal ccDiff = ccEnd.subtract(ccTotal);
                BigDecimal gcDiff = gcEnd.subtract(gcTotal);
                BigDecimal othDiff = othEnd.subtract(othTotal);
                BigDecimal grossDiff = cashDiff.add(checkDiff.add(ccDiff.add(gcDiff.add(othDiff))));

                reportMap.put("cashDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashDiff), 8, false, ' '));
                reportMap.put("checkDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkDiff), 8, false, ' '));
                reportMap.put("ccDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccDiff), 8, false, ' '));
                reportMap.put("gcDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcDiff), 8, false, ' '));
                reportMap.put("otherDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(othDiff), 8, false, ' '));
                reportMap.put("grossDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(grossDiff), 8, false, ' '));

                // set the report template
                reportTemplate = "balance.txt";
            }

            Receipt receipt = DeviceLoader.receipt;
            if (receipt.isEnabled()) {
                receipt.printReport(trans, reportTemplate, reportMap);
            }
        }
    }

    public static synchronized void reports(PosScreen pos) {
        try {
            if (!mgrLoggedIn) {
                pos.showMessageDialog("dialog/error/mgrnotloggedin");
            } else {
       // ReportMainController findOrderListMain = new ReportMainController(ControllerOptions.getSession(), PosTransaction.getCurrentTx(XuiContainer.getSession()));
                //findOrderListMain.RunReportSelection(ControllerOptions.getDesktopPane());            
                ControllerOptions options = new ControllerOptions();
        //options.setSimpleScreenInterface(panelReport);
                //final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runControllerInternalFrame(options);
                final org.ofbiz.ordermax.report.reports.NewReportMainController frame1 = org.ofbiz.ordermax.report.reports.NewReportMainController.runControllerInternalFrame(options);
//            ReportMainScreen.loadScreen(PosTransaction.getCurrentTx(pos.getSession()));
//            pos.refresh();
//            if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductCatalogMainScreen.module) == false) {
//                org.ofbiz.ordermax.product.ProductCatalogMainScreen productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductCatalogMainScreen(null, pos.getSession());
//                productCatalogMainScreen.loadScreen(org.ofbiz.ordermax.product.ProductCatalogMainScreen.module);
//            }

            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
    }

    public static synchronized void products(final PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {

//            if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductMaintainController.module) == false) {
//                org.ofbiz.ordermax.product.ProductMaintainController productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductMaintainController(null, true, pos.getSession());
//                productCatalogMainScreen.loadScreenDialog();
//                productCatalogMainScreen.loadScreenTwoPanel(org.ofbiz.ordermax.product.ProductMaintainController.module);
//            }
            if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.product.ProductMaintainTreeController.module) == false) {
                ControllerOptions options = new ControllerOptions();
                org.ofbiz.ordermax.product.ProductMaintainTreeController.runController(options);// productCatalogMainScreen = new org.ofbiz.ordermax.product.ProductMaintainTreeController(options, pos.getSession());
//                productCatalogMainScreen.loadScreenDialog();
                //productCatalogMainScreen.loadScreenTwoPanel(org.ofbiz.ordermax.product.ProductMaintainTreeController.module);
            }
        }
    }

    protected static OnePanelNonSizableContainerDlg posSelectionDlg = null;
    static String productId = null;

    public static String showProductLookup(final PosScreen pos) {

        PosPanelMainScreen mainScreen = new PosPanelMainScreen(pos.getSession());
        mainScreen.loadScreen();
        productId = mainScreen.getProductId();
        Debug.logInfo("No context classloader productId: " + productId, module);
        return productId;
    }

    public static void productLookup(final PosScreen pos) {
        productId = ManagerEvents.showProductLookup(pos);

        if (productId != null) {
            Debug.logInfo("No context classloader productId: " + productId, module);
            MenuEvents.addFromDesktopItem(pos, productId);
        }
        pos.refresh();
    }

    private ClassLoader getClassLoader(PosScreen pos) {
        ClassLoader cl = pos.getClassLoader();
        if (cl == null) {
            try {
                cl = Thread.currentThread().getContextClassLoader();
            } catch (Throwable t) {
            }
            if (cl == null) {
                Debug.logInfo("No context classloader available; using class classloader", module);
                try {
                    cl = this.getClass().getClassLoader();
                } catch (Throwable t) {
                    Debug.logError(t, module);
                }
            }
        }

        return cl;
    }

    public static synchronized void priceList(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            if (BaseMainScreen.makeCurrentScreenVisible(PriceMaintainTreeController.module) == false) {
                ControllerOptions options = new ControllerOptions();
                PriceMaintainTreeController.runController(options); // priceMainScreen = new PriceMaintainTreeController(pos.getSession());
                //priceMainScreen.loadScreenTwoPanel(PriceMaintainTreeController.module);
            }
        }
    }

    public static synchronized void accounts(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.party.PartyMainScreen.module) == false) {
//                org.ofbiz.ordermax.party.PartyMainScreen partyMain = new org.ofbiz.ordermax.party.PartyMainScreen(null, null, pos.getSession());
//                partyMain.loadScreen(org.ofbiz.ordermax.party.PartyMainScreen.module);
                ControllerOptions options = new ControllerOptions();
                options.addRoleType("CUSTOMER");
                options.addPartyType("PARTY_GROUP");
                PartyTreeMaintainController.runController(options);// partyMain = new PartyTreeMaintainController("CUSTOMER", pos.getSession(), options);
                //partyMain.loadThreePanelFrame(PartyTreeMaintainController.module);
            }
        }
    }

    public static synchronized void findCustomer(PosScreen pos) {

        if (BaseMainScreen.makeCurrentScreenVisible(PartyTreeMaintainController.module) == false) {
            ControllerOptions options = new ControllerOptions();
            options.addRoleType("CUSTOMER");
            FindPartyListController findOrderListMain = FindPartyListController.runController(options); //new FindPartyListController(null, options, pos.getSession());
//            findOrderListMain.loadNonSizeableTwoPanelDialogScreen(FindPartyListController.module, null, null);
            Account acct = findOrderListMain.getSelectedAccount();
            if (acct != null) {
                pos.showMessageDialog(acct.getDisplayName());
                PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
                trans.setPartyId(acct.getParty().getpartyId());
            }
        }
    }

    public static synchronized void findCategory(PosScreen pos) {

        if (BaseMainScreen.makeCurrentScreenVisible(CategoryProductManagePanel.module) == false) {
            ControllerOptions options = new ControllerOptions();
            CategoryProductManagePanel categoryProductManagePanel = new CategoryProductManagePanel(new ControllerOptions());
            options.setSimpleScreenInterface(categoryProductManagePanel);
            SimpleFrameMainScreen.runController(options);// partyMain = new SimpleFrameMainScreen(categoryProductManagePanel, CategoryProductManagePanel.module, pos.getSession());
            //partyMain.loadNonSizeableTwoPanelDialogScreen(CategoryProductManagePanel.module, null, null);
        }
    }

    public static synchronized void getDeliveryDateAndComment(PosScreen pos) {

        if (BaseMainScreen.makeCurrentScreenVisible(SimpleFrameMainScreen.module) == false) {
            PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
            if (trans != null) {
                if (trans.cart.getShipAfterDate() != null) {
                    Debug.logError(" shipAfterDate: " + trans.cart.getShipAfterDate().toString(), module);
                } else {
                    trans.cart.setShipAfterDate(UtilDateTime.nowTimestamp());
                }
                Debug.logError(" shipAfterDate: ", module);
                if (trans.cart.getShipBeforeDate() != null) {
                    Debug.logError(" shipBeforeDate: " + trans.cart.getShipBeforeDate().toString(), module);
                } else {
                    trans.cart.setShipBeforeDate(UtilDateTime.nowTimestamp());
                }

                Debug.logError(" shipBeforeDate: ", module);
                if (trans.cart.getOrderDate() != null) {
                    Debug.logError(" orderDate: " + trans.cart.getOrderDate().toString(), module);
                } else {
                    trans.cart.setOrderDate(UtilDateTime.nowTimestamp());
                }

                Debug.logError(" orderDate: ", module);
            }

            DateSelectionDialog dlg = new DateSelectionDialog();
            SimpleFrameMainScreen findOrderListMain = new SimpleFrameMainScreen(dlg, "Date Selection", pos.getSession());
            findOrderListMain.loadNonSizeableTwoPanelDialogScreen(FindPartyListController.module, null, null);

            //public Timestamp getShipBeforeDate() {
        }
    }

    public static synchronized void purchaseOrder(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            if (BaseMainScreen.makeCurrentScreenVisible(PurchaseOrderController.module) == false) {
//                PurchaseOrderMainScreen purchaseOrder = new PurchaseOrderMainScreen(null, pos.getSession());
//                purchaseOrder.pos = pos;
//                purchaseOrder.loadScreen(PurchaseOrderMainScreen.module);
                try {
                    ControllerOptions controllerOptions = new ControllerOptions();
                    controllerOptions.addOrderType("PURCHASE_ORDER");
                    controllerOptions.addRoleType("SUPPLIER");
                    controllerOptions.addRoleTypeParent("SUPPLIER");
                    PurchaseOrderController.runController(controllerOptions);
                    //PurchaseOrderController purchaseOrder = new PurchaseOrderController(null, controllerOptions, pos.getSession());
                    //purchaseOrder.loadScreenTwoPanel(PurchaseOrderController.module);
                } catch (Exception ex) {
                    Debug.logError(ex, PurchaseOrderController.module);
                }
            }
        }
    }

    public static synchronized void salesOrder(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            if (BaseMainScreen.makeCurrentScreenVisible(SalesOrderController.module) == false) {
                try {
                    ControllerOptions controllerOptions = new ControllerOptions();
                    controllerOptions.addOrderType("SALES_ORDER");
                    controllerOptions.addRoleType("CUSTOMER");
                    controllerOptions.addRoleTypeParent("CUSTOMER");
//                    controllerOptions.put("orderId", orderId);

                    SalesOrderController.runController(controllerOptions);/* salesOrderController = new SalesOrderController(null, controllerOptions, pos.getSession());
                     salesOrderController.loadScreenTwoPanel(SalesOrderController.module);*/

                } catch (Exception ex) {
                    Debug.logError(ex, SalesOrderController.module);
                }
            }

        }
    }

    public static synchronized void payInvoice(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            if (BaseMainScreen.makeCurrentScreenVisible(org.ofbiz.ordermax.payment.PaymentController.module) == false) {
//                org.ofbiz.ordermax.payment.PaymentController paymentMainScreen = new org.ofbiz.ordermax.payment.PaymentController(null, true, pos.getSession());
//                paymentMainScreen.loadScreen(org.ofbiz.ordermax.payment.PaymentController.module);

                ControllerOptions options = new ControllerOptions();
                options.addPaymentType("VENDOR_PAYMENT");
                options.addRoleType("SUPPLIER");
                FindPaymentListController.runController(options);
                //FindPaymentListController findPaymentListController = new FindPaymentListController(options, pos.getSession());
                //findPaymentListController.loadScreenTwoPanel(FindPaymentListController.module);

            }

        }
    }

    public static synchronized void findSalesOrder(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
            OrderDisplayDialog dlg = new OrderDisplayDialog(null, true, pos, trans, true);
            BaseMainScreen.setSizeAndLocation(120, 50, dlg);
            dlg.setVisible(true);
        }
    }

    public static synchronized void findPurchaseOrder(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
            PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
            OrderDisplayDialog dlg = new OrderDisplayDialog(null, true, pos, trans, false);
            BaseMainScreen.setSizeAndLocation(120, 50, dlg);
            dlg.setVisible(true);
        }
    }

    public static synchronized void showPosDesigner(PosScreen pos) {

        if (!mgrLoggedIn) {
            pos.showMessageDialog("dialog/error/mgrnotloggedin");
        } else {
//            PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
//            OrderDisplayDialog dlg = new OrderDisplayDialog(null, true, pos, trans, false);
//            dlg.setVisible(true);
/*        final PosDesignPanel example = new PosDesignPanel();

             javax.swing.SwingUtilities.invokeLater(new Runnable() {
             public void run() {
             try {
             example.createAndShowGUI();
             } catch (ParserConfigurationException ex) {
             Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
             } catch (SAXException ex) {
             Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
             Logger.getLogger(PosDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
             }
             }
             });
             */

            if (BaseMainScreen.makeCurrentScreenVisible(PosPanelDesignerMainScreen.module) == false) {

                ControllerOptions controllerOptions = new ControllerOptions();
                PosPanelDesignerMainScreen purchaseOrder = PosPanelDesignerMainScreen.runController(controllerOptions);;
                // new PosPanelDesignerMainScreen(pos.getSession());
                //purchaseOrder.pos = pos;
                //purchaseOrder.loadThreePanelFrame(PosPanelDesignerMainScreen.module);

//                purchaseOrder.loadScreen(PosPanelDesignerMainScreen.module);
            }
            /*             
             JTextArea textArea = new JTextArea(10, 50);

             textArea.setText("All");
             textArea.setCaretPosition(0);

             int result = JOptionPane.showOptionDialog(null, new JScrollPane(textArea), "Please Enter Entity Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
             if (result == JOptionPane.OK_OPTION) {
             try {
             OrderMaxViewEntity.addToIgnoreFieldList();
             Delegator delegator = XuiContainer.getSession().getDelegator();
             String entName = delegator.getEntityGroupName("Party");
             Map<String, ModelEntity> map = delegator.getModelEntityMapByGroup(entName);
             if (textArea.getText().trim().contains("All")) {
             Debug.logInfo("All Entity Start " + entName, module);
             for (Map.Entry<String, ModelEntity> entryDept : map.entrySet()) {

             try {
             if (entryDept.getKey().equalsIgnoreCase("ProductCategoryMemberAndPrice") == false
             && entryDept.getKey().equalsIgnoreCase("OrderHeaderItemAndRoles") == false
             && entryDept.getKey().equalsIgnoreCase("OrderItemAndProductContentInfo") == false
             && entryDept.getKey().equalsIgnoreCase("OrderRoleAndProductContentInfo") == false) {
             List listVal = delegator.findList(entryDept.getKey(), null, null, null, null, false);

             if (listVal != null && listVal.size() > 0) {
             GenericValue entVal = delegator.findList(entryDept.getKey(), null, null, null, null, false).get(0);
             Debug.logInfo(entryDept.getKey() + " " + entryDept.getValue().getEntityName(), module);
             //                            GenericEntity ent = GenericEntity.createGenericEntity(entryDept.getValue());
             OrderMaxViewEntity.outputFieldMap(entVal, entryDept.getKey());
             OrderMaxViewEntity.writeJavaDisplayClass(entVal, entryDept.getKey());
             }
             }
             } catch (Exception e) {
             Debug.logError(e, module);
             }
             }
             Debug.logInfo("All Entity End: " + entName, module);
             } else {
             if (map.containsKey(textArea.getText())) {
             //                        GenericEntity ent = GenericEntity.createGenericEntity(map.get(textArea.getText()));
             //                        OrderMaxViewEntity.outputFieldMap(ent, textArea.getText());
             //                        OrderMaxViewEntity.writeJavaDisplayClass(ent, textArea.getText());

             OrderMaxViewEntity.outputFieldMap(delegator.findList(textArea.getText(), null, null, null, null, false).get(0), textArea.getText());
             OrderMaxViewEntity.writeJavaDisplayClass(delegator.findList(textArea.getText(), null, null, null, null, false).get(0), textArea.getText());
             }
             }
             OrderMaxViewEntity.writeColumnIds();
             } catch (GenericEntityException ex) {
             Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
             }

             //            outputFieldMap(session.getDelegator().findList("PartyGroup", null, null, null, null, false).get(0), "PartyGroup");
             }
             */
        }

    }

    protected static FilteredJList variantProductList = null;

    public static synchronized void productSearch(PosScreen pos) {

        if (variantProductList == null) {
            //final ProductDataLoaderInterace productListArray = BaseHelper.getVariantProductArray(ControllerOptions.getSession());
            List<TreeNode> productList = ProductSingleton.getAllVariantProductsTreeNodes();//.getAllProductsTreeNodes();//getAllProducts();
            // populate list

            variantProductList = new FilteredJList();
            for (int i = 0; i < productList.size(); i++) {
                Key key = productList.get(i);
                Debug.logInfo("key: " + key.toString(), module);
                //            list.addItem(listItems[i].toUpperCase());
                variantProductList.addItem(key);
            }
        }
        // add to gui
        JScrollPane pane = new JScrollPane(variantProductList, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ProductSearchDialog dialog = new ProductSearchDialog(new javax.swing.JFrame(), true);
        javax.swing.JPanel panel = dialog.panelDetail;
        panel.setLayout(new BorderLayout());
        panel.add(pane, BorderLayout.CENTER);

        variantProductList.getFilterField().setPreferredSize(new java.awt.Dimension(100, 40));
        variantProductList.getFilterField().setText("");
        panel.add(variantProductList.getFilterField(), BorderLayout.NORTH);
        panel.add(variantProductList.getKeyboardPanel(), BorderLayout.SOUTH);

        dialog.pack();
        //        frame.setVisible(true);
        dialog.setLocationRelativeTo(null);            
        dialog.setVisible(true);
        if (dialog.getReturnStatus() == ProductSearchDialog.RET_OK) {
            Key key = (Key) variantProductList.getSelectedValue();
            if (key != null) {
                MenuEvents.addFromDesktopItem(pos, key._id);
            }
        }

        /*
         String dir = "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\";
         String masterReportFileName = dir + "jasper_report_template.jrxml";
         String subReportFileName = dir + "AddressReport.jrxml";
         String destFileName = dir + "jasper_report_template.JRprint";
         DataBeanList dataBeanList = new DataBeanList();
         ArrayList<InvoiceReportItemEntity> dataList = dataBeanList.getDataBeanList(pos.getSession(), (String) pos.getSession().getAttribute("facilityId"),new  java.sql.Timestamp(2013,2,1,0,0,0,0), new java.sql.Timestamp(2014,2,23,0,0,0,0));
         JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);

         try {

         JasperReport jasperMasterReport = JasperCompileManager.compileReport(masterReportFileName);
         JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportFileName);

         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("subreportParameter", jasperSubReport);

         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);

         JasperViewer.viewReport(jasperPrint);

         } catch (JRException e) {

         e.printStackTrace();
         }
         System.out.println("Done filling!!! ...");
         */
        /*        CategoryProductManagePanel panel = new CategoryProductManagePanel(pos.getSession());
         JFrame f = new JFrame();        
         f.setLayout(new BorderLayout());
         f.add(BorderLayout.CENTER,panel);
         f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         f.setSize(1000, 700);
         //        f.setLocationRelativeTo(null);
         //        f.textField = panel.getPartyTextField();
         f.setVisible(true);
         f.setAlwaysOnTop(true);
         f.toFront();
         //        f.requestFocus();
         f.setAlwaysOnTop(false);
         
         try {

         Delegator delegator = XuiContainer.getSession().getDelegator();

         List<GenericValue> genValList = PosProductHelper.getGenericValueLists("ProductPriceRule", delegator);

         org.ofbiz.ordermax.generic.GenericValueDetailTableDialog dlg = new org.ofbiz.ordermax.generic.GenericValueDetailTableDialog(null, false, XuiContainer.getSession(), ProductPriceRule.ColumnNameId);

         dlg.setupOrderTableList(genValList);

         GenericValue val = genValList.get(0);

         GenericValuePanelInterfaceOrderMax panel = new PriceRulePanelForm();

         GenericValueObjectInterface uiObj = panel.createUIObject(val);

         panel.changeUIObject(uiObj);

         panel.setUIFields();

         dlg.setChildPanelInterface(panel);
         JTabbedPane tabed = new JTabbedPane();

         tabed.add(panel.getContainerPanel());
         tabed.setTitleAt(0, "Price Rules");
         PriceRuleConditionPanelForm conditionPanel = new PriceRuleConditionPanelForm();
         tabed.add(conditionPanel);
         tabed.setTitleAt(1, "Conditions");

         PriceRuleConditionDetailPanel detail = new PriceRuleConditionDetailPanel();
         conditionPanel.setLayout(new BorderLayout());
         conditionPanel.add(detail, BorderLayout.CENTER);

         dlg.getParentPanel().setLayout(new BorderLayout());
         dlg.getParentPanel().add(tabed, BorderLayout.CENTER);
         //            panel.getContainerPanel()
         //            OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());

         //            dlg.setLocationRelativeTo(null);
         dlg.pack();

         dlg.setVisible(true);

         } catch (ParseException ex) {

         Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

         }
         */
    }
}
