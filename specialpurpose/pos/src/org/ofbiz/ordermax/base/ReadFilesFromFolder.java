/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author siranjeev
 */
public class ReadFilesFromFolder {
    
    final public static Path currentRelativePath = Paths.get("");
    public static File folder = new File(currentRelativePath.toAbsolutePath().toString() + "\\specialpurpose\\pos\\src\\org\\ofbiz\\ordermax\\entity" );
    
    String s = currentRelativePath.toAbsolutePath().toString();
    static String temp = "";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("Reading files under the folder " + folder.getAbsolutePath());
        listFilesForFolder(folder);
    }

    public static List listFilesForFolder(final File folder) {
        List list = new ArrayList<String>();
        
        System.out.println("Reading files under the folder " + folder.getAbsolutePath());
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                // System.out.println("Reading files under the folder "+folder.getAbsolutePath());
                listFilesForFolder(fileEntry);
            } else {
                if (fileEntry.isFile()) {
                    temp = fileEntry.getName();
                    if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase()).equals("java")) {
                        System.out.println("File= " + folder.getAbsolutePath() + "\\" + fileEntry.getName());
                        list.add(temp.substring(0,temp.lastIndexOf('.')));
                    }
                }

            }
        }
        return list;
    }
}
