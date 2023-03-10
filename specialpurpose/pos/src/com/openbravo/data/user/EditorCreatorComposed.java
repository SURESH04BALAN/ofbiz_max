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

package com.openbravo.data.user;

import com.openbravo.basic.BasicException;
import org.ofbiz.entity.condition.EntityCondition;

public class EditorCreatorComposed implements EditorCreator{
    
    private EditorCreator[] m_editors;
    
    /** Creates a new instance of EditorCreatorComposed */
    public EditorCreatorComposed(EditorCreator... editors) {
        m_editors = editors;
    }

    public Object createValue() throws BasicException {
        
        Object[] value = new Object[m_editors.length];
        for (int i = 0; i < m_editors.length; i++) {
            value[i] = m_editors[i].createValue();
        }
        
        return value;
    } 

    /*@Override
    public EntityCondition getFilterByExpr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
