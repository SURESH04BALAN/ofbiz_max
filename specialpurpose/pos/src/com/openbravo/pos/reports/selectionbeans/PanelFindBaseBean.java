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
package com.openbravo.pos.reports.selectionbeans;

/*import com.openbravo.basic.BasicException;
 import com.openbravo.data.loader.BaseSentence;
 import com.openbravo.data.loader.Datas;
 import com.openbravo.data.loader.QBFBuilder;
 import com.openbravo.data.loader.SerializerReadBasic;
 import com.openbravo.data.loader.StaticSentence;*/
import com.openbravo.pos.reports.params.JParamsComposed;
import com.openbravo.pos.reports.*;
import com.openbravo.basic.BasicException;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

/**
 *
 * @author adrianromero
 */
public class PanelFindBaseBean extends JPanelFind  {

    public static final String module = PanelFindBaseBean.class.getName();
    private String title;
    private String report;

    private String resourcebundle = null;

    private String sentence;

//su    private List<Datas> fielddatas = new ArrayList<Datas>();
    private List<String> fieldnames = new ArrayList<String>();

    private List<String> paramnames = new ArrayList<String>();

    private JParamsComposed qbffilter = new JParamsComposed();

    @Override
    public void init(AppView app) throws BeanFactoryException {
        qbffilter.init(app);
        super.init();
    }


    @Override
    public void activate() throws BasicException {

        qbffilter.activate();
        super.activate();

        if (qbffilter.isEmpty()) {
            setVisibleFilter(false);
            setVisibleButtonFilter(false);
        }
    }
    
    @Override
    public JComponent getComponent() {
        return this;
    }
    
    @Override
    protected EditorCreator getEditorCreator() {

        return qbffilter;
    }

    public List<EntityCondition> getEntityConditions() {
        return qbffilter.getEntityConditions();
    }

    public Map<String, Object> getValues() throws BasicException {
        return qbffilter.getValues();

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleKey(String titlekey) {
        title = AppLocal.getIntString(titlekey);
    }

    public String getTitle() {
        return title;
    }

    public void setReport(String report) {
        this.report = report;
    }


    public void setResourceBundle(String resourcebundle) {
        this.resourcebundle = resourcebundle;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
    /*su  
     public void addField(String name, Datas data) {
     fieldnames.add(name);
     fielddatas.add(data);
     }
     */

    public void addParameter(String name) {
        paramnames.add(name);
    }
    /*su  
     protected BaseSentence getSentence() {
     return new StaticSentence(m_App.getSession()
     , new QBFBuilder(sentence, paramnames.toArray(new String[paramnames.size()]))
     , qbffilter.getSerializerWrite()
     , new SerializerReadBasic(fielddatas.toArray(new Datas[fielddatas.size()])));
     }
     */

    protected ReportFields getReportFields() {
        return new ReportFieldsArray(fieldnames.toArray(new String[fieldnames.size()]));
    }

    public void addQBFFilter(ReportEditorCreator qbff) {
        qbffilter.addEditor(qbff);
    }

    public JParamsComposed getQbffilter() {
        return qbffilter;
    }

    public void setQbffilter(JParamsComposed qbffilter) {
        this.qbffilter = qbffilter;
    }

}
