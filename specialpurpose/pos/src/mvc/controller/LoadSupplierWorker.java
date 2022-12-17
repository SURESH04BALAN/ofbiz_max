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
import mvc.data.Address;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Account;

/**
 *
 * @author siranjeev
 */
public class LoadSupplierWorker extends SwingWorker<List<Account>, Account> {

    public static final String module = LoadSupplierWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<Account> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private String csvFile;
    XuiSession session = null;

    public LoadSupplierWorker(ListAdapterListModel<Account> personListModel, String fileName, XuiSession delegator) {
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
            "CURRENCYUOM",};

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
            getClassLoader();
            final ClassLoader cl = this.getClassLoader();
            Thread.currentThread().setContextClassLoader(cl);

            //loop map
            for (Map<String, String> map : listMap) {

    //            for (Map.Entry<String, String> entry : map.entrySet()) {
    //                System.out.println("[ Key= " + entry.getKey() + " , Value="
                //                        + entry.getValue() + "]");
                //            }
                String partyId = map.get("PARTY_ID");
                Account account = null;

                try {
                    account = LoadAccountWorker.getAccount(partyId, session);
                } catch (Exception ex) {
                    //    Debug.logError(ex, module);
                }
                
                if (account == null) {
                    try {
                        account = LoadAccountWorker.createAccount(map, session);
//                        ListAdapterListModel<Account> personListModel = new ListAdapterListModel<Account>();
                        //            LoadAccountWorker worker = new LoadAccountWorker(personListModel, session);
                        LoadAccountWorker.saveAccount(account, true, session);
                        persons.add(account);
                        publish(account);
                        sleepAWhile();

                    } catch (Exception ex) {
                        Debug.logError(ex, module);
                    }
                } else {
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

    @Override
    protected void process(List<Account> chunks) {
        /*            try{
         throw new Exception("process(List<Account> chunks)");
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
