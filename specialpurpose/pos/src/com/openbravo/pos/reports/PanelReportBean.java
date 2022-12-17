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
package com.openbravo.pos.reports;

/*import com.openbravo.basic.BasicException;
 import com.openbravo.data.loader.BaseSentence;
 import com.openbravo.data.loader.Datas;
 import com.openbravo.data.loader.QBFBuilder;
 import com.openbravo.data.loader.SerializerReadBasic;
 import com.openbravo.data.loader.StaticSentence;*/
import com.openbravo.pos.reports.params.JParamsComposed;
import com.openbravo.pos.reports.params.JParamsCategoryId;
import com.openbravo.pos.reports.params.JParamsProductId;
import com.openbravo.basic.BasicException;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.entity.GoodIdentificationType;
import org.ofbiz.ordermax.report.OrderMaxJRViewer;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author adrianromero
 */
public class PanelReportBean extends JPanelReport implements SimpleScreenInterface {

    public static final String module = PanelReportBean.class.getName();
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
        super.init(app);
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

    @Override
    protected String getReport() {
        return report;
    }

    public void setResourceBundle(String resourcebundle) {
        this.resourcebundle = resourcebundle;
    }

    @Override
    protected String getResourceBundle() {
        return resourcebundle == null
                ? report
                : resourcebundle;
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

    @Override
    public JButton getOkButton() {
        return null;//btnOk;
    }

    @Override
    public JButton getCancelButton() {
        return null; //btnCancel;
    }

    @Override
    public JPanel getPanel() {
        return this;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPanel runReport() {
        launchreport(null);
        return this;
    }

    static public List<EntityCondition> getWhereClauseCond(List<ReportParameterSelectionInterface> filterList) {
        List<EntityCondition> whereClauseMap = new ArrayList<EntityCondition>();
        for (ReportParameterSelectionInterface genPanel : filterList) {
            EntityCondition cond = genPanel.getEntityCondition();
            if (cond != null) {
                Debug.logInfo("cond : " + cond.toString(), module);
                whereClauseMap.add(cond);
            }
        }
        return whereClauseMap;
    }

    public JParamsComposed getQbffilter() {
        return qbffilter;
    }

    public void setQbffilter(JParamsComposed qbffilter) {
        this.qbffilter = qbffilter;
    }

    @Override
    public String identifier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPanel getSelectionPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getShowSelectionPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setShowSelectionPanel(boolean val) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Object> getWhereClause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OrderMaxJRViewer getJRViewer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
