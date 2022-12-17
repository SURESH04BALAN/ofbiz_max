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
package com.openbravo.pos.reports;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.JPanelView;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/*import com.openbravo.pos.forms.JPanelView;
 import com.openbravo.pos.forms.AppView;
 import com.openbravo.pos.forms.AppLocal;
 */
import net.sf.jasperreports.engine.*;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import java.io.FileInputStream;
/*import com.openbravo.pos.forms.DataLogicSales;
 import com.openbravo.pos.sales.TaxesLogic;*/
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.report.OrderMaxJRViewer;
import org.ofbiz.ordermax.report.ReportInterface;
import org.ofbiz.ordermax.report.reports.NewEntityJasperReport;

public abstract class JPanelReport extends JPanel implements JPanelView, BeanFactoryApp, ReportInterface {

    protected OrderMaxJRViewer reportviewer = null;
    //JasperPrint jr = null;
    //private JasperReport jr = null;
    private EditorCreator editor = null;

    protected AppView m_App;
    ReportInterface currRepInterface = null;
    protected boolean active = false;
//su    protected SentenceList taxsent;
//su    protected TaxesLogic taxeslogic;

    /**
     * Creates new form JPanelReport
     */
    public JPanelReport() {

        initComponents();
        /*if (jToggleReportTree.getComponentOrientation().isLeftToRight()) {
         menu_open = new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/1rightarrow.png"));
         menu_close = new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/1leftarrow.png"));
         } else {
         menu_open = new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/menu-left.png"));
         menu_close = new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/menu-right.png"));
         }*/
        assignMenuButtonIcon(false);

    }
    private static Logger logger = Logger.getLogger("com.openbravo.pos.forms.StartPOS");

    @Override
    public void init(AppView app) throws BeanFactoryException {
        lblCaption.setText("");
        m_App = app;
//su        DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
//su        taxsent = dlSales.getTaxList();

        editor = getEditorCreator();
        if (editor instanceof ReportEditorCreator) {
            jPanelFilter.add(((ReportEditorCreator) editor).getComponent(), BorderLayout.CENTER);
        }

        reportviewer = new OrderMaxJRViewer(null);
//reportviewer.setWhenNoDataType(1);
        jPanel2.add(reportviewer, BorderLayout.CENTER);

        try {
            //     String path = "C:/jpos/2-30-916ee5e89542/reports" + getReport() + ".bs";
//            logger.log(Level.OFF, "getReport() : " + getReport());
            //     FileInputStream in = new FileInputStream(path);            
            //  InputStream in = getClass().getResourceAsStream(getReport() + ".ser");
            //   if (in == null) {      
            // read and compile the report
            //getClass().getResourceAsStream(getReport() + ".jrxml")
            //JasperDesign jd = JRXmlLoader.load(getReport());
//            jr = JasperCompileManager.compileReport(jd);
            /* } else {
             // read the compiled report
             ObjectInputStream oin = new ObjectInputStream(in);
             jr = (JasperReport) oin.readObject();
             oin.close();
             }*/

            //jasperPrint = JasperFillManager.fillReport(jasperCompiledFileName, parameters, new JRBeanCollectionDataSource(getBeanCollection(reportArgs)));
        } catch (Exception e) {
            com.openbravo.data.gui.MessageInf msg = new com.openbravo.data.gui.MessageInf(com.openbravo.data.gui.MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadreport"), e);
            msg.show(this);
//            jr = null;
        }
    }

    public Object getBean() {
        return this;
    }

    protected abstract String getReport();

    protected abstract String getResourceBundle();
//su    protected abstract BaseSentence getSentence();
//su    protected abstract ReportFields getReportFields();

    protected EditorCreator getEditorCreator() {
        return null;
    }

    public JComponent getComponent() {
        return this;
    }

    public void activate() throws BasicException {
        active = true;
        setVisibleFilter(true);
//su        taxeslogic = new TaxesLogic(taxsent.list()); 
    }

    @Override
    public boolean deactivate() {
        if (active) {
            reportviewer.loadJasperPrint(null);
        }
        active = false;
        return true;
    }

    protected void setVisibleButtonFilter(boolean value) {
        jToggleFilter.setVisible(value);
    }

    protected void setVisibleFilter(boolean value) {
        jToggleFilter.setSelected(value);
        jToggleFilterActionPerformed(null);
    }

    @Override
    abstract public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception;/* {

     String invoiceId = "";
     Collection result = new ArrayList<PosCloseData>();
     //        InvoiceComposite invoiceComposite = null;
     String partyId = null;
     String invoiceTypeId = null;
     /*
     if (reportArgs.containsKey("invoiceId")) {
     invoiceId = (String) reportArgs.get("invoiceId");
     } else {
     throw new Exception("Invalid invoice number: " + invoiceId);
     }

     if (UtilValidate.isNotEmpty(invoiceId)) {
     GenericValue genericVal = session.getDelegator().findByPrimaryKey("Invoice",
     UtilMisc.toMap("invoiceId", invoiceId));
     if (genericVal != null) {
     partyId = genericVal.getString("partyIdFrom");
     invoiceTypeId = (String) genericVal.getString("invoiceTypeId");
     }
     }
     else {
     throw new Exception("Invalid invoice number: " + invoiceId);
     }

     if (UtilValidate.isEmpty(partyId)) {
     throw new Exception("Invalid invoice number: " + invoiceId);
     }
     */
//        if (reportArgs.containsKey("partyId")) {
//            partyId = (String) reportArgs.get("partyId");
    //           invoiceTypeId = (String) reportArgs.get("invoiceTypeId");
//        }
//        else{
//        throw new Exception("Invalid invoice number: " + invoiceId);
    //       }
//        if (reportArgs.containsKey("Invoice")) {
//            invoiceComposite = (InvoiceComposite) reportArgs.get("Invoice");
//        }

//        if (reportArgs.containsKey("invoiceTypeId")) {
//            invoiceTypeId = (String) reportArgs.get("invoiceTypeId");
//        }
//        if (invoiceComposite == null) {
//            invoiceComposite = LoadInvoiceWorker.loadInvoice(invoiceId, session);
//        }
    //load financial data
    // final ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    //get financial data for this party
//        String billFromPartyId = invoiceComposite.getPartyFrom().getParty().getpartyId();
    // List<InvoiceComposite> list = LoadInvoiceWorker.loadOutstandingInvoices(partyId, invoiceTypeId /*"PURCHASE_INVOICE"*/, session);
    //invoiceCompositeListModel.addAll(list);
    //OrderFinancialData orderFinancialData = new OrderFinancialData(invoiceCompositeListModel, OrderFinancialData.getCurrentDate());
//        OrderFinancialData orderFinancialData = null;
//        if (reportArgs.containsKey("OrderFinancialData")) {
//            orderFinancialData = (OrderFinancialData) reportArgs.get("OrderFinancialData");
//        }
//        invoiceIds.add("MACI194");
//        for (String invoiceId : invoiceIds) {
//        InvoiceHeaderReport headerReport = dataBeanList.getInvoiceBean(session, invoiceId, startDate, endDate, orderFinancialData);
/*        PosCloseData posCloseData = new PosCloseData();
     posCloseData.TOTAL = 100;
     result.add(posCloseData);

     posCloseData = new PosCloseData();
     posCloseData.TOTAL = 200;
     result.add(posCloseData);
     return result;
     }
     */

    public abstract JPanel runReport();

    protected void launchreport(Map<String, Object> collectionMap) {
        lblCaption.setText(identifier());
        ControllerOptions.waitCursorBegin();
        if (UtilValidate.isNotEmpty(getReport())) {
            try {

                // Archivo de recursos
                String res = getResourceBundle();

                // Parametros y los datos
                Object params = (editor == null) ? null : editor.createValue();
                JRDataSource data = new JRBeanCollectionDataSource(getBeanCollection(collectionMap));// null;//new JRDataSourceBasic(getSentence(), getReportFields(), params);
                logger.log(Level.OFF, "res : " + res);
                // Construyo el mapa de los parametros.
                Map reportparams = new HashMap();
                reportparams.put("ARG", params);
                if (res != null) {
                    reportparams.put("REPORT_RESOURCE_BUNDLE", getResourceBundle(res));
                }
                if (collectionMap.containsKey(NewEntityJasperReport.ParametersNameMap)) {
                    reportparams.putAll((Map<String, Object>) collectionMap.get(NewEntityJasperReport.ParametersNameMap));
                }
//su                reportparams.put("TAXESLOGIC", taxeslogic); 
                //jasperPrint = JasperFillManager.fillReport(jasperCompiledFileName, parameters, new JRBeanCollectionDataSource(getBeanCollection(reportArgs)));
                JasperPrint jp = JasperFillManager.fillReport(getReport(), reportparams, data);
//JasperPrint
                //jp.set
                //reportviewer.set
                reportviewer.loadJasperPrint(jp);

                setVisibleFilter(false);

            } catch (MissingResourceException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadresourcedata"), e);
                msg.show(this);
            } catch (JRException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfillreport"), e);
                msg.show(this);
            } catch (BasicException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadreportdata"), e);
                msg.show(this);
            } catch (Exception ex) {
                Logger.getLogger(JPanelReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ControllerOptions.waitCursorEnd();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jPanelFilter = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jToggleFilter = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblCaption = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 204));
        jPanelHeader.setLayout(new java.awt.BorderLayout());

        jPanelFilter.setLayout(new java.awt.BorderLayout());
        jPanelHeader.add(jPanelFilter, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jToggleFilter.setIcon(new javax.swing.ImageIcon(BaseHelper.getOpenBravoIamgePath("/com/openbravo/images/1downarrow.png")));
        jToggleFilter.setSelected(true);
        jToggleFilter.setSelectedIcon(new javax.swing.ImageIcon(BaseHelper.getOpenBravoIamgePath("/com/openbravo/images/1uparrow.png")));
        jToggleFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleFilterActionPerformed(evt);
            }
        });
        jPanel3.add(jToggleFilter);

        jButton1.setIcon(new javax.swing.ImageIcon(BaseHelper.getOpenBravoIamgePath("/com/openbravo/images/launch.png")));
        jButton1.setText("Execute Report");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel1.add(jPanel3, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(new java.awt.BorderLayout());

        lblCaption.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblCaption.setText("jLabel1");
        jPanel4.add(lblCaption, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanelHeader.add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanelHeader, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        runReport();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jToggleFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleFilterActionPerformed

        jPanelFilter.setVisible(jToggleFilter.isSelected());

    }//GEN-LAST:event_jToggleFilterActionPerformed

    /* private void setMenuVisible(boolean value) {

     m_jPanelLeft.setVisible(value);
     assignMenuButtonIcon();
     revalidate();
     }
     */
    private Icon menu_open;
    private Icon menu_close;

    public void assignMenuButtonIcon(boolean isVisiable) {
        /*        jToggleReportTree.setIcon(isVisiable
         ? menu_close
         : menu_open);
         */
    }

    static public ResourceBundle getResourceBundle(String bundleNameAndPath) throws Exception {
        try {
            //File file = new File("C:/jpos/2-30-916ee5e89542/src-pos/locales/" + bundlename + ".properties");
            //URL[] urls = {file.toURI().toURL()};
            //ClassLoader loader = new URLClassLoader(urls);
            //ResourceBundle rb = ResourceBundle.getBundle(bundlename, Locale.getDefault(), loader);
            logger.log(Level.OFF, "bundleNameAndPath : " + bundleNameAndPath);
            FileInputStream fis = new FileInputStream(bundleNameAndPath);
            ResourceBundle resourceBundle = new PropertyResourceBundle(fis);
            return resourceBundle;
        } catch (Exception e) {
            logger.log(Level.OFF, bundleNameAndPath, e);
        }

        throw new Exception("Can't load resourece: " + bundleNameAndPath);
//        m_resources.add(getBundle(bundlename));
        //m_resources.add(ResourceBundle.getBundle(bundlename));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelFilter;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JToggleButton jToggleFilter;
    private javax.swing.JLabel lblCaption;
    // End of variables declaration//GEN-END:variables

}
