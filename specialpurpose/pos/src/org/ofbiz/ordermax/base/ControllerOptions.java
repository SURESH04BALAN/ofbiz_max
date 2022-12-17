/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import com.openbravo.pos.forms.AppView;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.billingaccount.PrimaryIdInterface;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.entity.InvoiceType;
import org.ofbiz.ordermax.entity.OrderType;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.ReturnHeaderType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.orderbase.returns.ReturnHeaderTypeSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.product.PartyTypeSingleton;

/**
 *
 * @author BBS Auctions
 */
public class ControllerOptions {

    public static final String module = ControllerOptions.class.getName();
    private Map<String, Object> controllerOptions = new HashMap<String, Object>();
    static AppView mainApp = null;

    public static AppView getMainApp() {
        return mainApp;
    }

    public static void setMainApp(AppView mainApp) {
        ControllerOptions.mainApp = mainApp;
    }

    public ControllerOptions() {
    }

    static public XuiSession getSession() {
        return XuiContainer.getSession();
    }

    static public JDesktopPane getDesktopPane() {
        return XuiContainer.getSession().getDesktopPane();
    }

    public ControllerOptions(ControllerOptions rhs) {
        this.controllerOptions.putAll(rhs.controllerOptions);
    }

    public void addRoleType(String partyRoleId) {
        try {
            RoleType roleType = RoleTypeSingleton.getRoleType(partyRoleId);
            controllerOptions.put("roleTypeId", roleType);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RoleType getRoleType() {
        return (RoleType) controllerOptions.get("roleTypeId");
    }

    public void addPartyType(String partyTypeId) {
        try {
            PartyType partyType = PartyTypeSingleton.getPartyType(partyTypeId);
            controllerOptions.put("partyType", partyType);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PartyType getPartyType() {
        return (PartyType) controllerOptions.get("partyType");
    }

    public void addRoleTypeParent(String parentTypeId) {
        try {

            controllerOptions.put("parentTypeId", parentTypeId);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRoleTypeParent() {
        return (String) controllerOptions.get("parentTypeId");
    }

    public void addOrderType(String orderTypeId) {
        try {
            OrderType roleType = OrderTypeSingleton.getOrderType(orderTypeId);
            controllerOptions.put("orderTypeId", roleType);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addOrderId(String orderId) {
        controllerOptions.put("orderId", orderId);
    }

    public String getOrderId() {
        if (controllerOptions.containsKey("orderId")) {
            return (String) controllerOptions.get("orderId");
        }
        return null;
    }
    
    

    public void addName(String orderId) {
        controllerOptions.put("Name", orderId);
    }

    public String getName() {
        if (controllerOptions.containsKey("Name")) {
            return (String) controllerOptions.get("Name");
        }
        return null;
    }

    public void addReturnId(String returnId) {
        controllerOptions.put("returnId", returnId);
    }

    public String getReturnId() {
        if (controllerOptions.containsKey("returnId")) {
            return (String) controllerOptions.get("returnId");
        }
        return null;
    }

    public void addOrder(Order order) {
        controllerOptions.put("orderHeader", order);
    }

    public Order getOrder() {
        if (controllerOptions.containsKey("orderHeader")) {
            return (Order) controllerOptions.get("orderHeader");
        }
        return null;
    }
    

    public void addPrimaryIdInterface(PrimaryIdInterface primaryIdInterface) {
        controllerOptions.put("PrimaryIdInterface", primaryIdInterface);
    }

    public PrimaryIdInterface getPrimaryIdInterface() {
        if (controllerOptions.containsKey("PrimaryIdInterface")) {
            return (PrimaryIdInterface) controllerOptions.get("PrimaryIdInterface");
        }
        return null;
    }
    
    public void addPartyId(String partyId) {
        controllerOptions.put("partyId", partyId);
    }

    public String getPartyId() {
        return (String) getValue("partyId");
    }

    public void addProductId(String partyId) {
        controllerOptions.put("productId", partyId);
    }

    public String getProductId() {
        return (String) getValue("productId");
    }

    public void setPartyIdReadOnly(boolean value) {
        controllerOptions.put("partyIdReadOnly", new Boolean(value));
    }

    public boolean isPartyIdReadOnly() {
        Boolean readOnly = (Boolean) getValue("partyIdReadOnly");

        if (readOnly == null) {
            return false;
        }
        return readOnly;
    }

    public void addDefaultPartyIdField(JTextField partyId) {
        controllerOptions.put("defaultPartyId", partyId);
    }

    public String getDefaultPartyId() {
        JTextField field = (JTextField) getValue("defaultPartyId");
        if (field != null) {
            return field.getText();
        }
        return "";
    }

    public void addParentPaymentTypeId(String parentPaymentTypeId) {
        controllerOptions.put("parentPaymentTypeId", parentPaymentTypeId);
    }

    public String getParentPaymentTypeId() {
        return (String) getValue("parentPaymentTypeId");
    }

    public void addPaymentId(String returnId) {
        controllerOptions.put("paymentId", returnId);
    }

    public String getPaymentId() {
        return (String) getValue("paymentId");
    }
    public String getPaymentGroupId() {
        return (String) getValue("paymentGroupId");
    }
    public void addInvoiceId(String returnId) {
        controllerOptions.put("invoiceId", returnId);
    }

    public String getInvoiceId() {
        return (String) getValue("invoiceId");
    }

    protected Object getValue(String id) {
        if (controllerOptions.containsKey(id)) {
            return controllerOptions.get(id);
        }
        return null;
    }

    public boolean isReadOnly() {
        if (controllerOptions.containsKey("readOnly")) {
            return (Boolean) controllerOptions.get("readOnly");
        }
        return false;
    }

    public boolean isShowFullProductScreen() {
        if (controllerOptions.containsKey("showFullProductScreen")) {
            return (Boolean) controllerOptions.get("showFullProductScreen");
        }
        return false;
    }
    
    public void setReadOnly(boolean readOnly) {
        controllerOptions.put("readOnly", new Boolean(readOnly));
    }
    
    public void setShowFullProductScreen(boolean readOnly) {
        controllerOptions.put("showFullProductScreen", new Boolean(readOnly));
    }    

    public OrderType getOrderType() {
        return (OrderType) controllerOptions.get("orderTypeId");
    }

    public void addReturnHeaderType(String returnHeaderTypeId) {
        try {
            Debug.logInfo("returnHeaderTypeId: " + returnHeaderTypeId, module);
            ReturnHeaderType roleType = ReturnHeaderTypeSingleton.getReturnHeaderType(returnHeaderTypeId);
            controllerOptions.put("returnHeaderTypeId", roleType);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ReturnHeaderType getReturnHeaderType() {
        return (ReturnHeaderType) controllerOptions.get("returnHeaderTypeId");
    }

    public void addOrderStatusType(String orderStatusItemId) {
        try {
            Debug.logInfo("orderStatusItem: " + orderStatusItemId, module);
            StatusItem statusItem = StatusSingleton.getStatusItem(orderStatusItemId);
            controllerOptions.put("orderStatusItem", statusItem);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StatusItem getOrderStatusType() {
        return (StatusItem) controllerOptions.get("orderStatusItem");
    }

    public boolean isSalesOrder() {
        if (controllerOptions.containsKey("orderTypeId")) {
            if ("SALES_ORDER".equals(getOrderType().getorderTypeId())) {
                return true;
            }
        }
        return true;
    }

    public boolean isPurchaseOrder() {
        if (controllerOptions.containsKey("orderTypeId")) {
            if ("PURCHASE_ORDER".equals(getOrderType().getorderTypeId())) {
                return true;
            }
        }
        return true;
    }

    public boolean isPurchaseReturn() {
        if (controllerOptions.containsKey("returnHeaderTypeId")) {
            if ("VENDOR_RETURN".equals(getReturnHeaderType().getreturnHeaderTypeId())) {
                return true;
            }
        }
        return true;
    }

    public boolean isSalesReturn() {
        if (controllerOptions.containsKey("returnHeaderTypeId")) {
            if ("CUSTOMER_RETURN".equals(getReturnHeaderType().getreturnHeaderTypeId())) {
                return true;
            }
        }
        return true;
    }

    public boolean isPurchaseInvoice() {
        if (controllerOptions.containsKey("invoiceTypeId")) {
            if ("SALES_INVOICE".equals(getInvoiceType().getinvoiceTypeId())) {
                return false;
            }
        }
        return true;
    }

    public boolean isSalesInvoice() {
        if (controllerOptions.containsKey("invoiceTypeId")) {
            if ("PURCHASE_INVOICE".equals(getInvoiceType().getinvoiceTypeId())) {
                return false;
            }
        }
        return true;
    }

    public boolean isDoubleClickCloseDialog() {
        return controllerOptions.containsKey("DoubleClickCloseDialog");
    }

    public void setDoubleClickCloseDialog() {
        /* try {
         throw new Exception("double click");
         } catch (Exception e1) {
         Debug.logError(e1, module);
         }*/
        controllerOptions.put("DoubleClickCloseDialog", true);
    }

    public void addPaymentType(String paymentTypeId) {
        try {
            PaymentType roleType = PaymentTypeSingleton.getPaymentType(paymentTypeId);
            controllerOptions.put("paymentTypeId", roleType);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PaymentType getPaymentType() {
        return (PaymentType) controllerOptions.get("paymentTypeId");
    }

    public void addInvoiceType(String invoiceTypeId) {
        try {
            InvoiceType roleType = InvoiceTypeSingleton.getInvoiceType(invoiceTypeId);
            controllerOptions.put("invoiceTypeId", roleType);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InvoiceType getInvoiceType() {
        return (InvoiceType) controllerOptions.get("invoiceTypeId");
    }

    public Object get(String key) {
        return getValue(key);
    }

    public ControllerOptions put(String key, Object obj) {
        controllerOptions.put(key, obj);
        return this;
    }

    public boolean contains(String key) {
        return controllerOptions.containsKey(key);
    }

    public void debugOutput() {
        for (Map.Entry<String, Object> entryDept : controllerOptions.entrySet()) {
            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
        }
    }

    public ControllerOptions getCopy() {
        return new ControllerOptions(this);
    }
    private SimpleScreenInterface simpleScreenInterface = null;

    public SimpleScreenInterface getSimpleScreenInterface() {
        return (SimpleScreenInterface) controllerOptions.get("simpleScreenInterface");
    }

    public void setSimpleScreenInterface(SimpleScreenInterface simpleScreenInterface) {
        controllerOptions.put("simpleScreenInterface", simpleScreenInterface);
    }

    public void addWidth(BigDecimal width) {
        try {
            Debug.logInfo("width: " + width, module);
            controllerOptions.put("width", width);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BigDecimal getWidth() {
        return (BigDecimal) controllerOptions.get("width");
    }

    public void addHeight(BigDecimal height) {
        try {
            Debug.logInfo("height: " + height, module);
            controllerOptions.put("height", height);
        } catch (Exception ex) {
            Logger.getLogger(ControllerOptions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BigDecimal getHeight() {
        return (BigDecimal) controllerOptions.get("height");
    }

    static public void waitCursorBegin() {
                    Debug.logInfo("waitCursorBegin mainApp begin", module);
        if (mainApp != null) {
            Debug.logInfo("waitCursorBegin mainApp", module);
            mainApp.waitCursorBegin();
        }
    }

    static public void waitCursorEnd() {
                    Debug.logInfo("waitCursorBegin mainApp end", module);
        if (mainApp != null) {
                        Debug.logInfo("waitCursorEnd mainApp", module);
            mainApp.waitCursorEnd();
        }        
    }
}
