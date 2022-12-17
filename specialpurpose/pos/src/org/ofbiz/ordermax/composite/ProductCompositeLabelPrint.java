/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

/**
 *
 * @author siranjeev
 */
public class ProductCompositeLabelPrint {

    private Boolean selected;
    private String productId;
    private String productName;
    private String weightStr;
    private String scanCode;
    
    private Integer selfLife;
    private java.util.Date packingDate;
    private java.util.Date expireDate;

    private String ingredientList ="";
    private String countryOfOrigin = "";
    private String productCategory = "";
   
    private Boolean selectedRootyHill = false;
    private Boolean selectedLiverpool = false;
    private Boolean selectedToongabbie = false;

    private Integer numberOfLabelsRootyHill = 0;
    private Integer numberOfLabelsLiverpool= 0;
    private Integer numberOfLabels= 0;
    
    //private Double priceRootyHill = new Double(0);
    //private Double priceLiverpool= new Double(0);
    private Double price= new Double(0);
    private Double weight = new Double(0);
    
    private ProductComposite productComposite;
    public ProductCompositeLabelPrint() {

    }

    /**
     * @return the selected
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the weightStr
     */
    public String getWeightStr() {
        return weightStr;
    }

    /**
     * @param weightStr the weightStr to set
     */
    public void setWeightStr(String weightStr) {
        this.weightStr = weightStr;
    }

    /**
     * @return the scanCode
     */
    public String getScanCode() {
        return scanCode;
    }

    /**
     * @param scanCode the scanCode to set
     */
    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    /**
     * @return the selfLife
     */
    public Integer getSelfLife() {
        return selfLife;
    }

    /**
     * @param selfLife the selfLife to set
     */
    public void setSelfLife(Integer selfLife) {
        this.selfLife = selfLife;
    }

    /**
     * @return the packingDate
     */
    public java.util.Date getPackingDate() {
        return packingDate;
    }

    /**
     * @param packingDate the packingDate to set
     */
    public void setPackingDate(java.util.Date packingDate) {
        this.packingDate = packingDate;
    }

    /**
     * @return the expireDate
     */
    public java.util.Date getExpireDate() {
        return expireDate;
    }

    /**
     * @param expireDate the expireDate to set
     */
    public void setExpireDate(java.util.Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * @return the ingredientList
     */
    public String getIngredientList() {
        return ingredientList;
    }

    /**
     * @param ingredientList the ingredientList to set
     */
    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    /**
     * @return the countryOfOrigin
     */
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    /**
     * @param countryOfOrigin the countryOfOrigin to set
     */
    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    /**
     * @return the selectedRootyHill
     */
    public Boolean getSelectedRootyHill() {
        return selectedRootyHill;
    }

    /**
     * @param selectedRootyHill the selectedRootyHill to set
     */
    public void setSelectedRootyHill(Boolean selectedRootyHill) {
        this.selectedRootyHill = selectedRootyHill;
    }

    /**
     * @return the selectedLiverpool
     */
    public Boolean getSelectedLiverpool() {
        return selectedLiverpool;
    }

    /**
     * @param selectedLiverpool the selectedLiverpool to set
     */
    public void setSelectedLiverpool(Boolean selectedLiverpool) {
        this.selectedLiverpool = selectedLiverpool;
    }

    /**
     * @return the selectedToongabbie
     */
    public Boolean getSelectedToongabbie() {
        return selectedToongabbie;
    }

    /**
     * @param selectedToongabbie the selectedToongabbie to set
     */
    public void setSelectedToongabbie(Boolean selectedToongabbie) {
        this.selectedToongabbie = selectedToongabbie;
    }

    /**
     * @return the numberOfLabelsRootyHill
     */
    public Integer getNumberOfLabelsRootyHill() {
        return numberOfLabelsRootyHill;
    }

    /**
     * @param numberOfLabelsRootyHill the numberOfLabelsRootyHill to set
     */
    public void setNumberOfLabelsRootyHill(Integer numberOfLabelsRootyHill) {
        this.numberOfLabelsRootyHill = numberOfLabelsRootyHill;
    }

    /**
     * @return the numberOfLabelsLiverpool
     */
    public Integer getNumberOfLabelsLiverpool() {
        return numberOfLabelsLiverpool;
    }

    /**
     * @param numberOfLabelsLiverpool the numberOfLabelsLiverpool to set
     */
    public void setNumberOfLabelsLiverpool(Integer numberOfLabelsLiverpool) {
        this.numberOfLabelsLiverpool = numberOfLabelsLiverpool;
    }

    /**
     * @return the numberOfLabels
     */
    public Integer getNumberOfLabels() {
        return numberOfLabels;
    }

    /**
     * @param numberOfLabelsToongabbie the numberOfLabels to set
     */
    public void setNumberOfLabels(Integer numberOfLabelsToongabbie) {
        this.numberOfLabels = numberOfLabelsToongabbie;
    }

    /**
     * @return the priceRootyHill
     */
   // public Double getPriceRootyHill() {
   //     return priceRootyHill;
   // }

    /**
     * @param priceRootyHill the priceRootyHill to set
     */
    //public void setPriceRootyHill(Double priceRootyHill) {
    //    this.priceRootyHill = priceRootyHill;
    //}

    /**
     * @return the priceLiverpool
     */
    //public Double getPriceLiverpool() {
    //    return priceLiverpool;
    ///}

    /**
     * @param priceLiverpool the priceLiverpool to set
     */
    //public void setPriceLiverpool(Double priceLiverpool) {
    //    this.priceLiverpool = priceLiverpool;
    //}

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param priceToongabbie the price to set
     */
    public void setPrice(Double priceToongabbie) {
        this.price = priceToongabbie;
    }

    /**
     * @return the productComposite
     */
    public ProductComposite getProductComposite() {
        return productComposite;
    }

    /**
     * @param productComposite the productComposite to set
     */
    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
    }

    /**
     * @return the weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getProductCategory() {
        return productCategory;
    }
    
    public void setProductCategory(String str) {
        productCategory = str;
    }    

}
