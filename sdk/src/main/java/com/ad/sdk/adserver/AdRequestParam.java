package com.ad.sdk.adserver;

import com.ad.sdk.mtrack.Device_settings;
import com.ad.sdk.mtrack.Utils;

import java.util.HashMap;

public class AdRequestParam extends Config {


    //DAC158
    private static HashMap<String, String> URL_PARAMS = null;

    private static HashMap<String, String> HEADER_PARAMS = null;


    //DAC158
    public static HashMap<String, String> getURL_PARAMS() {
        return URL_PARAMS;
    }


    public static HashMap<String, String> getHEADER_PARAMS() {
        return HEADER_PARAMS;
    }


    //end

    public AdRequestParam() {
        super();
        // TODO Auto-generated constructor stub
        if (HEADER_PARAMS != null) HEADER_PARAMS.clear();
        if (URL_PARAMS != null) URL_PARAMS.clear();
    }


    public AdRequestParam GenerateRequestURL(Device_settings dInfo, YeahAdsInitialize imgad) {

        HashMap<String, String> url_params = new HashMap<>();
        HashMap<String, String> h_params = new HashMap<>();

        url_params.put("publisherid", imgad.getZoneid());
        url_params.put("adwidth", String.valueOf(dInfo.screen_width));
        url_params.put("adheight", String.valueOf(dInfo.screen_height));
        url_params.put("carrier", String.valueOf(dInfo.carrier));


        URL_PARAMS = url_params;

        h_params.put("publisherid", imgad.getZoneid());

        h_params.put("screenwidth", String.valueOf(dInfo.screen_width));
        h_params.put("screenheight", String.valueOf(dInfo.screen_height));

        h_params.put("displaywidth", String.valueOf(dInfo.display_width));
        h_params.put("displayheight", String.valueOf(dInfo.display_height));

        h_params.put("model", String.valueOf(dInfo.model));

        h_params.put("make", String.valueOf(dInfo.make));
        h_params.put("os", String.valueOf(dInfo.os));
        h_params.put("osv", String.valueOf(dInfo.os_ver));
        h_params.put("carrier", String.valueOf(dInfo.carrier));


        h_params.put("udid", String.valueOf(dInfo.android_id));
        h_params.put("Dpidsha1", String.valueOf(dInfo.didsha1));
        h_params.put("Dpidmd5", String.valueOf(dInfo.didmd5));

        h_params.put("js", String.valueOf(dInfo.js_enable));
        h_params.put("appId", String.valueOf(dInfo.app_id));
        h_params.put("useragent", String.valueOf(dInfo.ua));
        h_params.put("language", String.valueOf(dInfo.language));


        h_params.put("connectionType", String.valueOf(dInfo.connection_type));
        h_params.put("dataSpeed", String.valueOf(dInfo.data_speed));
        h_params.put("deviceType", String.valueOf(dInfo.device_type));

        h_params.put("timezone", String.valueOf(dInfo.timezone));
        h_params.put("viewerName", String.valueOf(dInfo.user_name));
        h_params.put("viewerEmail", String.valueOf(dInfo.user_email));
        h_params.put("viewerPhone", String.valueOf(dInfo.user_phone));


        h_params.put("latitude", String.valueOf(dInfo.user_latitude));
        h_params.put("longitude", String.valueOf(dInfo.user_longitude));

        h_params.put("device_email", String.valueOf(dInfo.user_email));
        h_params.put("Dmp_id", String.valueOf(dInfo.getGAID()));
        h_params.put("userid", "");
        h_params.put("ip", Utils.getLocalIpAddress());


        HEADER_PARAMS = h_params;

        return this;
    }


}
