/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.pospaneldesigner;

import java.util.ArrayList;

/**
 *
 * @author administrator
 */
public interface ButtonDesignPanelInterface {

    void getPanelFields(PosButton pos);

    void setPanelFields(PosButton pos);
    void setStyles(ArrayList<PosButtonStyles> list);
    void setSelectionData(String id, String name);
}
