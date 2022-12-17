/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.pospaneldesigner;

import javax.swing.JButton;

public class PosButton implements Comparable {

    private String word = "";
    private String translation;
    private String textOne;
    private String id;
    private String name = "";
    private String alignment="Center";
    private int x = 0;
    private int y = 0;
    private int w = 0;
    private int h = 0;
    private int row = 0;
    private int col = 0;

    public Boolean isEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }
    private Boolean empty = true;

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
    private String style = "";
    private String content = "";

    private JButton button = null;

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public PosButton() {
    }

    /*    public PosButton(String word, String translation, String textOne, String textTwo) {
     this.word = word;
     this.translation = translation;
     this.textOne = textOne;
     this.textTwo = textTwo;
     }
     */
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTextOne() {
        return textOne;
    }

    public void setTextOne(String textOne) {
        this.textOne = textOne;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Card details:");
        sb.append("\nWord: " + getWord());
        sb.append("\nTranslation: " + getTranslation());
        sb.append("\nTextOne: " + getTextOne());
        sb.append("Button details:");
        sb.append("\nName: " + getName());
        sb.append("\ny: " + getY());
        sb.append("\nx: " + getX());
        if (getId() != null && getId().equals("blank")) {
            sb.append("\n\n");
        } else {
            sb.append("\nId: " + getId() + "\n\n");
        }

        return sb.toString();
    }

    public String shuffledList() {
        return getWord() + " ";
    }

    @Override
    public int compareTo(Object o) {
        double compareY = ((PosButton) o).getY();

        // ascending order
        return (int) (this.y - compareY);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
