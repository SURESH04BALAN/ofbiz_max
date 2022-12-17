/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.KeyStroke;
import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Order;

/**
 *
 * @author siranjeev
 */
public class LoadOrderAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<Order> orderListModel;
    private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession delegator = null;
    
    public LoadOrderAction(ListAdapterListModel<Order> orderListModel, XuiSession delegator) {
        this.orderListModel = orderListModel;
        this.delegator = delegator;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Load";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('L');
        }
        return super.getValue(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LoadPurchaseOrderWorker loadOrderWorker = new LoadPurchaseOrderWorker(orderListModel, delegator, null);
        loadOrderWorker.setLoadSpeedModel(loadPersonsSpeedModel);
        for (SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener : swingWorkerPropertyChangeListeners) {
            swingWorkerPropertyChangeListener
                    .attachPropertyChangeListener(loadOrderWorker);
        }
        loadOrderWorker.execute();
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
