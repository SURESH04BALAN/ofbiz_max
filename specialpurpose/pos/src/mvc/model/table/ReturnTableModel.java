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
import java.math.BigDecimal;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.party.PartyListSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ReturnTableModel extends ActionTableModel<ReturnHeaderComposite> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        RETURNID(0, 200, String.class, "Return ID", true),
        EFFECTIVEDATE(1, 150, java.sql.Timestamp.class, "Entry Date", true),
        RETURNPARTYID(2, 300, String.class, "Return From Party", false),
        DESTINATIONFACILITYID(3, 300, String.class, "Destination Facility", false),
        STATUSID(4, 200, String.class, "Status", true);

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

    public ReturnTableModel() {
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
        ReturnHeaderComposite returnHeaderComposite = (ReturnHeaderComposite) listModel.getElementAt(rowIndex);
        ReturnTableModel.Columns[] columns = ReturnTableModel.Columns.values();
        ReturnTableModel.Columns column = columns[columnIndex];
        switch (column) {
            case RETURNID:
                columnValue = returnHeaderComposite.getReturnHeader().getreturnId();
                break;
            case RETURNPARTYID:
                try {
                    if (returnHeaderComposite.getReturnHeader().getfromPartyId() != null) {
                        Account party = PartyListSingleton.getAccount(returnHeaderComposite.getReturnHeader().getfromPartyId());
                        columnValue = party.getDisplayName();
                    } else {
                        columnValue = "";
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ReturnTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case DESTINATIONFACILITYID:
                try {
                    if (UtilValidate.isNotEmpty(returnHeaderComposite.getReturnHeader().getdestinationFacilityId())) {
                        Facility facility = FacilitySingleton.getFacility(returnHeaderComposite.getReturnHeader().getdestinationFacilityId());
                        columnValue = facility.getdescription();
                    } else {
                        columnValue = "";
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ReturnTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case EFFECTIVEDATE:
                columnValue = returnHeaderComposite.getReturnHeader().getentryDate();
                break;

            case STATUSID:
                try {
                    StatusItem statusItem = StatusSingleton.getStatusItem(returnHeaderComposite.getReturnHeader().getstatusId());
                    columnValue = statusItem.getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(ReturnTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
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
