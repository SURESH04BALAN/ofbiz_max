/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.ofbiz.entity.GenericValue;

/**
 *
 * @author BBS Auctions
 */
public interface EntitySaveInterface {
    public boolean deleteEntity(GenericValueInterface genericSaveInterface, String entityName);
    public boolean saveEntity(GenericSaveInterface genericSaveInterface, String entityName);
}
