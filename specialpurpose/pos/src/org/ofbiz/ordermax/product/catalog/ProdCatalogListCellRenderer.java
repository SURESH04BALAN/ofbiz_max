/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import mvc.data.Address;
import mvc.data.Person;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProdCatalog;

/**
 *
 * @author siranjeev
 */
public class ProdCatalogListCellRenderer extends JLabel implements
        ListCellRenderer<ProdCatalog> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;
    DisplayNameInterface.DisplayTypes showKey = DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE;

    public ProdCatalogListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
        super();
        setOpaque(true);
        this.showKey = showKey;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ProdCatalog> list,
            ProdCatalog value, int index, boolean isSelected, boolean cellHasFocus) {
        String toString = toString(value);
        setText(toString);
        return this;
    }

    private String toString(ProdCatalog obj) {
        if (obj == null) {
            return "";
        }
        else{
            return obj.getdisplayName(showKey);
        }
        /*
        StringBuilder personToStringBuilder = new StringBuilder();

        personToStringBuilder.append(person.getcatalogName());
//		personToStringBuilder.append("[ ");
//		personToStringBuilder.append(person.getprodCatalogId());
//		personToStringBuilder.append(" ]");                
        return personToStringBuilder.toString();
        */
    }

}
