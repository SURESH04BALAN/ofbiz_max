/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.config;

/**
 *
 * @author siranjeev
 */
import com.openbravo.pos.forms.AppConfig;
import org.ofbiz.ordermax.pospaneldesigner.*;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
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
import org.ofbiz.base.container.ContainerConfig;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilURL;
import org.ofbiz.ordermax.base.BaseHelper;

public class XMLPosContainerWriter {

    private String configFile;
    protected AppConfig engine;

    public void setFile(String configFile) {
        this.configFile = configFile;
    }

    public XMLPosContainerWriter(AppConfig engine) {
        this.engine = engine;
        //URL url = UtilURL.fromResource("framework/base/config" + "pos-containers" + ".xml");
        configFile = BaseHelper.getAbsolutePath("\\framework\\base\\config") + "\\pos-containers.xml";
        Debug.logInfo("configFile getPath: " + configFile, "module");
        // Debug.logInfo("configFile getFile: " +  url.getFile(), "module" );        
        //configFile ="";
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
        //  StartElement configStartElement = eventFactory.createStartElement("",
        //          "", "XPage");
        //  eventWriter.add(configStartElement);
        //  eventWriter.add(end);
        // create config open tag
        //   StartElement configStartElement = eventFactory.createStartElement("",
        //           "", "ofbiz-containers");
//        // create Start node
        Attribute startAttrName = eventFactory.createAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        Attribute startClassName = eventFactory.createAttribute("xsi:noNamespaceSchemaLocation", "http://ofbiz.apache.org/dtds/ofbiz-containers.xsd");
        List attributeList1 = Arrays.asList(startClassName, startAttrName); //Arrays.asList(attrName,attrX,attrY,attrW,attrH, attrStyle, attrContent, attralignment );
        List nsList1 = Arrays.asList();
        StartElement configStartElement = eventFactory.createStartElement("",
                "", "ofbiz-containers", attributeList1.iterator(), nsList1.iterator());

        // eventWriter.add(tab);
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        Collection<ContainerConfig.Container> list = engine.getList();

        // Write the different nodes
        for (ContainerConfig.Container container : list) {

            eventWriter.add(tab);

//        // create Start node
            Attribute attrName = eventFactory.createAttribute("name", container.name);
            Attribute className = eventFactory.createAttribute("class", container.className);
            List attributeList = Arrays.asList(attrName, className); //Arrays.asList(attrName,attrX,attrY,attrW,attrH, attrStyle, attrContent, attralignment );
            List nsList = Arrays.asList();
            configStartElement = eventFactory.createStartElement("", "", "container", attributeList.iterator(), nsList.iterator());
            eventWriter.add(tab);
            eventWriter.add(configStartElement);
            eventWriter.add(end);
            for (ContainerConfig.Container.Property key : container.properties.values()) {
                System.out.println("key:" + key.name + " Value= " + key.value);

                Attribute attrNameP = eventFactory.createAttribute("name", key.name);
                Attribute attrValueP = eventFactory.createAttribute("value", key.value);
                attributeList = Arrays.asList(attrNameP, attrValueP); //Arrays.asList(attrName,attrX,attrY,attrW,attrH, attrStyle, attrContent, attralignment );
                nsList = Arrays.asList();

                createNode(eventWriter, "property", "", attributeList, nsList);
//                config.getM_propsconfig().put(key.name, key.value);
            }

            eventWriter.add(tab);
            eventWriter.add(eventFactory.createEndElement("", "", "container"));
            eventWriter.add(end);
        }

        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", "ofbiz-containers"));
        eventWriter.add(end);

        // eventWriter.add(eventFactory.createEndElement("", "", "XPage"));
        // eventWriter.add(end);
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
        XMLPosContainerWriter configFile = new XMLPosContainerWriter(new AppConfig(args));
        configFile.setFile("config2.xml");
        try {
            configFile.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
