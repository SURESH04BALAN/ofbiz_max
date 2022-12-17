/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.paymentmethod;


/**
 *
 * @author BBS Auctions
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Demo extends JPanel {

    private JPanel exteriorPanel;
    private JPanel interiorPanel;
    private JButton button;
   
    static private String[] imageNames = {"Bird", "Cat", "Dog", "Rabbit", "Pig", "dukeWaveRed",
        "kathyCosmo", "lainesTongue", "left", "middle", "right", "stickerface"};

    public Demo() {

        button = new JButton("Add element");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buttonActionPerformed(e);
            }
        });

        interiorPanel = new JPanel();
//        interiorPanel.add(listScrollPane);
//        interiorPanel.add(button);
        interiorPanel.setLayout(new BoxLayout(interiorPanel, BoxLayout.PAGE_AXIS));

        //interiorPanel.setLayout(new GridLayout(0, 1));

        JScrollPane anotherScroll = new JScrollPane();
        anotherScroll.setViewportView(interiorPanel);

        exteriorPanel = new JPanel();
        exteriorPanel.setLayout(new BorderLayout());
        exteriorPanel.add(BorderLayout.CENTER, anotherScroll);
        exteriorPanel.setPreferredSize(new Dimension(400, 200));

        interiorPanel.setBackground(new Color(255, 0, 0));

    }
    int i =0;
    private void buttonActionPerformed(ActionEvent e) {
  //      model.addElement("Hola");
        String s = "Hello " + i++;
             PaymentTypePaymentEntryPanel panel = new PaymentTypePaymentEntryPanel();
            panel.lbltypeName.setText(s);
            panel.txtPaymentAmount.setText(s);
            getPanelInterior().add(panel);
        interiorPanel.revalidate();
    }


    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Demo demo = new Demo();
        frame.getContentPane().add(demo.getExteriorPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        for (String s : imageNames) {
//            model.addElement(s);
            PaymentTypePaymentEntryPanel panel = new PaymentTypePaymentEntryPanel();
            panel.lbltypeName.setText(s);
            panel.txtPaymentAmount.setText(s);
            demo.getPanelInterior().add(panel);

        }
        demo.getPanelInterior().revalidate();
        demo.getPanelInterior().repaint();

    }

    public JPanel getExteriorPanel() {
        return exteriorPanel;
    }

    public JPanel getPanelInterior() {
        return interiorPanel;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
