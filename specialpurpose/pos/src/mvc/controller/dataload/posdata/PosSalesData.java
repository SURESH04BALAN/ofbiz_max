/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller.dataload.posdata;

import java.math.BigDecimal;

/**
 *
 * @author BBS Auctions
 */
public class PosSalesData {
    public java.sql.Timestamp date;
    public BigDecimal sales;
    public BigDecimal eftpos;
    public BigDecimal cashout;
    public BigDecimal cash;
    public BigDecimal total;
    public String destination;
    public String source;    

    public String outputDebug() {
        String str = date + ","
                + sales + ","
                + eftpos + ","
                + cashout + ","
                + cash + ","
                + total + ","
                + source + ","
                + destination                ;
        return str;
    }
}
