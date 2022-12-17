/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens;

/**
 *
 * @author siranjeev
 */
import javax.swing.ImageIcon;

public class IconFetch {

    private static IconFetch instance;

    private IconFetch(){
    }

    public static IconFetch getInstance() {
        if (instance == null)
            instance = new IconFetch();
        return instance;
    }

    public ImageIcon getIcon(String iconName) {
        java.net.URL imgUrl = getClass().getResource(iconName);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            throw new IllegalArgumentException("This icon file does not exist");
        }
    }

    public static final String MINESWEEPER_ONE = "C:/AuthLog/productIconsmall.png";
}