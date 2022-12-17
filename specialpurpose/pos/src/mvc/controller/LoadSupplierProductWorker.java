/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.guiapp.xui.XuiSession;

import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.ProductPriceTypeList;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.SupplierProduct;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadSupplierProductWorker extends LoadBaseSwingWorker<SupplierProductComposite> {

    public LoadSupplierProductWorker(ListAdapterListModel<SupplierProductComposite> personListModel, XuiSession session, Map<String, Object> findOptionMap) {
        super(personListModel, findOptionMap);
        this.session = session;
    }

    @Override
    protected List<SupplierProductComposite> doInBackground() throws Exception {
        List<SupplierProductComposite> supplierProducts = new ArrayList<SupplierProductComposite>();
        Map<String, Object> result = null;
        Debug.logError("LoadSupplierProductWorker: doInBackground: start", module);        
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();
            try {

                result = session.getDispatcher().runSync("performFind",
                        UtilMisc.<String, Object>toMap("entityName", "SupplierProduct",
                                "inputFields", findOptionMap,
                                "userLogin", session.getUserLogin()));

                for (Map.Entry<String, Object> entryDept : result.entrySet()) {
                    if (entryDept.getValue() == null) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + "Null", module);
                    } else {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                }

            } catch (GenericServiceException ex) {
                Debug.logError(ex, module);
            }

            EntityListIterator listIt = (EntityListIterator) result.get("listIt");
            if (listIt != null) {
                try {
                    List<GenericValue> list = listIt.getCompleteList();
                    maxProgress = list.size() + 1;
//                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
                    for (GenericValue gv : list) {

                        SupplierProductComposite supplierProductComp = new SupplierProductComposite();
                        SupplierProduct price = new SupplierProduct(gv);
                        supplierProductComp.setSupplierProduct(price);
                        int prograss = calcProgress(progressedItems + 1);

//                        if ((prograss + 1) % 2 == 0) {
                            publish(supplierProductComp);
  //                      } else {
   //                         progressedItems++;
   //                     }

                        if (isCancelled()) {
                            break;
                        }
                    }

                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                } finally {
                    try {
                        listIt.close();
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                    }
                }
            }

        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
//                pos.showMessageDialog("dialog/error/exception", e2.getMessage());
            }
//            pos.showMessageDialog("dialog/error/exception", e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
//                pos.showMessageDialog("dialog/error/exception", e.getMessage());
            }
        }
        Debug.logError("LoadSupplierProductWorker: doInBackground: end", module);        
        return supplierProducts;
    }

    static ProductPriceTypeList pptArray = null;

    static public SupplierProductList getSupplierProductListByProduct(String productId, XuiSession session) throws GenericEntityException {
        SupplierProductList spl = new SupplierProductList();
        //get the price list
        List<GenericValue> priceList = PosProductHelper.getSupplierProduct(productId, session.getDelegator());

        spl.createAllElement(priceList);

        return spl;

    }

    static public SupplierProductList getSupplierProductListByParty(String partyId, XuiSession session) throws GenericEntityException {
        SupplierProductList spl = new SupplierProductList();
        //get the price list
        List<GenericValue> priceList = PosProductHelper.getSupplierProductByParty(partyId, session.getDelegator());

        spl.createAllElement(priceList);

        return spl;
    }

    static public SupplierProductList getProductWithoutSupplierProductList(XuiSession session) throws GenericEntityException {
        SupplierProductList spl = new SupplierProductList();
        List<Product> productList = ProductSingleton.getValueList();
        for (Product prod : productList) {
            List<GenericValue> supplierProdList = PosProductHelper.getSupplierProduct(prod.getproductId(), session.getDelegator());
            if (UtilValidate.isEmpty(supplierProdList)) {
                SupplierProductComposite val = createSupplierProduct(prod.getproductId(), null,  session);
                spl.add(val);
            }
        }
        return spl;
    }

    static public SupplierProduct getSupplierProduct(String productId, String partyId, String currencyUom, XuiSession session) {
        SupplierProduct supplierProduct = null;

        Map<String, Object> params = UtilMisc.<String, Object>toMap("productId", productId,
                "partyId", partyId,
                "currencyUomId", currencyUom);
        try {
            Map<String, Object> result = session.getDispatcher().runSync("getSuppliersForProduct", params);
            List<GenericValue> productSuppliers = UtilGenerics.checkList(result.get("supplierProducts"));
            if ((productSuppliers != null) && (productSuppliers.size() > 0)) {
                supplierProduct = new SupplierProduct(productSuppliers.get(0));
            }
            //} catch (GenericServiceException e) {
        } catch (Exception e) {
            Debug.logWarning(UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
        }
        return supplierProduct;
    }

    static public SupplierProductList getSupplierProductList(String productId, String partyId, String currencyUom, XuiSession session) {
        SupplierProductList spl = new SupplierProductList();
        Map<String, Object> params = UtilMisc.<String, Object>toMap(
                "productId", productId,
                "partyId", partyId,
                "currencyUomId", currencyUom);
        try {
            Debug.logInfo("productId: " + productId + " partyId: " + partyId + " currencyUomId: " + currencyUom , module);
            Map<String, Object> result = session.getDispatcher().runSync("getSuppliersForProduct", params);
            List<GenericValue> productSuppliers = UtilGenerics.checkList(result.get("supplierProducts"));
            if ((productSuppliers != null) && (productSuppliers.size() > 0)) {
                spl.createAllElement(productSuppliers);
            }
            
            for (Map.Entry<String, Object> entryDept : result.entrySet()) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }            
            //} catch (GenericServiceException e) {
        } catch (Exception e) {
            Debug.logWarning(UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
        }
        return spl;
    }
    
    static public GenericValue saveSupplierProduct(SupplierProductComposite supplier, XuiSession session) {
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        GenericValue supplierProduct = null;
        Map<String, Object> params = UtilMisc.<String, Object>toMap("productId", supplier.getSupplierProduct().getproductId(),
                "partyId", supplier.getSupplierProduct().getpartyId(),
                "currencyUomId", supplier.getSupplierProduct().getcurrencyUomId());
        try {
            Map<String, Object> result = session.getDispatcher().runSync("getSuppliersForProduct", params);
            List<GenericValue> productSuppliers = UtilGenerics.checkList(result.get("supplierProducts"));
            if ((productSuppliers != null) && (productSuppliers.size() > 0)) {
                supplierProduct = productSuppliers.get(0);
            }
            //} catch (GenericServiceException e) {
        } catch (Exception e) {
            Debug.logWarning(UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
        }

        if (supplierProduct == null) {
            System.out.println("Suppier product found");
            try {
                Map<String, Object> toStore = supplier.getSupplierProduct().getValuesMap();
                toStore.put("userLogin", userLogin);
                toStore.put("locale", locale);

                resultMap = session.getDispatcher().runSync("createSupplierProduct", toStore);
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
                //              supplier.getSupplierProduct().
//                System.out.println("create Suppier product");
                //                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
            } catch (Exception exc) {
                Debug.logError(exc, module);
            }

        } else {
            System.out.println("Suppier product found");
            try {
                Map<String, Object> toStore = supplier.getSupplierProduct().getValuesMap();
                toStore.put("userLogin", userLogin);
                toStore.put("locale", locale);

                resultMap = session.getDispatcher().runSync("updateSupplierProduct", toStore);
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
                System.out.println("Update Suppier product");
                //                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
            } catch (Exception exc) {
                Debug.logError(exc, module);
            }
        }
        return supplierProduct;
    }

    static public SupplierProductComposite createSupplierProduct(String productId, String partyId, XuiSession session) {
        SupplierProductComposite suppProdComp = new SupplierProductComposite();
        SupplierProduct supplierProduct = new SupplierProduct();
        suppProdComp.setSupplierProduct(supplierProduct);

        supplierProduct.setproductId(productId);
        supplierProduct.setpartyId(partyId);
//        supplierProduct.setcurrencyUomId(session.get);
        supplierProduct.setavailableThruDate(null);
        //                    supplierProduct.setsupplierPrefOrderId(productId);
        supplierProduct.setsupplierProductId(productId);
        supplierProduct.setcomments("");

        return suppProdComp;
    }
}
