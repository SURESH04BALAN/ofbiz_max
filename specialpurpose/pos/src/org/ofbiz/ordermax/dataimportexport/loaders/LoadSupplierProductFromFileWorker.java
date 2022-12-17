/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.data.Address;
import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
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

import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.ProductPriceTypeList;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.SupplierProduct;
import org.ofbiz.ordermax.product.ProductSingleton;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadSupplierProductFromFileWorker extends SwingWorker<List<SupplierProductComposite>, SupplierProductComposite> {
    public static final String module = LoadSupplierProductFromFileWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<SupplierProductComposite> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session = null;
    private String csvFile;

    public LoadSupplierProductFromFileWorker(ListAdapterListModel<SupplierProductComposite> personListModel, String fileName, XuiSession session) {
//        super(personListModel, session);
        this.personListModel = personListModel;
        this.session = session;
        csvFile = fileName;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    private String partyId = "";

    @Override
    protected List<SupplierProductComposite> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<SupplierProductComposite> persons = new ArrayList<SupplierProductComposite>();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String fieldId[] = {
            "DATE",
            "PARTY_ID",
            "PARTY_NAME",
            "PRODUCT_ID",
            "PRODUCT_NAME",
            "REFERENCE",
            "QUANTITY",
            "COST", //            "FACTOR",
        //            "COST_PRICE",
        //            "VARIANCE",
        //            "TAX",
        //            "REF",
        };
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        Map<String, GenericValue> productNameMap = new HashMap<String, GenericValue>();
        List<GenericValue> productList = null;

        try {
            productList = delegator.findList("Product", null, null, null, null, false);
            for (GenericValue prod : productList) {
                productNameMap.put(prod.getString("internalName"), prod);
            }

        } catch (GenericEntityException e) {
            Debug.logError(e, module);
        }
        Debug.logInfo("csvFile: " + csvFile, module);
        try {

            List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                Map<String, String> maps = new HashMap<String, String>();

                // use comma as separator
                String[] details = line.split(cvsSplitBy);
                for (int i = 0; i < fieldId.length && i < details.length; ++i) {

                    maps.put(fieldId[i], details[i]);
                    Debug.logInfo("fieldId[i]: " + fieldId[i] + "details[i]: " + details[i], module);
                }
                listMap.add(maps);
            }

            personListModel.clear();
            maxProgress = listMap.size();
            List<Map<String, String>> uniqueListMap = new ArrayList<Map<String, String>>();
            Map<String, String> uniqueList = new HashMap<String, String>();
//            for (Map<String, String> map : listMap) {
//                if (uniqueList.containsKey(map.get("OLD_PRODUCT_ID")) == false) {
//                    for (Map.Entry<String, String> entry : map.entrySet()) {
//                        System.out.println("[ Key= " + entry.getKey() + " , Value="
//                                + entry.getValue() + "]");
//                    uniqueListMap.add(map);
//                    uniqueList.put(map.get("OLD_PRODUCT_ID"), map.get("PRODUCT_ID"));
//                }
//            }

            List<String> selectedField = Arrays.asList(fieldId);
//            GenerateCSV.generateCsvFile("C:\\backup\\Real_Data\\generated_unique_supplier_prod.csv", uniqueListMap, selectedField);

//            getClassLoader();
            //          final ClassLoader cl = this.getClassLoader();
            //          Thread.currentThread().setContextClassLoader(cl);
            //loop map
            for (Map<String, String> map : listMap) {

                String partyId = map.get("PARTY_ID");
                String productId = map.get("PRODUCT_ID");
                /*                String name = map.get("PRODUCT_NAME");

                 Person person3 = new Person();
                 person3.setFirstname(productId);
                 person3.setLastname(map.get("PRODUCT_NAME"));

                 Address address3 = new Address();
                 address3.setStreet(map.get("COST_PRICE"));
                 address3.setStreetNr(map.get("PARTY_NAME"));

                 address3.setCity(map.get("PARTY_NAME"));
                 address3.setZipCode(map.get("QUANTITY"));
                 person3.setAddress(address3);
                 publish(person3);
                 */
                GenericValue party = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));
                GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                if (party != null && product != null) {
                    SupplierProductComposite spc = new SupplierProductComposite();
                    SupplierProduct supplierProduct = new SupplierProduct();
                    supplierProduct.setpartyId(partyId);
                    supplierProduct.setproductId(productId);
                    supplierProduct.setcurrencyUomId("AUD");
                    supplierProduct.setavailableThruDate(null);
                    //                    supplierProduct.setsupplierPrefOrderId(productId);
                    supplierProduct.setsupplierProductId(productId);
                    supplierProduct.setcomments("initial load");
                    try {
                        supplierProduct.setlastPrice(new BigDecimal(map.get("COST")));
                    } catch (Exception e) {
                        supplierProduct.setlastPrice(BigDecimal.ZERO);
                    }
                    spc.setSupplierProduct(supplierProduct);
                    // Find a supplier for the product
                    String supplierPartyId = null;
                    try {
                        System.out.println("try to find Suppier product");
                        Map<String, Object> getSuppliersForProductResult = session.getDispatcher().runSync("getSuppliersForProduct",
                                UtilMisc.<String, Object>toMap("productId", productId,
                                        "partyId", partyId,
                                        "currencyUomId", "AUD"));

                        List<GenericValue> supplierProducts = UtilGenerics.checkList(getSuppliersForProductResult.get("supplierProducts"));

                        // Order suppliers by supplierPrefOrderId so that preferred suppliers are used first
                        GenericValue supplierProductGV = EntityUtil.getFirst(supplierProducts);
                        if (!UtilValidate.isEmpty(supplierProductGV)) {
                            supplierPartyId = supplierProductGV.getString("partyId");
                        }
                    } catch (Exception e) {
                        Debug.logWarning(UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
                    }

                    //create
                    if (supplierPartyId == null) {
                        GenericValue gevVal = saveSupplierProduct(spc, session);
                    }
                    publish(spc);
                } else {
                    //                   System.out.println("Not Found - [ Party= " + partyId + " , product =" + productId + "]");
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return persons;
    }

    public static boolean isSubsequence(String s, String t) {
        int M = s.length();
        if (M == 0) {
            return true;
        }

        int N = t.length();

        if (M > N) {
            return false;
        }

        if (s.charAt(0) == t.charAt(0)) {
            return isSubsequence(s.substring(1), t.substring(1));
        }
        return isSubsequence(s, t.substring(1));
    }

    static ProductPriceTypeList pptArray = null;

    static public SupplierProductList getSupplierProduct(String productId, XuiSession session) throws GenericEntityException {
        SupplierProductList spl = new SupplierProductList();
        //get the price list
        List<GenericValue> priceList = PosProductHelper.getSupplierProduct(productId, session.getDelegator());

        spl.createAllElement(priceList);

        return spl;

    }

    static public SupplierProductList getSupplierProductByParty(String partyId, XuiSession session) throws GenericEntityException {
        SupplierProductList spl = new SupplierProductList();
        //get the price list
        List<GenericValue> priceList = PosProductHelper.getSupplierProductByParty(partyId, session.getDelegator());

        spl.createAllElement(priceList);

        return spl;
    }

    static public SupplierProductList getProductWithoutSupplierProduct(XuiSession session) throws GenericEntityException {
        SupplierProductList spl = new SupplierProductList();
        List<Product> productList = ProductSingleton.getValueList();
        for (Product prod : productList) {
            List<GenericValue> supplierProdList = PosProductHelper.getSupplierProduct(prod.getproductId(), session.getDelegator());
            if (UtilValidate.isEmpty(supplierProdList)) {
                SupplierProductComposite val = newSupplierProduct(prod.getproductId());
                spl.add(val);
            }
        }
        return spl;
    }

    static public GenericValue getSupplierProduct(String productId, String partyId, String currencyUom, XuiSession session) {
        GenericValue supplierProduct = null;
        Map<String, Object> params = UtilMisc.<String, Object>toMap("productId", productId,
                "partyId", partyId,
                "currencyUomId", currencyUom);
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
        return supplierProduct;
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
            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }
            List<GenericValue> productSuppliers = UtilGenerics.checkList(result.get("supplierProducts"));
            if ((productSuppliers != null) && (productSuppliers.size() > 0)) {
                supplierProduct = productSuppliers.get(0);
            }
            //} catch (GenericServiceException e) {
        } catch (Exception e) {
            Debug.logWarning(UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
        }

        if (supplierProduct == null) {
            System.out.println("Suppier product not found");
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

    static public SupplierProductComposite newSupplierProduct(String productId) {
        SupplierProductComposite suppProdComp = new SupplierProductComposite();
        SupplierProduct supplierProduct = new SupplierProduct();
        suppProdComp.setSupplierProduct(supplierProduct);

        supplierProduct.setproductId(productId);
//        supplierProduct.setcurrencyUomId(session.get);
        supplierProduct.setavailableThruDate(null);
        //                    supplierProduct.setsupplierPrefOrderId(productId);
        supplierProduct.setsupplierProductId(productId);
        supplierProduct.setcomments("");

        return suppProdComp;
    }

    @Override
    protected void process(List<SupplierProductComposite> chunks) {
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

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

}
