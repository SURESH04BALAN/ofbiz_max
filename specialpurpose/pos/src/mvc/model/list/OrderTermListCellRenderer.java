/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderTerm;
import org.ofbiz.ordermax.entity.TermType;
import org.ofbiz.ordermax.orderbase.TermTypeSingleton;

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
public class OrderTermListCellRenderer extends JLabel implements
        ListCellRenderer<OrderTerm> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;
    DisplayNameInterface.DisplayTypes showKey;

    public OrderTermListCellRenderer() {
        super();
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends OrderTerm> list,
            OrderTerm value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
//        if (-1 < index) {
//          list.setToolTipText(tooltips[index]);
//        }
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setFont(list.getFont());
        String toString = toString(value);
        setText(toString);

        return this;
    }

    private String toString(OrderTerm orderTerm) {
        if (orderTerm == null) {
            return "";
        } else {
            StringBuilder orderToStringBuilder = new StringBuilder();
            try {
                TermType termType = TermTypeSingleton.getTermType(orderTerm.gettermTypeId());
                orderToStringBuilder.append(UtilFormatOut.padString(termType.getdescription(), 40, true, ' '));
            } catch (Exception ex) {
                Logger.getLogger(OrderTermListCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (UtilValidate.isNotEmpty(orderTerm.gettermDays())) {
                orderToStringBuilder.append(UtilFormatOut.padString(orderTerm.gettermDays().toString(), 20, true, ' '));
            } else {
                orderToStringBuilder.append(UtilFormatOut.padString("", 20, true, ' '));
            }

            if (UtilValidate.isNotEmpty(orderTerm.gettermValue())) {
                orderToStringBuilder.append(UtilFormatOut.padString(orderTerm.gettermValue().toString(), 20, true, ' '));
            } else {
                orderToStringBuilder.append(UtilFormatOut.padString("", 20, true, ' '));
            }

            if (UtilValidate.isNotEmpty(orderTerm.getdescription())) {
                orderToStringBuilder.append(UtilFormatOut.padString(orderTerm.getdescription(), 20, true, ' '));
            }
            
            return orderToStringBuilder.toString();
        }
    }

}
