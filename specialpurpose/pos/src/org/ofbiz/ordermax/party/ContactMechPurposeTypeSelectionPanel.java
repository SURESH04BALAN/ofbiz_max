/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.party;

import java.awt.BorderLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import mvc.model.list.ContactMechPurposeTypeListCellRenderer;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.PartyContactMechCompositeListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;

/**
 *
 * @author siranjeev
 */
public class ContactMechPurposeTypeSelectionPanel extends javax.swing.JPanel {
    
    final private ListAdapterListModel<ContactMechPurposeType> contactMechPurposeTypeListModel = new ListAdapterListModel<ContactMechPurposeType>();    
    private ListComboBoxModel<ContactMechPurposeType> countryComboBoxModel = new ListComboBoxModel<ContactMechPurposeType>();    
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();    
    private ListModelSelection<ContactMechPurposeType> listModelSelection = new ListModelSelection<ContactMechPurposeType>();  

    //seleted list
/*    final private ListAdapterListModel<ContactMechPurposeType> postalAddressListModel = new ListAdapterListModel<ContactMechPurposeType>();
    private JList<ContactMechPurposeType> contactMechPurposeTypeList = new JList<ContactMechPurposeType>(postalAddressListModel);
    private ListSelectionModel selectionListModel = new DefaultListSelectionModel();
    private ListModelSelection<ContactMechPurposeType> listModelListSelection = new ListModelSelection<ContactMechPurposeType>();
  */
    
    final private ListAdapterListModel<PartyContactMechComposite> partyContactMechCompositeListModel = new ListAdapterListModel<PartyContactMechComposite>();
    private JList<PartyContactMechComposite> partyContactMechCompositeList = new JList<PartyContactMechComposite>(partyContactMechCompositeListModel);
    private ListSelectionModel partyContactMechCompositeSelectionModel = new DefaultListSelectionModel();
    private ListModelSelection<PartyContactMechComposite> partyContactMechCompositelistModelSelection = new ListModelSelection<PartyContactMechComposite>();
    
    /**
     * Creates new form ContactMechPurposeTypeSelectionPanel
     */
    public ContactMechPurposeTypeSelectionPanel(String purposeType) {
        initComponents();
        contactMechPurposeTypeListModel.addAll(ContactMechTypePurposeSingleton.getValueList(purposeType));        
        countryComboBoxModel.setListModel(contactMechPurposeTypeListModel);
        comboPurposeId.setModel(countryComboBoxModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        listModelSelection.setListModels(contactMechPurposeTypeListModel, selectionModel);
        countryComboBoxModel.setListSelectionModel(selectionModel);
        
        ContactMechPurposeTypeListCellRenderer geoListCellRenderer = new ContactMechPurposeTypeListCellRenderer();        
        comboPurposeId.setRenderer(geoListCellRenderer);
        
        
        panelPurposeList.setLayout(new BorderLayout());
        panelPurposeList.add(BorderLayout.CENTER, partyContactMechCompositeList);
        PartyContactMechCompositeListCellRenderer render = new PartyContactMechCompositeListCellRenderer();
        partyContactMechCompositeList.setCellRenderer(render);
 
    }
    public void clearDialogFields()   {
        partyContactMechCompositeListModel.clear();
    }
    public ContactMechPurposeType getSelectedPurposeType(){
        return listModelSelection.getSelection();
    }

    public PartyContactMechComposite getPartyContactMechComposite(){
        return partyContactMechCompositelistModelSelection.getSelection();
    }
    
    public void setAddressPurpose(ListAdapterListModel<PartyContactMechComposite> purposeListModel){
        partyContactMechCompositeListModel.clear();
        partyContactMechCompositeListModel.addAll(purposeListModel.getList());
        Debug.logInfo("partyContactMechCompositeListModel.size : " + partyContactMechCompositeListModel.getSize(), "module");
        partyContactMechCompositeSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        partyContactMechCompositeList.setSelectionModel(partyContactMechCompositeSelectionModel);
        /*
        ListCellRenderer<PostalAddress> postalAddressListCellRenderer = new PostalAddressListCellRenderer();
        postalAddressList.setCellRenderer(postalAddressListCellRenderer);
        postalAddressList.setEnabled(true);
        postalAddressList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            setPostalAddressDetail(i);
                            break;
                        }
                    }
                    
                }
            }
        });        
        */
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        maritalStatusLabel = new javax.swing.JLabel();
        comboPurposeId = new javax.swing.JComboBox();
        btnAddPurpose = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        panelPurposeList = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        maritalStatusLabel.setText("Contact Purpose :"); // NOI18N

        btnAddPurpose.setMnemonic('A');
        btnAddPurpose.setText("Add");

        btnRemove.setMnemonic('R');
        btnRemove.setText("Remove");

        javax.swing.GroupLayout panelPurposeListLayout = new javax.swing.GroupLayout(panelPurposeList);
        panelPurposeList.setLayout(panelPurposeListLayout);
        panelPurposeListLayout.setHorizontalGroup(
            panelPurposeListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelPurposeListLayout.setVerticalGroup(
            panelPurposeListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddPurpose, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove)
                        .addGap(0, 240, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(maritalStatusLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboPurposeId, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelPurposeList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maritalStatusLabel)
                    .addComponent(comboPurposeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelPurposeList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPurpose)
                    .addComponent(btnRemove))
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAddPurpose;
    public javax.swing.JButton btnRemove;
    private javax.swing.JComboBox comboPurposeId;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel maritalStatusLabel;
    private javax.swing.JPanel panelPurposeList;
    // End of variables declaration//GEN-END:variables
}
