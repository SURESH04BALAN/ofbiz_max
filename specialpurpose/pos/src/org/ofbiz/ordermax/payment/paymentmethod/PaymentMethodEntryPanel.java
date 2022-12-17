/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment.paymentmethod;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.PaymentGroupComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PaymentMethodType;
import org.ofbiz.ordermax.payment.PaymentMethodTypeSingleton;

/**
 *
 * @author BBS Auctions
 */
public class PaymentMethodEntryPanel extends javax.swing.JPanel {
//    private JPanel exteriorPanel;

    private JPanel interiorPanel;
    private JButton button;

    protected Map<String, PaymentTypePaymentEntryPanel> mapPaymentTypePaymentEntryPanel = new HashMap<String, PaymentTypePaymentEntryPanel>();

    /**
     * Creates new form PaymentMethodEntryPanel
     */
    public PaymentMethodEntryPanel() {
        initComponents();
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
        PaymentTypePaymentEntryPanel panelVal = new PaymentTypePaymentEntryPanel();
        panelVal.lbltypeName.setText("Payment Type");
        panelVal.txtPaymentAmount.setText("Amount");
        panelVal.txtPaymentAmount.setEnabled(false);
        panelVal.txtPaymentReference.setEnabled(false);
        panelVal.txtPaymentReference.setText(" Reference ");
        getPanelInterior().add(panelVal);
        interiorPanel.setLayout(new BoxLayout(interiorPanel, BoxLayout.PAGE_AXIS));

        //interiorPanel.setLayout(new GridLayout(0, 1));
        JScrollPane anotherScroll = new JScrollPane();
        anotherScroll.setViewportView(interiorPanel);

        //exteriorPanel = new JPanel();
        panelPaymentMethodAmount.setLayout(new BorderLayout());
        panelPaymentMethodAmount.add(BorderLayout.CENTER, anotherScroll);
        panelPaymentMethodAmount.setPreferredSize(new Dimension(400, 200));

        interiorPanel.setBackground(new Color(255, 0, 0));

        ActionListener invoiceIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                changePaymentPanel(mapPaymentTypePaymentEntryPanel.get(e.getActionCommand()).getPaymentMethodType());
                Debug.logInfo("e.getActionCommand(): " + e.getActionCommand(), "Test");
            }
        };

        //get sorted list of type ids
        List<PaymentMethodType> sortedPaymentMethodTypeList = PaymentMethodTypeSingleton.getValueList();
        Collections.sort(sortedPaymentMethodTypeList, new Comparator<PaymentMethodType>() {
            public int compare(PaymentMethodType p1, PaymentMethodType p2) {
                return p1.getdescription().trim().compareTo(p2.getdescription().trim());
            }
        });

        for (PaymentMethodType pmt : sortedPaymentMethodTypeList) {
            //  for (String s : imageNames) {
//            model.addElement(s);
            PaymentTypePaymentEntryPanel panel = new PaymentTypePaymentEntryPanel();
            panel.lbltypeName.setText(pmt.getdisplayName(DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY));
            panel.txtPaymentAmount.setText("");
            panel.setPaymentMethodType(pmt);
            panel.addActionListener(invoiceIdChangeAction);
            getPanelInterior().add(panel);
            mapPaymentTypePaymentEntryPanel.put(pmt.getpaymentMethodTypeId(), panel);
        }
    }

    final public JPanel getPanelInterior() {
        return interiorPanel;
    }

    public Map<String, PaymentTypePaymentEntryPanel> getPaymentMethodEntry() {
        Map<String, PaymentTypePaymentEntryPanel> tmpPaymentTypePaymentEntryPanel = new HashMap<String, PaymentTypePaymentEntryPanel>();
        for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : mapPaymentTypePaymentEntryPanel.entrySet()) {
            if (UtilValidate.isNotEmpty(entryPanel.getValue().txtPaymentAmount.getText())) {
                tmpPaymentTypePaymentEntryPanel.put(entryPanel.getKey(), entryPanel.getValue());
            }
        }
        return tmpPaymentTypePaymentEntryPanel;
    }

    public void clearDialogFields() {

        for (Map.Entry<String, PaymentTypePaymentEntryPanel> entryPanel : mapPaymentTypePaymentEntryPanel.entrySet()) {
            entryPanel.getValue().txtPaymentAmount.setText("");
            entryPanel.getValue().txtPaymentReference.setText("");
        }

    }
    int i = 0;
    PaymentMethodType currPaymentMethodType = null;

    private void buttonActionPerformed(ActionEvent e) {
        //      model.addElement("Hola");
        String s = "Hello " + i++;
        PaymentTypePaymentEntryPanel panel = new PaymentTypePaymentEntryPanel();
        panel.lbltypeName.setText(s);
        panel.txtPaymentAmount.setText(s);
        panel.txtPaymentReference.setText(s);
        getPanelInterior().add(panel);
        interiorPanel.revalidate();
    }

    PaymentMethodInterface paymentMethodInterface = null;
    Map<String, Map<String, GenericValue>> mapList = null;

    public void changePaymentPanel(PaymentMethodType paymentMethodType) {
        System.out.println("Focus lost in JPanel 1");
        if (paymentMethodType != null) {
            System.out.println("Focus lost in JPanel 2");
            if (!(currPaymentMethodType != null && currPaymentMethodType.getpaymentMethodTypeId().equals(paymentMethodType.getpaymentMethodTypeId()))) {
                System.out.println("Focus lost in JPanel 3");
                try {
                    /*           List<PaymentMethod> list = new ArrayList<PaymentMethod>();
                     mapList = LoadPaymentWorker.getPartyPaymentMethodValueMaps(XuiContainer.getSession().getDelegator(), panelCustomerPartyIdFromPicker.textIdField.getText(),
                     paymentMethodType.getpaymentMethodTypeId(), false);
                     for (Map.Entry<String, Map<String, GenericValue>> mapIter : mapList.entrySet()) {
                     GenericValue val = mapIter.getValue().get("paymentMethod");
                     PaymentMethod payMeth = new PaymentMethod(val);
                     list.add(payMeth);
                     }
                     */
                    paymentMethodInterface = PaymentMethodBaseFactory.getReport(paymentMethodType.getpaymentMethodTypeId());
                } catch (Exception ex) {
                    //Logger.getLogger(CustomerPaymentEntryHeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
                    paymentMethodInterface = null;
                }

                panelPaymentMethodDetail.removeAll();
                if (paymentMethodInterface != null) {
                    panelPaymentMethodDetail.setLayout(new BorderLayout());
                    panelPaymentMethodDetail.add(paymentMethodInterface.getPanel(), BorderLayout.CENTER);
                }
                panelPaymentMethodDetail.revalidate();
                /*paymentMethodComboBox.setDataList(list);
                 if (!list.isEmpty()) {
                 paymentMethodComboBox.setSelectedItem(list.get(0));
                 }*/

            }
        }

    }

    public void setDialogFields() {
        if (paymentComposite != null) {
            setPaymentMethodValues(paymentComposite);
            if (UtilValidate.isNotEmpty(paymentComposite.getPayment().getpaymentMethodTypeId())) {
                PaymentTypePaymentEntryPanel panel = mapPaymentTypePaymentEntryPanel.get(paymentComposite.getPayment().getpaymentMethodTypeId());
                changePaymentPanel(panel.getPaymentMethodType());
            }
        } else if (paymentGroupComposite != null) {
            for (PaymentGroupComposite.PaymentGroupMemberComposite paymentCompo : paymentGroupComposite.getList()) {
                setPaymentMethodValues(paymentCompo.paymentComposite);
                if (UtilValidate.isNotEmpty(paymentCompo.paymentComposite.getPayment().getpaymentMethodTypeId())) {
                    PaymentTypePaymentEntryPanel panel = mapPaymentTypePaymentEntryPanel.get(paymentCompo.paymentComposite.getPayment().getpaymentMethodTypeId());
                    changePaymentPanel(panel.getPaymentMethodType());
                }
            }
        }
    }

    private void setPaymentMethodValues(PaymentComposite paymentComposite) {
        if (UtilValidate.isNotEmpty(paymentComposite.getPayment().getpaymentMethodTypeId())) {
            PaymentTypePaymentEntryPanel panel = mapPaymentTypePaymentEntryPanel.get(paymentComposite.getPayment().getpaymentMethodTypeId());
            panel.txtPaymentAmount.setText(paymentComposite.getPayment().getamount().toString());
            panel.txtPaymentReference.setText(paymentComposite.getPayment().getpaymentRefNum());
            // changePaymentPanel(panel.getPaymentMethodType());
        }
    }
    PaymentComposite paymentComposite = null;
    PaymentGroupComposite paymentGroupComposite = null;

    public void setPaymentComposite(PaymentComposite paymentComposite) {
        this.paymentComposite = paymentComposite;

    }

    public void setPaymentGroupComposite(PaymentGroupComposite paymentGroupComposite) {
        this.paymentGroupComposite = paymentGroupComposite;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPaymentMethodAmount = new javax.swing.JPanel();
        panelPaymentMethodDetail = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout panelPaymentMethodAmountLayout = new javax.swing.GroupLayout(panelPaymentMethodAmount);
        panelPaymentMethodAmount.setLayout(panelPaymentMethodAmountLayout);
        panelPaymentMethodAmountLayout.setHorizontalGroup(
            panelPaymentMethodAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelPaymentMethodAmountLayout.setVerticalGroup(
            panelPaymentMethodAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(panelPaymentMethodAmount);

        javax.swing.GroupLayout panelPaymentMethodDetailLayout = new javax.swing.GroupLayout(panelPaymentMethodDetail);
        panelPaymentMethodDetail.setLayout(panelPaymentMethodDetailLayout);
        panelPaymentMethodDetailLayout.setHorizontalGroup(
            panelPaymentMethodDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelPaymentMethodDetailLayout.setVerticalGroup(
            panelPaymentMethodDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(panelPaymentMethodDetail);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelPaymentMethodAmount;
    private javax.swing.JPanel panelPaymentMethodDetail;
    // End of variables declaration//GEN-END:variables
}
