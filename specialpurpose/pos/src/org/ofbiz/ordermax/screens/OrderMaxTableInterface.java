/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

import javax.swing.table.TableModel;

/**
 *
 * @author siranjeev
 */
public interface OrderMaxTableInterface {

    void appendEmpty(TableModel jmodel);

    void clearItemDataModel();

    TableModel  createModel();

//    void run(PosScreen pos, TableModel  model);

    void setColumnWidth(TableModel  jmodel);
    
}
