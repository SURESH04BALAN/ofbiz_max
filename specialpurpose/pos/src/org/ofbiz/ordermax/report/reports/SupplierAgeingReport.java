/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.DataBeanList;
import org.ofbiz.ordermax.entity.PartyNameView;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import org.ofbiz.ordermax.reportdesigner_old.InvoiceHeaderReport;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class SupplierAgeingReport extends EntityJasperReport {

    public static final String module = SupplierAgeingReport.class.getName();
    /*    static {
     ReportBaseFactory.registerReport(new InventoryItemReport());
     }
     */

    @Override
    public String identifier() {
        return "Supplier Ageing Table Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "CustomerPaymentAgeingReport.jasper";
    }

    @Override
    public String getReportFileName() {
        return "CustomerPaymentAgeingReport.jrxml";
    }

    @Override
    public JPanel runReport() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Inventory Item Report");
        param.put("DataFile", "Entity: OrderHeaderAndItems");
        org.ofbiz.ordermax.Dates.Month curMonth = new org.ofbiz.ordermax.Dates.Month();
        org.ofbiz.ordermax.Dates.RegularTimePeriod lastMonth = curMonth.previous();
        param.put("CURRENTEOM", curMonth.getEnd());
        param.put("LAST30EOM", lastMonth.getEnd());
        org.ofbiz.ordermax.Dates.RegularTimePeriod last60Month = lastMonth.previous();
        param.put("LAST60EOM", last60Month.getEnd());
        org.ofbiz.ordermax.Dates.RegularTimePeriod last90Month = last60Month.previous();
        param.put("LAST90EOM", last90Month.getEnd());

        EntityJasperReport.addConditionList(reportArgs, condList);
        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        addCompiliedFileName(reportArgs, getCompiledReportPathAndFile());
        addParametersNameMap(reportArgs, param);

        return super.runReport(reportArgs);
    }
    String currentDate = "currentDate";
    @Override
    public JPanel getSelectionPanel() {
        ReportParameterSelectionPanel panelFilter = new ReportParameterSelectionPanel();
        filterList.clear();
        GridBagLayout layout = new GridBagLayout();
        panelFilter.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        ControllerOptions controllerOptions = new ControllerOptions();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 0, 0, 0);  //top padding   
        gbc.weightx = 1;
        controllerOptions.put("endDateEnabled", false);
        int idx = 0;
        int idy = 0;
        JPanel panel = AddDateSelection(filterList, currentDate, controllerOptions, "Current Date Selection:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        return panelFilter;
    }
    
//    getInvoicePaymentInfoListByDueDateOffset

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
        //DataBeanList dataBeanList = new DataBeanList();
        String invoiceTypeId = "";
        Collection result = new ArrayList<InvoiceHeaderReport>();

        if (reportArgs.containsKey("invoiceTypeId")) {
            invoiceTypeId = (String) reportArgs.get("invoiceTypeId");
        } else {
            throw new Exception("Invalid invoice type id: " + invoiceTypeId);
        }

        if (UtilValidate.isNotEmpty(invoiceTypeId)) {
            result = loadInvoice(invoiceTypeId, session);
        } else {
            throw new Exception("Invalid invoice type  id: " + invoiceTypeId);
        }

        return result;
    }

    static public class PaymentAgeingReportData {

        private java.sql.Timestamp dueDate;
        private String invoiceId;
        private BigDecimal amount;
        private BigDecimal paidAmount;
        private BigDecimal outstandingAmount;
        private String paymentTypeId;

        private String comments;
        private String partyFromFirstName;
        private String partyFromGroupName;
        private String partyFromLastName;
        private String partyIdTo;
        private String partyToFirstName;

        private String partyToGroupName;
        private String partyToLastName;
        private String paymentMethodTypeDesc;
        private String paymentRefNum;
        private String partyTypeId;

        public String getPartyTypeId() {
            return partyTypeId;
        }

        public void setPartyTypeId(String partyTypeId) {
            this.partyTypeId = partyTypeId;
        }
        private String paymentTypeDesc;

        public void setPartyNameView(PartyNameView val) {
            partyIdTo = val.getpartyId();
            partyToFirstName = val.getfirstName();
            partyToGroupName = val.getgroupName();
            partyToLastName = val.getlastName();
            partyTypeId = val.getpartyTypeId();
        }

        public String getPaymentTypeId() {
            return paymentTypeId;
        }

        public void setPaymentTypeId(String paymentTypeId) {
            this.paymentTypeId = paymentTypeId;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getPartyFromFirstName() {
            return partyFromFirstName;
        }

        public void setPartyFromFirstName(String partyFromFirstName) {
            this.partyFromFirstName = partyFromFirstName;
        }

        public String getPartyFromGroupName() {
            return partyFromGroupName;
        }

        public void setPartyFromGroupName(String partyFromGroupName) {
            this.partyFromGroupName = partyFromGroupName;
        }

        public String getPartyFromLastName() {
            return partyFromLastName;
        }

        public void setPartyFromLastName(String partyFromLastName) {
            this.partyFromLastName = partyFromLastName;
        }

        public String getPartyIdTo() {
            return partyIdTo;
        }

        public void setPartyIdTo(String partyIdTo) {
            this.partyIdTo = partyIdTo;
        }

        public String getPartyToFirstName() {
            return partyToFirstName;
        }

        public void setPartyToFirstName(String partyToFirstName) {
            this.partyToFirstName = partyToFirstName;
        }

        public String getPartyToGroupName() {
            return partyToGroupName;
        }

        public void setPartyToGroupName(String partyToGroupName) {
            this.partyToGroupName = partyToGroupName;
        }

        public String getPartyToLastName() {
            return partyToLastName;
        }

        public void setPartyToLastName(String partyToLastName) {
            this.partyToLastName = partyToLastName;
        }

        public String getPaymentMethodTypeDesc() {
            return paymentMethodTypeDesc;
        }

        public void setPaymentMethodTypeDesc(String paymentMethodTypeDesc) {
            this.paymentMethodTypeDesc = paymentMethodTypeDesc;
        }

        public String getPaymentRefNum() {
            return paymentRefNum;
        }

        public void setPaymentRefNum(String paymentRefNum) {
            this.paymentRefNum = paymentRefNum;
        }

        public String getPaymentTypeDesc() {
            return paymentTypeDesc;
        }

        public void setPaymentTypeDesc(String paymentTypeDesc) {
            this.paymentTypeDesc = paymentTypeDesc;
        }

        public java.sql.Timestamp getDueDate() {
            return dueDate;
        }

        public void setDueDate(java.sql.Timestamp dueDate) {
            this.dueDate = dueDate;
        }

        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(BigDecimal paidAmount) {
            this.paidAmount = paidAmount;
        }

        public BigDecimal getOutstandingAmount() {
            return outstandingAmount;
        }

        public void setOutstandingAmount(BigDecimal outstandingAmount) {
            this.outstandingAmount = outstandingAmount;
        }

    }

    static public Collection<PaymentAgeingReportData> loadInvoice(String invoiceTypeId, XuiSession session) throws Exception {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Collection<PaymentAgeingReportData> invoiceList = FastList.newInstance();

        try {

            resultMap = dispatcher.runSync("getInvoicePaymentInfoListByDueDateOffset", UtilMisc.toMap(
                    "invoiceTypeId", invoiceTypeId,
                    "daysOffset", new Long("0"),
                    "userLogin", userLogin));
            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                Debug.logInfo("Payment Info : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }

            if (resultMap.containsKey("invoicePaymentInfoList")) {
                List<javolution.util.FastMap<String, Object>> invoiceItems = (List<javolution.util.FastMap<String, Object>>) resultMap.get("invoicePaymentInfoList");
                for (javolution.util.FastMap<String, Object> gv : invoiceItems) {
                    PaymentAgeingReportData repData = new PaymentAgeingReportData();
                    repData.setDueDate((Timestamp) gv.get("dueDate"));
                    repData.setInvoiceId((String) gv.get("invoiceId"));
                    repData.setAmount((BigDecimal) gv.get("amount"));
                    repData.setPaidAmount((BigDecimal) gv.get("paidAmount"));
                    repData.setOutstandingAmount((BigDecimal) gv.get("outstandingAmount"));

                    PartyNameView partyNameView = null;
                    String partyId = null;

                    try {
                        GenericValue genericVal = session.getDelegator().findByPrimaryKey("Invoice",
                                UtilMisc.toMap("invoiceId", repData.invoiceId));
                        if (genericVal != null) {
                            if ("SALES_INVOICE".equals(invoiceTypeId)) {
                                partyId = genericVal.getString("partyId");
                            } else {
                                partyId = genericVal.getString("partyIdFrom");
                            }
                            invoiceTypeId = (String) genericVal.getString("invoiceTypeId");
                        }
                        GenericValue genPartyNameView = session.getDelegator().findByPrimaryKeyCache("PartyNameView", UtilMisc.toMap("partyId", partyId));
                        partyNameView = new PartyNameView(genPartyNameView);
                        repData.setPartyNameView(partyNameView);
                        Debug.logInfo("setOutstandingAmount : " + repData.partyTypeId + " --- repData.partyToFirstName  --- " + repData.partyToFirstName + " --- partyNameView.getlastName() " + repData.partyToLastName+  " --- repData.gropNamr() " + repData.partyToGroupName, module);
                    } catch (GenericEntityException e) {
                        Debug.logError(e, "Error finding PartyAssignmentConstraint information for constraint pretty print", module);
                    }
                    invoiceList.add(repData);
                }
            }

        } catch (NumberFormatException exc) {
            Debug.logWarning("Unable to quick ship test sales order with id  with error: " + exc.getMessage(), module);
        }

        return invoiceList;
    }
}
