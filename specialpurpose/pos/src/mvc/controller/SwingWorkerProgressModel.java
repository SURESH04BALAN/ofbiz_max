/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;

/**
 *
 * @author siranjeev
 */
public class SwingWorkerProgressModel extends DefaultBoundedRangeModel
		implements SwingWorkerPropertyChangeListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 2652024572507645835L;

	private PropertyChangeListener swingWorkerAdapter = new SwingWorkerPropertyChangeListenerAdapter();

	/**
	 * @inherited
	 */
	@Override
	public void attachPropertyChangeListener(SwingWorker<?, ?> swingWorker) {
		swingWorker.addPropertyChangeListener(swingWorkerAdapter);
	}

	/**
	 * @inherited
	 */
	@Override
	public void detachPropertyChangeListener(SwingWorker<?, ?> swingWorker) {
		swingWorker.removePropertyChangeListener(swingWorkerAdapter);
	}

	private class SwingWorkerPropertyChangeListenerAdapter implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			SwingWorker<?, ?> swingWorker = (SwingWorker<?, ?>) evt.getSource();
			setValueIsAdjusting(true);
			setMinimum(0);
			setMaximum(100);
			setValue(swingWorker.getProgress());
			setValueIsAdjusting(false);
		}

	}

}
