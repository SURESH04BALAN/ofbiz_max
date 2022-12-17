/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

/**
 *
 * @author administrator
 */
public class PosButtonStyles {
    static String Style = "style";
    static String Value = "value";
    static String ColorBack = "color_back"; //value="000000"/>
    static String ColorFore = "color_fore";//value="000000"/>
    static String FontFace = "font_face";//value="verdana"/>
    static String FontSize = "font_size";//<font_size value="10"/>
    static String FontWeight = "font_weight";//<font_weight value="0"/>
    static String FontItalic = "font_italic";//value="0"/>    
    
    private String styleName = "";
    private String colorBack = "000000";
    private String colorFore = "000000";
    private String fontFace = "verdana";
    private String fontSize = "10";
    private String fontWeight = "0";
    private String fontItalic = "0";    

    public String getColorBack() {
        return colorBack;
    }

    public void setColorBack(String colorBack) {
        this.colorBack = colorBack;
    }

    public String getColorFore() {
        return colorFore;
    }

    public void setColorFore(String colorFore) {
        this.colorFore = colorFore;
    }

    public String getFontFace() {
        return fontFace;
    }

    public void setFontFace(String fontFace) {
        this.fontFace = fontFace;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getFontItalic() {
        return fontItalic;
    }

    public void setFontItalic(String fontItalic) {
        this.fontItalic = fontItalic;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

}
