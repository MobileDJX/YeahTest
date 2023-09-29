package com.ad.sdk.mtrack;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class Connectivity{
	public static NetworkInfo getNetworkInfo(Context context){
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    return cm.getActiveNetworkInfo();
	}
}
