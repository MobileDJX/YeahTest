package com.ad.sdk.adserver.Listener;

public interface YeahRewardedAdShowListener {

    void onYeahAdsShowFailure();

    void onYeahAdsShowStart();


    void onYeahAdsShowClicked();

    void onYeahAdsShowComplete();


    void Rewarded(String rewardItem, int rewarded);


}
