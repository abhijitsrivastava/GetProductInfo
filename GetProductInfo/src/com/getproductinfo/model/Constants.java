package com.getproductinfo.model;

public class Constants {

	public static final String SERVICE_URL = "http://85.214.232.99:9090/middleware/MWServlet";
	
	public static final String POST_DATA = "platform=android&serviceID=getProductDetail&outletId=1000&applicationName=PRODUCTADVISOR&cacheid=&userName=admin&storeWebServicesIp=10.10.10.10%3A8080&channel=rc&appver=1.0.25&appID=PAMM&productId=";
	public static final String POST_DATA_FOR_COMPETITOR = "platform=android&serviceID=getCompetitorPrices_NL&outletId=1000&applicationName=PRODUCTADVISOR&cacheid=&userName=admin&storeWebServicesIp=10.10.10.10%3A8080&channel=rc&appver=1.0.25&appID=PAMM&productId=";
	public static final String POST_DATA_FOR_STORES = "platform=android&serviceID=showStores&channel=rc&appID=AdminBackend&key=&myID=2";

	public static final String OUTLET_ID = "platform=android&serviceID=getProductDetail&outletId=";
	public static final String STORE_WEB_SERVICE_IP = "&applicationName=PRODUCTADVISOR&cacheid=&userName=admin&storeWebServicesIp=";
	public static final String PRODUCT_ID = "&channel=rc&appver=1.0.25&appID=PAMM&productId=";

	// product keys
	public static final String KEY_NAME = "NAME";
	public static final String KEY_STOCK6 = "STOCK6";
	public static final String KEY_HERSTELLER = "HERSTELLER";
	public static final String KEY_ARTIKELNUMMER = "ARTIKELNUMMER";
	public static final String KEY_DOI = "DOI";
	public static final String KEY_STOCK2 = "STOCK2";
	
	// outlet keys
	public static final String KEY_OUTLET_ID = "outlet_id";
	public static final String KEY_STORE_WEB_SERVICE_IP = "store_web_service_ip";
	
	public static final String KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY = "isComingFromAppLaunchActivity";
	
	public static final String KEY_STORE_NAME = "store_name";
}