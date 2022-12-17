/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.shoppinglist;

/**
 *
 * @author siranjeev
 */
import mvc.model.table.*;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.ShoppingList;
import org.ofbiz.ordermax.entity.ShoppingListType;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.product.productstore.ProductStoreSingleton;

/**
 * A {@link PersonTableModel} adds a table perspective on a {@link PersonsModel}
 * to adapt it to a {@link JTable}. Therefore it listens to updates of the
 * {@link PersonsModel} by implementing the {@link Observer} interface.
 *
 * @author rene.link
 *
 */
public class ShoppingListTableModel extends ActionTableModel<ShoppingList> {

    /**
     *
     */
    private static final long serialVersionUID = 1547542546403627396L;

    static public enum Columns {

        SHOPPINGLISTID(0, 150, String.class, "Shopping List Id", true),
        SHOPPINGLISTTYPEID(1, 150, String.class, "Shopping List Type Id", false),
        PARTYID(2, 200, String.class, "Party Id", true),
        DESCRIPTION(3, 200, String.class, "Description", false),
        LASTUPDATED(4, 150, java.sql.Timestamp.class, "Entry Date", true),
        PRODUCTSTOREID(5, 150, String.class, "Product Store", false),
        LISTNAME(6, 70, String.class, "List Name", true),
        ISPUBLIC(7, 150, String.class, "Is Public", true),
        ISACTIVE(8, 150, String.class, "Is Active", true);

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

    public ShoppingListTableModel() {
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

        ShoppingList shoppingList = listModel.getElementAt(rowIndex);
        ShoppingListTableModel.Columns[] columns = ShoppingListTableModel.Columns.values();
        ShoppingListTableModel.Columns column = columns[columnIndex];
        switch (column) {
            case SHOPPINGLISTID:
                Debug.logInfo("shoppingList.getshoppingListId() " + shoppingList.getshoppingListId(), " this");                
                columnValue = shoppingList.getshoppingListId();
                break;
            case PARTYID:
                try {
                    if (shoppingList.getpartyId() != null) {
                        Account party = PartyListSingleton.getAccount(shoppingList.getpartyId());
                        columnValue = party.getDisplayName();
                    } else {
                        columnValue = "";
                    }
                } catch (Exception ex) {
//                    Logger.getLogger(ShoppingListTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case PRODUCTSTOREID:
                try {
                    if (UtilValidate.isNotEmpty(shoppingList.getproductStoreId())) {
                        ProductStore facility = ProductStoreSingleton.getProductStore(shoppingList.getproductStoreId());
                        columnValue = facility.getstoreName();
                    } else {
                        columnValue = "";
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ShoppingListTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case LASTUPDATED:
                columnValue = shoppingList.getlastUpdatedStamp();
                break;
            case DESCRIPTION:
                columnValue = shoppingList.getdescription();
                break;

            case SHOPPINGLISTTYPEID:
                try {
                    ShoppingListType shoppingListType = ShoppingListTypeSingleton.getShoppingListType(shoppingList.getshoppingListTypeId());
                    columnValue = shoppingListType.getdescription();
                } catch (Exception ex) {
                    Logger.getLogger(ShoppingListTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case LISTNAME:
                columnValue = shoppingList.getlistName();
                break;
            case ISPUBLIC:
                columnValue = shoppingList.getisPublic();
                break;
            case ISACTIVE:
                columnValue = shoppingList.getisActive();
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
    
    public ShoppingList getRowGenericData(int row) {
        ShoppingList shoppingList = listModel.getElementAt(row);
        return shoppingList;
    }
}
