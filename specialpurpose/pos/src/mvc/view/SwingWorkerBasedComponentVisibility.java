/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view;

import mvc.controller.SwingWorkerPropertyChangeListener;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;


/**
 * Controls the visibility of a set of components depending on a
 * {@link SwingWorker}'s {@link StateValue} by listening to the
 * {@link SwingWorker}'s {@link PropertyChangeEvent}.
 * 
 * @author RenÃ© Link <a
 *         href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 *         intersystems.com]</a>
 * 
 */
public class SwingWorkerBasedComponentVisibility implements
		SwingWorkerPropertyChangeListener {

	private final SwingWorkerPropertyChangeAdapter swingWorkerPropertyChangeAdapter = new SwingWorkerPropertyChangeAdapter();

	private Component[] visibleComponentsOnProgress;

	public SwingWorkerBasedComponentVisibility(
			Component... visibleComponentsOnProgress) {
		this.visibleComponentsOnProgress = visibleComponentsOnProgress;
		setComponentsVisible(false);
	}

	private void setComponentsVisible(boolean visibility) {
		for (Component component : visibleComponentsOnProgress) {
			component.setVisible(visibility);
		}
	}

        @Override
	public void attachPropertyChangeListener(SwingWorker<?, ?> swingWorker) {
		swingWorker.addPropertyChangeListener(swingWorkerPropertyChangeAdapter);
	}

        @Override
	public void detachPropertyChangeListener(SwingWorker<?, ?> swingWorker) {
		swingWorker.removePropertyChangeListener(swingWorkerPropertyChangeAdapter);

	}

	private class SwingWorkerPropertyChangeAdapter implements
			PropertyChangeListener {

                @Override
		public void propertyChange(PropertyChangeEvent evt) {
			String propertyName = evt.getPropertyName();
			if ("state".equals(propertyName)) {
				StateValue stateValue = (StateValue) evt.getNewValue();
				switch (stateValue) {
				case STARTED:
				case PENDING:
					setComponentsVisible(true);
					break;
				case DONE:
					setComponentsVisible(false);
					break;
				}
			}
		}

	}

}
