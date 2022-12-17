/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

import java.awt.BorderLayout;
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
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.BaseHelper;
import org.ofbiz.ordermax.base.DatePickerEditPanel;
import org.ofbiz.ordermax.product.productloader.ProductDataTreeLoader;
import org.ofbiz.ordermax.base.components.screen.SimpleScreenInterface;
import org.ofbiz.ordermax.composite.ProductComposite;
import org.ofbiz.ordermax.composite.ProductContentComposite;
import org.ofbiz.ordermax.composite.ProductContentCompositeList;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.ProdCatalog;
import org.ofbiz.ordermax.entity.ProductContentType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.product.UomTimeSingleton;

/**
 *
 * @author siranjeev
 */
public class ProductContentMaintainPanel extends javax.swing.JPanel implements SimpleScreenInterface {

    final public static String module = ProductContentMaintainPanel.class.getName();
    private ListAdapterListModel<ProdCatalog> personListModel = new ListAdapterListModel<ProdCatalog>();
    public GenericTableModelPanel<ProductContentComposite, ProductContentTableModel> tablePanel = null;

//    private ListModelSelection<ProdCatalog> listModelSelection = new ListModelSelection<ProdCatalog>();
//    private ListSelectionModel selectionModel = null; //new DefaultListSelectionModel();
    /**
     * Creates new form ProdCatalogMaintainPanel
     */
    ProductContentComposite productContentComposite = null;
    ContentMaintainPanel contentMaintainPanel = null;
    DataResourceMaintainPanel dataResourceMaintainPanel = null;
    PanelDataResourceImages panelImages = new PanelDataResourceImages();
    PanelDataResourceText panelText = new PanelDataResourceText();
    PanelProductContentSet contentSet = new PanelProductContentSet();
    boolean isNew = false;
    boolean isModified = false;
    private DatePickerEditPanel fromDatePanel = null;
    private DatePickerEditPanel thruDatePanel = null;
    private DatePickerEditPanel purchaseFromDatePanel = null;
    private DatePickerEditPanel purchaseThruDatePanel = null;

    private JGenericComboBoxSelectionModel<ProductContentType> comboProductContentType = null;
    private JGenericComboBoxSelectionModel<Uom> comboUom = null;
    private JGenericComboBoxSelectionModel<RoleType> comboRoleType = null;

    public ProductContentMaintainPanel() {
        initComponents();
        /* ListCellRenderer<ProdCatalog> prodCatalogRenderer = new ProdCatalogListCellRenderer();
         prodCatalogList.setCellRenderer(prodCatalogRenderer);
         prodCatalogList.setEnabled(true);
         prodCatalogList.setSelectionBackground(Color.CYAN);
         */
        tabbedPaneProductContentDetail.add(panelImages, "Image");
        tabbedPaneProductContentDetail.add(panelText, "Text");

        tablePanel = new GenericTableModelPanel<ProductContentComposite, ProductContentTableModel>(new ProductContentTableModel());
        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(BorderLayout.CENTER, tablePanel);

        fromDatePanel = DatePickerEditPanel.addToPanel(panelFromDate);
        thruDatePanel = DatePickerEditPanel.addToPanel(panelThruDate);

        purchaseFromDatePanel = DatePickerEditPanel.addToPanel(panelPurchaseFromDate);
        purchaseThruDatePanel = DatePickerEditPanel.addToPanel(panelPurchaseThruDate);

        contentMaintainPanel = new ContentMaintainPanel();
//        tabbedPaneProductContent.add("Content", contentMaintainPanel);
        dataResourceMaintainPanel = new DataResourceMaintainPanel();
//        tabbedPaneProductContent.add("Data Resource", dataResourceMaintainPanel);
        tabbedPaneProductContent.add(contentSet, "New Content");

        for (int i = 0; i < ProductStoreCatalogTableModel.Columns.values().length; i++) {
            ProductStoreCatalogTableModel.Columns[] columns = ProductStoreCatalogTableModel.Columns.values();
            ProductStoreCatalogTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
        }
        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        comboProductContentType = new JGenericComboBoxSelectionModel<ProductContentType>(panelProductContentTypeId, ProductContentTypeSingleton.getValueList()
        );

        comboUom = new JGenericComboBoxSelectionModel<Uom>(panelUseTimeUom, UomTimeSingleton.getValueList()
        );

        comboRoleType = new JGenericComboBoxSelectionModel<RoleType>(panelUserRole, RoleTypeSingleton.getValueList()
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
                            ProductContentComposite value = tablePanel.listModel.getElementAt(i);
                            setDialogFields(value);

                            contentMaintainPanel.setContent(value.getContent());
                            dataResourceMaintainPanel.setDataResource(value.getDataResource());
                            try {
                                String dirPath = ProductDataTreeLoader.BaseImagePath;
                                String fileName = streamDataResource(XuiContainer.getSession().getDelegator(), value.getDataResource().getdataResourceId());
                                /* boolean val = BaseHelper.isFileExists(dirPath, fileName); 
                                 if(val){
                                 tabbedPaneProductContentDetail.setSelectedIndex(1);
                                 }
                                 else{
                                 tabbedPaneProductContentDetail.setSelectedIndex(2);
                                 }*/

                            } catch (Exception ex) {
                                Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            panelImages.setDataResource(value.getDataResource());
                            panelText.setDataResource(value.getDataResource());
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
//        ComponentBorder.loweredBevelBorder(panelDetail, "Details");
//        ComponentBorder.loweredBevelBorder(panelHeader, "Product Catalog Stores");
    }

//    ProdCatalog prodCatalog = null;
    public void setDialogFields(ProductContentComposite productContentComposite) {
        txtContentId.setText(productContentComposite.getProductContent().getcontentId());
        try {
            comboProductContentType.setSelectedItem(ProductContentTypeSingleton.getProductContentType(productContentComposite.getProductContent().getproductContentTypeId()));
        } catch (Exception ex) {
            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            comboUom.setSelectedItem(UomTimeSingleton.getUom(productContentComposite.getProductContent().getuseTimeUomId()));
        } catch (Exception ex) {
            //  Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            comboRoleType.setSelectedItem(RoleTypeSingleton.getRoleType(productContentComposite.getProductContent().getuseRoleTypeId()));
        } catch (Exception ex) {
            //         Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            fromDatePanel.setTimeStamp(productContentComposite.getProductContent().getfromDate());
        } catch (Exception ex) {
//            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            thruDatePanel.setTimeStamp(productContentComposite.getProductContent().getthruDate());
        } catch (Exception ex) {
//            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            purchaseFromDatePanel.setTimeStamp(productContentComposite.getProductContent().getpurchaseFromDate());
        } catch (Exception ex) {
//            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            purchaseThruDatePanel.setTimeStamp(productContentComposite.getProductContent().getpurchaseThruDate());
        } catch (Exception ex) {
//            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (UtilValidate.isNotEmpty(productContentComposite.getProductContent().getuseCountLimit())) {
            txtUseCountLimit.setText(productContentComposite.getProductContent().getuseCountLimit().toString());
        }

        if (UtilValidate.isNotEmpty(productContentComposite.getProductContent().getuseTime())) {
            txtUseTime.setText(productContentComposite.getProductContent().getuseTime().toString());
        }

        if (UtilValidate.isNotEmpty(productContentComposite.getProductContent().getsequenceNum())) {
            txtSequenceNum.setText(productContentComposite.getProductContent().getsequenceNum().toString());
        }
    }

    public void clearDialogFields() {
        txtContentId.setText("");
        fromDatePanel.txtDate.setText("");
        thruDatePanel.txtDate.setText("");
        txtUseCountLimit.setText("");
        txtSequenceNum.setText("");
        txtUseTime.setText("");
        txtUseCountLimit.setText("");
        purchaseThruDatePanel.txtDate.setText("");
        purchaseFromDatePanel.txtDate.setText("");
    }

    public void getDialogFields(ProductContentComposite productContentComposite) {
        try {
            productContentComposite.getProductContent().setfromDate(fromDatePanel.getTimeStamp());
        } catch (Exception ex) {
            Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            productContentComposite.getProductContent().setthruDate(thruDatePanel.getTimeStamp());
        } catch (Exception ex) {
            productContentComposite.getProductContent().setthruDate(null);
        }

        if (UtilValidate.isNotEmpty(comboProductContentType.getSelectedItem())) {
            productContentComposite.getProductContent().setproductContentTypeId(comboProductContentType.getSelectedItem().getproductContentTypeId());
        }
        if (UtilValidate.isNotEmpty(comboUom.getSelectedItem())) {
            productContentComposite.getProductContent().setuseTimeUomId(comboUom.getSelectedItem().getuomId());
        }
        if (UtilValidate.isNotEmpty(txtUseCountLimit.getText())) {
            productContentComposite.getProductContent().setsequenceNum(Long.parseLong(txtUseCountLimit.getText()));
        }
    }

    public void setProductContentList(ListAdapterListModel<ProductContentComposite> productContentList) {
        tablePanel.setListModel(productContentList);
    }

    public ProductContentComposite getProductStoreCatalog() {
        return productContentComposite;
    }
    ListAdapterListModel<ProductContentComposite> productContentList = new ListAdapterListModel<ProductContentComposite>();
    ;
    ProductComposite productComposite = null;

    public void setProductComposite(ProductComposite productComposite) {
        this.productComposite = productComposite;
        productContentList.clear();
        if (productComposite != null) {
            if (productComposite.getProductContentCompositeList() == null) {
                ProductContentCompositeList list = LoadProductWorker.loadProductContent(productComposite.getProduct().getproductId(), XuiContainer.getSession());
                productComposite.setProductContentCompositeList(list);
            }
            ProductContentCompositeList list = productComposite.getProductContentCompositeList();
            for (ProductContentComposite composite : list.getList()) {
                productContentList.add(composite);
            }
            setProductContentList(productContentList);

        }
        contentSet.setProductComposite(productComposite);

    }

    public void setProductStoreCatalog(ProductContentComposite productContentComposite) {
        this.productContentComposite = productContentComposite;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPaneProductContent = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        tabbedPaneProductContentDetail = new javax.swing.JTabbedPane();
        panelDetail = new javax.swing.JPanel();
        Store = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUseCountLimit = new javax.swing.JTextField();
        panelProductContentTypeId = new javax.swing.JPanel();
        panelFromDate = new javax.swing.JPanel();
        panelThruDate = new javax.swing.JPanel();
        Store1 = new javax.swing.JLabel();
        panelUseTimeUom = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtContentId = new javax.swing.JTextField();
        Store2 = new javax.swing.JLabel();
        panelUserRole = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        panelPurchaseFromDate = new javax.swing.JPanel();
        panelPurchaseThruDate = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtUseTime = new javax.swing.JTextField();
        txtSequenceNum = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(520, 550));
        setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelHeader.setMinimumSize(new java.awt.Dimension(0, 200));
        panelHeader.setPreferredSize(new java.awt.Dimension(489, 200));

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE)
        );

        jPanel2.add(panelHeader, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(826, 300));
        jPanel1.setLayout(new java.awt.GridLayout());

        panelDetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        panelDetail.setPreferredSize(new java.awt.Dimension(829, 300));

        Store.setText("Product Content Type Id:");

        jLabel3.setText("From Date:");

        jLabel4.setText("Thru Date:");

        jLabel5.setText("Use Count Limit:");

        txtUseCountLimit.setMaximumSize(new java.awt.Dimension(100, 26));
        txtUseCountLimit.setPreferredSize(new java.awt.Dimension(200, 26));

        panelProductContentTypeId.setEnabled(false);
        panelProductContentTypeId.setMaximumSize(new java.awt.Dimension(200, 26));
        panelProductContentTypeId.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelProductContentTypeIdLayout = new javax.swing.GroupLayout(panelProductContentTypeId);
        panelProductContentTypeId.setLayout(panelProductContentTypeIdLayout);
        panelProductContentTypeIdLayout.setHorizontalGroup(
            panelProductContentTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelProductContentTypeIdLayout.setVerticalGroup(
            panelProductContentTypeIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelFromDate.setMaximumSize(new java.awt.Dimension(200, 26));
        panelFromDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelFromDateLayout = new javax.swing.GroupLayout(panelFromDate);
        panelFromDate.setLayout(panelFromDateLayout);
        panelFromDateLayout.setHorizontalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelFromDateLayout.setVerticalGroup(
            panelFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelThruDate.setMaximumSize(new java.awt.Dimension(200, 26));
        panelThruDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelThruDateLayout = new javax.swing.GroupLayout(panelThruDate);
        panelThruDate.setLayout(panelThruDateLayout);
        panelThruDateLayout.setHorizontalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelThruDateLayout.setVerticalGroup(
            panelThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        Store1.setText("Use Time Uom:");

        panelUseTimeUom.setMaximumSize(new java.awt.Dimension(200, 26));
        panelUseTimeUom.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelUseTimeUomLayout = new javax.swing.GroupLayout(panelUseTimeUom);
        panelUseTimeUom.setLayout(panelUseTimeUomLayout);
        panelUseTimeUomLayout.setHorizontalGroup(
            panelUseTimeUomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelUseTimeUomLayout.setVerticalGroup(
            panelUseTimeUomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        txtContentId.setMaximumSize(new java.awt.Dimension(200, 26));
        txtContentId.setMinimumSize(new java.awt.Dimension(0, 0));
        txtContentId.setPreferredSize(new java.awt.Dimension(200, 26));

        Store2.setText("User Role:");

        panelUserRole.setMaximumSize(new java.awt.Dimension(200, 26));
        panelUserRole.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelUserRoleLayout = new javax.swing.GroupLayout(panelUserRole);
        panelUserRole.setLayout(panelUserRoleLayout);
        panelUserRoleLayout.setHorizontalGroup(
            panelUserRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelUserRoleLayout.setVerticalGroup(
            panelUserRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel7.setText("Purchase From Date:");

        panelPurchaseFromDate.setMaximumSize(new java.awt.Dimension(100, 26));
        panelPurchaseFromDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelPurchaseFromDateLayout = new javax.swing.GroupLayout(panelPurchaseFromDate);
        panelPurchaseFromDate.setLayout(panelPurchaseFromDateLayout);
        panelPurchaseFromDateLayout.setHorizontalGroup(
            panelPurchaseFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelPurchaseFromDateLayout.setVerticalGroup(
            panelPurchaseFromDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        panelPurchaseThruDate.setMaximumSize(new java.awt.Dimension(100, 26));
        panelPurchaseThruDate.setPreferredSize(new java.awt.Dimension(200, 26));

        javax.swing.GroupLayout panelPurchaseThruDateLayout = new javax.swing.GroupLayout(panelPurchaseThruDate);
        panelPurchaseThruDate.setLayout(panelPurchaseThruDateLayout);
        panelPurchaseThruDateLayout.setHorizontalGroup(
            panelPurchaseThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        panelPurchaseThruDateLayout.setVerticalGroup(
            panelPurchaseThruDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jLabel8.setText("Purchase Thru Date:");

        jLabel9.setText("Use Time:");

        txtUseTime.setMaximumSize(new java.awt.Dimension(100, 26));
        txtUseTime.setPreferredSize(new java.awt.Dimension(200, 26));

        txtSequenceNum.setMaximumSize(new java.awt.Dimension(100, 26));
        txtSequenceNum.setPreferredSize(new java.awt.Dimension(200, 26));

        jLabel11.setText("Sequence Num:");

        javax.swing.GroupLayout panelDetailLayout = new javax.swing.GroupLayout(panelDetail);
        panelDetail.setLayout(panelDetailLayout);
        panelDetailLayout.setHorizontalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Store2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(panelFromDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelUserRole, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelUseTimeUom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelProductContentTypeId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContentId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelThruDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUseTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSequenceNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUseCountLimit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelPurchaseThruDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelPurchaseFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelPurchaseFromDate, panelPurchaseThruDate, txtSequenceNum, txtUseCountLimit, txtUseTime});

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, saveButton});

        panelDetailLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelFromDate, panelProductContentTypeId, panelThruDate, panelUseTimeUom, panelUserRole, txtContentId});

        panelDetailLayout.setVerticalGroup(
            panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtContentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Store)
                            .addComponent(panelProductContentTypeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Store1)
                            .addComponent(panelUseTimeUom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Store2)
                            .addComponent(panelUserRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(panelFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(panelThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDetailLayout.createSequentialGroup()
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(panelPurchaseFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(panelPurchaseThruDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtUseCountLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtUseTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtSequenceNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(newButton)
                    .addComponent(deleteButton))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        tabbedPaneProductContentDetail.addTab("Detail", panelDetail);

        jPanel1.add(tabbedPaneProductContentDetail);

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        tabbedPaneProductContent.addTab("Product Content", jPanel2);

        add(tabbedPaneProductContent);

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        getDialogFields(productContentComposite);
        /*
         try {
         (new LoadProductCatalogWorker()).saveProdCatalogStore(productContentComposite, XuiContainer.getSession());
         if (isNew) {
         tablePanel.listModel.add(productContentComposite);
         }
         isNew = false;
         //        final SaveProdCatalogAction saveAction = new SaveProdCatalogAction(catalogListSelectionModel.dataListModel, XuiContainer.getSession());
         //        saveAction.actionPerformed(evt);
         } catch (Exception ex) {
         Logger.getLogger(ProductContentMaintainPanel.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        isNew = true;
        isModified = false;
        productContentComposite = new ProductContentComposite();
        clearDialogFields();
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Store;
    private javax.swing.JLabel Store1;
    private javax.swing.JLabel Store2;
    public javax.swing.JButton deleteButton;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JButton newButton;
    private javax.swing.JPanel panelDetail;
    private javax.swing.JPanel panelFromDate;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JPanel panelProductContentTypeId;
    private javax.swing.JPanel panelPurchaseFromDate;
    private javax.swing.JPanel panelPurchaseThruDate;
    private javax.swing.JPanel panelThruDate;
    private javax.swing.JPanel panelUseTimeUom;
    private javax.swing.JPanel panelUserRole;
    public javax.swing.JButton saveButton;
    private javax.swing.JTabbedPane tabbedPaneProductContent;
    private javax.swing.JTabbedPane tabbedPaneProductContentDetail;
    private javax.swing.JTextField txtContentId;
    private javax.swing.JTextField txtSequenceNum;
    private javax.swing.JTextField txtUseCountLimit;
    private javax.swing.JTextField txtUseTime;
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

    public static String streamDataResource(Delegator delegator, String dataResourceId) throws Exception {
        try {
            GenericValue dataResource = delegator.findOne("DataResource", UtilMisc.toMap("dataResourceId", dataResourceId), true);
            if (dataResource == null) {
                throw new Exception("Error in streamDataResource: DataResource with ID [" + dataResourceId + "] was not found.");
            }
            String dataResourceTypeId = dataResource.getString("dataResourceTypeId");
            if (UtilValidate.isEmpty(dataResourceTypeId)) {
                dataResourceTypeId = "SHORT_TEXT";
            }
            String mimeTypeId = dataResource.getString("mimeTypeId");
            if (UtilValidate.isEmpty(mimeTypeId)) {
                mimeTypeId = "text/html";
            }

            if (dataResourceTypeId.equals("SHORT_TEXT")) {
                String text = dataResource.getString("objectInfo");
                return text;
                //os.write(text.getBytes());
            } else if (dataResourceTypeId.equals("ELECTRONIC_TEXT")) {
                GenericValue electronicText = delegator.findOne("ElectronicText", UtilMisc.toMap("dataResourceId", dataResourceId), true);
                if (electronicText != null) {
                    String text = electronicText.getString("textData");
                    if (text != null) {
//                        os.write(text.getBytes());
                        return text;
                    }
                }
            } else if (dataResourceTypeId.equals("IMAGE_OBJECT")) {
                /*byte[] imageBytes = acquireImage(delegator, dataResource);
                 if (imageBytes != null) {
                 os.write(imageBytes);
                 }*/
            } else if (dataResourceTypeId.equals("LINK")) {
                /*String text = dataResource.getString("objectInfo");
                 os.write(text.getBytes());*/
            } else if (dataResourceTypeId.equals("URL_RESOURCE")) {
                /*URL url = new URL(dataResource.getString("objectInfo"));
                 if (url.getHost() == null) { // is relative
                 String prefix = buildRequestPrefix(delegator, locale, webSiteId, https);
                 String sep = "";
                 //String s = "";
                 if (url.toString().indexOf("/") != 0 && prefix.lastIndexOf("/") != (prefix.length() - 1)) {
                 sep = "/";
                 }
                 String s2 = prefix + sep + url.toString();
                 url = new URL(s2);
                 }
                 InputStream in = url.openStream();
                 UtilIO.copy(in, true, os, false);*/
            } else if (dataResourceTypeId.indexOf("_FILE") >= 0) {
                /*String objectInfo = dataResource.getString("objectInfo");
                 File inputFile = getContentFile(dataResourceTypeId, objectInfo, rootDir);
                 //long fileSize = inputFile.length();
                 FileInputStream fis = new FileInputStream(inputFile);
                 UtilIO.copy(fis, true, os, false);
                 */
            } else {
                throw new Exception("The dataResourceTypeId [" + dataResourceTypeId + "] is not supported in streamDataResource");
            }
        } catch (GenericEntityException e) {
            throw new Exception("Error in streamDataResource", e);
        }
        return "";
    }
}
