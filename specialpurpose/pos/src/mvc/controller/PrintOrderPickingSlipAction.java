package mvc.controller;

import java.awt.GridLayout;
import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ThreePanelContainerFrame;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.report.reports.EntityJasperReport;
import org.ofbiz.ordermax.report.reports.PickingSlipReportJasper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class PrintOrderPickingSlipAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> personListModel;
    private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session;

    public PrintOrderPickingSlipAction(ListAdapterListModel<Order> personListModel, XuiSession session) {
        this.personListModel = personListModel;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Print Invoice";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    public void actionPerformed(ActionEvent e) {
        
        Debug.logInfo(" Pick Slip Order Id: ", "");
        
        /*for (final Order order : personListModel.getList()) {

            try {
                Debug.logInfo("Pick Slip Order Id: " + order.getOrderId(), "");
                if (order.getOrderId() != null && order.getOrderId().isEmpty() == false) {

                    ThreePanelContainerFrame f = new ThreePanelContainerFrame("Output");
//                loadScreen(f);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(1000, 700);
                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("ReportTitle", "Address Report");
                    parameters.put("DataFile", "CustomBeanFactory.java - Bean Collection");
                    parameters.put(EntityJasperReport.DelegatorName, session.getDelegator());
                    parameters.put(EntityJasperReport.Session, session);
                    Set<Order> orderIds = new HashSet<Order>();
                    orderIds.add(order);
                    parameters.put("orderHeader", orderIds);
//                parameters.put(EntityJasperReport.WhereClauseMap, whereClauseMap);

                    EntityJasperReport jasperReport = new PickingSlipReportJasper();
                    final JasperPrint jasperPrint = jasperReport.runReport(parameters);
                    JPanel panelJasper = f.getPanelDetail();
                    f.getPanelSelecton().setVisible(false);
                    if (jasperPrint != null) {
                        panelJasper.removeAll();
                        JRViewer viewer = new JRViewer(jasperPrint);
                        panelJasper.setLayout(new GridLayout(1, 1));
                        panelJasper.add(viewer);
                    }

                    f.setLocationRelativeTo(null);
                    f.setVisible(true);
                    f.setAlwaysOnTop(true);
                    f.toFront();
                    f.setAlwaysOnTop(false);

                }
            } //          });
            catch (Exception ex) {
                Logger.getLogger(PrintOrderPickingSlipAction.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }
        }
        */
    }

    public void addSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
        swingWorkerPropertyChangeListeners.add(swingWorkerPropertyChangeListener);
    }

    public void removeSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
        swingWorkerPropertyChangeListeners.remove(swingWorkerPropertyChangeListener);
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;
    }
}
