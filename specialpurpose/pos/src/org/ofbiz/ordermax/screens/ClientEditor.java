/*
 * Copyright (c) 2010, Oracle.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ofbiz.ordermax.screens;

import org.ofbiz.ordermax.entity.Person;
import org.ofbiz.ordermax.entity.Client;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.party.party.PartyWorker;

/**
 * Form that allows editing of information about one client.
 *
 * @author Jiri Vagner, Jan Stola
 */
public class ClientEditor extends javax.swing.JPanel {

    public static final String module = ClientEditor.class.getName();
    private Client client = null;//Client.createTestClient();
    protected XuiSession session = null;
    protected GenericValue personalAddress;
    protected AddressPanel personalAddressPanel = null;
    protected AddressPanel mailingAddressPanel = null;
    protected AddressPanel deliveryAddressPanel = null;
    protected AddressPanel delivery2AddressPanel = null;
    GenericValue genricValue = null;

    public ClientEditor(XuiSession session) {

//        session = XuiContainer.getSession();

        initComponents();
        this.session = session;

        personalAddressPanel = new AddressPanel();
        addAddressPanelToTab(personalAddressPanel, addressPersonalPanel);

        mailingAddressPanel = new AddressPanel();
        addAddressPanelToTab(mailingAddressPanel, addressMailingPanel);

        deliveryAddressPanel = new AddressPanel();
        addAddressPanelToTab(deliveryAddressPanel, addressDelivery1lPanel);

        delivery2AddressPanel = new AddressPanel();
        addAddressPanelToTab(delivery2AddressPanel, addressDelivery2lPanel);

//        client = Client.createTestClient(session);
    }

    /**
     * Returns
     * <code>Client</code> being edited.
     *
     * @return <code>Client</code> being edited.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets client to edit.
     *
     * @param client client to edit.
     */
    public void setClient(Client client) {
        Client oldClient = this.client;
        this.client = client;
        firePropertyChange("client", oldClient, client);
    }
    /*
     public void setClient(Client client) {
     Client oldClient = this.client;
     this.client = client;
     firePropertyChange("client", oldClient, client);
     }
     */

    public void setClientId(Client client) {
        Client oldClient = this.client;
        this.client = client;
        firePropertyChange("client", oldClient, client);
    }

    public void setPerson(GenericValue person) {
        Client oldClient = this.client;
        this.client = new Client();
        this.client.setPerson(person);
        setPartyUIFields();
        GenericValue personalAddress = PartyWorker.findPartyLatestPostalAddress(this.client.person.getpartyId(), session.getDelegator());
        if(personalAddress!=null){
//            try {
//                personalAddress = session.getDelegator().create("PostalAddress");
//            } catch (GenericEntityException ex) {
//                Logger.getLogger(ClientEditor.class.getName()).log(Level.SEVERE, null, ex);
//            }
                  personalAddressPanel.setPostalAddress(personalAddress);
        }
       
  
        
//        editsalutation.setText(client.person.getfirstName());
//        editfirstName.setText(client.person.getlastName());        
        firePropertyChange("client", oldClient, client);
    }

    public XuiSession getSession() {
        return this.session;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        sexButtonGroup = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        clientInfoPane = new javax.swing.JTabbedPane();
        personalPanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        editpersonalTitle = new javax.swing.JTextField();
        surnameLabel = new javax.swing.JLabel();
        editfirstName = new javax.swing.JTextField();
        editmaritalStatus = new javax.swing.JComboBox();
        sexLabel = new javax.swing.JLabel();
        maritalStatusLabel = new javax.swing.JLabel();
        maleRadioButton = new javax.swing.JRadioButton();
        femaleRadioButton = new javax.swing.JRadioButton();
        ageLabel = new javax.swing.JLabel();
        ageTextField = new javax.swing.JTextField();
        firstNameLabel2 = new javax.swing.JLabel();
        editpartyId = new javax.swing.JTextField();
        firstNameLabel3 = new javax.swing.JLabel();
        editmiddleName = new javax.swing.JTextField();
        surnameLabel2 = new javax.swing.JLabel();
        editlastName = new javax.swing.JTextField();
        surnameLabel3 = new javax.swing.JLabel();
        editsalutation = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        addressMailingPanel = new javax.swing.JPanel();
        addressDelivery1lPanel = new javax.swing.JPanel();
        addressDelivery2lPanel = new javax.swing.JPanel();
        addressPersonalPanel = new javax.swing.JPanel();
        contactPanel = new javax.swing.JPanel();
        nicknameLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        webLabel = new javax.swing.JLabel();
        imLabel = new javax.swing.JLabel();
        nicknameTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        webTextField = new javax.swing.JTextField();
        imTextField = new javax.swing.JTextField();
        firstNameLabel4 = new javax.swing.JLabel();
        editmemberId = new javax.swing.JTextField();
        firstNameLabel5 = new javax.swing.JLabel();
        editbirthDate = new javax.swing.JTextField();
        surnameLabel4 = new javax.swing.JLabel();
        edittotalYearsWorkExperience = new javax.swing.JTextField();
        firstNameLabel6 = new javax.swing.JLabel();
        editcomments = new javax.swing.JTextField();
        surnameLabel5 = new javax.swing.JLabel();
        edityearsWithEmployer = new javax.swing.JTextField();
        surnameLabel6 = new javax.swing.JLabel();
        editcardId = new javax.swing.JTextField();

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        setMaximumSize(new java.awt.Dimension(900, 600));

        firstNameLabel.setText("Mr./Mrs./Dr:"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.person.firstName}"), editpersonalTitle, org.jdesktop.beansbinding.BeanProperty.create("text"), "firstName"); // NOI18N
        bindingGroup.addBinding(binding);

        surnameLabel.setText("First Name:"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.person.lastName}"), editfirstName, org.jdesktop.beansbinding.BeanProperty.create("text"), "surname"); // NOI18N
        bindingGroup.addBinding(binding);

        editmaritalStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single", "Married", "Separated", "Divorced" }));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.maritalStatus}"), editmaritalStatus, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"), "maritalStatus"); // NOI18N
        bindingGroup.addBinding(binding);

        sexLabel.setText("Sex:"); // NOI18N

        maritalStatusLabel.setText("Marital status:"); // NOI18N

        sexButtonGroup.add(maleRadioButton);
        maleRadioButton.setText("male"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.sex}"), maleRadioButton, org.jdesktop.beansbinding.BeanProperty.create("selected"), "sex"); // NOI18N
        bindingGroup.addBinding(binding);

        sexButtonGroup.add(femaleRadioButton);
        femaleRadioButton.setText("female"); // NOI18N

        ageLabel.setText("Age:"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.age}"), ageTextField, org.jdesktop.beansbinding.BeanProperty.create("text"), "age");
        bindingGroup.addBinding(binding);

        firstNameLabel2.setText("Party Id:"); // NOI18N

        firstNameLabel3.setText("Middle Name:"); // NOI18N

        surnameLabel2.setText("Last Name:"); // NOI18N

        surnameLabel3.setText("Position:"); // NOI18N

        org.jdesktop.layout.GroupLayout addressMailingPanelLayout = new org.jdesktop.layout.GroupLayout(addressMailingPanel);
        addressMailingPanel.setLayout(addressMailingPanelLayout);
        addressMailingPanelLayout.setHorizontalGroup(
            addressMailingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 510, Short.MAX_VALUE)
        );
        addressMailingPanelLayout.setVerticalGroup(
            addressMailingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 290, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Mailling Address:", addressMailingPanel);

        org.jdesktop.layout.GroupLayout addressDelivery1lPanelLayout = new org.jdesktop.layout.GroupLayout(addressDelivery1lPanel);
        addressDelivery1lPanel.setLayout(addressDelivery1lPanelLayout);
        addressDelivery1lPanelLayout.setHorizontalGroup(
            addressDelivery1lPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 510, Short.MAX_VALUE)
        );
        addressDelivery1lPanelLayout.setVerticalGroup(
            addressDelivery1lPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 290, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Delivery Address 1:", addressDelivery1lPanel);

        org.jdesktop.layout.GroupLayout addressDelivery2lPanelLayout = new org.jdesktop.layout.GroupLayout(addressDelivery2lPanel);
        addressDelivery2lPanel.setLayout(addressDelivery2lPanelLayout);
        addressDelivery2lPanelLayout.setHorizontalGroup(
            addressDelivery2lPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 510, Short.MAX_VALUE)
        );
        addressDelivery2lPanelLayout.setVerticalGroup(
            addressDelivery2lPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 290, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Delivery Address 2:", addressDelivery2lPanel);

        org.jdesktop.layout.GroupLayout addressPersonalPanelLayout = new org.jdesktop.layout.GroupLayout(addressPersonalPanel);
        addressPersonalPanel.setLayout(addressPersonalPanelLayout);
        addressPersonalPanelLayout.setHorizontalGroup(
            addressPersonalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 510, Short.MAX_VALUE)
        );
        addressPersonalPanelLayout.setVerticalGroup(
            addressPersonalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 290, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab4", addressPersonalPanel);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1)
        );

        org.jdesktop.layout.GroupLayout personalPanelLayout = new org.jdesktop.layout.GroupLayout(personalPanel);
        personalPanel.setLayout(personalPanelLayout);
        personalPanelLayout.setHorizontalGroup(
            personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(personalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(firstNameLabel2)
                    .add(firstNameLabel)
                    .add(surnameLabel)
                    .add(firstNameLabel3)
                    .add(surnameLabel2)
                    .add(surnameLabel3)
                    .add(maritalStatusLabel)
                    .add(ageLabel)
                    .add(sexLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(editpartyId)
                    .add(editpersonalTitle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .add(editfirstName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .add(editmiddleName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .add(editlastName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .add(editsalutation, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .add(editmaritalStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ageTextField)
                    .add(personalPanelLayout.createSequentialGroup()
                        .add(maleRadioButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(femaleRadioButton)))
                .addContainerGap(25, Short.MAX_VALUE))
            .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        personalPanelLayout.setVerticalGroup(
            personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(personalPanelLayout.createSequentialGroup()
                .add(21, 21, 21)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(firstNameLabel2)
                    .add(editpartyId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(firstNameLabel)
                    .add(editpersonalTitle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(surnameLabel)
                    .add(editfirstName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(firstNameLabel3)
                    .add(editmiddleName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(surnameLabel2)
                    .add(editlastName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(surnameLabel3)
                    .add(editsalutation, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(4, 4, 4)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maritalStatusLabel)
                    .add(editmaritalStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ageLabel)
                    .add(ageTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(sexLabel)
                    .add(femaleRadioButton)
                    .add(maleRadioButton))
                .addContainerGap(335, Short.MAX_VALUE))
            .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, personalPanelLayout.createSequentialGroup()
                    .addContainerGap(252, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        clientInfoPane.addTab("Personal", personalPanel);

        contactPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nicknameLabel.setText("Nickname:"); // NOI18N

        emailLabel.setText("E-mail:"); // NOI18N

        webLabel.setText("Web:"); // NOI18N

        imLabel.setText("IM:"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.nickname}"), nicknameTextField, org.jdesktop.beansbinding.BeanProperty.create("text"), "nickname"); // NOI18N
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.email}"), emailTextField, org.jdesktop.beansbinding.BeanProperty.create("text"), "email");
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.web}"), webTextField, org.jdesktop.beansbinding.BeanProperty.create("text"), "web");
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${client.im}"), imTextField, org.jdesktop.beansbinding.BeanProperty.create("text"), "im");
        bindingGroup.addBinding(binding);

        firstNameLabel4.setText("Member Id:"); // NOI18N

        firstNameLabel5.setText("Birth Date:"); // NOI18N

        surnameLabel4.setText("Work Exp:"); // NOI18N

        firstNameLabel6.setText("Comments"); // NOI18N

        surnameLabel5.setText("Year With"); // NOI18N

        surnameLabel6.setText("Card Id:"); // NOI18N

        org.jdesktop.layout.GroupLayout contactPanelLayout = new org.jdesktop.layout.GroupLayout(contactPanel);
        contactPanel.setLayout(contactPanelLayout);
        contactPanelLayout.setHorizontalGroup(
            contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(nicknameLabel)
                    .add(emailLabel)
                    .add(webLabel)
                    .add(imLabel)
                    .add(firstNameLabel4)
                    .add(firstNameLabel5)
                    .add(surnameLabel4)
                    .add(firstNameLabel6)
                    .add(surnameLabel5)
                    .add(surnameLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(nicknameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(emailTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(webTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(imTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(editmemberId)
                    .add(editbirthDate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(edittotalYearsWorkExperience, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(editcomments, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(edityearsWithEmployer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .add(editcardId, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
                .addContainerGap())
        );
        contactPanelLayout.setVerticalGroup(
            contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(nicknameLabel)
                    .add(nicknameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(emailLabel)
                    .add(emailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(webLabel)
                    .add(webTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(imLabel)
                    .add(imTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(firstNameLabel4)
                    .add(editmemberId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(firstNameLabel5)
                    .add(editbirthDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(surnameLabel4)
                    .add(edittotalYearsWorkExperience, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 68, Short.MAX_VALUE)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(firstNameLabel6)
                    .add(editcomments, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(surnameLabel5)
                    .add(edityearsWithEmployer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contactPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(surnameLabel6)
                    .add(editcardId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(245, 245, 245))
        );

        clientInfoPane.addTab("Contact", contactPanel);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(clientInfoPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(clientInfoPane))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                javax.swing.JFrame frame = new javax.swing.JFrame("Client Editor");
                frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//                frame.getContentPane().add(new ClientEditor());
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    final void addAddressPanelToTab(JPanel addressPanel, JPanel Container) {

        javax.swing.GroupLayout panelTreeContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(panelTreeContainerLayout);
        panelTreeContainerLayout.setHorizontalGroup(
                panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTreeContainerLayout.createSequentialGroup()
                .addComponent(addressPanel)
                .addGap(0, 0, Short.MAX_VALUE)));
        panelTreeContainerLayout.setVerticalGroup(
                panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTreeContainerLayout.createSequentialGroup()
                .addContainerGap(1, Short.MAX_VALUE)
                .addComponent(addressPanel)
                .addGap(1, 1, 1)));

//        panelTreeContainer..addComponent(treePanel);
        addressPanel.setVisible(true);
        Container.setVisible(true);;

    }

    public void setPartyUIFields() {
        Person person = client.person;
        editpartyId.setText(person.getpartyId());
        editpersonalTitle.setText(person.getpersonalTitle());
        editfirstName.setText(person.getfirstName());
        editmiddleName.setText(person.getmiddleName());
        editlastName.setText(person.getlastName());
        editsalutation.setText(person.getsalutation());
        nicknameTextField.setText(person.getsuffix());
//        editnickname.setText(person.getnickname());
//        editfirstNameLocal.setText(person.getfirstNameLocal());
//        editmiddleNameLocal.setText(person.getmiddleNameLocal());
//        editlastNameLocal.setText(person.getlastNameLocal());
//        editotherLocal.setText(person.getotherLocal());
        editmemberId.setText(person.getmemberId());
 //       editgender.setText(person.getgender());
//        editbirthDate.setText(person.getbirthDate());
//        editdeceasedDate.setText(person.getdeceasedDate());
//        editheight.setText(person.getheight());
//        editweight.setText(person.getweight());
//        editmothersMaidenName.setText(person.getmothersMaidenName());
//        editmaritalStatus.setText(person.getmaritalStatus());
//        editsocialSecurityNumber.setText(person.getsocialSecurityNumber());
//        editpassportNumber.setText(person.getpassportNumber());
//        editpassportExpireDate.setText(person.getpassportExpireDate());
//        edittotalYearsWorkExperience.setText(person.gettotalYearsWorkExperience());
        editcomments.setText(person.getcomments());
//        editemploymentStatusEnumId.setText(person.getemploymentStatusEnumId());
//        editresidenceStatusEnumId.setText(person.getresidenceStatusEnumId());
//        editoccupation.setText(person.getoccupation());
//        edityearsWithEmployer.setText(person.getyearsWithEmployer());
//        editmonthsWithEmployer.setText(person.getmonthsWithEmployer());
//        editexistingCustomer.setText(person.getexistingCustomer());
        editcardId.setText(person.getcardId());
    }

public void getPartyUIFields(){
        Person person = client.person;
    person.setpartyId(editpartyId.getText());
person.setsalutation(editsalutation.getText());
person.setfirstName(editfirstName.getText());
person.setmiddleName(editmiddleName.getText());
person.setlastName(editlastName.getText());
person.setpersonalTitle(editpersonalTitle.getText());
person.setsuffix(nicknameTextField.getText());
//person.setnickname(editnickname.getText());
//person.setfirstNameLocal(editfirstNameLocal.getText());
//person.setmiddleNameLocal(editmiddleNameLocal.getText());
//person.setlastNameLocal(editlastNameLocal.getText());
//person.setotherLocal(editotherLocal.getText());
person.setmemberId(editmemberId.getText());
//person.setgender(editgender.getText());
//person.setbirthDate(editbirthDate.getText());
//person.setdeceasedDate(editdeceasedDate.getText());/
//person.setheight(editheight.getText());
//person.setweight(editweight.getText());
//person.setmothersMaidenName(editmothersMaidenName.getText());
//person.setmaritalStatus(editmaritalStatus.getText());
//person.setsocialSecurityNumber(editsocialSecurityNumber.getText());
//person.setpassportNumber(editpassportNumber.getText());
//person.setpassportExpireDate(editpassportExpireDate.getText());
//person.settotalYearsWorkExperience(edittotalYearsWorkExperience.getText());
person.setcomments(editcomments.getText());
//person.setemploymentStatusEnumId(editemploymentStatusEnumId.getText());
//person.setresidenceStatusEnumId(editresidenceStatusEnumId.getText());
//person.setoccupation(editoccupation.getText());
//person.setyearsWithEmployer(edityearsWithEmployer.getText());
//person.setmonthsWithEmployer(editmonthsWithEmployer.getText());
//person.setexistingCustomer(editexistingCustomer.getText());
person.setcardId(editcardId.getText());
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addressDelivery1lPanel;
    private javax.swing.JPanel addressDelivery2lPanel;
    private javax.swing.JPanel addressMailingPanel;
    private javax.swing.JPanel addressPersonalPanel;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JTextField ageTextField;
    private javax.swing.JTabbedPane clientInfoPane;
    private javax.swing.JPanel contactPanel;
    private javax.swing.JTextField editbirthDate;
    private javax.swing.JTextField editcardId;
    private javax.swing.JTextField editcomments;
    private javax.swing.JTextField editfirstName;
    private javax.swing.JTextField editlastName;
    private javax.swing.JComboBox editmaritalStatus;
    private javax.swing.JTextField editmemberId;
    private javax.swing.JTextField editmiddleName;
    private javax.swing.JTextField editpartyId;
    private javax.swing.JTextField editpersonalTitle;
    private javax.swing.JTextField editsalutation;
    private javax.swing.JTextField edittotalYearsWorkExperience;
    private javax.swing.JTextField edityearsWithEmployer;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JRadioButton femaleRadioButton;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JLabel firstNameLabel2;
    private javax.swing.JLabel firstNameLabel3;
    private javax.swing.JLabel firstNameLabel4;
    private javax.swing.JLabel firstNameLabel5;
    private javax.swing.JLabel firstNameLabel6;
    private javax.swing.JLabel imLabel;
    private javax.swing.JTextField imTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton maleRadioButton;
    private javax.swing.JLabel maritalStatusLabel;
    private javax.swing.JLabel nicknameLabel;
    private javax.swing.JTextField nicknameTextField;
    private javax.swing.JPanel personalPanel;
    private javax.swing.ButtonGroup sexButtonGroup;
    private javax.swing.JLabel sexLabel;
    private javax.swing.JLabel surnameLabel;
    private javax.swing.JLabel surnameLabel2;
    private javax.swing.JLabel surnameLabel3;
    private javax.swing.JLabel surnameLabel4;
    private javax.swing.JLabel surnameLabel5;
    private javax.swing.JLabel surnameLabel6;
    private javax.swing.JLabel webLabel;
    private javax.swing.JTextField webTextField;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
