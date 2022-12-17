/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

/**
 *
 * @author siranjeev
 */
public class ComboKey extends Key {
    public ComboKey(String id, String name) {
        super(id, name);
    }

    public ComboKey(String id, String name, int in) {
        super(id, name);
        index = in;
    }

    @Override
    public String toString() {
        return _name;
    }
    
    private int index = 0;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
}
