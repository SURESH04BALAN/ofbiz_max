/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class DeletePostalAddressAction extends ScreenAction {

    public static final String module = DeletePostalAddressAction.class.getName();
    public static final String nameStr = "Delete Postal Address";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = ""; //salesorder_small.png";

    ListAdapterListModel<PartyContactMechComposite> listModel = new ListAdapterListModel<PartyContactMechComposite>();

    public DeletePostalAddressAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.DELETE_POSTALADDRESS_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public DeletePostalAddressAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.DELETE_POSTALADDRESS_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }


    public DeletePostalAddressAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane,ListAdapterListModel<PartyContactMechComposite> orderListModel) {
        super(ActionType.DELETE_POSTALADDRESS_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.listModel = orderListModel;
        loadIcons();
    }

    public DeletePostalAddressAction(String name, XuiSession session) {
        super(ActionType.DELETE_POSTALADDRESS_ACTION, session);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
//    private PostalAddress order = null;

    @Override
    public void actionPerformed(ActionEvent e) {

        ActionEvent event = new ActionEvent(this, 1, BEFORE_SAVE);
        for (ActionListener listener : listeners) {
            listener.actionPerformed(event); // broadcast to all
        }

        for (PartyContactMechComposite pcmc : listModel.getList()) {

            if ("POSTAL_ADDRESS".equals(pcmc.getContact().getContactMech().getcontactMechTypeId())) {
                String contechMechId = null;               
                Map<String, Object> resultMap = LoadAccountWorker.createOrUpdatePostalAddress(pcmc.getContact(), 
                        pcmc.getPartyContactMech().getpartyId(), UtilValidate.isNotEmpty(pcmc.getPartyContactMech().getcontactMechId()), session);
                
                if (resultMap.get("responseMessage").equals("success")) {
                    contechMechId = (String) resultMap.get("contactMechId");
                }
//                String contechMechId = LoadAccountWorker.savePostalAddressContachhMeach(pcmc.getContact().getPostalAddress(), session);
                //PartyContactMechComposite
                pcmc.setContachMechId(contechMechId);
                
                //link to party and purpose
                LoadAccountWorker.createContachMechPurposes(contechMechId, pcmc, session);

            } else if ("TELECOM_NUMBER".equals(pcmc.getContact().getContactMech().getcontactMechTypeId())) {

            } else if ("EMAIL_ADDRESS".equals(pcmc.getContact().getContactMech().getcontactMechTypeId())) {
            }
        }
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class ListPriceLookupActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new DeletePostalAddressAction(name, session, desktopPane);
        }

 

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new DeletePostalAddressAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.DELETE_POSTALADDRESS_ACTION, new ListPriceLookupActionFactory());
    }
}
