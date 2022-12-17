/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.printlabel;

import java.util.Vector;

/**
 *
 * @author MaxSpice
 */
public interface LoadDataFrom {
        public static int SELECTED = 0;
        public static int PRODUCTID = 1;
        public static int PRODUCTNAME = 2;
        public static int WEIGHT  = 3;
        public static int WEIGHTSTR = 4;
        public static int SCANCODE = 5;
        public static int SHELFLIFE = 6;
        public static int PACKINGDATE = 7;
        public static int EXPIREDATE = 8;
        public static int INGREDIENTLIST = 9;
        public static int COUNTRYOFORIGIN = 10;
        public static int PRODUCTCATEGORY = 11; 
        public static int PRICE = 12;        
/*        public static int SELECTEDROOTYHILL = 11;
        public static int PRICEROOTYHILL = 12;
        public static int NUMBEROFLABELSROOTYHILL = 13;
        public static int SELECTEDLIVERPOOL = 14;
        public static int PRICELIVERPOOL = 15;
        public static int NUMBEROFLABELSLIVERPOOL = 16;
        public static int SELECTEDTOONGABBIE = 17;
        public static int PRICETOONGABBIE = 18;
        public static int NUMBEROFLABELSTOONGABBIE = 19;
       */
        public static int INDEX_TOTAL =  PRICE+1;
//    Vector<Vector<Object>> loadData(String in, UserData data);
    Vector<Object> loadData(String in);    
    
}
