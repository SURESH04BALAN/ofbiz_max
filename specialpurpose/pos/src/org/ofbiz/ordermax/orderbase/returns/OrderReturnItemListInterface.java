/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.orderbase.returns;

import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ListModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.table.ActionTableModel;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.OrderHeader;

/**
 *
 * @author BBS Auctions
 */
public interface OrderReturnItemListInterface {

    void clearDialogFields();

    JButton getFindBtn();

    String getOrderId();
    String getInvoiceId();

    ActionTableModel getOrderEntryTableModel();

    void setDialogFields();

    void setupEditOrderTable();
    
    JPanel getPanel();  
    void setOrderComboModelList(List<OrderHeader>  list);        
    void reloadItemDataModel(ListModel<ReturnItemComposite> cutdownList);
    void setReturnHeaderComposite(ReturnHeaderComposite val);
    ReturnHeaderComposite getReturnHeaderComposite();
}
