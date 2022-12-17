/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import java.util.ArrayList;
import java.util.Iterator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author administrator
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlPosButtonStyleReader extends DefaultHandler {

    PosButtonStyles style;
    private String temp;
    ArrayList<PosButtonStyles> styleList = new ArrayList<PosButtonStyles>();

    @Override
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);

        System.out.println("characters: " + temp);
    }

    @Override
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws SAXException {
        System.out.println("qName: " + qName);
        temp = "";

        if (qName.equalsIgnoreCase(PosButtonStyles.Style)) {
            style = new PosButtonStyles();
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase("name")) {
                    style.setStyleName(temp1);
                }
            }
            styleList.add(style);
        }

        if (qName.equalsIgnoreCase(PosButtonStyles.ColorBack)) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase(PosButtonStyles.Value)) {
                    style.setColorBack(temp1);
                }
            }
        }

        if (qName.equalsIgnoreCase(PosButtonStyles.ColorFore)) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase(PosButtonStyles.Value)) {
                    style.setColorFore(temp1);
                }
            }
        }

        if (qName.equalsIgnoreCase(PosButtonStyles.FontFace)) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase(PosButtonStyles.Value)) {
                    style.setFontFace(temp1);
                }
            }
        }

        if (qName.equalsIgnoreCase(PosButtonStyles.FontItalic)) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase(PosButtonStyles.Value)) {
                    style.setFontItalic(temp1);
                }
            }
        }

        if (qName.equalsIgnoreCase(PosButtonStyles.FontSize)) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase(PosButtonStyles.Value)) {
                    style.setFontSize(temp1);
                }
            }
        }

        if (qName.equalsIgnoreCase(PosButtonStyles.FontWeight)) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase(PosButtonStyles.Value)) {
                    style.setFontWeight(temp1);
                }
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("endElement: " + temp);
    }

    public void readList() {
        System.out.println("Number of cards in the collection: " + styleList.size() + ".\n");
        Iterator<PosButtonStyles> it = styleList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

}
