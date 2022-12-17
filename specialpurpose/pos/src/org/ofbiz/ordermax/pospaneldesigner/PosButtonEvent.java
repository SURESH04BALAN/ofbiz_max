/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import java.util.logging.Logger;

/**
 *
 * @author administrator
 */
public class PosButtonEvent {

    private String buttonName; 
    private String className;
    private String methodName;
    private String disableLock;
    private static final Logger LOG = Logger.getLogger(PosButtonEvent.class.getName());

    public PosButtonEvent() {
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDisableLock() {
        return disableLock;
    }

    public void setDisableLock(String disableLock) {
        this.disableLock = disableLock;
    }
}
