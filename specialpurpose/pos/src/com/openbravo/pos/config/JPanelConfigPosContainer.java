//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import java.awt.Component;
import java.util.Locale;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.BasePickerEditPanel;
import org.ofbiz.ordermax.base.components.EntityComponentFactory;
import org.ofbiz.ordermax.base.components.LookupActionListnerInterface;
import org.ofbiz.ordermax.base.components.PartyPickerEditPanel;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;

/**
 *
 * @author adrianromero
 */
public class JPanelConfigPosContainer extends javax.swing.JPanel implements PanelConfig {

    private DirtyManager dirty = new DirtyManager();

    private final static String DEFAULT_VALUE = "(Default)";
    //  private JGenericComboBoxSelectionModel<PosTerminal> listSelectionModel = null;
    //  private JGenericComboBoxSelectionModel<ProductStore> productStoreCombo = null;
    // public JGenericComboBoxSelectionModel<Facility> facilityModel = null;
    // public JGenericComboBoxSelectionModel<ProdCatalog> prodCatalogComboModel = null;
    // private JGenericComboBoxSelectionModel<ProductCategory> prodCatalogCategoryComboModel = null;
    private ReportCreatorSelectionInterface catalogPickerPanel = null;
    private ReportCreatorSelectionInterface categoryPickerPanel = null;
    private ReportCreatorSelectionInterface partyPickerEditPanel = null;
    private ReportCreatorSelectionInterface facilityIdEditPanel = null;
    private ReportCreatorSelectionInterface productStoreIdEditPanel = null;
    private ReportCreatorSelectionInterface posTerminalIdEditPanel = null;
    private ReportCreatorSelectionInterface productStoreCatalogIdEditPanel = null;
    private ReportCreatorSelectionInterface prodCatalogCategoryEditPanel = null;

    /**
     * Creates new form JPanelConfigLocale
     */
        public JPanelConfigPosContainer() {
        initComponents();
        try {

            txtStartupDir.addActionListener(dirty);
            txtStartupFile.addActionListener(dirty);
            txtClassPackageName.addActionListener(dirty);
            txtDispatcherName.addActionListener(dirty);
            txtDelegatorName.addActionListener(dirty);
            jcboLAF.addActionListener(dirty);

            ControllerOptions controllerOptions = new ControllerOptions();
            controllerOptions.setDoubleClickCloseDialog();
            catalogPickerPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.CatalogId, controllerOptions, panelCatalogId);
            ControllerOptions categoryOptions = new ControllerOptions();
            categoryPickerPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.CategoryId, categoryOptions, panelCategoryId);

            partyPickerEditPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.PartyId, controllerOptions, panelBillToParty);
            // new PartyPickerEditPanel(controllerOptions, panelBillToParty);
            ControllerOptions facilityOptions = new ControllerOptions();
            facilityIdEditPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.FacilityId, facilityOptions, panelFacilityId);

            ControllerOptions psOptions = new ControllerOptions();
            controllerOptions.setDoubleClickCloseDialog();
            productStoreIdEditPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.ProductStoreId, psOptions, panelProductStore);
            ControllerOptions ptOptions = new ControllerOptions();
            posTerminalIdEditPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.TerminalId, ptOptions, panelTerminalId);

            ControllerOptions pscOptions = new ControllerOptions();
            productStoreCatalogIdEditPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.ProductStoreCatalogId, pscOptions, panelProductStoreCatalog);
            ControllerOptions pccOptions = new ControllerOptions();
            prodCatalogCategoryEditPanel = EntityComponentFactory.createControl(LookupActionListnerInterface.LookupType.ProdCatalogCategoryId, pccOptions, panelProdCatalogCategory);
//        jcboDate.addActionListener(dirty);
            // jcboTime.addActionListener(dirty);
            // jcboDatetime.addActionListener(dirty);

            List<Locale> availablelocales = new ArrayList<Locale>();
            availablelocales.addAll(Arrays.asList(Locale.getAvailableLocales())); // Available java locales
            addLocale(availablelocales, new Locale("eu", "ES", "")); // Basque
            addLocale(availablelocales, new Locale("gl", "ES", "")); // Gallegan

            Collections.sort(availablelocales, new LocaleComparator());
            // Installed skins
            UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
            for (int i = 0; i < lafs.length; i++) {
                jcboLAF.addItem(new LAFInfo(lafs[i].getName(), lafs[i].getClassName()));
            }
            jcboLAF.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    changeLAF();
                }
            });

            jcbLocale.addItem(new LocaleInfo(null));
            for (Locale l : availablelocales) {
                jcbLocale.addItem(new LocaleInfo(l));
            }
            /*
             listSelectionModel = new JGenericComboBoxSelectionModel<PosTerminal>(panelTerminalId, PosTerminalSingleton.getValueList());
             //facilityModel = new JGenericComboBoxSelectionModel<Facility>(panelFacilityId, FacilitySingleton.getValueList());
            
             productStoreCombo = new JGenericComboBoxSelectionModel<ProductStore>(ProductStoreSingleton.getValueList());
             panelProductStore.setLayout(new BorderLayout());
             panelProductStore.add(BorderLayout.CENTER, productStoreCombo.jComboBox);
             */
            /*
             jcboInteger.addItem(DEFAULT_VALUE);
             jcboInteger.addItem("#0");
             jcboInteger.addItem("#,##0");
            
             jcboDouble.addItem(DEFAULT_VALUE);
             jcboDouble.addItem("#0.0");
             jcboDouble.addItem("#,##0.#");
            
             jcboCurrency.addItem(DEFAULT_VALUE);
             jcboCurrency.addItem("\u00A4 #0.00");
             jcboCurrency.addItem("'$' #,##0.00");
            
             jcboPercent.addItem(DEFAULT_VALUE);
             jcboPercent.addItem("#,##0.##%");
             */
//        jcboDate.addItem(DEFAULT_VALUE);
//        jcboDate.addItem(DEFAULT_VALUE);
//        jcboTime.addItem(DEFAULT_VALUE);
//        jcboDatetime.addItem(DEFAULT_VALUE);
        } catch (Exception ex) {
            Logger.getLogger(JPanelConfigPosContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addLocale(List<Locale> ll, Locale l) {
        if (!ll.contains(l)) {
            ll.add(l);
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public Component getConfigComponent() {
        return this;
    }

    @Override
    public void loadProperties(AppConfig config) {
        String lafclass = config.getPosContainerProperty("look-and-feel");
        jcboLAF.setSelectedItem(null);
        for (int i = 0; i < jcboLAF.getItemCount(); i++) {
            LAFInfo lafinfo = (LAFInfo) jcboLAF.getItemAt(i);
            if (lafinfo.getClassName().equals(lafclass)) {
                jcboLAF.setSelectedIndex(i);
                break;
            }
        }
        /*
         String slang = config.getProperty("user.language");
         String scountry = config.getProperty("user.country");
         String svariant = config.getProperty("user.variant");

         if (slang != null && !slang.equals("") && scountry != null && svariant != null) {
         Locale currentlocale = new Locale(slang, scountry, svariant);
         for (int i = 0; i < jcbLocale.getItemCount(); i++) {
         LocaleInfo l = (LocaleInfo) jcbLocale.getItemAt(i);
         if (currentlocale.equals(l.getLocale())) {
         jcbLocale.setSelectedIndex(i);
         break;
         }
         }
         } else {
         jcbLocale.setSelectedIndex(0);
         }
         */
        //      pos-container
/*key:startup-directory Value= specialpurpose\pos\config\
         key:startup-file Value= xpos.properties
         key:class-package-name  Value= net.xoetrope.swing
         key:dispatcher-name Value= POSDispatcher
         key:delegator-name Value= default
         key:xui-session-id Value= pos-1
         key:facility-id Value= MAX_FAC
         key:product-store-id Value= MAXS_STORE_ID
         key:locale Value= en_AU
         key:look-and-feel Value= com.jgoodies.looks.plastic.PlasticLookAndFeel
         key:def-catalog-id Value= MS_CAT
         key:def-category-id Value= MS_CAT_ROOT
         key:def-bill-to-party Value= COMPANY
         */
        txtStartupDir.setText(writeWithDefault(config.getPosContainerProperty("startup-directory")).toString());
        txtStartupFile.setText(writeWithDefault(config.getPosContainerProperty("startup-file")).toString());
        txtClassPackageName.setText(writeWithDefault(config.getPosContainerProperty("class-package-name")).toString());
        txtDispatcherName.setText(writeWithDefault(config.getPosContainerProperty("dispatcher-name")).toString());
        txtDelegatorName.setText(writeWithDefault(config.getPosContainerProperty("delegator-name")).toString());
        catalogPickerPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("def-catalog-id")).toString());
        categoryPickerPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("def-category-id")).toString());
        partyPickerEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("ORGANIZATION_PARTY")).toString());
        facilityIdEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("facility-id")).toString());
        productStoreIdEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("product-store-id")).toString());
        posTerminalIdEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("xui-session-id")).toString());

        Locale locale = UtilMisc.parseLocale(config.getPosContainerProperty("locale"));
        for (int i = 0; i < jcbLocale.getItemCount(); i++) {
            LocaleInfo l = (LocaleInfo) jcbLocale.getItemAt(i);
            if (locale.equals(l.getLocale())) {
                jcbLocale.setSelectedIndex(i);
                break;
            }
        }

        dirty.setDirty(false);
    }

    @Override
    public void saveProperties(AppConfig config) {

        Locale l = ((LocaleInfo) jcbLocale.getSelectedItem()).getLocale();
        /*      if (l == null) {
         config.setProperty("user.language", "");
         config.setProperty("user.country", "");
         config.setProperty("user.variant", "");
         } else {
         config.setProperty("user.language", l.getLanguage());
         config.setProperty("user.country", l.getCountry());
         config.setProperty("user.variant", l.getVariant());
         }
         */
        /*        
         txtStartupDir.setText(writeWithDefault(config.getPosContainerProperty("startup-directory")).toString());
         txtStartupFile.setText(writeWithDefault(config.getPosContainerProperty("startup-file")).toString());
         txtClassPackageName.setText(writeWithDefault(config.getPosContainerProperty("class-package-name")).toString());
         txtDispatcherName.setText(writeWithDefault(config.getPosContainerProperty("dispatcher-name")).toString());
         txtDelegatorName.setText(writeWithDefault(config.getPosContainerProperty("delegator-name")).toString());
         catalogPickerPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("def-catalog-id")).toString());
         categoryPickerPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("def-category-id")).toString());
         partyPickerEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("def-bill-to-party")).toString());
         facilityIdEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("facility-id")).toString());
         productStoreIdEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("product-store-id")).toString());
         posTerminalIdEditPanel.setEntityValue(writeWithDefault(config.getPosContainerProperty("xui-session-id")).toString());
         */
        System.out.println("\"startup-directory\" key:" + readWithDefault(txtStartupDir.getText()));
        config.setPosContainerProperty("startup-directory", readWithDefault(txtStartupDir.getText()));
        config.setPosContainerProperty("startup-file", readWithDefault(txtStartupFile.getText()));
        config.setPosContainerProperty("class-package-name", readWithDefault(txtClassPackageName.getText()));
        config.setPosContainerProperty("dispatcher-name", readWithDefault(txtDispatcherName.getText()));
        config.setPosContainerProperty("delegator-name", readWithDefault(txtDelegatorName.getText()));
        config.setPosContainerProperty("def-catalog-id", readWithDefault(catalogPickerPanel.getEntityValue()));
        config.setPosContainerProperty("def-category-id", readWithDefault(categoryPickerPanel.getEntityValue()));
        config.setPosContainerProperty("ORGANIZATION_PARTY", readWithDefault(partyPickerEditPanel.getEntityValue()));
        config.setPosContainerProperty("facility-id", readWithDefault(facilityIdEditPanel.getEntityValue()));
        config.setPosContainerProperty("product-store-id", readWithDefault(productStoreIdEditPanel.getEntityValue()));
        config.setPosContainerProperty("xui-session-id", readWithDefault(posTerminalIdEditPanel.getEntityValue()));
        if (l != null) {
            String localeStr = l.getLanguage() + "_" + l.getCountry();
            config.setPosContainerProperty("locale", readWithDefault(localeStr));
        }

        LAFInfo laf = (LAFInfo) jcboLAF.getSelectedItem();
        config.setPosContainerProperty("look-and-feel", laf == null
                ? System.getProperty("swing.defaultlaf", "javax.swing.plaf.metal.MetalLookAndFeel")
                : laf.getClassName());
//        config.setPosContainerProperty("look-and-feel", DEFAULT_VALUE);
        dirty.setDirty(false);
    }

    private String readWithDefault(Object value) {
        if (DEFAULT_VALUE.equals(value)) {
            return "";
        } else {
            return value.toString();
        }
    }

    private Object writeWithDefault(String value) {
        if (value == null || value.equals("") || value.equals(DEFAULT_VALUE)) {
            return DEFAULT_VALUE;
        } else {
            return value.toString();
        }
    }

    private static class LocaleInfo {

        private Locale locale;

        public LocaleInfo(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }

        @Override
        public String toString() {
            return locale == null
                    ? "(System default)"
                    : locale.getDisplayName();
        }
    }

    private static class LAFInfo {

        private String name;
        private String classname;

        public LAFInfo(String name, String classname) {
            this.name = name;
            this.classname = classname;
        }

        public String getName() {
            return name;
        }

        public String getClassName() {
            return classname;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcbLocale = new javax.swing.JComboBox();
        jcboLAF = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtStartupDir = new javax.swing.JTextField();
        txtStartupFile = new javax.swing.JTextField();
        txtClassPackageName = new javax.swing.JTextField();
        txtDispatcherName = new javax.swing.JTextField();
        txtDelegatorName = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        panelCatalogId = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        panelProductStore = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        panelCategoryId = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        panelProdCatalogCategory = new javax.swing.JPanel();
        panelProductStoreCatalog = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        panelBillToParty = new javax.swing.JPanel();
        panelFacilityId = new javax.swing.JPanel();
        panelTerminalId = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(AppLocal.getIntString("label.poscontainer")));

        jLabel5.setText(AppLocal.getIntString("label.startup_directory")); // NOI18N

        jLabel1.setText(AppLocal.getIntString("label.startup_file")); // NOI18N

        jLabel2.setText(AppLocal.getIntString("label.class_package_name")); // NOI18N

        jLabel3.setText(AppLocal.getIntString("label.dispatcher_name")); // NOI18N

        jLabel4.setText(AppLocal.getIntString("label.delegator_name")); // NOI18N

        jLabel9.setText(AppLocal.getIntString("label.locale")); // NOI18N

        jcbLocale.setEditable(true);

        jcboLAF.setEditable(true);

        jLabel10.setText(AppLocal.getIntString("label.looknfeel")); // NOI18N

        jLabel11.setText(AppLocal.getIntString("label.def_catalog_id")); // NOI18N

        panelCatalogId.setPreferredSize(new java.awt.Dimension(189, 24));

        javax.swing.GroupLayout panelCatalogIdLayout = new javax.swing.GroupLayout(panelCatalogId);
        panelCatalogId.setLayout(panelCatalogIdLayout);
        panelCatalogIdLayout.setHorizontalGroup(
            panelCatalogIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelCatalogIdLayout.setVerticalGroup(
            panelCatalogIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel8.setText(AppLocal.getIntString("label.product_store_id")); // NOI18N

        panelProductStore.setPreferredSize(new java.awt.Dimension(189, 24));

        javax.swing.GroupLayout panelProductStoreLayout = new javax.swing.GroupLayout(panelProductStore);
        panelProductStore.setLayout(panelProductStoreLayout);
        panelProductStoreLayout.setHorizontalGroup(
            panelProductStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelProductStoreLayout.setVerticalGroup(
            panelProductStoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel12.setText(AppLocal.getIntString("label.def_category_id")); // NOI18N

        javax.swing.GroupLayout panelCategoryIdLayout = new javax.swing.GroupLayout(panelCategoryId);
        panelCategoryId.setLayout(panelCategoryIdLayout);
        panelCategoryIdLayout.setHorizontalGroup(
            panelCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelCategoryIdLayout.setVerticalGroup(
            panelCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel14.setText(AppLocal.getIntString("label.prod_catalog_category_id")); // NOI18N

        javax.swing.GroupLayout panelProdCatalogCategoryLayout = new javax.swing.GroupLayout(panelProdCatalogCategory);
        panelProdCatalogCategory.setLayout(panelProdCatalogCategoryLayout);
        panelProdCatalogCategoryLayout.setHorizontalGroup(
            panelProdCatalogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelProdCatalogCategoryLayout.setVerticalGroup(
            panelProdCatalogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelProductStoreCatalogLayout = new javax.swing.GroupLayout(panelProductStoreCatalog);
        panelProductStoreCatalog.setLayout(panelProductStoreCatalogLayout);
        panelProductStoreCatalogLayout.setHorizontalGroup(
            panelProductStoreCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelProductStoreCatalogLayout.setVerticalGroup(
            panelProductStoreCatalogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel15.setText(AppLocal.getIntString("label.product_store_catalog")); // NOI18N

        jLabel13.setText(AppLocal.getIntString("label.def_bill_to_party")); // NOI18N

        panelBillToParty.setPreferredSize(new java.awt.Dimension(189, 24));

        javax.swing.GroupLayout panelBillToPartyLayout = new javax.swing.GroupLayout(panelBillToParty);
        panelBillToParty.setLayout(panelBillToPartyLayout);
        panelBillToPartyLayout.setHorizontalGroup(
            panelBillToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelBillToPartyLayout.setVerticalGroup(
            panelBillToPartyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        panelFacilityId.setPreferredSize(new java.awt.Dimension(189, 24));
        panelFacilityId.setRequestFocusEnabled(false);

        javax.swing.GroupLayout panelFacilityIdLayout = new javax.swing.GroupLayout(panelFacilityId);
        panelFacilityId.setLayout(panelFacilityIdLayout);
        panelFacilityIdLayout.setHorizontalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelFacilityIdLayout.setVerticalGroup(
            panelFacilityIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelTerminalIdLayout = new javax.swing.GroupLayout(panelTerminalId);
        panelTerminalId.setLayout(panelTerminalIdLayout);
        panelTerminalIdLayout.setHorizontalGroup(
            panelTerminalIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        panelTerminalIdLayout.setVerticalGroup(
            panelTerminalIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel6.setText(AppLocal.getIntString("label.xui_session_id")); // NOI18N

        jLabel7.setText(AppLocal.getIntString("label.facility_id")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTerminalId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelProductStore, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelCatalogId, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelProdCatalogCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelProductStoreCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelBillToParty, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelBillToParty, panelCatalogId, panelCategoryId, panelFacilityId, panelProdCatalogCategory, panelProductStore, panelProductStoreCatalog, panelTerminalId});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel12, jLabel13, jLabel14, jLabel15, jLabel6, jLabel7, jLabel8});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(panelBillToParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTerminalId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(panelFacilityId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(panelProductStore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(panelCatalogId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(panelCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(panelProdCatalogCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(panelProductStoreCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStartupDir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStartupFile))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClassPackageName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDispatcherName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDelegatorName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbLocale, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboLAF, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel10, jLabel2, jLabel3, jLabel4, jLabel5, jLabel9});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtStartupDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtStartupFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtClassPackageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtDispatcherName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDelegatorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jcbLocale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jcboLAF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox jcbLocale;
    private javax.swing.JComboBox jcboLAF;
    private javax.swing.JPanel panelBillToParty;
    private javax.swing.JPanel panelCatalogId;
    private javax.swing.JPanel panelCategoryId;
    private javax.swing.JPanel panelFacilityId;
    private javax.swing.JPanel panelProdCatalogCategory;
    private javax.swing.JPanel panelProductStore;
    private javax.swing.JPanel panelProductStoreCatalog;
    private javax.swing.JPanel panelTerminalId;
    private javax.swing.JTextField txtClassPackageName;
    private javax.swing.JTextField txtDelegatorName;
    private javax.swing.JTextField txtDispatcherName;
    private javax.swing.JTextField txtStartupDir;
    private javax.swing.JTextField txtStartupFile;
    // End of variables declaration//GEN-END:variables
 private void changeLAF() {

        final LAFInfo laf = (LAFInfo) jcboLAF.getSelectedItem();
        if (laf != null && !laf.getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
            // The selected look and feel is different from the current look and feel.
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    try {
                        String lafname = laf.getClassName();
                        Object laf = Class.forName(lafname).newInstance();

                        if (laf instanceof LookAndFeel) {
                         //su   UIManager.setLookAndFeel((LookAndFeel) laf);
                        } /*else if (laf instanceof SubstanceSkin) {
                         SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                         }*/

                        SwingUtilities.updateComponentTreeUI(JPanelConfigPosContainer.this.getTopLevelAncestor());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }
}
