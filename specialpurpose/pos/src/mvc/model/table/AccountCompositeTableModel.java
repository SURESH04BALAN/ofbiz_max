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
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.PartyRolesList;
import org.ofbiz.ordermax.entity.UserLogin;
import org.ofbiz.ordermax.party.UserLoginSingleton;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class AccountCompositeTableModel extends ActionTableModel<Account>  {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        PARTYID(0, 75, String.class, "PARTY ID", true),
        USERLOGIN(1, 75, String.class, "USER LOGIN", false),
        NAME(2, 200, String.class, "NAME", false),
        RELATEDCOMPANY(3, 150, String.class, "RELATED COMPANY", false),
        TYPE(4, 150, String.class, "TYPE", false),
        MAINROLE(5, 150, String.class, "MAIN ROLE", false),
        CREATEDDATE(6, 150, java.sql.Timestamp.class, "CREATED DATE", false),
        LASTMODIFIEDDATE(7, 150, java.sql.Timestamp.class, "LAST MODIFIED DATE", false);

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

    //private ListModel<Account> listModel = new DefaultListModel<Account>();
    //private ListModelChangeListener listModelChangeListener = new ListModelChangeListener();

    public AccountCompositeTableModel() {
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
        Columns[] columns = Columns.values();
        Columns column = columns[columnIndex];
        Account productComposite = (Account) listModel.getElementAt(rowIndex);
        switch (column) {
            case PARTYID:
                columnValue = productComposite.getParty().getpartyId();
                break;
            case USERLOGIN:
                try {
                    columnValue = "";
                   UserLogin user = UserLoginSingleton.getUserLoginFromPartyId(productComposite.getParty().getpartyId());
                   if(user!=null){
                       columnValue = user.getuserLoginId();
                   }
                   else{
                       columnValue = "(none)";
                   }

                } catch (Exception ex) {
                    columnValue = "";
                    Logger.getLogger(AccountCompositeTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case NAME:
                columnValue = productComposite.getDisplayName();
                break;

            case RELATEDCOMPANY:

                columnValue = "";

                break;
            case TYPE:
                columnValue = productComposite.getParty().getpartyTypeId();
                break;
            case MAINROLE:
                PartyRolesList partyRolesList = productComposite.getPartyRolesList();
                if (partyRolesList!=null && partyRolesList.getSize() > 0) {
                    try {
                        columnValue = RoleTypeSingleton.getRoleType(partyRolesList.getElementAt(0).getPartyRole().getroleTypeId()).getdescription();
                    } catch (Exception ex) {
                        Logger.getLogger(AccountCompositeTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    columnValue = "";
                }
                break;
            case CREATEDDATE:
                columnValue = productComposite.getParty().getcreatedDate();
                break;
            case LASTMODIFIEDDATE:
                columnValue = productComposite.getParty().getlastModifiedDate();
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
