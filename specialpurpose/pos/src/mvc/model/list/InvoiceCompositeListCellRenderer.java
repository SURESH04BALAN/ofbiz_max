/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.list;

import mvc.data.Address;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.ofbiz.ordermax.composite.InvoiceComposite;

/**
 * A {@link ListCellRenderer} that uses an {@link ObjectToString} to get a
 * string representation for the model objects it renders.
 *
 * @author RenÃ© Link <a
 *         href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 *         intersystems.com]</a>
 *
 * @param <T>
 */
public class InvoiceCompositeListCellRenderer extends JLabel implements
		ListCellRenderer<InvoiceComposite> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1614367901813214864L;

	@Override
	public Component getListCellRendererComponent(JList<? extends InvoiceComposite> list,
			InvoiceComposite value, int index, boolean isSelected, boolean cellHasFocus) {
		String toString = toString(value);
		setText(toString);
		return this;
	}

	private String toString(InvoiceComposite invoiceComposite) {
		if (invoiceComposite == null) {
			return "";
		}
		StringBuilder personToStringBuilder = new StringBuilder();

		personToStringBuilder.append(invoiceComposite.getInvoice().getinvoiceId());
		personToStringBuilder.append(" [");
		personToStringBuilder.append(invoiceComposite.getPartyFrom().getDisplayName());
		personToStringBuilder.append(" ]");                
/*
                Address address = person.getAddress();
		if (address != null) {
			personToStringBuilder.append(", ");
			personToStringBuilder.append(address.getStreet());
			personToStringBuilder.append(", ");
			personToStringBuilder.append(address.getStreetNr());
			personToStringBuilder.append(", ");
			personToStringBuilder.append(address.getZipCode());
			personToStringBuilder.append(", ");
			personToStringBuilder.append(address.getCity());
		}
*/
        return personToStringBuilder.toString();
	}

}
