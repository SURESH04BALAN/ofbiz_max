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
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.Order;

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
public class OrderListCellRenderer extends JLabel implements
        ListCellRenderer<Order> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;

    @Override
    public Component getListCellRendererComponent(JList<? extends Order> list,
            Order value, int index, boolean isSelected, boolean cellHasFocus) {
        String toString = toString(value);
        setText(toString);
        return this;
    }

    private String toString(Order order) {
        if (order == null) {
            return "";
        }
        StringBuilder orderToStringBuilder = new StringBuilder();

        orderToStringBuilder.append(order.getOrderId());
        if (UtilValidate.isNotEmpty(order.getOrderName())) {
            orderToStringBuilder.append(" [");
            orderToStringBuilder.append(order.getOrderName());
            orderToStringBuilder.append(" ]");
        }
        return orderToStringBuilder.toString();
    }

}
