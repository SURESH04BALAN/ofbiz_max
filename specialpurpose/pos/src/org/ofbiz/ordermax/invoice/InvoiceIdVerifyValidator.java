/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JTextField;
import mvc.controller.BaseInputVerifier;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoles;

/**
 *
 * @author siranjeev
 */
public class InvoiceIdVerifyValidator extends BaseInputVerifier {

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(String roleTypeId) {
        this.roleTypeId = roleTypeId;
    }



    private String partyId;
    private String roleTypeId = "BILL_FROM_VENDOR";
    private String invoiceId;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public InvoiceIdVerifyValidator(JTextField field, XuiSession session) {
        this.field = field;
        this.session = session;
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {

        //if empty null then return
        if (field.getText() == null || field.getText().isEmpty()) {
            return true;
        }

        if (verify(input)) {
            //can Change??
            shouldYieldFocus = true;
            ActionEvent event = new ActionEvent(this, 1, "orderId", new Date().getTime(), 2);
            for (ActionListener listener : listeners) {
                listener.actionPerformed(event); // broadcast to all
            }
            return shouldYieldFocus;
        } else {
            field.selectAll();
            return false;
        }
    }

    @Override
    public boolean verify(JComponent input) {
        try {
            String entityName = "InvoiceRole";
            invoiceId = field.getText();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            if (partyId != null && partyId.isEmpty() == false) {
                whereClauseMap.put("partyId", partyId);
            }
            
            whereClauseMap.put("invoiceId", invoiceId);
            whereClauseMap.put("roleTypeId", roleTypeId);

            List<GenericValue> genValList = PosProductHelper.getGenericValueListsWithSelection(entityName, whereClauseMap, null, session.getDelegator());

            if (genValList.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
