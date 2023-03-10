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

package com.openbravo.pos.forms;

import com.openbravo.beans.LocaleResources;
import java.util.logging.Logger;

/**
 *
 * @author adrianromero
 */
public class AppLocal {
    
    public static final String APP_NAME = "Openbravo POS";
    public static final String APP_ID = "openbravopos";
    public static final String APP_VERSION = "2.30.2";
  
    // private static List<ResourceBundle> m_messages;
    private static LocaleResources m_resources;
    /*
    static {
        m_resources = new LocaleResources();
        m_resources.addBundleName("pos_messages");
        m_resources.addBundleName("erp_messages");
    }
    */
    /** Creates a new instance of AppLocal */
 
    public AppLocal() {
                m_resources = new LocaleResources();
        m_resources.addBundleName("pos_messages");
       m_resources.addBundleName("erp_messages");
    }
    
    public static String getIntString(String sKey) {
        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("pos_messages");
            m_resources.addBundleName("erp_messages");
        }
        return m_resources.getString(sKey);
    }
    
    public static String getIntString(String sKey, Object ... sValues) {
        return m_resources.getString(sKey, sValues);
    }
}
