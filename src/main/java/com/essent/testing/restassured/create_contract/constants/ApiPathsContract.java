package com.essent.testing.restassured.create_contract.constants;

import com.essent.testing.config.ConfigKey;
import com.essent.testing.config.ConfigProvider;

public class ApiPathsContract {

	public static final String CRM_ROOT_API = ConfigProvider.getProperty(ConfigKey.CRM_ROOT_API);

	public static final String API_LOGIN_CRM = CRM_ROOT_API + "/V8/login";
	public static final String API_CREATE_QUOTE_B2B_TC1 = CRM_ROOT_API + "/V8_Custom/Flow/B2B_CQ_TC1";
	public static final String API_CREATE_QUOTE_B2B_TC2_UP = CRM_ROOT_API + "/V8_Custom/Flow/cupq";
	public static final String API_CREATE_QUOTE_B2C = CRM_ROOT_API + "/V8_Custom/Flow/B2C_CQ";
	public static final String API_QUOTES_ON_ACCOUNT = CRM_ROOT_API + "/V8_Custom/List/QuotesOnAccount";
	public static final String API_CONTRACT_ON_ACCOUNT = CRM_ROOT_API + "/V8_Custom/List/ContractsOnAccount";
	public static final String API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER = CRM_ROOT_API + "/V8_Custom/ActionDTO/modal_to_gf_quote_send_to_customer";
	public static final String API_MODAL_TO_GF_QUOTE_SEND_TO_CUSTOMER_AND_RELOAD_LIST = CRM_ROOT_API + "/V8_Custom/ActionDTO/modal_to_gf_quote_send_to_customer_and_reload_list";
	public static final String API_GF_QUOTE_SEND_TO_CUSTOMER = CRM_ROOT_API + "/V8_Custom/Flow/gf_quote_send_to_customer";
	public static final String API_GF_QUOTE_SIGNATURE_RECEIVED = CRM_ROOT_API + "/V8_Custom/Flow/gf_quote_signatureReceived";
	public static final String API_FILE_UPLOAD = CRM_ROOT_API + "/V8_Custom/fileupload";
	public static final String API_SIGN_QUOTE_MODAL_TC1 = CRM_ROOT_API + "/V8_Custom/Flow/sign_quote_modal_tc1";
	public static final String API_SIGN_QUOTE_MODAL_TC2_UP = CRM_ROOT_API + "/V8_Custom/Flow/sign_quote_modal_tc2_up";
	public static final String API_VERIFY_CONTRACT_CREATED = CRM_ROOT_API + "/V8_Custom/List/ContractsOnAccount";
	public static final String API_LIST_BILLING_CUSTOMER_ACCOUNT = CRM_ROOT_API + "/V8_Custom/List/BillingCustomerOnaccount";
	public static final String API_MODAL_TO_GF_SIGN_MANDATE_PAPER = CRM_ROOT_API + "/V8_Custom/ActionDTO/Modal_to_gf_sign_mandate_paper";
	public static final String API_GF_SIGN_MANDATE_PAPER = CRM_ROOT_API + "/V8_Custom/Flow/gf_sign_mandate_paper";

	public static final String API_CONTRACTED_EAN = CRM_ROOT_API + "/V8_Custom/List/ContractedEansOnAccount";
	public static final String API_GET_ACCOUNT_NUMBER = CRM_ROOT_API + "/V8_Custom/BlueSidebar/Accounts/{recordId}";

	public static final String API_LIST_QUOTES = CRM_ROOT_API + "/V8_Custom/List/Quotes";
}
