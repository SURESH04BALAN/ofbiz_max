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
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.ProductPriceCompositeListCellRenderer;
import mvc.model.table.ProductPriceTableModel;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.composite.ProductPriceComposite;


/**
 *
 * @author siranjeev
 */
public class ProductPriceLoadViewPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4471606875093169644L;

    private ProductPriceTableModel personTableModel = new ProductPriceTableModel();
    private ListAdapterListModel<ProductPriceComposite> personListModel = new ListAdapterListModel<ProductPriceComposite>();
    private ListModelSelection<ProductPriceComposite> listModelSelection = new ListModelSelection<ProductPriceComposite>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private JTable personTable = new JTable(personTableModel);

    private JList<ProductPriceComposite> personList = new JList<ProductPriceComposite>(personListModel);
    private ListComboBoxModel<ProductPriceComposite> personComboBoxModel = new ListComboBoxModel<ProductPriceComposite>();
    private JComboBox<ProductPriceComposite> personsComboBox = new JComboBox<ProductPriceComposite>(personComboBoxModel);
   public final JTextField textEdit = new JTextField();    
    public void setProductPriceList(ListAdapterListModel<ProductPriceComposite> personListModel) {
        personList.setModel(personListModel);
        personTableModel.setListModel(personListModel);
        personComboBoxModel.setListModel(personListModel);
        listModelSelection.setListModels(personListModel, selectionModel);
    }
    
    public JButton jBtnLoad = null;//new JButton("Load");
//    public final JTextField textEdit = new JTextField();    
    public ProductPriceLoadViewPanel() {
        setLayout(new BorderLayout());
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personList.setSelectionModel(selectionModel);
        personTable.setSelectionModel(selectionModel);
        personComboBoxModel.setListSelectionModel(selectionModel);

        ListCellRenderer<ProductPriceComposite> personRenderer = new ProductPriceCompositeListCellRenderer();
        personList.setCellRenderer(personRenderer);
        personList.setEnabled(true);
        personsComboBox.setRenderer(personRenderer);
        personTable.setSelectionModel(personList.getSelectionModel());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 130);
        add(scrollPane, BorderLayout.CENTER);

        scrollPane.setViewportView(personTable);

        personsComboBox.setBounds(10, 153, 580, 30);
        JPanel panel = new JPanel(new GridLayout());
        panel.setBounds(10, 153, 580, 30);
        panel.add(personsComboBox);

        JPanel panelEditor = new JPanel(new BorderLayout());
        JPanel panelButton = new JPanel(new BorderLayout());

        textEdit.setText("C:\\ordermax\\Real_Data\\cost_sales_prices_per_unit.csv");
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
