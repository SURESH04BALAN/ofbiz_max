/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

/**
 *
 * @author siranjeev
 */
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.controller.LoadProductCatalogWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryRollup;
import org.ofbiz.ordermax.product.catalog.SaveProductCategoryAction;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadProductFromFileWorker extends SwingWorker<List<ProductComposite>, ProductComposite> {

    public static final String module = LoadProductFromFileWorker.class.getName();
    protected volatile int maxProgress;
    protected int progressedItems;
    protected ListAdapterListModel<ProductComposite> personListModel;
    protected BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    protected String csvFile;
    protected XuiSession session = null;

    public LoadProductFromFileWorker(ListAdapterListModel<ProductComposite> personListModel, String fileName, XuiSession delegator) {
        this.personListModel = personListModel;
        csvFile = fileName;
        this.session = delegator;

    }
    static String ultimateRoot = "CATEGORY_ROOT";

    static public void createCategories(Map<String, String> categories, XuiSession session) {

        ProductCategory rootCategory = new ProductCategory();
        String rootCategoryId = ultimateRoot.substring(0, ultimateRoot.length() < 20 ? ultimateRoot.length() : 19);
        rootCategory.setProductCategoryId(rootCategoryId);
        rootCategory.setCategoryName(ultimateRoot);
        rootCategory.setDescription(ultimateRoot);
        rootCategory.setLongDescription(ultimateRoot);
//        rootCategory.setprimaryParentCategoryId(null);
        rootCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");
        //save the parent
        LoadProductCatalogWorker.saveCategory(rootCategory, session);

        for (Map.Entry<String, String> entry : categories.entrySet()) {

            ProductCategory productCategory = new ProductCategory();
            String pCategoryId = entry.getValue().substring(0, entry.getValue().length() < 20 ? entry.getValue().length() : 19);
            productCategory.setProductCategoryId(pCategoryId);
            productCategory.setCategoryName(entry.getValue());
            productCategory.setDescription(entry.getValue());
            productCategory.setLongDescription(entry.getValue());
            productCategory.setPrimaryParentCategoryId(rootCategoryId);
            productCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");

            //save the parent
            LoadProductCatalogWorker.saveCategory(productCategory, session);

            ProductCategoryRollup productCategoryRollup = new ProductCategoryRollup();
            productCategoryRollup.setproductCategoryId(pCategoryId);
            productCategoryRollup.setparentProductCategoryId(rootCategoryId);
            SaveProductCategoryAction.addProductCategoryToCategory(productCategoryRollup, session);

            String cCategoryId = entry.getKey().substring(0, entry.getKey().length() < 20 ? entry.getKey().length() : 19);
            ProductCategory productCategoryChild = new ProductCategory();
            productCategoryChild.setProductCategoryId(cCategoryId);
            productCategoryChild.setCategoryName(entry.getKey());
            productCategoryChild.setDescription(entry.getKey());
            productCategoryChild.setLongDescription(entry.getKey());
            productCategoryChild.setPrimaryParentCategoryId(productCategory.getProductCategoryId());
            productCategoryChild.setProductCategoryTypeId("INTERNAL_CATEGORY");
            //save the parent
            LoadProductCatalogWorker.saveCategory(productCategoryChild, session);
            ProductCategoryRollup childCategoryRollup = new ProductCategoryRollup();
            childCategoryRollup.setproductCategoryId(cCategoryId);
            childCategoryRollup.setparentProductCategoryId(productCategory.getProductCategoryId());
            SaveProductCategoryAction.addProductCategoryToCategory(childCategoryRollup, session);

        }

    }

    static ProductCategory rootCategory = null;

    static public void createCategory(org.ofbiz.ordermax.entity.Product product, XuiSession session) {
        String parentCategoryId = null;
        if (rootCategory == null) {
            rootCategory = new ProductCategory();
            parentCategoryId = ultimateRoot.substring(0, ultimateRoot.length() < 20 ? ultimateRoot.length() : 19);
            rootCategory.setProductCategoryId(parentCategoryId);
            rootCategory.setCategoryName(ultimateRoot);
            rootCategory.setDescription(ultimateRoot);
            rootCategory.setLongDescription(ultimateRoot);
//        rootCategory.setprimaryParentCategoryId(null);
            rootCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");
            //save the parent
            LoadProductCatalogWorker.saveCategory(rootCategory, session);
        } else {
            parentCategoryId = rootCategory.getProductCategoryId();
        }

        if (UtilValidate.isNotEmpty(product.getdepartmentName())) {
            ProductCategory deptCategory = new ProductCategory();
            parentCategoryId = product.getdepartmentName().substring(0, product.getdepartmentName().length() < 20 ? product.getdepartmentName().length() : 19);
            deptCategory.setProductCategoryId(parentCategoryId);
            deptCategory.setCategoryName(ultimateRoot);
            deptCategory.setDescription(ultimateRoot);
            deptCategory.setLongDescription(ultimateRoot);
            deptCategory.setPrimaryParentCategoryId(rootCategory.getProductCategoryId());
            deptCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");
            //save the parent
            LoadProductCatalogWorker.saveCategory(deptCategory, session);

            ProductCategoryRollup productCategoryRollup = new ProductCategoryRollup();
            productCategoryRollup.setproductCategoryId(parentCategoryId);
            productCategoryRollup.setparentProductCategoryId(rootCategory.getProductCategoryId());
            SaveProductCategoryAction.addProductCategoryToCategory(productCategoryRollup, session);
        }

        ProductCategory brandCategory = new ProductCategory();
        String brandCategoryId = product.getbrandName().substring(0, product.getbrandName().length() < 20 ? product.getbrandName().length() : 19);
        brandCategory.setProductCategoryId(brandCategoryId);
        brandCategory.setCategoryName(product.getbrandName());
        brandCategory.setDescription(product.getbrandName());
        brandCategory.setLongDescription(product.getbrandName());
        brandCategory.setPrimaryParentCategoryId(parentCategoryId);
        brandCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");

        //save the parent
        LoadProductCatalogWorker.saveCategory(brandCategory, session);        
        ProductCategoryRollup childCategoryRollup = new ProductCategoryRollup();
        childCategoryRollup.setproductCategoryId(brandCategoryId);
        childCategoryRollup.setparentProductCategoryId(parentCategoryId);
        SaveProductCategoryAction.addProductCategoryToCategory(childCategoryRollup, session);

    }

    @Override
    protected List<ProductComposite> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<ProductComposite> persons = new ArrayList<ProductComposite>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

//        fieldMap.addAll(fields)
        String fieldId[] = {
            "PRODUCT_ID", "PRODUCT_TYPE_ID", "INTERNAL_NAME", "BRAND_NAME",
            "COMMENTS", "PRODUCT_NAME", "DESCRIPTION", "LONG_DESCRIPTION",
            "REQUIRE_INVENTORY", "QUANTITY_UOM_ID", "REQUIRE_AMOUNT", "TAXABLE",
            "IS_VIRTUAL", "IS_VARIANT", "PRIMARY_PRODUCT_CATEGORY_ID", "MANUFACTURER_PARTY_ID", "FACILITY_ID",
            "INTRODUCTION_DATE", "RELEASE_DATE", "SUPPORT_DISCONTINUATION_DATE",
            "SALES_DISCONTINUATION_DATE", "SALES_DISC_WHEN_NOT_AVAIL",
            "PRICE_DETAIL_TEXT", "SMALL_IMAGE_URL", "MEDIUM_IMAGE_URL", "LARGE_IMAGE_URL",
            "DETAIL_IMAGE_URL", "ORIGINAL_IMAGE_URL", "DETAIL_SCREEN", "INVENTORY_MESSAGE",
            "QUANTITY_INCLUDED", "PIECES_INCLUDED", "FIXED_AMOUNT", "AMOUNT_UOM_TYPE_ID",
            "WEIGHT_UOM_ID", "WEIGHT", "PRODUCT_WEIGHT", "HEIGHT_UOM_ID", "PRODUCT_HEIGHT",
            "SHIPPING_HEIGHT", "WIDTH_UOM_ID", "PRODUCT_WIDTH", "SHIPPING_WIDTH", "DEPTH_UOM_ID",
            "PRODUCT_DEPTH", "SHIPPING_DEPTH", "DIAMETER_UOM_ID", "PRODUCT_DIAMETER",
            "PRODUCT_RATING", "RATING_TYPE_ENUM", "RETURNABLE", "CHARGE_SHIPPING",
            "AUTO_CREATE_KEYWORDS", "INCLUDE_IN_PROMOTIONS", "VIRTUAL_VARIANT_METHOD_ENUM",
            "ORIGIN_GEO_ID", "REQUIREMENT_METHOD_ENUM_ID", "BILL_OF_MATERIAL_LEVEL",
            "RESERV_MAX_PERSONS", "RESERV2ND_P_P_PERC", "RESERV_NTH_P_P_PERC", "CONFIG_ID",
            "CREATED_DATE", "CREATED_BY_USER_LOGIN", "LAST_MODIFIED_DATE",
            "LAST_MODIFIED_BY_USER_LOGIN", "IN_SHIPPING_BOX", "DEFAULT_SHIPMENT_BOX_TYPE_ID",
            "ORDER_DECIMAL_QUANTITY", "LAST_UPDATED_STAMP", "LAST_UPDATED_TX_STAMP",
            "CREATED_STAMP", "CREATED_TX_STAMP", "LOT_ID_FILLED_IN", };

        try {

            try {
                Reader csvFileIo = null;
                try {

                    csvFileIo = new BufferedReader(new FileReader(csvFile));//new InputStreamReader(is);
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
                List<org.ofbiz.ordermax.entity.Product> personsRead = null;
                try {

                    CSVReader<org.ofbiz.ordermax.entity.Product> personReader = new CSVReaderBuilder<org.ofbiz.ordermax.entity.Product>(csvFileIo).entryParser(new ProductEntryParser()).build();
                    personsRead = personReader.readAll();
                } catch (Exception e) {
                    Debug.logError(e, "Unable to rollback transaction", module);
                }

                maxProgress = personsRead.size();

                Map<String, String> categories = new HashMap<String, String>();
                for (org.ofbiz.ordermax.entity.Product prod : personsRead) {
                    String catId = prod.getprimaryProductCategoryId();
                    if (UtilValidate.isNotEmpty(catId)) {
                        categories.put(prod.getbrandName(), prod.getprimaryProductCategoryId());
                    }
                }

                System.out.println("Start create product");
                personListModel.clear();
                for (org.ofbiz.ordermax.entity.Product product : personsRead) {
                    
                    createCategory(product,session);
                    
                    Map<String, Object> valueMap = product.getValuesMap();
                    String productId = product.getproductId();
                    String brand = product.getbrandName();
                    String productCategoryId = brand.substring(0, brand.length() < 20 ? brand.length() : 19);
//                String productCategoryId = map.get("PRIMARY_PRODUCT_CATEGORY_ID");
                    System.out.println("Start create product productId: " + productId);
                    ProductComposite productComposite = new ProductComposite();

                    productComposite.setProduct(product);
                    persons.add(productComposite);
                    publish(productComposite);
                    sleepAWhile();

                    GenericValue productCategory = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId));
                    GenericValue productGV = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                    if (productGV == null && productCategory != null) {
                        try {
                            valueMap.put("userLogin", userLogin);
                            valueMap.put("locale", locale);
                            valueMap.put("productCategoryId", productCategoryId);

                            Map<String, Object> featureMap = new HashMap<String, Object>();
                            featureMap.put("productFeatureId", "IMAGE_BOARD");
                            featureMap.put("productFeatureTypeId", "SIZE");
                            featureMap.put("productFeatureApplTypeId", "STANDARD_FEATURE");
                            //                        featureMap.put("productFeatureSelectableByType", "STANDARD_FEATURE");

                            valueMap.put("productFeatureIdByType", featureMap);

                            Map<String, Object> featureMapSel = new HashMap<String, Object>();
                            featureMapSel.put("productFeatureId", "IMAGE_BOARD");
                            featureMapSel.put("productFeatureApplTypeId", "STANDARD_FEATURE");
                            //                        featureMapSel.put("productFeatureSelectableByType", "STANDARD_FEATURE");                            
                            valueMap.put("productFeatureSelectableByType", featureMapSel);
                            //                        featureMapSel
                            boolean beganTransaction = false;
                            try {
                                beganTransaction = TransactionUtil.begin();

                                resultMap = session.getDispatcher().runSync("createProductInCategory", valueMap);
                                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                                }
                                System.out.println("create Suppier product");
                            } catch (GenericTransactionException e) {
                                Debug.logError(e, module);
                                try {
                                    TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
                                } catch (GenericTransactionException e2) {
                                    Debug.logError(e2, "Unable to rollback transaction", module);
                                }
                            } finally {
                                try {
                                    TransactionUtil.commit(beganTransaction);
                                } catch (GenericTransactionException e) {
                                    Debug.logError(e, "Unable to commit transaction", module);
                                }
                            }
                        } catch (Exception exc) {
                            Debug.logError(exc, module);
                            break;
                        }
                    }

                }
            } catch (Exception e) {
                Debug.logError(e, "Unable to rollback transaction", module);
            }


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

    static protected Map<String, Object> getProductMap(Map<String, String> map) {

        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productId", map.get("PRODUCT_ID"));
        valueMap.put("productTypeId", map.get("PRODUCT_TYPE_ID"));
        valueMap.put("primaryProductCategoryId", map.get("PRIMARY_PRODUCT_CATEGORY_ID"));
        valueMap.put("internalName", map.get("INTERNAL_NAME"));
        valueMap.put("brandName", map.get("BRAND_NAME"));
        valueMap.put("description", map.get("DESCRIPTION"));
        valueMap.put("longDescription", map.get("LONG_DESCRIPTION"));
        valueMap.put("productName", map.get("PRODUCT_NAME"));
        valueMap.put("comments", map.get("COMMENTS"));

        valueMap.put("longDescription", map.get("LONG_DESCRIPTION"));
        valueMap.put("smallImageUrl", map.get("SMALL_IMAGE_URL"));
        valueMap.put("mediumImageUrl", map.get("MEDIUM_IMAGE_URL"));
        valueMap.put("largeImageUrl", map.get("LARGE_IMAGE_URL"));

        valueMap.put("detailImageUrl", map.get("DETAIL_IMAGE_URL"));
        valueMap.put("originalImageUrl", map.get("ORIGINAL_IMAGE_URL"));
        valueMap.put("inventoryMessage", map.get("INVENTORY_MESSAGE"));
        valueMap.put("requireInventory", map.get("REQUIRE_INVENTORY"));
        valueMap.put("weightUomId", map.get("WEIGHT_UOM_ID"));
        valueMap.put("weight", map.get("WEIGHT"));
        valueMap.put("productWeight", map.get("PRODUCT_HEIGHT"));
        valueMap.put("returnable", map.get("RETURNABLE"));
        valueMap.put("taxable", map.get("TAXABLE"));
        valueMap.put("chargeShipping", map.get("CHARGE_SHIPPING"));
        valueMap.put("autoCreateKeywords", map.get("AUTO_CREATE_KEYWORDS"));
        valueMap.put("includeInPromotions", map.get("INCLUDE_IN_PROMOTIONS"));
        valueMap.put("isVirtual", map.get("IS_VIRTUAL"));
        valueMap.put("isVariant", map.get("IS_VARIANT"));
        valueMap.put("requirementMethodEnumId", map.get("REQUIREMENT_METHOD_ENUM_ID"));
        
        return valueMap;
    }

    @Override
    protected void process(List<ProductComposite> chunks) {
        personListModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    protected void sleepAWhile() {
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

    protected ClassLoader getClassLoader() {
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
