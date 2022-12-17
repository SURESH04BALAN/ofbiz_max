/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report.reports;

import com.openbravo.basic.BasicException;

import com.openbravo.pos.reports.common.TwoPanelNonSizableReportContainerDlg;
import com.openbravo.pos.reports.common.TwoPanelReportInternalFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.report.ReportInterface;
import org.ofbiz.ordermax.report.ReportSelectionTree;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.pos.PosTransaction;

/**
 *
 * @author siranjeev
 */
public class NewReportMainController extends BaseMainScreen {

    public static final String module = NewReportMainController.class.getName();
//    public ReportMainPanel panel = null;
    public final String caption = "Billing Account List";
    private ReportInterface reportInterface = null;
    ControllerOptions options = new ControllerOptions();

    @Override
    public String getCaption() {
        if (reportInterface != null) {
            return "Report [" + reportInterface.identifier() + "]";
        } else {
            return "Report [" + "]";
        }
    }

    static public NewReportMainController runControllerInternalFrame(ControllerOptions options) {

        NewReportMainController controller = new NewReportMainController(options);
        if (ControllerOptions.getDesktopPane() == null) {
            controller.loadNonSizeableReportDialogScreen(NewReportMainController.module, null);
        } else {
            controller.loadReportInternalFrameScreen(NewReportMainController.module, ControllerOptions.getDesktopPane(), true);
        }
        return controller;
    }
    PosTransaction m_trans = null;

    public NewReportMainController(XuiSession sess, final PosTransaction m_trans, ReportInterface reportInterface) {
        super(sess);
        this.m_trans = m_trans;
        this.reportInterface = reportInterface;

    }

    public NewReportMainController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
        if (options.contains("PosTransaction")) {
            this.reportInterface = (ReportInterface) options.get("PosTransaction");
        }

        if (options.contains("ReportInterface")) {
            this.m_trans = (PosTransaction) options.get("ReportInterface");
        }

    }

    public NewReportMainController(XuiSession sess, ReportInterface reportInterface) {
        super(sess);
        this.reportInterface = reportInterface;
    }
    ReportInterface currRepInterface = null;

    JPanel reportPanel;
    //JPanel reportCardPanel;
    Map<String, String> btnReportMap = new HashMap<String, String>();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        // final ClassLoader cl = getClassLoader();
        // Thread.currentThread().setContextClassLoader(cl);

        if (f instanceof TwoPanelNonSizableReportContainerDlg) {
            final TwoPanelNonSizableReportContainerDlg dlg = (TwoPanelNonSizableReportContainerDlg) f;
            //final ReportSelectionTree reportSelectionTree = new ReportSelectionTree();
            dlg.getPosReportPanel().m_jPanelLeft.getViewport().setView(null);
            List<String> list = ReportBaseFactory.getReportList(ReportBaseFactory.REP_GROUP_POS, null);//.reportMap.get(ReportBaseFactory.REP_GROUP_POS);
            com.openbravo.pos.reports.common.JFlowPanel jPeople = new com.openbravo.pos.reports.common.JFlowPanel();
            for (String instance : list) {
                try {
                    final ReportInterface repInterface = ReportBaseFactory.getReport(instance);
                    btnReportMap.put(repInterface.identifier(), instance);
                    // repInterface.identifier();
                    JButton btn = new JButton(repInterface.identifier());
                    //btn.applyComponentOrientation(getComponentOrientation());
                    btn.setFocusPainted(false);
                    btn.setFocusable(false);
                    btn.setRequestFocusEnabled(false);
                    btn.setHorizontalAlignment(SwingConstants.LEADING);
                    btn.setMaximumSize(new Dimension(200, 40));
                    btn.setPreferredSize(new Dimension(200, 40));
                    btn.setMinimumSize(new Dimension(200, 40));
                    btn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent actionEvent) {
                            System.out.println(btnReportMap.get(actionEvent.getActionCommand().toString()));
                           
                            //dlg.getPosReportPanel().m_jTitle.setText(actionEvent.getActionCommand().toString());
                            currRepInterface = null;
                            try {
                                currRepInterface = ReportBaseFactory.getReport(btnReportMap.get(actionEvent.getActionCommand().toString()));
                                f.getPanelDetail().removeAll();
                                if (currRepInterface instanceof NewEntityJasperReport) {
                                    final NewEntityJasperReport panelReport = (NewEntityJasperReport) currRepInterface;
                                    panelReport.loadParameterSelections();
                            //com.openbravo.pos.reports.PanelReportBean panelReport = new com.openbravo.pos.reports.PanelReportBean();
                                    //com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(panelReport.getQbffilter(), options, "Party Selection");
                                    //com.openbravo.pos.reports.params.JParamHelper.addReportProductIdSelection(panelReport.getQbffilter(), options, "Product Selection");

                                   /* panelReport.jToggleReportTree.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            selectionPanel.setMenuVisible(!selectionPanel.m_jPanelLeft.isVisible());
                                            panelReport.assignMenuButtonIcon(selectionPanel.m_jPanelLeft.isVisible());
                                        }
                                    });*/

                            //   System.out.println("getCompiledReportPathAndFile() : " + getCompiledReportPathAndFile());
                                    //   System.out.println("getReturnStatus : " + chooser.getReturnStatus());
                                    //   panelReport.setReport("C:\\ofbiz\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\com\\openbravo\\pos\\reports\\closedpos.jasper");
                                    panelReport.init(null);
                                    try {
                                        panelReport.activate();
                                    } catch (BasicException ex) {
                                        Logger.getLogger(NewReportMainController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    f.getPanelDetail().removeAll();
                                    f.getPanelDetail().setLayout(new BorderLayout());
                                    f.getPanelDetail().add(panelReport, BorderLayout.CENTER);
                                    f.getPanelDetail().repaint();
                                } /*else {
                                 final com.openbravo.pos.reports.PanelReportBean panelReport = NewReportMainController.getReportPanel(module);
                                 panelReport.jToggleReportTree.addActionListener(new ActionListener() {
                                 @Override
                                 public void actionPerformed(ActionEvent e) {
                                 selectionPanel.setMenuVisible(!selectionPanel.m_jPanelLeft.isVisible());
                                 panelReport.assignMenuButtonIcon(selectionPanel.m_jPanelLeft.isVisible());
                                 }
                                 });
                                 f.getPanelDetail().removeAll();
                                 f.getPanelDetail().setLayout(new BorderLayout());
                                 f.getPanelDetail().add(panelReport, BorderLayout.CENTER);
                                 f.getPanelDetail().repaint();
                                 }*/
                        //if (currRepInterface.getSelectionPanel() != null) {
                                //    containerPanel.getPanelDetail().add(currRepInterface.getSelectionPanel(), BorderLayout.CENTER);
                                //   }*/                                        
                                //containerPanel.getPanelDetail().repaint();

//        com.openbravo.pos.reports.PanelReportBean panelBean = getReportPanel("pos");
                            } catch (Exception ex) {
                                Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
                                currRepInterface = null;
                            }

                        }
                    });
                    jPeople.add(btn);

                } catch (Exception ex) {
                    Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            dlg.getPosReportPanel().m_jPanelLeft.getViewport().setView(jPeople);

        } else {
            //final ReportSelectionContainer selectionPanel = new ReportSelectionContainer();
            final ReportSelectionTree reportSelectionTree = new ReportSelectionTree();
            reportSelectionTree.tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) reportSelectionTree.tree.getLastSelectedPathComponent();
                    reportSelectionTree.selectedLabel.setText(selectedNode.toString());
                    currRepInterface = null;
                    reportSelectionTree.selectedLabel.setText(/*currRepInterface.identifier()//*/selectedNode.getUserObject().toString());
                    try {
                        currRepInterface = ReportBaseFactory.getReport(selectedNode.getUserObject().toString());
                        f.getPanelDetail().removeAll();
                        if (currRepInterface instanceof NewEntityJasperReport) {
                            final NewEntityJasperReport panelReport = (NewEntityJasperReport) currRepInterface;
                            panelReport.loadParameterSelections();
                            //com.openbravo.pos.reports.PanelReportBean panelReport = new com.openbravo.pos.reports.PanelReportBean();
                            //com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(panelReport.getQbffilter(), options, "Party Selection");
                            //com.openbravo.pos.reports.params.JParamHelper.addReportProductIdSelection(panelReport.getQbffilter(), options, "Product Selection");

                          /*  panelReport.jToggleReportTree.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    selectionPanel.setMenuVisible(!selectionPanel.m_jPanelLeft.isVisible());
                                    panelReport.assignMenuButtonIcon(selectionPanel.m_jPanelLeft.isVisible());
                                }
                            }); */

                            //   System.out.println("getCompiledReportPathAndFile() : " + getCompiledReportPathAndFile());
                            //   System.out.println("getReturnStatus : " + chooser.getReturnStatus());
                            //   panelReport.setReport("C:\\ofbiz\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\com\\openbravo\\pos\\reports\\closedpos.jasper");
                            panelReport.init(null);
                            try {
                                panelReport.activate();
                            } catch (BasicException ex) {
                                Logger.getLogger(NewReportMainController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            f.getPanelDetail().removeAll();
                            f.getPanelDetail().setLayout(new BorderLayout());
                            f.getPanelDetail().add(panelReport, BorderLayout.CENTER);
                            f.getPanelDetail().repaint();
                        } /*else {
                         final com.openbravo.pos.reports.PanelReportBean panelReport = NewReportMainController.getReportPanel(module);
                         panelReport.jToggleReportTree.addActionListener(new ActionListener() {
                         @Override
                         public void actionPerformed(ActionEvent e) {
                         selectionPanel.setMenuVisible(!selectionPanel.m_jPanelLeft.isVisible());
                         panelReport.assignMenuButtonIcon(selectionPanel.m_jPanelLeft.isVisible());
                         }
                         });
                         f.getPanelDetail().removeAll();
                         f.getPanelDetail().setLayout(new BorderLayout());
                         f.getPanelDetail().add(panelReport, BorderLayout.CENTER);
                         f.getPanelDetail().repaint();
                         }*/
                        //if (currRepInterface.getSelectionPanel() != null) {
                        //    containerPanel.getPanelDetail().add(currRepInterface.getSelectionPanel(), BorderLayout.CENTER);
                        //   }*/                                        
                        //containerPanel.getPanelDetail().repaint();

//        com.openbravo.pos.reports.PanelReportBean panelBean = getReportPanel("pos");
                    } catch (Exception ex) {
                        Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
                        currRepInterface = null;
                    }

                    /* if (currRepInterface == null) {
                     final com.openbravo.pos.reports.PanelReportBean panelReport = NewReportMainController.getReportPanel(module);
                     panelReport.jToggleReportTree.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                     selectionPanel.setMenuVisible(!selectionPanel.m_jPanelLeft.isVisible());
                     panelReport.assignMenuButtonIcon(selectionPanel.m_jPanelLeft.isVisible());
                     }
                     });
                     f.getPanelDetail().removeAll();
                     f.getPanelDetail().setLayout(new BorderLayout());
                     f.getPanelDetail().add(panelReport, BorderLayout.CENTER);
                     f.getPanelDetail().repaint();
                     }*/
                }
            });
    
            final TwoPanelReportInternalFrame internalFrame = (TwoPanelReportInternalFrame) f;
            com.openbravo.pos.reports.common.JFlowPanel jPeople = new com.openbravo.pos.reports.common.JFlowPanel();
            jPeople.setLayout(new BorderLayout());
            jPeople.add(reportSelectionTree, BorderLayout.CENTER);
            
            reportSelectionTree.setPreferredSize(new Dimension(270,ControllerOptions.getDesktopPane().getBounds().height-60));            
            jPeople.setPreferredSize(internalFrame.getPosReportPanel().m_jPanelLeft.getPreferredSize());            
            //final ReportSelectionTree reportSelectionTree = new ReportSelectionTree();
            internalFrame.getPosReportPanel().m_jPanelLeft.getViewport().setView(null);

            internalFrame.getPosReportPanel().m_jPanelLeft.getViewport().setView(jPeople);
//            selectionPanel.m_jPanelLeft.setLayout(new BorderLayout());
//            selectionPanel.m_jPanelLeft.add(reportSelectionTree, BorderLayout.CENTER);
//            f.getPanelButton().setLayout(new BorderLayout());
//            f.getPanelButton().add(selectionPanel, BorderLayout.CENTER);
        }

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
        if (reportInterface != null) {
            reportPanel = reportInterface.runReport();
            OrderMaxUtility.addAPanelGrid(reportPanel, f.getPanelDetail());
        }
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

    static public com.openbravo.pos.reports.PanelReportBean getReportPanel(String name) {
        ControllerOptions options = new ControllerOptions();
        com.openbravo.pos.reports.PanelReportBean panelReport = new com.openbravo.pos.reports.PanelReportBean();
        com.openbravo.pos.reports.params.JParamHelper.AddReportDateIntervalSelection(panelReport.getQbffilter(), options, "Date Selection", "orderDate");
        com.openbravo.pos.reports.params.JParamHelper.AddReportPartyId(panelReport.getQbffilter(), options, "Party Selection");
        com.openbravo.pos.reports.params.JParamHelper.addReportProductIdSelection(panelReport.getQbffilter(), options, "Product Selection");
        com.openbravo.pos.reports.params.JParamHelper.addReportCategoryIdSelection(panelReport.getQbffilter(), options, "Category Selection");

        //com.openbravo.pos.reports.JParamsDatesInterval paramdates = new com.openbravo.pos.reports.JParamsDatesInterval("invoiceDate");
        // paramdates.setStartDate(com.openbravo.beans.DateUtils.getToday());
        //panelReport.addQBFFilter(paramdates);
        //com.openbravo.pos.reports.JParamsPartyId panel = new com.openbravo.pos.reports.JParamsPartyId();
        //panelReport.addQBFFilter(panel);
        //panelReport.activate();
        //final SelectAddressPanel viewer = new SelectAddressPanel(options);
        //viewer.setPartyContactMechCompositeListData(panel.getOrder().getBillToAccount().getParty().getPartyContactList());
        options.setSimpleScreenInterface(panelReport);
        panelReport.setReport("C:\\ofbiz\\ofbiz-12.04.02\\specialpurpose\\pos\\src\\com\\openbravo\\pos\\reports\\closedpos.jasper");
        panelReport.init(null);
        try {
            panelReport.activate();
        } catch (BasicException ex) {
            Logger.getLogger(NewReportMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return panelReport;
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
