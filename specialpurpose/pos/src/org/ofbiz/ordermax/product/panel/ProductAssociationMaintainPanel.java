/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import org.ofbiz.ordermax.product.productContent.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadProductWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ProductAssocTableModel;
import mvc.model.table.ProductStoreCatalogTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.ProductPickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductAssoc;
import org.ofbiz.ordermax.entity.ProductAssocType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.Uom;

/**
 *
 * @author siranjeev
 */
public class ProductAssociationMaintainPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    final public static String module = ProductAssociationMaintainPanel.class.getName();

    boolean isNew = false;
    boolean isModified = false;
    ProductAssociationFromPanel productAssociationFromPanel = new ProductAssociationFromPanel();
    ProductAssociationToPanel productAssociationToPanel = new ProductAssociationToPanel();
    ControllerOptions controllerOptions = new ControllerOptions();
    public ProductAssociationMaintainPanel() {
        initComponents();
        tabbedPaneProductContent.add("Associations FROM this Product to", productAssociationFromPanel);
        tabbedPaneProductContent.add("Associations TO this Product from", productAssociationToPanel);
    }

//    ProdCatalog prodCatalog = null;
    public void setDialogFields(ProductAssoc productAssoc) {
    }

    public void clearDialogFields() {

   
    }

    public void getDialogFields(ProductAssoc productAssoc) {    
    }

    public void setproductAssocList(ListAdapterListModel<ProductAssoc> productAssocList) {       
    }


    ProductComposite productComposite = null;

    public void setProductComposite(ProductComposite productComposite) {
        productAssociationFromPanel.setProductComposite(productComposite);
        productAssociationToPanel.setProductComposite(productComposite);
        
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPaneProductContent = new javax.swing.JTabbedPane();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.GridLayout(1, 0));
        add(tabbedPaneProductContent);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabbedPaneProductContent;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return null;
    }

    @Override
    public JButton getCancelButton() {
        return null;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
