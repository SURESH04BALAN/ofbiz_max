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
package org.ofbiz.guiapp.xui;

import com.openbravo.pos.forms.AppConfig;
import java.awt.Point;
import java.util.Locale;
import java.util.Map;
import javax.swing.JDesktopPane;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.webapp.control.LoginWorker;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
//import org.ofbiz.ordermax.entity.Facility;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductStore;
import org.ofbiz.ordermax.entity.ProductStoreCatalog;
import org.ofbiz.ordermax.entity.ProductStoreFacility;
import org.ofbiz.ordermax.inventory.FacilitySingleton;
import org.ofbiz.ordermax.inventory.InventoryItemTypeSingleton;
import org.ofbiz.ordermax.inventory.RejectionReasonSingleton;
import org.ofbiz.ordermax.invoice.InvoiceTypeSingleton;
import org.ofbiz.ordermax.orderbase.OrderTypeSingleton;
import org.ofbiz.ordermax.orderbase.ShipmentMethodTypeSingleton;
import org.ofbiz.ordermax.orderbase.TermTypeSingleton;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.orderbase.returns.ReturnHeaderTypeSingleton;
import org.ofbiz.ordermax.orderbase.returns.ReturnReasonSingleton;
import org.ofbiz.ordermax.orderbase.returns.ReturnTypeSingleton;
import org.ofbiz.ordermax.party.ContactMechPurposeTypeSingleton;
import org.ofbiz.ordermax.party.ContactMechTypePurposeSingleton;
import org.ofbiz.ordermax.party.GeoCountryAssociationSingleton;
import org.ofbiz.ordermax.party.GeoCountrySingleton;
import org.ofbiz.ordermax.party.GeoSingleton;
import org.ofbiz.ordermax.party.UserLoginSingleton;
import org.ofbiz.ordermax.payment.FinAccountSingleton;
import org.ofbiz.ordermax.payment.PaymentMethodTypeSingleton;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.payment.UOMSingleton;
import org.ofbiz.ordermax.product.GoodIdentificationTypeSingleton;
import org.ofbiz.ordermax.product.PartyTypeSingleton;
import org.ofbiz.ordermax.product.ProductPricePurposeSingleton;
import org.ofbiz.ordermax.product.ProductPriceTypeSingleton;
import org.ofbiz.ordermax.product.productloader.ProductTreeArraySingleton;
import org.ofbiz.ordermax.product.ProductTypeSingleton;
import org.ofbiz.ordermax.product.SupplierPrefOrderSingleton;
import org.ofbiz.ordermax.product.UOMTypeSingleton;
import org.ofbiz.ordermax.product.UomCurrencySingleton;
import org.ofbiz.ordermax.product.UomDataMeasureSingleton;
import org.ofbiz.ordermax.product.UomDataSpeedSingleton;
import org.ofbiz.ordermax.product.UomEnergySingleton;
import org.ofbiz.ordermax.product.UomQuantitySingleton;
import org.ofbiz.ordermax.product.UomTemperatureSingleton;
import org.ofbiz.ordermax.product.UomTimeSingleton;
import org.ofbiz.ordermax.product.UomVolumeDrySingleton;
import org.ofbiz.ordermax.product.UomVolumeLiquidSingleton;
import org.ofbiz.ordermax.product.catalog.ProdCatalogSingleton;
import org.ofbiz.ordermax.product.catalog.ProductCategorySingleton;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

public class XuiSession {

    public static final String module = XuiSession.class.getName();

    protected Delegator delegator = null;
    protected LocalDispatcher dispatcher = null;
    protected GenericValue userLogin = null;
    protected XuiContainer container = null;
    protected ClassLoader classLoader = null;
    protected Map<String, Object> attributes = FastMap.newInstance();
    protected String id = null;
    protected final boolean IS_SAME_LOGIN = UtilProperties.propertyValueEqualsIgnoreCase("xui.properties", "isSameLogin", "true");
    private Locale locale = Locale.getDefault();
//    protected Facility facility = null;
    protected ProductStoreCatalog productStoreCatalog = null;
    protected ProductStoreFacility productStoreFacility = null;
    protected ProductStore productStore = null;
    protected ProdCatalog prodCatalog = null;
    protected ProductCategory productCategory = null;
    
    private javax.swing.JDesktopPane desktopPane = null;
    private AppConfig appConfig = null;
    static  private boolean canLogOut = false;
    static private boolean canGiftCardPayment = true;
    static  private boolean canExpensePayment = true;
    static private boolean canChequePayment = true;
    static private DisplayNameInterface.DisplayTypes comboBoxDisplayFormat = DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY;
    static private ControllerOptions controllerOptions = null;
    
    public static ControllerOptions getControllerOptions() {
        if(controllerOptions == null){
            controllerOptions = new ControllerOptions();
        }
        return controllerOptions.getCopy();
    }
    
    public static DisplayNameInterface.DisplayTypes getComboBoxDisplayFormat() {
        return comboBoxDisplayFormat;
    }

    public static void setComboBoxDisplayFormat(DisplayNameInterface.DisplayTypes comboBoxDisplayFormat) {
        XuiSession.comboBoxDisplayFormat = comboBoxDisplayFormat;
    }
    
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    public void setDesktopPane(JDesktopPane desktopPane) {
        this.desktopPane = desktopPane;
    }
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProdCatalog getProdCatalog() {
        return prodCatalog;
    }

    public void setProdCatalog(ProdCatalog prodCatalog) {
        this.prodCatalog = prodCatalog;
    }

    public XuiSession(String id, Delegator delegator, LocalDispatcher dispatcher, XuiContainer container) {
        this.id = id;
        this.delegator = delegator;
        this.dispatcher = dispatcher;
        this.container = container;
        this.classLoader = Thread.currentThread().getContextClassLoader();
        Debug.logInfo("Created XuiSession [" + id + "]", module);
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public XuiContainer getContainer() {
        return this.container;
    }

    public Delegator getDelegator() {
        return this.delegator;
    }

    public LocalDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public GenericValue getUserLogin() {
        return this.userLogin;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        if (this.userLogin == null) {
            return null;
        } else {
            return this.userLogin.getString("userLoginId");
        }
    }

    public String getUserPartyId() {
        if (this.userLogin == null) {
            return null;
        } else {
            return this.userLogin.getString("partyId");
        }
    }

    public void logout() {
        if (this.userLogin != null) {
            LoginWorker.setLoggedOut(this.userLogin.getString("userLoginId"), this.getDelegator());
            this.userLogin = null;
        }
    }

    public void login(String username, String password) throws UserLoginFailure {
        // if already logged in; verify for lock. Depends on SAME_LOGIN, false by default
        if (this.userLogin != null) {
            if (IS_SAME_LOGIN == true && !userLogin.getString("userLoginId").equals(username)) {
                throw new UserLoginFailure(UtilProperties.getMessage("XuiUiLabels", "XuiUsernameDoesNotMatchLoggedUser", locale));
            }
        }
        this.userLogin = this.checkLogin(username, password);
        
        if(this.userLogin != null){
            setSession();
        }
    }

    public GenericValue checkLogin(String username, String password) throws UserLoginFailure {
        // check the required parameters and objects
        if (dispatcher == null) {
            throw new UserLoginFailure(UtilProperties.getMessage("XuiUiLabels", "XuiUnableToLogIn", locale));
        }
        if (UtilValidate.isEmpty(username)) {
            throw new UserLoginFailure(UtilProperties.getMessage("PartyUiLabels", "PartyUserNameMissing", locale));
        }
        if (UtilValidate.isEmpty(password)) {
            throw new UserLoginFailure(UtilProperties.getMessage("PartyUiLabels", "PartyPasswordMissing", locale));
        }

        // call the login service
        Map<String, Object> result = null;
        try {
            Debug.logInfo("username: " + username + "password: " + password, module);
            result = dispatcher.runSync("userLogin", UtilMisc.toMap("login.username", username, "login.password", password));
        } catch (GenericServiceException e) {
            Debug.logError(e, module);
            throw new UserLoginFailure(e);
        } catch (Throwable t) {
            Debug.logError(t, "Throwable caught!", module);
        }

        // check for errors
        if (ServiceUtil.isError(result)) {
            throw new UserLoginFailure(ServiceUtil.getErrorMessage(result));
        } else {
            GenericValue ul = (GenericValue) result.get("userLogin");
            if (ul == null) {
                throw new UserLoginFailure(UtilProperties.getMessage("XuiUiLabels", "XuiUserLoginNotValid", locale));
            }
            return ul;
        }
    }

    public void setSession() {
        ProductTreeArraySingleton.setSingletonSesion(ControllerOptions.getSession());
/*        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                        // Here, we can safely update the GUI
                // because we'll be called from the
                // event dispatch thread
                ProductTreeArraySingleton.getInstance().loadList();
            }
        });
*/
        //initialized party singleton
//        PartyListSingleton.setSingletonSesion(ControllerOptions.getSession());
        //ProductSingleton.setSingletonSession(ControllerOptions.getSession());
        StatusSingleton.setSingletonSesion(ControllerOptions.getSession());
        TermTypeSingleton.setSingletonSesion(ControllerOptions.getSession());
        ShipmentMethodTypeSingleton.setSingletonSesion(ControllerOptions.getSession());
        InventoryItemTypeSingleton.setSingletonSesion(ControllerOptions.getSession());
        RejectionReasonSingleton.setSingletonSesion(ControllerOptions.getSession());
        OrderTypeSingleton.setSingletonSesion(ControllerOptions.getSession());        
        InvoiceTypeSingleton.setSingletonSesion(ControllerOptions.getSession());
        PaymentTypeSingleton.setSingletonSesion(ControllerOptions.getSession());
        PaymentMethodTypeSingleton.setSingletonSesion(ControllerOptions.getSession());
        RoleTypeSingleton.setSingletonSesion(ControllerOptions.getSession());
        UOMSingleton.setSingletonSesion(ControllerOptions.getSession());
        FinAccountSingleton.setSingletonSesion(ControllerOptions.getSession());
        org.ofbiz.ordermax.party.GeoStateSingleton.setSingletonSesion(ControllerOptions.getSession());
        GeoCountryAssociationSingleton.setSingletonSession(ControllerOptions.getSession());
        GeoCountrySingleton.setSingletonSession(ControllerOptions.getSession());
        GeoSingleton.setSingletonSession(ControllerOptions.getSession());
        ContactMechTypePurposeSingleton.setSingletonSession(ControllerOptions.getSession());
        ContactMechPurposeTypeSingleton.setSingletonSession(ControllerOptions.getSession());
        ProductTypeSingleton.setSingletonSession(ControllerOptions.getSession());
        UOMTypeSingleton.setSingletonSession(ControllerOptions.getSession());

        UomCurrencySingleton.setSingletonSession(ControllerOptions.getSession());
        UomDataMeasureSingleton.setSingletonSession(ControllerOptions.getSession());
        UomDataSpeedSingleton.setSingletonSession(ControllerOptions.getSession());
        UomEnergySingleton.setSingletonSession(ControllerOptions.getSession());
        UomTemperatureSingleton.setSingletonSession(ControllerOptions.getSession());
        UomTimeSingleton.setSingletonSession(ControllerOptions.getSession());
        UomVolumeDrySingleton.setSingletonSession(ControllerOptions.getSession());
        UomVolumeLiquidSingleton.setSingletonSession(ControllerOptions.getSession());
        UomQuantitySingleton.setSingletonSession(ControllerOptions.getSession());
        GoodIdentificationTypeSingleton.setSingletonSession(ControllerOptions.getSession());
        SupplierPrefOrderSingleton.setSingletonSession(ControllerOptions.getSession());
        ProductPriceTypeSingleton.setSingletonSession(ControllerOptions.getSession());
        ProductPricePurposeSingleton.setSingletonSession(ControllerOptions.getSession());
        ProdCatalogSingleton.setSingletonSession(ControllerOptions.getSession());
        ProductCategorySingleton.setSingletonSession(ControllerOptions.getSession());
        FacilitySingleton.setSingletonSession(ControllerOptions.getSession());
        PartyTypeSingleton.setSingletonSession(ControllerOptions.getSession());
        UserLoginSingleton.setSingletonSession(ControllerOptions.getSession());
        ReturnHeaderTypeSingleton.setSingletonSession(ControllerOptions.getSession());
        ReturnReasonSingleton.setSingletonSession(ControllerOptions.getSession());
        ReturnTypeSingleton.setSingletonSession(ControllerOptions.getSession());

    }

    public boolean hasRole(GenericValue userLogin, String roleTypeId) {
        if (userLogin == null || roleTypeId == null) {
            return false;
        }
        String partyId = userLogin.getString("partyId");
        GenericValue partyRole = null;
        try {
            partyRole = delegator.findOne("PartyRole", false, "partyId", partyId, "roleTypeId", roleTypeId);
        } catch (GenericEntityException e) {
            Debug.logError(e, module);
            return false;
        }

        if (partyRole == null) {
            return false;
        }

        return true;
    }
    protected Point p1 = null;
    protected Point p2 = null;

    public Point getScreenP1() {
        return p1;
    }

    public void setScreenP1(Point p1) {
        this.p1 = p1;
    }

    public Point getScreenP2() {
        return p2;
    }

    public void setScreenP2(Point p2) {
        this.p2 = p2;
    }

    /*public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
*/
    public ProductStoreCatalog getProductStoreCatalog() {
        return productStoreCatalog;
    }

    static String companyPartyId = null;

    static public String getCompanyPartyId() {
        return companyPartyId;
    }

    public void setCompanyPartyId(String partyId) {
        companyPartyId = partyId;
    }

    public void setProductStoreCatalog(ProductStoreCatalog productStoreCatalog) {
        this.productStoreCatalog = productStoreCatalog;
    }

    /*public ProductStoreFacility getProductStoreFacility() {
        return productStoreFacility;
    }

    public void setProductStoreFacility(ProductStoreFacility productStoreFacility) {
        this.productStoreFacility = productStoreFacility;
    }*/

    public ProductStore getProductStore() {
        return productStore;
    }

    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }
    
    public String getProductStoreGroupId() {
        return  this.productStoreGroupId;
    }
    
    private String productStoreGroupId = "_NA_";
    public void setProductStoreGroupId(String productStoreGroupId) {
        this.productStoreGroupId = productStoreGroupId;
    }    

    public boolean isCanLogOut() {
        return canLogOut;
    }

    public void setCanLogOut(boolean canLogOut) {
        this.canLogOut = canLogOut;
    }

    public boolean isCanGiftCardPayment() {
        return canGiftCardPayment;
    }

    public void setCanGiftCardPayment(boolean canGiftCardPayment) {
        this.canGiftCardPayment = canGiftCardPayment;
    }

    public boolean isCanExpensePayment() {
        return canExpensePayment;
    }

    public void setCanExpensePayment(boolean canExpensePayment) {
        this.canExpensePayment = canExpensePayment;
    }

    public boolean isCanChequePayment() {
        return canChequePayment;
    }

    public void setCanChequePayment(boolean canChequePayment) {
        this.canChequePayment = canChequePayment;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @SuppressWarnings("serial")
    public class UserLoginFailure extends GeneralException {

        public UserLoginFailure() {
            super();
        }

        public UserLoginFailure(String str) {
            super(str);
        }

        public UserLoginFailure(String str, Throwable nested) {
            super(str, nested);
        }

        public UserLoginFailure(Throwable nested) {
            super(nested);
        }
    }
}
