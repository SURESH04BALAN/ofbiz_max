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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.StringListCellRenderer;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductType;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.CreateProductIdPanel;
import org.ofbiz.ordermax.product.ProductTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

public class JParamsFormProducts extends ParamReportEditor {

    public String getEditorClassName() {
        return "JParamsFormProducts";
    }
    public JGenericComboBoxSelectionModel<ProductType> productTypeComboModel = null;
    public JGenericComboBoxSelectionModel<ProductCategory> productCategoryComboModel = null;
    public JGenericComboBoxSelectionModel<String> isVirtualComboBoxModel = null;
    public JGenericComboBoxSelectionModel<String> isVariantComboBoxModel = null;

    boolean showComboKeys = false;
    protected ControllerOptions controllerOptions;

    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();

    public JParamsFormProducts(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;

        JPanel         panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "productName", ConditionSelectSingleton.CONTAINS);
        panelInternalName.setLayout(new BorderLayout());
        panelInternalName.add(BorderLayout.CENTER, panel);
                
        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "productId", ConditionSelectSingleton.EQUALS);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(BorderLayout.CENTER, panel);

        panel = ReportBaseMain.AddTextIdSelection(filterList, controllerOptions, null, "brandName", ConditionSelectSingleton.EQUALS);
        panelBrandName.setLayout(new BorderLayout());
        panelBrandName.add(BorderLayout.CENTER, panel);



        List<ProductType> findProductList = ProductTypeSingleton.getValueList();
        ProductType temp = new ProductType();
        temp.setdescription("All");
        temp.setproductTypeId("All");
        findProductList.add(0, temp);

        //productTypeComboModel = new JGenericComboBoxSelectionModel<ProductType>(findProductList);
        //panelProductTypeId.setLayout(new BorderLayout());
        //panelProductTypeId.add(BorderLayout.CENTER, productTypeComboModel.jComboBox);
        //productTypeComboModel.comboBoxModel.setSelectedItem(temp);
        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "productTypeId", findProductList, null, temp);
        panelProductTypeId.setLayout(new BorderLayout());
        panelProductTypeId.add(BorderLayout.CENTER, panel);

        List<ProductCategory> nameProductList = ProductCategorySingleton.getValueList();
        ProductCategory tmp = new ProductCategory();
        tmp.setDescription("ALL");
        tmp.setProductCategoryId("All");
        nameProductList.add(0, tmp);

        //productCategoryComboModel = new JGenericComboBoxSelectionModel<ProductCategory>(nameProductList);
        //panelPrimaryCategory.setLayout(new BorderLayout());
        //panelPrimaryCategory.add(BorderLayout.CENTER, productCategoryComboModel.jComboBox);
        //productCategoryComboModel.comboBoxModel.setSelectedItem(tmp);
        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "primaryProductcategoryid", nameProductList, null, tmp);
        panelPrimaryCategory.setLayout(new BorderLayout());
        panelPrimaryCategory.add(BorderLayout.CENTER, panel);

        //isVariantComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVariantProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
        try {
            String str = YesNoConditionSelectSingleton.getString("N");
            //isVirtualComboBoxModel = new JGenericComboBoxSelectionModel<String>(panelVirtualProd, YesNoConditionSelectSingleton.getValueList(), new StringListCellRenderer(false));
            panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "isVirtual", YesNoConditionSelectSingleton.getValueList(), null, str);
            panelVirtualProd.setLayout(new BorderLayout());
            panelVirtualProd.add(BorderLayout.CENTER, panel);

            str = YesNoConditionSelectSingleton.getString("Y");
            panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "isVariant", YesNoConditionSelectSingleton.getValueList(), null, str);
            panelVariantProd.setLayout(new BorderLayout());
            panelVariantProd.add(BorderLayout.CENTER, panel);
        } catch (Exception ex) {
            Logger.getLogger(CreateProductIdPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public EntityCondition getEntityCondition() {
        return JParamHelper.getEntityCondition(filterList);
    }

    @Override
    public Map<String, Object> getValues() throws BasicException {
        return JParamHelper.getValuesMap(filterList);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelProductTypeId = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        panelPrimaryCategory = new javax.swing.JPanel();
        panelInternalName = new javax.swing.JPanel();
        panelProductId = new javax.swing.JPanel();
        panelBrandName = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        panelVirtualProd = new javax.swing.JPanel();
        panelVariantProd = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 220));
        setLayout(null);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Product Id:");
        add(jLabel2);
        jLabel2.setBounds(190, 40, 104, 25);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Product Type:");
        add(jLabel4);
        jLabel4.setBounds(193, 96, 104, 25);

        panelProductTypeId.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelProductTypeIdLayout = new javax.swing.GroupLayout(panelProductTypeId);
        panelProductTypeId.setLayout(panelProductTypeIdLayout);
        panelProductTypeIdLayout.setHorizontalGroup(
            panelProductTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelProductTypeIdLayout.setVerticalGroup(
            panelProductTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelProductTypeId);
        panelProductTypeId.setBounds(310, 96, 360, 25);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Internal Name:");
        add(jLabel5);
        jLabel5.setBounds(190, 10, 104, 25);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Brand Name:");
        add(jLabel7);
        jLabel7.setBounds(190, 70, 104, 25);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Primary Category:");
        add(jLabel8);
        jLabel8.setBounds(193, 129, 104, 25);

        panelPrimaryCategory.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelPrimaryCategoryLayout = new javax.swing.GroupLayout(panelPrimaryCategory);
        panelPrimaryCategory.setLayout(panelPrimaryCategoryLayout);
        panelPrimaryCategoryLayout.setHorizontalGroup(
            panelPrimaryCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelPrimaryCategoryLayout.setVerticalGroup(
            panelPrimaryCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelPrimaryCategory);
        panelPrimaryCategory.setBounds(310, 129, 360, 25);

        javax.swing.GroupLayout panelInternalNameLayout = new javax.swing.GroupLayout(panelInternalName);
        panelInternalName.setLayout(panelInternalNameLayout);
        panelInternalNameLayout.setHorizontalGroup(
            panelInternalNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelInternalNameLayout.setVerticalGroup(
            panelInternalNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelInternalName);
        panelInternalName.setBounds(310, 10, 360, 25);

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelProductId);
        panelProductId.setBounds(310, 40, 360, 25);

        javax.swing.GroupLayout panelBrandNameLayout = new javax.swing.GroupLayout(panelBrandName);
        panelBrandName.setLayout(panelBrandNameLayout);
        panelBrandNameLayout.setHorizontalGroup(
            panelBrandNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelBrandNameLayout.setVerticalGroup(
            panelBrandNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelBrandName);
        panelBrandName.setBounds(310, 70, 360, 25);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Virtual Product:");
        add(jLabel9);
        jLabel9.setBounds(190, 160, 110, 25);

        panelVirtualProd.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelVirtualProdLayout = new javax.swing.GroupLayout(panelVirtualProd);
        panelVirtualProd.setLayout(panelVirtualProdLayout);
        panelVirtualProdLayout.setHorizontalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelVirtualProdLayout.setVerticalGroup(
            panelVirtualProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelVirtualProd);
        panelVirtualProd.setBounds(310, 160, 360, 25);

        panelVariantProd.setMinimumSize(new java.awt.Dimension(0, 22));

        javax.swing.GroupLayout panelVariantProdLayout = new javax.swing.GroupLayout(panelVariantProd);
        panelVariantProd.setLayout(panelVariantProdLayout);
        panelVariantProdLayout.setHorizontalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        panelVariantProdLayout.setVerticalGroup(
            panelVariantProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        add(panelVariantProd);
        panelVariantProd.setBounds(310, 190, 360, 25);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Variant Product:");
        add(jLabel10);
        jLabel10.setBounds(190, 190, 110, 25);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel panelBrandName;
    private javax.swing.JPanel panelInternalName;
    private javax.swing.JPanel panelPrimaryCategory;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelProductTypeId;
    private javax.swing.JPanel panelVariantProd;
    private javax.swing.JPanel panelVirtualProd;
    // End of variables declaration//GEN-END:variables

}
