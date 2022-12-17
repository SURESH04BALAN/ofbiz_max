/*
 * To change this license header = ""; choose License Headers in Project Properties.
 * To change this template file = ""; choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BBS Auctions
 */
public class BigFishCategory {

    public String productCategoryId = "";
    public String parentCategoryId = "";
    public String categoryName = "";
    public String description = "";
    public String longDescription = "";
    public String plpImageName = "";
    public String additionalPlpText = "";
    public String additionalPdpText = "";
    public java.sql.Timestamp fromDate = null;
    public java.sql.Timestamp thruDate = null;
    public List<Facet> facets = new ArrayList<Facet>();

    public class Facet {

        public String facetGroupID = "";
        public String description = "";
        public String productCategoryID = "";
        public String sequenceNumber = "";
        public String tooltip = "";
        public String minDisplay = "";
        public String maxDisplay = "";
        public java.sql.Timestamp fromDate = null;
        public java.sql.Timestamp thruDate = null;
        public List<FacetValue> facetValues = new ArrayList<FacetValue>();
    }

    public class FacetValue {

        public String facetGroupID = "";
        public String facetValueID = "";
        public String description = "";
        public String sequenceNumber = "";
        public String pLPSwatchURL = "";
        public String pDPSwatchURL = "";
        public java.sql.Timestamp fromDate = null;
        public java.sql.Timestamp thruDate = null;
    }
}
