package com.ad.sdk.adserver.Listener;

public interface InterstitialVideoAdListener {

    void onInterstitialAdLoaded();

    void onInterstitialAdFailed();

    void onInterstitialAdShown();

    void onInterstitialAdClicked();

    void onInterstitialAdDismissed();
}
