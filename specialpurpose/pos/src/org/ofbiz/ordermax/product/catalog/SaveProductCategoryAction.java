package org.ofbiz.ordermax.product.catalog;

import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import mvc.controller.LoadProductCatalogWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryRollup;
import org.ofbiz.ordermax.party.ProductCategoryInterface;
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
public class SaveProductCategoryAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
//    private ListAdapterListModel<ProductCategory> productCategoryListModel;
//    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;
    ProductCategoryInterface productCategoryInterface = null;
    public SaveProductCategoryAction(ProductCategoryInterface productCategoryInterface, XuiSession session) {
        this.productCategoryInterface = productCategoryInterface;
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
        String productCategoryId;
        ProductCategory productCategory = productCategoryInterface.getProductCategory();
//        for (ProductCategory productCategory : productCategoryListModel.getList()) {
            LoadProductCatalogWorker.saveCategory(productCategory, session);
//        }

    }

    
    //create rollup
    public static void addProductCategoryToCategory(ProductCategoryRollup productCategoryRollup, XuiSession session) {
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        System.out.println("addProductCategoryToCategory: " + productCategoryRollup.getproductCategoryId()
                + "getparentProductCategoryId: " + productCategoryRollup.getparentProductCategoryId());
        String productCategoryId;
        try {

            productCategoryId = productCategoryRollup.getproductCategoryId();
            System.out.println("Start product category rollup: " + productCategoryId);
            GenericValue productCategoryGV = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId));
            Map<String, Object> toStore = productCategoryRollup.getValuesMap();
            toStore.put("userLogin", userLogin);
            toStore.put("locale", locale);

            if (productCategoryGV != null
                    && productCategoryRollup.getparentProductCategoryId() != null
                    && productCategoryRollup.getparentProductCategoryId().isEmpty() == false) {
                GenericValue genValue = PosProductHelper.getProductCategoryRollup(productCategoryId, productCategoryRollup.getparentProductCategoryId(), session.getDelegator());
                if (genValue == null) {
                    try {
                        System.out.println("Create product catalog: " + productCategoryId);
                        resultMap = session.getDispatcher().runSync("addProductCategoryToCategory", toStore);
                        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                        }
                        System.out.println("create Suppier product");
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                    } catch (Exception exc) {
                        Debug.logError(exc, module);
                    }
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(SaveProductCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
