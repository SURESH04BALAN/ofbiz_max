/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.Dates;

import java.util.Date;

/**
 *
 * @author BBS Auctions
 */

/**
 * A period of time measured to millisecond precision using two instances of
 * <code>java.util.Date</code>.
 */
public interface TimePeriod extends Comparable {

    /**
     * Returns the start date/time.  This will always be on or before the
     * end date.
     *
     * @return The start date/time (never <code>null</code>).
     */
    public Date getStart();

    /**
     * Returns the end date/time.  This will always be on or after the
     * start date.
     *
     * @return The end date/time (never <code>null</code>).
     */
    public Date getEnd();

}

