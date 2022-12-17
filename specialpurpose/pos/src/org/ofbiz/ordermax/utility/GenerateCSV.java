/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author siranjeev
 */
public class GenerateCSV {

    public static void main(String[] args) {
        generateCsvFile("c:\\test.csv");
    }

public static void generateCsvFile(String sFileName, List<Map<String, String>> listMap, List<String> fieldMap) {
        try {
            FileWriter writer = new FileWriter(sFileName);
            boolean first = true;
            for (Map<String, String> map : listMap) {
                //write header
                if (first == true) {
                    for (String fieldName : fieldMap) {
                        writer.append(fieldName);
                        writer.append(',');
                    }
                    writer.append('\n');
                    first = false;
                }
/*
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    writer.append(entry.getValue());
                    writer.append(',');
                }
*/                
                for (String fieldName : fieldMap) {
                    writer.append(map.get(fieldName));
                    writer.append(',');
                }

                writer.append('\n');
            }

	    //generate whatever data you want
            writer.flush();
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateCsvFile(String sFileName, List<Map<String, String>> listMap) {
        try {
            FileWriter writer = new FileWriter(sFileName);
            boolean first = true;
            for (Map<String, String> map : listMap) {
                //write header
                if (first == true) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        writer.append(entry.getKey());
                        writer.append(',');
                    }
                    writer.append('\n');
                    first = false;
                }

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    writer.append(entry.getValue());
                    writer.append(',');
                }
                writer.append('\n');
            }

	    //generate whatever data you want
            writer.flush();
            writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateCsvFile(String sFileName) {
        try {
            FileWriter writer = new FileWriter(sFileName);

            writer.append("DisplayName");
            writer.append(',');
            writer.append("Age");
            writer.append('\n');

            writer.append("MKYONG");
            writer.append(',');
            writer.append("26");
            writer.append('\n');

            writer.append("YOUR NAME");
            writer.append(',');
            writer.append("29");
            writer.append('\n');

	    //generate whatever data you want
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
