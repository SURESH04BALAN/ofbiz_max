/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.celleditors;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import org.ofbiz.guiapp.xui.XuiSession;
//import org.ofbiz.ordermax.screens.OrderViewDialog;

/**
 *
 * @author siranjeev
 */
public class GenericValueIdActionTableCellEditor extends ActionTableCellEditor {

    java.awt.Frame parent = null;
    boolean modal = false;
    XuiSession session = null;
    private final Border red = new LineBorder(Color.red);

    public GenericValueIdActionTableCellEditor(TableCellEditor editor, java.awt.Frame parent, boolean modal, XuiSession sessionVal, String idName) {
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

                String orderId = (String) table.getModel().getValueAt(row, column);
                if (orderId != null && orderId.isEmpty() == false) {
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                JFrame frame = new JFrame("Order Entery Form");
//                Order dlg = new OrderDetailTableForm(parent, session, orderId);
//                OrderDetailTableForm.loadOrderFromPersistance(orderId,  session.getDelegator());
//                dlg.setVisible(true);
//                frame.setContentPane(dlg);
                    //Display the window.
                    //               frame.pack();
                    //               frame.setVisible(true);                
//                    OrderViewDialog dlg = new OrderViewDialog(parent, true, session);
//                    dlg.loadOrder(orderId);
//                    dlg.setLocationRelativeTo(null);
//                    dlg.setVisible(true);
                }
            }
        });

    }
}
