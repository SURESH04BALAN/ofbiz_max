/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.product.catalog.CategoryProductManagePanel;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/**
 *
 * @author siranjeev
 */
public class ResourceDataContentMaintainAction extends ScreenAction {

    final String nameStr = "Resource Data Maintain";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
    ProductContentCompositeInterface parentInetrface = null;
    public ResourceDataContentMaintainAction(ProductContentCompositeInterface parent) {
        super(ActionType.ORDER_RETURN_LIST_ACTION, XuiContainer.getSession(),  XuiContainer.getSession().getDesktopPane());
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.parentInetrface = parent;
        loadIcons();
    }

    public ResourceDataContentMaintainAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ORDER_RETURN_LIST_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        ResourceDataContentMaintainPanel resourceDataContentMaintainPanel = new ResourceDataContentMaintainPanel();                
        resourceDataContentMaintainPanel.setProductContentComposite(parentInetrface.getProductContentComposite());        
        SimpleFrameMainScreen partyMain = new SimpleFrameMainScreen(resourceDataContentMaintainPanel, CategoryProductManagePanel.module, session);
        partyMain.loadSinglePanelNonSizeableFrameDialogScreen(ResourceDataContentMaintainPanel.module, desktopPane, null);
        Debug.logInfo("partyMain.getReturnStatus(): " + partyMain.getReturnStatus(), name);
        
        if(partyMain.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)){
            resourceDataContentMaintainPanel.getDialogFields();
        }
     //   partyMain.loadThreePanelInternalFrame(CategoryProductManagePanel.module, desktopPane, frame);

 
    }

    @Override
    public Action getAction() {
        return this;
    }

   

}
