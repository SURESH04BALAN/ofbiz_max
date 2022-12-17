/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import static mvc.controller.LoadOrderWorker.getOrderList;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.OrderSummaryView;
import org.ofbiz.ordermax.entity.SalesRegister;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class SalesRegistryWorker extends SwingWorker<List<SalesRegister>, SalesRegister> {

    public static final String module = SalesRegistryWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<SalesRegister> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session = null;

    public SalesRegistryWorker(ListAdapterListModel<SalesRegister> personListModel, XuiSession session) {
        this.personListModel = personListModel;
        this.session = session;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    private String partyId = "";

    @Override
    protected List<SalesRegister> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<SalesRegister> accounts = new ArrayList<SalesRegister>();
        return accounts;
    }

    @Override
    protected void process(List<SalesRegister> chunks) {
        /*            try{
         throw new Exception("process(List<Person> chunks)");
         }
         catch(Exception e){
         e.printStackTrace();
         }
         */
        personListModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    private void sleepAWhile() {
        try {
            Thread.yield();
            long timeToSleep = getTimeToSleep();
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
        }
    }

    private long getTimeToSleep() {
        return loadPersonsSpeedModel.getValue();
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;

    }

    public static SalesRegister getSalesRegister(String trxId, XuiSession session) {
        SalesRegister salesRegister = null;
        Delegator delegator = session.getDelegator();
        Map<String, Object> whereClauseMap = UtilMisc.toMap("trxId", trxId);
        List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("SalesRegister", whereClauseMap, null, delegator);
        if (valueList != null && !valueList.isEmpty()) {
            salesRegister = new SalesRegister(valueList.get(0));
        }
        return salesRegister;
    }

    public static void createOrUpdateSalesRegister(SalesRegister salesRegister, XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        Delegator delegator = session.getDelegator();
        GenericValue partyGenric = null;
        String partyId = salesRegister.getPartyId();
        String trxTypeId = salesRegister.getTrxTypeId();
        String relatedId = salesRegister.getRelatedId();
        boolean isUpdate = false;
        //try {
        Map<String, Object> whereClauseMap = UtilMisc.toMap("partyId", partyId, "trxTypeId", trxTypeId, "relatedId", relatedId);
        List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("SalesRegister", whereClauseMap, null, delegator);
        if (valueList != null) {
            isUpdate = !valueList.isEmpty();
        }
        //        delegator.findOne("SalesRegister", ,false);

        Debug.logInfo("createOrUpdatePartyIdentification... partyId: " + partyId + "  trxTypeId: " + trxTypeId + " relatedId: " + relatedId, module);
        if (!isUpdate) {

            try {

                //Debug.logInfo("Supplier with ID [" + supplierPartyId + "] not found, will be creating it", module);
                String registerId = delegator.getNextSeqId("SalesRegister");// "9009";
                salesRegister.setTrxId(registerId);
                Map<String, Object> map = salesRegister.getValuesMap();
                TransactionUtil.begin();
                delegator.create("SalesRegister", map);
                TransactionUtil.commit();
            } catch (GenericTransactionException ex) {
                Logger.getLogger(SalesRegistryWorker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GenericEntityException ex) {
                Logger.getLogger(SalesRegistryWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try{
                //String registerId = delegator.getNextSeqId("SalesRegister");// "9009";
               // salesRegister.setTrxId(registerId);
               // Map<String, Object> map = salesRegister.getGenericValueAsMap();
                TransactionUtil.begin();

                delegator.store(salesRegister.getGenericValueObj());
                TransactionUtil.commit();
            } catch (GenericTransactionException ex) {
                Logger.getLogger(SalesRegistryWorker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GenericEntityException ex) {
                Logger.getLogger(SalesRegistryWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    static public List<OrderSummaryView> getSalesRegistarSummary(XuiSession session, List<EntityCondition> entityConditionList) {
        List<OrderSummaryView> listModel = new ArrayList<OrderSummaryView>();

        List<GenericValue> resultList = getOrderList(session, entityConditionList);
        String entityName = "SalesRegister";
        Delegator delegator = session.getDelegator();
        String orderBy = "relatedId";
        List<GenericValue> genList = null;

        Map<String, Object> whereClauseMap = null;
       // List<EntityCondition> entityConditionList = null;

        // entityConditionList = (List<EntityCondition>) collectionMap.get(EntityConditionList);
        genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, entityConditionList, orderBy, delegator);
        for (GenericValue gvOrder : genList) {
            SalesRegister invoiceComp = new SalesRegister(gvOrder);
            OrderSummaryView orderSummaryView = new OrderSummaryView();
            if (invoiceComp.getTrxId() != null) {
                Debug.logInfo(" invoiceComp.getTrxId(): " + invoiceComp.getTrxId(), " ");
                //trxId
                orderSummaryView.setTrxId(invoiceComp.getTrxId());
                orderSummaryView.setPartyId(invoiceComp.getPartyId());
                orderSummaryView.setNumber(invoiceComp.getRelatedId());
                orderSummaryView.setDetails(invoiceComp.getTrxId());
                try {
                    orderSummaryView.setType(InvoiceTypeSingleton.getInvoiceType(invoiceComp.getTrxTypeId()).getdescription());
                } catch (Exception ex) {
                    Logger.getLogger(SalesRegistryWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
                //orderSummaryView.setReference(invoiceComp.getInvoice().getreferenceNumber());
                orderSummaryView.setDate(invoiceComp.getTrxDate());
                orderSummaryView.setLineItemType(OrderSummaryView.LineItemType.InvoiceLineItem);

                orderSummaryView.setAmount(invoiceComp.getAmount());
                //orderSummaryView.setAmount(invoiceComp.getTotalAmount());
            }

            listModel.add(orderSummaryView);
        }
        return listModel;
    }

}
