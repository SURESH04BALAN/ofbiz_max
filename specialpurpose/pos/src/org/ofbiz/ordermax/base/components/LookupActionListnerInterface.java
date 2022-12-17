/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.base.components;

import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.report.ReportCreatorSelectionInterface;

/**
 *
 * @author BBS Auctions
 */
public interface LookupActionListnerInterface extends ReportCreatorSelectionInterface {
//    void getSelection(Map<String, Object> resultMap);

    public enum LookupType {

        CountryGeoId,
        StateProvinceGeoId,
        PartyId,
        ProductId,
        OrderId,
        PaymentId,
        ReturnId,
        InvoiceId,
        CategoryId,
        CatalogId,
        FacilityId,
        ProductStoreId,
        TerminalId,
        ProdCatalogCategoryId,
        ProductStoreCatalogId,
        TextFieldSelection,
        PartyTypeId,
        PartyRoleTypeId,
        SingleDateSelection,        
    };

    void setGenericValue(GenericValue genericValue);

    GenericValue getGenericValue();

    LookupType getLookupType();

    String getEditorId();
}
