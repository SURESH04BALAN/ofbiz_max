/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.composite;

import org.ofbiz.ordermax.entity.SupplierProduct;

/**
 *
 * @author siranjeev
 */
public class SupplierProductComposite {


    private SupplierProduct supplierProduct=null;
    private Account account=null;
    
    public SupplierProductComposite() {
    }

    public SupplierProduct getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(SupplierProduct supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
}
