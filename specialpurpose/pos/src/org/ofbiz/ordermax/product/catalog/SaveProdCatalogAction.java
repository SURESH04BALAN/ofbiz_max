package org.ofbiz.ordermax.product.catalog;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.KeyStroke;
import mvc.controller.SwingWorkerPropertyChangeListener;
import mvc.data.Address;
import mvc.data.Person;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import static org.ofbiz.order.shoppingcart.ShoppingCart.resource_error;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.SupplierProduct;
import static org.ofbiz.party.party.PartyServices.module;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class SaveProdCatalogAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<ProdCatalog> prodCatalogListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;

    public SaveProdCatalogAction(ListAdapterListModel<ProdCatalog> prodCatalogList, XuiSession session) {
        this.prodCatalogListModel = prodCatalogList;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    private String partyId = "";

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Save";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('L');
        }
        return super.getValue(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        System.out.println("1 Start product catalog: ");
        String prodCatalogId;
        for (ProdCatalog prodCatalog : prodCatalogListModel.getList()) {

            try {

                prodCatalogId = prodCatalog.getprodCatalogId();
                System.out.println("Start product catalog: " + prodCatalogId);
                GenericValue prodCatalogGV = delegator.findByPrimaryKey("ProdCatalog", UtilMisc.toMap("prodCatalogId", prodCatalogId));
                Map<String, Object> toStore = prodCatalog.getValuesMap();
                toStore.put("userLogin", userLogin);
                toStore.put("locale", locale);

                if (prodCatalogGV == null) {
                    try {
                        System.out.println("Create product catalog: " + prodCatalogId);
                        resultMap = session.getDispatcher().runSync("createProdCatalog", toStore);
                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }
                        System.out.println("create Suppier product");
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                    } catch (Exception exc) {
                        Debug.logError(exc, module);
                    }

                } else {
                    try {
                        System.out.println("Update product catalog: " + prodCatalogId);
                        resultMap = session.getDispatcher().runSync("updateProdCatalog", toStore);
                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }

//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                    } catch (Exception exc) {
                        Debug.logError(exc, module);
                    }

                }
            } catch (GenericEntityException ex) {
                Logger.getLogger(SaveProdCatalogAction.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void addSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {

    }

    public void removeSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;
    }
}
