/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.Dates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;
import org.ofbiz.ordermax.Dates.SimpleTimePeriod;

/**
 *
 * @author siranjeev
 */
public class DateSingleton implements Serializable {

    final public static int CURRENT_PERIOD = 0;
    final public static int YTD_PERIOD = 1;
    final public static int LAST_PERIOD = 2;
    final public static int LAST_YTD_PERIOD = 3;

    public enum PERIOD {

        NODATE("<None>"),
        CUSTOMDATE("Custom Date"),
        TODAY("Today"),
        THIS_WEEK("This Week"),
        THIS_MONTH("This Month"),
        THIS_QUATER("This Quater"),
        THIS_HALF("This Half"),
        THIS_YEAR("This Year"),
        THIS_WEEK_TO_DATE("Week To Date", YTD_PERIOD),
        THIS_MONTH_TO_DATE("Month To Date", YTD_PERIOD),
        THIS_QUATER_TO_DATE("Quater To Date", YTD_PERIOD),
        THIS_HALF_TO_DATE("Half Year To Date", YTD_PERIOD),
        THIS_YEAR_TO_DATE("Year To Date", YTD_PERIOD);

        private String periodName;
        private int periodType = CURRENT_PERIOD;

        public int getPeriodType() {
            return periodType;
        }

        public String getPeriodName() {
            return periodName;
        }

        PERIOD(String header) {

            periodName = header;
        }

        PERIOD(String header, int type) {

            periodName = header;
            this.periodType = type;
        }

        @Override
        public String toString() {
            return periodName;
        }
    }

    public enum PERIOD_START_SEL {

        EQUALS("Equals"),
        SAMEDAY("Same Day"),
        GREATERTHANDAYSTART("Greater Than From Day Start"),
        GREATERTHAN("Greater Than");

        private String periodName;
   
        public String getPeriodName() {
            return periodName;
        }

        PERIOD_START_SEL(String header) {
            periodName = header;
        }

        @Override
        public String toString() {
            return periodName;
        }
    }

       public enum PERIOD_END_SEL {

        LESSTHAN("Less Than"),
        UPTODAY("Up To Day"),
        UPTHROUGHDAY("Up Through Day"),
        ISEMPTY("Is Empty");

        private String periodName;
   
        public String getPeriodName() {
            return periodName;
        }

        PERIOD_END_SEL(String header) {
            periodName = header;
        }

        @Override
        public String toString() {
            return periodName;
        }
    }
    private static final long serialVersionUID = 1L;
    Map<PERIOD, PERIOD> valueMap = null;
    List<PERIOD_END_SEL> periodEndSel = null;
    List<PERIOD_START_SEL> periodStartSel = null;

    private DateSingleton() {
      valueMap = new HashMap<PERIOD, PERIOD>();
      periodEndSel = new ArrayList<PERIOD_END_SEL>();
      periodStartSel =  new ArrayList<PERIOD_START_SEL>();
        
    }

    private static class OrderStatusSingletonHolder {

        public static final DateSingleton INSTANCE = new DateSingleton();
    }

    public static DateSingleton getInstance() {
        return OrderStatusSingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<PERIOD> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        List<PERIOD> list =  new ArrayList<PERIOD>(getInstance().valueMap.values());
        Collections.sort(list, new PeriodComparator());        
        return list;
    }

    final static public List<PERIOD_END_SEL>getPeriodEndValueList(){
        if (getInstance().periodEndSel.isEmpty()) {
            loadAll();
        }

        List<PERIOD_END_SEL> list =  new ArrayList<PERIOD_END_SEL>(getInstance().periodEndSel);
        Collections.sort(list, new PeriodEndSelComparator());        
        return list;        
    }
    
    final static public List<PERIOD_START_SEL> getPeriodStartValueList () {
        if (getInstance().periodStartSel.isEmpty()) {
            loadAll();
        }

        List<PERIOD_START_SEL> list =  new ArrayList<PERIOD_START_SEL>(getInstance().periodStartSel);
        Collections.sort(list, new PeriodStartSelComparator());        
        return list;         
    }
    
    final static public ListModel<PERIOD> getValueListModal() {
        ListAdapterListModel<PERIOD> modal = new ListAdapterListModel<PERIOD>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<PERIOD> getValueListModal(int typeId) {

        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        ListAdapterListModel<PERIOD> modal = new ListAdapterListModel<PERIOD>();
        for (Map.Entry<PERIOD, PERIOD> entry : getInstance().valueMap.entrySet()) {
            if (entry.getValue().getPeriodType() == typeId) {
                modal.add(entry.getValue());
            }
        }

        return modal;
    }

    final static public List<PERIOD> getValueList(int typeId) {

        List<PERIOD> modalRet = null;

        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        List<PERIOD> modal = new ArrayList<PERIOD>();
        for (Map.Entry<PERIOD, PERIOD> entry : getInstance().valueMap.entrySet()) {
            if (entry.getValue().getPeriodType() == typeId) {
                modal.add(entry.getValue());
            }
        }
        modalRet = modal;

        return modalRet;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            PERIOD[] valueList = PERIOD.values();
            for (PERIOD val : valueList) {
                getInstance().valueMap.put(val, val);
            }
            getInstance().periodEndSel.clear();

            getInstance().periodEndSel.addAll(Arrays.asList(PERIOD_END_SEL.values()));
            getInstance().periodStartSel.addAll(Arrays.asList(PERIOD_START_SEL.values()));
               
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SimpleTimePeriod getRegularTimePeriod(PERIOD period) throws Exception {

        SimpleTimePeriod regularTimePeriod = null;
        if (getInstance().valueMap.containsKey(period)) {
            regularTimePeriod = getDate(period);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(period)) {
                regularTimePeriod = getDate(period);
            } else {
                throw new Exception("unable to load uom : " + period);
            }
        }

        return regularTimePeriod;
    }

    public static SimpleTimePeriod getDate(PERIOD dateLiteral) {
        switch (dateLiteral) {
            case TODAY:
                return new SimpleTimePeriod((new Day()).getStart(), (new Day()).getEnd());
            case THIS_WEEK:
                return new SimpleTimePeriod((new Week()).getStart(), (new Week()).getEnd());
            case THIS_MONTH:
                return new SimpleTimePeriod((new Month()).getStart(), (new Month()).getEnd());
            case THIS_QUATER:
                return new SimpleTimePeriod((new Quarter()).getStart(), (new Quarter()).getEnd());
            case THIS_HALF:
                return new SimpleTimePeriod((new HalfYear()).getStart(), (new HalfYear()).getEnd());

            case THIS_YEAR:
                return new SimpleTimePeriod((new Year()).getStart(), (new Year()).getEnd());
            case THIS_WEEK_TO_DATE:
                return new SimpleTimePeriod((new Week()).getStart(), (new Day()).getEnd());

            case THIS_MONTH_TO_DATE:
                return new SimpleTimePeriod((new Month()).getStart(), (new Day()).getEnd());
            case THIS_QUATER_TO_DATE:
                return new SimpleTimePeriod((new Quarter()).getStart(), (new Day()).getEnd());
            case THIS_HALF_TO_DATE:
                return new SimpleTimePeriod((new HalfYear()).getStart(), (new Day()).getEnd());
            case THIS_YEAR_TO_DATE:
                return new SimpleTimePeriod((new Year()).getStart(), (new Day()).getEnd());

        }
        return null;
    }

    public static PERIOD getPeriodFromDesc(String desc) throws Exception {

        PERIOD[] valueList = PERIOD.values();
        for (PERIOD val : valueList) {
            if (val.toString().equals(desc)) {
                return val;
            }
        }

        throw new Exception("unable to find status desc : " + desc);
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        DateSingleton.singletonSesion = singletonSesion;
    }
    
    protected static class PeriodComparator implements Comparator<PERIOD> {

        @Override
        public int compare(PERIOD t, PERIOD t1) {
            return t.getPeriodName().compareTo(t1.getPeriodName());            
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }      

    protected static class PeriodEndSelComparator implements Comparator<PERIOD_END_SEL> {

        @Override
        public int compare(PERIOD_END_SEL t, PERIOD_END_SEL t1) {
            return t.getPeriodName().compareTo(t1.getPeriodName());            
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }      
    
    protected static class PeriodStartSelComparator implements Comparator<PERIOD_START_SEL> {

        @Override
        public int compare(PERIOD_START_SEL t, PERIOD_START_SEL t1) {
            return t.getPeriodName().compareTo(t1.getPeriodName());            
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }       
        
    
}
