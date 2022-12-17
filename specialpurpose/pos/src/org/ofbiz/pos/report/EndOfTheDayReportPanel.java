/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.report;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.ofbiz.accounting.invoice.InvoiceWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderReadHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.report.OrderMaxJRViewer;
import static org.ofbiz.ordermax.report.ReportBaseMain.AddDateSelection;
import static org.ofbiz.ordermax.report.ReportBaseMain.addToGridLayout;
import org.ofbiz.ordermax.report.ReportInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.generic.*;

/**
 *
 * @author siranjeev
 */
public class EndOfTheDayReportPanel extends AbstractReportPanel{

    static public String name = "End Of the day";
    public static final String module = EndOfTheDayReportPanel.class.getName();
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();

    /**
     * Creates new form EndOfTheDayReportPanel
     */
    public EndOfTheDayReportPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return name;
    }
    String[][] reportQueryIdColumnName = {
        {"fieldGroup", "Group", "java.lang.String"},
        {"itemName", "Invoice No", "java.lang.String"},
        {"itemTotalValue", "Total($)", "java.lang.String"},
        {"itemPaidValue", "Paid($)", "java.lang.String"},
        {"itemBalanceValue", "Balance($)", "java.lang.String"},};
    
    @Override
    public javax.swing.JPanel runReport() {
        try{
            throw new Exception("Why");
        }
        catch(Exception e){
            Debug.logError(e, module);
        }
        
        dataArray.clear();

        PosTransaction trans = PosTransaction.getCurrentTx(session);
        if (!trans.isOpen()) {
            OrderMaxOptionPane.showMessageDialog(null, "dialog/error/terminalclosed");
            return this;
        }

        GenericValue state = null;
        if (state == null) {
            state = trans.getTerminalState();
        }

        BigDecimal checkTotal = BigDecimal.ZERO;
        BigDecimal cashTotal = BigDecimal.ZERO;
        BigDecimal gcTotal = BigDecimal.ZERO;
        BigDecimal ccTotal = BigDecimal.ZERO;
        BigDecimal othTotal = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            Delegator delegator = session.getDelegator();
            List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("originFacilityId", EntityOperator.EQUALS, trans.getFacilityId()),
                    EntityCondition.makeCondition("terminalId", EntityOperator.EQUALS, trans.getTerminalId()));
            EntityListIterator eli = null;

            try {
                eli = delegator.find("OrderHeaderAndPaymentPref", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, null);
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
            }
            //    state.getTimestamp("openedDate").setMonth(1);
            java.sql.Timestamp dayStart = state.getTimestamp("openedDate");
            java.sql.Timestamp dayEnd = state.getTimestamp("closedDate");
            if (dayEnd == null) {
                dayEnd = UtilDateTime.nowTimestamp();
            }

            Debug.logInfo(" dayStart: " + dayStart.toString(), module);
            Debug.logInfo(" closedDate: " + dayEnd.toString(), module);

            if (eli != null) {
                GenericValue ohpp;

                addRowToTable("Sales Orders", "", "", "", "");

                while (((ohpp = eli.next()) != null)) {
                    java.sql.Timestamp orderDate = ohpp.getTimestamp("orderDate");
                    if (orderDate.after(dayStart) && orderDate.before(dayEnd)) {
                        String pmt = ohpp.getString("paymentMethodTypeId");
                        BigDecimal amt = ohpp.getBigDecimal("maxAmount");
                        String pmt1 = ohpp.getString("orderId");
                        String pmt2 = ohpp.getString("orderDate");
                        String pmt3 = ohpp.getString("originFacilityId");
                        String pmt4 = ohpp.getString("productStoreId");
                        String pmt5 = ohpp.getString("terminalId");
                        String pmt6 = ohpp.getString("webSiteId");
                        String pmt7 = ohpp.getString("currencyUom");
                        String pmt8 = ohpp.getString("orderPaymentPreferenceId");

                        String pmt9 = ohpp.getString("orderStatusId");
                        String pmt10 = ohpp.getString("paymentStatusId");
                        String msg = "paymentMethodTypeId: " + pmt + " ,orderId: " + pmt1 + " ,orderDate: " + pmt2 + " ,originFacilityId: "
                                + pmt3 + " ,productStoreId: " + pmt4 + " ,terminalId: " + pmt5 + " ,webSiteId: " + pmt6 + " ,currencyUom: " + pmt7 + " ,orderPaymentPreferenceId: " + pmt8 + " ,orderStatusId " + pmt9
                                + " ,paymentStatusId " + pmt10 + " ,maxAmount: " + amt.toString();
                        Debug.logInfo(msg, module);

//						line = BaseTableInterface.appendNode( "tr", "", "");                
//			            BaseTableInterface.appendNode(line, "td","fieldGroup", "");
//			            BaseTableInterface.appendNode(line, "td","itemName", pmt1);
//			            BaseTableInterface.appendNode(line, "td","itemValue", amt.toString());
                        addRowToTable("", pmt1, amt.toString(), amt.toString(), "");

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
                    OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
                }
            }

            addRowToTable("Total Sales Amount:", "", total.toString(), total.toString(), "");

            appendEmpty();
            appendEmpty();

            Debug.logInfo("Total Sales Amount: " + total.toString(), module);
            addRowToTable("Purchase Orders ", "", "", "", "");
            BigDecimal totalPurchaseAmount = BigDecimal.ZERO;
            BigDecimal totalPaidAmount = BigDecimal.ZERO;
            BigDecimal totalUnpaidAmount = BigDecimal.ZERO;

            List<GenericValue> invoiceList = getPurchaseInvoicesForGivenDatePeriod(dayStart, dayEnd);
            if (invoiceList.isEmpty() == false) {
                for (GenericValue invoice : invoiceList) {
//                GenericValue invoice = invoiceList.get(0);
                    BigDecimal notApplied = BigDecimal.TEN;
                    String invoiceId = "";

                    List<GenericValue> orderItemBillings = invoice.getRelated("OrderItemBilling");
                    for (GenericValue billing : orderItemBillings) {
                        OrderReadHelper orderReadHelper = new OrderReadHelper(OrderReadHelper.getOrderHeader(delegator, billing.getString("orderId")));
                        invoiceId = billing.getString("invoiceId");
                        String orderId = billing.getString("orderId");
                        String msg = "Invoice Saved ";
                        if (orderReadHelper != null) {

                            BigDecimal purchaseAmount = orderReadHelper.getOrderGrandTotal();
                            BigDecimal paidAmount = InvoiceWorker.getInvoiceApplied(delegator, invoiceId);
                            BigDecimal unpaidAmount = InvoiceWorker.getInvoiceNotApplied(delegator, invoiceId);

                            addRowToTable("", invoiceId, purchaseAmount.toString(),
                                    paidAmount.toString(),
                                    unpaidAmount.toString());

                            totalPurchaseAmount = totalPurchaseAmount.add(purchaseAmount);
                            totalPaidAmount = totalPaidAmount.add(paidAmount);
                            totalUnpaidAmount = totalUnpaidAmount.add(unpaidAmount);
//			            addRowToTable( "",  "Paid Amount - " + invoiceId , InvoiceWorker.getInvoiceApplied(delegator, invoiceId).toString());			            
//			            addRowToTable( "",  "Balance Amount -" + invoiceId , InvoiceWorker.getInvoiceNotApplied(delegator, invoiceId).toString());			            
                        }
                        break;
                    }
                    /*
                     List<GenericValue> paymentAppls = invoice.getRelated("PaymentApplication");
                     for (GenericValue paymentAppl : paymentAppls) {
                     GenericValue payment = paymentAppl.getRelatedOne("Payment");
                     String pymtString = payment.getString("paymentMethodTypeId");
                     if (pymtString!=null && pymtString.isEmpty() == false) {
                     if(pymtString.equals("CREDIT_CARD")){
                                
                     }
                     else{
                                
                     }

                     }
                     }
                     */
                }
            }
//            addRowToTable( "Total Purchase Orders",  "" + invoiceId , orderReadHelper.getOrderGrandTotal().toString(),  InvoiceWorker.getInvoiceApplied(delegator, invoiceId).toString(), InvoiceWorker.getInvoiceNotApplied(delegator, invoiceId).toString());
            addRowToTable("Total Purchase Orders:", "", totalPurchaseAmount.toString(), totalPaidAmount.toString(), totalUnpaidAmount.toString());
            appendEmpty();
            appendEmpty();

            addRowToTable("Total SALES:", "", total.toString(), "", "");
            addRowToTable("Total Payment:", "", totalPaidAmount.toString(), "", "");
            appendEmpty();

            addRowToTable("Total CASH:", "", cashTotal.toString(), "", "");
            addRowToTable("Total EFPOST:", "", ccTotal.toString(), "", "");
            addRowToTable("Total CASH+EFPOST:", "", total.subtract(totalPaidAmount).toString(), "", "");

            appendEmpty();
            appendEmpty();
            appendEmpty();
            appendEmpty();

        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e2.getMessage());
            }
            OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
                OrderMaxOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        MapValueTableDataModel model = new org.ofbiz.pos.generic.MapValueTableDataModel(reportQueryIdColumnName);
        jTable1.setModel(model);
        model.addMapRows(dataArray);
        return this;
    }

    protected List<GenericValue> getPurchaseInvoicesForGivenDatePeriod(java.sql.Timestamp dayStart, java.sql.Timestamp dayEnd) {
        //String errMsg = "";
        Delegator delegator = (Delegator) session.getDelegator();

        EntityListIterator entityListIterator = null;
        boolean beganTx = false;
        String productId = null;
        List<GenericValue> orderList = FastList.newInstance();
        //         Hashtable<String, Object> productsMap = new Hashtable<String, Object>();
        ArrayList<GenericValue> productsList = new ArrayList<GenericValue>();
        try {
            // begin the transaction
            beganTx = TransactionUtil.begin(7200);
            try {

                List<String> orderBy = FastList.newInstance();
                //Set<String> fieldsToSelect = FastSet.newInstance();
                List<String> conditionToSelect = FastList.newInstance();
                // fields we need to select; will be used to set distinct

                orderBy.add("invoiceDate");
                List<EntityExpr> exprs = UtilMisc.toList(EntityCondition.makeCondition("invoiceTypeId", EntityOperator.EQUALS, "PURCHASE_INVOICE"));

                EntityCondition cond = EntityCondition.makeCondition(exprs, EntityOperator.AND);
                EntityFindOptions efo = new EntityFindOptions();
                efo.setResultSetType(EntityFindOptions.TYPE_SCROLL_INSENSITIVE);

                entityListIterator = delegator.find("Invoice", cond, null, null, orderBy, efo);
                GenericValue invoice;
                while (((invoice = entityListIterator.next()) != null)) {
                    java.sql.Timestamp orderDate = invoice.getTimestamp("invoiceDate");
                    if (orderDate.after(dayStart) && orderDate.before(dayEnd)) {
                        String msg = "INVOICE ID: " + invoice.getString("invoiceId") + " ,Party Id from: " + invoice.getString("partyIdFrom");
                        Debug.logInfo(msg, module);
                        orderList.add(invoice);
                    }
                }

            } catch (GenericEntityException gee) {
                Debug.logWarning(gee, gee.getMessage(), module);
                Map<String, String> messageMap = UtilMisc.toMap("gee", gee.toString());
                throw gee;
            }

        } catch (GenericEntityException e) {
            try {
                TransactionUtil.rollback(beganTx, e.getMessage(), e);
            } catch (Exception e1) {
                Debug.logError(e1, module);
            }
        } catch (Throwable t) {
            Debug.logError(t, module);
            try {
                TransactionUtil.rollback(beganTx, t.getMessage(), t);
            } catch (Exception e2) {
                Debug.logError(e2, module);
            }

        } finally {
            if (entityListIterator != null) {
                try {
                    entityListIterator.close();
                } catch (GenericEntityException gee) {
                    Debug.logError(gee, "Error closing EntityListIterator when indexing product keywords.", module);
                }
            }

            // commit the transaction
            try {
                TransactionUtil.commit(beganTx);
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }

        return orderList;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.GridLayout(1, 0));

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    
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
}
