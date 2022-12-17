/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller.dataload.posdata;

/**
 *
 * @author siranjeev
 */
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import javolution.util.FastMap;
import org.ofbiz.ordermax.dataimportexport.loaders.ProductEntryParser;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.device.DeviceLoader;
import org.ofbiz.pos.device.impl.Receipt;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadPosSalesFromFileWorker extends SwingWorker<List<PosSalesData>, PosSalesData> {

    public static final String module = LoadPosSalesFromFileWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<PosSalesData> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private String csvFile;
    XuiSession session = null;

    public LoadPosSalesFromFileWorker(ListAdapterListModel<PosSalesData> personListModel, String fileName, XuiSession delegator) {
        this.personListModel = personListModel;
        csvFile = fileName;
        this.session = delegator;

    }

    @Override
    protected List<PosSalesData> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<PosSalesData> persons = new ArrayList<PosSalesData>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        try {

            try {
                Reader csvFileIo = null;
                try {
                    csvFileIo = new BufferedReader(new FileReader(csvFile));//new InputStreamReader(is);
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
                List<PosSalesData> personsRead = null;
                try {

                    CSVReader<PosSalesData> personReader = new CSVReaderBuilder<PosSalesData>(csvFileIo).entryParser(new PosSaleDataEntryParser()).build();
                    personsRead = personReader.readAll();
                } catch (Exception e) {
                    Debug.logError(e, "Unable to rollback transaction", module);
                }

                maxProgress = personsRead.size();

                System.out.println("Start create product");
                personListModel.clear();
                for (PosSalesData posSalesData : personsRead) {

                    persons.add(posSalesData);
                    publish(posSalesData);
                    sleepAWhile();

                }
            } catch (Exception e) {
                Debug.logError(e, "Unable to rollback transaction", module);
            }

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
    protected void process(List<PosSalesData> chunks) {
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
