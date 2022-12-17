/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import org.ofbiz.ordermax.report.reports.EntityJasperReport;
import org.ofbiz.ordermax.report.ReportInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.DataBeanList;
import org.ofbiz.ordermax.entity.InvoiceReportItemEntity;
import org.ofbiz.ordermax.reportdesigner_old.ReportDateSelectionPanel;
import org.ofbiz.pos.generic.GenericComboSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class ProfitReport extends EntityJasperReport implements ReportInterface {
    public static final String module = ProfitReport.class.getName();
    
    /*    static {
     ReportBaseFactory.registerReport(new InventoryItemReport());
     }
     */
    @Override
    public String identifier() {
        return "Profit Report";

    }

    @Override
    public String getReportFileName() {
        return "jasper_report_template.jrxml";
    }

    @Override
    public JPanel runReport() {

        Map<String, Object> whereClauseMap = getWhereClause();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Address Report");
        param.put("DataFile", "CustomBeanFactory.java - Bean Collection");

        Map<String, Object> reportArgs = EntityJasperReport.getNewArgMap();
//        EntityJasperReport.addParameters(reportArgs, param);
        EntityJasperReport.addWhereClause(reportArgs, whereClauseMap);
        EntityJasperReport.addDelegator(reportArgs, session.getDelegator());
        EntityJasperReport.addSession(reportArgs, session);
//        EntityJasperReport.addFacilityId(reportArgs, facilityId);

        if (panelFilter != null) {
            panelFilter.getDateSelection();

            java.sql.Timestamp dateEnd = new java.sql.Timestamp(panelFilter.getEndDate().getTime());
            java.sql.Timestamp dateStart = new java.sql.Timestamp(panelFilter.getStartDate().getTime());
            EntityJasperReport.addStartDate(reportArgs, dateStart);
            EntityJasperReport.addEndDate(reportArgs, dateEnd);

        }

        getParameters(reportArgs);

//        String dir = "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\";
        String masterReportFileName = getReportPathAndFile(); //dir + "jasper_report_template.jrxml";
        String subReportFileName = getReportPath().concat("AddressReport.jrxml");
//        String destFileName = dir + "jasper_report_template.JRprint";
        DataBeanList dataBeanList = new DataBeanList();

        ArrayList<InvoiceReportItemEntity> dataList = dataBeanList.getDataBeanList(session, (String) session.getAttribute("facilityId"), startDate, endDate);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        JasperPrint jasperPrint = null;
        try {
            /* Compile the master and sub report */
            JasperReport jasperMasterReport = JasperCompileManager.compileReport(masterReportFileName);
            JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportFileName);

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("subreportParameter", jasperSubReport);

            jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);

//            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {

            e.printStackTrace();
        }
        System.out.println("Done filling!!! ...");

        return super.getReportPanel(jasperPrint);
    }
    ReportDateSelectionPanel panelFilter = null;

}
