/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.config;

import org.ofbiz.ordermax.pospaneldesigner.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlPosContainerReader extends DefaultHandler {

    public static class Containers {

        String containerName;
        String containerClass;
        Properties m_propsconfig = new Properties();
    }

    Containers container;
    private String temp;
    ArrayList<Containers> containersList = new ArrayList<Containers>();

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

        if (qName.equalsIgnoreCase("container")) {
            container = new Containers();
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase("name")) {
                    container.containerName = temp1;
                } else if (qAttrName.equalsIgnoreCase("class")) {
                    container.containerClass = temp1;
                }

            }
            containersList.add(container);
        }

        if (container != null) {
            if (qName.equalsIgnoreCase("property")) {
                String name = null;
                String value = null;

                for (int i = 0; i < attributes.getLength(); ++i) {
                    String qAttrName = attributes.getQName(i);
                    String temp1 = attributes.getValue(i);

                    if (qAttrName.equalsIgnoreCase("name")) {
                        name = temp1;
                    } else if (qAttrName.equalsIgnoreCase("value")) {
                        value = temp1;
                    }
                }
                if (name != null) {
                    container.m_propsconfig.put(name, value);
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
        System.out.println("Number of cards in the collection: " + containersList.size() + ".\n");
        Iterator<Containers> it = containersList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().containerName);
            System.out.println(it.next().containerClass);
            System.out.println(it.next().m_propsconfig);
        }
    }

}
