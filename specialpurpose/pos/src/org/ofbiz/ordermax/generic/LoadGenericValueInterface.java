/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.generic;

import java.util.List;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author BBS Auctions
 */
public interface LoadGenericValueInterface<T> {
    public List<T> loadValues(ControllerOptions options);
}
