/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.ofbiz.entity.GenericValue;
import static org.ofbiz.ordermax.utility.GenericValueComboBox.ALL;
import org.ofbiz.pos.generic.ComboKey;

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
    static public String All = "All";
    static public String AllId = "All";

    public GenericValueComboBox(JComboBox cBox, List<GenericValue> genVal, String entityName, String id, String desc) {
        this.listValue = genVal;
        this.entityName = entityName;
        this.id = id;
        this.desc = desc;
        this.comboBox = cBox;
        loadCombo(false);
    }

    public GenericValueComboBox(JComboBox cBox, List<GenericValue> genVal, String entityName, String id, String desc, boolean addAll) {
        this.listValue = genVal;
        this.entityName = entityName;
        this.id = id;
        this.desc = desc;
        this.comboBox = cBox;
        loadCombo(addAll);
    }

    public void loadCombo(boolean addAll) {
        if (listValue != null && comboBox != null) {
            int i = 0;
            DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
            if (addAll) {
                ComboKey comboKey = new ComboKey(AllId, All, i++);
                comboModel.addElement(comboKey);
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
   
    public boolean getAllSelected() {
        ComboKey comboKey = (ComboKey) comboBox.getSelectedItem();
        if(comboKey._id.equals(AllId)){
            return true;
        }
        else{
            return false;
        }
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
    
    public boolean isAllSelected() {
//        ComboKey comboKey = (ComboKey) comboBox.getModel().getSelectedItem();
//        ComboKey comboKey = (ComboKey) comboBox.getSelectedItem();

        if (comboBox.getSelectedItem().toString().equalsIgnoreCase(ALL)) {
            return true;
        }
        
        return false;
    }    
}
