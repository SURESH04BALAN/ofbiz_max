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
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PartyType;

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
public class PartyTypeListCellRenderer extends JLabel implements
		ListCellRenderer<PartyType> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1614367901813214864L;
   DisplayNameInterface.DisplayTypes showKey;

    public PartyTypeListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
        super();
        setOpaque(true);
        this.showKey = showKey;
    }
    public PartyTypeListCellRenderer() {
        super();
        setOpaque(true);
        this.showKey = XuiContainer.getSession().getComboBoxDisplayFormat();
    }
    @Override
	public Component getListCellRendererComponent(JList<? extends PartyType> list,
			PartyType value, int index, boolean isSelected, boolean cellHasFocus) {
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

	private String toString(PartyType obj) {
		if (obj == null) {
			return "";
		}
                else{
                    return obj.getdisplayName(showKey);
                }
                
/**		StringBuilder orderToStringBuilder = new StringBuilder();

		orderToStringBuilder.append(obj.getdescription());
                if(showKey){
		orderToStringBuilder.append(" [");
		orderToStringBuilder.append(obj.getproductId());
                orderToStringBuilder.append(" ]");                
                }
                return orderToStringBuilder.toString();
                * */
	}

}
