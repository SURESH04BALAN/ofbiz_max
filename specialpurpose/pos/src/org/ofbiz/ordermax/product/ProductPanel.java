package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.entity.Product;

import org.ofbiz.base.util.Debug;

import org.ofbiz.entity.GenericValue;

import org.ofbiz.ordermax.generic.GenericValueObjectInterface;

import org.ofbiz.ordermax.generic.GenericValuePanelInterfaceOrderMax;

import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;

import javax.swing.*;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.LookupActionListner;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

public class ProductPanel extends JPanel implements GenericValuePanelInterfaceOrderMax {

    public static final String module = ProductPanel.class.getName();
    private Product product;
    private javax.swing.JLabel productWidthLabel;
    private javax.swing.JTextField productWidthTextField;
    private javax.swing.JLabel fixedAmountLabel;
    private javax.swing.JTextField fixedAmountTextField;
    private javax.swing.JLabel reservMaxPersonsLabel;
    private javax.swing.JTextField reservMaxPersonsTextField;
    private javax.swing.JLabel autoCreateKeywordsLabel;
    private javax.swing.JTextField autoCreateKeywordsTextField;
    private javax.swing.JLabel productTypeIdLabel;
    private javax.swing.JTextField productTypeIdTextField;
    private javax.swing.JLabel configIdLabel;
    private javax.swing.JTextField configIdTextField;
    private javax.swing.JLabel shippingWidthLabel;
    private javax.swing.JTextField shippingWidthTextField;
    private javax.swing.JLabel productIdLabel;
    private javax.swing.JTextField productIdTextField;
    private javax.swing.JLabel orderDecimalQuantityLabel;
    private javax.swing.JTextField orderDecimalQuantityTextField;
    private javax.swing.JLabel internalNameLabel;
    private javax.swing.JTextField internalNameTextField;
    private javax.swing.JLabel productDiameterLabel;
    private javax.swing.JTextField productDiameterTextField;
    private javax.swing.JLabel introductionDateLabel;
    private javax.swing.JTextField introductionDateTextField;
    private javax.swing.JLabel productHeightLabel;
    private javax.swing.JTextField productHeightTextField;
    private javax.swing.JLabel widthUomIdLabel;
    private javax.swing.JTextField widthUomIdTextField;
    private javax.swing.JLabel createdByUserLoginLabel;
    private javax.swing.JTextField createdByUserLoginTextField;
    private javax.swing.JLabel piecesIncludedLabel;
    private javax.swing.JTextField piecesIncludedTextField;
    private javax.swing.JLabel virtualVariantMethodEnumLabel;
    private javax.swing.JTextField virtualVariantMethodEnumTextField;
    private javax.swing.JLabel depthUomIdLabel;
    private javax.swing.JTextField depthUomIdTextField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JLabel isVirtualLabel;
    private javax.swing.JTextField isVirtualTextField;
    private javax.swing.JLabel quantityUomIdLabel;
    private javax.swing.JTextField quantityUomIdTextField;
    private javax.swing.JLabel originalImageUrlLabel;
    private javax.swing.JTextField originalImageUrlTextField;
    private javax.swing.JLabel shippingDepthLabel;
    private javax.swing.JTextField shippingDepthTextField;
    private javax.swing.JLabel taxableLabel;
    private javax.swing.JTextField taxableTextField;
    private javax.swing.JLabel reserv2ndPPPercLabel;
    private javax.swing.JTextField reserv2ndPPPercTextField;
    private javax.swing.JLabel facilityIdLabel;
    private javax.swing.JTextField facilityIdTextField;
    private javax.swing.JLabel includeInPromotionsLabel;
    private javax.swing.JTextField includeInPromotionsTextField;
    private javax.swing.JLabel mediumImageUrlLabel;
    private javax.swing.JTextField mediumImageUrlTextField;
    private javax.swing.JLabel manufacturerPartyIdLabel;
    private javax.swing.JTextField manufacturerPartyIdTextField;
    private javax.swing.JLabel productWeightLabel;
    private javax.swing.JTextField productWeightTextField;
    private javax.swing.JLabel defaultShipmentBoxTypeIdLabel;
    private javax.swing.JTextField defaultShipmentBoxTypeIdTextField;
    private javax.swing.JLabel supportDiscontinuationDateLabel;
    private javax.swing.JTextField supportDiscontinuationDateTextField;
    private javax.swing.JLabel reservNthPPPercLabel;
    private javax.swing.JTextField reservNthPPPercTextField;
    private javax.swing.JLabel brandNameLabel;
    private javax.swing.JTextField brandNameTextField;
    private javax.swing.JLabel quantityIncludedLabel;
    private javax.swing.JTextField quantityIncludedTextField;
    private javax.swing.JLabel inShippingBoxLabel;
    private javax.swing.JTextField inShippingBoxTextField;
    private javax.swing.JLabel diameterUomIdLabel;
    private javax.swing.JTextField diameterUomIdTextField;
    private javax.swing.JLabel longDescriptionLabel;
    private javax.swing.JTextField longDescriptionTextField;
    private javax.swing.JLabel requirementMethodEnumIdLabel;
    private javax.swing.JTextField requirementMethodEnumIdTextField;
    private javax.swing.JLabel shippingHeightLabel;
    private javax.swing.JTextField shippingHeightTextField;
    private javax.swing.JLabel heightUomIdLabel;
    private javax.swing.JTextField heightUomIdTextField;
    private javax.swing.JLabel inventoryMessageLabel;
    private javax.swing.JTextField inventoryMessageTextField;
    private javax.swing.JLabel ratingTypeEnumLabel;
    private javax.swing.JTextField ratingTypeEnumTextField;
    private javax.swing.JLabel lastModifiedDateLabel;
    private javax.swing.JTextField lastModifiedDateTextField;
    private javax.swing.JLabel billOfMaterialLevelLabel;
    private javax.swing.JTextField billOfMaterialLevelTextField;
    private javax.swing.JLabel commentsLabel;
    private javax.swing.JTextField commentsTextField;
    private javax.swing.JLabel returnableLabel;
    private javax.swing.JTextField returnableTextField;
    private javax.swing.JLabel productDepthLabel;
    private javax.swing.JTextField productDepthTextField;
    private javax.swing.JLabel salesDiscontinuationDateLabel;
    private javax.swing.JTextField salesDiscontinuationDateTextField;
    private javax.swing.JLabel requireAmountLabel;
    private javax.swing.JTextField requireAmountTextField;
    private javax.swing.JLabel isVariantLabel;
    private javax.swing.JTextField isVariantTextField;
    private javax.swing.JLabel weightUomIdLabel;
    private javax.swing.JTextField weightUomIdTextField;
    private javax.swing.JLabel primaryProductCategoryIdLabel;
    private javax.swing.JTextField primaryProductCategoryIdTextField;
    private javax.swing.JLabel releaseDateLabel;
    private javax.swing.JTextField releaseDateTextField;
    private javax.swing.JLabel largeImageUrlLabel;
    private javax.swing.JTextField largeImageUrlTextField;
    private javax.swing.JLabel detailScreenLabel;
    private javax.swing.JTextField detailScreenTextField;
    private javax.swing.JLabel lastModifiedByUserLoginLabel;
    private javax.swing.JTextField lastModifiedByUserLoginTextField;
    private javax.swing.JLabel detailImageUrlLabel;
    private javax.swing.JTextField detailImageUrlTextField;
    private javax.swing.JLabel createdDateLabel;
    private javax.swing.JTextField createdDateTextField;
    private javax.swing.JLabel chargeShippingLabel;
    private javax.swing.JTextField chargeShippingTextField;
    private javax.swing.JLabel productNameLabel;
    private javax.swing.JTextField productNameTextField;
    private javax.swing.JLabel requireInventoryLabel;
    private javax.swing.JTextField requireInventoryTextField;
    private javax.swing.JLabel weightLabel;
    private javax.swing.JTextField weightTextField;
    private javax.swing.JLabel productRatingLabel;
    private javax.swing.JTextField productRatingTextField;
    private javax.swing.JLabel priceDetailTextLabel;
    private javax.swing.JTextField priceDetailTextTextField;
    private javax.swing.JLabel salesDiscWhenNotAvailLabel;
    private javax.swing.JTextField salesDiscWhenNotAvailTextField;
    private javax.swing.JLabel amountUomTypeIdLabel;
    private javax.swing.JTextField amountUomTypeIdTextField;
    private javax.swing.JLabel originGeoIdLabel;
    private javax.swing.JTextField originGeoIdTextField;
    private javax.swing.JLabel smallImageUrlLabel;
    private javax.swing.JTextField smallImageUrlTextField;
    private JButton button;
    private ComponentBorder cb;

    public boolean isModified() {

        return isModified;

    }

    public void setIsModified(boolean isModified) {

        this.isModified = isModified;

    }
    protected boolean isModified = false;

    public ProductPanel(Product val) {

        this.product = val;

//        initComponents();

    }

    public ProductPanel() {

  //      initComponents();

    }

    private void initComponents() {

        productWidthLabel = new javax.swing.JLabel();

        productWidthTextField = new javax.swing.JTextField();

        productWidthTextField.getDocument().addDocumentListener(new TextChangeListner());

        fixedAmountLabel = new javax.swing.JLabel();

        fixedAmountTextField = new javax.swing.JTextField();

        fixedAmountTextField.getDocument().addDocumentListener(new TextChangeListner());

        reservMaxPersonsLabel = new javax.swing.JLabel();

        reservMaxPersonsTextField = new javax.swing.JTextField();

        reservMaxPersonsTextField.getDocument().addDocumentListener(new TextChangeListner());

        autoCreateKeywordsLabel = new javax.swing.JLabel();

        autoCreateKeywordsTextField = new javax.swing.JTextField();

        autoCreateKeywordsTextField.getDocument().addDocumentListener(new TextChangeListner());

        productTypeIdLabel = new javax.swing.JLabel();

        productTypeIdTextField = new javax.swing.JTextField();

        productTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(productTypeIdTextField);

        button.addActionListener(new LookupActionListner(productTypeIdTextField, "productTypeIdTextField"));

        configIdLabel = new javax.swing.JLabel();

        configIdTextField = new javax.swing.JTextField();

        configIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(configIdTextField);

        button.addActionListener(new LookupActionListner(configIdTextField, "configIdTextField"));

        shippingWidthLabel = new javax.swing.JLabel();

        shippingWidthTextField = new javax.swing.JTextField();

        shippingWidthTextField.getDocument().addDocumentListener(new TextChangeListner());

        productIdLabel = new javax.swing.JLabel();

        productIdTextField = new javax.swing.JTextField();

        productIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(productIdTextField);

        button.addActionListener(new LookupActionListner(productIdTextField, "productIdTextField"));

        orderDecimalQuantityLabel = new javax.swing.JLabel();

        orderDecimalQuantityTextField = new javax.swing.JTextField();

        orderDecimalQuantityTextField.getDocument().addDocumentListener(new TextChangeListner());

        internalNameLabel = new javax.swing.JLabel();

        internalNameTextField = new javax.swing.JTextField();

        internalNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        productDiameterLabel = new javax.swing.JLabel();

        productDiameterTextField = new javax.swing.JTextField();

        productDiameterTextField.getDocument().addDocumentListener(new TextChangeListner());

        introductionDateLabel = new javax.swing.JLabel();

        introductionDateTextField = new javax.swing.JTextField();

        introductionDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        productHeightLabel = new javax.swing.JLabel();

        productHeightTextField = new javax.swing.JTextField();

        productHeightTextField.getDocument().addDocumentListener(new TextChangeListner());

        widthUomIdLabel = new javax.swing.JLabel();

        widthUomIdTextField = new javax.swing.JTextField();

        widthUomIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(widthUomIdTextField);

        button.addActionListener(new LookupActionListner(widthUomIdTextField, "widthUomIdTextField"));

        createdByUserLoginLabel = new javax.swing.JLabel();

        createdByUserLoginTextField = new javax.swing.JTextField();

        createdByUserLoginTextField.getDocument().addDocumentListener(new TextChangeListner());

        piecesIncludedLabel = new javax.swing.JLabel();

        piecesIncludedTextField = new javax.swing.JTextField();

        piecesIncludedTextField.getDocument().addDocumentListener(new TextChangeListner());

        virtualVariantMethodEnumLabel = new javax.swing.JLabel();

        virtualVariantMethodEnumTextField = new javax.swing.JTextField();

        virtualVariantMethodEnumTextField.getDocument().addDocumentListener(new TextChangeListner());

        depthUomIdLabel = new javax.swing.JLabel();

        depthUomIdTextField = new javax.swing.JTextField();

        depthUomIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(depthUomIdTextField);

        button.addActionListener(new LookupActionListner(depthUomIdTextField, "depthUomIdTextField"));

        descriptionLabel = new javax.swing.JLabel();

        descriptionTextField = new javax.swing.JTextField();

        descriptionTextField.getDocument().addDocumentListener(new TextChangeListner());

        isVirtualLabel = new javax.swing.JLabel();

        isVirtualTextField = new javax.swing.JTextField();

        isVirtualTextField.getDocument().addDocumentListener(new TextChangeListner());

        quantityUomIdLabel = new javax.swing.JLabel();

        quantityUomIdTextField = new javax.swing.JTextField();

        quantityUomIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(quantityUomIdTextField);

        button.addActionListener(new LookupActionListner(quantityUomIdTextField, "quantityUomIdTextField"));

        originalImageUrlLabel = new javax.swing.JLabel();

        originalImageUrlTextField = new javax.swing.JTextField();

        originalImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        shippingDepthLabel = new javax.swing.JLabel();

        shippingDepthTextField = new javax.swing.JTextField();

        shippingDepthTextField.getDocument().addDocumentListener(new TextChangeListner());

        taxableLabel = new javax.swing.JLabel();

        taxableTextField = new javax.swing.JTextField();

        taxableTextField.getDocument().addDocumentListener(new TextChangeListner());

        reserv2ndPPPercLabel = new javax.swing.JLabel();

        reserv2ndPPPercTextField = new javax.swing.JTextField();

        reserv2ndPPPercTextField.getDocument().addDocumentListener(new TextChangeListner());

        facilityIdLabel = new javax.swing.JLabel();

        facilityIdTextField = new javax.swing.JTextField();

        facilityIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(facilityIdTextField);

        button.addActionListener(new LookupActionListner(facilityIdTextField, "facilityIdTextField"));

        includeInPromotionsLabel = new javax.swing.JLabel();

        includeInPromotionsTextField = new javax.swing.JTextField();

        includeInPromotionsTextField.getDocument().addDocumentListener(new TextChangeListner());

        mediumImageUrlLabel = new javax.swing.JLabel();

        mediumImageUrlTextField = new javax.swing.JTextField();

        mediumImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        manufacturerPartyIdLabel = new javax.swing.JLabel();

        manufacturerPartyIdTextField = new javax.swing.JTextField();

        manufacturerPartyIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(manufacturerPartyIdTextField);

        button.addActionListener(new LookupActionListner(manufacturerPartyIdTextField, "manufacturerPartyIdTextField"));

        productWeightLabel = new javax.swing.JLabel();

        productWeightTextField = new javax.swing.JTextField();

        productWeightTextField.getDocument().addDocumentListener(new TextChangeListner());

        defaultShipmentBoxTypeIdLabel = new javax.swing.JLabel();

        defaultShipmentBoxTypeIdTextField = new javax.swing.JTextField();

        defaultShipmentBoxTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(defaultShipmentBoxTypeIdTextField);

        button.addActionListener(new LookupActionListner(defaultShipmentBoxTypeIdTextField, "defaultShipmentBoxTypeIdTextField"));

        supportDiscontinuationDateLabel = new javax.swing.JLabel();

        supportDiscontinuationDateTextField = new javax.swing.JTextField();

        supportDiscontinuationDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        reservNthPPPercLabel = new javax.swing.JLabel();

        reservNthPPPercTextField = new javax.swing.JTextField();

        reservNthPPPercTextField.getDocument().addDocumentListener(new TextChangeListner());

        brandNameLabel = new javax.swing.JLabel();

        brandNameTextField = new javax.swing.JTextField();

        brandNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        quantityIncludedLabel = new javax.swing.JLabel();

        quantityIncludedTextField = new javax.swing.JTextField();

        quantityIncludedTextField.getDocument().addDocumentListener(new TextChangeListner());

        inShippingBoxLabel = new javax.swing.JLabel();

        inShippingBoxTextField = new javax.swing.JTextField();

        inShippingBoxTextField.getDocument().addDocumentListener(new TextChangeListner());

        diameterUomIdLabel = new javax.swing.JLabel();

        diameterUomIdTextField = new javax.swing.JTextField();

        diameterUomIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(diameterUomIdTextField);

        button.addActionListener(new LookupActionListner(diameterUomIdTextField, "diameterUomIdTextField"));

        longDescriptionLabel = new javax.swing.JLabel();

        longDescriptionTextField = new javax.swing.JTextField();

        longDescriptionTextField.getDocument().addDocumentListener(new TextChangeListner());

        requirementMethodEnumIdLabel = new javax.swing.JLabel();

        requirementMethodEnumIdTextField = new javax.swing.JTextField();

        requirementMethodEnumIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(requirementMethodEnumIdTextField);

        button.addActionListener(new LookupActionListner(requirementMethodEnumIdTextField, "requirementMethodEnumIdTextField"));

        shippingHeightLabel = new javax.swing.JLabel();

        shippingHeightTextField = new javax.swing.JTextField();

        shippingHeightTextField.getDocument().addDocumentListener(new TextChangeListner());

        heightUomIdLabel = new javax.swing.JLabel();

        heightUomIdTextField = new javax.swing.JTextField();

        heightUomIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(heightUomIdTextField);

        button.addActionListener(new LookupActionListner(heightUomIdTextField, "heightUomIdTextField"));

        inventoryMessageLabel = new javax.swing.JLabel();

        inventoryMessageTextField = new javax.swing.JTextField();

        inventoryMessageTextField.getDocument().addDocumentListener(new TextChangeListner());

        ratingTypeEnumLabel = new javax.swing.JLabel();

        ratingTypeEnumTextField = new javax.swing.JTextField();

        ratingTypeEnumTextField.getDocument().addDocumentListener(new TextChangeListner());

        lastModifiedDateLabel = new javax.swing.JLabel();

        lastModifiedDateTextField = new javax.swing.JTextField();

        lastModifiedDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        billOfMaterialLevelLabel = new javax.swing.JLabel();

        billOfMaterialLevelTextField = new javax.swing.JTextField();

        billOfMaterialLevelTextField.getDocument().addDocumentListener(new TextChangeListner());

        commentsLabel = new javax.swing.JLabel();

        commentsTextField = new javax.swing.JTextField();

        commentsTextField.getDocument().addDocumentListener(new TextChangeListner());

        returnableLabel = new javax.swing.JLabel();

        returnableTextField = new javax.swing.JTextField();

        returnableTextField.getDocument().addDocumentListener(new TextChangeListner());

        productDepthLabel = new javax.swing.JLabel();

        productDepthTextField = new javax.swing.JTextField();

        productDepthTextField.getDocument().addDocumentListener(new TextChangeListner());

        salesDiscontinuationDateLabel = new javax.swing.JLabel();

        salesDiscontinuationDateTextField = new javax.swing.JTextField();

        salesDiscontinuationDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        requireAmountLabel = new javax.swing.JLabel();

        requireAmountTextField = new javax.swing.JTextField();

        requireAmountTextField.getDocument().addDocumentListener(new TextChangeListner());

        isVariantLabel = new javax.swing.JLabel();

        isVariantTextField = new javax.swing.JTextField();

        isVariantTextField.getDocument().addDocumentListener(new TextChangeListner());

        weightUomIdLabel = new javax.swing.JLabel();

        weightUomIdTextField = new javax.swing.JTextField();

        weightUomIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(weightUomIdTextField);

        button.addActionListener(new LookupActionListner(weightUomIdTextField, "weightUomIdTextField"));

        primaryProductCategoryIdLabel = new javax.swing.JLabel();

        primaryProductCategoryIdTextField = new javax.swing.JTextField();

        primaryProductCategoryIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(primaryProductCategoryIdTextField);

        button.addActionListener(new LookupActionListner(primaryProductCategoryIdTextField, "primaryProductCategoryIdTextField"));

        releaseDateLabel = new javax.swing.JLabel();

        releaseDateTextField = new javax.swing.JTextField();

        releaseDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        largeImageUrlLabel = new javax.swing.JLabel();

        largeImageUrlTextField = new javax.swing.JTextField();

        largeImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        detailScreenLabel = new javax.swing.JLabel();

        detailScreenTextField = new javax.swing.JTextField();

        detailScreenTextField.getDocument().addDocumentListener(new TextChangeListner());

        lastModifiedByUserLoginLabel = new javax.swing.JLabel();

        lastModifiedByUserLoginTextField = new javax.swing.JTextField();

        lastModifiedByUserLoginTextField.getDocument().addDocumentListener(new TextChangeListner());

        detailImageUrlLabel = new javax.swing.JLabel();

        detailImageUrlTextField = new javax.swing.JTextField();

        detailImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        createdDateLabel = new javax.swing.JLabel();

        createdDateTextField = new javax.swing.JTextField();

        createdDateTextField.getDocument().addDocumentListener(new TextChangeListner());

        chargeShippingLabel = new javax.swing.JLabel();

        chargeShippingTextField = new javax.swing.JTextField();

        chargeShippingTextField.getDocument().addDocumentListener(new TextChangeListner());

        productNameLabel = new javax.swing.JLabel();

        productNameTextField = new javax.swing.JTextField();

        productNameTextField.getDocument().addDocumentListener(new TextChangeListner());

        requireInventoryLabel = new javax.swing.JLabel();

        requireInventoryTextField = new javax.swing.JTextField();

        requireInventoryTextField.getDocument().addDocumentListener(new TextChangeListner());

        weightLabel = new javax.swing.JLabel();

        weightTextField = new javax.swing.JTextField();

        weightTextField.getDocument().addDocumentListener(new TextChangeListner());

        productRatingLabel = new javax.swing.JLabel();

        productRatingTextField = new javax.swing.JTextField();

        productRatingTextField.getDocument().addDocumentListener(new TextChangeListner());

        priceDetailTextLabel = new javax.swing.JLabel();

        priceDetailTextTextField = new javax.swing.JTextField();

        priceDetailTextTextField.getDocument().addDocumentListener(new TextChangeListner());

        salesDiscWhenNotAvailLabel = new javax.swing.JLabel();

        salesDiscWhenNotAvailTextField = new javax.swing.JTextField();

        salesDiscWhenNotAvailTextField.getDocument().addDocumentListener(new TextChangeListner());

        amountUomTypeIdLabel = new javax.swing.JLabel();

        amountUomTypeIdTextField = new javax.swing.JTextField();

        amountUomTypeIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(amountUomTypeIdTextField);

        button.addActionListener(new LookupActionListner(amountUomTypeIdTextField, "amountUomTypeIdTextField"));

        originGeoIdLabel = new javax.swing.JLabel();

        originGeoIdTextField = new javax.swing.JTextField();

        originGeoIdTextField.getDocument().addDocumentListener(new TextChangeListner());

        button = new JButton("...");

        cb = new ComponentBorder(button);

        cb.install(originGeoIdTextField);

        button.addActionListener(new LookupActionListner(originGeoIdTextField, "originGeoIdTextField"));

        smallImageUrlLabel = new javax.swing.JLabel();

        smallImageUrlTextField = new javax.swing.JTextField();

        smallImageUrlTextField.getDocument().addDocumentListener(new TextChangeListner());

        productWidthLabel.setText("Product Width:");

        fixedAmountLabel.setText("Fixed Amount:");

        reservMaxPersonsLabel.setText("Reserv Max Persons:");

        autoCreateKeywordsLabel.setText("Auto Create Keywords:");

        productTypeIdLabel.setText("Product Type Id:");

        configIdLabel.setText("Config Id:");

        shippingWidthLabel.setText("Shipping Width:");

        productIdLabel.setText("Product Id:");

        orderDecimalQuantityLabel.setText("Order Decimal Quantity:");

        internalNameLabel.setText("Internal Name:");

        productDiameterLabel.setText("Product Diameter:");

        introductionDateLabel.setText("Introduction Date:");

        productHeightLabel.setText("Product Height:");

        widthUomIdLabel.setText("Width Uom Id:");

        createdByUserLoginLabel.setText("Created By User Login:");

        piecesIncludedLabel.setText("Pieces Included:");

        virtualVariantMethodEnumLabel.setText("Virtual Variant Method Enum:");

        depthUomIdLabel.setText("Depth Uom Id:");

        descriptionLabel.setText("Description:");

        isVirtualLabel.setText("Is Virtual:");

        quantityUomIdLabel.setText("Quantity Uom Id:");

        originalImageUrlLabel.setText("Original Image Url:");

        shippingDepthLabel.setText("Shipping Depth:");

        taxableLabel.setText("Taxable:");

        reserv2ndPPPercLabel.setText("Reserv 2Nd Pp Perc:");

        facilityIdLabel.setText("Facility Id:");

        includeInPromotionsLabel.setText("Include In Promotions:");

        mediumImageUrlLabel.setText("Medium Image Url:");

        manufacturerPartyIdLabel.setText("Manufacturer Party Id:");

        productWeightLabel.setText("Product Weight:");

        defaultShipmentBoxTypeIdLabel.setText("Default Shipment Box Type Id:");

        supportDiscontinuationDateLabel.setText("Support Discontinuation Date:");

        reservNthPPPercLabel.setText("Reserv Nth Pp Perc:");

        brandNameLabel.setText("Brand Name:");

        quantityIncludedLabel.setText("Quantity Included:");

        inShippingBoxLabel.setText("In Shipping Box:");

        diameterUomIdLabel.setText("Diameter Uom Id:");

        longDescriptionLabel.setText("Long Description:");

        requirementMethodEnumIdLabel.setText("Requirement Method Enum Id:");

        shippingHeightLabel.setText("Shipping Height:");

        heightUomIdLabel.setText("Height Uom Id:");

        inventoryMessageLabel.setText("Inventory Message:");

        ratingTypeEnumLabel.setText("Rating Type Enum:");

        lastModifiedDateLabel.setText("Last Modified Date:");

        billOfMaterialLevelLabel.setText("Bill Of Material Level:");

        commentsLabel.setText("Comments:");

        returnableLabel.setText("Returnable:");

        productDepthLabel.setText("Product Depth:");

        salesDiscontinuationDateLabel.setText("Sales Discontinuation Date:");

        requireAmountLabel.setText("Require Amount:");

        isVariantLabel.setText("Is Variant:");

        weightUomIdLabel.setText("Weight Uom Id:");

        primaryProductCategoryIdLabel.setText("Primary Product Category Id:");

        releaseDateLabel.setText("Release Date:");

        largeImageUrlLabel.setText("Large Image Url:");

        detailScreenLabel.setText("Detail Screen:");

        lastModifiedByUserLoginLabel.setText("Last Modified By User Login:");

        detailImageUrlLabel.setText("Detail Image Url:");

        createdDateLabel.setText("Created Date:");

        chargeShippingLabel.setText("Charge Shipping:");

        productNameLabel.setText("Product Name:");

        requireInventoryLabel.setText("Require Inventory:");

        weightLabel.setText("Weight:");

        productRatingLabel.setText("Product Rating:");

        priceDetailTextLabel.setText("Price Detail Text:");

        salesDiscWhenNotAvailLabel.setText("Sales Disc When Not Avail:");

        amountUomTypeIdLabel.setText("Amount Uom Type Id:");

        originGeoIdLabel.setText("Origin Geo Id:");

        smallImageUrlLabel.setText("Small Image Url:");

        org.jdesktop.layout.GroupLayout layoutPanel = new org.jdesktop.layout.GroupLayout(this);

//        this.setLayout(layoutPanel);

        layoutPanel.setHorizontalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(depthUomIdLabel)
                .add(productTypeIdLabel)
                .add(quantityUomIdLabel)
                .add(manufacturerPartyIdLabel)
                .add(weightUomIdLabel)
                .add(amountUomTypeIdLabel)
                .add(defaultShipmentBoxTypeIdLabel)
                .add(facilityIdLabel)
                .add(productIdLabel)
                .add(configIdLabel)
                .add(heightUomIdLabel)
                .add(diameterUomIdLabel)
                .add(widthUomIdLabel)
                .add(originGeoIdLabel)
                .add(requirementMethodEnumIdLabel)
                .add(primaryProductCategoryIdLabel)
                .add(internalNameLabel)
                .add(reservNthPPPercLabel)
                .add(brandNameLabel)
                .add(billOfMaterialLevelLabel)
                .add(productHeightLabel)
                .add(quantityIncludedLabel)
                .add(descriptionLabel)
                .add(longDescriptionLabel)
                .add(reservMaxPersonsLabel)
                .add(mediumImageUrlLabel)
                .add(createdDateLabel)
                .add(autoCreateKeywordsLabel)
                .add(shippingWidthLabel)
                .add(taxableLabel)
                .add(salesDiscWhenNotAvailLabel)
                .add(productRatingLabel)
                .add(lastModifiedByUserLoginLabel)
                .add(ratingTypeEnumLabel)
                .add(fixedAmountLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(depthUomIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(quantityUomIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(manufacturerPartyIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(weightUomIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(amountUomTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(defaultShipmentBoxTypeIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(facilityIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(configIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(heightUomIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(diameterUomIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(widthUomIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(originGeoIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(requirementMethodEnumIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(primaryProductCategoryIdTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(internalNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(reservNthPPPercTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(brandNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(billOfMaterialLevelTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productHeightTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(quantityIncludedTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(descriptionTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(longDescriptionTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(reservMaxPersonsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(mediumImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(createdDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(autoCreateKeywordsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(shippingWidthTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(taxableTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(salesDiscWhenNotAvailTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productRatingTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(lastModifiedByUserLoginTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(ratingTypeEnumTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(fixedAmountTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(returnableLabel)
                .add(piecesIncludedLabel)
                .add(weightLabel)
                .add(virtualVariantMethodEnumLabel)
                .add(salesDiscontinuationDateLabel)
                .add(chargeShippingLabel)
                .add(includeInPromotionsLabel)
                .add(supportDiscontinuationDateLabel)
                .add(detailScreenLabel)
                .add(originalImageUrlLabel)
                .add(introductionDateLabel)
                .add(releaseDateLabel)
                .add(requireAmountLabel)
                .add(productDepthLabel)
                .add(shippingDepthLabel)
                .add(largeImageUrlLabel)
                .add(inShippingBoxLabel)
                .add(reserv2ndPPPercLabel)
                .add(requireInventoryLabel)
                .add(detailImageUrlLabel)
                .add(productWidthLabel)
                .add(productDiameterLabel)
                .add(orderDecimalQuantityLabel)
                .add(smallImageUrlLabel)
                .add(isVirtualLabel)
                .add(priceDetailTextLabel)
                .add(productWeightLabel)
                .add(lastModifiedDateLabel)
                .add(shippingHeightLabel)
                .add(inventoryMessageLabel)
                .add(productNameLabel)
                .add(commentsLabel)
                .add(isVariantLabel)
                .add(createdByUserLoginLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(returnableTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(piecesIncludedTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(weightTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(virtualVariantMethodEnumTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(salesDiscontinuationDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(chargeShippingTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(includeInPromotionsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(supportDiscontinuationDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(detailScreenTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(originalImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(introductionDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(releaseDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(requireAmountTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productDepthTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(shippingDepthTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(largeImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(inShippingBoxTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(reserv2ndPPPercTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(requireInventoryTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(detailImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productWidthTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productDiameterTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(orderDecimalQuantityTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(smallImageUrlTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(isVirtualTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(priceDetailTextTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productWeightTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(lastModifiedDateTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(shippingHeightTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(inventoryMessageTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(productNameTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(commentsTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(isVariantTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .add(createdByUserLoginTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap()));

        layoutPanel.setVerticalGroup(
                layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layoutPanel.createSequentialGroup()
                .addContainerGap()
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(depthUomIdLabel)
                .add(depthUomIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(returnableLabel)
                .add(returnableTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(productTypeIdLabel)
                .add(productTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(piecesIncludedLabel)
                .add(piecesIncludedTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(quantityUomIdLabel)
                .add(quantityUomIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(weightLabel)
                .add(weightTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(manufacturerPartyIdLabel)
                .add(manufacturerPartyIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(virtualVariantMethodEnumLabel)
                .add(virtualVariantMethodEnumTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(weightUomIdLabel)
                .add(weightUomIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(salesDiscontinuationDateLabel)
                .add(salesDiscontinuationDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(amountUomTypeIdLabel)
                .add(amountUomTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(chargeShippingLabel)
                .add(chargeShippingTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(defaultShipmentBoxTypeIdLabel)
                .add(defaultShipmentBoxTypeIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(includeInPromotionsLabel)
                .add(includeInPromotionsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(facilityIdLabel)
                .add(facilityIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(supportDiscontinuationDateLabel)
                .add(supportDiscontinuationDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(productIdLabel)
                .add(productIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(detailScreenLabel)
                .add(detailScreenTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(configIdLabel)
                .add(configIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(originalImageUrlLabel)
                .add(originalImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(heightUomIdLabel)
                .add(heightUomIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(introductionDateLabel)
                .add(introductionDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(diameterUomIdLabel)
                .add(diameterUomIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(releaseDateLabel)
                .add(releaseDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(widthUomIdLabel)
                .add(widthUomIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(requireAmountLabel)
                .add(requireAmountTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(originGeoIdLabel)
                .add(originGeoIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(productDepthLabel)
                .add(productDepthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(requirementMethodEnumIdLabel)
                .add(requirementMethodEnumIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(shippingDepthLabel)
                .add(shippingDepthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(primaryProductCategoryIdLabel)
                .add(primaryProductCategoryIdTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(largeImageUrlLabel)
                .add(largeImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(internalNameLabel)
                .add(internalNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(inShippingBoxLabel)
                .add(inShippingBoxTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(reservNthPPPercLabel)
                .add(reservNthPPPercTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(reserv2ndPPPercLabel)
                .add(reserv2ndPPPercTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(brandNameLabel)
                .add(brandNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(requireInventoryLabel)
                .add(requireInventoryTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(billOfMaterialLevelLabel)
                .add(billOfMaterialLevelTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(detailImageUrlLabel)
                .add(detailImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(productHeightLabel)
                .add(productHeightTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(productWidthLabel)
                .add(productWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(quantityIncludedLabel)
                .add(quantityIncludedTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(productDiameterLabel)
                .add(productDiameterTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(descriptionLabel)
                .add(descriptionTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(orderDecimalQuantityLabel)
                .add(orderDecimalQuantityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(longDescriptionLabel)
                .add(longDescriptionTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(smallImageUrlLabel)
                .add(smallImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(reservMaxPersonsLabel)
                .add(reservMaxPersonsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(isVirtualLabel)
                .add(isVirtualTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(mediumImageUrlLabel)
                .add(mediumImageUrlTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(priceDetailTextLabel)
                .add(priceDetailTextTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(createdDateLabel)
                .add(createdDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(productWeightLabel)
                .add(productWeightTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(autoCreateKeywordsLabel)
                .add(autoCreateKeywordsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(lastModifiedDateLabel)
                .add(lastModifiedDateTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(shippingWidthLabel)
                .add(shippingWidthTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(shippingHeightLabel)
                .add(shippingHeightTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(taxableLabel)
                .add(taxableTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(inventoryMessageLabel)
                .add(inventoryMessageTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(salesDiscWhenNotAvailLabel)
                .add(salesDiscWhenNotAvailTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(productNameLabel)
                .add(productNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(productRatingLabel)
                .add(productRatingTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(commentsLabel)
                .add(commentsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(lastModifiedByUserLoginLabel)
                .add(lastModifiedByUserLoginTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(isVariantLabel)
                .add(isVariantTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(ratingTypeEnumLabel)
                .add(ratingTypeEnumTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(createdByUserLoginLabel)
                .add(createdByUserLoginTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layoutPanel.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(fixedAmountLabel)
                .add(fixedAmountTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));

    }

    public void setUIFields() throws  java.text.ParseException {

        orderDecimalQuantityTextField.setText(OrderMaxUtility.getValidString(product.getorderDecimalQuantity()));

        detailScreenTextField.setText(OrderMaxUtility.getValidString(product.getdetailScreen()));

        descriptionTextField.setText(OrderMaxUtility.getValidString(product.getdescription()));

        quantityUomIdTextField.setText(OrderMaxUtility.getValidString(product.getquantityUomId()));

        reservMaxPersonsTextField.setText(OrderMaxUtility.getValidString(product.getreservMaxPersons()));

        productWidthTextField.setText(OrderMaxUtility.getValidString(product.getproductWidth()));

        amountUomTypeIdTextField.setText(OrderMaxUtility.getValidString(product.getamountUomTypeId()));

        facilityIdTextField.setText(OrderMaxUtility.getValidString(product.getfacilityId()));

        commentsTextField.setText(OrderMaxUtility.getValidString(product.getcomments()));

        longDescriptionTextField.setText(OrderMaxUtility.getValidString(product.getlongDescription()));

        widthUomIdTextField.setText(OrderMaxUtility.getValidString(product.getwidthUomId()));

        productDiameterTextField.setText(OrderMaxUtility.getValidString(product.getproductDiameter()));

        virtualVariantMethodEnumTextField.setText(OrderMaxUtility.getValidString(product.getvirtualVariantMethodEnum()));

        mediumImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getmediumImageUrl()));

        piecesIncludedTextField.setText(OrderMaxUtility.getValidLong(product.getpiecesIncluded()));

        requireAmountTextField.setText(OrderMaxUtility.getValidString(product.getrequireAmount()));

        lastModifiedByUserLoginTextField.setText(OrderMaxUtility.getValidString(product.getlastModifiedByUserLogin()));

        diameterUomIdTextField.setText(OrderMaxUtility.getValidString(product.getdiameterUomId()));

        includeInPromotionsTextField.setText(OrderMaxUtility.getValidString(product.getincludeInPromotions()));

        lastModifiedDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getlastModifiedDate()));

        brandNameTextField.setText(OrderMaxUtility.getValidString(product.getbrandName()));

        quantityIncludedTextField.setText(OrderMaxUtility.getValidString(product.getquantityIncluded()));

        taxableTextField.setText(OrderMaxUtility.getValidString(product.gettaxable()));

        createdDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getcreatedDate()));

        shippingHeightTextField.setText(OrderMaxUtility.getValidString(product.getshippingHeight()));

        isVariantTextField.setText(OrderMaxUtility.getValidString(product.getisVariant()));

        shippingWidthTextField.setText(OrderMaxUtility.getValidString(product.getshippingWidth()));

        introductionDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getintroductionDate()));

        shippingDepthTextField.setText(OrderMaxUtility.getValidString(product.getshippingDepth()));

        createdByUserLoginTextField.setText(OrderMaxUtility.getValidString(product.getcreatedByUserLogin()));

        weightTextField.setText(OrderMaxUtility.getValidString(product.getweight()));

        requireInventoryTextField.setText(OrderMaxUtility.getValidString(product.getrequireInventory()));

        heightUomIdTextField.setText(OrderMaxUtility.getValidString(product.getheightUomId()));

        productDepthTextField.setText(OrderMaxUtility.getValidString(product.getproductDepth()));

        reservNthPPPercTextField.setText(OrderMaxUtility.getValidString(product.getreservNthPPPerc()));

        ratingTypeEnumTextField.setText(OrderMaxUtility.getValidString(product.getratingTypeEnum()));

        depthUomIdTextField.setText(OrderMaxUtility.getValidString(product.getdepthUomId()));

        productHeightTextField.setText(OrderMaxUtility.getValidBigDecimal(product.getproductHeight()));

        autoCreateKeywordsTextField.setText(OrderMaxUtility.getValidString(product.getautoCreateKeywords()));

        internalNameTextField.setText(OrderMaxUtility.getValidString(product.getinternalName()));

        productWeightTextField.setText(OrderMaxUtility.getValidString(product.getproductWeight()));

        salesDiscWhenNotAvailTextField.setText(OrderMaxUtility.getValidString(product.getsalesDiscWhenNotAvail()));

        primaryProductCategoryIdTextField.setText(OrderMaxUtility.getValidString(product.getprimaryProductCategoryId()));

//        billOfMaterialLevelTextField.setText(OrderMaxUtility.getValidString(product.getbillOfMaterialLevel()));

        salesDiscontinuationDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getsalesDiscontinuationDate()));

        supportDiscontinuationDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getsupportDiscontinuationDate()));

        productIdTextField.setText(OrderMaxUtility.getValidString(product.getproductId()));

        weightUomIdTextField.setText(OrderMaxUtility.getValidString(product.getweightUomId()));

        reserv2ndPPPercTextField.setText(OrderMaxUtility.getValidString(product.getreserv2ndPPPerc()));

        defaultShipmentBoxTypeIdTextField.setText(OrderMaxUtility.getValidString(product.getdefaultShipmentBoxTypeId()));

        chargeShippingTextField.setText(OrderMaxUtility.getValidString(product.getchargeShipping()));

        detailImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getdetailImageUrl()));

        configIdTextField.setText(OrderMaxUtility.getValidString(product.getconfigId()));

        requirementMethodEnumIdTextField.setText(OrderMaxUtility.getValidString(product.getrequirementMethodEnumId()));

        inShippingBoxTextField.setText(OrderMaxUtility.getValidString(product.getinShippingBox()));

        productRatingTextField.setText(OrderMaxUtility.getValidString(product.getproductRating()));

        originalImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getoriginalImageUrl()));

        largeImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getlargeImageUrl()));

        productNameTextField.setText(OrderMaxUtility.getValidString(product.getproductName()));

        releaseDateTextField.setText(OrderMaxUtility.getValidTimestamp(product.getreleaseDate()));

        fixedAmountTextField.setText(OrderMaxUtility.getValidString(product.getfixedAmount()));

        priceDetailTextTextField.setText(OrderMaxUtility.getValidString(product.getpriceDetailText()));

        originGeoIdTextField.setText(OrderMaxUtility.getValidString(product.getoriginGeoId()));

        inventoryMessageTextField.setText(OrderMaxUtility.getValidString(product.getinventoryMessage()));

        isVirtualTextField.setText(OrderMaxUtility.getValidString(product.getisVirtual()));

        smallImageUrlTextField.setText(OrderMaxUtility.getValidString(product.getsmallImageUrl()));

        manufacturerPartyIdTextField.setText(OrderMaxUtility.getValidString(product.getmanufacturerPartyId()));

        productTypeIdTextField.setText(OrderMaxUtility.getValidString(product.getproductTypeId()));

        returnableTextField.setText(OrderMaxUtility.getValidString(product.getreturnable()));

    }

    public void getUIFields() throws  java.text.ParseException {

        product.setorderDecimalQuantity(OrderMaxUtility.getValidString(orderDecimalQuantityTextField.getText()));

        product.setdetailScreen(OrderMaxUtility.getValidString(detailScreenTextField.getText()));

        product.setdescription(OrderMaxUtility.getValidString(descriptionTextField.getText()));

        product.setquantityUomId(OrderMaxUtility.getValidString(quantityUomIdTextField.getText()));

        product.setreservMaxPersons(OrderMaxUtility.getValidBigDecimal(reservMaxPersonsTextField.getText()));

//        product.setproductWidth(OrderMaxUtility.getValidString(productWidthTextField.getText()));

        product.setamountUomTypeId(OrderMaxUtility.getValidString(amountUomTypeIdTextField.getText()));

        product.setfacilityId(OrderMaxUtility.getValidString(facilityIdTextField.getText()));

        product.setcomments(OrderMaxUtility.getValidString(commentsTextField.getText()));

        product.setlongDescription(OrderMaxUtility.getValidString(longDescriptionTextField.getText()));

        product.setwidthUomId(OrderMaxUtility.getValidString(widthUomIdTextField.getText()));

//        product.setproductDiameter(OrderMaxUtility.getValidString(productDiameterTextField.getText()));

        product.setvirtualVariantMethodEnum(OrderMaxUtility.getValidString(virtualVariantMethodEnumTextField.getText()));

        product.setmediumImageUrl(OrderMaxUtility.getValidString(mediumImageUrlTextField.getText()));

//        product.setpiecesIncluded(OrderMaxUtility.getValidString(piecesIncludedTextField.getText()));

        product.setrequireAmount(OrderMaxUtility.getValidString(requireAmountTextField.getText()));

        product.setlastModifiedByUserLogin(OrderMaxUtility.getValidString(lastModifiedByUserLoginTextField.getText()));

        product.setdiameterUomId(OrderMaxUtility.getValidString(diameterUomIdTextField.getText()));

        product.setincludeInPromotions(OrderMaxUtility.getValidString(includeInPromotionsTextField.getText()));

        product.setlastModifiedDate(OrderMaxUtility.getValidTimestamp(lastModifiedDateTextField.getText()));

        product.setbrandName(OrderMaxUtility.getValidString(brandNameTextField.getText()));

//        product.setquantityIncluded(OrderMaxUtility.getValidString(quantityIncludedTextField.getText()));

        product.settaxable(OrderMaxUtility.getValidString(taxableTextField.getText()));

        product.setcreatedDate(OrderMaxUtility.getValidTimestamp(createdDateTextField.getText()));

//        product.setshippingHeight(OrderMaxUtility.getValidString(shippingHeightTextField.getText()));

        product.setisVariant(OrderMaxUtility.getValidString(isVariantTextField.getText()));

//        product.setshippingWidth(OrderMaxUtility.getValidString(shippingWidthTextField.getText()));

        product.setintroductionDate(OrderMaxUtility.getValidTimestamp(introductionDateTextField.getText()));

//        product.setshippingDepth(OrderMaxUtility.getValidString(shippingDepthTextField.getText()));

        product.setcreatedByUserLogin(OrderMaxUtility.getValidString(createdByUserLoginTextField.getText()));

        product.setweight(OrderMaxUtility.getValidBigDecimal(weightTextField.getText()));

        product.setrequireInventory(OrderMaxUtility.getValidString(requireInventoryTextField.getText()));

        product.setheightUomId(OrderMaxUtility.getValidString(heightUomIdTextField.getText()));

//        product.setproductDepth(OrderMaxUtility.getValidString(productDepthTextField.getText()));

        product.setreservNthPPPerc(OrderMaxUtility.getValidBigDecimal(reservNthPPPercTextField.getText()));

        product.setratingTypeEnum(OrderMaxUtility.getValidString(ratingTypeEnumTextField.getText()));

        product.setdepthUomId(OrderMaxUtility.getValidString(depthUomIdTextField.getText()));

        product.setproductHeight(OrderMaxUtility.getValidBigDecimal(productHeightTextField.getText()));

        product.setautoCreateKeywords(OrderMaxUtility.getValidString(autoCreateKeywordsTextField.getText()));

        product.setinternalName(OrderMaxUtility.getValidString(internalNameTextField.getText()));

//        product.setproductWeight(OrderMaxUtility.getValidString(productWeightTextField.getText()));

        product.setsalesDiscWhenNotAvail(OrderMaxUtility.getValidString(salesDiscWhenNotAvailTextField.getText()));

        product.setprimaryProductCategoryId(OrderMaxUtility.getValidString(primaryProductCategoryIdTextField.getText()));

//        product.setbillOfMaterialLevel(OrderMaxUtility.getValidString(billOfMaterialLevelTextField.getText()));

        product.setsalesDiscontinuationDate(OrderMaxUtility.getValidTimestamp(salesDiscontinuationDateTextField.getText()));

        product.setsupportDiscontinuationDate(OrderMaxUtility.getValidTimestamp(supportDiscontinuationDateTextField.getText()));

        product.setproductId(OrderMaxUtility.getValidString(productIdTextField.getText()));

        product.setweightUomId(OrderMaxUtility.getValidString(weightUomIdTextField.getText()));

        product.setreserv2ndPPPerc(OrderMaxUtility.getValidBigDecimal(reserv2ndPPPercTextField.getText()));

        product.setdefaultShipmentBoxTypeId(OrderMaxUtility.getValidString(defaultShipmentBoxTypeIdTextField.getText()));

        product.setchargeShipping(OrderMaxUtility.getValidString(chargeShippingTextField.getText()));

        product.setdetailImageUrl(OrderMaxUtility.getValidString(detailImageUrlTextField.getText()));

        product.setconfigId(OrderMaxUtility.getValidString(configIdTextField.getText()));

        product.setrequirementMethodEnumId(OrderMaxUtility.getValidString(requirementMethodEnumIdTextField.getText()));

        product.setinShippingBox(OrderMaxUtility.getValidString(inShippingBoxTextField.getText()));

//        product.setproductRating(OrderMaxUtility.getValidString(productRatingTextField.getText()));

        product.setoriginalImageUrl(OrderMaxUtility.getValidString(originalImageUrlTextField.getText()));

        product.setlargeImageUrl(OrderMaxUtility.getValidString(largeImageUrlTextField.getText()));

        product.setproductName(OrderMaxUtility.getValidString(productNameTextField.getText()));

        product.setreleaseDate(OrderMaxUtility.getValidTimestamp(releaseDateTextField.getText()));

        product.setfixedAmount(OrderMaxUtility.getValidBigDecimal(fixedAmountTextField.getText()));

        product.setpriceDetailText(OrderMaxUtility.getValidString(priceDetailTextTextField.getText()));

        product.setoriginGeoId(OrderMaxUtility.getValidString(originGeoIdTextField.getText()));

        product.setinventoryMessage(OrderMaxUtility.getValidString(inventoryMessageTextField.getText()));

        product.setisVirtual(OrderMaxUtility.getValidString(isVirtualTextField.getText()));

        product.setsmallImageUrl(OrderMaxUtility.getValidString(smallImageUrlTextField.getText()));

        product.setmanufacturerPartyId(OrderMaxUtility.getValidString(manufacturerPartyIdTextField.getText()));

        product.setproductTypeId(OrderMaxUtility.getValidString(productTypeIdTextField.getText()));

        product.setreturnable(OrderMaxUtility.getValidString(returnableTextField.getText()));

    }

    public static void createAndShowUI(Product val) {

        try {

            ProductPanel panel = new ProductPanel(val);

            JFrame frame = new JFrame("Test Gui");

            frame.getContentPane().add(panel);

            panel.setUIFields();

            frame.pack();

            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

        } catch (java.text.ParseException ex) {

            Debug.logError(ex, module);

        }

    }

    @Override
    public GenericValueObjectInterface createUIObject(GenericValue baseVal) {

        Product newObj = null;

        if (baseVal != null) {

            newObj = new Product(baseVal);

            try {

                newObj.setGenericValue();

            } catch (Exception ex) {

                Debug.logError(ex, module);

            }

        } else {

            newObj = new Product();

        }

        return newObj;

    }

    public GenericValueObjectInterface getUIObject() {

        return product;

    }

    @Override
    public void changeUIObject(GenericValueObjectInterface uiObject) {

        if (uiObject instanceof Product) {

            Product newObj = (Product) uiObject;

            product = newObj;

            try {

                product.setGenericValue();

            } catch (Exception ex) {
//Debug.logError (ex, module);
            }

        }

    }

    public JPanel getContainerPanel() {
        return this;
    }

    protected class TextChangeListner implements DocumentListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
        }

        public void changedUpdate(DocumentEvent e) {

            warn(e);

        }

        public void removeUpdate(DocumentEvent e) {

            warn(e);

        }

        public void insertUpdate(DocumentEvent e) {

            warn(e);

        }

        void warn(DocumentEvent e) {

            isModified = true;

        }
    }
}
//calling function copy and paste

/*

 try {



 Delegator delegator = XuiContainer.getSession().getDelegator();

 List<GenericValue> genValList = PosProductHelper.getGenericValueLists("Product", delegator);

 GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(this, false, XuiContainer.getSession(),Product.ColumnNameId);

 dlg.setupOrderTableList(genValList);

 GenericValue val = genValList.get(0);

 GenericValuePanelInterfaceOrderMax panel = new org.ofbiz.ordermax.utility.ProductPanel( ); 

 Object uiObj = panel.createUIObject(val);

 panel.changeUIObject(uiObj);

 panel.setUIFields();

 dlg.setChildPanelInterface(panel);

 OrderMaxUtility.addAPanelToPanel(panel.getContainerPanel(), dlg.getParentPanel());

 dlg.setLocationRelativeTo(null);

 dlg.pack();

 dlg.setVisible(true);

 } catch (ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 } catch (java.text.ParseException ex) {

 Logger.getLogger(OrderMaxMainForm.class.getName()).log(Level.SEVERE, null, ex);

 }

 */
