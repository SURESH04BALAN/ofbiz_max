/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import mvc.controller.LoadProductWorker;
import mvc.controller.dataload.posdata.PosSalesData;
import mvc.controller.dataload.posdata.PosSalesDataListCellRenderer;
import mvc.controller.dataload.posdata.PosSalesDataTableModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Product;

/**
 *
 * @author siranjeev
 */
public class FixImageLoadViewPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4471606875093169644L;
    public static final String module = FixImageLoadViewPanel.class.getName();
    private PosSalesDataTableModel productTableModel = new PosSalesDataTableModel();
    private ListAdapterListModel<PosSalesData> personListModel = new ListAdapterListModel<PosSalesData>();
    private ListModelSelection<PosSalesData> listModelSelection = new ListModelSelection<PosSalesData>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private JTable personTable = new JTable(productTableModel);

    private JList<PosSalesData> personList = new JList<PosSalesData>(personListModel);
    private ListComboBoxModel<PosSalesData> personComboBoxModel = new ListComboBoxModel<PosSalesData>();
    //  private JComboBox<PosSalesData> personsComboBox = new JComboBox<PosSalesData>(personComboBoxModel);
    public final JTextField srcEdit = new JTextField();
    public final JTextField destEdit = new JTextField();

    public void setProductList(ListAdapterListModel<PosSalesData> bigFishProductListModel) {
        personList.setModel(personListModel);
        productTableModel.setListModel(bigFishProductListModel);
        personComboBoxModel.setListModel(bigFishProductListModel);
        listModelSelection.setListModels(bigFishProductListModel, selectionModel);
    }

    public JButton jBtnLoadFile = null;//new JButton("Load");
    public JButton jBtnLoadDir = null;//new JButton("Load");    
    public JButton jBtnLoadOldOfBizFilesDir = null;//new JButton("Load");       
    public JButton jBtnSourceDir = null;//new JButton("Load");          
    public JButton jBtnDistDir = null;//new JButton("Load");      
    //  public JButton jBtnPrint = null;//new JButton("Load");

    public FixImageLoadViewPanel() {
        setLayout(new BorderLayout());
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personList.setSelectionModel(selectionModel);
        personTable.setSelectionModel(selectionModel);
        personComboBoxModel.setListSelectionModel(selectionModel);

        ListCellRenderer<PosSalesData> personRenderer = new PosSalesDataListCellRenderer();
        personList.setCellRenderer(personRenderer);
        personList.setEnabled(true);
        //personsComboBox.setRenderer(personRenderer);
        personTable.setSelectionModel(personList.getSelectionModel());

        JScrollPane scrollPane = new JScrollPane(personTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        personTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setBounds(10, 11, 580, 130);
        add(scrollPane, BorderLayout.CENTER);

//        scrollPane.setViewportView(personTable);
//        personsComboBox.setBounds(10, 153, 580, 30);
//        JPanel panel = new JPanel(new GridLayout());
        //       panel.setBounds(10, 153, 580, 30);
//        panel.add(personsComboBox);
        JPanel panelSrcEditor = new JPanel(new BorderLayout());
        //JPanel panelSrcButton = new JPanel(new BorderLayout());
        JPanel panelDestEditor = new JPanel(new BorderLayout());
        JPanel panelDestButton = new JPanel(new BorderLayout());
//        final JTextField textEdit = new JTextField();
        srcEdit.setText("C:\\ofbiz_web\\Max_Spice\\Max_Spice\\themes\\grocery_theme\\webapp\\grocery_theme\\images\\catalog\\grocery\\PLP");
        destEdit.setText("C:\\ofbiz_web\\Max_Spice\\Max_Spice\\themes\\grocery_theme\\webapp\\grocery_theme\\images\\catalog\\grocery\\update_plp");
//        JPanel panel = new JPanel(new BorderLayout());        
        jBtnSourceDir = new JButton("Source Dir");
        jBtnSourceDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File filePath = BaseHelper.getImageFilePath("original");
                    srcEdit.setText(filePath.getPath());
                } catch (Exception ex) {
                }
//                loadProductTree();
            }
        });

        jBtnDistDir = new JButton("Distination Dir");
        jBtnDistDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File filePath = BaseHelper.getDirPath("original");
                    destEdit.setText(filePath.getPath());
                } catch (Exception ex) {
                }
//                loadProductTree();
            }
        });

        panelSrcEditor.add(BorderLayout.WEST, jBtnSourceDir);
        panelSrcEditor.add(BorderLayout.CENTER, srcEdit);

        jBtnLoadFile = new JButton("Load File");
        jBtnLoadDir = new JButton("Load Dir");
        jBtnLoadOldOfBizFilesDir = new JButton("Copy ofbiz images");
        panelDestButton.add(BorderLayout.WEST, jBtnLoadFile);
        panelDestButton.add(BorderLayout.CENTER, jBtnLoadDir);
        panelDestButton.add(BorderLayout.EAST, jBtnLoadOldOfBizFilesDir);

        panelDestEditor.add(BorderLayout.WEST, jBtnDistDir);
        panelDestEditor.add(BorderLayout.CENTER, destEdit);
        panelDestEditor.add(BorderLayout.EAST, panelDestButton);

        JPanel panel = new JPanel(new GridLayout());
        panel.setBounds(10, 153, 580, 30);
        panel.add(panelSrcEditor, BorderLayout.WEST);
        panel.add(panelDestEditor, BorderLayout.CENTER);

        add(panel, BorderLayout.NORTH);
    }

    public synchronized void processFile() {
        if (UtilValidate.isNotEmpty(srcEdit.getText()) && UtilValidate.isNotEmpty(destEdit.getText())) {
            File fileName = new File(srcEdit.getText());
            if (UtilValidate.isNotEmpty(fileName.getName())) {
                Debug.logInfo("src processFile File Name: " + fileName.getName(), module);
                Debug.logInfo("src processFile Path: " + fileName.getPath(), module);
                File dest = new File(destEdit.getText());
                Debug.logInfo("dest processFile Path: " + dest.getName(), module);
                Debug.logInfo("dest processFile Path: " + dest.getPath(), module);
                Debug.logInfo("new dest processFile Path: " + dest.getPath() + "\\" + fileName.getName(), module);
                String newDestination = dest.getPath() + "\\" + fileName.getName();
                String src = srcEdit.getText();
                BaseHelper.autoCropAndResizeImage(src, newDestination, 180, 240, 0.05);
            } else {
                OrderMaxOptionPane.showMessageDialog(this, "Invalid file name");
            }
        }
    }

    public synchronized void processDir() {
        Debug.logInfo("processDir", module);

        if (UtilValidate.isNotEmpty(srcEdit.getText()) && UtilValidate.isNotEmpty(destEdit.getText())) {
            List<File> files = BaseHelper.getFiles(srcEdit.getText());
            for (File file : files) {
                //File dir = new File(srcEdit.getText());
                if (UtilValidate.isNotEmpty(file.getName())) {
                    //Debug.logInfo("src processFile File Name: " + file.getName(), module);
                    Debug.logInfo("src processFile Path: " + file.getAbsolutePath(), module);
                    File destDir = new File(destEdit.getText());
                    Debug.logInfo("dest processFile Path: " + destDir.getName(), module);
                    Debug.logInfo("dest processFile Path: " + destDir.getPath(), module);
                    Debug.logInfo("new dest processFile Path: " + destDir.getPath() + "\\" + file.getName(), module);
                    int pos = file.getName().indexOf(".");
                    String fName = file.getName();
                    if (pos > -1) {
                        fName = file.getName().substring(0, pos);
                    }
                    String newDestination = destDir.getPath() + "\\ofbiz\\plp\\" + fName + "_plp.jpg";
                    String src = file.getAbsolutePath();
                    BaseHelper.autoCropAndResizeImage(src, newDestination, 180, 240, 0.05);

                    //virtual
                    newDestination = destDir.getPath() + "\\ofbiz\\plp\\" + fName + "V_plp.jpg";
                    BaseHelper.autoCropAndResizeImage(src, newDestination, 180, 240, 0.05);

                    newDestination = destDir.getPath() + "\\ofbiz\\PDP\\" + fName + "_pdp.jpg";
                    BaseHelper.autoCropAndResizeImage(src, newDestination, 250, 334, 0.05);
                } else {
                    OrderMaxOptionPane.showMessageDialog(this, "Invalid file name");
                }
            }
        }
    }

    public synchronized void setNoImageAvaliable() {

        List<EntityCondition> entityConditionList = new ArrayList<EntityCondition>();
        List<GenericValue> list = PosProductHelper.getReadOnlyGenericValueListsWithSelection("Product", entityConditionList, "productId", ControllerOptions.getSession().getDelegator());
        //LoadProductWorker.loadProducts(inputFields, ControllerOptions.getSession());
        try {
            // public static void main(String[] args) throws Exception {

            final BufferedImage image = ImageIO.read(new File("C:\\josh\\no_image.jpg"));
            for (GenericValue prod : list) {
                ImageIO.write(image, "jpg", new File("C:\\josh\\ofbiz\\no_image\\" + prod.getString("productId") + ".jpg"));
            }
        } catch (IOException ex) {

        }

    }

    public synchronized void processOldOfbizFiles() {
        Debug.logInfo("processOldOfbizFiles", module);

        if (UtilValidate.isNotEmpty(srcEdit.getText()) && UtilValidate.isNotEmpty(destEdit.getText())) {
            List<File> files = BaseHelper.getSubDirectories(srcEdit.getText());
            for (File file : files) {
                //File dir = new File(srcEdit.getText());
                if (UtilValidate.isNotEmpty(file.getName())) {
                    File dest = new File(destEdit.getText());
                    String newDestination = dest.getPath() + "\\ofbiz\\plp\\" + file.getName() + "_plp.jpg";
                    String src = file.getAbsolutePath() + "\\original.jpg";

                    //Debug.logInfo("src processFile File Name: " + file.getName(), module);
                    Debug.logInfo("src processFile Path: " + src, module);

                    //Debug.logInfo("dest processFile Path: " + dest.getName(), module);
                    Debug.logInfo("dest processFile Path: " + newDestination, module);
                    //Debug.logInfo("new dest processFile Path: " + dest.getPath() + "\\" + file.getName(), module);

                    BaseHelper.autoCropAndResizeImage(src, newDestination, 180, 240, 0.05);
                    //virtual
                    newDestination = dest.getPath() + "\\ofbiz\\plp\\" + file.getName() + "V_plp.jpg";
                    BaseHelper.autoCropAndResizeImage(src, newDestination, 180, 240, 0.05);

                    newDestination = dest.getPath() + "\\ofbiz\\PDP\\" + file.getName() + "_pdp.jpg";
                    BaseHelper.autoCropAndResizeImage(src, newDestination, 250, 334, 0.05);
                } else {
                    OrderMaxOptionPane.showMessageDialog(this, "Invalid file name");
                }
            }
        }
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = ControllerOptions.getSession().getClassLoader();
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

}
