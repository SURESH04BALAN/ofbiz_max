/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlPosButtonEventReader extends DefaultHandler {

    PosButtonEvent event;
    private String temp;
    ArrayList<PosButtonEvent> cardList = new ArrayList<PosButtonEvent>();

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

        if (qName.equalsIgnoreCase("event")) {
            event = new PosButtonEvent();
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase("button-name")) {
                    event.setButtonName(temp1);
                } else if (qAttrName.equalsIgnoreCase("class-name")) {
                    event.setClassName(temp1);
                } else if (qAttrName.equalsIgnoreCase("method-name")) {
                    event.setMethodName(temp1);
                } else if (qAttrName.equalsIgnoreCase("disable-lock")) {
                    event.setDisableLock(temp1);
                }

            }
            cardList.add(event);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("endElement: " + temp);
    }

    public void readList() {
        System.out.println("Number of cards in the collection: " + cardList.size() + ".\n");
        Iterator<PosButtonEvent> it = cardList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

}
