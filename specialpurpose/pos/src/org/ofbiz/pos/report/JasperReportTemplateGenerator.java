/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.report;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRConditionalStyle;
import net.sf.jasperreports.engine.JRException;
import static net.sf.jasperreports.engine.JRVariable.REPORT_COUNT;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;

/**
 *
 * @author siranjeev
 */
public class JasperReportTemplateGenerator {

    protected int pageWidth = 842;
    protected int pageHeight = 545;
    protected int columnWidth = 515;
    protected int columnSpacing = 0;
    protected int leftMargin = 40;
    protected int rightMargin = 40;
    protected int topMargin = 50;
    protected int bottomMargin = 50;
    String name = "MyNoXmlDesignReport";
    String language ="groovy";
    public JasperDesign buildJasperReportTable() {
        JasperDesign jasperDesign = null;
        name="invoice Item";
        language="groovy";
        pageWidth=595;
        pageHeight=842;
        columnWidth=535;
        leftMargin=20;
        rightMargin=20;
        topMargin=20;
        bottomMargin=20;        
        
        try {
            jasperDesign = getJasperDesign(name, getPageWidth(), getPageHeight(), getColumnWidth(), getColumnSpacing(), getLeftMargin(), getRightMargin(), getTopMargin(), getBottomMargin());
            jasperDesign.setLanguage(language);          
        } catch (JRException ex) {
            Logger.getLogger(JasperReportTemplateGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public JasperDesign buildJasperReport() {
        JasperDesign jasperDesign = null;
        try {
            jasperDesign = getJasperDesign(name, getPageWidth(), getPageHeight(), getColumnWidth(), getColumnSpacing(), getLeftMargin(), getRightMargin(), getTopMargin(), getBottomMargin());

            jasperDesign.setOrientation(OrientationEnum.PORTRAIT);

            JRDesignConditionalStyle condStyle = getConditionStyle("$V{REPORT_COUNT}%2 == 0");
            condStyle.setBackcolor(new Color(0xe6dac3));
            JRDesignStyle normalStyle = getJRDesignStyle(jasperDesign, "Sans_Normal", true, "DejaVu Sans", 10, "Helvetica", "Cp1252", false);
            normalStyle.addConditionalStyle(condStyle);
            JRDesignStyle boldStyle = getJRDesignStyle(jasperDesign, "Sans_Bold", true, "DejaVu Sans", 10, "Helvetica-Bold", "Cp1252", false);
            JRDesignStyle italicStyle = getJRDesignStyle(jasperDesign, "Sans_Italic", true, "DejaVu Sans", 10, "Helvetica-Oblique", "Cp1252", false);
            JRDesignParameter pram = getJRDesignParameter(jasperDesign, "ReportTitle", java.lang.String.class);
            JRDesignParameter pram1 = getJRDesignParameter(jasperDesign, "OrderByClause", java.lang.String.class);
            JRDesignField field1 = getJRDesignField(jasperDesign, "id", java.lang.Integer.class);
            field1 = getJRDesignField(jasperDesign, "name", java.lang.String.class);
            field1 = getJRDesignField(jasperDesign, "orderName", java.lang.String.class);
            field1 = getJRDesignField(jasperDesign, "street", java.lang.String.class);
            field1 = getJRDesignField(jasperDesign, "city", java.lang.String.class);
            field1 = getJRDesignField(jasperDesign, "author", java.lang.String.class);

            String groupField = "city";
            String strGroupName = "CityGroup";
            int minHeightToStartNewPage = 60;
            JRDesignGroup group = getJRDesignGroup(jasperDesign, strGroupName, minHeightToStartNewPage);
            group.setExpression(new JRDesignExpression("$F{" + groupField + "}"));

            //variables
            String strCityNumberName = "CityNumber";
            JRDesignVariable varCityNumber = getJRDesignVariable(jasperDesign, strCityNumberName, java.lang.Integer.class, ResetTypeEnum.GROUP);

            varCityNumber.setResetType(ResetTypeEnum.GROUP);
            varCityNumber.setResetGroup(group);
            varCityNumber.setCalculation(CalculationEnum.SYSTEM);
            varCityNumber.setInitialValueExpression(new JRDesignExpression("($V{" + strCityNumberName + "} != null)?(new Integer($V{" + strCityNumberName + "}.intValue() + 1)):(new Integer(1))"));

            JRDesignVariable varAllCities = getJRDesignVariable(jasperDesign, "AllCities", java.lang.String.class, ResetTypeEnum.REPORT);
            varAllCities.setCalculation(CalculationEnum.SYSTEM);

            int groupHeight = 20;
            JRDesignBand bandHeader = getJRDesignBand(groupHeight);
            String expressionStr1 = "\"  \" + String.valueOf($V{" + strCityNumberName + "}) + \". \" + String.valueOf($F{" + groupField + "})";

            JRDesignTextField groupText = getJRDesignTextField(bandHeader, 0, 4, getPageWidth(), 15,
                    HorizontalAlignEnum.LEFT, boldStyle, expressionStr1);
            groupText.setBackcolor(new Color(0xC0, 0xC0, 0xC0));
            groupText.setMode(ModeEnum.OPAQUE);

            JRDesignLine line = getJRDesignLine(0, 19, getPageWidth(), 0);
            bandHeader.addElement(line);

            ((JRDesignSection) group.getGroupHeaderSection()).addBand(bandHeader);

            JRDesignBand bandFooter = getJRDesignBand(groupHeight);
            JRDesignLine footerLine = getJRDesignLine(0, -1, 515, 0);
            bandFooter.addElement(footerLine);

            JRDesignStaticText staticText1 = getJRDesignStaticText(bandFooter, 400, 0, 60, 15, HorizontalAlignEnum.RIGHT, boldStyle, "Count : ");
            expressionStr1 = "$V{CityGroup_COUNT}";
            JRDesignTextField footerText = getJRDesignTextField(bandFooter, 460, 0, 30, 15,
                    HorizontalAlignEnum.RIGHT, boldStyle, expressionStr1);
            ((JRDesignSection) group.getGroupFooterSection()).addBand(bandFooter);

            jasperDesign.addGroup(group);

            /*


    
             //        group.setExpression(new JRDesignExpression("$F{" + fieldName + "}"));
             //        ((JRDesignSection) group.getGroupHeaderSection()).addBand(band);


 
             String expressionStr = "\"  \" + String.valueOf($V{" + strCityNumberName + "}) + \". \" + String.valueOf($F{" + groupField + "})";

             JRDesignTextField groupText = getJRDesignTextField(bandHeader, 0, 4, pageWidth, 15,
             HorizontalAlignEnum.LEFT, boldStyle, expressionStr);
             groupText.setBackcolor(new Color(0xC0, 0xC0, 0xC0));
             groupText.setMode(ModeEnum.OPAQUE);
             bandHeader.addElement(groupText);

             JRDesignLine line = getJRDesignLine(0,19,pageWidth,0);
             bandHeader.addElement(line);
             ((JRDesignSection) group.getGroupHeaderSection()).addBand(bandHeader);


            
             JRDesignBand bandFooter = getJRDesignBand(groupHeight);
             JRDesignLine footerLine = getJRDesignLine(0, -1, 515, 0);
             bandFooter.addElement(footerLine);


             JRDesignStaticText staticText = getJRDesignStaticText(bandFooter, 400,0,60,15,HorizontalAlignEnum.RIGHT,boldStyle,"Count : ");
             expressionStr = "$V{CityGroup_COUNT}";
             JRDesignTextField footerText = getJRDesignTextField(bandFooter, 460, 0, 30, 15,
             HorizontalAlignEnum.RIGHT, boldStyle, expressionStr);


             ((JRDesignSection) group.getGroupFooterSection()).addBand(bandFooter);
             jasperDesign.addGroup(group);        
             */
            //Title
            int titleHeight = 50;
            JRDesignBand bandTitle = getJRDesignBand(titleHeight);
            JRDesignLine titleLine = getJRDesignLine(0, 0, getPageWidth(), 0);
            bandTitle.addElement(titleLine);

            String expressionStr = "$P{ReportTitle}";
            JRDesignTextField titleText = getJRDesignTextField(bandTitle, 0, 10, getPageWidth(), 30,
                    HorizontalAlignEnum.CENTER, normalStyle, expressionStr);

            titleText.setBlankWhenNull(true);
            titleText.setFontSize(22);
            jasperDesign.setTitle(bandTitle);

            //Page header
            int pageHeaderHeight = 20;
            JRDesignBand bandPageHeader = getJRDesignBand(pageHeaderHeight);

            JRDesignFrame frame = getJRDesignFrame(bandPageHeader, 0, 5, getPageWidth(), 15, new Color(0x33, 0x33, 0x33), new Color(0x33, 0x33, 0x33), ModeEnum.OPAQUE);
            JRDesignStaticText staticText = getJRDesignStaticText(0, 0, 55, 15, HorizontalAlignEnum.CENTER, boldStyle, "Id");
            staticText.setForecolor(Color.white);
            staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
            staticText.setMode(ModeEnum.OPAQUE);
            frame.addElement(staticText);

            staticText = getJRDesignStaticText(55, 0, 205, 15, HorizontalAlignEnum.CENTER, boldStyle, "Name");
            staticText.setForecolor(Color.white);
            staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
            staticText.setMode(ModeEnum.OPAQUE);
            frame.addElement(staticText);

            staticText = getJRDesignStaticText(55, 0, 205, 15, HorizontalAlignEnum.CENTER, boldStyle, "Name");
            staticText.setForecolor(Color.white);
            staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
            staticText.setMode(ModeEnum.OPAQUE);
            frame.addElement(staticText);

            staticText = getJRDesignStaticText(260, 0, 155, 15, HorizontalAlignEnum.CENTER, boldStyle, "Street");
            staticText.setForecolor(Color.white);
            staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
            staticText.setMode(ModeEnum.OPAQUE);
            frame.addElement(staticText);

            staticText = getJRDesignStaticText(370, 0, 155, 15, HorizontalAlignEnum.CENTER, boldStyle, "Author");
            staticText.setForecolor(Color.white);
            staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
            staticText.setMode(ModeEnum.OPAQUE);
            frame.addElement(staticText);

            frame.addElement(staticText);

            jasperDesign.setPageHeader(bandPageHeader);

            //Column header
            JRDesignBand bandColumnHeader = getJRDesignBand();
            jasperDesign.setColumnHeader(bandColumnHeader);

            //Detail Band
            int detailHeight = 21;
            int y = 0;
            JRDesignBand bandDetail = getJRDesignBand(detailHeight);
            JRDesignFrame detailFrame = getJRDesignFrame(bandDetail, 0, 0, getPageWidth(), detailHeight, ModeEnum.OPAQUE);
            detailFrame.setStyle(normalStyle);
            expressionStr = "$F{id}";
            JRDesignTextField detailText = getJRDesignTextField(detailFrame, 0, 0, 50, 15, HorizontalAlignEnum.RIGHT, normalStyle, expressionStr);
            detailText.setStretchWithOverflow(true);

            expressionStr = "$F{name} + \" \" + $F{orderName}";
            detailText = getJRDesignTextField(detailFrame, 55, 0, 200, 15, HorizontalAlignEnum.RIGHT, normalStyle, expressionStr);
            detailText.setPositionType(PositionTypeEnum.FLOAT);
            detailText.setStretchWithOverflow(true);

            expressionStr = "$F{street}";
            detailText = getJRDesignTextField(detailFrame, 260, 0, 155, 15, HorizontalAlignEnum.RIGHT, normalStyle, expressionStr);
            detailText.setPositionType(PositionTypeEnum.FLOAT);
            detailText.setStretchWithOverflow(true);

            expressionStr = "$F{author}";
            detailText = getJRDesignTextField(detailFrame, 370, 0, 100, 15, HorizontalAlignEnum.RIGHT, normalStyle, expressionStr);
            detailText.setPositionType(PositionTypeEnum.FLOAT);
            detailText.setStretchWithOverflow(true);

            JRDesignLine detailLine = getJRDesignLine(0, detailHeight, getPageWidth(), 0);
            detailLine.setForecolor(new Color(0x80, 0x80, 0x80));
            detailLine.setPositionType(PositionTypeEnum.FLOAT);
            bandDetail.addElement(detailLine);

            ((JRDesignSection) jasperDesign.getDetailSection()).addBand(bandDetail);

            //Column footer
            JRDesignBand bandColumnFooter = getJRDesignBand();
            jasperDesign.setColumnFooter(bandColumnFooter);

            //Page footer
            JRDesignBand bandPageFooter = new JRDesignBand();
            jasperDesign.setPageFooter(bandPageFooter);

            //Summary
            JRDesignBand bandSummary = new JRDesignBand();
            jasperDesign.setSummary(bandSummary);

            /*            
             int x = 0;
             for (OfbizEntityField ofbizfield : currOfbizEntity.getSelectField().getFields()) {
             JRDesignStaticText obj = ofbizfield.getDesignStaticField();
             obj.setX(x);
             obj.setHeight(15);
             obj.setWidth(50);
             x += 50;
             frame.addElement(obj);
             }
             */
        } catch (JRException ex) {
            Logger.getLogger(JasperReportTemplateGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jasperDesign;
    }

    public static JasperDesign getJasperDesign(String name, int pageWidth, int pageHeight,
            int columnWidth, int columnSpacing, int leftMargin, int rightMargin, int topMargin, int bottomMargin) throws JRException {
        //JasperDesign
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName(name);
        jasperDesign.setPageWidth(pageWidth);
        jasperDesign.setPageHeight(pageHeight);
        jasperDesign.setColumnWidth(columnWidth);
        jasperDesign.setColumnSpacing(columnSpacing);
        jasperDesign.setLeftMargin(leftMargin);
        jasperDesign.setRightMargin(rightMargin);
        jasperDesign.setTopMargin(topMargin);
        jasperDesign.setBottomMargin(bottomMargin);
        return jasperDesign;
    }

    public static JRDesignStyle getJRDesignStyle(JasperDesign jasperDesign, String name, boolean defaultVal, String fontName,
            int fontSize, String pdfFontName, String pdfEncoding, boolean pdfEmbedded) throws JRException {
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName(name);
        normalStyle.setDefault(defaultVal);
        normalStyle.setFontName(fontName);
        normalStyle.setFontSize(fontSize);
        normalStyle.setPdfFontName(pdfFontName);
        normalStyle.setPdfEncoding(pdfEncoding);
        normalStyle.setPdfEmbedded(pdfEmbedded);
        jasperDesign.addStyle(normalStyle);
        return normalStyle;
    }

    public static JRDesignParameter getJRDesignParameter(JasperDesign jasperDesign, String name, Class<?> str) throws JRException {
        JRDesignParameter parameter = new JRDesignParameter();
        parameter.setName(name);
        parameter.setValueClass(str);
        jasperDesign.addParameter(parameter);
        return parameter;
    }

    public static JRDesignQuery getJRDesignQuery(JasperDesign jasperDesign, String text) throws JRException {

        //Query
        JRDesignQuery query = new JRDesignQuery();
        query.setText(text);
        jasperDesign.setQuery(query);
        return query;
    }

    //Fields
    public static JRDesignField getJRDesignField(JasperDesign jasperDesign, String name, Class<?> str) throws JRException {

        JRDesignField field = new JRDesignField();
        field.setName(name);
        field.setValueClass(str);
        jasperDesign.addField(field);
        return field;
    }

    //Variables
    public static JRDesignVariable getJRDesignVariable(JasperDesign jasperDesign, String name, Class<?> str, ResetTypeEnum enumVal) throws JRException {

        JRDesignVariable variable = new JRDesignVariable();
        variable.setName(name);
        variable.setValueClass(str);
        variable.setResetType(enumVal);
        jasperDesign.addVariable(variable);
        return variable;
    }

    public static JRDesignLine getJRDesignLine(int x, int y, int width, int height) throws JRException {

        JRDesignLine line = new JRDesignLine();
        line.setX(0);
        line.setY(-1);
        line.setWidth(515);
        line.setHeight(0);
        return line;
    }

    public static JRDesignVariable getJRDesignVariable(JasperDesign jasperDesign,
            JRDesignGroup designGroup, String name, Class<?> str, ResetTypeEnum enumVal
    ) throws JRException {

        JRDesignVariable variable = new JRDesignVariable();
        variable.setName(name);
        variable.setValueClass(str);
        variable.setResetType(enumVal);
        jasperDesign.addVariable(variable);
        return variable;
    }

    public static JRDesignGroup getJRDesignGroup(JasperDesign jasperDesign,
            String name, int minHeightToStartNewPage//, String fieldName
    ) throws JRException {

        JRDesignGroup group = new JRDesignGroup();
        group.setName(name);
        group.setMinHeightToStartNewPage(minHeightToStartNewPage);
//        group.setExpression(new JRDesignExpression("$F{" + fieldName + "}"));
//        ((JRDesignSection) group.getGroupHeaderSection()).addBand(band);
//        jasperDesign.addGroup(group);
        return group;
    }

    public static JRDesignBand getJRDesignBand(int height
    ) throws JRException {

        JRDesignBand band = new JRDesignBand();
        band.setHeight(height);
        return band;
    }

    public static JRDesignBand getJRDesignBand() throws JRException {

        JRDesignBand band = new JRDesignBand();
        return band;
    }

    public static JRDesignLine getJRDesignBand(int x, int y, int width, int height
    ) throws JRException {

        JRDesignLine line = new JRDesignLine();
        line.setX(x);
        line.setY(y);
        line.setWidth(width);
        line.setHeight(height);
        return line;
    }

    public static JRDesignStaticText getJRDesignStaticText(JRDesignBand band, int x, int y, int width, int height,
            HorizontalAlignEnum horzEnum, JRDesignStyle boldStyle, String text)
            throws JRException {

        JRDesignStaticText staticText = new JRDesignStaticText();
        staticText.setX(x);
        staticText.setY(y);
        staticText.setWidth(width);
        staticText.setHeight(height);
        staticText.setHorizontalAlignment(horzEnum);
        staticText.setStyle(boldStyle);
        staticText.setText(text);

        band.addElement(staticText);
        return staticText;
    }

    public static JRDesignStaticText getJRDesignStaticText(int x, int y, int width, int height,
            HorizontalAlignEnum horzEnum, JRDesignStyle boldStyle, String text)
            throws JRException {

        JRDesignStaticText staticText = new JRDesignStaticText();
        staticText.setX(x);
        staticText.setY(y);
        staticText.setWidth(width);
        staticText.setHeight(height);
        staticText.setHorizontalAlignment(horzEnum);
        staticText.setStyle(boldStyle);
        staticText.setText(text);

        return staticText;
    }

    public static JRDesignTextField getJRDesignTextField(JRDesignBand band, int x, int y, int width, int height,
            HorizontalAlignEnum horzEnum, JRDesignStyle boldStyle, String expression)
            throws JRException {

        JRDesignTextField textField = new JRDesignTextField();
        textField.setX(x);
        textField.setY(y);
        textField.setWidth(width);
        textField.setHeight(height);
        textField.setHorizontalAlignment(horzEnum);
        textField.setStyle(boldStyle);
        textField.setExpression(new JRDesignExpression(expression));
        band.addElement(textField);
        return textField;
    }

    public static JRDesignTextField getJRDesignTextField(JRDesignFrame frame, int x, int y, int width, int height,
            HorizontalAlignEnum horzEnum, JRDesignStyle boldStyle, String expression)
            throws JRException {

        JRDesignTextField textField = new JRDesignTextField();
        textField.setX(x);
        textField.setY(y);
        textField.setWidth(width);
        textField.setHeight(height);
        textField.setHorizontalAlignment(horzEnum);
        textField.setStyle(boldStyle);
        textField.setExpression(new JRDesignExpression(expression));
        frame.addElement(textField);
        return textField;
    }

    public static JRDesignFrame getJRDesignFrame(JRDesignBand band, int x, int y, int width, int height,
            Color foreColor, Color backcolor, ModeEnum modeEnum)
            throws JRException {

        JRDesignFrame frame = new JRDesignFrame();
        frame.setX(x);
        frame.setY(y);
        frame.setWidth(width);
        frame.setHeight(height);
        frame.setForecolor(foreColor);
        frame.setBackcolor(backcolor);
        frame.setMode(modeEnum);
        band.addElement(frame);
        return frame;
    }

    public static JRDesignFrame getJRDesignFrame(JRDesignBand band, int x, int y, int width, int height,
            ModeEnum modeEnum)
            throws JRException {

        JRDesignFrame frame = new JRDesignFrame();
        frame.setX(x);
        frame.setY(y);
        frame.setWidth(width);
        frame.setHeight(height);
        frame.setMode(modeEnum);
        band.addElement(frame);
        return frame;
    }

    static public JRDesignConditionalStyle getConditionStyle(String expression) {
        JRDesignConditionalStyle condStyle = new JRDesignConditionalStyle();
        condStyle.setConditionExpression(new JRDesignExpression(expression));
        return condStyle;
    }

    /**
     * @return the pageWidth
     */
    public int getPageWidth() {
        return pageWidth;
    }

    /**
     * @param pageWidth the pageWidth to set
     */
    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    /**
     * @return the pageHeight
     */
    public int getPageHeight() {
        return pageHeight;
    }

    /**
     * @param pageHeight the pageHeight to set
     */
    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    /**
     * @return the columnWidth
     */
    public int getColumnWidth() {
        return columnWidth;
    }

    /**
     * @param columnWidth the columnWidth to set
     */
    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    /**
     * @return the columnSpacing
     */
    public int getColumnSpacing() {
        return columnSpacing;
    }

    /**
     * @param columnSpacing the columnSpacing to set
     */
    public void setColumnSpacing(int columnSpacing) {
        this.columnSpacing = columnSpacing;
    }

    /**
     * @return the leftMargin
     */
    public int getLeftMargin() {
        return leftMargin;
    }

    /**
     * @param leftMargin the leftMargin to set
     */
    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * @return the rightMargin
     */
    public int getRightMargin() {
        return rightMargin;
    }

    /**
     * @param rightMargin the rightMargin to set
     */
    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    /**
     * @return the topMargin
     */
    public int getTopMargin() {
        return topMargin;
    }

    /**
     * @param topMargin the topMargin to set
     */
    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    /**
     * @return the bottomMargin
     */
    public int getBottomMargin() {
        return bottomMargin;
    }

    /**
     * @param bottomMargin the bottomMargin to set
     */
    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }
}
