/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.entity;

/**
 *
 * @author siranjeev
 */
public interface DisplayNameInterface {
    public enum DisplayTypes{
        SHOW_NAME_ONLY,
        SHOW_CODE_ONLY,
        SHOW_NAME_AND_CODE,
        SHOW_CODE_AND_TIME,
    };
    public String getdisplayName(DisplayTypes showId);
}
