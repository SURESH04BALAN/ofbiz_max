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

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.component.Input;
import org.ofbiz.pos.component.Journal;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.base.util.UtilProperties;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Locale;
import org.ofbiz.base.util.UtilDateTime;

public class PaymentEvents {

    public static final String module = PaymentEvents.class.getName();

    public static synchronized void payCash(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.clearPayment("CASH");
        if (trans.isDifferentPaymentMethodExists("CASH")) {
            pos.showMessageDialog("Can't mix diffenet payment methods");
            return;
        }

        // all cash transactions are NO_PAYMENT; no need to check
        try {
            BigDecimal amount = processAmount(trans, pos, null);
            Debug.logInfo("Processing [Cash] Amount : " + amount, module);

            // add the payment
            trans.addPayment("CASH", amount, null, null);
        } catch (GeneralException e) {
            // errors handled
        }

        clearInputPaymentFunctions(pos);

        if (trans.getTotalDue().compareTo(BigDecimal.ZERO) <= 0) {
            processSale(pos);
        } else {
            pos.refresh();
        }
    }

    public static synchronized void payCash100(PosScreen pos) {
        BigDecimal amount = new BigDecimal("100");
        processCash(pos, amount);
    }

    public static synchronized void payCash50(PosScreen pos) {
        BigDecimal amount = new BigDecimal("50");
        processCash(pos, amount);
    }

    public static synchronized void payCash20(PosScreen pos) {
        BigDecimal amount = new BigDecimal("20");
        processCash(pos, amount);
    }

    public static synchronized void payCash10(PosScreen pos) {
        BigDecimal amount = new BigDecimal("10");
        processCash(pos, amount);
    }

    public static synchronized void payCash5(PosScreen pos) {
        BigDecimal amount = new BigDecimal("5");
        processCash(pos, amount);
    }

    public static synchronized void payCash2(PosScreen pos) {
        BigDecimal amount = new BigDecimal("2");
        processCash(pos, amount);
    }

    public static synchronized void payCash1(PosScreen pos) {
        BigDecimal amount = new BigDecimal("1");
        processCash(pos, amount);
    }

    public static synchronized void payCash50Cents(PosScreen pos) {
        BigDecimal amount = new BigDecimal("0.5");
        processCash(pos, amount);
    }

    public static synchronized void payCash20Cents(PosScreen pos) {
        BigDecimal amount = new BigDecimal("0.20");
        processCash(pos, amount);
    }

    public static synchronized void payCash10Cents(PosScreen pos) {
        BigDecimal amount = new BigDecimal("0.10");
        processCash(pos, amount);
    }

    public static synchronized void payCash5Cents(PosScreen pos) {
        BigDecimal amount = new BigDecimal("0.05");
        processCash(pos, amount);
    }

    public static synchronized void processCash(PosScreen pos, BigDecimal amount) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Timestamp timestamp = UtilDateTime.nowTimestamp();
        Debug.logInfo("Start processCash : " + UtilDateTime.nowTimestamp(), module);
        if (trans.isDifferentPaymentMethodExists("CASH")) {
            pos.showMessageDialog("Can't mix diffenet payment methods");
            return;
        }

        // all cash transactions are NO_PAYMENT; no need to check
        int count = trans.selectedPayments();
        BigDecimal value = trans.getPaymentTotal();
        trans.clearPayment("CASH");

        Debug.logInfo("Processing [Cash] Amount : " + amount + " count: " + count, module);
        if (value != null) {
            amount = amount.add(value);
        }

        Debug.logInfo("Processing [Cash] Amount : " + amount, module);

        // add the payment
        trans.addPayment("CASH", amount, null, null);

        clearInputPaymentFunctions(pos);

        if (trans.getTotalDue().compareTo(BigDecimal.ZERO) <= 0) {
            processSale(pos);
        } else {
            pos.refresh();
        }
        
        Timestamp endTimestamp = UtilDateTime.nowTimestamp();
        Debug.logInfo("Start processCash : " + endTimestamp + "   Time taken: " + (endTimestamp.getTime() - timestamp.getTime())/1000 , module);
    }

    public static synchronized void payCheck(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Input input = pos.getInput();
        String[] ckInfo = input.getFunction("CHECK");

        // check for no/external payment processing
        int paymentCheck = trans.checkPaymentMethodType("PERSONAL_CHECK");
        if (paymentCheck == PosTransaction.NO_PAYMENT) {
            trans.clearPayment("PERSONAL_CHECK");
            processNoPayment(pos, "PERSONAL_CHECK");
            return;
        } else if (paymentCheck == PosTransaction.EXTERNAL_PAYMENT) {
            if (ckInfo == null) {
                input.setFunction("CHECK");
                pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosRefNum", Locale.getDefault()));
                return;
            } else {
                processExternalPayment(pos, "PERSONAL_CHECK", ckInfo[1]);
                return;
            }
        }

        // now for internal payment processing
        pos.showMessageDialog("dialog/error/notyetsupported");
    }

    public static synchronized void payGiftCard(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Input input = pos.getInput();
        String[] gcInfo = input.getFunction("GIFTCARD");

        // check for no/external payment processing
        int paymentCheck = trans.checkPaymentMethodType("GIFT_CARD");
        if (paymentCheck == PosTransaction.NO_PAYMENT) {
            processNoPayment(pos, "GIFT_CARD");
            return;
        } else if (paymentCheck == PosTransaction.EXTERNAL_PAYMENT) {
            if (gcInfo == null) {
                input.setFunction("GIFTCARD");
                pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosRefNum", Locale.getDefault()));
                clearInputPaymentFunctions(pos);
                return;
            } else {
                processExternalPayment(pos, "GIFT_CARD", gcInfo[1]);
                return;
            }
        }

        // now for internal payment processing
        pos.showMessageDialog("dialog/error/notyetsupported");
        clearInputPaymentFunctions(pos);
    }

    public static synchronized void payCredit(PosScreen pos) {

        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
//        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.clearPayment("CREDIT_CARD");
        if (trans.isDifferentPaymentMethodExists("CREDIT_CARD")) {
            pos.showMessageDialog("Can't mix diffenet payment methods");
            return;
        }

        // all cash transactions are NO_PAYMENT; no need to check
        try {
            BigDecimal amount = processAmount(trans, pos, null);
            Debug.logInfo("Processing [Credit Card] Amount : " + amount, module);

            // add the payment
            trans.addPayment("CREDIT_CARD", amount, null, null);
        } catch (GeneralException e) {
            // errors handled
        }
        clearInputPaymentFunctions(pos);

        if (trans.getTotalDue().compareTo(BigDecimal.ZERO) <= 0) {
            processSale(pos);
        } else {
            pos.refresh();
        }

//        Input input = pos.getInput();

        /*
         String[] msrInfo = input.getFunction("MSRINFO");
         String[] crtInfo = input.getFunction("CREDIT");
         String[] track2Info = input.getFunction("TRACK2");
         String[] securityCodeInfo = input.getFunction("SECURITYCODE");
         String[] postalCodeInfo = input.getFunction("POSTALCODE");
         String[] creditExpirationInfo = input.getFunction("CREDITEXP");

         // check for no/external payment processing
         int paymentCheck = trans.checkPaymentMethodType("CREDIT_CARD");
         if (paymentCheck == PosTransaction.NO_PAYMENT) {
         processNoPayment(pos, "CREDIT_CARD");
         return;
         } else if (paymentCheck == PosTransaction.EXTERNAL_PAYMENT) {
         if (crtInfo == null) {
         input.setFunction("CREDIT");
         pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource,"PosRefNum",Locale.getDefault()));
         return;
         } else {
         processExternalPayment(pos, "CREDIT_CARD", crtInfo[1]);
         return;
         }
         }

         // now for internal payment processing
         if (crtInfo == null) {
         // set total, if entered
         input.clearLastFunction();
         input.setFunction("TOTAL");
         input.setFunction("CREDIT");
         pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource,"PosCredNo",Locale.getDefault()));
         } else {
         Debug.logInfo("Credit Func Info : " + crtInfo[1], module);
         if (msrInfo == null && (creditExpirationInfo == null))  {
         //test credit card
         if (UtilValidate.isNotEmpty(input.value()) && UtilValidate.isCreditCard(input.value())) {
         input.clearLastFunction();
         input.setFunction("CREDIT");
         input.setFunction("CREDITEXP");
         pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource,"PosCredex",Locale.getDefault()));
         } else {
         Debug.logInfo("Invalid card number - " + input.value(), module);
         clearInputPaymentFunctions(pos);
         input.clearInput();
         pos.showMessageDialog("dialog/error/invalidcardnumber");
         }
         } else if (msrInfo == null && (securityCodeInfo == null)) {
         // test expiration date
         if (UtilValidate.isNotEmpty(input.value()) && (input.value().length() == 4)) {
         // ask for Security Code, put in SECURITYCODE
         input.clearLastFunction();
         input.setFunction("CREDITEXP");
         input.setFunction("SECURITYCODE");
         pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource,"PosSecurityCode",Locale.getDefault()));
         } else {
         Debug.logInfo("Invalid expiration date", module);
         clearInputPaymentFunctions(pos);
         input.clearInput();
         pos.showMessageDialog("dialog/error/invalidexpirationdate");
         }
         } else if (msrInfo == null && (postalCodeInfo == null)) {
         // test security code - allow blank for illegible cards
         if (UtilValidate.isEmpty(input.value()) ||
         (UtilValidate.isNotEmpty(input.value()) && (input.value().length() <= 4))) {
         // ask for Security Code, put in SECURITYCODE
         input.clearLastFunction();
         input.setFunction("SECURITYCODE");
         input.setFunction("POSTALCODE");
         pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource,"PosPostalCode",Locale.getDefault()));
         } else {
         clearInputPaymentFunctions(pos);
         input.clearInput();
         pos.showMessageDialog("dialog/error/invalidsecuritycode");
         }
         } else {
         String msrInfoStr = null;
         if (msrInfo == null) {
         input.clearLastFunction();
         input.setFunction("POSTALCODE");
         postalCodeInfo = input.getFunction("POSTALCODE");
         if (UtilValidate.isNotEmpty(crtInfo[1])) {
         if (UtilValidate.isNotEmpty(creditExpirationInfo[1])) {
         // setup keyed transaction
         msrInfoStr = crtInfo[1] + "|" + creditExpirationInfo[1];
         } else {
         msrInfoStr = crtInfo[1];
         }
         }
         } else {  // is swiped
         // grab total from input, if available
         input.setFunction("TOTAL");
         msrInfoStr = msrInfo[1];
         }
         input.clearFunction("MSRINFO");
         input.setFunction("MSRINFO", msrInfoStr);
         String[] msrInfoArr = msrInfoStr.split("\\|");
         int allInfo = msrInfoArr.length;
         String firstName = null;
         String lastName = null;
         switch (allInfo) {
         case 4:
         lastName = msrInfoArr[3];
         case 3:
         firstName = msrInfoArr[2];
         case 2: // card number & exp date found
         BigDecimal amount = BigDecimal.ZERO;
         try {
         String[] totalInfo = input.getFunction("TOTAL");
         amount = processAmount(trans, pos, totalInfo[1]);
         Debug.logInfo("Processing Credit Card Amount : " + amount, module);
         } catch (GeneralException e) {
         Debug.logError("Exception caught calling processAmount.", module);
         Debug.logError(e.getMessage(), module);
         }

         String cardNumber = msrInfoArr[0];
         String expDate = msrInfoArr[1];
         String pmId = trans.makeCreditCardVo(cardNumber, expDate, firstName, lastName);
         if (pmId != null) {
         trans.addPayment(pmId, amount);
         }
         if (track2Info != null && UtilValidate.isNotEmpty(track2Info[1])) {
         // if swiped
         trans.setPaymentTrack2(pmId, null, track2Info[1]);
         } else { //keyed
         if (securityCodeInfo != null && UtilValidate.isNotEmpty(securityCodeInfo[1])) {
         trans.setPaymentSecurityCode(pmId, null, securityCodeInfo[1]);
         }
         if (postalCodeInfo != null && UtilValidate.isNotEmpty(postalCodeInfo[1])) {
         trans.setPaymentPostalCode(pmId, null, postalCodeInfo[1]);
         }
         }
         clearInputPaymentFunctions(pos);
         pos.refresh();
         break;
         case 1: // card number only found
         pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource,"PosCredex",Locale.getDefault()));
         break;
         default:
         Debug.logInfo("Hit the default switch case [" + allInfo + "] refreshing.", module);
         input.clearFunction("MSRINFO");
         pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource,"PosCredNo",Locale.getDefault()));
         break;
         }
         }
         }
         */
    }

    private static synchronized void processNoPayment(PosScreen pos, String paymentMethodTypeId) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());

        try {
            BigDecimal amount = processAmount(trans, pos, null);
            Debug.logInfo("Processing [" + paymentMethodTypeId + "] Amount : " + amount, module);

            // add the payment
            trans.addPayment(paymentMethodTypeId, amount, null, null);
        } catch (GeneralException e) {
            // errors handled
        }
        clearInputPaymentFunctions(pos);
        pos.refresh();
    }

    private static synchronized void processExternalPayment(PosScreen pos, String paymentMethodTypeId, String amountStr) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Input input = pos.getInput();
        String refNum = input.value();
        if (refNum == null) {
            pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosRefNum", Locale.getDefault()));
            return;
        }
        input.clearInput();

        try {
            BigDecimal amount = processAmount(trans, pos, amountStr);
            Debug.logInfo("Processing [" + paymentMethodTypeId + "] Amount : " + amount, module);

            // add the payment
            trans.addPayment(paymentMethodTypeId, amount, refNum, null);
        } catch (GeneralException e) {
            // errors handled
        }
        clearInputPaymentFunctions(pos);
        pos.refresh();
    }

    public static synchronized void clearPayment(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Journal journal = pos.getJournal();
        String sku = journal.getSelectedSku();
        String idx = journal.getSelectedIdx();
        if (UtilValidate.isNotEmpty(idx) && UtilValidate.isEmpty(sku)) {
            int index = -1;
            try {
                index = Integer.parseInt(idx);
            } catch (Exception e) {
            }
            if (index > -1) {
                trans.clearPayment(index);
            }
        }
        clearInputPaymentFunctions(pos);
        pos.refresh();
    }

    public static synchronized void clearAllPayments(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        trans.clearPayments();
        clearInputPaymentFunctions(pos);
        pos.refresh();
    }

    public static synchronized void setRefNum(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Journal journal = pos.getJournal();
        String sku = journal.getSelectedSku();
        String idx = journal.getSelectedIdx();

        if (UtilValidate.isNotEmpty(idx) && UtilValidate.isEmpty(sku)) {
            String refNum = pos.getInput().value();
            if (UtilValidate.isEmpty(refNum)) {
                pos.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosRefNum", Locale.getDefault()));
                pos.getInput().setFunction("REFNUM");
            } else {
                int index = -1;
                try {
                    index = Integer.parseInt(idx);
                } catch (Exception e) {
                }
                if (index > -1) {
                    trans.setPaymentRefNum(index, refNum, refNum);
                    pos.refresh();
                }
            }
        } else {
            pos.refresh();
        }
        clearInputPaymentFunctions(pos);
    }

    public static synchronized void processSale(PosScreen pos) {
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        Locale defaultLocale = Locale.getDefault();
        if (trans.isEmpty()) {
            PosScreen newPos = pos.showPage("pospanel");
            newPos.showMessageDialog("No items to pay");
        } else if (trans.getTotalDue().compareTo(BigDecimal.ZERO) > 0) {
            pos.showMessageDialog("dialog/error/exception", UtilProperties.getMessage("Xuilabels", "NOT_ENOUGH_FUNDS", defaultLocale));
            trans.clearPayment("CASH");
        } else {
            try {
                // manual locks (not secured; will be unlocked on clear)
                pos.setWaitCursor();
                PosScreen.currentScreen.getOutput().print(UtilProperties.getMessage(PosTransaction.resource, "PosProcessing", defaultLocale));
                pos.getInput().setLock(true);
                pos.getButtons().setLock(true);
                pos.refresh(false);

                // process the sale
                try {
//                    PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
                    trans.popDrawer();
//                    pos.refresh();
                    
                    trans.processSale(pos.getOutput());
                    pos.getInput().setFunction("PAID");
                } catch (GeneralException e) {
                    Debug.logError(e, e.getMessage(), module);
                    pos.getInput().setLock(false);
                    pos.getButtons().setLock(false);
                    pos.showMessageDialog("dialog/error/exception", e.getMessage());
                }
                clearInputPaymentFunctions(pos);
            } finally {
                pos.setNormalCursor();
            }
        }
    }

    private static synchronized BigDecimal processAmount(PosTransaction trans, PosScreen pos, String amountStr) throws GeneralException {
        Input input = pos.getInput();

        if (input.isFunctionSet("TOTAL")) {
            String amtStr = amountStr != null ? amountStr : input.value();
            BigDecimal amount;
            if (UtilValidate.isNotEmpty(amtStr)) {
                try {
                    amount = new BigDecimal(amtStr);
                } catch (NumberFormatException e) {
                    Debug.logError("Invalid number for amount : " + amtStr, module);
                    pos.getOutput().print("Invalid Amount!");
                    input.clearInput();
                    throw new GeneralException();
                }
                amount = amount.movePointLeft(2); // convert to decimal
                Debug.logInfo("Set amount / 100 : " + amount, module);
            } else {
                Debug.logInfo("Amount is empty; assumption is full amount : " + trans.getTotalDue(), module);
                amount = trans.getTotalDue();
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new GeneralException();
                }
            }
            return amount;
        } else {
            Debug.logInfo("TOTAL function NOT set", module);
            throw new GeneralException();
        }
    }

    // Removes all payment functions from the input function stack
    // Useful for clearing redundant data after a payment has been
    // processed or if an error occurred
    public static synchronized void clearInputPaymentFunctions(PosScreen pos) {
        String[] paymentFuncs = {"CHECK", "CHECKINFO", "CREDIT",
            "GIFTCARD", "MSRINFO", "REFNUM", "CREDITEXP",
            "TRACK2", "SECURITYCODE", "POSTALCODE"};
        Input input = pos.getInput();
        for (int i = 0; i < paymentFuncs.length; i++) {
            while (input.isFunctionSet(paymentFuncs[i])) {
                input.clearFunction(paymentFuncs[i]);
            }
        }
    }
}
