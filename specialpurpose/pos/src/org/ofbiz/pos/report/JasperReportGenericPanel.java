/*-
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.DatePicker;
import org.ofbiz.ordermax.base.ObservingTextField;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.report.reports.EntityJasperReport;
import org.ofbiz.ordermax.report.reports.ReportBaseFactory;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.pos.generic.*;

/**
 *
 * @author siranjeev
 */
public class JasperReportGenericPanel extends javax.swing.JPanel {

    static public String Name = "Jasper Report";
    final org.ofbiz.guiapp.xui.XuiSession session = XuiContainer.getSession();
    private GenericValueComboBox supplierIdCombo = null;
    boolean isSupplierLoaded = false;
    private org.ofbiz.ordermax.utility.GenericValueComboBox facilityIdCombo = null;
    private String facilityId = null;
//    GenericValueTablePanel tablePanel = null;
//    List<GenericComboSelectionPanel> filterList = new ArrayList<GenericComboSelectionPanel>();

    /**
     * Creates new form InventoryReportPanel
     */
    public JasperReportGenericPanel() {
        initComponents();
        List<String> list = ReportBaseFactory.getReportList(ReportBaseFactory.REP_GROUP_SUPPLIER, null);
        for (String str : list) {
            comboEntityName.addItem(str);
        }
        comboEntityName.setSelectedIndex(0);
/*
        filterList.clear();
        GenericComboSelectionPanel genPanel = new GenericComboSelectionPanel("StatusItem", "statusId", "");
        panelFilter.add(genPanel);
        filterList.add(genPanel);

        genPanel = new GenericComboSelectionPanel("Product", "productId", "");
        panelFilter.add(genPanel);
        filterList.add(genPanel);

        genPanel = new GenericComboSelectionPanel("OrderType", "orderTypeId", "");
        panelFilter.add(genPanel);
        filterList.add(genPanel);
*/
//        tablePanel = new GenericValueTablePanel(session, comboEntityName.getSelectedItem().toString());
//        OrderMaxUtility.addAPanelToPanel(tablePanel, jPanel5);
    }

    @Override
    public String getName() {
        return Name;
    }
    public static final String module = JasperReportGenericPanel.class.getName();



    public void setFacilityId(String facilityId) {
        if (facilityIdCombo != null) {
            facilityIdCombo.setSelectedItemId(facilityId);
        }
    }

    protected PosTransaction mtrans = null;
    List<GenericValue> supplierList = null;

    public void runReport( PosTransaction m_trans) {
        /*
         String jrxmlsourceFileName = "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InvoiceItem.jrxml";
         String jasperCompiledFileName = "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InvoiceItem.jasper";
         String printFileName = null;
         try {
         long start = System.currentTimeMillis();
         //Preparing parameters
         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("ReportTitle", "Address Report");
         parameters.put("DataFile", "CustomBeanFactory.java - Bean Collection");
         JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlsourceFileName);

         //		JasperFillManager.fillReportToFile(origSourceFileName, parameters, new JRBeanCollectionDataSource(CustomBeanFactory.getBeanCollection()));
         final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperCompiledFileName, parameters, new JRBeanCollectionDataSource(getBeanCollection("InvoiceItem")));
         /*           if (jasperPrint != null) {
         panelReportViewer.removeAll();
         JRViewer viewer = new JRViewer(jasperPrint);
         panelReportViewer.setLayout(new BorderLayout());
         panelReportViewer.add(BorderLayout.CENTER, viewer);
         this.setVisible(true);
         } else {

         }

         if (jasperPrint != null) {
         //                JasperViewer.viewReport(jasperPrint);
         new Thread(
         new Runnable() {
         public void run() {
         panelJasper.removeAll();

         JRViewer viewer = new JRViewer(jasperPrint);
         panelJasper.setLayout(new GridLayout(1, 1));
         panelJasper.add(viewer);
         panelJasper.invalidate();
         panelJasper.repaint();
         }
         }
         ).start();

         SwingUtilities.invokeLater(new Runnable() {
         public void run() {

         panelJasper.removeAll();
         JRViewer viewer = new JRViewer(jasperPrint);
         panelJasper.setLayout(new GridLayout(1, 1));
         panelJasper.add(viewer);
         panelJasper.invalidate();
         panelJasper.repaint();
         }
         });
         //                OrderMaxUtility.addAPanelToPanel(viewer, jPanel5);    
         //                                jPanel5.repaint();
         } else {

         int result = JOptionPane.showOptionDialog(null, "Invalid jsaper print object", "Please Enter Entity Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

         }

         System.err.println("Filling time : " + (System.currentTimeMillis() - start));

         } catch (JRException ex) {
         Debug.logError(ex, module);
         } catch (Exception ex) {
         Debug.logError(ex, module);
         }


         //
         if (tablePanel != null) {
         Map<String, Object> whereClauseMap = new HashMap<String, Object>();
         for (GenericComboSelectionPanel genPanel : filterList) {
         if (genPanel.isAllSelected() == false) {
         whereClauseMap.put(genPanel.getSelId(), genPanel.getValue());
         }
         }

         tablePanel.setGenericValueTableName(comboEntityName.getSelectedItem().toString(), whereClauseMap);
         }
         */
        
        if(facilityIdCombo!=null){
            facilityId = facilityIdCombo.getSelectedItemId();
        }
        
        Map<String,Object> whereClauseMap = currReport.getWhereClause();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Address Report");
        param.put("DataFile", "CustomBeanFactory.java - Bean Collection");
                
        Map<String,Object> resultMap = EntityJasperReport.getNewArgMap();  
//        EntityJasperReport.addParameters(resultMap, param);        
        EntityJasperReport.addWhereClause(resultMap, whereClauseMap);
        EntityJasperReport.addDelegator(resultMap, session.getDelegator());
        EntityJasperReport.addSession(resultMap, session );
        EntityJasperReport.addFacilityId(resultMap,facilityId);
        
//        final Locale locale = getLocale(lang);
//        DatePicker dp = new DatePicker((ObservingTextField) txtPaymentDate, locale);
//        startDate = dp.parseDate(txtPaymentDate.getText());        
//        endDate = dp.parseDate(txtEndDate.getText());
          
//        EntityJasperReport.addEndDate(parameters, null);
//        EntityJasperReport jasperReport = new EntityJasperReport();
        /*final JasperPrint jasperPrint = currReport.runReport(resultMap);
        if (jasperPrint != null) {
            panelReport.removeAll();
            JRViewer viewer = new JRViewer(jasperPrint);
            /*Rectangle rec = viewer.getBounds();
            rec.width = 900;
            rec.height = 700;
            viewer.setBounds(rec);
            panelReport.setLayout(new BorderLayout());
            viewer.setPreferredSize(new Dimension(getSize()));
            JScrollPane reportScroll = new JScrollPane(viewer);            
            panelReport.add(viewer,BorderLayout.CENTER);
            panelReport.invalidate();
            tabReportPane.setSelectedIndex(1);
            this.repaint();
        }*/
    }

    public String GetSelectedSupplierId() {
        return supplierIdCombo.getSelectedItemId();
    }//.getSelectedItem().toString();

    public boolean isAllSelected() {
        return supplierIdCombo.getAllSelected();
    }

    protected static String equals = "=";
    protected static String greaterThanequal = ">=";
    protected static String lessThanequal = "<=";
    protected static String notequal = "<>";

    protected boolean isValidQty(String comparator, Integer compQty, Integer qty) {
        boolean result = false;
        Debug.logInfo("comparator : " + comparator + " compQty: " + compQty.toString() + " qty: " + qty.toString(), module);
        if (comparator.equals(equals)) {
            return qty.intValue() == compQty.intValue();
        } else if (comparator.equals(greaterThanequal)) {
            return qty.intValue() >= compQty.intValue();
        } else if (comparator.equals(lessThanequal)) {
            return qty.intValue() <= compQty.intValue();
        } else if (comparator.equals(notequal)) {
            return qty.intValue() != compQty.intValue();
        }
        return result;
    }

    public void printReport( PosTransaction m_trans) {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabReportPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnFind = new javax.swing.JButton();
        lblSupplierName = new javax.swing.JLabel();
        comboEntityName = new javax.swing.JComboBox();
        panelFilter = new javax.swing.JPanel();
        panelReport = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(700, 700));
        setLayout(new java.awt.BorderLayout());

        tabReportPane.setMaximumSize(new java.awt.Dimension(700, 750));

        jPanel2.setMaximumSize(new java.awt.Dimension(700, 700));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setMaximumSize(new java.awt.Dimension(700, 700));
        jPanel4.setPreferredSize(new java.awt.Dimension(680, 120));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(700, 700));

        btnFind.setText("Run");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        lblSupplierName.setText("Report Object:");

        comboEntityName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEntityNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSupplierName)
                .addGap(32, 32, 32)
                .addComponent(comboEntityName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnFind)
                .addContainerGap(234, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSupplierName)
                    .addComponent(comboEntityName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addContainerGap())
        );

        jPanel4.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panelFilter.setBackground(new java.awt.Color(255, 102, 102));
        panelFilter.setForeground(new java.awt.Color(255, 51, 51));
        panelFilter.setMaximumSize(new java.awt.Dimension(700, 700));

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 227, Short.MAX_VALUE)
        );

        jPanel4.add(panelFilter, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        tabReportPane.addTab("Report Selection", jPanel2);

        panelReport.setMaximumSize(new java.awt.Dimension(700, 750));

        javax.swing.GroupLayout panelReportLayout = new javax.swing.GroupLayout(panelReport);
        panelReport.setLayout(panelReportLayout);
        panelReportLayout.setHorizontalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );
        panelReportLayout.setVerticalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        tabReportPane.addTab("Report View", panelReport);

        add(tabReportPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
//        if (mpos != null && mtrans != null) {
        runReport(mtrans);
//        }
    }//GEN-LAST:event_btnFindActionPerformed
    
    org.ofbiz.ordermax.report.ReportInterface currReport = null;
    
    private void comboEntityNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEntityNameActionPerformed
        try {
            // TODO add your handling code here:
            panelFilter.removeAll();
            currReport = ReportBaseFactory.getReport(comboEntityName.getSelectedItem().toString());
            panelFilter.setLayout(new BorderLayout());
            panelFilter.add(currReport.getSelectionPanel(), BorderLayout.CENTER);
            panelFilter.invalidate();
            this.repaint();            
        } catch (Exception ex) {
            Logger.getLogger(JasperReportGenericPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboEntityNameActionPerformed
    public Collection getBeanCollection(String entityName) throws Exception {
        Delegator delegator = session.getDelegator();
        List<GenericValue> genList = PosProductHelper.getGenericValueLists(entityName, delegator);

        Collection result = null;
        try {
            String classname = "org.ofbiz.ordermax.entity." + entityName;
            Debug.logInfo("classname: " + classname, module);
            String amethodname = "getObjectList";
            Class myTarget = Class.forName(classname);
            Object myinstance = myTarget.newInstance();
            Method myMethod;
            myMethod = myTarget.getDeclaredMethod(amethodname, List.class);
            if (myMethod != null) {
                result = (Collection) myMethod.invoke(myinstance, genList);
                if (result != null) {
                    Debug.logInfo("got valid result: " + classname, module);
                    if (result.size() > 0) {
                        Debug.logInfo("count got valid result of  " + result.size(), module);
                    }
                }
            } else {
                Debug.logInfo("method is not found amethodname: " + classname, module);
            }

        } catch (final ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (final SecurityException e) {
            throw new Exception(e.getMessage());
        } catch (final NoSuchMethodException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalArgumentException e) {
            throw new Exception(e.getMessage());
        } catch (final IllegalAccessException e) {
            throw new Exception(e.getMessage());
        } catch (InstantiationException ex) {
            Logger.getLogger(JasperReportGenericPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JComboBox comboEntityName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panelReport;
    private javax.swing.JTabbedPane tabReportPane;
    // End of variables declaration//GEN-END:variables
//    public JTable getjTable1() {
//        return jTable1;
//    }
}
