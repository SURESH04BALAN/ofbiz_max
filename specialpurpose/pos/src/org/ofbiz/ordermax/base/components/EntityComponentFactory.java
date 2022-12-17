/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import com.openbravo.pos.reports.params.JParamFormGeneric;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PartyNameView;
import org.ofbiz.ordermax.entity.PartyType;
import org.ofbiz.ordermax.entity.RoleType;
import org.ofbiz.ordermax.orderbase.ConditionSelectSingleton;
import org.ofbiz.ordermax.payment.RoleTypeSingleton;
import org.ofbiz.ordermax.product.PartyTypeSingleton;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;

/**
 *
 * @author BBS Auctions
 */
public class EntityComponentFactory {

    static int defaultX = 10;
    static int defaultY = 10;
    static int defaultW = 150;
    static int defaultH = 50;

    static public ReportCreatorSelectionInterface createControl(
            List<ReportParameterSelectionInterface> filterList,
            LookupActionListnerInterface.LookupType componentType,
            ControllerOptions controllerOptions,
            JParamFormGeneric form,
            String name) throws Exception {

        ReportCreatorSelectionInterface basePickerEditPanel = createControl(componentType, controllerOptions);

        createPanel(filterList, form, name, basePickerEditPanel);
        /*JPanel panel = new JPanel();
         panel.setMinimumSize(new Dimension(50, 25));
         panel.setPreferredSize(new Dimension(500, 25));
         panel.setMaximumSize(new Dimension(500, Short.MAX_VALUE));
         //panel.setPreferredSize(new Dimension(550, 24));
         panel.setLayout(new BorderLayout());
         JLabel label = new JLabel(name);
         label.setPreferredSize(new Dimension(150, 24));
         panel.add(BorderLayout.WEST, label);
         panel.add(BorderLayout.CENTER, basePickerEditPanel.getComponent());

         form.add(panel);
         filterList.add(basePickerEditPanel);*/
        return basePickerEditPanel;
    }

    public static <T> ReportCreatorSelectionInterface createControl(
            List<ReportParameterSelectionInterface> filterList,
            LookupActionListnerInterface.LookupType componentType,
            ControllerOptions controllerOptions,
            JParamFormGeneric form,
            String name,
            List<T> values, T defValue, String keyId) throws Exception {
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(50, 25));
        panel.setPreferredSize(new Dimension(500, 25));
        panel.setMaximumSize(new Dimension(500, Short.MAX_VALUE));

        ReportCreatorSelectionInterface basePickerEditPanel = new JGenericComboBoxSelectionModel<T>(panel, values, defValue, keyId);

        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(name);
        label.setPreferredSize(new Dimension(150, 24));
        panel.add(BorderLayout.WEST, label);
        panel.add(BorderLayout.CENTER, basePickerEditPanel.getComponent());

        form.add(panel);
        filterList.add(basePickerEditPanel);
        return basePickerEditPanel;
    }

    static protected void createPanel(
            List<ReportParameterSelectionInterface> filterList,
            JParamFormGeneric form,
            String name, ReportCreatorSelectionInterface basePickerEditPanel) {

        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(50, 25));
        panel.setPreferredSize(new Dimension(500, 25));
        panel.setMaximumSize(new Dimension(500, Short.MAX_VALUE));
        //panel.setPreferredSize(new Dimension(550, 24));
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(name);
        label.setPreferredSize(new Dimension(150, 24));
        panel.add(BorderLayout.WEST, label);
        panel.add(BorderLayout.CENTER, basePickerEditPanel.getComponent());

        form.add(panel);
        filterList.add(basePickerEditPanel);
    }

    static public ReportCreatorSelectionInterface createControl(List<ReportParameterSelectionInterface> filterList, LookupActionListnerInterface.LookupType componentType, ControllerOptions controllerOptions, JPanel panel) throws Exception {
        ReportCreatorSelectionInterface basePickerEditPanel = createControl(componentType, controllerOptions);
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.CENTER, basePickerEditPanel.getComponent());
        filterList.add(basePickerEditPanel);
        return basePickerEditPanel;
    }

    static public ReportCreatorSelectionInterface createControl(LookupActionListnerInterface.LookupType componentType, ControllerOptions controllerOptions, JPanel panel) throws Exception {
        ReportCreatorSelectionInterface basePickerEditPanel = createControl(componentType, controllerOptions);
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.CENTER, basePickerEditPanel.getComponent());
        return basePickerEditPanel;
    }

    static public ReportCreatorSelectionInterface createControl(LookupActionListnerInterface.LookupType componentType, ControllerOptions controllerOptions) throws Exception {
        ReportCreatorSelectionInterface pickerPanel = null;
        switch (componentType) {
            case CountryGeoId:
                break;
            case StateProvinceGeoId:
                break;
            case PartyId:
                pickerPanel = createPartyIdPicker(componentType, controllerOptions);
                break;
            case ProductId:
                break;
            case OrderId:
                break;
            case PaymentId:
                break;
            case ReturnId:
                break;
            case InvoiceId:
                break;
            case CategoryId:
                controllerOptions.put("EntityName", "ProductCategory");
                controllerOptions.put("entityId", "productCategoryId");
                controllerOptions.put("entityIdDescription", "description");
                pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.CategoryId, controllerOptions);
                //  new CategoryPickerEditPanel(controllerOptions);
                break;
            case CatalogId:
                controllerOptions.put("EntityName", "ProdCatalog");
                controllerOptions.put("entityId", "prodCatalogId");
                controllerOptions.put("entityIdDescription", "catalogName");
                pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.CatalogId, controllerOptions);
                break;
            case FacilityId:
                controllerOptions.put("EntityName", "Facility");
                controllerOptions.put("entityId", "facilityId");
                controllerOptions.put("entityIdDescription", "facilityName");
                pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.FacilityId, controllerOptions);
                //pickerPanel = new FacilityIdEditPanel(controllerOptions);
                break;
            case ProductStoreId:
                controllerOptions.put("EntityName", "ProductStore");
                controllerOptions.put("entityId", "productStoreId");
                controllerOptions.put("entityIdDescription", "storeName");
                pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.ProductStoreId, controllerOptions);
                //   pickerPanel = new ProductStoreIdEditPanel(controllerOptions);
                break;
            case TerminalId:
                controllerOptions.put("EntityName", "PosTerminal");
                controllerOptions.put("entityId", "posTerminalId");
                controllerOptions.put("entityIdDescription", "terminalName");
                pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.TerminalId, controllerOptions);
                //pickerPanel = new PosTerminalIdEditPanel(controllerOptions);
                break;
            case ProdCatalogCategoryId:
                controllerOptions.put("EntityName", "ProdCatalogCategory");
                controllerOptions.put("entityId", "prodCatalogId");
                controllerOptions.put("entityIdDescription", "productCategoryId");
                pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.ProdCatalogCategoryId, controllerOptions);
                //pickerPanel = new ProdCatalogCategoryEditPanel(controllerOptions);
                break;
            case ProductStoreCatalogId:
                controllerOptions.put("EntityName", "ProductStoreCatalog");
                controllerOptions.put("entityId", "productStoreId");
                controllerOptions.put("entityIdDescription", "prodCatalogId");
                pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.ProductStoreCatalogId, controllerOptions);
//                pickerPanel = new ProductStoreCatalogIdEditPanel(controllerOptions);
                break;
            case TextFieldSelection:
                pickerPanel = new TextSelectionEditPanel(controllerOptions);
                break;

            case PartyTypeId:
                pickerPanel = createPartyTypeIdPicker(controllerOptions);
                break;
            case PartyRoleTypeId:
                pickerPanel = createRoleTypeIdPicker(controllerOptions);
                break;
            case SingleDateSelection:
                String keyId = "startDateId";
                if (controllerOptions.contains("keyId")) {
                    keyId = (String) controllerOptions.get("keyId");
                } else {
                    keyId = (String) controllerOptions.get("entityId");
                }
                pickerPanel = new FindSingleDateSelectionPanel(keyId);
                break;
            default:
                throw new Exception("Component not found: " + componentType);
        };
        return pickerPanel;
    }

    static protected BasePickerEditPanel createPartyIdPicker(LookupActionListnerInterface.LookupType componentType, ControllerOptions controllerOptions) {
        String keyId = "partyId";
        if (controllerOptions.contains("keyId")) {
            keyId = (String) controllerOptions.get("keyId");
        }
        controllerOptions.put("EntityName", "ProductStoreCatalog");
        controllerOptions.put("entityId", keyId);
        controllerOptions.put("entityIdDescription", "partyName");
        controllerOptions.put("editorId", "PartyIdTextField");

        BasePickerEditPanel pickerPanel = new EntityPickerEditPanel(LookupActionListnerInterface.LookupType.PartyId, controllerOptions) {
            @Override
            public void setGenericValue(GenericValue genericValue) {
                this.genericValue = genericValue;

                if (genericValue != null) {
                    PartyNameView partyNameView = new PartyNameView(genericValue);
                    String partyName = partyNameView.getdisplayName(DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY);
                    jLabel1.setText(partyName);
                }
            }
        };
        return pickerPanel;
    }

    static protected BasePickerComboPanel createPartyTypeIdPicker(ControllerOptions controllerOptions) {
        String keyId = "partyTypeId";
        if (controllerOptions.contains("keyId")) {
            keyId = (String) controllerOptions.get("keyId");
        }
        controllerOptions.put("EntityName", "PartyType");
        controllerOptions.put("entityId", keyId);

        //create party type and role type
        List<PartyType> findPartyList = PartyTypeSingleton.getValueList();
        PartyType partyType = new PartyType();
        partyType.setdescription("All");
        partyType.setpartyTypeId("ANY");
        findPartyList.add(0, partyType);
        ComboBoxSelectionPanel<PartyType> combo = new ComboBoxSelectionPanel<PartyType>(controllerOptions, findPartyList) {
            @Override
            public void setGenericValue(PartyType typeVal) {
                if (typeVal != null) {
                    jLabel1.setText(typeVal.getpartyTypeId());
                }
            }

            @Override
            public void setEntityValue(java.lang.Object value) {
                try {
                    PartyType partyType = PartyTypeSingleton.getPartyType((String) value);
                    model.setSelectedItem(partyType);
                } catch (Exception ex) {
                    Logger.getLogger(EntityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        return combo;
    }

    static protected BasePickerComboPanel createRoleTypeIdPicker(ControllerOptions controllerOptions) {
        String keyId = "roleTypeId";
        if (controllerOptions.contains("keyId")) {
            keyId = (String) controllerOptions.get("keyId");
        }
        controllerOptions.put("EntityName", "RoleType");
        controllerOptions.put("entityId", keyId);

        //create party type and role type
        List<RoleType> findPartyRoleList = RoleTypeSingleton.getValueList(controllerOptions.getRoleTypeParent());
        RoleType partyRole = new RoleType();
        partyRole.setdescription("All");
        partyRole.setroleTypeId("ANY");
        findPartyRoleList.add(0, partyRole);
        ComboBoxSelectionPanel<RoleType> combo = new ComboBoxSelectionPanel<RoleType>(controllerOptions, findPartyRoleList) {
            @Override
            public void setGenericValue(RoleType typeVal) {
                if (typeVal != null) {
                    jLabel1.setText(typeVal.getroleTypeId());
                }
            }

            @Override
            public void setEntityValue(java.lang.Object value) {
                try {
                    RoleType roleType = RoleTypeSingleton.getRoleType((String) value);
                    model.setSelectedItem(roleType);
                } catch (Exception ex) {
                    Logger.getLogger(EntityComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        return combo;
    }

    static public class EntityPickerEditPanel extends BasePickerEditPanel {

        static public String module = CategoryPickerEditPanel.class.getName();
        //public String keyId = "productCategoryId";
        // LookupType lookupType = null;

        /**
         * Creates new form DatePickerEditPanel
         */
        public EntityPickerEditPanel(LookupType lookupType, final ControllerOptions controllerOptions) {
            super(lookupType, controllerOptions);

            controllerOptions.put(getEditorId(), textIdField);
            //controllerOptions.put("EntityName", "ProductCategory");
            entityId = (String) controllerOptions.get("entityId"); //"productCategoryId";
            entityIdDescription = (String) controllerOptions.get("entityIdDescription");
            // this.lookupType = lookupType;
            setDirtyManger(textIdField);
        }

        @Override
        public LookupType getLookupType() {
            return lookupType;//LookupType.CategoryId;
        }

        @Override
        public Component getComponent() {
            return this;
        }
    }

    static public class TextSelectionEditPanel extends FindSelectionEditPanel {

        static public String module = CatalogCategoryPickerEditPanel.class.getName();

        public TextSelectionEditPanel(final ControllerOptions controllerOptions) {
            super(controllerOptions, (String) controllerOptions.get("entityId"), ConditionSelectSingleton.EQUALS);

        }

    }

    static public class ComboBoxSelectionPanel<E> extends BasePickerComboPanel {

        static public String module = ComboBoxSelectionPanel.class.getName();
        JGenericComboBoxSelectionModel<E> model = null;

        public ComboBoxSelectionPanel(final ControllerOptions controllerOptions, List<E> values) {
            super(controllerOptions);
            model = new JGenericComboBoxSelectionModel<E>(this, values);
            model.keyId = (String) controllerOptions.get("entityId");
            model.setSelectedItem((E) controllerOptions.get("defaultValue"));
        }

        public void setGenericValue(E genericValue) {
        }

        @Override
        public Component getComponent() {
            return this;
        }

        @Override
        public String getEntityId() {
            return model.keyId;
        }

        @Override
        public Object getEntityValue() {
            return model.getEntityValue();
        }

        @Override
        public void getValueMap(Map<String, Object> valueMap) {
            model.getValueMap(valueMap);
        }

        @Override
        public EntityCondition getEntityCondition() {
            return model.getEntityCondition();
        }

        @Override
        public void setEntityValue(java.lang.Object value) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
