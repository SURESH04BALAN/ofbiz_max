/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javolution.util.FastList;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.ProductItemPrice;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.ProductPriceList;
import org.ofbiz.ordermax.composite.SupplierProductList;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.orders.OrderFindHelper;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;
import org.ofbiz.service.GenericServiceException;

/**
 *
 * @author siranjeev
 */
public class LoadProductPriceWorker extends LoadBaseSwingWorker< ProductPriceComposite> {

    public LoadProductPriceWorker(ListAdapterListModel<ProductPriceComposite> personListModel, XuiSession session, List<EntityCondition> entityConditionList) {
        super(personListModel, entityConditionList);

        this.session = session;
    }

    @Override
    protected List<ProductPriceComposite> doInBackground() throws Exception {

        List<ProductPriceComposite> productPriceComposites = new ArrayList<ProductPriceComposite>();
        Map<String, Object> result = null;

        List<GenericValue> resultList = PosProductHelper.getReadOnlyGenericValueListsWithSelection("ProductPrice", entityConditionList, null, session.getDelegator(), true);

        maxProgress = resultList.size() + 1;
//                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
        for (GenericValue gv : resultList) {

            ProductPriceComposite ppc = new ProductPriceComposite();
            ProductPrice price = new ProductPrice(gv);
            ppc.setProductPrice(price);

            ppc.setProductPriceType(ProductPriceTypeSingleton.getProductPriceType(price.getproductPriceTypeId()));
            productPriceComposites.add(ppc);
            int prograss = calcProgress(progressedItems + 1);

            //if ((prograss + 1) % 4 == 0) {
                publish(ppc);
           // } else {
            //    progressedItems++;
           // }

            if (isCancelled()) {
                break;
            }

        }

        return productPriceComposites;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    private String partyId = "";

    //static ProductPriceTypeList pptArray = null;
    public static ProductItemPrice getProductItemPrice(String productId, XuiSession session) throws GenericEntityException {
        ProductItemPrice productItemPrice = new ProductItemPrice();

        //set it to product item price
        //Average cost
        String productPriceTypeId = "AVERAGE_COST";
        productItemPrice.setAverageCost(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "BOX_PRICE";
        productItemPrice.setBoxPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "COMPETITIVE_PRICE";
        productItemPrice.setCompetitivePrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "DEFAULT_PRICE";
        productItemPrice.setDefaultPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "LIST_PRICE";
        productItemPrice.setListPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "MAXIMUM_PRICE";
        productItemPrice.setMaximumPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "MINIMUM_ORDER_PRICE";
        productItemPrice.setMinimumOrderPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "MINIMUM_PRICE";
        productItemPrice.setMinimumPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "PROMO_PRICE";
        productItemPrice.setPromoPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "SPECIAL_PROMO_PRICE";
        productItemPrice.setSpecialPromoPrice(getProductPriceList(productId, productPriceTypeId, session));

        productPriceTypeId = "WHOLESALE_PRICE";
        productItemPrice.setWholesalePrice(getProductPriceList(productId, productPriceTypeId, session));

        return productItemPrice;
    }

    static public ProductPriceList getProductPriceList(String productId, String productPriceTypeId, XuiSession session) throws GenericEntityException {
        ProductPriceList pplist = new ProductPriceList();
        try {
            Debug.logInfo(" productId: " + productId + " productPriceTypeId:" + productPriceTypeId, module);
            //get the price list
            List<GenericValue> priceList = PosProductHelper.getProductPriceByProductPriceType(productId, productPriceTypeId, session.getDelegator());
            //get the price type

            //create holding list
            List<GenericValue> filteredPriceList = new ArrayList<GenericValue>();
            for (GenericValue gen : priceList) {
                ProductPrice price = new ProductPrice(gen);
                if (price.getthruDate() == null || price.getthruDate().getTime() >= UtilDateTime.nowTimestamp().getTime()) {
                    filteredPriceList.add(gen);
                }
            }

            pplist.createAllElement(filteredPriceList);

        } catch (Exception ex) {
            Logger.getLogger(LoadProductPriceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pplist;
    }
    /*
     static public SupplierProductList getSupplierProduct(String productId, XuiSession session) throws GenericEntityException {
     SupplierProductList spl = new SupplierProductList();
     //get the price list
     List<GenericValue> priceList = PosProductHelper.getSupplierProduct(productId, session.getDelegator());

     spl.createAllElement(priceList);

     return spl;

     }
     */

    static public ProductPriceComposite createProductPriceComposite(String productId, XuiSession session) {
        return createProductPriceComposite(productId, "DEFAULT_PRICE", session);
    }

    static public ProductPriceComposite createProductPriceComposite(String productId, String productPriceTypeId, XuiSession session) {

        ProductPriceComposite ppc = new ProductPriceComposite();
        ProductPrice price = createProductPrice(productId, productPriceTypeId, session);

        ppc.setProductPrice(price);
        try {
            ppc.setProductPriceType(ProductPriceTypeSingleton.getProductPriceType(productPriceTypeId));
        } catch (Exception ex) {
            Logger.getLogger(LoadProductPriceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ppc;
    }

    static public ProductPrice createProductPrice(String productId, String productPriceTypeId, XuiSession session) {

        ProductPrice price = new ProductPrice();
        price.setproductPriceTypeId(productPriceTypeId);
        price.setproductId(productId);
        price.setproductPricePurposeId("PURCHASE");
        price.setcurrencyUomId((String) session.getAttribute("currency"));
        price.setproductStoreGroupId("_NA_");
        price.setfromDate(UtilDateTime.nowTimestamp());
        price.settaxInPrice("Y");
        price.setthruDate(null);

        return price;
    }

    static public GenericValue saveProductPrice(ProductPrice prodprice, XuiSession session) {
        GenericValue supplierProduct = null;
        Debug.logInfo("getproductId: " + prodprice.getproductId(), "module");
        Debug.logInfo("getproductPricePurposeId: " + prodprice.getproductPricePurposeId(), "module");
        Debug.logInfo("getproductPriceTypeId: " + prodprice.getproductPriceTypeId(), "module");
        Debug.logInfo("getproductStoreGroupId: " + prodprice.getproductStoreGroupId(), "module");
        Debug.logInfo("getcurrencyUomId: " + prodprice.getcurrencyUomId(), "module");
        Debug.logInfo("getfromDate: " + prodprice.getfromDate().toString(), "module");

        GenericValue prodPriceGV = getProductPrice(prodprice.getproductId(),
                prodprice.getproductPriceTypeId(),
                prodprice.getproductPricePurposeId(),
                prodprice.getproductStoreGroupId(),
                prodprice.getcurrencyUomId(),
                session.getDelegator());

        if (prodPriceGV == null) {
            Debug.logInfo("***********NOT FOUND CREATE Product price", "module");
            try {
                Map<String, Object> params = prodprice.getValuesMap();
                params.put("userLogin", session.getUserLogin());
                params.put("locale", Locale.getDefault());
                if (params.get("fromDate") != null) {
                    java.sql.Timestamp fromDate = (java.sql.Timestamp) params.get("fromDate");
                    fromDate = OrderFindHelper.noTimeDate(fromDate);
                    params.put("fromDate", fromDate);
                }

                Debug.logWarning("create Product price product Id: " + prodprice.getproductId(), module);
                Debug.logWarning("create Product price product price: " + prodprice.getprice(), module);
                Debug.logWarning("create Product price product getproductPriceTypeId: " + prodprice.getproductPriceTypeId(), module);
//                params.put("price", BigDecimal.ONE);
                Map<String, Object> resultMap = session.getDispatcher().runSync("createProductPrice", params);
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
                System.out.println("create Product price");

            } catch (Exception e) {
                Debug.logWarning(UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
            }
        } else {
            Debug.logInfo("*********** FOUND UPDATE Product price", "module");
            try {
                ProductPrice updateExisting = new ProductPrice(prodPriceGV);
                Map<String, Object> params = updateExisting.getValuesMap();
                params.put("userLogin", session.getUserLogin());
                params.put("locale", Locale.getDefault());

                //set the end date..
//                java.sql.Timestamp thruDate = UtilDateTime.nowTimestamp();
                //current date as end date
                //get new from date
                java.sql.Timestamp fromDate = prodprice.getfromDate();
                java.sql.Timestamp thruDate = OrderFindHelper.getEndateForDateWithNoTime(fromDate);
                if (params.containsKey("thruDate")) {
                    if (params.get("thruDate") != null) {
                        thruDate = OrderFindHelper.noTimeEndOfDayDate((java.sql.Timestamp) params.get("thruDate"));
                    }
                }

                //same day
                if (fromDate.getTime() > thruDate.getTime()) {
                    params.put("thruDate", fromDate);
                } else {
                    params.put("thruDate", thruDate);
                }

                Map<String, Object> resultMap = session.getDispatcher().runSync("updateProductPrice", params);
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
                Debug.logInfo("update Product price", "module");

                //create new one
                prodprice.setfromDate(UtilDateTime.nowTimestamp());
                params = prodprice.getValuesMap();
                params.put("userLogin", session.getUserLogin());
                params.put("locale", Locale.getDefault());
//                params.put("fromDate", fromDate);
                params.put("thruDate", null);

                resultMap = session.getDispatcher().runSync("createProductPrice", params);
                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                }
                Debug.logInfo("create Product price", "module");

            } catch (Exception e) {
                Debug.logWarning(UtilProperties.getMessage(org.ofbiz.order.shoppingcart.ShoppingCart.resource_error, "OrderRunServiceGetSuppliersForProductError", Locale.getDefault()) + e.getMessage(), module);
            }

        }

        return supplierProduct;
    }

    public static GenericValue getExactProductPrice(String productId,
            String productPriceTypeId,
            String productPricePurposeId,
            String productStoreGroupId,
            String currencyUomId,
            java.sql.Timestamp fromDate,
            Delegator delegator) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        params.put("productPriceTypeId", productPriceTypeId);
        params.put("productPricePurposeId", productPricePurposeId);
        params.put("currencyUomId", currencyUomId);
        params.put("productStoreGroupId", productStoreGroupId);
        params.put("fromDate", fromDate);

        GenericValue priceGV = null;
        try {
            priceGV = delegator.findByPrimaryKey("ProductPrice", params);
        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product", module);
        }
        return priceGV;
    }

    public static GenericValue getProductPrice(String productId, String productPriceTypeId,
            String productPricePurposeId, String productStoreGroupId,
            String currencyUomId, Delegator delegator) {
        GenericValue priceGV = null;

        try {

            List<EntityExpr> exprs = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId));
            exprs.add(EntityCondition.makeCondition("productPriceTypeId", EntityOperator.EQUALS, productPriceTypeId));
            exprs.add(EntityCondition.makeCondition("currencyUomId", EntityOperator.EQUALS, currencyUomId));
            exprs.add(EntityCondition.makeCondition("productPricePurposeId", EntityOperator.EQUALS, productPricePurposeId));
            exprs.add(EntityCondition.makeCondition("productStoreGroupId", EntityOperator.EQUALS, productStoreGroupId));

            List<GenericValue> priceList = delegator.findList("ProductPrice", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, null, false);
            if (priceList != null) {
                priceList = EntityUtil.filterByDate(priceList);
                Iterator<GenericValue> it = priceList.iterator();
                while (it.hasNext()) {
                    priceGV = it.next();
                    break;
                }
            }

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of product", module);
        }
        return priceGV;
    }

    public static BigDecimal getProductAveragePrice(String productId, String organizationPartyId, String facilityId, XuiSession session) {

        List<GenericValue> valList = PosProductHelper.getProductAverageCostList(productId, organizationPartyId, facilityId, session.getDelegator());
        if (valList.size() > 0) {
            GenericValue val = valList.get(0);
            return val.getBigDecimal("averageCost");
        } else {
            return getAverageCostPrice(productId, session);
        }
    }

    public static BigDecimal getAverageCostPrice(String productId, XuiSession session) {
        try {
            String productPriceTypeId = "AVERAGE_COST";
            List<GenericValue> valList = PosProductHelper.getProductPriceByProductPriceType(productId, productPriceTypeId, session.getDelegator());

            if (valList.size() > 0) {
                GenericValue val = valList.get(0);
                return val.getBigDecimal("price");
            } else {
                return BigDecimal.ZERO;
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadProductPriceWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BigDecimal.ZERO;
    }

}
