/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiSession;

/**
 *
 * @author BBS Auctions
 */
public abstract class LoadBaseSwingWorker<E> extends SwingWorker<List<E>, E> {

    protected volatile int maxProgress;
    protected int progressedItems;
    protected ListAdapterListModel<E> listModel;
    protected BoundedRangeModel loadSpeedModel = new DefaultBoundedRangeModel();
    protected XuiSession session = null;
    public static final String module = LoadBaseSwingWorker.class.getName();
    protected Map<String, Object> findOptionMap = new HashMap<String, Object>();
    protected List<EntityCondition> entityConditionList = null;
    
    
    public LoadBaseSwingWorker(ListAdapterListModel<E> listModel, Map<String, Object> findOptionMap) {
        this.listModel = listModel;
        this.findOptionMap = findOptionMap;
    }
    
   public LoadBaseSwingWorker(ListAdapterListModel<E> listModel, List<EntityCondition> entityConditionList) {
        this.listModel = listModel;
        this.entityConditionList = entityConditionList;
    }

   public LoadBaseSwingWorker() {
        this.listModel = null;
        this.entityConditionList = null;
    }
   
    @Override
    protected void process(List<E> chunks) {
        listModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    protected int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    protected void sleepAWhile() {
        try {
            Thread.yield();
            long timeToSleep = getTimeToSleep();
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
        }
    }

    protected long getTimeToSleep() {
        return 0; //loadSpeedModel.getValue();
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadSpeedModel = loadPersonsSpeedModel;

    }
    
    protected ClassLoader getClassLoader() {
        //Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            //Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    //Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable t) {
                    Debug.logError("class loader 5", module);
                }

                if (cl == null) {
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }
}
