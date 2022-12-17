/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.ProductPriceTypeList;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadProductPricesFromFileWorker extends SwingWorker<List<ProductPriceComposite>, ProductPriceComposite> {
    public static final String module = LoadProductPricesFromFileWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<ProductPriceComposite> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session = null;
    private String csvFile;

    public LoadProductPricesFromFileWorker(ListAdapterListModel<ProductPriceComposite> personListModel, String fileName, XuiSession session) {
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
    protected List<ProductPriceComposite> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<ProductPriceComposite> persons = new ArrayList<ProductPriceComposite>();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String fieldId[] = {
            "PRODUCT_ID",
            "PRODUCT_NAME",
            "UNIT_PRICE",
            "UNIT_COST",};

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

            List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                Map<String, String> maps = new HashMap<String, String>();

                // use comma as separator
                String[] details = line.split(cvsSplitBy);
                for (int i = 0; i < fieldId.length && i < details.length; ++i) {

                    maps.put(fieldId[i], details[i]);

                }
                listMap.add(maps);
            }

            personListModel.clear();
            maxProgress = listMap.size() * 3;

            for (Map<String, String> map : listMap) {

                String productId = map.get("PRODUCT_ID");
//                person3.setFirstname(productId);
//                person3.setLastname(map.get("PRODUCT_NAME"));

//                person3.setAddress(address3);
                GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                if (product != null) {

                    try {
                        List<GenericValue> priceList = PosProductHelper.getProductPriceByProductPriceType(productId, "LIST_PRICE", session.getDelegator());
                        if (priceList.isEmpty()) {
                            ProductPrice prodprice = new ProductPrice();
                            prodprice.setproductId(productId);
                            prodprice.setprice(new BigDecimal(map.get("UNIT_PRICE")));
                            prodprice.setproductPriceTypeId("LIST_PRICE");
                            prodprice.setproductPricePurposeId("PURCHASE");
                            prodprice.setcurrencyUomId("AUD");
                            prodprice.setproductStoreGroupId("_NA_");
                            LoadProductPriceWorker.saveProductPrice(prodprice, session);
                            ProductPriceComposite priceComposite = new ProductPriceComposite();
                            priceComposite.setProductPrice(prodprice);
                            persons.add(priceComposite);
                            publish(priceComposite);
                            sleepAWhile();
                        }

                        priceList = PosProductHelper.getProductPriceByProductPriceType(productId, "DEFAULT_PRICE", session.getDelegator());
                        if (priceList.isEmpty()) {
                            ProductPrice prodprice = new ProductPrice();
                            prodprice.setproductId(productId);
                            prodprice.setprice(new BigDecimal(map.get("UNIT_PRICE")));
                            prodprice.setproductPriceTypeId("DEFAULT_PRICE");
                            prodprice.setproductPricePurposeId("PURCHASE");
                            prodprice.setcurrencyUomId("AUD");
                            prodprice.setproductStoreGroupId("_NA_");
                            LoadProductPriceWorker.saveProductPrice(prodprice, session);
                            ProductPriceComposite priceComposite = new ProductPriceComposite();
                            priceComposite.setProductPrice(prodprice);
                            persons.add(priceComposite);
                            publish(priceComposite);
                            sleepAWhile();
                        }

                    } catch (Exception e) {
                        Debug.logError(e, module);
                    }

                    try {
                        List<GenericValue> priceList = PosProductHelper.getProductPriceByProductPriceType(productId, "AVERAGE_COST", session.getDelegator());
                        if (priceList.isEmpty()) {
                            ProductPrice prodprice = new ProductPrice();
                            prodprice.setproductId(productId);
                            prodprice.setprice(new BigDecimal(map.get("UNIT_COST")));
                            prodprice.setproductPriceTypeId("AVERAGE_COST");
                            prodprice.setproductPricePurposeId("PURCHASE");
                            prodprice.setcurrencyUomId("AUD");
                            prodprice.setproductStoreGroupId("_NA_");

                            LoadProductPriceWorker.saveProductPrice(prodprice, session);
                            ProductPriceComposite priceComposite = new ProductPriceComposite();
                            priceComposite.setProductPrice(prodprice);
                            persons.add(priceComposite);
                            publish(priceComposite);
                            sleepAWhile();
                        }

                    } catch (Exception e) {
                        Debug.logError(e, module);
                    }
                } else {
                    Debug.logInfo("Product Not Found: " + productId, module);
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
        return supplierProduct;
    }

    @Override
    protected void process(List<ProductPriceComposite> chunks) {
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
