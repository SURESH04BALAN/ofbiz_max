/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity;

/**
 *
 * @author siranjeev
 */
public class OrderMaxFacilityEditForm extends JPanel {

    private boolean DEBUG = true;
    protected static XuiSession session = null;
    List<GenericValue> facilityList = null;
    HashMap <String,String> columnNameId = new HashMap <String,String>();
    HashMap <Integer,String> columnNumberId = new HashMap <Integer,String>();

private String[][] IdColumnName = {        
    {"facilityId","Id","java.lang.String" },
    { "facilityName","Facility Name","java.lang.String"},
    { "ownerPartyId","Owner Party Id","java.lang.String"},    
    { "facilityTypeId","Type Id","java.lang.String"},
    { "parentFacilityId","Parent Facility Id","java.lang.String"},
    { "defaultInventoryItemTypeId","Default Inventory Item TypeId","java.lang.String"},

    { "primaryFacilityGroupId","Primary Facility GroupId","java.lang.String"},
    { "facilitySize","FacilitySize","java.lang.Long"},
    { "facilitySizeUomId","FacilitySizeUomId","java.lang.String"},
    { "productStoreId","productStoreId","java.lang.String"},
    { "defaultDaysToShip","Default Days To Ship","java.lang.Long"},
    { "openedDate","openedDate","java.sql.Timestamp"},
    { "closedDate","closedDate","java.sql.Timestamp"},
    { "description","description","java.lang.String"},
    { "defaultDimensionUomId","defaultDimensionUomId","java.lang.String"},
    { "defaultWeightUomId","defaultWeightUomId","java.lang.String"},
    { "geoPointId","geoPointId","java.lang.String"},
    { "lastUpdatedStamp","lastUpdatedStamp","java.sql.Timestamp"},
    { "lastUpdatedTxStamp","lastUpdatedTxStamp","java.sql.Timestamp"},
    { "createdStamp","createdStamp","java.sql.Timestamp"},
    { "createdTxStamp","createdTxStamp","java.sql.Timestamp"},  
};
    
    public OrderMaxFacilityEditForm(java.awt.Frame parent, boolean modal, XuiSession sessionVal) {
        super(new GridLayout(1, 0));
        session = sessionVal;
        
        runOrderList();
        
//        facilityList = PosProductHelper.findInvoiceFromExternalInvoiceNumber("ALPHA", "34567", session.getDelegator());
//        facilityList = PosProductHelper.getFacilityLists(session.getDelegator());
 //       if(facilityList.size() > 0){
            GenericValue val = null;// facilityList.get(0);
//            GenericValue val = session.getDelegator().makeValue("Facility");
//            String invoiceId = val.getString("invoiceId");
            GenericValue tmpProductGV;
            boolean productExists = false;
            try {
                val = session.getDelegator().findByPrimaryKey("OrderHeader", UtilMisc
                        .toMap("orderId", "10000"));
            } catch (GenericEntityException e) {
                Debug.logError("Problem in reading data of product", "this");
            }
            
            Map<String, Object> mapFields = val.getAllFields();
            int columnNumber = 0;
            for (Map.Entry<String, Object> entry : mapFields.entrySet()) {
                columnNameId.put(entry.getKey(), entry.getKey());
                columnNumberId.put(columnNumber++, entry.getKey());
                String fieldType = "null";
                Object field = val.get(entry.getKey());
                if(field!=null){
                    fieldType = val.get(entry.getKey()).getClass().toString();
                }
                Debug.logInfo("{\"" + entry.getKey() + "\"" + ",\"" + entry.getKey() + "\",\"" + fieldType + "\"},", "Module");                
            }
                    
//        }
  /*
        for (GenericValue val : facilityList) {

            Collection<String> colList = val.getAllKeys();
            for (String col : colList) {
                Debug.logInfo("Col list: ", col);
            }

            Map<String, Object> mapFields = val.getAllFields();
            for (Map.Entry<String, Object> entry : mapFields.entrySet()) {
                if(entry.getValue()!=null){
                Debug.logInfo("Key: " + entry.getKey() + " value: " + entry.getValue().toString(), "Module");
                }
                else{
                Debug.logInfo("Key: " + entry.getKey() + " value: NULL", "Module");                    
                }
            }
        }
*/
        JTable table = new JTable(new MyTableModel());
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up stricter input validation for the integer column.
//        table.setDefaultEditor(Integer.class,
//                               new IntegerEditor(0, 100));

        //If we didn't want this editor to be used for other
        //Integer columns, we'd do this:
//        table.getColumnModel().getColumn(3).setCellEditor(
//                new IntegerEditor(0, 100));

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
    void runOrderList() {
        facilityList = OrderMaxViewEntity.getOrderDetails(null, null,
                null, null, null, session.getDelegator());


        if (facilityList.size() > 0) {
            GenericValue val = facilityList.get(0);
            /*
             try {
             val = session.getDelegator().findByPrimaryKey("OrderHeader", UtilMisc
             .toMap("orderId", "10000"));
             } catch (GenericEntityException e) {
             Debug.logError("Problem in reading data of product", "this");
             }
             */
            Map<String, Object> mapFields = val.getAllFields();
            int columnNumber = 0;
            for (Map.Entry<String, Object> entry : mapFields.entrySet()) {
                columnNameId.put(entry.getKey(), entry.getKey());
                columnNumberId.put(columnNumber++, entry.getKey());
                String fieldType = "null";
                Object field = val.get(entry.getKey());
                if (field != null) {
                    fieldType = val.get(entry.getKey()).getClass().toString();
                }
                Debug.logInfo("{\"" + entry.getKey() + "\"" + ",\"" + entry.getKey() + "\",\"" + fieldType + "\"},", "Module");
            }

//        }
        }
    }
    
    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};
        private Object[][] data = {
            {"Kathy", "Smith",
                "Snowboarding", new Integer(5), new Boolean(false)},
            {"John", "Doe",
                "Rowing", new Integer(3), new Boolean(true)},
            {"Sue", "Black",
                "Knitting", new Integer(2), new Boolean(false)},
            {"Jane", "White",
                "Speed reading", new Integer(20), new Boolean(true)},
            {"Joe", "Brown",
                "Pool", new Integer(10), new Boolean(false)}
        };

        private class ColumnDetails{
        String Id;
        String Name;
        String ClassName;
        ColumnDetails(String Id, String Name, String ClassName){
            this.Id = Id;
            this.Name = Name;
            this.ClassName = ClassName;                    
        }
    }
        HashMap <Integer,ColumnDetails> columnDetailsMap = new HashMap <Integer,ColumnDetails>();
        MyTableModel(){

            for (int i=0; i < IdColumnName.length; i++) {
               ColumnDetails columnDetails = new ColumnDetails(IdColumnName[i][0],
                       IdColumnName[i][1],
                       IdColumnName[i][2] );
               columnDetailsMap.put(i, columnDetails);
            }
    }
/*        {"facilityId","Id", new String("String")},
         {"facilityName","FacilityName", new String("String")},        
        {"facilityTypeId","Type Id", new String("String")},
        {"parentFacilityId","Parent Facility Id", new String("String")},
        {"ownerPartyId","Owner Party Id", new String("String")},
        {"defaultInventoryItemTypeId","Default Inventory Item Type Id", new String("String")},
         {"primaryFacilityGroupId","Primary Facility GroupId", new String("String")},
         {"facilitySize","Facility Size", new String("String")},
         {"facilitySizeUomId","Facility Size UomId", new String("String")},
         {"productStoreId","Product Store Id", new String("String")},
         {"defaultDaysToShip","Default Days To Ship", new String("String")},
         {"openedDate","Opened Date", new String("String")},
         {"closedDate","Closed Date", new String("String")},
         {"description","Description", new String("String")},
         {"defaultDimensionUomId","Default Dimension UomId", new String("String")},
         {"defaultWeightUomId","Default Weight UomId", new String("String")},
         {"geoPointId","Geo Point Id", new String("String")},
         {"lastUpdatedStamp","Last Updated Stamp", new String("String")},
         {"lastUpdatedTxStamp","Last Updated TxStamp", new String("String")},
         {"createdStamp","Created Stamp", new String("String")},
         {"createdTxStamp","Created TxStamp", new String("String")},
};
*/
        public int getColumnCount() {
            return columnDetailsMap.size();
        }

        public int getRowCount() {
            return facilityList.size();
        }

        public String getColumnName(int col) {
//            String id =  columnNumberId.get(col);
//            return columnNameId.get(id);

            return columnDetailsMap.get(col).Name;
        }

        public Object getValueAt(int row, int col) {
            String id = columnDetailsMap.get(col).Id; // columnNumberId.get(col);
            GenericValue data = facilityList.get(row);
            if(data!=null) {
                return data.get(id);//data[row][col];                
            }
            else {
                            return new Object();//data[row][col];
            }

        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
/*            Object obj = getValueAt(0, c);
            if(obj!=null){
                String id =  columnNumberId.get(c);                
                return obj.getClass();
            }
            else{
                return new String("t").getClass();
            }
*/          Class cl = null;  
            try {
                cl = Class.forName(columnDetailsMap.get(c).ClassName);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OrderMaxFacilityEditForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            return cl;
        }

        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

//            data[row][col] = value;
            String id =  columnDetailsMap.get(col).Id;//columnNumberId.get(col);
            GenericValue data = facilityList.get(row);
            data.set(id, value);
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    String id =  columnNumberId.get(j);
                    GenericValue data = facilityList.get(i);

                    System.out.print("  " + data.get(id));
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI(java.awt.Frame parent, boolean modal, XuiSession sessionVal) {
        //Create and set up the window.
        JFrame frame = new JFrame("OrderMaxFacilityEditForm");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        OrderMaxFacilityEditForm newContentPane = new OrderMaxFacilityEditForm(parent, modal, sessionVal);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
/*
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
*/
    class IntegerEditor extends DefaultCellEditor {

        JFormattedTextField ftf;
        NumberFormat integerFormat;
        private Integer minimum, maximum;
        private boolean DEBUG = false;

        public IntegerEditor(int min, int max) {
            super(new JFormattedTextField());
            ftf = (JFormattedTextField) getComponent();
            minimum = new Integer(min);
            maximum = new Integer(max);

            //Set up the editor for the integer cells.
            integerFormat = NumberFormat.getIntegerInstance();
            NumberFormatter intFormatter = new NumberFormatter(integerFormat);
            intFormatter.setFormat(integerFormat);
            intFormatter.setMinimum(minimum);
            intFormatter.setMaximum(maximum);

            ftf.setFormatterFactory(
                    new DefaultFormatterFactory(intFormatter));
            ftf.setValue(minimum);
            ftf.setHorizontalAlignment(JTextField.TRAILING);
            ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);

            //React when the user presses Enter while the editor is
            //active.  (Tab is handled as specified by
            //JFormattedTextField's focusLostBehavior property.)
            ftf.getInputMap().put(KeyStroke.getKeyStroke(
                    KeyEvent.VK_ENTER, 0),
                    "check");
            ftf.getActionMap().put("check", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    if (!ftf.isEditValid()) { //The text is invalid.
                        if (userSaysRevert()) { //reverted
                            ftf.postActionEvent(); //inform the editor
                        }
                    } else {
                        try {              //The text is valid,
                            ftf.commitEdit();     //so use it.
                            ftf.postActionEvent(); //stop editing
                        } catch (java.text.ParseException exc) {
                        }
                    }
                }
            });
        }

        //Override to invoke setValue on the formatted text field.
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected,
                int row, int column) {
            JFormattedTextField ftf =
                    (JFormattedTextField) super.getTableCellEditorComponent(
                    table, value, isSelected, row, column);
            ftf.setValue(value);
            return ftf;
        }

        //Override to ensure that the value remains an Integer.
        public Object getCellEditorValue() {
            JFormattedTextField ftf = (JFormattedTextField) getComponent();
            Object o = ftf.getValue();
            if (o instanceof Integer) {
                return o;
            } else if (o instanceof Number) {
                return new Integer(((Number) o).intValue());
            } else {
                if (DEBUG) {
                    System.out.println("getCellEditorValue: o isn't a Number");
                }
                try {
                    return integerFormat.parseObject(o.toString());
                } catch (ParseException exc) {
                    System.err.println("getCellEditorValue: can't parse o: " + o);
                    return null;
                }
            }
        }

        //Override to check whether the edit is valid,
        //setting the value if it is and complaining if
        //it isn't.  If it's OK for the editor to go
        //away, we need to invoke the superclass's version 
        //of this method so that everything gets cleaned up.
        public boolean stopCellEditing() {
            JFormattedTextField ftf = (JFormattedTextField) getComponent();
            if (ftf.isEditValid()) {
                try {
                    ftf.commitEdit();
                } catch (java.text.ParseException exc) {
                }

            } else { //text is invalid
                if (!userSaysRevert()) { //user wants to edit
                    return false; //don't let the editor go away
                }
            }
            return super.stopCellEditing();
        }

        /**
         * Lets the user know that the text they entered is bad. Returns true if
         * the user elects to revert to the last good value. Otherwise, returns
         * false, indicating that the user wants to continue editing.
         */
        protected boolean userSaysRevert() {
            Toolkit.getDefaultToolkit().beep();
            ftf.selectAll();
            Object[] options = {"Edit",
                "Revert"};
            int answer = JOptionPane.showOptionDialog(
                    SwingUtilities.getWindowAncestor(ftf),
                    "The value must be an integer between "
                    + minimum + " and "
                    + maximum + ".\n"
                    + "You can either continue editing "
                    + "or revert to the last valid value.",
                    "Invalid Text Entered",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[1]);

            if (answer == 1) { //Revert!
                ftf.setValue(ftf.getValue());
                return true;
            }
            return false;
        }
    }
}