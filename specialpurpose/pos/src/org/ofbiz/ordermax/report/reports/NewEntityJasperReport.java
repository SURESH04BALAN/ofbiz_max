/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.pos.reports.PanelReportBean;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityComparisonOperator;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.components.FindSelectionEditPanel;
import org.ofbiz.ordermax.report.OrderMaxJRViewer;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.pos.report.JasperReportGenericPanel;

/**
 *
 * @author siranjeev
 */
public abstract class NewEntityJasperReport extends PanelReportBean {

    public static final String module = NewEntityJasperReport.class.getName();
    public static String JrxmlsourceFileName = "JrxmlsourceFileName";
    public static String JasperCompiledFileName = "JasperCompiledFileName";
    public static String JrxmlSubSourceFileName = "JrxmlSubSourceFileName";
    public static String JasperSubCompiledFileName = "JasperSubCompiledFileName";

    public static String DelegatorName = "Delegator";
    public static String ParametersNameMap = "ParametersNameMap";
    public static String ParametersNameCond = "ParametersNameCond";
    public static String EntityName = "EntityName";
    public static String WhereClauseMap = "WhereClauseMap";
    public static String OrderByClause = "OrderByClause";
    public static String Session = "Session";
    public static String DataObject = "DataObject";
    public static String FacilityId = "FacilityId";
    public static String StartDate = "StartDate";
    public static String EndDate = "EndDate";
    public static String EntityConditionList = "EntityConditionList";

    String jrxmlsourceFileName = "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jrxml";
    String jasperCompiledFileName = "";// "C:\\AuthLog\\server\\finalpos\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\reportdesigner\\InventoryItem.jasper";
    String jrxmlSubSourceFileName = "";
    String jasperSubCompiledFileName = "";

    String printFileName = null;
//    Delegator delegator = null;
//    String entityName = "OrderHeaderAndItems";
//    String orderBy = null;
    protected XuiSession session = XuiContainer.getSession();
    protected java.sql.Timestamp startDate = null;
    protected java.sql.Timestamp endDate = null;
//    String facilityId = null;
//    Map<String, Object> whereClauseMap = new HashMap<String, Object>();
    Map<String, Object> parameters = new HashMap<String, Object>();
    public Map<String, Object> reportArgs = new HashMap<String, Object>();
    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();

    //List<EntityCondition> parameterEntityCond = null;
    protected void getParameters(Map<String, Object> reportArgs) {
        if (reportArgs.containsKey(JrxmlsourceFileName)) {
            jrxmlsourceFileName = (String) reportArgs.get(JrxmlsourceFileName);
        }

        if (reportArgs.containsKey(JasperCompiledFileName)) {
            jasperCompiledFileName = (String) reportArgs.get(JasperCompiledFileName);
        }

        if (reportArgs.containsKey(JrxmlSubSourceFileName)) {
            jrxmlSubSourceFileName = (String) reportArgs.get(JrxmlSubSourceFileName);
        }

        if (reportArgs.containsKey(JasperSubCompiledFileName)) {
            jasperSubCompiledFileName = (String) reportArgs.get(JasperSubCompiledFileName);
        }
//        if (reportArgs.containsKey(OrderByClause)) {
//            orderBy = (String) reportArgs.get(OrderByClause);
//        }
//        if (reportArgs.containsKey(DelegatorName)) {
//            delegator = (Delegator) reportArgs.get(DelegatorName);
//        }
        if (reportArgs.containsKey(Session)) {
            session = (XuiSession) reportArgs.get(Session);
        }

        if (reportArgs.containsKey(ParametersNameMap)) {
            parameters = (HashMap<String, Object>) reportArgs.get(ParametersNameMap);
        }

//        if (reportArgs.containsKey(ParametersNameCond)) {
        //           parameterEntityCond = (List<EntityCondition>) reportArgs.get(ParametersNameCond);
        //       }
        //      if (reportArgs.containsKey(EntityName)) {
        //          entityName = (String) reportArgs.get(EntityName);
        //      }
//        if (reportArgs.containsKey(WhereClauseMap)) {
//            whereClauseMap = (Map<String, Object>) reportArgs.get(WhereClauseMap);
//        }
        //       if (reportArgs.containsKey(FacilityId)) {
        //           facilityId = (String) reportArgs.get(FacilityId);
        //       }
        if (reportArgs.containsKey(StartDate)) {
            startDate = (java.sql.Timestamp) reportArgs.get(StartDate);
        }
        if (reportArgs.containsKey(EndDate)) {
            endDate = (java.sql.Timestamp) reportArgs.get(EndDate);
        }
    }

    public void loadParameterSelections() {
//        throw new UnsupportedOperationException("loadParameterSelections: Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //   @Override
    public JPanel runReport(Map<String, Object> reportArgs) {

        JasperPrint jasperPrint = null;
        try {
            long start = System.currentTimeMillis();

            getParameters(reportArgs);
            parameters.put("ROOT_DIR", getReportPath());
            Debug.logInfo("jrxmlsourceFileName: " + jrxmlsourceFileName, module);
//            Debug.logInfo("ROOT_DIR: " + getReportPath(), module);

            if (UtilValidate.isNotEmpty(jasperSubCompiledFileName)) {
//                JasperReport jasperSubReport = JasperCompileManager.compileReport(jrxmlSubSourceFileName);

//            JasperReport jasperMasterReport = JasperCompileManager.compileReport(masterReportFileName);
                JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObjectFromFile(jasperCompiledFileName);
                //Map<String, Object> parameters = new HashMap<String, Object>();
//            SUBREPORT_DIR
                System.out.println(" subreportDir: " + getReportPath());

                parameters.put("SUBREPORT_DIR", getReportPath());
                //              parameters.put("subreportParameter", jasperSubReport);

                jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, new JRBeanCollectionDataSource(getBeanCollection(reportArgs)));

            } else {
//                JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlsourceFileName);
                jasperPrint = JasperFillManager.fillReport(jasperCompiledFileName, parameters, new JRBeanCollectionDataSource(getBeanCollection(reportArgs)));
            }
            System.err.println("End Filling time : " + (System.currentTimeMillis() - start));

        } catch (JRException ex) {
            Debug.logError(ex, module);
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }

        return getReportPanel(jasperPrint);
    }

    public JPanel runTestReport(Map<String, Object> reportArgs) {

        JasperPrint jasperPrint = null;
        try {
            long start = System.currentTimeMillis();

            getParameters(reportArgs);
            Debug.logInfo("jrxmlsourceFileName: " + jrxmlsourceFileName, module);

            if (UtilValidate.isNotEmpty(jasperSubCompiledFileName)) {
                JasperReport jasperSubReport = JasperCompileManager.compileReport(jrxmlSubSourceFileName);

//            JasperReport jasperMasterReport = JasperCompileManager.compileReport(masterReportFileName);
                JasperReport jasperMasterReport = (JasperReport) JRLoader.loadObjectFromFile(jasperCompiledFileName);
                Map<String, Object> parameters = new HashMap<String, Object>();
//            SUBREPORT_DIR
                System.out.println(" subreportDir: " + getReportPath());
                parameters.put("SUBREPORT_DIR", getReportPath());
                parameters.put("subreportParameter", jasperSubReport);

                jasperPrint = JasperFillManager.fillReport(jasperMasterReport, parameters, new JRBeanCollectionDataSource(getBeanCollection(reportArgs)));

            } else {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("TableDataSource", new JREmptyDataSource(50));
                //jasperPrint = 
                jasperPrint = JasperFillManager.fillReport(jasperCompiledFileName, params, new JREmptyDataSource(50));
//                JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlsourceFileName);
                //jasperPrint = JasperFillManager.fillReport(jasperCompiledFileName, parameters, new JRBeanCollectionDataSource(getBeanCollection(reportArgs)));
            }
            System.err.println("End Filling time : " + (System.currentTimeMillis() - start));

        } catch (JRException ex) {
            Debug.logError(ex, module);
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }

        return getReportPanel(jasperPrint);
    }

    protected JPanel getReportPanel(final JasperPrint jasperPrint) {
        JPanel panelReport = new JPanel();
        Rectangle rec = panelReport.getBounds();
        rec.width = 900;
        rec.height = 700;
        panelReport.setBounds(rec);

        if (jasperPrint != null) {
            reportviewer = new OrderMaxJRViewer(jasperPrint);
            /*Rectangle rec = viewer.getBounds();
             rec.width = 900;
             rec.height = 700;
             viewer.setBounds(rec);*/
            panelReport.setLayout(new BorderLayout());
            reportviewer.setPreferredSize(new Dimension(panelReport.getSize()));
//            JScrollPane reportScroll = new JScrollPane(viewer);
            panelReport.add(reportviewer, BorderLayout.CENTER);
            panelReport.invalidate();
//            tabReportPane.setSelectedIndex(1);
//            this.repaint();
        }
        return panelReport;
    }

    /* @Override
     public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

     String entityName = (String) collectionMap.get(EntityName);
     Delegator delegator = (Delegator) collectionMap.get(DelegatorName);
     String orderBy = (String) collectionMap.get(OrderByClause);
     List<GenericValue> genList = null;

     Map<String, Object> whereClauseMap = null;
     List<EntityCondition> entityConditionList = null;
     if (collectionMap.containsKey(WhereClauseMap)) {
     whereClauseMap = (Map<String, Object>) collectionMap.get(WhereClauseMap);
     genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, whereClauseMap, orderBy, delegator);
     } else {
     entityConditionList = (List<EntityCondition>) collectionMap.get(EntityConditionList);
     genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, entityConditionList, orderBy, delegator);
     }

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
     }*/
    /*
     public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
     String entityName = (String) collectionMap.get("entityName");
     Delegator delegator = (Delegator) collectionMap.get("delegator");
     List<EntityCondition> whereClauseMap = (List<EntityCondition>) collectionMap.get("whereClauseMap");
     String orderBy = (String) collectionMap.get("orderBy");

     List<GenericValue> genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, whereClauseMap, orderBy, delegator);
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
     */
    public static void addSourceFileName(Map<String, Object> reportArgs, String fileName) {
        reportArgs.put(JrxmlsourceFileName, fileName);
    }

//    0419442617
//    ref: 621680679035566131
//    rec 01608793
    public static void addCompiliedFileName(Map<String, Object> reportArgs, String fileName) {
        reportArgs.put(JasperCompiledFileName, fileName);
    }

    public static void addParametersNameMap(Map<String, Object> reportArgs, Map<String, Object> fileName) {
        reportArgs.put(ParametersNameMap, fileName);
    }

    public static void addSubSourceFileName(Map<String, Object> reportArgs, String fileName) {
        reportArgs.put(JrxmlSubSourceFileName, fileName);
    }

    public static void addSubCompiliedFileName(Map<String, Object> reportArgs, String fileName) {
        reportArgs.put(JasperSubCompiledFileName, fileName);
    }

    public static void addDelegator(Map<String, Object> reportArgs, Delegator delegator) {
        reportArgs.put(DelegatorName, delegator);
    }

    public static void addSession(Map<String, Object> reportArgs, XuiSession session) {
        reportArgs.put(Session, session);
    }

    public static void addDataObject(Map<String, Object> reportArgs, Object data) {
        reportArgs.put(DataObject, data);
    }

    public static void addConditionList(Map<String, Object> reportArgs, List<EntityCondition> param) {
        reportArgs.put(EntityConditionList, param);
    }

    public static void addEntity(Map<String, Object> reportArgs, String entityName) {
        reportArgs.put(EntityName, entityName);
    }

    public static void addOrderBy(Map<String, Object> reportArgs, String orderBy) {
        reportArgs.put(OrderByClause, orderBy);
    }

    public static void addWhereClause(Map<String, Object> reportArgs, Map<String, Object> whereClause) {
        reportArgs.put(WhereClauseMap, whereClause);
    }

    public static void addStartDate(Map<String, Object> reportArgs, java.sql.Timestamp date) {
        reportArgs.put(StartDate, date);
    }

    public static void addEndDate(Map<String, Object> reportArgs, java.sql.Timestamp date) {
        reportArgs.put(EndDate, date);
    }

    public static void addFacilityId(Map<String, Object> reportArgs, String facility) {
        reportArgs.put(FacilityId, facility);
    }

    public static Map<String, Object> getNewArgMap() {
        return new HashMap<String, Object>();
    }

    public Map<String, Object> getWhereClause() {
        Map<String, Object> whereClauseMap = new HashMap<String, Object>();
        for (ReportParameterSelectionInterface genPanel : filterList) {
            // if (genPanel.isAllSelected() == false) {
            //     whereClauseMap.put(genPanel.getSelId(), genPanel.getEntityValue());
            // }

//            genPanel.getSelection(whereClauseMap);
        }
        return whereClauseMap;
    }

    public void getSelectionPanelValues(Map<String, Object> values) {
        for (ReportParameterSelectionInterface genPanel : filterList) {
            genPanel.getValueMap(values);
        }
    }

    abstract public String getReportFileName();

    public String getReportCompiledFileName() {
        return "";
    }

    public String getResourceFileName() {
        return "";
    }

    public String getSubReportCompiledFileName() {
        return "";
    }

    public String getSubReportFileName() {
        return "";
    }

    public static String getReportPath() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        String masterReportFileName = s.concat("\\specialpurpose\\pos\\src\\com\\openbravo\\pos\\reports\\");
        return masterReportFileName;
    }

    public String getReportPathAndFile() {

        String masterReportFileName = getReportPath();
        return masterReportFileName.concat(getReportFileName());
    }

    public String getCompiledReportPathAndFile() {

        String masterReportFileName = getReportPath();
        return masterReportFileName.concat(getReportCompiledFileName());
    }

    public String getResourcePathAndFile() {

        String masterReportFileName = getReportPath();
        return masterReportFileName.concat(getResourceFileName());
    }

    public String getSubReportPathAndFile() {
        String masterReportFileName = getReportPath();
        return masterReportFileName.concat(getSubReportFileName());
    }

    public String getSubCompiledReportPathAndFile() {
        String masterReportFileName = getReportPath();
        return masterReportFileName.concat(getSubReportCompiledFileName());
    }

    public void addAItem(JPanel panel, Container container) {

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(panel);
    }

    public static java.sql.Timestamp zeroTime(java.sql.Timestamp dateVal) {
        Date date = new Date(dateVal.getTime());                      // timestamp now
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millis in second
        //Date zeroedDate = cal.getTime();             // actually computes the new Date       
        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    public static java.sql.Timestamp maxTime(java.sql.Timestamp dateVal) {
        Date date = new Date(dateVal.getTime());                      // timestamp now
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.set(Calendar.HOUR_OF_DAY, 23);            // set hour to midnight
        cal.set(Calendar.MINUTE, 59);                 // set minute in hour
        cal.set(Calendar.SECOND, 59);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 999);            // set millis in second
        //Date zeroedDate = cal.getTime();             // actually computes the new Date       
        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    public static java.sql.Timestamp incrementDate(java.sql.Timestamp dateVal, int days) {
        Date date = new Date(dateVal.getTime());                      // timestamp now
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);                           // set cal to date
        cal.add(Calendar.DAY_OF_WEEK, days);
        //Date zeroedDate = cal.getTime();             // actually computes the new Date       
        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    protected String getGoodIdentificationSql(Map<String, Object> collectionMap) {
        String str = null;
        if (collectionMap.containsKey("JParamsGoodIdentificationSelection")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsGoodIdentificationSelection");
            String idValue = null;
            String goodIdentificationTypeId = null;

            if (values.containsKey("idValue")) {
                idValue = (String) values.get("idValue");
            }

            if (values.containsKey("goodIdentificationTypeId")) {
                goodIdentificationTypeId = (String) values.get("goodIdentificationTypeId");
            }

            if (UtilValidate.isNotEmpty(idValue) && UtilValidate.isNotEmpty(goodIdentificationTypeId)) {

                str = "  SELECT PROD.PRODUCT_ID FROM ofbiz.good_identification as gi\n"
                        + "  WHERE gi.ID_VALUE ='" + idValue + "'\n";
                str = str.concat(" AND gi.GOOD_IDENTIFICATION_TYPE_ID = '" + goodIdentificationTypeId + "'\n");
                str = str.concat(" AND PROD.PRODUCT_ID = gi.PRODUCT_ID\n");
            }
        }
        return str;
    }

    protected String getProductFormSql(Map<String, Object> collectionMap) {
        String str = null;
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (collectionMap.containsKey("JParamsFormId")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsFormId");

            if (values.containsKey("productId")) {
                FindSelectionEditPanel.FindSelectionEditOption partyOption = (FindSelectionEditPanel.FindSelectionEditOption) values.get("productId");

                if (UtilValidate.isNotEmpty(partyOption.value)) {
                    if (partyOption.op.equals(EntityComparisonOperator.LIKE)) {
                        if (partyOption.ignoreCase) {
                            orderToStringBuilder.append(" lower(PROD.PRODUCT_ID)").append(" like '%").append(partyOption.value.toLowerCase()).append("%'");
                        } else {
                            orderToStringBuilder.append(" PROD.PRODUCT_ID").append(" like '%").append(partyOption.value).append("%'");
                        }
                    } else {
                        if (partyOption.ignoreCase) {
                            orderToStringBuilder.append(" lower(PROD.PRODUCT_ID)").append(" = '").append(partyOption.value.toLowerCase()).append("'");
                        } else {
                            orderToStringBuilder.append(" PROD.PRODUCT_ID").append(" = '").append(partyOption.value).append("'");
                        }
                    }
                }
            } else if (values.containsKey("productName")) {
                FindSelectionEditPanel.FindSelectionEditOption partyOption = (FindSelectionEditPanel.FindSelectionEditOption) values.get("productName");

                if (UtilValidate.isNotEmpty(partyOption.value)) {
                    if (partyOption.op.equals(EntityComparisonOperator.LIKE)) {
                        if (partyOption.ignoreCase) {
                            orderToStringBuilder.append(" lower(PROD.PRODUCT_NAME)").append(" like '%").append(partyOption.value.toLowerCase()).append("%'");
                        } else {
                            orderToStringBuilder.append(" PROD.PRODUCT_NAME").append(" like '%").append(partyOption.value).append("%'");
                        }
                    } else {
                        if (partyOption.ignoreCase) {
                            orderToStringBuilder.append(" lower(PROD.PRODUCT_NAME)").append(" = '").append(partyOption.value.toLowerCase()).append("'");
                        } else {
                            orderToStringBuilder.append(" PROD.PRODUCT_NAME").append(" = '").append(partyOption.value).append("'");
                        }
                    }
                }
            } else {
                if (values.containsKey("productCategoryId")) {
                    orderToStringBuilder.append(" EXISTS (");
                    orderToStringBuilder.append(getProductCategorySql(collectionMap));
                    orderToStringBuilder.append(")");
                }
            }

        }
        return orderToStringBuilder.toString();
    }

    protected String getProductCategorySql(Map<String, Object> collectionMap) {

        String str = null;
        if (collectionMap.containsKey("JParamsFormId")) {
            String productCategoryId = null;
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsFormId");

            if (values.containsKey("productCategoryId")) {
                productCategoryId = (String) values.get("productCategoryId");
            }

            if (UtilValidate.isNotEmpty(productCategoryId)) {
                str = " SELECT distinct pc.PRODUCT_ID  FROM ofbiz.product_category_MEMBER as pc\n"
                        + "     WHERE PROD.PRODUCT_ID  = pc.PRODUCT_ID AND pc.PRODUCT_CATEGORY_ID = '" + productCategoryId + "'\n";
            }
        }
        return str;
    }

    protected String getPartyIdSql(String partyIdName, Map<String, Object> collectionMap) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        if (collectionMap.containsKey("JParamsPartyId")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsPartyId");
            if (values.containsKey("partyId")) {
                String partyId = (String) values.get("partyId");
                if (UtilValidate.isNotEmpty(partyId)) {
                    orderToStringBuilder.append(partyIdName).append(" = '").append(partyId).append("'");
                }
            }
        }
        return orderToStringBuilder.toString();
    }

    protected String getFacilityIdSql(String facilityIdName, Map<String, Object> collectionMap) {

        StringBuilder orderToStringBuilder = new StringBuilder();
        if (collectionMap.containsKey("JParamsGenericComboBoxSelection")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsGenericComboBoxSelection");
            if (values.containsKey("facilityId")) {
                String facilityId = (String) values.get("facilityId");
                if (UtilValidate.isNotEmpty(facilityId)) {
                    orderToStringBuilder.append(facilityIdName).append(" = '").append(facilityId).append("'");
                }
            }
        }
        return orderToStringBuilder.toString();
    }

    protected String getFacilityId(Map<String, Object> collectionMap) {

        String facilityId = null;
        if (collectionMap.containsKey("JParamsGenericComboBoxSelection")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsGenericComboBoxSelection");
            if (values.containsKey("facilityId")) {
                facilityId = (String) values.get("facilityId");

            }
        }
        return facilityId;
    }

    protected String getDateSql(String startdateName, Map<String, Object> collectionMap) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (collectionMap.containsKey("JParamsStartAndEndDatesInterval")) {

            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsStartAndEndDatesInterval");
            java.sql.Timestamp dayStart = null; //zeroTime(UtilDateTime.nowTimestamp());
            java.sql.Timestamp dayEnd = null;//maxTime(UtilDateTime.nowTimestamp());

            if (values.containsKey("startDate")) {
                dayStart = (java.sql.Timestamp) values.get("startDate");
            }
            if (values.containsKey("endDate")) {
                dayEnd = (java.sql.Timestamp) values.get("endDate");
            }
            //             + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
            //             + "(pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"

            if (dayStart != null) {
                if (dayEnd == null) {
                    orderToStringBuilder.append("(").append(startdateName).append(">='").append(dayStart.toString()).append("'");
                    orderToStringBuilder.append(" OR ").append(startdateName).append(" is null )");
                    //             + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
                    //"(" pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"
                } else {
                    orderToStringBuilder.append("((").append(startdateName).append(" BETWEEN '").append(dayStart.toString()).append("' AND '").append(dayEnd.toString()).append("')");
                    orderToStringBuilder.append(" OR ").append(startdateName).append(" is null )");
                }
            }

        }

        return orderToStringBuilder.toString();
    }

    protected String getDateRangeSql(String startdateName, String endDateName, Map<String, Object> collectionMap) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (collectionMap.containsKey("JParamsStartAndEndDatesInterval")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsStartAndEndDatesInterval");
            java.sql.Timestamp dayStart = null;//zeroTime(UtilDateTime.nowTimestamp());
            java.sql.Timestamp dayEnd = null; //maxTime(UtilDateTime.nowTimestamp());

            if (values.containsKey("startDate")) {
                dayStart = (java.sql.Timestamp) values.get("startDate");
            }
            if (values.containsKey("endDate")) {
                dayEnd = (java.sql.Timestamp) values.get("endDate");
            }
            //             + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
            //             + "(pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"

            if (dayStart != null) {
                if (dayEnd == null) {
                    orderToStringBuilder.append("(").append(startdateName).append(">='").append(dayStart.toString()).append("'");
                    orderToStringBuilder.append(" OR ").append(startdateName).append(" is null )");
                    //             + " AND ((pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE  is null) OR \n"
                    //"(" pp.FROM_DATE >= '2015-05-25 00:00:00' AND  pp.THRU_DATE <=  '2015-12-25 00:00:00') )\n"
                } else {
                    orderToStringBuilder.append("(").append(startdateName).append(" BETWEEN'").append(dayStart.toString()).append("' ");
                    orderToStringBuilder.append(" AND ").append(endDateName).append("'").append(dayEnd.toString()).append("')");
                }
            }

        }
        return orderToStringBuilder.toString();
    }

    protected ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }
        
    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        String entityName = (String) collectionMap.get(EntityName);
        Delegator delegator = (Delegator) collectionMap.get(DelegatorName);
        String orderBy = (String) collectionMap.get(OrderByClause);
        List<GenericValue> genList = null;

        Map<String, Object> whereClauseMap = null;
        List<EntityCondition> entityConditionList = null;
        if (collectionMap.containsKey(WhereClauseMap)) {
            whereClauseMap = (Map<String, Object>) collectionMap.get(WhereClauseMap);
            genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, whereClauseMap, orderBy, delegator);
        } else {
            entityConditionList = (List<EntityCondition>) collectionMap.get(EntityConditionList);
            genList = PosProductHelper.getReadOnlyGenericValueListsWithSelection(entityName, entityConditionList, orderBy, delegator);
        }

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
    
}
