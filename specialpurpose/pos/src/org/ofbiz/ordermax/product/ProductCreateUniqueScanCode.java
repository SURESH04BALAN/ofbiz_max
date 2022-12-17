/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.util.List;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;

/**
 *
 * @author MaxSpice
 */
public class ProductCreateUniqueScanCode {

    protected XuiSession session;

    public ProductCreateUniqueScanCode(XuiSession val) {
        session = val;
    }
 public String generateScanCode(String productId) {

        String scanCode = getUniqueScanCode(productId);
        
        String strSeq = org.apache.commons.lang.StringUtils.leftPad(scanCode, 12, '0');
        return strSeq;
    }
    protected String getUniqueScanCode(String prodId) {

        String scanCode = "";
        String alpha = "";
        String numaric = "";

        for (int i = 0; i < prodId.length(); ++i) {
            String code = prodId.substring(i, i + 1);
            String val = PosProductHelper.phoneDialAlphaToNumaric(code);
            scanCode += val;

            if (code.equals(val)) {
                numaric += code;
            } else {
                alpha += code;
            }
        }

        if (isScanIdExists(scanCode)) {
            //Debug.logInfo("isScanIdExists  " + scanCode, module);
            try {
                Integer intVal = new Integer(numaric) + 1;

                if (intVal < 1000) {
                    prodId = alpha + "0" + intVal.toString();
                } else {
                    prodId = alpha + intVal.toString();
                }
                //Debug.logInfo("prodId new:   " + prodId, module);
                scanCode = getUniqueScanCode(prodId);

            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return scanCode;
    }

    public boolean isScanIdExists(String scanId) {
        List<GenericValue> list = PosProductHelper.getProductsFromIdentificationType(scanId, "EAN", session.getDelegator());
        return !list.isEmpty();
    }

}
