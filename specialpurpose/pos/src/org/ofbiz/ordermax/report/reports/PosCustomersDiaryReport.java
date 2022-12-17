/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.basic.BasicException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.datasource.GenericHelperInfo;
import org.ofbiz.entity.jdbc.SQLProcessor;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.OrderHeaderAndPaymentPref;
import org.ofbiz.ordermax.payment.PaymentMethodTypeSingleton;
import org.ofbiz.party.party.PartyHelper;

/**
 *
 * @author BBS Auctions
 */
public class PosCustomersDiaryReport extends NewEntityJasperReport {

    public static final String module = PosCustomersDiaryReport.class.getName();

    public PosCustomersDiaryReport() {
    }

    @Override
    public void loadParameterSelections() {
        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(getQbffilter(), options, "By Customer");
        com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(getQbffilter(), options, "By Dates", "orderDate");

    }

    @Override
    public JPanel runReport() {
        deactivate();
        Debug.logInfo("runreport : " + getCompiledReportPathAndFile(), module);

        setReport(getCompiledReportPathAndFile());
        setResourceBundle(getResourcePathAndFile());
        addConditionList(reportArgs, getEntityConditions());
        try {
            reportArgs.putAll(getValues());
        } catch (BasicException ex) {
            Logger.getLogger(PosCustomersDiaryReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "OrderHeaderAndPaymentPref");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "Sales By Customer Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "customersdiary.jasper";
    }

    @Override
    public String getReportFileName() {
        return "customersdiary.jrxml";
    }

    @Override
    public String getResourceFileName() {
        return "customersdiary_messages.properties";
    }

    public class CustomersDiaryData {

        private String TICKETID;
        private String PAYMENT;
        private java.sql.Timestamp DATENEW;
        private String TAXID;
        private String NAME;
        private double TOTAL;

        public String getTICKETID() {
            return TICKETID;
        }

        public void setTICKETID(String TICKETID) {
            this.TICKETID = TICKETID;
        }

        public String getPAYMENT() {
            return PAYMENT;
        }

        public void setPAYMENT(String PAYMENT) {
            this.PAYMENT = PAYMENT;
        }

        public java.sql.Timestamp getDATENEW() {
            return DATENEW;
        }

        public void setDATENEW(java.sql.Timestamp DATENEW) {
            this.DATENEW = DATENEW;
        }

        public String getTAXID() {
            return TAXID;
        }

        public void setTAXID(String TAXID) {
            this.TAXID = TAXID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public double getTOTAL() {
            return TOTAL;
        }

        public void setTOTAL(double TOTAL) {
            this.TOTAL = TOTAL;
        }

    }

    Map<String, CustomersDiaryData> dataStore = new HashMap<String, CustomersDiaryData>();

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        Delegator delegator = ControllerOptions.getSession().getDelegator();

        String dateParam = getDateSql("OH.order_Date", collectionMap);
        String partyIdWhere = getPartyIdSql("ors.party_Id", collectionMap);

        String whereClause = "  WHERE ors.ROLE_TYPE_ID = 'BILL_TO_CUSTOMER'";
        if (UtilValidate.isNotEmpty(dateParam) || UtilValidate.isNotEmpty(partyIdWhere)) {
            whereClause = whereClause.concat(" AND ");
            if (UtilValidate.isNotEmpty(dateParam)) {
                whereClause = whereClause.concat(dateParam);
                if (UtilValidate.isNotEmpty(partyIdWhere)) {
                    whereClause = whereClause.concat(" AND ");
                }
            }

            if (UtilValidate.isNotEmpty(partyIdWhere)) {
                whereClause = whereClause.concat(partyIdWhere);
            }
        }

        String sqlString = "Select  ors.party_Id,\n"
                + "        ors.role_Type_Id,      \n"
                + "        oh.order_Id,\n"
                + "        oh.order_Date,\n"
                + "        oh.origin_Facility_Id,\n"
                + "        oh.product_Store_Id,\n"
                + "        oh.terminal_Id,\n"
                + "        oh.web_Site_Id,\n"
                + "        oh.currency_Uom,\n"
                + "        opp.order_Payment_Preference_Id,\n"
                + "        opp.payment_Method_Type_Id,\n"
                + "        oh.status_Id as order_Status_Id,\n"
                + "        opp.status_Id as payment_Status_Id,\n"
                + "        opp.created_By_User_Login,\n"
                + "        opp.max_Amount  from ofbiz.order_header as oh\n"
                + "	inner join ofbiz.order_payment_preference as opp on (oh.order_id = opp.order_id)\n"
                + "    inner join ofbiz.order_role as ors on (ors.order_id = oh.order_id)";

        sqlString = sqlString.concat(whereClause);
        GenericHelperInfo helperName = delegator.getGroupHelperInfo("org.ofbiz");
        SQLProcessor sqlproc = new SQLProcessor(helperName);
        //       String str;
        Collection result = new ArrayList<CustomersDiaryData>();
        Debug.logInfo(sqlString, module);
        sqlproc.prepareStatement(sqlString);
        ResultSet rs = sqlproc.executeQuery();

        while (rs.next()) {

            //  OrderHeaderAndPaymentPref orderHeaderAndPaymentPref = new OrderHeaderAndPaymentPref(genVal);
            CustomersDiaryData customersDiaryData = new CustomersDiaryData();
            customersDiaryData.TICKETID = rs.getString("ORDER_ID");
            try {
                customersDiaryData.PAYMENT = PaymentMethodTypeSingleton.getPaymentMethodType(rs.getString("payment_Method_Type_Id")).getdescription();
            } catch (Exception e) {
                Debug.logError(e, module);
            }
            customersDiaryData.DATENEW = rs.getTimestamp("order_Date");
            customersDiaryData.TAXID = "";
            //  OrderReadHelper helper = new OrderReadHelper(delegator, orderHeaderAndPaymentPref.getorderId());
            customersDiaryData.NAME = PartyHelper.getPartyName(delegator, rs.getString("party_Id"), false);
            customersDiaryData.TOTAL = rs.getDouble("max_Amount");

            result.add(customersDiaryData);
        }
        rs.close();
        sqlproc.close();
        return result;
    }
}
