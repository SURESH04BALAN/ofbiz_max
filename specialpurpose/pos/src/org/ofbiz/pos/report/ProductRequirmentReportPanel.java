/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.pos.generic.MapValueTableDataModel;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.device.DeviceLoader;
import org.ofbiz.pos.device.impl.Receipt;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;

/**
 *
 * @author siranjeev
 */
public class ProductRequirmentReportPanel extends InventoryReportPanel {

    public ProductRequirmentReportPanel() {
        super();
    }
    static public String name1 = "Requirment Report";

    @Override
    public String getName() {
        return name1;
    }
    List<RequirmentReportData> invoiceListItems = null;
    public static final String module = ProductRequirmentReportPanel.class.getName();
    protected List<Map<String, Object>> dataArray = new ArrayList<Map<String, Object>>();
    protected List<Map<String, Object>> summaryDataArray = new ArrayList<Map<String, Object>>();

    public void setFacilityId(String facilityId) {

    }

    private class RequirmentReportData {

        BigDecimal availableToPromise = null;
        BigDecimal quantityOnHandTotal = null;
        BigDecimal price = null;
        BigDecimal totalPrice = null;
        String available;
        String productId;
        String productName;
        String supplierProductId;
        String supplierPartyId;
        BigDecimal supplierLastPrice = null;
        String supplierPartyDesc;

        RequirmentReportData() {
        }
    }

    /*
     partyId=NIMCO, 
     availableFromDate=2013-08-02 22:39:02.0, 
     availableThruDate=null, 
     supplierPrefOrderId=10_MAIN_SUPPL, 
     supplierRatingTypeId=null, 
     standardLeadTimeDays=null, 
     minimumOrderQuantity=1.000000, 
     orderQtyIncrements=null, 
     unitsIncluded=null, 
     quantityUomId=null, 
     agreementId=null, 
     agreementItemSeqId=null, 
     lastPrice=0.800, 
     shippingPrice=null, 
     currencyUomId=USD, 
     supplierProductName=null, 
     supplierProductId=FS0404, 
     canDropShip=null, 
     supplierCommissionPerc=null, 
     comments=null, 
     lastUpdatedStamp=2013-08-02 22:39:02.0, 
     lastUpdatedTxStamp=2013-08-02 22:39:01.0, 
     createdStamp=2013-08-02 22:39:02.0, 
     createdTxStamp=2013-08-02 22:39:01.0, 
     qoh=1144, 
     atp=992, 
     qtySold=152.000000, 
     requirementId=10062, 
     requirementTypeId=PRODUCT_REQUIREMENT, 
     statusId=REQ_APPROVED, 
     facilityId=SevenHillsStore, 
     deliverableId=null,
     fixedAssetId=null, 
     description=null, 
     requirementStartDate=null, 
     requiredByDate=null, 
     estimatedBudget=null, 
     quantity=144.000000, 
     reason=null, 
     lastModifiedByUserLogin=1,
     roleTypeId=SUPPLIER,
     fromDate=2013-07-23 22:59:52.0, 
     thruDate=null}, 
     */
    String[][] ColumnName = {
        {"productId", "Product Code", "java.lang.String"},
        {"supplierProductId", "Supplier Product Id", "java.lang.String"},
        {"internalName", "Product Name", "java.lang.String"},
        {"quantity", "Required Qty", "java.math.BigDecimal"},
        {"atp", "Quantity On Hand", "java.math.BigDecimal"},
        {"qtySold", "Quantity Sold", "java.math.BigDecimal"},
        {"lastPrice", "Purchase Price", "java.math.BigDecimal"},
        {"UnitPrice", "Selling Price", "java.math.BigDecimal"},
        {"partyId", "Supplier Name", "java.lang.String"},};

    public javax.swing.JPanel runReport() {

        mtrans = PosTransaction.getCurrentTx(session);
        if (!mtrans.isOpen()) {
            OrderMaxOptionPane.showMessageDialog(null, "dialog/error/terminalclosed");
            return this;
        }

        dataArray.clear();

//        dataArray.clear();
        loadSuppliers();

//        if (invoiceListItems == null) {
        String supplierId = GetSelectedSupplierId();

        //all selected
        if (isAllSelected()) {
            supplierId = null;
        }

        final ClassLoader cl = mtrans.getSession().getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        summaryDataArray = new ArrayList<Map<String, Object>>();

        //        String productId = null;
        Map<String, Object> svcRes = null;
        boolean beganTransaction = false;
        try {

            beganTransaction = TransactionUtil.begin();

            LocalDispatcher dispatcher = mtrans.getSession().getDispatcher();
            String facilityId = mtrans.getFacilityId();

            // Get ATP for the product
            Map<String, Object> getRequirementsForSupplier = null;
            List<String> statusIds = FastList.newInstance();
            statusIds.add("REQ_APPROVED");
            statusIds.add("REQ_CREATED");

            try {
                getRequirementsForSupplier = dispatcher.runSync("getRequirementsForSupplier", UtilMisc.toMap("partyId", supplierId, "statusIds", statusIds));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Debug.logError(e, module);
//                            throw new Exception(e);
            }

            quantityTotal = (BigDecimal) getRequirementsForSupplier.get("quantityTotal");
            amountTotal = (BigDecimal) getRequirementsForSupplier.get("amountTotal");
            distinctProductCount = (Integer) getRequirementsForSupplier.get("distinctProductCount");
            // List<Map<String, Object>> 
            dataArray = (List<Map<String, Object>>) getRequirementsForSupplier.get("requirementsForSupplier");

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

        //      }
        MapValueTableDataModel model = new org.ofbiz.pos.generic.MapValueTableDataModel(ColumnName);
        getjTable1().setModel(model);
        for (Map<String, Object> mapList : dataArray) {
            summariseData(mapList);
        }

        model.addMapRows(summaryDataArray);
        return this;
//        model.addMapRows(dataArray);
    }

    void summariseData(Map<String, Object> requirmentMap) {

        String productId = (String) requirmentMap.get("productId");
        String partyId = (String) requirmentMap.get("partyId");
        String supplierProductId = (String) requirmentMap.get("supplierProductId");
        BigDecimal qtyReq = (BigDecimal) requirmentMap.get("quantity");
        boolean found = false;

        for (Map<String, Object> mapList : summaryDataArray) {
            String productIdSum = (String) mapList.get("productId");
            String partyIdSum = (String) mapList.get("partyId");
            String supplierProductIdSum = (String) mapList.get("supplierProductId");

            if (productId != null && productIdSum != null && productIdSum.equals(productId)) {
//                if (partyIdSum != null && partyId != null && partyIdSum.equals(partyId)) {
                BigDecimal quantity = (BigDecimal) mapList.get("quantity");
                BigDecimal qt = new BigDecimal(quantity.intValue());
                qt = qt.add(qtyReq);
                mapList.put("quantity", qt);

//                Debug.logInfo("old qty productId " + productId + "   " + quantity.toString(), module);
//                Debug.logInfo("new req qty productId " + productId + "   " + qtyReq.toString(), module);
                Debug.logInfo("total new productId " + productId + "   " + qt.toString(), module);
//                } else {
//                    summaryDataArray.add(requirmentMap);
//                }
                found = true;
                break;
            }
        }

        if (found == false) {
            GenericValue prod = ReportHelper.getGenericValue("Product", "productId", productId, mpos.getSession().getDelegator());
            requirmentMap.put("internalName", prod.getString("internalName"));
            Debug.logInfo("not found productId " + productId + "   " + qtyReq.toString(), module);
            summaryDataArray.add(requirmentMap);
        }
    }
    // scales and rounding modes for BigDecimal math
    public static final int scale = UtilNumber.getBigDecimalScale("order.decimals");
    public static final int rounding = UtilNumber.getBigDecimalRoundingMode("order.rounding");
    public static final BigDecimal ZERO = (BigDecimal.ZERO).setScale(scale, rounding);
    BigDecimal quantityTotal = null;
    BigDecimal amountTotal = null;
    Integer distinctProductCount = null;

    public void printReport(PosScreen pos, PosTransaction trans) {

        GenericValue state = null;
        boolean runBalance = false;

        if (!trans.isOpen()) {
            pos.showMessageDialog("dialog/error/terminalclosed");
            return;
        }
        if (state == null) {
            state = trans.getTerminalState();
        }

        //all selected
        if (isAllSelected()) {
            pos.showMessageDialog("Can't print all suppliers. Please select one supplier then press run.");
            return;
        }

        Map<String, String> reportMap = FastMap.newInstance();
        String reportTemplate = "totals.txt";

        List< Map<String, String>> reporPrintList = new ArrayList<Map<String, String>>();
        addHeaderLine(reporPrintList);
        Map<String, String> reportDetailMap = null;
        //headings
        boolean first = true;
        for (Map<String, Object> mapList : summaryDataArray) {
            for (Map.Entry<String, Object> entryLine : mapList.entrySet()) {
                if (entryLine.getValue() != null) {
                    reportMap.put(entryLine.getKey(), entryLine.getValue().toString());
                } else {
                    reportMap.put(entryLine.getKey(), " ");
                }
            }

            if (first) {

                reportDetailMap = FastMap.newInstance();
                String partyId = (String) mapList.get("partyId");
                String tel = "";
                String name = "";
                String description = "";
                GenericValue phone = findPartyLatestTelecomNumber(partyId, mpos.getSession().getDelegator());
                if (phone != null) {
                    tel = phone.getString("contactNumber");
                    name = phone.getString("askForName");
                }

                GenericValue desc = getSupplierValue(partyId);
                if (desc != null) {
                    description = desc.getString("description");
                }
                reportDetailMap.put("productId", tel);
                reportDetailMap.put("supplierProductId", name);
                reportDetailMap.put("internalName", description);
                reportDetailMap.put("quantity", "");
                reportDetailMap.put("lastPrice", partyId);
                reporPrintList.add(reportDetailMap);

                reportDetailMap = FastMap.newInstance();
                reportDetailMap.put("productId", "Product Id");
                reportDetailMap.put("supplierProductId", "Supplier Code");
                reportDetailMap.put("internalName", "=================================================");
                reportDetailMap.put("quantity", "Order Qty");
                reportDetailMap.put("lastPrice", "Last Price");
                reporPrintList.add(reportDetailMap);
                first = false;
            }

            reportDetailMap = FastMap.newInstance();
            reportDetailMap.put("productId", (String) mapList.get("productId"));
            reportDetailMap.put("supplierProductId", (String) mapList.get("supplierProductId"));
            reportDetailMap.put("internalName", (String) mapList.get("internalName"));
            reportDetailMap.put("quantity", UtilFormatOut.padString(UtilFormatOut.formatPrice((BigDecimal) mapList.get("quantity")), 8, false, ' '));
            reportDetailMap.put("lastPrice", UtilFormatOut.padString(UtilFormatOut.formatPrice((BigDecimal) mapList.get("lastPrice")), 8, false, ' '));
            reporPrintList.add(reportDetailMap);
        }

        addTotalsLine(reporPrintList);

//        Map<String, String> reportMap = FastMap.newInstance();
        Receipt receipt = DeviceLoader.receipt;
        if (receipt.isEnabled()) {
//sur            receipt.printCustomReport(trans, reportTemplate, reporPrintList);
        }

    }

    public void addHeaderLine(List< Map<String, String>> reporPrintList) {
        Map<String, String> reportDetailMap = null;
        reportDetailMap = FastMap.newInstance();
        reportDetailMap.put("productId", "");
        reportDetailMap.put("supplierProductId", "");
        reportDetailMap.put("internalName", "Re-Order Report");
        reportDetailMap.put("quantity", "");
        reportDetailMap.put("lastPrice", "");
        reporPrintList.add(reportDetailMap);

        reportDetailMap = FastMap.newInstance();
        reportDetailMap.put("productId", "");
        reportDetailMap.put("supplierProductId", "");
        reportDetailMap.put("internalName", "-------------------------------------------------------");
        reportDetailMap.put("quantity", "");
        reportDetailMap.put("lastPrice", "");
        reporPrintList.add(reportDetailMap);

    }

    public void addTotalsLine(List< Map<String, String>> reporPrintList) {
        Map<String, String> reportDetailMap = null;
        reportDetailMap = FastMap.newInstance();
        reportDetailMap.put("productId", "");
        reportDetailMap.put("supplierProductId", "");
        reportDetailMap.put("internalName", "-------------------------------------------------------");
        reportDetailMap.put("quantity", "");
        reportDetailMap.put("lastPrice", "");
        reporPrintList.add(reportDetailMap);

        reportDetailMap = FastMap.newInstance();
        reportDetailMap.put("productId", "Totals: ");
        reportDetailMap.put("supplierProductId", UtilFormatOut.padString(UtilFormatOut.formatPrice(distinctProductCount), 8, false, ' '));
        reportDetailMap.put("internalName", "-------------------------------------------------------");
        reportDetailMap.put("quantity", UtilFormatOut.padString(UtilFormatOut.formatPrice(quantityTotal), 8, false, ' '));
        reportDetailMap.put("lastPrice", UtilFormatOut.padString(UtilFormatOut.formatPrice(amountTotal), 8, false, ' '));
        reporPrintList.add(reportDetailMap);

    }
}
