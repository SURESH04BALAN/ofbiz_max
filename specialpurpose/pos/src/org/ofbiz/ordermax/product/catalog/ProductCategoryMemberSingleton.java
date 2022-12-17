/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.datasource.GenericHelperInfo;
import org.ofbiz.entity.jdbc.SQLProcessor;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductCategory;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryMemberSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductCategory> valueMap = null;

    private ProductCategoryMemberSingleton() {
        valueMap = new HashMap<String, ProductCategory>();
    }

    private static class SingletonHolder {

        public static final ProductCategoryMemberSingleton INSTANCE = new ProductCategoryMemberSingleton();
    }

    public static ProductCategoryMemberSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductCategory> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<ProductCategory>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductCategory> getValueListModal() {
        ListAdapterListModel<ProductCategory> modal = new ListAdapterListModel<ProductCategory>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            String str = "SELECT * FROM ofbiz.product_category as p\n"
                    + "     WHERE EXISTS (\n"
                    + "         SELECT distinct pc.PRODUCT_CATEGORY_ID  FROM ofbiz.product_category_MEMBER as pc \n"
                    + "             where p.PRODUCT_CATEGORY_ID  = pc.PRODUCT_CATEGORY_ID \n"
                    + "                 )\n"
                    + "order by p.CATEGORY_NAME";
            GenericHelperInfo helperName = ControllerOptions.getSession().getDelegator().getGroupHelperInfo("org.ofbiz");
            SQLProcessor sqlproc = new SQLProcessor(helperName);
            sqlproc.prepareStatement(str);
            ResultSet rs = sqlproc.executeQuery();

            while (rs.next()) {
                ProductCategory cat = new ProductCategory();
                cat.setProductCategoryId(rs.getString("PRODUCT_CATEGORY_ID"));
                cat.setProductCategoryTypeId(rs.getString("PRODUCT_CATEGORY_TYPE_ID"));
                cat.setPrimaryParentCategoryId(rs.getString("PRIMARY_PARENT_CATEGORY_ID"));
                cat.setCategoryName(rs.getString("CATEGORY_NAME"));
                cat.setDescription(rs.getString("DESCRIPTION"));
                cat.setLongDescription(rs.getString("LONG_DESCRIPTION"));
                cat.setCategoryImageUrl(rs.getString("CATEGORY_IMAGE_URL"));
                cat.setLinkOneImageUrl(rs.getString("LINK_ONE_IMAGE_URL"));
                cat.setLinkTwoImageUrl(rs.getString("LINK_TWO_IMAGE_URL"));
                cat.setDetailScreen(rs.getString("DETAIL_SCREEN"));
                cat.setShowInSelect(rs.getString("SHOW_IN_SELECT"));
                cat.setLastUpdatedStamp(rs.getTimestamp("LAST_UPDATED_STAMP"));
                cat.setLastUpdatedTxStamp(rs.getTimestamp("LAST_UPDATED_TX_STAMP"));
                cat.setCreatedStamp(rs.getTimestamp("CREATED_STAMP"));
                cat.setCreatedTxStamp(rs.getTimestamp("CREATED_TX_STAMP"));
                getInstance().valueMap.put(cat.getProductCategoryId(), cat);
    //cat.setdrs.getTimestamp("CATEGORY_DATA")

            }

            rs.close();
            sqlproc.close();

//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
          /*  List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductCategory", whereClauseMap, null, ProductCategoryMemberSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ProductCategory prodCatalog = new ProductCategory(val);

                getInstance().valueMap.put(prodCatalog.getProductCategoryId(), prodCatalog);
            }*/
        } catch (Exception ex) {
            Logger.getLogger(ProductCategoryMemberSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ProductCategory getProductCategory(String productCategoryId) throws Exception {
        ProductCategory geoList = null;
        if (getInstance().valueMap.containsKey(productCategoryId)) {
            geoList = getInstance().valueMap.get(productCategoryId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(productCategoryId)) {
                geoList = getInstance().valueMap.get(productCategoryId);
            } else {
                throw new Exception("unable to load productCategoryId: " + productCategoryId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ProductCategoryMemberSingleton.singletonSession = singletonSession;
    }
}


