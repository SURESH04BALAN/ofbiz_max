/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.awt.Component;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.entity.DisplayNameInterface;

/**
 *
 * @author siranjeev
 */
public class JGenericListBoxSelectionModel<E> {

    final public ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
    final public JList<E> jlistBox = new JList<E>(dataListModel);
    final public ListSelectionModel selectionModel = new DefaultListSelectionModel();
    final public ListModelSelection<E> listModelSelection = new ListModelSelection<E>();

    public JGenericListBoxSelectionModel(List<E> values) {
        dataListModel.addAll(values);
        jlistBox.setSelectionModel(selectionModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModelSelection.setListModels(dataListModel, selectionModel);
        jlistBox.setCellRenderer(new GenericListCellRenderer());

    }

    public JGenericListBoxSelectionModel(DisplayNameInterface.DisplayTypes displayType) {
        jlistBox.setSelectionModel(selectionModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModelSelection.setListModels(dataListModel, selectionModel);
        jlistBox.setCellRenderer(new GenericListCellRenderer(displayType));
    }

    public JGenericListBoxSelectionModel(ListCellRenderer<E> render) {

        jlistBox.setSelectionModel(selectionModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModelSelection.setListModels(dataListModel, selectionModel);
        jlistBox.setCellRenderer(render);
        jlistBox.setEnabled(true);
    }

    public void setSelectedItem(E elem) {
        listModelSelection.setSelection(elem);//.setSelectedItem(elem);
    }

    public E getSelectedItem() {
        E selItem = null;
        if (listModelSelection.getSelection() != null) {
            selItem = (E) listModelSelection.getSelection();
        }
        return selItem;
    }

    private class GenericListCellRenderer extends JLabel implements
            ListCellRenderer<E> {

        /**
         *
         */
        private static final long serialVersionUID = -1614367901813214864L;
        DisplayNameInterface.DisplayTypes showKey;

        public GenericListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
            super();
            setOpaque(true);
            this.showKey = showKey;
        }

        public GenericListCellRenderer() {
            super();
            setOpaque(true);
            this.showKey = XuiContainer.getSession().getComboBoxDisplayFormat();
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list,
                E value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
//        if (-1 < index) {
//          list.setToolTipText(tooltips[index]);
//        }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            if (value instanceof DisplayNameInterface) {
                String toString = toString((DisplayNameInterface) value);
                setText(toString);
            }

            return this;
        }

        private String toString(DisplayNameInterface obj) {
            if (obj == null) {
                return "";
            } else {
                return obj.getdisplayName(showKey);
            }
        }

    }

    public void clear() {
        dataListModel.clear();
        selectionModel.clearSelection();
    }

    public void setDataList(List<E> values) {
        clear();
        dataListModel.addAll(values);
        jlistBox.updateUI();
    }

}
