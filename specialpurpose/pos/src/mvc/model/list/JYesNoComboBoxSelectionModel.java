/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.ofbiz.ordermax.report.ReportParameterSelectionInterface;
import org.ofbiz.ordermax.report.reports.EntityJasperReport;

/**
 *
 * @author siranjeev
 */
public class JYesNoComboBoxSelectionModel extends JGenericComboBoxSelectionModel<String> {

    public static final String module = JYesNoComboBoxSelectionModel.class.getName();

    public JYesNoComboBoxSelectionModel(JPanel panel, DirtyManager dirty) {
        super(panel, YesNoConditionSelectSingleton.getValueList(),  new StringListCellRenderer(false));
        this.jComboBox.addActionListener(dirty);
    }

    public void setSelected(String value) {
        if (UtilValidate.isNotEmpty(value)) {
            try {
                String str = YesNoConditionSelectSingleton.getString(value);
                this.setSelectedItem(str);
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
        }
    }

    public String getSelected() {
        if (this.getSelectedItem() != null) {
            try {
                return YesNoConditionSelectSingleton.getKeyFromDisplayName(this.getSelectedItem());
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }
        }
        return null;
    }
}
