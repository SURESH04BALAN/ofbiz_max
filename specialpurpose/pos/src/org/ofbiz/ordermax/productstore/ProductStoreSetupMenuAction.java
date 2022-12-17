/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.productstore;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JMenuItem;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.generic.GenericSaveInterface;
import org.ofbiz.ordermax.generic.GenericSavePanel;
import org.ofbiz.ordermax.generic.GenericSelectionPanel;
import org.ofbiz.ordermax.generic.entitypanelsimpl.EntityPanelFactory;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/**
 *
 * @author BBS Auctions
 */
public class ProductStoreSetupMenuAction {

    static public void addProductStoreMenus(ControllerOptions options, javax.swing.JMenu parentMenu) {
        
        ControllerOptions controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "Facility");
        ProductStoreConfigMenuAction productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Facility", controllerOptions);
        JMenuItem mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);
//        productStoreConfigMenuAction.createActionMenuItem(parentMenu);

        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductStoreGroup");
        productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Store Group", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);
        
        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductStore");
        productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Store", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);


        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProdCatalog");
        productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Catalog", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);
        

        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductCategory");        
        productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Category", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);    
        
        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProdCatalogCategory");        
        productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Catalog Category", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);    

        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "ProductStoreCatalog");        
        productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Product Store Catalog", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);    
        
        controllerOptions = new ControllerOptions(options);
        controllerOptions.put("EntityName", "PosTerminal");        
        productStoreConfigMenuAction = new ProductStoreConfigMenuAction("Pos Terminal", controllerOptions);
        mnuItem = productStoreConfigMenuAction.createActionMenuItem();
        parentMenu.add(mnuItem);                   
    }

    //menu actions
    static public class ProductStoreConfigMenuAction extends ScreenAction {

        public static final String module = ProductStoreConfigMenuAction.class.getName();
        //final String nameStr = "Show Inventory";
        final String iconPathStr = "";//"clients.png";
        final String iconPathSmallStr = "";//"clients.png";
        ControllerOptions options = null;

        public ProductStoreConfigMenuAction(String name, ControllerOptions options) {
            super(ActionType.INVENTORY_ITEM_LIST_ACTION, name, ControllerOptions.getSession(), ControllerOptions.getDesktopPane());
            this.options = options;
            this.setName(name);
            this.setIconPath(iconPathStr);
            this.setIconPathSmall(iconPathSmallStr);
            loadIcons();
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            ControllerOptions tmpOptions = new ControllerOptions(this.options);
            createAndShowPanel(tmpOptions);
        }

        @Override
        public Action getAction() {
            return this;
        }
        
        static public void createAndShowPanel(ControllerOptions tmpOptions){
            tmpOptions.put("X", 200);
            tmpOptions.put("Y", 0);

            GenericSaveInterface entityPanelInterface = EntityPanelFactory.createEntityPanel(tmpOptions);
            final GenericSavePanel viewer = new GenericSavePanel(tmpOptions);
            viewer.setGenericSaveInterface(entityPanelInterface);
            viewer.add(entityPanelInterface.getPanel(), BorderLayout.CENTER);
            tmpOptions.setSimpleScreenInterface(viewer);
            final SimpleFrameMainScreen frame = SimpleFrameMainScreen.runController(tmpOptions);
            frame.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (frame.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                                if (viewer.getSelectedValue() != null) {                                    
                                }
                                //genericValueTablePanel.loadList();
                            }
                        }
                    });
            
        }
    }

}
