package org.ofbiz.ordermax.product.catalog;

import mvc.controller.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelContainerPanel;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.base.components.screen.SimpleFrameMainScreen;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.ItemListInterface;
import org.ofbiz.ordermax.report.reports.EntityJasperReport;
import org.ofbiz.ordermax.report.reports.InventoryItemReceiptReportJasper;
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
public class ProductCategoryMaintainAction extends ScreenAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
//    private ListAdapterListModel<ShipmentReceiptComposite> invoiceCompListModel;
    ItemListInterface<ShipmentReceiptComposite> itemListInterface = null;
    public static final String module = ShipmentReceiptAction.class.getName();
    public static final String nameStr = "Edit Category";
    final String iconPathStr = "";
    final String iconPathSmallStr = "";

    public ProductCategoryMaintainAction(String name, ControllerOptions options) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, name, ControllerOptions.getSession(), ControllerOptions.getDesktopPane());

    }

    public ProductCategoryMaintainAction(ItemListInterface<ShipmentReceiptComposite> itemListInterface, XuiSession session) {
        super(ActionType.PRINT_PURCHASE_INVOICE_ACTION, session);
        this.itemListInterface = itemListInterface;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return nameStr;
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public Action getAction() {
        return this;
    }

    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 10;
        int x = 10;
        frame.setSize(desktopPane.getBounds().width - 2 * x, desktopPane.getBounds().height - 2 * y);
        frame.setLocation(x, y);
    }

    protected void setSizeAndLocation(TwoPanelNonSizableContainerDlg dlg) {
        int y = 10;
        int x = 10;
        dlg.setSize(1000 - 2 * x, 750 - 2 * y);
        dlg.setLocation(x, y);
    }

    public void actionPerformed(ActionEvent e) {
        ProductCategoryMaintainPanel viewer = new ProductCategoryMaintainPanel(session);
        if (desktopPane != null) {
            
        SimpleFrameMainScreen partyMain = new SimpleFrameMainScreen(viewer, ProductCategoryMaintainPanel.module, session);
        partyMain.loadSinglePanelInternalFrame(ProductCategoryMaintainPanel.module, desktopPane);
            
/*
            JOptionPane pane = new JOptionPane(viewer, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[0], null);

            final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Product Category");

            viewer.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dialog.setVisible(false);
                }
            });

//            JPanel panelJasper = twoPanelContainerPanel.getPanelDetail();
//                            chooser.getPanelSelecton().setVisible(false);
//            panelJasper.removeAll();
//            panelJasper.setLayout(new GridLayout(1, 1));
//            panelJasper.add(viewer);
            setSizeAndLocation(dialog);
            dialog.setVisible(true);
*/
        } else {
            TwoPanelNonSizableContainerDlg posSelectionDlg = new TwoPanelNonSizableContainerDlg(null, true);
            posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            posSelectionDlg.setSize(1000, 750);
            posSelectionDlg.setLocationRelativeTo(null);
            posSelectionDlg.setDividerLocation(0);
            JPanel panelJasper = posSelectionDlg.getPanelDetail();
            posSelectionDlg.getPanelSelecton().setVisible(false);
            panelJasper.removeAll();
            panelJasper.setLayout(new GridLayout(1, 1));
            panelJasper.add(viewer);
            setSizeAndLocation(posSelectionDlg);
            posSelectionDlg.setVisible(true);
            posSelectionDlg.toFront();
            posSelectionDlg.requestFocus();
        }
    }
}
