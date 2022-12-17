/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.reports.params;

import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppView;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

/**
 *
 * @author BBS Auctions
 */
public class JParamFormGeneric extends ParamReportEditor {

    public List<ReportParameterSelectionInterface> filterList = new ArrayList<ReportParameterSelectionInterface>();
    @Override
    public void init(AppView app) {
        this.setLayout(null);
    }
    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public EntityCondition getEntityCondition() {
        return JParamHelper.getEntityCondition(filterList);
    }

    @Override
    public Map<String, Object> getValues() throws BasicException {
        return JParamHelper.getValuesMap(filterList);
    }
    
    public String getEditorClassName(){
        return "JParamFormGeneric";
    }
}
