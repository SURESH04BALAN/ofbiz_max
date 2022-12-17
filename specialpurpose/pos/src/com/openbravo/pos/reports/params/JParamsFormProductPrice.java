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
import javax.swing.JPanel;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ProductPriceTypeListCellRenderer;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.ProductStoreGroup;
import org.ofbiz.ordermax.entity.ProductType;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;
import org.ofbiz.ordermax.product.ProductStoreGroupSingleton;
import org.ofbiz.ordermax.product.ProductTypeSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.ordermax.report.ReportBaseMain;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

public class JParamsFormProductPrice extends ParamReportEditor {
     public String getEditorClassName(){
        return "JParamsFormProductPrice";
    }
    ProductPickerEditPanel panelProductIdPicker;
    ControllerOptions controllerOptions = null;
    JGenericComboBoxSelectionModel<ProductStoreGroup> productStoreGroupComboModel = null;
    public JComboBoxSelectionModel<Account> accountComboModel = null;
    public JGenericComboBoxSelectionModel<Product> productComboModel = null;
    public JComboBoxSelectionModel<ProductPriceType> productPriceTypeComboModel = null;

    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();

    public JParamsFormProductPrice(ControllerOptions controllerOptions) {
        initComponents();
        this.controllerOptions = controllerOptions;

        panelProductIdPicker = new ProductPickerEditPanel(controllerOptions);
        panelProductId.setLayout(new BorderLayout());
        panelProductId.add(BorderLayout.CENTER, panelProductIdPicker);
        filterList.add(panelProductIdPicker);
        
        List<ProductPriceType> findProductPriceType = ProductPriceTypeSingleton.getValueList();
        ProductPriceType tempProductPriceType = new ProductPriceType();
        tempProductPriceType.setdescription("<All>");
        tempProductPriceType.setproductPriceTypeId(null);
        findProductPriceType.add(0, tempProductPriceType);

        
        JPanel panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "productPriceTypeId", findProductPriceType, null, tempProductPriceType);
        panelPriceType.setLayout(new BorderLayout());
        panelPriceType.add(BorderLayout.CENTER, panel);
        

        List<ProductStoreGroup> productStoreGroups = ProductStoreGroupSingleton.getValueList();
        ProductStoreGroup productStoreGroup = new ProductStoreGroup();
        productStoreGroup.setdescription("All");
        productStoreGroup.setproductStoreGroupId("ANY");
        productStoreGroups.add(0, productStoreGroup);
        
        panel = ReportBaseMain.createReportSelectionComboPanel(filterList, "productStoreGroupId", productStoreGroups, null, productStoreGroup);
        panelStoreGroup.setLayout(new BorderLayout());
        panelStoreGroup.add(BorderLayout.CENTER, panel);
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

        jLabel3 = new javax.swing.JLabel();
        panelProductId = new javax.swing.JPanel();
        panelStoreGroup = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panelPriceType = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        setPreferredSize(new java.awt.Dimension(200, 140));
        setLayout(null);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Product Id:");
        add(jLabel3);
        jLabel3.setBounds(90, 30, 90, 16);

        panelProductId.setPreferredSize(new java.awt.Dimension(200, 24));

        javax.swing.GroupLayout panelProductIdLayout = new javax.swing.GroupLayout(panelProductId);
        panelProductId.setLayout(panelProductIdLayout);
        panelProductIdLayout.setHorizontalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelProductIdLayout.setVerticalGroup(
            panelProductIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelProductId);
        panelProductId.setBounds(190, 30, 300, 24);

        panelStoreGroup.setPreferredSize(new java.awt.Dimension(200, 24));

        javax.swing.GroupLayout panelStoreGroupLayout = new javax.swing.GroupLayout(panelStoreGroup);
        panelStoreGroup.setLayout(panelStoreGroupLayout);
        panelStoreGroupLayout.setHorizontalGroup(
            panelStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelStoreGroupLayout.setVerticalGroup(
            panelStoreGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelStoreGroup);
        panelStoreGroup.setBounds(190, 90, 300, 24);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Price Type Id:");
        add(jLabel4);
        jLabel4.setBounds(90, 60, 90, 20);

        panelPriceType.setPreferredSize(new java.awt.Dimension(200, 24));

        javax.swing.GroupLayout panelPriceTypeLayout = new javax.swing.GroupLayout(panelPriceType);
        panelPriceType.setLayout(panelPriceTypeLayout);
        panelPriceTypeLayout.setHorizontalGroup(
            panelPriceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        panelPriceTypeLayout.setVerticalGroup(
            panelPriceTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelPriceType);
        panelPriceType.setBounds(190, 60, 300, 24);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Store Group:");
        add(jLabel6);
        jLabel6.setBounds(90, 90, 90, 20);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel panelPriceType;
    private javax.swing.JPanel panelProductId;
    private javax.swing.JPanel panelStoreGroup;
    // End of variables declaration//GEN-END:variables

}
