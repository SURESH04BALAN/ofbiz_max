/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.billingaccount;

/**
 *
 * @author siranjeev
 */
import mvc.model.table.*;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
import org.ofbiz.ordermax.entity.BillingAccountRole;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class BillingAccountRoleTableModel extends ActionTableModel<BillingAccountRole> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        PARTYID(0, 200, String.class, "Party Id", false),
        ROLETYPEID(1, 200, String.class, "Role Type Id", false),
        FROMDATE(2, 200, String.class, "From Date", false),
        THRUDATE(2, 200, String.class, "Thru Date", false);
//        BALANCEAMOUNT(8, 75, BigDecimal.class, "Balance Amount", false),        

        private int columnIndex;
        private int columnWidth;

        public String getHeaderString() {
            return headerString;
        }

        public void setHeaderString(String headerString) {
            this.headerString = headerString;
        }

        public Class getClassName() {
            return className;
        }

        public void setClassName(Class className) {
            this.className = className;
        }

        public boolean isIsEditable() {
            return isEditable;
        }

        public void setIsEditable(boolean isEditable) {
            this.isEditable = isEditable;
        }
        private String headerString;
        private Class className;
        private boolean isEditable;

        Columns(int index, int width, Class className, String header, boolean edit) {
            columnIndex = index;
            columnWidth = width;
            headerString = header;
            this.className = className;
            isEditable = edit;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public int getColumnWidth() {
            return columnWidth;
        }
    }

    public BillingAccountRoleTableModel() {
    }

    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public boolean isCellEditable(int row, int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.isEditable;

    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        BillingAccountRole billingAccountRole = (BillingAccountRole) listModel.getElementAt(rowIndex);
        RoleType roleType = null;
        try {
            roleType = RoleTypeSingleton.getRoleType(billingAccountRole.getroleTypeId());
        } catch (Exception ex) {
            Logger.getLogger(BillingAccountRoleTableModel.class.getName()).log(Level.SEVERE, null, ex);
            columnValue = "";
            return columnValue;
        }
        
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
//DATE, ORDERNBR,	ORDERNAME, ORDERTYPE, BILLFROMPARTY, BILLTOPARTY, PRODUCTSTORE,	AMOUNT, TRACKINGCODE, 	STATUS
        switch (column) {
            case ROLETYPEID:
                columnValue = roleType.getroleTypeId();
                break;
            case PARTYID:
                columnValue = billingAccountRole.getpartyId();
                break;
            case FROMDATE:
                columnValue = billingAccountRole.getfromDate();//order.getorderName();
                break;
            case THRUDATE:
                columnValue = billingAccountRole.getthruDate();//order.getorderName();
                break;                
            default:
                break;
        }

        return columnValue;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.className;
    }

    @Override
    public String getColumnName(int columnIndex) {
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        return column.headerString;
    }

}
