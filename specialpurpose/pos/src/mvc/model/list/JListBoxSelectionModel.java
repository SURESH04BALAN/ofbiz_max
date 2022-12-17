/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

/**
 *
 * @author siranjeev
 */
public class JListBoxSelectionModel<E> {

    final public  ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
    final public JList<E> jlistBox = new JList<E>(dataListModel);
    final public ListSelectionModel selectionModel = new DefaultListSelectionModel();
    final public ListModelSelection<E> listModelSelection = new ListModelSelection<E>();

    public JListBoxSelectionModel(ListCellRenderer<E> render) {

        jlistBox.setSelectionModel(selectionModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModelSelection.setListModels(dataListModel, selectionModel);
        jlistBox.setCellRenderer(render);
        jlistBox.setEnabled(true);
    }
}
