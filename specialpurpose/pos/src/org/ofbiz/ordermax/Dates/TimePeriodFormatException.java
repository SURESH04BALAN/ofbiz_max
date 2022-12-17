/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.Dates;


/**
 * An exception that indicates an invalid format in a string representing a
 * time period.
 */
public class TimePeriodFormatException extends IllegalArgumentException {

    /**
     * Creates a new exception.
     *
     * @param message  a message describing the exception.
     */
    public TimePeriodFormatException(String message) {
        super(message);
    }

}
