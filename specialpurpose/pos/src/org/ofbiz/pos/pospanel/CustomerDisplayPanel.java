/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.pos.pospanel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.screen.PosScreen;
import net.xoetrope.xui.data.XModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.Delegator;
import static org.ofbiz.pos.PosTransaction.module;
import org.ofbiz.entity.GenericEntityException;
import javax.swing.SwingConstants;

/**
 *import javax.swing.SwingConstants;

 * @author siranjeev
 */
public class CustomerDisplayPanel extends javax.swing.JPanel {

    public static final String module = CustomerDisplayPanel.class.getName();
    int[] columnWidth = {270, 50, 50, 90};

    /**
     * Creates new form CustomerDisplayPanel
     */
    public CustomerDisplayPanel() {
        initComponents();

        ArrayList<CustomerDisplayData> list = new ArrayList<CustomerDisplayData>();
        list.add(new CustomerDisplayData());
        jTable1.setModel(new MyTableModel(list));
        int colCount = jTable1.getModel().getColumnCount();

        for (int i = 0; i < colCount; i++) {
            TableColumn column = jTable1.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth[i]);
        }
        
         StringBuilder sb = new StringBuilder(64);
                sb.append("<html>Nivi Enterprises").
                                append(" <br>37-39 Bommerang Place,").
                                append(" <br>Seven Hills, NSW</html>");

                jLabel3.setText(sb.toString());
    }

    public CustomerDisplayData appendEmpty() {
        return new CustomerDisplayData();
    }

    public synchronized void refresh(PosScreen pos) {
        if (!jTable1.isEnabled()) {
            // no point in refreshing when we are locked;
            // we will auto-refresh when unlocked
            return;
        }

        PosTransaction tx = PosTransaction.getCurrentTx(pos.getSession());
        ArrayList<CustomerDisplayData> list = new ArrayList<CustomerDisplayData>();
//        XModel jmodel = this.createModel();
        if (tx != null && !tx.isEmpty()) {
            list.addAll(tx.appendCustomerItemDataModel());
//            tx.CustomerDisplayData();
            list.add(this.appendEmpty());
            list.addAll(tx.appendCustomerTotalDataModel());
//            if (tx.selectedPayments() > 0) {
            list.add(this.appendEmpty());
            list.addAll(tx.appendCustomerPaymentDataModel());
//            }
            if (pos.getInput().isFunctionSet("PAID")) {
                list.addAll(tx.appendCustomerChangeDataModel());
            }
        } else {
            list.add(this.appendEmpty());
        }

        // make sure we are at the last item in the journal
        try {
            jTable1.setModel(new MyTableModel(list));
            int colCount = jTable1.getModel().getColumnCount();

            for (int i = 0; i < colCount; i++) {
                TableColumn column = jTable1.getColumnModel().getColumn(i);
                column.setPreferredWidth(columnWidth[i]);
            }

//            jTable1.invalidate();
        } catch (ArrayIndexOutOfBoundsException e) {
            Debug.logError(e, "Unable to repaint the Journal", module);
        }
        String productId = pos.getJournal().getSelectedSku();
        setProductImage(pos, productId);
    /*    if (productId != null) {
            try {
                GenericValue product = pos.getSession().getDelegator().findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
                if (product != null) {
                    showLabelFileImage(product.getString("detailImageUrl"), product.getString("internalName"));
                }
            } catch (GenericEntityException e) {
                Debug.logError(e, module);

            }
        }*/
        //Debug.logInfo(getModelText(jmodel), module);
    }

    public void setProductImage(PosScreen pos, String productId){
        if (productId != null) {
            try {
                GenericValue product = pos.getSession().getDelegator().findByPrimaryKeyCache("Product", UtilMisc.toMap("productId", productId));
                if (product != null) {
                    showLabelFileImage(product.getString("detailImageUrl"), product.getString("internalName"));
                }
            } catch (GenericEntityException e) {
                Debug.logError(e, module);

            }
        }        
    }
    
    class MyTableModel extends AbstractTableModel {

        String[] columnNames = {"Description", "Qty", "Unit Price", "Price"};
        String[] name = {"PosSku", "PosItem", "PosQty", "PosAmt"};
        ArrayList<CustomerDisplayData> dataList = new ArrayList<CustomerDisplayData>();
        private Locale defaultLocale = Locale.getDefault();
        private Object[][] data = {
            {"Kathy", "Smith", "", ""}
        };

        public MyTableModel(ArrayList<CustomerDisplayData> dList) {
            dataList.addAll(dList);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public void setTableData(ArrayList<CustomerDisplayData> dList) {

            dataList.clear();
            dataList.addAll(dList);
        }

        public int getRowCount() {
            return dataList.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            if (col == 0) {
                return dataList.get(row).desc;
            } else if (col == 1) {
                return dataList.get(row).qty;
            } else if (col == 2) {
                return dataList.get(row).unitPrice;
            } else if (col == 3) {
                return dataList.get(row).price;
            } else {
                return "";
            }
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    public void showLabelFileImage(String field, String productName) {

        if (field != null && field.isEmpty() == false) {

            ImageIcon icon = BaseHelper.getImage(field);
            if (icon != null) {
                Image img = icon.getImage();
                Image newimg = img.getScaledInstance(230, 310, java.awt.Image.SCALE_SMOOTH);
                ImageIcon newIcon = new ImageIcon(newimg);
                jLabel1.setIcon(newIcon);
                jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel2.setText(productName);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setLayout(new java.awt.GridLayout(0, 2));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(30);
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(204, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel2.setBackground(new java.awt.Color(204, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setPreferredSize(new java.awt.Dimension(34, 134));
        jPanel2.add(jLabel2, java.awt.BorderLayout.PAGE_END);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setMaximumSize(new java.awt.Dimension(280, 54));
        jLabel3.setPreferredSize(new java.awt.Dimension(280, 94));
        jPanel2.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
