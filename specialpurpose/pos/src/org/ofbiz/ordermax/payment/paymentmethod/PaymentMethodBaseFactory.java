/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.paymentmethod;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ofbiz.ordermax.entity.PaymentMethodType;
import org.ofbiz.ordermax.payment.PaymentMethodTypeSingleton;


/**
 *
 * @author siranjeev
 */
public class PaymentMethodBaseFactory {

//    private static List<ReportInterface> reports = null;


static public String CERTIFIED_CHECK = "CERTIFIED_CHECK";
static public String COMPANY_CHECK = "COMPANY_CHECK";
static public String PERSONAL_CHECK = "PERSONAL_CHECK";

static public String COMPANY_ACCOUNT = "COMPANY_ACCOUNT";
static public String EFT_ACCOUNT = "EFT_ACCOUNT";
static public String FIN_ACCOUNT = "FIN_ACCOUNT";

static public String CREDIT_CARD = "CREDIT_CARD";

static public String CASH = "CASH";
static public String EXT_BILLACT = "EXT_BILLACT";
static public String EXT_COD = "EXT_COD";
static public String EXT_EBAY = "EXT_EBAY";
static public String EXT_GOOGLE_CHECKOUT = "EXT_GOOGLE_CHECKOUT";
static public String EXT_IDEAL = "EXT_IDEAL";
static public String EXT_OFFLINE = "EXT_OFFLINE";
static public String EXT_PAYPAL = "EXT_PAYPAL";
static public String EXT_WORLDPAY = "EXT_WORLDPAY";

static public String GIFT_CARD = "GIFT_CARD";
static public String GIFT_CERTIFICATE = "GIFT_CERTIFICATE";
static public String MONEY_ORDER = "MONEY_ORDER";
static public String EXT_EBS = "EXT_EBS";




    final static public Map<String, String> paymentMethodMap = new HashMap<String, String>();

    ;

    static {

//        reports = new ArrayList<ReportInterface>();
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EFT_ACCOUNT, EftPaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.COMPANY_ACCOUNT, EftPaymentPanel.module);        
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.FIN_ACCOUNT, EftPaymentPanel.module);                
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.PERSONAL_CHECK, ChequePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.COMPANY_CHECK, ChequePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.CERTIFIED_CHECK, ChequePaymentPanel.module);    
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.CASH, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_BILLACT, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_COD, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_EBAY, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_GOOGLE_CHECKOUT, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_IDEAL, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_OFFLINE, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_PAYPAL, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_WORLDPAY, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.GIFT_CARD, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.GIFT_CERTIFICATE, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.MONEY_ORDER, SimplePaymentPanel.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.EXT_EBS, SimplePaymentPanel.module);        
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.CREDIT_CARD, CreditCardPaymentPanel.module);        
                
/*        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.REP_GROUP_POS, InventoryItemReceiptReportJasper.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.REP_GROUP_POS, SupplierAgeingReport.module);
        PaymentMethodBaseFactory.registerReport(PaymentMethodBaseFactory.REP_GROUP_POS, CustomerPaymnetReportJasper.module);
        
*/        


    }

    public static void registerReport(String group, String report) {
        if (paymentMethodMap.containsKey(group) == false) {
            paymentMethodMap.put(group, report);
        } 
    }

    public static List<String> getReportList(String group) {
        List reportList = new ArrayList<String>();
        String rList = paymentMethodMap.get(group);
        if (rList != null) {

                reportList.add(rList);

        }
        return reportList;
    }

    public static PaymentMethodInterface getReport(String reportName) throws Exception {
        if(paymentMethodMap.containsKey(reportName)){

                    PaymentMethodInterface payMethodPanel = (PaymentMethodInterface) Class.forName(paymentMethodMap.get(reportName)).getConstructor().newInstance();
                    if(payMethodPanel!=null){
                        PaymentMethodType methodType = PaymentMethodTypeSingleton.getPaymentMethodType(reportName);
                        payMethodPanel.setPaymentMethodType(methodType);
                    }
                    return payMethodPanel;
        }

        throw new Exception("Report not found: " + reportName);
    }
}
