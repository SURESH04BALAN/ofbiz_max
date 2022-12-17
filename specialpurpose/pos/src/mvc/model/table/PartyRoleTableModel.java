/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.table;

/**
 *
 * @author siranjeev
 */
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
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
public class PartyRoleTableModel extends ActionTableModel<PartyRoleComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        ROLETYPEID(0, 200, String.class, "Role Type Id", false),
        ROLE(1, 200, String.class, "Role", false),
        PARENTTYPEID(2, 200, String.class, "Parent Type Id", false);
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

    public PartyRoleTableModel() {
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
        PartyRoleComposite partyRole = (PartyRoleComposite) listModel.getElementAt(rowIndex);
        RoleType roleType = null;
        try {
            roleType = RoleTypeSingleton.getRoleType(partyRole.getPartyRole().getroleTypeId());
        } catch (Exception ex) {
            Logger.getLogger(PartyRoleTableModel.class.getName()).log(Level.SEVERE, null, ex);
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
            case ROLE:
                columnValue = roleType.getdescription();
                break;
            case PARENTTYPEID:
                columnValue = roleType.getparentTypeId();//order.getorderName();
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
