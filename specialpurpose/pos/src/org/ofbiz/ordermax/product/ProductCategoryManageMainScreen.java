/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
/**
 *
 * @author siranjeev
 */
public class ProductCategoryManageMainScreen extends ProductCatalogMainScreen{
        public static final String module = ProductCategoryManageMainScreen.class.getName();
//    static String PRODUCT_TAB_INDEX = "PRODUCT";
    static String CATALOG_CATEGORY_TAB_INDEX = "CATALOGCATEGORY";
    static String PRODUCT_CATEGORY_TAB_INDEX = "PRODUCTCATEGORY";
    protected BaseMainPanelInterface baseMainPanelInterface = null;
    protected String visibleCardName = null;
    protected JPanel productCardPanel = null;
    protected ProdCatalogPanel prodCatalogPanel = null;
    protected ProductCategoryPanel productCategoryPanel = null;
   static public ProductCategoryManageMainScreen runController(ControllerOptions options) {

        ProductCategoryManageMainScreen controller = new ProductCategoryManageMainScreen(options);
        if (options.getDesktopPane() == null) {
            controller.loadSinglePanelNonSizeableFrameDialogScreen(ProductCategoryManageMainScreen.module, options.getDesktopPane(), null);
        } else {
           controller.loadThreePanelInternalFrame(ProductCategoryManageMainScreen.module, options.getDesktopPane());
        }
        return controller;
    }
    public ProductCategoryManageMainScreen( ControllerOptions options) {
        super( options );

    }

}
