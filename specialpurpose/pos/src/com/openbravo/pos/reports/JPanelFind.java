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

import com.openbravo.pos.forms.JPanelView;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/*import com.openbravo.pos.forms.JPanelView;
 import com.openbravo.pos.forms.AppView;
 import com.openbravo.pos.forms.AppLocal;
 */
import com.openbravo.basic.BasicException;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import java.io.FileInputStream;
/*import com.openbravo.pos.forms.DataLogicSales;
 import com.openbravo.pos.sales.TaxesLogic;*/
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.report.OrderMaxJRViewer;

public abstract class JPanelFind extends JPanel implements JPanelView, BeanFactoryApp {

    protected OrderMaxJRViewer reportviewer = null;
    //JasperPrint jr = null;
    //private JasperReport jr = null;
    private EditorCreator editor = null;

    /**
     * Creates new form JPanelReport
     */
    public JPanelFind() {
        initComponents();
    }
    
    private static Logger logger = Logger.getLogger("com.openbravo.pos.forms.StartPOS");

    public void init() throws BeanFactoryException {
        lblCaption.setText("");

//su        DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
//su        taxsent = dlSales.getTaxList();
        editor = getEditorCreator();
        if (editor instanceof ReportEditorCreator) {
            jPanelFilter.add(((ReportEditorCreator) editor).getComponent(), BorderLayout.CENTER);
        }

        //reportviewer = new OrderMaxJRViewer(null);
//reportviewer.setWhenNoDataType(1);
        //jPanel2.add(reportviewer, BorderLayout.CENTER);
    }

    public Object getBean() {
        return this;
    }

    public String getTitle() {
        return "";
    }

    public void activate() throws BasicException {

    }

    public boolean deactivate() {
        return true;
    }

    protected EditorCreator getEditorCreator() {
        return null;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    protected void setVisibleButtonFilter(boolean value) {
        jToggleFilter.setVisible(value);
    }

    public void setVisibleFilter(boolean value) {
        jToggleFilter.setSelected(value);
        jToggleFilterActionPerformed(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * 1downarrow.png
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jPanelFilter = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jToggleFilter = new javax.swing.JToggleButton();
        btnFind = new javax.swing.JButton();
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

        btnFind.setIcon(new javax.swing.ImageIcon(BaseHelper.getOpenBravoIamgePath("/com/openbravo/images/search.png")));
        btnFind.setText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });
        jPanel3.add(btnFind);

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

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed


    }//GEN-LAST:event_btnFindActionPerformed

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
    public javax.swing.JButton btnFind;
    private javax.swing.JPanel jPanel1;
    protected javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelFilter;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JToggleButton jToggleFilter;
    private javax.swing.JLabel lblCaption;
    // End of variables declaration//GEN-END:variables

}