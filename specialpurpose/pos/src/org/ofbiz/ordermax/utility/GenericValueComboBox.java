/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ComboKey;

/**
 *
 * @author siranjeev
 */
public final class GenericValueComboBox {

    private List<GenericValue> listValue = null;
    private String entityName;
    private String id;
    private String desc;
    private JComboBox comboBox = null;
    private boolean showAll = false;

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }
    public static String ALL = "All";

    public GenericValueComboBox(JComboBox cBox, List<GenericValue> genVal, String entityName, String id, String desc) {
        this.listValue = genVal;
        this.entityName = entityName;
        this.id = id;
        this.desc = desc;
        this.comboBox = cBox;
        loadCombo();
    }

    public GenericValueComboBox(JComboBox cBox, List<GenericValue> genVal, String entityName, String id, String desc, boolean showAll) {
        this.listValue = genVal;
        this.entityName = entityName;
        this.id = id;
        this.desc = desc;
        this.comboBox = cBox;
        this.showAll = showAll;

        loadCombo();
    }

    public boolean isAllSelected() {
//        ComboKey comboKey = (ComboKey) comboBox.getModel().getSelectedItem();
//        ComboKey comboKey = (ComboKey) comboBox.getSelectedItem();

        if (comboBox.getSelectedItem().toString().equalsIgnoreCase(ALL)) {
            return true;
        }
        
        return false;
    }

    public void loadCombo() {
        if (listValue != null && comboBox != null) {

            int i = 0;
            DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
            ComboKey comboKeyAll = null;
            if (showAll) {
                 comboKeyAll = new ComboKey(ALL, ALL, -1);
                comboModel.addElement(comboKeyAll);
            }

            for (GenericValue gVal : listValue) {
                String idVal = gVal.getString(id);
                String name = gVal.getString(desc);
                if (idVal != null && name != null) {
                    ComboKey comboKey = new ComboKey(idVal, name, i++);
                    comboModel.addElement(comboKey);
                }
            }

            comboBox.setModel(comboModel);
            if (showAll) {
                comboModel.setSelectedItem(ALL);
            }
        }
    }

    public List<GenericValue> getListValue() {
        return listValue;
    }

    public void setListValue(List<GenericValue> listValue) {
        this.listValue = listValue;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getGenericId() {
        return id;
    }

    public void setGenericId(String id) {
        this.id = id;
    }

    public String getGenericDesc() {
        return desc;
    }

    public void setGenericDesc(String desc) {
        this.desc = desc;
    }

    public String getSelectedItemId() {
        ComboKey comboKey = (ComboKey) comboBox.getSelectedItem();
        return comboKey._id;
    }

    public void setSelectedItemId(String id) {
        for (int i = 0; i < comboBox.getItemCount(); ++i) {
            ComboKey comboKey = (ComboKey) comboBox.getItemAt(i);
            if (comboKey._id.equals(id)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public String getSelectedValueDesc() {
        return comboBox.getSelectedItem().toString();
    }

    public String getSelectedItemDesc() {
        return desc;
    }

    public void setSelectedItemDesc(String desc) {
        this.desc = desc;
    }

    static public void setSelectedItemId(JComboBox cBox, String id) {
        for (int i = 0; i < cBox.getItemCount(); ++i) {
            ComboKey comboKey = (ComboKey) cBox.getItemAt(i);
            if (comboKey._id.equals(id)) {
                cBox.setSelectedIndex(i);
                break;
            }
        }
    }
}
