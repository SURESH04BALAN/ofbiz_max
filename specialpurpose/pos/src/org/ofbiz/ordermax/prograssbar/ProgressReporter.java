/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.prograssbar;

/**
 *
 * @author siranjeev
 */
public interface ProgressReporter {

    public void setSize(int size);

    public void setProgress(int current);
    public void setProgress(String str);
    public boolean isCancelled();
}
