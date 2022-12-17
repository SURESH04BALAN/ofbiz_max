/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.orderbase.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;

import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class InventoryLotIdMethodEnumSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, String> valueMap = null;

    private InventoryLotIdMethodEnumSingleton() {
        valueMap = new HashMap<String, String>();
    }

    private static class SingletonHolder {

        public static final InventoryLotIdMethodEnumSingleton INSTANCE = new InventoryLotIdMethodEnumSingleton();
    }

    public static InventoryLotIdMethodEnumSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<String> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<String>(getInstance().valueMap.values());
    }

    final static public ListModel<String> getValueListModal() {
        ListAdapterListModel<String> modal = new ListAdapterListModel<String>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<String> getValueListModal(String typeId) {

        ListModel<String> modalRet = null;
        if (typeId != null) {
            if (getInstance().valueMap.isEmpty()) {
                loadAll();
            }

            ListAdapterListModel<String> modal = new ListAdapterListModel<String>();
            for (Map.Entry<String, String> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().equals(typeId)) {
                    modal.add(entry.getValue());
                }
            }
            modalRet = modal;
        } else {
            modalRet = getValueListModal();
        }

        return modalRet;
    }
    
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            String Allowed = "Allowed";            
            String Mandatory = "Mandatory";
            String Forbidden = "Forbidden";            
            
            getInstance().valueMap.put("Allowed", Allowed);
            getInstance().valueMap.put("Mandatory", Mandatory);
            getInstance().valueMap.put("Forbidden", Forbidden);


        } catch (Exception ex) {
            Logger.getLogger(InventoryLotIdMethodEnumSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getString(String statusId) throws Exception {
        String string = null;
        if (getInstance().valueMap.containsKey(statusId)) {
            string = getInstance().valueMap.get(statusId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(statusId)) {
                string = getInstance().valueMap.get(statusId);
            } else {
                throw new Exception("unable to load uom : " + statusId);
            }
        }

        return string;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        InventoryLotIdMethodEnumSingleton.singletonSesion = singletonSesion;
    }
}
//Exception thrown while storing entity value: Error while updating: [GenericEntity:Product][amountUomTypeId,null()][autoCreateKeywords,N(java.lang.String)][billOfMaterialLevel,null()][brandName,BRITANIA(java.lang.String)][chargeShipping,N(java.lang.String)][comments,null()][configId,null()][createdByUserLogin,null()][createdDate,2013-11-16 17:25:40.0(java.sql.Timestamp)][createdStamp,2013-11-16 17:25:40.0(java.sql.Timestamp)][createdTxStamp,2013-11-16 17:25:40.0(java.sql.Timestamp)][defaultShipmentBoxTypeId,null()][depthUomId,null()][description,BABAJI SUJI TOAST- BRITANNIA(java.lang.String)][detailImageUrl,/images/products/BB6377/large-200.jpg(java.lang.String)][detailScreen,null()][diameterUomId,null()][facilityId,null()][fixedAmount,null()][heightUomId,null()][inShippingBox,null()][includeInPromotions,Y(java.lang.String)][internalName,BABAJI SUJI TOAST- BRITANNIA(java.lang.String)][introductionDate,null()][inventoryMessage,Test(java.lang.String)][isVariant,N(java.lang.String)][isVirtual,N(java.lang.String)][largeImageUrl,/images/products/BB6377/large.jpg(java.lang.String)][lastModifiedByUserLogin,admin(java.lang.String)][lastModifiedDate,2014-09-28 17:05:00.697(java.sql.Timestamp)][lastUpdatedStamp,2014-09-28 17:05:00.697(java.sql.Timestamp)][lastUpdatedTxStamp,2014-09-28 17:05:00.009(java.sql.Timestamp)][longDescription,null()][lotIdFilledIn,Allowed(java.lang.String)][manufacturerPartyId,null()][mediumImageUrl,/images/products/BB6377/small-60.jpg(java.lang.String)][orderDecimalQuantity,null()][originGeoId,null()][originalImageUrl,/images/products/BB6377/original.jpg(java.lang.String)][piecesIncluded,null()][priceDetailText,null()][primaryProductCategoryId,null()][productDepth,null()][productDiameter,null()][productHeight,0.000000(java.math.BigDecimal)][productId,BB6377(java.lang.String)][productName,BABAJI SUJI TOAST- BRITANNIA(java.lang.String)][productRating,null()][productTypeId,FINISHED_GOOD(java.lang.String)][productWeight,null()][productWidth,null()][quantityIncluded,null()][quantityUomId,null()][ratingTypeEnum,null()][releaseDate,null()][requireAmount,null()][requireInventory,N(java.lang.String)][requirementMethodEnumId,DROPS(java.lang.String)][reserv2ndPPPerc,null()][reservMaxPersons,null()][reservNthPPPerc,null()][returnable,Y(java.lang.String)][salesDiscWhenNotAvail,N(java.lang.String)][salesDiscontinuationDate,null()][shippingDepth,null()][shippingHeight,null()][shippingWidth,null()][smallImageUrl,/images/products/BB6377/small.jpg(java.lang.String)][supportDiscontinuationDate,null()][taxable,N(java.lang.String)][virtualVariantMethodEnum,null()][weight,1.000000(java.math.BigDecimal)][weightUomId,WT_lt(java.lang.String)][widthUomId,null()] (SQL Exception while executing the following:UPDATE PRODUCT SET PRODUCT_TYPE_ID=?, PRIMARY_PRODUCT_CATEGORY_ID=?, MANUFACTURER_PARTY_ID=?, FACILITY_ID=?, INTRODUCTION_DATE=?, RELEASE_DATE=?, SUPPORT_DISCONTINUATION_DATE=?, SALES_DISCONTINUATION_DATE=?, SALES_DISC_WHEN_NOT_AVAIL=?, INTERNAL_NAME=?, BRAND_NAME=?, COMMENTS=?, PRODUCT_NAME=?, DESCRIPTION=?, LONG_DESCRIPTION=?, PRICE_DETAIL_TEXT=?, SMALL_IMAGE_URL=?, MEDIUM_IMAGE_URL=?, LARGE_IMAGE_URL=?, DETAIL_IMAGE_URL=?, ORIGINAL_IMAGE_URL=?, DETAIL_SCREEN=?, INVENTORY_MESSAGE=?, REQUIRE_INVENTORY=?, QUANTITY_UOM_ID=?, QUANTITY_INCLUDED=?, PIECES_INCLUDED=?, REQUIRE_AMOUNT=?, FIXED_AMOUNT=?, AMOUNT_UOM_TYPE_ID=?, WEIGHT_UOM_ID=?, WEIGHT=?, PRODUCT_WEIGHT=?, HEIGHT_UOM_ID=?, PRODUCT_HEIGHT=?, SHIPPING_HEIGHT=?, WIDTH_UOM_ID=?, PRODUCT_WIDTH=?, SHIPPING_WIDTH=?, DEPTH_UOM_ID=?, PRODUCT_DEPTH=?, SHIPPING_DEPTH=?, DIAMETER_UOM_ID=?, PRODUCT_DIAMETER=?, PRODUCT_RATING=?, RATING_TYPE_ENUM=?, RETURNABLE=?, TAXABLE=?, CHARGE_SHIPPING=?, AUTO_CREATE_KEYWORDS=?, INCLUDE_IN_PROMOTIONS=?, IS_VIRTUAL=?, IS_VARIANT=?, VIRTUAL_VARIANT_METHOD_ENUM=?, ORIGIN_GEO_ID=?, REQUIREMENT_METHOD_ENUM_ID=?, BILL_OF_MATERIAL_LEVEL=?, RESERV_MAX_PERSONS=?, RESERV2ND_P_P_PERC=?, RESERV_NTH_P_P_PERC=?, CONFIG_ID=?, CREATED_DATE=?, CREATED_BY_USER_LOGIN=?, LAST_MODIFIED_DATE=?, LAST_MODIFIED_BY_USER_LOGIN=?, IN_SHIPPING_BOX=?, DEFAULT_SHIPMENT_BOX_TYPE_ID=?, LOT_ID_FILLED_IN=?, ORDER_DECIMAL_QUANTITY=?, LAST_UPDATED_STAMP=?, LAST_UPDATED_TX_STAMP=?, CREATED_STAMP=?, CREATED_TX_STAMP=? WHERE PRODUCT_ID=? (Cannot add or update a child row: a foreign key constraint fails (`ofbiz`.`product`, CONSTRAINT `PROD_RQMT_ENUM` FOREIGN KEY (`REQUIREMENT_METHOD_ENUM_ID`) REFERENCES `enumeration` (`ENUM_ID`))))