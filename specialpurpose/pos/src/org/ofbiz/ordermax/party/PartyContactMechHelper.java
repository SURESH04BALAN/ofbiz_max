/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.controller.LoadAccountWorker;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.Account;
import org.ofbiz.ordermax.composite.InvoiceContact;
import org.ofbiz.ordermax.composite.InvoiceContactList;
import org.ofbiz.ordermax.composite.OrderContact;
import org.ofbiz.ordermax.composite.OrderContactList;
import org.ofbiz.ordermax.composite.OrderContactMechComposite;
import org.ofbiz.ordermax.composite.OrderContactMechCompositeList;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.PostalAddress;
import org.ofbiz.ordermax.entity.TelecomNumber;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import static org.ofbiz.party.party.PartyServices.module;

/**
 *
 * @author siranjeev
 */
public class PartyContactMechHelper {

    static public String getInvoiceParmentDetails(InvoiceContactList contactList) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        //add all billing address        
        orderToStringBuilder.append(getPostalAddress(contactList, "PAYMENT_LOCATION"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "PHONE_PAYMENT"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "FAX_BILLING"));
        //PAYMENT_EMAIL
        return orderToStringBuilder.toString();
    }

    static public String getInvoiceBillingDetails(InvoiceContactList contactList) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        //add all billing address        
        orderToStringBuilder.append(getPostalAddress(contactList, "BILLING_LOCATION"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "PHONE_BILLING"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "FAX_BILLING"));
        return orderToStringBuilder.toString();
    }

    public static String getPostalAddress(InvoiceContactList contactList, String purposeTypeId) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        //add all billing address        
        for (InvoiceContact itr : contactList.getList()) {

            if (itr.getInvoiceContactMech().getcontactMechPurposeTypeId().equals(purposeTypeId)) {
                try {
                    orderToStringBuilder.append(getAddresHtmlString(itr.getContact().getPostalAddress(),
                            ContactMechPurposeTypeSingleton.getContactMechPurposeType(purposeTypeId).getdescription()));
                } catch (Exception ex) {
                    Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return orderToStringBuilder.toString();
    }

    public static String getTelecomNumbers(InvoiceContactList contactList, String purposeTypeId) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        //add all billing address        
        for (InvoiceContact itr : contactList.getList()) {

            if (itr.getInvoiceContactMech().getcontactMechPurposeTypeId().equals(purposeTypeId)) {
                try {
                    orderToStringBuilder.append(getPhoneHtmlString(itr.getContact().getTelecomNumber(),
                            ContactMechPurposeTypeSingleton.getContactMechPurposeType(purposeTypeId).getdescription()));
                } catch (Exception ex) {
                    Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return orderToStringBuilder.toString();
    }

    static public String getOrderShippingDetails(Map<String, String> contactList) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        //add all billing address        
        orderToStringBuilder.append(getPostalAddress(contactList, "SHIPPING_LOCATION"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "PHONE_SHIPPING"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "FAX_SHIPPING"));
        return orderToStringBuilder.toString();
    }

    static public String getOrderBillingDetails(Map<String, String> contactList) {
        StringBuilder orderToStringBuilder = new StringBuilder();

        //add all billing address        
        orderToStringBuilder.append(getPostalAddress(contactList, "BILLING_LOCATION"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "PHONE_BILLING"));
        orderToStringBuilder.append(getTelecomNumbers(contactList, "FAX_BILLING"));
//        orderToStringBuilder.append(getTelecomNumbers(contactList, "BILLING_EMAIL"));                     

        return orderToStringBuilder.toString();
    }

    public static String getPostalAddress(Map<String, String> contactList, String purposeTypeId) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (contactList.containsKey(purposeTypeId)) {
            PostalAddress postalAddress = LoadAccountWorker.getPostalAddress(contactList.get(purposeTypeId));
            try {
                orderToStringBuilder.append(getAddresHtmlString(postalAddress,
                        ContactMechPurposeTypeSingleton.getContactMechPurposeType(purposeTypeId).getdescription()));
            } catch (Exception ex) {
                Logger.getLogger(PartyContactMechHelper.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        /*
         //add all billing address        
         for (OrderContactMechComposite itr : contactList.getList()) {

         if (itr.getOrderContactMech().getcontactMechPurposeTypeId().equals(purposeTypeId)) {
         try {
         orderToStringBuilder.append(getAddresHtmlString(itr.getContact().getPostalAddress(),
         ContactMechPurposeTypeSingleton.getContactMechPurposeType(purposeTypeId).getdescription()));
         } catch (Exception ex) {
         Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         }*/
        return orderToStringBuilder.toString();
    }

    public static String getTelecomNumbers(Map<String, String> contactList, String purposeTypeId) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        if (contactList.containsKey(purposeTypeId)) {
            TelecomNumber telecomNumber = LoadAccountWorker.getTelecomNumber(contactList.get(purposeTypeId));

            try {
                orderToStringBuilder.append(getPhoneHtmlString(telecomNumber,
                            ContactMechPurposeTypeSingleton.getContactMechPurposeType(purposeTypeId).getdescription()));            } catch (Exception ex) {
                Logger.getLogger(PartyContactMechHelper.class.getName()).log(Level.SEVERE, null, ex);
            }

        }        
/*
        //add all billing address        
        for (OrderContactMechComposite itr : contactList.getList()) {

            if (itr.getOrderContactMech().getcontactMechPurposeTypeId().equals(purposeTypeId)) {
                try {
                    orderToStringBuilder.append(getPhoneHtmlString(itr.getContact().getTelecomNumber(),
                            ContactMechPurposeTypeSingleton.getContactMechPurposeType(purposeTypeId).getdescription()));
                } catch (Exception ex) {
                    Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }*/
        return orderToStringBuilder.toString();
    }

    static public String getPartyHtmlString(Account account) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        String rowHeader = "<tr>\n"
                + "          <td align=\"right\" valign=\"top\" width=\"19%\"><font size=\"5\" face=\"Georgia, Arial\" color=\"black\"><span class=\"label\">&nbsp;Name</span></td>\n"
                + "          <td width=\"1%\">&nbsp;</td>\n"
                + "          <td valign=\"top\" width=\"80%\">\n"
                + "            <div>"
                + "             <font size=\"5\" face=\"Georgia, Arial\" color=\"black\">\n";

        String rowFooter = " </font></div>\n"
                + "          </td>\n"
                + "        </tr>\n";

        orderToStringBuilder.append(rowHeader);
        orderToStringBuilder.append(account.getDisplayName() + "\n");

        orderToStringBuilder.append("                &nbsp;(<a href=\"https://localhost:8443/partymgr/control/viewprofile?partyId=INDPAS&amp;amp;externalLoginKey=EL212892928556\" target=\"partymgr\" class=\"buttontext\">");
        orderToStringBuilder.append(account.getParty().getpartyId());
        orderToStringBuilder.append("</a>)\n");
        orderToStringBuilder.append("                <br>\n");
        orderToStringBuilder.append(rowFooter);

        return orderToStringBuilder.toString();
    }

    static public String getDisplayHtmlString(String name, DisplayNameInterface dispalyInter) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        String rowHeader = "<tr>\n"
                + "          <td align=\"right\" valign=\"top\" width=\"19%\"><font size=\"5\" face=\"Georgia, Arial\" color=\"black\"><span class=\"label\">&nbsp;" + name + "</span></td>\n"
                + "          <td width=\"1%\">&nbsp;</td>\n"
                + "          <td valign=\"top\" width=\"80%\">\n"
                + "            <div>"
                + "             <font size=\"5\" face=\"Georgia, Arial\" color=\"black\">\n";

        String rowFooter = " </font></div>\n"
                + "          </td>\n"
                + "        </tr>\n";

        orderToStringBuilder.append(rowHeader);
        orderToStringBuilder.append(dispalyInter.getdisplayName(DisplayNameInterface.DisplayTypes.SHOW_NAME_ONLY) + " ");

        orderToStringBuilder.append("                &nbsp;(<a href=\"https://localhost:8443/partymgr/control/viewprofile?partyId=INDPAS&amp;amp;externalLoginKey=EL212892928556\" target=\"partymgr\" class=\"buttontext\">");
        orderToStringBuilder.append(dispalyInter.getdisplayName(DisplayNameInterface.DisplayTypes.SHOW_CODE_ONLY));
        orderToStringBuilder.append("</a>)\n");
        orderToStringBuilder.append("                <br>\n");
        orderToStringBuilder.append(rowFooter);

        return orderToStringBuilder.toString();
    }

    static public String getAddresHtmlString(PostalAddress postalAddress, String purpose) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        String rowHeader1 = "          <tr><td colspan=\"5\"><hr></td></tr>\n"
                + "          <tr>\n"
                + "            <td align=\"right\" valign=\"top\" width=\"19%\">\n"
                + "              <font size=\"5\" face=\"Georgia, Arial\" color=\"black\"> <span class=\"label\">&nbsp;";

        String rowHeader2 = "</span>\n"
                + "            </td>\n"
                + "            <td width=\"1%\">&nbsp;</td>\n"
                + "            <td valign=\"top\" width=\"80%\">\n"
                + "                  <div>\n"
                //            + "                     \n"
                + "                     <div><font size=\"5\" face=\"Georgia, Arial\" color=\"black\">\n";
//            + "                         \n"
  /*
         + "    <b>Attn:</b> NARENDER<br>\n"
         + "    235  DARLING STREET<br>\n"
         + "    \n"
         + "    BALMAIN NSW,  NSW\n"
         + "    2041\n"
         + "    <br>\n"
         + "      Australia\n"
         */

        String rowFooter = "    </div>\n"
                + "\n"
                + "\n"
                + "                  </div>\n"
                + "            </td>\n"
                + "          </tr>\n";

        orderToStringBuilder.append(rowHeader1.concat(purpose).concat(rowHeader2));
//        orderToStringBuilder.append(purpose);
//        orderToStringBuilder.append(rowHeader1);                
        if (postalAddress != null) {
            if (postalAddress.getAttnName() != null) {
                orderToStringBuilder.append("<b>Attn:</b> " + postalAddress.getAttnName() + "<br>\n");
            }
            if (postalAddress.getAddress1() != null) {
                orderToStringBuilder.append(postalAddress.getAddress1() + "<br>\n");
            }
            if (postalAddress.getAddress2() != null) {
                orderToStringBuilder.append(postalAddress.getAddress2() + "<br>\n");
            }
            try {
                if (UtilValidate.isNotEmpty(postalAddress.getCity())) {
                    orderToStringBuilder.append(postalAddress.getCity() + ",");
                }

                if (UtilValidate.isNotEmpty(postalAddress.getstateProvinceGeoId())) {
                    orderToStringBuilder.append(GeoSingleton.getGeo(postalAddress.getstateProvinceGeoId()).getgeoCode() + " ");
                }

                if (UtilValidate.isNotEmpty(postalAddress.getPostalCode())) {
                    orderToStringBuilder.append(postalAddress.getPostalCode());
                }
                orderToStringBuilder.append("<br>\n");

                if (UtilValidate.isNotEmpty(postalAddress.getcountryGeoId())) {
                    orderToStringBuilder.append(GeoSingleton.getGeo(postalAddress.getcountryGeoId()).getgeoName() + "\n");
                }
            } catch (Exception ex) {
                Logger.getLogger(OrderMaxUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
            orderToStringBuilder.append(rowFooter);
        }
        return orderToStringBuilder.toString();
    }

    static public String getPhoneHtmlString(TelecomNumber telecomNumber, String purpose) {
        StringBuilder orderToStringBuilder = new StringBuilder();
        String rowHeader1 = "          <tr><td colspan=\"3\"><hr></td></tr>\n"
                + "          <tr>\n"
                + "            <td align=\"right\" valign=\"top\" width=\"19%\">\n"
                + "              <font size=\"2\" face=\"Georgia, Arial\" color=\"black\"><span class=\"label\">&nbsp;";
        String rowHeader2 = "</span>\n"
                + "            </td>\n"
                + "            <td width=\"1%\">&nbsp;</td>\n"
                + "            <td valign=\"top\" width=\"80%\">\n"
                + "                  <div>\n"
                + "                     <div><font size=\"2\" face=\"Georgia, Arial\" color=\"black\">\n";

        String rowFooter = "    </div>\n"
                + "\n"
                + "\n"
                + "                  </div>\n"
                + "            </td>\n"
                + "          </tr>\n";

//        orderToStringBuilder.append(rowHeader);
        orderToStringBuilder.append(rowHeader1.concat(purpose).concat(rowHeader2));
        if (telecomNumber != null) {
            if (telecomNumber.getaskForName() != null) {
                orderToStringBuilder.append("<b>Ask  For:</b> " + telecomNumber.getaskForName() + "<br>\n");
            }

            if (telecomNumber.getcountryCode() != null) {
                orderToStringBuilder.append(telecomNumber.getcountryCode());
                orderToStringBuilder.append(" ");
            }

            if (telecomNumber.getareaCode() != null) {
                orderToStringBuilder.append("(");
                orderToStringBuilder.append(telecomNumber.getareaCode());
                orderToStringBuilder.append(") ");
            }

            if (telecomNumber.getcontactNumber() != null) {
                orderToStringBuilder.append(telecomNumber.getcontactNumber());
                orderToStringBuilder.append("<br>\n");
            }

            orderToStringBuilder.append(rowFooter);
        }

        return orderToStringBuilder.toString();
    }

    static public void outputServiceResult(Map<String, Object> resultMap) {
        for (Map.Entry<String, Object> entryDept : resultMap.entrySet()) {
            Debug.logInfo("Key : " + entryDept.getKey() + " Value: " + entryDept.getValue().toString(), module);
        }

    }
}
