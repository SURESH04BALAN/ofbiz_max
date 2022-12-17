/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports.erp;

import org.ofbiz.ordermax.report.reports.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mvc.controller.LoadPartyFromFileWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.InvoiceItemProductSummaries;
import org.ofbiz.ordermax.entity.InvoiceItemType;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.invoice.InvoiceItemTypeSingleton;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class NewCustomerSalesReport extends NewEntityJasperReport {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = NewCustomerSalesReport.class.getName();

    @Override
    public String identifier() {
        return "Customer Sales Report";
    }

    @Override
    public String getReportFileName() {
        return "SalesReport.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "SalesReport.jasper";
    }
    ShipmentReceiptComposite orderFinancialData = null;

    @Override
    public JPanel runReport() {

        deactivate();

        setReport(getCompiledReportPathAndFile());
        List<EntityCondition> condList = getEntityConditions();
      //  EntityCondition cond = EntityCondition.makeCondition("paymentTypeId", EntityOperator.EQUALS, "CUSTOMER_PAYMENT");
      //  condList.add(cond);
        NewEntityJasperReport.addConditionList(reportArgs, condList);
        NewEntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        NewEntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
        
        addEntity(reportArgs, "InvoiceItemProductSummaries");
        addOrderBy(reportArgs, "invoiceId DESC");

        launchreport(reportArgs);
        return this;
    }

    @Override
    public void loadParameterSelections() {
        ControllerOptions optionsDate = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(getQbffilter(), optionsDate, "Date Selection", "effectiveDate");

        ControllerOptions optionsParty = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(getQbffilter(), optionsParty, "Select Party");

        InvoiceType invType = null;
        try {
            invType = InvoiceTypeSingleton.getInvoiceType("SALES_INVOICE");
        } catch (Exception ex) {
            Logger.getLogger(NewSalesSummaryReportJasper.class.getName()).log(Level.SEVERE, null, ex);
        }      

        com.openbravo.pos.reports.params.JParamHelper.addReportSelectionComboPanel(getQbffilter(), "invoiceTypeId", InvoiceTypeSingleton.getValueList(), "Invoice Type Selection", "invoiceTypeId", "Invoice Type:", invType);        

        
        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportInvoiceId(getQbffilter(), options, "Select Invoice");
    }

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) {
        Collection result = null;
        try {
            //String invoiceTypeId = "";
//        Collection result = new ArrayList<InvoiceItemProductSummary>();
             result = super.getBeanCollection(collectionMap);
            for (Object gv : result) {
                InvoiceItemProductSummaries ips = (InvoiceItemProductSummaries) gv;
                if (UtilValidate.isEmpty(ips.getDescription())) {
                    InvoiceItemType invoiceItemType = InvoiceItemTypeSingleton.getInvoiceItemType(ips.getInvoiceItemTypeId());
                    ips.setDescription(invoiceItemType.getdescription());
                    Debug.logInfo("ips.getDescription: " + ips.getDescription(), module);
                }
                if (UtilValidate.isEmpty(ips.getQuantityTotal())) {
                    ips.setQuantityTotal(BigDecimal.ONE);
                }
                String partyId = null;
                if (UtilValidate.isNotEmpty(ips.getInvoiceTypeId())) {
                    if ("SALES_INVOICE".equals(ips.getInvoiceTypeId())) {
                        partyId = ips.getPartyId();
                        ips.setInvoiceTypeId(ips.getInvoiceTypeId());
                    } else {
                        partyId = ips.getPartyIdFrom();
                    }
                }
                Debug.logInfo("partyId: " + partyId + " invoiceTypeId: " + ips.getInvoiceTypeId() ,  module);
                
                InvoiceType invoiceType = InvoiceTypeSingleton.getInvoiceType(ips.getInvoiceTypeId());
                if(UtilValidate.isNotEmpty(invoiceType)){
                    ips.setInvoiceTypeDesc(invoiceType.getdescription());
                }
                
                if (UtilValidate.isNotEmpty(partyId)) {
                    String partyName = LoadPartyFromFileWorker.getFormatedPartyName(partyId, DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
                    ips.setPartyName(partyName);
                }
            }
            /*if (reportArgs.containsKey("invoiceTypeId")) {
            invoiceTypeId = (String) reportArgs.get("invoiceTypeId");
            } else {
            throw new Exception("Invalid invoice type id: " + invoiceTypeId);
            }
            
            if (UtilValidate.isNotEmpty(invoiceTypeId)) {
            result = loadInvoice(invoiceTypeId, session);
            } else {
            throw new Exception("Invalid invoice type  id: " + invoiceTypeId);
            }*/
            

        } catch (Exception ex) {
            Logger.getLogger(NewCustomerSalesReport.class.getName()).log(Level.SEVERE, null, ex);
        }
                    return result;
    }

    /*static public Collection<InvoiceItemProductSummary> loadInvoice(String invoiceTypeId, XuiSession session) throws Exception {

     GenericValue userLogin = session.getUserLogin();
     LocalDispatcher dispatcher = session.getDispatcher();
     Map<String, Object> resultMap = ServiceUtil.returnSuccess();
     Collection<InvoiceItemProductSummary> invoiceList = FastList.newInstance();

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
     Debug.logInfo("setOutstandingAmount : " + repData.partyTypeId + " --- repData.partyToFirstName  --- " + repData.partyToFirstName + " --- partyNameView.getlastName() " + repData.partyToLastName + " --- repData.gropNamr() " + repData.partyToGroupName, module);
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
     }*/
}
