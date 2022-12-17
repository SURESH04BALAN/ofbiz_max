/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

/**
 *
 * @author siranjeev
 */
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLPageWriter {

    private String configFile;
    protected XmlPosButtonReader engine;

    public void setFile(String configFile) {
        this.configFile = configFile;
    }

    public XMLPageWriter(XmlPosButtonReader engine) {
        this.engine = engine;
    }

    public void saveConfig() throws Exception {
        // create an XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // create XMLEventWriter
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(configFile));
        // create an EventFactory
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);

        eventWriter.add(end);
        // create config open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", "XPage");
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        // create config open tag
        configStartElement = eventFactory.createStartElement("",
                "", "Components");
        eventWriter.add(tab);
        eventWriter.add(configStartElement);
        eventWriter.add(end);

        // Write the different nodes
        for (int i = 0; i < engine.cardList.size(); i++) {
            final String test = engine.cardList.get(i).getTextOne();
            eventWriter.add(tab);
            PosButton pos = engine.cardList.get(i);
//        // create Start node
            Attribute attrName = eventFactory.createAttribute("name", pos.getName());
            Attribute attrX = eventFactory.createAttribute("x", String.valueOf(pos.getX()));
            Attribute attrY = eventFactory.createAttribute("y", String.valueOf(pos.getY()));
            Attribute attrW = eventFactory.createAttribute("w", String.valueOf(pos.getW()));
            Attribute attrH = eventFactory.createAttribute("h", String.valueOf(pos.getH()));
            Attribute attrStyle = eventFactory.createAttribute("style", pos.getStyle());

            Attribute attrContent = eventFactory.createAttribute("content", pos.getContent());
            Attribute attralignment = eventFactory.createAttribute("alignment", pos.getAlignment());
                      
            List attributeList = Arrays.asList(attrName,attrX,attrY,attrW,attrH, attrStyle, attrContent, attralignment );
            List nsList = Arrays.asList();

            createNode(eventWriter, "Button", "", attributeList, nsList);
        }

        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "Components"));
        eventWriter.add(end);

        eventWriter.add(eventFactory.createEndElement("", "", "XPage"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }

    private void createNode(XMLEventWriter eventWriter, String name,
            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);

        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

    private void createNode(XMLEventWriter eventWriter, String name,
            String value, List attributeList, List nsList) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

//        // create Start node
//        Attribute attribute = eventFactory.createAttribute("name", "1");
//        List attributeList = Arrays.asList(attribute);
//        List nsList = Arrays.asList();

        StartElement sElement = eventFactory.createStartElement("", "", name, attributeList.iterator(), nsList.iterator());
        eventWriter.add(tab);
        eventWriter.add(sElement);

        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);

        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

    public static void main(String[] args) {
        XMLPageWriter configFile = new XMLPageWriter(new XmlPosButtonReader());
        configFile.setFile("config2.xml");
        try {
            configFile.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}