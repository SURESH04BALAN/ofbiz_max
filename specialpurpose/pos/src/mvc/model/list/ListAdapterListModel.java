/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import org.ofbiz.base.util.Debug;

/**
 * A {@link ListModel} that is based upon a {@link List}.
 *
 * @author RenÃ© Link <a
 * href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 * intersystems.com]</a>
 *
 */
public class ListAdapterListModel<E> extends AbstractListModel<E> {

    /**
     *
     */
    private static final long serialVersionUID = -3348705533107354297L;

    protected List<E> list = new ArrayList<E>();

    public void setList(List<E> list) {
        this.list.clear();
        this.list.addAll(list);
        fireListDataChanged();
    }

    public void clear() {
        this.list.clear();
        fireListDataChanged();
    }

    public void fireListDataChanged() {
        fireContentsChanged(this, 0, Math.max(list.size() - 1, 0));
    }

    public void addAll(List<E> elements) {
        if (this.list.addAll(elements)) {
      //      Debug.logInfo("fire added: " + this.list.size(), "added");
            fireIntervalAdded(elements.size());
        }
    }

    public void add(E element) {
        if (this.list.add(element)) {
            int index = indexOf(element);
            fireIntervalAdded(1);
            if (index < this.list.size() && index > -1) {
                fireIntervalAdded(this, index, index + 1);
            }
        }
    }

    public void add(int row, E element) {
        this.list.add(row, element);

        int index = indexOf(element);
        fireIntervalAdded(1);
        if (index < this.list.size() && index > -1) {
            fireIntervalAdded(this, index, index + 1);
        }
    }
    
   public void addAll(int row,   List<E> elements) {
        if (this.list.addAll(row, elements)) {
            fireIntervalAdded(elements.size());
        }
    }
   public List<E>  subList(int row1,   int row2) {
        return this.list.subList(row1, row2);
    }
   
   
    public void remove(E element) {
        int index = indexOf(element);
        if (this.list.remove(element)) {
            if (index < this.list.size() && index > -1) {
                fireIntervalRemoved(this, index, index + 1);
            }
        }
    }

    public void remove(int index) {
        E element = getElementAt(index);
        if (this.list.remove(element)) {
            if (index < this.list.size() && index > -1) {
                fireIntervalRemoved(this, index, index + 1);
            }
        }
    }    
    protected void fireIntervalAdded(int elementCOunt) {
        int index0 = list.size() - elementCOunt;
        fireIntervalAdded(this, Math.max(0, index0),
                Math.max(0, list.size() - 1));
    }

    /**
     * Returns the list that this {@link ListAdapterListModel} is backed by.
     *
     * @return
     */
    public List<E> getList() {
        return Collections.unmodifiableList(list);
    }

    public int getSize() {
        return list.size();
    }

    public E getElementAt(int index) {
        E elementAt = list.get(index);
        return elementAt;
    }

    public int indexOf(E element) {
        return list.indexOf(element);
    }

}
