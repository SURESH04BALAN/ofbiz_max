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
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import mvc.controller.LoadAccountWorker;
import mvc.controller.LoadProductCatalogWorker;
import mvc.controller.LoadProductPriceWorker;
import mvc.controller.LoadProductWorker;
import mvc.controller.SwingWorkerProgressModel;
import mvc.controller.SwingWorkerPropertyChangeListener;
import mvc.view.SwingWorkerBasedComponentVisibility;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.dataimportexport.ProductLoadViewPanel;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryRollup;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.product.SupplierPrefOrderSingleton;
import org.ofbiz.ordermax.product.catalog.SaveProductCategoryAction;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadGroceryAndScaleProductWorker extends SwingWorker<List<ProductComposite>, ProductComposite> {

    public static final String module = LoadGroceryAndScaleProductWorker.class.getName();
    protected volatile int maxProgress;
    protected int progressedItems;
    protected ListAdapterListModel<ProductComposite> productListModel;
    protected BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    protected String csvFile;
    protected XuiSession session = null;

    public LoadGroceryAndScaleProductWorker(ListAdapterListModel<ProductComposite> productListModel, String fileName, XuiSession delegator) {
        this.productListModel = productListModel;
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

    public ProductComposite createProductComposite(BigDecimal supplierPrice, String productId, String productName) {

        ProductComposite productComposite = LoadProductWorker.newProduct();
        org.ofbiz.ordermax.entity.Product prod = productComposite.getProduct();
//        productComposite.setProduct(prod);

        prod.setproductId(productId);

        prod.setinternalName(productName);
        prod.setbrandName("Temporary");
        prod.setprimaryProductCategoryId("Temporary");

        prod.setdepartmentName("Temporary");

        prod.setproductName(prod.getinternalName());
        prod.setdescription(prod.getinternalName());
        prod.setlongDescription(prod.getinternalName());
        prod.setquantityUomId("OTH_ea");

        //supplier product
        SupplierProductComposite spComp = LoadSupplierProductFromFileWorker.newSupplierProduct(productComposite.getProduct().getproductId());
        spComp.getSupplierProduct().setsupplierProductName(productComposite.getProduct().getinternalName());
        spComp.getSupplierProduct().setsupplierProductId(productComposite.getProduct().getproductId());
        spComp.getSupplierProduct().setcomments("_NA_");
//        spComp.getSupplierProduct().setpartyId("BigSupplier");

        spComp.getSupplierProduct().setcurrencyUomId((String) XuiContainer.getSession().getAttribute("currency"));

        spComp.getSupplierProduct().setlastPrice(supplierPrice);

        productComposite.getSupplierProductList().add(spComp);
        BigDecimal defPrice = BigDecimal.ONE;

        ProductPriceComposite productPriceDefComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
//            productPriceDefComposite.getProductPrice().setprice(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ONE);
        productPriceDefComposite.getProductPrice().setprice(defPrice);

        ProductPriceComposite productPriceListComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
        productPriceListComposite.getProductPrice().setprice(defPrice);

        //          productPriceListComposite.getProductPrice().setprice(i < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ONE);
        //  Debug.logError("Field" + i+1 + "[ListPrice]: " + data[i-1], null);
        productPriceListComposite.getProductPrice().setproductPriceTypeId("LIST_PRICE");
        try {
            productComposite.getProductItemPrice().addProductPrice(productPriceDefComposite);
            productComposite.getProductItemPrice().addProductPrice(productPriceListComposite);
        } catch (Exception ex) {
            Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        BigDecimal avgPrice = BigDecimal.ONE;

        ProductPriceComposite productPriceCostComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
        productPriceCostComposite.getProductPrice().setprice(avgPrice);
        productPriceCostComposite.getProductPrice().setproductPriceTypeId("AVERAGE_COST");

        try {
//                if (productPriceCostComposite.getProductPrice().equals(BigDecimal.ZERO) == false) {
            productComposite.getProductItemPrice().addProductPrice(productPriceCostComposite);
            //               }
        } catch (Exception ex) {
            Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        prod.settaxable("N");

        return productComposite;
    }

    @Override
    protected List<ProductComposite> doInBackground() throws Exception {
        Debug.logInfo("doInBackground ", module);
        productListModel.clear();
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

        Debug.logInfo("doInBackground ", module);
        try {

            try {
                /*                Reader csvFileIo = null;
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
                 */
                List<ProductComposite> personsRead = new ArrayList<ProductComposite>();
                for (int i = 0; i < 200; i++) {

                    String val = "GT6" + String.format("%03d", i);
                    String name = new String("Grocery ").concat(Integer.toString(i));
                    ProductComposite procComp = createProductComposite(new BigDecimal(1.0), val, name);
                    personsRead.add(procComp);
                }

                for (int i = 0; i < 200; i++) {

                    String val = "ST6" + String.format("%03d", i);
                    String name = new String("Scale ").concat(Integer.toString(i));
                    ProductComposite procComp = createProductComposite(new BigDecimal(1.0), val, name);
                    procComp.getProduct().setamountRequired("Y");
                    personsRead.add(procComp);
                }
                maxProgress = personsRead.size();
                
                Map<String, String> supplierList = createSupplierList(personsRead);
                
                Map<String, String> categories = new HashMap<String, String>();
                for (ProductComposite prod : personsRead) {
                    String catId = prod.getProduct().getprimaryProductCategoryId();
                    if (UtilValidate.isNotEmpty(catId)) {
                        categories.put(prod.getProduct().getbrandName(), prod.getProduct().getprimaryProductCategoryId());
                    }
                }

                System.out.println("Start create product");
                productListModel.clear();
                for (ProductComposite productComposite : personsRead) {

                    createCategory(productComposite.getProduct(), session);

                    Map<String, Object> valueMap = productComposite.getProduct().getValuesMap();
                    String productId = productComposite.getProduct().getproductId();
                    String brand = productComposite.getProduct().getbrandName();
                    String productCategoryId = brand.substring(0, brand.length() < 20 ? brand.length() : 19);
//                String productCategoryId = map.get("PRIMARY_PRODUCT_CATEGORY_ID");
                    System.out.println("Start create product productId: " + productId);
                    //ProductComposite productComposite = new ProductComposite();

                    //productComposite.setProduct(product.getProduct());
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
                                saveSupplierProduct(supplierList, productComposite);
                                saveGoodIdentification( productComposite);
                                savePrices(productComposite);
                                
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

    static protected Map<String, String> createSupplierList(List<ProductComposite> productComposites) {
        String supplierId = "SUPPLIER";
        int i = 1;
        Map<String, String> supplierList = new HashMap<String, String>();
        for (ProductComposite prodComposite : productComposites) {
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

        createSupplier(supplierList, ControllerOptions.getSession());
        return supplierList;
    }

    static protected boolean saveGoodIdentification(ProductComposite prodComposite) {

        boolean result = true;
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
        return result;
    }

    static protected boolean saveSupplierProduct(Map<String, String> supplierList, ProductComposite prodComposite) {

        boolean result = true;
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
        return result;
    }

    static protected boolean savePrices(ProductComposite prodComposite) {

        boolean result = true;
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
        return result;
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
        productListModel.addAll(chunks);
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

    static public class LoadGroceryAndScalAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2636985714796751517L;
        private ListAdapterListModel<ProductComposite> productListModel;
        private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();
        private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
        private JTextField textField = null;
        private ListAdapterListModel<ProductComposite> productCompListModel = new ListAdapterListModel<ProductComposite>();
        public ProductLoadViewPanel productCompLoadViewPanel = new ProductLoadViewPanel();

        public LoadGroceryAndScalAction(SwingWorkerProgressModel swingWorkerPropertyChangeListener,
                SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility,
                BoundedRangeModel boundedRangeModel) {
            addSwingWorkerPropertyChangeListener(swingWorkerPropertyChangeListener);
            addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
            setLoadSpeedModel(boundedRangeModel);
            productCompLoadViewPanel.jBtnLoad.addActionListener(this);

            this.textField = productCompLoadViewPanel.textEdit;
        }

        @Override
        public Object[] getKeys() {
            return new Object[]{Action.NAME};
        }

        @Override
        public Object getValue(String key) {
            if (Action.NAME.equals(key)) {
                return "Load Grocery And Scale Temprary Product";
            } else if (Action.ACCELERATOR_KEY.equals(key)) {
                return KeyStroke.getKeyStroke('S');
            }
            return super.getValue(key);
        }

        public void actionPerformed(ActionEvent e) {
//        LoadProductFromFileWorker loadPersonsWorker = new LoadProductFromFileWorker(productListModel,"C:\\backup\\Real_Data\\seven_product_final.csv", session);
//    LoadProductFromFileWorker loadPersonsWorker = new LoadProductFromFileWorker(productListModel,"C:\\backup\\Real_Data\\generated_product_load.csv", session);        
            LoadGroceryAndScaleProductWorker loadPersonsWorker = new LoadGroceryAndScaleProductWorker(productCompListModel, textField.getText(), ControllerOptions.getSession());
            Debug.logInfo("actionPerformed ", module);
            loadPersonsWorker.setLoadSpeedModel(loadPersonsSpeedModel);
            Debug.logInfo("actionPerformed 1", module);
            for (SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener : swingWorkerPropertyChangeListeners) {
                swingWorkerPropertyChangeListener
                        .attachPropertyChangeListener(loadPersonsWorker);
                Debug.logInfo("actionPerformed 1.5", module);
            }
            Debug.logInfo("actionPerformed 2", module);
            loadPersonsWorker.execute();
            /*try {
             loadPersonsWorker.doInBackground();
             } catch (Exception ex) {
             Logger.getLogger(LoadGroceryAndScaleProductWorker.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            Debug.logInfo("actionPerformed done", module);
        }

        public void addSwingWorkerPropertyChangeListener(
                SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
            swingWorkerPropertyChangeListeners.add(swingWorkerPropertyChangeListener);
        }

        public void removeSwingWorkerPropertyChangeListener(
                SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
            swingWorkerPropertyChangeListeners.remove(swingWorkerPropertyChangeListener);
        }

        public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
            this.loadPersonsSpeedModel = loadPersonsSpeedModel;
        }
    }

    static public class LoadViewPanel extends ProductLoadViewPanel {

    }
}
