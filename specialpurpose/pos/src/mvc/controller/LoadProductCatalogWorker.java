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
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProdCatalogCategory;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductStoreCatalog;
import org.ofbiz.ordermax.product.catalog.SaveProductCategoryAction;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author BBS Auctions
 */
public class LoadProductCatalogWorker extends LoadBaseSwingWorker<ProdCatalog> {

    public static final String module = LoadProductWorker.class.getName();

    public LoadProductCatalogWorker() {
        super();

        this.session = XuiContainer.getSession();

    }

    public LoadProductCatalogWorker(ListAdapterListModel<ProdCatalog> personListModel, XuiSession delegator, Map<String, Object> findOptionMap) {
        super(personListModel, findOptionMap);

        this.session = delegator;

    }

    @Override
    protected List<ProdCatalog> doInBackground() throws Exception {

        List<ProdCatalog> products = new ArrayList<ProdCatalog>();

        Map<String, Object> result = null;
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();
            try {

                result = session.getDispatcher().runSync("performFind",
                        UtilMisc.<String, Object>toMap("entityName", "ProdCatalog",
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

                        ProdCatalog productComp = new ProdCatalog();
//                        productComp.setProduct(new ProdCatalog(gv));
                        products.add(productComp);
//                        int prograss = calcProgress(progressedItems + 1);

//                        if ((prograss + 1) % 4 == 0) {
                        publish(productComp);
//                        } else {
//                            progressedItems++;
//                        }

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
        return products;

    }

    static public ProdCatalog loadProduct(String productCatalogId, XuiSession session) {
        ProdCatalog productComposite = null;
        try {
            GenericValue productGen = session.getDelegator().findByPrimaryKey("ProductCatalog", UtilMisc.toMap("productId", productCatalogId));
            if (productGen != null) {
                productComposite = new ProdCatalog();
            }

        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadProductCatalogWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productComposite;
    }

    static public ProdCatalog newProdCatalog() {

        ProdCatalog product = new ProdCatalog();
        product.setuseQuickAdd("Y");

        return product;
    }

    public void saveProdCatalogStore(ProductStoreCatalog productStoreCatalog, XuiSession session) throws Exception {
        try {
            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();
            System.out.println("1 Start product catalog: ");

            String prodCatalogId = productStoreCatalog.getprodCatalogId();
            System.out.println("Start product catalog: " + prodCatalogId);
            GenericValue prodCatalogGV = delegator.findByPrimaryKey("ProductStoreCatalog",
                    UtilMisc.toMap("prodCatalogId", prodCatalogId,
                            "productStoreId", productStoreCatalog.getproductStoreId(),
                            "fromDate", productStoreCatalog.getfromDate())
            );
            Map<String, Object> toStore = productStoreCatalog.getValuesMap();
            toStore.put("userLogin", userLogin);
            toStore.put("locale", locale);

            if (prodCatalogGV == null) {
                try {
                    System.out.println("Create product catalog: " + prodCatalogId);
                    resultMap = session.getDispatcher().runSync("createProductStoreCatalog", toStore);
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                    System.out.println("create Suppier product");
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                    throw exc;
                }

            } else {
                try {
                    System.out.println("Update product catalog: " + prodCatalogId);
                    resultMap = session.getDispatcher().runSync("updateProductStoreCatalog", toStore);
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }

//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                    throw exc;
                }

            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadProductCatalogWorker.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    public void saveProdCatalogCategory(ProdCatalogCategory prodCatalogCategory, XuiSession session) throws Exception {
        try {
            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();
            System.out.println("1 Start product catalog: ");

            String prodCatalogId = prodCatalogCategory.getprodCatalogId();
            System.out.println("Start product catalog: " + prodCatalogId);
            GenericValue prodCatalogGV = delegator.findByPrimaryKey("ProdCatalogCategory",
                    UtilMisc.toMap("prodCatalogId", prodCatalogId,
                            "productCategoryId", prodCatalogCategory.getproductCategoryId(),
                            "prodCatalogCategoryTypeId", prodCatalogCategory.getprodCatalogCategoryTypeId(),
                            "fromDate", prodCatalogCategory.getfromDate())
            );
            Map<String, Object> toStore = prodCatalogCategory.getValuesMap();
            toStore.put("userLogin", userLogin);
            toStore.put("locale", locale);

            if (prodCatalogGV == null) {
                try {
                    System.out.println("Create product catalog: " + prodCatalogId);
                    resultMap = session.getDispatcher().runSync("addProductCategoryToProdCatalog", toStore);
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                    System.out.println("create Suppier product");
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                    throw exc;
                }

            } else {
                try {
                    System.out.println("Update product catalog: " + prodCatalogId);
                    resultMap = session.getDispatcher().runSync("updateProductCategoryToProdCatalog", toStore);
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }

//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                    throw exc;
                }

            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadProductCatalogWorker.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    public static void saveCategory(ProductCategory productCategory, XuiSession session) {
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        System.out.println("1 Start product catalog: ");
        String productCategoryId;
        try {

            productCategoryId = productCategory.getProductCategoryId();
            System.out.println("Start product catalog: " + productCategoryId);
            GenericValue productCategoryGV = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId));
            Map<String, Object> toStore = productCategory.getValuesMap();
            toStore.put("userLogin", userLogin);
            toStore.put("locale", locale);

            if (productCategoryGV == null) {
                try {
                    System.out.println("Create product catalog: " + productCategoryId);
                    resultMap = session.getDispatcher().runSync("createProductCategory", toStore);
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
                    System.out.println("Update product catalog: " + productCategoryId);
                    resultMap = session.getDispatcher().runSync("updateProductCategory", toStore);
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }

                    //                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                }

            }

        } catch (GenericEntityException ex) {
            Logger.getLogger(SaveProductCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deleteCategory(ProductCategory productCategory, XuiSession session) {
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        System.out.println("1 Start product catalog: ");
        String productCategoryId;
        try {

            productCategoryId = productCategory.getProductCategoryId();
            System.out.println("Start product catalog: " + productCategoryId);
            GenericValue productCategoryGV = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId));
            Map<String, Object> toStore = productCategory.getValuesMap();
            toStore.put("userLogin", userLogin);
            toStore.put("locale", locale);

            if (productCategoryGV != null) {
                try {
/*                    System.out.println("Create product catalog: " + productCategoryId);
                    resultMap = session.getDispatcher().runSync("createProductCategory", toStore);
                    for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                    System.out.println("create Suppier product");
//                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
        */
                    productCategoryGV.remove();
                } catch (Exception exc) {
                    Debug.logError(exc, module);
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(SaveProductCategoryAction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
