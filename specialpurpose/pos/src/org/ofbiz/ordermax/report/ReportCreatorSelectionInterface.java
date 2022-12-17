/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report;

import org.ofbiz.ordermax.generic.EntityFilterEditorCreator;
import java.awt.Component;
import java.util.Map;
import org.ofbiz.entity.condition.EntityCondition;

/**
 *
 * @author BBS Auctions
 */
public interface ReportCreatorSelectionInterface extends ReportParameterSelectionInterface {

    public Component getComponent();
    public void setEntityValue(java.lang.Object value);    
}
