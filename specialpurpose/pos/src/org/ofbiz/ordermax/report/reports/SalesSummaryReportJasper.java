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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mvc.controller.LoadPartyFromFileWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.InvoiceItemProductSummaries;
import org.ofbiz.ordermax.entity.InvoiceItemProductSummariesWithOrderId;
import org.ofbiz.ordermax.entity.InvoiceItemType;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.PartyNameView;
import org.ofbiz.ordermax.invoice.InvoiceItemTypeSingleton;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class SalesSummaryReportJasper extends EntityJasperReport {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = SalesSummaryReportJasper.class.getName();

    @Override
    public String identifier() {
        return "Sales Summary Report";
    }

    @Override
    public String getReportFileName() {
        return "SalesSummaryReport.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "SalesSummaryReport.jasper";
    }
    ShipmentReceiptComposite orderFinancialData = null;

    @Override
    public JPanel runReport() {

        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Inventory Item Receipt Report");
        param.put("DataFile", "CustomBeanFactory.java - Bean Collection");

//        Map<String, Object> reportArgs = EntityJasperReport.getNewArgMap();
//        EntityJasperReport.addParameters(reportArgs, param);
        getSelectionPanelValues(reportArgs);
        EntityJasperReport.addConditionList(reportArgs, condList);

        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
//        EntityJasperReport.addFacilityId(reportArgs, facilityId);

        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        addCompiliedFileName(reportArgs, getCompiledReportPathAndFile());
//        String subReport = getReportPath().concat("InventoryItem.jasper");
//        addCompiliedFileName(reportArgs, subReport);//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jasper");
        addEntity(reportArgs, "InvoiceItemProductSummaries");
        addOrderBy(reportArgs, "invoiceId DESC");
//        addOrderBy(reportArgs, "amountTotal DESC");        

        return super.runReport(reportArgs);
    }

    ReportDateSelectionPanel panelFilter = null;

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

        int idx = 0;
        int idy = 0;
        JPanel panel = AddDateSelection(filterList, "invoiceDate", controllerOptions, "Invoice Date Selection:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        panel = AddPartyIdSelection(filterList, controllerOptions, "Customer Party Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

//        panel = AddProductIdSelection(controllerOptions, "Product Id:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        //       EntityCondition cond = EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, "SALES_INVOICE");
        //       condList.add(cond);       
        InvoiceType invType = null;
        try {
            invType = InvoiceTypeSingleton.getInvoiceType("SALES_INVOICE");
        } catch (Exception ex) {
            Logger.getLogger(SalesSummaryReportJasper.class.getName()).log(Level.SEVERE, null, ex);
        }
        panel = createReportSelectionComboPanel(filterList, "invoiceTypeId", InvoiceTypeSingleton.getValueList(), "Invoice Type", invType);
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        panel = AddInvoiceIdSelection(filterList, controllerOptions, "Invoice Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        return panelFilter;
    }

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) {
        Collection result = null;
        try {
            Collection resultTemp = new ArrayList<InvoiceItemProductSummaries>();
            //String invoiceTypeId = "";
//        Collection result = new ArrayList<InvoiceItemProductSummary>();
            result = super.getBeanCollection(collectionMap);
            String invoiceId = null;
            for (Object gv : result) {
                InvoiceItemProductSummaries ips = (InvoiceItemProductSummaries) gv;

                if (UtilValidate.isEmpty(invoiceId) || !invoiceId.equals(ips.getInvoiceId())) {
                    invoiceId = ips.getInvoiceId();
                    List<EntityCondition> whereClauseMap = new ArrayList<EntityCondition>();
                    EntityCondition cond = EntityCondition.makeCondition("invoiceId", EntityOperator.EQUALS, invoiceId);

                    EntityCondition cond1 = EntityCondition.makeCondition("invoiceItemTypeId", EntityOperator.NOT_EQUAL, "INV_FPROD_ITEM");
                    whereClauseMap.add(cond);
                    whereClauseMap.add(cond1);
                    List<GenericValue> genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("InvoiceItem", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
                    for (GenericValue invoiceItem : genList) {
                        InvoiceItemProductSummaries item = new InvoiceItemProductSummaries();
                        item.setAmountTotal(invoiceItem.getBigDecimal("amount"));
                        item.setUnitAverageCost(invoiceItem.getBigDecimal("amount"));
                        item.setItemAmount(invoiceItem.getBigDecimal("amount"));
                        item.setQuantityTotal(invoiceItem.getBigDecimal("quantity"));
                        //item.setDescription(invoiceItem.getString("invoiceItemTypeId"));
                        item.setInvoiceItemTypeId(invoiceItem.getString("invoiceItemTypeId"));
                        item.setInvoiceItemSeqId(invoiceItem.getString("invoiceItemSeqId"));
                        item.setPartyIdFrom(ips.getPartyIdFrom());
                        item.setInvoiceTypeId(ips.getInvoiceTypeId());
                        item.setInvoiceDate(ips.getInvoiceDate());
                        item.setInvoiceId(ips.getInvoiceId());
                        item.setCurrencyUomId(ips.getCurrencyUomId());
                        item.setStatusId(ips.getStatusId());
                        item.setPartyId(ips.getPartyId());
                        setDetails(item);
                        resultTemp.add(item);
                    }
                }
                setDetails(ips);
                resultTemp.add(ips);
            }
            result = (resultTemp);
        } catch (Exception ex) {
            Logger.getLogger(SalesSummaryReportJasper.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Object item : result) {
            InvoiceItemProductSummaries ips = (InvoiceItemProductSummaries) item;
            Debug.logInfo("partyId: " + ips.getPartyId() + " invoiceTypeId: " + ips.getInvoiceTypeId() + "   ips.getInvoiceItemTypeId(): " + ips.getInvoiceItemTypeId() + " Quantity: " + ips.getQuantityTotal() + " UnitPrice: " + ips.getItemAmount() + " InvoiceId: " + ips.getInvoiceId(), module);
        }
        return result;
    }

    protected void setDetails(InvoiceItemProductSummaries ips) {
        if (UtilValidate.isEmpty(ips.getDescription())) {
            InvoiceItemType invoiceItemType;
            try {
                invoiceItemType = InvoiceItemTypeSingleton.getInvoiceItemType(ips.getInvoiceItemTypeId());
                ips.setDescription(invoiceItemType.getdescription());
                // Debug.logInfo("ips.getDescription: " + ips.getDescription(), module);

            } catch (Exception ex) {
                //  Logger.getLogger(SalesSummaryReportJasper.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        // Debug.logInfo("partyId: " + partyId + " invoiceTypeId: " + ips.getInvoiceTypeId() + "   ips.getInvoiceItemTypeId(): " + ips.getInvoiceItemTypeId(), module);

        try {
            InvoiceType invoiceType = InvoiceTypeSingleton.getInvoiceType(ips.getInvoiceTypeId());
            if (UtilValidate.isNotEmpty(invoiceType)) {
                ips.setInvoiceTypeDesc(invoiceType.getdescription());
            }
        } catch (Exception ex) {
            //  Logger.getLogger(SalesSummaryReportJasper.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (UtilValidate.isNotEmpty(partyId)) {
            String partyName = LoadPartyFromFileWorker.getFormatedPartyName(partyId, DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY);
            ips.setPartyName(partyName);
        }
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
