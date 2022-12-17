//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.reports.params;

import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.pos.forms.AppView;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.utility.ComponentBorder;
//import com.openbravo.pos.forms.DataLogicSales;

/**
 *
 * @author adrianromero
 */
public class JParamsGenericComboBoxSelection<E> extends javax.swing.JPanel implements ReportEditorCreator {
         public String getEditorClassName(){
        return "JParamsGenericComboBoxSelection";
    }
    //private SentenceList m_sentlocations;
    //private ComboBoxValModel m_LocationsModel;    
     /** Creates new form JParamsClosedPos */
    //org.ofbiz.ordermax.base.components.FacilityPickerComboPanel pickerEditPanel = null;
    JGenericComboBoxSelectionModel<E> model = null;
    
    /** Creates new form JParamsLocation */
    public JParamsGenericComboBoxSelection(String keyId, List<E> values, String paramId, E defaultValue) {
        initComponents();     

        model = new JGenericComboBoxSelectionModel<E>(panelId, values);        
        model.keyId = keyId;     
        model.paramId = paramId; 
        if(defaultValue!=null){
            model.setSelectedItem(defaultValue);
        }
    }

    public void init(AppView app) {
         
    //    DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        
        // El modelo de locales
    //    m_sentlocations = dlSales.getLocationsList();
    //    m_LocationsModel = new ComboBoxValModel();   
    }
        
    public void activate() throws BasicException {
       // List a = m_sentlocations.list();
       // addFirst(a);
  //      m_LocationsModel = new ComboBoxValModel(a);
//        m_LocationsModel.setSelectedFirst();
//        m_jLocation.setModel(m_LocationsModel); // refresh model   
    }
    
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING});
    }

    public Component getComponent() {
        return this;
    }

    
    protected void addFirst(List a) {
        // do nothing
    }
    
    public void addActionListener(ActionListener l) {
       // m_jLocation.addActionListener(l);
    }
    
    public void removeActionListener(ActionListener l) {
      //  m_jLocation.removeActionListener(l);
    }
    
    public Object createValue() throws BasicException {
        
        return null;// new Object[] {
//            m_LocationsModel.getSelectedKey() == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_EQUALS, 
//            m_LocationsModel.getSelectedKey()
//        };
    }    
    
    @Override
    public EntityCondition getEntityCondition() {
        return model.getEntityCondition();
    }
        @Override
    public Map<String, Object> getValues() throws BasicException {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put(UtilValidate.isNotEmpty(model.paramId) ? model.paramId :  model.keyId, model.getEntityValue());
        return values;
    }   
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelCaption = new javax.swing.JLabel();
        panelId = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(AppLocal.getIntString("label.bywarehouse"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(200, 50));
        setPreferredSize(new java.awt.Dimension(200, 60));
        setLayout(null);

        labelCaption.setText(AppLocal.getIntString("label.warehouse")); // NOI18N
        add(labelCaption);
        labelCaption.setBounds(18, 22, 110, 16);

        panelId.setPreferredSize(new java.awt.Dimension(100, 24));

        javax.swing.GroupLayout panelIdLayout = new javax.swing.GroupLayout(panelId);
        panelId.setLayout(panelIdLayout);
        panelIdLayout.setHorizontalGroup(
            panelIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelIdLayout.setVerticalGroup(
            panelIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        add(panelId);
        panelId.setBounds(140, 22, 100, 24);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel labelCaption;
    private javax.swing.JPanel panelId;
    // End of variables declaration//GEN-END:variables
}
