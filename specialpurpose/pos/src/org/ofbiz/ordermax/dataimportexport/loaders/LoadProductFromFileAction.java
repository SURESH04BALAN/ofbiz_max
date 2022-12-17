package org.ofbiz.ordermax.dataimportexport.loaders;

import org.ofbiz.ordermax.dataimportexport.loaders.LoadProductFromFileWorker;
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
import mvc.controller.SwingWorkerPropertyChangeListener;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.ProductComposite;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siranjeev
 */
public class LoadProductFromFileAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2636985714796751517L;
    private ListAdapterListModel<ProductComposite> personListModel;
    private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private XuiSession session;
    private JTextField textField=null;    
    
    public LoadProductFromFileAction(JTextField field,ListAdapterListModel<ProductComposite> personListModel, XuiSession session) {
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
            return "Product Load";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('S');
        }
        return super.getValue(key);
    }

    public void actionPerformed(ActionEvent e) {
//        LoadProductFromFileWorker loadPersonsWorker = new LoadProductFromFileWorker(personListModel,"C:\\backup\\Real_Data\\seven_product_final.csv", session);
//    LoadProductFromFileWorker loadPersonsWorker = new LoadProductFromFileWorker(personListModel,"C:\\backup\\Real_Data\\generated_product_load.csv", session);        
        LoadProductFromFileWorker loadPersonsWorker = new LoadProductFromFileWorker(personListModel,textField.getText(), session);                
        
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
