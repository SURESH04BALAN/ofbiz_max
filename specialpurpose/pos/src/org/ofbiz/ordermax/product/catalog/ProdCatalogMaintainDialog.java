/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListSelectionModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.SwingWorkerProgressModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import mvc.view.LoadSpeedSimulationPanel;
import mvc.view.SwingWorkerBasedComponentVisibility;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.ProdCatalog;

/**
 *
 * @author siranjeev
 */
public class ProdCatalogMaintainDialog extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;

    private ListAdapterListModel<ProdCatalog> prodCatalogListModel = new ListAdapterListModel<ProdCatalog>();
    private SwingWorkerProgressModel swingWorkerProgressModel = new SwingWorkerProgressModel();
    private JProgressBar progressBar = new JProgressBar(swingWorkerProgressModel);
    private SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility = new SwingWorkerBasedComponentVisibility(progressBar);
    private LoadSpeedSimulationPanel loadPersonSpeedSimulationPanel = new LoadSpeedSimulationPanel();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();

    private Component currentContent;
    XuiSession session = null;
    ProdCatalogMaintainPanel catalogMaintainPanel = null;
    private ListModelSelection<ProdCatalog> listModelSelection = new ListModelSelection<ProdCatalog>();

    /**
     * Creates new form ProdCatalogMaintainDialog
     */
    boolean isNew = true;
    boolean isModified = false;
    ProdCatalog prodCatalog = new ProdCatalog();

    public ProdCatalogMaintainDialog(java.awt.Frame parent, boolean modal, XuiSession sess) {

        super(parent, modal);
        initComponents();
        this.session = sess;
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        progressBar.setStringPainted(true);
        catalogMaintainPanel = new ProdCatalogMaintainPanel();
        setContent(catalogMaintainPanel);
        catalogMaintainPanel.add(loadPersonSpeedSimulationPanel, BorderLayout.NORTH);
        catalogMaintainPanel.add(progressBar, BorderLayout.SOUTH);
//getRootPane().setDefaultButton(okButton);

//        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        catalogMaintainPanel.catalogListSelectionModel.dataListModel.setSelectionModel(selectionModel);
/*        selectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                JList<ProdCatalog> list = (JList<ProdCatalog>) catalogMaintainPanel.prodCatalogList;
                prodCatalog = listModelSelection.getSelection();
                catalogMaintainPanel.setDialogFields(prodCatalog);
                isNew = false;
            }
        });
*/
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });

        catalogMaintainPanel.cancelButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });

        catalogMaintainPanel.deleteButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });

        catalogMaintainPanel.newButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                isNew = true;
                isModified = false;
                prodCatalog = new ProdCatalog();
                catalogMaintainPanel.clearDialogFields();

            }
        });

        catalogMaintainPanel.okButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_OK);
            }
        });

        catalogMaintainPanel.saveButton.addActionListener(
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        catalogMaintainPanel.getDialogFields(prodCatalog);
                        if (isNew) {
                            prodCatalogListModel.add(prodCatalog);
                        }
                        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(prodCatalogListModel, session);
                        saveAction.actionPerformed(e);
                    }
                });

    }

    public void setProdCatalogList(ListAdapterListModel<ProdCatalog> personListModel) {
//        catalogMaintainPanel.setProdCatalogList(personListModel);

        prodCatalogListModel = personListModel;
//        catalogMaintainPanel.prodCatalogList.setModel(personListModel);
        listModelSelection.setListModels(personListModel, selectionModel);

    }

    int returnStatus = RET_CANCEL;

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(700, 700));
        setMinimumSize(new java.awt.Dimension(520, 550));
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public void setContent(Component component) {
        Container contentPane = getContentPane();
        if (currentContent != null) {
            contentPane.remove(currentContent);
        }
        contentPane.add(component, BorderLayout.CENTER);
        currentContent = component;
        contentPane.doLayout();
        repaint();
    }

}
