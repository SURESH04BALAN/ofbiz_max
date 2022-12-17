/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.basic.BasicException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.ImageIcon;
import javax.swing.JPanel;
import mvc.controller.LoadAccountWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.party.party.PartyHelper;

/**
 *
 * @author BBS Auctions
 */
public class PosPeopleReport extends NewEntityJasperReport {

    public static final String module = PosPeopleReport.class.getName();

    public PosPeopleReport() {
    }

    @Override
    public void loadParameterSelections() {
        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(getQbffilter(), options, "By Person");
    }

    @Override
    public JPanel runReport() {
        deactivate();
        Debug.logInfo("runreport : " + getCompiledReportPathAndFile(), module);

        setReport(getCompiledReportPathAndFile());
        setResourceBundle(getResourcePathAndFile());
        addConditionList(reportArgs, getEntityConditions());
        try {
            reportArgs.putAll(getValues());
        } catch (BasicException ex) {
            Logger.getLogger(PosPeopleReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        addEntity(reportArgs, "Party");
        //(String) collectionMap.get(EntityName);
        launchreport(reportArgs);
        return this;
    }

    @Override
    public String identifier() {
        return "User Report";
    }

    @Override
    public String getReportCompiledFileName() {
        return "people.jasper";
    }

    @Override
    public String getReportFileName() {
        return "people.jrxml";
    }

    //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\pos\reports\taxes_messages.properties
    @Override
    public String getResourceFileName() {
        return "people_messages.properties";
    }
//report.setResourceBundle("com/openbravo/reports/usersales_messages");

    public class PeopleData {

        private java.lang.String ID;
        java.lang.String NAME;
        private java.lang.String CARD;
        private java.lang.String ROLE;
        //C:\ofbiz\ofbiz-12.04.02\specialpurpose\pos\src\com\openbravo\images\person.png
        private java.lang.Object IMAGE = BaseHelper.loadBufferedImage("\\specialpurpose\\pos\\src\\com\\openbravo\\images\\person.png");
        //com.openbravo.pos.util.BarcodeImage.getBarcodeEAN13("4796005810237");//new ImageIcon(getClass().getResource("/com/openbravo/images/2uparrow.png"));

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public java.lang.String getID() {
            return ID;
        }

        public void setID(java.lang.String ID) {
            this.ID = ID;
        }

        public java.lang.String getCARD() {
            return CARD;
        }

        public void setCARD(java.lang.String CARD) {
            this.CARD = CARD;
        }

        public java.lang.String getROLE() {
            return ROLE;
        }

        public void setROLE(java.lang.String ROLE) {
            this.ROLE = ROLE;
        }

        public java.lang.Object getIMAGE() {
            return IMAGE;
        }

        public void setIMAGE(java.lang.Object IMAGE) {
            this.IMAGE = IMAGE;
        }
    }

    Map<String, PeopleData> dataStore = new HashMap<String, PeopleData>();

    @Override
    public Collection getBeanCollection(Map<String, Object> collectionMap) throws Exception {

        String entityName = (String) collectionMap.get(EntityName);
        Delegator delegator = ControllerOptions.getSession().getDelegator();
        String orderBy = (String) collectionMap.get(OrderByClause);
        List<GenericValue> genList = null;

        List<EntityCondition> entityConditionList = null;
        if (collectionMap.containsKey(EntityConditionList)) {
            entityConditionList = (List<EntityCondition>) collectionMap.get(EntityConditionList);
        }

        String partyId = null;
        if (collectionMap.containsKey("JParamsPartyId")) {
            Map<String, Object> values = (Map<String, Object>) collectionMap.get("JParamsPartyId");
            if (values.containsKey("partyId")) {
                partyId = (String) values.get("partyId");
            }
        }

        Account account = LoadAccountWorker.getAccount(partyId, ControllerOptions.getSession());
        // GenericValue party = PosProductHelper.getParty(partyId, delegator);
        String partyName = PartyHelper.getPartyName(account.getParty().getGenericValueObj(), false);
        PeopleData posCloseData = new PeopleData();
        posCloseData.setID(partyId);
        posCloseData.setNAME(partyName);
        posCloseData.setCARD("4796005810237");
        if (account.getPartyRolesList().getSize() > 0) {
            posCloseData.setROLE(RoleTypeSingleton.getRoleType(account.getPartyRolesList().getElementAt(0).getPartyRole().getroleTypeId()).getdescription());
        }
//        posCloseData.setUNITS(new Double(rand.nextInt(50) + 1));
//        posCloseData.setTOTAL(new Double(100));
        Collection result = new ArrayList<PeopleData>();
        result.add(posCloseData);

        return result;
    }
}
