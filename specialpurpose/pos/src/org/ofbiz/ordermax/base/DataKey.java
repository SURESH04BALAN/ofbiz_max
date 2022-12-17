/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

/**
 *
 * @author administrator
 */
public class DataKey extends ComboKey {

    public DataKey(String id, String name) {
        super(id, name);
    }

    public DataKey(String id, String name, int in) {
        super(id, name, in);

    }

    public DataKey(String id, String name, String in) {
        super(id, name, 0);
        data = in;
    }

    @Override
    public String toString() {
        return _name;
    }
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
