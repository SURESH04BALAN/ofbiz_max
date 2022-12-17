/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

/**
 *
 * @author siranjeev
 */
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.composite.GoodIdentificationList;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.composite.ProductContentCompositeList;
import org.ofbiz.ordermax.composite.ProductItemPrice;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.Content;
import org.ofbiz.ordermax.entity.DataResource;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductAssoc;
import org.ofbiz.ordermax.entity.ProductCategoryContent;
import org.ofbiz.ordermax.entity.ProductCategoryMember;
import org.ofbiz.ordermax.entity.ProductContent;
import org.ofbiz.ordermax.entity.SupplierProduct;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.product.catalog.categorycontent.ProductCategoryContentComposite;
import org.ofbiz.ordermax.product.catalog.categorycontent.ProductCategoryContentCompositeList;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadProductWorker extends LoadBaseSwingWorker<ProductComposite> {

    public static final String module = LoadProductWorker.class.getName();

    public LoadProductWorker(ListAdapterListModel<ProductComposite> personListModel, XuiSession delegator, Map<String, Object> findOptionMap) {
        super(personListModel, findOptionMap);

        this.session = delegator;

    }

    public LoadProductWorker(ListAdapterListModel<ProductComposite> personListModel, XuiSession delegator, List<EntityCondition> entityConditionList) {
        super(personListModel, entityConditionList);

        this.session = delegator;

    }

    @Override
    protected List<ProductComposite> doInBackground() throws Exception {

        List<ProductComposite> products = new ArrayList<ProductComposite>();

        Map<String, Object> result = null;

        if (entityConditionList == null) {
            boolean beganTransaction = false;
            try {
                beganTransaction = TransactionUtil.begin();
                try {

                    result = session.getDispatcher().runSync("performFind",
                            UtilMisc.<String, Object>toMap("entityName", "Product",
                                    "inputFields", findOptionMap,
                                    "userLogin", session.getUserLogin()));

                    /*for (Map.Entry<String, Object> entryDept : result.entrySet()) {
                     if (entryDept.getValue() == null) {
                     Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + "Null", module);
                     } else {
                     Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                     }
                     }*/
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

                            ProductComposite productComp = new ProductComposite();
                            productComp.setProduct(new Product(gv));
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

                        listIt.close();
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
        } else {
            Debug.logInfo("getReadOnlyGenericValueListsWithSelection ", module);
            List<GenericValue> list = PosProductHelper.getReadOnlyGenericValueListsWithSelection("Product", entityConditionList, "productId", this.session.getDelegator());
            maxProgress = list.size() + 1;
//                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
            for (GenericValue gv : list) {

                ProductComposite productComp = new ProductComposite();
                productComp.setProduct(new Product(gv));
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

        }
        return products;
    }

    static public ProductComposite loadProduct(String productId, XuiSession session) {
        ProductComposite productComposite = null;
        try {
            GenericValue productGen = session.getDelegator().findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
            if (productGen != null) {
                productComposite = new ProductComposite();
                Product product = new Product(productGen);
                productComposite.setProduct(product);

                ArrayList<GoodIdentification> array = new ArrayList<GoodIdentification>();
                List<GenericValue> goodIdentificationList = productGen.getRelated("GoodIdentification");
                for (GenericValue partyRoleGen : goodIdentificationList) {
                    //create order role
                    GoodIdentification goodIdentification = new GoodIdentification(partyRoleGen);
                    array.add(goodIdentification);
                }

                productComposite.setGoodIdentificationList(new GoodIdentificationList());
                productComposite.getGoodIdentificationList().addAll(array);
                ArrayList<ProductCategoryMember> arrayProdCat = new ArrayList<ProductCategoryMember>();
                List<GenericValue> productCategoryMemberList = productGen.getRelated("ProductCategoryMember");
                for (GenericValue productCategoryMember : productCategoryMemberList) {
                    //create order role
                    ProductCategoryMember catMem = new ProductCategoryMember(productCategoryMember);
                    arrayProdCat.add(catMem);
                }

                productComposite.setProductCategoryList(new ListAdapterListModel<ProductCategoryMember>());
                productComposite.getProductCategoryList().addAll(arrayProdCat);

                List<GenericValue> supplierProductListGV = productGen.getRelated("SupplierProduct");
                SupplierProductList supplierProductList = new SupplierProductList();//LoadSupplierProductWorker.getSupplierProduct(productId, session);

                for (GenericValue suppGV : supplierProductListGV) {
                    //create order role                    
                    SupplierProductComposite ppc = new SupplierProductComposite();
                    SupplierProduct supplierProduct = new SupplierProduct(suppGV);
                    ppc.setSupplierProduct(supplierProduct);
                    Debug.logInfo("SupplierProduct.getpartyId(): " + supplierProduct.getpartyId(), "SupplierProduct");
                    supplierProductList.add(ppc);

                }
                productComposite.setSupplierProductList(supplierProductList);
                ProductItemPrice productItemPrice = LoadProductPriceWorker.getProductItemPrice(productId, session);
                productComposite.setProductItemPrice(productItemPrice);
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadInvoiceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productComposite;
    }

    static public ProductComposite newProduct() {

        ProductComposite productComposite = new ProductComposite();

        Product product = new Product();
        product.setproductTypeId("FINISHED_GOOD");
        product.setrequireInventory("N");
        product.setproductHeight(BigDecimal.ZERO);
        product.setproductWeight(BigDecimal.ZERO);
        product.setreturnable("Y");
        product.settaxable("N");
        product.setchargeShipping("N");
        product.setautoCreateKeywords("N");
        product.setincludeInPromotions("Y");
        product.setisVariant("N");
        product.setisVirtual("N");
        product.setrequirementMethodEnumId("PRODRQM_NONE");
        product.setbillOfMaterialLevel(new Long(0));

        productComposite.setProduct(product);
        productComposite.setGoodIdentificationList(new GoodIdentificationList());
        productComposite.setSupplierProductList(new SupplierProductList());
        productComposite.setProductItemPrice(new ProductItemPrice());
        return productComposite;
    }

    static public GoodIdentification createNewGoodsIdentification(String productId, String goodIdentificationTypeId) {

        GoodIdentification goodIdentification = new GoodIdentification();
        goodIdentification.setproductId(productId);
        goodIdentification.setgoodIdentificationTypeId(goodIdentificationTypeId);

        return goodIdentification;
    }

    public static void saveProduct(ProductComposite productComp, XuiSession session) {

        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        Map<String, Object> inMap = productComp.getProduct().getValuesMap();
        inMap.put("userLogin", userLogin);
        GenericValue prod = PosProductHelper.getProduct(productComp.getProduct().getproductId(), delegator);
        if (prod != null) {

            try {
                Debug.logInfo("update product: " + productComp.getProduct().getproductId(), module);
                resultMap = dispatcher.runSync("updateProduct", inMap);
                OrderMaxUtility.handleServiceReturn("Update product: ", resultMap);
                // if (UtilValidate.isNotEmpty(productComp.getCategoryTreeDef())) {
                //    AddProductCategoryItem(productComp.getProduct().getproductId(), productComp.getCategoryTreeDef(), session);
                //}                
            } catch (Exception ex) {
                Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Debug.logInfo("insert product: " + productComp.getProduct().getproductId(), module);
                resultMap = dispatcher.runSync("createProduct", inMap);
                OrderMaxUtility.handleServiceReturn("create product: ", resultMap);
                if (UtilValidate.isNotEmpty(productComp.getCategoryTreeDef())) {
                    AddProductCategoryItem(productComp.getProduct().getproductId(), productComp.getCategoryTreeDef(), session);
                }
            } catch (Exception ex) {
                Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        saveGoodIdentification(productComp.getGoodIdentificationList().getList(), session);
        saveProductAssoc(productComp.getProductAssocToList(), session);
    }

    public static void AddProductCategoryItem(String productId, org.ofbiz.ordermax.base.TreeNode catNode, XuiSession session) {
        try {
            GenericValue catMember = PosProductHelper.getProductCategoryMember(productId, catNode._id, session.getDelegator());
            if (catMember == null) {
                session.getDelegator().create(PosProductHelper.createProductCategoryMember(productId, catNode._id, session.getDelegator()));
            }

        } catch (Exception e2) {
            Debug.logError(e2, module);
        }
    }

    public static void saveGoodIdentification(List<GoodIdentification> goodIdentificationList, XuiSession session) {
        Debug.logInfo("Save: " + goodIdentificationList.size(), "module");
        for (GoodIdentification val : goodIdentificationList) {
            GenericValue eanVal = PosProductHelper.getGoodIdentification(val.getproductId(), val.getgoodIdentificationTypeId(), session.getDelegator());
            if (eanVal != null) {
                PosProductHelper.updateGoodIdentificationType(eanVal, val.getidValue(), session.getDelegator());
                Debug.logInfo("updateGoodIdentificationType: " + val.getproductId(), "module");
            } else {
                if (UtilValidate.isNotEmpty(val.getidValue())) {
                    PosProductHelper.createGoodIdentificationType(val.getproductId(),
                            val.getgoodIdentificationTypeId(), val.getidValue(), session.getDelegator());
                    Debug.logInfo("createGoodIdentificationType: " + val.getproductId(), "module");
                }
            }

        }
    }

    public static void removeGoodIdentification(GoodIdentification goodIdentification, XuiSession session) {
//        Debug.logInfo("Save: " + goodIdentificationList.size(), "module");
//        for (GoodIdentification val : goodIdentificationList) {
        GenericValue eanVal = PosProductHelper.getGoodIdentification(goodIdentification.getproductId(), goodIdentification.getgoodIdentificationTypeId(), session.getDelegator());
        if (eanVal != null) {
            PosProductHelper.removeGoodIdentificationType(eanVal, session.getDelegator());
        }

        //      }
    }

    public static void saveProductAssoc(List<ProductAssoc> productAssocList, XuiSession session) {
//        Debug.logInfo("Save: " + productAssocList.size(), module);
        if (productAssocList != null) {
            GenericValue userLogin = session.getUserLogin();

            for (ProductAssoc val : productAssocList) {
                List<GenericValue> assocList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ProductAssoc",
                        UtilMisc.toMap("productIdTo", val.getproductIdTo(), "productId",
                                val.getproductId(), "productAssocTypeId", val.getproductAssocTypeId()),
                        null, session.getDelegator());

                Map<String, Object> inMap = val.getValuesMap();
                inMap.put("userLogin", userLogin);

                if (assocList != null && !assocList.isEmpty()) {

                    try {
                        Debug.logInfo("update Product Assoc: " + val.getproductIdTo(), module);
                        Map<String, Object> resultMap = session.getDispatcher().runSync("updateProductAssoc", inMap);
                        OrderMaxUtility.handleServiceReturn("Update product: ", resultMap);
                        // if (UtilValidate.isNotEmpty(productComp.getCategoryTreeDef())) {
                        //    AddProductCategoryItem(productComp.getProduct().getproductId(), productComp.getCategoryTreeDef(), session);
                        //}                
                    } catch (Exception ex) {
                        Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        Debug.logInfo("insert product assoc: " + val.getproductIdTo(), module);
                        Map<String, Object> resultMap = session.getDispatcher().runSync("createProductAssoc", inMap);
                        OrderMaxUtility.handleServiceReturn("create product: ", resultMap);

                    } catch (Exception ex) {
                        Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
    }

    public static void removeProductAssoc(GoodIdentification goodIdentification, XuiSession session) {
//        Debug.logInfo("Save: " + goodIdentificationList.size(), "module");
//        for (GoodIdentification val : goodIdentificationList) {
        GenericValue eanVal = PosProductHelper.getGoodIdentification(goodIdentification.getproductId(), goodIdentification.getgoodIdentificationTypeId(), session.getDelegator());
        if (eanVal != null) {
            PosProductHelper.removeGoodIdentificationType(eanVal, session.getDelegator());
        }

        //      }
    }

    static public List<Product> loadProducts(Map<String, Object> inputFields, XuiSession session) {
        List<Product> listInventItemLocation = new ArrayList<Product>();

        Map<String, Object> result = null;
        List<Map<String, Object>> tempResults = FastList.newInstance();
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            // = FastMap.newInstance();
            try {
                result = session.getDispatcher().runSync("performFind", UtilMisc.<String, Object>toMap("entityName", "Product",
                        "inputFields", inputFields, "userLogin", session.getUserLogin()));
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
//                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
                    for (GenericValue gv : list) {
//                tempResults.addAll(filteredList);
                        listInventItemLocation.add(new Product(gv));
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
        return listInventItemLocation;
    }

    static public ProductContentCompositeList loadProductContent(String productId, XuiSession session) {
        ProductContentCompositeList productContentCompositeList = new ProductContentCompositeList();

        Map<String, Object> whereClauseMap = new HashMap<String, Object>();
        whereClauseMap.put("productId", productId);
        List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductContent", whereClauseMap, null, XuiContainer.getSession().getDelegator());
        for (GenericValue genValue : valueList) {

            try {
                ProductContent productContent = new ProductContent(genValue);
                ProductContentComposite composite = new ProductContentComposite();
                composite.setProductContent(productContent);
                List<GenericValue> contentList = genValue.getRelated("Content");
                for (GenericValue contentGen : contentList) {
                    try {
                        Content content = new Content(contentGen);
                        composite.setContent(content);
                        List<GenericValue> dataResourceList = contentGen.getRelated("DataResource");
                        for (GenericValue dataResourceGen : dataResourceList) {
                            DataResource dataResource = new DataResource(dataResourceGen);
                            composite.setDataResource(dataResource);
                            if (dataResource.getdataResourceTypeId() != null && dataResource.getdataResourceTypeId().equals("ELECTRONIC_TEXT")) {
                                String str;
                                try {
                                    str = LoadProductWorker.streamDataResource(XuiContainer.getSession().getDelegator(), dataResource.getdataResourceId());
                                    dataResource.setobjectInfo(str);
                                    Debug.logInfo("ELECTRONIC_TEXT: " + str, module);
                                } catch (Exception ex) {
                                    Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }
                    } catch (GenericEntityException ex) {
                        Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                productContentCompositeList.add(composite);
            } catch (GenericEntityException ex) {
                Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return productContentCompositeList;
    }

    static public ArrayList<ProductAssoc> loadProductVariantList(String productId, XuiSession session) {

        ArrayList<ProductAssoc> variantProductList = new ArrayList<ProductAssoc>();
        try {
            List<GenericValue> paList = EntityUtil.filterByDate(session.getDelegator().findByAnd("ProductAssoc", UtilMisc.toMap("productId", productId, "productAssocTypeId", "PRODUCT_VARIANT")));
            for (GenericValue prodAssoc : paList) {
                variantProductList.add(new ProductAssoc(prodAssoc));
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductDataTreeLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return variantProductList;
    }

    public static ProductAssoc createProductAssoc(String productId, String associationTypeId) {

        ProductAssoc currProductAssoc = new ProductAssoc();
        currProductAssoc.setproductAssocTypeId(associationTypeId/*"PRODUCT_VARIANT"*/);
        currProductAssoc.setfromDate(UtilDateTime.nowTimestamp());
        currProductAssoc.setproductId(productId);
        return currProductAssoc;

    }

    static public ArrayList<ProductAssoc> loadProductAssocToList(String productIdTo, XuiSession session) {

        ArrayList<ProductAssoc> variantProductList = new ArrayList<ProductAssoc>();
        try {
            List<GenericValue> paList = EntityUtil.filterByDate(session.getDelegator().findByAnd("ProductAssoc", UtilMisc.toMap("productIdTo", productIdTo)));
            for (GenericValue prodAssoc : paList) {
                variantProductList.add(new ProductAssoc(prodAssoc));
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(ProductDataTreeLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return variantProductList;
    }

    static public ProductContentComposite loadProductContent(String productId, String productContentTypeId, XuiSession session) {

        Map<String, Object> whereClauseMap = new HashMap<String, Object>();
        ProductContentComposite productContentComposite = null;
        whereClauseMap.put("productId", productId);
        whereClauseMap.put("productContentTypeId", productContentTypeId);
        List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductContent", whereClauseMap, null, XuiContainer.getSession().getDelegator());
        for (GenericValue genValue : valueList) {

            try {
                ProductContent productContent = new ProductContent(genValue);
                productContentComposite = new ProductContentComposite();
                productContentComposite.setProductContent(productContent);
                List<GenericValue> contentList = genValue.getRelated("Content");
                for (GenericValue contentGen : contentList) {
                    try {
                        Content content = new Content(contentGen);
                        productContentComposite.setContent(content);
                        List<GenericValue> dataResourceList = contentGen.getRelated("DataResource");
                        for (GenericValue dataResourceGen : dataResourceList) {
                            DataResource dataResource = new DataResource(dataResourceGen);
                            productContentComposite.setDataResource(dataResource);
                            if (dataResource.getdataResourceTypeId() != null && dataResource.getdataResourceTypeId().equals("ELECTRONIC_TEXT")) {
                                String str;
                                try {
                                    str = LoadProductWorker.streamDataResource(XuiContainer.getSession().getDelegator(), dataResource.getdataResourceId());
                                    dataResource.setobjectInfo(str);
                                    Debug.logInfo("ELECTRONIC_TEXT: " + str, module);
                                } catch (Exception ex) {
                                    Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }
                    } catch (GenericEntityException ex) {
                        Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (GenericEntityException ex) {
                Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return productContentComposite;
    }

    static public ProductCategoryContentCompositeList loadProductCategoryContent(String categoryId, XuiSession session) {
        ProductCategoryContentCompositeList productContentCompositeList = new ProductCategoryContentCompositeList();

        Map<String, Object> whereClauseMap = new HashMap<String, Object>();
        whereClauseMap.put("productCategoryId", categoryId);
        List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductCategoryContent", whereClauseMap, null, XuiContainer.getSession().getDelegator());
        for (GenericValue genValue : valueList) {

            try {
                ProductCategoryContent productContent = new ProductCategoryContent(genValue);
                ProductCategoryContentComposite composite = new ProductCategoryContentComposite();
                composite.setProductCategoryContent(productContent);
                List<GenericValue> contentList = genValue.getRelated("Content");
                for (GenericValue contentGen : contentList) {
                    try {
                        Content content = new Content(contentGen);
                        composite.setContent(content);
                        List<GenericValue> dataResourceList = contentGen.getRelated("DataResource");
                        for (GenericValue dataResourceGen : dataResourceList) {
                            DataResource dataResource = new DataResource(dataResourceGen);
                            composite.setDataResource(dataResource);
                        }
                    } catch (GenericEntityException ex) {
                        Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                productContentCompositeList.add(composite);
            } catch (GenericEntityException ex) {
                Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return productContentCompositeList;
    }

    static public ProductContentComposite newProductContent(String productId, String productContentTypeId) {

        ProductContent productContent = new ProductContent();
        productContent.setproductContentTypeId(productContentTypeId);
        productContent.setproductId(productId);
        ProductContentComposite composite = new ProductContentComposite();
        composite.setProductContent(productContent);

        Content content = new Content();
        composite.setContent(content);

        DataResource dataResource = new DataResource();
        composite.setDataResource(dataResource);

        return composite;
    }

    static public ProductContentComposite newImageProductContent(String productId, String productContentTypeId, String contentName) {

        ProductContentComposite composite = newProductContent(productId, productContentTypeId);
        Content content = composite.getContent();
        content.setcontentTypeId("DOCUMENT");
        content.setlocaleString("en");
        content.setcontentName(contentName);

        DataResource dataResource = composite.getDataResource();
        dataResource.setdataResourceTypeId("SHORT_TEXT");
        dataResource.setdataTemplateTypeId("FTL");
        dataResource.setstatusId("CTNT_PUBLISHED");
        dataResource.setmimeTypeId("text/html");
        dataResource.setisPublic("Y");
        dataResource.setdataResourceName(productContentTypeId);

        return composite;
    }

    static public ProductContentComposite newLongTextProductContent(String productId, String productContentTypeId, String contentName) {

        ProductContentComposite composite = newProductContent(productId, productContentTypeId);
        Content content = composite.getContent();
        content.setcontentTypeId("DOCUMENT");
        content.setlocaleString("en");
        content.setcontentName(contentName);

        DataResource dataResource = composite.getDataResource();
        dataResource.setdataResourceTypeId("ELECTRONIC_TEXT");
        dataResource.setdataTemplateTypeId("FTL");
        dataResource.setstatusId("CTNT_PUBLISHED");
        dataResource.setmimeTypeId("application/octet-stream");
        dataResource.setisPublic("Y");

        return composite;
    }

    static public ProductContentCompositeList newProductContentList(String productId) {
        ProductContentCompositeList productContentCompositeList = new ProductContentCompositeList();
        productContentCompositeList.add(newImageProductContent(productId, "SMALL_IMAGE_URL", "smallImage"));
        productContentCompositeList.add(newImageProductContent(productId, "SMALL_IMAGE_ALT_URL", "smallImageAlt"));
        productContentCompositeList.add(newImageProductContent(productId, "THUMBNAIL_IMAGE_URL", "thumbImage"));
        productContentCompositeList.add(newImageProductContent(productId, "DETAIL_IMAGE_URL", "detailImage"));

        productContentCompositeList.add(newImageProductContent(productId, "ADDITIONAL_IMAGE_1", "addImage1"));
        productContentCompositeList.add(newImageProductContent(productId, "XTRA_IMG_1_LARGE", "xtraLargeImage1"));
        productContentCompositeList.add(newImageProductContent(productId, "ADDITIONAL_IMAGE_2", "addImage2"));
        productContentCompositeList.add(newImageProductContent(productId, "XTRA_IMG_2_LARGE", "xtraLargeImage2"));

        productContentCompositeList.add(newImageProductContent(productId, "LARGE_IMAGE_URL", "largeImage"));

        productContentCompositeList.add(newLongTextProductContent(productId, "PRODUCT_NAME", "productName"));
        productContentCompositeList.add(newLongTextProductContent(productId, "LONG_DESCRIPTION", "longDescription"));
        productContentCompositeList.add(newLongTextProductContent(productId, "SPECIALINSTRUCTIONS", "specialInstructions"));
        productContentCompositeList.add(newLongTextProductContent(productId, "DELIVERY_INFO", "deliveryInfo"));

        productContentCompositeList.add(newLongTextProductContent(productId, "TERMS_AND_CONDS", "termsConditions"));
        productContentCompositeList.add(newLongTextProductContent(productId, "INGREDIENTS", "ingredients"));

        productContentCompositeList.add(newLongTextProductContent(productId, "PLP_LABEL", "plpLabel"));
        productContentCompositeList.add(newLongTextProductContent(productId, "PDP_LABEL", "pdpLabel"));

        return productContentCompositeList;
    }

    static public ProductContentCompositeList newProductContentGroceryList(String productId) {
        ProductContentCompositeList productContentCompositeList = new ProductContentCompositeList();
        productContentCompositeList.add(newImageProductContent(productId, "SMALL_IMAGE_URL", "smallImage"));
//        productContentCompositeList.add(newImageProductContent(productId, "SMALL_IMAGE_ALT_URL", "smallImageAlt"));
//        productContentCompositeList.add(newImageProductContent(productId, "THUMBNAIL_IMAGE_URL", "thumbImage"));
        productContentCompositeList.add(newImageProductContent(productId, "DETAIL_IMAGE_URL", "detailImage"));

//        productContentCompositeList.add(newImageProductContent(productId, "ADDITIONAL_IMAGE_1", "addImage1"));
//        productContentCompositeList.add(newImageProductContent(productId, "XTRA_IMG_1_LARGE", "xtraLargeImage1"));
        //       productContentCompositeList.add(newImageProductContent(productId, "ADDITIONAL_IMAGE_2", "addImage2"));
//        productContentCompositeList.add(newImageProductContent(productId, "XTRA_IMG_2_LARGE", "xtraLargeImage2"));
        productContentCompositeList.add(newImageProductContent(productId, "LARGE_IMAGE_URL", "largeImage"));

        productContentCompositeList.add(newLongTextProductContent(productId, "PRODUCT_NAME", "productName"));
        productContentCompositeList.add(newLongTextProductContent(productId, "LONG_DESCRIPTION", "longDescription"));
        productContentCompositeList.add(newLongTextProductContent(productId, "WARNINGS", "warnings"));
        productContentCompositeList.add(newLongTextProductContent(productId, "DIRECTIONS", "directions"));

        return productContentCompositeList;
    }

    public static void saveDataResource(DataResource dataResource, XuiSession session) {
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();

            Map<String, Object> inMap = dataResource.getValuesMap();
            inMap.put("userLogin", userLogin);
            List<GenericValue> list = PosProductHelper.getGenericValueLists("DataResource", "dataResourceId", dataResource.getdataResourceId(), delegator);
            if (list.isEmpty() == false) {
                if (dataResource.getdataResourceTypeId() != null && dataResource.getdataResourceTypeId().equals("ELECTRONIC_TEXT")) {
                    inMap.put("textData", dataResource.getobjectInfo());
                    inMap.put("objectInfo", "");
                    dataResource.setobjectInfo("");
                    resultMap = dispatcher.runSync("updateDataResourceAndText", inMap);
                } else {
                    resultMap = dispatcher.runSync("updateDataResource", inMap);
                }
            } else {
                if (dataResource.getdataResourceTypeId() != null && dataResource.getdataResourceTypeId().equals("ELECTRONIC_TEXT")) {
                    inMap.put("textData", dataResource.getobjectInfo());
                    dataResource.setobjectInfo("");
                    inMap.put("objectInfo", "");
                    resultMap = dispatcher.runSync("createDataResourceAndText", inMap);
                } else {
                    resultMap = dispatcher.runSync("createDataResource", inMap);
                }
            }
            // if (resultMap.containsKey("responseMessage") && "success".equalsIgnoreCase((String) resultMap.get("responseMessage"))) {
            dataResource.setdataResourceId((String) resultMap.get("dataResourceId"));
            // }

            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                if (entryDept.getValue() == null) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + "Null", module);
                } else {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
            }
        } catch (GenericServiceException ex) {
            Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveContent(Content content, XuiSession session) {
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();

            Map<String, Object> inMap = content.getValuesMap();
            inMap.put("userLogin", userLogin);
            List<GenericValue> list = PosProductHelper.getGenericValueLists("Content", "contentId", content.getcontentId(), delegator);
            if (list.isEmpty() == false) {

                resultMap = dispatcher.runSync("updateContent", inMap);

            } else {

                resultMap = dispatcher.runSync("createContent", inMap);

            }
            if (resultMap.containsKey("responseMessage") && "success".equalsIgnoreCase((String) resultMap.get("responseMessage"))) {
                content.setcontentId((String) resultMap.get("contentId"));
            }

            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                if (entryDept.getValue() == null) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + "Null", module);
                } else {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
            }
        } catch (GenericServiceException ex) {
            Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveProductContent(ProductContent productContent, XuiSession session) {
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();

            Map<String, Object> inMap = productContent.getValuesMap();
            inMap.put("userLogin", userLogin);
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productId", productContent.getproductId());
            whereClauseMap.put("contentId", productContent.getcontentId());
            whereClauseMap.put("productContentTypeId", productContent.getproductContentTypeId());
            whereClauseMap.put("fromDate", productContent.getfromDate());

            GenericValue gv = PosProductHelper.getGenericValueByKey("ProductContent", whereClauseMap, delegator);
            if (gv != null) {
                resultMap = dispatcher.runSync("updateProductContent", inMap);
            } else {

                resultMap = dispatcher.runSync("createProductContent", inMap);

            }
            /*          if (resultMap.containsKey("responseMessage") && "success".equalsIgnoreCase((String) resultMap.get("responseMessage"))) {
             productContent.setcontentId((String) resultMap.get("contentId"));
             }
             */
            for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                if (entryDept.getValue() == null) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + "Null", module);
                } else {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
            }
        } catch (GenericServiceException ex) {
            Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveProductContentComposite(ProductContentComposite productContentComposite, XuiSession session) {

        saveDataResource(productContentComposite.getDataResource(), session);
        productContentComposite.getContent().setdataResourceId(productContentComposite.getDataResource().getdataResourceId());
        saveContent(productContentComposite.getContent(), session);
        productContentComposite.getProductContent().setcontentId(productContentComposite.getContent().getcontentId());
        saveProductContent(productContentComposite.getProductContent(), session);

    }

    /*
     public static void saveContent(ProductContentComposite productComp, XuiSession session) {
     try {
     GenericValue userLogin = session.getUserLogin();
     LocalDispatcher dispatcher = session.getDispatcher();
     Map<String, Object> resultMap = ServiceUtil.returnSuccess();
     Delegator delegator = session.getDelegator();
     Locale locale = Locale.getDefault();

     Map<String, Object> inMap = productComp.getProduct().getValuesMap();
     inMap.put("userLogin", userLogin);
     GenericValue prod = PosProductHelper.getProduct(productComp.getProduct().getproductId(), delegator);
     if (prod != null) {
     dispatcher.runSync("updateProduct", inMap);
     } else {
     dispatcher.runSync("createProduct", inMap);
     }
     saveGoodIdentification(productComp.getGoodIdentificationList().getList(), session);
     } catch (GenericServiceException ex) {
     Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
     }

     }

     public static void saveProductContent(ProductContentComposite productComp, XuiSession session) {
     try {
     GenericValue userLogin = session.getUserLogin();
     LocalDispatcher dispatcher = session.getDispatcher();
     Map<String, Object> resultMap = ServiceUtil.returnSuccess();
     Delegator delegator = session.getDelegator();
     Locale locale = Locale.getDefault();

     Map<String, Object> inMap = productComp.getProduct().getValuesMap();
     inMap.put("userLogin", userLogin);
     GenericValue prod = PosProductHelper.getProduct(productComp.getProduct().getproductId(), delegator);
     if (prod != null) {
     dispatcher.runSync("updateProduct", inMap);
     } else {
     dispatcher.runSync("createProduct", inMap);
     }
     saveGoodIdentification(productComp.getGoodIdentificationList().getList(), session);
     } catch (GenericServiceException ex) {
     Logger.getLogger(LoadProductWorker.class.getName()).log(Level.SEVERE, null, ex);
     }

     }*/
    public static String streamDataResource(Delegator delegator, String dataResourceId) throws Exception {
        try {
            GenericValue dataResource = delegator.findOne("DataResource", UtilMisc.toMap("dataResourceId", dataResourceId), true);
            if (dataResource == null) {
                throw new Exception("Error in streamDataResource: DataResource with ID [" + dataResourceId + "] was not found.");
            }
            String dataResourceTypeId = dataResource.getString("dataResourceTypeId");
            if (UtilValidate.isEmpty(dataResourceTypeId)) {
                dataResourceTypeId = "SHORT_TEXT";
            }
            String mimeTypeId = dataResource.getString("mimeTypeId");
            if (UtilValidate.isEmpty(mimeTypeId)) {
                mimeTypeId = "text/html";
            }

            if (dataResourceTypeId.equals("SHORT_TEXT")) {
                String text = dataResource.getString("objectInfo");
                return text;
                //os.write(text.getBytes());
            } else if (dataResourceTypeId.equals("ELECTRONIC_TEXT")) {
                GenericValue electronicText = delegator.findOne("ElectronicText", UtilMisc.toMap("dataResourceId", dataResourceId), true);
                if (electronicText != null) {
                    String text = electronicText.getString("textData");
                    if (text != null) {
//                        os.write(text.getBytes());
                        return text;
                    }
                }
            } else if (dataResourceTypeId.equals("IMAGE_OBJECT")) {
                /*byte[] imageBytes = acquireImage(delegator, dataResource);
                 if (imageBytes != null) {
                 os.write(imageBytes);
                 }*/
            } else if (dataResourceTypeId.equals("LINK")) {
                /*String text = dataResource.getString("objectInfo");
                 os.write(text.getBytes());*/
            } else if (dataResourceTypeId.equals("URL_RESOURCE")) {
                /*URL url = new URL(dataResource.getString("objectInfo"));
                 if (url.getHost() == null) { // is relative
                 String prefix = buildRequestPrefix(delegator, locale, webSiteId, https);
                 String sep = "";
                 //String s = "";
                 if (url.toString().indexOf("/") != 0 && prefix.lastIndexOf("/") != (prefix.length() - 1)) {
                 sep = "/";
                 }
                 String s2 = prefix + sep + url.toString();
                 url = new URL(s2);
                 }
                 InputStream in = url.openStream();
                 UtilIO.copy(in, true, os, false);*/
            } else if (dataResourceTypeId.indexOf("_FILE") >= 0) {
                /*String objectInfo = dataResource.getString("objectInfo");
                 File inputFile = getContentFile(dataResourceTypeId, objectInfo, rootDir);
                 //long fileSize = inputFile.length();
                 FileInputStream fis = new FileInputStream(inputFile);
                 UtilIO.copy(fis, true, os, false);
                 */
            } else {
                throw new Exception("The dataResourceTypeId [" + dataResourceTypeId + "] is not supported in streamDataResource");
            }
        } catch (GenericEntityException e) {
            throw new Exception("Error in streamDataResource", e);
        }
        return "";
    }

    public static List<GenericValue> loadAllProducts(List<EntityCondition> entityCondList, Delegator delegator) {
        Debug.logInfo("getReadOnlyGenericValueListsWithSelection ", module);
      
        List<GenericValue> list = PosProductHelper.getReadOnlyGenericValueListsWithSelection("Product", entityCondList, "productName", delegator);

        return list;
    }
}
