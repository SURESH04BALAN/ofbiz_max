/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.PrinterStringListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.DatePicker;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.composite.GoodIdentificationList;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductCompositeLabelPrint;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.ProductPriceList;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.product.CustomProductPanel;
import org.ofbiz.ordermax.product.UomWeightSingleton;
import org.ofbiz.ordermax.product.printlabel.PanelSelectedProductPrintLabelTable;
import org.ofbiz.ordermax.product.printlabel.ProductCategoryGroupSelectSingleton;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class PanelProductLabelPrint extends javax.swing.JPanel {

    public static final String module = PanelProductLabelPrint.class.getName();
    private JGenericComboBoxSelectionModel<Uom> weightUom = null;
    private JGenericComboBoxSelectionModel<String> productCategory = null;
    public PanelSelectedProductPrintLabelTable panelSelectedTable = null;

    /**
     * @return the productCompositeLabelPrint
     */
    public ProductCompositeLabelPrint getProductCompositeLabelPrint() {
        return productCompositeLabelPrint;
    }

    /**
     * @param productCompositeLabelPrint the productCompositeLabelPrint to set
     */
    public void setProductCompositeLabelPrint(ProductCompositeLabelPrint productCompositeLabelPrint) {
        this.productCompositeLabelPrint = productCompositeLabelPrint;
    }

    private class SetupSelectionModel<E> {

        final private ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
        private ListComboBoxModel<E> comboBoxModel = new ListComboBoxModel<E>();
        private ListSelectionModel selectionModel = new DefaultListSelectionModel();
        private ListModelSelection<E> listModelSelection = new ListModelSelection<E>();

        private SetupSelectionModel(List<E> values, JComboBox comboBox, ListCellRenderer<E> render) {
            dataListModel.addAll(values);
            comboBoxModel.setListModel(dataListModel);
            selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listModelSelection.setListModels(dataListModel, selectionModel);
            comboBoxModel.setListSelectionModel(selectionModel);
            comboBox.setModel(comboBoxModel);
            comboBox.setRenderer(render);
        }
    }

    private SetupSelectionModel<PrintService> printersSelectionComboModel = null;
    List<PrintService> printList = new ArrayList<PrintService>();

    private ProductCompositeLabelPrint productCompositeLabelPrint = null;

    /**
     * Creates new form PanelProductLabelPrint
     */
    public PanelProductLabelPrint() {
        initComponents();

        weightUom = new JGenericComboBoxSelectionModel<Uom>(panelWeightStr, UomWeightSingleton.getValueList());
        try {
            Uom uom = UomWeightSingleton.getUom("WT_kg");
            weightUom.setSelectedItem(uom);
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        productCategory = new JGenericComboBoxSelectionModel<String>(panelProductCategory, ProductCategoryGroupSelectSingleton.getValueList());
        try {
            String uom = ProductCategoryGroupSelectSingleton.getString(ProductCategoryGroupSelectSingleton.LENTILS);
            productCategory.setSelectedItem(uom);
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Number of print services: " + printServices.length);
//        List<PrintService>
        for (PrintService printer : printServices) {
            printList.add(printer);
        }
        printersSelectionComboModel = new SetupSelectionModel<PrintService>(printList,
                comboPrinters, new PrinterStringListCellRenderer(false));
        panelSelectedTable = new PanelSelectedProductPrintLabelTable();
        OrderMaxUtility.addAPanelGrid(panelSelectedTable, panelPrintLabelList);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtPackDate = new ObservingTextField();
        txtProductName = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtUseByDate = new ObservingTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtBarCode = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txtPrice = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtIngrident = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        txtShelfLife = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        txtWeight = new javax.swing.JTextField();
        btnUpdateTable = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnSaveToDB = new javax.swing.JButton();
        btnBarCodeGenerate = new javax.swing.JButton();
        panelWeightStr = new javax.swing.JPanel();
        txtWeightUom = new javax.swing.JTextField();
        panelProductCategory = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtCountryOfOrigin = new ObservingTextField();
        btnAddProduct = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        printLabel = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        txtLabelCount = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        comboPrinters = new javax.swing.JComboBox();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        panelPrintLabelList = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnPrintShelfLabel = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        comboPrinters1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpZebraCode = new javax.swing.JTextPane();
        btnZebraPrint = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 450));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(900, 450));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Label Details"));
        jPanel9.setPreferredSize(new java.awt.Dimension(400, 379));

        txtProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameActionPerformed(evt);
            }
        });

        jLabel21.setText("Product Name:");

        jLabel24.setText("Weight:");

        jLabel27.setText("Ingrident:");

        jLabel23.setText("Price:");

        jLabel26.setText("Use By Date:");

        jLabel25.setText("Packed Date:");

        jLabel22.setText("Bar Code:");

        jButton4.setText("Date");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Date");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Refresh");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        txtPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        txtPrice.setToolTipText("");

        txtIngrident.setColumns(20);
        txtIngrident.setRows(5);
        jScrollPane2.setViewportView(txtIngrident);

        jLabel20.setText("Shelf Life:");

        txtShelfLife.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtShelfLife.setText("730");
        txtShelfLife.setToolTipText("");

        jLabel33.setText("Days");

        btnUpdateTable.setText("Update Table");
        btnUpdateTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTableActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnSaveToDB.setText("Save To DB");

        btnBarCodeGenerate.setText("Bar Code");

        panelWeightStr.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelWeightStr.setPreferredSize(new java.awt.Dimension(100, 22));

        javax.swing.GroupLayout panelWeightStrLayout = new javax.swing.GroupLayout(panelWeightStr);
        panelWeightStr.setLayout(panelWeightStrLayout);
        panelWeightStrLayout.setHorizontalGroup(
            panelWeightStrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWeightStrLayout.createSequentialGroup()
                .addComponent(txtWeightUom, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelWeightStrLayout.setVerticalGroup(
            panelWeightStrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtWeightUom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelProductCategory.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelProductCategory.setPreferredSize(new java.awt.Dimension(100, 22));

        javax.swing.GroupLayout panelProductCategoryLayout = new javax.swing.GroupLayout(panelProductCategory);
        panelProductCategory.setLayout(panelProductCategoryLayout);
        panelProductCategoryLayout.setHorizontalGroup(
            panelProductCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelProductCategoryLayout.setVerticalGroup(
            panelProductCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jLabel38.setText("Category:");

        jLabel39.setText("Country of Origin:");

        btnAddProduct.setText("Add Product");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtBarCode, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBarCodeGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPackDate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUseByDate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4)
                                    .addComponent(jButton5)))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(panelWeightStr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelProductCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCountryOfOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtShelfLife, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(98, 98, 98))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnAddProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSaveToDB))
                            .addComponent(btnUpdateTable))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(panelProductCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtBarCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBarCodeGenerate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelWeightStr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtPackDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtUseByDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtCountryOfOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtShelfLife, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel27)))))
                .addGap(7, 7, 7)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateTable)
                    .addComponent(jButton6)
                    .addComponent(btnRemove)
                    .addComponent(btnSaveToDB)
                    .addComponent(btnAddProduct)))
        );

        jPanel1.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Printer Settings"));
        jPanel12.setPreferredSize(new java.awt.Dimension(200, 441));

        printLabel.setText("Print Label");
        printLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printLabelActionPerformed(evt);
            }
        });

        jLabel28.setText("Number OF Copies:");

        txtLabelCount.setText("1");

        jLabel29.setText("Printer Name:");

        jLabel30.setText("Label Type");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPrinters, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtLabelCount, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(printLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(64, Short.MAX_VALUE))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboPrinters, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLabelCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(printLabel)
                .addContainerGap(423, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel12, java.awt.BorderLayout.EAST);

        jTabbedPane2.addTab("Products", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelPrintLabelList.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panelPrintLabelList.setPreferredSize(new java.awt.Dimension(400, 379));

        javax.swing.GroupLayout panelPrintLabelListLayout = new javax.swing.GroupLayout(panelPrintLabelList);
        panelPrintLabelList.setLayout(panelPrintLabelListLayout);
        panelPrintLabelListLayout.setHorizontalGroup(
            panelPrintLabelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );
        panelPrintLabelListLayout.setVerticalGroup(
            panelPrintLabelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 661, Short.MAX_VALUE)
        );

        jPanel2.add(panelPrintLabelList, java.awt.BorderLayout.CENTER);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Printer Settings"));
        jPanel13.setPreferredSize(new java.awt.Dimension(200, 441));

        btnPrintShelfLabel.setText("Print Label");
        btnPrintShelfLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintShelfLabelActionPerformed(evt);
            }
        });

        jLabel46.setText("Printer Name:");

        jLabel47.setText("Label Type");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPrinters1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrintShelfLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(64, Short.MAX_VALUE))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboPrinters1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(btnPrintShelfLabel)
                .addContainerGap(423, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jTabbedPane2.addTab("Print Multiple", jPanel2);

        tpZebraCode.setText("^FX Delete Format^FS\n  ^XA ^EF^FS \n^XZ ^FX Save Format^FS\n ^XA^DFFORMAT^FS\n ^LH0,0 ^BY2,3,100 ^FO10,10^CFD50,40^FN1^FS \n^FO270,120,^BY3,^BE,N,10,N,N^FN2^FA20^FS\n^FO10,265^CFB0,20^FN3^FA50^FS\n^FO10,300^CFD0,20^FN4^FA50^FS\n^FO10,325^CFD0,20^FN5^FA50^FS\n^FX Item Details^FS\n^FO10, 120^CFD0,15^FN7^FA50^FS\n^FO10,160^CFD0,15^FN8^FA50^FS\n^FO10,200^CFD0,15^FN9^FA50^FS\n^FO590,120^CFD0,15^FN10^FA50^FS\n^FO590,200^CFD0,15^FN11^FA50^FS\n^FO115, 120^CFB0,20^FN13^FA50^FS\n^FO115,160^CFD0,15^FN14^FA50^FS\n^FO115,200^CFD0,15^FN15^FA50^FS\n^FO670,120^CFD0,15^FN16^FA50^FS\n^FO590,160^CFD0,15^FN17^FA50^FS\n^FO590,240^CFD0,15^FN18^FA50^FS\n^XZ^FXSHIPPING LABEL^FS\n^XA^LL660^LH0,0^FO10,70^GB775,4,4^FS\n^XFFORMAT^FS^FN1^FDBLUE PEAS 1KG^FS\n^FN2^FD000000261585^FS\n^FN7^FDWeight:^FS\n^FN8^FDPacked:^FS\n^FN9^FDUse By:^FS\n^FN10^FDIngrident:^FS\n^FN11^FDOrigin:^FS\n^FN13^FD1 kg^FS\n^FN14^FD29/01/2020^FS\n^FN15^FD18/01/2022^FS\n^FN16^FD^FS\n^FN17^FD^FS\n^FN18^FDProsduct of Australia^FS\n^PQ1^XZ");
        jScrollPane1.setViewportView(tpZebraCode);

        btnZebraPrint.setText("test Print");
        btnZebraPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZebraPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnZebraPrint)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnZebraPrint)
                .addGap(0, 385, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Testing", jPanel3);

        add(jTabbedPane2, java.awt.BorderLayout.CENTER);
        jTabbedPane2.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void txtProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        // TODO add your handling code here:
        String lang = null;
        final Locale locale = getLocale(lang);
        DatePicker dp = new DatePicker((ObservingTextField) txtPackDate, locale);
        //previously Selectd date
        java.util.Date selectedDate = dp.parseDate(txtPackDate.getText());
        dp.setSelectedDate(selectedDate);
        dp.start(txtPackDate);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        // TODO add your handling code here:
        String lang = null;
        final Locale locale = getLocale(lang);
        DatePicker dp = new DatePicker((ObservingTextField) txtUseByDate, locale);
        //previously Selectd date
        java.util.Date selectedDate = dp.parseDate(txtUseByDate.getText());
        dp.setSelectedDate(selectedDate);
        dp.start(txtUseByDate);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

    }//GEN-LAST:event_jButton6ActionPerformed
    private ProductComposite productComposite = null;

    public ProductComposite getProductComposite() {
        return productComposite;
    }

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }

    public void setSelectedPrintListModel(ListAdapterListModel<ProductCompositeLabelPrint> listModel) {
        panelSelectedTable.setMemberList(listModel);
    }

    public void clearDialogFields() {
    }

    public void getDialogField() {
    }

    public void setDialogFieldFromLabelPrint() {

        txtProductName.setText(productCompositeLabelPrint.getProductName());

        txtWeight.setText(productCompositeLabelPrint.getWeight().toString());

        txtWeightUom.setText(productCompositeLabelPrint.getWeightStr());

        try {
            if (UtilValidate.isNotEmpty(productCompositeLabelPrint.getProductComposite().getProduct().getweightUomId())) {
                Uom uom = UomWeightSingleton.getUom(productCompositeLabelPrint.getProductComposite().getProduct().getweightUomId());
                weightUom.setSelectedItem(uom);
                if (weightUom.getSelectedItem() != null) {
                    txtWeightUom.setText(weightUom.getSelectedItem().getabbreviation());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(productCompositeLabelPrint.getProductCategory())) {
                String uom = ProductCategoryGroupSelectSingleton.getString(productCompositeLabelPrint.getProductCategory());
                productCategory.setSelectedItem(uom);
                //   if (weightUom.getSelectedItem() != null) {
                //       txtWeightUom.setText(weightUom.getSelectedItem().getabbreviation());
                //   }
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtPrice.setText(productCompositeLabelPrint.getPrice().toString());

        txtBarCode.setText(productCompositeLabelPrint.getScanCode());

        setStartAndEndDate(productCompositeLabelPrint.getPackingDate(), productCompositeLabelPrint.getExpireDate());
        /*
         cbPrintLiverpool.setSelected(productCompositeLabelPrint.getSelectedLiverpool());
         txtLiverpoolLabelCount.setText(productCompositeLabelPrint.getNumberOfLabelsLiverpool().toString());
         txtLiverpoolPrice.setText(productCompositeLabelPrint.getPriceLiverpool().toString());
         cbPrintRootyHill.setSelected(productCompositeLabelPrint.getSelectedRootyHill());
         txtRootyHillLabelCount.setText(productCompositeLabelPrint.getNumberOfLabelsRootyHill().toString());
         txtRootyHillPrice.setText(productCompositeLabelPrint.getPriceRootyHill().toString());
         cbPrintToongabbie.setSelected(productCompositeLabelPrint.getSelectedToongabbie());
         txtToongabbieLabelCount.setText(productCompositeLabelPrint.getNumberOfLabels().toString());
         txtToongabbiePrice.setText(productCompositeLabelPrint.getPrice().toString());
         */
        txtIngrident.setText(productCompositeLabelPrint.getIngredientList());
        txtCountryOfOrigin.setText(productCompositeLabelPrint.getCountryOfOrigin());
    }

    public void getDialogFieldFromLabelPrint() {

        productCompositeLabelPrint.setProductName(txtProductName.getText());

        productCompositeLabelPrint.setWeight(Double.valueOf(txtWeight.getText()));

//        productCompositeLabelPrint.setWeightStr(txtWeightUom.getText());
        try {
            productCompositeLabelPrint.setWeightStr(weightUom.getSelectedItem().getuomId());//productEntity.getBoolean("taxable"));                
        } catch (Exception ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        productCompositeLabelPrint.setProductCategory(productCategory.getSelectedItem());
        // String uom = ProductCategoryGroupSelectSingleton.getString(productCompositeLabelPrint.getProductCategory());
        // productCategory.setSelectedItem(uom);
        //   if (weightUom.getSelectedItem() != null) {
        //       txtWeightUom.setText(weightUom.getSelectedItem().getabbreviation());
        //   }
        //}
        productCompositeLabelPrint.setPrice(Double.valueOf(txtPrice.getText()));

        productCompositeLabelPrint.setScanCode(txtBarCode.getText());
        productCompositeLabelPrint.setIngredientList(txtIngrident.getText());
        productCompositeLabelPrint.setCountryOfOrigin(txtCountryOfOrigin.getText());
        try {
            //txtUseByDate.setText(
            productCompositeLabelPrint.setExpireDate(new SimpleDateFormat("dd/MM/yyyy").parse(txtUseByDate.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(PanelProductLabelPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            productCompositeLabelPrint.setPackingDate(new SimpleDateFormat("dd/MM/yyyy").parse(txtPackDate.getText()));
        } catch (ParseException ex) {
            Logger.getLogger(PanelProductLabelPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*        productCompositeLabelPrint.setSelectedLiverpool(cbPrintLiverpool.isSelected());

         productCompositeLabelPrint.setNumberOfLabelsLiverpool(Integer.parseInt(txtLiverpoolLabelCount.getText()));
         productCompositeLabelPrint.setPriceLiverpool(Double.parseDouble(txtLiverpoolPrice.getText()));

         productCompositeLabelPrint.setSelectedRootyHill(cbPrintRootyHill.isSelected());
         productCompositeLabelPrint.setNumberOfLabelsRootyHill(Integer.parseInt(txtRootyHillLabelCount.getText()));
         productCompositeLabelPrint.setPriceRootyHill(Double.parseDouble(txtRootyHillPrice.getText()));

         productCompositeLabelPrint.setSelectedToongabbie(cbPrintToongabbie.isSelected());
         productCompositeLabelPrint.setNumberOfLabels(Integer.parseInt(txtToongabbieLabelCount.getText()));
         productCompositeLabelPrint.setPrice(Double.parseDouble(txtToongabbiePrice.getText()));*/
        ProductPriceList list = productCompositeLabelPrint.getProductComposite().getProductItemPrice().getDefaultPrice();//.getList();
        if (UtilValidate.isNotEmpty(list) && list.getSize() > 0) {
            ProductPriceComposite productPriceComposite = (ProductPriceComposite) list.getElementAt(0);
            //productCompositeLabelPrint.setPrice(new Double(Double.parseDouble(txtPrice.getText())));
            productCompositeLabelPrint.setPrice(new Double(Double.parseDouble(txtPrice.getText())));

            productPriceComposite.getProductPrice().setprice(OrderMaxUtility.getValidBigDecimal(txtPrice.getText()));
            //productCompositeLabelPrint.setPrice(new Double(Double.parseDouble(txtPrice.getText())));
        }
    }

    public void setDialogField() {
        Product product = productComposite.getProduct();
        txtProductName.setText(product.getproductName());
        if (UtilValidate.isNotEmpty(product.getproductWeight())) {
            txtWeight.setText(product.getproductWeight().toString());
        }

        try {
            if (product.getweightUomId() != null) {
                txtWeightUom.setText(UomWeightSingleton.getUom(product.getweightUomId()).getabbreviation());
            }
        } catch (Exception ex) {
            Logger.getLogger(PanelProductLabelPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProductPriceList list = productComposite.getProductItemPrice().getDefaultPrice();//.getList();
        if (UtilValidate.isNotEmpty(list) && list.getSize() > 0) {
            ProductPriceComposite productPriceComposite = (ProductPriceComposite) list.getElementAt(0);
            txtPrice.setText(OrderMaxUtility.getValidBigDecimal(productPriceComposite.getProductPrice().getprice()));
        }

        GoodIdentificationList identificationList = productComposite.getGoodIdentificationList();
        for (GoodIdentification gf : identificationList.getList()) {
            if (gf.getgoodIdentificationTypeId().equals("EAN")) {
                txtBarCode.setText(gf.getidValue());
                break;
            }
        }

        /*        txtBarCode.setText(editScanCode.getText());
         txtPrice.setValue(new BigDecimal(editListPrice.getText()));
         txtWeight.setValue(new BigDecimal(txtItemWeight.getText()));
         txtWeightUom.setText(weightUomIdCombo.getSelectedValueDesc());
         txtIngrident.setText(txtAreaDescription.getText());
         */
        try {
            txtShelfLife.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        BigDecimal millis = BigDecimal.ZERO;
        try {
            millis = BigDecimal.valueOf(((Long) txtShelfLife.getValue()).longValue());
        } catch (Exception e) {
            millis = BigDecimal.valueOf(((Double) txtShelfLife.getValue()).doubleValue());
        }

//        millis = millis.multiply(BigDecimal.valueOf(365));
//        if (txtPackDate.getText() == null || txtPackDate.getText().isEmpty()) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(cal.getTime());                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
//        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(cal.getTimeInMillis());        

        millis = millis.multiply(BigDecimal.valueOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)));
//                    BigDecimal.valueOf(86400000).multiply(millis);
        setStartAndEndDate(new java.util.Date(cal.getTimeInMillis()), new java.util.Date(cal.getTimeInMillis() + millis.longValue()));
    }

    public void setStartAndEndDate(java.util.Date startDate, java.util.Date endDate) {
        String lang = null;
        final Locale locale = getLocale(lang);
        DatePicker dp = new DatePicker((ObservingTextField) txtPackDate, locale);
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(startDate);                           // set cal to date

        txtPackDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(startDate));
        txtUseByDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(endDate));

    }

  void btnUpdateTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTableActionPerformed

    }//GEN-LAST:event_btnUpdateTableActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnZebraPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZebraPrintActionPerformed
        try {
            PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob job = pservice.createPrintJob();

            String commands = tpZebraCode.getText();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
            job.print(doc, null);

        } catch (PrintException ex) {
            Debug.logError(ex, module);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnZebraPrintActionPerformed

    private void printLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printLabelActionPerformed
        /*try {
            String productName = txtProductName.getText();
            String barCode = txtBarCode.getText();
            //            if (txtBarCode.getText().length() == 12) {
            //                barCode = "0" + barCode;
            //            }

            //            BigDecimal qty = new BigDecimal(txtWeight.getText());
            BigDecimal qty = BigDecimal.ZERO;
            if (UtilValidate.isNotEmpty(txtWeight.getText())) {
                qty = new BigDecimal(txtWeight.getText());
            }

            BigDecimal price = BigDecimal.ZERO;
            if (UtilValidate.isNotEmpty(txtPrice.getText())) {
                price = new BigDecimal(txtPrice.getText());
            }
            String packedDate = txtPackDate.getText();;
            String useBy = txtUseByDate.getText();;
            String ingrifent = txtIngrident.getText();;
            String weightUom = txtWeightUom.getText();
            String strCountryOfOrigin = txtCountryOfOrigin.getText();
            Debug.logError(weightUom, module);
            try {
                Uom uom = UomWeightSingleton.getUom(weightUom);
                if (uom != null) {
                    weightUom = uom.getabbreviation();
                }
            } catch (Exception e) {
                Debug.logError(e, module);
            }
            PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
            org.ofbiz.ordermax.product.CustomProductPanel.printTransperentPackingLabel(productName,
                    barCode, qty, price, packedDate,
                    useBy, ingrifent, weightUom, strCountryOfOrigin, new BigDecimal(txtLabelCount.getText()).intValue(), pservice);
        } catch (Exception e) {
            Debug.logError(e, module);
        }*/
    }//GEN-LAST:event_printLabelActionPerformed

    private void btnPrintShelfLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintShelfLabelActionPerformed

        /*for (int i = 0; i < panelSelectedTable.productCompositePrintLabelTableModel.getRowCount(); ++i) {
            try {
                ProductCompositeLabelPrint elem = panelSelectedTable.productCompositePrintLabelTableModel.getRowData(i);
                printLabel(elem);
            } catch (Exception ex) {
                Logger.getLogger(PanelProductLabelPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
// TODO add your handling code here:
    }//GEN-LAST:event_btnPrintShelfLabelActionPerformed

    private void printLabel(ProductCompositeLabelPrint elem) {
        try {
            String productName = elem.getProductName();
            String barCode = elem.getScanCode();
            //            if (txtBarCode.getText().length() == 12) {
            //                barCode = "0" + barCode;
            //            }

            //            BigDecimal qty = new BigDecimal(txtWeight.getText());
            BigDecimal qty = BigDecimal.ZERO;
            if (UtilValidate.isNotEmpty(elem.getWeight())) {
                qty = new BigDecimal(elem.getWeight());
            }

            BigDecimal price = BigDecimal.ZERO;
            if (UtilValidate.isNotEmpty(elem.getPrice())) {
                price = new BigDecimal(elem.getPrice());
            }

            String packedDate = new SimpleDateFormat("dd/MM/yyyy").format(elem.getPackingDate());
            String useBy = new SimpleDateFormat("dd/MM/yyyy").format(elem.getExpireDate());
            String ingrifent = elem.getIngredientList();;
            String weightUom = elem.getWeightStr();
            String strCountryOfOrigin = elem.getCountryOfOrigin();
            Debug.logError(weightUom, module);
            try {
                Uom uom = UomWeightSingleton.getUom(weightUom);
                if (uom != null) {
                    weightUom = uom.getabbreviation();
                }
            } catch (Exception e) {
                Debug.logError(e, module);
            }
            PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
            org.ofbiz.ordermax.product.CustomProductPanel.printTransperentPackingLabel(productName,
                    barCode, qty, price, packedDate,
                    useBy, ingrifent, weightUom, strCountryOfOrigin, new BigDecimal(elem.getNumberOfLabels()).intValue(), pservice);
        } catch (Exception e) {
            Debug.logError(e, module);
        }
    }

    private Locale getLocale(String loc) {
        if (loc != null && loc.length() > 0) {
            return new Locale(loc);
        } else {
            return Locale.getDefault();
        }

    }

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = XuiContainer.getSession().getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAddProduct;
    public javax.swing.JButton btnBarCodeGenerate;
    public javax.swing.JButton btnPrintShelfLabel;
    public javax.swing.JButton btnRemove;
    public javax.swing.JButton btnSaveToDB;
    public javax.swing.JButton btnUpdateTable;
    private javax.swing.JButton btnZebraPrint;
    private javax.swing.JComboBox comboPrinters;
    private javax.swing.JComboBox comboPrinters1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JPanel panelPrintLabelList;
    private javax.swing.JPanel panelProductCategory;
    private javax.swing.JPanel panelWeightStr;
    public javax.swing.JButton printLabel;
    private javax.swing.JTextPane tpZebraCode;
    public javax.swing.JTextField txtBarCode;
    public javax.swing.JTextField txtCountryOfOrigin;
    public javax.swing.JTextArea txtIngrident;
    public javax.swing.JTextField txtLabelCount;
    public javax.swing.JTextField txtPackDate;
    public javax.swing.JFormattedTextField txtPrice;
    public javax.swing.JTextField txtProductName;
    public javax.swing.JFormattedTextField txtShelfLife;
    public javax.swing.JTextField txtUseByDate;
    public javax.swing.JTextField txtWeight;
    public javax.swing.JTextField txtWeightUom;
    // End of variables declaration//GEN-END:variables
}
