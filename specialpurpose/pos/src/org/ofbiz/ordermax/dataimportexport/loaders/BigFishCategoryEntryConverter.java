/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

import com.googlecode.jcsv.writer.CSVEntryConverter;
import java.text.SimpleDateFormat;
import static org.apache.xalan.lib.ExsltDatetime.date;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author BBS Auctions
 */
public class BigFishCategoryEntryConverter implements CSVEntryConverter<BigFishCategory> {

    @Override
    public String[] convertEntry(BigFishCategory cat) {

        Debug.logInfo("productCategoryId : " + cat.productCategoryId, "text");
        String[] columns = new String[10];
        for (int i = 0; i < columns.length; ++i) {
            columns[i] = "";
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        int i = 0;
        try {
            columns[i++] = cat.productCategoryId;
            columns[i++] = cat.parentCategoryId;
            columns[i++] = cat.categoryName;
            columns[i++] = cat.description;
            columns[i++] = cat.longDescription;
            columns[i++] = cat.plpImageName;
            columns[i++] = cat.additionalPlpText;
            columns[i++] = cat.additionalPdpText;
            columns[i++] = cat.fromDate.toString();
            if (cat.thruDate != null) {

//System.out.println(dt1.format(date));
                columns[i++] = dt1.format(cat.thruDate);
                Debug.logInfo("cat.fromDate.toString() : " + dt1.format(cat.thruDate), "text");
            } else {
                columns[i++] = "";
            }

        } catch (Exception e) {
            Debug.logError(e, "Error");
        }
        return columns;
    }

    public class FacetEntryConverter implements CSVEntryConverter<BigFishCategory.Facet> {

        @Override
        public String[] convertEntry(BigFishCategory.Facet cat) {

            Debug.logInfo("productCategoryId : " + cat.productCategoryID, "text");
            String[] columns = new String[10];
            for (int i = 0; i < columns.length; ++i) {
                columns[i] = "";
            }
            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            int i = 0;
            try {
                columns[i++] = cat.facetGroupID;
                columns[i++] = cat.description;
                columns[i++] = cat.productCategoryID;
                columns[i++] = cat.sequenceNumber;
                columns[i++] = cat.tooltip;
                columns[i++] = cat.minDisplay;
                columns[i++] = cat.maxDisplay;
                columns[i++] = cat.fromDate.toString();
                if (cat.thruDate != null) {
                    columns[i++] = dt1.format(cat.thruDate);
                    Debug.logInfo("cat.fromDate.toString() : " + dt1.format(cat.thruDate), "text");
                } else {
                    columns[i++] = "";
                }

            } catch (Exception e) {
                Debug.logError(e, "Error");
            }
            return columns;
        }

    }
    
public class FacetValueConverter implements CSVEntryConverter<BigFishCategory.FacetValue> {

        @Override
        public String[] convertEntry(BigFishCategory.FacetValue cat) {

            Debug.logInfo("facetGroupID : " + cat.facetGroupID, "text");
            String[] columns = new String[10];
            for (int i = 0; i < columns.length; ++i) {
                columns[i] = "";
            }
            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            int i = 0;
            try {
                columns[i++] = cat.facetGroupID;
                columns[i++] = cat.facetValueID;
                columns[i++] = cat.description;
                columns[i++] = cat.sequenceNumber;
                columns[i++] = cat.pLPSwatchURL;
                columns[i++] = cat.pDPSwatchURL;
                columns[i++] = cat.fromDate.toString();
                if (cat.thruDate != null) {
                    columns[i++] = dt1.format(cat.thruDate);
                    Debug.logInfo("cat.fromDate.toString() : " + dt1.format(cat.thruDate), "text");
                } else {
                    columns[i++] = "";
                }

            } catch (Exception e) {
                Debug.logError(e, "Error");
            }
            return columns;
        }

    }
    
}
