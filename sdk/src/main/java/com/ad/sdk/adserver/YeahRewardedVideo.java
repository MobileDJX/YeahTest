package com.ad.sdk.adserver;


import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.ad.sdk.adserver.Listener.YeahRewardedAdLoadListener;
import com.ad.sdk.adserver.Listener.YeahRewardedAdShowListener;


public class YeahRewardedVideo {
    public static YeahRewardedAdLoadListener rewardedListener = null;
    public static YeahRewardedAdShowListener rewardedshowListener = null;

    public static void setRewardedListen(YeahRewardedAdLoadListener rewardadListen) {
        rewardedListener = rewardadListen;
    }

    public static void setRewardedshowListener(YeahRewardedAdShowListener rewardedshowListener) {
        YeahRewardedVideo.rewardedshowListener = rewardedshowListener;
    }

    @SuppressLint("LongLogTag")
    public static void load(Context context, YeahRewardedAdLoadListener listener) {
        setRewardedListen(listener);
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("RewardedVideo", MODE_PRIVATE);
            String ad_url = sharedPreferences.getString("RewardedVideo_URL", "");
            String ad_check = sharedPreferences.getString("ad_check", "0");

            if (ad_check.equalsIgnoreCase("1")) {
                if (ad_url.length() > 0) {
                    Log.e("RewardedVideoStatus:", "" + "Ready to show");
                    listener.onYeahAdsAdLoaded();
                } else {
                    Log.d("SDK", "No Ads:");
                    listener.onYeahAdsAdFailed();
                }
            } else {
                Log.e("Ad Shown Status :", "Targeting Not Match");
            }

        } catch (Exception e) {
            Log.d("SDK", "Rewardedvideo Ad Exception:" + e);
        }


    }

    @SuppressLint("LongLogTag")
    public static void show(Context context, YeahRewardedAdShowListener rewardedAdShowListener) {
        setRewardedshowListener(rewardedAdShowListener);
        Intent i = new Intent(context, RewardedLoadActivity.class);
        context.startActivity(i);
        Log.e("RewardedVideoStatus:", "" + "Ad is showing");


    }


}


