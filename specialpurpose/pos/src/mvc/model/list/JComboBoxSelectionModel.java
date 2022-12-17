/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import org.ofbiz.ordermax.entity.DisplayNameInterface;

/**
 *
 * @author siranjeev
 */
public class JComboBoxSelectionModel<E> {

    public ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
    public ListComboBoxModel<E> comboBoxModel = new ListComboBoxModel<E>();
    final public ListSelectionModel selectionModel = new DefaultListSelectionModel();
//    final public ListModelSelection<E> listModelSelection = new ListModelSelection<E>();
    public JComboBox<E> jComboBox = new JComboBox<E>(comboBoxModel);
    public AutoCompletion autoCompletion = null;
    public JComboBoxSelectionModel(List<E> values, ListCellRenderer<E> render, DisplayNameInterface.DisplayTypes displayType) {
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //      listModelSelection.setListModels(dataListModel, selectionModel);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(render);
//        if (E instanceof DisplayNameInterface) {
        autoCompletion = AutoCompletion.enable(jComboBox, displayType);
//        }
    }

    public JComboBoxSelectionModel(List<E> values, ListCellRenderer<E> render) {
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //      listModelSelection.setListModels(dataListModel, selectionModel);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(render);
//        if (E instanceof DisplayNameInterface) {
        autoCompletion = AutoCompletion.enable(jComboBox);
//        }
    }

    public JComboBoxSelectionModel(ListCellRenderer<E> render) {
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //    listModelSelection.setListModels(dataListModel, selectionModel);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(render);
    }
    
    public void setSelectedItem(E elem) {
        comboBoxModel.setSelectedItem(elem);
    }

    public E getSelectedItem() {
        E selItem = null;
        if (comboBoxModel.getSelectedItem() != null) {
            selItem = (E) comboBoxModel.getSelectedItem();
        }
        return selItem;
    }
    public void setDataList(List<E> values) {
        dataListModel.clear();        
        dataListModel.addAll(values);
    }
}
