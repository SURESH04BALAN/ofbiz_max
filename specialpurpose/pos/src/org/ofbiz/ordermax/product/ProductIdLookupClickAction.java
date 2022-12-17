/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.product.productloader.ProductTreeArraySingleton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import static org.ofbiz.guiapp.xui.XuiSession.module;
import org.ofbiz.ordermax.utility.ProductSelectionPanel;

/**
 *
 * @author siranjeev
 */
public class ProductIdLookupClickAction implements ActionListener {

    private SetProductIdInterface prodInterface;
    private JFrame pFrame;
    private XuiSession session;

    javax.swing.JDesktopPane desktopPane;

    public ProductIdLookupClickAction(SetProductIdInterface prodInterface, javax.swing.JDesktopPane desktopPane, XuiSession session) {
        this.prodInterface = prodInterface;

        this.session = session;
        this.desktopPane = desktopPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());
            if (genVal != null) {
                if (genVal.containsKey("productId")) {
                    String productId = genVal.get("productId").toString();
                    prodInterface.setProductId(productId);
                    Debug.logWarning("Order changed - product id text field: " + genVal.get("productId").toString(), module);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(ProductIdLookupClickAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
