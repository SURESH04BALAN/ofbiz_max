/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport;

import org.ofbiz.ordermax.screens.action.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import mvc.view.MainFrame;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.party.PartyMainScreen;

/**
 *
 * @author siranjeev
 */
public class ImportExportDataAction extends ScreenAction {

    final String nameStr = "Import Data From File";
    final String iconPathStr = "clients.png";
    final String iconPathSmallStr = "clients.png";

    public ImportExportDataAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.IMPORT_DATA_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ImportExportDataAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.IMPORT_DATA_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }



    public void actionPerformed(ActionEvent e) {
        ImportExportMainFrame mainFrame = new ImportExportMainFrame(session);
        mainFrame.setMaximizable(true);
        mainFrame.setIconifiable(true);
        mainFrame.setClosable(true);
        mainFrame.setResizable(true);
        desktopPane.add(mainFrame);
        mainFrame.setVisible(true);
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class ImportDataActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new ImportDataAction(name, session, desktopPane);
        }


        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new ImportDataAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.IMPORT_DATA_ACTION, new ImportDataActionFactory());
    }

}
