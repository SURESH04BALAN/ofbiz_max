/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import mvc.controller.LoadProductWorker;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ProductContentTableModel;
import mvc.model.table.ProductStoreCatalogTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.CharacterSet;
import org.ofbiz.ordermax.entity.DataCategory;
import org.ofbiz.ordermax.entity.DataResource;
import org.ofbiz.ordermax.entity.DataResourceType;
import org.ofbiz.ordermax.entity.DataTemplateType;

import org.ofbiz.ordermax.entity.MimeType;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductContentType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.product.UomTimeSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class DataResourceMaintainPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    final public static String module = DataResourceMaintainPanel.class.getName();
    private ListAdapterListModel<ProdCatalog> personListModel = new ListAdapterListModel<ProdCatalog>();
    public GenericTableModelPanel<ProductContentComposite, ProductContentTableModel> tablePanel = null;

//    private ListModelSelection<ProdCatalog> listModelSelection = new ListModelSelection<ProdCatalog>();
//    private ListSelectionModel selectionModel = null; //new DefaultListSelectionModel();
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
    DataResource dataResource = null;

    boolean isNew = false;
    boolean isModified = false;
    private DatePickerEditPanel datePickerCreateDate = null;
    private DatePickerEditPanel datePickerModifiedDate = null;
    private JGenericComboBoxSelectionModel<ProductContentType> comboProductContentType = null;
    private JGenericComboBoxSelectionModel<Uom> comboUom = null;

    private JGenericComboBoxSelectionModel<DataResourceType> comboDataResourceTypeId = null;
    private JGenericComboBoxSelectionModel<DataTemplateType> comboDataTemplateTypeId = null;
    private JGenericComboBoxSelectionModel<StatusItem> comboStatusItem = null;
    private JGenericComboBoxSelectionModel<MimeType> comboMimeType = null;
    private JGenericComboBoxSelectionModel<CharacterSet> comboCharacterSet = null;
    private JGenericComboBoxSelectionModel<DataCategory> comboDataCategory = null;
    private JGenericComboBoxSelectionModel<String> comboLocale = null;

    public JGenericComboBoxSelectionModel<String> comboIsPublic = null;

    public DataResourceMaintainPanel() {
        initComponents();
        /* ListCellRenderer<ProdCatalog> prodCatalogRenderer = new ProdCatalogListCellRenderer();
         prodCatalogList.setCellRenderer(prodCatalogRenderer);
         prodCatalogList.setEnabled(true);
         prodCatalogList.setSelectionBackground(Color.CYAN);
         */
        tablePanel = new GenericTableModelPanel<ProductContentComposite, ProductContentTableModel>(new ProductContentTableModel());
//        panelHeader.setLayout(new BorderLayout());
//        panelHeader.add(BorderLayout.CENTER, tablePanel);
        datePickerCreateDate = DatePickerEditPanel.addToPanel(panelCreatedDate);
        datePickerModifiedDate = DatePickerEditPanel.addToPanel(panelLastModifiedDate);

        for (int i = 0; i < ProductStoreCatalogTableModel.Columns.values().length; i++) {
            ProductStoreCatalogTableModel.Columns[] columns = ProductStoreCatalogTableModel.Columns.values();
            ProductStoreCatalogTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        comboProductContentType = new JGenericComboBoxSelectionModel<ProductContentType>(panelDataResourceTypeId, ProductContentTypeSingleton.getValueList()
                );

        comboUom = new JGenericComboBoxSelectionModel<Uom>(panelDataCategoryId, UomTimeSingleton.getValueList()
                );

        comboDataResourceTypeId = new JGenericComboBoxSelectionModel<DataResourceType>(panelDataResourceTypeId, DataResourceTypeSingleton.getValueList()
                );

        comboDataTemplateTypeId = new JGenericComboBoxSelectionModel<DataTemplateType>(panelDataTemplateTypeId, DataTemplateTypeSingleton.getValueList()
                );

        comboStatusItem = new JGenericComboBoxSelectionModel<StatusItem>(panelStatusID, StatusSingleton.getValueList("CONTENT_STATUS")
                );
        comboMimeType = new JGenericComboBoxSelectionModel<MimeType>(panelMimeTypeId, MIMETypeSingleton.getValueList()
                );

        comboCharacterSet = new JGenericComboBoxSelectionModel<CharacterSet>(panelCharacterSetId, CharacterSetSingleton.getValueList()
                );

        comboIsPublic = new JGenericComboBoxSelectionModel<String>(panelIsPublic, YesNoConditionSelectSingleton.getValueList()
                );

        comboDataCategory = new JGenericComboBoxSelectionModel<DataCategory>(panelDataCategoryId, DataCategorySingleton.getValueList()
                );

        comboLocale = new JGenericComboBoxSelectionModel<String>(panelLocaleString, YesNoConditionSelectSingleton.getValueList()
                );

        tablePanel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                ListSelectionModel lsm = (ListSelectionModel) tablePanel.selectionModel;//listSelectionModel.selectionModel;
                if (!listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()) {
                    // Find out which indexes are selected.
                    int minIndex = lsm.getMinSelectionIndex();
                    int maxIndex = lsm.getMaxSelectionIndex();
                    for (int i = minIndex; i <= maxIndex; i++) {
                        if (lsm.isSelectedIndex(i)) {
                            System.out.println(" " + i);
                            clearDialogFields();
                            //setDialogFields(tablePanel.listModel.getElementAt(i));
                            break;
                        }
                    }
                }
            }
        });

        /*panelHeader.add(BorderLayout.CENTER, scrollPane);
         catalogListSelectionModel.selectionModel.addListSelectionListener(new ListSelectionListener() {

         public void valueChanged(ListSelectionEvent e) {
         prodCatalog = catalogListSelectionModel.listModelSelection.getSelection();
         setDialogFields(prodCatalog);
         isNew = false;
         }
         });
         */
        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
//        ComponentBorder.loweredBevelBorder(panelHeader, "Product Catalog Stores");

    }

//    ProdCatalog prodCatalog = null;
    public void setDialogFields() {
        txtDataResourceId.setText(dataResource.getdataResourceId());

        try {
            if (UtilValidate.isNotEmpty(dataResource.getdataResourceTypeId())) {
                comboDataResourceTypeId.setSelectedItem(DataResourceTypeSingleton.getDataResourceType(dataResource.getdataResourceTypeId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(dataResource.getdataTemplateTypeId())) {
                comboDataTemplateTypeId.setSelectedItem(DataTemplateTypeSingleton.getDataTemplateType(dataResource.getdataTemplateTypeId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(dataResource.getdataCategoryId())) {
                comboDataCategory.setSelectedItem(DataCategorySingleton.getDataCategory(dataResource.getdataCategoryId()));
            }
        } catch (Exception ex) {
            //   Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtDataSourceId.setText(dataResource.getdataSourceId());
        try {
            comboStatusItem.setSelectedItem(StatusSingleton.getStatusItem(dataResource.getstatusId()));
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtDataResourceName.setText(dataResource.getdataResourceName());

        try {
            comboMimeType.setSelectedItem(MIMETypeSingleton.getMimeType(dataResource.getmimeTypeId()));
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(dataResource.getcharacterSetId())) {
                comboCharacterSet.setSelectedItem(CharacterSetSingleton.getCharacterSet(dataResource.getcharacterSetId()));
            }
        } catch (Exception ex) {
            //   Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtSurveyId.setText(dataResource.getsurveyId());
        txtSurveyResponseId.setText(dataResource.getsurveyResponseId());
        txtRelatedDetailId.setText(dataResource.getrelatedDetailId());

        try {
            if (UtilValidate.isNotEmpty(dataResource.getisPublic())) {
                comboIsPublic.setSelectedItem(YesNoConditionSelectSingleton.getString(dataResource.getisPublic()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtCreatedByUserLogin.setText(dataResource.getcreatedByUserLogin());
        txtLastModifiedByUserLogin.setText(dataResource.getlastModifiedByUserLogin());
        txtObjectInfo.setText(dataResource.getobjectInfo());
        try {
            datePickerCreateDate.setTimeStamp(dataResource.getcreatedDate());
        } catch (Exception ex) {
//            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            datePickerModifiedDate.setDate(dataResource.getlastModifiedDate());
        } catch (Exception ex) {
//            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*try {
         datePickerCreateDate.setTimeStamp(dataResource.getcreatedDate());
         } catch (Exception ex) {
         Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         datePickerModifiedDate.setTimeStamp(dataResource.getProductContent().getthruDate());
         } catch (Exception ex) {
         Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         comboProductContentType.setSelectedItem(ProductContentTypeSingleton.getProductContentType(dataResource.getProductContent().getproductContentTypeId()));
         } catch (Exception ex) {
         Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
         comboUom.setSelectedItem(UomTimeSingleton.getUom(dataResource.getProductContent().getuseTimeUomId()));
         } catch (Exception ex) {
         Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }

         if (UtilValidate.isNotEmpty(dataResource.getProductContent().getsequenceNum())) {
         txtChildLeafCount.setText(dataResource.getProductContent().getsequenceNum().toString());
         }*/
    }

    public void clearDialogFields() {
        datePickerCreateDate.txtDate.setText("");
        datePickerModifiedDate.txtDate.setText("");
        txtSurveyId.setText("");
    }

    public void getDialogFields() {
        dataResource.setdataResourceId(txtDataResourceId.getText());

        try {
            dataResource.setdataResourceTypeId(comboDataResourceTypeId.getSelectedItem().getdataResourceTypeId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            dataResource.setdataTemplateTypeId(comboDataTemplateTypeId.getSelectedItem().getdataTemplateTypeId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            dataResource.setdataCategoryId(comboDataCategory.getSelectedItem().getdataCategoryId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        dataResource.setdataSourceId(txtDataSourceId.getText());
        try {
            dataResource.setstatusId(comboStatusItem.getSelectedItem().getstatusId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataResource.setdataResourceName(txtDataResourceName.getText());

        try {
            dataResource.setmimeTypeId(comboMimeType.getSelectedItem().getmimeTypeId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(comboCharacterSet.getSelectedItem())) {
                dataResource.setcharacterSetId(comboCharacterSet.getSelectedItem().getcharacterSetId());
            }
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataResource.setsurveyId(txtSurveyId.getText());
        dataResource.setsurveyResponseId(txtSurveyResponseId.getText());
        dataResource.setrelatedDetailId(txtRelatedDetailId.getText());

        try {
            dataResource.setisPublic(comboIsPublic.getSelectedItem());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataResource.setcreatedByUserLogin(txtCreatedByUserLogin.getText());
        dataResource.setlastModifiedByUserLogin(txtLastModifiedByUserLogin.getText());
        dataResource.setobjectInfo(txtObjectInfo.getText());
        Debug.logInfo("dataResource.setobjectInfo(): " + dataResource.getobjectInfo(), "Test");
        try {
            dataResource.setcreatedDate(datePickerCreateDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dataResource.setlastModifiedDate(datePickerModifiedDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*try {
         dataResource.getProductContent().setfromDate(datePickerCreateDate.getTimeStamp());
         } catch (Exception ex) {
         Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }

         try {
         dataResource.getProductContent().setthruDate(datePickerModifiedDate.getTimeStamp());
         } catch (Exception ex) {
         dataResource.getProductContent().setthruDate(null);
         }

         if (UtilValidate.isNotEmpty(comboProductContentType.getSelectedItem())) {
         dataResource.getProductContent().setproductContentTypeId(comboProductContentType.getSelectedItem().getproductContentTypeId());
         }
         if (UtilValidate.isNotEmpty(comboUom.getSelectedItem())) {
         dataResource.getProductContent().setuseTimeUomId(comboUom.getSelectedItem().getuomId());
         }
         if (UtilValidate.isNotEmpty(txtChildLeafCount.getText())) {
         dataResource.getProductContent().setsequenceNum(Long.parseLong(txtChildLeafCount.getText()));
         }
         */
    }

    public void setProductContentList(ListAdapterListModel<DataResource> productContentList) {
        // tablePanel.setListModel(productContentList);
    }

    public DataResource getProductStoreCatalog() {
        return dataResource;
    }
    ListAdapterListModel<DataResource> productContentList = new ListAdapterListModel<DataResource>();
    ;
    ProductComposite productComposite = null;

    public void setDataResource(DataResource dataResource) {
        this.dataResource = dataResource;
        setDialogFields();
        /*  this.productComposite = productComposite;
         if (productComposite != null) {

         Map<String, Object> whereClauseMap = new HashMap<String, Object>();
         whereClauseMap.put("productId", productComposite.getProduct().getproductId());
         List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductContent", whereClauseMap, null, XuiContainer.getSession().getDelegator());
         for (GenericValue val : valueList) {
         ProductContent  productContent = new ProductContent(val);     
         productContentList.add(productContent);
         }
         setProductContentList(productContentList);
         /*
         supplierProductListModel.clear();
         supplierProductListModel.addAll(productComposite.getProductItemPrice().getProductPriceList().getList());

         if (supplierProductListModel.getSize() > 0) {
         setSupplierproduct(0);
         } else {
         clearDialogFields();
         productPriceComposite = LoadProductPriceWorker.createProductPriceComposite(productComposite.getProduct().getproductId(), SupplierPrefOrderSingleton.getSingletonSession());
         setDialogField();
         }
         }*/

    }

    public void setProductStoreCatalog(DataResource dataResource) {
        this.dataResource = dataResource;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The dataResource of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDetail = new javax.swing.JPanel();
        panelIsPublic = new javax.swing.JPanel();
        Store = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSurveyId = new javax.swing.JTextField();
        panelDataResourceTypeId = new javax.swing.JPanel();
        panelStatusID = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelDataCategoryId = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDataResourceId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        panelCreatedDate = new javax.swing.JPanel();
        panelLastModifiedDate = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSurveyResponseId = new javax.swing.JTextField();
        txtCreatedByUserLogin = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtRelatedDetailId = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        panelLocaleString = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        panelMimeTypeId = new javax.swing.JPanel();
        panelCharacterSetId = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panelDataTemplateTypeId = new javax.swing.JPanel();
        txtDataSourceId = new javax.swing.JTextField();
        txtDataResourceName = new javax.swing.JTextField();
        txtLastModifiedByUserLogin = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        txtObjectInfo = new javax.swing.JTextField();
        btnOriginalIcon = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.BorderLayout());

        panelDetail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 300));

        panelIsPublic.setMaximumSize(new java.awt.Dimension(160, 32767));

        javax.swing.GroupLayout panelIsPublicLayout = new javax.swing.GroupLayout(panelIsPublic);
        panelIsPublic.setLayout(panelIsPublicLayout);
        panelIsPublicLayout.setHorizontalGroup(
            panelIsPublicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        panelIsPublicLayout.setVerticalGroup(
            panelIsPublicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        Store.setText("Data Template Type Id:");

        jLabel3.setText("Data Source Id:");

        jLabel4.setText("Status ID:");

        jLabel5.setText("Survey Id:");

        txtSurveyId.setMaximumSize(new java.awt.Dimension(160, 32767));

        panelDataResourceTypeId.setEnabled(false);
        panelDataResourceTypeId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelDataResourceTypeId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelDataResourceTypeIdLayout = new javax.swing.GroupLayout(panelDataResourceTypeId);
        panelDataResourceTypeId.setLayout(panelDataResourceTypeIdLayout);
        panelDataResourceTypeIdLayout.setHorizontalGroup(
            panelDataResourceTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelDataResourceTypeIdLayout.setVerticalGroup(
            panelDataResourceTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelStatusID.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelStatusID.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelStatusIDLayout = new javax.swing.GroupLayout(panelStatusID);
        panelStatusID.setLayout(panelStatusIDLayout);
        panelStatusIDLayout.setHorizontalGroup(
            panelStatusIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelStatusIDLayout.setVerticalGroup(
            panelStatusIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        Store1.setText("Data Category Id:");

        panelDataCategoryId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelDataCategoryId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelDataCategoryIdLayout = new javax.swing.GroupLayout(panelDataCategoryId);
        panelDataCategoryId.setLayout(panelDataCategoryIdLayout);
        panelDataCategoryIdLayout.setHorizontalGroup(
            panelDataCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelDataCategoryIdLayout.setVerticalGroup(
            panelDataCategoryIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Data Resource Id:");

        txtDataResourceId.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtDataResourceId.setPreferredSize(new java.awt.Dimension(100, 26));

        jLabel7.setText("Created Date:");

        panelCreatedDate.setMaximumSize(new java.awt.Dimension(160, 32767));

        javax.swing.GroupLayout panelCreatedDateLayout = new javax.swing.GroupLayout(panelCreatedDate);
        panelCreatedDate.setLayout(panelCreatedDateLayout);
        panelCreatedDateLayout.setHorizontalGroup(
            panelCreatedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        panelCreatedDateLayout.setVerticalGroup(
            panelCreatedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        panelLastModifiedDate.setMaximumSize(new java.awt.Dimension(160, 32767));

        javax.swing.GroupLayout panelLastModifiedDateLayout = new javax.swing.GroupLayout(panelLastModifiedDate);
        panelLastModifiedDate.setLayout(panelLastModifiedDateLayout);
        panelLastModifiedDateLayout.setHorizontalGroup(
            panelLastModifiedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        panelLastModifiedDateLayout.setVerticalGroup(
            panelLastModifiedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel8.setText("Last Modified Date:");

        jLabel9.setText("Survey Response Id:");

        txtSurveyResponseId.setMaximumSize(new java.awt.Dimension(160, 32767));

        txtCreatedByUserLogin.setMaximumSize(new java.awt.Dimension(160, 32767));

        jLabel10.setText("Created By User Login:");

        txtRelatedDetailId.setMaximumSize(new java.awt.Dimension(160, 32767));

        jLabel11.setText("Related Detail Id:");

        jLabel12.setText("Data Resource Name:");

        jLabel13.setText("Locale String:");

        panelLocaleString.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelLocaleString.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelLocaleStringLayout = new javax.swing.GroupLayout(panelLocaleString);
        panelLocaleString.setLayout(panelLocaleStringLayout);
        panelLocaleStringLayout.setHorizontalGroup(
            panelLocaleStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelLocaleStringLayout.setVerticalGroup(
            panelLocaleStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel14.setText("Data Resource Type Id:");

        jLabel15.setText("Mime Type Id:");

        panelMimeTypeId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelMimeTypeId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelMimeTypeIdLayout = new javax.swing.GroupLayout(panelMimeTypeId);
        panelMimeTypeId.setLayout(panelMimeTypeIdLayout);
        panelMimeTypeIdLayout.setHorizontalGroup(
            panelMimeTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelMimeTypeIdLayout.setVerticalGroup(
            panelMimeTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelCharacterSetId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelCharacterSetId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelCharacterSetIdLayout = new javax.swing.GroupLayout(panelCharacterSetId);
        panelCharacterSetId.setLayout(panelCharacterSetIdLayout);
        panelCharacterSetIdLayout.setHorizontalGroup(
            panelCharacterSetIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelCharacterSetIdLayout.setVerticalGroup(
            panelCharacterSetIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel16.setText("Character Set Id:");

        jLabel17.setText("Object Info:");

        jLabel19.setText("Is Public:");

        jLabel21.setText("Last Modified By User Login:");

        panelDataTemplateTypeId.setEnabled(false);
        panelDataTemplateTypeId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelDataTemplateTypeId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelDataTemplateTypeIdLayout = new javax.swing.GroupLayout(panelDataTemplateTypeId);
        panelDataTemplateTypeId.setLayout(panelDataTemplateTypeIdLayout);
        panelDataTemplateTypeIdLayout.setHorizontalGroup(
            panelDataTemplateTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelDataTemplateTypeIdLayout.setVerticalGroup(
            panelDataTemplateTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        txtDataSourceId.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtDataSourceId.setPreferredSize(new java.awt.Dimension(100, 26));

        txtDataResourceName.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtDataResourceName.setPreferredSize(new java.awt.Dimension(100, 26));

        txtLastModifiedByUserLogin.setMaximumSize(new java.awt.Dimension(160, 32767));
        txtLastModifiedByUserLogin.setPreferredSize(new java.awt.Dimension(100, 22));

        jPanel1.setLayout(new java.awt.BorderLayout());

        txtObjectInfo.setMaximumSize(new java.awt.Dimension(200, 2147483647));
        txtObjectInfo.setMinimumSize(new java.awt.Dimension(200, 22));
        txtObjectInfo.setPreferredSize(new java.awt.Dimension(200, 22));
        jPanel1.add(txtObjectInfo, java.awt.BorderLayout.CENTER);

        btnOriginalIcon.setText("jButton1");
        btnOriginalIcon.setPreferredSize(new java.awt.Dimension(25, 25));
        btnOriginalIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOriginalIconActionPerformed(evt);
            }
        });
        jPanel1.add(btnOriginalIcon, java.awt.BorderLayout.EAST);

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataResourceId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDataResourceTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDataCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelStatusID, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelLocaleString, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelMimeTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelCharacterSetId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDataTemplateTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataSourceId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataResourceName, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDetailLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelDetailLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSurveyId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelDetailLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSurveyResponseId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelDetailLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtRelatedDetailId, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                                    .addGroup(panelDetailLayout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(panelIsPublic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelDetailLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(panelCreatedDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelDetailLayout.createSequentialGroup()
                                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCreatedByUserLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(panelLastModifiedDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(panelDetailLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLastModifiedByUserLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(346, 346, 346))
        );

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, saveButton});

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelCreatedDate, panelIsPublic, panelLastModifiedDate, txtCreatedByUserLogin, txtLastModifiedByUserLogin, txtRelatedDetailId, txtSurveyId, txtSurveyResponseId});

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelCharacterSetId, panelDataCategoryId, panelDataResourceTypeId, panelDataTemplateTypeId, panelLocaleString, panelMimeTypeId, panelStatusID, txtDataResourceId, txtDataResourceName, txtDataSourceId});

        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtDataResourceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtSurveyId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(panelDataResourceTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtSurveyResponseId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store)
                    .addComponent(panelDataTemplateTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtRelatedDetailId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store1)
                    .addComponent(panelDataCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(panelIsPublic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtDataSourceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(panelCreatedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(panelStatusID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCreatedByUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtDataResourceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(panelLastModifiedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelLocaleString, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txtLastModifiedByUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(panelMimeTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(panelCharacterSetId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(newButton)
                    .addComponent(deleteButton))
                .addContainerGap(146, Short.MAX_VALUE))
        );

        add(panelDetail, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        getDialogFields();

        try {
            LoadProductWorker.saveDataResource(dataResource, XuiContainer.getSession());
            /* if (isNew) {
             tablePanel.listModel.add(dataResource);
             }*/
            isNew = false;
            setDialogFields();
            //        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(catalogListSelectionModel.dataListModel, XuiContainer.getSession());
            //        saveAction.actionPerformed(evt);
        } catch (Exception ex) {
            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        isNew = true;
        isModified = false;
        dataResource = new DataResource();
        clearDialogFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed
    String productId = "";

    private void btnOriginalIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOriginalIconActionPerformed
        if (UtilValidate.isNotEmpty(comboDataResourceTypeId.getSelectedItem()) && "ELECTRONIC_TEXT".equalsIgnoreCase(comboDataResourceTypeId.getSelectedItem().getdataResourceTypeId())) {
            JTextArea textArea = new JTextArea(6, 25);
            textArea.setText(txtObjectInfo.getText());
            textArea.setEditable(true);

            // wrap a scrollpane around it
            JScrollPane scrollPane = new JScrollPane(textArea);

            // display them in a message dialog
            OrderMaxOptionPane.showMessageDialog(this, scrollPane);
            txtObjectInfo.setText(textArea.getText());
        } else {
            File filePath = BaseHelper.getImageFilePath("original");
            if (UtilValidate.isNotEmpty(txtDataResourceName.getText()) && UtilValidate.isNotEmpty(filePath)) {
                txtObjectInfo.setText(BaseHelper.CopyDataResourceImageSetFileName(txtDataResourceName.getText(), filePath, txtDataResourceName.getText(), 0, 0));
            }
        }
//        showSelectandFileImage(txtObjectInfo.getText());

    }//GEN-LAST:event_btnOriginalIconActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    private javax.swing.JButton btnOriginalIcon;
    public javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JButton newButton;
    private javax.swing.JPanel panelCharacterSetId;
    private javax.swing.JPanel panelCreatedDate;
    private javax.swing.JPanel panelDataCategoryId;
    private javax.swing.JPanel panelDataResourceTypeId;
    private javax.swing.JPanel panelDataTemplateTypeId;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelIsPublic;
    private javax.swing.JPanel panelLastModifiedDate;
    private javax.swing.JPanel panelLocaleString;
    private javax.swing.JPanel panelMimeTypeId;
    private javax.swing.JPanel panelStatusID;
    public javax.swing.JButton saveButton;
    private javax.swing.JTextField txtCreatedByUserLogin;
    public javax.swing.JTextField txtDataResourceId;
    public javax.swing.JTextField txtDataResourceName;
    private javax.swing.JTextField txtDataSourceId;
    private javax.swing.JTextField txtLastModifiedByUserLogin;
    private javax.swing.JTextField txtObjectInfo;
    private javax.swing.JTextField txtRelatedDetailId;
    private javax.swing.JTextField txtSurveyId;
    private javax.swing.JTextField txtSurveyResponseId;
    // End of variables declaration//GEN-END:variables

    @Override
    public JButton getOkButton() {
        return null;
    }

    @Override
    public JButton getCancelButton() {
        return null;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

}
