/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.ofbiz.base.util.Debug;

/**
 * A {@link ListComboBoxModel} is an adapter that combines a {@link ListModel}
 * and a {@link ListSelectionModel} to a {@link ComboBoxModel}. The
 * {@link ListSelectionModel} that this {@link ListComboBoxModel} uses should be
 * set to {@link ListSelectionModel#SINGLE_SELECTION} selection mode, because a
 * {@link ComboBoxModel} can only have one selected item. Otherwise the first
 * selected item will be the combo box selection.
 *
 * @author RenÃ© Link <a
 * href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 * intersystems.com]</a>
 *
 */
public class ListComboBoxModel<E> extends AbstractListModel<E> implements
        ComboBoxModel<E> {

    /**
     *
     */
    private static final long serialVersionUID = -5099034646018127351L;
    private ListModel<E> listModel = new DefaultListModel<E>();
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private ListSelectionUpdater listSelectionUpdater = new ListSelectionUpdater();
    private ListModelUpdater listModelUpdater = new ListModelUpdater();

    public void setListSelectionModel(ListSelectionModel listSelectionModel) {
        if (this.listSelectionModel != null) {
            this.listSelectionModel
                    .removeListSelectionListener(listSelectionUpdater);
        }
        this.listSelectionModel = listSelectionModel;
        if (listSelectionModel != null) {
            listSelectionModel.addListSelectionListener(listSelectionUpdater);
        }
    }

    public void setListModel(ListModel<E> listModel) {
        if (this.listModel != null) {
            this.listModel.removeListDataListener(listModelUpdater);
        }
        this.listModel = listModel;
        if (listModel != null) {
            listModel.removeListDataListener(listModelUpdater);
        }
    }

    public E getElementAt(int index) {
        return listModel.getElementAt(index);
    }

    public int getSize() {
        return listModel.getSize();
    }

    public void setSelectedItem(Object anItem) {
        int index = -1;
        for (int i = 0; i < getSize(); i++) {
            Object elementAt = listModel.getElementAt(i);
            //Debug.log("Find Combo elementAt: " + elementAt.toString() + " - i: " +  i + " anItem:" + anItem);
            if (elementAt != null && elementAt.equals(anItem)) {
                index = i;
                //Debug.log("Found Combo : " + anItem.toString() + " index: " + index);
                break;
            }
        }
        /* if(index < 0){
         Debug.log("Unable Find Combo : " + anItem.toString() + " index: " + index);
         try{
         //                throw new Exception("Unable Find Combo : " + anItem.toString() + " index: " + index);
         }
         catch(Exception e){
         Debug.logError(e, "module");
         }*/
        //  }
        listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel.setSelectionInterval(index, index);
    }

    public E getSelectedItem() {
        int minSelectionIndex = listSelectionModel.getMinSelectionIndex();
        if (minSelectionIndex < 0) {
            return null;
        }
        E elementAt = getElementAt(minSelectionIndex);
        return elementAt;
    }

    public int getSelectedItemIndex(Object anItem) {
        int index = -1;
        for (int i = 0; i < getSize(); i++) {
            Object elementAt = listModel.getElementAt(i);
//            Debug.log("Find Combo elementAt: " + elementAt.toString() + " - i: " + i);
            if (elementAt != null && elementAt.equals(anItem)) {
                index = i;
                //Debug.log("Found Combo : " + anItem.toString() + " index: " + index);
                break;
            }
        }
        return index;
    }

    private class ListSelectionUpdater implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            fireContentsChanged(this, 0, getSize() - 1);
        }

    }

    private class ListModelUpdater implements ListDataListener {

        public void intervalAdded(ListDataEvent e) {
            fireIntervalAdded(this, e.getIndex0(), e.getIndex1());

        }

        public void intervalRemoved(ListDataEvent e) {
            fireIntervalRemoved(this, e.getIndex0(), e.getIndex1());
        }

        public void contentsChanged(ListDataEvent e) {
            fireContentsChanged(this, e.getIndex0(), e.getIndex1());
        }
    }
}
