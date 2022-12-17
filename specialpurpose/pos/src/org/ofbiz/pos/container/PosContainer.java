/**
 * *****************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * *****************************************************************************
 */
package org.ofbiz.pos.container;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ofbiz.base.container.ContainerConfig;
import org.ofbiz.base.container.ContainerException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.ProductStoreCatalog;
import org.ofbiz.ordermax.entity.ProductStoreFacility;
import org.ofbiz.product.store.ProductStoreWorker;

public class PosContainer extends XuiContainer {

    public PosContainer(){
                 Debug.logInfo("PosContainer", module);    
    }
    @Override
    public String getContainerConfigName() {
        return "pos-container";
    }

    @Override
    public void configure(ContainerConfig.Container cc) throws ContainerException {
        XuiSession session = XuiContainer.getSession();
        GenericValue partyId = null;

        // get the company party id
        String defaultBillToPartyId = ContainerConfig.getPropertyValue(cc, "ORGANIZATION_PARTY", null);
        Debug.logError("ORGANIZATION_PARTY: " + defaultBillToPartyId, module);
        if (UtilValidate.isNotEmpty(defaultBillToPartyId)) {
            try {
                partyId = session.getDelegator().findByPrimaryKey("Party", UtilMisc.toMap("partyId", defaultBillToPartyId));
            } catch (GenericEntityException ex) {
                Logger.getLogger(PosContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // verify the facility exists
        if (partyId != null) {
            Debug.logError("partyId: " + partyId.getString("partyId"), module);
            session.setCompanyPartyId(defaultBillToPartyId);
        }

        GenericValue facility = null;
        // get the facility id
        String facilityId = ContainerConfig.getPropertyValue(cc, "facility-id", null);
        if (UtilValidate.isEmpty(facilityId)) {
            throw new ContainerException("No facility-id value set in pos-container!");
        } else {
            try {
                facility = session.getDelegator().findByPrimaryKey("Facility", UtilMisc.toMap("facilityId", facilityId));

            } catch (GenericEntityException e) {
                throw new ContainerException("Invalid facilityId : " + facilityId);
            }
        }

        // verify the facility exists
        if (facility != null) {
            session.setAttribute("facilityId", facilityId);
        }

        GenericValue productStore = null;
        // get the product store id
        String productStoreId = ContainerConfig.getPropertyValue(cc, "product-store-id", null);
        if (UtilValidate.isNotEmpty(productStoreId)) {
            try {
                productStore = session.getDelegator().findByPrimaryKey("ProductStore", UtilMisc.toMap("productStoreId", productStoreId));
            } catch (GenericEntityException e) {
                throw new ContainerException("product-store-id : " + productStoreId);
            }
        }

        if (UtilValidate.isNotEmpty(productStore)) {
            facilityId = productStore.getString("inventoryFacilityId");
            session.setAttribute("facilityId", facilityId);
        } else {
            XuiContainer.showConfig();
        }

        // verify the facility exists
        if (facility == null) {
            throw new ContainerException("Invalid facility; facility ID not found [" + facilityId + "]");
        }

        if (UtilValidate.isEmpty(productStoreId)) {
            // get the product store id
            productStoreId = facility.getString("productStoreId");
            if (UtilValidate.isEmpty(productStoreId)) {
                throw new ContainerException("No productStoreId set on facility [" + facilityId + "]!");
            } else {
                productStore = ProductStoreWorker.getProductStore(productStoreId, session.getDelegator());

                if (productStore == null) {
                    throw new ContainerException("Invalid productStoreId : " + productStoreId);
                }
            }
        }

        session.setAttribute("productStoreId", productStoreId);
        session.setAttribute("productStore", productStore);
        session.setProductStore(new ProductStore(productStore));
        // get the product store id

        try {
            List<GenericValue> productStoreCatalogs = session.getDelegator().findByAnd("ProductStoreCatalog", UtilMisc.toMap("productStoreId", productStoreId));
            productStoreCatalogs = EntityUtil.filterByDate(productStoreCatalogs);
            List<ProductStoreCatalog> list = ProductStoreCatalog.getObjectList(productStoreCatalogs);
            if (list.size() > 0) {
//                session.setProductStoreCatalog();
                try {
                    GenericValue prodCatalog = session.getDelegator().findByPrimaryKey("ProdCatalog", UtilMisc.toMap("prodCatalogId", list.get(0).getprodCatalogId()));
                    session.setProdCatalog(new ProdCatalog(prodCatalog));
                } catch (GenericEntityException e) {
                    throw new ContainerException("Invalid prodCatalogId : " + list.get(0).getprodCatalogId());
                }
            } else {
                //no catalog... set to max catalog
                // get the catalogId
                String defCatalogId = ContainerConfig.getPropertyValue(cc, "def-catalog-id", null);
                if (UtilValidate.isEmpty(defCatalogId)) {
                    throw new ContainerException("No defCatalogId value set in pos-container!");
                } else {
                    try {
                        GenericValue prodCatalog = session.getDelegator().findByPrimaryKey("ProdCatalog", UtilMisc.toMap("prodCatalogId", defCatalogId));
                        session.setProdCatalog(new ProdCatalog(prodCatalog));
                    } catch (GenericEntityException e) {
                        throw new ContainerException("Invalid defCatalogId : " + defCatalogId);
                    }
                }
            }
        } catch (GenericEntityException e) {
            throw new ContainerException("Invalid productStoreId : " + productStoreId);
        }

//        ProductDataTreeLoader.CatalogId = session.getProdCatalog().getprodCatalogId();
//        Debug.logError("ProductListArray.CatalogId: " + ProductDataTreeLoader.CatalogId, module);
        if (session.getProdCatalog() != null) {
            try {
                String defCatageoryId = ContainerConfig.getPropertyValue(cc, "def-category-id", null);
                /*                if (UtilValidate.isNotEmpty(defCatageoryId)) {
                 GenericValue val = PosProductHelper.getProductCategory(defCatageoryId, session.getDelegator());
                 if (val != null) {
                 session.setProductCategory(defCatageoryId);
                 ProductDataTreeLoader.categoryRootId = new ProductCategory(val);
                 }
                 } else {
                 */
                List<GenericValue> prodCatalogCategories = session.getDelegator().findByAnd("ProdCatalogCategory", UtilMisc.toMap("prodCatalogId", session.getProdCatalog().getprodCatalogId(), "prodCatalogCategoryTypeId", "PCCT_BROWSE_ROOT"));
                prodCatalogCategories = EntityUtil.filterByDate(prodCatalogCategories);
                //List<ProductStoreCatalog> list = ProdCatalogCategory.getObjectList(prodCatalogCategories);
                if (prodCatalogCategories.size() > 0) {
                    GenericValue prodCatalogCategorie = prodCatalogCategories.get(0);
                    if (prodCatalogCategorie != null) {
                        GenericValue val = PosProductHelper.getProductCategory(prodCatalogCategorie.getString("productCategoryId"), session.getDelegator());
                        session.setProductCategory(new ProductCategory(val));
                    }
                }

//                }
            } catch (GenericEntityException e) {
                throw new ContainerException("Invalid productStoreId : " + productStoreId);
            }
        }
        Debug.logInfo("session.getProductCategoryId: " + session.getProductCategory().getProductCategoryId(), module);

        try {
            List<GenericValue> productStoreFacilitys = session.getDelegator().findByAnd("ProductStoreFacility", UtilMisc.toMap("productStoreId", productStoreId));
            List<ProductStoreFacility> list = ProductStoreFacility.getObjectList(productStoreFacilitys);
        } catch (GenericEntityException e) {
            throw new ContainerException("Invalid productStoreId : " + productStoreId);
        }

        // get the store locale
        String localeStr = ContainerConfig.getPropertyValue(cc, "locale", null);
        if (UtilValidate.isEmpty(localeStr)) {
            localeStr = productStore.getString("defaultLocaleString");
        }

        Debug.logError("Locale for POS: " + localeStr, module);
        if (UtilValidate.isEmpty(localeStr)) {
            throw new ContainerException("Invalid Locale for POS!");
        }
        Locale locale = UtilMisc.parseLocale(localeStr);
        session.setAttribute("locale", locale);

        // get the store currency
        String currencyStr = ContainerConfig.getPropertyValue(cc, "currency", null);
        if (UtilValidate.isEmpty(currencyStr)) {
            currencyStr = productStore.getString("defaultCurrencyUomId");
        }
        if (UtilValidate.isEmpty(currencyStr)) {
            throw new ContainerException("Invalid Currency for POS!");
        }
        session.setAttribute("currency", currencyStr);

        XuiContainer.appConfig();
        /*String screenmode = config.getProperty("machine.screenmode");
         if ("fullscreen".equals(screenmode)) {
         JRootKiosk rootkiosk = new JRootKiosk();
         rootkiosk.initFrame(config);
         } else {
         JRootFrame rootframe = new JRootFrame();
         rootframe.initFrame(config);
         }*/
    }

}
