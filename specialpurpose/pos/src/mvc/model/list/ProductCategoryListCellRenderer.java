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
import org.ofbiz.ordermax.entity.ProductCategory;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryListCellRenderer extends JLabel implements
        ListCellRenderer<ProductCategory> {

    /**
     *
     */
    private static final long serialVersionUID = -1614367901813214864L;
    DisplayNameInterface.DisplayTypes showKey;

    public ProductCategoryListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
        super();
        setOpaque(true);
        this.showKey = showKey;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ProductCategory> list,
            ProductCategory value, int index, boolean isSelected, boolean cellHasFocus) {
        String toString = toString(value);
        setText(toString);
        return this;
    }

    private String toString(ProductCategory person) {
        if (person == null) {
            return "";
        }
        StringBuilder personToStringBuilder = new StringBuilder();

        personToStringBuilder.append(person.getCategoryName());
        /*		personToStringBuilder.append("[ id:");
         personToStringBuilder.append(person.getproductCategoryId());
         personToStringBuilder.append(", pid: ");                                
         personToStringBuilder.append(person.getprimaryParentCategoryId());                
         personToStringBuilder.append(" ]");                
         */
        return personToStringBuilder.toString();
    }

}
