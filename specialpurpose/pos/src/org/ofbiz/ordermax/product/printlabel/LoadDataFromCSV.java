/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.printlabel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.table.ProductCompositePrintLabelTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.ProductCompositeLabelPrint;

/**
 *
 * @author MaxSpice
 */
public class LoadDataFromCSV implements LoadDataFrom {
    
public class ProductCompositeLabelPrintComparator implements Comparator<ProductCompositeLabelPrint> {
    
    /** Creates a new instance of LocaleComparator */
    public ProductCompositeLabelPrintComparator() {
    }
    
    public int compare(ProductCompositeLabelPrint o1, ProductCompositeLabelPrint o2) {
        return o1.getProductName().compareTo(o2.getProductName());
    }
}
    @Override
    public Vector<Object> loadData(String datafile) {
        Vector rows = new Vector<ProductCompositeLabelPrint>();
        try {

            Scanner s = null;
            DecimalFormat df = new DecimalFormat("0.00");

            FileReader fin;

            fin = new FileReader(datafile);
            s = new Scanner(fin);
            int rowCount = 0;
            while (s.hasNextLine()) {
                Vector<Object> row = new Vector<Object>(Arrays.asList(s.nextLine()
                        .split("\\s*,\\s*",
                                -1)));

                if (row.size() > LoadDataFrom.INDEX_TOTAL) {
                    for (int i = LoadDataFrom.INDEX_TOTAL; i < row.size(); i++) {
                        row.remove(i);
                    }

                }
                if (rowCount++ == 0) {
                    continue;
                }
                
                            Debug.logInfo("rowCount:  " + rowCount, "LoadDataFromCSV ");  
                            
                ProductCompositeLabelPrint productComposite = new ProductCompositeLabelPrint();

                productComposite.setSelected((Boolean) getData(ProductCompositePrintLabelTableModel.Columns.SELECTED.getClassName(), row.get(ProductCompositePrintLabelTableModel.Columns.SELECTED.getColumnIndex())));
                productComposite.setProductId((String) getData(ProductCompositePrintLabelTableModel.Columns.PRODUCTID.getClassName(), row.get(LoadDataFrom.PRODUCTID)));
                productComposite.setProductName((String) getData(ProductCompositePrintLabelTableModel.Columns.PRODUCTNAME.getClassName(), row.get(LoadDataFrom.PRODUCTNAME)));
                productComposite.setWeight((Double) getData(ProductCompositePrintLabelTableModel.Columns.WEIGHT.getClassName(), row.get(LoadDataFrom.WEIGHT)));                
                productComposite.setWeightStr((String) getData(ProductCompositePrintLabelTableModel.Columns.WEIGHTSTR.getClassName(), row.get(LoadDataFrom.WEIGHTSTR)));
                productComposite.setScanCode((String) getData(ProductCompositePrintLabelTableModel.Columns.SCANCODE.getClassName(), row.get(LoadDataFrom.SCANCODE)));
                productComposite.setSelfLife((Integer) getData(ProductCompositePrintLabelTableModel.Columns.SHELFLIFE.getClassName(), row.get(LoadDataFrom.SHELFLIFE)));
                productComposite.setPackingDate((java.util.Date) getData(ProductCompositePrintLabelTableModel.Columns.PACKINGDATE.getClassName(), row.get(LoadDataFrom.PACKINGDATE)));
                productComposite.setExpireDate((java.util.Date) getData(ProductCompositePrintLabelTableModel.Columns.EXPIREDATE.getClassName(), row.get(LoadDataFrom.EXPIREDATE)));
                productComposite.setIngredientList((String) getData(ProductCompositePrintLabelTableModel.Columns.INGREDIENTLIST.getClassName(), row.get(LoadDataFrom.INGREDIENTLIST)));
                productComposite.setCountryOfOrigin((String) getData(ProductCompositePrintLabelTableModel.Columns.COUNTRYOFORIGIN.getClassName(), row.get(LoadDataFrom.COUNTRYOFORIGIN)));
//                productComposite.setSelectedRootyHill((Boolean) getData(ProductCompositePrintLabelTableModel.Columns.SELECTEDROOTYHILL.getClassName(), row.get(LoadDataFrom.SELECTEDROOTYHILL)));
//                productComposite.setPriceRootyHill((Double) getData(ProductCompositePrintLabelTableModel.Columns.PRICEROOTYHILL.getClassName(), row.get(LoadDataFrom.PRICEROOTYHILL)));
//                productComposite.setNumberOfLabelsRootyHill((Integer) getData(ProductCompositePrintLabelTableModel.Columns.NUMBEROFLABELSROOTYHILL.getClassName(), row.get(LoadDataFrom.NUMBEROFLABELSROOTYHILL)));
//                productComposite.setSelectedLiverpool((Boolean) getData(ProductCompositePrintLabelTableModel.Columns.SELECTEDLIVERPOOL.getClassName(), row.get(LoadDataFrom.SELECTEDLIVERPOOL)));
//                productComposite.setPriceLiverpool((Double) getData(ProductCompositePrintLabelTableModel.Columns.PRICELIVERPOOL.getClassName(), row.get(LoadDataFrom.PRICELIVERPOOL)));
//                productComposite.setNumberOfLabelsLiverpool((Integer) getData(ProductCompositePrintLabelTableModel.Columns.NUMBEROFLABELSLIVERPOOL.getClassName(), row.get(LoadDataFrom.NUMBEROFLABELSLIVERPOOL)));
//                productComposite.setSelectedToongabbie((Boolean) getData(ProductCompositePrintLabelTableModel.Columns.SELECTEDTOONGABBIE.getClassName(), row.get(LoadDataFrom.SELECTEDTOONGABBIE)));
                productComposite.setProductCategory((String)getData(ProductCompositePrintLabelTableModel.Columns.PRODUCTCATEGORY.getClassName(), row.get(LoadDataFrom.PRODUCTCATEGORY)));
                productComposite.setPrice((Double) getData(ProductCompositePrintLabelTableModel.Columns.PRICE.getClassName(), row.get(LoadDataFrom.PRICE)));
                rows.add(productComposite);
            }
            
            Collections.sort(rows, new ProductCompositeLabelPrintComparator());
            
            try {
                fin.close();

            } catch (IOException ex) {
                Logger.getLogger(LoadDataFromCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadDataFromCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }

    private Object getData(Class className, Object value) {

        if (java.util.Date.class.equals(className)) {
            if (value instanceof String) {
                String strVal = (String) value;
                if (!strVal.isEmpty()) {
                    java.util.Date date1 = new java.util.Date();
                    try {
                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse(strVal);
                    } catch (ParseException ex) {
                        Logger.getLogger(LoadDataFromCSV.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return date1;
                } else {
                    return new java.util.Date();
                }
            } else {
                return new java.util.Date();
            }
        } else if (String.class.equals(className)) {
            if (value != null) {
                return value;
            } else {
                return " ";
            }
        } else if (Double.class.equals(className)) {
            if (value instanceof String) {
                String strVal = (String) value;
                if (!strVal.isEmpty()) {
                    return Double.valueOf(strVal);
                } else {
                    return new Double(0); //value;
                }
            } else {
                return new Double(0);
            }
        } else if (Integer.class.equals(className)) {
            if (value instanceof String) {
                String strVal = (String) value;
                if (!strVal.isEmpty()) {

                    strVal.trim();
                    if (isInteger(strVal)) {
                        return Integer.parseInt(strVal.trim());
                    } else {
                        return 0; //value;
                    }
                } else {
                    return 0; //value;
                }
            } else {
                return 0;
            }
        } else if (Boolean.class.equals(className)) {
            if (value != null) {
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (strVal.toLowerCase().contains("true")) {
                        return true;
                    } else {
                        return false; //value;
                    }
                }
            } else {
                return false;
            }
        }

        return " ";
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
