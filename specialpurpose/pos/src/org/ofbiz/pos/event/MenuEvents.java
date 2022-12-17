/**
 * *****************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * *****************************************************************************
 */
package org.ofbiz.pos.event;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.order.shoppingcart.CartItemModifyException;
import org.ofbiz.order.shoppingcart.ItemNotFoundException;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.config.ButtonEventConfig;
import org.ofbiz.pos.component.Input;
import org.ofbiz.pos.component.Journal;
import org.ofbiz.pos.screen.SelectProduct;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.shoppingcart.ShoppingCartItem;
import org.ofbiz.ordermax.base.OnePanelNonSizableContainerDlg;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.product.ProductSelectDialog;
import static org.ofbiz.pos.PosTransaction.module;
import org.ofbiz.pos.screen.ConfigureItem;
import org.ofbiz.product.config.ProductConfigWrapper;

public class MenuEvents {

    public static final String module = MenuEvents.class.getName();

    // scales and rounding modes for BigDecimal math
    public static final int scale = UtilNumber.getBigDecimalScale("order.decimals");
    public static final int rounding = UtilNumber.getBigDecimalRoundingMode("order.rounding");
    public static final BigDecimal ZERO = (BigDecimal.ZERO).setScale(scale, rounding);

    // extended number events
    public static synchronized void triggerClear(PosScreen pos) {
        // clear the pieces
        String[] totalFunc = pos.getInput().getFunction("TOTAL");
        String[] paidFunc = pos.getInput().getFunction("PAID");
        if (paidFunc != null) {
            pos.getInput().clear();
            pos.showPage("pospanel");
        } else {
            if (UtilValidate.isEmpty(pos.getInput().value())) {
                pos.getInput().clear();
            }
            if (totalFunc != null) {
                pos.getInput().setFunction("TOTAL", totalFunc[1]);
            }
        }

        // refresh the current screen
        pos.refresh();

        // clear out the manual locks
        if (!pos.isLocked()) {
            pos.getInput().setLock(false);
            pos.getButtons().setLock(false);
        } else {
            // just re-call set lock
            pos.setLock(true);
        }
    }

    public static synchronized void triggerQty(PosScreen pos) {
        pos.getInput().setFunction("QTY");
    }

    public static synchronized void triggerEnter(PosScreen pos, AWTEvent event) {
        // enter key maps to various different events; depending on the function
        Input input = pos.getInput();
        Debug.logError("String value =" + input.value(), module);

        String[] lastFunc = input.getLastFunction();
        if (lastFunc != null) {
            if ("MGRLOGIN".equals(lastFunc[0])) {
                SecurityEvents.mgrLogin(pos);
            } else if ("LOGIN".equals(lastFunc[0])) {
                SecurityEvents.login(pos);
            } else if ("OPEN".equals(lastFunc[0])) {
                ManagerEvents.openTerminal(pos);
            } else if ("CLOSE".equals(lastFunc[0])) {
                ManagerEvents.closeTerminal(pos);
            } else if ("PAID_IN".equals(lastFunc[0])) {
                ManagerEvents.paidOutAndIn(pos, "IN");
            } else if ("PAID_OUT".equals(lastFunc[0])) {
                ManagerEvents.paidOutAndIn(pos, "OUT");
            } else if ("VOID".equals(lastFunc[0])) {
                ManagerEvents.voidOrder(pos);
            } else if ("REFNUM".equals(lastFunc[0])) {
                PaymentEvents.setRefNum(pos);
            } else if ("CREDIT".equals(lastFunc[0])) {
                PaymentEvents.payCredit(pos);
            } else if ("CREDITEXP".equals(lastFunc[0])) {
                PaymentEvents.payCredit(pos);
            } else if ("SECURITYCODE".equals(lastFunc[0])) {
                PaymentEvents.payCredit(pos);
            } else if ("POSTALCODE".equals(lastFunc[0])) {
                PaymentEvents.payCredit(pos);
            } else if ("CHECK".equals(lastFunc[0])) {
                PaymentEvents.payCheck(pos);
            } else if ("GIFTCARD".equals(lastFunc[0])) {
                PaymentEvents.payGiftCard(pos);
            } else if ("MSRINFO".equals(lastFunc[0])) {
                if (input.isFunctionSet("CREDIT")) {
                    PaymentEvents.payCredit(pos);
                } else if (input.isFunctionSet("GIFTCARD")) {
                    PaymentEvents.payGiftCard(pos);
                }
            } else if ("SKU".equals(lastFunc[0])) {
                MenuEvents.addItem(pos, event);
            } else if ("PROMOCODE".equals(lastFunc[0])) {
                PromoEvents.addPromoCode(pos);
            }
        } else if (input.value().length() > 0) {
            if (event != null) {
                Debug.logError("String value =" + input.value() + " even: " + event.toString(), module);
            }
            MenuEvents.addItem(pos, event);
        }
    }

    public static synchronized void addItem(PosScreen pos, AWTEvent event) {

        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Input input = pos.getInput();
        String[] func = input.getFunction("QTY");
        String value = input.value();
        String value2 = value.toLowerCase();
        Debug.logInfo("menuSku: addItem: " + value, module);
        BigDecimal priceInput = BigDecimal.ZERO;
        BigDecimal qtyInput = BigDecimal.ONE;
        boolean qtyEntered = false;
        boolean priceEntered = false;

        // no value; just return
        if (event != null) {//&& UtilValidate.isEmpty(value)) {
            if (UtilValidate.isEmpty(value)) {
                String buttonName = ButtonEventConfig.getButtonName(event);
                if (UtilValidate.isNotEmpty(buttonName)) {
                    if (buttonName.startsWith("SKU.")) {
                        value = buttonName.substring(4);
                    }
                }
                if (UtilValidate.isEmpty(value)) {
                    return;
                }
                if (value.equals(PosTransaction.GroceryItem) || value.equals(PosTransaction.ScalItem) ) {
                    if (UtilValidate.isEmpty(value2)) {
                        pos.showMessageDialog("Please enter product price.");
                        return;
                    }
                }
            } else {
                String buttonName = ButtonEventConfig.getButtonName(event);
                if (UtilValidate.isNotEmpty(buttonName)) {
                    if (buttonName.startsWith("SKU.")) {
                        value = buttonName.substring(4);
                        int multIndex = value2.indexOf("x");
                        if (multIndex != -1) {
                            try {
                                String val1 = value2.substring(0, multIndex);
                                Debug.logInfo("val1: " + val1, module);
                                try {
                                    qtyInput = new BigDecimal(val1);
                                    qtyEntered = true;
                                } catch (NumberFormatException e) {
                                    Debug.logError(e, module);
                                }

                                String val2 = value2.substring(multIndex + 1);
                                Debug.logInfo("val2: " + val2, module);
                                try {
                                    priceInput = new BigDecimal(val2).movePointLeft(2);
                                    priceEntered = true;
                                } catch (NumberFormatException e) {
                                }

                            } catch (NumberFormatException e) {
                                Debug.logError(e, module);
                            }
                        } else {
                            try {
                                priceInput = new BigDecimal(value2).movePointLeft(2);
                                priceEntered = true;
                            } catch (NumberFormatException e) {
                                Debug.logError(e, module);
                            }
                        }
                    }
                }
            }
        }

        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
        } else {

            // check for quantity
            BigDecimal quantity = qtyInput;//BigDecimal.ONE;
            if (func != null && "QTY".equals(func[0])) {
                try {
                    quantity = new BigDecimal(func[1]);
                } catch (NumberFormatException e) {
                    quantity = BigDecimal.ONE;
                }
            }

            // locate the product ID
            String productId = null;
            try {
                List<GenericValue> items = trans.lookupItem(value);
                if (items != null) {
                    ListIterator<GenericValue> it = items.listIterator();
                    if (it.hasNext()) {
                        GenericValue product = it.next();
                        productId = product.getString("productId");
                        Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
                        productsMap.put(product.getString("productId"), product.get("internalName"));
                        while (it.hasNext()) {
                            product = it.next();
                            if (!productId.equals(product.getString("productId"))) {
                                productsMap.put(product.getString("productId"), product.get("internalName"));
                            }
                        }
                        if (productsMap.size() > 1 && ButtonEventConfig.getButtonName(event).equals("menuSku")) {
                            SelectProduct SelectProduct = new SelectProduct(productsMap, trans, pos);
                            productId = SelectProduct.openDlg();
                        }
                    }
                }
            } catch (GeneralException e) {
                Debug.logError(e, module);
                pos.showMessageDialog("dialog/error/producterror");
            }

            // Check for Aggregated item
            boolean aggregatedItem = false;
            ProductConfigWrapper pcw = null;
            try {
                aggregatedItem = trans.isAggregatedItem(productId);
                if (aggregatedItem) {
                    pcw = trans.getProductConfigWrapper(productId);
                    pcw.setDefaultConfig();
                    ConfigureItem configureItem = new ConfigureItem(pcw, trans, pos);
                    pcw = configureItem.openDlg();
                    configureItem = null;
                }
            } catch (Exception e) {
                Debug.logError(e, module);
                pos.showMessageDialog("dialog/error/producterror");
            }

            // add the item to the cart; report any errors to the user
            if (productId != null) {
                try {
                    if (!aggregatedItem) {
                        ShoppingCartItem shoppingCartItem = trans.addItem(productId, quantity);
                        if (priceEntered) {
                            //entered price includes gst
                            if (shoppingCartItem != null) {
                                if (shoppingCartItem.taxApplies()) {
                                    priceInput = priceInput.divide(new BigDecimal(1.1));
                                }
                            }
                            trans.modifyPrice(productId, priceInput);
                        }
                    } else {
                        trans.addItem(productId, pcw);
                    }
                } catch (CartItemModifyException e) {
                    Debug.logError(e, module);
                    pos.showMessageDialog("dialog/error/producterror", e.getMessage());
                } catch (ItemNotFoundException e) {
                    pos.showMessageDialog("dialog/error/productnotfound");
                }
            } else {
                pos.showMessageDialog("dialog/error/productnotfound");
            }
        }

        // clear the qty flag
        input.clearFunction("QTY");

        Debug.logInfo("menuSku: addItem: end", module);

        // re-calc tax
        trans.calcTax();

        // refresh the others
        pos.refresh();
    }

    /*
     public static synchronized void addItem(final PosScreen pos, final AWTEvent event) {
     final PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
     final Input input = pos.getInput();
     String[] func = input.getFunction("QTY");
     String value = input.value();

     // no value; just return
     if (event != null && UtilValidate.isEmpty(value)) {
     String buttonName = ButtonEventConfig.getButtonName(event);
     if (UtilValidate.isNotEmpty(buttonName)) {
     if (buttonName.startsWith("SKU.")) {
     value = buttonName.substring(4);
     }
     }
     if (UtilValidate.isEmpty(value)) {
     return;
     }
     }

     if (!trans.isOpen()) {
     pos.showDialog("dialog/error/terminalclosed");
     } else {

     // check for quantity
     BigDecimal quantity = BigDecimal.ONE;
     if (func != null && "QTY".equals(func[0])) {
     try {
     quantity = new BigDecimal(func[1]);
     } catch (NumberFormatException e) {
     quantity = BigDecimal.ONE;
     }
     }
     boolean addItem = true;

     // locate the product ID
     String productId = null;
     try {
                            
     List<GenericValue> items = trans.lookupItem(value);
     Debug.logInfo("Name value : " + value + " items size: " + items.size(), module);
     if (items != null) {
     ListIterator<GenericValue> it = items.listIterator();
     if (it.hasNext()) {
     GenericValue product = it.next();
     productId = product.getString("productId");
     Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
     productsMap.put(product.getString("productId"), product.get("internalName"));
     final List<Product> productList = new ArrayList<Product>();
     while (it.hasNext()) {
     product = it.next();
     if (!productId.equals(product.getString("productId"))) {
     productsMap.put(product.getString("productId"), product.get("internalName"));
     productList.add(new Product(product));
     Debug.logInfo("Multi is opened: " + product.getString("productId"), "module");
     }

     }

     if (productList.size() > 1 && ButtonEventConfig.getButtonName(event).equals("menuSku")) {
     Debug.logInfo("Brand OnePanelNonSizableContainerDlg is opened: before ", module);
     //                           
     if (!SwingUtilities.isEventDispatchThread()) {
     addItem = false;
     final BigDecimal quantityThread = quantity;
     SwingUtilities.invokeLater(new Runnable() {
     public void run() {

     ClassLoader cl = null;
     try {
     cl = pos.getSession().getClassLoader();

     if (cl == null) {
     try {
     cl = Thread.currentThread().getContextClassLoader();
     } catch (Throwable t) {
     }
     if (cl == null) {
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

     Thread.currentThread().setContextClassLoader(cl);
     final OnePanelNonSizableContainerDlg posSelectionDlg = new OnePanelNonSizableContainerDlg(new javax.swing.JFrame(), true);
     final ProductSelectDialog productdlg = new ProductSelectDialog();
     productdlg.listSelectionModel.dataListModel.addAll(productList);
     productdlg.setProductIndex(0);
     posSelectionDlg.getPanelDetail().setLayout(new BorderLayout());
     posSelectionDlg.getPanelDetail().add(BorderLayout.CENTER, productdlg);
     posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     posSelectionDlg.setSize(800, 500);
     posSelectionDlg.setLocationRelativeTo(null);
     Debug.logInfo("Brand OnePanelNonSizableContainerDlg is opened ", "module");

     productdlg.btnCancel.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
     posSelectionDlg.cancelButtonPressed();
     Debug.logInfo("Brand OnePanelNonSizableContainerDlg is cancelButtonPressed  ", module);
     }
     });

     productdlg.btnSelect.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
     posSelectionDlg.okButtonPressed();
     Debug.logInfo("Brand OnePanelNonSizableContainerDlg is okButtonPressed  ", module);
     }
     });
     MouseListener mouseListener = new MouseAdapter() {
     public void mouseClicked(MouseEvent e) {
     if (e.getClickCount() == 2) {
     posSelectionDlg.okButtonPressed();   
     }
     }
     };
                                        
     productdlg.listSelectionModel.jlistBox.addMouseListener(mouseListener);
     posSelectionDlg.setVisible(true);
     posSelectionDlg.toFront();
     posSelectionDlg.requestFocus();
     if (posSelectionDlg.getReturnStatus() == OnePanelNonSizableContainerDlg.RET_OK) {
     Product prod = productdlg.listSelectionModel.listModelSelection.getSelection();
     if (prod != null) {
     addItem(trans, pos, event, prod.getproductId(), quantityThread);
     // clear the qty flag
     input.clearFunction("QTY");

     // re-calc tax
     trans.calcTax();
     }
     // refresh the others
     pos.refresh();
     }

     }
     });
     //
     } else {
     final ProductSelectDialog productdlg = new ProductSelectDialog();
     productdlg.listSelectionModel.dataListModel.addAll(productList);

     OrderMaxOptionPane.showConfirmDialog(null,
     productdlg,
     "JOptionPane Example : ",
     JOptionPane.OK_CANCEL_OPTION,
     JOptionPane.PLAIN_MESSAGE);

     }
     Debug.logInfo("Brand OnePanelNonSizableContainerDlg is opened: after visible ", module);

     }
     }
     }
     } catch (GeneralException e) {
     Debug.logError(e, module);
     pos.showMessageDialog("dialog/error/producterror", e.getMessage());
     }

     if (addItem) {
     addItem(trans, pos, event, productId, quantity);
     }
     }

     // clear the qty flag
     input.clearFunction("QTY");

     // re-calc tax
     trans.calcTax();

     // refresh the others
     pos.refresh();
     }
     */
    protected ClassLoader getClassLoader(XuiSession session) {

        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();

            if (cl == null) {
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                }
                if (cl == null) {
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

    public static synchronized void addItem(PosTransaction trans, PosScreen pos, AWTEvent event, String productId, BigDecimal quantity) {
// Check for Aggregated item
        boolean aggregatedItem = false;
        ProductConfigWrapper pcw = null;
        try {
            aggregatedItem = trans.isAggregatedItem(productId);
            if (aggregatedItem) {
                pcw = trans.getProductConfigWrapper(productId);
                pcw.setDefaultConfig();
                ConfigureItem configureItem = new ConfigureItem(pcw, trans, pos);
                pcw = configureItem.openDlg();
                configureItem = null;
            }
        } catch (Exception e) {
            Debug.logError(e, module);
            pos.showMessageDialog("dialog/error/producterror", e.getMessage());
        }

        // add the item to the cart; report any errors to the user
        if (productId != null) {
            try {
                if (!aggregatedItem) {
                    trans.addItem(productId, quantity);
                } else {
                    trans.addItem(productId, pcw);
                }
            } catch (CartItemModifyException e) {
//                    Debug.logError(e, module);
                pos.showMessageDialog("dialog/error/producterror", e.getMessage());
            } catch (ItemNotFoundException e) {
                pos.showMessageDialog("dialog/error/productnotfound", e.getMessage());
            }
        } else {
            pos.showDialog("dialog/error/productnotfound");
        }
    }
    /* suresh
     public static synchronized void addItem(PosScreen pos, AWTEvent event) {

     PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
     Input input = pos.getInput();
     String[] func = input.getFunction("QTY");
     String value = input.value();
     String value2 = value.toLowerCase();
     Debug.logInfo("menuSku: addItem: " + value, module);
     BigDecimal priceInput = BigDecimal.ZERO;
     BigDecimal qtyInput = BigDecimal.ONE;
     boolean qtyEntered = false;
     boolean priceEntered = false;

     // no value; just return
     if (event != null) {//&& UtilValidate.isEmpty(value)) {
     if (UtilValidate.isEmpty(value)) {
     String buttonName = ButtonEventConfig.getButtonName(event);
     if (UtilValidate.isNotEmpty(buttonName)) {
     if (buttonName.startsWith("SKU.")) {
     value = buttonName.substring(4);
     }
     }
     if (UtilValidate.isEmpty(value)) {
     return;
     }
     if (value.equals(PosTransaction.GroceryItem)) {
     if (UtilValidate.isEmpty(value2)) {
     pos.showMessageDialog("Please enter product price.");
     return;
     }
     }
     } else {
     String buttonName = ButtonEventConfig.getButtonName(event);
     if (UtilValidate.isNotEmpty(buttonName)) {
     if (buttonName.startsWith("SKU.")) {
     value = buttonName.substring(4);
     int multIndex = value2.indexOf("x");
     if (multIndex != -1) {
     try {
     String val1 = value2.substring(0, multIndex);
     Debug.logInfo("val1: " + val1, module);
     try {
     qtyInput = new BigDecimal(val1);
     qtyEntered = true;
     } catch (NumberFormatException e) {
     Debug.logError(e, module);
     }

     String val2 = value2.substring(multIndex + 1);
     Debug.logInfo("val2: " + val2, module);
     try {
     priceInput = new BigDecimal(val2).movePointLeft(2);
     priceEntered = true;
     } catch (NumberFormatException e) {
     }

     } catch (NumberFormatException e) {
     Debug.logError(e, module);
     }
     } else {
     try {
     priceInput = new BigDecimal(value2).movePointLeft(2);
     priceEntered = true;
     } catch (NumberFormatException e) {
     Debug.logError(e, module);
     }
     }
     }
     }
     }
     }

     if (!trans.isOpen()) {
     pos.showMessageDialog("dialog/error/terminalclosed");
     } else {

     // check for quantity
     BigDecimal quantity = qtyInput;//BigDecimal.ONE;
     if (func != null && "QTY".equals(func[0])) {
     try {
     quantity = new BigDecimal(func[1]);
     } catch (NumberFormatException e) {
     quantity = BigDecimal.ONE;
     }
     }

     // locate the product ID
     String productId = null;
     try {
     List<GenericValue> items = trans.lookupItem(value);
     if (items != null) {
     ListIterator<GenericValue> it = items.listIterator();
     if (it.hasNext()) {
     GenericValue product = it.next();
     productId = product.getString("productId");
     Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
     productsMap.put(product.getString("productId"), product.get("internalName"));
     while (it.hasNext()) {
     product = it.next();
     if (!productId.equals(product.getString("productId"))) {
     productsMap.put(product.getString("productId"), product.get("internalName"));
     }
     }
     if (productsMap.size() > 1 && ButtonEventConfig.getButtonName(event).equals("menuSku")) {
     SelectProduct SelectProduct = new SelectProduct(productsMap, trans, pos);
     productId = SelectProduct.openDlg();
     }
     }
     }
     } catch (GeneralException e) {
     Debug.logError(e, module);
     pos.showMessageDialog("dialog/error/producterror");
     }

     // Check for Aggregated item
     boolean aggregatedItem = false;
     ProductConfigWrapper pcw = null;
     try {
     aggregatedItem = trans.isAggregatedItem(productId);
     if (aggregatedItem) {
     pcw = trans.getProductConfigWrapper(productId);
     pcw.setDefaultConfig();
     ConfigureItem configureItem = new ConfigureItem(pcw, trans, pos);
     pcw = configureItem.openDlg();
     configureItem = null;
     }
     } catch (Exception e) {
     Debug.logError(e, module);
     pos.showMessageDialog("dialog/error/producterror");
     }

     // add the item to the cart; report any errors to the user
     if (productId != null) {
     try {
     if (!aggregatedItem) {
     trans.addItem(productId, quantity);
     if (priceEntered) {
     trans.modifyPrice(productId, priceInput);
     }
     } else {
     trans.addItem(productId, pcw);
     }
     } catch (CartItemModifyException e) {
     Debug.logError(e, module);
     pos.showMessageDialog("dialog/error/producterror", e.getMessage());
     } catch (ItemNotFoundException e) {
     pos.showMessageDialog("dialog/error/productnotfound");
     }
     } else {
     pos.showMessageDialog("dialog/error/productnotfound");
     }
     }

     // clear the qty flag
     input.clearFunction(
     "QTY");

     Debug.logInfo(
     "menuSku: addItem: end", module);

     // re-calc tax
     trans.calcTax();

     // refresh the others
     pos.refresh();
     }
     */
    /*
     public static synchronized void addItem(PosScreen pos, AWTEvent event) {
     PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
     Input input = pos.getInput();
     String[] func = input.getFunction("QTY");
     String value = input.value();
     Debug.logInfo("menuSku: addItem: " + value, module );
     // no value; just return
     if (event != null && UtilValidate.isEmpty(value)) {
     String buttonName = ButtonEventConfig.getButtonName(event);
     if (UtilValidate.isNotEmpty(buttonName)) {
     if (buttonName.startsWith("SKU.")) {
     value = buttonName.substring(4);
     }
     }
     if (UtilValidate.isEmpty(value)) {
     return;
     }
     }

     if (!trans.isOpen()) {
     pos.showMessageDialog("dialog/error/terminalclosed");
     } else {

     // check for quantity
     BigDecimal quantity = BigDecimal.ONE;
     if (func != null && "QTY".equals(func[0])) {
     try {
     quantity = new BigDecimal(func[1]);
     } catch (NumberFormatException e) {
     quantity = BigDecimal.ONE;
     }
     }

     // locate the product ID
     String productId = null;
     try {
     List<GenericValue> items = trans.lookupItem(value);
     if (items != null) {
     ListIterator<GenericValue> it = items.listIterator();
     if (it.hasNext()) {
     GenericValue product = it.next();
     productId = product.getString("productId");
     Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
     productsMap.put(product.getString("productId"), product.get("internalName"));
     while (it.hasNext()) {
     product = it.next();
     if (!productId.equals(product.getString("productId"))) {
     productsMap.put(product.getString("productId"), product.get("internalName"));
     }
     }
     if (productsMap.size() > 1 && ButtonEventConfig.getButtonName(event).equals("menuSku")) {
     SelectProduct SelectProduct = new SelectProduct(productsMap, trans, pos);
     productId = SelectProduct.openDlg();
     }
     }
     }
     } catch (GeneralException e) {
     Debug.logError(e, module);
     pos.showMessageDialog("dialog/error/producterror");
     }

     // Check for Aggregated item
     boolean aggregatedItem = false;
     ProductConfigWrapper pcw = null;
     try {
     aggregatedItem = trans.isAggregatedItem(productId);
     if (aggregatedItem) {
     pcw = trans.getProductConfigWrapper(productId);
     pcw.setDefaultConfig();
     ConfigureItem configureItem = new ConfigureItem(pcw, trans, pos);
     pcw = configureItem.openDlg();
     configureItem = null;
     }
     } catch (Exception e) {
     Debug.logError(e, module);
     pos.showMessageDialog("dialog/error/producterror");
     }

     // add the item to the cart; report any errors to the user
     if (productId != null) {
     try {
     if (!aggregatedItem) {
     trans.addItem(productId, quantity);
     } else {
     trans.addItem(productId, pcw);
     }
     } catch (CartItemModifyException e) {
     Debug.logError(e, module);
     pos.showMessageDialog("dialog/error/producterror", e.getMessage());
     } catch (ItemNotFoundException e) {
     pos.showMessageDialog("dialog/error/productnotfound");
     }
     } else {
     pos.showMessageDialog("dialog/error/productnotfound");
     }
     }

     // clear the qty flag
     input.clearFunction("QTY");
        
     Debug.logInfo("menuSku: addItem: end", module );
        
     // re-calc tax
     trans.calcTax();

     // refresh the others
     pos.refresh();
     }
     */

    public static void addFromDesktopItem(PosScreen pos, String value) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Input input = pos.getInput();
        String[] func = input.getFunction("QTY");
//        String value = input.value();
        Debug.logInfo("menuSku: addItem: " + value, module);

        /*        // no value; just return
         if (event != null && UtilValidate.isEmpty(value)) {
         String buttonName = ButtonEventConfig.getButtonName(event);
         if (UtilValidate.isNotEmpty(buttonName)) {
         if (buttonName.startsWith("SKU.")) {
         value = buttonName.substring(4);
         }
         }
         if (UtilValidate.isEmpty(value)) {
         return;
         }
         }
         */
        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
        } else {

            // check for quantity
            BigDecimal quantity = BigDecimal.ONE;
            if (func != null && "QTY".equals(func[0])) {
                try {
                    quantity = new BigDecimal(func[1]);
                } catch (NumberFormatException e) {
                    quantity = BigDecimal.ONE;
                }
            }

            // locate the product ID
            String productId = null;
            try {
                List<GenericValue> items = trans.lookupItem(value);
                if (items != null) {
                    ListIterator<GenericValue> it = items.listIterator();
                    if (it.hasNext()) {
                        GenericValue product = it.next();
                        productId = product.getString("productId");
                        Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
                        productsMap.put(product.getString("productId"), product.get("internalName"));
                        while (it.hasNext()) {
                            product = it.next();
                            if (!productId.equals(product.getString("productId"))) {
                                productsMap.put(product.getString("productId"), product.get("internalName"));
                            }
                        }
                        if (productsMap.size() > 1 /*&& ButtonEventConfig.getButtonName(event).equals("menuSku")*/) {
                            SelectProduct SelectProduct = new SelectProduct(productsMap, trans, pos);
                            productId = SelectProduct.openDlg();
                        }
                    }
                }
            } catch (GeneralException e) {
                Debug.logError(e, module);
                pos.showMessageDialog("dialog/error/producterror");
            }

            // Check for Aggregated item
            boolean aggregatedItem = false;
            ProductConfigWrapper pcw = null;
            try {
                aggregatedItem = trans.isAggregatedItem(productId);
                if (aggregatedItem) {
                    pcw = trans.getProductConfigWrapper(productId);
                    pcw.setDefaultConfig();
                    ConfigureItem configureItem = new ConfigureItem(pcw, trans, pos);
                    pcw = configureItem.openDlg();
                    configureItem = null;
                }
            } catch (Exception e) {
                Debug.logError(e, module);
                pos.showMessageDialog("dialog/error/producterror");
            }

            // add the item to the cart; report any errors to the user
            if (productId != null) {
                try {
                    if (!aggregatedItem) {
                        trans.addItem(productId, quantity);
                    } else {
                        trans.addItem(productId, pcw);
                    }
                } catch (CartItemModifyException e) {
                    Debug.logError(e, module);
                    pos.showMessageDialog("dialog/error/producterror", e.getMessage());
                } catch (ItemNotFoundException e) {
                    pos.showMessageDialog("dialog/error/productnotfound");
                }
            } else {
                pos.showMessageDialog("dialog/error/productnotfound");
            }
        }

        // clear the qty flag
        input.clearFunction("QTY");

        Debug.logInfo("menuSku: addItem: end", module);

        // re-calc tax
        trans.calcTax();

        // refresh the others
        pos.refresh();
    }

    public static synchronized void changeQty(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        String sku = null;
        try {
            sku = getSelectedItem(pos);
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        if (sku == null) {
            pos.getOutput().print("Invalid Selection!");
            pos.getJournal().refresh(pos);
            pos.getInput().clear();
        }

        Input input = pos.getInput();
        String value = input.value();

        boolean increment = true;
        BigDecimal quantity = BigDecimal.ONE;
        if (UtilValidate.isNotEmpty(value)) {
            try {
                quantity = new BigDecimal(value);
            } catch (NumberFormatException e) {
                quantity = BigDecimal.ONE;
            }
        } else {
            String[] func = input.getLastFunction();
            if (func != null && "QTY".equals(func[0])) {
                increment = false;
                try {
                    quantity = new BigDecimal(func[1]);
                } catch (NumberFormatException e) {
                    quantity = trans.getItemQuantity(sku);
                }
            }
        }

        // adjust the quantity
        quantity = (increment ? trans.getItemQuantity(sku).add(quantity) : quantity);

        try {
            trans.modifyQty(sku, quantity);
        } catch (CartItemModifyException e) {
            Debug.logError(e, module);
            pos.showMessageDialog("dialog/error/producterror");
        }

        // clear the qty flag
        input.clearFunction("QTY");

        // re-calc tax
        trans.calcTax();

        // refresh the others
        pos.refresh();
    }

    public static synchronized void saleDiscount(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
        } else {
            Input input = pos.getInput();
            String value = input.value();
            if (UtilValidate.isNotEmpty(value)) {
                BigDecimal amount = ZERO;
                boolean percent = false;
                if (value.endsWith("%")) {
                    percent = true;
                    value = value.substring(0, value.length() - 1);
                }
                try {
                    amount = new BigDecimal(value);
                } catch (NumberFormatException e) {
                }

                amount = amount.movePointLeft(2).negate();
                trans.addDiscount(null, amount, percent);
                trans.calcTax();
            }
        }
        pos.refresh();
    }

    public static synchronized void itemDiscount(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
        } else {
            String sku = null;
            try {
                sku = getSelectedItem(pos);
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            if (sku == null) {
                pos.getOutput().print("Invalid Selection!");
                pos.getJournal().refresh(pos);
                pos.getInput().clear();
            }

            Input input = pos.getInput();
            String value = input.value();
            if (UtilValidate.isNotEmpty(value)) {
                BigDecimal amount = ZERO;
                boolean percent = false;
                if (value.endsWith("%")) {
                    percent = true;
                    value = value.substring(0, value.length() - 1);
                }
                try {
                    amount = new BigDecimal(value);
                } catch (NumberFormatException e) {
                }

                amount = amount.movePointLeft(2).negate();
                trans.addDiscount(sku, amount, percent);
                trans.calcTax();
            }
        }
        pos.refresh();
    }

    public static synchronized void clearDiscounts(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.clearDiscounts();
        trans.calcTax();
        pos.refresh();
    }

    public static synchronized void calcTotal(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.calcTax();

        pos.getInput().setFunction("TOTAL");
        pos.getJournal().refresh(pos);
    }

    public static synchronized void voidItem(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        String sku = null;
        try {
            sku = getSelectedItem(pos);
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        if (sku == null) {
            pos.getOutput().print("Invalid Selection!");
            pos.getJournal().refresh(pos);
            pos.getInput().clear();
        }

        try {
            trans.voidItem(sku);
        } catch (CartItemModifyException e) {
            pos.getOutput().print(e.getMessage());
        }

        // re-calc tax
        trans.calcTax();
        pos.refresh();
    }

    public static synchronized void voidAll(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.voidSale(pos);
        NavagationEvents.showPosScreen(pos);
        pos.refresh();
    }

    public static synchronized void saveSale(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.saveSale(pos);
    }

    public static synchronized void loadSale(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.loadSale(pos);
//        trans.loadOrder(pos); // TODO use order instead of shopping list
    }

    public static synchronized String getSelectedItem(PosScreen pos) {
        Journal journal = pos.getJournal();
        return journal.getSelectedSku();
    }

    public static synchronized void configureItem(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Journal journal = pos.getJournal();
        String index = journal.getSelectedIdx();
        String productId = journal.getSelectedSku();
        //trans.configureItem(index, pos);

        boolean aggregatedItem = false;
        ProductConfigWrapper pcw = null;
        try {
            aggregatedItem = trans.isAggregatedItem(productId);
            if (aggregatedItem) {
                pcw = trans.getProductConfigWrapper(productId, index);
                ConfigureItem configureItem = new ConfigureItem(pcw, trans, pos);
                pcw = configureItem.openDlg();
                configureItem = null;
                trans.modifyConfig(productId, pcw, index);
            } else {
                pos.showMessageDialog("dialog/error/itemnotconfigurable");
            }
        } catch (Exception e) {
            Debug.logError(e, module);
            pos.showMessageDialog("dialog/error/producterror");
        }

        trans.calcTax();
        pos.refresh();

        return;
    }

}
