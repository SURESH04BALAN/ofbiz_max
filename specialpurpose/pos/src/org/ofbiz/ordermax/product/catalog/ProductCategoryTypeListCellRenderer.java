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
import org.ofbiz.ordermax.entity.ProductCategoryType;

/**
 *
 * @author siranjeev
 */

public class ProductCategoryTypeListCellRenderer extends JLabel implements
		ListCellRenderer<ProductCategoryType> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1614367901813214864L;

	@Override
	public Component getListCellRendererComponent(JList<? extends ProductCategoryType> list,
			ProductCategoryType value, int index, boolean isSelected, boolean cellHasFocus) {
		String toString = toString(value);
		setText(toString);
		return this;
	}

	private String toString(ProductCategoryType person) {
		if (person == null) {
			return "";
		}
		StringBuilder personToStringBuilder = new StringBuilder();

		personToStringBuilder.append(person.getdescription());
		personToStringBuilder.append(" [id:");
		personToStringBuilder.append(person.getproductCategoryTypeId());		
		personToStringBuilder.append("]");                
		return personToStringBuilder.toString();
	}

}
