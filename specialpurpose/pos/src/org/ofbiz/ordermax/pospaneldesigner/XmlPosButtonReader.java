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

public class XmlPosButtonReader extends DefaultHandler {

    PosButton card;
    private String temp;
    ArrayList<PosButton> cardList = new ArrayList<PosButton>();

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

        if (qName.equalsIgnoreCase("Card")) {
            card = new PosButton();
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase("Name")) {
                    card.setName(temp1);
                } else if (qAttrName.equalsIgnoreCase("y")) {
                    if (temp1 == null || temp1.isEmpty()) {
                        temp1 = "0";
                    }
                    card.setY(Integer.parseInt(temp1));
                } else if (qAttrName.equalsIgnoreCase("x")) {
                    if (temp1 == null || temp1.isEmpty()) {
                        temp1 = "0";
                    }
                    card.setX(Integer.parseInt(temp1));
                } else if (qAttrName.equalsIgnoreCase("style")) {
                    card.setStyle(temp1);
                }
            }
        }
        
        if (qName.equalsIgnoreCase("Button")) {
            card = new PosButton();
            for (int i = 0; i < attributes.getLength(); ++i) {
                String qAttrName = attributes.getQName(i);
                String temp1 = attributes.getValue(i);
                if (qAttrName.equalsIgnoreCase("Name")) {
                    card.setName(temp1);
                } else if (qAttrName.equalsIgnoreCase("y")) {
                    if (temp1 == null || temp1.isEmpty()) {
                        temp1 = "0";
                    }
                    card.setY(Integer.parseInt(temp1));
                } else if (qAttrName.equalsIgnoreCase("x")) {
                    if (temp1 == null || temp1.isEmpty()) {
                        temp1 = "0";
                    }
                    card.setX(Integer.parseInt(temp1));
                } else if (qAttrName.equalsIgnoreCase("style")) {
                    card.setStyle(temp1);
                } else if (qAttrName.equalsIgnoreCase("h")) {
                    if (temp1 == null || temp1.isEmpty()) {
                        temp1 = "0";
                    }
                    card.setH(Integer.parseInt(temp1));
                } else if (qAttrName.equalsIgnoreCase("w")) {
                    if (temp1 == null || temp1.isEmpty()) {
                        temp1 = "0";
                    }
                    card.setW(Integer.parseInt(temp1));
                }else if (qAttrName.equalsIgnoreCase("alignment")) {
                    card.setAlignment(temp1);
                }else if (qAttrName.equalsIgnoreCase("content")) {
                    card.setContent(temp1);
                }
                
            }
            cardList.add(card);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("endElement: " + temp);
        if (qName.equalsIgnoreCase("Card")) {
            cardList.add(card);
        } else if (qName.equalsIgnoreCase("Word")) {
            card.setWord(temp);
        } else if (qName.equalsIgnoreCase("Translation")) {
            card.setTranslation(temp);
        } else if (qName.equalsIgnoreCase("TextOne")) {
            card.setTextOne(temp);
        } else if (qName.equalsIgnoreCase("TextTwo")) {
            card.setId(temp);
        } else if (qName.equalsIgnoreCase("Name")) {
            card.setName(temp);
//        } else if (qName.equalsIgnoreCase("y")) {
//            card.setY(temp);
//        } else if (qName.equalsIgnoreCase("x")) {
//            card.setX(temp);
        } else if (qName.equalsIgnoreCase("style")) {
            card.setStyle(temp);
        }
    }

    public void readList() {
        System.out.println("Number of cards in the collection: " + cardList.size() + ".\n");
        Iterator<PosButton> it = cardList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    public void shuffleList(int x) {
        System.out.println("Shuffled cards order (" + x + "): ");
        Collections.shuffle(cardList);
        Iterator<PosButton> it = cardList.iterator();
        while (it.hasNext()) {
            System.out.print(it.next().shuffledList());
        }
        System.out.println("\n");
    }
}
