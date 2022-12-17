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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.composite.PartyRoleComposite;
import org.ofbiz.ordermax.composite.PartyRolesList;
import org.ofbiz.ordermax.entity.BillingAccountRole;
import org.ofbiz.ordermax.entity.PartyIdentification;
import org.ofbiz.ordermax.entity.PartyRole;

/**
 *
 * @author siranjeev
 */
public class LoadCustomerWorker extends SwingWorker<List<Account>, Account> {

    public static final String module = LoadCustomerWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<Account> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private String csvFile;
    XuiSession session = null;

    public LoadCustomerWorker(ListAdapterListModel<Account> personListModel, String fileName, XuiSession delegator) {
        this.personListModel = personListModel;
        csvFile = fileName;
        this.session = delegator;
    }

    @Override
    protected List<Account> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<Account> persons = new ArrayList<Account>();

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String fieldId[] = {
            "PARTY_ID",
            "TO_NAME",
            "ATTN_NAME",
            "ADDRESS1",
            "ADDRESS2",
            "DIRECTIONS",
            "CITY",
            "POSTAL_CODE",
            "POSTAL_CODE_EXT",
            "COUNTRY_GEO_ID",
            "STATE_PROVINCE_GEO_ID",
            "COUNTY_GEO_ID",
            "POSTAL_CODE_GEO_ID",
            "GEO_POINT_ID",
            "COUNTRY_CODE",
            "AREA_CODE",
            "TELE_PHONE",
            "FAX_AREA_CODE",
            "FAX_TELE_PHONE",
            "FAX_NUMBER",
            "ABN",
            "CATEGORY",
            "TERMS",
            "INACTIVE",
            "ISPERSON",
            "CURRENCYUOM",
            "DEL_PARTY_ID",
            "DEL_TO_NAME",
            "DEL_ATTN_NAME",
            "DEL_ADDRESS1",
            "DEL_ADDRESS2",
            "DEL_DIRECTIONS",
            "DEL_CITY",
            "DEL_POSTAL_CODE",
            "DEL_POSTAL_CODE_EXT",
            "DEL_COUNTRY_GEO_ID",
            "DEL_STATE_PROVINCE_GEO_ID",
            "DEL_COUNTY_GEO_ID",
            "DEL_POSTAL_CODE_GEO_ID",
            "DEL_GEO_POINT_ID",
            "DEL_COUNTRY_CODE",
            "DEL_AREA_CODE",
            "DEL_TELE_PHONE",
            "DEL_FAX_NUMBER",
            "BILLING_ACCOUNT_LIMIT",
            "BILLING_ACCOUNT_BALANCE"};

        try {

            List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                Map<String, String> maps = new HashMap<String, String>();

                // use comma as separator
                String[] details = line.split(cvsSplitBy);
                for (int i = 0; i < fieldId.length && i < details.length; ++i) {

                    maps.put(fieldId[i], details[i]);

                }
                listMap.add(maps);
            }

            personListModel.clear();
            maxProgress = listMap.size();

            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            //loop map
            for (Map<String, String> map : listMap) {

                for (Map.Entry<String, String> entry : map.entrySet()) {

                    System.out.println("[ Key= " + entry.getKey() + " , Value="
                            + entry.getValue() + "]");
                }
                String partyId = map.get("PARTY_ID");
                String billingAmount = map.get("BILLING_ACCOUNT_LIMIT");
                BigDecimal billAmount = new BigDecimal(10000);
                try {
                    if (UtilValidate.isNotEmpty(billingAmount)) {
                        billAmount = new BigDecimal(billingAmount);
                    }
                } catch (Exception e) {
                    Debug.logError(line, module);
                }
                String abn = map.get("ABN");
                Account account = null;

                try {
                    account = LoadAccountWorker.getAccount(partyId, session);
                    List<BillingAccountComposite> billingAccounts = LoadBillingAccountWorker.getPartyBillingAccountComposites(partyId, XuiContainer.getSession());
                    account.setBillingAccountComposite(billingAccounts);

                } catch (Exception ex) {
                    //    Debug.logError(ex, module);
                }

                if (account == null) {
                    try {
                        account = LoadAccountWorker.createAccount(map, session);

                        Debug.logInfo("account.getParty().getpartyId(): " + account.getParty().getpartyId(), module);
                        createPartyRoles(account);
                        createBillingAccount(account, billAmount);

                        LoadAccountWorker.saveAccount(account, true, session);

                        for (BillingAccountComposite billingAccountComposite : account.getBillingAccountComposite()) {
                            LoadBillingAccountWorker.saveBillingAccount(billingAccountComposite, ControllerOptions.getSession());
                            for (BillingAccountRole bar : billingAccountComposite.getBillingAccountRoleList()) {
                                LoadBillingAccountWorker.saveBillingAccountRole(bar, ControllerOptions.getSession());
                            }
                        }
                        if (UtilValidate.isNotEmpty(abn)) {
                            PartyIdentification partyIdentification = new PartyIdentification();
                            account.setPartyIdentification(partyIdentification);
                            partyIdentification.setIdValue(abn);
                            partyIdentification.setPartyId(account.getParty().getpartyId());
                            partyIdentification.setPartyIdentificationTypeId("AUS_ABN");
                            //update party identification
                            if (account.getPartyIdentification() != null) {
                                account.getPartyIdentification().setPartyId(partyId);
                                LoadAccountWorker.createOrUpdatePartyIdentification(account.getPartyIdentification(), session);
                            }
                        }

                        persons.add(account);
                        publish(account);
                        sleepAWhile();

                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                } else {
                    createPartyRoles(account);
                    createBillingAccount(account, billAmount);

                    LoadAccountWorker.createPartyRoles(account, XuiContainer.getSession());
                    for (BillingAccountComposite billingAccountComposite : account.getBillingAccountComposite()) {
                        LoadBillingAccountWorker.saveBillingAccount(billingAccountComposite, ControllerOptions.getSession());
                        for (BillingAccountRole bar : billingAccountComposite.getBillingAccountRoleList()) {
                            LoadBillingAccountWorker.saveBillingAccountRole(bar, ControllerOptions.getSession());
                        }
                    }

                    if (UtilValidate.isNotEmpty(abn) && account.getPartyIdentification() == null) {
                        PartyIdentification partyIdentification = new PartyIdentification();
                        account.setPartyIdentification(partyIdentification);
                        partyIdentification.setIdValue(abn);
                        partyIdentification.setPartyId(account.getParty().getpartyId());
                        partyIdentification.setPartyIdentificationTypeId("AUS_ABN");

                        mvc.controller.LoadAccountWorker.createOrUpdatePartyIdentification(account.getPartyIdentification(), session);
                    }
                    // LoadAccountWorker.saveAccount(account, true, session);
                    persons.add(account);
                    publish(account);
                    sleepAWhile();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return persons;
    }

    static public void createPartyRoles(Account account) {
        if (account.getPartyRolesList() == null || account.getPartyRolesList().getList().isEmpty()) {
            account.setPartyRolesList(new PartyRolesList());
            List<PartyRole> list = LoadAccountWorker.createCustomerPartyRoles(account.getParty().getpartyId());
            for (PartyRole prole : list) {
                PartyRoleComposite tmpPartyRoleComposite = new PartyRoleComposite();
                tmpPartyRoleComposite.setParty(account);
                tmpPartyRoleComposite.setPartyRole(prole);
                account.getPartyRolesList().add(tmpPartyRoleComposite);
                //tablePanel.listModel.add(tmpPartyRoleComposite);
            }
            //
            Debug.logInfo("createPartyRoles:; account.getParty().getpartyId(): " + account.getParty().getpartyId(), module);
            // 
        }
    }

    static public void createBillingAccount(Account account, BigDecimal accountLimit) {

        Debug.logInfo("createPartyRoles:; account.getParty().getpartyId(): " + account.getParty().getpartyId() + "  accountLimit: " + accountLimit, module);
        if (account.getBillingAccountComposite() == null || account.getBillingAccountComposite().isEmpty()) {
            List<BillingAccountComposite> billingAccountComposites = new ArrayList<BillingAccountComposite>();

            BillingAccountComposite billingAccountComposite = LoadBillingAccountWorker.newBillingAccountComposite();
            billingAccountComposite.getBillingAccount().setaccountCurrencyUomId(account.getParty().getpreferredCurrencyUomId());
            billingAccountComposite.getBillingAccount().setaccountLimit(accountLimit);
            billingAccountComposite.getBillingAccount().setdescription("Credit Account - " + account.getParty().getpartyId());
//            billingAccount.getBillingAccount()
            billingAccountComposites.add(billingAccountComposite);
            BillingAccountRole role = LoadBillingAccountWorker.newBillingAccountRole(account.getParty().getpartyId(), "BILL_TO_CUSTOMER");
            billingAccountComposite.getBillingAccountRoleList().add(role);
            billingAccountComposite.setPrimaryPartyId(account.getParty().getpartyId());
            account.setBillingAccountComposite(billingAccountComposites);
        }
    }

    @Override
    protected void process(List<Account> chunks) {
        /*            try{
         throw new Exception("process(List<Person> chunks)");
         }
         catch(Exception e){
         e.printStackTrace();
         }
         */
        personListModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    private void sleepAWhile() {
        try {
            Thread.yield();
            long timeToSleep = getTimeToSleep();
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
        }
    }

    private long getTimeToSleep() {
        return loadPersonsSpeedModel.getValue();
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;

    }

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

}
