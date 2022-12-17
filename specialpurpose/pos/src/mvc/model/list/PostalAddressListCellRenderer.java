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
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.entity.PostalAddress;
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
public class PostalAddressListCellRenderer extends JLabel implements
        ListCellRenderer<PostalAddress> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;

    @Override
    public Component getListCellRendererComponent(JList<? extends PostalAddress> list,
            PostalAddress value, int index, boolean isSelected, boolean cellHasFocus) {

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

    static public  String toString(PostalAddress postalAddress) {
        
        if (postalAddress == null) {
            return "";
        }
        
        StringBuilder orderToStringBuilder = new StringBuilder();

        orderToStringBuilder.append(postalAddress.getToName());
        orderToStringBuilder.append(" [");
        orderToStringBuilder.append(postalAddress.getAddress1());

        if (postalAddress.getAddress2() != null) {
            orderToStringBuilder.append(",");
            orderToStringBuilder.append(postalAddress.getAddress2());
        }
        try {
            orderToStringBuilder.append(", ");
            if(UtilValidate.isNotEmpty(postalAddress.getCity())){
                orderToStringBuilder.append(postalAddress.getCity() + ",");                
            }

            if(UtilValidate.isNotEmpty(postalAddress.getstateProvinceGeoId())){
                orderToStringBuilder.append(GeoSingleton.getGeo(postalAddress.getstateProvinceGeoId()).getgeoCode());                
            }
            
            if(UtilValidate.isNotEmpty(postalAddress.getPostalCode())){
                orderToStringBuilder.append(" " + postalAddress.getPostalCode());                
            }
             
//            orderToStringBuilder.append(postalAddress.getCity() + ","
//                    + GeoSingleton.getGeo(postalAddress.getstateProvinceGeoId()).getgeoCode()
//                    + " " + postalAddress.getPostalCode());
            orderToStringBuilder.append(",");            
//            orderToStringBuilder.append(GeoSingleton.getGeo(postalAddress.getcountryGeoId()).getgeoName());
            if(UtilValidate.isNotEmpty(postalAddress.getcountryGeoId())){
                orderToStringBuilder.append(GeoSingleton.getGeo(postalAddress.getcountryGeoId()).getgeoName());                
            }            
        } catch (Exception ex) {
            Logger.getLogger(PostalAddressListCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }

        orderToStringBuilder.append(" ]");

        return orderToStringBuilder.toString();
    }

}
