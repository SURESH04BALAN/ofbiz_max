/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import org.ofbiz.guiapp.xui.XuiSession;

/**
 *
 * @author siranjeev
 */
public interface ReportInterface {
    public String identifier();
    public JPanel runReport();
    public JPanel getSelectionPanel();
    public boolean getShowSelectionPanel();
    public void setShowSelectionPanel(boolean val);    
    public Map<String, Object> getWhereClause();    
    public OrderMaxJRViewer getJRViewer();
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception;
}
