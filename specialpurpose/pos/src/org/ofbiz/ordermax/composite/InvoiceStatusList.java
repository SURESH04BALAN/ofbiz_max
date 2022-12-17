/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.entity.InvoiceStatus;
import org.ofbiz.ordermax.entity.OrderStatus;

/**
 *
 * @author siranjeev
 */
public class InvoiceStatusList extends ListAdapterListModel<InvoiceStatus>{
/*   ORDER_APPROVED
ORDER_CANCELLED
ORDER_COMPLETED
ORDER_CREATED
ORDER_HOLD
ORDER_PROCESSING
ORDER_REJECTED
ORDER_SENT
    public OrderStatus getBillingLocationContact(){
        OrderStatus oc = getOrderStatus("ORDER_APPROVED");
        return oc;      
    }

    public OrderStatus getBillingLocationPhoneContact(){
        OrderStatus oc = getOrderStatus("PHONE_BILLING");
        return oc;      
    }    

    public OrderStatus getShippingLocationContact(){
        OrderStatus oc = getOrderStatus("SHIPPING_LOCATION");
        return oc;      
    }

    public OrderStatus getShippingLocationPhoneContact(){
        OrderStatus oc = getOrderStatus("PHONE_SHIPPING");
        return oc;      
    }    
  */  
    public InvoiceStatus getOrderStatus(String statusId){
        InvoiceStatus os = null;
        for(InvoiceStatus itr : list){
            if(itr.getstatusId().equals(statusId)){
                os = itr;
                break;
            }
        }
        return os;     
    }    
}
