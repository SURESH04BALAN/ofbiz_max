//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.beans;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.UtilURL;

/**
 *
 * @author adrian
 */
public class LocaleResources {

    private List<ResourceBundle> m_resources;
//    private ClassLoader m_localeloader;

    /**
     * Creates a new instance of LocaleResources
     */
    public LocaleResources() {
        m_resources = new LinkedList<ResourceBundle>();

//        File fuserdir = new File(System.getProperty("user.dir"));
//        File fresources = new File(fuserdir, "locales");
//        try {
//            m_localeloader = URLClassLoader.newInstance(
//                    new URL[] { fresources.toURI().toURL() },
//                    Thread.currentThread().getContextClassLoader());
//        } catch (MalformedURLException e) {
//            m_localeloader = Thread.currentThread().getContextClassLoader();
//        }        
    }

//    public ResourceBundle getBundle(String bundlename) {
//        return ResourceBundle.getBundle(bundlename, Locale.getDefault(), m_localeloader);
//    }
    private static Logger logger = Logger.getLogger("com.openbravo.pos.forms.StartPOS");

    public void addBundleName(String bundlename) {
        try {
            //File file = new File("C:/jpos/2-30-916ee5e89542/src-pos/locales/" + bundlename + ".properties");
            //URL[] urls = {file.toURI().toURL()};
            //ClassLoader loader = new URLClassLoader(urls);
            //ResourceBundle rb = ResourceBundle.getBundle(bundlename, Locale.getDefault(), loader);
            // configfile.getAbsolutePath()
            File file = getFile(bundlename);
            if (file != null) {
                FileInputStream fis = new FileInputStream(file);
                ResourceBundle resourceBundle = new PropertyResourceBundle(fis);
                m_resources.add(resourceBundle);
            }
        } catch (Exception e) {
            logger.log(Level.OFF, bundlename, e);
        }
//        m_resources.add(getBundle(bundlename));
        //m_resources.add(ResourceBundle.getBundle(bundlename));
    }

    private File getFile(String name) {
        URL url = UtilURL.fromResource("specialpurpose/pos/locales/" + name + ".properties");

        if (url != null) {
            System.out.println("URL is " + url.toString());
            System.out.println("protocol is " + url.getProtocol());
            System.out.println("authority is " + url.getAuthority());
            System.out.println("file name is " + url.getFile());
            System.out.println("host is " + url.getHost());
            System.out.println("path is " + url.getPath());
            System.out.println("port is " + url.getPort());
            System.out.println("default port is " + url.getDefaultPort());
            System.out.println("query is " + url.getQuery());
            System.out.println("ref is " + url.getRef());
            return new File(url.getFile());
        }

        return null;
    }

    static public ResourceBundle getResourceBundle(String bundleNameAndPath) throws Exception {
        try {
            //URL url = UtilURL.fromResource("specialpurpose/pos/locales/" + bundleNameAndPath + ".properties");

            //File file = new File("C:/jpos/2-30-916ee5e89542/src-pos/locales/" + bundleNameAndPath + ".properties");
            //URL[] urls = {file.toURI().toURL()};
            //ClassLoader loader = new URLClassLoader(urls);
            //ResourceBundle rb = ResourceBundle.getBundle(bundlename, Locale.getDefault(), loader);
            FileInputStream fis = new FileInputStream(bundleNameAndPath);
            ResourceBundle resourceBundle = new PropertyResourceBundle(fis);
            return resourceBundle;
        } catch (Exception e) {
            logger.log(Level.OFF, bundleNameAndPath, e);
        }

        throw new Exception("Can't load resourece: " + bundleNameAndPath);
//        m_resources.add(getBundle(bundlename));
        //m_resources.add(ResourceBundle.getBundle(bundlename));
    }

    public String getString(String sKey) {

        if (sKey == null) {
            return null;
        } else {
            for (ResourceBundle r : m_resources) {
                try {
                    return r.getString(sKey);
                } catch (MissingResourceException e) {
                    // Next
                }
            }

            // MissingResourceException in all ResourceBundle
            return "** " + sKey + " **";
        }
    }

    public String getString(String sKey, Object... sValues) {

        if (sKey == null) {
            return null;
        } else {
            for (ResourceBundle r : m_resources) {
                try {
                    return MessageFormat.format(r.getString(sKey), sValues);
                } catch (MissingResourceException e) {
                    // Next
                }
            }

            // MissingResourceException in all ResourceBundle
            StringBuffer sreturn = new StringBuffer();
            sreturn.append("** ");
            sreturn.append(sKey);
            for (Object value : sValues) {
                sreturn.append(" < ");
                sreturn.append(value.toString());
            }
            sreturn.append("** ");

            return sreturn.toString();
        }
    }
}
