/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
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
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.base.components.ContentPickerEditPanel;
import org.ofbiz.ordermax.base.components.DataResourcePickerEditPanel;
import org.ofbiz.ordermax.base.components.LocalePickerEditPanel;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.entity.CharacterSet;
import org.ofbiz.ordermax.entity.Content;
import org.ofbiz.ordermax.entity.ContentType;

import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.MimeType;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ContentMaintainPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    final public static String module = ContentMaintainPanel.class.getName();
    private ListAdapterListModel<ProdCatalog> personListModel = new ListAdapterListModel<ProdCatalog>();
//    public GenericTableModelPanel<ProductContentComposite, ProductContentTableModel> tablePanel = null;

//    private ListModelSelection<ProdCatalog> listModelSelection = new ListModelSelection<ProdCatalog>();
//    private ListSelectionModel selectionModel = null; //new DefaultListSelectionModel();
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
    Content content = null;

    boolean isNew = false;
    boolean isModified = false;
    private DatePickerEditPanel datePickerCreateDate = null;
    private DatePickerEditPanel datePickerModifyDate = null;
    private DataResourcePickerEditPanel dataResourcePickerEditPanel = null;
    private ContentPickerEditPanel ownerContentIdPickerEditPanel = null;
    private ContentPickerEditPanel decoratorContentIdPickerEditPanel = null;

    public DataResourcePickerEditPanel dataResourceIdPickerEditPanel = null;
    private DataResourcePickerEditPanel tmpDataResourceIdPickerEditPanel = null;

    private JGenericComboBoxSelectionModel<ContentType> comboContentType = null;
    private JGenericComboBoxSelectionModel<StatusItem> comboStatusItem = null;
    private JGenericComboBoxSelectionModel<MimeType> comboMimeType = null;
    private JGenericComboBoxSelectionModel<CharacterSet> comboCharacterSet = null;
    private LocalePickerEditPanel localePickerEditPanel = null;
//    private JGenericComboBoxSelectionModel<String> comboLocale = null;

    //private JGenericComboBoxSelectionModel<Uom> comboUom = null;
    ControllerOptions controllerOptions = new ControllerOptions();

    public ContentMaintainPanel() {
        initComponents();
        /* ListCellRenderer<ProdCatalog> prodCatalogRenderer = new ProdCatalogListCellRenderer();
         prodCatalogList.setCellRenderer(prodCatalogRenderer);
         prodCatalogList.setEnabled(true);
         prodCatalogList.setSelectionBackground(Color.CYAN);
         */
//        tablePanel = new GenericTableModelPanel<ProductContentComposite, ProductContentTableModel>(new ProductContentTableModel());
//        panelHeader.setLayout(new BorderLayout());
//        panelHeader.add(BorderLayout.CENTER, tablePanel);
        datePickerCreateDate = DatePickerEditPanel.addToPanel(panelCreatedDate);
        datePickerModifyDate = DatePickerEditPanel.addToPanel(panelLastModifiedDate);
//        dataResourcePickerEditPanel = new DataResourcePickerEditPanel(controllerOptions);
        ownerContentIdPickerEditPanel = new ContentPickerEditPanel(controllerOptions, panelOwnerContentId);
        decoratorContentIdPickerEditPanel = new ContentPickerEditPanel(controllerOptions, panelDecoratorContentId);

        dataResourceIdPickerEditPanel = new DataResourcePickerEditPanel(controllerOptions, panelDataResourceId);
        tmpDataResourceIdPickerEditPanel = new DataResourcePickerEditPanel(controllerOptions, panelTemplateDataResourceId);

        localePickerEditPanel = new LocalePickerEditPanel(controllerOptions, panelLocaleString);
        /*       for (int i = 0; i < ProductStoreCatalogTableModel.Columns.values().length; i++) {
         ProductStoreCatalogTableModel.Columns[] columns = ProductStoreCatalogTableModel.Columns.values();
         ProductStoreCatalogTableModel.Columns column = columns[i];
         TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);

         col.setPreferredWidth(column.getColumnWidth());
         }
         tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         */
        comboContentType = new JGenericComboBoxSelectionModel<ContentType>(panelContentTypeId, ContentTypeSingleton.getValueList());

        comboStatusItem = new JGenericComboBoxSelectionModel<StatusItem>(panelStatusID, StatusSingleton.getValueList("CONTENT_STATUS")
                );
        comboMimeType = new JGenericComboBoxSelectionModel<MimeType>(panelMimeType, MIMETypeSingleton.getValueList()
                );
        comboCharacterSet = new JGenericComboBoxSelectionModel<CharacterSet>(panelCharacterSetId, CharacterSetSingleton.getValueList()
                );

        //comboUom = new JGenericComboBoxSelectionModel<Uom>(panelOwnerContentId, UomTimeSingleton.getValueList(),
        //        );
/*        tablePanel.selectionModel.addListSelectionListener(new ListSelectionListener() {
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
         */
        /*panelHeader.add(BorderLayout.CENTER, scrollPane);
         catalogListSelectionModel.selectionModel.addListSelectionListener(new ListSelectionListener() {

         public void valueChanged(ListSelectionEvent e) {
         prodCatalog = catalogListSelectionModel.listModelSelection.getSelection();
         setDialogFields(prodCatalog);
         isNew = false;
         }
         });
         */
//        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
//        ComponentBorder.loweredBevelBorder(panelHeader, "Product Catalog Stores");
    }

//    ProdCatalog prodCatalog = null;
    public void setDialogFields() {
        txtContentId.setText(content.getcontentId());

        try {
            if (UtilValidate.isNotEmpty(content.getcontentTypeId())) {
                comboContentType.setSelectedItem(ContentTypeSingleton.getContentType(content.getcontentTypeId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ownerContentIdPickerEditPanel.textIdField.setText(content.getownerContentId());
        decoratorContentIdPickerEditPanel.textIdField.setText(content.getdecoratorContentId());

        dataResourceIdPickerEditPanel.textIdField.setText(content.getdataResourceId());
        tmpDataResourceIdPickerEditPanel.textIdField.setText(content.gettemplateDataResourceId());
        txtInstanceContentId.setText(content.getinstanceOfContentId());
        txtDataSourceId.setText(content.getdataSourceId());
        try {
            comboStatusItem.setSelectedItem(StatusSingleton.getStatusItem(content.getstatusId()));
        } catch (Exception ex) {
            // Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtPrivilegeEnumId.setText(content.getprivilegeEnumId());
        txtServiceName.setText(content.getserviceName());
        txtContentName.setText(content.getcontentName());
        txtDescription.setText(content.getdescription());

        try {
            if (UtilValidate.isNotEmpty(content.getmimeTypeId())) {
                comboMimeType.setSelectedItem(MIMETypeSingleton.getMimeType(content.getmimeTypeId()));
            }
        } catch (Exception ex) {
            //   Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (UtilValidate.isNotEmpty(content.getcharacterSetId())) {
                comboCharacterSet.setSelectedItem(CharacterSetSingleton.getCharacterSet(content.getcharacterSetId()));
            }
        } catch (Exception ex) {
            //    Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtChildBranchCount.setText(content.getchildBranchCount());
        txtChildLeafCount.setText(content.getchildLeafCount());
        txtCreatedByUserLogin.setText(content.getcreatedByUserLogin());
        txtLastModifiedByUserLogin.setText(content.getlastModifiedByUserLogin());
        localePickerEditPanel.textIdField.setText(content.getlocaleString());

        try {
            datePickerCreateDate.setTimeStamp(content.getcreatedDate());
        } catch (Exception ex) {
            //Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            datePickerModifyDate.setDate(content.getlastModifiedDate());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearDialogFields() {
        datePickerCreateDate.txtDate.setText("");
        datePickerModifyDate.txtDate.setText("");
        txtChildLeafCount.setText("");
    }

    public void getDialogFields() {
        content.setcontentId(txtContentId.getText());

        try {
            content.setcontentTypeId(comboContentType.getSelectedItem().getcontentTypeId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        content.setownerContentId(ownerContentIdPickerEditPanel.textIdField.getText());
        content.setdecoratorContentId(decoratorContentIdPickerEditPanel.textIdField.getText());

        content.setdataResourceId(dataResourceIdPickerEditPanel.textIdField.getText());
        content.settemplateDataResourceId(tmpDataResourceIdPickerEditPanel.textIdField.getText());
        content.setinstanceOfContentId(txtInstanceContentId.getText());
        content.setdataSourceId(txtDataSourceId.getText());
        try {
            content.setstatusId(comboStatusItem.getSelectedItem().getstatusId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        content.setprivilegeEnumId(txtPrivilegeEnumId.getText());
        content.setserviceName(txtServiceName.getText());
        content.setcontentName(txtContentName.getText());
        content.setdescription(txtDescription.getText());

        try {
            content.setmimeTypeId(comboMimeType.getSelectedItem().getmimeTypeId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            content.setcharacterSetId(comboCharacterSet.getSelectedItem().getcharacterSetId());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        content.setchildBranchCount(txtChildBranchCount.getText());
        content.setchildLeafCount(txtChildLeafCount.getText());
        content.setcreatedByUserLogin(txtCreatedByUserLogin.getText());
        content.setlastModifiedByUserLogin(txtLastModifiedByUserLogin.getText());
        content.setlocaleString(localePickerEditPanel.textIdField.getText());

        try {
            content.setcreatedDate(datePickerCreateDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            content.setlastModifiedDate(datePickerModifyDate.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(ContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setProductContentList(ListAdapterListModel<Content> productContentList) {
        // tablePanel.setListModel(productContentList);
    }

    public Content getProductStoreCatalog() {
        return content;
    }
    ListAdapterListModel<Content> productContentList = new ListAdapterListModel<Content>();
    ;
    ProductComposite productComposite = null;

    public void setContent(Content content) {
        this.content = content;
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

    public void setProductStoreCatalog(Content content) {
        this.content = content;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDetail = new javax.swing.JPanel();
        Store = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtChildLeafCount = new javax.swing.JTextField();
        panelContentTypeId = new javax.swing.JPanel();
        panelDecoratorContentId = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelOwnerContentId = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtContentId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        panelLastModifiedDate = new javax.swing.JPanel();
        panelCreatedDate = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtChildBranchCount = new javax.swing.JTextField();
        txtCreatedByUserLogin = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtLastModifiedByUserLogin = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelDataResourceId = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        panelTemplateDataResourceId = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtDataSourceId = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        panelStatusID = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panelLocaleString = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelCharacterSetId = new javax.swing.JPanel();
        panelLocaleString1 = new javax.swing.JPanel();
        txtInstanceContentId = new javax.swing.JTextField();
        txtPrivilegeEnumId = new javax.swing.JTextField();
        txtServiceName = new javax.swing.JTextField();
        txtContentName = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        panelMimeType = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.BorderLayout());

        panelDetail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 300));

        Store.setText("Content Type Id:");

        jLabel3.setText("Decorator Content Id:");

        jLabel4.setText("Instance Of Content Id:");

        jLabel5.setText("Child Leaf Count:");

        txtChildLeafCount.setMaximumSize(new java.awt.Dimension(200, 32767));
        txtChildLeafCount.setMinimumSize(new java.awt.Dimension(200, 0));
        txtChildLeafCount.setPreferredSize(new java.awt.Dimension(200, 26));

        panelContentTypeId.setEnabled(false);
        panelContentTypeId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelContentTypeId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelContentTypeIdLayout = new javax.swing.GroupLayout(panelContentTypeId);
        panelContentTypeId.setLayout(panelContentTypeIdLayout);
        panelContentTypeIdLayout.setHorizontalGroup(
            panelContentTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelContentTypeIdLayout.setVerticalGroup(
            panelContentTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        panelDecoratorContentId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelDecoratorContentId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelDecoratorContentIdLayout = new javax.swing.GroupLayout(panelDecoratorContentId);
        panelDecoratorContentId.setLayout(panelDecoratorContentIdLayout);
        panelDecoratorContentIdLayout.setHorizontalGroup(
            panelDecoratorContentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelDecoratorContentIdLayout.setVerticalGroup(
            panelDecoratorContentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        Store1.setText("Owner Content Id:");

        panelOwnerContentId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelOwnerContentId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelOwnerContentIdLayout = new javax.swing.GroupLayout(panelOwnerContentId);
        panelOwnerContentId.setLayout(panelOwnerContentIdLayout);
        panelOwnerContentIdLayout.setHorizontalGroup(
            panelOwnerContentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelOwnerContentIdLayout.setVerticalGroup(
            panelOwnerContentIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        jLabel6.setText("Content ID:");

        txtContentId.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtContentId.setPreferredSize(new java.awt.Dimension(100, 26));

        jLabel7.setText("Content Name:");

        panelLastModifiedDate.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelLastModifiedDate.setMinimumSize(new java.awt.Dimension(200, 0));
        panelLastModifiedDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelLastModifiedDateLayout = new javax.swing.GroupLayout(panelLastModifiedDate);
        panelLastModifiedDate.setLayout(panelLastModifiedDateLayout);
        panelLastModifiedDateLayout.setHorizontalGroup(
            panelLastModifiedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelLastModifiedDateLayout.setVerticalGroup(
            panelLastModifiedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelCreatedDate.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelCreatedDate.setMinimumSize(new java.awt.Dimension(200, 0));
        panelCreatedDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelCreatedDateLayout = new javax.swing.GroupLayout(panelCreatedDate);
        panelCreatedDate.setLayout(panelCreatedDateLayout);
        panelCreatedDateLayout.setHorizontalGroup(
            panelCreatedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelCreatedDateLayout.setVerticalGroup(
            panelCreatedDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel8.setText("Last Modified Date:");

        jLabel9.setText("Child Branch Count:");

        txtChildBranchCount.setMaximumSize(new java.awt.Dimension(200, 32767));
        txtChildBranchCount.setMinimumSize(new java.awt.Dimension(200, 0));
        txtChildBranchCount.setPreferredSize(new java.awt.Dimension(200, 26));

        txtCreatedByUserLogin.setMaximumSize(new java.awt.Dimension(200, 32767));
        txtCreatedByUserLogin.setMinimumSize(new java.awt.Dimension(200, 0));
        txtCreatedByUserLogin.setPreferredSize(new java.awt.Dimension(200, 26));

        jLabel10.setText("Created By User Login:");

        txtLastModifiedByUserLogin.setMaximumSize(new java.awt.Dimension(200, 32767));
        txtLastModifiedByUserLogin.setMinimumSize(new java.awt.Dimension(200, 0));
        txtLastModifiedByUserLogin.setPreferredSize(new java.awt.Dimension(200, 26));

        jLabel11.setText("Last Modified By User Login:");

        jLabel12.setText("Data Resource Id:");

        panelDataResourceId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelDataResourceId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelDataResourceIdLayout = new javax.swing.GroupLayout(panelDataResourceId);
        panelDataResourceId.setLayout(panelDataResourceIdLayout);
        panelDataResourceIdLayout.setHorizontalGroup(
            panelDataResourceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelDataResourceIdLayout.setVerticalGroup(
            panelDataResourceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel13.setText("Temp. Data Resource Id:");

        panelTemplateDataResourceId.setMaximumSize(new java.awt.Dimension(100, 32767));
        panelTemplateDataResourceId.setPreferredSize(new java.awt.Dimension(100, 26));

        javax.swing.GroupLayout panelTemplateDataResourceIdLayout = new javax.swing.GroupLayout(panelTemplateDataResourceId);
        panelTemplateDataResourceId.setLayout(panelTemplateDataResourceIdLayout);
        panelTemplateDataResourceIdLayout.setHorizontalGroup(
            panelTemplateDataResourceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );
        panelTemplateDataResourceIdLayout.setVerticalGroup(
            panelTemplateDataResourceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel14.setText("Data Source Id:");

        txtDataSourceId.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtDataSourceId.setPreferredSize(new java.awt.Dimension(100, 26));

        jLabel15.setText("Status ID:");

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

        jLabel16.setText("Privilege Enum Id:");

        jLabel17.setText("Service Name:");

        jLabel18.setText("Created Date:");

        jLabel19.setText("Description:");

        jLabel20.setText("Locale String:");

        panelLocaleString.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelLocaleString.setMinimumSize(new java.awt.Dimension(200, 0));
        panelLocaleString.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelLocaleStringLayout = new javax.swing.GroupLayout(panelLocaleString);
        panelLocaleString.setLayout(panelLocaleStringLayout);
        panelLocaleStringLayout.setHorizontalGroup(
            panelLocaleStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelLocaleStringLayout.setVerticalGroup(
            panelLocaleStringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel21.setText("Mime Type Id:");

        jLabel22.setText("Character Set Id:");

        panelCharacterSetId.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelCharacterSetId.setMinimumSize(new java.awt.Dimension(200, 0));
        panelCharacterSetId.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelCharacterSetIdLayout = new javax.swing.GroupLayout(panelCharacterSetId);
        panelCharacterSetId.setLayout(panelCharacterSetIdLayout);
        panelCharacterSetIdLayout.setHorizontalGroup(
            panelCharacterSetIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelCharacterSetIdLayout.setVerticalGroup(
            panelCharacterSetIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panelLocaleString1.setMaximumSize(new java.awt.Dimension(160, 32767));

        javax.swing.GroupLayout panelLocaleString1Layout = new javax.swing.GroupLayout(panelLocaleString1);
        panelLocaleString1.setLayout(panelLocaleString1Layout);
        panelLocaleString1Layout.setHorizontalGroup(
            panelLocaleString1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelLocaleString1Layout.setVerticalGroup(
            panelLocaleString1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        txtInstanceContentId.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtInstanceContentId.setPreferredSize(new java.awt.Dimension(100, 26));

        txtPrivilegeEnumId.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtPrivilegeEnumId.setPreferredSize(new java.awt.Dimension(100, 26));

        txtServiceName.setMaximumSize(new java.awt.Dimension(100, 32767));
        txtServiceName.setPreferredSize(new java.awt.Dimension(100, 26));

        txtContentName.setMaximumSize(new java.awt.Dimension(200, 32767));
        txtContentName.setMinimumSize(new java.awt.Dimension(200, 0));
        txtContentName.setPreferredSize(new java.awt.Dimension(200, 26));

        txtDescription.setMaximumSize(new java.awt.Dimension(200, 32767));
        txtDescription.setMinimumSize(new java.awt.Dimension(200, 0));
        txtDescription.setPreferredSize(new java.awt.Dimension(200, 26));

        panelMimeType.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelMimeType.setMinimumSize(new java.awt.Dimension(200, 0));
        panelMimeType.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelMimeTypeLayout = new javax.swing.GroupLayout(panelMimeType);
        panelMimeType.setLayout(panelMimeTypeLayout);
        panelMimeTypeLayout.setHorizontalGroup(
            panelMimeTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelMimeTypeLayout.setVerticalGroup(
            panelMimeTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Store, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtServiceName, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrivilegeEnumId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelStatusID, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataSourceId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelTemplateDataResourceId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDataResourceId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtInstanceContentId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelDecoratorContentId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelOwnerContentId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelContentTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContentId, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCreatedByUserLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelCreatedDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtChildLeafCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtChildBranchCount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelCharacterSetId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelMimeType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelLocaleString, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescription, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContentName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLastModifiedByUserLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelLastModifiedDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGap(575, 575, 575)
                        .addComponent(panelLocaleString1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(103, 103, 103))
        );

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, saveButton});

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelCharacterSetId, panelCreatedDate, panelLastModifiedDate, panelLocaleString, panelLocaleString1, panelMimeType, txtChildBranchCount, txtChildLeafCount, txtContentName, txtCreatedByUserLogin, txtDescription, txtLastModifiedByUserLogin});

        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtContentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtContentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store)
                    .addComponent(jLabel19)
                    .addComponent(txtDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelContentTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Store1)
                    .addComponent(panelOwnerContentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(panelLocaleString, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(panelDecoratorContentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(panelMimeType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtInstanceContentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(panelCharacterSetId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelLocaleString1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(panelDataResourceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtChildBranchCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtChildLeafCount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTemplateDataResourceId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtDataSourceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(panelCreatedDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(panelStatusID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCreatedByUserLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(txtPrivilegeEnumId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(panelLastModifiedDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txtServiceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtLastModifiedByUserLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(newButton)
                    .addComponent(deleteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(panelDetail, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        getDialogFields();
        try {
            LoadProductWorker.saveContent(content, XuiContainer.getSession());
            setDialogFields();
            isNew = false;
            //        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(catalogListSelectionModel.dataListModel, XuiContainer.getSession());
            //        saveAction.actionPerformed(evt);
        } catch (Exception ex) {
            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        isNew = true;
        isModified = false;
        content = new Content();
        clearDialogFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    public javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JButton newButton;
    private javax.swing.JPanel panelCharacterSetId;
    private javax.swing.JPanel panelContentTypeId;
    private javax.swing.JPanel panelCreatedDate;
    private javax.swing.JPanel panelDataResourceId;
    private javax.swing.JPanel panelDecoratorContentId;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelLastModifiedDate;
    private javax.swing.JPanel panelLocaleString;
    private javax.swing.JPanel panelLocaleString1;
    private javax.swing.JPanel panelLocaleString2;
    private javax.swing.JPanel panelMimeType;
    private javax.swing.JPanel panelMimeTypeId;
    private javax.swing.JPanel panelOwnerContentId;
    private javax.swing.JPanel panelStatusID;
    private javax.swing.JPanel panelTemplateDataResourceId;
    public javax.swing.JButton saveButton;
    private javax.swing.JTextField txtChildBranchCount;
    private javax.swing.JTextField txtChildLeafCount;
    private javax.swing.JTextField txtContentId;
    private javax.swing.JTextField txtContentName;
    private javax.swing.JTextField txtCreatedByUserLogin;
    private javax.swing.JTextField txtDataSourceId;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtInstanceContentId;
    private javax.swing.JTextField txtLastModifiedByUserLogin;
    private javax.swing.JTextField txtPrivilegeEnumId;
    private javax.swing.JTextField txtServiceName;
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
