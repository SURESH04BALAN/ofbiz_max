/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainPanelInterface;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProdCatalogCategory;
import org.ofbiz.ordermax.entity.ProductStoreCatalog;

/**
 *
 * @author siranjeev
 */
public class ProductCatalogMaintainController extends BaseMainScreen {

    public static String module = ProductCatalogMaintainController.class.getName();
//    public BaseMainPanelInterface panel = null;
    protected BaseMainPanelInterface baseMainPanelInterface = null;

    public ProductCatalogMaintainController(String name, XuiSession sess) {
        super(sess);

        module = name;

    }

    @Override
    protected void setSizeAndLocation(JInternalFrame contFrame) {
        int y = 10;
        int x = 100;
        contFrame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        contFrame.setLocation(x, y);
    }
    javax.swing.JTabbedPane tabbedPanePrice = null;
    ProdCatalogMaintainPanel prodCatalogMaintainPanel = null;
    ProductStoreCatalogMaintainPanel productStoreCatalogMaintainPanel = null;
    ProductCatalogCategoryMaintainPanel productCatalogCategoryMaintainPanel = null;

    public void loadScreen(final ContainerPanelInterface f) {
        tabbedPanePrice = new javax.swing.JTabbedPane();
        prodCatalogMaintainPanel = new ProdCatalogMaintainPanel();
        productStoreCatalogMaintainPanel = new ProductStoreCatalogMaintainPanel();
        productCatalogCategoryMaintainPanel = new ProductCatalogCategoryMaintainPanel();

        f.getPanelDetail().setLayout(new BorderLayout());
        f.getPanelDetail().add(BorderLayout.CENTER, tabbedPanePrice);
        //ProductCatalogButtonPanel buttonPanel = new ProductCatalogButtonPanel();

        tabbedPanePrice.add("Product Catalog", prodCatalogMaintainPanel);
        tabbedPanePrice.add("Stores", productStoreCatalogMaintainPanel);
        tabbedPanePrice.add("Categories", productCatalogCategoryMaintainPanel);

        tabbedPanePrice.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                if (e.getSource() instanceof JTabbedPane) {

                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    if (pane.getSelectedIndex() == 1) {
                        ListAdapterListModel<ProductStoreCatalog> listModel = new ListAdapterListModel<ProductStoreCatalog>();
                        if (UtilValidate.isNotEmpty(prodCatalogMaintainPanel.getProdCatalog())) {
                            List<GenericValue> productStoreCatalogList = PosProductHelper.getGenericValueLists("ProductStoreCatalog", "prodCatalogId", prodCatalogMaintainPanel.getProdCatalog().getprodCatalogId(), ControllerOptions.getSession().getDelegator());
                            //Debug.logInfo("ContactMechPurposeType: start ", NAME);

                            for (GenericValue gv : productStoreCatalogList) {
                                ProductStoreCatalog val = new ProductStoreCatalog(gv);
                                listModel.add(val);
//            Debug.logInfo("ContactMechPurposeType: " + val.getdescription(), NAME);
                            }
                        }
                        if (listModel.getSize() > 0) {
                            productStoreCatalogMaintainPanel.setProductStoreCatalogList(listModel);
                            ProductStoreCatalog productStoreCatalog = listModel.getElementAt(0);
                            productStoreCatalogMaintainPanel.setProductStoreCatalog(productStoreCatalog);
                            productStoreCatalogMaintainPanel.setDialogFields(productStoreCatalog);
                        } else {
                            ProductStoreCatalog productStoreCatalog = new ProductStoreCatalog();
                            if (UtilValidate.isNotEmpty(prodCatalogMaintainPanel.getProdCatalog())) {
                                productStoreCatalog.setprodCatalogId(prodCatalogMaintainPanel.getProdCatalog().getprodCatalogId());
                            }
                            productStoreCatalogMaintainPanel.setProductStoreCatalog(productStoreCatalog);
                            productStoreCatalogMaintainPanel.setDialogFields(productStoreCatalog);
                        }

                    } else if (pane.getSelectedIndex() == 2) {
                        ListAdapterListModel<ProdCatalogCategory> listModel = new ListAdapterListModel<ProdCatalogCategory>();
                        if (UtilValidate.isNotEmpty(prodCatalogMaintainPanel.getProdCatalog())) {
                            List<GenericValue> productStoreCatalogList = PosProductHelper.getGenericValueLists("ProdCatalogCategory", "prodCatalogId", prodCatalogMaintainPanel.getProdCatalog().getprodCatalogId(), ControllerOptions.getSession().getDelegator());
                            //Debug.logInfo("ContactMechPurposeType: start ", NAME);

                            for (GenericValue gv : productStoreCatalogList) {
                                ProdCatalogCategory val = new ProdCatalogCategory(gv);
                                listModel.add(val);
//            Debug.logInfo("ContactMechPurposeType: " + val.getdescription(), NAME);
                            }
                        }
                        if (listModel.getSize() > 0) {
                            productCatalogCategoryMaintainPanel.setProductStoreCatalogList(listModel);
                            ProdCatalogCategory productStoreCatalog = listModel.getElementAt(0);
                            productCatalogCategoryMaintainPanel.setProductStoreCatalog(productStoreCatalog);
                            productCatalogCategoryMaintainPanel.setDialogFields(productStoreCatalog);
                        } else {
                            ProdCatalogCategory productStoreCatalog = new ProdCatalogCategory();
                            productStoreCatalog.setprodCatalogId(prodCatalogMaintainPanel.getProdCatalog().getprodCatalogId());
                            productCatalogCategoryMaintainPanel.setProductStoreCatalog(productStoreCatalog);
                            productCatalogCategoryMaintainPanel.setDialogFields(productStoreCatalog);
                        }

                    }
                    System.out.println("Selected paneNo : " + pane.getSelectedIndex());
                }
            }
        });

//        OrderMaxUtility.addAPanelGrid(productPriceEditTablePanel, );
//        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), f.getPanelSelecton());
//        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        f.getPanelDetail().setLayout(new java.awt.BorderLayout());
        f.getPanelDetail().add(BorderLayout.CENTER, tabbedPanePrice);

        
        setScreenButtons(f,prodCatalogMaintainPanel);
        setScreenButtons(f,productStoreCatalogMaintainPanel);
        setScreenButtons(f,productCatalogCategoryMaintainPanel);
    }

    public void addItem(String id) throws Exception {
    }

    public void refreshScreen() {
    }

    @Override
    public void addItem(String id, BigDecimal price, BigDecimal qty) throws Exception {
//        panel.addItem(id, price, qty);
    }

    @Override
    public String getClassName() {
        return module; //To change body of generated methods, choose Tools | Templates.
    }
}
