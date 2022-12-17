/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic.entitypanelsimpl;

import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.generic.GenericSaveInterface;

/**
 *
 * @author BBS Auctions
 */
public class EntityPanelFactory {

    public static GenericSaveInterface createEntityPanel(ControllerOptions options) {
        String entityName = null;
        if (options.contains("EntityName")) {
            entityName = (String) options.get("EntityName");
        }

        if (UtilValidate.isNotEmpty(entityName)) {
            if (entityName.equalsIgnoreCase("Facility")) {
                return new SimpleFacilityPanel(options);
            } else if (entityName.equalsIgnoreCase("PosTerminal")) {
                return new SimplePosTerminalPanel(options);
            } else if (entityName.equalsIgnoreCase("ProductStore")) {
                return new SimpleProductStorePanel(options);
            } else if (entityName.equalsIgnoreCase("ProdCatalog")) {
                return new SimpleProdCatalogPanel(options);
            } else if (entityName.equalsIgnoreCase("ProductCategory")) {
                return new SimpleProductCategoryPanel(options);
            } else if (entityName.equalsIgnoreCase("ProdCatalogCategory")) {
                return new SimpleProdCatalogCategoryPanel(options);
            } else if (entityName.equalsIgnoreCase("ProductStoreCatalog")) {
                return new SimpleProductStoreCatalogPanel(options);
            } else if (entityName.equalsIgnoreCase("UserLoginSecurityGroup")) {
                return new SimpleUserLoginSecurityGroupPanel(options);
            } else if (entityName.equalsIgnoreCase("ProductStoreGroup")) {
                return new SimpleProductStoreGroupPanel(options);
            } else if (entityName.equalsIgnoreCase("ShoppingListItem")) {
                return new SimpleShoppingListItemPanel(options);
            } else if (entityName.equalsIgnoreCase("ShoppingList")) {
                return new SimpleShoppingListPanel(options);
            } else if (entityName.equalsIgnoreCase("ProductFeature")) {
                return new SimpleProductFeaturePanel(options);
            } else if (entityName.equalsIgnoreCase("ProductFeatureGroup")) {
                return new SimpleProductFeatureGroupPanel(options);
            } else if (entityName.equalsIgnoreCase("ProductFeatureGroupAppl")) {
                return new SimpleProductFeatureGroupApplPanel(options);
            }
        }
        throw new IllegalArgumentException("EntityPanelFactory: No such Entity Panel");
    }

}
