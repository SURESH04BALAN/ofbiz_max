/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

import com.googlecode.jcsv.reader.CSVEntryParser;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author BBS Auctions
 */
public class MaxProductEntryParser implements CSVEntryParser<org.ofbiz.ordermax.entity.Product> {

    @Override
    public org.ofbiz.ordermax.entity.Product parseEntry(String... data) {

        org.ofbiz.ordermax.entity.Product prod = new org.ofbiz.ordermax.entity.Product();
        int i = 0;
        int length = data.length;

        prod.setproductTypeId(null);
        //parent
        prod.setrequireInventory(i++ < length ? data[i - 1] : "");
  //      Debug.logError("setrequireInventory" + i + ": " + (i < length ? data[i - 1] : ""), null);
        //virtual
        prod.setisVirtual(i++ < length ? data[i - 1] : "");

        //variant
        prod.setisVariant(i++ < length ? data[i - 1] : "");

        prod.setproductId(i++ < length ? data[i - 1] : "");
        Debug.logError("product id: " + prod.getproductId(), null);
    //    Debug.logError("setproductId" + i + ": " + (i < length ? data[i - 1] : ""), null);

        prod.setinternalName(prod.getproductId());
      //  Debug.logError("Field" + i + "[internalName]: " + (i < length ? data[i - 1] : ""), null);

        prod.setproductName(i++ < length ? data[i - 1] : "");
        //Debug.logError("Field" + i + "[setproductName]: " + (i < length ? data[i - 1] : ""), null);

        prod.setbrandName(i++ < length ? data[i - 1] : "");
        //Debug.logError("Field" + i + "[setbrandName]: " + (i < length ? data[i - 1] : ""), null);

        prod.setsmallImageUrl(i++ < length ? data[i - 1] : "");
        prod.setmediumImageUrl(i++ < length ? data[i - 1] : "");
        prod.setlargeImageUrl(i++ < length ? data[i - 1] : "");
        prod.setdetailImageUrl(i++ < length ? data[i - 1] : "");
        prod.setoriginalImageUrl(i++ < length ? data[i - 1] : "");
        prod.setweightUomId(i++ < length ? data[i - 1] : "");
        prod.setweight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);

        prod.setheightUomId(i++ < length ? data[i - 1] : "");
        try{
        prod.setproductHeight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ZERO);
        }
        catch(Exception e){
            Debug.logError(e, "error");
        }
        prod.setoriginGeoId(i++ < length ? data[i - 1] : "");

        //set scan code
        prod.setconfigId(i++ < length ? data[i - 1] : "");        
        //Debug.logError("Field" + i + "[scan code]: " + (i < length ? data[i - 1] : ""), null);
        prod.setproductDepth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
        //Debug.logError("Field" + i + "[Selling price]: " + (i < length ? data[i - 1] : ""), null);
        prod.setshippingDepth(i <= length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
        //Debug.logError("Field" + i + "[Cost price]: " + (i < length ? data[i - 1] : ""), null);
          
        return prod;
    }
}
