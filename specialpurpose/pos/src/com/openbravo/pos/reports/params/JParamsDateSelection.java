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

import com.openbravo.pos.forms.AppView;
import java.awt.Component;
import java.util.Date;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.format.Formats;
import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.ordermax.base.BaseHelper;

public class JParamsDateSelection extends javax.swing.JPanel implements ReportEditorCreator {
     public String getEditorClassName(){
        return "JParamsDateSelection";
    }
    final public String startDateId;
    static final public String StartDateName = "startDate";
    static final public String EndDateName = "endDate";

    /**
     * Creates new form JParamsClosedPos
     */
    public JParamsDateSelection(String startDateId) {
        initComponents();
        this.startDateId = startDateId;
    }

    public void setDate(Date d) {
        jTxtStartDate.setText(Formats.TIMESTAMP.formatValue(d));
    }

    public void init(AppView app) {
    }

    public void activate() throws BasicException {
    }

    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(new Datas[]{Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.TIMESTAMP});
    }

    public Component getComponent() {
        return this;
    }

    public Object createValue() throws BasicException {
        Object startdate = Formats.TIMESTAMP.parseValue(jTxtStartDate.getText());

        return new Object[]{
            startdate == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_GREATEROREQUALS,
            startdate
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTxtStartDate = new javax.swing.JTextField();
        btnDateStart = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(AppLocal.getIntString("label.bydates"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(0, 50));
        setLayout(null);

        jLabel1.setText(AppLocal.getIntString("Label.StartDate")); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(20, 20, 120, 14);

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 24));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jTxtStartDate.setMinimumSize(new java.awt.Dimension(6, 25));
        jTxtStartDate.setPreferredSize(new java.awt.Dimension(6, 25));
        jPanel1.add(jTxtStartDate, java.awt.BorderLayout.CENTER);

        btnDateStart.setIcon(new javax.swing.ImageIcon(BaseHelper.getOpenBravoIamgePath("/com/openbravo/images/date.png")));
        btnDateStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateStartActionPerformed(evt);
            }
        });
        jPanel1.add(btnDateStart, java.awt.BorderLayout.EAST);

        add(jPanel1);
        jPanel1.setBounds(145, 20, 274, 24);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDateStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateStartActionPerformed

        Date date;
        try {
            date = (Date) Formats.TIMESTAMP.parseValue(jTxtStartDate.getText());
        } catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTimeHours(this, date);
        if (date != null) {
            jTxtStartDate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }//GEN-LAST:event_btnDateStartActionPerformed

    /*@Override
     public EntityCondition getEntityCondition() {

     try {
     Timestamp fromDate = null;
     Timestamp thruDate = null;

     Object startdate = Formats.TIMESTAMP.parseValue(jTxtStartDate.getText());
     Object enddate = Formats.TIMESTAMP.parseValue(jTxtEndDate.getText());
     if (startdate != null) {
     fromDate = new Timestamp(((java.util.Date) startdate).getTime());
     }

     if (enddate != null) {
     fromDate = new Timestamp(((java.util.Date) enddate).getTime());
     }

     EntityConditionList<EntityExpr> betweenCondition = EntityCondition.makeCondition(
     EntityCondition.makeCondition(startDateId, EntityOperator.GREATER_THAN_EQUAL_TO, fromDate),
     EntityCondition.makeCondition(startDateId, EntityOperator.LESS_THAN, thruDate));
     return EntityCondition.makeCondition(EntityCondition.makeCondition(startDateId, EntityOperator.NOT_EQUAL, null), betweenCondition);
        
     } catch (BasicException ex) {
     Logger.getLogger(JParamsDatesInterval.class.getName()).log(Level.SEVERE, null, ex);
     }
        
     return null;
     }
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDateStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTxtStartDate;
    // End of variables declaration//GEN-END:variables

    @Override
    public EntityCondition getEntityCondition() {

        try {
            Timestamp fromDate = null;

            Object startdate = Formats.TIMESTAMP.parseValue(jTxtStartDate.getText());

            if (startdate != null) {
                fromDate = new Timestamp(((java.util.Date) startdate).getTime());
            }

            if (fromDate != null) {
                EntityConditionList<EntityExpr> betweenCondition = null;

                betweenCondition = EntityCondition.makeCondition(
                        EntityCondition.makeCondition(startDateId, EntityOperator.GREATER_THAN_EQUAL_TO, fromDate));

                return EntityCondition.makeCondition(EntityCondition.makeCondition(startDateId, EntityOperator.NOT_EQUAL, null), betweenCondition);
            }

        } catch (BasicException ex) {
            Logger.getLogger(JParamsDateSelection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<String, Object>();

        try {
            Timestamp fromDate = null;

            Object startdate = Formats.TIMESTAMP.parseValue(jTxtStartDate.getText());

            if (startdate != null) {
                fromDate = new Timestamp(((java.util.Date) startdate).getTime());
                values.put(StartDateName, fromDate);
            }
        } catch (BasicException ex) {
            Logger.getLogger(JParamsDateSelection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

}