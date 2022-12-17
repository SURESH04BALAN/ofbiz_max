/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import mvc.controller.LoadBaseSwingWorker;
import mvc.controller.SwingWorkerProgressModel;
import mvc.controller.SwingWorkerPropertyChangeListener;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import mvc.model.table.ActionTableModel;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author BBS Auctions
 * @param <T>
 */
public class GenericTableModelPanel<E, T extends ActionTableModel<E>> extends JPanel {

    static final String module = GenericTableModelPanel.class.getName();
    /**
     *
     */
    private static final long serialVersionUID = -4471606875093169644L;
    private SwingWorkerProgressModel swingWorkerProgressModel = new SwingWorkerProgressModel();
    private JProgressBar progressBar = new JProgressBar(swingWorkerProgressModel);

    JPanel panelProgress = new JPanel();
    private SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility = new SwingWorkerBasedComponentVisibility(panelProgress);
    private LoadSpeedSimulationPanel loadSpeedSimulationPanel = new LoadSpeedSimulationPanel();

    private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();

//    private MVCBaseTableModel<E> jTableModel = null;//new PersonTableModel();// new Model<E>();
    public ListAdapterListModel<E> listModel = null;//new ListAdapterListModel<E>();
    public T tableModel = null;
    public ListModelSelection<E> listModelSelection = new ListModelSelection<E>();
    public ListSelectionModel selectionModel = new DefaultListSelectionModel();

    public JTable jTable = null; //new JTable(tableModel);
    public JScrollPane scrollPane = null;
//    LoadBaseSwingWorker<E> loadWorker = null;

    public void setListModel(ListAdapterListModel<E> listModel) {
        tableModel.setListModel(listModel);
        
//        jTable.setModel(tableModel);
        listModelSelection.setListModels(listModel, selectionModel);
        Debug.logInfo(" new listModel.size: " + listModel.getSize(), module);
        //jTable.getModel().
        this.listModel = listModel;
        jTable.setModel(this.tableModel);        
        tableModel.fireTableDataChanged();
    }

    JButton btnCancel = null;

    public GenericTableModelPanel(T/*MVCBaseTableModel<E>*/ tableModel) {
        this.tableModel = tableModel;
        jTable = new JTable();
        jTable.setModel(this.tableModel);
        setLayout(new BorderLayout());
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.setSelectionModel(selectionModel);
        jTable.setFillsViewportHeight(true);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(10, 11, 580, 130);
        add(BorderLayout.CENTER, scrollPane);
//        panelProgress = new JPanel();
        panelProgress.setLayout(new BorderLayout());
        panelProgress.add(progressBar, BorderLayout.CENTER);
        btnCancel = new JButton("Cancel");

        panelProgress.add(btnCancel, BorderLayout.EAST);

//        scrollPane.setViewportView(jTable);       
        progressBar.setStringPainted(true);
        this.add(panelProgress, BorderLayout.SOUTH);

        addSwingWorkerPropertyChangeListener(swingWorkerProgressModel);
        addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
        setLoadSpeedModel(loadSpeedSimulationPanel.getPersonsLoadSpeedModel());
    }

    public void addSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
        swingWorkerPropertyChangeListeners
                .add(swingWorkerPropertyChangeListener);
    }

    public void removeSwingWorkerPropertyChangeListener(
            SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
        swingWorkerPropertyChangeListeners
                .remove(swingWorkerPropertyChangeListener);
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;
    }

    public void actionPerformed(ActionEvent e) {
        /*        LoadPersonsWorker loadPersonsWorker = new LoadPersonsWorker(listModel);
         loadPersonsWorker.setLoadSpeedModel(loadPersonsSpeedModel);
         for (SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener : swingWorkerPropertyChangeListeners) {
         swingWorkerPropertyChangeListener.attachPropertyChangeListener(loadPersonsWorker);
         }
         loadPersonsWorker.execute();
         */
    }

    public void actionPerformed(final LoadBaseSwingWorker<E> loadWorker) {
//        LoadPersonsWorker loadPersonsWorker = new LoadPersonsWorker(listModel);
//        this.loadWorker = loadWorker;
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (loadWorker != null) {
                    loadWorker.cancel(true);
                }

            }
        });
        loadWorker.setLoadSpeedModel(loadPersonsSpeedModel);
        for (SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener : swingWorkerPropertyChangeListeners) {
            swingWorkerPropertyChangeListener.attachPropertyChangeListener(loadWorker);
        }
        Debug.logError("actionPerformed: loadWorker.execute(): start", "val");
        loadWorker.execute();
        Debug.logError("actionPerformed: loadWorker.execute(): end", "val");
    }

    public void scrollToVisible(final JTable table, final int rowIndex, final int vColIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int lastrow = tableModel.getRowCount();
                if (rowIndex == lastrow - 1) {
                    table.scrollRectToVisible(table.getCellRect(rowIndex - 1, vColIndex, false));
                } else {
                    table.scrollRectToVisible(table.getCellRect(rowIndex + 1, vColIndex, false));
                }

            }
        });
    }

    public class InteractiveTableModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                Debug.logInfo("row: " + row + " column: " + column, module);
                jTable.setColumnSelectionInterval(column + 1, column + 1);
                jTable.setRowSelectionInterval(row, row);
            }
        }
    }

}
