/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.awt.Color;
import mvc.data.Address;
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;
import org.ofbiz.ordermax.party.GeoSingleton;

/**
 * A {@link ListCellRenderer} that uses an {@link ObjectToString} to get a
 * string representation for the model objects it renders.
 *
 * @author RenÃ© Link <a
 * href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 * intersystems.com]</a>
 *
 * @param <T>
 */
public class ContactMechPurposeTypeListCellRenderer extends JLabel implements
        ListCellRenderer<ContactMechPurposeType> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;

    @Override
    public Component getListCellRendererComponent(JList<? extends ContactMechPurposeType> list,
            ContactMechPurposeType value, int index, boolean isSelected, boolean cellHasFocus) {

        setFont(list.getFont());

        String toString = toString(value);
        setText(toString);
        if (isSelected) {
//            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBackground(list.getBackground());
            setForeground(Color.BLUE);
//        if (-1 < index) {
//          list.setToolTipText(tooltips[index]);
//        }
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }

    private String toString(ContactMechPurposeType postalAddress) {
        if (postalAddress == null) {
            return "";
        }
        StringBuilder orderToStringBuilder = new StringBuilder();

        orderToStringBuilder.append(postalAddress.getdescription());
        orderToStringBuilder.append(" [");
        orderToStringBuilder.append(postalAddress.getcontactMechPurposeTypeId());

        orderToStringBuilder.append(" ]");

        return orderToStringBuilder.toString();
    }

}
