package mvc.controller;

import java.beans.PropertyChangeListener;
import javax.swing.BoundedRangeModel;
import javax.swing.SwingWorker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author siranjeev
 */
public interface SwingWorkerPropertyChangeListener {

	/**
	 * Attaches this {@link SwingWorkerProgressModel} as an
	 * {@link PropertyChangeListener} to the given {@link SwingWorker} so that
	 * the {@link SwingWorker}'s progress events will update this
	 * {@link BoundedRangeModel}.
	 * 
	 * @param sw
	 *            the {@link SwingWorker} to attach this {@link SwingWorker}'s
	 *            {@link PropertyChangeListener} adater from.
	 */
	public abstract void attachPropertyChangeListener(SwingWorker<?, ?> sw);

	/**
	 * Detaches this {@link SwingWorkerProgressModel}'s
	 * {@link PropertyChangeListener} adapter from the given {@link SwingWorker}
	 * so that no progress events will update this {@link BoundedRangeModel}
	 * anymore.
	 * 
	 * @param sw
	 *            the {@link SwingWorker} to dettach this {@link SwingWorker}'s
	 *            {@link PropertyChangeListener} adater from.
	 */
	public abstract void detachPropertyChangeListener(SwingWorker<?, ?> sw);

}