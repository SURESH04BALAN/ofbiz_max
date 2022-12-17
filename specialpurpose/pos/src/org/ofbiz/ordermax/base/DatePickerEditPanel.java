/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;

/**
 *
 * @author siranjeev
 */
public class DatePickerEditPanel extends javax.swing.JPanel {

    static public String module = DatePickerEditPanel.class.getName();

    /**
     * Creates new form DatePickerEditPanel
     */
    public DatePickerEditPanel() {
        initComponents();
    }

    protected XuiSession session = null;

    public XuiSession getSession() {
        return session;
    }

    public void setSession(XuiSession session) {
        this.session = session;
    }

    public static DatePickerEditPanel addToPanel(JPanel panelEntryDate) {
        DatePickerEditPanel editEntryDate = new DatePickerEditPanel();
        editEntryDate.setSession(XuiContainer.getSession());
        panelEntryDate.setLayout(new BorderLayout());
        panelEntryDate.add(BorderLayout.CENTER, editEntryDate);
        return editEntryDate;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        txtDate = new ObservingTextField();

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\backup\\ofbiz-12.04.02\\dateselecticon.png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtDate.setMinimumSize(new java.awt.Dimension(6, 20));
        txtDate.setPreferredSize(new java.awt.Dimension(6, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txtDate, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, txtDate});

    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        final ClassLoader cl = this.getClassLoader();
//        Thread.currentThread().setContextClassLoader(cl);

        // TODO add your handling code here:
        String lang = null;
        final Locale locale = Locale.getDefault();//getLocale(lang);
        DatePicker dp = new DatePicker((ObservingTextField) txtDate, locale);
        //previously Selectd date
        java.util.Date selectedDate = dp.parseDate(txtDate.getText());
        dp.setSelectedDate(selectedDate);
        dp.start(txtDate);
        
        ActionEvent event = new ActionEvent(this, 1, txtDate.getText());
        for (ActionListener listener : listeners) {
            Debug.logInfo("Date Picker Action listner", module);
            listener.actionPerformed(event); // broadcast to all
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setCurrentDate() {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(cal.getTime());                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
        setDate(cal.getTimeInMillis());
        //String lang = null;
        //final Locale locale = getLocale(lang);
        //DatePicker dp = new DatePicker((ObservingTextField) txtDate, locale);

        //txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(cal.getTimeInMillis())));
    }

    public void setDate(long millis) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(new java.util.Date(millis));                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
//        String lang = null;
//        final Locale locale = getLocale(lang);
//        DatePicker dp = new DatePicker((ObservingTextField) txtDate, locale);

        txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(cal.getTimeInMillis())));
    }

    public void setDate(java.sql.Timestamp timestamp) {
        if (UtilValidate.isNotEmpty(timestamp)) {
            txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(timestamp.getTime())));
        }
    }

    public java.sql.Timestamp getTimeStamp() throws Exception {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        java.sql.Timestamp date = null; // new java.sql.Timestamp(cal.getTimeInMillis());
        String lang = null;
        final Locale locale = getLocale(lang);
        if (txtDate != null && UtilValidate.isNotEmpty(txtDate.getText())) {
//            try {
            DatePicker dp = new DatePicker((ObservingTextField) txtDate, locale);
            if (dp != null) {
                return new java.sql.Timestamp(dp.parseDate(txtDate.getText()).getTime());
            }
//            } catch (Exception ex) {
//                Debug.logError(ex, module);
            //           }
        } else {
            throw new Exception("Date is empty");
        }
        return date;
    }

    public void setTimeStamp(java.sql.Timestamp date) throws Exception {
        if (UtilValidate.isEmpty(date)) {
            throw new Exception("Date is null");
        }
        setDate(date.getTime());

    }

    private Locale getLocale(String loc) {
        if (loc != null && loc.length() > 0) {
            return new Locale(loc);
        } else {
            return Locale.getDefault();
        }

    }

    protected List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public void clearActionListener() {
        listeners.clear();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    public javax.swing.JTextField txtDate;
    // End of variables declaration//GEN-END:variables
}