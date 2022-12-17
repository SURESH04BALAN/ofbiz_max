/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report;

import org.ofbiz.ordermax.base.components.GenericDateSelectionPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import mvc.model.list.JGenericComboBoxSelectionModel;
import net.sf.jasperreports.engine.JasperPrint;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.Dates.DateSingleton;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoPanelNonSizableContainerDlg;
import org.ofbiz.ordermax.base.components.FindDateSelectionPanel;
import org.ofbiz.ordermax.base.components.FindSelectionEditPanel;
import org.ofbiz.ordermax.base.components.FindSingleDateSelectionPanel;
import org.ofbiz.ordermax.base.components.InvoicePickerEditPanel;
import org.ofbiz.ordermax.base.components.OrderPickerEditPanel;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author BBS Auctions
 */
public abstract class ReportBaseMain implements ReportInterface {

    public static final String module = ReportBaseMain.class.getName();
    private boolean showSelectionPanel = true;
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();

    @Override
    public boolean getShowSelectionPanel() {
        return showSelectionPanel;
    }

    @Override
    public void setShowSelectionPanel(boolean val) {
        showSelectionPanel = val;
    }

    @Override
    abstract public String identifier();

    abstract public JPanel runReport(Map<String, Object> reportArgs);

    @Override
    public JPanel getSelectionPanel() {
        return null;
    }

    //  @Override
    //   public Map<String, Object> getWhereClause() {
    //       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //  }
    protected OrderMaxJRViewer viewer = null;

    @Override
    public OrderMaxJRViewer getJRViewer() {
        return viewer;
    }

    @Override
    abstract public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception;

    protected JPanel getReportPanel(final JasperPrint jasperPrint) {
        JPanel panelReport = new JPanel();
        Rectangle rec = panelReport.getBounds();
        rec.width = 900;
        rec.height = 700;
        panelReport.setBounds(rec);

        if (jasperPrint != null) {
            viewer = new OrderMaxJRViewer(jasperPrint);
            /*Rectangle rec = viewer.getBounds();
             rec.width = 900;
             rec.height = 700;
             viewer.setBounds(rec);*/
            panelReport.setLayout(new BorderLayout());
            viewer.setPreferredSize(new Dimension(panelReport.getSize()));
//            JScrollPane reportScroll = new JScrollPane(viewer);
            panelReport.add(viewer, BorderLayout.CENTER);
            panelReport.invalidate();
//            tabReportPane.setSelectedIndex(1);
//            this.repaint();
        }
        return panelReport;
    }
    TwoPanelNonSizableContainerDlg posSelectionDlg = null;

    public void loadSinglePanelNonSizeableFrameDialogScreen(javax.swing.JDesktopPane desktopPane, JFrame parentJFrame) {
//        this.desktopPane = desktopPane;

        if (desktopPane == null) {
            posSelectionDlg = new TwoPanelNonSizableContainerDlg(parentJFrame, true);
//            OnePanelNonSizableContainerDlg singlePanelSelectionDlg = new OnePanelNonSizableContainerDlg(parentJFrame, true);

//            loadScreen(posSelectionDlg);
            posSelectionDlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            setSizeAndLocation(posSelectionDlg);
            posSelectionDlg.setLocationRelativeTo(null);
            posSelectionDlg.setDividerLocation(0);
//        f.textField = panel.getPartyTextField();
//        f.pack();
            posSelectionDlg.setVisible(true);
            posSelectionDlg.getReturnStatus();
        } else {

            JOptionPane optionPane = new JOptionPane();
            optionPane.setMessage("Hello, World");
            optionPane.setMessageType(
                    JOptionPane.INFORMATION_MESSAGE);
            JInternalFrame modal
                    = optionPane.createInternalFrame(desktopPane, "Modal");

            JPanel glass = new JPanel();
            glass.setOpaque(false);
            glass.add(modal);
            parentJFrame.setGlassPane(glass);
            glass.setVisible(true);
            modal.setVisible(true);

            /*
             final TwoPanelContainerPanel chooser = new TwoPanelContainerPanel();
             JOptionPane pane = new JOptionPane(chooser, JOptionPane.PLAIN_MESSAGE,
             JOptionPane.DEFAULT_OPTION, null, new Object[0], null);
             final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Dialog Frame");

             chooser.addActionListener(new ActionListener() {

             public void actionPerformed(ActionEvent e) {
             if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
             System.out.println("getReturnStatus : "
             + chooser.getReturnStatus());
             }
             dialog.setVisible(false);
             }
             });
             JPanel panelJasper = chooser.getPanelDetail();
             //                            chooser.getPanelSelecton().setVisible(false);
             panelJasper.removeAll();
             panelJasper.setLayout(new GridLayout(1, 1));
             //            panelJasper.add(viewer);
             //            setSizeAndLocation(dialog);
             dialog.setVisible(true);*/
            /*
             final JOptionPane pane = new JOptionPane("Only Way to close",  JOptionPane.QUESTION_MESSAGE,
             JOptionPane.YES_NO_OPTION);
            
             final JDialog dialog = new JDialog(parentJFrame,"click button", true);
             dialog.setContentPane(pane);
             dialog.pack();
             dialog.setVisible(true);*/
            /*            final TwoPanelContainerPanel twoPanelContainerPanel = new TwoPanelContainerPanel();
             final JOptionPane pane = new JOptionPane(twoPanelContainerPanel, JOptionPane.PLAIN_MESSAGE,
             JOptionPane.DEFAULT_OPTION, null, new Object[0], null);
             pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
             final JInternalFrame dialog = pane.createInternalFrame(desktopPane, "Dialog Frame");
             removeSystemMenuListener(dialog);
             dialog.addInternalFrameListener(new InternalFrameAdapter() {
             public void internalFrameClosed(InternalFrameEvent e) {
             Object value = pane.getInputValue();
             if (value == JOptionPane.UNINITIALIZED_VALUE) {
             value = null;
             }
             }
             }
             );
            
             //            loadScreen(twoPanelContainerPanel);
             //          setSizeAndLocation(dialog);
             twoPanelContainerPanel.addActionListener(new ActionListener() {

             public void actionPerformed(ActionEvent e) {
             //                    returnStatus = twoPanelContainerPanel.getReturnStatus();
             System.out.println("twoPanelContainerPanel getReturnStatus : " + twoPanelContainerPanel.getReturnStatus());
             dialog.setVisible(false);
             }
             });

             dialog.setVisible(true);
             */
        }

    }

    private static void removeSystemMenuListener(JInternalFrame modal) {
        BasicInternalFrameUI ui = (BasicInternalFrameUI) modal.getUI();
        JComponent titleBar = (JComponent) ui.getNorthPane();
        for (Component c : titleBar.getComponents()) {
            if (c instanceof JLabel || "InternalFrameTitlePane.menuButton".equals(c.getName())) {
                for (MouseListener ml : c.getMouseListeners()) {
                    ((JComponent) c).removeMouseListener(ml);
                }
            }
        }
    }

    class ModalAdapter extends InternalFrameAdapter {

        Component glass;

        public ModalAdapter(Component glass) {
            this.glass = glass;

// Associate dummy mouse listeners
// Otherwise mouse events pass through
            MouseInputAdapter adapter = new MouseInputAdapter() {
            };
            glass.addMouseListener(adapter);
            glass.addMouseMotionListener(adapter);
        }

        public void internalFrameClosed(InternalFrameEvent e) {
            glass.setVisible(false);
        }
    }

    static public JPanel AddPartyIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name) {
        PartyPickerEditPanel panelSupplierPartyIdPicker = new PartyPickerEditPanel(controllerOptions);
//        addAItem(panelSupplierPartyIdPicker, panelFilter);
        filterList.add(panelSupplierPartyIdPicker);
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelSupplierPartyIdPicker, name);
        return panelSupplierPartyIdPicker;
    }
//        ControllerOptions partyOptions = new ControllerOptions();

    static public JPanel AddProductIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name) {
        ProductPickerEditPanel productPickerEditPanel = new ProductPickerEditPanel(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(productPickerEditPanel, name);
//        addAItem(productPickerEditPanel, panelFilter);
        filterList.add(productPickerEditPanel);
        return productPickerEditPanel;
    }

    static public JPanel AddOrderIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name) {
        OrderPickerEditPanel orderPickerEditPanel = new OrderPickerEditPanel(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(orderPickerEditPanel, name);
        //      addAItem(orderPickerEditPanel, panelFilter);
        filterList.add(orderPickerEditPanel);
        return orderPickerEditPanel;
    }

    static public JPanel AddInvoiceIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name) {
        InvoicePickerEditPanel invoicePickerEditPanel = new InvoicePickerEditPanel(controllerOptions);
        ComponentBorder.doubleRaisedLoweredBevelBorder(invoicePickerEditPanel, name);
        //    addAItem(invoicePickerEditPanel, panelFilter);
        filterList.add(invoicePickerEditPanel);
        return invoicePickerEditPanel;
    }

    static public JPanel AddDateSelection(List<ReportParameterSelectionInterface> filterList, String idName, ControllerOptions controllerOptions, String name) {
        GenericDateSelectionPanel dateFilter = new GenericDateSelectionPanel(idName);
        //  if (controllerOptions.contains("endDateEnabled")) {

        dateFilter.endDatePickerEditPanel.setVisible(!controllerOptions.contains("endDateEnabled"));
        dateFilter.lblEndDate.setVisible(!controllerOptions.contains("endDateEnabled"));
        ComponentBorder.doubleRaisedLoweredBevelBorder(dateFilter, name);
        //      addAItem(dateFilter, panelFilter);
        filterList.add(dateFilter);
        return dateFilter;
    }

    static public FindSingleDateSelectionPanel AddFindSingleDateSelection(List<ReportParameterSelectionInterface> filterList, String key, ControllerOptions controllerOptions, String name) {
        FindSingleDateSelectionPanel dateFilter = new FindSingleDateSelectionPanel(key);
        //  if (controllerOptions.contains("endDateEnabled")) {

//        dateFilter.endDatePickerEditPanel.setVisible(!controllerOptions.contains("endDateEnabled"));
//        dateFilter.lblEndDate.setVisible(!controllerOptions.contains("endDateEnabled"));
        ComponentBorder.doubleRaisedLoweredBevelBorder(dateFilter, name);
        //      addAItem(dateFilter, panelFilter);
        filterList.add(dateFilter);
        return dateFilter;
    }

    static public JPanel AddFindDateSelection(List<ReportParameterSelectionInterface> filterList, String idName, ControllerOptions controllerOptions, String name) {
        FindDateSelectionPanel dateFilter = new FindDateSelectionPanel(idName, DateSingleton.PERIOD.NODATE);
        //  if (controllerOptions.contains("endDateEnabled")) {

//        dateFilter.endDatePickerEditPanel.setVisible(!controllerOptions.contains("endDateEnabled"));
//        dateFilter.lblEndDate.setVisible(!controllerOptions.contains("endDateEnabled"));
        ComponentBorder.doubleRaisedLoweredBevelBorder(dateFilter, name);
        //      addAItem(dateFilter, panelFilter);
        filterList.add(dateFilter);
        return dateFilter;
    }

    static public FindDateSelectionPanel AddFindDateSelection(List<ReportParameterSelectionInterface> filterList, String idName, ControllerOptions controllerOptions, String name, DateSingleton.PERIOD defOption) {
        FindDateSelectionPanel dateFilter = new FindDateSelectionPanel(idName, defOption);
        //  if (controllerOptions.contains("endDateEnabled")) {

//        dateFilter.endDatePickerEditPanel.setVisible(!controllerOptions.contains("endDateEnabled"));
//        dateFilter.lblEndDate.setVisible(!controllerOptions.contains("endDateEnabled"));
        ComponentBorder.doubleRaisedLoweredBevelBorder(dateFilter, name);
        //      addAItem(dateFilter, panelFilter);
        filterList.add(dateFilter);
        return dateFilter;
    }

    static public FindSelectionEditPanel AddTextIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name, String key) {
        FindSelectionEditPanel findSelectionEditPanel = new FindSelectionEditPanel(controllerOptions, key, ConditionSelectSingleton.EQUALS);
        ComponentBorder.doubleRaisedLoweredBevelBorder(findSelectionEditPanel, name);
        //    addAItem(invoicePickerEditPanel, panelFilter);
        filterList.add(findSelectionEditPanel);
        return findSelectionEditPanel;
    }
    
    static public FindSelectionEditPanel AddTextIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name, String key, String defOperator) {
        FindSelectionEditPanel findSelectionEditPanel = new FindSelectionEditPanel(controllerOptions, key, defOperator);
        ComponentBorder.doubleRaisedLoweredBevelBorder(findSelectionEditPanel, name);
        //    addAItem(invoicePickerEditPanel, panelFilter);
        filterList.add(findSelectionEditPanel);
        return findSelectionEditPanel;
    }

    static public FindSelectionEditPanel AddTextIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name, String key, JPanel panel, String defOperator) {
        FindSelectionEditPanel findSelectionEditPanel = AddTextIdSelection(filterList, controllerOptions,  name, key, defOperator);
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.CENTER, findSelectionEditPanel);
        return findSelectionEditPanel;
    }

    static public FindSelectionEditPanel AddTextIdSelection(List<ReportParameterSelectionInterface> filterList, ControllerOptions controllerOptions, String name, String key, JPanel panel) {
        FindSelectionEditPanel findSelectionEditPanel = AddTextIdSelection(filterList, controllerOptions,  name, key, ConditionSelectSingleton.EQUALS);
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.CENTER, findSelectionEditPanel);
        return findSelectionEditPanel;
    }

    static public <E> JPanel createReportSelectionComboPanel(List<ReportParameterSelectionInterface> filterList, String keyId, List<E> values, String name) {

        JPanel contPanel = new JPanel();
        JGenericComboBoxSelectionModel model = new JGenericComboBoxSelectionModel<E>(contPanel, values);
        model.keyId = keyId;
        filterList.add(model);
        if (name != null) {
            ComponentBorder.doubleRaisedLoweredBevelBorder(contPanel, name);
        }
//        reportObject.addAItem(contPanel, panel);
        return contPanel;
    }

    static public <E> JPanel createReportSelectionComboPanel(List<ReportParameterSelectionInterface> filterList, String keyId, List<E> values, String name, E defValue) {

        JPanel contPanel = new JPanel();
        JGenericComboBoxSelectionModel model = new JGenericComboBoxSelectionModel<E>(contPanel, values);
        model.keyId = keyId;
        model.setSelectedItem(defValue);
        filterList.add(model);
        ComponentBorder.doubleRaisedLoweredBevelBorder(contPanel, name);
//        reportObject.addAItem(contPanel, panel);
        return contPanel;
    }

    static public List<EntityCondition> getWhereClauseCond(List<ReportParameterSelectionInterface> filterList) {
        List<EntityCondition> whereClauseMap = new ArrayList<EntityCondition>();
        for (ReportParameterSelectionInterface genPanel : filterList) {
            EntityCondition cond = genPanel.getEntityCondition();
            if (cond != null) {
                Debug.logInfo("cond : " + cond.toString(), module);
                whereClauseMap.add(cond);
            }
        }
        return whereClauseMap;
    }

    public List<EntityCondition> getWhereClauseCond() {
        return ReportBaseMain.getWhereClauseCond(filterList);
    }

    static public void addToGridLayout(JPanel parent, JPanel child, GridBagConstraints gbc, int idx, int idy) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = idx;
        gbc.gridy = idy;
        parent.add(child, gbc);
    }
}
