/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.jpos.service;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import jpos.JposException;
import jpos.POSPrinterConst;

import org.ofbiz.base.util.Debug;
/**
 *
 * @author BBS Auctions
 */
public class WindowsPosPrinter extends BaseService implements jpos.services.POSPrinterService12{
     protected static final String ESC = ((char) 0x1b) + "";
    protected static final String PAPER_CUT = ESC + "|100fP";
    static int ALIGN_RIGHT = 2;
    static int ALIGN_CENTER = 1;
    static int ALIGN_LEFT = 0;
    public static String FULL_LINE = "FULL_LINE";
    public static String ITEM_START = "ITEM_START";
    public static String ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    public static String ITEM_PRICE = "ITEM_PRICE";
    public static String ITEM_QTY = "ITEM_QTY";
    public static String ITEM_TOTAL = "ITEM_TOTAL";
    public static String ITEM_END = "ITEM_END";
    public static String ITEM_PRODUCTID = "ITEM_PRODUCTID";
    public static String HEADER_LINE_TITLE = "HEADER_LINE_TITLE";
    public static String HEADER_LINE_DETAIL = "HEADER_LINE_DETAIL";
    public static String ITEM_INFO_SALE = "ITEM_INFO_SALE";
    public static String ITEM_INFO_SALE_DETAIL = "ITEM_INFO_SALE_DETAIL";
    public static String ITEM_INFO_TX = "ITEM_INFO_TX";
    public static String ITEM_INFO_TX_DETAIL = "ITEM_INFO_TX_DETAIL";
    public static String ITEM_INFO_DR = "ITEM_INFO_DR";
    public static String ITEM_INFO_DR_DETAIL = "ITEM_INFO_DR_DETAIL";
    public static String ITEM_INFO_CLERK = "ITEM_INFO_CLERK";
    public static String ITEM_INFO_CLERK_DETAIL = "ITEM_INFO_CLERK_DETAIL";
    public static String FULL_LINE_DATE = "FULL_LINE_DATE";
    static protected PrintRectangle[] printRectangle = new PrintRectangle[]{
        new PrintRectangle(NullPosPrinter.FULL_LINE_DATE, 24, "\\n", 9f, ALIGN_CENTER),
        new PrintRectangle(NullPosPrinter.FULL_LINE, 24, "\\n", 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.HEADER_LINE_TITLE, 24, "\\n", 15f, ALIGN_CENTER),
        new PrintRectangle(NullPosPrinter.HEADER_LINE_DETAIL, 24, "\\n", 9f, ALIGN_CENTER),
        new PrintRectangle(NullPosPrinter.ITEM_START, 0, null),
        new PrintRectangle(NullPosPrinter.ITEM_PRODUCTID, 10, null),
        new PrintRectangle(NullPosPrinter.ITEM_DESCRIPTION, 10, null),
        new PrintRectangle(NullPosPrinter.ITEM_PRICE, 4, null, ALIGN_RIGHT),
        new PrintRectangle(NullPosPrinter.ITEM_QTY, 4, null, ALIGN_RIGHT),
        new PrintRectangle(NullPosPrinter.ITEM_TOTAL, 4, null, ALIGN_RIGHT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_SALE, 3, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_SALE_DETAIL, 6, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_TX, 2, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_TX_DETAIL, 3, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_DR, 2, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_DR_DETAIL, 1, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_CLERK, 3, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_INFO_CLERK_DETAIL, 2, null, 9f, ALIGN_LEFT),
        new PrintRectangle(NullPosPrinter.ITEM_END, 0, "\\n"),};

    static protected class PrintRectangle {

        String id = null;
        int width = 0;
        String val = null;
        int align = 0;
        float fontSize = 9f;

        public PrintRectangle(String id, int width, String val) {
            this.id = id;
            this.width = width;
            this.val = val;

        }

        public PrintRectangle(String id, int width, String val, int a) {
            this.id = id;
            this.width = width;
            this.val = val;
            this.align = a;
        }

        public PrintRectangle(String id, int width, String val, float font) {
            this.id = id;
            this.width = width;
            this.val = val;
            this.fontSize = font;
        }

        public PrintRectangle(String id, int width, String val, float font, int align) {
            this.id = id;
            this.width = width;
            this.val = val;
            this.fontSize = font;
            this.align = align;
        }
    }
    private StringBuffer printerBuffer = new StringBuffer();

    protected PrintRectangle getPrintDetails(String itemName) {
        PrintRectangle printRect = null;

        for (int i = 0; i < printRectangle.length; ++i) {
            if (printRectangle[i].id.equals(itemName) == true) {
                return printRectangle[i];
            }
        }
        return printRect;
    }

    @Override
    public int getDeviceServiceVersion() throws JposException {
        return 1002000;
    }

    public int getCapCharacterSet() throws JposException {
        return 0;
    }

    public boolean getCapConcurrentJrnRec() throws JposException {
        return false;
    }

    public boolean getCapConcurrentJrnSlp() throws JposException {
        return false;
    }

    public boolean getCapConcurrentRecSlp() throws JposException {
        return false;
    }

    public boolean getCapCoverSensor() throws JposException {
        return false;
    }

    public boolean getCapJrn2Color() throws JposException {
        return false;
    }

    public boolean getCapJrnBold() throws JposException {
        return false;
    }

    public boolean getCapJrnDhigh() throws JposException {
        return false;
    }

    public boolean getCapJrnDwide() throws JposException {
        return false;
    }

    public boolean getCapJrnDwideDhigh() throws JposException {
        return false;
    }

    public boolean getCapJrnEmptySensor() throws JposException {
        return false;
    }

    public boolean getCapJrnItalic() throws JposException {
        return false;
    }

    public boolean getCapJrnNearEndSensor() throws JposException {
        return false;
    }

    public boolean getCapJrnPresent() throws JposException {
        return false;
    }

    public boolean getCapJrnUnderline() throws JposException {
        return false;
    }

    public boolean getCapRec2Color() throws JposException {
        return false;
    }

    public boolean getCapRecBarCode() throws JposException {
        return false;
    }

    public boolean getCapRecBitmap() throws JposException {
        return false;
    }

    public boolean getCapRecBold() throws JposException {
        return false;
    }

    public boolean getCapRecDhigh() throws JposException {
        return false;
    }

    public boolean getCapRecDwide() throws JposException {
        return false;
    }

    public boolean getCapRecDwideDhigh() throws JposException {
        return false;
    }

    public boolean getCapRecEmptySensor() throws JposException {
        return false;
    }

    public boolean getCapRecItalic() throws JposException {
        return false;
    }

    public boolean getCapRecLeft90() throws JposException {
        return false;
    }

    public boolean getCapRecNearEndSensor() throws JposException {
        return false;
    }

    public boolean getCapRecPapercut() throws JposException {
        return false;
    }

    public boolean getCapRecPresent() throws JposException {
        return false;
    }

    public boolean getCapRecRight90() throws JposException {
        return false;
    }

    public boolean getCapRecRotate180() throws JposException {
        return false;
    }

    public boolean getCapRecStamp() throws JposException {
        return false;
    }

    public boolean getCapRecUnderline() throws JposException {
        return false;
    }

    public boolean getCapSlp2Color() throws JposException {
        return false;
    }

    public boolean getCapSlpBarCode() throws JposException {
        return false;
    }

    public boolean getCapSlpBitmap() throws JposException {
        return false;
    }

    public boolean getCapSlpBold() throws JposException {
        return false;
    }

    public boolean getCapSlpDhigh() throws JposException {
        return false;
    }

    public boolean getCapSlpDwide() throws JposException {
        return false;
    }

    public boolean getCapSlpDwideDhigh() throws JposException {
        return false;
    }

    public boolean getCapSlpEmptySensor() throws JposException {
        return false;
    }

    public boolean getCapSlpFullslip() throws JposException {
        return false;
    }

    public boolean getCapSlpItalic() throws JposException {
        return false;
    }

    public boolean getCapSlpLeft90() throws JposException {
        return false;
    }

    public boolean getCapSlpNearEndSensor() throws JposException {
        return false;
    }

    public boolean getCapSlpPresent() throws JposException {
        return false;
    }

    public boolean getCapSlpRight90() throws JposException {
        return false;
    }

    public boolean getCapSlpRotate180() throws JposException {
        return false;
    }

    public boolean getCapSlpUnderline() throws JposException {
        return false;
    }

    public boolean getCapTransaction() throws JposException {
        return false;
    }

    public boolean getAsyncMode() throws JposException {
        return false;
    }

    public void setAsyncMode(boolean b) throws JposException {
    }

    public int getCharacterSet() throws JposException {
        return 0;
    }

    public void setCharacterSet(int i) throws JposException {
    }

    public String getCharacterSetList() throws JposException {
        return null;
    }

    public boolean getCoverOpen() throws JposException {
        return false;
    }

    public int getErrorLevel() throws JposException {
        return 0;
    }

    public int getErrorStation() throws JposException {
        return 0;
    }

    public String getErrorString() throws JposException {
        return null;
    }

    public boolean getFlagWhenIdle() throws JposException {
        return false;
    }

    public void setFlagWhenIdle(boolean b) throws JposException {
    }

    public String getFontTypefaceList() throws JposException {
        return null;
    }

    public boolean getJrnEmpty() throws JposException {
        return false;
    }

    public boolean getJrnLetterQuality() throws JposException {
        return false;
    }

    public void setJrnLetterQuality(boolean b) throws JposException {
    }

    public int getJrnLineChars() throws JposException {
        return 0;
    }

    public void setJrnLineChars(int i) throws JposException {
    }

    public String getJrnLineCharsList() throws JposException {
        return null;
    }

    public int getJrnLineHeight() throws JposException {
        return 0;
    }

    public void setJrnLineHeight(int i) throws JposException {
    }

    public int getJrnLineSpacing() throws JposException {
        return 0;
    }

    public void setJrnLineSpacing(int i) throws JposException {
    }

    public int getJrnLineWidth() throws JposException {
        return 0;
    }

    public boolean getJrnNearEnd() throws JposException {
        return false;
    }

    public int getMapMode() throws JposException {
        return 0;
    }

    public void setMapMode(int i) throws JposException {
    }

    public int getOutputID() throws JposException {
        return 0;
    }

    public String getRecBarCodeRotationList() throws JposException {
        return null;
    }

    public boolean getRecEmpty() throws JposException {
        return false;
    }

    public boolean getRecLetterQuality() throws JposException {
        return false;
    }

    public void setRecLetterQuality(boolean b) throws JposException {
    }

    public int getRecLineChars() throws JposException {
        return 0;
    }

    public void setRecLineChars(int i) throws JposException {
    }

    public String getRecLineCharsList() throws JposException {
        return null;
    }

    public int getRecLineHeight() throws JposException {
        return 0;
    }

    public void setRecLineHeight(int i) throws JposException {
    }

    public int getRecLineSpacing() throws JposException {
        return 0;
    }

    public void setRecLineSpacing(int i) throws JposException {
    }

    public int getRecLinesToPaperCut() throws JposException {
        return 0;
    }

    public int getRecLineWidth() throws JposException {
        return 0;
    }

    public boolean getRecNearEnd() throws JposException {
        return false;
    }

    public int getRecSidewaysMaxChars() throws JposException {
        return 0;
    }

    public int getRecSidewaysMaxLines() throws JposException {
        return 0;
    }

    public int getRotateSpecial() throws JposException {
        return 0;
    }

    public void setRotateSpecial(int i) throws JposException {
    }

    public String getSlpBarCodeRotationList() throws JposException {
        return null;
    }

    public boolean getSlpEmpty() throws JposException {
        return false;
    }

    public boolean getSlpLetterQuality() throws JposException {
        return false;
    }

    public void setSlpLetterQuality(boolean b) throws JposException {
    }

    public int getSlpLineChars() throws JposException {
        return 0;
    }

    public void setSlpLineChars(int i) throws JposException {
    }

    public String getSlpLineCharsList() throws JposException {
        return null;
    }

    public int getSlpLineHeight() throws JposException {
        return 0;
    }

    public void setSlpLineHeight(int i) throws JposException {
    }

    public int getSlpLinesNearEndToEnd() throws JposException {
        return 0;
    }

    public int getSlpLineSpacing() throws JposException {
        return 0;
    }

    public void setSlpLineSpacing(int i) throws JposException {
    }

    public int getSlpLineWidth() throws JposException {
        return 0;
    }

    public int getSlpMaxLines() throws JposException {
        return 0;
    }

    public boolean getSlpNearEnd() throws JposException {
        return false;
    }

    public int getSlpSidewaysMaxChars() throws JposException {
        return 0;
    }

    public int getSlpSidewaysMaxLines() throws JposException {
        return 0;
    }

    public void beginInsertion(int i) throws JposException {
    }

    public void beginRemoval(int i) throws JposException {
    }

    public void clearOutput() throws JposException {
    }

    public void cutPaper(int i) throws JposException {
    }

    public void endInsertion() throws JposException {
    }

    public void endRemoval() throws JposException {
    }

    protected List<String> getPrintList() {

        List<String> list = FastList.newInstance();
        boolean lineStart = false;
        String valueStr = "";

        for (Map<String, String> listMap : listItems) {
            for (Map.Entry<String, String> entryMap : listMap.entrySet()) {

                String value = entryMap.getValue() == null ? "" : entryMap.getValue();

                if (entryMap.getKey().equals(NullPosPrinter.ITEM_START)) {
                    lineStart = true;
                    valueStr = value;
                }

                if (lineStart == false) {
                    list.add(entryMap.getValue());
                } else {

                    if (lineStart == true && entryMap.getKey().equals(NullPosPrinter.ITEM_END) == false) {
                        valueStr = valueStr.concat("\t" + value);
                    } else if (lineStart == true && entryMap.getKey().equals(NullPosPrinter.ITEM_END) == true) {
                        valueStr = valueStr.concat("\t" + value);
                        list.add(valueStr);
                        valueStr = "";
                        lineStart = false;
                    } else if (lineStart == true) {
                        valueStr = valueStr.concat("\t" + value);
                    }
                }
            }
        }
        return list;
    }

    private void printALine(String s) {
        printerBuffer = printerBuffer.append(s);
//        list.add(s);
//        listMap.put(FULL_LINE, s);
        try {
            printTwoNormal(0, FULL_LINE, s);
        } catch (JposException e) {
        }

        if (s.indexOf(NullPosPrinter.PAPER_CUT) > 0) {
            Debug.log(printerBuffer.toString(), module);
            printerBuffer = new StringBuffer();
//            list = FastList.newInstance();

        }
    }

    public void printBarCode(int i, String s, int i1, int i2, int i3, int i4, int i5) throws JposException {
        printALine("Barcode:" + s);
    }

    public void printBitmap(int i, String s, int i1, int i2) throws JposException {
        printALine("Bitmap:" + s);
    }

    public void printImmediate(int i, String s) throws JposException {
        printALine("Immediate:" + s);
    }

    public void printNormal(int i, String s) throws JposException {
        printALine(s);
    }
    boolean itemStart = false;
    int lineNumber = 0;
    List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
    Map<String, String> itemMap = null;

    public void printTwoNormal(int i, String id, String value) throws JposException {
//        printALine("2Normal:" + s);
        if (id.equals(ITEM_START)) {
            itemMap = new LinkedHashMap<String, String>();
        }

        if (itemMap != null) {
            itemMap.put(id, value);
        }

        if (id.equals(ITEM_END)) {
            listItems.add(itemMap);
            itemMap = null;
        } else if (itemMap == null) {
            HashMap<String, String> map = new LinkedHashMap<String, String>();
            map.put(id, value);
            listItems.add(map);
        }

//        listMap.put(FULL_LINE, s);
    }

    public void rotatePrint(int i, int i1) throws JposException {
    }

    public void setBitmap(int i, int i1, String s, int i2, int i3) throws JposException {
    }

    public void setLogo(int i, String s) throws JposException {
    }

    String getTruncatedString(Graphics g2d, String s, int size) {
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        if (stringLen <= size) {
            return s;
        } else {
            return getTruncatedString(g2d, s.substring(0, s.length() - 1), size);
        }

    }

    public class PrintArea implements Printable {

        private void drawString(Graphics g, String text, int x, int y) {
            int count = 0;
            try {
                if (text.startsWith("repItemName")) {

                    for (String line : text.split("\t")) {
                        line = line.replaceFirst("repItemName", "");
                        line = line.replaceAll("\t", "");
                        if (count++ == 0) {
                            line = getTruncatedString((Graphics2D) g, line, 62);
                            g.drawString(line, x, y);
                            x += 90;
                        } else {
                            line = getTruncatedString((Graphics2D) g, line, 20);
                            g.drawString(line, x, y);
                            x += 25;
                        }
                    }

                } else {
                    text = getTruncatedString((Graphics2D) g, text, 160);
                    g.drawString(text, x, y);
                }
            } catch (Exception pp) {
                Debug.log(pp, module);
            }

        }

        private void printSimpleCenterdString(Graphics g2d, String s, int width, int XPos, int YPos) {
            int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            int start = width / 2 - stringLen / 2;
            g2d.drawString(s, start + XPos, YPos);
        }

        private void printSimpleString(Graphics g2d, String s, int width, int XPos, int YPos) {
            int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
            int start = width / 2 - stringLen / 2;
            g2d.drawString(s, XPos, YPos);
        }

        public int print(Graphics g, PageFormat pf, int page) throws
                PrinterException {

            if (page > 0) { /* We have only one page, and 'page' is zero-based */

                return NO_SUCH_PAGE;
            }

            List<String> list = getPrintList();

            Graphics2D g2d = (Graphics2D) g;

            g2d.translate(pf.getImageableX(), pf.getImageableY());

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.PLAIN, 9);
            g2d.setFont(font);
            g2d.setFont(g2d.getFont().deriveFont(9f));
            int fHeight = g2d.getFontMetrics().getHeight();
            int y = fHeight;
            int x = 30;
            int x1 = 0;//-10;

            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN));
            g2d.setFont(g2d.getFont().deriveFont(9f));

            x = 0;
            for (Map<String, String> listMap : listItems) {
                printMap(g2d, listMap, (int) pf.getPaper().getWidth(), x, y);
                y += fHeight;
            }

            return PAGE_EXISTS;
        }

        private void drawStringWithWidth(Graphics2D g, String text, int x, int y, int width, int align) {
            try {

                text = getTruncatedString((Graphics2D) g, text, width);
                if (align == ALIGN_RIGHT) {
                    int xLeft = x + width - (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
                    g.drawString(text, xLeft, y);
                } else if (align == ALIGN_CENTER) {
                    int stringLen = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
                    int start = width / 2 - stringLen / 2;
                    g.drawString(text, start + x, y);
                } else { //left
                    g.drawString(text, x, y);
                }

            } catch (Exception pp) {
                Debug.log(pp, module);
            }

        }

        private void drawStringWithWidth(Graphics2D g, String text, int x, int y, int width) {
            try {

                text = getTruncatedString((Graphics2D) g, text, width);
                g.drawString(text, x, y);
            } catch (Exception pp) {
                Debug.log(pp, module);
            }

        }

        private void drawStringWithWidthCentered(Graphics2D g, String text, int paperWidth, int x, int y, int width) {
            try {

                text = getTruncatedString((Graphics2D) g, text, width);
//                g.drawString(text, x, y);	 		
                printSimpleCenterdString(g, text, paperWidth, x, y);
            } catch (Exception pp) {
                Debug.log(pp, module);
            }

        }

        int getFontMetricWidth(Graphics2D g2d, int charWidth) {
            return (int) g2d.getFontMetrics().getStringBounds("W", g2d).getWidth() * charWidth;
        }

        void printMap(Graphics2D g2d, Map<String, String> listMap, int paperWidth, int x, int y) {
            int x1 = x;
            int x2 = x;
            boolean first = true;
            paperWidth = 200;
            for (Map.Entry<String, String> entryMap : listMap.entrySet()) {
                if (entryMap.getKey().equals(HEADER_LINE_TITLE) || entryMap.getKey().equals(HEADER_LINE_DETAIL)) {
                    PrintRectangle rect = getPrintDetails(entryMap.getKey());
                    if (rect != null && rect.width != 0) {
                        Debug.log("paperWidth: " + paperWidth, module);
                        g2d.setFont(g2d.getFont().deriveFont(rect.fontSize));
                        x2 = x1 + getFontMetricWidth(g2d, rect.width);
                        String value = entryMap.getValue() == null ? "" : entryMap.getValue();
                        drawStringWithWidthCentered(g2d, value, paperWidth, x1, y, x2 - x1);
                        x1 = x2 + 1;
                    }
                } else {

                    PrintRectangle rect = getPrintDetails(entryMap.getKey());
                    if (rect != null && rect.width != 0) {
                        g2d.setFont(g2d.getFont().deriveFont(rect.fontSize));
                        x2 = x1 + getFontMetricWidth(g2d, rect.width);
                        String value = entryMap.getValue() == null ? "" : entryMap.getValue();
                        drawStringWithWidth(g2d, value, x1, y, x2 - x1, rect.align);
                        x1 = x2 + 1;
                    }
                }
            }
        }

        public void print() {

            PrinterJob pj = PrinterJob.getPrinterJob();

            PageFormat pf = pj.defaultPage();
            Paper paper = new Paper();
            double margin = 20;
            paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight());
            pf.setPaper(paper);

            pj.setPrintable(new WindowsPosPrinter.PrintArea(), pf);
//            if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException pp) {
                System.out.println(pp);
            }

            listItems.clear();
        }
    }

    public void transactionPrint(int i, int i1) throws JposException {

        Debug.log("transactionPrint:\n\n", module);
        if (POSPrinterConst.PTR_TP_NORMAL == i1) {
//            try {

            List<String> list = getPrintList();

//                BufferedWriter out;
//				out = new BufferedWriter(new FileWriter("1.txt"));
//                out = new BufferedWriter(new FileWriter("1.txt"));
//	    		out.write("Jeevi Slurpy               $2.70");
            WindowsPosPrinter.PrintArea printArea = new WindowsPosPrinter.PrintArea();
            printArea.print();

            for (int ii = 0; ii < list.size(); ii++) {
//                    out.write(list.get(ii));
                Debug.log(list.get(ii), module);
            }

//				out.write("Nivi Slurpy               $2.70");		
//                out.flush();
//                out.close();
//                list.clear();
//                listItems = new ArrayList<Map<String, String>>();
//                File ff = new File("1.txt");
//                PrinterJob pj = PrinterJob.getPrinterJob();
//                PageFormat pf = pj.defaultPage();
//                Paper p = pf.getPaper();
//                p.setImageableArea(0, 0, pf.getImageableWidth(), pf.getImageableHeight());
//                pf.setPaper(p);
//				pj.setPrintable(painter, format)											
//                Desktop desktop = Desktop.getDesktop();
//				desktop.print(ff);
//            } catch (IOException e) {
            // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        } else if (100 == i1) {
            Debug.log("NoSalePrintable:\n\n", module);
            NoSalePrintable printArea = new NoSalePrintable();
            printArea.print();
        }
    }

    class NoSalePrintable implements Printable {

        public int print(Graphics g, PageFormat pf, int pageIndex) {
            if (pageIndex != 0) {
                return NO_SUCH_PAGE;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("Serif", Font.PLAIN, 9));
            g2.setPaint(java.awt.Color.black);
            g2.drawString("Open Drawer", 80, 9);

            return PAGE_EXISTS;
        }

        public void print() {

            PrinterJob pj = PrinterJob.getPrinterJob();

            PageFormat pf = pj.defaultPage();
            Paper paper = new Paper();
            double margin = 20;
            paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight());
            pf.setPaper(paper);

            pj.setPrintable(new NoSalePrintable(), pf);
//            if (pj.printDialog()) {
            try {
                Debug.log("NoSalePrintable: printing now", module);
                pj.print();
            } catch (PrinterException pp) {
                System.out.println(pp);
            }
        }
    }

    public void validateData(int i, String s) throws JposException {
    }

}
