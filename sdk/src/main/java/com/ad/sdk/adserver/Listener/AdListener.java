package com.ad.sdk.adserver.Listener;

import com.ad.sdk.adserver.YeahAdsInitialize;

public interface AdListener {
	void param_required(YeahAdsInitialize ad, boolean flag);
	
	void internet_connection_failed(YeahAdsInitialize ad, boolean flag);
	
	void load_ad_failed(YeahAdsInitialize ad, boolean flag, String ecode, String edesc);
	
}
