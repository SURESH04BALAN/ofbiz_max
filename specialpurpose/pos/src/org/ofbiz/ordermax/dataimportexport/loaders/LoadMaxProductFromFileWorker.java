/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.dataimportexport.loaders;

/**
 *
 * @author siranjeev
 */
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.tools.ant.util.FileUtils;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilDateTime;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.generic.OrderMaxViewEntity;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadMaxProductFromFileWorker extends LoadProductFromFileWorker {

    public static final String module = LoadMaxProductFromFileWorker.class.getName();

    public LoadMaxProductFromFileWorker(ListAdapterListModel<ProductComposite> personListModel, String fileName, XuiSession delegator) {
        super(personListModel, fileName, delegator);

    }

    Map<String, String> listMap = new HashMap<String, String>();
    Map<Integer, String> reverselistMap = new HashMap<Integer, String>();

    String getParentCategoryId(Integer val) {
        for (Map.Entry<Integer, String> entryMap : reverselistMap.entrySet()) {

            if (entryMap.getKey() != null) {
                Integer right = entryMap.getKey() + 99;

                if (entryMap.getKey().compareTo(val) <= 0 && val.compareTo(right) < 0) {
                    Debug.logInfo("entryMap.getKey(): " + entryMap.getKey().toString() + " right: " + right.toString() + " val: " + val, module);

                    return entryMap.getValue();
                }
            } else {
                Debug.logInfo("entryMap.getKey(): null", module);

            }
        }
        return "Unknown";
    }

    @Override
    protected List<ProductComposite> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<ProductComposite> persons = new ArrayList<ProductComposite>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Map<String, Object> resultMap = ServiceUtil.returnSuccess();
        GenericValue userLogin = session.getUserLogin();
        LocalDispatcher dispatcher = session.getDispatcher();
        Delegator delegator = session.getDelegator();
        Locale locale = Locale.getDefault();

        getClassLoader();
        final ClassLoader cl = this.getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        /*
         //        fieldMap.addAll(fields)
         String fieldId[] = {
         "PRODUCT_ID", "PRODUCT_TYPE_ID", "INTERNAL_NAME", "BRAND_NAME",
         "COMMENTS", "PRODUCT_NAME", "DESCRIPTION", "LONG_DESCRIPTION",
         "REQUIRE_INVENTORY", "QUANTITY_UOM_ID", "REQUIRE_AMOUNT", "TAXABLE",
         "IS_VIRTUAL", "IS_VARIANT", "PRIMARY_PRODUCT_CATEGORY_ID", "MANUFACTURER_PARTY_ID", "FACILITY_ID",
         "INTRODUCTION_DATE", "RELEASE_DATE", "SUPPORT_DISCONTINUATION_DATE",
         "SALES_DISCONTINUATION_DATE", "SALES_DISC_WHEN_NOT_AVAIL",
         "PRICE_DETAIL_TEXT", "SMALL_IMAGE_URL", "MEDIUM_IMAGE_URL", "LARGE_IMAGE_URL",
         "DETAIL_IMAGE_URL", "ORIGINAL_IMAGE_URL", "DETAIL_SCREEN", "INVENTORY_MESSAGE",
         "QUANTITY_INCLUDED", "PIECES_INCLUDED", "FIXED_AMOUNT", "AMOUNT_UOM_TYPE_ID",
         "WEIGHT_UOM_ID", "WEIGHT", "PRODUCT_WEIGHT", "HEIGHT_UOM_ID", "PRODUCT_HEIGHT",
         "SHIPPING_HEIGHT", "WIDTH_UOM_ID", "PRODUCT_WIDTH", "SHIPPING_WIDTH", "DEPTH_UOM_ID",
         "PRODUCT_DEPTH", "SHIPPING_DEPTH", "DIAMETER_UOM_ID", "PRODUCT_DIAMETER",
         "PRODUCT_RATING", "RATING_TYPE_ENUM", "RETURNABLE", "CHARGE_SHIPPING",
         "AUTO_CREATE_KEYWORDS", "INCLUDE_IN_PROMOTIONS", "VIRTUAL_VARIANT_METHOD_ENUM",
         "ORIGIN_GEO_ID", "REQUIREMENT_METHOD_ENUM_ID", "BILL_OF_MATERIAL_LEVEL",
         "RESERV_MAX_PERSONS", "RESERV2ND_P_P_PERC", "RESERV_NTH_P_P_PERC", "CONFIG_ID",
         "CREATED_DATE", "CREATED_BY_USER_LOGIN", "LAST_MODIFIED_DATE",
         "LAST_MODIFIED_BY_USER_LOGIN", "IN_SHIPPING_BOX", "DEFAULT_SHIPMENT_BOX_TYPE_ID",
         "ORDER_DECIMAL_QUANTITY", "LAST_UPDATED_STAMP", "LAST_UPDATED_TX_STAMP",
         "CREATED_STAMP", "CREATED_TX_STAMP", "LOT_ID_FILLED_IN",};
         */
        String fieldId[][] = {
            {"Baby_Food", "100"},
            {"Spices_and_Masala", "200"},
            {"Biscuits_Cookies", "4900"},
            {"Body_Care", "300"},
            {"Pickles", "400"},
            {"BreakfastCereals", "500"},
            {"Cleaning_Agents", "600"},
            {"Coffee", "700"},
            {"Confectionery", "800"},
            {"Deos_Perfumes", "900"},
            {"DetergentsWashCare", "1000"},
            {"Dry_Fruits", "1100"},
            {"Energy_HealthDrinks", "1200"},
            {"Entertainment", "1300"},
            {"Rice_Rice_Products", "1400"},
            {"Pulses_and_Dals", "1500"},
            {"Atta_OtherFlours", "1600"},
            {"Hair_Care", "1700"},
            {"HealthWellness", "1800"},
            {"IndianSweets", "1900"},
            {"JamsSpreads_Sauces", "2000"},
            {"Baby_Care", "2100"},
            {"Oil_and_Ghee", "2200"},
            {"Noodles", "2300"},
            {"Oral_Care", "2400"},
            {"OTC", "2500"},
            {"Others", "2600"},
            {"PaperDisposable", "2700"},
            {"PastaVermicelli", "2800"},
            {"Plasticware", "2900"},
            {"PoojaNeeds", "3000"},
            {"ReadytoEatCook", "3100"},
            {"RepellentsFresheners", "3200"},
            {"RepellentsFresheners", "3300"},
            {"Salt_and_Sugar", "3400"},
            {"Shaving_Needs", "3500"},
            {"ShoeCare", "3600"},
            {"Skin_Care", "3700"},
            {"Snacks", "3800"},
            {"Soft_Drinks", "3900"},
            {"Tea", "4000"},
            {"ToiletFloorCleaner", "4100"},
            {"UtensilCleaner", "4200"},
            {"Women_Needs", "4300"},
            {"App", "4400"},
            {"KitchenApp", "4500"},
            {"Utensils", "4600"},
            {"Juice", "4700"},
            {"MilkMilkProducts", "4800"},};

        for (int i = 0; i < fieldId.length; ++i) {
            listMap.put(fieldId[i][0], fieldId[i][1]);
            reverselistMap.put(new Integer(fieldId[i][1]), fieldId[i][0]);
        }

        List<BigFishProduct> bigFishProducts = new ArrayList<BigFishProduct>();
        List<BigFishCategory> bigFishCategories = new ArrayList<BigFishCategory>();
        String basePath = "C:/ordermax/Real_Data/";
        String currImagePath = "C:/backup/finalpos";
        try {

            //    listMap.add(maps);
            //}
            try {
                Reader csvFileIo = null;
                try {

                    csvFileIo = new BufferedReader(new FileReader(csvFile));//new InputStreamReader(is);
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
                List<org.ofbiz.ordermax.entity.Product> personsRead = null;
                try {

                    CSVReader<org.ofbiz.ordermax.entity.Product> personReader = new CSVReaderBuilder<org.ofbiz.ordermax.entity.Product>(csvFileIo).entryParser(new MaxProductEntryParser()).build();
                    personsRead = personReader.readAll();
                } catch (Exception e) {
                    Debug.logError(e, "Unable to rollback transaction", module);
                }

                for (org.ofbiz.ordermax.entity.Product prod : personsRead) {
                    String productName = prod.getproductName();
                    if (UtilValidate.isNotEmpty(productName)) {
                        productName = OrderMaxViewEntity.capitalizeString(productName);
                        prod.setproductName(productName);
                        prod.setlongDescription(productName);
                    }

                    String brandName = prod.getbrandName();
                    if (UtilValidate.isNotEmpty(brandName)) {
                        brandName = OrderMaxViewEntity.capitalizeString(brandName);
                        prod.setbrandName(brandName);
                        prod.setlongDescription(productName);
                    }
                }
                maxProgress = personsRead.size();

                Map<String, BigFishCategory> categories = new HashMap<String, BigFishCategory>();

                Map<org.ofbiz.ordermax.entity.Product, List<org.ofbiz.ordermax.entity.Product>> productVirtualMap = new HashMap<org.ofbiz.ordermax.entity.Product, List<org.ofbiz.ordermax.entity.Product>>();
//                boolean isLastParent = false;
                org.ofbiz.ordermax.entity.Product parent = null;
                for (org.ofbiz.ordermax.entity.Product prod : personsRead) {
                    String isParent = prod.getrequireInventory();
                    Debug.logInfo("load product id: " + prod.getproductId(),module);                    
                    if (UtilValidate.isNotEmpty(isParent) && "P".equalsIgnoreCase(isParent)) {
                        parent = prod;
                        List<org.ofbiz.ordermax.entity.Product> list = new ArrayList<org.ofbiz.ordermax.entity.Product>();
                        list.add(prod);
                        productVirtualMap.put(prod, list);
                    } else {
                        if (productVirtualMap.containsKey(parent)) {
                            productVirtualMap.get(parent).add(prod);
                        } else {
                            System.out.println("Unknown situation: " + prod.getproductId());
                        }
                    }
                }

                for (Map.Entry<org.ofbiz.ordermax.entity.Product, List<org.ofbiz.ordermax.entity.Product>> entryMap : productVirtualMap.entrySet()) {
                    List<org.ofbiz.ordermax.entity.Product> orderItem = (List<org.ofbiz.ordermax.entity.Product>) entryMap.getValue();
                    org.ofbiz.ordermax.entity.Product parentVirtual = entryMap.getKey();
                    BigFishProduct bigFishProduct = new BigFishProduct();
                    String parentCatlog = "Unknown";
                    try {
                        String numbers = parentVirtual.getproductId().substring(parentVirtual.getproductId().length() - 4);
                        Debug.logInfo("numbers: " + Integer.valueOf(numbers), module);
                        parentCatlog = getParentCategoryId(Integer.valueOf(numbers));
                    } catch (Exception e) {
                        Debug.logError(e, module);
                    }
                    //for (org.ofbiz.ordermax.entity.Product prod : personsRead) {
                    //String catId = parentVirtual.getprimaryProductCategoryId();
                    String brandName = parentVirtual.getbrandName();
                    //initialise;
                    parentVirtual.setprimaryProductCategoryId("Unknown - product id: " + parentVirtual.getproductId() + " Brand Name: " + brandName + " found cat: " + parentCatlog);

                    if (UtilValidate.isNotEmpty(parentCatlog) && UtilValidate.isNotEmpty(brandName)) {
                        String brandCatId = brandName.replaceAll(" ", "_");
                        String bfParentCatlog = brandCatId + ";" + parentCatlog;
                        parentVirtual.setprimaryProductCategoryId(bfParentCatlog);                        
                        if (!categories.containsKey(bfParentCatlog)) {
                            BigFishCategory bfCat = new BigFishCategory();
                            bfCat.productCategoryId = brandCatId;
                           // bfCat.parentCategoryId = parentCatlog;
                            bfCat.parentCategoryId = parentCatlog;
                            bfCat.categoryName = brandName;
                            bfCat.description = brandName;
                            bfCat.longDescription = brandName;
                            bfCat.plpImageName = "";
                            bfCat.additionalPlpText = "";
                            bfCat.additionalPdpText = "";
                            bfCat.fromDate = UtilDateTime.nowTimestamp();
                            categories.put(bfParentCatlog, bfCat);
                            bigFishCategories.add(bfCat);   
                            
                            bfCat = new BigFishCategory();
                            bfCat.productCategoryId = brandCatId;
                           // bfCat.parentCategoryId = parentCatlog;
                            bfCat.parentCategoryId = "Popular_Brands";
                            bfCat.categoryName = brandName;
                            bfCat.description = brandName;
                            bfCat.longDescription = brandName;
                            bfCat.plpImageName = "";
                            bfCat.additionalPlpText = "";
                            bfCat.additionalPdpText = "";
                            bfCat.fromDate = UtilDateTime.nowTimestamp();
                            //parentVirtual.setprimaryProductCategoryId(bfParentCatlog);
                            //categories.put(bfParentCatlog, bfCat);
                         
                            bigFishCategories.add(bfCat);                            
                        }
                    }
                    //}
                    //parentCatlog = parentVirtual.getbrandName() + ";" + parentCatlog;// + "\"";

                    String productIdVirtual = parentVirtual.getproductId() + "V";
                    if (UtilValidate.isNotEmpty(parentVirtual.getproductName()) && (parentVirtual.getproductName().indexOf(brandName) == -1 && parentVirtual.getproductName().lastIndexOf(brandName) == -1)) {
                        parentVirtual.setproductName(brandName.concat(" ").concat(parentVirtual.getproductName()));
                        parentVirtual.setlongDescription(parentVirtual.getproductName());
                    }

                    bigFishProduct.setProduct(productIdVirtual, productIdVirtual, parentVirtual.getprimaryProductCategoryId(), parentVirtual);
                    bigFishProduct.Product_ID = productIdVirtual;
                    bigFishProduct.Descriptive_Features_1 = "Brand:" + brandName;
                    bigFishProduct.Descriptive_Features_2 = "PRODUCTGROUP:Powdered Spices";                    
                    String plp = getImageFileName(basePath + "image", "PLP", productIdVirtual, "plp");
                    String pdp = getImageFileName(basePath + "image", "PDP", productIdVirtual, "pdp");
                    Debug.logInfo("pdp: " + pdp, module);
                    Debug.logInfo("currImagePath: " + currImagePath + parentVirtual.getdetailImageUrl(), module);
                    if (UtilValidate.isNotEmpty(parentVirtual.getdetailImageUrl())) {
                        FileUtils.getFileUtils().copyFile(currImagePath + parentVirtual.getdetailImageUrl(), plp);
                        bigFishProduct.PLP_Image = getImageFileName("grocery", "PLP", productIdVirtual, "plp");
                    }
                    if (UtilValidate.isNotEmpty(parentVirtual.getlargeImageUrl())) {
                        FileUtils.getFileUtils().copyFile(currImagePath + parentVirtual.getlargeImageUrl(), pdp);
                        bigFishProduct.PDP_Large_Image = getImageFileName("grocery", "PDP", productIdVirtual, "pdp");
                        bigFishProduct.PDP_Regular_Image = getImageFileName("grocery", "PDP", productIdVirtual, "pdp");
                    }
                    bigFishProducts.add(bigFishProduct);

                    for (org.ofbiz.ordermax.entity.Product prod : orderItem) {
                        if (UtilValidate.isNotEmpty(prod.getproductName()) && (prod.getproductName().indexOf(brandName) == -1 && prod.getproductName().lastIndexOf(brandName) == -1)) {
                            prod.setproductName(brandName.concat(" ").concat(prod.getproductName()));
                            prod.setlongDescription(prod.getproductName());
                        }

                        bigFishProduct = new BigFishProduct();
                        bigFishProduct.setProduct(productIdVirtual, prod.getproductId(), "", prod);
                        bigFishProduct.Descriptive_Features_1 = "Brand:" + brandName;
                        bigFishProduct.Descriptive_Features_2 = "PRODUCTGROUP:Powdered Spices";
                        bigFishProduct.setSequence_Number("");
                        bigFishProducts.add(bigFishProduct);
                        String size = getSizeStr(bigFishProduct.getProduct_Name());
                        if (size != null) {
                            bigFishProduct.setSelectable_Features_1("Size:".concat(size));
                        }
                         plp = getImageFileName(basePath + "image", "PLP", prod.getproductId(), "plp");
                         pdp = getImageFileName(basePath + "image", "PDP", prod.getproductId(), "pdp");
                        Debug.logInfo("pdp: " + pdp, module);
                        Debug.logInfo("currImagePath: " + currImagePath + prod.getdetailImageUrl(), module);
                        if (UtilValidate.isNotEmpty(prod.getdetailImageUrl())) {
                            FileUtils.getFileUtils().copyFile(currImagePath + prod.getdetailImageUrl(), plp);
                            bigFishProduct.PLP_Image = getImageFileName("grocery", "PLP", prod.getproductId(), "plp");
                        }
                        if (UtilValidate.isNotEmpty(prod.getlargeImageUrl())) {
                            FileUtils.getFileUtils().copyFile(currImagePath + prod.getlargeImageUrl(), pdp);
                            bigFishProduct.PDP_Large_Image = getImageFileName("grocery", "PDP", prod.getproductId(), "pdp");
                            bigFishProduct.PDP_Regular_Image = getImageFileName("grocery", "PDP", prod.getproductId(), "pdp");
                        }
                        

                        ProductComposite productComposite = new ProductComposite();

                        productComposite.setProduct(prod);
                        persons.add(productComposite);
                        publish(productComposite);
                        sleepAWhile();
                    }
                }
                /*
                 System.out.println("Start create product");
                 personListModel.clear();
                 for (org.ofbiz.ordermax.entity.Product product : personsRead) {

                 //createCategory(product,session);
                 Map<String, Object> valueMap = product.getGenericValueAsMap();
                 String productId = product.getproductId();
                 String brand = product.getbrandName();
                 String productCategoryId = brand.substring(0, brand.length() < 20 ? brand.length() : 19);
                 //                String productCategoryId = map.get("PRIMARY_PRODUCT_CATEGORY_ID");
                 System.out.println("Start create product productId: " + productId);
                 ProductComposite productComposite = new ProductComposite();

                 productComposite.setProduct(product);
                 persons.add(productComposite);
                 publish(productComposite);
                 sleepAWhile();

                 GenericValue productCategory = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", productCategoryId));
                 GenericValue productGV = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", productId));
                 if (productGV == null && productCategory != null) {
                 try {
                 valueMap.put("userLogin", userLogin);
                 valueMap.put("locale", locale);
                 valueMap.put("productCategoryId", productCategoryId);

                 Map<String, Object> featureMap = new HashMap<String, Object>();
                 featureMap.put("productFeatureId", "IMAGE_BOARD");
                 featureMap.put("productFeatureTypeId", "SIZE");
                 featureMap.put("productFeatureApplTypeId", "STANDARD_FEATURE");
                 //                        featureMap.put("productFeatureSelectableByType", "STANDARD_FEATURE");

                 valueMap.put("productFeatureIdByType", featureMap);

                 Map<String, Object> featureMapSel = new HashMap<String, Object>();
                 featureMapSel.put("productFeatureId", "IMAGE_BOARD");
                 featureMapSel.put("productFeatureApplTypeId", "STANDARD_FEATURE");
                 //                        featureMapSel.put("productFeatureSelectableByType", "STANDARD_FEATURE");                            
                 valueMap.put("productFeatureSelectableByType", featureMapSel);
                 } catch (Exception exc) {
                 Debug.logError(exc, module);
                 break;
                 }
                 }
                 }*/
            } catch (Exception e) {
                Debug.logError(e, "Unable to rollback transaction", module);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("bigFishProducts : " + bigFishProducts.size());

//        Writer out = new FileWriter("persons.csv");
        String csvFileName = basePath + "bigfish_max.txt";
        Writer csvFileOut = null;
        try {

            csvFileOut = new BufferedWriter(new FileWriter(csvFileName));//new InputStreamReader(is);
            CSVWriter<BigFishProduct> csvWriter = new CSVWriterBuilder<BigFishProduct>(csvFileOut).entryConverter(new BigFishProductEntryConverter()).build();
            csvWriter.writeAll(bigFishProducts);
            csvWriter.close();

        } catch (Exception e) {
            Debug.logError(e, module);
        }

//        Writer out = new FileWriter("persons.csv");
        System.out.println("bigFishCategories : " + bigFishCategories.size());
        String csvFileName1 = basePath + "bigfish_max_cat.txt";
        Writer csvFileOut1 = null;
        try {

            csvFileOut1 = new BufferedWriter(new FileWriter(csvFileName1));//new InputStreamReader(is);
            CSVWriter<BigFishCategory> csvWriter = new CSVWriterBuilder<BigFishCategory>(csvFileOut1).entryConverter(new BigFishCategoryEntryConverter()).build();
            csvWriter.writeAll(bigFishCategories);
            csvFileOut1.close();

        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return persons;
    }

    String getSizeStr(String nameStr) {
        String sizeStr = null;
        String fields[] = {
            "gm",
            "g",
            "kg",
            "ml",
            "l",};
String displayfields[] = {
            "g",
            "g",
            "kg",
            "ml",
            "l",};
        String weights[] = {
            "1",
            "2",
            "5",
            "10",
            "25",
            "50",
            "100",
            "150", "125", "175", "200", "225", "250", "300", "400", "500", "100", "750",};

        String findStr = nameStr.toLowerCase();

        for (int i = 0; i < fields.length; ++i) {
            for (int j = 0; j < weights.length; ++j) {
                String str1 = weights[j] + " " + fields[i];
                String str2 = weights[j] + fields[i];
                if (findStr.indexOf(str1) != -1 || findStr.indexOf(str2) != -1) {
                    return weights[j] + " " + displayfields[i];
                }

            }

        }
        return sizeStr;
    }

    protected String getImageFileName(String basePath, String subDirName, String productId, String type) {
        String newFilePath = basePath + "/" + subDirName + "/" + productId + "_" + type;

        newFilePath = newFilePath.concat(".jpg");

        File f = new File(newFilePath);
        if (!f.exists() && !f.isDirectory()) {
            return newFilePath;
        }
        return newFilePath;
    }

}
