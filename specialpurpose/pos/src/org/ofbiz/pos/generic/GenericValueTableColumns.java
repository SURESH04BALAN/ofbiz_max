/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.generic;

/**
 *
 * @author siranjeev
 */
public class GenericValueTableColumns {
    /*    
    final static int ID_COL_SIZE = 50;
    final static int DESC_COL_SIZE = 150;
    final static int DATE_COL_SIZE = 50;
    final static int AMOUNT_COL_SIZE = 50;
    final static int CURRENCY_COL_SIZE = 25;
    final static int INTEGER_COL_SIZE = 40;
    final static int invoiceId = ID_COL_SIZE;
    final static int invoiceTypeId = ID_COL_SIZE;
    final static int partyIdFrom = ID_COL_SIZE;
    final static int partyId = ID_COL_SIZE;
    final static int roleTypeId = ID_COL_SIZE;
    final static int statusId = ID_COL_SIZE;
    final static int billingAccountId = ID_COL_SIZE;
    final static int contactMechId = ID_COL_SIZE;
    final static int invoiceDate = DATE_COL_SIZE;
    final static int dueDate = DATE_COL_SIZE;
    final static int paidDate = DATE_COL_SIZE;
    final static int invoiceMessage = DESC_COL_SIZE;
    final static int referenceNumber = ID_COL_SIZE;
    final static int description = DESC_COL_SIZE;
    final static int currencyUomId = CURRENCY_COL_SIZE;
    final static int recurrenceInfoId = ID_COL_SIZE;
    final static int lastUpdatedStamp = DATE_COL_SIZE;
    final static int lastUpdatedTxStamp = DATE_COL_SIZE;
    final static int createdStamp = DATE_COL_SIZE;
    final static int createdTxStamp = DATE_COL_SIZE;
    final static int orderId = ID_COL_SIZE;
    final static int orderTypeId = ID_COL_SIZE;
    final static int productId = ID_COL_SIZE;
    final static int itemDescription = DESC_COL_SIZE;
    final static int quantity = INTEGER_COL_SIZE;
    final static int unitPrice = AMOUNT_COL_SIZE;
    final static String Default_Cell_Rendere = "DEFAULT";
    static public ColumnDetails[] GenericColumnName = new ColumnDetails[]{
        new ColumnDetails("invoiceId", "invoiceId", "java.lang.String", new Integer(invoiceId), Default_Cell_Rendere),
        new ColumnDetails("invoiceTypeId", "invoiceTypeId", "java.lang.String", new Integer(invoiceTypeId), Default_Cell_Rendere),
        new ColumnDetails("partyIdFrom", "partyIdFrom", "java.lang.String", new Integer(partyIdFrom), Default_Cell_Rendere),
        new ColumnDetails("partyId", "partyId", "java.lang.String", new Integer(partyId), Default_Cell_Rendere),
        new ColumnDetails("roleTypeId", "roleTypeId", "java.lang.String", new Integer(roleTypeId), Default_Cell_Rendere),
        new ColumnDetails("statusId", "statusId", "java.lang.String", new Integer(statusId), Default_Cell_Rendere),
        new ColumnDetails("billingAccountId", "billingAccountId", "java.lang.String", new Integer(billingAccountId), Default_Cell_Rendere),
        new ColumnDetails("contactMechId", "contactMechId", "java.lang.String", new Integer(contactMechId), Default_Cell_Rendere),
        new ColumnDetails("invoiceDate", "invoiceDate", "java.sql.Timestamp", new Integer(invoiceDate), Default_Cell_Rendere),
        new ColumnDetails("dueDate", "dueDate", "java.sql.Timestamp", new Integer(dueDate), Default_Cell_Rendere),
        new ColumnDetails("paidDate", "paidDate", "java.sql.Timestamp", new Integer(paidDate), Default_Cell_Rendere),
        new ColumnDetails("invoiceMessage", "invoiceMessage", "java.lang.String", new Integer(invoiceMessage), Default_Cell_Rendere),
        new ColumnDetails("referenceNumber", "referenceNumber", "java.lang.String", new Integer(referenceNumber), Default_Cell_Rendere),
        new ColumnDetails("description", "description", "java.lang.String", new Integer(description), Default_Cell_Rendere),
        new ColumnDetails("currencyUomId", "currencyUomId", "java.lang.String", new Integer(currencyUomId), Default_Cell_Rendere),
        new ColumnDetails("recurrenceInfoId", "recurrenceInfoId", "java.lang.String", new Integer(recurrenceInfoId), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedStamp", "lastUpdatedStamp", "java.sql.Timestamp", new Integer(lastUpdatedStamp), Default_Cell_Rendere),
        new ColumnDetails("lastUpdatedTxStamp", "lastUpdatedTxStamp", "java.sql.Timestamp", new Integer(lastUpdatedTxStamp), Default_Cell_Rendere),
        new ColumnDetails("createdStamp", "createdStamp", "java.sql.Timestamp", new Integer(createdStamp), Default_Cell_Rendere),
        new ColumnDetails("createdTxStamp", "createdTxStamp", "java.sql.Timestamp", new Integer(createdTxStamp), Default_Cell_Rendere),
        new ColumnDetails("orderId", "orderId", "java.lang.String", new Integer(orderId), Default_Cell_Rendere),
        new ColumnDetails("orderTypeId", "orderTypeId", "java.lang.String", new Integer(orderTypeId), Default_Cell_Rendere),
        new ColumnDetails("productId", "productId", "java.lang.String", new Integer(productId), Default_Cell_Rendere),
        new ColumnDetails("itemDescription", "itemDescription", "java.lang.String", new Integer(itemDescription), Default_Cell_Rendere),
        new ColumnDetails("quantity", "quantity", "java.math.BigDecimal", new Integer(quantity), Default_Cell_Rendere),
        new ColumnDetails("unitPrice", "unitPrice", "java.math.BigDecimal", new Integer(unitPrice), Default_Cell_Rendere),};
   
    static class ColumnDetails {
        String Id;
        String Name;
        String ClassName;
        Integer ColumnWidth;
        String RendererName;
        ColumnDetails(String Id, String Name, String ClassName, Integer width, String rendererName) {
            this.Id = Id;
            this.Name = Name;
            this.ClassName = ClassName;
            this.ColumnWidth = width;
            this.RendererName = rendererName;          
        }
    }

    final static int ID_INDEX = 0;
    final static int NAME_INDEX = 1;
    final static int CLASS_NAME_INDEX = 2;
    final static int COLUMN_WIDTH_INDEX = 3;
    final static int COLUMN_RENDERER_INDEX = 4;
    * */
/*
    static public int getRowIndex(final String id) throws Exception {
        for (int i = 0; i < GenericColumnName.length; ++i) {
            if (GenericColumnName[i][0].equals(id)) {
                return i;
            }
        }

        throw new Exception("Row Index not found: " + id);

    }

    static public String getColumnName(final String id) throws Exception {
        return GenericColumnName[getRowIndex(id)][NAME_INDEX];
    }

    static public String getClassName(final String id) throws Exception {
        return GenericColumnName[getRowIndex(id)][CLASS_NAME_INDEX];
    }

    static public String getColumnWidth(final String id) throws Exception {
        return GenericColumnName[getRowIndex(id)][CLASS_NAME_INDEX];
    }

    static public String getColumnRendere(final String id) throws Exception {
        return GenericColumnName[getRowIndex(id)][COLUMN_RENDERER_INDEX];
    }
    */ 
}
