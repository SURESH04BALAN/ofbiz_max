/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.reports.params;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteBasic;
import org.ofbiz.ordermax.generic.EntityFilterEditorCreator;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author BBS Auctions
 */
public abstract class ParamReportEditor extends JPanel implements ReportEditorCreator{

    public ParamReportEditor() {
    }

    @Override
    public void init(AppView app) {
    }

    @Override
    public void activate() throws BasicException {
    }

    @Override
    public SerializerWrite getSerializerWrite() {
        return new SerializerWriteBasic(new Datas[]{Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.TIMESTAMP});
    }

    @Override
    public abstract Component getComponent();

    @Override
    public Object createValue() throws BasicException {
//        Object startdate = partyPickerEditPanel.textIdField.getText();
        //Object enddate = Formats.TIMESTAMP.parseValue(jTxtEndDate.getText());
      //  return new Object[]{startdate == null ? QBFCompareEnum.COMP_NONE : QBFCompareEnum.COMP_GREATEROREQUALS, startdate};
        return null;
    }
    
}
