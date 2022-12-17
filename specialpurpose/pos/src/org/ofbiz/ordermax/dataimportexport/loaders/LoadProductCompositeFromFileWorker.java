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
import mvc.controller.LoadAccountWorker;
import mvc.controller.LoadProductCatalogWorker;
import mvc.controller.LoadProductPriceWorker;
import mvc.controller.LoadProductWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryRollup;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.product.catalog.SaveProductCategoryAction;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadProductCompositeFromFileWorker extends SwingWorker<List<ProductComposite>, ProductComposite> {

    public static final String module = LoadProductCompositeFromFileWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<ProductComposite> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private String csvFile;
    XuiSession session = null;

    public LoadProductCompositeFromFileWorker(ListAdapterListModel<ProductComposite> personListModel, String fileName, XuiSession delegator) {
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

    static public void createSupplier(Map<String, String> suppliers, XuiSession session) {

        for (Map.Entry<String, String> entry : suppliers.entrySet()) {
            GenericValue gv = PosProductHelper.getParty(entry.getValue(), session.getDelegator());
            if (gv == null) {
                Account account = LoadAccountWorker.createNewEmptyAccount(session);
                account.getParty().setpartyId(entry.getValue());
                account.getPartyGroup().setpartyId(entry.getValue());
                account.getPartyGroup().setgroupName(entry.getKey());
                account.getPartyGroup().setgroupNameLocal(entry.getKey());
                LoadAccountWorker.saveAccount(account, false, session);
            }
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
            deptCategory.setCategoryName(product.getdepartmentName());
            deptCategory.setDescription(product.getdepartmentName());
            deptCategory.setLongDescription(product.getdepartmentName());
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

//        String fields[] = {
//            "DUPLICATE", "NEW_PRODUCT_ID", "NEW_NAME", "STRIPED", "PRODUCT_ID", "PRODUCT_TYPE_ID",
//            "INTERNAL_NAME", "BRAND_NAME","PRODUCT_NAME", "DESCRIPTION", "LONG_DESCRIPTION", };
        String fields[] = {
            "DUPLICATE", "NEW_PRODUCT_ID", "NEW_NAME", "STRIPED", "PRODUCT_ID", "PRODUCT_TYPE_ID", "PRIMARY_PRODUCT_CATEGORY_ID", "MANUFACTURER_PARTY_ID", "FACILITY_ID", "INTRODUCTION_DATE", "RELEASE_DATE", "SUPPORT_DISCONTINUATION_DATE", "SALES_DISCONTINUATION_DATE", "SALES_DISC_WHEN_NOT_AVAIL", "INTERNAL_NAME", "BRAND_NAME", "COMMENTS", "PRODUCT_NAME", "DESCRIPTION", "LONG_DESCRIPTION", "PRICE_DETAIL_TEXT", "SMALL_IMAGE_URL", "MEDIUM_IMAGE_URL", "LARGE_IMAGE_URL", "DETAIL_IMAGE_URL", "ORIGINAL_IMAGE_URL", "DETAIL_SCREEN", "INVENTORY_MESSAGE", "REQUIRE_INVENTORY", "QUANTITY_UOM_ID", "QUANTITY_INCLUDED", "PIECES_INCLUDED", "REQUIRE_AMOUNT", "FIXED_AMOUNT", "AMOUNT_UOM_TYPE_ID", "WEIGHT_UOM_ID", "WEIGHT", "PRODUCT_WEIGHT", "HEIGHT_UOM_ID", "PRODUCT_HEIGHT", "SHIPPING_HEIGHT", "WIDTH_UOM_ID", "PRODUCT_WIDTH", "SHIPPING_WIDTH", "DEPTH_UOM_ID", "PRODUCT_DEPTH", "SHIPPING_DEPTH", "DIAMETER_UOM_ID", "PRODUCT_DIAMETER", "PRODUCT_RATING", "RATING_TYPE_ENUM", "RETURNABLE", "TAXABLE", "CHARGE_SHIPPING", "AUTO_CREATE_KEYWORDS", "INCLUDE_IN_PROMOTIONS", "IS_VIRTUAL", "IS_VARIANT", "VIRTUAL_VARIANT_METHOD_ENUM", "ORIGIN_GEO_ID", "REQUIREMENT_METHOD_ENUM_ID", "BILL_OF_MATERIAL_LEVEL", "RESERV_MAX_PERSONS", "RESERV2ND_P_P_PERC", "RESERV_NTH_P_P_PERC", "CONFIG_ID", "CREATED_DATE", "CREATED_BY_USER_LOGIN", "LAST_MODIFIED_DATE", "LAST_MODIFIED_BY_USER_LOGIN", "IN_SHIPPING_BOX", "DEFAULT_SHIPMENT_BOX_TYPE_ID", "ORDER_DECIMAL_QUANTITY", "LAST_UPDATED_STAMP", "LAST_UPDATED_TX_STAMP", "CREATED_STAMP", "CREATED_TX_STAMP", "LOT_ID_FILLED_IN",};
        List<String> selectedField = Arrays.asList(fields);

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
            "CREATED_STAMP", "CREATED_TX_STAMP", "LOT_ID_FILLED_IN", 
        };

        try {

          
            try {
                Reader csvFileIo = null;
                try {

                    csvFileIo = new BufferedReader(new FileReader(csvFile));//new InputStreamReader(is);
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
                List<ProductComposite> personsRead = null;
                try {

                    CSVReader<ProductComposite> personReader = new CSVReaderBuilder<ProductComposite>(csvFileIo).entryParser(new ProductCompositeEntryParser()).build();
                    personsRead = personReader.readAll();
                } catch (Exception e) {
                    Debug.logError(e, "Unable to rollback transaction", module);
                }

                maxProgress = personsRead.size();
//                if(true) return new ArrayList<ProductComposite>();
                //create all suppliers
                String supplierId = "SUPPLIER";
                int i = 1;
                Map<String, String> supplierList = new HashMap<String, String>();
                for (ProductComposite prodComposite : personsRead) {
                     System.out.println("Start create product 1 ");
                    for (SupplierProductComposite suppProduct : prodComposite.getSupplierProductList().getList()) {
                         System.out.println("Start create product 2 ");
                        if (UtilValidate.isEmpty(suppProduct.getSupplierProduct().getpartyId())
                                && UtilValidate.isNotEmpty(suppProduct.getSupplierProduct().getcomments())) {
                             System.out.println("Start create product 3");
                            if (supplierList.containsKey(suppProduct.getSupplierProduct().getcomments()) == false) {
                                 System.out.println("Start create product 4");
                                supplierList.put(suppProduct.getSupplierProduct().getcomments(), supplierId + i++);
                            }
                        }
                    }
                }
                
                createSupplier(supplierList,  session);  

                Map<String, String> categories = new HashMap<String, String>();
                for (ProductComposite prodComposite : personsRead) {
                    String catId = prodComposite.getProduct().getprimaryProductCategoryId();
                    if (UtilValidate.isNotEmpty(catId)) {
                        categories.put(prodComposite.getProduct().getbrandName(), prodComposite.getProduct().getprimaryProductCategoryId());
                    }
                }

                System.out.println("Start create product");
                personListModel.clear();
                for (ProductComposite prodComposite : personsRead) {

                    createCategory(prodComposite.getProduct(), session);

                    Map<String, Object> valueMap = prodComposite.getProduct().getValuesMap();
                    String productId = prodComposite.getProduct().getproductId();
                    String brand = prodComposite.getProduct().getbrandName();
                    String productCategoryId = brand.substring(0, brand.length() < 20 ? brand.length() : 19);
//                String productCategoryId = map.get("PRIMARY_PRODUCT_CATEGORY_ID");
                    System.out.println("Start create product productId: " + productId);
//                    ProductComposite productComposite = new ProductComposite();

                    //                  productComposite.setProduct(product);
                    persons.add(prodComposite);
                    publish(prodComposite);
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
                                List<GoodIdentification> list = new ArrayList<GoodIdentification>();
                                for (GoodIdentification goodIdentification : prodComposite.getGoodIdentificationList().getList()) {
                                    if (goodIdentification != null
                                            && (goodIdentification.getproductId() != null && !goodIdentification.getproductId().isEmpty())) {
                                        if (!PosProductHelper.isDuplicateGoodIdentification(goodIdentification.getproductId(),
                                                goodIdentification.getidValue(), goodIdentification.getgoodIdentificationTypeId(), GoodIdentificationTypeSingleton.getSingletonSession().getDelegator())) {

                                            list.add(goodIdentification);
                                        }
                                    }
                                }
                                
                                LoadProductWorker.saveGoodIdentification(list, GoodIdentificationTypeSingleton.getSingletonSession());
                               
                                for (SupplierProductComposite supplierProductComp : prodComposite.getSupplierProductList().getList()) {

                                    if (UtilValidate.isEmpty(supplierProductComp.getSupplierProduct().getpartyId())
                                            && UtilValidate.isNotEmpty(supplierProductComp.getSupplierProduct().getcomments())) {
                                        if (supplierList.containsKey(supplierProductComp.getSupplierProduct().getcomments())) {
                                            supplierProductComp.getSupplierProduct().setpartyId(supplierList.get(supplierProductComp.getSupplierProduct().getcomments()));
                                        }
                                    }

                                    if (supplierProductComp != null
                                            && UtilValidate.isNotEmpty(supplierProductComp.getSupplierProduct().getproductId())
                                            && UtilValidate.isNotEmpty(supplierProductComp.getSupplierProduct().getpartyId())) {
                                        Debug.logInfo("btnSaveTelephoneActionPerformed productId: " + supplierProductComp.getSupplierProduct().getproductId()
                                                + " PartyId: " + supplierProductComp.getSupplierProduct().getpartyId(), module);
                                        LoadSupplierProductFromFileWorker.saveSupplierProduct(supplierProductComp, GoodIdentificationTypeSingleton.getSingletonSession());
                                    }
                                }

                                for (ProductPriceComposite productPriceComposite : prodComposite.getProductItemPrice().getProductPriceList().getList()) {

                                    if (productPriceComposite != null
                                            && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductId())
                                            && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductPricePurposeId())
                                            && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductPriceTypeId())
                                            && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getproductStoreGroupId())
                                            && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getcurrencyUomId())
                                            && UtilValidate.isNotEmpty(productPriceComposite.getProductPrice().getfromDate())) {

                                        try {
                                            Debug.logInfo("btnSaveTelephoneActionPerformed: ", "iSelectedIndex");
                                            LoadProductPriceWorker.saveProductPrice(productPriceComposite.getProductPrice(), GoodIdentificationTypeSingleton.getSingletonSession());

                                        } catch (Exception ex) {
                                        }
                                    } else {
                                        Debug.logInfo("getproductId: " + productPriceComposite.getProductPrice().getproductId(), "module");
                                        Debug.logInfo("getproductPricePurposeId: " + productPriceComposite.getProductPrice().getproductPricePurposeId(), "module");
                                        Debug.logInfo("getproductPriceTypeId: " + productPriceComposite.getProductPrice().getproductPriceTypeId(), "module");
                                        Debug.logInfo("getproductStoreGroupId: " + productPriceComposite.getProductPrice().getproductStoreGroupId(), "module");
                                        Debug.logInfo("getcurrencyUomId: " + productPriceComposite.getProductPrice().getcurrencyUomId(), "module");
                                        Debug.logInfo("getfromDate: " + productPriceComposite.getProductPrice().getfromDate().toString(), "module");
                                    }
                                }
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

//            maxProgress = listMap.size();
//            GenerateCSV.generateCsvFile("C:\\backup\\Real_Data\\generated_product.csv", listMap, selectedField);
            //if(true){
            //    throw new Exception("Hello");
            //}
            //loop map

            /*for (Map<String, String> map : listMap) {
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
             //                valueMap.put("weight", map.get("WEIGHT").isEmpty() ? BigDecimal.ZERO : new BigDecimal(map.get("WEIGHT")));
             //                valueMap.put("productHeight", map.get("PRODUCT_HEIGHT").isEmpty() ? BigDecimal.ZERO : new BigDecimal(map.get("PRODUCT_HEIGHT")));
             valueMap.put("returnable", map.get("RETURNABLE"));
             valueMap.put("taxable", map.get("TAXABLE"));
             valueMap.put("chargeShipping", map.get("CHARGE_SHIPPING"));
             valueMap.put("autoCreateKeywords", map.get("AUTO_CREATE_KEYWORDS"));
             valueMap.put("includeInPromotions", map.get("INCLUDE_IN_PROMOTIONS"));
             valueMap.put("isVirtual", map.get("IS_VIRTUAL"));
             valueMap.put("isVariant", map.get("IS_VARIANT"));
             valueMap.put("requirementMethodEnumId", map.get("REQUIREMENT_METHOD_ENUM_ID"));

             String productId = map.get("PRODUCT_ID");
             String brand = map.get("BRAND_NAME");
             String productCategoryId = brand.substring(0, brand.length() < 20 ? brand.length() : 19);
             //                String productCategoryId = map.get("PRIMARY_PRODUCT_CATEGORY_ID");
             System.out.println("Start create product productId: " + productId);
             ProductComposite productComposite = new ProductComposite();
             org.ofbiz.ordermax.entity.Product productVal = new org.ofbiz.ordermax.entity.Product();
             //                    supplierProduct.setpartyId(partyId);
             productVal.setproductId(productId);
             productVal.setinternalName(map.get("DESC"));
             productVal.setproductId(map.get("PRODUCT_ID"));
             productVal.setproductTypeId(map.get("PRODUCT_TYPE_ID"));
             productVal.setprimaryProductCategoryId(map.get("PRIMARY_PRODUCT_CATEGORY_ID"));
             productVal.setinternalName(map.get("INTERNAL_NAME"));
             productVal.setbrandName(map.get("BRAND_NAME"));
             productVal.setdescription(map.get("DESCRIPTION"));
             productVal.setlongDescription(map.get("LONG_DESCRIPTION"));
             productVal.setproductName(map.get("PRODUCT_NAME"));
             productVal.setcomments(map.get("COMMENTS"));
             productComposite.setProduct(productVal);
             persons.add(productComposite);
             publish(productComposite);
             sleepAWhile();

             GenericValue productCategory = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId));
             GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
             if (product == null && productCategory != null) {
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

             }*/
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

        /*
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

         //createProductInCategory 
         String partyId = "";
         String productId = "";
         GenericValue party = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));
         GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
         if (party != null && product != null) {

         SupplierProduct supplierProduct = new SupplierProduct();
         supplierProduct.setpartyId(partyId);
         supplierProduct.setproductId(productId);
         supplierProduct.setcurrencyUomId("AUD");
         supplierProduct.setavailableThruDate(null);
         //                    supplierProduct.setsupplierPrefOrderId(productId);
         supplierProduct.setsupplierProductId(productId);
         supplierProduct.setcomments("initial load");
         try {
         //                supplierProduct.setlastPrice(new BigDecimal(map.get("COST_PRICE")));
         } catch (Exception e) {
         supplierProduct.setlastPrice(BigDecimal.ZERO);
         }
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
         Debug.logWarning(UtilProperties.getMessage(resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
         }

         //create
         if (supplierPartyId == null) {
         System.out.println("Suppier product not found");
         try {
         Map<String, Object> toStore = supplierProduct.getGenericValueAsMap();
         toStore.put("userLogin", userLogin);
         toStore.put("locale", locale);

         resultMap = session.getDispatcher().runSync("createSupplierProduct", toStore);

         System.out.println("create Suppier product");
         //                Debug.logInfo("createInvoiceForOrderAllItems  [" + resultMap.get("invoiceId") + "] has been shipped", module);
         } catch (Exception exc) {
         Debug.logError(exc, module);
         }

         }

         } else {
         //                   System.out.println("Not Found - [ Party= " + partyId + " , product =" + productId + "]");
         }
         */
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


        /*        
         valueMap.put("reservNthPPPerc", map.get("reservNthPPPerc"));
         valueMap.put("productHeight", map.get("productHeight"));
         valueMap.put("billOfMaterialLevel", map.get("billOfMaterialLevel"));
         valueMap.put("quantityIncluded", map.get("quantityIncluded"));
         valueMap.put("createdStamp", map.get("createdStamp"));
         valueMap.put("heightUomId", map.get("heightUomId"));
         valueMap.put("widthUomId", map.get("widthUomId"));
         valueMap.put("reservMaxPersons", map.get("reservMaxPersons"));
         valueMap.put("mediumImageUrl", map.get("mediumImageUrl"));
         valueMap.put("createdDate", map.get("createdDate"));

         valueMap.put("shippingWidth", map.get("shippingWidth"));

         valueMap.put("salesDiscWhenNotAvail", map.get("salesDiscWhenNotAvail"));
         valueMap.put("amountUomTypeId", map.get("amountUomTypeId"));
         valueMap.put("facilityId", map.get("facilityId"));
         valueMap.put("configId", map.get("configId"));
         valueMap.put("productRating", map.get("productRating"));
         valueMap.put("lastModifiedByUserLogin", map.get("lastModifiedByUserLogin"));
         valueMap.put("ratingTypeEnum", map.get("ratingTypeEnum"));
         valueMap.put("fixedAmount", map.get("fixedAmount"));
         valueMap.put("returnable", map.get("returnable"));

         valueMap.put("requirementMethodEnumId", map.get("requirementMethodEnumId"));
         valueMap.put("depthUomId", map.get("depthUomId"));
         valueMap.put("piecesIncluded", map.get("piecesIncluded"));
         valueMap.put("quantityUomId", map.get("quantityUomId"));

         valueMap.put("weight", map.get("weight"));
         valueMap.put("virtualVariantMethodEnum", map.get("virtualVariantMethodEnum"));
         valueMap.put("salesDiscontinuationDate", map.get("salesDiscontinuationDate"));

         valueMap.put("includeInPromotions", map.get("includeInPromotions"));
         valueMap.put("supportDiscontinuationDate", map.get("supportDiscontinuationDate"));
         valueMap.put("originalImageUrl", map.get("originalImageUrl"));
         valueMap.put("detailScreen", map.get("detailScreen"));
         valueMap.put("introductionDate", map.get("introductionDate"));
         valueMap.put("requireAmount", map.get("requireAmount"));
         valueMap.put("releaseDate", map.get("releaseDate"));
         valueMap.put("createdTxStamp", map.get("createdTxStamp"));
         valueMap.put("productDepth", map.get("productDepth"));
         valueMap.put("shippingDepth", map.get("shippingDepth"));
         valueMap.put("largeImageUrl", map.get("largeImageUrl"));
         valueMap.put("originGeoId", map.get("originGeoId"));
         valueMap.put("reserv2ndPPPerc", map.get("reserv2ndPPPerc"));
         valueMap.put("inShippingBox", map.get("inShippingBox"));
         valueMap.put("requireInventory", map.get("requireInventory"));
         valueMap.put("detailImageUrl", map.get("detailImageUrl"));
         valueMap.put("productWidth", map.get("productWidth"));
         valueMap.put("lastUpdatedStamp", map.get("lastUpdatedStamp"));
         valueMap.put("weightUomId", map.get("weightUomId"));
         valueMap.put("manufacturerPartyId", map.get("manufacturerPartyId"));
         valueMap.put("productDiameter", map.get("productDiameter"));
         valueMap.put("orderDecimalQuantity", map.get("orderDecimalQuantity"));
         valueMap.put("smallImageUrl", map.get("smallImageUrl"));

         valueMap.put("priceDetailText", map.get("priceDetailText"));
         valueMap.put("defaultShipmentBoxTypeId", map.get("defaultShipmentBoxTypeId"));
         valueMap.put("productId", map.get("productId"));
         //        valueMap.put("lotIdFilledIn", map.get("lotIdFilledIn"));
         valueMap.put("productWeight", map.get("productWeight"));
         valueMap.put("lastModifiedDate", map.get("lastModifiedDate"));
         valueMap.put("diameterUomId", map.get("diameterUomId"));
         valueMap.put("shippingHeight", map.get("shippingHeight"));
         valueMap.put("productName", map.get("productName"));
         valueMap.put("inventoryMessage", map.get("inventoryMessage"));
         valueMap.put("lastUpdatedTxStamp", map.get("lastUpdatedTxStamp"));
         valueMap.put("comments", map.get("comments"));
         valueMap.put("createdByUserLogin", map.get("createdByUserLogin"));
         */
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
