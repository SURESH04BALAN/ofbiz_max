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
import java.io.File;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import org.ofbiz.ordermax.dataimportexport.loaders.BigFishProduct;
import mvc.model.list.BigFishProductListCellRenderer;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.table.BigFishProductTableModel;
import org.ofbiz.ordermax.base.BaseHelper;

/**
 *
 * @author siranjeev
 */
    public class BigFishProductLoadViewPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4471606875093169644L;

    private BigFishProductTableModel productTableModel = new BigFishProductTableModel();
    private ListAdapterListModel<BigFishProduct> personListModel = new ListAdapterListModel<BigFishProduct>();
    private ListModelSelection<BigFishProduct> listModelSelection = new ListModelSelection<BigFishProduct>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private JTable personTable = new JTable(productTableModel);

    private JList<BigFishProduct> personList = new JList<BigFishProduct>(personListModel);
    private ListComboBoxModel<BigFishProduct> personComboBoxModel = new ListComboBoxModel<BigFishProduct>();
    private JComboBox<BigFishProduct> personsComboBox = new JComboBox<BigFishProduct>(personComboBoxModel);
    public final JTextField textEdit = new JTextField();    
    public void setProductList(ListAdapterListModel<BigFishProduct> bigFishProductListModel) {
        personList.setModel(personListModel);
        productTableModel.setListModel(bigFishProductListModel);
        personComboBoxModel.setListModel(bigFishProductListModel);
        listModelSelection.setListModels(bigFishProductListModel, selectionModel);
    }
    
    public JButton jBtnLoad = null;//new JButton("Load");
    public BigFishProductLoadViewPanel() {
        setLayout(new BorderLayout());
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personList.setSelectionModel(selectionModel);
        personTable.setSelectionModel(selectionModel);
        personComboBoxModel.setListSelectionModel(selectionModel);

        ListCellRenderer<BigFishProduct> personRenderer = new BigFishProductListCellRenderer();
        personList.setCellRenderer(personRenderer);
        personList.setEnabled(true);
        personsComboBox.setRenderer(personRenderer);
        personTable.setSelectionModel(personList.getSelectionModel());

        JScrollPane scrollPane = new JScrollPane(personTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        personTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        scrollPane.setBounds(10, 11, 580, 130);
        add(scrollPane, BorderLayout.CENTER);

//        scrollPane.setViewportView(personTable);

        personsComboBox.setBounds(10, 153, 580, 30);
        JPanel panel = new JPanel(new GridLayout());
        panel.setBounds(10, 153, 580, 30);
        panel.add(personsComboBox);

        JPanel panelEditor = new JPanel(new BorderLayout());
        JPanel panelButton = new JPanel(new BorderLayout());
//        final JTextField textEdit = new JTextField();
        textEdit.setText("C:\\ordermax\\Real_Data\\ProductImport_copy_sorted_product_code_fnal_latest.csv");
//        JPanel panel = new JPanel(new BorderLayout());        
        jBtnLoad = new JButton("Load");
        JButton jBtnDir = new JButton("Dir");
        jBtnDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File filePath = BaseHelper.getImageFilePath("original");
                    textEdit.setText(filePath.getPath());
                } catch (Exception ex) {
                }
//                loadProductTree();
            }
        });

        panelButton.add(BorderLayout.EAST, jBtnLoad);
        panelButton.add(BorderLayout.CENTER, jBtnDir);

        panelEditor.add(BorderLayout.CENTER, textEdit);
//        JPanel panelEditor = new JPanel(new BorderLayout());
        panelEditor.add(BorderLayout.EAST, panelButton);

        panel.add(panelEditor);
        add(panel, BorderLayout.NORTH);
    }
}
