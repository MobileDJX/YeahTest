package com.ad.sdk.adserver.Listener;

public interface PopupAdListener {

    void onYeahAdsShowFailure();

    void onYeahAdsShowStart();

    void onYeahAdsShowClick();

    void onYeahAdsShowComplete();

    void onYeahAdsDismissed();
}
