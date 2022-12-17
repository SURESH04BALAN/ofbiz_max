/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.reportdesigner_old;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.DataBeanList;

/**
 *
 * @author siranjeev
 */
public class InvoiceReportData {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = InvoiceReportData.class.getName();

    public void loadData(Map<String, Object> reportArgs) {

        /*        addSourceFileName(reportArgs, "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
         addCompiliedFileName(reportArgs, "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jasper");
         addEntity(reportArgs, "OrderHeaderAndItems");
         addOrderBy(reportArgs, "orderDate ASC");
         */
//        if (panelFilter != null) {
//            panelFilter.getDateSelection();
//            java.sql.Timestamp dateEnd = new java.sql.Timestamp(panelFilter.getEndDate().getTime());
//            java.sql.Timestamp dateStart = new java.sql.Timestamp(panelFilter.getStartDate().getTime());

//        }
        
    }
}
