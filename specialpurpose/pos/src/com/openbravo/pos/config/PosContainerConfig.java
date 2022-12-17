/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.config;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author BBS Auctions
 */
public class PosContainerConfig {

    XmlPosContainerReader styleReader = new XmlPosContainerReader();

    public void loadContainerConfig(String filePath) {
        try {

            SAXParserFactory spfac1 = SAXParserFactory.newInstance();
            SAXParser sp1 = spfac1.newSAXParser();
            try {
                sp1.parse(new File(filePath), styleReader);
                styleReader.readList();

            } catch (IOException ex) {
                //  Logger.getLogger(SystemButtonDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParserConfigurationException ex) {
            // Logger.getLogger(SystemButtonDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            //Logger.getLogger(SystemButtonDesignPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
