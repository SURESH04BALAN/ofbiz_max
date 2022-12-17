package org.ofbiz.ordermax.party;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.entity.Person;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class PersonPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = PersonPanel.class.getName();

    private Person person;

    private javax.swing.JLabel lastNameLocalLabel;

    private javax.swing.JTextField lastNameLocalTextField;

    private javax.swing.JLabel firstNameLabel;

    private javax.swing.JTextField firstNameTextField;

    private javax.swing.JLabel socialSecurityNumberLabel;

    private javax.swing.JTextField socialSecurityNumberTextField;

    private javax.swing.JLabel commentsLabel;

    private javax.swing.JTextField commentsTextField;

    private javax.swing.JLabel nicknameLabel;

    private javax.swing.JTextField nicknameTextField;

    private javax.swing.JLabel deceasedDateLabel;

    private javax.swing.JTextField deceasedDateTextField;

    private javax.swing.JLabel residenceStatusEnumIdLabel;

    private javax.swing.JTextField residenceStatusEnumIdTextField;

    private javax.swing.JLabel middleNameLocalLabel;

    private javax.swing.JTextField middleNameLocalTextField;

    private javax.swing.JLabel firstNameLocalLabel;

    private javax.swing.JTextField firstNameLocalTextField;

    private javax.swing.JLabel partyIdLabel;

    private javax.swing.JTextField partyIdTextField;

    private javax.swing.JLabel genderLabel;

    private javax.swing.JTextField genderTextField;

    private javax.swing.JLabel passportExpireDateLabel;

    private javax.swing.JTextField passportExpireDateTextField;

    private javax.swing.JLabel suffixLabel;

    private javax.swing.JTextField suffixTextField;

    private javax.swing.JLabel passportNumberLabel;

    private javax.swing.JTextField passportNumberTextField;

    private javax.swing.JLabel otherLocalLabel;

    private javax.swing.JTextField otherLocalTextField;

    private javax.swing.JLabel cardIdLabel;

    private javax.swing.JTextField cardIdTextField;

    private javax.swing.JLabel totalYearsWorkExperienceLabel;

    private javax.swing.JTextField totalYearsWorkExperienceTextField;

    private javax.swing.JLabel heightLabel;

    private javax.swing.JTextField heightTextField;

    private javax.swing.JLabel monthsWithEmployerLabel;

    private javax.swing.JTextField monthsWithEmployerTextField;

    private javax.swing.JLabel yearsWithEmployerLabel;

    private javax.swing.JTextField yearsWithEmployerTextField;

    private javax.swing.JLabel middleNameLabel;

    private javax.swing.JTextField middleNameTextField;

    private javax.swing.JLabel weightLabel;

    private javax.swing.JTextField weightTextField;

    private javax.swing.JLabel memberIdLabel;

    private javax.swing.JTextField memberIdTextField;

    private javax.swing.JLabel lastNameLabel;

    private javax.swing.JTextField lastNameTextField;

    private javax.swing.JLabel mothersMaidenNameLabel;

    private javax.swing.JTextField mothersMaidenNameTextField;

    private javax.swing.JLabel employmentStatusEnumIdLabel;

    private javax.swing.JTextField employmentStatusEnumIdTextField;

    private javax.swing.JLabel birthDateLabel;

    private javax.swing.JTextField birthDateTextField;

    private javax.swing.JLabel salutationLabel;

    private javax.swing.JTextField salutationTextField;

    private javax.swing.JLabel occupationLabel;

    private javax.swing.JTextField occupationTextField;

    private javax.swing.JLabel existingCustomerLabel;

    private javax.swing.JTextField existingCustomerTextField;

    private javax.swing.JLabel maritalStatusLabel;

    private javax.swing.JTextField maritalStatusTextField;

    private javax.swing.JLabel personalTitleLabel;

    private javax.swing.JTextField personalTitleTextField;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }

    private boolean isModified = false;

    public PersonPanel(Person val) {

        this.person = val;

        initComponents();

    }

    public PersonPanel() {

        initComponents();

    }

    private void initComponents() {

        lastNameLocalLabel = new javax.swing.JLabel();

        lastNameLocalTextField = new javax.swing.JTextField();

        lastNameLocalTextField.getDocument().addDocumentListener(new TextChangeListner());

        firstNameLabel = new javax.swing.JLabel();

        firstNameTextField = new javax.swing.JTextField();

        firstNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        socialSecurityNumberLabel = new javax.swing.JLabel();

        socialSecurityNumberTextField = new javax.swing.JTextField();

        socialSecurityNumberTextField.getDocument().addDocumentListener(new TextChangeListner());

        commentsLabel = new javax.swing.JLabel();

        commentsTextField = new javax.swing.JTextField();

        commentsTextField.getDocument().addDocumentListener(new TextChangeListner());

        nicknameLabel = new javax.swing.JLabel();

        nicknameTextField = new javax.swing.JTextField();

        nicknameTextField.getDocument().addDocumentListener(new TextChangeListner());

        deceasedDateLabel = new javax.swing.JLabel();

        deceasedDateTextField = new javax.swing.JTextField();

        deceasedDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        residenceStatusEnumIdLabel = new javax.swing.JLabel();

        residenceStatusEnumIdTextField = new javax.swing.JTextField();

        residenceStatusEnumIdTextField.getDocument().addDocumentListener(new TextChangeListner());

//        button = new JButton("...");
//        button.addActionListener(new LookupActionListner(residenceStatusEnumIdTextField, "residenceStatusEnumIdTextField"));
        middleNameLocalLabel = new javax.swing.JLabel();

        middleNameLocalTextField = new javax.swing.JTextField();

        middleNameLocalTextField.getDocument().addDocumentListener(new TextChangeListner());

        firstNameLocalLabel = new javax.swing.JLabel();

        firstNameLocalTextField = new javax.swing.JTextField();

        firstNameLocalTextField.getDocument().addDocumentListener(new TextChangeListner());

        partyIdLabel = new javax.swing.JLabel();

        partyIdTextField = new javax.swing.JTextField();

        partyIdTextField.getDocument().addDocumentListener(new TextChangeListner());

//        cb.install(partyIdTextField);
//        button.addActionListener(new LookupActionListner(partyIdTextField, "partyIdTextField"));
        genderLabel = new javax.swing.JLabel();

        genderTextField = new javax.swing.JTextField();

        genderTextField.getDocument().addDocumentListener(new TextChangeListner());

        passportExpireDateLabel = new javax.swing.JLabel();

        passportExpireDateTextField = new javax.swing.JTextField();

        passportExpireDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        suffixLabel = new javax.swing.JLabel();

        suffixTextField = new javax.swing.JTextField();

        suffixTextField.getDocument().addDocumentListener(new TextChangeListner());

        passportNumberLabel = new javax.swing.JLabel();

        passportNumberTextField = new javax.swing.JTextField();

        passportNumberTextField.getDocument().addDocumentListener(new TextChangeListner());

        otherLocalLabel = new javax.swing.JLabel();

        otherLocalTextField = new javax.swing.JTextField();

        otherLocalTextField.getDocument().addDocumentListener(new TextChangeListner());

        cardIdLabel = new javax.swing.JLabel();

        cardIdTextField = new javax.swing.JTextField();

        cardIdTextField.getDocument().addDocumentListener(new TextChangeListner());

//        cb.install(cardIdTextField);
//        button.addActionListener(new LookupActionListner(cardIdTextField, "cardIdTextField"));
        totalYearsWorkExperienceLabel = new javax.swing.JLabel();

        totalYearsWorkExperienceTextField = new javax.swing.JTextField();

        totalYearsWorkExperienceTextField.getDocument().addDocumentListener(new TextChangeListner());

        heightLabel = new javax.swing.JLabel();

        heightTextField = new javax.swing.JTextField();

        heightTextField.getDocument().addDocumentListener(new TextChangeListner());

        monthsWithEmployerLabel = new javax.swing.JLabel();

        monthsWithEmployerTextField = new javax.swing.JTextField();

        monthsWithEmployerTextField.getDocument().addDocumentListener(new TextChangeListner());

        yearsWithEmployerLabel = new javax.swing.JLabel();

        yearsWithEmployerTextField = new javax.swing.JTextField();

        yearsWithEmployerTextField.getDocument().addDocumentListener(new TextChangeListner());

        middleNameLabel = new javax.swing.JLabel();

        middleNameTextField = new javax.swing.JTextField();

        middleNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        weightLabel = new javax.swing.JLabel();

        weightTextField = new javax.swing.JTextField();

        weightTextField.getDocument().addDocumentListener(new TextChangeListner());

        memberIdLabel = new javax.swing.JLabel();

        memberIdTextField = new javax.swing.JTextField();

        memberIdTextField.getDocument().addDocumentListener(new TextChangeListner());

//        cb.install(memberIdTextField);
//        button.addActionListener(new LookupActionListner(memberIdTextField, "memberIdTextField"));
        lastNameLabel = new javax.swing.JLabel();

        lastNameTextField = new javax.swing.JTextField();

        lastNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        mothersMaidenNameLabel = new javax.swing.JLabel();

        mothersMaidenNameTextField = new javax.swing.JTextField();

        mothersMaidenNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        employmentStatusEnumIdLabel = new javax.swing.JLabel();

        employmentStatusEnumIdTextField = new javax.swing.JTextField();

        employmentStatusEnumIdTextField.getDocument().addDocumentListener(new TextChangeListner());

//        cb.install(employmentStatusEnumIdTextField);
//        button.addActionListener(new LookupActionListner(employmentStatusEnumIdTextField, "employmentStatusEnumIdTextField"));
        birthDateLabel = new javax.swing.JLabel();

        birthDateTextField = new javax.swing.JTextField();

        birthDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        salutationLabel = new javax.swing.JLabel();

        salutationTextField = new javax.swing.JTextField();

        salutationTextField.getDocument().addDocumentListener(new TextChangeListner());

        occupationLabel = new javax.swing.JLabel();

        occupationTextField = new javax.swing.JTextField();

        occupationTextField.getDocument().addDocumentListener(new TextChangeListner());

        existingCustomerLabel = new javax.swing.JLabel();

        existingCustomerTextField = new javax.swing.JTextField();

        existingCustomerTextField.getDocument().addDocumentListener(new TextChangeListner());

        maritalStatusLabel = new javax.swing.JLabel();

        maritalStatusTextField = new javax.swing.JTextField();

        maritalStatusTextField.getDocument().addDocumentListener(new TextChangeListner());

        personalTitleLabel = new javax.swing.JLabel();

        personalTitleTextField = new javax.swing.JTextField();

        personalTitleTextField.getDocument().addDocumentListener(new TextChangeListner());

        lastNameLocalLabel.setText("Last Name Local:");

        firstNameLabel.setText("First Name:");

        socialSecurityNumberLabel.setText("Social Security Number:");

        commentsLabel.setText("Comments:");

        nicknameLabel.setText("Nickname:");

        deceasedDateLabel.setText("Deceased Date:");

        residenceStatusEnumIdLabel.setText("Residence Status Enum Id:");

        middleNameLocalLabel.setText("Middle Name Local:");

        firstNameLocalLabel.setText("First Name Local:");

        partyIdLabel.setText("Party Id:");

        genderLabel.setText("Gender:");

        passportExpireDateLabel.setText("Passport Expire Date:");

        suffixLabel.setText("Suffix:");

        passportNumberLabel.setText("Passport Number:");

        otherLocalLabel.setText("Other Local:");

        cardIdLabel.setText("Card Id:");

        totalYearsWorkExperienceLabel.setText("Total Years Work Experience:");

        heightLabel.setText("Height:");

        monthsWithEmployerLabel.setText("Months With Employer:");

        yearsWithEmployerLabel.setText("Years With Employer:");

        middleNameLabel.setText("Middle Name:");

        weightLabel.setText("Weight:");

        memberIdLabel.setText("Member Id:");

        lastNameLabel.setText("Last Name:");

        mothersMaidenNameLabel.setText("Mothers Maiden Name:");

        employmentStatusEnumIdLabel.setText("Employment Status Enum Id:");

        birthDateLabel.setText("Birth Date:");

        salutationLabel.setText("Salutation:");

        occupationLabel.setText("Occupation:");

        existingCustomerLabel.setText("Existing Customer:");

        maritalStatusLabel.setText("Marital Status:");

        personalTitleLabel.setText("Personal Title:");

//        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);
//        this.setLayout(layoutPanel);
        /*      gbc.gridx = 0;
         gbc.gridy = i;
         panel.add(thingNameLabel, gbc);

         gbc.gridx = 1;
         gbc.gridy = i;
         gbc.gridwidth = 2;
         gbc.fill = GridBagConstraints.NONE;
         panel.add(facilityComboModel.jComboBox, gbc);

         i++;

         gbc.gridx = 1;
         gbc.gridy = i;
         gbc.gridwidth = 2;
         panel.add(checkbox1, gbc);

         i++;

         gbc.gridx = 0;
         gbc.gridy = i;
         gbc.gridwidth = 1;
         gbc.fill = GridBagConstraints.NONE;
         panel.add(anAttributeLabel, gbc);
         */
        /*
         layoutPanel.setHorizontalGroup(
         layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(layoutPanel.createSequentialGroup()
         .addContainerGap()
         .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(cardIdLabel)
         .add(partyIdLabel)
         .add(memberIdLabel)
         .add(employmentStatusEnumIdLabel)
         .add(residenceStatusEnumIdLabel)
         .add(weightLabel)
         .add(otherLocalLabel)
         .add(passportExpireDateLabel)
         .add(passportNumberLabel)
         .add(maritalStatusLabel)
         .add(monthsWithEmployerLabel)
         .add(mothersMaidenNameLabel)
         .add(heightLabel)
         .add(lastNameLocalLabel)
         .add(yearsWithEmployerLabel)
         .add(middleNameLocalLabel)
         )
         .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
         .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(cardIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(partyIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(memberIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(employmentStatusEnumIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(residenceStatusEnumIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(weightTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(otherLocalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(passportExpireDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(passportNumberTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(maritalStatusTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(monthsWithEmployerTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(mothersMaidenNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(heightTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(lastNameLocalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(yearsWithEmployerTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(middleNameLocalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         )
         .addContainerGap()
         .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(genderLabel)
         .add(birthDateLabel)
         .add(totalYearsWorkExperienceLabel)
         .add(firstNameLabel)
         .add(middleNameLabel)
         .add(lastNameLabel)
         .add(occupationLabel)
         .add(nicknameLabel)
         .add(existingCustomerLabel)
         .add(firstNameLocalLabel)
         .add(socialSecurityNumberLabel)
         .add(suffixLabel)
         .add(personalTitleLabel)
         .add(deceasedDateLabel)
         .add(salutationLabel)
         .add(commentsLabel)
         )
         .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
         .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
         .add(genderTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(birthDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(totalYearsWorkExperienceTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(firstNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(middleNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(lastNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(occupationTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(nicknameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(existingCustomerTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(firstNameLocalTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(socialSecurityNumberTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(suffixTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(personalTitleTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(deceasedDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(salutationTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         .add(commentsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
         )
         .addContainerGap()
         ));
         */
        /*       this.setLayout(new GridBagLayout());

         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(2, 2, 2, 2);
         gbc.anchor = GridBagConstraints.NORTHEAST;
         if (shouldFill) {
         //natural height, maximum width
         gbc.fill = GridBagConstraints.HORIZONTAL;
         }


         ArrayList<Object> arrayList = new ArrayList<Object>();
         //        arrayList.add(partyIdLabel);
         //        arrayList.add(partyIdTextField);
         int yIndex = 0;
         int xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);
         arrayList.clear();
         arrayList.add(salutationLabel);//
         arrayList.add(salutationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         //        yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(firstNameLabel);
         arrayList.add(firstNameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(middleNameLabel);//
         arrayList.add(middleNameTextField);//org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(lastNameLabel);//
         arrayList.add(lastNameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(personalTitleLabel);//
         arrayList.add(personalTitleTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(suffixLabel);//
         arrayList.add(suffixTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(nicknameLabel);//
         arrayList.add(nicknameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))

         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(firstNameLocalLabel);//
         arrayList.add(firstNameLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(middleNameLocalLabel);//
         arrayList.add(middleNameLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
         arrayList.add(lastNameLocalLabel);//
         arrayList.add(lastNameLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(otherLocalLabel);//
         arrayList.add(otherLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(mothersMaidenNameLabel);//
         arrayList.add(mothersMaidenNameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
         arrayList.add(passportExpireDateLabel);//
         arrayList.add(passportExpireDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(maritalStatusLabel);//
         arrayList.add(maritalStatusTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(genderLabel);
         arrayList.add(genderTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(passportNumberLabel);//
         arrayList.add(passportNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(birthDateLabel);
         arrayList.add(birthDateTextField);
         arrayList.add(deceasedDateLabel);//
         arrayList.add(deceasedDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
         arrayList.add(monthsWithEmployerLabel);//
         arrayList.add(monthsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(weightLabel);//
         arrayList.add(weightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(heightLabel);//
         arrayList.add(heightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(yearsWithEmployerLabel);//
         arrayList.add(yearsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(occupationLabel);//         
         arrayList.add(occupationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(employmentStatusEnumIdLabel);
         arrayList.add(employmentStatusEnumIdTextField);
         arrayList.add(totalYearsWorkExperienceLabel);
         arrayList.add(totalYearsWorkExperienceTextField);
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(cardIdLabel);
         arrayList.add(cardIdTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(residenceStatusEnumIdLabel);//
         arrayList.add(residenceStatusEnumIdTextField);// org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
         arrayList.add(socialSecurityNumberLabel);//
         arrayList.add(socialSecurityNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(existingCustomerLabel);//
         arrayList.add(existingCustomerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(memberIdLabel);
         arrayList.add(memberIdTextField);
         arrayList.add(commentsLabel);//
         arrayList.add(commentsTextField);//, org.jdesktop.layout.GroupLayout.          
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);
         */
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        this.add(topPanel);

        // Create the tab pages
        createPage1();
        createPage2();
        createPage3();

        // Create a tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Name", panel1);
        tabbedPane.addTab("Personal Details", panel2);
        tabbedPane.addTab("Employment Details", panel3);
        topPanel.add(tabbedPane, BorderLayout.CENTER);

        /*        arrayList.clear();
         arrayList.add(passportNumberLabel);//
         arrayList.add(passportNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(passportExpireDateLabel);//
         arrayList.add(passportExpireDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);
         */
        /*      arrayList.clear();
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(monthsWithEmployerLabel);//
         arrayList.add(monthsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(yearsWithEmployerLabel);//
         arrayList.add(yearsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);
         */
        /*        arrayList.clear();

         arrayList.add(commentsLabel);//
         arrayList.add(commentsTextField);//, org.jdesktop.layout.GroupLayout.        
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);
         */
        //     arrayList.clear();
        //     arrayList.add(commentsLabel);//
        //     arrayList.add(commentsTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        //     yIndex++;
        //     xIndex = 0;
        //     setHorizontalGridBag(yIndex, xIndex, gbc, arrayList);
    }

    void createPage1() {
        panel1 = new JPanel();
        panel1.setLayout(null);
        //salutationLabel;//
        //arrayList.add(salutationTextField);
  /*      salutationLabel.setBounds(10, 15, 150, 24);
         salutationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
         panel1.add(salutationLabel);

         salutationTextField.setBounds(170, 15, 250, 24);
         //salutationTextField.setHorizontalAlignment(SwingConstants.RIGHT);
         panel1.add(salutationTextField);
         */
        //  JPanel panelLeft = new JPanel();
        //   panelLeft.setBounds( 20, 15, 400, 200 );
        //  JPanel panelRight = new JPanel();
        //  panelRight.setBounds( 165, 15, 400, 200 );
        // panel1.setLayout(null);
 /*       panel1.setLayout(new FlowLayout());

         // panel1.setLayout(null);
         // panelLeft.setLayout(new GridBagLayout());
         // panelRight.setLayout(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(2, 2, 2, 2);
         gbc.anchor = GridBagConstraints.NORTHEAST;
         if (shouldFill) {
         //natural height, maximum width
         gbc.fill = GridBagConstraints.HORIZONTAL;
         }
         */
        ArrayList<Object> arrayList = new ArrayList<Object>();
        //        arrayList.add(partyIdLabel);
        //        arrayList.add(partyIdTextField);
        int yIndex = 20;
        int xIndex = 10;
        //   setHorizontalGridBag(panelLeft, yIndex, xIndex, gbc, arrayList);
        arrayList.clear();
        arrayList.add(salutationLabel);//
        arrayList.add(salutationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        //        yIndex++;
        //xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(firstNameLabel);
        arrayList.add(firstNameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
//        yIndex += 30;
        //xIndex = 10;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(middleNameLabel);//
        arrayList.add(middleNameTextField);//org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        //yIndex++;
        // xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(lastNameLabel);//
        arrayList.add(lastNameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        //     yIndex++;
        //     xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(personalTitleLabel);//
        arrayList.add(personalTitleTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        /// yIndex++;
        //xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);
        arrayList.clear();
        arrayList.add(suffixLabel);//
        arrayList.add(suffixTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        //   yIndex++;
        //    xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);
        arrayList.clear();
        arrayList.add(nicknameLabel);//
        arrayList.add(nicknameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        yIndex = 20;
        xIndex = 420;
        arrayList.clear();
        arrayList.add(firstNameLocalLabel);//
        arrayList.add(firstNameLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        //yIndex++;

        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(middleNameLocalLabel);//
        arrayList.add(middleNameLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
        //yIndex++;
        // xIndex = 0;

        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);
        arrayList.clear();
        arrayList.add(lastNameLocalLabel);//
        arrayList.add(lastNameLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

        //yIndex++;
        //xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(otherLocalLabel);//
        arrayList.add(otherLocalTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        //yIndex++;
        //xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(mothersMaidenNameLabel);//
        arrayList.add(mothersMaidenNameTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
        //        arrayList.add(passportExpireDateLabel);//
        //        arrayList.add(passportExpireDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
        //yIndex++;
        //xIndex = 0;
        yIndex = setHorizontalGridPanel(panel1, yIndex, xIndex, arrayList);
        //     panel1.add(BorderLayout.EAST ,panelLeft);
        //     panel1.add(BorderLayout.WEST ,panelRight);    

        /*
         arrayList.clear();
         arrayList.add(maritalStatusLabel);//
         arrayList.add(maritalStatusTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(genderLabel);
         arrayList.add(genderTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(passportNumberLabel);//
         arrayList.add(passportNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel1,yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(birthDateLabel);
         arrayList.add(birthDateTextField);
         arrayList.add(deceasedDateLabel);//
         arrayList.add(deceasedDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
         arrayList.add(monthsWithEmployerLabel);//
         arrayList.add(monthsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel1,yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(weightLabel);//
         arrayList.add(weightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(heightLabel);//
         arrayList.add(heightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(yearsWithEmployerLabel);//
         arrayList.add(yearsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel1,yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(occupationLabel);//         
         arrayList.add(occupationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(employmentStatusEnumIdLabel);
         arrayList.add(employmentStatusEnumIdTextField);
         arrayList.add(totalYearsWorkExperienceLabel);
         arrayList.add(totalYearsWorkExperienceTextField);
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel1,yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(cardIdLabel);
         arrayList.add(cardIdTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(residenceStatusEnumIdLabel);//
         arrayList.add(residenceStatusEnumIdTextField);// org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
         arrayList.add(socialSecurityNumberLabel);//
         arrayList.add(socialSecurityNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel1,yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(existingCustomerLabel);//
         arrayList.add(existingCustomerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(memberIdLabel);
         arrayList.add(memberIdTextField);
         arrayList.add(commentsLabel);//
         arrayList.add(commentsTextField);//, org.jdesktop.layout.GroupLayout.          
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel1,yIndex, xIndex, gbc, arrayList);
         */
    }

    void createPage2() {
        panel2 = new JPanel();
        panel2.setLayout(null);
        // panel2.setLayout(new GridBagLayout());
/*
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(2, 2, 2, 2);
         gbc.anchor = GridBagConstraints.NORTHEAST;
         if (shouldFill) {
         //natural height, maximum width
         gbc.fill = GridBagConstraints.HORIZONTAL;
         }
         */
        ArrayList<Object> arrayList = new ArrayList<Object>();
        int yIndex = 20;
        int xIndex = 10;

        arrayList.clear();
        arrayList.add(passportNumberLabel);//
        arrayList.add(passportNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(passportExpireDateLabel);//
        arrayList.add(passportExpireDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(maritalStatusLabel);//
        arrayList.add(maritalStatusTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(genderLabel);
        arrayList.add(genderTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(birthDateLabel);
        arrayList.add(birthDateTextField);
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(deceasedDateLabel);//
        arrayList.add(deceasedDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(weightLabel);//
        arrayList.add(weightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(heightLabel);//
        arrayList.add(heightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        yIndex = 20;
        xIndex = 420;

//        arrayList.clear();
//        arrayList.add(occupationLabel);//         
//        arrayList.add(occupationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
//        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);
        arrayList.clear();
        arrayList.add(cardIdLabel);
        arrayList.add(cardIdTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(residenceStatusEnumIdLabel);//
        arrayList.add(residenceStatusEnumIdTextField);// org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(socialSecurityNumberLabel);//
        arrayList.add(socialSecurityNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))                
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(existingCustomerLabel);//
        arrayList.add(existingCustomerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(memberIdLabel);
        arrayList.add(memberIdTextField);
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);

        arrayList.clear();
        arrayList.add(commentsLabel);//
        arrayList.add(commentsTextField);//, org.jdesktop.layout.GroupLayout.   
        yIndex = setHorizontalGridPanel(panel2, yIndex, xIndex, arrayList);
        /*        
         arrayList.add(monthsWithEmployerLabel);//
         arrayList.add(monthsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel2, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(weightLabel);//
         arrayList.add(weightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(heightLabel);//
         arrayList.add(heightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(yearsWithEmployerLabel);//
         arrayList.add(yearsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel2, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(occupationLabel);//         
         arrayList.add(occupationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(employmentStatusEnumIdLabel);
         arrayList.add(employmentStatusEnumIdTextField);
         arrayList.add(totalYearsWorkExperienceLabel);
         arrayList.add(totalYearsWorkExperienceTextField);
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel2, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(cardIdLabel);
         arrayList.add(cardIdTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(residenceStatusEnumIdLabel);//
         arrayList.add(residenceStatusEnumIdTextField);// org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
         arrayList.add(socialSecurityNumberLabel);//
         arrayList.add(socialSecurityNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel2, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(existingCustomerLabel);//
         arrayList.add(existingCustomerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(memberIdLabel);
         arrayList.add(memberIdTextField);
         arrayList.add(commentsLabel);//
         arrayList.add(commentsTextField);//, org.jdesktop.layout.GroupLayout.          
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel2, yIndex, xIndex, gbc, arrayList);
         */
    }

    void createPage3() {
        panel3 = new JPanel();
        panel3.setLayout(null);

        ArrayList<Object> arrayList = new ArrayList<Object>();
        int yIndex = 20;
        int xIndex = 10;

      arrayList.clear();
        arrayList.add(occupationLabel);//         
        arrayList.add(occupationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        yIndex = setHorizontalGridPanel(panel3, yIndex, xIndex, arrayList);
        
        arrayList.clear();
        arrayList.add(monthsWithEmployerLabel);//
        arrayList.add(monthsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)       
        yIndex = setHorizontalGridPanel(panel3, yIndex, xIndex, arrayList);
        arrayList.clear();
        arrayList.add(yearsWithEmployerLabel);//
        arrayList.add(yearsWithEmployerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)         
        yIndex = setHorizontalGridPanel(panel3, yIndex, xIndex, arrayList);

  
        arrayList.clear();
        arrayList.add(employmentStatusEnumIdLabel);
        arrayList.add(employmentStatusEnumIdTextField);
        yIndex = setHorizontalGridPanel(panel3, yIndex, xIndex, arrayList);
        arrayList.clear();
        arrayList.add(totalYearsWorkExperienceLabel);
        arrayList.add(totalYearsWorkExperienceTextField);
        yIndex = setHorizontalGridPanel(panel3, yIndex, xIndex, arrayList);
        /*
         //        arrayList.add(partyIdLabel);
         //        arrayList.add(partyIdTextField);
         int yIndex = 0;
         int xIndex = 0;
         xIndex = 0;
         setHorizontalGridBag(panel3, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(maritalStatusLabel);//
         arrayList.add(maritalStatusTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(genderLabel);
         arrayList.add(genderTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel3, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(birthDateLabel);
         arrayList.add(birthDateTextField);
         arrayList.add(deceasedDateLabel);//
         arrayList.add(deceasedDateTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel3, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(weightLabel);//
         arrayList.add(weightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(heightLabel);//
         arrayList.add(heightTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)

         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel3, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(occupationLabel);//         
         arrayList.add(occupationTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(employmentStatusEnumIdLabel);
         arrayList.add(employmentStatusEnumIdTextField);
         arrayList.add(totalYearsWorkExperienceLabel);
         arrayList.add(totalYearsWorkExperienceTextField);
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel3, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(cardIdLabel);
         arrayList.add(cardIdTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
         arrayList.add(residenceStatusEnumIdLabel);//
         arrayList.add(residenceStatusEnumIdTextField);// org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)        
         arrayList.add(socialSecurityNumberLabel);//
         arrayList.add(socialSecurityNumberTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))        
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel3, yIndex, xIndex, gbc, arrayList);

         arrayList.clear();
         arrayList.add(existingCustomerLabel);//
         arrayList.add(existingCustomerTextField);//, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
         arrayList.add(memberIdLabel);
         arrayList.add(memberIdTextField);
         arrayList.add(commentsLabel);//
         arrayList.add(commentsTextField);//, org.jdesktop.layout.GroupLayout.          
         yIndex++;
         xIndex = 0;
         setHorizontalGridBag(panel3, yIndex, xIndex, gbc, arrayList);
         */
    }

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private JTabbedPane tabbedPane;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    public void setHorizontalGridBag(JPanel panel, Integer yIndex, Integer xIndex, GridBagConstraints gbc, ArrayList<Object> values) {

        for (Object val : values) {
            if (val instanceof JLabel) {
                gbc.gridx = xIndex++;
                gbc.gridy = yIndex;
                gbc.gridwidth = 1;
//                gbc.fill = GridBagConstraints.HORIZONTAL;                
                gbc.weightx = 0;
                gbc.insets = new Insets(2, 7, 2, 2);
                JLabel jLabel = (JLabel) val;
                jLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                panel.add(jLabel, gbc);
            } else if (val instanceof JTextField) {
                gbc.gridx = xIndex;
                gbc.gridy = yIndex;
                gbc.gridwidth = 2;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 0.5;
                gbc.insets = new Insets(2, 2, 2, 2);
                panel.add((JTextField) val, gbc);
                xIndex++;
                xIndex++;
            }
        }
    }
    /*
     salutationLabel.setBounds (

     10, 15, 150, 24);
     salutationLabel.setHorizontalAlignment (SwingConstants.RIGHT);

     panel1.add (salutationLabel);

     salutationTextField.setBounds (

     170, 15, 250, 24);
     //salutationTextField.setHorizontalAlignment(SwingConstants.RIGHT);
     panel1.add (salutationTextField);
     */

    public int setHorizontalGridPanel(JPanel panel, Integer yIndex, Integer xIndex, ArrayList<Object> values) {
        int labelWidth = 150;
        int txtWidth = 250;
        int height = 24;
        for (Object val : values) {
            if (val instanceof JLabel) {
                /*gbc.gridx = xIndex++;
                 gbc.gridy = yIndex;
                 gbc.gridwidth = 1;
                 //                gbc.fill = GridBagConstraints.HORIZONTAL;                
                 gbc.weightx = 0;
                 gbc.insets = new Insets(2, 7, 2, 2);*/
                JLabel jLabel = (JLabel) val;
                //jLabel.setHorizontalAlignment(SwingConstants.TRAILING);
                //panel.add(jLabel, gbc);*
                jLabel.setBounds(10 + xIndex, yIndex, labelWidth, height);
                jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                panel.add(jLabel);
            } else if (val instanceof JTextField) {
                /* gbc.gridx = xIndex;
                 gbc.gridy = yIndex;
                 gbc.gridwidth = 2;
                 gbc.fill = GridBagConstraints.HORIZONTAL;
                 gbc.weightx = 0.5;
                 gbc.insets = new Insets(2, 2, 2, 2);
                 panel.add((JTextField) val, gbc);
                 xIndex++;
                 xIndex++;*/

                JTextField text = (JTextField) val;
                text.setBounds(10 + labelWidth + xIndex, yIndex, txtWidth, height);
                //jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                panel.add(text);
            }
        }
        return yIndex + 8 + height;
    }

    public void setUIFields() throws java.text.ParseException {
        if (person != null) {
            salutationTextField.setText(OrderMaxUtility.getValidString(person.getsalutation()));

            deceasedDateTextField.setText(OrderMaxUtility.getValidTimestamp(person.getdeceasedDate()));

            genderTextField.setText(OrderMaxUtility.getValidString(person.getgender()));

            totalYearsWorkExperienceTextField.setText(person.gettotalYearsWorkExperience());

            firstNameTextField.setText(OrderMaxUtility.getValidString(person.getfirstName()));

            cardIdTextField.setText(OrderMaxUtility.getValidString(person.getcardId()));

            birthDateTextField.setText(OrderMaxUtility.getValidTimestamp(person.getbirthDate()));

            maritalStatusTextField.setText(OrderMaxUtility.getValidString(person.getmaritalStatus()));

            passportExpireDateTextField.setText(OrderMaxUtility.getValidTimestamp(person.getpassportExpireDate()));

            middleNameLocalTextField.setText(OrderMaxUtility.getValidString(person.getmiddleNameLocal()));

            firstNameLocalTextField.setText(OrderMaxUtility.getValidString(person.getfirstNameLocal()));

            partyIdTextField.setText(OrderMaxUtility.getValidString(person.getpartyId()));

            residenceStatusEnumIdTextField.setText(OrderMaxUtility.getValidString(person.getresidenceStatusEnumId()));

            occupationTextField.setText(OrderMaxUtility.getValidString(person.getoccupation()));

            yearsWithEmployerTextField.setText(person.getyearsWithEmployer());

            memberIdTextField.setText(OrderMaxUtility.getValidString(person.getmemberId()));

            commentsTextField.setText(OrderMaxUtility.getValidString(person.getcomments()));

            middleNameTextField.setText(OrderMaxUtility.getValidString(person.getmiddleName()));

            lastNameTextField.setText(OrderMaxUtility.getValidString(person.getlastName()));

            mothersMaidenNameTextField.setText(OrderMaxUtility.getValidString(person.getmothersMaidenName()));

            monthsWithEmployerTextField.setText(person.getmonthsWithEmployer());

            socialSecurityNumberTextField.setText(OrderMaxUtility.getValidString(person.getsocialSecurityNumber()));

            existingCustomerTextField.setText(OrderMaxUtility.getValidString(person.getexistingCustomer()));

            otherLocalTextField.setText(OrderMaxUtility.getValidString(person.getotherLocal()));

            suffixTextField.setText(OrderMaxUtility.getValidString(person.getsuffix()));

            personalTitleTextField.setText(OrderMaxUtility.getValidString(person.getpersonalTitle()));

            nicknameTextField.setText(OrderMaxUtility.getValidString(person.getnickname()));

            lastNameLocalTextField.setText(OrderMaxUtility.getValidString(person.getlastNameLocal()));

            employmentStatusEnumIdTextField.setText(OrderMaxUtility.getValidString(person.getemploymentStatusEnumId()));

            passportNumberTextField.setText(OrderMaxUtility.getValidString(person.getpassportNumber()));

            weightTextField.setText(person.getweight());

            heightTextField.setText(person.getheight());
        }
    }

    public void clearUIFields() {

        salutationTextField.setText("");

        deceasedDateTextField.setText("");

        genderTextField.setText("");

        totalYearsWorkExperienceTextField.setText("");

        firstNameTextField.setText("");

        cardIdTextField.setText("");

        birthDateTextField.setText("");

        maritalStatusTextField.setText("");

        passportExpireDateTextField.setText("");

        middleNameLocalTextField.setText("");

        firstNameLocalTextField.setText("");

        partyIdTextField.setText("");

        residenceStatusEnumIdTextField.setText("");

        occupationTextField.setText("");

        yearsWithEmployerTextField.setText("");

        memberIdTextField.setText("");

        commentsTextField.setText("");

        middleNameTextField.setText("");

        lastNameTextField.setText("");

        mothersMaidenNameTextField.setText("");

        monthsWithEmployerTextField.setText("");

        socialSecurityNumberTextField.setText("");

        existingCustomerTextField.setText("");

        otherLocalTextField.setText("");

        suffixTextField.setText("");

        personalTitleTextField.setText("");

        nicknameTextField.setText("");

        lastNameLocalTextField.setText("");

        employmentStatusEnumIdTextField.setText("");

        passportNumberTextField.setText("");

        weightTextField.setText("");

        heightTextField.setText("");

    }

    public void getUIFields() throws java.text.ParseException {

        person.setsalutation(OrderMaxUtility.getValidString(salutationTextField.getText()));

        person.setdeceasedDate(OrderMaxUtility.getValidTimestamp(deceasedDateTextField.getText()));

        person.setgender(OrderMaxUtility.getValidString(genderTextField.getText()));

        person.settotalYearsWorkExperience(totalYearsWorkExperienceTextField.getText());

        person.setfirstName(OrderMaxUtility.getValidString(firstNameTextField.getText()));

        person.setcardId(OrderMaxUtility.getValidString(cardIdTextField.getText()));

        person.setbirthDate(OrderMaxUtility.getValidTimestamp(birthDateTextField.getText()));

        person.setmaritalStatus(OrderMaxUtility.getValidString(maritalStatusTextField.getText()));

        person.setpassportExpireDate(OrderMaxUtility.getValidTimestamp(passportExpireDateTextField.getText()));

        person.setmiddleNameLocal(OrderMaxUtility.getValidString(middleNameLocalTextField.getText()));

        person.setfirstNameLocal(OrderMaxUtility.getValidString(firstNameLocalTextField.getText()));

        person.setpartyId(OrderMaxUtility.getValidString(partyIdTextField.getText()));

        person.setresidenceStatusEnumId(OrderMaxUtility.getValidString(residenceStatusEnumIdTextField.getText()));

        person.setoccupation(OrderMaxUtility.getValidString(occupationTextField.getText()));

        person.setyearsWithEmployer(yearsWithEmployerTextField.getText());

        person.setmemberId(OrderMaxUtility.getValidString(memberIdTextField.getText()));

        person.setcomments(OrderMaxUtility.getValidString(commentsTextField.getText()));

        person.setmiddleName(OrderMaxUtility.getValidString(middleNameTextField.getText()));

        person.setlastName(OrderMaxUtility.getValidString(lastNameTextField.getText()));

        person.setmothersMaidenName(OrderMaxUtility.getValidString(mothersMaidenNameTextField.getText()));

        person.setmonthsWithEmployer(monthsWithEmployerTextField.getText());

        person.setsocialSecurityNumber(OrderMaxUtility.getValidString(socialSecurityNumberTextField.getText()));

        person.setexistingCustomer(OrderMaxUtility.getValidString(existingCustomerTextField.getText()));

        person.setotherLocal(OrderMaxUtility.getValidString(otherLocalTextField.getText()));

        person.setsuffix(OrderMaxUtility.getValidString(suffixTextField.getText()));

        person.setpersonalTitle(OrderMaxUtility.getValidString(personalTitleTextField.getText()));

        person.setnickname(OrderMaxUtility.getValidString(nicknameTextField.getText()));

        person.setlastNameLocal(OrderMaxUtility.getValidString(lastNameLocalTextField.getText()));

        person.setemploymentStatusEnumId(OrderMaxUtility.getValidString(employmentStatusEnumIdTextField.getText()));

        person.setpassportNumber(OrderMaxUtility.getValidString(passportNumberTextField.getText()));

        person.setweight(weightTextField.getText());

        person.setheight(heightTextField.getText());

    }

    public static void createAndShowUI(Person val) {

        try {

            PersonPanel panel = new PersonPanel(val);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        } catch (java.text.ParseException ex) {

            Debug.logError(ex, module);

        }

    }

    @Override

    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        Person newObj = null;

        if (baseVal != null) {

            newObj = new Person(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new Person();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return person;

    }

    @Override

    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof Person) {

            Person newObj = (Person) uiObject;

            person = newObj;

            try {

                person.setGenericValue();

            } catch (Exception ex) {

//Debug.logError (ex, module);
            }

        }

    }

    public JPanel getContainerPanel() {
        return this;
    }

    private class TextChangeListner implements DocumentListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {

        }

        public void changedUpdate(DocumentEvent e) {

            warn(e);

        }

        public void removeUpdate(DocumentEvent e) {

            warn(e);

        }

        public void insertUpdate(DocumentEvent e) {

            warn(e);

        }

        void warn(DocumentEvent e) {

            isModified = true;

        }

    }

}

//calling function copy and paste

/*

 try {



 Delegator delegator = XuiContainer.getSession().getDelegator();

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("Person", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),Person.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.PersonPanel( ); 

 Object uiObj = panel.createUIObject(val);

 panel.changeUIObject(uiObj);

 panel.setUIFields();

 dlg.setChildPanelInterface(panel);

 OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());

 dlg.setLocationRelativeTo(null);

 dlg.pack();

 dlg.setVisible(true);

 } catch (ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 } catch (java.text.ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 }

 */
