/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.printshelflabel;

import org.ofbiz.ordermax.product.printlabel.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import mvc.controller.LoadProductWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ProductCompositePrintLabelTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer;
import org.ofbiz.ordermax.composite.GoodIdentificationList;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductCompositeLabelPrint;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.ProductPriceList;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.party.PartyIdInterface;
import org.ofbiz.ordermax.product.ProductIdClickAction;
import org.ofbiz.ordermax.product.GetProductIdInterface;
import org.ofbiz.ordermax.product.ProductCreateUniqueScanCode;
import org.ofbiz.ordermax.product.UomWeightSingleton;
import org.ofbiz.ordermax.product.panel.PanelProductLabelPrint;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class ProductPrintShelfLabelController extends BaseMainScreen
        implements GetProductIdInterface, PartyIdInterface {

    public static final String module = ProductPrintShelfLabelController.class.getName();
    public ProductPrintLabelTable panel = null;
    public PanelProductLabelPrint labelDetailPanel = null;
//    final ListAdapterListModel<ProductCompositeLabelPrint> orderListModel = new ListAdapterListModel<ProductCompositeLabelPrint>();
    final private ListAdapterListModel<ProductCompositeLabelPrint> invoiceCompositeListModel = new ListAdapterListModel<ProductCompositeLabelPrint>();
    final private ListAdapterListModel<ProductCompositeLabelPrint> selectedPrintLabelList = new ListAdapterListModel<ProductCompositeLabelPrint>();
    private ControllerOptions controllerOptions;
    protected LookupActionListner lookupActionListner = null;
    //ControllerOptions options = new ControllerOptions();

    public String getCaption() {

        return "Print Shelf Labels";
    }

    public ProductPrintShelfLabelController(ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        this.controllerOptions = controllerOptions;
    }

    public ProductPrintShelfLabelController(ListAdapterListModel<ProductCompositeLabelPrint> ordListModel, ControllerOptions controllerOptions, XuiSession sess) {
        super(sess);
        invoiceCompositeListModel.addAll(ordListModel.getList());
        this.controllerOptions = controllerOptions;
    }

    ProductPrintLabelButtonPanel buttonPanel = null;
    String defaultFileLocation = "C:\\Users\\MaxSpice\\Documents\\productshelflabels_working.csv";

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new ProductPrintLabelTable();
        panel.setProductCompositeLabelPrintList(invoiceCompositeListModel);

        panel.btnAddToPrintList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPrintLabelList.clear();
                for (int i = 0; i < invoiceCompositeListModel.getSize(); ++i) {

                    ProductCompositeLabelPrint elem = invoiceCompositeListModel.getElementAt(i);
                    if (elem.getSelected()) {
                        selectedPrintLabelList.add(elem);
                    }
                }

            }
        });
        panel.btnClearPrintList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPrintLabelList.clear();
                for (int i = 0; i < invoiceCompositeListModel.getSize(); ++i) {

                    ProductCompositeLabelPrint elem = invoiceCompositeListModel.getElementAt(i);
                    if (elem.getSelected()) {
                        elem.setSelected(Boolean.FALSE);
                    }
                    panel.tableReceiveInv.updateUI();
                }
            }
        });

        labelDetailPanel = new PanelProductLabelPrint();
        labelDetailPanel.setSelectedPrintListModel(selectedPrintLabelList);

        buttonPanel = new ProductPrintLabelButtonPanel();

        OrderMaxUtility.addTwoPanelToPanel(panel, labelDetailPanel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());
        loadProductsFromFile(defaultFileLocation);
        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        lookupActionListner = new LookupActionListner(LookupActionListnerInterface.LookupType.ProductId, controllerOptions);
        lookupActionListner.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {
                final List<String> listStr = lookupActionListner.getResultIds();
                Debug.logError("listStr: " + listStr.size(), "Module");
                //String tmpproductId = editFindId.getText();
                if (!SwingUtilities.isEventDispatchThread()) {

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // save the changes
                            loadProducts(listStr);
                        }
                    });
                } else {
                    loadProducts(listStr);
                }
                if (listStr.size() > 0) {
                    invoiceCompositeListModel.fireListDataChanged();
                    panel.setDialogField();
                    panel.tableReceiveInv.updateUI();
                }
                Debug.logError("no thread true", "Module");
            }
        });

        labelDetailPanel.btnPrintShelfLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i = 0;
                int count = labelDetailPanel.panelSelectedTable.productCompositePrintLabelTableModel.getRowCount();
                while (i < count) {
                    try {
                        ProductCompositeLabelPrint elem1 = labelDetailPanel.panelSelectedTable.productCompositePrintLabelTableModel.getRowData(i++);

                        ProductCompositeLabelPrint elem2 = null;
                        if (i < count) {;
                            elem2 = labelDetailPanel.panelSelectedTable.productCompositePrintLabelTableModel.getRowData(i++);
                        }
                        printLabel(elem1, elem2);
                    } catch (Exception ex) {
                        Logger.getLogger(PanelProductLabelPrint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
// TODO add your handling code here:
            }
        });

        labelDetailPanel.printLabel.setEnabled(false);

        /*.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         String productName = labelDetailPanel.txtProductName.getText();
         String barCode = labelDetailPanel.txtBarCode.getText();
         //            if (txtBarCode.getText().length() == 12) {
         //                barCode = "0" + barCode;
         //            }

         //            BigDecimal qty = new BigDecimal(txtWeight.getText());
         BigDecimal qty = BigDecimal.ZERO;
         if (UtilValidate.isNotEmpty(labelDetailPanel.txtWeight.getText())) {
         qty = new BigDecimal(labelDetailPanel.txtWeight.getText());
         }

         BigDecimal price = BigDecimal.ZERO;
         if (UtilValidate.isNotEmpty(labelDetailPanel.txtPrice.getText())) {
         price = new BigDecimal(labelDetailPanel.txtPrice.getText());
         }
         PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
         org.ofbiz.ordermax.product.CustomProductPanel.printTransperentShelfLabel(productName,
         price, productName, price, new BigDecimal(labelDetailPanel.txtLabelCount.getText()).intValue(), pservice);
         } catch (Exception e2) {
         Debug.logError(e2, module);
         }
         }
         }  );
         */

        labelDetailPanel.btnAddProduct.addActionListener(lookupActionListner);

        buttonPanel.btnSaveFileAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileWriter out = null;
                try {

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");

                    int userSelection = fileChooser.showSaveDialog(panel);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                        out = new FileWriter(fileToSave.getAbsolutePath());
                        defaultTableModelToStream(panel.productCompositePrintLabelTableModel, out);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ProductPrintShelfLabelController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ProductPrintShelfLabelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        buttonPanel.btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileWriter out = null;
                try {

                    out = new FileWriter(defaultFileLocation);
                    defaultTableModelToStream(panel.productCompositePrintLabelTableModel, out);

                } catch (IOException ex) {
                    Logger.getLogger(ProductPrintShelfLabelController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ProductPrintShelfLabelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        buttonPanel.btnLoadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Specify a max invoice csv file to open");
                int returnValue = jfc.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    invoiceCompositeListModel.clear();
                    /*//panel.setDialogField();
                     LoadDataFrom loadDataFrom = new LoadDataFromCSV();
                     Vector<Object> rows = loadDataFrom.loadData(selectedFile.getAbsolutePath());
                     for (Object value : rows) {
                     ProductCompositeLabelPrint label = (ProductCompositeLabelPrint) value;
                     ProductComposite productComp = LoadProductWorker.loadProduct(label.getProductId(), XuiContainer.getSession());
                     label.setProductComposite(productComp);
                     invoiceCompositeListModel.add(label);
                     }
                     panel.setDialogField();
                     */
                    loadProductsFromFile(selectedFile.getAbsolutePath());
                }

            }
        });

        labelDetailPanel.btnUpdateTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelDetailPanel.getDialogFieldFromLabelPrint();
                panel.tableReceiveInv.updateUI();
                //panel.setDialogField();
            }

        });

        labelDetailPanel.btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductCompositeLabelPrint itemToRemove = labelDetailPanel.getProductCompositeLabelPrint();
                if (itemToRemove != null) {
                    invoiceCompositeListModel.remove(itemToRemove);;
                    invoiceCompositeListModel.fireListDataChanged();
                    panel.setDialogField();
                    panel.tableReceiveInv.updateUI();
                }
//                panel.tableReceiveInv.updateUI();                
//                panel.setDialogField();
            }
        });

        labelDetailPanel.btnSaveToDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelDetailPanel.getDialogFieldFromLabelPrint();
                ProductCompositeLabelPrint label = labelDetailPanel.getProductCompositeLabelPrint();
                Product product = labelDetailPanel.getProductCompositeLabelPrint().getProductComposite().getProduct();
                product.setproductName(label.getProductName());
                product.setinternalName(label.getProductName());
                product.setweight(new BigDecimal(label.getWeight()));
                product.setcomments(label.getIngredientList());
                product.setweightUomId(label.getWeightStr());

                /*ProductPriceComposite productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), "LIST_PRICE", ControllerOptions.getSession());
                 productPriceComposite.getProductPrice().setproductId(productComposite.getProduct().getproductId());
                 productPriceComposite.getProductPrice().setprice(new BigDecimal(editListPrice.getText()));
                 productPriceComposite.getProductPrice().setcurrencyUomId(currencyComboModel.getSelectedItem().getuomId());
                 try {
                 productComposite.getProductItemPrice().addProductPrice(productPriceComposite);
                 } catch (Exception ex) {
                 Logger.getLogger(CustomProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
                LoadProductWorker.saveProduct(labelDetailPanel.getProductCompositeLabelPrint().getProductComposite(), controllerOptions.getSession());
            }
        });

        labelDetailPanel.btnBarCodeGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductCreateUniqueScanCode scanCode = new ProductCreateUniqueScanCode(controllerOptions.getSession());
                labelDetailPanel.getProductCompositeLabelPrint().setScanCode(scanCode.generateScanCode(labelDetailPanel.getProductCompositeLabelPrint().getProductId()));
                labelDetailPanel.txtBarCode.setText(labelDetailPanel.getProductCompositeLabelPrint().getScanCode());
//                labelDetailPanel.setDialogFieldFromLabelPrint();
                ProductComposite composite = labelDetailPanel.getProductCompositeLabelPrint().getProductComposite();
                if (composite.getGoodIdentificationList().getSize() == 0) {
                    GoodIdentification goodIdentification = LoadProductWorker.createNewGoodsIdentification(composite.getProduct().getproductId(), "EAN");
                    goodIdentification.setidValue(labelDetailPanel.getProductCompositeLabelPrint().getScanCode());
                    composite.getGoodIdentificationList().add(goodIdentification);
                }

            }
        });

        ProductIdClickAction productIdClickAction = new ProductIdClickAction(this, controllerOptions, ControllerOptions.getDesktopPane(), ControllerOptions.getSession());
        setupPriceCalculationTable();
//        panel.getProductActionTableCellEditor().addActionListener(productIdClickAction);

    }

    public void loadProductsFromFile(String absolutePath) {
        LoadDataFrom loadDataFrom = new LoadDataFromCSV();
        Vector<Object> rows = loadDataFrom.loadData(absolutePath);
        for (Object value : rows) {
            ProductCompositeLabelPrint label = (ProductCompositeLabelPrint) value;
            ProductComposite productComp = LoadProductWorker.loadProduct(label.getProductId(), XuiContainer.getSession());
            label.setProductComposite(productComp);
            invoiceCompositeListModel.add(label);
        }
        panel.setDialogField();
    }

    public static void defaultTableModelToStream(AbstractTableModel dtm,
            Writer out) throws IOException {
        final String LINE_SEP = System.getProperty("line.separator");
        int numCols = dtm.getColumnCount();
        int numRows = dtm.getRowCount();

        // Write headers
        String sep = "";

        for (int i = 0; i < numCols; i++) {
            out.write(sep);
            out.write(dtm.getColumnName(i));
            sep = ",";
        }

        out.write(LINE_SEP);

        for (int r = 0; r < numRows; r++) {
            sep = "";

            for (int c = 0; c < numCols; c++) {
                out.write(sep);
                if (dtm.getValueAt(r, c) != null) {
                    if (dtm.getColumnClass(c).equals(java.util.Date.class)) {
                        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
                        out.write(date1.format(dtm.getValueAt(r, c)));
                    } else {
                        if (dtm.getColumnClass(c).equals(Boolean.class)) {
                            out.write(Boolean.FALSE.toString());
                        } else {
                            out.write(dtm.getValueAt(r, c).toString());
                        }
                    }
                } else {
                    if (dtm.getColumnClass(c).equals(Boolean.class)) {
                        out.write(Boolean.FALSE.toString());
                    }

                }
                sep = ",";
            }

            out.write(LINE_SEP);
        }
    }

    public void loadProducts(List<String> listStr) {

        for (String val : listStr) {
            final String productId = val;
            Debug.logError("loadProducts: " + productId, "Module");
            //GenericValue prod = ControllerOptions.getSession().getDelegator().findOne("Product", UtilMisc.toMap("productId", productId), false);
            ProductComposite productComp = LoadProductWorker.loadProduct(productId, XuiContainer.getSession());
            invoiceCompositeListModel.add(createProductCompositeLabelPrint(productComp));

        }
        Debug.logError("loadProducts: " + "done", "Module");
        java.awt.Dimension dim = panel.tableReceiveInv.getPreferredSize();
        dim.width = 1500;
        panel.tableReceiveInv.setPreferredSize(new java.awt.Dimension(dim.width, (invoiceCompositeListModel.getList().size() + 20) * panel.tableReceiveInv.getRowHeight()));

    }

    protected ProductCompositeLabelPrint createProductCompositeLabelPrint(ProductComposite productComposite) {
        Debug.logError("createProductCompositeLabelPrint: ", "Module");
        ProductCompositeLabelPrint label = new ProductCompositeLabelPrint();
        label.setProductComposite(productComposite);

        Product product = productComposite.getProduct();
        label.setProductId(product.getproductId());
        label.setProductName(product.getproductName());
        label.setIngredientList(product.getcomments());

        try {

            if (UtilValidate.isNotEmpty(product.getweight())) {

                label.setWeight(product.getweight().doubleValue());
            }
            String str = new String();
            if (product.getweightUomId() != null) {
                str = UomWeightSingleton.getUom(product.getweightUomId()).getabbreviation();
            }
            label.setWeightStr(str);
        } catch (Exception ex) {
            Logger.getLogger(PanelProductLabelPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProductPriceList list = productComposite.getProductItemPrice().getDefaultPrice();//.getList();
        if (UtilValidate.isNotEmpty(list) && list.getSize() > 0) {
            ProductPriceComposite productPriceComposite = (ProductPriceComposite) list.getElementAt(0);
            //label.setPriceLiverpool(new Double(OrderMaxUtility.getValidBigDecimal(productPriceComposite.getProductPrice().getprice())));
            //label.setPriceRootyHill(new Double(OrderMaxUtility.getValidBigDecimal(productPriceComposite.getProductPrice().getprice())));
            label.setPrice(new Double(OrderMaxUtility.getValidBigDecimal(productPriceComposite.getProductPrice().getprice())));
        }

        GoodIdentificationList identificationList = productComposite.getGoodIdentificationList();
        for (GoodIdentification gf : identificationList.getList()) {
            if (gf.getgoodIdentificationTypeId().equals("EAN")) {
                label.setScanCode(gf.getidValue());
                break;
            }
        }

        /*        txtBarCode.setText(editScanCode.getText());
         txtPrice.setValue(new BigDecimal(editListPrice.getText()));
         txtWeight.setValue(new BigDecimal(txtItemWeight.getText()));
         txtWeightUom.setText(weightUomIdCombo.getSelectedValueDesc());
         txtIngrident.setText(txtAreaDescription.getText());
         */
        int shelfLife = 720;

        BigDecimal millis = BigDecimal.ZERO;
        try {
            millis = BigDecimal.valueOf(shelfLife);
        } catch (Exception e) {
            millis = BigDecimal.valueOf(shelfLife);
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
        label.setPackingDate(new java.util.Date(cal.getTimeInMillis()));
        label.setExpireDate(new java.util.Date(cal.getTimeInMillis() + millis.longValue()));
      //  String lang = null;
        //  final Locale locale = getLocale(lang);
        //  DatePicker dp = new DatePicker((ObservingTextField) txtPackDate, locale);
        ///  Calendar cal = Calendar.getInstance();       // get calendar instance
        //  cal.setTime(startDate);                           // set cal to date

        //  txtPackDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(startDate));
        //  txtUseByDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(endDate));
        return label;
    }

    public class InteractiveTableModelListener implements TableModelListener {

        JTable table;
        AbstractTableModel model;

        public InteractiveTableModelListener(JTable table, AbstractTableModel model) {
            this.table = table;
            this.model = model;
        }

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                if (column + 1 < model.getColumnCount()) {
                    table.setColumnSelectionInterval(column + 1, column + 1);
                }
                if (model.getRowCount() != 0) {
                    table.setRowSelectionInterval(row, row);
                }
//                setTotals();
            } else if (evt.getType() == TableModelEvent.INSERT) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                if (column + 1 < model.getColumnCount()) {
                    table.setColumnSelectionInterval(column + 1, column + 1);
                }
                table.setRowSelectionInterval(row, row);
//                setTotals();
            }
        }
    }

    final public void setupPriceCalculationTable() {

        panel.productCompositePrintLabelTableModel.addTableModelListener(new InteractiveTableModelListener(panel.tableReceiveInv, panel.productCompositePrintLabelTableModel));
        panel.tableReceiveInv.getSelectionModel().addListSelectionListener(new SharedListSelectionHandler());
        panel.tableReceiveInv.setSurrendersFocusOnKeystroke(true);

        for (int i = 1; i < ProductCompositePrintLabelTableModel.Columns.values().length; i++) {
            ProductCompositePrintLabelTableModel.Columns[] columns = ProductCompositePrintLabelTableModel.Columns.values();
            ProductCompositePrintLabelTableModel.Columns column = columns[i];
            TableColumn col = panel.tableReceiveInv.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

        /*        TableColumn hidden = panel.tableReceiveInv.getColumnModel().getColumn(ProductCompositePrintLabelTableModel.Columns.HIDDEN_INDEX.getColumnIndex());
         hidden.setMinWidth(2);
         hidden.setPreferredWidth(2);
         hidden.setMaxWidth(2);
         */
        // interactiveRenderer = new InteractiveRenderer(ImportPriceEntryTableModel.Columns.HIDDEN_INDEX.getColumnIndex());
        //hidden.setCellRenderer(interactiveRenderer);
        panel.tableReceiveInv.setSelectAllForEdit(true);
        panel.tableReceiveInv.setSurrendersFocusOnKeystroke(true);
        panel.tableReceiveInv.setRowHeight(20);
        panel.tableReceiveInv.setSelectionBackground(Color.WHITE);
        panel.tableReceiveInv.setSelectionForeground(Color.BLACK);
        panel.tableReceiveInv.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panel.tableReceiveInv.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

    }

    class SharedListSelectionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            int firstIndex = e.getFirstIndex();
            int lastIndex = e.getLastIndex();
            boolean isAdjusting = e.getValueIsAdjusting();

            if (lsm.isSelectionEmpty()) {
//                print(" <none>");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        /*                        selectedProductName = jTable1.getModel().getValueAt(i, 3).toString();
                         selectedProductImagePath = jTable1.getModel().getValueAt(i, 4).toString();
                         txtCategoryName.setText(jTable1.getModel().getValueAt(i, 7).toString());
                         txtProductName.setText(selectedProductName);

                         imageLabel.setIcon(BaseHelper.getImage(selectedProductImagePath));
                         print(jTable1.getModel().getValueAt(i, 3).toString() + " " + i);
                         */
                        getRowData(i);
                    }
                }
            }
//            print(newline);
//            output.setCaretPosition(output.getDocument().getLength());
        }
    }

    void printLabel(ProductCompositeLabelPrint elem1, ProductCompositeLabelPrint elem2) {
        try {
            String productName1 = elem1.getProductName();
            BigDecimal price1 = BigDecimal.ZERO;
            String barCode1 = elem1.getScanCode();
            if (UtilValidate.isNotEmpty(elem1.getPrice())) {
                double roundOff = Math.round((elem1.getPrice() * 100.00)/100.00);
                price1 = new BigDecimal(elem1.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);// new BigDecimal(roundOff);
            }

            String productName2 = "";//elem1.getProductName();
            BigDecimal price2 = BigDecimal.ZERO;
            String barCode2 = "";
            if (elem2 != null) {
                productName2 = elem2.getProductName();
                if (UtilValidate.isNotEmpty(elem2.getPrice())) {
               // double roundOff = Math.round((elem2.getPrice() * 100.00)/100.00);                    
                    price2 = new BigDecimal(elem2.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    barCode2 = elem2.getScanCode();                    
                }
            }
       System.out.println(price1);
              System.out.println(price2);
            PrintService pservice = PrintServiceLookup.lookupDefaultPrintService();
            org.ofbiz.ordermax.product.CustomProductPanel.printTransperentShelfLabel(productName1,
                    price1,barCode1, productName2, price2, barCode2, new BigDecimal(1).intValue(), pservice);
        } catch (Exception e) {
            Debug.logError(e, module);
        }
    }

    ProductCompositeLabelPrint rowData = null;

    public void getRowData(int i) {
        try {
            rowData = panel.productCompositePrintLabelTableModel.getRowData(i);
            setFormData();
        } catch (Exception ex) {
            Logger.getLogger(ProductCompositeLabelPrint.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("row selectd  : " + i + " column: don't care");
        }
        System.out.println("row selectd  : " + i + " column: don't care");
    }

    void setFormData() {
        if (rowData != null) {
            labelDetailPanel.setProductCompositeLabelPrint(rowData);
            labelDetailPanel.setDialogFieldFromLabelPrint();
        }
    }

    @Override
    public String getProductId() {
        return new String();//panel.getTxtProdIdTableTextField().getText();

    }

    @Override
    public String getPartyId() {
        return panel.getTxtPartyIdTableTextField().getText();
    }

    @Override
    public String getClassName() {
        return module;
    }

}
