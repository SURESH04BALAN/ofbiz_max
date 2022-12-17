/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.utility;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.TreeNode;
import org.ofbiz.ordermax.generic.GenericProductSearchPanel;
import org.ofbiz.ordermax.generic.GenericValueDetailTableDialog;
import javolution.util.FastList;
/**
 *
 * @author siranjeev
 */
public class ProductSelectionPanel {
    
    static private boolean isVaildProductSearch(Map<String, Object> genParty, Map<String, String> searchSelMap) {
        boolean result = true;

        if (searchSelMap.isEmpty() == false) {

            for (Map.Entry<String, String> anEntry : searchSelMap.entrySet()) {
                String val = (String) genParty.get(anEntry.getKey());
                if (anEntry.getValue() == null || val.toUpperCase().indexOf(anEntry.getValue()) == -1) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    static public List<Map<String, Object>> getProductList(ProductDataTreeLoader productListArray, Map<String, String> searchSelMap) {
        List<Map<String, Object>> list = FastList.newInstance();

        List<TreeNode> productList = productListArray.getAllProductsTreeNodes();
        for (TreeNode treeProduct : productList) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("productId", treeProduct._id);
            dataMap.put("internalName", treeProduct._name);

            if (isVaildProductSearch(dataMap, searchSelMap)) {
                list.add(dataMap);
            }
        }

        return list;
    }

    static public Map<String, Object> getUserProductSelection(final ProductDataTreeLoader productListArray) throws java.text.ParseException, ParseException {
        {
            Map<String, Object> seleVal = null;

            String[][] PartyIdColumnName = {
                {"productId", "Product Id"},
                {"internalName", "description"},};

            final GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(null, false, XuiContainer.getSession(), PartyIdColumnName);
            final GenericProductSearchPanel searchPanel = new GenericProductSearchPanel();
            dlg.setColumnWidths(Arrays.asList(250, 400));
            dlg.setDoCellRendering(false);
            dlg.setReadOnlyTable(true);
            
            Map<String, String> searchSelMap = searchPanel.getSearchSelection();
            List<Map<String, Object>> list = getProductList(productListArray,searchSelMap);
            dlg.setupOrderTableMap(list);

            searchPanel.btnSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Map<String, String> searchSelMap = searchPanel.getSearchSelection();
                    List<Map<String, Object>> list = getProductList(productListArray,searchSelMap);
                    dlg.setupOrderTableMap(list);

                }
            });

            searchPanel.textProductId.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    Map<String, String> searchSelMap = searchPanel.getSearchSelection();
                    List<Map<String, Object>> list = getProductList(productListArray,searchSelMap);
                    dlg.setupOrderTableMap(list);
                }
            });
            
            searchPanel.textDescription.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    Map<String, String> searchSelMap = searchPanel.getSearchSelection();
                    List<Map<String, Object>> list = getProductList(productListArray,searchSelMap);
                    dlg.setupOrderTableMap(list);
                }
            });

            searchPanel.btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    searchPanel.resetSearchSelection();
                    Map<String, String> searchSelMap = searchPanel.getSearchSelection();
                    List<Map<String, Object>> list = getProductList(productListArray,searchSelMap);
                    dlg.setupOrderTableMap(list);

                }
            });

            dlg.getParentPanel().setPreferredSize(new Dimension(0, 123));
            dlg.getParentPanel().setLayout(new BorderLayout());
            dlg.getParentPanel().add(BorderLayout.CENTER, searchPanel);

            dlg.setVisible(true);
            Map<String, Object> genVal = dlg.getSelectedMapVal();
            if (genVal != null && dlg.getReturnStatus() == GenericValueDetailTableDialog.RET_OK) {
                seleVal = genVal;
            }

            return seleVal;
        }
    }
    
}
