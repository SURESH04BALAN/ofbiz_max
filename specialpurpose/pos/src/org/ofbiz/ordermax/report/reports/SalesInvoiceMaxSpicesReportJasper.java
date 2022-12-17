/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.data.gui.MdiMessageInf;
import com.openbravo.data.gui.MessageInf;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.ofbiz.ordermax.report.ReportInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilNumber;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.entity.DataBeanList;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.orderbase.OrderFinancialData;
import org.ofbiz.ordermax.report.ReportParameterSelectionPanel;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addCompiliedFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSourceFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSubCompiliedFileName;
import static org.ofbiz.ordermax.report.reports.EntityJasperReport.addSubSourceFileName;
import org.ofbiz.ordermax.reportdesigner_old.InvoiceHeaderReport;

/**
 *
 * @author siranjeev
 */
public class SalesInvoiceMaxSpicesReportJasper extends EntityJasperReport implements ReportInterface {

//        static {
//     ReportBaseFactory.registerReport(new InvoiceReportJasper());
//     }
    public static final String module = SalesInvoiceMaxSpicesReportJasper.class.getName();

    @Override
    public String identifier() {
        return "Invoice Report";

    }
    boolean balaji = false;

    @Override
    public String getReportFileName() {
        return "SalesInvoice_maxspices.jrxml";

    }

    @Override
    public String getReportCompiledFileName() {
        return "SalesInvoice_maxspices.jasper";
    }

    @Override
    public String getSubReportCompiledFileName() {
        return "SalesInvoiceItemSubReport.jasper";
    }

    @Override
    public String getSubReportFileName() {
        return "SalesInvoiceItemSubReport.jrxml";
    }

    @Override
    public JPanel runReport() {

        List<EntityCondition> condList = getWhereClauseCond();
        Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
        param.put("ReportTitle", "Invoice Report");
        param.put("DataFile", "CustomBeanFactory.java - Bean Collection");

//        Map<String, Object> reportArgs = EntityJasperReport.getNewArgMap();
//        EntityJasperReport.addParameters(reportArgs, param);
        getSelectionPanelValues(reportArgs);
        EntityJasperReport.addConditionList(reportArgs, condList);
        EntityJasperReport.addDelegator(reportArgs, XuiContainer.getSession().getDelegator());
        EntityJasperReport.addSession(reportArgs, XuiContainer.getSession());
//        EntityJasperReport.addFacilityId(reportArgs, facilityId);

        addSourceFileName(reportArgs, getReportPathAndFile());//"C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml");
        addCompiliedFileName(reportArgs, getCompiledReportPathAndFile());
        addSubSourceFileName(reportArgs, getSubReportPathAndFile());
        addSubCompiliedFileName(reportArgs, getSubCompiledReportPathAndFile());

        return super.runReport(reportArgs);
        /*
         Map<String, Object> whereClauseMap = getWhereClause();
         Map<String, Object> param = EntityJasperReport.getNewArgMap();//new HashMap<String, Object>();        
         param.put("ReportTitle", "Address Report");
         param.put("DataFile", "CustomBeanFactory.java - Bean Collection");

         Map<String, Object> reportArgs = EntityJasperReport.getNewArgMap();
         //        EntityJasperReport.addParameters(reportArgs, param);        
         EntityJasperReport.addWhereClause(reportArgs, whereClauseMap);
         EntityJasperReport.addDelegator(reportArgs, session.getDelegator());
         EntityJasperReport.addSession(reportArgs, session);
         //       EntityJasperReport.addFacilityId(reportArgs,facilityId);

         if (panelFilter != null) {
         panelFilter.getDateSelection();

         java.sql.Timestamp dateEnd = new java.sql.Timestamp(panelFilter.getEndDate().getTime());
         java.sql.Timestamp dateStart = new java.sql.Timestamp(panelFilter.getStartDate().getTime());
         EntityJasperReport.addStartDate(reportArgs, dateStart);
         EntityJasperReport.addEndDate(reportArgs, dateEnd);

         }

         getParameters(reportArgs);

         //Path currentRelativePath = Paths.get("");
         // String s = currentRelativePath.toAbsolutePath().toString();
         //System.out.println("Current relative path is: " + s);
         String masterReportFileName = getReportPathAndFile();// s.concat("\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\PurchaseInvoice3.jasper");
         //String dir = "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\";
         //        String dir = "C:\\AuthLog\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\";
         //        String masterReportFileName = dir + "PurchaseInvoice3.jasper";
         //        String subReportFileName = s.concat("\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\PurchaseInvoiceItemReport.jrxml");
         String subReportFileName = getReportPath().concat("PurchaseInvoiceItemReport.jrxml");
         //        dir + "PurchaseInvoiceItemReport.jrxml";
         */
        //      String destFileName = s + "PurchaseInvoice.JRprint";
//        }

        //     JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        //      JasperPrint jasperPrint = null;
//        try {
            /* Compile the master and sub report */
        /*           JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportFileName);

         //            JasperReport jasperMasterReport = JasperCompileManager.compileReport(masterReportFileName);
         JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObjectFromFile(masterReportFileName);
         Map<String, Object> parameters = new HashMap<String, Object>();
         parameters.put("subreportParameter", jasperSubReport);

         parameters.put("TOTAL_AMT", new BigDecimal(1000).toString());
         System.out.println(" subreportDir: " + getReportPath());
         parameters.put("SUBREPORT_DIR", getReportPath());

         jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, beanColDataSource);

         //            JasperViewer.viewReport(jasperPrint);
         } catch (JRException e) {

         Debug.logError(e, module);
         }
         System.out.println("Done filling!!! ...");
         */
//        return super.getReportPanel(jasperPrint);
    }
    private static final int DECIMALS = UtilNumber.getBigDecimalScale("order.decimals");
    private static final int ROUNDING = UtilNumber.getBigDecimalRoundingMode("order.rounding");
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(DECIMALS, ROUNDING);

    private class InvoiceData {

        String invoiceId;
        String newInvoiceId;
        BigDecimal invoiceAmount = BigDecimal.valueOf(0);
        BigDecimal balanceAmount = BigDecimal.valueOf(0);
        java.sql.Timestamp invoiceDate;
    }

    Map<String, InvoiceData> invoiceDataMap = new HashMap<String, InvoiceData>();

    void loadInvoiceAmounts() {
        try {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            BigDecimal balanceAmount = new BigDecimal(33862.14).setScale(DECIMALS, RoundingMode.UP);
            if (!balaji) {
             //od35870 --5262.68.56    - LIVERPPO           
                InvoiceData data = new InvoiceData();
                data.invoiceId = "MACI22772";
                data.newInvoiceId = "54395";
                data.invoiceAmount = BigDecimal.valueOf(5262.68);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                Date date = dateFormat.parse("01/04/2014");
                long time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                
                //od35862 --4009.10- MACI22771   - LIVERPPO           
                data = new InvoiceData();
                data.invoiceId = "MACI22771";
                data.newInvoiceId = "54410";
                data.invoiceAmount = BigDecimal.valueOf(4009.10);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("04/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                
                //od35890 --6266.08- MACI22774   - LIVERPPO           
                data = new InvoiceData();
                data.invoiceId = "MACI22774";
                data.newInvoiceId = "54413";
                data.invoiceAmount = BigDecimal.valueOf(6266.08);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("04/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                
                //OD35710 --3595.56 - MACI22740   - LIVERPPO           
                data = new InvoiceData();
                data.invoiceId = "MACI22740";
                data.newInvoiceId = "54424";
                data.invoiceAmount = BigDecimal.valueOf(3595.56);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("07/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35780 --3595.56 - MACI22750   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22750";
                data.newInvoiceId = "54446";
                data.invoiceAmount = BigDecimal.valueOf(5093.25);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("10/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35804 --2593.45 - MACI22751   - SEVEN HILLS  
                data = new InvoiceData();
                data.invoiceId = "MACI22760";
                data.newInvoiceId = "57729";
                data.invoiceAmount = BigDecimal.valueOf(2593.45);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("11/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35711 - 3636.71 - MACI22741            
                data = new InvoiceData();
                data.invoiceId = "MACI22741";
                data.newInvoiceId = "54497";
                data.invoiceAmount = BigDecimal.valueOf(3595.56);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("14/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35720 - 3595.40 - MACI22743            
                data = new InvoiceData();
                data.invoiceId = "MACI22743";
                data.newInvoiceId = "54544";
                data.invoiceAmount = BigDecimal.valueOf(3595.40);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("22/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35730 - 4226.77 - MACI22744            
                data = new InvoiceData();
                data.invoiceId = "MACI22744";
                data.newInvoiceId = "54581";
                data.invoiceAmount = BigDecimal.valueOf(4226.77);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("29/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35781 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22751";
                data.newInvoiceId = "54537";
                data.invoiceAmount = BigDecimal.valueOf(5455.36);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("19/04/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                                
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(10000));
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(20000));
                
                //od35900 --11563.88- MACI22775   - LIVERPPO           
                data = new InvoiceData();
                data.invoiceId = "MACI22775";
                data.newInvoiceId = "54599";
                data.invoiceAmount = BigDecimal.valueOf(11563.88);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("01/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(11563.88));                
                
                //od35880 --10667.88- MACI22775   - LIVERPPO           
                data = new InvoiceData();
                data.invoiceId = "MACI22773";
                data.newInvoiceId = "54637";
                data.invoiceAmount = BigDecimal.valueOf(10667.88);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("08/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(10667.88)); 
                
                //OD35790 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22752";
                data.newInvoiceId = "54655";
                data.invoiceAmount = BigDecimal.valueOf(610.03);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("08/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(5000));

                //OD35740 - 2455.96 - MACI22745
                data = new InvoiceData();
                data.invoiceId = "MACI22745";
                data.newInvoiceId = "54676";
                data.invoiceAmount = BigDecimal.valueOf(2455.96);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("13/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35804 --2593.45 - MACI22751   - SEVEN HILLS  
                data = new InvoiceData();
                data.invoiceId = "MACI22760";
                data.newInvoiceId = "54722";
                data.invoiceAmount = BigDecimal.valueOf(2593.45);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("16/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35805 --2593.45 - MACI22761   - SEVEN HILLS  
                data = new InvoiceData();
                data.invoiceId = "MACI22761";
                data.newInvoiceId = "54728";
                data.invoiceAmount = BigDecimal.valueOf(5701.80);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("20/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(10000));
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(5000));
                //OD35750 - 1021.40 - MACI22746
                data = new InvoiceData();
                data.invoiceId = "MACI22746";
                data.newInvoiceId = "54748";
                data.invoiceAmount = BigDecimal.valueOf(1021.40);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("21/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35760 - 2254.80- MACI22747
                data = new InvoiceData();
                data.invoiceId = "MACI22747";
                data.newInvoiceId = "54765";
                data.invoiceAmount = BigDecimal.valueOf(2254.80);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("23/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35791 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22753";
                data.newInvoiceId = "54766";
                data.invoiceAmount = BigDecimal.valueOf(7013.30);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("23/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35792 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22754";
                data.newInvoiceId = "54797";
                data.invoiceAmount = BigDecimal.valueOf(5201.29);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("28/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(13000));
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(6000));
                
                
                //OD35861 --3595.56 - MACI22770   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22770";
                data.newInvoiceId = "54800";
                data.invoiceAmount = BigDecimal.valueOf(4240.84);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("05/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);                
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(4240.84)); 
                
                
                //OD35860 --2279.33 - MACI22769   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22769";
                data.newInvoiceId = "54810";
                data.invoiceAmount = BigDecimal.valueOf(2279.33);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("06/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);                
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(2279.33)); 
                
                //OD35801 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22757";
                data.newInvoiceId = "54814";
                data.invoiceAmount = BigDecimal.valueOf(6549.10);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("13/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                
            //OD35771 - 2254.80- MACI22748
                data = new InvoiceData();
                data.invoiceId = "MACI22749";
                data.newInvoiceId = "54945";
                data.invoiceAmount = BigDecimal.valueOf(4611.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("16/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                
                //OD35770 - 2254.80- MACI22748
                data = new InvoiceData();
                data.invoiceId = "MACI22748";
                data.newInvoiceId = "54960";
                data.invoiceAmount = BigDecimal.valueOf(3080.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("16/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                
                //OD35803 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22759";
                data.newInvoiceId = "55011";
                data.invoiceAmount = BigDecimal.valueOf(3650.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("25/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35800 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22755";
                data.newInvoiceId = "55016";
                data.invoiceAmount = BigDecimal.valueOf(2656.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("25/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                
                //OD35802 --3595.56 - MACI22751   - ROOTY HILL  
                data = new InvoiceData();
                data.invoiceId = "MACI22758";
                data.newInvoiceId = "55033";
                data.invoiceAmount = BigDecimal.valueOf(6549.10);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("27/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);



            } else {
                //OD35810 BALAJI
                balanceAmount = new BigDecimal(29200).setScale(DECIMALS, RoundingMode.UP);
                InvoiceData data = new InvoiceData();
                data.invoiceId = "MACI22762";
                data.newInvoiceId = "000291";
                data.invoiceAmount = BigDecimal.valueOf(6332.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                Date date = dateFormat.parse("07/04/2014");
                long time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(4000));
                //OD35811 BALAJI
                balanceAmount = new BigDecimal(29200).setScale(DECIMALS, RoundingMode.UP);
                data = new InvoiceData();
                data.invoiceId = "MACI22763";
                data.newInvoiceId = "000304";
                data.invoiceAmount = BigDecimal.valueOf(1536.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("03/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35820 BALAJI
                balanceAmount = new BigDecimal(29200).setScale(DECIMALS, RoundingMode.UP);
                data = new InvoiceData();
                data.invoiceId = "MACI22764";
                data.newInvoiceId = "000304";
                data.invoiceAmount = BigDecimal.valueOf(7712.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("17/05/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
                balanceAmount = balanceAmount.subtract(BigDecimal.valueOf(4000));
                
                //OD35830 BALAJI
                balanceAmount = new BigDecimal(29200).setScale(DECIMALS, RoundingMode.UP);
                data = new InvoiceData();
                data.invoiceId = "MACI22765";
                data.newInvoiceId = "000344";
                data.invoiceAmount = BigDecimal.valueOf(6048.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("09/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);

                //OD35831 BALAJI
                balanceAmount = new BigDecimal(29200).setScale(DECIMALS, RoundingMode.UP);
                data = new InvoiceData();
                data.invoiceId = "MACI22766";
                data.newInvoiceId = "000367";
                data.invoiceAmount = BigDecimal.valueOf(4152.00);
                balanceAmount = balanceAmount.add(data.invoiceAmount);
                data.balanceAmount = new BigDecimal(balanceAmount.doubleValue()).setScale(DECIMALS, RoundingMode.UP);
                date = dateFormat.parse("30/06/2014");
                time = date.getTime();
                data.invoiceDate = new Timestamp(time);
                invoiceDataMap.put(data.invoiceId, data);
            }
            /*
             data.invoiceAmount = BigDecimal.TEN;
             data.balanceAmount = BigDecimal.TEN;
             data.invoiceDate = UtilDateTime.nowTimestamp();            
             Date date = dateFormat.parse("23/04/2014");
             long time = date.getTime();            
             data.invoiceDate = new Timestamp(time);            
             invoiceDataMap.put(data.invoiceId, data);
             */
        } catch (ParseException ex) {
            Logger.getLogger(SalesInvoiceMaxSpicesReportJasper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
        DataBeanList dataBeanList = new DataBeanList();
        String invoiceId = "";
        Collection result = new ArrayList<InvoiceHeaderReport>();
//        InvoiceComposite invoiceComposite = null;
        String partyId = null;
        String invoiceTypeId = null;

        if (reportArgs.containsKey("invoiceId")) {
            invoiceId = (String) reportArgs.get("invoiceId");
        } else {
            throw new Exception("Invalid invoice number: " + invoiceId);
        }

        if (UtilValidate.isNotEmpty(invoiceId)) {
            GenericValue genericVal = session.getDelegator().findByPrimaryKey("Invoice",
                    UtilMisc.toMap("invoiceId", invoiceId));
            if (genericVal != null) {
                partyId = genericVal.getString("partyId");
                invoiceTypeId = (String) genericVal.getString("invoiceTypeId");
            }
        } else {
            throw new Exception("Invalid invoice number: " + invoiceId);
        }

        if (UtilValidate.isEmpty(partyId)) {
            throw new Exception("Invalid invoice number: " + invoiceId);
        }

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
        final ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();

        //get financial data for this party
//        String billFromPartyId = invoiceComposite.getPartyFrom().getParty().getpartyId();
        List<InvoiceComposite> list = LoadInvoiceWorker.loadOutstandingInvoices(partyId, invoiceTypeId /*"PURCHASE_INVOICE"*/, session);
        invoiceCompositeListModel.addAll(list);
        OrderFinancialData orderFinancialData = new OrderFinancialData(invoiceCompositeListModel, OrderFinancialData.getCurrentDate());

//        OrderFinancialData orderFinancialData = null;
//        if (reportArgs.containsKey("OrderFinancialData")) {
//            orderFinancialData = (OrderFinancialData) reportArgs.get("OrderFinancialData");
//        }
//        invoiceIds.add("MACI194");
//        for (String invoiceId : invoiceIds) {
        Debug.logInfo(" Invoice ID: " + invoiceId, module);
        InvoiceHeaderReport headerReport = dataBeanList.getSalesInvoiceBean(session, invoiceId, startDate, endDate, orderFinancialData);
/*        loadInvoiceAmounts();

        if (invoiceDataMap.containsKey(invoiceId)) {

            InvoiceData data = invoiceDataMap.get(invoiceId);
            headerReport.setInvoiceNumber(data.newInvoiceId);
            headerReport.setTotalOutstanding(data.balanceAmount);
            headerReport.setinvoiceDate(data.invoiceDate);
            Debug.logInfo(" found Invoice ID: " + data.newInvoiceId, module);
        }
        */
        
        Debug.logInfo(" new Invoice ID: " + headerReport.getInvoiceNumber(), module);
                                        try {
                                    throw new Exception("Party Id:" + headerReport.getpartyId() + "   ABN: " + headerReport.getPartyIdentification());
                                } catch (Exception ex) {
                                    Debug.logError(ex, module);
 //                                   MdiMessageInf msg = new MdiMessageInf(MessageInf.SGN_NOTICE, ex.getMessage(), ex);
//                                    msg.show(null);
                                }
        System.out.println(invoiceId);
        result.add(headerReport);

        return result;
    }

    @Override
    public JPanel getSelectionPanel() {

        Debug.logInfo("getSelectionPanel", module);
        ReportParameterSelectionPanel panelFilter = new ReportParameterSelectionPanel();
        filterList.clear();
        GridBagLayout layout = new GridBagLayout();
        panelFilter.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        ControllerOptions controllerOptions = new ControllerOptions();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 0, 0, 0);  //top padding   
        gbc.weightx = 1;

        int idx = 0;
        int idy = 0;
//        JPanel panel = AddDateSelection("orderDate", controllerOptions, "Date Selection:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

//        JPanel panel = AddProductIdSelection(controllerOptions, "Product Id:");
//        addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        //JPanel panel = AddPartyIdSelection(controllerOptions, "Party Id:");
        //addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        //JPanel panel = createReportSelectionComboPanel("invoiceTypeId", InvoiceTypeSingleton.getValueList(), "Invoice Type");
        //addToGridLayout(panelFilter, panel, gbc, idx, idy++);
        JPanel panel = AddInvoiceIdSelection(filterList, controllerOptions, "Invoice Id:");
        addToGridLayout(panelFilter, panel, gbc, idx, idy++);

        return panelFilter;
    }
}
