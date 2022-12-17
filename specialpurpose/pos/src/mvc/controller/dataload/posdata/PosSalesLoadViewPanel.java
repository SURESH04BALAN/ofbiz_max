/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller.dataload.posdata;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javolution.util.FastMap;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.ListModelSelection;
import mvc.model.table.BigFishProductTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilFormatOut;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.pos.PosTransaction;
import org.ofbiz.pos.device.DeviceLoader;
import org.ofbiz.pos.device.impl.Receipt;

/**
 *
 * @author siranjeev
 */
public class PosSalesLoadViewPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4471606875093169644L;
    public static final String module = PosSalesLoadViewPanel.class.getName();
    private PosSalesDataTableModel productTableModel = new PosSalesDataTableModel();
    private ListAdapterListModel<PosSalesData> personListModel = new ListAdapterListModel<PosSalesData>();
    private ListModelSelection<PosSalesData> listModelSelection = new ListModelSelection<PosSalesData>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private JTable personTable = new JTable(productTableModel);

    private JList<PosSalesData> personList = new JList<PosSalesData>(personListModel);
    private ListComboBoxModel<PosSalesData> personComboBoxModel = new ListComboBoxModel<PosSalesData>();
    private JComboBox<PosSalesData> personsComboBox = new JComboBox<PosSalesData>(personComboBoxModel);
    public final JTextField textEdit = new JTextField();

    public void setProductList(ListAdapterListModel<PosSalesData> bigFishProductListModel) {
        personList.setModel(personListModel);
        productTableModel.setListModel(bigFishProductListModel);
        personComboBoxModel.setListModel(bigFishProductListModel);
        listModelSelection.setListModels(bigFishProductListModel, selectionModel);
    }

    public JButton jBtnLoad = null;//new JButton("Load");
    public JButton jBtnPrint = null;//new JButton("Load");

    public PosSalesLoadViewPanel() {
        setLayout(new BorderLayout());
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personList.setSelectionModel(selectionModel);
        personTable.setSelectionModel(selectionModel);
        personComboBoxModel.setListSelectionModel(selectionModel);

        ListCellRenderer<PosSalesData> personRenderer = new PosSalesDataListCellRenderer();
        personList.setCellRenderer(personRenderer);
        personList.setEnabled(true);
        personsComboBox.setRenderer(personRenderer);
        personTable.setSelectionModel(personList.getSelectionModel());

        JScrollPane scrollPane = new JScrollPane(personTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        personTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setBounds(10, 11, 580, 130);
        add(scrollPane, BorderLayout.CENTER);

//        scrollPane.setViewportView(personTable);
        personsComboBox.setBounds(10, 153, 580, 30);
        JPanel panel = new JPanel(new GridLayout());
        panel.setBounds(10, 153, 580, 30);
        panel.add(personsComboBox);

        JPanel panelEditor = new JPanel(new BorderLayout());
        JPanel panelButton = new JPanel(new BorderLayout());
//        final JTextField textEdit = new JTextField();
        textEdit.setText("C:\\ordermax\\Real_Data\\ProductImport_copy_sorted_product_code_fnal_latest.csv");
//        JPanel panel = new JPanel(new BorderLayout());        
        jBtnLoad = new JButton("Load");
        JButton jBtnDir = new JButton("Dir");
        jBtnDir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File filePath = BaseHelper.getImageFilePath("original");
                    textEdit.setText(filePath.getPath());
                } catch (Exception ex) {
                }
//                loadProductTree();
            }
        });
        jBtnPrint = new JButton("Print");

        jBtnLoad = new JButton("Load");

        panelButton.add(BorderLayout.EAST, jBtnLoad);
        panelButton.add(BorderLayout.WEST, jBtnDir);
        panelButton.add(BorderLayout.CENTER, jBtnPrint);

        panelEditor.add(BorderLayout.CENTER, textEdit);
//        JPanel panelEditor = new JPanel(new BorderLayout());
        panelEditor.add(BorderLayout.EAST, panelButton);

        panel.add(panelEditor);
        add(panel, BorderLayout.NORTH);
    }

    public synchronized void printTotals(PosTransaction trans, GenericValue state, boolean runBalance, List<PosSalesData> closingData, String reportTemplate) {
        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        if (state == null) {
            state = trans.getTerminalState();
        }
//List<Map<String, BigDecimal>> closingData
        for (PosSalesData values : closingData) {
            BigDecimal checkTotal = BigDecimal.ZERO;
            BigDecimal cashTotal = BigDecimal.ZERO;
            BigDecimal gcTotal = BigDecimal.ZERO;
            BigDecimal ccTotal = BigDecimal.ZERO;
            BigDecimal othTotal = BigDecimal.ZERO;

            BigDecimal total = BigDecimal.ZERO;
            cashTotal = values.cash.add(values.cashout);
            ccTotal = values.eftpos;
            othTotal = values.cashout;
            total = values.cash.add(values.eftpos).add(values.cashout);

            Calendar c = Calendar.getInstance();
            c.setTime(values.date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 5) {
                values.date.setHours(7);
            } else {
                values.date.setHours(6);
            }
            values.date.setMinutes(randInt(35, 60));
            values.date.setSeconds(randInt(0, 60));
            trans.setPrintDate(values.date);

            /*
             if ("PERSONAL_CHECK".equals(pmt)) {
             checkTotal = checkTotal.add(amt);
             } else if ("GIFT_CARD".equals(pmt)) {
             gcTotal = gcTotal.add(amt);
             } else if ("CREDIT_CARD".equals(pmt)) {
             ccTotal = ccTotal.add(amt);
             } else {
             othTotal = othTotal.add(amt);
             }
             total = total.add(amt);
             */
            Map<String, String> reportMap = FastMap.newInstance();
//            String reportTemplate = "totals_seven_hills.txt";

            // miscellaneous
            reportMap.put("term", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosTerm", Locale.getDefault()), 20, false, ' '));
            reportMap.put("draw", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosDraw", Locale.getDefault()), 20, false, ' '));
            reportMap.put("clerk", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosClerk", Locale.getDefault()), 20, false, ' '));
            reportMap.put("total_report", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosTotalReport", Locale.getDefault()), 20, false, ' '));

            // titles
            reportMap.put("cashTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCash", Locale.getDefault()), 20, false, ' '));
            reportMap.put("checkTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCheck", Locale.getDefault()), 20, false, ' '));
            reportMap.put("giftCardTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGiftCard", Locale.getDefault()), 20, false, ' '));
            reportMap.put("creditCardTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosCreditCard", Locale.getDefault()), 20, false, ' '));
            reportMap.put("otherTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosOther", Locale.getDefault()), 20, false, ' '));
            reportMap.put("grossSalesTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGrossSales", Locale.getDefault()), 20, false, ' '));
            reportMap.put("grossSalesDiffTitle", UtilFormatOut.padString(UtilProperties.getMessage(PosTransaction.resource, "PosGrossSalesDiff", Locale.getDefault()), 0, false, ' '));

            reportMap.put("+/-", UtilFormatOut.padString("+/-", 20, false, ' '));
            reportMap.put("spacer", UtilFormatOut.padString("", 20, false, ' '));

            // logged
            reportMap.put("cashTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashTotal), 8, false, ' '));
            reportMap.put("checkTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkTotal), 8, false, ' '));
            reportMap.put("ccTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccTotal), 8, false, ' '));
            reportMap.put("gcTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcTotal), 8, false, ' '));
            reportMap.put("otherTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(othTotal), 8, false, ' '));
            reportMap.put("grossTotal", UtilFormatOut.padString(UtilFormatOut.formatPrice(total), 8, false, ' '));

            if (runBalance) {
                // actuals
                BigDecimal cashEnd = state.getBigDecimal("actualEndingCash");
                BigDecimal checkEnd = state.getBigDecimal("actualEndingCheck");
                BigDecimal ccEnd = state.getBigDecimal("actualEndingCc");
                BigDecimal gcEnd = state.getBigDecimal("actualEndingGc");
                BigDecimal othEnd = state.getBigDecimal("actualEndingOther");
                BigDecimal grossEnd = cashEnd.add(checkEnd.add(ccEnd.add(gcEnd.add(othEnd))));

                reportMap.put("cashEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashEnd), 8, false, ' '));
                reportMap.put("checkEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkEnd), 8, false, ' '));
                reportMap.put("ccEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccEnd), 8, false, ' '));
                reportMap.put("gcEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcEnd), 8, false, ' '));
                reportMap.put("otherEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(othEnd), 8, false, ' '));
                reportMap.put("grossEnd", UtilFormatOut.padString(UtilFormatOut.formatPrice(grossEnd), 8, false, ' '));

                // diffs
                BigDecimal cashDiff = cashEnd.subtract(cashTotal);
                BigDecimal checkDiff = checkEnd.subtract(checkTotal);
                BigDecimal ccDiff = ccEnd.subtract(ccTotal);
                BigDecimal gcDiff = gcEnd.subtract(gcTotal);
                BigDecimal othDiff = othEnd.subtract(othTotal);
                BigDecimal grossDiff = cashDiff.add(checkDiff.add(ccDiff.add(gcDiff.add(othDiff))));

                reportMap.put("cashDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(cashDiff), 8, false, ' '));
                reportMap.put("checkDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(checkDiff), 8, false, ' '));
                reportMap.put("ccDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(ccDiff), 8, false, ' '));
                reportMap.put("gcDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(gcDiff), 8, false, ' '));
                reportMap.put("otherDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(othDiff), 8, false, ' '));
                reportMap.put("grossDiff", UtilFormatOut.padString(UtilFormatOut.formatPrice(grossDiff), 8, false, ' '));

                // set the report template
                //reportTemplate = "balance.txt";
            }

            Receipt receipt = DeviceLoader.receipt;
            if (receipt.isEnabled()) {
                receipt.printReport(trans, reportTemplate, reportMap);
            }
            else{
                
            }
        }
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = ControllerOptions.getSession().getClassLoader();
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
