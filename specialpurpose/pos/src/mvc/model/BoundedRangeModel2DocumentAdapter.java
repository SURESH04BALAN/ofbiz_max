/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import java.text.Format;
import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author siranjeev
 */
public class BoundedRangeModel2DocumentAdapter {

	private ChangeListenerAdapter changeListenerAdapter = new ChangeListenerAdapter();

	private Format format;
	private Document document;
	private BoundedRangeModel boundedRangeModel;

	public BoundedRangeModel2DocumentAdapter(Document document, Format format) {
		this.document = document;
		this.format = format;
	}

	public void bind(BoundedRangeModel boundedRangeModel) {
		if (this.boundedRangeModel != null) {
			this.boundedRangeModel.removeChangeListener(changeListenerAdapter);
		}
		this.boundedRangeModel = boundedRangeModel;
		if (boundedRangeModel != null) {
			boundedRangeModel.addChangeListener(changeListenerAdapter);
			ChangeEvent modelChangedEvent = new ChangeEvent(boundedRangeModel);
			changeListenerAdapter.stateChanged(modelChangedEvent);
		}
	}

	public void unbind() {
		bind(null);
	}

	private class ChangeListenerAdapter implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			BoundedRangeModel boundedRangeModel = (BoundedRangeModel) e
					.getSource();
			Object[] formatArgs = new Object[] { boundedRangeModel.getValue() };
			String text = format.format(formatArgs);
			try {
				document.remove(0, document.getLength());
				document.insertString(0, text, null);
			} catch (BadLocationException e1) {
			}
		}

	}
}
