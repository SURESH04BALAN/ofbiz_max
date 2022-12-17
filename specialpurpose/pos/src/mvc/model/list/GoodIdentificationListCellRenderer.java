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
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;

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
public class GoodIdentificationListCellRenderer extends JLabel implements
        ListCellRenderer<GoodIdentification> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;
   DisplayNameInterface.DisplayTypes showKey;

    public GoodIdentificationListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
        super();
        setOpaque(true);
        this.showKey = showKey;
    }
     public GoodIdentificationListCellRenderer() {
        super();
        setOpaque(true);
        this.showKey = XuiContainer.getSession().getComboBoxDisplayFormat();
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends GoodIdentification> list,
            GoodIdentification value, int index, boolean isSelected, boolean cellHasFocus) {
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

    private String toString(GoodIdentification obj) {
        
     if (obj == null) {
            return "";
        } else {
            return obj.getdisplayName(showKey);
        }
/*        
        if (obj == null) {
            return "";
        }
        StringBuilder orderToStringBuilder = new StringBuilder();
        try {
            GoodIdentificationType type = GoodIdentificationTypeSingleton.getGoodIdentificationType(obj.getgoodIdentificationTypeId());
            orderToStringBuilder.append(type.getdescription());            
        } catch (Exception ex) {
            Logger.getLogger(GoodIdentificationListCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
       
            orderToStringBuilder.append(" [");
            orderToStringBuilder.append(obj.getidValue());
            orderToStringBuilder.append(" ]");
       
        return orderToStringBuilder.toString();
        */
    }

}
