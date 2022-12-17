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

import javax.swing.JTable;
import org.ofbiz.ordermax.entity.UserLogin;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class UserLoginTableModel extends ActionTableModel<UserLogin> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;
    final static int defaultWidth = 150;

    static public enum Columns {

        USERLOGINID(0, defaultWidth, String.class, "User Login", true),
        PASSWORDHINT(1, 300, String.class, "Password Hint", false),
        ENABLED(2, 50, String.class, "Enabled", false),
        HASLOGGEDOUT(3, defaultWidth, String.class, "Has Logged Out", false),
        REQUIREPASSWORDCHANGE(4, defaultWidth, String.class, "Require Password Change", false),
        FAILEDLOGGINS(5, defaultWidth, java.math.BigDecimal.class, "Successive Failed Logins", false),
        EXTERNALAUTHID(6, defaultWidth, String.class, "External Auth Id", false),
        THRUDATE(7, defaultWidth, java.sql.Timestamp.class, "Disabled Date Time", false),
        FROMDATETIME(8, defaultWidth, java.sql.Timestamp.class, "Created Date", false);

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

    public UserLoginTableModel() {
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
        UserLogin userLogin = (UserLogin) listModel.getElementAt(rowIndex);

        switch (column) {
            case USERLOGINID:
                columnValue = userLogin.getuserLoginId();
                break;
            case PASSWORDHINT: {

                columnValue = userLogin.getpasswordHint();

                break;
            }
            case ENABLED: {
                columnValue = userLogin.getenabled();
                break;
            }

            case FROMDATETIME:

                columnValue = userLogin.getcreatedStamp();

                break;

            case THRUDATE:
                columnValue = userLogin.getdisabledDateTime();
                break;
            case HASLOGGEDOUT:
                columnValue = userLogin.gethasLoggedOut();
                break;
            case REQUIREPASSWORDCHANGE:
                columnValue = userLogin.getrequirePasswordChange();                
                break;
            case FAILEDLOGGINS:
                columnValue = userLogin.getsuccessiveFailedLogins();                
                break;
            case EXTERNALAUTHID:
                columnValue = userLogin.getexternalAuthId();                
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
