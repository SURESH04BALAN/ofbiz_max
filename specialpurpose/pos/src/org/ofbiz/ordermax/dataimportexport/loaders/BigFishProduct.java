/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

/**
 *
 * @author BBS Auctions
 */
public class BigFishProduct {

    public String Master_Product_ID = "";
    public String Product_ID = "";
    public String Product_Category_ID = "";
    public String Brand = "";
    public String Catrgory = "";
    public String virtual = "";
    public String Product_CODE = "";
    public String BRAND_FULL = "";
    public String Catrgory_Full = "";
    public String Internal_Name = "";
    public String Product_Name = "";
    public String Sales_Pitch = "";
    public String Long_Description = "";
    public String Special_Instr = "";
    public String Delivery_Info = "";
    public String Directions = "";
    public String Terms_Cond = "";
    public String Ingredients = "";
    public String Warnings = "";
    public String PLP_Label = "";
    public String PDP_Label = "";
    public String List_Price = "";
    public String Sales_Price = "";
    public String Selectable_Features_1 = "";
    public String PLP_Swatch_Image = "";
    public String PDP_Swatch_Image = "";
    public String Selectable_Features_2 = "";
    public String Selectable_Features_3 = "";
    public String Selectable_Features_4 = "";
    public String Selectable_Features_5 = "";
    public String Descriptive_Features_1 = "";
    public String Descriptive_Features_2 = "";
    public String Descriptive_Features_3 = "";
    public String Descriptive_Features_4 = "";
    public String Descriptive_Features_5 = "";
    public String PLP_Image = "";
    public String PLP_Image_Alt = "";
    public String PDP_Thumbnail_Image = "";
    public String PDP_Regular_Image = "";
    public String PDP_Large_Image = "";
    public String PDP_Alt_1_Thumbnail_Image = "";
    public String PDP_Alt_1_Regular_Image = "";
    public String PDP_Alt_1_Large_Image = "";
    public String PDP_Alt_2_Thumbnail_Image = "";
    public String PDP_Alt_2_Regular_Image = "";
    public String PDP_Alt_2_Large_Image = "";
    public String PDP_Alt_3_Thumbnail_Image = "";
    public String PDP_Alt_3_Regular_Image = "";
    public String PDP_Alt_3_Large_Image = "";
    public String PDP_Alt_4_Thumbnail_Image = "";
    public String PDP_Alt_4_Regular_Image = "";
    public String PDP_Alt_4_Large_Image = "";
    public String PDP_Alt_5_Thumbnail_Image = "";
    public String PDP_Alt_5_Regular_Image = "";
    public String PDP_Alt_5_Large_Image = "";
    public String PDP_Alt_6_Thumbnail_Image = "";
    public String PDP_Alt_6_Regular_Image = "";
    public String PDP_Alt_6_Large_Image = "";
    public String PDP_Alt_7_Thumbnail_Image = "";
    public String PDP_Alt_7_Regular_Image = "";
    public String PDP_Alt_7_Large_Image = "";
    public String PDP_Alt_8_Thumbnail_Image = "";
    public String PDP_Alt_8_Regular_Image = "";
    public String PDP_Alt_8_Large_Image = "";
    public String PDP_Alt_9_Thumbnail_Image = "";
    public String PDP_Alt_9_Regular_Image = "";
    public String PDP_Alt_9_Large_Image = "";
    public String PDP_Alt_10_Thumbnail_Image = "";
    public String PDP_Alt_10_Regular_Image = "";
    public String PDP_Alt_10_Large_Image = "";
    public String Product_Height = "";
    public String Product_Width = "";
    public String Product_Depth = "";
    public String Returnable = "";
    public String Taxable = "";
    public String Charge_Shipping = "";
    public String Introduction_Date = "";
    public String Discontinued_Date = "";
    public String Manufacturer_ID = "";
    public String SKU = "";
    public String Google_ID = "";
    public String ISBN = "";
    public String Manufacturer_Number = "";
    public String Product_Video = "";
    public String Product_360_Video = "";
    public String Sequence_Number = "";
    public String BF_Inventory_Total = "";
    public String BF_Inventory_Warehouse = "";
    public String PDP_Select_Multi_Variant = "";
    public String Product_Weight = "";
    public String Check_Out_Gift_Message = "";
    public String PDP_Min_Quantity = "";
    public String PDP_Max_Quantity = "";
    public String PDP_Default_Quantity = "";
    public String PDP_In_Store_Only = "";

    void setProduct(String master_Product_ID, String productId, String product_Category_ID, org.ofbiz.ordermax.entity.Product product){
     Master_Product_ID = master_Product_ID;
     Product_ID = productId;
     Product_Category_ID = product_Category_ID;
     Brand = product.getbrandName();
     //Catrgory = product;
     virtual = product.getisVirtual();
     Product_CODE = productId;
     BRAND_FULL = product.getbrandName();
//     Catrgory_Full = product;
     Internal_Name = productId;
     Product_Name = product.getproductName();
//     Sales_Pitch = product;
     Long_Description = product.getlongDescription();
//     Special_Instr = product;
//     Delivery_Info = product;
     Directions = product.getbrandName();
//     Terms_Cond = product;
//     Ingredients = product;
     Warnings = product.getbrandName();
//     PLP_Label = product;
//     PDP_Label = product;
     List_Price = product.getproductDepth().toString();
     Sales_Price = product.getproductDepth().toString();
/*     Selectable_Features_1 = product;
     PLP_Swatch_Image = product;
     PDP_Swatch_Image = product;
     Selectable_Features_2 = product;
     Selectable_Features_3 = product;
     Selectable_Features_4 = product;
     Selectable_Features_5 = product;
     Descriptive_Features_1 = product;
     Descriptive_Features_2 = product;
     Descriptive_Features_3 = product;
     Descriptive_Features_4 = product;
     Descriptive_Features_5 = product;*/
     PLP_Image = product.getoriginalImageUrl();
     PLP_Image_Alt = "";
     PDP_Thumbnail_Image = "";
     PDP_Regular_Image = product.getdetailImageUrl();
     PDP_Large_Image = product.getlargeImageUrl();
     /*
     PDP_Alt_1_Thumbnail_Image = product;
     PDP_Alt_1_Regular_Image = product;
     PDP_Alt_1_Large_Image = product;
     PDP_Alt_2_Thumbnail_Image = product;
     PDP_Alt_2_Regular_Image = product;
     PDP_Alt_2_Large_Image = product;
     PDP_Alt_3_Thumbnail_Image = product;
     PDP_Alt_3_Regular_Image = product;
     PDP_Alt_3_Large_Image = product;
     PDP_Alt_4_Thumbnail_Image = product;
     PDP_Alt_4_Regular_Image = product;
     PDP_Alt_4_Large_Image = product;
     PDP_Alt_5_Thumbnail_Image = product;
     PDP_Alt_5_Regular_Image = product;
     PDP_Alt_5_Large_Image = product;
     PDP_Alt_6_Thumbnail_Image = product;
     PDP_Alt_6_Regular_Image = product;
     PDP_Alt_6_Large_Image = product;
     PDP_Alt_7_Thumbnail_Image = product;
     PDP_Alt_7_Regular_Image = product;
     PDP_Alt_7_Large_Image = product;
     PDP_Alt_8_Thumbnail_Image = product;
     PDP_Alt_8_Regular_Image = product;
     PDP_Alt_8_Large_Image = product;
     PDP_Alt_9_Thumbnail_Image = product;
     PDP_Alt_9_Regular_Image = product;
     PDP_Alt_9_Large_Image = product;
     PDP_Alt_10_Thumbnail_Image = product;
     PDP_Alt_10_Regular_Image = product;
     PDP_Alt_10_Large_Image = product;
     Product_Height = product;
     Product_Width = product;
     Product_Depth = product;
     Returnable = product;
     Taxable = product;
     Charge_Shipping = product;
     Introduction_Date = product;
     Discontinued_Date = product;
     Manufacturer_ID = product;
     SKU = product;
     Google_ID = product;
     ISBN = product;
     Manufacturer_Number = product;
     Product_Video = product;
     Product_360_Video = product;*/
     Sequence_Number = "1000";
     BF_Inventory_Total = "100";
     BF_Inventory_Warehouse = "100";
 /*    Product_Weight = product;
     Check_Out_Gift_Message = product;
     PDP_Min_Quantity = product;
     PDP_Max_Quantity = product;
     PDP_Default_Quantity = product;
     PDP_In_Store_Only = product;
*/        
    }
    public String getMaster_Product_ID() {
        return Master_Product_ID;
    }

    public void setMaster_Product_ID(String Master_Product_ID) {
        this.Master_Product_ID = Master_Product_ID;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String Product_ID) {
        this.Product_ID = Product_ID;
    }

    public String getProduct_Category_ID() {
        return Product_Category_ID;
    }

    public void setProduct_Category_ID(String Product_Category_ID) {
        this.Product_Category_ID = Product_Category_ID;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public String getCatrgory() {
        return Catrgory;
    }

    public void setCatrgory(String Catrgory) {
        this.Catrgory = Catrgory;
    }

    public String getVirtual() {
        return virtual;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }

    public String getProduct_CODE() {
        return Product_CODE;
    }

    public void setProduct_CODE(String Product_CODE) {
        this.Product_CODE = Product_CODE;
    }

    public String getBRAND_FULL() {
        return BRAND_FULL;
    }

    public void setBRAND_FULL(String BRAND_FULL) {
        this.BRAND_FULL = BRAND_FULL;
    }

    public String getCatrgory_Full() {
        return Catrgory_Full;
    }

    public void setCatrgory_Full(String Catrgory_Full) {
        this.Catrgory_Full = Catrgory_Full;
    }

    public String getInternal_Name() {
        return Internal_Name;
    }

    public void setInternal_Name(String Internal_Name) {
        this.Internal_Name = Internal_Name;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String Product_Name) {
        this.Product_Name = Product_Name;
    }

    public String getSales_Pitch() {
        return Sales_Pitch;
    }

    public void setSales_Pitch(String Sales_Pitch) {
        this.Sales_Pitch = Sales_Pitch;
    }

    public String getLong_Description() {
        return Long_Description;
    }

    public void setLong_Description(String Long_Description) {
        this.Long_Description = Long_Description;
    }

    public String getSpecial_Instr() {
        return Special_Instr;
    }

    public void setSpecial_Instr(String Special_Instr) {
        this.Special_Instr = Special_Instr;
    }

    public String getDelivery_Info() {
        return Delivery_Info;
    }

    public void setDelivery_Info(String Delivery_Info) {
        this.Delivery_Info = Delivery_Info;
    }

    public String getDirections() {
        return Directions;
    }

    public void setDirections(String Directions) {
        this.Directions = Directions;
    }

    public String getTerms_Cond() {
        return Terms_Cond;
    }

    public void setTerms_Cond(String Terms_Cond) {
        this.Terms_Cond = Terms_Cond;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String Ingredients) {
        this.Ingredients = Ingredients;
    }

    public String getWarnings() {
        return Warnings;
    }

    public void setWarnings(String Warnings) {
        this.Warnings = Warnings;
    }

    public String getPLP_Label() {
        return PLP_Label;
    }

    public void setPLP_Label(String PLP_Label) {
        this.PLP_Label = PLP_Label;
    }

    public String getPDP_Label() {
        return PDP_Label;
    }

    public void setPDP_Label(String PDP_Label) {
        this.PDP_Label = PDP_Label;
    }

    public String getList_Price() {
        return List_Price;
    }

    public void setList_Price(String List_Price) {
        this.List_Price = List_Price;
    }

    public String getSales_Price() {
        return Sales_Price;
    }

    public void setSales_Price(String Sales_Price) {
        this.Sales_Price = Sales_Price;
    }

    public String getSelectable_Features_1() {
        return Selectable_Features_1;
    }

    public void setSelectable_Features_1(String Selectable_Features_1) {
        this.Selectable_Features_1 = Selectable_Features_1;
    }

    public String getPLP_Swatch_Image() {
        return PLP_Swatch_Image;
    }

    public void setPLP_Swatch_Image(String PLP_Swatch_Image) {
        this.PLP_Swatch_Image = PLP_Swatch_Image;
    }

    public String getPDP_Swatch_Image() {
        return PDP_Swatch_Image;
    }

    public void setPDP_Swatch_Image(String PDP_Swatch_Image) {
        this.PDP_Swatch_Image = PDP_Swatch_Image;
    }

    public String getSelectable_Features_2() {
        return Selectable_Features_2;
    }

    public void setSelectable_Features_2(String Selectable_Features_2) {
        this.Selectable_Features_2 = Selectable_Features_2;
    }

    public String getSelectable_Features_3() {
        return Selectable_Features_3;
    }

    public void setSelectable_Features_3(String Selectable_Features_3) {
        this.Selectable_Features_3 = Selectable_Features_3;
    }

    public String getSelectable_Features_4() {
        return Selectable_Features_4;
    }

    public void setSelectable_Features_4(String Selectable_Features_4) {
        this.Selectable_Features_4 = Selectable_Features_4;
    }

    public String getSelectable_Features_5() {
        return Selectable_Features_5;
    }

    public void setSelectable_Features_5(String Selectable_Features_5) {
        this.Selectable_Features_5 = Selectable_Features_5;
    }

    public String getDescriptive_Features_1() {
        return Descriptive_Features_1;
    }

    public void setDescriptive_Features_1(String Descriptive_Features_1) {
        this.Descriptive_Features_1 = Descriptive_Features_1;
    }

    public String getDescriptive_Features_2() {
        return Descriptive_Features_2;
    }

    public void setDescriptive_Features_2(String Descriptive_Features_2) {
        this.Descriptive_Features_2 = Descriptive_Features_2;
    }

    public String getDescriptive_Features_3() {
        return Descriptive_Features_3;
    }

    public void setDescriptive_Features_3(String Descriptive_Features_3) {
        this.Descriptive_Features_3 = Descriptive_Features_3;
    }

    public String getDescriptive_Features_4() {
        return Descriptive_Features_4;
    }

    public void setDescriptive_Features_4(String Descriptive_Features_4) {
        this.Descriptive_Features_4 = Descriptive_Features_4;
    }

    public String getDescriptive_Features_5() {
        return Descriptive_Features_5;
    }

    public void setDescriptive_Features_5(String Descriptive_Features_5) {
        this.Descriptive_Features_5 = Descriptive_Features_5;
    }

    public String getPLP_Image() {
        return PLP_Image;
    }

    public void setPLP_Image(String PLP_Image) {
        this.PLP_Image = PLP_Image;
    }

    public String getPLP_Image_Alt() {
        return PLP_Image_Alt;
    }

    public void setPLP_Image_Alt(String PLP_Image_Alt) {
        this.PLP_Image_Alt = PLP_Image_Alt;
    }

    public String getPDP_Thumbnail_Image() {
        return PDP_Thumbnail_Image;
    }

    public void setPDP_Thumbnail_Image(String PDP_Thumbnail_Image) {
        this.PDP_Thumbnail_Image = PDP_Thumbnail_Image;
    }

    public String getPDP_Regular_Image() {
        return PDP_Regular_Image;
    }

    public void setPDP_Regular_Image(String PDP_Regular_Image) {
        this.PDP_Regular_Image = PDP_Regular_Image;
    }

    public String getPDP_Large_Image() {
        return PDP_Large_Image;
    }

    public void setPDP_Large_Image(String PDP_Large_Image) {
        this.PDP_Large_Image = PDP_Large_Image;
    }

    public String getPDP_Alt_1_Thumbnail_Image() {
        return PDP_Alt_1_Thumbnail_Image;
    }

    public void setPDP_Alt_1_Thumbnail_Image(String PDP_Alt_1_Thumbnail_Image) {
        this.PDP_Alt_1_Thumbnail_Image = PDP_Alt_1_Thumbnail_Image;
    }

    public String getPDP_Alt_1_Regular_Image() {
        return PDP_Alt_1_Regular_Image;
    }

    public void setPDP_Alt_1_Regular_Image(String PDP_Alt_1_Regular_Image) {
        this.PDP_Alt_1_Regular_Image = PDP_Alt_1_Regular_Image;
    }

    public String getPDP_Alt_1_Large_Image() {
        return PDP_Alt_1_Large_Image;
    }

    public void setPDP_Alt_1_Large_Image(String PDP_Alt_1_Large_Image) {
        this.PDP_Alt_1_Large_Image = PDP_Alt_1_Large_Image;
    }

    public String getPDP_Alt_2_Thumbnail_Image() {
        return PDP_Alt_2_Thumbnail_Image;
    }

    public void setPDP_Alt_2_Thumbnail_Image(String PDP_Alt_2_Thumbnail_Image) {
        this.PDP_Alt_2_Thumbnail_Image = PDP_Alt_2_Thumbnail_Image;
    }

    public String getPDP_Alt_2_Regular_Image() {
        return PDP_Alt_2_Regular_Image;
    }

    public void setPDP_Alt_2_Regular_Image(String PDP_Alt_2_Regular_Image) {
        this.PDP_Alt_2_Regular_Image = PDP_Alt_2_Regular_Image;
    }

    public String getPDP_Alt_2_Large_Image() {
        return PDP_Alt_2_Large_Image;
    }

    public void setPDP_Alt_2_Large_Image(String PDP_Alt_2_Large_Image) {
        this.PDP_Alt_2_Large_Image = PDP_Alt_2_Large_Image;
    }

    public String getPDP_Alt_3_Thumbnail_Image() {
        return PDP_Alt_3_Thumbnail_Image;
    }

    public void setPDP_Alt_3_Thumbnail_Image(String PDP_Alt_3_Thumbnail_Image) {
        this.PDP_Alt_3_Thumbnail_Image = PDP_Alt_3_Thumbnail_Image;
    }

    public String getPDP_Alt_3_Regular_Image() {
        return PDP_Alt_3_Regular_Image;
    }

    public void setPDP_Alt_3_Regular_Image(String PDP_Alt_3_Regular_Image) {
        this.PDP_Alt_3_Regular_Image = PDP_Alt_3_Regular_Image;
    }

    public String getPDP_Alt_3_Large_Image() {
        return PDP_Alt_3_Large_Image;
    }

    public void setPDP_Alt_3_Large_Image(String PDP_Alt_3_Large_Image) {
        this.PDP_Alt_3_Large_Image = PDP_Alt_3_Large_Image;
    }

    public String getPDP_Alt_4_Thumbnail_Image() {
        return PDP_Alt_4_Thumbnail_Image;
    }

    public void setPDP_Alt_4_Thumbnail_Image(String PDP_Alt_4_Thumbnail_Image) {
        this.PDP_Alt_4_Thumbnail_Image = PDP_Alt_4_Thumbnail_Image;
    }

    public String getPDP_Alt_4_Regular_Image() {
        return PDP_Alt_4_Regular_Image;
    }

    public void setPDP_Alt_4_Regular_Image(String PDP_Alt_4_Regular_Image) {
        this.PDP_Alt_4_Regular_Image = PDP_Alt_4_Regular_Image;
    }

    public String getPDP_Alt_4_Large_Image() {
        return PDP_Alt_4_Large_Image;
    }

    public void setPDP_Alt_4_Large_Image(String PDP_Alt_4_Large_Image) {
        this.PDP_Alt_4_Large_Image = PDP_Alt_4_Large_Image;
    }

    public String getPDP_Alt_5_Thumbnail_Image() {
        return PDP_Alt_5_Thumbnail_Image;
    }

    public void setPDP_Alt_5_Thumbnail_Image(String PDP_Alt_5_Thumbnail_Image) {
        this.PDP_Alt_5_Thumbnail_Image = PDP_Alt_5_Thumbnail_Image;
    }

    public String getPDP_Alt_5_Regular_Image() {
        return PDP_Alt_5_Regular_Image;
    }

    public void setPDP_Alt_5_Regular_Image(String PDP_Alt_5_Regular_Image) {
        this.PDP_Alt_5_Regular_Image = PDP_Alt_5_Regular_Image;
    }

    public String getPDP_Alt_5_Large_Image() {
        return PDP_Alt_5_Large_Image;
    }

    public void setPDP_Alt_5_Large_Image(String PDP_Alt_5_Large_Image) {
        this.PDP_Alt_5_Large_Image = PDP_Alt_5_Large_Image;
    }

    public String getPDP_Alt_6_Thumbnail_Image() {
        return PDP_Alt_6_Thumbnail_Image;
    }

    public void setPDP_Alt_6_Thumbnail_Image(String PDP_Alt_6_Thumbnail_Image) {
        this.PDP_Alt_6_Thumbnail_Image = PDP_Alt_6_Thumbnail_Image;
    }

    public String getPDP_Alt_6_Regular_Image() {
        return PDP_Alt_6_Regular_Image;
    }

    public void setPDP_Alt_6_Regular_Image(String PDP_Alt_6_Regular_Image) {
        this.PDP_Alt_6_Regular_Image = PDP_Alt_6_Regular_Image;
    }

    public String getPDP_Alt_6_Large_Image() {
        return PDP_Alt_6_Large_Image;
    }

    public void setPDP_Alt_6_Large_Image(String PDP_Alt_6_Large_Image) {
        this.PDP_Alt_6_Large_Image = PDP_Alt_6_Large_Image;
    }

    public String getPDP_Alt_7_Thumbnail_Image() {
        return PDP_Alt_7_Thumbnail_Image;
    }

    public void setPDP_Alt_7_Thumbnail_Image(String PDP_Alt_7_Thumbnail_Image) {
        this.PDP_Alt_7_Thumbnail_Image = PDP_Alt_7_Thumbnail_Image;
    }

    public String getPDP_Alt_7_Regular_Image() {
        return PDP_Alt_7_Regular_Image;
    }

    public void setPDP_Alt_7_Regular_Image(String PDP_Alt_7_Regular_Image) {
        this.PDP_Alt_7_Regular_Image = PDP_Alt_7_Regular_Image;
    }

    public String getPDP_Alt_7_Large_Image() {
        return PDP_Alt_7_Large_Image;
    }

    public void setPDP_Alt_7_Large_Image(String PDP_Alt_7_Large_Image) {
        this.PDP_Alt_7_Large_Image = PDP_Alt_7_Large_Image;
    }

    public String getPDP_Alt_8_Thumbnail_Image() {
        return PDP_Alt_8_Thumbnail_Image;
    }

    public void setPDP_Alt_8_Thumbnail_Image(String PDP_Alt_8_Thumbnail_Image) {
        this.PDP_Alt_8_Thumbnail_Image = PDP_Alt_8_Thumbnail_Image;
    }

    public String getPDP_Alt_8_Regular_Image() {
        return PDP_Alt_8_Regular_Image;
    }

    public void setPDP_Alt_8_Regular_Image(String PDP_Alt_8_Regular_Image) {
        this.PDP_Alt_8_Regular_Image = PDP_Alt_8_Regular_Image;
    }

    public String getPDP_Alt_8_Large_Image() {
        return PDP_Alt_8_Large_Image;
    }

    public void setPDP_Alt_8_Large_Image(String PDP_Alt_8_Large_Image) {
        this.PDP_Alt_8_Large_Image = PDP_Alt_8_Large_Image;
    }

    public String getPDP_Alt_9_Thumbnail_Image() {
        return PDP_Alt_9_Thumbnail_Image;
    }

    public void setPDP_Alt_9_Thumbnail_Image(String PDP_Alt_9_Thumbnail_Image) {
        this.PDP_Alt_9_Thumbnail_Image = PDP_Alt_9_Thumbnail_Image;
    }

    public String getPDP_Alt_9_Regular_Image() {
        return PDP_Alt_9_Regular_Image;
    }

    public void setPDP_Alt_9_Regular_Image(String PDP_Alt_9_Regular_Image) {
        this.PDP_Alt_9_Regular_Image = PDP_Alt_9_Regular_Image;
    }

    public String getPDP_Alt_9_Large_Image() {
        return PDP_Alt_9_Large_Image;
    }

    public void setPDP_Alt_9_Large_Image(String PDP_Alt_9_Large_Image) {
        this.PDP_Alt_9_Large_Image = PDP_Alt_9_Large_Image;
    }

    public String getPDP_Alt_10_Thumbnail_Image() {
        return PDP_Alt_10_Thumbnail_Image;
    }

    public void setPDP_Alt_10_Thumbnail_Image(String PDP_Alt_10_Thumbnail_Image) {
        this.PDP_Alt_10_Thumbnail_Image = PDP_Alt_10_Thumbnail_Image;
    }

    public String getPDP_Alt_10_Regular_Image() {
        return PDP_Alt_10_Regular_Image;
    }

    public void setPDP_Alt_10_Regular_Image(String PDP_Alt_10_Regular_Image) {
        this.PDP_Alt_10_Regular_Image = PDP_Alt_10_Regular_Image;
    }

    public String getPDP_Alt_10_Large_Image() {
        return PDP_Alt_10_Large_Image;
    }

    public void setPDP_Alt_10_Large_Image(String PDP_Alt_10_Large_Image) {
        this.PDP_Alt_10_Large_Image = PDP_Alt_10_Large_Image;
    }

    public String getProduct_Height() {
        return Product_Height;
    }

    public void setProduct_Height(String Product_Height) {
        this.Product_Height = Product_Height;
    }

    public String getProduct_Width() {
        return Product_Width;
    }

    public void setProduct_Width(String Product_Width) {
        this.Product_Width = Product_Width;
    }

    public String getProduct_Depth() {
        return Product_Depth;
    }

    public void setProduct_Depth(String Product_Depth) {
        this.Product_Depth = Product_Depth;
    }

    public String getReturnable() {
        return Returnable;
    }

    public void setReturnable(String Returnable) {
        this.Returnable = Returnable;
    }

    public String getTaxable() {
        return Taxable;
    }

    public void setTaxable(String Taxable) {
        this.Taxable = Taxable;
    }

    public String getCharge_Shipping() {
        return Charge_Shipping;
    }

    public void setCharge_Shipping(String Charge_Shipping) {
        this.Charge_Shipping = Charge_Shipping;
    }

    public String getIntroduction_Date() {
        return Introduction_Date;
    }

    public void setIntroduction_Date(String Introduction_Date) {
        this.Introduction_Date = Introduction_Date;
    }

    public String getDiscontinued_Date() {
        return Discontinued_Date;
    }

    public void setDiscontinued_Date(String Discontinued_Date) {
        this.Discontinued_Date = Discontinued_Date;
    }

    public String getManufacturer_ID() {
        return Manufacturer_ID;
    }

    public void setManufacturer_ID(String Manufacturer_ID) {
        this.Manufacturer_ID = Manufacturer_ID;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getGoogle_ID() {
        return Google_ID;
    }

    public void setGoogle_ID(String Google_ID) {
        this.Google_ID = Google_ID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getManufacturer_Number() {
        return Manufacturer_Number;
    }

    public void setManufacturer_Number(String Manufacturer_Number) {
        this.Manufacturer_Number = Manufacturer_Number;
    }

    public String getProduct_Video() {
        return Product_Video;
    }

    public void setProduct_Video(String Product_Video) {
        this.Product_Video = Product_Video;
    }

    public String getProduct_360_Video() {
        return Product_360_Video;
    }

    public void setProduct_360_Video(String Product_360_Video) {
        this.Product_360_Video = Product_360_Video;
    }

    public String getSequence_Number() {
        return Sequence_Number;
    }

    public void setSequence_Number(String Sequence_Number) {
        this.Sequence_Number = Sequence_Number;
    }

    public String getBF_Inventory_Total() {
        return BF_Inventory_Total;
    }

    public void setBF_Inventory_Total(String BF_Inventory_Total) {
        this.BF_Inventory_Total = BF_Inventory_Total;
    }

    public String getBF_Inventory_Warehouse() {
        return BF_Inventory_Warehouse;
    }

    public void setBF_Inventory_Warehouse(String BF_Inventory_Warehouse) {
        this.BF_Inventory_Warehouse = BF_Inventory_Warehouse;
    }

    public String getPDP_Select_Multi_Variant() {
        return PDP_Select_Multi_Variant;
    }

    public void setPDP_Select_Multi_Variant(String PDP_Select_Multi_Variant) {
        this.PDP_Select_Multi_Variant = PDP_Select_Multi_Variant;
    }

    public String getProduct_Weight() {
        return Product_Weight;
    }

    public void setProduct_Weight(String Product_Weight) {
        this.Product_Weight = Product_Weight;
    }

    public String getCheck_Out_Gift_Message() {
        return Check_Out_Gift_Message;
    }

    public void setCheck_Out_Gift_Message(String Check_Out_Gift_Message) {
        this.Check_Out_Gift_Message = Check_Out_Gift_Message;
    }

    public String getPDP_Min_Quantity() {
        return PDP_Min_Quantity;
    }

    public void setPDP_Min_Quantity(String PDP_Min_Quantity) {
        this.PDP_Min_Quantity = PDP_Min_Quantity;
    }

    public String getPDP_Max_Quantity() {
        return PDP_Max_Quantity;
    }

    public void setPDP_Max_Quantity(String PDP_Max_Quantity) {
        this.PDP_Max_Quantity = PDP_Max_Quantity;
    }

    public String getPDP_Default_Quantity() {
        return PDP_Default_Quantity;
    }

    public void setPDP_Default_Quantity(String PDP_Default_Quantity) {
        this.PDP_Default_Quantity = PDP_Default_Quantity;
    }

    public String getPDP_In_Store_Only() {
        return PDP_In_Store_Only;
    }

    public void setPDP_In_Store_Only(String PDP_In_Store_Only) {
        this.PDP_In_Store_Only = PDP_In_Store_Only;
    }

    public String outputDebug() {
        String str = Master_Product_ID + ","
                + Product_ID + ","
                + Product_Category_ID + ","
                + Brand + ","
                + Catrgory + ","
                + virtual + ","
                + Product_CODE + ","
                + BRAND_FULL + ","
                + Catrgory_Full + ","
                + Internal_Name + ","
                + Product_Name + ","
                + Sales_Pitch + ","
                + Long_Description + ","
                + Special_Instr + ","
                + Delivery_Info + ","
                + Directions + ","
                + Terms_Cond + ","
                + Ingredients + ","
                + Warnings + ","
                + PLP_Label + ","
                + PDP_Label + ","
                + List_Price + ","
                + Sales_Price + ","
                + Selectable_Features_1 + ","
                + PLP_Swatch_Image + ","
                + PDP_Swatch_Image + ","
                + Selectable_Features_2 + ","
                + Selectable_Features_3 + ","
                + Selectable_Features_4 + ","
                + Selectable_Features_5 + ","
                + Descriptive_Features_1 + ","
                + Descriptive_Features_2 + ","
                + Descriptive_Features_3 + ","
                + Descriptive_Features_4 + ","
                + Descriptive_Features_5 + ","
                + PLP_Image + ","
                + PLP_Image_Alt + ","
                + PDP_Thumbnail_Image + ","
                + PDP_Regular_Image + ","
                + PDP_Large_Image + ","
                + PDP_Alt_1_Thumbnail_Image + ","
                + PDP_Alt_1_Regular_Image + ","
                + PDP_Alt_1_Large_Image + ","
                + PDP_Alt_2_Thumbnail_Image + ","
                + PDP_Alt_2_Regular_Image + ","
                + PDP_Alt_2_Large_Image + ","
                + PDP_Alt_3_Thumbnail_Image + ","
                + PDP_Alt_3_Regular_Image + ","
                + PDP_Alt_3_Large_Image + ","
                + PDP_Alt_4_Thumbnail_Image + ","
                + PDP_Alt_4_Regular_Image + ","
                + PDP_Alt_4_Large_Image + ","
                + PDP_Alt_5_Thumbnail_Image + ","
                + PDP_Alt_5_Regular_Image + ","
                + PDP_Alt_5_Large_Image + ","
                + PDP_Alt_6_Thumbnail_Image + ","
                + PDP_Alt_6_Regular_Image + ","
                + PDP_Alt_6_Large_Image + ","
                + PDP_Alt_7_Thumbnail_Image + ","
                + PDP_Alt_7_Regular_Image + ","
                + PDP_Alt_7_Large_Image + ","
                + PDP_Alt_8_Thumbnail_Image + ","
                + PDP_Alt_8_Regular_Image + ","
                + PDP_Alt_8_Large_Image + ","
                + PDP_Alt_9_Thumbnail_Image + ","
                + PDP_Alt_9_Regular_Image + ","
                + PDP_Alt_9_Large_Image + ","
                + PDP_Alt_10_Thumbnail_Image + ","
                + PDP_Alt_10_Regular_Image + ","
                + PDP_Alt_10_Large_Image + ","
                + Product_Height + ","
                + Product_Width + ","
                + Product_Depth + ","
                + Returnable + ","
                + Taxable + ","
                + Charge_Shipping + ","
                + Introduction_Date + ","
                + Discontinued_Date + ","
                + Manufacturer_ID + ","
                + SKU + ","
                + Google_ID + ","
                + ISBN + ","
                + Manufacturer_Number + ","
                + Product_Video + ","
                + Product_360_Video + ","
                + Sequence_Number + ","
                + BF_Inventory_Total + ","
                + BF_Inventory_Warehouse + ","
                + PDP_Select_Multi_Variant + ","
                + Product_Weight + ","
                + Check_Out_Gift_Message + ","
                + PDP_Min_Quantity + ","
                + PDP_Max_Quantity + ","
                + PDP_Default_Quantity + ","
                + PDP_In_Store_Only;
        return str;
    }
}
