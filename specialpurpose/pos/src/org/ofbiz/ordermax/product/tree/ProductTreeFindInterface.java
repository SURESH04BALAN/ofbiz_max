/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.tree;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author BBS Auctions
 */
public interface ProductTreeFindInterface {
    String getName();
    List<String> getProductIds();
    JPanel getPanel();
    void addActionListener(ActionListener listener);
}
