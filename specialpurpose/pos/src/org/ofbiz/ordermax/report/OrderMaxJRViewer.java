/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report;

import javax.swing.JButton;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author BBS Auctions
 */
public class OrderMaxJRViewer extends net.sf.jasperreports.view.JRViewer {

    public OrderMaxJRViewer(JasperPrint jrPrint) {
        super(jrPrint);
        btnReload.setEnabled(true);
                cmbZoom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbZoomItemStateChanged(evt);
            }
        });
    }

    public JButton getBtnReload() {
        return btnReload;
    }

    public void loadJasperPrint(JasperPrint jrPrint) {

        loadReport(jrPrint);
        setZoomRatio(zooms[defaultZoomIndex] / 100f);
        cmbZoomItemStateChanged(null);
        refreshPage();
    }
    
    void cmbZoomItemStateChanged(java.awt.event.ItemEvent evt) {                                         
		// Add your handling code here:
		btnActualSize.setSelected(false);
		btnFitPage.setSelected(false);
		btnFitWidth.setSelected(false);
	}            
    
    
}
