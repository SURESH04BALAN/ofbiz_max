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
import org.ofbiz.ordermax.entity.UomType;

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
public class UomTypeListCellRenderer extends JLabel implements
		ListCellRenderer<UomType> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1614367901813214864L;
boolean showKey = false;
   public UomTypeListCellRenderer(boolean showKey) {
        super();
        setOpaque(true);
        this.showKey = showKey;
    }
	@Override
	public Component getListCellRendererComponent(JList<? extends UomType> list,
			UomType value, int index, boolean isSelected, boolean cellHasFocus) {
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

	private String toString(UomType obj) {
		if (obj == null) {
			return "";
		}
		StringBuilder orderToStringBuilder = new StringBuilder();

		orderToStringBuilder.append(obj.getdescription());
                if(showKey){
		orderToStringBuilder.append(" [");
		orderToStringBuilder.append(obj.getuomTypeId());
                orderToStringBuilder.append(" ]");                
                }
                return orderToStringBuilder.toString();
	}

}
