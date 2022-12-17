/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.list;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.composite.PartyContactMechComposite;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeComposite;
import org.ofbiz.ordermax.composite.PartyContactMechPurposeList;
import org.ofbiz.ordermax.entity.PostalAddress;

/**
 * A {@link ListCellRenderer} that uses an {@link ObjectToString} to get a
 * string representation for the model objects it renders.
 *
 * @author RenÃ© Link <a
 * href="mailto:rene.link@link-intersystems.com">[rene.link@link-
 * intersystems.com]</a>
 *
 * @param <T>
 */
public class PartyContactMechCompositeListCellRenderer extends JLabel implements
        ListCellRenderer<PartyContactMechComposite> {

    /**
     *
     */
    private static final long serialVersionUID = -161436790181321447L;

    @Override
    public Component getListCellRendererComponent(JList<? extends PartyContactMechComposite> list,
            PartyContactMechComposite value, int index, boolean isSelected, boolean cellHasFocus) {

        setFont(list.getFont());

        String toString = toString(value);
        setText(toString);
        if (isSelected) {
//            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setBackground(list.getBackground());
            setForeground(Color.BLUE);
//        if (-1 < index) {
//          list.setToolTipText(tooltips[index]);
//        }
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }

    private String toString(PartyContactMechComposite partyContactMechComposite) {

        if (partyContactMechComposite == null) {
            return "null";
        }

        StringBuilder orderToStringBuilder = new StringBuilder();
//        orderToStringBuilder.append(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId());
        if ("POSTAL_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {
  //          orderToStringBuilder.append(partyContactMechComposite.getContact().getContactMech().getcontactMechId());
    //        orderToStringBuilder.append(" - ");
            if (partyContactMechComposite.getPartyContactMechPurposeList() != null) {
                PartyContactMechPurposeList mechPurposeList = partyContactMechComposite.getPartyContactMechPurposeList();
                for (PartyContactMechPurposeComposite purpose : mechPurposeList.getList()) {
                    orderToStringBuilder.append(purpose.getContactMechPurposeType().getdescription());

      //              orderToStringBuilder.append(" [");
      //              orderToStringBuilder.append(PostalAddressListCellRenderer.toString(partyContactMechComposite.getContact().getPostalAddress()));

      //              orderToStringBuilder.append(" ]");
                }
            }

        } else if ("TELECOM_NUMBER".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

            if (partyContactMechComposite.getPartyContactMechPurposeList() != null) {
                PartyContactMechPurposeList mechPurposeList = partyContactMechComposite.getPartyContactMechPurposeList();
                for (PartyContactMechPurposeComposite purpose : mechPurposeList.getList()) {
                    orderToStringBuilder.append(purpose.getContactMechPurposeType().getdescription());

      //              orderToStringBuilder.append(" [");
      //              orderToStringBuilder.append(PostalAddressListCellRenderer.toString(partyContactMechComposite.getContact().getPostalAddress()));

      //              orderToStringBuilder.append(" ]");
                }
            }

        } else if ("EMAIL_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

        } else if ("DOMAIN_NAME".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

        } else if ("ELECTRONIC_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

        } else if ("INTERNAL_PARTYID".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

        } else if ("IP_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

        } else if ("LDAP_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

        } else if ("WEB_ADDRESS".equals(partyContactMechComposite.getContact().getContactMech().getcontactMechTypeId())) {

        }

        return orderToStringBuilder.toString();
    }

}
