/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party.contacts;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.PostalAddressListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.Contact;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechCompositeList;
import org.ofbiz.ordermax.entity.PostalAddress;
import static org.ofbiz.ordermax.orderbase.salesorder.SalesOrderController.module;
import org.ofbiz.ordermax.screens.ContactMechPanelMain;

/**
 *
 * @author BBS Auctions
 */
public class SelectAddressPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    JPanel addressTablePanel = null;
    public static final String module = SelectAddressPanel.class.getName();
    final private ListAdapterListModel<PostalAddress> postalAddressListModel = new ListAdapterListModel<PostalAddress>();
    public JList<PostalAddress> postalAddressList = new JList<PostalAddress>(postalAddressListModel);
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private ListModelSelection<PostalAddress> listModelSelection = new ListModelSelection<PostalAddress>();
    org.ofbiz.ordermax.party.PostalAddressPanel postalAddressPanel = new org.ofbiz.ordermax.party.PostalAddressPanel();

    ControllerOptions options = null;
    String partyContactPurposeTypeId = null;
    String postalAddressMechId = null;

    /**
     * Creates new form SelectAddressPanel
     */
    public SelectAddressPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
        if (options.contains("partyContactPurposeTypeId")) {
            partyContactPurposeTypeId = (String) options.get("partyContactPurposeTypeId");
        }

        if (options.contains("postalAddressMechId")) {
            postalAddressMechId = (String) options.get("postalAddressMechId");
        }

        ListCellRenderer<PostalAddress> postalAddressListCellRenderer = new PostalAddressListCellRenderer();
        postalAddressList.setCellRenderer(postalAddressListCellRenderer);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        postalAddressList.setSelectionModel(selectionModel);
        postalAddressList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) selectionModel;
                System.out.println("postalAddressList: ");
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    System.out.println("postalAddressList: " + lsm.isSelectionEmpty());
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    System.out.println("postalAddressList: minIndex " + minIndex + " " + maxIndex);
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println("postalAddressList: " + i);
                            setPostalAddressDetail(i);
                            break;
                        }
                    }

                }
            }
        });
        postalAddressList.setEnabled(true);
//        listModelSelection.setListModels(postalAddressListModel, selectionModel);
        addressTablePanel = new JPanel();
        addressTablePanel.setLayout(new BorderLayout());
        addressTablePanel.add(BorderLayout.CENTER, postalAddressList);
        panelAddressList.setLayout(new BorderLayout());
        panelAddressList.add(BorderLayout.CENTER, addressTablePanel);

        panelAddressDetail.setLayout(new BorderLayout());
        panelAddressDetail.add(BorderLayout.CENTER, postalAddressPanel);
    }

    public void setPostalAddressDetail(int index) {
        if (index < postalAddressListModel.getSize()) {
            PostalAddress opt = postalAddressListModel.getElementAt(index);
            postalAddressPanel.setPostalAddress(opt);
            try {
                postalAddressPanel.setDialogFields();
            } catch (ParseException ex) {
                Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("selected index :" + postalAddressList.getSelectedIndex() + "[[" + opt.getAddress1() + "]]");
        }
    }

    public void setPostalAddressDetail(String postalAddressMechId) {
        for (int i = 0; i < postalAddressListModel.getSize(); ++i) {
            PostalAddress opt = postalAddressListModel.getElementAt(i);
            if (opt.getContactMechId().equals(postalAddressMechId)) {
                postalAddressList.setSelectedIndex(i);
                /* postalAddressPanel.setPostalAddress(opt);
                 try {
                 postalAddressPanel.setDialogFields();
                 } catch (ParseException ex) {
                 Logger.getLogger(ContactMechPanelMain.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
                break;
            }
            System.out.println("selected index :" + postalAddressList.getSelectedIndex() + "[[" + opt.getAddress1() + "]]");
        }
    }

    public Contact getSelectedPostalContact() {
        Contact contact = null;
        if (postalAddressList.getSelectedValue() != null) {
            Debug.logInfo("ok pressed: " + postalAddressList.getSelectedValue().getContactMechId(), module);
            for (PartyContactMechComposite partyContactMechComposite : partyContactMechCompositeListData.getList()) {
                if (postalAddressList.getSelectedValue().getContactMechId().equals(partyContactMechComposite.getContact().getContactMech().getcontactMechId())) {

                    return partyContactMechComposite.getContact();
                }
            }
        }
        return contact;

    }
    PartyContactMechCompositeList partyContactMechCompositeListData = null;

    public void setPartyContactMechCompositeListData(PartyContactMechCompositeList partyContactMechCompositeListData) {
        this.partyContactMechCompositeListData = partyContactMechCompositeListData;
        postalAddressListModel.clear();
//        telecomNumberListModel.clear();
//        panelAddressPurpose.clearDialogFields();
        postalAddressPanel.clearDialogFields();

//        ListAdapterListModel<PartyContactMechComposite> tempAddressPurposeListModel = new ListAdapterListModel<PartyContactMechComposite>();
        System.out.println("partyContactMechCompositeListData.getList() :" + partyContactMechCompositeListData.getList().size());
        for (PartyContactMechComposite partyContactMechComposite : partyContactMechCompositeListData.getList()) {
            if ("POSTAL_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
                if (partyContactPurposeTypeId != null) {
                    if (partyContactMechComposite.getPartyContactMechPurposeList().getPartyContactPurpose(partyContactPurposeTypeId) != null) {
                        postalAddressListModel.add(partyContactMechComposite.getContact().getPostalAddress());
                    }
                } else {
                    postalAddressListModel.add(partyContactMechComposite.getContact().getPostalAddress());
                }
                setPostalAddressDetail(postalAddressMechId);
            }/* else if ("TELECOM_NUMBER".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
             telecomNumberListModel.add(partyContactMechComposite.getContact().getTelecomNumber());
             }*/

        }

//        setPostalAddressDetail(0);
        this.revalidate();
        this.repaint();

        //panelAddressPurpose.setAddressPurpose(tempAddressPurposeListModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAddressList = new javax.swing.JPanel();
        panelAddressDetail = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelAddressList.setPreferredSize(new java.awt.Dimension(0, 170));
        panelAddressList.setLayout(new java.awt.GridLayout());
        add(panelAddressList, java.awt.BorderLayout.PAGE_START);

        panelAddressDetail.setPreferredSize(new java.awt.Dimension(424, 300));

        javax.swing.GroupLayout panelAddressDetailLayout = new javax.swing.GroupLayout(panelAddressDetail);
        panelAddressDetail.setLayout(panelAddressDetailLayout);
        panelAddressDetailLayout.setHorizontalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 887, Short.MAX_VALUE)
        );
        panelAddressDetailLayout.setVerticalGroup(
            panelAddressDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );

        add(panelAddressDetail, java.awt.BorderLayout.CENTER);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        //      doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        //        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton cancelButton;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JButton okButton;
    private javax.swing.JPanel panelAddressDetail;
    private javax.swing.JPanel panelAddressList;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return okButton;
    }

    @Override
    public JButton getCancelButton() {
        return cancelButton;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
