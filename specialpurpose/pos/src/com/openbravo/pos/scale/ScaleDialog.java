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
package com.openbravo.pos.scale;

import com.openbravo.beans.JNumberDialog;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.Utility;
import java.awt.Component;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author adrian
 */
public class ScaleDialog implements Scale {

    private Component parent;

    public ScaleDialog(Component parent) {
        this.parent = parent;
    }

    @Override
    public Double readWeight() throws ScaleException {
        /*try {
            throw new Exception("resize");
        } catch (Exception e) {
            Debug.logError(e, "name");
        }*/
        // Set title for grams Kilos, ounzes, pounds, ...C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\images /com/openbravo/images/ark2.png 
        return JNumberDialog.showEditNumber(parent, "Scale"/*AppLocal.getIntString("label.scale")*/, "Scale Input"/*AppLocal.getIntString("label.scaleinput")*/, Utility.loadImage("/com/openbravo/images/ark2.png"));
    }
}
