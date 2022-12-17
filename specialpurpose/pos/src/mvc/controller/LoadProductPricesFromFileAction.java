package mvc.controller;

import mvc.model.list.ListAdapterListModel;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.entity.ProductPrice;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class LoadProductPricesFromFileAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<ProductPriceComposite> personListModel;
    private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session;
    private JTextField textField=null;    
    public LoadProductPricesFromFileAction(JTextField field, ListAdapterListModel<ProductPriceComposite> personListModel, XuiSession session) {
        this.personListModel = personListModel;
        this.session = session;
        this.textField = field;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "Product list and cost price Load";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('S');
        }
        return super.getValue(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        LoadSupplierProductWorker loadPersonsWorker = new LoadSupplierProductWorker(personListModel,"C:\\backup\\Real_Data\\Product_Receipt_Transactions_1.csv", session);
        LoadProductPricesFromFileWorker loadPersonsWorker = new LoadProductPricesFromFileWorker(personListModel,textField.getText(), session);        
        
        loadPersonsWorker.setLoadSpeedModel(loadPersonsSpeedModel);
        for (SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener : swingWorkerPropertyChangeListeners) {
            swingWorkerPropertyChangeListener
                    .attachPropertyChangeListener(loadPersonsWorker);
        }
        loadPersonsWorker.execute();
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
