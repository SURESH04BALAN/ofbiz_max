/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.orderbase.YesNoConditionSelectSingleton;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.report.reports.EntityJasperReport;

/**
 *
 * @author siranjeev
 */
public class JGenericComboBoxSelectionModel<E> implements ReportCreatorSelectionInterface {

    public ListAdapterListModel<E> dataListModel = new ListAdapterListModel<E>();
    public ListComboBoxModel<E> comboBoxModel = new ListComboBoxModel<E>();
    final public ListSelectionModel selectionModel = new DefaultListSelectionModel();
//    final public ListModelSelection<E> listModelSelection = new ListModelSelection<E>();
    public JComboBox<E> jComboBox = new JComboBox<E>(comboBoxModel);
    public AutoCompletion autoCompletion = null;
    public String keyId = "";
    public String paramId = "";
    public JPanel holdingPanel = null;

    public JGenericComboBoxSelectionModel(ListComboBoxModel<E> comboBoxModel, JComboBox<E> jComboBox, List<E> values) {
        dataListModel.addAll(values);
        this.comboBoxModel = comboBoxModel;
        this.jComboBox = jComboBox;
        this.comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer());
        autoCompletion = AutoCompletion.enable(this.jComboBox);
    }

    public JGenericComboBoxSelectionModel(List<E> values) {
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer());
        autoCompletion = AutoCompletion.enable(jComboBox);
    }

    public JGenericComboBoxSelectionModel(JPanel panel, List<E> values) {
        holdingPanel = panel;
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer());
        autoCompletion = AutoCompletion.enable(jComboBox);
        holdingPanel.setLayout(new BorderLayout());
        holdingPanel.add(jComboBox, BorderLayout.CENTER);
    }
   public JGenericComboBoxSelectionModel(JPanel panel, List<E> values, E defValue) {
        holdingPanel = panel;
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer());
        autoCompletion = AutoCompletion.enable(jComboBox);
        holdingPanel.setLayout(new BorderLayout());
        holdingPanel.add(jComboBox, BorderLayout.CENTER);
        if (defValue != null) {
            setSelectedItem(defValue);
        }
    }
   
    public JGenericComboBoxSelectionModel(JPanel panel, List<E> values, E defValue, String keyId) {
        holdingPanel = panel;
        this.keyId = keyId;
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer());
        autoCompletion = AutoCompletion.enable(jComboBox);
        holdingPanel.setLayout(new BorderLayout());
        holdingPanel.add(jComboBox, BorderLayout.CENTER);
        if (defValue != null) {
            setSelectedItem(defValue);
        }
    }

    public JGenericComboBoxSelectionModel(JPanel panel, List<E> values, DisplayNameInterface.DisplayTypes showKey) {
        holdingPanel = panel;
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer(showKey));
        autoCompletion = AutoCompletion.enable(jComboBox, showKey);
        holdingPanel.setLayout(new BorderLayout());
        holdingPanel.add(jComboBox, BorderLayout.CENTER);
    }

    public JGenericComboBoxSelectionModel(JPanel panel, List<E> values, ListCellRenderer<E> render) {
        holdingPanel = panel;
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //      listModelSelection.setListModels(dataListModel, selectionModel);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(render);
//        if (E instanceof DisplayNameInterface) {
        autoCompletion = AutoCompletion.enable(jComboBox);
        panel.setLayout(new BorderLayout());
        panel.add(jComboBox, BorderLayout.CENTER);
//        }
    }

    public JGenericComboBoxSelectionModel(List<E> values, String keyId) {
        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        this.keyId = keyId;
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer());
        autoCompletion = AutoCompletion.enable(jComboBox);
    }

    public JGenericComboBoxSelectionModel(JPanel panel) {
        holdingPanel = panel;
        dataListModel.addAll(new ArrayList<E>());
//        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //      listModelSelection.setListModels(dataListModel, selectionModel);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer());
//        if (E instanceof DisplayNameInterface) {
        autoCompletion = AutoCompletion.enable(jComboBox);
        panel.setLayout(new BorderLayout());
        panel.add(jComboBox, BorderLayout.CENTER);
//        }
    }

    public JGenericComboBoxSelectionModel(JPanel panel, DisplayNameInterface.DisplayTypes showKey) {
        holdingPanel = panel;
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer(showKey));
        autoCompletion = AutoCompletion.enable(jComboBox, showKey);
        holdingPanel.setLayout(new BorderLayout());
        holdingPanel.add(jComboBox, BorderLayout.CENTER);
    }

    public JGenericComboBoxSelectionModel(ListCellRenderer<E> render) {
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //    listModelSelection.setListModels(dataListModel, selectionModel);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(render);
    }

    public JGenericComboBoxSelectionModel(DisplayNameInterface.DisplayTypes displayType) {
//        dataListModel.addAll(values);
        comboBoxModel.setListModel(dataListModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //      listModelSelection.setListModels(dataListModel, selectionModel);
        comboBoxModel.setListSelectionModel(selectionModel);
        jComboBox.setRenderer(new GenericListCellRenderer(displayType));
//        if (E instanceof DisplayNameInterface) {
        autoCompletion = AutoCompletion.enable(jComboBox);
//        }
    }

    static public <E> ReportParameterSelectionInterface createReportSelectionComboPanel(String keyId, EntityJasperReport reportObject, JPanel panel, List<E> values) {
        JPanel contPanel = new JPanel();
        JGenericComboBoxSelectionModel model = new JGenericComboBoxSelectionModel<E>(contPanel, values);
        model.keyId = keyId;
        reportObject.addAItem(contPanel, panel);
        return model;
    }

    public void setSelectedItem(E elem) {

        comboBoxModel.setSelectedItem(elem);
        jComboBox.repaint();
        /*        int index = comboBoxModel.getSelectedItemIndex(elem);
         if(index > -1){
         selectionModel.setLeadSelectionIndex(index);
         }*/
    }

    public E getSelectedItem() {
        E selItem = null;
        if (comboBoxModel.getSelectedItem() != null) {
            selItem = (E) comboBoxModel.getSelectedItem();
        }
        return selItem;
    }

    public void addItem(E val) {
        List<E> valList = new ArrayList();
        valList.add(val);
        valList.addAll(dataListModel.getList());
        setDataList(valList);
    }

    @Override
    public EntityCondition getEntityCondition() {
        Debug.logInfo("getEntityCondition: " + keyId, keyId);
        if (getSelectedItem() != null) {
            E e = getSelectedItem();
            Debug.logInfo("getEntityCondition getSelectedItem: " + e.toString(), keyId);
            if (e instanceof org.ofbiz.ordermax.generic.GenericValueObjectInterface) {
                org.ofbiz.ordermax.generic.GenericValueObjectInterface gvoi = (org.ofbiz.ordermax.generic.GenericValueObjectInterface) e;
                if (gvoi.getGenericValueObj() != null) {
                    return EntityCondition.makeCondition(UtilValidate.isNotEmpty(paramId) ? paramId : keyId, EntityOperator.EQUALS, gvoi.getGenericValueObj().getString(keyId));
                }
            } else if (e instanceof String) {
                String val = (String) e;
                if (val != null) {
                    try {
                        String str = YesNoConditionSelectSingleton.getKeyFromDisplayName(val);
                        return EntityCondition.makeCondition(UtilValidate.isNotEmpty(paramId) ? paramId : keyId, EntityOperator.EQUALS, str);                        
                    } catch (Exception ex) {
                        Logger.getLogger(JGenericComboBoxSelectionModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return null;

    }

    @Override
    public String getEntityId() {
        return keyId;
    }

    @Override
    public Object getEntityValue() {
        if (getSelectedItem() != null) {
            E e = getSelectedItem();
            if (e instanceof org.ofbiz.ordermax.generic.GenericValueObjectInterface) {
                org.ofbiz.ordermax.generic.GenericValueObjectInterface gvoi = (org.ofbiz.ordermax.generic.GenericValueObjectInterface) e;
                if (gvoi.getGenericValueObj() != null) {
                    return gvoi.getGenericValueObj().getString(keyId);
                }
            }
        }
        return null;
    }

    @Override
    public void getValueMap(Map<String, Object> valueMap) {
        String val = (String) getEntityValue();
        Debug.logInfo("Key Id: " + keyId + " val: " + val, "module");
        if (val != null) {
            valueMap.put(UtilValidate.isNotEmpty(paramId) ? paramId : keyId, val);
        }
    }

    @Override
    public Component getComponent() {
        return jComboBox;
    }

    @Override
    public void setEntityValue(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class GenericListCellRenderer extends JLabel implements
            ListCellRenderer<E> {

        /**
         *
         */
        private static final long serialVersionUID = -1614367901813214864L;
        DisplayNameInterface.DisplayTypes showKey;

        public GenericListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
            super();
            setOpaque(true);
            this.showKey = showKey;
        }

        public GenericListCellRenderer() {
            super();
            setOpaque(true);
            this.showKey = XuiContainer.getSession().getComboBoxDisplayFormat();
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list,
                E value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
//        if (-1 < index) {
//          list.setToolTipText(tooltips[index]);
//        }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            if (value instanceof DisplayNameInterface) {
                String toString = toString((DisplayNameInterface) value);
                setText(toString);
            } else if (value instanceof String) {
                String toString = (String) value;
                setText(toString);
            } else if (value.toString() != null) {
//                String toString = (String) value;
                setText(value.toString());
            }
            return this;
        }

        private String toString(DisplayNameInterface obj) {
            if (obj == null) {
                return "Doesn't have DisplayNameInterface";
            } else {
                return obj.getdisplayName(showKey);
            }
        }

    }

    public void setDataList(List<E> values) {
        selectionModel.clearSelection();
        dataListModel.clear();
        dataListModel.addAll(values);
        jComboBox.repaint();
    }
}
