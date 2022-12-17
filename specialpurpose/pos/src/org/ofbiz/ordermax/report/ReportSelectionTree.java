/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report;

import org.ofbiz.ordermax.report.reports.ReportBaseFactory;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.ofbiz.base.util.Debug;

/**
 * JTree basic tutorial and example
 *
 * @author wwww.codejava.net
 */
public class ReportSelectionTree extends JPanel {

    public JTree tree;
    public JLabel selectedLabel;

    public ReportSelectionTree() {
        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        for (Map.Entry<String, Map<String, List<String>>> subreports : ReportBaseFactory.reportMap.entrySet()) {
            DefaultMutableTreeNode repGroup = new DefaultMutableTreeNode(subreports.getKey());

            Map<String, List<String>> reports = subreports.getValue();

            for (Map.Entry<String, List<String>> mapEntry : reports.entrySet()) {
                DefaultMutableTreeNode subReportGroup = new DefaultMutableTreeNode(mapEntry.getKey());
                for (String instance : mapEntry.getValue()) {
                    Debug.logInfo("instance: " + instance, instance);
                    try {
                        final ReportInterface repInterface = ReportBaseFactory.getReport(instance);
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(repInterface.identifier()) {

                            @Override
                            public String toString() {
                                return repInterface.identifier();
                            }
                        };
                        //DefaultMutableTreeNode node = new DefaultMutableTreeNode(repInterface.identifier());
                        node.setUserObject(instance);
                        subReportGroup.add(node);
                    } catch (Exception ex) {
                        Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                repGroup.add(subReportGroup);
            }
            root.add(repGroup);
            /*            
             for (Map.Entry<String, List<String>> mapEntry : ReportBaseFactory.reportMap.entrySet()) {
             DefaultMutableTreeNode repGroup = new DefaultMutableTreeNode(mapEntry.getKey());
            
             for (String instance : mapEntry.getValue()) {
             try {
             final ReportInterface repInterface = ReportBaseFactory.getReport(instance);
             DefaultMutableTreeNode node = new DefaultMutableTreeNode(repInterface.identifier()) {

             @Override
             public String toString() {
             return repInterface.identifier();
             }
             };
             //DefaultMutableTreeNode node = new DefaultMutableTreeNode(repInterface.identifier());
             node.setUserObject(instance);
             repGroup.add(node);
             } catch (Exception ex) {
             Logger.getLogger(ReportSelectionTree.class.getName()).log(Level.SEVERE, null, ex);
             }
             }*/

        }

        //add the child nodes to the root node
//        root.add(vegetableNode);
//        root.add(fruitNode);
        //create the tree by passing in the root node
        tree = new JTree(root);

//        ImageIcon imageIcon = new ImageIcon(TreeExample.class.getResource("/leaf.jpg"));
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        //      renderer.setLeafIcon(imageIcon);

        tree.setCellRenderer(renderer);
        tree.setShowsRootHandles(true);
        tree.setRootVisible(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(tree), BorderLayout.CENTER);

        selectedLabel = new JLabel();
        add(selectedLabel, BorderLayout.SOUTH);

//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setTitle("JTree Example");       
//        this.setSize(200, 200);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReportSelectionTree();
            }
        });
    }
}
