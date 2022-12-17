package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class CreateNewShipGroupAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderCompListModel;

    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Create New Ship Group";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//salesorder_small.png";

    private OrderActionInterface orderActionInterface;

    public CreateNewShipGroupAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.CREATE_NEW_SHIPGROUP_ACTION,
                CreateNewShipGroupAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }
    
    public CreateNewShipGroupAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane,  ListAdapterListModel<Order> orderCompListModel) {
        super(ActionType.CREATE_NEW_SHIPGROUP_ACTION, name, session, desktopPane);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    public CreateNewShipGroupAction(ListAdapterListModel<Order> orderCompListModel, XuiSession session) {
        super(ActionType.CREATE_NEW_SHIPGROUP_ACTION, session);
        this.orderCompListModel = orderCompListModel;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return name;
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    public void actionPerformed(ActionEvent e) {

        
    }
}
