/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import static java.awt.SystemColor.text;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author administrator
 */
public class PosPageCreateClass {

    static public String xmlFile_Header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<XPage class=\"org.ofbiz.pos.screen.PosScreen\">"
            + "<Components>"
            + "<Panel name=\"mainPanel\" x=\"0\" y=\"0\" w=\"1024\" h=\"768\" style=\"input\" border=\"0\">"
            + "<Panel name=\"inputPanel\" x=\"0\" y=\"0\" w=\"1024\" h=\"48\" style=\"input\" border=\"0\">"
            + "<Include file=\"default/includes/posinput\"/>"
            + "</Panel>"
            + "<Panel name=\"journalPanel\" x=\"0\" y=\"48\" w=\"412\" h=\"700\" style=\"menu\">"
            + "<Include file=\"default/includes/journal\"/>"
            + "</Panel>";

    static public String xmlFile_Detail_panel = "<Panel name=\"menuPanel\" x=\"412\" y=\"48\" w=\"612\" h=\"407\" style=\"menu\">";
    static public String xmlFile_Detail = "<Include file=\"default/menu/";

    static public String xmlFile_Detail_end = "\"/>";
    static public String xmlFile_Detail_panel_end = "</Panel>";

    static public String xmlFile_footer
            = "<Panel name=\"numericPanel\" x=\"412\" y=\"455\" w=\"612\" h=\"290\" style=\"menu\">"
            + "<Include file=\"default/includes/numeric\"/>"
            + "</Panel>"
            + "<Panel name=\"statusPanel\" x=\"0\" y=\"744\" w=\"1024\" h=\"20\" style=\"operTitle\" border=\"0\">"
            + "<Include file=\"default/includes/promostatusbar\"/>"
            + "</Panel>"
            + "</Panel>"
            + "</Components>"
            + "</XPage>";
    String menuName;
    String filePath;
    public PosPageCreateClass(String path, String pageFileName, String menuName) {
        this.menuName = menuName;
        filePath = path +"\\" + pageFileName;
         System.out.println(filePath);
    }

    public void savePageFile() {
        PrintStream out = null;
        try {
            String xmlFile =   xmlFile_Header + 
                    xmlFile_Detail_panel + 
                    xmlFile_Detail + 
                    menuName +
                    xmlFile_Detail_end +
                    xmlFile_Detail_panel_end +                     
                    xmlFile_footer;
            System.out.println(xmlFile);                    
            out = new PrintStream(new FileOutputStream(filePath));
            out.print(xmlFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PosPageCreateClass.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
