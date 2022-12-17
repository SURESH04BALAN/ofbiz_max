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
import org.ofbiz.ordermax.entity.TelecomNumber;
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
public class TelecomNumberListCellRenderer extends JLabel implements
        ListCellRenderer<TelecomNumber> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;

    @Override
    public Component getListCellRendererComponent(JList<? extends TelecomNumber> list,
            TelecomNumber value, int index, boolean isSelected, boolean cellHasFocus) {

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

    static public String toString(TelecomNumber postalAddress) {

        if (postalAddress == null) {
            return "";
        }

        StringBuilder orderToStringBuilder = new StringBuilder();

        if (postalAddress.getaskForName() != null) {
            orderToStringBuilder.append(postalAddress.getaskForName());
            orderToStringBuilder.append(" [");
        }
        if (postalAddress.getcountryCode() != null) {

            orderToStringBuilder.append(postalAddress.getcountryCode());
        }

        if (postalAddress.getareaCode() != null) {
            orderToStringBuilder.append(" ");
            orderToStringBuilder.append(postalAddress.getareaCode());
        }

        if (postalAddress.getcontactNumber() != null) {
            orderToStringBuilder.append(" ");
            orderToStringBuilder.append(postalAddress.getcontactNumber());
        }
        if (postalAddress.getaskForName() != null) {
            orderToStringBuilder.append("]");
        }

        return orderToStringBuilder.toString();
    }

}
