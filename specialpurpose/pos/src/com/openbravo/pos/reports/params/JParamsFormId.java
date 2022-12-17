//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.pos.reports.params;

import com.openbravo.basic.BasicException;
import java.awt.Component;
//import com.openbravo.pos.forms.AppLocal;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategoryMemberSingleton;

public class JParamsFormId extends ParamReportEditor {
     public String getEditorClassName(){
        return "JParamsFormId";
    }
    /**
     * Creates new form JParamsClosedPos
     */
    org.ofbiz.ordermax.base.components.FindSelectionEditPanel idFindSelectionEditPanel = null;
    org.ofbiz.ordermax.base.components.FindSelectionEditPanel nameFindSelectionEditPanel = null;
    org.ofbiz.ordermax.base.components.FindSelectionEditPanel buyPriceFindSelectionEditPanel = null;
    org.ofbiz.ordermax.base.components.FindSelectionEditPanel sellPriceFindSelectionEditPanel = null;
    JGenericComboBoxSelectionModel<ProductCategory> categoryPickerEditPanel = null;
    final String labelText;

    public JParamsFormId(String labelText) {
        this.labelText = labelText;
        initComponents();

        idFindSelectionEditPanel = new org.ofbiz.ordermax.base.components.FindSelectionEditPanel(new ControllerOptions(), "internalName", ConditionSelectSingleton.EQUALS);
        panelId.setLayout(new BorderLayout());
        panelId.add(BorderLayout.CENTER, idFindSelectionEditPanel);

        nameFindSelectionEditPanel = new org.ofbiz.ordermax.base.components.FindSelectionEditPanel(new ControllerOptions(), "internalName", ConditionSelectSingleton.EQUALS);
        panelName.setLayout(new BorderLayout());
        panelName.add(BorderLayout.CENTER, nameFindSelectionEditPanel);

        buyPriceFindSelectionEditPanel = new org.ofbiz.ordermax.base.components.FindSelectionEditPanel(new ControllerOptions(), "buyPrice", ConditionSelectSingleton.EQUALS);
        panelBuyPrice.setLayout(new BorderLayout());
        panelBuyPrice.add(BorderLayout.CENTER, buyPriceFindSelectionEditPanel);

        sellPriceFindSelectionEditPanel = new org.ofbiz.ordermax.base.components.FindSelectionEditPanel(new ControllerOptions(), "sellPrice", ConditionSelectSingleton.EQUALS);
        panelSellPrice.setLayout(new BorderLayout());
        panelSellPrice.add(BorderLayout.CENTER, sellPriceFindSelectionEditPanel);

        categoryPickerEditPanel = new JGenericComboBoxSelectionModel<ProductCategory>(panelCategory, ProductCategoryMemberSingleton.getValueList());
        // panelCategory.setLayout(new BorderLayout());
        //panelCategory.add(BorderLayout.CENTER, categoryPickerEditPanel);        
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public EntityCondition getEntityCondition() {
        return nameFindSelectionEditPanel.getEntityCondition();
    }

    @Override
    public Map<String, Object> getValues() throws BasicException {
        
        Map<String, Object> values = new HashMap<String, Object>();
        if (categoryPickerEditPanel.getSelectedItem() != null) {
            values.put("productCategoryId", categoryPickerEditPanel.getSelectedItem().getProductCategoryId());
        }

        if (idFindSelectionEditPanel.isValidOption()) {
            values.put("productId", idFindSelectionEditPanel.getFindSelectionOptions());
        }
        
        if (nameFindSelectionEditPanel.isValidOption()) {
           values.put("productName", nameFindSelectionEditPanel.getFindSelectionOptions());
        }
        
        if (buyPriceFindSelectionEditPanel.isValidOption()) {
           values.put("buyPrice", buyPriceFindSelectionEditPanel.getFindSelectionOptions());
        }
        
        if (sellPriceFindSelectionEditPanel.isValidOption()) {
           values.put("sellPrice", sellPriceFindSelectionEditPanel.getFindSelectionOptions());
        }

        return values;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panelName = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelBuyPrice = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelSellPrice = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelCategory = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelId = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setMinimumSize(new java.awt.Dimension(240, 80));
        setPreferredSize(new java.awt.Dimension(240, 180));
        setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setLabelFor(panelName);
        jLabel1.setText("Name");
        add(jLabel1);
        jLabel1.setBounds(20, 50, 100, 16);
        jLabel1.getAccessibleContext().setAccessibleName("");

        panelName.setBackground(new java.awt.Color(255, 255, 204));
        panelName.setPreferredSize(new java.awt.Dimension(100, 24));
        add(panelName);
        panelName.setBounds(140, 50, 400, 24);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setLabelFor(panelName);
        jLabel2.setText("Buy Price");
        add(jLabel2);
        jLabel2.setBounds(20, 80, 100, 16);

        panelBuyPrice.setPreferredSize(new java.awt.Dimension(100, 24));
        add(panelBuyPrice);
        panelBuyPrice.setBounds(140, 80, 400, 24);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setLabelFor(panelName);
        jLabel3.setText("Sell Price");
        add(jLabel3);
        jLabel3.setBounds(20, 110, 100, 16);

        panelSellPrice.setPreferredSize(new java.awt.Dimension(100, 24));
        add(panelSellPrice);
        panelSellPrice.setBounds(140, 110, 400, 24);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setLabelFor(panelName);
        jLabel4.setText("Category");
        add(jLabel4);
        jLabel4.setBounds(20, 140, 100, 16);

        panelCategory.setPreferredSize(new java.awt.Dimension(100, 24));
        add(panelCategory);
        panelCategory.setBounds(140, 140, 400, 24);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setLabelFor(panelName);
        jLabel5.setText("Id");
        add(jLabel5);
        jLabel5.setBounds(20, 22, 100, 16);

        panelId.setBackground(new java.awt.Color(255, 255, 204));
        panelId.setPreferredSize(new java.awt.Dimension(100, 24));
        add(panelId);
        panelId.setBounds(140, 22, 400, 24);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel panelBuyPrice;
    private javax.swing.JPanel panelCategory;
    private javax.swing.JPanel panelId;
    private javax.swing.JPanel panelName;
    private javax.swing.JPanel panelSellPrice;
    // End of variables declaration//GEN-END:variables

}
