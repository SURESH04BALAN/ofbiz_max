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

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.entity.BillingAccount;


/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class BillingAccountTableModel extends ActionTableModel<BillingAccount>{

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    public enum Columns {

        BILLINGACCOUNTID(0, 200, String.class, "Billing Account ID", true),
        DESCRIPTION(1, 200, String.class, "Description", false),
        ACCOUNTLIMIT(2, 100, java.math.BigDecimal.class, "Account Limit", false),
        EFFECTIVEDATE(3, 200, java.sql.Timestamp.class, "From Date", true),
        THRUDATE(4, 200, java.sql.Timestamp.class, "Thru Date", true);

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

//"Date 	BillingAccount Nbr 	BillingAccount Name 	BillingAccount Type 	Bill From Party 	Bill To Party 	Product Store 	Amount 	Tracking Code 	Status";
 //   private ListModel<BillingAccount> listModel = new DefaultListModel<BillingAccount>();
   // private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public BillingAccountTableModel() {
    }


    public int getRowCount() {
        return listModel.getSize();
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object columnValue = null;
        BillingAccount billingAccount =  listModel.getElementAt(rowIndex);
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        switch (column) {
            case BILLINGACCOUNTID:
                columnValue = billingAccount.getbillingAccountId();
                break;
            case DESCRIPTION:
                try {
                    columnValue = billingAccount.getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(BillingAccountTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }                
                break;
            case ACCOUNTLIMIT:
                try {
                    columnValue = billingAccount.getaccountLimit();
                } catch (Exception ex) {
                    Logger.getLogger(BillingAccountTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
   
            case EFFECTIVEDATE:
                columnValue = billingAccount.getfromDate();
                break;

            case THRUDATE:
                columnValue = billingAccount.getthruDate();
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
