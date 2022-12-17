/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.DisplayNameInterface;

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
public class OrderHeaderListCellRenderer extends JLabel implements
        ListCellRenderer<OrderHeader> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;
    DisplayNameInterface.DisplayTypes showKey;

    public OrderHeaderListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
        super();
        setOpaque(true);
        this.showKey = showKey;
    }

    public OrderHeaderListCellRenderer() {
        super();
        setOpaque(true);
        this.showKey = XuiContainer.getSession().getComboBoxDisplayFormat();
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends OrderHeader> list,
            OrderHeader value, int index, boolean isSelected, boolean cellHasFocus) {
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

    private String toString(OrderHeader obj) {

        if (obj == null) {
            return "";
        } else {
            return obj.getdisplayName(showKey);
        }
        /*        StringBuilder orderToStringBuilder = new StringBuilder();

         orderToStringBuilder.append(obj.getDisplayName());
         if (showKey) {
         orderToStringBuilder.append(" [");
         orderToStringBuilder.append(obj.getParty().getpartyId());
         orderToStringBuilder.append(" ]");
         }
         return orderToStringBuilder.toString();*/

    }

}
