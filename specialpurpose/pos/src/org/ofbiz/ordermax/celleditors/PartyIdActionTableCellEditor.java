/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.celleditors;


import java.awt.Color;
import java.text.ParseException;
import java.util.logging.Level;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.generic.GenericValueObjectInterface;
import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.ordermax.entity.PartyGroup;
import org.ofbiz.ordermax.utility.PartyGroupPanel;
import org.ofbiz.ordermax.utility.PersonDialogForm;

/**
 *
 * @author siranjeev
 */
public class PartyIdActionTableCellEditor extends ActionTableCellEditor {

    java.awt.Frame parent = null;
    boolean modal = false;
    XuiSession session = null;
    private final Border red = new LineBorder(Color.red);

    public PartyIdActionTableCellEditor(TableCellEditor editor, java.awt.Frame parent, boolean modal, XuiSession sessionVal) {
        super(editor);
        this.parent = parent;
        this.modal = modal;
        this.session = sessionVal;

    }

    protected void editCell(final JTable table, Object partialValue, final int row, final int column) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            private ClassLoader getClassLoader() {
                ClassLoader cl = session.getClassLoader();
                if (cl == null) {
                    try {
                        cl = Thread.currentThread().getContextClassLoader();
                    } catch (Throwable t) {
                    }
                    if (cl == null) {
//                Debug.log("No context classloader available; using class classloader", module);
                        try {
                            cl = this.getClass().getClassLoader();
                        } catch (Throwable t) {
                            //                  Debug.logError(t, module);
                        }
                    }
                }
                return cl;
            }

            public void run() {
                getClassLoader();
                final ClassLoader cl = this.getClassLoader();
                Thread.currentThread().setContextClassLoader(cl);
                Debug.logInfo("party run", "party id");
                String partyId = (String) table.getModel().getValueAt(row, column);
                Debug.logInfo("party Id: " + partyId, "party id");
                if (partyId != null && partyId.isEmpty() == false) {
                    Delegator delegator = session.getDelegator();
                    try {



//                            editor.setClientId(Client.createTestClient(session));
//                        GenericValueTableDataModel model = (GenericValueTableDataModel) table.getModel();
//                        if (model != null) {

                        GenericValue party = PosProductHelper.getParty(partyId, delegator);
                        String partyTypeId = party.getString("partyTypeId");
                        Debug.logInfo(partyTypeId, partyId);
                        if ("PERSON".equals(partyTypeId)) {


                            /*                                javax.swing.JFrame frame = new javax.swing.JFrame("Client Editor");
                             ClientEditor editor = new ClientEditor(session);
                             Debug.logInfo("Person", partyId);
                             GenericValue person = PosProductHelper.getPerson(partyId, delegator);
                             editor.setPerson(person);
                             frame.getContentPane().add(editor);
                             frame.pack();
                             frame.setLocationRelativeTo(null);
                             frame.setVisible(true);
                             */
                            try {
//            Delegator delegator = XuiContainer.getSession().getDelegator();
//            List<GenericValue> genValList = PosProductHelper.getGenericValueLists("Person", delegator);
                                //        GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(), Person.ColumnNameId);
                                //        dlg.setupOrderTableList(genValList);
//            GenericValue val = genValList.get(0);
                                GenericValue person = PosProductHelper.getPerson(partyId, delegator);
                                //                              editor.setPerson(person);

                                GenericValuePanelInterfaceOrderMax panel = new PersonDialogForm(session);
                                GenericValueObjectInterface uiObj = panel.createUIObject(person);
                                panel.changeUIObject(uiObj);
                                panel.setUIFields();
                                javax.swing.JFrame frame = new javax.swing.JFrame("Client Editor");
                                frame.getContentPane().add(panel.getContainerPanel());
                                frame.pack();
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
                                //         dlg
                            } catch (ParseException ex) {
                                java.util.logging.Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
                            } 


                        } else if ("PARTY_GROUP".equals(partyTypeId)) {
                            Debug.logInfo("Party Group", partyId);
                            GenericValue partyGroupGen = PosProductHelper.getPartyGroup(partyId, delegator);
                            PartyGroup partyGroup = new PartyGroup(partyGroupGen);
                            partyGroup.setGenericValue();
                            PartyGroupPanel.createAndShowUI(partyGroup);

//                                editor.setPartyGroup(partyGroup);                                                                
                        }
                        //                       }
                    } catch (Exception ex) {
                        Debug.logError(ex, "Error");
//                        Logger.getLogger(PartyIdActionTableCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }



            }
        });

    }
}
