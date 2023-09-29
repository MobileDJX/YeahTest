package com.ad.sdk.adserver;

class Config {


    //Live server
    public static String ADSERVER_URL = "https://app.yeahads.net/api/request_sdk.php";


    //Local Server
//    public static String ADSERVER_URL = "https://revphpe.djaxbidder.com/Yeah_app/api/request_sdkang.php";


    // AD REQUEST API RESPONSE - JSON FIELDS LIST

    final static public String TAG_AD_RESPONSE = "response";
    final static public String TAG_ADS = "ads";
    final static public String TAG_CLICK_URL = "click_url";
    final static public String TAG_ADTAG = "ad_tag";
    final static public String TAG_BEACON_URL = "imp_url";
    final static public String TAG_AD_TYPE = "ad_type";
    final static public String TAG_ERROR = "error";
    final static public String TAG_ERR_CODE = "code";
    final static public String TAG_ERR_DESC = "description";
}
