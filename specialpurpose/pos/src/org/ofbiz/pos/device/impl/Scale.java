/**
 * *****************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * *****************************************************************************
 */
package org.ofbiz.pos.device.impl;

import jpos.JposConst;
import jpos.JposException;

import org.ofbiz.pos.device.GenericDevice;

public class Scale extends GenericDevice {

    public static final String module = Scale.class.getName();

    public Scale(String deviceName, int timeout) {
        super(deviceName, timeout);
        this.control = new jpos.Scale();
    }

    protected void initialize() throws JposException {
//        throw new JposException(JposConst.JPOS_E_NOEXIST, "Device not yet implemented");
    }

    public void readWeight(int[] ints, int i) throws JposException {
        ((jpos.Scale) (this.control)).readWeight(ints, i);
    }

    public String getDeviceName() {
        return deviceName;
    }

    public boolean getCapDisplayText() throws JposException {
        return ((jpos.Scale) (this.control)).getCapDisplayText();
    }

    public boolean getCapPriceCalculating() throws JposException {
        return ((jpos.Scale) (this.control)).getCapPriceCalculating();
    }

    public int getCapPowerReporting() throws JposException {
        return ((jpos.Scale) (this.control)).getCapPowerReporting();
    }

    public boolean getCapTareWeight() throws JposException {
        return ((jpos.Scale) (this.control)).getCapTareWeight();
    }

    public boolean getCapZeroScale() throws JposException {
        return ((jpos.Scale) (this.control)).getCapZeroScale();
    }

    public boolean getAsyncMode() throws JposException {
        return ((jpos.Scale) (this.control)).getAsyncMode();
    }

    public void setAsyncMode(boolean bln) throws JposException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean getAutoDisable() throws JposException {
        return ((jpos.Scale) (this.control)).getAutoDisable();
    }

    public void setAutoDisable(boolean bln) throws JposException {
        ((jpos.Scale) (this.control)).setAutoDisable(bln);
    }

    public int getMaxDisplayTextChars() throws JposException {
        return ((jpos.Scale) (this.control)).getMaxDisplayTextChars();
    }

    public long getSalesPrice() throws JposException {
        return ((jpos.Scale) (this.control)).getSalesPrice();
    }

    public int getTareWeight() throws JposException {
        return ((jpos.Scale) (this.control)).getTareWeight();
    }

    public void setTareWeight(int i) throws JposException {
        ((jpos.Scale) (this.control)).setTareWeight(i);
    }

    public long getUnitPrice() throws JposException {
        return ((jpos.Scale) (this.control)).getUnitPrice();
    }

    public void setUnitPrice(long l) throws JposException {
        ((jpos.Scale) (this.control)).setUnitPrice(l);
    }

    public int getPowerNotify() throws JposException {
        return ((jpos.Scale) (this.control)).getPowerNotify();
    }

    public void setPowerNotify(int i) throws JposException {
        ((jpos.Scale) (this.control)).setPowerNotify(i);
    }

    public int getPowerState() throws JposException {
        return ((jpos.Scale) (this.control)).getPowerState();
    }

    public void clearInput() throws JposException {
        ((jpos.Scale) (this.control)).clearInput();
    }

    public void displayText(String string) throws JposException {
        ((jpos.Scale) (this.control)).displayText(string);
    }

    public void zeroScale() throws JposException {
        ((jpos.Scale) (this.control)).zeroScale();
    }

    public boolean getCapDisplay() throws JposException {
        return ((jpos.Scale) (this.control)).getCapDisplay();
    }

    public int getMaximumWeight() throws JposException {
        return ((jpos.Scale) (this.control)).getMaximumWeight();
    }

    public int getWeightUnit() throws JposException {
        return ((jpos.Scale) (this.control)).getWeightUnit();
    }

}
