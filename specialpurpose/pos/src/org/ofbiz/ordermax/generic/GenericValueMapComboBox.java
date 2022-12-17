/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ComboKey;

/**
 *
 * @author administrator
 */
public final class GenericValueMapComboBox {

    private Map<String, String> listValue = null;
    private JComboBox comboBox = null;
    public static String all = "All";

    public GenericValueMapComboBox(JComboBox cBox, Map<String, String> genVal) {
        this.listValue = genVal;
        this.comboBox = cBox;
        loadCombo();
    }

    public void loadCombo() {
        if (listValue != null && comboBox != null) {
            int i = 0;
            DefaultComboBoxModel comboModel = new DefaultComboBoxModel();

            ComboKey comboKeyAll = new ComboKey(all, all, i++);
            comboModel.addElement(comboKeyAll);

            for (Map.Entry<String, String> entryVal : listValue.entrySet()) {
                String idVal = entryVal.getKey();
                String name = entryVal.getValue();
                if (idVal != null && name != null) {
                    ComboKey comboKey = new ComboKey(idVal, name, i++);
                    comboModel.addElement(comboKey);
                }
            }
            comboBox.setModel(comboModel);
        }
    }

    public Map<String, String> getListValue() {
        return listValue;
    }

    public void setListValue(Map<String, String> listValue) {
        this.listValue = listValue;
    }

    public String getSelectedItemId() {
//        Debug.logInfo("getSelectedItemId() node name: " + comboBox.getSelectedItem().toString(), "module"); 
        ComboKey comboKey = (ComboKey) comboBox.getSelectedItem();
        return comboKey._id;
    }

    public void setSelectedItemId(String id) {
        for (int i = 0; i < comboBox.getItemCount(); ++i) {
            ComboKey comboKey = (ComboKey) comboBox.getItemAt(i);
            //Debug.logInfo("setParentItem node name: " + comboKey._id + " my id: " + id, "module"); 
            if (comboKey._id.equals(id)) {
                comboBox.setSelectedIndex(i);
                //Debug.logInfo("comboBox.setSelectedIndex(i);: " + i, "module");                 
                break;
            }
        }
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
    
     public void checkItem() {
        for (int i = 0; i < comboBox.getItemCount(); ++i) {
            try{
            Object key = (Object) comboBox.getItemAt(i);                
            System.out.println("string: " + key.toString());                
            ComboKey comboKey = (ComboKey) comboBox.getItemAt(i);
            System.out.println("comboKey: " + comboKey.toString());      
            }
            catch(Exception ex){
                System.out.println("product sel id: " + ex.getMessage());                
            }
        }
    }
}
