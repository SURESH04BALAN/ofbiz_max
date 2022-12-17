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
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import mvc.model.list.ListAdapterListModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import mvc.controller.LoadProductPriceWorker;
import mvc.controller.LoadProductWorker;
import mvc.controller.SwingWorkerProgressModel;
import mvc.controller.SwingWorkerPropertyChangeListener;
import mvc.view.SwingWorkerBasedComponentVisibility;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.transaction.GenericTransactionException;
import org.ofbiz.entity.transaction.TransactionUtil;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductPriceComposite;
import org.ofbiz.ordermax.composite.SupplierProductComposite;
import org.ofbiz.ordermax.dataimportexport.ProductLoadViewPanel;
import org.ofbiz.ordermax.entity.GoodIdentification;
import org.ofbiz.ordermax.product.SupplierPrefOrderSingleton;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class LoadJoshProductWorker extends LoadGroceryAndScaleProductWorker {

    public static final String module = LoadJoshProductWorker.class.getName();
    protected volatile int maxProgress;
    protected int progressedItems;
    protected ListAdapterListModel<ProductComposite> productListModel;
    protected BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
    protected String csvFile;
    protected XuiSession session = null;

    public LoadJoshProductWorker(ListAdapterListModel<ProductComposite> productListModel, String fileName, XuiSession delegator) {
        super(productListModel, fileName, delegator);
        this.productListModel = productListModel;
        csvFile = fileName;
        this.session = delegator;

    }

    @Override
    protected List<ProductComposite> doInBackground() throws Exception {
        Debug.logInfo("doInBackground ", module);
        productListModel.clear();
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

        Debug.logInfo("doInBackground ", module);
        try {

            try {
                Reader csvFileIo = null;
                try {

                    csvFileIo = new BufferedReader(new FileReader(csvFile));//new InputStreamReader(is);
                } catch (Exception e) {
                    Debug.logError(e, module);
                }
                List<ProductComposite> personsRead = null;
                try {

                    CSVReader<ProductComposite> personReader = new CSVReaderBuilder<ProductComposite>(csvFileIo).entryParser(new JoshProductCompositeEntryParser()).build();
                    personsRead = personReader.readAll();
                } catch (Exception e) {
                    Debug.logError(e, "Unable to rollback transaction", module);
                }

                maxProgress = personsRead.size();

                Map<String, String> supplierList = createSupplierList(personsRead);

                Map<String, String> categories = new HashMap<String, String>();
                for (ProductComposite prod : personsRead) {
                    String catId = prod.getProduct().getprimaryProductCategoryId();
                    if (UtilValidate.isNotEmpty(catId)) {
                        categories.put(prod.getProduct().getbrandName(), prod.getProduct().getprimaryProductCategoryId());
                    }
                }

                System.out.println("Start create product");
                productListModel.clear();
                for (ProductComposite productComposite : personsRead) {

                    createCategory(productComposite.getProduct(), session);

                    Map<String, Object> valueMap = productComposite.getProduct().getValuesMap();
                    String productId = productComposite.getProduct().getproductId();
                    String brand = productComposite.getProduct().getbrandName();
                    String productCategoryId = brand.substring(0, brand.length() < 20 ? brand.length() : 19);
//                String productCategoryId = map.get("PRIMARY_PRODUCT_CATEGORY_ID");
                    System.out.println("Start create product productId: " + productId);
                    //ProductComposite productComposite = new ProductComposite();

                    //productComposite.setProduct(product.getProduct());
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
                            //                        featureMapSel
                            boolean beganTransaction = false;
                            try {
                                beganTransaction = TransactionUtil.begin();

                                resultMap = session.getDispatcher().runSync("createProductInCategory", valueMap);
                                for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
                                    Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
                                }
                                System.out.println("create Suppier product");
                                saveSupplierProduct(supplierList, productComposite);
                                saveGoodIdentification(productComposite);
                                savePrices(productComposite);

                            } catch (GenericTransactionException e) {
                                Debug.logError(e, module);
                                try {
                                    TransactionUtil.rollback(beganTransaction, e.getMessage(), e);
                                } catch (GenericTransactionException e2) {
                                    Debug.logError(e2, "Unable to rollback transaction", module);
                                }
                            } finally {
                                try {
                                    TransactionUtil.commit(beganTransaction);
                                } catch (GenericTransactionException e) {
                                    Debug.logError(e, "Unable to commit transaction", module);
                                }
                            }
                        } catch (Exception exc) {
                            Debug.logError(exc, module);
                            break;
                        }
                    }

                }
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

        return persons;
    }

    @Override
    protected void process(List<ProductComposite> chunks) {
        productListModel.addAll(chunks);
        progressedItems = progressedItems + chunks.size();
        setProgress(calcProgress(progressedItems));
    }

    private int calcProgress(int progressedItems) {
        int progress = (int) ((100.0 / (double) maxProgress) * (double) progressedItems);
        return progress;
    }

    protected void sleepAWhile() {
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

    static public class LoadJoshProductAction extends AbstractAction {

        /**
         *
         */
        private static final long serialVersionUID = 2636985714796751517L;
        private ListAdapterListModel<ProductComposite> productListModel;
        private Collection<SwingWorkerPropertyChangeListener> swingWorkerPropertyChangeListeners = new HashSet<SwingWorkerPropertyChangeListener>();
        private BoundedRangeModel loadPersonsSpeedModel = new DefaultBoundedRangeModel();
        private JTextField textField = null;
        private ListAdapterListModel<ProductComposite> productCompListModel = new ListAdapterListModel<ProductComposite>();
        public ProductLoadViewPanel productCompLoadViewPanel = new ProductLoadViewPanel();

        public LoadJoshProductAction(SwingWorkerProgressModel swingWorkerPropertyChangeListener,
                SwingWorkerBasedComponentVisibility swingWorkerBasedComponentVisibility,
                BoundedRangeModel boundedRangeModel) {
            addSwingWorkerPropertyChangeListener(swingWorkerPropertyChangeListener);
            addSwingWorkerPropertyChangeListener(swingWorkerBasedComponentVisibility);
            setLoadSpeedModel(boundedRangeModel);
            productCompLoadViewPanel.jBtnLoad.addActionListener(this);

            this.textField = productCompLoadViewPanel.textEdit;
        }

        @Override
        public Object[] getKeys() {
            return new Object[]{Action.NAME};
        }

        @Override
        public Object getValue(String key) {
            if (Action.NAME.equals(key)) {
                return "Load Josh's Product List";
            } else if (Action.ACCELERATOR_KEY.equals(key)) {
                return KeyStroke.getKeyStroke('S');
            }
            return super.getValue(key);
        }

        public void actionPerformed(ActionEvent e) {
//        LoadProductFromFileWorker loadPersonsWorker = new LoadProductFromFileWorker(productListModel,"C:\\backup\\Real_Data\\seven_product_final.csv", session);
//    LoadProductFromFileWorker loadPersonsWorker = new LoadProductFromFileWorker(productListModel,"C:\\backup\\Real_Data\\generated_product_load.csv", session);        
            LoadJoshProductWorker loadPersonsWorker = new LoadJoshProductWorker(productCompListModel, textField.getText(), ControllerOptions.getSession());
            Debug.logInfo("actionPerformed ", module);
            loadPersonsWorker.setLoadSpeedModel(loadPersonsSpeedModel);
            Debug.logInfo("actionPerformed 1", module);
            for (SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener : swingWorkerPropertyChangeListeners) {
                swingWorkerPropertyChangeListener
                        .attachPropertyChangeListener(loadPersonsWorker);
                Debug.logInfo("actionPerformed 1.5", module);
            }
            Debug.logInfo("actionPerformed 2", module);
            loadPersonsWorker.execute();
            /*try {
             loadPersonsWorker.doInBackground();
             } catch (Exception ex) {
             Logger.getLogger(LoadGroceryAndScaleProductWorker.class.getName()).log(Level.SEVERE, null, ex);
             }*/
            Debug.logInfo("actionPerformed done", module);
        }

        public void addSwingWorkerPropertyChangeListener(
                SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
            swingWorkerPropertyChangeListeners.add(swingWorkerPropertyChangeListener);
        }

        public void removeSwingWorkerPropertyChangeListener(
                SwingWorkerPropertyChangeListener swingWorkerPropertyChangeListener) {
            swingWorkerPropertyChangeListeners.remove(swingWorkerPropertyChangeListener);
        }

        public void setLoadSpeedModel(BoundedRangeModel loadPersonsSpeedModel) {
            this.loadPersonsSpeedModel = loadPersonsSpeedModel;
        }
    }

    static public class LoadViewPanel extends ProductLoadViewPanel {

    }

    static public class JoshProductCompositeEntryParser implements CSVEntryParser<ProductComposite> {

        @Override
        public ProductComposite parseEntry(String... data) {

            ProductComposite productComposite = LoadProductWorker.newProduct();
            org.ofbiz.ordermax.entity.Product prod = productComposite.getProduct();
//        productComposite.setProduct(prod);
// Stock Code, Stock Name,Brand Name, Department, SCAN CODE, item sale type(ea - each),
//Require Inv for sale(N-No),Supplier Name, Supplier Price, Selling Price, Avg Coast, Stock at hand,GST,,,,,
            int i = 0;
            int length = data.length;
            Debug.logError("Field Length: " + length, null);
            prod.setproductId(i++ < length ? data[i - 1] : "");
            Debug.logError("Field" + i + ": " + (i < length ? data[i - 1] : ""), null);

            prod.setinternalName(i++ < length ? data[i - 1] : "");
            prod.setbrandName(i++ < length ? data[i - 1] : "");
            prod.setdepartmentName(i++ < length ? data[i - 1] : "");
            Debug.logError("Field" + i + "[departmentName]: " + (i < length ? data[i - 1] : ""), null);

            GoodIdentification goodIdentification = new GoodIdentification();
            goodIdentification.setproductId(productComposite.getProduct().getproductId());
            goodIdentification.setgoodIdentificationTypeId("EAN");
            goodIdentification.setidValue(i++ < length ? data[i - 1] : "");
            productComposite.getGoodIdentificationList().add(goodIdentification);

            prod.setproductName(prod.getinternalName());
            prod.setdescription(prod.getinternalName());
            prod.setlongDescription(prod.getinternalName());

            String qtyUom = i++ < length ? data[i - 1] : "OTH_ea";
            if ("EA".equalsIgnoreCase(qtyUom)) {
                qtyUom = "OTH_ea";
            }
            prod.setquantityUomId(qtyUom);


            Debug.logError("qtyUom" + qtyUom, null);

            //supplier product
            SupplierProductComposite spComp = LoadSupplierProductFromFileWorker.newSupplierProduct(productComposite.getProduct().getproductId());
            spComp.getSupplierProduct().setsupplierProductName(productComposite.getProduct().getinternalName());
            spComp.getSupplierProduct().setsupplierProductId(productComposite.getProduct().getproductId());
            spComp.getSupplierProduct().setcomments(i++ < length ? data[i - 1] : "_NA_");
//        spComp.getSupplierProduct().setpartyId("BigSupplier");
            Debug.logError("Field" + i + "[SupplierPartyId]: " + spComp.getSupplierProduct().getcomments(), null);

            spComp.getSupplierProduct().setcurrencyUomId((String) XuiContainer.getSession().getAttribute("currency"));
            BigDecimal lastPrice = BigDecimal.ONE;

            if (i++ < length) {
                String val = data[i - 1];
                if (UtilValidate.isNotEmpty(val)) {
                    lastPrice = OrderMaxUtility.getValidEntityBigDecimal(val);
                }
                spComp.getSupplierProduct().setlastPrice(lastPrice);
                Debug.logError("Field" + i + 1 + "[SupplierPrice]: " + data[i - 1], null);
            }
            productComposite.getSupplierProductList().add(spComp);
            BigDecimal defPrice = BigDecimal.ONE;

            if (i++ < length) {
                String val = data[i - 1];
                Debug.logError("Field" + i + "[DefaultPrice -org ]: " + val, null);
                if (UtilValidate.isNotEmpty(val)) {
                    defPrice = OrderMaxUtility.getValidEntityBigDecimal(val);
                }

                ProductPriceComposite productPriceDefComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
//            productPriceDefComposite.getProductPrice().setprice(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ONE);
                productPriceDefComposite.getProductPrice().setprice(defPrice);
                Debug.logError("Field" + i + "[DefaultPrice]: " + defPrice, null);

                ProductPriceComposite productPriceListComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
                productPriceListComposite.getProductPrice().setprice(defPrice);

                //          productPriceListComposite.getProductPrice().setprice(i < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ONE);
                //  Debug.logError("Field" + i+1 + "[ListPrice]: " + data[i-1], null);
                productPriceListComposite.getProductPrice().setproductPriceTypeId("LIST_PRICE");
                try {
                    productComposite.getProductItemPrice().addProductPrice(productPriceDefComposite);
                    productComposite.getProductItemPrice().addProductPrice(productPriceListComposite);
                } catch (Exception ex) {
                    Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            BigDecimal avgPrice = BigDecimal.ONE;
            if (i++ < length) {
                String val = data[i - 1];
                Debug.logError("Field" + i + "[CostPrice-org]: " + val, null);
                if (UtilValidate.isNotEmpty(val)) {
                    avgPrice = OrderMaxUtility.getValidEntityBigDecimal(val);
                } else {
                    avgPrice = spComp.getSupplierProduct().getlastPrice();
                }

                ProductPriceComposite productPriceCostComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
                productPriceCostComposite.getProductPrice().setprice(avgPrice);
                productPriceCostComposite.getProductPrice().setproductPriceTypeId("AVERAGE_COST");
                Debug.logError("Field" + i + "[CostPrice]: " + avgPrice, null);

                try {
//                if (productPriceCostComposite.getProductPrice().equals(BigDecimal.ZERO) == false) {
                    productComposite.getProductItemPrice().addProductPrice(productPriceCostComposite);
                    //               }
                } catch (Exception ex) {
                    Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Debug.logError("Field" + ++i + "[Stock At Hand]: " + data[i - 1], null);

            prod.settaxable(i++ < length ? data[i - 1] : "N");
            Debug.logError("Field" + i + "[Taxable]: " + data[i - 1], null);

            /*prod.setisVirtual(i++ < length ? data[i - 1] : "N");
             prod.setisVariant(i++ < length ? data[i - 1] : "N");
             prod.setproductTypeId(i++ < length ? data[i - 1] : "FINISHED_GOOD");
             //        Debug.logError("Field" + i + ": " + (i < length ? data[i - 1] : ""), null);

             prod.setmanufacturerPartyId(i++ < length ? data[i - 1] : "");
             prod.setfacilityId(i++ < length ? data[i - 1] : "");
             try {
             prod.setintroductionDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
             prod.setreleaseDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
             prod.setsupportDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }
             try {
             prod.setsalesDiscontinuationDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }
             prod.setsalesDiscWhenNotAvail(i++ < length ? data[i - 1] : "");
             prod.setpriceDetailText(i++ < length ? data[i - 1] : "");
             prod.setsmallImageUrl(i++ < length ? data[i - 1] : "");
             prod.setmediumImageUrl(i++ < length ? data[i - 1] : "");
             prod.setlargeImageUrl(i++ < length ? data[i - 1] : "");
             prod.setdetailImageUrl(i++ < length ? data[i - 1] : "");
             prod.setoriginalImageUrl(i++ < length ? data[i - 1] : "");
             prod.setdetailScreen(i++ < length ? data[i - 1] : "");
             prod.setinventoryMessage(i++ < length ? data[i - 1] : "");
             prod.setquantityIncluded(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setpiecesIncluded(i++ < length ? OrderMaxUtility.getValidEntityLong(data[i - 1]) : null);
             prod.setfixedAmount(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setamountUomTypeId(i++ < length ? data[i - 1] : "");
             prod.setweightUomId(i++ < length ? data[i - 1] : "");
             prod.setweight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setproductWeight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ZERO);

             prod.setheightUomId(i++ < length ? data[i - 1] : "");
             prod.setproductHeight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : BigDecimal.ZERO);
             prod.setshippingHeight(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setwidthUomId(i++ < length ? data[i - 1] : "");
             prod.setproductWidth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setshippingWidth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setdepthUomId(i++ < length ? data[i - 1] : "");
             prod.setproductDepth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setshippingDepth(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setdiameterUomId(i++ < length ? data[i - 1] : "");
             prod.setproductDiameter(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setproductRating(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setratingTypeEnum(i++ < length ? data[i - 1] : "");
             prod.setreturnable(i++ < length ? data[i - 1] : "Y");
             prod.setchargeShipping(i++ < length ? data[i - 1] : "N");
             prod.setautoCreateKeywords(i++ < length ? data[i - 1] : "N");
             prod.setincludeInPromotions(i++ < length ? data[i - 1] : "Y");
             prod.setvirtualVariantMethodEnum(i++ < length ? data[i - 1] : "");
             prod.setoriginGeoId(i++ < length ? data[i - 1] : "");
             prod.setrequirementMethodEnumId(i++ < length ? data[i - 1] : "PRODRQM_AUTO");
             prod.setbillOfMaterialLevel(i++ < length ? OrderMaxUtility.getValidEntityLong(data[i - 1]) : new Long(0));
             prod.setreservMaxPersons(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setreserv2ndPPPerc(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setreservNthPPPerc(i++ < length ? OrderMaxUtility.getValidEntityBigDecimal(data[i - 1]) : null);
             prod.setconfigId(i++ < length ? data[i - 1] : "");
             try {
             prod.setcreatedDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }
             prod.setcreatedByUserLogin(i++ < length ? data[i - 1] : "");
             try {
             prod.setlastModifiedDate(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }
             prod.setlastModifiedByUserLogin(i++ < length ? data[i - 1] : "");
             prod.setinShippingBox(i++ < length ? data[i - 1] : "");
             prod.setdefaultShipmentBoxTypeId(i++ < length ? data[i - 1] : "");
             prod.setorderDecimalQuantity(i++ < length ? data[i - 1] : "");
             try {
             prod.setlastUpdatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }

             try {
             prod.setlastUpdatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }

             try {
             prod.setcreatedStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }

             try {
             prod.setcreatedTxStamp(i++ < length ? OrderMaxUtility.getValidEntityTimestamp(data[i - 1]) : null);
             } catch (ParseException ex) {
             Logger.getLogger(ProductCompositeEntryParser.class.getName()).log(Level.SEVERE, null, ex);
             }
             prod.setlotIdFilledIn(i++ < length ? data[i - 1] : "");
             */
            return productComposite;
        }
    }
}
