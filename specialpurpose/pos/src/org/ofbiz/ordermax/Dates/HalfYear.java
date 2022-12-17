/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.Dates;

/**
 *
 * @author BBS Auctions
 */

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ------------
 * Half Year.java
 * ------------
 * (C) Copyright 2001-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 11-Oct-2001 : Version 1 (DG);
 * 18-Dec-2001 : Changed order of parameters in constructor (DG);
 * 19-Dec-2001 : Added a new constructor as suggested by Paul English (DG);
 * 29-Jan-2002 : Added a new method parseQuarter(String) (DG);
 * 14-Feb-2002 : Fixed bug in Half Year(Date) constructor (DG);
 * 26-Feb-2002 : Changed getStart(), getMiddle() and getEnd() methods to
 *               evaluate with reference to a particular time zone (DG);
 * 19-Mar-2002 : Changed API for TimePeriod classes (DG);
 * 24-Jun-2002 : Removed main method (just test code) (DG);
 * 10-Sep-2002 : Added getSerialIndex() method (DG);
 * 07-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 10-Jan-2003 : Changed base class and method names (DG);
 * 13-Mar-2003 : Moved to com.jrefinery.data.time package, and implemented
 *               Serializable (DG);
 * 21-Oct-2003 : Added hashCode() method (DG);
 * 10-Dec-2005 : Fixed argument checking bug (1377239) in constructor (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 05-Oct-2006 : Updated API docs (DG);
 * 06-Oct-2006 : Refactored to cache first and last millisecond values (DG);
 * 16-Sep-2008 : Deprecated DEFAULT_TIME_ZONE (DG);
 * 25-Nov-2008 : Added new constructor with Locale (DG);
 *
 */

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Defines a quarter (in a given year).  The range supported is Q1 1900 to
 * Q4 9999.  This class is immutable, which is a requirement for all
 * {@link RegularTimePeriod} subclasses.
 */
public class HalfYear extends RegularTimePeriod implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 3810061714380888671L;

    /** Constant for quarter 1. */
    public static final int FIRST_HALFYEAR= 1;

    /** Constant for quarter 4. */
    public static final int LAST_HALFYEAR = 2;

    /** The first month in each quarter. */
    public static final int[] FIRST_MONTH_IN_HALFYEAR = {
        0, MonthConstants.JANUARY,  MonthConstants.JULY
    };

    /** The last month in each quarter. */
    public static final int[] LAST_MONTH_IN_HALFYEAR = {
        0,  MonthConstants.JUNE, MonthConstants.DECEMBER
    };

    /** The year in which the quarter falls. */
    private short year;

    /** The quarter (1-4). */
    private byte halfyear;

    /** The first millisecond. */
    private long firstMillisecond;

    /** The last millisecond. */
    private long lastMillisecond;

    /**
     * Constructs a new Half Year, based on the current system date/time.
     */
    public HalfYear() {
        this(new Date());
    }

    /**
     * Constructs a new halfyear.
     *
     * @param year  the year (1900 to 9999).
     * @param halfyear  the halfyear (1 to 4).
     */
    public HalfYear(int halfyear, int year) {
        if ((halfyear < FIRST_HALFYEAR) || (halfyear > LAST_HALFYEAR)) {
            throw new IllegalArgumentException("Half Year outside valid range.");
        }
        this.year = (short) year;
        this.halfyear = (byte) halfyear;
        peg(Calendar.getInstance());
    }

    /**
     * Constructs a new halfyear.
     *
     * @param halfyear  the halfyear (1 to 4).
     * @param year  the year (1900 to 9999).
     */
    public HalfYear(int halfyear, Year year) {
        if ((halfyear < FIRST_HALFYEAR) || (halfyear > LAST_HALFYEAR)) {
            throw new IllegalArgumentException("Half Year outside valid range.");
        }
        this.year = (short) year.getYear();
        this.halfyear = (byte) halfyear;
        peg(Calendar.getInstance());
    }

    /**
     * Constructs a new instance, based on a date/time and the default time
     * zone.
     *
     * @param time  the date/time (<code>null</code> not permitted).
     *
     * @see #Half Year(Date, TimeZone)
     */
    public HalfYear(Date time) {
        this(time, TimeZone.getDefault());
    }

    /**
     * Constructs a Half Year, based on a date/time and time zone.
     *
     * @param time  the date/time.
     * @param zone  the zone (<code>null</code> not permitted).
     *
     * @deprecated Since 1.0.12, use {@link #Half Year(Date, TimeZone, Locale)}
     *     instead.
     */
    public HalfYear(Date time, TimeZone zone) {
        this(time, zone, Locale.getDefault());
    }

    /**
     * Creates a new <code>Half Year</code> instance, using the specified
     * zone and locale.
     *
     * @param time  the current time.
     * @param zone  the time zone.
     * @param locale  the locale.
     *
     * @since 1.0.12
     */
    public HalfYear(Date time, TimeZone zone, Locale locale) {
        Calendar calendar = Calendar.getInstance(zone, locale);
        calendar.setTime(time);
        int month = calendar.get(Calendar.MONTH) + 1;
        this.halfyear = (byte) SerialDate.monthCodeToHalfYear(month);
        this.year = (short) calendar.get(Calendar.YEAR);
        peg(calendar);
    }

    /**
     * Returns the halfyear.
     *
     * @return The halfyear.
     */
    public int getQuarter() {
        return this.halfyear;
    }

    /**
     * Returns the year.
     *
     * @return The year.
     */
    public Year getYear() {
        return new Year(this.year);
    }

    /**
     * Returns the year.
     *
     * @return The year.
     *
     * @since 1.0.3
     */
    public int getYearValue() {
        return this.year;
    }

    /**
     * Returns the first millisecond of the halfyear.  This will be determined
     * relative to the time zone specified in the constructor, or in the
     * calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The first millisecond of the halfyear.
     *
     * @see #getLastMillisecond()
     */
    @Override
    public long getFirstMillisecond() {
        return this.firstMillisecond;
    }

    /**
     * Returns the last millisecond of the halfyear.  This will be
     * determined relative to the time zone specified in the constructor, or
     * in the calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The last millisecond of the halfyear.
     *
     * @see #getFirstMillisecond()
     */
    @Override
    public long getLastMillisecond() {
        return this.lastMillisecond;
    }

    /**
     * Recalculates the start date/time and end date/time for this time period
     * relative to the supplied calendar (which incorporates a time zone).
     *
     * @param calendar  the calendar (<code>null</code> not permitted).
     *
     * @since 1.0.3
     */
    @Override
    public final void peg(Calendar calendar) {
        this.firstMillisecond = getFirstMillisecond(calendar);
        this.lastMillisecond = getLastMillisecond(calendar);
    }

    /**
     * Returns the halfyear preceding this one.
     *
     * @return The halfyear preceding this one (or <code>null</code> if this is
     *     Q1 1900).
     */
    @Override
    public RegularTimePeriod previous() {
        HalfYear result;
        if (this.halfyear > FIRST_HALFYEAR) {
            result = new HalfYear(this.halfyear - 1, this.year);
        }
        else {
            if (this.year > 1900) {
                result = new HalfYear(LAST_HALFYEAR, this.year - 1);
            }
            else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Returns the halfyear following this one.
     *
     * @return The halfyear following this one (or null if this is Q4 9999).
     */
    @Override
    public RegularTimePeriod next() {
        HalfYear result;
        if (this.halfyear < LAST_HALFYEAR) {
            result = new HalfYear(this.halfyear + 1, this.year);
        }
        else {
            if (this.year < 9999) {
                result = new HalfYear(FIRST_HALFYEAR, this.year + 1);
            }
            else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Returns a serial index number for the halfyear.
     *
     * @return The serial index number.
     */
    @Override
    public long getSerialIndex() {
        return this.year * 4L + this.halfyear;
    }

    /**
     * Tests the equality of this Half Year object to an arbitrary object.
     * Returns <code>true</code> if the target is a Half Year instance
     * representing the same halfyear as this object.  In all other cases,
     * returns <code>false</code>.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return <code>true</code> if halfyear and year of this and the object are
     *         the same.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj != null) {
            if (obj instanceof HalfYear) {
                HalfYear target = (HalfYear) obj;
                return (this.halfyear == target.getQuarter()
                        && (this.year == target.getYearValue()));
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }

    /**
     * Returns a hash code for this object instance.  The approach described by
     * Joshua Bloch in "Effective Java" has been used here:
     * <p>
     * <code>http://developer.java.sun.com/developer/Books/effectivejava
     * /Chapter3.pdf</code>
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.halfyear;
        result = 37 * result + this.year;
        return result;
    }

    /**
     * Returns an integer indicating the order of this Half Year object relative
     * to the specified object:
     *
     * negative == before, zero == same, positive == after.
     *
     * @param o1  the object to compare
     *
     * @return negative == before, zero == same, positive == after.
     */
    @Override
    public int compareTo(Object o1) {

        int result;

        // CASE 1 : Comparing to another Half Year object
        // --------------------------------------------
        if (o1 instanceof HalfYear) {
            HalfYear q = (HalfYear) o1;
            result = this.year - q.getYearValue();
            if (result == 0) {
                result = this.halfyear - q.getQuarter();
            }
        }

        // CASE 2 : Comparing to another TimePeriod object
        // -----------------------------------------------
        else if (o1 instanceof RegularTimePeriod) {
            // more difficult case - evaluate later...
            result = 0;
        }

        // CASE 3 : Comparing to a non-TimePeriod object
        // ---------------------------------------------
        else {
            // consider time periods to be ordered after general objects
            result = 1;
        }

        return result;

    }

    /**
     * Returns a string representing the halfyear (e.g. "Q1/2002").
     *
     * @return A string representing the halfyear.
     */
    @Override
    public String toString() {
        return "Q" + this.halfyear + "/" + this.year;
    }

    /**
     * Returns the first millisecond in the Half Year, evaluated using the
     * supplied calendar (which determines the time zone).
     *
     * @param calendar  the calendar (<code>null</code> not permitted).
     *
     * @return The first millisecond in the Half Year.
     *
     * @throws NullPointerException if <code>calendar</code> is
     *     <code>null</code>.
     */
    @Override
    public long getFirstMillisecond(Calendar calendar) {
        int month = HalfYear.FIRST_MONTH_IN_HALFYEAR[this.halfyear];
        calendar.set(this.year, month - 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // in the following line, we'd rather call calendar.getTimeInMillis()
        // to avoid object creation, but that isn't supported in Java 1.3.1
        return calendar.getTime().getTime();
    }

    /**
     * Returns the last millisecond of the Half Year, evaluated using the
     * supplied calendar (which determines the time zone).
     *
     * @param calendar  the calendar (<code>null</code> not permitted).
     *
     * @return The last millisecond of the Half Year.
     *
     * @throws NullPointerException if <code>calendar</code> is
     *     <code>null</code>.
     */
    @Override
    public long getLastMillisecond(Calendar calendar) {
        int month = HalfYear.LAST_MONTH_IN_HALFYEAR[this.halfyear];
        int eom = SerialDate.lastDayOfMonth(month, this.year);
        calendar.set(this.year, month - 1, eom, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        // in the following line, we'd rather call calendar.getTimeInMillis()
        // to avoid object creation, but that isn't supported in Java 1.3.1
        return calendar.getTime().getTime();
    }

    /**
     * Parses the string argument as a halfyear.
     * <P>
     * This method should accept the following formats: "YYYY-QN" and "QN-YYYY",
     * where the "-" can be a space, a forward-slash (/), comma or a dash (-).
     * @param s A string representing the halfyear.
     *
     * @return The halfyear.
     */
    public static HalfYear parseQuarter(String s) {

        // find the Q and the integer following it (remove both from the
        // string)...
        int i = s.indexOf("Q");
        if (i == -1) {
            throw new TimePeriodFormatException("Missing Q.");
        }

        if (i == s.length() - 1) {
            throw new TimePeriodFormatException("Q found at end of string.");
        }

        String qstr = s.substring(i + 1, i + 2);
        int halfyear = Integer.parseInt(qstr);
        String remaining = s.substring(0, i) + s.substring(i + 2, s.length());

        // replace any / , or - with a space
        remaining = remaining.replace('/', ' ');
        remaining = remaining.replace(',', ' ');
        remaining = remaining.replace('-', ' ');

        // parse the string...
        Year year = Year.parseYear(remaining.trim());
        HalfYear result = new HalfYear(halfyear, year);
        return result;

    }

}
