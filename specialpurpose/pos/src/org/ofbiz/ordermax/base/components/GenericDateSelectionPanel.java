/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityConditionList;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.ordermax.Dates.DateSingleton;
import org.ofbiz.ordermax.Dates.RegularTimePeriod;
import org.ofbiz.ordermax.Dates.SimpleTimePeriod;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.entity.ContentType;
import org.ofbiz.ordermax.product.productContent.ContentTypeSingleton;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel.PERIOD;

/**
 *
 * @author siranjeev
 */
public class GenericDateSelectionPanel extends javax.swing.JPanel implements ReportParameterSelectionInterface{

    public static final String module = GenericDateSelectionPanel.class.getName();

    /**
     * Creates new form ReportDateSelectionPanel
     */
    public DatePickerEditPanel startDatePickerEditPanel;
    public DatePickerEditPanel endDatePickerEditPanel;
    public String startDateId;
    public String endDateId;
    private JGenericComboBoxSelectionModel<DateSingleton.PERIOD> comboDateType = null;
    boolean changeComboSel = false;

    public GenericDateSelectionPanel(String startDateId) {
        initComponents();

        this.startDateId = startDateId;
//        this.endDateId = endDateId;
        startDatePickerEditPanel = DatePickerEditPanel.addToPanel(panelStartDate);
        endDatePickerEditPanel = DatePickerEditPanel.addToPanel(panelEndDate);

//        JComboBox<PERIOD> comboBox = new JComboBox<>();
//        comboBoxPeriod.setModel(new DefaultComboBoxModel(PERIOD.values()));
        comboDateType = new JGenericComboBoxSelectionModel<DateSingleton.PERIOD>(panelDateBoxPeriod, DateSingleton.getValueList());

        startDatePickerEditPanel.txtDate.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (changeComboSel == false) {
                    comboDateType.setSelectedItem(DateSingleton.PERIOD.CUSTOMDATE);
                }
            }
        });

        startDatePickerEditPanel.txtDate.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (changeComboSel == false) {
                    comboDateType.setSelectedItem(DateSingleton.PERIOD.CUSTOMDATE);
                }
            }
        });

        endDatePickerEditPanel.txtDate.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (changeComboSel == false) {
                    comboDateType.setSelectedItem(DateSingleton.PERIOD.CUSTOMDATE);
                }
            }
        });
        endDatePickerEditPanel.txtDate.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (changeComboSel == false) {
                    comboDateType.setSelectedItem(DateSingleton.PERIOD.CUSTOMDATE);
                }
            }
        });

        comboDateType.selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            DateSingleton.PERIOD period = comboDateType.dataListModel.getElementAt(i);
                            try {
                                SimpleTimePeriod regularTimePeriod = DateSingleton.getRegularTimePeriod(period);
                                if (regularTimePeriod != null) {
                                    Date sDate = regularTimePeriod.getStart();
                                    Date eDate = regularTimePeriod.getEnd();
                                    changeComboSel = true;
                                    setStartAndEndDate(sDate, eDate);
                                    changeComboSel = false;
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                }
            }
        });

        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(cal.getTime());                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
//        java.sql.Timestamp zeroedDate = new java.sql.Timestamp(cal.getTimeInMillis());        
        setStartAndEndDate(new java.util.Date(cal.getTimeInMillis()), new java.util.Date(cal.getTimeInMillis()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        panelStartDate = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        lblEndDate = new javax.swing.JLabel();
        panelEndDate = new javax.swing.JPanel();
        panelDateBoxPeriod = new javax.swing.JPanel();

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("Date Selection:");

        javax.swing.GroupLayout panelStartDateLayout = new javax.swing.GroupLayout(panelStartDate);
        panelStartDate.setLayout(panelStartDateLayout);
        panelStartDateLayout.setHorizontalGroup(
            panelStartDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );
        panelStartDateLayout.setVerticalGroup(
            panelStartDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("Start Date:");

        lblEndDate.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblEndDate.setText("End Date:");

        javax.swing.GroupLayout panelEndDateLayout = new javax.swing.GroupLayout(panelEndDate);
        panelEndDate.setLayout(panelEndDateLayout);
        panelEndDateLayout.setHorizontalGroup(
            panelEndDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );
        panelEndDateLayout.setVerticalGroup(
            panelEndDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDateBoxPeriodLayout = new javax.swing.GroupLayout(panelDateBoxPeriod);
        panelDateBoxPeriod.setLayout(panelDateBoxPeriodLayout);
        panelDateBoxPeriodLayout.setHorizontalGroup(
            panelDateBoxPeriodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );
        panelDateBoxPeriodLayout.setVerticalGroup(
            panelDateBoxPeriodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblEndDate, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDateBoxPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelDateBoxPeriod, panelEndDate, panelStartDate});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(panelDateBoxPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(panelStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEndDate)
                    .addComponent(panelEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelEndDate, panelStartDate});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    protected java.util.Date startDate = null;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    protected java.util.Date endDate = null;

    //@Override
    public void getSelection(Map<String, Object> resultMap) {
        try {
            resultMap.put(startDateId, (java.sql.Timestamp) startDatePickerEditPanel.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            resultMap.put(endDateId, (java.sql.Timestamp) endDatePickerEditPanel.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void setStartAndEndDate(java.util.Date startDate, java.util.Date endDate) {
        try {
            startDatePickerEditPanel.setTimeStamp(new java.sql.Timestamp(startDate.getTime()));
        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            endDatePickerEditPanel.setTimeStamp(new java.sql.Timestamp(endDate.getTime()));
        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public EntityCondition getEntityCondition() {

        Timestamp fromDate = null;
        if (comboDateType.getSelectedItem() == null || comboDateType.getSelectedItem().equals(DateSingleton.PERIOD.NODATE)) {
            return null;
        }

        try {
            fromDate = (java.sql.Timestamp) startDatePickerEditPanel.getTimeStamp();
        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        Timestamp thruDate = null;
        try {
            thruDate = (java.sql.Timestamp) endDatePickerEditPanel.getTimeStamp();
               // timestamp now
            Calendar cal = Calendar.getInstance();       // get calendar instance
            cal.setTime(thruDate);                           // set cal to date
            cal.set(Calendar.HOUR_OF_DAY, 23);            // set hour to midnight
            cal.set(Calendar.MINUTE, 59);                 // set minute in hour
            cal.set(Calendar.SECOND, 59);                 // set second in minute
            cal.set(Calendar.MILLISECOND, 999);            // set millis in second
            thruDate = new Timestamp(cal.getTime().getTime());
        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        EntityConditionList<EntityExpr> betweenCondition = EntityCondition.makeCondition(EntityCondition.makeCondition(startDateId, EntityOperator.GREATER_THAN_EQUAL_TO, fromDate), EntityCondition.makeCondition(startDateId, EntityOperator.LESS_THAN, thruDate));
        return EntityCondition.makeCondition(EntityCondition.makeCondition(startDateId, EntityOperator.NOT_EQUAL, null), betweenCondition);
    }

    @Override
    public String getEntityId() {
        return startDateId;
    }

    @Override
    public Object getEntityValue() {
        if (startDatePickerEditPanel.txtDate.getText().isEmpty() == false) {
            Timestamp fromDate = null;
            try {
                fromDate = (java.sql.Timestamp) startDatePickerEditPanel.getTimeStamp();
                return fromDate.toString();
            } catch (Exception ex) {
                Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public void getValueMap(Map<String, Object> valueMap) {
        Timestamp fromDate = null;
        try {
            fromDate = (java.sql.Timestamp) startDatePickerEditPanel.getTimeStamp();
            if (fromDate != null) {
                valueMap.put(startDateId, fromDate);
            }
        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        Timestamp thruDate = null;
        try {
            thruDate = (java.sql.Timestamp) endDatePickerEditPanel.getTimeStamp();
            if (thruDate != null) {
                valueMap.put(endDateId, thruDate);
            }

        } catch (Exception ex) {
            Logger.getLogger(GenericDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JLabel lblEndDate;
    private javax.swing.JPanel panelDateBoxPeriod;
    private javax.swing.JPanel panelEndDate;
    private javax.swing.JPanel panelStartDate;
    // End of variables declaration//GEN-END:variables
}
