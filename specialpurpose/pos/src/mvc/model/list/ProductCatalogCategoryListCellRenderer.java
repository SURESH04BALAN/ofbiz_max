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
import mvc.data.Address;
import mvc.data.Person;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProdCatalogCategory;

/**
 *
 * @author siranjeev
 */
public class ProductCatalogCategoryListCellRenderer extends JLabel implements
        ListCellRenderer<ProdCatalogCategory> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;
    DisplayNameInterface.DisplayTypes showKey;

    public ProductCatalogCategoryListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
        super();
        setOpaque(true);
        this.showKey = showKey;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ProdCatalogCategory> list,
            ProdCatalogCategory value, int index, boolean isSelected, boolean cellHasFocus) {
        String toString = toString(value);
        setText(toString);
        return this;
    }

    private String toString(ProdCatalogCategory obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.getdisplayName(showKey);
        }
        /*
         StringBuilder personToStringBuilder = new StringBuilder();

         personToStringBuilder.append(person.getproductCategoryId());
         //		personToStringBuilder.append(" [ ");                                
         //		personToStringBuilder.append(person.getprodCatalogCategoryTypeId());                
         //personToStringBuilder.append(" ]");                
         return personToStringBuilder.toString();*/
    }

}
