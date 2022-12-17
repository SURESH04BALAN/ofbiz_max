/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

/**
 *
 * @author siranjeev
 */
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.GeneralException;
import org.ofbiz.base.util.UtilDateTime;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.entity.BillingAccount;
import org.ofbiz.ordermax.entity.BillingAccountRole;
import org.ofbiz.ordermax.entity.BillingAccountTerm;
import org.ofbiz.ordermax.entity.Payment;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadBillingAccountWorker extends BaseOrderMaxSwingWorker<List<BillingAccount>, BillingAccount> {

    public static final String module = LoadBillingAccountWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<BillingAccount> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    XuiSession session = null;

    public LoadBillingAccountWorker(ListAdapterListModel<BillingAccount> personListModel, String fileName, XuiSession delegator) {
        super(personListModel, delegator);
        this.personListModel = personListModel;
        this.session = delegator;
    }

    static public BillingAccount loadBillingAccount(String billingAccountId, XuiSession session) {
        BillingAccount billingAccount = null;
        try {
            GenericValue billingGen = session.getDelegator().findByPrimaryKey("BillingAccount", UtilMisc.toMap("billingAccountId", billingAccountId));
            if (billingGen != null) {
                billingAccount = new BillingAccount(billingGen);
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return billingAccount;
    }

    static public List<BillingAccount> loadBillingAccounts(Map<String, Object> inputFields, XuiSession session) {
        List<BillingAccount> listInventItemLocation = new ArrayList<BillingAccount>();

        Map<String, Object> result = null;
        List<Map<String, Object>> tempResults = FastList.newInstance();
        boolean beganTransaction = false;
        try {
            beganTransaction = TransactionUtil.begin();

            // = FastMap.newInstance();
            try {
//                inputFields.put("facilityId", "Company");
//        inputFields.put("infoString_ic", caseInsensitiveEmail);
                result = session.getDispatcher().runSync("performFind", UtilMisc.<String, Object>toMap("entityName", "BillingAccount",
                        "inputFields", inputFields, "userLogin", session.getUserLogin()));
                for (Map.Entry<String, Object> entryDept : result.entrySet()) {
                    if (entryDept.getValue() == null) {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + "Null", module);
                    } else {
                        Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                    }
                }
            } catch (GenericServiceException ex) {
                Debug.logError(ex, module);
            }
            EntityListIterator listIt = (EntityListIterator) result.get("listIt");
            if (listIt != null) {
                try {
                    List<GenericValue> list = listIt.getCompleteList();
//                    List<GenericValue> filteredList = EntityUtil.filterByDate(list);
                    for (GenericValue gv : list) {
//                tempResults.addAll(filteredList);
                        listInventItemLocation.add(new BillingAccount(gv));
                    }

                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                } finally {
                    try {
                        listIt.close();
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                    }
                }

            }
        } catch (GenericTransactionException e) {
            Debug.logError(e, module);
            try {
                TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
            } catch (GenericTransactionException e2) {
                Debug.logError(e2, "Unable to rollback transaction", module);
//                pos.showMessageDialog("dialog/error/exception", e2.getMessage());
            }
//            pos.showMessageDialog("dialog/error/exception", e.getMessage());
        } finally {
            try {
                TransactionUtil.commit(beganTransaction);
            } catch (GenericTransactionException e) {
                Debug.logError(e, "Unable to commit transaction", module);
//                pos.showMessageDialog("dialog/error/exception", e.getMessage());
            }
        }

        return listInventItemLocation;

    }

    @Override
    protected List<BillingAccount> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<BillingAccount> persons = new ArrayList<BillingAccount>();
        return persons;
    }
    /*
     static public BillingAccountComposite getPartyBillingAccountComposite(String partyId, XuiSession session) {
     BillingAccount getPartyBillingAccount
     }
     */

    static public BillingAccountComposite getBillingAccountComposite(String billingAccountId, XuiSession session) {
        BillingAccountComposite billingAccountComposite = new BillingAccountComposite();
        BillingAccount billingAccount = LoadBillingAccountWorker.loadBillingAccount(billingAccountId, session);
        if (billingAccount == null) {
            return null;
        }
        billingAccountComposite.setBillingAccount(billingAccount);

        // set the invoice bill_to_customer from the billing account
        try {
            billingAccountComposite.setBillingAccountRoleList(new ArrayList<BillingAccountRole>());
            List<GenericValue> billToRoles = billingAccount.getGenericValueObj().getRelated("BillingAccountRole", null, null);
            for (GenericValue billToRole : billToRoles) {
                billingAccountComposite.getBillingAccountRoleList().add(new BillingAccountRole(billToRole));

            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            billingAccountComposite.setBillingAccountTermList(new ArrayList<BillingAccountTerm>());
            List<GenericValue> billingAccountTerms = billingAccount.getGenericValueObj().getRelated("BillingAccountTerm", null, null);
            for (GenericValue billingAccountTerm : billingAccountTerms) {
                billingAccountComposite.getBillingAccountTermList().add(new BillingAccountTerm(billingAccountTerm));
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return billingAccountComposite;
    }

    static public BillingAccountComposite newBillingAccountComposite() {
        BillingAccountComposite billingAccountComposite = new BillingAccountComposite();
        billingAccountComposite.setBillingAccount(new BillingAccount());
        billingAccountComposite.setBillingAccountRoleList(new ArrayList<BillingAccountRole>());
        return billingAccountComposite;
    }

    static public BillingAccountRole newBillingAccountRole(String partyId, String roleTypeId) {
        BillingAccountRole role = new BillingAccountRole();
        role.setpartyId(partyId);
        role.setroleTypeId(roleTypeId);
        return role;
    }

    static public List<BillingAccountComposite> getPartyBillingAccountComposites(String partyId, XuiSession session) {
        List<BillingAccountComposite> billingAccountComposites = new ArrayList<BillingAccountComposite>();

        try {
            List<BillingAccount> billingAccounts = LoadBillingAccountWorker.getPartyBillingAccounts(partyId, "BILL_TO_CUSTOMER", session.getDelegator());
            for (BillingAccount billingAccount : billingAccounts) {
                BillingAccountComposite billingAccountComposite = new BillingAccountComposite();
                billingAccountComposite.setBillingAccount(billingAccount);
                billingAccountComposites.add(billingAccountComposite);
                billingAccountComposite.setPrimaryPartyId(partyId);
                // set the invoice bill_to_customer from the billing account
                try {
                    billingAccountComposite.setBillingAccountRoleList(new ArrayList<BillingAccountRole>());
                    List<GenericValue> billToRoles = billingAccount.getGenericValueObj().getRelated("BillingAccountRole", null, null);
                    for (GenericValue billToRole : billToRoles) {
                        billingAccountComposite.getBillingAccountRoleList().add(new BillingAccountRole(billToRole));

                    }
                } catch (GenericEntityException ex) {
                    Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    billingAccountComposite.setBillingAccountTermList(new ArrayList<BillingAccountTerm>());
                    List<GenericValue> billingAccountTerms = billingAccount.getGenericValueObj().getRelated("BillingAccountTerm", null, null);
                    for (GenericValue billingAccountTerm : billingAccountTerms) {
                        billingAccountComposite.getBillingAccountTermList().add(new BillingAccountTerm(billingAccountTerm));
                    }
                } catch (GenericEntityException ex) {
                    Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (GeneralException ex) {
            Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return billingAccountComposites;
    }

    public static BillingAccount getPartyBillingAccount(String currencyUomId, String partyId, Delegator delegator) throws GeneralException {

        //List<BillingAccount> billingAccounts = new ArrayList<BillingAccount>();
        EntityCondition barFindCond = EntityCondition.makeCondition(UtilMisc.toList(
                EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId),
                EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, "BILL_TO_CUSTOMER")), EntityOperator.AND);

        List<GenericValue> billingAccountRoleList = delegator.findList("BillingAccountRole", barFindCond, null, null, null, false);

        billingAccountRoleList = EntityUtil.filterByDate(billingAccountRoleList);

        if (billingAccountRoleList.size() > 0) {
            for (GenericValue billingAccountRole : billingAccountRoleList) {
                GenericValue billingAccountVO = billingAccountRole.getRelatedOne("BillingAccount");

                // skip accounts that have thruDate < nowTimestamp
                java.sql.Timestamp thruDate = billingAccountVO.getTimestamp("thruDate");
                if ((thruDate != null) && UtilDateTime.nowTimestamp().after(thruDate)) {
                    continue;
                }

                if (currencyUomId.equals(billingAccountVO.getString("accountCurrencyUomId"))) {
                    return new BillingAccount(billingAccountVO);

                }
            }
        }

        return null;
    }

    public static List<BillingAccount> getPartyBillingAccounts(String partyId, String roleTypeId, Delegator delegator) throws GeneralException {

        List<BillingAccount> billingAccounts = new ArrayList<BillingAccount>();

        EntityCondition barFindCond = EntityCondition.makeCondition(UtilMisc.toList(
                EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId),
                EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, roleTypeId)), EntityOperator.AND);

        List<GenericValue> billingAccountRoleList = delegator.findList("BillingAccountRole", barFindCond, null, null, null, false);

        billingAccountRoleList = EntityUtil.filterByDate(billingAccountRoleList);

        if (billingAccountRoleList.size() > 0) {
            for (GenericValue billingAccountRole : billingAccountRoleList) {
                GenericValue billingAccountVO = billingAccountRole.getRelatedOne("BillingAccount");

                // skip accounts that have thruDate < nowTimestamp
                java.sql.Timestamp thruDate = billingAccountVO.getTimestamp("thruDate");
                if ((thruDate != null) && UtilDateTime.nowTimestamp().after(thruDate)) {
                    continue;
                }

                BillingAccount billAccount = new BillingAccount(billingAccountVO);
                billingAccounts.add(billAccount);
            }
        }

        return billingAccounts;
    }

    public static List<BillingAccount> getPartyBillingAccountList(String currencyUomId, String partyId, Delegator delegator) throws GeneralException {
        return getPartyBillingAccountList(currencyUomId, partyId, "BILL_TO_CUSTOMER", delegator);
    }

    public static List<BillingAccount> getPartyBillingAccountList(String currencyUomId, String partyId, String roleTypeId, Delegator delegator) throws GeneralException {

        Debug.logInfo("currencyUomId: " + currencyUomId, module);
        Debug.logInfo("partyId: " + partyId, module);
        List<BillingAccount> billingList = FastList.newInstance();
        EntityCondition barFindCond = EntityCondition.makeCondition(UtilMisc.toList(
                EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, partyId),
                EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, roleTypeId)), EntityOperator.AND);

        List<GenericValue> billingAccountRoleList = delegator.findList("BillingAccountRole", barFindCond, null, null, null, false);

        billingAccountRoleList = EntityUtil.filterByDate(billingAccountRoleList);

        if (billingAccountRoleList.size() > 0) {
            for (GenericValue billingAccountRole : billingAccountRoleList) {
                GenericValue billingAccountVO = billingAccountRole.getRelatedOne("BillingAccount");

                // skip accounts that have thruDate < nowTimestamp
                java.sql.Timestamp thruDate = billingAccountVO.getTimestamp("thruDate");
                if ((thruDate != null) && UtilDateTime.nowTimestamp().after(thruDate)) {
                    continue;
                }

                if (currencyUomId.equals(billingAccountVO.getString("accountCurrencyUomId"))) {
                    BillingAccount billAccount = new BillingAccount(billingAccountVO);
                    billingList.add(billAccount);
                }
            }

        }
        return billingList;
    }

    public static void saveBillingAccountRoles(BillingAccountComposite billingAccountComposite, XuiSession session) {
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        String billingAccountId = billingAccountComposite.getBillingAccount().getbillingAccountId();

        if (UtilValidate.isNotEmpty(billingAccountId)) {

            for (BillingAccountRole billingAccountRole : billingAccountComposite.getBillingAccountRoleList()) {
                Debug.logInfo("billingAccountRole.getroleTypeId() : " + billingAccountRole.getroleTypeId()
                        + " partyId: " + billingAccountRole.getpartyId() + " billingAccountId:: " + billingAccountId, module);
                try {
                    List<EntityExpr> contactList = UtilMisc.toList(
                            EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, billingAccountRole.getroleTypeId()),
                            EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, billingAccountRole.getpartyId()),
                            EntityCondition.makeCondition("billingAccountId", EntityOperator.EQUALS, billingAccountId)
                    );

                    List<GenericValue> machList = delegator.findList("BillingAccountRole", EntityCondition.makeCondition(contactList, EntityOperator.AND), null, null, null, false);

                    if (UtilValidate.isEmpty(machList)) {
                        try {
                            // create a new party contact mech for the partyIdTo
                            resultMap = dispatcher.runSync("createBillingAccountRole",
                                    UtilMisc.<String, Object>toMap(
                                            "partyId", billingAccountRole.getpartyId(),
                                            "userLogin", userLogin,
                                            "roleTypeId", billingAccountRole.getroleTypeId(),
                                            "billingAccountId", billingAccountId,
                                            "fromDate", billingAccountRole.getfromDate()
                                    ));

                            if (OrderMaxUtility.handleServiceReturn("create Billing Account Role", resultMap) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                                return;
                            }
                        } catch (GenericServiceException ex) {
                            Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        try {
                            // create a new party contact mech for the partyIdTo
                            resultMap = dispatcher.runSync("updateBillingAccountRole",
                                    UtilMisc.<String, Object>toMap(
                                            "partyId", billingAccountRole.getpartyId(),
                                            "userLogin", userLogin,
                                            "roleTypeId", billingAccountRole.getroleTypeId(),
                                            "billingAccountId", billingAccountId,
                                            "fromDate", billingAccountRole.getfromDate(),
                                            "thruDate", billingAccountRole.getthruDate()
                                    ));

                            if (OrderMaxUtility.handleServiceReturn("create Billing Account Role", resultMap) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                                return;
                            }
                        } catch (GenericServiceException ex) {
                            Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (GenericEntityException ex) {
                    Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void saveBillingAccountRole(BillingAccountRole billingAccountRole, XuiSession session) {
        try {
            GenericValue userLogin = session.getUserLogin();
            LocalDispatcher dispatcher = session.getDispatcher();
            Map<String, Object> resultMap = ServiceUtil.returnSuccess();
            Delegator delegator = session.getDelegator();
            Locale locale = Locale.getDefault();

            List<EntityExpr> contactList = UtilMisc.toList(
                    EntityCondition.makeCondition("roleTypeId", EntityOperator.EQUALS, billingAccountRole.getroleTypeId()),
                    EntityCondition.makeCondition("partyId", EntityOperator.EQUALS, billingAccountRole.getpartyId()),
                    EntityCondition.makeCondition("billingAccountId", EntityOperator.EQUALS, billingAccountRole.getbillingAccountId())
            );

            List<GenericValue> machList = delegator.findList("BillingAccountRole", EntityCondition.makeCondition(contactList, EntityOperator.AND), null, null, null, false);

            if (UtilValidate.isEmpty(machList)) {
                try {
                    // create a new party contact mech for the partyIdTo
                    resultMap = dispatcher.runSync("createBillingAccountRole",
                            UtilMisc.<String, Object>toMap(
                                    "partyId", billingAccountRole.getpartyId(),
                                    "userLogin", userLogin,
                                    "roleTypeId", billingAccountRole.getroleTypeId(),
                                    "billingAccountId", billingAccountRole.getbillingAccountId(),
                                    "fromDate", billingAccountRole.getfromDate()
                            ));

                    if (OrderMaxUtility.handleServiceReturn("create Billing Account Role", resultMap) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                        return;
                    }
                } catch (GenericServiceException ex) {
                    Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                try {
                    // create a new party contact mech for the partyIdTo
                    if (billingAccountRole.getthruDate() != null) {
                        resultMap = dispatcher.runSync("updateBillingAccountRole",
                                UtilMisc.<String, Object>toMap(
                                        "partyId", billingAccountRole.getpartyId(),
                                        "userLogin", userLogin,
                                        "roleTypeId", billingAccountRole.getroleTypeId(),
                                        "billingAccountId", billingAccountRole.getbillingAccountId(),
                                        "fromDate", billingAccountRole.getfromDate(),
                                        "thruDate", billingAccountRole.getthruDate()
                                ));

                        if (OrderMaxUtility.handleServiceReturn("create Billing Account Role", resultMap) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                            return;
                        }
                    }
                } catch (GenericServiceException ex) {
                    Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (GenericEntityException ex) {
            Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveBillingAccount(BillingAccountComposite billingAccountComposite, XuiSession session) {
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();
        String billingAccountId = billingAccountComposite.getBillingAccount().getbillingAccountId();

        GenericValue val = null;

        if (UtilValidate.isNotEmpty(billingAccountId)) {
            try {
                val = session.getDelegator().findByPrimaryKey("BillingAccount", UtilMisc.toMap("billingAccountId", billingAccountId));
            } catch (GenericEntityException ex) {
                Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (val == null) {

            try {
                // create a new party contact mech for the partyIdTo
                resultMap = dispatcher.runSync("createBillingAccount",
                        UtilMisc.<String, Object>toMap(
                                //        "billingAccountId",billingAccountComposite.getBillingAccount().getbillingAccountId()
                                "accountLimit", billingAccountComposite.getBillingAccount().getaccountLimit(),
                                "accountCurrencyUomId", billingAccountComposite.getBillingAccount().getaccountCurrencyUomId(),
                                "contactMechId", billingAccountComposite.getBillingAccount().getcontactMechId(),
                                "fromDate", billingAccountComposite.getBillingAccount().getfromDate(),
                                "thruDate", billingAccountComposite.getBillingAccount().getthruDate(),
                                "description", billingAccountComposite.getBillingAccount().getdescription(),
                                "externalAccountId", billingAccountComposite.getBillingAccount().getexternalAccountId(),
                                "userLogin", userLogin
                        ));

                if (OrderMaxUtility.handleServiceReturn("create Billing Account Role", resultMap) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    billingAccountId = (String) resultMap.get("billingAccountId");
                    billingAccountComposite.getBillingAccount().setbillingAccountId(billingAccountId);
                }
            } catch (GenericServiceException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (BillingAccountRole role : billingAccountComposite.getBillingAccountRoleList()) {
                role.setbillingAccountId(billingAccountId);
                LoadBillingAccountWorker.saveBillingAccountRole(role, ControllerOptions.getSession());
            }
        } else {
            try {
                // create a new party contact mech for the partyIdTo
                resultMap = dispatcher.runSync("updateBillingAccount",
                        UtilMisc.<String, Object>toMap(
                                "billingAccountId", billingAccountComposite.getBillingAccount().getbillingAccountId(),
                                "accountLimit", billingAccountComposite.getBillingAccount().getaccountLimit(),
                                "accountCurrencyUomId", billingAccountComposite.getBillingAccount().getaccountCurrencyUomId(),
                                "contactMechId", billingAccountComposite.getBillingAccount().getcontactMechId(),
                                "fromDate", billingAccountComposite.getBillingAccount().getfromDate(),
                                "thruDate", billingAccountComposite.getBillingAccount().getthruDate(),
                                "description", billingAccountComposite.getBillingAccount().getdescription(),
                                "externalAccountId", billingAccountComposite.getBillingAccount().getexternalAccountId(),
                                "userLogin", userLogin
                        ));

                if (OrderMaxUtility.handleServiceReturn("create Billing Account Role", resultMap) == OrderMaxUtility.ServiceReturnStatus.ERROR) {
                    return;
                }
            } catch (GenericServiceException ex) {
                Logger.getLogger(LoadAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LoadBillingAccountWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static public void saveBillingAccountPayment(PaymentComposite paymentComposite, XuiSession session) throws Exception {

        try {

            Map<String, Object> paymentParams
                    = UtilMisc.<String, Object>toMap("paymentTypeId", paymentComposite.getPayment().getpaymentTypeId(),//"CUSTOMER_PAYMENT", 
                            "paymentMethodTypeId", paymentComposite.getPayment().getpaymentMethodTypeId(),//"EXT_BILLACT",
                            "partyIdFrom", paymentComposite.getPayment().getpartyIdFrom(),
                            "partyIdTo", paymentComposite.getPayment().getpartyIdTo(),
                            "statusId", paymentComposite.getPayment().getstatusId(),//"PMNT_RECEIVED", 
                            "effectiveDate", paymentComposite.getPayment().geteffectiveDate());

            paymentParams.put("amount", paymentComposite.getPayment().getamount());
            paymentParams.put("currencyUomId", paymentComposite.getPayment().getcurrencyUomId());
            paymentParams.put("paymentRefNum", paymentComposite.getPayment().getpaymentRefNum());
            paymentParams.put("comments", paymentComposite.getPayment().getcomments());
            if (paymentComposite.getOrderPaymentPreference() != null) {
                paymentParams.put("paymentPreferenceId", paymentComposite.getOrderPaymentPreference().getorderPaymentPreferenceId());
            }
            Debug.logInfo("amount : " + paymentComposite.getPayment().getamount() + " paymentPreferenceId: " + paymentParams.get("paymentPreferenceId"), module);
            Map<String, Object> tmpResult = null;
            paymentParams.put("userLogin", session.getUserLogin());
            String paymentId = null;

            if (UtilValidate.isEmpty(paymentComposite.getPayment().getpaymentId())) {
                tmpResult = session.getDispatcher().runSync("createPayment", paymentParams);
                if (OrderMaxUtility.handleServiceReturn("Save new Payment", tmpResult) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    paymentId = (String) tmpResult.get("paymentId");

                    GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
                    paymentComposite.setPayment(new Payment(genValue));

                }

            } else {
                paymentParams.put("paymentId", paymentComposite.getPayment().getpaymentId());
                tmpResult = session.getDispatcher().runSync("updatePayment", paymentParams);

                if (OrderMaxUtility.handleServiceReturn("Update Payment", tmpResult) == OrderMaxUtility.ServiceReturnStatus.SUCCESS) {
                    paymentId = (String) tmpResult.get("paymentId");

                    GenericValue genValue = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
                    paymentComposite.setPayment(new Payment(genValue));

                }
            }

            /*
             if (ServiceUtil.isError(tmpResult)) {
             Debug.logError("createPayment service erroe", module);
             } else {
             //                String paymentId = (String) tmpResult.get("paymentId");
             paymentComposite.getPayment().setpaymentId(paymentId);
             for (PaymentApplication pa : paymentComposite.getPaymentApplicationCompositeList().getList()) {
             pa.setpaymentId(paymentId);
             savePaymentApplication(pa, session);
             }
             }
             */
            /*            
             tmpResult = dispatcher.runSync("createPaymentApplication", UtilMisc.<String, Object>toMap("paymentId", paymentId, "invoiceId", invoiceId, "billingAccountId", billingAccountId,
             "amountApplied", captureAmount, "userLogin", userLogin));
             if (ServiceUtil.isError(tmpResult)) {
             return tmpResult;
             }
             if (paymentId == null) {
             return ServiceUtil.returnError(UtilProperties.getMessage(resource, 
             "AccountingNoPaymentCreatedForInvoice", 
             UtilMisc.toMap("invoiceId", invoiceId, "billingAccountId", billingAccountId), locale));
             }
             results.put("paymentId", paymentId);
            
             GenericValue val = session.getDelegator().findByPrimaryKey("Payment", UtilMisc.toMap("paymentId", paymentId));
             if (val != null) {
             paymentComposite = new PaymentComposite();
             Payment payment = new Payment(val);
             paymentComposite.setPayment(payment);
             paymentComposite.setPartyPaymentFrom(PartyListSingleton.getAccount(payment.getpartyIdFrom()));
             paymentComposite.setPartyPaymentTo(PartyListSingleton.getAccount(payment.getpartyIdTo()));
             paymentComposite.setPaymentApplicationCompositeList(loadPaymentApplications(paymentId, session));
             Debug.logInfo("paymentId: " + val.getString("paymentId"), module);
             }
             */
        } catch (Exception ex) {
            Debug.logError(ex, module);
        }
    }
}
