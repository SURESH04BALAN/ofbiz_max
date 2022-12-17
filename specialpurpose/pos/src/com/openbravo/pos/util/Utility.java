/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author PospointAus
 */
public class Utility {

    static public ImageIcon loadImage(String filePath) {

        String path = null;
//        Debug.logInfo("is data file name" + filePath, module);
        File f = new File(filePath);
        if (f.isAbsolute() == false) {

            String s = getAbsoluteImagePath();
//            s = s.concat("\\framework\\images\\webapp");
//            Debug.logInfo("is data file name s: " + s, module);
            f = new File(s + filePath);

        }
        ImageIcon icon = null;
        BufferedImage img = null;

        try {
            if (f.exists()) { /* do something */

                //icon = new ImageIcon(OrderMaxUtility.getImage(fileName));//prod.getString("smallImageUrl")));

                URL url = f.toURI().toURL();
                img = ImageIO.read(url);
                icon = new ImageIcon(img);
            } else {
                //              Debug.logInfo("file doesb't exists to load Image file name" + f.getAbsolutePath(), module);
            }
        } catch (Exception e) {
            Debug.logInfo("unable to load Image file name" + f.getAbsolutePath(), "module");
//            Debug.logError(e,"module");
        }

        return icon;
    }

    static public String getAbsoluteImagePath() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        s = s.concat("/specialpurpose/pos/src");

//            C:\backup\ofbiz-12.04.02\framework\images\webapp\images\products
        Debug.logInfo("is data file name s: " + s, "module");
        return s;
    }
}
