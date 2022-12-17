/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import java.awt.Component;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

/**
 *
 * @author BBS Auctions
 */
public class FindSingleDateSelectionPanel extends DatePickerEditPanel implements ReportCreatorSelectionInterface {

    public String startDateId;

    public FindSingleDateSelectionPanel(String startDateId) {
        this.startDateId = startDateId;
    }

    @Override
    public String getEntityId() {
        return startDateId;
    }

    @Override
    public Object getEntityValue() {
        try {
            return getTimeStamp();
        } catch (Exception ex) {
            Logger.getLogger(FindSingleDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void getValueMap(Map<String, Object> valueMap) {
        
        Timestamp fromDate = null;
        try {
            fromDate = this.getTimeStamp();
            valueMap.put(startDateId, fromDate);
        } catch (Exception ex) {
            Logger.getLogger(FindDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    @Override
    public EntityCondition getEntityCondition() {
        Timestamp fromDate = null;
        try {
            fromDate = this.getTimeStamp();
        } catch (Exception ex) {
            Logger.getLogger(FindDateSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return EntityCondition.makeCondition(startDateId, EntityOperator.NOT_EQUAL, fromDate);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void setEntityValue(Object value) {
        setDate((java.sql.Timestamp) value);
    }

}
