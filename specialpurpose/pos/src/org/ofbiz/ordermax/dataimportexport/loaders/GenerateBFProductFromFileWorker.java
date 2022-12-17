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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.SwingWorker;
import mvc.controller.LoadProductCatalogWorker;
import mvc.controller.LoadProductWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.Product;
import org.ofbiz.ordermax.entity.ProductCategory;
import org.ofbiz.ordermax.entity.ProductCategoryRollup;
import org.ofbiz.ordermax.product.catalog.SaveProductCategoryAction;
import org.ofbiz.ordermax.screens.mainscreen.OrderMaxMainForm;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class GenerateBFProductFromFileWorker extends SwingWorker<List<BigFishProduct>, BigFishProduct> {

    public static final String module = GenerateBFProductFromFileWorker.class.getName();
    private volatile int maxProgress;
    private int progressedItems;
    private ListAdapterListModel<BigFishProduct> personListModel;
    private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    private String csvFile;
    XuiSession session = null;

    public GenerateBFProductFromFileWorker(ListAdapterListModel<BigFishProduct> personListModel, String fileName, XuiSession delegator) {
        this.personListModel = personListModel;
        csvFile = fileName;
        this.session = delegator;

    }
    static String ultimateRoot = "CATEGORY_ROOT";

    static public void createCategories(Map<String, String> categories, XuiSession session) {

        ProductCategory rootCategory = new ProductCategory();
        String rootCategoryId = ultimateRoot.substring(0, ultimateRoot.length() < 20 ? ultimateRoot.length() : 19);
        rootCategory.setProductCategoryId(rootCategoryId);
        rootCategory.setCategoryName(ultimateRoot);
        rootCategory.setDescription(ultimateRoot);
        rootCategory.setLongDescription(ultimateRoot);
//        rootCategory.setprimaryParentCategoryId(null);
        rootCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");
        //save the parent
        LoadProductCatalogWorker.saveCategory(rootCategory, session);

        for (Map.Entry<String, String> entry : categories.entrySet()) {

            ProductCategory productCategory = new ProductCategory();
            String pCategoryId = entry.getValue().substring(0, entry.getValue().length() < 20 ? entry.getValue().length() : 19);
            productCategory.setProductCategoryId(pCategoryId);
            productCategory.setCategoryName(entry.getValue());
            productCategory.setDescription(entry.getValue());
            productCategory.setLongDescription(entry.getValue());
            productCategory.setPrimaryParentCategoryId(rootCategoryId);
            productCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");

            //save the parent
            LoadProductCatalogWorker.saveCategory(productCategory, session);

            ProductCategoryRollup productCategoryRollup = new ProductCategoryRollup();
            productCategoryRollup.setproductCategoryId(pCategoryId);
            productCategoryRollup.setparentProductCategoryId(rootCategoryId);
            SaveProductCategoryAction.addProductCategoryToCategory(productCategoryRollup, session);

            String cCategoryId = entry.getKey().substring(0, entry.getKey().length() < 20 ? entry.getKey().length() : 19);
            ProductCategory productCategoryChild = new ProductCategory();
            productCategoryChild.setProductCategoryId(cCategoryId);
            productCategoryChild.setCategoryName(entry.getKey());
            productCategoryChild.setDescription(entry.getKey());
            productCategoryChild.setLongDescription(entry.getKey());
            productCategoryChild.setPrimaryParentCategoryId(productCategory.getProductCategoryId());
            productCategoryChild.setProductCategoryTypeId("INTERNAL_CATEGORY");
            //save the parent
            LoadProductCatalogWorker.saveCategory(productCategoryChild, session);
            ProductCategoryRollup childCategoryRollup = new ProductCategoryRollup();
            childCategoryRollup.setproductCategoryId(cCategoryId);
            childCategoryRollup.setparentProductCategoryId(productCategory.getProductCategoryId());
            SaveProductCategoryAction.addProductCategoryToCategory(childCategoryRollup, session);

        }

    }

    static ProductCategory rootCategory = null;

    static public void createCategory(org.ofbiz.ordermax.entity.Product product, XuiSession session) {
        String parentCategoryId = null;
        if (rootCategory == null) {
            rootCategory = new ProductCategory();
            parentCategoryId = ultimateRoot.substring(0, ultimateRoot.length() < 20 ? ultimateRoot.length() : 19);
            rootCategory.setProductCategoryId(parentCategoryId);
            rootCategory.setCategoryName(ultimateRoot);
            rootCategory.setDescription(ultimateRoot);
            rootCategory.setLongDescription(ultimateRoot);
//        rootCategory.setprimaryParentCategoryId(null);
            rootCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");
            //save the parent
            LoadProductCatalogWorker.saveCategory(rootCategory, session);
        } else {
            parentCategoryId = rootCategory.getProductCategoryId();
        }

        if (UtilValidate.isNotEmpty(product.getdepartmentName())) {
            ProductCategory deptCategory = new ProductCategory();
            parentCategoryId = product.getdepartmentName().substring(0, product.getdepartmentName().length() < 20 ? product.getdepartmentName().length() : 19);
            deptCategory.setProductCategoryId(parentCategoryId);
            deptCategory.setCategoryName(ultimateRoot);
            deptCategory.setDescription(ultimateRoot);
            deptCategory.setLongDescription(ultimateRoot);
            deptCategory.setPrimaryParentCategoryId(rootCategory.getProductCategoryId());
            deptCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");
            //save the parent
            LoadProductCatalogWorker.saveCategory(deptCategory, session);

            ProductCategoryRollup productCategoryRollup = new ProductCategoryRollup();
            productCategoryRollup.setproductCategoryId(parentCategoryId);
            productCategoryRollup.setparentProductCategoryId(rootCategory.getProductCategoryId());
            SaveProductCategoryAction.addProductCategoryToCategory(productCategoryRollup, session);
        }

        ProductCategory brandCategory = new ProductCategory();
        String brandCategoryId = product.getbrandName().substring(0, product.getbrandName().length() < 20 ? product.getbrandName().length() : 19);
        brandCategory.setProductCategoryId(brandCategoryId);
        brandCategory.setCategoryName(product.getbrandName());
        brandCategory.setDescription(product.getbrandName());
        brandCategory.setLongDescription(product.getbrandName());
        brandCategory.setPrimaryParentCategoryId(parentCategoryId);
        brandCategory.setProductCategoryTypeId("INTERNAL_CATEGORY");

        //save the parent
        LoadProductCatalogWorker.saveCategory(brandCategory, session);
        ProductCategoryRollup childCategoryRollup = new ProductCategoryRollup();
        childCategoryRollup.setproductCategoryId(brandCategoryId);
        childCategoryRollup.setparentProductCategoryId(parentCategoryId);
        SaveProductCategoryAction.addProductCategoryToCategory(childCategoryRollup, session);

    }
    Map<String, Integer> currCatMap = new HashMap<String, Integer>();
    Map<String, String> listMap = new HashMap<String, String>();

    void UpdateProductId(List<BigFishProduct> listBigFishProduct) {
        BigFishProduct virBf = null;
        String Master_Product_Id = "";
        for (BigFishProduct bf : listBigFishProduct) {
            if ("V".equals(bf.virtual)) {
                virBf = bf;
                break;
            }
        }

        if (virBf != null) {
            Integer val = new Integer(0);
            Integer catId = new Integer(listMap.get(virBf.Catrgory_Full.trim()));
            if (UtilValidate.isNotEmpty(virBf.BRAND_FULL) && UtilValidate.isNotEmpty(virBf.Catrgory_Full)) {
                String prefix = String.valueOf(virBf.Catrgory_Full.charAt(0)) + String.valueOf(virBf.BRAND_FULL.charAt(0));
                if (currCatMap.containsKey(prefix)) {
                    val = currCatMap.get(prefix);
                    val += 1;
                } else {
                    val = catId;
                }
                Master_Product_Id = virBf.Master_Product_ID;

                String productId = prefix + val.toString();
                virBf.Master_Product_ID = productId + virBf.virtual;
                virBf.Product_ID = virBf.Master_Product_ID;
                virBf.Product_Category_ID = virBf.BRAND_FULL + "%%%" + virBf.Catrgory_Full;
                virBf.Internal_Name = virBf.Product_ID;
                for (BigFishProduct bf : listBigFishProduct) {
                    if ("V".equals(bf.virtual) == false) {
                        String lastStr = Master_Product_Id.substring(0, Master_Product_Id.length() - 1);
                        Debug.logInfo("Master_Product_Id: " + Master_Product_Id + " lastStr:" + lastStr
                                + " bf.Product_ID: " + bf.Product_ID, module);
                        if (bf.Product_ID.equals(lastStr)) {
//                            Debug.logInfo("Matchedl product found!!!!!!!", module);
                            bf.Product_ID = productId;
                            bf.Master_Product_ID = virBf.Master_Product_ID;
                            bf.Internal_Name = bf.Product_ID;
//                            val += 1;
                            currCatMap.put(prefix, val);
                        } else {
                            val += 1;
                            bf.Product_ID = prefix + val.toString();
                            bf.Master_Product_ID = virBf.Master_Product_ID;
                            bf.Internal_Name = bf.Product_ID;
                            //            bf.Master_Product_ID = virBf.Master_Product_ID;
                            currCatMap.put(prefix, val);
                        }
                    }
                }
            }
        }
    }

    void UpdateNonVirtualProductId(List<BigFishProduct> listBigFishProduct) {
        BigFishProduct virtualBf = null;
        String Master_Product_Id = "";
        for (BigFishProduct bf : listBigFishProduct) {
            if ("V".equals(bf.virtual)) {
                virtualBf = bf;
                break;
            }
        }

//        if (virtualBf == null) {
//            Debug.logInfo("ERROR Virtual product found!!!!!!!", module);
//        }
        for (BigFishProduct virBf : listBigFishProduct) {

            Integer val = new Integer(0);
            Integer catId = new Integer(listMap.get(virBf.Catrgory_Full.trim()));
            if (UtilValidate.isNotEmpty(virBf.BRAND_FULL) && UtilValidate.isNotEmpty(virBf.Catrgory_Full)) {
                String prefix = String.valueOf(virBf.Catrgory_Full.charAt(0)) + String.valueOf(virBf.BRAND_FULL.charAt(0));
                if (currCatMap.containsKey(prefix)) {
                    val = currCatMap.get(prefix);
                    val += 1;
                } else {
                    val = catId;
                }
                Master_Product_Id = virBf.Master_Product_ID;

                String productId = prefix + val.toString();
                virBf.Master_Product_ID = productId;
                virBf.Product_ID = "";
                virBf.Product_Category_ID = virBf.BRAND_FULL + "%%%" + virBf.Catrgory_Full;
                virBf.Internal_Name = virBf.Master_Product_ID;

                currCatMap.put(prefix, val);

            }
        }

    }

    @Override
    protected List<BigFishProduct> doInBackground() throws Exception {
        personListModel.clear();
        maxProgress = 3;
        List<BigFishProduct> persons = new ArrayList<BigFishProduct>();
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
        String fieldId[][] = {
            {"Baby_Food", "0100"},
            {"Spices_and_Masala", "0200"},
            {"Biscuits_Cookies", "0200"},
            {"Body_Care", "0300"},
            {"Pickles", "0400"},
            {"BreakfastCereals", "0500"},
            {"Cleaning_Agents", "0600"},
            {"Coffee", "0700"},
            {"Confectionery", "0800"},
            {"Deos_Perfumes", "0900"},
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

        Map<String, String> categories = new HashMap<String, String>();
        List<BigFishProduct> listBigFishProduct = new ArrayList<BigFishProduct>();

        for (int i = 0; i < fieldId.length; ++i) {
            listMap.put(fieldId[i][0], fieldId[i][1]);
        }

        try {
            try {
                Reader csvFileIo = null;
                try {

                    csvFileIo = new BufferedReader(new FileReader(csvFile));//new InputStreamReader(is);
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
                List<BigFishProduct> personsRead = null;
                try {

                    CSVReader<BigFishProduct> personReader = new CSVReaderBuilder<BigFishProduct>(csvFileIo).entryParser(new BigFishProductEntryParser()).build();
                    personsRead = personReader.readAll();
                } catch (Exception e) {
                    Debug.logError(e, "Unable to rollback transaction", module);
                }

                maxProgress = personsRead.size();
                System.out.println("maxProgress: " + maxProgress);

                System.out.println("Start create product");
                personListModel.clear();
                //rename files
                for (BigFishProduct product : personsRead) {
                    //create max images
                    setTextToImage(product.getProduct_Name(), product.getProduct_ID());

                    //rename images
                    //renameImageFiles(product);
                    persons.add(product);
                    publish(product);
                }

                /*for (BigFishProduct product : personsRead) {

                 System.out.println("create Suppier product: " + product.outputDebug());

                 String catKey = product.Catrgory_Full.trim();
                 categories.put(catKey, catKey);
                 //                    System.out.println("personsRead: " + product.Product_ID + "product.virtual: " + product.virtual);
                 try {
                 if (listMap.containsKey(catKey)) {
                 if ("V".equals(product.virtual)) {
                 if (!listBigFishProduct.isEmpty()) {
                 UpdateProductId(listBigFishProduct);
                 for (BigFishProduct bf : listBigFishProduct) {
                 persons.add(bf);
                 publish(bf);
                 }

                 listBigFishProduct.clear();
                 }
                 listBigFishProduct.add(product);
                 } else if ("C".equals(product.virtual)) {
                 if (listBigFishProduct.isEmpty()) {
                 System.out.println("Child without virtual... please check");
                 }
                 listBigFishProduct.add(product);
                 } else if ("P".equals(product.virtual)) {
                 if (!listBigFishProduct.isEmpty()) {
                 UpdateProductId(listBigFishProduct);
                 //                                    System.out.println("PPPPPPPP: " + product.Catrgory_Full + "   " + listMap.get(product.Catrgory_Full));
                 for (BigFishProduct bf : listBigFishProduct) {
                 persons.add(bf);
                 publish(bf);
                 }
                 listBigFishProduct.clear();
                 }
                 //process the non virtual product
                 listBigFishProduct.add(product);
                 UpdateNonVirtualProductId(listBigFishProduct);
                 for (BigFishProduct bf : listBigFishProduct) {
                 persons.add(bf);
                 publish(bf);
                 }
                 listBigFishProduct.clear();

                 } else {
                 System.out.println("virtual " + product.virtual);
                 }
                 } else {

                 System.out.println("catKey is not found: " + catKey + " product ID: " + product.Product_ID);
                 }
                 } catch (Exception e) {
                 Debug.logError(e, "Error", module);
                 }
                 sleepAWhile();
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

        for (Map.Entry<String, String> entryDept : categories.entrySet()) {
            Debug.logInfo(entryDept.getKey(), module);
        }

//        Writer out = new FileWriter("persons.csv");
        String csvFile = "C://ordermax//Real_Data//bigfish.csv";
        Writer csvFileOut = null;
        try {

            csvFileOut = new BufferedWriter(new FileWriter(csvFile));//new InputStreamReader(is);
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        CSVWriter<BigFishProduct> csvWriter = new CSVWriterBuilder<BigFishProduct>(csvFileOut).entryConverter(new BigFishProductEntryConverter()).build();
        csvWriter.writeAll(persons);
        return persons;
    }

    public void renameImageFiles(BigFishProduct bf) {
        String srcDir = "C:/ofbiz_web/Max_Spice/Max_Spice/themes/grocery_theme/webapp/grocery_theme/images/catalog/";
        String destDir = "C:/ofbiz_web/Max_Spice/Max_Spice/themes/grocery_theme/webapp/grocery_theme/images/catalog/fixed/";

        String srcFullPath = srcDir + bf.PLP_Image;
        String destFullPath = destDir + "grocery/PLP/" + bf.Product_ID + "_plp.jpg";
        Debug.logInfo("plp srcFullPath: " + srcFullPath, module);
        Debug.logInfo("plp destFullPath: " + destFullPath, module);
        BaseHelper.copyFile(srcFullPath, destFullPath);

        srcFullPath = srcDir + bf.PDP_Regular_Image;
        destFullPath = destDir + "grocery/PDP/" + bf.Product_ID + "_pdp.jpg";
        Debug.logInfo("pdp srcFullPath: " + srcFullPath, module);
        Debug.logInfo("pdp destFullPath: " + destFullPath, module);
        BaseHelper.copyFile(srcFullPath, destFullPath);
    }

    void setTextToImage(String STRING, String productId) {
        try {
            // public static void main(String[] args) throws Exception {

            final BufferedImage image = ImageIO.read(new File("C:\\josh\\ofbiz\\label.jpg"));
//                String STRING = strings[i];
            int index = STRING.indexOf("Max Spices");
            if (index > -1) {
                STRING = STRING.substring("Max Spices".length()).trim();
            }
            Debug.logInfo(" substring index: " + index, module);
            //Graphics g = image.getGraphics();
            Graphics2D gO = image.createGraphics();
            gO.setColor(Color.BLACK);
            gO.setFont(new Font("SansSerif", Font.BOLD, 44));
            StringMetrics metrics = new StringMetrics(gO);

            double width = metrics.getWidth(STRING);
            double height = metrics.getHeight(STRING);
            double middle = (488 / 2) - (width / 2);
            double middleH = 510 + (100 - (height / 2));
            if (middle < 10) {
                int midpoint = STRING.length() / 2;

                int splitSpace = STRING.indexOf(" ", midpoint);
                String firstHalf = STRING.substring(0, splitSpace).trim();
                width = metrics.getWidth(firstHalf);
                middle = (488 / 2) - (width / 2);
                gO.drawString(firstHalf, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());

                String secondHalf = STRING.substring(splitSpace);
                width = metrics.getWidth(secondHalf);
                middle = (488 / 2) - (width / 2);
                middleH += height * 1.2;
                gO.drawString(secondHalf, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());
            } else {
                gO.drawString(STRING, Double.valueOf(middle).intValue(), Double.valueOf(middleH).intValue());
            }

            //    g.setColor(Color.BLACK);
            //  g.setFont(g.getFont().deriveFont(30f));
            //g.drawString("Hello World!", 530, 100);
            gO.dispose();

            ImageIO.write(image, "jpg", new File("C:\\josh\\ofbiz\\" + productId + ".jpg"));

        } catch (IOException ex) {
            Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    public void createMaxSpicesProductImages() {

        try {
            // public static void main(String[] args) throws Exception {
            final BufferedImage image = ImageIO.read(new URL("http://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png"));

            Graphics g = image.getGraphics();
            g.setFont(g.getFont().deriveFont(30f));
            g.drawString("Hello World!", 100, 100);
            g.dispose();

            ImageIO.write(image, "png", new File("test.png"));
//}
        } catch (IOException ex) {
            Logger.getLogger(GenerateBFProductFromFileWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static protected Map<String, Object> getProductMap(Map<String, String> map) {

        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("productId", map.get("PRODUCT_ID"));
        valueMap.put("productTypeId", map.get("PRODUCT_TYPE_ID"));
        valueMap.put("primaryProductCategoryId", map.get("PRIMARY_PRODUCT_CATEGORY_ID"));
        valueMap.put("internalName", map.get("INTERNAL_NAME"));
        valueMap.put("brandName", map.get("BRAND_NAME"));
        valueMap.put("description", map.get("DESCRIPTION"));
        valueMap.put("longDescription", map.get("LONG_DESCRIPTION"));
        valueMap.put("productName", map.get("PRODUCT_NAME"));
        valueMap.put("comments", map.get("COMMENTS"));

        valueMap.put("longDescription", map.get("LONG_DESCRIPTION"));
        valueMap.put("smallImageUrl", map.get("SMALL_IMAGE_URL"));
        valueMap.put("mediumImageUrl", map.get("MEDIUM_IMAGE_URL"));
        valueMap.put("largeImageUrl", map.get("LARGE_IMAGE_URL"));

        valueMap.put("detailImageUrl", map.get("DETAIL_IMAGE_URL"));
        valueMap.put("originalImageUrl", map.get("ORIGINAL_IMAGE_URL"));
        valueMap.put("inventoryMessage", map.get("INVENTORY_MESSAGE"));
        valueMap.put("requireInventory", map.get("REQUIRE_INVENTORY"));
        valueMap.put("weightUomId", map.get("WEIGHT_UOM_ID"));
        valueMap.put("weight", map.get("WEIGHT"));
        valueMap.put("productWeight", map.get("PRODUCT_HEIGHT"));
        valueMap.put("returnable", map.get("RETURNABLE"));
        valueMap.put("taxable", map.get("TAXABLE"));
        valueMap.put("chargeShipping", map.get("CHARGE_SHIPPING"));
        valueMap.put("autoCreateKeywords", map.get("AUTO_CREATE_KEYWORDS"));
        valueMap.put("includeInPromotions", map.get("INCLUDE_IN_PROMOTIONS"));
        valueMap.put("isVirtual", map.get("IS_VIRTUAL"));
        valueMap.put("isVariant", map.get("IS_VARIANT"));
        valueMap.put("requirementMethodEnumId", map.get("REQUIREMENT_METHOD_ENUM_ID"));


        /*        
         valueMap.put("reservNthPPPerc", map.get("reservNthPPPerc"));
         valueMap.put("productHeight", map.get("productHeight"));
         valueMap.put("billOfMaterialLevel", map.get("billOfMaterialLevel"));
         valueMap.put("quantityIncluded", map.get("quantityIncluded"));
         valueMap.put("createdStamp", map.get("createdStamp"));
         valueMap.put("heightUomId", map.get("heightUomId"));
         valueMap.put("widthUomId", map.get("widthUomId"));
         valueMap.put("reservMaxPersons", map.get("reservMaxPersons"));
         valueMap.put("mediumImageUrl", map.get("mediumImageUrl"));
         valueMap.put("createdDate", map.get("createdDate"));

         valueMap.put("shippingWidth", map.get("shippingWidth"));

         valueMap.put("salesDiscWhenNotAvail", map.get("salesDiscWhenNotAvail"));
         valueMap.put("amountUomTypeId", map.get("amountUomTypeId"));
         valueMap.put("facilityId", map.get("facilityId"));
         valueMap.put("configId", map.get("configId"));
         valueMap.put("productRating", map.get("productRating"));
         valueMap.put("lastModifiedByUserLogin", map.get("lastModifiedByUserLogin"));
         valueMap.put("ratingTypeEnum", map.get("ratingTypeEnum"));
         valueMap.put("fixedAmount", map.get("fixedAmount"));
         valueMap.put("returnable", map.get("returnable"));

         valueMap.put("requirementMethodEnumId", map.get("requirementMethodEnumId"));
         valueMap.put("depthUomId", map.get("depthUomId"));
         valueMap.put("piecesIncluded", map.get("piecesIncluded"));
         valueMap.put("quantityUomId", map.get("quantityUomId"));

         valueMap.put("weight", map.get("weight"));
         valueMap.put("virtualVariantMethodEnum", map.get("virtualVariantMethodEnum"));
         valueMap.put("salesDiscontinuationDate", map.get("salesDiscontinuationDate"));

         valueMap.put("includeInPromotions", map.get("includeInPromotions"));
         valueMap.put("supportDiscontinuationDate", map.get("supportDiscontinuationDate"));
         valueMap.put("originalImageUrl", map.get("originalImageUrl"));
         valueMap.put("detailScreen", map.get("detailScreen"));
         valueMap.put("introductionDate", map.get("introductionDate"));
         valueMap.put("requireAmount", map.get("requireAmount"));
         valueMap.put("releaseDate", map.get("releaseDate"));
         valueMap.put("createdTxStamp", map.get("createdTxStamp"));
         valueMap.put("productDepth", map.get("productDepth"));
         valueMap.put("shippingDepth", map.get("shippingDepth"));
         valueMap.put("largeImageUrl", map.get("largeImageUrl"));
         valueMap.put("originGeoId", map.get("originGeoId"));
         valueMap.put("reserv2ndPPPerc", map.get("reserv2ndPPPerc"));
         valueMap.put("inShippingBox", map.get("inShippingBox"));
         valueMap.put("requireInventory", map.get("requireInventory"));
         valueMap.put("detailImageUrl", map.get("detailImageUrl"));
         valueMap.put("productWidth", map.get("productWidth"));
         valueMap.put("lastUpdatedStamp", map.get("lastUpdatedStamp"));
         valueMap.put("weightUomId", map.get("weightUomId"));
         valueMap.put("manufacturerPartyId", map.get("manufacturerPartyId"));
         valueMap.put("productDiameter", map.get("productDiameter"));
         valueMap.put("orderDecimalQuantity", map.get("orderDecimalQuantity"));
         valueMap.put("smallImageUrl", map.get("smallImageUrl"));

         valueMap.put("priceDetailText", map.get("priceDetailText"));
         valueMap.put("defaultShipmentBoxTypeId", map.get("defaultShipmentBoxTypeId"));
         valueMap.put("productId", map.get("productId"));
         //        valueMap.put("lotIdFilledIn", map.get("lotIdFilledIn"));
         valueMap.put("productWeight", map.get("productWeight"));
         valueMap.put("lastModifiedDate", map.get("lastModifiedDate"));
         valueMap.put("diameterUomId", map.get("diameterUomId"));
         valueMap.put("shippingHeight", map.get("shippingHeight"));
         valueMap.put("productName", map.get("productName"));
         valueMap.put("inventoryMessage", map.get("inventoryMessage"));
         valueMap.put("lastUpdatedTxStamp", map.get("lastUpdatedTxStamp"));
         valueMap.put("comments", map.get("comments"));
         valueMap.put("createdByUserLogin", map.get("createdByUserLogin"));
         */
        return valueMap;
    }

    @Override
    protected void process(List<BigFishProduct> chunks) {
        personListModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    private void sleepAWhile() {
        try {
            Thread.yield();
            long timeToSleep = getTimeToSleep();
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
        }
    }

    private long getTimeToSleep() {
        return loadPersonsSpeedModel.getValue();
    }

    public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
        this.loadPersonsSpeedModel = loadPersonsSpeedModel;

    }

    private ClassLoader getClassLoader() {
        Debug.logInfo("class loader 1", module);
        ClassLoader cl = null;
        try {
            cl = session.getClassLoader();
            Debug.logInfo("class loader 2", module);
            if (cl == null) {
                try {
                    Debug.logInfo("class loader 3", module);
                    cl = Thread.currentThread().getContextClassLoader();
                    Debug.logInfo("class loader 4", module);
                } catch (Throwable t) {
                    Debug.logInfo("class loader 5", module);
                }
                if (cl == null) {
                    Debug.log("No context classloader available; using class classloader", module);
                    try {
                        cl = this.getClass().getClassLoader();
                    } catch (Throwable t) {
                        Debug.logError(t, module);
                    }
                }
            }
        } catch (Exception e) {
            Debug.logError(e, module);
        }
        return cl;
    }

    class StringMetrics {

        Font font;
        FontRenderContext context;

        public StringMetrics(Graphics2D g2) {

            font = g2.getFont();
            context = g2.getFontRenderContext();
        }

        Rectangle2D getBounds(String message) {

            return font.getStringBounds(message, context);
        }

        double getWidth(String message) {

            Rectangle2D bounds = getBounds(message);
            return bounds.getWidth();
        }

        double getHeight(String message) {

            Rectangle2D bounds = getBounds(message);
            return bounds.getHeight();
        }

    }
}
