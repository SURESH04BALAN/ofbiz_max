/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.tree.TreePath;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.base.ProductInventoryPojo;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.screens.ContactMechPanelMain;
import org.ofbiz.ordermax.composite.OrderContact;
import org.ofbiz.ordermax.composite.OrderContactList;
import org.ofbiz.ordermax.entity.Geo;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.party.GeoSingleton;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class OrderMaxUtility {

    static public String module = OrderMaxUtility.class.getName();
    //messages
    public static String ITEM_NEW = "ITEM_NEW";
    public static String ITEM_SAVED = "ITEM_SAVED";
    public static String ITEM_MODIFIED = "ITEM_MODIFIED";
    public static String ITEM_STATUS_CHANGED = "ITEM_STATUS_CHANGED";
    public static String PARTY_CHANGED = "PARTY_CHANGED";
    public static String ITEM_CHANGED = "ITEM_CHANGED";

    static public TreeNode GetRecusevilyNodeType(TreeNode parent, TreePath tp, String nodeType) {
        TreeNode child = null;
        if (parent.getNodeName().equals(nodeType)) {
            child = parent;
        }

        if (child == null && tp != null) {
//            TreeNode node = (TreeNode) tp.getLastPathComponent();
//            if (node != null) {

            TreePath parentPath = tp.getParentPath();
            if (parentPath != null) {
                TreeNode currTreeParentNode = (TreeNode) parentPath.getLastPathComponent();
                child = GetRecusevilyNodeType(currTreeParentNode, parentPath, nodeType);
            }

        }
        return child;
    }

    static public String getValidString(String obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    static public String getValidString(BigDecimal obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    static public String getValidTimestamp(Timestamp obj) {
        if (obj != null) {
            return UtilDateTime.timeStampToString(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
        } else {
            return "";
        }
    }

    static public Timestamp getValidTimestamp(String obj) throws ParseException {
        if (obj.isEmpty()) {
            return null;
        } else {
            return UtilDateTime.stringToTimeStamp(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
//            return new UtilDateTime.toDateString(obj,UtilDateTime.DATE_FORMAT);// .toString();Timestamp(obj);
        }
    }

    static public String getValidBigDecimal(BigDecimal obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    static public BigDecimal getValidBigDecimal(String obj) {
        if (obj != null && obj.trim().isEmpty() == false) {
            return new BigDecimal(obj);//.toString();
        } else {
            return null;
        }
    }

    static public String getValidLong(Long obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    static public Long getValidLong(String obj) {
        if (obj != null && obj.trim().isEmpty() == false) {
            return new Long(obj);//.toString();
        } else {
            return null;
        }
    }

    static public String getValidDouble(Double obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    static public Double getValidDouble(String obj) {
        if (obj != null && obj.trim().isEmpty() == false) {
            return new Double(obj);//.toString();
        } else {
            return null;
        }
    }

    static public String getValidDate(java.sql.Date obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    static public java.sql.Date getValidDate(String obj) {
        if (obj.isEmpty()) {
            return null;
        } else {
            return java.sql.Date.valueOf(obj);//UtilDateTime.stringToTimeStamp(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
//            return new UtilDateTime.toDateString(obj,UtilDateTime.DATE_FORMAT);// .toString();Timestamp(obj);
        }
    }

    static public String getValidTime(java.sql.Time obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "";
        }
    }

    static public java.sql.Time getValidTime(String obj) {
        if (obj.isEmpty()) {
            return null;
        } else {
            return java.sql.Time.valueOf(obj);//UtilDateTime.stringToTimeStamp(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
//            return new UtilDateTime.toDateString(obj,UtilDateTime.DATE_FORMAT);// .toString();Timestamp(obj);
        }
    }

    static public String getValidEntityString(String obj) {
        if (obj != null && obj.toString().isEmpty() == false) {
            return obj.toString();
        } else {
            return null;
        }
    }

    public static Timestamp getValidEntityTimestamp(String obj) throws ParseException {
        if (obj == null || obj.isEmpty()) {
            return null;
        } else {
            return UtilDateTime.stringToTimeStamp(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
//            return new UtilDateTime.toDateString(obj,UtilDateTime.DATE_FORMAT);// .toString();Timestamp(obj);
        }
    }

    public static Timestamp getValidEntityTimestamp(Timestamp obj) {
        if (obj == null) {
            return null;
        } else {
            return obj;//UtilDateTime.stringToTimeStamp(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
//            return new UtilDateTime.toDateString(obj,UtilDateTime.DATE_FORMAT);// .toString();Timestamp(obj);
        }
    }

    public static BigDecimal getValidEntityBigDecimal(String obj) {
        if (obj != null && UtilValidate.isNotEmpty(obj)) {
            return new BigDecimal(obj);//.toString();
        } else {
            return null;
        }
    }

    public static BigDecimal getValidEntityBigDecimal(BigDecimal obj) {
        if (obj != null) {
            return obj;//.toString();
        } else {
            return null;
        }
    }

    public static Long getValidEntityLong(Long obj) {
        if (obj != null) {
            return obj;//.toString();
        } else {
            return null;
        }
    }

    public static Long getValidEntityLong(String obj) {
        if (obj != null && obj.toString().isEmpty() == false) {
            return new Long(obj);//.toString();
        } else {
            return null;
        }
    }

    public static Double getValidEntityDouble(String obj) {
        if (obj != null && obj.toString().isEmpty() == false) {
            return new Double(obj);//.toString();
        } else {
            return null;
        }
    }

    public static Double getValidEntityDouble(Double obj) {
        if (obj != null) {
            return new Double(obj);//.toString();
        } else {
            return null;
        }
    }

    public static java.sql.Date getValidEntityDate(java.sql.Date obj) {
        /*        if (obj& obj.toString().isEmpty() == false) {
         return null;
         } else {
         return java.sql.Date.valueOf(obj);//UtilDateTime.stringToTimeStamp(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
         //            return new UtilDateTime.toDateString(obj,UtilDateTime.DATE_FORMAT);// .toString();Timestamp(obj);
         }
         * */
        return obj;
    }

    public static java.sql.Time getValidEntityTime(String obj) {
        if (obj.isEmpty() && obj.toString().isEmpty() == false) {
            return null;
        } else {
            return java.sql.Time.valueOf(obj);//UtilDateTime.stringToTimeStamp(obj, UtilDateTime.DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());// .toString();
//            return new UtilDateTime.toDateString(obj,UtilDateTime.DATE_FORMAT);// .toString();Timestamp(obj);
        }
    }

    public static String getFormatedAddress(final GenericValue postalMech) {
        String address = "";
        if (postalMech != null) {
            StringBuilder orderToStringBuilder = new StringBuilder();
            orderToStringBuilder.append(postalMech.getString("address1") != null ? postalMech.getString("address1") + ", " : "");
            //orderToStringBuilder.append(postalMech.getString("address2") != null ? postalMech.getString("address2") + '\n' : '\n');

            if (postalMech.getString("address2") != null) {
                orderToStringBuilder.append(postalMech.getString("address2")).append('\n');
            } else {
                orderToStringBuilder.append('\n');
            }

            if (postalMech.getString("address3") != null) {
                orderToStringBuilder.append(postalMech.getString("address3")).append('\n');
            }
            // orderToStringBuilder.append('\n');
            orderToStringBuilder.append(postalMech.getString("city") != null ? postalMech.getString("city") + '\n' : '\n');
            String state = "";
            if (postalMech.getString("stateProvinceGeoId") != null) {
                try {
                    Geo geo = GeoSingleton.getGeo(postalMech.getString("stateProvinceGeoId"));
                    state = address.concat(geo.getgeoCode() + " ");
                    if (postalMech.getString("postalCode") != null) {
                        state = state.concat(postalMech.getString("postalCode"));
                    }

                    state = state + '\n';

                } catch (Exception ex) {
                    Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            orderToStringBuilder.append(state);
            if (postalMech.getString("countryGeoId") != null) {
                try {
                    Geo geo = GeoSingleton.getGeo(postalMech.getString("countryGeoId"));
                    state = address.concat(geo.getgeoName());
                } catch (Exception ex) {
                    Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
                orderToStringBuilder.append(state).append('.');
            }
            address = orderToStringBuilder.toString();
            /*address = postalMech.getString("address1") != null ? postalMech.getString("address1") + ", " : "";
             address = postalMech.getString("address2") != null ? address.concat(postalMech.getString("address2") + '\n') : address;
             address = postalMech.getString("address3") != null ? address.concat(postalMech.getString("address3") + '\n') : address;
             address = postalMech.getString("city") != null ? address.concat(postalMech.getString("city") + '\n') : address;
             //        address = postalMech.getString("stateProvinceGeoId") != null ? address.concat(postalMech.getString("stateProvinceGeoId") + '\n') : address;
             //        address = postalMech.getString("countryGeoId") != null ? address.concat(postalMech.getString("countryGeoId") + '\n') : address;
             if (postalMech.getString("stateProvinceGeoId") != null) {
             try {
             Geo geo = GeoSingleton.getGeo(postalMech.getString("stateProvinceGeoId"));
             address = address.concat(geo.getgeoCode() + ", ");
             } catch (Exception ex) {
             Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
             }
             }

             if (postalMech.getString("countryGeoId") != null) {
             try {
             Geo geo = GeoSingleton.getGeo(postalMech.getString("countryGeoId"));
             address = address.concat(geo.getgeoName());
             } catch (Exception ex) {
             Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
             }
             }
             address = postalMech.getString("postalCode") != null ? address.concat(postalMech.getString("postalCode") + '\n') : address;
             */
        }
        return address;
    }

    public static String getFormatedAddress1(final GenericValue postalMech) {

        String address = postalMech.getString("address1") != null ? postalMech.getString("address1") + ", " : "";
        address = postalMech.getString("address2") != null ? address.concat(postalMech.getString("address2")) : address;
        return address;
    }

    public static String getFormatedAddress2(final GenericValue postalMech) {

        String address = postalMech.getString("city") != null ? postalMech.getString("city") + ", " : "";
//        address = 
        if (postalMech.getString("stateProvinceGeoId") != null) {
            try {
                Geo geo = GeoSingleton.getGeo(postalMech.getString("stateProvinceGeoId"));
                address = address.concat(geo.getgeoName() + ", ");
            } catch (Exception ex) {
                Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (postalMech.getString("countryGeoId") != null) {
            try {
                Geo geo = GeoSingleton.getGeo(postalMech.getString("countryGeoId"));
                address = address.concat(geo.getgeoName());
            } catch (Exception ex) {
                Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//        address = postalMech.getString("countryGeoId") != null ? address.concat(postalMech.getString("countryGeoId") + ", ") : address;
        address = postalMech.getString("postalCode") != null ? address.concat(" " + postalMech.getString("postalCode")) : address;

        return address;
    }

    public static String getFormatedTelecom(final GenericValue postalMech) {

        String address = postalMech.getString("countryCode") != null ? postalMech.getString("countryCode") + " " : "";
        address = postalMech.getString("areaCode") != null ? address.concat(postalMech.getString("areaCode") + " ") : address;
        address = postalMech.getString("contactNumber") != null ? address.concat(postalMech.getString("contactNumber") + " ") : address;

        address = address.concat('\n' + "Ask For: ");
        address = postalMech.getString("askForName") != null ? address.concat(postalMech.getString("askForName") + '\n') : address;
        return address;
    }

    public static String getCountryCodeAreaCode(final GenericValue postalMech) {

        String address = postalMech.getString("countryCode") != null ? postalMech.getString("countryCode") + " " : "";
        String areaCode = postalMech.getString("areaCode") != null ? postalMech.getString("areaCode") : "";
        if (areaCode.isEmpty() == false) {
            address = address.concat("(" + areaCode + ")");
        }

        return address;
    }

    public static String getAreadCodeContactNumber(final GenericValue postalMech) {
        String address = getCountryCodeAreaCode(postalMech);
        address = address.concat(" " + getContactNumber(postalMech));
        return address;
    }

    public static String getContactNumber(final GenericValue postalMech) {
        return postalMech.getString("contactNumber") != null ? postalMech.getString("contactNumber") : "";
    }

    public static String getContactName(final GenericValue postalMech) {
        return postalMech.getString("askForName") != null ? postalMech.getString("askForName") : "";
    }

    static public String getOrderBillingContact(OrderContactList orderContactList) {

        StringBuilder orderToStringBuilder = new StringBuilder();

        OrderContact oc = orderContactList.getBillingLocationContact();
        PostalAddress postalAddress = oc.getContact().getPostalAddress();
        if (postalAddress != null) {
            orderToStringBuilder.append("Attn: " + postalAddress.getAttnName());
            orderToStringBuilder.append(postalAddress.getAddress1());
            orderToStringBuilder.append(postalAddress.getAddress2());
            orderToStringBuilder.append(postalAddress.getCity() + ","
                    + postalAddress.getstateProvinceGeoId()
                    + " " + postalAddress.getPostalCode());

            orderToStringBuilder.append(postalAddress.getcountryGeoId());
        }

        OrderContact oc1 = orderContactList.getBillingLocationPhoneContact();

        return orderToStringBuilder.toString();
    }

    public static void addAPanelToPanel(JPanel childPanel, JPanel Container) {

//        JScrollPane scroller = new JScrollPane(childPanel);
//    this.getContentPane().add(scroller, BorderLayout.CENTER);  
        javax.swing.GroupLayout panelTreeContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(panelTreeContainerLayout);
        panelTreeContainerLayout.setHorizontalGroup(
                panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTreeContainerLayout.createSequentialGroup()
                        .addComponent(childPanel) //.addGap(0, 0, 0)
                ));
        panelTreeContainerLayout.setVerticalGroup(
                panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTreeContainerLayout.createSequentialGroup()
                        //.addContainerGap(0, 0)
                        .addComponent(childPanel) //.addGap(0, 0, 0)
                ));

//        panelTreeContainer..addComponent(treePanel);
        //    childPanel.setVisible(true);
        //    Container.setVisible(true);;
    }

    public static void addAPanelGrid(JPanel childPanel, JPanel container) {

        container.setLayout(new java.awt.GridLayout(0, 1, 5, 5));
        container.add(childPanel);
    }

    public static void addAPanelToBorder(JPanel childPanel, JPanel container) {

        container.setLayout(new java.awt.BorderLayout());
        container.add(childPanel);
    }

    public static void addTwoPanelToPanel(JPanel childPanel, JPanel childPanel2, JPanel Container) {

//        JScrollPane scroller = new JScrollPane(childPanel);
//    this.getContentPane().add(scroller, BorderLayout.CENTER);  
        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(Container);

        Container.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(childPanel)
                                .add(childPanel2))
                        .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(childPanel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(childPanel2))
                        .addContainerGap()));
    }

    public static void addThreePanelToPanel(JPanel childPanel, JPanel childPanel2, JPanel childPanel3, JPanel Container) {

//        JScrollPane scroller = new JScrollPane(childPanel);
//    this.getContentPane().add(scroller, BorderLayout.CENTER);  
        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(Container);

        Container.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(childPanel)
                                .add(childPanel2)
                                .add(childPanel3))
                        .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                        .addContainerGap()
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(childPanel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(childPanel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(childPanel3))
                        .addContainerGap()));
    }

    public static List<GenericValue> getCountries(Delegator delegator) {

        GenericValue tmpSupplierProductGV = null;
        GenericValue supplierProductGV = null;
        List<GenericValue> invList = FastList.newInstance();
        try {
            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("geoTypeId", EntityOperator.EQUALS, "COUNTRY"));

            //order by createdStamp
            orderBy.add("createdStamp DESC");
//EntityExpr ee = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("infoString"), EntityOperator.LIKE, EntityFunction.UPPER(("%" + email.toUpperCase()) + "%"));
            invList = delegator.findList("Geo", EntityCondition.makeCondition(exprs, EntityOperator.OR), null, orderBy, null, false);

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", "Error");
        }

        return invList;
    }

    public static List<GenericValue> getStates(Delegator delegator) {

        GenericValue tmpSupplierProductGV = null;
        GenericValue supplierProductGV = null;
        List<GenericValue> invList = FastList.newInstance();
        try {
            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("geoTypeId", EntityOperator.EQUALS, "STATE"));
            exprs.add(EntityCondition.makeCondition("geoTypeId", EntityOperator.EQUALS, "TERRITORY"));
            //order by createdStamp
            orderBy.add("createdStamp DESC");
//EntityExpr ee = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("infoString"), EntityOperator.LIKE, EntityFunction.UPPER(("%" + email.toUpperCase()) + "%"));
            invList = delegator.findList("Geo", EntityCondition.makeCondition(exprs, EntityOperator.OR), null, orderBy, null, false);

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", "Error");
        }

        return invList;
    }

    public static List<GenericValue> getRelationShips(Delegator delegator, String partyId) {

        List<GenericValue> invList = FastList.newInstance();
        try {
            List<EntityExpr> exprs = FastList.newInstance();
            List<String> orderBy = FastList.newInstance();
            exprs.add(EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId));

            //order by createdStamp
//            orderBy.add("createdStamp DESC");
//EntityExpr ee = EntityCondition.makeCondition(EntityFunction.UPPER_FIELD("infoString"), EntityOperator.LIKE, EntityFunction.UPPER(("%" + email.toUpperCase()) + "%"));
            invList = delegator.findList("PartyRelationshipAndDetail", EntityCondition.makeCondition(exprs, EntityOperator.OR), null, null, null, false);

        } catch (GenericEntityException e) {
            Debug.logError("Problem in reading data of inventoryGV", "Error");
        }

        return invList;
    }

    static public boolean createOrStorePerson(GenericValue person, Delegator delegator) {
        boolean result = true;
        String partyId = person.getString("partyId");
        try {
            GenericValue party = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));
            if (party == null) {
                party = delegator.makeValue("Party");
                party.set("partyId", partyId);
                party.set("partyTypeId", "PERSON");
                party.create();
            }
            //store person
            delegator.createOrStore(person);
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    static public boolean createOrStorePartyGroup(GenericValue partyGroup, Delegator delegator) {
        boolean result = true;
        String partyId = partyGroup.getString("partyId");
        try {
            GenericValue party = delegator.findByPrimaryKey("Party", UtilMisc.toMap("partyId", partyId));
            if (party == null) {
                party = delegator.makeValue("Party");
                party.set("partyId", partyId);
                party.set("partyTypeId", "PARTY_GROUP");
                party.create();
            }
            //store person
            delegator.createOrStore(partyGroup);
        } catch (GenericEntityException e) {
            // TODO Auto-generated catch block
            Debug.logError(e, module);
            e.printStackTrace();
            result = false;
        }
        return result;
    }
    /*
     public static Order loadOrderFromPersistance(final String orderId, final Delegator delegator) {

     GenericValue orderGenVal = OrderReadHelper.getOrderHeader(delegator, orderId);
     OrderReadHelper orderReadHelper = new OrderReadHelper(orderGenVal);

     //        Order orderHeader = new Order(orderGenVal, orderReadHelper.getOrderTypeId());
     Order orderHeader = LoadPurchaseOrderWorker.loadOrder(genValue.getString("orderId"), session) ;        
     orderHeader.setDestinationFacilityId(orderGenVal.getString("originFacilityId"));
     Debug.logInfo("orderGenVal.getString(\"originFacilityId\"): " + orderGenVal.getString("originFacilityId"), "module");

     orderHeader.getFinancials().setOrderTotal(orderReadHelper.getOrderGrandTotal());
     orderHeader.setOrderId(orderId);
     GenericValue orderParty = orderReadHelper.getSupplierAgent();
     Debug.logInfo("orderParty.getString(partyId): " + orderParty.getString("partyId"), "module");
     if (orderParty != null) {
     Debug.logInfo("orderParty.getString(partyId): " + orderParty.getString("partyId"), "module");
     //            orderHeader.setPartyId(orderParty.getString("partyId"));
     }

     List<GenericValue> shipGroups = orderReadHelper.getOrderItemShipGroups();
     for (GenericValue shipGroup : shipGroups) {
     orderHeader.getOrderItemShipGroupList().add(new OrderItemShipGroup(shipGroup));
     //            orderHeader.shipGroupSeqId = orderHeader.getOrderItemShipGroupList().get(0).getshipGroupSeqId();
     }

     //load shipment
     List<GenericValue> shipments = PosProductHelper.getGenericValueLists("Shipment", "primaryOrderId", orderId, delegator);
     for (GenericValue shipment : shipments) {
     orderHeader.shipment = new Shipment(shipment);
     break;
     }

     Debug.logInfo("orderHeader.getPartyId(): " + orderHeader.getPartyId(), "module");
     orderHeader.setExternalId(orderReadHelper.getOrderName());
     List<GenericValue> orderItems = orderReadHelper.getOrderItems();
     //        List<GenericValue> orderItemList = billing.getRelated("OrderItemComposite");
     for (GenericValue orderItem : orderItems) {
     Map<String, Object> itemInfo = orderReadHelper.getItemInfoMap(orderItem);
     OrderItemComposite productItem = new OrderItemComposite(orderItem.getString("productId"), orderItem.getString("itemDescription"));
     productItem.getOrderItem().setorderId(orderItem.getString("orderId"));
     Debug.logInfo("orderItem.getString(\"orderItemSeqId\"): " + orderItem.getString("orderItemSeqId"), "module");
     productItem.getOrderItem().setorderItemSeqId(orderItem.getString("orderItemSeqId"));
     productItem.getOrderItem().setquantity(orderItem.getBigDecimal("quantity"));
     productItem.getOrderItem().setunitPrice(orderItem.getBigDecimal("unitPrice"));
     //            productItem.saleLineTotal = orderReadHelper.getOrderItemTotal(orderItem);
     //            productItem.saleGstTotal = orderReadHelper.getOrderItemTax(orderItem);
     try {
     orderHeader.addOrderItem(productItem);
     } catch (CartItemModifyException e) {
     Debug.logError(e, module);
     } catch (ItemNotFoundException e1) {
     Debug.logError(e1, module);
     }
     }

     String purposeId = Order.DELIVERY_LOCATION;
     List<GenericValue> orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
     for (GenericValue genVal : orderContachs) {
     orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
     Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
     }

     purposeId = Order.BILLING_LOCATION;
     orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
     for (GenericValue genVal : orderContachs) {
     orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
     Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
     }
     purposeId = Order.DELIVERY_PHONE;
     orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
     for (GenericValue genVal : orderContachs) {
     orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
     Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
     }

     purposeId = Order.BILLING_EMAIL;
     orderContachs = orderReadHelper.getOrderContactMechs(purposeId);
     for (GenericValue genVal : orderContachs) {
     orderHeader.addContactMechId(purposeId, genVal.getString("contactMechId"));
     Debug.logInfo(" mech: " + genVal.getString("contactMechId"), module);
     }

     return orderHeader;
     }
     */

    public static String createPurchaseOrder(String supplierPartyId, String ownerPartyId, String currencyUomId, String origCurrencyUomId, String destinationFacilityId,
            List<ProductInventoryPojo> productList, String referenceNumber, Delegator delegator)
            throws GenericEntityException {

        String orderId = delegator.getNextSeqId("OrderHeader");
        String orderTypeId = "PURCHASE_ORDER";
        String orderName = "Demo Purchase Order";
        String salesChannelEnumId = "UNKNWN_SALES_CHANNEL";
        Timestamp orderDate = UtilDateTime.nowTimestamp();
        String priority = "2";
        Timestamp entryDate = UtilDateTime.nowTimestamp();
        String statusId = "ORDER_COMPLETED";
        String organizationPartyId = XuiContainer.getSession().getCompanyPartyId();;
        String webSiteId = "OrderEntry";
        BigDecimal remainingSubTotal = new BigDecimal("48.00");
        BigDecimal grandTotal = new BigDecimal("48.00");

        int orderItemSeqIdval = 1;
        String orderItemSeqId = UtilFormatOut.formatPaddedNumber(
                orderItemSeqIdval, 4);
        String shipmentItemSeqId = UtilFormatOut.formatPaddedNumber(
                orderItemSeqIdval, 4);

        // first create all the party roles
        PosProductHelper.createPartyRoles(ownerPartyId, supplierPartyId, delegator);

        GenericValue genVal = PosProductHelper.createOrderHeader(orderId, orderTypeId,
                orderName, salesChannelEnumId, orderDate, priority, entryDate,
                statusId, currencyUomId, webSiteId, remainingSubTotal,
                grandTotal, delegator);

        if (genVal != null) {

            orderId = genVal.getString("orderId");

            // set order status
            PosProductHelper.orderStatus(orderId, delegator);

            // create all the roles for this order
            PosProductHelper.createOrderRoles(ownerPartyId, supplierPartyId, orderId, delegator);
            String contactMechPurposeTypeId = "SHIPPING_LOCATION";
            String contactMechId = "9300";
            PosProductHelper.createOrderContactMech(orderId, contactMechPurposeTypeId,
                    contactMechId, delegator);

            // create ship group
            String shipGroupSeqId = "00001";
            String shipmentMethodTypeId = "NO_SHIPPING";
            String carrierPartyId = "_NA_";
            String carrierRoleTypeId = "CARRIER";
//			contactMechId = "9200";
            String maySplit = "N";
            String isGift = "N";
            Timestamp estimatedDeliveryDate = UtilDateTime.nowTimestamp();

            GenericValue val = PosProductHelper.createOrderItemShipGroup(orderId,
                    shipGroupSeqId, shipmentMethodTypeId, carrierPartyId,
                    carrierRoleTypeId, contactMechId, maySplit, isGift,
                    estimatedDeliveryDate, delegator);

            // create the shipment
            String shipmentId = delegator.getNextSeqId("Shipment");
            String shipmentTypeId = "PURCHASE_SHIPMENT";
            BigDecimal estimatedShipCost = new BigDecimal("0.00");
            String destinationContactMechId = contactMechId;
            String destinationTelecomNumberId = "9301";

            GenericValue val3 = PosProductHelper.createShipment(shipmentId, shipmentTypeId,
                    statusId, orderId, shipGroupSeqId, estimatedShipCost,
                    destinationFacilityId, destinationContactMechId,
                    destinationTelecomNumberId, supplierPartyId, delegator);

            String shipmentRouteSegmentId = "00001";
            // String destFacilityId="WebStoreWarehouse";
//			String destContactMechId = "9200";
//			String destTelecomNumberId = "9201";
            carrierPartyId = "_NA_";
            shipmentMethodTypeId = "NO_SHIPPING";
            String carrierServiceStatusId = "SHRSCS_NOT_STARTED";

            GenericValue val6 = PosProductHelper.createShipmentRouteSegment(shipmentId,
                    shipmentRouteSegmentId, destinationFacilityId,
                    destinationContactMechId, destinationTelecomNumberId, carrierPartyId,
                    shipmentMethodTypeId, carrierServiceStatusId, delegator);

            String shipmentPackageSeqId = "00001";
            Timestamp dateCreated = UtilDateTime.nowTimestamp();
            GenericValue val7 = PosProductHelper.createShipmentPackage(shipmentId,
                    shipmentPackageSeqId, dateCreated, delegator);

            GenericValue val9 = PosProductHelper.createShipmentPackageRouteSeg(shipmentId,
                    shipmentPackageSeqId, shipmentRouteSegmentId, delegator);

            GenericValue val10 = PosProductHelper.createShipmentStatus(shipmentId,
                    "PURCH_SHIP_CREATED", UtilDateTime.nowTimestamp(),
                    delegator);
            GenericValue val11 = PosProductHelper.createShipmentStatus(shipmentId,
                    "PURCH_SHIP_RECEIVED", UtilDateTime.nowTimestamp(),
                    delegator);
            GenericValue val12 = PosProductHelper.createShipmentStatus(shipmentId,
                    "PURCH_SHIP_SHIPPED", UtilDateTime.nowTimestamp(),
                    delegator);

            String acctgTransShipmentId = delegator.getNextSeqId("AcctgTrans");// "9000";
            String acctgTransTypeId = "SHIPMENT_RECEIPT";
            Timestamp transactionDate = UtilDateTime.nowTimestamp();
            String isPosted = "Y";
            Timestamp postedDate = UtilDateTime.nowTimestamp();
            String glFiscalTypeId = "ACTUAL";

            // shipmentId="9997";
            GenericValue acctgTrans = PosProductHelper.createAcctgTrans(acctgTransShipmentId,
                    acctgTransTypeId, transactionDate, isPosted, postedDate,
                    glFiscalTypeId, supplierPartyId, shipmentId, null, null,
                    delegator);

            String invoiceId = delegator.getNextSeqId("Invoice");// "8008";
            String invoiceTypeId = "PURCHASE_INVOICE";
            String description = "Purchase Order Invoice";
            statusId = "INVOICE_READY";
            Timestamp invoiceDate = UtilDateTime.nowTimestamp();

            GenericValue val19 = PosProductHelper.createInvoice(invoiceId, invoiceTypeId,
                    description, supplierPartyId, ownerPartyId, statusId,
                    invoiceDate, currencyUomId, referenceNumber, delegator);
            int transSq = 1;
            for (ProductInventoryPojo entry : productList) {

                String acctgTransEntrySeqId = UtilFormatOut.formatPaddedNumber(transSq++, 4);
                shipmentItemSeqId = UtilFormatOut.formatPaddedNumber(orderItemSeqIdval, 4);

                String invoiceItemSeqId = UtilFormatOut.formatPaddedNumber(orderItemSeqIdval, 4);
                orderItemSeqId = UtilFormatOut.formatPaddedNumber(orderItemSeqIdval++, 4);

                String productId = entry.productId;
                String orderItemTypeId = "PRODUCT_ORDER_ITEM";

                Debug.logInfo(" entry.productId: " + entry.productId, module);

                String prodCatalogId = "DemoCatalog";
                String isPromo = "N";
                BigDecimal quantity = entry.quantity;
                BigDecimal selectedAmount = BigDecimal.ZERO;
                BigDecimal unitPrice = entry.supplierLastPrice;// new BigDecimal("24.00");
                BigDecimal unitListPrice = new BigDecimal("0.00");
                String isModifiedPrice = "N";

                statusId = "ITEM_COMPLETED";
                estimatedDeliveryDate = UtilDateTime.nowTimestamp();

                val = PosProductHelper.createOrderItem(orderId, orderItemSeqId, orderItemTypeId,
                        productId, prodCatalogId, isPromo, quantity,
                        selectedAmount, unitPrice, unitListPrice,
                        isModifiedPrice, entry.productName, statusId,
                        estimatedDeliveryDate, delegator);

                // set the item status
                PosProductHelper.orderItemStatus(orderId, orderItemSeqId, delegator);

                String orderItemPriceInfoId = delegator
                        .getNextSeqId("OrderItemPriceInfo");
                description = entry.productName; // "SupplierProduct [minimumOrderQuantity:0.000000, lastPrice: 24.000]";

                genVal = PosProductHelper.createOrderItemPriceInfo(orderItemPriceInfoId,
                        orderId, orderItemSeqId, description, delegator);

                GenericValue val1 = PosProductHelper.createOrderItemShipGroupAssoc(orderId,
                        orderItemSeqId, shipGroupSeqId, quantity, delegator);

                GenericValue val4 = PosProductHelper.createShipmentItem(shipmentId,
                        shipmentItemSeqId, productId, quantity, delegator);

                GenericValue val8 = PosProductHelper.createShipmentPackageContent(shipmentId,
                        shipmentPackageSeqId, shipmentItemSeqId, quantity,
                        delegator);

                String acctgTransEntryTypeId = "_NA_";

                String roleTypeId = "BILL_FROM_VENDOR";
                String glAccountTypeId = "UNINVOICED_SHIP_RCPT";
                String glAccountId = "214000";
                BigDecimal amount = new BigDecimal(quantity.intValue()).multiply(unitPrice);
                BigDecimal origAmount = new BigDecimal(quantity.intValue()).multiply(unitPrice);

                String debitCreditFlag = "C";
                String reconcileStatusId = "AES_NOT_RECONCILED";

                GenericValue val13 = PosProductHelper.createAcctgTransEntry(
                        acctgTransShipmentId, acctgTransEntrySeqId,
                        acctgTransEntryTypeId, supplierPartyId, roleTypeId,
                        productId, glAccountTypeId, glAccountId,
                        organizationPartyId, amount, currencyUomId, origAmount,
                        origCurrencyUomId, debitCreditFlag, reconcileStatusId,
                        delegator);

                acctgTransEntrySeqId = UtilFormatOut.formatPaddedNumber(transSq++, 4);
                acctgTransEntryTypeId = "_NA_";
                roleTypeId = "BILL_FROM_VENDOR";
                glAccountTypeId = "INVENTORY_ACCOUNT";
                glAccountId = "140000";

                debitCreditFlag = "D";
                reconcileStatusId = "AES_NOT_RECONCILED";

                val13 = PosProductHelper.createAcctgTransEntry(acctgTransShipmentId,
                        acctgTransEntrySeqId, acctgTransEntryTypeId,
                        supplierPartyId, roleTypeId, productId,
                        glAccountTypeId, glAccountId, organizationPartyId,
                        amount, currencyUomId, origAmount, origCurrencyUomId,
                        debitCreditFlag, reconcileStatusId, delegator);

                String inventoryItemId = delegator.getNextSeqId("InventoryItem");

                String inventoryItemTypeId = "NON_SERIAL_INV_ITEM";
                Timestamp datetimeReceived = UtilDateTime.nowTimestamp();
                String locationSeqId = "TLTLTLLL01";
                BigDecimal quantityOnHandTotal = quantity;
                BigDecimal availableToPromiseTotal = quantity;
                BigDecimal unitCost = unitPrice;

                GenericValue val15 = PosProductHelper.createInventoryItem(inventoryItemId,
                        inventoryItemTypeId, productId, ownerPartyId,
                        datetimeReceived, destinationFacilityId, locationSeqId,
                        quantityOnHandTotal, availableToPromiseTotal, unitCost,
                        currencyUomId, delegator);

                String shipmentReceiptId = delegator.getNextSeqId("ShipmentReceipt");// "9000";
                datetimeReceived = UtilDateTime.nowTimestamp();
                BigDecimal quantityAccepted = quantity;
                BigDecimal quantityRejected = new BigDecimal("0.000000");

                GenericValue val16 = PosProductHelper.createShipmentReceipt(shipmentReceiptId,
                        inventoryItemId, productId, shipmentId, orderId,
                        orderItemSeqId, datetimeReceived, quantityAccepted,
                        quantityRejected, delegator);

                String inventoryItemDetailSeqId = "00001";
                Timestamp effectiveDate = UtilDateTime.nowTimestamp();
                BigDecimal quantityOnHandDiff = quantity;
                BigDecimal availableToPromiseDiff = quantity;
                BigDecimal accountingQuantityDiff = quantity;
                unitCost = unitPrice;

                GenericValue val17 = PosProductHelper.createInventoryItemDetail(inventoryItemId,
                        inventoryItemDetailSeqId, effectiveDate,
                        quantityOnHandDiff, availableToPromiseDiff,
                        accountingQuantityDiff, unitCost, orderId,
                        orderItemSeqId, shipmentId, shipmentReceiptId,
                        delegator);

                String itemIssuanceId = delegator.getNextSeqId("ItemIssuance");
                Timestamp issuedDateTime = UtilDateTime.nowTimestamp();

                GenericValue val18 = PosProductHelper.createItemIssuance(itemIssuanceId,
                        orderId, orderItemSeqId, shipGroupSeqId, shipmentId,
                        shipmentItemSeqId, issuedDateTime, quantity, delegator);

                String invoiceItemTypeId = "PINV_FPROD_ITEM";

                amount = unitPrice;

                GenericValue val20 = PosProductHelper.createInvoiceItem(invoiceId,
                        invoiceItemSeqId, invoiceItemTypeId, productId,
                        quantity, amount, description, delegator);

                GenericValue val21 = PosProductHelper.createOrderItemBilling(orderId,
                        orderItemSeqId, invoiceId, invoiceItemSeqId,
                        shipmentReceiptId, quantity, amount, delegator);

                String acctgTransInvoiceId = delegator.getNextSeqId("AcctgTrans");// 9001";
                acctgTransTypeId = "PURCHASE_INVOICE";
                transactionDate = UtilDateTime.nowTimestamp();
                isPosted = "Y";
                postedDate = UtilDateTime.nowTimestamp();
                glFiscalTypeId = "ACTUAL";
                roleTypeId = "BILL_FROM_VENDOR";

                GenericValue val22 = PosProductHelper.createAcctgTrans(acctgTransInvoiceId,
                        acctgTransTypeId, transactionDate, isPosted,
                        postedDate, glFiscalTypeId, supplierPartyId, null,
                        roleTypeId, invoiceId, delegator);

                acctgTransEntrySeqId = "00001";
                acctgTransEntryTypeId = "_NA_";

                roleTypeId = "BILL_FROM_VENDOR";
                glAccountId = "214000";
                debitCreditFlag = "D";
                reconcileStatusId = "AES_NOT_RECONCILED";

                GenericValue val23 = PosProductHelper.createAcctgTransEntry(acctgTransInvoiceId,
                        acctgTransEntrySeqId, acctgTransEntryTypeId,
                        supplierPartyId, roleTypeId, productId,
                        glAccountTypeId, glAccountId, organizationPartyId,
                        amount, currencyUomId, origAmount, origCurrencyUomId,
                        debitCreditFlag, reconcileStatusId, delegator);

                acctgTransEntrySeqId = "00002";
                acctgTransEntryTypeId = "_NA_";
                roleTypeId = "BILL_FROM_VENDOR";
                glAccountTypeId = "ACCOUNTS_PAYABLE";
                glAccountId = "210000";
                debitCreditFlag = "C";
                reconcileStatusId = "AES_NOT_RECONCILED";

                GenericValue val24 = PosProductHelper.createAcctgTransEntry(acctgTransInvoiceId,
                        acctgTransEntrySeqId, acctgTransEntryTypeId,
                        supplierPartyId, roleTypeId, null, glAccountTypeId,
                        glAccountId, organizationPartyId, amount,
                        currencyUomId, origAmount, origCurrencyUomId,
                        debitCreditFlag, reconcileStatusId, delegator);

            }
        }
        return orderId;
    }

    //listType = "postalAddress";
    //contcMechTypeId = ContactMechPanelMain.Location;
    static public String getValidContactMechId(String purposeId, List<Map<String, Object>> partyMechList, String contcMechTypeId, String listType) {
        String validMechId = "";

        for (Map<String, Object> mapList : partyMechList) {
            if (mapList.containsKey("contactMech")) {
                GenericValue contactMech = (GenericValue) mapList.get("contactMech");
                String contactMechTypeId = contactMech.getString("contactMechTypeId");
                if (contactMechTypeId.equals(contcMechTypeId) && mapList.containsKey(listType) && mapList.get(listType) != null) {

                    List<GenericValue> mechPurposes = (List<GenericValue>) mapList.get("partyContactMechPurposes");
                    for (GenericValue mechPurpose : mechPurposes) {
                        String purposeTypeId = mechPurpose.getString("contactMechPurposeTypeId");
                        if (purposeTypeId != null && purposeId.equals(purposeTypeId)) {
                            validMechId = contactMech.getString("contactMechId");
                            break;
                        }
                    }

                }
            }
        }
        return validMechId;
    }

    static public String getValidContactMechIdPurposeTypeId(List<Map<String, Object>> partyMechList, String contcMechTypeId, String listType) {
        String purposeId = "";
        for (Map<String, Object> mapList : partyMechList) {
            if (mapList.containsKey("contactMech")) {
                GenericValue contactMech = (GenericValue) mapList.get("contactMech");
                String contactMechTypeId = contactMech.getString("contactMechTypeId");
//                Debug.logInfo("contactMechTypeId : " + contactMechTypeId, module);
                if (contactMechTypeId.equals(contcMechTypeId)
                        && mapList.containsKey(listType) && mapList.get(listType) != null) {

                    List<GenericValue> mechPurposes = (List<GenericValue>) mapList.get("partyContactMechPurposes");
                    for (GenericValue mechPurpose : mechPurposes) {
                        purposeId = mechPurpose.getString("contactMechPurposeTypeId");
                        break;
                    }

                }
            }
        }
        return purposeId;
    }

    static public String getContachMechDetail(String mechId, List<Map<String, Object>> machList) {
        String mechDetails = "";
        if (mechId != null && mechId.isEmpty() == false) {
            for (Map<String, Object> mapList : machList) {
                if (mapList.containsKey("contactMech")) {
                    GenericValue contactMech = (GenericValue) mapList.get("contactMech");
                    String contactMechTypeId = contactMech.getString("contactMechTypeId");
                    String contactMechId = contactMech.getString("contactMechId");
                    if (contactMechId.equals(mechId)) {

                        if (contactMechTypeId.equals(ContactMechPanelMain.Location) && mapList.containsKey("postalAddress")) {

                            if (mapList.get("postalAddress") != null) {
                                GenericValue postalMech = (GenericValue) mapList.get("postalAddress");
                                mechDetails = OrderMaxUtility.getFormatedAddress(postalMech);
                                break;
                            }
                        } else if (contactMechTypeId.equals(ContactMechPanelMain.Phone) && mapList.containsKey("telecomNumber")) {
                            if (mapList.get("telecomNumber") != null) {
                                GenericValue postalMech = (GenericValue) mapList.get("telecomNumber");
                                mechDetails = OrderMaxUtility.getFormatedTelecom(postalMech);
                                break;
                            }
                        } else if (contactMechTypeId.equals(ContactMechPanelMain.Email)) {

                            mechDetails = contactMech.getString("infoString");
                            break;
                        }
                    }
                }
            }
        }
        return mechDetails;
    }

    static public GenericValue getContachMechObject(String mechId, List<Map<String, Object>> machList) {
        GenericValue mechDetails = null;
        if (mechId != null && mechId.isEmpty() == false) {
            for (Map<String, Object> mapList : machList) {
                if (mapList.containsKey("contactMech")) {
                    GenericValue contactMech = (GenericValue) mapList.get("contactMech");
                    String contactMechTypeId = contactMech.getString("contactMechTypeId");
                    String contactMechId = contactMech.getString("contactMechId");
                    if (contactMechId.equals(mechId)) {

                        if (contactMechTypeId.equals(ContactMechPanelMain.Location) && mapList.containsKey("postalAddress")) {

                            if (mapList.get("postalAddress") != null) {
                                mechDetails = (GenericValue) mapList.get("postalAddress");
                                break;
                            }
                        } else if (contactMechTypeId.equals(ContactMechPanelMain.Phone) && mapList.containsKey("telecomNumber")) {
                            if (mapList.get("telecomNumber") != null) {
                                mechDetails = (GenericValue) mapList.get("telecomNumber");
                                break;
                            }
                        } else if (contactMechTypeId.equals(ContactMechPanelMain.Email)) {

                            mechDetails = contactMech;
                            break;
                        }
                    }
                }
            }
        }
        return mechDetails;
    }

    static public byte[] getImage(String fileName) {
        InputStream is = null;

        try {
            is = new BufferedInputStream(OrderMaxUtility.class.getClassLoader()
                    .getResourceAsStream(fileName));
            byte[] b = new byte[is.available()];
            is.read(b);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public enum ServiceReturnStatus {

        SUCCESS,
        ERROR
    };

    public enum ServiceReturnErrorStatusDisplay {

        SHOWOPTIONPANE,
        THROWEXCPETION,
        LOGERROR,
        IGNOREERROR
    };

    static public ServiceReturnStatus handleServiceReturn(String title, Map<String, Object> resultMap) throws Exception {
        return handleServiceReturn(title, resultMap, ServiceReturnErrorStatusDisplay.SHOWOPTIONPANE);
    }

    static public ServiceReturnStatus handleServiceReturn(String title, Map<String, Object> resultMap, ServiceReturnErrorStatusDisplay stausDisplay) throws Exception {

        ServiceReturnStatus returnStaus = ServiceReturnStatus.SUCCESS;

        Debug.logInfo("Key : 0", module);
        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
            if (entryDept.getValue() != null) {
                Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
            }
        }

        if (resultMap != null && ServiceUtil.isError(resultMap)) {
            //if (resultMap.get("responseMessage").equals("error")) {
            returnStaus = ServiceReturnStatus.ERROR;

            if (stausDisplay.SHOWOPTIONPANE.equals(stausDisplay)) {
                JTextArea msg = new JTextArea();
                msg.setWrapStyleWord(true);
                JScrollPane scrollpane = new JScrollPane();

                msg.setText(ServiceUtil.getErrorMessage(resultMap));

                scrollpane = new JScrollPane(msg);
                scrollpane.setPreferredSize(new Dimension(600, 250));//<-----any size you want
                JPanel panel = new JPanel();
                panel.add(scrollpane);
                scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollpane.getViewport().add(msg);

                int result = OrderMaxOptionPane.showConfirmDialog(null, panel, title,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                try {
                    throw new Exception(ServiceUtil.getErrorMessage(resultMap));
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
            } else if (stausDisplay.THROWEXCPETION.equals(stausDisplay)) {
                throw new Exception(ServiceUtil.getErrorMessage(resultMap));
            } else if (stausDisplay.LOGERROR.equals(stausDisplay)) {
                try {
                    throw new Exception(ServiceUtil.getErrorMessage(resultMap));
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
            } else {
            }

            //}
        }
        Debug.logInfo("Key : 10", module);
        return returnStaus;
    }
}
