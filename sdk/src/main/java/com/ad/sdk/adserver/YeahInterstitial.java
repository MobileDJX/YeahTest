package com.ad.sdk.adserver;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.ad.sdk.adserver.Listener.YeahInterstitialLoadAdListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialAdShowListener;

public class YeahInterstitial {

    public static YeahInterstitialLoadAdListener interstitialVideoAdListener = null;

    public static YeahInterstitialAdShowListener int_show_listener = null;

    public static void setInterstitialVideoAdListener(YeahInterstitialLoadAdListener interstitialVideoAdListener) {
        YeahInterstitial.interstitialVideoAdListener = interstitialVideoAdListener;
    }

    public static void setInt_show_listener(YeahInterstitialAdShowListener int_show_listener) {
        YeahInterstitial.int_show_listener = int_show_listener;
    }

    @SuppressLint("LongLogTag")
    public static void load(Context context, YeahInterstitialLoadAdListener interstitialVideoAdListener) {
        try {
            setInterstitialVideoAdListener(interstitialVideoAdListener);
            SharedPreferences sharedPreferences = context.getSharedPreferences("InterstitialVideo", MODE_PRIVATE);
            String ad_url = sharedPreferences.getString("InterstitialVideo_URL", "");
            String ad_check = sharedPreferences.getString("ad_check", "0");


            if (ad_url.length() > 0) {

                if (ad_check.equalsIgnoreCase("1")) {
                    YeahInterstitial.interstitialVideoAdListener.onYeahAdsAdLoaded();


                } else {
                    Log.e("Ad Shown Status", "Targeting Not Match");
                    YeahInterstitial.interstitialVideoAdListener.onYeahAdsAdFailed();
                }

//
            } else {
                Log.d("SDK", "No Ads");
            }


        } catch (Exception e) {
            Log.d("SDK", "InterstitialVideo Ad Exception:" + e);
        }


    }


    @SuppressLint("LongLogTag")
    public static void show(Context context, YeahInterstitialAdShowListener int_show_listener) {
        setInt_show_listener(int_show_listener);
        Intent i = new Intent(context, InterstitialLoadActivity.class);
        Log.e("InterstitialVideoStatus:", "" + "Ad is showing");
        context.startActivity(i);

    }

}
