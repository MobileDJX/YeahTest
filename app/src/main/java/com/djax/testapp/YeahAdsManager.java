package com.djax.testapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.ad.sdk.adserver.Listener.AdViewListener;
import com.ad.sdk.adserver.Listener.BannerListener;
import com.ad.sdk.adserver.Listener.InterstitialImageAdListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialAdShowListener;
import com.ad.sdk.adserver.Listener.YeahInterstitialLoadAdListener;
import com.ad.sdk.adserver.Listener.YeahRewardedAdLoadListener;
import com.ad.sdk.adserver.Listener.YeahRewardedAdShowListener;
import com.ad.sdk.adserver.YeahAdsInitialize;
import com.ad.sdk.adserver.YeahBannerImageAD;
import com.ad.sdk.adserver.YeahInterstitial;
import com.ad.sdk.adserver.YeahInterstitialImage;
import com.ad.sdk.adserver.YeahRewardedVideo;


public class YeahAdsManager {

    private static String myZoneId = "21";
    private static Activity mActivity;

    public static void init(Activity appActivity) {
        mActivity = appActivity;

        //Initialize the SDK
        new YeahAdsInitialize(mActivity, myZoneId, new AdViewListener() {
            @Override
            public void onInitializationComplete() {
                Log.e("SDK_Init_Status : ", "" + "Initialization Successfully");
                loadInterstitialYeahAds();
                loadRewardedYeahAds();
            }

            @Override
            public void onInitializationFailure() {
                Log.e("SDK_Init_Status : ", "" + "Initialization Failed");
            }
        });

    }


    public static void showBanner() {
        YeahBannerImageAD.show(mActivity, new BannerListener() {
            @Override
            public void onYeahAdsAdLoaded() {
                Log.e(" Banner AD Status :", "" + "Loaded");
            }

            @Override
            public void onYeahAdsAdFailed() {
                Log.e(" Banner AD Status :", "" + "Failed");
            }
        });
    }


    public static void showIntImage() {

        YeahInterstitialImage.show(mActivity, new InterstitialImageAdListener() {
            @Override
            public void onInterstitialAdLoaded() {

            }

            @Override
            public void onInterstitialAdFailed() {

            }

            @Override
            public void onInterstitialAdShown() {

            }

            @Override
            public void onInterstitialAdClicked() {

            }

            @Override
            public void onInterstitialAdDismissed() {

            }
        });
    }


    public static void loadInterstitialYeahAds() {

        YeahInterstitial.load(mActivity, new YeahInterstitialLoadAdListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsAdLoaded() {
                Log.e("Interstitial Video Load Status :", "" + "Loaded");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsAdFailed() {
                Log.e("Interstitial Video Load Status :", "" + "Failed");
            }
        });

    }


    public static void loadRewardedYeahAds() {
        YeahRewardedVideo.load(mActivity, new YeahRewardedAdLoadListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsAdLoaded() {
                Log.e("Rewarded Video Load Status :", "" + "Loaded");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsAdFailed() {
                Log.e("Rewarded Video Load Status :", "" + "Failed");
            }
        });

    }


    public static void showInterstitialYeahAds() {

        YeahInterstitial.show(mActivity, new YeahInterstitialAdShowListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowFailure() {
                Log.e("Interstitial Video Show Status :", "" + "Ad Failed");

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowStart() {
                Log.e("Interstitial Video Show Status :", "" + "Ad Showed");


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowClick() {
                Log.e("Interstitial Video Click Status :", "" + "Ad Clicked");

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowComplete() {
                Log.e("Interstitial Video Show Status :", "" + "Ad Dismissed");

            }
        });
    }


    public static void showRewardedYeahAds() {
        YeahRewardedVideo.show(mActivity, new YeahRewardedAdShowListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowFailure() {
                Log.e("Rewarded Video Show Status :", "" + "Ad show Failure");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowStart() {
                Log.e("Rewarded Video Click Status :", "" + "Ad Showed");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowClicked() {
                Log.e("Rewarded Video Click Status :", "" + "Ad Clicked");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onYeahAdsShowComplete() {
                Log.e("Rewarded Video Show Status :", "" + "Ad Closed");

            }

            @Override
            public void Rewarded(String rewardItem, int rewarded) {
                Toast.makeText(mActivity, "Reward Point : " + rewarded, Toast.LENGTH_SHORT).show();
            }
        });

    }


}