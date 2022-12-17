/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.reportdesigner_old;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.ordermax.entity.Invoice;
import org.ofbiz.ordermax.entity.SubReportBean;

/**
 *
 * @author siranjeev
 */
public class InvoiceHeaderReport extends Invoice{
   public InvoiceHeaderReport(GenericValue val) {
       super(val);
    }
    public Long getPkid() {
        return pkid;
    }

    public Long getStmt_no() {
        return stmt_no;
    }

    public Long getSales_txn_id() {
        return sales_txn_id;
    }

    public Timestamp getTime_issued() {
        return time_issued;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCcy_pair() {
        return ccy_pair;
    }

    public BigDecimal getXrate() {
        return xrate;
    }

    public Integer getPayment_terms_id() {
        return payment_terms_id;
    }

    public BigDecimal getTotalAmountExcludingGst() {
        return totalAmountExcludingGst;
    }

    public void setTotalAmountExcludingGst(BigDecimal totalAmountExcludingGst) {
        this.totalAmountExcludingGst = totalAmountExcludingGst;
    }

    public BigDecimal getTotalGstAmount() {
        return totalGstAmount;
    }

    public void setTotalGstAmount(BigDecimal totalGstAmount) {
        this.totalGstAmount = totalGstAmount;
    }
    
    BigDecimal totalAmountExcludingGst = BigDecimal.ZERO;
    BigDecimal totalGstAmount = BigDecimal.ZERO;
        
    public BigDecimal getTotal_amt() {
        return total_amt;
    }

    public BigDecimal getOutstanding_amt() {
        return outstanding_amt;
    }

    public BigDecimal getOutstanding_bf_pdc() {
        return outstanding_bf_pdc;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getState() {
        return state;
    }

    public String getStatus() {
        return status;
    }

    public Integer getUserid_edit() {
        return userid_edit;
    }

    public Timestamp getLastupdate() {
        return lastupdate;
    }

    public String getEntity_table() {
        return entity_table;
    }

    public Integer getEntity_key() {
        return entity_key;
    }

    public String getEntity_name() {
        return entity_name;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public String getEntity_contact_person() {
        return entity_contact_person;
    }

    public String getForeign_table() {
        return foreign_table;
    }

    public Integer getForeign_key() {
        return foreign_key;
    }

    public String getForeign_text() {
        return foreign_text;
    }

    public Integer getCust_svcctr_id() {
        return cust_svcctr_id;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public Integer getPc_center() {
        return pc_center;
    }

    public String getTxntype() {
        return txntype;
    }

    public String getStmt_type() {
        return stmt_type;
    }

    public String getReference_no() {
        return reference_no;
    }

    public String getDescription() {
        return description;
    }

    public Long getWork_order() {
        return work_order;
    }

    public Long getDelivery_order() {
        return delivery_order;
    }

    public Long getReceipt_id() {
        return receipt_id;
    }

    public String getDisplay_format() {
        return display_format;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public String getBilling_handphone() {
        return billing_handphone;
    }

    public String getBilling_phone1() {
        return billing_phone1;
    }

    public String getBilling_phone2() {
        return billing_phone2;
    }

    public String getBilling_fax() {
        return billing_fax;
    }

    public String getBilling_email() {
        return billing_email;
    }

    public String getBilling_company_name() {
        return billing_company_name;
    }

    public String getBilling_add1() {
        return billing_add1;
    }

    public String getBilling_add2() {
        return billing_add2;
    }

    public String getBilling_add3() {
        return billing_add3;
    }

    public String getBilling_city() {
        return billing_city;
    }

    public String getBilling_zip() {
        return billing_zip;
    }

    public String getBilling_state() {
        return billing_state;
    }

    public String getBilling_country() {
        return billing_country;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_handphone() {
        return receiver_handphone;
    }

    public String getReceiver_phone1() {
        return receiver_phone1;
    }

    public String getReceiver_phone2() {
        return receiver_phone2;
    }

    public String getReceiver_fax() {
        return receiver_fax;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public String getReceiver_company_name() {
        return receiver_company_name;
    }

    public String getReceiver_add1() {
        return receiver_add1;
    }

    public String getReceiver_add2() {
        return receiver_add2;
    }

    public String getReceiver_add3() {
        return receiver_add3;
    }

    public String getReceiver_city() {
        return receiver_city;
    }

    public String getReceiver_zip() {
        return receiver_zip;
    }

    public String getReceiver_state() {
        return receiver_state;
    }

    public String getReceiver_country() {
        return receiver_country;
    }

    public String getTerms1_option() {
        return terms1_option;
    }

    public Timestamp getTerms1_date() {
        return terms1_date;
    }

    public BigDecimal getTerms1_disc_pct() {
        return terms1_disc_pct;
    }

    public BigDecimal getTerms1_disc_amt() {
        return terms1_disc_amt;
    }

    public String getTerms2_option() {
        return terms2_option;
    }

    public Timestamp getTerms2_date() {
        return terms2_date;
    }

    public BigDecimal getTerms2_disc_pct() {
        return terms2_disc_pct;
    }

    public BigDecimal getTerms2_disc_amt() {
        return terms2_disc_amt;
    }

    public String getRebate1_option() {
        return rebate1_option;
    }

    public Timestamp getRebate1_date() {
        return rebate1_date;
    }

    public BigDecimal getRebate1_gain() {
        return rebate1_gain;
    }

    public BigDecimal getRebate1_consume() {
        return rebate1_consume;
    }

    public String getRebate2_option() {
        return rebate2_option;
    }

    public Timestamp getRebate2_date() {
        return rebate2_date;
    }

    public BigDecimal getRebate2_gain() {
        return rebate2_gain;
    }

    public BigDecimal getRebate2_consume() {
        return rebate2_consume;
    }

    public String getCode_dept() {
        return code_dept;
    }

    public String getCode_project() {
        return code_project;
    }

    public String getCode_supplier() {
        return code_supplier;
    }

    public Integer getSales_id() {
        return sales_id;
    }

    public String getTracking_no() {
        return tracking_no;
    }

    public String getGuid() {
        return guid;
    }

    public BigDecimal getTax_amount() {
        return tax_amount;
    }

    public String getTax_type() {
        return tax_type;
    }

    public String getSubtype1() {
        return subtype1;
    }

    public String getSubtype2() {
        return subtype2;
    }

    public String getSubtype3() {
        return subtype3;
    }

    public Timestamp getTime_accident() {
        return time_accident;
    }

    public BigDecimal getClaim_amount() {
        return claim_amount;
    }

    public String getPolicy_number() {
        return policy_number;
    }

    public String getLoyalty_card_status() {
        return loyalty_card_status;
    }

    public Long getLoyalty_card_pkid() {
        return loyalty_card_pkid;
    }

    public String getTc_stmt_no() {
        return tc_stmt_no;
    }

    public Long getInvoice_id() {
        return invoice_id;
    }

    public Integer getPos_item_id() {
        return pos_item_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public BigDecimal getTotal_quantity() {
        return total_quantity;
    }

    public BigDecimal getUnit_price_quoted() {
        return unit_price_quoted;
    }

    public String getStr_name_1() {
        return str_name_1;
    }

    public String getStr_value_1() {
        return str_value_1;
    }

    public Integer getPic1() {
        return pic1;
    }

    public Integer getPic2() {
        return pic2;
    }

    public Integer getPic3() {
        return pic3;
    }

    public String getCurrency2() {
        return currency2;
    }

    public BigDecimal getUnit_price_quoted2() {
        return unit_price_quoted2;
    }

    public BigDecimal getTaxamt() {
        return taxamt;
    }

    public BigDecimal getTaxamt2() {
        return taxamt2;
    }

    public String getStr_name_2() {
        return str_name_2;
    }

    public String getStr_value_2() {
        return str_value_2;
    }

    public String getStr_name_3() {
        return str_name_3;
    }

    public String getStr_value_3() {
        return str_value_3;
    }

    public String getInt_name_1() {
        return int_name_1;
    }

    public Integer getInt_value_1() {
        return int_value_1;
    }

    public String getInt_name_2() {
        return int_name_2;
    }

    public Integer getInt_value_2() {
        return int_value_2;
    }

    public String getBd_name_1() {
        return bd_name_1;
    }

    public BigDecimal getBd_value_1() {
        return bd_value_1;
    }

    public String getPos_item_type() {
        return pos_item_type;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getBar_code() {
        return bar_code;
    }

    public Boolean getSerialized() {
        return serialized;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getOutstanding_qty() {
        return outstanding_qty;
    }

    public Boolean getPackage_ship() {
        return package_ship;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public BigDecimal getUnit_cost_ma() {
        return unit_cost_ma;
    }

    public BigDecimal getUnit_price_std() {
        return unit_price_std;
    }

    public BigDecimal getUnit_discount() {
        return unit_discount;
    }

    public BigDecimal getUnit_commission() {
        return unit_commission;
    }

    public String getCode_department() {
        return code_department;
    }

    public String getCode_dealer() {
        return code_dealer;
    }

    public String getCode_salesman() {
        return code_salesman;
    }

    public Integer getStk_id() {
        return stk_id;
    }

    public Integer getStk_location_id() {
        return stk_location_id;
    }

    public String getStk_location_code() {
        return stk_location_code;
    }

    public String getBom_convert_mode() {
        return bom_convert_mode;
    }

    public Integer getBom_id() {
        return bom_id;
    }

    public String getBom_convert_status() {
        return bom_convert_status;
    }

    public Timestamp getBom_convert_time() {
        return bom_convert_time;
    }

    public Integer getBom_convert_user() {
        return bom_convert_user;
    }

    public Integer getBom_target_loc() {
        return bom_target_loc;
    }

    public Integer getBom_source_loc() {
        return bom_source_loc;
    }

    public String getWarranty_type() {
        return warranty_type;
    }

    public String getWarranty_option() {
        return warranty_option;
    }

    public Timestamp getWarranty_expiry() {
        return warranty_expiry;
    }

    public String getPseudo_logic() {
        return pseudo_logic;
    }

    public String getPseudo_code() {
        return pseudo_code;
    }

    public String getPseudo_name() {
        return pseudo_name;
    }

    public String getPseudo_description() {
        return pseudo_description;
    }

    public String getPseudo_currency() {
        return pseudo_currency;
    }

    public BigDecimal getPseudo_price() {
        return pseudo_price;
    }

    public BigDecimal getPseudo_qty() {
        return pseudo_qty;
    }

    public String getLoyalty_logic() {
        return loyalty_logic;
    }

    public BigDecimal getLoyalty_points_awarded() {
        return loyalty_points_awarded;
    }

    public BigDecimal getLoyalty_points_redeemed() {
        return loyalty_points_redeemed;
    }

    public String getPackage_group() {
        return package_group;
    }

    public String getItem_remarks() {
        return item_remarks;
    }

    public String getSerials() {
        return serials;
    }

    public String getUom() {
        return uom;
    }

    public String getCode() {
        return code;
    }

    public String getReg_no() {
        return reg_no;
    }

    public String getAddr1() {
        return addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public String getZip() {
        return zip;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getFax_no() {
        return fax_no;
    }

    public String getWeb_url() {
        return web_url;
    }

    public Integer getAcc_pccenterid() {
        return acc_pccenterid;
    }

    public Integer getInv_locationid() {
        return inv_locationid;
    }

    public Integer getCashbook_cash() {
        return cashbook_cash;
    }

    public Integer getCashbook_card() {
        return cashbook_card;
    }

    public Integer getCashbook_cheque() {
        return cashbook_cheque;
    }

    public Integer getCashbook_pd_cheque() {
        return cashbook_pd_cheque;
    }

    public Integer getCashbook_coupon() {
        return cashbook_coupon;
    }

    public Integer getCashbook_other() {
        return cashbook_other;
    }

    public Integer getCashbook_payment() {
        return cashbook_payment;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public String getPricing() {
        return pricing;
    }

    public String getHotlines() {
        return hotlines;
    }

    public BigDecimal getDefault_visa_rate() {
        return default_visa_rate;
    }

    public BigDecimal getDefault_master_rate() {
        return default_master_rate;
    }

    public BigDecimal getDefault_amex_rate() {
        return default_amex_rate;
    }

    public BigDecimal getDefault_diner_rate() {
        return default_diner_rate;
    }

    public BigDecimal getDefault_card1_rate() {
        return default_card1_rate;
    }

    public BigDecimal getDefault_card2_rate() {
        return default_card2_rate;
    }

    public BigDecimal getDefault_card3_rate() {
        return default_card3_rate;
    }

    public BigDecimal getDefault_other_rate() {
        return default_other_rate;
    }

    public String getFormat_invoice_header_text() {
        return format_invoice_header_text;
    }

    public String getFormat_invoice_footer_text() {
        return format_invoice_footer_text;
    }

    public String getFormat_invoice_entity_address() {
        return format_invoice_entity_address;
    }

    public Integer getFormat_invoice_row() {
        return format_invoice_row;
    }

    public String getFormat_invoice_type() {
        return format_invoice_type;
    }

    public String getFormat_cashsale_header_text() {
        return format_cashsale_header_text;
    }

    public String getFormat_cashsale_footer_text() {
        return format_cashsale_footer_text;
    }

    public String getFormat_cashsale_entity_address() {
        return format_cashsale_entity_address;
    }

    public Integer getFormat_cashsale_row() {
        return format_cashsale_row;
    }

    public String getFormat_cashsale_type() {
        return format_cashsale_type;
    }

    public String getFormat_receipt_header_text() {
        return format_receipt_header_text;
    }

    public String getFormat_receipt_footer_text() {
        return format_receipt_footer_text;
    }

    public String getFormat_receipt_entity_address() {
        return format_receipt_entity_address;
    }

    public Integer getFormat_receipt_row() {
        return format_receipt_row;
    }

    public String getFormat_receipt_type() {
        return format_receipt_type;
    }

    public String getFormat_purchase_order_header_text() {
        return format_purchase_order_header_text;
    }

    public String getFormat_purchase_order_footer_text() {
        return format_purchase_order_footer_text;
    }

    public Integer getFormat_purchase_order_row() {
        return format_purchase_order_row;
    }

    public String getFormat_purchase_order_type() {
        return format_purchase_order_type;
    }

    public String getFormat_salary_slip_header_text() {
        return format_salary_slip_header_text;
    }

    public String getFormat_salary_slip_footer_text() {
        return format_salary_slip_footer_text;
    }

    public Integer getFormat_salary_slip_row() {
        return format_salary_slip_row;
    }

    public String getFormat_salary_slip_type() {
        return format_salary_slip_type;
    }

    public String getFormat_payment_voucher_header_text() {
        return format_payment_voucher_header_text;
    }

    public String getFormat_payment_voucher_footer_text() {
        return format_payment_voucher_footer_text;
    }

    public Integer getFormat_payment_voucher_row() {
        return format_payment_voucher_row;
    }

    public String getFormat_payment_voucher_type() {
        return format_payment_voucher_type;
    }

    public String getFormat_credit_memo_header_text() {
        return format_credit_memo_header_text;
    }

    public String getFormat_credit_memo_footer_text() {
        return format_credit_memo_footer_text;
    }

    public Integer getFormat_credit_memo_row() {
        return format_credit_memo_row;
    }

    public String getFormat_credit_memo_type() {
        return format_credit_memo_type;
    }

    public Integer getDefault_rma_location_cust() {
        return default_rma_location_cust;
    }

    public Integer getDefault_rma_location_supp() {
        return default_rma_location_supp;
    }

    public Integer getDefault_rma_location_hq() {
        return default_rma_location_hq;
    }

    public Integer getDefault_rma_location_branch() {
        return default_rma_location_branch;
    }

    public String getManager_password01() {
        return manager_password01;
    }

    public String getManager_password02() {
        return manager_password02;
    }

    public String getManager_password03() {
        return manager_password03;
    }

    public String getManager_password04() {
        return manager_password04;
    }

    public String getManager_password05() {
        return manager_password05;
    }

    public String getManager_password06() {
        return manager_password06;
    }

    public String getManager_password07() {
        return manager_password07;
    }

    public String getManager_password08() {
        return manager_password08;
    }

    public String getManager_password09() {
        return manager_password09;
    }

    public String getTelephone_developer() {
        return telephone_developer;
    }

    public String getTelephone_sysadmin() {
        return telephone_sysadmin;
    }

    public String getTelephone_clerk() {
        return telephone_clerk;
    }

    public String getTelephone_admin() {
        return telephone_admin;
    }

    public String getTelephone_manager() {
        return telephone_manager;
    }

    public String getTelephone_sales() {
        return telephone_sales;
    }

    public String getTelephone_tech_support() {
        return telephone_tech_support;
    }

    public String getTelephone_director() {
        return telephone_director;
    }

    public String getMobilephone_developer() {
        return mobilephone_developer;
    }

    public String getMobilephone_sysadmin() {
        return mobilephone_sysadmin;
    }

    public String getMobilephone_clerk() {
        return mobilephone_clerk;
    }

    public String getMobilephone_admin() {
        return mobilephone_admin;
    }

    public String getMobilephone_manager() {
        return mobilephone_manager;
    }

    public String getMobilephone_sales() {
        return mobilephone_sales;
    }

    public String getMobilephone_tech_support() {
        return mobilephone_tech_support;
    }

    public String getMobilephone_director() {
        return mobilephone_director;
    }

    public String getEmail_system() {
        return email_system;
    }

    public String getEmail_developer() {
        return email_developer;
    }

    public String getEmail_sysadmin() {
        return email_sysadmin;
    }

    public String getEmail_clerk() {
        return email_clerk;
    }

    public String getEmail_admin() {
        return email_admin;
    }

    public String getEmail_manager() {
        return email_manager;
    }

    public String getEmail_sales() {
        return email_sales;
    }

    public String getEmail_tech_support() {
        return email_tech_support;
    }

    public String getEmail_director() {
        return email_director;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public String getLoyalty_card_no_prefix() {
        return loyalty_card_no_prefix;
    }

    public String getLoyalty_card_no_postfix() {
        return loyalty_card_no_postfix;
    }

    public Integer getCrv_day_from() {
        return crv_day_from;
    }

    public Integer getCrv_day_to() {
        return crv_day_to;
    }

    public String getFormat_proforma_invoice_type() {
        return format_proforma_invoice_type;
    }

    public String getFormat_proforma_invoice_footer_text() {
        return format_proforma_invoice_footer_text;
    }

    public String getFormat_proforma_invoice_header_text() {
        return format_proforma_invoice_header_text;
    }

    public Integer getFormat_proforma_invoice_row() {
        return format_proforma_invoice_row;
    }

    public String getFormat_service_note_type() {
        return format_service_note_type;
    }

    public String getFormat_service_note_footer_text() {
        return format_service_note_footer_text;
    }

    public String getFormat_service_note_header_text() {
        return format_service_note_header_text;
    }

    public Integer getFormat_service_note_row() {
        return format_service_note_row;
    }

    public String getTax_reg_number() {
        return tax_reg_number;
    }

    public String getBranch_state() {
        return branch_state;
    }

    private java.lang.Long pkid = new java.lang.Long(1);
    private java.lang.Long stmt_no = new java.lang.Long(1);
    ; 
 private java.lang.Long sales_txn_id = new java.lang.Long(1);
    ; 
 private java.sql.Timestamp time_issued;
    private java.lang.String currency;
    private java.lang.String ccy_pair;
    private java.math.BigDecimal xrate;
    private java.lang.Integer payment_terms_id;
    private java.math.BigDecimal total_amt;
    private java.math.BigDecimal outstanding_amt;
    private java.math.BigDecimal outstanding_bf_pdc;
    private java.lang.String remarks;

    private java.lang.String status;
    private java.lang.Integer userid_edit;
    private java.sql.Timestamp lastupdate;
    private java.lang.String entity_table;
    private java.lang.Integer entity_key;
    private java.lang.String entity_name;
    private java.lang.String entity_type;
    private java.lang.String identity_number;
    private java.lang.String entity_contact_person;
    private java.lang.String foreign_table;
    private java.lang.Integer foreign_key;
    private java.lang.String foreign_text;
    private java.lang.Integer cust_svcctr_id;
    private java.lang.Integer location_id;
    private java.lang.Integer pc_center;
    private java.lang.String txntype;
    private java.lang.String stmt_type;
    private java.lang.String reference_no;
    private java.lang.String description;
    private java.lang.Long work_order = new java.lang.Long(1);

    private java.lang.Long delivery_order = new java.lang.Long(1);

    private java.lang.Long receipt_id = new java.lang.Long(1);

    private java.lang.String display_format;
    private java.lang.String doc_type;
    private java.lang.String billing_handphone;
    private java.lang.String billing_phone1;
    private java.lang.String billing_phone2;
    private java.lang.String billing_fax;
    private java.lang.String billing_email;
    private java.lang.String billing_company_name;
    private java.lang.String billing_add1 = "";
    private java.lang.String billing_add2 = "";
    private java.lang.String billing_add3;
    private java.lang.String billing_city = "";
    private java.lang.String billing_zip = "";
    private java.lang.String billing_state = "";
    private java.lang.String billing_country;
    private java.lang.String receiver_name;
    private java.lang.String receiver_handphone;
    private java.lang.String receiver_phone1;
    private java.lang.String receiver_phone2;
    private java.lang.String receiver_fax;
    private java.lang.String receiver_email;
    private java.lang.String receiver_company_name;
    private java.lang.String receiver_add1;
    private java.lang.String receiver_add2;
    private java.lang.String receiver_add3;
    private java.lang.String receiver_city;
    private java.lang.String receiver_zip;
    private java.lang.String receiver_state;
    private java.lang.String receiver_country;
    private java.lang.String terms1_option;
    private java.sql.Timestamp terms1_date;
    private java.sql.Timestamp orderDate;    
    private java.lang.String orderId;    
    private java.math.BigDecimal terms1_disc_pct;
    private java.math.BigDecimal terms1_disc_amt;
    private java.lang.String terms2_option;
    private java.sql.Timestamp terms2_date;
    private java.math.BigDecimal terms2_disc_pct;
    private java.math.BigDecimal terms2_disc_amt;
    private java.lang.String rebate1_option;
    private java.sql.Timestamp rebate1_date;
    private java.math.BigDecimal rebate1_gain;
    private java.math.BigDecimal rebate1_consume;
    private java.lang.String rebate2_option;
    private java.sql.Timestamp rebate2_date;
    private java.math.BigDecimal rebate2_gain;
    private java.math.BigDecimal rebate2_consume;
    private java.lang.String code_dept;
    private java.lang.String code_project;
    private java.lang.String code_supplier;
    private java.lang.Integer sales_id;
    private java.lang.String tracking_no;
    private java.lang.String guid;
    private java.math.BigDecimal tax_amount;
    private java.lang.String tax_type;
    private java.lang.String subtype1;
    private java.lang.String subtype2;
    private java.lang.String subtype3;
    private java.sql.Timestamp time_accident;
    private java.math.BigDecimal claim_amount;
    private java.lang.String policy_number;
    private java.lang.String loyalty_card_status;
    private java.lang.Long loyalty_card_pkid = new java.lang.Long(1);

 private java.lang.String tc_stmt_no;
    private java.lang.Long invoice_id = new java.lang.Long(1);

 private java.lang.Integer pos_item_id;
    private java.lang.String item_type;
    private java.math.BigDecimal total_quantity;
    private java.math.BigDecimal unit_price_quoted;
    private java.lang.String str_name_1;
    private java.lang.String str_value_1;
    private java.lang.Integer pic1;
    private java.lang.Integer pic2;
    private java.lang.Integer pic3;
    private java.lang.String currency2;
    private java.math.BigDecimal unit_price_quoted2;
    private java.math.BigDecimal taxamt;
    private java.math.BigDecimal taxamt2;
    private java.lang.String str_name_2;
    private java.lang.String str_value_2;
    private java.lang.String str_name_3;
    private java.lang.String str_value_3;
    private java.lang.String int_name_1;
    private java.lang.Integer int_value_1;
    private java.lang.String int_name_2;
    private java.lang.Integer int_value_2;
    private java.lang.String bd_name_1;
    private java.math.BigDecimal bd_value_1;
    private java.lang.String pos_item_type;
    private java.lang.Integer item_id;
    private java.lang.String item_code;
    private java.lang.String bar_code;
    private java.lang.Boolean serialized;
    private java.lang.String name;
    private java.math.BigDecimal outstanding_qty;
    private java.lang.Boolean package_ship;
    private java.lang.Long parent_id = new java.lang.Long(1);
 private java.math.BigDecimal unit_cost_ma;
    private java.math.BigDecimal unit_price_std;
    private java.math.BigDecimal unit_discount;
    private java.math.BigDecimal unit_commission;
    private java.lang.String code_department;
    private java.lang.String code_dealer;
    private java.lang.String code_salesman;
    private java.lang.Integer stk_id;
    private java.lang.Integer stk_location_id;
    private java.lang.String stk_location_code;
    private java.lang.String bom_convert_mode;
    private java.lang.Integer bom_id;
    private java.lang.String bom_convert_status;
    private java.sql.Timestamp bom_convert_time;
    private java.lang.Integer bom_convert_user;
    private java.lang.Integer bom_target_loc;
    private java.lang.Integer bom_source_loc;
    private java.lang.String warranty_type;
    private java.lang.String warranty_option;
    private java.sql.Timestamp warranty_expiry;
    private java.lang.String pseudo_logic;
    private java.lang.String pseudo_code;
    private java.lang.String pseudo_name;
    private java.lang.String pseudo_description;
    private java.lang.String pseudo_currency;
    private java.math.BigDecimal pseudo_price;
    private java.math.BigDecimal pseudo_qty;
    private java.lang.String loyalty_logic;
    private java.math.BigDecimal loyalty_points_awarded;
    private java.math.BigDecimal loyalty_points_redeemed;
    private java.lang.String package_group;
    private java.lang.String item_remarks;
    private java.lang.String serials;
    private java.lang.String uom;
    private java.lang.String code;
    private java.lang.String reg_no;
    private java.lang.String addr1 = "10";
    private java.lang.String addr2 = "JUMAL PLACE";
    private java.lang.String addr3 = "SMITHFIELD";
    private java.lang.String city = "SMITHFIELD";
    private java.lang.String zip = "2164";
    private java.lang.String state = "NSW";

    private java.lang.String countrycode = "+61";
    private java.lang.String phone_no = "(02) 9604 9600";
    private java.lang.String fax_no = "(02) 9604 3673";
    private java.lang.String web_url = "sales@maxspices.com";//"www.maxspices.com";
    private java.lang.Integer acc_pccenterid;
    private java.lang.Integer inv_locationid;
    private java.lang.Integer cashbook_cash;
    private java.lang.Integer cashbook_card;
    private java.lang.Integer cashbook_cheque;
    private java.lang.Integer cashbook_pd_cheque;
    private java.lang.Integer cashbook_coupon;
    private java.lang.Integer cashbook_other;
    private java.lang.Integer cashbook_payment;
    private java.lang.String logo_url;
    private java.lang.String pricing;
    private java.lang.String hotlines;
    private java.math.BigDecimal default_visa_rate;
    private java.math.BigDecimal default_master_rate;
    private java.math.BigDecimal default_amex_rate;
    private java.math.BigDecimal default_diner_rate;
    private java.math.BigDecimal default_card1_rate;
    private java.math.BigDecimal default_card2_rate;
    private java.math.BigDecimal default_card3_rate;
    private java.math.BigDecimal default_other_rate;
    private java.lang.String format_invoice_header_text;
    private java.lang.String format_invoice_footer_text;
    private java.lang.String format_invoice_entity_address;
    private java.lang.Integer format_invoice_row;
    private java.lang.String format_invoice_type;
    private java.lang.String format_cashsale_header_text;
    private java.lang.String format_cashsale_footer_text;
    private java.lang.String format_cashsale_entity_address;
    private java.lang.Integer format_cashsale_row;
    private java.lang.String format_cashsale_type;
    private java.lang.String format_receipt_header_text;
    private java.lang.String format_receipt_footer_text;
    private java.lang.String format_receipt_entity_address;
    private java.lang.Integer format_receipt_row;
    private java.lang.String format_receipt_type;
    private java.lang.String format_purchase_order_header_text;
    private java.lang.String format_purchase_order_footer_text;
    private java.lang.Integer format_purchase_order_row;
    private java.lang.String format_purchase_order_type;
    private java.lang.String format_salary_slip_header_text;
    private java.lang.String format_salary_slip_footer_text;
    private java.lang.Integer format_salary_slip_row;
    private java.lang.String format_salary_slip_type;
    private java.lang.String format_payment_voucher_header_text;
    private java.lang.String format_payment_voucher_footer_text;
    private java.lang.Integer format_payment_voucher_row;
    private java.lang.String format_payment_voucher_type;
    private java.lang.String format_credit_memo_header_text;
    private java.lang.String format_credit_memo_footer_text;
    private java.lang.Integer format_credit_memo_row;
    private java.lang.String format_credit_memo_type;
    private java.lang.Integer default_rma_location_cust;
    private java.lang.Integer default_rma_location_supp;
    private java.lang.Integer default_rma_location_hq;
    private java.lang.Integer default_rma_location_branch;
    private java.lang.String manager_password01;
    private java.lang.String manager_password02;
    private java.lang.String manager_password03;
    private java.lang.String manager_password04;
    private java.lang.String manager_password05;
    private java.lang.String manager_password06;
    private java.lang.String manager_password07;
    private java.lang.String manager_password08;
    private java.lang.String manager_password09;
    private java.lang.String telephone_developer;
    private java.lang.String telephone_sysadmin;
    private java.lang.String telephone_clerk;
    private java.lang.String telephone_admin;
    private java.lang.String telephone_manager;
    private java.lang.String telephone_sales;
    private java.lang.String telephone_tech_support;
    private java.lang.String telephone_director;
    private java.lang.String mobilephone_developer;
    private java.lang.String mobilephone_sysadmin;
    private java.lang.String mobilephone_clerk;
    private java.lang.String mobilephone_admin;
    private java.lang.String mobilephone_manager;
    private java.lang.String mobilephone_sales;
    private java.lang.String mobilephone_tech_support;
    private java.lang.String mobilephone_director;
    private java.lang.String email_system;
    private java.lang.String email_developer;
    private java.lang.String email_sysadmin;
    private java.lang.String email_clerk;
    private java.lang.String email_admin;
    private java.lang.String email_manager;
    private java.lang.String email_sales = "sales@maxspices.com";
    private java.lang.String email_tech_support;
    private java.lang.String email_director;
    private java.lang.Integer customer_id;
    private java.lang.Integer supplier_id;
    private java.lang.String loyalty_card_no_prefix;
    private java.lang.String loyalty_card_no_postfix;
    private java.lang.Integer crv_day_from;
    private java.lang.Integer crv_day_to;
    private java.lang.String format_proforma_invoice_type;
    private java.lang.String format_proforma_invoice_footer_text;
    private java.lang.String format_proforma_invoice_header_text;
    private java.lang.Integer format_proforma_invoice_row;
    private java.lang.String format_service_note_type;
    private java.lang.String format_service_note_footer_text;
    private java.lang.String format_service_note_header_text;
    private java.lang.Integer format_service_note_row;
    private java.lang.String tax_reg_number;
    private java.lang.String branch_state;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getAttnName() {
        return attnName;
    }

    public void setAttnName(String attnName) {
        this.attnName = attnName;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getSupplierABN() {
        return supplierABN;
    }

    public void setSupplierABN(String supplierABN) {
        this.supplierABN = supplierABN;
    }

    public String getCodeCustomer() {
        return codeCustomer;
    }

    public void setCodeCustomer(String codeCustomer) {
        this.codeCustomer = codeCustomer;
    }
    String customerReference;

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }
    String codeSupplier;
    String phoneNo;
    String faxNo;
    String attnName;
    String salesPerson;
    String supplierABN;
    String supplierPhone;
    String codeCustomer;
    String accountStatus;
    String invoiceNumber;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    BigDecimal currentOutstanding;
    BigDecimal days30Outstanding;
    BigDecimal days60Outstanding;
    BigDecimal days90OrMoreOutstanding;
    BigDecimal totalOutstanding;  

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public BigDecimal getCurrentOutstanding() {
        return currentOutstanding;
    }

    public void setCurrentOutstanding(BigDecimal currentOutstanding) {
        this.currentOutstanding = currentOutstanding;
    }

    public BigDecimal getDays30Outstanding() {
        return days30Outstanding;
    }

    public void setDays30Outstanding(BigDecimal days30Outstanding) {
        this.days30Outstanding = days30Outstanding;
    }

    public BigDecimal getDays60Outstanding() {
        return days60Outstanding;
    }

    public void setDays60Outstanding(BigDecimal days60Outstanding) {
        this.days60Outstanding = days60Outstanding;
    }

    public BigDecimal getDays90OrMoreOutstanding() {
        return days90OrMoreOutstanding;
    }

    public void setDays90OrMoreOutstanding(BigDecimal days90OrMoreOutstanding) {
        this.days90OrMoreOutstanding = days90OrMoreOutstanding;
    }

    public BigDecimal getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(BigDecimal totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }
    
    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }
    
    public String getCodeSupplier() {
        return codeSupplier;
    }

    public void setCodeSupplier(String codeSupplier) {
        this.codeSupplier = codeSupplier;
    }
    
    public Timestamp getOrderDate() {
        return orderDate;
    }
    private Timestamp invoiceDate;
    public void setInvoiceDate(Timestamp invDate) {
        this.invoiceDate = invDate;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setPkid(java.lang.Long pkid) {
        this.pkid = pkid;
    }

    public void setStmt_no(java.lang.Long stmt_no) {
        this.stmt_no = stmt_no;
    }

    public void setSales_txn_id(java.lang.Long sales_txn_id) {
        this.sales_txn_id = sales_txn_id;
    }

    public void setTime_issued(java.sql.Timestamp time_issued) {
        this.time_issued = time_issued;
    }

    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }

    public void setCcy_pair(java.lang.String ccy_pair) {
        this.ccy_pair = ccy_pair;
    }

    public void setXrate(java.math.BigDecimal xrate) {
        this.xrate = xrate;
    }

    public void setPayment_terms_id(java.lang.Integer payment_terms_id) {
        this.payment_terms_id = payment_terms_id;
    }

    public void setTotal_amt(java.math.BigDecimal total_amt) {
        this.total_amt = total_amt;
    }

    public void setOutstanding_amt(java.math.BigDecimal outstanding_amt) {
        this.outstanding_amt = outstanding_amt;
    }

    public void setOutstanding_bf_pdc(java.math.BigDecimal outstanding_bf_pdc) {
        this.outstanding_bf_pdc = outstanding_bf_pdc;
    }

    public void setRemarks(java.lang.String remarks) {
        this.remarks = remarks;
    }

    public void setState(java.lang.String state) {
        this.state = state;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    public void setUserid_edit(java.lang.Integer userid_edit) {
        this.userid_edit = userid_edit;
    }

    public void setLastupdate(java.sql.Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }

    public void setEntity_table(java.lang.String entity_table) {
        this.entity_table = entity_table;
    }

    public void setEntity_key(java.lang.Integer entity_key) {
        this.entity_key = entity_key;
    }

    public void setEntity_name(java.lang.String entity_name) {
        this.entity_name = entity_name;
    }

    public void setEntity_type(java.lang.String entity_type) {
        this.entity_type = entity_type;
    }

    public void setIdentity_number(java.lang.String identity_number) {
        this.identity_number = identity_number;
    }

    public void setEntity_contact_person(java.lang.String entity_contact_person) {
        this.entity_contact_person = entity_contact_person;
    }

    public void setForeign_table(java.lang.String foreign_table) {
        this.foreign_table = foreign_table;
    }

    public void setForeign_key(java.lang.Integer foreign_key) {
        this.foreign_key = foreign_key;
    }

    public void setForeign_text(java.lang.String foreign_text) {
        this.foreign_text = foreign_text;
    }

    public void setCust_svcctr_id(java.lang.Integer cust_svcctr_id) {
        this.cust_svcctr_id = cust_svcctr_id;
    }

    public void setLocation_id(java.lang.Integer location_id) {
        this.location_id = location_id;
    }

    public void setPc_center(java.lang.Integer pc_center) {
        this.pc_center = pc_center;
    }

    public void setTxntype(java.lang.String txntype) {
        this.txntype = txntype;
    }

    public void setStmt_type(java.lang.String stmt_type) {
        this.stmt_type = stmt_type;
    }

    public void setReference_no(java.lang.String reference_no) {
        this.reference_no = reference_no;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public void setWork_order(java.lang.Long work_order) {
        this.work_order = work_order;
    }

    public void setDelivery_order(java.lang.Long delivery_order) {
        this.delivery_order = delivery_order;
    }

    public void setReceipt_id(java.lang.Long receipt_id) {
        this.receipt_id = receipt_id;
    }

    public void setDisplay_format(java.lang.String display_format) {
        this.display_format = display_format;
    }

    public void setDoc_type(java.lang.String doc_type) {
        this.doc_type = doc_type;
    }

    public void setBilling_handphone(java.lang.String billing_handphone) {
        this.billing_handphone = billing_handphone;
    }

    public void setBilling_phone1(java.lang.String billing_phone1) {
        this.billing_phone1 = billing_phone1;
    }

    public void setBilling_phone2(java.lang.String billing_phone2) {
        this.billing_phone2 = billing_phone2;
    }

    public void setBilling_fax(java.lang.String billing_fax) {
        this.billing_fax = billing_fax;
    }

    public void setBilling_email(java.lang.String billing_email) {
        this.billing_email = billing_email;
    }

    public void setBilling_company_name(java.lang.String billing_company_name) {
        this.billing_company_name = billing_company_name;
    }

    public void setBilling_add1(java.lang.String billing_add1) {
        this.billing_add1 = billing_add1;
    }

    public void setBilling_add2(java.lang.String billing_add2) {
        this.billing_add2 = billing_add2;
    }

    public void setBilling_add3(java.lang.String billing_add3) {
        this.billing_add3 = billing_add3;
    }

    public void setBilling_city(java.lang.String billing_city) {
        this.billing_city = billing_city;
    }

    public void setBilling_zip(java.lang.String billing_zip) {
        this.billing_zip = billing_zip;
    }

    public void setBilling_state(java.lang.String billing_state) {
        this.billing_state = billing_state;
    }

    public void setBilling_country(java.lang.String billing_country) {
        this.billing_country = billing_country;
    }

    public void setReceiver_name(java.lang.String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_handphone(java.lang.String receiver_handphone) {
        this.receiver_handphone = receiver_handphone;
    }

    public void setReceiver_phone1(java.lang.String receiver_phone1) {
        this.receiver_phone1 = receiver_phone1;
    }

    public void setReceiver_phone2(java.lang.String receiver_phone2) {
        this.receiver_phone2 = receiver_phone2;
    }

    public void setReceiver_fax(java.lang.String receiver_fax) {
        this.receiver_fax = receiver_fax;
    }

    public void setReceiver_email(java.lang.String receiver_email) {
        this.receiver_email = receiver_email;
    }

    public void setReceiver_company_name(java.lang.String receiver_company_name) {
        this.receiver_company_name = receiver_company_name;
    }

    public void setReceiver_add1(java.lang.String receiver_add1) {
        this.receiver_add1 = receiver_add1;
    }

    public void setReceiver_add2(java.lang.String receiver_add2) {
        this.receiver_add2 = receiver_add2;
    }

    public void setReceiver_add3(java.lang.String receiver_add3) {
        this.receiver_add3 = receiver_add3;
    }

    public void setReceiver_city(java.lang.String receiver_city) {
        this.receiver_city = receiver_city;
    }

    public void setReceiver_zip(java.lang.String receiver_zip) {
        this.receiver_zip = receiver_zip;
    }

    public void setReceiver_state(java.lang.String receiver_state) {
        this.receiver_state = receiver_state;
    }

    public void setReceiver_country(java.lang.String receiver_country) {
        this.receiver_country = receiver_country;
    }

    public void setTerms1_option(java.lang.String terms1_option) {
        this.terms1_option = terms1_option;
    }

    public void setTerms1_date(java.sql.Timestamp terms1_date) {
        this.terms1_date = terms1_date;
    }

    public void setTerms1_disc_pct(java.math.BigDecimal terms1_disc_pct) {
        this.terms1_disc_pct = terms1_disc_pct;
    }

    public void setTerms1_disc_amt(java.math.BigDecimal terms1_disc_amt) {
        this.terms1_disc_amt = terms1_disc_amt;
    }

    public void setTerms2_option(java.lang.String terms2_option) {
        this.terms2_option = terms2_option;
    }

    public void setTerms2_date(java.sql.Timestamp terms2_date) {
        this.terms2_date = terms2_date;
    }

    public void setTerms2_disc_pct(java.math.BigDecimal terms2_disc_pct) {
        this.terms2_disc_pct = terms2_disc_pct;
    }

    public void setTerms2_disc_amt(java.math.BigDecimal terms2_disc_amt) {
        this.terms2_disc_amt = terms2_disc_amt;
    }

    public void setRebate1_option(java.lang.String rebate1_option) {
        this.rebate1_option = rebate1_option;
    }

    public void setRebate1_date(java.sql.Timestamp rebate1_date) {
        this.rebate1_date = rebate1_date;
    }

    public void setRebate1_gain(java.math.BigDecimal rebate1_gain) {
        this.rebate1_gain = rebate1_gain;
    }

    public void setRebate1_consume(java.math.BigDecimal rebate1_consume) {
        this.rebate1_consume = rebate1_consume;
    }

    public void setRebate2_option(java.lang.String rebate2_option) {
        this.rebate2_option = rebate2_option;
    }

    public void setRebate2_date(java.sql.Timestamp rebate2_date) {
        this.rebate2_date = rebate2_date;
    }

    public void setRebate2_gain(java.math.BigDecimal rebate2_gain) {
        this.rebate2_gain = rebate2_gain;
    }

    public void setRebate2_consume(java.math.BigDecimal rebate2_consume) {
        this.rebate2_consume = rebate2_consume;
    }

    public void setCode_dept(java.lang.String code_dept) {
        this.code_dept = code_dept;
    }

    public void setCode_project(java.lang.String code_project) {
        this.code_project = code_project;
    }

    public void setCode_supplier(java.lang.String code_supplier) {
        this.code_supplier = code_supplier;
    }

    public void setSales_id(java.lang.Integer sales_id) {
        this.sales_id = sales_id;
    }

    public void setTracking_no(java.lang.String tracking_no) {
        this.tracking_no = tracking_no;
    }

    public void setGuid(java.lang.String guid) {
        this.guid = guid;
    }

    public void setTax_amount(java.math.BigDecimal tax_amount) {
        this.tax_amount = tax_amount;
    }

    public void setTax_type(java.lang.String tax_type) {
        this.tax_type = tax_type;
    }

    public void setSubtype1(java.lang.String subtype1) {
        this.subtype1 = subtype1;
    }

    public void setSubtype2(java.lang.String subtype2) {
        this.subtype2 = subtype2;
    }

    public void setSubtype3(java.lang.String subtype3) {
        this.subtype3 = subtype3;
    }

    public void setTime_accident(java.sql.Timestamp time_accident) {
        this.time_accident = time_accident;
    }

    public void setClaim_amount(java.math.BigDecimal claim_amount) {
        this.claim_amount = claim_amount;
    }

    public void setPolicy_number(java.lang.String policy_number) {
        this.policy_number = policy_number;
    }

    public void setLoyalty_card_status(java.lang.String loyalty_card_status) {
        this.loyalty_card_status = loyalty_card_status;
    }

    public void setLoyalty_card_pkid(java.lang.Long loyalty_card_pkid) {
        this.loyalty_card_pkid = loyalty_card_pkid;
    }

    public void setTc_stmt_no(java.lang.String tc_stmt_no) {
        this.tc_stmt_no = tc_stmt_no;
    }

    public void setInvoice_id(java.lang.Long invoice_id) {
        this.invoice_id = invoice_id;
    }

    public void setPos_item_id(java.lang.Integer pos_item_id) {
        this.pos_item_id = pos_item_id;
    }

    public void setItem_type(java.lang.String item_type) {
        this.item_type = item_type;
    }

    public void setTotal_quantity(java.math.BigDecimal total_quantity) {
        this.total_quantity = total_quantity;
    }

    public void setUnit_price_quoted(java.math.BigDecimal unit_price_quoted) {
        this.unit_price_quoted = unit_price_quoted;
    }

    public void setStr_name_1(java.lang.String str_name_1) {
        this.str_name_1 = str_name_1;
    }

    public void setStr_value_1(java.lang.String str_value_1) {
        this.str_value_1 = str_value_1;
    }

    public void setPic1(java.lang.Integer pic1) {
        this.pic1 = pic1;
    }

    public void setPic2(java.lang.Integer pic2) {
        this.pic2 = pic2;
    }

    public void setPic3(java.lang.Integer pic3) {
        this.pic3 = pic3;
    }

    public void setCurrency2(java.lang.String currency2) {
        this.currency2 = currency2;
    }

    public void setUnit_price_quoted2(java.math.BigDecimal unit_price_quoted2) {
        this.unit_price_quoted2 = unit_price_quoted2;
    }

    public void setTaxamt(java.math.BigDecimal taxamt) {
        this.taxamt = taxamt;
    }

    public void setTaxamt2(java.math.BigDecimal taxamt2) {
        this.taxamt2 = taxamt2;
    }

    public void setStr_name_2(java.lang.String str_name_2) {
        this.str_name_2 = str_name_2;
    }

    public void setStr_value_2(java.lang.String str_value_2) {
        this.str_value_2 = str_value_2;
    }

    public void setStr_name_3(java.lang.String str_name_3) {
        this.str_name_3 = str_name_3;
    }

    public void setStr_value_3(java.lang.String str_value_3) {
        this.str_value_3 = str_value_3;
    }

    public void setInt_name_1(java.lang.String int_name_1) {
        this.int_name_1 = int_name_1;
    }

    public void setInt_value_1(java.lang.Integer int_value_1) {
        this.int_value_1 = int_value_1;
    }

    public void setInt_name_2(java.lang.String int_name_2) {
        this.int_name_2 = int_name_2;
    }

    public void setInt_value_2(java.lang.Integer int_value_2) {
        this.int_value_2 = int_value_2;
    }

    public void setBd_name_1(java.lang.String bd_name_1) {
        this.bd_name_1 = bd_name_1;
    }

    public void setBd_value_1(java.math.BigDecimal bd_value_1) {
        this.bd_value_1 = bd_value_1;
    }

    public void setPos_item_type(java.lang.String pos_item_type) {
        this.pos_item_type = pos_item_type;
    }

    public void setItem_id(java.lang.Integer item_id) {
        this.item_id = item_id;
    }

    public void setItem_code(java.lang.String item_code) {
        this.item_code = item_code;
    }

    public void setBar_code(java.lang.String bar_code) {
        this.bar_code = bar_code;
    }

    public void setSerialized(java.lang.Boolean serialized) {
        this.serialized = serialized;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setOutstanding_qty(java.math.BigDecimal outstanding_qty) {
        this.outstanding_qty = outstanding_qty;
    }

    public void setPackage_ship(java.lang.Boolean package_ship) {
        this.package_ship = package_ship;
    }

    public void setParent_id(java.lang.Long parent_id) {
        this.parent_id = parent_id;
    }

    public void setUnit_cost_ma(java.math.BigDecimal unit_cost_ma) {
        this.unit_cost_ma = unit_cost_ma;
    }

    public void setUnit_price_std(java.math.BigDecimal unit_price_std) {
        this.unit_price_std = unit_price_std;
    }

    public void setUnit_discount(java.math.BigDecimal unit_discount) {
        this.unit_discount = unit_discount;
    }

    public void setUnit_commission(java.math.BigDecimal unit_commission) {
        this.unit_commission = unit_commission;
    }

    public void setCode_department(java.lang.String code_department) {
        this.code_department = code_department;
    }

    public void setCode_dealer(java.lang.String code_dealer) {
        this.code_dealer = code_dealer;
    }

    public void setCode_salesman(java.lang.String code_salesman) {
        this.code_salesman = code_salesman;
    }

    public void setStk_id(java.lang.Integer stk_id) {
        this.stk_id = stk_id;
    }

    public void setStk_location_id(java.lang.Integer stk_location_id) {
        this.stk_location_id = stk_location_id;
    }

    public void setStk_location_code(java.lang.String stk_location_code) {
        this.stk_location_code = stk_location_code;
    }

    public void setBom_convert_mode(java.lang.String bom_convert_mode) {
        this.bom_convert_mode = bom_convert_mode;
    }

    public void setBom_id(java.lang.Integer bom_id) {
        this.bom_id = bom_id;
    }

    public void setBom_convert_status(java.lang.String bom_convert_status) {
        this.bom_convert_status = bom_convert_status;
    }

    public void setBom_convert_time(java.sql.Timestamp bom_convert_time) {
        this.bom_convert_time = bom_convert_time;
    }

    public void setBom_convert_user(java.lang.Integer bom_convert_user) {
        this.bom_convert_user = bom_convert_user;
    }

    public void setBom_target_loc(java.lang.Integer bom_target_loc) {
        this.bom_target_loc = bom_target_loc;
    }

    public void setBom_source_loc(java.lang.Integer bom_source_loc) {
        this.bom_source_loc = bom_source_loc;
    }

    public void setWarranty_type(java.lang.String warranty_type) {
        this.warranty_type = warranty_type;
    }

    public void setWarranty_option(java.lang.String warranty_option) {
        this.warranty_option = warranty_option;
    }

    public void setWarranty_expiry(java.sql.Timestamp warranty_expiry) {
        this.warranty_expiry = warranty_expiry;
    }

    public void setPseudo_logic(java.lang.String pseudo_logic) {
        this.pseudo_logic = pseudo_logic;
    }

    public void setPseudo_code(java.lang.String pseudo_code) {
        this.pseudo_code = pseudo_code;
    }

    public void setPseudo_name(java.lang.String pseudo_name) {
        this.pseudo_name = pseudo_name;
    }

    public void setPseudo_description(java.lang.String pseudo_description) {
        this.pseudo_description = pseudo_description;
    }

    public void setPseudo_currency(java.lang.String pseudo_currency) {
        this.pseudo_currency = pseudo_currency;
    }

    public void setPseudo_price(java.math.BigDecimal pseudo_price) {
        this.pseudo_price = pseudo_price;
    }

    public void setPseudo_qty(java.math.BigDecimal pseudo_qty) {
        this.pseudo_qty = pseudo_qty;
    }

    public void setLoyalty_logic(java.lang.String loyalty_logic) {
        this.loyalty_logic = loyalty_logic;
    }

    public void setLoyalty_points_awarded(java.math.BigDecimal loyalty_points_awarded) {
        this.loyalty_points_awarded = loyalty_points_awarded;
    }

    public void setLoyalty_points_redeemed(java.math.BigDecimal loyalty_points_redeemed) {
        this.loyalty_points_redeemed = loyalty_points_redeemed;
    }

    public void setPackage_group(java.lang.String package_group) {
        this.package_group = package_group;
    }

    public void setItem_remarks(java.lang.String item_remarks) {
        this.item_remarks = item_remarks;
    }

    public void setSerials(java.lang.String serials) {
        this.serials = serials;
    }

    public void setUom(java.lang.String uom) {
        this.uom = uom;
    }

    public void setCode(java.lang.String code) {
        this.code = code;
    }

    public void setReg_no(java.lang.String reg_no) {
        this.reg_no = reg_no;
    }

    public void setAddr1(java.lang.String addr1) {
        this.addr1 = addr1;
    }

    public void setAddr2(java.lang.String addr2) {
        this.addr2 = addr2;
    }

    public void setAddr3(java.lang.String addr3) {
        this.addr3 = addr3;
    }

    public void setZip(java.lang.String zip) {
        this.zip = zip;
    }

    public void setCountrycode(java.lang.String countrycode) {
        this.countrycode = countrycode;
    }

    public void setPhone_no(java.lang.String phone_no) {
        this.phone_no = phone_no;
    }

    public void setFax_no(java.lang.String fax_no) {
        this.fax_no = fax_no;
    }

    public void setWeb_url(java.lang.String web_url) {
        this.web_url = web_url;
    }

    public void setAcc_pccenterid(java.lang.Integer acc_pccenterid) {
        this.acc_pccenterid = acc_pccenterid;
    }

    public void setInv_locationid(java.lang.Integer inv_locationid) {
        this.inv_locationid = inv_locationid;
    }

    public void setCashbook_cash(java.lang.Integer cashbook_cash) {
        this.cashbook_cash = cashbook_cash;
    }

    public void setCashbook_card(java.lang.Integer cashbook_card) {
        this.cashbook_card = cashbook_card;
    }

    public void setCashbook_cheque(java.lang.Integer cashbook_cheque) {
        this.cashbook_cheque = cashbook_cheque;
    }

    public void setCashbook_pd_cheque(java.lang.Integer cashbook_pd_cheque) {
        this.cashbook_pd_cheque = cashbook_pd_cheque;
    }

    public void setCashbook_coupon(java.lang.Integer cashbook_coupon) {
        this.cashbook_coupon = cashbook_coupon;
    }

    public void setCashbook_other(java.lang.Integer cashbook_other) {
        this.cashbook_other = cashbook_other;
    }

    public void setCashbook_payment(java.lang.Integer cashbook_payment) {
        this.cashbook_payment = cashbook_payment;
    }

    public void setLogo_url(java.lang.String logo_url) {
        this.logo_url = logo_url;
    }

    public void setPricing(java.lang.String pricing) {
        this.pricing = pricing;
    }

    public void setHotlines(java.lang.String hotlines) {
        this.hotlines = hotlines;
    }

    public void setDefault_visa_rate(java.math.BigDecimal default_visa_rate) {
        this.default_visa_rate = default_visa_rate;
    }

    public void setDefault_master_rate(java.math.BigDecimal default_master_rate) {
        this.default_master_rate = default_master_rate;
    }

    public void setDefault_amex_rate(java.math.BigDecimal default_amex_rate) {
        this.default_amex_rate = default_amex_rate;
    }

    public void setDefault_diner_rate(java.math.BigDecimal default_diner_rate) {
        this.default_diner_rate = default_diner_rate;
    }

    public void setDefault_card1_rate(java.math.BigDecimal default_card1_rate) {
        this.default_card1_rate = default_card1_rate;
    }

    public void setDefault_card2_rate(java.math.BigDecimal default_card2_rate) {
        this.default_card2_rate = default_card2_rate;
    }

    public void setDefault_card3_rate(java.math.BigDecimal default_card3_rate) {
        this.default_card3_rate = default_card3_rate;
    }

    public void setDefault_other_rate(java.math.BigDecimal default_other_rate) {
        this.default_other_rate = default_other_rate;
    }

    public void setFormat_invoice_header_text(java.lang.String format_invoice_header_text) {
        this.format_invoice_header_text = format_invoice_header_text;
    }

    public void setFormat_invoice_footer_text(java.lang.String format_invoice_footer_text) {
        this.format_invoice_footer_text = format_invoice_footer_text;
    }

    public void setFormat_invoice_entity_address(java.lang.String format_invoice_entity_address) {
        this.format_invoice_entity_address = format_invoice_entity_address;
    }

    public void setFormat_invoice_row(java.lang.Integer format_invoice_row) {
        this.format_invoice_row = format_invoice_row;
    }

    public void setFormat_invoice_type(java.lang.String format_invoice_type) {
        this.format_invoice_type = format_invoice_type;
    }

    public void setFormat_cashsale_header_text(java.lang.String format_cashsale_header_text) {
        this.format_cashsale_header_text = format_cashsale_header_text;
    }

    public void setFormat_cashsale_footer_text(java.lang.String format_cashsale_footer_text) {
        this.format_cashsale_footer_text = format_cashsale_footer_text;
    }

    public void setFormat_cashsale_entity_address(java.lang.String format_cashsale_entity_address) {
        this.format_cashsale_entity_address = format_cashsale_entity_address;
    }

    public void setFormat_cashsale_row(java.lang.Integer format_cashsale_row) {
        this.format_cashsale_row = format_cashsale_row;
    }

    public void setFormat_cashsale_type(java.lang.String format_cashsale_type) {
        this.format_cashsale_type = format_cashsale_type;
    }

    public void setFormat_receipt_header_text(java.lang.String format_receipt_header_text) {
        this.format_receipt_header_text = format_receipt_header_text;
    }

    public void setFormat_receipt_footer_text(java.lang.String format_receipt_footer_text) {
        this.format_receipt_footer_text = format_receipt_footer_text;
    }

    public void setFormat_receipt_entity_address(java.lang.String format_receipt_entity_address) {
        this.format_receipt_entity_address = format_receipt_entity_address;
    }

    public void setFormat_receipt_row(java.lang.Integer format_receipt_row) {
        this.format_receipt_row = format_receipt_row;
    }

    public void setFormat_receipt_type(java.lang.String format_receipt_type) {
        this.format_receipt_type = format_receipt_type;
    }

    public void setFormat_purchase_order_header_text(java.lang.String format_purchase_order_header_text) {
        this.format_purchase_order_header_text = format_purchase_order_header_text;
    }

    public void setFormat_purchase_order_footer_text(java.lang.String format_purchase_order_footer_text) {
        this.format_purchase_order_footer_text = format_purchase_order_footer_text;
    }

    public void setFormat_purchase_order_row(java.lang.Integer format_purchase_order_row) {
        this.format_purchase_order_row = format_purchase_order_row;
    }

    public void setFormat_purchase_order_type(java.lang.String format_purchase_order_type) {
        this.format_purchase_order_type = format_purchase_order_type;
    }

    public void setFormat_salary_slip_header_text(java.lang.String format_salary_slip_header_text) {
        this.format_salary_slip_header_text = format_salary_slip_header_text;
    }

    public void setFormat_salary_slip_footer_text(java.lang.String format_salary_slip_footer_text) {
        this.format_salary_slip_footer_text = format_salary_slip_footer_text;
    }

    public void setFormat_salary_slip_row(java.lang.Integer format_salary_slip_row) {
        this.format_salary_slip_row = format_salary_slip_row;
    }

    public void setFormat_salary_slip_type(java.lang.String format_salary_slip_type) {
        this.format_salary_slip_type = format_salary_slip_type;
    }

    public void setFormat_payment_voucher_header_text(java.lang.String format_payment_voucher_header_text) {
        this.format_payment_voucher_header_text = format_payment_voucher_header_text;
    }

    public void setFormat_payment_voucher_footer_text(java.lang.String format_payment_voucher_footer_text) {
        this.format_payment_voucher_footer_text = format_payment_voucher_footer_text;
    }

    public void setFormat_payment_voucher_row(java.lang.Integer format_payment_voucher_row) {
        this.format_payment_voucher_row = format_payment_voucher_row;
    }

    public void setFormat_payment_voucher_type(java.lang.String format_payment_voucher_type) {
        this.format_payment_voucher_type = format_payment_voucher_type;
    }

    public void setFormat_credit_memo_header_text(java.lang.String format_credit_memo_header_text) {
        this.format_credit_memo_header_text = format_credit_memo_header_text;
    }

    public void setFormat_credit_memo_footer_text(java.lang.String format_credit_memo_footer_text) {
        this.format_credit_memo_footer_text = format_credit_memo_footer_text;
    }

    public void setFormat_credit_memo_row(java.lang.Integer format_credit_memo_row) {
        this.format_credit_memo_row = format_credit_memo_row;
    }

    public void setFormat_credit_memo_type(java.lang.String format_credit_memo_type) {
        this.format_credit_memo_type = format_credit_memo_type;
    }

    public void setDefault_rma_location_cust(java.lang.Integer default_rma_location_cust) {
        this.default_rma_location_cust = default_rma_location_cust;
    }

    public void setDefault_rma_location_supp(java.lang.Integer default_rma_location_supp) {
        this.default_rma_location_supp = default_rma_location_supp;
    }

    public void setDefault_rma_location_hq(java.lang.Integer default_rma_location_hq) {
        this.default_rma_location_hq = default_rma_location_hq;
    }

    public void setDefault_rma_location_branch(java.lang.Integer default_rma_location_branch) {
        this.default_rma_location_branch = default_rma_location_branch;
    }

    public void setManager_password01(java.lang.String manager_password01) {
        this.manager_password01 = manager_password01;
    }

    public void setManager_password02(java.lang.String manager_password02) {
        this.manager_password02 = manager_password02;
    }

    public void setManager_password03(java.lang.String manager_password03) {
        this.manager_password03 = manager_password03;
    }

    public void setManager_password04(java.lang.String manager_password04) {
        this.manager_password04 = manager_password04;
    }

    public void setManager_password05(java.lang.String manager_password05) {
        this.manager_password05 = manager_password05;
    }

    public void setManager_password06(java.lang.String manager_password06) {
        this.manager_password06 = manager_password06;
    }

    public void setManager_password07(java.lang.String manager_password07) {
        this.manager_password07 = manager_password07;
    }

    public void setManager_password08(java.lang.String manager_password08) {
        this.manager_password08 = manager_password08;
    }

    public void setManager_password09(java.lang.String manager_password09) {
        this.manager_password09 = manager_password09;
    }

    public void setTelephone_developer(java.lang.String telephone_developer) {
        this.telephone_developer = telephone_developer;
    }

    public void setTelephone_sysadmin(java.lang.String telephone_sysadmin) {
        this.telephone_sysadmin = telephone_sysadmin;
    }

    public void setTelephone_clerk(java.lang.String telephone_clerk) {
        this.telephone_clerk = telephone_clerk;
    }

    public void setTelephone_admin(java.lang.String telephone_admin) {
        this.telephone_admin = telephone_admin;
    }

    public void setTelephone_manager(java.lang.String telephone_manager) {
        this.telephone_manager = telephone_manager;
    }

    public void setTelephone_sales(java.lang.String telephone_sales) {
        this.telephone_sales = telephone_sales;
    }

    public void setTelephone_tech_support(java.lang.String telephone_tech_support) {
        this.telephone_tech_support = telephone_tech_support;
    }

    public void setTelephone_director(java.lang.String telephone_director) {
        this.telephone_director = telephone_director;
    }

    public void setMobilephone_developer(java.lang.String mobilephone_developer) {
        this.mobilephone_developer = mobilephone_developer;
    }

    public void setMobilephone_sysadmin(java.lang.String mobilephone_sysadmin) {
        this.mobilephone_sysadmin = mobilephone_sysadmin;
    }

    public void setMobilephone_clerk(java.lang.String mobilephone_clerk) {
        this.mobilephone_clerk = mobilephone_clerk;
    }

    public void setMobilephone_admin(java.lang.String mobilephone_admin) {
        this.mobilephone_admin = mobilephone_admin;
    }

    public void setMobilephone_manager(java.lang.String mobilephone_manager) {
        this.mobilephone_manager = mobilephone_manager;
    }

    public void setMobilephone_sales(java.lang.String mobilephone_sales) {
        this.mobilephone_sales = mobilephone_sales;
    }

    public void setMobilephone_tech_support(java.lang.String mobilephone_tech_support) {
        this.mobilephone_tech_support = mobilephone_tech_support;
    }

    public void setMobilephone_director(java.lang.String mobilephone_director) {
        this.mobilephone_director = mobilephone_director;
    }

    public void setEmail_system(java.lang.String email_system) {
        this.email_system = email_system;
    }

    public void setEmail_developer(java.lang.String email_developer) {
        this.email_developer = email_developer;
    }

    public void setEmail_sysadmin(java.lang.String email_sysadmin) {
        this.email_sysadmin = email_sysadmin;
    }

    public void setEmail_clerk(java.lang.String email_clerk) {
        this.email_clerk = email_clerk;
    }

    public void setEmail_admin(java.lang.String email_admin) {
        this.email_admin = email_admin;
    }

    public void setEmail_manager(java.lang.String email_manager) {
        this.email_manager = email_manager;
    }

    public void setEmail_sales(java.lang.String email_sales) {
        this.email_sales = email_sales;
    }

    public void setEmail_tech_support(java.lang.String email_tech_support) {
        this.email_tech_support = email_tech_support;
    }

    public void setEmail_director(java.lang.String email_director) {
        this.email_director = email_director;
    }

    public void setCustomer_id(java.lang.Integer customer_id) {
        this.customer_id = customer_id;
    }

    public void setSupplier_id(java.lang.Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public void setLoyalty_card_no_prefix(java.lang.String loyalty_card_no_prefix) {
        this.loyalty_card_no_prefix = loyalty_card_no_prefix;
    }

    public void setLoyalty_card_no_postfix(java.lang.String loyalty_card_no_postfix) {
        this.loyalty_card_no_postfix = loyalty_card_no_postfix;
    }

    public void setCrv_day_from(java.lang.Integer crv_day_from) {
        this.crv_day_from = crv_day_from;
    }

    public void setCrv_day_to(java.lang.Integer crv_day_to) {
        this.crv_day_to = crv_day_to;
    }

    public void setFormat_proforma_invoice_type(java.lang.String format_proforma_invoice_type) {
        this.format_proforma_invoice_type = format_proforma_invoice_type;
    }

    public void setFormat_proforma_invoice_footer_text(java.lang.String format_proforma_invoice_footer_text) {
        this.format_proforma_invoice_footer_text = format_proforma_invoice_footer_text;
    }

    public void setFormat_proforma_invoice_header_text(java.lang.String format_proforma_invoice_header_text) {
        this.format_proforma_invoice_header_text = format_proforma_invoice_header_text;
    }

    public void setFormat_proforma_invoice_row(java.lang.Integer format_proforma_invoice_row) {
        this.format_proforma_invoice_row = format_proforma_invoice_row;
    }

    public void setFormat_service_note_type(java.lang.String format_service_note_type) {
        this.format_service_note_type = format_service_note_type;
    }

    public void setFormat_service_note_footer_text(java.lang.String format_service_note_footer_text) {
        this.format_service_note_footer_text = format_service_note_footer_text;
    }

    public void setFormat_service_note_header_text(java.lang.String format_service_note_header_text) {
        this.format_service_note_header_text = format_service_note_header_text;
    }

    public void setFormat_service_note_row(java.lang.Integer format_service_note_row) {
        this.format_service_note_row = format_service_note_row;
    }

    public void setTax_reg_number(java.lang.String tax_reg_number) {
        this.tax_reg_number = tax_reg_number;
    }

    public void setBranch_state(java.lang.String branch_state) {
        this.branch_state = branch_state;
    }

   private List<SubReportBean> subReportBeanList = new ArrayList<SubReportBean>();    
   
   public List<SubReportBean> getSubReportBeanList() {
      return subReportBeanList;
   }

   public void setSubReportBeanList(List<SubReportBean> subReportBeanList) {
      this.subReportBeanList = subReportBeanList;
   }    
   
   private String partyId;
   private String partyIdentification;
   
   
/*
    public static Collection getInvoiceList() {
        Vector invoices = new Vector();
        int invoiceNum = 0;
        Invoice invoice = null;

        try {
            for (int i = 0; i < 10; i++) {


                    for (int j = 0; j < 5; j++) {
                        invoice = new Invoice();
                        invoiceNum = i;
                        invoice.invoice_id = Integer.valueOf(invoiceNum).longValue();
                        invoice.item_code = String.valueOf(j);
                        invoices.add(invoice);
                    }//

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return invoices;
    }
  */  

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyIdentification() {
        return partyIdentification;
    }

    public void setPartyIdentification(String partyIdentification) {
        this.partyIdentification = partyIdentification;
    }
}
