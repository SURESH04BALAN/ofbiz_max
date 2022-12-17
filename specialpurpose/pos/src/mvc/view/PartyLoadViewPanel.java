/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import java.awt.BorderLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.list.PersonListCellRenderer;
import mvc.model.table.PersonTableModel;

/**
 *
 * @author siranjeev
 */
public class PartyLoadViewPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4471606875093169644L;

    private PersonTableModel personTableModel = new PersonTableModel();
    private ListAdapterListModel<Person> personListModel = new ListAdapterListModel<Person>();
    private ListModelSelection<Person> listModelSelection = new ListModelSelection<Person>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private JTable personTable = new JTable(personTableModel);

    private JList<Person> personList = new JList<Person>(personListModel);
    private ListComboBoxModel<Person> personComboBoxModel = new ListComboBoxModel<Person>();
    private JComboBox<Person> personsComboBox = new JComboBox<Person>(personComboBoxModel);

    public void setPersonList(ListAdapterListModel<Person> personListModel) {
        personList.setModel(personListModel);
        personTableModel.setListModel(personListModel);
        listModelSelection.setListModels(personListModel, selectionModel);
    }

    public PartyLoadViewPanel() {
        setLayout(new BorderLayout());
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personList.setSelectionModel(selectionModel);
        personTable.setSelectionModel(selectionModel);
        personComboBoxModel.setListSelectionModel(selectionModel);

        ListCellRenderer<Person> personRenderer = new PersonListCellRenderer();
        personList.setCellRenderer(personRenderer);
        personList.setEnabled(true);
        personsComboBox.setRenderer(personRenderer);
        personTable.setSelectionModel(personList.getSelectionModel());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 580, 130);
        add(scrollPane, BorderLayout.CENTER);

        scrollPane.setViewportView(personTable);

        personsComboBox.setBounds(10, 153, 580, 30);
        add(personsComboBox, BorderLayout.NORTH);
    }
}
