/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller.dataload.posdata;

import com.googlecode.jcsv.reader.CSVEntryParser;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.UtilDateTime;

import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class PosSaleDataEntryParser implements CSVEntryParser<PosSalesData> {

    @Override
    public PosSalesData parseEntry(String... data) {
        PosSalesData prod = new PosSalesData();
        int i = 0;
        int length = data.length;

        prod.date = getValidTimestamp(data[i++]);
        prod.sales = getValidBigDecimal(data[i++]);        
        prod.eftpos = getValidBigDecimal(data[i++]);        
        prod.cashout = getValidBigDecimal(data[i++]);        
        prod.cash = getValidBigDecimal(data[i++]);                
        prod.total = getValidBigDecimal(data[i++]);                
        
        return prod;
    }

    public static Timestamp getValidTimestamp(String obj) {
        if (UtilValidate.isNotEmpty(obj)) {
            try {
//                "9/07/2013"
                return UtilDateTime.stringToTimeStamp(obj, "dd/MM/yyyy", TimeZone.getDefault(), Locale.getDefault());
            } catch (ParseException ex) {
                Logger.getLogger(PosSaleDataEntryParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static BigDecimal getValidBigDecimal(String obj) {
        if (UtilValidate.isNotEmpty(obj)) {
            return OrderMaxUtility.getValidEntityBigDecimal(obj);
        }
        return BigDecimal.ZERO;
    }

}
