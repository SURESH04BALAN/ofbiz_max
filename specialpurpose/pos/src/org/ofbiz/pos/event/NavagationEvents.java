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
 ******************************************************************************
 */
package org.ofbiz.pos.event;

import java.awt.AWTEvent;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.component.Input;
import org.ofbiz.pos.screen.PosScreen;
import org.ofbiz.base.util.Debug;
import org.ofbiz.pos.config.ButtonEventConfig;
import org.ofbiz.base.util.UtilValidate;
import net.xoetrope.xui.XPage;
import net.xoetrope.xui.XProject;
import net.xoetrope.xui.XProjectManager;

public class NavagationEvents {

    public static synchronized void showPosScreen(PosScreen pos) {
        ManagerEvents.mgrLoggedIn = false;
        pos.showPage("pospanel");
    }

    public static synchronized void showPayScreen(PosScreen pos) {
        ManagerEvents.mgrLoggedIn = false;
        PosTransaction trans = PosTransaction.getCurrentTx(pos.getSession());
        if (trans.isEmpty()) {
            pos.showMessageDialog("dialog/error/noitems");
        } else {
            PosScreen newPos = pos.showPage("paypanel");
            newPos.getInput().setFunction("TOTAL");
            newPos.refresh();
        }
    }

    public static synchronized void showPromoScreen(PosScreen pos) {
        ManagerEvents.mgrLoggedIn = false;
        pos.showPage("promopanel");
    }

    public static synchronized void showRiceScreen(PosScreen pos) {
        ManagerEvents.mgrLoggedIn = false;
        pos.showPage("ricepanel");

    }

    public static synchronized void actionNavigation(PosScreen pos, AWTEvent event) {
        Input input = pos.getInput();
        String[] func = input.getFunction("QTY");
        String value = input.value();
        Debug.logInfo("menuAction: " + value, "module");
        ManagerEvents.mgrLoggedIn = false;
//        pos.showPage("ricepanel");
        
// no value; just return
        if (event != null && UtilValidate.isEmpty(value)) {
            String buttonName = ButtonEventConfig.getButtonName(event);
            if (UtilValidate.isNotEmpty(buttonName)) {
                if (buttonName.startsWith("ACTION.")) {
                    value = buttonName.substring(7);
                }
            }
            if (UtilValidate.isEmpty(value)) {
                return;
            }
        }
        Debug.logInfo("menuAction: " + value, "module");        
        XProject currentProject = XProjectManager.getCurrentProject();
        currentProject.getPageManager().loadPage(pos.getScreenLocation() + "/" + value);        
        pos.showPage(value);
        
    }

}
