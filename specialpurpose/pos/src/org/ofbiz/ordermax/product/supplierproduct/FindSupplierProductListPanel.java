/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.supplierproduct;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javolution.util.FastMap;
import mvc.controller.LoadAccountWorker;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.PartyListCellRenderer;
import mvc.model.table.FindProductPriceTableModel;
import mvc.model.table.SupplierProductCompositeDetailTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.utility.CollapsiblePanel;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class FindSupplierProductListPanel extends javax.swing.JPanel {
   
    private final JTextField txtProdIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;

    private final JTextField txtPartyIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor orderRowColumnClickActionTableCellEditor = null;
    
//    private SupplierProductCompositeDetailTableModel paymentTableModel = new SupplierProductCompositeDetailTableModel();
//    private ListAdapterListModel<SupplierProductComposite> invoiceCompositeListModel = new ListAdapterListModel<SupplierProductComposite>();
      public GenericTableModelPanel<SupplierProductComposite, SupplierProductCompositeDetailTableModel> tablePanel = null;  
    
    public JComboBoxSelectionModel<Account> accountComboModel = null;
    
    
//    private JComboBox<String> invoiceIdCondComboBox = new JComboBox<String>(descCondComboBoxModel);
    
    boolean showComboKeys = false;
    PartyPickerEditPanel supplierPartyId;
    /**
     * Creates new form ReceiveInventoryPanel
     */
    
    public FindSupplierProductListPanel(ControllerOptions controllerOptions) {
        initComponents();
        this.removeAll();
        this.setLayout(new BorderLayout());        
        jPanel4.removeAll();
        
        supplierPartyId = new PartyPickerEditPanel(controllerOptions);    
        JPanel test = createDetailsPanel();        
        this.add(BorderLayout.BEFORE_FIRST_LINE, test);   
        this.add(BorderLayout.CENTER, panelIResultList);   
        
    
        tablePanel = new GenericTableModelPanel<SupplierProductComposite, SupplierProductCompositeDetailTableModel>(new SupplierProductCompositeDetailTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        ComponentBorder.doubleRaisedLoweredBevelBorder(panelIResultList, "Inventory Detail List");        
    }

    public void setReceiveInventoryList(ListAdapterListModel<SupplierProductComposite> orderListModel) {

        tablePanel.setListModel(orderListModel);
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getProductActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    public JTextField getTxtPartyIdTableTextField() {
        return txtPartyIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getPartyActionTableCellEditor() {
        return orderRowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {
    
        for (int i = 0; i < SupplierProductCompositeDetailTableModel.Columns.values().length; i++) {
            SupplierProductCompositeDetailTableModel.Columns[] columns = SupplierProductCompositeDetailTableModel.Columns.values();
            SupplierProductCompositeDetailTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
    
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            }
            else if(column.getClassName().equals(java.sql.Timestamp.class)){  
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());                        
            }
            else if(SupplierProductCompositeDetailTableModel.Columns.PRODUCTID == column){
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);                    
            }
            else if(SupplierProductCompositeDetailTableModel.Columns.SUPPLIERID == column){
                tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
                txtPartyIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtPartyIdTableTextField);
                editor.setClickCountToStart(0);
                orderRowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(orderRowColumnClickActionTableCellEditor);                   
            }   
        }        
    }
    
    String getSupplierPartyId(){
        String partyId=null;
        if(accountComboModel.jComboBox.getSelectedIndex()> -1){
            Account account = accountComboModel.comboBoxModel.getElementAt(accountComboModel.jComboBox.getSelectedIndex());
            partyId = account.getParty().getpartyId();
        }
        return partyId;
    }
    
    public Map<String, Object> getFindOptionList(){
       boolean showAll = true;
        Map<String, Object> svcCtx = FastMap.newInstance();

        if (UtilValidate.isNotEmpty(supplierPartyId.textIdField.getText())) {
            svcCtx.put("partyId", supplierPartyId.textIdField.getText());
            showAll = false;
        }    
       
        if (UtilValidate.isNotEmpty(cbNoSupplierProduct) && cbNoSupplierProduct.isSelected()) {
            svcCtx.put("partyId", "null");
            showAll = false;
        } 
    //status type method
        if (showAll == true) {
            svcCtx.put("showAll", "Y");
                        svcCtx.put("noConditionFind", "Y");
            
        }
        
        svcCtx.put("lookupFlag", "Y");
        
        return svcCtx;
    }
    public boolean getProductWithoutSupplier(){
        return cbNoSupplierProduct.isSelected();
    }
    static class DecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("#.00");

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            BigDecimal bdValue = (BigDecimal) value;
            value = bdValue.setScale(scale, rounding);
            setHorizontalAlignment(JLabel.RIGHT);
// And pass it on to parent class
            return super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
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

        jPanel4 = new CollapsiblePanel();
        btnFind = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        referenceCombo = new javax.swing.JComboBox();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cbProcessing = new javax.swing.JCheckBox();
        txtInvoiceId = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        cbCreated = new javax.swing.JCheckBox();
        txtDescription = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jToggleButton1 = new javax.swing.JToggleButton();
        jComboBox4 = new javax.swing.JComboBox();
        jToggleButton2 = new javax.swing.JToggleButton();
        jComboBox5 = new javax.swing.JComboBox();
        jTextField6 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        txtDescription1 = new javax.swing.JTextField();
        cbCreated1 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        txtDescription2 = new javax.swing.JTextField();
        cbCreated2 = new javax.swing.JCheckBox();
        panelIResultList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(678, 315));

        btnFind.setText("Find");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Serial Number:");

        jComboBox1.setEnabled(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Facility Id:");

        cbProcessing.setSelected(true);
        cbProcessing.setText("Ignore Case");

        jComboBox2.setEnabled(false);

        cbCreated.setSelected(true);
        cbCreated.setText("Ignore Case");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Product Id:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Date Time Received:");

        jToggleButton1.setText("jToggleButton1");

        jToggleButton2.setText("jToggleButton1");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Internal Name:");

        jComboBox3.setEnabled(false);

        cbCreated1.setSelected(true);
        cbCreated1.setText("Ignore Case");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Inventory Item Id:");

        jComboBox6.setEnabled(false);

        cbCreated2.setSelected(true);
        cbCreated2.setText("Ignore Case");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(referenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cbProcessing))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cbCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtDescription1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cbCreated1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtDescription2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cbCreated2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(243, 243, 243))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbProcessing))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbCreated))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtDescription1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbCreated1))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtDescription2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbCreated2))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel5))
                    .addComponent(referenceCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(btnFind))
        );

        add(jPanel4, java.awt.BorderLayout.NORTH);

        panelIResultList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1070, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );

        add(panelIResultList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
        public JCheckBox cbNoSupplierProduct = new JCheckBox("Without Supplier Product");
	private JPanel createDetailsPanel() {
		
		
		JPanel panel = new CollapsiblePanel();
		
		JLabel partyIdLabel = new JLabel("Supplier Name:");
                
/*		JLabel anAttributeLabel = jLabel7;//new JLabel("An Attribute");
		JLabel dateFieldLabel = jLabel3;//new JLabel("Date Field");
		JLabel anAttLabel = new JLabel("An Att");
		JLabel anotherAttLabel = new JLabel("Another Att");
		JLabel anotherAtt2Label = new JLabel("Another Att");
*/
                /*PartyListCellRenderer pccRender = new PartyListCellRenderer(DisplayNameInterface.DisplayTypes.SHOW_NAME_AND_CODE);
                List<Account> list = PartyListSingleton.getSupplierValueList();
                Account account = LoadAccountWorker.createNewEmptyAccount(PartyListSingleton.getSingletonSesion());
                account.getParty().setpartyTypeId("PARTY_GROUP");
                account.getPartyGroup().setgroupName("All");
                list.add(0, account);
                accountComboModel = new JComboBoxSelectionModel<Account>(list,pccRender);
                
                JComboBox<Account> jComboBox = accountComboModel.jComboBox;
                */
                JPanel pFromPartyId = new JPanel();
                pFromPartyId.setLayout(new BorderLayout());
                pFromPartyId.add(BorderLayout.CENTER, supplierPartyId);
    
		panel.setLayout(new GridBagLayout());
	
		GridBagConstraints gbc = new GridBagConstraints();
		
		int i=0;
		
		gbc.insets = new Insets(2,2,2,2);
		gbc.anchor = GridBagConstraints.NORTHEAST;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		panel.add(partyIdLabel,  gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;		
		panel.add(pFromPartyId,  gbc);		
		
		i++;
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		panel.add(cbNoSupplierProduct,  gbc);

		i++;
/*				
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anAttributeLabel,  gbc);
				
		gbc.gridx = 1;
		gbc.gridy = i;		
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(anAttributeField,  gbc);		
		
		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(dateFieldLabel,  gbc);

		gbc.gridx = 1;
		gbc.gridy = i;		
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(dateFieldField,  gbc);		
		
		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anAttLabel,  gbc);
				
		gbc.gridx = 1;
		gbc.gridy = i;				
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(anAttField,  gbc);		

		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anotherAttLabel,  gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;				
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
//		gbc.weightx = 1.0;
//		gbc.weighty = 1.0;
		panel.add(new JScrollPane(anotherAttField),  gbc);

		i++;
		gbc.gridx = 0;
		gbc.gridy = i;		
		gbc.gridwidth = 1;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(anotherAtt2Label,  gbc);

		gbc.gridx = 1;
		gbc.gridy = i;				
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panel.add(anotherAtt2Field,  gbc);
		
*/
		gbc.gridx = 2;
		gbc.gridy = i;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(btnFind,  gbc);
		
		return panel;
	}
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    public javax.swing.JCheckBox cbCreated;
    public javax.swing.JCheckBox cbCreated1;
    public javax.swing.JCheckBox cbCreated2;
    public javax.swing.JCheckBox cbProcessing;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JPanel panelIResultList;
    private javax.swing.JComboBox referenceCombo;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtDescription1;
    private javax.swing.JTextField txtDescription2;
    private javax.swing.JTextField txtInvoiceId;
    // End of variables declaration//GEN-END:variables
}
