/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.tree;

import java.util.HashMap;
import org.ofbiz.ordermax.base.ControllerOptions;
import static org.ofbiz.pos.report.InventoryReportPanel.name;

/**
 *
 * @author siranjeev
 */
public class ProductTreeFindFactory {

    private static final HashMap<String, ProductTreeFindPanelFactoryInterface> productTreeFindPanelRegistry;

    static {
        productTreeFindPanelRegistry = new HashMap<String, ProductTreeFindPanelFactoryInterface>();
        ProductTreeFindFactory.registerPanel("Product Id", new PanelProductId.CreationFactoryClass());
        ProductTreeFindFactory.registerPanel("Scan Code", new PanelGoodsIdentificationId.CreationFactoryClass());
        ProductTreeFindFactory.registerPanel("Supplier Product Code", new PanelSupplierProductId.CreationFactoryClass());
        ProductTreeFindFactory.registerPanel("Product Description", new ProductSelectionTreePanel.CreationFactoryClass());        
    }

    private static class ProductTreeFindFactorySingletonHolder {

        public static final ProductTreeFindFactory INSTANCE = new ProductTreeFindFactory();
    }

    public static ProductTreeFindFactory getInstance() {
        return ProductTreeFindFactorySingletonHolder.INSTANCE;
    }

    static public void registerPanel(String actionType, ProductTreeFindPanelFactoryInterface p) {
        productTreeFindPanelRegistry.put(actionType, p);
    }

    static public ProductTreeFindInterface createPanel(String actionType, ControllerOptions options) {
        return productTreeFindPanelRegistry.get(actionType).createFind(options);
    }

    public static HashMap<String, ProductTreeFindPanelFactoryInterface> getRegisteredTreeFindPanels() {
        return productTreeFindPanelRegistry;
    }
}
