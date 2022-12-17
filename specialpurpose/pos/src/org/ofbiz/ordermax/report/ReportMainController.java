/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report;

import org.ofbiz.ordermax.report.reports.ReportBaseFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import org.ofbiz.ordermax.invoice.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.TwoButtonThreeContainerPanel;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author siranjeev
 */
public class ReportMainController extends BaseMainScreen {

    public static final String module = ReportMainController.class.getName();
//    public ReportMainPanel panel = null;
    public final String caption = "Billing Account List";
    private ReportInterface reportInterface = null;

    @Override
    public String getCaption() {
        return "Report [" + reportInterface.identifier() + "]";
    }

    PosTransaction m_trans = null;

    public ReportMainController(XuiSession sess, final PosTransaction m_trans, ReportInterface reportInterface) {
        super(sess);
        this.m_trans = m_trans;
        this.reportInterface = reportInterface;
    }

    public ReportMainController(XuiSession sess, final PosTransaction m_trans) {
        super(sess);
        this.m_trans = m_trans;
    }

    public ReportMainController(XuiSession sess, ReportInterface reportInterface) {
        super(sess);
        this.reportInterface = reportInterface;
    }
    ReportInterface currRepInterface = null;

    public void RunReportSelection(javax.swing.JDesktopPane dPane) {
        if (dPane != null) {
            RunReportSelectionInternalFrame(dPane);
        } else {
            RunReportSelectionDialog(dPane);
        }
    }

    public void RunReportSelectionDialog(javax.swing.JDesktopPane dPane) {

        final TwoButtonThreeContainerPanel containerPanel = new TwoButtonThreeContainerPanel();
        JOptionPane pane = new JOptionPane(containerPanel, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[0], null);

        Rectangle rec = pane.getBounds();
        rec.width = 900;
        rec.height = 500;
        pane.setBounds(rec);

        final JDialog internalFrame = pane.createDialog(ControllerOptions.getDesktopPane(), "Report Selection");
        internalFrame.setBounds(rec);
//internalFrame.setLocationRelativeTo(null); 

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - internalFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - internalFrame.getHeight()) / 2);
        internalFrame.setLocation(x, y - 100);

        containerPanel.getPanelDetail().setLayout(new BorderLayout());
//        containerPanel.getPanelDetail().add(reportInterface.getSelectionPanel(), BorderLayout.CENTER);
        final ReportSelectionTree reportSelectionTree = new ReportSelectionTree();
        reportSelectionTree.tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) reportSelectionTree.tree.getLastSelectedPathComponent();
                reportSelectionTree.selectedLabel.setText(selectedNode.getUserObject().toString());
                try {
                    currRepInterface = ReportBaseFactory.getReport(selectedNode.getUserObject().toString());
                    reportSelectionTree.selectedLabel.setText(/*currRepInterface.identifier()*/selectedNode.getUserObject().toString());
                    containerPanel.getPanelDetail().removeAll();
                    if (currRepInterface.getSelectionPanel() != null) {
                        containerPanel.getPanelDetail().add(currRepInterface.getSelectionPanel(), BorderLayout.CENTER);
                    }
                    containerPanel.getPanelDetail().repaint();
                } catch (Exception ex) {
                    Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
                    currRepInterface = null;
                }
            }
        });

        containerPanel.getPanelSelecton().setLayout(new BorderLayout());
        containerPanel.getPanelSelecton().add(reportSelectionTree, BorderLayout.CENTER);

        containerPanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                System.out.println("getReturnStatus : " + containerPanel.getReturnStatus());
                if (containerPanel.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                    reportInterface = currRepInterface;
                    if (reportInterface != null) {
                        loadSinglePanelInternalFrame(ReportMainController.module, ControllerOptions.getDesktopPane());
//                    String cancelName = "cancel";
//                    InputMap inputMap = reportInterface.getJRViewer().getBtnReload().getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//                    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
//                    ActionMap actionMap = reportInterface.getJRViewer().getRootPane().getActionMap();
//                    ;
                        if (reportInterface.getJRViewer() != null) {
                            reportInterface.getJRViewer().getBtnReload().addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("reportInterface.getJRViewer() : " + "actionPerformed");
                                    ReportMainController.this.singlePanelContainerInternalFrame.setVisible(false);
                                    internalFrame.setVisible(true);
                                }
                            });
                        }
                    } else {
                        System.out.println("Empty report : " + "actionPerformed");
                    }
                }

                //              }
                internalFrame.setVisible(false);
            }
        });
        internalFrame.setVisible(true);

    }

    public void RunReportSelectionInternalFrame(javax.swing.JDesktopPane dPane) {

        final TwoButtonThreeContainerPanel containerPanel = new TwoButtonThreeContainerPanel();
        JOptionPane pane = new JOptionPane(containerPanel, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[0], null);

        Rectangle rec = pane.getBounds();
        rec.width = 900;
        rec.height = 500;
        pane.setBounds(rec);

        final JInternalFrame internalFrame = pane.createInternalFrame(ControllerOptions.getDesktopPane(), "Report Selection");
        internalFrame.setBounds(rec);
//internalFrame.setLocationRelativeTo(null); 

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - internalFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - internalFrame.getHeight()) / 2);
        internalFrame.setLocation(x, y - 100);

        containerPanel.getPanelDetail().setLayout(new BorderLayout());
//        containerPanel.getPanelDetail().add(reportInterface.getSelectionPanel(), BorderLayout.CENTER);
        final ReportSelectionTree reportSelectionTree = new ReportSelectionTree();
        reportSelectionTree.tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) reportSelectionTree.tree.getLastSelectedPathComponent();
                reportSelectionTree.selectedLabel.setText(selectedNode.getUserObject().toString());
                try {
                    currRepInterface = ReportBaseFactory.getReport(selectedNode.getUserObject().toString());
                    reportSelectionTree.selectedLabel.setText(/*currRepInterface.identifier()*/selectedNode.getUserObject().toString());
                    containerPanel.getPanelDetail().removeAll();
                    if (currRepInterface.getSelectionPanel() != null) {
                        containerPanel.getPanelDetail().add(currRepInterface.getSelectionPanel(), BorderLayout.CENTER);
                    }
                    containerPanel.getPanelDetail().repaint();
                } catch (Exception ex) {
                    Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
                    currRepInterface = null;
                }
            }
        });

        containerPanel.getPanelSelecton().setLayout(new BorderLayout());
        containerPanel.getPanelSelecton().add(reportSelectionTree, BorderLayout.CENTER);

        containerPanel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                System.out.println("getReturnStatus : " + containerPanel.getReturnStatus());
                if (containerPanel.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                    reportInterface = currRepInterface;
                    if (reportInterface != null) {
                        loadSinglePanelInternalFrame(ReportMainController.module, ControllerOptions.getDesktopPane());
//                    String cancelName = "cancel";
//                    InputMap inputMap = reportInterface.getJRViewer().getBtnReload().getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//                    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
//                    ActionMap actionMap = reportInterface.getJRViewer().getRootPane().getActionMap();
//                    ;
                        if (reportInterface.getJRViewer() != null) {
                            reportInterface.getJRViewer().getBtnReload().addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent e) {
                                    System.out.println("reportInterface.getJRViewer() : " + "actionPerformed");
                                    ReportMainController.this.singlePanelContainerInternalFrame.setVisible(false);
                                    internalFrame.setVisible(true);
                                }
                            });
                        }
                    } else {
                        System.out.println("Empty report : " + "actionPerformed");
                    }
                }

                //              }
                internalFrame.setVisible(false);
            }
        });
        internalFrame.setVisible(true);

    }

    FindInvoiceListButtonPanel buttonPanel = null;
    final private ListAdapterListModel<BillingAccount> invoiceCompositeListModel = new ListAdapterListModel<BillingAccount>();

    public void RunReport(javax.swing.JDesktopPane dPane) {
        //report.loadSinglePanelNonSizeableFrameDialogScreen(desktopPane, frame);

        if (reportInterface.getShowSelectionPanel() == true && reportInterface.getSelectionPanel() != null) {
            final TwoButtonThreeContainerPanel chooser = new TwoButtonThreeContainerPanel();
            JOptionPane pane = new JOptionPane(chooser, JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[0], null);
            Rectangle rec = pane.getBounds();
            rec.width = 900;
            rec.height = 500;
            pane.setBounds(rec);
            final JInternalFrame dialog = pane.createInternalFrame(ControllerOptions.getDesktopPane(), "Dialog Frame");
            dialog.setBounds(rec);

            chooser.getPanelDetail().setLayout(new BorderLayout());
            chooser.getPanelDetail().add(reportInterface.getSelectionPanel(), BorderLayout.CENTER);
            final ReportSelectionTree reportSelectionTree = new ReportSelectionTree();
            reportSelectionTree.tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) reportSelectionTree.tree.getLastSelectedPathComponent();
                    reportSelectionTree.selectedLabel.setText(selectedNode.getUserObject().toString());
                    try {
                        ReportInterface repInterface = ReportBaseFactory.getReport(selectedNode.getUserObject().toString());
                        reportSelectionTree.selectedLabel.setText(repInterface.identifier());
                        chooser.getPanelDetail().removeAll();
                        if (repInterface.getSelectionPanel() != null) {
                            chooser.getPanelDetail().add(repInterface.getSelectionPanel(), BorderLayout.CENTER);
                        }
                        chooser.getPanelDetail().repaint();
                    } catch (Exception ex) {
                        Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            chooser.getPanelSelecton().setLayout(new BorderLayout());
            chooser.getPanelSelecton().add(reportSelectionTree, BorderLayout.CENTER);

            chooser.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    System.out.println("getReturnStatus : " + chooser.getReturnStatus());
                    if (chooser.getReturnStatus().equals(ContainerPanelInterface.ReturnStausType.RET_OK)) {
                        loadSinglePanelInternalFrame(ReportMainController.module, ControllerOptions.getDesktopPane());
                        String cancelName = "cancel";
                        InputMap inputMap = reportInterface.getJRViewer().getBtnReload().getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
                        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
                        ActionMap actionMap = reportInterface.getJRViewer().getRootPane().getActionMap();
                        ;
                        reportInterface.getJRViewer().getBtnReload().addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                                System.out.println("reportInterface.getJRViewer() : " + "actionPerformed");
                                ReportMainController.this.singlePanelContainerInternalFrame.setVisible(false);
                                dialog.setVisible(true);
                            }
                        });

                    }

                    //              }
                    dialog.setVisible(false);
                }
            });
            dialog.setVisible(true);
        } else {
            loadSinglePanelInternalFrame(ReportMainController.module, ControllerOptions.getDesktopPane());
        }
    }
    JPanel reportPanel;
    //JPanel reportCardPanel;

    @Override
    public void loadScreen(ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        //reportCardPanel = new JPanel(new CardLayout());
//        reportCardPanel.setSize(900, 650);
//        final InventoryReportPanel inventoryReportPanel = new InventoryReportPanel();
//       final EndOfTheDayReportPanel endOfTheDayReportPanel = new EndOfTheDayReportPanel();
//        final ProductRequirmentReportPanel productRequirmentReportPanel = new ProductRequirmentReportPanel();
//        final GenericReportPanel genericReportPanel = new GenericReportPanel();
//        final JasperReportGenericPanel jasperReportPanel = new JasperReportGenericPanel();

        //inventoryReportPanel.setFacilityId((String) session.getAttribute("facilityId"));
        //reportCardPanel.add(inventoryReportPanel, inventoryReportPanel.getName());
//        reportCardPanel.add(endOfTheDayReportPanel, endOfTheDayReportPanel.getName());
        //reportCardPanel.add(productRequirmentReportPanel, productRequirmentReportPanel.getName());                
        //reportCardPanel.add(genericReportPanel, genericReportPanel.getName());                
//        reportCardPanel.add(jasperReportPanel, jasperReportPanel.getName());
        reportPanel = reportInterface.runReport();

//        cardChanged(reportCardPanel, endOfTheDayReportPanel.getName());
//                cardChanged(reportCardPanel, jasperReportPanel.getName());                
/*
         ReportSelectionPanel buttonSelPanel = new ReportSelectionPanel();
         //      ProductButtonPanel buttonMainPanel = new ProductButtonPanel();
         buttonSelPanel.getBtnDailySalesReport().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         endOfTheDayReportPanel.runReport(m_trans);
         cardChanged(reportCardPanel, endOfTheDayReportPanel.getName());

         } catch (Exception ex) {
         Logger.getLogger(ReportMainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         });

         buttonSelPanel.getBtnStockValueReport().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         inventoryReportPanel.runReport(m_trans);
         cardChanged(reportCardPanel, inventoryReportPanel.getName());

         } catch (Exception ex) {
         Logger.getLogger(ReportMainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         });

         buttonSelPanel.getBtnReorderReport().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         productRequirmentReportPanel.runReport(m_trans);
         cardChanged(reportCardPanel, productRequirmentReportPanel.getName());

         } catch (Exception ex) {
         Logger.getLogger(ReportMainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         });

         buttonSelPanel.getBtnDetailReport().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         genericReportPanel.runReport(m_trans);
         cardChanged(reportCardPanel, genericReportPanel.getName());
         } catch (Exception ex) {
         Logger.getLogger(ReportMainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         });

         buttonSelPanel.getJasperReport().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         //                            jasperReportPanel.runReport( m_trans);                            
         //cardChanged(reportCardPanel, jasperReportPanel.getName());
         cardChanged(reportCardPanel, jasperReportPanel.getName());
         } catch (Exception ex) {
         Logger.getLogger(ReportMainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         });
         */
        /*                dialog.getBtnPrint().addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         try {
         productRequirmentReportPanel.printReport( m_trans);                            
         cardChanged(reportCardPanel, productRequirmentReportPanel.getName());

         } catch (Exception ex) {
         Logger.getLogger(ReportMainScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         });
         */
//        panel = new ReportMainPanel();
//        buttonPanel = new FindInvoiceListButtonPanel();
        OrderMaxUtility.addAPanelGrid(reportPanel, f.getPanelDetail());
    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 30;
        int x = 100;
        frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        frame.setLocation(x, y);
    }

    @Override
    public String getClassName() {
        return module;
    }

    public static void addAPanelToPanel(JPanel childPanel, JPanel Container) {

//        JScrollPane scroller = new JScrollPane(childPanel);
//    this.getContentPane().add(scroller, BorderLayout.CENTER);  
        javax.swing.GroupLayout panelTreeContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(panelTreeContainerLayout);
        panelTreeContainerLayout.setHorizontalGroup(
                panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTreeContainerLayout.createSequentialGroup()
                        .addComponent(childPanel) //.addGap(0, 0, 0)
                ));
        panelTreeContainerLayout.setVerticalGroup(
                panelTreeContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTreeContainerLayout.createSequentialGroup()
                        //.addContainerGap(0, 0)
                        .addComponent(childPanel) //.addGap(0, 0, 0)
                ));

//        panelTreeContainer..addComponent(treePanel);
        //    childPanel.setVisible(true);
        //    Container.setVisible(true);;
    }

}
